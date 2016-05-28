package com.neworin.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.neworin.customview.R;

import java.util.Calendar;

/**
 * 自定义View绘画时钟
 * Created by NewOr on 2016/4/26.
 */
public class ClockView extends View {

    private Paint mPaint;
    private int radius;//半径
    private int circleColor;//圆圈颜色
    private int circleWidth;//圆圈宽度
    private int numSize;//数字大小
    private int numColor;//数字颜色
    private int scaleSize;//刻度大小
    private int scaleColor;//刻度颜色

    private int mHour;
    private int mMinute;
    private int mSecond;
    private int hpLength, mpLength, spLength;
    private Rect mRect;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClockView, defStyleAttr, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ClockView_radius:
                    radius = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    hpLength = (int) (radius * 0.4);
                    mpLength = (int) (radius * 0.5);
                    spLength = (int) (radius * 0.6);
                    break;
                case R.styleable.ClockView_circleColor:
                    circleColor = ta.getColor(i, Color.BLACK);
                    break;
                case R.styleable.ClockView_circleWidth:
                    circleWidth = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ClockView_numSize:
                    numSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ClockView_numColor:
                    numColor = ta.getColor(i, Color.BLACK);
                    break;
                case R.styleable.ClockView_scaleSize:
                    scaleSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ClockView_scaleColor:
                    scaleColor = ta.getColor(i, Color.BLACK);
                    break;
            }
        }
        ta.recycle();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR);
                    mMinute = c.get(Calendar.MINUTE);
                    mSecond = c.get(Calendar.SECOND);
                    postInvalidate();
                    Log.d("NewOrin", "Hour:" + mHour + ",Min:" + mMinute + ",Sec:" + mSecond);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mRect = new Rect();
        drawCircle(canvas);//画圆圈
        drawPoint(canvas);//画中心点
        drawScale(canvas);//画刻度
        drawNumber(canvas);//画数字
        drawHPointer(canvas);//画指针
        drawMPointer(canvas);
        drawSPointer(canvas);
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setColor(circleColor);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, mPaint);
        canvas.save();
    }

    /**
     * 画中心点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(15);
        canvas.drawPoint(getMeasuredWidth() / 2, getMeasuredHeight() / 2, mPaint);
        canvas.save();
    }

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.FILL);
            if (i == 0 || i == 15 || i == 30 || i == 45) {
                mPaint.setStrokeWidth(15);
                canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius, getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius + 80, mPaint);
            } else if (i % 5 == 0) {
                mPaint.setStrokeWidth(5);
                canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius, getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius + 40, mPaint);
            } else {
                mPaint.setStrokeWidth(3);
                canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius, getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius + 20, mPaint);
            }
            canvas.rotate(6, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }
        canvas.save();
    }

    /**
     * 画数字
     *
     * @param canvas
     */
    private void drawNumber(Canvas canvas) {
        for (int i = 0; i < 12; i++) {
            mPaint.setColor(numColor);
            mPaint.setStrokeWidth(5);
            mPaint.setTextSize(numSize);
            mPaint.getTextBounds(i + "", 0, (i + "").length(), mRect);
            canvas.drawText(i + "", getMeasuredWidth() / 2 - mRect.width() / 2, getMeasuredHeight() / 2 - radius + 160, mPaint);
            canvas.rotate(30, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            canvas.save();
        }
    }

    /**
     * 画指针
     *
     * @param canvas
     */
    private void drawHPointer(Canvas canvas) {
        int x, y;
        x = (int) (hpLength * Math.sin(Math.toRadians(((mHour + mMinute / 60) * 30))));
        y = (int) (hpLength * Math.cos(Math.toRadians(((mHour + mMinute / 60) * 30))));
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(18);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 + x, getMeasuredHeight() / 2 - y, mPaint);//画时针
    }

    /**
     * 分针
     *
     * @param canvas
     */
    private void drawMPointer(Canvas canvas) {
        int x, y;
        x = (int) (mpLength * Math.sin((Math.toRadians((mMinute + mSecond / 60) * 6))));
        y = (int) (mpLength * Math.cos((Math.toRadians((mMinute + mSecond / 60) * 6))));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(12);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 + x, getMeasuredHeight() / 2 - y, mPaint);//画分针
    }

    /**
     * 秒针
     *
     * @param canvas
     */
    private void drawSPointer(Canvas canvas) {
        int x, y;
        x = (int) (spLength * Math.sin(Math.toRadians(mSecond * 6)));
        y = (int) (spLength * Math.cos(Math.toRadians(mSecond * 6)));
        Log.d("NewOrin", "S_X=" + x + "S_Y=" + y);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(6);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 + x, getMeasuredHeight() / 2 - y, mPaint);//画分针
    }

}
