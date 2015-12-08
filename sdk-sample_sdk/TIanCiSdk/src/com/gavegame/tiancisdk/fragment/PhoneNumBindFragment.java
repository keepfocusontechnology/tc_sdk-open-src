package com.gavegame.tiancisdk.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

/**
 * 游客绑定手机号界面
 * 
 * @author Tianci
 *
 */
public class PhoneNumBindFragment extends TCBaseFragment {

	private EditText et_phone;
	private EditText et_psw;
	private EditText et_code;

	@Override
	void initID() {
		et_phone = (EditText) view.findViewById(R.id.et_username);
		et_psw = (EditText) view.findViewById(R.id.et_psw);
		et_code = (EditText) view.findViewById(R.id.et_captcha);
		et_code.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		view.findViewById(R.id.bt_mobile_reg_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						callback.back();
					}
				});
		;
		TextView tv_confirm = (TextView) view
				.findViewById(R.id.bt_mabile_reg_confirm);
		tv_confirm.setText("绑定");
		tv_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!dataCheck(et_phone.getText() + "", et_psw.getText() + "",
						et_code.getText() + "")) {
					return;
				}

				TianCi.getInstance().userBind(et_phone.getText() + "",
						et_psw.getText() + "", et_code.getText() + "",
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg msg) {
								TCLogUtils.toastShort(getActivity(), "绑定成功");
							}

							@Override
							public void onFailure(String msg) {
								TCLogUtils.toastShort(getActivity(), msg);
							}
						});
			}
		});
	}

	@Override
	int getLayoutId() {
		return R.layout.phone_num_register_fragment;
	}

	// 检测用户名，密码是否合法
	private boolean dataCheck(String... params) {
		if (TextUtils.isEmpty(params[0]) || TextUtils.isEmpty(params[1])
				|| TextUtils.isEmpty(params[2])) {
			// TCSdkToast.show("不能为空", getActivity());
			return false;
		}

		if (!NormalUtils.isMobile(params[0])) {
			// TCSdkToast.show("不是手机啊", getActivity());
			return false;
		}
		return true;
	}

}
