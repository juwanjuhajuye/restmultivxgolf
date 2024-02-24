package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class QtyEditCashInOut extends Activity {
    static Activity mActivity;
    Context context;

    private Button qtyEditCashInOut_suButton1,qtyEditCashInOut_suButton2,qtyEditCashInOut_suButton3;
    private Button qtyEditCashInOut_suButton4,qtyEditCashInOut_suButton5,qtyEditCashInOut_suButton6;
    private Button qtyEditCashInOut_suButton7,qtyEditCashInOut_suButton8,qtyEditCashInOut_suButton9;
    private Button qtyEditCashInOut_suButton0,qtyEditCashInOut_suButton00,qtyEditCashInOut_suButtonBack;
    private Button qtyEditCashInOut_suButtonV,qtyEditCashInOut_suButtonX;

    private EditText qtyEditCashInOut_qty;
    private TextView serviceNameTv;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    String parentSelectedPosition;

    Intent mIntent;

    String mSelectedInOutType, mSelectedDollarType;
    double mDollarValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_qty_edit_cash_in_out);
        setLayoutContent();

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mSelectedInOutType = mIntent.getStringExtra("selectedInOutType");
            mSelectedDollarType = mIntent.getStringExtra("selectedDollarType");
            switch (mSelectedDollarType) {
                // Cash In 관련
                case "inOne" : {
                    mDollarValue = 1.00;
                    break;
                }
                case "inTwo" : {
                    mDollarValue = 2.00;
                    break;
                }
                case "inFive" : {
                    mDollarValue = 5.00;
                    break;
                }
                case "inTen" : {
                    mDollarValue = 10.00;
                    break;
                }
                case "inTwenty" : {
                    mDollarValue = 20.00;
                    break;
                }
                case "inFifty" : {
                    mDollarValue = 50.00;
                    break;
                }
                case "inHundred" : {
                    mDollarValue = 100.00;
                    break;
                }
                // Cash Out 관련
                case "outOne" : {
                    mDollarValue = 1.00;
                    break;
                }
                case "outTwo" : {
                    mDollarValue = 2.00;
                    break;
                }
                case "outFive" : {
                    mDollarValue = 5.00;
                    break;
                }
                case "outTen" : {
                    mDollarValue = 10.00;
                    break;
                }
                case "outTwenty" : {
                    mDollarValue = 20.00;
                    break;
                }
                case "outFifty" : {
                    mDollarValue = 50.00;
                    break;
                }
                case "outHundred" : {
                    mDollarValue = 100.00;
                    break;
                }
            }
            /*******************************************************************************************/

        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void setLayoutContent() {
        qtyEditCashInOut_suButton1 = (Button)findViewById(R.id.qtyEditCashInOut_suButton1);
        qtyEditCashInOut_suButton2 = (Button)findViewById(R.id.qtyEditCashInOut_suButton2);
        qtyEditCashInOut_suButton3 = (Button)findViewById(R.id.qtyEditCashInOut_suButton3);
        qtyEditCashInOut_suButton4 = (Button)findViewById(R.id.qtyEditCashInOut_suButton4);
        qtyEditCashInOut_suButton5 = (Button)findViewById(R.id.qtyEditCashInOut_suButton5);
        qtyEditCashInOut_suButton6 = (Button)findViewById(R.id.qtyEditCashInOut_suButton6);
        qtyEditCashInOut_suButton7 = (Button)findViewById(R.id.qtyEditCashInOut_suButton7);
        qtyEditCashInOut_suButton8 = (Button)findViewById(R.id.qtyEditCashInOut_suButton8);
        qtyEditCashInOut_suButton9 = (Button)findViewById(R.id.qtyEditCashInOut_suButton9);
        qtyEditCashInOut_suButton0 = (Button)findViewById(R.id.qtyEditCashInOut_suButton0);
        qtyEditCashInOut_suButton00 = (Button)findViewById(R.id.qtyEditCashInOut_suButton00);
        qtyEditCashInOut_suButtonBack = (Button)findViewById(R.id.qtyEditCashInOut_suButtonBack);
        qtyEditCashInOut_suButtonV = (Button)findViewById(R.id.qtyEditCashInOut_suButtonV);
        qtyEditCashInOut_suButtonX = (Button)findViewById(R.id.qtyEditCashInOut_suButtonX);

        qtyEditCashInOut_suButton1.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton2.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton3.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton4.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton5.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton6.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton7.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton8.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton9.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton0.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButton00.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButtonBack.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButtonV.setOnClickListener(qtyEditCashInOutButtonClickListener);
        qtyEditCashInOut_suButtonX.setOnClickListener(qtyEditCashInOutButtonClickListener);

        qtyEditCashInOut_qty = (EditText)findViewById(R.id.qtyEditCashInOut_qty);
        qtyEditCashInOut_qty.setOnTouchListener(mTouchqtyEditCashInOutTvTouchListener);

        serviceNameTv = (TextView)findViewById(R.id.serviceNameTv);
        serviceNameTv.setText(GlobalMemberValues.getStringFormatNumber(mDollarValue, "2"));

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), qtyEditCashInOut_qty);
    }

    View.OnClickListener qtyEditCashInOutButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qtyEditCashInOut_suButton1 : {
                    numberButtonClick(qtyEditCashInOut_suButton1);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton2 : {
                    numberButtonClick(qtyEditCashInOut_suButton2);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton3 : {
                    numberButtonClick(qtyEditCashInOut_suButton3);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton4 : {
                    numberButtonClick(qtyEditCashInOut_suButton4);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton5 : {
                    numberButtonClick(qtyEditCashInOut_suButton5);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton6 : {
                    numberButtonClick(qtyEditCashInOut_suButton6);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton7 : {
                    numberButtonClick(qtyEditCashInOut_suButton7);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton8 : {
                    numberButtonClick(qtyEditCashInOut_suButton8);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton9 : {
                    numberButtonClick(qtyEditCashInOut_suButton9);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton0 : {
                    numberButtonClick(qtyEditCashInOut_suButton0);
                    break;
                }
                case R.id.qtyEditCashInOut_suButton00 : {
                    numberButtonClick(qtyEditCashInOut_suButton00);
                    break;
                }
                case R.id.qtyEditCashInOut_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mQtyEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mQtyEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                            mQtyEtValue = "0";
                        }
                    } else {
                        mQtyEtValue = "0";
                    }
                    qtyEditCashInOut_qty.setText(mQtyEtValue);
                    break;
                }
                case R.id.qtyEditCashInOut_suButtonV : {
                    String tempEdittingQtyStr = qtyEditCashInOut_qty.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempEdittingQtyStr)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Enter number", "Close");
                        return;
                    }
                    int tempEdittingQty = GlobalMemberValues.getIntAtString(tempEdittingQtyStr);
                    double tempAmount = mDollarValue * tempEdittingQty;

                    double tempTotalAmount = tempAmount + calcCashInOutAmount();
                    String tempTotalAmountStr = GlobalMemberValues.getStringFormatNumber(tempTotalAmount, "2");

                    CashInOut.mSelectedTextView.setText(tempEdittingQtyStr);
                    CashInOut.mSelectedAmountTextView.setText(GlobalMemberValues.getStringFormatNumber(tempAmount, "2"));
                    if (mSelectedInOutType.equals("in")) {
                        CashInOut.cashInOutInTotalAmountTextView.setText(tempTotalAmountStr);
                    } else {
                        CashInOut.cashInOutOutTotalAmountTextView.setText(tempTotalAmountStr);
                    }

                    finish();       // 해당 Dialog 를 닫는다.
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.qtyEditCashInOut_suButtonX : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };

    public double calcCashInOutAmount() {
        double returnValue = 0.00;

        double cashInOneAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInOneDollarAmountTextView.getText().toString());
        double cashInTwoAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInTwoDollarAmountTextView.getText().toString());
        double cashInFiveAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInFiveDollarAmountTextView.getText().toString());
        double cashInTenAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInTenDollarAmountTextView.getText().toString());
        double cashInTwentyAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInTwentyDollarAmountTextView.getText().toString());
        double cashInFiftyAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInFiftyDollarAmountTextView.getText().toString());
        double cashInHundredAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutInHundredDollarAmountTextView.getText().toString());

        double cashOutOneAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutOneDollarAmountTextView.getText().toString());
        double cashOutTwoAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutTwoDollarAmountTextView.getText().toString());
        double cashOutFiveAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutFiveDollarAmountTextView.getText().toString());
        double cashOutTenAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutTenDollarAmountTextView.getText().toString());
        double cashOutTwentyAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutTwentyDollarAmountTextView.getText().toString());
        double cashOutFiftyAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutFiftyDollarAmountTextView.getText().toString());
        double cashOutHundredAmount = GlobalMemberValues.getDoubleAtString(CashInOut.cashInOutOutHundredDollarAmountTextView.getText().toString());

        switch (mSelectedDollarType) {
            // Cash In 관련
            case "inOne" : {
                cashInOneAmount = 0.00;
                break;
            }
            case "inTwo" : {
                cashInTwoAmount = 0.00;
                break;
            }
            case "inFive" : {
                cashInFiveAmount = 0.00;
                break;
            }
            case "inTen" : {
                cashInTenAmount = 0.00;
                break;
            }
            case "inTwenty" : {
                cashInTwentyAmount = 0.00;
                break;
            }
            case "inFifty" : {
                cashInFiftyAmount = 0.00;
                break;
            }
            case "inHundred" : {
                cashInHundredAmount = 0.00;
                break;
            }
            // Cash Out 관련
            case "outOne" : {
                cashOutOneAmount = 0.00;
                break;
            }
            case "outTwo" : {
                cashOutTwoAmount = 0.00;
                break;
            }
            case "outFive" : {
                cashOutFiveAmount = 0.00;
                break;
            }
            case "outTen" : {
                cashOutTenAmount = 0.00;
                break;
            }
            case "outTwenty" : {
                cashOutTwentyAmount = 0.00;
                break;
            }
            case "outFifty" : {
                cashOutFiftyAmount = 0.00;
                break;
            }
            case "outHundred" : {
                cashOutHundredAmount = 0.00;
                break;
            }
        }
        if (mSelectedInOutType.equals("in")) {
            returnValue = cashInOneAmount +cashInTwoAmount +cashInFiveAmount +cashInTenAmount +cashInTwentyAmount +cashInFiftyAmount +cashInHundredAmount;
        } else {
            returnValue = cashOutOneAmount +cashOutTwoAmount +cashOutFiveAmount +cashOutTenAmount +cashOutTwentyAmount +cashOutFiftyAmount +cashOutHundredAmount;
        }
        return returnValue;
    }

    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        if (mQtyEtValue.length() < 6) {
            sb.append(mQtyEtValue).append(btn.getText().toString());
            int tempNumber = Integer.parseInt(sb.toString());
            mQtyEtValue = String.valueOf(tempNumber);
            qtyEditCashInOut_qty.setText(mQtyEtValue);
        }
    }

    View.OnTouchListener mTouchqtyEditCashInOutTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            qtyEditCashInOut_qty.setFocusable(true);
            qtyEditCashInOut_qty.selectAll();
            // 키보드 안보이게
            GlobalMemberValues.setKeyPadHide(getApplication(), qtyEditCashInOut_qty);
            //qtyEditCashInOut_qty.hasFocus();
            return true;
        }
    };


}
