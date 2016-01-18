package com.gavegame.tiancisdkdemo;

import java.util.Random;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.TCPayActivity;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.network.bean.WxpayEntity;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayActivity extends Activity {
	private final String TAG = "PayActivity";

	private IWXAPI api;

	String appId = "wx7f69866f179fa23e";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);

		api = WXAPIFactory.createWXAPI(this, appId);

		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// PayReq request = new PayReq();
				// request.appId = "wx7f69866f179fa23e";
				// request.partnerId = "10000100";
				// request.prepayId = "wx201601151418065482990a3e0045273659";
				// request.packageValue = "Sign=WXPay";
				// request.nonceStr = "6bf0639fd2022139c613e78915445524";
				// request.timeStamp = "1452838686";
				// request.sign = "FFE5B8FCBB5EABCFFF898A7186AAF25D";
				// api.sendReq(request);

				String roleId = "123";
				String cp_orderId = new Random().nextInt(10000) + 1 + "";
				String serverId = "152001";
				String amount = "0.01";

				TianCi.getInstance().getOrder(roleId, cp_orderId, serverId,
						amount, PayWay.wechat.getPayway(),
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg responseMsg) {

								TCLogUtils.e(TAG, responseMsg.toString());

								WxpayEntity entity = (WxpayEntity) responseMsg
										.getBaseOrder();
								TCLogUtils.e(TAG, entity.toString());
								// api.registerApp(entity.appId);
								PayReq request = new PayReq();
								request.appId = entity.appId;
								request.partnerId = entity.partnerId;
								request.prepayId = entity.prepayId;
								request.packageValue = entity.packageValue;
								request.nonceStr = entity.nonceStr;
								request.timeStamp = entity.timeStamp;
								request.sign = entity.sign;
								request.extData = "app data";
								api.sendReq(request);
							}

							@Override
							public void onFailure(String msg) {
								TCLogUtils.e(TAG, msg);
							}
						});
			}
		});
		Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
		checkPayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				Toast.makeText(PayActivity.this,
						String.valueOf(isPaySupported), Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
