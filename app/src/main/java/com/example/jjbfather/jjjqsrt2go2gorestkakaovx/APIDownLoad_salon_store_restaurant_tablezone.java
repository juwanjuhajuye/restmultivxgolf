package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-04.
 */
public class APIDownLoad_salon_store_restaurant_tablezone extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbtablecounts = "salon_store_restaurant_tablezone";

    String idx = "";
    String scode = "";
    String sidx = "";
    String zonename = "";
    String tablecounts = "";
    String zonetype = "";    
    String useyn = "";
    String deleteyn = "";
    String wdate = "";
    String mdate = "";
    /**********************************************************/

    String mInsertSqlQuery = "";
    String mUpdateSqlQuery = "";
    String mQueryCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEB_URL + "?" +
            "RequestSqlType=" + mDbtablecounts + "&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=" + GlobalMemberValues.API_WEB_DB_XML_TYPE;

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

            GlobalMemberValues.logWrite("salon_store_restaurant_table", "mStrUrl : " + mStrUrl + " \n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            sqlQueryVecIns.add("delete from " + mDbtablecounts);

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
                            GlobalMemberValues.logWrite("salon_store_restaurant_table", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_store_restaurant_table", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_store_restaurant_table", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_store_restaurant_table", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("zonename")) {
                            zonename = xpp.getText();
                        }
                        if (tagName.equals("tablecounts")) {
                            tablecounts = xpp.getText();
                        }
                        if (tagName.equals("zonetype")) {
                            zonetype = xpp.getText();
                        }
                        if (tagName.equals("useyn")) {
                            useyn = xpp.getText();
                        }
                        if (tagName.equals("deleteyn")) {
                            deleteyn = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }
                        if (tagName.equals("mdate")) {
                            mdate = xpp.getText();
                        }
                    }



                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        // 등록쿼리 조합하기
                        mInsertSqlQuery = "insert into " + mDbtablecounts +
                                " ( " +
                                " idx, scode, sidx, zonename, tablecounts, zonetype, useyn, deleteyn, wdate, mdate " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(zonename, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tablecounts, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(zonetype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(useyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deleteyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbtablecounts + " set " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " zonename = '" + GlobalMemberValues.getDBTextAfterChecked(zonename, 0) + "', " +
                                " tablecounts = '" + GlobalMemberValues.getDBTextAfterChecked(tablecounts, 0) + "', " +
                                " zonetype = '" + GlobalMemberValues.getDBTextAfterChecked(zonetype, 0) + "', " +
                                " useyn = '" + GlobalMemberValues.getDBTextAfterChecked(useyn, 0) + "', " +
                                " deleteyn = '" + GlobalMemberValues.getDBTextAfterChecked(deleteyn, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_store_restaurant_table", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        scode = "";
                        sidx = "";
                        zonename = "";
                        tablecounts = "";
                        zonetype = "";
                        useyn = "";
                        deleteyn = "";
                        wdate = "";
                        mdate = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_store_restaurant_table", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbtablecounts, "DB 처리결과 : " + returnResult + "\n");
        }


        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_store_restaurant_table", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_store_restaurant_table", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_store_restaurant_table", "----------------------------\n");
        }
        return null;
    }
}
