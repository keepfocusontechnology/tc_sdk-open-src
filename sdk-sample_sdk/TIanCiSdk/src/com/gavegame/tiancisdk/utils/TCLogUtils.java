package com.gavegame.tiancisdk.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Tianci on 15/10/10.
 */
public class TCLogUtils {

	private TCLogUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "TianCiSdk";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.v(tag, msg);
	}

	public static void showToast(Context context, String msg) {
		if (isDebug)
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 正式吐司，恒定显示
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShort(Context context, String msg) {
		if (isDebug) {
			Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	/**
	 * 正式吐司，恒定显示
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastLong(Context context, String msg) {
		if (isDebug) {
			Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
}
