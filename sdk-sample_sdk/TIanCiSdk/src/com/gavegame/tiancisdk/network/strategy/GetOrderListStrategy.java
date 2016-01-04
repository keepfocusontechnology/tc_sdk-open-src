package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

public class GetOrderListStrategy implements ParamsStrategy {

	public GetOrderListStrategy(String tcsso) {
		this.tcsso = tcsso;
	}

	public String tcsso;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("tcsso", tcsso);
		return paramsQuest;
	}
}
