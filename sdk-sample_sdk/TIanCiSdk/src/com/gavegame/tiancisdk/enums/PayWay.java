package com.gavegame.tiancisdk.enums;

/**
 * 四种支付渠道
 * 
 * @author Tianci
 *
 */

public enum PayWay {
	alipay("alipay"), // 支付宝
	wechat("wechat"), // 微信
	caihutong("caihutong"), // 财付通
	yinlian("yinlian"); // 银联

	final String payWay;

	PayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayway() {
		return payWay;
	}
}
