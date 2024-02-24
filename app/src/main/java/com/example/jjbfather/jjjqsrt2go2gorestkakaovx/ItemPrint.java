package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

import java.util.Vector;

public class ItemPrint {

    // 01072023
    public String makeTempPrintingCode() {
        String returnReturnCode = "";
        returnReturnCode = "TPT" + GlobalMemberValues.makeSalesCode();
        return returnReturnCode;
    }

    // 01072023
    public boolean setAddItemQtyByDuplicate(String paramPrintCode, String[] paramItemDatas) {
        boolean returnValue = false;

        if (paramItemDatas != null) {
            // ---------------------------------------------------------------------------------
            String[] strOrderItems = paramItemDatas;

            // 상품명, 수량 정보 --------------------------------------------------------------------
            String tempItemNameOptionAdd = strOrderItems[0];
            String tempItemQty = strOrderItems[1];
            String tempPrice = strOrderItems[4];
            String tempPriceAmount = strOrderItems[5];
            String tempTaxAmount = "0.0";
//                        if (strOrderItems.length > 7) {
//                            tempTaxAmount = strOrderItems[7];
//                        }
            if (strOrderItems.length > 8) {
                tempTaxAmount = strOrderItems[8];
            }
            String tempTotalAmount = strOrderItems[4];

            GlobalMemberValues.logWrite("saleitemstrarray", "tempItemNameOptionAdd : " + tempItemNameOptionAdd + "\n");

            String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
            String tempItemName = strItemNAmeOptionAdd[0];
            String tempOptionTxt = "";
            String tempAdditionalTxt1 = "";
            String tempAdditionalTxt2 = "";
            String tempItemIdx = "";
            String tempKitchenMemo = "";
            String tempOptionPrice = "";
            String temptemp_additionalprice1 = "";
            String temptemp_additionalprice2 = "";
            String selectedDcExtraAllEach = "";
            String selectedDcExtraType = "";
            String dcextratype = "";
            String dcextravalue = "";
            String selectedDcExtraPrice = "";
            if (strItemNAmeOptionAdd.length > 1) {
                tempOptionTxt = strItemNAmeOptionAdd[1];
                if (strItemNAmeOptionAdd.length > 2) {
                    tempAdditionalTxt1 = strItemNAmeOptionAdd[2];
                }
                if (strItemNAmeOptionAdd.length > 3) {
                    tempAdditionalTxt2 = strItemNAmeOptionAdd[3];
                }
                if (strItemNAmeOptionAdd.length > 4) {
                    tempItemIdx = strItemNAmeOptionAdd[4];
                }
                if (strItemNAmeOptionAdd.length > 6) {
                    tempKitchenMemo = strItemNAmeOptionAdd[6];
                }
                if (strItemNAmeOptionAdd.length > 7) {
                    tempOptionPrice = strItemNAmeOptionAdd[7];
                }
                if (strItemNAmeOptionAdd.length > 8) {
                    temptemp_additionalprice1 = strItemNAmeOptionAdd[8];
                }
                if (strItemNAmeOptionAdd.length > 9) {
                    temptemp_additionalprice2 = strItemNAmeOptionAdd[9];
                }
                // discount / extra
                if (strItemNAmeOptionAdd.length > 10) {
                    selectedDcExtraAllEach = strItemNAmeOptionAdd[10];
                }
                if (strItemNAmeOptionAdd.length > 11) {
                    selectedDcExtraType = strItemNAmeOptionAdd[11];
                }
                if (strItemNAmeOptionAdd.length > 12) {
                    dcextratype = strItemNAmeOptionAdd[12];
                }
                if (strItemNAmeOptionAdd.length > 13) {
                    dcextravalue = strItemNAmeOptionAdd[13];
                }
                if (strItemNAmeOptionAdd.length > 14) {
                    selectedDcExtraPrice = strItemNAmeOptionAdd[14];
                }
            }

            // ---------------------------------------------------------------------------------

            String strQuery = "";

            strQuery = " select idx from temp_item_printing where " +
                    " printcode = '" + paramPrintCode + "' " +
                    " and svcIdx = '" + tempItemIdx + "' " +
                    " and svcName = '" + tempItemName + "' " +
                    " and optionTxt = '" + tempOptionTxt + "' " +
                    " and additionalTxt1 = '" + tempAdditionalTxt1 + "' " +
                    " and additionalTxt2 = '" + tempAdditionalTxt2 + "' " +
                    " and selectedDcExtraPrice = '" + selectedDcExtraPrice + "' " +
                    " and memoToKitchen = '" + tempKitchenMemo + "' ";
//                    " and itemprice = '" + paramPriceAmount + "' ";
            String tempPrintIdx = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);

            // 01132023
            double tempPriceAmountDbl = GlobalMemberValues.getDoubleAtString(tempPriceAmount) * GlobalMemberValues.getIntAtString(tempItemQty);
            tempPriceAmount = tempPriceAmountDbl + "";

            Vector<String> strInsertQueryVec = new Vector<String>();
            if (!GlobalMemberValues.isStrEmpty(tempPrintIdx)) {
                strQuery = " update temp_item_printing set " +
                        " qty = qty + " + GlobalMemberValues.getIntAtString(tempItemQty) + ", " +
                        " itemprice = itemprice + " + GlobalMemberValues.getDoubleAtString(tempPriceAmount) + ", " +
                        " selectedDcExtraPrice = selectedDcExtraPrice + " + GlobalMemberValues.getDoubleAtString(selectedDcExtraPrice) + ", " +
                        " itemtax = itemtax + " + GlobalMemberValues.getDoubleAtString(tempTaxAmount) +
                        " where idx = '" + tempPrintIdx + "' ";
            } else {
                strQuery = " insert into temp_item_printing ( " +
                        " printcode, svcIdx, svcName, optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                        " memoToKitchen, itemprice, itemtax, qty, selectedDcExtraAllEach,selectedDcExtraType,dcextratype,dcextravalue,selectedDcExtraPrice " +
                        " ) values ( " +
                        " '" + paramPrintCode + "', " +
                        " '" + tempItemIdx + "', " +
                        " '" + tempItemName + "', " +
                        " '" + tempOptionTxt + "', " +
                        " '" + tempOptionPrice + "', " +
                        " '" + tempAdditionalTxt1 + "', " +
                        " '" + temptemp_additionalprice1 + "', " +
                        " '" + tempAdditionalTxt2 + "', " +
                        " '" + temptemp_additionalprice2 + "', " +
                        " '" + tempKitchenMemo + "', " +
                        " '" + tempPriceAmount + "', " +
                        " '" + tempTaxAmount + "', " +
                        " '" + GlobalMemberValues.getIntAtString(tempItemQty) + "', " +
                        " '" + selectedDcExtraAllEach + "', " +
                        " '" + selectedDcExtraType + "', " +
                        " '" + dcextratype + "', " +
                        " '" + dcextravalue + "', " +
                        " '" + selectedDcExtraPrice + "' " +
                        " ) ";
            }

            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("printdblogjjj", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                returnValue = false;
            } else {
                returnValue = true;
            }
        }

