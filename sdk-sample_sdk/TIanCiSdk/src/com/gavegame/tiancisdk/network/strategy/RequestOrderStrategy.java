package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.content.Context;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

public class RequestOrderStrategy implements IParamsStrategy {

	public RequestOrderStrategy(Context context, String... params) {
		this.context = context;
		this.params = params;
	}

	public Context context;
	public String[] params;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		int channelId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_CHANNEL_ID, 0);
		int gameId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_GAME_ID, 0);
		int deviceType = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_TYPE, 0);
		String tcsso = (String) SharedPreferencesUtils.getParam(context,
				Config.USER_TCSSO, "");

		paramsQuest.put("cp_role", Integer.valueOf(params[0]));
		paramsQuest.put("cp_order", params[1]);
		paramsQuest.put("serverid", params[2]);
		paramsQuest.put("amount", params[3]);
		paramsQuest.put("pay_type", params[4]);
		paramsQuest.put("tcsso", tcsso);
		paramsQuest.put("gameid", gameId);
		paramsQuest.put("channelid", channelId);
		paramsQuest.put("device_type", deviceType);
		return paramsQuest;
	}
}
