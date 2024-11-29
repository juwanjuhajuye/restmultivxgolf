package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class SaleHistory_web extends Activity {
    static final String TAG = "SaleHistory_web";

    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    static DatabaseInit dbInitForTipUpload;

    private Button closeBtn;

    private static LinearLayout saleHistorySaleListLinearLayout;

    private LinearLayout parentLn;

    ScrollView saleHistorySaleListScrollView;

    static TextView saleHistoryStartDateEditText, saleHistoryEndDateEditText;
    static EditText saleHistoryCustomerSearchEditText;
    Button saleHistoryCustomerSearchSHButton;
    Button saleHistoryReturnButton, saleHistoryReprintButton;
    static Button saleHistoryDeliveringButton;
    Button saleHistoryDoneButton;
    static Button saleHistory_sendNotification_Btn;
    static Button saleHistoryPickupButton;
    //Button saleHistoryTipAdjustmentButton, saleHistoryVoidButton;
    static Button saleHistoryNotifyDeliveryButton;

    // 04.20.2022 추가
    static ImageButton saleHistoryMMSButton;

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

    //Spinner mSaleHistoryCustomerSearchSpinner;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    //static String selectedItemSpinner = "All";

    static Intent mIntent;

    static String mSelectedSalesCode = "";
    static String mSelectedSalonSalesDetailIdx = "";
    static String mSelectedSalonSalesDetailStatus = "";
    static String mSelectedReceiptJsonArrayIndex = "";
    static LinearLayout mSelectedSalonSalesDetailLn;

    TextView mSelectedDateTextView;

    static String mCustomerValue = "";

    static String mPushSalesCode = "";
    String mPushIntentOpenType = "";

    // 카드결제 연동 관련 변수 -------------------------------
    public static ProgressDialog proDial;       // 프로그래스바
    public static ProgressDialog proDialForStatusChange;       // 프로그래스바
    public static ProgressDialog proDialForGetReceiptJson;       // 프로그래스바
    String creditCardVoidReturnValue;           // 결제처리후 리턴값
    // ----------------------------------------------------

    // 터미널 연결정보 --------------------------------------
    public static String paymentGateway = "";
    public static String networkIp = "";
    public static String networkPort = "";
    // ----------------------------------------------------

    // Pax Void 처리를 위한 ArrayList
    public static ArrayList<String> mCardPaymentInfoArrayList;

    // 주문내역을 저장할 ListView
    public static ArrayList<TemporaryWebOrders> mCollectionOrdersArrList;

    public static int mTempFlag = 0;
    public static int mTempFlagForChangeStatus = 0;

    // 검색어
    public static String mSearchDeliveryTakeaway = "All";

    public static String mApiReturnValue = "";

    public static String mDeliveryTakeawayValueFromOutActivity = "All";

    public static String mApiReturnCode = "";

    // ReceiptJson 저장을 위한 ArrayList
    public static ArrayList<String> mReceiptJsonArray;

    public static String mSelectedReceiptJsonStr = "";

    // curbside
    public static String curbside_carinfo;
    public static String curbside_calltime;
    public static String curbside_phonenum;
    public static String curbside_ordernum;

    public static int i_isreturn = 0;

    // spinner
    public static Spinner mSpinnerAdapterCancelReason;
    public String[] mStringArray = {"select a reason for cancellation.","Exhaustion of ingredients", "End of business hours"};

    public static EditText saleHistoryCancelReason_text;
    public TextView salehistory_web_title_order_type;

    public static String mPushOrderType;

    // 10112023
    public static String mPushOrderfrom;
    public static String mPushSalescodethirdparty;


    // 04.20.2022 추가 -----------------------------------------
    static String setCustomerOrderNum = "";
    static String setToPhoneNum = "";

    private MmsPopup mmsPopup;
    // --------------------------------------------------------

    int REQUEST_CODE_RETURN_WEB = 11113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_web);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        dbInitForTipUpload = dbInit;

        parentLn = (LinearLayout)findViewById(R.id.shlparentln);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mCustomerValue = mIntent.getStringExtra("CustomerValue");
            mPushSalesCode = mIntent.getStringExtra("pushSalesCode");
            mPushIntentOpenType = mIntent.getStringExtra("pushIntentOpenType");
            mPushOrderType = mIntent.getStringExtra("pushOrderType");

            // 10112023
            mPushOrderfrom = mIntent.getStringExtra("pushOrderfrom");
            mPushSalescodethirdparty = mIntent.getStringExtra("pushSalescodethirdparty");

            GlobalMemberValues.logWrite("setSalesCode", "넘겨받은 mPushSalesCode : " + mPushSalesCode + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();
    }

    public void setContents() {

        if (!GlobalMemberValues.isStrEmpty(mPushSalesCode)) {
            mSelectedSalesCode = mPushSalesCode;
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
        deliverytakeawayGroup = (RadioGroup)findViewById(R.id.deliverytakeawayGroup);
        deliverytakeawayGroup.setOnCheckedChangeListener(checkDeliveryTakeaway);

        deliveryRadioButton = (RadioButton)findViewById(R.id.deliveryRadioButton);
        deliveryRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + deliveryRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        takeawayRadioButton = (RadioButton)findViewById(R.id.takeawayRadioButton);
        takeawayRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + takeawayRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        hereRadioButton = (RadioButton)findViewById(R.id.hereRadioButton);
        hereRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + hereRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        allRadioButton = (RadioButton)findViewById(R.id.allRadioButton);
        allRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + allRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        if (!GlobalMemberValues.isStrEmpty(mDeliveryTakeawayValueFromOutActivity)) {
            if (mDeliveryTakeawayValueFromOutActivity.equals("All")) {
                allRadioButton.setChecked(true);
            }
        }

        /***********************************************************************************************************/

        // 스크롤뷰 객체 생성
        saleHistorySaleListScrollView = (ScrollView)findViewById(R.id.saleHistorySaleListScrollView);

        /** 객체 생성 및 리스너 연결 *******************************************************************************/
        salehistory_web_title_order_type = (TextView)findViewById(R.id.salehistory_web_title_order_type);

        if (mPushOrderType == null){
            mPushOrderType = "W";
        }

        switch (mPushOrderType){
            case "W" :
                salehistory_web_title_order_type.setBackgroundColor(Color.parseColor("#1062dc"));
                salehistory_web_title_order_type.setText("WEBSITE ORDER");
                break;
            case "M" :
                salehistory_web_title_order_type.setBackgroundColor(Color.parseColor("#ff322d"));
                salehistory_web_title_order_type.setText("MOBILE ORDER");
                break;
            case "A" :
                salehistory_web_title_order_type.setBackgroundColor(Color.parseColor("#67ac09"));
                salehistory_web_title_order_type.setText("APP ORDER");
                break;
            case "T" :
                salehistory_web_title_order_type.setBackgroundColor(Color.parseColor("#a96d25"));
                salehistory_web_title_order_type.setText("OTTER ORDER");
                break;
            default:
                break;
        }

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

        // Spinner 객체 생성 및 연결 ----------------------------------------------------------------------------
        /**
        mSaleHistoryCustomerSearchSpinner = (Spinner)findViewById(R.id.saleHistoryCustomerSearchSpinner);
        String[] strSearchItemList = {"All", "Delivery", "Pick Up"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < strSearchItemList.length; i++) {
            mGeneralArrayListForSpinner.add(strSearchItemList[i]);
        }
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSaleHistoryCustomerSearchSpinner.setAdapter(mSpinnerAdapter);
        mSaleHistoryCustomerSearchSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);
         **/
        // -----------------------------------------------------------------------------------------------------

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

        /**
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
         **/


        saleHistoryCancelReason_text = (EditText)findViewById(R.id.saleHistoryCancelReason_text);

        mSpinnerAdapterCancelReason = (Spinner)findViewById(R.id.saleHistoryCancelReason_roll);

        ArrayAdapter<String> mSpinnerAdapterPrinterName = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mStringArray);
        mSpinnerAdapterPrinterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAdapterCancelReason.setAdapter(mSpinnerAdapterPrinterName);
        mSpinnerAdapterCancelReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    saleHistoryCancelReason_text.setText("");
                } else {
                    saleHistoryCancelReason_text.setText(mStringArray[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saleHistoryReturnButton = (Button)findViewById(R.id.saleHistoryReturnButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnButton.setText("");
            saleHistoryReturnButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_cancel);
        } else {
            saleHistoryReturnButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryReturnButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 230421
        saleHistoryReturnButton.setVisibility(View.GONE);

        saleHistoryReprintButton = (Button)findViewById(R.id.saleHistoryReprintButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReprintButton.setText("");
            saleHistoryReprintButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_reprint);
        } else {
            saleHistoryReprintButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryReprintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryDeliveringButton = (Button)findViewById(R.id.saleHistoryDeliveringButton);

        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryDeliveringButton.setText("");
            saleHistoryDeliveringButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_shipping);
        } else {
            saleHistoryDeliveringButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryDeliveringButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryDoneButton = (Button)findViewById(R.id.saleHistoryDoneButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryDoneButton.setText("");
            saleHistoryDoneButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_done);
        } else {
            saleHistoryDoneButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryDoneButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistory_sendNotification_Btn = (Button)findViewById(R.id.saleHistorySendNotificationButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistory_sendNotification_Btn.setText("");
            saleHistory_sendNotification_Btn.setBackgroundResource(R.drawable.button_salehistory_detail_web_send_notification);
        } else {
            saleHistory_sendNotification_Btn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistory_sendNotification_Btn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryPickupButton = (Button)findViewById(R.id.saleHistoryPickupButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryPickupButton.setText("");
            saleHistoryPickupButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_pickup_done);
        } else {
            saleHistoryPickupButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryPickupButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }





        saleHistoryNotifyDeliveryButton = (Button)findViewById(R.id.saleHistoryNotifyDeliveryButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryNotifyDeliveryButton.setText("");
            saleHistoryNotifyDeliveryButton.setBackgroundResource(R.drawable.button_salehistory_detail_web_notify_delivery);
        } else {
            saleHistoryNotifyDeliveryButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryNotifyDeliveryButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        /**
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
         **/

        //saleHistoryVoidButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryReturnButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryReprintButton.setOnClickListener(saleHistoryBtnClickListener);
        //saleHistoryTipAdjustmentButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryDeliveringButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryDoneButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistory_sendNotification_Btn.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryNotifyDeliveryButton.setOnClickListener(saleHistoryBtnClickListener);
        saleHistoryPickupButton.setOnClickListener(saleHistoryBtnClickListener);

        // 04.20.2022 추가
        saleHistoryMMSButton = (ImageButton)findViewById(R.id.saleHistoryMMSButton);
        saleHistoryMMSButton.setOnClickListener(saleHistoryBtnClickListener);


        // ScrollView 에 속한 첫번째 LinearLayout 객체
        saleHistorySaleListLinearLayout = (LinearLayout)findViewById(R.id.saleHistorySaleListLinearLayout);
        /***********************************************************************************************************/

        if (!GlobalMemberValues.isStrEmpty(mCustomerValue)) {
            String tempCustName = dbInit.dbExecuteReadReturnString(
                    "select name from member1 where uid = '" + mCustomerValue + "' ");
            saleHistoryTopTitleTextView.setText(saleHistoryTopTitleTextView.getText() +
                    " (Customer : " + tempCustName + ")");
        }

        setSearchSalesHistory();

        // 초기화
        setInit();
        saleHistoryCustomerSearchEditText.setText(mCustomerValue);

        if (!GlobalMemberValues.isStrEmpty(mDeliveryTakeawayValueFromOutActivity)) {
            mSearchDeliveryTakeaway = mDeliveryTakeawayValueFromOutActivity;
        }

        if (!GlobalMemberValues.isStrEmpty(mPushIntentOpenType)) {
            if (mPushIntentOpenType.equals("weborders")) {
                // 구글 플레이 서비스 구글플레이서비스 (Google Play Service)
                //FirebaseMessagingService.notiNumbersForReservation = 0;
                // notification 삭제
                NotificationManager mNotificationManager =
                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(9125);
            }
        }

        // curbside info
        String curbsideQuery = "select carinfo, calltime, phonenum, ordernum" +
                " from salon_store_curbsidepickup " +
                " where salesCode = '" + mPushSalesCode + "' " ;
        Cursor curbsideCursor;
        curbsideCursor = dbInit.dbExecuteRead(curbsideQuery);
        while (curbsideCursor.moveToNext()) {
            curbside_carinfo = GlobalMemberValues.getDBTextAfterChecked(curbsideCursor.getString(0), 1);
            curbside_calltime = GlobalMemberValues.getDBTextAfterChecked(curbsideCursor.getString(1), 1);
            curbside_phonenum = GlobalMemberValues.getDBTextAfterChecked(curbsideCursor.getString(2), 1);
            curbside_ordernum = GlobalMemberValues.getDBTextAfterChecked(curbsideCursor.getString(3), 1);
        }
        //

        if (!GlobalMemberValues.isStrEmpty(mPushSalesCode)) {
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "update salon_sales_web_push_realtime set viewyn = 'Y' " +
                    " where salesCode = '" + mPushSalesCode + "' ";
            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("salon_sales_web_push_realtime", "query : " + tempQuery + "\n");
            }
            DatabaseInit dbInit = new DatabaseInit(getApplicationContext());
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
            } else {
                saleHistoryCustomerSearchEditText.setText(mPushSalesCode);
            }
        }


        if (mPushOrderType.equals("T")) {
            mSpinnerAdapterCancelReason.setVisibility(View.GONE);
            saleHistoryCancelReason_text.setVisibility(View.GONE);
            saleHistoryReturnButton.setVisibility(View.GONE);
            saleHistoryDoneButton.setVisibility(View.GONE);
            saleHistoryPickupButton.setVisibility(View.GONE);
            saleHistoryNotifyDeliveryButton.setVisibility(View.GONE);
            saleHistoryDeliveringButton.setVisibility(View.GONE);
            saleHistory_sendNotification_Btn.setVisibility(View.GONE);

            // 10112023
            // saleHistoryReprintButton.setVisibility(View.GONE);
        }
    }

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    /**
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite(TAG, "Selected Spinner Item : " + selectedItemSpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
     **/
    /*******************************************************************************/


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
                            mSearchDeliveryTakeaway = "All";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    // 04.20.2022 추가
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
                case R.id.saleHistoryReturnButton: {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<2>")) {
                        Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent.putExtra("no_permission", true);
                        adminPasswordIntent.putExtra("openClassMethod", "salehistory_detail_return_web");
                        mActivity.startActivityForResult(adminPasswordIntent,REQUEST_CODE_RETURN_WEB);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Please select an order to change the order status to delivering", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
                        return;
                    }

                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            return;
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("RETURN")
                                .setMessage("Are you sure you want to return?\n[Sale Group# : " + mSelectedSalesCode + "]")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                                                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                                adminPasswordIntent.putExtra("openClassMethod", "salehistoryweb_return");
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

                    } else {
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                }
                case R.id.saleHistoryDeliveringButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Please select an order to change the order status to delivering", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
                        return;
                    }

                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
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
                                                adminPasswordIntent.putExtra("openClassMethod", "salehistoryweb_delivering");
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

                    } else {
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                }

                case R.id.saleHistorySendNotificationButton : {
                    setStatusDone("T");
                    break;
                }
                case R.id.saleHistoryDoneButton : {
                    setStatusDone("D");
                    break;
                }


                /**
                case R.id.saleHistoryTipAdjustmentButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "TIP ADJUSTMENT", "Choose a item", "Close");
                        return;
                    }

                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
                        return;
                    }

                    Intent tipAdjustmentIntent = new Intent(getApplicationContext(), SaleHistoryTipAdjustment.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    tipAdjustmentIntent.putExtra("salesCode", mSelectedSalesCode);
                    tipAdjustmentIntent.putExtra("salonSalesDetailIdx", mSelectedSalonSalesDetailIdx);
                    // -------------------------------------------------------------------------------------
                    startActivity(tipAdjustmentIntent);
                    overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);

                    GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 1" + "\n");

                    break;
                }
                 **/
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
                    //GlobalMemberValues.logWrite("voidSelectedStatus", "selectedStatus : " + mSelectedSalonSalesDetailStatus + "\n");
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Choose sale to reprint", "Close");
                        return;
                    }
                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
                        return;
                    }

                    GlobalMemberValues.mReReceiptprintYN = "Y";
                    printReceipt();
                    /**
                    new AlertDialog.Builder(context)
                            .setTitle("REPRINT")
                            .setMessage("Are you sure you want to reprint?\n[Sale Group# : " + mSelectedSalesCode + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            GlobalMemberValues.mReReceiptprintYN = "Y";
                                            printReceipt();
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
                case R.id.saleHistoryNotifyDeliveryButton : {
                    String curbsideConfirmString = "";
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {

                            String strChangeStatus = "";
                            // 프로그래스바 띄우기 -------------------------------------------------
                            proDialForStatusChange = new ProgressDialog(mActivity);
                            proDialForStatusChange.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            proDialForStatusChange.setTitle("Navyzebra QSR Cloud");
                            proDialForStatusChange.setMessage("The Notify to Delivery progress....");
                            proDialForStatusChange.setProgress(0);
                            proDialForStatusChange.setMax(100);
                            proDialForStatusChange.setButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            proDialForStatusChange.show();

                            final String tempSalesCode = mPushSalesCode;
                            Thread thread = new Thread(new Runnable() {
                                public void run() {
                                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                    API_NotifyDeliveryCompanyOfOrders api_notifyDeliveryCompanyOfOrders = new API_NotifyDeliveryCompanyOfOrders(tempSalesCode);
                                    api_notifyDeliveryCompanyOfOrders.execute(null, null, null);
                                    try {
                                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                        if (api_notifyDeliveryCompanyOfOrders.mFlag) {
                                            mApiReturnValue = api_notifyDeliveryCompanyOfOrders.mApiReturnCode;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handlerNotifyDelivery.sendEmptyMessage(0);
                                }
                            });
                            thread.start();

                        }
                    }
                    break;
                }
                case R.id.saleHistoryPickupButton : {
                    if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
                        return;
                    }

                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            return;
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("Pick up done")
                                .setMessage("Would you like to change the status to pick up done?\n[Sale Group# : " + mSelectedSalesCode + "]")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                                                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                                adminPasswordIntent.putExtra("openClassMethod", "salehistoryweb_pickupdone");
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

                    } else {
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                }

                // 04.20.2022 추가
                case R.id.saleHistoryMMSButton : {
                    mmsPopup = new MmsPopup(context, mms_close_listener, setCustomerOrderNum, setToPhoneNum);
                    mmsPopup.show();
                    GlobalMemberValues.logWrite("mmspopup", "mmspopup 실행 - 1" + "\n");
                    break;
                }
            }
        }
    };

    public void setStatusDone(String paramDT) {
        String msg1 = "Please select an order to change the order status to done";
        String msg2 = "Change status to done";
        String msg3 = "Would you like to change the status of the selected order to done?";
        if (paramDT.equals("T")) {
            msg1 = "Please select an order to send order ready notification";
            msg2 = "Send order ready notification";
            msg3 = "Would you like to send notification?";
        }

        if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesDetailIdx)) {
            GlobalMemberValues.displayDialog(context, "Waraning", msg1, "Close");
            return;
        }

        if (GlobalMemberValues.getIntAtString(mSelectedSalonSalesDetailStatus) > 0) {
            GlobalMemberValues.displayDialog(context, "Waraning", "This item was void or returned", "Close");
            return;
        }

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle(msg2)
//                    .setMessage("Would you like to change the status of the selected order to done?\n[Sale Group# : " + mSelectedSalesCode + "]")
                    .setMessage(msg3)
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                    adminPasswordIntent.putExtra("openClassMethod", "salehistoryweb_done");
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

        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }
    }

    public static void setChangeStatus(final String paramStatus) {
//        mApiReturnValue = "99";
        String strChangeStatus = "";
        switch (paramStatus) {
            case "1" : {
                strChangeStatus = "delivering";
                break;
            }

            case "2" : {
                if (mSearchDeliveryTakeaway.equals("T")) {
                    strChangeStatus = "'Sent Notification (Done)'";
                } else {
                    strChangeStatus = "Done";
                }
                break;
            }

            case "3" : {
                strChangeStatus = "Pick Up Done";
                break;
            }

        }
        // 프로그래스바 띄우기 -------------------------------------------------
        proDialForStatusChange = new ProgressDialog(mActivity);
        proDialForStatusChange.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialForStatusChange.setTitle("Navyzebra QSR Cloud");
        proDialForStatusChange.setMessage("The order's status is changing to " + strChangeStatus + "...");
        proDialForStatusChange.setProgress(0);
        proDialForStatusChange.setMax(100);
        proDialForStatusChange.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDialForStatusChange.show();
        // -------------------------------------------------------------------

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                API_weborder_deliverytakeaway_changestatus apiWebOrderChangeStatus = new API_weborder_deliverytakeaway_changestatus(mSelectedSalesCode, paramStatus);
                apiWebOrderChangeStatus.execute(null, null, null);

                int i_timeout = 0;
                while (mApiReturnValue == ""){
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apiWebOrderChangeStatus.mFlag) {
                            mApiReturnValue = apiWebOrderChangeStatus.mApiReturnCode;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i_timeout++;
                    if (i_timeout == 5000){
                        mApiReturnValue = "99"; // 오류
                    }
                }
                mTempFlagForChangeStatus = 0;
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handlerWebOrderChange.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public static void setReturnProcessing() {
        String strChangeStatus = "";
        // 프로그래스바 띄우기 -------------------------------------------------
        proDialForStatusChange = new ProgressDialog(mActivity);
        proDialForStatusChange.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialForStatusChange.setTitle("Navyzebra QSR Cloud");
        proDialForStatusChange.setMessage("The return processing is in progress....");
        proDialForStatusChange.setProgress(0);
        proDialForStatusChange.setMax(100);
        proDialForStatusChange.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDialForStatusChange.show();
        // -------------------------------------------------------------------

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                API_weborder_return apiWebOrderReturn = new API_weborder_return(mSelectedSalesCode,saleHistoryCancelReason_text.getText().toString());
                apiWebOrderReturn.execute(null, null, null);

                int i_timeout = 0;
                while (mApiReturnValue == ""){
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apiWebOrderReturn.mFlag) {
                            mApiReturnValue = apiWebOrderReturn.mApiReturnCode;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i_timeout++;
                    if (i_timeout == 30){
                        mApiReturnValue = "9999"; // 오류
                    }
                }
                i_isreturn = 1;
                mTempFlagForChangeStatus = 0;
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handlerWebOrderChange.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------

            }
        });
        thread.start();
    }

    public static void printReceipt() {
        GlobalMemberValues.logWrite("LogAboutreceiptJson", "여기실행.. " + "\n");

        if (GlobalMemberValues.isStrEmpty(mSelectedSalesCode)) {
            GlobalMemberValues.displayDialog(context, "Waraning", "Choose sales to reprint", "Close");
            return;
        }

        // 프로그래스바 띄우기 -------------------------------------------------
        proDialForGetReceiptJson = new ProgressDialog(mActivity);
        proDialForGetReceiptJson.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialForGetReceiptJson.setTitle("Navyzebra QSR Cloud");
        proDialForGetReceiptJson.setMessage("Download the receipt data from the Cloud Server...");
        proDialForGetReceiptJson.setProgress(0);
        proDialForGetReceiptJson.setMax(100);
        proDialForGetReceiptJson.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDialForGetReceiptJson.show();
        // -------------------------------------------------------------------

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    if (!GlobalMemberValues.isOnline().equals("00")) {
                        GlobalMemberValues.showDialogNoInternet(context);
                    } else {
                        API_download_weborders_receiptjson apiWebOrdersReceiptJson = new API_download_weborders_receiptjson(mSelectedSalesCode);
                        apiWebOrdersReceiptJson.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (apiWebOrdersReceiptJson.mFlag) {
                                mSelectedReceiptJsonStr = apiWebOrdersReceiptJson.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            mSelectedReceiptJsonStr = "";
                        }
                    }
                }
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                receiptJsonHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public static Handler receiptJsonHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mSelectedReceiptJsonStr)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음.
                String receiptJson = mSelectedReceiptJsonStr;
                GlobalMemberValues.logWrite("LogAboutreceiptJson", "receiptJson : " + receiptJson + "\n");

                if (!GlobalMemberValues.isStrEmpty(receiptJson)) {
                    JSONObject jsonroot = null;
                    try {
                        jsonroot = new JSONObject(receiptJson);
                        jsonroot.put("reprintyn", "Y");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (jsonroot != null) {
                        Intent salehistoryReprintIntent = new Intent(context.getApplicationContext(), SaleHistory_Reprint.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        salehistoryReprintIntent.putExtra("JsonReceipt", jsonroot.toString());
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(salehistoryReprintIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }

                        /**
                        // 영수증 프린트 하기 ------------------------------------------------------------------
                        GlobalMemberValues.printReceiptByJHP(jsonroot, context, "payment");
                        // -----------------------------------------------------------------------------------
                         **/
                    }
                } else {
                    GlobalMemberValues.displayDialog(context, "Waraning", "There is no data to reprint", "Close");
                }
            }

            // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
            GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
            proDialForGetReceiptJson.dismiss();
            GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
            // -------------------------------------------------------------------------------------
        }
    };

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
        mCollectionOrdersArrList = null;
        mCollectionOrdersArrList = new ArrayList<TemporaryWebOrders>();

        String searchStartDate = saleHistoryStartDateEditText.getText().toString();
        String searchEndDate = saleHistoryEndDateEditText.getText().toString();
        //String searchCustomerKind = selectedItemSpinner;

        searchStartDate  = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, -10, 0, 0);
        searchEndDate = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 1);

        saleHistoryStartDateEditText.setText(searchStartDate);
        saleHistoryEndDateEditText.setText(searchEndDate);

        // 검색을 위한 전역변수 할당 -------------------------------------------
        if (deliveryRadioButton.isChecked()) {
            mSearchDeliveryTakeaway = "D";
        }
        if (takeawayRadioButton.isChecked()) {
            mSearchDeliveryTakeaway = "T";
        }
        if (hereRadioButton.isChecked()) {
            mSearchDeliveryTakeaway = "H";
        }
        if (allRadioButton.isChecked()) {
            mSearchDeliveryTakeaway = "All";
        }
        // -------------------------------------------------------------------

        // 프로그래스바 띄우기 -------------------------------------------------
        proDial = new ProgressDialog(mActivity);
        proDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDial.setTitle("Navyzebra QSR Cloud");
        proDial.setMessage("Online orders' data is loading...");
        proDial.setProgress(0);
        proDial.setMax(100);
        proDial.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDial.show();
        // -------------------------------------------------------------------

        GlobalMemberValues.logWrite("onlineorders", "mSearchDeliveryTakeaway111 : " + mSearchDeliveryTakeaway + "\n");

        final String finalSearchStartDate = searchStartDate;
        final String finalSearchEndDate = searchEndDate;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                APIDownLoad_online_orders apiDownloadOnlineOrders
                        = new APIDownLoad_online_orders(finalSearchStartDate, finalSearchEndDate, mPushSalesCode, mDeliveryTakeawayValueFromOutActivity);
                apiDownloadOnlineOrders.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiDownloadOnlineOrders.mFlag) {
                        GlobalMemberValues.logWrite("searchSaleHistoryWebDataReturnCode", "ReturnCode : " + apiDownloadOnlineOrders.mApiReturnCode + "\n");
                        mApiReturnCode = apiDownloadOnlineOrders.mApiReturnCode;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTempFlag = 0;
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -------------------------------------------------
                handlerWebOrder.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private static void callingSetSaleHistory() {
        if (mCollectionOrdersArrList.size() == 0) {
            if (mApiReturnCode.equals("11")) {
                setSaleHistory();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Network error or no data\nTouch [YES] button to try again")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setSearchSalesHistory();
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
            setSaleHistory();
        }
    }

    private static Handler handlerWebOrder = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlag == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                callingSetSaleHistory();
                // -------------------------------------------------------------------------------------
                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
                proDial.dismiss();
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
                // -------------------------------------------------------------------------------------
            }
        }
    };

    private static Handler handlerWebOrderChange = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagForChangeStatus == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                if (mApiReturnValue.equals("00")) {
                    // 주방 프린트 처리해야함.
                    if (i_isreturn == 1){

                        return_kitchenPrint();

                        i_isreturn = 0;
                    }
                    setSearchSalesHistory();
                } else {
                    GlobalMemberValues.displayDialog(context, "Order Status Change", "Cloud API Network Error [" + mApiReturnValue + "]", "Close");
                }
                mApiReturnValue = "";
                // -------------------------------------------------------------------------------------
                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
                proDialForStatusChange.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };

    private static Handler handlerNotifyDelivery = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagForChangeStatus == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                if (mApiReturnValue.equals("00")) {
                    // 주방 프린트 처리해야함.
                    if (mApiReturnValue.equals("00")) {
                        GlobalMemberValues.displayDialog(context, "Notify to Delivery", "Successfully Notified to Delivery Company", "Close");
                    } else {
                        GlobalMemberValues.displayDialog(context, "Notify to Delivery", "confirm fail", "Close");
                    }

//                    setSearchSalesHistory();
                } else {
                    GlobalMemberValues.displayDialog(context, "Notify to Delivery", "Cloud API Network Error [" + mApiReturnValue + "]", "Close");
                }
                mApiReturnValue = "";
                // -------------------------------------------------------------------------------------
                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
                proDialForStatusChange.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };

    private static Handler handler_refrash = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlag == 0) {
//                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
//                callingSetSaleHistory();
//                // -------------------------------------------------------------------------------------
//                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
//                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
//                proDial.dismiss();
//                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
//                // -------------------------------------------------------------------------------------

                Intent intent = mIntent;
                mActivity.finish();
                mActivity.startActivity(intent);

            }
        }
    };

    public static void setSaleHistory() {
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

        int custCount = 0;
        for (int weborderi = 0; weborderi < mCollectionOrdersArrList.size(); weborderi++) {
            TemporaryWebOrders temporaryWebOrders = mCollectionOrdersArrList.get(weborderi);

            // salon_sales_web 테이블 정보 ----------------------------------------------------------------------------------
            String salonSalesWebIdx = temporaryWebOrders.idx;
            String salonSalesWebSalesCode = temporaryWebOrders.salesCode;

            String salonSalesWebCustomerId = temporaryWebOrders.customerId;
            String salonSalesWebCustomerName = temporaryWebOrders.customerName;
            String salonSalesWebCustomerPhone = temporaryWebOrders.customerPhone;
            String salonSalesWebCustomerEmail = temporaryWebOrders.customerEmail;
            String salonSalesWebCustomerAddr1 = temporaryWebOrders.customerAddr1;
            String salonSalesWebCustomerAddr2 = temporaryWebOrders.customerAddr2;
            String salonSalesWebCustomerCity = temporaryWebOrders.customerCity;
            String salonSalesWebCustomerState = temporaryWebOrders.customerState;
            String salonSalesWebCustomerZip = temporaryWebOrders.customerZip;

            String salonSalesWebCustomerAddressAll = temporaryWebOrders.CustomerAddressAll;

            String salonSalesWebDeliveryDay = temporaryWebOrders.deliveryday;
            String salonSalesWebDeliveryTime = temporaryWebOrders.deliverytime;
            String salonSalesWebDeliveryDate = temporaryWebOrders.deliverydate;

            String salonSalesWebCustomermemo = temporaryWebOrders.customermemo;

            String salonSalesWebCouponNumber = temporaryWebOrders.couponnumber;
            String salonSalesWebGiftcardNumberUsed = temporaryWebOrders.giftcardNumberUsed;

            String salonSalesWebDiscount = temporaryWebOrders.discount;
            String salonSalesWebDiscountTxt = temporaryWebOrders.discountTxt;

            String salonSalesWebGiftcardPriceUsed = temporaryWebOrders.giftcardPriceUsed;
            String salonSalesWebCardPriceUsed = temporaryWebOrders.CardPriceUsed;
            String salonSalesWebCashPriceUsed = temporaryWebOrders.CashPriceUsed;
            String salonSalesWebPointPriceUsed = temporaryWebOrders.PointPriceUsed;

            String salonSalesWebDeliveryTakeaway = temporaryWebOrders.deliverytakeaway;
            String salonSalesWebDeliveryStatus = temporaryWebOrders.DeliveryStatus;
            String salonSalesWebTakeawayStatus = temporaryWebOrders.TakeawayStatus;

            String salonSalesWebPickupdone = temporaryWebOrders.Pickupdone;

            String salonSalesWebReceiptJson = temporaryWebOrders.ReceiptJson;
            String salonSalesWebSaleDate = temporaryWebOrders.SaleDate;
            String salonSalesWebStatus = temporaryWebOrders.Status;

            String salonSalesWebSaleItems = temporaryWebOrders.SalesItems;

            String salonSalesWebKitchenPrintedYN = temporaryWebOrders.KitchenprintedYN;

            String salonSalesDeliveryPickupFee = temporaryWebOrders.DeliveryPickupFee;

            String salonSalesCustomerOrderNumber = temporaryWebOrders.CustomerOrderNumber;

            String carInfo_License = temporaryWebOrders.CarInfo_License;
            String carInfo_Color = temporaryWebOrders.CarInfo_Color;
            String carInfo_Type = temporaryWebOrders.CarInfo_Type;
            String carInfo_ParkingSpace = temporaryWebOrders.CarInfo_ParkingSpace;

            String onlinetype = temporaryWebOrders.onlinetype;
            if (GlobalMemberValues.isStrEmpty(onlinetype)) {
                onlinetype = "W";
            }

            String onlinetypeStr = "";
            switch (onlinetype) {
                case "W" : {
                    onlinetypeStr = "Website";
                    break;
                }
                case "A" : {
                    onlinetypeStr = "App";
                    break;
                }
                case "K" : {
                    onlinetypeStr = "Kiosk";
                    break;
                }
            }

            // 12172022
            // 키오스크 일 경우 아래 부분은 gone 처리
            if (onlinetype == "K" || onlinetype.equals("K")) {
                mSpinnerAdapterCancelReason.setVisibility(View.GONE);
                saleHistoryCancelReason_text.setVisibility(View.GONE);
                saleHistoryPickupButton.setVisibility(View.GONE);
                saleHistoryNotifyDeliveryButton.setVisibility(View.GONE);
                saleHistoryDeliveringButton.setVisibility(View.GONE);
                saleHistory_sendNotification_Btn.setVisibility(View.GONE);
                saleHistoryMMSButton.setVisibility(View.GONE);
            }


            // ------------------------------------------------------------------------------------------------------------
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

            String dbSalesCode = salonSalesWebSalesCode;
            // 세일코드값이 있을 경우에만..
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(dbSalesCode, " ", ""))) {
                // 세일 상태
                int salonSalesStatus = GlobalMemberValues.getIntAtString(salonSalesWebStatus);
                //String salonSalesLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALESBGCOLOR[salonSalesStatus];

                int saleListBg = R.drawable.roundlayout_salelist_bg2;
                switch (salonSalesStatus) {
                    case 0 : {
                        saleListBg = R.drawable.roundlayout_salelist_bg2;
                        break;
                    }
                    case 1 : {
                        saleListBg = R.drawable.roundlayout_salelist_bg1;
                        break;
                    }
                    case 3 : {
                        saleListBg = R.drawable.roundlayout_salelist_bg3;
                        break;
                    }
                }

                String statusStr = "";
                String deliveryTakeawayStatusTxtColor = "#189dba";
                if (salonSalesStatus == 1 || salonSalesStatus == 3) {
                    if (salonSalesStatus == 1) {
                        statusStr = "Void";
                        deliveryTakeawayStatusTxtColor = "#ac5331";
                    } else {
                        statusStr = "Return";
                        deliveryTakeawayStatusTxtColor = "#f50e25";
                    }
                } else {
                    if (salonSalesWebDeliveryTakeaway.equals("D") || salonSalesWebDeliveryTakeaway == "D") {
                        switch (salonSalesWebDeliveryStatus) {
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
                        switch (salonSalesWebTakeawayStatus) {
                            case "0" : {
                                statusStr = "Cooking";
                                deliveryTakeawayStatusTxtColor = "#189dba";
                                break;
                            }
                            case "2" : {
                                if (salonSalesWebDeliveryTakeaway.equals("T")) {
                                    statusStr = "Sent Notification";
                                    deliveryTakeawayStatusTxtColor = "#d62966";
                                } else {
                                    statusStr = "Done";
                                    deliveryTakeawayStatusTxtColor = "#d62966";
                                }


                                break;
                            }
                        }
                        if (salonSalesWebPickupdone.equals("Y")){
                            statusStr = "Pick Up Done";
                            deliveryTakeawayStatusTxtColor = "#ff0000";
                        }
                    }
                }

                String kitchenPrintedStr = "";
                String kitchenPrintedColorStr = "#a4a2a9";
                if (salonSalesWebKitchenPrintedYN.equals("Y")) {
                    kitchenPrintedStr = "Printed in kitchen. <font color=\"#f20e02\">Touch Here</font> to reprint in kitchen again";
//                    kitchenPrintedColorStr = "#a4a2a9";
                    kitchenPrintedColorStr = "#000000";
                } else {
                    kitchenPrintedStr = "Not printed in kitchen. <font color=\"#f20e02\">Touch Here</font> to print in kitchen";
//                    kitchenPrintedColorStr = "#0054d5";
                    kitchenPrintedColorStr = "#000000";
                }

                double salonSalesDeliveryPickupFeeDbl = GlobalMemberValues.getDoubleAtString(salonSalesDeliveryPickupFee);
                String tempDeliveryPickUpFee = "";
                if (salonSalesDeliveryPickupFeeDbl > 0) {
                    tempDeliveryPickUpFee = " (fee +" + GlobalMemberValues.getCommaStringForDouble(salonSalesDeliveryPickupFeeDbl + "") + ")";
                }

                String tempGetType = "";
                String tempGetTypeColor = "#3a52d8";
                boolean tempIsDeliveringButtonVisible = false;
                switch (salonSalesWebDeliveryTakeaway) {
                    case "H" : {
                        tempGetType = "Here";
                        tempGetTypeColor = "#3a52d8";
                        tempIsDeliveringButtonVisible = false;
                        break;
                    }
                    case "T" : {
                        tempGetType = "To Go (Pick Up)";
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

                ////// PICK UP TIME 추가
                TextView pickupTimeTv = new TextView(context);
                pickupTimeTv.setLayoutParams(statusTvLayoutParams2);
                pickupTimeTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                pickupTimeTv.setPadding(5, 5, 5, 5);
                pickupTimeTv.setText( "PICKUP TIME : "+ salonSalesWebDeliveryDay + " " + salonSalesWebDeliveryTime);
                pickupTimeTv.setTypeface(typeface_monserrat_bold);
                pickupTimeTv.setTextSize(f_fontsize-4);
                pickupTimeTv.setTextColor(Color.parseColor("#050505"));
                pickupTimeTv.setPaintFlags(pickupTimeTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                pickupTimeTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                statusValueLn1.addView(pickupTimeTv);

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
                    case "Sent Notification" :
                        statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_done);
                        break;
                    case "Void" :
                        statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_void);
                        break;
                    case "Return" :
                        statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_return);
                        break;
                    case "Pick Up Done" :
                        statusValueTv.setBackgroundResource(R.drawable.roundlayout_salehistory_detail_pickup_done);
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

                // 해당 주문건이 Void 또는 Return 일 경우 Sale Group 앞에 "VOID" 또는 "RETURN" 표시
                switch (salonSalesStatus) {
                    case 1 : {
                        // Sale Group
                        TextView summaryTopNewTvVoid = new TextView(context);
                        summaryTopNewTvVoid.setLayoutParams(summaryNewTvLayoutParams1);
                        //summaryTopNewTvVoid.setWidth(GlobalMemberValues.getDisplayWidth(context) / 30);
                        summaryTopNewTvVoid.setGravity(Gravity.CENTER);
                        summaryTopNewTvVoid.setPadding(5, 5, 5, 5);
                        summaryTopNewTvVoid.setBackgroundColor(Color.parseColor("#815b56"));
                        summaryTopNewTvVoid.setText("VOID");
                        summaryTopNewTvVoid.setTextSize(GlobalMemberValues.globalAddFontSize() + 10.0f);
                        summaryTopNewTvVoid.setTextSize(GlobalMemberValues.globalAddFontSize() + summaryTopNewTvVoid.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                        summaryTopNewTvVoid.setTextColor(Color.parseColor("#ffffff"));
                        summaryTopNewTvVoid.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        summaryLn1.addView(summaryTopNewTvVoid);
                        break;
                    }
                    case 3 : {
                        // Sale Group
                        TextView summaryTopNewTvVoid = new TextView(context);
                        summaryTopNewTvVoid.setLayoutParams(summaryNewTvLayoutParams1);
                        //summaryTopNewTvVoid.setWidth(GlobalMemberValues.getDisplayWidth(context) / 30);
                        summaryTopNewTvVoid.setGravity(Gravity.CENTER);
                        summaryTopNewTvVoid.setPadding(5, 5, 5, 5);
                        summaryTopNewTvVoid.setBackgroundColor(Color.parseColor("#5e7c80"));
                        summaryTopNewTvVoid.setText("RETURN");
                        summaryTopNewTvVoid.setTextSize(GlobalMemberValues.globalAddFontSize() + 10.0f);
                        summaryTopNewTvVoid.setTextSize(GlobalMemberValues.globalAddFontSize() + summaryTopNewTvVoid.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                        summaryTopNewTvVoid.setTextColor(Color.parseColor("#ffffff"));
                        summaryTopNewTvVoid.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.2f));
                        summaryLn1.addView(summaryTopNewTvVoid);
                        break;
                    }
                }

                String tempReceiptNoCustomerOrderNo = "";
                if (!GlobalMemberValues.isStrEmpty(salonSalesCustomerOrderNumber)) {
                    String temp_order_type = "W";
                    switch (mPushOrderType){
                        case "W" :
                            temp_order_type = "W";
                            break;
                        case "M" :
                            temp_order_type = "M";
                            break;
                        case "A" :
                            temp_order_type = "A";
                            break;
                        default:
                            break;
                    }
                    tempReceiptNoCustomerOrderNo += "ORDER No. : "+ temp_order_type + salonSalesCustomerOrderNumber + "";

                    // 04.20.2022 추가
                    setCustomerOrderNum = temp_order_type + salonSalesCustomerOrderNumber;
                }
                if (!tempReceiptNoCustomerOrderNo.isEmpty()){
                    tempReceiptNoCustomerOrderNo += "\n";
                }
                tempReceiptNoCustomerOrderNo += "Receipt No. : " + dbSalesCode;

                // Sale Group
                TextView summaryTopNewTv1 = new TextView(context);
                summaryTopNewTv1.setLayoutParams(summaryNewTvLayoutParams1);
                summaryTopNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                summaryTopNewTv1.setPadding(5, 5, 5, 5);
                summaryTopNewTv1.setText(tempReceiptNoCustomerOrderNo);
                summaryTopNewTv1.setTypeface(typeface_monserrat_bold);
                summaryTopNewTv1.setPaintFlags(summaryTopNewTv1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                summaryTopNewTv1.setTextSize(f_fontsize + 10);
                summaryTopNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                summaryTopNewTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                summaryLn1.addView(summaryTopNewTv1);

                // Date
                TextView summaryTopNewTv2 = new TextView(context);
                summaryTopNewTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                summaryTopNewTv2.setPadding(5, 5, 5, 5);
                summaryTopNewTv2.setText(salonSalesWebSaleDate);
                summaryTopNewTv2.setTypeface(typeface_monserrat_regular);
//                summaryTopNewTv2.setTextSize(f_fontsize + 4);
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
                summaryTopNewTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.1f));
                summaryLn1.addView(summaryTopNewTv2);

                newLn.addView(summaryLn1);
                /*************************************************************************************************************/


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

                String tempUseCash      = salonSalesWebCashPriceUsed;
                String tempUseCard      = salonSalesWebCardPriceUsed;
                String tempUseGiftCard  = salonSalesWebGiftcardPriceUsed;
                String tempUseCheck     = "0";
                String tempUsePoint     = salonSalesWebPointPriceUsed;
                String tempChangeMoney  = "0";

                String tempTotalDiscountExtraPrice = "0";
                double tempDoubleTotalDcExtra = GlobalMemberValues.getDoubleAtString(tempTotalDiscountExtraPrice);

                String tempDiscountPrice  = "0";
                double tempDoubleDiscountPrice = GlobalMemberValues.getDoubleAtString(tempDiscountPrice);
                String tempExtraPrice  = "0";
                double tempDoubleExtraPrice = GlobalMemberValues.getDoubleAtString(tempExtraPrice);

                String tempAllDiscountExtraPrice  = salonSalesWebDiscount;
                double tempDoubleAllDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(tempAllDiscountExtraPrice);
                String tempAllDiscountExtraStr  = salonSalesWebDiscountTxt;

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
                    tempCheckStr = "[Other] " + GlobalMemberValues.getCommaStringForDouble(tempUseCheck) + tempSpaceStr;
                }
                if (!GlobalMemberValues.isStrEmpty(tempUsePoint) && GlobalMemberValues.getDoubleAtString(tempUsePoint) > 0) {
                    tempPointStr = "[Point] " + GlobalMemberValues.getCommaStringForDouble(tempUsePoint) + tempSpaceStr;
                }
                if (!GlobalMemberValues.isStrEmpty(tempChangeMoney) && GlobalMemberValues.getDoubleAtString(tempChangeMoney) > 0) {
                    tempChangeStr = "[Change] " + GlobalMemberValues.getCommaStringForDouble(tempChangeMoney) + tempSpaceStr;
                }

                // -------------------------------------------------------------------------------------------------------------

                String strSummaryBottomNewTv1 = tempCashStr + tempCardStr + tempGiftCardStr + tempCheckStr + tempPointStr + tempChangeStr;

                summaryBottomNewTv1.setTypeface(typeface_monserrat_bold);
                summaryBottomNewTv1.setText(strSummaryBottomNewTv1);
                summaryBottomNewTv1.setTextSize(f_fontsize + 7);
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
                        tempDiscountStr = "[Each Discount] " + tempDoubleDiscountPrice + tempSpaceStr;
                    }

                    if (tempDoubleExtraPrice > 0) {
                        tempExtraStr = "[Each Extra] " + tempDoubleExtraPrice + tempSpaceStr;
                    }

                    if ((tempDoubleAllDiscountExtraPrice > 0 || tempDoubleAllDiscountExtraPrice < 0)) {
                        if (tempDoubleAllDiscountExtraPrice > 0) {
                            //tempAllDcExtraStr = "[" + tempAllDiscountExtraStr + " : " + tempDoubleAllDiscountExtraPrice + "]" + tempSpaceStr;
                            tempAllDcExtraStr = "[All Extra] " + tempDoubleAllDiscountExtraPrice + tempSpaceStr;
                        }
                        if (tempDoubleAllDiscountExtraPrice < 0) {
                            //tempAllDcExtraStr = "[" + tempAllDiscountExtraStr + " : " + tempDoubleAllDiscountExtraPrice + "]" + tempSpaceStr;
                            tempAllDcExtraStr = "[All Discount] " + tempDoubleAllDiscountExtraPrice + tempSpaceStr;
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
                        tempTotalDiscountExtraStr = tempStr + tempDoubleTotalDcExtra + tempSpaceStr;
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

                mReceiptJsonArray = new ArrayList<String>();

                String saleItemsTxt = "";
                String[] strOrderItemsList = salonSalesWebSaleItems.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                int saleItemsCount = 0;
                for (int salonSalesIndex = 0; salonSalesIndex < strOrderItemsList.length; salonSalesIndex++) {
                    String[] strOrderItems = strOrderItemsList[salonSalesIndex].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                    // salon_sales_detail_web 테이블 정보 --------------------------------------------------------------------------------
                    String salonSalesDetailWebIdx = strOrderItems[0];
                    String salonSalesDetailWebItemIdx = strOrderItems[1];
                    String salonSalesDetailWebItemName = strOrderItems[2];
                    String salonSalesDetailWebItemFilePath = strOrderItems[3];
                    String salonSalesDetailWebOptionTxt = strOrderItems[4];
                    String salonSalesDetailWebQty = strOrderItems[5];
                    String salonSalesDetailWebSalesOrgPrice = strOrderItems[6];
                    String salonSalesDetailWebSalesPrice = strOrderItems[7];
                    String salonSalesDetailWebOptionPrice = strOrderItems[8];
                    String salonSalesDetailWebTax = strOrderItems[9];
                    String salonSalesDetailWebIsSale = strOrderItems[10];
                    String salonSalesDetailWebParentSalesCode = strOrderItems[11];
                    String salonSalesDetailWebParentSalesIdx = strOrderItems[12];
                    String salonSalesDetailWebReturnCode = strOrderItems[13];
                    String salonSalesDetailWebStatus = strOrderItems[14];
                    String salonSalesDetailWebSaveType = strOrderItems[15];
                    String salonSalesDetailWebAddtionalTxt1 = strOrderItems[16];
                    String salonSalesDetailWebAddtionalTxt2 = strOrderItems[17];
                    String salonSalesDetailWebAddtionalprice1 = strOrderItems[18];
                    String salonSalesDetailWebAddtionalprice2 = strOrderItems[19];

                    String salonSalesDetailWebKitchenPrintYN = "N";
                    if (strOrderItems.length > 20) {
                        salonSalesDetailWebKitchenPrintYN = strOrderItems[20];
                    }
                    String salonSalesDetailWebKitchenPrinterNum = "";
                    if (strOrderItems.length > 21) {
                        salonSalesDetailWebKitchenPrinterNum = strOrderItems[21];
                    }
                    // jihun add up
                    String salonSales_Customer_Memo = "";
                    if (strOrderItems.length > 22) {
                        salonSales_Customer_Memo = strOrderItems[22];
                    }
                    String salonSales_If_sold_out = "";
                    if (strOrderItems.length > 23) {
                        salonSales_If_sold_out = strOrderItems[23];
                    }

                    // ------------------------------------------------------------------------------------------------------------------

                    String temp_saveType = GlobalMemberValues.getSaveType(salonSalesDetailWebSaveType);

                    String temp_itemIdx = salonSalesDetailWebItemIdx;

                    String temp_kitchenPrintYN = salonSalesDetailWebKitchenPrintYN;

                    /*String temp_kitchenPrintYN = "N";
                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                        temp_kitchenPrintYN = dbInit.dbExecuteReadReturnString(
                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                            temp_kitchenPrintYN = "N";
                        }
                    }*/

                    int salonSalesDetailStatus = GlobalMemberValues.getIntAtString(salonSalesDetailWebStatus);
                    String salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[0];
                    /**
                     if (salonSalesDetailStatus == 3) {
                     salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[1];
                     }
                     **/

                    // ReceiptJson 을 arraylist 에 저장한다.
                    mReceiptJsonArray.add(salonSalesIndex, salonSalesWebReceiptJson);

                    LinearLayout subNewLn = new LinearLayout(context);
                    subNewLn.setLayoutParams(subNewLnLayoutParams);
                    String salesCodeAndIdx = dbSalesCode + "||" + salonSalesDetailWebIdx + "||" + salonSalesDetailStatus + "||" + salonSalesIndex;
                    subNewLn.setTag(salesCodeAndIdx);
                    //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                    subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                    subNewLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));

                    LinearLayout subNewLn_in_title = new LinearLayout(context);
                    subNewLn_in_title.setLayoutParams(subNewLnLayoutParams);
                    subNewLn_in_title.setTag(salesCodeAndIdx);
                    subNewLn_in_title.setOrientation(LinearLayout.HORIZONTAL);
                    subNewLn_in_title.setBackgroundColor(Color.parseColor("#f5f5f5"));


                    // 선택한 주문의 수령방법에 따라 버튼을 visible, invisible 처리
                    // 210319 수정

                    String tempLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[0];
                    if (tempIsDeliveringButtonVisible) {
                        if (temporaryWebOrders.UseDcomYN.equals("Y")){
                            saleHistoryDeliveringButton.setVisibility(View.INVISIBLE);
                            saleHistoryNotifyDeliveryButton.setVisibility(View.VISIBLE);
                            saleHistoryPickupButton.setVisibility(View.INVISIBLE);
                        } else {
                            saleHistoryDeliveringButton.setVisibility(View.VISIBLE);
                            saleHistoryNotifyDeliveryButton.setVisibility(View.INVISIBLE);
                            saleHistoryPickupButton.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        saleHistoryPickupButton.setVisibility(View.VISIBLE);
                        saleHistoryDeliveringButton.setVisibility(View.INVISIBLE);
                        saleHistoryNotifyDeliveryButton.setVisibility(View.INVISIBLE);
                    }

                    mSelectedSalesCode = dbSalesCode;
                    mSelectedSalonSalesDetailIdx = salonSalesDetailWebIdx;
                    mSelectedSalonSalesDetailStatus = (salonSalesDetailStatus + "");
                    if (mSelectedSalonSalesDetailStatus == "3") {
                        tempLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[1];
                    }
                    mSelectedReceiptJsonArrayIndex = (salonSalesIndex + "");

                    if (mSelectedSalonSalesDetailLn != null) {
                        mSelectedSalonSalesDetailLn.setBackgroundColor(Color.parseColor(tempLnBackGroundColor));
                    }

                    GlobalMemberValues.logWrite("salesHistorySelected", "salesCode : " + mSelectedSalesCode + "\n");
                    GlobalMemberValues.logWrite("salesHistorySelected", "idx : " + mSelectedSalonSalesDetailIdx + "\n");
                    GlobalMemberValues.logWrite("salesHistorySelected", "status : " + mSelectedSalonSalesDetailStatus + "\n");
                    GlobalMemberValues.logWrite("mSelectedReceiptJson", "receiptJson Index : " + mSelectedReceiptJsonArrayIndex + "\n");


                    if (mPushOrderType.equals("T")) {
                        saleHistoryNotifyDeliveryButton.setVisibility(View.GONE);
                    }




                    // Kind (SaveType 0 : 서비스    1 : Product     2 : GiftCard)
                    TextView subNewTv1 = new TextView(context);
                    subNewTv1.setLayoutParams(subNewTvLayoutParams);
                    subNewTv1.setWidth(GlobalMemberValues.getDisplayWidth(context) / 27);
                    subNewTv1.setGravity(Gravity.CENTER);
                    String subSaveType = GlobalMemberValues.getSaveType(salonSalesDetailWebSaveType);
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
                        subNewTvReturn.setTextSize(f_fontsize + 2.0f);
//                        subNewTvReturn.setTextSize(GlobalMemberValues.globalAddFontSize() + subNewTvReturn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                        subNewTvReturn.setText("[RETURN]");
                        subNewTvReturn.setTextColor(Color.parseColor("#e8420b"));
                        subNewLn_in_title.addView(subNewTvReturn);

                        // 서비스 이름
                        TextView subNewTv2 = new TextView(context);
                        subNewTv2.setLayoutParams(subNewTvLayoutParams);
                        subNewTv2.setWidth((GlobalMemberValues.getDisplayWidth(context) * 2) / 12);
                        subNewTv2.setText(salonSalesDetailWebItemName);
                        subNewTv2.setTypeface(typeface_monserrat_semi_bold);
                        subNewTv2.setTextSize(f_fontsize);
                        subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        subNewLn_in_title.addView(subNewTv2);
                    } else {
                        // 서비스 이름
                        TextView subNewTv2 = new TextView(context);
                        subNewTv2.setLayoutParams(subNewTvLayoutParams);
                        subNewTv2.setWidth(GlobalMemberValues.getDisplayWidth(context) / 5);
                        subNewTv2.setText(salonSalesDetailWebItemName);
                        subNewTv2.setTypeface(typeface_monserrat_semi_bold);
                        subNewTv2.setTextSize(f_fontsize);
                        subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        subNewLn_in_title.addView(subNewTv2);
                    }

                    int tempItemQty = GlobalMemberValues.getIntAtString(salonSalesDetailWebQty);
                    //int tempItemQty = 0;
                    double tempItemPrice = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebSalesPrice);
                    double tempItemOptionprice = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebOptionPrice);
                    double tempItemAdditionalprice1 = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebAddtionalprice1);
                    double tempItemAdditionalprice2 = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebAddtionalprice2);
                    double tempItemTax = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebTax);

                    double tempSaleAmount = ((tempItemPrice + tempItemOptionprice + tempItemAdditionalprice1 + tempItemAdditionalprice2) * tempItemQty);
                    double tempTaxAmount = (tempItemTax * tempItemQty);
                    double tempSaleTaxAmount = tempSaleAmount + tempTaxAmount;

                    GlobalMemberValues.logWrite(TAG, "tempItemQty : " + tempItemQty + "\n");
                    GlobalMemberValues.logWrite(TAG, "tempItemPrice : " + tempItemPrice + "\n");
                    GlobalMemberValues.logWrite(TAG, "tempItemOptionprice : " + tempItemOptionprice + "\n");
                    GlobalMemberValues.logWrite(TAG, "tempItemTax : " + tempItemTax + "\n");

                    // 수량
                    TextView subNewTv3 = new TextView(context);
                    subNewTv3.setLayoutParams(subNewTvLayoutParams);
                    subNewTv3.setWidth(GlobalMemberValues.getDisplayWidth(context) / 23);
                    subNewTv3.setGravity(Gravity.CENTER);
                    subNewTv3.setPadding(5, 0, 5, 0);
                    subNewTv3.setText("" + tempItemQty);
                    subNewTv3.setTypeface(typeface_monserrat_semi_bold);
                    subNewTv3.setTextSize(f_fontsize);
                    subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    subNewLn_in_title.addView(subNewTv3);

                    // Amount (세금미포함)
                    TextView subNewTv4 = new TextView(context);
                    subNewTv4.setLayoutParams(subNewTvLayoutParams);
                    subNewTv4.setWidth(GlobalMemberValues.getDisplayWidth(context) / 15);
                    subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    subNewTv4.setPadding(0, 0, 5, 0);
                    subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble("" + tempSaleAmount));
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
                    subNewTv5.setText(onlinetypeStr);
                    subNewTv5.setTypeface(typeface_monserrat_semi_bold);
                    subNewTv5.setTextSize(f_fontsize);
                    subNewTv5.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    subNewLn_in_title.addView(subNewTv5);

                    // totalAmount
                    TextView subNewTv6 = new TextView(context);
                    subNewTv6.setLayoutParams(subNewTvLayoutParams);
                    subNewTv6.setWidth(GlobalMemberValues.getDisplayWidth(context) / 13);
                    subNewTv6.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    subNewTv6.setPadding(0, 0, 5, 0);
                    subNewTv6.setText(GlobalMemberValues.getCommaStringForDouble("" + tempSaleTaxAmount));
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
                    //if (GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(12), 1) == "3") {
                    //    tempBatchYN = "Y";
                    //}
                    subNewTv7.setTypeface(typeface_monserrat_semi_bold);
                    subNewTv7.setText(tempBatchYN);
                    subNewTv7.setTextSize(f_fontsize);
                    subNewTv7.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    subNewLn_in_title.addView(subNewTv7);
                    subNewLn.addView(subNewLn_in_title);

                    newLn.addView(subNewLn);

                    // 옵션 또는 추가사항이 있을 경우 아래에 보여준다. --------------------------------------------------------------------
                    String tempOptionTxt = salonSalesDetailWebOptionTxt;
                    String tempOptionPrice = salonSalesDetailWebOptionPrice;

                    String tempAdditionalTxt1 = salonSalesDetailWebAddtionalTxt1;
                    String tempAdditionalPrice1 = salonSalesDetailWebAddtionalprice1;

                    String tempAdditionalTxt2 = salonSalesDetailWebAddtionalTxt2;
                    String tempAdditionalPrice2 = salonSalesDetailWebAddtionalprice2;

                    if (!GlobalMemberValues.isStrEmpty(tempOptionTxt) ||
                            !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1) || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {

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
                    // kitchen memo
                    if (!GlobalMemberValues.isStrEmpty(salonSales_Customer_Memo)){
                        LinearLayout subOptionAddLn = new LinearLayout(context);
                        subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                        //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                        subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);
                        subOptionAddLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));

                        // Option & Additional 내용
                        TextView optionAddTv = new TextView(context);
                        optionAddTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        optionAddTv.setPadding(5, 0, 5, 0);
                        optionAddTv.setTypeface(typeface_monserrat_semi_bold);
                        optionAddTv.setText("[Kitchen memo] : ");
                        optionAddTv.setTextSize(f_fontsize);
                        optionAddTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        optionAddTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
                        subOptionAddLn.addView(optionAddTv);

                        // Option & Additional 가격
                        TextView optionAddPriceTv = new TextView(context);
                        optionAddPriceTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddPriceTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        optionAddPriceTv.setPadding(5, 0, 5, 0);
                        optionAddPriceTv.setTypeface(typeface_monserrat_regular);
                        optionAddPriceTv.setText(salonSales_Customer_Memo);
                        optionAddPriceTv.setTextSize(f_fontsize);
                        optionAddPriceTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        optionAddPriceTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                        subOptionAddLn.addView(optionAddPriceTv);

                        // Option & Additional 빈칸
                        TextView optionAddEmptyTv = new TextView(context);
                        optionAddEmptyTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddEmptyTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                        subOptionAddLn.addView(optionAddEmptyTv);

                        newLn.addView(subOptionAddLn);
                    }
                    if (!GlobalMemberValues.isStrEmpty(salonSales_If_sold_out)){
                        LinearLayout subOptionAddLn = new LinearLayout(context);
                        subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                        //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                        subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);
                        subOptionAddLn.setBackgroundColor(Color.parseColor(salonSalesDetailLnBackGroundColor));

                        // Option & Additional 내용
                        TextView optionAddTv = new TextView(context);
                        optionAddTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        optionAddTv.setPadding(5, 0, 5, 0);
                        optionAddTv.setTypeface(typeface_monserrat_semi_bold);
                        optionAddTv.setText("[If sold out] : ");
                        optionAddTv.setTextSize(f_fontsize);
                        optionAddTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        optionAddTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
                        subOptionAddLn.addView(optionAddTv);

                        // Option & Additional 가격
                        TextView optionAddPriceTv = new TextView(context);
                        optionAddPriceTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddPriceTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        optionAddPriceTv.setPadding(5, 0, 5, 0);
                        optionAddPriceTv.setTypeface(typeface_monserrat_regular);
                        optionAddPriceTv.setText(salonSales_If_sold_out);
                        optionAddPriceTv.setTextSize(f_fontsize);
                        optionAddPriceTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        optionAddPriceTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                        subOptionAddLn.addView(optionAddPriceTv);

                        // Option & Additional 빈칸
                        TextView optionAddEmptyTv = new TextView(context);
                        optionAddEmptyTv.setLayoutParams(subNewTvLayoutParams);
                        optionAddEmptyTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                        subOptionAddLn.addView(optionAddEmptyTv);

                        newLn.addView(subOptionAddLn);
                    }



