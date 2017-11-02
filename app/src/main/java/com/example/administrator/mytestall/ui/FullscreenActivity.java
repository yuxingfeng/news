package com.example.administrator.mytestall.ui;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.lang.ref.WeakReference;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends BaseActivity {

    WebView event_wv_context;
    ActionBar toolbar;
    LinearLayout mRootLayout;
    FullscreenActivity.MyRunnable myRunnable;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mRootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        initView();
        initData();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getRefWatcher().watch(this);
    }
    static class MyRunnable implements Runnable{
        private WeakReference<FullscreenActivity> weakActivity;
        public MyRunnable(FullscreenActivity activity) {
            weakActivity = new WeakReference<FullscreenActivity>(activity);
        }

        @Override
        public void run() {
            FullscreenActivity activity = weakActivity.get();
            if (null != activity) {
                activity.mRootLayout.setVisibility(View.VISIBLE);
                Animator animator = activity.createRevealAnimator(false, 0, 0);
                animator.start();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        myRunnable=new FullscreenActivity.MyRunnable(this);
        mRootLayout= (LinearLayout) findViewById(R.id.mRootLayout_full);
        toolbar= getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("正在加载...");
        event_wv_context = (WebView) findViewById(R.id.wb_full);
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

    /*    event_wv_context.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });*/
        event_wv_context.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient(){
            /**
             * 防止加载网页时调起系统浏览器
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
