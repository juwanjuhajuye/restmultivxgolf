package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleHistoryList extends Activity {
    static Activity mActivity;
    Context context;
    Intent mIntent;
    LinearLayout parentLn;
    static ListView listView;
    SaleHistoryListAdapter saleHistoryListAdapter;
    ImageButton btn_search, btn_close;
    Button btn_loglist;
    TextView salehistory_list_StartDateEditText, salehistory_list_EndDateEditText, mSelectedDateTextView;
    TextView shititletv1, shititletv2, shititletv3, shititletv4, shititletv_tablenum, shititletv5, shititletv6, shititletv7, shititletv8, shititletv9, shititletv10, shititletv11;
    TextView salehistory_list_row_alltotal;
    EditText shlSearchEv;

    ArrayList<String> m_arr_string;

    private RadioGroup deliverytakeawayGroup;
    private RadioButton deliveryRadioButton;
    private RadioButton takeawayRadioButton;
    private RadioButton hereRadioButton;
    private RadioButton allRadioButton;

    private static LinearLayout listPageLn;

    public String mSearchDeliveryTakeaway = "";

    public static int mListPageno = 1;

    public String mGetSearchWord = "";

    public static String temp_start_date = "";
    public static String temp_end_date = "";

    private SaleHistoryListReceiver receiver;

    public class SaleHistoryListReceiver extends BroadcastReceiver {
        public static final String ACTION = "com.example.jjbfather.jjjqsrt2go2gorest.SaleHistoryList";
        @Override
        public void onReceive(Context context, Intent intent) {
            String callback = intent.getStringExtra("TipAddPopup");
            int list_row_number = intent.getIntExtra("list_row_number", 0);
            if (callback.equals("close")){
                searchData();
                listView.setSelection(list_row_number);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_list);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        parentLn = (LinearLayout)findViewById(R.id.shlparentln);

        mIntent = getIntent();
        if (mIntent != null) {
            mGetSearchWord = mIntent.getStringExtra("CustomerValue");
            GlobalMemberValues.logWrite("salehistorycustomerlog", "넘겨받은 mGetSearchWord : " + mGetSearchWord + "\n");
        } else {
            finish();
        }
        receiver = new SaleHistoryListReceiver();
        this.registerReceiver(receiver, new IntentFilter(SaleHistoryListReceiver.ACTION));

        // wanhaye 08232023 ---------------------------------------------------------------------------
        // 세일 리스트 오픈전에 내부 DB 와 mssql 을 비교해서
        // mssql 에 없는 세일 데이터를 insert 한다.

        //setContents();

        Thread thread = new Thread(new Runnable() {
            public void run() {

                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                InsertSaleDataInServer insDB = new InsertSaleDataInServer();
                insDB.insDataInServer();
                // ---------------------------------------------------------------------------------
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
        // wanhaye 08232023 ---------------------------------------------------------------------------
    }


    // wanhaye 08232023 ------------------------------
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            setContents();
        }
    };
    // wanhaye 08232023 ------------------------------



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

        int temppagenum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select salehistorypageno from salon_storestationsettings_system "
                ));
        if (temppagenum == 0) {
            temppagenum = 30;
        }
        GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY = temppagenum;

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
        shititletv_tablenum = (TextView)findViewById(R.id.shititletv_tablenum);
        shititletv5 = (TextView)findViewById(R.id.shititletv5);
        shititletv6 = (TextView)findViewById(R.id.shititletv6);
        shititletv7 = (TextView)findViewById(R.id.shititletv7);
        shititletv8 = (TextView)findViewById(R.id.shititletv8);
        shititletv9 = (TextView)findViewById(R.id.shititletv9);
        shititletv10 = (TextView)findViewById(R.id.shititletv10);
        shititletv11 = (TextView)findViewById(R.id.shititletv11);
        salehistory_list_row_alltotal = (TextView)findViewById(R.id.salehistory_list_row_alltotal);

        if (GlobalMemberValues.isDeviceSunmiFromDB() || GlobalMemberValues.isDevicePAXFromDB()) {
            int fontsize = 14;
            shititletv1.setTextSize(fontsize);
            shititletv2.setTextSize(fontsize);
            shititletv3.setTextSize(fontsize);
            shititletv4.setTextSize(fontsize);
            shititletv_tablenum.setTextSize(fontsize);
            shititletv5.setTextSize(fontsize);
            shititletv6.setTextSize(fontsize);
            shititletv7.setTextSize(fontsize);
            shititletv8.setTextSize(fontsize);
            shititletv9.setTextSize(fontsize);
            shititletv10.setTextSize(fontsize);
            shititletv11.setTextSize(fontsize);
            salehistory_list_row_alltotal.setTextSize(fontsize);
        }

        if (GlobalMemberValues.mDeviceTabletPC){
            int fontsize = 12;
            shititletv1.setTextSize(fontsize);
            shititletv2.setTextSize(fontsize);
            shititletv3.setTextSize(fontsize);
            shititletv4.setTextSize(fontsize);
            shititletv_tablenum.setTextSize(fontsize);
            shititletv5.setTextSize(fontsize);
            shititletv6.setTextSize(fontsize);
            shititletv7.setTextSize(fontsize);
            shititletv8.setTextSize(fontsize);
            shititletv9.setTextSize(fontsize);
            shititletv10.setTextSize(fontsize);
            shititletv11.setTextSize(fontsize);
            salehistory_list_row_alltotal.setTextSize(fontsize);
        }


        btn_search = (ImageButton)findViewById(R.id.salehistory_list_date_search);
        btn_search.setOnClickListener(btn_clockListener);
        btn_close = (ImageButton)findViewById(R.id.salehistory_list_close);
        btn_close.setOnClickListener(btn_clockListener);

        listView = (ListView)findViewById(R.id.salehistory_list);
        listView.setOnItemClickListener(lvClick);

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
        /***********************************************************************************************************/

        // 페이징 LinearLayount
        listPageLn = (LinearLayout)findViewById(R.id.listPageLn);
        listPageLn.removeAllViews();

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

        mListPageno = 1;

        if (!GlobalMemberValues.isStrEmpty(mGetSearchWord)) {
            shlSearchEv.setText(mGetSearchWord);
            searchStartDate = "01-01-2010";
            searchEndDate = "12-31-2030";

            salehistory_list_StartDateEditText.setText(searchStartDate);
            salehistory_list_EndDateEditText.setText(searchEndDate);
        }

        if (temp_start_date != GlobalMemberValues.STR_NOW_DATE && temp_start_date != ""){
            searchStartDate = temp_start_date;
            salehistory_list_StartDateEditText.setText(searchStartDate);
        }
        if (temp_end_date != GlobalMemberValues.STR_NOW_DATE && temp_end_date != ""){
            searchEndDate = temp_end_date;
            salehistory_list_EndDateEditText.setText(searchEndDate);
        }

        setSaleHistoryList(searchStartDate, searchEndDate, mListPageno);

        btn_loglist = findViewById(R.id.salehistory_list_loglist);
        btn_loglist.setOnClickListener(btn_clockListener);
    }

    public ArrayList<String> getSaleHistoryList(String paramStartDate, String paramEndDate, String paramPickUpType, String paramSearchValue, int paramPage) {

        String mOrderNum, mPickupType, mReceiptNum, mTotalAmount;
        String tempPickupType;
        double tempTotalAmount = 0.0;
        String mDataL = "";
        String tempSaleDate;
        String tempCustomername;
        String tempStcode;
        String tempCheckcompany;

        String pickupSql = "";
        String searchKeywordSql = "";

        if (!GlobalMemberValues.isStrEmpty(paramPickUpType)) {
            pickupSql = " and deliverytakeaway = '" + paramPickUpType + "' ";
        }

        if (!GlobalMemberValues.isStrEmpty(paramSearchValue)) {
            searchKeywordSql = " and ( " +
                    " customerId like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or customerName like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or customerPhone like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or customerPosCode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or customerPhone like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or salesCode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or customerordernumber like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or salescode in (select salescode from salon_sales_card where cardLastFourDigitNumbers like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%') " +
                    " ) ";
        }

        ArrayList<String> mArrList = new ArrayList<String>();
        String strQuery = "select customerordernumber, deliverytakeaway, salesCode, totalAmount, saledate, customername, stcode, checkcompany, useTotalCashAmount, " +
                " useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, togotype from salon_sales " +
                " where delyn = 'N' " +
                " and saleDate between '" + DateMethodClass.getCustomEditDate(paramStartDate, 0, 0, 0) + "' " +
                " and '" + DateMethodClass.getCustomEditDate(paramEndDate, 0, 0, 1) + "' " + pickupSql + searchKeywordSql +
                " order by idx desc ";
        GlobalMemberValues.logWrite("salehistoryquerylog", "strQuery : " + strQuery + "\n");


//        Cursor salecursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        ResultSet salecursor = MssqlDatabase.getResultSetValue(strQuery);
        // 페이징 관련 -----------------------------------------------------------------------------------------------
        listPageLn.removeAllViews();

        // 현재 선택한 페이지 저장
        if (GlobalMemberValues.isStrEmpty(paramPage + "")) {
            paramPage = 1;
        }
        mListPageno = paramPage;

        boolean isPageuse = false;

//        int datasu = salecursor.getCount();
        int datasu = MssqlDatabase.getCount(salecursor);

        if (datasu > 0 && datasu > GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY) {
            int pagesize = datasu / GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY;
            int pagesize2 = datasu % GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY;

            GlobalMemberValues.logWrite("pagingtestlog", "pagesize : " + pagesize + "\n");
            GlobalMemberValues.logWrite("pagingtestlog", "pagesize2 : " + pagesize2 + "\n");

            if (pagesize2 > 0) {
                pagesize++;
            }

            LinearLayout.LayoutParams pageLayoutParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pageLayoutParams.setMargins(0, 0, 15, 0);

            while (true){
                try {
                    if (!salecursor.next()) break;
                    TextView pageTv = new TextView(context);
                    pageTv.setLayoutParams(pageLayoutParams);
                    pageTv.setGravity(Gravity.CENTER);
                    pageTv.setPadding(15, 5, 15, 5);
                    pageTv.setText(1 + "");
                    if (GlobalMemberValues.isEloPro()) {
                        pageTv.setTextSize(20);
                    } else {
                        pageTv.setTextSize(16);
                    }

                    if (1 == mListPageno) {
                        pageTv.setTextColor(Color.parseColor("#ffffff"));
                        pageTv.setBackgroundResource(R.drawable.button_selector_page2);
                    } else {
                        pageTv.setTextColor(Color.parseColor("#000000"));
                        pageTv.setBackgroundResource(R.drawable.button_selector_page1);
                    }

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

                    String finalSearchStartDate = searchStartDate;
                    String finalSearchEndDate = searchEndDate;
                    pageTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSaleHistoryList(finalSearchStartDate, finalSearchEndDate, 1);
                        }
                    });

                    listPageLn.addView(pageTv);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

//            for (int i = 0; i < pagesize; i++) {
//                final int pageno = i + 1;
//
//                TextView pageTv = new TextView(context);
//                pageTv.setLayoutParams(pageLayoutParams);
//                pageTv.setGravity(Gravity.CENTER);
//                pageTv.setPadding(15, 5, 15, 5);
//                pageTv.setText(pageno + "");
//                if (GlobalMemberValues.isEloPro()) {
//                    pageTv.setTextSize(20);
//                } else {
//                    pageTv.setTextSize(16);
//                }
//
//                if (pageno == mListPageno) {
//                    pageTv.setTextColor(Color.parseColor("#ffffff"));
//                    pageTv.setBackgroundResource(R.drawable.button_selector_page2);
//                } else {
//                    pageTv.setTextColor(Color.parseColor("#000000"));
//                    pageTv.setBackgroundResource(R.drawable.button_selector_page1);
//                }
//
//                String searchStartDate = salehistory_list_StartDateEditText.getText().toString();
//                String searchEndDate = salehistory_list_EndDateEditText.getText().toString();
//                if (GlobalMemberValues.isStrEmpty(searchStartDate)) {
//                    searchStartDate = GlobalMemberValues.STR_NOW_DATE;
//                    salehistory_list_StartDateEditText.setText(searchStartDate);
//                }
//                if (GlobalMemberValues.isStrEmpty(searchEndDate)) {
//                    searchEndDate = GlobalMemberValues.STR_NOW_DATE;
//                    salehistory_list_EndDateEditText.setText(searchEndDate);
//                }
//
//                String finalSearchStartDate = searchStartDate;
//                String finalSearchEndDate = searchEndDate;
//                pageTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setSaleHistoryList(finalSearchStartDate, finalSearchEndDate, pageno);
//                    }
//                });
//
//                listPageLn.addView(pageTv);
//            }

            // 커서위치 지정
//            if (datasu > 0) {
//                int nowCursorPoint = 1;
//                nowCursorPoint = (mListPageno - 1) * GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY;
//                salecursor.moveToPosition(nowCursorPoint - 1);
//            }

            isPageuse = true;
        }

        // -----------------------------------------------------------------------------------------------

        int dataCount = 0;
        while (true) {
            try {
                if (!salecursor.next()) break;
                mOrderNum = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(1), 1);

                tempPickupType = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(2), 1);
                if (GlobalMemberValues.isStrEmpty(tempPickupType)) {
                    tempPickupType = "H";
                }

                mPickupType = "HERE";
                switch (tempPickupType) {
                    case "H" : {
                        mPickupType = "HERE";
                        break;
                    }
                    case "T" : {
                        mPickupType = "TO GO";
                        break;
                    }
                    case "D" : {
                        mPickupType = "DELIVERY";
                        break;
                    }
                }

                mReceiptNum = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(3), 1);

                tempTotalAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(4), 1));
                mTotalAmount = GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + "");

                tempSaleDate = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(5), 1);
                tempCustomername = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(6), 1);
                tempStcode = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(7), 1);
                tempCheckcompany = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(8), 1);

                String payment_kind = "";
                String useTotalCashAmount = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(9), 1);
                double d_useTotalCashAmount = GlobalMemberValues.getDoubleAtString(useTotalCashAmount);
                if (d_useTotalCashAmount > 0){
                    if (!payment_kind.isEmpty()) payment_kind = payment_kind + ",";
                    payment_kind = payment_kind + "CASH";
                }
                String useTotalCardAmount = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(10), 1);
                double d_useTotalCardAmount = GlobalMemberValues.getDoubleAtString(useTotalCardAmount);
                String cardLastFourDigitNumbers = "";
                String cardCom = "";
                String cardRefNumber = "";
                String cardTid = "";
                if (d_useTotalCardAmount > 0){
                    if (!payment_kind.isEmpty()) payment_kind = payment_kind + ",";

                    cardCom = MssqlDatabase.getResultSetValueToString(
                            " select cardCom from salon_sales_card where salesCode = '" + mReceiptNum + "' "
                    );
                    if (cardCom.equals("OFFLINECARD")){
                        payment_kind = payment_kind + "OFF - CARD";
                    } else {
                        payment_kind = payment_kind + "CARD-" + cardCom;
                    }

                    cardLastFourDigitNumbers = MssqlDatabase.getResultSetValueToString(
                            " select cardLastFourDigitNumbers from salon_sales_card where salesCode = '" + mReceiptNum + "' "
                    );

                    cardRefNumber = MssqlDatabase.getResultSetValueToString(
                            " select cardRefNumber from salon_sales_card where salesCode = '" + mReceiptNum + "' "
                    );
                    cardTid = MssqlDatabase.getResultSetValueToString(
                            " select tid from salon_sales_card where salesCode = '" + mReceiptNum + "' "
                    );

                    if (!cardTid.equals("") ){
                        cardLastFourDigitNumbers = cardLastFourDigitNumbers + " / " + cardTid;
                    }

                }
                String useTotalGiftCardAmount = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(11), 1);
                double d_useTotalGiftCardAmount = GlobalMemberValues.getDoubleAtString(useTotalGiftCardAmount);
                if (d_useTotalGiftCardAmount > 0){
                    if (!payment_kind.isEmpty()) payment_kind = payment_kind + ",";
                    payment_kind = payment_kind + "GIFT CARD";
                }
                String useTotalCheckAmount = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(12), 1);
                double d_useTotalCheckAmount = GlobalMemberValues.getDoubleAtString(useTotalCheckAmount);
                if (d_useTotalCheckAmount > 0){
                    if (!payment_kind.isEmpty()) payment_kind = payment_kind + ",";
                    payment_kind = payment_kind + tempCheckcompany;
                }
                String useTotalPointAmount = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(13), 1);
                double d_useTotalPointAmount = GlobalMemberValues.getDoubleAtString(useTotalPointAmount);
                if (d_useTotalPointAmount > 0){
                    if (!payment_kind.isEmpty()) payment_kind = payment_kind + ",";
                    payment_kind = payment_kind + "POINT";
                }

                if (payment_kind.isEmpty()){
                    payment_kind = " ";
                }

                String tempTogoType = GlobalMemberValues.getDBTextAfterChecked(salecursor.getString(14), 1);

                if (!tempStcode.isEmpty()){
                    tempStcode = tempStcode.substring(0,4);
                }
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempCustomername, " ", ""))) {
                    if (!GlobalMemberValues.isStrEmpty(tempTogoType)) {
                        if (tempTogoType.equals("C")) {
                            tempCustomername = "CALL IN";
                        } else {
                            tempCustomername = "WALK IN";
                        }
                    } else {
                        tempCustomername = "WALK IN";
                    }
                }



                mDataL = mOrderNum + "-JJJ-" + mPickupType + "-JJJ-" + mReceiptNum + "-JJJ-" + mTotalAmount + "-JJJ-" + tempSaleDate + "-JJJ-" + tempCustomername + "-JJJ-" + tempStcode + "-JJJ-" + tempCheckcompany + "-JJJ-" + cardLastFourDigitNumbers +"-JJJ-" + payment_kind;
                mArrList.add(mDataL);

                dataCount++;
                if (isPageuse) {
                    if (dataCount >= GlobalMemberValues.PAGEDATACOUNNT_FORSALEHISTORY) {
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        //07052024 close resultset
        try {
            if(!salecursor.isClosed()){
                salecursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return mArrList;
    }

    private void setSaleHistoryList(String paramStartDate, String paramEndDate, int paramPage) {
        temp_start_date = paramStartDate;
        temp_end_date = paramEndDate;
        String tempSearchValue = shlSearchEv.getText().toString();

        m_arr_string = getSaleHistoryList(paramStartDate,paramEndDate, mSearchDeliveryTakeaway, tempSearchValue, paramPage);
//        ArrayList<String> arr_string = new ArrayList<String>();

        saleHistoryListAdapter = new SaleHistoryListAdapter(m_arr_string);
        listView.setAdapter(null);      // 초기화
        listView.setAdapter(saleHistoryListAdapter);
    }

    // 짧게(일반적인) 한번 클릭할 때
    AdapterView.OnItemClickListener lvClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            GlobalMemberValues.logWrite("arraySaleListLog", "position.size : " + position + "\n");
//            GlobalMemberValues.logWrite("arraySaleListLog", "m_arr_string.size : " + m_arr_string.size() + "\n");
            if (m_arr_string.size() > 0) {
                String tempSaledata = m_arr_string.get(position).toString();
               // GlobalMemberValues.logWrite("arraySaleListLog", "marr : " + tempSaledata + "\n");

                String[] item_string = tempSaledata.split("-JJJ-");

                String tempReceiptNum = item_string[2];

                if (!GlobalMemberValues.isStrEmpty(tempReceiptNum)) {
                    Intent saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistory.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    saleHistoryIntent.putExtra("receiptnum", tempReceiptNum);
                    // 03102018
                    GlobalMemberValues.sh_fromCommand = "Y";
                    // -------------------------------------------------------------------------------------
                    startActivity(saleHistoryIntent);
                    //overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
            }
        }
    };

    View.OnClickListener btn_clickListener_logSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (m_arr_string.size() > 0) {
                String tempSaledata = m_arr_string.get((Integer) v.getTag()).toString();
                // GlobalMemberValues.logWrite("arraySaleListLog", "marr : " + tempSaledata + "\n");

                String[] item_string = tempSaledata.split("-JJJ-");

                String tempReceiptNum = item_string[2];
                Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), LogHistory.class);
                intent.putExtra("salescode",tempReceiptNum);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                // -------------------------------------------------------------------------------------
                context.startActivity(intent);
            }

        }
    };




    View.OnClickListener btn_clockListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.salehistory_list_close: {
                    temp_start_date = "";
                    temp_end_date = "";
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
                case R.id.salehistory_list_loglist :
                    Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), LogHistory.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    // -------------------------------------------------------------------------------------
                    startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
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
                            mSearchDeliveryTakeaway = "";
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
        listPageLn.removeAllViews();

        String tempStartDate = salehistory_list_StartDateEditText.getText().toString();
        String tempEndDate = salehistory_list_EndDateEditText.getText().toString();

        setSaleHistoryList(tempStartDate, tempEndDate, 1);
    }

//    public static void moveList(int position){
//        listView.setSelection(position);
//    }



}
