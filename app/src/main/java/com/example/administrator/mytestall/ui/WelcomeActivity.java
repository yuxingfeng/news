package com.example.administrator.mytestall.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.network.NewsData;
import com.example.administrator.mytestall.unitls.CommonUtil;
import com.example.administrator.mytestall.unitls.ParseUnitls;
import com.example.administrator.mytestall.unitls.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    List<NewsBean.ResultBean.DataBean> beanBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (CommonUtil.isNetworkAvailable(this)) {
            if (CommonUtil.isMobile(this)) {
                Toast.makeText(this, "当前为移动网络注意流量哦···", Toast.LENGTH_LONG).show();
            }
            if (App.getInstance().getmCache().getAsString("time") == null) {
                AllNews();
                App.getInstance().getmCache().put("time", TimeUtil.getTime(), 3600);
            }
        } else {
            Toast.makeText(this, "当前没有网络呢···", Toast.LENGTH_LONG).show();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.launch(WelcomeActivity.this);
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getRefWatcher().watch(this);
    }

    public void AllNews() {
        App.getInstance().getData().News("top");
        App.getInstance().getData().News("guonei");
        App.getInstance().getData().News("guoji");
        App.getInstance().getData().News("keji");
        App.getInstance().getData().News("junshi");
     /*   App.getInstance().getData().OkNews("top");
        App.getInstance().getData().OkNews("guonei");
        App.getInstance().getData().OkNews("guoji");
        App.getInstance().getData().OkNews("keji");
        App.getInstance().getData().OkNews("junshi");*/

    }
}
