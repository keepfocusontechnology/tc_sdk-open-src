package com.gavegame.tiancisdk.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;

public class PhoneBindFragment extends TCBaseFragment {

	@Override
	void initID() {
		view.findViewById(R.id.bt_tcsdk_phone_bind_next).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent data = new Intent();
						data.putExtra("tcsso", TianCi.getInstance().getTcsso());
						getActivity().setResult(Config.REQUEST_STATUS_CODE_SUC,
								data);
						getActivity().finish();
					}
				});
		;
		view.findViewById(R.id.bt_tcsdk_phone_bind_now).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						callback.jumpNextPage(Config.MOBILE_BIND_FRAGMENT);
					}
				});
		;
	}

	@Override
	int getLayoutId() {
		return R.layout.phone_num_bind;
	}

}
