package com.gavegame.tiancisdk.activity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.alipay.PayResult;
import com.gavegame.tiancisdk.alipay.SignUtils;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.AlipayEntity;
import com.gavegame.tiancisdk.network.BaseOrder;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.OrderUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.widget.ImageRadiobutton;
import com.gavegame.tiancisdk.widget.ImageRadiobutton.RadioButtonCheckedListener;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class TCPayActivity extends BaseActivity {

	private final String TAG = "TCPayActivity";
	private String subject;
	private String body;
	private String price;

	// "123",
	// new Random().nextInt(100000) + "", "152001", price,
	// payWay.getPayway(), new RequestCallBack()

	private String roleId;
	private String serverId;
	private String cp_orderId;

	private ImageRadiobutton pay_alipay;
	private ImageRadiobutton pay_wechat;
	private ImageRadiobutton pay_bank;
	private ImageRadiobutton pay_cht;

	// 缓存上次选择的radioButton
	private ImageRadiobutton cache;

	private PayWay payWay = PayWay.alipay;

	// 商户PID
	private String PARTNER;
	// 商户收款账号
	private String SELLER;
	// 商户私钥，pkcs8格式
	private String RSA_PRIVATE;
	// 支付宝公钥
	private String RSA_PUBLIC;

	private String notify_url;

	private String orderId;

	private static final int SDK_PAY_FLAG = 1;

	private static final int PAY_RESULT = 2233;

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
					TCLogUtils.showToast(getApplicationContext(), "支付成功");
					Intent data = getIntent();
					data.putExtra("pay_resultcode", 200);
					setResult(PAY_RESULT, data);
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						TCLogUtils
								.showToast(getApplicationContext(), "支付结果确认中");
						Intent data = getIntent();
						data.putExtra("pay_resultcode", 300);
						setResult(PAY_RESULT, data);
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						TCLogUtils.showToast(getApplicationContext(), "支付失败");
						Intent data = getIntent();
						data.putExtra("pay_resultcode", 400);
						setResult(PAY_RESULT, data);
					}
				}

				break;
			}
			default:
				break;
			}
			finish();
		};
	};
	private TextView tv_game_name;
	private TextView tv_pay_amount;

	private void back() {

		new AlertDialog.Builder(this).setTitle("支付未完成")
				.setMessage("支付未完成，确定退出本次支付吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	@Override
	void initId() {
		tv_game_name = (TextView) findViewById(R.id.tv_game_name);
		tv_pay_amount = (TextView) findViewById(R.id.tv_pay_amount);
		pay_alipay = (ImageRadiobutton) findViewById(R.id.pay_alipay);
		pay_wechat = (ImageRadiobutton) findViewById(R.id.pay_wechat);
		pay_bank = (ImageRadiobutton) findViewById(R.id.pay_bank);
		pay_cht = (ImageRadiobutton) findViewById(R.id.pay_cht);
		ImageView iv_back = (ImageView) findViewById(R.id.iv_pay_back);
		// 返回键
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}
		});
		// final ImageRadiobutton irbs[] = { pay_alipay, pay_wechat, pay_bank,
		// pay_cht };
		pay_alipay.setChecked();
		cache = pay_alipay;
		pay_alipay.setCheckListener(new RadioButtonCheckedListener() {
			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.alipay;
				pay_alipay.setChecked();
				if (cache != null && cache != pay_alipay)
					cache.setUnChecked();
				cache = pay_alipay;
			}
		});

		pay_wechat.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.wechat;
				// setAllUnChecked(irbs);
				pay_wechat.setChecked();
				if (cache != null && cache != pay_wechat)
					cache.setUnChecked();
				cache = pay_wechat;
			}
		});

		pay_bank.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.yinlian;
				pay_bank.setChecked();
				if (cache != null && cache != pay_bank)
					cache.setUnChecked();
				cache = pay_bank;
			}
		});

		pay_cht.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.caihutong;
				pay_cht.setChecked();
				if (cache != null && cache != pay_cht)
					cache.setUnChecked();
				cache = pay_cht;
			}
		});

		final Button bt_earn = (Button) findViewById(R.id.bt_earn);
		bt_earn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TCLogUtils.showToast(getApplicationContext(),
						"支付方式为：" + payWay.toString());
				if (payWay == PayWay.alipay) {
					TianCi.getInstance().getOrder(roleId, cp_orderId, serverId,
							price, payWay.getPayway(), new RequestCallBack() {

								@Override
								public void onSuccessed(ResponseMsg responseMsg) {
									AlipayEntity entity = (AlipayEntity) responseMsg
											.getBaseOrder();
									PARTNER = entity.pantner;
									SELLER = entity.seller;
									RSA_PRIVATE = entity.rsa_private;
									RSA_PUBLIC = entity.rsa_public;
									notify_url = entity.notify_url;
									orderId = entity.orderId;
									pay(bt_earn);
								}

								@Override
								public void onFailure(String msg) {
									TCLogUtils.toastShort(
											getApplicationContext(), msg);
									Intent data = new Intent();
									data.putExtra("result", msg);
									setResult(
											Config.REQUEST_STATUS_CODE_FAILURE,
											data);
									finish();
								}
							});
				} else if (payWay == PayWay.yinlian) {
					// “00” – 银联正式环境
					// “01” – 银联测试环境，该环境中不发生真实交易

					String serverMode = "01";
//					UPPayAssistEx.startPayByJAR(this, PayActivity.class, null,
//							null, tn, serverMode);

				}
			}
		});

		findViewById(R.id.iv_pay_rechange_notes).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						TianCi.getInstance().getOrderList(
								new RequestCallBack() {

									@Override
									public void onSuccessed(
											ResponseMsg responseMsg) {
										List<BaseOrder> list = responseMsg
												.getOrderList();
										if (list != null && list.size() > 0) {
											Intent intent = new Intent(
													TCPayActivity.this,
													TCOrderActivity.class);
											intent.putExtra("order_list",
													(Serializable) list);
											startActivity(intent);
										}
										Toast.makeText(getApplicationContext(),
												"此账号没有订单信息", 0).show();
									}

									@Override
									public void onFailure(String msg) {
										// Intent data = new Intent();
										// data.putExtra("result", msg);
										// setResult(
										// Config.REQUEST_STATUS_CODE_FAILURE,
										// data);

										Toast.makeText(getApplicationContext(),
												msg, 0).show();
									}
								});

						// startActivity(new Intent(TCPayActivity.this,
						// TCOrderActivity.class));
					}
				});

	}

	/**
	 * 设置所有的radioButton为未选中
	 * 
	 * @param objs
	 */
	private void setAllUnChecked(ImageRadiobutton[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].isChecked())
				objs[i].setUnChecked();
		}
	}

	@Override
	int initView() {
		return R.layout.tcsdk_pay_view;
	}

	@Override
	boolean hasActionBar() {
		return false;
	}

	@Override
	void initData(Bundle saveInstance) {
		Intent intent = getIntent();
		subject = intent.getStringExtra("subject");
		body = intent.getStringExtra("body");
		price = intent.getStringExtra("price");
		roleId = intent.getStringExtra("roleId");
		serverId = intent.getStringExtra("serverId");
		cp_orderId = intent.getStringExtra("cp_orderId");

		tv_game_name.setText("游戏名：" + NormalUtils.getShoriStr(subject));
		tv_pay_amount.setText("支付金额：" + price + "元");
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									// finish();
								}
							}).show();
			return;
		}

		// 订单
		// String orderInfo = getOrderInfo(subject, body, price);
		String orderInfo = OrderUtils.getAlipayOrderInfo(subject, body, price,
				cp_orderId, PARTNER, SELLER, notify_url);
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
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(TCPayActivity.this);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
