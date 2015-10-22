package com.gavegame.tiancisdk.paysdk.wxpay;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiXinPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        Log.e(TAG, "onPayFinish, 回调Acticity = " + this.toString());
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		Log.e(TAG, "onPayFinish, successdCode = " + req.toString());
	}

	@Override
	public void onResp(BaseResp resp) {
		TCLogUtils.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			if(resp.errCode == 0){
				builder.setMessage("支付成功，游戏在此回调");
			}else{
				builder.setMessage("支付失败，错误码：code=" + String.valueOf(resp.errCode));
			}
//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp. +";code=" + String.valueOf(resp.errCode)));
			builder.show();
		}
	}
}