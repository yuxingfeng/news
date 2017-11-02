package com.example.administrator.mytestall.ui;


import android.app.PendingIntent;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.AppCompatTextView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;



import com.example.administrator.mytestall.App;

import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.fragment.NewsFragment;
import com.example.administrator.mytestall.fragment.NewsFragment1;
import com.example.administrator.mytestall.fragment.NewsFragment2;
import com.example.administrator.mytestall.fragment.NewsFragment3;

import com.example.administrator.mytestall.network.NewsData;

import com.example.administrator.mytestall.notification.CookieBars;
import com.lzy.widget.AlphaIndicator;

import java.util.ArrayList;

import java.util.List;

import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    public static MainActivity instance = null;
    private int postion = 0;//记录pager的位置
    private NewsData data;
    private App app;


    /**
     * 主界面不需要支持滑动返回，重写该方法永久禁用当前界面的滑动返回功能
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App) getApplication();
        instance = this;
        initViews();

    }

    public void initViews(){
        //悬浮按钮
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("今日资讯");
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //
        data = new NewsData(app);


        //底部导航
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        final AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                postion = position;
                switch (position) {
                    case 0:
                        toolbar.setTitle("国内");
                        break;
                    case 1:
                        toolbar.setTitle("头条");
                        break;
                    case 2:
                        toolbar.setTitle("国际");
                        break;
                    case 3:
                        toolbar.setTitle("科技");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        final Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.imageview);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (postion) {
                    case 0:
                        new CookieBars(MainActivity.this,5000, R.string.cookie_title, R.mipmap.ic_launcher,
                                R.string.cookie_message, Gravity.TOP, R.string.cookie_action, new OnActionClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(getApplicationContext(), "点击后，我更帅了!", Toast.LENGTH_LONG).show();
                            }
                        });

                        break;
                    case 1:
                        new CookieBar.Builder(MainActivity.this)
                                .setMessage(R.string.cookie_message)
                                .setDuration(10000)
                                .setActionWithIcon(R.mipmap.ic_action_close, new OnActionClickListener() {
                                    @Override
                                    public void onClick() {
                                        Toast.makeText(getApplicationContext(), "点击后，我更帅了!", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();
                        break;
                    case 2:
                        new CookieBars(MainActivity.this, R.string.cookie_title, R.string.cookie_message, 3000, R.color.blued,
                                R.color.white, R.color.card, R.string.cookie_action,
                                new OnActionClickListener() {
                                    @Override
                                    public void onClick() {
                                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        break;
                    case 3:
                        new CookieBar.Builder(MainActivity.this)
                                .setTitle(R.string.cookie_title)
                                .setMessage(R.string.cookie_message)
                                .show();
                        break;
                }


            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", requestCode + "");
        Log.e("resultCode", resultCode + "");
        if (resultCode == RESULT_OK && requestCode == 3) {

            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            new CookieBars(MainActivity.this,5000, R.string.cookie_title, R.drawable.icon,
                    R.string.cookie_message, Gravity.BOTTOM, R.string.cookie_action, new OnActionClickListener() {
                @Override
                public void onClick() {
                 /*   App.getInstance().getmCache().remove("top");
                    App.getInstance().getmCache().remove("guonei");
                    App.getInstance().getmCache().remove("guoji");
                    App.getInstance().getmCache().remove("keji");*/
                    App.getInstance().getmCache().remove("time");
                    Toast.makeText(getApplicationContext(), "清除成功", Toast.LENGTH_LONG).show();
                }
            });



        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void launch(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.overridePendingTransition(R.anim.apha_in, R.anim.apha_out);
        activity.finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getRefWatcher().watch(this);
    }


    /**
     * 底部菜单
     */
    private class MainAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new NewsFragment1());
            fragments.add(new NewsFragment());
            fragments.add(new NewsFragment2());
            fragments.add(new NewsFragment3());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * 按俩次返回 退出程序
     */
    private double mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                /*if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    // Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    showExit();
                }*/
            showExit();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出
     */
    private void showExit() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

}
