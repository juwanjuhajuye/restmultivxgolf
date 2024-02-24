package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CheckOnlineStatus extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_ETHENET = 3;
    public static int TYPE_NOT_CONNECTED = 0;

    public CheckOnlineStatus() {
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
            GlobalMemberValues.logWrite("newreservationcheckstr", "신규 온라인 체크서비스 시작" + "\n");
            checkOnline();
        } catch (Exception e) {
        }
    }

    public void checkOnline() {
        String tempStatus = null;
        int tempStatusImageId = 0;

        int conn = NetworkUtil.getConnectivityStatus(context);
        GlobalMemberValues.logWrite("newreservationcheckstr", "conn : " + conn + "\n");
        if (conn > 0) {
            if(conn == TYPE_WIFI) {
                tempStatus = "WIFI";
                tempStatusImageId = R.drawable.aa_images_main_wifi;
            } else if (conn == TYPE_MOBILE) {
                tempStatus = "3G/LTE";
                tempStatusImageId = R.drawable.aa_images_main_lte;
            } else {
                if (GlobalMemberValues.isOnlineInternet("http://clients3.google.com/generate_204")) {
                    tempStatus = "ONLINE";
                    tempStatusImageId = R.drawable.aa_images_main_online;
                } else {
                    tempStatus = "NOT CONNECTED";
                    tempStatusImageId = R.drawable.aa_images_main_disconnect;
                }
            }
        } else {
            // 인터넷 연결 안됨...
            tempStatus = "NOT CONNECTED";
            tempStatusImageId = R.drawable.aa_images_main_disconnect;
        }

        GlobalMemberValues.GLOBALNETWORKSTATUS = conn;

        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK != null && GlobalMemberValues.CURRENTSERVICEINTENT_ONLINECHECK != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_ONLINECHECK);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
