package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class JJJ_SignPad extends Activity {
    final String TAG = "JJJ_SignPadLog";

    public static Activity mActivity;
    public static Context mContext;

    private LinearLayout tipmainLn, signpadLn, tipinsertLn;

    Button signPadOKBtn, signPadEraseBtn, signPadPaperSignBtn, signPadCancelBtn;
    ImageView cardCompanyIv;
    TextView cardFourDigitsTv, cardAmountTv, cardDescTv;
    TextView tipAmountTv;

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

    private Button tipinsertkeypad_suButton1,tipinsertkeypad_suButton2,tipinsertkeypad_suButton3;
    private Button tipinsertkeypad_suButton4,tipinsertkeypad_suButton5,tipinsertkeypad_suButton6;
    private Button tipinsertkeypad_suButton7,tipinsertkeypad_suButton8,tipinsertkeypad_suButton9;
    private Button tipinsertkeypad_suButton0,tipinsertkeypad_suButton00,tipinsertkeypad_suButtonBack;
    private Button tipinsertkeypad_suButtonV;

    Intent mIntent;
    String mGetMaxSalonSalesCardIdx = "";
    String mGetAuthNum = "";
    String mGetRefNum = "";
    String mGetCardCompany = "";
    String mGetCardAmount = "";
    String mGetCardFourDigits = "";

    public static String mRefNum = "";

    String mSaveYNFlag = "N";

    double mTipAmount = 0;

    private JJJ_SignPad.DrawView mDrawView;
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
        if (GlobalMemberValues.isDeviceClover()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            // orientation 에 따라 레이아웃 xml 동적으로 적용.
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_jjj_sign_pad2);
            } else {
                setContentView(R.layout.activity_jjj_sign_pad);
            } // end if

        } else {
            setContentView(R.layout.activity_jjj_sign_pad3);
            /**
             if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
             setContentView(R.layout.activity_jjj_sign_pad3);
             } else {
             setContentView(R.layout.activity_jjj_sign_pad);
             }
             **/
        }
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;

        mRefNum = "";

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mGetMaxSalonSalesCardIdx = mIntent.getStringExtra("maxSalonSalesCardIdx");
            mGetAuthNum = mIntent.getStringExtra("cardAuthnum");
            mGetRefNum = mIntent.getStringExtra("cardrefnum");
            mGetCardCompany = mIntent.getStringExtra("cardcompany");
            mGetCardAmount = mIntent.getStringExtra("cardamount");
            mGetCardFourDigits = mIntent.getStringExtra("cardfourdigits");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetMaxSalonSalesCardIdx : " + mGetMaxSalonSalesCardIdx + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetAuthNum : " + mGetAuthNum + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetRefNum : " + mGetRefNum + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetCardCompany : " + mGetCardCompany + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetCardAmount : " + mGetCardAmount + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 mGetCardFourDigits : " + mGetCardFourDigits + "\n");

            mRefNum = mGetRefNum;
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

        // 그림판 관련 부분 -----------------------------------------------------------------
        LinearLayout parentLn = (LinearLayout)findViewById(R.id.mainLn);
        mDrawView = new JJJ_SignPad.DrawView(this);

        if(savedInstanceState!=null) {
            // 화면전환 전에 넣어주었던 pointList를 꺼내서 세팅
            mDrawView.pointList =
                    (ArrayList<JJJ_SignPad.DrawView.Point>) savedInstanceState.getSerializable("list");
        }
        //setContentView(mDrawView);
        parentLn.addView(mDrawView);
        if (isMinPayForSign) {
            parentLn.setBackgroundResource(R.drawable.aa_images_signpad_bg);
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                parentLn.setBackgroundResource(R.drawable.aa_images_signpad_bg);
            }
        } else {
            parentLn.setBackgroundResource(R.drawable.aa_images_nosignpad_bg);
        }
        // ---------------------------------------------------------------------------------

        setContents();
        setContents2();
        setContents3();

        String str_before_tip = MainActivity.mDbInit.dbExecuteReadReturnString(" select beforetippricessingyn from salon_storestationsettings_paymentgateway ");
        if (str_before_tip.equals("Y")){
            mTipAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.str_before_tip_amount);
            setCardAndTipAmountTv(GlobalMemberValues.str_before_tip_amount);
            setFrameLayount("0",tipSuggestLn6);
        }

        // 전자서명 최소결제금액 이하이고 셋팅에서 tip use 가 no 일 경우 ---------------------
        // 바로 결제완료로 진행한다.
        if (!isMinPayForSign && !GlobalMemberValues.isTipUse()) {
            submitForNotMinPayForSign();
        }
        // ---------------------------------------------------------------------------------

        // 07.16.2022 ---------------------------------------------------------------
        if (GlobalMemberValues.mNotPayYNOnCard) {
            setFrameLayount("0", tipSuggestLn1);
            setCardAndTipAmountTv(tipSuggestLn1.getTag().toString());

            submitForNotMinPayForSign();
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


        signPadOKBtn = (Button)findViewById(R.id.signPadOKBtn);
        if (GlobalMemberValues.isDeviceElo()) {
            signPadOKBtn.setBackgroundResource(R.drawable.button_selector_jjjsignpad_ok2);
        }

        signPadEraseBtn = (Button)findViewById(R.id.signPadEraseBtn);
        signPadPaperSignBtn = (Button)findViewById(R.id.signPadPaperSignBtn);
        signPadCancelBtn = (Button)findViewById(R.id.signPadCancelBtn);

        if (GlobalMemberValues.isSignatureCancelShowYN()){
//            signPadCancelBtn.setVisibility(View.VISIBLE);
            signPadCancelBtn.setBackgroundResource(R.drawable.button_selector_jjjsignpad_cancel_void);
            signPadCancelBtn.setOnClickListener(JJJ_SignPadBtnClick);
        } else {
//            signPadCancelBtn.setVisibility(View.INVISIBLE);
            signPadCancelBtn.setBackgroundResource(R.drawable.rectangle_white);
//            signPadCancelBtn.setOnLongClickListener(cancelListener);
            signPadCancelBtn.setOnTouchListener(cancelTouchListener);
        }

        signPadOKBtn.setOnClickListener(JJJ_SignPadBtnClick);
        signPadEraseBtn.setOnClickListener(JJJ_SignPadBtnClick);
        signPadPaperSignBtn.setOnClickListener(JJJ_SignPadBtnClick);
//        signPadCancelBtn.setOnClickListener(JJJ_SignPadBtnClick);

        int cardCompanyImage = 0;
        String cardCompanyName = "";
        cardCompanyIv = (ImageView) findViewById(R.id.cardCompanyIv);
        if (!GlobalMemberValues.isStrEmpty(mGetCardCompany)) {
            switch (mGetCardCompany) {
                case "V" : {
                    cardCompanyImage = R.drawable.aa_images_cardimg_visa;
                    cardCompanyName = "VISA";
                    break;
                }
                case "M" : {
                    cardCompanyImage = R.drawable.aa_images_cardimg_master;
                    cardCompanyName = "MASTER";
                    break;
                }
                case "A" : {
                    cardCompanyImage = R.drawable.aa_images_cardimg_amex;
                    cardCompanyName = "AMEX";
                    break;
                }
                case "D" : {
                    cardCompanyImage = R.drawable.aa_images_cardimg_discover;
                    cardCompanyName = "DISCOVER";
                    break;
                }
            }
            if (cardCompanyImage != 0) {
                cardCompanyIv.setImageResource(cardCompanyImage);
            }

            mCardCompanyName = cardCompanyName;
        }

        cardFourDigitsTv = (TextView) findViewById(R.id.cardFourDigitsTv);
        if (!GlobalMemberValues.isStrEmpty(mGetCardFourDigits)) {
            cardFourDigitsTv.setText("**** **** **** " + mGetCardFourDigits);
        }

        cardAmountTv = (TextView)findViewById(R.id.cardAmountTv);
        if (!GlobalMemberValues.isStrEmpty(mGetCardAmount)) {
            cardAmountTv.setText("$" + mGetCardAmount);
        }

        String agreeStr = "I agree to pay $" + mGetCardAmount + " according to my card issuer agreement," +
                " for " + cardCompanyName + " card ending in " + mGetCardFourDigits + ".";
        cardDescTv = (TextView)findViewById(R.id.cardDescTv);
        cardDescTv.setText(agreeStr);
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

        tipAmountTv = (TextView)findViewById(R.id.tipAmountTv);

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

        tipAmountTv.setText("($" + mGetCardAmount + " + " + "$0.0)");
    }

    public void setContents3() {
        tipmainLn = (LinearLayout)findViewById(R.id.tipmainLn);
        signpadLn = (LinearLayout)findViewById(R.id.signpadLn);
        tipinsertLn = (LinearLayout)findViewById(R.id.tipinsertLn);

        tipInsertTv = (TextView)findViewById(R.id.tipInsertTv);
        tipInsertTv.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() + GlobalMemberValues.globalAddFontSize() + 36);

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
                tipAmountTv.setVisibility(View.VISIBLE);
                tipSelectionLn.setVisibility(View.VISIBLE);
                tipSelectionLn2.setVisibility(View.INVISIBLE);
            } else {
                setFrameLayount("0", tipSuggestLn1);
                setCardAndTipAmountTv("0");
                tipAmountTv.setVisibility(View.INVISIBLE);
                tipSelectionLn.setVisibility(View.INVISIBLE);
                tipSelectionLn2.setVisibility(View.VISIBLE);
            }
        } else {
            setFrameLayount("0", tipSuggestLn1);
            setCardAndTipAmountTv("0");
            tipAmountTv.setVisibility(View.INVISIBLE);
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
                    setTipCustom(GlobalMemberValues.getDoubleAtString(tipInsertTv.getText().toString()));
                    break;
                }

            }
        }
    };

    private void setTipCustom(double paramCustomTip) {
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
        tipAmountTv.setText(
                "($" + GlobalMemberValues.getCommaStringForDouble(tempCardAmt + "") + " + " + GlobalMemberValues.getCommaStringForDouble(tempTipAmt + "") + ")"
        );

        mTipAmount = tempTipAmt;

        double tempTotalCardAmtTipAmt = GlobalMemberValues.getDoubleAtString(mGetCardAmount) + GlobalMemberValues.getDoubleAtString(mTipAmount + "");
        String tempStr = "";
        if (GlobalMemberValues.getDoubleAtString(mTipAmount + "") > 0) {
            tempStr = "(" + GlobalMemberValues.getDoubleAtString(mGetCardAmount) + "+" + GlobalMemberValues.getDoubleAtString(mTipAmount + "") + ")";
        }
        String agreeStr = "I agree to pay $" + GlobalMemberValues.getCommaStringForDouble(tempTotalCardAmtTipAmt + "") + tempStr +
                " according to my card issuer agreement," +
                " for " + mCardCompanyName + " card ending in " + mGetCardFourDigits + ".";
        cardDescTv.setText(agreeStr);
    }

    private void setFrameLayount(String paramFlag, LinearLayout paramLn) {
        tipmainLn.setVisibility(View.GONE);

        if (paramFlag == "0" || paramFlag.equals("0")) {
            if (!mNowWindowType.equals("TIPINSERT")) {
                tipinsertLn.setVisibility(View.GONE);

                Animation animation1;
                // 애니메이션 옵션 저장
                animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
                //Animation animation = new AlphaAnimation(0, 1);
                animation1.setDuration(200);

                signpadLn.setVisibility(View.VISIBLE);
                signpadLn.setAnimation(animation1);

                tipLn.setVisibility(View.GONE);

                mNowWindowType = "TIPINSERT";
            }
        } else {
            if (!mNowWindowType.equals("SIGN")) {
                mCustomTipValue = "";
                signpadLn.setVisibility(View.GONE);
                tipLn.setVisibility(View.GONE);

                Animation animation1;
                // 애니메이션 옵션 저장
                animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
                //Animation animation = new AlphaAnimation(0, 1);
                animation1.setDuration(200);

                tipinsertLn.setVisibility(View.VISIBLE);
                tipinsertLn.setAnimation(animation1);

                mNowWindowType = "SIGN";
            }
        }

        tipSuggestLn1.setBackgroundResource(R.drawable.button_selector_tipselect);
        tipSuggestLn2.setBackgroundResource(R.drawable.button_selector_tipselect);
        tipSuggestLn3.setBackgroundResource(R.drawable.button_selector_tipselect);
        tipSuggestLn4.setBackgroundResource(R.drawable.button_selector_tipselect);
        tipSuggestLn5.setBackgroundResource(R.drawable.button_selector_tipselect);
        tipSuggestLn6.setBackgroundResource(R.drawable.button_selector_tipselect);

        paramLn.setBackgroundResource(R.drawable.roundlayout_button_tip_rollover);
    }

    public void submitForNotMinPayForSign() {
        mDrawView.pointList.clear();
        mDrawView.invalidate();

        GlobalMemberValues.DELETEWAITING += 500;
        setSignatureResult(false);
    }

    View.OnTouchListener cancelTouchListener = new View.OnTouchListener(){
        private Handler handler = new Handler();
        private Runnable longPressRunnable = new Runnable() {
            @Override
            public void run() {
                // 커스텀 길게 누르기 동작
                new AlertDialog.Builder(mContext)
                        .setTitle("Signature")
                        .setMessage("Check the card is inserted\n(If the card is inserted, it may not be void)\n\nTouch 'Yes' and this item will be void")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setVoidOnPaymentCreditCardClass();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
        };
        private static final int CUSTOM_LONG_PRESS_TIME = 5000; // 1초

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 누른 상태일 때 일정 시간 후 실행
                    handler.postDelayed(longPressRunnable, CUSTOM_LONG_PRESS_TIME);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // 손을 뗄 경우 핸들러 작업 취소
                    handler.removeCallbacks(longPressRunnable);
                    break;
            }
            return true; // 터치 이벤트 소비
        }

    };

    JJJ_OnSingleClickListener JJJ_SignPadBtnClick = new JJJ_OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.signPadOKBtn : {
                    // 먼저 버튼 모양을 비활성화 한다.
                    signPadOKBtn.setBackgroundResource(R.drawable.button_selector_jjjsignpad_notclick);
                    //signPadOKBtn.setEnabled(false);

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        // 프로그래스 바를 실행~
                        proDial = new ProgressDialog(mContext);
                        proDial.setMessage("    Finishing your payment....   ");
                        proDial.setCancelable(false);
                        proDial.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        proDial.show();
                    }

                    if (!isMinPayForSign) {
                        submitForNotMinPayForSign();
                    } else {
                        savePicture();
                    }
                    break;
                }
                case R.id.signPadEraseBtn : {
                    mDrawView.pointList.clear();
                    mDrawView.invalidate();
                    break;
                }
                case R.id.signPadPaperSignBtn : {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Signature")
                            .setMessage("Are you sure you want to sign on receipt?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            setActivityShowFinish("Y");
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
                case R.id.signPadCancelBtn : {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Signature")
                            .setMessage("Check the card is inserted\n(If the card is inserted, it may not be void)\n\nTouch 'Yes' and this item will be void")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            setVoidOnPaymentCreditCardClass();
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
            }
        }

    };

    public void setSignatureResult(boolean paramIsRequiredSignature) {
        // paramIsRequiredSignature : 서명 필요여부

        String strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) +
                "/sign_" + mGetMaxSalonSalesCardIdx + "_" + mGetAuthNum + "_" + mGetRefNum + ".png";

        if (paramIsRequiredSignature) {
            if (!(new File(strFilePath).exists())) {
                // 서명이 없을 때...
                GlobalMemberValues.logWrite(TAG, "저장된 서명없음\n");
                setVoidOnPaymentCreditCardClass();
            }
            // 서명이 있을 때...
            // Bitmap 으로 변환
            //Bitmap signatureBm = makeBitmapWithText(strFilePath, "JJJFather", "", 12.0f, 100, 100);
            Bitmap signatureBm = BitmapFactory.decodeFile(strFilePath);
            Drawable bgDrawable = new BitmapDrawable(signatureBm);
        }

        try {
            if (PaymentCreditCard.mCardPaymentJsonArray != null) {
                PaymentCreditCard.mCardPaymentJsonArray[GlobalMemberValues.getIntAtString(mGetMaxSalonSalesCardIdx)].put("cardsalesignatureimageyn", "Y");
            }

            // 9999 수정
            // jihun 0709
//            if (PaymentCreditCard.mCardPaymentJsonArrayList != null){
//                for (int z = 0; z < PaymentCreditCard.mCardPaymentJsonArrayList.size(); z++){
////                    JSONObject tempJson;
////                    tempJson = PaymentCreditCard.mCardPaymentJsonArrayList.get(z);
//                    String temp = PaymentCreditCard.mCardPaymentJsonArrayList.get(z).getString("cardsalonsalescardidx");
//                    if (mGetMaxSalonSalesCardIdx.equals(temp)){
//                        PaymentCreditCard.mCardPaymentJsonArrayList.get(z).put("cardsalesignatureimageyn", "Y");
//                    } else {
//
//                    }
//                }
//            }
            //


            // Signed 표시
            PaymentCreditCard.mSelectedTv.setHeight(20);
            PaymentCreditCard.mSelectedTv.setBackgroundResource(R.drawable.aa_images_signed);

            if (paramIsRequiredSignature) {
                // salon_sales_signedimg 테이블에 저장된 서명이미지명을 저장
                GlobalMemberValues.saveSignedImageNameInSales(Payment.mSalesCode, strFilePath);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tip 이 있을 경우 팁처리 -----------------------------------------------------------------------
        if (mTipAmount > 0) {
            //07122024 add check for setting tip before payment (one payment including tip instead of
            //1st payment of original total amount, and 2nd adjustment payment including tip)
            //This is so there is only one transaction to the card processor not two.
            String beforeTipYN = MainActivity.mDbInit.dbExecuteReadReturnString(" select beforetippricessingyn from salon_storestationsettings_paymentgateway ");
            //If tip is processed before, since tip was already added to total, don't add it again.
            if (GlobalMemberValues.CARD_TIP_PROCESSING && beforeTipYN.equals("N")) {
                payCardTip();
            } else {
                databaseProcessAfterCardProcess("00", Payment.mSalesCode, mGetMaxSalonSalesCardIdx, mTipAmount, mGetCardCompany);
            }
        } else {
            setActivityShowFinish("Y");
        }
        // ----------------------------------------------------------------------------------------------
    }

    private void setVoidOnPaymentCreditCardClass() {
        String strRefNum = "";
//        strRefNum = PaymentCreditCard.dbInit.dbExecuteReadReturnString("select cardRefNumber from salon_sales_card where idx = '"
//                + mGetMaxSalonSalesCardIdx + "' ");
        strRefNum =   MssqlDatabase.getResultSetValueToString("select cardRefNumber from salon_sales_card where idx = '"
                + mGetMaxSalonSalesCardIdx + "' ");

        String strPriceAmount = "";

//        strPriceAmount = PaymentCreditCard.dbInit.dbExecuteReadReturnString("select priceAmount from salon_sales_card where idx = '"
//                + mGetMaxSalonSalesCardIdx + "' ");
        strPriceAmount = MssqlDatabase.getResultSetValueToString("select priceAmount from salon_sales_card where idx = '"
                + mGetMaxSalonSalesCardIdx + "' ");

        GlobalMemberValues.logWrite(TAG, "mSelectedSalonSalesCardIdx : " + mGetMaxSalonSalesCardIdx + "\n");
        GlobalMemberValues.logWrite(TAG, "mGetAuthNum : " + mGetAuthNum + "\n");
        if (!GlobalMemberValues.isStrEmpty(mGetAuthNum)) {
            mVoidOnSignPad = "Y";
            PaymentCreditCard.voidCardProcess(mGetMaxSalonSalesCardIdx, strRefNum, strPriceAmount);
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning",
                    "There is no transaction ID", "Close");
        }
        setActivityShowFinish("N");
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

    /** Tip 관련 카드처리 ******************************************************************************************/
    public void payCardTip() {
        ConfigPGInfo pgInfo = new ConfigPGInfo();
        String paymentGateway = pgInfo.cfpaymentgateway;
        switch (paymentGateway) {
            // Ingenico ICT200 인지니코
            case "0": {

                break;
            }
            // PAX
            case "1": {
                setCreditCardProcessModifyForPax();
                break;
            }
        }
        // ----------------------------------------------------------------------
    }

    public void setCreditCardProcessModifyForPax() {
        if (!GlobalMemberValues.isStrEmpty(mGetRefNum)) {
            if (GlobalMemberValues.ISCHECK_BEFORE_CARDPAY) {
                // 가장 먼저 인터넷 체크를 한다.
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    if (!GlobalMemberValues.isOnline2().equals("00")) {
                        GlobalMemberValues.showDialogNoInternet(mContext);
                        return;
                    }
                } else {
                    if (!GlobalMemberValues.isOnline2().equals("00")) {
                        GlobalMemberValues.openNetworkNotConnected();
                        return;
                    }
                }
            }

            GlobalMemberValues.logWrite("tipamountlog", "amounttopay1 : " + mTipAmount + "\n");

            Intent intent = new Intent(mContext, JJJ_PaxPay.class);
            // 객체 및 데이터 전달하기 ---------------------------------------------------------------
            intent.putExtra("salonsalescardidx", mGetMaxSalonSalesCardIdx);
            intent.putExtra("cardtendertype", "CREDIT");
            intent.putExtra("processtype", "ADJUST");
            intent.putExtra("amounttopay", String.valueOf(GlobalMemberValues.getIntAtString2(GlobalMemberValues.getCommaStringForDouble(mTipAmount + "") + "")));
            intent.putExtra("cardcom", mGetCardCompany);
            intent.putExtra("lastfourdigits", mGetCardFourDigits);
            intent.putExtra("refnum", mGetRefNum);
            intent.putExtra("ecrrefnum", Payment.mSalesCode);
            intent.putExtra("fromsignpad", "Y");
            GlobalMemberValues.logWrite("cardtipadjustment", "card comp : " + mGetCardCompany + "\n");
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(intent);

            GlobalMemberValues.logWrite("tipamountlog", "salonsalescardidx2 : " + mGetMaxSalonSalesCardIdx + "\n");
            GlobalMemberValues.logWrite("tipamountlog", "cardcom : " + mGetCardCompany + "\n");
            GlobalMemberValues.logWrite("tipamountlog", "lastfourdigits : " + mGetCardFourDigits + "\n");
            GlobalMemberValues.logWrite("tipamountlog", "refnum : " + mGetRefNum + "\n");
            GlobalMemberValues.logWrite("tipamountlog", "ecrrefnum : " + Payment.mSalesCode + "\n");
        } else {
            GlobalMemberValues.displayDialog(mContext, "Waraning", "Invalid Access [No Employee Info]", "Close");
        }
    }

    public static void databaseProcessAfterCardProcess(
            String paramCreditCardReturnValue,
            String paramSalesCode,
            String paramSelectedSalonSalesCardIdx,
            double paramModPriceAmount,
            String paramCardCom) {
        String tipResultCode = "";
        String strQuery = "";

        GlobalMemberValues.logWrite("tipbycreditcard", "DB 처리전 카드처리결과 값 확인 : " + paramCreditCardReturnValue + "\n");

        if (!paramCreditCardReturnValue.equals("00")) {
            tipResultCode = "99";
            GlobalMemberValues.logWrite("processingerrorlog", "paramCreditCardReturnValue : " + paramCreditCardReturnValue + "\n");
//            GlobalMemberValues.displayDialog(mContext, "Warning", "Credit Card Processing Error", "Close");
            GlobalMemberValues.displayDialog(mContext, "Warning", "Tip adjustment error", "Close");
            return;
        } else {
            tipResultCode = "00";
            /**
             // 원래 팁금액 가져오기 (카드) ----------------------------------------------------------------------
             String tempSql = "select usedCard from salon_sales_tip where idx = '" + salonSalesTipidx + "' ";
             String originTipAmountStr = dbInit.dbExecuteReadReturnString(tempSql);
             // -----------------------------------------------------------------------------------------------
             **/
            strQuery = "update salon_sales_card set " +
                    " tipAmount = '" + paramModPriceAmount + "' " +
                    " where idx = '" + paramSelectedSalonSalesCardIdx + "' ";
            GlobalMemberValues.logWrite("SaleHistoryTipAdjustmentLog", "strQuery : " + strQuery + "\n");

            Vector<String> strQueryVec = new Vector<String>();
            strQueryVec.addElement(strQuery);

            String tempAdjustedYN = "N";
            if (GlobalMemberValues.CARD_TIP_PROCESSING) {
                tempAdjustedYN = "Y";
            }

            strQuery = "insert into salon_sales_tip (" +
                    " sidx, salesCode, employeeIdx, employeeName, usedCard, cardCom, codeforupload, isCloudUpload, adjustedyn, refnum " +
                    " ) values (" +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + paramSalesCode + "', " +
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                    " '" + paramModPriceAmount + "', " +
                    " '" + paramCardCom + "', " +
                    " '" + GlobalMemberValues.getCodeForUpload("tip") + "', " +
                    " '" + "0" + "', " +
                    " '" + tempAdjustedYN + "', " +
                    " '" + mRefNum + "' " +
                    " ) ";

            strQueryVec.addElement(strQuery);

            // 처리결과에 따라 DB 처리 --------------------------------------------------------------------
            if (tipResultCode.equals("00")) {   // 성공 (00)
                for (String sql : strQueryVec) {
                    GlobalMemberValues.logWrite("tipbycreditcard", "DB 처리할 query : " + sql + "\n");
                }
                DatabaseInit dbInit = new DatabaseInit(mContext);
                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    setActivityShowFinish("N");
                } else { // 정상처리일 경우 Sale History 리스트 리로드

                    // 팁을 줬는지 여부를 true 로 변경
                    // 이 값을 가지고 팁프로세싱을 진행할지 여부를 결정
                    GlobalMemberValues.ispaidtip = true;

                    if (paramModPriceAmount > 0) {
                        String tempTxt = "";
                        tempTxt = tempTxt + "(tip : " + GlobalMemberValues.getCommaStringForDouble(paramModPriceAmount + "") + ")";
                        PaymentCreditCard.mNowTv.setText(tempTxt);
                        PaymentCreditCard.mNowTv.setVisibility(View.VISIBLE);
                        try {
                            PaymentCreditCard.mJsontmp.put("cardtipamount", paramModPriceAmount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            PaymentCreditCard.mJsontmp.put("cardtipamount", "0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // 팁 데이터 클라우드 전송 ------------------------------------------------------------
                    GlobalMemberValues.setSendTipsToCloud(mContext, mActivity);
                    // -------------------------------------------------------------------------------------
                    GlobalMemberValues.logWrite("UploadTipsDataToCloud", "여기실행됨 - 2." + "\n");
                    setActivityShowFinish("Y");
                }
            } else {    //실패
                GlobalMemberValues.displayDialog(mContext, "Warning", "Tip adjustment error", "Close");
                return;
            }
            // ------------------------------------------------------------------------------------------
        }
    }
    /**************************************************************************************************************/

    // Back Key 막기 위해 onKeyDown 메소드 Override
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** 그림판 관련 시작 ******************************************************************************************/

    // 방향이 바뀔때 호출되는 메소드(자원 저장용 메소드)
    // Activity가 죽기전에 호출되는 메서드
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", mDrawView.pointList);
    }

    private void loadPicture() {
        File dir = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER);
        Bitmap bm = BitmapFactory.decodeFile(dir+"/sign_" + mGetMaxSalonSalesCardIdx + "_" + mGetAuthNum + "_" + mGetRefNum + ".png");
        Bitmap copyBitmap = bm.copy(Bitmap.Config.ARGB_8888,true);
        Log.d("signpad",dir+"/sign_" + mGetMaxSalonSalesCardIdx + "_" + mGetAuthNum + "_" + mGetRefNum + ".png");
        mDrawView.draw(new Canvas(copyBitmap));
    }

    private void savePicture() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 처리가 오래걸리는 부분 실행 --------------------------------------------------
                // 1. 캐쉬(Cache)를 허용시킨다.
                // 2. 그림을 Bitmap 으로 저장.
                // 3. 캐쉬를 막는다.
                mSaveYNFlag = "N";
                mDrawView.setDrawingCacheEnabled(true);    // 캐쉬허용
                // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
                Bitmap screenshot = Bitmap.createBitmap(mDrawView.getDrawingCache());
                mDrawView.setDrawingCacheEnabled(false);   // 캐쉬닫기

                // SDCard(ExternalStorage) : 외부저장공간
                // 접근하려면 반드시 AndroidManifest.xml에 권한 설정을 한다.
                File dir =
                        Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER);
                // 폴더가 있는지 확인 후 없으면 새로 만들어준다.
                if(!dir.exists())
                    dir.mkdirs();
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(new File(dir, "/sign_" + mGetMaxSalonSalesCardIdx + "_" + mGetAuthNum + "_" + mGetRefNum + ".png"));
                    screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    GlobalMemberValues.logWrite(TAG, "이미지 저장성공\n");

                    mSaveYNFlag = "Y";
                } catch (Exception e) {
                    GlobalMemberValues.logWrite(TAG, "이미지 저장실패\n");
                    setVoidOnPaymentCreditCardClass();
                }
                // ---------------------------------------------------------------------------------

                // 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handlerSp.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler handlerSp = new Handler() {
        public void handleMessage(Message msg) {
            if (mSaveYNFlag == "Y" || mSaveYNFlag.equals("Y")) {
                GlobalMemberValues.DELETEWAITING += 500;
                setSignatureResult(true);
            }
        }
    };

    /**
     public Bitmap makeBitmapWithText(String paramStrFilePath, String paramText, String paramFontName, float paramTextSize, int paramWidth, int paramHeight) {
     //Bitmap textBitmap = Bitmap.createBitmap(paramWidth, paramHeight, Bitmap.Config.ARGB_8888);
     Bitmap textBitmap = BitmapFactory.decodeFile(paramStrFilePath);
     textBitmap.eraseColor(0x88888888);
     Canvas canvas = new Canvas(textBitmap);
     Typeface typeface = Typeface.create(paramFontName, Typeface.NORMAL);
     Paint textPaint = new Paint();
     textPaint.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() + paramTextSize);
     textPaint.setAntiAlias(true);
     textPaint.setColor(Color.WHITE);
     // textPaint.setStrokeWidth(5);
     // textPaint.setStrokeCap(Paint.Cap.ROUND);
     // textPaint.setARGB(255, 0, 0, 0);
     textPaint.setTypeface(typeface);
     Rect bounds = new Rect();
     textPaint.getTextBounds(paramText, 0, paramText.length(), bounds);
     canvas.drawText(paramText, 0.0f, Math.abs(bounds.top - bounds.bottom), textPaint);

     // size check~!!!
     Log.i("#@#", "measureText : " + textPaint.measureText(paramText));
     Log.i("#@#", "textPaint Rect .. left : " + bounds.left + " , top : " + bounds.top + " , right : " + bounds.right + " , bottom : "
     + bounds.bottom);
     return textBitmap;
     }
     **/

    private static class DrawView extends View implements View.OnTouchListener {

        float x;
        float y;
        public ArrayList<JJJ_SignPad.DrawView.Point> pointList = new ArrayList<JJJ_SignPad.DrawView.Point>();

        public DrawView(Context context) {
            super(context);
            setOnTouchListener(this);
            setFocusableInTouchMode(true);  // 이벤트가 계속해서 발생하기 위해
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(mBackColor);

            Paint paint = new Paint();

            if(pointList.size() < 2) return;
            for (int i=1; i<pointList.size(); i++) {

                if (pointList.get(i).draw) {
                    paint.setColor(pointList.get(i).mStrokeColor);
                    paint.setStrokeWidth(pointList.get(i).mStrokeWidth);
                    canvas.drawLine(pointList.get(i - 1).x,
                            pointList.get(i - 1).y, pointList.get(i).x,
                            pointList.get(i).y, paint);
                }
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            x = event.getX();
            y = event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("signpad", "손가락으로 터치했음");
                    pointList.add(new JJJ_SignPad.DrawView.Point(x,y,false,mStrokeWidth,mStrokeColor));
                    invalidate();         // 그림 다시 그리기
                    return true;                // 이벤트가 여기에서 끝난다.

                case MotionEvent.ACTION_MOVE:
                    Log.d("signpad", "손가락으로 움직이는 중");
                    pointList.add(new JJJ_SignPad.DrawView.Point(x,y,true,mStrokeWidth,mStrokeColor));
                    invalidate();         // 그림 다시 그리기
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.d("signpad", "손가락 땠음");
                default:

            }


            return false;
        }//end class DrawView

        static class Point implements Serializable {
            float x,y;
            boolean draw;
            float mStrokeWidth;
            int mStrokeColor;
            public Point(float x,float y,boolean draw,float mStrokeWidth,int mStrokeColor) {
                this.x = x;
                this.y = y;
                this.draw = draw;
                this.mStrokeColor = mStrokeColor;
                this.mStrokeWidth = mStrokeWidth;
            }

        }//end class Point
    }

    /** 그림판 관련 끝 ********************************************************************************************/


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
