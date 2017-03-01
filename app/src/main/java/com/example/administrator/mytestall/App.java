package com.example.administrator.mytestall;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.mytestall.network.NewsData;

/**
 * Created by Administrator on 2016/12/15.
 */

public class App extends Application {
    private static final String VALUE = "Harvey";
    private SharedPreferences mSharedPreferences;
    private String value;
    private  NewsData data=new NewsData(this);
    @Override
    public void onCreate()
    {
        super.onCreate();
        mSharedPreferences=getSharedPreferences("MyTestAll", Context.MODE_PRIVATE);
        setValue(VALUE); // 初始化全局变量
        AllNews();
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
