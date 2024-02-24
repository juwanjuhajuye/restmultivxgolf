package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import com.sam4s.printer.Sam4sBuilder;
import com.sam4s.printer.Sam4sPrint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sam4GiantPrinter {
    public static Sam4sPrint Giant_mPrinter = new Sam4sPrint();
    public static Sam4sPrint Giant_mPrinterkitchen1 = new Sam4sPrint();
    public static Sam4sPrint Giant_mPrinterkitchen2 = new Sam4sPrint();
    public static Sam4sPrint Giant_mPrinterkitchen3 = new Sam4sPrint();
    public static Sam4sPrint Giant_mPrinterkitchen4 = new Sam4sPrint();
    public static Sam4sPrint Giant_mPrinterkitchen5 = new Sam4sPrint();

    public Context mContext;
    public String mPrintType;
    public JSONObject jsonObject;
    int connect_count = 5;

    public static boolean b_stopReTryMain = false;
    public static boolean b_stopReTry1 = false;
    public static boolean b_stopReTry2 = false;
    public static boolean b_stopReTry3 = false;
    public static boolean b_stopReTry4 = false;
    public static boolean b_stopReTry5 = false;

    public void Sam4GiantPrinter(Context mContext, JSONObject jsonObject, String mPrintType){
        this.mContext = mContext;
    }

    public void disconnect(){
        try {
            Giant_mPrinterkitchen1.closePrinter();
            Giant_mPrinterkitchen2.closePrinter();
            Giant_mPrinterkitchen3.closePrinter();
            Giant_mPrinterkitchen4.closePrinter();
            Giant_mPrinterkitchen5.closePrinter();
            Giant_mPrinter.closePrinter();
        } catch (Exception e){

        }
    }

    public void connect(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {
                        switch (mPrintType){
                            case "payment" :
                            case "batchsummary" :
                            case "batchdetail" :
                            case "void" :
                            case "return" :
                            case "openCashDrawer" :
                            case "cashout" :
                            case "phoneordercheckprint" :
                            case "testprint" :
                            case "tablemain_checkprint" :
                                Giant_mPrinter = new Sam4sPrint();
                                b_is_connected = Giant_mPrinter.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT));
                                count++;
                                break;
                        }

                        if (b_is_connected==false){

                            try {
                                switch (mPrintType) {
                                    case "payment" :
                                    case "batchsummary" :
                                    case "batchdetail" :
                                    case "void" :
                                    case "return" :
                                    case "openCashDrawer" :
                                    case "cashout" :
                                    case "phoneordercheckprint" :
                                    case "testprint" :
                                    case "tablemain_checkprint" :
                                        if (count > 3) {
                                            count = 0;
                                            Giant_mPrinter.resetPrinter();
                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }

                        } else {
                            switch (mPrintType) {
                                case "payment" : {
                                    printSalesReceipt(jsonObject);
                                    break;
                                }
                                case "batchsummary" : {
                                    printBatchReceipt(jsonObject);
                                    break;
                                }
                                case "batchdetail" : {
                                    printBatchDetailReceipt(jsonObject);
                                    break;
                                }
                                case "void" : {
                                    printVoidReceipt(jsonObject);
                                    break;
                                }
                                case "return" : {
                                    printReturnReceipt(jsonObject);
                                    break;
                                }
                                case "openCashDrawer" : {
                                    openCashDrawer();
                                    break;
                                }
                                case "cashout" : {
                                    printCashOutReceipt(jsonObject);
                                    break;
                                }
                                case "startingcash" : {
                                    printStartingCashReceipt(jsonObject);
                                    break;
                                }
                                case "phoneordercheckprint" : {
                                    printPhoneOrderCheckReceipt(jsonObject);
                                    break;
                                }
                                case "testprint":
                                    testPrintN();
                                    break;
                                case "tablemain_checkprint" : {
                                    printTablemain_CheckReceipt(jsonObject);
                                    break;
                                }

                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){
                                case "payment" :
                                case "batchsummary" :
                                case "batchdetail" :
                                case "void" :
                                case "return" :
                                case "openCashDrawer" :
                                case "cashout" :
                                case "phoneordercheckprint" :
                                case "testprint" :
                                    if (count > 3) {
                                        count = 0;
                                        Giant_mPrinter.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("sam4 connect error","" + e.toString());
                    }
                    if (b_stopReTryMain) {
                        b_is_connected = true;
                        b_stopReTryMain = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();
    }

    public void testPrintN(){

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    Sam4sBuilder builder = null;
                    try {
                        //create builder
                        //Builder
                        builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);

                        //add command
                        //addTextFont
                        builder.addTextFont(Sam4sBuilder.FONT_A);

                        //addTextAlign
                        builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);

                        //addTextPosition
                        builder.addTextPosition(0);

                        //addTextLineSpace
                        builder.addTextLineSpace(0);

                        //addTextLang
                        builder.addTextLang(Sam4sBuilder.LANG_EN);

                        //addTextSize
                        builder.addTextSize(1, 1);

                        //addTextStyle
                        builder.addTextStyle(false, false, false, Sam4sBuilder.COLOR_1);

                        //addText
                        builder.addText("Giant - 100 Test Printing.....");

                        //addFeedUnit
                        builder.addFeedUnit(0);

                        builder.addCut(1);

                        //send builder data

                        try {
                            Giant_mPrinter.sendData(builder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch(Exception e){

                        e.printStackTrace();
                    }

                } catch (Exception e){

                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();


    }

    public void testPrint(final String kitchen_num){

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                try {

                    Sam4sBuilder builder = null;
                    try {
                        //create builder
                        //Builder
                        builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);

                        //add command
                        //addTextFont
                        builder.addTextFont(Sam4sBuilder.FONT_A);

                        //addTextAlign
                        builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);

                        //addTextPosition
                        builder.addTextPosition(0);

                        //addTextLineSpace
                        builder.addTextLineSpace(0);

                        //addTextLang
                        builder.addTextLang(Sam4sBuilder.LANG_EN);

                        //addTextSize
                        builder.addTextSize(1, 1);

                        //addTextStyle
                        builder.addTextStyle(false, false, false, Sam4sBuilder.COLOR_1);

                        //addText
                        builder.addText("Giant - 100 Test Printing.....");

                        //addFeedUnit
                        builder.addFeedUnit(0);

                        builder.addCut(1);

                        //send builder data

                        try {

                            switch (kitchen_num){
                                case "1" :
                                    Giant_mPrinterkitchen1.sendData(builder);
                                    break;
                                case "2" :
                                    Giant_mPrinterkitchen2.sendData(builder);
                                    break;
                                case "3" :
                                    Giant_mPrinterkitchen3.sendData(builder);
                                    break;
                                case "4" :
                                    Giant_mPrinterkitchen4.sendData(builder);
                                    break;
                                case "5" :
                                    Giant_mPrinterkitchen5.sendData(builder);
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch(Exception e){

                        e.printStackTrace();
                    }

                } catch (Exception e){

                }

                switch (kitchen_num){
                    case "1" :
                        Giant_mPrinterkitchen1.closePrinter();
                        break;
                    case "2" :
                        Giant_mPrinterkitchen2.closePrinter();
                        break;
                    case "3" :
                        Giant_mPrinterkitchen3.closePrinter();
                        break;
                    case "4" :
                        Giant_mPrinterkitchen4.closePrinter();
                        break;
                    case "5" :
                        Giant_mPrinterkitchen5.closePrinter();
                        break;
                }


            }
        };
        thread.start();



//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                try {
//
//                    Sam4sBuilder builder = null;
//                    try {
//                        //create builder
//                        //Builder
//                        builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);
//
//                        //add command
//                        //addTextFont
//                        builder.addTextFont(Sam4sBuilder.FONT_A);
//
//                        //addTextAlign
//                        builder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);
//
//                        //addTextPosition
//                        builder.addTextPosition(0);
//
//                        //addTextLineSpace
//                        builder.addTextLineSpace(0);
//
//                        //addTextLang
//                        builder.addTextLang(Sam4sBuilder.LANG_EN);
//
//                        //addTextSize
//                        builder.addTextSize(1, 1);
//
//                        //addTextStyle
//                        builder.addTextStyle(false, false, false, Sam4sBuilder.COLOR_1);
//
//                        //addText
//                        builder.addText("Giant - 100 Test Printing.....");
//
//                        //addFeedUnit
//                        builder.addFeedUnit(0);
//
//                        builder.addCut(1);
//
//                        //send builder data
//
//                        try {
//
//                            switch (kitchen_num){
//                                case "1" :
//                                    Giant_mPrinterkitchen1.sendData(builder);
//                                    break;
//                                case "2" :
//                                    Giant_mPrinterkitchen2.sendData(builder);
//                                    break;
//                                case "3" :
//                                    Giant_mPrinterkitchen3.sendData(builder);
//                                    break;
//                                case "4" :
//                                    Giant_mPrinterkitchen4.sendData(builder);
//                                    break;
//                                case "5" :
//                                    Giant_mPrinterkitchen5.sendData(builder);
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }catch(Exception e){
//
//                        e.printStackTrace();
//                    }
//
//                } catch (Exception e){
//
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                switch (kitchen_num){
//                    case "1" :
//                        Giant_mPrinterkitchen1.closePrinter();
//                        break;
//                    case "2" :
//                        Giant_mPrinterkitchen2.closePrinter();
//                        break;
//                    case "3" :
//                        Giant_mPrinterkitchen3.closePrinter();
//                        break;
//                    case "4" :
//                        Giant_mPrinterkitchen4.closePrinter();
//                        break;
//                    case "5" :
//                        Giant_mPrinterkitchen5.closePrinter();
//                        break;
//                }
//
//
//            }
//        };
//        asyncTask.execute();


    }

    public void openCashDrawer(){

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                try {

                    Sam4sBuilder builder = null;
                    try {
                        //create builder
                        //Builder
                        builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);

                        //add command
                        builder.addOpenCashDrawer(0,10);
                        //send builder data

                        try {
                            Giant_mPrinter.sendData(builder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch(Exception e){

                        e.printStackTrace();
                    }

                } catch (Exception e){

                }

                Giant_mPrinter.closePrinter();

            }
        };
        thread.start();
    }

    public void printGiantPrinter(JSONObject data, String printType, String isCusmerMerchant, String str_receiptType){
        LinearLayout getPrintingLn = null;
        switch (str_receiptType){
            case "payment" :
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, printType, isCusmerMerchant);
                break;
            case "batchsummary" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatch(data);
                break;
            }
            case "batchdetail" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatchDetail(data);
                break;
            }
            case "void" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, printType, isCusmerMerchant);
                break;
            }
            case "return" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, printType, isCusmerMerchant);
                break;
            }
            case "openCashDrawer" : {
                break;
            }
            case "cashout" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(data, "");
                break;
            }
            case "startingcash" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForStartingCash(data);
                break;
            }
            case "phoneordercheckprint" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(data);
                break;
            }
            case "tablemain_checkprint" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView(data);
            }
        }
