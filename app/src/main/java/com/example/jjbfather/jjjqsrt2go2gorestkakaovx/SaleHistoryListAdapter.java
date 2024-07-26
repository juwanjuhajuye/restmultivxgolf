package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaleHistoryListAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<String> listViewItemList = new ArrayList<String>();

    // ListViewAdapter의 생성자
    public SaleHistoryListAdapter(ArrayList<String> arrayList) {
        this.listViewItemList = arrayList;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.salehistory_list_item, parent, false);
        }
        String[] item_string = listViewItemList.get(position).split("-JJJ-");

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        // 04242023
        LinearLayout lineLn = (LinearLayout) convertView.findViewById(R.id.salehistorylineLn);
        lineLn.setBackgroundColor(Color.parseColor("#ffffff"));

        TextView text1 = (TextView) convertView.findViewById(R.id.salehistory_list_text1);
//        text1.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text2 = (TextView) convertView.findViewById(R.id.salehistory_list_text2);
//        text2.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text3 = (TextView) convertView.findViewById(R.id.salehistory_list_text3);
//        text3.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text4 = (TextView) convertView.findViewById(R.id.salehistory_list_text4);
//        text4.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text_tablenum = (TextView) convertView.findViewById(R.id.salehistory_list_table_num);

        TextView text5 = (TextView) convertView.findViewById(R.id.salehistory_list_text5);
//        text5.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text6 = (TextView) convertView.findViewById(R.id.salehistory_list_text6);
//        text6.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text_stcode = (TextView) convertView.findViewById(R.id.salehistory_list_text_stcode);
//        text_stcode.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView tip_text = (TextView) convertView.findViewById(R.id.salehistory_list_text7_tip);
//        tip_text.setTextSize(GlobalMemberValues.globalAddFontSize()
//                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text6_1 = (TextView) convertView.findViewById(R.id.salehistory_list_text6_1);
        TextView text6_2 = (TextView) convertView.findViewById(R.id.salehistory_list_text6_2);

        TextView all_total_txt = (TextView)convertView.findViewById(R.id.salehistory_list_text_alltotal);

        TextView text_gratuity = (TextView) convertView.findViewById(R.id.salehistory_list_text_gratuity);


        TextView tip_add = (TextView) convertView.findViewById(R.id.salehistory_list_button_tipadd);
        tip_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalMemberValues.checkEmployeePermission(
                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<3>")) {
                    GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                    return;
                }

                // 배치된 주문인지 확인
//                int tempBatchSaleCount = 0;
//                tempBatchSaleCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
//                        "select count(idx) from salon_sales where salesCode = '" + item_string[2] + "' " +
//                                " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
//                ));
//                if (tempBatchSaleCount > 0) {
//                    GlobalMemberValues.displayDialog(context, "Warning", "Batched Order can not be void", "Close");
//                    return;
//                }



                String salonSalesDetailQuery = "select categoryName, itemIdx, itemName, qty, salesBalPriceAmount, taxAmount, totalAmount, pointAmount, " +
                        " employeeIdx, employeeName, saveType, isQuickSale, status, idx, " +
                        " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, memotokitchen " +
                        " from salon_sales_detail " +
                        " where delyn = 'N' " +
                        " and salesCode = '" + item_string[2] + "' " +
                        " order by employeeName asc, idx asc ";
