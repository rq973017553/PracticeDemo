package com.rq.practice;

import android.app.Application;

import com.rq.practice.utils.EasyLog;

/**
 *  Application类
 *
 * @author rock you
 * @version [1.0.0 2018.6.4]
 */
public class PracticeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyLog.v("onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EasyLog.v("onTerminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        EasyLog.v("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        EasyLog.v("onTrimMemory");
    }
}
