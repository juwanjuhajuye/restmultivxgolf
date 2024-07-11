package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.JJJ_SignPad;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.LogsSave;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.PaymentCreditCard;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.VoidCardList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class BillSplitMerge extends Activity {
    private LinearLayout parentLn;

    public static Activity mActivity;
    public static Context mContext;

    Intent mIntent;
    String mTableIdx = "";
    String mSubTableNum = "1";

    public LinearLayout billsplit_main, tablesale_splitmerge_receiptview;

    public Button btn_tablesale_byfoodmenu, btn_tablesale_byequalamount, btn_tablesale_bycustomamount;
    public LinearLayout tablesale_byfoodmenu_view, tablesale_byequalamount_view,tablesale_bycustomamount_view;

    public Button btn_close;
    public ImageButton btn_merge_selected, btn_reset_selected, btn_reset_all;
    public ImageButton btn_tablesale_splitmerge_splitselected, btn_tablesale_splitmerge_splitevenly, btn_tablesale_splitmerge_splitbyamount;
    public ImageButton btn_tablesale_evenly_plus, btn_tablesale_evenly_minus;

    // 04212023
    public ImageButton btn_tablesale_evenly_plus2, btn_tablesale_evenly_minus2;

    public ListView tablesale_byfoodmenu_list;
    public GridView grid_billList;
    public ScrollView tablesale_left_scrollview;
    public TextView tablesale_split_evenly_txt;

    // 04212023
    public TextView tablesale_split_evenly_txt2;

    public static TextView tablesale_split_byamount;
    public TextView tablesale_balance_txt, tablesale_subtotal_txt, count_of_guests;
    public ImageButton tablesale_splitmerge_selected_printind, tablesale_splitmerge_pay_by_bills;
    public ImageButton tablesale_splitmerge_void;

    public String[] selected_table_info = null;
    public static ArrayList<String[]> str_list_selected_table_info;
    public JSONObject selected_jsonObject = null;
    public String str_selectTableInfos;



    public ArrayList<String> arrayList_list = null;

    public String str_holdCode = "";
    public static double d_TotalAmount = 0.00;

    public static String str_Billsplit_Type = "0";

    public static ArrayList<String> mSelectedBillBoxValue;

    BillSplitGridAdapter billSplitGridAdapter;

    public ProgressDialog itemProDial;


    // 04212023
    public static int mBillSplitCount = 0;

    // 05042023
    int mChangeStatus = 0;

    // 05162023
    boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상단 Status Bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게

        setContentView(R.layout.activity_billsplitmerge);

        TableSaleMain.mSelectedIdxArrListInCart = new ArrayList<String>();
        TableSaleMain.mSelectedIdxArrListInCart.clear();

        TableSaleMain.mBillSplitMerge_menulist_array = new JSONArray();

        billsplit_main = findViewById(R.id.billsplit_main);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mTableIdx = mIntent.getStringExtra("selectedTableIdx");
            mSubTableNum = mIntent.getStringExtra("selectedSubTableNum");

            if (GlobalMemberValues.isStrEmpty(mSubTableNum)) {
                mSubTableNum = "1";
            }

            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mTableIdx : " + mTableIdx + "\n");
            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mSubTableNum : " + mSubTableNum + "\n");

            String teststr = "ABCDEFG";
            GlobalMemberValues.logWrite("logtexttestjjj", "값 : " + teststr.substring(0, (teststr.length() -2)) + "\n");
            GlobalMemberValues.logWrite("logtexttestjjj", "값 : " + teststr.substring((teststr.length() -2)) + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

            if (GlobalMemberValues.isStrEmpty(mTableIdx)) {
                finish();
            }
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        mActivity = this;
        mContext = this;

        // 프로그래스 바를 실행~
//        itemProDial = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, "Bill split page is loading...", true, false);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                setContents();
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                startingHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler startingHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            setContents2();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (tablesale_splitmerge_pay_by_bills != null) {
                        tablesale_splitmerge_pay_by_bills.setVisibility(View.VISIBLE);

                        // 09082023
                        setPayBtnVisible();
                    }
                }
            }, 2500);// 0.6초 정도 딜레이를 준 후 시작


//            if (itemProDial != null) {
//                itemProDial.dismiss();
//            }
            // -------------------------------------------------------------------------------------
        }
    };

    // 09082023
    public void setPayBtnVisible() {
        if (str_Billsplit_Type != "0") {
            tablesale_splitmerge_pay_by_bills.setVisibility(View.VISIBLE);
            return;
        }

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "select idx from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                    " order by svcName desc ";
        } else {
            strQuery = "select idx from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                    " order by svcName desc ";
        }

        GlobalMemberValues.logWrite("paybtncheckjjjlog", "strQuery : " + strQuery + "\n");

        String itemIdx = "";

        ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
        int billnotCnt = 0;
        try{
            while (cartCurosr.next()) {
                itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);

                if (!BillExecute.isInBillList_byCartIdx(itemIdx)) {
                    GlobalMemberValues.logWrite("paybtncheckjjjlog", itemIdx + " : 없음" + "\n");
                    billnotCnt++;
                }
            }
            cartCurosr.close();
        }catch (Exception e){
        }

        if (billnotCnt > 0) {
            tablesale_splitmerge_pay_by_bills.setVisibility(View.GONE);
        } else {
            tablesale_splitmerge_pay_by_bills.setVisibility(View.VISIBLE);
        }
    }

    public void setContents() {
        // 05042023
        mChangeStatus = 0;

        GlobalMemberValues.mIsClickPayment = false;

        setInitValuesForBillPay();

        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            TableSaleMain.setClearSelectedTableIdx(true);
        }

        mSelectedBillBoxValue = new ArrayList<String>();
        str_list_selected_table_info = new ArrayList<String[]>();

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. ------------------------------------------------------------------
        billsplit_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardHide();
            }
        });
        // ---------------------------------------------------------------------------------------------------------------

        tablesale_left_scrollview = findViewById(R.id.tablesale_left_scrollview);

        tablesale_splitmerge_receiptview = findViewById(R.id.tablesale_splitmerge_receiptview);

        btn_close = findViewById(R.id.tablesale_splitmerge_close);
        btn_close.setOnClickListener(split_onClickListener);

        btn_merge_selected = findViewById(R.id.tablesale_splitmerge_merge_selected);
        btn_reset_selected = findViewById(R.id.tablesale_splitmerge_reset_selected);
        btn_reset_all = findViewById(R.id.tablesale_splitmerge_reset_all);

        btn_merge_selected.setOnClickListener(split_onClickListener);
        btn_reset_selected.setOnClickListener(split_onClickListener);
        btn_reset_all.setOnClickListener(split_onClickListener);

//        exList_foodmenulist = findViewById(R.id.tablesale_splitmerge_list);
        grid_billList = findViewById(R.id.tablesale_splitmerge_grid);
        tablesale_byfoodmenu_list = findViewById(R.id.tablesale_byfoodmenu_list);
