package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.content.Context;

import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

public class MobileRegisterStrategy implements IParamsStrategy {

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
}
