package com.gavegame.tiancisdk.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.utils.TimerCount;

public class MobileBindFragment extends TCBaseFragment {

	private EditText et_mobile_num;
	private EditText et_captcha;

	@Override
	void initID() {
		et_mobile_num = (EditText) view.findViewById(R.id.et_bind_phone_num);
		et_captcha = (EditText) view.findViewById(R.id.et_mobile_bind_captcha);
		et_captcha.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		Button bt_bind = (Button) view.findViewById(R.id.bt_check);
		bt_bind.setText("绑定");
		view.findViewById(R.id.bt_mobile_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						callback.back();
					}
				});
		;
		bt_bind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_mobile_num.getText() + "")) {
					// TODO 自定义吐司，手机号码不能为空
					TCLogUtils.toastShort(getActivity(), "手机号码不能为空");
					return;
				}
				if (TextUtils.isEmpty(et_captcha.getText() + "")) {
					// TODO 自定义吐司，验证码不能为空
					TCLogUtils.toastShort(getActivity(), "验证码不能为空");
					return;
				}
				if (!NormalUtils.isMobile(et_mobile_num.getText() + "")) {
					// TODO 自定义吐司，手机号码格式有误
					TCLogUtils.toastShort(getActivity(), "手机号码格式有误");
					return;
				}
				TianCi.getInstance().userBind(et_mobile_num.getText() + "",
						et_captcha.getText() + "", new RequestCallBack() {

							@Override
							public void onSuccessed(int userBindCode) {
								// TODO 自定义吐司，绑定成功
								TCLogUtils.toastShort(getActivity(), "手机绑定成功");
								// 绑定成功，将当前手机号码带入第一个登陆页面显示出来
								Bundle bundle = new Bundle();
								bundle.putString("account",
										et_mobile_num.getText() + "");
								callback.jumpNextPage(
										Config.QUCKILY_LOGIN_FRAGMENT, bundle);
							}

							@Override
							public void onFailure(ResponseMsg msg) {
								// 绑定不成功，留在此页面
								TCLogUtils.toastShort(getActivity(),
										msg.getRetMsg());

							}
						});
			}
		});
		final Button bt_captcha = (Button) view.findViewById(R.id.bt_captcha);
		bt_captcha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (NormalUtils.isMobile(et_mobile_num.getText() + "")) {
					TimerCount timer = new TimerCount(60000, 1000, bt_captcha);
					TianCi.getInstance().getCaptcha(
							et_mobile_num.getText() + "",
							new RequestCallBack() {

								@Override
								public void onSuccessed(int code) {
									TCLogUtils
											.toastShort(getActivity(), "获取成功");
								}

								@Override
								public void onFailure(ResponseMsg msg) {
									TCLogUtils.toastShort(getActivity(),
											msg.getRetMsg());
								}
							});
					timer.start();
				}

			}
		});
	}

	@Override
	int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.psw_retake_fragment;
	}

}