/*                    if (temp_saveType.equals("S") && temp_kitchenPrintYN.equals("Y")) {
                        String salonSalesDetailWebItemName_option = salonSalesDetailWebItemName +
                                "-ANNIETTASU-" + salonSalesDetailWebOptionTxt +
                                "-ANNIETTASU-" + salonSalesDetailWebAddtionalTxt1 +
                                "-ANNIETTASU-" + salonSalesDetailWebAddtionalTxt2 +
                                "-ANNIETTASU-" + temp_itemIdx;

                        GlobalMemberValues.logWrite("kitchenPrintLogWanhayeprinttxt", "salonSalesDetailWebItemName_option : " + salonSalesDetailWebItemName_option + "\n");

                        if (saleItemsCount == 0) {
                            saleItemsTxt = salonSalesDetailWebItemName_option + "-JJJ-" + tempItemQty;
                        } else {
                            saleItemsTxt = saleItemsTxt + "-WANHAYE-" + salonSalesDetailWebItemName_option + "-JJJ-" + tempItemQty;
                        }
                        saleItemsCount++;
                    }
*/

                    if (temp_saveType.equals("S") && temp_kitchenPrintYN.equals("Y")) {
                        //String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);
                        String tempKitchenPrinterNumber = salonSalesDetailWebKitchenPrinterNum;

                        String saleitem_name_optionadd = salonSalesDetailWebItemName +
                                "-ANNIETTASU-" + tempOptionTxt +
                                "-ANNIETTASU-" + tempAdditionalTxt1 +
                                "-ANNIETTASU-" + tempAdditionalTxt2 +
                                "-ANNIETTASU-" + temp_itemIdx +
                                "-ANNIETTASU-" + tempKitchenPrinterNumber +
                                "-ANNIETTASU-" + "nokitchenmemo" +
                                "-ANNIETTASU-" + salonSales_Customer_Memo +
                                "-ANNIETTASU-" + salonSales_If_sold_out;

                        String temp_kitchenprintnum = "0";
                        temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                        String item_categoryName1 = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select servicename from salon_storeservice_main " +
                                        " where idx in (select midx from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ) ");
                        String item_categoryName2 = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select servicename2 from salon_storeservice_main " +
                                        " where idx in (select midx from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ) ");

                        String item_categoryName = item_categoryName1;
                        if (!GlobalMemberValues.isStrEmpty(item_categoryName1)) {
                            item_categoryName += " " + item_categoryName2;
                        }

                        if (saleItemsCount == 0) {
                            saleItemsTxt = saleitem_name_optionadd + "-JJJ-" + salonSalesDetailWebQty +
                                    "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + item_categoryName;
                        } else {
                            saleItemsTxt = saleItemsTxt + "-WANHAYE-"
                                    + saleitem_name_optionadd + "-JJJ-" + salonSalesDetailWebQty +
                                    "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + item_categoryName;
                        }

                        GlobalMemberValues.logWrite("saleItemsTxtlog", "saleItemsTxt : " + saleItemsTxt + "\n");

                        saleItemsCount++;
                    }
                }
                // ----------------------------------------------------------------------------------------------------------------------------------

                // salon_sales_tip 데이터 출력 ---------------------------------------------------------------------------------------------------
                /**
                 String salonSalesTipQuery = "";
                 salonSalesTipQuery = "select idx, employeeIdx, employeeName, usedCash, usedCard " +
                 " from salon_sales_tip " +
                 " where salesCode = '" + dbSalesCode + "' " +
                 " order by employeeName asc";
                 GlobalMemberValues.logWrite("salonSalesTip", "salonSalesTipQuery  : " + salonSalesTipQuery + "\n");
                 salonSalesTipCursor = dbInit.dbExecuteRead(salonSalesTipQuery);
                 String strEmpTip = "";
                 while (salonSalesTipCursor.moveToNext()) {
                 String strEmpName = GlobalMemberValues.getDBTextAfterChecked(salonSalesTipCursor.getString(2), 1);

                 String cashTip = "";
                 String strCashTip = GlobalMemberValues.getDBTextAfterChecked(salonSalesTipCursor.getString(3), 1);
                 if (!GlobalMemberValues.isStrEmpty(strCashTip) && GlobalMemberValues.getDoubleAtString(strCashTip) > 0) {
                 cashTip = " CASH : " + GlobalMemberValues.getCommaStringForDouble(strCashTip) + "  ";
                 }

                 String cardTip = "";
                 String strCardTip = GlobalMemberValues.getDBTextAfterChecked(salonSalesTipCursor.getString(4), 1);
                 if (!GlobalMemberValues.isStrEmpty(strCardTip) && GlobalMemberValues.getDoubleAtString(strCardTip) > 0) {
                 cardTip = " CARD : " + GlobalMemberValues.getCommaStringForDouble(strCardTip) + "  ";
                 }

                 if (!GlobalMemberValues.isStrEmpty(cashTip) || !GlobalMemberValues.isStrEmpty(cardTip)) {
                 strEmpTip = "[" + strEmpName + "] " + cashTip + cardTip + "     ";
                 }

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
                 tipNewTv1.setText("--- Tip Info ---\n" + strEmpTip);
                 tipNewTv1.setTextSize(f_fontsize);
                 tipNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                 tipLn1.addView(tipNewTv1);

                 newLn.addView(tipLn1);
                 }
                 }
                 **/
                // ------------------------------------------------------------------------------------------------------------------


                String cashTip = "";
                String strCashTip = "";
                String strCardTip = "";
                String strEmpTip = "";
                String customername = "";
                try {
                    JSONObject jsonObject_receiptjson = new JSONObject(salonSalesWebReceiptJson);
                    if (jsonObject_receiptjson.toString().contains("tipcash")) {
                        strCashTip = (String) jsonObject_receiptjson.get("tipcash");
                    }
                    if (jsonObject_receiptjson.toString().contains("tipcard")) {
                        strCardTip = (String) jsonObject_receiptjson.get("tipcard");
                    }
                    if (jsonObject_receiptjson.toString().contains("customername")) {
                        customername = (String) jsonObject_receiptjson.get("customername");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!GlobalMemberValues.isStrEmpty(strCashTip) && GlobalMemberValues.getDoubleAtString(strCashTip) > 0) {
                    cashTip = " CASH : " + GlobalMemberValues.getCommaStringForDouble(strCashTip) + "  ";
                }

                String cardTip = "";
                if (!GlobalMemberValues.isStrEmpty(strCardTip) && GlobalMemberValues.getDoubleAtString(strCardTip) > 0) {
                    cardTip = " CARD : " + GlobalMemberValues.getCommaStringForDouble(strCardTip) + "  ";
                }

                if (!GlobalMemberValues.isStrEmpty(cashTip) || !GlobalMemberValues.isStrEmpty(cardTip)) {
                    strEmpTip = "[" + customername + "] " + cashTip + cardTip + "     ";
                }

                if (!GlobalMemberValues.isStrEmpty(strEmpTip)) {
                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams tipLnLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    tipLnLayoutParams1.setMargins(0, 0, 0, 0);

                    LinearLayout tipLn1 = new LinearLayout(context);
                    tipLn1.setLayoutParams(tipLnLayoutParams1);
                    tipLn1.setOrientation(LinearLayout.HORIZONTAL);
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
                    tipNewTv1.setText("Tip Info\n" + strEmpTip);
                    tipNewTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    tipNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    tipLn1.addView(tipNewTv1);

                    newLn.addView(tipLn1);
                }



                // 고객정보 출력 ---------------------------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(salonSalesWebCustomerName)) {
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
//                    customerIdNewTv.setBackgroundColor(Color.parseColor("#f5f5f5"));
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
                    customerIdValueNewTv.setText(salonSalesWebCustomerId);
                    customerIdValueNewTv.setTypeface(typeface_monserrat_regular);
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
                    customerNameValueNewTv.setText(salonSalesWebCustomerName);
                    customerNameValueNewTv.setTypeface(typeface_monserrat_regular);
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
                    customerPhoneValueNewTv.setText(salonSalesWebCustomerPhone);
                    customerPhoneValueNewTv.setTypeface(typeface_monserrat_regular);
                    customerPhoneValueNewTv.setTextSize(f_fontsize);
                    customerPhoneValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerPhoneValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f));
                    customerValueLn2.addView(customerPhoneValueNewTv);

                    // 04.20.2022 추가
                    setToPhoneNum = salonSalesWebCustomerPhone;

