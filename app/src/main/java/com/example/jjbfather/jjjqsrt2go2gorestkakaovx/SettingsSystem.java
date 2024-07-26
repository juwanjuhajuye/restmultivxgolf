package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.multidex.BuildConfig;

import androidx.multidex.BuildConfig;

import org.jsoup.helper.StringUtil;

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

public class SettingsSystem extends Activity {
    static Activity mActivity;
    static Context context;
    public static Context insContext;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private LinearLayout receipttypeonnoselectLn;
    private CheckBox receipttypeonnoselectCb1, receipttypeonnoselectCb2;

    private LinearLayout dualdpadtypeLn;

    private Switch splashUseSwitch, downloadDataSwitch, clockinoutTypeSwitch, departmentViewYNSwitch, syncPointInSelectingCustomerSwitch;
    private Switch showCostAfterDCExtraSwitch, cardIsLaslSwitch;
    private Switch pointpaysavepointynSwitch;
    private Spinner devicekindSinner, tableboardtypeSinner;
    private Switch customerinfoviewSwitch, customerselectreceiptSwitch;
    private Switch cashpaduseSwitch, autoreceiptprintingynSwitch;
    private Switch couponinfoviewSwitch, onlineorderuseynSwitch;
    private Switch timemenuactiveSwitch, couponimmediatelySwitch;
    private Switch pageruseynSwitch, pagernumberautoynSwitch;
    private Switch productminalertynSwitch, batchincashoutynSwitch;
    private Switch startingcashprintynSwitch, saledatemodifyynSwitch;
    private Switch buttonmodifierbottomviewynSwith, modifierpriceviewynSwith;
    private Switch dualdisplaypossibleSwitch, pricedollarynSwitch;
    private Switch dualdpadtypeSwitch, thankyoupageynSwith;
    private Switch uploadynonlysalesdataSwith, autosaledatauploadynSwitch;
    private Switch cloudbackupintenderbackupynSwitch;
    private Switch itemanimationynSwitch, carddirectynSwitch;
    private Switch modifierqtyviewynSwith, vrkitchenprintingSwitch;
    private Switch autoweborderprintingSwitch;
    private Switch qtyaddupynSwitch;
    private Switch tipsalehistoryaddupynSwitch;
    private Switch passwordyninmodSwitch;
    private Switch ordernumbershowonpickuporderSwitch, pagernumbershowonpickuporderSwitch;
    private Switch cardstatususeSwitch, qtyinsynSwitch;
    private Switch customernumberviewynSwitch;
    private Switch pickuptypepopupuseynSwitch;
    private Switch printingcategoryynSwitch;
    private Switch billprintpopupynSwitch;

    private Switch pushpopupopenynSwitch;

    private Switch openmsgwhendeletemenu_ynSwitch;
    private Switch otherpayprinting_ynSwitch;
    private Switch tipprintingwhentogo_ynSwitch;

    private Switch custombillsplituseynSwitch;

    private Switch serverCode_useSwitch, serverPassword_useSwitch;

    private Switch customeronlinecheckynSwitch;

    private Switch saledatauploadpauseynSwitch;

    private Switch timeviewontableynSwitch;

    // 10272023
    private Switch itemdeletereasonynSwitch;

    // 01172024
    private Switch tableorderuseynSwitch;

    private EditText cloudUrlEditText;
    private EditText gmailIdEditText, gmailPwdEditText;
    private EditText timemenuchecktimeEditText, maxpagernumEditText, daystouploaddataEv;
    private EditText pagernumberheadertxtEditText;
    private EditText initcapacityEditText;

    private EditText salehistorypagenoEv;

    private EditText mssqldbipTextView1;

    private EditText startpagernumEditText;

    private RadioGroup inReverseRadioGroup, databaseBackupRadioGroup, pushtypeRadioGroup, timemenuopentypeRadioGroup;

    private RadioGroup mssqlinsatdownloadRadioGroup;

    private RadioButton yesRadioButton, noRadioButton;
    private RadioButton sensorRadioButton, forwardRadioButton, reverseRadioButton;
    private RadioButton databaseBackupNoRadioButton, databaseBackupInTenderRadioButton, databaseBackupInClosingAppRadioButton;
    private RadioButton pushtypeNoRadioButton, pushtypeGoogleRadioButton, pushtypeCloudSystemRadioButton;
    private RadioButton timemenuopentypeNoRadioButton, timemenuopentypePopupOpenRadioButton, timemenuopentypeAutoRadioButton;

    private Button saveSettingsSystemButton, systemResetButton;
    private Button deleteSalesButton, syncrealtimebtn;
    private Button dualdpadlocalpathBtn, dualdprestartBtn;

    private Button btn_setting_receipt_setting;

    private EditText employeecardstartnumEditText, employeecardcountnumEditText;
    private EditText giftcardstartnumEditText, giftcardcountnumEditText;

    private EditText pagernumofdigitsEditText;

    private TextView settingsSystemTitleTextView1, settingsSystemTitleTextView2;
    private TextView settingsSystemTitleTextView3, settingsSystemTitleTextView4;
    private TextView settingsSystemTitleTextView5, settingsSystemTitleTextView6;
    private TextView settingsSystemTitleTextView7, settingsSystemTitleTextView8;
    private TextView settingsSystemTitleTextView9;
    private TextView settingsSystemTitleTextView10, settingsSystemTitleTextView11;
    private TextView settingsSystemTitleTextView12, settingsSystemTitleTextView13;
    private TextView settingsSystemTitleTextView14, settingsSystemTitleTextView15;
    private TextView settingsSystemTitleTextView17, settingsSystemTitleTextView18;
    private TextView settingsSystemTitleTextView19, settingsSystemTitleTextView20;
    private TextView settingsSystemTitleTextView21, settingsSystemTitleTextView22;
    private TextView settingsSystemTitleTextView23, settingsSystemTitleTextView24;
    private TextView settingsSystemTitleTextView25, settingsSystemTitleTextView26;
    private TextView settingsSystemTitleTextView27, settingsSystemTitleTextView28;
    private TextView settingsSystemTitleTextView29, settingsSystemTitleTextView30;
    private TextView settingsSystemTitleTextView31, settingsSystemTitleTextView32;
    private TextView settingsSystemTitleTextView33, settingsSystemTitleTextView34;
    private TextView settingsSystemTitleTextView35, settingsSystemTitleTextView36;
    private TextView settingsSystemTitleTextView37, settingsSystemTitleTextView38;
    private TextView settingsSystemTitleTextView39, settingsSystemTitleTextView40;
    private TextView settingsSystemTitleTextView41, settingsSystemTitleTextView42;
    private TextView settingsSystemTitleTextView43, settingsSystemTitleTextView45;
    private TextView settingsSystemTitleTextView46, settingsSystemTitleTextView47;
    private TextView settingsSystemTitleTextView48, settingsSystemTitleTextView49;
    private TextView settingsSystemTitleTextView50, settingsSystemTitleTextView51;
    private TextView settingsSystemTitleTextView52, settingsSystemTitleTextView53;
    private TextView settingsSystemTitleTextView54, settingsSystemTitleTextView55;
    private TextView settingsSystemTitleTextView56, settingsSystemTitleTextView57;
    private TextView settingsSystemTitleTextView58, settingsSystemTitleTextView59;
    private TextView settingsSystemTitleTextView60, settingsSystemTitleTextView61;
    private TextView settingsSystemTitleTextView62, settingsSystemTitleTextView63;
    private TextView settingsSystemTitleTextView64, settingsSystemTitleTextView65;
    private TextView settingsSystemTitleTextView66, settingsSystemTitleTextView67;
    private TextView settingsSystemTitleTextView68, settingsSystemTitleTextView69;
    private TextView settingsSystemTitleTextView70, settingsSystemTitleTextView71;
    private TextView settingsSystemTitleTextView72, settingsSystemTitleTextView73;
    private TextView settingsSystemTitleTextView74, settingsSystemTitleTextView75;
    private TextView settingsSystemTitleTextView76;
    private TextView settingsSystemTitleTextView77, settingsSystemTitleTextView78;
    private TextView settingsSystemTitleTextView85;
    private TextView settingsSystemTitleTextView89, settingsSystemTitleTextView90;

    // 10272023
    private TextView settingsSystemTitleTextView91;

    // 01172024
    private TextView settingsSystemTitleTextView92;

    private TextView settingsSystemTitleTextView93;

    private TextView infoTextView1, infoTextView2;
    // 01172024
    private TextView infoTextView3;

    private TextView settingsSystemHttpTitleTextView, settingsSystemSlashTextView;
    private TextView timemenuminsTextView1, timemenuminsTextView2, daystouploaddataTextView;

    private TextView salehistorypagenoTextView;

    private String orgin_timeMenuAutoReload = "";
    private String orgin_onlineorderuseyn = "";
    private String orgin_timemenuchecktime = "";

    // 01172024
    private String orgin_tableorderuseyn = "";

    public int mInReverseValue = 2;
    public int mDatabaseBackUpValue = 0;
    public String mPushTypeValue = "2";

    public String mTimeMenuAutoReload = "N";

    public String mMssqlSyncYN = "N";

    public static ProgressDialog checkingProDial;

    public static String selectedItemDeviceNameSpinner = GlobalMemberValues.DEVICEGROUP[0];
    ArrayList<String> mGeneralArrayListForSpinnerDeviceName;
    ArrayAdapter<String> mSpinnerAdapterDeviceName;
    String mSavedDeviceName = "";

    // table board type
    public static String[] tableBoardTypeArr = {
            "Auto", "Type A - Big Size", "Type B - Middle Size", "Type C - Small Size"
    };
    public static String selectedItemTableBoardTypeSpinner = tableBoardTypeArr[1];
    ArrayList<String> mGeneralArrayListForSpinnerTableBoardType;
    ArrayAdapter<String> mSpinnerAdapterTableBoardType;
    String mSavedTableBoardType = "";


    public static String mFinalResult = "N";

    // 파일 다운로드 관련 ---------------------------------------------------------------------------
    private ProgressDialog progressBar;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    private File outputFile; //파일명까지 포함한 경로
    private File path;//디렉토리경로
    // ----------------------------------------------------------------------------------------------

    TextView txv_system_info_num;
    ImageButton img_button_info;
    InformationPopup informationPopup;

    LinearLayout customerinfoviewSwitch_type_ln;
    CheckBox customerinfoviewSwitch_type_here, customerinfoviewSwitch_type_togo, customerinfoviewSwitch_type_delivery;

