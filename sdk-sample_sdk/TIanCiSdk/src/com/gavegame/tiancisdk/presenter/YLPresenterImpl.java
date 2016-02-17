package com.gavegame.tiancisdk.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.BaseActivity;
import com.gavegame.tiancisdk.activity.TCPayActivity;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayView;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class YLPresenterImpl implements IPayPresenter {

	private final String TAG = "YLPresenterImpl";

	private final String OFFICIALSERVERMODE = "00";
	private final String TESTSERVERMODE = "01";

	private IPayView payView;

	private Activity context;

	private String cp_orderId;

	private String amount;
	private String roleId;
	private String serverId;

	public YLPresenterImpl(BaseActivity activity) {
		this.payView = (IPayView) activity;
		this.context = activity;
		TianCi.init(activity);

		Intent intent = context.getIntent();
		amount = intent.getStringExtra("price");
		roleId = intent.getStringExtra("roleId");
		serverId = intent.getStringExtra("serverId");
		cp_orderId = intent.getStringExtra("cp_orderId");

	}

	String tn;

	@Override
	public void pay() {
		TianCi.getInstance().getOrder(roleId, cp_orderId, serverId, amount,
				PayWay.yinlian.getPayway(), new RequestCallBack() {

					@Override
					public void onSuccessed(ResponseMsg responseMsg) {
						// “00” – 银联正式环境
						// “01” – 银联测试环境，该环境中不发生真实交易
						tn = responseMsg.getRetMsg();
						TCLogUtils.e(TAG, "tn = " + responseMsg.getRetMsg());
						// try {
						// UPPayAssistEx.startPay(context, null, null,
						// responseMsg.getRetMsg(), serverMode);
						// } catch (Throwable e) {
						// e.printStackTrace();
						// TCLogUtils.e(TAG, e.getMessage());
						// TCLogUtils.e(TAG, e.getLocalizedMessage());
						// }
						UPPayAssistEx.startPay(context, null, null, tn, OFFICIALSERVERMODE);
					}

					@Override
					public void onFailure(String msg) {
						TCLogUtils.e(TAG, msg);
					}
				});



		// TianCi.getInstance().testGetTn(new RequestCallBack() {
		//
		// @Override
		// public void onSuccessed(ResponseMsg responseMsg) {
		// TCLogUtils.e(TAG, responseMsg.toString());
		// }
		//
		// @Override
		// public void onFailure(String msg) {
		// // “00” – 银联正式环境
		// // “01” – 银联测试环境，该环境中不发生真实交易
		// String serverMode = "01";
		// UPPayAssistEx.startPay(context, null, null, msg,
		// serverMode);
		// }
		// });

	}

}
