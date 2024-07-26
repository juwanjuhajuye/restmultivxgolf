package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UploadCartDataDeleteToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public UploadCartDataDeleteToCloud() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
        //receivedSalesCode = MainActivity.mSalesCode;

        GlobalMemberValues.logWrite("uploadcartdatalog", "생성자 진입" + "\n");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        //Thread mThread = new Thread(this);
        //mThread.start();

        GlobalMemberValues.logWrite("uploadcartdatalog", "onCreate..." + "\n");
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
            sendCartDataDeleteToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(MainActivity.context, "Warning", e.getMessage().toString(), "Close");
        }
    }


    public void sendCartDataDeleteToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            String apiParametersStr = "";
            String strUpdateQuery = "";
            Vector<String> apiVec = new Vector<String>();

            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;

            String strQueryString = " select idx, holdcode, sidx, stcode, cartIdx, alldelyn, tordercode " +
                    " from temp_salecart_del " +
                    " where isCloudUpload = 0 " +
                    " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                    " order by idx asc";
            GlobalMemberValues.logWrite("uploadcartdatalog", "sql : " + strQueryString + "\n");

//            Cursor cartCursor = dbInitForUploadCloud.dbExecuteRead(strQueryString);
            ResultSet cartCursor = MssqlDatabase.getResultSetValue(strQueryString);
            try {
                while (cartCursor.next()) {
                    String delidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,0), 1);
                    String holdcodefrompos = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,1), 1);
                    String sidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,2), 1);
                    String stcode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,3), 1);
                    String cartIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,4), 1);
                    String alldelyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,5), 1);
                    String tordercode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,6), 1);

                    if (GlobalMemberValues.isStrEmpty(holdcodefrompos)) {
                        holdcodefrompos = "";
                    }
                    if (GlobalMemberValues.isStrEmpty(sidx)) {
                        sidx = "";
                    }
                    if (GlobalMemberValues.isStrEmpty(cartIdx)) {
                        cartIdx = "";
                    }
                    if (GlobalMemberValues.isStrEmpty(alldelyn)) {
                        alldelyn = "N";
                    }
                    if (GlobalMemberValues.isStrEmpty(tordercode)) {
                        tordercode = "";
                    }

                    apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + sidx +
                            "&STCODE=" + stcode +
                            "&holdcodefrompos=" + holdcodefrompos +
                            "&cartidx=" + cartIdx +
                            "&alldelyn=" + GlobalMemberValues.getEncoderUtf8(alldelyn) +
                            "&tordercode=" + GlobalMemberValues.getEncoderUtf8(tordercode);

                    strUpdateQuery = "delete from temp_salecart_del where idx = '" + delidx + "' ";
                    apiVec.addElement(apiParametersStr + "|||" + strUpdateQuery);
                    GlobalMemberValues.logWrite("uploadcartdatalog", "data string : " + apiParametersStr + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //07052024 close resultset
            try {
                if(!cartCursor.isClosed()){
                    cartCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            if (apiVec.size() > 0) {
                Vector<String > apiUpdateQueryVec = new Vector<String>();
                for (String params : apiVec) {
                    GlobalMemberValues.logWrite("uploadcartdatalog", "params : " + params + "\n");
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        String[] tempParams = params.split(GlobalMemberValues.STRSPLITTER2);
                        // API 로 넘기는 문자열에는 공백이 있으면 안되므로
                        // 공백부분을 ||| 으로 처리하고
                        // 웹 API 처리하는 부분에서 ||| 을 공백으로 치환한다.
                        String paramStr = GlobalMemberValues.getReplaceText(tempParams[0], " ", "|||");
                        String returnValue = sendCartDataDeleteByApi(paramStr);
                        GlobalMemberValues.logWrite("uploadcartdatalog", "returnValue : " + returnValue + "\n");
                        if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                            apiUpdateQueryVec.addElement(tempParams[1]);
                        }
                    }
                }

                for (String strQuery : apiUpdateQueryVec) {
                    GlobalMemberValues.logWrite("uploadcartdatalog", "query : " + strQuery + "\n");
                }
                String returnResult = "";
                // 트랜잭션으로 DB 처리한다.
//                returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                returnResult = MssqlDatabase.executeTransaction(apiUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {
                } else { // 정상처리일 경우 Sale History 리스트 리로드
                    // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                    // 서비스 중지 전 3초간 기다린다.
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                        // 07192024
//                        if (GlobalMemberValues.isTOrderUse()){
//                            GlobalMemberValues.sendTOrderAPIOrderData("K");
//                        }
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CARTDEL != null && GlobalMemberValues.CURRENTSERVICEINTENT_CARTDEL != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CARTDEL.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_CARTDEL);
    }


    public String sendCartDataDeleteByApi(String strParams) {
        GlobalMemberValues.logWrite("uploadcartdatalog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                API_cartdatauploaddel_tocloud apiSalesDataInstance = new API_cartdatauploaddel_tocloud(strParams);
                apiSalesDataInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiSalesDataInstance.mFlag) {
                        returnValue = apiSalesDataInstance.mReturnValue;
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