//                    TextView customer_dummy = new TextView(context);
//                    customer_dummy.setLayoutParams(tipNewTvLayoutParams2);
//                    customer_dummy.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                    customer_dummy.setPadding(5, 5, 5, 5);
//                    customer_dummy.setTypeface(typeface_monserrat_regular);
////                    customer_dummy.setText(GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(12), 1));
//                    customer_dummy.setTextSize(f_fontsize);
//                    customer_dummy.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
//                    customer_dummy.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//                    customer_dummy.setVisibility(View.INVISIBLE);
//                    customerValueLn2.addView(customer_dummy);

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

                // 배송정보 출력 (배달일경우) -----------------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(salonSalesWebDeliveryTakeaway) && salonSalesWebDeliveryTakeaway.equals("D")) {
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
                    deliveryRequestDateValueTv.setText(salonSalesWebDeliveryDate);
                    deliveryRequestDateValueTv.setTypeface(typeface_monserrat_regular);
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
//                    addressValueTv.setText(
//                            salonSalesWebCustomerAddr1 + " " + salonSalesWebCustomerAddr2 + ", " + salonSalesWebCustomerCity + ", "
//                                    + salonSalesWebCustomerState + salonSalesWebCustomerZip );
                    addressValueTv.setText(salonSalesWebCustomerAddressAll);
                    addressValueTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    addressValueTv.setTextColor(Color.parseColor("#1f71ba"));
                    addressValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                    deliveryAddrValueLn1.addView(addressValueTv);

                    newLn.addView(deliveryAddrValueLn1);
                    // ---------------------------------------------------------------------------------------------------------
                }
                // ------------------------------------------------------------------------------------------------------------------

                // 배송정보 출력 (Pick Up 일 경어) -----------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(salonSalesWebDeliveryTakeaway) && salonSalesWebDeliveryTakeaway.equals("T")) {
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
                if (!GlobalMemberValues.isStrEmpty(salonSalesWebCustomermemo)) {
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

                    // 배송정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                    LinearLayout customermemoValueLn1 = new LinearLayout(context);
                    customermemoValueLn1.setLayoutParams(customerLnLayoutParams1);
                    customermemoValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                    customermemoValueLn1.setBackgroundResource(R.drawable.roundlayout_void_round);
//                    customermemoValueLn1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    // 배송타입
                    TextView customermemoValueTv = new TextView(context);
                    customermemoValueTv.setLayoutParams(tipNewTvLayoutParams2);
                    customermemoValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customermemoValueTv.setPadding(5, 5, 5, 5);
                    customermemoValueTv.setText(salonSalesWebCustomermemo);
                    customermemoValueTv.setTypeface(typeface_monserrat_regular);
                    customermemoValueTv.setTextSize(f_fontsize);
                    customermemoValueTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customermemoValueTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    customermemoValueLn1.addView(customermemoValueTv);

                    newLn.addView(customermemoValueLn1);
                    // ---------------------------------------------------------------------------------------------------------
                }
                // ------------------------------------------------------------------------------------------------------------------

