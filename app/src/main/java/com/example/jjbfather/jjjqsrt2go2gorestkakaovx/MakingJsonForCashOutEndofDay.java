package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;

public class MakingJsonForCashOutEndofDay {

    public static String getDataOnSql(boolean isEndofday, String paramQuery) {
        String returnValue = "";

        if (isEndofday) {
            paramQuery = GlobalMemberValues.getReplaceText(paramQuery, "substr(", "substring(");
            returnValue = MssqlDatabase.getResultSetValueToString(paramQuery);
        } else {
            returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(paramQuery);
        }
        return returnValue;
    }

    public static JSONObject getMakingJson(String paramEndofdayYN, int paramEndOfNum, String paramEmployeeIdx) throws JSONException {
        if (GlobalMemberValues.isStrEmpty(paramEndofdayYN)) {
            paramEndofdayYN = "N";
        }

        boolean isEndofday = false;
        if (paramEndofdayYN == "Y" || paramEndofdayYN.equals("Y")) {
            isEndofday = true;
        }

        String empSqlQuery = " idx is not null ";
        String addSqlQuery = "";
        if (isEndofday) {
            addSqlQuery = " and cashoutNum > 0 and endofdayNum = '" + paramEndOfNum + "' ";
        } else {
            //empSqlQuery += " and cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' ";
            if (!GlobalMemberValues.isStrEmpty(paramEmployeeIdx)) {
                empSqlQuery += " and cashoutNum = 0 and employeeIdx = '" + paramEmployeeIdx + "' ";
            } else {
                empSqlQuery += " and cashoutNum = 0 ";
            }
        }

        // 최상단 json객체
        JSONObject jsonroot = new JSONObject();
        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList_loginout = new JSONArray();
        JSONArray jsonList_cashin = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp_loginout = null;
        JSONObject jsontmp_cashin = null;

        String sqlQuery =  "";

        DatabaseInit dbInit = MainActivity.mDbInit;

        String subStrString = "substr(salesCode, 2)";
        String subStrString2 = "substr(salesCode, 1, 1)";
        if (isEndofday) {
            subStrString = "substring(salescode, 2, len(salescode))";
            subStrString2 = "substring(salesCode, 1, 1)";
        }

        // 직원 로그인/아웃 로그기록 ------------------------------------------------------------------------------------------
        sqlQuery = "select loginouttype, loginoutdatetime " +
                " from salon_employee_loginout_history " +
                " where " +
                empSqlQuery +
                " and cashoutNum = 0 " +
                " order by idx asc ";
        GlobalMemberValues.logWrite("employeeloginlog", "query : " + sqlQuery + "\n");
        if (isEndofday) {
            ResultSet empLoginCursor = MssqlDatabase.getResultSetValue(sqlQuery);
            try {
                while (empLoginCursor.next()){
                    String tempLoginouttype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,0), 1);
                    String tempLoginoutdatetime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,1), 1);

                    String tempLoginouttypeStr = "Login";
                    if (GlobalMemberValues.getIntAtString(tempLoginouttype) == 0) {
                        tempLoginouttypeStr = "Login";
                    } else {
                        tempLoginouttypeStr = "Logout";
                    }

                    jsontmp_loginout = new JSONObject();
                    jsontmp_loginout.put("loginouttype", tempLoginouttypeStr);
                    jsontmp_loginout.put("loginoutdatetime", tempLoginoutdatetime);

