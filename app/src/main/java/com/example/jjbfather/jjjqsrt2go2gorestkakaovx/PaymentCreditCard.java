package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.byullbam.restapi.RestApiConfig;
import com.byullbam.restapi.RestApiSale;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class PaymentCreditCard extends Activity {
    static final String TAG = "CreditCardProcessingClass";

    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    public static LinearLayout rootLinearLayout;

    private LinearLayout parentLn;

    public static LinearLayout paymentCreditCardProcessingCardListLinearLayout;

    private LinearLayout insertswipekeyinLinearLayout;

    private Button closeBtn;

    private Button paymentCreditCardSuButton1,paymentCreditCardSuButton2,paymentCreditCardSuButton3;
    private Button paymentCreditCardSuButton4,paymentCreditCardSuButton5,paymentCreditCardSuButton6;
    private Button paymentCreditCardSuButton7,paymentCreditCardSuButton8,paymentCreditCardSuButton9;
    private Button paymentCreditCardSuButton0,paymentCreditCardSuButton00,paymentCreditCardSuButtonBack;

    private Button paymentCreditCardInsertSwipeButton, paymentCreditCardKeyInButton;
    private Button paymentCreditCardCardProcessButton;
    private Button paymentCreditCardVoidButton;
    public static Button paymentCreditCardVButton;

    static TextView paymentCreditCardProcessingAmountTextView;
    private TextView paymentCreditCardAmountTextView;
    static TextView paymentCreditCardBalanceTextView;
    static TextView paymentCreditCardPaidTextView;

    private LinearLayout paymentCreditCardContentsLinearLayout;

    private TextView paymentCreditCardTopTitleTextView;
    private TextView paymentCreditCardTitleTextView;

    private TextView paymentCreditCardProcessingAmountTitleTextView;
    private TextView paymentCreditCardCardNumberTitleTextView;
    public static TextView paymentCreditCardCardNumberTextView;

    private TextView paymentCreditCardAmountTitleTextView;
    private TextView paymentCreditCardBalanceTitleTextView;
    private TextView paymentCreditCardPaidTitleTextView;

    private ImageButton pcc_cardstatusbtn;

    private LinearLayout linearLayout_frameMain;
    private LinearLayout linearLayout_frameInsertSwipe;
    private LinearLayout linearLayout_frameKeyIn;
    private static LinearLayout cardprocessinglistLn2;

    // 07.18.2022 - add pay for cash, card
    public static LinearLayout paymnetcreidtcartaddpayLn;
    public static TextView paymnetcreidtcartaddpayTv;

    Intent mIntent;
    static String mBalanceAmountValue = "0.0";
    static String mExetype = "";

    // Paid 증가값
    static double mTempPaidValue = 0.0;

    // 임시저장값
    static String mTempPriceValue = "";

    // 선택한 카드 결제내역의 salon_sales_card 의 인덱스값
    static String mSelectedSalonSalesCardIdx = "";

    public static LinearLayout mSelectedLn;
    public static TextView mSelectedTv;

    static String mSelectedInsSwKi = "IS";

    // 카드결제 연동 관련 변수 -------------------------------
    public static ProgressDialog proDial;   // 프로그래스바
    public static JSONObject creditCardReturnValueJson;   // 결제처리후 리턴값
    // ----------------------------------------------------

    // PAX 카드 결제관련 변수 -------------------------------
    public static String pax_processingResult = "";
    public static String pax_processingResultTxt = "";
    public static String pax_exeData = "";
    // ----------------------------------------------------

    // 터미널 연결정보 --------------------------------------
    public static String paymentGateway = "";
    public static String networkIp = "";
    public static String networkPort = "";
    // ----------------------------------------------------

    public static JSONObject[] mCardPaymentJsonArray = null;
    // 9999 수정
//    public static ArrayList<JSONObject> mCardPaymentJsonArrayList;

    public static TextView mNowTv = null;

    public static JSONObject mJsontmp = null;

    public static String mKeyinCardNumber = "";
    public static String mKeyinExpDate = "";
    public static String mKeyinCVVNumber = "";
    public static String mKeyinAddr = "";
    public static String mKeyinZip = "";

    public static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_credit_card);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 6;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.paymentCreditCardLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mBalanceAmountValue = mIntent.getStringExtra("paymentCreditCardPopupAmount");
            mExetype = mIntent.getStringExtra("exetype");
            // GlobalMemberValues.logWrite("mBalanceAmountValue", "넘겨받은 mBalanceAmountValue : " + mBalanceAmountValue + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            setFinish();
        }

        setContents();
    }

    public void setContents() {
        mActivity = this;
        context = this;

        // 07.16.2022 -------------------------------------
        GlobalMemberValues.mNotPayYNOnCard = false;
        GlobalMemberValues.mNotPayYNOnCardMsg = "";
        // ------------------------------------------------

        rootLinearLayout = (LinearLayout)findViewById(R.id.rootLinearLayout);

        int numberBackground = R.drawable.ab_imagebutton_discountextra_number;
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            numberBackground = R.drawable.ab_imagebutton_discountextra_number_lite;
            GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR = "#0054d5";
        }

        // 카드 프로세싱 스텝 초기화
        GlobalMemberValues.CARD_PROCESSING_STEP = 0;

        mTempPaidValue = 0.0;

        ConfigPGInfo pgInfo = new ConfigPGInfo();
        paymentGateway = pgInfo.cfpaymentgateway;
        networkIp = pgInfo.cfnetworkip;
        networkPort = pgInfo.cfnetworkport;

        dbInit = new DatabaseInit(this);
        creditCardReturnValueJson = null;
        Payment.mJsonList = null;
        Payment.mJsonList = new JSONArray();

        paymentCreditCardContentsLinearLayout = (LinearLayout)findViewById(R.id.paymentCreditCardContentsLinearLayout);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentCreditCardContentsLinearLayout.setPadding(3, 0, 3, 0);
        }

        paymentCreditCardTopTitleTextView = (TextView)findViewById(R.id.paymentCreditCardTopTitleTextView);
        paymentCreditCardTopTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardTopTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardTitleTextView = (TextView)findViewById(R.id.paymentCreditCardTitleTextView);
        paymentCreditCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardProcessingAmountTitleTextView = (TextView)findViewById(R.id.paymentCreditCardProcessingAmountTitleTextView);
        paymentCreditCardProcessingAmountTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardProcessingAmountTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardCardNumberTitleTextView = (TextView)findViewById(R.id.paymentCreditCardCardNumberTitleTextView);
        paymentCreditCardCardNumberTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardCardNumberTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardCardNumberTextView = (TextView)findViewById(R.id.paymentCreditCardCardNumberTextView);
        paymentCreditCardCardNumberTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardCardNumberTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardBalanceTitleTextView = (TextView)findViewById(R.id.paymentCreditCardBalanceTitleTextView);
        paymentCreditCardBalanceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardBalanceTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardPaidTitleTextView = (TextView)findViewById(R.id.paymentCreditCardPaidTitleTextView);
        paymentCreditCardPaidTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardPaidTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardAmountTitleTextView = (TextView)findViewById(R.id.paymentCreditCardAmountTitleTextView);
        paymentCreditCardAmountTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardAmountTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );


        paymentCreditCardBalanceTextView = (TextView)findViewById(R.id.paymentCreditCardBalanceTextView);
        paymentCreditCardBalanceTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardBalanceTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardPaidTextView = (TextView)findViewById(R.id.paymentCreditCardPaidTextView);
        paymentCreditCardPaidTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardPaidTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        paymentCreditCardAmountTextView = (TextView)findViewById(R.id.paymentCreditCardAmountTextView);
        paymentCreditCardAmountTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardAmountTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("paymentCreditCardCloseBtnTag");
        closeBtn.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        paymentCreditCardSuButton1 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton1.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton2 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton2.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton3 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton3.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton4 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton4.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton5 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton5.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton6 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton6.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton7 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton7.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton8 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton8.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton9 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton9.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton0 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton0.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButton00 = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentCreditCardSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentCreditCardSuButton00.setBackgroundResource(numberBackground);
        } else {
            paymentCreditCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardSuButtonBack = (Button)parentLn
                .findViewWithTag("paymentCreditCardSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardSuButtonBack.setText("");
            paymentCreditCardSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_delete);
        } else {
            paymentCreditCardSuButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardSuButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        pcc_cardstatusbtn = (ImageButton) parentLn.findViewWithTag("pcc_cardstatusbtnTag");
        if (!GlobalMemberValues.isCardStatusSaveUse()) {
            pcc_cardstatusbtn.setVisibility(View.GONE);
        } else {
            pcc_cardstatusbtn.setVisibility(View.VISIBLE);
        }

        paymentCreditCardSuButton1.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton2.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton3.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton4.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton5.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton6.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton7.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton8.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton9.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton0.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButton00.setOnClickListener(paymentCreditCardBtnClickListener);
        paymentCreditCardSuButtonBack.setOnClickListener(paymentCreditCardBtnClickListener);

        pcc_cardstatusbtn.setOnClickListener(paymentCreditCardBtnClickListener);

        paymentCreditCardCardProcessButton = (Button)parentLn
                .findViewWithTag("paymentCreditCardCardProcessButtonTag");
        paymentCreditCardCardProcessButton.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardCardProcessButton.setText("");
            paymentCreditCardCardProcessButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_cardprocessing);
        } else {
            paymentCreditCardCardProcessButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardCardProcessButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        /***********************************************************************************************************/

        /** Insert/Swipe, Key-In 버튼 객체 생성 및 리스너 연결 ******************************************************/
        paymentCreditCardInsertSwipeButton = (Button)parentLn
                .findViewWithTag("paymentCreditCardInsertSwipeButtonTag");
        paymentCreditCardInsertSwipeButton.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardInsertSwipeButton.setText("");
            paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
        } else {
            paymentCreditCardInsertSwipeButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardInsertSwipeButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_insertswipe_rollover);

        paymentCreditCardKeyInButton = (Button)parentLn
                .findViewWithTag("paymentCreditCardKeyInButtonTag");
        paymentCreditCardKeyInButton.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardKeyInButton.setText("");
            paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
        } else {
            paymentCreditCardKeyInButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardKeyInButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/


        /** Void, V 버튼 객체 생성 및 리스너 연결 ******************************************************************/
        paymentCreditCardVoidButton = (Button)parentLn
                .findViewWithTag("paymentCreditCardVoidButtonTag");
        paymentCreditCardVoidButton.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardVoidButton.setText("");
            paymentCreditCardVoidButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_void);
        } else {
            paymentCreditCardVoidButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardVoidButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        paymentCreditCardVButton = (Button)parentLn
                .findViewWithTag("paymentCreditCardVButtonTag");
        paymentCreditCardVButton.setOnClickListener(paymentCreditCardBtnClickListener2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentCreditCardVButton.setText("");
            paymentCreditCardVButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_enter);
        } else {
            paymentCreditCardVButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    paymentCreditCardVButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/


        /** Processing Amount, Balance, Paid TextView 객체 생성 및 리스너 ******************************************/
        paymentCreditCardProcessingAmountTextView = (TextView)parentLn
                .findViewWithTag("paymentCreditCardProcessingAmountTextViewTag");
        paymentCreditCardProcessingAmountTextView.setTextSize(GlobalMemberValues.globalAddFontSize() +
                paymentCreditCardProcessingAmountTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        paymentCreditCardProcessingAmountTextView.setOnClickListener(paymentCreditCardBtnClickListener2);

        paymentCreditCardAmountTextView = (TextView)parentLn
                .findViewWithTag("paymentCreditCardAmountTextViewTag");
        paymentCreditCardBalanceTextView = (TextView)parentLn
                .findViewWithTag("paymentCreditCardBalanceTextViewTag");
        paymentCreditCardPaidTextView = (TextView)parentLn
                .findViewWithTag("paymentCreditCardPaidTextViewTag");
        /***********************************************************************************************************/

        // 객체 및 색상 초기화
        setInit();

        /** Amount 초기화 *******************************************************************************************/
        paymentCreditCardProcessingAmountTextView.setText(mBalanceAmountValue);

        paymentCreditCardAmountTextView.setText(mBalanceAmountValue);
        paymentCreditCardBalanceTextView.setText(mBalanceAmountValue);
        /***********************************************************************************************************/

        // ScrollView 에 속한 첫번째 LinearLayout 객체
        paymentCreditCardProcessingCardListLinearLayout = (LinearLayout)findViewById(R.id.paymentCreditCardProcessingCardListLinearLayout);

        // insert/swipe, keyin 버튼이 있는 LinearLayount 객체
        insertswipekeyinLinearLayout = (LinearLayout)findViewById(R.id.insertswipekeyinLinearLayout);
        // insert/swipe, keyin 버튼을 보여줄지 여부
        if (GlobalMemberValues.CARD_KEY_IN) {
            insertswipekeyinLinearLayout.setVisibility(View.VISIBLE);
        } else {
            insertswipekeyinLinearLayout.setVisibility(View.GONE);
        }

        paymentCreditCardProcessingCardListLinearLayout = (LinearLayout)findViewById(R.id.paymentCreditCardProcessingCardListLinearLayout);

        linearLayout_frameMain = (LinearLayout)findViewById(R.id.linearLayout_frameMain);
        linearLayout_frameInsertSwipe = (LinearLayout)findViewById(R.id.linearLayout_frameInsertSwipe);
        linearLayout_frameInsertSwipe.setBackgroundResource(R.drawable.roundlayout_button_paymentcreditcard_insertswipe);
        linearLayout_frameKeyIn = (LinearLayout)findViewById(R.id.linearLayout_frameKeyIn);
        linearLayout_frameKeyIn.setBackgroundResource(R.drawable.roundlayout_button_paymentcreditcard_insertswipe);

        mCardPaymentJsonArray = null;
        mCardPaymentJsonArray = new JSONObject[9999999];

        cardprocessinglistLn2 = (LinearLayout)findViewById(R.id.cardprocessinglistLn2);
        //cardprocessinglistLn2.setVisibility(View.GONE);

        // 07.18.2022 - add pay for cash, card
        paymnetcreidtcartaddpayLn = (LinearLayout)findViewById(R.id.paymnetcreidtcartaddpayLn);
        paymnetcreidtcartaddpayLn.setVisibility(View.GONE);
        paymnetcreidtcartaddpayTv = (TextView) findViewById(R.id.paymnetcreidtcartaddpayTv);
        paymnetcreidtcartaddpayTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + 20 * GlobalMemberValues.getGlobalFontSize()
        );
        if (GlobalMemberValues.getAddPayType().equals("C")) {
            paymnetcreidtcartaddpayLn.setVisibility(View.VISIBLE);
            paymnetcreidtcartaddpayTv.setText(GlobalMemberValues.mAddPayMsgForCard);
        } else {
            GlobalMemberValues.mAddPayMsgForCard = "";
            paymnetcreidtcartaddpayLn.setVisibility(View.GONE);
            paymnetcreidtcartaddpayTv.setText("");
        }

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            LinearLayout cardprocessinglistLn1 = (LinearLayout)findViewById(R.id.cardprocessinglistLn1);
            cardprocessinglistLn1.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,  3.7f));

            cardprocessinglistLn2.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));

            LinearLayout cardprocessinglistLn3 = (LinearLayout)findViewById(R.id.cardprocessinglistLn3);
            cardprocessinglistLn3.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.6f));
        }

        setForSwipeInsertType();

        if (!GlobalMemberValues.isStrEmpty(mExetype) && mExetype.equals("direct")) {
            setCreditCardProcess();
        }

        // 06162023
        if (AddPayCustomerSelectPopup.isStartCartProcessing) {
            setStartProcessing();
            if(GlobalMemberValues.ISDUALDISPLAYPOSSIBLE){
                MainActivity.updatePresentation();
            }
        }
    }

    public static void setAddSalonSalesCardList() throws JSONException {
        mNowTv = null;

        String insInsertSwipeKeyin = mSelectedInsSwKi;
        if (GlobalMemberValues.isStrEmpty(insInsertSwipeKeyin)) {
            insInsertSwipeKeyin = "IS";
        }

        // 이 부분에는 카드연동처리 관련코드를 작성한다. --------------------------------------------------------------
        String returnCodeFromCardModule = "00";     // 카드결제결과 리턴값

        // 리턴받은 카드결제 Transaction 정보
        String insSalesCode = Payment.mSalesCode;
        String insTid = "00000000000";      // Auth Number
        String insCardCom = "V";
        String cardCompanyName = "VISA";
        String insStatus = "0";
        double insPriceAmount = GlobalMemberValues.getDoubleAtString(
                paymentCreditCardProcessingAmountTextView.getText().toString()
        );
        String insCardLastFourDigitNumbers = "0000";    // 사용한 카드 뒤 4자리
        String insCardRefNumber = "1";                  // 레퍼런스 값

        String insCardEmvAid = "A00000000000";          // EMV 결제시 리턴받은 AID 값
        String insCardEmvTsi = "A00000000000";          // EMV 결제시 리턴받은 TSI 값
        String insCardEmvTvr = "A00000000000";          // EMV 결제시 리턴받은 TVR 값

        String tempTransactionType = "";                // 결제디바이스(터미널) 연결여부 체크값
        String tempTransactionStatus = "";              // 결제디바이스(터미널) 연결여부 체크상태

        String insCardPLNameOnCard = "";                // EMV 결제시 리턴받은 PLNameOnCard 값

        String insCardAmountDue = "";                   // EMV 결제시 리턴받은 AmountDue 값

        if (creditCardReturnValueJson != null) {
            GlobalMemberValues.logWrite(TAG, "여기까지 오면 성공..\n");
            //GlobalMemberValues.logWrite(TAG, "JSON 내용전체 : " + creditCardReturnValueJson + "\n");
            if (creditCardReturnValueJson.toString().contains("Transaction Type")){
                tempTransactionType = creditCardReturnValueJson.getString("Transaction Type");
            }

            if (creditCardReturnValueJson.toString().contains("Transaction Status")){
                tempTransactionStatus = creditCardReturnValueJson.getString("Transaction Status");
                GlobalMemberValues.logWrite(TAG, "TransactionStatus : " + tempTransactionStatus + "\n");
            }
            if (creditCardReturnValueJson.toString().contains("returncodefromdevice")){
                returnCodeFromCardModule = creditCardReturnValueJson.getString("returncodefromdevice");
            } else {
                returnCodeFromCardModule = "";
            }
            if (creditCardReturnValueJson.toString().contains("authnumber")){
                insTid = creditCardReturnValueJson.getString("authnumber");
            } else {
                insTid = "";
            }
            if (creditCardReturnValueJson.toString().contains("cardcompay")){
                insCardCom = creditCardReturnValueJson.getString("cardcompay");
            } else {
                insCardCom = "";
            }
            if (creditCardReturnValueJson.toString().contains("lastfourdigits")){
                insCardLastFourDigitNumbers = creditCardReturnValueJson.getString("lastfourdigits");
            } else {
                insCardLastFourDigitNumbers = "";
            }
            if (creditCardReturnValueJson.toString().contains("referencenumber")){
                insCardRefNumber = creditCardReturnValueJson.getString("referencenumber");
            } else {
                insCardRefNumber = "";
            }
            if (creditCardReturnValueJson.toString().contains("emvaid")){
                insCardEmvAid = creditCardReturnValueJson.getString("emvaid");
            } else {
                insCardEmvAid = "";
            }
            if (creditCardReturnValueJson.toString().contains("emvtsi")){
                insCardEmvTsi = creditCardReturnValueJson.getString("emvtsi");
            } else {
                insCardEmvTsi = "";
            }
            if (creditCardReturnValueJson.toString().contains("emvtvr")){
                insCardEmvTvr = creditCardReturnValueJson.getString("emvtvr");
            } else {
                insCardEmvTvr = "";
            }
            //PLNameOnCard
            if (creditCardReturnValueJson.toString().contains("plnameoncard")){
                insCardPLNameOnCard = creditCardReturnValueJson.getString("plnameoncard");
            } else {
                insCardPLNameOnCard = "";
            }
            //AmountDue
            if (creditCardReturnValueJson.toString().contains("amountdue")){
                insCardAmountDue = creditCardReturnValueJson.getString("amountdue");
            } else {
                insCardAmountDue = "";
            }


            GlobalMemberValues.logWrite(TAG, "결과값 : " + returnCodeFromCardModule + "\n");
        } else {
            GlobalMemberValues.logWrite(TAG, "여기왔으면 실패..\n");
            returnCodeFromCardModule = "99";
            insTid = "";
            insCardCom = "";
            insCardLastFourDigitNumbers = "";
            insCardRefNumber = "";
            insCardEmvAid = "";
            insCardEmvTsi = "";
            insCardEmvTvr = "";
            insCardPLNameOnCard = "";
        }
        // ------------------------------------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("paymentcreditcardresultlog", "카드처리 결과값 : " + returnCodeFromCardModule + "\n");

        if (returnCodeFromCardModule == "00" || returnCodeFromCardModule.equals("00")) {     // 카드프로세싱이 성공했을 경우에
            GlobalMemberValues.logWrite("paymentcreditcardlog", "여기까지2\n");

            // salon_sales_card 테이블에 저장한다. ----------------------------------------------------------
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strInsSqlQuery = "";
            String returnResult = "";

            strInsSqlQuery = "insert into salon_sales_card (" +
                    " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                    " cardLastFourDigitNumbers, cardRefNumber, " +
                    " cardEmvAid, cardEmvTsi, cardEmvTvr, " +
                    " employeeIdx, employeeName, " +
                    " serverIdx, serverName  " +
                    " ) values ( " +
                    " '" + insSalesCode + "', " +
                    " '" + insTid + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + insCardCom + "', " +
                    " '" + insPriceAmount + "', " +
                    " '" + insInsertSwipeKeyin + "', " +
                    " '" + insStatus + "', " +
                    " '" + insCardLastFourDigitNumbers + "', " +
                    " '" + insCardRefNumber + "', " +
                    " '" + insCardEmvAid + "', " +
                    " '" + insCardEmvTsi + "', " +
                    " '" + insCardEmvTvr + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "' " +
                    ")";
            // salon_sales_card 입력쿼리를 백터 strInsertQueryVec 에 담는다.
            strInsertQueryVec.addElement(strInsSqlQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패

                GlobalMemberValues.displayDialog(context, "Warning",
                        "There was a problem in the card processing." +
                                "\nPlease void the paid process and try again." +
                                "\n(Error : database error(1))", "Close");

                // 카드 Void 처리 ------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(insCardRefNumber)) {
                    String tempFlagCardProcessingForVoid = "";

                    // creditCardPaymentByTerminalForVoid 메소드 파라미터로 넘겨주기 위해 ArrayList 객체생성후
                    // void 처리할 레퍼런스코드값을 add 한다.
                    ArrayList<String> tempArrRefNum = new ArrayList<String>();
                    tempArrRefNum.add(insCardRefNumber);

                    // 여기 하나 .? void 처리.
//

                }
                // --------------------------------------------------------------------------------
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // salon_sales_card 테이블에 방금 저장한 데이터의 idx 값을 가져온다.
                String tempSalonSalesCardIdx = "";
                tempSalonSalesCardIdx = MssqlDatabase.getResultSetValueToString("select top 1 idx from salon_sales_card order by idx desc");
                GlobalMemberValues.mCardSalesIdxForBillPay = tempSalonSalesCardIdx;

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
                        = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                subNewTvLayoutParams.setMargins(0, 0, 0, 0);

                // LinearLayout 객체 생성
                LinearLayout newLn = new LinearLayout(context);
                newLn.setLayoutParams(newLnLayoutParams);
                newLn.setOrientation(LinearLayout.VERTICAL);
                newLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                newLn.setTag(tempSalonSalesCardIdx);
                newLn.setPadding(15, 15, 15, 0);

                newLn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectedSalonSalesCardIdx == v.getTag().toString()) {
                            mSelectedLn = null;
                            mSelectedSalonSalesCardIdx = "";
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                        } else {
                            // 앞서 선택한 LinearLayout 객체의 배경색을 초기화
                            if (mSelectedLn != null) {
                                mSelectedLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                            }
                            mSelectedLn = (LinearLayout) v;
                            mSelectedSalonSalesCardIdx = v.getTag().toString();
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist_select);
                        }
                    }
                });

                // 이미지뷰 객체 생성 및 붙이기 --------------------------------------------------
                int tempCardImgId = R.drawable.aa_images_cardimg_visa;     // 결제된 카드의 종류 이미지 주소 (기본값을 Visa 로 한다.)
                ImageView cardComImage = new ImageView(context);
                switch (insCardCom) {
                    case "V" : {
                        tempCardImgId = R.drawable.aa_images_cardimg_visa;
                        cardCompanyName = "VISA";
                        break;
                    }
                    case "M" : {
                        tempCardImgId = R.drawable.aa_images_cardimg_master;
                        cardCompanyName = "MASTER";
                        break;
                    }
                    case "A" : {
                        tempCardImgId = R.drawable.aa_images_cardimg_amex;
                        cardCompanyName = "AMEX";
                        break;
                    }
                    case "D" : {
                        tempCardImgId = R.drawable.aa_images_cardimg_discover;
                        cardCompanyName = "DISCOVER";
                        break;
                    }
                }
                cardComImage.setImageResource(tempCardImgId);
                cardComImage.setMaxWidth(50);
                newLn.addView(cardComImage);
                // -------------------------------------------------------------------------------

                // 결제하는 금액
                String tempProcessingAmount = "0.0";
                tempProcessingAmount = paymentCreditCardProcessingAmountTextView.getText().toString();

                // add up 210120
                GlobalMemberValues.logWrite("mTempPaidValueCheck2", "insCardAmountDue : " + insCardAmountDue + "\n");
                tempProcessingAmount = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(tempProcessingAmount)  - (GlobalMemberValues.getDoubleAtString(insCardAmountDue) * 0.01), "2");
                // add up 210120

                // 결제금액 관련 TextView 객체 생성 및 붙이기 ---------------------------------------
                TextView salonSalesCardListAmountTv = new TextView(context);
                salonSalesCardListAmountTv.setLayoutParams(subNewTvLayoutParams);
                salonSalesCardListAmountTv.setGravity(Gravity.CENTER);
                salonSalesCardListAmountTv.setPadding(0, 5, 0, 5);
                salonSalesCardListAmountTv.setText(tempProcessingAmount);
                salonSalesCardListAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);
                salonSalesCardListAmountTv.setTextColor(Color.parseColor("#3e3d39"));
                salonSalesCardListAmountTv.setPaintFlags(salonSalesCardListAmountTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                newLn.addView(salonSalesCardListAmountTv);
                // -------------------------------------------------------------------------------

                // Tip 관련 TextView 객체 생성 및 붙이기 -------------------------------------------
                TextView salonSalesCardListTipAmountTv = new TextView(context);
                salonSalesCardListTipAmountTv.setLayoutParams(subNewTvLayoutParams);
                salonSalesCardListTipAmountTv.setGravity(Gravity.CENTER);
                salonSalesCardListTipAmountTv.setPadding(0, 0, 0, 5);
                salonSalesCardListTipAmountTv.setText(tempProcessingAmount);
                salonSalesCardListTipAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
                salonSalesCardListTipAmountTv.setTextColor(Color.parseColor("#3e3d39"));
                salonSalesCardListTipAmountTv.setVisibility(View.GONE);
                newLn.addView(salonSalesCardListTipAmountTv);

                mNowTv = salonSalesCardListTipAmountTv;
                // -------------------------------------------------------------------------------

                // TextView 객체 생성 및 붙이기 --------------------------------------------------
                TextView signedTv = new TextView(context);
                signedTv.setLayoutParams(subNewTvLayoutParams);
                signedTv.setGravity(Gravity.CENTER);
                signedTv.setHeight(8);
                signedTv.setPadding(0, 0, 0, 0);
                signedTv.setText("");
                signedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
                newLn.addView(signedTv);

                mSelectedTv = signedTv;
                // -------------------------------------------------------------------------------

                paymentCreditCardProcessingCardListLinearLayout.addView(newLn);
                //cardprocessinglistLn2.setVisibility(View.VISIBLE);

                double tempProcessingAmountDouble = GlobalMemberValues.getDoubleAtString(tempProcessingAmount);

                if (tempProcessingAmountDouble > 0) {
                    GlobalMemberValues.logWrite("mTempPaidValueCheck1", "mTempPaidValue : " + mTempPaidValue + "\n");

                    // Paid 증가값 반영
                    mTempPaidValue += tempProcessingAmountDouble;


                    GlobalMemberValues.logWrite("mTempPaidValueCheck2", "mTempPaidValue : " + mTempPaidValue + "\n");

                    GlobalMemberValues.logWrite("mTempPaidValueCheck2", "mBalanceAmountValue : " + mBalanceAmountValue + "\n");

                    // Balance 수정
                    double tempBalance = GlobalMemberValues.getDoubleAtString(mBalanceAmountValue) - mTempPaidValue;
                    paymentCreditCardBalanceTextView.setText(GlobalMemberValues.getStringFormatNumber(tempBalance, "2"));

                    // Paid TextView 에 값 할당
                    paymentCreditCardPaidTextView.setText(GlobalMemberValues.getStringFormatNumber(mTempPaidValue, "2"));

                    // Processing Amount 값 초기화
                    paymentCreditCardProcessingAmountTextView.setText("0.00");
                    mTempPriceValue = "";
                }

                String maxSalonSalesCardIdx = MssqlDatabase.getResultSetValueToString(
                        "select top 1 idx from salon_sales_card order by idx desc"
                );

                // 영수증 출력을 위한 JSON 관련 --------------------------------------------------------------------
                // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트

                // amountdue 값이 있으면 빼준다.
                if ((GlobalMemberValues.getDoubleAtString(insCardAmountDue) * 0.01) > 0){
                    insPriceAmount = insPriceAmount - (GlobalMemberValues.getDoubleAtString(insCardAmountDue) * 0.01);
                }

                mJsontmp = null;
                mJsontmp = new JSONObject();
                mJsontmp.put("cardsalonsalescardidx", maxSalonSalesCardIdx);
                mJsontmp.put("cardtype", cardCompanyName);
                mJsontmp.put("creditcardnumber", insCardLastFourDigitNumbers);
                mJsontmp.put("chargeamount", GlobalMemberValues.getStringFormatNumber(insPriceAmount, "2"));
                mJsontmp.put("cardauthnumber", insTid);
                mJsontmp.put("cardrefnumber", insCardRefNumber);
                mJsontmp.put("cardaid", insCardEmvAid);
                mJsontmp.put("cardtsi", insCardEmvTsi);
                mJsontmp.put("cardtvr", insCardEmvTvr);
                mJsontmp.put("cardholder", insCardPLNameOnCard);
                // 9999 수정
//                mCardPaymentJsonArrayList.add(mJsontmp);

                mCardPaymentJsonArray[GlobalMemberValues.getIntAtString(tempSalonSalesCardIdx)] = mJsontmp;
                // ----------------------------------------------------------------------------------------------

                // Sign Pad 사용여부
//                String getSignaturePadYN = dbInit.dbExecuteReadReturnString(
//                        "select signpaduseyn from salon_storestationsettings_paymentgateway"
//                );
//                if (GlobalMemberValues.isStrEmpty(getSignaturePadYN)) {
//                    getSignaturePadYN = "N";
//                }

                if (!GlobalMemberValues.isSignPadUse()) {
                    // 싸인패드를 사용하지 않을 경우
                    // 카드결제가 완료된후 최종결제로 결제할 금액을 모두 결제했을 경우
                    // 결제 프로세스를 완료한다.
                    finishPaymentAfterCheckBalance();
                } else {
                    if (!GlobalMemberValues.isStrEmpty(maxSalonSalesCardIdx)) {
                        // 먼저 결제된 카드 LinearLayout 을 mSelectedLn 으로 지정
                        mSelectedLn = newLn;

                        // 프레젠테이션 닫기
                        GlobalMemberValues.dismissPresentationView();

                        Intent jjjSignPadIntent = new Intent(context, JJJ_SignPad.class);
                        jjjSignPadIntent.putExtra("maxSalonSalesCardIdx", maxSalonSalesCardIdx);
                        jjjSignPadIntent.putExtra("cardAuthnum", insTid);
                        jjjSignPadIntent.putExtra("cardrefnum", insCardRefNumber);
                        jjjSignPadIntent.putExtra("cardcompany", insCardCom);
                        jjjSignPadIntent.putExtra("cardamount", GlobalMemberValues.getStringFormatNumber(insPriceAmount, "2"));
                        jjjSignPadIntent.putExtra("cardfourdigits", insCardLastFourDigitNumbers);
                        mActivity.startActivity(jjjSignPadIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning",
                                "There was a problem in the card processing." +
                                        "\nPlease void the paid process and try again." +
                                        "\n(Error : max card idx error)", "Close");
                    }
                }
            }
            // --------------------------------------------------------------------------------------------
        } else {     // 카드프로세싱이 실패했을 경우에
            String tempDpMsgStr = "";
            tempDpMsgStr = GlobalMemberValues.getCreditCardErrors(returnCodeFromCardModule);
            GlobalMemberValues.displayDialog(context, "Warning", tempDpMsgStr, "Close");
            return;
        }

        GlobalMemberValues.logWrite("paymentcreditcardlog", "여기까지3\n");
    }

    public static void finishPaymentAfterCheckBalance() {
        double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());
        if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
            // 결제 완료로 판단 Log Payment 완료 처리.
                Payment.paymentComplite_LogSave(true);
            //
            finishCreditCardProcessing();
        }
    }

    View.OnClickListener paymentCreditCardBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pcc_cardstatusbtn : {
                    Intent creditCardStatusIntent = new Intent(MainActivity.mContext.getApplicationContext(), CreditCardStatusActivity.class);
                    mActivity.startActivity(creditCardStatusIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
                    }

                    break;
                }
                case R.id.paymentCreditCardSuButton1 : {
                    numberButtonClick(paymentCreditCardSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton2 : {
                    numberButtonClick(paymentCreditCardSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton3 : {
                    numberButtonClick(paymentCreditCardSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton4 : {
                    numberButtonClick(paymentCreditCardSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton5 : {
                    numberButtonClick(paymentCreditCardSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton6 : {
                    numberButtonClick(paymentCreditCardSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton7 : {
                    numberButtonClick(paymentCreditCardSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton8 : {
                    numberButtonClick(paymentCreditCardSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton9 : {
                    numberButtonClick(paymentCreditCardSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton0 : {
                    numberButtonClick(paymentCreditCardSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButton00 : {
                    numberButtonClick(paymentCreditCardSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.paymentCreditCardSuButtonBack : {
                    StringBuilder sb = new StringBuilder();
                    sb.delete(0, sb.toString().length());
                    sb.append(mTempPriceValue);
                    if (!GlobalMemberValues.isStrEmpty(mTempPriceValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mTempPriceValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mTempPriceValue)) {
                            mTempPriceValue = "0";
                        }
                    } else {
                        mTempPriceValue = "0";
                    }

                    String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * 0.01), "2");
                    paymentCreditCardProcessingAmountTextView.setText(inputSu);

                    GlobalMemberValues.logWrite("paymentCreditCard", "paymentCreditCard mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    break;
                }

            }
        }
    };

    // 07.16.2022
    public void setPayAmountBalance() {
        String tempRemainBalance = paymentCreditCardBalanceTextView.getText().toString();
        paymentCreditCardProcessingAmountTextView.setText(tempRemainBalance);
    }

    JJJ_OnSingleClickListener paymentCreditCardBtnClickListener2 = new JJJ_OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.paymentCreditCardProcessingAmountTextView : {
                    // 07.16.2022
                    setPayAmountBalance();

                    break;
                }
                case R.id.paymentCreditCardCloseBtn : {

                    /**
                     String tempQuery = "select count(idx) from salon_sales_card where salesCode = '" + Payment.mSalesCode + "' ";
                     int tempSalonSalesCardCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
                     if (tempSalonSalesCardCount > 0) {
                     new AlertDialog.Builder(context)
                     .setTitle("RECALL")
                     .setMessage("Are you sure you want to exit?\nThe credit card(s) will be void")
                     //.setIcon(R.drawable.ic_launcher)
                     .setPositiveButton("Yes",
                     new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                     voidCardSales();
                     }
                     })
                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                     //
                     }
                     })
                     .show();
                     }
                     **/

                    // 카드결제가 있는지 체크한다.
                    double tempPaidAmount = GlobalMemberValues.getDoubleAtString(paymentCreditCardPaidTextView.getText().toString());

                    if (tempPaidAmount > 0 || tempPaidAmount > 0.0) {
                        paymentCreditCardVButton.setEnabled(true);
                        GlobalMemberValues.displayDialog(context, "Warning", "You can not close the window because the amount paid", "Close");
                    } else {
                        // 04042023
                        if (GlobalMemberValues.getAddPayType().equals("C")) {
                            Payment.setAddDeleteAddPay(false);
                        }

                        setFinish();
                        // 초기화
                        setInit();
                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(getApplication(), paymentCreditCardProcessingAmountTextView);
                    }

                    break;
                }

                case R.id.paymentCreditCardInsertSwipeButton : {
                    if (!GlobalMemberValues.isStrEmpty(mKeyinCardNumber)) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {
                            new AlertDialog.Builder(mActivity)
                                    .setTitle("Swipe / Insert")
                                    .setMessage("The card information entered as the key in is initialized\nWould you like to continue?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            setForSwipeInsertType();
                                        }
                                    }).show();
                        }
                    } else {
                        setForSwipeInsertType();
                    }
                    /**
                     double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());
                     if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                     GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning", "You do not have to pay any more money", "Close");
                     return;
                     }
                     //setInsertSwipeKeyin("IS");
                     setCreditCardProcess();
                     mSelectedInsSwKi = "IS";
                     **/

                    break;
                }
                case R.id.paymentCreditCardKeyInButton : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Key In")
                                .setMessage("Do you want to enter your card information manually?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent keyinIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_CardKeyIn.class);
                                        keyinIntent.putExtra("keyincardnumber", mKeyinCardNumber);
                                        keyinIntent.putExtra("keyinexpdate", mKeyinExpDate);
                                        keyinIntent.putExtra("keyincvvnumber", mKeyinCVVNumber);
                                        keyinIntent.putExtra("keyinaddr", mKeyinAddr);
                                        mActivity.startActivity(keyinIntent);
                                        if (GlobalMemberValues.isUseFadeInOut()) {
                                            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                                        }

                                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
                                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_keyin_rollover);

                                        mSelectedInsSwKi = "KI";
                                    }
                                }).show();
                    }

                    /**
                     double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());
                     if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                     GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning", "You do not have to pay any more money", "Close");
                     return;
                     }
                     //setInsertSwipeKeyin("KI");
                     setCreditCardProcess();
                     mSelectedInsSwKi = "KI";
                     **/
                    break;
                }
                case R.id.paymentCreditCardCardProcessButton : {
                    // 06162023
                    setStartProcessing();

                    break;
                }
                case R.id.paymentCreditCardVoidButton : {
                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<22>")){
                        GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesCardIdx)) {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning", "Select the card to void", "Close");
                        return;
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("VOID")
                                .setMessage("Are you sure you want to void?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // tid 가져오기
                                                String strTid = "";
                                                strTid = MssqlDatabase.getResultSetValueToString("select tid from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                String strRefNum = "";
                                                strRefNum = MssqlDatabase.getResultSetValueToString("select cardRefNumber from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                String strPriceAmount = "";
                                                strPriceAmount = MssqlDatabase.getResultSetValueToString("select priceAmount from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                GlobalMemberValues.logWrite("paymentcreditcardlog2", "mSelectedSalonSalesCardIdx : " + mSelectedSalonSalesCardIdx + "\n");
                                                GlobalMemberValues.logWrite("paymentcreditcardlog2", "strTid : " + strTid + "\n");
                                                if (!GlobalMemberValues.isStrEmpty(strTid)) {
                                                    voidCardProcess(mSelectedSalonSalesCardIdx, strRefNum, strPriceAmount);
                                                } else {
                                                    GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning",
                                                            "There is no transaction ID", "Close");
                                                    return;
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
                    break;
                }
                case R.id.paymentCreditCardVButton : {
                    // 07.16.2022
                    setClickVBtn();

                    break;
                }
            }
        }
    };

    // 07.16.2022
    public void setClickVBtn() {
        paymentCreditCardVButton.setEnabled(false);

        double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());

        if (GlobalMemberValues.GLOBAL_CARDISLAST == "Y" || GlobalMemberValues.GLOBAL_CARDISLAST.equals("Y")) {
            if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                finishCreditCardProcessing();
            } else {
                paymentCreditCardVButton.setEnabled(true);
                GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning",
                        "Payment has not been completed\nYou have to pay $" + tempBalanceValue + "", "Close");
            }
        } else {
            finishCreditCardProcessing();
        }
    }

    // 06162023
    public void setStartProcessing() {
        // 02132023
        // 프로세싱 버튼을 클릭하면 백그라운드에서 업로드되던 세일 데이터는 일시 정지한다.
        GlobalMemberValues.isProcessCreditCard = true;

        setCreditCardProcess();

        AddPayCustomerSelectPopup.isStartCartProcessing = false;
    }

    public void setForSwipeInsertType() {
        setInitKeyInData();

        //setInsertSwipeKeyin("IS");
        mSelectedInsSwKi = "IS";

        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_insertswipe_rollover);
        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);

        setCardInfo("", "", "", "", "");
    }

    public static void setCardInfo(String paramCardnumber, String paramExpDate, String paramCvvNum, String paramAddr, String paramZipcode) {
        if (!GlobalMemberValues.isStrEmpty(paramCardnumber)) {
            String tempCardNum = paramCardnumber.substring(0, 4) + " " + paramCardnumber.substring(4, 8) + " **** ****";
            paymentCreditCardCardNumberTextView.setText(tempCardNum);

            mKeyinCardNumber = paramCardnumber;
            mKeyinExpDate = paramExpDate;
            mKeyinCVVNumber = paramCvvNum;
            mKeyinAddr = paramAddr;
            mKeyinZip = paramAddr;

            GlobalMemberValues.logWrite("cardinfolog", "mKeyinCardNumber : " + mKeyinCardNumber + "\n");
            GlobalMemberValues.logWrite("cardinfolog", "mKeyinExpDate : " + mKeyinExpDate + "\n");
            GlobalMemberValues.logWrite("cardinfolog", "mKeyinCVVNumber : " + mKeyinCVVNumber + "\n");
        } else {
            setInitKeyInData();
            paymentCreditCardCardNumberTextView.setText("**** **** **** ****");
        }
    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void voidCardSales() {
//        String tempQuery = "select idx, tid, priceAmount from salon_sales_card where salesCode = '" + Payment.mSalesCode + "' ";
//        Cursor salonSalesCardCursor = dbInit.dbExecuteRead(tempQuery);
//        int tempCount = 0;
//        while (salonSalesCardCursor.moveToNext()) {
//            String tempIdx = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(0), 1);
//            String tempTid = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);
//            String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(2), 1);
//            if (!GlobalMemberValues.isStrEmpty(tempIdx) && !GlobalMemberValues.isStrEmpty(tempPriceAmount)
//                    && GlobalMemberValues.getDoubleAtString(tempPriceAmount) > 0) {
//                voidCardProcess(tempIdx, tempTid, tempPriceAmount);
//            }
//            Payment.mJsonList.remove(tempCount);
//            tempCount++;
//        }
//    }

    public static void voidCardProcess(String paramTagValue, String paramRefNum, String paramPriceAmount) {
        if (GlobalMemberValues.isStrEmpty(paramTagValue)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Index Error (Null)", "Close");
            return;
        }

        if (GlobalMemberValues.isStrEmpty(paramRefNum)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Reference Number Error (Null)", "Close");
            return;
        }

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

        // 먼저 전자서명이 있는지 체크후 있으면 삭제한다.
        String tempAuthNum = MssqlDatabase.getResultSetValueToString(
                "select tid from salon_sales_card where idx = '" + paramTagValue + "' "
        );
        if (!GlobalMemberValues.isStrEmpty(tempAuthNum) && !GlobalMemberValues.isStrEmpty(paramRefNum)) {
            String strFilePath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/sign_" + paramTagValue + "_" + tempAuthNum + "_" + paramRefNum + ".png";
            if (!GlobalMemberValues.isStrEmpty(strFilePath)) {
                if (new File(strFilePath).exists() == true) {
                    new File(strFilePath).delete();
                }
            }
        }

        if (GlobalMemberValues.isStrEmpty(paymentGateway)) {
            paymentGateway = "1";
        }

        // Void 처리
        switch (paymentGateway) {
            // Ingenico ICT200 인지니코
            case "0" : {
                break;
            }
            // PAX
            case "1" : {
                if (context == null) {
                    context = MainActivity.mContext;
                }
                if (mActivity == null) {
                    mActivity = MainActivity.mActivity;
                }

                GlobalMemberValues.logWrite("jjjbillvoidlogjjj2", "refnum : " + paramRefNum + "\n");
                GlobalMemberValues.logWrite("jjjbillvoidlogjjj2", "priceamountforvoid : " + paramPriceAmount + "\n");

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
                // -------------------------------------------------------------------------------------
                mActivity.startActivity(intent);

                break;
            }
        }
    }

    public static void setVoidCard(String paramResultCode, String paramSalonSalesCardIdx, String paramPriceAmount) {
        if (paramResultCode.equals("00") || paramResultCode == "00") {
            String returnResult = "";

            String tempSalesCode = MssqlDatabase.getResultSetValueToString("select salesCode from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ");

            Vector<String> strUpdateQueryVec = new Vector<String>();
            String strDeleteQuery = "";
            strDeleteQuery = "delete from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ";
            strUpdateQueryVec.addElement(strDeleteQuery);
            if (JJJ_SignPad.mVoidOnSignPad == "N" || JJJ_SignPad.mVoidOnSignPad.equals("N")) {
                strDeleteQuery = "delete from salon_sales_tip where salesCode = '" + tempSalesCode + "' ";
                strUpdateQueryVec.addElement(strDeleteQuery);
                JJJ_SignPad.mVoidOnSignPad = "N";
            }
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "delete query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // DB 처리도 성공...
                //LinearLayout deleteLn = mSelectedLn;
                if (mSelectedLn != null) {
                    paymentCreditCardProcessingCardListLinearLayout.removeView(mSelectedLn);
                } else {
                    LinearLayout deleteLn = (LinearLayout)paymentCreditCardProcessingCardListLinearLayout.findViewWithTag(paramSalonSalesCardIdx);
                    paymentCreditCardProcessingCardListLinearLayout.removeView(deleteLn);
                }

                double tempDoublePriceAmount = GlobalMemberValues.getDoubleAtString(paramPriceAmount);      // 결제한 카드 금액 (Void 처리한 금액)

                double tempDoublePaidAmount = GlobalMemberValues.getDoubleAtString(paymentCreditCardPaidTextView.getText().toString());
                double tempDoubleBalanceAmount = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());

                // Void 처리한 값 반영
                mTempPaidValue -= tempDoublePriceAmount;

                tempDoublePaidAmount = tempDoublePaidAmount - tempDoublePriceAmount;
                tempDoubleBalanceAmount = tempDoubleBalanceAmount + tempDoublePriceAmount;

                paymentCreditCardPaidTextView.setText(GlobalMemberValues.getStringFormatNumber(tempDoublePaidAmount, "2"));
                paymentCreditCardBalanceTextView.setText(GlobalMemberValues.getStringFormatNumber(tempDoubleBalanceAmount, "2"));
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Void processing error", "Close");
        }
    }

    private void setInsertSwipeKeyin(String paramFlag) {

        switch (paramFlag) {
            case "IS" : {               // Insert/Swipe 선택한 경우
                if (mSelectedInsSwKi.equals("IS")) {

                    mSelectedInsSwKi = "";
                    setFrameVisibility("");

                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
                    } else {
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_insertswipe);
                    }
                } else {
                    mSelectedInsSwKi = "IS";
                    setFrameVisibility("IS");
                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_insertswipe_rollover);
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
                    } else {
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.roundlayout_button_selector_paymentcreditcard);
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_keyin);
                    }
                }

                break;
            }
            case "KI" : {               // Key-in 선택한 경우
                if (mSelectedInsSwKi.equals("KI")) {

                    mSelectedInsSwKi = "";
                    setFrameVisibility("");

                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
                    } else {
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_keyin);
                    }
                } else {
                    mSelectedInsSwKi = "KI";
                    setFrameVisibility("KI");
                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_keyin_rollover);
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
                    } else {
                        paymentCreditCardKeyInButton.setBackgroundResource(R.drawable.roundlayout_button_selector_paymentcreditcard);
                        paymentCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_insertswipe);
                    }
                }

                break;
            }
        }
    }

    private void setFrameVisibility(String paramFlag) {
        if (paramFlag == "") {
            linearLayout_frameMain.setVisibility(View.VISIBLE);
            linearLayout_frameInsertSwipe.setVisibility(View.GONE);
            linearLayout_frameKeyIn.setVisibility(View.GONE);
        } else {
            switch (paramFlag) {
                case "IS" : {
                    linearLayout_frameMain.setVisibility(View.GONE);
                    linearLayout_frameInsertSwipe.setVisibility(View.VISIBLE);
                    linearLayout_frameKeyIn.setVisibility(View.GONE);
                    break;
                }
                case "KI" : {
                    linearLayout_frameMain.setVisibility(View.GONE);
                    linearLayout_frameInsertSwipe.setVisibility(View.GONE);
                    linearLayout_frameKeyIn.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    public static void finishCreditCardProcessing() {
        // 프린트 xml 반영
        if (mCardPaymentJsonArray != null) {
            if (mCardPaymentJsonArray.length > 0) {
                for (int i = 0; i < mCardPaymentJsonArray.length; i++) {
                    if (mCardPaymentJsonArray[i] != null && !GlobalMemberValues.isStrEmpty(mCardPaymentJsonArray[i].toString())) {
                        if (Payment.mJsonList != null) {
                            Payment.mJsonList.put(mCardPaymentJsonArray[i]);
                        }
                    }
                }
            }
        }

        // 9999 수정
//        if (mCardPaymentJsonArrayList != null){
//            if (mCardPaymentJsonArrayList.size() > 0){
//                for (int z = 0; z < mCardPaymentJsonArrayList.size(); z++){
//                    if (mCardPaymentJsonArrayList.get(z) != null && !GlobalMemberValues.isStrEmpty(mCardPaymentJsonArrayList.get(z).toString())){
//                        if (Payment.mJsonList != null){
//                            Payment.mJsonList.put(mCardPaymentJsonArrayList.get(z));
//                        }
//                    }
//                }
//            }
//        }



        String tempInsPaidAmount = "0";
        if (GlobalMemberValues.GLOBAL_CARDISLAST == "Y" || GlobalMemberValues.GLOBAL_CARDISLAST.equals("Y")) {
            tempInsPaidAmount = mBalanceAmountValue;
        } else {
            tempInsPaidAmount = paymentCreditCardPaidTextView.getText().toString();
        }

        if (GlobalMemberValues.getDoubleAtString(tempInsPaidAmount) > 0) {
            Payment.mCardPaidYN = "Y";
        }
        if (Payment.paymentCardEditText != null) {
            Payment.paymentCardEditText.setText(tempInsPaidAmount);
        }
        Payment.setPaymentPrice();

        if (GlobalMemberValues.GLOBAL_CARDISLAST == "Y" || GlobalMemberValues.GLOBAL_CARDISLAST.equals("Y")) {
            try {
                Payment.setFinishProcess();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String tempChange = Payment.paymentChangeEditText.getText().toString();
            if (GlobalMemberValues.getDoubleAtString(tempChange) >= 0) {
                try {
                    Payment.setFinishProcess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        setFinish();
        setInit();
    }

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mTempPriceValue.length() < 12) {
            String tempSu = "";
            sb.append(mTempPriceValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            tempSu = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(tempSu) * 0.01), "2");

            GlobalMemberValues.logWrite("pccnumberButtonClick", "mBalanceAmountValue : " + mBalanceAmountValue + "\n");

            String tempBalance = paymentCreditCardBalanceTextView.getText().toString();
            if (GlobalMemberValues.getDoubleAtString(inputSu) <= GlobalMemberValues.getDoubleAtString(tempBalance)) {
                mTempPriceValue = String.valueOf(tempNumber);
                paymentCreditCardProcessingAmountTextView.setText(inputSu);
            } else {
                GlobalMemberValues.displayDialog(this, "Warning", "Invalid Amount", "Close");
                return;
            }
        }
    }


    public void setCreditCardProcess() {
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

        double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentCreditCardBalanceTextView.getText().toString());
        if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
            GlobalMemberValues.displayDialog(this, "Information", "You do not have to pay any more money", "Close");
        } else {
            // 결제할 금액
            final double PRICEAMT = GlobalMemberValues.getDoubleAtString(
                    paymentCreditCardProcessingAmountTextView.getText().toString()
            );

            if (PRICEAMT > 0 || PRICEAMT > 0.0) {

                // 인지니코일 경우 프로그래스 바 오픈
                if (paymentGateway == "0" || paymentGateway.equals("0")) {
                    proDial = ProgressDialog.show(this, "NAVYZEBRA QSRt POS", "Credit Card Processing...", true, false);
                }

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        /** 1. 처리가 오래걸리는 부분 실행 *****************************************************/
                        // temp_salecart 의 cardtryyn 의 값을 Y 로 변경
                        GlobalMemberValues.setCardTryYNInTempSaleCart("Y");

                        // 카드 결제처리
                        creditCardReturnValueJson = new JSONObject();

                        if (GlobalMemberValues.CARD_DEVICE_TESTVERSION_YN == "Y") {     // 카드 단말기 없는 테스트버전일 떄..
                            // 테스트를 위한 리턴받은 JSONObject 만들기 ------------------------------------------------
                            try {
                                creditCardReturnValueJson.put("returncodefromdevice", "00");
                                creditCardReturnValueJson.put("authnumber", "000123456789");
                                creditCardReturnValueJson.put("cardcompay", "M");
                                creditCardReturnValueJson.put("lastfourdigits", "0551");
                                creditCardReturnValueJson.put("referencenumber", "1");
                                creditCardReturnValueJson.put("emvaid", "");
                                creditCardReturnValueJson.put("emvtsi", "");
                                creditCardReturnValueJson.put("emvtvr", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // -------------------------------------------------------------------------------------

                        } else {                                                        // 카드 단말기 있는 실제버전일 때..
                            // Key in 방식일 경우
                            if (mSelectedInsSwKi.equals("KI")) {
                                if (GlobalMemberValues.isStrEmpty(mKeyinCardNumber) || GlobalMemberValues.isStrEmpty(mKeyinExpDate) || GlobalMemberValues.isStrEmpty(mKeyinCVVNumber)) {
                                    GlobalMemberValues.displayDialog(context, "Warning", " Enter card information...", "Close");
                                    return;
                                }
                            }

                            switch (paymentGateway) {
                                // Ingenico ICT200 인지니코
                                case "0" : {
                                    // 카드결제 관련 터미널 연동 프로세스 ---------------------------------------------------------
                                    RestApiSale.SaleCallbackEvent saleCallbackEvent = new RestApiSale.SaleCallbackEvent() {
                                        @Override
                                        public void Salecallback(JSONObject jsonObject) {
                                            Log.e("SaleCallBack!!!!!",jsonObject.toString());

                                            creditCardReturnValueJson = returnToJsonObject(jsonObject);

                                            handler.sendEmptyMessage(0);
                                        }
                                    };
                                    RestApiSale sale = new RestApiSale(saleCallbackEvent);

                                    RestApiConfig config = new RestApiConfig();
                                    //byullbam 20160419 인제니코 아이피 셋팅 필요.

                                    config.setIpnumber(networkIp);
                                    config.setPortnumber(networkPort);
                                    sale.RequestSale(GlobalMemberValues.getIntAtString2(paymentCreditCardProcessingAmountTextView.getText().toString()));
                                    // -----------------------------------------------------------------------------------------
                                    break;
                                }
                                // PAX POSLink 팍스
                                case "1" : {
                                    GlobalMemberValues.logWrite(TAG, "카드결제할 금액 : -" + GlobalMemberValues.getIntAtString2(paymentCreditCardProcessingAmountTextView.getText().toString()) + "\n");

                                    Intent intent = new Intent(context, JJJ_PaxPay.class);
                                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                                    intent.putExtra("cardtendertype", "CREDIT");
                                    intent.putExtra("processtype", "SALE");
                                    intent.putExtra("amounttopay",
                                            String.valueOf(GlobalMemberValues.getIntAtString2(paymentCreditCardProcessingAmountTextView.getText().toString()))
                                    );
                                    intent.putExtra("refnum", "");
                                    intent.putExtra("ecrrefnum", Payment.mSalesCode);
                                    intent.putExtra("processtype", "SALE");

                                    GlobalMemberValues.logWrite("mSelectedInsSwKiLog", "mSelectedInsSwKi : " + mSelectedInsSwKi + "\n");
                                    // -------------------------------------------------------------------------------------
                                    mActivity.startActivity(intent);
                                    break;
                                }
                            }
                        }
                        /*************************************************************************************/
                        /** 2. 작업이 끝나면 이 핸들러를 호출 **************************************************/
                        // 테스트시에만..
                        if (GlobalMemberValues.CARD_DEVICE_TESTVERSION_YN == "Y") {
                            handler.sendEmptyMessage(0);
                        }
                        /*************************************************************************************/
                    }
                });
                thread.start();
            } else {
                GlobalMemberValues.displayDialog(this, "Information", "Insert price to pay", "Close");
            }
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업(카드 API 연동)이 끝난후 처리해야할 부분을 넣음. ---------------
            try {
                switch (paymentGateway) {
                    // Ingenico ICT200 인지니코
                    case "0": {
                        setAddSalonSalesCardList();
                        break;
                    }
                    /**
                     // PAX POSLink 팍스
                     case "1" : {
                     if (pax_processingResult.equals("000000")) {
                     setAddSalonSalesCardList();
                     } else {
                     GlobalMemberValues.displayDialog(context, "Payment Result Message", pax_processingResultTxt, "Close");
                     }
                     break;
                     }
                     **/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            // 인지니코일 경우 프로그래스 바 닫기
            if (paymentGateway == "0" || paymentGateway.equals("0")) {
                proDial.dismiss();
            }
            // -------------------------------------------------------------------------------------
        }
    };

    // 초기화 메소드
    public static void setInit() {
        paymentCreditCardProcessingAmountTextView.setText("0.0");
        paymentCreditCardBalanceTextView.setText("");
        paymentCreditCardPaidTextView.setText("0.0");
        mCardPaymentJsonArray = null;

        // 9999 수정
//        mCardPaymentJsonArrayList = new ArrayList<JSONObject>();

        mTempPriceValue = "";

        paymentCreditCardVButton.setEnabled(true);

        // 02132023
        GlobalMemberValues.isProcessCreditCard = false;

        setInitKeyInData();
    }

    public JSONObject returnToJsonObject(JSONObject ResponseObject){

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < ResponseObject.length(); i++) {
            try {
                if (ResponseObject.toString().contains("Host Response Code")){
                    jsonObject.put("returncodefromdevice",ResponseObject.getString("Host Response Code"));
                }
                if (ResponseObject.toString().contains("Authorization Num")){
                    jsonObject.put("authnumber",ResponseObject.getString("Authorization Num"));
                }
                if (ResponseObject.toString().contains("Customer Card Type")){
                    if (ResponseObject.getString("Customer Card Type").equals("Visa")){
                        jsonObject.put("cardcompay","V");
                    } else if (ResponseObject.getString("Customer Card Type").equals("MasterCard")){
                        jsonObject.put("cardcompay","M");
                    } else if (ResponseObject.getString("Customer Card Type").equals("Amex")){
                        jsonObject.put("cardcompay","A");
                    } else if (ResponseObject.getString("Customer Card Type").equals("Discover Card")){
                        jsonObject.put("cardcompay","D");
                    } else{
                        jsonObject.put("cardcompay",ResponseObject.getString("Customer Card Type").substring(0,1));
                    }

                }
                if (ResponseObject.toString().contains("Customer Acount Num")){
                    jsonObject.put("lastfourdigits",ResponseObject.getString("Customer Acount Num").replaceAll("[*]", ""));
                }
                if (ResponseObject.toString().contains("Reference Num")) {
                    jsonObject.put("referencenumber",ResponseObject.getString("Reference Num"));
                }

                if (ResponseObject.toString().contains("Customer Card Entry Mode")){
                    if (ResponseObject.getString("Customer Card Entry Mode").equals("Insert (Chip)")){
                        if (ResponseObject.toString().contains("EMV AID")){
                            jsonObject.put("emvaid",ResponseObject.getString("EMV AID"));
                        }else{
                            jsonObject.put("emvaid","");
                        }
                        if (ResponseObject.toString().contains("EMV TSI")){
                            jsonObject.put("emvtsi",ResponseObject.getString("EMV TSI"));
                        }else{
                            jsonObject.put("emvtsi","");
                        }
                        if (ResponseObject.toString().contains("EMV TVR")){
                            jsonObject.put("emvtvr",ResponseObject.getString("EMV TVR"));
                        }else{
                            jsonObject.put("emvtvr","");
                        }
                    }
                }

                if (ResponseObject.toString().contains("Transaction Type")){
                    if (ResponseObject.getString("Transaction Type").equals("-1")){
                        jsonObject.put("returncodefromdevice",ResponseObject.getString("Transaction Type"));
                    }
                    if (ResponseObject.getString("Transaction Type").equals("Refund")){
                        if (ResponseObject.getString("Transaction Status").equals("Approved")){
                            jsonObject.put("returncodefromdevice","00");
                        }
                    }
                }

                if (ResponseObject.toString().contains("Transaction Status")){
                    if (ResponseObject.toString().contains("Transaction Status")){
                        jsonObject.put("TransactionStatus",ResponseObject.getString("Transaction Status"));
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("byulbam.test add 0504", jsonObject.toString());

        return jsonObject;
    }

    public static void setInitKeyInData() {
        mKeyinCardNumber = "";
        mKeyinExpDate = "";
        mKeyinCVVNumber = "";
        mKeyinAddr = "";
        mKeyinZip = "";
    }

    // Back Key 막기 위해 onKeyDown 메소드 Override
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void setFinishActivity(){
        if (PaymentCreditCard.mActivity != null){
            setFinish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 07.16.2022 -------------------------------------------------------------------
        if (GlobalMemberValues.mNotPayYNOnCard) {
            GlobalMemberValues.mNotPayYNOnCard = false;

            String tempMsg = GlobalMemberValues.mNotPayYNOnCardMsg;

            new AlertDialog.Builder(context)
                    .setTitle("Balance Check")
                    .setMessage(tempMsg)
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Continue",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    new AlertDialog.Builder(context)
                                            .setTitle("Balance Check")
                                            .setMessage(tempMsg)
                                            .setNegativeButton("Pay in Cash",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            setClickVBtn();

                                                            Payment.setClickCashLn();
                                                        }
                                                    })
                                            .setPositiveButton("Pay in Card", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    setPayAmountBalance();
                                                }
                                            })
                                            .show();


                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GlobalMemberValues.logWrite("jjjcardgcjjjlog", "여기에..jjj" + "\n");

                            mSelectedSalonSalesCardIdx = MssqlDatabase.getResultSetValueToString("select top 1 idx from salon_sales_card order by idx desc");

                            // tid 가져오기
                            String strTid = "";
                            strTid = MssqlDatabase.getResultSetValueToString("select tid from salon_sales_card where idx = '"
                                    + mSelectedSalonSalesCardIdx + "' ");
                            String strRefNum = "";
                            strRefNum = MssqlDatabase.getResultSetValueToString("select cardRefNumber from salon_sales_card where idx = '"
                                    + mSelectedSalonSalesCardIdx + "' ");
                            String strPriceAmount = "";
                            strPriceAmount = MssqlDatabase.getResultSetValueToString("select priceAmount from salon_sales_card where idx = '"
                                    + mSelectedSalonSalesCardIdx + "' ");

                            if (!GlobalMemberValues.isStrEmpty(strTid)) {
                                voidCardProcess(mSelectedSalonSalesCardIdx, strRefNum, strPriceAmount);
                            } else {
                                GlobalMemberValues.displayDialog(PaymentCreditCard.this, "Warning",
                                        "There is no transaction ID", "Close");
                                return;
                            }

                        }
                    })
                    .show();

            GlobalMemberValues.mNotPayYNOnCardMsg = "";
        }
        // ---------------------------------------------------------------------------------------
    }

    static Handler mProgress_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (PaymentCreditCard.mProgressDialog != null){
                PaymentCreditCard.mProgressDialog.dismiss();
                PaymentCreditCard.mProgressDialog = null;
            }
        }
    };

    static Handler getmProgress_handler_start = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PaymentCreditCard.mProgressDialog = new ProgressDialog(context);
            PaymentCreditCard.mProgressDialog.show();
        }
    };


    public static void setFinish() {
        // 02132023
        GlobalMemberValues.isProcessCreditCard = false;

        mActivity.finish();
    }

    @Override
    protected void onDestroy() {
        if (PaymentCreditCard.mProgressDialog != null){
            PaymentCreditCard.mProgressDialog.dismiss();
            PaymentCreditCard.mProgressDialog = null;
        }
        super.onDestroy();
    }
}
