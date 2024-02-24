package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class EndOfDaySelectDate extends Activity {
    Activity mActivity;
    Context context;

    private Button eodDateCloseBtn;

    private Button submitEDODateBtn;

    private TextView toptexttv, eodDateTextView;

    TextView mSelectedDateTextView;

    Intent mIntent;

    String mParamEODDate = "";

    private LinearLayout parentLn, emptyLinearLayout, emptyLinearLayout2, emptyLinearLayout3, emptyLinearLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_end_of_day_select_date);
        this.setFinishOnTouchOutside(false);

        parentLn = (LinearLayout)findViewById(R.id.eoddateparentln);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mParamEODDate = mIntent.getStringExtra("parameoddate");
            GlobalMemberValues.logWrite("mParamEODDate", "넘겨받은 mParamEODDate : " + mParamEODDate + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finishActivity();
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


        mActivity = this;
        context = this;

        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();


        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        eodDateCloseBtn = (Button) findViewById(R.id.eodDateCloseBtn);
        eodDateCloseBtn.setText("");
        //eodDateCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        eodDateCloseBtn.setOnClickListener(eodDateBtnClickListener);

        submitEDODateBtn = (Button) findViewById(R.id.submitEDODateBtn);
        submitEDODateBtn.setText("");
        submitEDODateBtn.setOnClickListener(eodDateBtnClickListener);

        toptexttv = (TextView) findViewById(R.id.toptexttv);
        toptexttv.setTextSize(toptexttv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        eodDateTextView = (TextView)findViewById(R.id.eodDateTextView);
        eodDateTextView.setTextSize(eodDateTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        eodDateTextView.setOnClickListener(eodDateBtnClickListener);

        String strSearchDate = GlobalMemberValues.STR_NOW_DATE;
        if (GlobalMemberValues.isStrEmpty(eodDateTextView.getText().toString())) {
            eodDateTextView.setText(strSearchDate);
        }
    }

    View.OnClickListener eodDateBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.eodDateTextView: {
                    String tempEodDate = eodDateTextView.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempEodDate)) {
                        tempEodDate = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = eodDateTextView;
                    openDatePickerDialog(tempEodDate);

                    break;
                }
                case R.id.eodDateCloseBtn: {
                    finishActivity();
                    break;
                }
                case R.id.submitEDODateBtn: {
                    setEODDate();

                    break;
                }
            }
        }
    };

    public void setEODDate() {
        new AlertDialog.Builder(mActivity)
                .setTitle("END OF DAY")
                .setMessage("Do you want to end of day?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tempDate = eodDateTextView.getText().toString();
                        if (GlobalMemberValues.isStrEmpty(tempDate)) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Select date", "Close");
                            return;
                        } else {
                            EndOfDay.mEOD_Date = tempDate;
                            EndOfDay.setEndOfDayAfterCheck();

                            finishActivity();
                        }
                    }
                }).show();
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

    public void finishActivity() {
        mParamEODDate = "";
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
        mActivity.finish();
    }
}