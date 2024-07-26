package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class TableDetailInfo extends Activity {
    Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    TextView table_main_popup_top;
    TextView table_info_name,table_info_capacity,table_info_menu_total;
    TextView infotitle1,infotitle2,infotitle3, infotitle4;
    TextView dateintv, etcinfotv;

    String str_table_name, str_table_capacity, str_table_menu_total;

    Button selecteddeletebtn, alldeletebtn;

    private ImageButton btn_close_img;
    ListView table_main_split_item_list;

    Intent mIntent;

    String mTableIdx = "";
    String mSubTableNum = "1";

    ArrayList<String> m_arr_string;

    BillSplitItemAdapter billSplitItemAdapter;

    LinearLayout table_detail_info_list_main;

    Button detailinfo_print;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_detail_info);
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
        GlobalMemberValues.logWrite("tbdtinfo", "tableidx : " + mTableIdx + "\n");
        GlobalMemberValues.logWrite("tbdtinfo", "subtablenum : " + mSubTableNum + "\n");

        String strQuery = "";

        str_table_name = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(mTableIdx, "T", "") + "' ");

        str_table_capacity = TableSaleMain.getTablePeopleCntByTableIdx(mTableIdx) + "";

        str_table_menu_total = "";
        if (!GlobalMemberValues.isStrEmpty(mTableIdx) && !GlobalMemberValues.isStrEmpty(mSubTableNum)) {
            str_table_menu_total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                    " select sum(sTotalAmount) from temp_salecart " +
                            " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' "
            )) + "";
        }

        // Ordered Date ---------------------------------------------------------------------------------------
        strQuery = " select top 1 datepart(hour, wdate), datepart(minute, wdate) from temp_salecart " +
                " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(mTableIdx, TableSaleMain.mSubTableNum) + "' order by wdate asc";
        GlobalMemberValues.logWrite("dateintimejjj", "strQuery : " + strQuery + "\n");

        ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery);
        String ordereddateValue = "";
        try {
            while (timeCursor.next()){
                String getHour = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                String getMin = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,1), 1);

                String ampmstr = "AM";
                if (GlobalMemberValues.getIntAtString(getHour) > 11) {
                    ampmstr = "PM";
                    getHour = (GlobalMemberValues.getIntAtString(getHour) - 12) + "";
                }

                ordereddateValue = getHour + ":" + getMin + " " + ampmstr;
            }
        } catch (Exception e) {
        }

        //07052024 close resultset
        try {
            if(!timeCursor.isClosed()){
                timeCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // -------------------------------------------------------------------------------------------------

        TableSaleMain.mSelectedIdxArrListInCart = new ArrayList<String>();
        TableSaleMain.mSelectedIdxArrListInCart.clear();

        table_main_popup_top = (TextView)findViewById(R.id.table_main_popup_top);
        table_main_popup_top.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_main_popup_top.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        table_info_name = (TextView)findViewById(R.id.table_info_name);
        table_info_name.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_info_name.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        table_info_name.setText(str_table_name);

        table_info_capacity = (TextView)findViewById(R.id.table_info_capacity);
        table_info_capacity.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_info_capacity.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        table_info_capacity.setText(str_table_capacity);

        table_info_menu_total = (TextView)findViewById(R.id.table_info_menu_total);
        table_info_menu_total.setTextSize(GlobalMemberValues.globalAddFontSize()
                + table_info_menu_total.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        table_info_menu_total.setText(str_table_menu_total);

        dateintv = (TextView)findViewById(R.id.dateintv);
        dateintv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + dateintv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        dateintv.setText(ordereddateValue);

        etcinfotv = (TextView)findViewById(R.id.etcinfotv);
        etcinfotv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + etcinfotv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        etcinfotv.setText("");


        infotitle1 = (TextView)findViewById(R.id.infotitle1);
        infotitle1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + infotitle1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        infotitle2 = (TextView)findViewById(R.id.infotitle2);
        infotitle2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + infotitle2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        infotitle3 = (TextView)findViewById(R.id.infotitle3);
        infotitle3.setTextSize(GlobalMemberValues.globalAddFontSize()
                + infotitle3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        infotitle4 = (TextView)findViewById(R.id.infotitle4);
        infotitle4.setTextSize(GlobalMemberValues.globalAddFontSize()
                + infotitle4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        selecteddeletebtn = (Button) findViewById(R.id.selecteddeletebtn);
        selecteddeletebtn.setTextSize(GlobalMemberValues.globalAddFontSize()
                + selecteddeletebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        selecteddeletebtn.setOnClickListener(clickListener);

        alldeletebtn = (Button) findViewById(R.id.alldeletebtn);
        alldeletebtn.setTextSize(GlobalMemberValues.globalAddFontSize()
                + alldeletebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        alldeletebtn.setOnClickListener(clickListener);


        btn_close_img = (ImageButton)findViewById(R.id.table_main_bill_split_close);
        btn_close_img.setOnClickListener(clickListener);

        table_main_split_item_list = (ListView)findViewById(R.id.table_main_split_item_list);




        // mergednum -------------------------------------------------------------------------
        String tableTxtColor = "#797773";
        String tablename = "";

        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
        );
        if (mergednum > 0) {
            if (TableSaleMain.getTableCountByHoldcode(TableSaleMain.getHoldCodeByTableidx(mTableIdx, TableSaleMain.mSubTableNum)) == 1
                    && TableSaleMain.getTableCountByTableidxInMergedTable(mTableIdx) < 2) {
                String newHoldCode = GlobalMemberValues.makeHoldCode();
                Vector<String> updateVector = new Vector<String>();
                strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '0', isCloudUpload = 0  " +
                        " where tableidx like '%" + mTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + mTableIdx + "%' ";
                updateVector.addElement(strQuery);

                for (String tempQuery : updateVector) {
                    //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(updateVector);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    //return;
                } else {
                    // 클라우드 업로드
                    //uploadTableDataCloudExe();
                }
            } else {
                tableTxtColor = "#ffffff";

                String mergednumstr = "0" + mergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tablename = "Merged - (" + mergednumstr + ")";

                etcinfotv.setBackgroundColor(Color.parseColor("#2c4ff1"));
                etcinfotv.setTextColor(Color.parseColor(tableTxtColor));
                etcinfotv.setText(tablename);
            }
        } else {
            if (TableSaleMain.getTableCountByTableidxInMergedTable(mTableIdx) > 1) {
                String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(mTableIdx);
                if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {


                    tableTxtColor = "#ffffff";

                    mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(mTableIdx);

                    String mergednumstr = "0" + mergednum;
                    mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                    tablename = "Merged - (" + mergednumstr + ")";



                    int tablecolornum = mergednum % 7;

                    etcinfotv.setText(tablename);
                    etcinfotv.setTextColor(Color.parseColor(tableTxtColor));
                    etcinfotv.setBackgroundColor(Color.parseColor("#2c4ff1"));
                }
            } else {
                Vector<String> deleteVec = new Vector<String>();
                strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + mTableIdx + "%' ";
                deleteVec.addElement(strQuery);

                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(deleteVec);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    //return;
                } else {
                }
            }
        }

        // -----------------------------------------------------------------------------------

        // 테이블이 split 되어 있는지 확인
        int subTableCount = TableSaleMain.getTableSplitCount(mTableIdx);

        if (subTableCount > 0 && subTableCount > 1) {
            String tempSubTableStr = subTableCount + "ea";
            for (int jjj = 0; jjj < subTableCount; jjj++) {
                tempSubTableStr += " " + (jjj + 1);
            }

            etcinfotv.setText("Split : " + tempSubTableStr);
            etcinfotv.setTextColor(Color.parseColor(tableTxtColor));
            etcinfotv.setBackgroundColor(Color.parseColor("#2c4ff1"));
        }

        //
        table_detail_info_list_main = findViewById(R.id.table_detail_info_list_main);
//        table_detail_info_list_main.setOnClickListener(clickListener);
        detailinfo_print = findViewById(R.id.detailinfo_print);
        detailinfo_print.setOnClickListener(clickListener);


        setSaleCartList();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detailinfo_print : {
                    Intent intent = new Intent(mContext.getApplicationContext(),TableSaleBillPrint.class);
                    intent.putExtra("selectedTableIdx", mTableIdx);
                    intent.putExtra("selectedSubTableNum", mSubTableNum);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    if (GlobalMemberValues.isBillPrintPopupOpen()){
                        finish();
                    }
                    break;
                }
                case R.id.table_main_bill_split_close : {
                    finish();

                    break;
                }
                case R.id.selecteddeletebtn : {
                    if (TableSaleMain.mSelectedIdxArrListInCart == null) {
                        GlobalMemberValues.displayDialog(mContext, "", "Select a item to delete", "Close");
                        return;
                    } else {
                        if (TableSaleMain.mSelectedIdxArrListInCart.size() == 0) {
                            GlobalMemberValues.displayDialog(mContext, "", "Select a item to delete", "Close");
                            return;
                        }
                    }

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Bill Index")
                                .setMessage("Do you want to delete the selected items?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteSelected();
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
                case R.id.alldeletebtn : {

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Bill Merge")
                                .setMessage("Do you want to delete all?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteAll();
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

    public void deleteSelected() {
        if (TableSaleMain.mSelectedIdxArrListInCart != null & TableSaleMain.mSelectedIdxArrListInCart.size() > 0) {
            String strQuery = "";
            Vector<String> updateVector = new Vector<String>();
            for (String tempCartIdx : TableSaleMain.mSelectedIdxArrListInCart) {
                if (!GlobalMemberValues.isStrEmpty(tempCartIdx)) {
                    strQuery = " delete from temp_salecart where idx = '" + tempCartIdx + "' ";
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
                TableSaleMain.mSelectedIdxArrListInCart.clear();
            }
        }

        //                    GlobalMemberValues.logWrite("billsplitlog2", "selectedItemSpinner : " + selectedItemSpinner + "\n");
//                    GlobalMemberValues.logWrite("billsplitlog2", "mSelectedIdxArrListInCart : " + TableSaleMain.mSelectedIdxArrListInCart.toString() + "\n");
    }

    public void deleteAll() {
        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();


        strQuery = "delete from temp_salecart " +
                " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '" + mSubTableNum + "' ";

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
            TableSaleMain.mSelectedIdxArrListInCart.clear();
            TableSaleMain.setClearCartOfTableByTableidx(mTableIdx);
        }
    }


    public void setSaleCartList() {
        String tempQuery = " select count(idx) from temp_salecart where tableidx like '%" + mTableIdx + "%' ";

        GlobalMemberValues.logWrite("mssqlitemlog", "tempQuery : " + tempQuery + "\n");

        int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
        }

        GlobalMemberValues.logWrite("mssqlitemlog", "tableOrderCount : " + tableOrderCount + "\n");

        if (tableOrderCount == 0) {
            TableSaleMain.setClearCartOfTableByTableidx(mTableIdx);
        }

        m_arr_string = getItemList(mTableIdx, mSubTableNum);

        billSplitItemAdapter = new BillSplitItemAdapter(m_arr_string);
        table_main_split_item_list.setAdapter(null);      // 초기화
        table_main_split_item_list.setAdapter(billSplitItemAdapter);
    }

    public static ArrayList<String> getItemList(String mTableIdx, String mSubTableNum) {
        String tempQuery = "select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ";
        int mergednum = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(tempQuery)
        );

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            mergednum = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
        }

        String strQuery = "";
        ArrayList<String> mArrList = new ArrayList<String>();
        mArrList.clear();

        if (mergednum > 0) {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt, empName from temp_salecart " +
                    " where mergednum = '" + mergednum + "' " +
                    " order by svcName desc ";
        } else {
            strQuery = "select idx, svcName, sQty, billnum, optionTxt, empName from temp_salecart " +
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
        String empName = "";

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            GlobalMemberValues.logWrite("mssqlitemlog", "여기1" + "\n");
            ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (cartCurosr.next()) {
                    itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,0), 1);
                    itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,1), 1);
                    itemQty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,2), 1);
                    billNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,3), 1);
                    optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,4), 1);
                    empName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(cartCurosr,5), 1);

                    mDataL = itemIdx + "-JJJ-" + itemName+ "-JJJ-" + itemQty + "-JJJ-" + billNum + "-JJJ-" + optionTxt + "-JJJ-" + empName + "-JJJ-" + "END";

                    GlobalMemberValues.logWrite("mssqlitemlog", "data : " + mDataL + "\n");
                    mArrList.add(mDataL);
                }
                MssqlDatabase.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //07052024 close resultset
            try {
                if(!cartCurosr.isClosed()){
                    cartCurosr.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            GlobalMemberValues.logWrite("mssqlitemlog", "여기2" + "\n");

            ResultSet cartCurosr = MssqlDatabase.getResultSetValue(strQuery);
            try {
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
            } catch (Exception e){

            }

        }



        return mArrList;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
