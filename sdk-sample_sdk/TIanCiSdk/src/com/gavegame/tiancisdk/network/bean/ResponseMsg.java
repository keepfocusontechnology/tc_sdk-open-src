package com.gavegame.tiancisdk.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tianci on 15/9/24.
 */
public class ResponseMsg implements Serializable {

	private int retCode;
	private String retMsg;
	private String tcsso;
	private int orderId;
	private int bindCode;
	private BaseOrder baseOrder;
	private List<BaseOrder> orderList;

	public void setOrderList(List<BaseOrder> orderList) {
		this.orderList = orderList;
	}

	public List<BaseOrder> getOrderList() {
		return orderList;
	}

	public BaseOrder getBaseOrder() {
		return baseOrder;
	}

	public void setBaseOrder(BaseOrder baseOrder) {
		this.baseOrder = baseOrder;
	}

	public void setBindCode(int bindCode) {
		this.bindCode = bindCode;
	}

	public int getBindCode() {
		return bindCode;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getTcsso() {
		return tcsso;
	}

	public void setTcsso(String tcsso) {
		this.tcsso = tcsso;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "ResponseMsg [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", tcsso=" + tcsso + ", orderId=" + orderId + ", bindCode="
				+ bindCode + ", baseOrder=" + baseOrder + ", orderList="
				+ orderList + "]";
	}
}
