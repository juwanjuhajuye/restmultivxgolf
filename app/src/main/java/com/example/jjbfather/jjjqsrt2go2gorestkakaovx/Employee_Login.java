package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripeCardParser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Employee_Login extends Activity {
    static Activity mActivity;
    static Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private RelativeLayout parentLn;

    LinearLayout closBtnLn;

    Intent mIntent;

    static TextView employeeLoginTitleTextView1, employeeLoginTitleTextView2;

    TextView emplogintv1, emplogintv2, emplogintv3, emplogintv4;

    TextView txv_version_num;

    TextView loginNtepccinfotv;

    ImageView emplogin_imagetitle;

    ImageButton btn_image_infomation;
    InformationPopup informationPopup;
    public static TextView employeeLoginEmpSelectTextView;

    EditText employeeLoginPasswordEditText;
    Button employeeLoginQuitButton ;
    ImageButton employeeLoginButton;
    LinearLayout login_backoffice_command, login_dbcompack;

    // 추가버튼
    LinearLayout adminloginBtn, clockinoutBtn, endofdayBtn;


    String mReturnValue = "";

    static ProgressDialog mProgressDialog;

    Button emplogin_number1, emplogin_number2, emplogin_number3, emplogin_number4, emplogin_number5;
    Button emplogin_number6, emplogin_number7, emplogin_number8, emplogin_number9, emplogin_number0, emplogin_number00;
    Button emplogin_number_back, emplogin_number_close, emplogin_number_done;
    Button login_del_dbcompack;
    StringBuffer sb = new StringBuffer();
    static String mServerNum = "";

    static CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        if (GlobalMemberValues.isLiteVersion()) {
            setContentView(R.layout.activity_emploee_login_lite);
        } else {
            setContentView(R.layout.activity_emploee_login);
        }
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;
        dbInit = new DatabaseInit(mContext);

        parentLn = (RelativeLayout) findViewById(R.id.employeeLoginChoiceLinearLayout);

        /**
         int parentLnWidth = (GlobalMemberValues.getDisplayWidth(mContext) / 10) * 10;
         int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(mContext) / 10) * 10;
         if (GlobalMemberValues.thisTabletRealHeight < 800) {
         parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(mContext) / 100) * 10;
         }
         LinearLayout.LayoutParams parentLnParams
         = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
         parentLnParams.setMargins(0, 0, 0, 0);
         parentLn.setLayoutParams(parentLnParams);
         **/

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            //mOpenFrom = mIntent.getStringExtra("openfrom");
            //GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            GlobalMemberValues.isEmployeeLoginPopup = false;
            GlobalMemberValues.isOpenedTimeMenuPopup = false;
            closeEmployeeLoginActivity();
        }

        setContents();
    }

    public void setContents() {
        GlobalMemberValues.tablesalemain_open_cnt = 0;

        int tempReturnInt = MainActivity.setBasicInformationInit();
        if (tempReturnInt == 0) {
            GlobalMemberValues.isEmployeeLoginPopup = false;
            GlobalMemberValues.isOpenedTimeMenuPopup = false;

            closeEmployeeLoginActivity();

            MainActivity.openIntroRealDemoChoice();
            return;
        }

        GlobalMemberValues.isEmployeeLoginPopup = true;

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // MSR 활성화
        //magSwiper(mActivity, mContext);

        closBtnLn = (LinearLayout)findViewById(R.id.closBtnLn);
        parentLn.bringChildToFront(closBtnLn);

        emplogintv1 = (TextView)findViewById(R.id.emplogintv1);
        emplogintv1.setTextSize(emplogintv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        emplogintv2 = (TextView)findViewById(R.id.emplogintv2);
        emplogintv2.setTextSize(emplogintv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        emplogintv3 = (TextView)findViewById(R.id.emplogintv3);
        emplogintv3.setTextSize(emplogintv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        emplogintv4 = (TextView)findViewById(R.id.emplogintv4);
        emplogintv4.setTextSize(emplogintv4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        employeeLoginTitleTextView1 = (TextView)findViewById(R.id.employeeLoginTitleTextView1);
        employeeLoginTitleTextView1.setTextSize(employeeLoginTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        employeeLoginTitleTextView2 = (TextView)findViewById(R.id.employeeLoginTitleTextView2);
        employeeLoginTitleTextView2.setTextSize(employeeLoginTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        employeeLoginEmpSelectTextView = (TextView)findViewById(R.id.employeeLoginEmpSelectTextView);
        employeeLoginEmpSelectTextView.setTextSize(employeeLoginEmpSelectTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        employeeLoginPasswordEditText = (EditText)findViewById(R.id.employeeLoginPasswordEditText);

        employeeLoginQuitButton = (Button)findViewById(R.id.employeeLoginQuitButton);

        employeeLoginButton = (ImageButton) findViewById(R.id.employeeLoginButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//            employeeLoginButton.setText("");
//            employeeLoginButton.setBackgroundResource(R.drawable.ab_imagebutton_emp_login);
        } else {
//            employeeLoginButton.setTextSize(
//                    employeeLoginButton.getTextSize()
//                            * GlobalMemberValues.getGlobalFontSize()
//            );
        }

        login_del_dbcompack = (Button)findViewById(R.id.login_del_dbcompack);
        login_del_dbcompack.setOnClickListener(loginBtnClickListener);

        adminloginBtn = (LinearLayout)findViewById(R.id.adminloginBtn);
        clockinoutBtn = (LinearLayout)findViewById(R.id.clockinoutBtn);
        endofdayBtn = (LinearLayout)findViewById(R.id.endofdayBtn);
        login_backoffice_command = (LinearLayout)findViewById(R.id.login_backoffice_command);
        login_dbcompack = (LinearLayout)findViewById(R.id.login_dbcompack);

        employeeLoginEmpSelectTextView.setOnClickListener(loginBtnClickListener);
        employeeLoginQuitButton.setOnClickListener(loginBtnClickListener);
        employeeLoginButton.setOnClickListener(loginBtnClickListener);

        adminloginBtn.setOnClickListener(loginBtnClickListener);
        clockinoutBtn.setOnClickListener(loginBtnClickListener);
        endofdayBtn.setOnClickListener(loginBtnClickListener);
        login_backoffice_command.setOnClickListener(loginBtnClickListener);
        login_dbcompack.setOnClickListener(loginBtnClickListener);

        //
        txv_version_num = (TextView)findViewById(R.id.emplogin_info_version_num);
        txv_version_num.setText(GlobalMemberValues.getAppVersionName(mContext));
        txv_version_num.setOnClickListener(loginBtnClickListener);

        btn_image_infomation = (ImageButton)findViewById(R.id.emplogin_info_btn);
        btn_image_infomation.setOnClickListener(loginBtnClickListener);


        loginNtepccinfotv = (TextView)findViewById(R.id.loginNtepccinfotv);
        loginNtepccinfotv.setTextSize(loginNtepccinfotv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        emplogin_imagetitle = (ImageView) findViewById(R.id.emplogin_imagetitle);
        if (GlobalMemberValues.CLOUD_SERVER_URL_BASIC.indexOf("2go2go") != -1) {
            emplogin_imagetitle.setImageResource(R.drawable.aa_images_salonposlogo_2go2go);
        }

        emplogin_number1 = (Button)findViewById(R.id.emplogin_number1);
        emplogin_number2 = (Button)findViewById(R.id.emplogin_number2);
        emplogin_number3 = (Button)findViewById(R.id.emplogin_number3);
        emplogin_number4 = (Button)findViewById(R.id.emplogin_number4);
        emplogin_number5 = (Button)findViewById(R.id.emplogin_number5);
        emplogin_number6 = (Button)findViewById(R.id.emplogin_number6);
        emplogin_number7 = (Button)findViewById(R.id.emplogin_number7);
        emplogin_number8 = (Button)findViewById(R.id.emplogin_number8);
        emplogin_number9 = (Button)findViewById(R.id.emplogin_number9);
        emplogin_number0 = (Button)findViewById(R.id.emplogin_number0);
        emplogin_number00 = (Button)findViewById(R.id.emplogin_number00);
        emplogin_number_back = (Button)findViewById(R.id.emplogin_number_back);
        emplogin_number_close = (Button)findViewById(R.id.emplogin_number_close);
        emplogin_number_done = (Button)findViewById(R.id.emplogin_number_done);
        emplogin_number1.setOnClickListener(login_numberClicklistener);
        emplogin_number2.setOnClickListener(login_numberClicklistener);
        emplogin_number3.setOnClickListener(login_numberClicklistener);
        emplogin_number4.setOnClickListener(login_numberClicklistener);
        emplogin_number5.setOnClickListener(login_numberClicklistener);
        emplogin_number6.setOnClickListener(login_numberClicklistener);
        emplogin_number7.setOnClickListener(login_numberClicklistener);
        emplogin_number8.setOnClickListener(login_numberClicklistener);
        emplogin_number9.setOnClickListener(login_numberClicklistener);
        emplogin_number0.setOnClickListener(login_numberClicklistener);
        emplogin_number00.setOnClickListener(login_numberClicklistener);
        emplogin_number_back.setOnClickListener(login_numberClicklistener);
        emplogin_number_close.setOnClickListener(login_numberClicklistener);
        emplogin_number_done.setOnClickListener(login_numberClicklistener);

        setInit();


        // 07182024
        // 카드결제 기기등록관련
        pgIpDataMake();
    }

    View.OnClickListener loginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.employeeLoginQuitButton : {
                    LogsSave.saveLogsInDB(6);
                    GlobalMemberValues.setCloseAndroidApp(mActivity);

                    break;
                }
                case R.id.employeeLoginEmpSelectTextView : {
//                    if (EmployeeSelectionPopup.openCount == 0) {
//                        Intent employeeSelectionPopup = new Intent(mContext.getApplicationContext(), EmployeeSelectionPopup.class);
//                        mActivity.startActivity(employeeSelectionPopup);
//                        if (GlobalMemberValues.isUseFadeInOut()) {
//                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
//                        }
//                    }

                    break;
                }
                case R.id.adminloginBtn : {
                    Intent adminPasswordIntent = new Intent(mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "admin_login");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.clockinoutBtn : {
                    LogsSave.saveLogsInDB(17);
                    // clockinout 의 타입을 구한다. (0 : 오프라인   1 : 온라인)
                    String strQuery = "select clockinouttype from salon_storestationsettings_system ";
                    String tempClockInOutType = dbInit.dbExecuteReadReturnString(strQuery);

                    Intent clockInOutIntent;
                    if (GlobalMemberValues.getIntAtString(tempClockInOutType) == 0) {
                        clockInOutIntent = new Intent(mContext.getApplicationContext(), ClockInOut.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                        clockInOutIntent.putExtra("clockInOutValue", "");
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(clockInOutIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    } else {
                        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                            if (!GlobalMemberValues.isOnline().equals("00")) {
                                GlobalMemberValues.showDialogNoInternet(mContext);
                                return;
                            }
                            clockInOutIntent = new Intent(mContext.getApplicationContext(), ClockInOutNavtiveWeb.class);
                            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                            clockInOutIntent.putExtra("clockInOutValue", "");
                            // -------------------------------------------------------------------------------------
                            mActivity.startActivity(clockInOutIntent);
                            if (GlobalMemberValues.isUseFadeInOut()) {
                                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                            }
                        } else {
                            GlobalMemberValues.openNetworkNotConnected();
                        }
                    }

                    break;
                }
                case R.id.endofdayBtn : {
                    // 권한체크
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null) {
                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO = new TemporaryEmployeeInfo();
                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName = "ADMIN";
                    }
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx == null) GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName == null) GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName = "ADMIN";

                    if (!GlobalMemberValues.checkEmployeePermission(
                            GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<11>")) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent = new Intent(mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "endofday");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.employeeLoginButton : {
                    loginEmployee();

                    break;
                }
                case R.id.login_backoffice_command : {
                    LogsSave.saveLogsInDB(15);
                    Intent backOfficeCommand = new Intent(mContext.getApplicationContext(), BackOfficeCommand.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // 03102018
                    // -------------------------------------------------------------------------------------
                    startActivity(backOfficeCommand);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }

                    break;
                }
                case R.id.emplogin_info_btn : {
                    LogsSave.saveLogsInDB(11);
                    informationPopup = new InformationPopup(mContext, GlobalMemberValues.getAppVersionName(mContext),information_close_listener);
                    informationPopup.show();

                    break;
                }

                case R.id.emplogin_info_version_num : {
                    informationPopup = new InformationPopup(mContext, GlobalMemberValues.getAppVersionName(mContext),information_close_listener);
                    informationPopup.show();

                    break;
                }
                case R.id.login_dbcompack : {
                    Intent adminPasswordIntent = new Intent(mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "login_db_compack");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.login_del_dbcompack : {
                    new AlertDialog.Builder(Employee_Login.mActivity)
                            .setTitle("NZ ANDROID")
                            .setMessage("Are you sure to delete compact backup database?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(2);
                                            Thread thread = new Thread(new Runnable() {
                                                public void run() {
                                                    Employee_Login.itemdownhandler.sendEmptyMessage(0);
                                                    // ---------------------------------------------------------------------------------
                                                }
                                            });
                                            thread.start();
//                                GlobalMemberValues.startDatabaseCheckBackup();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        }
    };

    View.OnClickListener login_numberClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            employeeLoginEmpSelectTextView.setTextColor(Color.BLACK);
            switch (v.getId()) {
                case R.id.emplogin_number1 : {
                    numberButtonClick(emplogin_number1);
                    LogsSave.saveLogsInDB(7);
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number2 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number2);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number3 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number3);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number4 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number4);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number5 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number5);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number6 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number6);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number7 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number7);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number8 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number8);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number9 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number9);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.emplogin_number0 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number0);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }

                case R.id.emplogin_number00 : {
                    LogsSave.saveLogsInDB(7);
                    numberButtonClick(emplogin_number00);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }

                case R.id.emplogin_number_back : {
                    LogsSave.saveLogsInDB(8);
                    sb.delete(0, sb.toString().length());
                    sb.append(mServerNum);
                    if (!GlobalMemberValues.isStrEmpty(mServerNum)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mServerNum = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mServerNum)) {
                            mServerNum = "";
                        }
                    } else {
                        mServerNum = "";
                    }
                    employeeLoginEmpSelectTextView.setText(mServerNum);
                    break;
                }

                case R.id.emplogin_number_done: {
                    LogsSave.saveLogsInDB(10);
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        //로딩창 객체 생성
                        customProgressDialog = new CustomProgressDialog(mContext);
                        //로딩창을 투명하게
                        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        customProgressDialog.show();
                    }

                    goNextStep();

                    break;
                }

                case R.id.emplogin_number_close : {
//                    closeActivity();
                    LogsSave.saveLogsInDB(9);
                    break;
                }
