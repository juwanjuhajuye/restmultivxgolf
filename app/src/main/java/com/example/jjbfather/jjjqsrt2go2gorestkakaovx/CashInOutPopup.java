package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class CashInOutPopup extends Activity {
    final String TAG = "CashInOutPopupLog";

    private LinearLayout parentLn;
    Intent mIntent;

    static Activity mActivity;
    static Context context;

    LinearLayout cashinoutReasonLn;

    TextView cashInOutPoupuLogoutTv, cashInOutPoupuPreviousListTv;

    static TextView cashInOutAmountTv;
    static TextView cashInOutReasonEv;

    TextView overshortTv;

    static TextView cashInOutOverShortTv, cashInOutAllCashTotalTv;

    TextView cashInOutStartCashTotalTv, cashInOutCashInTotalTv, cashInOutCashOutTotalTv, cashInOutSalesCashTotalTv;
    TextView cashInOutEndCashTv;
    TextView cashInOutCreditCardTotalTv;
    TextView cashInOutCheckTotalTv, cashInOutPointTotalTv;
    TextView cashInOutGiftCardTotalTv;
    TextView cashInOutEmployeeNameTv;

    TextView cashAmtTv01,cashAmtTv02,cashAmtTv03,cashAmtTv04,cashAmtTv05,cashAmtTv06;
    TextView cashAmtTv07,cashAmtTv08,cashAmtTv09,cashAmtTv10,cashAmtTv11,cashAmtTv12;
    TextView cashAmtTv13,cashAmtTv14,cashAmtTv15,cashAmtTv16;

//    static TextView cashSuEv01,cashSuEv02,cashSuEv03,cashSuEv04,cashSuEv05,cashSuEv06;
//    static TextView cashSuEv07,cashSuEv08,cashSuEv09,cashSuEv10,cashSuEv11,cashSuEv12;
//    static TextView cashSuEv13,cashSuEv14,cashSuEv15,cashSuEv16;

    TextView tempTitleTv1,tempTitleTv2,tempTitleTv3,tempTitleTv4,tempTitleTv5;
    TextView tempTitleTv6,tempTitleTv7,tempTitleTv8,tempTitleTv9,tempTitleTv10;
    TextView tempTitleTv11,tempTitleTv12,tempTitleTv13,tempTitleTv14,tempTitleTv15;

    LinearLayout cashSuLn01,cashSuLn02,cashSuLn03,cashSuLn04,cashSuLn05,cashSuLn06;
    LinearLayout cashSuLn07,cashSuLn08,cashSuLn09,cashSuLn10,cashSuLn11,cashSuLn12;
    LinearLayout cashSuLn13,cashSuLn14,cashSuLn15,cashSuLn16;

    Button cashInOutPopupCloseButton;
    Button cashinoutAddButton, cashinoutRemoveButton, cashinoutDetailViewButton, cashinoutGotosaleButton, cashinoutCashDrawerButton, cashinout_inputtype;
    Button cashinoutCashoutButton;

    ToggleButton cashInOutTypeTb;

    // Reason 히스토리 리스트뷰
    private ListView cashInOutHistoryLv;
    // ListView 에 Reason 내역 붙일 때 필요한 객체들
    TemporaryCashInOutReasonHistory mTempCashInOutReasonHistoryList;
    public static ArrayList<TemporaryCashInOutReasonHistory> mGeneralArrayList;
    public static CashInOutReasonHistoryListAdapter mCashInOutReasonHistoryListAdapter;

    // 검색부분 관련객체들
    Spinner cashinoutReasonSpinner;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    String selectedItemSpinner = "";

    static int selectedPosition = -1;

    // DB 객체 선언
    static DatabaseInit dbInit;

    public ProgressDialog itemProDial;
    public int mTempFlagItemDown = 0;

    public String mSelectedSpinnerString = "";

    View selectedListViewItemView = null;

    static JSONObject jsonroot = null;

    public static ProgressDialog proDial;

    public static boolean isMemoOpen = false;

    JSONObject mJsonForPrint = null;

    static ProgressDialog mProgressDialog;

    String direxetypeStr = "";

    static JSONArray cashinout_inputType_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out_popup);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 95;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 92;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 95;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.cashInOutPopupLn);
        parentLn.setLayoutParams(parentLnParams);

        direxetypeStr = "";

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            direxetypeStr = mIntent.getStringExtra("direxetype");
            GlobalMemberValues.logWrite("direxetypeStrLog", "direxetypeStr : " + direxetypeStr + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            closeCashInOutPopup();
        }

        if (GlobalMemberValues.LOGIN_EMPLOYEE_ID.toUpperCase().equals("ADMIN")) {
            if (proDial != null && proDial.isShowing()) {
                proDial.dismiss();
            }
            mActivity.finish();
            return;
        } else {


            // 092022 ------------------------------------------------------------------------------------------------------------
            // cash in / out 을 사용하지 않는 스테이션일 경우
            if (!GlobalMemberValues.isCashInOutPossible()) {
                Vector<String> strInsertQueryVec = new Vector<String>();
                String insStrQuery = "";

                String jjj_type = "SC";
                String jjj_tempInoutMoney = "0";
                String jjj_tempReason = "Starting cash";
                if (direxetypeStr == null) direxetypeStr = "";
                if (direxetypeStr.equals("endingcash")) {
                    // ending cash 로 들어왔는데, starting cash 가 없을 경우 starting cash 를 등록한다.
                    if (getCashInout() == 0) {
                        insStrQuery = " insert into salon_sales_cashintout_history (" +
                                " scode, sidx, stcode, employeeIdx, employeeName, inouttype, inoutmoney, inoutreason, cashoutnum " +
                                " ) values ( " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked("+", 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked("Starting cash", 0) + "', " +
                                " '0' " +
                                " ) ";
                        strInsertQueryVec.addElement(insStrQuery);
                    }

                    String sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and cashoutnum = 0 " +
                            " and not(inoutreason = 'Ending cash')";
                    jjj_tempInoutMoney = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
                    jjj_tempReason = "Ending cash";
                    jjj_type = "EC";
                } else {
                    jjj_tempInoutMoney = "0";
                    jjj_tempReason = "Starting cash";
                    jjj_type = "SC";
                }

                insStrQuery = " insert into salon_sales_cashintout_history (" +
                        " scode, sidx, stcode, employeeIdx, employeeName, inouttype, inoutmoney, inoutreason, cashoutnum " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("+", 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(jjj_tempInoutMoney, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(jjj_tempReason, 0) + "', " +
                        " '0' " +
                        " ) ";
                strInsertQueryVec.addElement(insStrQuery);

                for (String tempQuery : strInsertQueryVec) {
                    GlobalMemberValues.logWrite("CashInOutReason", "query : " + tempQuery + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                } else {
                    if (jjj_type.equals("SC")) {
                        closeCashInOutPopup();
                    } else {
                        cashInOutExec();
                    }
                }
            }
            // -------------------------------------------------------------------------------------------------------------------




            // 프로그래스 바를 실행~
//            itemProDial = ProgressDialog.show(context, GlobalMemberValues.ANDROIDPOSNAME, "Cash In / Out Page is Loading...", true, false);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    startingHandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();
        }
        GlobalMemberValues.b_no_dbcompact = false;
    }

    private Handler startingHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            setContents();
            setReasonClearEditText();
            setInit();
            setFields(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);

            // 06212023
            saveStartingCashEndingCashCheck();
            // -------------------------------------------------------------------------------------

//            if (itemProDial != null) {
//                itemProDial.dismiss();
//            }
        }
    };

    // 06212023
    public void saveStartingCashEndingCashCheck() {
        if (!GlobalMemberValues.isCashInOutStartEndUse()) {
            if (getCashInout() == 0) {
                saveStartingEndingCash("S");
            } else {
                saveStartingEndingCash("E");
            }
        }
    }

    // 06212023
    public void saveStartingEndingCash(String paramValue) {
        if (paramValue.equals("S")) {
            mSelectedSpinnerString = "Starting cash";
            cashInOutAmountTv.setText("0");
            insertCashInOutReason();

            checkCashInOut();
        } else {
            String totalCash = cashInOutAllCashTotalTv.getText().toString();

            mSelectedSpinnerString = "Ending cash";
            cashInOutAmountTv.setText(totalCash);
            insertCashInOutReason();
        }
    }

    public void setContents() {
        if (BatchSummary.mActivity != null && !BatchSummary.mActivity.isFinishing()) {
            BatchSummary.finishActivity();
        }

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            finish();
            return;
        }

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. ------------------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardHide();
            }
        });
        // ---------------------------------------------------------------------------------------------------------------

        dbInit = new DatabaseInit(context);

        // 자동 배치
        //CommandButton.openBatchSummary("Y");

        cashinoutReasonLn = (LinearLayout) findViewById(R.id.cashinoutReasonLn);
        cashinoutReasonLn.setVisibility(View.GONE);

        // 상단 Logout Textview 버튼 --------------------------------------------------------------------------------------
        cashInOutPoupuLogoutTv = (TextView)findViewById(R.id.cashInOutPoupuLogoutTv);
        cashInOutPoupuLogoutTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutPoupuLogoutTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        SpannableString logoutTvContent = new SpannableString("LOG OUT");
        logoutTvContent.setSpan(new UnderlineSpan(), 0, logoutTvContent.length(), 0);
        cashInOutPoupuLogoutTv.setText(logoutTvContent);
        // ---------------------------------------------------------------------------------------------------------------

        // 하단 previous list Textview 버튼 -------------------------------------------------------------------------------
        cashInOutPoupuPreviousListTv = (TextView)findViewById(R.id.cashInOutPoupuPreviousListTv);
        cashInOutPoupuPreviousListTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutPoupuPreviousListTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        SpannableString previousListTvContent = new SpannableString("Previous End-Bank");
        previousListTvContent.setSpan(new UnderlineSpan(), 0, previousListTvContent.length(), 0);
        cashInOutPoupuPreviousListTv.setText(previousListTvContent);
        // ---------------------------------------------------------------------------------------------------------------

        cashInOutAmountTv = (TextView) findViewById(R.id.cashInOutAmountTv);
        cashInOutAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutReasonEv = (TextView) findViewById(R.id.cashInOutReasonEv);
        cashInOutReasonEv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutReasonEv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashInOutReasonEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashInOutPopup.isMemoOpen = true;
                openKeyinMemo();
            }
        });

        cashInOutStartCashTotalTv = (TextView)findViewById(R.id.cashInOutStartCashTotalTv);
        cashInOutStartCashTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutStartCashTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutCashInTotalTv = (TextView)findViewById(R.id.cashInOutCashInTotalTv);
        cashInOutCashInTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutCashInTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutCashOutTotalTv = (TextView)findViewById(R.id.cashInOutCashOutTotalTv);
        cashInOutCashOutTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutCashOutTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutSalesCashTotalTv = (TextView)findViewById(R.id.cashInOutSalesCashTotalTv);
        cashInOutSalesCashTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutSalesCashTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutAllCashTotalTv = (TextView)findViewById(R.id.cashInOutAllCashTotalTv);
        cashInOutAllCashTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutAllCashTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutEndCashTv = (TextView)findViewById(R.id.cashInOutEndCashTv);
        cashInOutEndCashTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutEndCashTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutOverShortTv = (TextView)findViewById(R.id.cashInOutOverShortTv);
        cashInOutOverShortTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutOverShortTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        overshortTv = (TextView)findViewById(R.id.overshortTv);
        overshortTv.setTextSize(GlobalMemberValues.globalAddFontSize() + overshortTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutCreditCardTotalTv = (TextView)findViewById(R.id.cashInOutCreditCardTotalTv);
        cashInOutCreditCardTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutCreditCardTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutCheckTotalTv = (TextView)findViewById(R.id.cashInOutCheckTotalTv);
        cashInOutCheckTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutCheckTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutPointTotalTv = (TextView)findViewById(R.id.cashInOutPointTotalTv);
        cashInOutPointTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutPointTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutGiftCardTotalTv = (TextView)findViewById(R.id.cashInOutGiftCardTotalTv);
        cashInOutGiftCardTotalTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutGiftCardTotalTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutEmployeeNameTv = (TextView)findViewById(R.id.cashInOutEmployeeNameTv);
        cashInOutEmployeeNameTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cashInOutEmployeeNameTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashInOutEmployeeNameTv.setText(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName);



        cashInOutAmountTv.setOnClickListener(evClick);

        cashInOutPopupCloseButton = (Button)findViewById(R.id.cashInOutPopupCloseButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashInOutPopupCloseButton.setText("");
            cashInOutPopupCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_close_common);
        } else {
            cashInOutPopupCloseButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashInOutPopupCloseButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        cashinoutAddButton = (Button) findViewById(R.id.cashinoutAddButton);

        cashinoutRemoveButton = (Button) findViewById(R.id.cashinoutRemoveButton);
        cashinoutRemoveButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                cashinoutRemoveButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashinout_inputtype = (Button) findViewById(R.id.cashinout_inputtype);

        cashinoutDetailViewButton = (Button) findViewById(R.id.cashinoutDetailViewButton);
        cashinoutDetailViewButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                cashinoutDetailViewButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );



        cashinoutGotosaleButton = (Button) findViewById(R.id.cashinoutGotosaleButton);
        cashinoutCashDrawerButton = (Button) findViewById(R.id.cashinoutCashDrawerButton);
        cashinoutCashoutButton = (Button) findViewById(R.id.cashinoutCashoutButton);

        cashInOutPopupCloseButton.setOnClickListener(loginBtnClickListener);
        cashinoutAddButton.setOnClickListener(loginBtnClickListener);
        cashinoutRemoveButton.setOnClickListener(loginBtnClickListener);
        cashinout_inputtype.setOnClickListener(loginBtnClickListener);
        cashinoutDetailViewButton.setOnClickListener(loginBtnClickListener);
        cashinoutGotosaleButton.setOnClickListener(loginBtnClickListener);
        cashinoutCashDrawerButton.setOnClickListener(loginBtnClickListener);
        cashinoutCashoutButton.setOnClickListener(loginBtnClickListener);


        cashInOutPoupuLogoutTv.setOnClickListener(loginBtnClickListener);
        cashInOutPoupuPreviousListTv.setOnClickListener(loginBtnClickListener);

        // Button 객체
        cashInOutTypeTb = (ToggleButton)findViewById(R.id.cashInOutTypeTb);
        cashInOutTypeTb.setOnCheckedChangeListener(inoutTypeToggleBtnChangeListener);

        // 메모리스트 보여지는 ListView 객체 생성
        cashInOutHistoryLv = (ListView)findViewById(R.id.cashInOutHistoryLv);
        cashInOutHistoryLv.setOnItemClickListener(reasonListViewClickListener);

        // 메모 히스토리 리스팅
        setCashInOutReasonHistoryListView(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);

        // Cash InOut Reason 항목 관련 -------------------------------------------------------------------------------------
        // Cash InOut Reason 항목 Spinner 객체 생성 및 항목연결
        // 메모 Selection 구성
        String strQuery = "select description from salon_store_cashinoutreason " +
                " order by description asc, idx desc ";
        Cursor reasonSelCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);

        String[] reasonSelectionArr = new String[22];
        reasonSelectionArr[0] = "Select reason";
        reasonSelectionArr[1] = "Starting cash";
        reasonSelectionArr[2] = "Ending cash";
        reasonSelectionArr[3] = "Cash Tip";
        int reasonSelCount = 4;
        while (reasonSelCursor.moveToNext()) {
            if (reasonSelCount < 23) {
                reasonSelectionArr[reasonSelCount] =  GlobalMemberValues.getDBTextAfterChecked(reasonSelCursor.getString(0), 1);
                reasonSelCount++;
            }
        }
        cashinoutReasonSpinner = (Spinner)findViewById(R.id.cashinoutReasonSpinner);
        //String[] strSearchItemList = {"Select Reason", "Good Customer", "Bad Customer", "New Comer", "Rich Customer", "VIP", "VVIP"};
        String[] strSearchItemList = reasonSelectionArr;
        mGeneralArrayListForSpinner = new ArrayList<String>();
        int arrCount = 0;
        for (int i = 0; i < strSearchItemList.length; i++) {
            if (!GlobalMemberValues.isStrEmpty(strSearchItemList[i])) {
                mGeneralArrayListForSpinner.add(strSearchItemList[i]);
            }
            arrCount++;
        }
        mGeneralArrayListForSpinner.add(">>> Type Reason <<<");
        mGeneralArrayListForSpinner.add(">>> Download cash in/out reason <<<");
        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cashinoutReasonSpinner.setAdapter(mSpinnerAdapter);
        cashinoutReasonSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);
        // ------------------------------------------------------------------------------------------------------
    }





    View.OnClickListener evClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tvnum = v.getTag().toString();

            Intent cashInOutKeyPadIntent = new Intent(context.getApplicationContext(), CashInOutPopupKeypad.class);
            cashInOutKeyPadIntent.putExtra("tvnum", tvnum);
            if (tvnum.equals("1000")) {
                String nowamt = cashInOutAmountTv.getText().toString();
                cashInOutKeyPadIntent.putExtra("nowamt", nowamt);
            }
            mActivity.startActivity(cashInOutKeyPadIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
            }
        }
    };

    ToggleButton.OnCheckedChangeListener inoutTypeToggleBtnChangeListener =
            new ToggleButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (cashInOutTypeTb.isChecked()) {
                        cashInOutTypeTb.setBackgroundResource(R.drawable.roundlayout_background_plus);
                    } else {
                        cashInOutTypeTb.setBackgroundResource(R.drawable.roundlayout_background_minus);
                    }
                }
            };

    /** 메모 리스트 배치하기 ********************************************************/
    public void setCashInOutReasonHistoryListView(String paramEmpIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramEmpIdx)) {
            DatabaseInit dbInit = new DatabaseInit(context);
            String strQuery = "select idx, inouttype, inoutmoney, inoutreason, wdate from salon_sales_cashintout_history " +
                    " where employeeIdx = '" + GlobalMemberValues.getDBTextAfterChecked(paramEmpIdx, 0) + "' " +
                    " and cashoutnum = 0 " +
                    " order by wdate desc ";
            Cursor cashInOutReasonCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            // ArrayList 객체 생성
            mGeneralArrayList = new ArrayList<TemporaryCashInOutReasonHistory>();
            while (cashInOutReasonCursor.moveToNext()) {
                String tempReason = cashInOutReasonCursor.getString(2);
                if (!GlobalMemberValues.isStrEmpty(tempReason)) {
                    String paramsTempCashInOutReasonHistoryListArray[] = {
                            GlobalMemberValues.getDBTextAfterChecked(cashInOutReasonCursor.getString(0), 1),
                            GlobalMemberValues.getDBTextAfterChecked(cashInOutReasonCursor.getString(1), 1),
                            GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.getDBTextAfterChecked(cashInOutReasonCursor.getString(2), 1)),
                            GlobalMemberValues.getDBTextAfterChecked(cashInOutReasonCursor.getString(3), 1),
                            GlobalMemberValues.getDBTextAfterChecked(cashInOutReasonCursor.getString(4), 1)
                    };
                    mTempCashInOutReasonHistoryList = new TemporaryCashInOutReasonHistory(paramsTempCashInOutReasonHistoryListArray);
                    mGeneralArrayList.add(mTempCashInOutReasonHistoryList);
                }
            }
            mCashInOutReasonHistoryListAdapter = new CashInOutReasonHistoryListAdapter(context, R.layout.cashinoutreason_history_list, mGeneralArrayList);
            cashInOutHistoryLv.setAdapter(mCashInOutReasonHistoryListAdapter);

            // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
            //giftCardHistoryListView.setOnItemClickListener(mCustomerInfoItemClickListener);
        }
    }
    /*******************************************************************************/

    AdapterView.OnItemClickListener reasonListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectedPosition == position) {
                mCashInOutReasonHistoryListAdapter.notifyDataSetChanged();
                selectedPosition = -1;

                view.setBackgroundColor(Color.parseColor("#fefff0"));

                selectedListViewItemView = null;
            } else {
                selectedPosition = position;

                if (selectedListViewItemView != null) {
                    selectedListViewItemView.setBackgroundColor(Color.parseColor("#fefff0"));
                }
                view.setBackgroundColor(Color.parseColor("#fbeafd"));

                selectedListViewItemView = view;
            }
            GlobalMemberValues.logWrite("CashInOutReasonSelectPosition", "선택값 : " + selectedPosition + "\n");
        }
    };

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3866e0"));

            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite("Cash In/Out Reason", "Selected Spinner Item : " + selectedItemSpinner + "\n");

            cashinoutReasonLn.setVisibility(View.GONE);

            if (!selectedItemSpinner.equals("Select Reason")) {
                if (selectedItemSpinner.equals(">>> Download cash in/out reason <<<")) {
                    downloadReasonSelection();
                } else if (selectedItemSpinner.equals(">>> Type Reason <<<")) {
                    viewTypeReason();
                } else {
                    mSelectedSpinnerString = selectedItemSpinner.toString();
                }
            } else {
                mSelectedSpinnerString = "";
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    private void viewTypeReason() {
        cashinoutReasonLn.setVisibility(View.VISIBLE);
        cashInOutReasonEv.setText("");
    }

    private void downloadReasonSelection() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
            CommandButton.backupDatabase(false);

            String[] tblNameArr = null;
            String tempTblMsg = "";

            tblNameArr = new String[]{
                    "salon_store_cashinoutreason"
            };
            tempTblMsg = "Cash In/Out Reason";

            final String currentSyncButtonStr = "member";

            if (tblNameArr != null) {
                final String[] finalTblNameArr = tblNameArr;
                final String finalTempTblMsg = tempTblMsg;
                new AlertDialog.Builder(context)
                        .setTitle("Cash In/Out Reason Download")
                        .setMessage("Download " + tempTblMsg + " Data?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 프로그래스 바를 실행~
                                        itemProDial = ProgressDialog.show(
                                                context, finalTempTblMsg + " DATA DOWNLOAD", finalTempTblMsg + " Data is Downloading...", true, false
                                        );
                                        Thread thread = new Thread(new Runnable() {
                                            public void run() {
                                                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                                // DB 생성 및 처리 관련 메소드
                                                // setDatabaseAndApiDataDownload(int paramStatus, int actionType)
                                                // paramStatus       --- 0 : 메소드 실행             1 : 실행안함
                                                // actionType        --- 0 : DB 테이블 먼저 삭제     1 : 삭제안함
                                                MainActivity.mDbInit.insertDownloadDataInDatabase(finalTblNameArr, currentSyncButtonStr, 1);
                                                // ---------------------------------------------------------------------------------
                                                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                                itemdownhandler.sendEmptyMessage(0);
                                                // ---------------------------------------------------------------------------------
                                            }
                                        });
                                        thread.start();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
        }
    }

    private Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagItemDown == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                Intent cashinoutReasonIntent = new Intent(context.getApplicationContext(), CashInOutPopup.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                // -------------------------------------------------------------------------------------
                mActivity.startActivity(cashinoutReasonIntent);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
                finish();
//                if (GlobalMemberValues.isUseFadeInOut()) {
//                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
//                }
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                itemProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };

    private void insertCashInOutReason() {
        if (cashinoutReasonLn.getVisibility() == View.VISIBLE) {
            mSelectedSpinnerString = cashInOutReasonEv.getText().toString();
        }

        if (GlobalMemberValues.isStrEmpty(mSelectedSpinnerString)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Select reason", "Close");
            return;
        } else {
            if (mSelectedSpinnerString.equals("Select reason")) {
                GlobalMemberValues.displayDialog(context, "Warning", "Select reason", "Close");
                return;
            }
        }

        String tempAmount = cashInOutAmountTv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempAmount)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter amount", "Close");
            return;
        }

        // 06212023 아래 사항 주석처리
