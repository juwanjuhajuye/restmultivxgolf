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
public class APIDownLoad_coupon_issue_history extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "coupon_issue_history";

    String idx = "";
    String cp_idx = "";
    String scode = "";
    String uid = "";
    String viewyn = "";
    String usedyn = "";
    String ent_nal = "";
    String barcode = "";
    String sendType = "";
    String mailPushIdx = "";
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
                            GlobalMemberValues.logWrite("coupon_issue_history", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("coupon_issue_history", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("coupon_issue_history", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("coupon_issue_history", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("cp_idx")) {
                            cp_idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("uid")) {
                            uid = xpp.getText();
                        }
                        if (tagName.equals("viewyn")) {
                            viewyn = xpp.getText();
                        }
                        if (tagName.equals("usedyn")) {
                            usedyn = xpp.getText();
                        }
                        if (tagName.equals("ent_nal")) {
                            ent_nal = xpp.getText();
                        }
                        if (tagName.equals("barcode")) {
                            barcode = xpp.getText();
                        }
                        if (tagName.equals("sendType")) {
                            sendType = xpp.getText();
                        }
                        if (tagName.equals("mailPushIdx")) {
                            mailPushIdx = xpp.getText();
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
                                " idx, cp_idx, scode, uid, viewyn, usedyn, ent_nal, barcode, " +
                                " sendType, mailPushIdx " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(cp_idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(viewyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(usedyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(ent_nal, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(barcode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sendType, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mailPushIdx, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " cp_idx = '" + GlobalMemberValues.getDBTextAfterChecked(cp_idx, 0) + "', " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " uid = '" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "', " +
                                " viewyn = '" + GlobalMemberValues.getDBTextAfterChecked(viewyn, 0) + "', " +
                                " usedyn = '" + GlobalMemberValues.getDBTextAfterChecked(usedyn, 0) + "', " +
                                " ent_nal = '" + GlobalMemberValues.getDBTextAfterChecked(ent_nal, 0) + "', " +
                                " barcode = '" + GlobalMemberValues.getDBTextAfterChecked(barcode, 0) + "', " +
                                " sendType = '" + GlobalMemberValues.getDBTextAfterChecked(sendType, 0) + "', " +
                                " mailPushIdx = '" + GlobalMemberValues.getDBTextAfterChecked(mailPushIdx, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("coupon_issue_history", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        cp_idx = "";
                        scode = "";
                        uid = "";
                        viewyn = "";
                        usedyn = "";
                        ent_nal = "";
                        barcode = "";
                        sendType = "";
                        mailPushIdx = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("coupon_issue_history", "예외발생 : " + e.getMessage());
        }
        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }
        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("coupon_issue_history", "----------------------------\n");
            GlobalMemberValues.logWrite("coupon_issue_history", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("coupon_issue_history", "----------------------------\n");
        }
        return null;
    }
}
