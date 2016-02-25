package com.gavegame.tiancisdk.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;

import com.gavegame.tiancisdk.R;

public class UrlConfigManager {
	private static ArrayList<String> urlList;

	private static void fetchUrlDataFromXml(final Context context) {
		urlList = new ArrayList<String>();

		final XmlResourceParser xmlParser = context.getResources().getXml(R.xml.url);

		int eventCode;
		try {
			eventCode = xmlParser.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT) {
				switch (eventCode) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if ("Node".equals(xmlParser.getName())) {
						final String url = xmlParser.getAttributeValue(null,
								"Url");
						urlList.add(url);
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventCode = xmlParser.next();
			}
		} catch (final XmlPullParserException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			xmlParser.close();
		}
	}

	public static String findURL(final Context context) {
		// 如果urlList还没有数据（第一次），或者被回收了，那么（重新）加载xml
		if (urlList == null || urlList.isEmpty())
			fetchUrlDataFromXml(context);

		return urlList.get(0);
	}
}