//        if (GlobalMemberValues.getDoubleAtString(tempAmount) == 0) {
//            if (!mSelectedSpinnerString.equals("Starting cash")) {
//                GlobalMemberValues.displayDialog(context, "Warning", "Enter amount", "Close");
//                return;
//            }
//        }

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Select employee", "Close");
            return;
        }

        if (getCashInout() == 0 && !mSelectedSpinnerString.equals("Starting cash")) {
            GlobalMemberValues.displayDialog(context, "Warning", "There is no 'Starting cash'\nFirst you must register 'Starting cash'", "Close");
            return;
        }

        if (getCashInout() > 0 && mSelectedSpinnerString.equals("Starting cash")) {
            GlobalMemberValues.displayDialog(context, "Warning", "You have already registered 'Starting cash'", "Close");
            return;
        }

        if (getEndCashCount() > 0 && mSelectedSpinnerString.equals("Ending cash")) {
            GlobalMemberValues.displayDialog(context, "Warning", "You have already registered 'Ending cash'", "Close");
            return;
        }

        LogsSave.saveLogsInDB(45);

        String cashTypeStr1 = "";
        String cashTypeStr2 = "";
        String cashTypeStr3 = "";
        String cashTypeStr4 = "";
        String cashTypeStr5 = "";
        String cashTypeStr6 = "";
        String cashTypeStr7 = "";
        String cashTypeStr8 = "";
        String cashTypeStr9 = "";
        String cashTypeStr10 ="";
        String cashTypeStr11 ="";
        String cashTypeStr12 ="";

        String cashTypeStr13 ="";
        String cashTypeStr14 ="";
        String cashTypeStr15 ="";
        String cashTypeStr16 ="";

        if (cashinout_inputType_array != null){
            try {
                cashTypeStr1  = (String) cashinout_inputType_array.get(0);
                cashTypeStr2  = (String) cashinout_inputType_array.get(1);
                cashTypeStr3  = (String) cashinout_inputType_array.get(2);
                cashTypeStr4  = (String) cashinout_inputType_array.get(3);
                cashTypeStr5  = (String) cashinout_inputType_array.get(4);
                cashTypeStr6  = (String) cashinout_inputType_array.get(5);
                cashTypeStr7  = (String) cashinout_inputType_array.get(6);
                cashTypeStr8  = (String) cashinout_inputType_array.get(7);
                cashTypeStr9  = (String) cashinout_inputType_array.get(8);
                cashTypeStr10 = (String) cashinout_inputType_array.get(9);
                cashTypeStr11 = (String) cashinout_inputType_array.get(10);
                cashTypeStr12 = (String) cashinout_inputType_array.get(11);
                cashTypeStr13 = (String) cashinout_inputType_array.get(12);
                cashTypeStr14 = (String) cashinout_inputType_array.get(13);
                cashTypeStr15 = (String) cashinout_inputType_array.get(14);
                cashTypeStr16 = (String) cashinout_inputType_array.get(15);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // salon_sales_cashinout_history 테이블의 max idx 를 구한다.
        String sqlQuery = " select idx from salon_sales_cashintout_history " +
                " order by idx asc limit 1 ";
        String maxIdx = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        int newChIdx = GlobalMemberValues.getIntAtString(maxIdx) + 1;

        String tempInOutType = "+";
        if (cashInOutTypeTb.isChecked()) {
            tempInOutType = "+";
        } else {
            tempInOutType = "-";
            tempAmount = "-" + tempAmount;
        }

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String insStrQuery = "";

        insStrQuery = " insert into salon_sales_cashintout_history (" +
                " scode, sidx, stcode, employeeIdx, employeeName, inouttype, inoutmoney, inoutreason, cashoutnum " +
                " ) values ( " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(tempInOutType, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(tempAmount, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(mSelectedSpinnerString, 0) + "', " +
                " '0' " +
                " ) ";
        strInsertQueryVec.addElement(insStrQuery);

        insStrQuery = " insert into salon_sales_cashintout_history_cashlist (" +
                " chidx, " +
                " cashtype1, cashtype2, cashtype3, cashtype4, cashtype5, cashtype6, " +
                " cashtype7, cashtype8, cashtype9, cashtype10, cashtype11, cashtype12, " +
                " cashtype13, cashtype14, cashtype15, cashtype16 " +
                " ) values ( " +
                " '" + GlobalMemberValues.getDBTextAfterChecked((newChIdx + ""), 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr1, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr2, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr3, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr4, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr5, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr6, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr7, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr8, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr9, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr10, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr11, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr12, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr13, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr14, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr15, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(cashTypeStr16, 0) + "' " +

                " ) ";
        strInsertQueryVec.addElement(insStrQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("CashInOutReason", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else {
            switch (mSelectedSpinnerString) {
                // 등록항목이 starting cash 일 경우 프린트 실행
                case "Starting cash" : {
                    GlobalMemberValues.logWrite("startingcashprintlog", "여기실행..." + "\n");
                    try {
                        printStartingCash("Starting cash");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                // 등록항목이 starting cash 일 경우 프린트 실행
                case "Ending cash" : {
                    GlobalMemberValues.logWrite("startingcashprintlog", "여기실행..." + "\n");
                    try {
                        printStartingCash("Ending cash");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (GlobalMemberValues.isAutoSaleDataUpload()) {
                        GlobalMemberValues.logWrite("startingcashprintlog", "여기실행...2" + "\n");
                        GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
                    }

                    break;
                }
            }

            // 정상적인 등록 완료이므로 메모 history list
            // GlobalMemberValues.displayDialog(context, "Information", "Successfully Registered", "Close");
            setReasonClearEditText();
            setCashInOutReasonHistoryListView(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);

            setFields(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);

            // 키보드 사라지게
            keyboardHide();
        }
    }

    private void deleteCashInOutReason() {
        if (mGeneralArrayList.size() > 0 && selectedPosition > -1) {
            TemporaryCashInOutReasonHistory tempCashInOutReasonInstance = mGeneralArrayList.get(selectedPosition);
            if (selectedPosition > -1 && !GlobalMemberValues.isStrEmpty(tempCashInOutReasonInstance.reasonIdx)) {
                String tempSql = "";

                tempSql = " select inoutreason from salon_sales_cashintout_history " +
                        " where " +
                        " idx = '" + tempCashInOutReasonInstance.reasonIdx + "' ";
                String tempReasonDesc = MainActivity.mDbInit.dbExecuteReadReturnString(tempSql);

                if (tempReasonDesc.equals("Starting cash")) {
                    tempSql = " select count(idx) from salon_sales_cashintout_history " +
                            " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and cashoutNum = 0 " +
                            " and not(inoutreason like '%Starting cash%') ";
                    int noStartingCashCount = GlobalMemberValues.getIntAtString(
                            MainActivity.mDbInit.dbExecuteReadReturnString(tempSql)
                    );

                    if (getCashInout() > 0 && noStartingCashCount > 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You can not delete 'Starting cash'\nif you have registered another entry with 'Starting cash'.", "Close");
                        return;
                    }
                }

                tempSql = "delete from salon_sales_cashintout_history where idx = '" + tempCashInOutReasonInstance.reasonIdx + "' ";
                String returnDbResult = MainActivity.mDbInit.dbExecuteWriteReturnValue(tempSql);
                // DB 에서 삭제가 정상적으로 되었을 경우에만 (리턴값이 "0" 일경우)
                if (returnDbResult == "0" || returnDbResult.equals("0")) {

                    LogsSave.saveLogsInDB(46);
                    // 해당 Discount / Extra 아이템 삭제
                    mGeneralArrayList.remove(selectedPosition);
                    mCashInOutReasonHistoryListAdapter.notifyDataSetChanged();

                    setReasonClearEditText();
                    setFields(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);
                    // 키보드 사라지게
                    keyboardHide();
                }
            }
        }
    }

    private void viewDetailCash() {
        GlobalMemberValues.logWrite(TAG, "여기 나오나요?" + "\n");
        if (mGeneralArrayList.size() > 0 && selectedPosition > -1) {
            TemporaryCashInOutReasonHistory tempCashInOutReasonInstance = mGeneralArrayList.get(selectedPosition);
            if (selectedPosition > -1 && !GlobalMemberValues.isStrEmpty(tempCashInOutReasonInstance.reasonIdx)) {
                int cashCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select count(idx) from salon_sales_cashintout_history_cashlist " +
                                " where chidx = '" + tempCashInOutReasonInstance.reasonIdx + "' "));
                GlobalMemberValues.logWrite(TAG, "cashCount : " + cashCount + "\n");
                if (cashCount > 0) {
                    LogsSave.saveLogsInDB(47);
                    Intent viewDetailIntent = new Intent(context.getApplicationContext(), CashInOutPopupViewDetail.class);
                    viewDetailIntent.putExtra("chidx", tempCashInOutReasonInstance.reasonIdx);
                    mActivity.startActivity(viewDetailIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                } else {
                    GlobalMemberValues.displayDialog(context, "Warning", "There are no cash lists in the selected item", "Close");
                }
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Please select an item to view details", "Close");
        }
    }

    public static int getCashInout() {
        // Cash In 기록이 있는지 체크
        int returnValue = 0;
//        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
//            String tempSql = " select count(idx) from salon_sales_cashintout_history " +
//                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                    " and cashoutNum = 0 " +
//                    " and inoutreason like '%Starting cash%' ";
//
//            GlobalMemberValues.logWrite("jjjcashinoutsqllog", "sql : " + tempSql + "\n");
//
//            returnValue = GlobalMemberValues.getIntAtString(
//                    MainActivity.mDbInit.dbExecuteReadReturnString(tempSql)
//            );
//        } else {
//            returnValue = 0;
//        }

        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            String tempSql = " select count(idx) from salon_sales_cashintout_history " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0 " +
                    " and inoutreason like '%Starting cash%' ";

            GlobalMemberValues.logWrite("jjjcashinoutsqllog", "sql : " + tempSql + "\n");

            returnValue = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(tempSql)
            );
        } else {
//            String tempSql = "select cashoutnum from salon_sales_cashintout_history  where cashoutNum > 0  order by cashoutNum desc  limit 1";
            String tempSql = " select count(idx) from salon_sales_cashintout_history where cashoutNum = 0 " +
                    " and inoutreason like '%Starting cash%' ";

            GlobalMemberValues.logWrite("jjjcashinoutsqllog", "sql : " + tempSql + "\n");

            returnValue = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(tempSql)
            );
        }

        return returnValue;
    }

    public static int getEndCashCount() {
        // Cash In 기록이 있는지 체크
        int returnValue = 0;
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            String tempSql = " select count(idx) from salon_sales_cashintout_history " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0 " +
                    " and inoutreason = 'Ending cash' ";
            returnValue = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(tempSql)
            );
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private void checkCashInOut() {
        // Cash In 기록이 있는지 체크
        int cashInRecordCount = 0;
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            cashInRecordCount = getCashInout();
        } else {
            cashInRecordCount = 0;
        }
        if (cashInRecordCount == 0) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("Warning")
                    .setMessage("There is no 'cash in' record\nClick [Yes] to log out")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogsSave.saveLogsInDB(49);
                            empLogout("N");
                        }
                    }).show();
        } else {
            closeCashInOutPopup();
        }
    }

    public static void closeCashInOutPopup() {
        if (proDial != null) {
            proDial.dismiss();
        }

        // Cashout Json 데이터 클라우드 전송
        GlobalMemberValues.setSendCashOutJsonToCloud(context, mActivity);
        // End of day Json 데이터 클라우드 전송
        GlobalMemberValues.setSendEndOfDayJsonToCloud(context, mActivity);

        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public static void empLogout(String paramAnswer) {
        if (paramAnswer.equals("Y") || paramAnswer == "Y") {
            if (CashInOutPopup.mActivity == null) {

            } else {
                new AlertDialog.Builder(CashInOutPopup.mActivity)
                        .setTitle("Employee Logout")
                        .setMessage("Do you want to logout?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeCashInOutAndEmpLogout();
                            }
                        }).show();
            }
        } else {
            closeCashInOutAndEmpLogout();
        }
    }

    public static void closeCashInOutAndEmpLogout() {
        closeCashInOutPopup();
        MainActivity.employeeLogout();
    }

    private void setFields(String paramEmpIdx) {
        String sqlQuery = "";

        // Starting Cash -----------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and inoutreason like '%Starting cash%' " +
                " and cashoutnum = 0 ";
        String startCashAmount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        startCashAmount = GlobalMemberValues.getCommaStringForDouble(startCashAmount);
        cashInOutStartCashTotalTv.setText(startCashAmount);
        // -------------------------------------------------------------------------------------------------------------------

        // Cash In Total -----------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and inouttype = '+' " +
                " and cashoutnum = 0 " +
                " and not(inoutreason like '%Starting cash%') " +
                " and not(inoutreason = 'Ending cash')";
        String cashInTotalAmount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        cashInTotalAmount = GlobalMemberValues.getCommaStringForDouble(cashInTotalAmount);
        cashInOutCashInTotalTv.setText(cashInTotalAmount);
        // -------------------------------------------------------------------------------------------------------------------

        // Cash Out Total ----------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and inouttype = '-' " +
                " and cashoutnum = 0 ";
        String cashOutTotalAmount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        cashOutTotalAmount = GlobalMemberValues.getCommaStringForDouble(cashOutTotalAmount);
        cashInOutCashOutTotalTv.setText(cashOutTotalAmount);
        // -------------------------------------------------------------------------------------------------------------------

        // All Cash Total ----------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and not(inoutreason = 'Ending cash')";
        String allCashTotalAmount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        allCashTotalAmount = GlobalMemberValues.getCommaStringForDouble(allCashTotalAmount);
        cashInOutAllCashTotalTv.setText(allCashTotalAmount);
        // 진짜 All Cash Total 은 아래에서 한번더 계산됨..................
        // -------------------------------------------------------------------------------------------------------------------

        // End Cash ----------------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and inoutreason = 'Ending cash' ";
        String endCashTotalAmount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        endCashTotalAmount = GlobalMemberValues.getCommaStringForDouble(endCashTotalAmount);
        cashInOutEndCashTv.setText(endCashTotalAmount);
        // -------------------------------------------------------------------------------------------------------------------

        // Sales Cash total --------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) from salon_sales " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempCash = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
        cashInOutSalesCashTotalTv.setText(tempCash);
        // -------------------------------------------------------------------------------------------------------------------

        // Credit card total -------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and delyn = 'N' " +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCard = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
        cashInOutCreditCardTotalTv.setText(tempCard);
        // -------------------------------------------------------------------------------------------------------------------

        // Gift card total ---------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) from salon_sales " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempGiftCardAmount = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
        cashInOutGiftCardTotalTv.setText(tempGiftCardAmount);
        // -------------------------------------------------------------------------------------------------------------------

        // Point total -------------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) from salon_sales " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempGiftPoint = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
        cashInOutPointTotalTv.setText(tempGiftPoint);
        // -------------------------------------------------------------------------------------------------------------------

        // Check total -------------------------------------------------------------------------------------------------------
        sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) from salon_sales " +
                " where employeeIdx = '" + paramEmpIdx + "' " +
                " and cashoutnum = 0 " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempCheck = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
        cashInOutCheckTotalTv.setText(tempCheck);
        // -------------------------------------------------------------------------------------------------------------------

        // All cash total ----------------------------------------------------------------------------------------------------
        double tempTotalCash = GlobalMemberValues.getDoubleAtString(allCashTotalAmount) + GlobalMemberValues.getDoubleAtString(tempCash);
        cashInOutAllCashTotalTv.setText(GlobalMemberValues.getCommaStringForDouble(tempTotalCash + ""));
        // -------------------------------------------------------------------------------------------------------------------

        // Over/Short Cash ---------------------------------------------------------------------------------------------------
        String tempOverShortCashStr = "Over/Short";
        String tempOverShortCashTxtColor = "#ffffff";
        double allCashTotalAmountDbl = tempTotalCash;
        double endCashTotalAmountDbl = GlobalMemberValues.getDoubleAtString(endCashTotalAmount);
        double overShortCash = endCashTotalAmountDbl - allCashTotalAmountDbl;

        GlobalMemberValues.logWrite("overshorttotallog", "allCashTotalAmountDbl : " + allCashTotalAmountDbl + "\n");
        GlobalMemberValues.logWrite("overshorttotallog", "endCashTotalAmountDbl : " + endCashTotalAmountDbl + "\n");

        if (overShortCash < 0) {
            tempOverShortCashStr = "Short Cash";
            tempOverShortCashTxtColor = "#fe221d";
            cashInOutOverShortTv.setBackgroundResource(R.drawable.roundlayout_cashinout_tv_bg4);
        }
        if (overShortCash > 0) {
            tempOverShortCashStr = "Over Cash";
            tempOverShortCashTxtColor = "#1bd624";
            cashInOutOverShortTv.setBackgroundResource(R.drawable.roundlayout_cashinout_tv_bg5);
        }
        if (overShortCash == 0) {
            tempOverShortCashStr = "Over/Short";
            tempOverShortCashTxtColor = "#3e3d42";
            cashInOutOverShortTv.setBackgroundResource(R.drawable.roundlayout_cashinout_tv_bg3);
        }

        overshortTv.setTextColor(Color.parseColor(tempOverShortCashTxtColor));
        overshortTv.setText(tempOverShortCashStr);

        if (overShortCash < 0) {
            overShortCash = overShortCash * -1;
        }

        cashInOutOverShortTv.setText(GlobalMemberValues.getCommaStringForDouble(overShortCash + ""));
        // -------------------------------------------------------------------------------------------------------------------
    }

    public void printStartingCash(String str_isStart_isEnding) throws JSONException {
        // Starting Cash 입력할 때 프린트 하는지 여부 -------------------------------------------
        String tempStartingcashprintyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select startingcashprintyn from salon_storestationsettings_system");
        GlobalMemberValues.logWrite("startingcashprintlog", "tempStartingcashprintyn : " + tempStartingcashprintyn + "\n");
        if (GlobalMemberValues.isStrEmpty(tempStartingcashprintyn)) {
            tempStartingcashprintyn = "Y";
        }
        if (tempStartingcashprintyn == "N" || tempStartingcashprintyn.equals("N")) {
            return;
        }
        // -------------------------------------------------------------------------------------

        if (getCashInout() == 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "There is no 'Starting cash'\nFirst you must register 'Starting cash'", "Close");
            return;
        }

        // 최상단 json객체
        jsonroot = new JSONObject();
        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList_loginout = new JSONArray();
        JSONArray jsonList_cashin = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp_loginout = null;
        JSONObject jsontmp_cashin = null;

        String sqlQuery =  "";

        // 직원 로그인/아웃 로그기록 ------------------------------------------------------------------------------------------
        Cursor empLoginCursor;
        sqlQuery = "select loginouttype, loginoutdatetime " +
                " from salon_employee_loginout_history " +
                " where " +
                " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                " and cashoutNum = 0 " +
                " order by idx asc ";
        GlobalMemberValues.logWrite("employeeloginlog", "query : " + sqlQuery + "\n");
        empLoginCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
        while (empLoginCursor.moveToNext()) {
            String tempLoginouttype = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(0), 1);
            String tempLoginoutdatetime = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(1), 1);

            String tempLoginouttypeStr = "Login";
            if (GlobalMemberValues.getIntAtString(tempLoginouttype) == 0) {
                tempLoginouttypeStr = "Login";
            } else {
                tempLoginouttypeStr = "Logout";
            }

            jsontmp_loginout = new JSONObject();
            jsontmp_loginout.put("loginouttype", tempLoginouttypeStr);
            jsontmp_loginout.put("loginoutdatetime", tempLoginoutdatetime);

            jsonList_loginout.put(jsontmp_loginout);
        }
        // Employee 별 Sales 리스트를 Json 객체에 담는다.
        jsonroot.put("employeeloginoutlog", jsonList_loginout);
        // ------------------------------------------------------------------------------------------------------------------

        // 직원 Cash In 기록 -------------------------------------------------------------------------------------------------
        Cursor empCashInCursor;
        sqlQuery = "select inoutmoney, inoutreason " +
                " from salon_sales_cashintout_history " +
                " where " +
                " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                " and cashoutNum = 0 " +
                " order by idx asc ";
        GlobalMemberValues.logWrite("employeecashinlog", "query : " + sqlQuery + "\n");
        empCashInCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
        while (empCashInCursor.moveToNext()) {
            double tempCashinoutmoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(0), 1));
            String tempCashinoutreason = GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(1), 1);

            jsontmp_cashin = new JSONObject();
            jsontmp_cashin.put("cashinmoney", GlobalMemberValues.getCommaStringForDouble(tempCashinoutmoney + ""));
            jsontmp_cashin.put("cashinreason", tempCashinoutreason);

            jsonList_cashin.put(jsontmp_cashin);
        }
        // Employee 별 Sales 리스트를 Json 객체에 담는다.
        jsonroot.put("employeecashinlog", jsonList_cashin);
        // ------------------------------------------------------------------------------------------------------------------
