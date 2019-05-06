package com.msproject.myhome.mydays;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {//WorkManager사용?..

    private boolean isStop;
    int count;
    int startTime;
    public boolean callback;
    Thread counter;
    final int APPLICATION_ID = 12982;

    IMySleepCountService.Stub binder = new IMySleepCountService.Stub() {
        @Override
        public int getCount() throws RemoteException{
            return count;
        }

        @Override
        public int getStart() {
            return startTime;
        }

        @Override
        public boolean getCallback() throws RemoteException {
            return callback;
        }
    };


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("service==", "bind");
        return binder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("service==", "unbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int startTime = intent.getIntExtra("startTime", -1);
            int count = intent.getIntExtra("count", -1);
            if (startTime != -1 && count != -1) {
                this.startTime = startTime;
                this.count = count;

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service==", "create");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String formatDate = sdfNow.format(date);
        String[] split = formatDate.split(":");
        startTime = Integer.parseInt(split[0]);
        unregisterRestartAlarm();
        counter = new Thread(new Counter());
        counter.start();
    }

    @Override
    public void onDestroy() {
        Log.d("service==", "destroy");
        counter.interrupt();
        registerRestartAlarm();
        super.onDestroy();
    }

    public void registerRestartAlarm(){
        Log.d("restartAl==", "true");
        Intent intent = new Intent(MyService.this, MyReceiver.class);
        intent.setAction(MyReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        intent.putExtra("startTime", startTime);
        intent.putExtra("count", count);
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, APPLICATION_ID, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 1 * 1000;
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10*1000, sender);

        Log.d("startcount==", startTime + " " + count);
    }

    public void unregisterRestartAlarm(){
        Log.d("restartAl==", "false");
        Intent intent = new Intent(MyService.this, MyReceiver.class);
        intent.setAction(MyReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, APPLICATION_ID,intent,0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sleepingEnd(){//notification발생 && 쓰레드멈춤..?
        int endTime = startTime + (count / 6);
        if(endTime > 24){
            endTime -= 24;
        }
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("startTime", startTime);
        resultIntent.putExtra("endTime", endTime);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent mPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_white_24dp);
        //notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_add_white_24dp)
                .setContentTitle("수면시간을 등록해보세요.")
                .setContentText(startTime + "시부터 " + (startTime + count/360) + "시까지 잠을 잤나요?")//360
                .setLargeIcon(mLargeIconForNoti)
                .setAutoCancel(true)
                .setContentIntent(mPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

    private class Counter implements Runnable{
        boolean duplicate;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            while(!isStop){
                Log.d("count==", count + "");
                PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pm.isScreenOn();
                Log.d("ScreenOn==", isScreenOn + "");
                if(isScreenOn){
                    if(duplicate && count < 1080){//3시간=1080
                        count = 0;
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
                        String formatDate = sdfNow.format(date);
                        String[] split = formatDate.split(":");
                        startTime = Integer.parseInt(split[0]);
                        if(Integer.parseInt(split[1]) > 30){//30분이상이면 다음시간으로 취급(hour)
                            startTime++;
                        }
                        callback = true;
                    }
                    else if(duplicate && count >= 1080){
                        sleepingEnd();
                        count= 0;
                    }
                    duplicate = true;
                }
                else{
                    duplicate = false;
                    callback = false;
                }
                try {
                    Thread.sleep(10000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
