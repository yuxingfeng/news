package com.example.administrator.mytestall;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.widget.Toast;


import com.example.administrator.mytestall.Bean.Constants;
import com.example.administrator.mytestall.network.NewsData;
import com.example.administrator.mytestall.unitls.ACache;
import com.example.administrator.mytestall.unitls.CommonUtil;
import com.example.administrator.mytestall.unitls.TimeUtil;
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
    public static int H,W;
    private RefWatcher mRefWatcher;
    private static final String VALUE = "Harvey";
    private SharedPreferences mSharedPreferences;
    private String value;
    private  NewsData data=new NewsData(this);
    public static List<String> images=new ArrayList<>();
    public static List<String> imagesUrl=new ArrayList<>();
    public ACache mCache;


    @Override

    public void onCreate()
    {
        super.onCreate();
        //x5
        com.tencent.smtt.sdk.WebView webView = new com.tencent.smtt.sdk.WebView(this);
        int width = webView.getView().getWidth();
        getScreen(this);
        mCache=ACache.get(this);

        // 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);
        mSharedPreferences=getSharedPreferences("MyTestAll", Context.MODE_PRIVATE);
        setValue(VALUE); // 初始化全局变量

       /* String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        List list1 = Arrays.asList(tips);
        titles= new ArrayList(list1);*/
        //初始化Leak内存泄露检测工具
        instance = this;
        mRefWatcher = Constants.DEBUG ?  LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    public static List<String> getImages() {
        return images;
    }

    public static void setImages(List<String> images) {
        App.images = images;
    }

    public static List<String> getImagesUrl() {
        return imagesUrl;
    }

    public static void setImagesUrl(List<String> imagesUrl) {
        App.imagesUrl = imagesUrl;
    }

    public static App getInstance() {
        return instance;
    }
    public ACache getmCache() {
        return mCache;
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
    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H=dm.heightPixels;
        W=dm.widthPixels;
    }
    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}
