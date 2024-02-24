package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2018-09-13.
 */
public class APIDownLoad_salon_storeservice_sub_timemenuprice extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeservice_sub_timemenuprice";

    String idx = "";
    String scode = "";
    String sidx = "";
    String midx = "";
    String svcidx = "";

    String m_0 = "";
    String m_1 = "";
    String m_2 = "";
    String m_3 = "";
    String m_4 = "";
    String m_5 = "";
    String m_6 = "";

    String a_0 = "";
    String a_1 = "";
    String a_2 = "";
    String a_3 = "";
    String a_4 = "";
    String a_5 = "";
    String a_6 = "";

    String e_0 = "";
    String e_1 = "";
    String e_2 = "";
    String e_3 = "";
    String e_4 = "";
    String e_5 = "";
    String e_6 = "";

    String n_0 = "";
    String n_1 = "";
    String n_2 = "";
    String n_3 = "";
    String n_4 = "";
    String n_5 = "";
    String n_6 = "";

    String mdate = "";

    /**********************************************************/

    String mInsertSqlQuery = "";
    String mUpdateSqlQuery = "";
    String mQueryCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEB_URL + "?" +
            "RequestSqlType=" + mDbTableName + "&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=" + GlobalMemberValues.API_WEB_DB_XML_TYPE +
            "&midx=" + CloudBackOffice.mCategoryIdx + "&appversioncode=" + GlobalMemberValues.getAppVersionCode();

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();
    public Vector<String> sqlQueryVecIns = new Vector<String>();

    @Override
    protected Object doInBackground(Object[] params) {
        Log.i("downloadurl", "downloadurl : " + mStrUrl + "\n");

        try {
            GlobalMemberValues gmvs = new GlobalMemberValues();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite("menudownloadlog", "url : " + mStrUrl + "\n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            if (!GlobalMemberValues.isStrEmpty(CloudBackOffice.mCategoryIdx)) {
                String tempSpt[] = CloudBackOffice.mCategoryIdx.split("JJJ");
                String tempSql = "";
                for (int i = 0; i < tempSpt.length; i++) {
                    tempSql = "delete from " + mDbTableName;
                    tempSql += " where midx = '" + tempSpt[i] + "' ";
                    sqlQueryVecIns.add(tempSql);
                }
            } else {
                String tempSql = "";
                tempSql = "delete from " + mDbTableName;
                sqlQueryVecIns.add(tempSql);
            }

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
                            GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("midx")) {
                            midx = xpp.getText();
                        }
                        if (tagName.equals("svcidx")) {
                            svcidx = xpp.getText();
                        }

                        if (tagName.equals("m_0")) {
                            m_0 = xpp.getText();
                        }
                        if (tagName.equals("m_1")) {
                            m_1 = xpp.getText();
                        }
                        if (tagName.equals("m_2")) {
                            m_2 = xpp.getText();
                        }
                        if (tagName.equals("m_3")) {
                            m_3 = xpp.getText();
                        }
                        if (tagName.equals("m_4")) {
                            m_4 = xpp.getText();
                        }
                        if (tagName.equals("m_5")) {
                            m_5 = xpp.getText();
                        }
                        if (tagName.equals("m_6")) {
                            m_6 = xpp.getText();
                        }

                        if (tagName.equals("a_0")) {
                            a_0 = xpp.getText();
                        }
                        if (tagName.equals("a_1")) {
                            a_1 = xpp.getText();
                        }
                        if (tagName.equals("a_2")) {
                            a_2 = xpp.getText();
                        }
                        if (tagName.equals("a_3")) {
                            a_3 = xpp.getText();
                        }
                        if (tagName.equals("a_4")) {
                            a_4 = xpp.getText();
                        }
                        if (tagName.equals("a_5")) {
                            a_5 = xpp.getText();
                        }
                        if (tagName.equals("a_6")) {
                            a_6 = xpp.getText();
                        }

                        if (tagName.equals("e_0")) {
                            e_0 = xpp.getText();
                        }
                        if (tagName.equals("e_1")) {
                            e_1 = xpp.getText();
                        }
                        if (tagName.equals("e_2")) {
                            e_2 = xpp.getText();
                        }
                        if (tagName.equals("e_3")) {
                            e_3 = xpp.getText();
                        }
                        if (tagName.equals("e_4")) {
                            e_4 = xpp.getText();
                        }
                        if (tagName.equals("e_5")) {
                            e_5 = xpp.getText();
                        }
                        if (tagName.equals("e_6")) {
                            e_6 = xpp.getText();
                        }

                        if (tagName.equals("n_0")) {
                            n_0 = xpp.getText();
                        }
                        if (tagName.equals("n_1")) {
                            n_1 = xpp.getText();
                        }
                        if (tagName.equals("n_2")) {
                            n_2 = xpp.getText();
                        }
                        if (tagName.equals("n_3")) {
                            n_3 = xpp.getText();
                        }
                        if (tagName.equals("n_4")) {
                            n_4 = xpp.getText();
                        }
                        if (tagName.equals("n_5")) {
                            n_5 = xpp.getText();
                        }
                        if (tagName.equals("n_6")) {
                            n_6 = xpp.getText();
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
                        mInsertSqlQuery = "insert into " + mDbTableName +
                                " ( " +
                                " idx, scode, sidx, midx, svcidx, " +
                                " m_0, m_1, m_2, m_3, m_4, m_5, m_6, " +
                                " a_0, a_1, a_2, a_3, a_4, a_5, a_6, " +
                                " e_0, e_1, e_2, e_3, e_4, e_5, e_6, " +
                                " n_0, n_1, n_2, n_3, n_4, n_5, n_6, " +
                                " mdate " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(svcidx, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_0, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_4, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_5, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(m_6, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_0, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_4, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_5, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(a_6, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_0, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_4, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_5, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(e_6, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_0, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_4, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_5, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(n_6, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "' " +
                                ")";


                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " midx = '" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                " svcidx = '" + GlobalMemberValues.getDBTextAfterChecked(svcidx, 0) + "', " +

                                " m_0 = '" + GlobalMemberValues.getDBTextAfterChecked(m_0, 0) + "', " +
                                " m_1 = '" + GlobalMemberValues.getDBTextAfterChecked(m_1, 0) + "', " +
                                " m_2 = '" + GlobalMemberValues.getDBTextAfterChecked(m_2, 0) + "', " +
                                " m_3 = '" + GlobalMemberValues.getDBTextAfterChecked(m_3, 0) + "', " +
                                " m_4 = '" + GlobalMemberValues.getDBTextAfterChecked(m_4, 0) + "', " +
                                " m_5 = '" + GlobalMemberValues.getDBTextAfterChecked(m_5, 0) + "', " +
                                " m_6 = '" + GlobalMemberValues.getDBTextAfterChecked(m_6, 0) + "', " +

                                " a_0 = '" + GlobalMemberValues.getDBTextAfterChecked(a_0, 0) + "', " +
                                " a_1 = '" + GlobalMemberValues.getDBTextAfterChecked(a_1, 0) + "', " +
                                " a_2 = '" + GlobalMemberValues.getDBTextAfterChecked(a_2, 0) + "', " +
                                " a_3 = '" + GlobalMemberValues.getDBTextAfterChecked(a_3, 0) + "', " +
                                " a_4 = '" + GlobalMemberValues.getDBTextAfterChecked(a_4, 0) + "', " +
                                " a_5 = '" + GlobalMemberValues.getDBTextAfterChecked(a_5, 0) + "', " +
                                " a_6 = '" + GlobalMemberValues.getDBTextAfterChecked(a_6, 0) + "', " +

                                " e_0 = '" + GlobalMemberValues.getDBTextAfterChecked(e_0, 0) + "', " +
                                " e_1 = '" + GlobalMemberValues.getDBTextAfterChecked(e_1, 0) + "', " +
                                " e_2 = '" + GlobalMemberValues.getDBTextAfterChecked(e_2, 0) + "', " +
                                " e_3 = '" + GlobalMemberValues.getDBTextAfterChecked(e_3, 0) + "', " +
                                " e_4 = '" + GlobalMemberValues.getDBTextAfterChecked(e_4, 0) + "', " +
                                " e_5 = '" + GlobalMemberValues.getDBTextAfterChecked(e_5, 0) + "', " +
                                " e_6 = '" + GlobalMemberValues.getDBTextAfterChecked(e_6, 0) + "', " +

                                " n_0 = '" + GlobalMemberValues.getDBTextAfterChecked(n_0, 0) + "', " +
                                " n_1 = '" + GlobalMemberValues.getDBTextAfterChecked(n_1, 0) + "', " +
                                " n_2 = '" + GlobalMemberValues.getDBTextAfterChecked(n_2, 0) + "', " +
                                " n_3 = '" + GlobalMemberValues.getDBTextAfterChecked(n_3, 0) + "', " +
                                " n_4 = '" + GlobalMemberValues.getDBTextAfterChecked(n_4, 0) + "', " +
                                " n_5 = '" + GlobalMemberValues.getDBTextAfterChecked(n_5, 0) + "', " +
                                " n_6 = '" + GlobalMemberValues.getDBTextAfterChecked(n_6, 0) + "', " +

                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "' " +

                                " where idx = " + idx;

                        sqlQueryVecIns.add(mInsertSqlQuery);
//                        // 저장된 데이터가 있는지 검색해서 데이터기 있으면 update 없으면 insert 한다.
//                        if (gmvs.isDataInDBTable(mDbTableName, idx)) {
//                            sqlQueryVecIns.add(mUpdateSqlQuery);
//                        } else {
//                            sqlQueryVecIns.add(mInsertSqlQuery);
//                        }

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice_log", "mQueryCollection : " + mQueryCollection);

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        scode = "";
                        sidx = "";
                        midx = "";
                        svcidx = "";

                        m_0 = "";
                        m_1 = "";
                        m_2 = "";
                        m_3 = "";
                        m_4 = "";
                        m_5 = "";
                        m_6 = "";

                        a_0 = "";
                        a_1 = "";
                        a_2 = "";
                        a_3 = "";
                        a_4 = "";
                        a_5 = "";
                        a_6 = "";

                        e_0 = "";
                        e_1 = "";
                        e_2 = "";
                        e_3 = "";
                        e_4 = "";
                        e_5 = "";
                        e_6 = "";

                        n_0 = "";
                        n_1 = "";
                        n_2 = "";
                        n_3 = "";
                        n_4 = "";
                        n_5 = "";
                        n_6 = "";

                        mdate = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeservice_sub_timemenuprice", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 0) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("jjjapiquerylog", "----------------------------\n");
            GlobalMemberValues.logWrite("jjjapiquerylog", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("jjjapiquerylog", "----------------------------\n");
        }
        return null;
    }
}
