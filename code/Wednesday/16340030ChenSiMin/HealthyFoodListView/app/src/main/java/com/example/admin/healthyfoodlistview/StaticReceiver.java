package com.example.admin.healthyfoodlistview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class StaticReceiver extends BroadcastReceiver {
    public static final String STATICACTION = "com.example.hasee.myapplication2.MyStaticFilter";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            Collection collection = (Collection) bundle.get("data");

            String channelName = "a_healthy_channel";//渠道名字
            String channelId = "12345678"; // 渠道ID
            String channelDescrption = "I_am_a_healthy_channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(channelDescrption);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            Intent mIntent = new Intent(context, DetailActivity.class).setAction(STATICACTION);
            Bundle bundles = new Bundle();
            bundles.putSerializable("data", collection);
            mIntent.putExtras(bundles);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Notification.Builder builder = new Notification.Builder(context, channelId);
            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
                    .setContentText(collection.getName())   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON（通知栏），可以用以前的素材，例如空星
                    .setContentIntent(pendingIntent)   //传递内容
                    .setAutoCancel(true); //点击通知头自动取消
            Notification notification = builder.build();
            manager.notify(0, notification);
        }
    }
}
