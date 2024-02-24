package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import org.json.JSONObject;

public class PaxPrinterStart {

    Context mContext;

    public PaxPrinterStart(){
    }

    public static void paxPrintSwitch(Context mContext, JSONObject jsonObject, String mPrintType) {
        if (mContext == null) {
            mContext = MainActivity.mContext;
        }

        GlobalMemberValues.logWrite("paxprintinglog", "mPrintType : " + mPrintType + "\n");
        GlobalMemberValues.logWrite("paxprintinglog", "여기실행..1" + "\n");
        PaxPrinterReceipt paxPrinterReceipt = new PaxPrinterReceipt(mContext);
        switch (mPrintType) {
            case "payment" : {
                paxPrinterReceipt.printSalesReceiptPax(jsonObject);
                break;
            }
            case "batchsummary" : {
                paxPrinterReceipt.printBatchPax(jsonObject);
                break;
            }
            case "batchdetail" : {
                paxPrinterReceipt.printBatchDetailPax(jsonObject);
                break;
            }
            case "void" : {
                paxPrinterReceipt.printVoidReceiptPax(jsonObject);
                break;
            }
            case "return" : {
                paxPrinterReceipt.printReturnReceiptPax(jsonObject);
                break;
            }
            case "testprint" : {
                paxPrinterReceipt.printTestPax(jsonObject);
                break;
            }
            case "openCashDrawer" : {
                paxPrinterReceipt.openCashDrawerPax();
                break;
            }
            case "cashout" : {
                paxPrinterReceipt.printCashOutPax(jsonObject);
                break;
            }
            case "startingcash" : {
                paxPrinterReceipt.printStartingCashPax(jsonObject);
                break;
            }
            case "phoneordercheckprint" : {
                paxPrinterReceipt.printPhoneOrderCheckPrintPax(jsonObject);
                break;
            }

            case "menu_print" : {
                paxPrinterReceipt.printMenuPax(jsonObject);
                break;
            }

            case "tablemain_checkprint" : {
                paxPrinterReceipt.printTablemain_CheckPrintPax(jsonObject);
            }

            // kitchen
            case "kitchen1" : {
                PaxPrinterKitchen1 paxPrinterKitchen1 = new PaxPrinterKitchen1(mContext);
                paxPrinterKitchen1.printKitchenPax(jsonObject);
                break;
            }
            case "kitchen2" : {
                PaxPrinterKitchen2 paxPrinterKitchen2 = new PaxPrinterKitchen2(mContext);
                paxPrinterKitchen2.printKitchenPax(jsonObject);
                break;
            }
            case "kitchen3" : {
                PaxPrinterKitchen3 paxPrinterKitchen3 = new PaxPrinterKitchen3(mContext);
                paxPrinterKitchen3.printKitchenPax(jsonObject);
                break;
            }
            case "kitchen4" : {
                PaxPrinterKitchen4 paxPrinterKitchen4 = new PaxPrinterKitchen4(mContext);
                paxPrinterKitchen4.printKitchenPax(jsonObject);
                break;
            }
            case "kitchen5" : {
                PaxPrinterKitchen5 paxPrinterKitchen5 = new PaxPrinterKitchen5(mContext);
                paxPrinterKitchen5.printKitchenPax(jsonObject);
                break;
            }
            case "testkitchenprint" : {
                PaxPrinterKitchen1 paxPrinterKitchen1 = new PaxPrinterKitchen1(mContext);
                paxPrinterKitchen1.printTestPaxStation(jsonObject);
                break;
            }
            case "testprint1" : {
                PaxPrinterKitchen1 paxPrinterKitchen1 = new PaxPrinterKitchen1(mContext);
                paxPrinterKitchen1.printTestPaxStation(jsonObject);
                break;
            }
            case "testprint2" : {
                PaxPrinterKitchen2 paxPrinterKitchen2 = new PaxPrinterKitchen2(mContext);
                paxPrinterKitchen2.printTestPaxStation(jsonObject);
                break;
            }
            case "testprint3" : {
                PaxPrinterKitchen3 paxPrinterKitchen3 = new PaxPrinterKitchen3(mContext);
                paxPrinterKitchen3.printTestPaxStation(jsonObject);
                break;
            }
            case "testprint4" : {
                PaxPrinterKitchen4 paxPrinterKitchen4 = new PaxPrinterKitchen4(mContext);
                paxPrinterKitchen4.printTestPaxStation(jsonObject);
                break;
            }
            case "testprint5" : {
                PaxPrinterKitchen5 paxPrinterKitchen5 = new PaxPrinterKitchen5(mContext);
                paxPrinterKitchen5.printTestPaxStation(jsonObject);
                break;
            }


        }


    }
}
