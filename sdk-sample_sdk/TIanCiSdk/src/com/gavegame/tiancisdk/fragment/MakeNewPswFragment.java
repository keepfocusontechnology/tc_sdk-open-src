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

/**
 * 账号已绑定，且验证码通过后，最后的设置新密码
 * 
 * @author Tianci
 *
 */
public class MakeNewPswFragment extends TCBaseFragment {

	private EditText et_new_psw;
	private Button bt_back;
	private Button bt_confirm;

	private String mobileNum;
	private String captcha;

	@Override
	public void initData(Bundle data) {
		super.initData(data);
		mobileNum = data.getString("account");
		captcha = data.getString("captcha");
	}

	@Override
	void initID() {
		et_new_psw = (EditText) view.findViewById(R.id.et_new_psw);
		bt_back = (Button) view.findViewById(R.id.bt_new_psw_back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.back();
			}
		});

		bt_confirm = (Button) view.findViewById(R.id.bt_new_psw_retake);
		bt_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(et_new_psw.getText()))
					TianCi.getInstance().setPsw(mobileNum, captcha,
							et_new_psw.getText() + "", new RequestCallBack() {

								@Override
								public void onSuccessed(ResponseMsg msg) {
									TCLogUtils.toastShort(getActivity(),
											"修改密码成功");
									Bundle data = new Bundle();
									data.putString("account", mobileNum);
									callback.jumpNextPage(
											Config.QUCKILY_LOGIN_FRAGMENT, data);
								}

								@Override
								public void onFailure(String msg) {
									TCLogUtils.toastShort(getActivity(),
											msg);
								}
							});

			}
		});
	}

	@Override
	int getLayoutId() {
		return R.layout.make_new_psw_fragment;
	}

}
