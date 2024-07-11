package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.sql.ResultSet;

public class HereToGoInfo extends Activity {
    final String TAG = "HereToGoInfo";

    Activity activity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView saleHistoryTopTitleTextView;
    private TextView customerNameTextView, customerIdTextView;
    private TextView customerMemoTextView, customerToGoFeeTextView;

    private EditText customerNameEditText, customerNameEditText2, customerIdEditText;
    private EditText customerMemoEditText;

    private Button hereToGoSubmitButton, hereToGoInfoCloseBtn;
    private Button customerWalkInButotn, onlinebtn;

    Intent mIntent;
    String mGetHoldCode = "";

    public int mTempFlagMemCheck = 0;
    public ProgressDialog memCheckProDial;

    String mReturnValue = "";

    String mMemFirstName = "";
    String mMemLastName = "";
    String mMemEmail = "";

    String memReturnVal1 = "";
    String memReturnVal2 = "";

    String hereToGoType = "";

    TemporaryCustomerInfo selectedItemCustomerInfo;

    private Long mLastClickTime_okbtn = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_here_to_go_info);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 75;
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
            hereToGoType = mIntent.getStringExtra("heretogotype");
            GlobalMemberValues.logWrite(TAG + "1", "hereToGoType : " + hereToGoType + "\n");
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

        mGetHoldCode = MainMiddleService.mHoldCode;
        GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (인텐트에서 받은것) : " + mGetHoldCode + "\n");

        // 고객이 선택되어 있을 경우 고객정보를 가져온다.
        String vCustomerId = "";
        String vCustomerName = "";
        String vCustomerEmail = "";

        String vCustomerName1 = "";
        String vCustomerName2 = "";

        String vCustomerMemo = "";

        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                vCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                vCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                vCustomerEmail = "";

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
                //Cursor memberCursor = dbInit.dbExecuteRead(strQuery);
                ResultSet memberCursor = MssqlDatabase.getResultSetValue(strQuery);

                try {
                    while (memberCursor.next()) {
                        vCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                        vCustomerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,0), 1);
                        vCustomerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,1), 1);

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
                    //07052024 close result set
                    memberCursor.close();
                } catch (Exception e) {
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
            //Cursor memberCursor = dbInit.dbExecuteRead(strQuery);
            ResultSet memberCursor = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (memberCursor.next()) {
                    vCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,0), 1);
                    vCustomerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,1), 1);
                    vCustomerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,2), 1);
                    vCustomerMemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(memberCursor,8), 1);

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
                //07052024 close resultset
                memberCursor.close();
            } catch (Exception e) {
            }
        }

        // TextView -------------------------------------------------------------------------------------------------------
        saleHistoryTopTitleTextView = (TextView)findViewById(R.id.saleHistoryTopTitleTextView);
        saleHistoryTopTitleTextView.setTextSize(saleHistoryTopTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerIdTextView = (TextView)findViewById(R.id.customerIdTextView);
        customerIdTextView.setTextSize(customerIdTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerNameTextView = (TextView)findViewById(R.id.customerNameTextView);
        customerNameTextView.setTextSize(customerNameTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerMemoTextView = (TextView)findViewById(R.id.customerMemoTextView);
        customerMemoTextView.setTextSize(customerMemoTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        customerToGoFeeTextView = (TextView)findViewById(R.id.customerToGoFeeTextView);
        customerToGoFeeTextView.setTextSize(customerToGoFeeTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        if (hereToGoType.equals("T") || hereToGoType == "T") {
            double store_pickupcharge = GlobalMemberValues.getDoubleAtString(
                    dbInit.dbExecuteReadReturnString("select pickupcharge from salon_storegeneral")
            );
            customerToGoFeeTextView.setText("$" + GlobalMemberValues.getCommaStringForDouble(store_pickupcharge + ""));

            saleHistoryTopTitleTextView.setText("TO GO INFORMATION");
        } else {
            customerToGoFeeTextView.setText("0.0");
            saleHistoryTopTitleTextView.setText("HERE INFORMATION");
        }
        // ----------------------------------------------------------------------------------------------------------------

        // EditText -------------------------------------------------------------------------------------------------------
        customerIdEditText = (EditText) findViewById(R.id.customerIdEditText);
        customerIdEditText.setTextSize(customerIdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        if (GlobalMemberValues.isCustomerOnlineCheck()) {
            customerIdEditText.setOnFocusChangeListener(customerIdChangeFocus);
        }
        customerIdEditText.setOnTouchListener(customerIdTouch);
        customerIdEditText.setText(vCustomerId);

        GlobalMemberValues.logWrite("jjjcustidjjjlog", "vCustomerId : " + customerIdEditText.getText().toString() + "\n");

        customerNameEditText = (EditText) findViewById(R.id.customerNameEditText);
        customerNameEditText.setTextSize(customerNameEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerNameEditText.setText(vCustomerName1);

        customerNameEditText2 = (EditText) findViewById(R.id.customerNameEditText2);
        customerNameEditText2.setTextSize(customerNameEditText2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        customerNameEditText2.setText(vCustomerName2);

        if (GlobalMemberValues.isToGoSale()) {
            customerNameEditText.setHint("Full Name");
            customerNameEditText2.setHint("");
//            customerNameEditText2.setVisibility(View.INVISIBLE);
        }

        customerMemoEditText = (EditText)findViewById(R.id.customerMemoEditText);
        //customerMemoEditText.setOnTouchListener(customerMemoEditTextTouchListener);
        //customerMemoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        customerMemoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        customerMemoEditText.setText(vCustomerMemo);

        // ----------------------------------------------------------------------------------------------------------------

        // Button ---------------------------------------------------------------------------------------------------------
        hereToGoSubmitButton = (Button)findViewById(R.id.hereToGoSubmitButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            hereToGoSubmitButton.setText("");
            hereToGoSubmitButton.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            hereToGoSubmitButton.setTextSize(
                    hereToGoSubmitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        hereToGoInfoCloseBtn = (Button)findViewById(R.id.hereToGoInfoCloseBtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            hereToGoInfoCloseBtn.setText("");
            hereToGoInfoCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            hereToGoInfoCloseBtn.setTextSize(
                    hereToGoInfoCloseBtn.getTextSize()
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

        onlinebtn = (Button)findViewById(R.id.onlinebtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //customerAddCloseBtn.setText("");
            //customerAddCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            onlinebtn.setTextSize(
                    onlinebtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        hereToGoInfoCloseBtn.setOnClickListener(customerAddClickListener);
        hereToGoSubmitButton.setOnClickListener(customerAddClickListener);
        customerWalkInButotn.setOnClickListener(customerAddClickListener);
        onlinebtn.setOnClickListener(customerAddClickListener);
        // ----------------------------------------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...0" + "\n");

        // Customer Info Show 가 No 일 경우 바로 다음 스텝으로..
        if (!GlobalMemberValues.isCustomerInfoShow()) {
            GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...1" + "\n");

            String tempStr = customerIdEditText.getText().toString();
            if (GlobalMemberValues.GLOBAL_CUSTOMERINFO == null || GlobalMemberValues.isStrEmpty(tempStr)) {
                customerIdEditText.setText("Walk In");
            }
            hereToGoSubmit();
        } else {
            GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...2" + "\n");

            boolean isOpen = false;

            if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
                GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...3" + "\n");

                GlobalMemberValues.logWrite("jjjwanhayejjj", "여기..." + "\n");
                GlobalMemberValues.logWrite("jjjwanhayejjj", "table idx : " + TableSaleMain.mTableIdxArrList.toString() + "\n");
                isOpen = false;
                String tempQuickSaleyn = "N";
                String tempTableIdx = "";
                if (TableSaleMain.mTableIdxArrList.size() > 1) {
                    int tempi = 0;
                    for (int i = 0; i < TableSaleMain.mTableIdxArrList.size(); i++) {
                        tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mTableIdxArrList.get(i), "T", "");
                        tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                        );
                        if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                            tempi++;
                        }
                    }
                    if (tempi > 0) {
                        isOpen = true;
                    }
                } else {
                    tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mTableIdxArrList.get(0), "T", "");
                    tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                    );
                    if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                        isOpen = true;
                    }
                }
            } else {
                GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...4" + "\n");

                switch (hereToGoType) {
                    case "H" : {
                        if (GlobalMemberValues.isCustomer_info_here_yn()) {
                            isOpen = true;
                        }
                        break;
                    }
                    case "T" : {
                        if (GlobalMemberValues.isCustomer_info_togo_yn()) {
                            isOpen = true;
                        }
                        break;
                    }
                    case "D" : {
                        if (GlobalMemberValues.isCustomer_info_delivery_yn()) {
                            isOpen = true;
                        }
                        break;
                    }
                }
            }

            if (GlobalMemberValues.mIsClickPayment) {
                isOpen = false;
            }

            GlobalMemberValues.logWrite("jjjtestlogjjj", "mIsClickSendToKitchen : " + GlobalMemberValues.mIsClickSendToKitchen + "\n");
            GlobalMemberValues.logWrite("jjjtestlogjjj", "mGetHoldCode : " + mGetHoldCode + "\n");

            if (GlobalMemberValues.mIsClickSendToKitchen) {
                if (!GlobalMemberValues.isStrEmpty(mGetHoldCode)) {
                    int tempCustInfoCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select count(*) from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' "
                    ));

                    GlobalMemberValues.logWrite("jjjtestlogjjj", "tempCustInfoCnt : " + tempCustInfoCnt + "\n");

                    if (tempCustInfoCnt > 0) {
                        isOpen = false;
                    }
                }
            }

            if (!isOpen) {
                String tempStr = customerIdEditText.getText().toString();
                if (GlobalMemberValues.GLOBAL_CUSTOMERINFO == null) {
                    if (GlobalMemberValues.isStrEmpty(tempStr)) {
                        customerIdEditText.setText("Walk In");
                    }
                }
                hereToGoSubmit();
            }
        }
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
                    memReturnVal1 = "";
                    memReturnVal2 = "";
                    customerNameEditText.setText("");
                    customerNameEditText2.setText("");
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

                                    mMemFirstName = memberCheckInfoArr[0];

                                    if (memberCheckInfoArr.length > 0) {
                                        mMemLastName = memberCheckInfoArr[1];
                                    }
                                    if (memberCheckInfoArr.length > 1) {
                                        mMemEmail = memberCheckInfoArr[2];
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
                                    memReturnVal1 = "";
                                    memReturnVal2 = "";
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
                // 고객 이름 넣기
                customerNameEditText.setText(mMemFirstName);
                customerNameEditText2.setText(mMemLastName);

                // 회원정보 변수저장
                if (!GlobalMemberValues.isStrEmpty(memReturnVal2)) {
                    String[] memArr = memReturnVal2.split(GlobalMemberValues.STRSPLITTER2);
                    if (memArr.length > 0) {
                        selectedItemCustomerInfo = new TemporaryCustomerInfo(memArr);
                        // jihun add 1013
                        if (selectedItemCustomerInfo.memMileage != null){
                            GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText(
                                    "Point : " + GlobalMemberValues.getCommaStringForDouble(selectedItemCustomerInfo.memMileage + "")
                            );
                        } else {
                            GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText(
                                    "Point : " + GlobalMemberValues.getCommaStringForDouble("0.00" + "")
                            );
                        }


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

    public void hereToGoSubmit() {
        String tempCustName1 = customerNameEditText.getText().toString();
        String tempCustName2 = customerNameEditText2.getText().toString();
        String tempCustEmail = mMemEmail;

        String tempCustName = tempCustName1 + " " + tempCustName2;

        String tempCustMemo =  customerMemoEditText.getText().toString();

        String memUid = customerIdEditText.getText().toString();

        GlobalMemberValues.mTempCustomerInfo = memUid + "-JJJ-" + tempCustName + "-JJJ-" + tempCustMemo;

        GlobalMemberValues.logWrite("tempcustmemojjjinfo", "GlobalMemberValues.mTempCustomerInfo...1 : " + GlobalMemberValues.mTempCustomerInfo + "\n");

//        if (customerIdEditText.getText().toString() + tempCustName1 + tempCustName2 == "") {
//            GlobalMemberValues.displayDialog(context, "Waraning", "Please enter your phone number (customer ID)", "Close");
//            return;
//        }

        GlobalMemberValues.logWrite("jjjaaabbb", "hereToGoType : " + hereToGoType + "\n");


        // Get Type 별 tax free 일 경우
        if (GlobalMemberValues.isTaxFreeByGetType(hereToGoType)) {
            GlobalMemberValues.setTaxFreeOnAllItems(hereToGoType);
        }

        if (GlobalMemberValues.isStrEmpty(memUid)) {
            if (!GlobalMemberValues.isCustomer_info_here_yn() &&
            !GlobalMemberValues.isCustomer_info_togo_yn() &&
            !GlobalMemberValues.isCustomer_info_delivery_yn()){
                // Customer Info Show off 상태. 또는 Here, Togo, Delivery 모두 off 상태
            } else {
                GlobalMemberValues.displayDialog(context, "Waraning", "Please enter your phone number (customer ID)", "Close");
                return;
            }

        }

        if (GlobalMemberValues.isToGoSale()){
            if (customerIdEditText.getText().toString() == "Walk In" && tempCustName1 + tempCustName2 == "") {
                GlobalMemberValues.displayDialog(context, "Waraning", "Please enter customer name", "Close");
                return;
            }
        }

        if (GlobalMemberValues.mIsClickSendToKitchen) {
            GlobalMemberValues.logWrite("orderkitchenprintinglog", "여기에..." + "\n");
            MainActivity.clickSendToKitchen();
        }

        SelectGetFoodType.insPickUpInfo(hereToGoType, memUid, tempCustName, tempCustEmail, tempCustMemo);


        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }

        GlobalMemberValues.mIsClickSendToKitchen = false;
        GlobalMemberValues.mIsClickPayment = false;
    }

    View.OnClickListener customerAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hereToGoSubmitButton: {
                    if(SystemClock.elapsedRealtime() - mLastClickTime_okbtn < 1000) {
                        return;
                    }
                    mLastClickTime_okbtn = SystemClock.elapsedRealtime();
                    hereToGoSubmit();
                    break;
                }
                case R.id.customerWalkInButotn : {
                    customerIdEditText.setText("TO GO");
                    break;
                }
                case R.id.onlinebtn : {
//                    customerIdEditText.setText("Online");
                    Intent otherpaymentIntent = new Intent(context.getApplicationContext(), PaymentSelectOtherPay.class);
// Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
//otherpaymentIntent.putExtra("ParentMainMiddleService", this.getClass());
                    otherpaymentIntent.putExtra("from_custinfoYN", "Y");
                    otherpaymentIntent.putExtra("selectedpayment", "");
// -------------------------------------------------------------------------------------
                    startActivityForResult(otherpaymentIntent,9999);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.hereToGoInfoCloseBtn: {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999){
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String companyName = data.getStringExtra("select_txt");
            customerIdEditText.setText(companyName);
        }
    }
}
