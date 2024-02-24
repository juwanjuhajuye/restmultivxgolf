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
public class APIDownLoad_member1 extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "member1";

    String uid = "";
    String idx = "";
    String name = "";
    String password = "";
    String wdate = "";
    String mdate = "";
    String aid = "";
    String emailreceiveyn = "";
    String lastlogin = "";
    String lastvisit = "";
    String delyn = "";
    String gender = "";
    String grade = "";
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

    // 회원아이디 임시저장용 Vector
    public Vector<String> tempSavedCustomerId_vec = new Vector<String>();

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
                            GlobalMemberValues.logWrite("member1", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("member1", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("member1", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("member1", "성공일때만.." + "\n");
                        if (tagName.equals("uid")) {
                            uid = xpp.getText();
                        }
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("name")) {
                            name = xpp.getText();
                        }
                        if (tagName.equals("password")) {
                            password = xpp.getText();
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
                        if (tagName.equals("emailreceiveyn")) {
                            emailreceiveyn = xpp.getText();
                        }
                        if (tagName.equals("lastlogin")) {
                            lastlogin = xpp.getText();
                        }
                        if (tagName.equals("lastvisit")) {
                            lastvisit = xpp.getText();
                        }
                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }
                        if (tagName.equals("gender")) {
                            gender = xpp.getText();
                        }
                        if (tagName.equals("grade")) {
                            grade = xpp.getText();
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
                                " uid, idx, name, password, wdate, mdate, aid, emailreceiveyn, " +
                                " lastlogin, lastvisit, delyn, gender, grade " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(name, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(password, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(emailreceiveyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(lastlogin, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(lastvisit, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(gender, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(grade, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " idx = '" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                " name = '" + GlobalMemberValues.getDBTextAfterChecked(name, 0) + "', " +
                                " password = '" + GlobalMemberValues.getDBTextAfterChecked(password, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " aid = '" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                " emailreceiveyn = '" + GlobalMemberValues.getDBTextAfterChecked(emailreceiveyn, 0) + "', " +
                                " lastlogin = '" + GlobalMemberValues.getDBTextAfterChecked(lastlogin, 0) + "', " +
                                " lastvisit = '" + GlobalMemberValues.getDBTextAfterChecked(lastvisit, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " gender = '" + GlobalMemberValues.getDBTextAfterChecked(gender, 0) + "', " +
                                " grade = '" + GlobalMemberValues.getDBTextAfterChecked(grade, 0) + "' " +
                                " where uid = '" + GlobalMemberValues.getDBTextAfterChecked(uid, 0) + "' ";

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = uid + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        tempSavedCustomerId_vec.addElement(uid);

                        GlobalMemberValues.logWrite("member1", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        uid = "";
                        idx = "";
                        name = "";
                        password = "";
                        wdate = "";
                        mdate = "";
                        aid = "";
                        emailreceiveyn = "";
                        lastlogin = "";
                        lastvisit = "";
                        delyn = "";
                        gender = "";
                        grade = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("member1", "예외발생 : " + e.getMessage());
        }

        // 먼저 회원검색을 통해 클라우드에서 삭제된 회원정보는 포스에서도 삭제한다.
        String returnDelResult = "N";
        if (tempSavedCustomerId_vec.size() > 0) {
            returnDelResult = GlobalMemberValues.deleteMemberInfoAfterCheckingMember(tempSavedCustomerId_vec);
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                if (returnDelResult == "Y" || returnDelResult.equals("Y")) {
                    returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
                }
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("member1", "----------------------------\n");
            GlobalMemberValues.logWrite("member1", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("member1", "----------------------------\n");
        }
        return null;
    }
}
