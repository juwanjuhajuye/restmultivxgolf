package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripeCardParser;

public class PaymentGiftCard extends Activity {
    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private Button closeBtn;

    private Button paymentGiftCardPopupSuButton1,paymentGiftCardPopupSuButton2,paymentGiftCardPopupSuButton3;
    private Button paymentGiftCardPopupSuButton4,paymentGiftCardPopupSuButton5,paymentGiftCardPopupSuButton6;
    private Button paymentGiftCardPopupSuButton7,paymentGiftCardPopupSuButton8,paymentGiftCardPopupSuButton9;
    private Button paymentGiftCardPopupSuButton0,paymentGiftCardPopupSuButton00,paymentGiftCardPopupSuButtonBack;
    private Button paymentGiftCardPopupSuButtonV;

    private Button paymentGiftCardPopupButtonSearch;

    private EditText paymentGiftCardPopupEditTextCardNumber, paymentGiftCardPopupEditTextUse;
    private TextView paymentGiftCardPopupEditTextAmount;
    private TextView paymentGiftCardPopupEditTextRemaining, paymentGiftCardPopupEditTextRemainingAfterPaid;

    private LinearLayout parentLn;

    private TextView discountExtraTitleEditText;
    private TextView temppgcTv1, temppgcTv2, temppgcTv3, temppgcTv4, temppgcTv5;


    Intent mIntent;
    String mBalanceAmountValue = "0.0";
    String mRemainingAmount = "0.0";

    // 임시저장값
    String mTempCardNumber = "";
    String mTempPriceValue = "";

