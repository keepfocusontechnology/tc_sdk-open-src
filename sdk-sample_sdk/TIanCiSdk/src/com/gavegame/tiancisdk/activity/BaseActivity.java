package com.gavegame.tiancisdk.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Tianci on 15/9/28.
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity {

	// private String TAG = getTag();
	// protected ViewGroup contentView;
	// private ImageView iv_back;
	// private TextView tv_title;
	// public Context context;
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// requestWindowFeature(Window.FEATURE_NO_TITLE);
	// setContentView(R.layout.action_bar_activity);
	// initContentView();
	// context = getApplicationContext();
	// }
	//
	// public abstract String getTag();
	//
	// protected abstract int getLayoutId();
	//
	// private void initContentView() {
	// contentView = (ViewGroup) findViewById(R.id.fl_content);
	// LayoutInflater inflater = LayoutInflater.from(this);
	// inflater.inflate(getLayoutId(), contentView, true);
	// iv_back = (ImageView) findViewById(R.id.iv_back);
	// tv_title = (TextView) findViewById(R.id.tv_actionbar_title);
	// }
	//
	//
	// /**
	// * 模拟返回键
	// */
	// private void goBack() {
	// try {
	// Runtime runtime = Runtime.getRuntime();
	// runtime.exec("" + KeyEvent.KEYCODE_BACK);
	// } catch (IOException e) {
	// TCLogUtils.e(TAG, e.toString());
	// }
	// }
}
