package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

public class CheckBindStrategy implements IParamsStrategy {

	public String user_login;

	public CheckBindStrategy(String user_login) {
		this.user_login = user_login;
	}

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("user_login", user_login);
		return paramsQuest;
	}
}
