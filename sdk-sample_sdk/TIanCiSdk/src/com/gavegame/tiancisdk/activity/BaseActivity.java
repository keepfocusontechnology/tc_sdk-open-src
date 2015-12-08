package com.gavegame.tiancisdk.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.utils.TCLogUtils;

/**
 * Created by Tianci on 15/9/28.
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity {

	private final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		if (!hasActionBar()) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		TCLogUtils.e(TAG, TianCiSDK.getScreenState() + "");
		if (TianCiSDK.getScreenState()) {
			// 竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setWindowParams(0.9);
		} else {
			// 横屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setWindowParams(1.2);
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
		TianCi.init(this);

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
		TCLogUtils.e(TAG, "屏幕宽度为："+d.getWidth()+"");
		// params.alpha = 1.0f; //设置本身透明度
		// params.dimAmount = 0.0f; //设置黑暗度
		getWindow().setAttributes(params);

		// WindowManager m = getWindowManager();
		// Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		// LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
		// p.height = (int) (d.getHeight() * 1.0); // 高度设置为屏幕的1.0
		// p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.8
		// p.alpha = 1.0f; // 设置本身透明度
		// p.dimAmount = 0.0f; // 设置黑暗度
		// getWindow().setAttributes(p); // 设置生效
		// getWindow().setGravity(Gravity.CENTER); // 设置靠右对齐
	}
}
