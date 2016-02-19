package com.gavegame.tiancisdk.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
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
import com.gavegame.tiancisdk.fragment.VisitorUpdateFragment;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class TCLoginActivity extends BaseActivity implements
		FragmentChangedCallback {

	private final String TAG = "TCLoginActivity";

	private FragmentManager manager;
	private FragmentTransaction transaction;
	private TCBaseFragment contentFragment;
	private String tag;

	private TextView tv_title;
	private ImageView iv_back;
	private View title;

	private String loginModel;

	private List<String> titleStack;

	private View mainView;

	@SuppressLint("NewApi")
	private boolean isFristLogin() {
		final Intent data = new Intent();
		if (TextUtils.isEmpty(loginModel)) {
			return true;
		} else {
			if (loginModel.equals("visitor")) {
				TianCi.getInstance().autoLogin(new RequestCallBack() {

					@Override
					public void onSuccessed(ResponseMsg responseMsg) {
						TCLogUtils.toastShort(getApplicationContext(),
								"游客自动登陆成功");
						data.putExtra("tcsso", TianCi.getInstance().getTcsso());
						setResult(Config.REQUEST_STATUS_CODE_SUC, data);
					}

					@Override
					public void onFailure(String msg) {
						TCLogUtils.toastShort(getApplicationContext(), msg);
						data.putExtra("result", msg);
						setResult(Config.REQUEST_STATUS_CODE_FAILURE, data);
					}
				});
			} else if (loginModel.equals("account")) {
				String username = TianCi.getInstance().getUserAccount(
						"user_account");
				String psw = TianCi.getInstance().getUserAccount(
						"user_password");
				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(psw))
					return true;
				TianCi.getInstance().login(username, psw,
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg responseMsg) {
								data.putExtra("tcsso", TianCi.getInstance()
										.getTcsso());
								setResult(Config.REQUEST_STATUS_CODE_SUC, data);
								finish();
								TCLogUtils.toastShort(getApplicationContext(),
										"自动登陆成功");
							}

							@Override
							public void onFailure(String msg) {
								data.putExtra("result", msg);
								setResult(Config.REQUEST_STATUS_CODE_FAILURE,
										data);
								finish();
							}
						});
			}
		}
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		// TianCi.init(this);
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
		switchFragment(fragmentID, bundle, true);
	}

	private String titleStr;

	private void switchFragment(int fragmentID, Bundle bundle,
			boolean isHaveAnimation) {
		titleStr = null;
		switch (fragmentID) {
		case Config.QUCKILY_LOGIN_FRAGMENT:
			contentFragment = new QuckilyLoginFragment();
			tag = "QuckilyLoginFragment";
			titleStr = "账号登陆";
			break;
		case Config.QUCKILY_REGISTER_FRAGMENT:
			contentFragment = new QuckilyRegisterFragment();
			tag = "QuckilyRegisterFragment";
			titleStr = "快速账号注册";
			break;
		case Config.PHONE_NUM_REGISTER_FRAGMENT:
			contentFragment = new PhoneNumRegisterFragment();
			tag = "PhoneNumRegisterFragment";
			titleStr = "手机注册";
			break;
		case Config.PSW_RETAKE_FRAGMENT:
			contentFragment = new PswRetakeFragment();
			tag = "PswRetakeFragment";
			titleStr = "修改密码";
			break;
		case Config.MAKE_NEWPSW_FRAGMENT:
			contentFragment = new MakeNewPswFragment();
			tag = "MakeNewPswFragment";
			titleStr = "设置新密码";
			break;
		case Config.FIND_PSW_FRAGMENT:
			contentFragment = new FindPswFragment();
			tag = "FindPswFragment";
			titleStr = "找回密码";
			break;
		case Config.FINISH_CODE_CHECK_FRAGMENT:
			contentFragment = new FinishCodeCheckFragment();
			tag = "FinishCodeCheckFragment";
			titleStr = "找回密码";
			break;
		case Config.MOBILE_BIND_FRAGMENT:
			contentFragment = new MobileBindFragment();
			tag = "MobileBindFragment";
			titleStr = "账号绑定";
			break;
		case Config.DIALOG_BIND_FRAGMENT:
			contentFragment = new PhoneBindFragment();
			tag = "PhoneBindFragment";
			break;
		case Config.VIESITOR_UPDATE_FRAGMENT:
			contentFragment = new VisitorUpdateFragment();
			tag = "VisitorUpdateFragment";
			titleStr = "游客账号升级";
		default:
			break;
		}
		if (bundle != null) {
			contentFragment.initData(bundle);
		}
//		if (isHaveAnimation) {
			showFragment(fragmentID, contentFragment, isHaveAnimation);
//		} else {
//			showFragment(fragmentID, contentFragment);
//		}

	}

//	private void showFragment(int fragmentID, TCBaseFragment fragment) {
//		showFragment(fragmentID, fragment, false);
//	}

	private void showFragment(int fragmentID, TCBaseFragment fragment,
			boolean isHaveAnimation) {
		transaction = manager.beginTransaction();
			if (isHaveAnimation) {
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			
//			transaction.setCustomAnimations(R.anim.left_silde,
//					R.anim.right_silde);
		}
		transaction.replace(R.id.fl_content, fragment, tag);
		if (!tag.equals("QuckilyLoginFragment")) {
			transaction.addToBackStack(tag);
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
		switchFragment(targetFragmentID, null, true);
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
		switchFragment(fragmentID, bundle, true);
	}

	@Override
	void initId() {

	}

	@SuppressLint("NewApi")
	@Override
	void initData(Bundle savedInstanceState) {
		loginModel = TianCi.getInstance().getUserAccount("login_model");
		mainView = (View) LayoutInflater.from(this).inflate(
				R.layout.action_bar_activity, null);
		setContentView(mainView);
		if (VERSION.SDK_INT >= 11)
			TCLoginActivity.this.setFinishOnTouchOutside(false);

		manager = getSupportFragmentManager();
		titleStack = new ArrayList<String>();
		initTitle();
		// 点击切换账号会恒定显示登录界面
		String userAction = getIntent().getAction();
		// 如果是正常登陆，则判断是否为首次登陆
		if (TextUtils.isEmpty(userAction)) {
			if (!isFristLogin()) {
				mainView.setVisibility(View.GONE);
				finish();
			} else {
				switchFragment(0, null);
			}
			// 切换账号，如果最近一次登陆为普通登陆，则记录账户信息
		} else {
			if (userAction.equals("switch_account")
					&& loginModel.equals("account")) {
				String username = TianCi.getInstance().getUserAccount(
						"user_account");
				String psw = TianCi.getInstance().getUserAccount(
						"user_password");

				Bundle bundle = new Bundle();
				bundle.putString("user_account", username);
				bundle.putString("user_password", psw);
				switchFragment(0, bundle);
			} else {
				switchFragment(0, null);
			}
		}
		// switchFragment(0, savedInstanceState);
	}

	@Override
	int initView() {
		return R.layout.action_bar_activity;
	}

	@Override
	boolean hasActionBar() {
		return false;
	}

}