                    jsonList_loginout.put(jsontmp_loginout);

                }
                //07052024 close resultset
                empLoginCursor.close();
            } catch (Exception e) {
            }

        } else {
            Cursor empLoginCursor;
            empLoginCursor = dbInit.dbExecuteRead(sqlQuery);
            while (empLoginCursor.moveToNext()) {
                String tempLoginouttype = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(0), 1);
                String tempLoginoutdatetime = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(1), 1);

                String tempLoginouttypeStr = "Login";
                if (GlobalMemberValues.getIntAtString(tempLoginouttype) == 0) {
                    tempLoginouttypeStr = "Login";
                } else {
                    tempLoginouttypeStr = "Logout";
                }

                jsontmp_loginout = new JSONObject();
                jsontmp_loginout.put("loginouttype", tempLoginouttypeStr);
                jsontmp_loginout.put("loginoutdatetime", tempLoginoutdatetime);

                jsonList_loginout.put(jsontmp_loginout);
            }
        }


        // Employee 별 Sales 리스트를 Json 객체에 담는다.
        jsonroot.put("employeeloginoutlog", jsonList_loginout);
        // ------------------------------------------------------------------------------------------------------------------

        // 직원 Cash In 기록 -------------------------------------------------------------------------------------------------
        sqlQuery = "select inoutmoney, inoutreason " +
                " from salon_sales_cashintout_history " +
                " where " +
                empSqlQuery +
                " and cashoutNum = 0 " +
                " order by idx asc ";
        GlobalMemberValues.logWrite("employeecashinlog", "query : " + sqlQuery + "\n");

        if (isEndofday) {
            ResultSet empCashInCursor = MssqlDatabase.getResultSetValue(sqlQuery);
            try {
                while (empCashInCursor.next()){
                    double tempCashinoutmoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empCashInCursor,0), 1));
                    String tempCashinoutreason = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empCashInCursor,1), 1);

                    jsontmp_cashin = new JSONObject();
                    jsontmp_cashin.put("cashinmoney", GlobalMemberValues.getCommaStringForDouble(tempCashinoutmoney + ""));
                    jsontmp_cashin.put("cashinreason", tempCashinoutreason);

                    jsonList_cashin.put(jsontmp_cashin);
                }
                //07052024 close resultset
                empCashInCursor.close();
            } catch (Exception e) {
            }
        } else {
            Cursor empCashInCursor;
            empCashInCursor = dbInit.dbExecuteRead(sqlQuery);
            while (empCashInCursor.moveToNext()) {
                double tempCashinoutmoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(0), 1));
                String tempCashinoutreason = GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(1), 1);

                jsontmp_cashin = new JSONObject();
                jsontmp_cashin.put("cashinmoney", GlobalMemberValues.getCommaStringForDouble(tempCashinoutmoney + ""));
                jsontmp_cashin.put("cashinreason", tempCashinoutreason);

                jsonList_cashin.put(jsontmp_cashin);
            }
        }

        // Employee 별 Sales 리스트를 Json 객체에 담는다.
        jsonroot.put("employeecashinlog", jsonList_cashin);
        // ------------------------------------------------------------------------------------------------------------------

        jsonroot.put("endofdayyn", paramEndofdayYN);

        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        String strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = dbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }

        jsonroot.put("storename", storeNameForReceipt);  // JSON
        jsonroot.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot.put("storecity", storeCityForReceipt);  // JSON
        jsonroot.put("storestate", storeStateForReceipt);  // JSON
        jsonroot.put("storezip", storeZipForReceipt);  // JSON
        jsonroot.put("storephone", storePhoneForReceipt);  // JSON
        /******************************************************************/

        /** SALES OVERVIEW 시작 ********************************************************************************************************************/
        // All Tickets --------------------------------------------------------------------------------------------------
        sqlQuery = "select count(distinct customerordernumber) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        String salesoverview_alltickets = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesoverview_alltickets", salesoverview_alltickets);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Employee name
        String salesoverview_employeename = getDataOnSql(isEndofday, " select name from salon_storeemployee where idx = '" + paramEmployeeIdx + "' ");
        jsonroot.put("salesoverview_employeename", salesoverview_employeename);

        // Cash In Date -------------------------------------------------------------------------------------------------
        if (isEndofday) {
            sqlQuery = " select top 1 wdate from salon_sales_cashintout_history " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and inoutreason like '%Starting cash%' " +
                    " order by idx asc ";
        } else {
            sqlQuery = " select wdate from salon_sales_cashintout_history " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and inoutreason like '%Starting cash%' " +
                    " order by idx asc limit 1 ";
        }

        String salesoverview_cashindate = getDataOnSql(isEndofday, sqlQuery);
        jsonroot.put("salesoverview_cashindate", salesoverview_cashindate);  // JSON
        // ------------------------------------------------------------------------------------------------------------------

        // Cash Out Date -------------------------------------------------------------------------------------------------
        if (isEndofday) {
            sqlQuery = " select top 1 wdate from salon_sales_cashintout_history " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and not(inoutreason like '%Starting cash%') " +
                    " order by idx desc ";
        } else {
            sqlQuery = " select wdate from salon_sales_cashintout_history " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and not(inoutreason like '%Starting cash%') " +
                    " order by idx desc limit 1 ";
        }

        String salesoverview_cashoutdate = getDataOnSql(isEndofday, sqlQuery);
        jsonroot.put("salesoverview_cashoutdate", salesoverview_cashoutdate);  // JSON
        // ------------------------------------------------------------------------------------------------------------------

        /** SALES OVERVIEW 끝 ********************************************************************************************************************/

        String commonGratuitySql = " and not(itemName = '" + GlobalMemberValues.mCommonGratuityName + "') ";

        // 09012022
        /** 멀티 스테이션 관련 시작 ********************************************************************************************************************/
        String[] stcode_arr = new String[0];
        String[] station_name_arr = new String[0];;

        double[] salessummary_sales_arr = new double[0];
        double[] salessummary_discount_arr = new double[0];
        double[] salessummary_refund_arr = new double[0];
        double[] salessummary_netsales_arr = new double[0];
        double[] taxAmount_arr = new double[0];
        double[] salessummary_subtotal_arr = new double[0];

        double[] salessummary_extra_arr = new double[0];

        double[] salessummary_totaltips_arr = new double[0];

        // 01062023
        double[] salessummary_totaltips_cash_arr = new double[0];
        double[] salessummary_totaltips_card_arr = new double[0];

        double[] salessummary_pickupfee_arr = new double[0];
        double[] salessummary_deliveryfee_arr = new double[0];
        double[] salessummary_gratuity_arr = new double[0];

        double[] salessummary_total_arr = new double[0];

        int stationCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select count(*) from salon_storestationinfo where sidx = '" + GlobalMemberValues.STORE_INDEX + "' and delyn = 'N' and useYN = 'Y' "));

        if (stationCnt > 1) {
            stcode_arr = new String[stationCnt];
            station_name_arr = new String[stationCnt];

            String getQuery = " select stcode, stname from salon_storestationinfo where sidx = '" + GlobalMemberValues.STORE_INDEX + "' and delyn = 'N' and useYN = 'Y' ";
            Cursor tempSaleCartCursor = dbInit.dbExecuteRead(getQuery);
            int tempArrIdx = 0;
            while (tempSaleCartCursor.moveToNext()) {
                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String temp_stcode = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(0), 1);
                String temp_stname = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(0), 1);

                if (tempArrIdx <= stationCnt) {
                    stcode_arr[tempArrIdx] = temp_stcode;
                    station_name_arr[tempArrIdx] = temp_stname;
                }

                tempArrIdx++;
            }

            salessummary_sales_arr = new double[stationCnt];
            salessummary_discount_arr = new double[stationCnt];
            salessummary_refund_arr = new double[stationCnt];
            salessummary_netsales_arr = new double[stationCnt];
            taxAmount_arr = new double[stationCnt];
            salessummary_subtotal_arr = new double[stationCnt];

            salessummary_extra_arr = new double[stationCnt];

            salessummary_totaltips_arr = new double[stationCnt];

            // 01062023
            salessummary_totaltips_cash_arr = new double[stationCnt];
            salessummary_totaltips_card_arr = new double[stationCnt];

            salessummary_pickupfee_arr = new double[stationCnt];
            salessummary_deliveryfee_arr = new double[stationCnt];
            salessummary_gratuity_arr = new double[stationCnt];

            salessummary_total_arr = new double[stationCnt];
        }


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and not(saveType = '2') " +
                        " and delyn = 'N' " +
                        " and (" + subStrString2 + " = 'K' or " + subStrString2 + " = 'V') " + commonGratuitySql;
                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_sales_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData);
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(totalExtraPrice, 2)) from salon_sales " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and delyn = 'N' " +
                        " and " + subStrString2 + " = 'K' ";
                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_extra_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData);

                salessummary_sales_arr[i] += salessummary_extra_arr[i];
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        //08212024 add check for orders that were returned: salescode that start with 'C'
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and not(saveType = '2') " +
                        " and delyn = 'N' " +
                        " and (" + subStrString2 + " = 'K' or " + subStrString2 + " = 'V' or " + subStrString2 + " = 'C') " +
                        " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_gratuity_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {

                sqlQuery = "select sum(round(dcextraforreturn, 2)) from salon_sales_detail " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and delyn = 'N' " +
                        " and " + subStrString + " not in " +
                        // 06122023
                        // commonGratuitySql 부분을 제거
                        //" (select " + subStrString + " from salon_sales_detail where " + subStrString2 + " = 'V') " + commonGratuitySql;
                        " (select " + subStrString + " from salon_sales_detail where " + subStrString2 + " = 'V') ";
                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_discount_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and not(saveType = '2') " +
                        " and delyn = 'N' " +
                        " and " + subStrString2 + " = 'C' " + commonGratuitySql;
                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_refund_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(taxAmount, 2)) from salon_sales_detail " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and not(saveType = '2') " +
                        " and delyn = 'N' " + commonGratuitySql;

                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                taxAmount_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------

        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                salessummary_netsales_arr[i] = salessummary_sales_arr[i] + salessummary_discount_arr[i] + salessummary_refund_arr[i];
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                salessummary_subtotal_arr[i] = salessummary_netsales_arr[i] + taxAmount_arr[i];
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                // tip total
                sqlQuery = " select sum(round((usedCash + usedCard), 2)) from salon_sales_tip " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' and stcode = '" + stcode_arr[i] + "' ) " +
                        " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where " + subStrString2 + " = 'V') ";

                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_totaltips_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");

                // 01062023 -------------------------------------------------------------------------------------------------
                // cash tip
                sqlQuery = " select sum(round(usedCash, 2)) from salon_sales_tip " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' and stcode = '" + stcode_arr[i] + "' ) " +
                        " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where " + subStrString2 + " = 'V') ";

                String getTempData2 = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_totaltips_cash_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData2 + "");

                // card tip
                sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' and stcode = '" + stcode_arr[i] + "' ) " +
                        " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where " + subStrString2 + " = 'V') ";

                String getTempData3 = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_totaltips_card_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData3 + "");
                // ----------------------------------------------------------------------------------------------------------

            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and delyn = 'N' " +
                        " and status = 0 " +
                        " and deliverytakeaway = 'T' ";

                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_pickupfee_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        if (stationCnt > 1) {
            for (int i = 0; i < stationCnt; i++) {
                sqlQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                        " where " + empSqlQuery + addSqlQuery +
                        " and stcode = '" + stcode_arr[i] + "' " +
                        " and delyn = 'N' " +
                        " and status = 0 " +
                        " and deliverytakeaway = 'D' ";

                String getTempData = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                salessummary_deliveryfee_arr[i] = GlobalMemberValues.getDoubleAtString(getTempData + "");
            }
        }
        // --------------------------------------------------------------------------------------------


        // 09012022
        // 멀티 스테이션일 경우 --------------------------------------------------------------------------
        String salessummary_total_for_multistation = "";
        if (stationCnt > 1) {
            String temp_multistation_total = "";
            for (int i = 0; i < stationCnt; i++) {
                salessummary_total_arr[i] = salessummary_subtotal_arr[i]
                        + salessummary_totaltips_arr[i]
                        + salessummary_pickupfee_arr[i]
                        + salessummary_deliveryfee_arr[i]
                        + salessummary_gratuity_arr[i];

                temp_multistation_total = station_name_arr[i] + "-JJJ-" + GlobalMemberValues.getCommaStringForDouble(salessummary_total_arr[i] + "");

                if (i == 0) {
                    salessummary_total_for_multistation += temp_multistation_total;
                } else {
                    salessummary_total_for_multistation += "-WANHAYE-" + temp_multistation_total;
                }
            }
        }

        jsonroot.put("salessummary_total_formulti", salessummary_total_for_multistation);  // JSON


        // --------------------------------------------------------------------------------------------
        /** 멀티 스테이션 관련 끝 **********************************************************************************************************************/


        // 11072023 - 위치 변경
        // Common Gratuity  -------------------------------------------------------------------------------------------------
        //08212024 also check for salescode for return: salescode that start with 'C'
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V' or substr(salesCode, 1, 1) = 'C')" +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
        String salessummary_gratuity = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        salessummary_gratuity = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity + "");
        jsonroot.put("salessummary_commongratuity", salessummary_gratuity);  // JSON

        // 10302023 ------------------------------------------------------------------------------------------------
        // cash gratuity
        String sqlQuery2 = "select salescode from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and useTotalCashAmountAfterReturned > 0";
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and salescode in (" + sqlQuery2 + ") ";
        String salessummary_gratuity_cash = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        salessummary_gratuity_cash = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity_cash + "");
        jsonroot.put("salessummary_commongratuity_cash", salessummary_gratuity_cash);  // JSON

        // card gratuity
        //08202024 use holdcode instead of salescode to avoid error due to void salescode starting with "V" instead
        //of "K" leading to voided/returned gratuity from not being accounted for.
//        sqlQuery2 = "select salescode from salon_sales " +
//                " where " + empSqlQuery + addSqlQuery +
//                " and useTotalCardAmountAfterReturned > 0";
        sqlQuery2 = "select holdcode from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and useTotalCardAmountAfterReturned > 0";
        String sqlQuery3 = "select sum(sidx) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and salescode in (" + sqlQuery2 + ") ";
