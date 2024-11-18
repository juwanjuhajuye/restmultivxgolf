package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.EmployeeKeyIn;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class SaleHistory extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    static DatabaseInit dbInitForTipUpload;

    int REQ_CODE_SALEHISTORY = 7770;

    private Button closeBtn;

    private static LinearLayout saleHistorySaleListLinearLayout;

    private LinearLayout parentLn;

    ScrollView saleHistorySaleListScrollView;

    static TextView saleHistoryStartDateEditText, saleHistoryEndDateEditText;
    static EditText saleHistoryCustomerSearchEditText;
    Button saleHistoryCustomerSearchSHButton;
    static Button saleHistoryVoidButton, saleHistoryReturnButton, saleHistoryReprintButton, saleHistoryTipAdjustmentButton;
    static Button saleHistoryDeliveringButton;
    Button saleHistoryDoneButton;

    ImageButton saleHistoryMMSButton;
    // 05.31.2022
    static ImageButton repayButton;

    static LinearLayout salehistory_button_ln1, salehistory_button_ln2, salehistory_button_ln3;

    private TextView saleHistoryTopTitleTextView;

    private TextView saleHistoryListViewTitleTextView1, saleHistoryListViewTitleTextView2;
    private TextView saleHistoryListViewTitleTextView3, saleHistoryListViewTitleTextView4;
    private TextView saleHistoryListViewTitleTextView5, saleHistoryListViewTitleTextView6;
    private TextView saleHistoryListViewTitleTextView7;

    private TextView saleHistoryDateTitleTextView, saleHistoryCustomerTitleTextView;

    private RadioGroup deliverytakeawayGroup;
    private static RadioButton deliveryRadioButton;
    private static RadioButton takeawayRadioButton;
    private static RadioButton hereRadioButton;
    private static RadioButton allRadioButton;

    static GetDataAtSQLite dataAtSqlite;

    Intent mIntent;

    static String mSelectedSalesCode;
    static String mSelectedSalonSalesDetailIdx;
    static String mSelectedSalonSalesDetailStatus;
    static LinearLayout mSelectedSalonSalesDetailLn;

    TextView mSelectedDateTextView;

    static String mCustomerValue = "";

    static boolean mSearchForCustomer = false;

    // 카드결제 연동 관련 변수 -------------------------------
    public static ProgressDialog proDial;       // 프로그래스바
    String creditCardVoidReturnValue;           // 결제처리후 리턴값
    // ----------------------------------------------------

    // 터미널 연결정보 --------------------------------------
    public static String paymentGateway = "";
    public static String networkIp = "";
    public static String networkPort = "";
    // ----------------------------------------------------

    // Pax Void 처리를 위한 ArrayList
    public static ArrayList<String> mCardPaymentInfoArrayList;

    // 검색어
    public static String mSearchDeliveryTakeaway = "H";

    // 회원별 검색시 회원아이디
    public static String mSchMemberId = "";

    public static String mReceiptNum = "";

    public static String mGetGiftCardBalanceInfo = "";
    static ProgressDialog dialog;

    public static String mGetMemberMileage = "";

    public static String str_getStationCode = "";

    public static String mCancelReason = "";

    private PopupVoidReason popupVoidReason;

    private MmsPopup mmsPopup;

    // 04.20.2022 추가
    static String setCustomerOrderNum = "";
    static String setToPhoneNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        dbInitForTipUpload = dbInit;

        dataAtSqlite = new GetDataAtSQLite(context);

        parentLn = (LinearLayout)findViewById(R.id.saleHistoryLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mReceiptNum = mIntent.getStringExtra("receiptnum");
            GlobalMemberValues.logWrite("salehistorycustomerlog", "넘겨받은 mReceiptNum : " + mReceiptNum + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

            if (GlobalMemberValues.isStrEmpty(mReceiptNum)) {
                finish();
            }
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();
    }

    public void setContents() {
        mCancelReason = "";

        // Elo 관련
        if (MainActivity.mApiAdapter == null) {
            MainActivity.setEloInit();
        }

        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE  = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // 03102018 ------------------------------------------------------------------------------------------
        if (GlobalMemberValues.sh_fromCommand == "Y" || GlobalMemberValues.sh_fromCommand.equals("Y")) {
            GlobalMemberValues.sh_startdate = "";
            GlobalMemberValues.sh_enddate = "";
            GlobalMemberValues.sh_deliverytakeaway = "";
            GlobalMemberValues.sh_keyword = "";
        }
        GlobalMemberValues.sh_fromCommand = "N";
        // ---------------------------------------------------------------------------------------------------

        GlobalMemberValues.sh_keyword = mCustomerValue;

        ConfigPGInfo pgInfo = new ConfigPGInfo();
        paymentGateway = pgInfo.cfpaymentgateway;
        networkIp = pgInfo.cfnetworkip;
        networkPort = pgInfo.cfnetworkport;

        saleHistoryTopTitleTextView = (TextView)findViewById(R.id.saleHistoryTopTitleTextView);
        saleHistoryTopTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + saleHistoryTopTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView1 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView1);
        saleHistoryListViewTitleTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView2 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView2);
        saleHistoryListViewTitleTextView2.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView3 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView3);
        saleHistoryListViewTitleTextView3.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView4 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView4);
        saleHistoryListViewTitleTextView4.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView5 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView5);
        saleHistoryListViewTitleTextView5.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView6 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView6);
        saleHistoryListViewTitleTextView6.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryListViewTitleTextView7 = (TextView)findViewById(R.id.saleHistoryListViewTitleTextView7);
        saleHistoryListViewTitleTextView7.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryListViewTitleTextView7.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryDateTitleTextView = (TextView)findViewById(R.id.saleHistoryDateTitleTextView);
        saleHistoryDateTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + saleHistoryDateTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryCustomerTitleTextView = (TextView)findViewById(R.id.saleHistoryCustomerTitleTextView);
        saleHistoryCustomerTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + saleHistoryCustomerTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.saleHistoryCloseBtn);
        closeBtn.setOnClickListener(saleHistoryBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        /** 라디오 버튼 객체 생성 및 리스너 연결 *********************************************************************/
        salehistory_button_ln1 = (LinearLayout)findViewById(R.id.salehistory_button_ln1);
        salehistory_button_ln1.setVisibility(View.VISIBLE);
        salehistory_button_ln2 = (LinearLayout)findViewById(R.id.salehistory_button_ln2);
        salehistory_button_ln2.setVisibility(View.VISIBLE);
        salehistory_button_ln3 = (LinearLayout)findViewById(R.id.salehistory_button_ln3);
        salehistory_button_ln1.setVisibility(View.VISIBLE);


        deliverytakeawayGroup = (RadioGroup)findViewById(R.id.deliverytakeawayGroup);
        deliverytakeawayGroup.setOnCheckedChangeListener(checkDeliveryTakeaway);

        deliveryRadioButton = (RadioButton)findViewById(R.id.deliveryRadioButton);
        deliveryRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + deliveryRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            deliveryRadioButton.setVisibility(View.GONE);
        }

        takeawayRadioButton = (RadioButton)findViewById(R.id.takeawayRadioButton);
        takeawayRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + takeawayRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        hereRadioButton = (RadioButton)findViewById(R.id.hereRadioButton);
        hereRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + hereRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        allRadioButton = (RadioButton)findViewById(R.id.allRadioButton);
        allRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + allRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        /***********************************************************************************************************/

        // 스크롤뷰 객체 생성
        saleHistorySaleListScrollView = (ScrollView)findViewById(R.id.saleHistorySaleListScrollView);

        /** 객체 생성 및 리스너 연결 *******************************************************************************/
        saleHistoryStartDateEditText = (TextView)findViewById(R.id.saleHistoryStartDateEditText);
        saleHistoryEndDateEditText = (TextView)findViewById(R.id.saleHistoryEndDateEditText);

        saleHistoryCustomerSearchEditText = (EditText)findViewById(R.id.saleHistoryCustomerSearchEditText);
        saleHistoryCustomerSearchEditText.setOnTouchListener(saleHistoryCustomerSearchEditTextTouchListener);
        saleHistoryCustomerSearchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        saleHistoryCustomerSearchEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        saleHistoryCustomerSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        setSearchSalesHistory();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        saleHistoryStartDateEditText.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryEndDateEditText.setOnClickListener(saleHistoryBtnClickListener);

        saleHistoryCustomerSearchSHButton = (Button)findViewById(R.id.saleHistoryCustomerSearchSHButton);
        saleHistoryCustomerSearchSHButton.setOnClickListener(saleHistoryBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryCustomerSearchSHButton.setText("");
            saleHistoryCustomerSearchSHButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_search);
        } else {
            saleHistoryCustomerSearchSHButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryCustomerSearchSHButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryVoidButton = (Button)findViewById(R.id.saleHistoryVoidButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryVoidButton.setText("");
            saleHistoryVoidButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_void);
        } else {
            saleHistoryVoidButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryVoidButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnButton = (Button)findViewById(R.id.saleHistoryReturnButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnButton.setText("");
            saleHistoryReturnButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_return);
        } else {
            saleHistoryReturnButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryReturnButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReprintButton = (Button)findViewById(R.id.saleHistoryReprintButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReprintButton.setText("");
            saleHistoryReprintButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_reprint);
        } else {
            saleHistoryReprintButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryReprintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentButton = (Button)findViewById(R.id.saleHistoryTipAdjustmentButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentButton.setText("");
            saleHistoryTipAdjustmentButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_tipadjustment);
        } else {
            saleHistoryTipAdjustmentButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        if (GlobalMemberValues.getTipaddSalehistoryYN()){
            // visible
            saleHistoryTipAdjustmentButton.setVisibility(View.VISIBLE);
        } else {
            //invisible
            saleHistoryTipAdjustmentButton.setVisibility(View.INVISIBLE);
        }

        saleHistoryDeliveringButton = (Button)findViewById(R.id.saleHistoryDeliveringButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryDeliveringButton.setText("");
            saleHistoryDeliveringButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_delivering);
        } else {
            saleHistoryDeliveringButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryDeliveringButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryDoneButton = (Button)findViewById(R.id.saleHistoryDoneButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryDoneButton.setText("");
            saleHistoryDoneButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_done);
        } else {
            saleHistoryDoneButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryDoneButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryMMSButton = (ImageButton)findViewById(R.id.saleHistoryMMSButton);
        saleHistoryMMSButton.setOnClickListener(saleHistoryBtnClickListener);

        // 05.31.2022 -----------------------------------------------
        repayButton = (ImageButton)findViewById(R.id.repayButton);
        repayButton.setOnClickListener(saleHistoryBtnClickListener);
        // ----------------------------------------------------------

        // 06.03.2022 ---------------------------------------------------------------
        // 주문일자가 오늘 일자일 경우에만 버튼이 보이도록 한다.
        int nowDateY = GlobalMemberValues.getIntAtString(DateMethodClass.nowYearGet());
        int nowDateM = GlobalMemberValues.getIntAtString(DateMethodClass.nowMonthGet());
        int nowDateD = GlobalMemberValues.getIntAtString(DateMethodClass.nowDayGet());

        int tempSaledateY = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select year(saledate) from salon_sales where salescode = '" + mReceiptNum + "' "
        ));
        int tempSaledateM = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select month(saledate) from salon_sales where salescode = '" + mReceiptNum + "' "
        ));
        int tempSaledateD = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select day(saledate) from salon_sales where salescode = '" + mReceiptNum + "' "
        ));

        if (nowDateY == tempSaledateY && nowDateM == tempSaledateM && nowDateD == tempSaledateD) {
            repayButton.setVisibility(View.VISIBLE);
        } else {
            repayButton.setVisibility(View.GONE);
        }
        // -----------------------------------------------------------------------------


        saleHistoryVoidButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryReturnButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryReprintButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryTipAdjustmentButton.setOnClickListener(saleHistoryBtnClickListener);

        saleHistoryDeliveringButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryDoneButton.setOnClickListener(saleHistoryBtnClickListener);


        // ScrollView 에 속한 첫번째 LinearLayout 객체
        saleHistorySaleListLinearLayout = (LinearLayout)findViewById(R.id.saleHistorySaleListLinearLayout);

        /***********************************************************************************************************/

        if (mSearchForCustomer) {
//            String tempCustName = dbInit.dbExecuteReadReturnString(
//                    "select name from member1 where uid = '" + mCustomerValue + "' ");
            String tempCustName = MssqlDatabase.getResultSetValueToString(
                    "select name from member1 where uid = '" + mCustomerValue + "' ");
            saleHistoryTopTitleTextView.setText(saleHistoryTopTitleTextView.getText() +
                    " (Customer : " + tempCustName + ")");
        }

        // 03102018 ------------------------------------------------------------------------
        saleHistoryStartDateEditText.setText(GlobalMemberValues.sh_startdate);
        saleHistoryEndDateEditText.setText(GlobalMemberValues.sh_enddate);

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.sh_deliverytakeaway)) {
            GlobalMemberValues.sh_deliverytakeaway = "H";
        }

        if (!mSearchForCustomer) {
            switch (GlobalMemberValues.sh_deliverytakeaway) {
                case " D" : {
                    deliveryRadioButton.setChecked(true);
                    break;
                }
                case " T" : {
                    takeawayRadioButton.setChecked(true);
                    break;
                }
                case " H" : {
                    hereRadioButton.setChecked(true);
                    break;
                }
            }
        } else {
            allRadioButton.setChecked(true);
        }

        saleHistoryCustomerSearchEditText.setText(mCustomerValue);
        // --------------------------------------------------------------------------------

        setSearchSalesHistory();

        // 초기화
        setInit();
        saleHistoryCustomerSearchEditText.setText(mCustomerValue);
    }

    RadioGroup.OnCheckedChangeListener checkDeliveryTakeaway = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.deliverytakeawayGroup : {
                    switch (checkedId) {
                        case R.id.deliveryRadioButton : {
                            mSearchDeliveryTakeaway = "D";
                            break;
                        }
                        case R.id.takeawayRadioButton : {
                            mSearchDeliveryTakeaway = "T";
                            break;
                        }
                        case R.id.hereRadioButton : {
                            mSearchDeliveryTakeaway = "H";
                            break;
                        }
                        case R.id.allRadioButton : {
                            mSearchDeliveryTakeaway = "";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    View.OnClickListener mms_close_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mmsPopup.cancel();
        }
    };

    View.OnClickListener saleHistoryBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saleHistoryCloseBtn : {
                    LogsSave.saveLogsInDB(268,mSelectedSalesCode);

                    // 04222023
                    GlobalMemberValues.isRepay3 = false;

                    SaleHistoryList.mActivity.recreate();
                    finish();
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.saleHistoryCustomerSearchSHButton : {
                    setSearchSalesHistory();
                    break;
                }
                case R.id.saleHistoryVoidButton : {
                    LogsSave.saveLogsInDB(261,mSelectedSalesCode);
                    startVoidProcess();
                    break;
                }
                case R.id.saleHistoryReturnButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<2>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to return", "Close");
                        return;
                    }

//                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
//                        return;
//                    }
                    if (getSalecodeItemReturnAllYN(mSelectedSalesCode) || getSalecodeItemVoidYN(mSelectedSalesCode)){
                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
                        return;
                    }

                    LogsSave.saveLogsInDB(263,mSelectedSalesCode);

                    // All Discount 나 All Extra 가 되어 있으면 리턴을 못하도록 한다. ----------------------------------------------
                    /**
                     double tempAllDcExtra = 0;
                     String tempSalesCode = dbInit.dbExecuteReadReturnString(
                     "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
                     if (GlobalMemberValues.isStrEmpty(tempSalesCode)) {
                     tempAllDcExtra = 0;
                     } else {
                     tempAllDcExtra = GlobalMemberValues.getDoubleAtString(dbInit.dbExecuteReadReturnString(
                     "select allDiscountExtraPrice from salon_sales where salesCode = '" + tempSalesCode + "' "));
                     }

                     if (tempAllDcExtra > 0 || tempAllDcExtra < 0) {
                     GlobalMemberValues.displayDialog(context, "Warning", "This sale can not be returned due to 'All D/C or All Extra'", "Close");
                     return;
                     }
                     **/
                    // -----------------------------------------------------------------------------------------------------------

                    Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "salehistory_return");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.saleHistoryTipAdjustmentButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<3>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "TIP ADJUSTMENT", "Choose a item", "Close");
                        return;
                    }

                    // 배치된 주문인지 확인
                    int tempBatchSaleCount = 0;
                    tempBatchSaleCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            "select count(idx) from salon_sales where salesCode = '" + mSelectedSalesCode + "' " +
                                    " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
                    ));
                    if (tempBatchSaleCount > 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Batched Order can not be void", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
                        return;
                    }

                    LogsSave.saveLogsInDB(267,mSelectedSalesCode);

                    Intent tipAdjustmentIntent = new Intent(getApplicationContext(), SaleHistoryTipAdjustment.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    tipAdjustmentIntent.putExtra("salesCode", mSelectedSalesCode);
                    tipAdjustmentIntent.putExtra("salonSalesDetailIdx", mSelectedSalonSalesDetailIdx);
                    tipAdjustmentIntent.putExtra("is_history_list",false);
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(tipAdjustmentIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }

                    GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 1" + "\n");

                    break;
                }
                case R.id.saleHistoryStartDateEditText : {
                    String tempClockInOutSearchDay = saleHistoryStartDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = saleHistoryStartDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
                case R.id.saleHistoryEndDateEditText : {
                    String tempClockInOutSearchDay = saleHistoryEndDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = saleHistoryEndDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
                case R.id.saleHistoryReprintButton : {
                    //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                    GlobalMemberValues.logWrite("reprintlog", "selectedStatus : " + mSelectedSalonSalesDetailStatus + "\n");
                    GlobalMemberValues.logWrite("reprintlog", "mSelectedSalonSalesDetailIdx : " + mSelectedSalonSalesDetailIdx + "\n");
                    GlobalMemberValues.logWrite("reprintlog", "mSelectedSalesCode : " + mSelectedSalesCode + "\n");

                    LogsSave.saveLogsInDB(266,mSelectedSalesCode);

                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to reprint", "Close");
                        return;
                    }

//                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
//                        return;
//                    }

                    String status = "payment";
                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) == 1){
                            // void
                            status = "void";
                        } else if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) == 3){
                            // return
                            status = "return";
                        } else {
                            status = "payment";
                        }
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        if (!SaleHistory.this.isFinishing()) {
                            String finalStatus = status;
                            new AlertDialog.Builder(context)
                                    .setTitle("REPRINT")
                                    .setMessage("This order was void or returned?\nWould you like to print anyway?")
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    GlobalMemberValues.mReReceiptprintYN = "Y";
                                                    printReceipt(context, mSelectedSalesCode, finalStatus);
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    })
                                    .show();
                        }

                        //GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");

                    } else {
                        GlobalMemberValues.mReReceiptprintYN = "Y";
                        printReceipt(context, mSelectedSalesCode, "payment");
                    }

                    /**
                     new AlertDialog.Builder(context)
                     .setTitle("REPRINT")
                     .setMessage("Are you sure you want to reprint?\n[Sale Group# : " + mSelectedSalesCode + "]")
                     //.setIcon(R.drawable.ic_launcher)
                     .setPositiveButton("Yes",
                     new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                     GlobalMemberValues.mReReceiptprintYN = "Y";
                     printReceipt(context, mSelectedSalesCode, "payment");
                     }
                     })
                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                     //
                     }
                     })
                     .show();
                     **/


                    break;
                }

                case R.id.saleHistoryDeliveringButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please select an order to change the order status to delivering", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
                        return;
                    }

                    new AlertDialog.Builder(context)
                            .setTitle("Change status to delivering")
                            .setMessage("Would you like to change the status of the selected order to delivering?\n[Sale Group# : " + mSelectedSalesCode + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                                            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                            adminPasswordIntent.putExtra("openClassMethod", "salehistory_delivering");
                                            // -------------------------------------------------------------------------------------
                                            mActivity.startActivity(adminPasswordIntent);
                                            if (GlobalMemberValues.isUseFadeInOut()) {
                                                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                            }
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

                case R.id.saleHistoryDoneButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please select an order to change the order status to done", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
                        return;
                    }

                    new AlertDialog.Builder(context)
                            .setTitle("Change status to done")
                            .setMessage("Would you like to change the status of the selected order to done?\n[Sale Group# : " + mSelectedSalesCode + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                                            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                            adminPasswordIntent.putExtra("openClassMethod", "salehistory_done");
                                            // -------------------------------------------------------------------------------------
                                            mActivity.startActivity(adminPasswordIntent);
                                            if (GlobalMemberValues.isUseFadeInOut()) {
                                                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                            }
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
                case R.id.saleHistoryMMSButton : {

                    LogsSave.saveLogsInDB(270,mSelectedSalesCode);

                    // 04.20.2022 수정
                    mmsPopup = new MmsPopup(context, mms_close_listener, setCustomerOrderNum, setToPhoneNum);
                    mmsPopup.show();
                    GlobalMemberValues.logWrite("mmspopup", "mmspopup 실행 - 1" + "\n");
                    break;
                }

                // 05.31.2022
                case R.id.repayButton : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        //11152024 if station code doesn't match the payment station code, don't void


                        new AlertDialog.Builder(context)
                                .setTitle("REPAY")
                                .setMessage("Are you sure you want to repay this order?\n[Sale Group# : " + mSelectedSalesCode + "]")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                LogsSave.saveLogsInDB(269,mSelectedSalesCode);

                                                // void 처리후 repay 실행
                                                GlobalMemberValues.isRepay = true;

                                                // 04222023
                                                GlobalMemberValues.isRepay3 = true;

                                                GlobalMemberValues.isOpenPayment = true;
                                                startVoidProcess();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    }



                    break;
                }


            }
        }
    };

    public static void setChangeStatus(String paramStatus) {
        String strChangeStatus = "";

        if (GlobalMemberValues.isStrEmpty(paramStatus)) {
            paramStatus = "1";
        }

        switch (paramStatus) {
            case "1" : {
                strChangeStatus = "delivering";
                break;
            }
            case "2" : {
                strChangeStatus = "Done";
                break;
            }
        }

        String returnResult = "";
        String strUpdSqlQuery = "";
        Vector<String> strInsertQueryVec = new Vector<String>();
        String tempDeliveryTakeaway = MssqlDatabase.getResultSetValueToString("select deliverytakeaway from salon_sales where salesCode = '" + mSelectedSalesCode + "' ");
//        String tempDeliveryTakeaway = dbInit.dbExecuteReadReturnString("select deliverytakeaway from salon_sales where salesCode = '" + mSelectedSalesCode + "' ");
        if (GlobalMemberValues.isStrEmpty(tempDeliveryTakeaway)) {
            tempDeliveryTakeaway = "H";
        }

        if (tempDeliveryTakeaway.equals("D")) {
            strUpdSqlQuery = "update salon_sales set deliveryStatus = '" + paramStatus + "' where salesCode = '" + mSelectedSalesCode + "' ";
        } else {
            strUpdSqlQuery = "update salon_sales set takeawayStatus = '" + paramStatus + "' where salesCode = '" + mSelectedSalesCode + "' ";
        }

        // salon_sales_detail 수정쿼리를 백터 strInsertQueryVec 에 담는다.
        strInsertQueryVec.addElement(strUpdSqlQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("PaymentQueryStringVoid", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
//        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);

        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우

            switch (paramStatus) {
                case "1" : {
                    strChangeStatus = "delivering";
                    LogsSave.saveLogsInDB(264,mSelectedSalesCode);
                    break;
                }
                case "2" : {
                    strChangeStatus = "Done";
                    LogsSave.saveLogsInDB(265,mSelectedSalesCode);
                    break;
                }
            }


            // 주문이 here 또는 togo 일 경우
            if (!tempDeliveryTakeaway.equals("D")) {
                // 주문이 완료일때..
                if (paramStatus == "2" || paramStatus.equals("2")) {
                    // 주문대기 문자를 발송하는지 여부 체크 -----------------------------------------------------------
//                    String ordercompletesmsyn = dbInit.dbExecuteReadReturnString(
//                            " select ordercompletesmsyn from salon_storegeneral "
                    String ordercompletesmsyn = MssqlDatabase.getResultSetValueToString(
                            " select ordercompletesmsyn from salon_storegeneral "
                    );
                    if (GlobalMemberValues.isStrEmpty(ordercompletesmsyn)) {
                        ordercompletesmsyn = "N";
                    }
                    if (ordercompletesmsyn == "Y" || ordercompletesmsyn.equals("Y")) {
                        // 클라우드로 salescode 전송한다..
                        GlobalMemberValues.setSendSaleDoneSMSToCloud(context, mActivity, mSelectedSalesCode);
                    }
                    // ------------------------------------------------------------------------------------------------
                }
            }

            // 정상처리일 경우 Sale History 리스트 리로드
            setSearchSalesHistory();
        }
    }

    public static void printReceipt(Context paramContext, String paramSalesCode, String paramPrintType) {
        GlobalMemberValues.logWrite("reprintlog", "여기실행이오.. " + "\n");

        if (paramSalesCode == null) return;
        if (paramSalesCode.isEmpty()) return;

        // 팁 총액구하기
        String salonSalesTipQuery = "select sum(usedCash + usedCard) " +
                " from salon_sales_tip " +
                " where salesCode = '" + paramSalesCode + "' ";
//                " order by employeeName asc";
//        String tempTipAmount = dbInit.dbExecuteReadReturnString(salonSalesTipQuery);
        String tempTipAmount = MssqlDatabase.getResultSetValueToString(salonSalesTipQuery);
        double tempTipAmountDbl = GlobalMemberValues.getDoubleAtString(tempTipAmount);

//        DatabaseInit dbInit = new DatabaseInit(paramContext);   // DatabaseInit 객체 생성
//        String receiptJson = dbInit.dbExecuteReadReturnString(
//                "select receiptJson from salon_sales where salescode = '" + paramSalesCode + "' ");
        String receiptJson = MssqlDatabase.getResultSetValueToString(
                "select receiptJson from salon_sales where salescode = '" + paramSalesCode + "' ");
        GlobalMemberValues.logWrite("reprintlog", "receiptJson : " + receiptJson + "\n");
        JSONObject jsonroot = null;
        ResultSet receiptJson_return = MssqlDatabase.getResultSetValue(
                "select parentSalesidx from salon_sales_detail where salescode = '" + "C" + paramSalesCode.substring(1) + "' ");

//        String str_voidreason = dbInit.dbExecuteReadReturnString("select cancelreason from salon_sales where salescode = '" + paramSalesCode + "' ");
        String str_voidreason = dbInit.dbExecuteReadReturnString("select cancelreason from salon_sales where salescode = '" + paramSalesCode + "' order by idx desc limit 1 ");

        if (!GlobalMemberValues.isStrEmpty(receiptJson)) {
            try {
                jsonroot = new JSONObject(receiptJson);
                jsonroot.put("reprintyn", "Y");
                if (!GlobalMemberValues.isStrEmpty(str_voidreason)){
                    jsonroot.put("cancelreason", str_voidreason);
                }
                if (tempTipAmountDbl > 0) {
                    jsonroot.put("tipamount", GlobalMemberValues.getStringFormatNumber(tempTipAmountDbl, "2"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonroot != null) {
            GlobalMemberValues.logWrite("JsonRoot", jsonroot.toString());

            if (paramPrintType.equals("payment") || paramPrintType.equals("return")) {
                String str_sale_item_count = MssqlDatabase.getResultSetValueToString(
                        "select count(*) from salon_sales_detail where salescode = '" + "K" + paramSalesCode.substring(1) + "' ");
                String str_return_item_count = MssqlDatabase.getResultSetValueToString(
                        "select count(*) from salon_sales_detail where salescode = '" + "C" + paramSalesCode.substring(1) + "' and frommssql = 'Y' ");
                int i_itemcount = Integer.parseInt(str_sale_item_count) - Integer.parseInt(str_return_item_count);
                if (i_itemcount == 0){
                    // 모두 리턴됨.
                } else {
                    // 01052023
                    int billpaidlistCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select count(*) from bill_list_paid where salesCode = '" + paramSalesCode + "' "
                    ));

                    GlobalMemberValues.logWrite("billlistlogjjj", "count : " + billpaidlistCnt + "\n");

                    if (billpaidlistCnt < 2) {
                        Intent salehistoryReprintIntent = new Intent(context.getApplicationContext(), SaleHistory_Reprint.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        salehistoryReprintIntent.putExtra("JsonReceipt", jsonroot.toString());
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(salehistoryReprintIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    } else {
                        Intent salehistoryReprintIntent = new Intent(context.getApplicationContext(), BillPaidList.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        salehistoryReprintIntent.putExtra("salesCode", paramSalesCode);
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(salehistoryReprintIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }

                    }

                }
                if (Integer.parseInt(str_return_item_count) > 0){
                    // 리턴이 있음.
                    printReturnItems(paramSalesCode, receiptJson_return, jsonroot);

                }

            } else {
                String str_cashtendered = "";
                str_cashtendered = jsonroot.optString("cashtendered","0.0");
                double d_str_cashtendered = GlobalMemberValues.getDoubleAtString(str_cashtendered);
                if (!GlobalMemberValues.isStrEmpty(d_str_cashtendered + "") && d_str_cashtendered > 0) {
                    CommandButton.openCashDrawer();
                }

                // 영수증 프린트 하기 ------------------------------------------------------------------
                GlobalMemberValues.printReceiptByJHP(jsonroot, context, paramPrintType);
                // -----------------------------------------------------------------------------------

            }
        }
        //07052024 close resultset
        try {
            if(!receiptJson_return.isClosed()){
                receiptJson_return.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LabelprintReceipt(Context paramContext, String paramSalesCode) {
        // 05152023
        GlobalMemberValues.isLabelReprint = true;

        GlobalMemberValues.logWrite("reprintlog", "여기실행이오.. " + "\n");

        if (paramSalesCode == null) return;
        if (paramSalesCode.isEmpty()) return;

        DatabaseInit dbInit = new DatabaseInit(paramContext);   // DatabaseInit 객체 생성
        String receiptJson = dbInit.dbExecuteReadReturnString(
                "select receiptJson from salon_sales where salescode = '" + paramSalesCode + "' ");
//        String receiptJson = MssqlDatabase.getResultSetValueToString(
//                "select receiptJson from salon_sales where salescode = '" + paramSalesCode + "' ");
        GlobalMemberValues.logWrite("reprintlog", "receiptJson : " + receiptJson + "\n");
        JSONObject jsonroot = null;
        if (!GlobalMemberValues.isStrEmpty(receiptJson)) {
            try {
                jsonroot = new JSONObject(receiptJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonroot != null) {
            GlobalMemberValues.logWrite("JsonRoot", jsonroot.toString());
            if (GlobalMemberValues.isUseLabelPrinter()){
                // json 쪼개기..
                JSONArray temp_array = new JSONArray();
                temp_array = GlobalMemberValues.labelPrint_menuSplit(jsonroot);
                if (temp_array != null && temp_array.length() != 0){
//            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");

                    GlobalMemberValues.printLabel1to5(temp_array);
                }
            }
        }
    }

    public static void printReturnItems(String paramSalesCode, ResultSet receiptJson_return, JSONObject jsonroot){
        JSONArray jsonArray_parentIdx = new JSONArray();
        JSONArray jsonArray_returnItems = new JSONArray();
        double d_return_items_taxtotal = 0.00;

        // while 문 변경. 230522
        try {
            if (receiptJson_return.next()) {
                do {
                    jsonArray_parentIdx.put(receiptJson_return.getInt(1));
                } while (receiptJson_return.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        while (true) {
//            try {
//                if (!receiptJson_return.next()) break;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                jsonArray_parentIdx.put(receiptJson_return.getInt(1));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        for (int i_parentIdx = 0; i_parentIdx < jsonArray_parentIdx.length(); i_parentIdx++){
            String str_temp = "";
            JSONObject jsonObject_returnItem = new JSONObject();
            try {
                str_temp = String.valueOf(jsonArray_parentIdx.get(i_parentIdx));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResultSet salonSalesDetailCursor = MssqlDatabase.getResultSetValue(
                    "select itemName, qty, salesPrice, salesPriceAmount, eachDiscountExtraType, eachDiscountExtraPrice, " +
                            " eachDiscountExtraStr, tax, taxAmount, optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, memotokitchen " +
                            " from salon_sales_detail " +
                            " where idx = '" + str_temp + "' " +
                            " order by employeeName asc, idx asc ");
            try {
                if (salonSalesDetailCursor.next()) {
                    try {
                        jsonObject_returnItem.put("itemname", salonSalesDetailCursor.getString(1));
                        jsonObject_returnItem.put("itemqty", salonSalesDetailCursor.getString(2));
                        jsonObject_returnItem.put("itemprice", GlobalMemberValues.getStringFormatNumber(salonSalesDetailCursor.getString(3),"2"));
                        jsonObject_returnItem.put("itemamount", GlobalMemberValues.getStringFormatNumber(salonSalesDetailCursor.getString(4),"2"));
                        jsonObject_returnItem.put("itemdcextratype", salonSalesDetailCursor.getString(5));
                        jsonObject_returnItem.put("itemdcextraprice", salonSalesDetailCursor.getString(6));
                        jsonObject_returnItem.put("itemdcextrastr", salonSalesDetailCursor.getString(7));
                        if (GlobalMemberValues.getDoubleAtString(salonSalesDetailCursor.getString(8)) != 0.0){
                            if (GlobalMemberValues.getDoubleAtString(salonSalesDetailCursor.getString(9)) != 0.0){
                                jsonObject_returnItem.put("itemtaxexempt", "N");
                            } else {
                                jsonObject_returnItem.put("itemtaxexempt", "Y");
                            }
                        } else {
                            jsonObject_returnItem.put("itemtaxexempt", "N");
                        }
                        jsonObject_returnItem.put("optiontxt", salonSalesDetailCursor.getString(10));
                        jsonObject_returnItem.put("optionprice", GlobalMemberValues.getStringFormatNumber(salonSalesDetailCursor.getString(11),"2"));
                        jsonObject_returnItem.put("additionaltxt1", salonSalesDetailCursor.getString(12));
                        jsonObject_returnItem.put("additionalprice1", GlobalMemberValues.getStringFormatNumber(salonSalesDetailCursor.getString(13),"2"));
                        jsonObject_returnItem.put("additionaltxt2", salonSalesDetailCursor.getString(14));
                        jsonObject_returnItem.put("additionalprice2", GlobalMemberValues.getStringFormatNumber(salonSalesDetailCursor.getString(15),"2"));
                        jsonObject_returnItem.put("kitchenmemo", salonSalesDetailCursor.getString(16));
                        d_return_items_taxtotal = d_return_items_taxtotal + GlobalMemberValues.getDoubleAtString(salonSalesDetailCursor.getString(9));
                        jsonArray_returnItems.put(jsonObject_returnItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //07052024 close resultset
            try {
                if(!salonSalesDetailCursor.isClosed()){
                    salonSalesDetailCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // jsonroot 를 리턴에 맞게 변경.
        ResultSet receiptJson_return_returncode = MssqlDatabase.getResultSetValue(
                "select sum(useTotalCashAmount), sum(useTotalCardAmount), sum(useTotalGiftCardAmount), sum(useTotalCheckAmount), sum(useTotalPointAmount) " +
                        "returnCode from salon_sales_return where salescode = '" + "C" + paramSalesCode.substring(1) + "' ");
        try {
            if (receiptJson_return_returncode.next()) {
                try {
                    jsonroot.put("saleitemlist", jsonArray_returnItems);

                    jsonroot.put("cashtendered",receiptJson_return_returncode.getString(1));
                    jsonroot.put("creditcardtendered",receiptJson_return_returncode.getString(2));
                    jsonroot.put("giftcardtendered",receiptJson_return_returncode.getString(3));
                    jsonroot.put("checktendered",receiptJson_return_returncode.getString(4));
                    jsonroot.put("pointtendered",receiptJson_return_returncode.getString(5));
                    jsonroot.put("tax",GlobalMemberValues.getCommaStringForDouble(d_return_items_taxtotal + ""));
                    double d_total = receiptJson_return_returncode.getDouble(1)
                            + receiptJson_return_returncode.getDouble(2)
                            + receiptJson_return_returncode.getDouble(3)
                            + receiptJson_return_returncode.getDouble(4)
                            + receiptJson_return_returncode.getDouble(5);
                    jsonroot.put("subtotal", GlobalMemberValues.getCommaStringForDouble(d_total - d_return_items_taxtotal + "") );
                    jsonroot.put("totalamount", GlobalMemberValues.getCommaStringForDouble(d_total + "") );
                    jsonroot.put("deliverypickupfee", "0");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //07052024 close resultset
        try {
            if(!receiptJson_return_returncode.isClosed()){
                receiptJson_return_returncode.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        GlobalMemberValues.printReceiptByJHP(jsonroot, context, "return");


    }

    public static void setReturnOpen() {
        Intent saleHistoryReturnIntent = new Intent(context.getApplicationContext(), SaleHistoryReturnItemList.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        saleHistoryReturnIntent.putExtra("selectedSalesCode", mSelectedSalesCode);
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(saleHistoryReturnIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
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
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };


    public static void setSearchSalesHistory() {
        String searchStartDate = "";
        String searchEndDate = "";
        String searchCustomerText = mReceiptNum;

        if (!GlobalMemberValues.isStrEmpty(mReceiptNum)) {
            if (GlobalMemberValues.isStrEmpty(searchStartDate)) {
                searchStartDate = "2010-01-01";
            }
            if (GlobalMemberValues.isStrEmpty(searchEndDate)) {
                searchEndDate = "2050-12-31";
            }
        }

        String dateSearchQuery = "";
        dateSearchQuery = " saleDate between  '" + searchStartDate + "' " +
                // strftime('%m-%d-%Y', a.saleDate, 'localtime') 가 아닌
                // 그냥 a.saleDate 로 검색하게 되면 yyyy-mm-dd 로 해야하는데,
                // 그렇게 할 경우 searchEndDate 에 하루를 더해야 함...
                // "' and '" + DateMethodClass.getCustomEditDate(searchEndDate, 0, 0, 1) + "' ";
                " and '" + searchEndDate + "' ";

        if (!GlobalMemberValues.isStrEmpty(dateSearchQuery)) {
            dateSearchQuery += " and ";
        }

        String customerSearchQuery = "";
        customerSearchQuery = " ( " +
                " a.customerId like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.customerName like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.customerPhone like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.customerPosCode like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.customerPhone like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.salesCode like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " or a.customerordernumber like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " ) ";

        final String saleHistorySearchQuery = dateSearchQuery + customerSearchQuery;

        setSaleHistory(saleHistorySearchQuery, "", "");
    }

    public static void setSaleHistory(final String paramSearchQuery, final String paramGroupbyQuery, final String paramOrderbyQuery) {
        ResultSet salonSalesCursor, salonSalesDetailCursor, salonSalesTipCursor;

        // 세일정보 가져오기 (GetDataAtSQLite 클래스의 getSaleHistory 메소드를 통해 가져온다)
        salonSalesCursor = dataAtSqlite.getSaleHistory(paramSearchQuery, paramGroupbyQuery, paramOrderbyQuery);

        // 뷰 추가전 먼저 초기화(삭제)한다.
        saleHistorySaleListLinearLayout.removeAllViews();

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 10);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);
        // TextView 파라미터 설정
        LinearLayout.LayoutParams pageLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pageLayoutParams.setMargins(0, 0, 15, 0);

        Typeface typeface_monserrat_regular = null;
        Typeface typeface_monserrat_bold = null;
        Typeface typeface_monserrat_semi_bold = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface_monserrat_regular = mActivity.getResources().getFont(R.font.montserratregular);
            typeface_monserrat_bold = mActivity.getResources().getFont(R.font.montserratbold);
            typeface_monserrat_semi_bold = mActivity.getResources().getFont(R.font.montserratsemibold);
        } else {
            typeface_monserrat_regular = ResourcesCompat.getFont(context, R.font.montserratregular);
            typeface_monserrat_bold = ResourcesCompat.getFont(context, R.font.montserratbold);
            typeface_monserrat_semi_bold = ResourcesCompat.getFont(context, R.font.montserratsemibold);
        }

        float f_fontsize = 0.0f;
        if (GlobalMemberValues.mDevicePAX){
            f_fontsize = GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 6.0f;
        } else {
            f_fontsize = GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE;
        }


//        int datasu = salonSalesCursor.getCount();

        int dataCount = 0;
        try {
            while (salonSalesCursor.next()) {
                String dbSalesCode = GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,1);
                // 세일코드값이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(dbSalesCode, " ", ""))) {
                    // 세일 상태
                    int salonSalesStatus = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,18);
                    String salonSalesLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALESBGCOLOR[salonSalesStatus];

                    int saleListBg = R.drawable.roundlayout_salelist_bg0;
                    switch (salonSalesStatus) {
                        case 0 : {
                            saleListBg = R.drawable.roundlayout_salelist_bg0;
                            break;
                        }
                        case 1 : {
                            saleListBg = R.drawable.roundlayout_salelist_bg1;
                            break;
                        }
                    }

                    String salonSalesDeliveryTakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,32), 1);
                    String salonSalesDeliveryStatus = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,34), 1);
                    String salonSalesTakeawayStatus = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,35), 1);
                    String salonSalesKitchenPrintedYN = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,36), 1);

                    String str_voidreason = dbInit.dbExecuteReadReturnString("select cancelreason from salon_sales where salescode = '" + dbSalesCode + "' order by idx desc limit 1 ");

                    // void reason text
                    String str_cancelreason = str_voidreason;

                    if (GlobalMemberValues.isStrEmpty(salonSalesDeliveryTakeaway)) {
                        salonSalesDeliveryTakeaway = "H";
                    }

                    if (GlobalMemberValues.isStrEmpty(salonSalesDeliveryStatus)) {
                        salonSalesDeliveryStatus = "0";
                    }

                    if (GlobalMemberValues.isStrEmpty(salonSalesTakeawayStatus)) {
                        salonSalesTakeawayStatus = "0";
                    }

                    String statusStr = "";
                    String deliveryTakeawayStatusTxtColor = "#189dba";
                    if (salonSalesStatus == 1) {
                        statusStr = "Void";
                        deliveryTakeawayStatusTxtColor = "#ac5331";
                    } else {
                        if (salonSalesDeliveryTakeaway.equals("D") || salonSalesDeliveryTakeaway == "D") {
                            switch (salonSalesDeliveryStatus) {
                                case "0" : {
                                    statusStr = "Cooking";
                                    deliveryTakeawayStatusTxtColor = "#189dba";
                                    break;
                                }
                                case "1" : {
                                    statusStr = "Deliverying";
                                    deliveryTakeawayStatusTxtColor = "#11cc40";
                                    break;
                                }
                                case "2" : {
                                    statusStr = "Done";
                                    deliveryTakeawayStatusTxtColor = "#d62966";
                                    break;
                                }
                            }
                        } else {
                            switch (salonSalesTakeawayStatus) {
                                case "0" : {
                                    statusStr = "Cooking";
                                    deliveryTakeawayStatusTxtColor = "#189dba";
                                    break;
                                }
                                case "2" : {
                                    statusStr = "Done";
                                    deliveryTakeawayStatusTxtColor = "#d62966";
                                    break;
                                }
                            }
                        }
                    }

                    String kitchenPrintedStr = "";
                    String kitchenPrintedColorStr = "#a4a2a9";
                    if (salonSalesKitchenPrintedYN.equals("Y")) {
                        kitchenPrintedStr = "Printed in kitchen. <font color=\"#f20e02\">Touch Here</font> to reprint in kitchen again";
//              	        kitchenPrintedColorStr = "#a4a2a9";
                        kitchenPrintedColorStr = "#000000";
                    } else {
                        kitchenPrintedStr = "Not printed in kitchen. <font color=\"#f20e02\">Touch Here</font> to print in kitchen";
//	                      kitchenPrintedColorStr = "#0054d5";
                        kitchenPrintedColorStr = "#000000";
                    }

                    double salonSalesDeliveryPickupFee = GlobalMemberValues.getDoubleAtString(
                            GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,37), 1)
                    );
                    String tempDeliveryPickUpFee = "";
                    if (salonSalesDeliveryPickupFee > 0) {
                        tempDeliveryPickUpFee = " (fee +" + GlobalMemberValues.getCommaStringForDouble(salonSalesDeliveryPickupFee + "") + ")";
                    }

                    String tempCustomerOrderNumber = "";
                    tempCustomerOrderNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,38), 1);
                    if (GlobalMemberValues.isStrEmpty(tempCustomerOrderNumber)) {
                        tempCustomerOrderNumber = GlobalMemberValues.getCustomerOrderNumber(dbSalesCode);
                    }

                    String tempCustomerpagernumber = "";
                    tempCustomerpagernumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,39), 1);
                    if (GlobalMemberValues.isStrEmpty(tempCustomerpagernumber)) {
                        tempCustomerpagernumber = GlobalMemberValues.getCustomerPagerNumber(dbSalesCode);
                    }

                    String tempGetType = "";
                    String tempGetTypeColor = "#3a52d8";
                    boolean tempIsDeliveringButtonVisible = false;
                    switch (salonSalesDeliveryTakeaway) {
                        case "H" : {
                            tempGetType = "Here";
                            tempGetTypeColor = "#3a52d8";
                            tempIsDeliveringButtonVisible = false;
                            break;
                        }
                        case "T" : {
                            tempGetType = "To Go";
                            tempGetTypeColor = "#bd33a9";
                            tempIsDeliveringButtonVisible = false;
                            break;
                        }
                        case "D" : {
                            tempGetType = "Delivery";
                            tempGetTypeColor = "#bab616";
                            tempIsDeliveringButtonVisible = true;
                            break;
                        }
                    }

                    // LinearLayout 객체 생성
                    LinearLayout newLn = new LinearLayout(context);
                    newLn.setLayoutParams(newLnLayoutParams);
                    newLn.setOrientation(LinearLayout.VERTICAL);
                    //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                    newLn.setBackgroundResource(saleListBg);
                    newLn.setPadding(8, 5, 8, 5);

                    /** 상태표시 ********************************************************************************************************/
                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams statusLnLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    statusLnLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                    LinearLayout.LayoutParams statusTvLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    statusTvLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                    LinearLayout.LayoutParams statusTvLayoutParams2
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    statusTvLayoutParams2.setMargins(0, 0, 0, 0);

                    LinearLayout statusValueLn1 = new LinearLayout(context);
                    statusValueLn1.setLayoutParams(statusLnLayoutParams1);
                    statusValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                    statusValueLn1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    TextView statusTitleTv = new TextView(context);
                    statusTitleTv.setLayoutParams(statusTvLayoutParams2);
                    statusTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    statusTitleTv.setPadding(5, 5, 5, 5);
                    statusTitleTv.setText(tempGetType);
                    statusTitleTv.setTypeface(typeface_monserrat_bold);
                    statusTitleTv.setTextSize(f_fontsize + 2 + 6);
                    statusTitleTv.setTextColor(Color.parseColor(tempGetTypeColor));
                    statusTitleTv.setPaintFlags(statusTitleTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    statusTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                    statusValueLn1.addView(statusTitleTv);

                    TextView statusValueTv = new TextView(context);
                    statusValueTv.setLayoutParams(statusTvLayoutParams2);
                    statusValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    statusValueTv.setPadding(20, 5, 20, 5);
                    statusValueTv.setTypeface(typeface_monserrat_regular);
                    statusValueTv.setText(statusStr);
                    statusValueTv.setTextColor(Color.WHITE);
                    switch (statusStr){
                        case "Cooking" :
                            statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_cooking);
                            break;
                        case "Deliverying" :
                            statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_deliverying);
                            break;
                        case "Done" :
                            statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_done);
                            break;
                        case "Void" :
                            statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_void);

                            break;
                        default:
                            break;
                    }
                    statusValueTv.setTextSize(f_fontsize + 2 + 2);
//                statusValueTv.setTextColor(Color.parseColor(deliveryTakeawayStatusTxtColor));
                    statusValueTv.setPaintFlags(statusValueTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    LinearLayout.LayoutParams statusValueTv_layout_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    statusValueTv_layout_params.setMargins(5, 5, 5, 5);
                    statusValueTv.setLayoutParams(statusValueTv_layout_params);
                    statusValueLn1.addView(statusValueTv);

                    newLn.addView(statusValueLn1);
                    /********************************************************************************************************************/

                    /** Sale Group, Date 요약 *******************************************************************************************/
                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams summaryLnLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    summaryLnLayoutParams1.setMargins(0, 0, 0, 0);

                    LinearLayout summaryLn1 = new LinearLayout(context);
                    summaryLn1.setLayoutParams(summaryLnLayoutParams1);
                    summaryLn1.setOrientation(LinearLayout.HORIZONTAL);
                    //summaryLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    // Sale Group, Date 관련
                    LinearLayout.LayoutParams summaryNewTvLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    summaryNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                    String getSalesCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,1), 1);

                    float tempDateWeight = 7.0f;
                    /**
                     // 배치(Batch) 처리된 세일즈 건인지 확인한다.
                     int tempBatchSaleCount = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                     "select count(idx) from salon_sales where salesCode in (select salesCode from salon_sales_batch where delyn = 'N')"
                     ));
                     **/

                    int tempBatchSaleCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            "select count(idx) from salon_sales where salesCode = '" + getSalesCode + "' " +
                                    " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
                    ));

                    if (tempBatchSaleCount > 0) {
                        // Batch
                        TextView summaryTopNewTvBatch = new TextView(context);
                        summaryTopNewTvBatch.setLayoutParams(summaryNewTvLayoutParams1);
                        //summaryTopNewTvBatch.setWidth(GlobalMemberValues.getDisplayWidth(context) / 30);
                        summaryTopNewTvBatch.setGravity(Gravity.CENTER);
                        summaryTopNewTvBatch.setPadding(5, 5, 5, 5);
                        summaryTopNewTvBatch.setBackgroundResource(R.drawable.roundlayout_batched_bg);
                        summaryTopNewTvBatch.setTypeface(typeface_monserrat_semi_bold);
                        summaryTopNewTvBatch.setText("BATCHED");
                        summaryTopNewTvBatch.setTextSize(f_fontsize +  2 + 2);
                        summaryTopNewTvBatch.setTextSize(GlobalMemberValues.globalAddFontSize() + summaryTopNewTvBatch.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                        summaryTopNewTvBatch.setTextColor(Color.parseColor("#ffffff"));
                        summaryTopNewTvBatch.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        summaryLn1.addView(summaryTopNewTvBatch);

                        TextView summaryTopNewTvBatchMargin = new TextView(context);
                        summaryTopNewTvBatchMargin.setLayoutParams(summaryNewTvLayoutParams1);
                        summaryTopNewTvBatchMargin.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.1f));
                        summaryLn1.addView(summaryTopNewTvBatchMargin);

                        tempDateWeight = 5.0f;
                    }

                    // 해당 주문건이 Void 인 경우 Sale Group 앞에 "VOID" 표시
                    if (salonSalesStatus == 1) {
                        // Sale Group
                        TextView summaryTopNewTvVoid = new TextView(context);
                        summaryTopNewTvVoid.setLayoutParams(summaryNewTvLayoutParams1);
                        summaryTopNewTvVoid.setGravity(Gravity.CENTER);
                        summaryTopNewTvVoid.setPadding(5, 5, 5, 5);
                        summaryTopNewTvVoid.setBackgroundResource(R.drawable.roundlayout_voided_bg);
                        summaryTopNewTvVoid.setTypeface(typeface_monserrat_semi_bold);
                        summaryTopNewTvVoid.setText("VOID");
                        summaryTopNewTvVoid.setTextSize(f_fontsize +  2 + 2);
                        summaryTopNewTvVoid.setTextSize(GlobalMemberValues.globalAddFontSize() + summaryTopNewTvVoid.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                        summaryTopNewTvVoid.setTextColor(Color.parseColor("#ffffff"));
                        summaryTopNewTvVoid.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        summaryLn1.addView(summaryTopNewTvVoid);
                    }

                    String tempReceiptNoCustomerOrderNo = "";
                    if (!GlobalMemberValues.isStrEmpty(tempCustomerOrderNumber)) {
                        tempReceiptNoCustomerOrderNo += "Order No. : P" + tempCustomerOrderNumber + "";
                        tempReceiptNoCustomerOrderNo = GlobalMemberValues.getReplaceText(tempReceiptNoCustomerOrderNo, "PPHONE", "PHONE");

                        // 04.20.2022 추가
                        setCustomerOrderNum = "P" + tempCustomerOrderNumber;
                    }
                    if (!tempReceiptNoCustomerOrderNo.isEmpty()){
                        tempReceiptNoCustomerOrderNo += "\n";
                    }
                    tempReceiptNoCustomerOrderNo += "Receipt No. : " + getSalesCode;

                    // Sale Group
                    TextView summaryTopNewTv1 = new TextView(context);
                    summaryTopNewTv1.setLayoutParams(summaryNewTvLayoutParams1);
                    summaryTopNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    summaryTopNewTv1.setPadding(5, 5, 5, 5);
                    summaryTopNewTv1.setText(tempReceiptNoCustomerOrderNo);
                    summaryTopNewTv1.setTypeface(typeface_monserrat_bold);
                    summaryTopNewTv1.setPaintFlags(summaryTopNewTv1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    summaryTopNewTv1.setTextSize(f_fontsize + 5);
                    summaryTopNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    summaryTopNewTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 14.0f));
                    summaryLn1.addView(summaryTopNewTv1);

                    // Date
                    TextView summaryTopNewTv2 = new TextView(context);
                    summaryTopNewTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    summaryTopNewTv2.setPadding(5, 5, 5, 5);
                    summaryTopNewTv2.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,17), 1).substring(0,19));
                    summaryTopNewTv2.setTypeface(typeface_monserrat_regular);
                    summaryTopNewTv2.setTextSize(f_fontsize);
                    summaryTopNewTv2.setSingleLine();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        summaryTopNewTv2.setAutoSizeTextTypeUniformWithConfiguration(
                                1, (int)f_fontsize + 4, 1, TypedValue.COMPLEX_UNIT_DIP);
                    } else {
                        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(summaryTopNewTv2, 1, (int)f_fontsize + 4, 1,
                                TypedValue.COMPLEX_UNIT_DIP);
                    }

//                TextViewCompat.setAutoSizeTextTypeWithDefaults(summaryTopNewTv2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    summaryTopNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    summaryTopNewTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 6.0f));
                    summaryLn1.addView(summaryTopNewTv2);

                    newLn.addView(summaryLn1);
                    /*************************************************************************************************************/

                    LinearLayout summaryLn_stcode = new LinearLayout(context);
                    summaryLn_stcode.setLayoutParams(summaryLnLayoutParams1);
                    summaryLn_stcode.setOrientation(LinearLayout.HORIZONTAL);
                    TextView summaryTopNewTv_stcode = new TextView(context);
                    summaryTopNewTv_stcode.setLayoutParams(summaryNewTvLayoutParams1);
                    summaryTopNewTv_stcode.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    summaryTopNewTv_stcode.setPadding(5, 5, 5, 5);
                    summaryTopNewTv_stcode.setText("Station : " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,40), 1).substring(0,4));
                    summaryTopNewTv_stcode.setTypeface(typeface_monserrat_bold);
                    summaryTopNewTv_stcode.setPaintFlags(summaryTopNewTv1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    summaryTopNewTv_stcode.setTextSize(f_fontsize);
                    summaryTopNewTv_stcode.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    summaryTopNewTv_stcode.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 14.0f));
                    summaryLn_stcode.addView(summaryTopNewTv_stcode);
                    newLn.addView(summaryLn_stcode);

                    // 스테이션 코드 확인
                    str_getStationCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,40), 1).substring(0,4);
                    if (str_getStationCode.equals(GlobalMemberValues.getStationCode().substring(0,4))) {
                        saleHistoryReturnButton.setVisibility(View.VISIBLE);
                        saleHistoryVoidButton.setVisibility(View.VISIBLE);
                        salehistory_button_ln1.setVisibility(View.VISIBLE);
                        salehistory_button_ln2.setVisibility(View.VISIBLE);
                    } else {
                        saleHistoryReturnButton.setVisibility(View.INVISIBLE);
                        saleHistoryVoidButton.setVisibility(View.INVISIBLE);
                        salehistory_button_ln1.setVisibility(View.GONE);
                        salehistory_button_ln2.setVisibility(View.GONE);
                        //11152024 don't show repay button when the sales was made in a different station.
                        repayButton.setVisibility(View.GONE);
                    }

                    /** Payment 요약 *******************************************************************************************/
                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams summaryLnLayoutParams2
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    summaryLnLayoutParams2.setMargins(0, 0, 0, 0);

                    LinearLayout summaryLn2 = new LinearLayout(context);
                    summaryLn2.setLayoutParams(summaryLnLayoutParams2);
                    summaryLn2.setOrientation(LinearLayout.HORIZONTAL);
                    //summaryLn2.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    // Payment 관련
                    LinearLayout.LayoutParams summaryNewTvLayoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    summaryNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                    // Payment -----------------------------------------------------------------------------------------------------
                    TextView summaryBottomNewTv1 = new TextView(context);
                    summaryBottomNewTv1.setLayoutParams(summaryNewTvLayoutParams2);
                    summaryBottomNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    summaryBottomNewTv1.setPadding(5, 5, 5, 5);

                    String tempCashStr = "";
                    String tempCardStr = "";
                    String tempGiftCardStr = "";
                    String tempCheckStr = "";
                    String tempPointStr = "";
                    String tempChangeStr = "";

                    String tempSpaceStr = "        ";

                    String tempUseCash      = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,5), 1);
                    String tempUseCard      = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,6), 1);
                    String tempUseGiftCard  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,7), 1);
                    String tempUseCheck     = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,8), 1);
                    String tempUsePoint     = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,9), 1);
                    String tempChangeMoney  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,19), 1);

                    String tempTotalDiscountExtraPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,14), 1);
                    double tempDoubleTotalDcExtra = GlobalMemberValues.getDoubleAtString(tempTotalDiscountExtraPrice);

                    String tempDiscountPrice  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,20), 1);
                    double tempDoubleDiscountPrice = GlobalMemberValues.getDoubleAtString(tempDiscountPrice);
                    String tempExtraPrice  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,21), 1);
                    double tempDoubleExtraPrice = GlobalMemberValues.getDoubleAtString(tempExtraPrice);

                    String tempAllDiscountExtraPrice  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,16), 1);
                    double tempDoubleAllDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(tempAllDiscountExtraPrice);
                    String tempAllDiscountExtraStr  = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,22), 1);

                    double tempCashPlusChange
                            = GlobalMemberValues.getDoubleAtString(tempUseCash) + GlobalMemberValues.getDoubleAtString(tempChangeMoney);

                    if (tempCashPlusChange > 0) {
                        tempCashStr = "[Cash] " + tempCashPlusChange + tempSpaceStr;
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempUseCard) && GlobalMemberValues.getDoubleAtString(tempUseCard) > 0) {
                        tempCardStr = "[Card] " + GlobalMemberValues.getCommaStringForDouble(tempUseCard) + tempSpaceStr;
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempUseGiftCard) && GlobalMemberValues.getDoubleAtString(tempUseGiftCard) > 0) {
                        tempGiftCardStr = "[Gift Card] " + GlobalMemberValues.getCommaStringForDouble(tempUseGiftCard) + tempSpaceStr;
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempUseCheck) && GlobalMemberValues.getDoubleAtString(tempUseCheck) > 0) {
//                        String otherPaymentStr = dbInit.dbExecuteReadReturnString("" +
//                                "select checkcompany from salon_sales where salesCode = '" + dbSalesCode + "' ");
                        String otherPaymentStr = MssqlDatabase.getResultSetValueToString("" +
                                "select checkcompany from salon_sales where salesCode = '" + dbSalesCode + "' ");
                        if (GlobalMemberValues.isStrEmpty(otherPaymentStr)) {
                            otherPaymentStr = "Check or Other";
                        } else {
                            otherPaymentStr = "Other (" + otherPaymentStr + ")";
                        }
                        tempCheckStr = "[" + otherPaymentStr + "] " + GlobalMemberValues.getCommaStringForDouble(tempUseCheck) + tempSpaceStr;
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempUsePoint) && GlobalMemberValues.getDoubleAtString(tempUsePoint) > 0) {
                        tempPointStr = "[Point] " + GlobalMemberValues.getCommaStringForDouble(tempUsePoint) + tempSpaceStr;
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempChangeMoney) && GlobalMemberValues.getDoubleAtString(tempChangeMoney) > 0) {
                        tempChangeStr = "[Change] " + GlobalMemberValues.getCommaStringForDouble(tempChangeMoney) + tempSpaceStr;
                    }

                    // -------------------------------------------------------------------------------------------------------------

                    String strSummaryBottomNewTv1 = tempCashStr + tempCardStr + tempGiftCardStr + tempCheckStr + tempPointStr + tempChangeStr;

                    ArrayList<String> stringArrayList = GlobalMemberValues.getPaidListBySaleCode(getSalesCode);