        return returnValue;
    }

    // 01072023
    public String[] getItemPrintListArr(String paramPrintCode) {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(*) from temp_item_printing " +
                " where printcode = '" + paramPrintCode + "' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select svcIdx, svcName, optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                    " memoToKitchen, itemprice, itemtax, qty, selectedDcExtraAllEach,selectedDcExtraType,dcextratype,dcextravalue,selectedDcExtraPrice " +
                    " from temp_item_printing " +
                    " where printcode = '" + paramPrintCode + "' " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String svcIdx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String svcName = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String optionTxt = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String optionprice = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
                String additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
                String additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(5), 1);
                String additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(6), 1);
                String additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(7), 1);
                String memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(8), 1);
                String itemprice = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(9), 1);
                String itemtax = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(10), 1);
                String qty = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(11), 1);

                // 추가
                String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(12), 1);
                String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(13), 1);
                String dcextratype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(14), 1);
                String dcextravalue = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(15), 1);
                String selectedDcExtraPrice = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(16), 1);

                double d_tempPriceAmount = GlobalMemberValues.getDoubleAtString(itemprice);
                int i_qty = Integer.parseInt(qty);
                d_tempPriceAmount = d_tempPriceAmount / i_qty;
                String itemPrice_divide_qty = GlobalMemberValues.getStringFormatNumber(d_tempPriceAmount,"2");

                double d_tempTax = GlobalMemberValues.getDoubleAtString(itemprice);
                d_tempTax = d_tempTax / i_qty;
                String itemTax_divide_qty = GlobalMemberValues.getStringFormatNumber(d_tempTax,"2");

