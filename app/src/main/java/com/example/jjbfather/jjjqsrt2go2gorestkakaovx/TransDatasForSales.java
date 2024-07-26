package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

public class TransDatasForSales {
    public TransDatasForSales() {
    }

    public String transDatas() {
        String returnData = "";

        String[] tablesArr = {
                "salon_sales",
                "salon_sales_detail",
                "salon_storegiftcard_number",
                "salon_storegiftcard_number_history",
                "member_mileage",
                "salon_sales_customerpagernumber",
                "salon_sales_kitchenprintingdata_json",
                "salon_sales_card",
                "member_salevisit",
                "temp_salecart_del2",
                "salon_sales_receipt_json",
                "salon_sales_kitchen_json",
                "bill_list_paid",
                "bill_list_receipt_json"
        };


        String[] uniqueColumnArr = new String[tablesArr.length];
        String[] orderbyColumnArr = new String[tablesArr.length];


        // salon_sales S -------------------------------------------
        String[] salon_sales_arr = {
                "salesCode",
                "holdCode",
                "reservationCode",

                "sidx",
                "stcode",

                "qty",

                "salesBalPriceAmount",
                "taxAmount",
                "totalAmount",

                "isTotalCashUse",
                "isTotalCardUse",
                "isTotalGiftCardUse",
                "isTotalCheckUse",
                "isTotalPointUse",

                "useTotalCashAmount",
                "useTotalCardAmount",
                "useTotalGiftCardAmount",
                "useTotalCheckAmount",
                "useTotalPointAmount",

                "useTotalCashRatio",
                "useTotalCardRatio",
                "useTotalGiftCardRatio",
                "useTotalCheckRatio",
                "useTotalPointRatio",

                "useTotalCashAmountAfterReturned",
                "useTotalCardAmountAfterReturned",
                "useTotalGiftCardAmountAfterReturned",
                "useTotalCheckAmountAfterReturned",
                "useTotalPointAmountAfterReturned",

                "commissionAmount",
                "pointAmount",

                "customerId",
                "customerName",
                "customerPhone",
                "customerPosCode",

                "customerAddr1",
                "customerAddr2",
                "customerCity",
                "customerState",
                "customerZip",
                "customermemo",

                "deliveryday",
                "deliverytime",
                "deliverydate",
                "deliverytakeaway",

                "giftcardNumberUsed",
                "giftcardPriceUsed",

                "totalDiscountExtraPrice",
                "totalDiscountPrice",
                "totalExtraPrice",
                "eachDiscountExtraPrice",
                "allDiscountExtraPrice",
                "allDiscountExtraStr",

                "cardTidNumber",

                "isCloudUpload",
                "delyn",
                "saleDate",
                "status",
                "deliveryStatus",
                "takeawayStatus",
                "changeMoney",
                "receiptJson",
                "kitchenprintedyn",

                "employeeIdx",
                "employeeName",

                "severIdx",
                "serverName",

                "cashoutNum",

                "endofdayNum",

                "deliverypickupfee",

                "checkcompany",

                "phoneorder",
                "customerordernumber",
                "customerpagernumber",

                "returneddeliverypickupfee",
                "returnedtip",

                "tablename",
                "tablepeoplecnt",

                "customerEmail",

                "salepg_ip",

                "isCloudUpload2",

                "cancelreason",

                "togotype"
        };

        uniqueColumnArr[0] = "salescode";
        orderbyColumnArr[0] = "idx";
        // salon_sales E -------------------------------------------


        // salon_sales_detail S -------------------------------------------
        String[] salon_sales_detail_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                // 각종 코드값 관련
                "salesCode",
                "holdCode",
                "reservationCode",


                "sidx",
                "stcode",
                "categoryCode",
                "categoryName",
                "itemIdx",
                "itemName",
                "itemFileName",
                "itemFilePath",
                "servicePositionNo",
                "qty",
                "salesOrgPrice",

                "salesPrice",
                "salesPriceAmount",


                "salesBalPrice",
                "salesBalPriceAmount",

                "tax",
                "taxAmount",

                "totalAmount",

                "commissionRatioType",
                "commissionRatio",
                "commission",
                "commissionAmount",

                "pointRatio",
                "point",
                "pointAmount",

                "customerId",
                "customerName",
                "customerPhone",
                "customerPosCode",

                "employeeIdx",
                "employeeName",

                "severIdx",
                "serverName",

                "giftcardNumberToSave",
                "giftcardSavePriceToSave",

                "couponNumber",

                "eachDiscountExtraPrice",
                "eachDiscountExtraType",
                "eachDiscountExtraStr",


                "dcextraforreturn",

                "saveType",
                "isQuickSale",
                "isSale",

                "isCloudUpload",

                "status",
                "delyn",
                "saleDate",
                "parentSalesCode",
                "parentSalesIdx",
                "returnCode",

                "optionTxt",
                "optionprice",

                "additionalTxt1",
                "additionalprice1",

                "additionalTxt2",
                "additionalprice2",

                "cashoutNum",

                "endofdayNum",

                "checkcompany",

                "memoToKitchen",

                "discountbuttonname",

                "tableidx",
                "mergednum",
                "subtablenum",
                "billnum",
                "tablename",

                "frommssql",

                "togodelitype",

                "togotype",

                "kitchenPrintedYN",
                "labelPrintedYN",

                "wmodyn",
                "wmodresult",
                "wmodresultmsg",
                "wmurl"
        };

