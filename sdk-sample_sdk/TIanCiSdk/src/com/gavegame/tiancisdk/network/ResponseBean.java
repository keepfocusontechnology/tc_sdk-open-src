package com.gavegame.tiancisdk.network;

import android.content.Context;


import java.lang.ref.WeakReference;

/**
 * Created by Tianci on 15/9/23.
 */
public class ResponseBean {

    private WeakReference<Context> mContextRef;
    private RequestCallBack callBack;
    private Method method;
    private String paramsUri;
    private Context context;

//    Context context, String paramsUri, Method method, RequestCallBack callBack

    public ResponseBean(Context context, String paramsUri, Method method, RequestCallBack callBack){
        this.paramsUri = paramsUri;
        this.callBack = callBack;
        this.method = method;
        mContextRef = new WeakReference<>(context);
        this.context = mContextRef.get();
    }

    public RequestCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(RequestCallBack callBack) {
        this.callBack = callBack;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getParamsUri() {
        return paramsUri;
    }

    public void setParamsUri(String paramsUri) {
        this.paramsUri = paramsUri;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

//    public static ResponseBean newResponseBean(){
//
//    }

}
