package com.gavegame.tiancisdk.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.gavegame.tiancisdk.R;

/**
 * Created by Tianci on 15/9/28.
 */
public class PolicyDialog extends Dialog {

    private TextView tv_dismiss;
    private WebView webView;

    public PolicyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.policy_dialog);
        //设置标题
        tv_dismiss = (TextView) findViewById(R.id.tv_dialog_dismiss);
        webView = (WebView) findViewById(R.id.web_policy);
        tv_dismiss.setOnClickListener(clickListener);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tcsdk_user_need_know.html");
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            PolicyDialog.this.dismiss();
        }
    };
}