//        billSplitItemAdapter = new BillSplitItemAdapter(m_arr_string);


        btn_tablesale_byfoodmenu = findViewById(R.id.btn_tablesale_byfoodmenu);
        btn_tablesale_byfoodmenu.setOnClickListener(menuTypeClickListener);
        btn_tablesale_byequalamount = findViewById(R.id.btn_tablesale_byequalamount);
        btn_tablesale_byequalamount.setOnClickListener(menuTypeClickListener);
        btn_tablesale_bycustomamount = findViewById(R.id.btn_tablesale_bycustomamount);
        btn_tablesale_bycustomamount.setOnClickListener(menuTypeClickListener);

        tablesale_byfoodmenu_view = findViewById(R.id.tablesale_byfoodmenu_view);
        tablesale_byfoodmenu_view.setVisibility(View.GONE);
        tablesale_byequalamount_view = findViewById(R.id.tablesale_byequalamount_view);
        tablesale_byequalamount_view.setVisibility(View.GONE);
        tablesale_bycustomamount_view = findViewById(R.id.tablesale_bycustomamount_view);
        tablesale_bycustomamount_view.setVisibility(View.GONE);

        btn_tablesale_splitmerge_splitselected = findViewById(R.id.tablesale_splitmerge_splitselected);
        btn_tablesale_splitmerge_splitselected.setOnClickListener(split_onClickListener);
        btn_tablesale_splitmerge_splitevenly = findViewById(R.id.tablesale_splitmerge_splitevenly);
        btn_tablesale_splitmerge_splitevenly.setOnClickListener(split_onClickListener);
        btn_tablesale_splitmerge_splitbyamount = findViewById(R.id.tablesale_splitmerge_splitbyamount);
        btn_tablesale_splitmerge_splitbyamount.setOnClickListener(split_onClickListener);

        tablesale_splitmerge_selected_printind = findViewById(R.id.tablesale_splitmerge_selected_printind);
        tablesale_splitmerge_selected_printind.setOnClickListener(split_onClickListener);
        tablesale_splitmerge_pay_by_bills = findViewById(R.id.tablesale_splitmerge_pay_by_bills);
        tablesale_splitmerge_pay_by_bills.setOnClickListener(split_onClickListener);

        tablesale_splitmerge_pay_by_bills.setVisibility(View.INVISIBLE);

        tablesale_splitmerge_void = findViewById(R.id.tablesale_splitmerge_void);
        tablesale_splitmerge_void.setOnClickListener(split_onClickListener);

        if (GlobalMemberValues.isPaymentPossible()){
            //tablesale_splitmerge_pay_by_bills.setVisibility(View.VISIBLE);
        } else {
            tablesale_splitmerge_pay_by_bills.setVisibility(View.INVISIBLE);
            tablesale_splitmerge_void.setVisibility(View.INVISIBLE);
        }

        btn_tablesale_byfoodmenu.setSelected(false);
        btn_tablesale_byequalamount.setSelected(false);
        btn_tablesale_bycustomamount.setSelected(false);
