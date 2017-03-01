package com.example.administrator.mytestall.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
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
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.administrator.mytestall.Adapter.NewsAdapter;
import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.fragment.NewsFragment;
import com.example.administrator.mytestall.fragment.NewsFragment1;
import com.example.administrator.mytestall.fragment.NewsFragment2;
import com.example.administrator.mytestall.fragment.NewsFragment3;
import com.example.administrator.mytestall.fragment.TextFragment;
import com.example.administrator.mytestall.network.NewsClient;
import com.example.administrator.mytestall.network.NewsData;
import com.example.administrator.mytestall.unitls.ParseUnitls;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzy.widget.AlphaIndicator;
import com.lzy.widget.AlphaView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   private ViewPager viewPager;
    private AlphaView alphaView1;
    public static MainActivity instance = null;
    private int once = 0;
    private int postion = 0;//记录pager的位置
    private  NewsData data;
   private App app;
    AppCompatTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app= (App) getApplication();
        instance = this;
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("国内");
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        data=new NewsData(app);


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
                switch (position)
                {
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

        tv= (AppCompatTextView) findViewById(R.id.bt);


        final Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.imageview);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                // startActivityForResult(new Intent(MainActivity.this,Main2Activity.class),3);
               /* if (operatingAnim != null) {
                    if (once == 0) {
                        fab.startAnimation(operatingAnim);
                        once = 1;
                    } else

                    {
                        fab.clearAnimation();
                        once = 0;
                    }

                }*/
                switch (postion) {
                    case 0:
                        /*tv.setText("更新成功");
                        TimerTask task = new TimerTask(){
                            public void run() {
                                tv.setText("国内新闻");

                            }
                        };
                        Timer  timer = new Timer(true);
                        timer.schedule(task,2000); //延时1000ms后执行，1000ms执行一次*/
                        data.News("top",fab,operatingAnim);

                        Toast.makeText(MainActivity.this, postion + "", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, postion + "", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, postion + "", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, postion + "", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
