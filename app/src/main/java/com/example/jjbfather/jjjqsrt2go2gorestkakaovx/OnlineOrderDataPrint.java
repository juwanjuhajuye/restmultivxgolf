package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class OnlineOrderDataPrint {
    ArrayList<String> orderDatasArr;

    String webOrdersSalesCode = "";
    String webOrdersCustomerName = "";
    String webOrdersSaleItems = "";
    String webOrdersDeliveryTakeaway = "";
    String webOrdersDeliveryDate = "";
    String webOrdersMemo = "";
    String webOrdersCustomerOrderNumber = "";
    String webOrdersTablename = "";
    String webOrdersOrderfrom = "";
    String webOrdersSalescodethirdparty = "";

    String mSelectedReceiptJsonStr = "";

    public OnlineOrderDataPrint(ArrayList<String> paramArr) {
        GlobalMemberValues.logWrite("webprintexejjjlog", "여기실행...0.9" + "\n");
        orderDatasArr = paramArr;
        GlobalMemberValues.logWrite("webprintexejjjlog", "여기실행...1" + "\n");
    }



    public void printOnlineOrders() {
        if (orderDatasArr != null && orderDatasArr.size() > 0) {

            GlobalMemberValues.logWrite("webprintexejjjlog", "여기실행...2" + "\n");

            webOrdersSalesCode = orderDatasArr.get(0);
            webOrdersCustomerName = orderDatasArr.get(1);
            webOrdersSaleItems = orderDatasArr.get(2);
            webOrdersDeliveryTakeaway = orderDatasArr.get(3);
            webOrdersDeliveryDate = orderDatasArr.get(4);
            webOrdersMemo = orderDatasArr.get(5);
            webOrdersCustomerOrderNumber = orderDatasArr.get(6);
            webOrdersTablename = orderDatasArr.get(7);
            webOrdersOrderfrom = orderDatasArr.get(8);
            webOrdersSalescodethirdparty = orderDatasArr.get(9);


            // 주방프린트 하기 --------------------------------------------------------------------
            // 메인스테이션 일 경우에만 주방프린트한다...
            String tempMainYN = "";
            if (MainActivity.mDbInit != null){
                if (GlobalMemberValues.STATION_CODE != null) {
                    if (MainActivity.mDbInit.dbExecuteReadReturnString("select mainYN from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ") != null){
                        tempMainYN = MainActivity.mDbInit.dbExecuteReadReturnString("select mainYN from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ");
                    }
                }

            }
            if (GlobalMemberValues.isStrEmpty(tempMainYN)) {
                tempMainYN = "N";
            }
            GlobalMemberValues.logWrite("mainstationynlog", "tempMainYN : " + tempMainYN + "\n");
            if (tempMainYN == "Y" || tempMainYN.equals("Y")) {
                if (GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
                    JSONObject jsonroot_kitchen = new JSONObject();
                    try {
                        jsonroot_kitchen.put("receiptno", webOrdersSalesCode);
                        jsonroot_kitchen.put("saledate", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                        jsonroot_kitchen.put("customername", webOrdersCustomerName);
                        jsonroot_kitchen.put("saleitemlist", webOrdersSaleItems);
                        jsonroot_kitchen.put("deliverytakeaway", webOrdersDeliveryTakeaway);
                        jsonroot_kitchen.put("deliverydate", webOrdersDeliveryDate);
                        jsonroot_kitchen.put("ordertype", "WEB");
                        jsonroot_kitchen.put("customermemo", webOrdersMemo);
                        jsonroot_kitchen.put("customerordernumber", webOrdersCustomerOrderNumber);
                        jsonroot_kitchen.put("restaurant_tablename",webOrdersTablename);
                        jsonroot_kitchen.put("pushdatayn","Y");

                        // 10112023
                        jsonroot_kitchen.put("orderfrom",webOrdersOrderfrom);
                        jsonroot_kitchen.put("salescodethirdparty",webOrdersSalescodethirdparty);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Vector<String> updateVec = new Vector<String>();

                    if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {
                        GlobalMemberValues.logWrite("weborderpopjjjlog", "여기1" + "\n");

                        GlobalMemberValues.logWrite("weborderpopjjjlog", "kitchen json : " + jsonroot_kitchen.toString() + "\n");

                        int tempKtprintedynCnt = 0;
                        tempKtprintedynCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select count(*) from salon_sales_web_push_realtime " +
                                        " where salescode = '" + webOrdersSalesCode + "' and kitchenprintedyn = 'Y' "));

                        if (tempKtprintedynCnt == 0) {
                            GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "Y";
                            GlobalMemberValues.printGateByKitchen(jsonroot_kitchen, MainActivity.mContext, "kitchen1");



                            GlobalMemberValues.logWrite("weborderpopjjjlog", "여기1-2" + "\n");

                            // 09152023
                            String strQuery = " update salon_sales_web_push_realtime set " +
                                    " kitchenprintedyn = 'Y' " +
                                    " where salescode = '" + webOrdersSalesCode + "' ";

                            updateVec.addElement(strQuery);
                        }

                    }
                    if (GlobalMemberValues.isOnlineOrderAutoReceiptPrinting()){
                        GlobalMemberValues.logWrite("weborderpopjjjlog", "여기2" + "\n");

                        int tempRtprintedynCnt = 0;
                        tempRtprintedynCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select count(*) from salon_sales_web_push_realtime " +
                                        " where salescode = '" + webOrdersSalesCode + "' and receiptprintedyn = 'Y' "));

                        if (tempRtprintedynCnt == 0) {
                            // printReceipt();


                            GlobalMemberValues.logWrite("weborderpopjjjlog", "여기2-2" + "\n");

                            // 09152023
                            String strQuery = " update salon_sales_web_push_realtime set " +
                                    " receiptprintedyn = 'Y' " +
                                    " where salescode = '" + webOrdersSalesCode + "' ";

                            updateVec.addElement(strQuery);
                        }
                    }

                    if (updateVec != null && updateVec.size() > 0) {
                        for (String tempQuery : updateVec) {
                            GlobalMemberValues.logWrite("weborderpopjjjlog", "query : " + tempQuery + "\n");
                        }

                        String returnResult = "";
                        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(updateVec);
                        if (returnResult == "N" || returnResult == "") {
                            //
                        } else {
                        }
                    }
                }
            }
            // -----------------------------------------------------------------------------------



        }
    }



