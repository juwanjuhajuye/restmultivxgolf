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
public class APIDownLoad_salon_storestationinfo extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storestationinfo";

    String idx = "";
    String sidx = "";
    String stcode = "";
    String stSerialNumber = "";
    String stNumber = "";
    String stname = "";
    String mainYN = "";
    String useYN = "";
    String delyn = "";
    String pushreceiveyn = "";
    String wdate = "";
    String mdate = "";
    String cashinoutyn = "";
    String paymentyn = "";
    String paymenttype = "";
    String sttype = "";
    String eodyn = "";
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
                            GlobalMemberValues.logWrite("salon_storestationinfo", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storestationinfo", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storestationinfo", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storestationinfo", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("stcode")) {
                            stcode = xpp.getText();
                        }
                        if (tagName.equals("stSerialNumber")) {
                            stSerialNumber = xpp.getText();
                        }
                        if (tagName.equals("stNumber")) {
                            stNumber = xpp.getText();
                        }
                        if (tagName.equals("stname")) {
                            stname = xpp.getText();
                        }
                        if (tagName.equals("mainYN")) {
                            mainYN = xpp.getText();
                        }
                        if (tagName.equals("useYN")) {
                            useYN = xpp.getText();
                        }
                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }
                        if (tagName.equals("pushreceiveyn")) {
                            pushreceiveyn = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }
                        if (tagName.equals("mdate")) {
                            mdate = xpp.getText();
                        }
                        if (tagName.equals("cashinoutyn")) {
                            cashinoutyn = xpp.getText();
                        }
                        if (tagName.equals("paymentyn")) {
                            paymentyn = xpp.getText();
                        }
                        if (tagName.equals("paymenttype")) {
                            paymenttype = xpp.getText();
                        }
                        if (tagName.equals("sttype")) {
                            sttype = xpp.getText();
                        }
                        if (tagName.equals("eodyn")) {
                            eodyn = xpp.getText();
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
                                " idx, sidx, stcode, stSerialNumber, stNumber, stname, mainYN, useYN, delyn, pushreceiveyn, wdate, mdate, " +
                                " cashinoutyn, paymentyn, paymenttype, sttype, eodyn " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(stcode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(stSerialNumber, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(stNumber, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(stname, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mainYN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(useYN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pushreceiveyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cashinoutyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(paymentyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(paymenttype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sttype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(eodyn, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " stcode = '" + GlobalMemberValues.getDBTextAfterChecked(stcode, 0) + "', " +
                                " stSerialNumber = '" + GlobalMemberValues.getDBTextAfterChecked(stSerialNumber, 0) + "', " +
                                " stNumber = '" + GlobalMemberValues.getDBTextAfterChecked(stNumber, 0) + "', " +
                                " stname = '" + GlobalMemberValues.getDBTextAfterChecked(stname, 0) + "', " +
                                " mainYN = '" + GlobalMemberValues.getDBTextAfterChecked(mainYN, 0) + "', " +
                                " useYN = '" + GlobalMemberValues.getDBTextAfterChecked(useYN, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " pushreceiveyn = '" + GlobalMemberValues.getDBTextAfterChecked(pushreceiveyn, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " cashinoutyn = '" + GlobalMemberValues.getDBTextAfterChecked(cashinoutyn, 0) + "', " +
                                " paymentyn = '" + GlobalMemberValues.getDBTextAfterChecked(paymentyn, 0) + "', " +
                                " paymenttype = '" + GlobalMemberValues.getDBTextAfterChecked(paymenttype, 0) + "', " +
                                " sttype = '" + GlobalMemberValues.getDBTextAfterChecked(sttype, 0) + "', " +
                                " eodyn = '" + GlobalMemberValues.getDBTextAfterChecked(eodyn, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storestationinfo", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        sidx = "";
                        stcode = "";
                        stSerialNumber = "";
                        stNumber = "";
                        stname = "";
                        mainYN = "";
                        useYN = "";
                        delyn = "";
                        pushreceiveyn = "";
                        wdate = "";
                        mdate = "";
                        cashinoutyn = "";
                        paymentyn = "";
                        paymenttype = "";
                        sttype = "";
                        eodyn = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storestationinfo", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_storestationinfo", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_storestationinfo", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_storestationinfo", "----------------------------\n");
        }
        return null;
    }
}
