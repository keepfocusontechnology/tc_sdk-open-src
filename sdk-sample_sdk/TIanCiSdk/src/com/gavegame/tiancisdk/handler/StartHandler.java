package com.gavegame.tiancisdk.handler;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by Tianci on 15/9/17.
 */
public class StartHandler extends Handler {
    WeakReference<Activity> mActivityRef;

    StartHandler(Activity activity) {
        mActivityRef = new WeakReference<Activity>(activity);
    }

}