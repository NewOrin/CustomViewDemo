package com.neworin.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.neworin.customview.view.CircleProgressView;
import com.neworin.customview.R;
import com.neworin.customview.view.ClockView;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra("flag", -1);
        switch (flag) {
            case 0:
                setContentView(R.layout.circle_view);
                CircleProgressView id_circle = (CircleProgressView) findViewById(R.id.circle);
                id_circle.setSweepValue(80);
                break;
            case 1:
                setContentView(R.layout.volume_view);
                break;
            case 2:
                setContentView(R.layout.scrollview_view);
                break;
            case 3:
                setContentView(R.layout.clock_view);
                initClockView();
                break;
            case 4:
                setContentView(R.layout.viewgroup01);
                initClockView();
                break;
        }
    }

    /**
     * ClockView
     */
    private void initClockView() {
        clockView = (ClockView) findViewById(R.id.clock_view);
    }
}
