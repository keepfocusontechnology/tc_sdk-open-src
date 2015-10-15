package com.gavegame.tiancisdkdemo;


import android.app.Application;

import com.gavegame.tiancisdk.TianCiSDK;

public class MyAppliction extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TianCiSDK.init(this);
		TianCiSDK.setDebugModel(true);
	}
}
