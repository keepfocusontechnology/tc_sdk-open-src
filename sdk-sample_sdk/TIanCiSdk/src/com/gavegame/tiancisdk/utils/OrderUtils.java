package com.gavegame.tiancisdk.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 获取各个渠道订单信息工具类
 * 
 * @author Tianci
 *
 */
public final class OrderUtils {

	/**
	 * 银联 tn_url
	 */
	public static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

	public static void getTn() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String tn = null;
				InputStream is;
				try {

					String url = TN_URL_01;

					URL myURL = new URL(url);
					URLConnection ucon = myURL.openConnection();
					ucon.setConnectTimeout(120000);
					is = ucon.getInputStream();
					int i = -1;
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while ((i = is.read()) != -1) {
						baos.write(i);
					}

					tn = baos.toString();
					is.close();
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 获取支付宝的订单信息
	 * 
	 * @param subject
	 *            商品名
	 * @param body
	 *            商品信息
	 * @param price
	 *            支付金额
	 * @param orderId
	 *            订单id
	 * @param PARTNER
	 *            签约合作者身份ID
	 * @param SELLER
	 *            签约卖家支付宝账号
	 * @param notify_url服务器异步通知页面路径
	 *
	 * @return 订单信息
	 */
	public static String getAlipayOrderInfo(String subject, String body,
			String price, String orderId, String PARTNER, String SELLER,
			String notify_url) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// // 服务器异步通知页面路径
		// orderInfo += "&notify_url=" + "\"" +
		// "http://notify.msp.hk/notify.htm"
		// + "\"";
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

}
