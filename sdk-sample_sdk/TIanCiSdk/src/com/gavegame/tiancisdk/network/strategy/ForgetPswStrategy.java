package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

public class ForgetPswStrategy implements ParamsStrategy {

	public String mobile;
	public String code;

	public ForgetPswStrategy(String mobile, String code) {
		this.mobile = mobile;
		this.code = code;
	}

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("mobile", mobile);
		paramsQuest.put("code", code);
		return paramsQuest;
	}
}
