package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TOrderDataSendService extends Service implements Runnable {
    int delayTime = 10000;

    public TOrderDataSendService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        String delayTimeStr = intent.getStringExtra("delaytime");
        if (!GlobalMemberValues.isStrEmpty(delayTimeStr)) {
            delayTime = GlobalMemberValues.getIntAtString(delayTimeStr);
        }

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            sendDataToTOrder();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            //GlobalMemberValues.logWrite("UploadTipsDataToCloud", "에러내용 : " + e.getMessage().toString() + "\n");
        }
    }

    public void sendDataToTOrder() {
        if (GlobalMemberValues.isTOrderUse()){
            GlobalMemberValues.logWrite("TORDERAPIJJJLOG", "delayTime : " + delayTime + "\n");

            try {
                Thread.sleep(delayTime);    // 10초 뒤 실행
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GlobalMemberValues.sendTOrderAPIOrderData("K");
        }

        delayTime = 10000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
