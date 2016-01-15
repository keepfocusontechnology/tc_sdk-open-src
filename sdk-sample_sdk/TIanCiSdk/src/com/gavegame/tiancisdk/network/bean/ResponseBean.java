package com.gavegame.tiancisdk.network.bean;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import com.gavegame.tiancisdk.network.Method;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.strategy.IParamsStrategy;

/**
 * Created by Tianci on 15/9/23.
 */
public class ResponseBean {

	private String url;
	private WeakReference<Context> mContextRef;
	private RequestCallBack callBack;
	private Method method;
	private Context context;
	// private HashMap<String, Object> mHashmap;
	private IParamsStrategy strategy;

	public ResponseBean(Context context, String url, Method method,
	// HashMap<String, Object> hashmap,
			IParamsStrategy strategy, RequestCallBack callBack) {
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public IParamsStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IParamsStrategy strategy) {
		this.strategy = strategy;
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