// jihun bill count add
        JSONObject temp_cashin_object = new JSONObject();
        sqlQuery = "select cashtype1, cashtype2, cashtype3, cashtype4, cashtype5, cashtype6, cashtype7, cashtype8, cashtype9, cashtype10, cashtype11, cashtype12, cashtype13, cashtype14, cashtype15, cashtype16 " +
                "from salon_sales_cashintout_history_cashlist order by idx desc limit 1";
        Cursor bill_count_cursor;
        bill_count_cursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
        while (bill_count_cursor.moveToNext()) {
            String cashtype1 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(0), 1);     // 100.00
            String cashtype2 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(1), 1);     // 50.00
            String cashtype3 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(2), 1);     // 20.00
            String cashtype4 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(3), 1);     // 10.00
            String cashtype5 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(4), 1);     // 5.00
            String cashtype6 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(5), 1);     // 2.00
            String cashtype7 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(6), 1);     // 1.00
            String cashtype8 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(7), 1);     // 0.50
            String cashtype9 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(8), 1);     // 0.25
            String cashtype10 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(9), 1);    // 0.10
            String cashtype11 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(10), 1);   // 0.05
            String cashtype12 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(11), 1);   // 0.01

            String cashtype13 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(12), 1);   // 0.25
            String cashtype14 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(13), 1);   // 0.10
            String cashtype15 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(14), 1);   // 0.05
            String cashtype16 = GlobalMemberValues.getDBTextAfterChecked(bill_count_cursor.getString(15), 1);   // 0.01

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(cashtype1);
            jsonArray.put(cashtype2);
            jsonArray.put(cashtype3);
            jsonArray.put(cashtype4);
            jsonArray.put(cashtype5);
            jsonArray.put(cashtype6);
            jsonArray.put(cashtype7);
            jsonArray.put(cashtype8);
            jsonArray.put(cashtype9);
            jsonArray.put(cashtype10);
            jsonArray.put(cashtype11);
            jsonArray.put(cashtype12);

            if (GlobalMemberValues.isStrEmpty(cashtype13)) {
                cashtype13 = "";
            }
            if (GlobalMemberValues.isStrEmpty(cashtype14)) {
                cashtype14 = "";
            }
            if (GlobalMemberValues.isStrEmpty(cashtype15)) {
                cashtype15 = "";
            }
            if (GlobalMemberValues.isStrEmpty(cashtype16)) {
                cashtype16 = "";
            }

            jsonArray.put(cashtype13);
            jsonArray.put(cashtype14);
            jsonArray.put(cashtype15);
            jsonArray.put(cashtype16);

            temp_cashin_object.put("cash_types", jsonArray);

        }
        jsonroot.put("employeecashinlog_money_type", temp_cashin_object);

        // Cash In/Out Date -------------------------------------------------------------------------------------------------
        //sqlQuery = " select strftime('%m-%d-%Y', wdate, 'localtime') from salon_sales_cashintout_history " +
        sqlQuery = " select wdate from salon_sales_cashintout_history " +
                " where " +
                " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                " and cashoutNum = 0 " +
                " and not(inoutreason like '%Starting cash%') " +
                " order by idx asc limit 1 ";
        String empSaleDate = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        jsonroot.put("cashinoutdate", empSaleDate);  // JSON
        // ------------------------------------------------------------------------------------------------------------------

        // "Cash In" Total ---------------------------------------------------------------------------------------------------
        String cashinTotal = GlobalMemberValues.getCommaStringForDouble(cashInOutCashInTotalTv.getText().toString());
        jsonroot.put("cashintotal", cashinTotal);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Starting Cash 프린트인지 여부 -------------------------------------------------------------------------------------
        jsonroot.put("startingcashprintyn", "Y");  // JSON

        // 231004 ending 추가
        if (str_isStart_isEnding.equals("Starting cash")){
            jsonroot.put("startingcashprint_kind", str_isStart_isEnding);
        } else if (str_isStart_isEnding.equals("Ending cash")){
            jsonroot.put("startingcashprint_kind", str_isStart_isEnding);
        }
        // 231004 ending 추가

        // -------------------------------------------------------------------------------------------------------------------

        // Starting Cash 프린트 실행
        GlobalMemberValues.printReceiptByJHP(jsonroot, context, "startingcash");
    }

    public static JSONObject setCashOutAndEOD(String paramEndofdayYN, String paramShiftViewYN, String paramPrintYN) throws JSONException {
        // 최상단 json객체
        jsonroot = new JSONObject();
        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList_loginout = new JSONArray();
        JSONArray jsonList_cashin = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp_loginout = null;
        JSONObject jsontmp_cashin = null;

        String maxIdx_salon_sales_cashout_emp = "";

        String printingYN = paramPrintYN;

        String sqlQuery =  "";

        if (GlobalMemberValues.isStrEmpty(paramEndofdayYN)) {
            paramEndofdayYN = "N";
        }

        // endofday number 구하기 -----------------------------------------------------------------------------------
        int newEndofdayNum = GlobalMemberValues.getNewEndOfDay();
        // ----------------------------------------------------------------------------------------------------------

        // cashout number 구하기
//        sqlQuery = "select cashoutnum from salon_sales_cashintout_history " +
//                " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                " and cashoutNum > 0 " +
//                " order by cashoutNum desc " +
//                " limit 1 ";
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null){
            GlobalMemberValues.GLOBAL_EMPLOYEEINFO = new TemporaryEmployeeInfo();
        }

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            sqlQuery = "select cashoutnum from salon_sales_cashintout_history  where cashoutNum > 0  order by cashoutNum desc  limit 1";

        } else {
            sqlQuery = "select cashoutnum from salon_sales_cashintout_history " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum > 0 " +
                    " order by cashoutNum desc " +
                    " limit 1 ";
        }


        int newCashOutNum = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery)) + 1;

        // Cash Out 일 경우
        if (paramEndofdayYN == "N" || paramEndofdayYN.equals("N")) {
            GlobalMemberValues.logWrite("jjjcashinoutcntlog", "count : " + getCashInout() + "\n");
            if (getCashInout() == 0) {
                GlobalMemberValues.logWrite("jjjcashinoutcntlog", "데이터 없 음" + "\n");
                //GlobalMemberValues.displayDialog(context, "Warning", "There is no item to cash out", "Close");
            } else {
                GlobalMemberValues.logWrite("jjjcashinoutcntlog", "데이터 있 음" + "\n");

                if (getEndCashCount() == 0) {
                    // 상황에 따라 주석처리하거나 주석풀것...
                    //GlobalMemberValues.displayDialog(context, "Warning", "There is no ending cash", "Close");
                    //return;
                }


                // 직원 로그인/아웃 로그기록 ------------------------------------------------------------------------------------------
                Cursor empLoginCursor;
//                sqlQuery = "select loginouttype, loginoutdatetime " +
//                        " from salon_employee_loginout_history " +
//                        " where " +
//                        " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and cashoutNum = 0 " +
//                        " order by idx asc ";
//                GlobalMemberValues.logWrite("employeeloginlog", "query : " + sqlQuery + "\n");
//                empLoginCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);

                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select loginouttype, loginoutdatetime " +
                            " from salon_employee_loginout_history " +
                            " where " +
                            " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and cashoutNum = 0 " +
                            " order by idx asc ";
                    GlobalMemberValues.logWrite("employeeloginlog", "query : " + sqlQuery + "\n");
                    empLoginCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
                } else {

                    sqlQuery = "select loginouttype, loginoutdatetime " +
                            " from salon_employee_loginout_history " +
                            " where cashoutNum = 0 " +
                            " order by idx asc ";
                    GlobalMemberValues.logWrite("employeeloginlog", "query : " + sqlQuery + "\n");
                    empLoginCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);

                }

                while (empLoginCursor.moveToNext()) {
                    String tempLoginouttype = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(0), 1);
                    String tempLoginoutdatetime = GlobalMemberValues.getDBTextAfterChecked(empLoginCursor.getString(1), 1);

                    String tempLoginouttypeStr = "Login";
                    if (GlobalMemberValues.getIntAtString(tempLoginouttype) == 0) {
                        tempLoginouttypeStr = "Login";
                    } else {
                        tempLoginouttypeStr = "Logout";
                    }

                    jsontmp_loginout = new JSONObject();
                    jsontmp_loginout.put("loginouttype", tempLoginouttypeStr);
                    jsontmp_loginout.put("loginoutdatetime", tempLoginoutdatetime);

                    jsonList_loginout.put(jsontmp_loginout);
                }
                // Employee 별 Sales 리스트를 Json 객체에 담는다.
                jsonroot.put("employeeloginoutlog", jsonList_loginout);
                // ------------------------------------------------------------------------------------------------------------------

                // 직원 Cash In 기록 -------------------------------------------------------------------------------------------------
                Cursor empCashInCursor;
