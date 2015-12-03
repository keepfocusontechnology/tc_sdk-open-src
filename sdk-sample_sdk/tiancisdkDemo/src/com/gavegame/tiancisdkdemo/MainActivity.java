package com.gavegame.tiancisdkdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.activity.TCLoginActivity;
import com.gavegame.tiancisdk.activity.TCPayActivity;
import com.gavegame.tiancisdk.alipay.AliPayActivity;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends Activity {

	private Context context;

	private static final int PAY_RESULT = 2233;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		TianCiSDK.init(this);
		// true为竖屏，false 为横屏
		TianCiSDK.setScreenIsPortrait(true);
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
						startActivity(intent);
					}
				});

		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TCPayActivity.class);
				intent.putExtra("subject", "商品名称");
				intent.putExtra("subject_desc", "商品详情");
				intent.putExtra("price", "0.01");
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
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TianCi.init(this);
	}
	// if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
	// String tcsso = data.getStringExtra("tcsso");
	// TCLogUtils.toastShort(context, "tcsso=" +tcsso);
	// } else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
	// TCLogUtils.toastShort(context,
	// data.getExtras().getSerializable("result").toString());
	// }else if(resultCode == PAY_RESULT){
	// int payResultCode = data.getExtras().getInt("pay_resultcode");
	// if(payResultCode == 200){
	// Toast.makeText(MainActivity.this, "支付成功",
	// Toast.LENGTH_SHORT).show();
	// }else if(payResultCode == 300){
	// Toast.makeText(MainActivity.this, "支付中",
	// Toast.LENGTH_SHORT).show();
	// }else if(payResultCode == 400){
	// Toast.makeText(MainActivity.this, "支付失败",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// }

}
