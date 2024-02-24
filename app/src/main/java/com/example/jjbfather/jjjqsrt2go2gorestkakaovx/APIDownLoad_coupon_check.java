package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-01-25.
 */
public class APIDownLoad_coupon_check extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String offType = "";
    String discountValue = "";
    String startDate = "";
    String endDate = "";
    String usedyn = "";
    String barcode = "";
    String userid = "";
    String username = "";
    String coupon_name = "";
    String applytype = "";
    String coupontype = "";
    /**********************************************************/

    String strReturn = "";

    String mStrUrl;

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;

    public APIDownLoad_coupon_check(String paramBarcode) {
        strReturn = "";

        mStrUrl = GlobalMemberValues.API_WEB +  "API_UP_MemCoupons_Item_RealTimeCheckUp.asp?" +
                "apiPageCmd=CheckUp_MemCoupons&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=3&barcode=" + paramBarcode;
        GlobalMemberValues.logWrite("coupon_check", "mStrUrl : " + mStrUrl + "\n");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = true;
                    }
                    if (tagName.equals("Data")) {
                        isReturnCode = false;
                        isDataTag = true;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isReturnCode && tagName.equals("ReturnCode")) {
                        mApiReturnCode = xpp.getText();
                        if (mApiReturnCode.equals("00")) {
                            isPossible = true;
                            GlobalMemberValues.logWrite("coupon_check", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("coupon_check", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("coupon_check", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("coupon_check", "성공일때만.." + "\n");
                        if (tagName.equals("Off_Type")) {
                            offType = xpp.getText();
                        }
                        if (tagName.equals("Discount")) {
                            discountValue = xpp.getText();
                        }
                        if (tagName.equals("StartDate")) {
                            startDate = xpp.getText();
                        }
                        if (tagName.equals("EndDate")) {
                            endDate = xpp.getText();
                        }
                        if (tagName.equals("UsedYN")) {
                            usedyn = xpp.getText();
                        }
                        if (tagName.equals("barcode")) {
                            barcode = xpp.getText();
                        }
                        if (tagName.equals("userid")) {
                            userid = xpp.getText();
                        }
                        if (tagName.equals("username")) {
                            username = xpp.getText();
                        }
                        if (tagName.equals("coupon_name")) {
                            coupon_name = xpp.getText();
                        }
                        if (tagName.equals("applytype")) {
                            applytype = xpp.getText();
                        }
                        if (tagName.equals("coupontype")) {
                            coupontype = xpp.getText();
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        if (GlobalMemberValues.isStrEmpty(applytype)) {
                            applytype = "B";
                        }
                        if (GlobalMemberValues.isStrEmpty(coupontype)) {
                            coupontype = "S";
                        }

                        // 리턴값 조합
                        strReturn = offType + "||" + discountValue + "||" + startDate + "||" + endDate + "||" + usedyn + "||" +
                                userid + "||" + username + "||" + coupon_name + "||" + barcode + "||" + applytype + "||" + coupontype;
                                GlobalMemberValues.logWrite("coupon_check", "strReturn : " + strReturn + "\n");

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("coupon_check", "예외발생 : " + e.getMessage());
        }

        GlobalMemberValues.logWrite("coupon_check", "strReturn : " + strReturn + "\n");

        return null;
    }
}
