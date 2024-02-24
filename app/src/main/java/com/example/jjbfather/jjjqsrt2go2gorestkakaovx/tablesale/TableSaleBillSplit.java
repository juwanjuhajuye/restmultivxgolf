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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class TableSaleBillSplit extends Activity {
    Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    TextView table_main_popup_top, titleTv1, titleTv2, titleTv3, billindextv, totaltv, totalamounttv;

    Button setindexbtn, mergebillbtn;

    private ImageButton btn_close_img;
    ListView table_sale_bill_split_list;
    ListView table_main_split_item_list;
    Spinner table_main_split_item_index_spinner;

    Intent mIntent;

    String mTableIdx = "";
    String mSubTableNum = "1";

    ArrayList<String> m_arr_string;
    ArrayList<String> m_arr_string2;

    BillSplitItemAdapter billSplitItemAdapter;
    BillListAdapter billListAdapter;

    String selectedItemSpinner = "A";

    ArrayList<String> mGeneralArrayListForSpinner = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_bill_split);
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

        setContent();

    }

    public void setContent(){

        TableSaleMain.mSelectedIdxArrListInCart = new ArrayList<String>();
        TableSaleMain.mSelectedIdxArrListInCart.clear();

        table_main_popup_top = (TextView)findViewById(R.id.table_main_popup_top);
        table_main_popup_top.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_main_popup_top.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView titleTv1 = (TextView)findViewById(R.id.titleTv1);
        titleTv1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + titleTv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView titleTv2 = (TextView)findViewById(R.id.titleTv2);
        titleTv2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + titleTv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView titleTv3 = (TextView)findViewById(R.id.titleTv3);
        titleTv3.setTextSize(GlobalMemberValues.globalAddFontSize()
                + titleTv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        billindextv = (TextView)findViewById(R.id.billindextv);
        billindextv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + billindextv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        totaltv = (TextView)findViewById(R.id.totaltv);
        totaltv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + totaltv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        totalamounttv = (TextView)findViewById(R.id.totalamounttv);
        totalamounttv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + totalamounttv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        setindexbtn = (Button) findViewById(R.id.setindexbtn);
        setindexbtn.setTextSize(GlobalMemberValues.globalAddFontSize()
                + setindexbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        setindexbtn.setOnClickListener(clickListener);

        mergebillbtn = (Button) findViewById(R.id.mergebillbtn);
        mergebillbtn.setTextSize(GlobalMemberValues.globalAddFontSize()
                + mergebillbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        mergebillbtn.setOnClickListener(clickListener);


        btn_close_img = (ImageButton)findViewById(R.id.table_main_bill_split_close);
        btn_close_img.setOnClickListener(clickListener);

        table_main_split_item_index_spinner = (Spinner)findViewById(R.id.table_main_split_item_index_spinner);
        String[] billIndexList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < billIndexList.length; i++) {
            mGeneralArrayListForSpinner.add(billIndexList[i]);
        }
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);

        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        table_main_split_item_index_spinner.setAdapter(mSpinnerAdapter);
        table_main_split_item_index_spinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);
        table_main_split_item_index_spinner.setSelection(0);

        table_main_split_item_list = (ListView)findViewById(R.id.table_main_split_item_list);

        table_sale_bill_split_list = (ListView)findViewById(R.id.table_main_billsplit_list);
        table_sale_bill_split_list.setOnItemClickListener(lvClick);

        setSaleCartList();
        setBillList();

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.table_main_bill_split_close : {
                    finish();

                    break;
                }
                case R.id.setindexbtn : {
                    GlobalMemberValues.logWrite("billsplitlog2", "selectedItemSpinner : " + selectedItemSpinner + "\n");
                    GlobalMemberValues.logWrite("billsplitlog2", "mSelectedIdxArrListInCart : " + TableSaleMain.mSelectedIdxArrListInCart.toString() + "\n");
                    if (TableSaleMain.mSelectedIdxArrListInCart == null) {
                        GlobalMemberValues.displayDialog(mContext, "", "Select a item to change", "Close");
                        return;
                    } else {
                        if (TableSaleMain.mSelectedIdxArrListInCart.size() == 0) {
                            GlobalMemberValues.displayDialog(mContext, "", "Select a item to change", "Close");
                            return;
                        }
                    }

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Bill Index")
                                .setMessage("Do you want to change it?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                setBillNum();
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
                case R.id.mergebillbtn : {

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Bill Merge")
                                .setMessage("Do you want to merge bills?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mergeBill();
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

            }
        }
    };

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite("billsplitlog2", "Selected Spinner Item : " + selectedItemSpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    public void setBillNum() {
        if (TableSaleMain.mSelectedIdxArrListInCart != null & TableSaleMain.mSelectedIdxArrListInCart.size() > 0) {
            String strQuery = "";
            Vector<String> updateVector = new Vector<String>();
            for (String tempCartIdx : TableSaleMain.mSelectedIdxArrListInCart) {
                if (!GlobalMemberValues.isStrEmpty(tempCartIdx)) {
                    strQuery = " update temp_salecart set billnum = '" + TableSaleBillSplit.getBillNumIndex(selectedItemSpinner) + "' where idx = '" + tempCartIdx + "' ";
                    updateVector.addElement(strQuery);
                }
            }
            for (String tempQuery : updateVector) {
                //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(updateVector);
            if (returnResult == "N" || returnResult == "") {
                //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                //return;
            } else {
                setSaleCartList();
                setBillList();
                TableSaleMain.mSelectedIdxArrListInCart.clear();
            }
        }

        //                    GlobalMemberValues.logWrite("billsplitlog2", "selectedItemSpinner : " + selectedItemSpinner + "\n");
//                    GlobalMemberValues.logWrite("billsplitlog2", "mSelectedIdxArrListInCart : " + TableSaleMain.mSelectedIdxArrListInCart.toString() + "\n");
    }

    public void mergeBill() {
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "update temp_salecart set billnum = '1' " +
                    " where mergednum = '" + mergednum + "' ";
        } else {
            strQuery = "update temp_salecart set billnum = '1' " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' ";
        }

        Vector<String> updateVector = new Vector<String>();
        updateVector.addElement(strQuery);

        for (String tempQuery : updateVector) {
            GlobalMemberValues.logWrite("mergebillog", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = MssqlDatabase.executeTransaction(updateVector);
        if (returnResult == "N" || returnResult == "") {
            //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            //return;
        } else {
            setSaleCartList();
            setBillList();
            TableSaleMain.mSelectedIdxArrListInCart.clear();
        }

    }


    public void setSaleCartList() {
        m_arr_string = getSaleHistoryList();

        billSplitItemAdapter = new BillSplitItemAdapter(m_arr_string);
        table_main_split_item_list.setAdapter(null);      // 초기화
        table_main_split_item_list.setAdapter(billSplitItemAdapter);
    }

    public void setBillList() {
        m_arr_string2 = getBillList();

        billListAdapter = new BillListAdapter(m_arr_string2);
        table_sale_bill_split_list.setAdapter(null);      // 초기화
        table_sale_bill_split_list.setAdapter(billListAdapter);
    }

    public ArrayList<String> getSaleHistoryList() {
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " order by svcName desc ";
        } else {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt from temp_salecart " +
                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' " +
                    " order by svcName desc ";
        }

        GlobalMemberValues.logWrite("billsplitlog", "strQuery : " + strQuery + "\n");

        String itemIdx = "";
        String itemName = "";
        String itemQty = "";
        String billNum = "";
        String optionTxt = "";
        String mDataL = "";

        ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
        try{
            while (cartCurosr.next()) {
                itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1);
                itemQty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,2), 1);
                billNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,3), 1);
                optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,4), 1);

                mDataL = itemIdx + "-JJJ-" + itemName+ "-JJJ-" + itemQty + "-JJJ-" + billNum + "-JJJ-" + optionTxt + "-JJJ-" + "END";
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
        totalamounttv.setText("$ " + GlobalMemberValues.getCommaStringForDouble(totalAmount + ""));

        return mArrList;
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
        try{
            while (cartCurosr.next()) {
                billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                totalamount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1);

                mDataL = billnum + "-JJJ-" + totalamount + "-JJJ-" + "END";
                mArrList.add(mDataL);
            }
            cartCurosr.close();
        }catch (Exception e){

        }



        return mArrList;
    }

    public static String getBillNumString(String paramBillNum) {
        String billNumStr = "A";
        switch (paramBillNum) {
            case "1" : {
                billNumStr = "A";
                break;
            }
            case "2" : {
                billNumStr = "B";
                break;
            }
            case "3" : {
                billNumStr = "C";
                break;
            }
            case "4" : {
                billNumStr = "D";
                break;
            }
            case "5" : {
                billNumStr = "E";
                break;
            }
            case "6" : {
                billNumStr = "F";
                break;
            }
            case "7" : {
                billNumStr = "G";
                break;
            }
            case "8" : {
                billNumStr = "H";
                break;
            }
            case "9" : {
                billNumStr = "I";
                break;
            }
            case "10" : {
                billNumStr = "J";
                break;
            }
        }

        return billNumStr;
    }

    public static String getBillNumIndex(String paramBillIndex) {
        String billNumStr = "1";
        switch (paramBillIndex) {
            case "A" : {
                billNumStr = "1";
                break;
            }
            case "B" : {
                billNumStr = "2";
                break;
            }
            case "C" : {
                billNumStr = "3";
                break;
            }
            case "D" : {
                billNumStr = "4";
                break;
            }
            case "E" : {
                billNumStr = "5";
                break;
            }
            case "F" : {
                billNumStr = "6";
                break;
            }
            case "G" : {
                billNumStr = "7";
                break;
            }
            case "H" : {
                billNumStr = "8";
                break;
            }
            case "I" : {
                billNumStr = "9";
                break;
            }
            case "J" : {
                billNumStr = "10";
                break;
            }
        }

        return billNumStr;
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
                    setBillList();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }


}
