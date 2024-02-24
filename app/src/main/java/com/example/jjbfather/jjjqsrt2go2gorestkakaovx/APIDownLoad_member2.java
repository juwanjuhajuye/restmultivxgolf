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
public class APIDownLoad_member2 extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "member2";

    String uid = "";
    String phone = "";
    String mobile = "";
    String byear = "";
    String bmonth = "";
    String bday = "";
    String addr1 = "";
    String addr2 = "";
    String zip = "";
    String state = "";
    String city = "";
    String country = "";
    String membershipcardno = "";
    String mileage = "";
    String memo = "";
    String email = "";
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

            //member2 는 테이블 데이터를 삭제하지 않는다.
            //sqlQueryVecIns.add("delete from " + mDbTableName);

            int tempUidCnt = 0;

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
                            GlobalMemberValues.logWrite("member2", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("member2", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("member2", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("member2", "성공일때만.." + "\n");
                        if (tagName.equals("uid")) {
                            uid = xpp.getText();
                        }
                        if (tagName.equals("phone")) {
                            phone = xpp.getText();
                        }
                        if (tagName.equals("mobile")) {
                            mobile = xpp.getText();
                        }
                        if (tagName.equals("byear")) {
                            byear = xpp.getText();
                        }
                        if (tagName.equals("bmonth")) {
                            bmonth = xpp.getText();
                        }
                        if (tagName.equals("bday")) {
                            bday = xpp.getText();
                        }
                        if (tagName.equals("addr1")) {
                            addr1 = xpp.getText();
                        }
                        if (tagName.equals("addr2")) {
                            addr2 = xpp.getText();
                        }
                        if (tagName.equals("zip")) {
                            zip = xpp.getText();
                        }
                        if (tagName.equals("state")) {
                            state = xpp.getText();
                        }
                        if (tagName.equals("city")) {
                            city = xpp.getText();
                        }
                        if (tagName.equals("country")) {
                            country = xpp.getText();
                        }
                        if (tagName.equals("membershipcardno")) {
                            membershipcardno = xpp.getText();
                        }
                        if (tagName.equals("mileage")) {
                            mileage = xpp.getText();
                        }
                        if (tagName.equals("memo")) {
                            memo = xpp.getText();
                        }
                        if (tagName.equals("email")) {
                            email = xpp.getText();
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
                                " uid, phone, mobile, byear, bmonth, bday, addr1, addr2, " +
                                " zip, state, city, country, membershipcardno, mileage, memo, email " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(phone, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mobile, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(byear, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(bmonth, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(bday, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addr1, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(addr2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(zip, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(state, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(city, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(country, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(membershipcardno, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mileage, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(memo, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(email, 0) + "' " +
                                ")";

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " phone = '" + GlobalMemberValues.getDBTextAfterChecked(phone, 0) + "', " +
                                " mobile = '" + GlobalMemberValues.getDBTextAfterChecked(mobile, 0) + "', " +
                                " byear = '" + GlobalMemberValues.getDBTextAfterChecked(byear, 0) + "', " +
                                " bmonth = '" + GlobalMemberValues.getDBTextAfterChecked(bmonth, 0) + "', " +
                                " bday = '" + GlobalMemberValues.getDBTextAfterChecked(bday, 0) + "', " +
                                " addr1 = '" + GlobalMemberValues.getDBTextAfterChecked(addr1, 0) + "', " +
                                " addr2 = '" + GlobalMemberValues.getDBTextAfterChecked(addr2, 0) + "', " +
                                " zip = '" + GlobalMemberValues.getDBTextAfterChecked(zip, 0) + "', " +
                                " state = '" + GlobalMemberValues.getDBTextAfterChecked(state, 0) + "', " +
                                " city = '" + GlobalMemberValues.getDBTextAfterChecked(city, 0) + "', " +
                                " country = '" + GlobalMemberValues.getDBTextAfterChecked(country, 0) + "', " +
                                " membershipcardno = '" + GlobalMemberValues.getDBTextAfterChecked(membershipcardno, 0) + "', " +
                                //" mileage = '" + GlobalMemberValues.getDBTextAfterChecked(mileage, 0) + "', " +
                                " memo = '" + GlobalMemberValues.getDBTextAfterChecked(memo, 0) + "', " +
                                " email = '" + GlobalMemberValues.getDBTextAfterChecked(email, 0) + "' " +
                                " where uid = '" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "'";

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = uid + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        tempUidCnt = GlobalMemberValues.getIntAtString(
                                MainActivity.mDbInit.dbExecuteReadReturnString(
                                        "select count(uid) from member2 where uid = '" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "' "
                                )
                        );
                        if (tempUidCnt > 0) {
                            sqlQueryVecIns.add(mUpdateSqlQuery);
                        } else {
                            sqlQueryVecIns.add(mInsertSqlQuery);
                        }

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("member2", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        uid = "";
                        phone = "";
                        mobile = "";
                        byear = "";
                        bmonth = "";
                        bday = "";
                        addr1 = "";
                        addr2 = "";
                        zip = "";
                        state = "";
                        city = "";
                        country = "";
                        membershipcardno = "";
                        mileage = "";
                        memo = "";
                        email = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("member2", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 0) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("member2", "----------------------------\n");
            GlobalMemberValues.logWrite("member2", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("member2", "----------------------------\n");
        }
        return null;
    }
}
