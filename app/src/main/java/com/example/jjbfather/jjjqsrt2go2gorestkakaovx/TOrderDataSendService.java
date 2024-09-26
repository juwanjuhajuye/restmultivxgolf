package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TOrderDataSendService extends Service implements Runnable {
    int delayTime = 10000;
    String eventCode = "P0401";
    String tableID = "";

    public TOrderDataSendService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (intent != null){
            String delayTimeStr = intent.getStringExtra("delaytime");
            if (!GlobalMemberValues.isStrEmpty(delayTimeStr)) {
                delayTime = GlobalMemberValues.getIntAtString(delayTimeStr);
            }

            String eventCodeStr = intent.getStringExtra("eventcode");
            if (!GlobalMemberValues.isStrEmpty(eventCodeStr)) {
                eventCode = eventCodeStr;
            }

            String tableIDStr = intent.getStringExtra("tableID");
            if (!GlobalMemberValues.isStrEmpty(tableIDStr)) {
                tableID = tableIDStr;
            }

            Thread mThread = new Thread(this);
            mThread.start();
        }

    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            //09252024 run different api calls depending on parameter input
            if (eventCode.equals("P0401")) {
                sendDataToTOrder();
            } else if (eventCode.equals("P0402")) {
                if (tableID == null || tableID.equals("")){
                    GlobalMemberValues.logWrite("TORDERAPIJJJLOG", "Trying to send table clear api call, but table id info missing!");
                } else {
                    sendTableClearToTOrder();
                }
            } else {
                sendDataToTOrder();
            }

            //09252024 finish service after api call is made
            stopSelf();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            //GlobalMemberValues.logWrite("UploadTipsDataToCloud", "에러내용 : " + e.getMessage().toString() + "\n");
        }
    }

    public void sendDataToTOrder() {
        if (GlobalMemberValues.isTOrderUse()){
            GlobalMemberValues.logWrite("TORDERAPIJJJLOG", "sendDataToTorder()");
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

    public void sendTableClearToTOrder() {
        if (GlobalMemberValues.isTOrderUse()){
            GlobalMemberValues.logWrite("TORDERAPIJJJLOG", "sendTableClearToTorder()");
            GlobalMemberValues.logWrite("TORDERAPIJJJLOG", "delayTime : " + delayTime + "\n");

            if(delayTime > 0) {
                try {
                    Thread.sleep(delayTime);    // 10초 뒤 실행
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            GlobalMemberValues.sendTOrderAPITableClear(tableID);
        }

        //delayTime = 10000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
