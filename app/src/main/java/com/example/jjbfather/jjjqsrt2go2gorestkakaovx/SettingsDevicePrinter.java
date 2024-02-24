package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.CommonAlertDialogFragment;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.Communication;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.ILocalizeReceipts;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.ModelCapability;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.PrinterFunctions;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.PrinterSettingManager;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.PrinterSettings;
import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.ConnectionCallback;

//import com.starmicronics.stario.StarResultCode;
//import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;

public class SettingsDevicePrinter extends Activity implements CommonAlertDialogFragment.Callback{
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout imgLn, receiptlogoimgLn;

    private Spinner printernameSpinner, receiptfontsizeSpinner, receiptpapercountSpinner, printlanguageSpinner;
    private Switch autoreceiptSwitch, tenderuseforzerobalanceSwitch, autodiscountuseforonlycashSwitch;
    private Switch managerpwduseforvoidSwitch, cashdraweronoffonsalemodeSwitch, modifierprintynSwitch;
    private Switch voidPrintSwitch, returnPrintSwitch;
    private Switch signatureprintynSwitch, signatureprintseperatelySwitch;
    private Switch cashdraweronoffonreceiptSwitch, cardmerchantreceiptynSwitch;
    private Switch ordernumbersectionviewynSwitch, taxexemptprintingynSwitch;
    private Switch logoprintingonreceiptynSwitch;
    private Switch empinfoprintingynSwitch;
    private Switch menulistprintingynSwitch;

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

    private RadioGroup employeecommissionpolicyRadioGroup;
    private RadioButton employeecommissionpolicyEmployeeOnlyRadioButton, employeecommissionpolicyEmployeeMenuRadioButton;

    private Button saveSettingsDevicePrinterButton;
    private Button dualdpadlocalpathBtn;

    private TextView settingsDevicePrinterTitleTextView1, settingsDevicePrinterTitleTextView2;
    private TextView settingsDevicePrinterSubTitleTextView1, settingsDevicePrinterSubTitleTextView2;
    private TextView settingsDevicePrinterSubTitleTextView3, settingsDevicePrinterSubTitleTextView4;
    private TextView settingsDevicePrinterSubTitleTextView5, settingsDevicePrinterSubTitleTextView6;
    private TextView settingsDevicePrinterSubTitleTextView7, settingsDevicePrinterSubTitleTextView8;
    private TextView settingsDevicePrinterSubTitleTextView9, settingsDevicePrinterSubTitleTextView10;
    private TextView settingsDevicePrinterSubTitleTextView11, settingsDevicePrinterSubTitleTextView12;
    private TextView settingsDevicePrinterSubTitleTextView13, settingsDevicePrinterSubTitleTextView14;
    private TextView settingsDevicePrinterSubTitleTextView15, settingsDevicePrinterSubTitleTextView16;
    private TextView settingsDevicePrinterSubTitleTextView17, settingsDevicePrinterSubTitleTextView18;
    private TextView settingsDevicePrinterSubTitleTextView19, settingsDevicePrinterSubTitleTextView20;
    private TextView settingsDevicePrinterSubTitleTextView21, settingsDevicePrinterSubTitleTextView22;
    private TextView settingsDevicePrinterSubTitleTextView23, settingsDevicePrinterSubTitleTextView24;
    private TextView settingsDevicePrinterSubTitleTextView25;
    private TextView settingsDevicePrinterSubTitleTextView26, settingsDevicePrinterSubTitleTextView27;


    private TextView settingsDevicePrinterSubSubTitleTextView1,settingsDevicePrinterSubSubTitleTextView2;
    private TextView settingsDevicePrinterSubSubTitleTextView3,settingsDevicePrinterSubSubTitleTextView4;
    private TextView settingsDevicePrinterSubSubTitleTextView5, networkportcolon;

    private TextView receiptLogoImgTv;

    private ImageView receiptlogimgIv;

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

    // 파일 다운로드 관련 ---------------------------------------------------------------------------
    private ProgressDialog progressBar;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    private File outputFile; //파일명까지 포함한 경로
    private File path;//디렉토리경로
    // ----------------------------------------------------------------------------------------------

