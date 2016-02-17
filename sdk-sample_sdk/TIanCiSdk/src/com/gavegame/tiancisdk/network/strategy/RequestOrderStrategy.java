package com.gavegame.tiancisdk.network.strategy;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.bean.AlipayEntity;
import com.gavegame.tiancisdk.network.bean.BaseOrder;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.network.bean.WxpayEntity;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class RequestOrderStrategy implements IParamsStrategy {

	private final String TAG = "RequestOrderStrategy";

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

	@Override
	public ResponseMsg resolveJson(String json) throws Exception {
		TCLogUtils.e(TAG, json);
		ResponseMsg responsMsg = new ResponseMsg();
		JSONObject jsonObject = new JSONObject(json);

		responsMsg.setRetCode(jsonObject.getInt("retcode"));
		responsMsg.setRetMsg(jsonObject.getString("retmsg"));
//		responsMsg.setOrderId(jsonObject.getInt("tc_order"));
		SharedPreferencesUtils.setParam(context, Config.TC_ORDER_ID,
				jsonObject.getInt(Config.TC_ORDER_ID));
		JSONObject pay_info = jsonObject.getJSONObject("pay_info");

		if (params[4].equals(PayWay.alipay.getPayway())) {
			AlipayEntity entity = null;
			try {
				entity = new AlipayEntity();
				entity.pantner = pay_info.getString("partner");
				entity.seller = pay_info.getString("seller");
				entity.rsa_public = pay_info.getString("rsa_public");
				entity.rsa_private = pay_info.getString("rsa_private");
				entity.notify_url = pay_info.getString("notify_url");
				entity.orderId = jsonObject.getString("tc_order");
			} catch (Exception e) {
				TCLogUtils.e(TAG, "获取支付宝支付信息错误：" + e.getMessage());
			}
			responsMsg.setBaseOrder(entity);
		} else if (params[4].equals(PayWay.yinlian.getPayway())) {
			try {
				String tn = pay_info.getString("tn");
				if (!TextUtils.isEmpty(tn)) {
					responsMsg.setRetMsg(tn);
				}
			} catch (Exception e) {
				TCLogUtils.e(TAG, "获取银联支付信息错误：" + e.getMessage());
			}
		} else if (params[4].equals(PayWay.wechat.getPayway())) {
			try {
				WxpayEntity entity = new WxpayEntity();
				entity.appId = pay_info.getString("appid").trim();
				entity.nonceStr = pay_info.getString("noncestr");
				entity.packageValue = pay_info.getString("package");
				entity.partnerId = pay_info.getString("partnerid");
				entity.prepayId = pay_info.getString("prepayid");
				entity.timeStamp = pay_info.getString("timestamp");
				entity.sign = pay_info.getString("sign");
				responsMsg.setBaseOrder(entity);
			} catch (Exception e) {
				TCLogUtils.e(TAG, "获取微信支付信息错误：" + e.getMessage());
			}
		}

		return responsMsg;
	}
}
