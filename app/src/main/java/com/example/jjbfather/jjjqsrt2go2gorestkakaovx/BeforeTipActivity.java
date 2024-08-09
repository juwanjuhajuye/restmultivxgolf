package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BeforeTipActivity extends Activity {
    final String TAG = "JJJ_SignPadLog";

    public static Activity mActivity;
    public static Context mContext;

    private LinearLayout tipmainLn, tipinsertLn;

    Button signPadOKBtn, signPadEraseBtn, signPadPaperSignBtn, signPadCancelBtn;
    ImageView cardCompanyIv;
    TextView cardFourDigitsTv, cardAmountTv, cardDescTv;
//    TextView tipAmountTv;

    LinearLayout tipSelectionLn, tipSelectionLn2;
    LinearLayout tipSuggestLn1, tipSuggestLn2, tipSuggestLn3;
    LinearLayout tipSuggestLn4, tipSuggestLn5, tipSuggestLn6;

    LinearLayout tipLn;

    TextView tipSuggest1_1, tipSuggest1_2;
    TextView tipSuggest2_1, tipSuggest2_2;
    TextView tipSuggest3_1, tipSuggest3_2;
    TextView tipSuggest4_1, tipSuggest4_2;
    TextView tipSuggest5_1, tipSuggest5_2;
    TextView tipSuggest6_1, tipSuggest6_2;
    TextView salonNameTv;

    TextView tipInsertTv;

    TextView totalAmountDisplayTV;

    private Button tipinsertkeypad_suButton1,tipinsertkeypad_suButton2,tipinsertkeypad_suButton3;
    private Button tipinsertkeypad_suButton4,tipinsertkeypad_suButton5,tipinsertkeypad_suButton6;
    private Button tipinsertkeypad_suButton7,tipinsertkeypad_suButton8,tipinsertkeypad_suButton9;
    private Button tipinsertkeypad_suButton0,tipinsertkeypad_suButton00,tipinsertkeypad_suButtonBack;
    private Button tipinsertkeypad_suButtonV;

    Intent mIntent;

    String mGetCardAmount = "";
    String tempSalesCode = "";


    String mSaveYNFlag = "N";

    double mTipAmount = 0;

    public static float mStrokeWidth = 5;
    public static int mStrokeColor = Color.BLACK;
    public static int mBackColor = Color.parseColor("#00000000");

    // Tip 고객 입력값
    static String mCustomTipValue = "";

    // 사용한 카드종료
    static String mCardCompanyName = "";

    // 현재 보여지는 창타입
    String mNowWindowType = "";

    // 싸인패드에서 void 처리하는지 여부
    public static String mVoidOnSignPad = "N";

    private long mLastClickTime = 0;

    boolean isMinPayForSign = true;

    public static ProgressDialog proDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.before_tip_activity);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            tempSalesCode = mIntent.getStringExtra("tempSalesCode");
            mGetCardAmount = mIntent.getStringExtra("cardamount");

            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetCardAmount : " + mGetCardAmount + "\n");



            /*******************************************************************************************/
        } else {
            setActivityShowFinish("N");
        }

        // 싸인패드(전자서명)를 사용하지 않거나 총 결제금액이 설정한 금액이하일 경우
        // 싸인패드(전자서명) 없이 바로 영수증 선택페이지로 이동한다. -------------------------------------------------------------------------------
        isMinPayForSign = true;
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW != null){
            isMinPayForSign = GlobalMemberValues.isSignOnMinPay(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString());
        }
        // -------------------------------------------------------------------------------------------------------------------------------------------



        setContents();
        setContents2();
        setContents3();

        // 전자서명 최소결제금액 이하이고 셋팅에서 tip use 가 no 일 경우 ---------------------
        // 바로 결제완료로 진행한다.
        if (!isMinPayForSign && !GlobalMemberValues.isTipUse()) {
//            submitForNotMinPayForSign();
        }
        // ---------------------------------------------------------------------------------

        // 07.16.2022 ---------------------------------------------------------------
        if (GlobalMemberValues.mNotPayYNOnCard) {
            setFrameLayount("0", tipSuggestLn1);
            setCardAndTipAmountTv(tipSuggestLn1.getTag().toString());

//            submitForNotMinPayForSign();
        }
        // -------------------------------------------------------------------------
    }

    public void setContents() {
        // 프레젠테이션 닫기
        GlobalMemberValues.dismissPresentationView();

        // 카드 프로세싱 스텝 초기화
        GlobalMemberValues.CARD_PROCESSING_STEP = 0;

        // Key In 으로 결제가 된 경우 Key in 으로 등록한 카드정보를 초기화한다.
        PaymentCreditCard.setInitKeyInData();

        // 가장 먼저 카드결제창을 안보이게 처리
        //PaymentCreditCard.rootLinearLayout.setVisibility(View.GONE);

        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            LinearLayout emptyLn01, emptyLn02, emptyLn03, emptyLn04;

            emptyLn01 = (LinearLayout) findViewById(R.id.emptyLn01);
            emptyLn02 = (LinearLayout) findViewById(R.id.emptyLn02);
            emptyLn03 = (LinearLayout) findViewById(R.id.emptyLn03);
            emptyLn04 = (LinearLayout) findViewById(R.id.emptyLn04);

            emptyLn01.setVisibility(View.GONE);
            emptyLn02.setVisibility(View.GONE);
            emptyLn03.setVisibility(View.GONE);
            emptyLn04.setVisibility(View.GONE);
        }







        cardAmountTv = (TextView)findViewById(R.id.cardAmountTv);
        if (!GlobalMemberValues.isStrEmpty(mGetCardAmount)) {
            cardAmountTv.setText("$" + mGetCardAmount);
        }

