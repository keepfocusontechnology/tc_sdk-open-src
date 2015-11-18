package com.gavegame.tiancisdk.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class CustomerDialog extends AlertDialog {
	private int layoutId;
	private Context context;
	private View view;
	private int width;
	private int height;

	public CustomerDialog(Context context, int layoutId) {
		super(context);
		this.context = context;
		this.layoutId = layoutId;
	}

	public CustomerDialog(Context context, View view) {
		super(context);
		this.context = context;
		this.view = view;
	}

	public CustomerDialog(Context context, View view, int width, int height) {
		super(context);
		this.context = context;
		this.view = view;
		this.width = width;
		this.height = height;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (view == null) {
			setContentView(layoutId);
			view = LayoutInflater.from(context).inflate(layoutId, null);
		}
		setContentView(view);
		WindowManager.LayoutParams params = this.getWindow()
				.getAttributes();
		if (width == 0 && height == 0) {
			params.width = 700;
			params.height = 500;
		}else{
			params.width = width;
			params.height = height;
		}
		
		getWindow().setAttributes(params);
		setView(view, 0, 0, 0, 0);
		// view.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// CustomerDialog.this.dismiss();
		// return true;
		// }
		// });
	}
}
