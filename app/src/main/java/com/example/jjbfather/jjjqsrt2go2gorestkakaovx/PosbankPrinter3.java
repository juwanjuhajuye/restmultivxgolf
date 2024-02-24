package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.printer.posbank.Printer;

import java.util.Set;
import java.util.Vector;

/**
 * Created by jihun.park on 16. 8. 9..
 */
public class PosbankPrinter3 {
    ProgressDialog asyncDialog;
    static Printer mPrinter;
    private String mConnectedDeviceName = null;

    Context mContext;
    String mLocation;

    Context dialContext;

    public PosbankPrinter3(Context context, String paramLoc){
        this.mContext = context;
        this.mLocation = paramLoc;

        switch (mLocation) {
            case "MAIN" : {
                dialContext = MainActivity.mContext;
                break;
            }
            case "SETTINGS" : {
                dialContext = SettingsDeviceKitchenPrinter2.context;
                break;
            }
            default: {
                if (mContext == null) {
                    dialContext = MainActivity.mContext;
                } else {
                    dialContext = mContext;
                }
                break;
            }
        }
    }

    public void setConnectForce() {
        GlobalMemberValues.logWrite("setConnectForce", "여기실행됨..\n");
        mPrinter = new Printer(mContext, mHandler, null);
        asyncDialog = new ProgressDialog(mContext);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("Loading to connect printer device (POSBANK A11 - kitchen1)...");
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
        Thread thread = new Thread() {
            @Override
            public void run() {
                mPrinter.findNetworkPrinters(3000);

            }
        };
        thread.start();
    }

    public void setConnect(boolean paramDialog){
        if (GlobalMemberValues.PRINTERCONNECTEDSTATUS3 == false) {
            mPrinter = new Printer(mContext, mHandler, null);
            asyncDialog = new ProgressDialog(mContext);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("Loading to connect printer device (POSBANK A11 - kitchen2)...");
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
            Thread thread = new Thread() {
                @Override
                public void run() {
                    mPrinter.findNetworkPrinters(3000);

                }
            };
            thread.start();
        } else {
            GlobalMemberValues.logWrite("kitchenprintinglog3", "여기실행...2 " + "\n");
            Toast.makeText(mContext, "PosBank Printer Connect Completed - kitchen2 !", Toast.LENGTH_LONG).show();
            if (paramDialog) {
                if (mContext != null) {
                    GlobalMemberValues.displayDialog(mContext, "Printer Connected Message", "Printer has been already connected", "Close");
                } else {
                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Printer has been already connected", "Close");
                }
            }
        }
    }

    public void setDisconnect() {
        if (mPrinter != null) {
            mPrinter.disconnect();
            GlobalMemberValues.PRINTERCONNECTEDSTATUS3 = false;
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
                            GlobalMemberValues.PRINTERCONNECTEDSTATUS3 = true;
                            Toast.makeText(mContext, "PosBank Printer Connect Completed - kitchen2 !", Toast.LENGTH_LONG).show();
                            GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "PosBank A11 Printer Connect Completed - kitchen2 !", "Close");

                            break;

                        case Printer.STATE_CONNECTING:

                            break;

                        case Printer.STATE_NONE:

                            break;
                    }
                    //return true;
                    break;

                case Printer.MESSAGE_TOAST:
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS3 = false;

                    String tempMsg = "";
                    tempMsg = msg.getData().getString(Printer.KEY_STRING_TOAST);

