package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-02.
 */
public class APIDownLoad_salon_storeinfo_worktime_forPOS extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeinfo_worktime_forPOS";

    String idx = "";
    String sidx = "";
    String SUN_OPN = "";
    String SUN_CLS = "";
    String MON_OPN = "";
    String MON_CLS = "";
    String TUE_OPN = "";
    String TUE_CLS = "";
    String WED_OPN = "";
    String WED_CLS = "";
    String THU_OPN = "";
    String THU_CLS = "";
    String FRI_OPN = "";
    String FRI_CLS = "";
    String SAT_OPN = "";
    String SAT_CLS = "";
    String wdate = "";
    /**********************************************************/

    String mInsertSqlQuery = "";
    String mUpdateSqlQuery = "";
    String mQueryCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEB_URL + "?" +
            "RequestSqlType=" + mDbTableName + "&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=" + GlobalMemberValues.API_WEB_DB_XML_TYPE;

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();
    public Vector<String> sqlQueryVecIns = new Vector<String>();

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

            sqlQueryVecIns.add("delete from " + mDbTableName);

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
                            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("SUN_OPN")) {
                            SUN_OPN = xpp.getText();
                        }
                        if (tagName.equals("SUN_CLS")) {
                            SUN_CLS = xpp.getText();
                        }
                        if (tagName.equals("MON_OPN")) {
                            MON_OPN = xpp.getText();
                        }
                        if (tagName.equals("MON_CLS")) {
                            MON_CLS = xpp.getText();
                        }
                        if (tagName.equals("TUE_OPN")) {
                            TUE_OPN = xpp.getText();
                        }
                        if (tagName.equals("TUE_CLS")) {
                            TUE_CLS = xpp.getText();
                        }
                        if (tagName.equals("WED_OPN")) {
                            WED_OPN = xpp.getText();
                        }
                        if (tagName.equals("WED_CLS")) {
                            WED_CLS = xpp.getText();
                        }
                        if (tagName.equals("THU_OPN")) {
                            THU_OPN = xpp.getText();
                        }
                        if (tagName.equals("THU_CLS")) {
                            THU_CLS = xpp.getText();
                        }
                        if (tagName.equals("FRI_OPN")) {
                            FRI_OPN = xpp.getText();
                        }
                        if (tagName.equals("FRI_CLS")) {
                            FRI_CLS = xpp.getText();
                        }
                        if (tagName.equals("SAT_OPN")) {
                            SAT_OPN = xpp.getText();
                        }
                        if (tagName.equals("SAT_CLS")) {
                            SAT_CLS = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }
                    }



                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        // 등록쿼리 조합하기
                        mInsertSqlQuery = "insert into " + mDbTableName +
                                " ( " +
                                " idx, sidx, SUN_OPN, SUN_CLS, MON_OPN, MON_CLS, TUE_OPN, TUE_CLS, WED_OPN, WED_CLS, THU_OPN, THU_CLS, FRI_OPN, FRI_CLS, SAT_OPN, SAT_CLS, wdate " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(SUN_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(SUN_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(MON_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(MON_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(TUE_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(TUE_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(WED_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(WED_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(THU_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(THU_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(FRI_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(FRI_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(SAT_OPN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(SAT_CLS, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " SUN_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(SUN_OPN, 0) + "', " +
                                " SUN_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(SUN_CLS, 0) + "', " +
                                " MON_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(MON_OPN, 0) + "', " +
                                " MON_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(MON_CLS, 0) + "', " +
                                " TUE_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(TUE_OPN, 0) + "', " +
                                " TUE_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(TUE_CLS, 0) + "', " +
                                " WED_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(WED_OPN, 0) + "', " +
                                " WED_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(WED_CLS, 0) + "', " +
                                " THU_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(THU_OPN, 0) + "', " +
                                " THU_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(THU_CLS, 0) + "', " +
                                " FRI_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(FRI_OPN, 0) + "', " +
                                " FRI_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(FRI_CLS, 0) + "', " +
                                " SAT_OPN = '" + GlobalMemberValues.getDBTextAfterChecked(SAT_OPN, 0) + "', " +
                                " SAT_CLS = '" + GlobalMemberValues.getDBTextAfterChecked(SAT_CLS, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        sidx = "";
                        SUN_OPN = "";
                        SUN_CLS = "";
                        MON_OPN = "";
                        MON_CLS = "";
                        TUE_OPN = "";
                        TUE_CLS = "";
                        WED_OPN = "";
                        WED_CLS = "";
                        THU_OPN = "";
                        THU_CLS = "";
                        FRI_OPN = "";
                        FRI_CLS = "";
                        SAT_OPN = "";
                        SAT_CLS = "";
                        wdate = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_storeinfo_worktime_forPOS", "----------------------------\n");
        }
        return null;
    }
}