//        grid_billList.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                tablesale_left_scrollview.requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

        btn_tablesale_evenly_plus = findViewById(R.id.btn_tablesale_evenly_plus);
        btn_tablesale_evenly_plus.setOnClickListener(evenlyQty_listener);
        btn_tablesale_evenly_minus = findViewById(R.id.btn_tablesale_evenly_minus);
        btn_tablesale_evenly_minus.setOnClickListener(evenlyQty_listener);
        tablesale_split_evenly_txt = findViewById(R.id.tablesale_split_evenly_txt);
        tablesale_split_evenly_txt.setText("1");


        // 04212023
        btn_tablesale_evenly_plus2 = findViewById(R.id.btn_tablesale_evenly_plus2);
        btn_tablesale_evenly_plus2.setOnClickListener(evenlyQty_listener2);
        btn_tablesale_evenly_minus2 = findViewById(R.id.btn_tablesale_evenly_minus2);
        btn_tablesale_evenly_minus2.setOnClickListener(evenlyQty_listener2);
        tablesale_split_evenly_txt2 = findViewById(R.id.tablesale_split_evenly_txt2);



        tablesale_split_byamount = findViewById(R.id.tablesale_split_byamount);
        tablesale_split_byamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempValue = tablesale_split_byamount.getText().toString();
                if (GlobalMemberValues.getDoubleAtString(tempValue) == 0) {
                    tablesale_split_byamount.setText(GlobalMemberValues.getDoubleAtString(tablesale_balance_txt.getText().toString()) + "");
                }

                Intent billkpd = new Intent(mContext.getApplicationContext(), Bill_KeyPad.class);
                String nowamt = tablesale_split_byamount.getText().toString();
                billkpd.putExtra("nowamt", nowamt);
                mActivity.startActivity(billkpd);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                }
            }
        });


        // balance 계산
        tablesale_balance_txt = findViewById(R.id.tablesale_balance_txt);
        tablesale_subtotal_txt = findViewById(R.id.tablesale_subtotal_txt);

        String tempCustCnt = MssqlDatabase.getResultSetValueToString(
                " select peoplecnt from salon_store_restaurant_table_peoplecnt " +
                        " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(mTableIdx, mSubTableNum) + "' "
        );
        count_of_guests = findViewById(R.id.count_of_guests);
        count_of_guests.setText(tempCustCnt);
    }

    public void setContents2() {
        setCartListView();


        // 05162023
        isFirst = true;
        renew_gridview(gettHoldcode(""),mTableIdx,"0");

        grid_billList.setOnItemClickListener(grid_onItemClickListener);

        d_TotalAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + str_holdCode + "' "));


        setBalanceSubtotal_byCustomAmount();

        // 230817 이전 split 테이블이 있으면 갯수 카운팅해서 표시.
        if (arrayList_list == null && arrayList_list.isEmpty()) {
            tablesale_split_evenly_txt2.setText("1");
        } else {
            String str_arrayList_list_count = String.valueOf(arrayList_list.size());
            tablesale_split_evenly_txt2.setText(str_arrayList_list_count);
        }
        // 230817 이전 split 테이블이 있으면 갯수 카운팅해서 표시.

        // 해당 holdcode 로 저장된 bill_list 가 있는지 확인한다.
        String getBillSplitType = MssqlDatabase.getResultSetValueToString(
                " select billsplittype from bill_list where holdcode = '" + gettHoldcode("") + "' "
        );
        if (GlobalMemberValues.isStrEmpty(getBillSplitType)) {
            getBillSplitType = "0";
        }

        // 05162023
        isFirst = false;
        setRadioButton(getBillSplitType);
    }

    public void setBalanceSubtotal_byCustomAmount() {
        double totalBillAmount = GlobalMemberValues.getDoubleAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select sum(billamount) from bill_list where holdcode = '" + str_holdCode + "' "
                )
        );

        d_TotalAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + str_holdCode + "' "));

        double totalBalance = d_TotalAmount - totalBillAmount;

        tablesale_balance_txt.setText("$ " + GlobalMemberValues.getCommaStringForDouble(totalBalance + ""));
        tablesale_subtotal_txt.setText("$ " + GlobalMemberValues.getCommaStringForDouble(totalBillAmount + ""));
    }

    private void keyboardHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(billsplit_main.getWindowToken(), 0);
    }

    public void setEvenlyCountTextView() {
        int billCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(*) from bill_list where holdcode = '" + gettHoldcode("") + "' "
        ));

        if (billCnt == 0) {
            billCnt = 2;
        }
        tablesale_split_evenly_txt.setText(billCnt + "");
    }

    public void setRadioButton(String paramFlag) {
        switch (paramFlag) {
            case "0" : {
                btn_tablesale_byfoodmenu.setSelected(true);
                btn_tablesale_byequalamount.setSelected(false);
                btn_tablesale_bycustomamount.setSelected(false);

                tablesale_byfoodmenu_view.setVisibility(View.VISIBLE);
                tablesale_byequalamount_view.setVisibility(View.GONE);
                tablesale_bycustomamount_view.setVisibility(View.GONE);
                str_Billsplit_Type = "0";

                setCartListView();

                renew_gridview(str_holdCode,mTableIdx,paramFlag);

                break;
            }
            case "1" : {
                btn_tablesale_byfoodmenu.setSelected(false);
                btn_tablesale_byequalamount.setSelected(true);
                btn_tablesale_bycustomamount.setSelected(false);

                tablesale_byfoodmenu_view.setVisibility(View.GONE);
                tablesale_byequalamount_view.setVisibility(View.VISIBLE);
                tablesale_bycustomamount_view.setVisibility(View.GONE);
                str_Billsplit_Type = "1";

                setEvenlyCountTextView();

                renew_gridview(str_holdCode,mTableIdx,paramFlag);

                break;
            }
            case "2" : {
                btn_tablesale_byfoodmenu.setSelected(false);
                btn_tablesale_byequalamount.setSelected(false);
                btn_tablesale_bycustomamount.setSelected(true);

                tablesale_byfoodmenu_view.setVisibility(View.GONE);
                tablesale_byequalamount_view.setVisibility(View.GONE);
                tablesale_bycustomamount_view.setVisibility(View.VISIBLE);
                str_Billsplit_Type = "2";

                setBalanceSubtotal_byCustomAmount();

                renew_gridview(str_holdCode,mTableIdx,paramFlag);

                break;
            }
        }
    }

    public boolean getSelectBillSplitType(Context paramContext, String paramHoldCode, String paramBillSplitType) {
        final boolean[] returnValue = {false};

        // 해당 holdcode 로 결제된 내역이 있는지 확인한다
        int billPaidCount = getCountInBillPaid(paramHoldCode);

        if (billPaidCount > 0) {
            GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                    "There is a bill that has been paid and cannot be changed - (1)", "Close");
            returnValue[0] = false;
        } else {
            // 해당 holdcode 로 저장된 bill_list 가 있는지 확인한다.
            String getBillSplitType = MssqlDatabase.getResultSetValueToString(
                    " select billsplittype from bill_list where holdcode = '" + paramHoldCode + "' "
            );
            if (!GlobalMemberValues.isStrEmpty(getBillSplitType) && !getBillSplitType.equals(paramBillSplitType)) {
                new AlertDialog.Builder(paramContext)
                        .setTitle("Warning")
                        .setMessage("There are already split checks\nExisting split checks are deleted")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        BillExecute.deleteBillSplit(paramHoldCode);
                                        setRadioButton(paramBillSplitType);
                                        mSelectedBillBoxValue.clear();
                                        str_list_selected_table_info.clear();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                returnValue[0] = false;
                            }
                        })
                        .show();
            } else {
                setRadioButton(paramBillSplitType);
                mSelectedBillBoxValue.clear();
                str_list_selected_table_info.clear();
                returnValue[0] = true;
            }
        }

        return returnValue[0];
    }

    View.OnClickListener menuTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_tablesale_byfoodmenu : {
                    if (getSelectBillSplitType(BillSplitMerge.this, str_holdCode,"0")){
                        setRadioButton("0");

                        // 09082023
                        setPayBtnVisible();
                    }
                    break;
                }
                case R.id.btn_tablesale_byequalamount : {
                    if (getSelectBillSplitType(BillSplitMerge.this,str_holdCode,"1")){
                        setRadioButton("1");

                        // 09082023
                        setPayBtnVisible();
                    }
                    break;
                }
                case R.id.btn_tablesale_bycustomamount : {
                    if (getSelectBillSplitType(BillSplitMerge.this,str_holdCode,"2")){
                        setRadioButton("2");

                        // 09082023
                        setPayBtnVisible();
                    }
                    break;
                }
            }
        }
    };

    public int getCountInBillPaid(String paramHoldCode) {
        int billPaidCount = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select count(*) from bill_list_paid where holdcode = '" + paramHoldCode + "' "
                )
        );

        return billPaidCount;
    }

    View.OnClickListener split_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tablesale_splitmerge_splitselected : {
                    if (TableSaleMain.mSelectedIdxArrListInCart == null) return;

                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) > 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "There is a bill that has been paid and cannot be changed - (2)", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(252);
                    String cartidxs = "";
                    double d_total = 0.00;
                    for (String temp : TableSaleMain.mSelectedIdxArrListInCart) {
                        String[] tempCartItemInfo = temp.split("-JJJ-");
                        if (!GlobalMemberValues.isStrEmpty(tempCartItemInfo[0])) {
                            if (cartidxs.isEmpty()){
                                cartidxs = tempCartItemInfo[0];
                            } else {
                                cartidxs = cartidxs + "," + tempCartItemInfo[0];
                            }
                        }
                        if (!GlobalMemberValues.isStrEmpty(tempCartItemInfo[1])) {
                            if (d_total == 0.00){
                                d_total = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCartItemInfo[1],"2"));
                            } else {
                                d_total = d_total + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCartItemInfo[1],"2"));
                            }
                        }
                    }
                    // bill split 시 균등분배하면 아이템이 안나오는것 추적 0515
                    if (BillExecute.doInsertByMenu_Bill(str_holdCode,mTableIdx,d_total,cartidxs).equals("Y")){
                        renew_gridview(str_holdCode,mTableIdx,"0");

                        setCartListView();
                    }


                    // 09082023
                    setPayBtnVisible();

                    break;
                }
                case R.id.tablesale_splitmerge_splitevenly : {
                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) > 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "There is a bill that has been paid and cannot be changed - (3)", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(253);
                    double d_total = 0.00;
                    for (String temp : getSaleHistoryList()){
                        String[] tempCartItemInfo = temp.split("-JJJ-");
                        if (!GlobalMemberValues.isStrEmpty(tempCartItemInfo[2])) {
                            if (d_total == 0.00){
                                d_total = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCartItemInfo[2],"2"));
                            } else {
                                d_total = d_total + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempCartItemInfo[2],"2"));
                            }
                        }
                    }
                    if (BillExecute.doInsertByEvenly_Bill(str_holdCode,mTableIdx,d_total,Integer.parseInt((String)tablesale_split_evenly_txt.getText())).equals("Y")){
                        renew_gridview(str_holdCode,mTableIdx,"1");
                    }
                    break;
                }
                case R.id.tablesale_splitmerge_splitbyamount : {
                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) > 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "There is a bill that has been paid and cannot be changed - (4)", "Close");
                        return;
                    }

                    double nowBalance = GlobalMemberValues.getDoubleAtString(tablesale_balance_txt.getText().toString());
                    double insValue = GlobalMemberValues.getDoubleAtString(tablesale_split_byamount.getText().toString());

                    if (insValue > nowBalance) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "By Custom Amount", "You entered an amount more than balance", "Close");
                    } else {
                        if (BillExecute.doInsertByAmount_Bill(str_holdCode, mTableIdx, insValue).equals("Y")){
                            setBalanceSubtotal_byCustomAmount();
                            renew_gridview(str_holdCode, mTableIdx,"2");
                        }
                    }
                    break;
                }
                case R.id.tablesale_splitmerge_close : {
                    LogsSave.saveLogsInDB(251);
                    BillExecute.closeCheckAmount(mActivity, mContext, str_holdCode, d_TotalAmount);
                    MainMiddleService.initList();

                    // 05042023
                    if (mChangeStatus > 0) {
                        TableSaleMain.onRefreshAtOutside();
                        mChangeStatus = 0;
                    }
                    break;
                }

                case R.id.tablesale_splitmerge_merge_selected : {
                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
//                    if (getCountInBillPaid(str_holdCode) > 0) {
//                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
//                                "There is a bill that has been paid and cannot be changed", "Close");
//                        return;
//                    }

                    if (mSelectedBillBoxValue.size() < 2) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select two or more bills to merge", "Close");
                        return;
                    }

                    new AlertDialog.Builder(mContext)
                            .setTitle("Merge Selected")
                            .setMessage("Would you like to merge the selected bills?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(254);
                                            setSelectedMerge();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                    break;
                }

                case R.id.tablesale_splitmerge_reset_selected : {
                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) > 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "There is a bill that has been paid and cannot be changed - (5)", "Close");
                        return;
                    }

                    if (mSelectedBillBoxValue.size() == 0) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select one or more bills to delete", "Close");
                        return;
                    }

                    new AlertDialog.Builder(mContext)
                            .setTitle("Reset Selected")
                            .setMessage("Would you like to delete the selected bills?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(255);
                                            setSelectedDelete();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                    break;
                }

                case R.id.tablesale_splitmerge_reset_all : {
                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) > 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "There is a bill that has been paid and cannot be changed - (6)", "Close");
                        return;
                    }

                    new AlertDialog.Builder(mContext)
                            .setTitle("Reset All")
                            .setMessage("Would you like to delete the all bills?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(256);
                                            setAllDelete();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                    break;
                }
                case R.id.tablesale_splitmerge_pay_by_bills : {
                    if (mSelectedBillBoxValue.size() == 0) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a bill to pay", "Close");
                        return;
                    }
                    if (mSelectedBillBoxValue.size() > 1) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one bill to pay", "Close");
                        return;
                    }
                    if (mSelectedBillBoxValue.size() == 1) {

                        // 07182023
                        // by menu 로 split 할 경우
                        // 설정한 split 갯수만큼 split 되지 않았을 경우
                        // 결제되지 않도록 할 것
                        if (str_Billsplit_Type == "0" || str_Billsplit_Type.equals("0")) {
                            String tempHoldcodeByBillidx = MssqlDatabase.getResultSetValueToString(
                                    " select holdcode from bill_list where idx = '" + mSelectedBillBoxValue.get(0) + "' "
                            );
                            double billlistcnt = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from bill_list where holdcode = '" + tempHoldcodeByBillidx + "' "
                            ));
                            double tempSplitCntByMenu = GlobalMemberValues.getDoubleAtString(tablesale_split_evenly_txt2.getText().toString());
                            if (billlistcnt < tempSplitCntByMenu) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Split is not complete", "Close");
                                return;
                            }
                        }


                        // 이미 결제가 완료된 bill 인지 체크
                        String paidyn = MssqlDatabase.getResultSetValueToString(
                                " select paidyn from bill_list where idx = '" + mSelectedBillBoxValue.get(0) + "' "
                        );
                        if (GlobalMemberValues.isStrEmpty(paidyn)) {
                            paidyn = "N";
                        }
                        if (paidyn.equals("Y")) {
                            GlobalMemberValues.displayDialog(mContext, "Warning", "The selected bill has been paid", "Close");
                            return;
                        }
                    }
                    LogsSave.saveLogsInDB(260);
                    GlobalMemberValues.mIsClickPayment = true;

                    if (GlobalMemberValues.isPaymentByBills) {
                        setPayOnBill();
                    } else {
//                        new AlertDialog.Builder(mContext)
//                                .setTitle("Pay on Bill")
//                                .setMessage("Would you like to pay the selected bill?")
//                                //.setIcon(R.drawable.ic_launcher)
//                                .setPositiveButton("Yes",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                setPayOnBill();
//                                            }
//                                        })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .show();
                        setPayOnBill();
                    }
                    break;
                }
                case R.id.tablesale_splitmerge_selected_printind : {

                    int index = 0;
                    for (String[] temp : str_list_selected_table_info){
                        if (selected_table_info == null) return;
                        // check 프린팅 내용
                        LogsSave.saveLogsInDB(257);
                        GlobalMemberValues.RECEIPTPRINTTYPE = "";
//                        GlobalMemberValues.TableSaleMenuPrintingJson(holdcode, "R", selected_table_info, "", str_Billsplit_Type);
//                        GlobalMemberValues.printReceiptByJHP(selected_jsonObject, mContext, "payment");

                        /////////////////////////////////////

                        JSONObject jsonObject = new JSONObject();
                        String split_total = "";
                        try {
                            jsonObject = GlobalMemberValues.TableSaleMenuPrintingJson(gettHoldcode(""), "R", str_selectTableInfos, "", temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 12282022 --------------------------------------------------------------------------------------------------------------------
                        if (jsonObject != null && !GlobalMemberValues.isStrEmpty(jsonObject.toString())) {
                            if (selected_table_info != null && selected_table_info.length > 0) {
                                try {
                                    if (mSelectedBillBoxValue != null && mSelectedBillBoxValue.size() > 0) {
                                        jsonObject.put("bill_list_idx", mSelectedBillBoxValue.get(0));

                                        GlobalMemberValues.logWrite("billdatatestlog", "bill idx : " + GlobalMemberValues.getDataInJsonData(jsonObject, "bill_list_idx") + "\n");
                                    }
                                    jsonObject.put("bill_list_billamount", selected_table_info[2]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                GlobalMemberValues.logWrite("billdatatestlog", "bill amount : " + GlobalMemberValues.getDataInJsonData(jsonObject, "bill_list_billamount") + "\n");
                            }
                        }
                        // ------------------------------------------------------------------------------------------------------------------------------

                        if (str_Billsplit_Type.equals("2") || str_Billsplit_Type.equals("1") || str_Billsplit_Type.equals("0")){
                            if (temp != null){
                                String price = temp[2];
                                try {
                                    jsonObject.put("split_price",price );
                                    jsonObject.put("split_idx",temp[3] );
                                    split_total = price;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        ///////////////////////////////
                        try {
//                            setBillPrintLn("","L", selected_table_info, str_Billsplit_Type);
                            GlobalMemberValues.tableMainPrinting(gettHoldcode(""), "R", str_selectTableInfos,"",temp, str_Billsplit_Type, jsonObject);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 05042023
                        GlobalMemberValues.setChangeBillPrintedStatus(mTableIdx, mSubTableNum, false);
                        mChangeStatus++;

                    }
                    setFreshBillBoard(str_holdCode, GlobalMemberValues.getReplaceText(mTableIdx, "T", ""), str_Billsplit_Type);

                    break;
                }

                case R.id.tablesale_splitmerge_void : {
                    if (mSelectedBillBoxValue.size() > 1) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one bill to void", "Close");
                        return;
                    }

                    // 해당 holdcode 로 결제된 내역이 있는지 확인한다
                    if (getCountInBillPaid(str_holdCode) == 0) {
                        GlobalMemberValues.displayDialog(BillSplitMerge.this, "Warning",
                                "Please select a bill for which payment has been completed", "Close");
                        return;
                    }

                    if (mSelectedBillBoxValue.size() == 0) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a bill to void", "Close");
                        return;
                    }

                    new AlertDialog.Builder(mContext)
                            .setTitle("Void Selected")
                            .setMessage("Are you sure you want to void the selected bill?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(258);
                                            setVoidOnBill();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                    break;
                }
            }
        }
    };

    public void setVoidOnBill() {
        GlobalMemberValues.mOnVoidForBillPay = true;

        for (String billidx : mSelectedBillBoxValue) {
            GlobalMemberValues.logWrite("jjjbillvoidlogjjj", "billidx : " + billidx + "\n");

            String tempCardSalesIdx = MssqlDatabase.getResultSetValueToString(
                    " select cardsalesidx from bill_list_paid where billidx = '" + billidx + "' "
            );

            String tempRefNum = MssqlDatabase.getResultSetValueToString(
                    " select cardRefNumber from salon_sales_card where idx = '" + tempCardSalesIdx + "' "
            );

            String tempPriceAmount = MssqlDatabase.getResultSetValueToString(
                    " select priceAmount from salon_sales_card where idx = '" + tempCardSalesIdx + "' "
            );

            // 먼저 선택된 bill 의 결제완료된 수단(캐쉬/카드)을 확인한다.
            String tmepPaytype = MssqlDatabase.getResultSetValueToString(
                    " select paytype from bill_list_paid where billidx = '" + billidx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(tmepPaytype)) {
                tmepPaytype = "CASH";
            }

            if (tmepPaytype.equals("CASH")) {
                String strDeleteQuery = "";
                Vector<String> strUpdateQueryVec = new Vector<String>();

                strDeleteQuery = "delete from bill_list_paid where billidx = '" + billidx + "' ";
                strUpdateQueryVec.addElement(strDeleteQuery);

                strDeleteQuery = "update bill_list set paidyn = 'N' where idx = '" + billidx + "' ";
                strUpdateQueryVec.addElement(strDeleteQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("jjjbillvoidlogjjj", "delete query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error. Try Again", "Close");
                } else {                                                // DB (salon_sales_card 테이블) 입력성공
                    // DB 처리도 성공...
                    mActivity.recreate();
                }
            } else {
                if (!GlobalMemberValues.isStrEmpty(tempCardSalesIdx) && !GlobalMemberValues.isStrEmpty(tempRefNum)
                        && !GlobalMemberValues.isStrEmpty(tempPriceAmount)) {
                    GlobalMemberValues.logWrite("jjjbillvoidlogjjj", "void 시작전..." + "\n");
                    PaymentCreditCard.voidCardProcess(tempCardSalesIdx, tempRefNum, (GlobalMemberValues.getDoubleAtString(tempPriceAmount) + ""));
                }
            }
        }
    }

    public void setPayOnBill() {
        MainMiddleService.initList();

        String bill_idx = mSelectedBillBoxValue.get(0);

        String strQuery = " select holdcode, billamount, cartidxs, tableidx from bill_list  " +
                " where " +
                " idx = '" + bill_idx + "' ";
        ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);

        String get_holdcode = "";
        String get_billamount = "";
        String get_cartidxs = "";
        String get_tableidx = "";

        try {
            while (resultSet.next()) {
                get_holdcode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                get_billamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                get_cartidxs = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                get_tableidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);
            }
        } catch (Exception e) {
        }

        //07052024 close resultset
        try {
            if(!resultSet.isClosed()){
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (GlobalMemberValues.isStrEmpty(get_holdcode)) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a bill to pay", "Close");
        } else {
            GlobalMemberValues.isPaymentByBills = true;
            GlobalMemberValues.mSelectedHoldCodeForBillPay = get_holdcode;
            GlobalMemberValues.mSelectedTableIdxOnBillPay = mTableIdx;
            GlobalMemberValues.mSelectedSubTableNumOnBillPay = mSubTableNum;
            GlobalMemberValues.mBillSplitType = str_Billsplit_Type;

            GlobalMemberValues.mCartIdxsOnBillPay = get_cartidxs;

            GlobalMemberValues.mBill_Idx = bill_idx;

            GlobalMemberValues.mPayTotalAmountOnBill = d_TotalAmount;
            GlobalMemberValues.mPayAmountOnBill = GlobalMemberValues.getDoubleAtString(get_billamount);

            GlobalMemberValues.logWrite("billpaylog", "holdcode : " + GlobalMemberValues.mSelectedHoldCodeForBillPay + "\n");
            GlobalMemberValues.logWrite("billpaylog", "table idx : " + GlobalMemberValues.mSelectedTableIdxOnBillPay + "\n");
            GlobalMemberValues.logWrite("billpaylog", "sub num : " + GlobalMemberValues.mSelectedSubTableNumOnBillPay + "\n");
            GlobalMemberValues.logWrite("billpaylog", "split type : " + GlobalMemberValues.mBillSplitType + "\n");
            GlobalMemberValues.logWrite("billpaylog", "cart idx : " + GlobalMemberValues.mCartIdxsOnBillPay + "\n");
            GlobalMemberValues.logWrite("billpaylog", "total pay : " + GlobalMemberValues.mPayTotalAmountOnBill + "\n");
            GlobalMemberValues.logWrite("billpaylog", "pay amount : " + GlobalMemberValues.mPayAmountOnBill + "\n");

            // 결제는 현재 cash in 되어 있는 cashier 만 한다.  ----------------------------------------
//            String tempSql = " select EmployeeIdx from salon_sales_cashintout_history " +
//                    " where cashoutNum = 0 " +
//                    " and inoutreason like '%Starting cash%' ";
//            tempSql = " select eid from salon_storeemployee where idx in ( " + tempSql + " ) ";
//            String tempEmpId = MssqlDatabase.getResultSetValueToString(tempSql);
//            EmployeeKeyIn.setEmpInfo(tempEmpId);
//            GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;
            // -----------------------------------------------------------------------------------

            TableSaleMain.setClearSelectedTableIdx(true);
            TableSaleMain.mSelectedTablesArrList.add(get_tableidx);
            TableSaleMain.openSales(TableSaleMain.mSelectedTablesArrList, true);

            GlobalMemberValues.mIsOnPaymentProcessForBillPay = false;
            GlobalMemberValues.isOpenTableSaleMain = false;

            GlobalMemberValues.logWrite("billopenjjjj", "GlobalMemberValues.mIsOnPaymentProcessForBillPay : "
                    + GlobalMemberValues.mIsOnPaymentProcessForBillPay + "\n");

            mActivity.finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void setAllDelete() {

        Vector<String> vec = new Vector<String>();
        String strQuery = " delete from bill_list where holdcode = '" + str_holdCode + "' ";
        vec.add(strQuery);

        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        if (returnValue.equals("N")) {
        } else {
            switch (str_Billsplit_Type) {
                case "0" : {
                    setCartListView();

                    break;
                }
                case "1" : {
                    setEvenlyCountTextView();

                    break;
                }
                case "2" : {
                    setBalanceSubtotal_byCustomAmount();

                    break;
                }
            }

            setFreshBillBoard(str_holdCode, GlobalMemberValues.getReplaceText(mTableIdx, "T", ""), str_Billsplit_Type);
        }
    }

    public void setFreshBillBoard(String paramHoldCode, String paramTableIdx, String paramBillSplitType) {
        renew_gridview(paramHoldCode, paramTableIdx, paramBillSplitType);
        mSelectedBillBoxValue.clear();
        str_list_selected_table_info.clear();
    }

    public void setSelectedDelete() {
        String strQuery = "";
        ResultSet resultSet;

        Vector<String> vec = new Vector<String>();

        String ins_tableidx = "";

        int rsCnt = 0;
        for (String billidx : mSelectedBillBoxValue) {
            if (rsCnt == 0) {
                ins_tableidx = MssqlDatabase.getResultSetValueToString(
                        " select tableidx from bill_list where idx = '" + billidx + "' "
                );
            }

            strQuery = " delete from bill_list where idx = '" + billidx + "' ";
            vec.add(strQuery);

            rsCnt++;
        }

        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        if (returnValue.equals("N")) {
        } else {
            switch (str_Billsplit_Type) {
                case "0" : {
                    setCartListView();

                    break;
                }
                case "1" : {
                    setEvenlyCountTextView();

                    break;
                }
                case "2" : {
                    setBalanceSubtotal_byCustomAmount();

                    break;
                }
            }

            setFreshBillBoard(str_holdCode, ins_tableidx, str_Billsplit_Type);
        }
    }

    public void setSelectedMerge() {
        String ins_holdcode = "";
        double ins_billamount = 0.0;
        String ins_cartidxs = "";

        String ins_tableidx = "";
        String ins_billsplittype = str_Billsplit_Type;

        String strQuery = "";
        ResultSet resultSet;

        String get_holdcode = "";
        String get_billamount = "";
        String get_cartidxs = "";
        String get_tableidx = "";

        Vector<String> vec = new Vector<String>();

        int rsCnt = 0;
        for (String billidx : mSelectedBillBoxValue) {
            strQuery = " select holdcode, billamount, cartidxs, tableidx from bill_list  " +
                    " where " +
                    " idx = '" + billidx + "' ";
            resultSet = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (resultSet.next()) {
                    get_holdcode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                    get_billamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                    get_cartidxs = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                    get_tableidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);

                    if (rsCnt == 0) {
                        ins_holdcode = get_holdcode;
                        ins_tableidx = get_tableidx;
                    } else {
                        ins_cartidxs += ",";
                    }

                    vec.add(" delete from bill_list where idx = '" + billidx + "' ");

                    ins_billamount += GlobalMemberValues.getDoubleAtString(get_billamount);
                    ins_cartidxs += get_cartidxs;
                }
            } catch (Exception e) {
            }

            //07052024 close resultset
            try {
                if(!resultSet.isClosed()){
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            rsCnt++;
        }

        if (str_Billsplit_Type == "0") {
        } else {
            ins_cartidxs = "";
        }

        strQuery = BillExecute.makeQueryByInsert_Bill(
                ins_holdcode, ins_tableidx, ins_billamount, ins_cartidxs, ins_billsplittype
        );

        vec.add(strQuery);

        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        if (returnValue.equals("N")) {
        } else {
            setFreshBillBoard(ins_holdcode, ins_tableidx, ins_billsplittype);
        }
    }

    public void setCartListView() {
        tablesale_byfoodmenu_list.setAdapter(null);      // 초기화
        tablesale_byfoodmenu_list.setAdapter(new BillSplitMergeMenuListAdapter(getSaleHistoryList()));
        setListViewSize(tablesale_byfoodmenu_list);
    }

    AdapterView.OnItemClickListener grid_onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String strSelectedArrList = "";
            String tempBill_Idx = "";

            String[] table_info;
            if (arrayList_list.isEmpty()){
                strSelectedArrList = "";
                return;
            } else {
                strSelectedArrList = arrayList_list.get(position);
                table_info = strSelectedArrList.split("-JJJ-");
                tempBill_Idx = table_info[3];
            }

            GlobalMemberValues.logWrite("jjjarrlog", "value : " + strSelectedArrList + "\n");

            final View finalConvertView = view;
            LinearLayout linearLayout = view.findViewById(R.id.tablesale_grid_row_ln);
//            if (linearLayout.isSelected()){
//                finalConvertView.setSelected(false);
//            } else {
//                finalConvertView.setSelected(true);
//            }

            selected_table_info = table_info;
            if (mSelectedBillBoxValue.contains(tempBill_Idx)) {
                //finalConvertView.setSelected(false);
                linearLayout.setSelected(false);
                int temp = mSelectedBillBoxValue.indexOf(tempBill_Idx);
                mSelectedBillBoxValue.remove(tempBill_Idx);

                str_list_selected_table_info.remove(temp);
            } else {
                //finalConvertView.setSelected(true);
                linearLayout.setSelected(true);
                mSelectedBillBoxValue.add(tempBill_Idx);
                str_list_selected_table_info.add(selected_table_info);

            }

            if (mSelectedBillBoxValue.size() == 1) {
                // check 프린팅 내용
                setBillPrintLn("","L", table_info, str_Billsplit_Type);
            }


            setRenewBillBox();
        }
    };

    public void setRenewBillBox() {
        for (String value : mSelectedBillBoxValue) {
            GlobalMemberValues.logWrite("jjjarrlogvalue", "value : " + value + "\n");
        }
    }

    public void renew_gridview(String paramHoldCode, String paramTableIdx, String paramBillSplitType) {
        setBillPrintLn("","L", null, paramBillSplitType);

        arrayList_list = BillExecute.getBillList(paramHoldCode,paramTableIdx,paramBillSplitType);
        billSplitGridAdapter = new BillSplitGridAdapter(arrayList_list);
        billSplitGridAdapter.notifyDataSetChanged();
        grid_billList.setAdapter(billSplitGridAdapter);

        if (paramBillSplitType.equals("2")){
            double d_split_price = 0.00;
            double d_split_subtotal = 0.00;
            for (String temp : arrayList_list){
                String[] str_split_item = temp.split("-JJJ-");
                d_split_subtotal = GlobalMemberValues.getDoubleAtString(str_split_item[2]);
            }
            d_split_price = d_TotalAmount - d_split_subtotal;

            setBalanceSubtotal_byCustomAmount();

            d_TotalAmount = d_split_price;
            tablesale_split_byamount.setText("");

        }
    }

    View.OnClickListener evenlyQty_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_tablesale_evenly_plus : {
                    String qty = (String) tablesale_split_evenly_txt.getText();
                    int i_qty = Integer.parseInt(qty);
                    i_qty = i_qty + 1;
                    tablesale_split_evenly_txt.setText(String.valueOf(i_qty));

                    // 04212023
                    mBillSplitCount = i_qty;

                    break;
                }
                case R.id.btn_tablesale_evenly_minus : {
                    String qty = (String) tablesale_split_evenly_txt.getText();
                    int i_qty = Integer.parseInt(qty);
                    i_qty = i_qty - 1;
                    if (i_qty == 0) return;
                    tablesale_split_evenly_txt.setText(String.valueOf(i_qty));

                    // 04212023
                    mBillSplitCount = i_qty;

                    break;
                }
            }
        }
    };

    View.OnClickListener evenlyQty_listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_tablesale_evenly_plus2 : {
                    // 04212023
                    // 등록된 bill_list 가 있으면 수량변경안되게 한다.
                    int tempBillListCnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from bill_list where holdcode = '" + str_holdCode + "' "
                            )
                    );

                    if (tempBillListCnt == 0) {
                        String qty = (String) tablesale_split_evenly_txt2.getText();
                        int i_qty = Integer.parseInt(qty);
                        i_qty = i_qty + 1;
                        tablesale_split_evenly_txt2.setText(String.valueOf(i_qty));

                        // 04212023
                        mBillSplitCount = i_qty;
                    }

                    break;
                }
                case R.id.btn_tablesale_evenly_minus2 : {
                    // 04212023
                    // 등록된 bill_list 가 있으면 수량변경안되게 한다.
                    int tempBillListCnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from bill_list where holdcode = '" + str_holdCode + "' "
                            )
                    );

                    if (tempBillListCnt == 0) {
                        String qty = (String) tablesale_split_evenly_txt2.getText();
                        int i_qty = Integer.parseInt(qty);
                        i_qty = i_qty - 1;
                        if (i_qty == 0) return;
                        tablesale_split_evenly_txt2.setText(String.valueOf(i_qty));

                        // 04212023
                        mBillSplitCount = i_qty;
                    }

                    break;
                }
            }
        }
    };

    public void setBillPrintLn(String paramBillNum, String paramType, String[] paramSplitMergeInfo, String paramBillSplitType) {
        String strQuery = "";

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        if (GlobalMemberValues.isStrEmpty(paramBillNum)) {
            if (mergednum > 0) {
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where mergednum = '" + mergednum + "' " +
                        " order by billnum asc";
            } else {
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                        " order by billnum asc";
            }

            String tempBillNum = MssqlDatabase.getResultSetValueToString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempBillNum)) {
                tempBillNum = "1";
            }
            paramBillNum = tempBillNum;

