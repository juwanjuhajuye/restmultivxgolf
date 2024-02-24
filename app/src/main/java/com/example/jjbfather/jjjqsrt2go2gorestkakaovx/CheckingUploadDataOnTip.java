package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */

import android.database.Cursor;

import java.util.Vector;


public class CheckingUploadDataOnTip {
    final String TAG = "CheckingUploadDataOnTipLog";

    public CheckingUploadDataOnTip() {
        //
    }

    public boolean checkDataInCloud(boolean paramIsService) {
        // 테이블
        String tblName = "salon_sales_tip";
        // 결과값
        boolean mReturnValue = false;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            GlobalMemberValues.logWrite(TAG, "여기실행됨 - 5." + "\n");

            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
            String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;

            String strQuery = "select idx, salesCode from " + tblName +
                    " where isCloudUpload = 0 order by idx asc";
            GlobalMemberValues.logWrite(TAG, "query : " + strQuery + "\n");
            Cursor resultCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (resultCursor.moveToNext()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(resultCursor.getString(0), 1);
                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(resultCursor.getString(1), 1);
                if (!GlobalMemberValues.isStrEmpty(tempSalesCode)) {

                    String apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + tempApi_SIDX +
                            "&stcode=" + GlobalMemberValues.STATION_CODE +
                            "&checkingtype=tip" +
                            "&idx=" + tempIdx +
                            "&salescode=" + tempSalesCode;

                    apiParametersStr = GlobalMemberValues.getReplaceText(apiParametersStr, " ", "|||");
                    String returnValue = "";
                    returnValue = sendCheckingApi(apiParametersStr);

                    GlobalMemberValues.logWrite(TAG, "returnValue : " + returnValue + "\n");

                    Vector<String > apiUpdateQueryVec = new Vector<String>();
                    if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                        apiUpdateQueryVec.addElement("update " + tblName + " set isCloudUpload = 1 where idx = '" + tempIdx + "' ");
                    }
                    String returnResult = "";
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.logWrite(TAG, "isCloudUpload 값 변경 실패" + "\n");
                    } else { // 정상처리일 경우 Sale History 리스트 리로드
                        if (paramIsService) {
                            // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                            // 서비스 중지 전 3초간 기다린다.
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            GlobalMemberValues.logWrite(TAG, "returnResult : " + returnResult + "\n");

                            // 서비스 중지
                            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_2 != null && GlobalMemberValues.CURRENTSERVICEINTENT_2 != null)
                                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_2.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_2);
                        } else {
                            mReturnValue = true;
                        }
                    }

                }
            }
        }

        return mReturnValue;
    }


    public String sendCheckingApi(String strParams) {
        GlobalMemberValues.logWrite(TAG, "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                // API 를 통해 클라우드에 해당고객의 포인트를 업로드
                API_checkinguploaddata_incloud apiTipsDataInstance = new API_checkinguploaddata_incloud(strParams);
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
}