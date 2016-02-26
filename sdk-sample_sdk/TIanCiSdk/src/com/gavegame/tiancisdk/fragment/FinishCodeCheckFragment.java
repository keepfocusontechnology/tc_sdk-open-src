package com.gavegame.tiancisdk.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.utils.TimerCount;

/**
 * 已经用手机号来找回密码，直接输入验证码的页面
 * 
 * @author Tianci
 *
 */
@SuppressLint("ShowToast")
public class FinishCodeCheckFragment extends TCBaseFragment {

	private EditText et_check_captcha;
	private Button bt_confirm_check;
	private Button bt_send_check_code;
	private String mobileNum;

	@Override
	public void initData(Bundle data) {
		// TODO Auto-generated method stub
		super.initData(data);
		mobileNum = data.getString("account");
	}

	@Override
	void initID() {
		et_check_captcha = (EditText) view
				.findViewById(R.id.et_finish_check_captcha);
		et_check_captcha.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		bt_send_check_code = (Button) view.findViewById(R.id.bt_send_captcha);
		bt_confirm_check = (Button) view.findViewById(R.id.bt_finish_check);

		bt_confirm_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 验证用户 是否绑定二维码

				if (TextUtils.isEmpty(et_check_captcha.getText())
						&& TextUtils.isEmpty(mobileNum)) {
					// TODO 手机号或二维码为空
				} else {
					// TODO 忘记密码,对应账号与二维码是否存在
					TianCi.getInstance().forgetPsw(mobileNum,
							et_check_captcha.getText() + "",
							new RequestCallBack() {

								@Override
								public void onSuccessed(ResponseMsg msg) {
									TianCi.getInstance().forgetPsw(mobileNum,
											et_check_captcha.getText() + "",
											new RequestCallBack() {

												@Override
												public void onSuccessed(
														ResponseMsg msg) {
													Bundle bundle = new Bundle();
													bundle.putString("account",
															mobileNum);
													bundle.putString(
															"captcha",
															et_check_captcha
																	.getText()
																	+ "");
													callback.jumpNextPage(
															Config.MAKE_NEWPSW_FRAGMENT,
															bundle);
												}

												@Override
												public void onFailure(String msg) {
													Toast.makeText(
															getActivity(), msg,
															0).show();
												}
											});
								}

								@Override
								public void onFailure(String msg) {
									Toast.makeText(getActivity(), msg, 0)
											.show();
								}
							});
				}
			}
		});

		bt_send_check_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TimerCount timer = new TimerCount(60000, 1000,
						bt_send_check_code);
				TianCi.getInstance().getCaptcha(mobileNum,
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(), "获取成功");
							}

							@Override
							public void onFailure(String msg) {
								TCLogUtils.toastShort(getActivity(), msg);
							}
						});
				timer.start();

			}
		});
	}

	@Override
	int getLayoutId() {
		return R.layout.only_check_code_fragment;
	}

}