//jihun 063020
                // 배달자 메모 ---------------------------------------------------------------------------------------------------------
                if (carInfo_License.equals("") &&
                        carInfo_Color.equals("") &&
                        carInfo_Type.equals("") &&
                        carInfo_ParkingSpace.equals("")) {

                }else {
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
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);


                    // 고객정보 타이틀용 -------------------------------------------------------------------------------------------
                    LinearLayout customerTitleLn1 = new LinearLayout(context);
                    customerTitleLn1.setLayoutParams(customerLnLayoutParams1);
                    customerTitleLn1.setOrientation(LinearLayout.HORIZONTAL);
                    //customerTitleLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    //
                    TextView customerIdNewTv = new TextView(context);
                    customerIdNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    //customerIdNewTv.setWidth(GlobalMemberValues.getDisplayWidth(context) / 18);
                    customerIdNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdNewTv.setPadding(5, 5, 5, 5);
                    customerIdNewTv.setTypeface(typeface_monserrat_bold);
                    customerIdNewTv.setText("Customer Arrive");
                    customerIdNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerTitleLn1.addView(customerIdNewTv);


                    // confirm button
                    Button customerArriveConfirm = new Button(context);
                    customerArriveConfirm.setLayoutParams(tipNewTvLayoutParams2);
                    customerArriveConfirm.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    customerArriveConfirm.setPadding(5, 5, 5, 5);
                    customerArriveConfirm.setTypeface(typeface_monserrat_regular);
                    customerArriveConfirm.setText("Arrival Confirm");
                    customerArriveConfirm.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerArriveConfirm.setTextColor(Color.parseColor("#ffffff"));
                    customerArriveConfirm.setBackgroundColor(Color.parseColor("#1356ff"));
                    customerTitleLn1.addView(customerArriveConfirm);
                    customerArriveConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new AlertDialog.Builder(context)
                                    .setTitle("Arrival confirm")
                                    .setMessage("Arrival confirm?\n[Sale Group# : " + mPushSalesCode + "]")
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String curbsideConfirmString = "";
                                                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                                        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                                                            API_weborder_customer_arrive_confirm api_weborder_customer_arrive_confirm = new API_weborder_customer_arrive_confirm(GlobalMemberValues.STORE_INDEX, mPushSalesCode);
                                                            try {
                                                                curbsideConfirmString = api_weborder_customer_arrive_confirm.execute(null, null, null).get().toString();
                                                                if (curbsideConfirmString.equals("00")){
                                                                    // 성공
                                                                    GlobalMemberValues.displayDialog(context, "Arrival confirm", "confirm success", "Close");
                                                                    new AlertDialog.Builder(context)
                                                                            .setTitle("Arrival confirm")
                                                                            .setMessage("confirm success")
                                                                            //.setIcon(R.drawable.ic_launcher)
                                                                            .setPositiveButton("Close",
                                                                                    new DialogInterface.OnClickListener() {
                                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                                            //
                                                                                            dialog.dismiss();
                                                                                            handler_refrash.sendEmptyMessage(0);
                                                                                        }
                                                                                    })
                                                                            .show();

                                                                } else {
                                                                    GlobalMemberValues.displayDialog(context, "Arrival confirm", "confirm fail", "Close");
                                                                }
                                                            } catch (ExecutionException e) {
                                                                e.printStackTrace();
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            if (api_weborder_customer_arrive_confirm.mFlag) {
                                                                curbsideConfirmString = api_weborder_customer_arrive_confirm.mReturnValue;

                                                            }
//                                                            try {
//                                                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//
//                                                            } catch (InterruptedException e) {
//                                                                GlobalMemberValues.logWrite("newCursidesrt confirm error", "Thread Error : " + e.getMessage() + "\n");
//                                                                //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
//                                                            }
                                                        }
                                                    }

                                                    GlobalMemberValues.logWrite("newCursidesrt confirm error", curbsideConfirmString + "\n");
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    })
                                    .show();
                        }
                    });


                    newLn.addView(customerTitleLn1);
                    // --------------------------------------------------------------------------------------------------------------

                    // 고객정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
