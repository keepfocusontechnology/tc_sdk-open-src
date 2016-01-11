package com.gavegame.tiancisdk.presenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.BaseActivity;
import com.gavegame.tiancisdk.alipay.PayResult;
import com.gavegame.tiancisdk.alipay.SignUtils;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.AlipayEntity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.OrderUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayVIew;

public class AlipayPayPresenter implements PayPresenter {

	private static final int SDK_PAY_FLAG = 1;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					// TCLogUtils.showToast(getApplicationContext(), "支付成功");
					// Intent data = getIntent();
					// data.putExtra("pay_resultcode", 200);
					// setResult(PAY_RESULT, data);
					payView.paySuccessAction();

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						// TCLogUtils
						// .showToast(getApplicationContext(), "支付结果确认中");
						// Intent data = getIntent();
						// data.putExtra("pay_resultcode", 300);
						// setResult(PAY_RESULT, data);
						payView.payWaitAction();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						// TCLogUtils.showToast(getApplicationContext(),
						// "支付失败");
						// Intent data = getIntent();
						// data.putExtra("pay_resultcode", 400);
						// setResult(PAY_RESULT, data);
						payView.payFailedAction();
					}
				}

				break;
			}
			default:
				break;
			}
		};
	};

	private final String TAG = "AlipayPayPresenter";

	// 商户PID
	private String PARTNER;
	// 商户收款账号
	private String SELLER;
	// 商户私钥，pkcs8格式
	private String RSA_PRIVATE;
	// 支付宝公钥
	private String RSA_PUBLIC;

	private String notify_url;

	private String cp_orderId;

	private String subject;
	private String body;
	private String amount;
	private String roleId;
	private String serverId;

	private IPayVIew payView;

	private Activity context;

	public AlipayPayPresenter(BaseActivity activity) {
		this.payView = (IPayVIew) activity;
		this.context = activity;
		TianCi.init(activity);

		Intent intent = context.getIntent();
		subject = intent.getStringExtra("subject");
		body = intent.getStringExtra("body");
		amount = intent.getStringExtra("price");
		roleId = intent.getStringExtra("roleId");
		serverId = intent.getStringExtra("serverId");
		cp_orderId = intent.getStringExtra("cp_orderId");
	}

	@Override
	public void pay() {

		TianCi.getInstance().getOrder(roleId, cp_orderId, serverId, amount,
				PayWay.alipay.getPayway(), new RequestCallBack() {

					@Override
					public void onSuccessed(ResponseMsg responseMsg) {
						AlipayEntity entity = (AlipayEntity) responseMsg
								.getBaseOrder();
						PARTNER = entity.pantner;
						SELLER = entity.seller;
						RSA_PRIVATE = entity.rsa_private;
						RSA_PUBLIC = entity.rsa_public;
						notify_url = entity.notify_url;

						// tc自身的订单号,暂不涉及到渠道支付
						String tc_order = entity.orderId;

						if (TextUtils.isEmpty(PARTNER)
								|| TextUtils.isEmpty(RSA_PRIVATE)
								|| TextUtils.isEmpty(SELLER)) {
							new AlertDialog.Builder(context)
									.setTitle("警告")
									.setMessage(
											"需要配置PARTNER | RSA_PRIVATE| SELLER")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialoginterface,
														int i) {
													//
													// finish();
												}
											}).show();
							return;
						}

						// 订单
						// String orderInfo = getOrderInfo(subject, body,
						// price);
						String orderInfo = OrderUtils.getAlipayOrderInfo(
								subject, body, amount, cp_orderId, PARTNER,
								SELLER, notify_url);
						TCLogUtils.e(TAG, orderInfo);

						// 对订单做RSA 签名
						String sign = sign(orderInfo);
						try {
							// 仅需对sign 做URL编码
							sign = URLEncoder.encode(sign, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

						// 完整的符合支付宝参数规范的订单信息
						final String payInfo = orderInfo + "&sign=\"" + sign
								+ "\"&" + getSignType();

						Runnable payRunnable = new Runnable() {

							@Override
							public void run() {
								// 构造PayTask 对象
								PayTask alipay = new PayTask(context);
								// 调用支付接口，获取支付结果
								String result = alipay.pay(payInfo);

								Message msg = new Message();
								msg.what = SDK_PAY_FLAG;
								msg.obj = result;
								mHandler.sendMessage(msg);
							}
						};

						// 必须异步调用
						Thread payThread = new Thread(payRunnable);
						payThread.start();

					}

					@Override
					public void onFailure(String msg) {
						payView.payFailedAction();
					}
				});

	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

}