//        String agreeStr = "I agree to pay $" + mGetCardAmount + " according to my card issuer agreement," +
//                " for " + cardCompanyName + " card ending in " + mGetCardFourDigits + ".";
//        cardDescTv = (TextView)findViewById(R.id.cardDescTv);
//        cardDescTv.setText(agreeStr);
    }

    public void setContents2() {
        if (PaymentCreditCard.dbInit == null) {
            if (MainActivity.mDbInit != null) {
                PaymentCreditCard.dbInit = MainActivity.mDbInit;
            }
        }

        String vTipSelect1 = PaymentCreditCard.dbInit.dbExecuteReadReturnString(" select tipselect1 from salon_storestationsettings_paymentgateway ");
        String vTipSelect2 = PaymentCreditCard.dbInit.dbExecuteReadReturnString(" select tipselect2 from salon_storestationsettings_paymentgateway ");
        String vTipSelect3 = PaymentCreditCard.dbInit.dbExecuteReadReturnString(" select tipselect3 from salon_storestationsettings_paymentgateway ");
        String vTipSelect4 = PaymentCreditCard.dbInit.dbExecuteReadReturnString(" select tipselect4 from salon_storestationsettings_paymentgateway ");

        if (GlobalMemberValues.isStrEmpty(vTipSelect1)) {
            vTipSelect1 = "5.0";
        }
        if (GlobalMemberValues.isStrEmpty(vTipSelect2)) {
            vTipSelect2 = "10.0";
        }
        if (GlobalMemberValues.isStrEmpty(vTipSelect3)) {
            vTipSelect3 = "15.0";
        }
        if (GlobalMemberValues.isStrEmpty(vTipSelect4)) {
            vTipSelect4 = "20.0";
        }
        double dblTipSelect1 = GlobalMemberValues.getDoubleAtString(vTipSelect1) * 0.01;
        double dblTipSelect2 = GlobalMemberValues.getDoubleAtString(vTipSelect2) * 0.01;
        double dblTipSelect3 = GlobalMemberValues.getDoubleAtString(vTipSelect3) * 0.01;
        double dblTipSelect4 = GlobalMemberValues.getDoubleAtString(vTipSelect4) * 0.01;

        // 퍼센트별 tip 금액
        double tempCardAmount = GlobalMemberValues.getDoubleAtString(mGetCardAmount);
        // 5%
        double tempTipAmount1 = 0;

        // 10%
        double tempTipAmount2 = 0;

        // 15%
        double tempTipAmount3 = 0;

        // 20%
        double tempTipAmount4 = 0;


        if (GlobalMemberValues.getSuggestionTipType().equals("BT")){

            String temp_tax = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText().toString();
            temp_tax = temp_tax.replace("$","");
            double d_temp_tax = Double.parseDouble(temp_tax);
            tempCardAmount = tempCardAmount - d_temp_tax;

            tempTipAmount1 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect1, "2"));
            tempTipAmount2 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect2, "2"));
            tempTipAmount3 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect3, "2"));
            tempTipAmount4 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect4, "2"));
        } else {
            // AT
            tempTipAmount1 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect1, "2"));
            tempTipAmount2 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect2, "2"));
            tempTipAmount3 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect3, "2"));
            tempTipAmount4 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCardAmount * dblTipSelect4, "2"));
        }



        tipSuggestLn1 = (LinearLayout)findViewById(R.id.tipSuggestLn1);
        tipSuggestLn2 = (LinearLayout)findViewById(R.id.tipSuggestLn2);
        tipSuggestLn3 = (LinearLayout)findViewById(R.id.tipSuggestLn3);
        tipSuggestLn4 = (LinearLayout)findViewById(R.id.tipSuggestLn4);
        tipSuggestLn5 = (LinearLayout)findViewById(R.id.tipSuggestLn5);
        tipSuggestLn6 = (LinearLayout)findViewById(R.id.tipSuggestLn6);

        tipLn = (LinearLayout)findViewById(R.id.tipLn);

        tipSuggestLn1.setTag("0");
        tipSuggestLn2.setTag(tempTipAmount1);
        tipSuggestLn3.setTag(tempTipAmount2);
        tipSuggestLn4.setTag(tempTipAmount3);
        tipSuggestLn5.setTag(tempTipAmount4);
        tipSuggestLn6.setTag("0");

        tipSuggestLn1.setOnClickListener(tipLnClick);
        tipSuggestLn2.setOnClickListener(tipLnClick);
        tipSuggestLn3.setOnClickListener(tipLnClick);
        tipSuggestLn4.setOnClickListener(tipLnClick);
        tipSuggestLn5.setOnClickListener(tipLnClick);
        tipSuggestLn6.setOnClickListener(tipLnClick);

        //tipSuggestLn1.setBackgroundResource(R.drawable.roundlayout_button_tip_rollover);

        tipSuggest1_1 = (TextView)findViewById(R.id.tipSuggest1_1);
        tipSuggest1_2 = (TextView)findViewById(R.id.tipSuggest1_2);

        tipSuggest2_1 = (TextView)findViewById(R.id.tipSuggest2_1);
        tipSuggest2_1.setText(vTipSelect1 + "%");
        tipSuggest2_2 = (TextView)findViewById(R.id.tipSuggest2_2);
        tipSuggest2_2.setText("($" + tempTipAmount1 + ")");

        tipSuggest3_1 = (TextView)findViewById(R.id.tipSuggest3_1);
        tipSuggest3_1.setText(vTipSelect2 + "%");
        tipSuggest3_2 = (TextView)findViewById(R.id.tipSuggest3_2);
        tipSuggest3_2.setText("($" + tempTipAmount2 + ")");

        tipSuggest4_1 = (TextView)findViewById(R.id.tipSuggest4_1);
        tipSuggest4_1.setText(vTipSelect3 + "%");
        tipSuggest4_2 = (TextView)findViewById(R.id.tipSuggest4_2);
        tipSuggest4_2.setText("($" + tempTipAmount3 + ")");

        tipSuggest5_1 = (TextView)findViewById(R.id.tipSuggest5_1);
        tipSuggest5_1.setText(vTipSelect4 + "%");
        tipSuggest5_2 = (TextView)findViewById(R.id.tipSuggest5_2);
        tipSuggest5_2.setText("($" + tempTipAmount4 + ")");

        tipSuggest6_1 = (TextView)findViewById(R.id.tipSuggest6_1);
        tipSuggest6_2 = (TextView)findViewById(R.id.tipSuggest6_2);

        salonNameTv = (TextView)findViewById(R.id.salonNameTv);
        salonNameTv.setText(GlobalMemberValues.SALON_NAME);

        tipSelectionLn = (LinearLayout)findViewById(R.id.tipSelectionLn);
        tipSelectionLn2 = (LinearLayout)findViewById(R.id.tipSelectionLn2);


    }

    public void setContents3() {
        tipmainLn = (LinearLayout)findViewById(R.id.tipmainLn);

        tipinsertLn = (LinearLayout)findViewById(R.id.tipinsertLn);

        tipInsertTv = (TextView)findViewById(R.id.tipInsertTv);
        tipInsertTv.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() + GlobalMemberValues.globalAddFontSize() + 36);

        //0712204 set total amount display
        totalAmountDisplayTV = (TextView)findViewById(R.id.beforeTipActivityTotalDisplayAmountTV);
        totalAmountDisplayTV.setText(mGetCardAmount);

        tipinsertkeypad_suButton1 = (Button)findViewById(R.id.tipinsertkeypad_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton1.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton1.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton2 = (Button)findViewById(R.id.tipinsertkeypad_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton2.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton2.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton3 = (Button)findViewById(R.id.tipinsertkeypad_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton3.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton3.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton4 = (Button)findViewById(R.id.tipinsertkeypad_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton4.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton4.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton5 = (Button)findViewById(R.id.tipinsertkeypad_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton5.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton5.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton6 = (Button)findViewById(R.id.tipinsertkeypad_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton6.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton6.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton7 = (Button)findViewById(R.id.tipinsertkeypad_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton7.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton7.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton8 = (Button)findViewById(R.id.tipinsertkeypad_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton8.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton8.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton9 = (Button)findViewById(R.id.tipinsertkeypad_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton9.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton9.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton0 = (Button)findViewById(R.id.tipinsertkeypad_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton0.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton0.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButton00 = (Button)findViewById(R.id.tipinsertkeypad_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButton00.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            tipinsertkeypad_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            tipinsertkeypad_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            tipinsertkeypad_suButton00.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButtonBack = (Button)findViewById(R.id.tipinsertkeypad_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButtonBack.setText("");
            tipinsertkeypad_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            tipinsertkeypad_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        tipinsertkeypad_suButtonV = (Button)findViewById(R.id.tipinsertkeypad_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipinsertkeypad_suButtonV.setText("");
            tipinsertkeypad_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            tipinsertkeypad_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    tipinsertkeypad_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        tipinsertkeypad_suButton1.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton2.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton3.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton4.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton5.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton6.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton7.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton8.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton9.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton0.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButton00.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButtonBack.setOnClickListener(tipSuButtonClickListener);
        tipinsertkeypad_suButtonV.setOnClickListener(tipSuButtonClickListener);

        String vTipselectonsignature = PaymentCreditCard.dbInit.dbExecuteReadReturnString(" select tipselectonsignature from salon_storestationsettings_paymentgateway ");
        if (GlobalMemberValues.isStrEmpty(vTipselectonsignature)) {
            vTipselectonsignature = "Y";
        }
        if (vTipselectonsignature == "Y" || vTipselectonsignature.equals("Y")) {
            if (GlobalMemberValues.isTipUse()) {

                tipSelectionLn.setVisibility(View.VISIBLE);
                tipSelectionLn2.setVisibility(View.INVISIBLE);
            } else {
                setFrameLayount("0", tipSuggestLn1);
                setCardAndTipAmountTv("0");

                tipSelectionLn.setVisibility(View.INVISIBLE);
                tipSelectionLn2.setVisibility(View.VISIBLE);
            }
        } else {
            setFrameLayount("0", tipSuggestLn1);
            setCardAndTipAmountTv("0");

            tipSelectionLn.setVisibility(View.INVISIBLE);
            tipSelectionLn2.setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener tipSuButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tipinsertkeypad_suButton1 : {
                    numberButtonClick(tipinsertkeypad_suButton1);
                    break;
                }
                case R.id.tipinsertkeypad_suButton2 : {
                    numberButtonClick(tipinsertkeypad_suButton2);
                    break;
                }
                case R.id.tipinsertkeypad_suButton3 : {
                    numberButtonClick(tipinsertkeypad_suButton3);
                    break;
                }
                case R.id.tipinsertkeypad_suButton4 : {
                    numberButtonClick(tipinsertkeypad_suButton4);
                    break;
                }
                case R.id.tipinsertkeypad_suButton5 : {
                    numberButtonClick(tipinsertkeypad_suButton5);
                    break;
                }
                case R.id.tipinsertkeypad_suButton6 : {
                    numberButtonClick(tipinsertkeypad_suButton6);
                    break;
                }
                case R.id.tipinsertkeypad_suButton7 : {
                    numberButtonClick(tipinsertkeypad_suButton7);
                    break;
                }
                case R.id.tipinsertkeypad_suButton8 : {
                    numberButtonClick(tipinsertkeypad_suButton8);
                    break;
                }
                case R.id.tipinsertkeypad_suButton9 : {
                    numberButtonClick(tipinsertkeypad_suButton9);
                    break;
                }
                case R.id.tipinsertkeypad_suButton0 : {
                    numberButtonClick(tipinsertkeypad_suButton0);
                    break;
                }
                case R.id.tipinsertkeypad_suButton00 : {
                    numberButtonClick(tipinsertkeypad_suButton00);
                    break;
                }
                case R.id.tipinsertkeypad_suButtonBack : {
                    StringBuilder sb = new StringBuilder();
                    sb.delete(0, sb.toString().length());
                    sb.append(mCustomTipValue);
                    if (!GlobalMemberValues.isStrEmpty(mCustomTipValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mCustomTipValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mCustomTipValue)) {
                            mCustomTipValue = "0";
                        }
                    } else {
                        mCustomTipValue = "0";
                    }

                    String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mCustomTipValue) * 0.01), "2");
                    tipInsertTv.setText(inputSu);
                    break;
                }
                case R.id.tipinsertkeypad_suButtonV : {
                    setTipCustom(tipInsertTv.getText().toString());

                    break;
                }

            }
        }
    };

    private void setTipCustom(String paramCustomTip) {
        //07122024 if tip selection before payment is applied apply tag so the custom tip amount can be passed back.
        String beforeTipYN = MainActivity.mDbInit.dbExecuteReadReturnString(" select beforetippricessingyn from salon_storestationsettings_paymentgateway ");
        if (beforeTipYN.equals("Y")){
            tipSuggestLn6.setTag(paramCustomTip);
        }

        setFrameLayount("0", tipSuggestLn6);
        setCardAndTipAmountTv(paramCustomTip + "");
    }

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mCustomTipValue.length() < 12) {
            sb.append(mCustomTipValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            if ((tempNumber * 0.01) <= 100) {
                mCustomTipValue = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mCustomTipValue) * 0.01), "2");
                tipInsertTv.setText(inputSu);
                GlobalMemberValues.logWrite("TipCustomLog", "mRateEditTextValue : " + mCustomTipValue + "\n");
            }
        }
    }

    View.OnClickListener tipLnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tipSuggestLn1 : {
                    setFrameLayount("0", tipSuggestLn1);
                    setCardAndTipAmountTv(tipSuggestLn1.getTag().toString());

                    break;
                }
                case R.id.tipSuggestLn2 : {
                    setFrameLayount("0", tipSuggestLn2);
                    setCardAndTipAmountTv(tipSuggestLn2.getTag().toString());

                    break;
                }
                case R.id.tipSuggestLn3 : {
                    setFrameLayount("0", tipSuggestLn3);
                    setCardAndTipAmountTv(tipSuggestLn3.getTag().toString());

                    break;
                }
                case R.id.tipSuggestLn4 : {
                    setFrameLayount("0", tipSuggestLn4);
                    setCardAndTipAmountTv(tipSuggestLn4.getTag().toString());

                    break;
                }
                case R.id.tipSuggestLn5 : {
                    setFrameLayount("0", tipSuggestLn5);
                    setCardAndTipAmountTv(tipSuggestLn5.getTag().toString());

                    break;
                }
                case R.id.tipSuggestLn6 : {
                    setFrameLayount("1", tipSuggestLn6);
                    setCardAndTipAmountTv(tipSuggestLn6.getTag().toString());
                    break;
                }
            }
        }
    };

    private void setCardAndTipAmountTv(String paramTipAmt) {
        // 초기화 ---------------------------------
        mTipAmount = 0;
        tipInsertTv.setText("0.0");
        // ----------------------------------------

        double tempCardAmt = GlobalMemberValues.getDoubleAtString(mGetCardAmount);
        double tempTipAmt = GlobalMemberValues.getDoubleAtString(paramTipAmt);

        cardAmountTv.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempCardAmt + tempTipAmt + ""));


        mTipAmount = tempTipAmt;

        GlobalMemberValues.str_before_tip_amount = paramTipAmt;

        GlobalMemberValues.logWrite("Tip Before", GlobalMemberValues.str_before_tip_amount);

