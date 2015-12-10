package com.gavegame.tiancisdk.network;

import java.io.Serializable;

public class BaseOrder implements Serializable {
	// 订单号
	public String orderId;
	public String notify_url;
}
