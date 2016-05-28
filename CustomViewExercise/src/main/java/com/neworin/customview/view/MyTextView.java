package com.neworin.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义TextView
 * Created by NewOr on 2016/4/7.
 */
public class MyTextView extends TextView {

    private Paint mPaint1;
    private Paint mPaint2;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 在构造方法中完成必要对象的初始化工作，如初始化画笔等等
         */
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(
                android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    /**
     * 为了改变原生的绘制行为，在系统调用super.onDraw(canvas)方法前，也就是在绘制文字之下 绘制两个不同大小的矩形
     */
    @Override
    protected void onDraw(Canvas canvas) {
// 绘制外层图形
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        }
        // 绘制内层图形
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10,
                    mPaint2);
        }
        canvas.save();
        // 绘制文字前平移10像素
        canvas.translate(10, 0);
        super.onDraw(canvas);// //父类完成的方法， 执行绘制文字工作
        canvas.restore();
    }
}
