package com.msproject.myhome.mydays;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(context,
                    context.getResources().getString(R.string.my_channel_id));
        } else {
            notificationBuilder = new Notification.Builder(context);
        }
        Notification.Builder builder = new Notification.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.logo3).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("일정 추가").setContentText("오늘의 일정을 추가해보세요.")
                .setDefaults(Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, notificationBuilder.build());

    }
}
