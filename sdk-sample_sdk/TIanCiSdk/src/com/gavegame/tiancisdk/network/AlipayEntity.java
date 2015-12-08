package com.gavegame.tiancisdk.network;

public class AlipayEntity extends BaseOrder {
	// 签约合作者身份id
	public String pantner;
	// 签约卖家的支付宝账号
	public String seller;
	// 商品名称
	public String subject;
	// 商品详情
	public String body;
	// 支付金额
	public String total_fee;
	// 私钥
	public String rsa_private;
	// 公钥
	public String rsa_public;
}
