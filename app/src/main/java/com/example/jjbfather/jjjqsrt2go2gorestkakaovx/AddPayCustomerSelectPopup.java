package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddPayCustomerSelectPopup extends Activity {
    RelativeLayout rl_creditcard, rl_cash, rl_otherpay;
    TextView popup_payment_text_creditcard_amount, popup_payment_text_cash_amount, popup_payment_text_otherpay_amount;
    TextView popup_payment_text_creditcard_amount2, popup_payment_text_cash_amount2, popup_payment_text_otherpay_amount2;


    // 08052023
    TextView popup_payment_text_discount_cash_amount, popup_payment_text_discount_card_amount;

    ImageButton popup_payment_close;

    Intent mIntent;

    Activity mActivity;
    Context mContext;

    // 06162023
    static boolean isStartCartProcessing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.addpay_customer_select_popup);
        this.setFinishOnTouchOutside(false);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            /*******************************************************************************************/
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        mActivity = this;
        mContext = this;

        // 06162023
        isStartCartProcessing = false;

        // 프레젠테이션 닫기
        GlobalMemberValues.dismissPresentationView();

        // 버튼으로 쓸 RelativeLayout
        rl_creditcard   = findViewById(R.id.rl_creditcard);
        rl_creditcard.setOnClickListener(mBtnClickListener);

        rl_cash         = findViewById(R.id.rl_cash);
        rl_cash.setOnClickListener(mBtnClickListener);

        rl_otherpay     = findViewById(R.id.rl_otherpay);
        rl_otherpay.setOnClickListener(mBtnClickListener);

        // 금액
        popup_payment_text_creditcard_amount    = findViewById(R.id.popup_payment_text_creditcard_amount);
        popup_payment_text_creditcard_amount.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_creditcard_amount.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );
        popup_payment_text_cash_amount          = findViewById(R.id.popup_payment_text_cash_amount);
        popup_payment_text_cash_amount.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_cash_amount.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );
        popup_payment_text_otherpay_amount      = findViewById(R.id.popup_payment_text_otherpay_amount);
        popup_payment_text_otherpay_amount.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_otherpay_amount.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );

        popup_payment_text_creditcard_amount2    = findViewById(R.id.popup_payment_text_creditcard_amount2);
        popup_payment_text_creditcard_amount2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_creditcard_amount2.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );
        popup_payment_text_cash_amount2          = findViewById(R.id.popup_payment_text_cash_amount2);
        popup_payment_text_cash_amount2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_cash_amount2.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );
        popup_payment_text_otherpay_amount2      = findViewById(R.id.popup_payment_text_otherpay_amount2);
        popup_payment_text_otherpay_amount2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                popup_payment_text_otherpay_amount2.getTextSize() * GlobalMemberValues.getGlobalFontSize()
        );


        popup_payment_text_discount_cash_amount = findViewById(R.id.popup_payment_text_discount_cash_amount);

        // 08052023
        popup_payment_text_discount_card_amount = findViewById(R.id.popup_payment_text_discount_card_amount);

        // close
        popup_payment_close = findViewById(R.id.popup_payment_close);
        popup_payment_close.setOnClickListener(mBtnClickListener);


        String s_isPaymentPossibleByType = GlobalMemberValues.isPaymentPossibleByTypeToString();
        GlobalMemberValues.logWrite("paymenttypelogjjj", "value : " + s_isPaymentPossibleByType + "\n");

        rl_otherpay.setVisibility(View.GONE);
        if (s_isPaymentPossibleByType.indexOf("giftcard") > 0 || s_isPaymentPossibleByType.indexOf("point") > 0) {
            rl_otherpay.setVisibility(View.VISIBLE);
        }

