package com.rq.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.rq.practice.utils.EasyLog;
import com.rq.practice.utils.ShellUtils;

import java.lang.reflect.Method;

/**
 * Created by antiy2015 on 2016/5/19.
 */
public class Tools {

    @Deprecated
    public static void setMobileDataEnabled(Context context, boolean flag){
        Method setMobileDataEnabledMethod;
        EasyLog.v("SDK_INT::"+Build.VERSION.SDK_INT);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            ConnectivityManager gprsCM = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                setMobileDataEnabledMethod = gprsCM.getClass()
                        .getDeclaredMethod("setMobileDataEnabled", boolean.class);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(gprsCM, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            TelephonyManager teleManager = (TelephonyManager)context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            try{
                setMobileDataEnabledMethod = teleManager.getClass().
                        getDeclaredMethod("setDataEnabled", boolean.class);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(teleManager, flag);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void setMobileDataEnabled(boolean enable){
        EasyLog.v("SDK_INT::"+Build.VERSION.SDK_INT);
        String cmd = enable? "svc data enable" : "svc data disable";
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmd, false, true);
        EasyLog.v("svc data 结果::"+
                  "\nresult::"+result.result+
                  "\n::errorMsg::"+result.errorMsg+
                  "\n::successMsg::"+result.successMsg);
    }
}
