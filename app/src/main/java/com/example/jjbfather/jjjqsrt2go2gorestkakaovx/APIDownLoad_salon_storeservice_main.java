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
public class APIDownLoad_salon_storeservice_main extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeservice_main";

    String idx = "";
    String sidx = "";
    String servicename = "";
    String wdate = "";
    String mdate = "";
    String aid = "";
    String pos_main_code = "";
    String delyn = "";
    String positionNo = "";
    String colorNo = "";
    String commissionratio = "";
    String pointratio = "";
    String servicename2 = "";
    String menusu = "";
    String taxfreeyn = "";
    String kitchenprintnum = "";

    String categoryusezone = "";

    String labelprintnum = "";

    String totaloptionitem = "";
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
        try {
            GlobalMemberValues gmvs = new GlobalMemberValues();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite("apiurllog", "url : " + mStrUrl + "\n");

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
                    tempSql += " where idx = '" + tempSpt[i] + "' ";
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
                            GlobalMemberValues.logWrite("salon_storeservice_main", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeservice_main", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeservice_main", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeservice_main", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }

                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }

                        if (tagName.equals("servicename")) {
                            servicename = xpp.getText();
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

                        if (tagName.equals("pos_main_code")) {
                            pos_main_code = xpp.getText();
                        }

                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }

                        if (tagName.equals("positionNo")) {
                            positionNo = xpp.getText();
                        }

                        if (tagName.equals("colorNo")) {
                            colorNo = xpp.getText();
                        }

                        if (tagName.equals("commissionratio")) {
                            commissionratio = xpp.getText();
                        }

                        if (tagName.equals("pointratio")) {
                            pointratio = xpp.getText();
                        }
                        if (tagName.equals("servicename2")) {
                            servicename2 = xpp.getText();
                        }
                        if (tagName.equals("menusu")) {
                            menusu = xpp.getText();
                        }
                        if (tagName.equals("taxfreeyn")) {
                            taxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("kitchenprintnum")) {
                            kitchenprintnum = xpp.getText();
                        }
                        if (tagName.equals("categoryusezone")) {
                            categoryusezone = xpp.getText();
                        }
                        if (tagName.equals("labelprintnum")) {
                            labelprintnum = xpp.getText();
                        }

                        if (tagName.equals("totaloptionitem")) {
                            totaloptionitem = xpp.getText();
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
                                " idx, sidx, servicename, wdate, mdate, aid, pos_main_code, delyn, positionNo, colorNo, commissionratio, pointratio, " +
                                " servicename2, menusu, taxfreeyn, kitchenprintnum, categoryusezone, labelprintnum " +
                                "  ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicename, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pos_main_code, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(positionNo, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(colorNo, 0) + "'," +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(commissionratio, 0) + "'," +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicename2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(menusu, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintnum, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(categoryusezone, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(labelprintnum, 0) + "' " +
                                ")";


                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " servicename = '" + GlobalMemberValues.getDBTextAfterChecked(servicename, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " aid = '" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                " pos_main_code = '" + GlobalMemberValues.getDBTextAfterChecked(pos_main_code, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " positionNo = '" + GlobalMemberValues.getDBTextAfterChecked(positionNo, 0) + "', " +
                                " colorNo = '" + GlobalMemberValues.getDBTextAfterChecked(colorNo, 0) + "', " +
                                " commissionratio = '" + GlobalMemberValues.getDBTextAfterChecked(commissionratio, 0) + "', " +
                                " pointratio = '" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                " servicename2 = '" + GlobalMemberValues.getDBTextAfterChecked(servicename2, 0) + "', " +
                                " menusu = '" + GlobalMemberValues.getDBTextAfterChecked(menusu, 0) + "', " +
                                " taxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(taxfreeyn, 0) + "', " +
                                " kitchenprintnum = '" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintnum, 0) + "', " +
                                " categoryusezone = '" + GlobalMemberValues.getDBTextAfterChecked(categoryusezone, 0) + "', " +
                                " labelprintnum = '" + GlobalMemberValues.getDBTextAfterChecked(labelprintnum, 0) + "' " +
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

                        GlobalMemberValues.logWrite("salon_storeservice_main", "mQueryCollection : " + mQueryCollection + "\n");

                        // 화면재개 지연시간 지정
                        if (!GlobalMemberValues.isStrEmpty(totaloptionitem)) {
                            GlobalMemberValues.RESTARTSCREEN_DELYTIME = totaloptionitem;
                        }

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        sidx = "";
                        servicename = "";
                        wdate = "";
                        mdate = "";
                        aid = "";
                        pos_main_code = "";
                        delyn = "";
                        positionNo = "";
                        colorNo = "";
                        commissionratio = "";
                        pointratio = "";
                        servicename2 = "";
                        menusu = "";
                        taxfreeyn = "";
                        kitchenprintnum = "";

                        categoryusezone = "";

                        labelprintnum = "";

                        totaloptionitem = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeservice_main", "예외발생 : " + e.getMessage());
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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (GlobalMemberValues.mProgressBar != null){
            GlobalMemberValues.mProgressBar.setProgress((GlobalMemberValues.mProgressBar.getProgress() + 10));
        }
    }
}
