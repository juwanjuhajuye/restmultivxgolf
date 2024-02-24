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
public class PosBankPrinterStart2 {

    ProgressDialog asyncDialog;
//    static com.printer.posbank.Printer mPrinter;
    private String mConnectedDeviceName = null;

    Context mContext;
    String mLocation;

    Context dialContext;

    JSONObject jsonObject;

    String mPrintType = "";

    public PosBankPrinterStart2(Context context, JSONObject jsonObject, String paramPrintType){
        GlobalMemberValues.logWrite("inconnectmethod", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");
        this.mContext = context;
        this.jsonObject = jsonObject;
        this.mPrintType = paramPrintType;

        GlobalMemberValues.logWrite("secondkitchenlog", "mPrintType : " + mPrintType + "\n");
    }

    public void setConnect() {
            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...0 : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");
            GlobalMemberValues.logWrite("inconnectmethod", "GlobalMemberValues.NETWORKPRINTERIP3 : " + GlobalMemberValues.NETWORKPRINTERIP3 + "\n");

            if (GlobalMemberValues.isStrEmpty(mPrintType)) {
                mPrintType = "kitchen1";
            }

            GlobalMemberValues.logWrite("inconnectmethod", "mPrintType : " + mPrintType + "\n");

            if (mPrintType.equals("kitchen1") || mPrintType.equals("testprint")) {
                GlobalMemberValues.disconnectPrinter2();
                PosbankPrinter2.mPrinter = new Printer(mContext, mHandler, null);
            } else if (mPrintType.equals("kitchen2") || mPrintType.equals("testprint2")) {
                GlobalMemberValues.disconnectPrinter3();
                GlobalMemberValues.logWrite("kitchenprintinglog3", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP3 + "\n");
                PosbankPrinter3.mPrinter = new Printer(mContext, mHandler, null);
            } else if (mPrintType.equals("kitchen3") || mPrintType.equals("testprint3")) {
                GlobalMemberValues.disconnectPrinter4();
                GlobalMemberValues.logWrite("kitchenprintinglog4", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP4 + "\n");
                PosbankPrinter4.mPrinter = new Printer(mContext, mHandler, null);
            } else if (mPrintType.equals("kitchen4") || mPrintType.equals("testprint4")) {
                GlobalMemberValues.disconnectPrinter5();
                GlobalMemberValues.logWrite("kitchenprintinglog5", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP5 + "\n");
                PosbankPrinter5	.mPrinter = new Printer(mContext, mHandler, null);
            } else if (mPrintType.equals("kitchen5") || mPrintType.equals("testprint5")) {
                GlobalMemberValues.disconnectPrinter6();
                GlobalMemberValues.logWrite("kitchenprintinglog6", "여기실행... : " + GlobalMemberValues.NETWORKPRINTERIP6 + "\n");
                PosbankPrinter6	.mPrinter = new Printer(mContext, mHandler, null);
            }

            asyncDialog = new ProgressDialog(mContext);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("..... Loading to connect printer device (POSBANK A11 -- KITCHEN)...");
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
            asyncDialog.show();
            asyncTaskMSalon.execute();

    }

    AsyncTask asyncTaskMSalon = new AsyncTask() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Object doInBackground(Object[] params) {
            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...1 : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");

            if (GlobalMemberValues.isStrEmpty(mPrintType)) {
                mPrintType = "kitchen1";
            }

            GlobalMemberValues.logWrite("inconnectmethod", "mPrintType : " + mPrintType + "\n");

            if (mPrintType.equals("kitchen1") || mPrintType.equals("testprint")) {
                PosbankPrinter2.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP2, 9100, 5000);
            } else if (mPrintType.equals("kitchen2") || mPrintType.equals("testprint2")) {
                PosbankPrinter3.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP3, 9100, 5000);
                GlobalMemberValues.logWrite("kitchenprintinglog3", "여기실행...1 " + "\n");
            } else if (mPrintType.equals("kitchen3") || mPrintType.equals("testprint3")) {
                PosbankPrinter4.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP4, 9100, 5000);
                GlobalMemberValues.logWrite("kitchenprintinglog4", "여기실행...1 " + "\n");
            } else if (mPrintType.equals("kitchen4") || mPrintType.equals("testprint4")) {
                PosbankPrinter5.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP5, 9100, 5000);
                GlobalMemberValues.logWrite("kitchenprintinglog5", "여기실행...1 " + "\n");
            } else if (mPrintType.equals("kitchen5") || mPrintType.equals("testprint5")) {
                PosbankPrinter6.mPrinter.connect(GlobalMemberValues.NETWORKPRINTERIP6, 9100, 5000);
                GlobalMemberValues.logWrite("kitchenprintinglog6", "여기실행...1 " + "\n");
            }


            GlobalMemberValues.logWrite("inconnectmethod", "여기실행...2 : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            GlobalMemberValues.logWrite("kitchenPrintLog", "mPrintType : " + mPrintType + "\n");
            //GlobalMemberValues.logWrite("inconnectmethod", "여기실행...3 : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");
            switch (mPrintType) {
                case "kitchen1" : {
                    //PrinterKitchen1 printerKitchen1 = new PrinterKitchen1();
                    PrinterKitchenByView1 printerKitchen1 = new PrinterKitchenByView1();
                    printerKitchen1.printKitchenPosbank(jsonObject);
                    GlobalMemberValues.logWrite("kitchenPrintLog", "여기실행...\n");
                    GlobalMemberValues.logWrite("kitchenPrintLog", "jsonObject : " + jsonObject.toString() + "\n");
                    break;
                }
                case "kitchen2" : {
                    //PrinterKitchen2 printerKitchen2 = new PrinterKitchen2();
                    PrinterKitchenByView2 printerKitchen2 = new PrinterKitchenByView2();
                    printerKitchen2.printKitchenPosbank(jsonObject);
                    GlobalMemberValues.logWrite("kitchenPrintLog2", "여기실행...\n");
                    GlobalMemberValues.logWrite("kitchenPrintLog2", "jsonObject : " + jsonObject.toString() + "\n");
                    break;
                }
                case "kitchen3" : {
                    //PrinterKitchen3 printerKitchen3 = new PrinterKitchen3();
                    PrinterKitchenByView3 printerKitchen3 = new PrinterKitchenByView3();
                    printerKitchen3.printKitchenPosbank(jsonObject);
                    GlobalMemberValues.logWrite("kitchenPrintLog3", "여기실행...\n");
                    GlobalMemberValues.logWrite("kitchenPrintLog3", "jsonObject : " + jsonObject.toString() + "\n");
                    break;
                }
                case "kitchen4" : {
                    //PrinterKitchen4 printerKitchen4 = new PrinterKitchen4();
                    PrinterKitchenByView4 printerKitchen4 = new PrinterKitchenByView4();
                    printerKitchen4.printKitchenPosbank(jsonObject);
                    GlobalMemberValues.logWrite("kitchenPrintLog4", "여기실행...\n");
                    GlobalMemberValues.logWrite("kitchenPrintLog4", "jsonObject : " + jsonObject.toString() + "\n");
                    break;
                }
                case "kitchen5" : {
                    //PrinterKitchen5 printerKitchen5 = new PrinterKitchen5();
                    PrinterKitchenByView5 printerKitchen5 = new PrinterKitchenByView5();
                    printerKitchen5.printKitchenPosbank(jsonObject);
                    GlobalMemberValues.logWrite("kitchenPrintLog5", "여기실행...\n");
                    GlobalMemberValues.logWrite("kitchenPrintLog5", "jsonObject : " + jsonObject.toString() + "\n");
                    break;
                }
                case "testprint" : {
                    PrinterKitchen1 printerKitchen1 = new PrinterKitchen1();
                    printerKitchen1.printTestPosbank(jsonObject);
                    break;
                }
                case "testprint2" : {
                    GlobalMemberValues.logWrite("secondkitchenlog", "PosBankPrinterStart2 onPostExecute 진입" + "\n");
                    PrinterKitchen2 printerKitchen2 = new PrinterKitchen2();
                    printerKitchen2.printTestPosbank(jsonObject);
                    break;
                }
                case "testprint3" : {
                    PrinterKitchen3 printerKitchen3 = new PrinterKitchen3();
                    printerKitchen3.printTestPosbank(jsonObject);
                    break;
                }
                case "testprint4" : {
                    PrinterKitchen4 printerKitchen4 = new PrinterKitchen4();
                    printerKitchen4.printTestPosbank(jsonObject);
                    break;
                }
                case "testprint5" : {
                    PrinterKitchen5 printerKitchen5 = new PrinterKitchen5();
                    printerKitchen5.printTestPosbank(jsonObject);
                    break;
                }
            }
            //GlobalMemberValues.logWrite("inconnectmethod", "여기실행...4 : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");
        }


    };

    public static final String TAG = "PosbankPrinter2";

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
                            GlobalMemberValues.PRINTERCONNECTEDSTATUS2 = true;
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
