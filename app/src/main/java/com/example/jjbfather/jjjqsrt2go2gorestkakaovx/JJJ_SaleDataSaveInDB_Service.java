package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class JJJ_SaleDataSaveInDB_Service extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public JJJ_SaleDataSaveInDB_Service() {
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
            saveDataInDatabase();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            //GlobalMemberValues.logWrite("UploadTipsDataToCloud", "에러내용 : " + e.getMessage().toString() + "\n");
        }
    }

    public void saveDataInDatabase() {
        // 이곳에서 데이터베이스 저장
        if (Payment.strInsertQueryVec != null && Payment.strInsertQueryVec.size() > 0) {
            // 해당 주문번호로 저장된 내역이 있으면 아래사항을 실행하지 않는다.
            boolean isPossible = false;
            int tempSalesCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select count(*) from salon_sales where salesCode = '" + Payment.mSalesCode + "' "
            ));
            if (tempSalesCount == 0) {
                isPossible = true;
            } else {
                isPossible = false;
            }

            if (isPossible) {
                for (String _query : Payment.strInsertQueryVec) {
                    GlobalMemberValues.logWrite("WANHAYEEXEDBLOGJJJJJJJJJ", "쿼리 : " + _query + "\n\n\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(Payment.strInsertQueryVec);
                GlobalMemberValues.logWrite("jjjsalespaymentlog", "returnResult : " + returnResult + "\n");
                if (returnResult == "N" || returnResult == "") {
                } else {
                    GlobalMemberValues.eloCfdScreenViewInit();
                    GlobalMemberValues.showMsgOnPaymentFinishment("Thanks for your purchase");
                }
            }

            Payment.strInsertQueryVec = null;
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SAVEDATAINDB != null && GlobalMemberValues.CURRENTSERVICEINTENT_SAVEDATAINDB != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SAVEDATAINDB.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SAVEDATAINDB);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
