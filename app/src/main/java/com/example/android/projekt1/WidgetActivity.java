package com.example.android.projekt1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.android.projekt1.Activities.ShopList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetActivityConfigureActivity WidgetActivityConfigureActivity}
 */
public class WidgetActivity extends AppWidgetProvider {

    private static int imageIndex = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = WidgetActivityConfigureActivity.loadTitlePref(context, appWidgetId);

        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webIntent.setData(Uri.parse("http://google.com"));
        PendingIntent webPending = PendingIntent.getActivity(context, 0, webIntent, 0);

        Intent appIntent = new Intent(context, ShopList.class);
        PendingIntent appPending = PendingIntent.getActivity(context, 0, appIntent, 0);




        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);
        views.setImageViewResource(R.id.imageView_widget, R.drawable.zima);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.button_web, webPending);
        views.setOnClickPendingIntent(R.id.button_app, appPending);


        String imageClick = "imageClick";
        Intent imageIntent = new Intent(context, WidgetActivity.class);
        imageIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        imageIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
//        imageIntent.setAction(imageClick);
//        PendingIntent imagePending = PendingIntent.getService(context, 0, imageIntent, 0);
//        views.setOnClickPendingIntent(R.id.imageView_widget, imagePending);
        views.setOnClickPendingIntent(R.id.imageView_widget, getPendingSelfIntent(context, imageClick));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, WidgetActivity.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetActivityConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);

        if (intent.getAction().equals("imageClick")) {
            if (imageIndex == 0) {
                imageIndex = 1;
                views.setImageViewResource(R.id.imageView_widget, R.drawable.zima);
            }
            else {
                imageIndex = 0;
                views.setImageViewResource(R.id.imageView_widget, R.drawable.lato);
            }
        }

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, WidgetActivity.class),views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

