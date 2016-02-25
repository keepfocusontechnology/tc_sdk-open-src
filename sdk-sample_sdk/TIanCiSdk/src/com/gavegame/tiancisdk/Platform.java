/**
 * <pre>
 * Copyright 2015 Soulwolf Ching
 * Copyright 2015 The Android Open Source Project for TianCiSdk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 */
package com.gavegame.tiancisdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.gavegame.tiancisdk.network.NetworkUtils;
import com.gavegame.tiancisdk.utils.SharedPreferencesUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.utils.UrlConfigManager;

public class Platform {

	static final String TAG = "Platform";

	// 用户标识.在联运渠道的包中为联运渠道返回的UID.在天赐自有渠道中为IMEI
	public static final String TIANCI_DEVICE_ID = "TIANCI_DEVICE_ID";
	// 天赐平台分配的游戏id
	public static final String TIANCI_GAME_ID = "TIANCI_GAME_ID";
	// 天赐平台分配的渠道ID
	public static final String TIANCI_CHANNEL_ID = "TIANCI_CHANNEL_ID";
	// 设备型号
	public static final String TIANCI_USER_AGENT = "TIANCI_USER_AGENT";
	// 设备类型
	public static final String TIANCI_DEVICE_TYPE = "TIANCI_DEVICE_TYPE";

	public static Platform fromMetaData(Context context) {
		return new Platform(context);
	}

	private String mDeviceId;
	private int mGameId;
	private int mChannelId;
	private String mUserAgent;
	private int mDeviceType;

	Platform(Context context) {
		parserMetaData(context);
	}

	private void parserMetaData(Context context) {
		try {
			NetworkUtils.setServer(UrlConfigManager.findURL(context));

			ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			mDeviceId = ((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			mUserAgent = Build.MODEL;
			mGameId = appInfo.metaData.getInt(TIANCI_GAME_ID);
			mChannelId = appInfo.metaData.getInt(TIANCI_CHANNEL_ID);
			mDeviceType = 1;

			SharedPreferencesUtils.setParam(context, TIANCI_DEVICE_ID,
					mDeviceId);
			SharedPreferencesUtils.setParam(context, TIANCI_GAME_ID, mGameId);
			SharedPreferencesUtils.setParam(context, TIANCI_CHANNEL_ID,
					mChannelId);
			SharedPreferencesUtils.setParam(context, TIANCI_USER_AGENT,
					mUserAgent);
			SharedPreferencesUtils.setParam(context, TIANCI_DEVICE_TYPE,
					mDeviceType);
			TCLogUtils.d("清单文件中取出的初始化内容:mGameId=" + mGameId + ","
					+ "mChannelId=" + mChannelId);
		} catch (Exception e) {
			TCLogUtils.e(TAG, "parserMetaData:" + e.toString());
		}
	}

	public String getDeviceId() {
		return mDeviceId;
	}

	public int getGameId() {
		return mGameId;
	}

	public int getChannelId() {
		return mChannelId;
	}

	public String getUserAgent() {
		return mUserAgent;
	}

	public int getPlatform() {
		return mDeviceType;
	}
}
