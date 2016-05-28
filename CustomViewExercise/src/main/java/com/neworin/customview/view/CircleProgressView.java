package com.neworin.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 重绘View
 * Created by NewOr on 2016/4/9.
 */
public class CircleProgressView extends View {

    private int mMeasureHeight;
    private int mMeasureWidth;

    /**
     * 画圆
     */
    private Paint mCirclePaint;
    private float mCircleXY;
    private float mRadius;
    /**
     * 画圆弧
     */
    private Paint mArcPaint;
    private RectF mArcRectF;//画矩形工具，这里用作画圆弧
    private float mSweepAngle;//圆弧角度
    private float mSweepValue = 66;
    /**
     * 画文字
     */
    private Paint mTextPaint;
    private String mShowText;
    private float mShowTextSize;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * MeasureSpec帮助我们测量View
         */
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);//获取绘制宽度，即屏幕宽度
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);//获取绘制高度
        Log.d("NewOrin", "绘制宽度=" + mMeasureWidth + " 绘制长度=" + mMeasureHeight);
        setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        //绘制弧线
        canvas.drawArc(mArcRectF, 270, mSweepAngle, false, mArcPaint);
        //绘制文字
        canvas.drawText(mShowText, 0, mShowText.length(), mCircleXY, mCircleXY + (mShowTextSize / 4), mTextPaint);
    }

    private void initView() {
        float length = 0;
        if (mMeasureHeight >= mMeasureWidth) {
            length = mMeasureWidth;
        } else {
            length = mMeasureHeight;
        }
        mCircleXY = length / 2;//圆弧半径
        mRadius = (float) (length * 0.5 / 2);//圆半径
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);//反锯齿
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_light));

        mArcRectF = new RectF((float) (length * 0.1), (float) (length * 0.1), (float) (length * 0.9), (float) (length * 0.9));//设置绘制圆弧参数
        mSweepAngle = (mSweepValue / 100f) * 360f;//圆弧角度
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mArcPaint.setStrokeWidth((float) (length * 0.1));//设置圆弧宽度
        mArcPaint.setStyle(Paint.Style.STROKE);

        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private String setShowText() {
        this.invalidate();
        return "Custom View";
    }

    public void forceInvalidate() {
        this.invalidate();
    }

    private float setShowTextSize() {
        this.invalidate();
        return 50;
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            //设置默认值
            mSweepValue = 25;
        }
        this.invalidate();
    }


}
