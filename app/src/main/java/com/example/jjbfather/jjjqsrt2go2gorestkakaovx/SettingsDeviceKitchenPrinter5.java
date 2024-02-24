package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class SettingsDeviceKitchenPrinter5 extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private Button closeBtn;

    private Spinner printernameSpinner;
    private Spinner receiptfontsizeSpinner, receiptpapercountSpinner, printlanguageSpinner;
    private Switch autoreceiptSwitch, tenderuseforzerobalanceSwitch, autodiscountuseforonlycashSwitch;
    private Switch kitchenPrintingSwitch;
    private EditText receiptfooterEditText;
    private EditText customercarddigitpositionstartEditText, customercarddigitpositioncountEditText;
    private EditText giftcarddigitpositionstartEditText, giftcarddigitpositioncountEditText;
    private EditText autodiscountuseforonlycashrateEditText;

    private RadioGroup customercarddigitpositionalignRadioGroup;
    private RadioButton customercarddigitpositionalignLeftRadioButton, customercarddigitpositionalignMiddleRadioButton;
    private RadioButton customercarddigitpositionalignRightRadioButton, customercarddigitpositionalignDefaultRadioButton;

    private RadioGroup giftcarddigitpositionalignRadioGroup;
    private RadioButton giftcarddigitpositionalignLeftRadioButton, giftcarddigitpositionalignMiddleRadioButton;
    private RadioButton giftcarddigitpositionalignRightRadioButton, giftcarddigitpositionalignDefaultRadioButton;

    private Button saveSettingsDevicePrinterButton;

    private TextView settingsDevicePrinterTitleTextView2;
    private TextView settingsDevicePrinterSubTitleTextView1, settingsDevicePrinterSubTitleTextView2;
    private TextView settingsDevicePrinterSubTitleTextView3, settingsDevicePrinterSubTitleTextView4;
    private TextView settingsDevicePrinterSubTitleTextView5, settingsDevicePrinterSubTitleTextView6;
    private TextView settingsDevicePrinterSubTitleTextView7, settingsDevicePrinterSubTitleTextView8;
    private TextView settingsDevicePrinterSubTitleTextView9;
    private TextView settingsDevicePrinterSubTitleTextView13, settingsDevicePrinterSubTitleTextView14;
    private TextView settingsDevicePrinterSubTitleTextView15;

    private TextView settingsDevicePrinterSubSubTitleTextView1,settingsDevicePrinterSubSubTitleTextView2;
    private TextView settingsDevicePrinterSubSubTitleTextView3,settingsDevicePrinterSubSubTitleTextView4;
    private TextView settingsDevicePrinterSubSubTitleTextView5, networkPrinterPortColon;

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

    public static String selectedItemPrinterNameSpinner = GlobalMemberValues.PRINTERGROUPKITCHEN[0];
    public static String selectedItemPrintLanguageSpinner = GlobalMemberValues.PRINTLANGUAGE[0];
    String selectedItemFontSizeSpinner = "15";
    String selectedItemPaperCountSpinner = "1";

    String mSavedPrinterName = "";
    String mSavedPrintLanguage = "";

    private LinearLayout parentLn;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_device_kitchen_printer5);
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

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
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
        /***********************************************************************************************************/

        String vPrintername = "";
        int vAutoreceipt = 0;
        int vReceiptfontsize = 15;
        int vReceiptpapercount = 1;
        String vNetworkprinterip1 = "";
        String vNetworkprinterip2 = "";
        String vNetworkprinterip3 = "";
        String vNetworkprinterip4 = "";
        String vNetworkprinterport = "";
        String vReceiptfooter = "";
        int vTenderuseforzerobalance = 0;
        int vAutodiscountuseforonlycash = 1;
        double vAutodiscountuseforonlycashrate = 0.0;
        String vCustomercarddigitpositionalign = "L";
        int vCustomercarddigitpositionstart = 0;
        int vCustomercarddigitpositioncount = 4;
        String vGiftcarddigitpositionalign = "L";
        int vGiftcarddigitpositionstart = 0;
        int vGiftcarddigitpositioncount = 4;

        int vKitchenPrinting = 0;

        String vPrintlanguage = "";

        String strQuery = "select printername, autoreceipt, receiptfontsize, receiptpapercount, receiptfooter, " +
                " tenderuseforzerobalance, autodiscountuseforonlycash, autodiscountuseforonlycashrate, " +
                " customercarddigitpositionalign, customercarddigitpositionstart, customercarddigitpositioncount, " +
                " giftcarddigitpositionalign, giftcarddigitpositionstart, giftcarddigitpositioncount, " +
                " networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4, networkprinterport, " +
                " kitchenprinting, printlanguage " +
                " from salon_storestationsettings_deviceprinter6 ";
        Cursor settingsDevicePrinterCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
            String tempPrintername = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
            String tempAutoreceipt = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
            String tempReceiptfontsize = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);
            String tempReceiptpapercount = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(3), 1);
            String tempReceiptfooter = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(4), 1);
            String tempTenderuseforzerobalance = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(5), 1);
            String tempAutodiscountuseforonlycash = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(6), 1);
            String tempAutodiscountuseforonlycashrate = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(7), 1);
            String tempCustomercarddigitpositionalign = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(8), 1);
            String tempCustomercarddigitpositionstart = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(9), 1);
            String tempCustomercarddigitpositioncount = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(10), 1);
            String tempGiftcarddigitpositionalign = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(11), 1);
            String tempGiftcarddigitpositionstart = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(12), 1);
            String tempGiftcarddigitpositioncount = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(13), 1);
            String tempNetworkprinterip1 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(14), 1);
            String tempNetworkprinterip2 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(15), 1);
            String tempNetworkprinterip3 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(16), 1);
            String tempNetworkprinterip4 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(17), 1);
            String tempNetworkprinterport = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(18), 1);
            String tempKitchenPrinting = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(19), 1);
            String tempPrintLanguage = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(20), 1);

            vPrintername = tempPrintername;

            if (!GlobalMemberValues.isStrEmpty(tempAutoreceipt)) {
                vAutoreceipt = GlobalMemberValues.getIntAtString(tempAutoreceipt);
            }
            if (!GlobalMemberValues.isStrEmpty(tempReceiptfontsize)) {
                vReceiptfontsize = GlobalMemberValues.getIntAtString(tempReceiptfontsize);
            }
            if (!GlobalMemberValues.isStrEmpty(tempReceiptpapercount)) {
                vReceiptpapercount = GlobalMemberValues.getIntAtString(tempReceiptpapercount);
                selectedItemPaperCountSpinner = tempReceiptpapercount;
            }

            vReceiptfooter = tempReceiptfooter;

            vNetworkprinterip1 = tempNetworkprinterip1;
            vNetworkprinterip2 = tempNetworkprinterip2;
            vNetworkprinterip3 = tempNetworkprinterip3;
            vNetworkprinterip4 = tempNetworkprinterip4;
            vNetworkprinterport = tempNetworkprinterport;

            if (!GlobalMemberValues.isStrEmpty(tempTenderuseforzerobalance)) {
                vTenderuseforzerobalance = GlobalMemberValues.getIntAtString(tempTenderuseforzerobalance);
            }
            if (!GlobalMemberValues.isStrEmpty(tempAutodiscountuseforonlycash)) {
                vAutodiscountuseforonlycash = GlobalMemberValues.getIntAtString(tempAutodiscountuseforonlycash);
            }
            if (!GlobalMemberValues.isStrEmpty(tempAutodiscountuseforonlycashrate)) {
                vAutodiscountuseforonlycashrate = GlobalMemberValues.getDoubleAtString(tempAutodiscountuseforonlycashrate);
            }

            vCustomercarddigitpositionalign = tempCustomercarddigitpositionalign;

            if (!GlobalMemberValues.isStrEmpty(tempCustomercarddigitpositionstart)) {
                vCustomercarddigitpositionstart = GlobalMemberValues.getIntAtString(tempCustomercarddigitpositionstart);
            }
            if (!GlobalMemberValues.isStrEmpty(tempCustomercarddigitpositioncount)) {
                vCustomercarddigitpositioncount = GlobalMemberValues.getIntAtString(tempCustomercarddigitpositioncount);
            }

            vGiftcarddigitpositionalign = tempGiftcarddigitpositionalign;

            if (!GlobalMemberValues.isStrEmpty(tempGiftcarddigitpositionstart)) {
                vGiftcarddigitpositionstart = GlobalMemberValues.getIntAtString(tempGiftcarddigitpositionstart);
            }
            if (!GlobalMemberValues.isStrEmpty(tempGiftcarddigitpositioncount)) {
                vGiftcarddigitpositioncount = GlobalMemberValues.getIntAtString(tempGiftcarddigitpositioncount);
            }

            if (!GlobalMemberValues.isStrEmpty(tempKitchenPrinting)) {
                vKitchenPrinting = GlobalMemberValues.getIntAtString(tempKitchenPrinting);
            }

            if (!GlobalMemberValues.isStrEmpty(tempPrintLanguage)) {
                vPrintlanguage = tempPrintLanguage;
            }

        }

        // Printer Name --------------------------------------------------------------------------------------------
        printernameSpinner = (Spinner)findViewById(R.id.printernameSpinner);
        mGeneralArrayListForSpinnerPrinterName = new ArrayList<String>();
        for (int i = 0; i < (GlobalMemberValues.PRINTERGROUPKITCHEN.length) ; i++) {
            mGeneralArrayListForSpinnerPrinterName.add(GlobalMemberValues.PRINTERGROUPKITCHEN[i]);
        }
        mSpinnerAdapterPrinterName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerPrinterName);
        mSpinnerAdapterPrinterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printernameSpinner.setAdapter(mSpinnerAdapterPrinterName);

        int tempPNPosition = 0;
        for (int i = 0; i < (GlobalMemberValues.PRINTERGROUPKITCHEN.length); i++) {
            if (vPrintername.equals(GlobalMemberValues.PRINTERGROUPKITCHEN[i])) {
                tempPNPosition = i;
            }
        }

        mSavedPrinterName = vPrintername;
        printernameSpinner.setSelection(tempPNPosition);
        printernameSpinner.setOnItemSelectedListener(mPrinternameSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // Print Language ------------------------------------------------------------------------------------------
        printlanguageSpinner = (Spinner)findViewById(R.id.printlanguageSpinner);
        mGeneralArrayListForSpinnerPrintLanguage = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.PRINTLANGUAGE.length; i++) {
            mGeneralArrayListForSpinnerPrintLanguage.add(GlobalMemberValues.PRINTLANGUAGE[i]);
        }
        mSpinnerAdapterPrintLanguage = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerPrintLanguage);
        mSpinnerAdapterPrintLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printlanguageSpinner.setAdapter(mSpinnerAdapterPrintLanguage);

        int tempPLPosition = 0;
        if (!GlobalMemberValues.isStrEmpty(vPrintlanguage)) {
            switch (vPrintlanguage) {
                case "EN" : {
                    tempPLPosition = 0;
                    break;
                }
                case "KO" : {
                    tempPLPosition = 1;
                    break;
                }
                case "JP" : {
                    tempPLPosition = 2;
                    break;
                }
                case "CH" : {
                    tempPLPosition = 3;
                    break;
                }
                case "FR" : {
                    tempPLPosition = 4;
                    break;
                }
                case "IT" : {
                    tempPLPosition = 5;
                    break;
                }
            }
        } else {
            tempPLPosition = 0;
        }

        mSavedPrintLanguage = vPrintlanguage;
        printlanguageSpinner.setSelection(tempPLPosition);
        printlanguageSpinner.setOnItemSelectedListener(mPrinterlanguageSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // Font Size ---------------------------------------------------------------------------------------------
        receiptfontsizeSpinner = (Spinner)findViewById(R.id.receiptfontsizeSpinner);
        mGeneralArrayListForSpinnerFontSize = new ArrayList<String>();
        for (int i = 0; i < 29; i++) {
            mGeneralArrayListForSpinnerFontSize.add((i + 1) + "");
        }
        mSpinnerAdapterFontSize = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerFontSize);
        mSpinnerAdapterFontSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiptfontsizeSpinner.setAdapter(mSpinnerAdapterFontSize);
        if (vReceiptfontsize > 0) {
            receiptfontsizeSpinner.setSelection(vReceiptfontsize-1, true);
        }
        receiptfontsizeSpinner.setOnItemSelectedListener(mFontSizeSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // Paper Count ---------------------------------------------------------------------------------------------
        receiptpapercountSpinner = (Spinner)findViewById(R.id.receiptpapercountSpinner);
        mGeneralArrayListForSpinnerReceiptPaperCount = new ArrayList<String>();
        for (int i = 0; i < 19; i++) {
            mGeneralArrayListForSpinnerReceiptPaperCount.add((i + 1) + "");
        }
        mSpinnerAdapterReceiptPaperCount = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerReceiptPaperCount);
        mSpinnerAdapterReceiptPaperCount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiptpapercountSpinner.setAdapter(mSpinnerAdapterReceiptPaperCount);
        if (vReceiptpapercount > 0) {
            receiptpapercountSpinner.setSelection(vReceiptpapercount-1, true);
        }
        receiptpapercountSpinner.setOnItemSelectedListener(mpPaperCountSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // Auto Receipt
        autoreceiptSwitch = (Switch)findViewById(R.id.autoreceiptSwitch);
        if (vAutoreceipt == 0) {
            autoreceiptSwitch.setChecked(true);
        } else {
            autoreceiptSwitch.setChecked(false);
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

        // Receipt Footer
        receiptfooterEditText = (EditText)findViewById(R.id.receiptfooterEditText);
        receiptfooterEditText.setText(vReceiptfooter);
        receiptfooterEditText.setTextSize(receiptfooterEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tender Use for Zero Balance
        tenderuseforzerobalanceSwitch = (Switch)findViewById(R.id.tenderuseforzerobalanceSwitch);
        if (vTenderuseforzerobalance == 0) {
            tenderuseforzerobalanceSwitch.setChecked(true);
        } else {
            tenderuseforzerobalanceSwitch.setChecked(false);
        }

        // Auto Discount use for Only Cash -----------------------------------------------------------------------------------
        autodiscountuseforonlycashrateEditText = (EditText)findViewById(R.id.autodiscountuseforonlycashrateEditText);
        autodiscountuseforonlycashrateEditText.setText(vAutodiscountuseforonlycashrate + "");
        autodiscountuseforonlycashrateEditText.setTextSize(autodiscountuseforonlycashrateEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        autodiscountuseforonlycashSwitch = (Switch)findViewById(R.id.autodiscountuseforonlycashSwitch);
        if (vAutodiscountuseforonlycash == 0) {
            autodiscountuseforonlycashSwitch.setChecked(true);
            autodiscountuseforonlycashrateEditText.setBackgroundResource(R.drawable.roundlayout_background_discount);
            autodiscountuseforonlycashrateEditText.setEnabled(true);
        } else {
            autodiscountuseforonlycashSwitch.setChecked(false);
            autodiscountuseforonlycashrateEditText.setBackgroundResource(R.drawable.roundlayout_background_discount_gray);
            autodiscountuseforonlycashrateEditText.setEnabled(false);
        }
        autodiscountuseforonlycashSwitch.setOnCheckedChangeListener(switchCheckedChange);
        // --------------------------------------------------------------------------------------------------------------------

        // Membership Card Digit Position -------------------------------------------------------------------------------------
        customercarddigitpositionalignRadioGroup = (RadioGroup)findViewById(R.id.customercarddigitpositionalignRadioGroup);
        customercarddigitpositionalignRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        customercarddigitpositionalignLeftRadioButton = (RadioButton)findViewById(R.id.customercarddigitpositionalignLeftRadioButton);
        customercarddigitpositionalignLeftRadioButton.setTextSize(customercarddigitpositionalignLeftRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customercarddigitpositionalignMiddleRadioButton = (RadioButton)findViewById(R.id.customercarddigitpositionalignMiddleRadioButton);
        customercarddigitpositionalignMiddleRadioButton.setTextSize(customercarddigitpositionalignMiddleRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customercarddigitpositionalignRightRadioButton = (RadioButton)findViewById(R.id.customercarddigitpositionalignRightRadioButton);
        customercarddigitpositionalignRightRadioButton.setTextSize(customercarddigitpositionalignRightRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customercarddigitpositionalignDefaultRadioButton = (RadioButton)findViewById(R.id.customercarddigitpositionalignDefaultRadioButton);
        customercarddigitpositionalignDefaultRadioButton.setTextSize(customercarddigitpositionalignDefaultRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        switch (vCustomercarddigitpositionalign) {
            case "L" : {
                customercarddigitpositionalignLeftRadioButton.setChecked(true);
                break;
            }
            case "M" : {
                customercarddigitpositionalignMiddleRadioButton.setChecked(true);
                break;
            }
            case "R" : {
                customercarddigitpositionalignRightRadioButton.setChecked(true);
                break;
            }
            case "D" : {
                customercarddigitpositionalignDefaultRadioButton.setChecked(true);
                break;
            }
        }
        customercarddigitpositionstartEditText = (EditText)findViewById(R.id.customercarddigitpositionstartEditText);
        customercarddigitpositionstartEditText.setText(vCustomercarddigitpositionstart + "");
        customercarddigitpositionstartEditText.setTextSize(customercarddigitpositionstartEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customercarddigitpositioncountEditText = (EditText)findViewById(R.id.customercarddigitpositioncountEditText);
        customercarddigitpositioncountEditText.setText(vCustomercarddigitpositioncount + "");
        customercarddigitpositioncountEditText.setTextSize(customercarddigitpositioncountEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // --------------------------------------------------------------------------------------------------------------------

        // Giftcard Digit Position -------------------------------------------------------------------------------------
        giftcarddigitpositionalignRadioGroup = (RadioGroup)findViewById(R.id.giftcarddigitpositionalignRadioGroup);
        giftcarddigitpositionalignRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        giftcarddigitpositionalignLeftRadioButton = (RadioButton)findViewById(R.id.giftcarddigitpositionalignLeftRadioButton);
        giftcarddigitpositionalignLeftRadioButton.setTextSize(giftcarddigitpositionalignLeftRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        giftcarddigitpositionalignMiddleRadioButton = (RadioButton)findViewById(R.id.giftcarddigitpositionalignMiddleRadioButton);
        giftcarddigitpositionalignMiddleRadioButton.setTextSize(giftcarddigitpositionalignMiddleRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        giftcarddigitpositionalignRightRadioButton = (RadioButton)findViewById(R.id.giftcarddigitpositionalignRightRadioButton);
        giftcarddigitpositionalignRightRadioButton.setTextSize(giftcarddigitpositionalignRightRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        giftcarddigitpositionalignDefaultRadioButton = (RadioButton)findViewById(R.id.giftcarddigitpositionalignDefaultRadioButton);
        giftcarddigitpositionalignDefaultRadioButton.setTextSize(giftcarddigitpositionalignDefaultRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vGiftcarddigitpositionalign) {
            case "L" : {
                giftcarddigitpositionalignLeftRadioButton.setChecked(true);
                break;
            }
            case "M" : {
                giftcarddigitpositionalignMiddleRadioButton.setChecked(true);
                break;
            }
            case "R" : {
                giftcarddigitpositionalignRightRadioButton.setChecked(true);
                break;
            }
            case "D" : {
                giftcarddigitpositionalignDefaultRadioButton.setChecked(true);
                break;
            }
        }
        giftcarddigitpositionstartEditText = (EditText)findViewById(R.id.giftcarddigitpositionstartEditText);
        giftcarddigitpositionstartEditText.setText(vGiftcarddigitpositionstart + "");
        giftcarddigitpositionstartEditText.setTextSize(giftcarddigitpositionstartEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        giftcarddigitpositioncountEditText = (EditText)findViewById(R.id.giftcarddigitpositioncountEditText);
        giftcarddigitpositioncountEditText.setText(vGiftcarddigitpositioncount + "");
        giftcarddigitpositioncountEditText.setTextSize(giftcarddigitpositioncountEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // --------------------------------------------------------------------------------------------------------------------

        // 주방 kitchen 프린트 여부
        kitchenPrintingSwitch = (Switch)findViewById(R.id.kitchenPrintingSwitch);
        if (vKitchenPrinting == 0) {
            kitchenPrintingSwitch.setChecked(true);
        } else {
            kitchenPrintingSwitch.setChecked(false);
        }

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

        settingsDevicePrinterTitleTextView2 = (TextView)findViewById(R.id.settingsDevicePrinterTitleTextView2);
        settingsDevicePrinterTitleTextView2.setTextSize(settingsDevicePrinterTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView1 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView1);
        settingsDevicePrinterSubTitleTextView1.setTextSize(settingsDevicePrinterSubTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView2 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView2);
        settingsDevicePrinterSubTitleTextView2.setTextSize(settingsDevicePrinterSubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView3 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView3);
        settingsDevicePrinterSubTitleTextView3.setTextSize(settingsDevicePrinterSubTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView4 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView4);
        settingsDevicePrinterSubTitleTextView4.setTextSize(settingsDevicePrinterSubTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView5 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView5);
        settingsDevicePrinterSubTitleTextView5.setTextSize(settingsDevicePrinterSubTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView6 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView6);
        settingsDevicePrinterSubTitleTextView6.setTextSize(settingsDevicePrinterSubTitleTextView6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView7 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView7);
        settingsDevicePrinterSubTitleTextView7.setTextSize(settingsDevicePrinterSubTitleTextView7.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView8 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView8);
        settingsDevicePrinterSubTitleTextView8.setTextSize(settingsDevicePrinterSubTitleTextView8.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView9 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView9);
        settingsDevicePrinterSubTitleTextView9.setTextSize(settingsDevicePrinterSubTitleTextView9.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView13 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView13);
        settingsDevicePrinterSubTitleTextView13.setTextSize(settingsDevicePrinterSubTitleTextView13.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView14 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView14);
        settingsDevicePrinterSubTitleTextView14.setTextSize(settingsDevicePrinterSubTitleTextView14.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView15 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView15);
        settingsDevicePrinterSubTitleTextView15.setTextSize(settingsDevicePrinterSubTitleTextView15.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubSubTitleTextView1 = (TextView)findViewById(R.id.settingsDevicePrinterSubSubTitleTextView1);
        settingsDevicePrinterSubSubTitleTextView1.setTextSize(settingsDevicePrinterSubSubTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubSubTitleTextView2 = (TextView)findViewById(R.id.settingsDevicePrinterSubSubTitleTextView2);
        settingsDevicePrinterSubSubTitleTextView2.setTextSize(settingsDevicePrinterSubSubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubSubTitleTextView3 = (TextView)findViewById(R.id.settingsDevicePrinterSubSubTitleTextView3);
        settingsDevicePrinterSubSubTitleTextView3.setTextSize(settingsDevicePrinterSubSubTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubSubTitleTextView4 = (TextView)findViewById(R.id.settingsDevicePrinterSubSubTitleTextView4);
        settingsDevicePrinterSubSubTitleTextView4.setTextSize(settingsDevicePrinterSubSubTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubSubTitleTextView5 = (TextView)findViewById(R.id.settingsDevicePrinterSubSubTitleTextView5);
        settingsDevicePrinterSubSubTitleTextView5.setTextSize(settingsDevicePrinterSubSubTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        networkPrinterPortColon = (TextView)findViewById(R.id.networkPrinterPortColon);

        connectPrintButton = (Button)findViewById(R.id.connectPrintButton);
        testPrintButton = (Button)findViewById(R.id.testPrintButton);

        connectPrintButton.setOnClickListener(mClickButtonAboutPrinter);
        testPrintButton.setOnClickListener(mClickButtonAboutPrinter);
    }

    View.OnClickListener mClickButtonAboutPrinter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                 case R.id.connectPrintButton : {
//                 GlobalMemberValues.displayDialog(context, "NZ Salon", "Under Construction", "Close");

                 // if (!mSavedPrinterName.equals(selectedItemPrinterNameSpinner)) {
                 // GlobalMemberValues.displayDialog(context, "Warning", "Please try again saved", "Close");
                 // } else {
                 // PosbankPrinter posbankPrinter = new PosbankPrinter(context, "SETTINGS");
                 // posbankPrinter.setConnect(true);
                 // }
                     if (selectedItemPrinterNameSpinner.equals("Giant-100")) {
                         new AlertDialog.Builder(mActivity)
                                 .setTitle("RECONNECT")
                                 .setMessage("Do you want to reconnect all connected printers?")
                                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         //connect
                                         Sam4GiantPrinter.Sam4sPrinterReset("5");
                                         try {
                                             Thread.sleep(2000);
                                         } catch (InterruptedException e) {
                                             e.printStackTrace();
                                         }
                                         GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint5");
                                     }
                                 })
                                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         //connect
                                         Sam4GiantPrinter.Sam4sPrinterReset("all");
                                         try {
                                             Thread.sleep(2000);
                                         } catch (InterruptedException e) {
                                             e.printStackTrace();
                                         }

                                         Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                                         giantPrinter.kitchenPrint_connect_test();
                                     }
                                 }).show();
                     }


                 break;
                 }

                case R.id.testPrintButton : {
                    GlobalMemberValues.disconnectPrinter6();
                    GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint5");
                    break;
                }
            }
        }
    };

    Switch.OnCheckedChangeListener switchCheckedChange = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.autodiscountuseforonlycashSwitch : {
                    if (isChecked) {
                        autodiscountuseforonlycashrateEditText.setBackgroundResource(R.drawable.roundlayout_background_discount);
                        autodiscountuseforonlycashrateEditText.setEnabled(true);
                    } else {
                        autodiscountuseforonlycashrateEditText.setText("0.0");
                        autodiscountuseforonlycashrateEditText.setBackgroundResource(R.drawable.roundlayout_background_discount_gray);
                        autodiscountuseforonlycashrateEditText.setEnabled(false);
                    }
                    break;
                }
            }
        }
    };

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveSettingsDevicePrinterButton : {
                    int resultValue = setSettingsDevicePrinter();
                    if (resultValue > 0) {
                        GlobalMemberValues.displayDialog(context, "Settings - Device & Printer", "Successfully Updated", "Close");
                        switch (mSavedPrinterName) {
                            case "STAR" : {
                                GlobalMemberValues.printReceiptByKitchen1(null, context, "testprint5");
                                break;
                            }
                            case "PosBank" : {
                                // 테스트 프린트
                                //testPrinting();
                                // 연결을 끊는다..
                                GlobalMemberValues.logWrite("secondkitchenlog", "완료버튼누름" + "\n");
                                GlobalMemberValues.disconnectPrinter6();
                                GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint5");
                                break;
                            }
                            case "Elo" : {
                                GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint");
                                break;
                            }
                            case "Giant-100" : {
                                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                                giantPrinter.disconnect();
                                String tempip = networkIpTextView1.getText().toString() + "." + networkIpTextView2.getText().toString()+ "."  + networkIpTextView3.getText().toString()+ "."  + networkIpTextView4.getText().toString();
                                GlobalMemberValues.NETWORKPRINTERIP6 = tempip;

                                String tempPort = networkPrinterPortTextView.getText().toString();
                                GlobalMemberValues.NETWORKPRINTERPORT6 = tempPort;

                                GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint5");
                                break;
                            }
                            case "Epson-T88" :{
                                String tempip = networkIpTextView1.getText().toString() + "." + networkIpTextView2.getText().toString()+ "."  + networkIpTextView3.getText().toString()+ "."  + networkIpTextView4.getText().toString();
                                GlobalMemberValues.NETWORKPRINTERIP6 = tempip;
                                GlobalMemberValues.printReceiptByKitchen5(null, context, "testprint");
                                break;
                            }
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
        int insAutoreceipt = 0;
        int insReceiptfontsize = 15;
        int insReceiptpapercount = 1;
        String insReceiptfooter = "";
        int insTenderuseforzerobalance = 0;
        int insAutodiscountuseforonlycash = 1;
        double insAutodiscountuseforonlycashrate = 0.0;
        String insCustomercarddigitpositionalign = "L";
        int insCustomercarddigitpositionstart = 0;
        int insCustomercarddigitpositioncount = 4;
        String insGiftcarddigitpositionalign = "L";
        int insGiftcarddigitpositionstart = 0;
        int insGiftcarddigitpositioncount = 4;
        int intKitchenPrinting = 0;

        String insNetworkprinterip1 = "";
        String insNetworkprinterip2 = "";
        String insNetworkprinterip3 = "";
        String insNetworkprinterip4 = "";
        String insNetworkprinterport = "";

        String insPrintLanguage = "";



        insPrintername = selectedItemPrinterNameSpinner;

        if (!GlobalMemberValues.isStrEmpty(selectedItemPrintLanguageSpinner)) {
            switch (selectedItemPrintLanguageSpinner) {
                case "English" : {
                    insPrintLanguage = "EN";
                    break;
                }
                case "English + Korean" : {
                    insPrintLanguage = "KO";
                    break;
                }
                case "English + Japanese" : {
                    insPrintLanguage = "JP";
                    break;
                }
                case "English + Chinese" : {
                    insPrintLanguage = "CH";
                    break;
                }
                case "English + French" : {
                    insPrintLanguage = "FR";
                    break;
                }
                case "English + Italian" : {
                    insPrintLanguage = "IT";
                    break;
                }
            }
        } else {
            insPrintLanguage = "EN";
        }

        if (autoreceiptSwitch.isChecked()) {
            insAutoreceipt = 0;
        } else {
            insAutoreceipt = 1;
        }

        insReceiptfontsize = GlobalMemberValues.getIntAtString(selectedItemFontSizeSpinner);
        insReceiptpapercount = GlobalMemberValues.getIntAtString(selectedItemPaperCountSpinner);
        insReceiptfooter = receiptfooterEditText.getText().toString();

        if (tenderuseforzerobalanceSwitch.isChecked()) {
            insTenderuseforzerobalance = 0;
        } else {
            insTenderuseforzerobalance = 1;
        }

        if (autodiscountuseforonlycashSwitch.isChecked()) {
            insAutodiscountuseforonlycash = 0;
        } else {
            insAutodiscountuseforonlycash = 1;
        }

        String tempAutoDCRate = autodiscountuseforonlycashrateEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempAutoDCRate)) {
            insAutodiscountuseforonlycashrate = 0.0;
        } else {
            insAutodiscountuseforonlycashrate = GlobalMemberValues.getDoubleAtString(tempAutoDCRate);
        }

        insCustomercarddigitpositionalign = mMembershipCardDigitPosition;
        String tempCustStart = customercarddigitpositionstartEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempCustStart)) {
            insCustomercarddigitpositionstart = 0;
        } else {
            insCustomercarddigitpositionstart = GlobalMemberValues.getIntAtString(tempCustStart);
        }
        String tempCustCount = customercarddigitpositioncountEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempCustCount)) {
            insCustomercarddigitpositioncount = 0;
        } else {
            insCustomercarddigitpositioncount = GlobalMemberValues.getIntAtString(tempCustCount);
        }

        insGiftcarddigitpositionalign = mGiftCardDigitPosition;
        String tempGiftStart = giftcarddigitpositionstartEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempGiftStart)) {
            insGiftcarddigitpositionstart = 0;
        } else {
            insGiftcarddigitpositionstart = GlobalMemberValues.getIntAtString(tempGiftStart);
        }
        String tempGiftCount = giftcarddigitpositioncountEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempGiftCount)) {
            insGiftcarddigitpositioncount = 0;
        } else {
            insGiftcarddigitpositioncount = GlobalMemberValues.getIntAtString(tempGiftCount);
        }

        if (kitchenPrintingSwitch.isChecked()) {
            intKitchenPrinting = 0;
        } else {
            intKitchenPrinting = 1;
        }

        if (insPrintername.toUpperCase().equals("NO PRINTER")) {
            networkIpTextView1.setText("");
            networkIpTextView2.setText("");
            networkIpTextView3.setText("");
            networkIpTextView4.setText("");
            networkPrinterPortTextView.setText("");

            receiptfooterEditText.setText("");

            printernameSpinner.setSelection(0);
        }

        insNetworkprinterip1 = networkIpTextView1.getText().toString();
        insNetworkprinterip2 = networkIpTextView2.getText().toString();
        insNetworkprinterip3 = networkIpTextView3.getText().toString();
        insNetworkprinterip4 = networkIpTextView4.getText().toString();
        insNetworkprinterport = networkPrinterPortTextView.getText().toString();

        insReceiptfooter = receiptfooterEditText.getText().toString();

        int returnValue = 0;

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String updStrQuery = "update salon_storestationsettings_deviceprinter6 set " +
                " printername = '" + insPrintername + "', " +
                " autoreceipt = '" + insAutoreceipt + "', " +
                " receiptfontsize = '" + insReceiptfontsize + "', " +
                " receiptpapercount = '" + insReceiptpapercount + "', " +
                " receiptfooter = '" + insReceiptfooter + "', " +
                " tenderuseforzerobalance = '" + insTenderuseforzerobalance + "', " +
                " autodiscountuseforonlycash = '" + insAutodiscountuseforonlycash + "', " +
                " autodiscountuseforonlycashrate = '" + insAutodiscountuseforonlycashrate + "', " +
                " customercarddigitpositionalign = '" + insCustomercarddigitpositionalign + "', " +
                " customercarddigitpositionstart = '" + insCustomercarddigitpositionstart + "', " +
                " customercarddigitpositioncount = '" + insCustomercarddigitpositioncount + "', " +
                " giftcarddigitpositionalign = '" + insGiftcarddigitpositionalign + "', " +
                " giftcarddigitpositionstart = '" + insGiftcarddigitpositionstart + "', " +
                " giftcarddigitpositioncount = '" + insGiftcarddigitpositioncount + "', " +
                " kitchenprinting = '" + intKitchenPrinting + "', " +
                " networkprinterip1 = '" + insNetworkprinterip1 + "', " +
                " networkprinterip2 = '" + insNetworkprinterip2 + "', " +
                " networkprinterip3 = '" + insNetworkprinterip3 + "', " +
                " networkprinterip4 = '" + insNetworkprinterip4 + "', " +
                " networkprinterport = '" + insNetworkprinterport + "', " +
                " printlanguage = '" + insPrintLanguage + "', " +
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
                mSavedPrinterName = insPrintername;

                GlobalMemberValues.kitchenprinter5_papercount = insReceiptpapercount;

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

            if (selectedItemPrinterNameSpinner.equals("PosBank")) {
                // posbank connect....!
                PosbankPrinter6 posbankPrinter6 = new PosbankPrinter6(context, "SETTINGS");
                posbankPrinter6.setConnect(true);
            }
            if (!selectedItemPrinterNameSpinner.equals("Giant-100")) {
                // Giant 100
                connectPrintButton.setVisibility(View.VISIBLE);
            } else {
                connectPrintButton.setVisibility(View.GONE);
            }

            if (!selectedItemPrinterNameSpinner.equals("Epson-T88")) {
                String[] arr_ip = GlobalMemberValues.STATION_IP.split("\\.");
                if (arr_ip.length > 0){
                    if (!arr_ip[0].isEmpty()) networkIpTextView1.setText(arr_ip[0]);
                    if (arr_ip.length > 1 && !arr_ip[1].isEmpty()) networkIpTextView2.setText(arr_ip[1]);
                    if (arr_ip.length > 2 && !arr_ip[2].isEmpty()) networkIpTextView3.setText(arr_ip[2]);
                    networkPrinterPortTextView.setVisibility(View.GONE);
                    networkPrinterPortColon.setVisibility(View.GONE);
                }
            } else {
                networkPrinterPortTextView.setVisibility(View.GONE);
                networkPrinterPortColon.setVisibility(View.GONE);
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
                case R.id.customercarddigitpositionalignRadioGroup : {
                    switch (checkedId) {
                        case R.id.customercarddigitpositionalignLeftRadioButton : {
                            mMembershipCardDigitPosition = "L";
                            break;
                        }
                        case R.id.customercarddigitpositionalignMiddleRadioButton : {
                            mMembershipCardDigitPosition = "M";
                            break;
                        }
                        case R.id.customercarddigitpositionalignRightRadioButton : {
                            mMembershipCardDigitPosition = "R";
                            break;
                        }
                        case R.id.customercarddigitpositionalignDefaultRadioButton : {
                            mMembershipCardDigitPosition = "D";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "mMembershipCardDigitPosition : " + mMembershipCardDigitPosition + "\n");
                    break;
                }
                case R.id.giftcarddigitpositionalignRadioGroup : {
                    switch (checkedId) {
                        case R.id.giftcarddigitpositionalignLeftRadioButton : {
                            mGiftCardDigitPosition = "L";
                            break;
                        }
                        case R.id.giftcarddigitpositionalignMiddleRadioButton : {
                            mGiftCardDigitPosition = "M";
                            break;
                        }
                        case R.id.giftcarddigitpositionalignRightRadioButton : {
                            mGiftCardDigitPosition = "R";
                            break;
                        }
                        case R.id.giftcarddigitpositionalignDefaultRadioButton : {
                            mGiftCardDigitPosition = "D";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "mGiftCardDigitPosition : " + mGiftCardDigitPosition + "\n");
                    break;
                }
                case R.id.employeecommissionpolicyRadioGroup : {
                    switch (checkedId) {
                        case R.id.employeecommissionpolicyEmployeeOnlyRadioButton : {
                            mEmployeeCommissionPolicy = 0;
                            break;
                        }
                        case R.id.employeecommissionpolicyEmployeeMenuRadioButton : {
                            mEmployeeCommissionPolicy = 1;
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER", "mGiftCardDigitPosition : " + mGiftCardDigitPosition + "\n");
                    break;
                }
            }
        }
    };

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
}
