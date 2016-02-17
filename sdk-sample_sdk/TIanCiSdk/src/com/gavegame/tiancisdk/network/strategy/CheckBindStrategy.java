package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class CheckBindStrategy implements IParamsStrategy {

	public String user_login;
	private final String TAG = "CheckBindStrategy";

	public CheckBindStrategy(String user_login) {
		this.user_login = user_login;
	}

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("user_login", user_login);
		return paramsQuest;
	}

	@Override
	public ResponseMsg resolveJson(String json) throws Exception {
		TCLogUtils.e(TAG, json);
		ResponseMsg responsMsg = new ResponseMsg();
		JSONObject jsonObject = new JSONObject(json);

		responsMsg.setRetCode(jsonObject.getInt("retcode"));
		responsMsg.setRetMsg(jsonObject.getString("retmsg"));
		responsMsg.setBindCode(jsonObject.getInt("is_bind"));

		return responsMsg;
	}
}
