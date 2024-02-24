package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class Payment_CreditCard_Proc_Exec {
    public static ArrayList<String> getProcList(String paramDate1, String paramDate2, String paramCheck) {
        ArrayList<String> returnArrayList = null;

        returnArrayList = new ArrayList<String>();

        String get_date = "";
        String get_items_list = "";
        String get_amount = "";
        String get_resultmsg = "";
        String get_idx = "";
        String get_resultcode = "";
        String get_delyn = "";

        String returnStr = "";

        if (GlobalMemberValues.isStrEmpty(paramDate1)) {
            paramDate1 = GlobalMemberValues.STR_NOW_DATE;
        }
        if (GlobalMemberValues.isStrEmpty(paramDate2)) {
            paramDate2 = GlobalMemberValues.STR_NOW_DATE;
        }

        String addSql1 = "";
        if (!GlobalMemberValues.isStrEmpty(paramCheck)) {
            if (!GlobalMemberValues.isStrEmpty(paramCheck)) {
                addSql1 += " and ( ";
                addSql1 += " resultcode is not null or resultcode = '' ";
                if (paramCheck.indexOf("P") != -1) {
                    addSql1 += " or resultcode = '' ";
                }
                if (paramCheck.indexOf("S") != -1) {
                    addSql1 += " or resultcode = '00' ";
                }
                if (paramCheck.indexOf("D") != -1) {
                    addSql1 += " or resultcode = '99' ";
                }
                addSql1 += " ) ";
            } else {
                addSql1 = " and (resultcode = 'NO' or resultcode is not null or resultcode = '' ) ";
            }
        }

        String strQuery = " select wdate, menuitems_list, amount, resultmsg, idx, resultcode, delyn from card_processing_data  " +
                " where " +
                " stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                " and wdate between '" + DateMethodClass.getCustomEditDate(paramDate1, 0, 0, 0) + "' " +
                " and '" + DateMethodClass.getCustomEditDate(paramDate2, 0, 0, 1) + "' " + addSql1 +
                " order by wdate desc ";
        GlobalMemberValues.logWrite("statusjjjlog", "sql : " + strQuery + "\n");
        ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
        try {
            String forceSaleBtn = "N";
            int cnt = 0;
            while (resultSet.next()) {
                get_date = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                get_items_list = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                get_amount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                get_resultmsg = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);
                get_idx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 4);
                get_resultcode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 5);
                get_delyn = GlobalMemberValues.resultDB_checkNull_string(resultSet, 6);

                if (GlobalMemberValues.isStrEmpty(get_delyn)) {
                    get_delyn = "N";
                }

                if (cnt == 0 && GlobalMemberValues.isStrEmpty(get_resultcode)) {
                    forceSaleBtn = "Y";
                } else {
                    forceSaleBtn = "N";
                }

                if (get_delyn.equals("N")) {
                    returnStr = get_date + "-JJJ-" + get_items_list +
                            "-JJJ-" + GlobalMemberValues.getDoubleAtString(get_amount) +
                            "-JJJ-" + get_resultmsg + "-JJJ-" + get_idx + "-JJJ-" + forceSaleBtn;
                    returnArrayList.add(returnStr);
                }

                GlobalMemberValues.logWrite("returnstrjjjlog", "returnStr : " + returnStr + "\n");

                forceSaleBtn = "N";
                cnt++;
            }
        } catch (Exception e) {
        }

        return returnArrayList;
    }

    public static String deleteCardTryList(String paramIdx) {
        Vector<String> vec = new Vector<String>();
        String tempSql = " update card_processing_data set delyn = 'Y' where idx = '" + paramIdx + "' ";
        vec.add(tempSql);
        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        return returnValue;
    }

    public static String updateCardTryList(String paramIdx) {
        Vector<String> vec = new Vector<String>();
        String tempSql = " update card_processing_data set resultcode = '00', resultmsg = 'FORCE SALE' where idx = '" + paramIdx + "' ";
        vec.add(tempSql);
        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        return returnValue;
    }

    public static String getMenuItemsAll(String paramIdx) {
        String returnValue = "";
        String tempSql = " select menuitems_all from card_processing_data where idx = '" + paramIdx + "' ";
        ResultSet resultSet = MssqlDatabase.getResultSetValue(tempSql);
        try {
            while (resultSet.next()) {
                returnValue = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                    returnValue = GlobalMemberValues.getReplaceText(returnValue, "@n@", "\n");
                }
            }
        } catch (Exception e) {
        }
        return returnValue;
    }

}