package com.neworin.customview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.neworin.customview.R;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;
    public static final String FLAG = "flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this, TestActivity.class);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_overwrite:
                mIntent.putExtra(FLAG, 0);
                startActivity(mIntent);
                break;
            case R.id.btn_volume:
                mIntent.putExtra(FLAG, 1);
                startActivity(mIntent);
                break;
            case R.id.btn_scrollerview:
                mIntent.putExtra(FLAG, 2);
                startActivity(mIntent);
                break;
            case R.id.btn_clock:
                mIntent.putExtra(FLAG, 3);
                startActivity(mIntent);
                break;
            case R.id.btn_viewgroup01:
                mIntent.putExtra(FLAG, 4);
                startActivity(mIntent);
                break;
        }
    }
}
