package com.gavegame.tiancisdk.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.utils.TCLogUtils;

import java.io.IOException;

/**
 * Created by Tianci on 15/9/28.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private String TAG = getTag();
    protected ViewGroup contentView;
    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_action;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.action_bar_activity);
        initContentView();
        context = getApplicationContext();
    }

    public abstract String getTag();

    protected abstract int getLayoutId();

    protected abstract String[] getTitleText();

    protected abstract void submit();

    private void initContentView() {
        contentView = (ViewGroup) findViewById(R.id.fl_content);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(getLayoutId(), contentView, true);
        iv_back = (ImageView) findViewById(R.id.iv_action_bar_icon_back);
        tv_title = (TextView) findViewById(R.id.tv_action_bar_text_title);
        if (getTitleText()[0] != null && !getTitleText()[0].equals("")) {
            tv_title.setText(getTitleText()[0]);
        }
        tv_action = (TextView) findViewById(R.id.tv_action_bar_text_action);
        if (getTitleText().length > 1 && getTitleText()[1] != null && !getTitleText()[1].equals("")) {
            tv_action.setText(getTitleText()[1]);
        }
        iv_back.setOnClickListener(this);
        tv_action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_action_bar_icon_back) {
            finish();
        } else if (v.getId() == R.id.tv_action_bar_text_action) {
            submit();
        }
    }

    /**
     * 模拟返回键
     */
    private void goBack() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("" + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            TCLogUtils.e(TAG, e.toString());
        }
    }
}
