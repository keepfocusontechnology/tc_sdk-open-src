package com.gavegame.tiancisdk;

/**
 * Created by Tianci on 15/9/15.
 */
public interface Config {
    String POST_URL = "http://fuzai.gavegame.com/";
    String SERVER = "http://fuzai.gavegame.com";

    String REQUEST_PARAMS_REGISTER = "reg_post";
    String REQUEST_PARAMS_LOGIN = "login_post";
    String REQUEST_PARAMS_AUTOLOGIN = "login_auto";
    String REQUEST_PARAMS_LOGIN_ROLE = "login_role";
    String REQUEST_PARAMS_CREATE_ORDER = "get_order";
    String REQUEST_PARAMS_FINISH_ORDER = "finish_order";


    int REQUEST_STATUS_CODE_SUC = 200;
    int REQUEST_STATUS_CODE_FAILURE = 300;
    int REQUEST_STATUS_CODE_USERNAME_ILLEGAL = 1;
    int REQUEST_STATUS_CODE_PSW_ILLEGAL = 2;
    int REQUEST_STATUS_CODE_GAMEID_ILLEGAL = 3;
    int REQUEST_STATUS_CODE_SERVERID_ILLEGAL = 4;
    int REQUEST_STATUS_CODE_DEVICETYPE_ILLEGAL = 5;
    int REQUEST_STATUS_CODE_SUC_USERAGAENT_ILLEGAL = 6;
    int REQUEST_STATUS_CODE_SUC_CHANNELID_ILLEGAL = 7;
    int REQUEST_STATUS_CODE_SUC_USER_REPEAT = 8;
    int REQUEST_STATUS_CODE_USERNAME_PSW_ERROR = 9;
    int REQUEST_STATUS_CODE_NEWWORK_ERROR = 10;
    int REQUEST_STATUS_CODE_TCSSO_ISNULL = 11;
    

    String STATUS_SUCCESSD = "成功";
    String STATUS_USERNAME_ILLEGAL = "用户名不合法";
    String STATUS_CODE_PSW_ILLEGAL = "密码不合法";
    String STATUS_CODE_GAMEID_ILLEGAL = "游戏ID配置不正确";
    String STATUS_CODE_SERVERID_ILLEGAL = "服务器ID配置不正确";
    String STATUS_CODE_DEVICETYPE_ILLEGAL = "设备类型配置不正确";
    String STATUS_CODE_SUC_USERAGAENT_ILLEGAL = "用户标识不能为空";
    String STATUS_CODE_SUC_CHANNELID_ILLEGAL = "渠道ID不存在";
    String STATUS_CODE_SUC_USER_REPEAT = "该用户名已存在";
    String STATUS_CODE_USERNAME_PSW_ERROR = "用户名和密码错误";
    String STATUS_CODE_NEWWORK_ERROR = "网络访问异常";
    String STATUS_CODE_TCSSO_ISNULL = "无此用户的tcsso验证码";

    int REQUEST_LOGIN = 0x0012;
    int REQUEST_REGISTER = 0x0013;
    int REQUEST_AUTOLOGIN = 0x0014;

    String REQUEST_ACTION = "request_action";
    
    String USER_TCSSO = "tcsso";

    String TC_ORDER_ID = "tc_order";
}