//                sqlQuery = "select inoutmoney, inoutreason " +
//                        " from salon_sales_cashintout_history " +
//                        " where " +
//                        " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and cashoutNum = 0 " +
//                        " order by idx asc ";
//                GlobalMemberValues.logWrite("employeecashinlog", "query : " + sqlQuery + "\n");
//                empCashInCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);

                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select inoutmoney, inoutreason " +
                            " from salon_sales_cashintout_history " +
                            " where " +
                            " employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and cashoutNum = 0 " +
                            " order by idx asc ";
                    GlobalMemberValues.logWrite("employeecashinlog", "query : " + sqlQuery + "\n");
                    empCashInCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
                } else {
                    sqlQuery = "select inoutmoney, inoutreason " +
                            " from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 " +
                            " order by idx asc ";
                    GlobalMemberValues.logWrite("employeecashinlog", "query : " + sqlQuery + "\n");
                    empCashInCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
                }


                while (empCashInCursor.moveToNext()) {
                    double tempCashinoutmoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(0), 1));
                    String tempCashinoutreason = GlobalMemberValues.getDBTextAfterChecked(empCashInCursor.getString(1), 1);

                    jsontmp_cashin = new JSONObject();
                    jsontmp_cashin.put("cashinmoney", GlobalMemberValues.getCommaStringForDouble(tempCashinoutmoney + ""));
                    jsontmp_cashin.put("cashinreason", tempCashinoutreason);

                    jsonList_cashin.put(jsontmp_cashin);
                }
                // Employee 별 Sales 리스트를 Json 객체에 담는다.
                jsonroot.put("employeecashinlog", jsonList_cashin);
                // ------------------------------------------------------------------------------------------------------------------

                /** salon_sales_cashout_emp 저장 시작 ****************************************************************************************************/

                // Sales Cash total --------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) from salon_sales " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and delyn = 'N' " +
