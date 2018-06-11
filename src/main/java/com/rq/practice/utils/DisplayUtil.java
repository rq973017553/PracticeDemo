package com.rq.practice.utils;

import android.app.Activity;
import android.content.Context;

public class DisplayUtil
{
    public static int dip2px(Context context, double dipValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static int px2dip(Context context, double pxValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * 长的为宽度 <功能详细描述>
     * 
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int screenWidthPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? widthPx : heightPx;
    }

    /**
     * 小的为高度
     * 
     * @param context
     * @return
     */
    public static int screenHeightPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? heightPx : widthPx;
    }

    /**
     * 宽度
     */
    public static int realScreenWidthPx(Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 高度
     */
    public static int realScreenHeightPx(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int sp2px(float spValue, float fontScale)
    {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * 
     * @param activity Activity
     * @return Width
     * @see [类、类#方法、类#成员]
     */
    public static int getDisplayWidth(final Activity activity)
    {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }
}
