package com.gavegame.tiancisdk.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.order.OrderManager;
import com.gavegame.tiancisdk.order.entity.OrderEntity;
import com.gavegame.tiancisdk.widget.CommonAdapter;
import com.gavegame.tiancisdk.widget.ViewHolder;

public class TCOrderActivity extends BaseActivity {

	private ListView lv_order;

	private List<OrderEntity> list;

	private final String ORDER_AMOUNT = "订单金额:";
	private final String ORDER_ID = "订单号:";
	private final String ORDER_PAY_WAY = "支付方式:";
	private final String ORDER_VALID = "充值成功";
	private final String ORDER_INVALID = "充值未成功";

	@Override
	void initId() {
		lv_order = (ListView) findViewById(R.id.lv_order_list);
		ImageView iv_closed = (ImageView) findViewById(R.id.iv_pay_closed);
		iv_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	void initData(Bundle saveInstance) {

		Intent intent = getIntent();
		list = (List<OrderEntity>) intent.getSerializableExtra("order_list");
		// for (int i = 0; i < 10; i++) {
		// list.add(new OrderEntity("123451231211231" + i, "12" + i,
		// "2015-12-11-13:1" + i, PayWay.alipay, true));
		// }
		CommonAdapter<OrderEntity> adapter = new CommonAdapter<OrderEntity>(
				this, list, R.layout.order_record_page_adapter) {

			@Override
			public void convert(ViewHolder holder, OrderEntity t) {
				holder.setText(R.id.tv_order_amount,
						ORDER_AMOUNT + t.getOrder_amount() + "元");
				holder.setText(R.id.tv_order_id, ORDER_ID + t.orderId);
				holder.setText(R.id.tv_order_payway, ORDER_PAY_WAY
						+ getPayWayDesc(t.getPayway()));
				holder.setText(R.id.tv_order_status,
						t.isSuccessed() ? ORDER_VALID : ORDER_INVALID);
				holder.setText(R.id.tv_order_time, t.getOrder_time());
				// holder.setText(R.id.tv_order_time,
				// NormalUtils.getTime(t.getOrder_time()));
			}
		};

		OrderManager orderManager = OrderManager.getInstance();
		orderManager.show(lv_order, list, adapter);
	}

	String getPayWayDesc(PayWay payway) {
		if (payway == PayWay.alipay) {
			return "支付宝";
		} else if (payway == PayWay.wechat) {
			return "微信";
		} else if (payway == PayWay.yinlian) {
			return "银联";
		} else if (payway == PayWay.caihutong) {
			return "财付通";
		}
		return null;
	}

	@Override
	int initView() {
		return R.layout.order_record_page;
	}

	@Override
	boolean hasActionBar() {
		return false;
	}

}