//                    if (stringArrayList != null && !stringArrayList.isEmpty()){
//                        strSummaryBottomNewTv1 = "";
//                        int count = 1;
//                        for (String temp : stringArrayList){
//                            String[] temp1 = temp.split("-JJJ-");
//                            strSummaryBottomNewTv1 += "[" + temp1[3] + " - " + count + "]";
//                            strSummaryBottomNewTv1 += " " + temp1[1];
//                            strSummaryBottomNewTv1 += "     ";
//                            count++;
//                        }
//                    }
                    if (stringArrayList != null && !stringArrayList.isEmpty()){
                        String tempCardDigitNum = "";

                        // 07202023
                        String tempBillIdx = "";

                        strSummaryBottomNewTv1 = "";
                        int count = 1;
                        for (String temp : stringArrayList){
                            String[] temp1 = temp.split("-JJJ-");

                            tempCardDigitNum = "";
                            if (temp1.length > 4) {
                                tempCardDigitNum = temp1[4];
                            }

                            // 07202023
                            tempBillIdx = "";
                            if (temp1.length > 5) {
                                tempBillIdx = temp1[5];
                            }

                            //strSummaryBottomNewTv1 += "[" + temp1[3] + " - " + count + " (" + tempCardDigitNum + ")]";
                            // 07202023
                            if (temp1[3].equals("CARD")) {
                                strSummaryBottomNewTv1 += "" + temp1[3] + " (Digit #" + tempCardDigitNum;
                                if (!GlobalMemberValues.isStrEmpty(tempBillIdx)) {
                                    strSummaryBottomNewTv1 += ", Split #" + tempBillIdx;
                                }
                                strSummaryBottomNewTv1 += ")";
                            } else {
                                strSummaryBottomNewTv1 += "" + temp1[3];
                                if (!GlobalMemberValues.isStrEmpty(tempBillIdx)) {
                                    strSummaryBottomNewTv1 += " (Split #" + tempBillIdx + ")";
                                }
                            }

                            strSummaryBottomNewTv1 += " " + temp1[1];
                            strSummaryBottomNewTv1 += "     ";
                            count++;
                        }
                    }

                    summaryBottomNewTv1.setTextAppearance(context,R.style.myBoldText);
                    summaryBottomNewTv1.setTypeface(typeface_monserrat_bold);
                    summaryBottomNewTv1.setText(strSummaryBottomNewTv1);
                    summaryBottomNewTv1.setTextSize(f_fontsize);
                    summaryBottomNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    summaryLn2.addView(summaryBottomNewTv1);

                    newLn.addView(summaryLn2);
                    /*************************************************************************************************************/

                    if (tempDoubleDiscountPrice < 0 || tempDoubleExtraPrice > 0
                            || tempDoubleAllDiscountExtraPrice > 0 || tempDoubleAllDiscountExtraPrice < 0) {
                        /** Discount / Extra 관련 요약 *******************************************************************************************/

                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams summaryLnLayoutParams3
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        summaryLnLayoutParams3.setMargins(0, 0, 0, 0);

                        LinearLayout summaryLn3 = new LinearLayout(context);
                        summaryLn3.setLayoutParams(summaryLnLayoutParams3);
                        summaryLn3.setOrientation(LinearLayout.HORIZONTAL);
                        //summaryLn3.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                        // Discount / Extra 관련 LinearLayout
                        LinearLayout.LayoutParams summaryNewTvLayoutParams3
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        summaryNewTvLayoutParams3.setMargins(0, 0, 0, 0);

                        // Discount / Extra 관련 TextView
                        TextView summaryDcExNewTv1 = new TextView(context);
                        summaryDcExNewTv1.setLayoutParams(summaryNewTvLayoutParams3);
                        summaryDcExNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        summaryDcExNewTv1.setPadding(5, 5, 5, 5);

                        String tempTotalDiscountExtraStr = "";
                        String tempDiscountStr = "";
                        String tempExtraStr = "";
                        String tempAllDcExtraStr = "";

                        if (tempDoubleDiscountPrice < 0) {
                            tempDiscountStr = "[Each Discount] " + GlobalMemberValues.getCommaStringForDouble(tempDoubleDiscountPrice + "") + tempSpaceStr;
                        }

                        if (tempDoubleExtraPrice > 0) {
                            tempExtraStr = "[Each Extra] " + GlobalMemberValues.getCommaStringForDouble(tempDoubleExtraPrice + "") + tempSpaceStr;
                        }

                        if ((tempDoubleAllDiscountExtraPrice > 0 || tempDoubleAllDiscountExtraPrice < 0)) {
                            if (tempDoubleAllDiscountExtraPrice > 0) {
                                //tempAllDcExtraStr = "[" + tempAllDiscountExtraStr + " : " + tempDoubleAllDiscountExtraPrice + "]" + tempSpaceStr;
                                tempAllDcExtraStr = "[All Extra] " + GlobalMemberValues.getCommaStringForDouble(tempDoubleAllDiscountExtraPrice + "") + tempSpaceStr;
                            }
                            if (tempDoubleAllDiscountExtraPrice < 0) {
                                //tempAllDcExtraStr = "[" + tempAllDiscountExtraStr + " : " + tempDoubleAllDiscountExtraPrice + "]" + tempSpaceStr;
                                tempAllDcExtraStr = "[All Discount] " + GlobalMemberValues.getCommaStringForDouble(tempDoubleAllDiscountExtraPrice + "") + tempSpaceStr;
                            }
                        }

                        String strTotalDcExPriceColor = "#125ccd";
                        if (!GlobalMemberValues.isStrEmpty(tempTotalDiscountExtraPrice)) {
                            String tempStr = "";
                            if (tempDoubleTotalDcExtra >= 0) {
                                tempStr = "[Total Extra] ";
                                strTotalDcExPriceColor = "#125ccd";
                            } else {
                                tempStr = "[Total Discount] ";
                                strTotalDcExPriceColor = "#e20c47";
                            }
                            tempTotalDiscountExtraStr = tempStr + GlobalMemberValues.getCommaStringForDouble(tempDoubleTotalDcExtra + "") + tempSpaceStr;
                        }

                        // -------------------------------------------------------------------------------------------------------------

                        String strSummaryDcExNewTv1 = tempDiscountStr + tempExtraStr + tempAllDcExtraStr + tempTotalDiscountExtraStr;

                        GlobalMemberValues.setTextViewColorPartial(
                                summaryDcExNewTv1, strSummaryDcExNewTv1, tempTotalDiscountExtraStr, Color.parseColor(strTotalDcExPriceColor)
                        );
                        summaryDcExNewTv1.setTextSize(f_fontsize);
                        summaryDcExNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        summaryLn3.addView(summaryDcExNewTv1);

                        newLn.addView(summaryLn3);
                        /*************************************************************************************************************/
                    }

                    int saleItemsCount = 0;
                    String saleItemsTxt = "";
                    String salonSalesDetailQuery = "";
                    // salon_sales_detail 데이터 출력 ---------------------------------------------------------------------------------------------------
                    salonSalesDetailQuery = "select categoryName, itemIdx, itemName, qty, salesBalPriceAmount, taxAmount, totalAmount, pointAmount, " +
                            " employeeIdx, employeeName, saveType, isQuickSale, status, idx, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, memotokitchen, " +
                            // 05082023
                            " wmodyn, wmodresult, wmodresultmsg, " +
                            // 05102023
                            " wmurl " +
                            " from salon_sales_detail " +
                            " where delyn = 'N' " +
                            " and salesCode = '" + dbSalesCode + "' " +
                            " order by employeeName asc, idx asc ";
