package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class SetPswStrategy implements IParamsStrategy {

	private final String TAG = "SetPswStrategy";
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

	@Override
	public ResponseMsg resolveJson(String json) throws Exception {
		TCLogUtils.e(TAG, json);
		ResponseMsg responsMsg = new ResponseMsg();
		JSONObject jsonObject = new JSONObject(json);

		responsMsg.setRetCode(jsonObject.getInt("retcode"));
		responsMsg.setRetMsg(jsonObject.getString("retmsg"));

		return responsMsg;
	}
}
