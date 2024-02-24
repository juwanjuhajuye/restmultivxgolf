package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PaymentCalculator extends Activity {
    final String TAG = "PaymentCalculatorLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    private Button closeBtn;

    private Button cashEnter_suButton1,cashEnter_suButton2,cashEnter_suButton3;
    private Button cashEnter_suButton4,cashEnter_suButton5,cashEnter_suButton6;
    private Button cashEnter_suButton7,cashEnter_suButton8,cashEnter_suButton9;
    private Button cashEnter_suButton0,cashEnter_suButtonBack;
    private Button cashEnter_suButtonX;

    private TextView cashAmountEv;
    private TextView splitTv1, splitTv2, splitTv3, splitTv4, splitTv5, splitTv6;

    private TextView paycaltempTv1,paycaltempTv2,paycaltempTv3,paycaltempTv4;
    private TextView paycaltempTv5,paycaltempTv6,paycaltempTv7;

    private Button clearBtn;

    // 임시저장값
    static String mTempPriceValue = "";
    static String mPaymentBalanceToPay = "0.0";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_calculator);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mPaymentBalanceToPay = mIntent.getStringExtra("paymentBalanceToPay");
            /*******************************************************************************************/
            if (GlobalMemberValues.getDoubleAtString(mPaymentBalanceToPay) > 0) {
                mPaymentBalanceToPay = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(mPaymentBalanceToPay), "2");
            }
            GlobalMemberValues.logWrite("cashpadlog", "mPaymentBalanceToPay : " + mPaymentBalanceToPay + "\n");
        } else {
            finish();
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
            }
        });
        // ---------------------------------------------------------------------------------------------------

        emptyLinearLayout = (LinearLayout)findViewById(R.id.emptyLinearLayout);
        emptyLinearLayout.setOnClickListener(cashEnterButtonClickListener);

        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.cashEnter_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        closeBtn.setOnClickListener(cashEnterButtonClickListener);
        /***********************************************************************************************************/

        clearBtn = (Button)findViewById(R.id.clearBtn);
        clearBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                clearBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        clearBtn.setOnClickListener(cashEnterButtonClickListener);

        cashEnter_suButton1 = (Button)findViewById(R.id.cashEnter_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton2 = (Button)findViewById(R.id.cashEnter_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton3 = (Button)findViewById(R.id.cashEnter_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton4 = (Button)findViewById(R.id.cashEnter_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton5 = (Button)findViewById(R.id.cashEnter_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton6 = (Button)findViewById(R.id.cashEnter_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton7 = (Button)findViewById(R.id.cashEnter_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton8 = (Button)findViewById(R.id.cashEnter_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton9 = (Button)findViewById(R.id.cashEnter_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton0 = (Button)findViewById(R.id.cashEnter_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButtonBack = (Button)findViewById(R.id.cashEnter_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonBack.setText("");
            cashEnter_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            cashEnter_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButtonX = (Button)findViewById(R.id.cashEnter_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonX.setText("");
            cashEnter_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            cashEnter_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        cashEnter_suButton1.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton2.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton3.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton4.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton5.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton6.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton7.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton8.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton9.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton0.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonBack.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonX.setOnClickListener(cashEnterButtonClickListener);

        cashAmountEv = (TextView) findViewById(R.id.cashAmountEv);
        cashAmountEv.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashAmountEv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        cashAmountEv.setText(mPaymentBalanceToPay);

        splitTv1 = (TextView) findViewById(R.id.splitTv1);
        splitTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv2 = (TextView) findViewById(R.id.splitTv2);
        splitTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv3 = (TextView) findViewById(R.id.splitTv3);
        splitTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv4 = (TextView) findViewById(R.id.splitTv4);
        splitTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv5 = (TextView) findViewById(R.id.splitTv5);
        splitTv5.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv5.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv6 = (TextView) findViewById(R.id.splitTv6);
        splitTv6.setTextSize(GlobalMemberValues.globalAddFontSize() + splitTv6.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv1 = (TextView) findViewById(R.id.paycaltempTv1);
        paycaltempTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv2 = (TextView) findViewById(R.id.paycaltempTv2);
        paycaltempTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv3 = (TextView) findViewById(R.id.paycaltempTv3);
        paycaltempTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv4 = (TextView) findViewById(R.id.paycaltempTv4);
        paycaltempTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv5 = (TextView) findViewById(R.id.paycaltempTv5);
        paycaltempTv5.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv5.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv6 = (TextView) findViewById(R.id.paycaltempTv6);
        paycaltempTv6.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv6.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        paycaltempTv7 = (TextView) findViewById(R.id.paycaltempTv7);
        paycaltempTv7.setTextSize(GlobalMemberValues.globalAddFontSize() + paycaltempTv7.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        splitTv1.setOnClickListener(cashEnterButtonClickListener);
        splitTv2.setOnClickListener(cashEnterButtonClickListener);
        splitTv3.setOnClickListener(cashEnterButtonClickListener);
        splitTv4.setOnClickListener(cashEnterButtonClickListener);
        splitTv5.setOnClickListener(cashEnterButtonClickListener);
        splitTv6.setOnClickListener(cashEnterButtonClickListener);


        mTempPriceValue = "";
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

            GlobalMemberValues.logWrite("pccnumberButtonClick", "mBalanceAmountValue : " + mPaymentBalanceToPay + "\n");

            mTempPriceValue = String.valueOf(tempNumber);
            cashAmountEv.setText(inputSu);

            splitCalculator(0);
        }
    }

    View.OnClickListener cashEnterButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashEnter_suButton1 : {
                    numberButtonClick(cashEnter_suButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton2 : {
                    numberButtonClick(cashEnter_suButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton3 : {
                    numberButtonClick(cashEnter_suButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton4 : {
                    numberButtonClick(cashEnter_suButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton5 : {
                    numberButtonClick(cashEnter_suButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton6 : {
                    numberButtonClick(cashEnter_suButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton7 : {
                    numberButtonClick(cashEnter_suButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton8 : {
                    numberButtonClick(cashEnter_suButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton9 : {
                    numberButtonClick(cashEnter_suButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton0 : {
                    numberButtonClick(cashEnter_suButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                /**
                case R.id.cashEnter_suButton00 : {
                    numberButtonClick(cashEnter_suButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                 **/
                case R.id.cashEnter_suButtonBack : {
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
                    cashAmountEv.setText(inputSu);

                    splitCalculator(0);

                    GlobalMemberValues.logWrite("paymentCreditCard", "paymentCreditCard mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButtonX : {
                    closeActivity();

                    break;
                }

                case R.id.emptyLinearLayout : {
                    closeActivity();
                    break;
                }
                case R.id.clearBtn : {
                    setCashAmountEv("0.00");
                    splitCalculator(0);
                    break;
                }
                /**
                case R.id.cashEnter_suButtonV : {
                    setCashAmountOnPayment();

                    break;
                }
                 **/

                case R.id.splitTv1 : {
                    splitCalculator(1);
                    break;
                }
                case R.id.splitTv2 : {
                    splitCalculator(2);
                    break;
                }
                case R.id.splitTv3 : {
                    splitCalculator(3);
                    break;
                }
                case R.id.splitTv4 : {
                    splitCalculator(4);
                    break;
                }
                case R.id.splitTv5 : {
                    splitCalculator(5);
                    break;
                }
                case R.id.splitTv6 : {
                    splitCalculator(6);
                    break;
                }

            }
        }
    };

    private void splitCalculator(double paramValue) {
        String[] splitValues = { "", "", "", "", "", "" };

        double tempCashAmountEv = GlobalMemberValues.getDoubleAtString(cashAmountEv.getText().toString());
        if (tempCashAmountEv > 0 && paramValue > 0) {
            double tempTotalAmount = 0;
            for (int i = 0; i < paramValue; i++) {
                double tempValue = tempCashAmountEv / paramValue;
                splitValues[i] = String.format("%.2f", tempValue);

                GlobalMemberValues.logWrite(TAG, "tempValue : " + tempValue + "\n");
                GlobalMemberValues.logWrite(TAG, "splitValues[" + i + "] : " + splitValues[i] + "\n\n");

                tempTotalAmount += GlobalMemberValues.getDoubleAtString(splitValues[i]);

                if (i == (paramValue -1)) {
                    double diffValue = tempCashAmountEv - tempTotalAmount;
                    double tempTotal = GlobalMemberValues.getDoubleAtString(splitValues[i]) + diffValue;

                    splitValues[i] = String.format("%.2f", tempTotal);
                }
            }
        }

        splitTv1.setText(splitValues[0]);
        splitTv2.setText(splitValues[1]);
        splitTv3.setText(splitValues[2]);
        splitTv4.setText(splitValues[3]);
        splitTv5.setText(splitValues[4]);
        splitTv6.setText(splitValues[5]);
    }

    private void setCashAmountEv(String paramValue) {
        cashAmountEv.setText(GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.getDoubleAtString(paramValue) + ""));
        mTempPriceValue = "0";
    }

    private void setCashAmountOnPayment () {
        double tempInsCash = GlobalMemberValues.getDoubleAtString(cashAmountEv.getText().toString()) * -1;

        TextView focusTv = Payment.paymentCashEditText;
        Payment.setPaymentTypeEditText(focusTv, tempInsCash);

        Payment.setPaymentPrice();

        closeActivity();
    }

    private void closeActivity() {
        finish();
        // 초기화
        setInit();
        // 키패드(키보드) 감추기
        //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_left);
        }
    }

    private void setInit() {
    }
}
