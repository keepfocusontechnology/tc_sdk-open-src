package com.gavegame.tiancisdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tianci on 15/9/29.
 */
public class NormalUtils {

    /**
     * 判断字符串是否为手机
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
