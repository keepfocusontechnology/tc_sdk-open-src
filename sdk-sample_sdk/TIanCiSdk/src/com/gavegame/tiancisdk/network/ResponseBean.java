package com.gavegame.tiancisdk.network;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import com.gavegame.tiancisdk.network.strategy.ParamsStrategy;

/**
 * Created by Tianci on 15/9/23.
 */
public class ResponseBean {

	public ParamsStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ParamsStrategy strategy) {
		this.strategy = strategy;
	}

	private WeakReference<Context> mContextRef;
	private RequestCallBack callBack;
	private Method method;
	private String paramsUri;
	private Context context;
	// private HashMap<String, Object> mHashmap;
	private ParamsStrategy strategy;

	public ResponseBean(Context context, String paramsUri, Method method,
	// HashMap<String, Object> hashmap,
			ParamsStrategy strategy, RequestCallBack callBack) {
		this.paramsUri = paramsUri;
		this.callBack = callBack;
		this.method = method;
		mContextRef = new WeakReference<>(context);
		this.context = mContextRef.get();
		this.strategy = strategy;
	}

	// public HashMap<String, Object> getmHashmap() {
	// return mHashmap;
	// }
	//
	// public void setmHashmap(HashMap<String, Object> mHashmap) {
	// this.mHashmap = mHashmap;
	// }

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

	// public static ResponseBean newResponseBean(){
	//
	// }

}
