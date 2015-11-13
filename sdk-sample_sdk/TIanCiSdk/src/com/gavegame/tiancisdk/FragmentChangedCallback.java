package com.gavegame.tiancisdk;

import android.os.Bundle;


public interface FragmentChangedCallback {

	//跳转页面
	void jumpNextPage(int targetFragmentID);
	
	//带账号的跳转页面
	void jumpNextPage(int targetFragmentID,Bundle bundle);
	
	//部分返回键的处理
	void back();
}
