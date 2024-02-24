package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

import java.util.Vector;

public class InsertSaleDataInServer {

    public void insDataInServer() {
        if (MainActivity.mDbInit != null) {
            DatabaseInit dbinit = MainActivity.mDbInit;

            String strQuery = "";

            String schDateStr = "";
            // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
            // 업로드 할 데이터 기준일
            String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -1);
            String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 1);

            schDateStr = " strftime('%Y-%m-%d', saledate) between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
            // -------------------------------------------------------------------------------------------------------------------------------------------------

            strQuery = "select salesCode, holdcode " +
                    " from salon_sales " +
                    " where " + schDateStr +
                    " order by idx asc";
            GlobalMemberValues.logWrite("uploaddatalog", "sql : " + strQuery + "\n");

            int salesCnt = 0;
            String tablenameStr = "";
            String columnsStr = "";
            Vector<String> mInsVec = new Vector<String>();
            Cursor salesCursor = dbinit.dbExecuteRead(strQuery);
            Cursor cursor;
            while (salesCursor.moveToNext()) {
                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salesCursor.getString(0), 1);
                String tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(salesCursor.getString(1), 1);

                salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from salon_sales where salescode = '" + tempSalesCode + "' "));
                if (salesCnt == 0) {


                    // salon_sales 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales";
                    columnsStr = " salesCode, holdCode, reservationCode, sidx, stcode, " +
                            " qty, " +
                            " salesBalPriceAmount, taxAmount, totalAmount, " +
                            " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                            " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                            " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                            " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                            " commissionAmount, pointAmount, " +
                            " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                            " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                            " deliverytakeaway, " +
                            " customermemo, " +
                            " giftcardNumberUsed, giftcardPriceUsed, " +
                            " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                            " cardTidNumber, " +
                            " isCloudUpload, " +
                            " status, " +
                            " changeMoney, " +
                            " employeeIdx, employeeName, " +
                            " serverIdx, serverName, " +
                            " deliverypickupfee, " +
                            " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                            " tablename, tablepeoplecnt, salepg_ip, togotype, saleDate ";
                    strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                    cursor = dbinit.dbExecuteRead(strQuery);
                    while (cursor.moveToNext()) {
                        strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                " ) values ( " +
                                " '" + cursor.getString(0) + "', " +
                                " '" + cursor.getString(1) + "', " +
                                " '" + cursor.getString(2) + "', " +
                                " '" + cursor.getString(3) + "', " +
                                " '" + cursor.getString(4) + "', " +
                                " '" + cursor.getString(5) + "', " +
                                " '" + cursor.getString(6) + "', " +
                                " '" + cursor.getString(7) + "', " +
                                " '" + cursor.getString(8) + "', " +
                                " '" + cursor.getString(9) + "', " +
                                " '" + cursor.getString(10) + "', " +
                                " '" + cursor.getString(11) + "', " +
                                " '" + cursor.getString(12) + "', " +
                                " '" + cursor.getString(13) + "', " +
                                " '" + cursor.getString(14) + "', " +
                                " '" + cursor.getString(15) + "', " +
                                " '" + cursor.getString(16) + "', " +
                                " '" + cursor.getString(17) + "', " +
                                " '" + cursor.getString(18) + "', " +
                                " '" + cursor.getString(19) + "', " +
                                " '" + cursor.getString(20) + "', " +
                                " '" + cursor.getString(21) + "', " +
                                " '" + cursor.getString(22) + "', " +
                                " '" + cursor.getString(23) + "', " +
                                " '" + cursor.getString(24) + "', " +
                                " '" + cursor.getString(25) + "', " +
                                " '" + cursor.getString(26) + "', " +
                                " '" + cursor.getString(27) + "', " +
                                " '" + cursor.getString(28) + "', " +
                                " '" + cursor.getString(29) + "', " +
                                " '" + cursor.getString(30) + "', " +
                                " '" + cursor.getString(31) + "', " +
                                " '" + cursor.getString(32) + "', " +
                                " '" + cursor.getString(33) + "', " +
                                " '" + cursor.getString(34) + "', " +
                                " '" + cursor.getString(35) + "', " +
                                " '" + cursor.getString(36) + "', " +
                                " '" + cursor.getString(37) + "', " +
                                " '" + cursor.getString(38) + "', " +
                                " '" + cursor.getString(39) + "', " +
                                " '" + cursor.getString(40) + "', " +
                                " '" + cursor.getString(41) + "', " +
                                " '" + cursor.getString(42) + "', " +
                                " '" + cursor.getString(43) + "', " +
                                " '" + cursor.getString(44) + "', " +
                                " '" + cursor.getString(45) + "', " +
                                " '" + cursor.getString(46) + "', " +
                                " '" + cursor.getString(47) + "', " +
                                " '" + cursor.getString(48) + "', " +
                                " '" + cursor.getString(49) + "', " +
                                " '" + cursor.getString(50) + "', " +
                                " '" + cursor.getString(51) + "', " +
                                " '" + cursor.getString(52) + "', " +
                                " '" + cursor.getString(53) + "', " +
                                " '" + cursor.getString(54) + "', " +
                                " '" + cursor.getString(55) + "', " +
                                " '" + cursor.getString(56) + "', " +
                                " '" + cursor.getString(57) + "', " +
                                " '" + cursor.getString(58) + "', " +
                                " '" + cursor.getString(59) + "', " +
                                " '" + cursor.getString(60) + "', " +
                                " '" + cursor.getString(61) + "', " +
                                " '" + cursor.getString(62) + "', " +
                                " '" + cursor.getString(63) + "', " +
                                " '" + cursor.getString(64) + "', " +
                                " '" + cursor.getString(65) + "', " +
                                " '" + cursor.getString(66) + "', " +
                                " '" + cursor.getString(67) + "', " +
                                " '" + cursor.getString(68) + "' " +
                                " ) ";

                        mInsVec.add(strQuery);
                    }
                    cursor.close();
                    // salon_sales 저장 ---------------------------------------------------------------------------------------



                    // salon_sales_detail 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_detail";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, holdCode, reservationCode, sidx, stcode, " +
                                " categoryCode, categoryName, " +
                                " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
                                " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
                                " salesBalPrice, salesBalPriceAmount, " +
                                " tax, taxAmount, " +
                                " totalAmount, " +
                                " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                                " pointRatio, point, pointAmount, " +
                                " customerId, customerName, customerPhone, customerPosCode, " +
                                " employeeIdx, employeeName, " +
                                " serverIdx, serverName, " +
                                " giftcardNumberToSave, giftcardSavePriceToSave, " +
                                " couponNumber, " +
                                " eachDiscountExtraPrice, eachDiscountExtraType, eachDiscountExtraStr, " +
                                " dcextraforreturn, " +
                                " saveType, isQuickSale, isSale, status, isCloudUpload,  " +
                                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                                " checkcompany, memoToKitchen, discountbuttonname, " +
                                " tableidx, mergednum, subtablenum, billnum, tablename, togodelitype, togotype, labelPrintedYN, kitchenPrintedYN, wmodyn, saleDate ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "', " +
                                    " '" + cursor.getString(6) + "', " +
                                    " '" + cursor.getString(7) + "', " +
                                    " '" + cursor.getString(8) + "', " +
                                    " '" + cursor.getString(9) + "', " +
                                    " '" + cursor.getString(10) + "', " +
                                    " '" + cursor.getString(11) + "', " +
                                    " '" + cursor.getString(12) + "', " +
                                    " '" + cursor.getString(13) + "', " +
                                    " '" + cursor.getString(14) + "', " +
                                    " '" + cursor.getString(15) + "', " +
                                    " '" + cursor.getString(16) + "', " +
                                    " '" + cursor.getString(17) + "', " +
                                    " '" + cursor.getString(18) + "', " +
                                    " '" + cursor.getString(19) + "', " +
                                    " '" + cursor.getString(20) + "', " +
                                    " '" + cursor.getString(21) + "', " +
                                    " '" + cursor.getString(22) + "', " +
                                    " '" + cursor.getString(23) + "', " +
                                    " '" + cursor.getString(24) + "', " +
                                    " '" + cursor.getString(25) + "', " +
                                    " '" + cursor.getString(26) + "', " +
                                    " '" + cursor.getString(27) + "', " +
                                    " '" + cursor.getString(28) + "', " +
                                    " '" + cursor.getString(29) + "', " +
                                    " '" + cursor.getString(30) + "', " +
                                    " '" + cursor.getString(31) + "', " +
                                    " '" + cursor.getString(32) + "', " +
                                    " '" + cursor.getString(33) + "', " +
                                    " '" + cursor.getString(34) + "', " +
                                    " '" + cursor.getString(35) + "', " +
                                    " '" + cursor.getString(36) + "', " +
                                    " '" + cursor.getString(37) + "', " +
                                    " '" + cursor.getString(38) + "', " +
                                    " '" + cursor.getString(39) + "', " +
                                    " '" + cursor.getString(40) + "', " +
                                    " '" + cursor.getString(41) + "', " +
                                    " '" + cursor.getString(42) + "', " +
                                    " '" + cursor.getString(43) + "', " +
                                    " '" + cursor.getString(44) + "', " +
                                    " '" + cursor.getString(45) + "', " +
                                    " '" + cursor.getString(46) + "', " +
                                    " '" + cursor.getString(47) + "', " +
                                    " '" + cursor.getString(48) + "', " +
                                    " '" + cursor.getString(49) + "', " +
                                    " '" + cursor.getString(50) + "', " +
                                    " '" + cursor.getString(51) + "', " +
                                    " '" + cursor.getString(52) + "', " +
                                    " '" + cursor.getString(53) + "', " +
                                    " '" + cursor.getString(54) + "', " +
                                    " '" + cursor.getString(55) + "', " +
                                    " '" + cursor.getString(56) + "', " +
                                    " '" + cursor.getString(57) + "', " +
                                    " '" + cursor.getString(58) + "', " +
                                    " '" + cursor.getString(59) + "', " +
                                    " '" + cursor.getString(60) + "', " +
                                    " '" + cursor.getString(61) + "', " +
                                    " '" + cursor.getString(62) + "', " +
                                    " '" + cursor.getString(63) + "', " +
                                    " '" + cursor.getString(64) + "', " +
                                    " '" + cursor.getString(65) + "', " +
                                    " '" + cursor.getString(66) + "', " +
                                    " '" + cursor.getString(67) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_detail 저장 ---------------------------------------------------------------------------------------





                    // salon_sales_customerpagernumber 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_customerpagernumber";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " scode, sidx, stcode, salesCode, customerpagernumber ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_customerpagernumber 저장 ---------------------------------------------------------------------------------------




                    // salon_sales_kitchenprintingdata_json 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_kitchenprintingdata_json";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, scode, sidx, stcode, jsonstr, printedyn ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_kitchenprintingdata_json 저장 ---------------------------------------------------------------------------------------





                    // salon_sales_card 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_card";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                                " employeeIdx, employeeName,  " +
                                " serverIdx, serverName ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "', " +
                                    " '" + cursor.getString(6) + "', " +
                                    " '" + cursor.getString(7) + "', " +
                                    " '" + cursor.getString(8) + "', " +
                                    " '" + cursor.getString(9) + "', " +
                                    " '" + cursor.getString(10) + "', " +
                                    " '" + cursor.getString(11) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_card 저장 ---------------------------------------------------------------------------------------




                    // salon_sales_receipt_json 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_receipt_json";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, scode, sidx, stcode, jsonstr ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_receipt_json 저장 ---------------------------------------------------------------------------------------





                    // salon_sales_kitchen_json 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "salon_sales_kitchen_json";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, scode, sidx, stcode, jsonstr, isCloudUpload ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // salon_sales_kitchen_json 저장 ---------------------------------------------------------------------------------------





                    // card_processing_data 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "card_processing_data";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where holdcode = '" + tempHoldCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " holdcode, sidx, stcode, " +
                                " customerId, customerName, employeeIdx, employeeName," +
                                " menuitems_list, menuitems_all, amount, procestype, resultcode, resultmsg ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where holdcode = '" + tempHoldCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "', " +
                                    " '" + cursor.getString(6) + "', " +
                                    " '" + cursor.getString(7) + "', " +
                                    " '" + cursor.getString(8) + "', " +
                                    " '" + cursor.getString(9) + "', " +
                                    " '" + cursor.getString(10) + "', " +
                                    " '" + cursor.getString(11) + "', " +
                                    " '" + cursor.getString(12) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // card_processing_data 저장 ---------------------------------------------------------------------------------------





                    // bill_list 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "bill_list";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where holdcode = '" + tempHoldCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " holdcode, sidx, stcode, billamount, tableidx, cartidxs, billsplittype, gratuityamt, subtotalamt, taxamt, totalamountAmt, dcextraAmt ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where holdcode = '" + tempHoldCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "', " +
                                    " '" + cursor.getString(6) + "', " +
                                    " '" + cursor.getString(7) + "', " +
                                    " '" + cursor.getString(8) + "', " +
                                    " '" + cursor.getString(9) + "', " +
                                    " '" + cursor.getString(10) + "', " +
                                    " '" + cursor.getString(11) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // bill_list 저장 ---------------------------------------------------------------------------------------





                    // bill_list_paid 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "bill_list_paid";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salescode, holdcode, sidx, stcode, billidx, paidamount, paytype, changeamount, ordernum, cardsalesidx, billcode ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "', " +
                                    " '" + cursor.getString(6) + "', " +
                                    " '" + cursor.getString(7) + "', " +
                                    " '" + cursor.getString(8) + "', " +
                                    " '" + cursor.getString(9) + "', " +
                                    " '" + cursor.getString(10) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // bill_list_paid 저장 ---------------------------------------------------------------------------------------






                    // bill_list_receipt_json 저장 ---------------------------------------------------------------------------------------
                    tablenameStr = "bill_list_receipt_json";
                    salesCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select count(*) from " + tablenameStr + " where salescode = '" + tempSalesCode + "' "));
                    if (salesCnt == 0) {
                        columnsStr = " salesCode, scode, sidx, stcode, jsonstr, billcode ";
                        strQuery = " select " + columnsStr + " from " + tablenameStr + " where salescode = '" + tempSalesCode + "' ";
                        cursor = dbinit.dbExecuteRead(strQuery);
                        while (cursor.moveToNext()) {
                            strQuery = " insert into " + tablenameStr + " ( " + columnsStr +
                                    " ) values ( " +
                                    " '" + cursor.getString(0) + "', " +
                                    " '" + cursor.getString(1) + "', " +
                                    " '" + cursor.getString(2) + "', " +
                                    " '" + cursor.getString(3) + "', " +
                                    " '" + cursor.getString(4) + "', " +
                                    " '" + cursor.getString(5) + "' " +
                                    " ) ";

                            mInsVec.add(strQuery);
                        }
                        cursor.close();
                    }
                    // bill_list_receipt_json 저장 ---------------------------------------------------------------------------------------





                    for (String tempQuery : mInsVec) {
                        GlobalMemberValues.logWrite("insmssqldbjjj", "query : " + tempQuery + "\n");
                    }
                    String returnValue = MssqlDatabase.executeTransaction(mInsVec);




                }
            }
        }
    }
}