//            mSelectedBillNum = tempBillNum;
        }

        // holdcode 구하기
        if (mergednum > 0) {
            strQuery = "select holdcode from temp_salecart " +
                    " where mergednum = '" + mergednum + "' ";
        } else {
            strQuery = "select holdcode from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' ";
        }

        String holdcode = MssqlDatabase.getResultSetValueToString(strQuery);
        if (!GlobalMemberValues.isStrEmpty(holdcode)) {
            str_holdCode = holdcode;
            String tableInfos = mergednum + "-JJJ-" + mTableIdx + "-JJJ-" + mSubTableNum + "-JJJ-" + paramBillNum + "-JJJ-" + "END";
            GlobalMemberValues.logWrite("phoneorderjsonlog", "tableInfos : " + tableInfos + "\n");

            if (paramType.equals("L")) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalMemberValues.TableSaleMenuPrintingJson(holdcode, "R", tableInfos, "", paramSplitMergeInfo);


                    // 12282022 --------------------------------------------------------------------------------------------------------------------
                    if (jsonObject != null && !GlobalMemberValues.isStrEmpty(jsonObject.toString())) {
                        if (selected_table_info != null && selected_table_info.length > 0) {
                            if (mSelectedBillBoxValue != null && mSelectedBillBoxValue.size() > 0) {
                                jsonObject.put("bill_list_idx", mSelectedBillBoxValue.get(0));

                                GlobalMemberValues.logWrite("billdatatestlog", "bill idx : " + GlobalMemberValues.getDataInJsonData(jsonObject, "bill_list_idx") + "\n");
                            }
                            jsonObject.put("bill_list_billamount", selected_table_info[2]);

                            GlobalMemberValues.logWrite("billdatatestlog", "bill amount : " + GlobalMemberValues.getDataInJsonData(jsonObject, "bill_list_billamount") + "\n");
                        }
                    }
                    // ------------------------------------------------------------------------------------------------------------------------------


                    selected_jsonObject = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {

                    if (paramBillSplitType.equals("2") || paramBillSplitType.equals("1") || paramBillSplitType.equals("0")){
                        if (paramSplitMergeInfo != null){
                            String split_idx = paramSplitMergeInfo[3];
                            String price = paramSplitMergeInfo[2];
                            try {
                                jsonObject.put("split_idx",split_idx );
                                jsonObject.put("split_price",price );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    // 05162023
                    if (!isFirst) {
                        LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView_onlyView(jsonObject);
                        tablesale_splitmerge_receiptview.removeAllViews();
                        tablesale_splitmerge_receiptview.addView(getJsonLn);
                    }
                }

                // Bill Title
//                String tableName = MssqlDatabase.getResultSetValueToString(" select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(mTableIdx, "T", "") + "' ");
//                tableName = tableName + " (Bill " + TableSaleBillSplit.getBillNumString(paramBillNum) + ")";
//                billtitletv.setText(tableName);
            } else {
                try {
                    GlobalMemberValues.phoneorderPrinting(holdcode, "R", tableInfos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public ArrayList<String> getSaleHistoryList() {
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt, sTotalAmount from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                    " order by svcName desc ";
        } else {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt, sTotalAmount from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                    " order by svcName desc ";
        }

        GlobalMemberValues.logWrite("billsplitlog", "strQuery : " + strQuery + "\n");

        String itemIdx = "";
        String itemName = "";
        String itemQty = "";
        String billNum = "";
        String optionTxt = "";
        String sTotalAmount = "";
        String mDataL = "";

        ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
        try{
            while (cartCurosr.next()) {
                itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1);
                itemQty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,2), 1);
                billNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,3), 1);
                optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,4), 1);
                sTotalAmount = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,5), 1),"2");

                mDataL = itemIdx + "-JJJ-" + itemName+ "-JJJ-" + sTotalAmount + "-JJJ-" + billNum + "-JJJ-" + optionTxt + "-JJJ-" + "END";
                mArrList.add(mDataL);
            }
            cartCurosr.close();
        }catch (Exception e){

        }

        // Total 구하기
        if (mergednum > 0) {
            strQuery = "select sum(sTotalAmount) from temp_salecart " +
                    " where mergednum = '" + mergednum + "' ";
        } else {
            strQuery = "select sum(sTotalAmount) from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' ";
        }
        double totalAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(strQuery));
