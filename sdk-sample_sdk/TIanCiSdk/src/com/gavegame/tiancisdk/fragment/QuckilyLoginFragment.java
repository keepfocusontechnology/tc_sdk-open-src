package com.gavegame.tiancisdk.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class QuckilyLoginFragment extends TCBaseFragment {

	private final String ACCOUNT = "username";
	EditText et_username;
	EditText et_psw;
	private String accountCache;

	@Override
	public void initData(Bundle data) {
		super.initData(data);
		accountCache = (String) data.get("account");
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO 判断最后登陆的账号是否登陆成功，显示最后登陆成功的账号，无成功账号显示为空
		if (!TextUtils.isEmpty(accountCache))
			et_username.setText(accountCache);
		// et_username.setText(psw);
	}

	@Override
	void initID() {
		Button bt_jump = (Button) view.findViewById(R.id.bt_fast_register);
		Button bt_immediately_login = (Button) view
				.findViewById(R.id.bt_immediately_login);
		et_username = (EditText) view.findViewById(R.id.et_username);
		et_psw = (EditText) view.findViewById(R.id.et_psw);
		// 找回密码
		view.findViewById(R.id.tv_psw_retake).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						callback.jumpNextPage(Config.FIND_PSW_FRAGMENT);
					}
				});

		bt_jump.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.jumpNextPage(Config.QUCKILY_REGISTER_FRAGMENT);
			}
		});
		bt_immediately_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 登录
				TianCi.getInstance().login(et_username.getText() + "",
						et_psw.getText() + "", new RequestCallBack() {

							@Override
							public void onSuccessed(int code) {
								TCLogUtils.toastShort(getActivity(),
										"登陆成功! tcsso:"
												+ TianCi.getInstance()
														.getTcsso());
								if(code == 1){
									
								}else{
									callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
								}
							}

							@Override
							public void onFailure(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(),
										msg.getRetMsg());
							}
						});
			}
		});
		view.findViewById(R.id.tv_visitor_one_key_login).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						TianCi.getInstance().autoLogin(new RequestCallBack() {
							@Override
							public void onSuccessed(int code) {
								TCLogUtils.toastShort(getActivity(),
										"游客登陆成功! tcsso:"
												+ TianCi.getInstance()
														.getTcsso());
							}

							@Override
							public void onFailure(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(),
										msg.getRetMsg());
							}
						});
					}
				});
		;
	}

	@Override
	int getLayoutId() {
		return R.layout.quckily_login_fragment;
	}

}
