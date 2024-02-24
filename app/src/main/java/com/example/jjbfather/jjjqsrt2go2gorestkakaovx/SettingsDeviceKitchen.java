package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class SettingsDeviceKitchen extends Activity {
    private LinearLayout parentLn;
    Intent mIntent;

    public static Activity mActivity;
    public static Context context;

    GetDataAtSQLite dataAtSqlite;

    LinearLayout cloudkitchenprinterLn, linearLayoutKitchenPrinterList;
    LinearLayout departmentLn;

    LinearLayout socketLn3, socketLn4, socketLn5, socketLn6, socketLn7;

    private RadioGroup cloudprintynGroup, localconnectiongtypeGroup;
    private RadioButton localprintRButton, socketprintRButton, bluetoothprintRButton, printingappRButton, networkprintRButton;
    private RadioButton localconnectiongtypeNetwork, localconnectiongtypeSerial;

    private TextView settingsSystemTitleTextView1, settingsSystemTitleTextView2;
    private TextView settingsSystemTitleTextView3, settingsSystemTitleTextView5;
    private TextView settingsSystemTitleTextView7, settingsSystemTitleTextView8;
    private TextView settingsSystemTitleTextView9, settingsSystemTitleTextView10;
    private TextView infoTextView1, thisstationip;

    private TextView temptext1, temptext2;

    private EditText serverstationipTextView1, serverstationipTextView2, serverstationipTextView3, serverstationipTextView4;
    private EditText serverstationportTextView;

    private EditText serverportTextView;

    private Button connectBtn, setserverBtn;

    private Switch cloudprintynSwitch, kitchenprintfontsizeSwitch;
    private Switch kitchenordernumbersectionviewynSwitch;
    private Switch printtypeSwitch;

    private String orgin_cloudprintyn = "";

    private ConnectivityManager cManager;

    // bluetooth
    Button bluetoothSearch_btn, bluetooth_test;
//    // Intent request codes
//    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
//    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
//    private static final int REQUEST_ENABLE_BT = 3;
//
//    private BlueToothPrinterService mBluetooth_PrinterService = null;
//    private BluetoothAdapter mBluetoothAdapter = null;
//    private String mConnectedDeviceName = null;

    // bluetooth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_device_kitchen);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        parentLn = (LinearLayout)findViewById(R.id.kitchenprinterSelectPtLn);

        setContents();
    }

    public void setContents() {
        infoTextView1 = (TextView)findViewById(R.id.infoTextView1);
        infoTextView1.setTextSize(infoTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        infoTextView1.setOnClickListener(mButtonClick);

        // 현재 스테이션 IP 가져오기 -------------------------------------------------------------------------------
        thisstationip = (TextView)findViewById(R.id.thisstationip);
        thisstationip.setTextSize(thisstationip.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnected()) {
            WifiManager wManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wManager.getConnectionInfo();
            thisstationip.setText(Formatter.formatIpAddress(info.getIpAddress()));
            GlobalMemberValues.STATION_IP = thisstationip.getText().toString();
        } else {
            thisstationip.setText("Disconnected");
        }
        // ---------------------------------------------------------------------------------------------------------

        settingsSystemTitleTextView1 = (TextView)findViewById(R.id.settingsSystemTitleTextView1);
        settingsSystemTitleTextView1.setTextSize(settingsSystemTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView2 = (TextView)findViewById(R.id.settingsSystemTitleTextView2);
        settingsSystemTitleTextView2.setTextSize(settingsSystemTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView3 = (TextView)findViewById(R.id.settingsSystemTitleTextView3);
        settingsSystemTitleTextView3.setTextSize(settingsSystemTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView5 = (TextView)findViewById(R.id.settingsSystemTitleTextView5);
        settingsSystemTitleTextView5.setTextSize(settingsSystemTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView7 = (TextView)findViewById(R.id.settingsSystemTitleTextView7);
        settingsSystemTitleTextView7.setTextSize(settingsSystemTitleTextView7.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView8 = (TextView)findViewById(R.id.settingsSystemTitleTextView8);
        settingsSystemTitleTextView8.setTextSize(settingsSystemTitleTextView8.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView9 = (TextView)findViewById(R.id.settingsSystemTitleTextView9);
        settingsSystemTitleTextView9.setTextSize(settingsSystemTitleTextView9.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView10 = (TextView)findViewById(R.id.settingsSystemTitleTextView10);
        settingsSystemTitleTextView10.setTextSize(settingsSystemTitleTextView10.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        temptext1 = (TextView)findViewById(R.id.temptext1);
        temptext1.setTextSize(temptext1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        temptext2 = (TextView)findViewById(R.id.temptext2);
        temptext2.setTextSize(temptext2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        cloudkitchenprinterLn = (LinearLayout)findViewById(R.id.cloudkitchenprinterLn);
        linearLayoutKitchenPrinterList = (LinearLayout)findViewById(R.id.linearLayoutKitchenPrinterList);
        if (GlobalMemberValues.isCloudKitchenPrinter()) {
            cloudkitchenprinterLn.setVisibility(View.VISIBLE);
            linearLayoutKitchenPrinterList.setVisibility(View.GONE);
        } else {
            if (GlobalMemberValues.isSocketKitchenPrinter()) {
                cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                linearLayoutKitchenPrinterList.setVisibility(View.GONE);
            } else {
                cloudkitchenprinterLn.setVisibility(View.GONE);
                linearLayoutKitchenPrinterList.setVisibility(View.VISIBLE);
            }
        }

        connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setTextSize(connectBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        connectBtn.setOnClickListener(mButtonClick);

        setserverBtn = (Button) findViewById(R.id.setserverBtn);
        setserverBtn.setTextSize(connectBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        setserverBtn.setOnClickListener(mButtonClick);

        // printing type -------------------------------------------------------------------------------------------------

        socketLn3 = (LinearLayout)findViewById(R.id.socketLn3);
        socketLn4 = (LinearLayout)findViewById(R.id.socketLn4);
        socketLn5 = (LinearLayout)findViewById(R.id.socketLn5);
        socketLn6 = (LinearLayout)findViewById(R.id.socketLn6);
        socketLn7 = (LinearLayout)findViewById(R.id.socketLn7);


        // Server / Client 타입 여부
        String tempGetValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select cloudkitchenprinteryn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }

        String tempSCValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select serverclienttype from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempSCValue)) {
            tempSCValue = "C";
        }

        cloudprintynGroup = (RadioGroup)findViewById(R.id.cloudprintynGroup);

        localprintRButton = (RadioButton)findViewById(R.id.localprintRButton);
        localprintRButton.setTextSize(localprintRButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        socketprintRButton = (RadioButton)findViewById(R.id.socketprintRButton);
        socketprintRButton.setTextSize(socketprintRButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        bluetoothprintRButton = (RadioButton)findViewById(R.id.bluetoothprintRButton);
        bluetoothprintRButton.setTextSize(bluetoothprintRButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        networkprintRButton = (RadioButton)findViewById(R.id.networkprintRButton);
        networkprintRButton.setTextSize(networkprintRButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        printingappRButton = (RadioButton)findViewById(R.id.printingappRButton);
        printingappRButton.setTextSize(printingappRButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (tempGetValue) {
            case "N" : {
                localprintRButton.setChecked(true);
                setSocketLn(false);

                cloudkitchenprinterLn.setVisibility(View.GONE);
                linearLayoutKitchenPrinterList.setVisibility(View.VISIBLE);

                break;
            }
            case "S" : {
                socketprintRButton.setChecked(true);
                setSocketLn(true);

                cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                temptext1.setText("STATION TO STATION KITCHEN PRINTER");
                temptext2.setText("by NZ Middleware Solution");

                break;
            }
            case "B" : {
                bluetoothprintRButton.setChecked(true);
                setSocketLn(false);

                cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                temptext1.setText("BLUETOOTH KITCHEN PRINTER");
                temptext2.setText("");

                break;
            }
            case "P" : {
                printingappRButton.setChecked(true);
                setSocketLn(false);

                cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                temptext1.setText("Printing with the other printing app");
                temptext2.setText("");

                break;
            }
            case "Y" : {
                networkprintRButton.setChecked(true);
                setSocketLn(false);

                cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                infoTextView1.setVisibility(View.VISIBLE);

                temptext1.setText("CLOUD KITCHEN PRINTER");
                temptext2.setText("by NZ Middleware Solution");

                break;
            }
        }

        final String finalTempSCValue = tempSCValue;
        cloudprintynGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String insValue = "N";

                infoTextView1.setVisibility(View.GONE);

                switch (checkedId) {
                    case R.id.localprintRButton : {
                        insValue = "N";

                        cloudkitchenprinterLn.setVisibility(View.GONE);
                        linearLayoutKitchenPrinterList.setVisibility(View.VISIBLE);

                        setSocketLn(false);

                        break;
                    }
                    case R.id.socketprintRButton : {
                        insValue = "S";

                        cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                        linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                        temptext1.setText("STATION TO STATION KITCHEN PRINTER");
                        temptext2.setText("by NZ Middleware Solution");

                        setSocketLn(true);

                        break;
                    }
                    case R.id.bluetoothprintRButton : {
                        insValue = "B";

                        cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                        linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                        temptext1.setText("BLUETOOTH KITCHEN PRINTER");
                        temptext2.setText("");

                        setSocketLn(false);

                        break;
                    }
                    case R.id.printingappRButton : {
                        // 먼저 프린팅 앱이 설치되어 있는지 확인한다.
                        if (GlobalMemberValues.isInstalledTheApp(context, GlobalMemberValues.PRINTINGAPPPACKAGENAME)) {
                            insValue = "P";

                            cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                            linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                            temptext1.setText("Printing with the other printing app");
                            temptext2.setText("");

                            setSocketLn(false);

                            // 앱이 실행중인지 확인후 실행중이 아니면 실행시킨다.
                            if (!GlobalMemberValues.isRunTheApp(context, GlobalMemberValues.PRINTINGAPPPACKAGENAME)) {
                                // 패키지명으로 실행시키는 경우
                                Intent intent = context.getPackageManager().getLaunchIntentForPackage(GlobalMemberValues.PRINTINGAPPPACKAGENAME);
                                startActivity(intent);

                                // 액티비티명으로 실행시키는 경우
                                /**
                                ComponentName compName = new ComponentName("com.package","com.package.activity");
                                Intent intent = newIntent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.setComponent(compName);
                                startActivity(intent);
                                 **/
                            }
                        } else {
                            /**  앱 설치 ********************************************************************************************/
                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                if (!GlobalMemberValues.isOnline().equals("00")) {
                                    return;
                                }
                                // 0 : FTP 통해 설치             1 : 플레이스토어 통해 설치
                                if (GlobalMemberValues.APPUPDATETYPE > 0) {
                                    AppUpdate_PrintingApp.appUpdateFromPlayStore(mActivity, context);
                                } else {
                                    AppUpdate_PrintingApp.appUpdateFromCloudServer(mActivity, context);
                                }
                            }
                            /**********************************************************************************************************/
                        }

                        break;
                    }
                    case R.id.networkprintRButton : {
                        insValue = "Y";

                        cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                        linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                        infoTextView1.setVisibility(View.VISIBLE);

                        temptext1.setText("CLOUD KITCHEN PRINTER");
                        temptext2.setText("by NZ Middleware Solution");

                        setSocketLn(false);

                        break;
                    }
                }

                int returnValue = 0;
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String strQuery = "update salon_storestationsettings_system set cloudkitchenprinteryn = '" + insValue + "' ";
                strUpdateQueryVec.addElement(strQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                }

                if (strUpdateQueryVec.size() > 0) {
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        returnValue = 0;
                    } else {
                        returnValue = 1;
                    }
                } else {
                    returnValue = 0;
                }

                if (returnValue > 0) {
                    switch (insValue) {
                        case "Y" : {
                            GlobalMemberValues.CLOUDKITCHENPRINTING = "Y";
                            cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                            linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                            temptext1.setText("CLOUD KITCHEN PRINTER");
                            temptext2.setText("by NZ Middleware Solution");

                            break;
                        }
                        case "N" : {
                            GlobalMemberValues.CLOUDKITCHENPRINTING = "N";
                            cloudkitchenprinterLn.setVisibility(View.GONE);
                            linearLayoutKitchenPrinterList.setVisibility(View.VISIBLE);

                            break;
                        }
                        case "P" : {
                            GlobalMemberValues.CLOUDKITCHENPRINTING = "P";
                            cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                            linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                            temptext1.setText("Printing with the other printing app");
                            temptext2.setText("");

                            break;
                        }
                        case "S" : {
                            GlobalMemberValues.CLOUDKITCHENPRINTING = "S";
                            cloudkitchenprinterLn.setVisibility(View.VISIBLE);
                            linearLayoutKitchenPrinterList.setVisibility(View.GONE);

                            temptext1.setText("STATION TO STATION KITCHEN PRINTER");
                            temptext2.setText("by NZ Middleware Solution");

                            break;
                        }
                    }

                    if (orgin_cloudprintyn.equals(insValue)) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Information")
                                .setMessage("You must restart the POS system for the changes to take effect.\nWould you close the POS system now?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        GlobalMemberValues.setCloseAndroidAppMethod(MainActivity.mActivity);
                                    }
                                }).show();
                    }
                } else {
                    cloudprintynSwitch.setChecked(false);

                    GlobalMemberValues.CLOUDKITCHENPRINTING = "N";
                    cloudkitchenprinterLn.setVisibility(View.GONE);
                    linearLayoutKitchenPrinterList.setVisibility(View.VISIBLE);
                }


            }
        });





        // Network / Serial 타입 여부
        String tempGetLocalConnectionType = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select localkitchenprintingtype from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetLocalConnectionType)) {
            tempGetLocalConnectionType = "N";
        }

        localconnectiongtypeGroup = (RadioGroup)findViewById(R.id.localconnectiongtypeGroup);

        localconnectiongtypeNetwork = (RadioButton)findViewById(R.id.localconnectiongtypeNetwork);
        localconnectiongtypeNetwork.setTextSize(localconnectiongtypeNetwork.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        localconnectiongtypeSerial = (RadioButton)findViewById(R.id.localconnectiongtypeSerial);
        localconnectiongtypeSerial.setTextSize(localconnectiongtypeSerial.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (tempGetLocalConnectionType) {
            case "N" : {
                localconnectiongtypeNetwork.setChecked(true);
                break;
            }
            case "S" : {
                localconnectiongtypeSerial.setChecked(true);
                break;
            }
        }

        localconnectiongtypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            String insValue = "N";

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.localconnectiongtypeNetwork : {
                        insValue = "N";
                        break;
                    }
                    case R.id.localconnectiongtypeSerial : {
                        insValue = "S";
                        break;
                    }
                }

                int returnValue = 0;
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String strQuery = "update salon_storestationsettings_system set localkitchenprintingtype = '" + insValue + "' ";
                strUpdateQueryVec.addElement(strQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                }

                if (strUpdateQueryVec.size() > 0) {
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        returnValue = 0;
                    } else {
                        returnValue = 1;
                    }
                } else {
                    returnValue = 0;
                }

            }
        });


        // -------------------------------------------------------------------------------------------------------------------

        kitchenprintfontsizeSwitch = (Switch)findViewById(R.id.kitchenprintfontsizeSwitch);
        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
            kitchenprintfontsizeSwitch.setChecked(true);
        } else {
            kitchenprintfontsizeSwitch.setChecked(false);
        }
        kitchenprintfontsizeSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String tempCheckValue = "R";
                int returnValue = 0;

                if (kitchenprintfontsizeSwitch.isChecked()) {
                    tempCheckValue = "R";
                } else {
                    tempCheckValue = "L";
                }
                String strQuery = "update salon_storestationsettings_system " +
                        " set kitchenprintfontsize = '" + tempCheckValue + "' ";
                strUpdateQueryVec.addElement(strQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                }

                if (strUpdateQueryVec.size() > 0) {
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        returnValue = 0;
                    } else {
                        returnValue = 1;
                    }
                } else {
                    returnValue = 0;
                }
            }
        });


        kitchenordernumbersectionviewynSwitch = (Switch)findViewById(R.id.kitchenordernumbersectionviewynSwitch);
        if (GlobalMemberValues.isKitchenOrderNumberSectionViewOn()) {
            kitchenordernumbersectionviewynSwitch.setChecked(true);
        } else {
            kitchenordernumbersectionviewynSwitch.setChecked(false);
        }
        kitchenordernumbersectionviewynSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String tempCheckValue = "Y";
                int returnValue = 0;

                if (kitchenordernumbersectionviewynSwitch.isChecked()) {
                    tempCheckValue = "Y";
                } else {
                    tempCheckValue = "N";
                }
                String strQuery = "update salon_storestationsettings_system " +
                        " set kitchenordernumbersectionviewyn = '" + tempCheckValue + "' ";
                strUpdateQueryVec.addElement(strQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                }

                if (strUpdateQueryVec.size() > 0) {
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        returnValue = 0;
                    } else {
                        returnValue = 1;
                    }
                } else {
                    returnValue = 0;
                }
            }
        });


        printtypeSwitch = (Switch)findViewById(R.id.printtypeSwitch);
        if (GlobalMemberValues.kitchenprinting_type.equals("I")) {
            printtypeSwitch.setChecked(true);
        } else {
            printtypeSwitch.setChecked(false);
        }
        printtypeSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String tempCheckValue = "I";
                int returnValue = 0;

                if (printtypeSwitch.isChecked()) {
                    tempCheckValue = "I";
                } else {
                    tempCheckValue = "T";
                }
                String strQuery = "update salon_storestationsettings_system " +
                        " set kitchenprintingtexttype = '" + tempCheckValue + "' ";
                strUpdateQueryVec.addElement(strQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                }

                if (strUpdateQueryVec.size() > 0) {
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        returnValue = 0;
                    } else {
                        returnValue = 1;

                        GlobalMemberValues.kitchenprinting_type = tempCheckValue;
                    }
                } else {
                    returnValue = 0;
                }
            }
        });



        // 서버 / 클라이언트 정보
        String vServerstationip1 = "";
        String vServerstationip2 = "";
        String vServerstationip3 = "";
        String vServerstationip4 = "";
        String vServerstationport = "";
        String vServerport = "";

        String strQuery = "select serverstationip1, serverstationip2, serverstationip3, serverstationip4, serverstationport, serverport " +
                " from salon_storestationsettings_system ";
        Cursor settingsSystemCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            vServerstationip1 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
            vServerstationip2 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
            vServerstationip3 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);
            vServerstationip4 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);
            vServerstationport = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(4), 1);
            vServerport = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(5), 1);
        }

        // Network IP 1
        serverstationipTextView1 = (EditText)findViewById(R.id.serverstationipTextView1);
        serverstationipTextView1.setText(vServerstationip1);
        serverstationipTextView1.setTextSize(serverstationipTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 2
        serverstationipTextView2 = (EditText)findViewById(R.id.serverstationipTextView2);
        serverstationipTextView2.setText(vServerstationip2);
        serverstationipTextView2.setTextSize(serverstationipTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 3
        serverstationipTextView3 = (EditText)findViewById(R.id.serverstationipTextView3);
        serverstationipTextView3.setText(vServerstationip3);
        serverstationipTextView3.setTextSize(serverstationipTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 4
        serverstationipTextView4 = (EditText)findViewById(R.id.serverstationipTextView4);
        serverstationipTextView4.setText(vServerstationip4);
        serverstationipTextView4.setTextSize(serverstationipTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP Port
        serverstationportTextView = (EditText)findViewById(R.id.serverstationportTextView);
        serverstationportTextView.setText(vServerstationport);
        serverstationportTextView.setTextSize(serverstationportTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Servier Port
        serverportTextView = (EditText)findViewById(R.id.serverportTextView);
        serverportTextView.setText(vServerport);
        serverportTextView.setTextSize(serverportTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        kitchenprinterSelectionInfo();


        // bluetooth
        bluetoothSearch_btn = (Button) findViewById(R.id.btn_bluetoothsearch);
        bluetoothSearch_btn.setTextSize(bluetoothSearch_btn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        bluetoothSearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serverIntent = new Intent(getApplicationContext(), BlueToothDeviceListActivity.class);
                startActivityForResult(serverIntent, MainActivity.REQUEST_CONNECT_DEVICE_SECURE);
            }
        });
        bluetooth_test = (Button) findViewById(R.id.btn_bluetoothtest);
        bluetooth_test.setTextSize(bluetooth_test.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        bluetooth_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetooth_sendMessage("Navyzebra bluetooth printing test...");
            }
        });
    }

    /** 프린터 배치하기 ********************************************************/
    public void kitchenprinterSelectionInfo() {
        int kitchenprinterSu = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select kitchenprintsu from salon_storegeneral")
        );

        LinearLayout.LayoutParams tvParam1
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < kitchenprinterSu; i++) {
            // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
            LinearLayout kitchenptLinear
                    = (LinearLayout) linearLayoutKitchenPrinterList.findViewWithTag("kitchenprinterSelectionLinearTag" + (i+1));
            kitchenptLinear.setOrientation(LinearLayout.VERTICAL);
            kitchenptLinear.setBackgroundResource(R.drawable.button_selector_employee_popup_select1);
            kitchenptLinear.setTag((i+1));
            // 동적으로 생성한 직원버튼 클릭시 이벤트리스너 연결
            kitchenptLinear.setOnClickListener(kitchenprintBtnSelectedClickListener);

            // Sub View 붙이기전에 전에 있던 Sub View 삭제
            kitchenptLinear.removeAllViews();

            // 버튼을 동적으로 생성한다.
            TextView kitchenTvInLn1 = new TextView(context);
            kitchenTvInLn1.setLayoutParams(tvParam1);
            kitchenTvInLn1.setText("KITCHEN " + (i+1));
            kitchenTvInLn1.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_ONCLICKTEXTCOLOR1));
            kitchenTvInLn1.setTextSize(26.0f * GlobalMemberValues.getGlobalFontSize());
            if (GlobalMemberValues.isSettingKitchenPrinter((i + 1) + "")) {
                kitchenTvInLn1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            } else {
                kitchenTvInLn1.setGravity(Gravity.CENTER);
            }
            kitchenTvInLn1.setPadding(5,5,5,5);
            kitchenTvInLn1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.0f));

            // LinearLayout 에 버튼을 붙인다.
            kitchenptLinear.addView(kitchenTvInLn1);


            // 해당 주방프린터가 셋팅이 되어 있는지 확인
            if (GlobalMemberValues.isSettingKitchenPrinter((i + 1) + "")) {
                String tempKitchenPrinterName = "";
                tempKitchenPrinterName = GlobalMemberValues.getSettingKitchenPrinter((i + 1) + "");

                // 버튼을 동적으로 생성한다.
                TextView kitchenTvInLn2 = new TextView(context);
                kitchenTvInLn2.setLayoutParams(tvParam1);
                //kitchenTvInLn2.setBackgroundColor(Color.parseColor("#fff8e8"));
                kitchenTvInLn2.setText(tempKitchenPrinterName);
                kitchenTvInLn2.setTextColor(Color.parseColor("#2287e1"));
                kitchenTvInLn2.setTextSize(22.0f * GlobalMemberValues.getGlobalFontSize());
                kitchenTvInLn2.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
                kitchenTvInLn2.setPadding(5,5,5,5);
                kitchenTvInLn2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.0f));

                // LinearLayout 에 버튼을 붙인다.
                kitchenptLinear.addView(kitchenTvInLn2);

            }
        }
    }
    /*******************************************************************************/


    View.OnClickListener kitchenprintBtnSelectedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout lnBtn = (LinearLayout)v;
            int printerNum = GlobalMemberValues.getIntAtString(v.getTag().toString());
            // Tag 값이 있을 경우에만
            String kitchenprinternum = lnBtn.getTag().toString();
            if (!GlobalMemberValues.isStrEmpty(kitchenprinternum)) {
                /**
                String tempSettingKitchenPrinter1 = GlobalMemberValues.getSettingKitchenPrinter("1");
                if (printerNum > 1 && !tempSettingKitchenPrinter1.toUpperCase().equals("POSBANK")) {
                    GlobalMemberValues.displayDialog(context, "KITCHEN PRINTER SETTINGS",
                            "If kitchen printer #1 is not 'POSBANK A11',\nyou can not add kitchen printer.", "Close");
                    return;
                }
                 **/

                Intent loadingIntent = null;
                switch (kitchenprinternum) {
                    case "1" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingsDeviceKitchenPrinter1.class);
                        break;
                    }
                    case "2" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingsDeviceKitchenPrinter2.class);
                        break;
                    }
                    case "3" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingsDeviceKitchenPrinter3.class);
                        break;
                    }
                    case "4" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingsDeviceKitchenPrinter4.class);
                        break;
                    }
                    case "5" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingsDeviceKitchenPrinter5.class);
                        break;
                    }
                    case "6" : {

                        break;
                    }
                    case "7" : {

                        break;
                    }
                    case "8" : {

                        break;
                    }
                    case "9" : {

                        break;
                    }
                    case "10" : {

                        break;
                    }
                }

                if (loadingIntent != null) {
                    mActivity.startActivity(loadingIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }
            }

        }
    };

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setserverBtn : {
                    // 먼저 데이터베이스에 저장한다.
                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String tempCheckValue = "C";
                    int returnValue = 0;

                    String insServerportTextView = serverportTextView.getText().toString();

                    String strQuery = "update salon_storestationsettings_system set " +
                            " serverport = '" + insServerportTextView + "'" +
                            " ";
                    strUpdateQueryVec.addElement(strQuery);

                    for (String tempQuery : strUpdateQueryVec) {
                        GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                    }

                    if (strUpdateQueryVec.size() > 0) {
                        // 트랜잭션으로 DB 처리한다.
                        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                            returnValue = 0;
                        } else {
                            returnValue = 1;
                        }
                    } else {
                        returnValue = 0;
                    }

                    if (returnValue > 0) {
                        GlobalMemberValues.setSocketServer();
                    }

                    break;
                }

                case R.id.connectBtn : {
                    // 먼저 데이터베이스에 저장한다.
                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String tempCheckValue = "C";
                    int returnValue = 0;

                    String insServerstationip1 = serverstationipTextView1.getText().toString();
                    String insServerstationip2 = serverstationipTextView2.getText().toString();
                    String insServerstationip3 = serverstationipTextView3.getText().toString();
                    String insServerstationip4 = serverstationipTextView4.getText().toString();
                    String insServerstationport = serverstationportTextView.getText().toString();

                    String strQuery = "update salon_storestationsettings_system set " +
                            " serverstationip1 = '" + insServerstationip1 + "'," +
                            " serverstationip2 = '" + insServerstationip2 + "'," +
                            " serverstationip3 = '" + insServerstationip3 + "'," +
                            " serverstationip4 = '" + insServerstationip4 + "'," +
                            " serverstationport = '" + insServerstationport + "'" +
                            " ";
                    strUpdateQueryVec.addElement(strQuery);

                    for (String tempQuery : strUpdateQueryVec) {
                        GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
                    }

                    if (strUpdateQueryVec.size() > 0) {
                        // 트랜잭션으로 DB 처리한다.
                        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                            returnValue = 0;
                        } else {
                            returnValue = 1;
                        }
                    } else {
                        returnValue = 0;
                    }

                    if (returnValue > 0) {
                        // 이곳에서 서버로의 커텍트
                        GlobalMemberValues.connectSocketServer();
                    }

                    break;
                }

                case R.id.infoTextView1 : {
                    // GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                    Intent tempIntent = new Intent(context.getApplicationContext(), JJJ_KitchenPrintingList.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(tempIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
                    }

                    break;
                }

            }
        }
    };

    public void setSocketLn(boolean paramIs) {
        if (paramIs) {
            socketLn3.setVisibility(View.VISIBLE);
            socketLn4.setVisibility(View.VISIBLE);
        } else {
            socketLn3.setVisibility(View.GONE);
            socketLn4.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MainActivity.REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case MainActivity.REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
//                    connectDevice(data, false);
                }
                break;
            case MainActivity.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
//                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
//                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getApplicationContext(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras().getString(BlueToothDeviceListActivity.EXTRA_DEVICE_ADDRESS);
        if (GlobalMemberValues.isStrEmpty(address)) return;
        // Get the BluetoothDevice object
        BluetoothDevice device = MainActivity.mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        MainActivity.mBluetooth_PrinterService.connect(device, secure);

        // 연결된 블루투스 기기의 mac address 저장
        GlobalMemberValues.bluetooth_saveMacAddress(mActivity, address);
    }
//
    private void bluetooth_sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (MainActivity.mBluetooth_PrinterService.getState() != BlueToothPrinterService.STATE_CONNECTED) {
            Toast.makeText(getApplicationContext(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            MainActivity.mBluetooth_PrinterService.write(send);
        }
    }


}
