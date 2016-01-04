package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import android.content.Context;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

public class LoginRoleStrategy implements ParamsStrategy {

	public LoginRoleStrategy(Context context, String cp_role, String serverid) {
		super();
		this.context = context;
		this.cp_role = cp_role;
		this.serverid = serverid;
	}

	public Context context;
	public String cp_role;
	public String serverid;

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

		paramsQuest.put("tcsso", tcsso);
		paramsQuest.put("gameid", gameId);
		paramsQuest.put("channelid", channelId);
		paramsQuest.put("device_type", deviceType);
		paramsQuest.put("cp_role", Integer.valueOf(cp_role));
		paramsQuest.put("serverid", Integer.valueOf(serverid));
		return paramsQuest;
	}
}
