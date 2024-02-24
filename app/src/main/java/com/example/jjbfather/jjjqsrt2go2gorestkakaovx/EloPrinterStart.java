package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.widget.Toast;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

import org.json.JSONObject;

public class EloPrinterStart {
    Context mContext;

    public static ProductInfo productInfo;
    public static ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    public EloPrinterReceipt.PrinterMake myPrinter;

    public EloPrinterStart(){
    }

    public static void eloPrintSwitch(Context mContext, JSONObject jsonObject, String mPrintType) {
        if (mContext == null) {
            mContext = MainActivity.mContext;
        }

        String eloPrintType = "Ordinary";

        if (MainActivity.mProductInfo == null) {
            MainActivity.setEloInit();
        }

        productInfo = MainActivity.mProductInfo;

        if (productInfo != null) {
            EloPlatform platform = productInfo.eloPlatform;
            if (MainActivity.mApiAdapter == null) {
                MainActivity.setEloInit();
            }
            apiAdapter = MainActivity.mApiAdapter;

            if (apiAdapter == null) {
                // We're not running on a supported platform.  This is a common customer scenario where
                // the APK may be written for multiple vendor platforms.  In our case, we can't do much
                Toast.makeText(mContext, "Cannot find support for this platform", Toast.LENGTH_LONG).show();
                return;
            }

            switch (platform) {  // TODO: Base off detection, not platform
                case PAYPOINT_1:
                case PAYPOINT_REFRESH:
                    eloPrintType = "Ordinary";
                    break;
                case PAYPOINT_2:
                    eloPrintType = "Pro";
                    break;
                default:
                    eloPrintType = "Ordinary";
            }
        }

        switch (eloPrintType) {
            // 일반 Elo 일 경우...
            case "Ordinary" : {
                switch (mPrintType) {
                    case "payment" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printSalesReceiptElo(jsonObject);
                        //eloPrinterReceipt.printSalesReceiptEloTest(jsonObject);
                        //eloPrinterReceipt.printTestElo(jsonObject);
                        break;
                    }
                    case "batchsummary" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printBatchElo(jsonObject);
                        break;
                    }
                    case "batchdetail" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printBatchDetailElo(jsonObject);
                        break;
                    }
                    case "void" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printVoidReceiptElo(jsonObject);
                        break;
                    }
                    case "return" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printReturnReceiptElo(jsonObject);
                        break;
                    }
                    case "testprint" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printTestElo(jsonObject);
                        break;
                    }
                    case "openCashDrawer" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.openCashDrawerElo();
                        break;
                    }
                    case "cashout" : {
                        EloPrinterReceipt eloPrinterReceipt = new EloPrinterReceipt(mContext);
                        eloPrinterReceipt.printCashOutElo(jsonObject);
                        break;
                    }
                }

                break;
            }
            // Elo Pro 일 경우...
            case "Pro" : {
                //EloProPrinterReceipt printerReceipt = new EloProPrinterReceipt(mContext);
                EloProPrinterReceiptByView printerReceipt = new EloProPrinterReceiptByView(mContext);
                switch (mPrintType) {
                    case "payment" : {
                        printerReceipt.printSalesReceiptEloPro(jsonObject);
                        break;
                    }
                    case "batchsummary" : {
                        printerReceipt.printBatchEloPro(jsonObject);
                        break;
                    }
                    case "batchdetail" : {
                        printerReceipt.printBatchDetailEloPro(jsonObject);
                        break;
                    }
                    case "void" : {
                        printerReceipt.printVoidReceiptEloPro(jsonObject);
                        break;
                    }
                    case "return" : {
                        printerReceipt.printReturnReceiptEloPro(jsonObject);
                        break;
                    }
                    case "testprint" : {
                        printerReceipt.printTestEloPro(jsonObject);
                        break;
                    }
                    case "openCashDrawer" : {
                        printerReceipt.openCashDrawerEloPro();
                        break;
                    }
                    case "cashout" : {
                        printerReceipt.printCashOutEloPro(jsonObject);
                        break;
                    }
                    case "startingcash" : {
                        printerReceipt.printStartingCashEloPro(jsonObject);
                        break;
                    }
                    case "phoneordercheckprint" : {
                        printerReceipt.printPhoneOrderCheckPrintEloPro(jsonObject);
                        break;
                    }
                    case "menu_print" : {
                        printerReceipt.printMenuPrintEloPro(jsonObject);
                    }
                    case "tablemain_checkprint" : {
                        printerReceipt.printTablemain_CheckPrintEloPro(jsonObject);
                    }
                }

                break;
            }
        }


    }
}
