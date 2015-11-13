package com.gavegame.tiancisdk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gavegame.tiancisdk.FragmentChangedCallback;
import com.gavegame.tiancisdk.activity.TCLoginActivity;

public abstract class TCBaseFragment extends Fragment {

	public FragmentChangedCallback callback;
	public View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(getLayoutId(), null);
		} catch (Throwable e) {
			return null;
		}
		initID();
		if (savedInstanceState != null)
			restoreAction(savedInstanceState);
		callback = (TCLoginActivity) getActivity();
		return view;
	}

	abstract void initID();

	abstract int getLayoutId();

	protected void restoreAction(Bundle savedInstanceState) {

	}

	public  void initData(Bundle data) {

	}
}
