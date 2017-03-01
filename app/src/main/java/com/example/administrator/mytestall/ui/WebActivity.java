package com.example.administrator.mytestall.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.view.ProgressWebView;


public class WebActivity extends AppCompatActivity {
    protected ProgressWebView event_wv_context;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main4);

        initView();
        initData();
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_web);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);//设置导航栏图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });
        toolbar.setTitle("正在加载...");
        event_wv_context = (ProgressWebView) findViewById(R.id.wb);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
           String url= intent.getStringExtra("url");
            Log.e("url",url);
            event_wv_context.loadUrl(url);
            event_wv_context.getSettings().setJavaScriptEnabled(true);
            event_wv_context.getSettings().setSupportZoom(true);
            event_wv_context.getSettings().setBuiltInZoomControls(true);
            event_wv_context.getSettings().setDisplayZoomControls(true);//显示或隐藏Zoom缩放按钮
        } else {
            this.finish();
        }

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        event_wv_context.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        event_wv_context.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);//a textview
            }
        });

    }
}
