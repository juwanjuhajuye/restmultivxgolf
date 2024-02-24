package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import java.util.Vector;

public class UploadTipsDataToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public UploadTipsDataToCloud() {
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
            sendTipsDataToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            //GlobalMemberValues.logWrite("UploadTipsDataToCloud", "에러내용 : " + e.getMessage().toString() + "\n");
        }
    }


    public void sendTipsDataToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            GlobalMemberValues.logWrite("UploadTipsDataToCloud", "여기실행됨 - 5." + "\n");

            // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
            String schDateStr = GlobalMemberValues.getSearchDateString(dbInitForUploadCloud, "wdate");
            // -------------------------------------------------------------------------------------------------------------------------------------------------

            /**
            // 아래 사항을 실행하기 전에 먼저 클라우드에 업로드 되지 않은 데이터들중 ---------------------------------------------------------------------------
            // codeforupload 필드의 값이 없는 데이터에 값을 넣는다..

            String insStrQuery = "";
            String tempSalonSalesTipQuery = "select idx from salon_sales_tip " +
                    " where isCloudUpload = 0 " +
                    " and (codeforupload = '' or codeforupload is null) " +
                    " and " + schDateStr +
                    " order by idx asc";
            GlobalMemberValues.logWrite("UploadTipsDataToCloud", "query : " + tempSalonSalesTipQuery + "\n");
            Cursor tempSalesSalesTipCursor = dbInitForUploadCloud.dbExecuteRead(tempSalonSalesTipQuery);
            while (tempSalesSalesTipCursor.moveToNext()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(tempSalesSalesTipCursor.getString(0), 1);
                String tempCodeForUpload = GlobalMemberValues.makeSalesCode() + tempIdx;

                // 쿼리문자열을 담을 벡터 변수생성
                Vector<String> strInsertQueryVec = new Vector<String>();
                insStrQuery = " update salon_sales_tip set " +
                        " codeforupload = '" + tempCodeForUpload + "' " +
                        " where idx = '" + tempIdx + "' ";
                strInsertQueryVec.addElement(insStrQuery);
                for (String tempQuery : strInsertQueryVec) {
                    GlobalMemberValues.logWrite("CustomerMemo", "query : " + tempQuery + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                } else {
                }
            }
            // -------------------------------------------------------------------------------------------------------------------------------------------------
             **/


            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
            String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;

            String strSalonSalesTipQuery = "select salesCode, employeeIdx, employeeName, usedCash, usedCard, " +
                    " wdate, strftime('%m-%Y', wdate), idx, cardCom, codeforupload " +
                    " from salon_sales_tip " +
                    " where isCloudUpload = 0 " +
                    " and " + schDateStr +
                    " order by idx asc";
            GlobalMemberValues.logWrite("UploadTipsDataToCloud", "query : " + strSalonSalesTipQuery + "\n");
            Cursor salesSalesTipCursor = dbInitForUploadCloud.dbExecuteRead(strSalonSalesTipQuery);
            while (salesSalesTipCursor.moveToNext()) {
                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(0), 1);
                String tempApi_EMP_COD = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(1), 1);
                String tempApi_EMP_NAM = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(2), 1);
                String tempApi_TIP_CAS = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(3), 1);
                String tempApi_TIP_CAR = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(4), 1);
                String tempApi_SER_YMD = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(5), 1);
                String tempApi_SER_MON = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(6), 1);
                tempApi_SER_MON = GlobalMemberValues.getReplaceText(tempApi_SER_MON, "-", "");
                String tempApi_POS_IDX = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(7), 1);
                String tempApi_TIPCARD_COM = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(8), 1);
                String tempApi_CODEFORUPLOAD = GlobalMemberValues.getDBTextAfterChecked(salesSalesTipCursor.getString(9), 1);

                if (!GlobalMemberValues.isStrEmpty(tempSalesCode)) {
                    String tempApi_IDG_COD = tempSalesCode;

                    String strSaleSalesDetail = "select sum(commissionAmount), " +
                            " sum(totalAmount), " +
                            " customerId, customerName, customerPhone " +
                            " from salon_sales_detail " +
                            " where salesCode = '" + tempSalesCode + "' " +
                            " and employeeIdx = '" + tempApi_EMP_COD + "' " +
                            " and employeeName = '" + tempApi_EMP_NAM + "' ";

                    Cursor salonSalesDetailCursor;
                    salonSalesDetailCursor = dbInitForUploadCloud.dbExecuteRead(strSaleSalesDetail);
                    if (salonSalesDetailCursor.getCount() > 0 && salonSalesDetailCursor.moveToFirst()) {
                        String tempApi_COM_AMT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(0), 1);
                        String tempApi_EMP_AMT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(1), 1);
                        String strTotalAmtQuery = "select sum(totalAmount) from salon_sales_detail where salesCode = '" + tempSalesCode + "' ";
                        String tempApi_TOT_AMT = dbInitForUploadCloud.dbExecuteReadReturnString(strTotalAmtQuery);

                        String tempApi_CUS_COD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(2), 1);
                        String tempApi_CUS_NAM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(3), 1);
                        String tempApi_PHN_NUM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(4), 1);
                        String tempApi_CEL_NUM = tempApi_PHN_NUM;

                        if (GlobalMemberValues.isStrEmpty(tempApi_EMP_COD)) {
                            tempApi_EMP_COD = "E0001";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempApi_CUS_COD)) {
                            tempApi_CUS_COD = "C0001";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempApi_CUS_NAM)) {
                            tempApi_CUS_NAM = "Walk in";
                        }

                        String apiParametersStr = "SCODE=" + tempApi_SCODE +
                                "&SIDX=" + tempApi_SIDX +
                                "&IDG_COD=" + tempApi_IDG_COD +
                                "&SER_YMD=" + tempApi_SER_YMD +
                                "&SER_MON=" + tempApi_SER_MON +
                                "&EMP_COD=" + tempApi_EMP_COD +
                                "&EMP_NAM=" + tempApi_EMP_NAM +
                                "&CUS_COD=" + tempApi_CUS_COD +
                                "&CUS_NAM=" + tempApi_CUS_NAM +
                                "&PHN_NUM=" + tempApi_PHN_NUM +
                                "&CEL_NUM=" + tempApi_CEL_NUM +
                                "&EMA_COD=" + tempApi_CUS_COD +
                                "&TIP_CAS=" + tempApi_TIP_CAS +
                                "&TIP_CAR=" + tempApi_TIP_CAR +
                                "&COM_AMT=" + tempApi_COM_AMT +
                                "&EMP_AMT=" + tempApi_EMP_AMT +
                                "&TOT_AMT=" + tempApi_TOT_AMT +
                                "&POS_IDX=" + tempApi_POS_IDX +
                                "&STATION_COD=" + GlobalMemberValues.STATION_CODE +
                                "&TIPCAR_COM=" + tempApi_TIPCARD_COM +
                                "&CODEFORUPLOAD=" + tempApi_CODEFORUPLOAD;
                        apiParametersStr = GlobalMemberValues.getReplaceText(apiParametersStr, " ", "|||");
                        String returnValue = sendTipsDataByApi(apiParametersStr);
                        Vector<String > apiUpdateQueryVec = new Vector<String>();
                        if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                            //apiUpdateQueryVec.addElement("update salon_sales_tip set isCloudUpload = 1 where idx = '" + tempApi_POS_IDX + "' and cloudIdx = '" + returnValue + "' ");
                            apiUpdateQueryVec.addElement("update salon_sales_tip set isCloudUpload = 1, cloudIdx = '" + returnValue + "' where idx = '" + tempApi_POS_IDX + "' ");
                            GlobalMemberValues.logWrite("tipreturnvaluelog", "update salon_sales_tip set isCloudUpload = 1, " +
                                    " cloudIdx = '" + returnValue + "' where idx = '" + tempApi_POS_IDX + "' " + "\n");
                        }
                        String returnResult = "";
                        // 트랜잭션으로 DB 처리한다.
                        returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                        } else { // 정상처리일 경우 Sale History 리스트 리로드
                            // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                            // 서비스 중지 전 3초간 기다린다.
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_TIP != null && GlobalMemberValues.CURRENTSERVICEINTENT_TIP != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_TIP.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_TIP);
    }


    public String sendTipsDataByApi(String strParams) {
        GlobalMemberValues.logWrite("paramsInSendSalesDataByApi", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                API_salestipupload_tocloud apiTipsDataInstance = new API_salestipupload_tocloud(strParams);
                apiTipsDataInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiTipsDataInstance.mFlag) {
                        returnValue = apiTipsDataInstance.mReturnValue;
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
