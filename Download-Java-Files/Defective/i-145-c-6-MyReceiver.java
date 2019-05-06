package com.msproject.myhome.mydays;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    final static String ACTION_RESTART_PERSISTENTSERVICE = "RESTARTSERVICE";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("ImmortalService", "restartService");

        if(intent.getAction().equals(ACTION_RESTART_PERSISTENTSERVICE)){
            Intent i = new Intent(context, MyService.class);
            int startTime = intent.getIntExtra("startTime", -1);
            int count = intent.getIntExtra("count", -1);
            if(startTime != -1 && count != -1){
                i.putExtra("startTime", startTime);
                i.putExtra("count", count);
            }
            context.startService(i);
        }
    }
}