//                    LinearLayout customerValueLn1 = new LinearLayout(context);
//                    customerValueLn1.setLayoutParams(customerLnLayoutParams1);
//                    customerValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                    //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

                    LinearLayout customerLn_back_All = new LinearLayout(context);
                    customerLn_back_All.setLayoutParams(tipNewTvLayoutParams1);
                    customerLn_back_All.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout customerLn_carinfo = new LinearLayout(context);
                    customerLn_carinfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    customerLn_carinfo.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout customerLn_back_1 = new LinearLayout(context);
                    customerLn_back_1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                    customerLn_back_1.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout customerValueLn2 = new LinearLayout(context);
                    customerValueLn2.setLayoutParams(customerLnLayoutParams1);
                    customerValueLn2.setOrientation(LinearLayout.HORIZONTAL);
                    // 고객 도착시간
                    TextView customerNameTitleNewTv = new TextView(context);
                    customerNameTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerNameTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerNameTitleNewTv.setPadding(5, 5, 5, 5);
                    customerNameTitleNewTv.setTypeface(typeface_monserrat_regular);
                    customerNameTitleNewTv.setText("Arrive Time : ");
                    customerNameTitleNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerNameTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerNameTitleNewTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn2.addView(customerNameTitleNewTv);

                    TextView customerNameValueNewTv = new TextView(context);
                    customerNameValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerNameValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerNameValueNewTv.setPadding(5, 5, 5, 5);
                    customerNameValueNewTv.setTypeface(typeface_monserrat_regular);
                    customerNameValueNewTv.setText(curbside_calltime);
                    customerNameValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerNameValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerNameValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn2.addView(customerNameValueNewTv);

                    customerLn_back_1.addView(customerValueLn2);

                    String[] temp_curbside_carinfo = curbside_carinfo.split("-TAJ-");
                    // 0 : car license , 1: car color , 2: car type, 3: parking space #


                    LinearLayout customerValueLn1 = new LinearLayout(context);
                    customerValueLn1.setLayoutParams(customerLnLayoutParams1);
                    customerValueLn1.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout customerLn_back_1_1 = new LinearLayout(context);
                    customerLn_back_1_1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                    customerLn_back_1_1.setOrientation(LinearLayout.VERTICAL);

                    // 고객 차량 정보 표시
                    TextView customerIdTitleNewTv = new TextView(context);
                    customerIdTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdTitleNewTv.setPadding(5, 5, 5, 5);
                    customerIdTitleNewTv.setTypeface(typeface_monserrat_regular);
                    customerIdTitleNewTv.setText("Car License # : ");
                    customerIdTitleNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdTitleNewTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn1.addView(customerIdTitleNewTv);


                    TextView customerIdValueNewTv1 = new TextView(context);
                    customerIdValueNewTv1.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdValueNewTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdValueNewTv1.setPadding(5, 5, 5, 5);
                    customerIdValueNewTv1.setTypeface(typeface_monserrat_regular);
                    customerIdValueNewTv1.setText(carInfo_License);
                    customerIdValueNewTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdValueNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdValueNewTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn1.addView(customerIdValueNewTv1);


                    customerLn_back_1_1.addView(customerValueLn1);



                    LinearLayout customerValueLn3 = new LinearLayout(context);
                    customerValueLn3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,3));
                    customerValueLn3.setOrientation(LinearLayout.HORIZONTAL);



                    LinearLayout customerLn_back_2 = new LinearLayout(context);
                    customerLn_back_2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                    customerLn_back_2.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout customerValueLn1_2 = new LinearLayout(context);
                    customerValueLn1_2.setLayoutParams(customerLnLayoutParams1);
                    customerValueLn1_2.setOrientation(LinearLayout.HORIZONTAL);


                    // 고객 차량 정보 표시
                    TextView customerIdTitleNewTv2 = new TextView(context);
                    customerIdTitleNewTv2.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdTitleNewTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdTitleNewTv2.setPadding(5, 5, 5, 5);
                    customerIdTitleNewTv2.setTypeface(typeface_monserrat_regular);
                    customerIdTitleNewTv2.setText("Car Color : ");
                    customerIdTitleNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdTitleNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdTitleNewTv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn1_2.addView(customerIdTitleNewTv2);


                    String str_carcolor = "";
                    switch (carInfo_Color) {
                        case "#ffffff" :
                            str_carcolor = "WHITE";
                            break;
                        case "#000000" :
                            str_carcolor = "BLACK";
                            break;
                        case "#dadada" :
                            str_carcolor = "SILVER";
                            break;
                        case "#585858" :
                            str_carcolor = "GRAY";
                            break;
                        case "#aba574" :
                            str_carcolor = "GOLD";
                            break;
                        case "#fe1414" :
                            str_carcolor = "RED";
                            break;
                        case "#2471ff" :
                            str_carcolor = "BLUE";
                            break;
                        case "#139c00" :
                            str_carcolor = "GREEN";
                            break;
                    }
                    if (str_carcolor.equals("")) str_carcolor = "OTHER";

                    TextView customerIdValueNewTv2_colortxt = new TextView(context);
                    customerIdValueNewTv2_colortxt.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdValueNewTv2_colortxt.setPadding(5, 5, 5, 5);
                    customerIdValueNewTv2_colortxt.setTypeface(typeface_monserrat_regular);
                    customerIdValueNewTv2_colortxt.setText(str_carcolor);
                    customerIdValueNewTv2_colortxt.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdValueNewTv2_colortxt.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdValueNewTv2_colortxt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float)1.7));
                    customerValueLn1_2.addView(customerIdValueNewTv2_colortxt);

                    TextView customerIdValueNewTv2 = new TextView(context);
                    customerIdValueNewTv2.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdValueNewTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdValueNewTv2.setPadding(5, 5, 5, 5);
                    customerIdValueNewTv2.setTypeface(typeface_monserrat_regular);
                    customerIdValueNewTv2.setText("                                 ");
                    customerIdValueNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdValueNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdValueNewTv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    customerIdValueNewTv2.setBackgroundColor(Color.parseColor(carInfo_Color));

                    LinearLayout ln_customerIdValue02_back = new LinearLayout(context);
                    ln_customerIdValue02_back.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.3));
                    ln_customerIdValue02_back.setPadding(1,1,1,1);
                    ln_customerIdValue02_back.setBackgroundColor(Color.BLACK);
                    ln_customerIdValue02_back.addView(customerIdValueNewTv2);

                    customerValueLn1_2.addView(ln_customerIdValue02_back);


                    customerLn_back_2.addView(customerValueLn1_2);

                    LinearLayout customerLn_back_3 = new LinearLayout(context);
                    customerLn_back_3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                    customerLn_back_3.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout customerValueLn1_3 = new LinearLayout(context);
                    customerValueLn1_3.setLayoutParams(customerLnLayoutParams1);
                    customerValueLn1_3.setOrientation(LinearLayout.HORIZONTAL);

                    TextView customerIdTitleNewTv3 = new TextView(context);
                    customerIdTitleNewTv3.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdTitleNewTv3.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdTitleNewTv3.setPadding(5, 5, 5, 5);
                    customerIdTitleNewTv3.setTypeface(typeface_monserrat_regular);
                    customerIdTitleNewTv3.setText("Car Type : ");
                    customerIdTitleNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdTitleNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdTitleNewTv3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn1_3.addView(customerIdTitleNewTv3);

                    TextView customerIdValueNewTv3 = new TextView(context);
                    customerIdValueNewTv3.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdValueNewTv3.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdValueNewTv3.setPadding(5, 5, 5, 5);
                    customerIdValueNewTv3.setTypeface(typeface_monserrat_regular);
                    customerIdValueNewTv3.setText(carInfo_Type);
                    customerIdValueNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
                    customerIdValueNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                    customerIdValueNewTv3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    customerValueLn1_3.addView(customerIdValueNewTv3);

                    customerLn_back_3.addView(customerValueLn1_3);

                    customerLn_carinfo.addView(customerLn_back_1);
                    customerLn_carinfo.addView(customerLn_back_1_1);
                    customerLn_carinfo.addView(customerLn_back_2);
                    customerLn_carinfo.addView(customerLn_back_3);

                    if (carInfo_ParkingSpace != null){
                        LinearLayout customerLn_back_4 = new LinearLayout(context);
                        customerLn_back_4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                        customerLn_back_4.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout customerValueLn1_4 = new LinearLayout(context);
                        customerValueLn1_4.setLayoutParams(customerLnLayoutParams1);
                        customerValueLn1_4.setOrientation(LinearLayout.HORIZONTAL);

                        TextView customerIdTitleNewTv4 = new TextView(context);
                        customerIdTitleNewTv4.setLayoutParams(tipNewTvLayoutParams2);
                        customerIdTitleNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdTitleNewTv4.setPadding(5, 5, 5, 5);
                        customerIdTitleNewTv4.setTypeface(typeface_monserrat_bold);
                        customerIdTitleNewTv4.setText("Parking Sapce # : ");
                        customerIdTitleNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE + 2);
                        customerIdTitleNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerIdTitleNewTv4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        customerValueLn1_4.addView(customerIdTitleNewTv4);

                        TextView customerIdValueNewTv4 = new TextView(context);
                        customerIdValueNewTv4.setLayoutParams(tipNewTvLayoutParams2);
                        customerIdValueNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        customerIdValueNewTv4.setPadding(5, 5, 5, 5);
                        customerIdValueNewTv4.setTypeface(typeface_monserrat_bold);
                        customerIdValueNewTv4.setText(carInfo_ParkingSpace);
                        customerIdValueNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE + 2);
                        customerIdValueNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                        customerIdValueNewTv4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        customerValueLn1_4.addView(customerIdValueNewTv4);

                        customerLn_back_4.addView(customerValueLn1_4);
                        customerLn_carinfo.addView(customerLn_back_4);
                    }

                    customerLn_back_All.addView(customerLn_carinfo);
                    customerLn_back_All.addView(customerValueLn3);
                    newLn.addView(customerLn_back_All);
                    // ---------------------------------------------------------------------------------------------------------
                }
                // ------------------------------------------------------------------------------------------------------------------


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
                kitchenPrintedLn.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                kitchenPrintedLn.setBackgroundColor(Color.parseColor("#f0f0f0"));