//                    salonSalesDetailCursor = dbInit.dbExecuteRead(salonSalesDetailQuery);   // todo
                    salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
                    try {
                        while (salonSalesDetailCursor.next()) {
                            int salonSalesDetailStatus = GlobalMemberValues.resultDB_checkNull_int(salonSalesDetailCursor,12);
                            String salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[0];
                            /**
                             if (salonSalesDetailStatus == 3) {
                             salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[1];
                             }
                             **/

                            String temp_saveType = GlobalMemberValues.getSaveType(
                                    GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,10), 1));

                            String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 1);
                            String temp_kitchenPrintYN = "N";
                            if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
//                            temp_kitchenPrintYN = dbInit.dbExecuteReadReturnString(
//                                    "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                                temp_kitchenPrintYN = MssqlDatabase.getResultSetValueToString(
                                        "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                                if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                                    temp_kitchenPrintYN = "N";
                                }
                            }

                            LinearLayout subNewLn = new LinearLayout(context);
                            subNewLn.setLayoutParams(subNewLnLayoutParams);
                            String salesCodeAndIdx = dbSalesCode + "||" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13), 1)
                                    + "||" + salonSalesDetailStatus;
                            subNewLn.setTag(salesCodeAndIdx);
                            //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                            subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                            subNewLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));

                            LinearLayout subNewLn_in_title = new LinearLayout(context);
                            subNewLn_in_title.setLayoutParams(subNewLnLayoutParams);
                            subNewLn_in_title.setTag(salesCodeAndIdx);
                            subNewLn_in_title.setOrientation(LinearLayout.HORIZONTAL);
                            subNewLn_in_title.setBackgroundColor(Color.parseColor("#f5f5f5"));

                            if (saleItemsCount == 0) {
                                mSelectedSalesCode = dbSalesCode;
                                mSelectedSalonSalesDetailIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13), 1);
                                mSelectedSalonSalesDetailStatus = salonSalesDetailStatus + "";
                            }

                            // Kind (SaveType 0 : 서비스    1 : Product     2 : GiftCard)
                            TextView subNewTv1 = new TextView(context);
                            subNewTv1.setLayoutParams(subNewTvLayoutParams);
                            subNewTv1.setWidth(GlobalMemberValues.getDisplayWidth(context) / 27);
                            subNewTv1.setGravity(Gravity.CENTER);
                            String subSaveType = GlobalMemberValues.getSaveType(
                                    GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,10), 1));
                            subNewTv1.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv1.setText(subSaveType);
                            subNewTv1.setTextSize(f_fontsize);
                            subNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            //subNewTv1.setBackgroundResource(R.drawable.xml_border);
                            subNewLn_in_title.addView(subNewTv1);

                            if (salonSalesDetailStatus == 3) {
                                // Return 표시
                                TextView subNewTvReturn = new TextView(context);
                                subNewTvReturn.setLayoutParams(subNewTvLayoutParams);
                                subNewTvReturn.setWidth(GlobalMemberValues.getDisplayWidth(context) / 13);
                                subNewTvReturn.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                subNewTvReturn.setTypeface(typeface_monserrat_semi_bold);
                                subNewTvReturn.setTypeface(typeface_monserrat_semi_bold);
                                subNewTvReturn.setTextSize(f_fontsize + 2.0f);
//                                subNewTvReturn.setTextSize(GlobalMemberValues.globalAddFontSize() + subNewTvReturn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                                subNewTvReturn.setText("[RETURN]");
                                subNewTvReturn.setTextColor(Color.parseColor("#e8420b"));
                                subNewLn_in_title.addView(subNewTvReturn);

                                // 서비스 이름
                                TextView subNewTv2 = new TextView(context);
                                subNewTv2.setLayoutParams(subNewTvLayoutParams);
                                subNewTv2.setWidth((GlobalMemberValues.getDisplayWidth(context) * 2) / 12);
                                subNewTv2.setTypeface(typeface_monserrat_semi_bold);
                                subNewTv2.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 1));
                                subNewTv2.setTextSize(f_fontsize);
                                subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                subNewTv2.setPadding(0,5,0,5);
                                subNewLn_in_title.addView(subNewTv2);
                            } else {
                                // 서비스 이름
                                TextView subNewTv2 = new TextView(context);
                                subNewTv2.setLayoutParams(subNewTvLayoutParams);
                                subNewTv2.setWidth(GlobalMemberValues.getDisplayWidth(context) / 5);
                                subNewTv2.setTypeface(typeface_monserrat_semi_bold);
                                subNewTv2.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 1));
                                subNewTv2.setTextSize(f_fontsize);
                                subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                subNewTv2.setPadding(0,5,0,5);
                                subNewLn_in_title.addView(subNewTv2);
                            }

                            // 수량
                            TextView subNewTv3 = new TextView(context);
                            subNewTv3.setLayoutParams(subNewTvLayoutParams);
                            subNewTv3.setWidth(GlobalMemberValues.getDisplayWidth(context) / 23);
                            subNewTv3.setGravity(Gravity.CENTER);
                            subNewTv3.setPadding(5, 0, 5, 0);
                            subNewTv3.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv3.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 1));
                            subNewTv3.setTextSize(f_fontsize);
                            subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            subNewLn_in_title.addView(subNewTv3);

                            // Amount (세금미포함)
                            TextView subNewTv4 = new TextView(context);
                            subNewTv4.setLayoutParams(subNewTvLayoutParams);
                            subNewTv4.setWidth(GlobalMemberValues.getDisplayWidth(context) / 15);
                            subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                            subNewTv4.setPadding(0, 0, 5, 0);
                            subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble(
                                    GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,4), 1)));
                            subNewTv4.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv4.setTextSize(f_fontsize);
                            subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            subNewLn_in_title.addView(subNewTv4);

                            // Employee
                            TextView subNewTv5 = new TextView(context);
                            subNewTv5.setLayoutParams(subNewTvLayoutParams);
                            subNewTv5.setWidth(GlobalMemberValues.getDisplayWidth(context) / 10);
                            subNewTv5.setGravity(Gravity.CENTER);
                            subNewTv5.setPadding(5, 0, 5, 0);
                            subNewTv5.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv5.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,9), 1));
                            subNewTv5.setTextSize(f_fontsize);
                            subNewTv5.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            subNewLn_in_title.addView(subNewTv5);

                            // totalAmount
                            TextView subNewTv6 = new TextView(context);
                            subNewTv6.setLayoutParams(subNewTvLayoutParams);
                            subNewTv6.setWidth(GlobalMemberValues.getDisplayWidth(context) / 13);
                            subNewTv6.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                            subNewTv6.setPadding(0, 0, 5, 0);
                            subNewTv6.setText(GlobalMemberValues.getCommaStringForDouble(
                                    GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 1)));
                            subNewTv6.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv6.setTextSize(f_fontsize);
                            subNewTv6.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            subNewLn_in_title.addView(subNewTv6);

                            // Batch YN
                            TextView subNewTv7 = new TextView(context);
                            subNewTv7.setLayoutParams(subNewTvLayoutParams);
                            subNewTv7.setWidth(GlobalMemberValues.getDisplayWidth(context) / 15);
                            subNewTv7.setGravity(Gravity.CENTER);
                            subNewTv7.setPadding(0, 0, 5, 0);
                            String tempBatchYN = "N";
                            if (GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12), 1) == "3") {
                                tempBatchYN = "Y";
                            }
                            subNewTv7.setTypeface(typeface_monserrat_semi_bold);
                            subNewTv7.setText(tempBatchYN);
                            subNewTv7.setTextSize(f_fontsize);
                            subNewTv7.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            subNewLn_in_title.addView(subNewTv7);
                            subNewLn.addView(subNewLn_in_title);

                            newLn.addView(subNewLn);

                            // 옵션 또는 추가사항이 있을 경우 아래에 보여준다. --------------------------------------------------------------------
                            String tempOptionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,14), 1);
                            String tempOptionPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,15), 1);

                            String tempAdditionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16), 1);
                            String tempAdditionalPrice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17), 1);

                            String tempAdditionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,18), 1);
                            String tempAdditionalPrice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,19), 1);

                            String tempMemoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,20), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempMemoToKitchen) && tempMemoToKitchen.equals("nokitchenmemo")) {
                                tempMemoToKitchen = "";
                            }

                            if (!GlobalMemberValues.isStrEmpty(tempOptionTxt) ||
                                    !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1) || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)
                                    || !GlobalMemberValues.isStrEmpty(tempMemoToKitchen)) {

                                String tempOptionAddTxt = "";
                                if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                    tempOptionAddTxt = "[Option] "+ tempOptionTxt;
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                    if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                                        tempOptionAddTxt += "\n[Add Ingredients] " + tempAdditionalTxt1;
                                    } else {
                                        tempOptionAddTxt = "[Add Ingredients] " + tempAdditionalTxt1;
                                    }
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                                    if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                                        tempOptionAddTxt += "\n[Add Ingredients2] " + tempAdditionalTxt2;
                                    } else {
                                        tempOptionAddTxt = "[Add Ingredients2] " + tempAdditionalTxt2;
                                    }
                                }

                                if (!GlobalMemberValues.isStrEmpty(tempMemoToKitchen)) {
                                    if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                                        tempOptionAddTxt += "\n[Kitchen Notes] " + tempMemoToKitchen;
                                    } else {
                                        tempOptionAddTxt = "[Kitchen Notes] " + tempMemoToKitchen;
                                    }
                                }

                                double tempOptionAdditionalPrice = GlobalMemberValues.getDoubleAtString(tempOptionPrice) +
                                        GlobalMemberValues.getDoubleAtString(tempAdditionalPrice1) +
                                        GlobalMemberValues.getDoubleAtString(tempAdditionalPrice2);

                                LinearLayout subOptionAddLn = new LinearLayout(context);
                                subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                                //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);
                                subOptionAddLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));

                                LinearLayout subOptionAddLn_insubOtion = new LinearLayout(context);
                                subOptionAddLn_insubOtion.setLayoutParams(subNewLnLayoutParams);
                                //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                subOptionAddLn_insubOtion.setOrientation(LinearLayout.HORIZONTAL);
                                subOptionAddLn_insubOtion.setBackgroundResource(R.drawable.roundlayout_void_round);

                                // 화살표
                                ImageView optionAddImg = new ImageView(context);
                                optionAddImg.setPadding(10, 0, 10, 0);
                                optionAddImg.setImageResource(R.drawable.aa_images_arrow);
                                optionAddImg.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
                                optionAddImg.setVisibility(View.INVISIBLE);
                                subOptionAddLn_insubOtion.addView(optionAddImg);

                                // Option & Additional 내용
                                TextView optionAddTv = new TextView(context);
                                optionAddTv.setLayoutParams(subNewTvLayoutParams);
                                optionAddTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                optionAddTv.setPadding(5, 5, 5, 5);
                                optionAddTv.setTypeface(typeface_monserrat_regular);
                                optionAddTv.setText(tempOptionAddTxt);
                                optionAddTv.setTextSize(f_fontsize - 2);
                                optionAddTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                optionAddTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                                subOptionAddLn_insubOtion.addView(optionAddTv);

                                // Option & Additional 가격
                                TextView optionAddPriceTv = new TextView(context);
                                optionAddPriceTv.setLayoutParams(subNewTvLayoutParams);
                                optionAddPriceTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                                optionAddPriceTv.setPadding(5, 5, 5, 5);
                                optionAddPriceTv.setTypeface(typeface_monserrat_regular);
                                optionAddPriceTv.setText("(" + GlobalMemberValues.getCommaStringForDouble(tempOptionAdditionalPrice + "") + ")");
                                optionAddPriceTv.setTextSize(f_fontsize - 2);
                                optionAddPriceTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                optionAddPriceTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                                subOptionAddLn_insubOtion.addView(optionAddPriceTv);

                                // Option & Additional 빈칸
                                TextView optionAddEmptyTv = new TextView(context);
                                optionAddEmptyTv.setLayoutParams(subNewTvLayoutParams);
                                optionAddEmptyTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                                subOptionAddLn_insubOtion.addView(optionAddEmptyTv);

                                subOptionAddLn.addView(subOptionAddLn_insubOtion);

                                newLn.addView(subOptionAddLn);
                            }
                            // -----------------------------------------------------------------------------------------------------------------




                            // 05082023 -----------------------------------------------------------------------------------------------------------------
                            String tempWmodyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,21), 1);
                            if (GlobalMemberValues.isStrEmpty(tempWmodyn)) {
                                tempWmodyn = "N";
                            }
                            String tempWmodresult = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,22), 1);
                            if (GlobalMemberValues.isStrEmpty(tempWmodresult)) {
                                tempWmodresult = "N";
                            }
                            String tempWmodresultmsg = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,23), 1);
                            if (GlobalMemberValues.isStrEmpty(tempWmodresultmsg)) {
                                tempWmodresultmsg = "";
                            }

                            // 05102023
                            String tempWmUrl = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(24), 1);
                            if (GlobalMemberValues.isStrEmpty(tempWmUrl)) {
                                tempWmUrl = "";
                            }

                            // Wingman 연동 메뉴일 경우
                            if (tempWmodyn == "Y" || tempWmodyn.equals("Y")) {
                                String wmmsg_str = "Processing : ";
                                // 05102023
                                wmmsg_str += tempWmodresultmsg;

                                LinearLayout wmodLn = new LinearLayout(context);
                                wmodLn.setLayoutParams(subNewLnLayoutParams);
                                //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                wmodLn.setOrientation(LinearLayout.HORIZONTAL);
                                wmodLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));
                                LinearLayout wmodLn_insubOtion = new LinearLayout(context);
                                wmodLn_insubOtion.setLayoutParams(subNewLnLayoutParams);
                                //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                wmodLn_insubOtion.setOrientation(LinearLayout.HORIZONTAL);
                                wmodLn_insubOtion.setBackgroundResource(R.drawable.roundlayout_void_round);


                                TextView wingmanTv1 = new TextView(context);
                                wingmanTv1.setLayoutParams(subNewTvLayoutParams);
                                wingmanTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                wingmanTv1.setPadding(5, 5, 5, 5);
                                wingmanTv1.setTypeface(typeface_monserrat_bold);
                                wingmanTv1.setText("WINGMAN");
                                wingmanTv1.setTextSize(f_fontsize - 2);
                                wingmanTv1.setTextColor(Color.parseColor("#000000"));
                                wingmanTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.2f));
                                wmodLn_insubOtion.addView(wingmanTv1);


                                TextView wingmanTv2 = new TextView(context);
                                wingmanTv2.setLayoutParams(subNewTvLayoutParams);
                                wingmanTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                wingmanTv2.setPadding(5, 5, 5, 5);
                                wingmanTv2.setTypeface(typeface_monserrat_regular);
                                wingmanTv2.setText(wmmsg_str);
                                wingmanTv2.setTextSize(f_fontsize - 2);
                                wingmanTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                wingmanTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3.0f));
                                wmodLn_insubOtion.addView(wingmanTv2);


                                // 05102023
                                TextView wingmanTv3 = new TextView(context);
                                wingmanTv3.setLayoutParams(subNewTvLayoutParams);
                                wingmanTv3.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                                wingmanTv3.setPadding(5, 5, 5, 5);
                                wingmanTv3.setTypeface(typeface_monserrat_regular);
                                wingmanTv3.setText(tempWmUrl);
                                wingmanTv3.setTextSize(f_fontsize - 6);
                                wingmanTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                wingmanTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                                wmodLn_insubOtion.addView(wingmanTv3);


                                wmodLn.addView(wmodLn_insubOtion);

                                newLn.addView(wmodLn);
                            }
                            // 05082023 -----------------------------------------------------------------------------------------------------------------




                            // 주방프린팅 여부와 주방프린터 번호 구하기
                            String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                            GlobalMemberValues.logWrite("salehistorysavetypelog", "savetype : " + temp_saveType + "\n");
                            GlobalMemberValues.logWrite("salehistorysavetypelog", "temp_kitchenPrintYN : " + temp_kitchenPrintYN + "\n");

                            if (temp_saveType.equals("S") && temp_kitchenPrintYN.equals("Y")) {

                                // 08102023
                                // alt name 구하기
                                String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                                        " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                                );
                                if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                                    temp_itemName_alt = "";
                                }

                                String saleitem_name_optionadd = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 1) +
                                        "-ANNIETTASU-" + tempOptionTxt +
                                        "-ANNIETTASU-" + tempAdditionalTxt1 +
                                        "-ANNIETTASU-" + tempAdditionalTxt2 +
                                        "-ANNIETTASU-" + temp_itemIdx +
                                        "-ANNIETTASU-" + tempKitchenPrinterNumber +
                                        "-ANNIETTASU-" + tempMemoToKitchen +

                                        // 08102023
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +       // ALL 또는 EACH
                                        "-ANNIETTASU-" + "" +          // DC 또는 EX
                                        "-ANNIETTASU-" + "" +          // $ 또는 %
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +
                                        "-ANNIETTASU-" + "" +          // additem
                                        "-ANNIETTASU-" + "" +        // 라벨 프린트 했는지 YN

                                        // 08102023
                                        "-ANNIETTASU-" + temp_itemName_alt;

                                if (saleItemsCount == 0) {
                                    saleItemsTxt = saleitem_name_optionadd + "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 1);
                                } else {
                                    saleItemsTxt = saleItemsTxt + "-WANHAYE-"
                                            + saleitem_name_optionadd + "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 1);
                                }

                                GlobalMemberValues.logWrite("saleItemsTxtlog", "saleItemsTxt : " + saleItemsTxt + "\n");

                                saleItemsCount++;
                            }
                        }
                    } catch (Exception e){
                        Log.e("",e.toString());
                    }


                    salonSalesDetailCursor.close();
                    // ----------------------------------------------------------------------------------------------------------------------------------



                    // salon_sales_tip 데이터 출력 ---------------------------------------------------------------------------------------------------
                    // mssql 변경
                    String salonSalesTipQuery = "";
                    salonSalesTipQuery = "select idx, employeeIdx, employeeName, usedCash, usedCard " +
                            " from salon_sales_tip " +
                            " where salesCode = '" + dbSalesCode + "' " +
                            " and (not(usedCash = 0) or not(usedCard = 0))  " +
                            " order by employeeName asc";
                    GlobalMemberValues.logWrite("salonSalesTip", "salonSalesTipQuery  : " + salonSalesTipQuery + "\n");
