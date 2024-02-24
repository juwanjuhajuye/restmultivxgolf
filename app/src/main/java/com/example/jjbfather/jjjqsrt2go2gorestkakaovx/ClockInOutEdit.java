package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.text.SimpleDateFormat;
import java.util.Vector;

public class ClockInOutEdit extends Activity {
    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    Intent mIntent;

    private TextView clockInOutEditEmployeeNameEditText;
    private TextView clockInOutEditDateInEditText, clockInOutEditTimeInEditText, clockInOutEditDateOutEditText, clockInOutEditTimeOutEditText;
    private Button clockInOutEditCloseBtn, clockInOutEditSaveButton, clockInOutEditDeleteButton;

    private TextView clockInOutEditTitleTextView;
    private TextView clockInOutEditSubTitleTextView1, clockInOutEditSubTitleTextView2, clockInOutEditSubTitleTextView3;


    String mClockInOutIdx = "";
    String mEmployeeIdx, mEmployeeName, mClockinDate, mClockinDay, mClockinTime, mClockoutDate;
    String mClockoutDay, mClockoutTime, mClockinout_closeYN;

    SimpleDateFormat dateFormat;

    TextView mSelectedDateTextView, mSelectedTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_clock_in_out_edit);
        this.setFinishOnTouchOutside(false);

        setContents();
    }

    public void setContents() {
        this.mActivity = this;
        this.context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 3;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 4;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 5;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.clockInOutEditLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        dateFormat = new SimpleDateFormat(GlobalMemberValues.STR_DATESTYLE);

        /** 인텐트에서 받은 Extra 값 저장 ***************************************************************************/
        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 salesCode 값
            mClockInOutIdx = mIntent.getStringExtra("clockInOutIdx");
            GlobalMemberValues.logWrite("clockInOutIdx", "넘겨받은 clockInOutIdx : " + mClockInOutIdx + "\n");
            /*******************************************************************************************/
        } else {
            GlobalMemberValues.displayDialog(context, "Waraning", "Invalid Access", "Close");
            finish();
            return;
        }

        // salon_clockinout 테이블 데이터 추출
        Cursor clockInOutCursor;
        String clockInOutQuery = "select employeeidx, employeeName, " +
                " clockinDate, strftime('%m-%d-%Y', clockinday), clockinTime, " +
                " clockoutDate, strftime('%m-%d-%Y', clockoutday), clockoutTime, " +
                " clockinout_closeYN " +
                " from salon_clockinout " +
                " where idx = '" + mClockInOutIdx + "' ";
        GlobalMemberValues.logWrite("clockInOutQuery", "clockInOutQuery : " + clockInOutQuery + "\n");
        clockInOutCursor = dbInit.dbExecuteRead(clockInOutQuery);
        if (clockInOutCursor.getCount() > 0 && clockInOutCursor.moveToFirst()) {
            mEmployeeIdx = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(0), 1);
            mEmployeeName = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(1), 1);
            mClockinDate = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(2), 1);
            mClockinDay = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(3), 1);
            mClockinTime = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(4), 1);
            mClockoutDate = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(5), 1);
            mClockoutDay = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(6), 1);
            mClockoutTime = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(7), 1);
            mClockinout_closeYN = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(8), 1);
        } else {
            GlobalMemberValues.displayDialog(context, "Waraning", "No Data", "Close");
            finish();
            return;
        }


        clockInOutEditTitleTextView = (TextView)findViewById(R.id.clockInOutEditTitleTextView);
        clockInOutEditTitleTextView.setTextSize(clockInOutEditTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditSubTitleTextView1 = (TextView)findViewById(R.id.clockInOutEditSubTitleTextView1);
        clockInOutEditSubTitleTextView1.setTextSize(clockInOutEditSubTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditSubTitleTextView2 = (TextView)findViewById(R.id.clockInOutEditSubTitleTextView2);
        clockInOutEditSubTitleTextView2.setTextSize(clockInOutEditSubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditSubTitleTextView3 = (TextView)findViewById(R.id.clockInOutEditSubTitleTextView3);
        clockInOutEditSubTitleTextView3.setTextSize(clockInOutEditSubTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        /** Name TextView 객체 생성 ********************************************************************************/
        clockInOutEditEmployeeNameEditText = (TextView)findViewById(R.id.clockInOutEditEmployeeNameEditText);
        clockInOutEditEmployeeNameEditText.setText(mEmployeeName);
        clockInOutEditEmployeeNameEditText.setTextSize(clockInOutEditEmployeeNameEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        /***********************************************************************************************************/

        /** Date, Time-In, Time-Out TextView 객체 생성 *****************************************************************/
        clockInOutEditDateInEditText = (TextView)findViewById(R.id.clockInOutEditDateInEditText);
        clockInOutEditDateInEditText.setText(mClockinDay);
        clockInOutEditDateInEditText.setTextSize(clockInOutEditDateInEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditTimeInEditText = (TextView)findViewById(R.id.clockInOutEditTimeInEditText);
        clockInOutEditTimeInEditText.setText(mClockinTime);
        clockInOutEditTimeInEditText.setTextSize(clockInOutEditTimeInEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditDateOutEditText = (TextView)findViewById(R.id.clockInOutEditDateOutEditText);
        clockInOutEditDateOutEditText.setText(mClockoutDay);
        clockInOutEditDateOutEditText.setTextSize(clockInOutEditDateOutEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditTimeOutEditText = (TextView)findViewById(R.id.clockInOutEditTimeOutEditText);
        clockInOutEditTimeOutEditText.setText(mClockoutTime);
        clockInOutEditTimeOutEditText.setTextSize(clockInOutEditTimeOutEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditDateInEditText.setOnClickListener(clockInOutEditClickListenr);
        clockInOutEditTimeInEditText.setOnClickListener(clockInOutEditClickListenr);
        clockInOutEditDateOutEditText.setOnClickListener(clockInOutEditClickListenr);
        clockInOutEditTimeOutEditText.setOnClickListener(clockInOutEditClickListenr);
        /***********************************************************************************************************/

        /** Close(X), 캘린더(C), SAVE, DELETE 버튼 객체 생성 및 리스너 연걸 ****************************************/
        clockInOutEditCloseBtn = (Button)findViewById(R.id.clockInOutEditCloseBtn);

        clockInOutEditSaveButton = (Button)findViewById(R.id.clockInOutEditSaveButton);
        clockInOutEditSaveButton.setTextSize(clockInOutEditSaveButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditDeleteButton = (Button)findViewById(R.id.clockInOutEditDeleteButton);
        clockInOutEditDeleteButton.setTextSize(clockInOutEditDeleteButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutEditCloseBtn.setOnClickListener(clockInOutEditClickListenr);
        clockInOutEditSaveButton.setOnClickListener(clockInOutEditClickListenr);
        clockInOutEditDeleteButton.setOnClickListener(clockInOutEditClickListenr);
        /***********************************************************************************************************/
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

    public void openTimePickerDialog(String paramTime) {
        String tempSplitStr[] = null;
        tempSplitStr = paramTime.split(":");
        int tempHour = Integer.parseInt(tempSplitStr[0]);
        int tempMinute = Integer.parseInt(tempSplitStr[1]);

        TimePickerDialog timeDialog = new TimePickerDialog(this, timeSelectListener, tempHour, tempMinute, true);
        timeDialog.show();
    }

    TimePickerDialog.OnTimeSetListener timeSelectListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tempHour = String.valueOf(hourOfDay);
            if (hourOfDay < 10) {
                tempHour = "0" + tempHour;
            }
            String tempMinute = String.valueOf(minute);
            if (minute < 10) {
                tempMinute = "0" + tempMinute;
            }
            mSelectedTimeTextView.setText(tempHour + ":" + tempMinute);
        }
    };


    public void deleteClockInOut() {
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String strDeleteQuery = " delete from salon_clockinout " +
                " where idx = '" + mClockInOutIdx + "' ";
        strUpdateQueryVec.addElement(strDeleteQuery);
        for (String tempQuery : strUpdateQueryVec) {
            GlobalMemberValues.logWrite("ClockInOutInfoDeleteButtonClickQuery", "Delete query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            return;
        } else { // 정상처리일 경우 리스트 리로드
            clockInOutListViewReload();
            finish();
        }
    }

    View.OnClickListener clockInOutEditClickListenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clockInOutEditCloseBtn : {
                    finish();
                    break;
                }
                case R.id.clockInOutEditDateInEditText : {
                    String tempClockinDay = clockInOutEditDateInEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockinDay)) {
                        tempClockinDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = clockInOutEditDateInEditText;
                    openDatePickerDialog(tempClockinDay);
                    break;
                }
                case R.id.clockInOutEditTimeInEditText : {
                    String tempClockinTime = clockInOutEditTimeInEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockinTime)) {
                        tempClockinTime = GlobalMemberValues.STR_NOW_TIME;
                    }
                    mSelectedTimeTextView = clockInOutEditTimeInEditText;
                    openTimePickerDialog(tempClockinTime);
                    break;
                }
                case R.id.clockInOutEditDateOutEditText : {
                    String tempClockOutDay = clockInOutEditDateOutEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockOutDay)) {
                        tempClockOutDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = clockInOutEditDateOutEditText;
                    openDatePickerDialog(tempClockOutDay);
                    break;
                }
                case R.id.clockInOutEditTimeOutEditText : {
                    String tempClockOutTime = clockInOutEditTimeOutEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockOutTime)) {
                        tempClockOutTime = GlobalMemberValues.STR_NOW_TIME;
                    }
                    mSelectedTimeTextView = clockInOutEditTimeOutEditText;
                    openTimePickerDialog(tempClockOutTime);
                    break;
                }
                case R.id.clockInOutEditSaveButton : {
                    String tempDateIn = clockInOutEditDateInEditText.getText().toString();
                    String tempTimeIn = clockInOutEditTimeInEditText.getText().toString();
                    String tempDateOut = clockInOutEditDateOutEditText.getText().toString();
                    String tempTimeOut = clockInOutEditTimeOutEditText.getText().toString();

                    if (GlobalMemberValues.isStrEmpty(tempDateIn)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Empty Date-In", "Close");
                        return;
                    }
                    if (GlobalMemberValues.isStrEmpty(tempTimeIn)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Empty Date-In", "Close");
                        return;
                    }
                    if (GlobalMemberValues.isStrEmpty(tempDateOut)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Empty Date-Out", "Close");
                        return;
                    }
                    if (GlobalMemberValues.isStrEmpty(tempTimeOut)) {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Empty Date-Out", "Close");
                        return;
                    }

                    // DB 입력을 위해 mm-dd-yyyy 를 yyyy-mm-dd 변경
                    tempDateIn = GlobalMemberValues.convertDate(tempDateIn);
                    tempDateOut = GlobalMemberValues.convertDate(tempDateOut);

                    String strDateTimeInForDB = tempDateIn + " " + tempTimeIn + ":00";
                    String strDateTimeOutForDB = tempDateOut + " " + tempTimeOut + ":00";

                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String strUpdateQuery = "update salon_clockinout set " +
                            " clockinDate = strftime('%Y-%m-%d %H:%M:%S', '" + strDateTimeInForDB + "'), " +
                            " clockinDay = '" + tempDateIn + "', " +
                            " clockinTime = '" + tempTimeIn + "' , " +
                            " clockOutDate = strftime('%Y-%m-%d %H:%M:%S', '" + strDateTimeOutForDB + "'), " +
                            " clockoutDay = '" + tempDateOut + "', " +
                            " clockoutTime = '" + tempTimeOut + "'  " +
                            " where idx = '" + mClockInOutIdx + "' ";
                    strUpdateQueryVec.addElement(strUpdateQuery);
                    String strUpdateQuery2 = " update salon_clockinout set " +
                            " worktimeAmount = ((strftime('%s', clockOutDate) - strftime('%s', clockInDate))/60) " +
                            " where idx = '" + mClockInOutIdx + "' ";
                    strUpdateQueryVec.addElement(strUpdateQuery2);
                    for (String tempQuery : strUpdateQueryVec) {
                        GlobalMemberValues.logWrite("ClockInOutInfoSaveButtonClickQuery", "Update query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = "";
                    returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                        return;
                    } else { // 정상처리일 경우 리스트 리로드
                        clockInOutListViewReload();
                        finish();
                    }
                    break;
                }
                case R.id.clockInOutEditDeleteButton : {
                    new AlertDialog.Builder(context)
                            .setTitle("Clock In / Out")
                            .setMessage("Are you sure you want to delete?")
                                    //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteClockInOut();
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

    public void clockInOutListViewReload() {
        String tempSearchEmployeeIdx = ClockInOut.mSelectedEmpIdx;
        String tempSearchDate = ClockInOut.clockInOutDateEditText.getText().toString();
        ClockInOut.setEmployeeClockInOutInfo(tempSearchEmployeeIdx, tempSearchDate);
    }

}
