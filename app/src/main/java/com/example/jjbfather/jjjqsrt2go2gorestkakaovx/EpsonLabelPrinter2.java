package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.cashchanger.CashChanger;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/*
    public static final int TM_M10 = 0;
    public static final int TM_M30 = 1;
    public static final int TM_P20 = 2;
    public static final int TM_P60 = 3;
    public static final int TM_P60II = 4;
    public static final int TM_P80 = 5;
    public static final int TM_T20 = 6;
    public static final int TM_T60 = 7;
    public static final int TM_T70 = 8;
    public static final int TM_T81 = 9;
    public static final int TM_T82 = 10;
    public static final int TM_T83 = 11;
    public static final int TM_T88 = 12;
    public static final int TM_T90 = 13;
    public static final int TM_T90KP = 14;
    public static final int TM_U220 = 15;
    public static final int TM_U330 = 16;
    public static final int TM_L90 = 17;

    public static final int MODEL_ANK = 0;
    public static final int MODEL_JAPANESE = 1;
    public static final int MODEL_CHINESE = 2;
    public static final int MODEL_TAIWAN = 3;
    public static final int MODEL_KOREAN = 4;
    public static final int MODEL_THAI = 5;
    public static final int MODEL_SOUTHASIA = 6;
*/


public class EpsonLabelPrinter2 implements ReceiveListener {

    private static final int REQUEST_PERMISSION = 100;

    private Context mContext = null;
    public static Printer mLabel_Printer = null;
    public JSONObject jsonObject_kitchen;
    static CashChanger mCashChanger = null;

    ArrayAdapter<EpsonSpnModelsItem> seriesAdapter = null;
    ArrayAdapter<EpsonSpnModelsItem> langAdapter = null;

    public String str_printername = "";

    int sleepTime = 5000;

