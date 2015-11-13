package com.gavegame.tiancisdk;

import android.content.Context;

import com.gavegame.tiancisdk.network.ApiSdkRequest;
import com.gavegame.tiancisdk.network.Method;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseBean;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;

/**
 * Created by Tianci on 15/10/9.
 */
public class TianCi {

	private static Context mContext;

	static TianCi mTianCi;

	private ResponseBean responseBean;

	private ApiSdkRequest apiSdkRequest;

	public static void init(Context context) {
		if (mContext != context) {
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
	 * 返回登录验证串
	 * 
	 * @return
	 */
	public String getTcsso() {
		return (String) SharedPreferencesUtils.getParam(mContext,
				Config.USER_TCSSO, "");
	}

	/**
	 * 登陆
	 *
	 * @param account
	 *            账户名
	 * @param psw
	 *            密码
	 * @param callBack
	 *            登陆结果回调
	 */
	public void login(String account, String psw, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext, Config.REQUEST_PARAMS_LOGIN,
				Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(account, psw);
	}

	/**
	 * 注册
	 *
	 * @param account
	 *            账户名
	 * @param psw
	 *            密码l
	 * @param callBack
	 *            登陆结果回调
	 */
	public void register(String account, String psw, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_REGISTER, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(account, psw);
	}

	/**
	 * 游客自动登陆
	 *
	 * @param autoLoginId
	 *            自动登陆的I
	 * @param callBack
	 *            访问结果回调
	 */
	public void autoLogin(RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_AUTOLOGIN, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute();
	}

	/**
	 * 角色自动登陆
	 *
	 * @param roleId
	 *            CP创建的角色ID
	 * @param callBack
	 *            访问结果回调
	 */
	public void roleAutoLogin(String roleId, String serverId,
			RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_LOGIN_ROLE, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(roleId, serverId);
	}

	/**
	 * 创建订单
	 * 
	 * @param roleId
	 *            CP创建的角色ID
	 * @param orderId
	 *            CP创建的订单ID
	 * @param amount
	 *            订单金额
	 * @param callBack
	 *            访问结果回调
	 */
	public void createOrder(String roleId, String orderId, String serverId,
			String amount, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_CREATE_ORDER, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(roleId, orderId, serverId, amount);
	}

	/**
	 * 完成订单
	 * 
	 * @param roleId
	 *            CP创建的角色ID
	 * @param orderId
	 *            CP创建的订单ID
	 * @param amount
	 *            订单金额
	 * @param callBack
	 *            访问结果回调
	 */
	public void finishOrder(String roleId, String orderId, String serverId,
			String amount, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_FINISH_ORDER, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(roleId, orderId, serverId, amount);
	}

	/**
	 * 获取验证码
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param callBack
	 *            访问结果回调
	 */
	public void getCaptcha(String phoneNum, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_GET_NUMBER, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(phoneNum);
	}

	/**
	 * 手机注册
	 * 
	 * @param mobileNum
	 *            电话号码
	 * @param psw
	 *            密码
	 * @param captchaCode
	 *            验证码
	 * @param callBack
	 *            访问结果回调
	 */
	public void mobileRegister(String mobileNum, String psw,
			String captchaCode, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_MOBILE_REGISTER, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(mobileNum, psw, captchaCode);
	}

	/**
	 * 用户绑定
	 * 
	 * @param mobileNum
	 *            手机号码
	 * @param captcha
	 *            验证码
	 * @param callBack
	 *            访问结果回调
	 */
	public void userBind(String mobileNum, String captcha,
			RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_USER_BIND, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(mobileNum, captcha);
	}

	/**
	 * 判断账户是否绑定
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param callBack
	 *            访问结果回调
	 */
	public void isBind(String userAccount, RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_CHECK_BIND, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(userAccount);
	}

	/**
	 * 忘记密码
	 * 
	 * @param mobileNum
	 *            手机号码
	 * @param captcha
	 *            验证码
	 * @param callBack
	 *            访问结果回调
	 */
	public void forgetPsw(String mobileNum, String captcha,
			RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_FORGET_PASS, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(mobileNum, captcha);
	}

	/**
	 * 设置密码
	 * 
	 * @param mobileNum
	 *            电话号码
	 * @param captcha
	 *            验证码
	 * @param userPsw
	 *            用户设置的新密码
	 * @param callBack
	 *            回调
	 */
	public void setPsw(String mobileNum, String captcha, String userPsw,
			RequestCallBack callBack) {
		responseBean = new ResponseBean(mContext,
				Config.REQUEST_PARAMS_SET_PASS, Method.POST, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute(mobileNum, captcha, userPsw);
	}

}
