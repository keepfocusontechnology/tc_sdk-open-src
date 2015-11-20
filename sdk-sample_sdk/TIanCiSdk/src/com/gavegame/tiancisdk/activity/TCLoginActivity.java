package com.gavegame.tiancisdk.activity;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.FragmentChangedCallback;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.fragment.FindPswFragment;
import com.gavegame.tiancisdk.fragment.FinishCodeCheckFragment;
import com.gavegame.tiancisdk.fragment.MakeNewPswFragment;
import com.gavegame.tiancisdk.fragment.MobileBindFragment;
import com.gavegame.tiancisdk.fragment.PhoneBindFragment;
import com.gavegame.tiancisdk.fragment.PhoneNumRegisterFragment;
import com.gavegame.tiancisdk.fragment.PswRetakeFragment;
import com.gavegame.tiancisdk.fragment.QuckilyLoginFragment;
import com.gavegame.tiancisdk.fragment.QuckilyRegisterFragment;
import com.gavegame.tiancisdk.fragment.TCBaseFragment;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class TCLoginActivity extends BaseActivity implements
		FragmentChangedCallback {

	private FragmentManager manager;
	private FragmentTransaction transaction;
	private TCBaseFragment contentFragment;
	private String tag;

	private TextView tv_title;
	private ImageView iv_back;
	private View title;

	private List<String> titleStack;
	
	private View mainView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mainView = (View) LayoutInflater.from(this).inflate(R.layout.action_bar_activity, null);
		setContentView(mainView);
		if (VERSION.SDK_INT >= 11)
			TCLoginActivity.this.setFinishOnTouchOutside(false);
		manager = getSupportFragmentManager();
		titleStack = new ArrayList<String>();
		initTitle();
		switchFragment(0, savedInstanceState);
	}

	private boolean isFristLogin() {
		String loginModel = TianCi.getInstance().getUserAccount("login_model");
		if (TextUtils.isEmpty(loginModel)) {
			return true;
		} else {
			if (loginModel.equals("visitor")) {
				TianCi.getInstance().autoLogin(new RequestCallBack() {

					@Override
					public void onSuccessed(int userBindCode) {
						TCLogUtils.toastShort(getApplicationContext(), "游客自动登陆成功");
						finish();
					}

					@Override
					public void onFailure(ResponseMsg msg) {
						TCLogUtils.toastShort(getApplicationContext(), msg.getRetMsg());
					}
				});
			} else if (loginModel.equals("account")) {
				String username = TianCi.getInstance().getUserAccount(
						"user_account");
				String psw = TianCi.getInstance().getUserAccount(
						"user_password");
				if(TextUtils.isEmpty(username) || TextUtils.isEmpty(psw))
					return true;
				TianCi.getInstance().login(username, psw, new RequestCallBack() {

					@Override
					public void onSuccessed(int userBindCode) {
						finish();
						TCLogUtils.toastShort(getApplicationContext(), "自动登陆成功");
					}

					@Override
					public void onFailure(ResponseMsg msg) {

					}
				});
			}
		}
		return false;
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

	@Override
	protected void onResume() {
		super.onResume();
		TianCi.init(this);
		if (TianCiSDK.getScreenState()) {
			// 竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setWindowParams(0.9);
		} else {
			// 横屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setWindowParams(0.6);
		}
		if(!isFristLogin()){
			mainView.setVisibility(View.GONE);
		}
	}

	private void initTitle() {
		title = (View) findViewById(R.id.title);
		tv_title = (TextView) findViewById(R.id.tv_actionbar_title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}

	private void switchFragment(int fragmentID, Bundle bundle) {
		switchFragment(fragmentID, bundle, false);
	}

	private void switchFragment(int fragmentID, Bundle bundle,
			boolean isHaveAnimation) {

		switch (fragmentID) {
		case Config.QUCKILY_LOGIN_FRAGMENT:
			contentFragment = new QuckilyLoginFragment();
			tag = "QuckilyLoginFragment";
			break;
		case Config.QUCKILY_REGISTER_FRAGMENT:
			contentFragment = new QuckilyRegisterFragment();
			tag = "QuckilyRegisterFragment";
			break;
		case Config.PHONE_NUM_REGISTER_FRAGMENT:
			contentFragment = new PhoneNumRegisterFragment();
			tag = "PhoneNumRegisterFragment";
			break;
		case Config.PSW_RETAKE_FRAGMENT:
			contentFragment = new PswRetakeFragment();
			tag = "PswRetakeFragment";
			break;
		case Config.MAKE_NEWPSW_FRAGMENT:
			contentFragment = new MakeNewPswFragment();
			tag = "MakeNewPswFragment";
			break;
		case Config.FIND_PSW_FRAGMENT:
			contentFragment = new FindPswFragment();
			tag = "FindPswFragment";
			break;
		case Config.FINISH_CODE_CHECK_FRAGMENT:
			contentFragment = new FinishCodeCheckFragment();
			tag = "FinishCodeCheckFragment";
			break;
		case Config.MOBILE_BIND_FRAGMENT:
			contentFragment = new MobileBindFragment();
			tag = "MobileBindFragment";
			break;
		case Config.DIALOG_BIND_FRAGMENT:
			contentFragment = new PhoneBindFragment();
			tag = "PhoneBindFragment";
			break;
		default:
			break;
		}
		if (bundle != null) {
			contentFragment.initData(bundle);
		}
		if (isHaveAnimation) {
			showFragment(fragmentID, contentFragment, isHaveAnimation);
		} else {
			showFragment(fragmentID, contentFragment);
		}

	}

	private void showFragment(int fragmentID, TCBaseFragment fragment) {
		showFragment(fragmentID, fragment, false);
	}

	private void showFragment(int fragmentID, TCBaseFragment fragment,
			boolean isHaveAnimation) {
		transaction = manager.beginTransaction();
		if (isHaveAnimation) {
			transaction.setCustomAnimations(R.anim.left_silde,
					R.anim.right_silde);
		}
		transaction.replace(R.id.fl_content, fragment, tag);
		if (!tag.equals("QuckilyLoginFragment")) {
			transaction.addToBackStack(tag);
		}
		String titleStr = null;
		try {
			titleStr = getResources().getStringArray(R.array.fragment_title)[fragmentID];
		} catch (IndexOutOfBoundsException e) {
			titleStr = null;
		}
		if (titleStr == null) {
			title.setVisibility(View.GONE);
			titleStack.add("");
		} else {
			title.setVisibility(View.VISIBLE);
			tv_title.setText(titleStr);
			titleStack.add(titleStr);
		}
		transaction.commit();
	}

	@Override
	public void jumpNextPage(int targetFragmentID) {
		switchFragment(targetFragmentID, null, false);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& isOutOfBounds(this, event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(Activity context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context)
				.getScaledWindowTouchSlop();
		final View decorView = context.getWindow().getDecorView();
		return (x < -slop) || (y < -slop)
				|| (x > (decorView.getWidth() + slop))
				|| (y > (decorView.getHeight() + slop));
	}

	private void goBack() {
		if (manager.getBackStackEntryCount() == 0) {
			finish();
			titleStack.clear();
			titleStack = null;
		} else {
			// manager.popBackStack();
			removeFragment(R.anim.right_silde);
			// Look ! ! : There is a illegalStateException
			// if (titleStack.iterator().hasNext()) {
			// titleStack.iterator().next();
			// titleStack.iterator().remove();
			// }
			if (titleStack.size() > 1) {
				titleStack.remove(titleStack.size() - 1);
				if (titleStack.get(titleStack.size() - 1).equals("")) {
					title.setVisibility(View.GONE);
				} else {
					title.setVisibility(View.VISIBLE);
					tv_title.setText(titleStack.get(titleStack.size() - 1));
				}
			}
		}
	}

	public void removeFragment(int outAnimId) {
		// if (contentFragment == null) return;
		// if (outAnimId != -1) {
		// transaction.setCustomAnimations(0, outAnimId);
		// }
		// transaction.remove(contentFragment);
		// transaction.commit();
		manager.popBackStackImmediate();
		// TCLogUtils.e("manager.getBackStackEntryCount() = "+manager.getBackStackEntryCount()+"");
	}

	@Override
	public void back() {
		goBack();
	}

	@Override
	public void jumpNextPage(int fragmentID, Bundle bundle) {
		// 首先弹出栈中所有fragment
		// manager.popBackStackImmediate(null, 1);
		// titleStack.clear();
		switchFragment(fragmentID, bundle, false);
	}

}
