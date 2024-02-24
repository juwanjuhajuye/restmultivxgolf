package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */

import android.database.Cursor;

// 구글 플레이 서비스 구글플레이서비스 (Google Play Service)
//import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Vector;


public class UploadMemberMileageHistoryToCloud {

    public UploadMemberMileageHistoryToCloud() {
        //
    }

    public boolean sendMemberMileageHistoryDataToCloud(boolean paramIsService) {
        // 결과값
        boolean mReturnValue = false;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            GlobalMemberValues.logWrite("membermileageuploadlog", "여기실행됨 - 5." + "\n");

            // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
            String schDateStr = GlobalMemberValues.getSearchDateString(MainActivity.mDbInit, "mdate");
            // -------------------------------------------------------------------------------------------------------------------------------------------------

            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
            String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;

            String strMemberMileageHistoryQuery = "select mdate, contents, mileage, uid, mcase, seno, membershipcardno, codeforupload " +
                    " from member_mileage " +
                    " where isCloudUpload = 0 " +
                    " and " + schDateStr +
                    " and not(uid = '' or uid is null) order by seno asc";
            GlobalMemberValues.logWrite("membermileageuploadlog", "query : " + strMemberMileageHistoryQuery + "\n");
            Cursor membermileagehistoryCursor = MainActivity.mDbInit.dbExecuteRead(strMemberMileageHistoryQuery);
            while (membermileagehistoryCursor.moveToNext()) {
                String tempMdate = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(0), 1);
                String tempContents = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(1), 1);
                String tempMileage = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(2), 1);
                String tempUid = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(3), 1);
                String tempMcase = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(4), 1);
                String tempSeno = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(5), 1);
                String tempMembershipcardno = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(6), 1);
                String tempCodeForUpload = GlobalMemberValues.getDBTextAfterChecked(membermileagehistoryCursor.getString(7), 1);

                if (GlobalMemberValues.getDoubleAtString(tempMileage) > 0 && !GlobalMemberValues.isStrEmpty(tempUid)) {
                    if (GlobalMemberValues.isStrEmpty(tempMcase)) {
                        tempMcase = "1";
                    }

                    String apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + tempApi_SIDX +
                            "&STCODE=" + GlobalMemberValues.STATION_CODE +
                            "&mdate=" + tempMdate +
                            "&uid=" + tempUid +
                            "&contents=" + tempContents +
                            "&mileage=" + tempMileage +
                            "&mcase=" + tempMcase +
                            "&membershipcardno=" + tempMembershipcardno +
                            "&seno=" + tempSeno +
                            "&codefrompos=" + tempCodeForUpload;

                    GlobalMemberValues.logWrite("membermileageuploadlog", "apiParametersStr : " + apiParametersStr + "\n");

                    apiParametersStr = GlobalMemberValues.getReplaceText(apiParametersStr, " ", "|||");
                    String returnValue = sendMemberMileageHistoryDataByApi(apiParametersStr);
                    Vector<String > apiUpdateQueryVec = new Vector<String>();
                    if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                        apiUpdateQueryVec.addElement("update member_mileage set isCloudUpload = 1 " +
                                " where seno = '" + tempSeno + "' ");
                    }
                    String returnResult = "";
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                    } else { // 정상처리일 경우 Sale History 리스트 리로드
                        if (paramIsService) {
                            // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                            // 서비스 중지 전 3초간 기다린다.
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mReturnValue = true;
                        }
                    }

                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY != null && GlobalMemberValues.CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY);

        return mReturnValue;
    }


    public String sendMemberMileageHistoryDataByApi(String strParams) {
        GlobalMemberValues.logWrite("membermileageuploadlog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                // API 를 통해 클라우드에 해당고객의 포인트를 업로드
                API_membermileagehistoryupload_tocloud apiTipsDataInstance = new API_membermileagehistoryupload_tocloud(strParams);
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