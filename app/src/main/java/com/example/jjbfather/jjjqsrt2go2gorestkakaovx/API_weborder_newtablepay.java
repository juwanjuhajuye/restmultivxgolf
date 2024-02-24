package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

public class API_weborder_newtablepay extends AsyncTask {
    final String TAG = "NEWTALBPAYAPI";

    String mReturnValue = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();

    public API_weborder_newtablepay(String paramStr) {
        if (!GlobalMemberValues.isStrEmpty(paramStr)) {
            mStrUrl = GlobalMemberValues.API_WEB + "API_Check_NewTablePay_ForAndroid.asp?sidx=" + paramStr + "&stcode=" + GlobalMemberValues.STATION_CODE;

            GlobalMemberValues.logWrite(TAG, "url : " + mStrUrl + "\n");
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GlobalMemberValues.logWrite(TAG, "여기까지..1" + "\n");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite(TAG, "여기까지..2" + "\n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            GlobalMemberValues.logWrite(TAG, "여기까지..3" + "\n");

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
                        } else {
                            isPossible = false;
                        }
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storePG", "성공일때만.." + "\n");
                        if (tagName.equals("ReturnValue")) {
                            mReturnValue = xpp.getText();
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태
            GlobalMemberValues.logWrite(TAG, "여기까지..4" + "\n");
            GlobalMemberValues.logWrite(TAG, "결과값 : " + mApiReturnCode + "\n");
            GlobalMemberValues.logWrite(TAG, "리턴값 : " + mReturnValue + "\n");

        } catch (Exception e) {
            // 예외발생
        }
        return null;
    }
}
