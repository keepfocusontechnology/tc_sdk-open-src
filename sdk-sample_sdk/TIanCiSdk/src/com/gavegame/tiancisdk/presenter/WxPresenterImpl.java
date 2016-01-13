package com.gavegame.tiancisdk.presenter;

import android.app.Activity;
import android.util.Log;

import com.gavegame.tiancisdk.activity.BaseActivity;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.view.IPayView;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WxPresenterImpl implements IPayPresenter, IWXAPIEventHandler {

	private final String app_id = "wx7f69866f179fa23e";

	private final String TAG = "WxPresenterImpl";

	private Activity context;
	private IPayView payView;
	private IWXAPI msgApi;


	public WxPresenterImpl(BaseActivity activity) {
		this.context = activity;
		this.payView = (IPayView) activity;
		msgApi = WXAPIFactory.createWXAPI(context, app_id);
	}

	@Override
	public void pay() {
		msgApi.registerApp("wx7f69866f179fa23e");

		PayReq request = new PayReq();
		request.appId = "wx7f69866f179fa23e";
		request.partnerId = "1900000109";
		request.prepayId = "1101000000140415649af9fc314aa427";
		request.packageValue = "Sign=WXPay";
		request.nonceStr = "1101000000140429eb40476f8896f4c9";
		request.timeStamp = "1398746574";
		request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
		msgApi.sendReq(request);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {

		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			TCLogUtils.e(TAG, String.valueOf(resp.errCode));
			// payView.payFailedAction();
		}

	}

}
