package com.gavegame.tiancisdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.provider.ContactsContract.Data;

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
	 * 秒数转化为时间
	 * 
	 * @param user_time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String convert(String str) {
		long mill = Long.valueOf(str);
		Date date = new Date(mill*1000l);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
}
