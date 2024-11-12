package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
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
            stopSelf();
        } catch (Exception e) {
        }
    }

    public void checkOnline() {
        String tempStatus = null;
        int tempStatusImageId = 0;


        //Returns connection type. 0: TYPE_NOT_CONNECTED; 1: TYPE_WIFI; 2: TYPE_MOBILE; 3: TYPE_ETHERNET
        int conn = 0;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        conn = 1;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        conn = 2;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        conn = 3;
                    }
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        conn = 1;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        conn = 2;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        conn = 3;
                    }
                }
            }
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
