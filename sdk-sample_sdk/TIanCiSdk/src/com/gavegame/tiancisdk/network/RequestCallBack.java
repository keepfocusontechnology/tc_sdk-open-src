package com.gavegame.tiancisdk.network;

/**
 * Created by Tianci on 15/9/22.
 */
public interface RequestCallBack {

	// 成功回调(参数为用户是否已绑定的code)
	void onSuccessed(int userBindCode);


	// 失败的回调
	void onFailure(ResponseMsg msg);
}
