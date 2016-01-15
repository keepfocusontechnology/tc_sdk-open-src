package com.gavegame.tiancisdk.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.BaseActivity;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.network.bean.WxpayEntity;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayView;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

public class WxPresenterImpl implements IPayPresenter {

	private final String TAG = "WxPresenterImpl";

	private Activity context;
	private IPayView payView;
	private IWXAPI msgApi;

	private String cp_orderId;
	private String amount;
	private String roleId;
	private String serverId;

	public WxPresenterImpl(BaseActivity activity) {
		this.context = activity;
		this.payView = (IPayView) activity;
		Intent intent = context.getIntent();
		amount = intent.getStringExtra("price");
		roleId = intent.getStringExtra("roleId");
		serverId = intent.getStringExtra("serverId");
		cp_orderId = intent.getStringExtra("cp_orderId");
	}

	@Override
	public void pay() {

		TianCi.getInstance().getOrder(roleId, cp_orderId, serverId, amount,
				PayWay.wechat.getPayway(), new RequestCallBack() {

					@Override
					public void onSuccessed(ResponseMsg responseMsg) {

						TCLogUtils.e(TAG, responseMsg.toString());

						WxpayEntity entity = (WxpayEntity) responseMsg
								.getBaseOrder();
						TCLogUtils.e(TAG, entity.toString());
						msgApi = WXAPIFactory.createWXAPI(context, null);
						msgApi.registerApp(entity.appId);

						PayReq request = new PayReq();
						request.appId = entity.appId;
						request.partnerId = entity.partnerId;
						request.prepayId = entity.prepayId;
						request.packageValue = entity.packageValue;
						request.nonceStr = entity.nonceStr;
						request.timeStamp = entity.timeStamp;
						request.sign = entity.sign;
						msgApi.sendReq(request);
					}

					@Override
					public void onFailure(String msg) {
						TCLogUtils.e(TAG, msg);
					}
				});
	}

}
