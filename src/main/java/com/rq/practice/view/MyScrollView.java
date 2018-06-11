package com.rq.practice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by antiy2015 on 2017/7/10.
 */
public class MyScrollView extends ScrollView{
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private AlphaChangeListener listener;

    public interface AlphaChangeListener{
        void onAlphaChange(int alpha);
    }

    public void setOnAlphaChangeListener(AlphaChangeListener listener){
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        View childView = ((ViewGroup)getChildAt(0)).getChildAt(0);
        int firstViewHeight = childView.getHeight();
//        Log.v("rq", "firstViewHeight::"+firstViewHeight);
//        Log.v("rq", "top::"+top);
        if (top > firstViewHeight){
            top = firstViewHeight;
        }else if (top<0){
            top = 0;
        }
        int alpha = (int)((top/(firstViewHeight*1.0f))*255);
//        Log.v("rq", "alpha::"+alpha);
        if (listener != null){
            listener.onAlphaChange(alpha);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
