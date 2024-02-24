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
public class APIDownLoad_salon_store_restaurant_table extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_store_restaurant_table";

    String idx = "";
    String scode = "";
    String sidx = "";
    String zoneidx = "";
    String tablename = "";
    String capacity = "";    
    String colorvalue = "";
    String tabletype = "";
    String chargeridx = "";
    String pagernum = "";
    String xvalue = "";
    String yvalue = "";
    String xvaluerate = "";
    String yvaluerate = "";
    String useyn = "";
    String deleteyn = "";
    String wdate = "";
    String mdate = "";
    String boxtype = "";
    String boxkinds = "";
    String quicksaleyn = "";

    String size = "";
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

            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "mStrUrl : " + mStrUrl + " \n");

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
                            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("zoneidx")) {
                            zoneidx = xpp.getText();
                        }
                        if (tagName.equals("tablename")) {
                            tablename = xpp.getText();
                        }
                        if (tagName.equals("capacity")) {
                            capacity = xpp.getText();
                        }
                        if (tagName.equals("colorvalue")) {
                            colorvalue = xpp.getText();
                        }
                        if (tagName.equals("tabletype")) {
                            tabletype = xpp.getText();
                        }
                        if (tagName.equals("chargeridx")) {
                            chargeridx = xpp.getText();
                        }
                        if (tagName.equals("pagernum")) {
                            pagernum = xpp.getText();
                        }
                        if (tagName.equals("xvalue")) {
                            xvalue = xpp.getText();
                        }
                        if (tagName.equals("yvalue")) {
                            yvalue = xpp.getText();
                        }
                        if (tagName.equals("xvaluerate")) {
                            xvaluerate = xpp.getText();
                        }
                        if (tagName.equals("yvaluerate")) {
                            yvaluerate = xpp.getText();
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
                        if (tagName.equals("boxtype")) {
                            boxtype = xpp.getText();
                        }
                        if (tagName.equals("boxkinds")) {
                            boxkinds = xpp.getText();
                        }
                        if (tagName.equals("quicksaleyn")) {
                            quicksaleyn = xpp.getText();
                        }
                        if (tagName.equals("size")) {
                            size = xpp.getText();
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
                                " idx, scode, sidx, zoneidx, tablename, capacity, colorvalue, tabletype, " +
                                " chargeridx, pagernum, xvalue, yvalue, xvaluerate, yvaluerate, useyn, deleteyn, " +
                                " wdate, mdate, boxtype, boxkinds, quicksaleyn, size " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(zoneidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tablename, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(capacity, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(colorvalue, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tabletype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(chargeridx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pagernum, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(xvalue, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(yvalue, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(xvaluerate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(yvaluerate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(useyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(deleteyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(boxtype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(boxkinds, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(quicksaleyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(size, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " zoneidx = '" + GlobalMemberValues.getDBTextAfterChecked(zoneidx, 0) + "', " +
                                " tablename = '" + GlobalMemberValues.getDBTextAfterChecked(tablename, 0) + "', " +
                                " capacity = '" + GlobalMemberValues.getDBTextAfterChecked(capacity, 0) + "', " +
                                " colorvalue = '" + GlobalMemberValues.getDBTextAfterChecked(colorvalue, 0) + "', " +
                                " tabletype = '" + GlobalMemberValues.getDBTextAfterChecked(tabletype, 0) + "', " +
                                " chargeridx = '" + GlobalMemberValues.getDBTextAfterChecked(chargeridx, 0) + "', " +
                                " pagernum = '" + GlobalMemberValues.getDBTextAfterChecked(pagernum, 0) + "', " +
                                " xvalue = '" + GlobalMemberValues.getDBTextAfterChecked(xvalue, 0) + "', " +
                                " yvalue = '" + GlobalMemberValues.getDBTextAfterChecked(yvalue, 0) + "', " +
                                " xvaluerate = '" + GlobalMemberValues.getDBTextAfterChecked(xvaluerate, 0) + "', " +
                                " yvaluerate = '" + GlobalMemberValues.getDBTextAfterChecked(yvaluerate, 0) + "', " +
                                " useyn = '" + GlobalMemberValues.getDBTextAfterChecked(useyn, 0) + "', " +
                                " deleteyn = '" + GlobalMemberValues.getDBTextAfterChecked(deleteyn, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " boxtype = '" + GlobalMemberValues.getDBTextAfterChecked(boxtype, 0) + "', " +
                                " boxkinds = '" + GlobalMemberValues.getDBTextAfterChecked(boxkinds, 0) + "', " +
                                " quicksaleyn = '" + GlobalMemberValues.getDBTextAfterChecked(quicksaleyn, 0) + "', " +
                                " size = '" + GlobalMemberValues.getDBTextAfterChecked(size, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        scode = "";
                        sidx = "";
                        zoneidx = "";
                        tablename = "";
                        capacity = "";
                        colorvalue = "";
                        tabletype = "";
                        chargeridx = "";
                        pagernum = "";
                        xvalue = "";
                        yvalue = "";
                        xvaluerate = "";
                        yvaluerate = "";
                        useyn = "";
                        deleteyn = "";
                        wdate = "";
                        mdate = "";
                        boxtype = "";
                        boxkinds = "";
                        quicksaleyn = "";
                        size = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }


        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_store_restaurant_tablelog", "----------------------------\n");
        }
        return null;
    }
}
