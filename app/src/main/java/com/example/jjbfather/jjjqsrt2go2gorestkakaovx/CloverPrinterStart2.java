package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import org.json.JSONObject;

public class CloverPrinterStart2 {
    Context mContext;

    public CloverPrinterStart2(){
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
                    case "kitchen1" : {

                        break;
                    }
                    case "kitchen2" : {
                        //
                        break;
                    }
                    case "testprint" : {

                        break;
                    }
                }

                break;
            }

            // Clover Station
            case "station" : {
                switch (mPrintType) {
                    case "kitchen1" : {
                        CloverStationPrinterKitchen1 cloverstationPrinterKitchen1 = new CloverStationPrinterKitchen1(mContext);
                        cloverstationPrinterKitchen1.printKitchenCloverStation(jsonObject);
                        break;
                    }
                    case "kitchen2" : {
                        //
                        break;
                    }
                    case "testprint" : {
                        CloverStationPrinterKitchen1 cloverstationPrinterKitchen1 = new CloverStationPrinterKitchen1(mContext);
                        cloverstationPrinterKitchen1.printTestCloverStation(jsonObject);
                        break;
                    }

                }

                break;
            }
        }


    }
}
