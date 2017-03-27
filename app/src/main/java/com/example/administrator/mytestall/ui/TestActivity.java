package com.example.administrator.mytestall.ui;

import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TestActivity extends BaseActivity {


    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  //模拟内存泄露
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3 * 60 * 1000);
        finish();*/
    }
}