    // star
    public StarIoExtManager mStarIoExtManager;
//    private TextView mComment;
    boolean mIsForeground;
    private ProgressDialog mProgressDialog;
    // star

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_device_printer);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        GlobalMemberValues.logWrite("SettingsDevicePrinterLog", "network ip : " + GlobalMemberValues.getNetworkPrinterIpPort() + "\n");

        mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

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

        mActivity = this;
        context = this;

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
        int vEmployeecommissionpolicy = 1;
        int vManagerpwduseforvoid = 0;
        int vCashdraweronoffonsalemode = 0;
        int vVoidprint = 0;
        int vReturnprint = 0;
        String vPrintlanguage = "";
        String vSignaturePrintYN = "";
        String vSignaturePrintSeperatlely = "";
        String vModifierPrintYN = "";
        String vCashdraweronoffonreceipt = "";
        String vCardmerchantreceiptyn = "";
        String vOrdernumbersectionviewyn = "";
        String vTaxexemptprintingyn = "";
        String vLogoprintingonreceiptyn = "";
        String vLogoimgpath = "";

        String vEmpinfoprintingyn = "";
        String vMenulistprintingyn = "";

        String strQuery = "select printername, autoreceipt, receiptfontsize, receiptpapercount, receiptfooter, " +
                " tenderuseforzerobalance, autodiscountuseforonlycash, autodiscountuseforonlycashrate, " +
                " customercarddigitpositionalign, customercarddigitpositionstart, customercarddigitpositioncount, " +
                " giftcarddigitpositionalign, giftcarddigitpositionstart, giftcarddigitpositioncount, " +
                " employeecommissionpolicy, managerpwduseforvoid, cashdraweronoffonsalemode, " +
                " networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4, networkprinterport, " +
                " voidprint, returnprint, printlanguage, signatureprintyn, signatureprintseperately, modifierprintyn, cashdraweropenonreceipt, cardmerchantreceiptyn, ordernumbersectionviewyn, " +
                " taxexemptprintingyn, logoprintingonreceiptyn, logoimgpath, empinfoprintingyn, menulistprintingyn " +
                " from salon_storestationsettings_deviceprinter ";
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
            String tempEmployeecommissionpolicy = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(14), 1);
            String tempManagerpwduseforvoid = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(15), 1);
            String tempCashdraweronoffonsalemode = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(16), 1);
            String tempNetworkprinterip1 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(17), 1);
            String tempNetworkprinterip2 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(18), 1);
            String tempNetworkprinterip3 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(19), 1);
            String tempNetworkprinterip4 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(20), 1);
            String tempNetworkprinterport = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(21), 1);
            String tempVoidprint = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(22), 1);
            String tempReturnprint = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(23), 1);
            String tempPrintLanguage = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(24), 1);
            String tempSignaturePrintYN = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(25), 1);
            String tempSignaturePrintSeperately = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(26), 1);
            String tempModifierPrintYN = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(27), 1);
            String tempCashDrawerOnReceipt = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(28), 1);
            String tempCardmerchantreceiptyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(29), 1);
            String tempOrdernumbersectionviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(30), 1);
            String tempTaxexemptprintingyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(31), 1);

            String tempLogoprintingonreceiptyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(32), 1);
            String tempLogoimgpath = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(33), 1);

            String tempEmpinfoprintingyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(34), 1);
            String tempMenulistprintingyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(35), 1);

            vPrintername = tempPrintername;

            if (!GlobalMemberValues.isStrEmpty(tempLogoimgpath)) {
                vLogoimgpath = tempLogoimgpath;
            }

            if (GlobalMemberValues.isStrEmpty(tempEmpinfoprintingyn)) {
                vEmpinfoprintingyn = "Y";
            } else {
                vEmpinfoprintingyn = tempEmpinfoprintingyn;
            }

            if (GlobalMemberValues.isStrEmpty(tempMenulistprintingyn)) {
                vMenulistprintingyn = "Y";
            } else {
                vMenulistprintingyn = tempMenulistprintingyn;
            }

            if (!GlobalMemberValues.isStrEmpty(tempAutoreceipt)) {
                vAutoreceipt = GlobalMemberValues.getIntAtString(tempAutoreceipt);
            }
            if (!GlobalMemberValues.isStrEmpty(tempReceiptfontsize)) {
                vReceiptfontsize = GlobalMemberValues.getIntAtString(tempReceiptfontsize);
            }
            if (!GlobalMemberValues.isStrEmpty(tempReceiptpapercount)) {
                vReceiptpapercount = GlobalMemberValues.getIntAtString(tempReceiptpapercount);
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

            if (!GlobalMemberValues.isStrEmpty(tempEmployeecommissionpolicy)) {
                vEmployeecommissionpolicy = GlobalMemberValues.getIntAtString(tempEmployeecommissionpolicy);
            }

            if (!GlobalMemberValues.isStrEmpty(tempManagerpwduseforvoid)) {
                vManagerpwduseforvoid = GlobalMemberValues.getIntAtString(tempManagerpwduseforvoid);
            }

            if (!GlobalMemberValues.isStrEmpty(tempCashdraweronoffonsalemode)) {
                vCashdraweronoffonsalemode = GlobalMemberValues.getIntAtString(tempCashdraweronoffonsalemode);
            }

            if (!GlobalMemberValues.isStrEmpty(tempVoidprint)) {
                vVoidprint = GlobalMemberValues.getIntAtString(tempVoidprint);
            }

            if (!GlobalMemberValues.isStrEmpty(tempReturnprint)) {
                vReturnprint = GlobalMemberValues.getIntAtString(tempReturnprint);
            }

            if (!GlobalMemberValues.isStrEmpty(tempPrintLanguage)) {
                vPrintlanguage = tempPrintLanguage;
            }

            if (GlobalMemberValues.isStrEmpty(tempSignaturePrintYN)) {
                vSignaturePrintYN = "N";
            } else {
                if (tempSignaturePrintYN == "Y" || tempSignaturePrintYN.equals("Y")) {
                    vSignaturePrintYN = "Y";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempSignaturePrintSeperately)) {
                vSignaturePrintSeperatlely = "N";
            } else {
                if (tempSignaturePrintSeperately == "Y" || tempSignaturePrintSeperately.equals("Y")) {
                    vSignaturePrintSeperatlely = "Y";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempModifierPrintYN)) {
                vModifierPrintYN = "N";
            } else {
                if (tempModifierPrintYN == "Y" || tempModifierPrintYN.equals("Y")) {
                    vModifierPrintYN = "Y";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempCashDrawerOnReceipt)) {
                vCashdraweronoffonreceipt = "N";
            } else {
                if (tempCashDrawerOnReceipt == "Y" || tempCashDrawerOnReceipt.equals("Y")) {
                    vCashdraweronoffonreceipt = "Y";
                } else {
                    vCashdraweronoffonreceipt = "N";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempCardmerchantreceiptyn)) {
                vCardmerchantreceiptyn = "N";
            } else {
                if (tempCardmerchantreceiptyn == "Y" || tempCardmerchantreceiptyn.equals("Y")) {
                    vCardmerchantreceiptyn = "Y";
                } else {
                    vCardmerchantreceiptyn = "N";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempOrdernumbersectionviewyn)) {
                vOrdernumbersectionviewyn = "Y";
            } else {
                if (tempOrdernumbersectionviewyn == "Y" || tempOrdernumbersectionviewyn.equals("Y")) {
                    vOrdernumbersectionviewyn = "Y";
                } else {
                    vOrdernumbersectionviewyn = "N";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempTaxexemptprintingyn)) {
                vTaxexemptprintingyn = "Y";
            } else {
                if (tempTaxexemptprintingyn == "Y" || tempTaxexemptprintingyn.equals("Y")) {
                    vTaxexemptprintingyn = "Y";
                } else {
                    vTaxexemptprintingyn = "N";
                }
            }

            if (GlobalMemberValues.isStrEmpty(tempLogoprintingonreceiptyn)) {
                vLogoprintingonreceiptyn = "N";
            } else {
                if (tempLogoprintingonreceiptyn == "Y" || tempLogoprintingonreceiptyn.equals("Y")) {
                    vLogoprintingonreceiptyn = "Y";
                } else {
                    vLogoprintingonreceiptyn = "N";
                }
            }
        }

        // Printer Name --------------------------------------------------------------------------------------------
        printernameSpinner = (Spinner)findViewById(R.id.printernameSpinner);
        mGeneralArrayListForSpinnerPrinterName = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.PRINTERGROUP.length; i++) {
            mGeneralArrayListForSpinnerPrinterName.add(GlobalMemberValues.PRINTERGROUP[i]);
        }
        mSpinnerAdapterPrinterName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerPrinterName);
        mSpinnerAdapterPrinterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printernameSpinner.setAdapter(mSpinnerAdapterPrinterName);

        int tempPNPosition = 0;
        for (int i = 0; i < GlobalMemberValues.PRINTERGROUP.length; i++) {
            if (vPrintername.equals(GlobalMemberValues.PRINTERGROUP[i])) {
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

        networkIpTextView1.setOnClickListener(logsSaveListener);
        networkIpTextView2.setOnClickListener(logsSaveListener);
        networkIpTextView3.setOnClickListener(logsSaveListener);
        networkIpTextView4.setOnClickListener(logsSaveListener);
        networkPrinterPortTextView.setOnClickListener(logsSaveListener);

        networkportcolon = (TextView)findViewById(R.id.networkportcolon);

        // Receipt Footer
        receiptfooterEditText = (EditText)findViewById(R.id.receiptfooterEditText);
        receiptfooterEditText.setText(vReceiptfooter);
        receiptfooterEditText.setTextSize(receiptfooterEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        receiptfooterEditText.setOnClickListener(logsSaveListener);

        // Tender Use for Zero Balance
        tenderuseforzerobalanceSwitch = (Switch)findViewById(R.id.tenderuseforzerobalanceSwitch);
        if (vTenderuseforzerobalance == 0) {
            tenderuseforzerobalanceSwitch.setChecked(true);
        } else {
            tenderuseforzerobalanceSwitch.setChecked(false);
        }

        dualdpadlocalpathBtn = (Button) findViewById(R.id.dualdpadlocalpathBtn);
        dualdpadlocalpathBtn.setOnClickListener(mButtonClick);
        dualdpadlocalpathBtn.setTextSize(dualdpadlocalpathBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        imgLn = (LinearLayout)findViewById(R.id.imgLn);
        receiptlogoimgLn = (LinearLayout)findViewById(R.id.receiptlogoimgLn);

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

        // Employee Commission Policy -----------------------------------------------------------------------------------------
        employeecommissionpolicyRadioGroup = (RadioGroup)findViewById(R.id.employeecommissionpolicyRadioGroup);
        employeecommissionpolicyRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        employeecommissionpolicyEmployeeOnlyRadioButton = (RadioButton)findViewById(R.id.employeecommissionpolicyEmployeeOnlyRadioButton);
        employeecommissionpolicyEmployeeOnlyRadioButton.setTextSize(employeecommissionpolicyEmployeeOnlyRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        employeecommissionpolicyEmployeeMenuRadioButton = (RadioButton)findViewById(R.id.employeecommissionpolicyEmployeeMenuRadioButton);
        employeecommissionpolicyEmployeeMenuRadioButton.setTextSize(employeecommissionpolicyEmployeeMenuRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vEmployeecommissionpolicy) {
            case 0 : {
                employeecommissionpolicyEmployeeOnlyRadioButton.setChecked(true);
                break;
            }
            case 1 : {
                employeecommissionpolicyEmployeeMenuRadioButton.setChecked(true);
                break;
            }
        }
        // --------------------------------------------------------------------------------------------------------------------

        // Manager Pwd Use for Void
        managerpwduseforvoidSwitch = (Switch)findViewById(R.id.managerpwduseforvoidSwitch);
        if (vManagerpwduseforvoid == 0) {
            managerpwduseforvoidSwitch.setChecked(true);
        } else {
            managerpwduseforvoidSwitch.setChecked(false);
        }

        cashdraweronoffonsalemodeSwitch = (Switch)findViewById(R.id.cashdraweronoffonsalemodeSwitch);
        if (vCashdraweronoffonsalemode == 0) {
            cashdraweronoffonsalemodeSwitch.setChecked(true);
        } else {
            cashdraweronoffonsalemodeSwitch.setChecked(false);
        }
        cashdraweronoffonsalemodeSwitch.setOnClickListener(logsSaveListener);

        cashdraweronoffonreceiptSwitch = (Switch)findViewById(R.id.cashdraweronoffonreceiptSwitch);
        if (vCashdraweronoffonreceipt == "Y" || vCashdraweronoffonreceipt.equals("Y")) {
            cashdraweronoffonreceiptSwitch.setChecked(true);
        } else {
            cashdraweronoffonreceiptSwitch.setChecked(false);
        }
        cashdraweronoffonreceiptSwitch.setOnClickListener(logsSaveListener);

        cardmerchantreceiptynSwitch = (Switch)findViewById(R.id.cardmerchantreceiptynSwitch);
        if (vCardmerchantreceiptyn == "Y" || vCardmerchantreceiptyn.equals("Y")) {
            cardmerchantreceiptynSwitch.setChecked(true);
        } else {
            cardmerchantreceiptynSwitch.setChecked(false);
        }
        cardmerchantreceiptynSwitch.setOnClickListener(logsSaveListener);

        // Void 프린트 여부
        voidPrintSwitch = (Switch)findViewById(R.id.voidPrintSwitch);
        if (vVoidprint == 0) {
            voidPrintSwitch.setChecked(true);
        } else {
            voidPrintSwitch.setChecked(false);
        }

        // Return 프린트 여부
        returnPrintSwitch = (Switch)findViewById(R.id.returnPrintSwitch);
        if (vReturnprint == 0) {
            returnPrintSwitch.setChecked(true);
        } else {
            returnPrintSwitch.setChecked(false);
        }

        // Singed 프린트 여부
        signatureprintynSwitch = (Switch)findViewById(R.id.signatureprintynSwitch);
        if (vSignaturePrintYN == "Y" || vSignaturePrintYN.equals("Y")) {
            signatureprintynSwitch.setChecked(true);
        } else {
            signatureprintynSwitch.setChecked(false);
        }
        signatureprintynSwitch.setOnClickListener(logsSaveListener);

        // Singed Seperately 프린트 여부
        signatureprintseperatelySwitch = (Switch)findViewById(R.id.signatureprintseperatelySwitch);
        if (vSignaturePrintSeperatlely == "Y" || vSignaturePrintSeperatlely.equals("Y")) {
            signatureprintseperatelySwitch.setChecked(true);
        } else {
            signatureprintseperatelySwitch.setChecked(false);
        }
        signatureprintseperatelySwitch.setOnClickListener(logsSaveListener);

        modifierprintynSwitch = (Switch)findViewById(R.id.modifierprintynSwitch);
        if (vModifierPrintYN == "Y" || vModifierPrintYN.equals("Y")) {
            modifierprintynSwitch.setChecked(true);
        } else {
            modifierprintynSwitch.setChecked(false);
        }
        modifierprintynSwitch.setOnClickListener(logsSaveListener);

        ordernumbersectionviewynSwitch = (Switch)findViewById(R.id.ordernumbersectionviewynSwitch);
        if (vOrdernumbersectionviewyn == "Y" || vOrdernumbersectionviewyn.equals("Y")) {
            ordernumbersectionviewynSwitch.setChecked(true);
        } else {
            ordernumbersectionviewynSwitch.setChecked(false);
        }
        ordernumbersectionviewynSwitch.setOnClickListener(logsSaveListener);

        taxexemptprintingynSwitch = (Switch)findViewById(R.id.taxexemptprintingynSwitch);
        if (vTaxexemptprintingyn == "Y" || vTaxexemptprintingyn.equals("Y")) {
            taxexemptprintingynSwitch.setChecked(true);
        } else {
            taxexemptprintingynSwitch.setChecked(false);
        }
        taxexemptprintingynSwitch.setOnClickListener(logsSaveListener);

        logoprintingonreceiptynSwitch = (Switch)findViewById(R.id.logoprintingonreceiptynSwitch);
        if (vLogoprintingonreceiptyn == "Y" || vLogoprintingonreceiptyn.equals("Y")) {
            logoprintingonreceiptynSwitch.setChecked(true);
            imgLn.setVisibility(View.VISIBLE);
        } else {
            logoprintingonreceiptynSwitch.setChecked(false);
            imgLn.setVisibility(View.GONE);
        }
        logoprintingonreceiptynSwitch.setOnClickListener(logsSaveListener);

        empinfoprintingynSwitch = (Switch)findViewById(R.id.empinfoprintingynSwitch);
        if (vEmpinfoprintingyn == "Y" || vEmpinfoprintingyn.equals("Y")) {
            empinfoprintingynSwitch.setChecked(true);
        } else {
            empinfoprintingynSwitch.setChecked(false);
        }
        empinfoprintingynSwitch.setOnClickListener(logsSaveListener);

        menulistprintingynSwitch = (Switch)findViewById(R.id.menulistprintingynSwitch);
        if (vMenulistprintingyn == "Y" || vMenulistprintingyn.equals("Y")) {
            menulistprintingynSwitch.setChecked(true);
        } else {
            menulistprintingynSwitch.setChecked(false);
        }
        menulistprintingynSwitch.setOnClickListener(logsSaveListener);

        logoprintingonreceiptynSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    imgLn.setVisibility(View.VISIBLE);
                    setReceiptLogoLn();
                } else {
                    imgLn.setVisibility(View.GONE);
                    receiptlogoimgLn.setVisibility(View.GONE);
                }
            }
        });

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

        settingsDevicePrinterTitleTextView1 = (TextView)findViewById(R.id.settingsDevicePrinterTitleTextView1);
        settingsDevicePrinterTitleTextView1.setTextSize(settingsDevicePrinterTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
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

        settingsDevicePrinterSubTitleTextView10 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView10);
        settingsDevicePrinterSubTitleTextView10.setTextSize(settingsDevicePrinterSubTitleTextView10.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView11 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView11);
        settingsDevicePrinterSubTitleTextView11.setTextSize(settingsDevicePrinterSubTitleTextView11.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView12 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView12);
        settingsDevicePrinterSubTitleTextView12.setTextSize(settingsDevicePrinterSubTitleTextView12.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView13 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView13);
        settingsDevicePrinterSubTitleTextView13.setTextSize(settingsDevicePrinterSubTitleTextView13.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView14 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView14);
        settingsDevicePrinterSubTitleTextView14.setTextSize(settingsDevicePrinterSubTitleTextView14.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView15 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView15);
        settingsDevicePrinterSubTitleTextView15.setTextSize(settingsDevicePrinterSubTitleTextView15.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView16 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView16);
        settingsDevicePrinterSubTitleTextView16.setTextSize(settingsDevicePrinterSubTitleTextView16.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView17 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView17);
        settingsDevicePrinterSubTitleTextView17.setTextSize(settingsDevicePrinterSubTitleTextView17.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView18 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView18);
        settingsDevicePrinterSubTitleTextView18.setTextSize(settingsDevicePrinterSubTitleTextView18.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView19 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView19);
        settingsDevicePrinterSubTitleTextView19.setTextSize(settingsDevicePrinterSubTitleTextView19.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView20 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView20);
        settingsDevicePrinterSubTitleTextView20.setTextSize(settingsDevicePrinterSubTitleTextView20.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView21 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView21);
        settingsDevicePrinterSubTitleTextView21.setTextSize(settingsDevicePrinterSubTitleTextView21.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView22 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView22);
        settingsDevicePrinterSubTitleTextView22.setTextSize(settingsDevicePrinterSubTitleTextView22.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView23 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView23);
        settingsDevicePrinterSubTitleTextView23.setTextSize(settingsDevicePrinterSubTitleTextView23.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView24 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView24);
        settingsDevicePrinterSubTitleTextView24.setTextSize(settingsDevicePrinterSubTitleTextView24.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView25 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView25);
        settingsDevicePrinterSubTitleTextView25.setTextSize(settingsDevicePrinterSubTitleTextView25.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView26 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView26);
        settingsDevicePrinterSubTitleTextView26.setTextSize(settingsDevicePrinterSubTitleTextView26.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsDevicePrinterSubTitleTextView27 = (TextView)findViewById(R.id.settingsDevicePrinterSubTitleTextView27);
        settingsDevicePrinterSubTitleTextView27.setTextSize(settingsDevicePrinterSubTitleTextView27.getTextSize() * GlobalMemberValues.getGlobalFontSize());

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

        receiptLogoImgTv = (TextView)findViewById(R.id.receiptLogoImgTv);
        receiptLogoImgTv.setTextSize(receiptLogoImgTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        receiptlogimgIv = (ImageView) findViewById(R.id.receiptlogimgIv);

        // 저장된 로고 이미지가 있는지 체크
        path = GlobalMemberValues.ADFILELOCALPATH;
        File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
        String adImage = isFile.getPath();
        if (new File(adImage).exists()) {
            receiptLogoImgTv.setText("There is the downloaded logo images");
            if (vLogoprintingonreceiptyn == "Y" || vLogoprintingonreceiptyn.equals("Y")) {
                setReceiptLogoLn();
            }
        } else {
            receiptlogoimgLn.setVisibility(View.GONE);
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
    }

    View.OnClickListener mClickButtonAboutPrinter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.connectPrintButton : {
                    LogsSave.saveLogsInDB(158);
//                    GlobalMemberValues.displayDialog(context, "NZ Salon", "Under Construction", "Close");

//                    if (!mSavedPrinterName.equals(selectedItemPrinterNameSpinner)) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "Please try again saved", "Close");
//                    } else {
//                      PosbankPrinter posbankPrinter = new PosbankPrinter(context, "SETTINGS");
//                        posbankPrinter.setConnect(true);
//                    }
                    //jihun park addup
                    if (selectedItemPrinterNameSpinner.equals("Giant-100")){

                        new AlertDialog.Builder(mActivity)
                                .setTitle("RECONNECT")
                                .setMessage("Do you want to reconnect all connected printers?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //connect
                                        Sam4GiantPrinter.Sam4sPrinterReset("main");
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
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
                                        GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                        Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                                        giantPrinter.kitchenPrint_connect_test();
                                    }
                                }).show();


                    }

                    break;
                }

                case R.id.testPrintButton : {
                    LogsSave.saveLogsInDB(159);
                    GlobalMemberValues.disconnectPrinter2();
                    GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
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
                    LogsSave.saveLogsInDB(174);
                    int resultValue = setSettingsDevicePrinter();
                    if (resultValue > 0) {
                        GlobalMemberValues.displayDialog(context, "Settings - Device & Printer", "Successfully Updated", "Close");
                        switch (mSavedPrinterName) {
                            case "STAR" : {
                                GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                break;
                            }
                            case "PosBank" : {
                                // 테스트 프린트
                                //testPrinting();
                                // 연결을 끊는다..
                                GlobalMemberValues.disconnectPrinter();
                                GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                break;
                            }
                            case "Elo" : {
                                GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                break;
                            }
                            case "Giant-100" : {
                                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                                giantPrinter.disconnect();
                                // 이 경우 쿼리문 실행하지 않고 내부 static ip 값 변경.
                                String tempip = networkIpTextView1.getText().toString() + "." + networkIpTextView2.getText().toString()+ "."  + networkIpTextView3.getText().toString()+ "."  + networkIpTextView4.getText().toString();
                                GlobalMemberValues.NETWORKPRINTERIP = tempip;
                                GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                break;
                            }
                            case "Epson-T88" :{
                                String tempip = networkIpTextView1.getText().toString() + "." + networkIpTextView2.getText().toString()+ "."  + networkIpTextView3.getText().toString()+ "."  + networkIpTextView4.getText().toString();
                                GlobalMemberValues.NETWORKPRINTERIP = tempip;
                                GlobalMemberValues.printReceiptByJHP(null, context, "testprint");
                                break;
                            }
                        }
                    } else {
                        GlobalMemberValues.displayDialog(context, "Settings - Device & Printer", "Update Failure", "Close");
                    }
                }
                case R.id.dualdpadlocalpathBtn : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("File Download")
                                .setMessage("Do you want to download the logo file?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadFile();
                                    }
                                }).show();
                    }

                    break;
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
        int insEmployeecommissionpolicy = 1;
        int insManagerpwduseforvoid = 0;
        int insCashdraweronoffonsalemode = 0;
        int insVoidPrint = 0;
        int insReturnPrint = 0;
        String insSigaturePrintYN = "N";
        String insSigaturePrintSeperately = "N";
        String insModifierPrintYN = "N";

        String insCashdraweronoffonreceipt = "N";
        String insCardmerchantreceiptyn = "N";

        String insNetworkprinterip1 = "";
        String insNetworkprinterip2 = "";
        String insNetworkprinterip3 = "";
        String insNetworkprinterip4 = "";
        String insNetworkprinterport = "";

        String insPrintLanguage = "";

        String insOrdernumbersectionviewyn = "";

        String insTaxexemptprintingyn = "";

        String insLogoprintingonreceiptyn = "N";

        String insEmpinfoprintingyn = "Y";
        String insMenulistprintingyn = "Y";


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

        insEmployeecommissionpolicy = mEmployeeCommissionPolicy;

        if (managerpwduseforvoidSwitch.isChecked()) {
            insManagerpwduseforvoid = 0;
        } else {
            insManagerpwduseforvoid = 1;
        }

        if (cashdraweronoffonsalemodeSwitch.isChecked()) {
            insCashdraweronoffonsalemode = 0;
        } else {
            insCashdraweronoffonsalemode = 1;
        }

        if (cashdraweronoffonreceiptSwitch.isChecked()) {
            insCashdraweronoffonreceipt = "Y";
        } else {
            insCashdraweronoffonreceipt = "N";
        }

        if (cardmerchantreceiptynSwitch.isChecked()) {
            insCardmerchantreceiptyn = "Y";
        } else {
            insCardmerchantreceiptyn = "N";
        }

        if (voidPrintSwitch.isChecked()) {
            insVoidPrint = 0;
        } else {
            insVoidPrint = 1;
        }

        if (returnPrintSwitch.isChecked()) {
            insReturnPrint = 0;
        } else {
            insReturnPrint = 1;
        }

        if (signatureprintynSwitch.isChecked()) {
            insSigaturePrintYN = "Y";
        } else {
            insSigaturePrintYN = "N";
        }

        if (signatureprintseperatelySwitch.isChecked()) {
            insSigaturePrintSeperately = "Y";
        } else {
            insSigaturePrintSeperately = "N";
        }

        if (modifierprintynSwitch.isChecked()) {
            insModifierPrintYN = "Y";
        } else {
            insModifierPrintYN = "N";
        }

        if (ordernumbersectionviewynSwitch.isChecked()) {
            insOrdernumbersectionviewyn = "Y";
        } else {
            insOrdernumbersectionviewyn = "N";
        }

        if (taxexemptprintingynSwitch.isChecked()) {
            insTaxexemptprintingyn = "Y";
        } else {
            insTaxexemptprintingyn = "N";
        }

        if (logoprintingonreceiptynSwitch.isChecked()) {
            insLogoprintingonreceiptyn = "Y";
        } else {
            insLogoprintingonreceiptyn = "N";
        }

        if (empinfoprintingynSwitch.isChecked()) {
            insEmpinfoprintingyn = "Y";
        } else {
            insEmpinfoprintingyn = "N";
        }

        if (menulistprintingynSwitch.isChecked()) {
            insMenulistprintingyn = "Y";
        } else {
            insMenulistprintingyn = "N";
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
        String updStrQuery = "update salon_storestationsettings_deviceprinter set " +
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
                " employeecommissionpolicy = '" + insEmployeecommissionpolicy + "', " +
                " managerpwduseforvoid = '" + insManagerpwduseforvoid + "', " +
                " cashdraweronoffonsalemode = '" + insCashdraweronoffonsalemode + "', " +
                " cashdraweropenonreceipt = '" + insCashdraweronoffonreceipt + "', " +
                " voidprint = '" + insVoidPrint + "', " +
                " returnprint = '" + insReturnPrint + "', " +
                " networkprinterip1 = '" + insNetworkprinterip1 + "', " +
                " networkprinterip2 = '" + insNetworkprinterip2 + "', " +
                " networkprinterip3 = '" + insNetworkprinterip3 + "', " +
                " networkprinterip4 = '" + insNetworkprinterip4 + "', " +
                " networkprinterport = '" + insNetworkprinterport + "', " +
                " printlanguage = '" + insPrintLanguage + "', " +
                " signatureprintyn = '" + insSigaturePrintYN + "', " +
                " signatureprintseperately = '" + insSigaturePrintSeperately + "', " +
                " modifierprintyn = '" + insModifierPrintYN + "', " +
                " cardmerchantreceiptyn = '" + insCardmerchantreceiptyn + "', " +
                " ordernumbersectionviewyn = '" + insOrdernumbersectionviewyn + "', " +
                " taxexemptprintingyn = '" + insTaxexemptprintingyn + "', " +
                " logoprintingonreceiptyn = '" + insLogoprintingonreceiptyn + "', " +
                " empinfoprintingyn = '" + insEmpinfoprintingyn + "', " +
                " menulistprintingyn = '" + insMenulistprintingyn + "', " +
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

                GlobalMemberValues.GLOBAL_COMMISSIONRATIOTYPE = insEmployeecommissionpolicy;

                if (insTaxexemptprintingyn == "Y" || insTaxexemptprintingyn.equals("Y")) {
                    GlobalMemberValues.mUseTaxExemptTxtYN = true;
                } else {
                    GlobalMemberValues.mUseTaxExemptTxtYN = false;
                }

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
            LogsSave.saveLogsInDB(157);
            selectedItemPrinterNameSpinner = mGeneralArrayListForSpinnerPrinterName.get(position);
            GlobalMemberValues.logWrite("SYSTEMDEVICEPRINTER2", "Selected Printer Item : " + selectedItemPrinterNameSpinner + "\n");

            if (selectedItemPrinterNameSpinner.equals("PosBank")) {
                // posbank connect....!
                PosbankPrinter posbankPrinter = new PosbankPrinter(context, "SETTINGS");
                posbankPrinter.setConnect(true);
            }

            if (!selectedItemPrinterNameSpinner.equals("Giant-100")) {
                // Giant 100
                connectPrintButton.setVisibility(View.VISIBLE);
            } else {
                connectPrintButton.setVisibility(View.GONE);
            }

            if (selectedItemPrinterNameSpinner.equals("STAR")){

            }

            if (!selectedItemPrinterNameSpinner.equals("Epson-T88")) {
                String[] arr_ip = GlobalMemberValues.STATION_IP.split("\\.");
                if (arr_ip.length != 0){
                    if (!arr_ip[0].isEmpty()) networkIpTextView1.setText(arr_ip[0]);
                    if (arr_ip.length > 1 && !arr_ip[1].isEmpty()) networkIpTextView2.setText(arr_ip[1]);
                    if (arr_ip.length > 2 && !arr_ip[2].isEmpty()) networkIpTextView3.setText(arr_ip[2]);
                    networkPrinterPortTextView.setVisibility(View.GONE);
                    networkportcolon.setVisibility(View.GONE);
                }
            } else {
                networkPrinterPortTextView.setVisibility(View.GONE);
                networkportcolon.setVisibility(View.GONE);
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
            LogsSave.saveLogsInDB(160);
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


    // 파일 다운로드 관련 ------------------------------------------------------------------------------------------------

    public void downloadFile() {
        String adImage = MainActivity.mDbInit.dbExecuteReadReturnString(" select posreceiptlogimg from salon_storegeneral ");
        String adType = "0";
        if (GlobalMemberValues.isStrEmpty(adType)) {
            adType = "0";
        }

        if (GlobalMemberValues.isStrEmpty(adImage)) {
            GlobalMemberValues.displayDialog(context, "Warning", "No files to download\nPlease synchronize with the cloud", "Close");
            return;
        } else {
            //웹브라우저에 아래 링크를 입력하면 Alight.avi 파일이 다운로드됨.
            //final String fileURL = "http://nzqsradmin.wanhaye.com/stores_userimages/_100001357_GSFDT08418058.mp4";
            final String fileURL = adImage;

            path = GlobalMemberValues.ADFILELOCALPATH;

            if (adType == "0" || adType.equals("0")) {
                outputFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
            } else {
                //outputFile = new File(path, "ad.mp4"); //파일명까지 포함함 경로의 File 객체 생성
            }

            //새로 다운로드 받는 경우
            if (BuildConfig.DEBUG) {
                Log.i("jjjdownloadlog", "여기실행...1" + "\n");
            }

            final DownloadFilesTask downloadTask = new DownloadFilesTask(context);
            downloadTask.execute(fileURL);

            progressBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });
        }
    }


    private class DownloadFilesTask extends AsyncTask<String, String, Long> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadFilesTask(Context context) {
            this.context = context;
        }


        //파일 다운로드를 시작하기 전에 프로그레스바를 화면에 보여줍니다.
        @Override
        protected void onPreExecute() { //2
            super.onPreExecute();

            //사용자가 다운로드 중 파워 버튼을 누르더라도 CPU가 잠들지 않도록 해서
            //다시 파워버튼 누르면 그동안 다운로드가 진행되고 있게 됩니다.
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();

            progressBar.show();
        }


        //파일 다운로드를 진행합니다.
        @Override
        protected Long doInBackground(String... string_url) { //3
            int count;
            long FileSize = -1;
            InputStream input = null;
            OutputStream output = null;
            URLConnection connection = null;

            try {
                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "여기실행...2" + "\n");
                }

                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "string_url[0] : " + string_url[0] + "\n");
                }

                URL url = new URL(string_url[0]);
                connection = url.openConnection();
                connection.connect();

                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "여기실행...3" + "\n");
                }

                //파일 크기를 가져옴
                FileSize = connection.getContentLength();

                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "FileSize : " + FileSize + "\n");
                }

                //URL 주소로부터 파일다운로드하기 위한 input stream
                input = new BufferedInputStream(url.openStream(), 8192);

                path = GlobalMemberValues.ADFILELOCALPATH;

                String adType = "0";
                if (GlobalMemberValues.isStrEmpty(adType)) {
                    adType = "0";
                }
                if (adType == "0" || adType.equals("0")) {
                    outputFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
                } else {
                    //outputFile = new File(path, "ad.mp4"); //파일명까지 포함함 경로의 File 객체 생성
                }

                // 저장하기 위한 Output stream
                output = new FileOutputStream(outputFile);

                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "여기실행...4" + "\n");
                }

                byte data[] = new byte[1024];
                long downloadedSize = 0;
                while ((count = input.read(data)) != -1) {
                    //사용자가 BACK 버튼 누르면 취소가능
                    if (isCancelled()) {
                        input.close();
                        return Long.valueOf(-1);
                    }

                    downloadedSize += count;

                    if (FileSize > 0) {
                        float per = ((float)downloadedSize/FileSize) * 100;
                        String str = "Downloaded " + downloadedSize + "KB / " + FileSize + "KB (" + (int)per + "%)";
                        publishProgress("" + (int) ((downloadedSize * 100) / FileSize), str);

                    }

                    //파일에 데이터를 기록합니다.
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();

                // Close streams
                output.close();
                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                if (BuildConfig.DEBUG) {
                    Log.i("jjjdownloadlog", "Error : " + e.getMessage() + "\n");
                }

            }finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                mWakeLock.release();

            }
            return FileSize;
        }


        //다운로드 중 프로그레스바 업데이트
        @Override
        protected void onProgressUpdate(String... progress) { //4
            super.onProgressUpdate(progress);

            // if we get here, length is known, now set indeterminate to false
            progressBar.setIndeterminate(false);
            progressBar.setMax(100);
            progressBar.setProgress(Integer.parseInt(progress[0]));
            progressBar.setMessage(progress[1]);
        }

        //파일 다운로드 완료 후
        @Override
        protected void onPostExecute(Long size) { //5
            super.onPostExecute(size);

            progressBar.dismiss();

            if ( size > 0) {
                Toast.makeText(getApplicationContext(), "File download completed. File Size - " + size.toString(), Toast.LENGTH_LONG).show();
                finishDownload();

            } else {
                Toast.makeText(getApplicationContext(), "Download Error...", Toast.LENGTH_LONG).show();
            }

        }

    }


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

    private void finishDownload() {
        setReceiptLogoLn();

        //GlobalMemberValues.dismissPresentationView();
        // showPresentationView 를 실행하면 이상하게도 다음 주문부터는
        // Change 창 뜰때 Presentation 이 가려지지 않음..
        //GlobalMemberValues.showPresentationView();
        //MainActivity.updatePresentation();
        /**
         Uri videoUri = Uri.fromFile(new File(path));
         Intent videoIntent = new Intent(Intent.ACTION_VIEW);
         videoIntent.setDataAndType(videoUri, "video/*");
         if (videoIntent.resolveActivity(getPackageManager()) != null) {
         startActivity(Intent.createChooser(videoIntent, null));
         }
         **/

    }
    // ---------------------------------------------------------------------------------------------------------------------


    public void setReceiptLogoLn() {
        path = GlobalMemberValues.ADFILELOCALPATH;
        File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
        String adImage = isFile.getPath();
        if (new File(adImage).exists()) {
            receiptLogoImgTv.setText("There is the downloaded logo images");

            receiptlogoimgLn.setVisibility(View.VISIBLE);
            Bitmap imgBitmap = BitmapFactory.decodeFile(isFile.getAbsolutePath());
            receiptlogimgIv.setImageBitmap(imgBitmap);
        }
    }

    View.OnClickListener logsSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.networkIpTextView1 :
                case R.id.networkIpTextView2 :
                case R.id.networkIpTextView3 :
                case R.id.networkIpTextView4 :
                case R.id.networkPrinterPortTextView :
                    LogsSave.saveLogsInDB(161);
                    break;
                case R.id.receiptfooterEditText :
                    LogsSave.saveLogsInDB(162);
                    break;
                case R.id.cashdraweronoffonsalemodeSwitch :
                    LogsSave.saveLogsInDB(163);
                    break;
                case R.id.cashdraweronoffonreceiptSwitch :
                    LogsSave.saveLogsInDB(164);
                    break;
                case R.id.signatureprintynSwitch :
                    LogsSave.saveLogsInDB(165);
                    break;
                case R.id.signatureprintseperatelySwitch :
                    LogsSave.saveLogsInDB(166);
                    break;
                case R.id.modifierprintynSwitch :
                    LogsSave.saveLogsInDB(167);
                    break;
                case R.id.cardmerchantreceiptynSwitch :
                    LogsSave.saveLogsInDB(168);
                    break;
                case R.id.ordernumbersectionviewynSwitch :
                    LogsSave.saveLogsInDB(169);
                    break;
                case R.id.taxexemptprintingynSwitch :
                    LogsSave.saveLogsInDB(170);
                    break;
                case R.id.empinfoprintingynSwitch :
                    LogsSave.saveLogsInDB(171);
                    break;
                case R.id.menulistprintingynSwitch :
                    LogsSave.saveLogsInDB(172);
                    break;
                case R.id.logoprintingonreceiptynSwitch :
                    LogsSave.saveLogsInDB(173);
                    break;

            }

        }
    };

    private final ConnectionCallback mConnectionCallback = new ConnectionCallback() {
        @Override
        public void onConnected(boolean result, int resultCode) {
            if (!mIsForeground) {
                return;
            }

            if (!result) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String message;

                if (resultCode == StarResultCode.FAILURE_IN_USE) {
                    message = "Check the device. (In use)\nThen touch up the Refresh button.";
                }
                else {
                    message = "Check the device. (Power and Bluetooth pairing)\nThen touch up the Refresh button.";
                }

//                mComment.setText(message);
//                mComment.setTextColor(Color.RED);

//                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(OPEN_FAILURE_DIALOG);
//                dialog.setTitle("Communication Result");
//                dialog.setMessage(Communication.getCommunicationResultMessage(new CommunicationResult(Result.ErrorOpenPort, resultCode)));
//                dialog.setPositiveButton("OK");
//                dialog.show(getChildFragmentManager());

                return;
            }
            print();
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


    @Override
    public void onDialogResult(String tag, Intent data) {

    }

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

//
    private void print() {
        mProgressDialog.show();

        byte[] data;

        PrinterSettingManager settingManager = new PrinterSettingManager(MainActivity.mContext);
        PrinterSettings settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(27);
        int paperSize = 832;

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(0, paperSize);
        data = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());


        Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), 30000, mCallback);     // 10000mS!!!
    }

//
    private final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(Communication.CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
            dialog.setPositiveButton("OK");
//            dialog.show(getChildFragmentManager());
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsForeground = false;
    }


}
