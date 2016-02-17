package com.gavegame.tiancisdk.network.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gavegame.tiancisdk.network.bean.BaseOrder;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.order.entity.OrderEntity;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class GetOrderListStrategy implements IParamsStrategy {

	private final String TAG = "GetOrderListStrategy";

	public GetOrderListStrategy(String tcsso) {
		this.tcsso = tcsso;
	}

	public String tcsso;

	@Override
	public HashMap<String, Object> getParamsQuest() {
		HashMap<String, Object> paramsQuest = new HashMap<>();
		paramsQuest.put("tcsso", tcsso);
		return paramsQuest;
	}

	@Override
	public ResponseMsg resolveJson(String json) throws Exception {
		TCLogUtils.e(TAG, json);
		ResponseMsg responsMsg = new ResponseMsg();
		JSONObject jsonObject = new JSONObject(json);
		responsMsg.setRetCode(jsonObject.getInt("retcode"));
		responsMsg.setRetMsg(jsonObject.getString("retmsg"));

		JSONArray order_list = null;
		try {
			order_list = jsonObject.getJSONArray("order_list");
		} catch (JSONException e) {
			TCLogUtils.e(TAG, "获取订单列表解析错误：" + e.getMessage());
		}

		if (order_list.length() > 0) {
			List<BaseOrder> list = new ArrayList<BaseOrder>();
			for (int i = 0; i < order_list.length(); i++) {
				OrderEntity entity = new OrderEntity();
				JSONObject order = (JSONObject) order_list.get(i);
				entity.setOrder_amount(order.getString("amount"));
				entity.setOrder_time(order.getString("create_time"));
				entity.orderId = order.getString("id");
				entity.setPayway(order.getString("pay_type"));
				entity.setSuccessed(true);
				list.add(entity);
			}
			responsMsg.setOrderList(list);
		}

		return responsMsg;
	}
}