//                        " and status = 0 ";
//                double temp_totalcash_dbl = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));

                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and delyn = 'N' " +
                            " and status = 0 ";
                } else {
                    sqlQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and delyn = 'N' " +
                            " and status = 0 ";
                }
                double temp_totalcash_dbl = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));

//                sqlQuery = " select sum(round(usedCash, 2)) from salon_sales_tip " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
//                        " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = " select sum(round(usedCash, 2)) from salon_sales_tip " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                            " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                } else {
                    sqlQuery = " select sum(round(usedCash, 2)) from salon_sales_tip " +
                            " where cashoutNum = 0 and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                            " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                }
                double temp_cashtip = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));

                temp_totalcash_dbl += temp_cashtip;
                // -------------------------------------------------------------------------------------------------------------------

                // Sales Card total --------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(useTotalCardAmountAfterReturned, 2)) from salon_sales " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and delyn = 'N' " +
//                        " and status = 0 ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(useTotalCardAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and delyn = 'N' " +
                            " and status = 0 ";
                } else {
                    sqlQuery = "select sum(round(useTotalCardAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and delyn = 'N' " +
                            " and status = 0 ";
                }
                double temp_totalcreditcard_dbl = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));

//                sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
//                        " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                            " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                } else {
                    sqlQuery = " select sum(round(usedCard, 2)) from salon_sales_tip " +
                            " where cashoutNum = 0 and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) " +
                            " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_detail where substr(salesCode, 1, 1) = 'V') ";
                }
                double temp_cardtip = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));

                temp_totalcreditcard_dbl += temp_cardtip;

                String temp_totalcreditcard = GlobalMemberValues.getCommaStringForDouble(temp_totalcreditcard_dbl + "");
                // -------------------------------------------------------------------------------------------------------------------

                // Sales Gift Card total --------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) from salon_sales " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and delyn = 'N' " +
