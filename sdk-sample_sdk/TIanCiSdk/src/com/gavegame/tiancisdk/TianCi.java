package com.gavegame.tiancisdk;

import android.content.Context;

import com.gavegame.tiancisdk.network.ApiSdkRequest;
import com.gavegame.tiancisdk.network.Method;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseBean;

/**
 * Created by Tianci on 15/10/9.
 */
public class TianCi {

    private static Context mContext;

    static TianCi mTianCi;

    private ResponseBean responseBean;

    private ApiSdkRequest apiSdkRequest;

    public static void init(Context context) {
    	if(mContext != context){
            mTianCi = null;
        }
        if (mTianCi == null) {
            synchronized (TianCiSDK.class) {
                if (mTianCi == null) {
                    mTianCi = new TianCi(context);
                }
            }
        }
    }

    public static TianCi getInstance() {
        if (mTianCi == null) {
            throw new IllegalStateException("The TianCiSDK is not initialize!");
        }
        return mTianCi;
    }

    private TianCi(Context context) {
        this.mContext = context;
    }

    /**
     * 登陆
     *
     * @param account  账户名
     * @param psw      密码
     * @param callBack 登陆结果回调
     */
    public void login(String account, String psw, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_LOGIN, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(account, psw);
    }

    /**
     * 注册
     *
     * @param account  账户名
     * @param psw      密码l
     * @param callBack 登陆结果回调
     */
    public void register(String account, String psw, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_REGISTER, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(account, psw);
    }

    /**
     * 自动登陆
     *
     * @param autoLoginId 自动登陆的I
     * @param callBack    访问结果回调
     */
    public void autoLogin(String autoLoginId, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_AUTOLOGIN, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(autoLoginId);
    }

    /**
     * 角色自动登陆
     *
     * @param roleId   CP创建的角色ID
     * @param callBack 访问结果回调
     */
    public void roleAutoLogin(String roleId, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_LOGIN_ROLE, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(roleId);
    }

    /**
     * 创建订单
     * @param roleId   CP创建的角色ID
     * @param orderId  CP创建的订单ID
     * @param amount   订单金额
     * @param callBack 访问结果回调
     */
    public void createOrder(String roleId, String orderId, String amount, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_CREATE_ORDER, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(roleId, orderId, amount);
    }

    /**
     * 完成订单
     * @param roleId   CP创建的角色ID
     * @param orderId  CP创建的订单ID
     * @param amount   订单金额
     * @param callBack 访问结果回调
     */
    public void finishOrder(String roleId, String orderId, String amount, RequestCallBack callBack) {
        responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_FINISH_ORDER, Method.POST, callBack);
        apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
        apiSdkRequest.execute(roleId, orderId, amount);
    }

}
