//package com.gavegame.tiancisdk.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//
//import com.gavegame.tiancisdk.Config;
//import com.gavegame.tiancisdk.R;
//import com.gavegame.tiancisdk.TianCi;
//import com.gavegame.tiancisdk.network.ApiSdkRequest;
//import com.gavegame.tiancisdk.network.RequestCallBack;
//import com.gavegame.tiancisdk.network.ResponseBean;
//import com.gavegame.tiancisdk.network.ResponseMsg;
//import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
//import com.gavegame.tiancisdk.utils.TCLogUtils;
//
///**
// * Created by Tianci on 15/9/25.
// */
//public class LoginPageActivity extends BaseActivity {
//    private final String TAG = LoginPageActivity.class.getSimpleName();
//    private EditText et_account;
//    private EditText et_psw;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        TianCi.init(this);
//    }
//
//    @Override
//    public String getTag() {
//        return TAG;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.login_page;
//    }
//
//    @Override
//    protected String[] getTitleText() {
//        return new String[]{"登录"};
//    }
//
//    @Override
//    protected void submit() {
//
//    }
//
//    private ResponseBean responseBean;
//    private ApiSdkRequest apiSdkRequest;
//
//    private void initView() {
//        et_psw = (EditText) contentView.findViewById(R.id.et_login_psw);
//        et_account = (EditText) contentView.findViewById(R.id.et_login_account);
//
//        findViewById(R.id.tv_login_auto).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                autoLogin("123");
//            }
//        });
//        findViewById(R.id.tv_login_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (et_account.getText().toString().length() == 0 || et_psw
//                        .getText().toString().length() == 0) {
//                    TCLogUtils.toastShort(context, "账号或密码不能为空!");
//                } else {
//                    login(et_account.getText().toString(), et_psw.getText().toString());
//                }
//            }
//        });
//        findViewById(R.id.tv_login_resgister).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(LoginPageActivity.this, RegisterPageActivity.class), Config.REQUEST_STATUS_CODE_SUC);
//            }
//        });
//    }
//
//    private void login(final String account,final String psw) {
//        TianCi.getInstance().login(account, psw, new RequestCallBack() {
//            @Override
//            public void onSuccessed() {
//                TCLogUtils.toastShort(context, "成功");
//                Intent data = getIntent();
//                data.putExtra("tcsso", (String)SharedPreferencesUtils.getParam(context, Config.USER_TCSSO, ""));
//                setResult(Config.REQUEST_STATUS_CODE_SUC, data);
//                finish();
//            }
//
//            @Override
//            public void onFailure(ResponseMsg msg) {
//                TCLogUtils.toastShort(context, msg.getRetMsg());
//                Intent data = getIntent();
//                data.putExtra("result",msg);
//                setResult(Config.REQUEST_STATUS_CODE_FAILURE, data);
//                finish();
//            }
//        });
//    }
//
//    /**
//     * 自定登陆
//     *
//     * @param autoLoginId 自动登陆需要的ID
//     */
//    private void autoLogin(String autoLoginId) {
//        TianCi.getInstance().autoLogin(autoLoginId, new RequestCallBack() {
//            @Override
//            public void onSuccessed() {
//                TCLogUtils.toastShort(context, "成功");
//            }
//
//            @Override
//            public void onFailure(ResponseMsg msg) {
//                TCLogUtils.toastShort(context, msg.getRetMsg());
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
//            et_account.setText(data.getStringExtra("account"));
//            et_psw.setText("");
//        }
//    }
//}
