package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class SettingDeviceLabelPrinter4 extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout imgLn, receiptlogoimgLn, ipconnectln;

    private Spinner printernameSpinner;

    private Switch label_printer_usb_sw;

    private Button saveSettingsDevicePrinterButton;
    private Button closeBtn;


    public static EditText networkIpTextView1, networkIpTextView2, networkIpTextView3, networkIpTextView4;
    public static EditText networkPrinterPortTextView;

    private Button connectPrintButton;
    private Button testPrintButton;


    public String mMembershipCardDigitPosition = "L";
    public String mGiftCardDigitPosition = "L";
    public int mEmployeeCommissionPolicy = 1;

    ArrayList<String> mGeneralArrayListForSpinnerPrinterName, mGeneralArrayListForSpinnerFontSize;
    ArrayList<String> mGeneralArrayListForSpinnerReceiptPaperCount, mGeneralArrayListForSpinnerPrintLanguage;
    ArrayAdapter<String> mSpinnerAdapterPrinterName, mSpinnerAdapterFontSize, mSpinnerAdapterReceiptPaperCount, mSpinnerAdapterPrintLanguage;

    public static String selectedItemPrinterNameSpinner = GlobalMemberValues.PRINTERGROUP[0];
    public static String selectedItemPrintLanguageSpinner = GlobalMemberValues.PRINTLANGUAGE[0];
    String selectedItemFontSizeSpinner = "15";
    String selectedItemPaperCountSpinner = "1";

    String mSavedPrinterName = "";
    String mSavedPrintLanguage = "";

    String str_target = "";

    private LinearLayout parentLn;
    Intent mIntent;

    // 파일 다운로드 관련 ---------------------------------------------------------------------------
    private ProgressDialog progressBar;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    private File outputFile; //파일명까지 포함한 경로
    private File path;//디렉토리경로
    // ----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_device_label_printer4);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        GlobalMemberValues.logWrite("SettingsDevicePrinterLog", "network ip : " + GlobalMemberValues.getNetworkPrinterIpPort() + "\n");

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 11) * 8;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.settingsSystemLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
//        enableLocationSetting();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.settingsSystemLn);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        mActivity = this;
        context = this;

        String vPrintername = "";
        String vNetworkprinterip1 = "";
        String vNetworkprinterip2 = "";
        String vNetworkprinterip3 = "";
        String vNetworkprinterip4 = "";
        String vNetworkprinterport = "";

        int vConnectusb = 0;

        String strQuery = "select printername, " +
                " networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4, networkprinterport, " +
                " connectusb " +
                " from salon_storestationsettings_deviceprinter_label4 ";
        Cursor settingsDevicePrinterCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
            String tempPrintername = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
            String tempNetworkprinterip1 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
            String tempNetworkprinterip2 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);
            String tempNetworkprinterip3 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(3), 1);
            String tempNetworkprinterip4 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(4), 1);
            String tempNetworkprinterport = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(5), 1);
            String tempPrintConnectUSB = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(6), 1);

            vPrintername = tempPrintername;

            vNetworkprinterip1 = tempNetworkprinterip1;
            vNetworkprinterip2 = tempNetworkprinterip2;
            vNetworkprinterip3 = tempNetworkprinterip3;
            vNetworkprinterip4 = tempNetworkprinterip4;
            vNetworkprinterport = tempNetworkprinterport;

            if (!GlobalMemberValues.isStrEmpty(tempPrintConnectUSB)) {
                vConnectusb = GlobalMemberValues.getIntAtString(tempPrintConnectUSB);
            }


        }


        // Printer Name --------------------------------------------------------------------------------------------
        printernameSpinner = (Spinner)findViewById(R.id.printernameSpinner);
        mGeneralArrayListForSpinnerPrinterName = new ArrayList<String>();
        for (int i = 0; i < (GlobalMemberValues.PRINTERGROUP_LABEL.length); i++) {
            mGeneralArrayListForSpinnerPrinterName.add(GlobalMemberValues.PRINTERGROUP_LABEL[i]);
        }
        mSpinnerAdapterPrinterName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerPrinterName);
        mSpinnerAdapterPrinterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printernameSpinner.setAdapter(mSpinnerAdapterPrinterName);

        int tempPNPosition = 0;
        for (int i = 0; i < (GlobalMemberValues.PRINTERGROUP_LABEL.length); i++) {
            if (vPrintername.equals(GlobalMemberValues.PRINTERGROUP_LABEL[i])) {
                tempPNPosition = i;
            }
        }

        mSavedPrinterName = vPrintername;
        printernameSpinner.setSelection(tempPNPosition);
        printernameSpinner.setOnItemSelectedListener(mPrinternameSpinnerItemSelectedListener);

        label_printer_usb_sw = (Switch)findViewById(R.id.label_printer_is_usb);
        if (vConnectusb == 1){
            // 1 connect usb , 0 not connect usb
            label_printer_usb_sw.setChecked(true);
        } else {
            label_printer_usb_sw.setChecked(false);
        }

        ipconnectln = findViewById(R.id.ipconnectln);
        label_printer_usb_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ipconnectln.setVisibility(View.GONE);
                } else {
                    ipconnectln.setVisibility(View.VISIBLE);
                }
            }
        });

        if (vConnectusb == 1){
            ipconnectln.setVisibility(View.GONE);
        } else {
            ipconnectln.setVisibility(View.VISIBLE);
        }

        // Network IP 1
        networkIpTextView1 = (EditText)findViewById(R.id.networkIpTextView1);
        networkIpTextView1.setText(vNetworkprinterip1);
        networkIpTextView1.setTextSize(networkIpTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 2
        networkIpTextView2 = (EditText)findViewById(R.id.networkIpTextView2);
        networkIpTextView2.setText(vNetworkprinterip2);
        networkIpTextView2.setTextSize(networkIpTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 3
        networkIpTextView3 = (EditText)findViewById(R.id.networkIpTextView3);
        networkIpTextView3.setText(vNetworkprinterip3);
        networkIpTextView3.setTextSize(networkIpTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 4
        networkIpTextView4 = (EditText)findViewById(R.id.networkIpTextView4);
        networkIpTextView4.setText(vNetworkprinterip4);
        networkIpTextView4.setTextSize(networkIpTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP Port
        networkPrinterPortTextView = (EditText)findViewById(R.id.networkPrinterPortTextView);
        networkPrinterPortTextView.setText(vNetworkprinterport);
        networkPrinterPortTextView.setTextSize(networkPrinterPortTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saveSettingsDevicePrinterButton = (Button)findViewById(R.id.saveSettingsDevicePrinterButton);
        saveSettingsDevicePrinterButton.setOnClickListener(mButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saveSettingsDevicePrinterButton.setText("");
            saveSettingsDevicePrinterButton.setBackgroundResource(R.drawable.ab_imagebutton_settings_enter);
        } else {
            saveSettingsDevicePrinterButton.setTextSize(
                    saveSettingsDevicePrinterButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.activityCloseBtn);
        closeBtn.setOnClickListener(activityBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        testPrintButton = (Button)findViewById(R.id.testPrintButton);
        testPrintButton.setOnClickListener(mClickButtonAboutPrinter);
    }

    View.OnClickListener mClickButtonAboutPrinter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.connectPrintButton : {
//                    GlobalMemberValues.displayDialog(context, "NZ Salon", "Under Construction", "Close");

//                    if (!mSavedPrinterName.equals(selectedItemPrinterNameSpinner)) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "Please try again saved", "Close");
//                    } else {
//                      PosbankPrinter posbankPrinter = new PosbankPrinter(context, "SETTINGS");
//                        posbankPrinter.setConnect(true);
//                    }
                    //jihun park addup
                    Intent intent = new Intent(context, EpsonLabelPrinterDiscoveryActivity.class);
                    startActivityForResult(intent, 0);

                    break;
                }

                case R.id.testPrintButton : {

                    EpsonLabelPrinter4 epsonLabelPrinter = new EpsonLabelPrinter4(MainActivity.mContext);
                    epsonLabelPrinter.runTestPrintReceiptSequence();

                    break;
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            String target = data.getStringExtra("Target");
            if (target != null) {
                str_target = target;
            }
        }
    }


    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveSettingsDevicePrinterButton : {
                    int resultValue = setSettingsDevicePrinter();
                    if (resultValue > 0) {
                        GlobalMemberValues.displayDialog(context, "Settings - Device & Printer", "Successfully Updated", "Close");
//                        if (GlobalMemberValues.isUseLabelPrinter()){
//                            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//                            epsonLabelPrinter.runTestPrintReceiptSequence();
//                        }
                        if (mSavedPrinterName.equals(selectedItemPrinterNameSpinner)){
                        } else {
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
                        GlobalMemberValues.displayDialog(context, "Settings - Device & Printer", "Update Failure", "Close");
                    }

                }
            }
        }
    };


    public int setSettingsDevicePrinter() {

        String insPrintername = "";
        int intUsbConnect = 0;

        String insNetworkprinterip1 = "";
        String insNetworkprinterip2 = "";
        String insNetworkprinterip3 = "";
        String insNetworkprinterip4 = "";
        String insNetworkprinterport = "";

        insPrintername = selectedItemPrinterNameSpinner;

        if (insPrintername.toUpperCase().equals("NO PRINTER")) {
            networkIpTextView1.setText("");
            networkIpTextView2.setText("");
            networkIpTextView3.setText("");
            networkIpTextView4.setText("");
            networkPrinterPortTextView.setText("");

            printernameSpinner.setSelection(0);
        }

        if (label_printer_usb_sw.isChecked()){
            intUsbConnect = 1;
        } else {
            intUsbConnect = 0;
        }

        insNetworkprinterip1 = networkIpTextView1.getText().toString();
        insNetworkprinterip2 = networkIpTextView2.getText().toString();
        insNetworkprinterip3 = networkIpTextView3.getText().toString();
        insNetworkprinterip4 = networkIpTextView4.getText().toString();
        insNetworkprinterport = networkPrinterPortTextView.getText().toString();

        int returnValue = 0;

        String qurey = "SELECT * FROM salon_storestationsettings_deviceprinter_label4 WHERE idx";
        String qurey_result = dbInit.dbExecuteReadReturnString(qurey);
        if (qurey_result.isEmpty()){
            qurey = "insert into salon_storestationsettings_deviceprinter_label4(printername)values('NO PRINTER')";
            dbInit.dbExecuteWriteReturnValue(qurey);
        }

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String updStrQuery = "update salon_storestationsettings_deviceprinter_label4 set " +
                " printername = '" + insPrintername + "', " +
                " networkprinterip1 = '" + insNetworkprinterip1 + "', " +
                " networkprinterip2 = '" + insNetworkprinterip2 + "', " +
                " networkprinterip3 = '" + insNetworkprinterip3 + "', " +
                " networkprinterip4 = '" + insNetworkprinterip4 + "', " +
                " networkprinterport = '" + insNetworkprinterport + "', " +
                " connectusb = '" + intUsbConnect + "', " +
                " mdate = datetime('now') ";
        strUpdateQueryVec.addElement(updStrQuery);

        for (String tempQuery : strUpdateQueryVec) {
            GlobalMemberValues.logWrite("SettingsDevicePrinter", "query : " + tempQuery + "\n");
        }

        if (strUpdateQueryVec.size() > 0) {
            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {
                returnValue = 0;
            } else {
                /**
                 // 저장할 프린터명이 기존 프린터명과 다를 경우 프린터 연결상태를 false 로 한다...
                 if (!mSavedPrinterName.equals(insPrintername)) {
                 GlobalMemberValues.PRINTERCONNECTEDSTATUS = false;
                 GlobalMemberValues.disconnectPrinter();
                 }
                 **/
//                mSavedPrinterName = insPrintername;
                returnValue = 1;
            }
        } else {
            returnValue = 0;
        }
        return returnValue;
    }


    AdapterView.OnItemSelectedListener mPrinternameSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemPrinterNameSpinner = mGeneralArrayListForSpinnerPrinterName.get(position);
            GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "Selected Printer Item : " + selectedItemPrinterNameSpinner + "\n");

            if (position == 1) { // Epson-L90

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mPrinterlanguageSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemPrintLanguageSpinner = mGeneralArrayListForSpinnerPrintLanguage.get(position);
            GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "Selected Print Language : " + selectedItemPrintLanguageSpinner + "\n");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mFontSizeSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemFontSizeSpinner = mGeneralArrayListForSpinnerFontSize.get(position);
            GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "Selected Font Size : " + selectedItemFontSizeSpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mpPaperCountSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemPaperCountSpinner = mGeneralArrayListForSpinnerReceiptPaperCount.get(position);
            GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "Selected Paper Count : " + selectedItemPaperCountSpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    RadioGroup.OnCheckedChangeListener checkRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.label_printer_name_RadioGroup_fname : {

                    break;
                }
                case R.id.label_printer_name_RadioGroup_lname : {

                    break;
                }
                case R.id.label_printer_name_RadioGroup_fl_name : {

                    break;
                }
            }
        }
    };



    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if ( !readAccepted || !writeAccepted  )
                        {
                            showDialogforPermission("You have to grant permissions to run the download");
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showDialogforPermission(String msg) {

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(  context);
        myDialog.setTitle("Information");
        myDialog.setMessage(msg);
        myDialog.setCancelable(false);
        myDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
                }
            }
        });
        myDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        myDialog.show();
    }

    View.OnClickListener activityBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activityCloseBtn : {
                    //SettingsDeviceKitchen.mActivity.recreate();

                    mActivity.finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
            }
        }
    };

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
}
