package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class LoginRoleStrategy implements IParamsStrategy {
	
	private final String TAG = "LoginRoleStrategy";

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
		paramsQuest.put("cp_role", cp_role);
		paramsQuest.put("serverid", Integer.valueOf(serverid));
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