//                    salonSalesTipCursor = dbInit.dbExecuteRead(salonSalesTipQuery);
                    salonSalesTipCursor = MssqlDatabase.getResultSetValue(salonSalesTipQuery);
                    String strEmpTip = "";
                    int tipCnt = 0;
                    while (salonSalesTipCursor.next()) {
                        String strEmpName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,2), 1);

                        String cashTip = "";
                        String strCashTip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,3), 1);
                        if (!GlobalMemberValues.isStrEmpty(strCashTip) && GlobalMemberValues.getDoubleAtString(strCashTip) > 0) {
                            cashTip = " CASH : " + GlobalMemberValues.getCommaStringForDouble(strCashTip) + "  ";
                        }

                        String cardTip = "";
                        String strCardTip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,4), 1);
                        if (!GlobalMemberValues.isStrEmpty(strCardTip) && GlobalMemberValues.getDoubleAtString(strCardTip) > 0) {
                            cardTip = " CARD : " + GlobalMemberValues.getCommaStringForDouble(strCardTip) + "  ";
                        }

                        if (!GlobalMemberValues.isStrEmpty(cashTip) || !GlobalMemberValues.isStrEmpty(cardTip)) {
                            strEmpTip = "[" + strEmpName + "] " + cashTip + cardTip + "     ";
                        }

                        // 05.04.2022 --------------------------------------------------------------------------------
                        ArrayList<String> tipArrayList = GlobalMemberValues.getTipListBySaleCode(getSalesCode);
                        if (tipArrayList != null && !tipArrayList.isEmpty()){
                            String tempCardDigitNum = "";
                            strEmpTip = "[" + strEmpName + "] ";
                            int count = 1;
                            for (String temp : tipArrayList){
                                String[] temp1 = temp.split("-JJJ-");

                                tempCardDigitNum = "";
                                if (temp1.length > 3) {
                                    tempCardDigitNum = temp1[3];
                                }
                                if (temp1[2].equals("CARD")) {
                                    strEmpTip += " " + temp1[2] + " (#" + tempCardDigitNum + ")";
                                } else {
                                    strEmpTip += " " + temp1[2];
                                }
                                strEmpTip += " " + temp1[1];
                                strEmpTip += "     ";
                                count++;
                            }
                        }
                        if (tipCnt > 0) {
                            strEmpTip = "\n" + strEmpTip;
                        }
                        // ---------------------------------------------------------------------------------------

                        if (!GlobalMemberValues.isStrEmpty(strEmpTip)) {
                            // LinearLayout 파라미터 설정
                            LinearLayout.LayoutParams tipLnLayoutParams1
                                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            tipLnLayoutParams1.setMargins(0, 0, 0, 0);

                            LinearLayout tipLn1 = new LinearLayout(context);
                            tipLn1.setLayoutParams(tipLnLayoutParams1);
                            tipLn1.setOrientation(LinearLayout.HORIZONTAL);
                            tipLn1.setBackgroundResource(R.drawable.roundlayout_void_round);
                            //tipLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                            LinearLayout.LayoutParams tipNewTvLayoutParams1
                                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                            TextView tipNewTv1 = new TextView(context);
                            tipNewTv1.setLayoutParams(tipNewTvLayoutParams1);
                            tipNewTv1.setWidth((GlobalMemberValues.getDisplayWidth(context) * 2) / 3);
                            tipNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            tipNewTv1.setPadding(5, 5, 5, 5);
                            tipNewTv1.setTypeface(typeface_monserrat_regular);
                            tipNewTv1.setText("Tip Info \n" + strEmpTip);
                            tipNewTv1.setTextSize(f_fontsize);
                            tipNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            tipLn1.addView(tipNewTv1);

                            newLn.addView(tipLn1);
                        }

                        tipCnt++;
                    }
                    salonSalesTipCursor.close();
                    // ------------------------------------------------------------------------------------------------------------------


                    // 고객정보 출력 ---------------------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,11))) {
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams customerLnLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams2
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                        // 고객정보 타이틀용 -------------------------------------------------------------------------------------------
                        LinearLayout customerTitleLn1 = new LinearLayout(context);
                        customerTitleLn1.setLayoutParams(customerLnLayoutParams1);
                        customerTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //customerTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        customerTitleLn1.setBackgroundColor(Color.parseColor("#f5f5f5"));

                        // 고객 아이디
                        TextView customerIdNewTv = new TextView(context);
                        customerIdNewTv.setLayoutParams(tipNewTvLayoutParams1);
                        //customerIdNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        customerIdNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdNewTv.setPadding(5, 5, 5, 5);
                        customerIdNewTv.setTypeface(typeface_monserrat_bold);
                        customerIdNewTv.setText("Customer Information");
                        customerIdNewTv.setTextSize(f_fontsize);
                        customerIdNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerIdNewTv.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        customerTitleLn1.addView(customerIdNewTv);

                        newLn.addView(customerTitleLn1);
                        // --------------------------------------------------------------------------------------------------------------

                        // 고객정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                        LinearLayout customerValueLn1 = new LinearLayout(context);
                        customerValueLn1.setLayoutParams(customerLnLayoutParams1);
                        customerValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        LinearLayout customerValueLn2 = new LinearLayout(context);
                        customerValueLn2.setLayoutParams(customerLnLayoutParams1);
                        customerValueLn2.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout customerValueLn_total = new LinearLayout(context);
                        customerValueLn_total.setLayoutParams(customerLnLayoutParams1);
                        customerValueLn_total.setOrientation(LinearLayout.VERTICAL);
                        customerValueLn_total.setBackgroundResource(R.drawable.roundlayout_void_round);

                        // 고객 아이디
                        TextView customerIdTitleNewTv = new TextView(context);
                        customerIdTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerIdTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdTitleNewTv.setPadding(5, 5, 5, 5);
                        customerIdTitleNewTv.setTypeface(typeface_monserrat_regular);
                        customerIdTitleNewTv.setText("ID : ");
                        customerIdTitleNewTv.setTextSize(f_fontsize);
                        customerIdTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerIdTitleNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                        customerValueLn1.addView(customerIdTitleNewTv);

                        TextView customerIdValueNewTv = new TextView(context);
                        customerIdValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerIdValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdValueNewTv.setPadding(5, 5, 5, 5);
                        customerIdValueNewTv.setTypeface(typeface_monserrat_regular);
                        customerIdValueNewTv.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,10), 1));
                        customerIdValueNewTv.setTextSize(f_fontsize);
                        customerIdValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerIdValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f));
                        customerValueLn1.addView(customerIdValueNewTv);

                        // 고객 이름
                        TextView customerNameTitleNewTv = new TextView(context);
                        customerNameTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerNameTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerNameTitleNewTv.setPadding(5, 5, 5, 5);
                        customerNameTitleNewTv.setTypeface(typeface_monserrat_regular);
                        customerNameTitleNewTv.setText("Name : ");
                        customerNameTitleNewTv.setTextSize(f_fontsize);
                        customerNameTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerNameTitleNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                        customerValueLn1.addView(customerNameTitleNewTv);

                        TextView customerNameValueNewTv = new TextView(context);
                        customerNameValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerNameValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerNameValueNewTv.setPadding(5, 5, 5, 5);
                        customerNameValueNewTv.setTypeface(typeface_monserrat_regular);
                        customerNameValueNewTv.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,11), 1));
                        customerNameValueNewTv.setTextSize(f_fontsize);
                        customerNameValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerNameValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f));
                        customerValueLn1.addView(customerNameValueNewTv);

                        // 전화번호
                        TextView customerPhoneTitleNewTv = new TextView(context);
                        customerPhoneTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerPhoneTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerPhoneTitleNewTv.setPadding(5, 5, 5, 5);
                        customerPhoneTitleNewTv.setTypeface(typeface_monserrat_regular);
                        customerPhoneTitleNewTv.setText("Phone# : ");
                        customerPhoneTitleNewTv.setTextSize(f_fontsize);
                        customerPhoneTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerPhoneTitleNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                        customerValueLn2.addView(customerPhoneTitleNewTv);

                        TextView customerPhoneValueNewTv = new TextView(context);
                        customerPhoneValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        customerPhoneValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerPhoneValueNewTv.setPadding(5, 5, 5, 5);
                        customerPhoneValueNewTv.setTypeface(typeface_monserrat_regular);
                        customerPhoneValueNewTv.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,12), 1));
                        customerPhoneValueNewTv.setTextSize(f_fontsize);
                        customerPhoneValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerPhoneValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f));
                        customerValueLn2.addView(customerPhoneValueNewTv);

