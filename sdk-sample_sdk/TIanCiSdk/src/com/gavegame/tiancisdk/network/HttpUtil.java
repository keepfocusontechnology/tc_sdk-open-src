package com.gavegame.tiancisdk.network;


import com.gavegame.tiancisdk.utils.TCLogUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Tianci on 15/9/15.
 */
public class HttpUtil {

    static final String TAG = HttpUtil.class.getSimpleName();
    private static final String SERVLET_POST = "POST";
    private static final String SERVLET_GET = "GET";
    private static final String SERVLET_DELETE = "DELETE";
    private static final String SERVLET_PUT = "PUT";
    private static final int TIMEOUT = 10000;


    private static String prepareParam(Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        if (paramMap.isEmpty()) {
            return "";
        } else {
            for (String key : paramMap.keySet()) {
                Object value = (Object) paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }

    public static String doPost(String urlStr, Map<String, Object> paramMap) throws Exception {

        HttpURLConnection conn = null;
//        try {
        URL url = new URL(urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_POST);
        String paramStr = prepareParam(paramMap);
        TCLogUtils.d("url:"+urlStr);
        TCLogUtils.d("request" + paramStr);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
        conn.connect();
        OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
        os.flush();
        os.close();
        return dealResponseResult(conn.getInputStream());
       /* } catch (SocketTimeoutException ste) {
            TCLog.e(TAG, "连接超时");
        } catch (IOException ioe) {
            TCLog.e(TAG, "请求出错");
        } catch (Exception e) {
            TCLog.e(TAG, e.toString());
        } finally {
            if (conn != null) conn.disconnect();
        }
        return null;*/
    }

    public static String dealResponseResult(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    public static String doGet(String urlStr, Map<String, Object> paramMap) throws Exception {
        String result = "";
        HttpURLConnection conn = null;
//        try {
        String paramStr = prepareParam(paramMap);
        if (paramStr == null || paramStr.trim().length() < 1) {

        } else {
            urlStr += "&" + paramStr;
        }
        TCLogUtils.d("url:"+urlStr);
        TCLogUtils.d("request"+paramStr);
        URL url = new URL(urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_GET);
        conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
        conn.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            result += line;
        }
        br.close();
       /* } catch (SocketTimeoutException ste) {
            TCLog.e(TAG, "连接超时");
        } catch (IOException ioe) {
            TCLog.e(TAG, "请求出错");
        } catch (Exception e) {
            TCLog.e(TAG, e.toString());
        } finally {
            if (conn != null) conn.disconnect();
        }*/
        return result;
    }

    public static void doPut(String urlStr, Map<String, Object> paramMap) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(SERVLET_PUT);
        String paramStr = prepareParam(paramMap);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String result = "";
        while ((line = br.readLine()) != null) {
            result += line;
        }
        br.close();

    }

    public static void doDelete(String urlStr, Map<String, Object> paramMap) throws Exception {
        String paramStr = prepareParam(paramMap);
        if (paramStr == null || paramStr.trim().length() < 1) {

        } else {
            urlStr += "?" + paramStr;
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(SERVLET_DELETE);
        //屏蔽掉的代码是错误的，java.net.ProtocolException: HTTP method DELETE doesn't support output
/*      OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
        os.close();  */

        if (conn.getResponseCode() == 200) {
            TCLogUtils.d(TAG, "请求成功");
        } else {

        }
    }
}
