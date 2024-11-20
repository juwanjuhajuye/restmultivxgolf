package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.Payment;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TableSaleBillPrint extends Activity {
    public static Activity mActivity;
    Context mContext;

    ListView table_main_billprint_list;

    private LinearLayout parentLn, billcontentsLn;

    private TextView table_main_popup_top;

    private ImageButton btn_close_img;

    private ImageButton selectedPrintBtn, allPrintBtn, kitchenprint;

    TextView billtitletv;

    Intent mIntent;

    String mTableIdx = "";
    String mSubTableNum = "1";

    ArrayList<String> m_arr_string2;

    BillListAdapter billListAdapter;

    JSONObject jsonForPrinting = null;

    static String mSelectedBillNum = "";

    private ImageButton orderpayment;

    private String isMainIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_bill_print);
        this.setFinishOnTouchOutside(false);


        parentLn = (LinearLayout)findViewById(R.id.table_main_table_split_ln);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mTableIdx = mIntent.getStringExtra("selectedTableIdx");
            mSubTableNum = mIntent.getStringExtra("selectedSubTableNum");
            isMainIn = mIntent.getStringExtra("isMainIn");
            if (GlobalMemberValues.isStrEmpty(isMainIn)) {
                isMainIn = "N";
            }

            if (GlobalMemberValues.isStrEmpty(mSubTableNum)) {
                mSubTableNum = "1";
            }

            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mTableIdx : " + mTableIdx + "\n");
            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mSubTableNum : " + mSubTableNum + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

            if (GlobalMemberValues.isStrEmpty(mTableIdx)) {
                finish();
            }
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }


        if (GlobalMemberValues.isBillPrintPopupOpen()){
            setContent();
        } else {
            setBillPrintLn("","P");
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setContent() {
        mSelectedBillNum = "";

        billcontentsLn = (LinearLayout)findViewById(R.id.billcontentsLn);
        billcontentsLn.removeAllViews();

        table_main_popup_top = (TextView)findViewById(R.id.table_main_popup_top);
        table_main_popup_top.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_main_popup_top.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        btn_close_img = (ImageButton)findViewById(R.id.table_main_bill_split_close);
        btn_close_img.setOnClickListener(clickListener);

        table_main_billprint_list = (ListView)findViewById(R.id.table_main_billprint_list);
        table_main_billprint_list.setOnItemClickListener(lvClick);

        billtitletv = (TextView) findViewById(R.id.billtitletv);
        billtitletv.setTextSize(GlobalMemberValues.globalAddFontSize() + billtitletv.getTextSize()
                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        selectedPrintBtn = (ImageButton) findViewById(R.id.selectedPrintBtn);
//        selectedPrintBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + selectedPrintBtn.getTextSize()
//                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        selectedPrintBtn.setOnClickListener(clickListener);

        allPrintBtn = (ImageButton) findViewById(R.id.allPrintBtn);
//        allPrintBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + allPrintBtn.getTextSize()
//                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        allPrintBtn.setOnClickListener(clickListener);

        orderpayment = (ImageButton) findViewById(R.id.orderpayment);
//        orderpayment.setTextSize(GlobalMemberValues.globalAddFontSize() + orderpayment.getTextSize()
//                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        orderpayment.setOnClickListener(clickListener);

        kitchenprint = (ImageButton) findViewById(R.id.kitchenprint);
//        kitchenprint.setTextSize(GlobalMemberValues.globalAddFontSize() + kitchenprint.getTextSize()
//                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        kitchenprint.setOnClickListener(clickListener);

        if (isMainIn.equals("Y")){
            orderpayment.setVisibility(View.GONE);
        } else {
            orderpayment.setVisibility(View.VISIBLE);

            // check list 에서 오픈됐을 경우
            // TableSaleMain.mSelectedTablesArrList 의 값을 초기화 후 해당 테이블 값을 추가한다.
            TableSaleMain.mSelectedTablesArrList.clear();
            TableSaleMain.mSelectedTablesArrList.add("T" + GlobalMemberValues.getTableIdxWithoutStringT(mTableIdx) + "T");
        }


        setBillPrintLn("", "L");

        setBillList();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.table_main_bill_split_close : {
                    finishActivity();

                    break;
                }
                case R.id.selectedPrintBtn : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedBillNum)) {
                        GlobalMemberValues.displayDialog(mContext, "", "Select a bill", "Close");

                        return;
                    }

                    setBillPrintLn(mSelectedBillNum, "P");

                    break;
                }
                case R.id.allPrintBtn : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("")
                                .setMessage("Are you sure you want to print all bills?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                allBillsPrint();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    }

                    break;
                }
                case R.id.orderpayment : {
                    if (GlobalMemberValues.isStrEmpty(mTableIdx)) {
                        return;
                    }
                    TableSaleMain.startOrderPayment(mTableIdx);
                    mActivity.finish();
                    if (TableCheckList.mActivity != null) {
                        TableCheckList.mActivity.finish();
                    }
                    GlobalMemberValues.is_billprint_in_payment = true;

                    break;
                }
                case R.id.kitchenprint : {
                    GlobalMemberValues.isKitchenReprinting = "K";
                    TableSaleMain.kitchenPrint(TableSaleMain.getHoldCodeByTableidx(TableSaleMain.mSelectedTablesArrList.get(0), TableSaleMain.mSubTableNum), true, mContext);
//                    TableSaleMain tsm = new TableSaleMain();
//                    tsm.doKitchenReprint(false);
                    break;
                }
            }
        }
    };

    public void setBillList() {
        m_arr_string2 = getBillList();

        billListAdapter = new BillListAdapter(m_arr_string2);
        table_main_billprint_list.setAdapter(null);      // 초기화
        table_main_billprint_list.setAdapter(billListAdapter);
    }

    public ArrayList<String> getBillList() {
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "select billnum, sum(sTotalAmount) from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " group by billnum " +
                    " order by billnum asc ";
        } else {
            strQuery = "select billnum, sum(sTotalAmount) from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                    " group by billnum " +
                    " order by billnum asc ";
        }

        GlobalMemberValues.logWrite("billsplitlog", "strQuery : " + strQuery + "\n");

        String billnum = "";
        String totalamount = "";
        String mDataL = "";

        ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
        try {
            while (cartCurosr.next()) {
                billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                totalamount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1);

                mDataL = billnum + "-JJJ-" + totalamount + "-JJJ-" + "END";
                mArrList.add(mDataL);
            }
            cartCurosr.close();
        } catch (Exception e){

        }


        return mArrList;
    }

    public void setBillPrintLn(String paramBillNum, String paramType) {
        String strQuery = "";

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        if (GlobalMemberValues.isStrEmpty(paramBillNum)) {
            if (mergednum > 0) {
//                strQuery = "select billnum from temp_salecart " +
//                        " where mergednum = '" + mergednum + "' " +
//                        " order by billnum asc limit 1 ";
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where mergednum = '" + mergednum + "' " +
                        " order by billnum asc";
            } else {
//                strQuery = "select billnum from temp_salecart " +
//                        " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
//                        " order by billnum asc limit 1 ";
                strQuery = "select top 1 billnum from temp_salecart " +
                        " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                        " order by billnum asc";
            }

            String tempBillNum = MssqlDatabase.getResultSetValueToString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempBillNum)) {
                tempBillNum = "1";
            }
            paramBillNum = tempBillNum;

            mSelectedBillNum = tempBillNum;
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
            String tableInfos = mergednum + "-JJJ-" + mTableIdx + "-JJJ-" + mSubTableNum + "-JJJ-" + paramBillNum + "-JJJ-" + "END";
            GlobalMemberValues.logWrite("phoneorderjsonlog", "tableInfos : " + tableInfos + "\n");

            String str_table_menu_total = "";

            if (!GlobalMemberValues.isStrEmpty(mTableIdx) && !GlobalMemberValues.isStrEmpty(mSubTableNum)) {
                str_table_menu_total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                        " select sum(sTotalAmount) from temp_salecart " +
                                " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " + " and svcCategoryName not in('" + GlobalMemberValues.mCommonGratuityName + "')"
                )) + "";
            }


            if (paramType.equals("L")) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalMemberValues.phoneorderPrintingJson(holdcode, "R", tableInfos);
                    String temp_itemlist = (String) jsonObject.get("saleitemlist");
                    // common gratuity 관련
