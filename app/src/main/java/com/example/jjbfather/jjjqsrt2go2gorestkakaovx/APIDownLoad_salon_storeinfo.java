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
public class APIDownLoad_salon_storeinfo extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeinfo";

    String idx = "";
    String scode = "";
    String name = "";
    String addr1 = "";
    String addr2 = "";
    String city = "";
    String state = "";
    String zip = "";
    String country = "";
    String phone = "";
    String fax = "";
    String bizhour = "";
    String servicetime1 = "";
    String servicetime2 = "";
    String wdate = "";
    String mdate = "";
    String aid = "";
    String store_workweekday = "";
    String status = "";
    String email = "";

    // 06202023
    String name_en = "";

    // 09012023
    String name2 = "";

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
                            GlobalMemberValues.logWrite("salon_storeinfo", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeinfo", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeinfo", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeinfo", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("name")) {
                            name = xpp.getText();
                        }
                        if (tagName.equals("addr1")) {
                            addr1 = xpp.getText();
                        }
                        if (tagName.equals("addr2")) {
                            addr2 = xpp.getText();
                        }
                        if (tagName.equals("city")) {
                            city = xpp.getText();
                        }
                        if (tagName.equals("state")) {
                            state = xpp.getText();
                        }
                        if (tagName.equals("zip")) {
                            zip = xpp.getText();
                        }
                        if (tagName.equals("country")) {
                            country = xpp.getText();
                        }
                        if (tagName.equals("phone")) {
                            phone = xpp.getText();
                        }
                        if (tagName.equals("fax")) {
                            fax = xpp.getText();
                        }
                        if (tagName.equals("bizhour")) {
                            bizhour = xpp.getText();
                        }
                        if (tagName.equals("servicetime1")) {
                            servicetime1 = xpp.getText();
                        }
                        if (tagName.equals("servicetime2")) {
                            servicetime2 = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }
                        if (tagName.equals("mdate")) {
                            mdate = xpp.getText();
                        }
                        if (tagName.equals("aid")) {
                            aid = xpp.getText();
                        }
                        if (tagName.equals("store_workweekday")) {
                            store_workweekday = xpp.getText();
                        }
                        if (tagName.equals("status")) {
                            status = xpp.getText();
                        }
                        if (tagName.equals("email")) {
                            email = xpp.getText();
                        }
                        if (tagName.equals("name_en")){
                            name_en = xpp.getText();
                        }

                        if (tagName.equals("name2")) {
                            name2 = xpp.getText();
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
                                " idx, scode, name, name2, addr1, addr2, city, state, zip, country, phone, fax, bizhour, " +
                                " servicetime1, servicetime2, wdate, mdate, aid, store_workweekday, status, email, name_en " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(name, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(name2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addr1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addr2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(city, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(state, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(zip, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(country, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(phone, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(fax, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(bizhour, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicetime1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicetime2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(store_workweekday, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(status, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(email, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(name_en, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " name = '" + GlobalMemberValues.getDBTextAfterChecked(name  , 0) + "', " +
                                " name2 = '" + GlobalMemberValues.getDBTextAfterChecked(name2  , 0) + "', " +
                                " addr1 = '" + GlobalMemberValues.getDBTextAfterChecked(addr1, 0) + "', " +
                                " addr2 = '" + GlobalMemberValues.getDBTextAfterChecked(addr2, 0) + "', " +
                                " city = '" + GlobalMemberValues.getDBTextAfterChecked(city, 0) + "', " +
                                " state = '" + GlobalMemberValues.getDBTextAfterChecked(state, 0) + "', " +
                                " zip = '" + GlobalMemberValues.getDBTextAfterChecked(zip, 0) + "', " +
                                " country = '" + GlobalMemberValues.getDBTextAfterChecked(country, 0) + "', " +
                                " phone = '" + GlobalMemberValues.getDBTextAfterChecked(phone, 0) + "', " +
                                " fax = '" + GlobalMemberValues.getDBTextAfterChecked(fax, 0) + "', " +
                                " bizhour = '" + GlobalMemberValues.getDBTextAfterChecked(bizhour, 0) + "', " +
                                " servicetime1 = '" + GlobalMemberValues.getDBTextAfterChecked(servicetime1, 0) + "', " +
                                " servicetime2 = '" + GlobalMemberValues.getDBTextAfterChecked(servicetime2, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " aid = '" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                " store_workweekday = '" + GlobalMemberValues.getDBTextAfterChecked(store_workweekday, 0) + "', " +
                                " status = '" + GlobalMemberValues.getDBTextAfterChecked(status, 0) + "', " +
                                " email = '" + GlobalMemberValues.getDBTextAfterChecked(email, 0) + "', " +
                                " name_en = '" + GlobalMemberValues.getDBTextAfterChecked(name_en, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storeinfo", "mQueryCollection : " + mQueryCollection);

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        scode = "";
                        name = "";
                        addr1 = "";
                        addr2 = "";
                        city = "";
                        state = "";
                        zip = "";
                        country = "";
                        phone = "";
                        fax = "";
                        bizhour = "";
                        servicetime1 = "";
                        servicetime2 = "";
                        wdate = "";
                        mdate = "";
                        aid = "";
                        store_workweekday = "";
                        status = "";
                        email = "";
                        name_en = "";
                        name2 = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeinfo", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_storeinfo", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_storeinfo", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_storeinfo", "----------------------------\n");
        }
        return null;
    }
}
