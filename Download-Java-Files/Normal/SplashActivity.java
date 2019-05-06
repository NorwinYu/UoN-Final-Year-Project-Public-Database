package com.msproject.myhome.mydays;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class SplashActivity extends Activity {
    private final int REQUEST_ACCESS_FINE_LOCATION = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionNoti = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY);
            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                // 권한 없음
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);
            } else{
                // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음
            }
        }
        // OS가 Mar shmallow 이전일 경우 권한체크를 하지 않는다.
        else{

        }
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Handler hd = new Handler();
            hd.postDelayed(new splashhandler(), 3000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//위치 권한요청에 대한 반환메소드
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"승인이 허가되었습니다.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    SplashActivity.this.finish();
                } else {
                    Toast.makeText(this,"승인되지 않았습니다.",Toast.LENGTH_LONG).show();
                    finish();
                }
                return;

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
