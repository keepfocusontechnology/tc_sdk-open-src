package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class GetNumberStrategy implements IParamsStrategy {
	private final String TAG = "GetNumberStrategy";

	public GetNumberStrategy(Context context, String mobile) {
		super();
		this.context = context;
		this.mobile = mobile;
	}

	public Context context;
	public String mobile;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		String deviceId = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_ID, "");
		paramsQuest.put("mobile", mobile);
		paramsQuest.put("deviceid", deviceId);
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