//                getArr[tableCount] = svcIdx + "-JJJ-" + svcName + "-JJJ-" + optionTxt + "-JJJ-" + optionprice + "-JJJ-" + additionalTxt1 +
//                        "-JJJ-" + additionalprice1 + "-JJJ-" + additionalTxt2 + "-JJJ-" + additionalprice2 + "-JJJ-" + memoToKitchen +
//                        "-JJJ-" + itemprice + "-JJJ-" + itemtax  + "-JJJ-" + qty + "-JJJ-" + "END";


                // 08102023
                // alt name 구하기
                String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicenamealt from salon_storeservice_sub where idx = '" + svcIdx + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                    temp_itemName_alt = "";
                }


                getArr[tableCount] = svcName + "-ANNIETTASU-"
                        + optionTxt + "-ANNIETTASU-"
                        + additionalTxt1 + "-ANNIETTASU-"
                        + additionalTxt2 + "-ANNIETTASU-"
                        + svcIdx + "-ANNIETTASU-"   // temp_itemIdx

                        // 08102023
                        + GlobalMemberValues.getKitchenPrinterNumber(svcIdx) + "-ANNIETTASU-" // tempKitchenPrinterNumber


                        + memoToKitchen + "-ANNIETTASU-"
                        + optionprice + "-ANNIETTASU-"
                        + additionalprice1 + "-ANNIETTASU-"
                        + additionalprice2 + "-ANNIETTASU-"
                        + selectedDcExtraAllEach + "-ANNIETTASU-"       // selectedDcExtraAllEach
                        + selectedDcExtraType + "-ANNIETTASU-"       // selectedDcExtraType
                        + dcextratype + "-ANNIETTASU-"       // dcextratype
                        + dcextravalue + "-ANNIETTASU-"       // dcextravalue
                        + selectedDcExtraPrice + "-ANNIETTASU-"       // selectedDcExtraPrice
                        + "" + "-ANNIETTASU-"       // togodelitype
                        + "" + "-ANNIETTASU-"       // temp_categoryname
                        + "" + "-ANNIETTASU-"       // str_additem
                        + "" + "-ANNIETTASU-"       // labelprintedYN

                        // 08102023
                        + temp_itemName_alt + "-ANNIETTASU-"

                        // detail
                        + "-JJJ-"
                        + qty + "-JJJ-"
                        + "" + "-JJJ-" //tempKitchenPrinterNumber
                        + "" + "-JJJ-" //temp_categoryname
                        + "" + "-JJJ-" //
                        + itemPrice_divide_qty + "-JJJ-" //temp_sPrice
                        + itemprice + "-JJJ-" //temp_sPriceAmount
                        + itemTax_divide_qty + "-JJJ-" //temp_stax
                        + itemtax + "-JJJ-" //temp_sTaxAmount
                        + optionprice + "-JJJ-" //temp_optionprice
                        + additionalprice1 + "-JJJ-" //temp_additionalprice1
                        + additionalprice2 + "-JJJ-" //temp_additionalprice2
                        + "" + "-JJJ-" //togodelitype
                        + "END";


                GlobalMemberValues.logWrite("tablejjjlog", "arr : " + getArr[tableCount] + "\n");

                tableCount++;
            }

            returnValue = getArr;
        }

        return returnValue;
    }


    // 01072023
    public void deleteItemPrintingList(String paramPrintCode) {
        Vector<String> strInsertQueryVec = new Vector<String>();

        String strQuery = " delete from temp_item_printing " +
                " where printcode = '" + paramPrintCode + "' ";

        strInsertQueryVec.addElement(strQuery);
        // 트랜잭션으로 DB 처리한다.
        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
        }
    }
}