        uniqueColumnArr[1] = "";
        orderbyColumnArr[1] = "idx";
        // salon_sales_detail E -------------------------------------------


        // salon_storegiftcard_number S -------------------------------------------
        String[] salon_storegiftcard_number_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "sidx",
                "gcNumber",
                "customerId",
                "customerName",
                "remainingPrice",
                "wdate"
        };

        uniqueColumnArr[2] = "";
        orderbyColumnArr[2] = "idx";
        // salon_storegiftcard_number E -------------------------------------------


        // salon_storegiftcard_number_history S -------------------------------------------
        String[] salon_storegiftcard_number_history_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "sidx",
                "gcNumber",
                "saleItemIdx",
                "empIdx",
                "empName",
                "serverIdx",
                "serverName",
                "customerId",
                "customerName",
                "addUsePrice",
                "addUseType",
                "addUseMemo",
                "salesCode",
                "codeforupload",
                "wdate",
                "isCloudUpload",
                "firstsaveyn",
                "giftcardname"
        };

        uniqueColumnArr[3] = "";
        orderbyColumnArr[3] = "idx";
        // salon_storegiftcard_number_history E -------------------------------------------


        // member_mileage S -------------------------------------------
        String[] member_mileage_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "mdate",
                "contents",
                "mileage",
                "uid",
                "mcase",
                "seno",
                "membershipcardno",
                "sid",
                "savetype",
                "isCloudUpload",
                "codeforupload",
                "idxfromweb"
        };

        uniqueColumnArr[4] = "";
        orderbyColumnArr[4] = "idx";
        // member_mileage E -------------------------------------------


        // salon_sales_customerpagernumber S -------------------------------------------
        String[] salon_sales_customerpagernumber_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "scode",
                "sidx",
                "stcode",
                "salesCode",
                "customerpagernumber",
                "wdate"
        };

        uniqueColumnArr[5] = "salescode";
        orderbyColumnArr[5] = "idx";
        // salon_sales_customerpagernumber E -------------------------------------------


        // salon_sales_kitchenprintingdata_json S -------------------------------------------
        String[] salon_sales_kitchenprintingdata_json_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salesCode",
                "scode",
                "sidx",
                "stcode",
                "jsonstr",

                "downloadyn",

                "printedyn",

                "uploaddate",
                "downloaddate",

                "preSalesCode",
                "nowtableidx",
                "nowtablename",

                "reprintyn"
        };

        uniqueColumnArr[6] = "salescode";
        orderbyColumnArr[6] = "idx";
        // salon_sales_kitchenprintingdata_json E -------------------------------------------


        // salon_sales_card S -------------------------------------------
        String[] salon_sales_card_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salesCode",
                "tid",
                "sidx",
                "stcode",
                "cardCom",
                "priceAmount",
                "tipAmount",
                "insertSwipeKeyin",
                "status",
                "delyn",
                "cardLastFourDigitNumbers",
                "cardRefNumber",
                "cardEmvAid",
                "cardEmvTsi",
                "cardEmvTvr",
                "returnCode",
                "wdate",

                "employeeIdx",
                "employeeName",

                "severIdx",
                "serverName",

                "cashoutNum",
                "endofdayNum",
                "split_transaction_id",

                "orgTip"
        };

        uniqueColumnArr[7] = "";
        orderbyColumnArr[7] = "idx";
        // salon_sales_card E -------------------------------------------


        // member_salevisit S -------------------------------------------
        String[] member_salevisit_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "uid",
                "name",
                "lastvisitForSale"
        };

        uniqueColumnArr[8] = "";
        orderbyColumnArr[8] = "idx";
        // member_salevisit E -------------------------------------------


        // temp_salecart_del2 S -------------------------------------------
        String[] temp_salecart_del2_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "holdcode",
                "stcode",
                "tableidx",

                "wdate"
        };

        uniqueColumnArr[9] = "";
        orderbyColumnArr[9] = "idx";
        // temp_salecart_del2 E -------------------------------------------


        // salon_sales_receipt_json S -------------------------------------------
        String[] salon_sales_receipt_json_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salesCode",
                "scode",
                "sidx",
                "stcode",
                "jsonstr",
                "isCloudUpload",
                "wdate"
        };

        uniqueColumnArr[10] = "";
        orderbyColumnArr[10] = "idx";
        // salon_sales_receipt_json E -------------------------------------------


        // salon_sales_kitchen_json S -------------------------------------------
        String[] salon_sales_kitchen_json_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salesCode",
                "scode",
                "sidx",
                "stcode",
                "jsonstr",
                "isCloudUpload",
                "wdate"
        };

        uniqueColumnArr[11] = "";
        orderbyColumnArr[11] = "idx";
        // salon_sales_kitchen_json E -------------------------------------------


        // bill_list_paid S -------------------------------------------
        String[] bill_list_paid_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salescode",
                "holdcode",
                "ordernum",
                "sidx",
                "stcode",
                "billidx",
                "paidamount",
                "changeamount",
                "paytype",
                "wdate",
                "cardsalesidx",
                "billcode",
                "split_transaction_id"
        };

        uniqueColumnArr[12] = "";
        orderbyColumnArr[12] = "idx";
        // bill_list_paid E -------------------------------------------


        // bill_list_receipt_json S -------------------------------------------
        String[] bill_list_receipt_json_arr = {
                // 이곳에 컬럼 리스트를 넣을 것
                "salesCode",
                "scode",
                "sidx",
                "stcode",
                "jsonstr",
                "isCloudUpload",
                "billcode",
                "wdate"
        };

        uniqueColumnArr[13] = "";
        orderbyColumnArr[13] = "idx";
        // bill_list_receipt_json E -------------------------------------------


        String[][] columnArr = {
                salon_sales_arr,
                salon_sales_detail_arr,
                salon_storegiftcard_number_arr,
                salon_storegiftcard_number_history_arr,
                member_mileage_arr,
                salon_sales_customerpagernumber_arr,
                salon_sales_kitchenprintingdata_json_arr,
                salon_sales_card_arr,
                member_salevisit_arr,
                temp_salecart_del2_arr,
                salon_sales_receipt_json_arr,
                salon_sales_kitchen_json_arr,
                bill_list_paid_arr,
                bill_list_receipt_json_arr
        };


        TransDatasFromSQLITEToMSSQL tfSql;
        boolean result = false;
        for (int i = 0; i < tablesArr.length; i++) {
            tfSql = new TransDatasFromSQLITEToMSSQL(tablesArr[i], columnArr[i], uniqueColumnArr[i], orderbyColumnArr[i]);
            result = tfSql.transDatasExecute();

            if (!result) {
                if (!GlobalMemberValues.isStrEmpty(returnData)) {
                    returnData += ", ";
                }
                returnData += tablesArr[i];
            }
        }

        return returnData;
    }
}
