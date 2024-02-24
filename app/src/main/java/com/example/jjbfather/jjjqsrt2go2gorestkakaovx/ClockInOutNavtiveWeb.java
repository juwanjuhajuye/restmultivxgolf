package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClockInOutNavtiveWeb extends Activity {
    static Activity mActivity;
    Context context;

    GetDataAtSQLite dataAtSqlite;

    private final Handler handler = new Handler();

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private WebView clockinoutWebPageWebView;

    static LinearLayout inoutLn, editLn;

    private LinearLayout clockinoutWebPageTopBarLinearLayout1, clockinoutWebPageTopBarLinearLayout2;

    private TextView titleTv1, titleTv2, titleTv3;
    private TextView titleTv4, titleTv5, titleTv6;

    static TextView empTv;
    static EditText pwdEv;

    static EditText memoEv, memoEv2;

    static TextView indateTv, intimeTv;
    static TextView outdateTv, outtimeTv;

    private Button clockinbtn, clockoutbtn;
    private Button breakinbtn, breakoutbtn;
    private Button savebtn, deletebtn;

    private String mCurrentUrl;

    Intent mIntent;

    String mSelectedClockInOutIdxs = "";

    static String mSelectedEmpIdx;
    String mClockinoutIdx = "";

    TextView mSelectedDateTextView;
    TextView mSelectedTimeTextView;

    // Edit 일 경우 데이터
    static String mGetClockInOutIdx = "";
    static String mGetClockInDay = "";
    static String mGetClockInTime = "";
    static String mGetClockOutDay = "";
    static String mGetClockOutTime = "";
    static String mGetMemo = "";
    static String mGetEmployeeIdx = "";
    static String mGetFromDate = "";
    static String mGetToDate = "";
    static String mGetPageNo = "";

    public TextView clock_in_out_top_nowtime;
    public TextView clock_in_out_top_nowdate;

    ScheduledExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_clock_in_out_navtive_web);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        int childLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 85;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        }

        parentLn = (LinearLayout)findViewById(R.id.clockinoutWebPageLinearLayout);
        //parentLn.setLayoutParams(parentLnParams);

        FrameLayout.LayoutParams fln = new FrameLayout.LayoutParams(parentLnWidth,parentLnHeight);
        fln.setMargins(0,0,0,0);
        parentLn.setLayoutParams(fln);


        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mClockinoutIdx = mIntent.getStringExtra("clockinoutIdx");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();

        LinearLayout.LayoutParams webViewLn
                = new LinearLayout.LayoutParams((parentLnWidth / 100) * 78, childLnHeight);
        webViewLn.setMargins(0, 0, 0, 0);
        clockinoutWebPageWebView.setLayoutParams(webViewLn);
        LinearLayout.LayoutParams inoutViewLn
                = new LinearLayout.LayoutParams((parentLnWidth / 100) * 22, childLnHeight);
        webViewLn.setMargins(0, 0, 0, 0);
        inoutLn.setLayoutParams(inoutViewLn);
        editLn.setLayoutParams(inoutViewLn);
    }

    public void setContents() {
        dataAtSqlite = new GetDataAtSQLite(this);

        clockinoutWebPageTopBarLinearLayout1 = (LinearLayout) findViewById(R.id.clockinoutWebPageTopBarLinearLayout1);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            clockinoutWebPageTopBarLinearLayout1.setPadding(5, 5, 5, 5);
        }
        clockinoutWebPageTopBarLinearLayout2 = (LinearLayout) findViewById(R.id.clockinoutWebPageTopBarLinearLayout2);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            clockinoutWebPageTopBarLinearLayout2.setPadding(5, 5, 5, 5);
        }

        inoutLn = (LinearLayout) findViewById(R.id.inoutLn);
        editLn = (LinearLayout) findViewById(R.id.editLn);

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.clockinoutWebPageCloseBtn);
        closeBtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        /***********************************************************************************************************/

        titleTv1 = (TextView)findViewById(R.id.titleTv1);
        titleTv1.setTextSize(titleTv1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv2 = (TextView)findViewById(R.id.titleTv2);
        titleTv2.setTextSize(titleTv2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv3 = (TextView)findViewById(R.id.titleTv3);
        titleTv3.setTextSize(titleTv3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv4 = (TextView)findViewById(R.id.titleTv4);
        titleTv4.setTextSize(titleTv4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv5 = (TextView)findViewById(R.id.titleTv5);
        titleTv5.setTextSize(titleTv5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv6 = (TextView)findViewById(R.id.titleTv6);
        titleTv6.setTextSize(titleTv6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        empTv = (TextView)findViewById(R.id.empTv);
        empTv.setTextSize(empTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        empTv.setText("");
        empTv.setOnClickListener(clockinoutWebPageBtnClickListener);

        indateTv = (TextView)findViewById(R.id.indateTv);
        indateTv.setTextSize(indateTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        indateTv.setText(GlobalMemberValues.STR_NOW_DATE);
        indateTv.setOnClickListener(clockinoutWebPageBtnClickListener);

        intimeTv = (TextView)findViewById(R.id.intimeTv);
        intimeTv.setTextSize(intimeTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        intimeTv.setText(GlobalMemberValues.STR_NOW_TIME);
        intimeTv.setOnClickListener(clockinoutWebPageBtnClickListener);

        outdateTv = (TextView)findViewById(R.id.outdateTv);
        outdateTv.setTextSize(outdateTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        outdateTv.setText(GlobalMemberValues.STR_NOW_DATE);
        outdateTv.setOnClickListener(clockinoutWebPageBtnClickListener);

        outtimeTv = (TextView)findViewById(R.id.outtimeTv);
        outtimeTv.setTextSize(outtimeTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        outtimeTv.setText(GlobalMemberValues.STR_NOW_TIME);
        outtimeTv.setOnClickListener(clockinoutWebPageBtnClickListener);

        pwdEv = (EditText)findViewById(R.id.pwdEv);
        pwdEv.setTextSize(pwdEv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        memoEv = (EditText)findViewById(R.id.memoEv);
        memoEv.setText("");
        memoEv.setTextSize(memoEv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        memoEv2 = (EditText)findViewById(R.id.memoEv2);
        memoEv2.setText("");
        memoEv2.setTextSize(memoEv2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockinbtn = (Button) findViewById(R.id.clockinbtn);
        clockinbtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        clockinbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + clockinbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        clockoutbtn = (Button) findViewById(R.id.clockoutbtn);
        clockoutbtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        clockoutbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + clockoutbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        breakinbtn = (Button) findViewById(R.id.breakinbtn);
        breakinbtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        breakinbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + breakinbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        breakoutbtn = (Button) findViewById(R.id.breakoutbtn);
        breakoutbtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        breakoutbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + breakoutbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        savebtn = (Button) findViewById(R.id.savebtn);
        savebtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        savebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + savebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        deletebtn = (Button) findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        deletebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + deletebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        setVisibleLn(true);

        initValue();

        // 웹뷰 관련 ------------------------------------------------------------------------------------------
        clockinoutWebPageWebView = (WebView)findViewById(R.id.clockinoutWebPageWebView);

        //스크롤바 없애기
        clockinoutWebPageWebView.setVerticalScrollBarEnabled(false);
        clockinoutWebPageWebView.setHorizontalScrollBarEnabled(false);


        WebSettings set = clockinoutWebPageWebView.getSettings();
        // 웹뷰 속도개선
        clockinoutWebPageWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        set.setRenderPriority(WebSettings.RenderPriority.HIGH);
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);


        //자바스크립트 가능하게
        set.setJavaScriptEnabled(true);
        //웹뷰 멀티터치 가능하게
        set.setBuiltInZoomControls(true);
        set.setSupportZoom(true);
        //Local Storage, sessionStorage 유효화
        set.setDomStorageEnabled(true);
        //window.open 메서드 이용할 때의 동작 설정
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        //아래것 사용하면 팝업이 사용안됨
        //set.setSupportMultipleWindows(true);
        clockinoutWebPageWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메서드를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.웹뷰이름(여기서는 msalonAndroidClockinout 로 했음).setMessage("메시지 내용");
        clockinoutWebPageWebView.addJavascriptInterface(new AndroidBridge(), "qsrtclockinout");

        //alert 안될경우 아래를 넣어준다.
        final Context myApp = this;
        clockinoutWebPageWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog
                        .Builder(myApp)
                        .setTitle("AlertDialog")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });

        clockinoutWebPageWebView.loadUrl(
                GlobalMemberValues.CLOCKINOUT_WEB_URL5 +
                "schScode=" + GlobalMemberValues.SALON_CODE +
                "&schSidx=" + GlobalMemberValues.STORE_INDEX +
                "&schStcode=" + GlobalMemberValues.STATION_CODE +
                "&posopentype=native"
        );
        clockinoutWebPageWebView.setWebViewClient(new WebViewClientClass());
        mCurrentUrl = null;
        //----------------------------------------------------------------------------------------------------------------


        // 230426
        clock_in_out_top_nowtime = findViewById(R.id.clock_in_out_top_nowtime);
        clock_in_out_top_nowdate = findViewById(R.id.clock_in_out_top_nowdate);
        gettime();
    }

    public void gettime(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Date curDate;
                String strCurTime;
                String str_cur_date;
                SimpleDateFormat sdf;
                SimpleDateFormat sdf_date;
                sdf = new SimpleDateFormat("hh:mm:ss aa", Locale.US);
                sdf_date = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                curDate = new Date();
                strCurTime = sdf.format(curDate);
                str_cur_date = sdf_date.format(curDate);

                System.out.println(str_cur_date + " " +strCurTime);

                String finalNowTime = strCurTime;
                String finalNowDate = str_cur_date;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clock_in_out_top_nowdate.setText(finalNowDate);
                        clock_in_out_top_nowtime.setText(finalNowTime);
                    }
                });
            }
        },0,1, TimeUnit.SECONDS);
    }

    View.OnClickListener clockinoutWebPageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clockinoutWebPageCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.empTv : {
                    Intent employeeSelectionPopup = new Intent(context.getApplicationContext(), EmployeeSelectionPopup.class);
                    employeeSelectionPopup.putExtra("openfrom", "clockinout");
                    employeeSelectionPopup.putExtra("empidx", mSelectedEmpIdx);
                    mActivity.startActivity(employeeSelectionPopup);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.clockinbtn : {
                    LogsSave.saveLogsInDB(34);
                    setClockinout("in");

                    break;
                }
                case R.id.clockoutbtn : {
                    LogsSave.saveLogsInDB(35);
                    setClockinout("out");

                    break;
                }

                case R.id.breakinbtn : {
                    LogsSave.saveLogsInDB(36);
                    setClockinout("bin");

                    break;
                }
                case R.id.breakoutbtn : {
                    LogsSave.saveLogsInDB(37);
                    setClockinout("bout");

                    break;
                }

                case R.id.savebtn : {
                    setSaveDelete("edit");

                    break;
                }
                case R.id.deletebtn : {
                    if (! ClockInOutNavtiveWeb.this.isFinishing()) {
                        new AlertDialog.Builder(context)
                                .setTitle("Warning")
                                .setMessage("Are you sure to delete?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        setSaveDelete("delete");
                                    }
                                }).show();
                    }

                    break;
                }
                case R.id.indateTv : {
                    String tempValue = indateTv.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempValue)) {
                        tempValue = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = indateTv;
                    openDatePickerDialog(tempValue);

                    break;
                }
                case R.id.outdateTv : {
                    String tempValue = outdateTv.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempValue)) {
                        tempValue = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = outdateTv;
                    openDatePickerDialog(tempValue);

                    break;
                }

                case R.id.intimeTv : {
                    String tempValue = intimeTv.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempValue)) {
                        tempValue = GlobalMemberValues.STR_NOW_TIME;
                    }
                    mSelectedTimeTextView = intimeTv;
                    openTimePickerDialog(tempValue);

                    break;
                }
                case R.id.outtimeTv : {
                    String tempValue = outtimeTv.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempValue)) {
                        tempValue = GlobalMemberValues.STR_NOW_TIME;
                    }
                    mSelectedTimeTextView = outtimeTv;
                    openTimePickerDialog(tempValue);

                    break;
                }
            }
        }
    };

    public void setSaveDelete(String paramType) {
        String tempMemo = memoEv2.getText().toString();

        String inDay = indateTv.getText().toString();
        String inTime = intimeTv.getText().toString();
        String outDay = outdateTv.getText().toString();
        String outTime = outtimeTv.getText().toString();

        if (GlobalMemberValues.isStrEmpty(mGetClockInOutIdx)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Invalid Access", "Close");
            setVisibleLn(true);
            return;
        }

        if (GlobalMemberValues.isStrEmpty(inDay)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose the clock-in day", "Close");
            return;
        }

        if (GlobalMemberValues.isStrEmpty(inTime)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose the clock-in time", "Close");
            return;
        }

        String tempUrl = "";
        if (paramType.equals("edit")) {
            tempUrl = GlobalMemberValues.CLOCKINOUT_WEB_URL3 +
                    "schScode=" + GlobalMemberValues.SALON_CODE +
                    "&schSidx=" + GlobalMemberValues.STORE_INDEX +
                    "&schStcode=" + GlobalMemberValues.STATION_CODE +
                    "&schEmployeeIdx=" + mGetEmployeeIdx +
                    "&fromDate=" + mGetFromDate +
                    "&toDate=" + mGetToDate +
                    "&pageno=" + mGetPageNo +
                    "&clockinoutIdx=" + mGetClockInOutIdx +
                    "&clockinday=" + inDay +
                    "&clockinTime=" + inTime +
                    "&clockoutday=" + outDay +
                    "&clockoutTime=" + outTime +
                    "&memo=" + tempMemo +
                    "&posopentype=native";
        } else {
            tempUrl = GlobalMemberValues.CLOCKINOUT_WEB_URL4 +
                    "schScode=" + GlobalMemberValues.SALON_CODE +
                    "&schSidx=" + GlobalMemberValues.STORE_INDEX +
                    "&schStcode=" + GlobalMemberValues.STATION_CODE +
                    "&schEmployeeIdx=" + mGetEmployeeIdx +
                    "&fromDate=" + mGetFromDate +
                    "&toDate=" + mGetToDate +
                    "&pageno=" + mGetPageNo +
                    "&clockinoutIdx=" + mGetClockInOutIdx +
                    "&clockinday=" + inDay +
                    "&clockinTime=" + inTime +
                    "&clockoutday=" + outDay +
                    "&clockoutTime=" + outTime +
                    "&memo=" + tempMemo +
                    "&posopentype=native";
        }

        GlobalMemberValues.logWrite("clockinouturllog", "url : " + tempUrl + "\n");

        clockinoutWebPageWebView.loadUrl(tempUrl);
        setVisibleLn(true);

        initValue();
    }

    public void openTimePickerDialog(String paramTime) {
        String tempSplitStr[] = null;
        tempSplitStr = paramTime.split(":");
        int tempHour = Integer.parseInt(tempSplitStr[0]);
        int tempMinute = Integer.parseInt(tempSplitStr[1]);
        TimePickerDialog timeDialog = new TimePickerDialog(this, timeSelectListener, tempHour, tempMinute, false);
        timeDialog.show();
    }

    TimePickerDialog.OnTimeSetListener timeSelectListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tempHour = String.valueOf(hourOfDay);
            if ((hourOfDay) < 10) {
                tempHour = "0" + tempHour;
            }
            String tempMinute = String.valueOf(minute);
            if (minute < 10) {
                tempMinute = "0" + tempMinute;
            }
            mSelectedTimeTextView.setText(tempHour + ":" + tempMinute);
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
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    private void setClockinout(String paramType) {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline2().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                return;
            }
        }

        if (GlobalMemberValues.isStrEmpty(mSelectedEmpIdx)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Select employee", "Close");
            return;
        }
        String tempPwdEv = pwdEv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempPwdEv)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter password", "Close");
            return;
        }

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline2().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                return;
            }
        }

        String tempMemo = memoEv.getText().toString();

        String clockinoutDateYear = DateMethodClass.nowYearGet();
        String clockinoutDateMonth = DateMethodClass.nowMonthGet();
        String clockinoutDateDay = DateMethodClass.nowDayGet();
        String clockinoutDateHour = DateMethodClass.nowHourGet();
        String clockinoutDateMinute = DateMethodClass.nowMinuteGet();

        if (GlobalMemberValues.isStrEmpty(mClockinoutIdx)) {
            mClockinoutIdx = "";
        }

        String tempUrl = GlobalMemberValues.CLOCKINOUT_WEB_URL2 +
                "schScode=" + GlobalMemberValues.SALON_CODE +
                "&schSidx=" + GlobalMemberValues.STORE_INDEX +
                "&schStcode=" + GlobalMemberValues.STATION_CODE +
                "&clockinout=" + paramType +
                "&employeeIdx=" + mSelectedEmpIdx +
                "&empClockInOutPwd=" + tempPwdEv +
                "&manualclockout=N" +
                "&clockinoutDateYear=" + clockinoutDateYear +
                "&clockinoutDateMonth=" + clockinoutDateMonth +
                "&clockinoutDateDay=" + clockinoutDateDay +
                "&clockinoutDateHour=" + clockinoutDateHour +
                "&clockinoutDateMinute=" + clockinoutDateMinute +
                "&memo=" + tempMemo +
                "&clockinoutIdx=" + mClockinoutIdx +
                "&posopentype=native";

        GlobalMemberValues.logWrite("clockinouturllog", "url : " + tempUrl + "\n");

        clockinoutWebPageWebView.loadUrl(tempUrl);

        // 초기화
        initValue();
    }

    public static void setDataFromWeb(String paramData) {
        if (!GlobalMemberValues.isStrEmpty(paramData)) {
            String[] dataArr = paramData.split(GlobalMemberValues.STRSPLITTER1);
            mGetClockInOutIdx = dataArr[0];
            mGetClockInDay = dataArr[1];
            mGetClockInTime = dataArr[2];
            mGetClockOutDay = dataArr[3];
            mGetClockOutTime = dataArr[4];
            mGetMemo = dataArr[5];
            mGetEmployeeIdx = dataArr[6];
            mGetFromDate = dataArr[7];
            mGetToDate = dataArr[8];
            mGetPageNo = dataArr[9];

            indateTv.setText(mGetClockInDay);
            intimeTv.setText(mGetClockInTime);
            outdateTv.setText(mGetClockOutDay);
            outtimeTv.setText(mGetClockOutTime);
            memoEv.setText(mGetMemo);

            setVisibleLn(false);
        }
    }

    public static void setVisibleLn(boolean paramIsOpenInOutLn) {
        if (paramIsOpenInOutLn) {
            inoutLn.setVisibility(View.VISIBLE);
            editLn.setVisibility(View.GONE);
        } else {
            inoutLn.setVisibility(View.GONE);
            editLn.setVisibility(View.VISIBLE);
        }
    }

    public static void initValue() {
        // 초기화
        mSelectedEmpIdx = "";
        empTv.setText("");
        pwdEv.setText("");
        memoEv.setText("");
        indateTv.setText("");
        intimeTv.setText("");
        outdateTv.setText("");
        outtimeTv.setText("");

        mSelectedEmpIdx = "";

        mGetClockInOutIdx = "";
        mGetClockInDay = "";
        mGetClockInTime = "";
        mGetClockOutDay = "";
        mGetClockOutTime = "";
        mGetMemo = "";
        mGetEmployeeIdx = "";
        mGetFromDate = "";
        mGetToDate = "";
        mGetPageNo = "";
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(ClockInOutNavtiveWeb.this, msg, Toast.LENGTH_SHORT).show();
                    //GlobalMemberValues.displayDialog(context, "getdatafromweblog", msg, "Close");
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        setDataFromWeb(msg);
                    }
                }
            });
        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mCurrentUrl = url;
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);
            String urlString = clockinoutWebPageWebView.getUrl().toString();
            if (urlString.indexOf("step_viewReservation") == -1) {
                //getReservationButton.setVisibility(View.INVISIBLE);
            } else {
                //getReservationButton.setVisibility(View.VISIBLE);
            }
            //GlobalMemberValues.displayDialog(context, "Warninig", urlString, "Close");
            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String urlString = clockinoutWebPageWebView.getUrl().toString();
            if (urlString.indexOf("step_viewReservation") == -1) {
                //getReservationButton.setVisibility(View.INVISIBLE);
            } else {
                //getReservationButton.setVisibility(View.VISIBLE);
            }
            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
        }
    }

    @Override
    //Back 버튼 클릭시 뒤로가기 추가 뒤로가기 더이상 없으면 종료
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && clockinoutWebPageWebView.canGoBack()) {
            clockinoutWebPageWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    //종료처리시 종료할지 물어보기 추가
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you quit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    }
                }).show();
    }


    // 자동회전시 새로고침 방지 --------------------------------------------
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        clockinoutWebPageWebView.saveState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        clockinoutWebPageWebView.restoreState(savedInstanceState);
    }
    // -------------------------------------------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null){
            executorService.shutdown();
        }
    }
}