//        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
//                " where " + empSqlQuery + addSqlQuery +
//                " and not(saveType = '2') " +
//                " and delyn = 'N' " +
//                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
//                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
//                " and salescode in (" + sqlQuery2 + ") ";
        sqlQuery = "select sum(salesPriceAmount) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and holdcode in (" + sqlQuery2 + ") ";

        String salessummary_gratuity_card = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        String test = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery3));
        GlobalMemberValues.logWrite("ZAPPA", test);
        salessummary_gratuity_card = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity_card + "");
        jsonroot.put("salessummary_commongratuity_card", salessummary_gratuity_card);  // JSON

        // gift card gratuity
        sqlQuery2 = "select salescode from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and useTotalGiftCardAmountAfterReturned > 0";
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and salescode in (" + sqlQuery2 + ") ";
        String salessummary_gratuity_giftcard = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        salessummary_gratuity_giftcard = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity_giftcard + "");
        jsonroot.put("salessummary_commongratuity_giftcard", salessummary_gratuity_giftcard);  // JSON

        // point gratuity
        sqlQuery2 = "select salescode from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and useTotalPointAmountAfterReturned > 0";
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and salescode in (" + sqlQuery2 + ") ";
        String salessummary_gratuity_point = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        salessummary_gratuity_point = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity_point + "");
        jsonroot.put("salessummary_commongratuity_point", salessummary_gratuity_point);  // JSON

        // other gratuity
        sqlQuery2 = "select salescode from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and useTotalCheckAmountAfterReturned > 0";
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "' " +
                " and salescode in (" + sqlQuery2 + ") ";
        String salessummary_gratuity_other = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        salessummary_gratuity_other = GlobalMemberValues.getCommaStringForDouble(salessummary_gratuity_other + "");
        jsonroot.put("salessummary_commongratuity_other", salessummary_gratuity_other);  // JSON
        // 10302023 ------------------------------------------------------------------------------------------------
        // Common Gratuity  -------------------------------------------------------------------------------------------------



        /** Sales Summary 시작 ********************************************************************************************************************/
        // Sales Summary  -------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'K' or substr(salesCode, 1, 1) = 'V') " + commonGratuitySql;
        String salessummary_sales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        double salessummary_sales_dbl = GlobalMemberValues.getDoubleAtString(salessummary_sales);
        GlobalMemberValues.logWrite("discountextrasumlog", "salessummary_sales_dbl_1 : " + salessummary_sales_dbl + "\n");
        GlobalMemberValues.logWrite("discountextrasumlog", "sqlQuery : " + sqlQuery + "\n");





        // Extra Total
        sqlQuery = "select sum(round(totalExtraPrice, 2)) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'K' ";
        String salessummary_extra = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        double salessummary_extra_dbl = GlobalMemberValues.getDoubleAtString(salessummary_extra);
        //jsonroot.put("salessummary_extra", salessummary_extra);  // JSON

        salessummary_sales_dbl += salessummary_extra_dbl;

        // 11072023
        // 11232023
        // salessummary_sales_dbl -= GlobalMemberValues.getDoubleAtString(salessummary_gratuity);

        GlobalMemberValues.logWrite("discountextrasumlog", "salessummary_sales_dbl_2 : " + salessummary_sales_dbl + "\n");

        salessummary_sales = GlobalMemberValues.getCommaStringForDouble(salessummary_sales_dbl + "");

        jsonroot.put("salessummary_sales", salessummary_sales);  // JSON



        // -------------------------------------------------------------------------------------------------------------------



        // Discount Total  -------------------------------------------------------------------------------------------------
