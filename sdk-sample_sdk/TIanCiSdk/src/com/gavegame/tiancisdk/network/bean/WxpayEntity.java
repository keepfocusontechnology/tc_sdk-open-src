package com.gavegame.tiancisdk.network.bean;

@SuppressWarnings("serial")
public class WxpayEntity extends BaseOrder {

	@Override
	public String toString() {
		return "WxpayEntity [appId=" + appId + ", partnerId=" + partnerId
				+ ", prepayId=" + prepayId + ", packageValue=" + packageValue
				+ ", nonceStr=" + nonceStr + ", timeStamp=" + timeStamp
				+ ", sign=" + sign + "]";
	}
	public String appId;
	public String partnerId;
	public String prepayId;
	public String packageValue;
	public String nonceStr;
	public String timeStamp;
	public String sign;
}
