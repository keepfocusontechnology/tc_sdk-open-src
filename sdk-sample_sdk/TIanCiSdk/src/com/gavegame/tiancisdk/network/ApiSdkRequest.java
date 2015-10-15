package com.gavegame.tiancisdk.network;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.Platform;
import com.gavegame.tiancisdk.utils.DialogUtils;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Tianci on 15/9/22.
 */
@SuppressLint("NewApi") public class ApiSdkRequest extends AsyncTask<String, Void, ResponseMsg> {

    private static final String loginUrl = Config.SERVER + "/index.php?g=mobile&m=login&a=%s";
    private static final String orderUri = Config.SERVER + "/index.php?g=mobile&m=order&a=%s";
    private ResponseMsg responsMsg;

    private WeakReference<Context> mContextRef;

    private RequestCallBack callBack;
    private Method method;
    private String paramsUri;
    private Context context;
    private HashMap<String, Object> paramsQuest;

    private Dialog dialog;

//    public ApiSdkRequest(Context context, String paramsUri, Method method, RequestCallBack callBack) {
//        this.paramsUri = paramsUri;
//        this.callBack = callBack;
//        this.method = method;
//        mContextRef = new WeakReference<>(context);
//        this.context = mContextRef.get();
//    }

    public static ApiSdkRequest newApiSdkRequest(ResponseBean responseBean) {
        if (responseBean == null) return null;
        return new ApiSdkRequest(responseBean);
    }

    private ApiSdkRequest(ResponseBean responseBean) {
        this.paramsUri = responseBean.getParamsUri();
        this.callBack = responseBean.getCallBack();
        this.method = responseBean.getMethod();
        this.context = responseBean.getContext();
    }


    @Override
    protected ResponseMsg doInBackground(String... params) {

        int resultCode = 0;
        String resultJson = null;
        String tcsso = null;

        if (paramsUri.equals(Config.REQUEST_PARAMS_AUTOLOGIN)) {
            paramsQuest.put("deviceid", params[0]);
        } else if (paramsUri.equals(Config.REQUEST_PARAMS_LOGIN) || paramsUri.equals(Config.REQUEST_PARAMS_REGISTER)) {
            paramsQuest.put("user_login", params[0]);
            paramsQuest.put("user_pass", params[1]);
        } else if (paramsUri.equals(Config.REQUEST_PARAMS_LOGIN_ROLE)) {
            tcsso = (String) SharedPreferencesUtils.getParam(context, Config.USER_TCSSO, "");
//            if (tcsso != null && !tcsso.equals("")) {
//                paramsQuest.put("tcsso", tcsso);
//            } else {
//                return Config.REQUEST_STATUS_CODE_TCSSO_ISNULL;
//            }
            paramsQuest.put("tcsso", tcsso);
            paramsQuest.remove("useragent");
            paramsQuest.remove("deviceid");
            paramsQuest.put("cp_role", Integer.valueOf(params[0]));
        } else if (paramsUri.equals(Config.REQUEST_PARAMS_CREATE_ORDER) ||
                paramsUri.equals(Config.REQUEST_PARAMS_FINISH_ORDER)) {
            tcsso = (String) SharedPreferencesUtils.getParam(context, Config.USER_TCSSO, "");
//            if (tcsso != null && !tcsso.equals("")) {
//                paramsQuest.put("tcsso", tcsso);
//            } else {
//                //TODO 返回tcsso为空的response
//                responsMsg = new ResponseMsg();
//                responsMsg.setRetCode(Config.REQUEST_STATUS_CODE_TCSSO_ISNULL);
//                return responsMsg;
//            }
            paramsQuest.put("tcsso",tcsso);
            paramsQuest.remove("useragent");
            paramsQuest.remove("deviceid");
            paramsQuest.put("cp_role", Integer.valueOf(params[0]));
            paramsQuest.put("cp_order", params[1]);
            paramsQuest.put("amount",Double.valueOf(params[2]));
        }
        String uri;
        if (paramsUri.equals(Config.REQUEST_PARAMS_FINISH_ORDER) ||
                paramsUri.equals(Config.REQUEST_PARAMS_CREATE_ORDER)) {
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
//               HttpUtil.doPut(uri, paramsQuest);
            } else if (method == Method.DELETE) {
//               HttpUtil.doDelete(uri, paramsQuest);
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
     * @param result 服务器返回的json串
     * @return json串中的结果码
     * @throws Exception
     */
    private ResponseMsg getJsonObjcet(String result) throws Exception {
        responsMsg = new ResponseMsg();

        JSONObject jsonObject = new JSONObject(result);

        if (result.contains("\"tcsso\"")) {
            responsMsg.setTcsso(jsonObject.getString(Config.USER_TCSSO));
            SharedPreferencesUtils.setParam(context, Config.USER_TCSSO, jsonObject.getString(Config.USER_TCSSO));
        }
        if(result.contains("\"tc_order\"")){
            responsMsg.setOrderId(jsonObject.getInt("tc_order"));
            SharedPreferencesUtils.setParam(context, Config.TC_ORDER_ID, jsonObject.getInt(Config.TC_ORDER_ID));
        }
        responsMsg.setRetCode(jsonObject.getInt("retcode"));
        responsMsg.setRetMsg(jsonObject.getString("retmsg"));
        return responsMsg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = DialogUtils.createLoadingDialog(context);
        dialog.show();

        int channelId = (int) SharedPreferencesUtils.getParam(context, Platform.TIANCI_CHANNEL_ID, 0);
        int gameId = (int) SharedPreferencesUtils.getParam(context, Platform.TIANCI_GAME_ID, 0);
        int deviceType = (int) SharedPreferencesUtils.getParam(context, Platform.TIANCI_DEVICE_TYPE, 0);
        int serverId = (int) SharedPreferencesUtils.getParam(context, Platform.TIANCI_SERVER_ID, 0);
        String useragent = (String) SharedPreferencesUtils.getParam(context, Platform.TIANCI_USER_AGENT, "");
        String deviceId = (String) SharedPreferencesUtils.getParam(context, Platform.TIANCI_DEVICE_ID, "");

        paramsQuest = new HashMap<>();
        paramsQuest.put("deviceid", deviceId);
        paramsQuest.put("gameid", gameId);
        paramsQuest.put("serverid", serverId);
        paramsQuest.put("channelid", channelId);
        paramsQuest.put("useragent", useragent);
        paramsQuest.put("device_type", deviceType);
    }

    @SuppressLint("NewApi") @Override
    protected void onPostExecute(ResponseMsg msg) {
        super.onPostExecute(msg);
        if (msg.getRetCode() == Config.REQUEST_STATUS_CODE_SUC) {
            callBack.onSuccessed();
        } else {
            callBack.onFailure(msg);
        }
        dialog.dismiss();
        paramsQuest.clear();
        paramsQuest = null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }


}