//        totalamounttv.setText("$ " + GlobalMemberValues.getCommaStringForDouble(totalAmount + ""));

        return mArrList;
    }

    public JSONArray getBillList() {
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        JSONArray jsonArray = new JSONArray();


        if (mergednum > 0) {
            strQuery = "select billnum, sum(sTotalAmount), sum(sQty) from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " group by billnum " +
                    " order by billnum asc ";
        } else {
            strQuery = "select billnum, sum(sTotalAmount), sum(sQty) from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                    " group by billnum " +
                    " order by billnum asc ";
        }

        GlobalMemberValues.logWrite("billsplitlog", "strQuery : " + strQuery + "\n");

        String billnum = "";
        String totalamount = "";
        String tableidx = "";
        String qty = "";

        ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
        try{
            while (cartCurosr.next()) {
                JSONObject jsonObject = new JSONObject();
                billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                totalamount = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1),"2");
                qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,2), 1);

                jsonObject.put("title",mTableIdx +"-" + billnum);
                jsonObject.put("amount",totalamount);
                jsonObject.put("menu_qty",qty);
                jsonObject.put("billnum", billnum);

                jsonArray.put(jsonObject);
            }
            cartCurosr.close();
        }catch (Exception e){

        }

        // add grid add
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title","empty");
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonArray;
    }

    public String gettHoldcode(String paramBillNum) {
        String strQuery = "";

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        if (GlobalMemberValues.isStrEmpty(paramBillNum)) {
            if (mergednum > 0) {
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where mergednum = '" + mergednum + "' " +
                        " order by billnum asc";
            } else {
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                        " order by billnum asc";
            }

            String tempBillNum = MssqlDatabase.getResultSetValueToString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempBillNum)) {
                tempBillNum = "1";
            }
            paramBillNum = tempBillNum;

