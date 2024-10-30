package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UploadCloudDBIdxsToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    String cloudDBIdxs = "";

    public UploadCloudDBIdxsToCloud() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
        //receivedSalesCode = MainActivity.mSalesCode;

        GlobalMemberValues.logWrite("uploadapplieddownloaddatalog", "생성자 진입" + "\n");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        //Thread mThread = new Thread(this);
        //mThread.start();



        GlobalMemberValues.logWrite("uploadapplieddownloaddatalog", "onCreate..." + "\n");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


        if (intent != null) {
            cloudDBIdxs = intent.getStringExtra("cloudDBIdxs");
        }


        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            sendCloudDBIdxsToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(MainActivity.context, "Warning", e.getMessage().toString(), "Close");
        }
    }


    public void sendCloudDBIdxsToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(cloudDBIdxs)) {
                String apiParametersStr = "";

                apiParametersStr = "SCODE=" + GlobalMemberValues.SALON_CODE +
                        "&SIDX=" + GlobalMemberValues.STORE_INDEX +
                        "&STCODE=" + GlobalMemberValues.STATION_CODE +
                        "&clouddbidxs=" + cloudDBIdxs;

                String returnValue = sendCloudDBIdxsByApi(apiParametersStr);

                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CLOUDDBIDX != null && GlobalMemberValues.CURRENTSERVICEINTENT_CLOUDDBIDX != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CLOUDDBIDX.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_CLOUDDBIDX);
    }


    public String sendCloudDBIdxsByApi(String strParams) {
        GlobalMemberValues.logWrite("uploadapplieddownloaddatalog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                API_clouddbidxs_tocloud apiData = new API_clouddbidxs_tocloud(strParams);
                apiData.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiData.mFlag) {
                        returnValue = apiData.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        return returnValue;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