//                        TextView customer_dummy = new TextView(context);
//                        customer_dummy.setLayoutParams(tipNewTvLayoutParams2);
//                        customer_dummy.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                        customer_dummy.setPadding(5, 5, 5, 5);
//                        customer_dummy.setTypeface(typeface_monserrat_regular);
////                    customer_dummy.setText(GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(12), 1));
//                        customer_dummy.setTextSize(f_fontsize);
//                        customer_dummy.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
//                        customer_dummy.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//                        customer_dummy.setVisibility(View.INVISIBLE);
//                        customerValueLn2.addView(customer_dummy);

                        // 고객 코드
                        /**
                         TextView customerPosCodeTitleNewTv = new TextView(context);
                         customerPosCodeTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                         customerPosCodeTitleNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 22);
                         customerPosCodeTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                         customerPosCodeTitleNewTv.setPadding(5, 5, 5, 5);
                         customerPosCodeTitleNewTv.setText("Code : ");
                         customerPosCodeTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                         customerValueLn1.addView(customerPosCodeTitleNewTv);

                         TextView customerPosCodeValueNewTv = new TextView(context);
                         customerPosCodeValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                         customerPosCodeValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 10);
                         customerPosCodeValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                         customerPosCodeValueNewTv.setPadding(5, 5, 5, 5);
                         customerPosCodeValueNewTv.setText(GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(13), 1));
                         customerPosCodeValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                         customerValueLn1.addView(customerPosCodeValueNewTv);
                         **/

                        customerValueLn_total.addView(customerValueLn1);
                        customerValueLn_total.addView(customerValueLn2);

                        newLn.addView(customerValueLn_total);
                        // ---------------------------------------------------------------------------------------------------------
                    } else {
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams customerLnLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        // WALK IN 고객 표시용 -------------------------------------------------------------------------------------------
                        LinearLayout customerTitleLn1 = new LinearLayout(context);
                        customerTitleLn1.setLayoutParams(customerLnLayoutParams1);
                        customerTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
//                    customerTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        customerTitleLn1.setBackgroundResource(R.drawable.roundlayout_void_round);

                        // 고객 아이디
                        TextView customerIdNewTv = new TextView(context);
                        customerIdNewTv.setLayoutParams(tipNewTvLayoutParams1);
                        //customerIdNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        customerIdNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdNewTv.setPadding(5, 5, 5, 5);
                        customerIdNewTv.setTypeface(typeface_monserrat_regular);
                        customerIdNewTv.setText("Customer Info : WALK IN");
                        customerIdNewTv.setTextSize(f_fontsize);
                        customerIdNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerTitleLn1.addView(customerIdNewTv);

                        newLn.addView(customerTitleLn1);
                        // --------------------------------------------------------------------------------------------------------------
                    }
                    // ------------------------------------------------------------------------------------------------------------------

                    String salonSalesCustomerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,24), 1);
                    String salonSalesCustomerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,25), 1);
                    String salonSalesCustomerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,26), 1);
                    String salonSalesCustomerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,27), 1);
                    String salonSalesCustomerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,28), 1);

                    String salonSalesCustomermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,33), 1);

                    String salonSalesDeliveryDate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,31), 1);



                    // 배송정보 출력 (배달일경우) -----------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(salonSalesDeliveryTakeaway) && salonSalesDeliveryTakeaway.equals("D")) {
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams customerLnLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams2
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                        // 배송정보 타이틀용 -------------------------------------------------------------------------------------------
                        LinearLayout deliveryTitleLn1 = new LinearLayout(context);
                        deliveryTitleLn1.setLayoutParams(customerLnLayoutParams1);
                        deliveryTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //deliveryTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        deliveryTitleLn1.setBackgroundColor(Color.parseColor("#f5f5f5"));


                        TextView deliveryTitleNewTv = new TextView(context);
                        deliveryTitleNewTv.setLayoutParams(tipNewTvLayoutParams1);
                        //deliveryTitleNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        deliveryTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        deliveryTitleNewTv.setPadding(5, 15, 5, 0);
                        deliveryTitleNewTv.setTypeface(typeface_monserrat_bold);
                        deliveryTitleNewTv.setText("Delivery Information ");
                        deliveryTitleNewTv.setTextSize(f_fontsize);
                        deliveryTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        deliveryTitleLn1.addView(deliveryTitleNewTv);

                        newLn.addView(deliveryTitleLn1);
                        // --------------------------------------------------------------------------------------------------------------

                        // 배송정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                        LinearLayout deliveryValueLn1 = new LinearLayout(context);
                        deliveryValueLn1.setLayoutParams(customerLnLayoutParams1);
                        deliveryValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        deliveryValueLn1.setBackgroundResource(R.drawable.roundlayout_void_round);

                        // 배송타입
                        TextView ReceivingTypeTv = new TextView(context);
                        ReceivingTypeTv.setLayoutParams(tipNewTvLayoutParams2);
                        ReceivingTypeTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        ReceivingTypeTv.setPadding(5, 5, 5, 5);
                        ReceivingTypeTv.setTypeface(typeface_monserrat_regular);
                        ReceivingTypeTv.setText("Receiving Type : ");
                        ReceivingTypeTv.setTextSize(f_fontsize);
                        ReceivingTypeTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        ReceivingTypeTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        deliveryValueLn1.addView(ReceivingTypeTv);

                        TextView ReceivingTypeValueTv = new TextView(context);
                        ReceivingTypeValueTv.setLayoutParams(tipNewTvLayoutParams2);
                        ReceivingTypeValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        ReceivingTypeValueTv.setPadding(5, 5, 5, 5);
                        ReceivingTypeValueTv.setTypeface(typeface_monserrat_regular);
                        ReceivingTypeValueTv.setText("DELIVERY" + tempDeliveryPickUpFee);
                        ReceivingTypeValueTv.setTextSize(f_fontsize);
                        ReceivingTypeValueTv.setTextColor(Color.parseColor("#1f71ba"));
                        ReceivingTypeValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        deliveryValueLn1.addView(ReceivingTypeValueTv);

                        TextView deliveryRequestDateTv = new TextView(context);
                        deliveryRequestDateTv.setLayoutParams(tipNewTvLayoutParams2);
                        deliveryRequestDateTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        deliveryRequestDateTv.setPadding(5, 5, 5, 5);
                        deliveryRequestDateTv.setTypeface(typeface_monserrat_regular);
                        deliveryRequestDateTv.setText("Date of delivery request : ");
                        deliveryRequestDateTv.setTextSize(f_fontsize);
                        deliveryRequestDateTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        deliveryRequestDateTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.2f));
                        deliveryValueLn1.addView(deliveryRequestDateTv);

                        // 배송예약일시
                        TextView deliveryRequestDateValueTv = new TextView(context);
                        deliveryRequestDateValueTv.setLayoutParams(tipNewTvLayoutParams2);
                        deliveryRequestDateValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        deliveryRequestDateValueTv.setPadding(5, 5, 5, 5);
                        deliveryRequestDateValueTv.setTypeface(typeface_monserrat_regular);
                        deliveryRequestDateValueTv.setText(salonSalesDeliveryDate);
                        deliveryRequestDateValueTv.setTextSize(f_fontsize);
                        deliveryRequestDateValueTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        deliveryRequestDateValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.3f));
                        deliveryValueLn1.addView(deliveryRequestDateValueTv);

                        newLn.addView(deliveryValueLn1);
                        // ---------------------------------------------------------------------------------------------------------

                        // 배송정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                        LinearLayout deliveryAddrValueLn1 = new LinearLayout(context);
                        deliveryAddrValueLn1.setLayoutParams(customerLnLayoutParams1);
                        deliveryAddrValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        deliveryAddrValueLn1.setBackgroundResource(R.drawable.roundlayout_void_round);

                        // 배송타입
                        TextView addressTitleTv = new TextView(context);
                        addressTitleTv.setLayoutParams(tipNewTvLayoutParams2);
                        addressTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        addressTitleTv.setPadding(5, 5, 5, 5);
                        addressTitleTv.setTypeface(typeface_monserrat_bold);
                        addressTitleTv.setText("Delivery Address : ");
                        addressTitleTv.setTextSize(f_fontsize);
                        addressTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        addressTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
                        deliveryAddrValueLn1.addView(addressTitleTv);

                        TextView addressValueTv = new TextView(context);
                        addressValueTv.setLayoutParams(tipNewTvLayoutParams2);
                        addressValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        addressValueTv.setPadding(5, 5, 5, 5);
                        addressValueTv.setTypeface(typeface_monserrat_regular);
                        addressValueTv.setText(
                                salonSalesCustomerAddr1 + " " + salonSalesCustomerAddr2 + ", " + salonSalesCustomerCity + ", "
                                        + salonSalesCustomerState + salonSalesCustomerZip);
                        addressValueTv.setTextSize(f_fontsize);
                        addressValueTv.setTextColor(Color.parseColor("#1f71ba"));
                        addressValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                        deliveryAddrValueLn1.addView(addressValueTv);

                        newLn.addView(deliveryAddrValueLn1);
                        // ---------------------------------------------------------------------------------------------------------
                    }
                    // ------------------------------------------------------------------------------------------------------------------

                    // 배송정보 출력 (Pick Up 일 경어) -----------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(salonSalesDeliveryTakeaway) && salonSalesDeliveryTakeaway.equals("T")) {
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams customerLnLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams2
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                        // 배송정보 타이틀용 -------------------------------------------------------------------------------------------
                        LinearLayout takeawayTitleLn1 = new LinearLayout(context);
                        takeawayTitleLn1.setLayoutParams(customerLnLayoutParams1);
                        takeawayTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //takeawayTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        takeawayTitleLn1.setBackgroundColor(Color.parseColor("#f5f5f5"));


                        TextView takeawayTitleNewTv = new TextView(context);
                        takeawayTitleNewTv.setLayoutParams(tipNewTvLayoutParams1);
                        //takeawayTitleNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        takeawayTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        takeawayTitleNewTv.setPadding(5, 15, 5, 0);
                        takeawayTitleNewTv.setTypeface(typeface_monserrat_bold);
                        takeawayTitleNewTv.setText("Pick Up Information");
                        takeawayTitleNewTv.setTextSize(f_fontsize);
                        takeawayTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        takeawayTitleLn1.addView(takeawayTitleNewTv);

                        newLn.addView(takeawayTitleLn1);
                        // --------------------------------------------------------------------------------------------------------------

                        // 배송정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                        LinearLayout takeawayValueLn1 = new LinearLayout(context);
                        takeawayValueLn1.setLayoutParams(customerLnLayoutParams1);
                        takeawayValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                        // 배송타입
                        TextView ReceivingTypeTv = new TextView(context);
                        ReceivingTypeTv.setLayoutParams(tipNewTvLayoutParams2);
                        ReceivingTypeTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        ReceivingTypeTv.setPadding(5, 5, 5, 5);
                        ReceivingTypeTv.setTypeface(typeface_monserrat_regular);
                        ReceivingTypeTv.setText("Receiving Type : ");
                        ReceivingTypeTv.setTextSize(f_fontsize);
                        ReceivingTypeTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        ReceivingTypeTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        takeawayValueLn1.addView(ReceivingTypeTv);

                        TextView ReceivingTypeValueTv = new TextView(context);
                        ReceivingTypeValueTv.setLayoutParams(tipNewTvLayoutParams2);
                        ReceivingTypeValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        ReceivingTypeValueTv.setPadding(5, 5, 5, 5);
                        ReceivingTypeValueTv.setTypeface(typeface_monserrat_regular);
                        ReceivingTypeValueTv.setText("PICK UP" + tempDeliveryPickUpFee);
                        ReceivingTypeValueTv.setTextSize(f_fontsize);
                        ReceivingTypeValueTv.setTextColor(Color.parseColor("#1f71ba"));
                        ReceivingTypeValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3.5f));
                        takeawayValueLn1.addView(ReceivingTypeValueTv);

                        newLn.addView(takeawayValueLn1);
                        // ---------------------------------------------------------------------------------------------------------
                    }
                    // ------------------------------------------------------------------------------------------------------------------

                    // 고객 메모 ---------------------------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(salonSalesCustomermemo)) {
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams customerLnLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams2
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                        // 배송정보 타이틀용 -------------------------------------------------------------------------------------------
                        LinearLayout customermemoTitleLn1 = new LinearLayout(context);
                        customermemoTitleLn1.setLayoutParams(customerLnLayoutParams1);
                        customermemoTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                        //deliveryTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        customermemoTitleLn1.setBackgroundColor(Color.parseColor("#f5f5f5"));

                        TextView customermemoTitleNewTv = new TextView(context);
                        customermemoTitleNewTv.setLayoutParams(tipNewTvLayoutParams1);
                        //customermemoTitleNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        customermemoTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customermemoTitleNewTv.setPadding(5, 15, 5, 0);
                        customermemoTitleNewTv.setTypeface(typeface_monserrat_bold);
                        customermemoTitleNewTv.setText("Notes");
                        customermemoTitleNewTv.setTextSize(f_fontsize);
                        customermemoTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customermemoTitleLn1.addView(customermemoTitleNewTv);

                        newLn.addView(customermemoTitleLn1);
                        // --------------------------------------------------------------------------------------------------------------

                        // --------------------------------------------------------------------------------------------------------------
                        LinearLayout customermemoValueLn1 = new LinearLayout(context);
                        customermemoValueLn1.setLayoutParams(customerLnLayoutParams1);
                        customermemoValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                        customermemoValueLn1.setBackgroundResource(R.drawable.roundlayout_void_round);
//                    customermemoValueLn1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                        //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                        TextView customermemoValueTv = new TextView(context);
                        customermemoValueTv.setLayoutParams(tipNewTvLayoutParams2);
                        customermemoValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customermemoValueTv.setPadding(5, 5, 5, 5);
                        customermemoValueTv.setTypeface(typeface_monserrat_regular);
                        customermemoValueTv.setText(salonSalesCustomermemo);
                        customermemoValueTv.setTextSize(f_fontsize);
                        customermemoValueTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customermemoValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        customermemoValueLn1.addView(customermemoValueTv);

                        newLn.addView(customermemoValueLn1);
                        // ---------------------------------------------------------------------------------------------------------
                    }
                    // ------------------------------------------------------------------------------------------------------------------

                    // void reason add up
                    if (!str_cancelreason.isEmpty()){
                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams tipNewTvLayoutParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                        LinearLayout voidReason_Ln1 = new LinearLayout(context);
                        voidReason_Ln1.setLayoutParams(tipNewTvLayoutParams1);
                        voidReason_Ln1.setOrientation(LinearLayout.HORIZONTAL);
                        voidReason_Ln1.setBackgroundColor(Color.parseColor("#000000"));
                        voidReason_Ln1.setPadding(0, 8, 0, 8);

                        TextView tv_voidReason = new TextView(context);
                        tv_voidReason.setLayoutParams(statusTvLayoutParams2);
                        tv_voidReason.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        tv_voidReason.setPadding(15, 5, 15, 5);
                        tv_voidReason.setTypeface(typeface_monserrat_semi_bold);
                        tv_voidReason.setText("Void Reason : " + str_cancelreason);
                        tv_voidReason.setTextSize(f_fontsize);
                        tv_voidReason.setTextColor(Color.parseColor("#ffffff"));

                        voidReason_Ln1.addView(tv_voidReason);
                        newLn.addView(voidReason_Ln1);
                    }



                    // 주방프린트 상태 ----------------------------------------------------------------------------------------------------

                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams customerLnLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                    LinearLayout.LayoutParams tipNewTvLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                    LinearLayout.LayoutParams tipNewTvLayoutParams2
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                    // ----------------------------------------------------------------------------------------------------------------
                    LinearLayout kitchenprintedTitleLn1 = new LinearLayout(context);
                    kitchenprintedTitleLn1.setLayoutParams(customerLnLayoutParams1);
                    kitchenprintedTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                    kitchenprintedTitleLn1.setPadding(0, 8, 0, 8);
                    //deliveryTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    TextView kitchenprintedTv1 = new TextView(context);
                    kitchenprintedTv1.setLayoutParams(tipNewTvLayoutParams1);
                    //kitchenprintedTv1.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                    kitchenprintedTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    kitchenprintedTv1.setPadding(5, 10, 15, 10);
                    kitchenprintedTv1.setTextSize(f_fontsize);
                    kitchenprintedTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    kitchenprintedTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//                kitchenprintedTv1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    kitchenprintedTitleLn1.addView(kitchenprintedTv1);
                    // ----------------------------------------------------------------------------------------
                    LinearLayout kitchenPrintedLn = new LinearLayout(context);
                    kitchenPrintedLn.setLayoutParams(statusLnLayoutParams1);
                    kitchenPrintedLn.setOrientation(LinearLayout.HORIZONTAL);
                    kitchenPrintedLn.setPadding(15, 5, 5, 5);
                    kitchenPrintedLn.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    kitchenPrintedLn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                    kitchenPrintedLn.setBackgroundColor(Color.parseColor("#f0f0f0"));


                    TextView kitchenPrintedTitleTv = new TextView(context);
                    kitchenPrintedTitleTv.setLayoutParams(statusTvLayoutParams2);
                    kitchenPrintedTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    kitchenPrintedTitleTv.setPadding(15, 5, 15, 5);
                    kitchenPrintedTitleTv.setTypeface(typeface_monserrat_semi_bold);
                    kitchenPrintedTitleTv.setText(Html.fromHtml(kitchenPrintedStr));
                    kitchenPrintedTitleTv.setTextSize(f_fontsize);
                    kitchenPrintedTitleTv.setTextColor(Color.parseColor(kitchenPrintedColorStr));
//                    kitchenPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint);
                    if (salonSalesKitchenPrintedYN.equals("Y")) {
                        kitchenPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint2);
                    } else {
                        kitchenPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint3);
                    }

                    final String kpSalesCode = dbSalesCode;
                    final String kpCustomerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,11), 1);
                    final String kpDeliveryTakeaway = salonSalesDeliveryTakeaway;
                    final String kpDeliveryDate = salonSalesDeliveryDate;
                    final String kpCustomerMemo = salonSalesCustomermemo;
                    final String kpSaleItems = saleItemsTxt;
                    final String kpCustomerOrderNumber = tempCustomerOrderNumber;
                    final String finalTempCustomerpagernumber = tempCustomerpagernumber;

                    final String final_customerPhoneNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,12), 1);

                    // 04.20.2022 추가
                    if (!GlobalMemberValues.isStrEmpty(final_customerPhoneNum)) {
                        setToPhoneNum = final_customerPhoneNum;
                    }

                    kitchenPrintedTitleTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 주방프린트 하기 --------------------------------------------------------------------
                            if (GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                                    "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
//                            if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
//                                    "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
                                JSONObject jsonroot_kitchen = new JSONObject();
                                try {
                                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "pick type : " + kpDeliveryTakeaway + "\n");
                                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "order no : " + kpCustomerOrderNumber + "\n");

                                    jsonroot_kitchen.put("receiptno", kpSalesCode);
                                    jsonroot_kitchen.put("saledate", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                                    jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                                    jsonroot_kitchen.put("customername", kpCustomerName);
                                    jsonroot_kitchen.put("saleitemlist", kpSaleItems);
                                    jsonroot_kitchen.put("deliverytakeaway", kpDeliveryTakeaway);
                                    jsonroot_kitchen.put("deliverydate", kpDeliveryDate);
                                    jsonroot_kitchen.put("ordertype", "POS");
                                    jsonroot_kitchen.put("customermemo", kpCustomerMemo);
                                    jsonroot_kitchen.put("customerordernumber", kpCustomerOrderNumber);
                                    jsonroot_kitchen.put("customerpagernumber", finalTempCustomerpagernumber);

                                    jsonroot_kitchen.put("customerphonenum", final_customerPhoneNum);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

//                                rePrintInKitchen(jsonroot_kitchen, kpSalesCode);
                                rePrintInKitchen_mssql(kpSalesCode);
                            }
                            // -----------------------------------------------------------------------------------
                        }
                    });
//                    kitchenPrintedLn_inln.addView(kitchenPrintedTitleTv);
                    kitchenPrintedLn.addView(kitchenPrintedTitleTv);
                    // ----------------------------------------------------------------------------------------

                    kitchenprintedTitleLn1.addView(kitchenPrintedLn);

                    newLn.addView(kitchenprintedTitleLn1);
                    // --------------------------------------------------------------------------------------------------------------
                    // ------------------------------------------------------------------------------------------------------------------
                    // Label print
                    if (GlobalMemberValues.isUseLabelPrinter()){
                        // LinearLayout 파라미터 설정
                        LinearLayout.LayoutParams labelPrintLnParams1
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        labelPrintLnParams1.setMargins(0, 0, 0, 0);

                        // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                        LinearLayout.LayoutParams labelPrintLnParams2
                                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        labelPrintLnParams2.setMargins(0, 0, 0, 0);

                        // ----------------------------------------------------------------------------------------------------------------
                        LinearLayout LabelprintedTitleLn1 = new LinearLayout(context);
                        LabelprintedTitleLn1.setLayoutParams(labelPrintLnParams1);
                        LabelprintedTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                        LabelprintedTitleLn1.setPadding(0, 8, 0, 8);
                        //deliveryTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                        TextView LabelprintedTv1 = new TextView(context);
                        LabelprintedTv1.setLayoutParams(labelPrintLnParams2);
                        //LabelprintedTv1.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                        LabelprintedTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        LabelprintedTv1.setPadding(5, 10, 15, 10);
                        LabelprintedTv1.setTextSize(f_fontsize);
                        LabelprintedTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        LabelprintedTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//                LabelprintedTv1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                        LabelprintedTitleLn1.addView(LabelprintedTv1);
                        // ----------------------------------------------------------------------------------------
                        LinearLayout LabelPrintedLn = new LinearLayout(context);
                        LabelPrintedLn.setLayoutParams(statusLnLayoutParams1);
                        LabelPrintedLn.setOrientation(LinearLayout.HORIZONTAL);
                        LabelPrintedLn.setPadding(15, 5, 5, 5);
                        LabelPrintedLn.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        LabelPrintedLn.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                LabelPrintedLn.setBackgroundColor(Color.parseColor("#f0f0f0"));


                        String LabelPrintedStr = "";
                        String LabelPrintedColorStr = "#a4a2a9";
                        if (salonSalesKitchenPrintedYN.equals("Y")) {
                            LabelPrintedStr = "<font color=\"#f20e02\">Touch Here</font> to print in Label";
//                    kitchenPrintedColorStr = "#a4a2a9";
                            LabelPrintedColorStr = "#000000";
                        } else {
                            LabelPrintedStr = "<font color=\"#f20e02\">Touch Here</font> to print in Label";
//                    kitchenPrintedColorStr = "#0054d5";
                            LabelPrintedColorStr = "#000000";
                        }

                        TextView LabelPrintedTitleTv = new TextView(context);
                        LabelPrintedTitleTv.setLayoutParams(statusTvLayoutParams2);
                        LabelPrintedTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        LabelPrintedTitleTv.setPadding(15, 5, 15, 5);
                        LabelPrintedTitleTv.setTypeface(typeface_monserrat_semi_bold);
                        LabelPrintedTitleTv.setText(Html.fromHtml(LabelPrintedStr));
                        LabelPrintedTitleTv.setTextSize(f_fontsize);
                        LabelPrintedTitleTv.setTextColor(Color.parseColor(LabelPrintedColorStr));
                        LabelPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint2);
//                if (salonSalesKitchenPrintedYN.equals("Y")) {
//                    LabelPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint2);
//                } else {
//                    LabelPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint3);
//                }

                        LabelPrintedTitleTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // label print 하기 --------------------------------------------------------------------
                                LabelprintReceipt(context,mSelectedSalesCode);
                                // -----------------------------------------------------------------------------------
                            }
                        });

                        LabelPrintedLn.addView(LabelPrintedTitleTv);
                        // ----------------------------------------------------------------------------------------

                        LabelprintedTitleLn1.addView(LabelPrintedLn);

                        newLn.addView(LabelprintedTitleLn1);
                        // =========================================================================================================
                    }

                    saleHistorySaleListLinearLayout.addView(newLn);
                    dataCount++;
                }
            }
            salonSalesCursor.close();
