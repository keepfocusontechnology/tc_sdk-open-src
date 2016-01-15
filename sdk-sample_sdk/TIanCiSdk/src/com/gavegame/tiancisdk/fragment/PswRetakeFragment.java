package com.gavegame.tiancisdk.fragment;

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
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.utils.TimerCount;

/**
 * 验证手机是否绑定的界面
 * 
 * @author Tianci
 *
 */

public class PswRetakeFragment extends TCBaseFragment {

	private TimerCount timer;
	private Button bt_captcha;
	private EditText et_phone_num;
	private EditText et_captcha;

	@Override
	void initID() {
		String phoneNum = (String) SharedPreferencesUtils.getParam(
				getActivity(), "mobile", "");
		et_phone_num = (EditText) view.findViewById(R.id.et_bind_phone_num);
		et_phone_num.setEnabled(false);
		if (!TextUtils.isEmpty(phoneNum))
			et_phone_num.setText(phoneNum);
		et_captcha = (EditText) view.findViewById(R.id.et_mobile_bind_captcha);
		// 弹出数字键盘
		et_captcha.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		bt_captcha = (Button) view.findViewById(R.id.bt_captcha);
		timer = new TimerCount(60000, 1000, bt_captcha);
		bt_captcha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.start();
				if (!NormalUtils.isMobile(et_phone_num.getText() + "")) {
					TCLogUtils.toastShort(getActivity(), "手机号码格式不正确！");
				} else {
					TianCi.getInstance().getCaptcha(
							et_phone_num.getText() + "", new RequestCallBack() {

								@Override
								public void onSuccessed(ResponseMsg msg) {
									TCLogUtils.toastShort(getActivity(),
											"获取成功!");
								}

								@Override
								public void onFailure(String msg) {
									TCLogUtils.toastShort(getActivity(), msg);
								}
							});
				}
			}
		});
		view.findViewById(R.id.bt_check).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						TianCi.getInstance().forgetPsw(
								et_phone_num.getText() + "",
								et_captcha.getText() + "",
								new RequestCallBack() {

									@Override
									public void onSuccessed(ResponseMsg msg) {
										Bundle bundle = new Bundle();
										bundle.putString("account",
												et_phone_num.getText() + "");
										bundle.putString("captcha",
												et_captcha.getText() + "");
										callback.jumpNextPage(
												Config.MAKE_NEWPSW_FRAGMENT,
												bundle);
									}

									@Override
									public void onFailure(String msg) {
										TCLogUtils.toastShort(getActivity(),
												msg);
									}
								});

					}
				});
		view.findViewById(R.id.bt_mobile_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						callback.back();
					}
				});
	}

	@Override
	int getLayoutId() {
		return R.layout.psw_retake_fragment;
	}

}