//                LinearLayout kitchenPrintedLn_inln = new LinearLayout(context);
//                kitchenPrintedLn_inln.setLayoutParams(statusLnLayoutParams1);
//                kitchenPrintedLn_inln.setOrientation(LinearLayout.HORIZONTAL);
//                kitchenPrintedLn_inln.setPadding(15, 5, 5, 5);
//                kitchenPrintedLn_inln.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
//                kitchenPrintedLn_inln.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

                TextView kitchenPrintedTitleTv = new TextView(context);
                kitchenPrintedTitleTv.setLayoutParams(statusTvLayoutParams2);
                kitchenPrintedTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                kitchenPrintedTitleTv.setPadding(15, 5, 15, 5);
                kitchenPrintedTitleTv.setTypeface(typeface_monserrat_semi_bold);
                kitchenPrintedTitleTv.setText(Html.fromHtml(kitchenPrintedStr));
                kitchenPrintedTitleTv.setTextSize(f_fontsize);
                kitchenPrintedTitleTv.setTextColor(Color.parseColor(kitchenPrintedColorStr));
                if (salonSalesWebKitchenPrintedYN.equals("Y")) {
                    kitchenPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint2);
                } else {
                    kitchenPrintedTitleTv.setBackgroundResource(R.drawable.button_selector_kitchenprint3);
                }


                final String kpSalesCode = dbSalesCode;
                final String kpCustomerName = salonSalesWebCustomerName;
                final String kpDeliveryTakeaway = salonSalesWebDeliveryTakeaway;
                final String kpDeliveryDate = salonSalesWebDeliveryDate;
                final String kpCustomerMemo = salonSalesWebCustomermemo;
                final String kpSaleItems = saleItemsTxt;
                final String kpCustomerOrderNumber = "W" + salonSalesCustomerOrderNumber;

                kitchenPrintedTitleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 주방프린트 하기 --------------------------------------------------------------------
                        if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                                "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {

                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpSalesCode + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + salonSalesWebSaleDate + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerName + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpSaleItems + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpDeliveryTakeaway + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpDeliveryDate + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerMemo + "\n");
                            GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerOrderNumber + "\n");


                            JSONObject jsonroot_kitchen = new JSONObject();
                            try {
                                jsonroot_kitchen.put("receiptno", kpSalesCode);
                                jsonroot_kitchen.put("saledate", salonSalesWebSaleDate);
                                jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                                jsonroot_kitchen.put("customername", kpCustomerName);
                                jsonroot_kitchen.put("saleitemlist", kpSaleItems);
                                jsonroot_kitchen.put("deliverytakeaway", kpDeliveryTakeaway);
                                jsonroot_kitchen.put("deliverydate", kpDeliveryDate);
                                jsonroot_kitchen.put("ordertype", "WEB");
                                jsonroot_kitchen.put("customermemo", kpCustomerMemo);
                                //jsonroot_kitchen.put("reprint", "Y");
                                jsonroot_kitchen.put("customerordernumber", kpCustomerOrderNumber);
                                jsonroot_kitchen.put("customerpagernumber", "");

                                // 10112023
                                jsonroot_kitchen.put("orderfrom",mPushOrderfrom);
                                jsonroot_kitchen.put("salescodethirdparty",mPushSalescodethirdparty);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

                            rePrintInKitchen(jsonroot_kitchen, kpSalesCode);
                        }
                        // -----------------------------------------------------------------------------------
                    }
                });