//    public void printReceipt() {
//        GlobalMemberValues.logWrite("LogAboutreceiptJson", "여기실행.. " + "\n");
//
//        if (GlobalMemberValues.isStrEmpty(webOrdersSalesCode)) {
//            return;
//        }
//
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
//                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
//                    if (!GlobalMemberValues.isOnline().equals("00")) {
////                        GlobalMemberValues.showDialogNoInternet(context);
//                    } else {
//                        API_download_weborders_receiptjson apiWebOrdersReceiptJson = new API_download_weborders_receiptjson(webOrdersSalesCode);
//                        apiWebOrdersReceiptJson.execute(null, null, null);
//                        try {
//                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                            if (apiWebOrdersReceiptJson.mFlag) {
//                                mSelectedReceiptJsonStr = apiWebOrdersReceiptJson.mReturnValue;
//                            }
//                        } catch (InterruptedException e) {
//                            mSelectedReceiptJsonStr = "";
//                        }
//                    }
//                }
//                // ---------------------------------------------------------------------------------
//                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
//                receiptJsonHandler.sendEmptyMessage(0);
//                // ---------------------------------------------------------------------------------
//            }
//        });
//        thread.start();
//    }
//
//    public Handler receiptJsonHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (!GlobalMemberValues.isStrEmpty(mSelectedReceiptJsonStr)) {
//                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음.
//                String receiptJson = mSelectedReceiptJsonStr;
//                GlobalMemberValues.logWrite("LogAboutreceiptJson", "receiptJson : " + receiptJson + "\n");
//
//                if (!GlobalMemberValues.isStrEmpty(receiptJson)) {
//                    JSONObject jsonroot = null;
//                    try {
//                        jsonroot = new JSONObject(receiptJson);
//                        jsonroot.put("reprintyn", "N");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (jsonroot != null) {
//                        GlobalMemberValues.mReReceiptprintYN = "Y";
//                        GlobalMemberValues.mOnlineOrder = "Y";
//                        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
//                        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
//                            GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
//                            GlobalMemberValues.printReceiptByJHP(jsonroot, MainActivity.mContext, "payment");
//                        }
//                        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
//                        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
//                            if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
//                                GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
//                                GlobalMemberValues.printReceiptByJHP(jsonroot, MainActivity.mContext, "payment");
//                            }
//                        }
//                    }
//                } else {
//                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Waraning", "There is no data to reprint", "Close");
//                }
//            }
//        }
//    };


}

