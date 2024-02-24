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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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

public class CustomerMemo extends Activity {
    Activity mActivity;
    Context context;

    private LinearLayout parentLn;

    private TextView quantityTitleTextView;

    private EditText customerMemoEditText;

    private Button customerMemoVButton, customerMemoSaleHistoryButton;
    private Button customerMemoOtherCustomerButton, customerMemoDeleteButton, customerMemoCloseButton;
    private Button customerMemoPointButton;

    // 검색부분 관련객체들
    Spinner customerMemoSpinner;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    String selectedItemSpinner = "";

    // 메모 히스토리 리스트뷰
    private ListView customerMemoHistoryListView;
    // ListView 에 고객항목 붙일 때 필요한 객체들
    TemporaryCustomerMemoHistory mTempCustomerMemoHistoryList;
    public static ArrayList<TemporaryCustomerMemoHistory> mGeneralArrayList;
    public static CustomerMemoHistoryListAdapter mCustomerMemoHistoryListAdapter;

    static int selectedPosition = -1;

    View selectedListViewItemView = null;

    DatabaseInit dbInit;

    public ProgressDialog itemProDial;
    public int mTempFlagItemDown = 0;

    Intent mIntent;

    // 프렌차이즈 관련 -------------------------------
    LinearLayout offlineLn, onlineLn;
    WebView customerMemoWv;
    private final Handler handler = new Handler();
    ProgressDialog dialog;
    private String mCurrentUrl;
    // -----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_customer_memo);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 6;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 10;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.customerMemoLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            //mSelectedUid = mIntent.getStringExtra("SelectedUid");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void setLayoutContent() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        quantityTitleTextView = (TextView) findViewById(R.id.quantityTitleTextView);
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName)) {
            quantityTitleTextView.setText("\"" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName + "\" Memo");
        }

        customerMemoVButton = (Button)findViewById(R.id.customerMemoVButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerMemoVButton.setText("");
            customerMemoVButton.setBackgroundResource(R.drawable.ab_imagebutton_custmeno_enter);
        } else {
            customerMemoVButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerMemoVButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        customerMemoSaleHistoryButton = (Button)findViewById(R.id.customerMemoSaleHistoryButton);
        customerMemoSaleHistoryButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerMemoSaleHistoryButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerMemoOtherCustomerButton = (Button)findViewById(R.id.customerMemoOtherCustomerButton);
        customerMemoOtherCustomerButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerMemoOtherCustomerButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerMemoDeleteButton = (Button)findViewById(R.id.customerMemoDeleteButton);
        customerMemoDeleteButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerMemoDeleteButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerMemoPointButton = (Button)findViewById(R.id.customerMemoPointButton);
        customerMemoPointButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerMemoPointButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerMemoCloseButton = (Button)findViewById(R.id.customerMemoCloseButton);
        customerMemoCloseButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerMemoCloseButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        customerMemoCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_custmemo_close);

        customerMemoVButton.setOnClickListener(customerMemoButtonClickListener);
        customerMemoSaleHistoryButton.setOnClickListener(customerMemoButtonClickListener);
        customerMemoDeleteButton.setOnClickListener(customerMemoButtonClickListener);
        customerMemoCloseButton.setOnClickListener(customerMemoButtonClickListener);
        customerMemoOtherCustomerButton.setOnClickListener(customerMemoButtonClickListener);
        customerMemoPointButton.setOnClickListener(customerMemoButtonClickListener);

        // 메모리스트 보여지는 ListView 객체 생성
        customerMemoHistoryListView = (ListView)findViewById(R.id.customerMemoHistoryListView);
        customerMemoHistoryListView.setOnItemClickListener(memoListViewClickListener);

        // 메모선택항목 관련 -------------------------------------------------------------------------------------
        // 메모선택항목 Spinner 객체 생성 및 항목연결
        // 메모 Selection 구성
        dbInit = new DatabaseInit(context);
        String strQuery = "select description from salon_storememosel " +
                " order by description asc, idx desc ";
        Cursor memoSelCursor = dbInit.dbExecuteRead(strQuery);

        String[] memoSelectionArr = new String[21];
        memoSelectionArr[0] = "Type Memo";
        int memoSelCount = 1;
        while (memoSelCursor.moveToNext()) {
            memoSelectionArr[memoSelCount] =  GlobalMemberValues.getDBTextAfterChecked(memoSelCursor.getString(0), 1);
            memoSelCount++;
        }
        customerMemoSpinner = (Spinner)findViewById(R.id.customerMemoSpinner);
        //String[] strSearchItemList = {"Type Memo", "Good Customer", "Bad Customer", "New Comer", "Rich Customer", "VIP", "VVIP"};
        String[] strSearchItemList = memoSelectionArr;
        mGeneralArrayListForSpinner = new ArrayList<String>();
        int arrCount = 0;
        for (int i = 0; i < strSearchItemList.length; i++) {
            if (!GlobalMemberValues.isStrEmpty(strSearchItemList[i])) {
                mGeneralArrayListForSpinner.add(strSearchItemList[i]);
            }
            arrCount++;
        }
        mGeneralArrayListForSpinner.add(">>> Download memo selection <<<");
        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerMemoSpinner.setAdapter(mSpinnerAdapter);
        customerMemoSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);

        customerMemoEditText = (EditText)findViewById(R.id.customerMemoEditText);
        customerMemoEditText.setOnTouchListener(customerMemoEditTextTouchListener);
        customerMemoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        customerMemoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        customerMemoEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        insertCustMemo();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // ------------------------------------------------------------------------------------------------------

        // 메모 히스토리 리스팅
        setCustomerMemoHistoryListView(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), customerMemoEditText);

        // 프렌차이즈 관련
        offlineLn = (LinearLayout)findViewById(R.id.offlineLn);
        onlineLn = (LinearLayout)findViewById(R.id.onlineLn);

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            offlineLn.setVisibility(View.VISIBLE);

            setCustomerMemoForWeb();
        }
    }

    View.OnTouchListener customerMemoEditTextTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    AdapterView.OnItemClickListener memoListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectedPosition == position) {
                mCustomerMemoHistoryListAdapter.notifyDataSetChanged();
                selectedPosition = -1;

                view.setBackgroundColor(Color.parseColor("#fefff0"));

                selectedListViewItemView = null;
            } else {
                selectedPosition = position;

                if (selectedListViewItemView != null) {
                    selectedListViewItemView.setBackgroundColor(Color.parseColor("#fefff0"));
                }
                view.setBackgroundColor(Color.parseColor("#fbeafd"));

                selectedListViewItemView = view;
            }
            GlobalMemberValues.logWrite("CustomerMemoSelectPosition", "선택값 : " + selectedPosition + "\n");
        }
    };

    View.OnClickListener customerMemoButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerMemoVButton : {
                    insertCustMemo();
                    break;
                }
                case R.id.customerMemoSaleHistoryButton : {
                    GlobalMemberValues.logWrite("memuseridlog",
                            "GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid : " + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "\n");
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid)) {
                        Intent saleHistoryIntent = new Intent(context.getApplicationContext(), SaleHistoryList.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        saleHistoryIntent.putExtra("CustomerValue", GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid);
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(saleHistoryIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    }
                    break;
                }
                case R.id.customerMemoCloseButton : {
                    setMemoClearEditText();
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.customerMemoDeleteButton : {
                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Select memo to delete", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Memo Delete")
                            .setMessage("Do you want to delete this memo?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 리스트뷰의 항목을 삭제하는 메소드 호출
                                            deleteCustomerMemo();
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
                case R.id.customerMemoOtherCustomerButton : {
                    new AlertDialog.Builder(context)
                            .setTitle("Customer Selection")
                            .setMessage("Would you like to select another customer?" +
                                    "\nThe currently selected customer will be deselected")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 현재 선택 초기화 및 고객 선택창 오픈
                                            selectOtherCustomer();
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
                case R.id.customerMemoPointButton : {
                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid)) {
                        Intent customerPointEditIntent = new Intent(context.getApplicationContext(), CustomerPointEdit.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        customerPointEditIntent.putExtra("CustomerValue", GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid);
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(customerPointEditIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    }
                    break;
                }
            }
        }
    };

    public void selectOtherCustomer() {
        // 선택고객 초기화
        GlobalMemberValues.setCustomerInfoInit();
        // 고객선택창 오픈
        GlobalMemberValues.setFrameLayoutVisibleChange("customerSearch");
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(this);
        CustomerSearch custSearch = new CustomerSearch(this, this, dataAtSqlite);
        //custSearch.setCustomerInfo("", "", "");
        custSearch.viewCustomerSearch();
        // 현재 고객메모창 닫기
        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (((TextView)parent.getChildAt(0)) != null) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3e3d42"));
                ((TextView)parent.getChildAt(0)).setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
            }

            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite("CustomerMemo", "Selected Spinner Item : " + selectedItemSpinner + "\n");

            if (!selectedItemSpinner.equals("Type Memo")) {
                if (selectedItemSpinner.equals(">>> Download memo selection <<<")) {
                    downloadMemoSelection();
                } else {
                    customerMemoEditText.setText(selectedItemSpinner.toString());
                    GlobalMemberValues.setUseableOrNotEditText(customerMemoEditText, true);
                    customerMemoEditText.requestFocus();
                    // 메모 샘플 선택시 입력칸을 비활성화 시키려면 아래 부분을 주석해제할 것...
                    //GlobalMemberValues.setUseableOrNotEditText(customerMemoEditText, false);
                    //customerMemoEditText.setBackgroundResource(R.drawable.roundlayout_background_custmemo_disable);
                }
            } else {
                setMemoClearEditText();
            }

            // 키보드 안보이게 --------------------------------------------------------------------------------------
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(customerMemoEditText.getWindowToken(), 0);
            // ------------------------------------------------------------------------------------------------------
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    private void downloadMemoSelection() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
            CommandButton.backupDatabase(false);

            String[] tblNameArr = null;
            String tempTblMsg = "";

            tblNameArr = new String[]{
                    "salon_storememosel"
            };
            tempTblMsg = "Memo Selection";

            final String currentSyncButtonStr = "member";

            if (tblNameArr != null) {
                final String[] finalTblNameArr = tblNameArr;
                final String finalTempTblMsg = tempTblMsg;
                new AlertDialog.Builder(context)
                        .setTitle("Memo Selection Download")
                        .setMessage("Download " + tempTblMsg + " Data?")
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
                Intent customerMemoIntent = new Intent(context.getApplicationContext(), CustomerMemo.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                // -------------------------------------------------------------------------------------
                mActivity.startActivity(customerMemoIntent);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
                finish();
//                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                itemProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };


    private void insertCustMemo() {
        String tempMemo = customerMemoEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempMemo)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter Memo", "Close");
            return;
        }

        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO == null) {
            GlobalMemberValues.displayDialog(context, "Warning", "Select Customer", "Close");
            return;
        }

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Select Customer", "Close");
            return;
        }

        DatabaseInit dbInit = new DatabaseInit(context);

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String insStrQuery = " insert into member_memo (" +
                " uid, memo " +
                " ) values ( " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(tempMemo, 0) + "' " +
                " ) ";

        strInsertQueryVec.addElement(insStrQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("CustomerMemo", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else {
            // 정상적인 등록 완료이므로 메모 history list
            // GlobalMemberValues.displayDialog(context, "Information", "Successfully Registered", "Close");
            setMemoClearEditText();
            setCustomerMemoHistoryListView(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid);
        }
    }

    /** 메모 리스트 배치하기 ********************************************************/
    public void setCustomerMemoHistoryListView(String paramUid) {
        if (!GlobalMemberValues.isStrEmpty(paramUid)) {
            DatabaseInit dbInit = new DatabaseInit(context);
            String strQuery = "select idx, wdate, memo from member_memo " +
                    " where uid = '" + GlobalMemberValues.getDBTextAfterChecked(paramUid, 0) + "' " +
                    " order by wdate desc ";
            Cursor customerMemoCursor = dbInit.dbExecuteRead(strQuery);
            // ArrayList 객체 생성
            mGeneralArrayList = new ArrayList<TemporaryCustomerMemoHistory>();
            while (customerMemoCursor.moveToNext()) {
                String tempCustMemoNote = customerMemoCursor.getString(2);
                if (!GlobalMemberValues.isStrEmpty(tempCustMemoNote)) {
                    String paramsTempCustomerMemoHistoryListArray[] = {
                            GlobalMemberValues.getDBTextAfterChecked(customerMemoCursor.getString(0), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerMemoCursor.getString(1), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerMemoCursor.getString(2), 1),
                    };
                    mTempCustomerMemoHistoryList = new TemporaryCustomerMemoHistory(paramsTempCustomerMemoHistoryListArray);
                    mGeneralArrayList.add(mTempCustomerMemoHistoryList);
                }
            }
            mCustomerMemoHistoryListAdapter = new CustomerMemoHistoryListAdapter(context, R.layout.customermemo_history_list, mGeneralArrayList);
            customerMemoHistoryListView.setAdapter(mCustomerMemoHistoryListAdapter);

            // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
            //giftCardHistoryListView.setOnItemClickListener(mCustomerInfoItemClickListener);
        }
    }
    /*******************************************************************************/

    private void deleteCustomerMemo() {
        if (mGeneralArrayList.size() > 0 && selectedPosition > -1) {
            TemporaryCustomerMemoHistory tempSaleCartInstance = mGeneralArrayList.get(selectedPosition);
            if (selectedPosition > -1 && !GlobalMemberValues.isStrEmpty(tempSaleCartInstance.customerMemoIdx)) {
                DatabaseInit dbInit = new DatabaseInit(context);
                String tempSql = "delete from member_memo where idx = '" + tempSaleCartInstance.customerMemoIdx + "' ";
                String returnDbResult = dbInit.dbExecuteWriteReturnValue(tempSql);
                // DB 에서 삭제가 정상적으로 되었을 경우에만 (리턴값이 "0" 일경우)
                if (returnDbResult == "0" || returnDbResult.equals("0")) {
                    // 해당 Discount / Extra 아이템 삭제
                    mGeneralArrayList.remove(selectedPosition);
                    mCustomerMemoHistoryListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void setMemoClearEditText() {
        customerMemoSpinner.setSelection(0);
        customerMemoEditText.setText("");
        GlobalMemberValues.setUseableOrNotEditText(customerMemoEditText, true);
        customerMemoEditText.setBackgroundResource(R.drawable.roundlayout_background_custmemo);
        customerMemoEditText.requestFocus();
    }





    // 프렌차이즈 관련
    private void setCustomerMemoForWeb() {
        // 히스토리 리스트 보여지는 ListView 객체 생성 - WEB 용 -------------------------------------------------------------------------
        customerMemoWv = (WebView)findViewById(R.id.customerSearchWv);

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            onlineLn.setVisibility(View.VISIBLE);
        }

        //스크롤바 없애기
        customerMemoWv.setVerticalScrollBarEnabled(false);
        customerMemoWv.setHorizontalScrollBarEnabled(false);

        customerMemoWv.setWebViewClient(new WebViewClient());
        WebSettings set = customerMemoWv.getSettings();

        // 화면 꽉 차게
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        set.setLoadWithOverviewMode(true);
        set.setUseWideViewPort(true);
        customerMemoWv.setInitialScale(1);
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
        customerMemoWv.addJavascriptInterface(new AndroidBridge(), "customerdatatrans");
        customerMemoWv.setWebChromeClient(wcc);
        customerMemoWv.setWebViewClient(new WebViewClientClass());

        mCurrentUrl = null;

        String webviewUrl = GlobalMemberValues.CLOUD_SERVER_URL + "NZCCUSTOMER/";
        webviewUrl += "webview_memo.asp?";
        webviewUrl += "schScode=" + GlobalMemberValues.SALON_CODE;
        webviewUrl += "&schSidx=" + GlobalMemberValues.STORE_INDEX;
        webviewUrl += "&schStcode=" + GlobalMemberValues.STATION_CODE;
        webviewUrl += "&schEmployeeIdx=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
        webviewUrl += "&schEmployeeName=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
        webviewUrl += "&schUid=" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
        webviewUrl = GlobalMemberValues.getReplaceText(webviewUrl, " ", "|||");

        customerMemoWv.loadUrl(webviewUrl);
        GlobalMemberValues.logWrite("customerwebviewlo", "cloud url : " + webviewUrl + "\n");
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

            String urlString = customerMemoWv.getUrl().toString();
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




                        GlobalMemberValues.logWrite("customerwebviewlo", "msg : " + msg + "\n");
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("registrationComplete"));

        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
    }

}
