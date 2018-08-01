package com.rq.practice.activities.practice;

import android.os.Build;
import android.view.ViewTreeObserver;

import com.rq.practice.R;
import com.rq.practice.activities.base.BaseActivity;
import com.rq.practice.utils.EasyLog;
import com.rq.practice.view.CustomHorizontalScrollView;

/**
 * 自定义ViewGroup(com.rq.view.CustomHorizontalScrollView)
 * Created by rock you on 2017/12/16.
 */
public class CustomScrollActivity extends BaseActivity{

    private CustomHorizontalScrollView mCustomView;

    @Override
    public int getLayoutID() {
        return R.layout.activity_custom_scroll;
    }

    @Override
    public void bindView() {
        mCustomView = findViewById(R.id.custom_horizontal_scrollView);
    }

    @Override
    public void initData() {
        testViewSize();
    }

    // 获取View的宽高
    public void testViewSize(){
        // 1.获取View的宽高的一种方法。使用该方案的时候，建议放在onAttachedToWindow方法中
        mCustomView.post(new Runnable() {
            @Override
            public void run() {
                EasyLog.v("Width::"+ mCustomView.getWidth());
                EasyLog.v("Height::"+ mCustomView.getHeight());
            }
        });

        // 2.获取View的宽高的一种方法
        final ViewTreeObserver observer = mCustomView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                EasyLog.v("Width::"+ mCustomView.getWidth());
                EasyLog.v("Height::"+ mCustomView.getHeight());
                // 注意isAlive一定要判断
                if (observer.isAlive()){
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
                        // 安卓版本在16以上使用
                        observer.removeOnGlobalLayoutListener(this);
                    }else {
                        // 安卓版本在16以下使用
                        observer.removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
    }
}