//                        " and status = 0 ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and delyn = 'N' " +
                            " and status = 0 ";
                } else {
                    sqlQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and delyn = 'N' " +
                            " and status = 0 ";
                }
                String temp_totalgiftcard = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
                // -------------------------------------------------------------------------------------------------------------------

                // Sales Points total --------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) from salon_sales " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and delyn = 'N' " +
//                        " and status = 0 ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and delyn = 'N' " +
                            " and status = 0 ";
                } else {
                    sqlQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and delyn = 'N' " +
                            " and status = 0 ";
                }
                String temp_totalpoints = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
                // -------------------------------------------------------------------------------------------------------------------

                // Sales Others total --------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) from salon_sales " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and delyn = 'N' " +
//                        " and status = 0 ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and delyn = 'N' " +
                            " and status = 0 ";
                } else {
                    sqlQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) from salon_sales " +
                            " where cashoutNum = 0 and delyn = 'N' " +
                            " and status = 0 ";
                }
                String temp_totalothers = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery));
                // -------------------------------------------------------------------------------------------------------------------

                // Starting Cash -------------------------------------------------------------------------------------------------
//                sqlQuery = " select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and inoutreason like '%Starting cash%' ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = " select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and inoutreason like '%Starting cash%' ";
                } else {
                    sqlQuery = " select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and inoutreason like '%Starting cash%' ";
                }
                String temp_registerstartamount = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
                if (GlobalMemberValues.isStrEmpty(temp_registerstartamount)) {
                    temp_registerstartamount = "0";
                }
                // ------------------------------------------------------------------------------------------------------------------

                // 세일한 Total Cash 에 Starting Cash 더하기
                String temp_totalcash = GlobalMemberValues.getCommaStringForDouble((temp_totalcash_dbl + GlobalMemberValues.getDoubleAtString(temp_registerstartamount)) + "");

                // Total Amount on Hand -------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and not(inoutreason like '%Starting cash%' or inoutreason like '%Ending cash%')";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and not(inoutreason like '%Starting cash%' or inoutreason like '%Ending cash%')";
                } else {
                    sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and not(inoutreason like '%Starting cash%' or inoutreason like '%Ending cash%')";
                }
                String cashinouttotal = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);

                double tempTotalCash = GlobalMemberValues.getDoubleAtString(cashinouttotal)
                        + GlobalMemberValues.getDoubleAtString(temp_totalcash)
                        + GlobalMemberValues.getDoubleAtString(temp_totalcreditcard)
                        + GlobalMemberValues.getDoubleAtString(temp_totalgiftcard)
                        + GlobalMemberValues.getDoubleAtString(temp_totalpoints)
                        + GlobalMemberValues.getDoubleAtString(temp_totalothers);
                // ------------------------------------------------------------------------------------------------------------------

                // Ending Cash -------------------------------------------------------------------------------------------------
