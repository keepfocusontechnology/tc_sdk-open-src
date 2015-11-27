package com.gavegame.tiancisdk.activity;

import com.gavegame.tiancisdk.TianCiSDK;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * Created by Tianci on 15/9/28.
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		if(!hasActionBar()){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		setContentView(initView());
		initId();
		initData(saveInstance);
	}
	
	abstract void initId();
	abstract void initData(Bundle saveInstance);
	abstract int initView();
	
	abstract boolean hasActionBar();
	
	@Override
	protected void onResume() {
		super.onResume();
		if (TianCiSDK.getScreenState()) {
			// 竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setWindowParams(0.9);
		} else {
			// 横屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setWindowParams(0.6);
		}
	}
	
	/**
	 * 设置窗口大小
	 */
	private void setWindowParams(double width) {
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams params = getWindow().getAttributes(); // 获取对话框当前的参数值
		// params.height = (int) (d.getHeight() * height); //高度设置为屏幕的1.0
		params.width = (int) (d.getWidth() * width); // 宽度设置为屏幕的0.8
		// params.alpha = 1.0f; //设置本身透明度
		// params.dimAmount = 0.0f; //设置黑暗度
		getWindow().setAttributes(params);
	}
}