//        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, printType, isCusmerMerchant);
        if (getPrintingLn != null) {

            Sam4sBuilder builder = null;

            try{
                builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);
                int px = 576;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
                View emptyView = new View(MainActivity.mContext);
                emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
                getPrintingLn.addView(emptyView);

                //setTextView(v);
                getPrintingLn.setDrawingCacheEnabled(true);
                getPrintingLn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());

                Bitmap bitMap = Bitmap.createBitmap(px, getPrintingLn.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitMap);
                canvas.drawColor(Color.WHITE);
                getPrintingLn.draw(canvas);

                getPrintingLn.setDrawingCacheEnabled(false);

                builder.addImage(bitMap, bitMap.getWidth(), bitMap.getHeight());
                builder.addCut(1);
                //send builder data
                try{
                    Giant_mPrinter.sendData(builder);
                }catch(Exception e){
                    e.printStackTrace();
                }
                //remove builder
                if(builder != null){
                    try{
                        builder.clearCommandBuffer();
                        builder = null;
                    }catch(Exception e){
                        builder = null;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        getPrintingLn = null;
    }

    //


    public synchronized void printGiantPrinter_kitchen(JSONObject data, String kitchenNum) {

        LinearLayout getPrintingLn = null;

        if (GlobalMemberValues.kitchenprinting_type == "I" || GlobalMemberValues.kitchenprinting_type.equals("I")) {
            getPrintingLn = null;
            getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data,  kitchenNum);
            if (getPrintingLn != null) {

                Sam4sBuilder builder = null;

                try{

                    builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);
                    int px = 576;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
                    View emptyView = new View(MainActivity.mContext);
                    emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
                    getPrintingLn.addView(emptyView);

                    //setTextView(v);
                    getPrintingLn.setDrawingCacheEnabled(true);
                    getPrintingLn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());

                    Bitmap bitMap = Bitmap.createBitmap(px, getPrintingLn.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitMap);
                    canvas.drawColor(Color.WHITE);
                    getPrintingLn.draw(canvas);

                    getPrintingLn.setDrawingCacheEnabled(false);

                    builder.addImage(bitMap, bitMap.getWidth(), bitMap.getHeight());
                    builder.addCut(1);
                    //send builder data
//                switch (kitchenNum){
//                    case "1" :
//                        for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("1"); temp++) {
//                            Giant_mPrinterkitchen1.sendData(builder);
//                        }
//                        break;
//                    case "2" :
//                        for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("2"); temp++) {
//                            Giant_mPrinterkitchen2.sendData(builder);
//                        }
//                        break;
//                    case "3" :
//                        for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("3"); temp++) {
//                            Giant_mPrinterkitchen3.sendData(builder);
//                        }
//                        break;
//                    case "4" :
//                        for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("4"); temp++) {
//                            Giant_mPrinterkitchen4.sendData(builder);
//                        }
//                        break;
//                    case "5" :
//                        for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("5"); temp++) {
//                            Giant_mPrinterkitchen5.sendData(builder);
//                        }
//                        break;
//                }
                    switch (kitchenNum){
                        case "1" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("1"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen1.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen1 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen1.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                ////Thread.sleep(5000);
                            }

                            break;
                        case "2" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("2"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen2.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen2 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen2.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "3" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("3"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen3.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen3 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen3.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "4" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("4"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen4.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen4 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen4.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "5" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("5"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen5.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen5 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen5.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    Log.e("test 2","" + e.toString());
                }

            }
        } else {
            Sam4sBuilder builder = Sam4sGiantPrinterTextInPrinting.makingTextForKitchen(data,  kitchenNum);
            if (builder != null) {

//            Sam4sBuilder builder = null;

                try {
                    builder.addCut(1);

                    switch (kitchenNum){
                        case "1" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("1"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen1.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen1 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen1.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                ////Thread.sleep(5000);
                            }

                            break;
                        case "2" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("2"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen2.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen2 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen2.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "3" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("3"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen3.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen3 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen3.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "4" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("4"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen4.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen4 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen4.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                        case "5" :
                            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("5"); temp++) {
                                String tempStatusStr = "";
                                for (int i = 0; i < 10; i++) {
                                    tempStatusStr = Giant_mPrinterkitchen5.getPrinterStatus();
                                    GlobalMemberValues.logWrite("giantprintertestlog", "kitchen5 - status : " + tempStatusStr + "\n");
                                    if (tempStatusStr.equals("Printer Ready")) {
                                        Giant_mPrinterkitchen5.sendData(builder);
                                        break;
                                    }
                                    //Thread.sleep(1000);
                                }
                                //Thread.sleep(5000);
                            }

                            break;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    Log.e("test 2","" + e.toString());
                }

            }
        }


        getPrintingLn = null;
    }

    public void printSalesReceipt(final JSONObject data) {
        Payment.openPaymentReview(Payment.context);
        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
            printGiantPrinter(data,"sales","merchant","payment");
        }
        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
            if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                printGiantPrinter(data, "sales", "customer", "payment");
            }
        }
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        // reprint 일 경우...

        if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
            GlobalMemberValues.mReReceiptprintYN = "N";
            return;
        }

        // 돈통 열기 cash drawer
        if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
//            GlobalMemberValues.paxOpenDrawer();
        }

        Giant_mPrinter.closePrinter();

        // 고객이 영수증 발급타입을 선택하는 경우가 아닐 때에만 주방프린트 실행
        // (고객이 영수증 발급타입을 선택하는 경우에는 앞에서 주방프린트가 실행되었음)
        int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
        if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
            try {
                Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String tempSalesCode = "";
            try {
                tempSalesCode = Payment.jsonroot_kitchen.getString("receiptno");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                // 주방프린트 하기 ----------------------------------------------------------------
                GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                // -------------------------------------------------------------------------------
            } else {
                if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                        // 주방프린트 하기 ----------------------------------------------------------------
                        GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                        // -------------------------------------------------------------------------------
                    }
                }
            }
        }

        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {

                if (GlobalMemberValues.RECEIPTPRINTTYPE.equals("")) {
                    PaymentCustomerSelectReceipt.finishPayment();
                }
                GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
            }
        }
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                // 결제 리뷰창 오픈
//
//            }
//
//            @Override
//            protected Object doInBackground(Object[] objects) {
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//            }
//        };
//        asyncTask.execute();
    }

    public void printBatchReceipt(final JSONObject data){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"batchsummary","","batchsummary");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }
    public void printBatchDetailReceipt(final JSONObject data){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"batchdetail","","batchdetail");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }

    public void printVoidReceipt(final JSONObject data) {

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                GlobalMemberValues.logWrite("paxprintinglogvoid", "받은 데이터 : " + data.toString() + "\n");

                // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
                printGiantPrinter(data,"void", "merchant","void");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------

                // Customer 영수증 프린트 -----------------------------------------------`-----------------------------------------------------------------------------
                printGiantPrinter(data,"void", "customer","void");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }
    public void printReturnReceipt(final JSONObject data) {
        AsyncTask asyncTask = new AsyncTask() {
            ProgressDialog progressDialog = new ProgressDialog(mContext);

            @Override
            protected void onPreExecute() {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Now printing...");

                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                for (int i = 0; i < 5; i ++){
                    progressDialog.setProgress(i * 30);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                GlobalMemberValues.logWrite("paxprintinglogvoid", "받은 데이터 : " + data.toString() + "\n");
                // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
                printGiantPrinter(data,"return", "merchant","return");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------

                // Customer 영수증 프린트 -----------------------------------------------`-----------------------------------------------------------------------------
                printGiantPrinter(data,"return", "customer","return");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
                progressDialog.dismiss();
            }
        };
        asyncTask.execute();
        SaleHistory.mActivity.recreate();
    }
    public void printCashOutReceipt(final JSONObject data) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"cashout","","cashout");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }
    public void printStartingCashReceipt(final JSONObject data) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"startingcash","","startingcash");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }
    public void printPhoneOrderCheckReceipt(final JSONObject data) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"phoneordercheckprint","","phoneordercheckprint");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }
    public void printTablemain_CheckReceipt(final JSONObject data) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                printGiantPrinter(data,"tablemain_checkprint","","tablemain_checkprint");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Giant_mPrinter.closePrinter();
            }
        };
        asyncTask.execute();
    }

    public void printKitchenReceipt1(final JSONObject jsonObject){
        GlobalMemberValues.mKitchenPrintedQty = 0;

        GlobalMemberValues.mKitchenPrintedQty++;

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                if (jsonObject == null) {
//                    return null;
//                }
//                printGiantPrinter_kitchen(jsonObject, "1");
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                Giant_mPrinterkitchen1.closePrinter();
//            }
//        };
//        asyncTask.execute();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if (jsonObject == null) {
                } else {
                    printGiantPrinter_kitchen(jsonObject, "1");


                }
                Giant_mPrinterkitchen1.closePrinter();
            }
        };
        thread.start();

        String receiptNo = GlobalMemberValues.getDataInJsonData(jsonObject, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(jsonObject, "ordertype");

        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);


    }
    public void printKitchenReceipt2(final JSONObject jsonObject){
        GlobalMemberValues.mKitchenPrintedQty++;

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//
//                if (jsonObject == null) {
//                    return null;
//                }
//                printGiantPrinter_kitchen(jsonObject, "2");
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                Giant_mPrinterkitchen2.closePrinter();
//            }
//        };
//        asyncTask.execute();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if (jsonObject == null) {
                } else {
                    printGiantPrinter_kitchen(jsonObject, "2");
                }
                Giant_mPrinterkitchen2.closePrinter();
            }
        };
        thread.start();

        String receiptNo = GlobalMemberValues.getDataInJsonData(jsonObject, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(jsonObject, "ordertype");

        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);


    }
    public void printKitchenReceipt3(final JSONObject jsonObject){
        GlobalMemberValues.mKitchenPrintedQty++;
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                if (jsonObject == null) {
//                    return null;
//                }
//                printGiantPrinter_kitchen(jsonObject, "3");
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                Giant_mPrinterkitchen3.closePrinter();
//            }
//        };
//        asyncTask.execute();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if (jsonObject == null) {
                } else {
                    printGiantPrinter_kitchen(jsonObject, "3");
                }
                Giant_mPrinterkitchen3.closePrinter();
            }
        };
        thread.start();

        String receiptNo = GlobalMemberValues.getDataInJsonData(jsonObject, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(jsonObject, "ordertype");

        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);

    }
    public void printKitchenReceipt4(final JSONObject jsonObject){
        GlobalMemberValues.mKitchenPrintedQty++;
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                if (jsonObject == null) {
//                    return null;
//                }
//                printGiantPrinter_kitchen(jsonObject, "4");
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                Giant_mPrinterkitchen4.closePrinter();
//            }
//        };
//        asyncTask.execute();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if (jsonObject == null) {
                } else {
                    printGiantPrinter_kitchen(jsonObject, "4");
                }
                Giant_mPrinterkitchen4.closePrinter();
            }
        };
        thread.start();

        String receiptNo = GlobalMemberValues.getDataInJsonData(jsonObject, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(jsonObject, "ordertype");

        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);

    }
    public void printKitchenReceipt5(final JSONObject jsonObject){
        GlobalMemberValues.mKitchenPrintedQty++;
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                if (jsonObject == null) {
//                    return null;
//                }
//                printGiantPrinter_kitchen(jsonObject, "5");
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//                Giant_mPrinterkitchen5.closePrinter();
//            }
//        };
//        asyncTask.execute();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                if (jsonObject == null) {
                } else {
                    printGiantPrinter_kitchen(jsonObject, "5");
                }
                Giant_mPrinterkitchen5.closePrinter();
            }
        };
        thread.start();
        String receiptNo = GlobalMemberValues.getDataInJsonData(jsonObject, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(jsonObject, "ordertype");

        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);
    }


    public void connectKitchen1(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {
                        switch (mPrintType){

                            case "testprint1":
                            case "kitchen1":
//                                Giant_mPrinterkitchen1.closePrinter();
                                Giant_mPrinterkitchen1 = null;
                                Giant_mPrinterkitchen1 = new Sam4sPrint();
                                b_is_connected = Giant_mPrinterkitchen1.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP2, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT2));
                                count++;
                                break;
                        }

                        if (b_is_connected==false){
                            //Thread.sleep(5000);
                            try {
                                switch (mPrintType){

                                    case "testprint1":
                                    case "kitchen1":
                                        if (count > connect_count) {
                                            //Thread.sleep(2000);
                                            count = 0;
//                                            Giant_mPrinterkitchen1.resetPrinter();

                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }

                        } else {
                            switch (mPrintType) {

                                // kitchen
                                case "kitchen1" : {
                                    printKitchenReceipt1(jsonObject);
                                    break;
                                }

                                case "testprint1":
                                    testPrint("1");
                                    break;
                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){

                                case "testprint1":
                                case "kitchen1":
                                    if (count > connect_count) {
                                        //Thread.sleep(2000);
                                        count = 0;
//                                        Giant_mPrinterkitchen1.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("jihun park connect error","" + e.toString());
                    }
                    if (b_stopReTry1) {
                        b_is_connected = true;
                        b_stopReTry1 = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();

        if (mPrintType.equals("testprint1")){

        } else {
            //GlobalMemberValues.printReceiptByKitchen2(jsonObject, MainActivity.mContext, "kitchen2");
        }

    }
    public void connectKitchen2(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        GlobalMemberValues.logWrite("jjjkitchenprinterlog", "여기왔습니다...0" + "\n");

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {

                        switch (mPrintType){
                            case "testprint2":
                            case "kitchen2":
//                                Giant_mPrinterkitchen2.closePrinter();
                                Giant_mPrinterkitchen2 = null;
                                Giant_mPrinterkitchen2 = new Sam4sPrint();
                                GlobalMemberValues.logWrite("jjjkitchenprinterlog", "여기왔습니다...1" + "\n");
                                b_is_connected = Giant_mPrinterkitchen2.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP3, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT3));
                                GlobalMemberValues.logWrite("jjjkitchenprinterlog", "여기왔습니다...2" + "\n");
                                count++;
                                break;
                        }

                        if (b_is_connected==false){
                            try {
                                switch (mPrintType){
                                    case "testprint2":
                                    case "kitchen2":
                                        if (count > connect_count){
                                            //Thread.sleep(2000);
                                            count = 0;
//                                            Giant_mPrinterkitchen2.resetPrinter();
                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }
                        } else {
                            switch (mPrintType) {
                                case "kitchen2" : {
                                    printKitchenReceipt2(jsonObject);
                                    break;
                                }
                                case "testprint2":
                                    testPrint("2");
                                    break;
                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){
                                case "testprint2":
                                case "kitchen2":
                                    if (count > connect_count){
                                        //Thread.sleep(2000);
                                        count = 0;
//                                        Giant_mPrinterkitchen2.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("jihun park connect error","" + e.toString());
                    }
                    if (b_stopReTry2) {
                        b_is_connected = true;
                        b_stopReTry2 = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                boolean b_is_connected = false;
//                do {
//                    b_is_connected = false;
//                    try {
//
//                        switch (mPrintType){
//                            case "testprint2":
//                            case "kitchen2":
//                                Giant_mPrinterkitchen2.closePrinter();
//                                Giant_mPrinterkitchen2 = new Sam4sPrint();
//                                b_is_connected = Giant_mPrinterkitchen2.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP3, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT3));
//                                break;
//                        }
//
//                        if (b_is_connected==false){
//
//
//                        }
//
//
//                    }catch (Exception e){
//                        try {
//                            switch (mPrintType){
//                                case "testprint2":
//                                case "kitchen2":
//                                    Giant_mPrinterkitchen2.resetPrinter();
//                                    break;
//                            }
//                        } catch (Exception e1) {
//                            Log.e("jihun park reset error","" + e.toString());
//                        }
//                        Log.e("jihun park connect error","" + e.toString());
//                    }
//                } while (!b_is_connected);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                switch (mPrintType) {
//                    case "kitchen2" : {
//                        printKitchenReceipt2(jsonObject);
//                        break;
//                    }
//                    case "testprint2":
//                        testPrint("2");
//                        break;
//                }
//                super.onPostExecute(o);
//            }
//        };
//        asyncTask.execute();


        if (mPrintType.equals("testprint2")){

        } else {
            //GlobalMemberValues.printReceiptByKitchen3(jsonObject, MainActivity.mContext, "kitchen3");
        }


    }
    public void connectKitchen3(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {
                        switch (mPrintType){
                            case "testprint3":
                            case "kitchen3":
//                                Giant_mPrinterkitchen3.closePrinter();
                                Giant_mPrinterkitchen3 = null;
                                Giant_mPrinterkitchen3 = new Sam4sPrint();
                                b_is_connected = Giant_mPrinterkitchen3.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP4, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT4));
                                count++;
                                break;
                        }

                        if (b_is_connected==false){
                            try {
                                switch (mPrintType){
                                    case "testprint3":
                                    case "kitchen3":
                                        if (count > connect_count){
                                            //Thread.sleep(2000);
                                            count = 0;
//                                            Giant_mPrinterkitchen3.resetPrinter();
                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }

                        } else {
                            switch (mPrintType) {
                                case "kitchen3" : {
                                    printKitchenReceipt3(jsonObject);
                                    break;
                                }
                                case "testprint3":
                                    testPrint("3");
                                    break;
                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){
                                case "testprint3":
                                case "kitchen3":
                                    if (count > connect_count){
                                        //Thread.sleep(2000);
                                        count = 0;
//                                        Giant_mPrinterkitchen3.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("sam4 connect error","" + e.toString());
                    }
                    if (b_stopReTry3) {
                        b_is_connected = true;
                        b_stopReTry3 = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                boolean b_is_connected = false;
//                do {
//                    b_is_connected = false;
//                    try {
//                        switch (mPrintType){
//                            case "testprint3":
//                            case "kitchen3":
//                                Giant_mPrinterkitchen3.closePrinter();
//                                Giant_mPrinterkitchen3 = new Sam4sPrint();
//                                b_is_connected = Giant_mPrinterkitchen3.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP4, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT5));
//                                break;
//                        }
//
//                        if (b_is_connected==false){
//
//
//                        }
//
//
//                    }catch (Exception e){
//                        try {
//                            switch (mPrintType){
//                                case "testprint3":
//                                case "kitchen3":
//                                    Giant_mPrinterkitchen3.resetPrinter();
//                                    break;
//                            }
//                        } catch (Exception e1) {
//                            Log.e("jihun park reset error","" + e.toString());
//                        }
//                        Log.e("jihun park connect error","" + e.toString());
//                    }
//                } while (!b_is_connected);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                switch (mPrintType) {
//                    case "kitchen3" : {
//                        printKitchenReceipt3(jsonObject);
//                        break;
//                    }
//                    case "testprint3":
//                        testPrint("3");
//                        break;
//                }
//                super.onPostExecute(o);
//            }
//        };
//        asyncTask.execute();

        if (mPrintType.equals("testprint3")){

        } else {
            //GlobalMemberValues.printReceiptByKitchen4(jsonObject, MainActivity.mContext, "kitchen4");
        }



    }
    public void connectKitchen4(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {
                        switch (mPrintType){
                            case "testprint4":
                            case "kitchen4":
//                                Giant_mPrinterkitchen4.closePrinter();
                                Giant_mPrinterkitchen4 = null;
                                Giant_mPrinterkitchen4 = new Sam4sPrint();
                                b_is_connected = Giant_mPrinterkitchen4.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP5, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT5));
                                count++;
                                break;
                        }

                        if (b_is_connected==false){
                            try {
                                switch (mPrintType){
                                    case "testprint4":
                                    case "kitchen4":
                                        if (count > connect_count){
                                            ////Thread.sleep(2000);
                                            count = 0;
//                                            Giant_mPrinterkitchen4.resetPrinter();
                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }

                        } else {
                            switch (mPrintType) {
                                case "kitchen4" : {
                                    printKitchenReceipt4(jsonObject);
                                    break;
                                }
                                case "testprint4":
                                    testPrint("4");
                                    break;
                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){
                                case "testprint4":
                                case "kitchen4":
                                    if (count > connect_count){
                                        //Thread.sleep(2000);
                                        count = 0;
//                                        Giant_mPrinterkitchen4.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("sam4 connect error","" + e.toString());
                    }
                    if (b_stopReTry4) {
                        b_is_connected = true;
                        b_stopReTry4 = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                boolean b_is_connected = false;
//                do {
//                    b_is_connected = false;
//                    try {
//                        switch (mPrintType){
//                            case "testprint4":
//                            case "kitchen4":
//                                Giant_mPrinterkitchen4.closePrinter();
//                                Giant_mPrinterkitchen4 = new Sam4sPrint();
//                                b_is_connected = Giant_mPrinterkitchen4.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP5, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT6));
//                                break;
//                        }
//
//                        if (b_is_connected==false){
//
//
//                        }
//
//
//                    }catch (Exception e){
//                        try {
//                            switch (mPrintType){
//                                case "testprint4":
//                                case "kitchen4":
//                                    Giant_mPrinterkitchen4.resetPrinter();
//                                    break;
//                            }
//                        } catch (Exception e1) {
//                            Log.e("jihun park reset error","" + e.toString());
//                        }
//                        Log.e("jihun park connect error","" + e.toString());
//                    }
//                } while (!b_is_connected);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                switch (mPrintType) {
//                    case "kitchen4" : {
//                        printKitchenReceipt4(jsonObject);
//                        break;
//                    }
//                    case "testprint4":
//                        testPrint("4");
//                        break;
//                }
//                super.onPostExecute(o);
//            }
//        };
//        asyncTask.execute();

        if (mPrintType.equals("testprint4")){

        } else {
            //GlobalMemberValues.printReceiptByKitchen5(jsonObject, MainActivity.mContext, "kitchen5");
        }

    }
    public void connectKitchen5(Context mContext,final JSONObject jsonObject, final String mPrintType) {

        this.mPrintType = mPrintType;
        this.jsonObject = jsonObject;
        this.mContext = mContext;

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                boolean b_is_connected = false;
                int count = 0;
                do {
                    b_is_connected = false;
                    try {

                        switch (mPrintType){
                            case "testprint5":
                            case "kitchen5":
//                                Giant_mPrinterkitchen5.closePrinter();
                                Giant_mPrinterkitchen5 = null;
                                Giant_mPrinterkitchen5 = new Sam4sPrint();
                                b_is_connected = Giant_mPrinterkitchen5.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP6, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT6));
                                count++;
                                break;
                        }

                        if (b_is_connected==false){

                            try {
                                switch (mPrintType){
                                    case "testprint5":
                                    case "kitchen5":
                                        if (count > connect_count){
                                            //Thread.sleep(2000);
                                            count = 0;
//                                            Giant_mPrinterkitchen5.resetPrinter();
                                        }
                                        break;
                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e1.toString());
                            }
                        } else {
                            switch (mPrintType) {
                                case "kitchen5" : {
                                    printKitchenReceipt5(jsonObject);
                                    break;
                                }
                                case "testprint5":
                                    testPrint("5");
                                    break;

                            }
                        }


                    }catch (Exception e){
                        try {
                            switch (mPrintType){
                                case "testprint5":
                                case "kitchen5":
                                    if (count > connect_count){
                                        //Thread.sleep(2000);
                                        count = 0;
//                                        Giant_mPrinterkitchen5.resetPrinter();
                                    }
                                    break;
                            }
                        } catch (Exception e1) {
                            Log.e("jihun park reset error","" + e.toString());
                        }
                        Log.e("sam4 connect error","" + e.toString());
                    }
                    if (b_stopReTry5) {
                        b_is_connected = true;
                        b_stopReTry5 = false;
                    }
                } while (!b_is_connected);
            }
        };
        thread.start();

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                boolean b_is_connected = false;
//                do {
//                    b_is_connected = false;
//                    try {
//
//                        switch (mPrintType){
//                            case "testprint5":
//                            case "kitchen5":
//                                Giant_mPrinterkitchen5.closePrinter();
//                                Giant_mPrinterkitchen5 = new Sam4sPrint();
//                                b_is_connected = Giant_mPrinterkitchen5.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP6, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT6));
//                                break;
//                        }
//
//                        if (b_is_connected==false){
//
//
//                        }
//
//
//                    }catch (Exception e){
//                        try {
//                            switch (mPrintType){
//                                case "testprint5":
//                                case "kitchen5":
//                                    Giant_mPrinterkitchen5.resetPrinter();
//                                    break;
//                            }
//                        } catch (Exception e1) {
//                            Log.e("jihun park reset error","" + e.toString());
//                        }
//                        Log.e("jihun park connect error","" + e.toString());
//                    }
//                } while (!b_is_connected);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                switch (mPrintType) {
//                    case "kitchen5" : {
//                        printKitchenReceipt5(jsonObject);
//                        break;
//                    }
//                    case "testprint5":
//                        testPrint("5");
//                        break;
//
//                }
//                super.onPostExecute(o);
//            }
//        };
//        asyncTask.execute();

    }
    //  연결 확인..

    public void kitchenPrint_connect_test(){
        if (GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext).equals("Giant-100")){
            Thread thread1 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    boolean b_is_connected = false;
                    int count = 0;
                    do {
                        b_is_connected = false;

                        try {

                            Giant_mPrinterkitchen1 = new Sam4sPrint();
                            b_is_connected = Giant_mPrinterkitchen1.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP2, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT2));
                            count++;
                            if (b_is_connected==false){

                                try {
//                                    if (count > 3){
//                                        //Thread.sleep(2000);
//                                        Giant_mPrinterkitchen1.resetPrinter();
//                                    }
                                } catch (Exception e1) {
                                    Log.e("jihun park reset error","" + e1.toString());
                                }
                            } else {
                                testPrint("1");
                            }
                        }catch (Exception e){
                            try {
//                                if (count > 3){
//                                    //Thread.sleep(2000);
//                                    Giant_mPrinterkitchen1.resetPrinter();
//                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e.toString());
                            }
                            Log.e("sam4 connect error","" + e.toString());
                        }
                        if (count > 5){
                            b_is_connected = true;
                        }
                        if (b_stopReTry1) {
                            b_is_connected = true;
                            b_stopReTry1 = false;
                        }
                    } while (!b_is_connected);


                }
            };
            thread1.start();
        }
        if (GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext).equals("Giant-100")){
            Thread thread2 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    boolean b_is_connected = false;
                    int count = 0;
                    do {
                        b_is_connected = false;
                        try {

                            Giant_mPrinterkitchen2 = new Sam4sPrint();
                            b_is_connected = Giant_mPrinterkitchen2.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP3, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT3));
                            count++;
                            if (b_is_connected==false){

                                try {
//                                    if (count > 3){
//                                        //Thread.sleep(2000);
//                                        Giant_mPrinterkitchen2.resetPrinter();
//                                    }
                                } catch (Exception e1) {
                                    Log.e("jihun park reset error","" + e1.toString());
                                }
                            } else {
                                testPrint("2");
                            }
                        }catch (Exception e){
                            try {
//                                if (count > 3){
//                                    //Thread.sleep(2000);
//                                    Giant_mPrinterkitchen2.resetPrinter();
//                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e.toString());
                            }
                            Log.e("sam4 connect error","" + e.toString());
                        }
                        if (count > 5){
                            b_is_connected = true;
                        }
                        if (b_stopReTry2) {
                            b_is_connected = true;
                            b_stopReTry2 = false;
                        }
                    } while (!b_is_connected);


                }
            };
            thread2.start();
        }

        if (GlobalMemberValues.getSavedPrinterNameForKitchen4(MainActivity.mContext).equals("Giant-100")){
            Thread thread3 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    boolean b_is_connected = false;
                    int count = 0;
                    do {
                        b_is_connected = false;
                        try {

                            Giant_mPrinterkitchen3 = new Sam4sPrint();
                            b_is_connected = Giant_mPrinterkitchen3.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP4, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT4));
                            count++;
                            if (b_is_connected==false){

                                try {
//                                    if (count > 3){
//                                        //Thread.sleep(2000);
//                                        Giant_mPrinterkitchen3.resetPrinter();
//                                    }
                                } catch (Exception e1) {
                                    Log.e("jihun park reset error","" + e1.toString());
                                }
                            } else {
                                testPrint("3");
                            }
                        }catch (Exception e){
                            try {
//                                if (count > 3){
//                                    //Thread.sleep(2000);
//                                    Giant_mPrinterkitchen3.resetPrinter();
//                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e.toString());
                            }
                            Log.e("sam4 connect error","" + e.toString());
                        }
                        if (count > 5){
                            b_is_connected = true;
                        }
                        if (b_stopReTry3) {
                            b_is_connected = true;
                            b_stopReTry3 = false;
                        }
                    } while (!b_is_connected);


                }
            };
            thread3.start();
        }

        if (GlobalMemberValues.getSavedPrinterNameForKitchen5(MainActivity.mContext).equals("Giant-100")){
            Thread thread4 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    boolean b_is_connected = false;
                    int count = 0;
                    do {
                        b_is_connected = false;
                        try {

                            Giant_mPrinterkitchen4 = new Sam4sPrint();
                            b_is_connected = Giant_mPrinterkitchen4.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP5, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT5));
                            count++;
                            if (b_is_connected==false){

                                try {
//                                    if (count > 3){
//                                        //Thread.sleep(2000);
//                                        Giant_mPrinterkitchen4.resetPrinter();
//                                    }
                                } catch (Exception e1) {
                                    Log.e("jihun park reset error","" + e1.toString());
                                }
                            } else {
                                testPrint("4");
                            }
                        }catch (Exception e){
                            try {
//                                if (count > 3){
//                                    //Thread.sleep(2000);
//                                    Giant_mPrinterkitchen4.resetPrinter();
//                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e.toString());
                            }
                            Log.e("sam4 connect error","" + e.toString());
                        }
                        if (count > 5){
                            b_is_connected = true;
                        }
                        if (b_stopReTry4) {
                            b_is_connected = true;
                            b_stopReTry4 = false;
                        }
                    } while (!b_is_connected);


                }
            };
            thread4.start();
        }

        if (GlobalMemberValues.getSavedPrinterNameForKitchen6(MainActivity.mContext).equals("Giant-100")){
            Thread thread5 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    boolean b_is_connected = false;
                    int count = 0;
                    do {
                        b_is_connected = false;
                        try {
                            Giant_mPrinterkitchen5 = new Sam4sPrint();
                            b_is_connected = Giant_mPrinterkitchen5.openPrinter(Sam4sPrint.DEVTYPE_ETHERNET, GlobalMemberValues.NETWORKPRINTERIP6, GlobalMemberValues.getIntAtString(GlobalMemberValues.NETWORKPRINTERPORT6));
                            count++;
                            if (b_is_connected==false){

                                try {
//                                    if (count > 3){
//                                        //Thread.sleep(2000);
//                                        Giant_mPrinterkitchen5.resetPrinter();
//                                    }
                                } catch (Exception e1) {
                                    Log.e("jihun park reset error","" + e1.toString());
                                }
                            } else {
                                testPrint("5");
                            }
                        }catch (Exception e){
                            try {
//                                if (count > 3){
//                                    //Thread.sleep(2000);
//                                    Giant_mPrinterkitchen5.resetPrinter();
//                                }
                            } catch (Exception e1) {
                                Log.e("jihun park reset error","" + e.toString());
                            }
                            Log.e("sam4 connect error","" + e.toString());
                        }
                        if (count > 5){
                            b_is_connected = true;
                        }
                        if (b_stopReTry5) {
                            b_is_connected = true;
                            b_stopReTry5 = false;
                        }
                    } while (!b_is_connected);
                }


            };
            thread5.start();
        }

    }


    public String getDate(){
        String nowDate = "";
        Date curDate;
        String     strCurTime;
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        curDate = new Date();
        strCurTime = sdf.format(curDate);
        System.out.println(strCurTime);
        nowDate = strCurTime;
        return nowDate;
    }

    public void connectAndTestprint(String printnum){
        if (printnum.equals("main")){
            testPrintN();
        } else {
            testPrint(printnum);
        }

    }


    public static void Sam4sPrinterReset(String printerNum){

        switch (printerNum){
            case "main" :
                b_stopReTryMain = true;
                Giant_mPrinter = null;
                break;
            case "1" :
                b_stopReTry1 = true;
                Giant_mPrinterkitchen1 = null;
                break;
            case "2" :
                b_stopReTry2 = true;
                Giant_mPrinterkitchen2 = null;
                break;
            case "3" :
                b_stopReTry3 = true;
                Giant_mPrinterkitchen3 = null;
                break;
            case "4" :
                b_stopReTry4 = true;
                Giant_mPrinterkitchen4 = null;
                break;
            case "5" :
                b_stopReTry5 = true;
                Giant_mPrinterkitchen5 = null;
                break;
            case "all" :
                b_stopReTryMain = true;
                b_stopReTry1 = true;
                b_stopReTry2 = true;
                b_stopReTry3 = true;
                b_stopReTry4 = true;
                b_stopReTry5 = true;

                Giant_mPrinter = null;
                Giant_mPrinterkitchen1 = null;
                Giant_mPrinterkitchen2 = null;
                Giant_mPrinterkitchen3 = null;
                Giant_mPrinterkitchen4 = null;
                Giant_mPrinterkitchen5 = null;
        }
    }

}