//            MssqlDatabase.conn.close();
        } catch (Exception e){
            Log.e("",e.toString());
        }

    }

    public static void rePrintInKitchen_mssql(String paramSalesCode) {
        if ((context != null) && (!mActivity.isFinishing())) {
            new AlertDialog.Builder(context)
                    .setTitle("Kitchen Printing")
                    .setMessage("Are you sure you want to reprint in kitchen?\n[Sale Group# : " + paramSalesCode + "]")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!GlobalMemberValues.isStrEmpty(paramSalesCode)) {
                                        Vector<String> strInsertQueryVec = new Vector<String>();

                                        String tempJsonStr_kitchen = "";
                                        String tempKitchenQuery = "";
                                        tempKitchenQuery = " select jsonstr from salon_sales_kitchenprintingdata_json where salesCode = 'K" + paramSalesCode.substring(1) + "' ";
                                        tempJsonStr_kitchen = MssqlDatabase.getResultSetValueToString(tempKitchenQuery);

                                        // 01132023 ---------------------------------------------------------------------------
                                        tempKitchenQuery = " select holdcode from salon_sales where salesCode = 'K" + paramSalesCode.substring(1) + "' ";
                                        String tempHoldCode = MssqlDatabase.getResultSetValueToString(tempKitchenQuery);
                                        JSONObject tempJsonObject = null;
                                        try {
                                            tempJsonObject = new JSONObject(tempJsonStr_kitchen);
                                            tempJsonObject.put("holdcode", tempHoldCode);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // -------------------------------------------------------------------------------------

                                        // 11102023
                                        if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(tempJsonObject.toString())) {
                                            tempKitchenQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                                    " (salesCode, scode, sidx, stcode, jsonstr) values ( " +
                                                    " 'K" + paramSalesCode.substring(1) + "', " +
                                                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                                                    "  " + GlobalMemberValues.STORE_INDEX + ", " +
                                                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                                                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempJsonObject.toString(), 0) + "' " +
                                                    " ) ";
                                            strInsertQueryVec.addElement(tempKitchenQuery);
                                        }

                                        for (String tempQuery : strInsertQueryVec) {
                                            GlobalMemberValues.logWrite("PaymentQueryStringVoidLog", "query : " + tempQuery + "\n");
                                        }

                                        // 트랜잭션으로 DB 처리한다.
                                        String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
//                                        String returnResult = dbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
                                        if (returnResult == "N" || returnResult == "") {
                                            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                        } else { // 정상처리일 경우 Sale History 리스트 리로드
                                            setSearchSalesHistory();
                                        }
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
    }

    public static void rePrintInKitchen(final JSONObject paramJson, String paramSalesCode) {
        if ((context != null) && (!mActivity.isFinishing())) {
            new AlertDialog.Builder(context)
                    .setTitle("Kitchen Printing")
                    .setMessage("Are you sure you want to reprint in kitchen?\n[Sale Group# : " + paramSalesCode + "]")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!GlobalMemberValues.isStrEmpty(paramJson.toString())) {
                                        GlobalMemberValues.mRekitchenprintYN = "Y";
                                        GlobalMemberValues.printGateByKitchen(paramJson, context, "kitchen1");
                                        setSearchSalesHistory();
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
    }


    public void setCreditCardProcessVoid() {
        // 프로그래스 바를 실행~
        //proDial = ProgressDialog.show(this, "NAVYZEBRA MSALON ANDROID", "Credit Card Void Processing...", true, false);

        // 먼저 void 처리할 세일즈에서 기프트카드가 있는지 확인하고, 사용한 이력이 있는 기프트카드이면 void 처리를 못하게 한다.
        if (!checkUsedGiftCardInSales(mSelectedSalonSalesDetailIdx, "VOID")) {
            GlobalMemberValues.displayDialog(context, "Warning", "The gift card balance is less than the amount sold", "Close");
            return;
        }

        switch (paymentGateway) {
            // Ingenico ICT200 인지니코
            case "0": {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        /** 1. 처리가 오래걸리는 부분 실행 *****************************************************/
                        ArrayList<String> tempArrCreditCardItem = getCreditCardItemsArrayListForIngenico();
                        if (tempArrCreditCardItem.size() > 0) {         // 온라인 카드결제가 있을 경우...
                            // 카드 결제처리
                            //PayByCreditCard payByCreditCard = new PayByCreditCard();
                            //creditCardVoidReturnValue = payByCreditCard.creditCardPaymentByTerminalForVoid(tempArrCreditCardItem);
                            GlobalMemberValues.logWrite("creditcardvoidprocess", "카드 Void 처리결과 : " + creditCardVoidReturnValue + "\n");
                        } else {                                        // 온라인 카드결제가 없을 경우...
                            creditCardVoidReturnValue = "00";
                        }
                        /*************************************************************************************/
                        /** 2. 작업이 끝나면 이 핸들러를 호출 **************************************************/
                        handler.sendEmptyMessage(0);
                        /*************************************************************************************/
                    }
                });
                thread.start();

                break;
            }
            // PAX
            case "1": {
                if (!GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                    mCardPaymentInfoArrayList = null;
                    mCardPaymentInfoArrayList = getCreditCardItemsArrayListForPax(mSelectedSalonSalesDetailIdx);
                    String tempCardPaymentInfo = "";
                    if (mCardPaymentInfoArrayList.size() > 0) {
                        tempCardPaymentInfo = mCardPaymentInfoArrayList.get(0);
                        if (!GlobalMemberValues.isStrEmpty(tempCardPaymentInfo)) {
                            String[] strCardPaymentInfoArr = tempCardPaymentInfo.split(GlobalMemberValues.STRSPLITTER2);
                            String tempIdx = strCardPaymentInfoArr[0];
                            String tempRefNum = strCardPaymentInfoArr[1];
                            String tempTid = strCardPaymentInfoArr[2];
                            String tempPriceAmount = strCardPaymentInfoArr[3];
                            if (!GlobalMemberValues.isStrEmpty(tempTid)) {
                                openJJJPaxPayForGroupVoid(tempRefNum, tempIdx, tempPriceAmount, "0");
                            }
                        }
                    } else {
                        setVoidSalesDatabaseProcess();
                    }
                } else {
                    GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to void", "Close");
                }
            }
        }
    }

    public static boolean checkUsedGiftCardInSales(String paramSelectedSalonSalesDetailIdx, String paramCheckType) {
        boolean returnValue = true;

        if (!GlobalMemberValues.isStrEmpty(paramSelectedSalonSalesDetailIdx)) {
            if (paramCheckType.equals("VOID")) {                // Void 처리시
                String selectedSalesCode = "";
                selectedSalesCode = MssqlDatabase.getResultSetValueToString(
                        "select salesCode from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' ");
//                selectedSalesCode = dbInit.dbExecuteReadReturnString(
//                        "select salesCode from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' ");
                String tempGCNum = "";
                tempGCNum = MssqlDatabase.getResultSetValueToString(
                        "select giftcardNumberToSave from salon_sales_detail where salesCode = '" + selectedSalesCode + "' and saveType = '2' ");
//                tempGCNum = dbInit.dbExecuteReadReturnString(
//                        "select giftcardNumberToSave from salon_sales_detail where salesCode = '" + selectedSalesCode + "' and saveType = '2' ");
                if (!GlobalMemberValues.isStrEmpty(tempGCNum)) {
                    // 기프트카드 구매금액
                    String tempGiftCardSavedPrice = "";
                    tempGiftCardSavedPrice = MssqlDatabase.getResultSetValueToString(
                            "select giftcardSavePriceToSave from salon_sales_detail where salesCode = '" + selectedSalesCode + "' and saveType = '2' ");
//                    tempGiftCardSavedPrice = dbInit.dbExecuteReadReturnString(
//                            "select giftcardSavePriceToSave from salon_sales_detail where salesCode = '" + selectedSalesCode + "' and saveType = '2' ");

                    // 현재 남아있는 기프트카드 적립금액
                    String giftCardBalance = "";
                    giftCardBalance = GlobalMemberValues.checkGiftCardBalance(context, tempGCNum);

                    if (GlobalMemberValues.getDoubleAtString(giftCardBalance) < GlobalMemberValues.getDoubleAtString(tempGiftCardSavedPrice)) {
                        returnValue = false;
                    } else {
                        returnValue = true;
                    }
                }
            } else {                                            // Return 처리시
                String tempGCNum = "";
                tempGCNum = MssqlDatabase.getResultSetValueToString(
                        "select giftcardNumberToSave from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' and saveType = '2' ");
//                tempGCNum = dbInit.dbExecuteReadReturnString(
//                        "select giftcardNumberToSave from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' and saveType = '2' ");
                if (!GlobalMemberValues.isStrEmpty(tempGCNum)) {
                    // 기프트카드 구매금액
                    String tempGiftCardSavedPrice = "";
                    tempGiftCardSavedPrice = MssqlDatabase.getResultSetValueToString(
                            "select giftcardSavePriceToSave from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' and saveType = '2' ");
//                    tempGiftCardSavedPrice = dbInit.dbExecuteReadReturnString(
//                            "select giftcardSavePriceToSave from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' and saveType = '2' ");

                    // 현재 남아있는 기프트카드 적립금액
                    String giftCardBalance = "";
                    giftCardBalance = GlobalMemberValues.checkGiftCardBalance(context, tempGCNum);

                    if (GlobalMemberValues.getDoubleAtString(giftCardBalance) < GlobalMemberValues.getDoubleAtString(tempGiftCardSavedPrice)) {
                        returnValue = false;
                    } else {
                        returnValue = true;
                    }
                }
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    public static void openJJJPaxPayForGroupVoid(String paramRefNum, String paramTagValue, String paramPriceAmount, String paramGroupVoidCount) {
        if (GlobalMemberValues.ISCHECK_BEFORE_CARDPAY) {
            // 가장 먼저 인터넷 체크를 한다.
            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                if (!GlobalMemberValues.isOnline2().equals("00")) {
                    GlobalMemberValues.showDialogNoInternet(context);
                    return;
                }
            } else {
                if (!GlobalMemberValues.isOnline2().equals("00")) {
                    GlobalMemberValues.openNetworkNotConnected();
                    return;
                }
            }
        }

        Intent intent = new Intent(context, JJJ_PaxPay.class);
        // 객체 및 데이터 전달하기 ---------------------------------------------------------------
        intent.putExtra("cardtendertype", "CREDIT");
        intent.putExtra("processtype", "VOID");
        intent.putExtra("amounttopay", "");
        intent.putExtra("refnum", paramRefNum);
        intent.putExtra("ecrrefnum", Payment.mSalesCode);
        intent.putExtra("salonsalescardidx", paramTagValue);
        intent.putExtra("priceamountforvoid", paramPriceAmount);
        intent.putExtra("selectedcardtagname", paramTagValue);
        intent.putExtra("groupvoidcount", paramGroupVoidCount);
        intent.putExtra("groupvoidyn", "Y");
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(intent);
    }


    public ArrayList<String> getCreditCardItemsArrayListForIngenico() {
        ArrayList<String> returnArrayRefNumbers = new ArrayList<String>();

        String selectedSalesCode = "";
        selectedSalesCode = MssqlDatabase.getResultSetValueToString(
                "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
//        selectedSalesCode = dbInit.dbExecuteReadReturnString(
//                "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        String salonSalesCardQuery = "";
        // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
        salonSalesCardQuery = "select idx, cardRefNumber " +
                " from salon_sales_card " +
                " where salesCode = '" + selectedSalesCode + "' " +
                " and not(cardCom = 'OFFLINECARD') ";
        ResultSet salonSalesCardCursor;
        salonSalesCardCursor = MssqlDatabase.getResultSetValue(salonSalesCardQuery);
        try {
            while (salonSalesCardCursor.next()) {
                String salonSalesCard_idx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,0), 1);
                String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,1), 1);

                returnArrayRefNumbers.add(salonSalesCard_cardRefNumber);
            }
            salonSalesCardCursor.close();
        } catch (Exception e){

        }

        //setCreditCardProcessVoid(arrayRefNumbers);
        // --------------------------------------------------------------------------------------------------------------------------------------

        return returnArrayRefNumbers;
    }

    public static ArrayList<String> getCreditCardItemsArrayListForPax(String paramSelectedSalonSalesDetailIdx) {
        ArrayList<String> cardPaymentInfoArrayList = new ArrayList<String>();

        String selectedSalesCode = "";
        selectedSalesCode = MssqlDatabase.getResultSetValueToString(
                "select salesCode from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' ");
        String salonSalesCardQuery = "";
        // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
        salonSalesCardQuery = "select idx, cardRefNumber, tid, priceAmount " +
                " from salon_sales_card " +
                " where salesCode = '" + selectedSalesCode + "' " +
                " and not(cardCom = 'OFFLINECARD') ";
        ResultSet salonSalesCardCursor;
        salonSalesCardCursor = MssqlDatabase.getResultSetValue(salonSalesCardQuery);
        try {
            while (salonSalesCardCursor.next()) {
                String salonSalesCard_idx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,0), 1);
                String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,1), 1);
                String salonSalesCard_tid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,2), 1);
                String salonSalesCard_priceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,3), 1);

                String cardPaymentInfo = salonSalesCard_idx + "|||" + salonSalesCard_cardRefNumber + "|||" + salonSalesCard_tid + "|||" + salonSalesCard_priceAmount;
                cardPaymentInfoArrayList.add(cardPaymentInfo);
            }
            salonSalesCardCursor.close();
        } catch (Exception e){

        }

        //setCreditCardProcessVoid(arrayRefNumbers);
        // --------------------------------------------------------------------------------------------------------------------------------------

        return cardPaymentInfoArrayList;
    }

    public static void setGroupVoid(String paramGroupVoidCount) {
        int tempGroupVoidCount = GlobalMemberValues.getIntAtString(paramGroupVoidCount);
        int cardPaymentInfoArrayListCount = mCardPaymentInfoArrayList.size();
        if (cardPaymentInfoArrayListCount > (tempGroupVoidCount + 1)) {
            mCardPaymentInfoArrayList = null;
            mCardPaymentInfoArrayList = getCreditCardItemsArrayListForPax(mSelectedSalonSalesDetailIdx);
            String tempCardPaymentInfo = mCardPaymentInfoArrayList.get(tempGroupVoidCount + 1);
            if (!GlobalMemberValues.isStrEmpty(tempCardPaymentInfo)) {
                String[] strCardPaymentInfoArr = tempCardPaymentInfo.split(GlobalMemberValues.STRSPLITTER2);
                String tempIdx = strCardPaymentInfoArr[0];
                String tempRefNum = strCardPaymentInfoArr[1];
                String tempTid = strCardPaymentInfoArr[2];
                String tempPriceAmount = strCardPaymentInfoArr[3];
                if (!GlobalMemberValues.isStrEmpty(tempTid)) {
                    openJJJPaxPayForGroupVoid(tempRefNum, tempIdx, tempPriceAmount, String.valueOf(tempGroupVoidCount + 1));
                }
            }
        } else {
            setVoidSalesDatabaseProcess();
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업(카드 API 연동)이 끝난후 처리해야할 부분을 넣음. ---------------
            GlobalMemberValues.logWrite("creditcardvoidprocess", "--- 시간이 걸리는 작업이 끝난뒤 처리할 메소드 시작 -------\n");
            setVoidSalesDatabaseProcess();
            GlobalMemberValues.logWrite("creditcardvoidprocess", "--- 시간이 걸리는 작업이 끝난뒤 처리할 메소드 완료 -------\n");
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 ----------------------------------------------------------
            if (proDial != null && proDial.isShowing()) {
                proDial.dismiss();
            }
            // -------------------------------------------------------------------------------------
        }
    };

    public static String setVoidSalesDatabaseProcess() {
        String returnResultCode = "";

        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";

        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();

        // 선택한 salonSalesDetail 의 idx 에 해당하는 salesCode 구하기
        String selectedSalesCode = "";
        GlobalMemberValues.logWrite("salehistorySqlQuery", "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        selectedSalesCode = MssqlDatabase.getResultSetValueToString(
                "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        if (GlobalMemberValues.isStrEmpty(selectedSalesCode)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to void", "Close");
        } else {
            String item_employeeIdx = "";
            String item_employeeName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                item_employeeIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                item_employeeName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }



            /** salon_sales_detail 테이블 관련처리 ******************************************************************************************************/
            // 입력, 수정, 삭제용 쿼리문자열 객체
            String strInsSqlQuery_item = "";
            String strUpdSqlQuery_item = "";
            String strDelSqlQuery_item = "";

            ResultSet salonSalesDetailCursor;
            String salonSalesDetailQuery = "select salesCode, holdCode, reservationCode, sidx, stcode, categoryCode, categoryName, " +
                    " itemIdx, itemName, itemFileName, itemFilePath, servicePositionNo, qty, salesOrgPrice, salesPrice,  " +
                    " salesPriceAmount, salesBalPrice, salesBalPriceAmount, tax, taxAmount, totalAmount, commissionRatioType, " +
                    " commissionRatio, commission, commissionAmount, pointRatio, point, pointAmount, customerId, customerName,  " +
                    " customerPhone, customerPosCode, employeeIdx, employeeName, giftcardNumberToSave, giftcardSavePriceToSave,  " +
                    " couponNumber, eachDiscountExtraPrice, eachDiscountExtraType, saveType, isQuickSale, isSale, isCloudUpload, status, idx, " +
                    " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
                    " from salon_sales_detail " +
                    " where salesCode = '" + selectedSalesCode + "' ";
            salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
            try {
                while (salonSalesDetailCursor.next()) {
                    /** Void 관련하여 원래 salon_sales_detail 테이블의 status 수정 *****************************************************/
                    String parentSalesIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,44), 1);
                    String parentSalesCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,0), 1);
                    strUpdSqlQuery_item = "update salon_sales_detail set status = 1 where salesCode = '" + parentSalesCode + "' ";
                    // salon_sales_detail 수정쿼리를 백터 strInsertQueryVec 에 담는다.
                    strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    /*******************************************************************************************************************/

                    /** Void 관련하여 salon_sales_detail 테이블 입력 *******************************************************************/
                    String salesCodeForVoid = "V" + parentSalesCode.substring(1);
                    // 클라우드 업로드 여부
                    int isCloudUpload = 0;
                    // 상태 (1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소)
                    int item_status = 1;
                    strInsSqlQuery_item = "insert into salon_sales_detail (" +
                            " salesCode, holdCode, reservationCode, sidx, stcode, " +
                            " categoryCode, categoryName, " +
                            " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
                            " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
                            " salesBalPrice, salesBalPriceAmount, " +
                            " tax, taxAmount, " +
                            " totalAmount, " +
                            " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                            " pointRatio, point, pointAmount, " +
                            " customerId, customerName, customerPhone, customerPosCode, " +
                            " employeeIdx, employeeName, " +
                            " serverIdx, serverName, " +
                            " giftcardNumberToSave, giftcardSavePriceToSave, " +
                            " couponNumber, " +
                            " eachDiscountExtraPrice, eachDiscountExtraType, " +
                            " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
                            " ) values ( " +

                            " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,4), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,5), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,9), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,10), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,11), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12), 0) + "', " +

                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,14) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,15) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,18) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,19) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,20) + "', ") +

                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,21), 0) + "', " +

                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,22) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,23) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,24) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,25) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,26) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,27) + "', ") +

                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,30), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,31), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,32), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,33), 0) + "', " +
                            //" '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                            //" '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,37), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,38), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,40), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,41), 0) + "', " +
                            " '" + isCloudUpload + "', " +
                            " '" + item_status + "', " +
                            " '" + parentSalesCode + "', " +
                            " '" + parentSalesIdx + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,45), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,46) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,47), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,48) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,49), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,50) + "' ") +

                            ")";
                    // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
                    strInsertQueryVec.addElement(strInsSqlQuery_item);
                    /*******************************************************************************************************************/

                    /** 구매한 상품(Product)이 있으면 void 처리한 수량만큼 재고(onhand)에 추가한다 ********************************************/
                    if (GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39) == "1" || GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39).equals("1")) {
                        strInsSqlQuery_item = " update salon_storeproduct set onhand = onhand + " + GlobalMemberValues.getIntAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12)) +
                                " where idx = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7) + "' ";
                        strInsertQueryVec.addElement(strInsSqlQuery_item);
                    }
                    /*******************************************************************************************************************/

                    /** 사용한 쿠폰이 있다면 다시 미사용으로 처리 **********************************************************************/
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36))) {
                        strUpdSqlQuery_item = "update coupon_issue_history set " +
                                " usedyn = 'N' " +
                                " where barcode = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36) + "' ";
                        strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    }
                    /*******************************************************************************************************************/

                    /** 적립한 기프트카드가 있으면 해당 기프트카드를 삭제한다 **********************************************************/
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34))) {
                        // salon_storegiftcard_number_history 에 기록된 것이 하나만 있을 경우 최초 발급이므로
                        // salon_storegiftcard_number 와 salon_storegiftcard_number_history 테이블에서 해당 기프트카드 정보 모두 삭제하고
                        int countGc = 0;
                        String countGcStr = MssqlDatabase.getResultSetValueToString(
                                "select count(*) from salon_storegiftcard_number_history where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ");
                        countGc = Integer.parseInt(countGcStr);
                        if (countGc > 1) {
                            // salon_storegiftcard_number 의 기프트카드 잔액 수정 (마이너스 할 것)
                            double minustGcMoney = 0.0;
                            minustGcMoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35));
                            strUpdSqlQuery_item = "update salon_storegiftcard_number set " +
                                    " remainingPrice = remainingPrice - " + minustGcMoney +
                                    " where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ";
                            strInsertQueryVec.addElement(strUpdSqlQuery_item);

                            // salon_storegiftcard_number_history 테이블 등록 (마이너스로 등록)
                            strInsSqlQuery_item = "insert into salon_storegiftcard_number_history (" +
                                    " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                                    " customerId, customerName, addUsePrice, addUseType, addUseMemo, codeforupload " +
                                    " ) values ( " +
                                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35), 0) + "', " +
                                    " '" + "U" + "', " +         // 기프트카드 구매이므로 적립인 'A' 로 저장 (사용시 'U')
                                    " '" + "VOID" + "', " +
                                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                                    ")";
                            strInsertQueryVec.addElement(strInsSqlQuery_item);
                        } else {
                            strDelSqlQuery_item = "delete from salon_storegiftcard_number where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ";
                            strInsertQueryVec.addElement(strDelSqlQuery_item);

                            // salon_storegiftcard_number_history 테이블 등록 (마이너스로 등록)
                            //strDelSqlQuery_item = "delete from salon_storegiftcard_number_history where gcNumber = '" + salonSalesDetailCursor.getString(34) + "' ";
                            strDelSqlQuery_item = "insert into salon_storegiftcard_number_history (" +
                                    " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                                    " customerId, customerName, addUsePrice, addUseType, addUseMemo, codeforupload " +
                                    " ) values ( " +
                                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35), 0) + "', " +
                                    " '" + "U" + "', " +         // 기프트카드 구매이므로 적립인 'A' 로 저장 (사용시 'U')
                                    " '" + "VOID" + "', " +
                                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                                    ")";
                            strInsertQueryVec.addElement(strDelSqlQuery_item);
                        }
                    }
                    /*******************************************************************************************************************/
                }
                salonSalesDetailCursor.close();
            }catch (Exception e){

            }

            // 01132023
            int voidKitchenPrintCnt = 0;

            // 보이드리턴관련
            // 키친프린팅 데이터 가져오기
            if (GlobalMemberValues.isVoidReturnKitchenPrinting()) {
                JSONObject tempKitchenJson = null;
                String tempJsonStr_kitchen = "";
                String tempKitchenQuery = "";
                tempKitchenQuery = " select jsonstr from salon_sales_kitchenprintingdata_json where salesCode = 'K" + selectedSalesCode.substring(1) + "' ";
                tempJsonStr_kitchen = MssqlDatabase.getResultSetValueToString(tempKitchenQuery);
                if (!GlobalMemberValues.isStrEmpty(tempJsonStr_kitchen)) {
                    try {
                        tempJsonStr_kitchen = GlobalMemberValues.getReplaceText(tempJsonStr_kitchen, "\"receiptno\":\"K", "\"receiptno\":\"V");
                        tempKitchenJson = new JSONObject(tempJsonStr_kitchen);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // 01132023 ------------------------------------------------------------------------------------------
                tempKitchenQuery = " select holdcode from salon_sales where salesCode = '" + selectedSalesCode + "' ";
                String tempHoldCode = MssqlDatabase.getResultSetValueToString(tempKitchenQuery);
                JSONObject tempJsonObject = null;
                try {
                    tempJsonObject = new JSONObject(tempJsonStr_kitchen);
                    tempJsonObject.put("holdcode", tempHoldCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // ---------------------------------------------------------------------------------------------------

                // 04222023
                String tempprintedyn = "N";
                if (GlobalMemberValues.isRepay3) {
                    tempprintedyn = "Y";
                }

                // 11102023
                if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(tempJsonObject.toString())) {
                    tempKitchenQuery = " insert into salon_sales_kitchenprintingdata_json " +
                            " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                            " 'V" + selectedSalesCode.substring(1) + "', " +
                            " '" + GlobalMemberValues.SALON_CODE + "', " +
                            "  " + GlobalMemberValues.STORE_INDEX + ", " +
                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempJsonObject.toString(), 0) + "', " +
                            " '" + tempprintedyn + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(tempKitchenQuery);
                }


                voidKitchenPrintCnt++;
            }

            /********************************************************************************************************************************************/

            /** salon_sales 테이블 관련처리 *************************************************************************************************************/
            // 입력, 수정, 삭제용 쿼리문자열 객체
            String strInsSqlQuery = "";
            String strUpdSqlQuery = "";

            // salon_sales 의 status 를 1 로 수정한다.
            strInsSqlQuery = "update salon_sales set status = 1 where salesCode = '" + selectedSalesCode + "' ";
            strInsertQueryVec.addElement(strInsSqlQuery);


            // Void 처리를 위한 변수 ---------------------------------------------------------------------
            String salesCodeForVoid = "V" + selectedSalesCode.substring(1);
            ResultSet salonSalesCardCursor;
            String salonSalesCardQuery = "";
            // ----------------------------------------------------------------------------------------------

            // 오프라인 카드 결제가 있으면 salon_sales_card 에서 void 처리한다. ---------------------------------------------------------------------
            salonSalesCardQuery = "select priceAmount from salon_sales_card " +
                    " where salesCode = '" + selectedSalesCode + "' " +
                    " and cardCom = 'OFFLINECARD' ";
            salonSalesCardCursor = MssqlDatabase.getResultSetValue(salonSalesCardQuery);
            int salonSalesCardCursor_count = 0;
            boolean salonSalesCardCursor_isBeforeFirst = false;
            try {
                salonSalesCardCursor.last();
                salonSalesCardCursor_count = salonSalesCardCursor.getRow();
                salonSalesCardCursor.beforeFirst();
                salonSalesCardCursor_isBeforeFirst = salonSalesCardCursor.isBeforeFirst();
            } catch (Exception e){

            }
            if (salonSalesCardCursor_count > 0 && salonSalesCardCursor_isBeforeFirst) {
                String salonSalesCard_priceAmountStr = "";
                double salonSalesCard_priceAmount = 0;
                try {
                    salonSalesCard_priceAmount = salonSalesCardCursor.getDouble(0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (salonSalesCard_priceAmount > 0) {
                    salonSalesCard_priceAmountStr = "-" + salonSalesCard_priceAmount;
                } else {
                    salonSalesCard_priceAmountStr = "0.00";
                }
                strInsSqlQuery = "insert into salon_sales_card (" +
                        " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status " +
                        " ) values ( " +
                        "'" + salesCodeForVoid + "', " +
                        "'" + "" + "', " +
                        "'" + GlobalMemberValues.STORE_INDEX + "', " +
                        "'" + GlobalMemberValues.STATION_CODE + "', " +
                        "'" + "OFFLINECARD" + "', " +
                        "'" + salonSalesCard_priceAmountStr + "', " +
                        "'" + "" + "', " +
                        "'" + "1" + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }

            //07052024 close resultset
            try {
                if(!salonSalesCardCursor.isClosed()){
                    salonSalesCardCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // --------------------------------------------------------------------------------------------------------------------------------------

            // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
            salonSalesCardQuery = "select priceAmount, cardRefNumber, cardCom, " +
                    " insertSwipeKeyin, cardLastFourDigitNumbers, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                    " from salon_sales_card " +
                    " where salesCode = '" + selectedSalesCode + "' " +
                    " and not(cardCom = 'OFFLINECARD') ";
            salonSalesCardCursor = MssqlDatabase.getResultSetValue(salonSalesCardQuery);
            try {
                while (salonSalesCardCursor.next()) {
                    String salonSalesCard_priceAmountStr = "";
                    double salonSalesCard_priceAmount = salonSalesCardCursor.getDouble(0);
                    String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,1), 1);
                    String salonSalesCard_cardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,2), 1);
                    String salonSalesCard_insertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,3), 1);
                    String salonSalesCard_cardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,4), 1);
                    String salonSalesCard_cardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,5), 1);
                    String salonSalesCard_cardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,6), 1);
                    String salonSalesCard_cardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,7), 1);

                    if (salonSalesCard_priceAmount > 0) {
                        salonSalesCard_priceAmountStr = "-" + salonSalesCard_priceAmount;
                    } else {
                        salonSalesCard_priceAmountStr = "0.00";
                    }

                    strInsSqlQuery = "insert into salon_sales_card (" +
                            " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                            " cardLastFourDigitNumbers, cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                            " ) values ( " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', " +
                            "'" + "" + "', " +
                            "'" + GlobalMemberValues.STORE_INDEX + "', " +
                            "'" + GlobalMemberValues.STATION_CODE + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardCom, 0) + "', " +
                            "'" + salonSalesCard_priceAmountStr + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_insertSwipeKeyin, 0) + "', " +
                            "'" + "1" + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardLastFourDigitNumbers, 0) + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardRefNumber, 0) + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvAid, 0) + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvTsi, 0) + "', " +
                            "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvTvr, 0) + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
                salonSalesCardCursor.close();
            }catch (Exception e){

            }

            // --------------------------------------------------------------------------------------------------------------------------------------

            // 사용한 기프트카드가 있으면 해당 기프트카드의 발란스 수정(재적립) 및 히스토리에 등록한다. -----------------------------------------------
            ResultSet salonSalesCursor;
            String salonSalesQuery = "select customerId, customerName, giftcardNumberUsed, giftcardPriceUsed from salon_sales where salesCode = '" + selectedSalesCode + "' ";
            salonSalesCursor = MssqlDatabase.getResultSetValue(salonSalesQuery);
            String temp_str_sales_giftcardNumberUsed = "";
            String str_giftCardRemaining = "";
            String temp_str_sales_giftcardPriceUsed = "";
            int salonSalesCursor_count = 0;
            boolean salonSalesCursor_isBeforeFirst = false;
            try {
                salonSalesCursor.last();
                salonSalesCursor_count = salonSalesCursor.getRow();
                salonSalesCursor.beforeFirst();
                salonSalesCursor_isBeforeFirst = salonSalesCursor.isBeforeFirst();
            } catch (Exception e){

            }
            if (salonSalesCursor_count > 0 && salonSalesCursor_isBeforeFirst) {
                String sales_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,0), 1);
                String sales_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,1), 1);
                String sales_giftcardNumberUsed = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,2), 1);
                temp_str_sales_giftcardNumberUsed = sales_giftcardNumberUsed;
                String sales_giftcardPriceUsed = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,3), 1);
                temp_str_sales_giftcardPriceUsed = sales_giftcardPriceUsed;

                // salon_storegiftcard_number 테이블 수정
                strUpdSqlQuery = "update salon_storegiftcard_number set " +
                        " remainingPrice = remainingPrice + " + GlobalMemberValues.getDoubleAtString(sales_giftcardPriceUsed) + " " +
                        " where gcNumber = '" + sales_giftcardNumberUsed + "' ";
                strInsertQueryVec.addElement(strUpdSqlQuery);

                // salon_storegiftcard_number_history 테이블 저장
                strInsSqlQuery = "insert into salon_storegiftcard_number_history (" +
                        " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                        " customerId, customerName, addUsePrice, addUseType, addUseMemo, salesCode, codeforupload " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                        " '" + "" + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +
                        " '" + "A" + "', " +         // 사용된 기프트카드의 void 처리로 재적립이므로 적립인 'A' 로 저장 (사용처리시 'U')
                        " '" + "VOID" + "', " +
                        " '" + selectedSalesCode + "', " +         // 세일즈코드
                        " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                        ")";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }

            //07052024 close resultset
            try {
                if(!salonSalesCursor.isClosed()){
                    salonSalesCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // --------------------------------------------------------------------------------------------------------------------
            /********************************************************************************************************************************************/

            /** 포인트(mileage) 관련 ********************************************************************************************************************/
            ResultSet salonSalesCursorForMileage;
            String salonSalesQueryForMileage = "select customerId, customerName, useTotalPointAmount, pointAmount from salon_sales where salesCode = '" + selectedSalesCode + "' ";
            salonSalesCursorForMileage = MssqlDatabase.getResultSetValue(salonSalesQueryForMileage);

            int salonSalesCursorForMileage_count = 0;
            boolean salonSalesCursorForMileage_isBeforeFirst = false;
            try {
                salonSalesCursorForMileage.last();
                salonSalesCursorForMileage_count = salonSalesCursorForMileage.getRow();
                salonSalesCursorForMileage.beforeFirst();
                salonSalesCursorForMileage_isBeforeFirst = salonSalesCursorForMileage.isBeforeFirst();
            } catch (Exception e){

            }

            if (salonSalesCursorForMileage_count > 0 && salonSalesCursorForMileage_isBeforeFirst) {
                double tempUsedPointAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursorForMileage,2));
                String tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursorForMileage,0), 1);
                double tempSavedPointAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursorForMileage,3));

                // 사용한 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 다시 해당금액만큼 더한다. -------------------------
                if (!GlobalMemberValues.isStrEmpty(tempCustomerId) && tempUsedPointAmount > 0) {
                    strUpdSqlQuery = "update salon_member set mileage = mileage + " + tempUsedPointAmount +
                            " where uid = '" + tempCustomerId + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery);

                    String temmpEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }
                    strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                            " ) values ( " +
                            " '" + "Void Restored by Sales - " + selectedSalesCode + "', " +
                            " '" + tempUsedPointAmount + "', " +
                            " '" + tempCustomerId + "', " +
                            " '" + "1" + "', " +            // 1: 적립        2 : 사용
                            " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                            " '" + temmpEmpName + "', " +
                            " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
                // --------------------------------------------------------------------------------------------------------------------

                // 적립된 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 차감한다. -----------------------------------------
                if (!GlobalMemberValues.isStrEmpty(tempCustomerId) && tempSavedPointAmount > 0) {
                    strUpdSqlQuery = "update salon_member set mileage = mileage - " + tempSavedPointAmount +
                            " where uid = '" + tempCustomerId + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery);

                    String temmpEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }
                    strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                            " ) values ( " +
                            " '" + "Void Minus by Sales - " + selectedSalesCode + "', " +
                            " '" + tempSavedPointAmount + "', " +
                            " '" + tempCustomerId + "', " +
                            " '" + "2" + "', " +            // 1: 적립        2 : 사용
                            " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                            " '" + temmpEmpName + "', " +
                            " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
                // --------------------------------------------------------------------------------------------------------------------
            }

            //07052024 close resultset
            try {
                if(!salonSalesCursorForMileage.isClosed()){
                    salonSalesCursorForMileage.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            // 클라우드 키친 프린팅일 경우 ----------------------------------------------------------------------------------------------
            // 클라우드 프린팅 일 경우에만 아래 사항이 저장되도록 했었음 (01132023 수정)
//            if (GlobalMemberValues.isCloudKitchenPrinter()) {
//            }

            String tempJson = MssqlDatabase.getResultSetValueToString(
                    "select jsonstr from salon_sales_kitchenprintingdata_json where salesCode = '" + selectedSalesCode + "' ");
            JSONObject mJson;
            String insJson = "";
            try {
                mJson = new JSONObject(tempJson);
                mJson.remove("receiptno");
                mJson.put("receiptno", salesCodeForVoid);

                insJson = mJson.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!GlobalMemberValues.isStrEmpty(insJson)) {
                int insIsCloudUpload = 0;
                strInsSqlQuery = "insert into salon_sales_kitchen_json (" +
                        " salesCode, scode, sidx, stcode, jsonstr, isCloudUpload " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insJson.toString(), 0) + "', " +
                        " '" + insIsCloudUpload + "' " +
                        ")";
                strInsertQueryVec.addElement(strInsSqlQuery);


                // GlobalMemberValues.isVoidReturnKitchenPrinting() 설정값이 Yes 로 되어 있어
                // 앞에서 이미 프린팅 처리를 했을 경우
                // 보이드 키친프린팅 관련 데이터를 저장하지 않고
                // 처리안했을 경우에만 저장한다.
                if (voidKitchenPrintCnt == 0) {
                    // 01132023 ------------------------------------------------------------------------------------------
                    strInsSqlQuery = " select holdcode from salon_sales where salesCode = '" + selectedSalesCode + "' ";
                    String tempHoldCode = MssqlDatabase.getResultSetValueToString(strInsSqlQuery);
                    JSONObject tempJsonObject = null;
                    try {
                        tempJsonObject = new JSONObject(insJson);
                        tempJsonObject.put("holdcode", tempHoldCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // ---------------------------------------------------------------------------------------------------

                    // 04222023
                    String tempprintedyn = "N";
                    if (GlobalMemberValues.isRepay3) {
                        tempprintedyn = "Y";
                    }

                    // 11102023
                    if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(tempJsonObject.toString())) {
                        strInsSqlQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                                "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(tempJsonObject.toString(), 0) + "', " +
                                " '" + tempprintedyn + "' " +
                                " ) ";
                        strInsertQueryVec.addElement(strInsSqlQuery);
                    }
                }

            }

            // --------------------------------------------------------------------------------------------------------------------

            // sqllite 트렌젝션.
            setVoidSalesDatabaseProcess_for_sqllite(selectedSalesCode);

            /********************************************************************************************************************************************/

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("StringVoidjjjjj", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
//            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "" ) {
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            } else { // 정상처리일 경우 Sale History 리스트 리로드

                GlobalMemberValues.RECEIPTNOFORUPLOAD = "";
                // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
                GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);

                // Void 처리 후 프린트 ------------------------------------------------------------------
                // Void 처리시 프린트 여부
//                String voidprint = dbInit.dbExecuteReadReturnString(
//                        " select voidprint from salon_storestationsettings_deviceprinter "
//                );
                String voidprint = MssqlDatabase.getResultSetValueToString(
                        " select voidprint from salon_storestationsettings_deviceprinter "
                );
                if (!GlobalMemberValues.isStrEmpty(voidprint)) {
                    voidprint = "0";
                }
                //GlobalMemberValues.logWrite("VoidReturnPrint", "voidprint : " + voidprint + "\n");
                if (voidprint.equals("0") || voidprint == "0") {

                    // 프린트 하는부분 giftCardBalanceInfoDownloadHandler 쪽으로 이동
//                    printReceipt(context, mSelectedSalesCode, "void");
                }
                // -------------------------------------------------------------------------------------

                setSearchSalesHistory();

                // 05.31.2022 --------------------------------------------------------------------------
                // repay 관련부분
                if (GlobalMemberValues.isRepay) {
                    GlobalMemberValues.saveTempSaleCartForRepay(context, mSelectedSalesCode);
                    mActivity.finish();
                    if (SaleHistoryList.mActivity != null) {
                        SaleHistoryList.mActivity.finish();
                    }
                    GlobalMemberValues.isRepay = false;
                }
                // --------------------------------------------------------------------------------------
            }
        }

        return returnResultCode;
    }

    View.OnTouchListener saleHistoryCustomerSearchEditTextTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };


    // 초기화 메소드
    private void setInit() {
        // 검색날짜를 현재일로 초기화
        //saleHistoryStartDateEditText.setText(GlobalMemberValues.STR_NOW_DATE);
        //saleHistoryEndDateEditText.setText(GlobalMemberValues.STR_NOW_DATE);
        if (!mSearchForCustomer) {
            saleHistoryCustomerSearchEditText.setText("");
            mCustomerValue = "";
        }

        mSelectedSalesCode = "";
        mSelectedSalonSalesDetailIdx = "";
        mSelectedSalonSalesDetailLn = null;

        mSelectedSalonSalesDetailStatus = "";

        GlobalMemberValues.mReReceiptprintYN = "N";
        GlobalMemberValues.mRekitchenprintYN = "N";

        mSchMemberId = "";
    }

    @Override
    protected void onResume() {
        super.onResume();

        setSearchSalesHistory();
    }


    public static void setVoidSalesDatabaseProcess_for_sqllite(String tempSalesCode) {
        // 세일일시를 지정했을 경우
        String sqlQuery_add1 = "";
        String sqlQuery_add2 = "";
        if (GlobalMemberValues.isDifferentSaleDateToday()) {
            String tempSearchDate = "";
            String[] tempSettingSaleDateArr = GlobalMemberValues.SETTING_SALE_DATE.split("-");
            if (tempSettingSaleDateArr.length > 0) {
                tempSearchDate = tempSettingSaleDateArr[2] + "-" + tempSettingSaleDateArr[0] + "-" + tempSettingSaleDateArr[1];

                sqlQuery_add1 = ", saleDate ";
                sqlQuery_add2 = ", DATETIME('" + tempSearchDate + " 23:59:59') ";
            }
        }

        String strGetSqlQuery = "";
        strGetSqlQuery = "select salesCode, holdCode, reservationCode, sidx, stcode, " +
                " qty, " +
                " salesBalPriceAmount, taxAmount, totalAmount, " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " commissionAmount, pointAmount, " +
                " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo, " +
                " giftcardNumberUsed, giftcardPriceUsed, " +
                " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                " cardTidNumber, " +
                " isCloudUpload, " +
                " status, " +
                " changeMoney, " +
                " employeeIdx, employeeName, " +
                " deliverypickupfee, " +
                " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                " tablename, tablepeoplecnt, salepg_ip, " +

                // 07202024
                " pgdevicenum " +

                " from salon_sales where salesCode = '" + tempSalesCode + "' " ;

        ResultSet salonSalesCursor_temp = MssqlDatabase.getResultSetValue(strGetSqlQuery);

        try {
            while (salonSalesCursor_temp.next()){
                String strInsSqlQuery = "";
                strInsSqlQuery = "insert into salon_sales (" +
                        " salesCode, holdCode, reservationCode, sidx, stcode, " +
                        " qty, " +
                        " salesBalPriceAmount, taxAmount, totalAmount, " +
                        " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                        " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                        " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                        " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                        " commissionAmount, pointAmount, " +
                        " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                        " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                        " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                        " customermemo, " +
                        " giftcardNumberUsed, giftcardPriceUsed, " +
                        " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                        " cardTidNumber, " +
                        " isCloudUpload, " +
                        " status, " +
                        " changeMoney, " +
                        " employeeIdx, employeeName, " +
                        " deliverypickupfee, " +
                        " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                        " tablename, tablepeoplecnt, salepg_ip, " +

                        // 07202024
                        " pgdevicenum, " +

                        " cancelreason " + sqlQuery_add1 +
                        " ) values ( " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,0), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,1), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,2), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,3), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,4), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,5), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,6), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,7), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,8), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,9), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,10), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,11), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,12), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,13), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,14), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,15), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,16), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,17), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,18), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,19), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,20), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,21), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,22), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,23), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,24), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,25), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,26), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,27), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,28), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,29), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,30), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,31), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,32), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,33), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,34), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,35), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,36), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,37), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,38), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,39), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,40), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,41), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,42), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,43), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,44), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,45), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,46), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,47), 0) + "', " +

                        " '" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor_temp,48), "2") + "', " +
                        " '" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor_temp,49), "2") + "', " +
                        " '" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor_temp,50), "2") + "', " +
                        " '" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor_temp,51), "2") + "', " +
                        " '" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor_temp,52), "2") + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,53), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,54), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,55), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,56), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,57), 0) + "', " +

                        " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                        " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,60), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,61), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,62), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,63), 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,64), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(TableSaleMain.mTableName, 0) + "', " +
                        " '" + TableSaleMain.mTablePeopleCnt + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,67), 0) + "', " +

                        // 07202024
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor_temp,68), 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(mCancelReason, 0) + "' " +

                        sqlQuery_add2 +
                        ")";
                dbInit.dbExecuteWriteReturnValueOnlySqlite(strInsSqlQuery);
            }
            salonSalesCursor_temp.close();
        } catch (Exception e){
        }

        mCancelReason = "";

        String returnResultCode = "";

        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";

        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();

        // 선택한 salonSalesDetail 의 idx 에 해당하는 salesCode 구하기
        String selectedSalesCode = "";
        GlobalMemberValues.logWrite("salehistorySqlQuery", "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        selectedSalesCode = MssqlDatabase.getResultSetValueToString(
                "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        if (GlobalMemberValues.isStrEmpty(selectedSalesCode)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to void", "Close");
        } else {
            String item_employeeIdx = "";
            String item_employeeName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                item_employeeIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                item_employeeName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }

            /** salon_sales_detail 테이블 관련처리 ******************************************************************************************************/
            // 입력, 수정, 삭제용 쿼리문자열 객체
            String strInsSqlQuery_item = "";
            String strUpdSqlQuery_item = "";
            String strDelSqlQuery_item = "";

            ResultSet salonSalesDetailCursor;
            String salonSalesDetailQuery = "select salesCode, holdCode, reservationCode, sidx, stcode, categoryCode, categoryName, " +
                    " itemIdx, itemName, itemFileName, itemFilePath, servicePositionNo, qty, salesOrgPrice, salesPrice,  " +
                    " salesPriceAmount, salesBalPrice, salesBalPriceAmount, tax, taxAmount, totalAmount, commissionRatioType, " +
                    " commissionRatio, commission, commissionAmount, pointRatio, point, pointAmount, customerId, customerName,  " +
                    " customerPhone, customerPosCode, employeeIdx, employeeName, giftcardNumberToSave, giftcardSavePriceToSave,  " +
                    " couponNumber, eachDiscountExtraPrice, eachDiscountExtraType, saveType, isQuickSale, isSale, isCloudUpload, status, idx, " +
                    " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2 " +
                    " from salon_sales_detail " +
                    " where salesCode = '" + selectedSalesCode + "' ";
            salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
            try {
                while (salonSalesDetailCursor.next()) {
                    /** Void 관련하여 원래 salon_sales_detail 테이블의 status 수정 *****************************************************/
                    String parentSalesIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,44), 1);
                    String parentSalesCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,0), 1);
                    strUpdSqlQuery_item = "update salon_sales_detail set status = 1 where salesCode = '" + parentSalesCode + "' ";
                    // salon_sales_detail 수정쿼리를 백터 strInsertQueryVec 에 담는다.
                    strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    /*******************************************************************************************************************/

                    /** Void 관련하여 salon_sales_detail 테이블 입력 *******************************************************************/
                    String salesCodeForVoid = "V" + parentSalesCode.substring(1);
                    // 클라우드 업로드 여부
                    int isCloudUpload = 0;
                    // 상태 (1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소)
                    int item_status = 1;
                    strInsSqlQuery_item = "insert into salon_sales_detail (" +
                            " salesCode, holdCode, reservationCode, sidx, stcode, " +
                            " categoryCode, categoryName, " +
                            " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
                            " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
                            " salesBalPrice, salesBalPriceAmount, " +
                            " tax, taxAmount, " +
                            " totalAmount, " +
                            " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                            " pointRatio, point, pointAmount, " +
                            " customerId, customerName, customerPhone, customerPosCode, " +
                            " employeeIdx, employeeName, " +
                            " giftcardNumberToSave, giftcardSavePriceToSave, " +
                            " couponNumber, " +
                            " eachDiscountExtraPrice, eachDiscountExtraType, " +
                            " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                            " frommssql " +
                            " ) values ( " +

                            " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,4), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,5), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,9), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,10), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,11), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,14) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,15) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,18) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,19) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,20) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,21), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,22) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,23) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,24) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,25) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,26) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,27) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,30), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,31), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,32), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,33), 0) + "', " +
                            //" '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                            //" '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,37), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,38), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,40), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,41), 0) + "', " +
                            " '" + isCloudUpload + "', " +
                            " '" + item_status + "', " +
                            " '" + parentSalesCode + "', " +
                            " '" + parentSalesIdx + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,45), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,46) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,47), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,48) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,49), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,50) + "', ") +
                            " 'Y' " +

                            ")";
                    // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
                    strInsertQueryVec.addElement(strInsSqlQuery_item);
                    /*******************************************************************************************************************/

                    /** 구매한 상품(Product)이 있으면 void 처리한 수량만큼 재고(onhand)에 추가한다 ********************************************/
                    if (GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39) == "1" || GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39).equals("1")) {
                        strInsSqlQuery_item = " update salon_storeproduct set onhand = onhand + " + GlobalMemberValues.getIntAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12)) +
                                " where idx = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7) + "' ";
                        strInsertQueryVec.addElement(strInsSqlQuery_item);
                    }
                    /*******************************************************************************************************************/

                    /** 사용한 쿠폰이 있다면 다시 미사용으로 처리 **********************************************************************/
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36))) {
                        strUpdSqlQuery_item = "update coupon_issue_history set " +
                                " usedyn = 'N' " +
                                " where barcode = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36) + "' ";
                        strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    }
                    /*******************************************************************************************************************/

                    /** 적립한 기프트카드가 있으면 해당 기프트카드를 삭제한다 **********************************************************/
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34))) {
                        // salon_storegiftcard_number_history 에 기록된 것이 하나만 있을 경우 최초 발급이므로
                        // salon_storegiftcard_number 와 salon_storegiftcard_number_history 테이블에서 해당 기프트카드 정보 모두 삭제하고
                        int countGc = 0;
                        String countGcStr = dbInit.dbExecuteReadReturnString(
                                "select count(*) from salon_storegiftcard_number_history where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ");
                        countGc = Integer.parseInt(countGcStr);
                        if (countGc > 1) {
                            // salon_storegiftcard_number 의 기프트카드 잔액 수정 (마이너스 할 것)
                            double minustGcMoney = 0.0;
                            minustGcMoney = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35));
                            strUpdSqlQuery_item = "update salon_storegiftcard_number set " +
                                    " remainingPrice = remainingPrice - " + minustGcMoney +
                                    " where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ";
                            strInsertQueryVec.addElement(strUpdSqlQuery_item);

                            // salon_storegiftcard_number_history 테이블 등록 (마이너스로 등록)
                            strInsSqlQuery_item = "insert into salon_storegiftcard_number_history (" +
                                    " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                                    " customerId, customerName, addUsePrice, addUseType, addUseMemo, codeforupload " +
                                    " ) values ( " +
                                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35), 0) + "', " +
                                    " '" + "U" + "', " +         // 기프트카드 구매이므로 적립인 'A' 로 저장 (사용시 'U')
                                    " '" + "VOID" + "', " +
                                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                                    ")";
                            strInsertQueryVec.addElement(strInsSqlQuery_item);
                        } else {
                            strDelSqlQuery_item = "delete from salon_storegiftcard_number where gcNumber = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34) + "' ";
                            strInsertQueryVec.addElement(strDelSqlQuery_item);

                            // salon_storegiftcard_number_history 테이블 등록 (마이너스로 등록)
                            //strDelSqlQuery_item = "delete from salon_storegiftcard_number_history where gcNumber = '" + salonSalesDetailCursor.getString(34) + "' ";
                            strDelSqlQuery_item = "insert into salon_storegiftcard_number_history (" +
                                    " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                                    " customerId, customerName, addUsePrice, addUseType, addUseMemo, codeforupload " +
                                    " ) values ( " +
                                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35), 0) + "', " +
                                    " '" + "U" + "', " +         // 기프트카드 구매이므로 적립인 'A' 로 저장 (사용시 'U')
                                    " '" + "VOID" + "', " +
                                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                                    ")";
                            strInsertQueryVec.addElement(strDelSqlQuery_item);
                        }
                    }



                    /*******************************************************************************************************************/
                }
                salonSalesDetailCursor.close();
            } catch (Exception e){

            }

            /********************************************************************************************************************************************/

            /** salon_sales 테이블 관련처리 *************************************************************************************************************/
            // 입력, 수정, 삭제용 쿼리문자열 객체
            String strInsSqlQuery = "";
            String strUpdSqlQuery = "";

            // salon_sales 의 status 를 1 로 수정한다.
            strInsSqlQuery = "update salon_sales set status = 1 where salesCode = '" + selectedSalesCode + "' ";
            strInsertQueryVec.addElement(strInsSqlQuery);


            // Void 처리를 위한 변수 ---------------------------------------------------------------------
            String salesCodeForVoid = "V" + selectedSalesCode.substring(1);
            Cursor salonSalesCardCursor;
            String salonSalesCardQuery = "";
            // ----------------------------------------------------------------------------------------------

            // 오프라인 카드 결제가 있으면 salon_sales_card 에서 void 처리한다. ---------------------------------------------------------------------
            salonSalesCardQuery = "select priceAmount from salon_sales_card " +
                    " where salesCode = '" + selectedSalesCode + "' " +
                    " and cardCom = 'OFFLINECARD' ";
            salonSalesCardCursor = dbInit.dbExecuteRead(salonSalesCardQuery);
            if (salonSalesCardCursor.getCount() > 0 && salonSalesCardCursor.moveToFirst()) {
                String salonSalesCard_priceAmountStr = "";
                double salonSalesCard_priceAmount = salonSalesCardCursor.getDouble(0);
                if (salonSalesCard_priceAmount > 0) {
                    salonSalesCard_priceAmountStr = "-" + salonSalesCard_priceAmount;
                } else {
                    salonSalesCard_priceAmountStr = "0.00";
                }
                strInsSqlQuery = "insert into salon_sales_card (" +
                        " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status " +
                        " ) values ( " +
                        "'" + salesCodeForVoid + "', " +
                        "'" + "" + "', " +
                        "'" + GlobalMemberValues.STORE_INDEX + "', " +
                        "'" + GlobalMemberValues.STATION_CODE + "', " +
                        "'" + "OFFLINECARD" + "', " +
                        "'" + salonSalesCard_priceAmountStr + "', " +
                        "'" + "" + "', " +
                        "'" + "1" + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
            // --------------------------------------------------------------------------------------------------------------------------------------

            // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
            salonSalesCardQuery = "select priceAmount, cardRefNumber, cardCom, " +
                    " insertSwipeKeyin, cardLastFourDigitNumbers, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                    " from salon_sales_card " +
                    " where salesCode = '" + selectedSalesCode + "' " +
                    " and not(cardCom = 'OFFLINECARD') ";
            salonSalesCardCursor = dbInit.dbExecuteRead(salonSalesCardQuery);
            while (salonSalesCardCursor.moveToNext()) {
                String salonSalesCard_priceAmountStr = "";
                double salonSalesCard_priceAmount = salonSalesCardCursor.getDouble(0);
                String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);
                String salonSalesCard_cardCom = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(2), 1);
                String salonSalesCard_insertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(3), 1);
                String salonSalesCard_cardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(4), 1);
                String salonSalesCard_cardEmvAid = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(5), 1);
                String salonSalesCard_cardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(6), 1);
                String salonSalesCard_cardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(7), 1);

                if (salonSalesCard_priceAmount > 0) {
                    salonSalesCard_priceAmountStr = "-" + salonSalesCard_priceAmount;
                } else {
                    salonSalesCard_priceAmountStr = "0.00";
                }

                strInsSqlQuery = "insert into salon_sales_card (" +
                        " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                        " cardLastFourDigitNumbers, cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr " +
                        " ) values ( " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid, 0) + "', " +
                        "'" + "" + "', " +
                        "'" + GlobalMemberValues.STORE_INDEX + "', " +
                        "'" + GlobalMemberValues.STATION_CODE + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardCom, 0) + "', " +
                        "'" + salonSalesCard_priceAmountStr + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_insertSwipeKeyin, 0) + "', " +
                        "'" + "1" + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardLastFourDigitNumbers, 0) + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardRefNumber, 0) + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvAid, 0) + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvTsi, 0) + "', " +
                        "'" + GlobalMemberValues.getDBTextAfterChecked(salonSalesCard_cardEmvTvr, 0) + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
            salonSalesCardCursor.close();
            // --------------------------------------------------------------------------------------------------------------------------------------

            // 사용한 기프트카드가 있으면 해당 기프트카드의 발란스 수정(재적립) 및 히스토리에 등록한다. -----------------------------------------------
            Cursor salonSalesCursor;
            String salonSalesQuery = "select customerId, customerName, giftcardNumberUsed, giftcardPriceUsed from salon_sales where salesCode = '" + selectedSalesCode + "' ";
            salonSalesCursor = dbInit.dbExecuteRead(salonSalesQuery);
            String temp_str_sales_giftcardNumberUsed = "";
            String str_giftCardRemaining = "";
            String temp_str_sales_giftcardPriceUsed = "";
            if (salonSalesCursor.getCount() > 0 && salonSalesCursor.moveToFirst()) {
                String sales_customerId = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(0), 1);
                String sales_customerName = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(1), 1);
                String sales_giftcardNumberUsed = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(2), 1);
                String sales_giftcardPriceUsed = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(3), 1);
                temp_str_sales_giftcardNumberUsed = sales_giftcardNumberUsed;
                temp_str_sales_giftcardPriceUsed = sales_giftcardPriceUsed;

                // salon_storegiftcard_number 테이블 수정
                strUpdSqlQuery = "update salon_storegiftcard_number set " +
                        " remainingPrice = remainingPrice + " + GlobalMemberValues.getDoubleAtString(sales_giftcardPriceUsed) + " " +
                        " where gcNumber = '" + sales_giftcardNumberUsed + "' ";
                strInsertQueryVec.addElement(strUpdSqlQuery);

                // salon_storegiftcard_number_history 테이블 저장
                strInsSqlQuery = "insert into salon_storegiftcard_number_history (" +
                        " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                        " customerId, customerName, addUsePrice, addUseType, addUseMemo, salesCode, codeforupload " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                        " '" + "" + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +
                        " '" + "A" + "', " +         // 사용된 기프트카드의 void 처리로 재적립이므로 적립인 'A' 로 저장 (사용처리시 'U')
                        " '" + "VOID" + "', " +
                        " '" + selectedSalesCode + "', " +         // 세일즈코드
                        " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                        ")";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
            // --------------------------------------------------------------------------------------------------------------------
            /********************************************************************************************************************************************/

            /** 포인트(mileage) 관련 ********************************************************************************************************************/
            Cursor salonSalesCursorForMileage;
            String salonSalesQueryForMileage = "select customerId, customerName, useTotalPointAmount, pointAmount from salon_sales where salesCode = '" + selectedSalesCode + "' ";
            salonSalesCursorForMileage = dbInit.dbExecuteRead(salonSalesQueryForMileage);
            if (salonSalesCursorForMileage.getCount() > 0 && salonSalesCursorForMileage.moveToFirst()) {
                double tempUsedPointAmount = GlobalMemberValues.getDoubleAtString(salonSalesCursorForMileage.getString(2));
                String tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursorForMileage.getString(0), 1);
                double tempSavedPointAmount = GlobalMemberValues.getDoubleAtString(salonSalesCursorForMileage.getString(3));

                // 사용한 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 다시 해당금액만큼 더한다. -------------------------
                if (!GlobalMemberValues.isStrEmpty(tempCustomerId) && tempUsedPointAmount > 0) {
                    strUpdSqlQuery = "update salon_member set mileage = mileage + " + tempUsedPointAmount +
                            " where uid = '" + tempCustomerId + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery);

                    String temmpEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }
                    strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                            " ) values ( " +
                            " '" + "Void Restored by Sales - " + selectedSalesCode + "', " +
                            " '" + tempUsedPointAmount + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempCustomerId, 0) + "', " +
                            " '" + "1" + "', " +            // 1: 적립        2 : 사용
                            " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                            " '" + GlobalMemberValues.getDBTextAfterChecked(temmpEmpName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.getCodeForUpload("mileage"), 0) + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
                // --------------------------------------------------------------------------------------------------------------------

                // 적립된 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 차감한다. -----------------------------------------
                if (!GlobalMemberValues.isStrEmpty(tempCustomerId) && tempSavedPointAmount > 0) {
                    strUpdSqlQuery = "update salon_member set mileage = mileage - " + tempSavedPointAmount +
                            " where uid = '" + tempCustomerId + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery);

                    String temmpEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }
                    strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                            " ) values ( " +
                            " '" + "Void Minus by Sales - " + selectedSalesCode + "', " +
                            " '" + tempSavedPointAmount + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempCustomerId, 0) + "', " +
                            " '" + "2" + "', " +            // 1: 적립        2 : 사용
                            " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                            " '" + GlobalMemberValues.getDBTextAfterChecked(temmpEmpName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.getCodeForUpload("mileage"), 0) + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
                // --------------------------------------------------------------------------------------------------------------------

                // 클라우드 키친 프린팅일 경우 ----------------------------------------------------------------------------------------------
                // 클라우드 프린팅 일 경우에만 아래 사항이 저장되도록 했었음 (01132023 수정)