    Button btn_transdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_system);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

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

        int vSplashUse = 0;
        int vInReverse = 0;
        String vCloudUrl = GlobalMemberValues.getReplaceText(
                GlobalMemberValues.getReplaceText(GlobalMemberValues.CLOUD_SERVER_URL_BASIC, "http://", ""), "https://", "");
        String vGmailId = "";
        String vGmailPwd = "";
        int vDownloadData = 1;
        int vDatabaseBackup = 0;
        int vClockinoutType = 0;
        String vDepartmentViewYN = "N";
        String vSyncPointYN = "N";

        String vShowcostafterdcextra = "N";
        String vCardislast = "N";
        String vPointpaysavepointyn = "N";
        String vTimemenuautoreload = "N";
        String vDeviceKind = "N";

        String vMssqlsyncyn = "N";
        String vMssqldbip = "";

        String vPushType = "2";

        String vPicktype_here = "N";
        String vPicktype_togo = "N";
        String vPicktype_delivery = "N";

        String vCustomerInfoShow = "N";
        String vCustomerSelectReceipt = "N";

        String vCashPadUse = "N";

        String vAutoReceiptPrintingYN = "Y";

        String vCouponInfoView = "Y";
        String vCouponImmediately = "Y";

        String vOnlineOrderUseYN = "Y";

        String vTimeMenuUseYN = "Y";

        String vEmployeeCardStartNumber = "";
        String vEmployeeCardCountNumber = "";

        String vGiftCardStartNumber = "";
        String vGiftCardCountNumber = "";

        String vTimemenuchecktime = "10";

        String vMaxPagerNum = "10";

        String vPagernumberheadertxt = "#";

        String vPagerUseYN = "N";

        String vPagerNumberAutoYN = "Y";

        String vProductminalertyn = "N";

        String vDaystouploaddataEv = "10";

        String vBatchincashoutyn = "N";

        String vStartingcashprintyn = "N";

        String vSaledatemodifyyn = "N";

        String vSalehistorypageno = "30";

        String vButtonmodifierbottomviewyn = "Y";

        String vReceipttypeonnoselect = "A";

        String vModifierpriceviewyn = "N";

        String vDualdisplaypossible = "N";

        String vPricedollarynSwitch = "N";

        String vDualdpadtypeSwitch = "W";

        String vThankyoupageyn = "N";

        String vUploadynonlysalesdata = "N";

        String vAutosaledatauploadyn = "N";

        String vCloudbackupintenderbackupyn = "N";

        String vItemanimationyn = "N";

        String vCarddirectyn = "N";

        String vVrkitchenprinting = "N";

        String vAutoweborderprinting = "N";

        String vModifierqtyviewyn = "Y";

        String vInitcapacity = "";

        // qty add
        String vQtyaddupyn = "N";
        // tip salehistory add visible
        String vTipaddsalehistory = "Y";

        String vPasswordyninmod = "N";

        String vTableBoardType = "2";

        String vOrdernumbershowonpickuporder = "N";
        String vPagernumbershowonpickuporder = "N";

        String vCardstatususe = "N";

        String vQtyinsyn = "N";

        String vCustomernumberviewyn = "N";

        String vServerCodeUseyn = "N";
        String vServerPasswordUseyn = "N";

        String vPickuptypepopupuseyn = "N";

        String vPrintingcategoryyn = "N";

        String vCustomer_info_here = "N";
        String vCustomer_info_togo = "N";
        String vCustomer_info_delivery = "N";

        String vOpenmsgwhendeletemenu_yn = "N";
        String vOtherpayprinting_yn = "N";
        String vTipprintingwhentogo_yn = "N";

        String vBillprintpopupyn = "N";

        String vCustombillsplituseyn = "N";

        String vPagernumofdigits = "0";

        String vCustomeronlinecheckyn = "Y";

        String vSaledatauploadpauseyn = "N";

        String vPushpopupopenyn = "N";

        String vTimeviewontableyn = "N";

        // 102720231
        String vItemdeletereasonyn = "N";

        // 01172024
        String vTableorderuseyn = "N";

        String vStartpagernum = "";

        String strQuery = "select splashuse, inreverse, cloudurl, downloaddata, databasebackup, clockinouttype, " +
                " gmailId, gmailPwd, departmentviewyn, mileagesyncinselectcustomer, showcostafterdcextra, cardislast, pointpaysavepointyn, timemenuautoreload, pushtype, devicekind, " +
                " picktype_here, picktype_togo, picktype_delivery, customerinfoshow, customerselectreceipt, " +
                " empcardstartnum, empcardcountnum, giftcardstartnum, giftcardcountnum, cashpaduse, autoreceiptprintingyn, couponinfoview, onlineorderuseyn, timemenuuseyn, couponimmediately, " +
                " timemenuchecktime, maxpagernum, pageruseyn, pagernumberautoyn, productminalertyn, daystouploaddata, batchincashoutyn, startingcashprintyn, saledatemodifyyn, salehistorypageno, " +
                " buttonmodifierbottomviewyn, receipttypeonnoselect, modifierpriceviewyn, pagernumberheadertxt, dualdisplaypossible, pricedollaryn, dualdpadtype, thankyoupageyn, uploadynonlysalesdata, " +
                " autosaledatauploadyn, cloudbackupintenderbackupyn, itemanimationyn, carddirectyn, modifierqtyviewyn, " +
                " mssqlsyncyn, mssqldbip, vrkitchenprinting, autoweborderprinting, initcapacity, qtyaddupyn, tipaddhistoryvisibleyn, passwordyninmod, tableboardtype, " +
                " ordernumbershowonpickuporder, pagernumbershowonpickuporder, cardstatususe, qtyinsyn, customernumberviewyn, servercodeuse, serverpassworduse, pickuptypepopupuseyn, printingcategoryyn, " +
                " customer_info_here_yn, customer_info_togo_yn, customer_info_delivery_yn, " +
                " openmsgwhendeletemenu_yn, otherpayprinting_yn, tipprintingwhentogo_yn, billprintpopupyn, custombillsplituseyn, pagernumofdigits, customeronlinecheckyn, " +
                " startpagernum, saledatauploadpauseyn, timeviewontableyn,  " +

                // 10272023
                " itemdeletereasonyn, " +

                // 01172024
                " tableorderuseyn, " +

                " pushpopupopenyn " +

                " from salon_storestationsettings_system ";
        Cursor settingsSystemCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            String tempSplashUse = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
            String tempInReverse = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
            String tempCloudUrl = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);
            String tempdownloadData = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);
            String tempDatabaseBackup = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(4), 1);
            String tempClockinoutType = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(5), 1);
            String tempGmailId = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(6), 1);
            String tempGmailPwd = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(7), 1);
            String tempDepartmentViewYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(8), 1);
            String tempSyncPointYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(9), 1);
            String tempShowcostafterdcextra = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(10), 1);
            String tempCardislast = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(11), 1);
            String tempPointpaysavepointyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(12), 1);
            String tempTimemenuautoreload = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(13), 1);
            String tempPushtype = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(14), 1);
            String tempDeviceKind = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(15), 1);
            String tempPicktype_here = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(16), 1);
            String tempPicktype_togo = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(17), 1);
            String tempPicktype_delivery = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(18), 1);
            String tempCustomerInfoShow = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(19), 1);
            String tempCustomerSelectReceipt = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(20), 1);
            String tempEmployeeCardStartNumber = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(21), 1);
            String tempEmployeeCardCountNumber = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(22), 1);
            String tempGiftCardStartNumber = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(23), 1);
            String tempGiftCardCountNumber = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(24), 1);
            String tempCashPadUse = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(25), 1);
            String tempAutoReceiptPrintingYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(26), 1);
            String tempCouponInfoView = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(27), 1);
            String tempOnlineOrderUseYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(28), 1);
            String tempTimeMenuUseYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(29), 1);
            String tempCouponImmediately = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(30), 1);
            String tempTimemenuchecktime = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(31), 1);
            String tempMaxPagerNum = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(32), 1);
            String tempPagerUseYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(33), 1);
            String tempPagerNumberAutoYN = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(34), 1);
            String tempProductminalertyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(35), 1);
            String tempDaystouploaddataEv = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(36), 1);
            String tempBatchincashoutyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(37), 1);
            String tempStartingcashprintyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(38), 1);
            String tempSaledatemodifyyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(39), 1);
            String tempSalehistorypageno = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(40), 1);
            String tempButtonmodifierbottomviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(41), 1);
            String tempReceipttypeonnoselect = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(42), 1);
            String tempModifierpriceviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(43), 1);
            String tempPagernumberheadertxt = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(44), 1);
            String tempDualdisplaypossible = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(45), 1);
            String tempPricedollarynSwitch = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(46), 1);
            String tempDualdpadtype = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(47), 1);
            String tempThankyoupageyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(48), 1);
            String tempUploadynonlysalesdatayn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(49), 1);
            String tempAutosaledatauploadyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(50), 1);
            String tempCloudbackupintenderbackupyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(51), 1);
            String tempItemanimationyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(52), 1);
            String tempCarddirectyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(53), 1);
            String tempModifierqtyviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(54), 1);
            String tempMssqlsyncyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(55), 1);
            String tempMssqldbip = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(56), 1);
            String tempVrkitchenprinting = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(57), 1);
            String tempAutoweborderprinting = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(58), 1);
            String tempInitcapacity = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(59), 1);
            String tempQtyaddupyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(60), 1);
            String tempTipaddsalehistory = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(61), 1);
            String tempPasswordyninmod = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(62), 1);
            String tempTableBoardType = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(63), 1);
            String tempOrdernumbershowonpickuporder = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(64), 1);
            String tempPagernumbershowonpickuporder = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(65), 1);
            String tempCardstatususe = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(66), 1);
            String tempQtyinsyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(67), 1);
            String tempCustomernumberviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(68), 1);
            String tempServerCodeUseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(69), 1);
            String tempServerPasswordUseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(70), 1);
            String tempPickuptypepopupuseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(71), 1);
            String tempPrintingcategoryyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(72), 1);
            String tempCustomer_info_here = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(73), 1);
            String tempCustomer_info_togo = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(74), 1);
            String tempCustomer_info_delivery = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(75), 1);

            String tempOpenmsgwhendeletemenu_yn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(76), 1);
            String tempOtherpayprinting_yn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(77), 1);
            String tempTipprintingwhentogo_yn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(78), 1);

            String tempBillprintpopupyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(79), 1);

            String tempCustombillsplituseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(80), 1);

            String tempPagernumofdigits = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(81), 1);

            String tempCustomeronlinecheckyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(82), 1);

            String tempStartpagernum = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(83), 1);

            String tempSaledatauploadpauseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(84), 1);

            String tempTimeviewontableyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(85), 1);

            // 10272023
            String tempItemdeletereasonyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(86), 1);

            // 01172024
            String tempTableorderuseyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(87), 1);

            String tempPushpopupopenyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(88), 1);

            GlobalMemberValues.logWrite("settingslog", "tempReceipttypeonnoselect : " + tempReceipttypeonnoselect + "\n");

            if (!GlobalMemberValues.isStrEmpty(tempSplashUse)) {
                vSplashUse = GlobalMemberValues.getIntAtString(tempSplashUse);
            }
            if (!GlobalMemberValues.isStrEmpty(tempInReverse)) {
                vInReverse = GlobalMemberValues.getIntAtString(tempInReverse);
            }
            if (!GlobalMemberValues.isStrEmpty(tempCloudUrl)) {
                vCloudUrl = tempCloudUrl;
            }
            if (!GlobalMemberValues.isStrEmpty(tempdownloadData)) {
                vDownloadData = GlobalMemberValues.getIntAtString(tempdownloadData);
            }
            if (!GlobalMemberValues.isStrEmpty(tempDatabaseBackup)) {
                vDatabaseBackup = GlobalMemberValues.getIntAtString(tempDatabaseBackup);
            }
            if (!GlobalMemberValues.isStrEmpty(tempClockinoutType)) {
                vClockinoutType = GlobalMemberValues.getIntAtString(tempClockinoutType);
            }
            if (!GlobalMemberValues.isStrEmpty(tempGmailId)) {
                vGmailId = tempGmailId;
            }
            if (!GlobalMemberValues.isStrEmpty(tempGmailPwd)) {
                vGmailPwd = tempGmailPwd;
            }
            if (!GlobalMemberValues.isStrEmpty(tempDepartmentViewYN)) {
                vDepartmentViewYN = tempDepartmentViewYN;
            } else {
                vDepartmentViewYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempSyncPointYN)) {
                vSyncPointYN = tempSyncPointYN;
            } else {
                vSyncPointYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempShowcostafterdcextra)) {
                vShowcostafterdcextra = tempShowcostafterdcextra;
            } else {
                vShowcostafterdcextra = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCardislast)) {
                vCardislast = tempCardislast;
            } else {
                vCardislast = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPointpaysavepointyn)) {
                vPointpaysavepointyn = tempPointpaysavepointyn;
            } else {
                vPointpaysavepointyn = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempTimemenuautoreload)) {
                vTimemenuautoreload = tempTimemenuautoreload;
            } else {
                vTimemenuautoreload = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPushtype)) {
                vPushType = tempPushtype;
            } else {
                vPushType = "2";
            }
            if (!GlobalMemberValues.isStrEmpty(tempDeviceKind)) {
                vDeviceKind = tempDeviceKind;
            } else {
                vDeviceKind = "";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPicktype_here)) {
                vPicktype_here = tempPicktype_here;
            } else {
                vPicktype_here = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPicktype_togo)) {
                vPicktype_togo = tempPicktype_togo;
            } else {
                vPicktype_togo = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPicktype_delivery)) {
                vPicktype_delivery = tempPicktype_delivery;
            } else {
                vPicktype_delivery = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCustomerInfoShow)) {
                vCustomerInfoShow = tempCustomerInfoShow;
            } else {
                vCustomerInfoShow = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCustomerSelectReceipt)) {
                vCustomerSelectReceipt = tempCustomerSelectReceipt;
            } else {
                vCustomerSelectReceipt = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempEmployeeCardStartNumber)) {
                vEmployeeCardStartNumber = tempEmployeeCardStartNumber;
            } else {
                vEmployeeCardStartNumber = "1";
            }
            if (!GlobalMemberValues.isStrEmpty(tempEmployeeCardCountNumber)) {
                vEmployeeCardCountNumber = tempEmployeeCardCountNumber;
            } else {
                vEmployeeCardCountNumber = "0";
            }
            if (!GlobalMemberValues.isStrEmpty(tempGiftCardStartNumber)) {
                vGiftCardStartNumber = tempGiftCardStartNumber;
            } else {
                vGiftCardStartNumber = "1";
            }
            if (!GlobalMemberValues.isStrEmpty(tempGiftCardCountNumber)) {
                vGiftCardCountNumber = tempGiftCardCountNumber;
            } else {
                vGiftCardCountNumber = "0";
            }

            if (!GlobalMemberValues.isStrEmpty(tempInitcapacity)) {
                vInitcapacity = tempInitcapacity;
            } else {
                vInitcapacity = "500";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCashPadUse)) {
                vCashPadUse = tempCashPadUse;
            } else {
                vCashPadUse = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempAutoReceiptPrintingYN)) {
                vAutoReceiptPrintingYN = tempAutoReceiptPrintingYN;
            } else {
                vAutoReceiptPrintingYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCouponInfoView)) {
                vCouponInfoView = tempCouponInfoView;
            } else {
                vCouponInfoView = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempOnlineOrderUseYN)) {
                vOnlineOrderUseYN = tempOnlineOrderUseYN;
            } else {
                vOnlineOrderUseYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempTimeMenuUseYN)) {
                vTimeMenuUseYN = tempTimeMenuUseYN;
            } else {
                vTimeMenuUseYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCouponImmediately)) {
                vCouponImmediately = tempCouponImmediately;
            } else {
                vCouponImmediately = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempTimemenuchecktime)) {
                vTimemenuchecktime = tempTimemenuchecktime;
            } else {
                vTimemenuchecktime = "10";
            }
            if (!GlobalMemberValues.isStrEmpty(tempMaxPagerNum)) {
                vMaxPagerNum = tempMaxPagerNum;
            } else {
                vMaxPagerNum = "50";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPagernumberheadertxt)) {
                vPagernumberheadertxt = tempPagernumberheadertxt;
            } else {
                vPagernumberheadertxt = "";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPagerUseYN)) {
                vPagerUseYN = tempPagerUseYN;
            } else {
                vPagerUseYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempPagerNumberAutoYN)) {
                vPagerNumberAutoYN = tempPagerNumberAutoYN;
            } else {
                vPagerNumberAutoYN = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempProductminalertyn)) {
                vProductminalertyn = tempProductminalertyn;
            } else {
                vProductminalertyn = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempDaystouploaddataEv)) {
                vDaystouploaddataEv = tempDaystouploaddataEv;
            } else {
                vDaystouploaddataEv = "10";
            }
            if (!GlobalMemberValues.isStrEmpty(tempBatchincashoutyn)) {
                vBatchincashoutyn = tempBatchincashoutyn;
            } else {
                vBatchincashoutyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempStartingcashprintyn)) {
                vStartingcashprintyn = tempStartingcashprintyn;
            } else {
                vStartingcashprintyn = "Y";
            }

            if (!GlobalMemberValues.isStrEmpty(tempSaledatemodifyyn)) {
                vSaledatemodifyyn = tempSaledatemodifyyn;
            } else {
                vSaledatemodifyyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempSalehistorypageno)) {
                vSalehistorypageno = tempSalehistorypageno;
            } else {
                vSalehistorypageno = "30";
            }

            if (!GlobalMemberValues.isStrEmpty(tempButtonmodifierbottomviewyn)) {
                vButtonmodifierbottomviewyn = tempButtonmodifierbottomviewyn;
            } else {
                vButtonmodifierbottomviewyn = "Y";
            }

            if (!GlobalMemberValues.isStrEmpty(tempReceipttypeonnoselect)) {
                vReceipttypeonnoselect = tempReceipttypeonnoselect;
            } else {
                vReceipttypeonnoselect = "A";
            }

            GlobalMemberValues.logWrite("settingslog", "vReceipttypeonnoselect : " + vReceipttypeonnoselect + "\n");

            if (!GlobalMemberValues.isStrEmpty(tempModifierpriceviewyn)) {
                vModifierpriceviewyn = tempModifierpriceviewyn;
            } else {
                vModifierpriceviewyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempDualdisplaypossible)) {
                vDualdisplaypossible = tempDualdisplaypossible;
            } else {
                vDualdisplaypossible = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempPricedollarynSwitch)) {
                vPricedollarynSwitch = tempPricedollarynSwitch;
            } else {
                vPricedollarynSwitch = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempDualdpadtype)) {
                vDualdpadtypeSwitch = tempDualdpadtype;
            } else {
                vDualdpadtypeSwitch = "W";
            }

            if (!GlobalMemberValues.isStrEmpty(tempThankyoupageyn)) {
                vThankyoupageyn = tempThankyoupageyn;
            } else {
                vThankyoupageyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempUploadynonlysalesdatayn)) {
                vUploadynonlysalesdata = tempUploadynonlysalesdatayn;
            } else {
                vUploadynonlysalesdata = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempAutosaledatauploadyn)) {
                vAutosaledatauploadyn = tempAutosaledatauploadyn;
            } else {
                vAutosaledatauploadyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCloudbackupintenderbackupyn)) {
                vCloudbackupintenderbackupyn = tempCloudbackupintenderbackupyn;
            } else {
                vCloudbackupintenderbackupyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempItemanimationyn)) {
                vItemanimationyn = tempItemanimationyn;
            } else {
                vItemanimationyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCarddirectyn)) {
                vCarddirectyn = tempCarddirectyn;
            } else {
                vCarddirectyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempVrkitchenprinting)) {
                vVrkitchenprinting = tempVrkitchenprinting;
            } else {
                vVrkitchenprinting = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempAutoweborderprinting)) {
                vAutoweborderprinting = tempAutoweborderprinting;
            } else {
                vAutoweborderprinting = "N";
            }



            if (!GlobalMemberValues.isStrEmpty(tempModifierqtyviewyn)) {
                vModifierqtyviewyn = tempModifierqtyviewyn;
            } else {
                vModifierqtyviewyn = "Y";
            }

            if (!GlobalMemberValues.isStrEmpty(tempQtyaddupyn)) {
                vQtyaddupyn = tempQtyaddupyn;
            } else {
                vQtyaddupyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempTipaddsalehistory)) {
                vTipaddsalehistory = tempTipaddsalehistory;
            } else {
                vTipaddsalehistory = "Y";
            }

            if (!GlobalMemberValues.isStrEmpty(tempMssqlsyncyn)) {
                vMssqlsyncyn = tempMssqlsyncyn;
            } else {
                vMssqlsyncyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempMssqldbip)) {
                vMssqldbip = tempMssqldbip;
            }

            if (!GlobalMemberValues.isStrEmpty(tempPasswordyninmod)) {
                vPasswordyninmod = tempPasswordyninmod;
            } else {
                vPasswordyninmod = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempTableBoardType)) {
                vTableBoardType = tempTableBoardType;
            } else {
                vTableBoardType = "2";
            }

            if (!GlobalMemberValues.isStrEmpty(tempOrdernumbershowonpickuporder)) {
                vOrdernumbershowonpickuporder = tempOrdernumbershowonpickuporder;
            } else {
                vOrdernumbershowonpickuporder = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempPagernumbershowonpickuporder)) {
                vPagernumbershowonpickuporder = tempPagernumbershowonpickuporder;
            } else {
                vPagernumbershowonpickuporder = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCardstatususe)) {
                vCardstatususe = tempCardstatususe;
            } else {
                vCardstatususe = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempQtyinsyn)) {
                vQtyinsyn = tempQtyinsyn;
            } else {
                vQtyinsyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCustomernumberviewyn)) {
                vCustomernumberviewyn = tempCustomernumberviewyn;
            } else {
                vCustomernumberviewyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempServerCodeUseyn)) {
                vServerCodeUseyn = tempServerCodeUseyn;
            } else {
                vServerCodeUseyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempServerPasswordUseyn)) {
                vServerPasswordUseyn = tempServerPasswordUseyn;
            } else {
                vServerPasswordUseyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempPickuptypepopupuseyn)) {
                vPickuptypepopupuseyn = tempPickuptypepopupuseyn;
            } else {
                vPickuptypepopupuseyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempPrintingcategoryyn)) {
                vPrintingcategoryyn = tempPrintingcategoryyn;
            } else {
                vPrintingcategoryyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCustomer_info_here)) {
                vCustomer_info_here = tempCustomer_info_here;
            } else {
                vCustomer_info_here = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCustomer_info_togo)) {
                vCustomer_info_togo = tempCustomer_info_togo;
            } else {
                vCustomer_info_togo = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempCustomer_info_delivery)) {
                vCustomer_info_delivery = tempCustomer_info_delivery;
            } else {
                vCustomer_info_delivery = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempOpenmsgwhendeletemenu_yn)) {
                vOpenmsgwhendeletemenu_yn = tempOpenmsgwhendeletemenu_yn;
            } else {
                vOpenmsgwhendeletemenu_yn = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempOtherpayprinting_yn)) {
                vOtherpayprinting_yn = tempOtherpayprinting_yn;
            } else {
                vOtherpayprinting_yn = "N";
            }
            if (!GlobalMemberValues.isStrEmpty(tempTipprintingwhentogo_yn)) {
                vTipprintingwhentogo_yn = tempTipprintingwhentogo_yn;
            } else {
                vTipprintingwhentogo_yn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempBillprintpopupyn)) {
                vBillprintpopupyn = tempBillprintpopupyn;
            } else {
                vBillprintpopupyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCustombillsplituseyn)) {
                vCustombillsplituseyn = tempCustombillsplituseyn;
            } else {
                vCustombillsplituseyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempPagernumofdigits)) {
                vPagernumofdigits = tempPagernumofdigits;
            } else {
                vPagernumofdigits = "0";
            }

            if (!GlobalMemberValues.isStrEmpty(tempCustomeronlinecheckyn)) {
                vCustomeronlinecheckyn = tempCustomeronlinecheckyn;
            } else {
                vCustomeronlinecheckyn = "Y";
            }

            if (!GlobalMemberValues.isStrEmpty(tempStartpagernum)) {
                vStartpagernum = tempStartpagernum;
            } else {
                vStartpagernum = "1";
            }

            if (!GlobalMemberValues.isStrEmpty(tempSaledatauploadpauseyn)) {
                vSaledatauploadpauseyn = tempSaledatauploadpauseyn;
            } else {
                vSaledatauploadpauseyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempTimeviewontableyn)) {
                vTimeviewontableyn = tempTimeviewontableyn;
            } else {
                vTimeviewontableyn = "N";
            }

            // 10272023
            if (!GlobalMemberValues.isStrEmpty(tempItemdeletereasonyn)) {
                vItemdeletereasonyn = tempItemdeletereasonyn;
            } else {
                vItemdeletereasonyn = "N";
            }

            // 01172024
            if (!GlobalMemberValues.isStrEmpty(tempTableorderuseyn)) {
                vTableorderuseyn = tempTableorderuseyn;
            } else {
                vTableorderuseyn = "N";
            }


            if (!GlobalMemberValues.isStrEmpty(tempPushpopupopenyn)) {
                vPushpopupopenyn = tempPushpopupopenyn;
            } else {
                vPushpopupopenyn = "N";
            }


            orgin_timeMenuAutoReload = vTimemenuautoreload;
            orgin_onlineorderuseyn = vOnlineOrderUseYN;
            orgin_timemenuchecktime = vTimemenuchecktime;

            orgin_tableorderuseyn = vTableorderuseyn;
        }

        dualdpadtypeLn = (LinearLayout)findViewById(R.id.dualdpadtypeLn);

        splashUseSwitch = (Switch)findViewById(R.id.splashUseSwitch);
        if (vSplashUse == 0) {
            splashUseSwitch.setChecked(true);
        } else {
            splashUseSwitch.setChecked(false);
        }
        splashUseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogsSave.saveLogsInDB(124);
            }
        });

        /**
         inReverseSwitch = (Switch)findViewById(R.id.inReverseSwitch);
         if (vInReverse == 0) {
         inReverseSwitch.setChecked(false);
         } else {
         inReverseSwitch.setChecked(true);
         }
         **/

        downloadDataSwitch = (Switch)findViewById(R.id.downloadDataSwitch);
        if (vDownloadData == 0) {
            downloadDataSwitch.setChecked(false);
        } else {
            downloadDataSwitch.setChecked(true);
        }
        downloadDataSwitch.setOnClickListener(logSaveListener);

        clockinoutTypeSwitch = (Switch)findViewById(R.id.clockinoutTypeSwitch);
        if (vClockinoutType == 0) {
            clockinoutTypeSwitch.setChecked(false);
        } else {
            clockinoutTypeSwitch.setChecked(true);
        }
        clockinoutTypeSwitch.setOnClickListener(logSaveListener);

        departmentViewYNSwitch = (Switch)findViewById(R.id.departmentViewYNSwitch);
        if (vDepartmentViewYN == "Y" || vDepartmentViewYN.equals("Y")) {
            departmentViewYNSwitch.setChecked(true);
        } else {
            departmentViewYNSwitch.setChecked(false);
        }

        syncPointInSelectingCustomerSwitch = (Switch)findViewById(R.id.syncPointInSelectingCustomerSwitch);
        if (vSyncPointYN == "Y" || vSyncPointYN.equals("Y")) {
            syncPointInSelectingCustomerSwitch.setChecked(true);
        } else {
            syncPointInSelectingCustomerSwitch.setChecked(false);
        }

        showCostAfterDCExtraSwitch = (Switch)findViewById(R.id.showCostAfterDCExtraSwitch);
        if (vShowcostafterdcextra == "Y" || vShowcostafterdcextra.equals("Y")) {
            showCostAfterDCExtraSwitch.setChecked(true);
        } else {
            showCostAfterDCExtraSwitch.setChecked(false);
        }

        cardIsLaslSwitch = (Switch)findViewById(R.id.cardIsLaslSwitch);
        if (vCardislast == "Y" || vCardislast.equals("Y")) {
            cardIsLaslSwitch.setChecked(true);
        } else {
            cardIsLaslSwitch.setChecked(false);
        }

        pointpaysavepointynSwitch = (Switch)findViewById(R.id.pointpaysavepointynSwitch);
        if (vPointpaysavepointyn == "Y" || vPointpaysavepointyn.equals("Y")) {
            pointpaysavepointynSwitch.setChecked(true);
        } else {
            pointpaysavepointynSwitch.setChecked(false);
        }

        // Device Name --------------------------------------------------------------------------------------------
        devicekindSinner = (Spinner)findViewById(R.id.devicekindSinner);
        mGeneralArrayListForSpinnerDeviceName = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.DEVICEGROUP.length; i++) {
            mGeneralArrayListForSpinnerDeviceName.add(GlobalMemberValues.DEVICEGROUP[i]);
        }
        mSpinnerAdapterDeviceName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerDeviceName);
        mSpinnerAdapterDeviceName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        devicekindSinner.setAdapter(mSpinnerAdapterDeviceName);
        devicekindSinner.setOnItemSelectedListener(mDevicenameSpinnerItemSelectedListener);

        int tempPNPosition = 0;
        for (int i = 0; i < GlobalMemberValues.DEVICEGROUP.length; i++) {
            if (vDeviceKind.equals(GlobalMemberValues.DEVICEGROUP[i])) {
                tempPNPosition = i;
            }
        }

        mSavedDeviceName = vDeviceKind;
        devicekindSinner.setSelection(tempPNPosition);
        // ---------------------------------------------------------------------------------------------------------

        // Table Board type -----------------------------------------------------------------------------------------
        tableboardtypeSinner = (Spinner)findViewById(R.id.tableboardtypeSinner);
        mGeneralArrayListForSpinnerTableBoardType = new ArrayList<String>();
        for (int i = 0; i < tableBoardTypeArr.length; i++) {
            mGeneralArrayListForSpinnerTableBoardType.add(tableBoardTypeArr[i]);
        }
        mSpinnerAdapterTableBoardType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerTableBoardType);
        mSpinnerAdapterTableBoardType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableboardtypeSinner.setAdapter(mSpinnerAdapterTableBoardType);
        tableboardtypeSinner.setOnItemSelectedListener(mTableBoardTypeSpinnerItemSelectedListener);

        int tempPNPosition2 = 0;
        switch (vTableBoardType) {
            case "A" : {
                tempPNPosition2 = 0;
                break;
            }
            case "1" : {
                tempPNPosition2 = 1;
                break;
            }
            case "2" : {
                tempPNPosition2 = 2;
                break;
            }
            case "3" : {
                tempPNPosition2 = 3;
                break;
            }
        }

        mSavedTableBoardType = vTableBoardType;
        tableboardtypeSinner.setSelection(tempPNPosition2);
        // ---------------------------------------------------------------------------------------------------------

        //
        customerinfoviewSwitch_type_ln = (LinearLayout)findViewById(R.id.customerinfoviewSwitch_type_ln);
        customerinfoviewSwitch_type_here = (CheckBox)findViewById(R.id.customerinfoviewSwitch_type_here);
        if (vCustomer_info_here == "Y" || vCustomer_info_here.equals("Y")) {
            customerinfoviewSwitch_type_here.setChecked(true);
        } else {
            customerinfoviewSwitch_type_here.setChecked(false);
        }
        customerinfoviewSwitch_type_togo = (CheckBox)findViewById(R.id.customerinfoviewSwitch_type_togo);
        if (vCustomer_info_togo == "Y" || vCustomer_info_togo.equals("Y")) {
            customerinfoviewSwitch_type_togo.setChecked(true);
        } else {
            customerinfoviewSwitch_type_togo.setChecked(false);
        }
        customerinfoviewSwitch_type_delivery = (CheckBox)findViewById(R.id.customerinfoviewSwitch_type_delivery);
        if (vCustomer_info_delivery == "Y" || vCustomer_info_delivery.equals("Y")) {
            customerinfoviewSwitch_type_delivery.setChecked(true);
        } else {
            customerinfoviewSwitch_type_delivery.setChecked(false);
        }

        customerinfoviewSwitch = (Switch)findViewById(R.id.customerinfoviewSwitch);
        customerinfoviewSwitch.setOnCheckedChangeListener(customerinfoviewSwitch_listener);
        if (vCustomerInfoShow == "Y" || vCustomerInfoShow.equals("Y")) {
            customerinfoviewSwitch.setChecked(true);
            customerinfoviewSwitch_type_ln.setVisibility(View.VISIBLE);
        } else {
            customerinfoviewSwitch.setChecked(false);
            customerinfoviewSwitch_type_ln.setVisibility(View.INVISIBLE);
        }


        customerselectreceiptSwitch = (Switch)findViewById(R.id.customerselectreceiptSwitch);
        if (vCustomerSelectReceipt == "Y" || vCustomerSelectReceipt.equals("Y")) {
            customerselectreceiptSwitch.setChecked(true);
        } else {
            customerselectreceiptSwitch.setChecked(false);
        }
        customerselectreceiptSwitch.setOnCheckedChangeListener(setCheckCustomerSelectReceipt);

        cashpaduseSwitch = (Switch)findViewById(R.id.cashpaduseSwitch);
        if (vCashPadUse == "Y" || vCashPadUse.equals("Y")) {
            cashpaduseSwitch.setChecked(true);
        } else {
            cashpaduseSwitch.setChecked(false);
        }

        autoreceiptprintingynSwitch = (Switch)findViewById(R.id.autoreceiptprintingynSwitch);
        if (vAutoReceiptPrintingYN == "Y" || vAutoReceiptPrintingYN.equals("Y")) {
            autoreceiptprintingynSwitch.setChecked(true);
        } else {
            autoreceiptprintingynSwitch.setChecked(false);
        }

        couponinfoviewSwitch = (Switch)findViewById(R.id.couponinfoviewSwitch);
        if (vCouponInfoView == "Y" || vCouponInfoView.equals("Y")) {
            couponinfoviewSwitch.setChecked(true);
        } else {
            couponinfoviewSwitch.setChecked(false);
        }

        onlineorderuseynSwitch = (Switch)findViewById(R.id.onlineorderuseynSwitch);
        if (vOnlineOrderUseYN == "Y" || vOnlineOrderUseYN.equals("Y")) {
            onlineorderuseynSwitch.setChecked(true);
        } else {
            onlineorderuseynSwitch.setChecked(false);
        }

        timemenuactiveSwitch = (Switch)findViewById(R.id.timemenuactiveSwitch);
        if (vTimeMenuUseYN == "Y" || vTimeMenuUseYN.equals("Y")) {
            timemenuactiveSwitch.setChecked(true);
        } else {
            timemenuactiveSwitch.setChecked(false);
        }

        couponimmediatelySwitch = (Switch)findViewById(R.id.couponimmediatelySwitch);
        if (vCouponImmediately == "Y" || vCouponImmediately.equals("Y")) {
            couponimmediatelySwitch.setChecked(true);
        } else {
            couponimmediatelySwitch.setChecked(false);
        }

        pageruseynSwitch = (Switch)findViewById(R.id.pageruseynSwitch);
        if (vPagerUseYN == "Y" || vPagerUseYN.equals("Y")) {
            pageruseynSwitch.setChecked(true);
        } else {
            pageruseynSwitch.setChecked(false);
        }

        pagernumberautoynSwitch = (Switch)findViewById(R.id.pagernumberautoynSwitch);
        if (vPagerNumberAutoYN == "Y" || vPagerNumberAutoYN.equals("Y")) {
            pagernumberautoynSwitch.setChecked(true);
        } else {
            pagernumberautoynSwitch.setChecked(false);
        }

        productminalertynSwitch = (Switch)findViewById(R.id.productminalertynSwitch);
        if (vProductminalertyn == "Y" || vProductminalertyn.equals("Y")) {
            productminalertynSwitch.setChecked(true);
        } else {
            productminalertynSwitch.setChecked(false);
        }
        productminalertynSwitch.setOnClickListener(logSaveListener);

        batchincashoutynSwitch = (Switch)findViewById(R.id.batchincashoutynSwitch);
        if (vBatchincashoutyn == "Y" || vBatchincashoutyn.equals("Y")) {
            batchincashoutynSwitch.setChecked(true);
        } else {
            batchincashoutynSwitch.setChecked(false);
        }
        batchincashoutynSwitch.setOnClickListener(logSaveListener);

        startingcashprintynSwitch = (Switch)findViewById(R.id.startingcashprintynSwitch);
        if (vStartingcashprintyn == "Y" || vStartingcashprintyn.equals("Y")) {
            startingcashprintynSwitch.setChecked(true);
        } else {
            startingcashprintynSwitch.setChecked(false);
        }
        startingcashprintynSwitch.setOnClickListener(logSaveListener);

        saledatemodifyynSwitch = (Switch)findViewById(R.id.saledatemodifyynSwitch);
        if (vSaledatemodifyyn == "Y" || vSaledatemodifyyn.equals("Y")) {
            saledatemodifyynSwitch.setChecked(true);
        } else {
            saledatemodifyynSwitch.setChecked(false);
        }
        saledatemodifyynSwitch.setOnClickListener(logSaveListener);

        buttonmodifierbottomviewynSwith = (Switch)findViewById(R.id.buttonmodifierbottomviewynSwith);
        if (vButtonmodifierbottomviewyn == "Y" || vButtonmodifierbottomviewyn.equals("Y")) {
            buttonmodifierbottomviewynSwith.setChecked(true);
        } else {
            buttonmodifierbottomviewynSwith.setChecked(false);
        }
        buttonmodifierbottomviewynSwith.setOnClickListener(logSaveListener);

        modifierpriceviewynSwith = (Switch)findViewById(R.id.modifierpriceviewynSwith);
        if (vModifierpriceviewyn == "Y" || vModifierpriceviewyn.equals("Y")) {
            modifierpriceviewynSwith.setChecked(true);
        } else {
            modifierpriceviewynSwith.setChecked(false);
        }
        modifierpriceviewynSwith.setOnClickListener(logSaveListener);

        dualdprestartBtn = (Button) findViewById(R.id.dualdprestartBtn);
        dualdprestartBtn.setOnClickListener(mButtonClick);
        dualdprestartBtn.setTextSize(dualdprestartBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        dualdisplaypossibleSwitch = (Switch)findViewById(R.id.dualdisplaypossibleSwitch);
        if (vDualdisplaypossible == "Y" || vDualdisplaypossible.equals("Y")) {
            dualdisplaypossibleSwitch.setChecked(true);
            dualdpadtypeLn.setVisibility(View.VISIBLE);
            dualdprestartBtn.setVisibility(View.VISIBLE);
        } else {
            dualdisplaypossibleSwitch.setChecked(false);
            dualdpadtypeLn.setVisibility(View.GONE);
            dualdprestartBtn.setVisibility(View.GONE);
        }
        dualdisplaypossibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GlobalMemberValues.displayDialog(context, "Warning", "If dual display is not supported\nthe system error may occur.", "Close");
                    dualdpadtypeLn.setVisibility(View.VISIBLE);
                    dualdprestartBtn.setVisibility(View.VISIBLE);
                } else {
                    dualdpadtypeLn.setVisibility(View.GONE);
                    dualdprestartBtn.setVisibility(View.GONE);
                }
            }
        });

        pricedollarynSwitch = (Switch)findViewById(R.id.pricedollarynSwitch);
        if (vPricedollarynSwitch == "Y" || vPricedollarynSwitch.equals("Y")) {
            pricedollarynSwitch.setChecked(true);
        } else {
            pricedollarynSwitch.setChecked(false);
        }
        pricedollarynSwitch.setOnClickListener(logSaveListener);

        thankyoupageynSwith = (Switch)findViewById(R.id.thankyoupageynSwith);
        if (vThankyoupageyn == "Y" || vThankyoupageyn.equals("Y")) {
            thankyoupageynSwith.setChecked(true);
        } else {
            thankyoupageynSwith.setChecked(false);
        }
        thankyoupageynSwith.setOnClickListener(logSaveListener);

        uploadynonlysalesdataSwith = (Switch)findViewById(R.id.uploadynonlysalesdataSwith);
        if (vUploadynonlysalesdata == "Y" || vUploadynonlysalesdata.equals("Y")) {
            uploadynonlysalesdataSwith.setChecked(true);
        } else {
            uploadynonlysalesdataSwith.setChecked(false);
        }

        cloudbackupintenderbackupynSwitch = (Switch)findViewById(R.id.cloudbackupintenderbackupynSwitch);
        if (vAutosaledatauploadyn == "Y" || vAutosaledatauploadyn.equals("Y")) {
            cloudbackupintenderbackupynSwitch.setChecked(true);
        } else {
            cloudbackupintenderbackupynSwitch.setChecked(false);
        }
        cloudbackupintenderbackupynSwitch.setOnClickListener(logSaveListener);

        autosaledatauploadynSwitch = (Switch)findViewById(R.id.autosaledatauploadynSwitch);
        if (vCloudbackupintenderbackupyn == "Y" || vCloudbackupintenderbackupyn.equals("Y")) {
            autosaledatauploadynSwitch.setChecked(true);
        } else {
            autosaledatauploadynSwitch.setChecked(false);
        }

        itemanimationynSwitch = (Switch)findViewById(R.id.itemanimationynSwitch);
        if (vItemanimationyn == "Y" || vItemanimationyn.equals("Y")) {
            itemanimationynSwitch.setChecked(true);
        } else {
            itemanimationynSwitch.setChecked(false);
        }

        carddirectynSwitch = (Switch)findViewById(R.id.carddirectynSwitch);
        if (vCarddirectyn == "Y" || vCarddirectyn.equals("Y")) {
            carddirectynSwitch.setChecked(true);
        } else {
            carddirectynSwitch.setChecked(false);
        }

        vrkitchenprintingSwitch = (Switch)findViewById(R.id.vrkitchenprintingSwitch);
        if (vVrkitchenprinting == "Y" || vVrkitchenprinting.equals("Y")) {
            vrkitchenprintingSwitch.setChecked(true);
        } else {
            vrkitchenprintingSwitch.setChecked(false);
        }

        autoweborderprintingSwitch = (Switch)findViewById(R.id.autoweborderprintingSwitch);
        if (vAutoweborderprinting == "Y" || vAutoweborderprinting.equals("Y")) {
            autoweborderprintingSwitch.setChecked(true);
        } else {
            autoweborderprintingSwitch.setChecked(false);
        }



        modifierqtyviewynSwith = (Switch)findViewById(R.id.modifierqtyviewynSwith);
        if (vModifierqtyviewyn == "Y" || vModifierqtyviewyn.equals("Y")) {
            modifierqtyviewynSwith.setChecked(true);
        } else {
            modifierqtyviewynSwith.setChecked(false);
        }
        modifierqtyviewynSwith.setOnClickListener(logSaveListener);

        // qty add up option
        qtyaddupynSwitch = (Switch)findViewById(R.id.qtyaddupynswitch);
        if (vQtyaddupyn == "Y" || vQtyaddupyn.equals("Y")) {
            qtyaddupynSwitch.setChecked(true);
        } else {
            qtyaddupynSwitch.setChecked(false);
        }

        // qty add up option
        tipsalehistoryaddupynSwitch = (Switch)findViewById(R.id.tipsalehistoryaddupynSwitch);
        if (vTipaddsalehistory == "Y" || vTipaddsalehistory.equals("Y")) {
            tipsalehistoryaddupynSwitch.setChecked(true);
        } else {
            tipsalehistoryaddupynSwitch.setChecked(false);
        }

        // 다른 직원 메뉴 변경시 비밀번호 사용여부
        passwordyninmodSwitch = (Switch)findViewById(R.id.passwordyninmodSwitch);
        if (vPasswordyninmod == "Y" || vPasswordyninmod.equals("Y")) {
            passwordyninmodSwitch.setChecked(true);
        } else {
            passwordyninmodSwitch.setChecked(false);
        }

        // 교환권에 order no. 표시여부
        ordernumbershowonpickuporderSwitch = (Switch)findViewById(R.id.ordernumbershowonpickuporderSwitch);
        if (vOrdernumbershowonpickuporder == "Y" || vOrdernumbershowonpickuporder.equals("Y")) {
            ordernumbershowonpickuporderSwitch.setChecked(true);
        } else {
            ordernumbershowonpickuporderSwitch.setChecked(false);
        }

        // 교환권에 order no. 표시여부
        pagernumbershowonpickuporderSwitch = (Switch)findViewById(R.id.pagernumbershowonpickuporderSwitch);
        if (vPagernumbershowonpickuporder == "Y" || vPagernumbershowonpickuporder.equals("Y")) {
            pagernumbershowonpickuporderSwitch.setChecked(true);
        } else {
            pagernumbershowonpickuporderSwitch.setChecked(false);
        }

        // Cart Status Save 여부
        cardstatususeSwitch = (Switch)findViewById(R.id.cardstatususeSwitch);
        if (vCardstatususe == "Y" || vCardstatususe.equals("Y")) {
            cardstatususeSwitch.setChecked(true);
        } else {
            cardstatususeSwitch.setChecked(false);
        }

        // 수기로 Quantity 입력가능 여부
        qtyinsynSwitch = (Switch)findViewById(R.id.qtyinsynSwitch);
        if (vQtyinsyn == "Y" || vQtyinsyn.equals("Y")) {
            qtyinsynSwitch.setChecked(true);
        } else {
            qtyinsynSwitch.setChecked(false);
        }

        // Customer Numbers 창을 보여줄지 여부
        customernumberviewynSwitch = (Switch)findViewById(R.id.customernumberviewynSwitch);
        if (vCustomernumberviewyn == "Y" || vCustomernumberviewyn.equals("Y")) {
            customernumberviewynSwitch.setChecked(true);
        } else {
            customernumberviewynSwitch.setChecked(false);
        }

        // 퀵 세일시 pick up 타입 팝업창 사용여부
        pickuptypepopupuseynSwitch = (Switch)findViewById(R.id.pickuptypepopupuseynSwitch);
        if (vPickuptypepopupuseyn == "Y" || vPickuptypepopupuseyn.equals("Y")) {
            pickuptypepopupuseynSwitch.setChecked(true);
        } else {
            pickuptypepopupuseynSwitch.setChecked(false);
        }
        pickuptypepopupuseynSwitch.setOnClickListener(logSaveListener);

        // 프린팅시 카테고리명 표시여부
        printingcategoryynSwitch = (Switch)findViewById(R.id.printingcategoryynSwitch);
        if (vPrintingcategoryyn == "Y" || vPrintingcategoryyn.equals("Y")) {
            printingcategoryynSwitch.setChecked(true);
        } else {
            printingcategoryynSwitch.setChecked(false);
        }
        printingcategoryynSwitch.setOnClickListener(logSaveListener);

        // bill print 클릭시 팝업창 오픈 여부
        billprintpopupynSwitch = (Switch)findViewById(R.id.billprintpopupynSwitch);
        if (vBillprintpopupyn == "Y" || vBillprintpopupyn.equals("Y")) {
            billprintpopupynSwitch.setChecked(true);
        } else {
            billprintpopupynSwitch.setChecked(false);
        }
        billprintpopupynSwitch.setOnClickListener(logSaveListener);

        // custom bill split 사용여부
        custombillsplituseynSwitch = (Switch)findViewById(R.id.custombillsplituseynSwitch);
        if (vCustombillsplituseyn == "Y" || vCustombillsplituseyn.equals("Y")) {
            custombillsplituseynSwitch.setChecked(true);
        } else {
            custombillsplituseynSwitch.setChecked(false);
        }
        custombillsplituseynSwitch.setOnClickListener(logSaveListener);


        // 메뉴 삭제시 키친프린팅을 하겠냐는 메시지 오픈 여부
        openmsgwhendeletemenu_ynSwitch = (Switch)findViewById(R.id.openmsgwhendeletemenu_ynSwitch);
        if (vOpenmsgwhendeletemenu_yn == "Y" || vOpenmsgwhendeletemenu_yn.equals("Y")) {
            openmsgwhendeletemenu_ynSwitch.setChecked(true);
        } else {
            openmsgwhendeletemenu_ynSwitch.setChecked(false);
        }
        openmsgwhendeletemenu_ynSwitch.setOnClickListener(logSaveListener);
        // OTHER PAYMENT (GRUB HUB, DOOR DASH, UBER) 일 때 영수증 프린팅 여부
        otherpayprinting_ynSwitch = (Switch)findViewById(R.id.otherpayprinting_ynSwitch);
        if (vOtherpayprinting_yn == "Y" || vOtherpayprinting_yn.equals("Y")) {
            otherpayprinting_ynSwitch.setChecked(true);
        } else {
            otherpayprinting_ynSwitch.setChecked(false);
        }
        otherpayprinting_ynSwitch.setOnClickListener(logSaveListener);
        // to go 주문일 때 tip 관련된 부분을 영수증에 표기할 지 여부
        tipprintingwhentogo_ynSwitch = (Switch)findViewById(R.id.tipprintingwhentogo_ynSwitch);
        if (vTipprintingwhentogo_yn == "Y" || vTipprintingwhentogo_yn.equals("Y")) {
            tipprintingwhentogo_ynSwitch.setChecked(true);
        } else {
            tipprintingwhentogo_ynSwitch.setChecked(false);
        }
        tipprintingwhentogo_ynSwitch.setOnClickListener(logSaveListener);

        customeronlinecheckynSwitch = (Switch)findViewById(R.id.customeronlinecheckynSwitch);
        if (vCustomeronlinecheckyn == "Y" || vCustomeronlinecheckyn.equals("Y")) {
            customeronlinecheckynSwitch.setChecked(true);
        } else {
            customeronlinecheckynSwitch.setChecked(false);
        }

        saledatauploadpauseynSwitch = (Switch)findViewById(R.id.saledatauploadpauseynSwitch);
        if (vSaledatauploadpauseyn == "Y" || vSaledatauploadpauseyn.equals("Y")){
            saledatauploadpauseynSwitch.setChecked(true);
        }else{
            saledatauploadpauseynSwitch.setChecked(false);
        }

        timeviewontableynSwitch = (Switch)findViewById(R.id.timeviewontableynSwitch);
        if (vTimeviewontableyn == "Y" || vTimeviewontableyn.equals("Y")){
            timeviewontableynSwitch.setChecked(true);
        }else{
            timeviewontableynSwitch.setChecked(false);
        }


        // 10272023
        itemdeletereasonynSwitch = (Switch)findViewById(R.id.itemdeletereasonynSwitch);
        if (vItemdeletereasonyn == "Y" || vItemdeletereasonyn.equals("Y")){
            itemdeletereasonynSwitch.setChecked(true);
        }else{
            itemdeletereasonynSwitch.setChecked(false);
        }

        // 01172024
        tableorderuseynSwitch = (Switch)findViewById(R.id.tableorderuseynSwitch);
        if (vTableorderuseyn == "Y" || vTableorderuseyn.equals("Y")){
            tableorderuseynSwitch.setChecked(true);
        }else{
            tableorderuseynSwitch.setChecked(false);
        }


        pushpopupopenynSwitch = (Switch)findViewById(R.id.pushpopupopenynSwitch);
        if (vPushpopupopenyn == "Y" || vPushpopupopenyn.equals("Y")){
            pushpopupopenynSwitch.setChecked(true);
        }else{
            pushpopupopenynSwitch.setChecked(false);
        }


        dualdpadlocalpathBtn = (Button) findViewById(R.id.dualdpadlocalpathBtn);
        dualdpadlocalpathBtn.setOnClickListener(mButtonClick);
        dualdpadlocalpathBtn.setTextSize(dualdpadlocalpathBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        dualdpadtypeSwitch = (Switch)findViewById(R.id.dualdpadtypeSwitch);
        if (vDualdpadtypeSwitch == "W" || vDualdpadtypeSwitch.equals("W")) {
            dualdpadtypeSwitch.setChecked(true);
            dualdpadlocalpathBtn.setVisibility(View.GONE);
        } else {
            dualdpadtypeSwitch.setChecked(false);
            dualdpadlocalpathBtn.setVisibility(View.VISIBLE);
        }
        dualdpadtypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dualdpadlocalpathBtn.setVisibility(View.GONE);
                } else {
                    dualdpadlocalpathBtn.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_setting_receipt_setting = (Button)findViewById(R.id.btn_setting_receipt_setting);
        btn_setting_receipt_setting.setOnClickListener(mButtonClick);

        cloudUrlEditText = (EditText)findViewById(R.id.cloudUrlEditText);
        cloudUrlEditText.setText(vCloudUrl);
        cloudUrlEditText.setTextSize(cloudUrlEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        gmailIdEditText = (EditText)findViewById(R.id.gmailIdEditText);
        gmailIdEditText.setText(vGmailId);
        gmailIdEditText.setTextSize(gmailIdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        gmailIdEditText.setOnClickListener(logSaveListener);

        gmailPwdEditText = (EditText)findViewById(R.id.gmailPwdEditText);
        gmailPwdEditText.setText(vGmailPwd);
        gmailPwdEditText.setTextSize(gmailPwdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        gmailPwdEditText.setOnClickListener(logSaveListener);

        timemenuchecktimeEditText = (EditText)findViewById(R.id.timemenuchecktimeEditText);
        timemenuchecktimeEditText.setText(vTimemenuchecktime);
        timemenuchecktimeEditText.setTextSize(timemenuchecktimeEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        maxpagernumEditText = (EditText)findViewById(R.id.maxpagernumEditText);
        maxpagernumEditText.setText(vMaxPagerNum);
        maxpagernumEditText.setTextSize(maxpagernumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pagernumberheadertxtEditText = (EditText)findViewById(R.id.pagernumberheadertxtEditText);
        pagernumberheadertxtEditText.setText(vPagernumberheadertxt);
        pagernumberheadertxtEditText.setTextSize(pagernumberheadertxtEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        pagernumberheadertxtEditText.setOnClickListener(logSaveListener);

        daystouploaddataEv = (EditText)findViewById(R.id.daystouploaddataEv);
        daystouploaddataEv.setText(vDaystouploaddataEv);
        daystouploaddataEv.setTextSize(daystouploaddataEv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        daystouploaddataEv.setOnClickListener(logSaveListener);

        salehistorypagenoEv = (EditText)findViewById(R.id.salehistorypagenoEv);
        salehistorypagenoEv.setText(vSalehistorypageno);
        salehistorypagenoEv.setTextSize(salehistorypagenoEv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        salehistorypagenoEv.setOnClickListener(logSaveListener);

        // Employee / Gift Card Setting --------------------------------------------------------------------------------------
        employeecardstartnumEditText = (EditText)findViewById(R.id.employeecardstartnumEditText);
        employeecardstartnumEditText.setText(vEmployeeCardStartNumber);
        employeecardstartnumEditText.setTextSize(employeecardstartnumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        employeecardstartnumEditText.setOnClickListener(logSaveListener);

        employeecardcountnumEditText = (EditText)findViewById(R.id.employeecardcountnumEditText);
        employeecardcountnumEditText.setText(vEmployeeCardCountNumber);
        employeecardcountnumEditText.setTextSize(employeecardcountnumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        employeecardcountnumEditText.setOnClickListener(logSaveListener);

        giftcardstartnumEditText = (EditText)findViewById(R.id.giftcardstartnumEditText);
        giftcardstartnumEditText.setText(vGiftCardStartNumber);
        giftcardstartnumEditText.setTextSize(giftcardstartnumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        giftcardstartnumEditText.setOnClickListener(logSaveListener);

        giftcardcountnumEditText = (EditText)findViewById(R.id.giftcardcountnumEditText);
        giftcardcountnumEditText.setText(vGiftCardCountNumber);
        giftcardcountnumEditText.setTextSize(giftcardcountnumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        giftcardcountnumEditText.setOnClickListener(logSaveListener);

        initcapacityEditText = (EditText)findViewById(R.id.initcapacityEditText);
        initcapacityEditText.setText(vInitcapacity);
        initcapacityEditText.setTextSize(initcapacityEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pagernumofdigitsEditText = (EditText)findViewById(R.id.pagernumofdigitsEditText);
        pagernumofdigitsEditText.setText(vPagernumofdigits);
        pagernumofdigitsEditText.setTextSize(pagernumofdigitsEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());



        // Forward / Reverse -------------------------------------------------------------------------------------------------
        inReverseRadioGroup = (RadioGroup)findViewById(R.id.inReverseRadioGroup);
        inReverseRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        sensorRadioButton = (RadioButton)findViewById(R.id.sensorRadioButton);
        sensorRadioButton.setTextSize(sensorRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        forwardRadioButton = (RadioButton)findViewById(R.id.forwardRadioButton);
        forwardRadioButton.setTextSize(forwardRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        reverseRadioButton = (RadioButton)findViewById(R.id.reverseRadioButton);
        reverseRadioButton.setTextSize(reverseRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vInReverse) {
            case 0 : {
                forwardRadioButton.setChecked(true);
                break;
            }
            case 1 : {
                reverseRadioButton.setChecked(true);
                break;
            }
            case 2 : {
                sensorRadioButton.setChecked(true);
                break;
            }
        }
        // -------------------------------------------------------------------------------------------------------------------


        // Database Backup -------------------------------------------------------------------------------------------------
        databaseBackupRadioGroup = (RadioGroup)findViewById(R.id.databaseBackupRadioGroup);
        databaseBackupRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        databaseBackupNoRadioButton = (RadioButton)findViewById(R.id.databaseBackupNoRadioButton);
        databaseBackupNoRadioButton.setTextSize(databaseBackupNoRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        databaseBackupInTenderRadioButton = (RadioButton)findViewById(R.id.databaseBackupInTenderRadioButton);
        databaseBackupInTenderRadioButton.setTextSize(databaseBackupInTenderRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        databaseBackupInClosingAppRadioButton = (RadioButton)findViewById(R.id.databaseBackupInClosingAppRadioButton);
        databaseBackupInClosingAppRadioButton.setTextSize(databaseBackupInClosingAppRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vDatabaseBackup) {
            case 0 : {
                databaseBackupNoRadioButton.setChecked(true);
                break;
            }
            case 1 : {
                databaseBackupInTenderRadioButton.setChecked(true);
                break;
            }
            case 2 : {
                databaseBackupInClosingAppRadioButton.setChecked(true);
                break;
            }
        }
        // -------------------------------------------------------------------------------------------------------------------

        // Push Type ---------------------------------------------------------------------------------------------------------
        pushtypeRadioGroup = (RadioGroup)findViewById(R.id.pushtypeRadioGroup);
        pushtypeRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        pushtypeNoRadioButton = (RadioButton)findViewById(R.id.pushtypeNoRadioButton);
        pushtypeNoRadioButton.setTextSize(pushtypeNoRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pushtypeGoogleRadioButton = (RadioButton)findViewById(R.id.pushtypeGoogleRadioButton);
        pushtypeGoogleRadioButton.setTextSize(pushtypeGoogleRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pushtypeCloudSystemRadioButton = (RadioButton)findViewById(R.id.pushtypeCloudSystemRadioButton);
        pushtypeCloudSystemRadioButton.setTextSize(pushtypeCloudSystemRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vPushType) {
            case "0" : {
                pushtypeNoRadioButton.setChecked(true);
                break;
            }
            case "1" : {
                pushtypeGoogleRadioButton.setChecked(true);
                break;
            }
            case "2" : {
                pushtypeCloudSystemRadioButton.setChecked(true);
                break;
            }
        }
        // -------------------------------------------------------------------------------------------------------------------

        // Time Menu Auto Reload ---------------------------------------------------------------------------------------------
        timemenuopentypeRadioGroup = (RadioGroup)findViewById(R.id.timemenuopentypeRadioGroup);
        timemenuopentypeRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        timemenuopentypeNoRadioButton = (RadioButton)findViewById(R.id.timemenuopentypeNoRadioButton);
        timemenuopentypeNoRadioButton.setTextSize(timemenuopentypeNoRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        timemenuopentypePopupOpenRadioButton = (RadioButton)findViewById(R.id.timemenuopentypePopupOpenRadioButton);
        timemenuopentypePopupOpenRadioButton.setTextSize(timemenuopentypePopupOpenRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        timemenuopentypeAutoRadioButton = (RadioButton)findViewById(R.id.timemenuopentypeAutoRadioButton);
        timemenuopentypeAutoRadioButton.setTextSize(timemenuopentypeAutoRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vTimemenuautoreload) {
            case "N" : {
                timemenuopentypeNoRadioButton.setChecked(true);
                break;
            }
            case "Y" : {
                timemenuopentypePopupOpenRadioButton.setChecked(true);
                break;
            }
            case "A" : {
                timemenuopentypeAutoRadioButton.setChecked(true);
                break;
            }
        }
        // -------------------------------------------------------------------------------------------------------------------

        // mssql database sync. -----------------------------------------------------------------------------------------------
        mssqlinsatdownloadRadioGroup = (RadioGroup)findViewById(R.id.mssqlinsatdownloadRadioGroup);
        mssqlinsatdownloadRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        yesRadioButton = (RadioButton)findViewById(R.id.yesRadioButton);
        yesRadioButton.setTextSize(yesRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        noRadioButton = (RadioButton)findViewById(R.id.noRadioButton);
        noRadioButton.setTextSize(noRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vMssqlsyncyn) {
            case "N" : {
                noRadioButton.setChecked(true);
                break;
            }
            case "Y" : {
                yesRadioButton.setChecked(true);
                break;
            }
        }
        // -------------------------------------------------------------------------------------------------------------------

        startpagernumEditText = (EditText)findViewById(R.id.startpagernumEditText);
        startpagernumEditText.setText(vStartpagernum);
        startpagernumEditText.setTextSize(startpagernumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());



        mssqldbipTextView1 = (EditText)findViewById(R.id.mssqldbipTextView1);
        mssqldbipTextView1.setTextSize(mssqldbipTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mssqldbipTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogsSave.saveLogsInDB(123);
            }
        });


        GlobalMemberValues.logWrite("mssqlsyslog", "vMssqldbip1 : " + vMssqldbip + "\n");

        if (GlobalMemberValues.isStrEmpty(vMssqldbip)) {
            vMssqldbip = GlobalMemberValues.mssql_ip;
        }

        GlobalMemberValues.logWrite("mssqlsyslog", "vMssqldbip2 : " + vMssqldbip + "\n");

        if (!GlobalMemberValues.isStrEmpty(vMssqldbip)) {
//            String[] vIps = new String[4];
//            for (int i = 0; i < 4; i++) {
//                vIps[i] = "";
//            }
//
//            String[] vMssqldbip_spt = vMssqldbip.split("[.]");
//            for (int i = 0; i < vMssqldbip_spt.length; i++) {
//                vIps[i] = vMssqldbip_spt[i];
//
//                GlobalMemberValues.logWrite("mssqlsyslog", "vMssqldbip_spt[i] : " + vMssqldbip_spt[i] + "\n");
//                GlobalMemberValues.logWrite("mssqlsyslog", "vIps[i] : " + vIps[i] + "\n");
//            }

            mssqldbipTextView1.setText(vMssqldbip);
        }

        deleteSalesButton = (Button) findViewById(R.id.deleteSalesButton);
        deleteSalesButton.setOnClickListener(mButtonClick);
        //deleteSalesButton.setOnLongClickListener(mButtonLongClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            deleteSalesButton.setText("");
            deleteSalesButton.setBackgroundResource(R.drawable.ab_imagebutton_system_init);
        } else {
            deleteSalesButton.setTextSize(
                    deleteSalesButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        systemResetButton = (Button)findViewById(R.id.systemResetButton);
        systemResetButton.setOnClickListener(mButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            systemResetButton.setText("");
            systemResetButton.setBackgroundResource(R.drawable.ab_imagebutton_system_reset);
        } else {
            systemResetButton.setTextSize(
                    systemResetButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saveSettingsSystemButton = (Button)findViewById(R.id.saveSettingsSystemButton);
        saveSettingsSystemButton.setOnClickListener(mButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saveSettingsSystemButton.setText("");
            saveSettingsSystemButton.setBackgroundResource(R.drawable.ab_imagebutton_settings_enter);
        } else {
            saveSettingsSystemButton.setTextSize(
                    saveSettingsSystemButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }


        serverCode_useSwitch = (Switch)findViewById(R.id.serverCode_useSwitch);
        if (vServerCodeUseyn == "Y" || vServerCodeUseyn.equals("Y")) {
            serverCode_useSwitch.setChecked(true);
        } else {
            serverCode_useSwitch.setChecked(false);
        }
        serverCode_useSwitch.setOnClickListener(logSaveListener);

        serverPassword_useSwitch = (Switch)findViewById(R.id.serverPassword_useSwitch);
        if (vServerPasswordUseyn == "Y" || vServerPasswordUseyn.equals("Y")) {
            serverPassword_useSwitch.setChecked(true);
        } else {
            serverPassword_useSwitch.setChecked(false);
        }
        serverPassword_useSwitch.setOnClickListener(logSaveListener);


        syncrealtimebtn = (Button) findViewById(R.id.syncrealtimebtn);
        syncrealtimebtn.setOnClickListener(mButtonClick);
        syncrealtimebtn.setTextSize(syncrealtimebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView1 = (TextView)findViewById(R.id.settingsSystemTitleTextView1);
        settingsSystemTitleTextView1.setTextSize(settingsSystemTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView2 = (TextView)findViewById(R.id.settingsSystemTitleTextView2);
        settingsSystemTitleTextView2.setTextSize(settingsSystemTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView3 = (TextView)findViewById(R.id.settingsSystemTitleTextView3);
        settingsSystemTitleTextView3.setTextSize(settingsSystemTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView4 = (TextView)findViewById(R.id.settingsSystemTitleTextView4);
        settingsSystemTitleTextView4.setTextSize(settingsSystemTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView5 = (TextView)findViewById(R.id.settingsSystemTitleTextView5);
        settingsSystemTitleTextView5.setTextSize(settingsSystemTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView6 = (TextView)findViewById(R.id.settingsSystemTitleTextView6);
        settingsSystemTitleTextView6.setTextSize(settingsSystemTitleTextView6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView7 = (TextView)findViewById(R.id.settingsSystemTitleTextView7);
        settingsSystemTitleTextView7.setTextSize(settingsSystemTitleTextView7.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView8 = (TextView)findViewById(R.id.settingsSystemTitleTextView8);
        settingsSystemTitleTextView8.setTextSize(settingsSystemTitleTextView8.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView9 = (TextView)findViewById(R.id.settingsSystemTitleTextView9);
        settingsSystemTitleTextView9.setTextSize(settingsSystemTitleTextView9.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView10 = (TextView)findViewById(R.id.settingsSystemTitleTextView10);
        settingsSystemTitleTextView10.setTextSize(settingsSystemTitleTextView10.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView11 = (TextView)findViewById(R.id.settingsSystemTitleTextView11);
        settingsSystemTitleTextView11.setTextSize(settingsSystemTitleTextView11.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView12 = (TextView)findViewById(R.id.settingsSystemTitleTextView12);
        settingsSystemTitleTextView12.setTextSize(settingsSystemTitleTextView12.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView13 = (TextView)findViewById(R.id.settingsSystemTitleTextView13);
        settingsSystemTitleTextView13.setTextSize(settingsSystemTitleTextView13.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView14 = (TextView)findViewById(R.id.settingsSystemTitleTextView14);
        settingsSystemTitleTextView14.setTextSize(settingsSystemTitleTextView14.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView15 = (TextView)findViewById(R.id.settingsSystemTitleTextView15);
        settingsSystemTitleTextView15.setTextSize(settingsSystemTitleTextView15.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView17 = (TextView)findViewById(R.id.settingsSystemTitleTextView17);
        settingsSystemTitleTextView17.setTextSize(settingsSystemTitleTextView17.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView18 = (TextView)findViewById(R.id.settingsSystemTitleTextView18);
        settingsSystemTitleTextView18.setTextSize(settingsSystemTitleTextView18.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView19 = (TextView)findViewById(R.id.settingsSystemTitleTextView19);
        settingsSystemTitleTextView19.setTextSize(settingsSystemTitleTextView19.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView20 = (TextView)findViewById(R.id.settingsSystemTitleTextView20);
        settingsSystemTitleTextView20.setTextSize(settingsSystemTitleTextView20.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView21 = (TextView)findViewById(R.id.settingsSystemTitleTextView21);
        settingsSystemTitleTextView21.setTextSize(settingsSystemTitleTextView21.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView22 = (TextView)findViewById(R.id.settingsSystemTitleTextView22);
        settingsSystemTitleTextView22.setTextSize(settingsSystemTitleTextView22.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView23 = (TextView)findViewById(R.id.settingsSystemTitleTextView23);
        settingsSystemTitleTextView23.setTextSize(settingsSystemTitleTextView23.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView24 = (TextView)findViewById(R.id.settingsSystemTitleTextView24);
        settingsSystemTitleTextView24.setTextSize(settingsSystemTitleTextView24.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView25 = (TextView)findViewById(R.id.settingsSystemTitleTextView25);
        settingsSystemTitleTextView25.setTextSize(settingsSystemTitleTextView25.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView26 = (TextView)findViewById(R.id.settingsSystemTitleTextView26);
        settingsSystemTitleTextView26.setTextSize(settingsSystemTitleTextView26.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView27 = (TextView)findViewById(R.id.settingsSystemTitleTextView27);
        settingsSystemTitleTextView27.setTextSize(settingsSystemTitleTextView27.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView28 = (TextView)findViewById(R.id.settingsSystemTitleTextView28);
        settingsSystemTitleTextView28.setTextSize(settingsSystemTitleTextView28.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView29 = (TextView)findViewById(R.id.settingsSystemTitleTextView29);
        settingsSystemTitleTextView29.setTextSize(settingsSystemTitleTextView29.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView30 = (TextView)findViewById(R.id.settingsSystemTitleTextView30);
        settingsSystemTitleTextView30.setTextSize(settingsSystemTitleTextView30.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView31 = (TextView)findViewById(R.id.settingsSystemTitleTextView31);
        settingsSystemTitleTextView31.setTextSize(settingsSystemTitleTextView31.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView32 = (TextView)findViewById(R.id.settingsSystemTitleTextView32);
        settingsSystemTitleTextView32.setTextSize(settingsSystemTitleTextView32.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView33 = (TextView)findViewById(R.id.settingsSystemTitleTextView33);
        settingsSystemTitleTextView33.setTextSize(settingsSystemTitleTextView33.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView34 = (TextView)findViewById(R.id.settingsSystemTitleTextView34);
        settingsSystemTitleTextView34.setTextSize(settingsSystemTitleTextView34.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView35 = (TextView)findViewById(R.id.settingsSystemTitleTextView35);
        settingsSystemTitleTextView35.setTextSize(settingsSystemTitleTextView35.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView36 = (TextView)findViewById(R.id.settingsSystemTitleTextView36);
        settingsSystemTitleTextView36.setTextSize(settingsSystemTitleTextView36.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView37 = (TextView)findViewById(R.id.settingsSystemTitleTextView37);
        settingsSystemTitleTextView37.setTextSize(settingsSystemTitleTextView37.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView38 = (TextView)findViewById(R.id.settingsSystemTitleTextView38);
        settingsSystemTitleTextView38.setTextSize(settingsSystemTitleTextView38.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView39 = (TextView)findViewById(R.id.settingsSystemTitleTextView39);
        settingsSystemTitleTextView39.setTextSize(settingsSystemTitleTextView39.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView40 = (TextView)findViewById(R.id.settingsSystemTitleTextView40);
        settingsSystemTitleTextView40.setTextSize(settingsSystemTitleTextView40.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView41 = (TextView)findViewById(R.id.settingsSystemTitleTextView41);
        settingsSystemTitleTextView41.setTextSize(settingsSystemTitleTextView41.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView42 = (TextView)findViewById(R.id.settingsSystemTitleTextView42);
        settingsSystemTitleTextView42.setTextSize(settingsSystemTitleTextView42.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView43 = (TextView)findViewById(R.id.settingsSystemTitleTextView43);
        settingsSystemTitleTextView43.setTextSize(settingsSystemTitleTextView43.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView45 = (TextView)findViewById(R.id.settingsSystemTitleTextView45);
        settingsSystemTitleTextView45.setTextSize(settingsSystemTitleTextView45.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView46 = (TextView)findViewById(R.id.settingsSystemTitleTextView46);
        settingsSystemTitleTextView46.setTextSize(settingsSystemTitleTextView46.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView47 = (TextView)findViewById(R.id.settingsSystemTitleTextView47);
        settingsSystemTitleTextView47.setTextSize(settingsSystemTitleTextView47.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView48 = (TextView)findViewById(R.id.settingsSystemTitleTextView48);
        settingsSystemTitleTextView48.setTextSize(settingsSystemTitleTextView48.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView49 = (TextView)findViewById(R.id.settingsSystemTitleTextView49);
        settingsSystemTitleTextView49.setTextSize(settingsSystemTitleTextView49.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView50 = (TextView)findViewById(R.id.settingsSystemTitleTextView50);
        settingsSystemTitleTextView50.setTextSize(settingsSystemTitleTextView50.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView51 = (TextView)findViewById(R.id.settingsSystemTitleTextView51);
        settingsSystemTitleTextView51.setTextSize(settingsSystemTitleTextView51.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView52 = (TextView)findViewById(R.id.settingsSystemTitleTextView52);
        settingsSystemTitleTextView52.setTextSize(settingsSystemTitleTextView52.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView53 = (TextView)findViewById(R.id.settingsSystemTitleTextView53);
        settingsSystemTitleTextView53.setTextSize(settingsSystemTitleTextView53.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView54 = (TextView)findViewById(R.id.settingsSystemTitleTextView54);
        settingsSystemTitleTextView54.setTextSize(settingsSystemTitleTextView54.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView55 = (TextView)findViewById(R.id.settingsSystemTitleTextView55);
        settingsSystemTitleTextView55.setTextSize(settingsSystemTitleTextView55.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView56 = (TextView)findViewById(R.id.settingsSystemTitleTextView56);
        settingsSystemTitleTextView56.setTextSize(settingsSystemTitleTextView56.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView57 = (TextView)findViewById(R.id.settingsSystemTitleTextView57);
        settingsSystemTitleTextView57.setTextSize(settingsSystemTitleTextView57.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView58 = (TextView)findViewById(R.id.settingsSystemTitleTextView58);
        settingsSystemTitleTextView58.setTextSize(settingsSystemTitleTextView58.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView59 = (TextView)findViewById(R.id.settingsSystemTitleTextView59);
        settingsSystemTitleTextView59.setTextSize(settingsSystemTitleTextView59.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView60 = (TextView)findViewById(R.id.settingsSystemTitleTextView60);
        settingsSystemTitleTextView60.setTextSize(settingsSystemTitleTextView60.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView61 = (TextView)findViewById(R.id.settingsSystemTitleTextView61);
        settingsSystemTitleTextView61.setTextSize(settingsSystemTitleTextView61.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView62 = (TextView)findViewById(R.id.settingsSystemTitleTextView62);
        settingsSystemTitleTextView62.setTextSize(settingsSystemTitleTextView62.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView63 = (TextView)findViewById(R.id.settingsSystemTitleTextView63);
        settingsSystemTitleTextView63.setTextSize(settingsSystemTitleTextView63.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView64 = (TextView)findViewById(R.id.settingsSystemTitleTextView64);
        settingsSystemTitleTextView64.setTextSize(settingsSystemTitleTextView64.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView65 = (TextView)findViewById(R.id.settingsSystemTitleTextView65);
        settingsSystemTitleTextView65.setTextSize(settingsSystemTitleTextView65.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView66 = (TextView)findViewById(R.id.settingsSystemTitleTextView66);
        settingsSystemTitleTextView66.setTextSize(settingsSystemTitleTextView66.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView67 = (TextView)findViewById(R.id.settingsSystemTitleTextView67);
        settingsSystemTitleTextView67.setTextSize(settingsSystemTitleTextView67.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView68 = (TextView)findViewById(R.id.settingsSystemTitleTextView68);
        settingsSystemTitleTextView68.setTextSize(settingsSystemTitleTextView68.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView69 = (TextView)findViewById(R.id.settingsSystemTitleTextView69);
        settingsSystemTitleTextView69.setTextSize(settingsSystemTitleTextView69.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView70 = (TextView)findViewById(R.id.settingsSystemTitleTextView70);
        settingsSystemTitleTextView70.setTextSize(settingsSystemTitleTextView70.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView71 = (TextView)findViewById(R.id.settingsSystemTitleTextView71);
        settingsSystemTitleTextView71.setTextSize(settingsSystemTitleTextView71.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView72 = (TextView)findViewById(R.id.settingsSystemTitleTextView72);
        settingsSystemTitleTextView72.setTextSize(settingsSystemTitleTextView72.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView73 = (TextView)findViewById(R.id.settingsSystemTitleTextView73);
        settingsSystemTitleTextView73.setTextSize(settingsSystemTitleTextView73.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView74 = (TextView)findViewById(R.id.settingsSystemTitleTextView74);
        settingsSystemTitleTextView74.setTextSize(settingsSystemTitleTextView74.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView75 = (TextView)findViewById(R.id.settingsSystemTitleTextView75);
        settingsSystemTitleTextView75.setTextSize(settingsSystemTitleTextView75.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView76 = (TextView)findViewById(R.id.settingsSystemTitleTextView76);
        settingsSystemTitleTextView76.setTextSize(settingsSystemTitleTextView76.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView77 = (TextView)findViewById(R.id.settingsSystemTitleTextView77);
        settingsSystemTitleTextView77.setTextSize(settingsSystemTitleTextView77.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView78 = (TextView)findViewById(R.id.settingsSystemTitleTextView78);
        settingsSystemTitleTextView78.setTextSize(settingsSystemTitleTextView78.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView85 = (TextView)findViewById(R.id.settingsSystemTitleTextView85);
        settingsSystemTitleTextView85.setTextSize(settingsSystemTitleTextView85.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView89 = (TextView)findViewById(R.id.settingsSystemTitleTextView89);
        settingsSystemTitleTextView89.setTextSize(settingsSystemTitleTextView89.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemTitleTextView90 = (TextView)findViewById(R.id.settingsSystemTitleTextView90);
        settingsSystemTitleTextView90.setTextSize(settingsSystemTitleTextView90.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        // 10272023
        settingsSystemTitleTextView91 = (TextView)findViewById(R.id.settingsSystemTitleTextView91);
        settingsSystemTitleTextView91.setTextSize(settingsSystemTitleTextView91.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // 01172024
        settingsSystemTitleTextView92 = (TextView)findViewById(R.id.settingsSystemTitleTextView92);
        settingsSystemTitleTextView92.setTextSize(settingsSystemTitleTextView92.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        settingsSystemTitleTextView93 = (TextView)findViewById(R.id.settingsSystemTitleTextView93);
        settingsSystemTitleTextView93.setTextSize(settingsSystemTitleTextView93.getTextSize() * GlobalMemberValues.getGlobalFontSize());



        infoTextView1 = (TextView)findViewById(R.id.infoTextView1);
        infoTextView1.setTextSize(infoTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        infoTextView2 = (TextView)findViewById(R.id.infoTextView2);
        infoTextView2.setTextSize(infoTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // 01172024
        infoTextView3 = (TextView)findViewById(R.id.infoTextView3);
        infoTextView3.setTextSize(infoTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemHttpTitleTextView = (TextView)findViewById(R.id.settingsSystemHttpTitleTextView);
        settingsSystemHttpTitleTextView.setTextSize(settingsSystemHttpTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsSystemSlashTextView = (TextView)findViewById(R.id.settingsSystemSlashTextView);
        settingsSystemSlashTextView.setTextSize(settingsSystemSlashTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        if (GlobalMemberValues.CLOUD_SERVER_URL_BASIC.toLowerCase().indexOf("https://") > -1) {
            settingsSystemHttpTitleTextView.setText("https://");
        }

        timemenuminsTextView1 = (TextView)findViewById(R.id.timemenuminsTextView1);
        timemenuminsTextView1.setTextSize(timemenuminsTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        timemenuminsTextView2 = (TextView)findViewById(R.id.timemenuminsTextView2);
        timemenuminsTextView2.setTextSize(timemenuminsTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        daystouploaddataTextView = (TextView)findViewById(R.id.daystouploaddataTextView);
        daystouploaddataTextView.setTextSize(daystouploaddataTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        salehistorypagenoTextView = (TextView)findViewById(R.id.salehistorypagenoTextView);
        salehistorypagenoTextView.setTextSize(salehistorypagenoTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        receipttypeonnoselectLn = (LinearLayout) findViewById(R.id.receipttypeonnoselectLn);
        if (vCustomerSelectReceipt == "Y" || vCustomerSelectReceipt.equals("Y")) {
            receipttypeonnoselectLn.setVisibility(View.GONE);
        } else {
            receipttypeonnoselectLn.setVisibility(View.VISIBLE);
        }

        receipttypeonnoselectCb1 = (CheckBox) findViewById(R.id.receipttypeonnoselectCb1);
        receipttypeonnoselectCb1.setTextSize(receipttypeonnoselectCb1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        receipttypeonnoselectCb2 = (CheckBox) findViewById(R.id.receipttypeonnoselectCb2);
        receipttypeonnoselectCb2.setTextSize(receipttypeonnoselectCb2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vReceipttypeonnoselect) {
            case "A" : {
                receipttypeonnoselectCb1.setChecked(true);
                receipttypeonnoselectCb2.setChecked(true);

                break;
            }
            case "C" : {
                receipttypeonnoselectCb1.setChecked(true);
                receipttypeonnoselectCb2.setChecked(false);
                break;
            }
            case "M" : {
                receipttypeonnoselectCb1.setChecked(false);
                receipttypeonnoselectCb2.setChecked(true);
                break;
            }
            default : {
                receipttypeonnoselectCb1.setChecked(false);
                receipttypeonnoselectCb2.setChecked(false);

                break;
            }
        }


        // 파일 다운로드 관련 ------------------------------------------------------------------------------------------------
        progressBar=new ProgressDialog(context);
        progressBar.setMessage("Downloading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(true);

        // information popup
        txv_system_info_num = (TextView)findViewById(R.id.setting_system_version_num);
        txv_system_info_num.setText("Version : " + GlobalMemberValues.getAppVersionName(context) + " ");
        img_button_info = (ImageButton)findViewById(R.id.system_info_btn);
        img_button_info.setOnClickListener(infomation_btn_listener);

        btn_transdata = findViewById(R.id.transdata);
        btn_transdata.setOnClickListener(mButtonClick);

    }

    CompoundButton.OnCheckedChangeListener setCheckCustomerSelectReceipt = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                receipttypeonnoselectLn.setVisibility(View.GONE);
            } else {
                receipttypeonnoselectLn.setVisibility(View.VISIBLE);
            }
        }
    };

    AdapterView.OnItemSelectedListener mDevicenameSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemDeviceNameSpinner = mGeneralArrayListForSpinnerDeviceName.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mTableBoardTypeSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemTableBoardTypeSpinner = mGeneralArrayListForSpinnerTableBoardType.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /**
                 case R.id.deleteSalesButton : {
                 new AlertDialog.Builder(context)
                 .setTitle("Delete Sales Data")
                 .setMessage("Do you want to clear all sales history?\nDeleted data can not be recovered")
                 //.setIcon(R.drawable.ic_launcher)
                 .setPositiveButton("Yes",
                 new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                 Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                 // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                 adminPasswordIntent.putExtra("openClassMethod", "settingssystem_salesdelete");
                 // -------------------------------------------------------------------------------------
                 mActivity.startActivity(adminPasswordIntent);
                 mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                 }
                 })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                 //
                 }
                 })
                 .show();
                 break;
                 }
                 **/
                case R.id.saveSettingsSystemButton : {
                    String ip1 = mssqldbipTextView1.getText().toString().trim();

                    if (GlobalMemberValues.isStrEmpty(ip1)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter DB Server IP Address", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(156);

                    int resultValue = setSettingsSystem();

                    // 타임메뉴 사용여부
                    GlobalMemberValues.setTimeMenuUseYN();
                    // 타임메뉴 설정창 자동오픈 여부값 설정
                    GlobalMemberValues.setTimeMenuSetAutoOpenValue();

                    // 서비스 메뉴 리로드 ----------------------------------------------------------------------------------------------------
                    GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(MainActivity.mContext);
                    MainTopCategory mainTopCate = new MainTopCategory(MainActivity.mActivity, MainActivity.mContext, dataAtSqlite, 0);
                    mainTopCate.setTopCategory();
                    // ---------------------------------------------------------------------------------------------------------------------

                    if (resultValue > 0) {
                        if (mPushTypeValue == "2" || mPushTypeValue.equals("2")) {
                            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER == null || GlobalMemberValues.CURRENTSERVICEINTENT_NEWWEBORDER == null) {
                                MainActivity.runTimer_newWebOrder();
                            }
                        }
                        GlobalMemberValues.displayDialog(context, "Settings - System", "Successfully Updated", "Close");
                    } else {
                        GlobalMemberValues.displayDialog(context, "Settings - System", "Update Failure", "Close");
                    }
                    break;
                }

                case R.id.systemResetButton : {
                    Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "system_reset");
                    adminPasswordIntent.putExtra("passwordType", "masterpwd");
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.deleteSalesButton : {
                    Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "settingssystem_salesdelete");
                    adminPasswordIntent.putExtra("passwordType", "masterpwd");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.syncrealtimebtn : {
                    Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "syncrealsaletime");
                    adminPasswordIntent.putExtra("passwordType", "masterpwd");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.dualdpadlocalpathBtn : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("File Download")
                                .setMessage("Do you want to download the ad file?")
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
                case R.id.dualdprestartBtn : {
                    reloadPresentation();
                    break;
                }
                case R.id.btn_setting_receipt_setting : {
                    Intent settingsIntent = new Intent(MainActivity.mContext, SettingReceipt_popup.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    startActivity(settingsIntent);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.transdata: {

                    // popup
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Trans. DATABASE")
                                .setMessage("Are you sure to transfer data from POS to SERVER?\n" +
                                        "If you have data on your server, it may be duplicated")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 해당 기능 실행
                                        TransDatasForSales transDatasForSales = new TransDatasForSales();
                                        transDatasForSales.transDatas();
                                    }
                                }).show();
                    }


                    break;
                }
            }
        }
    };

    public int setSettingsSystem() {
        int insSplashUse = 0;
        int insInReverse = 0;
        String insCloudUrl = "";
        String insGmailPwd = "";
        int insDownloadData = 1;
        int insDatabaseBackup = 0;
        String insGmailId = "";
        int insClockinoutType = 0;
        String insDepartmentViewYN = "N";
        String insSyncPointYN = "N";
        String insShowcostafterdcextra = "N";
        String insCardislast = "N";
        String insPointpaysavepointyn = "N";
        String insTimemenuautoreload = "N";
        String insPushtype = "2";
        String insDeviceKind = "";
        String insPick_here = "N";
        String insPick_togo = "N";
        String insPick_delivery = "N";
        String insCustomerInfoShow = "N";
        String insCustomerSelectReceipt = "N";
        String insEmployeeCardStartNumber = "1";
        String insEmployeeCardCountNumber = "0";
        String insGiftCardStartNumber = "1";
        String insGiftCardCountNumber = "0";
        String insCashPadUse = "N";
        String insAutoReceiptPrintingYN = "Y";
        String insCouponInfoView = "Y";
        String insOnlineOrderUseYN = "N";
        String insTimeMenuUseYN = "Y";
        String insCouponImmediately = "Y";
        String insTimemenuchecktime = "10";
        String insMaxPagerNum = "50";
        String insPagernumberheadertxt = "#";
        String insPagerUseYN = "N";
        String insPagerNumberAutoYN = "Y";
        String insProductminalertyn = "N";
        String insDaystouploaddataEv = "10";
        String insBatchincashoutyn = "N";
        String insStartingcashprintyn = "Y";
        String insSaledatemodifyyn = "N";
        String insSalehistorypageno = "30";
        String insButtonmodifierbottomviewyn = "Y";
        String insReceipttypeonnoselect = "E";
        String insModifierpriceviewyn = "N";
        String insDualdisplaypossible = "N";
        String insPricedollarynSwitch = "N";
        String insDualdpadtype = "W";
        String insThankyoupageyn = "N";
        String insUploadynonlysalesdata = "N";
        String insAutosaledatauploadyn = "N";
        String insCloudbackupintenderbackupyn = "N";
        String insItemanimationyn = "N";
        String insCarddirectyn = "N";
        String insVrkitchenprinting = "N";
        String insAutoweborderprinting = "N";
        String insModifierqtyviewyn = "Y";
        String insMssqldbip = "";

        String insInitcapacity = "500";

        String insPagernumofdigits = "0";

        String insQtyaddupyn = "N";
        String insTipaddsalehistoryyn = "N";

        String insPasswordyninmod = "N";

        String insTableBoardType = "2";

        String insOrdernumbershowonpickuporder = "N";
        String insPagernumbershowonpickuporder = "N";

        String insCardstatususe = "N";

        String insQtyinsyn = "N";

        String insCustomernumberviewyn = "N";

        String insServerCodeUseYn = "N";
        String insServerPasswordUseYn = "N";

        String insPickuptypepopupuseyn = "N";

        String insPrintingcategoryyn = "N";

        String insOpenmsgwhendeletemenu_yn = "N";
        String insOtherpayprinting_yn = "N";
        String insTipprintingwhentogo_yn = "N";

        String insBillprintpopupyn = "N";

        String insCustombillsplituseyn = "N";

        String insCustomeronlinecheckyn = "Y";

        String insStartpagernum = "1";

        String insSaledatauploadpauseyn = "N";

        String insTimeviewontableyn = "N";

        // 10272023
        String insItemdeletereasonyn = "N";

        // 01172024
        String insTableorderuseyn = "N";

        String insPushpopupopenyn = "N";

        if (splashUseSwitch.isChecked()) {
            insSplashUse = 0;
        } else {
            insSplashUse = 1;
        }

        String insCustomer_info_here = "N";
        String insCustomer_info_togo = "N";
        String insCustomer_info_delivery = "N";

        /**
         if (inReverseSwitch.isChecked()) {
         insInReverse = 1;
         } else {
         insInReverse = 0;
         }
         **/

        insCloudUrl = cloudUrlEditText.getText().toString();
        if (!GlobalMemberValues.isStrEmpty(insCloudUrl)) {
            if (!insCloudUrl.substring((insCloudUrl.length()-1), insCloudUrl.length()).equals("/")) {
                insCloudUrl = insCloudUrl + "/";
            }
            /**
             if (insCloudUrl.toLowerCase().indexOf("http://") == -1) {
             insCloudUrl = "http://" + insCloudUrl;
             }
             **/
        }

        String tempGmailId = gmailIdEditText.getText().toString();
        if (tempGmailId.indexOf("@") > -1) {
            String[] strSplitStrArr = tempGmailId.split("@");
            insGmailId = strSplitStrArr[0];
        } else {
            insGmailId = tempGmailId;
        }

        insEmployeeCardStartNumber = employeecardstartnumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insEmployeeCardStartNumber)) {
            insEmployeeCardStartNumber = "1";
        }
        insEmployeeCardCountNumber = employeecardcountnumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insEmployeeCardCountNumber)) {
            insEmployeeCardCountNumber = "0";
        }

        insGiftCardStartNumber = giftcardstartnumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insGiftCardStartNumber)) {
            insGiftCardStartNumber = "1";
        }
        insGiftCardCountNumber = giftcardcountnumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insGiftCardCountNumber)) {
            insGiftCardCountNumber = "0";
        }

        insInitcapacity = initcapacityEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insInitcapacity)) {
            insInitcapacity = "500";
        }

        insPagernumofdigits = pagernumofdigitsEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insPagernumofdigits)) {
            insPagernumofdigits = "0";
        } else {
            if (GlobalMemberValues.getIntAtString(insPagernumofdigits) > 4) {
                insPagernumofdigits = "4";
            }
        }


        String tempIP1 = mssqldbipTextView1.getText().toString();
        insMssqldbip = tempIP1;


        if (downloadDataSwitch.isChecked()) {
            insDownloadData = 1;
        } else {
            insDownloadData = 0;
        }

        if (clockinoutTypeSwitch.isChecked()) {
            insClockinoutType = 1;
        } else {
            insClockinoutType = 0;
        }

        if (departmentViewYNSwitch.isChecked()) {
            insDepartmentViewYN = "Y";
        } else {
            insDepartmentViewYN = "N";
        }

        if (syncPointInSelectingCustomerSwitch.isChecked()) {
            insSyncPointYN = "Y";
        } else {
            insSyncPointYN = "N";
        }

        if (showCostAfterDCExtraSwitch.isChecked()) {
            insShowcostafterdcextra = "Y";
        } else {
            insShowcostafterdcextra = "N";
        }

        if (cardIsLaslSwitch.isChecked()) {
            insCardislast = "Y";
        } else {
            insCardislast = "N";
        }

        if (pointpaysavepointynSwitch.isChecked()) {
            insPointpaysavepointyn = "Y";
        } else {
            insPointpaysavepointyn = "N";
        }

        insDeviceKind = selectedItemDeviceNameSpinner;

        if (customerinfoviewSwitch.isChecked()) {
            insCustomerInfoShow = "Y";
        } else {
            insCustomerInfoShow = "N";
        }

        if (customerselectreceiptSwitch.isChecked()) {
            insCustomerSelectReceipt = "Y";
        } else {
            insCustomerSelectReceipt = "N";
        }

        if (cashpaduseSwitch.isChecked()) {
            insCashPadUse = "Y";
        } else {
            insCashPadUse = "N";
        }
        if (autoreceiptprintingynSwitch.isChecked()) {
            insAutoReceiptPrintingYN = "Y";
        } else {
            insAutoReceiptPrintingYN = "N";
        }
        if (couponinfoviewSwitch.isChecked()) {
            insCouponInfoView = "Y";
        } else {
            insCouponInfoView = "N";
        }
        if (onlineorderuseynSwitch.isChecked()) {
            insOnlineOrderUseYN = "Y";
        } else {
            insOnlineOrderUseYN = "N";
        }
        if (timemenuactiveSwitch.isChecked()) {
            insTimeMenuUseYN = "Y";
        } else {
            insTimeMenuUseYN = "N";
        }
        if (couponimmediatelySwitch.isChecked()) {
            insCouponImmediately = "Y";
        } else {
            insCouponImmediately = "N";
        }
        if (pageruseynSwitch.isChecked()) {
            insPagerUseYN = "Y";
        } else {
            insPagerUseYN = "N";
        }
        if (pagernumberautoynSwitch.isChecked()) {
            insPagerNumberAutoYN = "Y";
        } else {
            insPagerNumberAutoYN = "N";
        }
        if (productminalertynSwitch.isChecked()) {
            insProductminalertyn = "Y";
        } else {
            insProductminalertyn = "N";
        }
        if (batchincashoutynSwitch.isChecked()) {
            insBatchincashoutyn = "Y";
        } else {
            insBatchincashoutyn = "N";
        }
        if (startingcashprintynSwitch.isChecked()) {
            insStartingcashprintyn = "Y";
        } else {
            insStartingcashprintyn = "N";
        }
        if (saledatemodifyynSwitch.isChecked()) {
            insSaledatemodifyyn = "Y";
        } else {
            insSaledatemodifyyn = "N";
        }
        if (buttonmodifierbottomviewynSwith.isChecked()) {
            insButtonmodifierbottomviewyn = "Y";
        } else {
            insButtonmodifierbottomviewyn = "N";
        }
        if (modifierpriceviewynSwith.isChecked()) {
            insModifierpriceviewyn = "Y";
        } else {
            insModifierpriceviewyn = "N";
        }
        if (dualdisplaypossibleSwitch.isChecked()) {
            insDualdisplaypossible = "Y";
        } else {
            insDualdisplaypossible = "N";
        }
        if (pricedollarynSwitch.isChecked()) {
            insPricedollarynSwitch = "Y";
        } else {
            insPricedollarynSwitch = "N";
        }
        if (dualdpadtypeSwitch.isChecked()) {
            insDualdpadtype = "W";
        } else {
            insDualdpadtype = "L";
        }
        if (thankyoupageynSwith.isChecked()) {
            insThankyoupageyn = "Y";
        } else {
            insThankyoupageyn = "N";
        }
        if (uploadynonlysalesdataSwith.isChecked()) {
            insUploadynonlysalesdata = "Y";
        } else {
            insUploadynonlysalesdata = "N";
        }
        if (autosaledatauploadynSwitch.isChecked()) {
            insAutosaledatauploadyn = "Y";
        } else {
            insAutosaledatauploadyn = "N";
        }
        if (cloudbackupintenderbackupynSwitch.isChecked()) {
            insCloudbackupintenderbackupyn = "Y";
        } else {
            insCloudbackupintenderbackupyn = "N";
        }
        if (itemanimationynSwitch.isChecked()) {
            insItemanimationyn = "Y";
        } else {
            insItemanimationyn = "N";
        }
        if (carddirectynSwitch.isChecked()) {
            insCarddirectyn = "Y";
        } else {
            insCarddirectyn = "N";
        }
        if (vrkitchenprintingSwitch.isChecked()) {
            insVrkitchenprinting = "Y";
        } else {
            insVrkitchenprinting = "N";
        }
        if (autoweborderprintingSwitch.isChecked()) {
            insAutoweborderprinting = "Y";
        } else {
            insAutoweborderprinting = "N";
        }
        if (modifierqtyviewynSwith.isChecked()) {
            insModifierqtyviewyn = "Y";
        } else {
            insModifierqtyviewyn = "N";
        }
        if (qtyaddupynSwitch.isChecked()) {
            insQtyaddupyn = "Y";
        } else {
            insQtyaddupyn = "N";
        }

        if (tipsalehistoryaddupynSwitch.isChecked()) {
            insTipaddsalehistoryyn = "Y";
        } else {
            insTipaddsalehistoryyn = "N";
        }

        if (passwordyninmodSwitch.isChecked()) {
            insPasswordyninmod = "Y";
        } else {
            insPasswordyninmod = "N";
        }

        if (ordernumbershowonpickuporderSwitch.isChecked()) {
            insOrdernumbershowonpickuporder = "Y";
        } else {
            insOrdernumbershowonpickuporder = "N";
        }

        if (pagernumbershowonpickuporderSwitch.isChecked()) {
            insPagernumbershowonpickuporder = "Y";
        } else {
            insPagernumbershowonpickuporder = "N";
        }

        if (cardstatususeSwitch.isChecked()) {
            insCardstatususe = "Y";
        } else {
            insCardstatususe = "N";
        }

        if (qtyinsynSwitch.isChecked()) {
            insQtyinsyn = "Y";
        } else {
            insQtyinsyn = "N";
        }

        if (saledatauploadpauseynSwitch.isChecked()){
            insSaledatauploadpauseyn = "Y";
        } else {
            insSaledatauploadpauseyn = "N";
        }

        if (timeviewontableynSwitch.isChecked()){
            insTimeviewontableyn = "Y";
        } else {
            insTimeviewontableyn = "N";
        }


        // 10272023
        if (itemdeletereasonynSwitch.isChecked()){
            insItemdeletereasonyn = "Y";
        } else {
            insItemdeletereasonyn = "N";
        }

        // 01172024
        if (tableorderuseynSwitch.isChecked()){
            insTableorderuseyn = "Y";
        } else {
            insTableorderuseyn = "N";
        }


        if (customernumberviewynSwitch.isChecked()) {
            insCustomernumberviewyn = "Y";
        } else {
            insCustomernumberviewyn = "N";
        }

        if (pickuptypepopupuseynSwitch.isChecked()) {
            insPickuptypepopupuseyn = "Y";
        } else {
            insPickuptypepopupuseyn = "N";
        }

        if (printingcategoryynSwitch.isChecked()) {
            insPrintingcategoryyn = "Y";
        } else {
            insPrintingcategoryyn = "N";
        }

        if (billprintpopupynSwitch.isChecked()) {
            insBillprintpopupyn = "Y";
        } else {
            insBillprintpopupyn = "N";
        }

        if (custombillsplituseynSwitch.isChecked()) {
            insCustombillsplituseyn = "Y";
        } else {
            insCustombillsplituseyn = "N";
        }


        if (openmsgwhendeletemenu_ynSwitch.isChecked()) {
            insOpenmsgwhendeletemenu_yn = "Y";
        } else {
            insOpenmsgwhendeletemenu_yn = "N";
        }
        if (otherpayprinting_ynSwitch.isChecked()) {
            insOtherpayprinting_yn = "Y";
        } else {
            insOtherpayprinting_yn = "N";
        }
        if (tipprintingwhentogo_ynSwitch.isChecked()) {
            insTipprintingwhentogo_yn = "Y";
        } else {
            insTipprintingwhentogo_yn = "N";
        }

        if (customeronlinecheckynSwitch.isChecked()){
            insCustomeronlinecheckyn = "Y";
        } else {
            insCustomeronlinecheckyn = "N";
        }



        if (serverCode_useSwitch.isChecked()) {
            insServerCodeUseYn = "Y";
        } else {
            insServerCodeUseYn = "N";
        }

        if (serverPassword_useSwitch.isChecked()) {
            insServerPasswordUseYn = "Y";
        } else {
            insServerPasswordUseYn = "N";
        }

        if (pushpopupopenynSwitch.isChecked()){
            insPushpopupopenyn = "Y";
        } else {
            insPushpopupopenyn = "N";
        }

        if (!receipttypeonnoselectCb1.isChecked() && !receipttypeonnoselectCb2.isChecked()) {
            insReceipttypeonnoselect = "E";
        } else {
            if (receipttypeonnoselectCb1.isChecked() && receipttypeonnoselectCb2.isChecked()) {
                GlobalMemberValues.logWrite("settingslog", "여기가...1" + "\n");
                insReceipttypeonnoselect = "A";
            } else {
                if (receipttypeonnoselectCb1.isChecked()) {
                    GlobalMemberValues.logWrite("settingslog", "여기가...2" + "\n");
                    insReceipttypeonnoselect = "C";
                }
                if (receipttypeonnoselectCb2.isChecked()) {
                    GlobalMemberValues.logWrite("settingslog", "여기가...3" + "\n");
                    insReceipttypeonnoselect = "M";
                }
            }
        }

        GlobalMemberValues.logWrite("settingslog", "insReceipttypeonnoselect : " + insReceipttypeonnoselect + "\n");

        insGmailPwd = gmailPwdEditText.getText().toString();

        insTimemenuchecktime = timemenuchecktimeEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTimemenuchecktime)) {
            insTimemenuchecktime = "10";
        }

        insMaxPagerNum = maxpagernumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insMaxPagerNum)) {
            insMaxPagerNum = "10";
        }

        insPagernumberheadertxt = pagernumberheadertxtEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insPagernumberheadertxt)) {
            insPagernumberheadertxt = "";
        }

        insDaystouploaddataEv = daystouploaddataEv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insDaystouploaddataEv)) {
            insDaystouploaddataEv = "10";
        }

        insMaxPagerNum = maxpagernumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insMaxPagerNum)) {
            insMaxPagerNum = "10";
        }

        insSalehistorypageno = salehistorypagenoEv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insSalehistorypageno)) {
            insSalehistorypageno = "30";
        }

        insStartpagernum = startpagernumEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insStartpagernum)) {
            insStartpagernum = "1";
        }

        insInReverse = mInReverseValue;
        insDatabaseBackup = mDatabaseBackUpValue;
        insPushtype = mPushTypeValue;
        insTimemenuautoreload = mTimeMenuAutoReload;


        String gTableBoardType = selectedItemTableBoardTypeSpinner;
        switch (gTableBoardType) {
            case "Auto" : {
                insTableBoardType = "A";
                break;
            }
            case "Type A - Big Size" : {
                insTableBoardType = "1";
                break;
            }
            case "Type B - Middle Size" : {
                insTableBoardType = "2";
                break;
            }
            case "Type C - Small Size" : {
                insTableBoardType = "3";
                break;
            }
        }

        if (customerinfoviewSwitch_type_here.isChecked()){
            insCustomer_info_here = "Y";
        }
        if (customerinfoviewSwitch_type_togo.isChecked()){
            insCustomer_info_togo = "Y";
        }
        if (customerinfoviewSwitch_type_delivery.isChecked()){
            insCustomer_info_delivery = "Y";
        }



        int returnValue = 0;

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String updStrQuery = "update salon_storestationsettings_system set " +
                " splashuse = '" + insSplashUse + "', " +
                " inreverse = '" + insInReverse + "', " +
                //" cloudurl = '" + insCloudUrl + "', " +
                " downloaddata = '" + insDownloadData + "', " +
                " databasebackup = '" + insDatabaseBackup + "', " +
                " clockinouttype = '" + insClockinoutType + "', " +
                " gmailId = '" + insGmailId + "', " +
                " gmailPwd = '" + insGmailPwd + "', " +
                " departmentviewyn = '" + insDepartmentViewYN + "', " +
                " mileagesyncinselectcustomer = '" + insSyncPointYN + "', " +
                " showcostafterdcextra = '" + insShowcostafterdcextra + "', " +
                " cardislast = '" + insCardislast + "', " +
                " pointpaysavepointyn = '" + insPointpaysavepointyn + "', " +
                " timemenuautoreload = '" + insTimemenuautoreload + "', " +
                " pushtype = '" + insPushtype + "', " +
                " devicekind = '" + insDeviceKind + "', " +
                " picktype_here = '" + insPick_here + "', " +
                " picktype_togo = '" + insPick_togo + "', " +
                " picktype_delivery = '" + insPick_delivery + "', " +
                " customerinfoshow = '" + insCustomerInfoShow + "', " +
                " customerselectreceipt = '" + insCustomerSelectReceipt + "', " +
                " empcardstartnum = '" + insEmployeeCardStartNumber + "', " +
                " empcardcountnum = '" + insEmployeeCardCountNumber + "', " +
                " giftcardstartnum = '" + insGiftCardStartNumber + "', " +
                " giftcardcountnum = '" + insGiftCardCountNumber + "', " +
                " cashpaduse = '" + insCashPadUse + "', " +
                " autoreceiptprintingyn = '" + insAutoReceiptPrintingYN + "', " +
                " couponinfoview = '" + insCouponInfoView + "', " +
                " onlineorderuseyn = '" + insOnlineOrderUseYN + "', " +
                " timemenuuseyn = '" + insTimeMenuUseYN + "', " +
                " couponimmediately = '" + insCouponImmediately + "', " +
                " timemenuchecktime = '" + insTimemenuchecktime + "', " +
                " maxpagernum = '" + insMaxPagerNum + "', " +
                " pagernumberheadertxt = '" + insPagernumberheadertxt + "', " +
                " pageruseyn = '" + insPagerUseYN + "', " +
                " pagernumberautoyn = '" + insPagerNumberAutoYN + "', " +
                " productminalertyn = '" + insProductminalertyn + "', " +
                " daystouploaddata = '" + insDaystouploaddataEv + "', " +
                " batchincashoutyn = '" + insBatchincashoutyn + "', " +
                " startingcashprintyn = '" + insStartingcashprintyn + "', " +
                " saledatemodifyyn = '" + insSaledatemodifyyn + "', " +
                " salehistorypageno = '" + insSalehistorypageno + "', " +
                " buttonmodifierbottomviewyn = '" + insButtonmodifierbottomviewyn + "', " +
                " receipttypeonnoselect = '" + insReceipttypeonnoselect + "', " +
                " modifierpriceviewyn = '" + insModifierpriceviewyn + "', " +
                " dualdisplaypossible = '" + insDualdisplaypossible + "', " +
                " pricedollaryn = '" + insPricedollarynSwitch + "', " +
                " dualdpadtype = '" + insDualdpadtype + "', " +
                " thankyoupageyn = '" + insThankyoupageyn + "', " +
                " uploadynonlysalesdata = '" + insUploadynonlysalesdata + "', " +
                " autosaledatauploadyn = '" + insAutosaledatauploadyn + "', " +
                " cloudbackupintenderbackupyn = '" + insCloudbackupintenderbackupyn + "', " +
                " itemanimationyn = '" + insItemanimationyn + "', " +
                " carddirectyn = '" + insCarddirectyn + "', " +
                " vrkitchenprinting = '" + insVrkitchenprinting + "', " +
                " autoweborderprinting = '" + insAutoweborderprinting + "', " +
                " modifierqtyviewyn = '" + insModifierqtyviewyn + "', " +
                " mssqlsyncyn = '" + mMssqlSyncYN + "', " +
                " mssqldbip = '" + insMssqldbip + "', " +
                " initcapacity = '" + insInitcapacity + "', " +
                " qtyaddupyn = '" + insQtyaddupyn + "', " +
                " tipaddhistoryvisibleyn = '" + insTipaddsalehistoryyn + "', " +
                " passwordyninmod = '" + insPasswordyninmod + "', " +
                " tableboardtype = '" + insTableBoardType + "', " +
                " ordernumbershowonpickuporder = '" + insOrdernumbershowonpickuporder + "', " +
                " pagernumbershowonpickuporder = '" + insPagernumbershowonpickuporder + "', " +
                " cardstatususe = '" + insCardstatususe + "', " +
                " qtyinsyn = '" + insQtyinsyn + "', " +
                " customernumberviewyn = '" + insCustomernumberviewyn + "', " +
                " servercodeuse = '" + insServerCodeUseYn + "', " +
                " serverpassworduse = '" + insServerPasswordUseYn + "', " +
                " pickuptypepopupuseyn = '" + insPickuptypepopupuseyn + "', " +
                " printingcategoryyn = '" + insPrintingcategoryyn + "', " +
                " customer_info_here_yn = '" + insCustomer_info_here + "', " +
                " customer_info_togo_yn = '" + insCustomer_info_togo + "', " +
                " customer_info_delivery_yn = '" + insCustomer_info_delivery + "', " +

                " openmsgwhendeletemenu_yn = '" + insOpenmsgwhendeletemenu_yn + "', " +
                " otherpayprinting_yn = '" + insOtherpayprinting_yn + "', " +
                " tipprintingwhentogo_yn = '" + insTipprintingwhentogo_yn + "', " +

                " billprintpopupyn = '" + insBillprintpopupyn + "', " +

                " custombillsplituseyn = '" + insCustombillsplituseyn + "', " +

                " pagernumofdigits = '" + insPagernumofdigits + "', " +

                " customeronlinecheckyn = '" + insCustomeronlinecheckyn + "', " +

                " startpagernum = '" + insStartpagernum + "', " +

                " saledatauploadpauseyn = '" + insSaledatauploadpauseyn + "', " +

                " timeviewontableyn = '" + insTimeviewontableyn + "', " +

                // 10272023
                " itemdeletereasonyn = '" + insItemdeletereasonyn + "', " +

                // 01172024
                " tableorderuseyn = '" + insTableorderuseyn + "', " +

                " pushpopupopenyn = '" + insPushpopupopenyn + "', " +

                " mdate = datetime('now') ";

        strUpdateQueryVec.addElement(updStrQuery);

        for (String tempQuery : strUpdateQueryVec) {
            GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
        }

        if (strUpdateQueryVec.size() > 0) {
            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {
                returnValue = 0;
            } else {
                returnValue = 1;
            }
        } else {
            returnValue = 0;
        }

        if (returnValue > 0) {
            if (insSaledatauploadpauseyn == "Y" || insSaledatauploadpauseyn.equals("Y")) {
                GlobalMemberValues.stopBackgroundService();
            }

            GlobalMemberValues.GLOBAL_SPLASHUSE = insSplashUse;
            GlobalMemberValues.PORTRAITORLANDSCAPE = insInReverse;
            GlobalMemberValues.logWrite("PORTRAITORLANDSCAPEValue1", "value : " + GlobalMemberValues.PORTRAITORLANDSCAPE + "\n");
            GlobalMemberValues.GLOBAL_DOWNLOADDATA = insDownloadData;
            GlobalMemberValues.GLOBAL_DATABASEBACKUP = insDatabaseBackup;
            GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN = insDepartmentViewYN;

            GlobalMemberValues.ITEMADDANIMATIONYN = insItemanimationyn;

            GlobalMemberValues.mPasswordYN_inMod = insPasswordyninmod;

            /**
             if (insCloudUrl.toLowerCase().indexOf("http://") == -1) {
             insCloudUrl = "http://" + insCloudUrl;
             }

             GlobalMemberValues.CLOUD_SERVER_URL = insCloudUrl;
             **/

            GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA = insShowcostafterdcextra;
            GlobalMemberValues.GLOBAL_CARDISLAST = insCardislast;

            GlobalMemberValues.GLOBAL_PUSHTYPE = insPushtype;

            if (insTimeMenuUseYN.equals("N") || insTimeMenuUseYN == "N") {
                GlobalMemberValues.setTimeMenuBottomTv("");
            } else {
                GlobalMemberValues.setTimeMenuBottomTv(GlobalMemberValues.SELECTED_TIME_CODEVALUE);
            }

            GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES = GlobalMemberValues.getIntAtString(insTimemenuchecktime);

            if (insModifierpriceviewyn == "Y" || insModifierpriceviewyn.equals("Y")) {
                GlobalMemberValues.MODIFIER_PRICEVIEW = true;
            } else {
                GlobalMemberValues.MODIFIER_PRICEVIEW = false;
            }

            if (insDualdisplaypossible == "Y" || insDualdisplaypossible.equals("Y")) {
                GlobalMemberValues.ISDUALDISPLAYPOSSIBLE = true;
            } else {
                GlobalMemberValues.ISDUALDISPLAYPOSSIBLE = false;
            }

            if (mSavedDeviceName.toUpperCase().equals("PAX")) {
                GlobalMemberValues.mDevicePAX = true;
            } else {
                GlobalMemberValues.mDevicePAX = false;
            }

            switch (mSavedDeviceName) {
                case "PAX" : {
                    GlobalMemberValues.mDevicePAX = true;
                    GlobalMemberValues.mDeviceSunmi = false;
                    break;
                }
                case "Sunmi" : {
                    GlobalMemberValues.mDeviceSunmi = true;
                    GlobalMemberValues.mDevicePAX = false;
                    break;
                }
            }

            if (mSavedDeviceName.toUpperCase().equals("Tablet PC")) {
                GlobalMemberValues.mDeviceTabletPC = true;
            } else {
                GlobalMemberValues.mDeviceTabletPC = false;
            }

            if (insPricedollarynSwitch == "Y" || insPricedollarynSwitch.equals("Y")) {
                GlobalMemberValues.ISDISPLAYPRICEDOLLAR = true;
            } else {
                GlobalMemberValues.ISDISPLAYPRICEDOLLAR = false;
            }

            GlobalMemberValues.mssql_sync = mMssqlSyncYN;
            GlobalMemberValues.mssql_ip = insMssqldbip;

            reloadPresentation();

            // 01172024
            if (!orgin_timeMenuAutoReload.equals(insTimemenuautoreload) || !orgin_onlineorderuseyn.equals(insOnlineOrderUseYN)
                    || !orgin_timemenuchecktime.equals(insTimemenuchecktime) || !orgin_tableorderuseyn.equals(insTableorderuseyn)) {
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
        }

        return returnValue;
    }


    RadioGroup.OnCheckedChangeListener checkRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.inReverseRadioGroup : {
                    LogsSave.saveLogsInDB(125);
                    switch (checkedId) {
                        // 가로 정방향 고정
                        case R.id.forwardRadioButton : {
                            mInReverseValue = 0;
                            break;
                        }
                        // 가로 역방향 고정
                        case R.id.reverseRadioButton : {
                            mInReverseValue = 1;
                            break;
                        }
                        // 자동 회전
                        case R.id.sensorRadioButton : {
                            mInReverseValue = 2;
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("DATABASEBACKUPVALUE", "value : " + mDatabaseBackUpValue + "\n");
                    break;
                }
                case R.id.databaseBackupRadioGroup : {
                    LogsSave.saveLogsInDB(128);
                    switch (checkedId) {
                        case R.id.databaseBackupNoRadioButton : {
                            mDatabaseBackUpValue = 0;
                            break;
                        }
                        case R.id.databaseBackupInTenderRadioButton : {
                            mDatabaseBackUpValue = 1;
                            break;
                        }
                        case R.id.databaseBackupInClosingAppRadioButton : {
                            mDatabaseBackUpValue = 2;
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("DATABASEBACKUPVALUE", "value : " + mDatabaseBackUpValue + "\n");
                    break;
                }
                case R.id.pushtypeRadioGroup : {
                    switch (checkedId) {
                        // 푸시 안받음
                        case R.id.pushtypeNoRadioButton : {
                            mPushTypeValue = "0";
                            break;
                        }
                        // 구글 푸시
                        case R.id.pushtypeGoogleRadioButton : {
                            mPushTypeValue = "1";
                            break;
                        }
                        // 클라우드 푸시
                        case R.id.pushtypeCloudSystemRadioButton : {
                            mPushTypeValue = "2";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("DATABASEBACKUPVALUE", "value : " + mDatabaseBackUpValue + "\n");
                    break;
                }
                case R.id.timemenuopentypeRadioGroup : {
                    switch (checkedId) {
                        // 푸시 안받음
                        case R.id.timemenuopentypeNoRadioButton : {
                            mTimeMenuAutoReload = "N";
                            break;
                        }
                        // 구글 푸시
                        case R.id.timemenuopentypePopupOpenRadioButton : {
                            mTimeMenuAutoReload = "Y";
                            break;
                        }
                        // 클라우드 푸시
                        case R.id.timemenuopentypeAutoRadioButton : {
                            mTimeMenuAutoReload = "A";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("DATABASEBACKUPVALUE", "value : " + mDatabaseBackUpValue + "\n");
                    break;
                }
                case R.id.mssqlinsatdownloadRadioGroup : {
                    LogsSave.saveLogsInDB(122);
                    switch (checkedId) {
                        case R.id.yesRadioButton : {
                            mMssqlSyncYN = "Y";
                            break;
                        }
                        case R.id.noRadioButton : {
                            mMssqlSyncYN = "N";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    public static void setResetApp() {
        new AlertDialog.Builder(context)
                .setTitle("RESET APPLICATION")
                .setMessage("Initialized data can not be restored\nDo you want to reset application?")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                    LogsSave.saveLogsInDB(154);
                                    resetApplication();
                                } else {
                                    //GlobalMemberValues.displayDialog(context, "Waraning", "Internet is not connected", "Close");
                                    GlobalMemberValues.openNetworkNotConnected();
                                }
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
                .show();
    }

    public static void resetApplication() {
        // 초기화 전에 먼저 데이터베이스 백업을 실행한다.
        CommandButton.backupDatabase(false);

        String returnValue = "";
        GlobalMemberValues.logWrite("commandButtonReset", "stcode : " + GlobalMemberValues.STATION_CODE + "\n");
        API_station_reset apiresetInstance = new API_station_reset(GlobalMemberValues.STATION_CODE);
        apiresetInstance.execute(null, null, null);
        try {
            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
            if (apiresetInstance.mFlag) {
                returnValue = apiresetInstance.mReturnValue;
            }
        } catch (InterruptedException e) {
            //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
        }
        GlobalMemberValues.logWrite("commandButtonReset", "returnValue : " + returnValue);
        // 전체 테이블 삭제 (일단 basic_pos_information 테이블을 제외한 나머지 모든 테이블 삭제)
        MainActivity.mDbInit.dropAllDatabaseTables(GlobalMemberValues.dbTableNameGroup);
        // 초기화 이므로 basic_pos_information 테이블도 삭제 !!!!!!!!!!!!!!!!!!!!!!!!!
        MainActivity.mDbInit.dropEachDatabaseTable("basic_pos_information");
        MainActivity.mDbInit.initDatabaseTables();                    // 데이터베이스 생성 및 테이블 생성

        // Settings 값 초기화 ----------------------------------------------------------------------
        GlobalMemberValues.APPOPENCOUNT = 0;
        GlobalMemberValues.SPLASHCOUNT = 0;
        GlobalMemberValues.DROPTABLETYPE = 0;
        GlobalMemberValues.INSERTDATAAFTERDELETE = 0;

        // Splash Use
        GlobalMemberValues.GLOBAL_SPLASHUSE = 0;         // 0 : 사용         1 : 사용안함
        // Download Data
        GlobalMemberValues.GLOBAL_DOWNLOADDATA = 1;      // 0 : 다운로드버튼 클릭시         1 : 앱 오픈시
        // Database Backup
        GlobalMemberValues.GLOBAL_DATABASEBACKUP = 0;      // 0 : 백업안함    1 : 텐더시   2 : 앱 종료시
        // Push 푸시메시지를 위한 App Instance 를 API 로 클라우드에 전송여부
        GlobalMemberValues.APPINSTANCEIDUP = 0;             // 0 : 미전송    1 : 전송완료
        // Push 팝업 카운트
        GlobalMemberValues.PUSH_POPUP_COUNT = 0;
        // App Instance ID
        GlobalMemberValues.APP_INSTANCE_ID = "";
        // MainActivity recreate 하는 경우 아래 메소드 실행여부
        // MainMiddleService.clearListView(), GlobalMemberValues.setCustomerInfoInit(), GlobalMemberValues.setEmployeeInfoInit()
        GlobalMemberValues.ITEMCANCELAPPLY = 0;             // 0 : 실행안함           1 : 실행함

        // Gmail 계정정보
        GlobalMemberValues.GMAILACCOUNT = "";         // @gmail.com 포함
        GlobalMemberValues.GMAILID = "";
        GlobalMemberValues.GMAILPWD = "";
        // ----------------------------------------------------------------------------------------

        if (mActivity != null) {
            mActivity.finish();
        }
        MainActivity.openIntroRealDemoChoice();
    }

    public static void initializeDB() {
        LogsSave.saveLogsInDB(155);
        Intent settingsSystemReset = new Intent(context.getApplicationContext(), SettingsSystemReset.class);
        mActivity.startActivity(settingsSystemReset);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    public static void syncRealSaleTimePrev() {
        new AlertDialog.Builder(mActivity)
                .setTitle("Sync. Real Sale Date")
                .setMessage("Would you like to change the sales records sold to actual sales time?\nData uploaded to the cloud will not be changed.\n(Some data may not change)")
                .setNegativeButton("Change all data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        syncRealSaleTime("all");
                    }
                })
                .setPositiveButton("Data change over last month", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        syncRealSaleTime("");
                    }
                }).show();
    }

    public static void syncRealSaleTime(final String paramType) {
        checkingProDial = new JJJCustomAnimationDialog(mActivity);
        checkingProDial.show();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                Vector<String> strQueryVec = new Vector<String>();

                String searchDate = "";
                if (GlobalMemberValues.isStrEmpty(paramType)) {
                    String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -30);
                    String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 1);
                    searchDate = " where strftime('%Y-%m-%d', wdate) between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
                }

                String strUpdateQuery = "";
                String strQuery = "select salesCode, wdate " +
                        " from salon_sales_receipt_json " +
                        searchDate +
                        " order by wdate asc ";
                Cursor salesDataCursor;
                salesDataCursor = dbInit.dbExecuteRead(strQuery);
                while (salesDataCursor.moveToNext()) {
                    String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salesDataCursor.getString(0), 1);
                    String tempSalesDate = GlobalMemberValues.getDBTextAfterChecked(salesDataCursor.getString(1), 1);

                    // salon_sales 테이블 수정
                    strUpdateQuery = " update salon_sales set saleDate = DATETIME('" + tempSalesDate + "') " +
                            " where salesCode = '" + tempSalesCode + "' ";
                    strQueryVec.addElement(strUpdateQuery);

                    // salon_sales_detail 테이블 수정
                    strUpdateQuery = " update salon_sales_detail set saleDate = DATETIME('" + tempSalesDate + "') " +
                            " where salesCode = '" + tempSalesCode + "' ";
                    strQueryVec.addElement(strUpdateQuery);
                }

                mFinalResult = "Y";

                // 트랜잭션으로 DB 처리한다.
                if (strQueryVec.size() > 0) {
                    String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
                    //GlobalMemberValues.logWrite("dbinsertlog", "returnResult : " + returnResult + "\n");
                    if (returnResult == "N" || returnResult == "") {
                        mFinalResult = "N";
                    } else {
                        mFinalResult = "Y";
                    }
                }
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                checkHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public static Handler checkHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            if (SettingsSystem.checkingProDial !=null && SettingsSystem.checkingProDial.isShowing()) {
                SettingsSystem.checkingProDial.cancel();
            }
            if (mFinalResult == "Y" || mFinalResult.equals("Y")) {
                GlobalMemberValues.displayDialog(context, "", "Successfully Updated", "Close");
            }

            mFinalResult = "N";
            // -------------------------------------------------------------------------------------
        }
    };

    public static void deleteSalesData() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                return;
            }

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 임시로 sales 데이터 삭제
            String tempDeleteDataSql = "delete from temp_salecart";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            String tempDeleteDataSql1 = "delete from salon_sales";
            strInsertQueryVec.addElement(tempDeleteDataSql1);
            String tempDeleteDataSql2 = "delete from salon_sales_detail";
            strInsertQueryVec.addElement(tempDeleteDataSql2);
            String tempDeleteDataSql3 = "delete from salon_sales_return";
            strInsertQueryVec.addElement(tempDeleteDataSql3);
            String tempDeleteDataSql4 = "delete from salon_sales_card";
            strInsertQueryVec.addElement(tempDeleteDataSql4);
            String tempDeleteDataSql5 = "delete from salon_sales_tip";
            strInsertQueryVec.addElement(tempDeleteDataSql5);
            String tempDeleteDataSql6 = "delete from salon_sales_batch";
            strInsertQueryVec.addElement(tempDeleteDataSql6);
            String tempDeleteDataSql7 = "delete from salon_sales_batch_json";
            strInsertQueryVec.addElement(tempDeleteDataSql7);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
            String returnValue = "";
            API_salesdatadelete_incloud apiSaleDelete = new API_salesdatadelete_incloud();
            apiSaleDelete.execute(null, null, null);
            try {
                Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                if (apiSaleDelete.mFlag) {
                    returnValue = apiSaleDelete.mReturnValue;
                }
            } catch (InterruptedException e) {
                returnValue = "";
            }

            String returnResult = "";

            if (returnValue.equals("00")) {
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                String returnSalesCode = "";
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
                } else {
                    GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");
                }
            } else {
                GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Cloud Error (Error Code : " + returnValue + ")", "Close");
            }
            //String returnResult5 = dbInit.dbExecuteWriteReturnValue(tempDeleteDataSql5);
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }

    }


    // 파일 다운로드 관련 ------------------------------------------------------------------------------------------------

    public void downloadFile() {
        String adImage = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdpimg from salon_storegeneral ");
        String adType = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdptype from salon_storegeneral ");
        if (GlobalMemberValues.isStrEmpty(adType)) {
            adType = "0";
        }

        if (GlobalMemberValues.isStrEmpty(adImage)) {
            GlobalMemberValues.displayDialog(context, "Warning", "No files to download", "Close");
            return;
        } else {
            //웹브라우저에 아래 링크를 입력하면 Alight.avi 파일이 다운로드됨.
            //final String fileURL = "http://nzqsradmin.wanhaye.com/stores_userimages/_100001357_GSFDT08418058.mp4";
            final String fileURL = adImage;

            path = GlobalMemberValues.ADFILELOCALPATH;

            if (adType == "0" || adType.equals("0")) {
                outputFile = new File(path, "ad.gif"); //파일명까지 포함함 경로의 File 객체 생성
            } else {
                outputFile = new File(path, "ad.mp4"); //파일명까지 포함함 경로의 File 객체 생성
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

                String adType = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdptype from salon_storegeneral ");
                if (GlobalMemberValues.isStrEmpty(adType)) {
                    adType = "0";
                }
                if (adType == "0" || adType.equals("0")) {
                    outputFile = new File(path, "ad.gif"); //파일명까지 포함함 경로의 File 객체 생성
                } else {
                    outputFile = new File(path, "ad.mp4"); //파일명까지 포함함 경로의 File 객체 생성
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

                Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(outputFile));
                sendBroadcast(mediaScanIntent);

                //reloadPresentation(outputFile.getPath());
                reloadPresentation();

            } else {
                Toast.makeText(getApplicationContext(), "Download Error.....", Toast.LENGTH_LONG).show();
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

    private void reloadPresentation() {
        GlobalMemberValues.dismissPresentationView();
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
    View.OnClickListener infomation_btn_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogsSave.saveLogsInDB(121);
            informationPopup = new InformationPopup(context,GlobalMemberValues.getAppVersionName(context),infomation_close_listener);
            informationPopup.show();
        }
    };
    View.OnClickListener infomation_close_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogsSave.saveLogsInDB(110);
            informationPopup.cancel();
        }
    };

    CompoundButton.OnCheckedChangeListener customerinfoviewSwitch_listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                customerinfoviewSwitch_type_ln.setVisibility(View.VISIBLE);
            } else {
                customerinfoviewSwitch_type_ln.setVisibility(View.INVISIBLE);
            }
        }
    };

    View.OnClickListener logSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.clockinoutTypeSwitch :
                    LogsSave.saveLogsInDB(126);
                    break;
                case R.id.downloadDataSwitch :
                    LogsSave.saveLogsInDB(127);
                    break;
                case R.id.cloudbackupintenderbackupynSwitch :
                    LogsSave.saveLogsInDB(129);
                    break;
                case R.id.gmailIdEditText:
                case R.id.gmailPwdEditText:
                    LogsSave.saveLogsInDB(130);
                    break;
                case R.id.employeecardstartnumEditText :
                case R.id.employeecardcountnumEditText :
                    LogsSave.saveLogsInDB(131);
                    break;
                case R.id.giftcardstartnumEditText:
                case R.id.giftcardcountnumEditText:
                    LogsSave.saveLogsInDB(132);
                    break;
                case R.id.pagernumberheadertxtEditText :
                    LogsSave.saveLogsInDB(133);
                    break;
                case R.id.productminalertynSwitch :
                    LogsSave.saveLogsInDB(134);
                    break;
                case R.id.daystouploaddataEv :
                    LogsSave.saveLogsInDB(135);
                    break;
                case R.id.batchincashoutynSwitch :
                    LogsSave.saveLogsInDB(136);
                    break;
                case R.id.startingcashprintynSwitch :
                    LogsSave.saveLogsInDB(137);
                    break;
                case R.id.saledatemodifyynSwitch :
                    LogsSave.saveLogsInDB(138);
                    break;
                case R.id.salehistorypagenoEv :
                    LogsSave.saveLogsInDB(139);
                    break;
                case R.id.buttonmodifierbottomviewynSwith :
                    LogsSave.saveLogsInDB(140);
                    break;
                case R.id.modifierpriceviewynSwith :
                    LogsSave.saveLogsInDB(141);
                    break;
                case R.id.modifierqtyviewynSwith :
                    LogsSave.saveLogsInDB(142);
                    break;
                case R.id.pricedollarynSwitch :
                    LogsSave.saveLogsInDB(143);
                    break;
                case R.id.thankyoupageynSwith :
                    LogsSave.saveLogsInDB(144);
                    break;
                case R.id.serverCode_useSwitch :
                    LogsSave.saveLogsInDB(145);
                    break;
                case R.id.serverPassword_useSwitch :
                    LogsSave.saveLogsInDB(146);
                    break;
                case R.id.pickuptypepopupuseynSwitch :
                    LogsSave.saveLogsInDB(147);
                    break;
                case R.id.printingcategoryynSwitch :
                    LogsSave.saveLogsInDB(148);
                    break;
                case R.id.openmsgwhendeletemenu_ynSwitch :
                    LogsSave.saveLogsInDB(149);
                    break;
                case R.id.otherpayprinting_ynSwitch :
                    LogsSave.saveLogsInDB(150);
                    break;
                case R.id.tipprintingwhentogo_ynSwitch :
                    LogsSave.saveLogsInDB(151);
                    break;
                case R.id.billprintpopupynSwitch :
                    LogsSave.saveLogsInDB(152);
                    break;
                case R.id.custombillsplituseynSwitch :
                    LogsSave.saveLogsInDB(153);
                    break;
            }

        }
    };

}
