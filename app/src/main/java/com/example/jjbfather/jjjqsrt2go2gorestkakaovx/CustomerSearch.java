package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class CustomerSearch {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;
    Button closeBtn;

    // 검색부분이 있는 LinearLayout
    LinearLayout customerListLinearLayout1;
    // 고객리스트가 있는 LinearLayout
    LinearLayout customerListLinearLayout2;

    // 선택한 고객의 TemporaryCustomerInfo 객체
    TemporaryCustomerInfo selectedItemCustomerInfo;

    // ListView 에 고객항목 붙일 때 필요한 객체들
    TemporaryCustomerInfo mTempCustomerInfo;
    public static ArrayList<TemporaryCustomerInfo> mGeneralArrayList;
    public static CustomerInfoAdapter mCustomerInfoAdapter;

    // 고객정보 보여지는 리스트뷰
    ListView mCustomerInfoListView;

    // 검색부분 관련객체들
    Spinner mCustomerInfoSpinner;
    TextView customerTitleEditText;
    TextView customerSearchListViewItemUid, customerSearchListViewItemName;
    TextView customerSearchListViewItemPhone, customerSearchListViewItemAddr;

    Button customerListDonwloadButton;

    static EditText mCustomerInfoEditText;
    Button mCustomerInfoButton;
    Button mCustomerSearchAddButton;

    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    String selectedItemSpinner = "All";

    Cursor customerInfoCursor;

    public ProgressDialog memMileageCheckProDial;
    String mGetMemberMileage = "";

    // DB 객체 선언
    DatabaseInit dbInit;

    // 프렌차이즈 관련 -------------------------------
    LinearLayout offlineLn, onlineLn;
    WebView customerSearchWv;
    private final Handler handler = new Handler();
    ProgressDialog dialog;
    private String mCurrentUrl;
    // -----------------------------------------------

    public ProgressDialog itemProDial;
    public int mTempFlagItemDown = 0;

    public CustomerSearch(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = MainActivity.mContext;
        this.dataAtSqlite = dataAtSqlite;

        // 객체 생성과 함께 Employee 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setOnClickListener(customerBtnClickListener);

        // Customer List LinearLayout 객체 생성 (검색어 입력하는 부분)
        customerListLinearLayout1 = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("linearLayoutCustomerListTag1");

        // Customer List LinearLayout 객체 생성 (고객 리스트 나오는 부분)
        customerListLinearLayout2 = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("linearLayoutCustomerListTag2");

        // Customer Search 타이틀
        customerTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerTitleEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerTitleEditText.setText("");
            customerTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_customer_title);
        } else {
            customerTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // ListView 타이틀
        customerSearchListViewItemUid = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerSearchListViewItemUidTag");
        customerSearchListViewItemUid.setTextSize(GlobalMemberValues.globalAddFontSize() + 14 * GlobalMemberValues.getGlobalFontSize());
        customerSearchListViewItemName = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerSearchListViewItemNameTag");
        customerSearchListViewItemName.setTextSize(GlobalMemberValues.globalAddFontSize() + 14 * GlobalMemberValues.getGlobalFontSize());
        customerSearchListViewItemPhone = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerSearchListViewItemPhoneTag");
        customerSearchListViewItemPhone.setTextSize(GlobalMemberValues.globalAddFontSize() + 14 * GlobalMemberValues.getGlobalFontSize());
        customerSearchListViewItemAddr = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerSearchListViewItemAddrTag");
        customerSearchListViewItemAddr.setTextSize(GlobalMemberValues.globalAddFontSize() + 14 * GlobalMemberValues.getGlobalFontSize());

        customerListDonwloadButton = (Button) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerListDonwloadButtonTag");
        customerListDonwloadButton.setOnClickListener(customerBtnClickListener);
        //customerListDonwloadButton.setVisibility(View.GONE);

        // 고객리스트 보여지는 ListView 객체 생성
        mCustomerInfoListView = (ListView)customerListLinearLayout2.findViewWithTag("customerSearchListViewTag");

        // 검색부분 관련 ----------------------------------------------------------------------------------------
        // 검색항목 Spinner 객체 생성 및 항목연결
        mCustomerInfoSpinner = (Spinner)customerListLinearLayout1.findViewWithTag("customerSearchSpinnerTag");
        String[] strSearchItemList = {"All", "Phone#", "Name", "ID#", "Membership#"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < strSearchItemList.length; i++) {
            mGeneralArrayListForSpinner.add(strSearchItemList[i]);
        }
        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCustomerInfoSpinner.setAdapter(mSpinnerAdapter);
        mCustomerInfoSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);
        //mCustomerInfoSpinner.setSelection(0);
        switch (GlobalMemberValues.CUSTOMERSEARCHSPINNER) {
            case "All" : {
                mCustomerInfoSpinner.setSelection(0);
                break;
            }
            case "Phone#" : {
                mCustomerInfoSpinner.setSelection(1);
                break;
            }
            case "ID#" : {
                mCustomerInfoSpinner.setSelection(2);
                break;
            }
            case "Membership#" : {
                mCustomerInfoSpinner.setSelection(3);
                break;
            }
        }

        // 검색 EditText 객체 생성
        mCustomerInfoEditText = (EditText)customerListLinearLayout1.findViewWithTag("customerSearchEditTextTag");
        mCustomerInfoEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        mCustomerInfoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        mCustomerInfoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        mCustomerInfoEditText.setOnFocusChangeListener(editTextViewChangeFocus);

        mCustomerInfoEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        searchCustomerInfo();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        mCustomerInfoEditText.requestFocus();


        // 검색 Button 객체 생성
        mCustomerInfoButton = (Button)customerListLinearLayout1.findViewWithTag("customerSearchSHButtonTag");
        mCustomerInfoButton.setOnClickListener(customerBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            mCustomerInfoButton.setText("");
            mCustomerInfoButton.setBackgroundResource(R.drawable.ab_imagebutton_customer_search);
        } else {
            mCustomerInfoButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    mCustomerInfoButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 회원 추가 Button 객체 생성
        mCustomerSearchAddButton = (Button)customerListLinearLayout1.findViewWithTag("customerSearchAddButtonTag");
        mCustomerSearchAddButton.setOnClickListener(customerBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            mCustomerSearchAddButton.setText("");
            mCustomerSearchAddButton.setBackgroundResource(R.drawable.ab_imagebutton_customer_add);
        } else {
            mCustomerSearchAddButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    mCustomerSearchAddButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // ------------------------------------------------------------------------------------------------------

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH
                .findViewWithTag("customerSearchCloseBtnTag");
        closeBtn.setOnClickListener(customerBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // ArrayList 객체 생성


        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        GlobalMemberValues.setKeyPadHide(context, mCustomerInfoEditText);

        // 프렌차이즈 관련
        offlineLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.findViewWithTag("offlineLnTag");
        onlineLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.findViewWithTag("onlineLnTag");

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            offlineLn.setVisibility(View.VISIBLE);

            customerListDonwloadButton.setVisibility(View.GONE);
        }
    }

    EditText.OnFocusChangeListener editTextViewChangeFocus = new EditText.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.customerSearchEditText : {
                    if (selectedItemSpinner.equals("Membership#")) {
                        mCustomerInfoEditText.setInputType(InputType.TYPE_CLASS_NUMBER);          // 키패드(키보드) 타입을 숫자로

                        if (!hasFocus) {
                            //GlobalMemberValues.displayDialog(context, "","포커스 이동...", "Close");
                            String getMSRData = mCustomerInfoEditText.getText().toString();
                            if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                                getMSRData = GlobalMemberValues.getMSRCardNumber("employee", getMSRData);
                                //GlobalMemberValues.displayDialog(context, "","reading value : " + getMSRData, "Close");
                                mCustomerInfoEditText.setText(getMSRData);

                                searchCustomerInfo();
                            }
                        }
                    }
                    break;
                }
            }
        }
    };


    /** 고객명단 배치하기 ********************************************************/
    public void setCustomerInfo(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        // 고객정보 가져오기 (GetDataAtSQLite 클래스의 getCustomerInfo 메소드를 통해 가져온다)
        customerInfoCursor = dataAtSqlite.getCustomerInfo(paramSearchQuery, paramGroupbyQuery, paramOrderbyQuery);
        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryCustomerInfo>();
        int custCount = 0;
        if (customerInfoCursor.getCount() > 0) {
            while (customerInfoCursor.moveToNext()) {
                String tempCustName = customerInfoCursor.getString(2);
                // 고객 이름이 있을 경우에만
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempCustName, " ", ""))) {
                    // 마지막방문일시(구매일시)를 구한다.
                    String tempSql = "select strftime('%m-%d-%Y %H:%M:%S', lastvisitForSale) from member_salevisit " +
                            " where uid = '" + customerInfoCursor.getString(1) + "'" +
                            " order by idx desc limit 1 ";
                    String tempLastVisitForSale = dbInit.dbExecuteReadReturnString(tempSql);

                    //TemporaryCustomerInfo 객체 생성시 전달할 값을 String 배열로 만든다.
                    String paramsTempCustomerinfoArray[] = {
                            String.valueOf(customerInfoCursor.getInt(0)),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(1), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(2), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(3), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(4), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(5), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(6), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(7), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(8), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(9), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(10), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(11), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(12), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(13), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(14), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(15), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(16), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(17), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(18), 1),
                            GlobalMemberValues.getDBTextAfterChecked(tempLastVisitForSale, 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(20), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(21), 1)
                    };
                    mTempCustomerInfo = new TemporaryCustomerInfo(paramsTempCustomerinfoArray);
                    mGeneralArrayList.add(mTempCustomerInfo);
                    custCount++;
                }
            }
        }

        mCustomerInfoAdapter = new CustomerInfoAdapter(context, R.layout.customer_search_list, mGeneralArrayList);
        mCustomerInfoListView.setAdapter(mCustomerInfoAdapter);

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        mCustomerInfoListView.setOnItemLongClickListener(mCustomerInfoItemLongClickListener);
        mCustomerInfoListView.setOnItemClickListener(mCustomerInfoItemClickListener);

        // Membership# 으로 검색하고 검색결과가 하나일 때 조회된 고객을 바로 선택한다.
        if (selectedItemSpinner.equals("Membership#")) {
            if (custCount == 1) {
                selectedItemCustomerInfo = mGeneralArrayList.get(0);
                setCustomerInfoOnMain();
            }
        }
    }
    /*******************************************************************************/


    /** 리스트뷰의 항목을 길게 클릭할 때 발생하는 OnClickListenr ************************/
    AdapterView.OnItemLongClickListener mCustomerInfoItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            TemporaryCustomerInfo tempCustInfo = mGeneralArrayList.get(position);
            openEditCustomer(tempCustInfo.memUid);
            GlobalMemberValues.logWrite("LongClickCustomer1", "uid : " + tempCustInfo.memUid + "\n");
            return true;
        }
    };
    /*******************************************************************************/

    /** 리스트뷰의 항목을 클릭할 때 발생하는 OnClickListenr ****************************/
    AdapterView.OnItemClickListener mCustomerInfoItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedItemCustomerInfo = mGeneralArrayList.get(position);
            setCustomerInfoOnMain();
        }
    };
    /*******************************************************************************/


    public void openEditCustomer(String paramUid) {
        if (!GlobalMemberValues.isStrEmpty(paramUid)) {
            GlobalMemberValues.logWrite("LongClickCustomer2", "uid : " + paramUid + "\n");
            LogsSave.saveLogsInDB(230);
            Intent customerEditIntent = new Intent(context.getApplicationContext(), CustomerEdit.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            customerEditIntent.putExtra("CustId", paramUid);
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(customerEditIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void setCustomerInfoOnMain() {
        mGetMemberMileage = "";

        // 프로그래스 바를 실행~
        //memMileageCheckProDial = ProgressDialog.show(context, "Customer Cloud Check", "Customer's point is being downloaded from cloud", true, false);

        // 고객정보 변수할당
        GlobalMemberValues.setCustomerSelected(selectedItemCustomerInfo);
        // 검색종류 및 검색필드 값 초기화
        mGeneralArrayListForSpinner = null;
        mCustomerInfoEditText.setText("");
        GlobalMemberValues.setFrameLayoutVisibleChange("main");

        String tempMileagesyncinselectcustomer = "N";
        tempMileagesyncinselectcustomer = dbInit.dbExecuteReadReturnString(
                "select mileagesyncinselectcustomer from salon_storestationsettings_system");

        if (tempMileagesyncinselectcustomer.equals("Y") || tempMileagesyncinselectcustomer == "Y") {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            mGetMemberMileage = "";
                        } else {
                            API_download_membermileage apiMemMileageDownload = new API_download_membermileage(selectedItemCustomerInfo.memUid);
                            apiMemMileageDownload.execute(null, null, null);
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                if (apiMemMileageDownload.mFlag) {
                                    mGetMemberMileage = apiMemMileageDownload.mReturnValue;
                                }
                            } catch (InterruptedException e) {
                                mGetMemberMileage = "";
                            }
                        }
                    }
                    // ---------------------------------------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    memMileageDownloadHandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();
        }
    }

    private Handler memMileageDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetMemberMileage)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                // temp_salecart 테이블 수정
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String strUpdateQuery = "";

                strUpdateQuery = "update temp_salecart set " +
                        " customerId = '" + selectedItemCustomerInfo.memUid + "', " +
                        " customerName = '" + selectedItemCustomerInfo.memName + "', " +
                        " customerPhoneNo = '" + selectedItemCustomerInfo.memPhone + "' " +
                        " where holdcode = '" + MainMiddleService.mHoldCode + "' ";
                strUpdateQueryVec.addElement(strUpdateQuery);

                strUpdateQuery = "update member2 set " +
                        " mileage = '" + GlobalMemberValues.getDoubleAtString(mGetMemberMileage) + "' " +
                        " where uid = '" + selectedItemCustomerInfo.memUid + "' ";
                strUpdateQueryVec.addElement(strUpdateQuery);

                strUpdateQuery = "update salon_member set " +
                        " mileage = '" + GlobalMemberValues.getDoubleAtString(mGetMemberMileage) + "' " +
                        " where uid = '" + selectedItemCustomerInfo.memUid + "' ";
                strUpdateQueryVec.addElement(strUpdateQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("MemberMileageDownloadQuery", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                    return;
                } else {
                    selectedItemCustomerInfo.memMileage = mGetMemberMileage;
                    // 고객정보 변수할당
                    GlobalMemberValues.setCustomerSelected(selectedItemCustomerInfo);
                }
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                //memMileageCheckProDial.dismiss();
                // -------------------------------------------------------------------------------------

                Toast.makeText(context, "Customer points have been synced with the cloud", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mGeneralArrayListForSpinner != null) {
                selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            }
            //GlobalMemberValues.logWrite("CustomerInfo", "Selected Spinner Item : " + selectedItemSpinner + "\n");
            setCustomerInfo("", "", "");
            mCustomerInfoEditText.setText("");
            mCustomerInfoEditText.requestFocus();
            if (((TextView)parent.getChildAt(0)) != null) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3e3d42"));
                ((TextView)parent.getChildAt(0)).setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
            }

            // 키보드 안보이게 --------------------------------------------------------------------------------------
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mCustomerInfoEditText.getWindowToken(), 0);
            // ------------------------------------------------------------------------------------------------------

            if (selectedItemSpinner.equals("Membership#")) {
                mCustomerInfoEditText.setInputType(InputType.TYPE_CLASS_NUMBER);          // 키패드(키보드) 타입을 숫자로
            } else {
                mCustomerInfoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
            }

            GlobalMemberValues.CUSTOMERSEARCHSPINNER = selectedItemSpinner;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    View.OnClickListener customerBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerListDonwloadButton : {
                    customerListDownload();
                    break;
                }
                case R.id.mainTopCustButton : {
                    LogsSave.saveLogsInDB(81);
                    // 프렌차이즈 관련
                    viewCustomerSearch();
                    break;
                }
                case R.id.customerSearchCloseBtn : {
                    LogsSave.saveLogsInDB(223);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mCustomerInfoEditText);
                    GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    mCustomerInfoEditText.setText("");
                    //mCustomerInfoSpinner.setSelection(0);
                    break;
                }
                case R.id.customerSearchSHButton : {
                    LogsSave.saveLogsInDB(224);
                    searchCustomerInfo();

                    break;
                }
                case R.id.customerSearchAddButton : {
                    LogsSave.saveLogsInDB(225);
                    //GlobalMemberValues.displayDialog(context, "Customer Add", "Under Construction...", "Close");
                    addCustomer();
                    break;
                }
            }
        }
    };

    // 프렌차이즈 관련
    public void viewCustomerSearch() {
        // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
        // 07252024 (edit)
        // if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
        if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y" && !GlobalMemberValues.isPaymentByBills) return;
        // ------------------------------------------------------------
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.getText().toString().equals("Walk In")
                || GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.getText().toString() == "Walk In") {
            GlobalMemberValues.setFrameLayoutVisibleChange("customerSearch");

            // 프렌차이즈 관련
            if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                setCustomerInfoForWeb();
            } else {
                setCustomerInfo("", "", "");
            }
        } else {
            Intent customerMemoIntent = new Intent(context.getApplicationContext(), CustomerMemo.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(customerMemoIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void addCustomer() {
        Intent customerAddIntent = new Intent(context.getApplicationContext(), CustomerAdd.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(customerAddIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public void searchCustomerInfo() {
        // 키보드 안보이게 --------------------------------------------------------------------------------------
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCustomerInfoEditText.getWindowToken(), 0);
        // ------------------------------------------------------------------------------------------------------
        String schSearchText = GlobalMemberValues.getDBTextAfterChecked(mCustomerInfoEditText.getText().toString(), 0);

        String schSearchStr = "";
        if (selectedItemSpinner.equals("Phone#")) {
            schSearchStr = " (c.memmobile like '%" + schSearchText + "%') ";
        } else if (selectedItemSpinner.equals("Name")) {
            schSearchStr = " a.name like '%" + schSearchText + "%' ";
        } else if (selectedItemSpinner.equals("ID#")) {
            schSearchStr = " a.uid like '%" + schSearchText + "%' ";
        } else if (selectedItemSpinner.equals("Membership#")) {
            schSearchStr = " c.memcardnum like '%" + schSearchText + "%' ";
        } else if (selectedItemSpinner.equals("All")) {
            schSearchStr = " ( a.uid like '%" + schSearchText + "%' " +
                    " or a.name like '%" + schSearchText + "%' " +
                    " or c.memcardnum like '%" + schSearchText + "%' " +
                    " or c.memmobile like '%" + schSearchText + "%' ) ";
        } else {
            //
        }
        setCustomerInfo(schSearchStr, "", "");
        GlobalMemberValues.logWrite("CustomerInfo", "ADD SQL : " + schSearchStr + "\n");
    }

    public void customerListDownload() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
            CommandButton.backupDatabase(false);

            String[] tblNameArr = null;
            String tempTblMsg = "";

            tblNameArr = new String[]{
                    "member1", "member2",
                    "salon_member", "salon_storememosel"
            };
            tempTblMsg = "member";

            final String currentSyncButtonStr = "member";

            if (tblNameArr != null) {
                final String[] finalTblNameArr = tblNameArr;
                final String finalTempTblMsg = tempTblMsg.toUpperCase();
                new AlertDialog.Builder(context)
                        .setTitle("Data Download")
                        .setMessage("Download " + tempTblMsg + " data?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 프로그래스 바를 실행~
                                        itemProDial = ProgressDialog.show(
                                                context, finalTempTblMsg + " DATA DOWNLOAD", finalTempTblMsg + " Data is Downloading...", true, false
                                        );
                                        Thread thread = new Thread(new Runnable() {
                                            public void run() {
                                                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                                // DB 생성 및 처리 관련 메소드
                                                // setDatabaseAndApiDataDownload(int paramStatus, int actionType)
                                                // paramStatus       --- 0 : 메소드 실행             1 : 실행안함
                                                // actionType        --- 0 : DB 테이블 먼저 삭제     1 : 삭제안함
                                                dbInit.insertDownloadDataInDatabase(finalTblNameArr, currentSyncButtonStr, 1);
                                                // ---------------------------------------------------------------------------------
                                                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                                itemdownhandler.sendEmptyMessage(0);
                                                // ---------------------------------------------------------------------------------
                                            }
                                        });
                                        thread.start();
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
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
        }
    }

    private Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagItemDown == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                //setCustomerInfo("", "", "");
                GlobalMemberValues.openCustomerSearch();
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                itemProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };

    // 프렌차이즈 관련
    private void setCustomerInfoForWeb() {
        // 히스토리 리스트 보여지는 ListView 객체 생성 - WEB 용 -------------------------------------------------------------------------
        customerSearchWv = (WebView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.findViewWithTag("customerSearchWvTag");

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            onlineLn.setVisibility(View.VISIBLE);
        }

        //스크롤바 없애기
        customerSearchWv.setVerticalScrollBarEnabled(false);
        customerSearchWv.setHorizontalScrollBarEnabled(false);

        customerSearchWv.setWebViewClient(new WebViewClient());
        WebSettings set = customerSearchWv.getSettings();

        // 화면 꽉 차게
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        set.setLoadWithOverviewMode(true);
        set.setUseWideViewPort(true);
        customerSearchWv.setInitialScale(1);
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

        //API Level 16 부터 이용 가능
        try {
            set.setAllowUniversalAccessFromFileURLs(true);
        } catch(Exception e) {
            //아무것도 하지 않는다.
        }

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메소두를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.thisWebViewName.setMessage("메시지 내용");
        customerSearchWv.addJavascriptInterface(new AndroidBridge(), "customerdatatrans");
        customerSearchWv.setWebChromeClient(wcc);
        customerSearchWv.setWebViewClient(new WebViewClientClass());

        mCurrentUrl = null;

        String webviewUrl = GlobalMemberValues.CLOUD_SERVER_URL + "NZCCUSTOMER/";
        webviewUrl += "webview_customer_search.asp?";
        webviewUrl += "schScode=" + GlobalMemberValues.SALON_CODE;
        webviewUrl += "&schSidx=" + GlobalMemberValues.STORE_INDEX;
        webviewUrl += "&schHeight=" + GlobalMemberValues.getDisplayHeiheight(context);

        customerSearchWv.loadUrl(webviewUrl);
        GlobalMemberValues.logWrite("customerwebviewlo", "cloud url : " + webviewUrl + "\n");
        GlobalMemberValues.logWrite("customerwebviewlo", "height : " + GlobalMemberValues.getDisplayHeiheight(context) + "\n");
        // -------------------------------------------------------------------------------------------------------------------------------
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
            }
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);

            GlobalMemberValues.logWrite("webcloudurl", "url : " + url + "\n");

            if (!mActivity.isFinishing()) {
                dialog = new JJJCustomAnimationDialog(mActivity);
                dialog.show();
            }
        }

        // 페이지 로딩시
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(mActivity, "Web Error : " + description, Toast.LENGTH_SHORT).show();
            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }

            String urlString = customerSearchWv.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {

            } else {

            }
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
    }


    WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog
                    .Builder(context)
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

        @Override
        public boolean onJsConfirm(WebView view, String url,String message, final JsResult result) {
            new AlertDialog.Builder(context)
                    .setTitle("ConfirmDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();

            return true;
        }

    };

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(GiftCardBalanceCheck.this, msg, Toast.LENGTH_SHORT).show();
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        if (!GlobalMemberValues.isStrEmpty(msg)) {
                            String[] memArr = msg.split(GlobalMemberValues.STRSPLITTER2);
                            if (memArr.length > 0) {
                                selectedItemCustomerInfo = new TemporaryCustomerInfo(memArr);
                                GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText(
                                        "Point : " + GlobalMemberValues.getCommaStringForDouble(selectedItemCustomerInfo.memMileage + "")
                                );
                                setCustomerInfoOnMain();
                            }
                        }
                        GlobalMemberValues.logWrite("customerwebviewlo", "msg : " + msg + "\n");
                    }
                }
            });
        }
    }

}
