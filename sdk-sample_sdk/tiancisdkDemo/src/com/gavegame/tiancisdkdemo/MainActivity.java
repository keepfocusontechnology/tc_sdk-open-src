package com.gavegame.tiancisdkdemo;

import java.io.File;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.activity.TCLoginActivity;
import com.gavegame.tiancisdk.activity.TCPayActivity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// true为竖屏，false 为横屏
			TianCiSDK.setScreenIsPortrait(true);
		} else {
			// true为竖屏，false 为横屏
			TianCiSDK.setScreenIsPortrait(false);
		}
		setContentView(R.layout.activity_main);
		findViewById(R.id.jump_login_page).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(MainActivity.this,
								TCLoginActivity.class), 0);
					}
				});
		findViewById(R.id.switch_account).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								TCLoginActivity.class);
						intent.setAction("switch_account");
						startActivityForResult(intent, 0);
					}
				});

		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TCPayActivity.class);
				// 全部参数均为String
				intent.putExtra("subject", "支付宝测试名称");
				intent.putExtra("body", "测试：描述信息");
				intent.putExtra("price", "0.01");
				intent.putExtra("roleId", "123");
				intent.putExtra("serverId", "152001");
				//此处请填入cp自己的订单号
				String order_id = new Random().nextInt(10000) + 1 + "";
				intent.putExtra("cp_orderId", order_id);
				startActivityForResult(intent, 0);
			}
		});

		findViewById(R.id.login_role).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 第一个参数是角色id 第二个参数是角色所在服务器id
				TianCi.getInstance().roleAutoLogin("123", "123",
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg responseMsg) {
								Toast.makeText(getApplicationContext(),
										"角色登录成功", 0).show();
								// TCLogUtils.showToast(getApplicationContext(),
								// "角色登陆成功");
							}

							@Override
							public void onFailure(String msg) {
								Toast.makeText(getApplicationContext(), msg, 0)
										.show();
								// TCLogUtils.showToast(getApplicationContext(),
								// msg);
							}
						});
			}
		});

	}

	private final String TAG = "MainActivity";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TCLogUtils.e(TAG, "requestCode:" + requestCode + "/nresultCode:"
		// + resultCode);
		// if (data != null) {
		// TCLogUtils.e(TAG, "intent:" + data);
		//
		// }
		//
		if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
			String tcsso = data.getStringExtra("tcsso");
			Toast.makeText(this, tcsso, 0).show();
		} else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
			Toast.makeText(this,
					data.getExtras().getSerializable("result").toString(), 0)
					.show();
		} else if (resultCode == Config.BIND_SUC) {
			Toast.makeText(this,
					data.getExtras().getSerializable("result").toString(), 0)
					.show();
		} else if (resultCode == 20000) {
			// 支付成功
		} else if (resultCode == 40000) {
			// 支付失败

		} else if (resultCode == 60000) {
			// 支付取消
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TianCi.init(this);
	}
}
