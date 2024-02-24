package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */

import android.database.Cursor;

import java.util.Vector;


public class UploadGiftCardHistoryToCloud {

    public UploadGiftCardHistoryToCloud() {
        //
    }

    public boolean sendGiftCardHistoryDataToCloud(boolean paramIsService) {
        // 결과값
        boolean mReturnValue = false;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            GlobalMemberValues.logWrite("giftcardhistoryloadlog", "여기실행됨 - 5." + "\n");

            // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
            String schDateStr = GlobalMemberValues.getSearchDateString(MainActivity.mDbInit, "wdate");
            // -------------------------------------------------------------------------------------------------------------------------------------------------

            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
            String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;

            String strMemberMileageHistoryQuery = "select idx, sidx, gcNumber, saleItemIdx, empIdx, empName, customerId, customerName, " +
                    " addUsePrice, addUseType, addUseMemo, salesCode, wdate, codeforupload, giftcardname, serverIdx, serverName " +
                    " from salon_storegiftcard_number_history " +
                    " where isCloudUpload = 0 " +
                    " and " + schDateStr +
                    " and not(gcNumber = '' or gcNumber is null) order by idx asc";
            GlobalMemberValues.logWrite("giftcardhistoryloadlog", "query : " + strMemberMileageHistoryQuery + "\n");
            Cursor membermileagehistoryCursor = MainActivity.mDbInit.dbExecuteRead(strMemberMileageHistoryQuery);
            while (membermileagehistoryCursor.moveToNext()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(0), 1);
                String tempSidx = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(1), 1);
                String tempGcNumber = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(2), 1);
                String tempSaleItemIdx = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(3), 1);
                String tempEmpIdx = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(4), 1);
                String tempEmpName = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(5), 1);
                String tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(6), 1);
                String tempCustomerName = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(7), 1);
                String tempAddUsePrice = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(8), 1);
                String tempAddUseType = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(9), 1);
                String tempAddUseMemo = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(10), 1);
                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(11), 1);
                String tempWdate = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(12), 1);
                String tempCodeForUpload = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(13), 1);
                String tempGiftCardName = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(14), 1);
                String tempServerIdx = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(15), 1);
                String tempServerName = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(16), 1);

                if (GlobalMemberValues.getDoubleAtString(tempAddUsePrice) > 0) {
                    if (GlobalMemberValues.isStrEmpty(tempAddUseType)) {
                        tempAddUseType = "A";
                    }

                    String apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + tempApi_SIDX +
                            "&stcode=" + GlobalMemberValues.STATION_CODE +
                            "&idx=" + tempIdx +
                            "&gcNumber=" + tempGcNumber +
                            "&saleItemIdx=" + tempSaleItemIdx +
                            "&empIdx=" + tempEmpIdx +
                            "&empName=" + GlobalMemberValues.getEncoderUtf8(tempEmpName) +
                            "&customerId=" + tempCustomerId +
                            "&customerName=" + tempCustomerName +
                            "&addUsePrice=" + tempAddUsePrice +
                            "&addUseType=" + tempAddUseType +
                            "&addUseMemo=" + tempAddUseMemo +
                            "&salesCode=" + tempSalesCode +
                            "&wdate=" + tempWdate +
                            "&codefrompos=" + tempCodeForUpload +
                            "&giftcardname=" + tempGiftCardName +
                            "&serverIdx=" + tempServerIdx +
                            "&serverName=" + GlobalMemberValues.getEncoderUtf8(tempServerName);

                    apiParametersStr = GlobalMemberValues.getReplaceText(apiParametersStr, " ", "|||");
                    String returnValue = "";
                    returnValue = sendGiftCardHistoryDataByApi(apiParametersStr);

                    GlobalMemberValues.logWrite("giftcardhistoryloadlog", "returnValue : " + returnValue + "\n");

                    Vector<String > apiUpdateQueryVec = new Vector<String>();
                    if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                        apiUpdateQueryVec.addElement("update salon_storegiftcard_number_history set isCloudUpload = 1 " +
                                " where idx = '" + tempIdx + "' ");
                    }
                    String returnResult = "";
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.logWrite("giftcardhistoryloadlog", "isCloudUpload 값 변경 실패" + "\n");
                    } else { // 정상처리일 경우 Sale History 리스트 리로드
                        if (paramIsService) {
                            // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                            // 서비스 중지 전 3초간 기다린다.
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            GlobalMemberValues.logWrite("giftcardhistoryloadlog", "returnResult : " + returnResult + "\n");

                        } else {
                            mReturnValue = true;
                        }
                    }

                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY != null && GlobalMemberValues.CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY);

        return mReturnValue;
    }


    public String sendGiftCardHistoryDataByApi(String strParams) {
        GlobalMemberValues.logWrite("giftcardhistoryloadlog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                // API 를 통해 클라우드에 해당고객의 포인트를 업로드
                API_giftcardhistoryupload_tocloud apiTipsDataInstance = new API_giftcardhistoryupload_tocloud(strParams);
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