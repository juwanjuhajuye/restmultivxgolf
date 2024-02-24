package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

//import com.google.android.gms.common.api.CommonStatusCodes;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.SettingsClient;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;

import java.io.File;
import java.util.ArrayList;


public class SettingDeviceLabelPrinter extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout imgLn, receiptlogoimgLn;
    LinearLayout cloudLabelprinterLn, linearLayoutLabelPrinterList;

    private Spinner printernameSpinner;
    private Spinner label_printer_buzzer_count;

    private Switch label_printer_use_sw;

    private Switch label_printer_pagerNumber_switch;
    private Switch label_printer_modifier_switch;
    private Switch label_printer_itemname_switch;
    private Switch label_printer_ordertype_switch;
    private Switch label_printer_buzzer;

    private RadioGroup label_printer_name_group;
    private RadioButton label_printer_fname, label_printer_lname, label_printer_flname;


    private Button saveSettingsDevicePrinterButton;
    private Button dualdpadlocalpathBtn;


    public static EditText networkIpTextView1, networkIpTextView2, networkIpTextView3, networkIpTextView4;
    public static EditText networkPrinterPortTextView;

    private Button connectPrintButton;
    private Button testPrintButton;


    public String mMembershipCardDigitPosition = "L";
    public String mGiftCardDigitPosition = "L";
    public int mEmployeeCommissionPolicy = 1;

    ArrayList<String> mGeneralArrayListForSpinnerPrinterName, mGeneralArrayListForSpinnerFontSize, mLabelBuzzerCountArray;
    ArrayList<String> mGeneralArrayListForSpinnerReceiptPaperCount, mGeneralArrayListForSpinnerPrintLanguage;
    ArrayAdapter<String> mSpinnerAdapterPrinterName, mSpinnerAdapterFontSize, mSpinnerAdapterReceiptPaperCount, mSpinnerAdapterPrintLanguage, mLabelPrinterBuzzerCountAdapter;

    public static String selectedItemPrinterNameSpinner = GlobalMemberValues.PRINTERGROUP[0];
    public static String selectedItemPrintLanguageSpinner = GlobalMemberValues.PRINTLANGUAGE[0];
    String selectedItemFontSizeSpinner = "15";
    String selectedItemPaperCountSpinner = "1";

    String mSavedPrinterName = "";
    String mSavedPrintLanguage = "";

    String str_target = "";

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
        setContentView(R.layout.activity_settings_label_printer);
        this.setFinishOnTouchOutside(false);

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


        SharedPreferences pref = getSharedPreferences("labelprinter_info", MODE_PRIVATE);
        String temp_order_printer_name = pref.getString("labelprinter_info_printer_name", "");
        String temp_pager_number = pref.getString("labelprinter_info_pager_number", "Y");
        String temp_info_name = pref.getString("labelprinter_info_name", "3");
        String temp_modifier = pref.getString("labelprinter_info_modifier", "Y");
        String temp_item_name = pref.getString("labelprinter_info_item_name", "Y");
        String temp_order_type = pref.getString("labelprinter_info_order_type", "Y");
        String temp_printer_buzzer = pref.getString("labelprinter_info_printer_buzzer","N");
        String temp_printer_buzzer_count = pref.getString("labelprinter_info_printer_buzzer_count","1");


        label_printer_pagerNumber_switch = (Switch)findViewById(R.id.label_printer_pagerNumber_switch);
        if (temp_pager_number.equals("Y")){
            label_printer_pagerNumber_switch.setChecked(true);
        } else {
            label_printer_pagerNumber_switch.setChecked(false);
        }

        label_printer_modifier_switch = (Switch)findViewById(R.id.label_printer_modifier_switch);
        if (temp_modifier.equals("Y")){
            label_printer_modifier_switch.setChecked(true);
        } else {
            label_printer_modifier_switch.setChecked(false);
        }

        label_printer_itemname_switch = (Switch)findViewById(R.id.label_printer_itemname_switch);
        if (temp_item_name.equals("Y")){
            label_printer_itemname_switch.setChecked(true);
        } else {
            label_printer_itemname_switch.setChecked(false);
        }

        label_printer_ordertype_switch = (Switch)findViewById(R.id.label_printer_ordertype_switch);
        if (temp_order_type.equals("Y")){
            label_printer_ordertype_switch.setChecked(true);
        } else {
            label_printer_ordertype_switch.setChecked(false);
        }

        label_printer_buzzer = (Switch)findViewById(R.id.label_printer_buzzer);
        if (temp_printer_buzzer.equals("Y")){
            label_printer_buzzer.setChecked(true);
        } else {
            label_printer_buzzer.setChecked(false);
        }

        label_printer_name_group = (RadioGroup)findViewById(R.id.label_printer_name_RadioGroup);
        label_printer_fname = (RadioButton)findViewById(R.id.label_printer_name_RadioGroup_fname);
        label_printer_lname = (RadioButton)findViewById(R.id.label_printer_name_RadioGroup_lname);
        label_printer_flname = (RadioButton)findViewById(R.id.label_printer_name_RadioGroup_fl_name);
        label_printer_name_group.setOnCheckedChangeListener(checkRadioGroup);
        switch (temp_info_name) {
            case "1" :
                label_printer_fname.setChecked(true);
                break;
            case "2" :
                label_printer_lname.setChecked(true);
                break;
            case "3" :
                label_printer_flname.setChecked(true);
                break;
        }


        // Printer Name --------------------------------------------------------------------------------------------
        printernameSpinner = (Spinner)findViewById(R.id.printernameSpinner);
        mGeneralArrayListForSpinnerPrinterName = new ArrayList<String>();
