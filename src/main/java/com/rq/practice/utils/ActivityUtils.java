package com.rq.practice.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理工具
 * 可以统一释放所有activity
 * 还可以在每个生命周期中调用一些统计方法
 *
 * @author rock you
 * @version [1.0.0 2018.6.5]
 */
public class ActivityUtils {

    private static final List<WeakReference<Activity>> STACK = new ArrayList<>();

    public static void onActivityCreate(Activity activity){
        STACK.add(new WeakReference<>(activity));
    }

    public static void onActivityStart(Activity activity){
        checkActivityNull(activity);
        // Empty Method
    }

    public static void onActivityResume(Activity activity){
        checkActivityNull(activity);
        // Empty Method
    }

    public static void onActivityPause(Activity activity){
        checkActivityNull(activity);
        // Empty Method
    }

    public static void onActivityStop(Activity activity){
        checkActivityNull(activity);
        // Empty Method
    }

    public static void onActivityDestroy(Activity activity){
        checkActivityNull(activity);
        // Empty Method
    }

    public static void finishAll(){
        for (WeakReference<Activity> weakActivity :STACK){
            Activity activity = weakActivity.get();
            if (activity != null){
                activity.finish();
            }
        }
        STACK.clear();
    }

    public static void exit(Activity activity){
        checkActivityNull(activity);
        finishAll();
        activity.finish();
    }

    private static void checkActivityNull(Activity activity){
        if (activity == null){
            throw new IllegalArgumentException("activity is null, Check whether the parameters are null");
        }
    }

}