//                kitchenPrintedLn_inln.addView(kitchenPrintedTitleTv);

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
                    if (salonSalesWebKitchenPrintedYN.equals("Y")) {
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
                            LabelprintReceipt(salonSalesWebReceiptJson,mSelectedSalesCode);
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
                custCount++;
            }
        }

        mSearchDeliveryTakeaway = "All";
    }

    public static void LabelprintReceipt(String str_json, String paramSalesCode) {
        GlobalMemberValues.logWrite("reprintlog", "여기실행이오.. " + "\n");

        if (paramSalesCode == null) return;
        if (paramSalesCode.isEmpty()) return;

        JSONObject jsonroot = null;
        if (!GlobalMemberValues.isStrEmpty(str_json)) {
            try {
                jsonroot = new JSONObject(str_json);
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
//                            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//                            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");
                    GlobalMemberValues.printLabel1to5(temp_array);
                }
            }
        }
    }

    public static void rePrintInKitchen(final JSONObject paramJson, String paramSalesCode) {
        new AlertDialog.Builder(context)
                .setTitle("Kitchen Printing")
                .setMessage("Are you sure you want to reprint in kitchen?\n[Sale Group# : " + paramSalesCode + "]")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!GlobalMemberValues.isStrEmpty(paramJson.toString())) {
                                    GlobalMemberValues.mRekitchenprintYN = "Y";
                                    GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "Y";
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

    public ArrayList<String> getCreditCardItemsArrayListForIngenico() {
        ArrayList<String> returnArrayRefNumbers = new ArrayList<String>();

        String selectedSalesCode = "";
        selectedSalesCode = dbInit.dbExecuteReadReturnString(
                "select salesCode from salon_sales_detail where idx = '" + mSelectedSalonSalesDetailIdx + "' ");
        String salonSalesCardQuery = "";
        // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
        salonSalesCardQuery = "select idx, cardRefNumber " +
                " from salon_sales_card " +
                " where salesCode = '" + selectedSalesCode + "' " +
                " and not(cardCom = 'OFFLINECARD') ";
        Cursor salonSalesCardCursor;
        salonSalesCardCursor = dbInit.dbExecuteRead(salonSalesCardQuery);
        while (salonSalesCardCursor.moveToNext()) {
            String salonSalesCard_idx = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(0), 1);
            String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);

            returnArrayRefNumbers.add(salonSalesCard_cardRefNumber);
        }
        //setCreditCardProcessVoid(arrayRefNumbers);
        // --------------------------------------------------------------------------------------------------------------------------------------

        return returnArrayRefNumbers;
    }

    public static ArrayList<String> getCreditCardItemsArrayListForPax(String paramSelectedSalonSalesDetailIdx) {
        ArrayList<String> cardPaymentInfoArrayList = new ArrayList<String>();

        String selectedSalesCode = "";
        selectedSalesCode = dbInit.dbExecuteReadReturnString(
                "select salesCode from salon_sales_detail where idx = '" + paramSelectedSalonSalesDetailIdx + "' ");
        String salonSalesCardQuery = "";
        // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
        salonSalesCardQuery = "select idx, tid, cardRefNumber, priceAmount " +
                " from salon_sales_card " +
                " where salesCode = '" + selectedSalesCode + "' " +
                " and not(cardCom = 'OFFLINECARD') ";
        Cursor salonSalesCardCursor;
        salonSalesCardCursor = dbInit.dbExecuteRead(salonSalesCardQuery);
        while (salonSalesCardCursor.moveToNext()) {
            String salonSalesCard_idx = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(0), 1);
            String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);
            String salonSalesCard_tid = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(2), 1);
            String salonSalesCard_priceAmount = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(3), 1);

            String cardPaymentInfo = salonSalesCard_idx + "|||" + salonSalesCard_cardRefNumber + "|||" + salonSalesCard_tid + "|||" + salonSalesCard_priceAmount;
            cardPaymentInfoArrayList.add(cardPaymentInfo);
        }
        //setCreditCardProcessVoid(arrayRefNumbers);
        // --------------------------------------------------------------------------------------------------------------------------------------

        return cardPaymentInfoArrayList;
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
        saleHistoryCustomerSearchEditText.setText("");
        //selectedItemSpinner = "All";
        mSelectedSalesCode = "";
        mSelectedSalonSalesDetailIdx = "";
        mSelectedSalonSalesDetailLn = null;
        mCustomerValue = "";
        mSelectedSalonSalesDetailStatus = "";

        mTempFlag = 0;

        mSearchDeliveryTakeaway = "All";

        mApiReturnCode = "";

        GlobalMemberValues.mReReceiptprintYN = "N";
        GlobalMemberValues.mRekitchenprintYN = "N";

        curbside_carinfo = "";
        curbside_calltime = "";
        curbside_phonenum = "";
        curbside_ordernum = "";
    }

    public static void return_kitchenPrint(){
        for (int weborderi = 0; weborderi < mCollectionOrdersArrList.size(); weborderi++) {
            TemporaryWebOrders temporaryWebOrders = mCollectionOrdersArrList.get(weborderi);

            // salon_sales_web 테이블 정보 ----------------------------------------------------------------------------------
            String salonSalesWebIdx = temporaryWebOrders.idx;
            String salonSalesWebSalesCode = temporaryWebOrders.salesCode;

            String salonSalesWebCustomerId = temporaryWebOrders.customerId;
            String salonSalesWebCustomerName = temporaryWebOrders.customerName;
            String salonSalesWebCustomerPhone = temporaryWebOrders.customerPhone;
            String salonSalesWebCustomerEmail = temporaryWebOrders.customerEmail;
            String salonSalesWebCustomerAddr1 = temporaryWebOrders.customerAddr1;
            String salonSalesWebCustomerAddr2 = temporaryWebOrders.customerAddr2;
            String salonSalesWebCustomerCity = temporaryWebOrders.customerCity;
            String salonSalesWebCustomerState = temporaryWebOrders.customerState;
            String salonSalesWebCustomerZip = temporaryWebOrders.customerZip;

            String salonSalesWebDeliveryDay = temporaryWebOrders.deliveryday;
            String salonSalesWebDeliveryTime = temporaryWebOrders.deliverytime;
            String salonSalesWebDeliveryDate = temporaryWebOrders.deliverydate;

            String salonSalesWebCustomermemo = temporaryWebOrders.customermemo;

            String salonSalesWebCouponNumber = temporaryWebOrders.couponnumber;
            String salonSalesWebGiftcardNumberUsed = temporaryWebOrders.giftcardNumberUsed;

            String salonSalesWebDiscount = temporaryWebOrders.discount;
            String salonSalesWebDiscountTxt = temporaryWebOrders.discountTxt;

            String salonSalesWebGiftcardPriceUsed = temporaryWebOrders.giftcardPriceUsed;
            String salonSalesWebCardPriceUsed = temporaryWebOrders.CardPriceUsed;
            String salonSalesWebCashPriceUsed = temporaryWebOrders.CashPriceUsed;
            String salonSalesWebPointPriceUsed = temporaryWebOrders.PointPriceUsed;

            String salonSalesWebDeliveryTakeaway = temporaryWebOrders.deliverytakeaway;
            String salonSalesWebDeliveryStatus = temporaryWebOrders.DeliveryStatus;
            String salonSalesWebTakeawayStatus = temporaryWebOrders.TakeawayStatus;
            String salonSalesWebPickupdone = temporaryWebOrders.Pickupdone;

            String salonSalesWebReceiptJson = temporaryWebOrders.ReceiptJson;
            String salonSalesWebSaleDate = temporaryWebOrders.SaleDate;
            String salonSalesWebStatus = temporaryWebOrders.Status;

            String salonSalesWebSaleItems = temporaryWebOrders.SalesItems;

            String salonSalesWebKitchenPrintedYN = temporaryWebOrders.KitchenprintedYN;

            String salonSalesDeliveryPickupFee = temporaryWebOrders.DeliveryPickupFee;

            String salonSalesCustomerOrderNumber = temporaryWebOrders.CustomerOrderNumber;

            // ------------------------------------------------------------------------------------------------------------

            String dbSalesCode = salonSalesWebSalesCode;
            // 세일코드값이 있을 경우에만..
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(dbSalesCode, " ", ""))) {
                // 세일 상태
                int salonSalesStatus = GlobalMemberValues.getIntAtString(salonSalesWebStatus);

                String tempCashStr = "";
                String tempCardStr = "";
                String tempGiftCardStr = "";
                String tempCheckStr = "";
                String tempPointStr = "";
                String tempChangeStr = "";

                String tempSpaceStr = "        ";

                String tempUseCash      = salonSalesWebCashPriceUsed;
                String tempUseCard      = salonSalesWebCardPriceUsed;
                String tempUseGiftCard  = salonSalesWebGiftcardPriceUsed;
                String tempUseCheck     = "0";
                String tempUsePoint     = salonSalesWebPointPriceUsed;
                String tempChangeMoney  = "0";

                String tempTotalDiscountExtraPrice = "0";
                double tempDoubleTotalDcExtra = GlobalMemberValues.getDoubleAtString(tempTotalDiscountExtraPrice);

                String tempDiscountPrice  = "0";
                double tempDoubleDiscountPrice = GlobalMemberValues.getDoubleAtString(tempDiscountPrice);
                String tempExtraPrice  = "0";
                double tempDoubleExtraPrice = GlobalMemberValues.getDoubleAtString(tempExtraPrice);

                String tempAllDiscountExtraPrice  = salonSalesWebDiscount;
                double tempDoubleAllDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(tempAllDiscountExtraPrice);
                String tempAllDiscountExtraStr  = salonSalesWebDiscountTxt;

                double tempCashPlusChange
                        = GlobalMemberValues.getDoubleAtString(tempUseCash) + GlobalMemberValues.getDoubleAtString(tempChangeMoney);

                /*************************************************************************************************************/


                mReceiptJsonArray = new ArrayList<String>();

                String saleItemsTxt = "";
                String[] strOrderItemsList = salonSalesWebSaleItems.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                int saleItemsCount = 0;
                for (int salonSalesIndex = 0; salonSalesIndex < strOrderItemsList.length; salonSalesIndex++) {
                    String[] strOrderItems = strOrderItemsList[salonSalesIndex].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                    // salon_sales_detail_web 테이블 정보 --------------------------------------------------------------------------------
                    String salonSalesDetailWebIdx = strOrderItems[0];
                    String salonSalesDetailWebItemIdx = strOrderItems[1];
                    String salonSalesDetailWebItemName = strOrderItems[2];
                    String salonSalesDetailWebItemFilePath = strOrderItems[3];
                    String salonSalesDetailWebOptionTxt = strOrderItems[4];
                    String salonSalesDetailWebQty = strOrderItems[5];
                    String salonSalesDetailWebSalesOrgPrice = strOrderItems[6];
                    String salonSalesDetailWebSalesPrice = strOrderItems[7];
                    String salonSalesDetailWebOptionPrice = strOrderItems[8];
                    String salonSalesDetailWebTax = strOrderItems[9];
                    String salonSalesDetailWebIsSale = strOrderItems[10];
                    String salonSalesDetailWebParentSalesCode = strOrderItems[11];
                    String salonSalesDetailWebParentSalesIdx = strOrderItems[12];
                    String salonSalesDetailWebReturnCode = strOrderItems[13];
                    String salonSalesDetailWebStatus = strOrderItems[14];
                    String salonSalesDetailWebSaveType = strOrderItems[15];
                    String salonSalesDetailWebAddtionalTxt1 = strOrderItems[16];
                    String salonSalesDetailWebAddtionalTxt2 = strOrderItems[17];
                    String salonSalesDetailWebAddtionalprice1 = strOrderItems[18];
                    String salonSalesDetailWebAddtionalprice2 = strOrderItems[19];

                    String salonSalesDetailWebKitchenPrintYN = "N";
                    if (strOrderItems.length > 20) {
                        salonSalesDetailWebKitchenPrintYN = strOrderItems[20];
                    }
                    String salonSalesDetailWebKitchenPrinterNum = "";
                    if (strOrderItems.length > 21) {
                        salonSalesDetailWebKitchenPrinterNum = strOrderItems[21];
                    }
                    // jihun add up
                    String salonSales_Customer_Memo = "";
                    if (strOrderItems.length > 22) {
                        salonSales_Customer_Memo = strOrderItems[22];
                    }
                    String salonSales_If_sold_out = "";
                    if (strOrderItems.length > 23) {
                        salonSales_If_sold_out = strOrderItems[23];
                    }

                    // ------------------------------------------------------------------------------------------------------------------

                    String temp_saveType = GlobalMemberValues.getSaveType(salonSalesDetailWebSaveType);

                    String temp_itemIdx = salonSalesDetailWebItemIdx;

                    String temp_kitchenPrintYN = salonSalesDetailWebKitchenPrintYN;

                    /*String temp_kitchenPrintYN = "N";
                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                        temp_kitchenPrintYN = dbInit.dbExecuteReadReturnString(
                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                            temp_kitchenPrintYN = "N";
                        }
                    }*/

                    int salonSalesDetailStatus = GlobalMemberValues.getIntAtString(salonSalesDetailWebStatus);
                    String salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[0];
                    /**
                     if (salonSalesDetailStatus == 3) {
                     salonSalesDetailLnBackGroundColor = GlobalMemberValues.SALEHISTORYSALONSALEDETAILSBGCOLOR[1];
                     }
                     **/

                    // ReceiptJson 을 arraylist 에 저장한다.
                    mReceiptJsonArray.add(salonSalesIndex, salonSalesWebReceiptJson);


                    mSelectedSalesCode = dbSalesCode;
                    mSelectedSalonSalesDetailIdx = salonSalesDetailWebIdx;
                    mSelectedSalonSalesDetailStatus = (salonSalesDetailStatus + "");

                    mSelectedReceiptJsonArrayIndex = (salonSalesIndex + "");


                    int tempItemQty = GlobalMemberValues.getIntAtString(salonSalesDetailWebQty);
                    //int tempItemQty = 0;
                    double tempItemPrice = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebSalesPrice);
                    double tempItemOptionprice = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebOptionPrice);
                    double tempItemAdditionalprice1 = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebAddtionalprice1);
                    double tempItemAdditionalprice2 = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebAddtionalprice2);
                    double tempItemTax = GlobalMemberValues.getDoubleAtString(salonSalesDetailWebTax);

                    double tempSaleAmount = ((tempItemPrice + tempItemOptionprice + tempItemAdditionalprice1 + tempItemAdditionalprice2) * tempItemQty);
                    double tempTaxAmount = (tempItemTax * tempItemQty);
                    double tempSaleTaxAmount = tempSaleAmount + tempTaxAmount;


                    // 옵션 또는 추가사항이 있을 경우 아래에 보여준다. --------------------------------------------------------------------
                    String tempOptionTxt = salonSalesDetailWebOptionTxt;
                    String tempOptionPrice = salonSalesDetailWebOptionPrice;

                    String tempAdditionalTxt1 = salonSalesDetailWebAddtionalTxt1;
                    String tempAdditionalPrice1 = salonSalesDetailWebAddtionalprice1;

                    String tempAdditionalTxt2 = salonSalesDetailWebAddtionalTxt2;
                    String tempAdditionalPrice2 = salonSalesDetailWebAddtionalprice2;

                    // -----------------------------------------------------------------------------------------------------------------




                    if (temp_saveType.equals("S") && temp_kitchenPrintYN.equals("Y")) {
                        //String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);
                        String tempKitchenPrinterNumber = salonSalesDetailWebKitchenPrinterNum;

                        String saleitem_name_optionadd = salonSalesDetailWebItemName +
                                "-ANNIETTASU-" + tempOptionTxt +
                                "-ANNIETTASU-" + tempAdditionalTxt1 +
                                "-ANNIETTASU-" + tempAdditionalTxt2 +
                                "-ANNIETTASU-" + temp_itemIdx +
                                "-ANNIETTASU-" + tempKitchenPrinterNumber +
                                "-ANNIETTASU-" + "nokitchenmemo";


                        String temp_kitchenprintnum = "0";
                        temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                        String item_categoryName1 = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select servicename from salon_storeservice_main " +
                                        " where idx in (select midx from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ) ");
                        String item_categoryName2 = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select servicename2 from salon_storeservice_main " +
                                        " where idx in (select midx from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ) ");

                        String item_categoryName = item_categoryName1;