//        sqlQuery = "select sum(round(totalDiscountPrice, 2)) from salon_sales " +
//                " where " + empSqlQuery + addSqlQuery +
//                " and delyn = 'N' " +
//                " and " + subStrString + " not in " +
//                " (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V')";
        sqlQuery = "select sum(round(dcextraforreturn, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and " + subStrString + " not in " +
                // 06122023
                // commonGratuitySql 부분을 제거
                //" (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') " + commonGratuitySql;
                " (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
        double salessummary_discount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery)) * -1;
        String salessummary_discount = GlobalMemberValues.getCommaStringForDouble(salessummary_discount_dbl + "");
        jsonroot.put("salessummary_discount", salessummary_discount);  // JSON

        GlobalMemberValues.logWrite("cashoutlogjjjfordc", "sql " + sqlQuery + "\n");
        // -------------------------------------------------------------------------------------------------------------------



        // Refund (Return) ---------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'C' " + commonGratuitySql;
        String salessummary_refund = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salessummary_refund", salessummary_refund);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // 자기 주문이 아닌 주문을 리턴한 내역 ------------------------------------------------------------------------------------
        double salessummary_othersales_cash_dbl = 0;
        double salessummary_othersales_card_dbl = 0;
        double salessummary_othersales_gc_dbl = 0;
        double salessummary_othersales_check_dbl = 0;
        double salessummary_othersales_point_dbl = 0;

        if (paramEndofdayYN == "N" || paramEndofdayYN.equals("N")) {
            String addQuery = "select salesCode from salon_sales_detail " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and not(saveType = '2') " +
                    " and delyn = 'N' " +
                    " and substr(salesCode, 1, 1) = 'C' " + commonGratuitySql;

            // CASH
            sqlQuery = " select sum(realreturnprice_cash) from salon_sales_return_byemplyee " +
                    " where salescode in (" + addQuery + ") ";
            salessummary_othersales_cash_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

            // CARD
            sqlQuery = " select sum(realreturnprice_card) from salon_sales_return_byemplyee " +
                    " where salescode in (" + addQuery + ") ";
            salessummary_othersales_card_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

            // GIFT CARD
            sqlQuery = " select sum(realreturnprice_gc) from salon_sales_return_byemplyee " +
                    " where salescode in (" + addQuery + ") ";
            salessummary_othersales_gc_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

            // CHECK
            sqlQuery = " select sum(realreturnprice_check) from salon_sales_return_byemplyee " +
                    " where salescode in (" + addQuery + ") ";
            salessummary_othersales_check_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

            // POINT
            sqlQuery = " select sum(realreturnprice_point) from salon_sales_return_byemplyee " +
                    " where salescode in (" + addQuery + ") ";
            salessummary_othersales_point_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
        }
        // -------------------------------------------------------------------------------------------------------------------


        // net sales ---------------------------------------------------------------------------------------------------
        /**
         double salessummary_netsales_dbl =
         GlobalMemberValues.getDoubleAtString(salessummary_sales)
         + GlobalMemberValues.getDoubleAtString(salessummary_extra)
         + GlobalMemberValues.getDoubleAtString(salessummary_discount)
         + GlobalMemberValues.getDoubleAtString(salessummary_refund);
         **/

        double salessummary_netsales_dbl =
                GlobalMemberValues.getDoubleAtString(salessummary_sales)
                        + GlobalMemberValues.getDoubleAtString(salessummary_discount)
                        + GlobalMemberValues.getDoubleAtString(salessummary_refund);

        String salessummary_netsales = GlobalMemberValues.getCommaStringForDouble(salessummary_netsales_dbl + "");

        jsonroot.put("salessummary_netsales", salessummary_netsales);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // taxes & fess ---------------------------------------------------------------------------------------------------
        // Taxes + CRV
        sqlQuery = "select sum(round(taxAmount, 2)) from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and not(saveType = '2') " +
                " and delyn = 'N' " + commonGratuitySql;
        String taxAmount = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salessummary_taxesandfees", taxAmount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------


        // sub total ---------------------------------------------------------------------------------------------------
        double salessummary_subtotal_dbl = salessummary_netsales_dbl
                + GlobalMemberValues.getDoubleAtString(taxAmount) +
                // 04242023
                + GlobalMemberValues.getDoubleAtString(salessummary_gratuity);

        String salessummary_subtotal = GlobalMemberValues.getCommaStringForDouble(salessummary_subtotal_dbl + "");

        jsonroot.put("salessummary_subtotal", salessummary_subtotal);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Employee 팁 ------------------------------------------------------------------------------------------------------
        sqlQuery = " select sum(round((usedCash + usedCard), 2)) from salon_sales_tip " +
                " where " + empSqlQuery + addSqlQuery +
                " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("tipAmountLog", "tipQuery : " + sqlQuery + "\n");

        double salessummary_totaltips_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
        String salessummary_totaltips = GlobalMemberValues.getStringFormatNumber(salessummary_totaltips_dbl, "2");
        if (GlobalMemberValues.isStrEmpty(salessummary_totaltips)) {
            salessummary_totaltips = " - ";
        }
        jsonroot.put("salessummary_totaltips", GlobalMemberValues.getCommaStringForDouble(salessummary_totaltips));  // JSON

        // ------------------------------------------------------------------------------------------------------------------


        // PickupFee --------------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and deliverytakeaway = 'T' ";
        String salessummary_pickupfee = getDataOnSql(isEndofday, sqlQuery);

        // 05302024 ------------------------------------
        // 오늘날짜가 아닌 다른 날짜를 return 처리한 deliverypickupfee 금액을 가져온다.
        sqlQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales_togodeliveryfee " +
                " where " + empSqlQuery + addSqlQuery;
        String diffdate_deliverypickupfee = getDataOnSql(isEndofday, sqlQuery);
        double pickupfee_dbl = GlobalMemberValues.getDoubleAtString(salessummary_pickupfee) + GlobalMemberValues.getDoubleAtString(diffdate_deliverypickupfee);
        salessummary_pickupfee = pickupfee_dbl + "";
        // 05302024 ------------------------------------

        jsonroot.put("salessummary_pickupfee",
                GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.getDoubleAtString(salessummary_pickupfee) + ""));  // JSON
        // ------------------------------------------------------------------------------------------------------------------


        // Delivery ---------------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and deliverytakeaway = 'D' ";
        String salessummary_deliveryfee = getDataOnSql(isEndofday, sqlQuery);
        jsonroot.put("salessummary_deliveryfee",
                GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.getDoubleAtString(salessummary_deliveryfee) + ""));  // JSON
        // ------------------------------------------------------------------------------------------------------------------

        // total ---------------------------------------------------------------------------------------------------
        // total 구하는 부분 09012022
        double salessummary_total_dbl = salessummary_subtotal_dbl
                + salessummary_totaltips_dbl
                + GlobalMemberValues.getDoubleAtString(salessummary_pickupfee)
                + GlobalMemberValues.getDoubleAtString(salessummary_deliveryfee);
        // 04242023
        // 아래 내용 주석처리
        //+ GlobalMemberValues.getDoubleAtString(salessummary_gratuity);

        String salessummary_total = GlobalMemberValues.getCommaStringForDouble(salessummary_total_dbl + "");

        jsonroot.put("salessummary_total", salessummary_total);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        if (isEndofday) {        // End of Day
            // End Cash ----------------------------------------------------------------------------------------------------------
            String tempJsonStr = "";
            JSONObject tempJsonObject = null;
            double tempOverShortCash = 0;
            double tempTotalOverShortCash = 0;
            sqlQuery = "select jsonstr from salon_sales_cashout_json " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' ";
            GlobalMemberValues.logWrite("jjjtemplog", "sqlQuery : " + sqlQuery + "\n");

            ResultSet dataCursor = MssqlDatabase.getResultSetValue(sqlQuery);
            try {
                while (dataCursor.next()){
                    tempJsonStr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(dataCursor,0), 1);
                    if (!GlobalMemberValues.isStrEmpty(tempJsonStr)) {
                        GlobalMemberValues.logWrite("jjjtemplog", "tempJsonStr : " + tempJsonStr + "\n");
                        tempJsonObject = new JSONObject(tempJsonStr);
                        tempOverShortCash = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDataInJsonData(tempJsonObject, "salessummary_overshortcash"));
                        GlobalMemberValues.logWrite("jjjtemplog", "tempOverShortCash : " + tempOverShortCash + "\n");
                        tempTotalOverShortCash += tempOverShortCash;
                    }
                }
                //07052024 close resultset
                dataCursor.close();
            } catch (Exception e) {
            }

            jsonroot.put("salessummary_overshortcash", GlobalMemberValues.getCommaStringForDouble(tempTotalOverShortCash + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
        } else {                                                            // Cash In / Out
            // End Cash ----------------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and inoutreason = 'Ending cash' ";
            String endCashTotalAmount = getDataOnSql(isEndofday, sqlQuery);
            endCashTotalAmount = GlobalMemberValues.getCommaStringForDouble(endCashTotalAmount);
            // -------------------------------------------------------------------------------------------------------------------

            // Over Short Cash ---------------------------------------------------------------------------------------------------
            if (CashInOutPopup.cashInOutAllCashTotalTv != null) {
                String tempAllCash = CashInOutPopup.cashInOutAllCashTotalTv.getText().toString();

                double allCashTotalAmountDbl = GlobalMemberValues.getDoubleAtString(tempAllCash);
                double endCashTotalAmountDbl = GlobalMemberValues.getDoubleAtString(endCashTotalAmount);

                double overShortCash = endCashTotalAmountDbl - allCashTotalAmountDbl;

                jsonroot.put("salessummary_overshortcash", GlobalMemberValues.getCommaStringForDouble(overShortCash + ""));  // JSON
            }
            // -------------------------------------------------------------------------------------------------------------------
        }


        /** Sales Summary 끝 ********************************************************************************************************************/



        /** Sales by Tender Types 시작 **********************************************************************************************************/
        // Sales Cash Count --------------------------------------------------------------------------------------------------
        sqlQuery = "select count(idx) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and useTotalCashAmountAfterReturned > 0";
        String salesbytendertypes_cashtransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesbytendertypes_cashtransactions", salesbytendertypes_cashtransactions);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Cash Tip --------------------------------------------------------------------------------------------------
        sqlQuery = " select sum(round(usedCash, 2)) from salon_sales_tip " +
                " where " + empSqlQuery + addSqlQuery +
                " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
        double salesbytendertypes_cashtipamount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

        // 01062023
        // Cash Tip
        jsonroot.put("salessummary_totaltips_cash", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cashtipamount_dbl + ""));  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Cash total --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) - " + salessummary_othersales_cash_dbl + " from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        double salesbytendertypes_cashtotalamount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

        salesbytendertypes_cashtotalamount_dbl += salesbytendertypes_cashtipamount_dbl;

        // 11072023
        salesbytendertypes_cashtotalamount_dbl -= GlobalMemberValues.getDoubleAtString(salessummary_gratuity_cash);

        String salesbytendertypes_cashtotalamount = GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cashtotalamount_dbl + "");

        jsonroot.put("salesbytendertypes_cashtotalamount", salesbytendertypes_cashtotalamount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Sales Card Count --------------------------------------------------------------------------------------------------
//        sqlQuery = "select count(idx) from salon_sales " +
//                " where " + empSqlQuery + addSqlQuery +
//                " and delyn = 'N' " +
//                " and status = 0 " +
//                " and useTotalCardAmountAfterReturned > 0";
        sqlQuery = "select count(idx) from salon_sales_card " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'K' " +
                " and priceAmount > 0 " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";

        String salesbytendertypes_creditcardtransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesbytendertypes_creditcardtransactions", salesbytendertypes_creditcardtransactions);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Card Tip --------------------------------------------------------------------------------------------------
        sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                " where " + empSqlQuery + addSqlQuery +
                " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
        double salesbytendertypes_cardtipamount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

        // 01062023
        // Cash Tip
        jsonroot.put("salessummary_totaltips_card", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_dbl + ""));  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Card total --------------------------------------------------------------------------------------------------
