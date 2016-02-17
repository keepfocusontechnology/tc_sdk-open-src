package com.gavegame.tiancisdk.activity;

import java.io.Serializable;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.BaseOrder;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.presenter.AliPresenterImpl;
import com.gavegame.tiancisdk.presenter.IPayPresenter;
import com.gavegame.tiancisdk.presenter.WxPresenterImpl;
import com.gavegame.tiancisdk.presenter.YLPresenterImpl;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayView;
import com.gavegame.tiancisdk.widget.ImageRadiobutton;
import com.gavegame.tiancisdk.widget.ImageRadiobutton.RadioButtonCheckedListener;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

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

	// private IWXAPI api;
	private final String app_id = "wx7f69866f179fa23e";

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

				} else if (payWay == PayWay.wechat) {
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
										}else{
											Toast.makeText(getApplicationContext(),
													"此账号没有订单信息", 0).show();
										}
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
		if (str.equalsIgnoreCase("success")) {
			setResult(PAY_SUCCESSED);
			finish();
		} else if (str.equalsIgnoreCase("fail")) {
			setResult(PAY_UNSUCCESSED);
//			finish();
		} else if (str.equalsIgnoreCase("cancel")) {
			setResult(PAY_CANCEL);
//			finish();
		}
	}

	private final int PAY_SUCCESSED = 20000;
	private final int PAY_UNSUCCESSED = 40000;
	private final int PAY_CANCEL = 60000;
	private final int PAY_WAITING = 80000;

	@Override
	public void paySuccessAction() {
		TCLogUtils.e("...支付成功");
		setResult(PAY_SUCCESSED);
		finish();
	}

	@Override
	public void payFailedAction() {
		TCLogUtils.e("...支付失败");
		setResult(PAY_UNSUCCESSED);
//		finish();
	}

	@Override
	public void payWaitAction() {
		TCLogUtils.e("支付结果确认中。。。");
		setResult(PAY_WAITING);
//		finish();
	}

}
