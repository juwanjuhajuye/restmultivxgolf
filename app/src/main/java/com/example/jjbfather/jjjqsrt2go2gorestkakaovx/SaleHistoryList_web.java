package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class SaleHistoryList_web extends Activity {
    static final String TAG = "SaleHistoryList_web_log";

    static Activity mActivity;
    static Context context;
    Intent mIntent;
    LinearLayout parentLn;
    ListView listView;
    SaleHistoryListWebAdapter saleHistoryListAdapter;
    ImageButton btn_search, btn_close;
    TextView salehistory_list_StartDateEditText, salehistory_list_EndDateEditText, mSelectedDateTextView;
    TextView shititletv1, shititletv2, shititletv3, shititletv4;
    EditText shlSearchEv;

    RadioGroup deliverytakeawayGroup, searchOnlineTypeGroup;
    RadioButton deliveryRadioButton;
    RadioButton takeawayRadioButton;
    RadioButton hereRadioButton;
    RadioButton allRadioButton;

    RadioButton searchOnlineType_web, searchOnlineType_App, searchOnlineType_Kiosk, searchOnlineType_All;

    public String mSearchDeliveryTakeaway = "All";

    // -------------------------------------------------------
    ProgressDialog proDial;       // 프로그래스바
    // -------------------------------------------------------

    String mApiReturnCode = "";
    int mTempFlag = 0;

    // 주문내역을 저장할 ListView
    public static ArrayList<String> mCollectionOrdersArrList;

    public ArrayList<String> m_arr_string;

    // 12092022
    public static String mSearchOnlineType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_list_web);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        parentLn = (LinearLayout)findViewById(R.id.shlparentln);

        mIntent = getIntent();
        if (mIntent != null) {
        } else {
            finish();
        }

        setContents();
    }

    private void setContents(){
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // 12092022
        mSearchOnlineType = "";

        mCollectionOrdersArrList = new ArrayList<String>();
        mCollectionOrdersArrList.clear();

        salehistory_list_StartDateEditText = (TextView)findViewById(R.id.salehistory_list_StartDateEditText);
        salehistory_list_StartDateEditText.setOnClickListener(btn_clockListener);
        salehistory_list_EndDateEditText = (TextView)findViewById(R.id.salehistory_list_EndDateEditText);
        salehistory_list_EndDateEditText.setOnClickListener(btn_clockListener);

        salehistory_list_StartDateEditText.setTextSize(GlobalMemberValues.globalAddFontSize()
                + salehistory_list_StartDateEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        salehistory_list_EndDateEditText.setTextSize(GlobalMemberValues.globalAddFontSize()
                + salehistory_list_EndDateEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        shititletv1 = (TextView)findViewById(R.id.shititletv1);
        shititletv2 = (TextView)findViewById(R.id.shititletv2);
        shititletv3 = (TextView)findViewById(R.id.shititletv3);
        shititletv4 = (TextView)findViewById(R.id.shititletv4);

        shititletv1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + shititletv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        shititletv2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + shititletv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        shititletv3.setTextSize(GlobalMemberValues.globalAddFontSize()
                + shititletv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        shititletv4.setTextSize(GlobalMemberValues.globalAddFontSize()
                + shititletv4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());



        btn_search = (ImageButton)findViewById(R.id.salehistory_list_date_search);
        btn_search.setOnClickListener(btn_clockListener);
        btn_close = (ImageButton)findViewById(R.id.salehistory_list_close);
        btn_close.setOnClickListener(btn_clockListener);

        listView = (ListView)findViewById(R.id.salehistory_list);
        listView.setOnItemClickListener(listviewClick);

        String searchStartDate = salehistory_list_StartDateEditText.getText().toString();
        String searchEndDate = salehistory_list_EndDateEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(searchStartDate)) {
            searchStartDate = GlobalMemberValues.STR_NOW_DATE;
            salehistory_list_StartDateEditText.setText(searchStartDate);
        }
        if (GlobalMemberValues.isStrEmpty(searchEndDate)) {
            searchEndDate = GlobalMemberValues.STR_NOW_DATE;
            salehistory_list_EndDateEditText.setText(searchEndDate);
        }

        /** 라디오 버튼 객체 생성 및 리스너 연결 *********************************************************************/
        deliverytakeawayGroup = (RadioGroup)findViewById(R.id.deliverytakeawayGroup);
        deliverytakeawayGroup.setOnCheckedChangeListener(checkDeliveryTakeaway);

        deliveryRadioButton = (RadioButton)findViewById(R.id.deliveryRadioButton);
        deliveryRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + deliveryRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        takeawayRadioButton = (RadioButton)findViewById(R.id.takeawayRadioButton);
        takeawayRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + takeawayRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        hereRadioButton = (RadioButton)findViewById(R.id.hereRadioButton);
        hereRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + hereRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        allRadioButton = (RadioButton)findViewById(R.id.allRadioButton);
        allRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + allRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        searchOnlineTypeGroup = (RadioGroup)findViewById(R.id.searchOnlineTypeGroup);
        searchOnlineTypeGroup.setOnCheckedChangeListener(checkSearchOnlineTypeGroup);

        searchOnlineType_web = (RadioButton)findViewById(R.id.searchOnlineType_web);
        searchOnlineType_web.setTextSize(GlobalMemberValues.globalAddFontSize() + searchOnlineType_web.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        searchOnlineType_App = (RadioButton)findViewById(R.id.searchOnlineType_App);
        searchOnlineType_App.setTextSize(GlobalMemberValues.globalAddFontSize() + searchOnlineType_App.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        searchOnlineType_Kiosk = (RadioButton)findViewById(R.id.searchOnlineType_Kiosk);
        searchOnlineType_Kiosk.setTextSize(GlobalMemberValues.globalAddFontSize() + searchOnlineType_Kiosk.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        searchOnlineType_All = (RadioButton)findViewById(R.id.searchOnlineType_All);
        searchOnlineType_All.setTextSize(GlobalMemberValues.globalAddFontSize() + searchOnlineType_All.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        /***********************************************************************************************************/

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.sh_deliverytakeaway)) {
            GlobalMemberValues.sh_deliverytakeaway = "";
        }

        shlSearchEv = (EditText)findViewById(R.id.shlSearchEv);
        shlSearchEv.setOnTouchListener(evListener);
        shlSearchEv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        shlSearchEv.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        shlSearchEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        searchData();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        setSaleHistoryList();
    }

    public void setSaleHistoryList() {
        String tempStartDate = salehistory_list_StartDateEditText.getText().toString();
        String tempEndDate = salehistory_list_EndDateEditText.getText().toString();
        String tempSearchValue = shlSearchEv.getText().toString();

        // 프로그래스바 띄우기 -------------------------------------------------
        proDial = new ProgressDialog(mActivity);
        proDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDial.setTitle("Navyzebra QSR Cloud");
        proDial.setMessage("Online orders' data is loading...");
        proDial.setProgress(0);
        proDial.setMax(100);
        proDial.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDial.show();
        // -------------------------------------------------------------------

        if (mCollectionOrdersArrList != null) {
            mCollectionOrdersArrList.clear();
        }

        final String finalSearchStartDate = tempStartDate;
        final String finalSearchEndDate = tempEndDate;
        final String finalSearchCustomerText = tempSearchValue;
        final String finalSearchPickUpType = tempSearchValue;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                APIDownLoad_online_orders_list apiDownloadOnlineOrders
                        = new APIDownLoad_online_orders_list(finalSearchStartDate, finalSearchEndDate, finalSearchCustomerText, finalSearchPickUpType);
                apiDownloadOnlineOrders.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiDownloadOnlineOrders.mFlag) {
                        GlobalMemberValues.logWrite("weborderlistlog", "ReturnCode : " + apiDownloadOnlineOrders.mApiReturnCode + "\n");
                        mApiReturnCode = apiDownloadOnlineOrders.mApiReturnCode;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTempFlag = 0;

                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -------------------------------------------------
                handlerWebOrder.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }


    private Handler handlerWebOrder = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlag == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                m_arr_string = mCollectionOrdersArrList;
                callingSetSaleHistory();
                // -------------------------------------------------------------------------------------
                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
                proDial.dismiss();
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
                // -------------------------------------------------------------------------------------
            }
        }
    };


    private void callingSetSaleHistory() {
        if (mCollectionOrdersArrList.size() == 0) {
            if (mApiReturnCode.equals("11")) {
                setSaleHistory();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Network error or no data\nTouch [YES] button to try again")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setSaleHistoryList();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
        } else {
            setSaleHistory();
        }
    }

    public void setSaleHistory() {
        if (m_arr_string != null && m_arr_string.size() > 0) {
            saleHistoryListAdapter = new SaleHistoryListWebAdapter(m_arr_string);
            listView.setAdapter(null);      // 초기화
            listView.setAdapter(saleHistoryListAdapter);
            saleHistoryListAdapter.notifyDataSetChanged();
        }
    }

    // 짧게(일반적인) 한번 클릭할 때
    AdapterView.OnItemClickListener listviewClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            GlobalMemberValues.logWrite("arraySaleListLog", "여기왔음..." + "\n");

            if (m_arr_string != null && m_arr_string.size() > 0) {
                String tempSaledata = m_arr_string.get(position).toString();
                GlobalMemberValues.logWrite("arraySaleListLog", "marr : " + tempSaledata + "\n");

                String[] item_string = tempSaledata.split("-JJJ-");

                String tempReceiptNum = item_string[2];
                String temp_ordertype = item_string[11];

                // 10112023
                String temp_orderfrom = "";
                if (item_string.length > 14) {
                    temp_orderfrom = item_string[14];
                }
                String temp_salescodethirdparty = "";
                if (item_string.length > 15) {
                    temp_salescodethirdparty = item_string[15];
                }

                if (!GlobalMemberValues.isStrEmpty(tempReceiptNum)) {
                    Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistory_web.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    intent.putExtra("pushSalesCode", tempReceiptNum);
                    intent.putExtra("pushOrderType", temp_ordertype);

                    // 10112023
                    intent.putExtra("pushOrderfrom", temp_orderfrom);
                    intent.putExtra("pushSalescodethirdparty", temp_salescodethirdparty);

                    // 03102018
                    GlobalMemberValues.sh_fromCommand = "Y";
                    // -------------------------------------------------------------------------------------
                    startActivity(intent);
                }
            }
        }
    };

    View.OnClickListener btn_clockListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.salehistory_list_close: {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.salehistory_list_date_search: {
                    searchData();
                    break;
                }
                case R.id.salehistory_list_StartDateEditText: {
                    String tempClockInOutSearchDay = salehistory_list_StartDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = salehistory_list_StartDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
                case R.id.salehistory_list_EndDateEditText :{
                    String tempClockInOutSearchDay = salehistory_list_EndDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = salehistory_list_EndDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
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
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    RadioGroup.OnCheckedChangeListener checkDeliveryTakeaway = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.deliverytakeawayGroup : {
                    switch (checkedId) {
                        case R.id.deliveryRadioButton : {
                            mSearchDeliveryTakeaway = "D";
                            break;
                        }
                        case R.id.takeawayRadioButton : {
                            mSearchDeliveryTakeaway = "T";
                            break;
                        }
                        case R.id.hereRadioButton : {
                            mSearchDeliveryTakeaway = "H";
                            break;
                        }
                        case R.id.allRadioButton : {
                            mSearchDeliveryTakeaway = "All";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    RadioGroup.OnCheckedChangeListener checkSearchOnlineTypeGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.searchOnlineTypeGroup : {
                    switch (checkedId) {
                        case R.id.searchOnlineType_web : {
                            mSearchOnlineType = "W";
                            break;
                        }
                        case R.id.searchOnlineType_App : {
                            mSearchOnlineType = "A";
                            break;
                        }
                        case R.id.searchOnlineType_Kiosk: {
                            mSearchOnlineType = "K";
                            break;
                        }
                        case R.id.searchOnlineType_All : {
                            mSearchOnlineType = "";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    View.OnTouchListener evListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    private void searchData() {
        setSaleHistoryList();
    }

}
