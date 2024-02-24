package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import java.io.File;
import java.util.Vector;

public class SignatureImageDelete extends Service implements Runnable {
    final String TAG = "SignatureImageDeleteLog";

    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public SignatureImageDelete() {
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
            GlobalMemberValues.logWrite(TAG, "특정기간의 서명이미지 삭제시작" + "\n");
            deleteSignature();
        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG, "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }


    public void deleteSignature() {
        String returnResult = "";

        DatabaseInit dbInit = dbInitForUploadCloud;

        // 삭제할 기준일수
        String tempAgoDays = dbInit.dbExecuteReadReturnString("select signatureimagedeletedaysago from salon_storestationsettings_paymentgateway");
        if (GlobalMemberValues.isStrEmpty(tempAgoDays)) {
            tempAgoDays = "180";
        }

        // 삭제할 기주일
        String tempDelteDays = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, (GlobalMemberValues.getIntAtString(tempAgoDays) * -1));
//        int temp_deletedays_60 = Integer.parseInt(tempAgoDays) + 60;
        int temp_deletedays_60 = 0;
        String tempDelteDays_60 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, GlobalMemberValues.getIntAtString(String.valueOf(temp_deletedays_60 * -1)));

//        String schDateStr = " strftime('%Y-%m-%d', wdate) between '2010-09-11' " +
//                " and '" + tempDelteDays + "' ";
        String schDateStr = " strftime('%Y-%m-%d', wdate) between '" + tempDelteDays_60 + "' " +
                " and '" + tempDelteDays + "' ";

        String strQuery = "select idx, signedimgdir from salon_sales_signedimg " +
                " where " + schDateStr +
                " and delyn = 'N' " +
                " order by idx asc ";
        GlobalMemberValues.logWrite(TAG, "select query : " + strQuery + "\n");
        Cursor delSignatureCursor = dbInit.dbExecuteRead(strQuery);
        while (delSignatureCursor.moveToNext()) {
            String tempIdx = delSignatureCursor.getString(0);
            String tempSignatureImageDir = delSignatureCursor.getString(1);

            // 서명이미지 삭제
            if (!GlobalMemberValues.isStrEmpty(tempSignatureImageDir) && new File(tempSignatureImageDir).exists()) {
                new File(tempSignatureImageDir).delete();
            }

            // 서명파일 삭제가 정상적으로 진행되면 delyn 값을 'Y' 로 처리
            if (!GlobalMemberValues.isStrEmpty(tempSignatureImageDir) && !(new File(tempSignatureImageDir).exists())) {
                Vector<String> strDeleteQueryVec = new Vector<String>();
                strQuery = " delete from salon_sales_signedimg " +
                        " where idx = '" + tempIdx + "' ";
                strDeleteQueryVec.addElement(strQuery);
                for (String tempQuery : strDeleteQueryVec) {
                    GlobalMemberValues.logWrite(TAG, "delete query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strDeleteQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                    GlobalMemberValues.logWrite(TAG, "Custom Error Msg : Database Error" + "\n");
                } else {
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