//                sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
//                        " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
//                        " and inoutreason like '%Ending cash%' ";
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                            " and inoutreason like '%Ending cash%' ";
                } else {
                    sqlQuery = "select sum(round(inoutmoney, 2)) from salon_sales_cashintout_history " +
                            " where cashoutNum = 0 and inoutreason like '%Ending cash%' ";
                }
                String temp_endingmoney = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);

                if (GlobalMemberValues.isStrEmpty(temp_endingmoney)) {
                    temp_endingmoney = "0";
                }
                // ------------------------------------------------------------------------------------------------------------------

                // Total Amount on Hand -------------------------------------------------------------------------------------------------
                double temp_netamountonhand = tempTotalCash - GlobalMemberValues.getDoubleAtString(temp_registerstartamount);
                // ------------------------------------------------------------------------------------------------------------------


                Vector<String> insVec = new Vector<String>();

                // 먼저 해당 직원에 대해서 cashoutNum 이 0 인 데이터가 있으면 삭제하고 새로 등록한다.
                sqlQuery = " delete from salon_sales_cashout_emp " +
                        " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                        " and cashoutNum = 0 " +
                        " and endofdayNum = '" + newEndofdayNum + "' ";
                insVec.addElement(sqlQuery);

                // salon_sales_cashout_emp 테이블에 먼저 저장한다.
                sqlQuery = " insert into salon_sales_cashout_emp ( " +
                        " scode, sidx, stcode, employeeIdx, employeeName, " +
                        " totalcash, totalcreditcard, totalgiftcard, totalpoints, totalothers, " +
                        " totalamountonhand, registerstartamount, endingmoney, netamountonhand, " +
                        " endofdayNum " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +

                        " '" + temp_totalcash + "', " +
                        " '" + temp_totalcreditcard + "', " +
                        " '" + temp_totalgiftcard + "', " +
                        " '" + temp_totalpoints + "', " +
                        " '" + temp_totalothers + "', " +

                        " '" + tempTotalCash + "', " +
                        " '" + temp_registerstartamount + "', " +
                        " '" + temp_endingmoney + "', " +
                        " '" + temp_netamountonhand + "', " +

                        " '" + newEndofdayNum + "' " +

                        " ) ";
                insVec.addElement(sqlQuery);

                for (String tempQuery : insVec) {
                    GlobalMemberValues.logWrite("salon_sales_cashout_emp_log", "query : " + tempQuery + "\n");
                }
                String returnResult = "";
                // 트랜잭션으로 DB 처리한다.
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(insVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Cashout is failed\n(Database Error)", "Close");
                } else {
                    maxIdx_salon_sales_cashout_emp = "";
                    String tempIdx = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select idx from salon_sales_cashout_emp " +
                                    " order by idx desc " +
                                    " limit 1 "
                    );
                    if (!GlobalMemberValues.isStrEmpty(tempIdx)) {
                        maxIdx_salon_sales_cashout_emp = tempIdx;
                    }
                }
                // -------------------------------------------------------------------------------------------------------------------

                /** salon_sales_cashout_emp 저장 끝 ****************************************************************************************************/
            }

        }

        // Json 데이터 가져오기
        jsonroot = MakingJsonForCashOutEndofDay.getMakingJson(paramEndofdayYN, newEndofdayNum, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx);

        if (GlobalMemberValues.isOnlineOrderUseYN()){
            // Online_Kiosk_관련 -----------------------------------------------------
            String mOnlineKioskData = getOnlineKioskDataFromApi(context, GlobalMemberValues.STR_NOW_DATE);
            String[] onlineDataArr = {"", "", "", "", ""};
            String[] kioskDataArr = {"", "", "", "", ""};

//            int while_count = 0;
//            while (mOnlineKioskData.isEmpty()){
//                if (while_count == 10)break;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                while_count++;
//            }

            // 10112023
            String thirdpartyDatas = "";


            if (!GlobalMemberValues.isStrEmpty(mOnlineKioskData)) {
//            String[] tempSplitData = mOnlineKioskData.split("-JJJ-");
                String[] tempSplitData = mOnlineKioskData.split("-J-");
                // Online 데이터
                if (!GlobalMemberValues.isStrEmpty(tempSplitData[0])) {
//                String[] tempOnlineData = tempSplitData[0].split("-J-");
//                onlineDataArr[0] = tempOnlineData[0];
//                onlineDataArr[1] = tempOnlineData[1];
//                onlineDataArr[2] = tempOnlineData[2];
//                onlineDataArr[3] = tempOnlineData[3];
//                onlineDataArr[4] = tempOnlineData[4];
                    onlineDataArr[0] = tempSplitData[0];
                    onlineDataArr[1] = tempSplitData[1];
                    onlineDataArr[2] = tempSplitData[2];
                    onlineDataArr[3] = tempSplitData[3];
                    onlineDataArr[4] = tempSplitData[4];

                }
                // Kiosk 데이터
                if (!GlobalMemberValues.isStrEmpty(tempSplitData[5])) {
//                String[] tempKioskData = tempSplitData[1].split("-J-");
//                kioskDataArr[0] = tempKioskData[0];
//                kioskDataArr[1] = tempKioskData[1];
//                kioskDataArr[2] = tempKioskData[2];
//                kioskDataArr[3] = tempKioskData[3];
//                kioskDataArr[4] = tempKioskData[4];
                    kioskDataArr[0] = tempSplitData[5];
                    kioskDataArr[1] = tempSplitData[6];
                    kioskDataArr[2] = tempSplitData[7];
                    kioskDataArr[3] = tempSplitData[8];
                    kioskDataArr[4] = tempSplitData[9];
                }



                // 10112023
                // third party 데이터
                if (tempSplitData.length > 10) {
                    if (!GlobalMemberValues.isStrEmpty(tempSplitData[10])) {
                        thirdpartyDatas = tempSplitData[10];
                    }
                }


                jsonroot.put("onlinekioskdatayn", "Y");

                // 받아온 부분을 salesbytendertypes_totaltransaction, salesbytendertypes_totalamount 에 추가 적용
                if (!GlobalMemberValues.isStrEmpty(tempSplitData[0]) && !GlobalMemberValues.isStrEmpty(tempSplitData[5])){
                    String temp_salesbytendertypes_totaltransaction = jsonroot.getString("salesbytendertypes_totaltransaction");
                    String temp_salesbytendertypes_totalamount = jsonroot.getString("salesbytendertypes_totalamount");
                    int addup_temp_salesbytendertypes_totaltransaction = Integer.parseInt(temp_salesbytendertypes_totaltransaction) + Integer.parseInt(onlineDataArr[0]) + Integer.parseInt(kioskDataArr[0]);
                    Double addup_temp_salesbytendertypes_totalamount = GlobalMemberValues.getDoubleAtString(temp_salesbytendertypes_totalamount) + GlobalMemberValues.getDoubleAtString(onlineDataArr[4]) + GlobalMemberValues.getDoubleAtString(kioskDataArr[4]);
                    String str_addup_temp_salesbytendertypes_totalamount = GlobalMemberValues.getStringFormatNumber(addup_temp_salesbytendertypes_totalamount,"2");

                    jsonroot.put("salesbytendertypes_totaltransaction", String.valueOf(addup_temp_salesbytendertypes_totaltransaction));
                    jsonroot.put("salesbytendertypes_totalamount", str_addup_temp_salesbytendertypes_totalamount);
                }
            } else {
                jsonroot.put("onlinekioskdatayn", "N");
            }

            // Online 데이터
            jsonroot.put("onlinedata_transactions", onlineDataArr[0]);
            jsonroot.put("onlinedata_salestotal", onlineDataArr[1]);
            jsonroot.put("onlinedata_refund", onlineDataArr[2]);
            jsonroot.put("onlinedata_tipamount", onlineDataArr[3]);
            jsonroot.put("onlinedata_totalamount", onlineDataArr[4]);

            // Kiosk 데이터
            jsonroot.put("kioskdata_transactions", kioskDataArr[0]);
            jsonroot.put("kioskdata_salestotal", kioskDataArr[1]);
            jsonroot.put("kioskdata_refund", kioskDataArr[2]);
            jsonroot.put("kioskdata_tipamount", kioskDataArr[3]);
            jsonroot.put("kioskdata_totalamount", kioskDataArr[4]);


            // 10112023
            // third party 데이터
            jsonroot.put("thirdparty_data", thirdpartyDatas);

            // ----------------------------------------------------------------------
        }

        // 마감(End of Day) 일 경우
        if (paramEndofdayYN == "Y" || paramEndofdayYN.equals("Y")) {
            // 가장 먼저 Cash In/Out 처리된 내역이 있는지 확인
            int cashoutHistoryCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select count(idx) from salon_sales_cashout_json " +
                            " where cashoutNum > 0 and endofdayNum = '" + newEndofdayNum + "' "
            ));

            if (cashoutHistoryCnt == 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "There is no history to end of day.\nPlease cash out first.", "Close");
            } else {
                String insUpdDelQuery = "";
                Vector<String> insUpdDelVec = new Vector<String>();

                insUpdDelQuery = " insert into salon_sales_endofday_json ( " +
                        " scode, sidx, stcode, jsonstr, endofdayDate, endofdayNum " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot.toString(), 0) + "', " +
                        //                        " strftime('%m-%d-%Y', datetime('now','localtime')), " +
                        " '" + EndOfDay.mEOD_Date + "', " +
                        " '" + newEndofdayNum + "' " +
                        " ) ";
                insUpdDelVec.addElement(insUpdDelQuery);

                for (String tempQuery : insUpdDelVec) {
                    GlobalMemberValues.logWrite("EndofDayDBQuery", "query : " + tempQuery + "\n");
                }
                String returnResult = "";
                // 트랜잭션으로 DB 처리한다.
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(insUpdDelVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Cashout is failed\n(Database Error)", "Close");
                } else {
                    printingYN = "Y";
                }
            }

        }

        // ShiftViewYN(미리보기) 가 N 이고, Cash Out 실행일 경우
        if ((paramShiftViewYN == "N" || paramShiftViewYN.equals("N")) && (paramEndofdayYN == "N" || paramEndofdayYN.equals("N"))) {
            // 상황에 따라 주석처리하거나 주석풀것...
            // DB Cash Out 처리 --------------------------------------------------------------------------------------------------

            String insUpdDelQuery = "";
            Vector<String> insUpdDelVec = new Vector<String>();

            insUpdDelQuery = " update salon_sales " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0" +
                    " and delyn = 'N'" +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            insUpdDelVec.addElement(insUpdDelQuery);

            insUpdDelQuery = " update salon_sales_detail " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0" +
                    " and delyn = 'N' " +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            insUpdDelVec.addElement(insUpdDelQuery);

            insUpdDelQuery = " update salon_sales_card " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0" +
                    " and delyn = 'N' " +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            insUpdDelVec.addElement(insUpdDelQuery);

            insUpdDelQuery = " update salon_sales_tip " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0 " +
                    " and salescode in (select salescode from salon_sales where stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
            insUpdDelVec.addElement(insUpdDelQuery);

            insUpdDelQuery = " update salon_sales_cashintout_history " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0 " +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            insUpdDelVec.addElement(insUpdDelQuery);

            insUpdDelQuery = " update salon_employee_loginout_history " +
                    " set " +
                    " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                    " where employeeIdx = '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "' " +
                    " and cashoutNum = 0 " +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            insUpdDelVec.addElement(insUpdDelQuery);

            if (!GlobalMemberValues.isStrEmpty(maxIdx_salon_sales_cashout_emp)) {
                insUpdDelQuery = " update salon_sales_cashout_emp " +
                        " set " +
                        " cashoutnum = '" + newCashOutNum + "', endofdayNum = '" + newEndofdayNum + "' " +
                        " where idx = '" + maxIdx_salon_sales_cashout_emp + "' " +
                        " and cashoutNum = 0 " +
                        " and stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
                insUpdDelVec.addElement(insUpdDelQuery);
            }

            insUpdDelQuery = " insert into salon_sales_cashout_json ( " +
                    " scode, sidx, stcode, employeeIdx, employeeName, cashoutNum, endofdayNum, jsonstr " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                    " '" + newCashOutNum + "', " +
                    " '" + newEndofdayNum + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot.toString(), 0) + "' " +
                    " ) ";
            insUpdDelVec.addElement(insUpdDelQuery);

            for (String tempQuery : insUpdDelVec) {
                GlobalMemberValues.logWrite("CashOutDBQuery", "query : " + tempQuery + "\n");
            }
            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(insUpdDelVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(context, "Warning", "Cashout is failed\n(Database Error)", "Close");
            } else {
                printingYN = "Y";

                if (GlobalMemberValues.isCashInOutPossible()) {
                    // Cash Out 일 때 배치를 함께하는지 체크 -----------------------------------------------
                    if (GlobalMemberValues.isAutoBatchInCashInOut(dbInit)) {
                        if (BatchSummary.getCountToBatch(dbInit) > 0) {
                            CommandButton.openBatchSummary("Y");
                            //BatchSummary.setBatchSales(context, mActivity, false);      // true 이면 batch 시 팝업창 안띄움..
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // -------------------------------------------------------------------------------------
                    // 영수증 이미지 폴더 삭제
                    GlobalMemberValues.deleteImageForPrinting();

                    // 직원 로그아웃
                    // 프린팅 여부 팝업에서 처리 여기서 안함.
//                 empLogout("N");
                }
            }
            // -------------------------------------------------------------------------------------------------------------------
        }

        if (GlobalMemberValues.isCashInOutPossible()) {
            // Cashout 결과 프린트
            if (printingYN == "Y" || printingYN.equals("Y")) {
                if (jsonroot != null) {
                    if (paramEndofdayYN.equals("Y")) {
                        if (paramShiftViewYN.equals("N")) {
                            // end of day 는 여기로 들어옴.
                            // end of day 에서 들어올시 프린팅 여기서 안함.
//                        GlobalMemberValues.printReceiptByJHP(jsonroot, context, "cashout");
                        }
                    } else {
                        if (paramShiftViewYN.equals("N")){
                            // cash in out popup 에서 여기로 들어옴
                            // cash in out 에서 들어올시 여기서 프린팅 안함.
                        } else {
                            GlobalMemberValues.printReceiptByJHP(jsonroot, context, "cashout");
                        }
                    }
                }
            }
        }

        return jsonroot;
    }

    View.OnClickListener loginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashInOutPopupCloseButton : {
                    checkCashInOut();
                    break;
                }
                case R.id.cashInOutPoupuLogoutTv : {
                    empLogout("Y");
                    break;
                }
                case R.id.cashinoutCashDrawerButton : {
                    LogsSave.saveLogsInDB(48);
                    CommandButton.openCashDrawer();
                    break;
                }
                case R.id.cashinoutRemoveButton : {
                    if (!CashInOutPopup.this.isFinishing()) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Cash In/Out Reason Delete")
                                    .setMessage("Do you want to delete this line?")
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteCashInOutReason();
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    })
                                    .show();
                        }
                    }
                    break;
                }
                case R.id.cashinoutDetailViewButton : {
                    viewDetailCash();
                    break;
                }
                case R.id.cashinoutAddButton : {
                    insertCashInOutReason();
                    break;
                }
                case R.id.cashinoutGotosaleButton : {
                    checkCashInOut();
                    break;
                }
                case R.id.cashinoutCashoutButton : {
                    if (!CashInOutPopup.this.isFinishing()) {
                        if (isCashOutEndCash()){

                            // table sale - temp_salecart 테이블이 비어있는지 확인.
                            String strQuery = "select count(*) from temp_salecart " +
                                    // 06172023
                                    " where wdate > dateadd(hh, -5, GETDATE()) ";
                            String temp_salecart_cnt = MssqlDatabase.getResultSetValueToString(strQuery);
                            if (!temp_salecart_cnt.equals("") && temp_salecart_cnt != null){
                                int i_temp_salecart_cnt = GlobalMemberValues.getIntAtString(temp_salecart_cnt);
                                if (i_temp_salecart_cnt > 0){
                                    // temp_salecart 주문이 있음.
                                    GlobalMemberValues.CARD_PROCESSING_STEP = 99;
                                    Intent removeCardIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_RemoveCard.class);
                                    mActivity.startActivity(removeCardIntent);
                                    return;
                                }
                            }

                            new AlertDialog.Builder(context)
                                    .setTitle("Cash Out")
                                    .setMessage("Do you want to cash out?")
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LogsSave.saveLogsInDB(50);
                                                    cashInOutExec();
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    })
                                    .show();
                        } else{
                            GlobalMemberValues.displayDialog(context, "Warning", "There is no 'Ending cash'\nFirst you must register 'Ending cash'", "Close");
                        }
                    }
                    break;
                }
                case R.id.cashInOutPoupuPreviousListTv : {
                    Intent intent = new Intent(context, CashInOutPopupPreviousList.class);
                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                    //intent.putExtra("openbatchedlist", "Y");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }

                case R.id.cashinout_inputtype : {
                    Intent intent = new Intent(context.getApplicationContext(), CashinOutPopup_TypeInput.class);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }
                    break;
                }
            }
        }
    };

    private void cashInOutExec() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                GlobalMemberValues.logWrite("direxetypeStrLog", "여기0" + "\n");
                // 화면재개 지연시간 초기화
                GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                try {
                    mJsonForPrint = setCashOutAndEOD("N", "N", "Y");
                    GlobalMemberValues.logWrite("tempJsonLog", "mJsonForPrint : " + mJsonForPrint.toString() + "\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // ---------------------------------------------------------------------------------

                GlobalMemberValues.logWrite("direxetypeStrLog", "여기1" + "\n");
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
//                handler_dbcompact.sendEmptyMessage(0);
            }
        });
        thread.start();
    }
