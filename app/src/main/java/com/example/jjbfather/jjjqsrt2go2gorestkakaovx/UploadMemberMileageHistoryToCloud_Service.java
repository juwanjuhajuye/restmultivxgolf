package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UploadMemberMileageHistoryToCloud_Service extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public UploadMemberMileageHistoryToCloud_Service() {
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
            UploadMemberMileageHistoryToCloud uploadInstance = new UploadMemberMileageHistoryToCloud();
            uploadInstance.sendMemberMileageHistoryDataToCloud(true);
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            //GlobalMemberValues.logWrite("membermileageuploadlog", "에러내용 : " + e.getMessage().toString() + "\n");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