//        double tempTotalCardAmtTipAmt = GlobalMemberValues.getDoubleAtString(mGetCardAmount) + GlobalMemberValues.getDoubleAtString(mTipAmount + "");
//        String tempStr = "";
//        if (GlobalMemberValues.getDoubleAtString(mTipAmount + "") > 0) {
//            tempStr = "(" + GlobalMemberValues.getDoubleAtString(mGetCardAmount) + "+" + GlobalMemberValues.getDoubleAtString(mTipAmount + "") + ")";
//        }

    }

    private void setFrameLayount(String paramFlag, LinearLayout paramLn) {
        if (paramFlag.equals("0")){
            //Payment.openCreditCardActivity(tempSalesCode, mGetCardAmount, "");
            Intent data = new Intent();
            data.putExtra("cardAmount", mGetCardAmount);
            data.putExtra("tipAmount", paramLn.getTag().toString());
            // Activity finished return ok, return the data
            setResult(RESULT_OK, data);

            finish();
        } else if (paramFlag.equals("1")){
            mCustomTipValue = "";
            tipLn.setVisibility(View.GONE);

            Animation animation1;
            // 애니메이션 옵션 저장
            animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
            //Animation animation = new AlphaAnimation(0, 1);
            animation1.setDuration(200);

            tipinsertLn.setVisibility(View.VISIBLE);
            tipinsertLn.setAnimation(animation1);

        }




//        tipmainLn.setVisibility(View.GONE);
//
//        if (paramFlag == "0" || paramFlag.equals("0")) {
//            if (!mNowWindowType.equals("TIPINSERT")) {
//                tipinsertLn.setVisibility(View.GONE);
//
//                Animation animation1;
//                // 애니메이션 옵션 저장
//                animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
//                //Animation animation = new AlphaAnimation(0, 1);
//                animation1.setDuration(200);
//
//
//
//                tipLn.setVisibility(View.GONE);
//
//                mNowWindowType = "TIPINSERT";
//            }
//        } else {
//            if (!mNowWindowType.equals("SIGN")) {
//                mCustomTipValue = "";
//
//                tipLn.setVisibility(View.GONE);
//
//                Animation animation1;
//                // 애니메이션 옵션 저장
//                animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
//                //Animation animation = new AlphaAnimation(0, 1);
//                animation1.setDuration(200);
//
//                tipinsertLn.setVisibility(View.VISIBLE);
//                tipinsertLn.setAnimation(animation1);
//
//                mNowWindowType = "SIGN";
//            }
//        }
//
//        tipSuggestLn1.setBackgroundResource(R.drawable.button_selector_tipselect);
//        tipSuggestLn2.setBackgroundResource(R.drawable.button_selector_tipselect);
//        tipSuggestLn3.setBackgroundResource(R.drawable.button_selector_tipselect);
//        tipSuggestLn4.setBackgroundResource(R.drawable.button_selector_tipselect);
//        tipSuggestLn5.setBackgroundResource(R.drawable.button_selector_tipselect);
//        tipSuggestLn6.setBackgroundResource(R.drawable.button_selector_tipselect);
//
//        paramLn.setBackgroundResource(R.drawable.roundlayout_button_tip_rollover);
    }








    public static void setActivityShowFinish(String paramCheckBalance) {
        // 먼저 카드결제창을 보이게 처리
        //PaymentCreditCard.rootLinearLayout.setVisibility(View.VISIBLE);

        // 창을 닫기 전에 현재 카드발란스가
        if (paramCheckBalance.equals("Y") || paramCheckBalance == "Y") {
            PaymentCreditCard.finishPaymentAfterCheckBalance();
        }

        // Payment 초기화
        //Payment.setInit();

        mActivity.finish();
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




    @Override
    protected void onResume() {
        super.onResume();

        // 프레젠테이션 닫기
        GlobalMemberValues.dismissPresentationView();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (proDial != null && proDial.isShowing()) {
            proDial.dismiss();
        }
    }
}
