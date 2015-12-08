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

import com.gavegame.tiancisdk.utils.TCLogUtils;

public final class TianCiSDK {

	static TianCiSDK mTianCiSDK;
	// 默认为竖屏 false为横屏 true为竖屏
	private static boolean screenIsPortrait;

	public static void init(Context context) {
		if (mTianCiSDK == null) {
			synchronized (TianCiSDK.class) {
				if (mTianCiSDK == null) {
					mTianCiSDK = new TianCiSDK(context);
				}
			}
		}
	}

	public static void setScreenIsPortrait(boolean screenState) {
		screenIsPortrait = screenState;
	}

	public static boolean getScreenState() {
		return screenIsPortrait;
	}

	public static TianCiSDK getInstance() {
		if (mTianCiSDK == null) {
			throw new IllegalStateException("The TianCiSDK is not initialize!");
		}
		return mTianCiSDK;
	}

	private Platform mPlatform;

	private TianCiSDK(Context context) {
		mPlatform = Platform.fromMetaData(context);
		// HttpConfiguration configuration = HttpConfiguration.create(context)
		// .setServer(Config.SERVER).setRequestInterceptor(new
		// HttpRequestInterceptor());
		// HttpClient.init(configuration);
	}

	public Platform getPlatform() {
		return mPlatform;
	}

	public static void setDebugModel(boolean isDebug) {
		TCLogUtils.isDebug = isDebug;
	}
}
