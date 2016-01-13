package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.content.Context;

import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

public class GetNumberStrategy implements IParamsStrategy {

	public GetNumberStrategy(Context context, String mobile) {
		super();
		this.context = context;
		this.mobile = mobile;
	}

	public Context context;
	public String mobile;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		String deviceId = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_ID, "");
		paramsQuest.put("mobile", mobile);
		paramsQuest.put("deviceid", deviceId);
		return paramsQuest;
	}
}
