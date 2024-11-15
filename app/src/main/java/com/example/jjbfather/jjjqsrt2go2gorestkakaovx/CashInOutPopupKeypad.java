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

public class CashInOutPopupKeypad extends Activity {
    final String TAG = "CashInOutPopupKeypadLog";

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
    private Button cashEnter_suButtonX, cashEnter_nextStep;

    // 임시저장값
    String mTempPriceValue = "";
    String mTvNum = "";

    String mGetNowAmt = "";

    double suXNum = 1;
    String sosujum = "0";

    TextView mTv = null;

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out_popup_keypad);
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
            mTvNum = mIntent.getStringExtra("tvnum");
            mGetNowAmt = mIntent.getStringExtra("nowamt");
            /*******************************************************************************************/
            GlobalMemberValues.logWrite(TAG, "mTvNum : " + mTvNum + "\n");
            GlobalMemberValues.logWrite(TAG, "mGetNowAmt : " + mGetNowAmt + "\n");
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

        cashEnter_suButton1 = (Button)findViewById(R.id.cashEnter_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton2 = (Button)findViewById(R.id.cashEnter_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton3 = (Button)findViewById(R.id.cashEnter_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton4 = (Button)findViewById(R.id.cashEnter_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton5 = (Button)findViewById(R.id.cashEnter_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton6 = (Button)findViewById(R.id.cashEnter_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton7 = (Button)findViewById(R.id.cashEnter_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton8 = (Button)findViewById(R.id.cashEnter_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton9 = (Button)findViewById(R.id.cashEnter_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton0 = (Button)findViewById(R.id.cashEnter_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButtonBack = (Button)findViewById(R.id.cashEnter_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//            cashEnter_suButtonBack.setText("");
//            cashEnter_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            cashEnter_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }


        cashEnter_suButtonX = (Button)findViewById(R.id.cashEnter_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonX.setText("");
            cashEnter_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_close_big);
        } else {
            cashEnter_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cashEnter_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        cashEnter_nextStep = (Button)findViewById(R.id.cashEnter_nextStep);
        cashEnter_nextStep.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                cashEnter_nextStep.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

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
        cashEnter_nextStep.setOnClickListener(cashEnterButtonClickListener);

        mTempPriceValue = "";

        if (!mTvNum.equals("1000")) {
            setCashInOutPopupEv(mTvNum);
            suXNum = 1;
            sosujum = "0";
        } else {
            mTv = CashInOutPopup.cashInOutAmountTv;
            suXNum = 0.01;
            sosujum = "2";

            if (!GlobalMemberValues.isStrEmpty(mGetNowAmt)) {
                //mTempPriceValue = String.valueOf(GlobalMemberValues.getDoubleAtString(mGetNowAmt) * 100);
                mTempPriceValue = "";
            }

            cashEnter_nextStep.setText("");
            cashEnter_nextStep.setVisibility(View.INVISIBLE);
        }
    }

    private void setNextButtonString(String paramNum) {
        String nextButtonStr = "";
        switch (paramNum) {
            case "01" : {
                nextButtonStr = "NEXT\n($50.00)";
                break;
            }
            case "02" : {
                nextButtonStr = "NEXT\n($20.00)";
                break;
            }
            case "03" : {
                nextButtonStr = "NEXT\n($10.00)";
                break;
            }
            case "04" : {
                nextButtonStr = "NEXT\n($5.00)";
                break;
            }
            case "05" : {
                nextButtonStr = "NEXT\n($2.00)";
                break;
            }
            case "06" : {
                nextButtonStr = "NEXT\n($1.00)";
                break;
            }
            case "07" : {
                nextButtonStr = "NEXT\n($0.50)";
                break;
            }
            case "08" : {
                nextButtonStr = "NEXT\n($0.25)";
                break;
            }
            case "09" : {
                nextButtonStr = "NEXT\n($0.10)";
                break;
            }
            case "10" : {
                nextButtonStr = "NEXT\n($0.05)";
                break;
            }
            case "11" : {
                nextButtonStr = "NEXT\n($0.01)";
                break;
            }
            case "12" : {
                nextButtonStr = "NEXT\n($100.00)";
                break;
            }
        }

        cashEnter_nextStep.setText(nextButtonStr);
    }

    private void setCashInOutPopupEv(String paramNum) {
        initCashInOutPopupCashSuEv();
        setNextButtonString(paramNum);
        selectTv(paramNum);

        mTempPriceValue = "";

        switch (paramNum) {
            case "01" : {
                CashinOutPopup_TypeInput.cashSuEv01.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "02" : {
                CashinOutPopup_TypeInput.cashSuEv02.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "03" : {
                CashinOutPopup_TypeInput.cashSuEv03.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "04" : {
                CashinOutPopup_TypeInput.cashSuEv04.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "05" : {
                CashinOutPopup_TypeInput.cashSuEv05.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "06" : {
                CashinOutPopup_TypeInput.cashSuEv06.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "07" : {
                CashinOutPopup_TypeInput.cashSuEv07.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "08" : {
                CashinOutPopup_TypeInput.cashSuEv08.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "09" : {
                CashinOutPopup_TypeInput.cashSuEv09.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "10" : {
                CashinOutPopup_TypeInput.cashSuEv10.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "11" : {
                CashinOutPopup_TypeInput.cashSuEv11.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "12" : {
                CashinOutPopup_TypeInput.cashSuEv12.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "13" : {
                CashinOutPopup_TypeInput.cashSuEv13.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "14" : {
                CashinOutPopup_TypeInput.cashSuEv14.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "15" : {
                CashinOutPopup_TypeInput.cashSuEv15.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
            case "16" : {
                CashinOutPopup_TypeInput.cashSuEv16.setBackgroundResource(R.drawable.roundlayout_background_suevselect);
                break;
            }
        }
    }

    private void initCashInOutPopupCashSuEv() {
        CashinOutPopup_TypeInput.cashSuEv01.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv02.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv03.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv04.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv05.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv06.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv07.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv08.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv09.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv10.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv11.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv12.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv13.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv14.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv15.setBackgroundResource(R.drawable.roundlayout_background_suev);
        CashinOutPopup_TypeInput.cashSuEv16.setBackgroundResource(R.drawable.roundlayout_background_suev);
    }

    View.OnClickListener cashEnterButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashEnter_suButton1 : {
                    numberButtonClick(cashEnter_suButton1);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton2 : {
                    numberButtonClick(cashEnter_suButton2);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton3 : {
                    numberButtonClick(cashEnter_suButton3);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton4 : {
                    numberButtonClick(cashEnter_suButton4);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton5 : {
                    numberButtonClick(cashEnter_suButton5);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton6 : {
                    numberButtonClick(cashEnter_suButton6);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton7 : {
                    numberButtonClick(cashEnter_suButton7);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton8 : {
                    numberButtonClick(cashEnter_suButton8);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton9 : {
                    numberButtonClick(cashEnter_suButton9);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton0 : {
                    numberButtonClick(cashEnter_suButton0);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                /**
                 case R.id.cashEnter_suButton00 : {
                 numberButtonClick(cashEnter_suButton00);

                 // 키패드(키보드) 감추기
                 //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
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

                    String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * suXNum), sosujum);
                    mTv.setText(inputSu);
                    if (inputSu.equals("0")) {
                        mTv.setText("");
                    }

                    GlobalMemberValues.logWrite("paymentCreditCard", "paymentCreditCard mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }

                case R.id.cashEnter_nextStep : {
                    if (!mTvNum.equals("1000")) {
                        int nowNum = GlobalMemberValues.getIntAtString(mTvNum);
                        int nextNum = 1;
                        if (nowNum < 12) {
                            nextNum = GlobalMemberValues.getIntAtString(mTvNum) + 1;
                        } else {
                            nextNum = 1;
                        }

                        String tempNextNum = "";
                        if (nextNum < 10) {
                            tempNextNum = "0" + nextNum;
                        } else {
                            tempNextNum = nextNum + "";
                        }

                        mTvNum = tempNextNum;
                        setCashInOutPopupEv(mTvNum);
                    }
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

            }
        }
    };

    private void numberButtonClick(Button btn) {
        GlobalMemberValues.logWrite(TAG, "mTempPriceValue---1 : " + mTempPriceValue + "\n");
        GlobalMemberValues.logWrite(TAG, "suXNum---1 : " + suXNum + "\n");

        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mTempPriceValue.length() < 12) {
            String tempSu = "";
            sb.append(mTempPriceValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            tempSu = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(tempSu) * suXNum), sosujum);

            GlobalMemberValues.logWrite("pccnumberButtonClick", "mBalanceAmountValue : " + mTvNum + "\n");

            mTempPriceValue = String.valueOf(tempNumber);
            mTv.setText(inputSu);

            if (inputSu.equals("0")) {
                mTv.setText("");
            }
        }
    }

    private void selectTv(String paramTvNum) {
        switch (paramTvNum) {
            case "01" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv01;
                break;
            }
            case "02" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv02;
                break;
            }
            case "03" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv03;
                break;
            }
            case "04" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv04;
                break;
            }
            case "05" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv05;
                break;
            }
            case "06" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv06;
                break;
            }
            case "07" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv07;
                break;
            }
            case "08" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv08;
                break;
            }
            case "09" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv09;
                break;
            }
            case "10" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv10;
                break;
            }
            case "11" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv11;
                break;
            }
            case "12" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv12;
                break;
            }
            case "13" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv13;
                break;
            }
            case "14" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv14;
                break;
            }
            case "15" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv15;
                break;
            }
            case "16" : {
                mTv = CashinOutPopup_TypeInput.cashSuEv16;
                break;
            }
        }
    }

    private void closeActivity() {
        mActivity.finish();
        // 초기화
        //setInit();
        // 키패드(키보드) 감추기
        //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
        }
    }

}
