package com.neworin.customview3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义View---圆
 * Created by NewOr on 2016/4/19.
 */
public class CustomCircleView extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 当前进度
     */
    private int mProgress;
    /**
     * 速度
     */
    private int mSpeed;
    /**
     * 是否开始下一个
     */
    private boolean isNext = false;

    private Paint mPaint;

    public CustomCircleView(Context context) {
        this(context, null);

    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获得自定义的属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCircleView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomCircleView_firstColor:
                    mFirstColor = typedArray.getColor(i, Color.GREEN);
                    break;
                case R.styleable.CustomCircleView_secondColor:
                    mSecondColor = typedArray.getColor(i, Color.BLUE);
                    break;
                case R.styleable.CustomCircleView_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomCircleView_speed:
                    mSpeed = typedArray.getInt(attr, 20);
                    break;
            }
        }
        typedArray.recycle();
        //绘图线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) isNext = true;
                        else isNext = false;
                    }
                    Log.d("NewOrin", "mProgess=" + mProgress);
                    postInvalidate();//刷新界面
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        int center = getWidth() / 2;//获取圆形的x坐标
        int radius = center - mCircleWidth / 2;//半径
        mPaint.setAntiAlias(true);//清楚锯齿
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环宽度
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);//用于定义的圆弧的形状和大小的界限
        if (!isNext) {
            //第一颜色的圈完整，第二颜色跑
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint);// 画出圆环
            mPaint.setColor(mSecondColor);// 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint);// 根据进度画圆弧
        } else {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }
        super.onDraw(canvas);
    }
}
