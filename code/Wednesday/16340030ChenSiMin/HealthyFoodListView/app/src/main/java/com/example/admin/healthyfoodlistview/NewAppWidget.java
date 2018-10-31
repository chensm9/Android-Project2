package com.example.admin.healthyfoodlistview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String WIDGETSTATICACTION = "com.example.hasee.myapplication2.MyWidgetStaticFilter";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "当前没有任何信息");
        views.setImageViewResource(R.id.widget_image, R.drawable.full_star);
        Intent mIntent = new Intent(context, MainActivity.class).setAction(WIDGETSTATICACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateView.setOnClickPendingIntent(R.id.widget_image, pi); //设置点击事件
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me, updateView);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(WIDGETSTATICACTION)){

            Bundle bundle = intent.getExtras();
            Collection collection = (Collection) bundle.get("data");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text, "今日推荐 " + collection.getName());
            views.setImageViewResource(R.id.widget_image, R.drawable.full_star);
            Intent mIntent = new Intent(context, DetailActivity.class).setAction(WIDGETSTATICACTION);
            Bundle bundles = new Bundle();
            bundles.putSerializable("data", collection);
            mIntent.putExtras(bundles);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_image,pendingIntent);
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, views);
        }
    }
}

