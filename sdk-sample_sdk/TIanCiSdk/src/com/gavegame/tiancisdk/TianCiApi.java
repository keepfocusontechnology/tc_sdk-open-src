package com.gavegame.tiancisdk;

import com.gavegame.tiancisdk.network.ApiSdkRequest;
import com.gavegame.tiancisdk.network.Method;
import com.gavegame.tiancisdk.network.NetworkUtils;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseBean;
import com.gavegame.tiancisdk.network.strategy.IParamsStrategy;
import com.gavegame.tiancisdk.network.strategy.LoginRoleStrategy;

import android.app.Activity;

public class TianCiApi {

	private Activity actiity;

	public TianCiApi(Activity activity) {
		this.actiity = activity;
	}

	private ResponseBean responseBean;

	private ApiSdkRequest apiSdkRequest;

	private IParamsStrategy strategy;

	/**
	 * 角色登陆
	 * 
	 * @param roleId
	 *            角色id
	 * @param serverId
	 *            角色所在服务器id
	 * @param callBack
	 *            访问接口回调
	 */
	public void roleAutoLogin(String roleId, String serverId,
			RequestCallBack callBack) {
		strategy = new LoginRoleStrategy(actiity, roleId, serverId);
		responseBean = new ResponseBean(actiity,
				NetworkUtils.getUrl(Config.REQUEST_PARAMS_LOGIN_ROLE),
				Method.POST, strategy, callBack);
		apiSdkRequest = ApiSdkRequest.newApiSdkRequest(responseBean);
		apiSdkRequest.execute();
	}
}
