package com.gavegame.tiancisdk.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

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
}
