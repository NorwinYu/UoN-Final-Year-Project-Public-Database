package com.msproject.myhome.mydays;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class MyNotiControl {
    Context context;
    RemoteViews rView;
    NotificationCompat.Builder nBuilder;

    public MyNotiControl (Context parent)
    {
        this.context= parent;

        nBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("GUNMAN SERVICE")
                .setSmallIcon(R.drawable.logo3)
                .setPriority(Notification.PRIORITY_MIN) //요 부분이 핵심입니다. MAX가 아닌 MIN을 줘야 합니다.
                .setOngoing(true);

        rView = new RemoteViews(parent.getPackageName(), R.layout.activity_my_noti_control); //노티바를 내렸을때 보여지는 화면입니다.

        //set the listener
        setListener(rView);
        nBuilder.setContent(rView);
    }

    public Notification getNoti()
    {
        return nBuilder.build();
    }

    public void setListener(RemoteViews view){
        Intent i = new Intent(context, MyNotiControlActivity.class);
        WiseSaying wiseSaying = WiseSaying.getInstance();
        view.setTextViewText(R.id.wise_title, wiseSaying.getWiseSay().getSay());
        view.setTextViewText(R.id.wise_who, wiseSaying.getWiseSay().getSource());
        PendingIntent button = PendingIntent.getActivity(context, 0, i, 0);
        view.setOnClickPendingIntent(R.id.wise_title, button);
    }
}
