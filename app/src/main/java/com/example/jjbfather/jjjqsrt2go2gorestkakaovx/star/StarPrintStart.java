package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.Payment;
import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

public class StarPrintStart {
    public StarIoExtManager mStarIoExtManager;
    JSONObject jsonObject;
    String printType;

    public StarPrintStart(){

        mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
        if (mStarIoExtManager == null) return;
        mStarIoExtManager.setCashDrawerOpenActiveHigh(true);
    }

    public StarPrintStart(String kitchenKind){
        switch (kitchenKind) {
            case "kitchen1" :
            case "testprint1" :
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp2(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
            case "kitchen2" :
            case "testprint2" :
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp3(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
            case "kitchen3" :
            case "testprint3" :
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp4(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
            case "kitchen4" :
            case "testprint4" :
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp5(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
            case "kitchen5" :
            case "testprint5" :
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
            default:
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "TCP:" + GlobalMemberValues.getNetworkPrinterIp2(MainActivity.mContext), "", 10000, null);     // 10000mS!!!
                break;
        }
        mStarIoExtManager.setCashDrawerOpenActiveHigh(true);

    }

    public void run(String printType, JSONObject data){

        this.jsonObject = data;
        this.printType = printType;

        if (mStarIoExtManager == null) return;

        mStarIoExtManager.setListener(mStarIoExtManagerListener);
        mStarIoExtManager.connect(mConnectionCallback);

    }

    public void openDrawer(){


        PrinterSettingManager settingManager = new PrinterSettingManager(MainActivity.mContext);
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(27);

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendPeripheral(ICommandBuilder.PeripheralChannel.No1);

        builder.endDocument();

        byte[] data = builder.getCommands();
        Communication.sendCommandsDoNotCheckCondition(this, data, "TCP:" + GlobalMemberValues.getNetworkPrinterIp(MainActivity.mContext), "", 10000, MainActivity.mContext, mCallback);     // 10000mS!!!
    }

    public void disconnect(){
        mStarIoExtManager.disconnect(mConnectionCallback);
    }

    public void receiptPrint(){

    }
    public void kitchenPrint(){

    }
    public void testPrint(){

    }

    private final ConnectionCallback mConnectionCallback = new ConnectionCallback() {
        @Override
        public void onConnected(boolean result, int resultCode) {
//            if (!mIsForeground) {
//                return;
//            }
//
//            if (!result) {
//                if (mProgressDialog != null) {
//                    mProgressDialog.dismiss();
//                }
//
//                String message;
//
//                if (resultCode == StarResultCode.FAILURE_IN_USE) {
//                    message = "Check the device. (In use)\nThen touch up the Refresh button.";
//                }
//                else {
//                    message = "Check the device. (Power and Bluetooth pairing)\nThen touch up the Refresh button.";
//                }
//
////                mComment.setText(message);
////                mComment.setTextColor(Color.RED);
//
////                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(OPEN_FAILURE_DIALOG);
////                dialog.setTitle("Communication Result");
////                dialog.setMessage(Communication.getCommunicationResultMessage(new CommunicationResult(Result.ErrorOpenPort, resultCode)));
////                dialog.setPositiveButton("OK");
////                dialog.show(getChildFragmentManager());
//
//                return;
//            }
            if (printType.equals("payment")
                || printType.equals("void")
                || printType.equals("return")){
                if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                    print("merchant");
                }
                // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
                if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
                    if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                        print("customer");
                    }
                }

                // 고객이 영수증 발급타입을 선택하는 경우가 아닐 때에만 주방프린트 실행
                // (고객이 영수증 발급타입을 선택하는 경우에는 앞에서 주방프린트가 실행되었음)
                int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                        "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
                if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
                    if (Payment.jsonroot_kitchen == null) return;
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

            } else if (printType.equals("kitchen1") || printType.equals("kitchen2") ||printType.equals("kitchen3") ||printType.equals("kitchen4") ||printType.equals("kitchen5")){
                // kitchen print
                printKitchen();
            } else if (printType.equals("testprint") || printType.equals("testprint1") || printType.equals("testprint2") || printType.equals("testprint3") || printType.equals("testprint4") || printType.equals("testprint5")){
                // test print
                printTest();
            }
            else {
                print("");
            }


//            if (mIsFirst) {
//                mIsFirst = false;
//
//                print();
//            }
//            else if (mProgressDialog != null) {
//                mProgressDialog.dismiss();
//            }
        }

        @Override
        public void onDisconnected() {
            // do nothing
        }
    };

    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onPrinterImpossible() {
//            mComment.setText("Printer Impossible.");
//
//            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterOnline() {
//            mComment.setText("Printer Online.");
//
//            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onPrinterOffline() {
//          mComment.setText("Printer Offline.");
//
//          mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterPaperReady() {
//          mComment.setText("Printer Paper Ready.");
//
//          mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onPrinterPaperNearEmpty() {
//            mComment.setText("Printer Paper Near Empty.");
//
//            mComment.setTextColor(0xffffa500);     // Orange
        }

