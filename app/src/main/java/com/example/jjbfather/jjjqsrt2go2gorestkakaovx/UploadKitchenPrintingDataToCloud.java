package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadKitchenPrintingDataToCloud extends Service implements Runnable {
    final String TAG = "UploadKitchenPrintingDataToCloudLog";
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public UploadKitchenPrintingDataToCloud() {
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
            GlobalMemberValues.logWrite(TAG, "주방프린팅 데이터 업로드 시작" + "\n");
            sendKitchenPrintingDataToCloud();
        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG, "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }

    public void sendKitchenPrintingDataToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // 이곳에서 클라우드로 주방프린터 데이터 전송

            String sqlQuery = "";

            sqlQuery = " select salesCode, scode, sidx, stcode, jsonstr, idx from salon_sales_kitchen_json where isCloudUpload = 0 ";
            Cursor salonSalesDetailSalesCodeCursor = dbInitForUploadCloud.dbExecuteRead(sqlQuery);
            while (salonSalesDetailSalesCodeCursor.moveToNext()) {
                JSONObject jsonReceipt = null;
                int cloudSentResult = 0;

                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(0), 1);
                String tempScode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(1), 1);
                String tempSidx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(2), 1);
                String tempStcode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(3), 1);
                String tempJsonstr = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(4), 1);
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(5), 1);

                if (!GlobalMemberValues.isStrEmpty(tempJsonstr)) {
                    try {
                        jsonReceipt = new JSONObject(tempJsonstr);
                        jsonReceipt.put("kitchendatascode", tempScode);
                        jsonReceipt.put("kitchendatasidx", tempSidx);
                        jsonReceipt.put("kitchendatastcode", tempStcode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                GlobalMemberValues.logWrite(TAG, "jsonstr : " + jsonReceipt.toString() + "\n");

                API_kitchenprintingdata_tocloud apiKitchenprintingdataTocloud = new API_kitchenprintingdata_tocloud(jsonReceipt.toString());
                apiKitchenprintingdataTocloud.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiKitchenprintingdataTocloud.mFlag) {
                        cloudSentResult = apiKitchenprintingdataTocloud.cloudSentResult;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite(TAG, "Thread Error : " + e.getMessage() + "\n");
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }

                if (cloudSentResult > 0) {
                    // 전송실패
                } else {
                    // 전송성공시 클라우드 업로드 했다고 표시
                    GlobalMemberValues.setKitchenDataUploadResult(tempIdx);
                }
            }
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
