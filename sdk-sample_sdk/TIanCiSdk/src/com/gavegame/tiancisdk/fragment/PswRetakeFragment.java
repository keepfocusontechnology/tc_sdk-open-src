package com.gavegame.tiancisdk.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class PswRetakeFragment extends TCBaseFragment {

	private TimerCount timer;
	private Button bt_captcha;
	private EditText et_phone_num;
	private EditText et_captcha;

	@Override
	void initID() {
		et_phone_num = (EditText) view.findViewById(R.id.et_bind_phone_num);
		et_captcha = (EditText)view.findViewById(R.id.et_mobile_bind_captcha);
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
								public void onSuccessed(int code) {
									TCLogUtils.toastShort(getActivity(),
											"获取成功!");
								}

								@Override
								public void onFailure(ResponseMsg msg) {
									TCLogUtils.toastShort(getActivity(),
											msg.getRetMsg());
								}
							});
				}
			}
		});
		view.findViewById(R.id.bt_check).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						TianCi.getInstance().forgetPsw(et_phone_num.getText()+"", et_captcha.getText()+"", new RequestCallBack() {
							
							@Override
							public void onSuccessed(int userBindCode) {
								Bundle bundle = new Bundle();
								bundle.putString("account", et_phone_num.getText()+"");
								bundle.putString("captcha", et_captcha.getText()+"");
								callback.jumpNextPage(Config.MAKE_NEWPSW_FRAGMENT);
							}
							
							@Override
							public void onFailure(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(), msg.getRetMsg());
							}
						});
						
					}
				});
		view.findViewById(R.id.bt_mobile_back).setOnClickListener(new OnClickListener() {
			
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
