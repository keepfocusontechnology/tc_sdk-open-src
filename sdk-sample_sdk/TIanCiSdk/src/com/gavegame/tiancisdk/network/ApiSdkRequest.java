package com.gavegame.tiancisdk.network;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.order.entity.OrderEntity;
import com.gavegame.tiancisdk.utils.DialogUtils;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

/**
 * Created by Tianci on 15/9/22.
 */
@SuppressLint("NewApi")
public class ApiSdkRequest extends AsyncTask<String, Void, ResponseMsg> {

	private final String TAG = "ApiSdkRequest";

	private static final String loginUrl = Config.SERVER
			+ "/index.php?g=mobile&m=login&a=%s";
	private static final String orderUri = Config.SERVER
			+ "/index.php?g=mobile&m=order&a=%s";
	private ResponseMsg responsMsg;

	private WeakReference<Context> mContextRef;

	private RequestCallBack callBack;
	private Method method;
	private String paramsUri;
	private Context context;
	private HashMap<String, Object> paramsQuest;

	private Dialog dialog;
	private int channelId;
	private int gameId;
	private int deviceType;
	private String useragent;
	private String deviceId;

	public static ApiSdkRequest newApiSdkRequest(ResponseBean responseBean) {
		if (responseBean == null)
			return null;
		return new ApiSdkRequest(responseBean);
	}

	private ApiSdkRequest(ResponseBean responseBean) {
		mContextRef = new WeakReference<Context>(responseBean.getContext());
		this.paramsUri = responseBean.getParamsUri();
		this.callBack = responseBean.getCallBack();
		this.method = responseBean.getMethod();
		this.context = mContextRef.get();
	}

	@Override
	protected ResponseMsg doInBackground(String... params) {

		// int resultCode = 0;
		String resultJson = null;
		setParams(params);
		String uri;
		if (paramsUri.equals(Config.REQUEST_PARAMS_FINISH_ORDER)
				|| paramsUri.equals(Config.REQUEST_PARAMS_CREATE_ORDER)
				|| paramsUri.equals(Config.REQUEST_PARAMS_REQUEST_ORDER)
				|| paramsUri.equals(Config.REQUEST_GET_ORDER_LIST)) {
			uri = String.format(orderUri, paramsUri);
		} else {
			uri = String.format(loginUrl, paramsUri);
		}
		try {
			if (method == Method.GET) {
				resultJson = HttpUtil.doGet(uri, paramsQuest);
			} else if (method == Method.POST) {
				resultJson = HttpUtil.doPost(uri, paramsQuest);
			} else if (method == Method.PUT) {
				// HttpUtil.doPut(uri, paramsQuest);
			} else if (method == Method.DELETE) {
				// HttpUtil.doDelete(uri, paramsQuest);
			}

			responsMsg = getJsonObjcet(resultJson);
		} catch (Exception e) {
			TCLogUtils.e(e.toString());
			responsMsg = new ResponseMsg();
			responsMsg.setRetMsg("网络访问错误");
		}
		return responsMsg;
	}

	/**
	 * 将结果码返回，如果result中包含tcsso字段，将其存储起来
	 *
	 * @param result
	 *            服务器返回的json串
	 * @return json串中的结果码
	 * @throws Exception
	 */
	private ResponseMsg getJsonObjcet(String result) throws Exception {
		TCLogUtils.e(TAG, result);

		responsMsg = new ResponseMsg();

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

			AlipayEntity entity = new AlipayEntity();
			entity.pantner = pay_info.getString("partner");
			entity.seller = pay_info.getString("seller");
			entity.rsa_public = pay_info.getString("rsa_public");
			entity.rsa_private = pay_info.getString("rsa_private");
			entity.notify_url = pay_info.getString("notify_url");
			entity.orderId = jsonObject.getString("tc_order");

			responsMsg.setBaseOrder(entity);
			pay_info = null;
		}

