package com.gavegame.tiancisdk.order.entity;

import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.bean.BaseOrder;

public class OrderEntity extends BaseOrder {

	// 订单金额
	private String order_amount;
	// 订单提交时间
	private String order_time;
	// 支付方式
	private PayWay payway;
	// 订单支付状态 integer 0 代表不成功 1 代表成功
	public static final int ORDER_SUCCESSED = 1;
	public static final int ORDER_UNSUCCESSED = 0;
	// 订单支付是否成功
	private boolean isSuccessed;

	public OrderEntity() {
	}

	public OrderEntity(String order_id, String order_amount, String order_time,
			PayWay payway, boolean isSuccessed) {
		super();
		super.orderId = order_id;
		this.order_time = order_time;
		this.order_amount = order_amount;
		this.payway = payway;
		this.isSuccessed = isSuccessed;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public PayWay getPayway() {
		return payway;
	}

	public void setPayway(PayWay payway) {
		this.payway = payway;
	}

	public boolean isSuccessed() {
		return isSuccessed;
	}

	public void setSuccessed(boolean isSuccessed) {
		this.isSuccessed = isSuccessed;
	}

	@Override
	public String toString() {
		return "OrderEntity [order_id=" + orderId + ", order_amount="
				+ order_amount + ", order_time=" + order_time + ", payway="
				+ payway + ", isSuccessed=" + isSuccessed + "]";
	}
}
