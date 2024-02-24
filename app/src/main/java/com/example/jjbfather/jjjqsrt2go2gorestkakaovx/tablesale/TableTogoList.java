package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class TableTogoList extends Activity {
    private LinearLayout parentLn;
    private Intent mIntent;
    private ListView listview_floor, listview_table;
    private ImageButton btn_sort, btn_close;
    private Button btn_checklist_allchecks;
    private TextView TV_floor_totalAmount;
    private double d_floor_totalAmount = 0.00;
    private String[] tableinfo;

    public GridView quick_table_grid_list;
    ArrayList<String> mAllTablesArrList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_togolist);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 90;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.checklist_main);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        viewTableSettignsForQuick();

    }

    public void viewTableSettignsForQuick() {
//        setClearSelectedTableIdx(true);
//
//        // 초기화
//        table_main_view.removeAllViews();

        GlobalMemberValues gm = new GlobalMemberValues();
        String[] tableinfo = gm.getRestaurantTableForQuick();

        gm.s_str_tableinfo = null;
        gm.s_str_tableinfo = tableinfo;

        // 저장된 테이블이 있을 경우에만..
        if (tableinfo != null && tableinfo.length > 0) {
//            viewTableSettingsTypeForQuick(tableinfo);



            QuickTable_GridListAdapter adapter1 = new QuickTable_GridListAdapter(this, tableinfo);
            quick_table_grid_list.setAdapter(adapter1);

        }
    }

    public class QuickTable_GridListAdapter implements ListAdapter {
        public Context context;
        public String[] itemList;

        public QuickTable_GridListAdapter(Context context, String[] itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return this.itemList.length;
        }

        @Override
        public Object getItem(int position) {
            return this.itemList[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.quick_table_grid_row, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 120));

            LinearLayout qt_row = view.findViewById(R.id.quick_table_row_ln);
            TextView textview2 = view.findViewById(R.id.quick_table_text2);
            TextView textview1 = view.findViewById(R.id.quick_table_text1);
            TextView textview3 = view.findViewById(R.id.quick_table_text3);
            TextView textview4 = view.findViewById(R.id.quick_table_text4);

            String[] tablearr = null;
            String tableidx = "";
            String tablename = "";
            String capacity = "";
            String colorvalue = "#5351fb";
            String tabletype = "";
            String chargeridx = "";
            String pagernum = "";
            String xvaluerate = "";
            String yvaluerate = "";
            String tableordercnt = "";
            String boxtype = "";
            String boxkinds = "";
            String customername = "";
            String customerphonenum = "";

            tablearr = itemList[position].split("-JJJ-");

            tableidx = tablearr[0];
            tablename = tablearr[1];
            capacity = tablearr[2];
            colorvalue = tablearr[3];
            tabletype = tablearr[4];
            chargeridx = tablearr[5];
            pagernum = tablearr[6];
            xvaluerate = tablearr[7];
            yvaluerate = tablearr[8];
            boxtype = tablearr[9];
            boxkinds = tablearr[10];
            customername = tablearr[11];
            customerphonenum = tablearr[12];

            if (GlobalMemberValues.isStrEmpty(boxtype)) {
                boxtype = "0";
            }
            if (GlobalMemberValues.isStrEmpty(boxkinds)) {
                boxkinds = "table";
            }

            if (GlobalMemberValues.isStrEmpty(colorvalue)) {
                colorvalue = "#5351fb";
            }

            if (GlobalMemberValues.isStrEmpty(capacity)) {
                capacity = "0";
            }

            textview1.setText(tablename);


            tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
            mAllTablesArrList.add(tableidx);

            // 테이블이 split 되어 있는지 확인
            int subTableCount = TableSaleMain.getTableSplitCount(tableidx);

            if (subTableCount > 0 && subTableCount > 1) {
                String tempSubTableStr = subTableCount + "ea";
                tablename = tempSubTableStr + " Split";
//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                textview1.setText(tablename);
            }

            String tableTxtColor = "#ffffff";

            // mergednum -------------------------------------------------------------------------
            int mergednum = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + tableidx + "%' ")
            );
            textview2.setTextSize(30);
            textview2.setText(customername);
            textview3.setText(customerphonenum);


            // 주문시간 add
            String strQuery_temp = " select top 1 wdate from temp_salecart " +
                    " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' order by wdate asc";

            ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
            String ordereddateValue = "";
            try {
                while (timeCursor.next()){
                    String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                    ordereddateValue = getTime;


                }
            } catch (Exception e) {
            }

            String date1 = ordereddateValue;

            try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date = new Date();
                String nowDate = simpleDateFormat.format(date);
