package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONObject;

public class EpsonPrinterKitchen4 implements ReceiveListener {

    private static final int REQUEST_PERMISSION = 100;

    private Context mContext = null;
    public static Printer  mPrinter = null;

    public EpsonPrinterKitchen4(Context context){
        this.mContext = context;
    }

    public boolean runPrintReceiptSequence(JSONObject data, String kitchenNum) {
        if (!initializeObject()) {
            return false;
        }

        if (kitchenNum.equals("testprint")
                || kitchenNum.equals("testprint4")) {
            if (!createReceiptTestData()) {
                finalizeObject();
                return false;
            }
        } else {
            if (!createReceiptData(data)) {
                finalizeObject();
                return false;
            }
        }

        if (!printData(data)) {
            finalizeObject();
            return false;
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
        if (mPrinter == null) {
            try {
                mPrinter = new Printer(Printer.TM_T88, Printer.LANG_EN, mContext);
            } catch (final Exception e) {
                MainActivity.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EpsonPrinterShowMsg.showExceptionConnectTest(e, "Printer", mContext, "Kitchen Printer 4");
                    }
                });
                return false;
            }
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }


    private boolean createReceiptData(JSONObject data) {
        String method = "";
        LinearLayout getPrintingLn = null;
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data,  "4");
        if (getPrintingLn != null) {
            int px = 510;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());
            View emptyView = new View(MainActivity.mContext);
            emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
            getPrintingLn.addView(emptyView);

            //setTextView(v);
            getPrintingLn.setDrawingCacheEnabled(true);
            getPrintingLn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());

            if (getPrintingLn.getHeight() <= 0){
                return false;
            }

            Bitmap bitMap = Bitmap.createBitmap(px, getPrintingLn.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitMap);
            canvas.drawColor(Color.WHITE);
            getPrintingLn.draw(canvas);

            getPrintingLn.setDrawingCacheEnabled(false);
            if (mPrinter == null) {
                return false;
            }
            try {
                for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("4"); temp++) {
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
                }
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

    private boolean printData(JSONObject data) {
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

        String receiptNo = GlobalMemberValues.getDataInJsonData(data, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(data, "ordertype");
        GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
        GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);
        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = receiptNo;
        GlobalMemberValues.logWrite("kitchenprintedlogs", "salescode : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

        return true;
    }
    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect("TCP:" + GlobalMemberValues.NETWORKPRINTERIP5, Printer.PARAM_DEFAULT);
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

        try {
            mPrinter.connect("TCP:" + GlobalMemberValues.NETWORKPRINTERIP5, Printer.PARAM_DEFAULT);
        }
        catch (final Exception e) {
            MainActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "connect", mContext,"Kitchen Printer 4");
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
                    EpsonPrinterShowMsg.showExceptionConnectTest(e, "beginTransaction", mContext, "Kitchen Printer 4");
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
}