//                    temp_itemlist = temp_itemlist + GlobalMemberValues.addCartLastItemForCommonGratuityUse_intable(holdcode, str_table_menu_total);
                    jsonObject.put("saleitemlist",temp_itemlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {
                    try {
                        jsonObject.put("isBillPrinteOn", "Y");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(jsonObject);
                    billcontentsLn.removeAllViews();
                    billcontentsLn.addView(getJsonLn);

                    jsonForPrinting = jsonObject;
                }

                // Bill Title
                String tableName = MssqlDatabase.getResultSetValueToString(" select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(mTableIdx, "T", "") + "' ");
                tableName = tableName + " (Bill " + TableSaleBillSplit.getBillNumString(paramBillNum) + ")";
                billtitletv.setText(tableName);
            } else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalMemberValues.phoneorderPrintingJson(holdcode, "R", tableInfos);
                    String temp_itemlist = (String) jsonObject.get("saleitemlist");
                    // common gratuity 관련
//                    temp_itemlist = temp_itemlist + GlobalMemberValues.addCartLastItemForCommonGratuityUse_intable(holdcode, str_table_menu_total);
                    jsonObject.put("saleitemlist",temp_itemlist);
                    GlobalMemberValues.TableBillPrinting(holdcode, "R", tableInfos, jsonObject);

                    // Json 생성용.
                    LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (isMainIn.equals("Y")){
                    if (GlobalMemberValues.isBillPrintPopupOpen()){
                        MainMiddleService.initList();
                        finishActivity();
                    } else {

                    }
                }

            }
        }
    }

    public void allBillsPrint() {
        String strQuery = "";

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        if (mergednum > 0) {
            strQuery = "select distinct holdcode, billnum from temp_salecart " +
                    " where mergednum = '" + mergednum + "' order by billnum asc ";
        } else {
            strQuery = "select distinct holdcode, billnum from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' order by billnum asc ";
        }

        ResultSet tempCursor;
        tempCursor = MssqlDatabase.getResultSetValue(strQuery);
        try {
            while (tempCursor.next()) {
                String getHoldcode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);
                String getBillNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,1), 1);

                if (!GlobalMemberValues.isStrEmpty(getHoldcode) && !GlobalMemberValues.isStrEmpty(getBillNum)) {
                    String tableInfos = mergednum + "-JJJ-" + mTableIdx + "-JJJ-" + mSubTableNum + "-JJJ-" + getBillNum + "-JJJ-" + "END";
                    GlobalMemberValues.logWrite("phoneorderprintlog", "tableInfos : " + tableInfos + "\n");
                    try {
                        GlobalMemberValues.phoneorderPrinting(getHoldcode, "R", tableInfos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            tempCursor.close();
        } catch (Exception e){

        }

    }

    // 짧게(일반적인) 한번 클릭할 때
    AdapterView.OnItemClickListener lvClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (m_arr_string2.size() > 0) {
                String billData = m_arr_string2.get(position).toString();
                // GlobalMemberValues.logWrite("arraySaleListLog", "marr : " + billData + "\n");

                String[] item_string = billData.split("-JJJ-");

                String billNum = item_string[0];

                if (!GlobalMemberValues.isStrEmpty(billNum)) {
                    if (mSelectedBillNum.equals(billNum)) {
                        //TableSaleBillSplit.mSelectedBillNum = "";
                    } else {
                        mSelectedBillNum = billNum;
                    }
                    setBillList();

                    setBillPrintLn(billNum, "L");
                }

                GlobalMemberValues.logWrite("billclicklog", "mSelectedBillNum : " + mSelectedBillNum + "\n");
            }
        }
    };

    public void finishActivity() {
        mSelectedBillNum = "";
        if(isMainIn.equals("Y")){
        } else {
            TableSaleMain.mSelectedTablesArrList.clear();
        }
        mActivity.finish();
        isMainIn = "";

    }
}

