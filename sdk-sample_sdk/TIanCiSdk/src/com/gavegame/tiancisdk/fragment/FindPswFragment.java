package com.gavegame.tiancisdk.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.widget.CustomerDialog;

/**
 * 找回密码页面，由主页的右下角找回密码进入
 * @author Tianci
 *
 */
public class FindPswFragment extends TCBaseFragment {

	private EditText et_username;

	@Override
	void initID() {
		et_username = (EditText) view.findViewById(R.id.et_find_psw_username);
		view.findViewById(R.id.tv_find_psw_contact);
		view.findViewById(R.id.bt_find_psw_confirm).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (TextUtils.isEmpty(et_username.getText())) {
							// TODO 自定义吐司 判空
//							TCSdkToast.show("账号为空", getActivity());
						} else {
							if (NormalUtils.isMobile(et_username.getText() + "")) {
								// TODO 是手机号直接跳转发送验证码界面
								Bundle bundle = new Bundle();
								bundle.putString("account", et_username.getText()+"");
								callback.jumpNextPage(Config.FINISH_CODE_CHECK_FRAGMENT, bundle);
							} else {
								// TODO 非手机号先判断是否绑定
								TianCi.getInstance().isBind(
										et_username.getText() + "",
										new RequestCallBack() {
											@Override
											public void onSuccessed(
													int userBindCode) {
												// TODO 非手机号绑定成功，跳转输入绑定手机号验证界面
												callback.jumpNextPage(Config.PSW_RETAKE_FRAGMENT);
											}

											@Override
											public void onFailure(
													ResponseMsg msg) {
												if (msg.getBindCode() == 1) {
//													TCSdkToast.show("这里如果返回绑定，逻辑有问题哟", getActivity());
												} else {
													// TODO 账号未绑定，跳转客服界面
													CustomerDialog dialog = new CustomerDialog(
															getActivity(),
															R.layout.tcsdk_customer_service);
													dialog.show();
												}
											}
										});
							}
						}
					}
				});
		;
	}

	@Override
	int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.find_psw_fragment;
	}

}