                    GlobalMemberValues.logWrite("PosBankPrinterLog", "여기출력됨...\n");
                    if (tempMsg.contains("Device connection was lost")) {
                    } else {
                        Toast.makeText(mContext, tempMsg, Toast.LENGTH_SHORT).show();
                    }
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "Printer Connected Message", tempMsg, "Close");


                    //return true;
                    break;

                case Printer.MESSAGE_PRINT_COMPLETE:
                    Toast.makeText(mContext, "Complete to print", Toast.LENGTH_SHORT).show();
                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Complete to print", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_INVALID_ARGUMENT:
                    Toast.makeText(mContext, "Invalid argument", Toast.LENGTH_SHORT).show();
                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Invalid argument", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_NV_MEMORY_CAPACITY:
                    Toast.makeText(mContext, "NV memory capacity error", Toast.LENGTH_SHORT).show();
                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "NV memory capacity error", "Close");
                    //return true;
                    break;

                case Printer.MESSAGE_ERROR_OUT_OF_MEMORY:
                    Toast.makeText(mContext, "Out of memory", Toast.LENGTH_SHORT).show();
                    GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "Out of memory", "Close");
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
                        GlobalMemberValues.displayDialog(dialContext, "Printer Connected Message", "No connectable device", "Close");
                    }

                    if (msg.obj != null) {
                        Set<String> ipAddressSet = (Set<String>) msg.obj;
                        final String[] items = ipAddressSet.toArray(new String[ipAddressSet.size()]);

                        new AlertDialog.Builder(mContext).setTitle("Connectable network printers")
                                .setItems(items, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPrinter.connect(items[which], 9100, 5000);
                                    }
                                }).show();
                    }

                    //return true;
                    break;

                case Printer.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(Printer.KEY_STRING_DEVICE_NAME);
                    Toast.makeText(mContext, mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    //String tempConnectedDeviceName = GlobalMemberValues.getReplaceText(mConnectedDeviceName, "/", "");
                    //String[] ipAddrs = tempConnectedDeviceName.split(".");
                    //GlobalMemberValues.displayDialog(dialContext, "IP Address", mConnectedDeviceName + "\n" + tempConnectedDeviceName+ "\n" + "", "Close");
                    if (!GlobalMemberValues.isStrEmpty(mConnectedDeviceName)) {
                        String tempConnectedDeviceName = GlobalMemberValues.getReplaceText(mConnectedDeviceName, "/", "");
                        String[] ipAddrs = tempConnectedDeviceName.split("\\.");

                        // 먼저 선택한 프린터에 이미 연결된 주방프린터가 있는지 체크한다.
                        if (GlobalMemberValues.checkConnectedPosBankKitchPrinterByIp("3",
                                ipAddrs[0], ipAddrs[1], ipAddrs[2], ipAddrs[3])) {
                            GlobalMemberValues.displayDialog(mContext, "Printer Connected Message",
                                    "The printer you have selected is connected to another kitchen printer.\nDuplicate selection may cause problems\nPlease select another printer.", "Close");
                        }

                        String strQuery = " update salon_storestationsettings_deviceprinter3 set " +
                                " printername = '" + SettingsDeviceKitchenPrinter2.selectedItemPrinterNameSpinner + "', " +
                                " networkprinterip1 = '" + ipAddrs[0] + "', " +
                                " networkprinterip2 = '" + ipAddrs[1] + "', " +
                                " networkprinterip3 = '" + ipAddrs[2] + "', " +
                                " networkprinterip4 = '" + ipAddrs[3] + "' ";
                        GlobalMemberValues.logWrite("mConnectedDeviceName", "mConnectedDeviceName1 : " + ipAddrs[0] + "." + ipAddrs[1] + "." + ipAddrs[2] + "." + ipAddrs[3] + "\n");
                        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
                        Vector<String> strInsertQueryVec = new Vector<String>();
                        strInsertQueryVec.addElement(strQuery);
                        String returnResult = "";
                        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                            //
                        } else {
                            GlobalMemberValues.NETWORKPRINTERIP3 = tempConnectedDeviceName;

                            SettingsDeviceKitchenPrinter2.networkIpTextView1.setText(ipAddrs[0]);
                            SettingsDeviceKitchenPrinter2.networkIpTextView2.setText(ipAddrs[1]);
                            SettingsDeviceKitchenPrinter2.networkIpTextView3.setText(ipAddrs[2]);
                            SettingsDeviceKitchenPrinter2.networkIpTextView4.setText(ipAddrs[3]);

                            GlobalMemberValues.logWrite("mConnectedDeviceName", "mConnectedDeviceName2 : " + ipAddrs[0] + "." + ipAddrs[1] + "." + ipAddrs[2] + "." + ipAddrs[3] + "\n");
                            GlobalMemberValues.logWrite("mConnectedDeviceName", "mConnectedDeviceName3 : " + GlobalMemberValues.NETWORKPRINTERIP3 + "\n");

                            GlobalMemberValues.PRINTERCONNECTEDSTATUS3 = false;
                            //mPrinter.disconnect();
                        }
                    }

                    break;
            }
            return false;
        }
    });
}
