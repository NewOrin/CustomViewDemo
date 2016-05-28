package com.neworin.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.neworin.customview.R;

/**
 * 自定义View实现音频条形图效果
 * Created by NewOr on 2016/4/9.
 */

public class VolumeView extends View {

    private Paint mPaint;//画笔工具
    private float mWidth, mRectHeight, mRectWidth;
    private LinearGradient linearGradient;
    private int mRectCount = 12;
    private int offset = 5;

    public VolumeView(Context context) {
        super(context);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mRectCount; i++) {
            float currentHeight = (float) (mRectHeight * Math.random());
            canvas.drawRect((float) (mWidth * 0.4 / 2 + mRectWidth * i + offset), currentHeight, (float) (mWidth * 0.4 / 2 + mRectWidth * (i + 1)), mRectHeight, mPaint);
        }
        postInvalidateDelayed(3000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mRectHeight = getHeight();
        mRectWidth = (float) (mWidth * 0.6 / mRectCount);
        linearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
    }
}