//
//                case R.id.emptyLinearLayout : {
//                    closeActivity();
//                    break;
//                }

            }
        }
    };

    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        if (mServerNum.length() < 8) {
            sb.append(mServerNum).append(btn.getText().toString());
            int tempNumber = Integer.parseInt(sb.toString());
            mServerNum = String.valueOf(tempNumber);
//            employeeLoginEmpSelectTextView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            employeeLoginEmpSelectTextView.setText(mServerNum);
        }
    }

    public static void openEndOfDay(String paramAutoCloseYN) {
        LogsSave.saveLogsInDB(18);
        try {
            Intent endofdayIntent = new Intent(mActivity, EndOfDay.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            endofdayIntent.putExtra("autocloseyn", paramAutoCloseYN);
            // -------------------------------------------------------------------------------------
            MainActivity.mActivity.startActivity(endofdayIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
            GlobalMemberValues.logWrite("batchsummaryjjj", "여기실행...2" + "\n");
        } catch (Exception e) {
            GlobalMemberValues.logWrite("batchsummaryjjj", "여기실행...3" + "\n");
            GlobalMemberValues.logWrite("batchsummaryjjj", "에러메시지 : " + e.getMessage().toString() + "\n");
        }
    }

    public void loginEmployee() {
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Choose a employee", "Close");
        } else {
            String getPassword = employeeLoginPasswordEditText.getText().toString();
            if (!GlobalMemberValues.isStrEmpty(getPassword)) {

                /**
                 try {
                 getPassword = GlobalMemberValues.getEncodingAsMD5(getPassword);
                 } catch (Exception e) {
                 e.printStackTrace();
                 }
                 **/

                String savedPassword = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empPwd;
                GlobalMemberValues.logWrite("employeeloginpwdlog", "getPwd : " + getPassword + "\n");
                GlobalMemberValues.logWrite("employeeloginpwdlog", "savedPwd : " + savedPassword + "\n");
                if (savedPassword.equals(getPassword)) {
                    GlobalMemberValues.isEmployeeLoginPopup = false;
                    GlobalMemberValues.isOpenedTimeMenuPopup = false;

                    // 타임메뉴 셋
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.timeMenu_getServiceTime();

                    GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;
                    Intent intent = new Intent();   // table sale add
                    mActivity.setResult(Activity.RESULT_OK,intent); // table sale add
                    closeEmployeeLoginActivity();

                    // 타임메뉴 설정된대로 실행.
                    GlobalMemberValues.openTimeMenuSelectPopupAuto();

                    GlobalMemberValues.logWrite("LOGIN_EMPLOYEE_ID_LOG", "GlobalMemberValues.LOGIN_EMPLOYEE_ID1 : " + GlobalMemberValues.LOGIN_EMPLOYEE_ID + "\n");
                    GlobalMemberValues.logWrite("LOGIN_EMPLOYEE_ID_LOG", "CashInOutPopup.getCashInout()1 : " + CashInOutPopup.getCashInout() + "\n");
                    loginDBInsert();
                } else {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Invalid password", "Close");
                }
            } else {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Enter password", "Close");
            }
        }
    }

    public static void finishLoginEmployee() {
        GlobalMemberValues.isEmployeeLoginPopup = false;
        GlobalMemberValues.isOpenedTimeMenuPopup = false;

        GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;
        closeEmployeeLoginActivity();

        loginDBInsert();
    }

    public static void loginDBInsert() {
        Vector<String> strInsertQueryVec = new Vector<String>();

        String sqlQuery = "insert into salon_employee_loginout_history ( " +
                " scode, sidx, stcode, employeeIdx, employeeName, loginouttype " +
                " ) values ( " +
                " '" + GlobalMemberValues.SALON_CODE + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "', " +
                " '0' " +
                " ) ";

        strInsertQueryVec.addElement(sqlQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("EmpLoginLog", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
        } else {
            if (GlobalMemberValues.isCashInOutPossible()){
                openCashInOutPopup();
            }

            // 프렌차이즈인지 여부를 설정한다.
            GlobalMemberValues.setComFranchise();

        }
    }

    public static void openAdminLogin() {
        LogsSave.saveLogsInDB(16);
        GlobalMemberValues.LOGIN_EMPLOYEE_ID = "Admin";

        GlobalMemberValues.setCustomerInfoInit();
        GlobalMemberValues.setEmployeeInfoInit();
        GlobalMemberValues.ITEMCANCELAPPLY = 0;
        MainMiddleService.mHoldCode = "NOHOLDCODE";

        GlobalMemberValues.isEmployeeLoginPopup = false;
        GlobalMemberValues.isOpenedTimeMenuPopup = false;

        closeEmployeeLoginActivity();
    }

    public static void closeEmployeeLoginActivity() {
        GlobalMemberValues.isOpenTableSaleMain = true;

        Employee_Login.mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            Employee_Login.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public static void openCashInOutPopup() {
        GlobalMemberValues.openCashInOutPopup(mActivity, mContext.getApplicationContext());
    }

    public void magSwiper(final Activity paramActivity, final Context paramContext) {
        paramActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MagStripDriver magStripDriver;

                magStripDriver = new MagStripDriver(MainActivity.mContext);
                magStripDriver.startDevice(); //Start the MagStripe Reader
                magStripDriver.registerMagStripeListener(new MagStripDriver.MagStripeListener() { //MageStripe Reader's Listener for notifying various events.

                    @Override
                    public void OnDeviceDisconnected() { //Fired when the Device has been Disconnected.
                        //  Toast.makeText(getActivity(), "Magnetic-Stripe Device Disconnected !", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnDeviceConnected() { //Fired when the Device has been Connected.
                        // TODO Auto-generated method stub
                        // Toast.makeText(getActivity(), "Magnetic-Stripe Device Connected !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCardSwiped(String cardData) { //Fired when a card has been swiped on the device.
                        try {
                            MagStripeCardParser mParser = new MagStripeCardParser(cardData); //Instance of card swipe reader
                            if(mParser.isDataParse()){
                                if(mParser.hasTrack1()){
                                    String getMSRData = mParser.getAccountNumber();
                                    getMSRData = GlobalMemberValues.getMSRCardNumber("employee", getMSRData);
                                    //GlobalMemberValues.displayDialog(paramContext, "Employee Info", "getMSRData : " + getMSRData, "Close");

                                    /**
                                     if(getMSRData.length() > 4) {
                                     getMSRData = getMSRData.substring(getMSRData.length() - 4);
                                     } else {
                                     if(getMSRData.length() < 4) {
                                     String tempStr = "";
                                     for (int i = 0; i < (4 - getMSRData.length()); i++) {
                                     tempStr += "0";
                                     }
                                     getMSRData = tempStr + getMSRData;
                                     }
                                     }
                                     **/


                                    String getEmployeeInfo = getEmployeeInfoByEmpCardNum(getMSRData);
                                    if (GlobalMemberValues.isStrEmpty(getEmployeeInfo)) {
                                        GlobalMemberValues.displayDialog(paramContext, "Employee Info", "There is no employee data", "Close");
                                        return;
                                    }

                                    //GlobalMemberValues.displayDialog(paramContext, "Employee Info", "getEmployeeInfo : " + getEmployeeInfo, "Close");

                                    // MSR 스윕하여 가져온 직원데이터로 직원변수에 저장
                                    EmployeeSelectionPopup.setSelectedEmployee(paramContext, getEmployeeInfo, "N");

                                    GlobalMemberValues.setFrameLayoutVisibleChange("main");

                                    // 로그인 처리
                                    Employee_Login.finishLoginEmployee();

                                    //paramTextView.setText(getMSRData); //Populate the edittext with the card number
                                }
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String getEmployeeInfoByEmpCardNum(String paramEmpCardNum) {
        String returnData = "";

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.clockinoutpwd, a.eLevel, a.sidx, a.commissionratio " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.empcardnum = '" + paramEmpCardNum + "' ";

        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor empCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (empCursor.getCount() > 0 && empCursor.moveToFirst()) {
            returnData = empCursor.getInt(0) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(1), 1) + "||" +
                    empCursor.getInt(2) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(3), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(4), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(5), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(6), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(7), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(8), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(9), 1) + "||" +
                    GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(10), 1) + "||" +
                    String.valueOf(empCursor.getInt(11)) + "||" +
                    tempCommissionratio;

            GlobalMemberValues.logWrite("getemployeeInfo", "데이터 : " + returnData + "\n");
        }

        return returnData;
    }

    public static void login_Db_Compack(){

        // back dialog
        mProgressDialog = ProgressDialog.show(
                mContext, "DB Compact", "Please wait until database backup is completed", true, false);
        mProgressDialog.show();
        // 용량 체크 없음.
        new AlertDialog.Builder(Employee_Login.mActivity)
                .setTitle("NZ ANDROID")
                .setMessage("Are you sure to compact the database?")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LogsSave.saveLogsInDB(14);
                                Thread thread = new Thread(new Runnable() {
                                    public void run() {
                                        // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                        GlobalMemberValues.startDatabaseCheckBackup();
                                        // ---------------------------------------------------------------------------------
                                        // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                        Employee_Login.itemdownhandler.sendEmptyMessage(0);
                                        // ---------------------------------------------------------------------------------
                                    }
                                });
                                thread.start();
//                                GlobalMemberValues.startDatabaseCheckBackup();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Employee_Login.itemdownhandler.sendEmptyMessage(0);
                    }
                })
                .setCancelable(false)
                .show();
    }

    // 초기화 메소드
    private void setInit() {
        employeeLoginEmpSelectTextView.setText("");
        employeeLoginPasswordEditText.setText("");
        mServerNum = "";
    }

    // 뒤로가기 버튼 막기
    @Override public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EmployeeSelectionPopup.openCount = 0;
        GlobalMemberValues.b_isLoginView = true;

        String scaleuseyn = dbInit.dbExecuteReadReturnString(" select scaleuseyn from salon_storegeneral ");
        if (GlobalMemberValues.isStrEmpty(scaleuseyn)) {
            scaleuseyn = "N";
        }
        if (scaleuseyn == "Y" || scaleuseyn.equals("Y")) {
            GlobalMemberValues.isScaleUSE = true;
        } else {
            GlobalMemberValues.isScaleUSE = false;
        }

        // 타임메뉴 셋
        MainActivity mainActivity = new MainActivity();
        mainActivity.timeMenu_getServiceTime();

        // jihun 네트워크 에러시 팝업 실행부분.
        if (GlobalMemberValues.b_datadownload_not_complete == true){
            GlobalMemberValues.b_datadownload_not_complete = false;
            new AlertDialog.Builder(mContext)
                    .setTitle("Error")
                    .setMessage("The data download failed because the Internet connection \n" +
                            "was lost or there was a problem with the network \n" +
                            "while downloading the data. Please try again.")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .show();
        }


        // mssql 연결 및 테이블 생성 ---------------------------------------------------------------------------
        GlobalMemberValues.setConnectMSSQL();                           // 연결

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            GlobalMemberValues.logWrite("mssqllog", "mssql 여기진입...2" + "\n");

            if (GlobalMemberValues.setConnectMssql()) {
                // 테이블 생성
                MssqlCreateTables.createTablesForMSSQL();
                // 테이블 수정
                MssqlAlterTables.alterTablesForMSSQL();
            }
        }
        // --------------------------------------------------------------------------------------------------

        if (GlobalMemberValues.is_datadownload_yn){
            GlobalMemberValues.sendDataToTOrderEventServer();
            GlobalMemberValues.is_datadownload_yn = false;
        }

    }

    View.OnClickListener information_close_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogsSave.saveLogsInDB(110);
            informationPopup.cancel();
        }
    };

    private static Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            if (msg.what == 0){
                deleteDBFile(180);
            } else if (msg.what == 1){
                deleteDBFile(0);
            }
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            if (Employee_Login.mProgressDialog != null) {
                Employee_Login.mProgressDialog.dismiss();
            }
            // -------------------------------------------------------------------------------------
        }
    };

    public static void deleteDBFile(int date) {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download"); // 저장 경로
        File[] childFileList = dir.listFiles();

        if (dir.exists()) {
            for (File childFile : childFileList) {

                if (date == 0){
                    // data all delete.
                    String fileName = childFile.getName();
                    if (fileName.contains(GlobalMemberValues.DATABASE_NAME + "_")){
                        childFile.delete();
                    }
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
                    long now = System.currentTimeMillis();
                    Date nowDate = new Date(now);
                    Date temp_Date = null;
                    String fileName = childFile.getName();
                    if (fileName.contains(GlobalMemberValues.DATABASE_NAME)){
                        fileName = fileName.replace(GlobalMemberValues.DATABASE_NAME + "_", "");
                        try {
                            temp_Date = dateFormat.parse(fileName);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (temp_Date != null){
                            long calDate = nowDate.getTime() - temp_Date.getTime();
                            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
                            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
                            long calDateDays = calDate / (24 * 60 * 60 * 1000);
                            calDateDays = Math.abs(calDateDays);
                            if (calDateDays > date) {
                                childFile.delete();
                            }
                        }
                    }
                }

            }
//            dir.delete();
        }
    }

    public static void goNextStep() {
        String getEmpNum = employeeLoginEmpSelectTextView.getText().toString();
        GlobalMemberValues.logWrite("eidlogjjj", "eid1 : " + getEmpNum + "\n");
        if (!GlobalMemberValues.isStrEmpty(getEmpNum)) {
            GlobalMemberValues.logWrite("eidlogjjj", "여기1" + "\n");
            // 직원정보 셋팅
            setEmpInfo(getEmpNum);
//            GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;
        } else {
            GlobalMemberValues.logWrite("eidlogjjj", "여기2" + "\n");
            employeeLoginEmpSelectTextView.setTextColor(Color.RED);
            employeeLoginEmpSelectTextView.setText("ENTER YOUR ACCESS CODE");
//            servernumtv.setText("");
            mServerNum = "";

            //GlobalMemberValues.displayDialog(mContext, "Warning", "Enter your server number", "Close");
        }


        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
    }

    public static void setEmpInfo(String paramEid) {
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(mContext);
        String getEmpInfo = dataAtSqlite.getEmployeeInfoByEid(paramEid);
        // 권한체크
        if (!getEmpInfo.isEmpty()){
            if (!GlobalMemberValues.checkEmployeePermission(getEmpInfo.split(GlobalMemberValues.STRSPLITTER1)[0], getEmpInfo.split(GlobalMemberValues.STRSPLITTER1)[1], "<14>")) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "You do not have permission", "Close");
                return;
            }
        }

        if (!GlobalMemberValues.isStrEmpty(getEmpInfo)) {
            String[] strEmployeeInfoArr = getEmpInfo.split(GlobalMemberValues.STRSPLITTER1);

            if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
                EmployeeSelectionPopup.setSelectedEmployee(mContext, getEmpInfo, "N");

//                GlobalMemberValues.GLOBAL_EMPLOYEEINFO
//                        = new TemporaryEmployeeInfo(strEmployeeInfoArr[0], strEmployeeInfoArr[1], strEmployeeInfoArr[2], strEmployeeInfoArr[3],
//                        strEmployeeInfoArr[4], strEmployeeInfoArr[5], strEmployeeInfoArr[6], strEmployeeInfoArr[7], strEmployeeInfoArr[8],
//                        strEmployeeInfoArr[9], strEmployeeInfoArr[10], strEmployeeInfoArr[12]);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText(strEmployeeInfoArr[1]);

                // 0719 server 정보 추가.
                GlobalMemberValues.SERVER_IDX = strEmployeeInfoArr[0];
                GlobalMemberValues.SERVER_ID = strEmployeeInfoArr[8];
                GlobalMemberValues.SERVER_NAME = strEmployeeInfoArr[1];

                GlobalMemberValues.logWrite("jjjserverloginlog", "GlobalMemberValues.SERVER_IDX : " + GlobalMemberValues.SERVER_IDX + "\n");
                GlobalMemberValues.logWrite("jjjserverloginlog", "GlobalMemberValues.SERVER_NAME : " + GlobalMemberValues.SERVER_NAME + "\n");


                // main_side_button on
                // 타임메뉴 셋
                MainActivity mainActivity = new MainActivity();
                mainActivity.timeMenu_getServiceTime();

                GlobalMemberValues.LOGIN_EMPLOYEE_ID = strEmployeeInfoArr[8];
//                GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;

                // 타임메뉴 설정된대로 실행.
                GlobalMemberValues.openTimeMenuSelectPopupAuto();

//                mSelectedYN = "Y";

                loginDBInsert();

                // 서비스 선택리스트에 선택된 서비스가 있을 경우
                // 선택한 서비스의 employee 를 변경할지 여부를 물어 처리한다.
                if (MainMiddleService.selectedPosition != -1) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Item Delete")
                            .setMessage("Change the employee seleted item?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);

                                            // temp_salecart 테이블 에서 선택한 서비스의 직원 employee 를 변경한다.
                                            // DB 수정
                                            String tempSaleCartIdx = tempSCIns.tempSaleCartIdx;
                                            if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                                                String strSqlQuery = "update temp_salecart set " +
                                                        " empIdx ='" + strEmployeeInfoArr[0] + "', " +
                                                        " empName ='" + strEmployeeInfoArr[1] + "' " +
                                                        " where idx = '" + tempSaleCartIdx + "' ";
                                                GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "temp_salecart update query : " + strSqlQuery + "\n");
                                                DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
                                                String returnCode = "";
                                                returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
                                                GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "returnCode : " + returnCode + "\n");
                                                if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                                                    // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                                                    tempSCIns.mEmpIdx = strEmployeeInfoArr[0];
                                                    tempSCIns.mEmpName = strEmployeeInfoArr[1];

                                                    MainMiddleService.mGeneralArrayList.set(MainMiddleService.selectedPosition, tempSCIns);
                                                    MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                                                    MainMiddleService.selectedPosition = -1;
                                                }
                                            }
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                                    MainMiddleService.selectedPosition = -1;
                                }
                            })
                            .show();
                } else {
                    if (GlobalMemberValues.b_isNotComplite_CashIn){
                        return;
                    } else {
                        Intent intent = new Intent();   // table sale add
                        mActivity.setResult(Activity.RESULT_OK,intent); // table sale add
                        closeEmployeeLoginActivity();
                    }

                }



