package com.rq.practice.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.rq.practice.utils.EasyLog;

/**
 * 该自定义View是关于字体的练习
 * Created by antiy2015 on 2017/11/16.
 */
public class FontMetricsView extends View{

    private TextPaint mTextPaint;

    private Paint mTopLinePaint;

    private Paint mBottomLinePaint;

    private Paint mAscentLinePaint;

    private Paint mDescentLinePaint;

    private Paint mBaseLinePaint;

    private Paint.FontMetrics mFontMetrics;

    private Paint mArcPaint;

    private Path mPath;

    public FontMetricsView(Context context) {
        this(context, null, 0);
    }

    public FontMetricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontMetricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        mPath = new Path();

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setColor(Color.BLUE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(2);

        mBaseLinePaint = new Paint();
        mBaseLinePaint.setStyle(Paint.Style.FILL);
        mBaseLinePaint.setAntiAlias(true);
        mBaseLinePaint.setColor(Color.RED);

        mTopLinePaint = new Paint();
        mTopLinePaint.setStyle(Paint.Style.FILL);
        mTopLinePaint.setAntiAlias(true);
        mTopLinePaint.setColor(Color.YELLOW);

        mBottomLinePaint = new Paint();
        mBottomLinePaint.setStyle(Paint.Style.FILL);
        mBottomLinePaint.setAntiAlias(true);
        mBottomLinePaint.setColor(Color.BLUE);


        mAscentLinePaint = new Paint();
        mAscentLinePaint.setStyle(Paint.Style.FILL);
        mAscentLinePaint.setAntiAlias(true);
        mAscentLinePaint.setColor(Color.BLACK);

        mDescentLinePaint = new Paint();
        mDescentLinePaint.setStyle(Paint.Style.FILL);
        mDescentLinePaint.setAntiAlias(true);
        mDescentLinePaint.setColor(Color.GRAY);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(150);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mFontMetrics = mTextPaint.getFontMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        EasyLog.v("width::"+width);
        EasyLog.v("height::"+height);
        EasyLog.v("widthMode::"+widthMode);
        EasyLog.v("heightMode::"+heightMode);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        EasyLog.v("left::"+left+"::top::"+top+"::right::"+right+"::bottom::"+bottom);
    }

    int baseLineX = 0 ;
    int baseLineY = 200;


    //重要公式
    //mFontMetrics.top = top坐标 - baseY
    //mFontMetrics.bottom = bottom坐标 - baseY
    //mFontMetrics.ascent = ascent坐标 - baseY
    //mFontMetrics.descent = descent坐标 - baseY

    RectF rectF = new RectF(100, 500, 300, 700);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        float top = Math.abs(mFontMetrics.top + baseLineY);
        float bottom = mFontMetrics.bottom + baseLineY;
        float ascent = Math.abs(mFontMetrics.ascent + baseLineY);
        float descent = mFontMetrics.descent + baseLineY;
        String text = "abcdgfhisk";
        canvas.drawText(text, baseLineX, baseLineY, mTextPaint);
        canvas.drawLine(baseLineX, baseLineY, 2000, baseLineY, mBaseLinePaint);
        canvas.drawLine(baseLineX, top, 2000, top, mBaseLinePaint);
        canvas.drawLine(baseLineX, bottom, 2000, bottom, mBaseLinePaint);
        canvas.drawLine(baseLineX, ascent, 2000, ascent, mBaseLinePaint);
        canvas.drawLine(baseLineX, descent, 2000, descent, mBaseLinePaint);

//        mPath.addArc(rectF, 0, 90);
//        mPath.addArc(rectF, 90, 90);
//        mPath.addArc(rectF, 180, 90);

        mPath.arcTo(rectF, 0, 90);
        mPath.arcTo(rectF, 90, 90);
        mPath.arcTo(rectF, 180, 90);
        mPath.moveTo(500, 500);
        mPath.quadTo(600, 250, 700, 500);
        canvas.drawRect(rectF, mArcPaint);
        canvas.drawPath(mPath, mArcPaint);
    }
}
