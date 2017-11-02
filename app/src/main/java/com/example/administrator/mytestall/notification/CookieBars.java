package com.example.administrator.mytestall.notification;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.ui.MainActivity;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;

/**
 * cookie_bar
 * Created by Administrator on 2017/10/12.
 */

public class CookieBars {

    public  CookieBars(Activity activity, int title,int message,int second,int background_color,
                      int action_color,int  title_color,int action,OnActionClickListener listener ) {

        new CookieBar.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setDuration(second)
                .setBackgroundColor(background_color)
                .setActionColor(action_color)
                .setTitleColor(title_color)
                .setAction(action,listener )
                .show();
    }

    public CookieBars(Activity activity,int second,int cookie_title,int image,int cookie_message,int  gravity
    ,int cookie_action,OnActionClickListener listener) {
        new CookieBar
                .Builder(activity)
                .setDuration(second)
                .setTitle(cookie_title)
                .setIcon(image)
                .setMessage(cookie_message)
                .setLayoutGravity(gravity)
                .setAction(cookie_action, listener)
                .show();
    }
}