    public EpsonLabelPrinter2(Context context){
        this.mContext = context;

        seriesAdapter = new ArrayAdapter<EpsonSpnModelsItem>(MainActivity.mContext, android.R.layout.simple_spinner_item);
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_m10), Printer.TM_M10));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_m30), Printer.TM_M30));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_p20), Printer.TM_P20));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_p60), Printer.TM_P60));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_p80), Printer.TM_P80));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t20), Printer.TM_T20));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t60), Printer.TM_T60));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t70), Printer.TM_T70));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t81), Printer.TM_T81));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t82), Printer.TM_T82));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t83), Printer.TM_T83));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t83iii), Printer.TM_T83III));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_t88), Printer.TM_T88));

        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_l90), Printer.TM_L90));
        seriesAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.printerseries_l90lfc), Printer.TM_L90LFC));


        langAdapter = new ArrayAdapter<EpsonSpnModelsItem>(MainActivity.mContext, android.R.layout.simple_spinner_item);
        langAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.lang_ank), Printer.MODEL_ANK));
        langAdapter.add(new EpsonSpnModelsItem(mContext.getString(R.string.lang_korean), Printer.MODEL_KOREAN));

    }

    public boolean runPrintReceiptSequence(JSONObject data, String usb_address) {

        if (!initializeObject()) {
            return false;
        }
        if (!printSalesReceipt(data)){
            finalizeObject();
            return false;
        }

        if (!printData(usb_address)) {
            finalizeObject();
            return false;
        }

        return true;
    }

    public boolean runPrintReceiptSequence_array(JSONArray data, String usb_address, String printername) {

        if (!initializeObject()) {
            return false;
        }

        this.str_printername = printername;

        if (data == null) return false;

//        if (!connectPrinter(usb_address)) {
////            mLabel_Printer.clearCommandBuffer();
//            finalizeObject();
//            return false;
//        }

        for (int j = 0 ; data.length() > j ; j++){
            try {
                if (!printSalesReceipt(data.getJSONObject(j))){
                    finalizeObject();
                    return false;
                }
//                if (!printData_notconnect(usb_address)) {
//                    finalizeObject();
//                    return false;
//                }
                String receiptNo = GlobalMemberValues.getDataInJsonData(data.getJSONObject(j).getJSONArray("saleitemlist").getJSONObject(0), "receiptno");
                GlobalMemberValues.setPhoneorderLabelPrinted(receiptNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        mLabel_Printer.clearCommandBuffer();

        try {
            mLabel_Printer.addFeedLine(1);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }

        if (!printData(usb_address)) {
            finalizeObject();
            return false;
        }

        sleepTime = (1000 * data.length()) + 5000;


        return true;
    }

    private boolean initializeObject() {
        try {
            mLabel_Printer = new Printer(((EpsonSpnModelsItem) seriesAdapter.getItem(15)).getModelConstant(),
                    Printer.LANG_EN,
                    mContext);
//            mPrinter = new Printer(Printer.TM_L90,Printer.LANG_EN,mContext);
        }
        catch (Exception e) {
//            EpsonPrinterShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mLabel_Printer.setReceiveEventListener(this);

        return true;
    }

    public boolean runTestPrintReceiptSequence() {

        if (!initializeObject()) {
            return false;
        }
        if (!createReceiptTestData()) {
            finalizeObject();
            return false;
        }

        String str_1connectAddress = "";
        String qurey = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label2";
        Cursor qurey_result = MainActivity.mDbInit.dbExecuteRead(qurey);
        if (qurey_result.getCount() > 0 && qurey_result.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER") || printername.equals("No Printer")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(1), 1);
                if (is_usb.equals("1")) {
                    str_1connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(5), 1);
                    str_1connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }
            }
        }

        if (!printData(str_1connectAddress)) {
            finalizeObject();
            return false;
        }

        return true;
    }
    private boolean initializeObjectTest() {
        if (mLabel_Printer == null){
            try {
                mLabel_Printer = new Printer(Printer.TM_T88,Printer.LANG_EN,mContext);
            }
            catch (final Exception e) {
                MainActivity.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        EpsonPrinterShowMsg.showExceptionConnectTest(e, "Printer", mContext,"Receipt Printer");
                    }
                });
                return false;
            }
        }


        mLabel_Printer.setReceiveEventListener(this);

        return true;
    }


    private boolean createReceiptData(JSONObject data) {

        String method = "";
        LinearLayout getPrintingLn = null;
        getPrintingLn = CloverMakingViewInPrinting.makingLabelLinearLayout(data, str_printername);


//        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data,  "1");
        if (getPrintingLn != null) {
            int px = 350;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
            SharedPreferences pref = MainActivity.mContext.getSharedPreferences("labelprinter_info", MODE_PRIVATE);
//            String temp_order_printer_name = pref.getString("labelprinter_info_printer_name", "");
            String temp_printer_buzzer = pref.getString("labelprinter_info_printer_buzzer","N");

            if (str_printername.contains("58")){
                px = 530;
            } else {
                px = 337;
            }

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
            if (mLabel_Printer == null) {

                return false;
            }
            try {
                method = "addImage";
                mLabel_Printer.addImage(bitMap, 0, 0,
                        bitMap.getWidth(),
                        bitMap.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_AUTO);
                method = "addCut";
                mLabel_Printer.addCut(Printer.CUT_FEED);
                if (temp_printer_buzzer.equals("Y")){
                    int count = GlobalMemberValues.getLabelPrinterBuzzerCount();
                    for (int i = 0; i < count; i++){
                        mLabel_Printer.addPulse(Printer.DRAWER_2PIN, 1);
                    }
                }
//                method = "addFeedLine";
//                mLabel_Printer.addFeedLine(3);
            } catch (Exception e){
                mLabel_Printer.clearCommandBuffer();
                EpsonPrinterShowMsg.showException(e, method, mContext);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean createReceiptData_text(JSONObject data) {

        String method = "";
        if (mLabel_Printer == null) {
            return false;
        }
        try {
//            CloverMakingViewInPrinting.makingLabelLinearLayout_text(data, mLabel_Printer);
            mLabel_Printer.addCut(Printer.CUT_FEED);
        } catch (Exception e){
            EpsonPrinterShowMsg.showException(e, method, mContext);
            return false;
        }
        return true;
    }

    private boolean createReceiptTestData() {
        String method = "";
        StringBuilder textData = new StringBuilder();
        try {
            method = "addTextAlign";
            mLabel_Printer.addTextAlign(Printer.ALIGN_CENTER);
            method = "addFeedLine";
            mLabel_Printer.addFeedLine(1);
            textData.append("Test Print..\n");
            method = "addText";
            mLabel_Printer.addText(textData.toString());
            textData.delete(0, textData.length());

            mLabel_Printer.addCut(Printer.CUT_FEED);
        } catch (Exception e){
//            EpsonPrinterShowMsg.showException(e, method, mContext);
            return false;
        }
        textData = null;
        return true;
    }

    private boolean printData(String usb_address) {
        if (mLabel_Printer == null) {
            return false;
        }

        if (!connectPrinter(usb_address)) {
            mLabel_Printer.clearCommandBuffer();
            return false;
        }


        try {
            mLabel_Printer.sendData(40000);
        }
        catch (Exception e) {
            mLabel_Printer.clearCommandBuffer();
            try {
                mLabel_Printer.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean printData_notconnect(String usb_address) {
        if (mLabel_Printer == null) {
            return false;
        }
        // connectPrinter 부분 빠짐.
        try {
            mLabel_Printer.sendData(Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            mLabel_Printer.clearCommandBuffer();
            try {
                mLabel_Printer.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean connectPrinter(String str_usb_address) {
        boolean isBeginTransaction = false;
        if (mLabel_Printer == null) {
            return false;
        }
        if (str_usb_address.isEmpty()) return false;

        try {
            if (str_usb_address.contains("USB:")){
                mLabel_Printer.connect(str_usb_address, Printer.PARAM_DEFAULT);
            } else if (str_usb_address.contains("TCP:")){
                mLabel_Printer.connect(str_usb_address, Printer.PARAM_DEFAULT);

                try {
                    mLabel_Printer.beginTransaction();
                    isBeginTransaction = true;
                }
                catch (Exception e) {
                    EpsonPrinterShowMsg.showException(e, "beginTransaction", mContext);
                }

                if (isBeginTransaction == false) {
                    try {
                        mLabel_Printer.disconnect();
                    }
                    catch (Epos2Exception e) {
                        // Do nothing
                        return false;
                    }
                }
            } else {
                return false;
            }
//            mLabel_Printer.connect(str_usb_address, 30000);
        }
        catch (Exception e) {
//            EpsonPrinterShowMsg.showException(e, "connect", mContext);
            return false;
        }

        return true;
    }

    public boolean connectTestPrinter() {
        if (!initializeObjectTest()) {
            return false;
        }
        boolean isBeginTransaction = false;

        if (mLabel_Printer == null) {
            return false;
        }

        final PrinterStatusInfo status = mLabel_Printer.getStatus();

        if (!isPrintable(status)) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    EpsonPrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
                }
            });

            try {
                mLabel_Printer.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mLabel_Printer.connect("TCP:" + GlobalMemberValues.NETWORKPRINTERIP, Printer.PARAM_DEFAULT);
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "connect", mContext,"Receipt Printer");
                }
            });

            return false;
        }

        try {
            mLabel_Printer.beginTransaction();
            isBeginTransaction = true;
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "beginTransaction", mContext, "Receipt Printer");
                }
            });
        }

        if (isBeginTransaction == false) {
            try {
                mLabel_Printer.disconnect();
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
        if (mLabel_Printer == null) {
            return;
        }

        while (true) {
            try {
                mLabel_Printer.disconnect();
                break;
            } catch (final Exception e) {
                if (e instanceof Epos2Exception) {
                    //Note: If printer is processing such as printing and so on, the disconnect API returns ERR_PROCESSING.
                    if (((Epos2Exception) e).getErrorStatus() == Epos2Exception.ERR_PROCESSING) {
                        try {
                            Thread.sleep(500);
                        } catch (Exception ex) {
                        }
                    }else{
                        MainActivity.mActivity.runOnUiThread(new Runnable() {
                            public synchronized void run() {
//                                ShowMsg.showException(e, "disconnect", mContext);
                            }
                        });
                        break;
                    }
                }else{
                    MainActivity.mActivity.runOnUiThread(new Runnable() {
                        public synchronized void run() {
//                            ShowMsg.showException(e, "disconnect", mContext);
                        }
                    });
                    break;
                }
            }
        }

//        mLabel_Printer.clearCommandBuffer();
        finalizeObject();
    }

    private void finalizeObject() {
        if (mLabel_Printer == null) {
            return;
        }

        mLabel_Printer.clearCommandBuffer();

        mLabel_Printer.setReceiveEventListener(null);

        mLabel_Printer = null;
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        MainActivity.mActivity.runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }

    // add up

    public boolean printSalesReceipt(final JSONObject data) {
        boolean result = false;

        result = createReceiptData(data);
//        result = createReceiptData_text(data);


        return result;
    }

    public boolean printTest(Context context, LinearLayout getPrintingLn){

        String method = "";
        if (getPrintingLn != null) {
            int px = 350;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
            SharedPreferences pref = MainActivity.mContext.getSharedPreferences("labelprinter_info", MODE_PRIVATE);
            String temp_order_printer_name = pref.getString("labelprinter_info_printer_name", "");
            String temp_printer_buzzer = pref.getString("labelprinter_info_printer_buzzer","N");
            if (temp_order_printer_name.contains("58")){
                px = 530;
            } else {
                px = 337;
            }
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
            if (mLabel_Printer == null) {
                return false;
            }
            try {
                method = "addImage";
                mLabel_Printer.addImage(bitMap, 0, 0,
                        bitMap.getWidth(),
                        bitMap.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_AUTO);
                mLabel_Printer.addCut(Printer.CUT_FEED);
                if (temp_printer_buzzer.equals("Y")){
                    int count = GlobalMemberValues.getLabelPrinterBuzzerCount();
                    for (int i = 0; i < count; i++){
                        mLabel_Printer.addPulse(Printer.DRAWER_2PIN, 1);
                    }
                }
            } catch (Exception e){
                mLabel_Printer.clearCommandBuffer();
//                EpsonPrinterShowMsg.showException(e, method, mContext);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    private boolean onOpenDrawer() {

        if (mLabel_Printer == null) {
            return false;
        }

        if (!connectPrinter("")) {
            return false;
        }

        PrinterStatusInfo status = mLabel_Printer.getStatus();

        if (dispPrinterWarnings(status).equals(""))
        {
        } else {
            return false;
        }

        if (!isPrintable(status)) {
//            EpsonPrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mLabel_Printer.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }


        try {
            mLabel_Printer.addPulse(mLabel_Printer.DRAWER_2PIN,1);
            mLabel_Printer.sendData(Printer.PARAM_DEFAULT);
        } catch (Epos2Exception e) {
//            EpsonPrinterShowMsg.showException(e, "openDrawer", mContext);
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
