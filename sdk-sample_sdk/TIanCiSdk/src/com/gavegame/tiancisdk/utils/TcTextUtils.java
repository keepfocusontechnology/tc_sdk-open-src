package com.gavegame.tiancisdk.utils;

public class TcTextUtils {

	public static boolean isEmpty(String str) {
		boolean a = str == null ? true : false;
		boolean b = "".equals(str) ? true : false;
		return a && b;
	}

	public static boolean isEmpty(CharSequence s) {
		return isEmpty(s + "");
	}

}
