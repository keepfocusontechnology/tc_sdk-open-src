package com.gavegame.tiancisdk.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.R.id;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.widget.CustomerDialog;
/**
 * 登陆主要界面
 * @author Tianci
 *
 */
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

	@SuppressLint("InflateParams")
	@Override
	void initID() {
		Button bt_jump = (Button) view.findViewById(R.id.bt_fast_register);
		Button bt_immediately_login = (Button) view
				.findViewById(R.id.bt_immediately_login);
		Button bt_visitor_login = (Button) view
				.findViewById(R.id.bt_visitor_login);
		// 游客登陆
		bt_visitor_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TianCi.getInstance().autoLogin(new RequestCallBack() {
					@Override
					public void onSuccessed(int code) {
						//记录为游客登录
						TianCi.getInstance().saveLoginModelIsVisitor();
						// 未绑定
						//第一次进入提示绑定
						if (TianCi.getInstance().getLastBindDialogPopTime() == 0) {
							callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
							TianCi.getInstance().saveCurrentTime();
						} else {
							//间隔上次弹出提示框24小时候再次弹出
							if (TianCi.getInstance().isPopBindPage()) {
								callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
								TianCi.getInstance().saveCurrentTime();
							} else {
								TCLogUtils.toastShort(getActivity(),
										"游客登陆成功! tcsso:"
												+ TianCi.getInstance()
														.getTcsso());
								getActivity().finish();
							}
						}

					}

					@Override
					public void onFailure(ResponseMsg msg) {
						TCLogUtils.toastShort(getActivity(), msg.getRetMsg());
					}
				});

			}
		});
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
				//记录为账户登录
				
				// TODO 登录
				TianCi.getInstance().login(et_username.getText() + "",
						et_psw.getText() + "", new RequestCallBack() {

							@Override
							public void onSuccessed(int code) {
								TianCi.getInstance().saveLoginModelIsAccount();
								if (code == 1) {// 账号已绑定
									TCLogUtils.toastShort(getActivity(),
											"登陆成功! tcsso:"
													+ TianCi.getInstance()
															.getTcsso());
									TianCi.getInstance().saveAccountAndPsw(
											et_username.getText() + "",
											et_psw.getText() + "");
									getActivity().finish();
								} else {
									// 未绑定
									if (TianCi.getInstance()
											.getLastBindDialogPopTime() == 0) {
										callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
										TianCi.getInstance().saveCurrentTime();
									} else {
										if (TianCi.getInstance()
												.isPopBindPage()) {
											callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
											TianCi.getInstance()
													.saveCurrentTime();
										} else {
											TCLogUtils
													.toastShort(
															getActivity(),
															"登陆成功! tcsso:"
																	+ TianCi.getInstance()
																			.getTcsso());
											TianCi.getInstance()
													.saveAccountAndPsw(
															et_username
																	.getText()
																	+ "",
															et_psw.getText()
																	+ "");
											getActivity().finish();
										}
									}
								}
							}

							@Override
							public void onFailure(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(),
										msg.getRetMsg());
//								TCSdkToast.show(msg.getRetMsg(), getActivity());
							}
						});
			}
		});
		view.findViewById(R.id.tv_customer_center).setOnClickListener(
				new OnClickListener() {

					@SuppressWarnings("deprecation")
					@SuppressLint("ResourceAsColor")
					@Override
					public void onClick(View v) {
						View dialogView = LayoutInflater.from(getActivity())
								.inflate(R.layout.tcsdk_customer_service, null);
						dialogView.findViewById(R.id.tv_customer_title)
								.setVisibility(View.GONE);
						TextView tv_mail = (TextView) dialogView
								.findViewById(R.id.tv_mail);
						TextView tv_phone = (TextView) dialogView
								.findViewById(R.id.tv_phone);
						TextView tv_qq = (TextView) dialogView
								.findViewById(R.id.tv_qq);
						tv_mail.setTextColor(getResources().getColor(
								R.color.tcsdk_button_immediately_login));
						tv_phone.setTextColor(getResources().getColor(
								R.color.tcsdk_button_immediately_login));
						tv_qq.setTextColor(getResources().getColor(
								R.color.tcsdk_button_immediately_login));
						CustomerDialog dialog = new CustomerDialog(
								getActivity(), dialogView, 500, 300);
						dialog.show();
					}
				});
		;
	}

	@Override
	int getLayoutId() {
		return R.layout.quckily_login_fragment;
	}

}