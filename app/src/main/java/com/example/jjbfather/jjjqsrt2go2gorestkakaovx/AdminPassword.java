package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class AdminPassword extends Activity {
    final String TAG = "AdminPasswordLog";

    private LinearLayout parentLn;

    private Button adminPassword_suButton1,adminPassword_suButton2,adminPassword_suButton3;
    private Button adminPassword_suButton4,adminPassword_suButton5,adminPassword_suButton6;
    private Button adminPassword_suButton7,adminPassword_suButton8,adminPassword_suButton9;
    private Button adminPassword_suButton0,adminPassword_suButton00,adminPassword_suButtonBack;
    private Button adminPassword_suButtonV,adminPassword_suButtonX;

    private EditText adminPassword_qty;

    private TextView adminPasswordTitleTextView;

    TemporarySaleCart parentTemporarySaleCart;

    String mAdminPasswordEtValue = "";

    StringBuffer sb = new StringBuffer();

    String parentSelectedPosition;

    Intent mIntent;

    String mOpenClassMethod = "";
    String mPasswordType = "";
    String mOpenClass = "";

    Activity activity;
    Context context;

    public ProgressDialog masterpwdCheckProDial;

    public String mMasterPwd = "";
    // 로그인 뷰에서 받은 Extra 값.
    static Activity login_activity;
    static Context login_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_admin_password);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 3;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 6;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.adminPasswordLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        activity = this;
        context = this;

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 선택포시션 값
            mOpenClassMethod = mIntent.getStringExtra("openClassMethod");
            mPasswordType = mIntent.getStringExtra("passwordType");
            mOpenClass = mIntent.getStringExtra("openClassName");
            GlobalMemberValues.logWrite(TAG, "openClassMethod : " + mOpenClassMethod + "\n");
            GlobalMemberValues.logWrite(TAG, "mPasswordType : " + mPasswordType + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }
        
        if (mOpenClass == null) mOpenClass = "";

    }

    public void setLayoutContent() {
        int numberBackground = R.drawable.ab_imagebutton_adminpassword_number;
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            numberBackground = R.drawable.ab_imagebutton_discountextra_number_lite;
            GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR = "#0054d5";
        }


        // 네트워크 상태 체크
        // GlobalMemberValues.checkOnlineService(context, activity);

        adminPasswordTitleTextView = (TextView)findViewById(R.id.adminPasswordTitleTextView);
        adminPasswordTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + adminPasswordTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        adminPassword_suButton1 = (Button)findViewById(R.id.adminPassword_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton1.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton2 = (Button)findViewById(R.id.adminPassword_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton2.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton3 = (Button)findViewById(R.id.adminPassword_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton3.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton4 = (Button)findViewById(R.id.adminPassword_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton4.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton5 = (Button)findViewById(R.id.adminPassword_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton5.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton6 = (Button)findViewById(R.id.adminPassword_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton6.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton7 = (Button)findViewById(R.id.adminPassword_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton7.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton8 = (Button)findViewById(R.id.adminPassword_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton8.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton9 = (Button)findViewById(R.id.adminPassword_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton9.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton0 = (Button)findViewById(R.id.adminPassword_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton0.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButton00 = (Button)findViewById(R.id.adminPassword_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            adminPassword_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            adminPassword_suButton00.setBackgroundResource(numberBackground);
        } else {
            adminPassword_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButtonBack = (Button)findViewById(R.id.adminPassword_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButtonBack.setText("");
            adminPassword_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_delete);
        } else {
            adminPassword_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButtonV = (Button)findViewById(R.id.adminPassword_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButtonV.setText("");
            adminPassword_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_enter);
        } else {
            adminPassword_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        adminPassword_suButtonX = (Button)findViewById(R.id.adminPassword_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            adminPassword_suButtonX.setText("");
            adminPassword_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_close);
        } else {
            adminPassword_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    adminPassword_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        adminPassword_suButton1.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton2.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton3.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton4.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton5.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton6.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton7.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton8.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton9.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton0.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButton00.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButtonBack.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButtonV.setOnClickListener(adminPasswordButtonClickListener);
        adminPassword_suButtonX.setOnClickListener(adminPasswordButtonClickListener);

        adminPassword_qty = (EditText)findViewById(R.id.adminPassword_qty);
        adminPassword_qty.setOnTouchListener(mTouchadminPasswordTvTouchListener);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), adminPassword_qty);
    }

    View.OnClickListener adminPasswordButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.adminPassword_suButton1 : {
                    numberButtonClick(adminPassword_suButton1);
                    break;
                }
                case R.id.adminPassword_suButton2 : {
                    numberButtonClick(adminPassword_suButton2);
                    break;
                }
                case R.id.adminPassword_suButton3 : {
                    numberButtonClick(adminPassword_suButton3);
                    break;
                }
                case R.id.adminPassword_suButton4 : {
                    numberButtonClick(adminPassword_suButton4);
                    break;
                }
                case R.id.adminPassword_suButton5 : {
                    numberButtonClick(adminPassword_suButton5);
                    break;
                }
                case R.id.adminPassword_suButton6 : {
                    numberButtonClick(adminPassword_suButton6);
                    break;
                }
                case R.id.adminPassword_suButton7 : {
                    numberButtonClick(adminPassword_suButton7);
                    break;
                }
                case R.id.adminPassword_suButton8 : {
                    numberButtonClick(adminPassword_suButton8);
                    break;
                }
                case R.id.adminPassword_suButton9 : {
                    numberButtonClick(adminPassword_suButton9);
                    break;
                }
                case R.id.adminPassword_suButton0 : {
                    numberButtonClick(adminPassword_suButton0);
                    break;
                }
                case R.id.adminPassword_suButton00 : {
                    numberButtonClick(adminPassword_suButton00);
                    break;
                }
                case R.id.adminPassword_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mAdminPasswordEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mAdminPasswordEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mAdminPasswordEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mAdminPasswordEtValue)) {
                            mAdminPasswordEtValue = "";
                        }
                    } else {
                        mAdminPasswordEtValue = "";
                    }
                    adminPassword_qty.setText(mAdminPasswordEtValue);
                    break;
                }
                case R.id.adminPassword_suButtonV : {
                    checkAdminPwdType();
                    break;
                }
                case R.id.adminPassword_suButtonX : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
            }
        }
    };

    public void checkMasterPwdFromCloud() {
        // 프로그래스 바를 실행~
        masterpwdCheckProDial = ProgressDialog.show(
                context, "Master Check", "Master Check In Cloud.....", true, false
        );
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                String masterpwd = GlobalMemberValues.getMasterPwdAfterCloudCheck(context, GlobalMemberValues.SALON_CODE);
                if (GlobalMemberValues.isStrEmpty(masterpwd)) {
                    masterpwd = GlobalMemberValues.MASTER_PWD;
                }
                GlobalMemberValues.logWrite(TAG, "cloud masterpwd : " + masterpwd + "\n");
                mMasterPwd = masterpwd;
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                masterPwdcheckhandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler masterPwdcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            GlobalMemberValues.logWrite(TAG, "mMasterPwd : " + mMasterPwd + "\n");
            setExecuteClassMethod();
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            masterpwdCheckProDial.dismiss();
            // -------------------------------------------------------------------------------------
        }
    };

    public void checkAdminPwdType() {
        if (mPasswordType !=null && mPasswordType.equals("masterpwd")) {
            checkMasterPwdFromCloud();
        } else {
            setExecuteClassMethod();
        }
    }

    public void setExecuteClassMethod() {
        String tempPassword = adminPassword_qty.getText().toString();
        String tempAdminPassword = GlobalMemberValues.STORE_ADMIN_PWD;
        if (GlobalMemberValues.isStrEmpty(tempAdminPassword)) {
            tempAdminPassword = "101216";
        }
        if (mPasswordType !=null && mPasswordType.equals("masterpwd")) {
            GlobalMemberValues.logWrite(TAG, "mMasterPwd : " + mMasterPwd + "\n");
            tempAdminPassword = mMasterPwd;
        } else {
            GlobalMemberValues.logWrite("adminpwdlog", "여기진입..." + "\n");

            // 로그인 페이지에서 관리자로 로그인했을 경우에만...
            if (!mOpenClassMethod.equals("admin_login")
                    && GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName != null && GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx != null) {
                // 관리자가 아닌 일반 직원일 때...
                String tempEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                String tempEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                if (!GlobalMemberValues.isStrEmpty(tempEmpName) && !tempEmpName.equals("ADMIN")) {
                    String tempEmpPwd = MainActivity.mDbInit.dbExecuteReadReturnString(
                            "select clockinoutpwd from salon_storeemployee where idx = '" + tempEmpIdx + "' ");
                    if (!GlobalMemberValues.isStrEmpty(tempEmpPwd)) {
                        tempAdminPassword = tempEmpPwd;
                    } else {
                        tempAdminPassword = "101216";
                    }
                }
                GlobalMemberValues.logWrite("adminpwdlog", "tempPassword : " + tempPassword + "\n");
                GlobalMemberValues.logWrite("adminpwdlog", "tempAdminPassword : " + tempAdminPassword + "\n");
            }
        }

        // NavyZ 용 POS 비밀번호
        String passwordForNZ = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select managerpwdnz from salon_storegeneral where sidx = '" + GlobalMemberValues.STORE_INDEX + "' "
        );
        if (GlobalMemberValues.isStrEmpty(passwordForNZ)) {
            passwordForNZ = "0904";
        }
        if (tempPassword.equals(tempAdminPassword) || tempPassword.equals(passwordForNZ)) {
            switch (mOpenClassMethod) {
                case "command_download" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(19);
                    } else if (mOpenClass.equals("commandbutton")){
                        LogsSave.saveLogsInDB(209);
                    }
                    CommandButton.setDownloadOutside();
                    break;
                }
                case "command_taxexempt" : {
                    CommandButton.setTaxExemptOutside();
                    break;
                }
                case "command_cloud" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(23);
                    } else if (mOpenClass.equals("commandbutton")){
                        LogsSave.saveLogsInDB(217);
                    }
                    CommandButton.setCloudOpenOutside();
                    break;
                }
                case "command_reservation" : {
                    CommandButton.setReservationOpen();
                    break;
                }
                case "command_backofficemain" : {
                    CommandButton.setBackOfficeMainOpen();
                    break;
                }
                case "system_reset" : {
                    SettingsSystem.setResetApp();
                    break;
                }
                case "salehistory_return" : {
                    SaleHistory.setReturnOpen();
                    break;
                }
                case "salehistoryweb_delivering" : {
                    SaleHistory_web.setChangeStatus("1");
                    break;
                }
                case "salehistory_delivering" : {
                    SaleHistory.setChangeStatus("1");
                    break;
                }
                case "salehistoryweb_done" : {
                    SaleHistory_web.setChangeStatus("2");
                    break;
                }
                case "salehistory_done" : {
                    SaleHistory.setChangeStatus("2");
                    break;
                }
                case "salehistoryweb_return" : {
                    SaleHistory_web.setReturnProcessing();
                    break;
                }
                case "salehistoryweb_pickupdone" : {
                    SaleHistory_web.setChangeStatus("3");
                    break;
                }
                case "command_databasebackup" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(20);
                    } else if (mOpenClass.equals("commandbutton")){
                        LogsSave.saveLogsInDB(219);
                    }
                    CommandButton.backupDatabase(true);
                    break;
                }
                case "command_databaserestore" : {
                    LogsSave.saveLogsInDB(220);
                    CommandButton.restoreDatabaseAfterCheckCloudBackup();
                    break;
                }
                case "settingssystem_salesdelete" : {
                    //SettingsSystem.deleteSalesData();
                    SettingsSystem.initializeDB();
                    break;
                }
                case "syncrealsaletime" : {
                    //SettingsSystem.deleteSalesData();
                    SettingsSystem.syncRealSaleTimePrev();
                    break;
                }
                case "apptopbar_settings" : {
                    AppTopBar.openSettings();
                    break;
                }
                case "command_batchsummary" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(30);
                    } else if (mOpenClass.equals("commandbutton")){
                        LogsSave.saveLogsInDB(214);
                    }
                    CommandButton.openBatchSummary("N");
                    break;
                }
                case "admin_login" : {
                    Employee_Login.openAdminLogin();
                    break;
                }
                case "endofday" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(29);
                    } else if (mOpenClass.equals("commandbutton")){

                    }
                    Employee_Login.openEndOfDay("N");
                    break;
                }
                case "batchsummary_syncmanualbatch" : {
                    BatchSummary.callSyncAfterManualBatch();
                    break;
                }
                case "login_databaserestore" : {
                    if (mOpenClass.equals("backofficecommand")){
                        LogsSave.saveLogsInDB(21);
                    } else if (mOpenClass.equals("commandbutton")){

                    }
                    BackOfficeCommand.restoreDatabaseAfterCheckCloudBackup();
                }
                case "login_db_compack" : {
                    Employee_Login.login_Db_Compack();
                }
                case "alltableclear" : {
                    Intent intentR = new Intent();
                    intentR.putExtra("result" , "pass_ok"); //사용자에게 입력받은값 넣기
                    setResult(RESULT_OK,intentR);
                }
                case "recall_delete_order" : {
                    setResult(RESULT_OK);
                }
                break;
            }
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        } else {
            GlobalMemberValues.displayDialog(this, "Warning", "Wrong Password", "Close");
            mAdminPasswordEtValue = "";
            adminPassword_qty.setText("");
        }
    }

    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        if (mAdminPasswordEtValue.length() < 8) {
            sb.append(mAdminPasswordEtValue).append(btn.getText().toString());
//            int tempNumber = Integer.parseInt(sb.toString());
//            mAdminPasswordEtValue = String.valueOf(tempNumber);
            mAdminPasswordEtValue = sb.toString();
            adminPassword_qty.setText(mAdminPasswordEtValue);
        }
    }

    View.OnTouchListener mTouchadminPasswordTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            adminPassword_qty.setFocusable(true);
            adminPassword_qty.selectAll();
            // 키보드 안보이게
            GlobalMemberValues.setKeyPadHide(getApplication(), adminPassword_qty);
            //adminPassword_qty.hasFocus();
            return true;
        }
    };

}
