package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class CheckNewTableOrderInCloudService extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public CheckNewTableOrderInCloudService() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        String CHANNEL_ID = "CheckNewTableOrderInCloudService";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("").setContentText("").build();
            startForeground(2,notification); // 추가
        }
        //Thread mThread = new Thread(this);
        //mThread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            GlobalMemberValues.logWrite("newtableorderstr", "신규 웹오더 체크서비스 시작" + "\n");
            checkNewTableOrder();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("newtableorderstr", "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }


    public void checkNewTableOrder() {
        String newTableOrderString = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                API_weborder_newtableorder apiIns = new API_weborder_newtableorder(GlobalMemberValues.STORE_INDEX);
                apiIns.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiIns.mFlag) {
                        newTableOrderString = apiIns.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("newtableorderstr", "Thread Error : " + e.getMessage() + "\n");
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        GlobalMemberValues.logWrite("newtableorderlogforexe", newTableOrderString + "\n");

        if (!GlobalMemberValues.isStrEmpty(newTableOrderString)) {
            GlobalMemberValues.logWrite("newtableorderlogforexe", newTableOrderString + "\n");
            // 테이블페이가 있을 경우 이곳에서 처리
            GlobalMemberValues.openNewTableOrderStr(newTableOrderString);
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        } else {
            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWTABLEORDER != null && GlobalMemberValues.CURRENTSERVICEINTENT_NEWTABLEORDER != null) {
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWTABLEORDER.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_NEWTABLEORDER);
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
