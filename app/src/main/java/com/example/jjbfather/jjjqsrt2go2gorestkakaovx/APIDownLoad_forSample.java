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
public class APIDownLoad_forSample extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "_databaseTablename_";

    String DbTableColumnNanme01 = "";
    String DbTableColumnNanme02 = "";
    String DbTableColumnNanme03 = "";
    String DbTableColumnNanme04 = "";
    String DbTableColumnNanme05 = "";
    String DbTableColumnNanme06 = "";
    String DbTableColumnNanme07 = "";
    String DbTableColumnNanme08 = "";
    String DbTableColumnNanme09 = "";
    String DbTableColumnNanme10 = "";
    String DbTableColumnNanme11 = "";
    String DbTableColumnNanme12 = "";
    String DbTableColumnNanme13 = "";
    String DbTableColumnNanme14 = "";
    String DbTableColumnNanme15 = "";
    String DbTableColumnNanme16 = "";
    String DbTableColumnNanme17 = "";
    String DbTableColumnNanme18 = "";
    String DbTableColumnNanme19 = "";
    String DbTableColumnNanme20 = "";
    String DbTableColumnNanme21 = "";
    String DbTableColumnNanme22 = "";
    String DbTableColumnNanme23 = "";
    String DbTableColumnNanme24 = "";
    String DbTableColumnNanme25 = "";
    String DbTableColumnNanme26 = "";
    String DbTableColumnNanme27 = "";
    String DbTableColumnNanme28 = "";
    String DbTableColumnNanme29 = "";
    String DbTableColumnNanme30 = "";
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
                            GlobalMemberValues.logWrite("_databaseTablename_", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("_databaseTablename_", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("_databaseTablename_", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("_databaseTablename_", "성공일때만.." + "\n");
                        if (tagName.equals("DbTableColumnNanme01")) {
                            DbTableColumnNanme01 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme02")) {
                            DbTableColumnNanme02 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme03")) {
                            DbTableColumnNanme03 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme04")) {
                            DbTableColumnNanme04 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme05")) {
                            DbTableColumnNanme05 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme06")) {
                            DbTableColumnNanme06 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme07")) {
                            DbTableColumnNanme07 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme08")) {
                            DbTableColumnNanme08 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme09")) {
                            DbTableColumnNanme09 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme10")) {
                            DbTableColumnNanme10 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme11")) {
                            DbTableColumnNanme11 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme12")) {
                            DbTableColumnNanme12 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme13")) {
                            DbTableColumnNanme13 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme14")) {
                            DbTableColumnNanme14 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme15")) {
                            DbTableColumnNanme15 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme16")) {
                            DbTableColumnNanme16 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme17")) {
                            DbTableColumnNanme17 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme18")) {
                            DbTableColumnNanme18 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme19")) {
                            DbTableColumnNanme19 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme20")) {
                            DbTableColumnNanme20 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme21")) {
                            DbTableColumnNanme21 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme22")) {
                            DbTableColumnNanme22 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme23")) {
                            DbTableColumnNanme23 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme24")) {
                            DbTableColumnNanme24 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme25")) {
                            DbTableColumnNanme25 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme26")) {
                            DbTableColumnNanme26 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme27")) {
                            DbTableColumnNanme27 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme28")) {
                            DbTableColumnNanme28 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme29")) {
                            DbTableColumnNanme29 = xpp.getText();
                        }
                        if (tagName.equals("DbTableColumnNanme30")) {
                            DbTableColumnNanme30 = xpp.getText();
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
                                " DbTableColumnNanme01, DbTableColumnNanme02, DbTableColumnNanme03, DbTableColumnNanme04, DbTableColumnNanme05, DbTableColumnNanme06, DbTableColumnNanme07, DbTableColumnNanme08, " +
                                " DbTableColumnNanme09, DbTableColumnNanme10, DbTableColumnNanme11, DbTableColumnNanme12, DbTableColumnNanme13, DbTableColumnNanme14, DbTableColumnNanme15, DbTableColumnNanme16, " +
                                " DbTableColumnNanme17, DbTableColumnNanme18, DbTableColumnNanme19, DbTableColumnNanme20, DbTableColumnNanme21, DbTableColumnNanme22, DbTableColumnNanme23, DbTableColumnNanme24, " +
                                " DbTableColumnNanme25, DbTableColumnNanme26, DbTableColumnNanme27, DbTableColumnNanme28, DbTableColumnNanme29, DbTableColumnNanme30 " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme01, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme02, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme03, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme04, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme05, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme06, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme07, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme08, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme09, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme10, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme11, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme12, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme13, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme14, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme15, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme16, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme17, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme18, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme19, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme20, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme21, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme22, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme23, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme24, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme25, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme26, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme27, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme28, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme29, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme30, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " DbTableColumnNanme02 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme02, 0) + "', " +
                                " DbTableColumnNanme03 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme03, 0) + "', " +
                                " DbTableColumnNanme04 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme04, 0) + "', " +
                                " DbTableColumnNanme05 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme05, 0) + "', " +
                                " DbTableColumnNanme06 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme06, 0) + "', " +
                                " DbTableColumnNanme07 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme07, 0) + "', " +
                                " DbTableColumnNanme08 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme08, 0) + "', " +
                                " DbTableColumnNanme09 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme09, 0) + "', " +
                                " DbTableColumnNanme10 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme10, 0) + "', " +
                                " DbTableColumnNanme11 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme11, 0) + "', " +
                                " DbTableColumnNanme12 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme12, 0) + "', " +
                                " DbTableColumnNanme13 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme13, 0) + "', " +
                                " DbTableColumnNanme14 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme14, 0) + "', " +
                                " DbTableColumnNanme15 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme15, 0) + "', " +
                                " DbTableColumnNanme16 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme16, 0) + "', " +
                                " DbTableColumnNanme17 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme17, 0) + "', " +
                                " DbTableColumnNanme18 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme18, 0) + "', " +
                                " DbTableColumnNanme19 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme19, 0) + "', " +
                                " DbTableColumnNanme20 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme20, 0) + "', " +
                                " DbTableColumnNanme21 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme21, 0) + "', " +
                                " DbTableColumnNanme22 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme22, 0) + "', " +
                                " DbTableColumnNanme23 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme23, 0) + "', " +
                                " DbTableColumnNanme24 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme24, 0) + "', " +
                                " DbTableColumnNanme25 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme25, 0) + "', " +
                                " DbTableColumnNanme26 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme26, 0) + "', " +
                                " DbTableColumnNanme27 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme27, 0) + "', " +
                                " DbTableColumnNanme28 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme28, 0) + "', " +
                                " DbTableColumnNanme29 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme29, 0) + "', " +
                                " DbTableColumnNanme30 = '" + GlobalMemberValues.getDBTextAfterChecked(DbTableColumnNanme30, 0) + "' " +
                                " where idx = " + DbTableColumnNanme01;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = DbTableColumnNanme01 + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("_databaseTablename_", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        DbTableColumnNanme01 = "";
                        DbTableColumnNanme02 = "";
                        DbTableColumnNanme03 = "";
                        DbTableColumnNanme04 = "";
                        DbTableColumnNanme05 = "";
                        DbTableColumnNanme06 = "";
                        DbTableColumnNanme07 = "";
                        DbTableColumnNanme08 = "";
                        DbTableColumnNanme09 = "";
                        DbTableColumnNanme10 = "";
                        DbTableColumnNanme11 = "";
                        DbTableColumnNanme12 = "";
                        DbTableColumnNanme13 = "";
                        DbTableColumnNanme14 = "";
                        DbTableColumnNanme15 = "";
                        DbTableColumnNanme16 = "";
                        DbTableColumnNanme17 = "";
                        DbTableColumnNanme18 = "";
                        DbTableColumnNanme19 = "";
                        DbTableColumnNanme20 = "";
                        DbTableColumnNanme21 = "";
                        DbTableColumnNanme22 = "";
                        DbTableColumnNanme23 = "";
                        DbTableColumnNanme24 = "";
                        DbTableColumnNanme25 = "";
                        DbTableColumnNanme26 = "";
                        DbTableColumnNanme27 = "";
                        DbTableColumnNanme28 = "";
                        DbTableColumnNanme29 = "";
                        DbTableColumnNanme30 = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("_databaseTablename_", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("_databaseTablename_", "----------------------------\n");
            GlobalMemberValues.logWrite("_databaseTablename_", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("_databaseTablename_", "----------------------------\n");
        }
        return null;
    }
}
