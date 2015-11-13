package com.gavegame.tiancisdk.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomerDialog extends AlertDialog{
	private int layoutId;
	private Context context;

	public CustomerDialog(Context context, int layoutId) {
		super(context);
		this.context = context;
		this.layoutId = layoutId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutId);
		View view = LayoutInflater.from(context).inflate(layoutId, null);

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.width = 700;
		params.height = 500;
		getWindow().setAttributes(params);
		setView(view, 0, 0, 0, 0);
//		view.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				CustomerDialog.this.dismiss();
//				return true;
//			}
//		});
	}


}
