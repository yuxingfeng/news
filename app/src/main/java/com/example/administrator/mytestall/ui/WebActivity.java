package com.example.administrator.mytestall.ui;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.view.ProgressWebView;

import java.lang.ref.WeakReference;


public class WebActivity extends BaseActivity {
    protected ProgressWebView event_wv_context;
    Toolbar toolbar;
    LinearLayout mRootLayout;
    MyRunnable myRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);

        initView();
        initData();

    }
    static class MyRunnable implements Runnable{
        private WeakReference<WebActivity> weakActivity;
        public MyRunnable(WebActivity activity) {
            weakActivity = new WeakReference<WebActivity>(activity);
        }

        @Override
        public void run() {
            WebActivity activity = weakActivity.get();
            if (null != activity) {
                activity.mRootLayout.setVisibility(View.VISIBLE);
                Animator animator = activity.createRevealAnimator(false, 0, 0);
                animator.start();
            }
        }
    }
    private void initView() {
       myRunnable=new MyRunnable(this);
        mRootLayout= (LinearLayout) findViewById(R.id.mRootLayout);
        toolbar= (Toolbar) findViewById(R.id.toolbar_web);
        toolbar.setNavigationIcon(R.mipmap.ahc);//设置导航栏图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });
        toolbar.setTitle("正在加载...");
        event_wv_context = (ProgressWebView) findViewById(R.id.wb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        mRootLayout.post(myRunnable);
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
    /**
     * 揭露动画
     */
    private Animator createRevealAnimator(boolean exit, int x, int y) {
        float hypot = (float) Math.hypot(mRootLayout.getHeight(),mRootLayout.getWidth());
        float startRadius = exit ? hypot : 0;
        float endRadius = exit ? 0 : hypot;

        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(mRootLayout,x,y,startRadius,endRadius);
        }
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (exit){
            animator.addListener(animatorListenerExit);
        }
        return animator;
    }
    private Animator.AnimatorListener animatorListenerExit = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            //动画结束时，销毁当前Activity
            mRootLayout.setVisibility(View.INVISIBLE);//在finish()的时候会闪屏的现象，先不可见，再销毁就不会闪屏了
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };
    public void onBackPressed() {
        Animator animator = createRevealAnimator(true, mRootLayout.getWidth()/2, mRootLayout.getHeight()/2);
        animator.start();
    }
}
