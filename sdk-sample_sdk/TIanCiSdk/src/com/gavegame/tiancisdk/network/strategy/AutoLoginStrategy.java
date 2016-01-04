package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.content.Context;

import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

public class AutoLoginStrategy implements ParamsStrategy {

	public Context context;

	public AutoLoginStrategy(Context context) {
		this.context = context;
	}

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
		return paramsQuest;
	}
}
