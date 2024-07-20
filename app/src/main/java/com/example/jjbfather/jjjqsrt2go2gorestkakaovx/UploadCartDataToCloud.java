package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UploadCartDataToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    public UploadCartDataToCloud() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
        //receivedSalesCode = MainActivity.mSalesCode;

        GlobalMemberValues.logWrite("uploadcartdatalog", "생성자 진입" + "\n");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        //Thread mThread = new Thread(this);
        //mThread.start();

        GlobalMemberValues.logWrite("uploadcartdatalog", "onCreate..." + "\n");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            sendCartDataToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(MainActivity.context, "Warning", e.getMessage().toString(), "Close");
        }
    }


    public void sendCartDataToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            String apiParametersStr = "";
            String strUpdateQuery = "";
            Vector<String> apiVec = new Vector<String>();

            String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
            String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;
            String tempApi_STCODE = GlobalMemberValues.STATION_CODE;

            String datesql = " and wdate between dateadd(dd, 0, '" + GlobalMemberValues.STR_NOW_DATE + "') " +
                    " and dateadd(dd, 1, '" + GlobalMemberValues.STR_NOW_DATE + "') ";

            String strQueryString = " select idx, holdcode, svcName, svcOrgPrice, sPrice, sTax, sQty, sSaleYN, " +
                    " (sPrice - svcOrgPrice) as optionprice, sCommission, sPoint, optionTxt, " +
                    " midx, svcidx, tableidx, tordercode, wdate, " +

                    // 07112024
                    " sTaxAmount, selecteddcextraprice, selecteddcextratype, mergednum " +

                    " from temp_salecart " +
                    " where isCloudUpload = 0 " +

                    // 02242024 - 추가작업
                    // " and not(tableidx is null or tableidx = '') " +

                    " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                    " order by idx asc";
            GlobalMemberValues.logWrite("uploadcartdatalogjjjlllooo", "sql111jjj : " + strQueryString + "\n");

