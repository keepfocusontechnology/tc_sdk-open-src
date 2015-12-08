package com.gavegame.tiancisdk.fragment;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * 手机号码直接注册
 * 
 * @author Tianci
 *
 */
public class PhoneNumRegisterFragment extends TCBaseFragment {

	private Button bt_captcha;
	private TimerCount timer;
	private EditText et_phone;
	private EditText et_psw;
	private EditText et_code;
	
	@Override
	void initID() {
		et_phone = (EditText) view.findViewById(R.id.et_username);
		et_psw = (EditText) view.findViewById(R.id.et_psw);
		et_code = (EditText) view.findViewById(R.id.et_captcha);
		et_code.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		view.findViewById(R.id.bt_mabile_reg_confirm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (NormalUtils.isMobile(et_phone.getText() + "")) {
							TianCi.getInstance().mobileRegister(
									et_phone.getText() + "",
									et_psw.getText() + "",
									et_code.getText() + "",
									new RequestCallBack() {

										@Override
										public void onSuccessed(ResponseMsg msg) {
											TCLogUtils.toastShort(
													getActivity(), "注册成功");
											Intent data = new Intent();
											data.putExtra("tcsso",
													msg.getTcsso());
											getActivity()
													.setResult(
															Config.REQUEST_STATUS_CODE_SUC,
															data);
											getActivity().finish();
										}

										@Override
										public void onFailure(String msg) {
											TCLogUtils.toastShort(
													getActivity(), msg);

											Intent data = new Intent();
											data.putExtra("result", msg);
											getActivity()
													.setResult(
															Config.REQUEST_STATUS_CODE_FAILURE,
															data);
//											getActivity().finish();
										}
									});
						}
					}
				});
		;

		Button bt_back = (Button) view.findViewById(R.id.bt_mobile_reg_back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.back();
			}
		});
		bt_captcha = (Button) view.findViewById(R.id.bt_captcha);
		bt_captcha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (NormalUtils.isMobile(et_phone.getText() + "")) {
					timer = new TimerCount(60000, 1000, bt_captcha);
					TianCi.getInstance().getCaptcha(et_phone.getText() + "",
							new RequestCallBack() {

								@Override
								public void onSuccessed(ResponseMsg msg) {
									TCLogUtils
											.toastShort(getActivity(), "获取成功");
								}

								@Override
								public void onFailure(String msg) {
									TCLogUtils.toastShort(getActivity(), msg);
								}
							});
					timer.start();
				}
			}
		});

	}

	@Override
	int getLayoutId() {
		return R.layout.phone_num_register_fragment;
	}
}
