package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import java.util.Vector;

public class SaleDataIns {
    Activity mActivity;
    Context context;
    DatabaseInit dbInit;

    public SaleDataIns(Activity paramActivity, Context paramContext, DatabaseInit paramDbInit) {
        mActivity = paramActivity;
        context = paramContext;
        dbInit = paramDbInit;
    }

    // 03042024
    // split void return 관련
    public void setVoidReturnDataIns(String paramVoidReturnType, String paramSalesCode, String paramHoldCode, String paramAmount, String paramPaidType, String paramBillPaidVoidIdx) {

        Vector<String> strInsertQueryVec = new Vector<String>();

        String strQuery = "";

        // 클라우드 업로드 여부
        int isCloudUpload = 0;

        String salesCodeForVoid = GlobalMemberValues.makeVoidCode();

        String voidReturnStr = "";

        // 상태 (1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소)
        int item_status = 1;
        switch (paramVoidReturnType) {
            case "V" : {
                item_status = 1;
                salesCodeForVoid = GlobalMemberValues.makeVoidCode();
                voidReturnStr = "PARTIAL VOID";
                break;
            }
            case "C" : {
                item_status = 3;
                salesCodeForVoid = GlobalMemberValues.makeReturnCode();
                voidReturnStr = "PARTIAL RETURN";
                break;
            }
        }

        strQuery = "insert into salon_sales_detail (" +
                " salesCode, holdCode, reservationCode, sidx, stcode, " +
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
                " eachDiscountExtraPrice, eachDiscountExtraType, " +
                " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
                " ) values ( " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', " +
                " '" + paramHoldCode + "', " +
                " '" + "" + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +

                " '" + "0" + "', " +
                " '" + voidReturnStr + "', " +

                " '" + "0" + "', " +
                " '" + voidReturnStr + "', " +
                " '" + "" + "', " +
                " '" + "" + "', " +
                " '" + "0" + "', " +

                " '" + "1" + "', " +
                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +

                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +

                " '-" + "0" + "', " +
                " '-" + "0" + "', " +

                " '-" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +


                // " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                " '-" + 0 + "', " +
                " '-" + 0 + "', " +
                " '-" + 0 + "', " +
                " '-" + 0 + "', " +

                // " pointRatio, point, pointAmount, " +
                " '-" + 0 + "', " +
                " '-" + 0 + "', " +
                " '-" + 0 + "', " +

                // " customerId, customerName, customerPhone, customerPosCode, " +
                " '" + "" + "', " +
                " '" + "" + "', " +
                " '" + "" + "', " +
                " '" + "" + "', " +

                // " employeeIdx, employeeName, " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +

                // " serverIdx, serverName, " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +

                // " giftcardNumberToSave, giftcardSavePriceToSave, " +
                " '" + "" + "', " +
                " '-" + 0 + "', " +

                // " couponNumber, " +
                " '" + "" + "', " +

                // " eachDiscountExtraPrice, eachDiscountExtraType, " +
                " '-" + 0 + "', " +
                " '" + "" + "', " +

                // " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
                " '" + "0" + "', " +
                " '" + "N" + "', " +
                " '" + "1" + "', " +
                " '" + isCloudUpload + "', " +
                " '" + item_status + "', " +
                " '" + paramSalesCode + "', " +
                " '" + "" + "', " +

                // " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
                " '" + "" + "', " +
                " '-" + "0" + "', " +
                " '" + "" + "', " +
                " '-" + "0" + "', " +
                " '" + "" + "', " +
                " '-" + "0" + "' " +
                ")";
        // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
        strInsertQueryVec.addElement(strQuery);




        // 세일일시를 지정했을 경우
        String sqlQuery_add1 = "";
        String sqlQuery_add2 = "";
        if (GlobalMemberValues.isDifferentSaleDateToday()) {
            String tempSearchDate = "";
            String[] tempSettingSaleDateArr = GlobalMemberValues.SETTING_SALE_DATE.split("-");
            if (tempSettingSaleDateArr.length > 0) {
                tempSearchDate = tempSettingSaleDateArr[2] + "-" + tempSettingSaleDateArr[0] + "-" + tempSettingSaleDateArr[1];

                sqlQuery_add1 = ", saleDate ";
                sqlQuery_add2 = ", DATETIME('" + tempSearchDate + " 23:59:59') ";
            }
        }

        int isTotalCashUse = 1;
        double useTotalCashAmount = 0;
        double useTotalCashRatio = 0;
        if (paramPaidType.toUpperCase().equals("CASH")) {
            isTotalCashUse = 0;
            useTotalCashAmount = GlobalMemberValues.getDoubleAtString(paramAmount);
            useTotalCashRatio = 1;
        }

        int isTotalCardUse = 1;
        double useTotalCardAmount = 0;
        double useTotalCardRatio = 0;
        if (paramPaidType.toUpperCase().equals("CARD")) {
            isTotalCardUse = 0;
            useTotalCardAmount = GlobalMemberValues.getDoubleAtString(paramAmount);
            useTotalCardRatio = 1;
        }

        // 상태 (1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소)
        int sale_status = 1;

        String getQuery = "select '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', holdCode, reservationCode, sidx, stcode, " +
                " 1, " +
                " " + GlobalMemberValues.getDoubleAtString(paramAmount) + ", 0, " + GlobalMemberValues.getDoubleAtString(paramAmount) + ", " +
                " " + isTotalCashUse + ", " + isTotalCardUse + ", isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " " + useTotalCashAmount + ", " + useTotalCardAmount + ", useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " " + useTotalCashRatio + ", " + useTotalCardRatio + ", useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " commissionAmount, pointAmount, " +
                " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo, " +
                " giftcardNumberUsed, giftcardPriceUsed, " +
                " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                " cardTidNumber, " +
                " '" + "0" + "', " +
                " '" + sale_status + "', " +
                " 0, " +
                " employeeIdx, employeeName, " +
                " 0, " +
                " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                " tablename, tablepeoplecnt, salepg_ip, " +

                // 07202024
                " pgdevicenum, " +

                " '" + voidReturnStr + "'" + sqlQuery_add2 +
                " from salon_sales where salesCode = '" + paramSalesCode + "' " ;

        strQuery = "insert into salon_sales (" +
                " salesCode, holdCode, reservationCode, sidx, stcode, " +
                " qty, " +
                " salesBalPriceAmount, taxAmount, totalAmount, " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " commissionAmount, pointAmount, " +
                " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo, " +
                " giftcardNumberUsed, giftcardPriceUsed, " +
                " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                " cardTidNumber, " +
                " isCloudUpload, " +
                " status, " +
                " changeMoney, " +
                " employeeIdx, employeeName, " +
                " deliverypickupfee, " +
                " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                " tablename, tablepeoplecnt, salepg_ip, " +

                // 07202024
                " pgdevicenum, " +

                " cancelreason " + sqlQuery_add1 +
                " ) " + getQuery;

        strInsertQueryVec.addElement(strQuery);



        // salon_sales_card
        if (paramPaidType.toUpperCase().equals("CARD")) {
            Cursor salonSalesCardCursor;
            strQuery = "select priceAmount, cardRefNumber, cardCom, " +
                    " insertSwipeKeyin, cardLastFourDigitNumbers, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                    " from salon_sales_card " +
                    " where idx = '" + paramBillPaidVoidIdx + "' ";
            salonSalesCardCursor = dbInit.dbExecuteRead(strQuery);
            while (salonSalesCardCursor.moveToNext()) {
                String salonSalesCard_priceAmountStr = "";
                double salonSalesCard_priceAmount = salonSalesCardCursor.getDouble(0);
                String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);
                String salonSalesCard_cardCom = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(2), 1);
                String salonSalesCard_insertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(3), 1);
                String salonSalesCard_cardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(4), 1);
                String salonSalesCard_cardEmvAid = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(5), 1);
                String salonSalesCard_cardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(6), 1);
                String salonSalesCard_cardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(7), 1);

                if (salonSalesCard_priceAmount > 0) {
                    salonSalesCard_priceAmountStr = "-" + salonSalesCard_priceAmount;
                } else {
                    salonSalesCard_priceAmountStr = "0.00";
                }

                strQuery = "insert into salon_sales_card (" +
                        " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                        " cardLastFourDigitNumbers, cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                        " ) values ( " +
                        "'" + salesCodeForVoid + "', " +
                        "'" + "" + "', " +
                        "'" + GlobalMemberValues.STORE_INDEX + "', " +
                        "'" + GlobalMemberValues.STATION_CODE + "', " +
                        "'" + salonSalesCard_cardCom + "', " +
                        "'" + salonSalesCard_priceAmountStr + "', " +
                        "'" + salonSalesCard_insertSwipeKeyin + "', " +
                        "'" + "1" + "', " +
                        "'" + salonSalesCard_cardLastFourDigitNumbers + "', " +
                        "'" + salonSalesCard_cardRefNumber + "', " +
                        "'" + salonSalesCard_cardEmvAid + "', " +
                        "'" + salonSalesCard_cardEmvTsi + "', " +
                        "'" + salonSalesCard_cardEmvTvr + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strQuery);
            }
        }

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("partialvoidlog", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우 Sale History 리스트 리로드
            // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
            GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
        }
    }

    
    
    // 03062024
    // Surcharge 관련
