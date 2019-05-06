package com.msproject.myhome.mydays;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class SampleJobService extends JobService {
    Handler mHandler = new Handler();
    JobParameters mRunningParams;
    public static boolean displayOn;
    public static int startTime;
    final Runnable mWorker = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            doJob(mRunningParams);
        }
    };

    public SampleJobService() {
        super();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("job==", "started");
        mRunningParams = job;
        mHandler.postDelayed(mWorker, 0);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("job==", "stop");
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void doJob(JobParameters parameters) {
        try {
//원하는 작업에 대한 코드 구현
            int startTime = parameters.getExtras().getInt("startTime");
            boolean displayOn = parameters.getExtras().getBoolean("displayOn");
            int thisTime = -1;
            long now = System.currentTimeMillis();
            Bundle jobParameters = new Bundle();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
            String formatDate = sdfNow.format(date);
            String[] split = formatDate.split(":");
            thisTime = Integer.parseInt(split[0]);
            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();
            Log.d("timejob==", thisTime + "");
            Log.d("checkDisplayjob==", thisTime + "");
            if(isScreenOn){//디바이스 켜짐상태
                if(thisTime < startTime && displayOn) {//24시 넘었을때
                    if(startTime + 24 - thisTime >= 3){
                        //알람생성
                        //시작시간 초기화
                        sleepingEnd(startTime, thisTime);
                    }
                }
                else if(thisTime - startTime >= 3){
                    //알람 생성
                    sleepingEnd(startTime, thisTime);
                }
                jobParameters.putInt("startTime", thisTime);
            }
            else{
                jobParameters.putInt("startTime", startTime);

            }
            jobParameters.putBoolean("displayOn", isScreenOn);

            jobFinished(parameters, true);
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sleepingEnd(int startTime, int endTime){//30분 반올림 미구현상태
        Log.d("savestartTime==", startTime + "");
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("startTime", startTime);
        resultIntent.putExtra("endTime", endTime);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent mPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.logo3);
        //notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo3)
                .setContentTitle("수면시간을 등록해보세요.")
                .setContentText(startTime + "시부터 " + endTime + "시까지 잠을 잤나요?")//360
                .setLargeIcon(mLargeIconForNoti)
                .setAutoCancel(true)
                .setContentIntent(mPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

}
