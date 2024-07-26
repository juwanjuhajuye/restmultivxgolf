package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.cashchanger.CashChanger;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EpsonReceiptPrint implements ReceiveListener {

    private static final int REQUEST_PERMISSION = 100;

    private Context mContext = null;
    public static Printer  mPrinter = null;
    public JSONObject jsonObject_kitchen;
    static CashChanger mCashChanger = null;

    public EpsonReceiptPrint(Context context){
        this.mContext = context;
    }

    public boolean runPrintReceiptSequence(JSONObject data, final String printType, JSONObject kitchen) {
        jsonObject_kitchen = kitchen;
        if (!initializeObject()) {
            return false;
        }
        if (printType.equals("testprint")
                || printType.equals("testprint1")) {
            if (!createReceiptTestData()) {
                finalizeObject();
                return false;
            }
        } else {

            switch (printType) {
                case "payment" : {
                    if (!printSalesReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "batchsummary" : {
                    if (!printBatchReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "batchdetail" : {
                    if (!printBatchDetailReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "void" : {
                    if (!printVoidReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "return" : {
                    if (!printReturnReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "openCashDrawer" : {
                    if (!createReceiptData(null,"","","openCashDrawer")){
                        finalizeObject();
                        return false;
                    } else {
                        return true;
                    }

//                    connectCashDrawerProcess();
//                    break;
                }
                case "cashout" : {
                    if (!printCashOutReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "startingcash" : {
                    if (!printStartingCashReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "phoneordercheckprint" : {
                    if (!printPhoneOrderCheckReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;
                }
                case "testprint":
//                testPrintN();
                    break;
                case "menu_print" :
                    ArrayList<LinearLayout> getPrintingLn_arr = CloverMakingViewInPrinting.makingLinearLayout_menu(data);

                    if (getPrintingLn_arr != null) {
                        for (int i = 0 ; i < getPrintingLn_arr.size() ; i++){
                            if (!printSales_Menu(mContext,getPrintingLn_arr.get(i))){
                                finalizeObject();
                                return false;
                            }
                        }
                    }
                    break;
                case "tablemain_checkprint" : {
                    if (!printTablemain_CheckReceipt(data)){
                        finalizeObject();
                        return false;
                    }
                    break;

                }

            }

//            if (!createReceiptData(data,printType)) {
//                finalizeObject();
//                return false;
//            }
        }


        if (!printData()) {
            finalizeObject();
//            return false;
        }

        if ("payment" == printType){
            if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
                GlobalMemberValues.mReReceiptprintYN = "N";
            } else {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        boolean b_exit = true;
                        while (b_exit){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (mPrinter == null){
                                kitchen_handler.sendEmptyMessage(0);
                                b_exit = false;
                            }
                        }

                    }
                };
                thread.setDaemon(true);
                thread.start();
            }

        }
        if ("void" == printType || "return" == printType){

            if (!GlobalMemberValues.isVoidReturnKitchenPrinting()){
                return true;
            }


            if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
                GlobalMemberValues.mReReceiptprintYN = "N";
            } else {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        boolean b_exit = true;
                        while (b_exit){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (mPrinter == null){
                                kitchen_handler_void_return.sendEmptyMessage(0);
                                b_exit = false;
                            }
                        }

                    }
                };
                thread.setDaemon(true);
                thread.start();
            }

        }

        return true;
    }

    private boolean initializeObject() {
        try {
            mPrinter = new Printer(Printer.TM_T88,Printer.LANG_EN,mContext);
        }
        catch (Exception e) {
            EpsonPrinterShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }
    private boolean initializeObjectTest() {
        if (mPrinter == null){
            try {
                mPrinter = new Printer(Printer.TM_T88,Printer.LANG_EN,mContext);
            }
            catch (final Exception e) {
                MainActivity.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EpsonPrinterShowMsg.showExceptionConnectTest(e, "Printer", mContext,"Receipt Printer");
                    }
                });
                return false;
            }
        }


        mPrinter.setReceiveEventListener(this);

        return true;
    }


    private boolean createReceiptData(JSONObject data, String printType, String isCusmerMerchant, String str_receiptType) {

        String method = "";
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
                return onOpenDrawer();
//                break;
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
//                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView_onlyView(data);
                break;
            }
        }


//        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data,  "1");
        if (getPrintingLn != null) {
            int px = 510;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
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
            if (mPrinter == null) {
                return false;
            }
            try {
                method = "addImage";
                mPrinter.addImage(bitMap, 0, 0,
                        bitMap.getWidth(),
                        bitMap.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_AUTO);
                mPrinter.addCut(Printer.CUT_FEED);
            } catch (Exception e){
                EpsonPrinterShowMsg.showException(e, method, mContext);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean createReceiptTestData() {
        String method = "";
        StringBuilder textData = new StringBuilder();
        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);
            textData.append("Test Printer.........\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Exception e){
            EpsonPrinterShowMsg.showException(e, method, mContext);
            return false;
        }
        textData = null;
        return true;
    }

    private boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        if (dispPrinterWarnings(status).equals(""))
        {
        } else {
            return false;
        }

        if (!isPrintable(status)) {
            EpsonPrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            EpsonPrinterShowMsg.showException(e, "sendData", mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }
    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect("TCP:" + GlobalMemberValues.NETWORKPRINTERIP, Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            EpsonPrinterShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        }
        catch (Exception e) {
            EpsonPrinterShowMsg.showException(e, "beginTransaction", mContext);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            }
            catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }
    public boolean connectTestPrinter() {
        if (!initializeObjectTest()) {
            return false;
        }
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        final PrinterStatusInfo status = mPrinter.getStatus();

        if (!isPrintable(status)) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EpsonPrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
                }
            });

            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }




        try {
            mPrinter.connect("TCP:" + GlobalMemberValues.NETWORKPRINTERIP, Printer.PARAM_DEFAULT);
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "connect", mContext,"Receipt Printer");
                }
            });

            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "beginTransaction", mContext, "Receipt Printer");
                }
            });
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            }
            catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private String dispPrinterWarnings(PrinterStatusInfo status) {
        String warningsMsg = "";
        if (status == null) {
            return "";
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += "Roll paper is nearly end.\\n";
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += "Battery level of printer is low.\\n";
        }
        return warningsMsg;
    }
    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        }
        else if (status.getOnline() == Printer.FALSE) {
            return false;
        }
        else {
            ;//print available
        }

        return true;
    }
    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += mContext.getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += mContext.getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += mContext.getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_autocutter);
            msg += mContext.getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += mContext.getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += mContext.getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    EpsonPrinterShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    EpsonPrinterShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        MainActivity.mActivity.runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                EpsonPrinterShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);