//    public void setSurchargeDataIns(String paramSalesCode, String paramAmount) {
//
//        String strQuery = "";
//
//        // 클라우드 업로드 여부
//        int isCloudUpload = 0;
//
//        String salesCodeStr = paramSalesCode;
//        String surchargeName = GlobalMemberValues.getAddPayName();
//        if (GlobalMemberValues.isStrEmpty(surchargeName)) {
//            surchargeName = "SURCHARGE";
//        }
//
//        int item_status = 0;
//
//        // 고객아이디
//        String sales_customerId = "";
//        // 고객이름
//        String sales_customerName = "";
//        // 고객전화번호
//        String sales_customerPhone = "";
//        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
//            sales_customerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
//            sales_customerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
//            sales_customerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
//        }
//
//        String sales_customerPosCode = "";
//        if (!GlobalMemberValues.isStrEmpty(sales_customerId)) {
//            String tempQuery_ = "select pos_mem_code from salon_member where uid = '" + sales_customerId + "' ";
//            sales_customerPosCode = dbInit.dbExecuteReadReturnString(tempQuery_);
//        }
//
//
//        strQuery = "insert into salon_sales_detail (" +
//                " salesCode, holdCode, reservationCode, sidx, stcode, " +
//                " categoryCode, categoryName, " +
//                " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
//                " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
//                " salesBalPrice, salesBalPriceAmount, " +
//                " tax, taxAmount, " +
//                " totalAmount, " +
//                " commissionRatioType, commissionRatio, commission, commissionAmount, " +
//                " pointRatio, point, pointAmount, " +
//                " customerId, customerName, customerPhone, customerPosCode, " +
//                " employeeIdx, employeeName, " +
//                " serverIdx, serverName, " +
//                " giftcardNumberToSave, giftcardSavePriceToSave, " +
//                " couponNumber, " +
//                " eachDiscountExtraPrice, eachDiscountExtraType, " +
//                " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
//                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
//                " ) values ( " +
//
//                // " salesCode, holdCode, reservationCode, sidx, stcode, "
//                " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeStr, 0) + "', " +
//                " '" + MainMiddleService.mHoldCode + "', " +
//                " '" + "" + "', " +
//                " '" + GlobalMemberValues.STORE_INDEX + "', " +
//                " '" + GlobalMemberValues.STATION_CODE + "', " +
//
//                // " categoryCode, categoryName, "
//                " '" + surchargeName + "', " +
//                " '" + surchargeName + "', " +
//
//                // " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, "
//                " '" + "0" + "', " +
//                " '" + surchargeName + "', " +
//                " '" + "" + "', " +
//                " '" + "" + "', " +
//                " '" + "0" + "', " +
//
//                // " qty, salesOrgPrice, salesPrice, salesPriceAmount, "
//                " '" + "1" + "', " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//
//                // " salesBalPrice, salesBalPriceAmount, " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//
//                // " tax, taxAmount, " +
//                " '" + "0" + "', " +
//                " '" + "0" + "', " +
//
//                // " totalAmount, " +
//                " '" + GlobalMemberValues.getDoubleAtString(paramAmount) + "', " +
//
//                // " commissionRatioType, commissionRatio, commission, commissionAmount, " +
//                " '" + 0 + "', " +
//                " '" + 0 + "', " +
//                " '" + 0 + "', " +
//                " '" + 0 + "', " +
//
//                // " pointRatio, point, pointAmount, " +
//                " '" + 0 + "', " +
//                " '" + 0 + "', " +
//                " '" + 0 + "', " +
//
//                // " customerId, customerName, customerPhone, customerPosCode, " +
//                " '" + sales_customerId + "', " +
//                " '" + sales_customerName + "', " +
//                " '" + sales_customerPhone + "', " +
//                " '" + sales_customerPosCode + "', " +
//
//                // " employeeIdx, employeeName, " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
//
//                // " serverIdx, serverName, " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
//
//                // " giftcardNumberToSave, giftcardSavePriceToSave, " +
//                " '" + "" + "', " +
//                " '" + 0 + "', " +
//
//                // " couponNumber, " +
//                " '" + "" + "', " +
//
//                // " eachDiscountExtraPrice, eachDiscountExtraType, " +
//                " '" + 0 + "', " +
//                " '" + "" + "', " +
//
//                // " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
//                " '" + "0" + "', " +
//                " '" + "N" + "', " +
//                " '" + "1" + "', " +
//                " '" + isCloudUpload + "', " +
//                " '" + item_status + "', " +
//                " '" + paramSalesCode + "', " +
//                " '" + "" + "', " +
//
//                // " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
//                " '" + "" + "', " +
//                " '-" + "0" + "', " +
//                " '" + "" + "', " +
//                " '-" + "0" + "', " +
//                " '" + "" + "', " +
//                " '-" + "0" + "' " +
//                ")";
//
//        // salon_sales_detail 입력쿼리를 Payment 의 strInsertQueryVec 에 담는다.
//
//        Payment.strInsertQueryVec.addElement(strQuery);
//    }


    // 03062024
    // Surcharge 관련
    public static void setSurchargeDataIns(String paramAmount) {
        if (GlobalMemberValues.getDoubleAtString(paramAmount) > 0) {
            String addpayname = GlobalMemberValues.getAddPayName();
            if (GlobalMemberValues.isStrEmpty(addpayname)) {
                addpayname = "SURCHARGE";
            }

            // 장바구니에 Add Pay 가 담겨져 있지 않는 경우에만 아래 실행
            int tempCnt = 0;
            for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
                String tempItemName = tempSaleCart.mSvcName;
                if (tempItemName.equals(addpayname)) {
                    tempCnt++;
                }
            }

            GlobalMemberValues.logWrite("surchargellljjjlog", "addpayname : " + addpayname + "\n");

            GlobalMemberValues.logWrite("surchargellljjjlog", "tempCnt : " + tempCnt + "\n");

            if (tempCnt > 0) {
                return;
            }

            String tempEmpIdx = "";
            String tempEmpName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                tempEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                tempEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }

            String tempCustomerId = "";
            String tempCustomerName = "";
            String tempCustomerPhone = "";
            if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                tempCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                tempCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                tempCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
            }

            String addpay_val = GlobalMemberValues.getStringFormatNumber(paramAmount, "2");

            String paramsString[] = {
                    "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                    "0", "0",
                    addpayname, "", "",
                    addpay_val, addpay_val, "", "",
                    "0", "", "0",
                    "", "N",
                    tempCustomerId, tempCustomerName, tempCustomerPhone, "0", tempEmpIdx, tempEmpName, "N", addpayname
            };

            MainMiddleService.mQuickSaleYN = "Y";
            MainMiddleService.insertTempSaleCart(paramsString);

            GlobalMemberValues.logWrite("surchargellljjjlog", "여기까지 진행" + "\n");
        }
    }



}