    // 프랜차이즈 관련 추가
    ProgressDialog dialog;
    String mGetGiftCardBalanceInfo = "";
    String mSearchedGcNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_gift_card);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 38;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.paymentGiftCardPopupLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mBalanceAmountValue = mIntent.getStringExtra("paymentGiftCardPopupAmount");
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
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
                paymentGiftCardPopupEditTextCardNumber.clearFocus();
            }
        });
        // ---------------------------------------------------------------------------------------------------

        dbInit = new DatabaseInit(this);

        // MSR 활성화
        magSwiper(mActivity, context);

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupCloseBtnTag");
        closeBtn.setOnClickListener(paymentGiftCardPopupBtnClickListener);
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
        paymentGiftCardPopupSuButton1 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton2 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton3 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton4 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton5 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton6 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton7 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton8 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton9 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton0 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButton00 = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            paymentGiftCardPopupSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentGiftCardPopupSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_number);
        } else {
            paymentGiftCardPopupSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButtonBack = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButtonBack.setText("");
            paymentGiftCardPopupSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_delete);
        } else {
            paymentGiftCardPopupSuButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        paymentGiftCardPopupSuButtonV = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupSuButtonV.setText("");
            paymentGiftCardPopupSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_enter);
        } else {
            paymentGiftCardPopupSuButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupSuButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        paymentGiftCardPopupSuButton1.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton2.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton3.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton4.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton5.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton6.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton7.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton8.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton9.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton0.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButton00.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButtonBack.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        paymentGiftCardPopupSuButtonV.setOnClickListener(paymentGiftCardPopupBtnClickListener);
        /***********************************************************************************************************/

        /** 기프트카드 번호 검색버튼 객체 생성 및 리스너 연결 *************************************************************/
        paymentGiftCardPopupButtonSearch = (Button)parentLn
                .findViewWithTag("paymentGiftCardPopupButtonSearchTag");

        paymentGiftCardPopupButtonSearch.setOnClickListener(paymentGiftCardPopupBtnClickListener);

        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentGiftCardPopupButtonSearch.setText("");
            paymentGiftCardPopupButtonSearch.setBackgroundResource(R.drawable.ab_imagebutton_paymentgiftcard_search);
        } else {
            paymentGiftCardPopupButtonSearch.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    paymentGiftCardPopupButtonSearch.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/


        /** Amount, Card#, Remaining, Remaining After Paid 객체 생성 및 리스너 연결 **********/
        paymentGiftCardPopupEditTextAmount = (TextView)parentLn
                .findViewWithTag("paymentGiftCardPopupEditTextAmountTag");

        paymentGiftCardPopupEditTextCardNumber = (EditText)parentLn
                .findViewWithTag("paymentGiftCardPopupEditTextCardNumberTag");
        paymentGiftCardPopupEditTextCardNumber.requestFocus();
        paymentGiftCardPopupEditTextCardNumber.setOnFocusChangeListener(mCardNumberFocusChangeListener);

        paymentGiftCardPopupEditTextUse = (EditText) parentLn
                .findViewWithTag("paymentGiftCardPopupEditTextUseTag");
        paymentGiftCardPopupEditTextRemaining = (TextView)parentLn
                .findViewWithTag("paymentGiftCardPopupEditTextRemainingTag");
        paymentGiftCardPopupEditTextRemainingAfterPaid = (TextView)parentLn
                .findViewWithTag("paymentGiftCardPopupEditTextRemainingAfterPaidTag");


        paymentGiftCardPopupEditTextCardNumber.setOnTouchListener(mEditTextTouch);

        paymentGiftCardPopupEditTextUse.setOnTouchListener(mEditTextTouch);

        /***********************************************************************************************************/

        discountExtraTitleEditText = (TextView)findViewById(R.id.discountExtraTitleEditText);
        discountExtraTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() +
                discountExtraTitleEditText.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        temppgcTv1 = (TextView)findViewById(R.id.temppgcTv1);
        temppgcTv1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                temppgcTv1.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        temppgcTv2 = (TextView)findViewById(R.id.temppgcTv2);
        temppgcTv2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                temppgcTv2.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        temppgcTv3 = (TextView)findViewById(R.id.temppgcTv3);
        temppgcTv3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                temppgcTv3.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        temppgcTv4 = (TextView)findViewById(R.id.temppgcTv4);
        temppgcTv4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                temppgcTv4.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        temppgcTv5 = (TextView)findViewById(R.id.temppgcTv5);
        temppgcTv5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                temppgcTv5.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );



        // 객체 및 색상 초기화
        setInit();

        /** Amount 초기화 *******************************************************************************************/
        paymentGiftCardPopupEditTextAmount.setText(mBalanceAmountValue);
        /***********************************************************************************************************/
    }

    View.OnFocusChangeListener mCardNumberFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //GlobalMemberValues.displayDialog(context, "","포커스 이동...", "Close");
                String getMSRData = paymentGiftCardPopupEditTextCardNumber.getText().toString();
                if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);
                    //GlobalMemberValues.displayDialog(context, "","reading value : " + getMSRData, "Close");
                    paymentGiftCardPopupEditTextCardNumber.setText(getMSRData);
                    searchGiftCardInfo();
                }
            }
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.paymentGiftCardPopupEditTextCardNumber : {
                    GlobalMemberValues.setKeyPadHide(context, paymentGiftCardPopupEditTextCardNumber);
                    paymentGiftCardPopupEditTextCardNumber.requestFocus();
                    paymentGiftCardPopupEditTextCardNumber.setSelection(paymentGiftCardPopupEditTextCardNumber.length());

                    break;
                }
                case R.id.paymentGiftCardPopupEditTextUse : {
                    GlobalMemberValues.setKeyPadHide(context, paymentGiftCardPopupEditTextUse);
                    paymentGiftCardPopupEditTextUse.requestFocus();
                    paymentGiftCardPopupEditTextUse.setSelection(paymentGiftCardPopupEditTextUse.length());

                    break;
                }
            }

            return true;
        }
    };


    View.OnClickListener paymentGiftCardPopupBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.paymentGiftCardPopupCloseBtn : {
                    finish();
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.paymentGiftCardPopupButtonSearch : {
                    searchGiftCardInfo();
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton1 : {
                    numberButtonClick(paymentGiftCardPopupSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton2 : {
                    numberButtonClick(paymentGiftCardPopupSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton3 : {
                    numberButtonClick(paymentGiftCardPopupSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton4 : {
                    numberButtonClick(paymentGiftCardPopupSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton5 : {
                    numberButtonClick(paymentGiftCardPopupSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton6 : {
                    numberButtonClick(paymentGiftCardPopupSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton7 : {
                    numberButtonClick(paymentGiftCardPopupSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton8 : {
                    numberButtonClick(paymentGiftCardPopupSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton9 : {
                    numberButtonClick(paymentGiftCardPopupSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton0 : {
                    numberButtonClick(paymentGiftCardPopupSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButton00 : {
                    numberButtonClick(paymentGiftCardPopupSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                    break;
                }
                case R.id.paymentGiftCardPopupSuButtonBack : {
                    // 카드번호 EditText
                    if (paymentGiftCardPopupEditTextCardNumber.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mTempCardNumber);
                        if (!GlobalMemberValues.isStrEmpty(mTempCardNumber)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mTempCardNumber = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mTempCardNumber)) {
                                mTempCardNumber = "";
                            }
                        } else {
                            mTempCardNumber = "";
                        }

                        paymentGiftCardPopupEditTextCardNumber.setText(mTempCardNumber);

                        GlobalMemberValues.logWrite("GIFTCARD", "mTempCardNumber : " + mTempCardNumber + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, paymentGiftCardPopupEditTextCardNumber);
                        // 커서를 먼저 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(paymentGiftCardPopupEditTextCardNumber);
                    }

                    // Use EditText
                    if (paymentGiftCardPopupEditTextUse.isFocused()) {
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
                        paymentGiftCardPopupEditTextUse.setText(inputSu);

                        double tempRemainingAfterPaid = GlobalMemberValues.getDoubleAtString(mRemainingAmount) - GlobalMemberValues.getDoubleAtString(inputSu);
                        paymentGiftCardPopupEditTextRemainingAfterPaid.setText(GlobalMemberValues.getStringFormatNumber(tempRemainingAfterPaid, "2"));


                        GlobalMemberValues.logWrite("paymentGiftCardPopup", "paymentGiftCardPopup mTempPriceValue : " + mTempPriceValue + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                        // 커서를 먼저 맨 뒤로...
                        paymentGiftCardPopupEditTextUse.setSelection(paymentGiftCardPopupEditTextUse.length());
                    }


                    break;
                }
                case R.id.paymentGiftCardPopupSuButtonV : {
                    String giftCardNumberToUse = paymentGiftCardPopupEditTextCardNumber.getText().toString();
                    String giftCardAmountToUse = paymentGiftCardPopupEditTextUse.getText().toString();

                    GlobalMemberValues.logWrite("GiftCardInformation", "number : " + giftCardNumberToUse + "\n");
                    GlobalMemberValues.logWrite("GiftCardInformation", "amount : " + giftCardAmountToUse + "\n");

                    if (GlobalMemberValues.getDoubleAtString(giftCardAmountToUse) <= 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please enter the amount to use", "Close");
                        return;
                    }

                    if (!GlobalMemberValues.isStrEmpty(giftCardNumberToUse)
                            && GlobalMemberValues.getDoubleAtString(mBalanceAmountValue) >= GlobalMemberValues.getDoubleAtString(giftCardAmountToUse)) {
                        /**
                        // 등록전 기프트카드번호의 오기일 수 있으므로 다시 체크한다. -------------------------------------
                        String reSearchGiftCardAmount = "";
                        String strQuery = "select remainingPrice from salon_storegiftcard_number " +
                                " where gcNumber = '" + giftCardNumberToUse + "' ";
                        reSearchGiftCardAmount = dbInit.dbExecuteReadReturnString(strQuery);
                        // -----------------------------------------------------------------------------------------------
                         if (GlobalMemberValues.getDoubleAtString(reSearchGiftCardAmount) >= GlobalMemberValues.getDoubleAtString(giftCardAmountToUse)) {
                         **/
                        if (mSearchedGcNumber.equals(giftCardNumberToUse)) {
                            GlobalMemberValues.GIFTCARD_AFTER_BALANCE = GlobalMemberValues.getDoubleAtString(paymentGiftCardPopupEditTextRemainingAfterPaid.getText().toString());
                            GlobalMemberValues.GIFTCARD_USE_NUMBER = giftCardNumberToUse;

                            Payment.mGiftCardNumberUsed = giftCardNumberToUse;
                            Payment.paymentGiftCardEditText.setText(giftCardAmountToUse);
                            Payment.setPaymentPrice();
                            // 초기화
                            setInit();
                            // 키패드(키보드) 감추기
                            GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                            GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);

                            // 창닫기
                            finish();
                            if (GlobalMemberValues.isUseFadeInOut()) {
                                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                            }
                        } else {
                            GlobalMemberValues.displayDialog(context, "Warning", "Invalid Giftcard Number", "Close");
                            // 초기화
                            setInit();
                            // 키패드(키보드) 감추기
                            GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextCardNumber);
                            GlobalMemberValues.setKeyPadHide(getApplication(), paymentGiftCardPopupEditTextUse);
                        }
                    } else {

                    }
                    break;
                }
            }
        }
    };

    private void searchGiftCardInfo() {
        String tempGcNumber = paymentGiftCardPopupEditTextCardNumber.getText().toString();
        if (!GlobalMemberValues.isStrEmpty(tempGcNumber)) {

            if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                dialog = new JJJCustomAnimationDialog(mActivity);
                dialog.show();
                final String finalTempGcNumber = tempGcNumber;
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
            } else {
                String strQuery = "select idx, customerId, customerName, remainingPrice " +
                        " from salon_storegiftcard_number " +
                        " where gcNumber = '" + tempGcNumber + "' ";
                Cursor c = dbInit.dbExecuteRead(strQuery);
                String tempGcIdx = "";
                String tempGcCustomerId = "";
                String tempGcCustomerName = "";
                double tempGcRemainingPrice = 0.0;
                if (c.getCount() > 0 && c.moveToFirst()) {
                    tempGcIdx = String.valueOf(c.getInt(0));
                    tempGcCustomerId = GlobalMemberValues.getDBTextAfterChecked(c.getString(1), 1);
                    tempGcCustomerName = GlobalMemberValues.getDBTextAfterChecked(c.getString(2), 1);
                    tempGcRemainingPrice = c.getDouble(3);
                }
                setGiftCardInfo(tempGcRemainingPrice);
            }
        }
    }

    private Handler giftCardBalanceInfoDownloadHandler = new Handler() {
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

                setGiftCardInfo(GlobalMemberValues.getDoubleAtString(tempGiftCardBalance));

                // 프로그래스바를 사라지게 함 -------------------------------------------------------
                if (dialog != null || dialog.isShowing()) {
                    dialog.dismiss();
                }
                // -------------------------------------------------------------------------------------

                Toast.makeText(context, "Customer points have been synced with the cloud", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setGiftCardInfo(double paramGcRemainingPrice) {
        String strTempGcRemainingPrice = GlobalMemberValues.getStringFormatNumber(paramGcRemainingPrice, "2");

        paymentGiftCardPopupEditTextRemaining.setText(GlobalMemberValues
                .getConvertTextReturn(strTempGcRemainingPrice, "0"));
        paymentGiftCardPopupEditTextRemainingAfterPaid.setText(GlobalMemberValues
                .getConvertTextReturn(strTempGcRemainingPrice, "0"));
        mRemainingAmount = strTempGcRemainingPrice;

        // 현재 기프트 카드 잔액 double
        double tempRemainingAmount = GlobalMemberValues.getDoubleAtString(mRemainingAmount);
        // 결제해야 할 남은 금액 double
        double tempBalanceAmountValue = GlobalMemberValues.getDoubleAtString(mBalanceAmountValue);
        // use 에 입력할 금액
        String tempUseAmountStr = "0.00";
        if (tempRemainingAmount >= tempBalanceAmountValue) {
            tempUseAmountStr = mBalanceAmountValue;
        } else {
            tempUseAmountStr = mRemainingAmount;
        }

        mTempPriceValue = tempUseAmountStr;
        paymentGiftCardPopupEditTextUse.setText(tempUseAmountStr);
        double tempRemainingAfterPaid = GlobalMemberValues.getDoubleAtString(mRemainingAmount) - GlobalMemberValues.getDoubleAtString(tempUseAmountStr);
        paymentGiftCardPopupEditTextRemainingAfterPaid.setText(GlobalMemberValues.getStringFormatNumber(tempRemainingAfterPaid, "2"));

        // 검색한 기프트카드번호를 저장해 둔다.
        mSearchedGcNumber = paymentGiftCardPopupEditTextCardNumber.getText().toString();

        // GlobalMemberValues.logWrite("mRemainingAmount", "mRemainingAmount : " + mRemainingAmount + "\n");
    }

    private void numberButtonClick(Button btn) {
        // 카드번호 EditText
        if (paymentGiftCardPopupEditTextCardNumber.isFocused()) {
            GlobalMemberValues.setKeyPadHide(context, paymentGiftCardPopupEditTextCardNumber);
            //GlobalMemberValues.displayDialog(context, "Info", "여기..", "Close");

            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());

            sb.append(mTempCardNumber).append(btn.getText().toString());
            mTempCardNumber = sb.toString();
            paymentGiftCardPopupEditTextCardNumber.setText(mTempCardNumber);

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(paymentGiftCardPopupEditTextCardNumber);
        }

        // Use EditText
        if (paymentGiftCardPopupEditTextUse.isFocused()) {
            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mTempPriceValue.length() < 12) {
                String tempSu = "";
                sb.append(mTempPriceValue).append(btn.getText().toString());
                String tempSb = GlobalMemberValues.getReplaceText(sb.toString(), ".", "");
                Long tempNumber = Long.parseLong(tempSb);
                tempSu = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(tempSu) * 0.01), "2");

                if (GlobalMemberValues.getDoubleAtString(inputSu) <= GlobalMemberValues.getDoubleAtString(mBalanceAmountValue)
                        && GlobalMemberValues.getDoubleAtString(inputSu) <= GlobalMemberValues.getDoubleAtString(mRemainingAmount)) {
                    mTempPriceValue = String.valueOf(tempNumber);
                    paymentGiftCardPopupEditTextUse.setText(inputSu);

                    double tempRemainingAfterPaid = GlobalMemberValues.getDoubleAtString(mRemainingAmount) - GlobalMemberValues.getDoubleAtString(inputSu);
                    paymentGiftCardPopupEditTextRemainingAfterPaid.setText(GlobalMemberValues.getStringFormatNumber(tempRemainingAfterPaid, "2"));
                } else {
                    GlobalMemberValues.displayDialog(this, "Warning", "Invalid Amount", "Close");
                    return;
                }
            }

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(paymentGiftCardPopupEditTextUse);
        }
    }

    public void magSwiper(final Activity paramActivity, final Context paramContext) {
        paramActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MagStripDriver magStripDriver;

                magStripDriver = new MagStripDriver(MainActivity.mContext);
                magStripDriver.startDevice(); //Start the MagStripe Reader
                magStripDriver.registerMagStripeListener(new MagStripDriver.MagStripeListener() { //MageStripe Reader's Listener for notifying various events.

                    @Override
                    public void OnDeviceDisconnected() { //Fired when the Device has been Disconnected.
                        //  Toast.makeText(getActivity(), "Magnetic-Stripe Device Disconnected !", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnDeviceConnected() { //Fired when the Device has been Connected.
                        // TODO Auto-generated method stub
                        // Toast.makeText(getActivity(), "Magnetic-Stripe Device Connected !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCardSwiped(String cardData) { //Fired when a card has been swiped on the device.
                        try {
                            MagStripeCardParser mParser = new MagStripeCardParser(cardData); //Instance of card swipe reader
                            if(mParser.isDataParse()){
                                if(mParser.hasTrack1()){
                                    String getMSRData = mParser.getAccountNumber();
                                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);

                                    if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                                        paymentGiftCardPopupEditTextCardNumber.setText("22222");
                                        searchGiftCardInfo();
                                    }

                                    //paramTextView.setText(getMSRData); //Populate the edittext with the card number
                                }
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    // 초기화 메소드
    private void setInit() {
        paymentGiftCardPopupEditTextAmount.setText("0.0");
        paymentGiftCardPopupEditTextCardNumber.setText("");
        paymentGiftCardPopupEditTextUse.setText("0.0");
        paymentGiftCardPopupEditTextRemaining.setText("0.0");
        paymentGiftCardPopupEditTextRemainingAfterPaid.setText("0.0");
        mSearchedGcNumber = "";
    }

}