//        if (!s_isPaymentPossibleByType.contains("check")){
//
//        }


        setPayPriceByPaymentType();
    }

    private void setPayPriceByPaymentType() {
        String[] paramAddPayValue = GlobalMemberValues.getAddPayData();
        String tempAddPayType = paramAddPayValue[0];

        double tempAddPayMoney = GlobalMemberValues.getDoubleAtString(paramAddPayValue[1]);
        double tempAddMinusPDF = 0.0;

        String tempSubtotalValueTv = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString();
        String tempTaxValueTv = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText().toString();
        String tempTotalValueTv = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString();


        // 08312023
        if (!GlobalMemberValues.isStrEmpty(tempSubtotalValueTv)) {
            tempSubtotalValueTv = GlobalMemberValues.getReplaceText(tempSubtotalValueTv, "$", "");
        }
        if (!GlobalMemberValues.isStrEmpty(tempTaxValueTv)) {
            tempTaxValueTv = GlobalMemberValues.getReplaceText(tempTaxValueTv, "$", "");
        }
        if (!GlobalMemberValues.isStrEmpty(tempTotalValueTv)) {
            tempTotalValueTv = GlobalMemberValues.getReplaceText(tempTotalValueTv, "$", "");
        }


        String tempTotalValue = "";
        if (paramAddPayValue[3].equals("AT")) {
            tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString();
            GlobalMemberValues.logWrite("addpaylogjjj", "여기..1 : " + tempTotalValue + "\n");

            tempAddMinusPDF = 0.0;
        } else {
            tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString();
            GlobalMemberValues.logWrite("addpaylogjjj", "여기..2 : " + tempTotalValue + "\n");

            tempAddMinusPDF = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString());
        }

        String addpay_val = "";
        if (tempAddPayType.equals("$")) {
            addpay_val = tempAddPayMoney + "";
        } else {
            addpay_val = (GlobalMemberValues.getDoubleAtString(tempTotalValue) + tempAddMinusPDF) * (tempAddPayMoney * 0.01) + "";
        }

        double addpay_val_dbl = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getCommaStringForDouble(addpay_val));

        if (GlobalMemberValues.getAddPayType().equals("B")) {
            addpay_val_dbl = addpay_val_dbl * -1;
        }

        //double adjust_value_dbl = GlobalMemberValues.getDoubleAtString(tempTotalValue) + addpay_val_dbl;

        double subtotalval_cal = 0.0;
        double taxval_cal = 0.0;
        double totalval_cal = 0.0;
        if (paramAddPayValue[3].equals("AT")) {
            subtotalval_cal = GlobalMemberValues.getDoubleAtString(tempSubtotalValueTv) + addpay_val_dbl;
            taxval_cal = GlobalMemberValues.getDoubleAtString(tempTaxValueTv);
            totalval_cal = subtotalval_cal + taxval_cal;
        } else {
            // 09282023

            double taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;

            subtotalval_cal = GlobalMemberValues.getDoubleAtString(tempSubtotalValueTv) + addpay_val_dbl;

            double org_tax = GlobalMemberValues.getDoubleAtString(tempTaxValueTv);

            double add_tax = addpay_val_dbl * taxToUse * 0.01;
            add_tax = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(add_tax, "2"));


            if (GlobalMemberValues.getTaxExemptItemsCountInCart() > 0
                    && GlobalMemberValues.getAllItemsCountInCart() == GlobalMemberValues.getTaxExemptItemsCountInCart()) {
                add_tax = 0.0;
            }


            if (GlobalMemberValues.getGiftCardItemsCountInCart() > 0
                    && GlobalMemberValues.getAllItemsCountInCart() == GlobalMemberValues.getGiftCardItemsCountInCart()) {
                add_tax = 0.0;
            }


            //taxval_cal = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getCommaStringForDouble((subtotalval_cal * taxToUse * 0.01) + ""));
            taxval_cal = org_tax + add_tax;
            totalval_cal = subtotalval_cal + taxval_cal;
        }



        String inValueCard = "";
        String inValueCash = "";
        String inValueOther = "";

        String inValueCard2 = "";
        String inValueCash2 = "";
        String inValueOther2 = "";

        switch (GlobalMemberValues.getAddPayType()) {
            case "A" : {
                if (GlobalMemberValues.isShowTaxValue()) {
                    // inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCard2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";

                    // inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCash2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";

                    // inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueOther2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";

                } else {
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCard2 = "";

                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCash2 = "";

                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueOther2 = "";
                }
                break;
            }

            case "B" : {
                if (GlobalMemberValues.isShowTaxValue()) {

                    // inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCard2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";

                    // inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(temp_total + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_tax + "") + ")";
                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueCash2 = "$" + GlobalMemberValues.getCommaStringForDouble(subtotalval_cal + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(taxval_cal + "") + "(Tax)";

                    // inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueOther2 = "$" + GlobalMemberValues.getCommaStringForDouble(subtotalval_cal + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(taxval_cal + "") + "(Tax)";

                } else {
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCard2 = "";

                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueCash2 = "";

                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueOther2 = "";
                }

                if (GlobalMemberValues.isAddPayNameOnCustomPopup()) {
                    popup_payment_text_discount_cash_amount.setText("(" + paramAddPayValue[2] + " "+GlobalMemberValues.getStringFormatNumber(tempAddPayMoney,"2") + tempAddPayType +")");
                } else {
                    popup_payment_text_discount_cash_amount.setText("");
                }

                break;
            }

            case "C" : {
                double temp_subtotal = 0.0;
                double temp_tax = 0.0;
                double temp_total = 0.0;

                double taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;

                if (GlobalMemberValues.isShowTaxValue()) {
                    // inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(temp_total + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_tax + "") + ")";
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueCard2 = "$" + GlobalMemberValues.getCommaStringForDouble(subtotalval_cal + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(taxval_cal + "") + "(Tax)";

                    // inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueCash2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";

                    // inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "") + " (+Tax $" + GlobalMemberValues.getCommaStringForDouble(temp_str_tax + "") + ")";
                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValueTv + "");
                    inValueOther2 = "$" + GlobalMemberValues.getCommaStringForDouble(tempSubtotalValueTv + "") +
                            "+" + "$" + GlobalMemberValues.getCommaStringForDouble(tempTaxValueTv + "") + "(Tax)";
                } else {
                    inValueCard = "$" + GlobalMemberValues.getCommaStringForDouble(totalval_cal + "");
                    inValueCard2 = "";

                    inValueCash = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "");
                    inValueCash2 = "";

                    inValueOther = "$" + GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "");
                    inValueOther2 = "";
                }

                if (GlobalMemberValues.isAddPayNameOnCustomPopup()) {
                    popup_payment_text_discount_card_amount.setText("(" + paramAddPayValue[2] + " "+GlobalMemberValues.getStringFormatNumber(tempAddPayMoney,"2") + tempAddPayType +")");
                } else {
                    popup_payment_text_discount_card_amount.setText("");
                }

                break;
            }

        }

        popup_payment_text_creditcard_amount.setGravity(Gravity.CENTER);
        popup_payment_text_cash_amount.setGravity(Gravity.CENTER);
        popup_payment_text_otherpay_amount.setGravity(Gravity.CENTER);

        popup_payment_text_creditcard_amount2.setGravity(Gravity.CENTER);
        popup_payment_text_cash_amount2.setGravity(Gravity.CENTER);
        popup_payment_text_otherpay_amount2.setGravity(Gravity.CENTER);

        popup_payment_text_creditcard_amount.setText(inValueCard);
        popup_payment_text_cash_amount.setText(inValueCash);
        popup_payment_text_otherpay_amount.setText(inValueOther);

        popup_payment_text_creditcard_amount2.setText(inValueCard2);
        popup_payment_text_cash_amount2.setText(inValueCash2);
        popup_payment_text_otherpay_amount2.setText(inValueOther2);
    }

    View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popup_payment_close : {
                    setCloseActivity();

                    break;
                }
                case R.id.rl_creditcard : {
                    // 06162023
                    isStartCartProcessing = true;

                    Payment.setClickCardLn(Payment.paymentCashLinearLayout);

                    setCloseActivity();
                    break;
                }
                case R.id.rl_cash : {
                    Payment.setClickCashLn();

                    setCloseActivity();
                    break;
                }
                case R.id.rl_otherpay : {
                    // 09282023
                    Payment.setClickGiftCardLn(Payment.paymentCashLinearLayout);

                    setCloseActivity();
                    break;
                }

            }
        }
    };

    private void setCloseActivity() {
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            //jihun park sub display
//            MainActivity.updatePresentation();
        }

        mActivity.finish();
    }

}
