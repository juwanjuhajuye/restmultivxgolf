package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class CheckNewSideMenuOrderCloudService extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public CheckNewSideMenuOrderCloudService() {
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

        String CHANNEL_ID = "CheckNewSideMenuOrderCloudService";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("").setContentText("").build();
            startForeground(2,notification); // 추가
        }
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
            GlobalMemberValues.logWrite("new SideOrder", "신규 new SideOrder 체크서비스 시작" + "\n");
            check_Curside();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("new SideOrder", "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }

    public void check_Curside() {
        String newOrderString = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                API_Check_NewSideMenuOrder_ForAndroid api_check_newSideMenuOrder_forAndroid = new API_Check_NewSideMenuOrder_ForAndroid(GlobalMemberValues.STORE_INDEX);
                api_check_newSideMenuOrder_forAndroid.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME + 4000); //1초마다 실행
                    if (api_check_newSideMenuOrder_forAndroid.mFlag) {
                        newOrderString = api_check_newSideMenuOrder_forAndroid.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("new SideOrder check error", "Thread Error : " + e.getMessage() + "\n");
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        GlobalMemberValues.logWrite("new SideOrder check error", newOrderString + "\n");

        if (!GlobalMemberValues.isStrEmpty(newOrderString)) {
            GlobalMemberValues.openNewSideMenuStr(newOrderString);
        }



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        } else {
            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER != null && GlobalMemberValues.CURRENTSERVICEINTENT_NEWSIDEORDER != null) {
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_NEWSIDEORDER);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
