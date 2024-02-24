package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CashInOutPopupViewDetail extends Activity {
    final String TAG = "CashInOutPopupViewDetailLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    Button cashInOutPopupCloseButton;

    TextView cashAmtTv01,cashAmtTv02,cashAmtTv03,cashAmtTv04,cashAmtTv05,cashAmtTv06;
    TextView cashAmtTv07,cashAmtTv08,cashAmtTv09,cashAmtTv10,cashAmtTv11,cashAmtTv12;

    TextView cashSuEv01,cashSuEv02,cashSuEv03,cashSuEv04,cashSuEv05,cashSuEv06;
    TextView cashSuEv07,cashSuEv08,cashSuEv09,cashSuEv10,cashSuEv11,cashSuEv12;

    TextView cashInOutReasonTitleTv;

    static String mGetChIdx = "";

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out_popup_view_detail);
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
            mGetChIdx = mIntent.getStringExtra("chidx");
            /*******************************************************************************************/
            GlobalMemberValues.logWrite(TAG, "mGetChIdx : " + mGetChIdx + "\n");
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

        parentLn.setOnClickListener(cashEnterButtonClickListener);
        /***********************************************************************************************************/

        cashInOutPopupCloseButton = (Button)findViewById(R.id.cashInOutPopupCloseButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashInOutPopupCloseButton.setText("");
            cashInOutPopupCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_close_common);
        } else {
            cashInOutPopupCloseButton.setTextSize(
                    cashInOutPopupCloseButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cashInOutPopupCloseButton.setOnClickListener(cashEnterButtonClickListener);

        cashAmtTv01 = (TextView)findViewById(R.id.cashAmtTv01);
        cashAmtTv01.setTextSize(cashAmtTv01.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv02 = (TextView)findViewById(R.id.cashAmtTv02);
        cashAmtTv02.setTextSize(cashAmtTv02.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv03 = (TextView)findViewById(R.id.cashAmtTv03);
        cashAmtTv03.setTextSize(cashAmtTv03.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv04 = (TextView)findViewById(R.id.cashAmtTv04);
        cashAmtTv04.setTextSize(cashAmtTv04.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv05 = (TextView)findViewById(R.id.cashAmtTv05);
        cashAmtTv05.setTextSize(cashAmtTv05.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv06 = (TextView)findViewById(R.id.cashAmtTv06);
        cashAmtTv06.setTextSize(cashAmtTv06.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv07 = (TextView)findViewById(R.id.cashAmtTv07);
        cashAmtTv07.setTextSize(cashAmtTv07.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv08 = (TextView)findViewById(R.id.cashAmtTv08);
        cashAmtTv08.setTextSize(cashAmtTv08.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv09 = (TextView)findViewById(R.id.cashAmtTv09);
        cashAmtTv09.setTextSize(cashAmtTv09.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv10 = (TextView)findViewById(R.id.cashAmtTv10);
        cashAmtTv10.setTextSize(cashAmtTv10.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv11 = (TextView)findViewById(R.id.cashAmtTv11);
        cashAmtTv11.setTextSize(cashAmtTv11.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv12 = (TextView)findViewById(R.id.cashAmtTv12);
        cashAmtTv12.setTextSize(cashAmtTv12.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv01 = (TextView)findViewById(R.id.cashSuEv01);
        cashSuEv01.setTextSize(cashSuEv01.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv02 = (TextView)findViewById(R.id.cashSuEv02);
        cashSuEv02.setTextSize(cashSuEv02.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv03 = (TextView)findViewById(R.id.cashSuEv03);
        cashSuEv03.setTextSize(cashSuEv03.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv04 = (TextView)findViewById(R.id.cashSuEv04);
        cashSuEv04.setTextSize(cashSuEv04.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv05 = (TextView)findViewById(R.id.cashSuEv05);
        cashSuEv05.setTextSize(cashSuEv05.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv06 = (TextView)findViewById(R.id.cashSuEv06);
        cashSuEv06.setTextSize(cashSuEv06.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv07 = (TextView)findViewById(R.id.cashSuEv07);
        cashSuEv07.setTextSize(cashSuEv07.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv08 = (TextView)findViewById(R.id.cashSuEv08);
        cashSuEv08.setTextSize(cashSuEv08.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv09 = (TextView)findViewById(R.id.cashSuEv09);
        cashSuEv09.setTextSize(cashSuEv09.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv10 = (TextView)findViewById(R.id.cashSuEv10);
        cashSuEv10.setTextSize(cashSuEv10.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv11 = (TextView)findViewById(R.id.cashSuEv11);
        cashSuEv11.setTextSize(cashSuEv11.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv12 = (TextView)findViewById(R.id.cashSuEv12);
        cashSuEv12.setTextSize(cashSuEv12.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashInOutReasonTitleTv = (TextView)findViewById(R.id.cashInOutReasonTitleTv);
        cashInOutReasonTitleTv.setTextSize(cashInOutReasonTitleTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        String strReason = dbInit.dbExecuteReadReturnString("select inoutreason from salon_sales_cashintout_history where idx = '" + mGetChIdx + "' ");
        if (!GlobalMemberValues.isStrEmpty(strReason)) {
            cashInOutReasonTitleTv.setText(strReason);
        } else {
            cashInOutReasonTitleTv.setText("");
        }

        setCashTv();
    }

    private void setCashTv() {
        String inouttype = dbInit.dbExecuteReadReturnString("select inouttype from salon_sales_cashintout_history where idx = '" + mGetChIdx + "' ");
        if (GlobalMemberValues.isStrEmpty(inouttype)) {
            inouttype = "+";
        }

        String strQuery = "select cashtype1, cashtype2, cashtype3, cashtype4, cashtype5, cashtype6, " +
                " cashtype7, cashtype8, cashtype9, cashtype10, cashtype11, cashtype12 " +
                " from salon_sales_cashintout_history_cashlist " +
                " where chidx = '" + mGetChIdx + "' ";
        Cursor detailViewCursor = dbInit.dbExecuteRead(strQuery);
        if (detailViewCursor.getCount() > 0 && detailViewCursor.moveToFirst()) {
            String[] inouttypeArr = {"", "", "", "", "", "", "", "", "", "", "", ""};
            if (inouttype.equals("-")) {
                for (int i = 0; i < 12; i++) {
                    inouttypeArr[i] = "-";
                }
            }

            int cashtype1 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(0), 1));
            int cashtype2 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(1), 1));
            int cashtype3 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(2), 1));
            int cashtype4 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(3), 1));
            int cashtype5 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(4), 1));
            int cashtype6 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(5), 1));
            int cashtype7 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(6), 1));
            int cashtype8 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(7), 1));
            int cashtype9 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(8), 1));
            int cashtype10 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(9), 1));
            int cashtype11 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(10), 1));
            int cashtype12 = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(detailViewCursor.getString(11), 1));

            if (cashtype1 > 0) {
                cashSuEv01.setText(GlobalMemberValues.getCommaStringForInteger(cashtype1 + ""));
            } else {
                inouttypeArr[0] = "";
            }
            if (cashtype2 > 0) {
                cashSuEv02.setText(GlobalMemberValues.getCommaStringForInteger(cashtype2 + ""));
            } else {
                inouttypeArr[1] = "";
            }
            if (cashtype3 > 0) {
                cashSuEv03.setText(GlobalMemberValues.getCommaStringForInteger(cashtype3 + ""));
            } else {
                inouttypeArr[2] = "";
            }
            if (cashtype4 > 0) {
                cashSuEv04.setText(GlobalMemberValues.getCommaStringForInteger(cashtype4 + ""));
            } else {
                inouttypeArr[3] = "";
            }
            if (cashtype5 > 0) {
                cashSuEv05.setText(GlobalMemberValues.getCommaStringForInteger(cashtype5 + ""));
            } else {
                inouttypeArr[4] = "";
            }
            if (cashtype6 > 0) {
                cashSuEv06.setText(GlobalMemberValues.getCommaStringForInteger(cashtype6 + ""));
            } else {
                inouttypeArr[5] = "";
            }
            if (cashtype7 > 0) {
                cashSuEv07.setText(GlobalMemberValues.getCommaStringForInteger(cashtype7 + ""));
            } else {
                inouttypeArr[6] = "";
            }
            if (cashtype8 > 0) {
                cashSuEv08.setText(GlobalMemberValues.getCommaStringForInteger(cashtype8 + ""));
            } else {
                inouttypeArr[7] = "";
            }
            if (cashtype9 > 0) {
                cashSuEv09.setText(GlobalMemberValues.getCommaStringForInteger(cashtype9 + ""));
            } else {
                inouttypeArr[8] = "";
            }
            if (cashtype10 > 0) {
                cashSuEv10.setText(GlobalMemberValues.getCommaStringForInteger(cashtype10 + ""));
            } else {
                inouttypeArr[9] = "";
            }
            if (cashtype11 > 0) {
                cashSuEv11.setText(GlobalMemberValues.getCommaStringForInteger(cashtype11 + ""));
            } else {
                inouttypeArr[10] = "";
            }
            if (cashtype12 > 0) {
                cashSuEv12.setText(GlobalMemberValues.getCommaStringForInteger(cashtype12 + ""));
            } else {
                inouttypeArr[11] = "";
            }

            cashAmtTv01.setText(inouttypeArr[0] + GlobalMemberValues.getCommaStringForDouble((cashtype1 * 100) + ""));
            cashAmtTv02.setText(inouttypeArr[1] + GlobalMemberValues.getCommaStringForDouble((cashtype2 * 50) + ""));
            cashAmtTv03.setText(inouttypeArr[2] + GlobalMemberValues.getCommaStringForDouble((cashtype3 * 20) + ""));
            cashAmtTv04.setText(inouttypeArr[3] + GlobalMemberValues.getCommaStringForDouble((cashtype4 * 10) + ""));
            cashAmtTv05.setText(inouttypeArr[4] + GlobalMemberValues.getCommaStringForDouble((cashtype5 * 5) + ""));
            cashAmtTv06.setText(inouttypeArr[5] + GlobalMemberValues.getCommaStringForDouble((cashtype6 * 2) + ""));
            cashAmtTv07.setText(inouttypeArr[6] + GlobalMemberValues.getCommaStringForDouble((cashtype7 * 1) + ""));
            cashAmtTv08.setText(inouttypeArr[7] + GlobalMemberValues.getCommaStringForDouble((cashtype8 * 0.5) + ""));
            cashAmtTv09.setText(inouttypeArr[8] + GlobalMemberValues.getCommaStringForDouble((cashtype9 * 0.25) + ""));
            cashAmtTv10.setText(inouttypeArr[9] + GlobalMemberValues.getCommaStringForDouble((cashtype10 * 0.1) + ""));
            cashAmtTv11.setText(inouttypeArr[10] + GlobalMemberValues.getCommaStringForDouble((cashtype11 * 0.05) + ""));
            cashAmtTv12.setText(inouttypeArr[11] + GlobalMemberValues.getCommaStringForDouble((cashtype12 * 0.01) + ""));
        }
    }

    View.OnClickListener cashEnterButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashInOutPopupCloseButton : {
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

    private void closeActivity() {
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
        }
    }
}
