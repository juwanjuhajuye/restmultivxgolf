
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

public class PaymentCashPad extends Activity {
    final String TAG = "PaymentCashPadLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    private Button closeBtn;

    private Button cashEnter_suButton1,cashEnter_suButton2,cashEnter_suButton3;
    private Button cashEnter_suButton4,cashEnter_suButton5,cashEnter_suButton6;
    private Button cashEnter_suButton7,cashEnter_suButton8,cashEnter_suButton9;
    private Button cashEnter_suButton0,cashEnter_suButton00,cashEnter_suButtonBack;
    private Button cashEnter_suButtonV,cashEnter_suButtonX;

    private TextView cashAmountEv;

    private Button cashEnterLeftBtn1,cashEnterLeftBtn2,cashEnterLeftBtn3,cashEnterLeftBtn4,cashEnterLeftBtn5;
    private Button cashEnterRightBtn1,cashEnterRightBtn2,cashEnterRightBtn3,cashEnterRightBtn4,cashEnterRightBtn5,cashEnterRightBtn6;
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
        setContentView(R.layout.activity_payment_cash_pad);
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
            cashEnter_suButton1.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton2.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton3.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton4.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton5.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton6.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton7.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton8.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton9.setTextColor(Color.parseColor("#37383D"));
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
            cashEnter_suButton0.setTextColor(Color.parseColor("#37383D"));
            cashEnter_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashEnter_suButton00 = (Button)findViewById(R.id.cashEnter_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cashEnter_suButton00.setTextColor(Color.parseColor("#37383D"));
            cashEnter_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton00.getTextSize()
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
        cashEnter_suButtonV = (Button)findViewById(R.id.cashEnter_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonV.setText("");
            cashEnter_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            cashEnter_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButtonV.getTextSize()
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
        cashEnter_suButton00.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonBack.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonV.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonX.setOnClickListener(cashEnterButtonClickListener);



        // 왼쪽 금액 버튼 ---------------------------------------------------------------
        cashEnterLeftBtn1 = (Button)findViewById(R.id.cashEnterLeftBtn1);
        cashEnterLeftBtn1.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterLeftBtn1.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterLeftBtn2 = (Button)findViewById(R.id.cashEnterLeftBtn2);
        cashEnterLeftBtn2.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterLeftBtn2.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterLeftBtn3 = (Button)findViewById(R.id.cashEnterLeftBtn3);
        cashEnterLeftBtn3.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterLeftBtn3.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterLeftBtn4 = (Button)findViewById(R.id.cashEnterLeftBtn4);
        cashEnterLeftBtn4.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterLeftBtn4.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterLeftBtn5 = (Button)findViewById(R.id.cashEnterLeftBtn5);
        cashEnterLeftBtn5.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterLeftBtn5.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterLeftBtn1.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn2.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn3.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn4.setOnClickListener(cashEnterButtonClickListener);
        cashEnterLeftBtn5.setOnClickListener(cashEnterButtonClickListener);
        // ----------------------------------------------------------------------------

        // 오른쪽 금액 버튼 -------------------------------------------------------------
        cashEnterRightBtn1 = (Button)findViewById(R.id.cashEnterRightBtn1);
        cashEnterRightBtn1.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn1.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn2 = (Button)findViewById(R.id.cashEnterRightBtn2);
        cashEnterRightBtn2.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn2.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn3 = (Button)findViewById(R.id.cashEnterRightBtn3);
        cashEnterRightBtn3.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn3.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn4 = (Button)findViewById(R.id.cashEnterRightBtn4);
        cashEnterRightBtn4.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn4.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn5 = (Button)findViewById(R.id.cashEnterRightBtn5);
        cashEnterRightBtn5.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn5.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn6 = (Button)findViewById(R.id.cashEnterRightBtn6);
        cashEnterRightBtn6.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnterRightBtn6.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashEnterRightBtn1.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn2.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn3.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn4.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn5.setOnClickListener(cashEnterButtonClickListener);
        cashEnterRightBtn6.setOnClickListener(cashEnterButtonClickListener);
        // ----------------------------------------------------------------------------

        cashAmountEv = (TextView) findViewById(R.id.cashAmountEv);
        cashAmountEv.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashAmountEv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        cashAmountEv.setText(mPaymentBalanceToPay);
        mTempPriceValue = "";

        // 오른쪽 버튼 값 만들기 ----------------------------------------------------------
        String tempRightBtnValue1 = mPaymentBalanceToPay;
        String tempRightBtnValue2 = GlobalMemberValues.getStringFormatNumber(Math.ceil(GlobalMemberValues.getDoubleAtString(tempRightBtnValue1)), "0");
        double tempDblRightBtnValue2 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue2);
        String tempRightBtnValue3 = GlobalMemberValues.getStringFormatNumber(tempDblRightBtnValue2 + (tempDblRightBtnValue2 * 0.2), "0");
        String tempRightBtnValue4 = GlobalMemberValues.getStringFormatNumber(tempDblRightBtnValue2 + (tempDblRightBtnValue2 * 0.5), "0");
        String tempRightBtnValue5 = GlobalMemberValues.getStringFormatNumber(tempDblRightBtnValue2 + (tempDblRightBtnValue2 * 1.0), "0");

        cashEnterLeftBtn1.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempRightBtnValue1));
        cashEnterLeftBtn2.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempRightBtnValue2));
        cashEnterLeftBtn3.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempRightBtnValue3));
        cashEnterLeftBtn4.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempRightBtnValue4));
        cashEnterLeftBtn5.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempRightBtnValue5));
        // ----------------------------------------------------------------------------
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
                case R.id.cashEnter_suButton00 : {
                    numberButtonClick(cashEnter_suButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
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
                    break;
                }
                case R.id.cashEnter_suButtonV : {
                    setCashAmountOnPayment();

                    break;
                }

                // 왼쪽버튼 터치(클릭)시
                case R.id.cashEnterLeftBtn1 : {
                    setCashAmountEv(cashEnterLeftBtn1.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn2 : {
                    setCashAmountEv(cashEnterLeftBtn2.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn3 : {
                    setCashAmountEv(cashEnterLeftBtn3.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn4 : {
                    setCashAmountEv(cashEnterLeftBtn4.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn5 : {
                    setCashAmountEv(cashEnterLeftBtn5.getText().toString());
                    break;
                }

                // 오른쪽 버튼 터치(클릭)시
                case R.id.cashEnterRightBtn1 : {
                    setCashAmountEv(cashEnterRightBtn1.getText().toString());
                    break;
                }
                case R.id.cashEnterRightBtn2 : {
                    setCashAmountEv(cashEnterRightBtn2.getText().toString());
                    break;
                }
                case R.id.cashEnterRightBtn3 : {
                    setCashAmountEv(cashEnterRightBtn3.getText().toString());
                    break;
                }
                case R.id.cashEnterRightBtn4 : {
                    setCashAmountEv(cashEnterRightBtn4.getText().toString());
                    break;
                }
                case R.id.cashEnterRightBtn5 : {
                    setCashAmountEv(cashEnterRightBtn5.getText().toString());
                    break;
                }
                case R.id.cashEnterRightBtn6 : {
                    setCashAmountEv(cashEnterRightBtn6.getText().toString());
                    break;
                }

                
            }
        }
    };

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
            mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_right);
        }
    }

    private void setInit() {
    }
}
