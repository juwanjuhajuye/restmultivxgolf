package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class ClockInOut extends Activity {
    Activity mActivity;
    public static Context context;
    // DB 객체 선언
    static DatabaseInit dbInit;

    GetDataAtSQLite dataAtSqlite;

    LinearLayout parentLn;

    Button closeBtn;

    static TextView clockInOutDateEditText;
    TextView clockInOutEmployeeNameEditText;
    EditText clockInOutEmployeePasswordEditText;
    Button clockInOutClockInButton, clockInOutClockOutButton;
    Button clockInOutSearchButton;

    TextView clockInOutTitleTextView;
    TextView clockInOutNameTitleTextView;
    TextView clockInOutPasswordTitleTextView;
    TextView clockInOutDateTitleTextView;
    TextView clockInOutListTitleTextView1, clockInOutListTitleTextView2, clockInOutListTitleTextView3;

    // ListView 에 Clock In / Out 붙일 때 필요한 객체들
    static TemporaryClockInOut mTempClockInOut;
    public static ArrayList<TemporaryClockInOut> mGeneralArrayList;
    public static ClockInOutInfoAdapter mClockInOutAdapter;

    // 고객정보 보여지는 리스트뷰
    static ListView mClockInOutListView;

    static String mSelectedEmpIdx = "";
    String mSelectedEmpName = "";
    String mSelectedEmpPwd = "";
    String mSelectedELevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_clock_in_out);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        setContents();
    }

    public void setContents() {
        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);
        dataAtSqlite = new GetDataAtSQLite(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 9;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.clockInOutLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        clockInOutTitleTextView = (TextView)findViewById(R.id.clockInOutTitleTextView);
        clockInOutTitleTextView.setTextSize(clockInOutTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutNameTitleTextView = (TextView)findViewById(R.id.clockInOutNameTitleTextView);
        clockInOutNameTitleTextView.setTextSize(clockInOutNameTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutPasswordTitleTextView = (TextView)findViewById(R.id.clockInOutPasswordTitleTextView);
        clockInOutPasswordTitleTextView.setTextSize(clockInOutPasswordTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutDateTitleTextView = (TextView)findViewById(R.id.clockInOutDateTitleTextView);
        clockInOutDateTitleTextView.setTextSize(clockInOutDateTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutListTitleTextView1 = (TextView)findViewById(R.id.clockInOutListTitleTextView1);
        clockInOutListTitleTextView1.setTextSize(clockInOutListTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutListTitleTextView2 = (TextView)findViewById(R.id.clockInOutListTitleTextView2);
        clockInOutListTitleTextView2.setTextSize(clockInOutListTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockInOutListTitleTextView3 = (TextView)findViewById(R.id.clockInOutListTitleTextView3);
        clockInOutListTitleTextView3.setTextSize(clockInOutListTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn.findViewWithTag("clockInOutCloseBtnTag");
        closeBtn.setOnClickListener(clockInOutBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        /** textview 객체 생성 ***********************************************************************/
        clockInOutEmployeeNameEditText = (TextView)findViewById(R.id.clockInOutEmployeeNameEditText);
        clockInOutDateEditText = (TextView)findViewById(R.id.clockInOutDateEditText);
        clockInOutDateEditText.setOnClickListener(clockInOutBtnClickListener);
        /*********************************************************************************************/
        /** EditText 객체 생성 ***********************************************************************/
        clockInOutEmployeePasswordEditText = (EditText)findViewById(R.id.clockInOutEmployeePasswordEditText);
        /*********************************************************************************************/
        /** button 객체 생성 및 리스너 연결 **********************************************************/
        clockInOutClockInButton = (Button)findViewById(R.id.clockInOutClockInButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            clockInOutClockInButton.setText("");
            clockInOutClockInButton.setBackgroundResource(R.drawable.ab_imagebutton_clockinout_clockin);
        } else {
            clockInOutClockInButton.setTextSize(
                    clockInOutClockInButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        clockInOutClockOutButton = (Button)findViewById(R.id.clockInOutClockOutButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            clockInOutClockOutButton.setText("");
            clockInOutClockOutButton.setBackgroundResource(R.drawable.ab_imagebutton_clockinout_clockout);
        } else {
            clockInOutClockOutButton.setTextSize(
                    clockInOutClockOutButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        clockInOutSearchButton = (Button)findViewById(R.id.clockInOutSearchButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            clockInOutSearchButton.setText("");
            clockInOutSearchButton.setBackgroundResource(R.drawable.ab_imagebutton_clockinout_search);
        } else {
            clockInOutSearchButton.setTextSize(
                    clockInOutSearchButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }


        clockInOutClockInButton.setOnClickListener(clockInOutBtnClickListener);
        clockInOutClockOutButton.setOnClickListener(clockInOutBtnClickListener);
        clockInOutSearchButton.setOnClickListener(clockInOutBtnClickListener);
        /*********************************************************************************************/

        /** Clock in / out 검색결과 보여지는 ListView 객체 생성 **************************************/
        mClockInOutListView = (ListView)findViewById(R.id.clockInOutListView);
        /*********************************************************************************************/

        setEmployeeInfo();
    }

    /** 직원 배치하기 ********************************************************/
    public void setEmployeeInfo() {
        setInit();

        // 직원배열 배열 가져오기 (GetDataAtSQLite 클래스의 getEmployeeInfo 메소드를 통해 가져온다)
        String[] strArrEmployee = dataAtSqlite.getEmployeeInfo("", "", "");

        String strEmployeeInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempEmpName = "";

        // 안드로이드 포스 시스템 정보에 직원 GridLayout 에 보여줄 item 수로 저장된 수만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.EmployeeItem_Count_At_TableLayout; i++) {
            if (!GlobalMemberValues.isStrEmpty(strArrEmployee[i])) {
                strEmployeeInfo = strArrEmployee[i];
                //GlobalMemberValues.logWrite("ClockInOutEmployeeInfo", "array : " + strEmployeeInfo + "\n");
                String[] tempStrEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);
                // 직원 이름 및 idx 값이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfo) && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[0])
                        && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[1])) {
                    tempEmpName = tempStrEmployeeInfoArr[1];

                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    LinearLayout employeeLinear
                            = (LinearLayout) parentLn.findViewWithTag("clockInOutLinearTag" + (i+1));

                    // 버튼을 동적으로 생성한다.
                    Button empBtnInLinear = new Button(context);
                    LinearLayout.LayoutParams btnParam
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    empBtnInLinear.setLayoutParams(btnParam);
                    // empBtnInLinear.setBackgroundColor(Color.parseColor("#fff8e8"));
                    empBtnInLinear.setBackgroundResource(R.drawable.button_selector_clockinout_employee);
                    empBtnInLinear.setText(tempEmpName);
                    empBtnInLinear.setTextColor(Color.parseColor("#ffffff"));
                    empBtnInLinear.setTextSize(GlobalMemberValues.BASICCLOCKINOUTEMPLOYEELISTTTEXTSIZE);
                    empBtnInLinear.setGravity(Gravity.CENTER);

                    empBtnInLinear.setTag(strEmployeeInfo);
                    // 동적으로 생성한 직원버튼 클릭시 이벤트리스너 연결
                    empBtnInLinear.setOnClickListener(clockInOutEmployeeSelectClickListener);

                    // LinearLayout 에 버튼을 붙인다.
                    employeeLinear.addView(empBtnInLinear);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrEmployee[i] + "\n");
            }
        }
    }
    /*******************************************************************************/

    /** 직원 Clock in / out 정보 리스트뷰 배치하기 ***************************************************************/
    public static void setEmployeeClockInOutInfo(String paramEmpIdx, String paramDate) {
        mTempClockInOut = null;
        mGeneralArrayList = null;
        mClockInOutAdapter = null;

        String searchQueryByEmpIdx = "";
        if (!GlobalMemberValues.isStrEmpty(paramEmpIdx)) {
            searchQueryByEmpIdx = " and employeeIdx = '" + paramEmpIdx + "' ";
        }
        String searchQueryByDate = "";
        if (!GlobalMemberValues.isStrEmpty(paramDate)) {
            searchQueryByDate = " and strftime('%m-%d-%Y', clockinDate) between '" + paramDate + "' and '" + paramDate + "' ";
        }
        String clockInOutQuery = "select idx, sidx, stcode, employeeIdx, employeeName, " +
                " clockinDate, strftime('%m-%d-%Y', clockinDay), clockinTime, " +
                " clockoutDate, strftime('%m-%d-%Y', clockoutDay), clockoutTime, " +
                " clockinout_closeYN, " +
                " workTimeAmount " +
                " from salon_clockinout " +
                " where sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                searchQueryByEmpIdx + searchQueryByDate +
                " order by clockinDate desc ";

        GlobalMemberValues.logWrite("ClockInOutInfo", "Query : " + clockInOutQuery + "\n");
        Cursor clockInOutCursor = dbInit.dbExecuteRead(clockInOutQuery);
        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryClockInOut>();
        int clockInOutCount = 1;
        while (clockInOutCursor.moveToNext()) {
            String employeeName = clockInOutCursor.getString(2);
            // 직원이름이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(employeeName, " ", ""))) {
                // 근무시간
                String workTimeAmount = "";
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(12), 1))) {
                    workTimeAmount = "-";
                } else {
                    String strWorkTimeHour = "00";
                    String strWorkTimeMinute = "00";
                    int workTimeHour = clockInOutCursor.getInt(12) / 60;
                    int workTimeMinute = clockInOutCursor.getInt(12) % 60;
                    if (workTimeHour > 0) {
                        if (workTimeHour < 10) {
                            strWorkTimeHour = "0" + workTimeHour;
                        } else {
                            strWorkTimeHour = String.valueOf(workTimeHour);
                        }
                    }
                    if (workTimeMinute > 0) {
                        if (workTimeMinute < 10) {
                            strWorkTimeMinute = "0" + workTimeMinute;
                        } else {
                            strWorkTimeMinute = String.valueOf(workTimeMinute);
                        }
                    }
                    workTimeAmount = strWorkTimeHour + ":" + strWorkTimeMinute;
                }
                GlobalMemberValues.logWrite("clockInOutWorktimeAmount", "clockInOutWorktimeAmount : " + clockInOutCursor.getInt(12) + "\n");
                String strOutDate = "";
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(8), 1))) {
                    strOutDate = "--";
                } else {
                    strOutDate = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(8), 1);
                }
                String strOutDay = "";
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(9), 1))) {
                    strOutDay = "--";
                } else {
                    strOutDay = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(9), 1);
                }
                String strOutTime = "";
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(10), 1))) {
                    strOutTime = "--";
                } else {
                    strOutTime = GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(10), 1);
                }

                //TemporaryClockInOutInfo 객체 생성시 전달할 값을 String 배열로 만든다.
                String paramsTempClockInOutInfoArray[] = {
                        String.valueOf(clockInOutCursor.getInt(0)),                                         // idx
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(1), 1),         // sidx
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(2), 1),         // stcode
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(3), 1),         // employeeIdx
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(4), 1),         // employeeName
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(5), 1),         // clockinDate
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(6), 1),         // clockinDay
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(7), 1),         // clockinTime
                        strOutDate,                                                                         // clockoutDate
                        strOutDay,                                                                          // clockoutDay
                        strOutTime,                                                                         // clockoutTime
                        workTimeAmount,                                                                     // workTimeAmount (clockinDate - clockoutDate)
                        GlobalMemberValues.getDBTextAfterChecked(clockInOutCursor.getString(11), 1),        // clockinout_closeYN
                        String.valueOf(clockInOutCount)                                                     // count
                };
                mTempClockInOut = new TemporaryClockInOut(paramsTempClockInOutInfoArray);
                mGeneralArrayList.add(mTempClockInOut);
                clockInOutCount++;
            }
        }
        mClockInOutAdapter = new ClockInOutInfoAdapter(context, R.layout.clockinout_search_list, mGeneralArrayList);
        mClockInOutListView.setAdapter(mClockInOutAdapter);

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        mClockInOutListView.setOnItemClickListener(mCustomerInfoItemClickListener);
    }
    /*************************************************************************************************************/

    static AdapterView.OnItemClickListener mCustomerInfoItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    View.OnClickListener clockInOutEmployeeSelectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // Tag 값이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(btn.getTag().toString())) {
                String strEmployeeInfo = btn.getTag().toString();
                GlobalMemberValues.logWrite("ClockInOutEmployeeInfoSelected", "array : " + strEmployeeInfo + "\n");
                String[] strEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);
                // Tag 에 직원이름이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
                    GlobalMemberValues.logWrite("ClockInOutEmployeeInfoSelected", "pwd : " + strEmployeeInfoArr[9] + "\n");
                    mSelectedEmpIdx = strEmployeeInfoArr[0];
                    mSelectedEmpName = strEmployeeInfoArr[1];
                    //mSelectedEmpPwd = strEmployeeInfoArr[9];
                    mSelectedEmpPwd = dbInit.dbExecuteReadReturnString(
                            "select clockinoutpwd from salon_storeemployee where idx = '" + mSelectedEmpIdx + "' ");
                    mSelectedELevel = strEmployeeInfoArr[10];

                    clockInOutEmployeeNameEditText.setText(mSelectedEmpName);

                    // 비밀번호가 없을 경우 비밀번호 입력란을 비활성화 한다.
                    if (GlobalMemberValues.isStrEmpty(mSelectedEmpPwd)) {
                        clockInOutEmployeePasswordEditText.setEnabled(false);
                        clockInOutEmployeePasswordEditText.setBackgroundResource(R.drawable.roundlayout_button_gray);
                    } else {
                        clockInOutEmployeePasswordEditText.setEnabled(true);
                        clockInOutEmployeePasswordEditText.setBackgroundResource(R.drawable.roundlayout_button_white);
                    }

                    // 해당 직원을 클릭할 경우 해당 직원의 clock in / out 정보를 listview 에 보여준다.
                    clockInOutEmployeePasswordEditText.setText("");
                    clockInOutDateEditText.setText("");     // 직원선택시 검색일 초기화
                    setEmployeeClockInOutInfo(mSelectedEmpIdx, "");
                }
            }
        }
    };


    View.OnClickListener clockInOutBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clockInOutCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.clockInOutClockInButton : {
                    String tempEmpName = clockInOutEmployeeNameEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempEmpName) || GlobalMemberValues.isStrEmpty(mSelectedEmpIdx)) {
                        GlobalMemberValues.displayDialog(context, "Clock In / Out", "Select a Employee", "Close");
                        return;
                    }
                    if (!GlobalMemberValues.isStrEmpty(mSelectedEmpPwd)) {
                        String tempEmpPassword = clockInOutEmployeePasswordEditText.getText().toString();
                        if (GlobalMemberValues.isStrEmpty(tempEmpPassword)) {
                            GlobalMemberValues.displayDialog(context, "Clock In / Out", "Enter Password", "Close");
                            return;
                        } else {
                            GlobalMemberValues.logWrite("ClocInOutPassword", "입력 비밀번호 : " + tempEmpPassword + "\n");
                            GlobalMemberValues.logWrite("ClocInOutPassword", "직원 비밀번호 : " + mSelectedEmpPwd + "\n");
                            // 비밀번호를 체크한다.
                            if (!(mSelectedEmpPwd.equals(tempEmpPassword) || mSelectedEmpPwd == tempEmpPassword)) {
                                GlobalMemberValues.displayDialog(context, "Clock In / Out", "Invalid Password", "Close");
                                return;
                            }
                        }
                    }

                    String returnResult = "";
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String strInsClockInOutQuery = "insert into salon_clockinout ( " +
                            " sidx, stcode, employeeIdx, employeeName, " +
                            " clockinDate, clockinDay, clockinTime, " +
                            " clockoutDate, clockoutDay, clockoutTime, " +
                            " worktimeAmount " +
                            " ) values ( " +
                            " '" + GlobalMemberValues.STORE_INDEX + "', " +
                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                            " '" + mSelectedEmpIdx + "', " +
                            " '" + mSelectedEmpName + "', " +
                            " datetime('now', 'localtime'), " +
                            " strftime('%Y-%m-%d', datetime('now', 'localtime')), " +
                            " strftime('%H:%M', datetime('now', 'localtime')), " +
                            " '', '', '', 0 " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsClockInOutQuery);
                    for (String tempQuery : strInsertQueryVec) {
                        GlobalMemberValues.logWrite("ClockInOutInfo", "Insert query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                        return;
                    } else { // 정상처리일 경우 리스트 리로드
                        setEmployeeClockInOutInfo(mSelectedEmpIdx, "");
                    }

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, clockInOutEmployeePasswordEditText);
                    break;
                }
                case R.id.clockInOutClockOutButton : {
                    String tempEmpName = clockInOutEmployeeNameEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempEmpName) || GlobalMemberValues.isStrEmpty(mSelectedEmpIdx)) {
                        GlobalMemberValues.displayDialog(context, "Clock In / Out", "Select a Employee", "Close");
                        return;
                    }
                    if (!GlobalMemberValues.isStrEmpty(mSelectedEmpPwd)) {
                        String tempEmpPassword = clockInOutEmployeePasswordEditText.getText().toString();
                        if (GlobalMemberValues.isStrEmpty(tempEmpPassword)) {
                            GlobalMemberValues.displayDialog(context, "Clock In / Out", "Enter Password", "Close");
                            return;
                        } else {
                            GlobalMemberValues.logWrite("ClocInOutPassword", "입력 비밀번호 : " + tempEmpPassword + "\n");
                            GlobalMemberValues.logWrite("ClocInOutPassword", "직원 비밀번호 : " + mSelectedEmpPwd + "\n");
                            // 비밀번호를 체크한다.
                            if (!(mSelectedEmpPwd.equals(tempEmpPassword) || mSelectedEmpPwd == tempEmpPassword)) {
                                GlobalMemberValues.displayDialog(context, "Clock In / Out", "Invalid Password", "Close");
                                return;
                            }
                        }
                    }
                    // 출근기록이 있는지 확인한다.
                    String clockInOutIdx = "";
                    String tempClockInOutQuery = " select idx from salon_clockinout " +
                            " where employeeIdx = " + mSelectedEmpIdx + " and clockinout_closeYN = 'N' ";
                    clockInOutIdx = dbInit.dbExecuteReadReturnString(tempClockInOutQuery);
                    if (GlobalMemberValues.isStrEmpty(clockInOutIdx)) {
                        GlobalMemberValues.displayDialog(context, "Clock In / Out", "Clock In First", "Close");
                        return;
                    } else {
                        String returnResult = "";
                        Vector<String> strInsertQueryVec = new Vector<String>();
                        String strUpdClockInOutQuery1 = " update salon_clockinout set " +
                                " clockoutDate = datetime('now', 'localtime'), " +
                                " clockoutDay = strftime('%Y-%m-%d', datetime('now', 'localtime')), " +
                                " clockoutTime = strftime('%H:%M', datetime('now', 'localtime')), " +
                                " clockinout_closeYN = 'Y' " +
                                " where idx = '" + clockInOutIdx + "' ";
                        strInsertQueryVec.addElement(strUpdClockInOutQuery1);
                        String strUpdClockInOutQuery2 = " update salon_clockinout set " +
                                " worktimeAmount = ((strftime('%s', clockOutDate) - strftime('%s', clockInDate))/60) " +
                                " where idx = '" + clockInOutIdx + "' ";
                        strInsertQueryVec.addElement(strUpdClockInOutQuery2);

                        for (String tempQuery : strInsertQueryVec) {
                            GlobalMemberValues.logWrite("ClockInOutInfoUpdateQuery", "Update query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                            return;
                        } else { // 정상처리일 경우 리스트 리로드
                            setEmployeeClockInOutInfo(mSelectedEmpIdx, "");
                        }

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, clockInOutEmployeePasswordEditText);
                    }
                    break;
                }
                case R.id.clockInOutDateEditText : {
                    String tempClockInOutSearchDay = clockInOutDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    openDatePickerDialog(tempClockInOutSearchDay);

                    break;
                }
                case R.id.clockInOutSearchButton : {
                    String tempEmpidx = mSelectedEmpIdx;
                    String tempDate = clockInOutDateEditText.getText().toString();
                    setEmployeeClockInOutInfo(tempEmpidx, tempDate);
                    break;
                }
            }
        }
    };

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
            clockInOutDateEditText.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    // 초기화 메소드
    private void setInit() {
        clockInOutEmployeeNameEditText.setText("");
        clockInOutEmployeePasswordEditText.setText("");
        clockInOutDateEditText.setText("");
        mSelectedEmpIdx = "";
        mSelectedEmpName = "";
        mSelectedEmpPwd = "";
        mSelectedELevel = "";
    }
}