//        mGeneralArrayListForSpinnerPrinterName.add("No Printer");
        mGeneralArrayListForSpinnerPrinterName.add("Epson-L90(40mm)");
        mGeneralArrayListForSpinnerPrinterName.add("Epson-L90(58mm)");

        mSpinnerAdapterPrinterName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerPrinterName);
        mSpinnerAdapterPrinterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printernameSpinner.setAdapter(mSpinnerAdapterPrinterName);

        int tempPNPosition = 0;
        for (int i = 0; i < mGeneralArrayListForSpinnerPrinterName.size(); i++) {
            if (temp_order_printer_name.equals(mGeneralArrayListForSpinnerPrinterName.get(i))) {
                tempPNPosition = i;
            }
        }

        mSavedPrinterName = temp_order_printer_name;
        printernameSpinner.setSelection(tempPNPosition);
        printernameSpinner.setOnItemSelectedListener(mPrinternameSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------


        // ---------------------------------------------------------------------------------------------------------

        receiptlogoimgLn = (LinearLayout)findViewById(R.id.receiptlogoimgLn);

        // --------------------------------------------------------------------------------------------------------------------



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



        connectPrintButton = (Button)findViewById(R.id.connectPrintButton);
        testPrintButton = (Button)findViewById(R.id.testPrintButton);

        connectPrintButton.setOnClickListener(mClickButtonAboutPrinter);
        testPrintButton.setOnClickListener(mClickButtonAboutPrinter);


        LinearLayout employeecommissionLn1 = (LinearLayout)findViewById(R.id.employeecommissionLn1);
        LinearLayout employeecommissionLn2 = (LinearLayout)findViewById(R.id.employeecommissionLn2);

        employeecommissionLn1.setVisibility(View.GONE);
        employeecommissionLn2.setVisibility(View.GONE);

        // 파일 다운로드 관련 ------------------------------------------------------------------------------------------------
        progressBar=new ProgressDialog(context);
        progressBar.setMessage("Downloading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(true);

        cloudLabelprinterLn = (LinearLayout)findViewById(R.id.cloudLabelprinterLn);
        linearLayoutLabelPrinterList = (LinearLayout)findViewById(R.id.linearLayoutLabelPrinterList);
        if (GlobalMemberValues.isCloudKitchenPrinter()) {
            cloudLabelprinterLn.setVisibility(View.VISIBLE);
            linearLayoutLabelPrinterList.setVisibility(View.GONE);
        } else {
            if (GlobalMemberValues.isSocketKitchenPrinter()) {
                cloudLabelprinterLn.setVisibility(View.VISIBLE);
                linearLayoutLabelPrinterList.setVisibility(View.GONE);
            } else {
                cloudLabelprinterLn.setVisibility(View.GONE);
                linearLayoutLabelPrinterList.setVisibility(View.VISIBLE);
            }
        }

        label_printer_buzzer_count = (Spinner)findViewById(R.id.label_printer_buzzer_count);
        mLabelBuzzerCountArray = new ArrayList<String>();
        mLabelBuzzerCountArray.add("1");
        mLabelBuzzerCountArray.add("2");
        mLabelBuzzerCountArray.add("3");
        mLabelPrinterBuzzerCountAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mLabelBuzzerCountArray);
        mLabelPrinterBuzzerCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        label_printer_buzzer_count.setAdapter(mLabelPrinterBuzzerCountAdapter);
        int temp_buzzercount = 0;
        for (int i = 0; i < mLabelBuzzerCountArray.size(); i++) {
            if (temp_printer_buzzer_count.equals(mLabelBuzzerCountArray.get(i))) {
                label_printer_buzzer_count.setSelection(i);
                temp_buzzercount = i;
            }
        }
        if (temp_printer_buzzer_count.isEmpty()){
            label_printer_buzzer_count.setSelection(0);
        } else {
            label_printer_buzzer_count.setSelection(temp_buzzercount);
        }

        printerBtn_List();
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

                    EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
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
                        if (GlobalMemberValues.isUseLabelPrinter()){
                            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
                            epsonLabelPrinter.runTestPrintReceiptSequence();
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
        String insPager_nNumber = "";
        String insName = "";
        String insModifier = "";
        String insItem_name = "";
        String insOrder_Type = "";
        String insBuzzer = "";


        insPrintername = selectedItemPrinterNameSpinner;

        if (label_printer_pagerNumber_switch.isChecked()){
            insPager_nNumber = "Y";
        } else {
            insPager_nNumber = "N";
        }

        if (label_printer_modifier_switch.isChecked()){
            insModifier = "Y";
        } else {
            insModifier = "N";
        }

        if (label_printer_itemname_switch.isChecked()){
            insItem_name = "Y";
        } else {
            insItem_name = "N";
        }

        if (label_printer_ordertype_switch.isChecked()){
            insOrder_Type = "Y";
        } else {
            insOrder_Type = "N";
        }

        if (label_printer_buzzer.isChecked()){
            insBuzzer = "Y";
        } else {
            insBuzzer = "N";
        }

        if (label_printer_fname.isChecked()){
            insName = "1";
        } else if (label_printer_lname.isChecked()){
            insName = "2";
        } else if (label_printer_flname.isChecked()){
            insName = "3";
        }

        int tempBuzzerCount = label_printer_buzzer_count.getSelectedItemPosition() + 1;

        int returnValue = 1;
        SharedPreferences pref = MainActivity.mActivity.getSharedPreferences("labelprinter_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("labelprinter_info_printer_name", insPrintername);
        editor.putString("labelprinter_info_pager_number", insPager_nNumber);
        editor.putString("labelprinter_info_name", insName);
        editor.putString("labelprinter_info_modifier", insModifier);
        editor.putString("labelprinter_info_item_name", insItem_name);
        editor.putString("labelprinter_info_order_type", insOrder_Type);
        editor.putString("labelprinter_info_printer_buzzer", insBuzzer);
        editor.putString("labelprinter_info_printer_buzzer_count",String.valueOf(tempBuzzerCount));
        editor.commit();


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

    /** 프린터 배치하기 ********************************************************/
    public void printerBtn_List() {
//        int kitchenprinterSu = GlobalMemberValues.getIntAtString(
//                MainActivity.mDbInit.dbExecuteReadReturnString(
//                        "select kitchenprintsu from salon_storegeneral")
//        );
        int kitchenprinterSu = 5;

        LinearLayout.LayoutParams tvParam1
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < kitchenprinterSu; i++) {
            // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
            LinearLayout kitchenptLinear
                    = (LinearLayout) linearLayoutLabelPrinterList.findViewWithTag("labelprinterSelectionLinearTag" + (i+1));
            kitchenptLinear.setOrientation(LinearLayout.VERTICAL);
            kitchenptLinear.setBackgroundResource(R.drawable.button_selector_employee_popup_select1);
            kitchenptLinear.setTag((i+1));
            // 동적으로 생성한 직원버튼 클릭시 이벤트리스너 연결
            kitchenptLinear.setOnClickListener(LabelprintBtnSelectedClickListener);

            // Sub View 붙이기전에 전에 있던 Sub View 삭제
            kitchenptLinear.removeAllViews();

            // 버튼을 동적으로 생성한다.
            TextView kitchenTvInLn1 = new TextView(context);
            kitchenTvInLn1.setLayoutParams(tvParam1);
            kitchenTvInLn1.setText("LABEL " + (i+1));
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
            if (GlobalMemberValues.isSettingLabelPrinter((i + 1) + "")) {
                String tempLabelPrinterName = "";
                tempLabelPrinterName = GlobalMemberValues.getSettingLabelPrinter((i + 1) + "");

                // 버튼을 동적으로 생성한다.
                TextView kitchenTvInLn2 = new TextView(context);
                kitchenTvInLn2.setLayoutParams(tvParam1);
                //kitchenTvInLn2.setBackgroundColor(Color.parseColor("#fff8e8"));
                kitchenTvInLn2.setText(tempLabelPrinterName);
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

    View.OnClickListener LabelprintBtnSelectedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout lnBtn = (LinearLayout)v;
            // Tag 값이 있을 경우에만
            String printerNum = lnBtn.getTag().toString();
            if (!GlobalMemberValues.isStrEmpty(printerNum)) {
                /**
                 String tempSettingKitchenPrinter1 = GlobalMemberValues.getSettingKitchenPrinter("1");
                 if (printerNum > 1 && !tempSettingKitchenPrinter1.toUpperCase().equals("POSBANK")) {
                 GlobalMemberValues.displayDialog(context, "KITCHEN PRINTER SETTINGS",
                 "If kitchen printer #1 is not 'POSBANK A11',\nyou can not add kitchen printer.", "Close");
                 return;
                 }
                 **/

                Intent loadingIntent = null;
                switch (printerNum) {
                    case "1" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingDeviceLabelPrinter1.class);
                        break;
                    }
                    case "2" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingDeviceLabelPrinter2.class);
                        break;
                    }
                    case "3" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingDeviceLabelPrinter3.class);
                        break;
                    }
                    case "4" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingDeviceLabelPrinter4.class);
                        break;
                    }
                    case "5" : {
                        loadingIntent = new Intent(context.getApplicationContext(), SettingDeviceLabelPrinter5.class);
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


}
