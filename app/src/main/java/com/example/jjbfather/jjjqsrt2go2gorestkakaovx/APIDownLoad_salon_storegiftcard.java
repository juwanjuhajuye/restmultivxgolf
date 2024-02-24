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
public class APIDownLoad_salon_storegiftcard extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storegiftcard";

    String idx = "";
    String sidx = "";
    String name = "";
    String description = "";
    String wdate = "";
    String mdate = "";
    String aid = "";
    String price = "";
    String pos_code = "";
    String delyn = "";
    String pointratio = "";
    String saleprice = "";
    String salestart = "";
    String saleend = "";
    String strFileName = "";
    String strFilePath = "";
    String strOrgFileName = "";
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
                            GlobalMemberValues.logWrite("salon_storegiftcard", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storegiftcard", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storegiftcard", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storegiftcard", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("name")) {
                            name = xpp.getText();
                        }
                        if (tagName.equals("description")) {
                            description = xpp.getText();
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
                        if (tagName.equals("price")) {
                            price = xpp.getText();
                        }
                        if (tagName.equals("pos_code")) {
                            pos_code = xpp.getText();
                        }
                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }
                        if (tagName.equals("pointratio")) {
                            pointratio = xpp.getText();
                        }
                        if (tagName.equals("saleprice")) {
                            saleprice = xpp.getText();
                        }
                        if (tagName.equals("salestart")) {
                            salestart = xpp.getText();
                        }
                        if (tagName.equals("saleend")) {
                            saleend = xpp.getText();
                        }
                        if (tagName.equals("strFileName")) {
                            strFileName = xpp.getText();
                        }
                        if (tagName.equals("strFilePath")) {
                            strFilePath = xpp.getText();
                        }
                        if (tagName.equals("strOrgFileName")) {
                            strOrgFileName = xpp.getText();
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
                                " idx, sidx, name, description, wdate, mdate, aid, price, " +
                                " pos_code, delyn, pointratio, saleprice, salestart, saleend, strFileName, strFilePath, " +
                                " strOrgFileName " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(name, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(description, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(price, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pos_code, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(saleprice, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(salestart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(saleend, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strFileName, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strFilePath, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strOrgFileName, 0) + "' " +
                                ")";
                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " name = '" + GlobalMemberValues.getDBTextAfterChecked(name, 0) + "', " +
                                " description = '" + GlobalMemberValues.getDBTextAfterChecked(description, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " aid = '" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                " price = '" + GlobalMemberValues.getDBTextAfterChecked(price, 0) + "', " +
                                " pos_code = '" + GlobalMemberValues.getDBTextAfterChecked(pos_code, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " pointratio = '" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                " saleprice = '" + GlobalMemberValues.getDBTextAfterChecked(saleprice, 0) + "', " +
                                " salestart = '" + GlobalMemberValues.getDBTextAfterChecked(salestart, 0) + "', " +
                                " saleend = '" + GlobalMemberValues.getDBTextAfterChecked(saleend, 0) + "', " +
                                " strFileName = '" + GlobalMemberValues.getDBTextAfterChecked(strFileName, 0) + "', " +
                                " strFilePath = '" + GlobalMemberValues.getDBTextAfterChecked(strFilePath, 0) + "', " +
                                " strOrgFileName = '" + GlobalMemberValues.getDBTextAfterChecked(strOrgFileName, 0) + "' " +
                                " where idx = " + idx;

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storegiftcard", "mQueryCollection : " + mQueryCollection);
                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        sidx = "";
                        name = "";
                        description = "";
                        wdate = "";
                        mdate = "";
                        aid = "";
                        price = "";
                        pos_code = "";
                        delyn = "";
                        pointratio = "";
                        saleprice = "";
                        salestart = "";
                        saleend = "";
                        strFileName = "";
                        strFilePath = "";
                        strOrgFileName = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storegiftcard", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 1) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("salon_storegiftcard", "----------------------------\n");
            GlobalMemberValues.logWrite("salon_storegiftcard", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("salon_storegiftcard", "----------------------------\n");
        }
        return null;
    }
}
