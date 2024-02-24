package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.widget.Toast;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

import org.json.JSONObject;

public class EloPrinterStart2 {
    Context mContext;

    public static ProductInfo productInfo;
    public static ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    public EloPrinterReceipt.PrinterMake myPrinter;

    public EloPrinterStart2(){
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
            case "Ordinary": {
                switch (mPrintType) {
                    case "kitchen1" : {
                        EloPrinterKitchen1 eloPrinterKitchen1 = new EloPrinterKitchen1(mContext);
                        eloPrinterKitchen1.printKitchenElo(jsonObject);
                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        break;
                    }
                    case "kitchen2" : {
                        //
                        break;
                    }
                    case "testprint" : {
                        EloPrinterKitchen1 eloPrinterKitchen1 = new EloPrinterKitchen1(mContext);
                        eloPrinterKitchen1.printTestElo(jsonObject);
                        break;
                    }
                }

                break;
            }
            case "Pro" : {
                switch (mPrintType) {
                    case "kitchen1" : {
                        //EloProPrinterKitchen1 eloProPrinterKitchen1 = new EloProPrinterKitchen1(mContext);                // 텍스트로 프린팅
                        EloProPrinterKitchenByView1 eloProPrinterKitchen1 = new EloProPrinterKitchenByView1(mContext);        // 이미지로 프린팅
                        eloProPrinterKitchen1.printKitchenEloPro(jsonObject);

                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        if (jsonObject != null) {
                            GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        }
                        break;
                    }
                    case "kitchen2" : {
                        //EloProPrinterKitchen1 eloProPrinterKitchen1 = new EloProPrinterKitchen1(mContext);                // 텍스트로 프린팅
                        EloProPrinterKitchenByView2 eloProPrinterKitchen2 = new EloProPrinterKitchenByView2(mContext);        // 이미지로 프린팅
                        eloProPrinterKitchen2.printKitchenEloPro(jsonObject);

                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        if (jsonObject != null) {
                            GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        }
                        break;
                    }
                    case "kitchen3" : {
                        //EloProPrinterKitchen1 eloProPrinterKitchen1 = new EloProPrinterKitchen1(mContext);                // 텍스트로 프린팅
                        EloProPrinterKitchenByView3 eloProPrinterKitchen3 = new EloProPrinterKitchenByView3(mContext);        // 이미지로 프린팅
                        eloProPrinterKitchen3.printKitchenEloPro(jsonObject);

                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        if (jsonObject != null) {
                            GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        }
                        break;
                    }
                    case "kitchen4" : {
                        //EloProPrinterKitchen1 eloProPrinterKitchen1 = new EloProPrinterKitchen1(mContext);                // 텍스트로 프린팅
                        EloProPrinterKitchenByView4 eloProPrinterKitchen4 = new EloProPrinterKitchenByView4(mContext);        // 이미지로 프린팅
                        eloProPrinterKitchen4.printKitchenEloPro(jsonObject);

                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        if (jsonObject != null) {
                            GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        }
                        break;
                    }
                    case "kitchen5" : {
                        //EloProPrinterKitchen1 eloProPrinterKitchen1 = new EloProPrinterKitchen1(mContext);                // 텍스트로 프린팅
                        EloProPrinterKitchenByView5 eloProPrinterKitchen5 = new EloProPrinterKitchenByView5(mContext);        // 이미지로 프린팅
                        eloProPrinterKitchen5.printKitchenEloPro(jsonObject);

                        GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                        if (jsonObject != null) {
                            GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                        }
                        break;
                    }

                    case "testprint1" : {
                        EloProPrinterKitchenByView1 EloProPrinterKitchenByView1 = new EloProPrinterKitchenByView1(mContext);
                        EloProPrinterKitchenByView1.printTestElo(jsonObject);
                        break;
                    }
                    case "testprint2" : {
                        EloProPrinterKitchenByView2 EloProPrinterKitchenByView2 = new EloProPrinterKitchenByView2(mContext);
                        EloProPrinterKitchenByView2.printTestElo(jsonObject);
                        break;
                    }
                    case "testprint3" : {
                        EloProPrinterKitchenByView3 EloProPrinterKitchenByView3 = new EloProPrinterKitchenByView3(mContext);
                        EloProPrinterKitchenByView3.printTestElo(jsonObject);
                        break;
                    }
                    case "testprint4" : {
                        EloProPrinterKitchenByView4 EloProPrinterKitchenByView4 = new EloProPrinterKitchenByView4(mContext);
                        EloProPrinterKitchenByView4.printTestElo(jsonObject);
                        break;
                    }
                    case "testprint5" : {
                        EloProPrinterKitchenByView5 EloProPrinterKitchenByView5 = new EloProPrinterKitchenByView5(mContext);
                        EloProPrinterKitchenByView5.printTestElo(jsonObject);
                        break;
                    }

                }

                break;
            }
        }



    }
}
