package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;


public class SetPswStrategy implements ParamsStrategy {

	public String[] params;

	public SetPswStrategy(String... params) {
		this.params = params;
	}

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("mobile", params[0]);
		paramsQuest.put("code", params[1]);
		paramsQuest.put("user_pass", params[2]);
		return paramsQuest;
	}
}