//                    //현재시간 구하기 (시작 시간)
//
                Date inputDate = simpleDateFormat.parse(nowDate);
                Date currDate = simpleDateFormat.parse(date1);
                // 시작 시간, 종료 시간 파싱해서 계산할 수 있게 바꿈

                long hour = (inputDate.getTime() - currDate.getTime()) / (1000) / 3600 % 24;
                long min = (inputDate.getTime() - currDate.getTime()) / (1000) / 60 % 60;
                long second = (inputDate.getTime() - currDate.getTime()) / (1000) % 60;
                // (시작 시간 - 종료시간)  시간 차이나는 부분을 시, 분, 초로 얻음


//                    TextView orderTime_tv = table_row3.findViewById(R.id.ordereddate);
                textview4.setText(hour +":"+ min +":"+ second);
                Log.e("byullbam_test", ordereddateValue + "-" + hour +":"+ min +":"+ second);
            }
            catch(ParseException e) {
                // 예외 처리
            }


//            textview4.setText("");
            if (mergednum > 0) {
                if (TableSaleMain.getTableCountByHoldcode(TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum)) == 1
                        && TableSaleMain.getTableCountByTableidxInMergedTable(tableidx) < 2) {
                    String newHoldCode = GlobalMemberValues.makeHoldCode();
                    Vector<String> updateVector = new Vector<String>();
                    String strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '0', isCloudUpload = 0  " +
                            " where tableidx like '%" + tableidx + "%' ";
                    updateVector.addElement(strQuery);

                    strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tableidx + "%' ";
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
                        GlobalMemberValues gm = new GlobalMemberValues();
                        if (gm.isPOSWebPay() &&
                                (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                            // 장바구니데이터 클라우드 전송
                            GlobalMemberValues.setSendCartToCloud(TableSaleMain.mContext, TableSaleMain.mActivity);
                            // 장바구니데이터 삭제 클라우드 전송
                            GlobalMemberValues.setSendCartDeleteToCloud(TableSaleMain.mContext, TableSaleMain.mActivity);
                        }
                    }
                } else {


                    String mergednumstr = "0" + mergednum;
                    mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                    tablename = "Merged\n(" + mergednumstr + ")";

                    textview1.setText(tablename);
//                    textview1.setTextColor(Color.parseColor(tableTxtColor));
                    int tablecolornum = mergednum % 7;
//                    textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                    textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                    textview2.setText("   Merged Table   ");
                    textview2.setTextSize(20);
                    textview2.setTextColor(Color.parseColor("#ffffff"));
                    qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
                }
            } else {
                if (TableSaleMain.getTableCountByTableidxInMergedTable(tableidx) > 1) {
                    String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(tableidx);
                    if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {




                        mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(tableidx);

                        String mergednumstr = "0" + mergednum;
                        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                        tablename = "Merged\n(" + mergednumstr + ")";

                        textview1.setText(tablename);
//                       textview1.setTextColor(Color.parseColor(tableTxtColor));

                        int tablecolornum = mergednum % 7;
//                        textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                        textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                        textview2.setText("   Merged Table   ");
                        textview2.setTextSize(20);
                        textview2.setTextColor(Color.parseColor("#ffffff"));
                        qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
                    }
                } else {
                    Vector<String> deleteVec = new Vector<String>();
                    String strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tableidx + "%' ";
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

            // 해당 테이블 주문메뉴 수
            tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' ");
            int tableordercnt_int = GlobalMemberValues.getIntAtString(tableordercnt);

            GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

            if (tableordercnt_int > 0){
                qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_sel);
            } else {
                qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_not_sel);
            }


            if (GlobalMemberValues.isDeviceSunmiFromDB()){
                textview1.setTextSize(10);
                textview2.setTextSize(22);
                textview3.setTextSize(16);
            }

            if (GlobalMemberValues.isDevicePAXFromDB()){
                textview1.setTextSize(10);
                textview2.setTextSize(22);
                textview3.setTextSize(16);
            }


            final String finalTablename = tablename;
            final String finalTableidx = tableidx;
            final String finalTableTxtColor = tableTxtColor;
            qt_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BillSplitMerge.setInitValuesForBillPay();

//                    tableCell_singleClick(view, qt_row, qt_row, finalTableidx,
//                            finalTablename, finalTableTxtColor, tableordercnt_int, "Q");
                }
            });

            // table double touch


            // table longclick
            qt_row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    tableCell_longClick(v);
                    return false;
                }
            });
            qt_row.setTag(tableidx);



            return view;

//            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

    }

}
