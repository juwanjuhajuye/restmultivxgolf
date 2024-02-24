package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
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
import android.widget.TimePicker;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class DeliveryTakeawayInfo extends Activity {
    final String TAG = "DeliveryTakeawayInfo";

    Activity activity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private LinearLayout deliveryFeeLn;

    private TextView customerNameTextView, customerIdTextView;
    private TextView customerEmailTextView, customerDeliveryDayTextView;
    private TextView customerAddr1TextView, customerAddr2TextView, customerCityTextView;
    private TextView customerStateTextView, customerZipTextView;
    private TextView customerDeilveryDaySearchTextView, customerDeilveryTimeSearchTextView;
    private TextView customerMemoTextView;

    private EditText customerNameEditText, customerNameEditText2, customerIdEditText;
    private EditText customerEmailEditText;
    private EditText customerAddr1EditText, customerAddr2EditText, customerCityEditText;
    private EditText customerZipEditText;
    private EditText customerMemoEditText;
    private EditText deliveryfeeEv;

    private Button customerDeliveryTakeawaySubmitButton, deliverytakeawayInfoCloseBtn;
    private Button customerWalkInButotn;
    private Button deliveryFreeButotn;

    private Spinner customerStateSpinner;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    static String selectedItemSpinner = "All";

    String mSelectedSpinnerState = "";
    String mSelectedGender = "f";

    Intent mIntent;
    String mGetHoldCode = "";

    public int mTempFlagMemCheck = 0;
    public ProgressDialog memCheckProDial;

    public int mTempFlagMemReg = 0;
    public ProgressDialog memRegProDial;

    String mReturnValue = "";

    String mMemFirstName = "";
    String mMemLastName = "";
    String mMemEmail = "";
    String mMemAddr1 = "";
    String mMemAddr2 = "";
    String mMemCity = "";
    String mMemState = "";
    String mMemZip = "";

    String memReturnVal1 = "";
    String memReturnVal2 = "";

    public String mTempDeliveryTakeaway = "D";

    Vector<String> mStrInsertQueryVec;

    String mPayYN = "";

    TemporaryCustomerInfo selectedItemCustomerInfo;

    private Long mLastClickTime_okbtn = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_delivery_takeaway_info);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 80;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 80;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.customerAddLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        activity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 선택포시션 값
            mPayYN = mIntent.getStringExtra("payyn");
            GlobalMemberValues.logWrite(TAG + "1", "mPayYN : " + mPayYN + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
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

        if (GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)) {
            GlobalMemberValues.displayDialog(context, "Waraning", "Invalid Access", "Close");
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
            return;
        }

        GlobalMemberValues.logWrite("PayYNCCC", "mPayYN : " + mPayYN + "\n");
        deliveryFeeLn = (LinearLayout)findViewById(R.id.deliveryFeeLn);
        if (mPayYN == "Y" || mPayYN.equals("Y")) {
            deliveryFeeLn.setVisibility(View.VISIBLE);
        } else {
            deliveryFeeLn.setVisibility(View.INVISIBLE);
        }

        mGetHoldCode = MainMiddleService.mHoldCode;
        GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (인텐트에서 받은것) : " + mGetHoldCode + "\n");

        // 고객이 선택되어 있을 경우 고객정보를 가져온다.
        String vCustomerId = "";
        String vCustomerName = "";
        String vCustomerEmail = "";
        String vCustomerAddr1 = "";
        String vCustomerAddr2 = "";
        String vCustomerCity = "";
        String vCustomerState = "";
        String vCustomerZip = "";

        String vCustomerName1 = "";
        String vCustomerName2 = "";

        String vCustomerMemo = "";

        String vDeliveryDay = "";
        String vDeliveryTime = "";
        String vDeliveryDate = "";

        String vDeliveryTakeaway = "T";

        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                vCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                vCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                vCustomerEmail = "";
                vCustomerAddr1 = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memAddr1;
                vCustomerAddr2 = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memAddr2;
                vCustomerCity = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memCity;
                vCustomerState = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memState;
                vCustomerZip = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memZip;

                if (!GlobalMemberValues.isStrEmpty(vCustomerName)) {
                    String[] tempSptName = vCustomerName.split(" ");
                    if (tempSptName.length > 1) {
                        vCustomerName1 = tempSptName[0];
                        vCustomerName2 = tempSptName[1];
                    } else if (tempSptName.length == 1) {
                        vCustomerName1 = tempSptName[0];
                    } else {
                    }
                }

            } else {
                String strQuery = "select a.name, b.email, b.addr1, b.addr2, b.city, b.state, b.zip " +
                        " from member1 a inner join member2 b on a.uid = b.uid " +
                        " where a.uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' ";
                GlobalMemberValues.logWrite(TAG, "strQuery1 : " + strQuery + "\n");
                Cursor memberCursor = dbInit.dbExecuteRead(strQuery);
                if (memberCursor.getCount() > 0 && memberCursor.moveToFirst()) {
                    vCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                    vCustomerName = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(0), 1);
                    vCustomerEmail = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(1), 1);
                    vCustomerAddr1 = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(2), 1);
                    vCustomerAddr2 = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(3), 1);
                    vCustomerCity = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(4), 1);
                    vCustomerState = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(5), 1);
                    vCustomerZip = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(6), 1);

                    if (!GlobalMemberValues.isStrEmpty(vCustomerName)) {
                        String[] tempSptName = vCustomerName.split(" ");
                        if (tempSptName.length > 1) {
                            vCustomerName1 = tempSptName[0];
                            vCustomerName2 = tempSptName[1];
                        } else if (tempSptName.length == 1) {
                            vCustomerName1 = tempSptName[0];
                        } else {
                        }
                    }
                }
            }

        }

        // 그 다음 해당 holdcode 로 저장된 temp_salecart_deliveryinfo 테이블에 저장된 정보가 있는지 확인한다.
        if (!GlobalMemberValues.isStrEmpty(mGetHoldCode)) {
            String strQuery = "select customerId, customerName, customerEmail, customerAddr1, customerAddr2, customerCity, customerState, customerZip, customermemo, " +
                    " deliveryday, deliverytime, deliverydate, deliverytakeaway " +
                    " from temp_salecart_deliveryinfo " +
                    " where holdcode = '" + mGetHoldCode + "' ";
            GlobalMemberValues.logWrite(TAG, "strQuery2 : " + strQuery + "\n");
            Cursor memberCursor = dbInit.dbExecuteRead(strQuery);
            if (memberCursor.getCount() > 0 && memberCursor.moveToFirst()) {
                vCustomerId = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(0), 1);
                vCustomerName = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(1), 1);
                vCustomerEmail = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(2), 1);
                vCustomerAddr1 = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(3), 1);
                vCustomerAddr2 = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(4), 1);
                vCustomerCity = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(5), 1);
                vCustomerState = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(6), 1);
                vCustomerZip = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(7), 1);
                vCustomerMemo = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(8), 1);

                if (!GlobalMemberValues.isStrEmpty(vCustomerName)) {
                    String[] tempSptName = vCustomerName.split(" ");
                    if (tempSptName.length > 1) {
                        vCustomerName1 = tempSptName[0];
                        vCustomerName2 = tempSptName[1];
                    } else if (tempSptName.length == 1) {
                        vCustomerName1 = tempSptName[0];
                    } else {
                    }
                }

                vDeliveryDay = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(9), 1);
                vDeliveryTime = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(10), 1);
                vDeliveryDate = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(11), 1);

                vDeliveryTakeaway = GlobalMemberValues.getDBTextAfterChecked(memberCursor.getString(12), 1);

            }
        }

        // TextView -------------------------------------------------------------------------------------------------------
        customerIdTextView = (TextView)findViewById(R.id.customerIdTextView);
        customerIdTextView.setTextSize(customerIdTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerNameTextView = (TextView)findViewById(R.id.customerNameTextView);
        customerNameTextView.setTextSize(customerNameTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerEmailTextView = (TextView)findViewById(R.id.customerEmailTextView);
        customerEmailTextView.setTextSize(customerEmailTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr1TextView = (TextView)findViewById(R.id.customerAddr1TextView);
        customerAddr1TextView.setTextSize(customerAddr1TextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerAddr2TextView = (TextView)findViewById(R.id.customerAddr2TextView);
        customerAddr2TextView.setTextSize(customerAddr2TextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerCityTextView = (TextView)findViewById(R.id.customerCityTextView);
        customerCityTextView.setTextSize(customerCityTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerStateTextView = (TextView)findViewById(R.id.customerStateTextView);
        customerStateTextView.setTextSize(customerStateTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerZipTextView = (TextView)findViewById(R.id.customerZipTextView);
        customerZipTextView.setTextSize(customerZipTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerDeliveryDayTextView = (TextView)findViewById(R.id.customerDeliveryDayTextView);
        customerDeliveryDayTextView.setTextSize(customerDeliveryDayTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerDeilveryDaySearchTextView = (TextView) findViewById(R.id.customerDeilveryDaySearchTextView);
        customerDeilveryDaySearchTextView.setTextSize(customerDeilveryDaySearchTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerDeilveryDaySearchTextView.setOnClickListener(customerAddClickListener);
        customerDeilveryDaySearchTextView.setText(vDeliveryDay);

        customerDeilveryTimeSearchTextView = (TextView) findViewById(R.id.customerDeilveryTimeSearchTextView);
        customerDeilveryTimeSearchTextView.setTextSize(customerDeilveryTimeSearchTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerDeilveryTimeSearchTextView.setOnClickListener(customerAddClickListener);
        customerDeilveryTimeSearchTextView.setText(vDeliveryTime);

        customerMemoTextView = (TextView)findViewById(R.id.customerMemoTextView);
        customerMemoTextView.setTextSize(customerMemoTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // ----------------------------------------------------------------------------------------------------------------

        // EditText -------------------------------------------------------------------------------------------------------
        customerIdEditText = (EditText) findViewById(R.id.customerIdEditText);
        customerIdEditText.setTextSize(customerIdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        if (GlobalMemberValues.isCustomerOnlineCheck()) {
            customerIdEditText.setOnFocusChangeListener(customerIdChangeFocus);
        }
        customerIdEditText.setOnTouchListener(customerIdTouch);
        customerIdEditText.setText(vCustomerId);

        customerNameEditText = (EditText) findViewById(R.id.customerNameEditText);
        customerNameEditText.setTextSize(customerNameEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerNameEditText.setText(vCustomerName1);

        customerNameEditText2 = (EditText) findViewById(R.id.customerNameEditText2);
        customerNameEditText2.setTextSize(customerNameEditText2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerNameEditText2.setText(vCustomerName2);

        customerEmailEditText = (EditText) findViewById(R.id.customerEmailEditText);
        customerEmailEditText.setTextSize(customerEmailEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerEmailEditText.setText(vCustomerEmail);

        customerAddr1EditText = (EditText) findViewById(R.id.customerAddr1EditText);
        customerAddr1EditText.setTextSize(customerAddr1EditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerAddr1EditText.setText(vCustomerAddr1);

        customerAddr2EditText = (EditText) findViewById(R.id.customerAddr2EditText);
        customerAddr2EditText.setTextSize(customerAddr2EditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerAddr2EditText.setText(vCustomerAddr2);

        customerCityEditText = (EditText) findViewById(R.id.customerCityEditText);
        customerCityEditText.setTextSize(customerCityEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerCityEditText.setText(vCustomerCity);

        customerZipEditText = (EditText) findViewById(R.id.customerZipEditText);
        customerZipEditText.setTextSize(customerZipEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerZipEditText.setText(vCustomerZip);

        customerMemoEditText = (EditText)findViewById(R.id.customerMemoEditText);
        //customerMemoEditText.setOnTouchListener(customerMemoEditTextTouchListener);
        //customerMemoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        customerMemoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        customerMemoEditText.setText(vCustomerMemo);


        // 배송비 책정 -----------------------------------------------------------------------------------------------------------------------------
        // 배송비
        double deliveryFee = 0.0;

        // 기본 배송비가 얼마인지 구한다.
        double store_deliverycharge = GlobalMemberValues.getDoubleAtString(
                dbInit.dbExecuteReadReturnString("select deliverycharge from salon_storegeneral")
        );

        // 배송비 무료 기준금액
        double store_deliveryfreemoney = GlobalMemberValues.getDoubleAtString(
                dbInit.dbExecuteReadReturnString("select deliveryfree from salon_storegeneral")
        );

        // 장바구니에 담긴 총액을 구한다.
        double tempTotalValue = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString());
        if (tempTotalValue > 0) {
            if (tempTotalValue < store_deliveryfreemoney) {
                deliveryFee = store_deliverycharge;
            }
        }

        deliveryfeeEv = (EditText)findViewById(R.id.deliveryfeeEv);
        //customerMemoEditText.setOnTouchListener(customerMemoEditTextTouchListener);
        //customerMemoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        deliveryfeeEv.setText(GlobalMemberValues.getCommaStringForDouble(deliveryFee + ""));
        // ----------------------------------------------------------------------------------------------------------------------------------------

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

        // State
        if (!GlobalMemberValues.isStrEmpty(vCustomerState)) {
            int tempPNPosition = 0;
            for (int i = 0; i < GlobalMemberValues.STATEPROVINCE.length; i++) {
                if (vCustomerState.equals(GlobalMemberValues.STATEPROVINCE[i][0])) {
                    tempPNPosition = i;
                }
            }
            customerStateSpinner.setSelection(tempPNPosition);
        }
        // ----------------------------------------------------------------------------------------------------------------

        //pickupRadioButton = (RadioButton)findViewById(R.id.pickupRadioButton);
        //pickupRadioButton.setTextSize(pickupRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        statusChangeView(true);

        // Button ---------------------------------------------------------------------------------------------------------
        customerDeliveryTakeawaySubmitButton = (Button)findViewById(R.id.customerDeliveryTakeawaySubmitButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerDeliveryTakeawaySubmitButton.setText("");
            customerDeliveryTakeawaySubmitButton.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            customerDeliveryTakeawaySubmitButton.setTextSize(
                    customerDeliveryTakeawaySubmitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        deliverytakeawayInfoCloseBtn = (Button)findViewById(R.id.hereToGoInfoCloseBtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            deliverytakeawayInfoCloseBtn.setText("");
            deliverytakeawayInfoCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            deliverytakeawayInfoCloseBtn.setTextSize(
                    deliverytakeawayInfoCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        customerWalkInButotn = (Button)findViewById(R.id.customerWalkInButotn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //customerAddCloseBtn.setText("");
            //customerAddCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            customerWalkInButotn.setTextSize(
                    customerWalkInButotn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        if (GlobalMemberValues.getStationType().equals("Q")) {
            customerWalkInButotn.setVisibility(View.GONE);
        }

        deliveryFreeButotn = (Button)findViewById(R.id.deliveryFreeButotn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //customerAddCloseBtn.setText("");
            //customerAddCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            deliveryFreeButotn.setTextSize(
                    deliveryFreeButotn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        deliverytakeawayInfoCloseBtn.setOnClickListener(customerAddClickListener);
        customerDeliveryTakeawaySubmitButton.setOnClickListener(customerAddClickListener);
        customerWalkInButotn.setOnClickListener(customerAddClickListener);
        deliveryFreeButotn.setOnClickListener(customerAddClickListener);
        // ----------------------------------------------------------------------------------------------------------------

        // Customer Info Show 가 No 일 경우 바로 다음 스텝으로..
        if (!GlobalMemberValues.isCustomerInfoShow()) {
            String tempStr = customerIdEditText.getText().toString();
            if (GlobalMemberValues.GLOBAL_CUSTOMERINFO == null || GlobalMemberValues.isStrEmpty(tempStr)) {
                customerIdEditText.setText("Walk In");
            }
            insTempSalecartDeliveryinfo();
        }
    }

    View.OnTouchListener customerMemoEditTextTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };


    public void statusChangeView(boolean paramBool) {
        String tempBgColor = "#ffffff";
        if (paramBool) {
            tempBgColor = "#ffffff";
        } else {
            tempBgColor = "#a2a2a2";
        }
        customerNameEditText.setEnabled(paramBool);
        customerNameEditText.setBackgroundColor(Color.parseColor(tempBgColor));

        customerNameEditText2.setEnabled(paramBool);
        customerNameEditText2.setBackgroundColor(Color.parseColor(tempBgColor));

        customerEmailEditText.setEnabled(paramBool);
        customerEmailEditText.setBackgroundColor(Color.parseColor(tempBgColor));

        customerDeilveryDaySearchTextView.setEnabled(paramBool);
        customerDeilveryDaySearchTextView.setBackgroundColor(Color.parseColor(tempBgColor));

        customerDeilveryTimeSearchTextView.setEnabled(paramBool);
        customerDeilveryTimeSearchTextView.setBackgroundColor(Color.parseColor(tempBgColor));

        customerAddr1EditText.setEnabled(paramBool);
        customerAddr1EditText.setBackgroundColor(Color.parseColor(tempBgColor));

        customerAddr2EditText.setEnabled(paramBool);
        customerAddr2EditText.setBackgroundColor(Color.parseColor(tempBgColor));

        customerCityEditText.setEnabled(paramBool);
        customerCityEditText.setBackgroundColor(Color.parseColor(tempBgColor));

        customerStateSpinner.setEnabled(paramBool);
        customerStateSpinner.setBackgroundColor(Color.parseColor(tempBgColor));

        customerZipEditText.setEnabled(paramBool);
        customerZipEditText.setBackgroundColor(Color.parseColor(tempBgColor));
    }

    EditText.OnTouchListener customerIdTouch = new EditText.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String tempStr = customerIdEditText.getText().toString();
            if (tempStr.equals("Walk In")) {
                customerIdEditText.setText("");
            }
            return false;
        }
    };

    EditText.OnFocusChangeListener customerIdChangeFocus = new EditText.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String tempUid = customerIdEditText.getText().toString();
                final String checkUid = tempUid;

                if (!GlobalMemberValues.isStrEmpty(tempUid)) {
                    if (tempUid.equals("Walk In")) {
                        return;
                    }

                    // 프로그래스 바를 실행~
                    memCheckProDial = ProgressDialog.show(
                            context, "Customer Check", "Customer data is checking In cloud.....", true, false
                    );

                    selectedItemCustomerInfo = null;
                    mMemFirstName = "";
                    mMemLastName = "";
                    mMemEmail = "";
                    mMemAddr1 = "";
                    mMemAddr2 = "";
                    mMemCity = "";
                    mMemState = "";
                    mMemZip = "";

                    customerNameEditText.setText("");
                    customerNameEditText2.setText("");
                    customerEmailEditText.setText("");
                    customerAddr1EditText.setText("");
                    customerAddr2EditText.setText("");
                    customerCityEditText.setText("");
                    customerZipEditText.setText("");

                    GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("");

                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                            String memberCheckInfo = GlobalMemberValues.getDataAfterMemberCheckForDeliveryTakeaway(context, checkUid);
                            if (GlobalMemberValues.isStrEmpty(memberCheckInfo)) {
                                GlobalMemberValues.displayDialog(getApplicationContext(), "Waraning", "You can not check the ID in the Cloud\n(Check the Internet or Network Status)", "Close");
                            } else {
                                //GlobalMemberValues.logWrite("ApiReturnValue", "처리전 : " + memberCheckInfo + "\n");
                                String tempStr = GlobalMemberValues.getReplaceText(memberCheckInfo, "|", "");
                                //GlobalMemberValues.logWrite("ApiReturnValue", "처리전 : " + tempStr + "\n");
                                if (!GlobalMemberValues.isStrEmpty(tempStr)) {
                                    String[] memberCheckInfoArr = memberCheckInfo.split(GlobalMemberValues.STRSPLITTER1);
                                    if (memberCheckInfoArr.length > 0) {
                                        mMemFirstName = memberCheckInfoArr[0];
                                    }
                                    if (memberCheckInfoArr.length > 1) {
                                        mMemLastName = memberCheckInfoArr[1];
                                    }
                                    if (memberCheckInfoArr.length > 2) {
                                        mMemEmail = memberCheckInfoArr[2];
                                    }
                                    if (memberCheckInfoArr.length > 3) {
                                        mMemAddr1 = memberCheckInfoArr[3];
                                    }
                                    if (memberCheckInfoArr.length > 4) {
                                        mMemAddr2 = memberCheckInfoArr[4];
                                    }
                                    if (memberCheckInfoArr.length > 5) {
                                        mMemCity = memberCheckInfoArr[5];
                                    }
                                    if (memberCheckInfoArr.length > 6) {
                                        mMemState = memberCheckInfoArr[6];
                                    }
                                    if (memberCheckInfoArr.length > 7) {
                                        mMemZip = memberCheckInfoArr[7];
                                    }

                                    String[] memReturnValuesArr = memberCheckInfo.split("-WANHAYE-");
                                    if (memReturnValuesArr.length > 0) {
                                        memReturnVal1 = memReturnValuesArr[0];
                                    }
                                    if (memReturnValuesArr.length > 1) {
                                        memReturnVal2 = memReturnValuesArr[1];
                                    }
                                } else {
                                    selectedItemCustomerInfo = null;
                                    mMemFirstName = "";
                                    mMemLastName = "";
                                    mMemEmail = "";
                                    mMemAddr1 = "";
                                    mMemAddr2 = "";
                                    mMemCity = "";
                                    mMemState = "";
                                    mMemZip = "";
                                }
                            }
                            // ---------------------------------------------------------------------------------
                            // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                            memcheckhandler.sendEmptyMessage(0);
                            // ---------------------------------------------------------------------------------
                        }
                    });
                    thread.start();
                }
            }
        }
    };

    private Handler memcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagMemCheck == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                customerNameEditText.setText(mMemFirstName);
                customerNameEditText2.setText(mMemLastName);
                customerEmailEditText.setText(mMemEmail);
                customerAddr1EditText.setText(mMemAddr1);
                customerAddr2EditText.setText(mMemAddr2);
                customerCityEditText.setText(mMemCity);
                customerZipEditText.setText(mMemZip);

                if (!GlobalMemberValues.isStrEmpty(mMemState)) {
                    int tempPNPosition = 0;
                    for (int i = 0; i < GlobalMemberValues.STATEPROVINCE.length; i++) {
                        if (mMemState.equals(GlobalMemberValues.STATEPROVINCE[i][0])) {
                            tempPNPosition = i;
                        }
                    }
                    customerStateSpinner.setSelection(tempPNPosition);
                }

                // 회원정보 변수저장
                if (!GlobalMemberValues.isStrEmpty(memReturnVal2)) {
                    String[] memArr = memReturnVal2.split(GlobalMemberValues.STRSPLITTER2);
                    if (memArr.length > 0) {
                        selectedItemCustomerInfo = new TemporaryCustomerInfo(memArr);
                        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText(
                                "Point : " + GlobalMemberValues.getCommaStringForDouble(selectedItemCustomerInfo.memMileage + "")
                        );

                        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
                        globalMemberValues.updateSaleCartDB(selectedItemCustomerInfo);
                    }
                } else {
                    selectedItemCustomerInfo = null;
                    GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("");
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                memCheckProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };

    View.OnClickListener customerAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerDeliveryTakeawaySubmitButton: {

                    if(SystemClock.elapsedRealtime() - mLastClickTime_okbtn < 1000) {
                        return;
                    }
                    mLastClickTime_okbtn = SystemClock.elapsedRealtime();

                    String memUid = customerIdEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(memUid)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Please enter your phone number (customer ID)", "Close");
                        return;
                    }
                    insTempSalecartDeliveryinfo();
                    break;
                }
                case R.id.customerWalkInButotn : {
                    customerIdEditText.setText("Walk In");
                    break;
                }
                case R.id.deliveryFreeButotn : {
                    deliveryfeeEv.setText("0.0");
                    break;
                }
                case R.id.hereToGoInfoCloseBtn: {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.customerDeilveryDaySearchTextView : {
                    String tempCustomerDeliveryDaySh = customerDeilveryDaySearchTextView.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempCustomerDeliveryDaySh)) {
                        tempCustomerDeliveryDaySh = GlobalMemberValues.STR_NOW_DATE;
                    }
                    openDatePickerDialog(tempCustomerDeliveryDaySh);

                    break;
                }
                case R.id.customerDeilveryTimeSearchTextView : {
                    String tempCustomerDeliveryTimeSh = customerDeilveryTimeSearchTextView.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempCustomerDeliveryTimeSh)) {
                        tempCustomerDeliveryTimeSh = GlobalMemberValues.STR_NOW_TIME;
                    }
                    openTimePickerDialog(tempCustomerDeliveryTimeSh);

                    break;
                }
            }
        }
    };

    public void insTempSalecartDeliveryinfo() {
        // Get Type 별 tax free 일 경우
        if (GlobalMemberValues.isTaxFreeByGetType("D")) {
            GlobalMemberValues.setTaxFreeOnAllItems("D");
        }

        mStrInsertQueryVec = new Vector<String>();

        String insCustomerPhoneNum = customerIdEditText.getText().toString();
        String insCustomerName = customerNameEditText.getText().toString() + " " + customerNameEditText2.getText().toString();
        String insCustomerEmail = customerEmailEditText.getText().toString();
        String insDeliveryDay = customerDeilveryDaySearchTextView.getText().toString();
        String insDeliveryTime = customerDeilveryTimeSearchTextView.getText().toString();
        String insAddr1 = customerAddr1EditText.getText().toString();
        String insAddr2 = customerAddr2EditText.getText().toString();
        String insCity = customerCityEditText.getText().toString();
        String insState = mSelectedSpinnerState;
        String insZip = customerZipEditText.getText().toString();
        String insCustomerMemo = customerMemoEditText.getText().toString();

        String insUpdStrQuery = "";

        String tempHoldcode = mGetHoldCode;

        /**
         int tempCnt = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
         "select count(idx) from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' "));

         GlobalMemberValues.logWrite(TAG, "temp_salecart_deliveryinfo 에 있는 수량 : " + tempCnt + "\n");
         **/

        // 먼저 해당 holdcode 로 저장된 데이터가 있는지 확인한다.
        if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                "select count(idx) from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' ")) > 0) {
            insUpdStrQuery = "update temp_salecart_deliveryinfo set " +
                    " customerId = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " customerName = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "', " +
                    " customerPhone = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " customerEmail = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerEmail, 0) + "', " +
                    " customerAddr1 = '" + GlobalMemberValues.getDBTextAfterChecked(insAddr1, 0) + "', " +
                    " customerAddr2 = '" + GlobalMemberValues.getDBTextAfterChecked(insAddr2, 0) + "', " +
                    " customerCity = '" + GlobalMemberValues.getDBTextAfterChecked(insCity, 0) + "', " +
                    " customerState = '" + GlobalMemberValues.getDBTextAfterChecked(insState, 0) + "', " +
                    " customerZip = '" + GlobalMemberValues.getDBTextAfterChecked(insZip, 0) + "', " +
                    " deliveryday = '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryDay, 0) + "', " +
                    " deliverytime = '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryTime, 0) + "', " +
                    " deliverydate = '" + insDeliveryDay + " " + insDeliveryTime + ":00" + "', " +
                    " deliverytakeaway = '" + GlobalMemberValues.getDBTextAfterChecked(mTempDeliveryTakeaway, 0) + "', " +
                    " customermemo = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerMemo, 0) + "', " +
                    " phoneorder = '" + SelectGetFoodType.mCheckedPhoneorder + "' " +
                    " where holdcode = '" + mGetHoldCode + "' ";
        } else {
            insUpdStrQuery = " insert into temp_salecart_deliveryinfo ("+
                    " holdcode, sidx, stcode, " +
                    " customerId, customerName, customerPhone, customerEmail, " +
                    " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                    " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                    " customermemo, phoneorder " +
                    " ) values ( " +
                    " '" + mGetHoldCode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +

                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerEmail, 0) + "', "
                    +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insAddr1, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insAddr2, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCity, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insState, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insZip, 0) + "', " +

                    " '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryDay, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryTime, 0) + "', " +
                    " '" + insDeliveryDay + " " + insDeliveryTime + ":00" + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mTempDeliveryTakeaway, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerMemo, 0) + "', " +
                    " '" + SelectGetFoodType.mCheckedPhoneorder + "' " +
                    ")";
        }
        mStrInsertQueryVec.addElement(insUpdStrQuery);

        for (String tempQuery : mStrInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(mStrInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Waraning", "Database Error", "Close");
        } else {
            // 등록성공
            //GlobalMemberValues.displayDialog(context, "Information", "Successfully Registered", "Close");
            if (!GlobalMemberValues.isStrEmpty(mPayYN) && mPayYN.equals("Y")) {
                GlobalMemberValues.setFrameLayoutVisibleChange("payment");
                GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED = "Y";

                // 배송비 추가처리
                double tempDeliveryFee = GlobalMemberValues.getDoubleAtString(deliveryfeeEv.getText().toString());
                Payment.setDeliveryPickUpLn("Delivery Fee ", tempDeliveryFee);

                // 결제창 상단 수령방법 기재
                if (Payment.paymentTopGetFoodTypeTv != null) {
                    Payment.paymentTopGetFoodTypeTv.setText("Delivery");
                }

                // Payment 창에 금액 업데이트
                Payment.setPaymentPrice();

            } else {
                GlobalMemberValues.setCustomerInfoInit();
                MainMiddleService.clearOnlyListView();
            }

            initView();
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }

            SelectGetFoodType.receiptKitchenPrinting1(tempHoldcode);
        }
    }

    public void openTimePickerDialog(String paramTime) {
        String tempSplitStr[] = null;
        tempSplitStr = paramTime.split(":");
        int tempHour = Integer.parseInt(tempSplitStr[0]);
        int tempMinute = Integer.parseInt(tempSplitStr[1]);
        TimePickerDialog timeDialog = new TimePickerDialog(this, timeSelectListener, tempHour, tempMinute, false);
        timeDialog.show();
    }

    TimePickerDialog.OnTimeSetListener timeSelectListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tempHour = String.valueOf(hourOfDay);
            if ((hourOfDay) < 10) {
                tempHour = "0" + tempHour;
            }
            String tempMinute = String.valueOf(minute);
            if (minute < 10) {
                tempMinute = "0" + tempMinute;
            }
            customerDeilveryTimeSearchTextView.setText(tempHour + ":" + tempMinute);
        }
    };

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
            customerDeilveryDaySearchTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
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

    public void initView() {
        customerIdEditText.setText("");
        customerNameEditText.setText("");
        customerNameEditText2.setText("");
        customerEmailEditText.setText("");

        customerDeilveryDaySearchTextView.setText("");
        customerDeilveryTimeSearchTextView.setText("");

        customerAddr1EditText.setText("");
        customerAddr2EditText.setText("");
        customerCityEditText.setText("");
        customerZipEditText.setText("");

        customerStateSpinner.setSelection(0);

        mGetHoldCode = "";
        mPayYN = "";
    }
}
