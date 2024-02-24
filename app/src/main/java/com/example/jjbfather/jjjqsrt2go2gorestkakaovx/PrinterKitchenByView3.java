package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.printer.posbank.Printer;

import org.json.JSONObject;

import java.util.Locale;

public class PrinterKitchenByView3 {
    public PrinterKitchenByView3() {
        // 프린트에 사용된 언어 설정
        String getPrintLang = MainActivity.mDbInit.dbExecuteReadReturnString("select printlanguage from salon_storestationsettings_deviceprinter4");
        if (!GlobalMemberValues.isStrEmpty(getPrintLang)) {
            switch (getPrintLang) {
                case "EN" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.ENGLISH;
                    break;
                }
                case "KO" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.KOREAN;
                    break;
                }
                case "JP" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.JAPANESE;
                    break;
                }
                case "CH" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.CHINESE;
                    break;
                }
                case "FR" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.FRENCH;
                    break;
                }
                case "IT" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.ITALIAN;
                    break;
                }
            }
        } else {
            GlobalMemberValues.PRINTLOCALELANG = Locale.ENGLISH;
        }
    }

    public void printKitchenPosbank(final JSONObject data) {
        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
        GlobalMemberValues.openCloseKitchenPrintLoading(true, "3");

        if (PosbankPrinter4.mPrinter == null){
            if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
                GlobalMemberValues.initPhoneOrderValues();

                // 키친프린터 로딩창 종료...
                GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
            }

            // 프린터4 실행
            //GlobalMemberValues.printReceiptByKitchen4(data, MainActivity.mContext, "kitchen4");

            return;
        }

        if (data != null) {
            GlobalMemberValues.logWrite("kitchenPrintLog4", "프린트안 json -- 3 : " + data.toString() + "\n");
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간3 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(4000); // 2 초 기다림 (가장 적절한 시간임)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                String strMadeFilePath = "";

                String receiptNo = GlobalMemberValues.getDataInJsonData(data, "receiptno");
                String orderType = GlobalMemberValues.getDataInJsonData(data, "ordertype");

                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                LinearLayout getPrintingLn = null;
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data, "3");

                if (GlobalMemberValues.isPossibleKitchenPrinting(data, "3") && getPrintingLn != null) {
                    GlobalMemberValues.logWrite("returnlayouttxtlog", "layouttxt : " + getPrintingLn.toString() + "\n");

                    GlobalMemberValues.logWrite("printLnLog", "width : " + getPrintingLn.getWidth() + "\n");
                    GlobalMemberValues.logWrite("printLnLog", "height : " + getPrintingLn.getHeight() + "\n");


                    int px = 576;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

                    View emptyView = new View(MainActivity.mContext);
                    emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
                    getPrintingLn.addView(emptyView);

                    //setTextView(v);
                    getPrintingLn.setDrawingCacheEnabled(true);
                    getPrintingLn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());

                    //Log.d("MainActivity", "===================="+v.getWidth() + ":" + px);

                    Bitmap bitMap = Bitmap.createBitmap(px, getPrintingLn.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitMap);
                    canvas.drawColor(Color.WHITE);
                    getPrintingLn.draw(canvas);

                    getPrintingLn.setDrawingCacheEnabled(false);

                    for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("3"); temp++) {
                        PosbankPrinter4.mPrinter.printBitmap(bitMap, Printer.ALIGNMENT_CENTER, 600, 13, false, true, false);
                        GlobalMemberValues.posbankPrintText4("\n\n\n\n\n\n\n", Printer.ALIGNMENT_LEFT, 0, 0, false);
                        PosbankPrinter4.mPrinter.cutPaper(true);
                    }

                    GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = receiptNo;
                    GlobalMemberValues.logWrite("kitchenprintedlogs", "salescode : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");
                }

                getPrintingLn = null;

                GlobalMemberValues.disconnectPrinter4();

                GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
                GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 현재까지 주방프린터가 실행된 수와 전체 셋팅된 주방프린터의 수가 같을 때..
        if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
            GlobalMemberValues.setKitchenPrinterValues();

            // 키친프린터 로딩창 종료...
            GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
        }

        try {
            Thread.sleep(1500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 프린터4 실행
        GlobalMemberValues.printReceiptByKitchen4(data, MainActivity.mContext, "kitchen4");
    }

    public void printTestPosbank(final JSONObject data) {
        if (PosbankPrinter4.mPrinter == null){
            return;
        }
        Thread thread = new Thread(){
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;

                String testTxt = "";

                if (data != null) {
                    testTxt = data.toString();
                }

                if (GlobalMemberValues.isStrEmpty(testTxt)) {
                    testTxt = "No Kitchen Printed Data (3) " +
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "\nThe End (Wanhaye)..............";
                }

                GlobalMemberValues.posbankPrintText4(testTxt, 1, attribute, 0, false);
                PosbankPrinter4.mPrinter.cutPaper(true);

                //PosbankPrinter4.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN4);
                GlobalMemberValues.disconnectPrinter4();

            }
        };
        thread.start();
    }
}
