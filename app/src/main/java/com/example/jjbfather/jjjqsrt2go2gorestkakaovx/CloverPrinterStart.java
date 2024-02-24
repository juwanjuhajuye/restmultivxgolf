package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import org.json.JSONObject;

public class CloverPrinterStart {
    Context mContext;

    public CloverPrinterStart(){
    }

    public static void cloverPrintSwitch(Context mContext, JSONObject jsonObject, String paramCloverType, String mPrintType) {
        if (mContext == null) {
            mContext = MainActivity.mContext;
        }

        GlobalMemberValues.logWrite("cloverprintinglog", "paramCloverType : " + paramCloverType + "\n");
        GlobalMemberValues.logWrite("cloverprintinglog", "mPrintType : " + mPrintType + "\n");

        // 프린터 연결설정
        GlobalMemberValues.setCloverConnect(MainActivity.mContext);

        switch (paramCloverType) {
            // Clover Mini
            case "mini" : {
                switch (mPrintType) {
                    case "payment" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printSalesReceiptCloverMini(jsonObject);
                        //CloverMiniPrinterReceipt.printSalesReceiptEloTest(jsonObject);
                        //CloverMiniPrinterReceipt.printTestElo(jsonObject);
                        break;
                    }
                    case "batchsummary" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printBatchCloverMini(jsonObject);
                        break;
                    }
                    case "batchdetail" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printBatchDetailCloverMini(jsonObject);
                        break;
                    }
                    case "void" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printVoidReceiptCloverMini(jsonObject);
                        break;
                    }
                    case "return" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printReturnReceiptCloverMini(jsonObject);
                        break;
                    }
                    case "testprint" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printTestCloverMini(jsonObject);
                        break;
                    }
                    case "openCashDrawer" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.openCashDrawerCloverMini();
                        break;
                    }
                    case "cashout" : {
                        CloverMiniPrinterReceipt cloverMiniPrinterReceipt = new CloverMiniPrinterReceipt(mContext);
                        cloverMiniPrinterReceipt.printCashOutCloverMini(jsonObject);
                        break;
                    }
                }

                break;
            }

            // Clover Station
            case "station" : {
                switch (mPrintType) {
                    case "payment" : {
                        GlobalMemberValues.logWrite("cloverprintinglog", "여기실행..1" + "\n");
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printSalesReceiptCloverStation(jsonObject);
                        break;
                    }
                    case "batchsummary" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printBatchCloverStation(jsonObject);
                        break;
                    }
                    case "batchdetail" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printBatchDetailCloverStation(jsonObject);
                        break;
                    }
                    case "void" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printVoidReceiptCloverStation(jsonObject);
                        break;
                    }
                    case "return" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printReturnReceiptCloverStation(jsonObject);
                        break;
                    }
                    case "testprint" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printTestCloverStation(jsonObject);
                        break;
                    }
                    case "openCashDrawer" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.openCashDrawerCloverStation();
                        break;
                    }
                    case "cashout" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printCashOutCloverStation(jsonObject);
                        break;
                    }
                    case "phoneordercheckprint" : {
                        CloverStationPrinterReceipt cloverStationPrinterReceipt = new CloverStationPrinterReceipt(mContext);
                        cloverStationPrinterReceipt.printPhoneOrderCheckPrintCloverStation(jsonObject);
                        break;
                    }
                }

                break;
            }
        }


    }
}
