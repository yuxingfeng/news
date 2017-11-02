package com.example.administrator.mytestall.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.ui.WebActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2017/11/1.
 */

public class NewsNotification {
    /**
     * 悬浮式通知栏
     * @param context
     * @param url
     * @param title
     * @param content
     * @param number
     */
    public static void notify(final Context context,
                              final String url,final String title,String content, final int number) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentText(content);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon));
        builder.setAutoCancel(true);
        //震动
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle(title);
         //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        builder.setWhen(System.currentTimeMillis());
        builder.setShowWhen(true);
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(context, WebActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        notificationManager.notify(number, builder.build());
    }
    public static void notify_1(final Context context,final String title,String content, final int number) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon));
/*        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        //这句是重点
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(context, WebActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);*/
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(number, notification);
    }
}
