package com.rq.practice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.rq.practice.utils.EasyLog;

/**
 * Created by antiy2015 on 2017/6/8.
 */
public class CustomHorizontalScrollView extends ViewGroup{

    private Scroller mScroller;

    private int mScaledTouchSlop;

    private int mMinimumFlingVelocity;

    private GestureDetector mGestureDetector;

    public CustomHorizontalScrollView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr){

        mScroller = new Scroller(context);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setLongClickable(true);
        mMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mGestureDetector = new GestureDetector(context, gestureListener);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    //停止
    private static final int SCROLL_STATE_FINISH = 0;

    //滑动
    private static final int SCROLL_STATE_SLIDE = 1;

    // 初始值设置为SCROLL_STATE_FINISH
    private int mScrollState = SCROLL_STATE_FINISH;

    private float mLastTouchPosition;

    private VelocityTracker mVelocityTracker;

    private int mCurScreen;


    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            EasyLog.v("短按");
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            EasyLog.v("长按");
        }

        @Override
        public boolean onDown(MotionEvent e) {
            EasyLog.v("onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            EasyLog.v("滑动");
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    } ;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mScrollState = (mScroller.isFinished()? SCROLL_STATE_FINISH: SCROLL_STATE_SLIDE);
                mLastTouchPosition = touchX;
                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int)Math.abs(mLastTouchPosition - touchX);
                if (xDiff > mScaledTouchSlop){
                    mScrollState = SCROLL_STATE_SLIDE;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mScrollState = SCROLL_STATE_FINISH;
                break;
        }
        // 在状态不是SCROLL_STATE_FINISH(说明这个时候用户还在操作，所以需要拦截)时候，拦截事件
        // 在状态是SCROLL_STATE_FINISH(说明这个时候用户不在操作，所以不需要拦截)时候，不拦截事件
        return mScrollState != SCROLL_STATE_FINISH;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastTouchPosition = touchX;
                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int)(mLastTouchPosition - touchX);
                // 1.使用scrollBy的时候会触发scrollTo
                // 2.再触发invalidate方法
                // 3.最后触发computeScroll
                // ACTION_MOVE的时候使用scrollBy达到按照手指移动更新位置
                scrollBy(xDiff, 0);
                mLastTouchPosition = touchX;
                break;
            case MotionEvent.ACTION_UP:
                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                // 负值为向右边移动，正值为向左边移动。和习惯相反
                float velocity = velocityTracker.getXVelocity();
                EasyLog.v("velocity::"+velocity);
                // 向左边移动
                if (velocity > mMinimumFlingVelocity
                        && mCurScreen > 0){
                        // 向左边移动则是mCurScreen减一
                        mCurScreen = mCurScreen-1;
                        switchScreen(mCurScreen);
                // 向右边移动
                }else if(velocity < -mMinimumFlingVelocity
                        && mCurScreen<(getChildCount()-1)){
                        // 向右边移动则是mCurScreen加一
                        mCurScreen = mCurScreen+1;
                        switchScreen(mCurScreen);
                }else {
                        curScreen();
                }
                mScrollState = SCROLL_STATE_FINISH;
                break;
            case MotionEvent.ACTION_CANCEL:
                mScrollState = SCROLL_STATE_FINISH;
                break;
        }
        // 当ViewGroup点击down的时候不去消费UP事件
//        if (mScrollState == SCROLL_STATE_FINISH){
////            performClick();
//            return false;
//        }
        return true;
    }

    /**
     * 切换屏幕
     *
     * @param curScreen
     */
    private void switchScreen(int curScreen){
        if (curScreen > getChildCount()-1){
            curScreen = getChildCount()-1;
        }
        mCurScreen = curScreen;
        int screenWidth = getWidth()*curScreen;
        int scrollX = getScrollX();
        int xDiff = screenWidth - scrollX;
//        EasyLog.v("screenWidth::"+screenWidth);
//        EasyLog.v("scrollX::"+scrollX);
//        EasyLog.v("xDiff::"+xDiff);

        /**
         * 1.startX和startY为正值的时候，在原点左(下)方
         * 2.startX和startY为负值的时候，在原点右(上)方
         * 3.dx和dy为正值的时候，向左(上)方滚动
         * 4.dx和dy为负值的时候，向右(下)方滚动
         */
        mScroller.startScroll(getScrollX(), 0, xDiff, 0, Math.abs(xDiff)*2);
        invalidate();
    }

    /**
     * 计算当前是第几屏
     */
    private void curScreen(){
        int screenNum = (getScrollX()+getWidth()/2)/getWidth();
//        EasyLog.v("当前屏幕::第"+screenNum+"屏");
        switchScreen(screenNum);
    }


    /**
     * computeScroll的主要作用是
     * 将滚动的数值拆分成getCurrX或者getCurrY
     * 而调用scrollTo可以使得ViewGroup通过getCurrX或者getCurrY更新位置
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        switch (MeasureSpec.getMode(heightMeasureSpec)){
            case View.MeasureSpec.EXACTLY:
//                EasyLog.v("mode::EXACTLY");
                break;
            case View.MeasureSpec.AT_MOST:
//                EasyLog.v("mode::AT_MOST");
                break;
            case View.MeasureSpec.UNSPECIFIED:
//                EasyLog.v("mode::UNSPECIFIED");
                break;
            default:
                break;
        }

        int childViewCount = getChildCount();
        for (int i = 0; i<childViewCount; i++){
            View childView = getChildAt(i);
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int childViewCount = getChildCount();
            int startLeftPosition = l;
            int startTopPosition = t;
            for (int i = 0; i < childViewCount; i++){
                View childView = getChildAt(i);
                if (childView.getVisibility() != GONE){
                    MarginLayoutParams marginParams =
                            (MarginLayoutParams) childView.getLayoutParams();
                    int marginLeft = marginParams.leftMargin;
                    int marginTop = marginParams.topMargin;
                    int marginRight = marginParams.rightMargin;
                    int width = childView.getMeasuredWidth();
                    int height = childView.getMeasuredHeight();
                    startLeftPosition = startLeftPosition+marginLeft;
                    startTopPosition = startTopPosition+marginTop;
                    childView.layout(startLeftPosition,
                                     startTopPosition,
                                     startLeftPosition+width,
                                     startTopPosition+height);
                    startLeftPosition = startLeftPosition+width+marginRight;
                    startTopPosition = t;
                }
            }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
