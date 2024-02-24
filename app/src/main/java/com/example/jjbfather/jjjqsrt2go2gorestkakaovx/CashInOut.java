package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CashInOut extends Activity {
    static Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    Intent mIntent;

    private Button closeBtn;

    TextView cashInOutInDateTextView, cashInOutOutDateTextView;
    Button cashInOutInDateButton, cashInOutOutDateButton;

    TextView cashInOutInOneDollarTextView, cashInOutInTwoDollarTextView, cashInOutInFiveDollarTextView;
    TextView cashInOutInTenDollarTextView, cashInOutInTwentyDollarTextView;
    TextView cashInOutInFiftyDollarTextView, cashInOutInHundredDollarTextView;

    public static TextView cashInOutInOneDollarAmountTextView, cashInOutInTwoDollarAmountTextView, cashInOutInFiveDollarAmountTextView;
    public static TextView cashInOutInTenDollarAmountTextView, cashInOutInTwentyDollarAmountTextView;
    public static TextView cashInOutInFiftyDollarAmountTextView, cashInOutInHundredDollarAmountTextView;

    public static TextView cashInOutInTotalAmountTextView;

    TextView cashInOutOutOneDollarTextView, cashInOutOutTwoDollarTextView, cashInOutOutFiveDollarTextView;
    TextView cashInOutOutTenDollarTextView, cashInOutOutTwentyDollarTextView;
    TextView cashInOutOutFiftyDollarTextView, cashInOutOutHundredDollarTextView;

    public static TextView cashInOutOutOneDollarAmountTextView, cashInOutOutTwoDollarAmountTextView, cashInOutOutFiveDollarAmountTextView;
    public static TextView cashInOutOutTenDollarAmountTextView, cashInOutOutTwentyDollarAmountTextView;
    public static TextView cashInOutOutFiftyDollarAmountTextView, cashInOutOutHundredDollarAmountTextView;

    public static TextView cashInOutOutTotalAmountTextView;

    Button cashInOutInOneDollarButton, cashInOutInTwoDollarButton, cashInOutInFiveDollarButton;
    Button cashInOutInTenDollarButton, cashInOutInTwentyDollarButton;
    Button cashInOutInFiftyDollarButton, cashInOutInHundredDollarButton;

    Button cashInOutOutOneDollarButton, cashInOutOutTwoDollarButton, cashInOutOutFiveDollarButton;
    Button cashInOutOutTenDollarButton, cashInOutOutTwentyDollarButton;
    Button cashInOutOutFiftyDollarButton, cashInOutOutHundredDollarButton;

    Button cashInOutInButton, cashInOutOutButton;

    public static TextView mSelectedTextView, mSelectedAmountTextView;
    TextView mSelectedDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        mActivity = this;
        context = this;

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.cashInOutCloseBtn);
        closeBtn.setOnClickListener(cashInOutBtnClickListener);
        /***********************************************************************************************************/

        /** Date 관련 객체 생성 및 리스너 연결 *********************************************************************/
        cashInOutInDateTextView = (TextView)findViewById(R.id.cashInOutInDateTextView);
        cashInOutOutDateTextView = (TextView)findViewById(R.id.cashInOutOutDateTextView);

        cashInOutInDateTextView.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutDateTextView.setOnClickListener(cashInOutBtnClickListener);

        cashInOutInDateButton = (Button)findViewById(R.id.cashInOutInDateButton);
        cashInOutOutDateButton = (Button)findViewById(R.id.cashInOutOutDateButton);

        cashInOutInDateButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutDateButton.setOnClickListener(cashInOutBtnClickListener);
        /***********************************************************************************************************/

        /** Cash In 관련 객체 생성 및 리스너 연결 *******************************************************************/
        cashInOutInOneDollarTextView = (TextView)findViewById(R.id.cashInOutInOneDollarTextView);
        cashInOutInTwoDollarTextView = (TextView)findViewById(R.id.cashInOutInTwoDollarTextView);
        cashInOutInFiveDollarTextView = (TextView)findViewById(R.id.cashInOutInFiveDollarTextView);
        cashInOutInTenDollarTextView = (TextView)findViewById(R.id.cashInOutInTenDollarTextView);
        cashInOutInTwentyDollarTextView = (TextView)findViewById(R.id.cashInOutInTwentyDollarTextView);
        cashInOutInFiftyDollarTextView = (TextView)findViewById(R.id.cashInOutInFiftyDollarTextView);
        cashInOutInHundredDollarTextView = (TextView)findViewById(R.id.cashInOutInHundredDollarTextView);

        cashInOutInOneDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInOneDollarAmountTextView);
        cashInOutInTwoDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInTwoDollarAmountTextView);
        cashInOutInFiveDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInFiveDollarAmountTextView);
        cashInOutInTenDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInTenDollarAmountTextView);
        cashInOutInTwentyDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInTwentyDollarAmountTextView);
        cashInOutInFiftyDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInFiftyDollarAmountTextView);
        cashInOutInHundredDollarAmountTextView = (TextView)findViewById(R.id.cashInOutInHundredDollarAmountTextView);

        cashInOutInTotalAmountTextView = (TextView)findViewById(R.id.cashInOutInTotalAmountTextView);

        cashInOutInOneDollarButton = (Button)findViewById(R.id.cashInOutInOneDollarButton);
        cashInOutInTwoDollarButton = (Button)findViewById(R.id.cashInOutInTwoDollarButton);
        cashInOutInFiveDollarButton = (Button)findViewById(R.id.cashInOutInFiveDollarButton);
        cashInOutInTenDollarButton = (Button)findViewById(R.id.cashInOutInTenDollarButton);
        cashInOutInTwentyDollarButton = (Button)findViewById(R.id.cashInOutInTwentyDollarButton);
        cashInOutInFiftyDollarButton = (Button)findViewById(R.id.cashInOutInFiftyDollarButton);
        cashInOutInHundredDollarButton = (Button)findViewById(R.id.cashInOutInHundredDollarButton);

        cashInOutInOneDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInTwoDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInFiveDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInTenDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInTwentyDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInFiftyDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutInHundredDollarButton.setOnClickListener(cashInOutBtnClickListener);
        /***********************************************************************************************************/

        /** Cash Out 관련 객체 생성 및 리스너 연결 *******************************************************************/
        cashInOutOutOneDollarTextView = (TextView)findViewById(R.id.cashInOutOutOneDollarTextView);
        cashInOutOutTwoDollarTextView = (TextView)findViewById(R.id.cashInOutOutTwoDollarTextView);
        cashInOutOutFiveDollarTextView = (TextView)findViewById(R.id.cashInOutOutFiveDollarTextView);
        cashInOutOutTenDollarTextView = (TextView)findViewById(R.id.cashInOutOutTenDollarTextView);
        cashInOutOutTwentyDollarTextView = (TextView)findViewById(R.id.cashInOutOutTwentyDollarTextView);
        cashInOutOutFiftyDollarTextView = (TextView)findViewById(R.id.cashInOutOutFiftyDollarTextView);
        cashInOutOutHundredDollarTextView = (TextView)findViewById(R.id.cashInOutOutHundredDollarTextView);

        cashInOutOutOneDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutOneDollarAmountTextView);
        cashInOutOutTwoDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutTwoDollarAmountTextView);
        cashInOutOutFiveDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutFiveDollarAmountTextView);
        cashInOutOutTenDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutTenDollarAmountTextView);
        cashInOutOutTwentyDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutTwentyDollarAmountTextView);
        cashInOutOutFiftyDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutFiftyDollarAmountTextView);
        cashInOutOutHundredDollarAmountTextView = (TextView)findViewById(R.id.cashInOutOutHundredDollarAmountTextView);

        cashInOutOutTotalAmountTextView = (TextView)findViewById(R.id.cashInOutOutTotalAmountTextView);

        cashInOutOutOneDollarButton = (Button)findViewById(R.id.cashInOutOutOneDollarButton);
        cashInOutOutTwoDollarButton = (Button)findViewById(R.id.cashInOutOutTwoDollarButton);
        cashInOutOutFiveDollarButton = (Button)findViewById(R.id.cashInOutOutFiveDollarButton);
        cashInOutOutTenDollarButton = (Button)findViewById(R.id.cashInOutOutTenDollarButton);
        cashInOutOutTwentyDollarButton = (Button)findViewById(R.id.cashInOutOutTwentyDollarButton);
        cashInOutOutFiftyDollarButton = (Button)findViewById(R.id.cashInOutOutFiftyDollarButton);
        cashInOutOutHundredDollarButton = (Button)findViewById(R.id.cashInOutOutHundredDollarButton);

        cashInOutOutOneDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutTwoDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutFiveDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutTenDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutTwentyDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutFiftyDollarButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutHundredDollarButton.setOnClickListener(cashInOutBtnClickListener);
        /***********************************************************************************************************/

        /** Cash In/Out 처리 관련 버튼객체 생성 및 리스너 연결 *****************************************************/
        cashInOutInButton = (Button)findViewById(R.id.cashInOutInButton);
        cashInOutOutButton = (Button)findViewById(R.id.cashInOutOutButton);

        cashInOutInButton.setOnClickListener(cashInOutBtnClickListener);
        cashInOutOutButton.setOnClickListener(cashInOutBtnClickListener);
        /***********************************************************************************************************/
    }

    View.OnClickListener cashInOutBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashInOutCloseBtn : {
                    finish();
                    // 초기화
                    setInit();
                    break;
                }
                case R.id.cashInOutInOneDollarButton : {
                    openDollarQtyEditIntent("in", "inOne");
                    break;
                }
                case R.id.cashInOutInTwoDollarButton : {
                    openDollarQtyEditIntent("in", "inTwo");
                    break;
                }
                case R.id.cashInOutInFiveDollarButton : {
                    openDollarQtyEditIntent("in", "inFive");
                    break;
                }
                case R.id.cashInOutInTenDollarButton : {
                    openDollarQtyEditIntent("in", "inTen");
                    break;
                }
                case R.id.cashInOutInTwentyDollarButton : {
                    openDollarQtyEditIntent("in", "inTwenty");
                    break;
                }
                case R.id.cashInOutInFiftyDollarButton : {
                    openDollarQtyEditIntent("in", "inFifty");
                    break;
                }
                case R.id.cashInOutInHundredDollarButton : {
                    openDollarQtyEditIntent("in", "inHundred");
                    break;
                }
                case R.id.cashInOutOutOneDollarButton : {
                    openDollarQtyEditIntent("out", "outOne");
                    break;
                }
                case R.id.cashInOutOutTwoDollarButton : {
                    openDollarQtyEditIntent("out", "outTwo");
                    break;
                }
                case R.id.cashInOutOutFiveDollarButton : {
                    openDollarQtyEditIntent("out", "outFive");
                    break;
                }
                case R.id.cashInOutOutTenDollarButton : {
                    openDollarQtyEditIntent("out", "outTen");
                    break;
                }
                case R.id.cashInOutOutTwentyDollarButton : {
                    openDollarQtyEditIntent("out", "outTwenty");
                    break;
                }
                case R.id.cashInOutOutFiftyDollarButton : {
                    openDollarQtyEditIntent("out", "outFifty");
                    break;
                }
                case R.id.cashInOutOutHundredDollarButton : {
                    openDollarQtyEditIntent("out", "outHundred");
                    break;
                }
                case R.id.cashInOutInDateTextView : {
                    openCustomDatePick(cashInOutInDateTextView);
                    break;
                }
                case R.id.cashInOutInDateButton : {
                    openCustomDatePick(cashInOutInDateTextView);
                    break;
                }
                case R.id.cashInOutOutDateTextView : {
                    openCustomDatePick(cashInOutOutDateTextView);
                    break;
                }
                case R.id.cashInOutOutDateButton : {
                    openCustomDatePick(cashInOutOutDateTextView);
                    break;
                }
            }
        }
    };

    public void openDollarQtyEditIntent(String paramInOutType, String paramTextViewString) {
        switch (paramTextViewString) {
            // Cash In 관련
            case "inOne" : {
                mSelectedTextView = cashInOutInOneDollarTextView;
                mSelectedAmountTextView = cashInOutInOneDollarAmountTextView;
                break;
            }
            case "inTwo" : {
                mSelectedTextView = cashInOutInTwoDollarTextView;
                mSelectedAmountTextView = cashInOutInTwoDollarAmountTextView;
                break;
            }
            case "inFive" : {
                mSelectedTextView = cashInOutInFiveDollarTextView;
                mSelectedAmountTextView = cashInOutInFiveDollarAmountTextView;
                break;
            }
            case "inTen" : {
                mSelectedTextView = cashInOutInTenDollarTextView;
                mSelectedAmountTextView = cashInOutInTenDollarAmountTextView;
                break;
            }
            case "inTwenty" : {
                mSelectedTextView = cashInOutInTwentyDollarTextView;
                mSelectedAmountTextView = cashInOutInTwentyDollarAmountTextView;
                break;
            }
            case "inFifty" : {
                mSelectedTextView = cashInOutInFiftyDollarTextView;
                mSelectedAmountTextView = cashInOutInFiftyDollarAmountTextView;
                break;
            }
            case "inHundred" : {
                mSelectedTextView = cashInOutInHundredDollarTextView;
                mSelectedAmountTextView = cashInOutInHundredDollarAmountTextView;
                break;
            }
            // Cash Out 관련
            case "outOne" : {
                mSelectedTextView = cashInOutOutOneDollarTextView;
                mSelectedAmountTextView = cashInOutOutOneDollarAmountTextView;
                break;
            }
            case "outTwo" : {
                mSelectedTextView = cashInOutOutTwoDollarTextView;
                mSelectedAmountTextView = cashInOutOutTwoDollarAmountTextView;
                break;
            }
            case "outFive" : {
                mSelectedTextView = cashInOutOutFiveDollarTextView;
                mSelectedAmountTextView = cashInOutOutFiveDollarAmountTextView;
                break;
            }
            case "outTen" : {
                mSelectedTextView = cashInOutOutTenDollarTextView;
                mSelectedAmountTextView = cashInOutOutTenDollarAmountTextView;
                break;
            }
            case "outTwenty" : {
                mSelectedTextView = cashInOutOutTwentyDollarTextView;
                mSelectedAmountTextView = cashInOutOutTwentyDollarAmountTextView;
                break;
            }
            case "outFifty" : {
                mSelectedTextView = cashInOutOutFiftyDollarTextView;
                mSelectedAmountTextView = cashInOutOutFiftyDollarAmountTextView;
                break;
            }
            case "outHundred" : {
                mSelectedTextView = cashInOutOutHundredDollarTextView;
                mSelectedAmountTextView = cashInOutOutHundredDollarAmountTextView;
                break;
            }
        }
        Intent qtyEditCashInOutIntent = new Intent(getApplicationContext(), QtyEditCashInOut.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        qtyEditCashInOutIntent.putExtra("selectedInOutType", paramInOutType);
        qtyEditCashInOutIntent.putExtra("selectedDollarType", paramTextViewString);
        // -------------------------------------------------------------------------------------
        startActivity(qtyEditCashInOutIntent);
    }

    public void openCustomDatePick(TextView paramTv) {
        String tempClockInOutSearchDay = paramTv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
            tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
        }
        mSelectedDateTextView = paramTv;
        openDatePickerDialog(tempClockInOutSearchDay);
    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(this, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };


    // 초기화 메소드
    private void setInit() {
    }

}