//            Cursor cartCursor = dbInitForUploadCloud.dbExecuteRead(strQueryString);
            ResultSet cartCursor = MssqlDatabase.getResultSetValue(strQueryString);
            try {
                while (cartCursor.next()) {
                    String posidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,0), 1);
                    String holdcodefrompos = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,1), 1);
                    String svcname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,2), 1);
                    String orgprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,3), 1);
                    String price = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,4), 1);
                    String tax = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,5), 1);
                    String qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,6), 1);
                    String saleyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,7), 1);
                    String optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,8), 1);
                    String commission = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,9), 1);
                    String point = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,10), 1);
                    String svcoption = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,11), 1);
                    String midx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,12), 1);
                    String svcidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,13), 1);
                    String tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,14), 1);
                    String tordercode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,15), 1);
                    String saledate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,16), 1);


                    // 07112024 -----------------------------------------
                    String taxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,17), 1);

                    String selecteddcextraprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,18), 1);
                    String selecteddcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,19), 1);

                    if (GlobalMemberValues.getDoubleAtString(selecteddcextraprice) > 0) {
                        if (GlobalMemberValues.isStrEmpty(selecteddcextratype)) {
                            selecteddcextratype = "DC";
                        }

                        if (selecteddcextratype.equals("DC")) {
                            selecteddcextraprice = "-" + selecteddcextraprice;
                        }
                    } else {
                        selecteddcextraprice = "0";
                        selecteddcextratype = "";
                    }

                    String mergednum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCursor,20), 1);
                    String tmergeyn = "N";
                    String tmergevalue = "";
                    if (GlobalMemberValues.getIntAtString(mergednum) > 0) {
                        tmergeyn = "Y";

                        String mergednumstr = "0" + mergednum;
                        tmergevalue = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                    } else {
                        tmergeyn = "N";
                        tmergevalue = "";
                    }
                    // 07112024 -----------------------------------------


                    // 02242024 - 추가작업 ---------------------------------------------------------------------
                    if (GlobalMemberValues.isStrEmpty(tableidx) && !GlobalMemberValues.isStrEmpty(holdcodefrompos)) {
                        tableidx = MssqlDatabase.getResultSetValueToString(
                                " select top 1 tableidx from temp_salecart where holdcode = '" + holdcodefrompos + "' order by idx asc "
                        );
                    }
                    // 02242024 - 추가작업 ---------------------------------------------------------------------

                    String tablename = "";
                    if (!GlobalMemberValues.isStrEmpty(tableidx)) {
                        tableidx = GlobalMemberValues.getReplaceText(tableidx, "T", "");
                        tablename = MssqlDatabase.getResultSetValueToString(
                                " select tablename from salon_store_restaurant_table where idx = '" + tableidx + "' ");
                    }

                    if (GlobalMemberValues.isStrEmpty(holdcodefrompos)) {
                        holdcodefrompos = "";
                    }
                    if (GlobalMemberValues.isStrEmpty(orgprice)) {
                        orgprice = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(price)) {
                        price = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(tax)) {
                        tax = "0";
                    }


                    // 07112024 ----------------------------------------
                    if (GlobalMemberValues.isStrEmpty(taxAmount)) {
                        taxAmount = "0";
                    }
                    if (GlobalMemberValues.getIntAtString(qty) > 1) {
                        double taxAmount_dbl = GlobalMemberValues.getDoubleAtString(taxAmount);
                        if (taxAmount_dbl > 0) {
                            double tax_dbl = taxAmount_dbl / GlobalMemberValues.getDoubleAtString(qty);
                            tax = GlobalMemberValues.getDoubleAtString(tax_dbl + "") + "";
                        }
                    } else {
                        tax = taxAmount;
                    }
                    // 07112024 ----------------------------------------


                    if (GlobalMemberValues.isStrEmpty(qty)) {
                        qty = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(saleyn)) {
                        saleyn = "N";
                    }
                    if (GlobalMemberValues.isStrEmpty(optionprice)) {
                        optionprice = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(commission)) {
                        commission = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(point)) {
                        point = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(svcoption)) {
                        svcoption = "";
                    }
                    if (GlobalMemberValues.isStrEmpty(midx)) {
                        midx = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(svcidx)) {
                        svcidx = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(tordercode)) {
                        tordercode = "";
                    }


                    apiParametersStr = "SCODE=" + tempApi_SCODE +
                            "&SIDX=" + tempApi_SIDX +
                            "&STCODE=" + tempApi_STCODE +
                            "&holdcodefrompos=" + holdcodefrompos +
                            "&midx=" + midx +
                            "&svcidx=" + svcidx +
                            "&svcname=" + GlobalMemberValues.getEncoderUtf8(svcname) +
                            "&svcoption=" + GlobalMemberValues.getEncoderUtf8(svcoption) +
                            "&qty=" + qty +
                            "&orgprice=" + orgprice +
                            "&price=" + price +
                            "&tax=" + tax +
                            "&saleyn=" + saleyn +
                            "&optionprice=" + optionprice +
                            "&commission=" + commission +
                            "&point=" + point +
                            "&posidx=" + posidx +
                            "&tablename=" + GlobalMemberValues.getEncoderUtf8(tablename) +
                            "&tableidx=" + tableidx +
                            "&tordercode=" + tordercode +
                            "&saledate=" + saledate +
                            // 07112024
                            "&selecteddcextraprice=" + selecteddcextraprice +
                            "&selecteddcextratype=" + selecteddcextratype +
                            "&tmergeyn=" + tmergeyn +
                            "&tmergevalue=" + tmergevalue;

                            strUpdateQuery = "update temp_salecart set isCloudUpload = 1 " +
                            " where idx = '" + posidx + "' ";
                    apiVec.addElement(apiParametersStr + "|||" + strUpdateQuery);
                    GlobalMemberValues.logWrite("uploadcartdataloglllllogjjj", "data string : " + apiParametersStr + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //07052024 close resultset
            try {
                if(!cartCursor.isClosed()){
                    cartCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



            if (apiVec.size() > 0) {
                Vector<String > apiUpdateQueryVec = new Vector<String>();
                for (String params : apiVec) {
                    GlobalMemberValues.logWrite("uploadcartdatalog", "params : " + params + "\n");
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        String[] tempParams = params.split(GlobalMemberValues.STRSPLITTER2);
                        // API 로 넘기는 문자열에는 공백이 있으면 안되므로
                        // 공백부분을 ||| 으로 처리하고
                        // 웹 API 처리하는 부분에서 ||| 을 공백으로 치환한다.
                        String paramStr = GlobalMemberValues.getReplaceText(tempParams[0], " ", "|||");
                        String returnValue = sendCartDataByApi(paramStr);
                        GlobalMemberValues.logWrite("uploadcartdatalog", "returnValue : " + returnValue + "\n");
                        if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                            apiUpdateQueryVec.addElement(tempParams[1]);
                        }
                    }
                }

                for (String strQuery : apiUpdateQueryVec) {
                    GlobalMemberValues.logWrite("uploadcartdatalog1111111", "query : " + strQuery + "\n");
                }
                String returnResult = "";
                // 트랜잭션으로 DB 처리한다.
//                returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                returnResult = MssqlDatabase.executeTransaction(apiUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {
                } else { // 정상처리일 경우 Sale History 리스트 리로드
                    // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                    // 서비스 중지 전 3초간 기다린다.
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 07192024
//                    if (GlobalMemberValues.isTOrderUse()){
//                        GlobalMemberValues.sendTOrderAPIOrderData("K");
//                    }
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CART != null && GlobalMemberValues.CURRENTSERVICEINTENT_CART != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CART.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_CART);
    }


    public String sendCartDataByApi(String strParams) {
        GlobalMemberValues.logWrite("uploadcartdatalog", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                API_cartdataupload_tocloud apiSalesDataInstance = new API_cartdataupload_tocloud(strParams);
                apiSalesDataInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiSalesDataInstance.mFlag) {
                        returnValue = apiSalesDataInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        return returnValue;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
