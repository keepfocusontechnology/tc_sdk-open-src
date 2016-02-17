package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MobileRegisterStrategy implements IParamsStrategy {

	private final String TAG = "MobileRegisterStrategy";

	public MobileRegisterStrategy(Context context, String mobile,
			String user_pass, String code) {
		super();
		this.context = context;
		this.mobile = mobile;
		this.user_pass = user_pass;
		this.code = code;
	}

	public Context context;
	public String mobile;
	public String user_pass;
	public String code;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		int channelId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_CHANNEL_ID, 0);
		int gameId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_GAME_ID, 0);
		int deviceType = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_TYPE, 0);
		String useragent = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_USER_AGENT, "");
		String deviceId = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_ID, "");

		paramsQuest.put("deviceid", deviceId);
		paramsQuest.put("gameid", gameId);
		paramsQuest.put("channelid", channelId);
		paramsQuest.put("useragent", useragent);
		paramsQuest.put("device_type", deviceType);
		paramsQuest.put("mobile", mobile);
		paramsQuest.put("user_pass", user_pass);
		paramsQuest.put("code", code);
		return paramsQuest;
	}

	@Override
	public ResponseMsg resolveJson(String json) throws Exception {
		TCLogUtils.e(TAG, json);
		ResponseMsg responsMsg = new ResponseMsg();
		JSONObject jsonObject = new JSONObject(json);

		responsMsg.setRetCode(jsonObject.getInt("retcode"));
		responsMsg.setRetMsg(jsonObject.getString("retmsg"));
		responsMsg.setTcsso(jsonObject.getString("tcsso"));
		SharedPreferencesUtils.setParam(context, Config.USER_TCSSO,
				jsonObject.getString(Config.USER_TCSSO));
		return responsMsg;
	}
}
