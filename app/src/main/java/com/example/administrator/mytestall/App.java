package com.example.administrator.mytestall;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.example.administrator.mytestall.Bean.Constants;
import com.example.administrator.mytestall.network.NewsData;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by Administrator on 2016/12/15.
 */

public class App extends Application {

    private static App instance;

    private RefWatcher mRefWatcher;
    private static final String VALUE = "Harvey";
    private SharedPreferences mSharedPreferences;
    private String value;
    private  NewsData data=new NewsData(this);
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();
    @Override
    public void onCreate()
    {
        super.onCreate();

        // 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);
        mSharedPreferences=getSharedPreferences("MyTestAll", Context.MODE_PRIVATE);
        setValue(VALUE); // 初始化全局变量
        AllNews();
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        List list1 = Arrays.asList(tips);
        titles= new ArrayList(list1);
        //初始化Leak内存泄露检测工具
        instance = this;
        mRefWatcher = Constants.DEBUG ?  LeakCanary.install(this) : RefWatcher.DISABLED;
    }
    public static App getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }
    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }


    /**@param key
     *
     */
    public String getmSharedPreferences(String key) {
        return mSharedPreferences.getString(key, null);
    }

    /**
     * @param key
     * @param data
     */
    public void setmSharedPreferences(String key, String data) {
        mSharedPreferences.edit().putString(key, data).commit();
    }
    public void AllNews(){
        data.News("top");
        data.News("guonei");
        data.News("guoji");
        data.News("keji");

    }

    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}
