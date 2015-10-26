package com.gavegame.tiancisdkdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.LoginPageActivity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.paysdk.alipay.AliPayActivity;
import com.gavegame.tiancisdk.paysdk.wxpay.WxPayActivity;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends Activity {

	private Context context;
	private static final int PAY_RESULT = 2233;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		context = getApplicationContext();

		findViewById(R.id.jump_login_page).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(MainActivity.this,
								LoginPageActivity.class), 0);
					}
				});
		;

		findViewById(R.id.bt_auto_login).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method
						TianCi.getInstance().autoLogin("123",
								new RequestCallBack() {

									@Override
									public void onSuccessed() {
										TCLogUtils.toastShort(getApplicationContext(),"自动登陆成功");
									}

									@Override
									public void onFailure(ResponseMsg arg0) {
										// TODO Auto-generated method stub
										TCLogUtils.d(arg0.getRetMsg());
									}
								});
					}
				});

		findViewById(R.id.bt_create_role).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method
						TianCi.getInstance().roleAutoLogin("123",
								new RequestCallBack() {

									@Override
									public void onSuccessed() {
										TCLogUtils.toastShort(getApplicationContext(),"创建角色成功");
									}

									@Override
									public void onFailure(ResponseMsg arg0) {
										// TODO Auto-generated method stub
										TCLogUtils.d(arg0.getRetMsg());
									}
								});
					}
				});

		findViewById(R.id.bt_create_order).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method
						TianCi.getInstance().createOrder("123", "123", "213",
								new RequestCallBack() {

									@Override
									public void onSuccessed() {
										TCLogUtils.toastShort(getApplicationContext(),"创建订单数据上传成功");
									}

									@Override
									public void onFailure(ResponseMsg arg0) {
										// TODO Auto-generated method stub
										TCLogUtils.toastShort(getApplicationContext(),"创建订单数据上传失败,失败原因："+arg0.getRetMsg());
									}
								});
					}
				});
		findViewById(R.id.bt_finish_order).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method
						TianCi.getInstance().finishOrder("123", "123", "213",
								new RequestCallBack() {

									@Override
									public void onSuccessed() {
										TCLogUtils.toastShort(getApplicationContext(),"完成订单数据上传成功");
									}

									@Override
									public void onFailure(ResponseMsg arg0) {
										// TODO Auto-generated method stub
										TCLogUtils.toastShort(getApplicationContext(),"完成订单数据上传失败,失败原因："+arg0.getRetMsg());
									}
								});
					}
				});

		findViewById(R.id.bt_alipay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						AliPayActivity.class);
				intent.putExtra("subject", "商品名称");
				intent.putExtra("subject_desc", "商品详情");
				intent.putExtra("price", "0.01");
				startActivityForResult(intent, 0);
			}
		});

		findViewById(R.id.bt_wxpay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WxPayActivity.class);
//				intent.putExtra("subject", "商品名称");
//				intent.putExtra("subject_desc", "商品详情");
//				intent.putExtra("price", "0.01");
//				startActivityForResult(intent, 0);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
			String tcsso = data.getStringExtra("tcsso");
			TCLogUtils.toastShort(context, "tcsso=" + tcsso);
		} else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
			TCLogUtils.toastShort(context,
					data.getExtras().getSerializable("result").toString());
		} else if (resultCode == PAY_RESULT) {
			int payResultCode = data.getExtras().getInt("pay_resultcode");
			if (payResultCode == 200) {
				Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT)
						.show();
			} else if (payResultCode == 300) {
				Toast.makeText(MainActivity.this, "支付中", Toast.LENGTH_SHORT)
						.show();
			} else if (payResultCode == 400) {
				Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TianCi.init(this);
	}

}
