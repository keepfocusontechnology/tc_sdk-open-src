package com.gavegame.tiancisdk.network;

import java.lang.ref.WeakReference;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.network.bean.ResponseBean;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.DialogUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;

/**
 * Created by Tianci on 15/9/22.
 */
@SuppressLint("NewApi")
public class ApiSdkRequest extends AsyncTask<Void, Void, ResponseMsg> {

	private final String TAG = "ApiSdkRequest";

	// private static final String loginUrl = Config.SERVER
	// + "/index.php?g=mobile&m=login&a=%s";
	// private static final String orderUri = Config.SERVER
	// + "/index.php?g=mobile&m=order&a=%s";
	private ResponseMsg responsMsg;

	private WeakReference<Context> mContextRef;

	private RequestCallBack callBack;
	private Method method;
	private Context context;
	private HashMap<String, Object> paramsQuest;
	private String url;
	private Dialog dialog;

	private HttpCore core;

	// private int channelId;
	// private int gameId;
	// private int deviceType;
	// private String useragent;
	// private String deviceId;

	public static ApiSdkRequest newApiSdkRequest(ResponseBean responseBean) {
		if (responseBean == null)
			return null;
		return new ApiSdkRequest(responseBean);
	}

	private ApiSdkRequest(ResponseBean responseBean) {
		mContextRef = new WeakReference<Context>(responseBean.getContext());
		this.url = responseBean.getUrl();
		this.callBack = responseBean.getCallBack();
		this.method = responseBean.getMethod();
		this.context = mContextRef.get();
		if (null != responseBean.getStrategy()) {
			this.paramsQuest = responseBean.getStrategy().getParamsQuest();
		}
	}

	@Override
	protected ResponseMsg doInBackground(Void... params) {

		String resultJson = null;
		core = new HttpCore();
		try {
			if (method == Method.GET) {
				resultJson = core.doGet(url, paramsQuest);
			} else if (method == Method.POST) {
				resultJson = core.doPost(url, paramsQuest);
			} else if (method == Method.PUT) {
				// HttpUtil.doPut(url, paramsQuest);
			} else if (method == Method.DELETE) {
				// HttpUtil.doDelete(url, paramsQuest);
			}

			try {
				responsMsg = NetworkUtils.getJsonObjcet(context, resultJson);
			} catch (Exception e) {
				TCLogUtils.e("返回的json数据解析出错!!" + e);
				responsMsg = new ResponseMsg();
				responsMsg.setRetMsg(resultJson);
			}
		} catch (Exception e) {
			TCLogUtils.e(e.toString());
			responsMsg = new ResponseMsg();
			responsMsg.setRetMsg("网络访问错误");
		}
		return responsMsg;
	}

	@Override
	protected void onCancelled(ResponseMsg result) {
		super.onCancelled(result);
		if (core != null)
			core.disConnect();
	}

	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (dialog != null) {
			dialog.show();
		} else {
			if (context != null) {
				dialog = DialogUtils.createLoadingDialog(context);
				dialog.show();
				dialog.setCanceledOnTouchOutside(false);
				// dialog.setOnCancelListener(new OnCancelListener() {
				//
				// @Override
				// public void onCancel(DialogInterface dialog) {
				// ApiSdkRequest.this.cancel(true);
				// }
				// });
			}
		}

		// preParams();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(ResponseMsg msg) {
		super.onPostExecute(msg);
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (paramsQuest != null) {
			paramsQuest.clear();
			paramsQuest = null;
		}
		if (msg.getRetCode() == Config.REQUEST_STATUS_CODE_SUC) {
			callBack.onSuccessed(msg);
		} else {
			callBack.onFailure(msg.getRetMsg());
		}
		// 此处存疑，是否手动调用性能更好
		// onCancelled();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
