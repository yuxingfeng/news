package com.example.administrator.mytestall.network;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.notification.CookieBars;
import com.example.administrator.mytestall.notification.NewMessageNotification;
import com.example.administrator.mytestall.notification.NewMessageNotification1;
import com.example.administrator.mytestall.notification.NewMessageNotification2;
import com.example.administrator.mytestall.notification.NewsNotification;
import com.example.administrator.mytestall.ui.MainActivity;
import com.example.administrator.mytestall.unitls.ParseUnitls;
import com.example.administrator.mytestall.unitls.TimeUtil;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/12/15.
 */

public final class NewsData {
    String newsUrl = "http://v.juhe.cn/toutiao/index";
    RequestParams params = new RequestParams();
    App app;
    private static final String APPKEY = "668708dfb9c57829afe66e3143ca5b1b";

    public NewsData(App app) {
        this.app = app;
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
    }

    public static byte[] ObjectToByte(java.lang.Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }


    /**
     * 获取资讯
     *
     * @param name
     * @return
     */
    public final Void News(final String name) {
        params.put("type", name);//leixing
        String timeString = app.mCache.getAsString("time");
        if (timeString == null) {
            NewsClient.get(newsUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String recString = "";
                    try {
                        recString = new String(responseBody, "UTF-8");
                        Log.e("recString", name + recString);
                        app.mCache.put(name, recString);
                        app.mCache.put("time", TimeUtil.getTime(), 3600);

                        NewsNotification.notify_1(app.getBaseContext(),"今日资讯","更新成功！",2);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        NewsNotification.notify_1(app.getBaseContext(),"今日资讯","更新失败！",2);
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    NewMessageNotification.notify(app.getBaseContext(), "访问接口异常", 3);

                }

            });
        } else {
            Toast.makeText(app.getApplicationContext(), "暂无更新", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 获取资讯
     *
     * @param name
     * @return
     */
    public final void News(final String name, final FloatingActionButton fab, Animation operatingAnim) {
        fab.setClickable(false);
        fab.startAnimation(operatingAnim);
        String timeString = app.mCache.getAsString("time");
        if (timeString == null) {
            app.mCache.put("time", TimeUtil.getTime(), 3600);
            params.put("type", name);//leixing
            NewsClient.get(newsUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String recString = "";
                    try {
                        recString = new String(responseBody, "UTF-8");
                        app.mCache.put(name, recString);
                        NewMessageNotification.notify(app.getBaseContext(), "缓存成功", 1);
                        fab.clearAnimation();
                        fab.setClickable(true);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        NewMessageNotification.notify(app.getBaseContext(), "缓存失败", 2);
                        fab.clearAnimation();
                        fab.setClickable(true);
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    NewMessageNotification.notify(app.getBaseContext(), "访问接口异常", 3);
                    fab.clearAnimation();
                    fab.setClickable(true);
                }

            });
        } else {
            fab.clearAnimation();
            fab.setClickable(true);
            Toast.makeText(app.getApplicationContext(), "暂无更新", Toast.LENGTH_SHORT).show();
        }

    }


}