//    private Handler handler_dbcompact = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            // 230106
//            // payment setfinish 에서 이동.
//            // 임시 주석처리
//            // 083123 salon_sales_signedimg db는 어떤 상황에서도 지워지지 않게 처리.
////            GlobalMemberValues.logWrite("SignatureImageDeleteLog", "서명이미지 파일지정한 기간의 파일 삭제시작\n");
////            GlobalMemberValues.deleteSignatureImageOnSales(context, mActivity);
//            //
//            if (GlobalMemberValues.isDataBase5mb()) {
//                if (GlobalMemberValues.isStrEmpty(direxetypeStr) || !direxetypeStr.equals("endingcash")) {
//                    CashInOutPopup.mProgressDialog = ProgressDialog.show(
//                            context, "DB Compact", "Please wait until database backup is completed", true, false);
//                    CashInOutPopup.mProgressDialog.show();
//                    new AlertDialog.Builder(mActivity)
//                            .setTitle("NZ ANDROID")
//                            .setMessage("Database capacity exceeded " + GlobalMemberValues.init_capacity_db + "mb. Would you initialize the sale data after database backup?")
//                            //.setIcon(R.drawable.ic_launcher)
//                            .setPositiveButton("Yes",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            Thread thread = new Thread(new Runnable() {
//                                                public void run() {
//                                                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
//                                                    GlobalMemberValues.startDatabaseCheckBackup();
//                                                    // ---------------------------------------------------------------------------------
//                                                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
////                                                CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);
//                                                    // ---------------------------------------------------------------------------------
//                                                    handler.sendEmptyMessage(0);
//                                                }
//                                            });
//                                            thread.start();
//                                        }
//                                    })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
////                                CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);
//                                    GlobalMemberValues.b_no_dbcompact = true;
//                                    handler.sendEmptyMessage(0);
//                                }
//                            })
//                            .setCancelable(false)
//                            .show();
//                } else {
//                    GlobalMemberValues.logWrite("direxetypeStrLog", "여기2" + "\n");
//                    handler.sendEmptyMessage(0);
//                }
//            } else {
//                GlobalMemberValues.logWrite("direxetypeStrLog", "여기3" + "\n");
////                CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);
//                handler.sendEmptyMessage(0);
//            }
//        }
//    };

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (GlobalMemberValues.isCashInOutPossible()) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("Printing")
                        .setMessage("Would you print the cash out report?")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                run_dbcompact();
//                            empLogout("N");
                            }
                        })
                        .setNeutralButton("Run End of Day",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!GlobalMemberValues.checkEmployeePermission(
                                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<11>")) {
                                    GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                                    return;
                                }

                                Intent adminPasswordIntent_back_office_end_of_day = new Intent(getApplicationContext(), AdminPassword.class);
                                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                adminPasswordIntent_back_office_end_of_day.putExtra("openClassMethod", "endofday");
                                // -------------------------------------------------------------------------------------
//                            startActivity(adminPasswordIntent_back_office_end_of_day);
                                startActivityForResult(adminPasswordIntent_back_office_end_of_day,7878);
                                if (GlobalMemberValues.isUseFadeInOut()) {
                                    overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                }
                            }
                        })
                        .setPositiveButton("Print Cash Out Report", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strQuery = "select jsonstr from salon_sales_cashout_json " +
                                        " where " +
                                        " cashoutnum > 0 " +
                                        " and delyn = 'N' " +
                                        " order by idx desc limit 1 ";
                                String tempJson = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
                                GlobalMemberValues.logWrite("tempJsonLog", "tempJson : " + tempJson + "\n");
                                if (!GlobalMemberValues.isStrEmpty(tempJson)) {
                                    try {
                                        mJsonForPrint = new JSONObject(tempJson);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                GlobalMemberValues.printReceiptByJHP(mJsonForPrint, context, "cashout");
                                run_dbcompact();
//                            empLogout("N");
                            }
                        }).show();
            } else {
                GlobalMemberValues.logWrite("direxetypeStrLog", "여기4" + "\n");
                run_dbcompact();
            }
        }
    };

    private void run_dbcompact() {
        if (GlobalMemberValues.isDataBase5mb()){
            CashInOutPopup.mProgressDialog = ProgressDialog.show(
                    context, "DB Compact", "Please wait until database backup is completed", true, false);
            CashInOutPopup.mProgressDialog.show();
            new AlertDialog.Builder(mActivity)
                    .setTitle("NZ ANDROID")
                    .setMessage("Database capacity exceeded " + GlobalMemberValues.init_capacity_db + "mb. Would you initialize the sale data after database backup?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Thread thread = new Thread(new Runnable() {
                                        public void run() {
                                            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                            GlobalMemberValues.startDatabaseCheckBackup();

                                            // 230616
                                            // db compact 를 진행후 다시 물어보는 버그가 있어서 yes 를 통과해도 db compact 창이 다시 나오지 않도록 true 처리함.
                                            GlobalMemberValues.b_no_dbcompact = true;
                                            // 230616

                                            // ---------------------------------------------------------------------------------
                                            // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                            CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);
                                            // ---------------------------------------------------------------------------------
                                        }
                                    });
                                    thread.start();
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GlobalMemberValues.b_no_dbcompact = true;
                            CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            GlobalMemberValues.logWrite("direxetypeStrLog", "여기5" + "\n");

            GlobalMemberValues.b_no_dbcompact = true;
            CashInOutPopup.dbcheckBackupHandler.sendEmptyMessage(0);

            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            if (CashInOutPopup.mProgressDialog != null){
                CashInOutPopup.mProgressDialog.dismiss();
            }
            // -------------------------------------------------------------------------------------

//            empLogout("N");
        }
    }


    private void openKeyinMemo() {

        String regValue = cashInOutReasonEv.getText().toString();

        Intent kitchenMemoIntent = new Intent(MainActivity.mContext, CashInOutPopupKeyin.class);
        kitchenMemoIntent.putExtra("regmemotxt", regValue);
        mActivity.startActivity(kitchenMemoIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    private void setReasonClearEditText() {
        if (cashinoutReasonSpinner != null) {
            cashinoutReasonSpinner.setSelection(0);
        }
        if (cashInOutAmountTv != null) {
            cashInOutAmountTv.setText("");
        }

        if (cashInOutTypeTb != null) {
            cashInOutTypeTb.setChecked(true);
        }
    }

    private void setInit() {
        jsonroot = null;

        cashInOutCashInTotalTv.setText("");
        cashInOutSalesCashTotalTv.setText("");
        cashInOutAllCashTotalTv.setText("");
        cashInOutCreditCardTotalTv.setText("");
        cashInOutCheckTotalTv.setText("");
        cashInOutPointTotalTv.setText("");
        cashInOutGiftCardTotalTv.setText("");

        if (MainActivity.proCustomDial != null) {
            MainActivity.proCustomDial.dismiss();
        }
    }

    private void keyboardHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
    }

    private boolean isCashOutEndCash(){
        boolean b_return = false;
        for (TemporaryCashInOutReasonHistory o : mGeneralArrayList){
            if (o.inoutreason.equals("Ending cash")){
                b_return = true;
            }
        }
        return b_return;
    }

    private static Handler dbcheckBackupHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            if (CashInOutPopup.mProgressDialog != null){
                CashInOutPopup.mProgressDialog.dismiss();
            }
            // -------------------------------------------------------------------------------------

            empLogout("N");
        }
    };

    public static String getOnlineKioskDataFromApi(Context context, String paramSearchDate) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = GlobalMemberValues.MASTER_PWD;
            } else {
                API_Online_Kiosk_Data_ForEOD apicheckInstance = new API_Online_Kiosk_Data_ForEOD(paramSearchDate);
                apicheckInstance.execute(null, null, null);

                int while_count = 0;
                while (apicheckInstance.mReturnValue.isEmpty()){
                    if (while_count == 10)break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while_count++;
                }
                returnValue = apicheckInstance.mReturnValue;

//                try {
//                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                    if (apicheckInstance.mFlag) {
//                        returnValue = apicheckInstance.mReturnValue;
//                    }
//                } catch (InterruptedException e) {
//                    returnValue = "";
//                }

                GlobalMemberValues.logWrite("API_Online_Kiosk_Data_ForEOD_Log", "returnValue : " + returnValue + "\n");
            }
        } else {
            //GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    // 뒤로가기 버튼 막기
    @Override public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (CashInOutPopup.isMemoOpen) {
            parentLn.setVisibility(View.GONE);
        }

        GlobalMemberValues.logWrite("lifecyclelog", "on pause 실행됨...");
    }

    @Override
    protected void onStop() {
        super.onStop();

        GlobalMemberValues.logWrite("lifecyclelog", "on stop 실행됨...");
    }

    @Override
    protected void onStart() {
        super.onStart();

        GlobalMemberValues.logWrite("lifecyclelog", "on start 실행됨...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        parentLn.setVisibility(View.VISIBLE);

        GlobalMemberValues.logWrite("lifecyclelog", "on resume 실행됨...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        GlobalMemberValues.logWrite("lifecyclelog", "on restart 실행됨...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7878){
            closeCashInOutAndEmpLogout();
        }
    }

    public static void setCashInOutAmountTv(String str, JSONArray jsonArray){
        cashInOutAmountTv.setText(str);
        cashinout_inputType_array = jsonArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalMemberValues.CARD_PROCESSING_STEP = 0;
    }
}
