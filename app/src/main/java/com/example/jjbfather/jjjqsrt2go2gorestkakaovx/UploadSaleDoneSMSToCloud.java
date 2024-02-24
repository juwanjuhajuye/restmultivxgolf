package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */


public class UploadSaleDoneSMSToCloud {

    public UploadSaleDoneSMSToCloud() {
        //
    }

    public boolean sendGiftCardHistoryDataToCloud(boolean paramIsService) {
        // 결과값
        boolean mReturnValue = false;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (GlobalMemberValues.isOnline2().equals("00")) {
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mSalesCode_SALEDONESMS)) {
                    String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
                    String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;

                    String apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + tempApi_SIDX +
                            "&stcode=" + GlobalMemberValues.STATION_CODE +
                            "&salesCode=" + GlobalMemberValues.mSalesCode_SALEDONESMS;

                    apiParametersStr = GlobalMemberValues.getReplaceText(apiParametersStr, " ", "|||");
                    String returnValue = "";
                    returnValue = sendSaleDoneSMSByApi(apiParametersStr);

                    if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                        // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                        // 서비스 중지 전 3초간 기다린다.
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // sales code 값 초기화
                        GlobalMemberValues.mSalesCode_SALEDONESMS = "";
                    }
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEDONESMS != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALEDONESMS != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEDONESMS.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALEDONESMS);

        return mReturnValue;
    }


    public String sendSaleDoneSMSByApi(String strParams) {
        GlobalMemberValues.logWrite("saledonesmslog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                // API 를 통해 클라우드에 해당고객의 포인트를 업로드
                API_saledonesms_tocloud apiTipsDataInstance = new API_saledonesms_tocloud(strParams);
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