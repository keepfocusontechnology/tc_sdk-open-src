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
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.activity.TCLoginActivity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		TianCiSDK.init(this);
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
	}

	// findViewById(R.id.bt_auto_login).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method
	// TianCi.getInstance().autoLogin(new RequestCallBack() {
	//
	// @Override
	// public void onSuccessed() {
	// Toast.makeText(getApplicationContext(), "200",
	// 0).show();
	// }
	//
	// @Override
	// public void onFailure(ResponseMsg arg0) {
	// // TODO Auto-generated method stub
	// TCLogUtils.d(arg0.getRetMsg());
	// }
	// });
	// }
	// });
	//
	// findViewById(R.id.bt_create_role).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method
	// TianCi.getInstance().roleAutoLogin("123", "150001",
	// new RequestCallBack() {
	//
	// @Override
	// public void onSuccessed() {
	// Toast.makeText(getApplicationContext(),
	// "200", 0).show();
	// }
	//
	// @Override
	// public void onFailure(ResponseMsg arg0) {
	// // TODO Auto-generated method stub
	// TCLogUtils.d(arg0.getRetMsg());
	// }
	// });
	// }
	// });

	// findViewById(R.id.bt_create_order).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method
	// TianCi.getInstance().createOrder("123", "123",
	// "150001", "213", new RequestCallBack() {
	//
	// @Override
	// public void onSuccessed() {
	// Toast.makeText(getApplicationContext(),
	// "200", 0).show();
	// }
	// @Override
	// public void onUnBindSuccessed() {
	// // TODO Auto-generated method stub
	//
	// }
	// @Override
	// public void onFailure(ResponseMsg arg0) {
	// // TODO Auto-generated method stub
	// TCLogUtils.d(arg0.getRetMsg());
	// }
	// });
	// }
	// });
	// findViewById(R.id.bt_finish_order).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method
	// TianCi.getInstance().finishOrder("123", "123",
	// "150001", "213", new RequestCallBack() {
	//
	// @Override
	// public void onSuccessed() {
	// Toast.makeText(getApplicationContext(),
	// "200", 0).show();
	// }
	//
	// @Override
	// public void onUnBindSuccessed() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onFailure(ResponseMsg arg0) {
	// // TODO Auto-generated method stub
	// TCLogUtils.d(arg0.getRetMsg());
	// }
	// });
	// }
	// });
	// }

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
}