//                        if (!GlobalMemberValues.isStrEmpty(item_categoryName1)) {
//                            item_categoryName += " " + item_categoryName2;
//                        }

                        if (saleItemsCount == 0) {
                            saleItemsTxt = saleitem_name_optionadd + "-JJJ-" + "-" + salonSalesDetailWebQty +
                                    "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + item_categoryName;
                        } else {
                            saleItemsTxt = saleItemsTxt + "-WANHAYE-"
                                    + saleitem_name_optionadd + "-JJJ-" + "-" + salonSalesDetailWebQty +
                                    "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + item_categoryName;
                        }

                        GlobalMemberValues.logWrite("saleItemsTxtlog", "saleItemsTxt : " + saleItemsTxt + "\n");

                        saleItemsCount++;
                    }
                }
                // ----------------------------------------------------------------------------------------------------------------------------------



                // ------------------------------------------------------------------------------------------------------------------


                final String kpSalesCode = dbSalesCode;
                final String kpCustomerName = salonSalesWebCustomerName;
                final String kpDeliveryTakeaway = salonSalesWebDeliveryTakeaway;
                final String kpDeliveryDate = salonSalesWebDeliveryDate;
                final String kpCustomerMemo = salonSalesWebCustomermemo;
                final String kpSaleItems = saleItemsTxt;
                final String kpCustomerOrderNumber = "W" + salonSalesCustomerOrderNumber;

                if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                        "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {

                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpSalesCode + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + salonSalesWebSaleDate + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerName + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpSaleItems + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpDeliveryTakeaway + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpDeliveryDate + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerMemo + "\n");
                    GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + kpCustomerOrderNumber + "\n");


                    SimpleDateFormat old = new SimpleDateFormat("MM/dd/yyyy H:mm:ss a");
                    SimpleDateFormat newTimeformat = new SimpleDateFormat("HH:mm:ss");

                    JSONObject jsonroot_kitchen = new JSONObject();
                    try {
                        Date old_date = old.parse(salonSalesWebSaleDate);
                        String new_salonSalesWebSaleDate = newTimeformat.format(old_date);

                        Date old_kpDeliveryDate = old.parse(kpDeliveryDate);
                        String new_kpDeliveryDate = newTimeformat.format(old_kpDeliveryDate);

                        GlobalMemberValues.logWrite("salehistorykitchenprintlog", "kpSalesCode : " + new_salonSalesWebSaleDate + "\n");

                        jsonroot_kitchen.put("receiptno",  "C" + kpSalesCode.substring(1));
                        jsonroot_kitchen.put("saledate", new_salonSalesWebSaleDate);
                        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                        jsonroot_kitchen.put("customername", kpCustomerName);
                        jsonroot_kitchen.put("saleitemlist", kpSaleItems);
                        jsonroot_kitchen.put("deliverytakeaway", kpDeliveryTakeaway);
                        jsonroot_kitchen.put("deliverydate", new_kpDeliveryDate);
                        jsonroot_kitchen.put("ordertype", "WEB");
                        jsonroot_kitchen.put("customermemo", kpCustomerMemo);
                        //jsonroot_kitchen.put("reprint", "Y");
                        jsonroot_kitchen.put("customerordernumber", kpCustomerOrderNumber);
                        jsonroot_kitchen.put("customerpagernumber", "");

                        jsonroot_kitchen.put("checkcompany", "");
                        jsonroot_kitchen.put("phoneordernumber", "");
                        jsonroot_kitchen.put("customerphonenum", "");
                        jsonroot_kitchen.put("customeraddress", "");
                        jsonroot_kitchen.put("restaurant_tablename", "");
                        jsonroot_kitchen.put("restaurant_tablepeoplecnt", 0);

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                    GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

                    //rePrintInKitchen(jsonroot_kitchen, kpSalesCode);
                    GlobalMemberValues.printGateByKitchen(jsonroot_kitchen, context, "kitchen1");
//
////                    rePrintInKitchen_mssql(jsonroot_kitchen, kpSalesCode);
//                    Vector<String> strInsertQueryVec = new Vector<String>();
//
//                    String tempKitchenQuery = "";
//                    tempKitchenQuery = " insert into salon_sales_kitchenprintingdata_json " +
//                            " (salesCode, scode, sidx, stcode, jsonstr) values ( " +
//                            " 'C" + kpSalesCode.substring(1) + "', " +
//                            " '" + GlobalMemberValues.SALON_CODE + "', " +
//                            "  " + GlobalMemberValues.STORE_INDEX + ", " +
//                            " '" + GlobalMemberValues.STATION_CODE + "', " +
//                            " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot_kitchen.toString(), 0) + "' " +
//                            " ) ";
//                    strInsertQueryVec.addElement(tempKitchenQuery);
//
//                    for (String tempQuery : strInsertQueryVec) {
//                        GlobalMemberValues.logWrite("PaymentQueryStringVoidLog", "query : " + tempQuery + "\n");
//                    }
//
//                    // 트랜잭션으로 DB 처리한다.
//                    String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
//                    if (returnResult == "N" || returnResult == "") {
//                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
//                    } else { // 정상처리일 경우 Sale History 리스트 리로드
//
//                    }
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RETURN_WEB){
            if (resultCode == RESULT_OK){
                setReturnOpen();
            }
        }
    }
}
