package com.gavegame.tiancisdkdemo;

import android.app.Application;

import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MyAppliction extends Application {
	private final String TAG = "MyAppliction";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		TianCiSDK.init(this);
		TianCiSDK.setDebugModel(true);
		// true为竖屏，false 为横屏
		TianCiSDK.setScreenIsPortrait(false);
		TCLogUtils.e(TAG, TianCiSDK.getScreenState() + "");
	}
}