//                updateButtonState(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }


    // add up

    public boolean printSalesReceipt(final JSONObject data) {
        boolean result = false;

        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
            result = createReceiptData(data,"sales","merchant","payment");
        }
        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
            if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                result = createReceiptData(data, "sales", "customer", "payment");
            }
        }
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        // reprint 일 경우...


        // 돈통 열기 cash drawer
        if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
            //            GlobalMemberValues.paxOpenDrawer();
        }


        return result;
    }

    public boolean printSales_Menu(Context context, LinearLayout getPrintingLn){

        String method = "";
        if (getPrintingLn != null) {
            int px = 510;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
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
            if (mPrinter == null) {
                return false;
            }
            try {
                method = "addImage";
                mPrinter.addImage(bitMap, 0, 0,
                        bitMap.getWidth(),
                        bitMap.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_AUTO);
                mPrinter.addCut(Printer.CUT_FEED);
            } catch (Exception e){
                EpsonPrinterShowMsg.showException(e, method, mContext);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean printBatchReceipt(final JSONObject data){
        return createReceiptData(data,"batchsummary","","batchsummary");
    }
    public boolean printBatchDetailReceipt(final JSONObject data){
        return createReceiptData(data,"batchdetail","","batchdetail");
    }

    public boolean printVoidReceipt(final JSONObject data) {
        boolean result = false;
        result = createReceiptData(data,"void", "merchant","void");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                // Customer 영수증 프린트 -----------------------------------------------`-----------------------------------------------------------------------------
        result = createReceiptData(data,"void", "customer","void");
        return result;
    }
    public boolean printReturnReceipt(final JSONObject data) {
        boolean result = false;
        result = createReceiptData(data,"return", "merchant","return");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                // Customer 영수증 프린트 -----------------------------------------------`-----------------------------------------------------------------------------
        result = createReceiptData(data,"return", "customer","return");
        ((MainActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SaleHistory.mActivity.recreate();
            }
        });

//        SaleHistory.mActivity.recreate();     SaleHistory.setSearchSalesHistory();
        return result;
    }


    public boolean printCashOutReceipt(final JSONObject data) {

        return createReceiptData(data,"cashout","","cashout");
    }
    public boolean printStartingCashReceipt(final JSONObject data) {

        return createReceiptData(data,"startingcash","","startingcash");

    }
    public boolean printPhoneOrderCheckReceipt(final JSONObject data) {
        return createReceiptData(data,"phoneordercheckprint","","phoneordercheckprint");
    }

    public boolean printTablemain_CheckReceipt(final JSONObject data) {
        return createReceiptData(data,"tablemain_checkprint","","tablemain_checkprint");
    }


    public void printToKitchen(){
//        if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
//            GlobalMemberValues.mReReceiptprintYN = "N";
//            return;
//        }
        // 결제 리뷰창 오픈
        Payment.openPaymentReview(Payment.context);

        // 고객이 영수증 발급타입을 선택하는 경우가 아닐 때에만 주방프린트 실행
        // (고객이 영수증 발급타입을 선택하는 경우에는 앞에서 주방프린트가 실행되었음)
        int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
        if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
            try {
                if (GlobalMemberValues.getReceiptFooterKitchen2() == null || GlobalMemberValues.getReceiptFooterKitchen2() == ""){
                } else {
                    jsonObject_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String tempSalesCode = "";
            try {
                if (jsonObject_kitchen != null) {
                    if (jsonObject_kitchen.isNull("receiptno")){

                    } else {
                        tempSalesCode = jsonObject_kitchen.getString("receiptno");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                // 주방프린트 하기 ----------------------------------------------------------------
                GlobalMemberValues.printGateByKitchen(jsonObject_kitchen, MainActivity.mContext, "kitchen1");
                // -------------------------------------------------------------------------------
            } else {
                if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                        // 주방프린트 하기 ----------------------------------------------------------------
                        GlobalMemberValues.printGateByKitchen(jsonObject_kitchen, MainActivity.mContext, "kitchen1");
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
    }

    public void printToKitchen_void_return(){

        // 고객이 영수증 발급타입을 선택하는 경우가 아닐 때에만 주방프린트 실행
        // (고객이 영수증 발급타입을 선택하는 경우에는 앞에서 주방프린트가 실행되었음)
        int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
        if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
            try {
                if (GlobalMemberValues.getReceiptFooterKitchen2() == null || GlobalMemberValues.getReceiptFooterKitchen2() == ""){
                } else {
                    jsonObject_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String tempSalesCode = "";

            tempSalesCode = GlobalMemberValues.getDataInJsonData(jsonObject_kitchen, "receiptno");

//            try {
//                tempSalesCode = jsonObject_kitchen.getString("receiptno");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                // 주방프린트 하기 ----------------------------------------------------------------
                GlobalMemberValues.printGateByKitchen(jsonObject_kitchen, MainActivity.mContext, "kitchen1");
                // -------------------------------------------------------------------------------
            } else {
                if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                        // 주방프린트 하기 ----------------------------------------------------------------
                        GlobalMemberValues.printGateByKitchen(jsonObject_kitchen, MainActivity.mContext, "kitchen1");
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
    }

    private boolean onOpenDrawer() {

        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        if (dispPrinterWarnings(status).equals(""))
        {
        } else {
            return false;
        }

        if (!isPrintable(status)) {
            EpsonPrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }


        try {
            mPrinter.addPulse(mPrinter.DRAWER_2PIN,1);
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Epos2Exception e) {
            EpsonPrinterShowMsg.showException(e, "openDrawer", mContext);
            e.printStackTrace();
            return false;
        }

        return true;
    }


    Handler kitchen_handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            printToKitchen();
        }

    };
    Handler kitchen_handler_void_return = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            printToKitchen_void_return();
        }

    };
}