//            mSelectedBillNum = tempBillNum;
        }

        // holdcode 구하기
        if (mergednum > 0) {
            strQuery = "select holdcode from temp_salecart " +
                    " where mergednum = '" + mergednum + "' ";
        } else {
            strQuery = "select holdcode from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' ";
        }

        str_selectTableInfos = mergednum + "-JJJ-" + mTableIdx + "-JJJ-" + mSubTableNum + "-JJJ-" + paramBillNum + "-JJJ-" + "END";

        String holdcode = MssqlDatabase.getResultSetValueToString(strQuery);
        return holdcode;
    }

    public void setListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
    }

    public static void setVoidCardOnBill(String paramResultCode, String paramSalonSalesCardIdx, String paramPriceAmount) {
        GlobalMemberValues.mOnVoidForBillPay = false;

        if (paramResultCode.equals("00") || paramResultCode == "00") {
            String returnResult = "";

            String tempBillPaidIdx = MssqlDatabase.getResultSetValueToString("select idx from bill_list_paid where cardsalesidx = '" + paramSalonSalesCardIdx + "' ");
            String tempBillIdx = MssqlDatabase.getResultSetValueToString("select billidx from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");
            String tempHoldCode = MssqlDatabase.getResultSetValueToString("select holdcode from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");

            String tempSalesCode = MssqlDatabase.getResultSetValueToString("select salesCode from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ");

            Vector<String> strUpdateQueryVec = new Vector<String>();
            String strDeleteQuery = "";

            strUpdateQueryVec = GlobalMemberValues.getVectorForVoid(paramSalonSalesCardIdx, tempBillPaidIdx, tempBillIdx);

//            strDeleteQuery = "delete from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ";
//            strUpdateQueryVec.addElement(strDeleteQuery);
//
//            strDeleteQuery = "delete from bill_list_paid where idx = '" + tempBillPaidIdx + "' ";
//            strUpdateQueryVec.addElement(strDeleteQuery);
//
//            strDeleteQuery = "update bill_list set paidyn = 'N' where idx = '" + tempBillIdx + "' ";
            strUpdateQueryVec.addElement(strDeleteQuery);

            if (JJJ_SignPad.mVoidOnSignPad == "N" || JJJ_SignPad.mVoidOnSignPad.equals("N")) {
                strDeleteQuery = "delete from salon_sales_tip where salesCode = '" + tempSalesCode + "' ";
                strUpdateQueryVec.addElement(strDeleteQuery);
                JJJ_SignPad.mVoidOnSignPad = "N";
            }
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "delete query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                if (mContext != null) {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error. Try Again", "Close");
                }
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // 092022
                GlobalMemberValues.serviceStartSendDataToCloud(mContext, mActivity);

                // DB 처리도 성공...
                if (mActivity != null) {
                    mActivity.recreate();
                }

                // 06.04.2022
                if (VoidCardList.mActivity != null) {
                    VoidCardList.mActivity.recreate();
                }
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Void processing error", "Close");
        }
    }

    public static void setInitValuesForBillPay() {
        GlobalMemberValues.mIsOnPaymentProcessForBillPay = false;
        GlobalMemberValues.isOpenTableSaleMain = false;

        GlobalMemberValues.isPaymentByBills = false;                     // bill 결제하는지 여부

        GlobalMemberValues.mBill_Idx = "";                                   // bill_list 테이블의 idx

        GlobalMemberValues.mBillSplitType = "";                           // bill split type

        GlobalMemberValues.mCartIdxsOnBillPay = "";                       // bill split type 이 0 일 경우 cart idx
        GlobalMemberValues.mSelectedHoldCodeForBillPay = "";

        GlobalMemberValues.mSelectedTableIdxOnBillPay = "";               // 선택한 table idx
        GlobalMemberValues.mSelectedSubTableNumOnBillPay = "";            // 선택한 테이블의 sub num

        GlobalMemberValues.mPayTotalAmountOnBill = 0.0;                   // bill 결제할 총 금액
        GlobalMemberValues.mPayAmountOnBill = 0.0;                        // bill 결제하는 금액

        GlobalMemberValues.mIsOnPaymentProcessForBillPay = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (GlobalMemberValues.isCustomBillSplitUse()) {
            if (btn_tablesale_bycustomamount != null) {
                btn_tablesale_bycustomamount.setVisibility(View.VISIBLE);
            }
            if (tablesale_byequalamount_view != null) {
                tablesale_byequalamount_view.setVisibility(View.VISIBLE);
            }
            if (tablesale_bycustomamount_view != null) {
                tablesale_bycustomamount_view.setVisibility(View.VISIBLE);
            }
        } else {
            if (btn_tablesale_bycustomamount != null) {
                btn_tablesale_bycustomamount.setVisibility(View.GONE);
            }
            if (tablesale_byequalamount_view != null) {
                tablesale_byequalamount_view.setVisibility(View.GONE);
            }
            if (tablesale_bycustomamount_view != null) {
                tablesale_bycustomamount_view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
