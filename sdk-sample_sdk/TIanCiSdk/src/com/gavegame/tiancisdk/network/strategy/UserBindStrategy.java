package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.text.TextUtils;

public class UserBindStrategy implements ParamsStrategy {

	public String tcsso;
	public String[] params;

	public UserBindStrategy(String tcsso, String... params) {
		this.tcsso = tcsso;
		this.params = params;
	}

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();

		paramsQuest.put("mobile", params[0]);
		paramsQuest.put("code", params[1]);
		if (params.length > 2 && !TextUtils.isEmpty(params[2])) {
			paramsQuest.put("user_pass", params[2]);
		}
		paramsQuest.put("tcsso", tcsso);
		return paramsQuest;
	}
}
