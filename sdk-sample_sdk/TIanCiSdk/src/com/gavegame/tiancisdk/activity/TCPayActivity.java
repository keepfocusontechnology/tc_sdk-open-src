package com.gavegame.tiancisdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.alipay.AliPayActivity;
import com.gavegame.tiancisdk.enums.PayWay;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.widget.ImageRadiobutton;
import com.gavegame.tiancisdk.widget.ImageRadiobutton.RadioButtonCheckedListener;

public class TCPayActivity extends BaseActivity {

	private final String TAG = "TCPayActivity";

	private ImageRadiobutton pay_alipay;
	private ImageRadiobutton pay_wechat;
	private ImageRadiobutton pay_bank;
	private ImageRadiobutton pay_cht;

	// 缓存上次选择的radioButton
	private ImageRadiobutton cache;

	private PayWay payWay = PayWay.alipay;

	@Override
	void initId() {
		pay_alipay = (ImageRadiobutton) findViewById(R.id.pay_alipay);
		pay_wechat = (ImageRadiobutton) findViewById(R.id.pay_wechat);
		pay_bank = (ImageRadiobutton) findViewById(R.id.pay_bank);
		pay_cht = (ImageRadiobutton) findViewById(R.id.pay_cht);
		ImageView iv_back = (ImageView) findViewById(R.id.iv_pay_back);
		//返回键
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		final ImageRadiobutton irbs[] = { pay_alipay, pay_wechat, pay_bank,
//				pay_cht };
		pay_alipay.setChecked();
		cache = pay_alipay;
		pay_alipay.setCheckListener(new RadioButtonCheckedListener() {
			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.alipay;
				pay_alipay.setChecked();
				if (cache != null && cache != pay_alipay)
					cache.setUnChecked();
				cache = pay_alipay;
			}
		});

		pay_wechat.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				payWay = PayWay.wechat;
				// setAllUnChecked(irbs);
				pay_wechat.setChecked();
				if (cache != null && cache != pay_wechat)
					cache.setUnChecked();
				cache = pay_wechat;
			}
		});

		pay_bank.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.bank;
				pay_bank.setChecked();
				if (cache != null && cache != pay_bank)
					cache.setUnChecked();
				cache = pay_bank;
			}
		});

		pay_cht.setCheckListener(new RadioButtonCheckedListener() {

			@Override
			public void onCheckedChanged(boolean isChecked) {
				// setAllUnChecked(irbs);
				payWay = PayWay.caihutong;
				pay_cht.setChecked();
				if (cache != null && cache != pay_cht)
					cache.setUnChecked();
				cache = pay_cht;
			}
		});

		findViewById(R.id.bt_immediately_login).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						TCLogUtils.showToast(getApplicationContext(), "支付方式为："
								+ payWay.toString());
						if (payWay == PayWay.alipay) {
							Intent intent = new Intent(TCPayActivity.this,
									AliPayActivity.class);
							intent.putExtra("subject", "商品名称");
							intent.putExtra("subject_desc", "商品详情");
							intent.putExtra("price", "0.01");
							startActivityForResult(intent, 0);
						}
					}
				});

		findViewById(R.id.iv_pay_rechange_notes).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(TCPayActivity.this,
								TCOrderActivity.class));
					}
				});

	}

	/**
	 * 设置所有的radioButton为未选中
	 * 
	 * @param objs
	 */
	private void setAllUnChecked(ImageRadiobutton[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].isChecked())
				objs[i].setUnChecked();
		}
	}

	@Override
	int initView() {
		return R.layout.tcsdk_pay_view;
	}

	@Override
	boolean hasActionBar() {
		return false;
	}

	@Override
	void initData(Bundle saveInstance) {
		Intent intent = getIntent();
	}
}