//        sqlQuery = "select sum(round(useTotalCardAmountAfterReturned, 2)) - " + salessummary_othersales_card_dbl + " from salon_sales " +
//                " where " + empSqlQuery + addSqlQuery +
//                " and delyn = 'N' " +
//                " and status = 0 ";

        sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";

        double salesbytendertypes_creditcardtotalamount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));

        salesbytendertypes_creditcardtotalamount_dbl += salesbytendertypes_cardtipamount_dbl;

        // 11072023
        salesbytendertypes_creditcardtotalamount_dbl -= GlobalMemberValues.getDoubleAtString(salessummary_gratuity_card);

        String salesbytendertypes_creditcardtotalamount = GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_creditcardtotalamount_dbl + "");

        jsonroot.put("salesbytendertypes_creditcardtotalamount", salesbytendertypes_creditcardtotalamount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Sales Gift Card Count --------------------------------------------------------------------------------------------------
        sqlQuery = "select count(idx) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and useTotalGiftCardAmountAfterReturned > 0";
        String salesbytendertypes_giftcardtransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesbytendertypes_giftcardtransactions", salesbytendertypes_giftcardtransactions);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Gift Card total --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) - " + salessummary_othersales_gc_dbl + " from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        String salesbytendertypes_giftcardtotalamount = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));

        // 11072023
        double salesbytendertypes_giftcardtotalamount_dbl = GlobalMemberValues.getDoubleAtString(salesbytendertypes_giftcardtotalamount) -
                GlobalMemberValues.getDoubleAtString(salessummary_gratuity_giftcard);
        salesbytendertypes_giftcardtotalamount = GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_giftcardtotalamount_dbl + "");

        jsonroot.put("salesbytendertypes_giftcardtotalamount", salesbytendertypes_giftcardtotalamount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Sales Points Count --------------------------------------------------------------------------------------------------
        sqlQuery = "select count(idx) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and useTotalPointAmountAfterReturned > 0";
        String salesbytendertypes_pointstransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesbytendertypes_pointstransactions", salesbytendertypes_pointstransactions);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Points total --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) - " + salessummary_othersales_point_dbl + " from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        String salesbytendertypes_pointstotalamount = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));

        // 11072023
        double salesbytendertypes_pointstotalamount_dbl = GlobalMemberValues.getDoubleAtString(salesbytendertypes_pointstotalamount) -
                GlobalMemberValues.getDoubleAtString(salessummary_gratuity_point);
        salesbytendertypes_pointstotalamount = GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_pointstotalamount_dbl + "");


        jsonroot.put("salesbytendertypes_pointstotalamount", salesbytendertypes_pointstotalamount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Sales Others Count --------------------------------------------------------------------------------------------------
        sqlQuery = "select count(idx) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and useTotalCheckAmountAfterReturned > 0";
        String salesbytendertypes_otherstransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("salesbytendertypes_otherstransactions", salesbytendertypes_otherstransactions);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Sales Others total --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) - " + salessummary_othersales_check_dbl + " from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        String salesbytendertypes_otherstotalamount = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));


        // 11072023
        double salesbytendertypes_otherstotalamount_dbl = GlobalMemberValues.getDoubleAtString(salesbytendertypes_otherstotalamount) -
                GlobalMemberValues.getDoubleAtString(salessummary_gratuity_other);
        salesbytendertypes_otherstotalamount = GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_otherstotalamount_dbl + "");


        jsonroot.put("salesbytendertypes_otherstotalamount", salesbytendertypes_otherstotalamount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Total --------------------------------------------------------------------------------------------------
        int salesbytendertypes_totaltransaction_dbl =
                GlobalMemberValues.getIntAtString(salesbytendertypes_cashtransactions) +
                        GlobalMemberValues.getIntAtString(salesbytendertypes_creditcardtransactions) +
                        GlobalMemberValues.getIntAtString(salesbytendertypes_giftcardtransactions) +
                        GlobalMemberValues.getIntAtString(salesbytendertypes_pointstransactions) +
                        GlobalMemberValues.getIntAtString(salesbytendertypes_otherstransactions);

        double salesbytendertypes_totalamount_dbl =
                GlobalMemberValues.getDoubleAtString(salesbytendertypes_cashtotalamount) +
                        GlobalMemberValues.getDoubleAtString(salesbytendertypes_creditcardtotalamount) +
                        GlobalMemberValues.getDoubleAtString(salesbytendertypes_giftcardtotalamount) +
                        GlobalMemberValues.getDoubleAtString(salesbytendertypes_pointstotalamount) +
                        GlobalMemberValues.getDoubleAtString(salesbytendertypes_otherstotalamount);

        jsonroot.put("salesbytendertypes_totaltransaction",
                GlobalMemberValues.getCommaStringForInteger(salesbytendertypes_totaltransaction_dbl + ""));  // JSON

        jsonroot.put("salesbytendertypes_totalamount",
                GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_totalamount_dbl + ""));  // JSON

        GlobalMemberValues.logWrite("salesbytendertypes_totalamount_log", "salesbytendertypes_totalamount : " + salesbytendertypes_totalamount_dbl + "\n");
        // -------------------------------------------------------------------------------------------------------------------
        /** Sales by Tender Types 끝 **********************************************************************************************************/


        /** Cash Summary Details 시작 **********************************************************************************************************/

        if (GlobalMemberValues.isCashoutreportPrintingByItemsNum("<1>")){
            // Cash Transactions --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and status = 0 " +
                    " and useTotalCashAmountAfterReturned > 0";
            String cashsummarydetails_cashtransactions = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cashsummarydetails_cashtransactions", cashsummarydetails_cashtransactions);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
        }

        // Cash Sales --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCashAmount, 2)) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " and status = 0 ";
        String cashsummarydetails_cashsales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));

        // 11232023
        double cashsummarydetails_cashsales_dbl = GlobalMemberValues.getDoubleAtString(cashsummarydetails_cashsales)
                - GlobalMemberValues.getDoubleAtString(salessummary_gratuity_cash);
        cashsummarydetails_cashsales = GlobalMemberValues.getCommaStringForDouble(cashsummarydetails_cashsales_dbl + "");

        jsonroot.put("cashsummarydetails_cashsales", cashsummarydetails_cashsales);  // JSON
        // -------------------------------------------------------------------------------------------------------------------
        // Cash Refund --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCashAmount, 2)) from salon_sales_return " +
                " where returnCode in (" +
                " select returnCode from salon_sales_detail " +
                " where " + empSqlQuery + addSqlQuery +
                " and delyn = 'N' " +
                " ) ";
        GlobalMemberValues.logWrite("cashrefundlog", "sqlQuery : " + sqlQuery + "\n");
        String cashsummarydetails_cashrefund = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        jsonroot.put("cashsummarydetails_cashrefund", cashsummarydetails_cashrefund);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Others (Tip + Tax + Fees) -----------------------------------------------------------------------------------------
        sqlQuery = " select sum(round((usedCash), 2)) from salon_sales_tip " +
                " where " + empSqlQuery + addSqlQuery +
                " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
        String cashsummarydetails_others = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));

        // 11232023
        double cashsummarydetails_others_dbl = GlobalMemberValues.getDoubleAtString(cashsummarydetails_others) + GlobalMemberValues.getDoubleAtString(salessummary_gratuity_cash);
        cashsummarydetails_others = GlobalMemberValues.getCommaStringForDouble(cashsummarydetails_others_dbl + "");

        jsonroot.put("cashsummarydetails_others", cashsummarydetails_others);  // JSON
        // ------------------------------------------------------------------------------------------------------------------

        // Others (Tip + Tax + Fees) -----------------------------------------------------------------------------------------
        String cashsummarydetails_cashtotal = salesbytendertypes_cashtotalamount;
        jsonroot.put("cashsummarydetails_cashtotal", cashsummarydetails_cashtotal);  // JSON
        // ------------------------------------------------------------------------------------------------------------------
        /** Cash Summary Details 끝 **********************************************************************************************************/

        if (GlobalMemberValues.isCashoutreportPrintingByItemsNum("<2>")){
/** Credit Card Merchant Settlement Details 시작 *************************************************************************************/

            // Master Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'M'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtransmaster = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtransmaster", cardsettlementdetail_cardtransmaster);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Master Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'M'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalesmaster = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalesmaster", cardsettlementdetail_cardsalesmaster);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Master Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'M'" +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefundmaster = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefundmaster", cardsettlementdetail_cardrefundmaster);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Master Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and cardCom = 'M' " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_master_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtipmaster", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_master_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Master Total --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotalmaster_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalesmaster) +
                            GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefundmaster) + salesbytendertypes_cardtipamount_master_dbl;
            String cardsettlementdetail_cardtotalmaster = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotalmaster_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotalmaster", cardsettlementdetail_cardtotalmaster);  // JSON
            // -------------------------------------------------------------------------------------------------------------------


            // Visa Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'V'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtransvisa = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtransvisa", cardsettlementdetail_cardtransvisa);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // visa Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'V'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalesvisa = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalesvisa", cardsettlementdetail_cardsalesvisa);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // visa Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'V'" +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefundvisa = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefundvisa", cardsettlementdetail_cardrefundvisa);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Visa Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and cardCom = 'V' " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_visa_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtipvisa", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_visa_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // visa Total --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotalvisa_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalesvisa) +
                            GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefundvisa) + salesbytendertypes_cardtipamount_visa_dbl;
            String cardsettlementdetail_cardtotalvisa = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotalvisa_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotalvisa", cardsettlementdetail_cardtotalvisa);  // JSON
            // -------------------------------------------------------------------------------------------------------------------


            // amex Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'A'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtransamex = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtransamex", cardsettlementdetail_cardtransamex);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // amex Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'A'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalesamex = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalesamex", cardsettlementdetail_cardsalesamex);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // amex Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'A'" +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefundamex = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefundamex", cardsettlementdetail_cardrefundamex);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Amex Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and cardCom = 'A' " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_amex_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtipamex", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_amex_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // amex Total --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotalamex_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalesamex) +
                            GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefundamex) + salesbytendertypes_cardtipamount_amex_dbl;
            String cardsettlementdetail_cardtotalamex = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotalamex_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotalamex", cardsettlementdetail_cardtotalamex);  // JSON
            // -------------------------------------------------------------------------------------------------------------------


            // discover Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'D'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtransdiscover = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtransdiscover", cardsettlementdetail_cardtransdiscover);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // discover Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'D'" +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalesdiscover = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalesdiscover", cardsettlementdetail_cardsalesdiscover);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // discover Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and cardCom = 'D'" +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefunddiscover = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefunddiscover", cardsettlementdetail_cardrefunddiscover);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Discover Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and cardCom = 'D' " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_discover_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtipdiscvoer", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_discover_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // discover Total --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotaldiscover_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalesdiscover) +
                            GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefunddiscover) + salesbytendertypes_cardtipamount_discover_dbl;
            String cardsettlementdetail_cardtotaldiscover = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotaldiscover_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotaldiscover", cardsettlementdetail_cardtotaldiscover);  // JSON
            // -------------------------------------------------------------------------------------------------------------------



            // other Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and not(cardCom = 'M' or cardCom = 'V' or cardCom = 'A' or cardCom = 'D') " +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtransother = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtransother", cardsettlementdetail_cardtransother);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // other Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and not(cardCom = 'M' or cardCom = 'V' or cardCom = 'A' or cardCom = 'D') " +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalesother = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalesother", cardsettlementdetail_cardsalesother);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // other Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and not(cardCom = 'M' or cardCom = 'V' or cardCom = 'A' or cardCom = 'D') " +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefundother = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefundother", cardsettlementdetail_cardrefundother);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Other Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and not(cardCom = 'M' or cardCom = 'V' or cardCom = 'A' or cardCom = 'D') " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_other_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtipother", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_other_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // other Total --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotalother_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalesother) +
                            GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefundother) + salesbytendertypes_cardtipamount_other_dbl;
            String cardsettlementdetail_cardtotalother = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotalother_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotalother", cardsettlementdetail_cardtotalother);  // JSON
            // -------------------------------------------------------------------------------------------------------------------


            // total Trans --------------------------------------------------------------------------------------------------
            sqlQuery = "select count(idx) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardtranstotal = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtranstotal", cardsettlementdetail_cardtranstotal);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // total Sales --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and substr(salesCode, 1, 1) = 'K' " +
                    " and priceAmount > 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardsalestotal = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardsalestotal", cardsettlementdetail_cardsalestotal);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // total Refund --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and substr(salesCode, 1, 1) = 'C' " +
                    " and priceAmount < 0 " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
            String cardsettlementdetail_cardrefundtotal = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardrefundtotal", cardsettlementdetail_cardrefundtotal);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Total Card Tip --------------------------------------------------------------------------------------------------
            sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
            double salesbytendertypes_cardtipamount_total_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("cardsettlementdetail_cardtiptotal", GlobalMemberValues.getCommaStringForDouble(salesbytendertypes_cardtipamount_total_dbl + ""));  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // total Total --------------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round((priceAmount + orgTip), 2)) from salon_sales_card " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' " +
                    " and " + subStrString + " not in (select " + subStrString + " from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";

            double cardsettlementdetail_cardtotaltotal_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery));
            cardsettlementdetail_cardtotaltotal_dbl += salesbytendertypes_cardtipamount_total_dbl;
            String cardsettlementdetail_cardtotaltotal = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotaltotal_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotaltotal", cardsettlementdetail_cardtotaltotal);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            // Others --------------------------------------------------------------------------------------------------
            double cardsettlementdetail_cardtotalothers_dbl =
                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardtotaltotal) -
                            (
                                    GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardsalestotal)
                                            + GlobalMemberValues.getDoubleAtString(cardsettlementdetail_cardrefundtotal)
                            );
            String cardsettlementdetail_cardtotalothers = GlobalMemberValues.getCommaStringForDouble(cardsettlementdetail_cardtotalothers_dbl + "");
            jsonroot.put("cardsettlementdetail_cardtotalothers", cardsettlementdetail_cardtotalothers);  // JSON
            // -------------------------------------------------------------------------------------------------------------------

            /** Credit Card Merchant Settlement Details 끝 *************************************************************************************/
        }



        if (GlobalMemberValues.isCashoutreportPrintingByItemsNum("<3>")){
            /** Cashiers 시작 *****************************************************************************************************************/

            GlobalMemberValues.logWrite("cashiers_log", "여기실행..." + "\n");

            // 직원 Cash In 기록 -------------------------------------------------------------------------------------------------
            String cashiers_addsql = "";
            if (isEndofday) {
                cashiers_addsql = " and cashoutNum > 0 ";
            } else {
                cashiers_addsql = " and cashoutNum = 0 ";
            }

            JSONArray jsonList_casher = new JSONArray();
            JSONObject jsontmp_casher = null;

            String cashier_empIdx = "";
            String cashier_empName = "";
            String cashier_totalcash = "";
            String cashier_creditcard = "";
            String cashier_giftcard = "";
            String cashier_points = "";
            String cashier_others = "";
            String cashier_totalamountonhand = "";
            String cashier_registerstartamount = "";
            String cashier_endingmoney = "";
            String cashier_netamountonhand = "";
            String cashier_cashoutdate = "";

            sqlQuery = " select distinct employeeidx from salon_sales " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and delyn = 'N' ";
            GlobalMemberValues.logWrite("cashiers_log", "sqlQuery : " + sqlQuery + "\n");


            if (isEndofday) {
                ResultSet empCashierCursor = MssqlDatabase.getResultSetValue(sqlQuery);
                try {
                    while (empCashierCursor.next()){
                        cashier_empIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empCashierCursor,0), 1);
                        GlobalMemberValues.logWrite("cashiers_log", "cashier_empIdx : " + cashier_empIdx + "\n");
                        if (!GlobalMemberValues.isStrEmpty(cashier_empIdx)) {
                            if (isEndofday) {
                                sqlQuery = " select top 1 employeeName, totalcash, totalcreditcard, totalgiftcard, totalpoints, totalothers, totalamountonhand, " +
                                        " registerstartamount, endingmoney, netamountonhand, wdate from salon_sales_cashout_emp" +
                                        " where employeeIdx = '" + cashier_empIdx + "' " + cashiers_addsql +
                                        " order by idx desc ";
                            } else {
                                sqlQuery = " select employeeName, totalcash, totalcreditcard, totalgiftcard, totalpoints, totalothers, totalamountonhand, " +
                                        " registerstartamount, endingmoney, netamountonhand, wdate from salon_sales_cashout_emp" +
                                        " where employeeIdx = '" + cashier_empIdx + "' " + cashiers_addsql +
                                        " order by idx desc limit 1 ";
                            }

                            GlobalMemberValues.logWrite("cashiers_log", "sqlQuery : " + sqlQuery + "\n");

                            ResultSet cashoutEmpCursor = MssqlDatabase.getResultSetValue(sqlQuery);
                            try {
                                while (cashoutEmpCursor.next()){
                                    cashier_empName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,0), 1);
                                    cashier_totalcash = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,1), 1);
                                    cashier_creditcard = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,2), 1);
                                    cashier_giftcard = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,3), 1);
                                    cashier_points = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,4), 1);
                                    cashier_others = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,5), 1);
                                    cashier_totalamountonhand = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,6), 1);
                                    cashier_registerstartamount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,7), 1);
                                    cashier_endingmoney = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,8), 1);
                                    cashier_netamountonhand = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,9), 1);
                                    cashier_cashoutdate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cashoutEmpCursor,10), 1);

                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_empName : " + cashier_empName + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_totalcash : " + cashier_totalcash + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_creditcard : " + cashier_creditcard + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_giftcard : " + cashier_giftcard + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_points : " + cashier_points + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_others : " + cashier_others + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_totalamountonhand : " + cashier_totalamountonhand + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_registerstartamount : " + cashier_registerstartamount + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_endingmoney : " + cashier_endingmoney + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_netamountonhand : " + cashier_netamountonhand + "\n");
                                    GlobalMemberValues.logWrite("cashiers_log", "cashier_cashoutdate : " + cashier_cashoutdate + "\n");

                                    jsontmp_casher = new JSONObject();
                                    jsontmp_casher.put("cashier_empname", cashier_empName);
                                    jsontmp_casher.put("cashier_totalcash", GlobalMemberValues.getCommaStringForDouble(cashier_totalcash));
                                    jsontmp_casher.put("cashier_creditcard", GlobalMemberValues.getCommaStringForDouble(cashier_creditcard));
                                    jsontmp_casher.put("cashier_giftcard", GlobalMemberValues.getCommaStringForDouble(cashier_giftcard));
                                    jsontmp_casher.put("cashier_points", GlobalMemberValues.getCommaStringForDouble(cashier_points));
                                    jsontmp_casher.put("cashier_others", GlobalMemberValues.getCommaStringForDouble(cashier_others));
                                    jsontmp_casher.put("cashier_totalamountonhand", GlobalMemberValues.getCommaStringForDouble(cashier_totalamountonhand));
                                    jsontmp_casher.put("cashier_registerstartamount", GlobalMemberValues.getCommaStringForDouble(cashier_registerstartamount));
                                    jsontmp_casher.put("cashier_endingmoney", GlobalMemberValues.getCommaStringForDouble(cashier_endingmoney));
                                    jsontmp_casher.put("cashier_netamountonhand", GlobalMemberValues.getCommaStringForDouble(cashier_netamountonhand));
                                    jsontmp_casher.put("cashier_cashoutdate", cashier_cashoutdate);

                                    jsonList_casher.put(jsontmp_casher);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    //07052024 close result set
                    empCashierCursor.close();
                } catch (Exception e) {
                }
            } else {
                Cursor empCashierCursor;
                empCashierCursor = dbInit.dbExecuteRead(sqlQuery);
                while (empCashierCursor.moveToNext()) {
                    cashier_empIdx = GlobalMemberValues.getDBTextAfterChecked(empCashierCursor.getString(0), 1);
                    GlobalMemberValues.logWrite("cashiers_log", "cashier_empIdx : " + cashier_empIdx + "\n");
                    if (!GlobalMemberValues.isStrEmpty(cashier_empIdx)) {
                        if (isEndofday) {
                            sqlQuery = " select top 1 employeeName, totalcash, totalcreditcard, totalgiftcard, totalpoints, totalothers, totalamountonhand, " +
                                    " registerstartamount, endingmoney, netamountonhand, wdate from salon_sales_cashout_emp" +
                                    " where employeeIdx = '" + cashier_empIdx + "' " + cashiers_addsql +
                                    " order by idx desc ";
                        } else {
                            sqlQuery = " select employeeName, totalcash, totalcreditcard, totalgiftcard, totalpoints, totalothers, totalamountonhand, " +
                                    " registerstartamount, endingmoney, netamountonhand, wdate from salon_sales_cashout_emp" +
                                    " where employeeIdx = '" + cashier_empIdx + "' " + cashiers_addsql +
                                    " order by idx desc limit 1 ";
                        }

                        GlobalMemberValues.logWrite("cashiers_log", "sqlQuery : " + sqlQuery + "\n");

                        Cursor cashoutEmpCursor = dbInit.dbExecuteRead(sqlQuery);
                        if (cashoutEmpCursor.getCount() > 0 && cashoutEmpCursor.moveToFirst()) {
                            cashier_empName = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(0), 1);
                            cashier_totalcash = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(1), 1);
                            cashier_creditcard = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(2), 1);
                            cashier_giftcard = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(3), 1);
                            cashier_points = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(4), 1);
                            cashier_others = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(5), 1);
                            cashier_totalamountonhand = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(6), 1);
                            cashier_registerstartamount = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(7), 1);
                            cashier_endingmoney = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(8), 1);
                            cashier_netamountonhand = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(9), 1);
                            cashier_cashoutdate = GlobalMemberValues.getDBTextAfterChecked(cashoutEmpCursor.getString(10), 1);

                            GlobalMemberValues.logWrite("cashiers_log", "cashier_empName : " + cashier_empName + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_totalcash : " + cashier_totalcash + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_creditcard : " + cashier_creditcard + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_giftcard : " + cashier_giftcard + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_points : " + cashier_points + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_others : " + cashier_others + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_totalamountonhand : " + cashier_totalamountonhand + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_registerstartamount : " + cashier_registerstartamount + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_endingmoney : " + cashier_endingmoney + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_netamountonhand : " + cashier_netamountonhand + "\n");
                            GlobalMemberValues.logWrite("cashiers_log", "cashier_cashoutdate : " + cashier_cashoutdate + "\n");

                            jsontmp_casher = new JSONObject();
                            jsontmp_casher.put("cashier_empname", cashier_empName);
                            jsontmp_casher.put("cashier_totalcash", GlobalMemberValues.getCommaStringForDouble(cashier_totalcash));
                            jsontmp_casher.put("cashier_creditcard", GlobalMemberValues.getCommaStringForDouble(cashier_creditcard));
                            jsontmp_casher.put("cashier_giftcard", GlobalMemberValues.getCommaStringForDouble(cashier_giftcard));
                            jsontmp_casher.put("cashier_points", GlobalMemberValues.getCommaStringForDouble(cashier_points));
                            jsontmp_casher.put("cashier_others", GlobalMemberValues.getCommaStringForDouble(cashier_others));
                            jsontmp_casher.put("cashier_totalamountonhand", GlobalMemberValues.getCommaStringForDouble(cashier_totalamountonhand));
                            jsontmp_casher.put("cashier_registerstartamount", GlobalMemberValues.getCommaStringForDouble(cashier_registerstartamount));
                            jsontmp_casher.put("cashier_endingmoney", GlobalMemberValues.getCommaStringForDouble(cashier_endingmoney));
                            jsontmp_casher.put("cashier_netamountonhand", GlobalMemberValues.getCommaStringForDouble(cashier_netamountonhand));
                            jsontmp_casher.put("cashier_cashoutdate", cashier_cashoutdate);

                            jsonList_casher.put(jsontmp_casher);


                        } else {
                        }
                        cashoutEmpCursor.close();
                    }
                }
                empCashierCursor.close();
            }

            jsonroot.put("cashiersdata", jsonList_casher);
            // ------------------------------------------------------------------------------------------------------------------
            /** Cashiers 끝 *****************************************************************************************************************/
        }


        if (GlobalMemberValues.isCashoutreportPrintingByItemsNum("<4>")){
            /** Gift Card Summary 시작 *******************************************************************************************************/
            // Gift Card Redeemed --------------------------------------------------------------------------------------------------
            String giftcardsummary_redeemed = salesbytendertypes_giftcardtotalamount;
            jsonroot.put("giftcardsummary_redeemed", giftcardsummary_redeemed);  // JSON
            // -------------------------------------------------------------------------------------------------------------------

            // New Gift Cert Sales -----------------------------------------------------------------------------------------------
            sqlQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                    " where " + empSqlQuery + addSqlQuery +
                    " and saveType = '2' " +
                    " and delyn = 'N' " + commonGratuitySql;
            String giftcardsummary_newcertsales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
            jsonroot.put("giftcardsummary_newcertsales", giftcardsummary_newcertsales);  // JSON
            // -------------------------------------------------------------------------------------------------------------------
            /** Gift Card Summary 끝 *********************************************************************************************************/
        }


        // 12292022
        // Cash Card DC EXTRA -----------------------------------------------------------------------------------------------
        String tempAddPayName = GlobalMemberValues.getAddPayData()[2];
        String cashcard_dcextra_sales = "0";
        if (!GlobalMemberValues.isStrEmpty(tempAddPayName)) {
            String empSqlQuery_forAddPay = " idx is not null and cashoutNum = 0 ";

            sqlQuery = "select sum(round(salesBalPriceAmount, 2)) from salon_sales_detail " +
                    " where " + empSqlQuery_forAddPay + addSqlQuery +
                    " and itemName like '%" + GlobalMemberValues.getAddPayData()[2] + "%' " +
                    " and delyn = 'N' ";
            cashcard_dcextra_sales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
        }
        jsonroot.put("cashcard_dcextra_addpayname", tempAddPayName);  // JSON
        jsonroot.put("cashcard_dcextra_sales", cashcard_dcextra_sales);  // JSON
        // -------------------------------------------------------------------------------------------------------------------



        /** Sales by Third Party App 시작 ************************************************************************************************/
        JSONArray jsonList_thirdparty = new JSONArray();
        JSONObject jsontmp_thirdparty = null;

        String thirdparty_name = "";
        String thirdparty_count = "";
        String thirdparty_amount = "";


        sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)), checkcompany, count(idx) from salon_sales " +
                " where " + empSqlQuery + addSqlQuery +
                " and (useTotalCheckAmountAfterReturned > 0 or useTotalCheckAmountAfterReturned < 0) " +
                " and delyn = 'N' " +
                " and status = 0 " +
                " group by checkcompany order by checkcompany asc ";

        if (isEndofday) {
            ResultSet empLoginCursor = MssqlDatabase.getResultSetValue(sqlQuery);
            try {
                while (empLoginCursor.next()){
                    thirdparty_name = "";
                    thirdparty_count = "";
                    thirdparty_amount = "";

                    thirdparty_name = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,0), 1);
                    thirdparty_count = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,1), 1);
                    thirdparty_amount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,2), 1);

                    jsontmp_thirdparty = new JSONObject();
                    jsontmp_thirdparty.put("salesbythirdpartyapp_name", thirdparty_name);
                    jsontmp_thirdparty.put("salesbythirdpartyapp_count", thirdparty_count);
                    jsontmp_thirdparty.put("salesbythirdpartyapp_amount", thirdparty_amount);

                    jsonList_thirdparty.put(jsontmp_thirdparty);
                }
                //07052025 close resultset
                empLoginCursor.close();
            } catch (Exception e) {
            }
        } else {
            Cursor thirdpartyCursor;
            thirdpartyCursor = dbInit.dbExecuteRead(sqlQuery);
            while (thirdpartyCursor.moveToNext()) {
                thirdparty_name = "";
                thirdparty_count = "";
                thirdparty_amount = "";

                thirdparty_name = GlobalMemberValues.getDBTextAfterChecked(thirdpartyCursor.getString(0), 1);
                thirdparty_count = GlobalMemberValues.getDBTextAfterChecked(thirdpartyCursor.getString(1), 1);
                thirdparty_amount = GlobalMemberValues.getDBTextAfterChecked(thirdpartyCursor.getString(2), 1);

                jsontmp_thirdparty = new JSONObject();
                jsontmp_thirdparty.put("salesbythirdpartyapp_name", thirdparty_name);
                jsontmp_thirdparty.put("salesbythirdpartyapp_count", thirdparty_count);
                jsontmp_thirdparty.put("salesbythirdpartyapp_amount", thirdparty_amount);

                jsonList_thirdparty.put(jsontmp_thirdparty);
            }
            thirdpartyCursor.close();
        }
        jsonroot.put("salesbythirdpartyapp", jsonList_thirdparty);



        /** Sales by Third Party App 끝 **************************************************************************************************/

        if (GlobalMemberValues.isCashoutreportPrintingByItemsNum("<5>")){
            // 카테고리별 매출금액 ---------------------------------------------------------------------------------------------------
            String tempCateIdx = "";
            String tempCateName = "";

            String category_sales = "";
            double category_sales_dbl = 0;

            String category_extra = "";
            double category_extra_dbl = 0;

            double category_discount_dbl = 0;
            String category_discount = "";

            String cateData = "";
            String returnCategoryData = "";

            String category_sales_cnt = "";


            strQuery = "select idx, servicename from salon_storeservice_main " +
                    " where delyn = 'N' " +
                    " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                    " and positionNo > 0 and not(positionNo is null or positionNo = '')" +
                    " order by servicename asc";

            if (isEndofday) {
                ResultSet empLoginCursor = MssqlDatabase.getResultSetValue(sqlQuery);
                try {
                    int catecnt = 0;
                    while (empLoginCursor.next()){
                        tempCateIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,0), 1);
                        tempCateName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(empLoginCursor,1), 1);


                        sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and categoryCode = '" + tempCateIdx + "' " +
                                " and not(saveType = '2') " +
                                " and delyn = 'N' " + commonGratuitySql;
                        category_sales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                        category_sales_dbl = GlobalMemberValues.getDoubleAtString(category_sales);

                        GlobalMemberValues.logWrite("discountextrasumlog", "category_sales_dbl_1 : " + category_sales_dbl + "\n");


                        String commonQuery = "select salesCode from salon_sales_detail " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and categoryCode = '" + tempCateIdx + "' " +
                                " and not(saveType = '2') " +
                                " and delyn = 'N' " + commonGratuitySql;

                        String commonQuery2 = "select salesCode from salon_sales_detail " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and categoryCode = '" + tempCateIdx + "' " +
                                " and not(saveType = '2') " +
                                " and delyn = 'N' ";

                        // Extra Total
                        sqlQuery = "select sum(round(totalExtraPrice, 2)) from salon_sales " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and delyn = 'N' " +
                                " and substr(salesCode, 1, 1) = 'K' " +
                                " and salescode in ( " + commonQuery + " ) ";
                        category_extra = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                        category_extra_dbl = GlobalMemberValues.getDoubleAtString(category_extra);
                        //jsonroot.put("category_extra", category_extra);  // JSON

                        // Discount Total
                        sqlQuery = "select sum(round(dcextraforreturn, 2)) from salon_sales_detail " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and delyn = 'N' " +
                                " and " + subStrString + " not in " +
                                " (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') " +
                                // 06122023
                                // commonGratuitySql 부분을 제거
                                //" and salescode in ( " + commonQuery + " ) " + commonGratuitySql;
                                " and salescode in ( " + commonQuery2 + " ) ";
                        category_discount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery)) * -1;
                        category_discount = GlobalMemberValues.getCommaStringForDouble(category_discount_dbl + "");
                        jsonroot.put("category_discount", category_discount);  // JSON


                        category_sales_dbl += (category_extra_dbl + category_discount_dbl);

                        GlobalMemberValues.logWrite("discountextrasumlog", "category_sales_dbl_2 : " + category_sales_dbl + "\n");

                        category_sales = GlobalMemberValues.getCommaStringForDouble(category_sales_dbl + "");

                        // 전체 세일 건수
                        sqlQuery = "select count(*) from salon_sales " +
                                " where " + empSqlQuery + addSqlQuery +
                                " and delyn = 'N' " +
                                " and substr(salesCode, 1, 1) = 'K' " +
                                " and salescode in ( " + commonQuery + " ) ";
                        category_sales_cnt = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));

                        // 카테고리 세일데이터 조합
                        cateData = tempCateName + "-JJJ-" + category_sales + "-JJJ-" + category_sales_cnt;
                        if (catecnt > 0) {
                            returnCategoryData += "-WHY-";
                        }
                        returnCategoryData += cateData;

                        catecnt++;
                    }
                    //07052024 close resultset
                    empLoginCursor.close();
                } catch (Exception e) {
                }
            } else {
                Cursor cursor = dbInit.dbExecuteRead(strQuery);

                int catecnt = 0;
                while (cursor.moveToNext()) {
                    tempCateIdx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
                    tempCateName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);


                    sqlQuery = "select sum(round(salesPriceAmount, 2)) from salon_sales_detail " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and categoryCode = '" + tempCateIdx + "' " +
                            " and not(saveType = '2') " +
                            " and delyn = 'N' " + commonGratuitySql;
                    category_sales = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                    category_sales_dbl = GlobalMemberValues.getDoubleAtString(category_sales);

                    GlobalMemberValues.logWrite("discountextrasumlog", "category_sales_dbl_1 : " + category_sales_dbl + "\n");


                    String commonQuery = "select salesCode from salon_sales_detail " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and categoryCode = '" + tempCateIdx + "' " +
                            " and not(saveType = '2') " +
                            " and delyn = 'N' " + commonGratuitySql;

                    // 06122023
                    String commonQuery2 = "select salesCode from salon_sales_detail " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and categoryCode = '" + tempCateIdx + "' " +
                            " and not(saveType = '2') " +
                            " and delyn = 'N' ";

                    // Extra Total
                    sqlQuery = "select sum(round(totalExtraPrice, 2)) from salon_sales " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and delyn = 'N' " +
                            " and substr(salesCode, 1, 1) = 'K' " +
                            " and salescode in ( " + commonQuery + " ) ";
                    category_extra = GlobalMemberValues.getCommaStringForDouble(getDataOnSql(isEndofday, sqlQuery));
                    category_extra_dbl = GlobalMemberValues.getDoubleAtString(category_extra);
                    //jsonroot.put("category_extra", category_extra);  // JSON

                    // Discount Total
                    sqlQuery = "select sum(round(dcextraforreturn, 2)) from salon_sales_detail " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and delyn = 'N' " +
                            " and " + subStrString + " not in " +
                            " (select " + subStrString + " from salon_sales_detail where substr(salesCode, 1, 1) = 'V') " +
                            // 06122023
                            // commonGratuitySql 부분을 제거
                            //" and salescode in ( " + commonQuery + " ) " + commonGratuitySql;
                            " and salescode in ( " + commonQuery2 + " ) ";
                    category_discount_dbl = GlobalMemberValues.getDoubleAtString(getDataOnSql(isEndofday, sqlQuery)) * -1;
                    category_discount = GlobalMemberValues.getCommaStringForDouble(category_discount_dbl + "");
                    jsonroot.put("category_discount", category_discount);  // JSON


                    category_sales_dbl += (category_extra_dbl + category_discount_dbl);

                    GlobalMemberValues.logWrite("discountextrasumlog", "category_sales_dbl_2 : " + category_sales_dbl + "\n");

                    category_sales = GlobalMemberValues.getCommaStringForDouble(category_sales_dbl + "");

                    // 전체 세일 건수
                    sqlQuery = "select count(*) from salon_sales " +
                            " where " + empSqlQuery + addSqlQuery +
                            " and delyn = 'N' " +
                            " and substr(salesCode, 1, 1) = 'K' " +
                            " and salescode in ( " + commonQuery + " ) ";
                    category_sales_cnt = GlobalMemberValues.getCommaStringForInteger(getDataOnSql(isEndofday, sqlQuery));

                    // 카테고리 세일데이터 조합
                    cateData = tempCateName + "-JJJ-" + category_sales + "-JJJ-" + category_sales_cnt;
                    if (catecnt > 0) {
                        returnCategoryData += "-WHY-";
                    }
                    returnCategoryData += cateData;

                    catecnt++;
                }
            }

            jsonroot.put("category_sales", returnCategoryData);  // JSON

            // -------------------------------------------------------------------------------------------------------------------
        }

        return jsonroot;
    }
}