//                if (GlobalMemberValues.isCloudKitchenPrinter()) {
//                }

                String tempJson = MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select jsonstr from salon_sales_kitchenprintingdata_json where salesCode = '" + selectedSalesCode + "' ");
                JSONObject mJson;
                String insJson = "";
                try {
                    mJson = new JSONObject(tempJson);
                    mJson.remove("receiptno");
                    mJson.put("receiptno", salesCodeForVoid);

                    insJson = mJson.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!GlobalMemberValues.isStrEmpty(insJson)) {
                    int insIsCloudUpload = 0;
                    strInsSqlQuery = "insert into salon_sales_kitchen_json (" +
                            " salesCode, scode, sidx, stcode, jsonstr, isCloudUpload " +
                            " ) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(insJson.toString(), 0) + "', " +
                            " '" + insIsCloudUpload + "' " +
                            ")";
                    strInsertQueryVec.addElement(strInsSqlQuery);



                    // 01132023 ------------------------------------------------------------------------------------------
                    strInsSqlQuery = " select holdcode from salon_sales where salesCode = '" + selectedSalesCode + "' ";
                    String tempHoldCode = MssqlDatabase.getResultSetValueToString(strInsSqlQuery);
                    JSONObject tempJsonObject = null;
                    try {
                        tempJsonObject = new JSONObject(insJson);
                        tempJsonObject.put("holdcode", tempHoldCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // ---------------------------------------------------------------------------------------------------

                    // 04222023
                    String tempprintedyn = "N";
                    if (GlobalMemberValues.isRepay3) {
                        tempprintedyn = "Y";
                    }

                    // 11102023
                    if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(insJson.toString())) {
                        strInsSqlQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForVoid,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                                "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(insJson.toString(), 0) + "', " +
                                " '" + tempprintedyn + "' " +
                                " ) ";
                        strInsertQueryVec.addElement(strInsSqlQuery);
                    }

                }
                // --------------------------------------------------------------------------------------------------------------------
            }

            /********************************************************************************************************************************************/

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryStringVoid", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            } else { // 정상처리일 경우 Sale History 리스트 리로드
                // 클라우드 키친 프린팅일 경우 ----------------------------------------------------------------------------------------------
                if (GlobalMemberValues.isCloudKitchenPrinter()) {
                    // 주방프린터 데이터 클라우드 전송
                    GlobalMemberValues.sendKitchenPrintingDataToCloud(MainActivity.mContext, mActivity);
                }
                // ---------------------------------------------------------------------------------------------------------------------