//                    salonSalesDetailCursor = dbInit.dbExecuteRead(salonSalesDetailQuery);   // todo
                ResultSet salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
                String mSelectedSalonSalesDetailIdx = "";
                int salonSalesDetailStatus = 0;
                try {
                    while (salonSalesDetailCursor.next()) {
                        String tempDTidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13), 1);
                        if (!GlobalMemberValues.isStrEmpty(tempDTidx)) {
                            mSelectedSalonSalesDetailIdx = tempDTidx;
                        }
                        salonSalesDetailStatus = GlobalMemberValues.resultDB_checkNull_int(salonSalesDetailCursor,12);
                    }
                } catch (Exception e){
                    Log.e("",e.toString());
                }

                //07052024 close resultset
                try {
                    if(!salonSalesDetailCursor.isClosed()){
                        salonSalesDetailCursor.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (salonSalesDetailStatus > 0) {
                    GlobalMemberValues.displayDialog(context, "Warning", "This item was void or returned", "Close");
                    return;
                }

                Intent tipAdjustmentIntent = new Intent(context, SaleHistoryTipAdjustment.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                tipAdjustmentIntent.putExtra("salesCode", item_string[2]);
                tipAdjustmentIntent.putExtra("salonSalesDetailIdx", mSelectedSalonSalesDetailIdx);
                tipAdjustmentIntent.putExtra("is_history_list",true);
                tipAdjustmentIntent.putExtra("list_row_number",position);
                // -------------------------------------------------------------------------------------
                context.startActivity(tipAdjustmentIntent);

                GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 1" + "\n");
            }
        });

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        TextView text_logview = convertView.findViewById(R.id.salehistory_list_button_viewlog);
        text_logview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LogHistory.class);
                intent.putExtra("salescode",item_string[2]);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                // -------------------------------------------------------------------------------------
                context.startActivity(intent);
            }
        });



        // 해당 주문이 void 처리된 주문인지 확인
        int voidOrderCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from salon_sales_detail where salesCode = 'V" + item_string[2].substring(1) + "' "
        ));

        int refundOrderCnt = 0;
        if (voidOrderCnt == 0) {
            refundOrderCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(idx) from salon_sales_detail where salesCode = 'C" + item_string[2].substring(1) + "' "
            ));
        }

        String tempSaleDate = item_string[4];

        SimpleDateFormat old_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String new_date = "";
        try {
            Date old_date = old_format.parse(tempSaleDate);
            new_date = new_format.format(old_date);
        } catch (ParseException e){
            e.printStackTrace();
        }

        tempSaleDate = new_date;


        if (voidOrderCnt > 0) {
            tempSaleDate = "<font color=\"#f82823\">[VOID]</font> " + tempSaleDate;
        } else {
            if (refundOrderCnt > 0) {
                tempSaleDate = "<font color=\"#f82823\">[RETURN]</font> " + tempSaleDate;
            }
        }

        String salonSalesTipQuery = "select sum(usedCash + usedCard) " +
                " from salon_sales_tip " +
                " where salesCode = '" + item_string[2] + "'" +
                " and (not(usedCash = 0) or not(usedCard = 0)) ";
//        String tempTipAmount = dbInit.dbExecuteReadReturnString(salonSalesTipQuery);
        String tempTipAmount = MssqlDatabase.getResultSetValueToString(salonSalesTipQuery);
        double tempTipAmountDbl = GlobalMemberValues.getDoubleAtString(tempTipAmount);
        if (tempTipAmountDbl > 0) {
            tempTipAmount = GlobalMemberValues.getStringFormatNumber(tempTipAmountDbl, "2");

            // 04242023
            lineLn.setBackgroundColor(Color.parseColor("#e0eefa"));
        } else {
            tempTipAmount = "0.00";

            lineLn.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        String salonSalesGratuityQuery = "select sum(totalAmount) from salon_sales_detail " +
                " where salesCode = '" + item_string[2] + "'" +
                " and itemName = '" + GlobalMemberValues.mCommonGratuityName + "'";
        String tempGratuityAmount = MssqlDatabase.getResultSetValueToString(salonSalesGratuityQuery);
        double tempGratuityAmountDbl = GlobalMemberValues.getDoubleAtString(tempGratuityAmount);
        if (tempGratuityAmountDbl > 0) {
            tempGratuityAmount = GlobalMemberValues.getStringFormatNumber(tempGratuityAmountDbl, "2");
        } else {
            tempGratuityAmount = "0.00";
        }

        String salonSalesDetailQuery = "select tablename" +
                " from salon_sales_detail " +
                " where delyn = 'N' " +
                " and salesCode = '" + item_string[2] + "'";
        String tempTableNumber = MssqlDatabase.getResultSetValueToString(salonSalesDetailQuery);
        if(GlobalMemberValues.isStrEmpty(tempTableNumber)){
            tempTableNumber = "missing";
        }

        // 아이템 내 각 위젯에 데이터 반영
        text1.setText(item_string[0]);
        text2.setText(item_string[1]);
        text3.setText(item_string[2]);
        text4.setText(item_string[3]);
        text5.setText(Html.fromHtml(tempSaleDate));
        text6.setText(item_string[5]);
        text_stcode.setText(item_string[6]);
        text6_1.setText(item_string[9]);
        text6_2.setText(item_string[8]);

        text_tablenum.setText(tempTableNumber);

        tip_text.setText(tempTipAmount);

        text_gratuity.setText(tempGratuityAmount);

        double allTotal = GlobalMemberValues.getDoubleAtString(item_string[3]) + GlobalMemberValues.getDoubleAtString(tempTipAmount);

        all_total_txt.setText(GlobalMemberValues.getStringFormatNumber(allTotal, "2"));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
}
