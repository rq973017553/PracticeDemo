package com.rq.practice.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by fanzhang on 2017/6/2.
 * <p>
 * 管理全屏SystemBar的展示和隐藏
 */

public class SystemBarUtils {

    /**
     * api 19以上才有
     */
    private final static int FLAG_IMMERSIVE_STICKY = 0x00001000;

    /**
     * vesion8.0状态栏
     *
     * @param window
     * @param dark
     */
    public static void setAndroidNativeLightStatusBar(Window window, boolean dark)
    {
        if (window == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            View view = window.getDecorView();
            if (view == null)
            {
                return;
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
            if (dark)
            {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            else
            {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 设置状态栏深色
     *
     * @param view 状态栏
     * @param dark 深色
     */
    public static void setAndroidNativeLightStatusBar(View view, boolean dark)
    {
        if (view == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            view.setVisibility(View.GONE);
        }
        else
        {
            transparencyStatusBar((Activity) view.getContext(), true);
            if (view.getLayoutParams() != null)
            {
                int height = getStatusHeight(view.getContext());
                view.getLayoutParams().height = height > 0 ? height : DisplayUtil.dip2px(view.getContext(), 20);
            }
            // 6.0以上可配深色浅色状态栏位文字
            if (dark)
            {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            else
            {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 全透明状态栏
     * @param window Window变量
     */
    public static void setStatusBarFullTransparent(Window window){
        setStatusBarFullTransparent(window, true);
    }


    /**
     * 全透明状态栏
     * @param window Window变量
     * @param isTransparencyFormText 状态栏的文字是否需要透明
     */
    public static void setStatusBarFullTransparent(Window window, boolean isTransparencyFormText) {
        View view = window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21表示5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (!isTransparencyFormText && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        // 考虑低于23的情况下的文字显示
    }

//    public static void setStatusBar

    public static void showNavigation(@Nullable View view)
    {
        if (view != null)
        {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * api 19以上调用
     */
    public static void hideNavigation(@Nullable View view)
    {
        if (view != null && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    public static  void setHalfTransparent(Window window) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21表示5.0
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 状态栏透明
     *
     * @param isTransparency 是否透明
     */
    public static void transparencyStatusBar(Activity activity,  boolean isTransparency)
    {
        if (activity == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return;
        }
        View contentView = activity.findViewById(android.R.id.content);
        if (contentView == null)
        {
            return;
        }
        contentView = ((ViewGroup) contentView).getChildAt(0);
        if (contentView == null)
        {
            return;
        }
        Window window = activity.getWindow();
        contentView.setFitsSystemWindows(!isTransparency);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH){
            contentView.requestApplyInsets();
        }else {
            contentView.requestFitSystemWindows();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (isTransparency)
            {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            else
            {
                window.getDecorView().setSystemUiVisibility(View.VISIBLE);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            if (isTransparency)
            {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            else
            {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public static int getStatusHeight(Context context)
    {
        String path = "com.android.internal.R$dimen";
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight)
        {
            Class<?> localClass;
            try
            {
                localClass = Class.forName(path);
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