        @Override
        public void onPrinterPaperEmpty() {
//            mComment.setText("Printer Paper Empty.");
//
//            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterCoverOpen() {
//            mComment.setText("Printer Cover Open.");
//
//            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterCoverClose() {
//          mComment.setText("Printer Cover Close.");
//
//          mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onAccessoryConnectSuccess() {
//            mComment.setText("Accessory Connect Success.");
//
//            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onAccessoryConnectFailure() {
//            mComment.setText("Accessory Connect Failure.");
//
//            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onAccessoryDisconnect() {
//            mComment.setText("Accessory Disconnect.");
//
//            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onStatusUpdate(String status) {
        }
    };

    private void print(String isCustomerMerchant) {

//        mProgressDialog.show();

        String method = "";
        LinearLayout getPrintingLn = null;

        switch (printType){
            case "payment" :
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject, "sale", isCustomerMerchant);
                break;
            case "batchsummary" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatch(jsonObject);
                break;
            }
            case "batchdetail" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatchDetail(jsonObject);
                break;
            }
            case "void" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject, "void", isCustomerMerchant);
                break;
            }
            case "return" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject, "return", isCustomerMerchant);
                break;
            }
            case "openCashDrawer" : {
//                return onOpenDrawer();
                break;
            }
            case "cashout" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(jsonObject, "");
                break;
            }
            case "startingcash" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForStartingCash(jsonObject);
                break;
            }
            case "phoneordercheckprint" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(jsonObject);
                break;
            }
            case "tablemain_checkprint" : {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView(jsonObject);
//                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView_onlyView(data);
                break;
            }
        }

        byte[] data = new byte[0];

        PrinterSettingManager settingManager = new PrinterSettingManager(MainActivity.mContext);
        PrinterSettings settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(27);
        int paperSize = 832;
        int px = 576;
        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(0, paperSize);
        
        if (getPrintingLn != null) {
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
            data = PrinterFunctions.createRasterReceiptData(emulation, bitMap);
        }

        if (data == null) return;

        Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), 30000, mCallback);     // 10000mS!!!
    }

    private void printKitchen() {

//        mProgressDialog.show();

        String method = "";
        LinearLayout getPrintingLn = null;
        String printnum = printType.replace("kitchen" , "");
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(jsonObject,printnum);


        byte[] data = new byte[0];

        PrinterSettingManager settingManager = new PrinterSettingManager(MainActivity.mContext);
        PrinterSettings settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(27);
        int paperSize = 832;
        int px = 576;
        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(0, paperSize);

        if (getPrintingLn != null) {
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
            data = PrinterFunctions.createRasterReceiptData(emulation, bitMap);
        }

        if (data == null) return;

        Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), 30000, mCallback);     // 10000mS!!!
    }

    private void printTest() {

//        mProgressDialog.show();

        byte[] data = new byte[0];

        PrinterSettingManager settingManager = new PrinterSettingManager(MainActivity.mContext);
        PrinterSettings settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(27);
        int paperSize = 832;
        int px = 576;
        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(0, paperSize);

        data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, false);

        Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), 30000, mCallback);     // 10000mS!!!
    }

    //
    private final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(Communication.CommunicationResult communicationResult) {
//            if (!mIsForeground) {
//                return;
//            }
//
//            if (mProgressDialog != null) {
//                mProgressDialog.dismiss();
//            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
            dialog.setPositiveButton("OK");
//            dialog.show(getChildFragmentManager());

            disconnect();
        }
    };
}
