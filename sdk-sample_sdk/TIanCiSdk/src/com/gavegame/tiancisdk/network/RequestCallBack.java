package com.gavegame.tiancisdk.network;

import com.gavegame.tiancisdk.network.bean.ResponseMsg;

/**
 * Created by Tianci on 15/9/22.
 */
public interface RequestCallBack {

	// 成功回调(将json解析后的实体对象返回)
	void onSuccessed(ResponseMsg responseMsg);


	// 失败的回调
	void onFailure(String msg);
}