//                GlobalMemberValues.RECEIPTNOFORUPLOAD = "";
//                // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
//                GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
//
//                // Void 처리 후 프린트 ------------------------------------------------------------------
//                // Void 처리시 프린트 여부
//                String voidprint = dbInit.dbExecuteReadReturnString(
//                        " select voidprint from salon_storestationsettings_deviceprinter "
//                );
//                if (!GlobalMemberValues.isStrEmpty(voidprint)) {
//                    voidprint = "0";
//                }
//                //GlobalMemberValues.logWrite("VoidReturnPrint", "voidprint : " + voidprint + "\n");
//                if (voidprint.equals("0") || voidprint == "0") {
//                    printReceipt(context, mSelectedSalesCode, "void");
//                }
//                // -------------------------------------------------------------------------------------
//
//                setSearchSalesHistory();
                // Gift Card
                String getUsedGiftCardNum = "";
                String strselSqlQuery_giftcardNum = "select giftcardNumberUsed from salon_sales where salesCode = '" + selectedSalesCode + "'";
                getUsedGiftCardNum = MssqlDatabase.getResultSetValueToString(strselSqlQuery_giftcardNum);
                GlobalMemberValues.GIFTCARD_USE_NUMBER = getUsedGiftCardNum;

                // Point
                String getUsedpointName = "";
                String strselSqlQuery_point = "select customerId from salon_sales where salesCode = '" + selectedSalesCode + "'";
                getUsedpointName = MssqlDatabase.getResultSetValueToString(strselSqlQuery_point);

                String strselSqlQuery_point_earned = "select pointAmount from salon_sales where salesCode = '" + selectedSalesCode + "'";
                String getUsedpoint_earned = MssqlDatabase.getResultSetValueToString(strselSqlQuery_point_earned);
                if (!getUsedpoint_earned.isEmpty() || !getUsedpoint_earned.equals("0.0")) {
                    GlobalMemberValues.POINT_EARNED = GlobalMemberValues.getDoubleAtString(getUsedpoint_earned);
                }

                if (!getUsedGiftCardNum.isEmpty()){

                    dialog = new JJJCustomAnimationDialog(mActivity);
                    dialog.show();
                    final String finalTempGcNumber = getUsedGiftCardNum;
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                if (!GlobalMemberValues.isOnline().equals("00")) {
                                    GlobalMemberValues.showDialogNoInternet(context);
                                } else {
                                    API_download_giftcardbalance apiGiftCardBalanceInfoDownload = new API_download_giftcardbalance(finalTempGcNumber);
                                    apiGiftCardBalanceInfoDownload.execute(null, null, null);
                                    try {
                                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                        if (apiGiftCardBalanceInfoDownload.mFlag) {
                                            mGetGiftCardBalanceInfo = apiGiftCardBalanceInfoDownload.mReturnValue;
                                        }
                                    } catch (InterruptedException e) {
                                        mGetGiftCardBalanceInfo = "";
                                    }
                                }
                            }

                            // ---------------------------------------------------------------------------------
                            // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                            giftCardBalanceInfoDownloadHandler.sendEmptyMessage(0);
                            // ---------------------------------------------------------------------------------
                        }
                    });
                    thread.start();

                } else if (!getUsedpointName.isEmpty() || !getUsedpointName.equals("Walk In")){
                    String finalGetUsedpointName = getUsedpointName;
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                if (!GlobalMemberValues.isOnline().equals("00")) {
                                    GlobalMemberValues.showDialogNoInternet(context);
                                    mGetMemberMileage = "";
                                } else {
                                    API_download_membermileage apiMemMileageDownload = new API_download_membermileage(finalGetUsedpointName);
                                    apiMemMileageDownload.execute(null, null, null);
                                    try {
                                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                        if (apiMemMileageDownload.mFlag) {
                                            mGetMemberMileage = apiMemMileageDownload.mReturnValue;
                                        }
                                    } catch (InterruptedException e) {
                                        mGetMemberMileage = "";
                                    }
                                }
                            }
                            // ---------------------------------------------------------------------------------
                            // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                            memMileageDownloadHandler.sendEmptyMessage(0);
                            // ---------------------------------------------------------------------------------
                        }
                    });
                    thread.start();
                }else {
                    printReceipt(context, mSelectedSalesCode, "void");
                }

            }
        }

//        return returnResultCode;
    }
    private static Handler giftCardBalanceInfoDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetGiftCardBalanceInfo)) {
                // -------------------------------------------------------------------------------------
                String[] arrGiftCardBalanceInfo = mGetGiftCardBalanceInfo.split(GlobalMemberValues.STRSPLITTER2);
                int tempGiftCardBalanceCnt = 0;
                if (arrGiftCardBalanceInfo.length > 0) {
                    tempGiftCardBalanceCnt = GlobalMemberValues.getIntAtString(arrGiftCardBalanceInfo[0]);
                }
                String tempGiftCardBalance = "";
                if (arrGiftCardBalanceInfo.length > 1) {
                    tempGiftCardBalance = arrGiftCardBalanceInfo[1];
                }
                if (tempGiftCardBalanceCnt == 0) {
                    tempGiftCardBalance = "";
                }

//                tempGiftCardBalance;
                Log.e("tempGiftCardBalance : ", tempGiftCardBalance);

//                setGiftCardBalance(tempGiftCardBalance);
                GlobalMemberValues.GIFTCARD_AFTER_BALANCE = GlobalMemberValues.getDoubleAtString(tempGiftCardBalance);

                printReceipt(context, mSelectedSalesCode, "void");

                // 프로그래스바를 사라지게 함 -------------------------------------------------------
                if (dialog != null || dialog.isShowing()) {
                    dialog.dismiss();
                }
                // -------------------------------------------------------------------------------------
            }
        }
    };

    private static Handler memMileageDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetMemberMileage)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                GlobalMemberValues.POINT_AFTER_BALANCE = GlobalMemberValues.getDoubleAtString(mGetMemberMileage);
                printReceipt(context, mSelectedSalesCode, "void");

            } else {
                printReceipt(context, mSelectedSalesCode, "void");
            }
        }
    };

    public static boolean getSalecodeItemReturnAllYN(String paramSalesCode){
        String str_sale_item_count = MssqlDatabase.getResultSetValueToString(
                "select count(*) from salon_sales_detail where salescode = '" + "K" + paramSalesCode.substring(1) + "' ");
        String str_return_item_count = MssqlDatabase.getResultSetValueToString(
                "select count(*) from salon_sales_detail where salescode = '" + "C" + paramSalesCode.substring(1) + "' and frommssql = 'Y' ");
        int i_itemcount = Integer.parseInt(str_sale_item_count) - Integer.parseInt(str_return_item_count);
        if (i_itemcount == 0){
            // 모두 리턴됨.
            return true;
        } else {
            return false;
        }
    }


    public static boolean getSalecodeItemReturnYN(String paramSalesCode){
        String str_return_item_count = dbInit.dbExecuteReadReturnString(
                "select count(*) from salon_sales_detail where salescode = '" + "C" + paramSalesCode.substring(1) + "' ");
        int i_itemcount = Integer.parseInt(str_return_item_count);
        if (i_itemcount >= 1){
            // 리턴 아이템이 있음.
            return true;
        } else {
            return false;
        }
    }

    public static boolean getSalecodeItemVoidYN(String paramSalesCode){
        String str_return_item_count = dbInit.dbExecuteReadReturnString(
                "select count(*) from salon_sales_detail where salescode = '" + "V" + paramSalesCode.substring(1) + "' ");
        int i_itemcount = Integer.parseInt(str_return_item_count);
        if (i_itemcount >= 1){
            // VOID 아이템이 있음.
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SALEHISTORY){

            startVoidProcess();

        }
    }

    public void startVoidProcess() {
        if (!GlobalMemberValues.checkEmployeePermission(
                GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<1>")) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");

            Intent intent = new Intent(context.getApplicationContext(), EmployeeKeyIn.class);
            intent.putExtra("main_side","H");
            intent.putExtra("table_idx_arr_list", "");
            mActivity.startActivityForResult(intent, REQ_CODE_SALEHISTORY);
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);

            return;
        }

        if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose sale to return", "Close");
            return;
        }

        // 배치된 주문인지 확인
        int tempBatchSaleCount = 0;
        tempBatchSaleCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                "select count(idx) from salon_sales where salesCode = '" + mSelectedSalesCode + "' " +
                        " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
        ));
        if (tempBatchSaleCount > 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "Batched Order can not be void", "Close");
            return;
        }

        if (getSalecodeItemReturnYN(mSelectedSalesCode) || getSalecodeItemVoidYN(mSelectedSalesCode)){
            GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
            return;
        }

        if (getSalecodeItemReturnYN(mSelectedSalesCode)){
            GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
            return;
        }

        if (getSalecodeItemVoidYN(mSelectedSalesCode)){
            GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
            return;
        }

        if (GlobalMemberValues.isRepay) {
            // 05.31.2022 ----------------------------------
            if (GlobalMemberValues.isRepay) {
                mCancelReason = "Repay";
            }
            // ---------------------------------------------
            setCreditCardProcessVoid();
        } else {
            // 06.04.2022 -------------------------------------------------------------------------------------------
            // 결제 수단이 몇개인지 확인
            // 2개일 경우 setNeutralButton 버튼 추가
            ArrayList<String> stringArrayList = GlobalMemberValues.getPaidListBySaleCode(mSelectedSalesCode);
            if (stringArrayList.size() > 1) {
                if ((mActivity != null) && (!mActivity.isFinishing())) {
                    new AlertDialog.Builder(context)
                            .setTitle("VOID")
                            .setMessage("Are you sure you want to void?\n[Sale Group# : " + mSelectedSalesCode + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("All Void",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(262,mSelectedSalesCode);
                                            get_void_reason();
//                                    setCreditCardProcessVoid();
                                        }
                                    })

                            .setNeutralButton("Partial Void", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent voidCardList = new Intent(getApplicationContext(), VoidCardList.class);
                                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ---------------------------------------------
                                    voidCardList.putExtra("salesCode", mSelectedSalesCode);
                                    // -------------------------------------------------------------------------------------
                                    startActivity(voidCardList);
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
                if ((mActivity != null) && (!mActivity.isFinishing())) {
                    new AlertDialog.Builder(context)
                            .setTitle("VOID")
                            .setMessage("Are you sure you want to void?\n[Sale Group# : " + mSelectedSalesCode + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(262,mSelectedSalesCode);
                                            get_void_reason();
//                                    setCreditCardProcessVoid();
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
            // -----------------------------------------------------------------------------------------------------
        }
    }

    public void get_void_reason(){
        popupVoidReason = new PopupVoidReason(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ok
                if (!popupVoidReason.et_reason.getText().toString().isEmpty()){
                    mCancelReason = popupVoidReason.et_reason.getText().toString();

                    setCreditCardProcessVoid();
                    popupVoidReason.dismiss();
                }
            }
        }, new View.OnClickListener() {
            // cancel
            @Override
            public void onClick(View v) {
                popupVoidReason.dismiss();
            }
        });
        popupVoidReason.show();

    }

    @Override
    public void finish() {
        super.finish();
        GlobalMemberValues.mRekitchenprintYN = "N";

        // 05152023
        GlobalMemberValues.isLabelReprint = false;

    }
}
