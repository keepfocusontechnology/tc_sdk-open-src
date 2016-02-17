package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

import android.text.TextUtils;

public class UserBindStrategy implements IParamsStrategy {

	private final String TAG = "UserBindStrategy";
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
