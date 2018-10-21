package com.example.admin.healthyfoodlistview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DynamicReceiver extends BroadcastReceiver {
    public static final String DYNAMICACTION = "com.example.hasee.myapplication2.MyDynamicFilter";
    private int id = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            Bundle bundle = intent.getExtras();
            Collection collection = (Collection) bundle.get("data");

            String channelName = "another_healthy_channel";//渠道名字
            String channelId = "16340030"; // 渠道ID
            String channelDescrption = "I_am_another_healthy_channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(channelDescrption);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            Intent mIntent = new Intent(context, MainActivity.class).setAction(DYNAMICACTION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context, channelId);
            builder.setContentTitle("已收藏")   //设置通知栏标题：发件人
                    .setContentText(collection.getName())   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON（通知栏）
                    .setContentIntent(pendingIntent)   //传递内容
                    .setAutoCancel(true); //点击通知头自动取消
            Notification notification = builder.build();
            manager.notify(id, notification);
            id++;
        }
    }
}