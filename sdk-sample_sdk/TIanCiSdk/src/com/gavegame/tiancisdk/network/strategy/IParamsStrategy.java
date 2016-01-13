package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;


public interface IParamsStrategy {
	

	/**
	 * 根据接口返回相应参数的策略
	 * @return
	 */
	HashMap<String, Object> getParamsQuest();

}
