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
import com.gavegame.tiancisdk.presenter.AliPresenterImpl;
import com.gavegame.tiancisdk.presenter.IPayPresenter;
import com.gavegame.tiancisdk.presenter.WxPresenterImpl;
import com.gavegame.tiancisdk.presenter.YLPresenterImpl;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.OrderUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayView;
import com.gavegame.tiancisdk.widget.ImageRadiobutton;
import com.gavegame.tiancisdk.widget.ImageRadiobutton.RadioButtonCheckedListener;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

public class TCPayActivity extends BaseActivity implements IPayView {

	private final String TAG = "TCPayActivity";
	private String subject;
	private String body;
	private String price;

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

	private static final int SDK_PAY_FLAG = 1;

	private static final int PAY_RESULT = 2233;

	private TextView tv_game_name;
	private TextView tv_pay_amount;

	/**
	 * 退出支付弹窗
	 */
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

	void setCacheView(ImageRadiobutton v) {
		v.setChecked();
		if (cache != null && cache != v)
			cache.setUnChecked();
		cache = v;
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
		pay_alipay.setChecked();
		cache = pay_alipay;
		pay_alipay.setCheckListener(new RadioButtonCheckedListener() {
			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.alipay;
				setCacheView(pay_alipay);
			}
		});

		pay_wechat.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.wechat;
				setCacheView(pay_wechat);
			}
		});

		pay_bank.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.yinlian;
				setCacheView(pay_bank);
			}
		});

		pay_cht.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.caihutong;
				setCacheView(pay_cht);
			}
		});

		final Button bt_earn = (Button) findViewById(R.id.bt_earn);
		bt_earn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TCLogUtils.showToast(getApplicationContext(),
						"支付方式为：" + payWay.toString());
				if (payWay == PayWay.alipay) {
					payPresenter = new AliPresenterImpl(TCPayActivity.this);
					payPresenter.pay();
				} else if (payWay == PayWay.yinlian) {
					payPresenter = new YLPresenterImpl(TCPayActivity.this);
					payPresenter.pay();
					
//					TianCi.getInstance().getOrder(roleId, cp_orderId, serverId,
//							price, payWay.getPayway(), new RequestCallBack() {
//
//								@Override
//								public void onSuccessed(ResponseMsg responseMsg) {
//									// “00” – 银联正式环境
//									// “01” – 银联测试环境，该环境中不发生真实交易
//									String serverMode = "01";
//									TCLogUtils.e(TAG,
//											"tn = " + responseMsg.getRetMsg());
//									UPPayAssistEx.startPay(TCPayActivity.this,
//											null, null,
//											responseMsg.getRetMsg(), serverMode);
//								}
//
//								@Override
//								public void onFailure(String msg) {
//									TCLogUtils.e(TAG, msg);
//								}
//							});

					// TianCi.getInstance().testGetTn(new RequestCallBack() {
					//
					// @Override
					// public void onSuccessed(ResponseMsg responseMsg) {
					// TCLogUtils.e(TAG, responseMsg.toString());
					// }
					//
					// @Override
					// public void onFailure(String msg) {
					// // “00” – 银联正式环境
					// // “01” – 银联测试环境，该环境中不发生真实交易
					// String serverMode = "01";
					// UPPayAssistEx.startPay(TCPayActivity.this, null, null,
					// msg, serverMode);
					// }
					// });

				} else if (payWay == PayWay.wechat) {
//					final IWXAPI msgApi = WXAPIFactory.createWXAPI(
//							getApplicationContext(), null);
//					// 将该app注册到微信
//					msgApi.registerApp("wx7f69866f179fa23e");
//
//					PayReq request = new PayReq();
//					request.appId = "wxd930ea5d5a258f4f";
//					request.partnerId = "1900000109";
//					request.prepayId = "1101000000140415649af9fc314aa427";
//					request.packageValue = "Sign=WXPay";
//					request.nonceStr = "1101000000140429eb40476f8896f4c9";
//					request.timeStamp = "1398746574";
//					request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//					msgApi.sendReq(request);
					payPresenter = new WxPresenterImpl(TCPayActivity.this);
					payPresenter.pay();
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

	@Override
	int initView() {
		return R.layout.tcsdk_pay_view;
	}

	@Override
	boolean hasActionBar() {
		return false;
	}

	private IPayPresenter payPresenter;

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}

		String str = data.getExtras().getString("pay_result");
		TCLogUtils.e(TAG, "银联返回的结果" + str);
		// if (str.equalsIgnoreCase(R_SUCCESS)) {
		// // 支付成功后，extra中如果存在result_data，取出校验
		// // result_data结构见c）result_data参数说明
		// if (data.hasExtra("result_data")) {
		// String sign = data.getExtras().getString("result_data");
		// // 验签证书同后台验签证书
		// // 此处的verify，商户需送去商户后台做验签
		// if (verify(sign)) {
		// // 验证通过后，显示支付结果
		// showResultDialog(" 支付成功！ ");
		// } else {
		// // 验证不通过后的处理
		// // 建议通过商户后台查询支付结果
		// }
		// } else {
		// // 未收到签名信息
		// // 建议通过商户后台查询支付结果
		// }
		// } else if (str.equalsIgnoreCase(R_FAIL)) {
		// showResultDialog(" 支付失败！ ");
		// } else if (str.equalsIgnoreCase(R_CANCEL)) {
		// showResultDialog(" 你已取消了本次订单的支付！ ");
		// }
	}

	@Override
	public void paySuccessAction() {
		TCLogUtils.e("...支付成功");
	}

	@Override
	public void payFailedAction() {
		TCLogUtils.e("...支付失败");
	}

	@Override
	public void payWaitAction() {
		TCLogUtils.e("支付结果确认中。。。");
	}

}
