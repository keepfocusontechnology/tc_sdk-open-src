//package com.gavegame.tiancisdk.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.Html;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.gavegame.tiancisdk.Config;
//import com.gavegame.tiancisdk.R;
//import com.gavegame.tiancisdk.TianCi;
//import com.gavegame.tiancisdk.network.RequestCallBack;
//import com.gavegame.tiancisdk.network.ResponseMsg;
//import com.gavegame.tiancisdk.utils.NormalUtils;
//import com.gavegame.tiancisdk.utils.TCLogUtils;
//import com.gavegame.tiancisdk.widget.PolicyDialog;
//
///**
// * Created by Tianci on 15/9/25.
// */
//public class RegisterPageActivity extends BaseActivity implements TextWatcher {
//
//    private static final String TAG = RegisterPageActivity.class.getSimpleName();
//    private EditText et_account;
//    private EditText et_psw;
//    private EditText et_repeat_psw;
//    private CheckBox checkBox;
//    private TextView tv_private_policy;
//    private boolean policyIsRead = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initView();
//    }
//    
//    @Override
//    protected void onResume() {
//    	// TODO Auto-generated method stub
//    	super.onResume();
//    	 TianCi.init(this);
//    }
//
//    @Override
//    public String getTag() {
//        return TAG;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.register_page;
//    }
//
//
//    @Override
//    public String[] getTitleText() {
//        return new String[]{"注册", "完成"};
//    }
//
//    private void initView() {
//        et_account = (EditText) contentView.findViewById(R.id.et_register_account);
//        et_psw = (EditText) contentView.findViewById(R.id.et_register_psw);
//        et_repeat_psw = (EditText) contentView.findViewById(R.id.et_register_repeat_psw);
//
//        et_account.addTextChangedListener(this);
//        et_psw.addTextChangedListener(this);
//        et_repeat_psw.addTextChangedListener(this);
//
//        checkBox = (CheckBox) contentView.findViewById(R.id.check_secret);
//        tv_private_policy = (TextView) contentView.findViewById(R.id.tv_private_policy);
//        tv_private_policy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popPolicyDialog();
//            }
//        });
//        et_account = (EditText) contentView.findViewById(R.id.et_register_account);
//        et_psw = (EditText) contentView.findViewById(R.id.et_register_psw);
//        et_repeat_psw = (EditText) contentView.findViewById(R.id.et_register_repeat_psw);
//
//        et_account.addTextChangedListener(this);
//        et_psw.addTextChangedListener(this);
//        et_repeat_psw.addTextChangedListener(this);
//
//        checkBox = (CheckBox) contentView.findViewById(R.id.check_secret);
//        checkBox.setChecked(policyIsRead);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                policyIsRead = !policyIsRead;
//                checkBox.setChecked(policyIsRead);
//            }
//        });
//        tv_private_policy = (TextView) contentView.findViewById(R.id.tv_private_policy);
//        tv_private_policy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popPolicyDialog();
//                policyIsRead = true;
//                checkBox.setChecked(policyIsRead);
//            }
//        });
//        tv_private_policy.setText(Html.fromHtml("<a href=\"http://google.com\">" + tv_private_policy.getText().toString() + "</a>"));
//    }
//
//    @Override
//    public void submit() {
//        String accountStr = et_account.getText().toString();
//        String pswStr = et_psw.getText().toString();
//        String repeatPswStr = et_repeat_psw.getText().toString();
//
//        if (accountStr.length() == 0
//                || pswStr.length() == 0
//                || repeatPswStr.length() == 0) {
//            TCLogUtils.toastShort(context, "账户或密码不能为空");
//            return;
//        }
//        if (!checkBox.isChecked()) {
//            TCLogUtils.toastShort(context, "请确认已阅读用户须知");
//            return;
//        }
//        if (!pswStr.equals(repeatPswStr)) {
//            TCLogUtils.toastShort(context, "两次输入密码不一致");
//            return;
//        }
//        if (!NormalUtils.isMobileNO(accountStr)) {
//            TCLogUtils.toastShort(context, "请输入手机号作为账号");
//            return;
//        }
//
//        register(accountStr, pswStr);
//
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }
//
//    void popPolicyDialog() {
//        PolicyDialog policyDialog = new PolicyDialog(this);
//        policyDialog.show();
//    }
//
//
//    private void register(final String account, String psw) {
//        TianCi.getInstance().register(account, psw, new RequestCallBack() {
//            @Override
//            public void onSuccessed() {
//                TCLogUtils.toastShort(context, "成功");
//                Intent data = new Intent();
//                data.putExtra("account",account);
//                setResult(Config.REQUEST_STATUS_CODE_SUC,data);
//                finish();
//            }
//
//            @Override
//            public void onFailure(ResponseMsg msg) {
//                TCLogUtils.toastShort(context, msg.getRetMsg());
//            }
//        });
//    }
//}
