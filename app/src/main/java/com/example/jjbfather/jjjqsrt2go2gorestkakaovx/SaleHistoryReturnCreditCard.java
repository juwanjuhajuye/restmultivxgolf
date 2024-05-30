package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byullbam.restapi.RestApiConfig;
import com.byullbam.restapi.RestApiRefund;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class SaleHistoryReturnCreditCard extends Activity {
    static final String TAG = "SaleHistoryReturnCreditCardReturn";

    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private LinearLayout parentLn;

    private static LinearLayout returnCreditCardProcessingCardListLinearLayout;

    private LinearLayout insertswipekeyinLinearLayout;

    private Button closeBtn;

    private Button returnCreditCardSuButton1,returnCreditCardSuButton2,returnCreditCardSuButton3;
    private Button returnCreditCardSuButton4,returnCreditCardSuButton5,returnCreditCardSuButton6;
    private Button returnCreditCardSuButton7,returnCreditCardSuButton8,returnCreditCardSuButton9;
    private Button returnCreditCardSuButton0,returnCreditCardSuButton00,returnCreditCardSuButtonBack;

    private Button returnCreditCardInsertSwipeButton, returnCreditCardKeyInButton;
    private Button returnCreditCardCardProcessButton, returnCreditCardVoidButton, returnCreditCardVButton;

    private static TextView returnCreditCardProcessingAmountTextView;
    private TextView returnCreditCardAmountTextView;
    private static TextView returnCreditCardBalanceTextView;
    private static TextView returnCreditCardPaidTextView;

    private LinearLayout returnCreditCardContentsLinearLayout;

    private TextView returnCreditCardTopTitleTextView;
    private TextView returnCreditCardTitleTextView;

    private TextView returnCreditCardProcessingAmountTitleTextView;
    private TextView returnCreditCardCardNumberTitleTextView;
    private TextView returnCreditCardCardNumberTextView;

    private TextView returnCreditCardAmountTitleTextView;
    private TextView returnCreditCardBalanceTitleTextView;
    private TextView returnCreditCardPaidTitleTextView;

    private LinearLayout linearLayout_frameMain;
    private LinearLayout linearLayout_frameInsertSwipe;
    private LinearLayout linearLayout_frameKeyIn;


    Intent mIntent;
    static String mBalanceAmountValue = "0.0";

    // Paid 증가값
    static double mTempPaidValue = 0.0;

    // 임시저장값
    static String mTempPriceValue = "";

    // 선택한 카드 결제내역의 salon_sales_card 의 인덱스값
    static String mSelectedSalonSalesCardIdx = "";

    static LinearLayout mSelectedLn;

    static String mSelectedInsSwKi = "";

    // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
    static JSONObject jsontmp = null;

    // 카드결제 연동 관련 변수 -------------------------------
    public static ProgressDialog proDial;   // 프로그래스바
    public static JSONObject creditCardReturnValueJson;   // 결제처리후 리턴값
    // ----------------------------------------------------

    // insert/swipe, keyin 버튼을 보여줄지 여부
    String insertSwipeKeyinButtonViewYN = "N";

    // Return Sales Code
    static String mReturnSalesCode = "";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_return_credit_card);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 8;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
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

        parentLn = (LinearLayout)findViewById(R.id.returnCreditCardLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 리턴 세일즈코드
            mReturnSalesCode = mIntent.getStringExtra("returnSalesCode");
            // 부모로부터 받은 Balance 값
            mBalanceAmountValue = mIntent.getStringExtra("returnCreditCardPopupAmount");
            GlobalMemberValues.logWrite("salehistoryreturnforcard", "넘겨받은 returnSalesCode : " + mReturnSalesCode + "\n");
            GlobalMemberValues.logWrite("salehistoryreturnforcard", "넘겨받은 mBalanceAmountValue : " + mBalanceAmountValue + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
    }

    public void setContents() {
        // 카드 프로세싱 스텝 초기화
        GlobalMemberValues.CARD_PROCESSING_STEP = 0;

        mTempPaidValue = 0.0;

        ConfigPGInfo pgInfo = new ConfigPGInfo();
        paymentGateway = pgInfo.cfpaymentgateway;
        networkIp = pgInfo.cfnetworkip;
        networkPort = pgInfo.cfnetworkport;

        dbInit = new DatabaseInit(this);

        returnCreditCardContentsLinearLayout = (LinearLayout)findViewById(R.id.returnCreditCardContentsLinearLayout);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            returnCreditCardContentsLinearLayout.setPadding(3, 0, 3, 0);
        }

        returnCreditCardTitleTextView = (TextView)findViewById(R.id.returnCreditCardTitleTextView);
        returnCreditCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardProcessingAmountTitleTextView = (TextView)findViewById(R.id.returnCreditCardProcessingAmountTitleTextView);
        returnCreditCardProcessingAmountTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardProcessingAmountTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardCardNumberTitleTextView = (TextView)findViewById(R.id.returnCreditCardCardNumberTitleTextView);
        returnCreditCardCardNumberTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardCardNumberTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardCardNumberTextView = (TextView)findViewById(R.id.returnCreditCardCardNumberTextView);
        returnCreditCardCardNumberTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardCardNumberTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardBalanceTitleTextView = (TextView)findViewById(R.id.returnCreditCardBalanceTitleTextView);
        returnCreditCardBalanceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardBalanceTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardPaidTitleTextView = (TextView)findViewById(R.id.returnCreditCardPaidTitleTextView);
        returnCreditCardPaidTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardPaidTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardAmountTitleTextView = (TextView)findViewById(R.id.returnCreditCardAmountTitleTextView);
        returnCreditCardAmountTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardAmountTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );


        returnCreditCardBalanceTextView = (TextView)findViewById(R.id.returnCreditCardBalanceTextView);
        returnCreditCardBalanceTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardBalanceTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardPaidTextView = (TextView)findViewById(R.id.returnCreditCardPaidTextView);
        returnCreditCardPaidTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardPaidTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardAmountTextView = (TextView)findViewById(R.id.returnCreditCardAmountTextView);
        returnCreditCardAmountTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardAmountTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("returnCreditCardCloseBtnTag");
        closeBtn.setOnClickListener(returnCreditCardBtnClickListener);
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

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        returnCreditCardSuButton1 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton1.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton2 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton2.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton3 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton3.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton4 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton4.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton5 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton5.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton6 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton6.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton7 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton7.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton8 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton8.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton9 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton9.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton0 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton0.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButton00 = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            returnCreditCardSuButton00.setTextColor(Color.parseColor("#37383D"));
            returnCreditCardSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_number);
        } else {
            returnCreditCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        returnCreditCardSuButtonBack = (Button)parentLn
                .findViewWithTag("returnCreditCardSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardSuButtonBack.setText("");
            returnCreditCardSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_delete);
        } else {
            returnCreditCardSuButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardSuButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        returnCreditCardSuButton1.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton2.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton3.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton4.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton5.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton6.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton7.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton8.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton9.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton0.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButton00.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardSuButtonBack.setOnClickListener(returnCreditCardBtnClickListener);

        returnCreditCardCardProcessButton = (Button)parentLn
                .findViewWithTag("returnCreditCardCardProcessButtonTag");
        returnCreditCardCardProcessButton.setOnClickListener(returnCreditCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardCardProcessButton.setText("");
            returnCreditCardCardProcessButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_cardprocessing);
        } else {
            returnCreditCardCardProcessButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardCardProcessButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        /** Insert/Swipe, Key-In 버튼 객체 생성 및 리스너 연결 ******************************************************/
        returnCreditCardInsertSwipeButton = (Button)parentLn
                .findViewWithTag("returnCreditCardInsertSwipeButtonTag");
        returnCreditCardInsertSwipeButton.setOnClickListener(returnCreditCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardInsertSwipeButton.setText("");
            returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
        } else {
            returnCreditCardInsertSwipeButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardInsertSwipeButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        returnCreditCardKeyInButton = (Button)parentLn
                .findViewWithTag("returnCreditCardKeyInButtonTag");
        returnCreditCardKeyInButton.setOnClickListener(returnCreditCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardKeyInButton.setText("");
            returnCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
        } else {
            returnCreditCardKeyInButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardKeyInButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/


        /** Void, V 버튼 객체 생성 및 리스너 연결 ******************************************************************/
        returnCreditCardVoidButton = (Button)parentLn
                .findViewWithTag("returnCreditCardVoidButtonTag");
        returnCreditCardVoidButton.setOnClickListener(returnCreditCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardVoidButton.setText("");
            returnCreditCardVoidButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_void);
        } else {
            returnCreditCardVoidButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardVoidButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        returnCreditCardVButton = (Button)parentLn
                .findViewWithTag("returnCreditCardVButtonTag");
        returnCreditCardVButton.setOnClickListener(returnCreditCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            returnCreditCardVButton.setText("");
            returnCreditCardVButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_enter);
        } else {
            returnCreditCardVButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    returnCreditCardVButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/


        /** Processing Amount, Balance, Paid TextView 객체 생성 및 리스너 ******************************************/
        returnCreditCardProcessingAmountTextView = (TextView)parentLn
                .findViewWithTag("returnCreditCardProcessingAmountTextViewTag");
        returnCreditCardProcessingAmountTextView.setOnClickListener(returnCreditCardBtnClickListener);
        returnCreditCardProcessingAmountTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                returnCreditCardProcessingAmountTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        returnCreditCardAmountTextView = (TextView)parentLn
                .findViewWithTag("returnCreditCardAmountTextViewTag");
        returnCreditCardBalanceTextView = (TextView)parentLn
                .findViewWithTag("returnCreditCardBalanceTextViewTag");
        returnCreditCardPaidTextView = (TextView)parentLn
                .findViewWithTag("returnCreditCardPaidTextViewTag");
        /***********************************************************************************************************/

        // 객체 및 색상 초기화
        setInit();

        /** Amount 초기화 *******************************************************************************************/
        returnCreditCardAmountTextView.setText(mBalanceAmountValue);
        returnCreditCardBalanceTextView.setText(mBalanceAmountValue);
        /***********************************************************************************************************/

        // ScrollView 에 속한 첫번째 LinearLayout 객체
        returnCreditCardProcessingCardListLinearLayout = (LinearLayout)findViewById(R.id.returnCreditCardProcessingCardListLinearLayout);

        // insert/swipe, keyin 버튼이 있는 LinearLayount 객체
        insertswipekeyinLinearLayout = (LinearLayout)findViewById(R.id.insertswipekeyinLinearLayout);
        // insert/swipe, keyin 버튼을 보여줄지 여부
        if (insertSwipeKeyinButtonViewYN == "Y") {
            insertswipekeyinLinearLayout.setVisibility(View.VISIBLE);
        } else {
            insertswipekeyinLinearLayout.setVisibility(View.GONE);
        }

        returnCreditCardProcessingCardListLinearLayout = (LinearLayout)findViewById(R.id.returnCreditCardProcessingCardListLinearLayout);

        linearLayout_frameMain = (LinearLayout)findViewById(R.id.linearLayout_frameMain);
        linearLayout_frameInsertSwipe = (LinearLayout)findViewById(R.id.linearLayout_frameInsertSwipe);
        linearLayout_frameInsertSwipe.setBackgroundResource(R.drawable.roundlayout_button_paymentcreditcard_insertswipe);
        linearLayout_frameKeyIn = (LinearLayout)findViewById(R.id.linearLayout_frameKeyIn);
        linearLayout_frameKeyIn.setBackgroundResource(R.drawable.roundlayout_button_paymentcreditcard_insertswipe);
    }

    public static void setAddSalonSalesCardList() throws JSONException {
        String insInsertSwipeKeyin = mSelectedInsSwKi;
        if (GlobalMemberValues.isStrEmpty(insInsertSwipeKeyin)) {
            insInsertSwipeKeyin = "IS";
        }

        // 이 부분에는 카드연동처리 관련코드를 작성한다. --------------------------------------------------------------
        String returnCodeFromCardModule = "00";     // 카드결제결과 리턴값

        // 리턴받은 카드결제 Transaction 정보
        String insSalesCode = mReturnSalesCode;
        String insTid = "00000000000";      // Auth Number
        String insCardCom = "V";
        String cardCompanyName = "VISA";
        String insStatus = "0";
        double insPriceAmount = GlobalMemberValues.getDoubleAtString(
                returnCreditCardProcessingAmountTextView.getText().toString()
        );
        String insCardLastFourDigitNumbers = "0000";    // 사용한 카드 뒤 4자리
        String insCardRefNumber = "1";                  // 레퍼런스 값

        String insCardEmvAid = "A00000000000";          // EMV 결제시 리턴받은 AID 값
        String insCardEmvTsi = "A00000000000";          // EMV 결제시 리턴받은 TSI 값
        String insCardEmvTvr = "A00000000000";          // EMV 결제시 리턴받은 TVR 값

        String tempTransactionType = "";                // 결제디바이스(터미널) 연결여부 체크값
        String tempTransactionStatus = "";              // 결제디바이스(터미널) 연결여부 체크상태

        if (creditCardReturnValueJson != null) {
            GlobalMemberValues.logWrite("creditCardProcessingTest", "여기까지 오면 성공..\n");

            if (creditCardReturnValueJson.toString().contains("Transaction Type")){
                tempTransactionType = creditCardReturnValueJson.getString("Transaction Type");
            } else {
                tempTransactionType = "-1";
            }
            if (creditCardReturnValueJson.toString().contains("Transaction Status")){
                tempTransactionStatus = creditCardReturnValueJson.getString("Transaction Status");
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
            GlobalMemberValues.logWrite("creditCardProcessingTest", "결과값 : " + returnCodeFromCardModule + "\n");
        } else {
            GlobalMemberValues.logWrite("creditCardProcessingTest", "여기왔으면 실패..\n");
            returnCodeFromCardModule = "99";
            insTid = "";
            insCardCom = "";
            insCardLastFourDigitNumbers = "";
            insCardRefNumber = "";
            insCardEmvAid = "";
            insCardEmvTsi = "";
            insCardEmvTvr = "";
        }
        // ------------------------------------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("returnCreditCardresultlog", "카드처리 결과값 : " + returnCodeFromCardModule + "\n");

        if (returnCodeFromCardModule == "00" || returnCodeFromCardModule.equals("00")) {     // 카드프로세싱이 성공했을 경우에
            GlobalMemberValues.logWrite("returnCreditCardlog", "여기까지2\n");

            // salon_sales_card 테이블에 저장한다. ----------------------------------------------------------
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strInsSqlQuery = "";
            String returnResult = "";

            // 원래 sale 의 직원 idx, name 가져오기
            String tempEmpIdx = MssqlDatabase.getResultSetValueToString(
                    " select employeeIdx from salon_sales_card where salesCode = 'K" + insSalesCode.substring(1) + "' "
            );
            String tempEmpName = MssqlDatabase.getResultSetValueToString(
                    " select employeeName from salon_sales_card where salesCode = 'K" + insSalesCode.substring(1) + "' "
            );


            // 05302024
            // 리턴시 원래 tip 금액 저장
            String tempOrgTip = MssqlDatabase.getResultSetValueToString(
                    " select tipAmount from salon_sales_card where salesCode = 'K" + insSalesCode.substring(1) + "' "
            );
            if (GlobalMemberValues.getDoubleAtString(tempOrgTip) == 0) {
                tempOrgTip = "0.0";
            }


            strInsSqlQuery = "insert into salon_sales_card (" +
                    " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                    " cardLastFourDigitNumbers, cardRefNumber, " +
                    " cardEmvAid, cardEmvTsi, cardEmvTvr, " +
                    " returnCode, employeeIdx, employeeName, " +
                    " orgTip " +
                    " ) values ( " +
                    " '" + insSalesCode + "', " +
                    " '" + insTid + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + insCardCom + "', " +
                    " '" + (insPriceAmount * -1) + "', " +              // 리턴결제이므로 마이너스
                    " '" + insInsertSwipeKeyin + "', " +
                    " '" + insStatus + "', " +
                    " '" + insCardLastFourDigitNumbers + "', " +
                    " '" + insCardRefNumber + "', " +
                    " '" + insCardEmvAid + "', " +
                    " '" + insCardEmvTsi + "', " +
                    " '" + insCardEmvTvr + "', " +
                    " '" + SaleHistoryReturn.mStrReturnCode + "', " +
                    " '" + tempEmpIdx + "', " +
                    " '" + tempEmpName + "', " +
                    " '" + tempOrgTip + "' " +
                    ")";
            // salon_sales_card 입력쿼리를 백터 strInsertQueryVec 에 담는다.
            strInsertQueryVec.addElement(strInsSqlQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("returnCreditCardQueryString", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
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
                tempSalonSalesCardIdx = dbInit.dbExecuteReadReturnString("select idx from salon_sales_card order by idx desc limit 1");

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
                newLn.setPadding(15, 15, 15, 15);

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
                tempProcessingAmount = returnCreditCardProcessingAmountTextView.getText().toString();

                // TextView 객체 생성 및 붙이기 --------------------------------------------------
                TextView salonSalesCardListAmountTv = new TextView(context);
                salonSalesCardListAmountTv.setLayoutParams(subNewTvLayoutParams);
                salonSalesCardListAmountTv.setGravity(Gravity.CENTER);
                salonSalesCardListAmountTv.setPadding(0, 0, 0, 0);
                salonSalesCardListAmountTv.setText("-" + tempProcessingAmount);         // 리턴결제이므로 마이너스 표시
                salonSalesCardListAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);
                salonSalesCardListAmountTv.setTextColor(Color.parseColor(GlobalMemberValues.RETURNPRICEAMOUNT_TEXTCOLOR));
                newLn.addView(salonSalesCardListAmountTv);
                // -------------------------------------------------------------------------------

                returnCreditCardProcessingCardListLinearLayout.addView(newLn);

                double tempProcessingAmountDouble = GlobalMemberValues.getDoubleAtString(tempProcessingAmount);

                if (tempProcessingAmountDouble > 0) {
                    // Paid 증가값 반영
                    mTempPaidValue += tempProcessingAmountDouble;

                    // Balance 수정
                    double tempBalance = GlobalMemberValues.getDoubleAtString(mBalanceAmountValue) - mTempPaidValue;
                    returnCreditCardBalanceTextView.setText(GlobalMemberValues.getStringFormatNumber(tempBalance, "2"));

                    // Paid TextView 에 값 할당
                    returnCreditCardPaidTextView.setText(GlobalMemberValues.getStringFormatNumber(mTempPaidValue, "2"));

                    // Processing Amount 값 초기화
                    returnCreditCardProcessingAmountTextView.setText("0.00");
                    mTempPriceValue = "";
                }

                // 영수증 출력을 위한 JSON 관련 --------------------------------------------------------------------
                jsontmp = new JSONObject();
                jsontmp.put("cardtype", cardCompanyName);
                jsontmp.put("creditcardnumber", insCardLastFourDigitNumbers);
                jsontmp.put("chargeamount", GlobalMemberValues.getStringFormatNumber(insPriceAmount, "2"));
                jsontmp.put("cardauthnumber", insTid);
                jsontmp.put("cardrefnumber", insCardRefNumber);
                jsontmp.put("cardaid", insCardEmvAid);
                jsontmp.put("cardtsi", insCardEmvTsi);
                jsontmp.put("cardtvr", insCardEmvTvr);

                SaleHistoryReturn.mJsonList.put(jsontmp);
                // ----------------------------------------------------------------------------------------------
            }
            // --------------------------------------------------------------------------------------------
        } else {     // 카드프로세싱이 실패했을 경우에
            String tempDpMsgStr = "";
            tempDpMsgStr = GlobalMemberValues.getCreditCardErrors(returnCodeFromCardModule);
            GlobalMemberValues.displayDialog(context, "Warning", tempDpMsgStr, "Close");
            return;
        }

        GlobalMemberValues.logWrite("returnCreditCardlog", "여기까지3\n");
    }

    View.OnClickListener returnCreditCardBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.returnCreditCardCloseBtn : {
                    /**
                    // 카드결제가 있는지 체크한다.
                    String tempQuery = "select count(idx) from salon_sales_card where salesCode = '" + mReturnSalesCode + "' ";
                    int tempSalonSalesCardCount = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(tempQuery));
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
                    double tempPaidAmount = GlobalMemberValues.getDoubleAtString(returnCreditCardPaidTextView.getText().toString());
                    if (tempPaidAmount > 0 || tempPaidAmount > 0.0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You can not close the window because the amount returned", "Close");
                    } else {
                        finish();
                        // 초기화
                        setInit();
                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    }

                    break;
                }
                case R.id.returnCreditCardSuButton1 : {
                    numberButtonClick(returnCreditCardSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton2 : {
                    numberButtonClick(returnCreditCardSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton3 : {
                    numberButtonClick(returnCreditCardSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton4 : {
                    numberButtonClick(returnCreditCardSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton5 : {
                    numberButtonClick(returnCreditCardSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton6 : {
                    numberButtonClick(returnCreditCardSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton7 : {
                    numberButtonClick(returnCreditCardSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton8 : {
                    numberButtonClick(returnCreditCardSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton9 : {
                    numberButtonClick(returnCreditCardSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton0 : {
                    numberButtonClick(returnCreditCardSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButton00 : {
                    numberButtonClick(returnCreditCardSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardSuButtonBack : {
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
                    returnCreditCardProcessingAmountTextView.setText(inputSu);

                    GlobalMemberValues.logWrite("returnCreditCard", "returnCreditCard mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), returnCreditCardProcessingAmountTextView);
                    break;
                }
                case R.id.returnCreditCardInsertSwipeButton : {
                    double tempBalanceValue = GlobalMemberValues.getDoubleAtString(returnCreditCardBalanceTextView.getText().toString());
                    if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                        GlobalMemberValues.displayDialog(SaleHistoryReturnCreditCard.this, "Warning", "You do not have to pay any more money", "Close");
                        return;
                    }
                    //setInsertSwipeKeyin("IS");
                    setCreditCardProcess();
                    mSelectedInsSwKi = "IS";
                    break;
                }
                case R.id.returnCreditCardKeyInButton : {
                    double tempBalanceValue = GlobalMemberValues.getDoubleAtString(returnCreditCardBalanceTextView.getText().toString());
                    if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                        GlobalMemberValues.displayDialog(SaleHistoryReturnCreditCard.this, "Warning", "You do not have to pay any more money", "Close");
                        return;
                    }
                    //setInsertSwipeKeyin("KI");
                    setCreditCardProcess();
                    mSelectedInsSwKi = "KI";
                    break;
                }
                case R.id.returnCreditCardCardProcessButton : {
                    setCreditCardProcess();
                    break;
                }
                case R.id.returnCreditCardVoidButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedSalonSalesCardIdx)) {
                        GlobalMemberValues.displayDialog(SaleHistoryReturnCreditCard.this, "Warning", "Select the card to void", "Close");
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
                                                strTid = dbInit.dbExecuteReadReturnString("select tid from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                String strRefNum = "";
                                                strRefNum = dbInit.dbExecuteReadReturnString("select cardRefNumber from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                String strPriceAmount = "";
                                                strPriceAmount = dbInit.dbExecuteReadReturnString("select priceAmount from salon_sales_card where idx = '"
                                                        + mSelectedSalonSalesCardIdx + "' ");
                                                GlobalMemberValues.logWrite("returnCreditCardlog2", "mSelectedSalonSalesCardIdx : " + mSelectedSalonSalesCardIdx + "\n");
                                                GlobalMemberValues.logWrite("returnCreditCardlog2", "strTid : " + strTid + "\n");
                                                if (!GlobalMemberValues.isStrEmpty(strTid)) {
                                                    voidCardProcess(mSelectedSalonSalesCardIdx, strRefNum, strPriceAmount);
                                                    //setVoidCard(mSelectedSalonSalesCardIdx, strTid, strPriceAmount);
                                                } else {
                                                    GlobalMemberValues.displayDialog(SaleHistoryReturnCreditCard.this, "Warning",
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
                case R.id.returnCreditCardVButton : {
                    double tempBalanceValue = GlobalMemberValues.getDoubleAtString(returnCreditCardBalanceTextView.getText().toString());
                    if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
                        new AlertDialog.Builder(context)
                                .setTitle("Item Delete")
                                .setMessage("Are you sure you want to return?")
                                        //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finishCreditCardProcessing();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    } else {
                        GlobalMemberValues.displayDialog(SaleHistoryReturnCreditCard.this, "Warning",
                                "Payment has not been completed\nYou have to pay $" + tempBalanceValue + "", "Close");
                    }

                    break;
                }
            }
        }
    };

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

        // 먼저 Void 처리부터..
        switch (paymentGateway) {
            // Ingenico ICT200 인지니코
            case "0" : {
                break;
            }
            // PAX
            case "1" : {
                Intent intent = new Intent(context, JJJ_PaxPay.class);
                // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                intent.putExtra("cardtendertype", "CREDIT");
                intent.putExtra("processtype", "VOID");
                intent.putExtra("voidtypereturnyn", "Y");
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

    public static void setVoidCard(String paramResultCode, String paramSalonSalesCardIdx, String paramPriceAmount, String paramTagValue) {
        if (paramResultCode.equals("00") || paramResultCode == "00") {
            String returnResult = "";
            Vector<String> strUpdateQueryVec = new Vector<String>();
            String strUpdateQuery = "";
            strUpdateQuery = "update salon_sales_card set status = '1' where idx = '" + paramSalonSalesCardIdx + "' ";
            strUpdateQueryVec.addElement(strUpdateQuery);
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "update query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // DB 처리도 성공...
                LinearLayout deleteLn = (LinearLayout)returnCreditCardProcessingCardListLinearLayout.findViewWithTag(paramTagValue);
                returnCreditCardProcessingCardListLinearLayout.removeView(deleteLn);

                double tempDoublePriceAmount = GlobalMemberValues.getDoubleAtString(paramPriceAmount) * -1;      // 결제한 카드 금액 (Void 처리한 금액)

                double tempDoublePaidAmount = GlobalMemberValues.getDoubleAtString(returnCreditCardPaidTextView.getText().toString());
                double tempDoubleBalanceAmount = GlobalMemberValues.getDoubleAtString(returnCreditCardBalanceTextView.getText().toString());

                // Void 처리한 값 반영
                mTempPaidValue -= tempDoublePriceAmount;

                tempDoublePaidAmount = tempDoublePaidAmount - tempDoublePriceAmount;
                tempDoubleBalanceAmount = tempDoubleBalanceAmount + tempDoublePriceAmount;

                returnCreditCardPaidTextView.setText(GlobalMemberValues.getStringFormatNumber(tempDoublePaidAmount, "2"));
                returnCreditCardBalanceTextView.setText(GlobalMemberValues.getStringFormatNumber(tempDoubleBalanceAmount, "2"));
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Void processing error", "Close");
        }
    }

    private void setInsertSwipeKeyin(String paramFlag) {

        switch (paramFlag) {
            case "IS" : {               // Insert/Swipe 선택한 경우
                if (mSelectedInsSwKi == "IS") {

                    mSelectedInsSwKi = "";
                    setFrameVisibility("");

                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
                    } else {
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_insertswipe);
                    }
                } else {
                    mSelectedInsSwKi = "IS";
                    setFrameVisibility("IS");
                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_insertswipe_rollover);
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
                    } else {
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.roundlayout_button_selector_paymentcreditcard);
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_keyin);
                    }
                }

                break;
            }
            case "KI" : {               // Key-in 선택한 경우
                if (mSelectedInsSwKi == "KI") {

                    mSelectedInsSwKi = "";
                    setFrameVisibility("");

                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_keyin);
                    } else {
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_keyin);
                    }
                } else {
                    mSelectedInsSwKi = "KI";
                    setFrameVisibility("KI");
                    if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.aa_images_paymentcreditcard_keyin_rollover);
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.ab_imagebutton_paymentcreditcard_insertswipe);
                    } else {
                        returnCreditCardKeyInButton.setBackgroundResource(R.drawable.roundlayout_button_selector_paymentcreditcard);
                        returnCreditCardInsertSwipeButton.setBackgroundResource(R.drawable.button_selector_paymentcreditcard_insertswipe);
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

    private void finishCreditCardProcessing() {
        SaleHistoryReturn.saleHistoryReturnCardEditText.setText(mBalanceAmountValue);
        SaleHistoryReturn.setSaleHistoryReturnPrice();
        SaleHistoryReturn.setReturnFinishProcess(1);
        this.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
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

            String tempBalance = returnCreditCardBalanceTextView.getText().toString();
            if (GlobalMemberValues.getDoubleAtString(inputSu) <= GlobalMemberValues.getDoubleAtString(tempBalance)) {
                mTempPriceValue = String.valueOf(tempNumber);
                returnCreditCardProcessingAmountTextView.setText(inputSu);
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

        double tempBalanceValue = GlobalMemberValues.getDoubleAtString(returnCreditCardBalanceTextView.getText().toString());
        if (tempBalanceValue == 0 || tempBalanceValue == 0.0) {
            GlobalMemberValues.displayDialog(this, "Information", "You do not have to pay any more money", "Close");
        } else {
            // 결제할 금액
            final double PRICEAMT = GlobalMemberValues.getDoubleAtString(
                    returnCreditCardProcessingAmountTextView.getText().toString()
            );

            if (PRICEAMT > 0 || PRICEAMT > 0.0) {
                // 프로그래스 바를 실행~
                if (paymentGateway == "0" || paymentGateway.equals("0")) {
                    proDial = ProgressDialog.show(this, "NAVYZEBRA MSALON ANDROID", "Credit Card Processing...", true, false);
                }

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        /** 1. 처리가 오래걸리는 부분 실행 *****************************************************/
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
                            switch (paymentGateway) {
                                // Ingenico ICT200 인지니코
                                case "0" : {
                                    // 카드결제 관련 터미널 연동 프로세스 ---------------------------------------------------------
                                    RestApiRefund.RefundCallbackEvent refundCallbackEvent = new RestApiRefund.RefundCallbackEvent() {
                                        @Override
                                        public void Refundcallback(JSONObject jsonObject) {
                                            Log.e("SaleCallBack!!!!!",jsonObject.toString());
                                            creditCardReturnValueJson = returnToJsonObject(jsonObject);
                                            handler.sendEmptyMessage(0);
                                        }
                                    };
                                    RestApiRefund refund = new RestApiRefund(refundCallbackEvent);

                                    RestApiConfig config = new RestApiConfig();
                                    //byullbam 20160419 인제니코 아이피 셋팅 필요.
                                    ConfigPGInfo pgInfo = new ConfigPGInfo();
                                    String networkIp = pgInfo.cfnetworkip;
                                    String networkPort = pgInfo.cfnetworkport;
                                    config.setIpnumber(networkIp);
                                    config.setPortnumber(networkPort);
                                    int tempReturnPriceAmount =
                                            GlobalMemberValues.getIntAtString2(returnCreditCardProcessingAmountTextView.getText().toString()) * -1;
                                    refund.RequestRefund(tempReturnPriceAmount * -1);
                                    // -----------------------------------------------------------------------------------------
                                    break;
                                }
                                // PAX POSLink 팍스
                                case "1" : {
                                    GlobalMemberValues.logWrite(TAG, "카드리턴할 금액 : -" + GlobalMemberValues.getIntAtString2(returnCreditCardProcessingAmountTextView.getText().toString()) + "\n");

                                    Intent intent = new Intent(context, JJJ_PaxPay.class);
                                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                                    intent.putExtra("cardtendertype", "CREDIT");
                                    intent.putExtra("processtype", "RETURN");
                                    intent.putExtra("amounttopay",
                                            String.valueOf(GlobalMemberValues.getIntAtString2(returnCreditCardProcessingAmountTextView.getText().toString()))
                                    );
                                    intent.putExtra("refnum", "");
                                    intent.putExtra("ecrrefnum", Payment.mSalesCode);
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
                GlobalMemberValues.displayDialog(this, "Information", "Insert price to return", "Close");
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
    private void setInit() {
        returnCreditCardProcessingAmountTextView.setText("0.0");
        returnCreditCardBalanceTextView.setText("");
        returnCreditCardPaidTextView.setText("0.0");
        mTempPriceValue = "";
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

    // Back Key 막기 위해 onKeyDown 메소드 Override
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
