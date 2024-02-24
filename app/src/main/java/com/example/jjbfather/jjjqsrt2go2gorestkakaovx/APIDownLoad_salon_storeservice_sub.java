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
public class APIDownLoad_salon_storeservice_sub extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeservice_sub";

    String idx = "";
    String midx = "";
    String servicename = "";
    String timefee = "";
    String description = "";
    String wdate = "";
    String mdate = "";
    String aid = "";
    String service_price = "";
    String pos_main_code = "";
    String pos_sub_code = "";
    String delyn = "";
    String subServiceTime = "";
    String positionNo = "";
    String commissionratioType = "";
    String commissionratio = "";
    String pointratio = "";
    String saleprice = "";
    String salestart = "";
    String saleend = "";
    String strFileName = "";
    String strFilePath = "";
    String strOrgFileName = "";
    String setmenuYN = "";
    String setmenuListArr = "";
    String activeyn = "";
    String taxfreeyn = "";
    String timemenuyn = "";
    String timemenutime = "";
    String modifiertype = "";
    String kitchenprintyn = "";
    String kitchenprintnum = "";
    String weekdays = "";
    String dcapplyyn = "";
    String servicename2 = "";
    String servicename3 = "";
    String servicenamealt = "";
    String colorNo = "";
    String taxtype = "";
    String taxvalues = "";
    String taxexemptvalues = "";

    String scaleuseyn = "";
    String tareidx = "";
    String tareweight = "";
    String tareqty = "";
    String taretotalweight = "";
    String perweight = "";

    String dynamicpriceyn = "";

    String barcode = "";

    String divideryn = "";

    String nameforlabel = "";

    String imageusezone = "";

    String menuusezone = "";

    String mdforceyn = "";

    String showynifzero_m = "";
    String showynifzero_k = "";

    String labelprintyn = "";
    String labelprintnum = "";

    String wmuseyn = "";
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

            GlobalMemberValues.logWrite("menudolwnloadlog", "url : " + mStrUrl + "\n");

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
                            GlobalMemberValues.logWrite("salon_storeservice_sub", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeservice_sub", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeservice_sub", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeservice_sub", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("midx")) {
                            midx = xpp.getText();
                        }
                        if (tagName.equals("servicename")) {
                            servicename = xpp.getText();
                        }
                        if (tagName.equals("timefee")) {
                            timefee = xpp.getText();
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
                        if (tagName.equals("service_price")) {
                            service_price = xpp.getText();
                        }
                        if (tagName.equals("pos_main_code")) {
                            pos_main_code = xpp.getText();
                        }
                        if (tagName.equals("pos_sub_code")) {
                            pos_sub_code = xpp.getText();
                        }
                        if (tagName.equals("delyn")) {
                            delyn = xpp.getText();
                        }
                        if (tagName.equals("subServiceTime")) {
                            subServiceTime = xpp.getText();
                        }
                        if (tagName.equals("positionNo")) {
                            positionNo = xpp.getText();
                        }
                        if (tagName.equals("commissionratioType")) {
                            commissionratioType = xpp.getText();
                        }
                        if (tagName.equals("commissionratio")) {
                            commissionratio = xpp.getText();
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
                        if (tagName.equals("setmenuYN")) {
                            setmenuYN = xpp.getText();
                        }
                        if (tagName.equals("setmenuListArr")) {
                            setmenuListArr = xpp.getText();
                        }
                        if (tagName.equals("activeyn")) {
                            activeyn = xpp.getText();
                        }
                        if (tagName.equals("taxfreeyn")) {
                            taxfreeyn = xpp.getText();
                        }
                        if (tagName.equals("timemenuyn")) {
                            timemenuyn = xpp.getText();
                        }
                        if (tagName.equals("timemenutime")) {
                            timemenutime = xpp.getText();
                        }
                        if (tagName.equals("modifiertype")) {
                            modifiertype = xpp.getText();
                        }
                        if (tagName.equals("kitchenprintyn")) {
                            kitchenprintyn = xpp.getText();
                        }
                        if (tagName.equals("kitchenprintnum")) {
                            kitchenprintnum = xpp.getText();
                        }
                        if (tagName.equals("weekdays")) {
                            weekdays = xpp.getText();
                        }
                        if (tagName.equals("dcapplyyn")) {
                            dcapplyyn = xpp.getText();
                        }
                        if (tagName.equals("servicename2")) {
                            servicename2 = xpp.getText();
                        }
                        if (tagName.equals("servicename3")) {
                            servicename3 = xpp.getText();
                        }
                        if (tagName.equals("servicenamealt")) {
                            servicenamealt = xpp.getText();
                        }
                        if (tagName.equals("colorNo")) {
                            colorNo = xpp.getText();
                        }
                        if (tagName.equals("taxtype")) {
                            taxtype = xpp.getText();
                        }
                        if (tagName.equals("taxvalues")) {
                            taxvalues = xpp.getText();
                        }
                        if (tagName.equals("taxexemptvalues")) {
                            taxexemptvalues = xpp.getText();
                        }

                        if (tagName.equals("scaleuseyn")) {
                            scaleuseyn = xpp.getText();
                        }
                        if (tagName.equals("tareidx")) {
                            tareidx = xpp.getText();
                        }
                        if (tagName.equals("tareweight")) {
                            tareweight = xpp.getText();
                        }
                        if (tagName.equals("tareqty")) {
                            tareqty = xpp.getText();
                        }
                        if (tagName.equals("taretotalweight")) {
                            taretotalweight = xpp.getText();
                        }
                        if (tagName.equals("perweight")) {
                            perweight = xpp.getText();
                        }

                        if (tagName.equals("dynamicpriceyn")) {
                            dynamicpriceyn = xpp.getText();
                        }

                        if (tagName.equals("barcode")) {
                            barcode = xpp.getText();
                        }

                        if (tagName.equals("divideryn")) {
                            divideryn = xpp.getText();
                        }

                        if (tagName.equals("nameforlabel")) {
                            nameforlabel = xpp.getText();
                        }

                        if (tagName.equals("imageusezone")) {
                            imageusezone = xpp.getText();
                        }

                        if (tagName.equals("menuusezone")) {
                            menuusezone = xpp.getText();
                        }

                        if (tagName.equals("mdforceyn")) {
                            mdforceyn = xpp.getText();
                        }

                        if (tagName.equals("showynifzero_m")) {
                            showynifzero_m = xpp.getText();
                        }
                        if (tagName.equals("showynifzero_k")) {
                            showynifzero_k = xpp.getText();
                        }

                        if (tagName.equals("labelprintyn")) {
                            labelprintyn = xpp.getText();
                        }
                        if (tagName.equals("labelprintnum")) {
                            labelprintnum = xpp.getText();
                        }

                        if (tagName.equals("wmuseyn")) {
                            wmuseyn = xpp.getText();
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
                                " idx, midx, servicename, timefee, description, wdate, mdate, aid, service_price, pos_main_code, pos_sub_code, " +
                                " delyn, subServiceTime, positionNo, commissionratioType, commissionratio, pointratio, saleprice, salestart, " +
                                " saleend, strFileName, strFilePath, strOrgFileName, setmenuYN, setmenuListArr, activeyn, taxfreeyn, timemenuyn, timemenutime, " +
                                " modifiertype, kitchenprintyn, kitchenprintnum, weekdays, dcapplyyn, " +
                                " servicename2, servicename3, servicenamealt, colorNo, taxtype, taxvalues, taxexemptvalues, " +
                                " scaleuseyn, tareidx, tareweight, tareqty, taretotalweight, perweight, dynamicpriceyn, barcode, divideryn, nameforlabel, imageusezone, menuusezone, mdforceyn, " +
                                " showynifzero_m, showynifzero_k, labelprintyn, labelprintnum, " +
                                " wmuseyn " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicename, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(timefee, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(description, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(service_price, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pos_main_code, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pos_sub_code, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(subServiceTime, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(positionNo, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(commissionratioType, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(commissionratio, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(saleprice, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(salestart, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(saleend, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strFileName, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strFilePath, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(strOrgFileName, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(setmenuYN, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(setmenuListArr, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(activeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxfreeyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(timemenuyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(timemenutime, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(modifiertype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintnum, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(weekdays, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(dcapplyyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicename2, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicename3, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(servicenamealt, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(colorNo, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxtype, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxvalues, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taxexemptvalues, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(scaleuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tareidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tareweight, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(tareqty, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(taretotalweight, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(perweight, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(dynamicpriceyn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(barcode, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(divideryn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(nameforlabel, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(imageusezone, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(menuusezone, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(mdforceyn, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(showynifzero_m, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(showynifzero_k, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(labelprintyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(labelprintnum, 0) + "', " +

                                "'" + GlobalMemberValues.getDBTextAfterChecked(wmuseyn, 0) + "' " +

                                ")";


                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " midx = '" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                " servicename = '" + GlobalMemberValues.getDBTextAfterChecked(servicename, 0) + "', " +
                                " timefee = '" + GlobalMemberValues.getDBTextAfterChecked(timefee, 0) + "', " +
                                " description = '" + GlobalMemberValues.getDBTextAfterChecked(description, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "', " +
                                " mdate = '" + GlobalMemberValues.getDBTextAfterChecked(mdate, 0) + "', " +
                                " aid = '" + GlobalMemberValues.getDBTextAfterChecked(aid, 0) + "', " +
                                " service_price = '" + GlobalMemberValues.getDBTextAfterChecked(service_price, 0) + "', " +
                                " pos_main_code = '" + GlobalMemberValues.getDBTextAfterChecked(pos_main_code, 0) + "', " +
                                " pos_sub_code = '" + GlobalMemberValues.getDBTextAfterChecked(pos_sub_code, 0) + "', " +
                                " delyn = '" + GlobalMemberValues.getDBTextAfterChecked(delyn, 0) + "', " +
                                " subServiceTime = '" + GlobalMemberValues.getDBTextAfterChecked(subServiceTime, 0) + "', " +
                                " positionNo = '" + GlobalMemberValues.getDBTextAfterChecked(positionNo, 0) + "', " +
                                " commissionratioType = '" + GlobalMemberValues.getDBTextAfterChecked(commissionratioType, 0) + "', " +
                                " commissionratio = '" + GlobalMemberValues.getDBTextAfterChecked(commissionratio, 0) + "', " +
                                " pointratio = '" + GlobalMemberValues.getDBTextAfterChecked(pointratio, 0) + "', " +
                                " saleprice = '" + GlobalMemberValues.getDBTextAfterChecked(saleprice, 0) + "', " +
                                " salestart = '" + GlobalMemberValues.getDBTextAfterChecked(salestart, 0) + "', " +
                                " saleend = '" + GlobalMemberValues.getDBTextAfterChecked(saleend, 0) + "', " +
                                " strFileName = '" + GlobalMemberValues.getDBTextAfterChecked(strFileName, 0) + "', " +
                                " strFilePath = '" + GlobalMemberValues.getDBTextAfterChecked(strFilePath, 0) + "', " +
                                " strOrgFileName = '" + GlobalMemberValues.getDBTextAfterChecked(strOrgFileName, 0) + "', " +
                                " setmenuYN = '" + GlobalMemberValues.getDBTextAfterChecked(setmenuYN, 0) + "', " +
                                " setmenuListArr = '" + GlobalMemberValues.getDBTextAfterChecked(setmenuListArr, 0) + "', " +
                                " activeyn = '" + GlobalMemberValues.getDBTextAfterChecked(activeyn, 0) + "', " +
                                " taxfreeyn = '" + GlobalMemberValues.getDBTextAfterChecked(taxfreeyn, 0) + "', " +
                                " timemenuyn = '" + GlobalMemberValues.getDBTextAfterChecked(timemenuyn, 0) + "', " +
                                " timemenutime = '" + GlobalMemberValues.getDBTextAfterChecked(timemenutime, 0) + "', " +
                                " modifiertype = '" + GlobalMemberValues.getDBTextAfterChecked(modifiertype, 0) + "', " +
                                " kitchenprintyn = '" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintyn, 0) + "', " +
                                " kitchenprintnum = '" + GlobalMemberValues.getDBTextAfterChecked(kitchenprintnum, 0) + "', " +
                                " weekdays = '" + GlobalMemberValues.getDBTextAfterChecked(weekdays, 0) + "', " +
                                " dcapplyyn = '" + GlobalMemberValues.getDBTextAfterChecked(dcapplyyn, 0) + "', " +
                                " servicename2 = '" + GlobalMemberValues.getDBTextAfterChecked(servicename2, 0) + "', " +
                                " servicename3 = '" + GlobalMemberValues.getDBTextAfterChecked(servicename3, 0) + "', " +
                                " servicenamealt = '" + GlobalMemberValues.getDBTextAfterChecked(servicenamealt, 0) + "', " +
                                " colorNo = '" + GlobalMemberValues.getDBTextAfterChecked(colorNo, 0) + "', " +
                                " taxtype = '" + GlobalMemberValues.getDBTextAfterChecked(taxtype, 0) + "', " +
                                " taxvalues = '" + GlobalMemberValues.getDBTextAfterChecked(taxvalues, 0) + "', " +
                                " taxexemptvalues = '" + GlobalMemberValues.getDBTextAfterChecked(taxexemptvalues, 0) + "', " +

                                " scaleuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(scaleuseyn, 0) + "', " +
                                " tareidx = '" + GlobalMemberValues.getDBTextAfterChecked(tareidx, 0) + "', " +
                                " tareweight = '" + GlobalMemberValues.getDBTextAfterChecked(tareweight, 0) + "', " +
                                " tareqty = '" + GlobalMemberValues.getDBTextAfterChecked(tareqty, 0) + "', " +
                                " taretotalweight = '" + GlobalMemberValues.getDBTextAfterChecked(taretotalweight, 0) + "', " +
                                " perweight = '" + GlobalMemberValues.getDBTextAfterChecked(perweight, 0) + "', " +

                                " dynamicpriceyn = '" + GlobalMemberValues.getDBTextAfterChecked(dynamicpriceyn, 0) + "', " +

                                " barcode = '" + GlobalMemberValues.getDBTextAfterChecked(barcode, 0) + "', " +

                                " divideryn = '" + GlobalMemberValues.getDBTextAfterChecked(divideryn, 0) + "', " +

                                " nameforlabel = '" + GlobalMemberValues.getDBTextAfterChecked(nameforlabel, 0) + "', " +

                                " imageusezone = '" + GlobalMemberValues.getDBTextAfterChecked(imageusezone, 0) + "', " +

                                " menuusezone = '" + GlobalMemberValues.getDBTextAfterChecked(menuusezone, 0) + "', " +

                                " mdforceyn = '" + GlobalMemberValues.getDBTextAfterChecked(mdforceyn, 0) + "', " +

                                " showynifzero_m = '" + GlobalMemberValues.getDBTextAfterChecked(showynifzero_m, 0) + "', " +
                                " showynifzero_k = '" + GlobalMemberValues.getDBTextAfterChecked(showynifzero_k, 0) + "', " +

                                " labelprintyn = '" + GlobalMemberValues.getDBTextAfterChecked(labelprintyn, 0) + "', " +
                                " labelprintnum = '" + GlobalMemberValues.getDBTextAfterChecked(labelprintnum, 0) + "', " +

                                " wmuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(wmuseyn, 0) + "' " +

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

                        GlobalMemberValues.logWrite("salon_storeservice_sub", "mQueryCollection : " + mQueryCollection);

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        midx = "";
                        servicename = "";
                        timefee = "";
                        description = "";
                        wdate = "";
                        mdate = "";
                        aid = "";
                        service_price = "";
                        pos_main_code = "";
                        pos_sub_code = "";
                        delyn = "";
                        subServiceTime = "";
                        positionNo = "";
                        commissionratioType = "";
                        commissionratio = "";
                        pointratio = "";
                        saleprice = "";
                        salestart = "";
                        saleend = "";
                        strFileName = "";
                        strFilePath = "";
                        strOrgFileName = "";
                        setmenuYN = "";
                        setmenuListArr = "";
                        activeyn = "";
                        taxfreeyn = "";
                        timemenuyn = "";
                        timemenutime = "";
                        modifiertype = "";
                        kitchenprintyn = "";
                        kitchenprintnum = "";
                        weekdays = "";
                        dcapplyyn = "";
                        servicename2 = "";
                        servicename3 = "";
                        servicenamealt = "";
                        colorNo = "";
                        taxtype = "";
                        taxvalues = "";
                        taxexemptvalues = "";

                        scaleuseyn = "";
                        tareidx = "";
                        tareweight = "";
                        tareqty = "";
                        taretotalweight = "";
                        perweight = "";

                        dynamicpriceyn = "";

                        barcode = "";

                        divideryn = "";

                        nameforlabel = "";

                        imageusezone = "";

                        menuusezone = "";

                        mdforceyn = "";

                        showynifzero_m = "";
                        showynifzero_k = "";

                        labelprintyn = "";
                        labelprintnum = "";

                        wmuseyn = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeservice_sub", "예외발생 : " + e.getMessage());
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
