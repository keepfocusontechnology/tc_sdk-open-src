package com.gavegame.tiancisdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tianci on 15/9/29.
 */
public class NormalUtils {

	/**
	 * 判断字符串是否为手机
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobile(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断字符串是否为纯数字
	 * 
	 * @param str
	 * @return true代表是，false代表不是
	 */
	public static boolean isAllNum(String str) {
		Pattern p = Pattern.compile("^\\d+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 字符串转化为时间戳
	 * @param user_time
	 * @return
	 */
	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		Date d;
		try {

			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}
}
