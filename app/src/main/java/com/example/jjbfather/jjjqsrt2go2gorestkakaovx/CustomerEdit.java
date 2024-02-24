package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class CustomerEdit extends Activity {
    final String TAG = "CustomerEditLog";

    Activity activity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView customerNameTextView, customerIdTextView, customerPasswordTextView;
    private TextView customerGenderTextView, customerEmailTextView, customerBirthdayTextView;
    private TextView customerAddr1TextView, customerAddr2TextView, customerCityTextView;
    private TextView customerStateTextView, customerZipTextView;
    private TextView customerBirthdaySearchTextView;
    private TextView customerIdEditText, customerCardNumTextView;

    private EditText customerNameEditText, customerNameEditText2, customerPasswordEditText;
    private EditText customerEmailEditText;
    private EditText customerAddr1EditText, customerAddr2EditText, customerCityEditText;
    private EditText customerZipEditText;
    private EditText customerMobileEditText, customerCardNumEditText;

    private Button customerEditSubmitButton, customerEditCloseBtn;

    private Spinner customerStateSpinner;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    static String selectedItemSpinner = "All";

    String mSelectedSpinnerState = "";
    String mSelectedGender = "m";

    Intent mIntent;

    public int mTempFlagMemCheck = 0;
    public ProgressDialog memCheckProDial;
    public ProgressDialog memCardNumCheckProDial;

    public int mTempFlagMemReg = 0;
    public ProgressDialog memRegProDial;

    String mReturnValue = "";

    int mMem1Cnt = 0;
    int mMem2Cnt = 0;
    int mSalonMemCnt = 0;

    int mMembershipCardNum = 0;

    Vector<String> mStrUpdateQueryVec;

    public String rsvCustId = "";

    public String dataUpdated = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_customer_edit);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 80;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.customerEditLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        activity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 선택포지션 값
            rsvCustId = mIntent.getStringExtra("CustId");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 ID : " + rsvCustId + "\n");
            /*******************************************************************************************/
            if (GlobalMemberValues.isStrEmpty(rsvCustId)) {
                finish();
                if (GlobalMemberValues.isUseFadeInOut()) {
                    activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
            }
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setLayoutContent();
    }

    public void setLayoutContent() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // TextView -------------------------------------------------------------------------------------------------------
        customerNameTextView = (TextView)findViewById(R.id.customerNameTextView);
        customerNameTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerNameTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerIdTextView = (TextView)findViewById(R.id.customerIdTextView);
        customerIdTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerIdTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerPasswordTextView = (TextView)findViewById(R.id.customerPasswordTextView);
        customerPasswordTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPasswordTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerGenderTextView = (TextView)findViewById(R.id.customerGenderTextView);
        customerGenderTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerGenderTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerEmailTextView = (TextView)findViewById(R.id.customerEmailTextView);
        customerEmailTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerEmailTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr1TextView = (TextView)findViewById(R.id.customerAddr1TextView);
        customerAddr1TextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerAddr1TextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr2TextView = (TextView)findViewById(R.id.customerAddr2TextView);
        customerAddr2TextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerAddr2TextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerCityTextView = (TextView)findViewById(R.id.customerCityTextView);
        customerCityTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerCityTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerStateTextView = (TextView)findViewById(R.id.customerStateTextView);
        customerStateTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerStateTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerZipTextView = (TextView)findViewById(R.id.customerZipTextView);
        customerZipTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerZipTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerBirthdayTextView = (TextView)findViewById(R.id.customerBirthdayTextView);
        customerBirthdayTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerBirthdayTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerCardNumTextView = (TextView) findViewById(R.id.customerCardNumTextView);
        customerCardNumTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerCardNumTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerBirthdaySearchTextView = (TextView) findViewById(R.id.customerBirthdaySearchTextView);
        customerBirthdaySearchTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + customerBirthdaySearchTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerBirthdaySearchTextView.setOnClickListener(customerEditClickListener);
        // ----------------------------------------------------------------------------------------------------------------

        // EditText -------------------------------------------------------------------------------------------------------
        customerNameEditText = (EditText) findViewById(R.id.customerNameEditText);
        customerNameEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerNameEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerNameEditText2 = (EditText) findViewById(R.id.customerNameEditText2);
        customerNameEditText2.setTextSize(GlobalMemberValues.globalAddFontSize() + customerNameEditText2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerIdEditText = (TextView) findViewById(R.id.customerIdEditText);
        customerIdEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerIdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerIdEditText.setTextColor(Color.parseColor("#ffffff"));

        customerPasswordEditText = (EditText) findViewById(R.id.customerPasswordEditText);
        customerPasswordEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPasswordEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerEmailEditText = (EditText) findViewById(R.id.customerEmailEditText);
        customerEmailEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerEmailEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr1EditText = (EditText) findViewById(R.id.customerAddr1EditText);
        customerAddr1EditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerAddr1EditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr2EditText = (EditText) findViewById(R.id.customerAddr2EditText);
        customerAddr2EditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerAddr2EditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerCityEditText = (EditText) findViewById(R.id.customerCityEditText);
        customerCityEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerCityEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerZipEditText = (EditText) findViewById(R.id.customerZipEditText);
        customerZipEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerZipEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerMobileEditText = (EditText) findViewById(R.id.customerMobileEditText);
        customerMobileEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerMobileEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerCardNumEditText = (EditText) findViewById(R.id.customerCardNumEditText);
        customerCardNumEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + customerCardNumEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerCardNumEditText.setOnFocusChangeListener(editTextViewChangeFocus);
        // ----------------------------------------------------------------------------------------------------------------

        // Spinner 객체 생성 및 연결 ----------------------------------------------------------------------------------------
        customerStateSpinner = (Spinner)findViewById(R.id.customerStateSpinner);
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.STATEPROVINCE.length; i++) {
            mGeneralArrayListForSpinner.add(GlobalMemberValues.STATEPROVINCE[i][1]);
        }
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerStateSpinner.setAdapter(mSpinnerAdapter);
        customerStateSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);
        // ----------------------------------------------------------------------------------------------------------------

        // Button ---------------------------------------------------------------------------------------------------------
        customerEditSubmitButton = (Button)findViewById(R.id.customerEditSubmitButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerEditSubmitButton.setText("");
            customerEditSubmitButton.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            customerEditSubmitButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerEditSubmitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        customerEditCloseBtn = (Button)findViewById(R.id.customerEditCloseBtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerEditCloseBtn.setText("");
            customerEditCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            customerEditCloseBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerEditCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        customerEditSubmitButton.setOnClickListener(customerEditClickListener);
        customerEditCloseBtn.setOnClickListener(customerEditClickListener);
        // ----------------------------------------------------------------------------------------------------------------

        setContentsInView();
    }

    private void setContentsInView() {
        DatabaseInit dbInit = new DatabaseInit(context);   // DatabaseInit 객체 생성
        Cursor dbCursor;
        String tempUid, tempName, tempPassword, tempGender, tempMobile, tempByear, tempBmonth, tempBday;
        String tempAddr1, tempAddr2, tempCity, tempState, tempZip, tempEmail;
        String strQuery = "select A.uid, A.name, A.password, A.gender, " +
                " B.mobile, B.byear, B.bmonth, B.bday, B.addr1, B.addr2, B.city, B.state, B.zip, B.email " +
                " from member1 A inner join member2 B on A.uid = B.uid " +
                " where A.uid = '" + rsvCustId + "' ";
        GlobalMemberValues.logWrite(TAG, "selectQuery : " + strQuery + "\n");
        dbCursor = dbInit.dbExecuteRead(strQuery);
        if (dbCursor.getCount() > 0 && dbCursor.moveToFirst()) {
            tempUid = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(0), 1);
            tempName = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(1), 1);
            tempPassword = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(2), 1);
            tempGender = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(3), 1);
            tempMobile = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(4), 1);
            tempByear = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(5), 1);
            tempBmonth = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(6), 1);
            tempBday = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(7), 1);
            tempAddr1 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(8), 1);
            tempAddr2 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(9), 1);
            tempCity = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(10), 1);
            tempState = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(11), 1);
            tempZip = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(12), 1);
            tempEmail = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(13), 1);

            String tempName1 = "";
            String tempName2 = "";
            if (!GlobalMemberValues.isStrEmpty(tempName)) {
                String[] tempSptName = tempName.split(" ");
                tempName1 = tempSptName[0];
                if (tempSptName.length > 1) {
                    tempName2 = tempSptName[1];
                }
            }
            customerIdEditText.setText(tempUid);
            customerNameEditText.setText(tempName1);
            customerNameEditText2.setText(tempName2);
            customerPasswordEditText.setText(tempPassword);
            customerEmailEditText.setText(tempEmail);

            String tempMemcardnum = dbInit.dbExecuteReadReturnString(
                    " select memcardnum from salon_member where uid = '" + tempUid + "' "
            );
            String tempMemmobile = dbInit.dbExecuteReadReturnString(
                    " select memmobile from salon_member where uid = '" + tempUid + "' "
            );
            if (tempUid.length() > 9 && GlobalMemberValues.isStrEmpty(tempMemmobile)) {
                tempMemmobile = tempUid;
            }

            customerMobileEditText.setText(tempMemmobile);
            customerCardNumEditText.setText(tempMemcardnum);

            GlobalMemberValues.logWrite(TAG, "select 생일 month : " + tempBmonth + "\n");
            GlobalMemberValues.logWrite(TAG, "select 생일 day : " + tempBday + "\n");
            GlobalMemberValues.logWrite(TAG, "select 생일 year : " + tempByear + "\n");

            //  생일 -----------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(tempBmonth)) {
                int tempMonth = GlobalMemberValues.getIntAtString(tempBmonth);
                if (tempMonth < 10) {
                    tempBmonth = "0" + tempBmonth;
                }
            }
            if (!GlobalMemberValues.isStrEmpty(tempBday)) {
                int tempDay = GlobalMemberValues.getIntAtString(tempBday);
                if (tempDay < 10) {
                    tempBday = "0" + tempBday;
                }
            }
            String tempBirthday = "";
            if (!GlobalMemberValues.isStrEmpty(tempByear)
                    && !GlobalMemberValues.isStrEmpty(tempBmonth) && !GlobalMemberValues.isStrEmpty(tempBday)) {
                tempBirthday = tempBmonth + "-" +tempBday + "-" +tempByear;
            }
            GlobalMemberValues.logWrite(TAG, "생일 : " + tempBirthday + "\n");
            customerBirthdaySearchTextView.setText(tempBirthday);
            // ----------------------------------------------------------------------------------------

            customerAddr1EditText.setText(tempAddr1);
            customerAddr2EditText.setText(tempAddr2);

            customerCityEditText.setText(tempCity);
            customerZipEditText.setText(tempZip);

            // State
            if (!GlobalMemberValues.isStrEmpty(tempState)) {
                int tempPNPosition = 0;
                for (int i = 0; i < GlobalMemberValues.STATEPROVINCE.length; i++) {
                    if (tempState.equals(GlobalMemberValues.STATEPROVINCE[i][0])) {
                        tempPNPosition = i;
                    }
                }
                customerStateSpinner.setSelection(tempPNPosition);
            }
        }
    }

    EditText.OnFocusChangeListener editTextViewChangeFocus = new EditText.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.customerCardNumEditText : {
                    if (!hasFocus) {
                        //GlobalMemberValues.displayDialog(context, "","포커스 이동...", "Close");
                        String getMSRData = customerCardNumEditText.getText().toString();
                        if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                            getMSRData = GlobalMemberValues.getMSRCardNumber("employee", getMSRData);
                            //GlobalMemberValues.displayDialog(context, "","reading value : " + getMSRData, "Close");
                            customerCardNumEditText.setText(getMSRData);

                            String tempUid = customerIdEditText.getText().toString();
                            if (GlobalMemberValues.isStrEmpty(tempUid)) {
                                customerCardNumEditText.setText("");
                                GlobalMemberValues.displayDialog(context, "Warning","Enter your ID", "Close");
                            } else {
                                // 멤버쉽 카드번호 중복체크
                                checkDuplicateMembershipCardNum(tempUid, getMSRData);
                            }
                        }
                    }
                    break;
                }
            }
        }
    };

    private void checkDuplicateMembershipCardNum(String paramMemUid, String paramMemCardNum) {
        final String checkMemUid = paramMemUid;
        final String checkMemCardNum = paramMemCardNum;
        // 프로그래스 바를 실행~
        memCardNumCheckProDial = ProgressDialog.show(
                context, "", "Membership Card Number Check In Cloud.....", true, false
        );
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                String membershipCardNumberCheckInfo = GlobalMemberValues.getDataAfterMemberShipCardNumberCheck(context, checkMemUid, checkMemCardNum);
                if (GlobalMemberValues.isStrEmpty(membershipCardNumberCheckInfo)) {
                    GlobalMemberValues.displayDialog(context, "Warning", "You can not check the ID in the Cloud\n(Check the Internet or Network Status)", "Close");
                } else {
                    mMembershipCardNum = GlobalMemberValues.getIntAtString(membershipCardNumberCheckInfo);
                }
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                memCardNumcheckhandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler memCardNumcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagMemCheck == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                String tempCardNum = customerCardNumEditText.getText().toString();
                if (mMembershipCardNum > 0) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Invalid or Duplicate Membership Card Number (" + tempCardNum + ")", "Close");
                    customerCardNumEditText.setText("");
                } else {
                    GlobalMemberValues.displayDialog(context, "Information", "Membership card number is available ", "Close");
                }

                mMembershipCardNum = 0;
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                memCardNumCheckProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };


    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mSelectedSpinnerState = GlobalMemberValues.STATEPROVINCE[position][0];
            GlobalMemberValues.logWrite(TAG, "Selected State : " + mSelectedSpinnerState + "\n");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    Button.OnClickListener customerEditClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerEditSubmitButton : {
                    LogsSave.saveLogsInDB(229);
                    editCustomer();

                    break;
                }
                case R.id.customerEditCloseBtn : {
                    LogsSave.saveLogsInDB(227);
                    if (dataUpdated == "Y" || dataUpdated.equals("Y")) {
                        GlobalMemberValues.openCustomerSearch();
                    }
                    // 현재창 닫기
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.customerBirthdaySearchTextView : {
                    String tempCustomerBirthdaySh = customerBirthdaySearchTextView.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempCustomerBirthdaySh)) {
                        tempCustomerBirthdaySh = GlobalMemberValues.STR_NOW_DATE;
                    }
                    openDatePickerDialog(tempCustomerBirthdaySh);

                    break;
                }
            }
        }
    };

    private void editCustomer() {
        mStrUpdateQueryVec = new Vector<String>();
        mReturnValue = "";

        String insName = "";
        String insId = "";
        String insPassword = "";
        String insGender = "";
        String insMobile = "";
        String insPhone = "";
        String insBirthday = "";
        int insBirthY = 2000;
        int insBirthM = 1;
        int insBirthD = 1;
        String insAddr1 = "";
        String insAddr2 = "";
        String insCity = "";
        String insState = "";
        String insZip = "";
        String insEmail = "";
        String insMemcardnum = "";
        String insMemmobile = "";

        insId = customerIdEditText.getText().toString();
        insName = customerNameEditText.getText().toString() + " " + customerNameEditText2.getText().toString();
        insPassword = customerPasswordEditText.getText().toString();
        insGender = mSelectedGender;
        //insMobile = customerMobileEditText.getText().toString();
        //insMobile = GlobalMemberValues.getReplaceText(GlobalMemberValues.getReplaceText(insMobile, "-", ""), ".", "");
        insPhone = insMobile;
        insBirthday = customerBirthdaySearchTextView.getText().toString();
        insAddr1 = customerAddr1EditText.getText().toString();
        insAddr2 = customerAddr2EditText.getText().toString();
        insCity = customerCityEditText.getText().toString();
        insState = mSelectedSpinnerState;
        insZip = customerZipEditText.getText().toString();
        insEmail = customerEmailEditText.getText().toString();

        insMemmobile = customerMobileEditText.getText().toString();
        insMemcardnum = customerCardNumEditText.getText().toString();

        if (!GlobalMemberValues.isStrEmpty(insId) && insId.length() > 9 && GlobalMemberValues.isStrEmpty(insMemmobile)) {
            insMemmobile = insId;
        }

        if (!GlobalMemberValues.isStrEmpty(insBirthday)) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            Date birthDayDate = null;
            try {
                birthDayDate = formatter.parse(insBirthday);

                SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");

                String strBirthYear = CurYearFormat.format(birthDayDate);
                String strBirthMonth = CurMonthFormat.format(birthDayDate);
                String strBirthDay = CurDayFormat.format(birthDayDate);

                insBirthY = GlobalMemberValues.getIntAtString(strBirthYear);
                insBirthM = GlobalMemberValues.getIntAtString(strBirthMonth);
                insBirthD = GlobalMemberValues.getIntAtString(strBirthDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        GlobalMemberValues.logWrite(TAG, "입력할 생일 month : " + insBirthM + "\n");
        GlobalMemberValues.logWrite(TAG, "입력할 생일 day : " + insBirthD + "\n");
        GlobalMemberValues.logWrite(TAG, "입력할 생일 year : " + insBirthY + "\n");

        // 필수입력 사항 체크 ------------------------------------------------------------------------------------------
        if (GlobalMemberValues.isStrEmpty(insName)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter Customer Name", "Close");
            return;
        }
        // -----------------------------------------------------------------------------------------------------------

        // 멤버쉽카드번호가 입력되어 있을 경우 API 를 통해 클라우드에서 회원카드정보를 검색한다. ---------------------
        int memCardNumCnt = 0;
        if (!GlobalMemberValues.isStrEmpty(insMemcardnum)) {
            String membershipCardInfo = GlobalMemberValues.getDataAfterMemberShipCardNumberCheck(context, insId, insMemcardnum);
            if (GlobalMemberValues.isStrEmpty(membershipCardInfo)) {
                GlobalMemberValues.displayDialog(context, "Warning", "You can not check the ID in the Cloud\n(Check the Internet or Network Status)", "Close");
                return;
            } else {
                memCardNumCnt = GlobalMemberValues.getIntAtString(membershipCardInfo);

                if (memCardNumCnt > 0) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Invalid or Duplicate Membership Card Number (" + insMemcardnum + ")", "Close");
                    return;
                }
            }
        }
        // -----------------------------------------------------------------------------------------------------------

        // 쿼리문자열을 담을 벡터 변수생성 ------------------------------------------------------------------------------
        String insStrQuery = "";

        int mem1CntInPos = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                " select count(uid) from member1 where uid = '" + insId + "' "
        ));
        int mem2CntInPos = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                " select count(uid) from member2 where uid = '" + insId + "' "
        ));

        if (mem1CntInPos > 0) {
            insStrQuery = " update member1 set " +
                    " name = '" + insName + "', ";
            if (!GlobalMemberValues.isStrEmpty(insPassword)) {
                insStrQuery += " password = '" + insPassword + "', ";
            }
            insStrQuery += " mdate = datetime('now', 'localtime'), " +
                    " gender = '" + insGender + "' " +
                    " where uid = '" + rsvCustId + "' ";
            mStrUpdateQueryVec.addElement(insStrQuery);
        }

        if (mem2CntInPos > 0) {
            insStrQuery = " update member2 set " +
                    " addr1 = '" + insAddr1 + "', " +
                    " addr2 = '" + insAddr2 + "', " +
                    " state = '" + insState + "', " +
                    " city = '" + insCity + "', " +
                    " zip = '" + insZip + "', " +
                    " byear = '" + insBirthY + "', " +
                    " bmonth = '" + insBirthM + "', " +
                    " bday = '" + insBirthD + "', " +
                    " phone = '" + insPhone + "', " +
                    " mobile = '" + insMobile + "', " +
                    " email = '" + insEmail + "' " +
                    " where uid = '" + rsvCustId + "' ";
            mStrUpdateQueryVec.addElement(insStrQuery);
        }

        insStrQuery = " update salon_member set " +
                " memmobile = '" + insMemmobile + "', " +
                " memcardnum = '" + insMemcardnum + "' " +
                " where uid = '" + rsvCustId + "' ";
        mStrUpdateQueryVec.addElement(insStrQuery);

        if (mStrUpdateQueryVec.size() > 0) {
            // 먼저 API 를 통해 클라우드에 회원정보를 업로드한다. ---------------------------------------------------------
            final String[] tempUpDataArr = {
                    insId, insName, insPassword, insGender, insMobile, insBirthday,
                    insAddr1, insAddr2, insCity, insState, insZip, insEmail, insMemcardnum, insMemmobile
            };

            // 프로그래스 바를 실행
            memRegProDial = ProgressDialog.show(
                    context, "Customer Edit", "Customer information is being updated...", true, false
            );
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    API_memberdataupload_tocloud apiMemEdit = new API_memberdataupload_tocloud("U", tempUpDataArr);
                    apiMemEdit.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apiMemEdit.mFlag) {
                            mReturnValue = apiMemEdit.mReturnValue;
                        }
                    } catch (InterruptedException e) {
                        mReturnValue = "";
                    }
                    // ---------------------------------------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    memReghandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();
            // -------------------------------------------------------------------------------------------------------
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "No Data", "Close");
        }
    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(this, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            customerBirthdaySearchTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    private Handler memReghandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagMemReg == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                if (mReturnValue.equals("00")) {
                    for (String tempQuery : mStrUpdateQueryVec) {
                        GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(mStrUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        dataUpdated = "N";
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                    } else {
                        dataUpdated = "Y";
                        // 수정성공
                        GlobalMemberValues.displayDialog(context, "Information", "Successfully Updated", "Close");
                    }
                } else {
                    dataUpdated = "N";
                    GlobalMemberValues.displayDialog(context, "Warning", "Cloud Upload Error (Error Code : " + mReturnValue + ")", "Close");
                }

                mStrUpdateQueryVec = null;
                mReturnValue = "";
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                memRegProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };
}
