package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.core.app.NotificationCompat;

public class CheckWebOrdersInCloudService extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public CheckWebOrdersInCloudService() {
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

        //Thread mThread = new Thread(this);
        //mThread.start();
        String CHANNEL_ID = "CheckWebOrdersInCloudService";
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
            GlobalMemberValues.logWrite("newordercheckstr", "신규 웹오더 체크서비스 시작" + "\n");
            checkWebOrders();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("newordercheckstr", "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }


    public void checkWebOrders() {
        String newOrderString = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                API_weborder_newordercheck apiNewOrderCheckInstance = new API_weborder_newordercheck(GlobalMemberValues.STORE_INDEX);
                apiNewOrderCheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiNewOrderCheckInstance.mFlag) {
                        newOrderString = apiNewOrderCheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("newordercheckstr", "Thread Error : " + e.getMessage() + "\n");
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        GlobalMemberValues.logWrite("newordercheckstr", newOrderString + "\n");

        if (!GlobalMemberValues.isStrEmpty(newOrderString)) {
            GlobalMemberValues.openNewWebOrderList(newOrderString);
        }

        // 오더 갯수 확인 및 AppTopBar Push 버튼 깜빡임 Animation.
        int cnt = GlobalMemberValues.get_web_push_realtime_n_cnt();

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON != null){

            // animation;
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);

            if (cnt > 0){
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.startAnimation(anim);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.clearAnimation();
            }
        }
        //

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        } else {
            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER != null && GlobalMemberValues.CURRENTSERVICEINTENT_NEWWEBORDER != null) {
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_NEWWEBORDER);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