		if (result.contains("order_list")) {
			JSONArray order_list = jsonObject.getJSONArray("order_list");
			if (order_list.length() > 0) {
				List<BaseOrder> list = new ArrayList<BaseOrder>();
				for (int i = 0; i < order_list.length(); i++) {
					OrderEntity entity = new OrderEntity();
					JSONObject order = (JSONObject) order_list.get(i);
					entity.setOrder_amount(order.getString("amount"));
					entity.setOrder_time(order.getString("create_time"));
					entity.orderId = order.getString("id");
					entity.setPayway(PayWay.alipay);
					entity.setSuccessed(true);
					list.add(entity);
				}
				responsMsg.setOrderList(list);
			}
		}
		return responsMsg;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (context != null) {
			dialog = DialogUtils.createLoadingDialog(context);
			dialog.show();
			dialog.setCancelable(false);
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					ApiSdkRequest.this.cancel(true);
				}
			});
		}

		preParams();
	}

	/**
	 * 准备参数
	 */
	private void preParams() {
		channelId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_CHANNEL_ID, 0);
		gameId = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_GAME_ID, 0);
		deviceType = (int) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_TYPE, 0);
		useragent = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_USER_AGENT, "");
		deviceId = (String) SharedPreferencesUtils.getParam(context,
				Platform.TIANCI_DEVICE_ID, "");

		paramsQuest = new HashMap<>();
		paramsQuest.put("deviceid", deviceId);
		paramsQuest.put("gameid", gameId);
		paramsQuest.put("channelid", channelId);
		paramsQuest.put("useragent", useragent);
		paramsQuest.put("device_type", deviceType);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(ResponseMsg msg) {
		super.onPostExecute(msg);
		if (msg.getRetCode() == Config.REQUEST_STATUS_CODE_SUC) {
			callBack.onSuccessed(msg);
		} else {
			callBack.onFailure(msg.getRetMsg());
		}
		if (dialog != null) {
			dialog.dismiss();
		}
		paramsQuest.clear();
		paramsQuest = null;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 参数规则匹配 前期各个接口的参数都差不多，都会有几个固定的参数，所以想到直接封装到底层，外面只需要关系不一样的
	 * 其实这种方法我感觉有点日了狗了，还是放在外层传吧，然后再封装一次，让用户只关系他需要用到的
	 * 
	 * @param params
	 */
	private void setParams(String... params) {

		String tcsso = null;
		if (paramsUri.equals(Config.REQUEST_PARAMS_AUTOLOGIN)) {
			// paramsQuest.put("deviceid", params[0]);
			// 这一句可以看出统一加几个参数的好处，这句啥都不用写，可是后面要去掉一些无用的参数啊喂
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_LOGIN)
				|| paramsUri.equals(Config.REQUEST_PARAMS_REGISTER)) {
			paramsQuest.put("user_login", params[0]);
			paramsQuest.put("user_pass", params[1]);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_LOGIN_ROLE)) {
			tcsso = (String) SharedPreferencesUtils.getParam(context,
					Config.USER_TCSSO, "");
			paramsQuest.put("tcsso", tcsso);
			paramsQuest.remove("useragent");
			paramsQuest.remove("deviceid");
			paramsQuest.put("cp_role", Integer.valueOf(params[0]));
			paramsQuest.put("serverid", Integer.valueOf(params[1]));
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_CREATE_ORDER)
				|| paramsUri.equals(Config.REQUEST_PARAMS_FINISH_ORDER)) {
			tcsso = (String) SharedPreferencesUtils.getParam(context,
					Config.USER_TCSSO, "");
			paramsQuest.put("tcsso", tcsso);
			paramsQuest.remove("useragent");
			paramsQuest.remove("deviceid");
			paramsQuest.put("cp_role", Integer.valueOf(params[0]));
			paramsQuest.put("cp_order", params[1]);
			paramsQuest.put("serverid", params[2]);
			paramsQuest.put("amount", Double.valueOf(params[3]));
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_GET_NUMBER)) {
			paramsQuest.clear();
			paramsQuest.put("mobile", params[0]);
			paramsQuest.put("deviceid", deviceId);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_MOBILE_REGISTER)) {
			paramsQuest.put("mobile", params[0]);
			paramsQuest.put("user_pass", params[1]);
			paramsQuest.put("code", params[2]);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_USER_BIND)) {
			paramsQuest.clear();
			tcsso = (String) SharedPreferencesUtils.getParam(context,
					Config.USER_TCSSO, "");
			paramsQuest.put("mobile", params[0]);
			paramsQuest.put("code", params[1]);
			if (params.length > 2 && !TextUtils.isEmpty(params[2])) {
				paramsQuest.put("user_pass", params[2]);
				paramsQuest.put("tcsso", tcsso);
			}
			paramsQuest.put("tcsso", tcsso);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_FORGET_PASS)) {
			paramsQuest.clear();
			paramsQuest.put("mobile", params[0]);
			paramsQuest.put("code", params[1]);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_CHECK_BIND)) {
			paramsQuest.clear();
			paramsQuest.put("user_login", params[0]);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_SET_PASS)) {
			paramsQuest.clear();
			paramsQuest.put("mobile", params[0]);
			paramsQuest.put("code", params[1]);
			paramsQuest.put("user_pass", params[2]);
		} else if (paramsUri.equals(Config.REQUEST_PARAMS_REQUEST_ORDER)) {
			paramsQuest.remove("deviceid");
			paramsQuest.remove("useragent");
			tcsso = (String) SharedPreferencesUtils.getParam(context,
					Config.USER_TCSSO, "");
			paramsQuest.put("cp_role", Integer.valueOf(params[0]));
			paramsQuest.put("cp_order", params[1]);
			paramsQuest.put("serverid", params[2]);
			paramsQuest.put("amount", params[3]);
			paramsQuest.put("pay_type", params[4]);
			paramsQuest.put("tcsso", tcsso);
		} else if (paramsUri.equals(Config.REQUEST_GET_ORDER_LIST)) {
			paramsQuest.clear();
			tcsso = (String) SharedPreferencesUtils.getParam(context,
					Config.USER_TCSSO, "");
			paramsQuest.put("tcsso", tcsso);
		}
	}
}
