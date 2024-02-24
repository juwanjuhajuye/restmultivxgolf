package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.printer.posbank.Printer;

import org.json.JSONObject;

/**
 * Created by jihun.park on 16. 8. 11..
 */
public class PosBankPrinterStart {

    ProgressDialog asyncDialog;
//    static com.printer.posbank.Printer mPrinter;
    private String mConnectedDeviceName = null;

    Context mContext;
    String mLocation;

    Context dialContext;

    JSONObject jsonObject;

    String mPrintType = "";

    public PosBankPrinterStart(Context context, JSONObject jsonObject, String paramPrintType){
        GlobalMemberValues.logWrite("inconnectmethod", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
        this.mContext = context;
        this.jsonObject = jsonObject;
        this.mPrintType = paramPrintType;
        GlobalMemberValues.logWrite("phoneordercheckprintlog", "mPrintType : " + mPrintType + "\n");
    }

    public void setConnect(){
            GlobalMemberValues.disconnectPrinter();

            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...0 : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
            PosbankPrinter.mPrinter = new Printer(mContext, mHandler, null);
            asyncDialog = new ProgressDialog(mContext);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("Loading to connect printer device (POSBANK A11 -- RECEIPT)...");
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼

                    }
                    Log.e("Loading..","KeyEvent.KEYCODE_BACK");
                    return true;
                }
            });
            // show dialog
            //asyncDialog.show();
            asyncTaskMSalon.execute();

    }

    AsyncTask asyncTaskMSalon = new AsyncTask() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Object doInBackground(Object[] params) {
            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...1 : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
            PosbankPrinter.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP, 9100, 5000);
            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...2 : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //GlobalMemberValues.logWrite("inconnectmethod", "여기실행...3 : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
            switch (mPrintType) {
                case "payment" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printSalesReceiptPosbank(jsonObject);
                    break;
                }
                case "batchsummary" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printBatchPosbank(jsonObject);
                    break;
                }
                case "batchdetail" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printBatchDetailPosbank(jsonObject);
                    break;
                }
                case "void" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printVoidReceiptPosbank(jsonObject);
                    break;
                }
                case "return" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printReturnReceiptPosbank(jsonObject);
                    break;
                }
                case "testprint" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printTestPosbank(jsonObject);
                    break;
                }
                case "openCashDrawer" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.openCashDrawerPosbank();
                    break;
                }
                case "cashout" : {
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printCashOutPosbank(jsonObject);
                    break;
                }
                case "phoneordercheckprint" : {
                    GlobalMemberValues.logWrite("phoneordercheckprintlog", "여기실행..." + "\n");
                    PrinterReceipt printerReceipt = new PrinterReceipt();
                    printerReceipt.printPhoneOrderCheckPrint(jsonObject);
                    break;
                }
                case "menu_print" : {
                    // 메뉴프린트 미구현
                }
            }
            //GlobalMemberValues.logWrite("inconnectmethod", "여기실행...4 : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");
        }


    } ;


    public void setDisconnect() {
        if (PosbankPrinter.mPrinter != null) {
            PosbankPrinter.mPrinter.disconnect();
            GlobalMemberValues.PRINTERCONNECTEDSTATUS = false;
        }
    }

    public static final String TAG = "PosbankPrinter";

    static final int MESSAGE_START_WORK = Integer.MAX_VALUE - 2;
    static final int MESSAGE_END_WORK = Integer.MAX_VALUE - 3;

    public final Handler mHandler = new Handler(new Handler.Callback() {

        @SuppressWarnings("unchecked")
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "mHandler.handleMessage(" + msg + ")");
            if (asyncDialog != null && asyncDialog.isShowing()) {
                asyncDialog.dismiss();
            }
            switch (msg.what) {
                case Printer.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case Printer.STATE_CONNECTED:
                            mConnectedDeviceName = msg.getData().getString(Printer.KEY_STRING_DEVICE_NAME);
                            GlobalMemberValues.PRINTERCONNECTEDSTATUS = true;
                            Toast.makeText(mContext, "PosBank Printer Connect Completed!", Toast.LENGTH_LONG).show();
//                            GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "PosBank A11 Printer Connect Completed!", "Close");
                            break;

                        case Printer.STATE_CONNECTING:

                            break;

                        case Printer.STATE_NONE:

                            break;
                    }
                    //return true;
                    break;


                case Printer.MESSAGE_PRINT_COMPLETE:
                    Toast.makeText(mContext, "Complete to print", Toast.LENGTH_SHORT).show();
//                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Complete to print", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_INVALID_ARGUMENT:
                    Toast.makeText(mContext, "Invalid argument", Toast.LENGTH_SHORT).show();
//                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Invalid argument", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_NV_MEMORY_CAPACITY:
                    Toast.makeText(mContext, "NV memory capacity error", Toast.LENGTH_SHORT).show();
//                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "NV memory capacity error", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_OUT_OF_MEMORY:
                    Toast.makeText(mContext, "Out of memory", Toast.LENGTH_SHORT).show();
//                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Out of memory", "Close");
                    //return true;
                    break;

                case MESSAGE_START_WORK:

                    //return true;
                    break;

                case MESSAGE_END_WORK:

                    //return true;
                    break;

                case Printer.MESSAGE_NETWORK_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(mContext, "No connectable device", Toast.LENGTH_SHORT).show();
//                        GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "No connectable device", "Close");
                    }

//                    if (msg.obj != null) {
//                        Set<String> ipAddressSet = (Set<String>) msg.obj;
//                        final String[] items = ipAddressSet.toArray(new String[ipAddressSet.size()]);
//
//                        new AlertDialog.Builder(mContext).setTitle("Connectable network printers")
//                                .setItems(items, new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        mPrinter.connect(items[which], 9100, 5000);
//                                    }
//                                }).show();
//                    }

                    //return true;
                    break;
            }
            return false;
        }
    });

}
