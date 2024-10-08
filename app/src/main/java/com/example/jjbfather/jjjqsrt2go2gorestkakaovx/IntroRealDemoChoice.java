package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class IntroRealDemoChoice extends Activity {
    static Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    TextView introRealDemoSerialNumberEditText, versionInfoTextView;

    EditText introRealDemoStationCodeEditText;
    EditText mssqldbipTextView1;

    Button introRealDemoApprovedRequestButton, introRealDemoDemoTestButton;
    Button introRealDemoQuitButton;

    private RadioGroup mssqlinsatdownloadRadioGroup;

    private RadioButton yesRadioButton, noRadioButton;

    LinearLayout introRealDemoQuitButtonLinearyLayout;
    TextView introRealDemoTitleTextView1, introRealDemoTitleTextView2, introRealDemoTitleTextView3, introRealDemoTitleTextView4;
    TextView introRealDemoDatabaseNameEditText,introRealDemoDbPasswordEditText,introRealDemoMobileHostEditText,introRealDemoCloudHostEditText, introRealDemoDbCodeNameEditText;

    public ProgressDialog stationCheckProDial;

    String mReturnValue = "";

    String tempSyncYN = "N";

    private PopupIpInput popupIpInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalMemberValues.setActivityOrientation(this, this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (GlobalMemberValues.isLiteVersion()) {
            setContentView(R.layout.activity_intro_real_demo_choice_lite);
        } else {
            setContentView(R.layout.activity_intro_real_demo_choice);
        }
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);
        parentLn = (LinearLayout)findViewById(R.id.introRealDemoChoiceLinearLayout);

        // basic info 테이블이 있으면 이미 승인받은 경우이므로 메인으로 이동하고 현재 액티비티는 종료 ---
        int basicTableCnt = dbInit.checkTable("basic_pos_information");
        if (basicTableCnt > 0) {        // 이미 승인받은 경우이므로
            setCheckBasicInfo();
        }
        // ---------------------------------------------------------------------

        setContents();

        // get ip
        String vMssqldbip = "";
        String tempDatabaseName = "";
        String tempDatabasePass = "";
        String tempMobileHost = "";
        String tempCloudHost = "";
        String tempDbCodeName = "";
        String strQuery = "select mssqldbip, databasename, databasepass, mobilehost, cloudhost, dbcodename " +
                " from salon_storestationsettings_system ";
        Cursor settingsSystemCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            String tempMssqldbip = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
            if (!GlobalMemberValues.isStrEmpty(tempMssqldbip)) {
                vMssqldbip = tempMssqldbip;
            }
            tempDatabaseName = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
            tempDatabasePass = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);

            tempMobileHost = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);

            tempCloudHost = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(4), 1);

            tempDbCodeName = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(5), 1);

        } // get ip

        if (!GlobalMemberValues.isStrEmpty(vMssqldbip) || !vMssqldbip.equals("0.0.0.0")) {
            GlobalMemberValues.mssql_ip = vMssqldbip;

            String tempip1 = GlobalMemberValues.mssql_ip;
            mssqldbipTextView1.setText(tempip1);
        }
        if (!GlobalMemberValues.isStrEmpty(tempDatabaseName)) {
            GlobalMemberValues.mssql_db = tempDatabaseName;
            introRealDemoDatabaseNameEditText.setText(GlobalMemberValues.mssql_db);
        }
        if (!GlobalMemberValues.isStrEmpty(tempDatabasePass)) {
            GlobalMemberValues.mssql_pw = tempDatabasePass;
            introRealDemoDbPasswordEditText.setText(GlobalMemberValues.mssql_pw);
        }
        if (!GlobalMemberValues.isStrEmpty(tempMobileHost)) {
            GlobalMemberValues.MOBILE_HOST = tempMobileHost;
            introRealDemoMobileHostEditText.setText(GlobalMemberValues.MOBILE_HOST);
        }
        if (!GlobalMemberValues.isStrEmpty(tempCloudHost)) {
            GlobalMemberValues.CLOUD_HOST = tempCloudHost;
            introRealDemoCloudHostEditText.setText(GlobalMemberValues.CLOUD_HOST);
            GlobalMemberValues.setCloudAddr();
        }


        if (!GlobalMemberValues.isStrEmpty(tempDbCodeName)) {
            GlobalMemberValues.M_DBCODENAME = tempDbCodeName;
            introRealDemoDbCodeNameEditText.setText(GlobalMemberValues.M_DBCODENAME);
            GlobalMemberValues.setCloudAddr();
        }




        popupIpInput = new PopupIpInput(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalMemberValues.mssql_ip = popupIpInput.ip1.getText().toString();
                if (GlobalMemberValues.setConnectMssql()) {
                    // 테이블 생성
                    MssqlCreateTables.createTablesForMSSQL();
                    // 테이블 수정
                    MssqlAlterTables.alterTablesForMSSQL();
                }
                mssqldbipTextView1.setText(popupIpInput.ip1.getText().toString().trim());

                if (popupIpInput.popup_dabasename_edtxt.getText().toString().trim().isEmpty()){
                    introRealDemoDatabaseNameEditText.setText(GlobalMemberValues.mssql_db);
                } else {
                    introRealDemoDatabaseNameEditText.setText(popupIpInput.popup_dabasename_edtxt.getText().toString().trim());
                }
                GlobalMemberValues.mssql_db = introRealDemoDatabaseNameEditText.getText().toString();

                if (popupIpInput.popup_dbpassword_edtxt.getText().toString().trim().isEmpty()){
                    introRealDemoDbPasswordEditText.setText(GlobalMemberValues.mssql_pw);
                } else {
                    introRealDemoDbPasswordEditText.setText(popupIpInput.popup_dbpassword_edtxt.getText().toString().trim());
                }
                GlobalMemberValues.mssql_pw = introRealDemoDbPasswordEditText.getText().toString();

                if (popupIpInput.popup_mobilehost_edtxt.getText().toString().trim().isEmpty()){
                    introRealDemoMobileHostEditText.setText(GlobalMemberValues.MOBILE_HOST);
                } else {
                    introRealDemoMobileHostEditText.setText(popupIpInput.popup_mobilehost_edtxt.getText().toString().trim());
                }
                GlobalMemberValues.MOBILE_HOST = introRealDemoMobileHostEditText.getText().toString();

                if (popupIpInput.popup_mobilehost_edtxt.getText().toString().trim().isEmpty()){
                    introRealDemoCloudHostEditText.setText(GlobalMemberValues.CLOUD_HOST);
                } else {
                    introRealDemoCloudHostEditText.setText(popupIpInput.popup_cloudhost_edtxt.getText().toString().trim());
                }
                GlobalMemberValues.CLOUD_HOST = introRealDemoCloudHostEditText.getText().toString();

                if (popupIpInput.popup_dbcodename_edtxt.getText().toString().trim().isEmpty()){
                    introRealDemoDbCodeNameEditText.setText(GlobalMemberValues.CLOUD_HOST);
                } else {
                    introRealDemoDbCodeNameEditText.setText(popupIpInput.popup_dbcodename_edtxt.getText().toString().trim());
                }
                GlobalMemberValues.M_DBCODENAME = introRealDemoDbCodeNameEditText.getText().toString();

                GlobalMemberValues.setCloudAddr();


                // update
                int returnValue = 0;

                // 쿼리문자열을 담을 벡터 변수생성
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String updStrQuery = "update salon_storestationsettings_system set " +
                        " mssqldbip = '" + GlobalMemberValues.mssql_ip + "', " +
                        " databasename = '" + GlobalMemberValues.mssql_db + "', " +
                        " databasepass = '" + GlobalMemberValues.mssql_pw + "', " +
                        " mobilehost = '" + GlobalMemberValues.MOBILE_HOST + "', " +
                        " cloudhost = '" + GlobalMemberValues.CLOUD_HOST + "', " +
                        " dbcodename = '" + GlobalMemberValues.M_DBCODENAME + "', " +
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

                        GlobalMemberValues.logWrite("dbinfolog", "ip : " + GlobalMemberValues.mssql_ip + "\n");
                        GlobalMemberValues.logWrite("dbinfolog", "dbname : " + GlobalMemberValues.mssql_db + "\n");
                        GlobalMemberValues.logWrite("dbinfolog", "dbpwd : " + GlobalMemberValues.mssql_pw + "\n");
                        GlobalMemberValues.logWrite("dbinfolog", "mobile host : " + GlobalMemberValues.MOBILE_HOST + "\n");
                        GlobalMemberValues.logWrite("dbinfolog", "cloud host : " + GlobalMemberValues.CLOUD_HOST + "\n");
                        GlobalMemberValues.logWrite("dbinfolog", "dbcodename : " + GlobalMemberValues.M_DBCODENAME + "\n");
                    }
                } else {
                    returnValue = 0;
                }

                if (returnValue > 0) {

                }
                // update

                popupIpInput.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupIpInput.dismiss();
            }
        });
        popupIpInput.show();
    }

    public void setContents() {
        // 최초 인터넷 체크
        GlobalMemberValues.GLOBALNETWORKSTATUS = NetworkUtil.getConnectivityStatus(context);

        // os 버전 체크
        // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
        GlobalMemberValues.setFileAccessPermission(mActivity, context);

        // 권한 확인 및 설정 ----------------------------------------------------------------------------------
        // 파일 읽기
        //MainActivity.checkAppPermission(mActivity, context, "FILEWRITE");
        // --------------------------------------------------------------------------------------------------


        // Settings 값 초기화 ----------------------------------------------------------------------
        GlobalMemberValues.APPOPENCOUNT = 0;
        GlobalMemberValues.SPLASHCOUNT = 0;
        GlobalMemberValues.DROPTABLETYPE = 0;
        GlobalMemberValues.INSERTDATAAFTERDELETE = 0;

        // Splash Use
        GlobalMemberValues.GLOBAL_SPLASHUSE = 0;               // 0 : 사용         1 : 사용안함
        // Download Data
        GlobalMemberValues.GLOBAL_DOWNLOADDATA = 1;            // 0 : 다운로드버튼 클릭시         1 : 앱 오픈시
        // Database Backup
        GlobalMemberValues.GLOBAL_DATABASEBACKUP = 0;          // 0 : 백업안함    1 : 텐더시   2 : 앱 종료시
        // CommissionratioType
        GlobalMemberValues.GLOBAL_COMMISSIONRATIOTYPE = 1;     // 0 : 직원 커미션만 부여    1 : 직원 + 서비스메뉴 커미션 부여

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

        introRealDemoQuitButtonLinearyLayout = (LinearLayout)findViewById(R.id.introRealDemoQuitButtonLinearyLayout);
        introRealDemoQuitButtonLinearyLayout.setPadding(3, 3, 3, 3);

        introRealDemoTitleTextView1 = (TextView)findViewById(R.id.introRealDemoTitleTextView1);
//        introRealDemoTitleTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        introRealDemoTitleTextView2 = (TextView)findViewById(R.id.introRealDemoTitleTextView2);
//        introRealDemoTitleTextView2.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        introRealDemoTitleTextView3 = (TextView)findViewById(R.id.introRealDemoTitleTextView3);
//        introRealDemoTitleTextView3.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        introRealDemoTitleTextView4 = (TextView)findViewById(R.id.introRealDemoTitleTextView4);
//        introRealDemoTitleTextView4.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);


        // mssql database sync. -----------------------------------------------------------------------------------------------
        mssqlinsatdownloadRadioGroup = (RadioGroup)findViewById(R.id.mssqlinsatdownloadRadioGroup);
        mssqlinsatdownloadRadioGroup.setOnCheckedChangeListener(checkRadioGroup);

        yesRadioButton = (RadioButton)findViewById(R.id.yesRadioButton);
        yesRadioButton.setTextSize(yesRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        noRadioButton = (RadioButton)findViewById(R.id.noRadioButton);
        noRadioButton.setTextSize(noRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // -------------------------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        introRealDemoQuitButton = (Button)findViewById(R.id.introRealDemoQuitButton);
        introRealDemoQuitButton.setOnClickListener(introRealDemoBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            introRealDemoQuitButton.setText("");
            introRealDemoQuitButton.setBackgroundResource(R.drawable.ab_imagebutton_close_common);
        } else {
            introRealDemoQuitButton.setTextSize(
                    introRealDemoQuitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        introRealDemoApprovedRequestButton = (Button)findViewById(R.id.introRealDemoApprovedRequestButton);
        introRealDemoApprovedRequestButton.setOnClickListener(introRealDemoBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            introRealDemoApprovedRequestButton.setText("");
            introRealDemoApprovedRequestButton.setBackgroundResource(R.drawable.ab_imagebutton_intro_request);
        } else {
            introRealDemoApprovedRequestButton.setTextSize(
                    introRealDemoApprovedRequestButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

//        introRealDemoDemoTestButton = (Button)findViewById(R.id.introRealDemoDemoTestButton);
//        introRealDemoDemoTestButton.setOnClickListener(introRealDemoBtnClickListener);
//        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//            introRealDemoDemoTestButton.setText("");
//            introRealDemoDemoTestButton.setBackgroundResource(R.drawable.ab_imagebutton_intro_demo);
//        } else {
//            introRealDemoDemoTestButton.setTextSize(
//                    introRealDemoDemoTestButton.getTextSize()
//                            * GlobalMemberValues.getGlobalFontSize()
//            );
//        }
        /***********************************************************************************************************/

        /** 객체 생성 및 리스너 연결 *******************************************************************************/
        introRealDemoStationCodeEditText = (EditText)findViewById(R.id.introRealDemoStationCodeEditText);
        introRealDemoStationCodeEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);

        mssqldbipTextView1 = (EditText)findViewById(R.id.mssqldbipTextView1);
        mssqldbipTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
        mssqldbipTextView1.setFocusable(false);


        introRealDemoSerialNumberEditText = (TextView)findViewById(R.id.introRealDemoSerialNumberEditText);
        introRealDemoSerialNumberEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
        versionInfoTextView = (TextView)findViewById(R.id.versionInfoTextView);
        versionInfoTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);

        String secureAndroidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        introRealDemoSerialNumberEditText.setText(secureAndroidId.toUpperCase());
        /***********************************************************************************************************/

        versionInfoTextView.setText("v." + GlobalMemberValues.getAppVersionName(context));

        // 스테이션 코드값을 입력하는 부분에서
        // 소문자를 대문자로 바꾸고, 최대 입력글자수를 17로 제한
        introRealDemoStationCodeEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        mssqldbipTextView1.setOnClickListener(introRealDemoBtnClickListener);


        introRealDemoDatabaseNameEditText = (TextView)findViewById(R.id.introRealDemoDatabaseNameEditText);
//        introRealDemoDatabaseNameEditText.setText("JJJQSRDBMULTI_VX");
        introRealDemoDbPasswordEditText = (TextView)findViewById(R.id.introRealDemoDbPasswordEditText);
//        introRealDemoDbPasswordEditText.setText("DhksGkDP@02)");
        introRealDemoMobileHostEditText = (TextView)findViewById(R.id.introRealDemoMobileHostEditText);
//        introRealDemoMobileHostEditText.setText("yukdaejangm");
        introRealDemoCloudHostEditText = (TextView)findViewById(R.id.introRealDemoCloudHostEditText);
//        introRealDemoCloudHostEditText.setText("yukdaejangcloud");

        introRealDemoDbCodeNameEditText = (TextView)findViewById(R.id.introRealDemoDbCodeNameEditText);

        introRealDemoDatabaseNameEditText.setOnClickListener(introRealDemoBtnClickListener);
        introRealDemoDbPasswordEditText.setOnClickListener(introRealDemoBtnClickListener);
        introRealDemoMobileHostEditText.setOnClickListener(introRealDemoBtnClickListener);
        introRealDemoCloudHostEditText.setOnClickListener(introRealDemoBtnClickListener);
        introRealDemoDbCodeNameEditText.setOnClickListener(introRealDemoBtnClickListener);

    }

    RadioGroup.OnCheckedChangeListener checkRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.mssqlinsatdownloadRadioGroup : {
                    switch (checkedId) {
                        // 가로 정방향 고정
                        case R.id.yesRadioButton : {
                            tempSyncYN = "Y";
                            break;
                        }
                        // 가로 역방향 고정
                        case R.id.noRadioButton : {
                            tempSyncYN = "N";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("mssqlvalue", "value : " + GlobalMemberValues.mssql_sync + "\n");
                    break;
                }


            }
        }
    };

    public void setCheckBasicInfo() {
        Cursor dbCursor;
        // 포스 기본정보 -------------------------------------------------------------------------------------------------
        String strBasicPosInformationQuery = "select sid, scode, sname, sidx, stcode, serialNumber, stname " +
                " from basic_pos_information ";
        dbCursor = dbInit.dbExecuteRead(strBasicPosInformationQuery);
        if (dbCursor.getCount() > 0 && dbCursor.moveToFirst()) {
            Intent mainIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            mActivity.startActivity(mainIntent);
            // basic_pos_information 테이블에 데이터가 있다는 것은
            // 승인받은 디바이스이므로, 그동안 쌓여있는 데이터가 있을 수 있으므로
            // GlobalMemberValues.DROPTABLETYPE 의 값을 1 로 바꾼다.
            // GlobalMemberValues.DROPTABLETYPE ---- 0 : 전체 테이블 삭제     1 : 클라우드 관련 테이블만 삭제
            GlobalMemberValues.DROPTABLETYPE = 1;
            GlobalMemberValues.INSERTDATAAFTERDELETE = 1;
            finish();
        }
    }


    View.OnClickListener introRealDemoBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.introRealDemoQuitButton : {
                    GlobalMemberValues.setCloseAndroidApp(mActivity);
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    break;
                }
                case R.id.introRealDemoApprovedRequestButton : {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS == 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Internet is not connected", "Close");
                        return;
                    }

                    // os 버전 체크
                    // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
                    GlobalMemberValues.setFileAccessPermission(mActivity, context);

                    String tempStcode = introRealDemoStationCodeEditText.getText().toString().trim();
                    String tempSerialNumber = introRealDemoSerialNumberEditText.getText().toString();

                    String ip1 = mssqldbipTextView1.getText().toString().trim();


                    String dbname = introRealDemoDatabaseNameEditText.getText().toString().trim();
                    String dbpwd = introRealDemoDbPasswordEditText.getText().toString().trim();
                    String mobilehost = introRealDemoMobileHostEditText.getText().toString().trim();
                    String cloudhost = introRealDemoCloudHostEditText.getText().toString().trim();


                    if (GlobalMemberValues.isStrEmpty(tempStcode)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter Station Code", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(ip1)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter DB Server IP Address", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(dbname)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter database name", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(dbpwd)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter database password", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mobilehost)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter mobile host", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(cloudhost)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter DB Server IP Address (4)", "Close");
                        return;
                    }

                    setCheckStation(tempStcode.toUpperCase(), tempSerialNumber);
                    break;
                }
                case R.id.mssqldbipTextView1 :
                case R.id.introRealDemoDatabaseNameEditText:
                case R.id.introRealDemoDbPasswordEditText :
                case R.id.introRealDemoMobileHostEditText :
                case R.id.introRealDemoCloudHostEditText :
                case R.id.introRealDemoDbCodeNameEditText :
                    if (popupIpInput != null)popupIpInput.show();
                    break;

