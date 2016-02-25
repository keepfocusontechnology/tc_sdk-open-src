package com.gavegame.tiancisdk.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.network.bean.AlipayEntity;
import com.gavegame.tiancisdk.network.bean.BaseOrder;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.network.bean.WxpayEntity;
import com.gavegame.tiancisdk.order.entity.OrderEntity;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class NetworkUtils {

	private final static String TAG = "NetworkUtils";
	private static String SERVER;

	private final static String loginUri = "/index.php?g=mobile&m=login&a=%s";
	private final static String orderUri = "/index.php?g=mobile&m=order&a=%s";

	public static String getUrl(String paramsUri) {
		String loginUrl = SERVER + loginUri;
		String orderUrl = SERVER + orderUri;

		if (paramsUri.equals(Config.REQUEST_PARAMS_FINISH_ORDER)
				|| paramsUri.equals(Config.REQUEST_PARAMS_CREATE_ORDER)
				|| paramsUri.equals(Config.REQUEST_PARAMS_REQUEST_ORDER)
				|| paramsUri.equals(Config.REQUEST_GET_ORDER_LIST)) {
			return String.format(orderUrl, paramsUri);
		} else {
			return String.format(loginUrl, paramsUri);
		}
	}

	public static void setServer(String url) {
		SERVER = url;
	}

	/**
	 * 将结果码返回，如果result中包含tcsso字段，将其存储起来
	 *
	 * @param result
	 *            服务器返回的json串
	 * @return json串中的结果码
	 * @throws Exception
	 */
	public static ResponseMsg getJsonObjcet(Context context, String result)
			throws Exception {
		TCLogUtils.e(TAG, result);

		ResponseMsg responsMsg = new ResponseMsg();

		JSONObject jsonObject = new JSONObject(result);

		if (result.contains("\"tcsso\"")) {
			responsMsg.setTcsso(jsonObject.getString(Config.USER_TCSSO));
			SharedPreferencesUtils.setParam(context, Config.USER_TCSSO,
					jsonObject.getString(Config.USER_TCSSO));
		}
		if (result.contains("\"tc_order\"")) {
			responsMsg.setOrderId(jsonObject.getInt("tc_order"));
			SharedPreferencesUtils.setParam(context, Config.TC_ORDER_ID,
					jsonObject.getInt(Config.TC_ORDER_ID));
		}
		if (result.contains("\"is_bind\"")) {
			responsMsg.setBindCode(jsonObject.getInt("is_bind"));
		}
		if (result.contains("\"retcode\"")) {
			responsMsg.setRetCode(jsonObject.getInt("retcode"));
		}
		if (result.contains("\"retmsg\"")) {
			responsMsg.setRetMsg(jsonObject.getString("retmsg"));
		}
		if (result.contains("\"mobile\"")) {
			// responsMsg.setRetMsg(jsonObject.getString("retmsg"));
			SharedPreferencesUtils.setParam(context, "mobile",
					jsonObject.getString("mobile"));
		}
		if (result.contains("\"pay_info\"") && result.contains("tc_order")) {
			JSONObject pay_info = jsonObject.getJSONObject("pay_info");

			try {
				String tn = pay_info.getString("tn");
				if (!TextUtils.isEmpty(tn)) {
					responsMsg.setRetMsg(tn);
					return responsMsg;
				}
			} catch (Exception e) {
				// 发生异常不做操作，代表不是银联的接口
			}

			// {"appid":"wx7f69866f179fa23e","noncestr":"zS2JhVP9S0Njyctc"
			// ,"package":"Sign=WXPay","partnerid":"1305459601",
			// "prepayid":"wx20160115095910733f7566010959807723",
			// "timestamp":"20160115095910","sign":"2B4A7A9D8FE1D656BA9DD4636A1F28EC"}}
			BaseOrder baseEntity = null;
			try {
				AlipayEntity entity = new AlipayEntity();
				entity.pantner = pay_info.getString("partner");
				entity.seller = pay_info.getString("seller");
				entity.rsa_public = pay_info.getString("rsa_public");
				entity.rsa_private = pay_info.getString("rsa_private");
				entity.notify_url = pay_info.getString("notify_url");
				entity.orderId = jsonObject.getString("tc_order");
				baseEntity = entity;
			} catch (Exception e) {
				try {
					WxpayEntity entity = new WxpayEntity();
					entity.appId = pay_info.getString("appid").trim();
					entity.nonceStr = pay_info.getString("noncestr");
					entity.packageValue = pay_info.getString("package");
					entity.partnerId = pay_info.getString("partnerid");
					entity.prepayId = pay_info.getString("prepayid");
					entity.timeStamp = pay_info.getString("timestamp");
					entity.sign = pay_info.getString("sign");
					baseEntity = entity;
				} catch (Exception e1) {

				}
			}

			responsMsg.setBaseOrder(baseEntity);
			pay_info = null;
		}

		if (result.contains("order_list")) {
			JSONArray order_list = null;
			try {
				order_list = jsonObject.getJSONArray("order_list");
			} catch (JSONException e) {
				return responsMsg;
			}

			if (order_list.length() > 0) {
				List<BaseOrder> list = new ArrayList<BaseOrder>();
				for (int i = 0; i < order_list.length(); i++) {
					OrderEntity entity = new OrderEntity();
					JSONObject order = (JSONObject) order_list.get(i);
					entity.setOrder_amount(order.getString("amount"));
					entity.setOrder_time(order.getString("create_time"));
					entity.orderId = order.getString("id");
					entity.setPayway(order.getString("pay_type"));
					entity.setSuccessed(true);
					list.add(entity);
				}
				responsMsg.setOrderList(list);
			}
		}
		return responsMsg;
	}
}
