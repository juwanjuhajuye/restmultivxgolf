package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

public class API_salesdataupload_tocloud extends AsyncTask {
    String mReturnValue = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();

    public API_salesdataupload_tocloud(String paramStr) {
        if (!GlobalMemberValues.isStrEmpty(paramStr)) {
            mStrUrl = GlobalMemberValues.API_WEB + "API_UP_Sales_item_ForAndroid.asp?" + paramStr;
            /**
            mStrUrl = "http://nzmsaloncloud.mvppos.com/API/API_UP_Sales_item_ForAndroid.asp?SCODE=10115&SIDX=82" +
                    "&IDG_COD=K12302015IVE5EFLEUW7126RSB85S&IDN_COD=K12302015IVE5EFLEUW7126RSB85S|43&SER_YMD=2015-12-30|||05:49:50" +
                    "&SER_MON=122015&EMP_COD=E0001&EMP_NAM=ADMIN&CUS_COD=C0001&CUS_NAM=Walk|||in&PHN_NUM=&CEL_NUM=&EMA_COD=" +
                    "&SER_IDN=S2123&SER_NAM=MANICURE&SAL_AMT=-10.00&BAL_AMT=-10.00&TAX_AMT=-1.00&QTY_CNT=-1&QTY_CNT2=-1&TEN_TY1=1&TEN_TY2=0" +
                    "&TEN_TY3=0&TEN_TY4=0&TEN_TY5=0&TEN_RT1=-10.0&TEN_RT2=-0.0&TEN_RT3=-0.0&TEN_RT4=-0.0&TEN_RT5=-0.0&PNT_AMT=-0.1&GIF_AMT=-0" +
                    "&COM_AMT=-0.2&TIP_AMT=-0.0&CUS_CNT=-0.5&CAT_COD=MANI|||/|||PEDI";
             **/


            GlobalMemberValues.logWrite("ApiMStrUrlUpdateLogJJJ", "url : " + mStrUrl + "\n");
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GlobalMemberValues.logWrite("checkLineLog", "여기까지..1" + "\n");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite("checkLineLog", "여기까지..2" + "\n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            GlobalMemberValues.logWrite("checkLineLog", "여기까지..3" + "\n");

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
            GlobalMemberValues.logWrite("checkLineLog", "여기까지..4" + "\n");
            GlobalMemberValues.logWrite("checkLineLog", "결과값 : " + mApiReturnCode + "\n");
            GlobalMemberValues.logWrite("checkLineLog", "리턴값 : " + mReturnValue + "\n");

        } catch (Exception e) {
            // 예외발생
        }
        return null;
    }
}
