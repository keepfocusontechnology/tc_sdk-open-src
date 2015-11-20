package com.gavegame.tiancisdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.gavegame.tiancisdk.R;


/**
 * Created by Tianci on 15/10/9.
 */
public class DialogUtils {

    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null); // 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view); // 加载布局
        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle); // 创建自定义样式dialog
//        loadingDialog.setCancelable(false); // 不可以用"返回键"取消
        loadingDialog.setContentView(layout, new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT,
        		RelativeLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }
}
