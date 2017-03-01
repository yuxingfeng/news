package com.example.administrator.mytestall.network;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;

import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.notification.NewMessageNotification;
import com.example.administrator.mytestall.notification.NewMessageNotification1;
import com.example.administrator.mytestall.notification.NewMessageNotification2;
import com.example.administrator.mytestall.unitls.ParseUnitls;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/12/15.
 */

public final class NewsData {
    RequestParams params=new RequestParams();
    App app;
    private static final String APPKEY ="668708dfb9c57829afe66e3143ca5b1b";
    public NewsData(App app) {
        this.app=app;
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
    }

    /**
     * 获取资讯
     * @param name
     * @return
     */
   public final Void News(final String name){
       params.put("type",name);//leixing
       NewsClient.get("http://v.juhe.cn/toutiao/index", params, new AsyncHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               String recString="";
               try {
                   recString  =new String( responseBody ,"UTF-8");
                   app.setmSharedPreferences(name,recString);
                   NewMessageNotification.notify(app.getBaseContext(),"缓存成功",1);
                /*   NewMessageNotification1.notify(app.getBaseContext(),"缓存成功",2);
                   NewMessageNotification2.notify(app.getBaseContext(),"缓存成功",3);*/

               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
                  NewMessageNotification.notify(app.getBaseContext(),"缓存失败",2);
               }


           }

           @Override
           public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               NewMessageNotification.notify(app.getBaseContext(),"访问接口异常",3);

           }

       });
       return null;
   }
    /**
     * 获取资讯
     * @param name
     * @return
     */
    public final void News(final String name, final FloatingActionButton fab, Animation operatingAnim ){
        fab.setClickable(false);
        fab.startAnimation(operatingAnim);
        params.put("type",name);//leixing
        NewsClient.get("http://v.juhe.cn/toutiao/index", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String recString="";
                try {
                    recString  =new String( responseBody ,"UTF-8");
                    app.setmSharedPreferences(name,recString);
                    NewMessageNotification.notify(app.getBaseContext(),"缓存成功",1);
                /*   NewMessageNotification1.notify(app.getBaseContext(),"缓存成功",2);
                   NewMessageNotification2.notify(app.getBaseContext(),"缓存成功",3);*/
                    fab.clearAnimation();
                    fab.setClickable(true);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    NewMessageNotification.notify(app.getBaseContext(),"缓存失败",2);
                    fab.clearAnimation();
                    fab.setClickable(true);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                NewMessageNotification.notify(app.getBaseContext(),"访问接口异常",3);
                fab.clearAnimation();
                fab.setClickable(true);
            }

        });

    }






}