//                case R.id.introRealDemoDemoTestButton : {
//                    if (GlobalMemberValues.GLOBALNETWORKSTATUS == 0) {
//                        GlobalMemberValues.displayDialog(context, "Waraning", "Internet is not connected", "Close");
//                        return;
//                    }
//
//                    final String tempStcode = GlobalMemberValues.DEMO_STATION_CODE;
//                    final String tempSerialNumber = introRealDemoSerialNumberEditText.getText().toString();
//
//                    if (GlobalMemberValues.isStrEmpty(tempStcode)) {
//                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter Station Code", "Close");
//                        return;
//                    }
//
//                    new AlertDialog.Builder(context)
//                            .setTitle("NZ ANDROID")
//                            .setMessage("Do you want to use demo version pos?")
//                                    //.setIcon(R.drawable.ic_launcher)
//                            .setPositiveButton("Yes",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            setCheckStation(tempStcode, tempSerialNumber);
//                                        }
//                                    })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //
//                                }
//                            })
//                            .show();
//                    break;
//                }
            }
        }
    };

    public void setCheckStation(final String paramStcode, final String paramSerialNumber) {
        if (!GlobalMemberValues.isStrEmpty(paramStcode) && !GlobalMemberValues.isStrEmpty(paramSerialNumber)) {

            GlobalMemberValues.logWrite("dbinfolog", "ip : " + GlobalMemberValues.mssql_ip + "\n");
            GlobalMemberValues.logWrite("dbinfolog", "dbname : " + GlobalMemberValues.mssql_db + "\n");
            GlobalMemberValues.logWrite("dbinfolog", "dbpwd : " + GlobalMemberValues.mssql_pw + "\n");
            GlobalMemberValues.logWrite("dbinfolog", "mobile host : " + GlobalMemberValues.MOBILE_HOST + "\n");
            GlobalMemberValues.logWrite("dbinfolog", "cloud host : " + GlobalMemberValues.CLOUD_HOST + "\n");

            GlobalMemberValues.logWrite("dbinfolog", "cloud : " + GlobalMemberValues.CLOUD_SERVER_URL + "\n");

            // 프로그래스 바를 실행~
            stationCheckProDial = ProgressDialog.show(
                    context, "Check Station", "Checking station number in the Cloud Server...", true, false
            );
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    API_station_check apicheckInstance = new API_station_check(paramStcode, paramSerialNumber);
                    apicheckInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME + 1500);
                        if (apicheckInstance.mFlag) {
                            mReturnValue = apicheckInstance.mReturnValue;
                        }
                    } catch (InterruptedException e) {
                        //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                    }
                    // ---------------------------------------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    stationcheckhandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            GlobalMemberValues.displayDialog(context, "Waraning", "Enter Station Code or Serial Number", "Close");
        }
    }

    private Handler stationcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            if (!GlobalMemberValues.isStrEmpty(mReturnValue)) {
                GlobalMemberValues.logWrite("StationCheckReturnValue", "returnValue = " + mReturnValue + "\n");
                String[] arrStationInfo = mReturnValue.split(GlobalMemberValues.STRSPLITTER1);
                if (!GlobalMemberValues.isStrEmpty(arrStationInfo[1]) && !GlobalMemberValues.isStrEmpty(arrStationInfo[2])) {
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String strInsertQuery = "insert into basic_pos_information (sid, scode, sname, sidx, stcode, serialNumber, stname) values (" +
                            " '" + arrStationInfo[0] + "', " +
                            " '" + arrStationInfo[1] + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(arrStationInfo[6], 0) + "', " +
                            " '" + arrStationInfo[2] + "', " +
                            " '" + arrStationInfo[5] + "', " +
                            " '" + arrStationInfo[3] + "', " +
                            " '" + arrStationInfo[4] + "' " +
                            ")";
                    strInsertQueryVec.addElement(strInsertQuery);

                    for (String tempQuery : strInsertQueryVec) {
                        GlobalMemberValues.logWrite("BASICINFOQUERY", "query : " + tempQuery + "\n");
                    }

                    // 트랜잭션 처리후 결과값 저장 객체
                    String returnResult = "";
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                        return;
                    } else { // 정상처리일 경우 메인 페이지 오픈
                        GlobalMemberValues.APPOPENCOUNT = 0;
                        GlobalMemberValues.SPLASHCOUNT = 0;
                        GlobalMemberValues.DROPTABLETYPE = 1;
                        GlobalMemberValues.INSERTDATAAFTERDELETE = 0;

                        GlobalMemberValues.mssql_sync = tempSyncYN;

                        String tempIP1 = mssqldbipTextView1.getText().toString();
                        String tempIPAll = tempIP1;
                        GlobalMemberValues.mssql_ip = tempIPAll;

                        String tempDatabaseName = introRealDemoDatabaseNameEditText.getText().toString();
                        GlobalMemberValues.mssql_db = tempDatabaseName;
                        String tempDatabasePass = introRealDemoDbPasswordEditText.getText().toString();
                        GlobalMemberValues.mssql_pw = tempDatabasePass;
                        String tempMobileHost = introRealDemoMobileHostEditText.getText().toString();
                        GlobalMemberValues.MOBILE_HOST = tempMobileHost;
                        String tempCloudHost = introRealDemoCloudHostEditText.getText().toString();
                        GlobalMemberValues.CLOUD_HOST = tempCloudHost;
                        String tempDbCodeName = introRealDemoDbCodeNameEditText.getText().toString();
                        GlobalMemberValues.M_DBCODENAME = tempDbCodeName;

                        GlobalMemberValues.setCloudAddr();

                        Intent mainIntent = new Intent(context.getApplicationContext(), MainActivity.class);
                        mActivity.startActivity(mainIntent);
                        MainActivity.setBasicInformationInit();
                        MainActivity.setStoreInformationInit();
                        finish();
                    }
                } else {
                    GlobalMemberValues.displayDialog(context, "Waraning", "Input incorrect Store Code (Error-01)", "Close");
                }
            } else {
                GlobalMemberValues.displayDialog(context, "Waraning", "Input incorrect Store Code (Error-02)", "Close");
            }
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            stationCheckProDial.dismiss();
            // -------------------------------------------------------------------------------------
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