//                closeActivity();
            }
        } else {
//            employeeLoginEmpSelectTextView.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
//            employeeLoginEmpSelectTextView.setText("WRONG NUMBER\nTRY AGAIN");
//            employeeLoginEmpSelectTextView.setTextColor(Color.RED);
            GlobalMemberValues.displayDialog(mContext, "Warning", "WRONG NUMBER TRY AGAIN", "Close");
            mServerNum = "";
        }
    }




    // 07182024
    // 카드결제 기기등록관련
    public void pgIpDataMake() {
        // salon_storestationsettings_system
        int pgipTableCnt = MainActivity.mDbInit.checkTable("salon_pgip");
        if (pgipTableCnt > 0) {
            String strQuery = "select count(*) from salon_pgip";
            int pgIpCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));
            if (pgIpCnt == 0) {
                String pgdevicenum = "";
                Vector<String> strInsertQueryVec = new Vector<String>();
                for (int i = 0; i < 10; i++) {
                    if (i < 9) {
                        pgdevicenum = "PG0" + (i + 1);
                    } else {
                        pgdevicenum = "PG" + (i + 1);
                    }

                    strQuery = " insert into salon_pgip (scode, sidx, pgdevicenum, networkip, networkport) values ( " +
                            " '" + GlobalMemberValues.SALON_CODE + "', " +
                            " '" + GlobalMemberValues.STORE_INDEX + "', " +
                            " '" + pgdevicenum + "', " +
                            " '', " +
                            " '' " +
                            " ) ";
                    strInsertQueryVec.addElement(strQuery);
                }

                for (String tempQuery : strInsertQueryVec) {
                    GlobalMemberValues.logWrite("salonpgiplogjjj", "query : " + tempQuery + "\n");
                }

                String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                GlobalMemberValues.logWrite("salonpgiplogjjj", "returnResult : " + returnResult + "\n");
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                } else {
                }

            }
        }
    }

}
