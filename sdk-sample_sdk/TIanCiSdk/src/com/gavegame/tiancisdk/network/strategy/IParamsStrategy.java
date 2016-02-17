package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import com.gavegame.tiancisdk.network.bean.ResponseMsg;


public interface IParamsStrategy {
	

	/**
	 * 根据接口返回相应参数的策略
	 * @return
	 */
	HashMap<String, Object> getParamsQuest();
	
	/**
	 * 解析json的策略
	 * @return
	 * @throws Exception 
	 */
	ResponseMsg resolveJson(String json) throws Exception;

}
