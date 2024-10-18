package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.ConfigPGInfo;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DateMethodClass;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.LogHistory;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.LogsSave;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TableCheckList extends Activity {
    public static Activity mActivity;
    // 05.20.2022
    public static Context mContext;

    private LinearLayout parentLn;
    private Intent mIntent;
    private ListView listview_floor, listview_table;
    private ImageButton btn_sort, btn_close;
    private Button btn_loglist;
    private Button btn_checklist_allchecks;
    private TextView TV_floor_totalAmount;
    private String[] tableinfo;
    public Button btn_checklist_clearchecked;

    // 05.20.2022
    public static ArrayList<String> mSelectedCheck = null;
    public static ProgressDialog proDial;   // 프로그래스바

    ScheduledExecutorService executorService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_checklist);
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

        mActivity = this;
        mContext = this;

        setLayoutContent();
    }

    private void setLayoutContent(){
        mSelectedCheck = null;
        mSelectedCheck = new ArrayList<String>();

        btn_close = findViewById(R.id.checklist_close);
        btn_close.setOnClickListener(onClickListener);

        btn_sort = findViewById(R.id.checklist_sort);
        btn_sort.setOnClickListener(onClickListener);

        btn_checklist_allchecks = findViewById(R.id.btn_checklist_allchecks);
        btn_checklist_allchecks.setOnClickListener(onClickListener);

        btn_checklist_clearchecked = findViewById(R.id.btn_checklist_clearchecked);
        btn_checklist_clearchecked.setOnClickListener(onClickListener);
        //btn_checklist_clearchecked.setVisibility(View.GONE);

        listview_floor = findViewById(R.id.checklist_list1);
        listview_table = findViewById(R.id.checklist_list2);

        TV_floor_totalAmount = findViewById(R.id.floor_totalamount);

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        String[] tableZone = globalMemberValues.getRestaurantTableZone();

        listview_floor.setAdapter(new floorAdapter(tableZone));
        listview_floor.setOnItemClickListener(floorItemClickListener);


        if (tableZone != null && tableZone.length != 0){
            setFirstCheckList(tableZone);
        }

        btn_loglist = findViewById(R.id.checklist_loglist);
        btn_loglist.setOnClickListener(onClickListener);

        // 07.12.2022
        // 아래 구문 주석처리
        //listview_table.setOnItemClickListener(tableItemClickListener);
    }

    private void setFirstCheckList(String[] tableZone){
        String[] floorarr = null;
        String zoneidx = "";

        ArrayList<String> temp_array2 = new ArrayList<String>();
        ArrayList<String> temp_array1 = new ArrayList<String>();
        for (int i = 0; i < tableZone.length; i++) {
            floorarr = tableZone[i].split("-JJJ-");
            zoneidx = floorarr[0];
            GlobalMemberValues gm = new GlobalMemberValues();
            String[] temp_tableinfo = gm.getRestaurantTable_checklist(zoneidx);
            if (temp_tableinfo == null) return;
            temp_array2 = new ArrayList<>(Arrays.asList(temp_tableinfo));
            temp_array1.addAll(temp_array2);

            if (tableZone.length == i + 1){
                temp_tableinfo = gm.getRestaurantTableForQuick_checklist();
                temp_array2 = new ArrayList<>(Arrays.asList(temp_tableinfo));
                temp_array1.addAll(temp_array2);
            }
        }

        tableinfo = temp_array1.toArray(new String[0]);
        listview_table.setAdapter(new tableAdapter(tablelist_timesort(tableinfo)));
        TV_floor_totalAmount.setText(""+getTableTotalAmount(tablelist_timesort(tableinfo)));
    }

    private void getCheckedItem(){
//        Descending descending = new Descending();
//        Collections.sort(checked_position, descending);
//
//        ArrayList<String> temp_tableinfo = new ArrayList<String>();
//        for (int z = 0; checked_position.size() > z; z++){
//
////            temp_tableinfo[0] = tableinfo[checked_position.get(z)];
//
//        }
//
//
//        for (int i = 0; tableinfo.length > i; i++){
//
//            for (int j = 0; checked_position.size() > j; j++){
//                if (i == checked_position.get(j)){
//
//                } else {
//                    temp_tableinfo.add(tableinfo[i]);
//                }
//            }
//        }
//
//        for (int j = checked_position.size()-1; j >= 0; j--) {
//
//        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checklist_close:
                    LogsSave.saveLogsInDB(237);
                    finish();
                    break;
                case R.id.checklist_sort:
                    break;
                case R.id.btn_checklist_allchecks :
                    LogsSave.saveLogsInDB(238);
                    GlobalMemberValues globalMemberValues = new GlobalMemberValues();
                    String[] tableZone = globalMemberValues.getRestaurantTableZone();
                    if (tableZone != null && tableZone.length != 0){
                        setFirstCheckList(tableZone);
                    }
                    break;
                case R.id.btn_checklist_clearchecked :

                    // 05.20.2022 -----------------------------------------
                    if (mSelectedCheck == null || mSelectedCheck.size() == 0) {
                        GlobalMemberValues.displayDialog(mContext, "Waraning", "There's no checked list", "Close");
                        return;
                    }
                    new AlertDialog.Builder(mContext)
                            .setTitle("Clear Checked")
                            .setMessage("Would you like to clean the checked list?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(240);
                                            proDial = ProgressDialog.show(mContext, "Clear Checked", "The selected list is being cleaned...", true, true);
                                            Thread thread = new Thread(new Runnable() {
                                                public void run() {
                                                    setClearCheckedList();
                                                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                                    handler.sendEmptyMessage(0);
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
                    // -------------------------------------------------------

                    break;
                case R.id.checklist_loglist:
                    Intent intent = new Intent(mContext.getApplicationContext(), LogHistory.class);
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

    AdapterView.OnItemClickListener floorItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("byullbam_test", "floorItemClickListener");
            GlobalMemberValues globalMemberValues = new GlobalMemberValues();
            String[] tableZone = globalMemberValues.getRestaurantTableZone();

            listview_floor.setAdapter(new floorAdapter(tableZone));
            if (tableZone != null && tableZone.length != 0){
                String[] floorarr = null;
                String zoneidx = "";

                if (tableZone.length <= position){
                    GlobalMemberValues gm = new GlobalMemberValues();
                    tableinfo = gm.getRestaurantTableForQuick_checklist();
                    if (tableinfo == null) return;
                    LogsSave.saveLogsInDB(239);
                    listview_table.setAdapter(new tableAdapter(tablelist_timesort(tableinfo)));
                    TV_floor_totalAmount.setText(""+getTableTotalAmount(tablelist_timesort(tableinfo)));
                } else {
                    floorarr = tableZone[position].split("-JJJ-");
                    zoneidx = floorarr[0];
                    GlobalMemberValues gm = new GlobalMemberValues();
                    tableinfo = gm.getRestaurantTable_checklist(zoneidx);
                    if (tableinfo == null) return;
                    listview_table.setAdapter(new tableAdapter(tablelist_timesort(tableinfo)));
                    TV_floor_totalAmount.setText(""+getTableTotalAmount(tablelist_timesort(tableinfo)));
                }

            }
        }
    };
    AdapterView.OnItemClickListener tableItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("byullbam_test", "tableItemClickListener");
            doOpenDetailInfo(position);
        }
    };

    public void doOpenDetailInfo(int position) {
        // 주문이 있는 테이블만 처리하도록

        TableAdapterElement[] tableAdapterElements = tablelist_timesort(tableinfo);

        int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from temp_salecart where tableidx like '%" + tableAdapterElements[position].tableidx + "%' "
        ));
        if (tableOrderCount == 0) {

            return;
        }

        Intent intent = new Intent(this.getApplicationContext(),TableSaleBillPrint.class);
        intent.putExtra("selectedTableIdx", tableAdapterElements[position].tableidx);
        intent.putExtra("selectedSubTableNum", "1");
        startActivity(intent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public class floorAdapter extends BaseAdapter{
        private String[] listViewItemList = null;
        public floorAdapter(String[] arrayList){
            this.listViewItemList = arrayList;
        }

        @Override
        public int getCount() {
            return listViewItemList.length + 1;
        }

        @Override
        public Object getItem(int position) {
            if (listViewItemList.length >= position){
                return listViewItemList[position];
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.floorlist_row, parent, false);
            }
            TextView TV_floor_name = convertView.findViewById(R.id.checklist_floor_name);
            String[] floorarr = null;
            String zoneidx = "";
            String zonename = "";
            String tablecounts = "";
            String zonetype = "";

            if (listViewItemList.length <= position){
                // quick
                TV_floor_name.setText("TO GO");
                TV_floor_name.setTextColor(Color.parseColor("#dc5b00"));
                TV_floor_name.setBackgroundColor(Color.parseColor("#ffecde"));
            } else {
                floorarr = listViewItemList[position].split("-JJJ-");
                zoneidx = floorarr[0];
                zonename = floorarr[1];
                tablecounts = floorarr[2];
                zonetype = floorarr[3];
                TV_floor_name.setText(zonename);
                TV_floor_name.setTextColor(Color.parseColor("#000000"));
                TV_floor_name.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            return convertView;
        }
    }

    public String getTableTotalAmount(TableAdapterElement[] temp_tableAdapterElement_arr){
        double mTableAmount = 0.0;
        double mTemp = 0.0;
        for (int position = 0 ; temp_tableAdapterElement_arr.length > position ; position++){
            String tableidx = temp_tableAdapterElement_arr[position].tableidx;

            int mSubTableCount = TableSaleMain.getTableSplitCount(tableidx);

            if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
                for (int wj = 0; wj < mSubTableCount; wj++) {
                    String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                            " select holdcode from salon_store_restaurant_table_split " +
                                    " where tableidx like '%" + tableidx + "%' and subtablenum = '" + (wj + 1) + "' "
                    );

                    if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                        mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                        ));
                    }
                }
            } else {                                                 // Table split 이 아닌 경우
                String temp_holdcode = TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);
//                    GlobalMemberValues.logWrite("strQueryLog", "query : " + " select holdcode from temp_salecart where tableidx like '%" + tableidx + "%' " + "\n");

                if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                    mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                            " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                    ));
                }
            }

            mTemp += mTableAmount;

        }

        return GlobalMemberValues.getCommaStringForDouble(""+mTemp);
    }

    public TableAdapterElement[] tablelist_timesort(String[] arrayList){
        String[] listViewItemList = null;
        ArrayList<ArrayList> temp_arraryList = new ArrayList<>();
        ArrayList<String> temp_tablearr = new ArrayList<>();
        TableAdapterElement[] temp_tableAdapterElement_arr = null;

        listViewItemList = arrayList;
        temp_tableAdapterElement_arr = new TableAdapterElement[arrayList.length];

        for (int position = 0; arrayList.length > position ; position++){
            String[] tablearr = null;
            String tableidx = "";

            tablearr = listViewItemList[position].split("-JJJ-");

            temp_tablearr = new ArrayList<>(Arrays.asList(tablearr));

            tableidx = tablearr[0];

            String strQuery_temp = " select top 1 wdate from temp_salecart " +
                    " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' order by wdate asc";

            ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
            String ordereddateValue = "";
            try {
                while (timeCursor.next()){
                    String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                    ordereddateValue = getTime;
                }
                timeCursor.close();
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
                String s_hour = "", s_min = "", s_second = "";
                if (hour < 10){
                    s_hour = "0" + hour;
                } else s_hour = "" + hour;
                if (min < 10){
                    s_min = "0" + min;
                } else s_min = "" + min;
                if (second < 10){
                    s_second = "0" + second;
                } else s_second = "" + second;

                arrayList[position] = arrayList[position] + "-JJJ-" + s_hour +":"+ s_min +":"+ s_second;

                tablearr = arrayList[position].split("-JJJ-");

                temp_tablearr.add(s_hour +":"+ s_min +":"+ s_second);

                temp_arraryList.add(temp_tablearr);
                Log.e("byullbam_test", ordereddateValue + "-" + hour +":"+ min +":"+ second);

                TableAdapterElement tableAdapterElement = new TableAdapterElement(tablearr[0],tablearr[1],tablearr[2],tablearr[3],tablearr[4],tablearr[5],tablearr[6],tablearr[7],tablearr[8],tablearr[9],tablearr[10],tablearr[11],tablearr[12],tablearr[14]);
                temp_tableAdapterElement_arr[position] = tableAdapterElement;
            }
            catch(ParseException e) {
                // 예외 처리
            }
        }

        Arrays.sort(temp_tableAdapterElement_arr, Collections.reverseOrder());

        return temp_tableAdapterElement_arr;
    }

    public class tableAdapter extends BaseAdapter{

        TableAdapterElement[] temp_tableAdapterElement_arr = null;
        public tableAdapter(TableAdapterElement[] arrayList){
//            this.listViewItemList = arrayList;
//            temp_tableAdapterElement_arr = new TableAdapterElement[arrayList.length];

            temp_tableAdapterElement_arr = arrayList;
        }

        @Override
        public int getCount() {
            return temp_tableAdapterElement_arr.length;
        }

        @Override
        public Object getItem(int position) {
            return temp_tableAdapterElement_arr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
//            if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checklist_row, parent, false);
//            }

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
            String orderTime = "";

//            tablearr = listViewItemList[position].split("-JJJ-");
//            tablearr = temp_tableAdapterElement_arr[position];

//            tablearr = temp_arraryList.get(position);

            tableidx = temp_tableAdapterElement_arr[position].tableidx;
            tablename = temp_tableAdapterElement_arr[position].tablename;
            capacity = temp_tableAdapterElement_arr[position].capacity;
            colorvalue = temp_tableAdapterElement_arr[position].colorvalue;
            tabletype = temp_tableAdapterElement_arr[position].tabletype;
            chargeridx = temp_tableAdapterElement_arr[position].chargeridx;
            pagernum = temp_tableAdapterElement_arr[position].pagernum;
            xvaluerate = temp_tableAdapterElement_arr[position].xvaluerate;
            yvaluerate = temp_tableAdapterElement_arr[position].yvaluerate;
            boxtype = temp_tableAdapterElement_arr[position].boxtype;
            boxkinds = temp_tableAdapterElement_arr[position].boxkinds;
            customername = temp_tableAdapterElement_arr[position].customername;
            customerphonenum = temp_tableAdapterElement_arr[position].customerphonenum;
            //
            orderTime = temp_tableAdapterElement_arr[position].orderTime;

            // 05.20.2022 ---------------------------------------------------------------------
            String customerid = "";
            if (!GlobalMemberValues.isRealTable(tableidx)) {
                customerid = MssqlDatabase.getResultSetValueToString(
                        " select customerid from temp_salecart_deliveryinfo where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' "
                );
            }
            // --------------------------------------------------------------------------------

            // 05262023 -----------------------------------------------------------------------
            String tempMergednum = MssqlDatabase.getResultSetValueToString(
                    " select mergednum from temp_salecart where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' "
            );
            if (GlobalMemberValues.getIntAtString(tempMergednum) > 0) {
                String mergednumstr = "0" + tempMergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tablename += " [merged : " + mergednumstr + "]";
            }
            // 05262023 -----------------------------------------------------------------------

            if (!GlobalMemberValues.isStrEmpty(customerid)) {
                tablename += " (" + customerid + ")";
            }

            TextView TV_tablename = convertView.findViewById(R.id.checklist_row_tablename);
            if (tablename != null) TV_tablename.setText(tablename);

            String tempEmpname = MssqlDatabase.getResultSetValueToString("select empName from temp_salecart where tableidx = 'T" + tableidx + "' ");

            TextView TV_empname = convertView.findViewById(R.id.checklist_row_empname);
            TV_empname.setText(tempEmpname);

            if (!GlobalMemberValues.isRealTable(tableidx)) {
                // TOGO 일 경우 고객의 전화번호 또는 Grub gub 등의 정보가 보이도록 할 것
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' "
                );
                TV_empname.setText(customerPhone);
            }

            TextView TV_tabletime = convertView.findViewById(R.id.checklist_row_time);
            TV_tabletime.setText(orderTime);

            String strQuery_temp = " select top 1 wdate from temp_salecart " +
                    " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum) + "' order by wdate asc";

            ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
            String ordereddateValue = "";
            try {
                while (timeCursor.next()){
                    String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                    ordereddateValue = getTime;
                }
                timeCursor.close();
            } catch (Exception e) {
            }

            String date1 = ordereddateValue;

            //10152024 split fractional seconds away
            date1 = date1.split("\\.")[0];

            // while 문 제거하고 주문 들어온 시간으로 변경표시.
            TV_tabletime.setText(date1);

//            executorService = Executors.newSingleThreadScheduledExecutor();
//
//            executorService.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                        Date date = new Date();
//                        String nowDate = simpleDateFormat.format(date);
//
//                        Date inputDate = simpleDateFormat.parse(nowDate);
//                        Date currDate = simpleDateFormat.parse(date1);
//
//                        long hour = (inputDate.getTime() - currDate.getTime()) / (1000) / 3600 % 24;
//                        long min = (inputDate.getTime() - currDate.getTime()) / (1000) / 60 % 60;
//                        long second = (inputDate.getTime() - currDate.getTime()) / (1000) % 60;
//
//                        String s_hour = "", s_min = "", s_second = "";
//                        if (hour < 10){
//                            s_hour = "0" + hour;
//                        } else s_hour = "" + hour;
//                        if (min < 10){
//                            s_min = "0" + min;
//                        } else s_min = "" + min;
//                        if (second < 10){
//                            s_second = "0" + second;
//                        } else s_second = "" + second;
//
//                        String finalS_hour = s_hour;
//                        String finalS_min = s_min;
//                        String finalS_second = s_second;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                TV_tabletime.setText(finalS_hour +":"+ finalS_min +":"+ finalS_second);
//                            }
//                        });
//
//                    } catch(ParseException e) {
//                        // 예외 처리
//                    }
//                }
//            }, 0, 1, TimeUnit.SECONDS);

//            Thread timeThread = new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    while (true){
//
//                        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                            Date date = new Date();
//                            String nowDate = simpleDateFormat.format(date);
////                    //현재시간 구하기 (시작 시간)
////
//                            Date inputDate = simpleDateFormat.parse(nowDate);
//                            Date currDate = simpleDateFormat.parse(date1);
//                            // 시작 시간, 종료 시간 파싱해서 계산할 수 있게 바꿈
//
//                            long hour = (inputDate.getTime() - currDate.getTime()) / (1000) / 3600 % 24;
//                            long min = (inputDate.getTime() - currDate.getTime()) / (1000) / 60 % 60;
//                            long second = (inputDate.getTime() - currDate.getTime()) / (1000) % 60;
//                            // (시작 시간 - 종료시간)  시간 차이나는 부분을 시, 분, 초로 얻음
//                            String s_hour = "", s_min = "", s_second = "";
//                            if (hour < 10){
//                                s_hour = "0" + hour;
//                            } else s_hour = "" + hour;
//                            if (min < 10){
//                                s_min = "0" + min;
//                            } else s_min = "" + min;
//                            if (second < 10){
//                                s_second = "0" + second;
//                            } else s_second = "" + second;
//
//                            String finalS_hour = s_hour;
//                            String finalS_min = s_min;
//                            String finalS_second = s_second;
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    TV_tabletime.setText(finalS_hour +":"+ finalS_min +":"+ finalS_second);
//                                }
//                            });
//                        }
//                        catch(ParseException e) {
//                            // 예외 처리
//                        }
//
//                        try {
//                            sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
//
//            timeThread.start();



            TextView TV_tableamount = convertView.findViewById(R.id.checklist_row_price);
//            TV_tableamount.setText();
            double mTableAmount = 0.0;
            int mSubTableCount = TableSaleMain.getTableSplitCount(tableidx);
            String temp_holdcode = "";

            if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
                for (int wj = 0; wj < mSubTableCount; wj++) {
                    temp_holdcode = MssqlDatabase.getResultSetValueToString(
                            " select holdcode from salon_store_restaurant_table_split " +
                                    " where tableidx like '%" + tableidx + "%' and subtablenum = '" + (wj + 1) + "' "
                    );

                    if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                        mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                        ));
                    }
                }
            } else {                                                 // Table split 이 아닌 경우
                temp_holdcode = TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);
//                    GlobalMemberValues.logWrite("strQueryLog", "query : " + " select holdcode from temp_salecart where tableidx like '%" + tableidx + "%' " + "\n");

                if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                    mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                            " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                    ));
                }
            }


            TV_tableamount.setText("$" + GlobalMemberValues.getCommaStringForDouble(mTableAmount + ""));


            Button Btn_masterPrint = convertView.findViewById(R.id.checklist_row_master_print);
            String finalTableidx = tableidx;
            Btn_masterPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 먼저 salon_sales_kitchenprintingdata_json 에
                    // 해당 holdcode 의 printedyn 의 값이 N 으로 되어 있는 것이 있는지 체크한다.
                    int notPrintedCnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from salon_sales_kitchenprintingdata_json " +
                                            " where salescode = '" + TableSaleMain.getHoldCodeByTableidx(finalTableidx, TableSaleMain.mSubTableNum) + "'" +
                                            " and printedyn = 'N' "
                            )
                    );
                    if (notPrintedCnt > 0) {
                        GlobalMemberValues.displayDialog(mContext, "Waraning", "There is a history that has not been printed in the kitchen for the same order" +
                                "\nPlease try again in a few seconds", "Close");
                    } else {
                        LogsSave.saveLogsInDB(241);
                        GlobalMemberValues.isKitchenReprinting = "M";
                        TableSaleMain.kitchenPrint(TableSaleMain.getHoldCodeByTableidx(finalTableidx, TableSaleMain.mSubTableNum), true, context);
                    }
                }
            });
            Button Btn_kitchenPrint = convertView.findViewById(R.id.checklist_row_kitchen_print);
            Btn_kitchenPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 먼저 salon_sales_kitchenprintingdata_json 에
                    // 해당 holdcode 의 printedyn 의 값이 N 으로 되어 있는 것이 있는지 체크한다.
                    int notPrintedCnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from salon_sales_kitchenprintingdata_json " +
                                            " where salescode = '" + TableSaleMain.getHoldCodeByTableidx(finalTableidx, TableSaleMain.mSubTableNum) + "'" +
                                            " and printedyn = 'N' "
                            )
                    );
                    if (notPrintedCnt > 0) {
                        GlobalMemberValues.displayDialog(mContext, "Waraning", "There is a history that has not been printed in the kitchen for the same order" +
                                "\nPlease try again in a few seconds", "Close");
                    } else {
                        LogsSave.saveLogsInDB(242);
                        GlobalMemberValues.isKitchenReprinting = "K";
                        TableSaleMain.kitchenPrint(TableSaleMain.getHoldCodeByTableidx(finalTableidx, TableSaleMain.mSubTableNum), true, context);
                    }

                }
            });

            // view log.
            String f_temp_holdcode = temp_holdcode;
            Button btn_View_Log = convertView.findViewById(R.id.checklist_row_view_log);
            btn_View_Log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(), LogHistory.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    // -------------------------------------------------------------------------------------
                    intent.putExtra("holdcode",f_temp_holdcode);
                    startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }
            });
            //

            String checkBoxValue = tableidx + "-jjj-" + TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);
            CheckBox checkBox = convertView.findViewById(R.id.checklist_check);
//            checkBox.setVisibility(View.GONE);
            checkBox.setTag(checkBoxValue);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    // 05.20.2022 ---------------------------------------------
                    String tempValue = buttonView.getTag().toString();
                    if (isChecked){
                        mSelectedCheck.add(tempValue);
                    } else {
                        if (mSelectedCheck != null && mSelectedCheck.size() > 0) {
                            for (String jjjvalue : mSelectedCheck) {
                                if (jjjvalue.equals(tempValue)) {
                                    mSelectedCheck.remove(jjjvalue);
                                    break;
                                }
                                GlobalMemberValues.logWrite("jjjcheckedjjjlog", "value : " + jjjvalue + "\n");
                            }
                        }
                    }

                    for (String jjjvalue : mSelectedCheck) {
                        GlobalMemberValues.logWrite("jjjcheckedjjjlog", "value : " + jjjvalue + "\n");
                    }
                    // --------------------------------------------------------
                }
            });


            if (mSelectedCheck != null && mSelectedCheck.size() > 0) {
                boolean is_checked = false;
                for (String jjjvalue : mSelectedCheck) {
                    if (jjjvalue.equals(checkBoxValue)) {
                        // 있음.
                        is_checked = true;
                    } else {

                    }
                }

                if (is_checked){
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }


            return convertView;
        }

    }
    public class TableAdapterElement implements Comparable{

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
        String orderTime = "";

        public TableAdapterElement(String tableidx, String tablename, String capacity, String colorvalue, String tabletype, String chargeridx, String pagernum, String xvaluerate, String yvaluerate, String boxtype, String boxkinds, String customername, String customerphonenum, String orderTime){
            this.tableidx = tableidx;
            this.tablename = tablename;
            this.capacity = capacity;
            this.colorvalue = colorvalue;
            this.tabletype = tabletype;
            this.chargeridx = chargeridx;
            this.pagernum = pagernum;
            this.xvaluerate = xvaluerate;
            this.yvaluerate = yvaluerate;
            this.boxtype = boxtype;
            this.boxkinds = boxkinds;
            this.customername = customername;
            this.customerphonenum = customerphonenum;
            this.orderTime = orderTime;
        }

        @Override
        public int compareTo(Object o) {
            TableAdapterElement tableAdapterElement = (TableAdapterElement) o;

//            int z = Integer.parseInt(orderTime.replace(":",""));
//            int x = Integer.parseInt(tableAdapterElement.orderTime.replace(":",""));

            String orderTime1 = orderTime;
            orderTime1 = GlobalMemberValues.getReplaceText(orderTime1, ":", "");
            orderTime1 = GlobalMemberValues.getReplaceText(orderTime1, "-", "");
            int z = Integer.parseInt(orderTime1);

            String orderTime2 = tableAdapterElement.orderTime;
            orderTime2 = GlobalMemberValues.getReplaceText(orderTime2, ":", "");
            orderTime2 = GlobalMemberValues.getReplaceText(orderTime2, "-", "");
            int x = Integer.parseInt(orderTime2);

            if (z < x) {
                return -1;
            } else if (z == x) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    class Descending implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }

    }

    // 05.20.2022
    public static void setClearCheckedList() {
        if (mSelectedCheck != null && mSelectedCheck.size() > 0) {
            String getTableidx = "";
            String getHoldCode = "";
            String[] tempsplit = null;
            for (String jjjvalue : mSelectedCheck) {
                getTableidx = "";
                getHoldCode = "";
                tempsplit = null;

                GlobalMemberValues.logWrite("jjjcheckedjjjlog2", "value : " + jjjvalue + "\n");

                if (!GlobalMemberValues.isStrEmpty(jjjvalue)) {
                    tempsplit = jjjvalue.split("-jjj-");
                    getTableidx = tempsplit[0];
                    if (tempsplit.length > 1) {
                        getHoldCode = tempsplit[1];
                    }

                    try {
                        saveInDatabaseForCheckedList(getHoldCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Waraning", "There's no checked list", "Close");
        }
    }

    public static String saveInDatabaseForCheckedList(String paramHoldcode) throws JSONException {
        String returnResult = "";

        // 구해야 할 값
        String mHoldCode = paramHoldcode;

        double mPayTotalAmount = 0.0;
        if (!GlobalMemberValues.isStrEmpty(mHoldCode)) {
            mPayTotalAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                    " select sum(sTotalAmount) from temp_salecart where holdcode = '" + mHoldCode + "' "
            ));
        }

        JSONObject jsonroot = null;
        JSONObject jsonroot_kitchen = null;

        // 07162023
        String sales_salesCode = "";
        if (!GlobalMemberValues.isStrEmpty(mHoldCode)) {
            String[] tempMHoldC = mHoldCode.split("T");
            sales_salesCode = "K" + tempMHoldC[0];
        } else {
            sales_salesCode = "K" + GlobalMemberValues.makeSalesCode();
        }


        String mSalesCode = sales_salesCode;

        Vector<String> strInsertQueryVec = null;
        strInsertQueryVec = new Vector<String>();

        String strQuery = "";
        String strInsSqlQuery_item = "";
        String strUpdSqlQuery_item = "";
        String strInsSqlQuery = "";
        String strDelSqlQuery = "";

        double tempCheckAmount = 0.0;

        /** 결제수단별 결제비율 ***********************************************************************************/
        double useTotalCashRatio = 0.0;
        double useTotalCardRatio = 0.0;
        double useTotalGiftCardRatio = 0.0;
        double useTotalCheckRatio = 0.0;
        double useTotalPointRatio = 0.0;

        String str_useTotalCashRatio = "0.00";
        String str_useTotalCardRatio = "0.00";
        String str_useTotalGiftCardRatio = "0.00";
        String str_useTotalCheckRatio = "1.00";
        String str_useTotalPointRatio = "0.00";

        /**********************************************************************************************************/

        // 세일일시를 지정했을 경우
        String sqlQuery_add1 = "";
        String sqlQuery_add2 = "";
        if (GlobalMemberValues.isDifferentSaleDateToday()) {
            String tempSearchDate = "";
            String[] tempSettingSaleDateArr = GlobalMemberValues.SETTING_SALE_DATE.split("-");
            if (tempSettingSaleDateArr.length > 0) {
                tempSearchDate = tempSettingSaleDateArr[2] + "-" + tempSettingSaleDateArr[0] + "-" + tempSettingSaleDateArr[1];

                sqlQuery_add1 = ", saleDate ";
                sqlQuery_add2 = ", DATETIME('" + tempSearchDate + " 23:59:59') ";
            }
        }

        /** salon_sales_detail 테이블 저장 관련 *******************************************************************/
        double tempTotalAmountOrg = 0.0;
        double tempTaxAmountOrg = 0.0;

        double tempSalesAmount = 0.0;                   // Discount, Extra 반영전 상품 총액
        String employeeNamesForJSON = "";

        int itemDetailCount = 0;
        String itemDetailText = "";
        double sales_pointAmount = 0;

        // 테이블에서 주문한 내역인지 여부
        boolean isOrderedOnTable = false;
        int tempTbidxCnt = 0;

        String isCloudUpload = "0";

        String mCheckCompany = "";

        int sales_qty = 0;

        // 실결제금액총액 (세금이전)
        double sales_salesBalPriceAmount = 0.0;
        // 총 세금액
        double sales_taxAmount = 0.0;
        // 전체 결제금액 (세금포함)
        double sales_totalAmount = mPayTotalAmount;

        // 커미션 지불총액
        double sales_commissionAmount = 0.0;

        // 사용한 기프트카드번호
        String sales_giftcardNumberUsed = "";
        // 기프트 카드 사용한 금액
        String sales_giftcardPriceUsed = "0";
        // 전체 할인/추가한 금액
        double totalDiscountExtraPrice = 0.0;
        // All Discount 또는 Extra 금액
        String allDiscountExtraPrice = "0.0";
        // All Discount 또는 Extra 문자열
        String allDiscountExtraStr = "";
        // Each Discount 또는 Extra 금액
        double eachDiscountExtraPrice = 0.0;
        // 전체 할인금액
        double totalDiscountPrice = 0.0;
        // 전체 추가금액
        double totalExtraPrice = 0.0;

        String mEmpIdx = "";
        String mEmpName = "";

        String mTableIdx = "";


        // 최상단 json객체
        jsonroot = new JSONObject();
        // 주방프린터용 json 객체
        jsonroot_kitchen = new JSONObject();

        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp = null;

        // JSON ----------------------------------------------------------------------
        jsonroot.put("receiptno", mSalesCode);
        jsonroot_kitchen.put("receiptno", mSalesCode);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());

        jsonroot_kitchen.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
        // ---------------------------------------------------------------------------


        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }

        jsonroot.put("storename", GlobalMemberValues.getDBTextAfterChecked(storeNameForReceipt,0));  // JSON
        jsonroot.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot.put("storecity", storeCityForReceipt);  // JSON
        jsonroot.put("storestate", storeStateForReceipt);  // JSON
        jsonroot.put("storezip", storeZipForReceipt);  // JSON
        jsonroot.put("storephone", storePhoneForReceipt);  // JSON
        /******************************************************************/

        // point
        jsonroot.put("earnedpoint",GlobalMemberValues.getStringFormatNumber("0.0","2"));

        String tempSaleCartQuery = "";
        tempSaleCartQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " midx, svcIdx, " +
                " svcName, svcFileName, svcFilePath, " +
                " sPrice, sPrice, '', '', " +
                " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                " svcPositionNo, svcSetMenuYN, " +
                " customerId, customerName, customerPhoneNo,  " +
                " saveType, empIdx, empName, quickSaleYN, " +
                " svcCategoryName, " +
                " giftcardNumber, giftcardSavePrice, " +
                " idx, svcCategoryColor, taxExempt, " +
                " reservationCode, " +
                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                " sPriceAmount, sTaxAmount, sTotalAmount, memoToKitchen, kitchenprintedyn, " + // 42
                " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, " + // 43, 44, 45, 46, 47
                " togodelitype, " +
                " stax, labelprintedyn, " + // 50 labelprintedyn
                " svcOrgPrice, sPriceBalAmount, sTotalAmount_org, " +
                " sCommission, sCommissionAmount, " +
                " sPoint, sPointAmount " +
                " from temp_salecart " +
                " where holdcode = '" + mHoldCode + "' " +
                " order by idx asc";

        GlobalMemberValues.logWrite("jjjwanhayelog2", "tempSaleCartQuery : " + tempSaleCartQuery + "\n");

        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempSaleCartQuery);
        try {
            while (tempSaleCartCursor.next()) {
                String sales_sidx = GlobalMemberValues.STORE_INDEX;
                String sales_stcode = GlobalMemberValues.STATION_CODE;

                String temp_item_qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);

                String temp_holdcode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,1), 1);

                String item_midx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,4), 1);

                String temp_item_itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1);

                String temp_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1);

                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                String temp_optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1);
                String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                String temp_additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1);
                String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                String temp_additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1);

                String temp_sPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1);
                String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);
                String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                String temp_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);

                String temp_isQuickSale = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1);

                String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1);

                String temp_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);

                String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);
                String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1),"2");
                String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1), "2");

                String togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1);

                String temp_categoryname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1);

                String temp_stax = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,49), 1), "2");

                String labelprintedYN = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,50), 1);

                String item_positionNo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,16), 1);

                String item_svcOrgPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,51), 1);
                String item_sPriceBalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,52), 1);
                String item_sTotalAmount_org = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,53), 1);

                String item_sCommissionRatio = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,13), 1);
                String item_sCommissionRatioType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,14), 1);

                String item_sCommission = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,54), 1);
                String item_sCommissionAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,55), 1);

                String item_sPointRatio = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,15), 1);
                String item_sPoint = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,56), 1);
                String item_sPointAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,57), 1);

                String item_empIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,22), 1);
                String item_empName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,23), 1);

                String temp_salecartidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,28), 1);

                // 카테고리코드(인덱스)
                String item_categoryCode = item_midx;
                String temp_item_categoryName = temp_categoryname;
                String item_categoryName = temp_categoryname;
                String item_categoryName2 = "";
                if (temp_item_categoryName.contains("-AAA-")){
                    item_categoryName = temp_item_categoryName.split("-AAA-")[0];
                    item_categoryName2 = temp_item_categoryName.split("-AAA-")[1];
                }

                String item_itemIdx = temp_itemIdx;
                String item_itemName = temp_item_itemName;
                String item_itemFileName = "";
                String item_itemFilePath = "";
                String item_servicePositionNo = item_positionNo;
                String item_qty = temp_item_qty;

                String item_salesOrgPrice = item_svcOrgPrice;
                String item_salesPrice = temp_sPrice;
                String item_salesPriceAmount = temp_sPriceAmount;

                String item_salesBalPrice = "0";
                String item_salesBalPriceAmount = item_sPriceBalAmount;
                double item_salesBalPriceAmount_double = GlobalMemberValues.getDoubleAtString(item_salesBalPriceAmount);
                if (item_salesBalPriceAmount_double > 0 && GlobalMemberValues.getIntAtString(item_qty) > 0) {
                    item_salesBalPrice = GlobalMemberValues.getStringFormatNumber(
                            item_salesBalPriceAmount_double / GlobalMemberValues.getIntAtString(item_qty), "2");
                }

                String item_sales_tax = temp_stax;
                String item_sales_taxAmount = temp_sTaxAmount;

                String item_sales_taxAmount_org = temp_sTaxAmount;
                if (!GlobalMemberValues.isStrEmpty(item_sales_taxAmount_org)) {
                    if (GlobalMemberValues.isCheckDouble(item_sales_taxAmount_org)) {
                        double dbl_item_sales_taxAmount_org = GlobalMemberValues.getDoubleAtString(item_sales_taxAmount_org);
                        if (!Double.isInfinite(dbl_item_sales_taxAmount_org) && !Double.isNaN(dbl_item_sales_taxAmount_org)) {
                            tempTaxAmountOrg += dbl_item_sales_taxAmount_org;
                        }
                    }
                }

                String item_sales_totalAmount = temp_sTotalAmount;

                String item_sales_totalAmount_org = item_sTotalAmount_org;

                if (!GlobalMemberValues.isStrEmpty(item_sales_totalAmount_org)) {
                    if (GlobalMemberValues.isCheckDouble(item_sales_totalAmount_org)) {
                        double dbl_item_sales_totalAmount_org = GlobalMemberValues.getDoubleAtString(item_sales_totalAmount_org);
                        if (!Double.isInfinite(dbl_item_sales_totalAmount_org) && !Double.isNaN(dbl_item_sales_totalAmount_org)) {
                            tempTotalAmountOrg += dbl_item_sales_totalAmount_org * GlobalMemberValues.getIntAtString(item_qty);
                        }
                    }
                }

                String item_commissionRatioType = item_sCommissionRatioType;
                String item_commissionRatio = item_sCommissionRatio;
                String item_commission = item_sCommission;
                String item_commissionAmount = item_sCommissionAmount;

                String item_pointRatio = item_sPointRatio;
                String item_point = item_sPoint;
                String item_pointAmount = item_sPointAmount;

                // 포인트 재계산 ---------------------------------------------------------------------------------------------------------------------
                // 실제 지급될 포인트 총액대비하여 현재 아이템의 포인트를 계산한다.
                double tempItemPointAmount = 0;
                item_pointAmount = String.valueOf(tempItemPointAmount);
                // ------------------------------------------------------------------------------------------------------------------------------------

                int item_isSale = 1;

                String item_employeeIdx = item_empIdx;
                String item_employeeName = item_empName;

                if (!GlobalMemberValues.isStrEmpty(item_employeeIdx)) {
                    mEmpIdx = item_employeeIdx;
                }
                if (!GlobalMemberValues.isStrEmpty(item_employeeName)) {
                    mEmpName = item_employeeName;
                }

                String item_giftcardNumberToSave = "";
                String item_giftcardSavePriceToSave = "0";

                String item_couponNumber = "";

                String item_eachDiscountExtraPrice = "0";
                String item_eachDiscountExtraType = "";

                String item_eachDiscountExtraStr = "";

                String item_saveType = temp_saveType;
                String item_isQuickSale = temp_isQuickSale;

                String item_isQuickKitchenPrintingYN = "";

                String sales_reservationCode = "";

                String item_selectedDcExtraParentTempSaleCartIdx = "";

                String item_mTaxExempt = "";

                String item_optionTxt = temp_optionTxt;
                String item_optionPrice = temp_optionprice;

                String item_addtionalTxt1 = temp_additionalTxt1;
                String item_addtionalPrice1 = temp_additionalprice1;

                String item_addtionalTxt2 = temp_additionalTxt2;
                String item_addtionalPrice2 = temp_additionalprice2;

                String item_kitchenmemo = temp_memoToKitchen;
                if (GlobalMemberValues.isStrEmpty(item_kitchenmemo)) {
                    item_kitchenmemo = "nokitchenmemo";
                }

                String item_discountbuttonname = "";

                String dcextraforreturn = "0";

                String item_tempSaleCartIdx = temp_salecartidx;     // temp_salecart idx 값

                // tableidx, mergednum, subtablenum, billnum
                // selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice
                String item_tableidx = "", item_mergednum = "", item_subtablenum = "", item_billnum = "", item_togodelitype = "H";
                String item_selectedDcExtraAllEach = "" , item_selectedDcExtraType = "", item_dcextratype = "", item_dcextravalue = "", item_selectedDcExtraPrice = "", item_labelPrintedYN = "";
                String item_togotype = "";
                if (!GlobalMemberValues.isStrEmpty(item_tempSaleCartIdx)) {
                    String strTempSaleCartSql = " select tableidx, mergednum, subtablenum, billnum, togodelitype, " +
                            " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, labelprintedyn, togotype " +
                            " from temp_salecart " +
                            " where idx = '" + item_tempSaleCartIdx + "' ";
                    ResultSet tempCursor = MssqlDatabase.getResultSetValue(strTempSaleCartSql);
                    try {
                        while (tempCursor.next()) {
                            item_tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);

                            if (!GlobalMemberValues.isStrEmpty(item_tableidx)) {
                                mTableIdx = item_tableidx;
                            }

                            item_mergednum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,1), 1);
                            item_subtablenum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,2), 1);
                            item_billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,3), 1);
                            item_togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,4), 1);

                            item_selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,5), 1);
                            item_selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,6), 1);
                            item_dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,7), 1);
                            item_dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,8), 1), "2");
                            item_selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,9), 1), "2");

                            item_labelPrintedYN = GlobalMemberValues.getDBTextAfterChecked(tempCursor.getString(10), 1);

                            item_togotype = GlobalMemberValues.getDBTextAfterChecked(tempCursor.getString(11), 1);
                        }
                    } catch (Exception e) {
                    }

                    //07052024 close resultset
                    try {
                        if(!tempCursor.isClosed()){
                            tempCursor.close();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (!GlobalMemberValues.isStrEmpty(item_tableidx)) {
                    //isOrderedOnTable = true;
                    tempTbidxCnt++;
                }

                String temp_kitchenprintnum = "0";
                if (item_isQuickSale == "Y" || item_isQuickSale.equals("Y")) {
                    if (item_isQuickKitchenPrintingYN == "Y" || item_isQuickKitchenPrintingYN.equals("Y")) {
                        temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumberForQuickSale(item_categoryCode);
                    } else {
                        temp_kitchenprintnum = "NOPRINT";
                    }
                } else {
                    temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(item_itemIdx);
                }

                // 적립할 기프트카드 금액
                double saveGiftCardMoney
                        = GlobalMemberValues.getDoubleAtString(item_giftcardSavePriceToSave) * GlobalMemberValues.getDoubleAtString(item_qty);

                // 0 : 정상주문      1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소
                int item_status = 0;

                // Discount, Extra 가 아닌 item 만 저장한다.
                String tempUpperItemName = item_itemName.toUpperCase();



                // 현재 주문의 배송타입(식사장소타입)이 T, D 일 경우에는 item_togodelitype 을 해당 정보로 저장한다.
                String tempDeliTakeType = MssqlDatabase.getResultSetValueToString(
                        "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + mHoldCode + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempDeliTakeType)) {
                    tempDeliTakeType = "H";
                }
                if (!tempDeliTakeType.equals("H")) {
                    item_togodelitype = tempDeliTakeType;
                }

                // togo delivery 추가
                item_togodelitype = "T";

                strInsSqlQuery_item = "insert into salon_sales_detail (" +
                        " salesCode, holdCode, reservationCode, sidx, stcode, " +
                        " categoryCode, categoryName, " +
                        " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
                        " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
                        " salesBalPrice, salesBalPriceAmount, " +
                        " tax, taxAmount, " +
                        " totalAmount, " +
                        " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                        " pointRatio, point, pointAmount, " +
                        " customerId, customerName, customerPhone, customerPosCode, " +
                        " employeeIdx, employeeName, " +
                        " serverIdx, serverName, " +
                        " giftcardNumberToSave, giftcardSavePriceToSave, " +
                        " couponNumber, " +
                        " eachDiscountExtraPrice, eachDiscountExtraType, eachDiscountExtraStr, " +
                        " dcextraforreturn, " +
                        " saveType, isQuickSale, isSale, status, isCloudUpload,  " +
                        " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                        " checkcompany, memoToKitchen, discountbuttonname, " +
                        " tableidx, mergednum, subtablenum, billnum, tablename, togodelitype, togotype " + sqlQuery_add1 +
                        " ) values ( " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(temp_holdcode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_reservationCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_sidx,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_stcode,0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_categoryCode, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_categoryName, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemFileName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemFilePath, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_servicePositionNo, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_qty,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesOrgPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesPriceAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesBalPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesBalPriceAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_tax, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_taxAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_totalAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionRatioType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionRatio, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commission, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_pointRatio, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_point, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_pointAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_giftcardNumberToSave, 0) + "', " +
                        " '" + saveGiftCardMoney + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_couponNumber, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraStr, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(dcextraforreturn, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_saveType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_isQuickSale, 0) + "', " +
                        " '" + item_isSale + "', " +
                        " '" + item_status + "', " +
                        " '" + isCloudUpload + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_optionTxt, 0) + "', " +
                        " '" + item_optionPrice + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_addtionalTxt1, 0) + "', " +
                        " '" + item_addtionalPrice1 + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_addtionalTxt2, 0) + "', " +
                        " '" + item_addtionalPrice2 + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(mCheckCompany, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_kitchenmemo, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_discountbuttonname, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_tableidx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_mergednum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_subtablenum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_billnum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(TableSaleMain.mTableName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_togodelitype, 0) + "', " +

                        " '" + item_togotype + "' " +

                        sqlQuery_add2 +             // 세일일시 지정일 때...
                        ")";

                // 전체주문 총수량
                sales_qty += GlobalMemberValues.getIntAtString(item_qty);
                // 전체주문 구매총액 (세금 미포함)
                sales_salesBalPriceAmount += item_salesBalPriceAmount_double;
                // 전체주문 세금총액
                sales_taxAmount += GlobalMemberValues.getDoubleAtString(item_sales_taxAmount);
                // 전체주문 커미션총액
                sales_commissionAmount += GlobalMemberValues.getDoubleAtString(item_commissionAmount);
                // 전체주문 포인트 총액
                sales_pointAmount += GlobalMemberValues.getDoubleAtString(item_pointAmount);

                // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
                strInsertQueryVec.addElement(strInsSqlQuery_item);


                jsontmp = new JSONObject();
                jsontmp.put("itemcartidx", item_itemIdx);
                jsontmp.put("itemname", item_itemName);
                jsontmp.put("itemqty", item_qty);
                jsontmp.put("itemprice", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(item_salesPrice), "2"));
                jsontmp.put("itemamount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(item_salesPriceAmount), "2"));
                jsontmp.put("itemdcextratype", item_eachDiscountExtraType);
                jsontmp.put("itemdcextraprice", item_eachDiscountExtraPrice);
                jsontmp.put("itemCategoryName", item_categoryName);
                jsontmp.put("itemCategoryName2", item_categoryName2);
                jsontmp.put("billcartidx",item_tempSaleCartIdx);

                if (!GlobalMemberValues.isStrEmpty(item_eachDiscountExtraStr)) {
                    jsontmp.put("itemdcextrastr", item_eachDiscountExtraStr);
                } else {
                    jsontmp.put("itemdcextrastr", "");
                }
                GlobalMemberValues.logWrite("paymentlogtest", "itemdcextrastr : " + item_eachDiscountExtraStr + "\n");

                jsontmp.put("itemtaxexempt", item_mTaxExempt);

                // 옵션 & 추가사항
                jsontmp.put("optiontxt", item_optionTxt);
                jsontmp.put("optionprice", item_optionPrice);
                jsontmp.put("additionaltxt1", item_addtionalTxt1);
                jsontmp.put("additionalprice1", item_addtionalPrice1);
                jsontmp.put("additionaltxt2", item_addtionalTxt2);
                jsontmp.put("additionalprice2", item_addtionalPrice2);
                jsontmp.put("kitchenmemo", item_kitchenmemo);
                jsontmp.put("togodelitype", item_togodelitype);
                jsontmp.put("labelprintedyn", item_labelPrintedYN);

                jsonList.put(jsontmp);

                tempSalesAmount += GlobalMemberValues.getDoubleAtString(item_salesPriceAmount);
                // ------------------------------------------------------------------------------------------------------------------

            }
            tempSaleCartCursor.close();
        } catch (Exception e){

        }

        if (tempTbidxCnt > 0) {
            isOrderedOnTable = true;
        }
        /*********************************************************************************************************/

        // JSON (구매 서비스 리스트) ---------------------------------------------------
        jsonroot.put("saleitemlist", jsonList);

        jsonroot_kitchen.put("saleitemlist", itemDetailText);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("employeename", employeeNamesForJSON);
        // ---------------------------------------------------------------------------

        /** Table Idx 구하기 **************************************************************************************/
        String tempTableIdx = MssqlDatabase.getResultSetValueToString(
                " select tableidx from temp_salecart where holdcode = '" + mHoldCode + "'"
        );
        if (GlobalMemberValues.isStrEmpty(tempTableIdx)) {
            tempTableIdx = "";
        }
        /*********************************************************************************************************/

        /** salon_sales_detail 테이블 저장 관련 *******************************************************************/
        // temp_salecart_deliveryinfo 테이블에서 배송관련정보 가져온다.
        String temp_customerId = "";
        String temp_customerName = "";
        String temp_customerPhone = "";
        String temp_customerEmail = "";
        String temp_customerAddr1 = "";
        String temp_customerAddr2 = "";
        String temp_customerCity = "";
        String temp_customerState = "";
        String temp_customerZip = "";
        String temp_deliveryday = "";
        String temp_deliverytime = "";
        String temp_deliverydate = "";
        String temp_deliverytakeaway = "";
        String temp_customermemo = "";
        String temp_phoneorder = "";
        String temp_phoneordernumber = "";
        String temp_customerAddrAll = "";
        String temp_kichenprintyn = "";

        strQuery = "select customerId, customerName, customerPhone, customerEmail, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo, phoneorder, phoneordernumber, kitchenprintedyn " +
                " from temp_salecart_deliveryinfo " +
                " where holdcode = '" + mHoldCode + "' ";
        GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
        ResultSet deliverytakeawayInfoCursor = MssqlDatabase.getResultSetValue(strQuery);
        while (true){
            try {
                if (!deliverytakeawayInfoCursor.next()) break;

                temp_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,0), 1);
                temp_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,1), 1);
                temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,2), 1);

                temp_customerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,3), 1);

                temp_customerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,4), 1);
                temp_customerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,5), 1);
                temp_customerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,6), 1);
                temp_customerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,7), 1);
                temp_customerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,8), 1);

                temp_deliveryday = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,9), 1);
                temp_deliverytime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,10), 1);
                temp_deliverydate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,11), 1);
                temp_deliverytakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,12), 1);

                temp_customermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,13), 1);

                temp_phoneorder = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,14), 1);
                temp_phoneordernumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,15), 1);

                if (GlobalMemberValues.isStrEmpty(temp_phoneorder)) {
                    temp_phoneorder = "N";
                }

                if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                    temp_phoneorder = "N";
                    temp_phoneordernumber = "";
                }

                if (!GlobalMemberValues.isStrEmpty(temp_customerAddr1)
                        || !GlobalMemberValues.isStrEmpty(temp_customerAddr2)
                        || !GlobalMemberValues.isStrEmpty(temp_customerCity)
                        || !GlobalMemberValues.isStrEmpty(temp_customerState)
                        || !GlobalMemberValues.isStrEmpty(temp_customerZip)) {

                    temp_customerAddrAll = temp_customerAddr1;

                    if (!GlobalMemberValues.isStrEmpty(temp_customerAddr2)) {
                        temp_customerAddrAll += "\n" + temp_customerAddr2;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerCity)) {
                        temp_customerAddrAll += "\n" + temp_customerCity;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerState)) {
                        temp_customerAddrAll += ", " + temp_customerState;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerZip)) {
                        temp_customerAddrAll += " " + temp_customerZip;
                    }
                }
                //deliverytakeawayInfoCursor.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        //07052024 close resultset
        try {
            if(!deliverytakeawayInfoCursor.isClosed()){
                deliverytakeawayInfoCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 07.11.2022 -------------------------------------------------------------------------
        // temp_customerId 의 값이 salon_store_deliveryappcompany 에 등록된 appname 과 같으면
        // doordash, grup hub 와 같은 딜리버리 앱으로 주문된 것으로 간주
        String tempSql = " select count(*) from salon_store_deliveryappcompany where appname = '" + temp_customerPhone + "' ";
        int tempCCCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(tempSql));
        if (tempCCCnt > 0) {
            mCheckCompany = temp_customerPhone;
        }
        // ------------------------------------------------------------------------------------


        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot.put("customerphonenum", temp_customerPhone);
        jsonroot.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        /** salon_sales 테이블 저장 관련 *******************************************************************/
        double tempDeliveryPickUpFee = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString());

        // 고객 주문번호
        String customerOrderNumber = "";
        if (temp_phoneorder.equals("Y")) {
            if (!GlobalMemberValues.isStrEmpty(temp_phoneordernumber)) {
                customerOrderNumber = "PHONE_" + temp_phoneordernumber;
            } else {
                customerOrderNumber = GlobalMemberValues.getCustomerOrderNewNumber(mSalesCode);
            }
        } else {
            customerOrderNumber = GlobalMemberValues.getCustomerOrderNewNumber(mSalesCode);
        }

        // 고객용 주문번호에 스테이션 번호 연결
        if (!GlobalMemberValues.isStrEmpty(customerOrderNumber)) {
            customerOrderNumber = GlobalMemberValues.getStationNumber() + "-" + customerOrderNumber;
        }

        // 고객 페이저 번호
        String customerPagerNumber = GlobalMemberValues.getCustomerPagerNewNumber();

        double dbl_allDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);

        double totalAllEachDcExPrice = dbl_allDiscountExtraPrice + totalDiscountExtraPrice;

        GlobalMemberValues.logWrite("discountresultjjjlog", "totalAllEachDcExPrice : " + (totalAllEachDcExPrice * -1) + "\n");
        GlobalMemberValues.logWrite("discountresultjjjlog", "tempTotalAmountOrg : " + tempTotalAmountOrg + "\n");

        String tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(mTableIdx, "T", "") + "' ");

        // 카드결제시 단말기 ip
        ConfigPGInfo pgInfo = new ConfigPGInfo();
        String salepg_ip = pgInfo.cfnetworkip;

        // 11182022
        // togotype 가져오기
        String tempTogoType = MssqlDatabase.getResultSetValueToString(
                " select togotype from temp_salecart where holdcode = '" + mHoldCode + "' "
        );

        // 06.28.2022
        tempCheckAmount = sales_totalAmount;

        // 0 : 정상주문      1 : 배치전 void
        int sales_status = 0;
        // salon_sales 입력 쿼리
        strInsSqlQuery = "insert into salon_sales (" +
                " salesCode, holdCode, reservationCode, sidx, stcode, " +
                " qty, " +
                " salesBalPriceAmount, taxAmount, totalAmount, " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " commissionAmount, pointAmount, " +
                " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
//                " deliveryday, deliverytime, deliverydate, " +
                " deliverytakeaway, " +
                " customermemo, " +
                " giftcardNumberUsed, giftcardPriceUsed, " +
                " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                " cardTidNumber, " +
                " isCloudUpload, " +
                " status, " +
                " changeMoney, " +
                " employeeIdx, employeeName, " +
                " serverIdx, serverName, " +
                " deliverypickupfee, " +
                " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                " tablename, tablepeoplecnt, salepg_ip, togotype, " +

                // 07202024
                " pgdevicenum " +

                " " + sqlQuery_add1 +
                " ) values ( " +

                " '" + mSalesCode + "', " +
                " '" + mHoldCode + "', " +
                " '" + "" + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +

                " '" + sales_qty + "', " +

                " '" + sales_salesBalPriceAmount + "', " +
                " '" + sales_taxAmount + "', " +
                " '" + sales_totalAmount + "', " +

                " '" + "1" + "', " +
                " '" + "1" + "', " +
                " '" + "1" + "', " +
                " '" + "0" + "', " +
                " '" + "1" + "', " +

                " '" + "0" + "', " +
                " '" + "0" + "', " +
                " '" + "0" + "', " +
                " '" + tempCheckAmount + "', " +
                " '" + "0" + "', " +

                " '" + str_useTotalCashRatio + "', " +
                " '" + str_useTotalCardRatio + "', " +
                " '" + str_useTotalGiftCardRatio + "', " +
                " '" + str_useTotalCheckRatio + "', " +
                " '" + str_useTotalPointRatio + "', " +

                " '" + "0" + "', " +
                " '" + "0" + "', " +
                " '" + "0" + "', " +
                " '" + tempCheckAmount + "', " +
                " '" + "0" + "', " +

                " '" + sales_commissionAmount + "', " +
                " '" + "0" + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerId, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerName, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerPhone, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerEmail, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerAddr1, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerAddr2, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerCity, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerState, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerZip, 0) + "', " +

//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliveryday, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverytime, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverydate, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverytakeaway, 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customermemo, 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +

                " '" + GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalDiscountPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalExtraPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(eachDiscountExtraPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2") + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(allDiscountExtraStr,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked("",0) + "', " +

                " '" + isCloudUpload + "', " +

                " '" + sales_status + "', " +

                " '" + "0" + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(mEmpIdx,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(mEmpName,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked("0",0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked("",0) + "', " +

                " '" + tempDeliveryPickUpFee + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(mCheckCompany,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_phoneorder,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(customerOrderNumber,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(customerPagerNumber,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(tableName, 0) + "', " +
                " '" + TableSaleMain.getTablePeopleCntByHoldCode(mHoldCode) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(salepg_ip,0) + "', " +

                " '" + tempTogoType + "', " +

                // 07202024
                " '" + GlobalMemberValues.getPGDeviceNum() + "' " +

                sqlQuery_add2 +
                ")";

        strInsertQueryVec.addElement(strInsSqlQuery);
        /*********************************************************************************************************/

        /** salon_sales_customerpagernumber 테이블 저장 관련 ********************************************************/
        if (GlobalMemberValues.isPagerUse()) {
            if (!GlobalMemberValues.isStrEmpty(customerPagerNumber)) {
                strInsSqlQuery = " insert into salon_sales_customerpagernumber ( " +
                        " scode, sidx, stcode, salesCode, customerpagernumber " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE.toUpperCase(),0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(customerPagerNumber, 0) + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
        } else {
            customerPagerNumber = "";
        }
        /*********************************************************************************************************/


        // JSON ----------------------------------------------------------------------
        jsonroot.put("customername", temp_customerName);
        jsonroot_kitchen.put("customername", temp_customerName);

        jsonroot.put("customerid",temp_customerId);
        jsonroot.put("customer_point_remaining", "0");


        // All Discount / Extra
        // JSON ----------------------------------------------------------------------
        jsonroot.put("alldiscountextraprice", GlobalMemberValues.getStringFormatNumber(dbl_allDiscountExtraPrice, "2"));
        if (!GlobalMemberValues.isStrEmpty(allDiscountExtraStr)) {
            jsonroot.put("alldiscountextrstr", allDiscountExtraStr);
        } else {
            jsonroot.put("alldiscountextrstr", "");
        }
        // ---------------------------------------------------------------------------

        // Extra - Discount 의 값
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totalextradiscountprice", GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2"));
        // ---------------------------------------------------------------------------

        // Discount
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totaldiscountprice", GlobalMemberValues.getStringFormatNumber(totalDiscountPrice, "2"));
        // ---------------------------------------------------------------------------

        // Extra
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totalextraprice", GlobalMemberValues.getStringFormatNumber(totalExtraPrice, "2"));
        // ---------------------------------------------------------------------------

        double sendTaxAmount = sales_taxAmount;
        if (sales_taxAmount == 0 && tempTaxAmountOrg > 0) {
            //sendTaxAmount = tempTaxAmountOrg;
        }

        jsonroot.put("subtotal", GlobalMemberValues.getStringFormatNumber(tempSalesAmount, "2"));
        jsonroot.put("tax", GlobalMemberValues.getStringFormatNumber(sendTaxAmount, "2"));
        jsonroot.put("totalamount", GlobalMemberValues.getStringFormatNumber(sales_totalAmount, "2"));

        jsonroot.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot.put("deliverypickupfee", GlobalMemberValues.getStringFormatNumber(tempDeliveryPickUpFee, "2"));

        jsonroot.put("customerordernumber", customerOrderNumber);
        jsonroot.put("customerpagernumber", customerPagerNumber);

        jsonroot.put("customermemo", temp_customermemo);
        jsonroot.put("checkcompany", mCheckCompany);

        jsonroot_kitchen.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot_kitchen.put("deliverydate", temp_deliverydate);
        jsonroot_kitchen.put("ordertype", "POS");

        jsonroot_kitchen.put("customermemo", temp_customermemo);

        jsonroot_kitchen.put("customerordernumber", customerOrderNumber);
        jsonroot_kitchen.put("customerpagernumber", customerPagerNumber);

        jsonroot_kitchen.put("customerpagerheader", GlobalMemberValues.PAGERNUMBERHEADERTXT);

        jsonroot_kitchen.put("checkcompany", mCheckCompany);

        jsonroot_kitchen.put("phoneordernumber", temp_phoneordernumber);

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot_kitchen.put("customerphonenum", temp_customerPhone);
        jsonroot_kitchen.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        // Restaurant 관련 데이터 ----------------------------------------------------
        jsonroot.put("restaurant_tablename", TableSaleMain.mTableName);
        jsonroot.put("restaurant_tablepeoplecnt", TableSaleMain.mTablePeopleCnt);
        jsonroot_kitchen.put("restaurant_tablename", TableSaleMain.mTableName);
        jsonroot_kitchen.put("restaurant_tablepeoplecnt", TableSaleMain.mTablePeopleCnt);
        // ---------------------------------------------------------------------------

        // mssql 을 사용할 경우 mssql 데이터베이스의 salon_sales_kitchenprintingdata_json 테이블에 데이터 저장
        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {

                GlobalMemberValues.logWrite("mssqljjjlog2", "쿼리 저장준비.." + "\n");
                GlobalMemberValues.logWrite("mssqljjjlog2", "주방json : " + jsonroot_kitchen.toString() + "\n");

                String tempPrintYN = "Y";
                if (!isOrderedOnTable) {
                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기0" + "\n");
                    tempPrintYN = "N";
                } else {
                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기1-1" + "\n");
                    if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                        GlobalMemberValues.logWrite("printlogjjjwhy", "여기1-2" + "\n");
                        String tempQuickSaleyn = "N";
                        tempTableIdx = GlobalMemberValues.getReplaceText(tempTableIdx, "T", "");
                        tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                        );
                        if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                            tempPrintYN = "N";
                        }
                    }
                }

                // salon_sales_kitchenprintingdata_json 에 데이터가 있는지 확인
                int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(*) from salon_sales_kitchenprintingdata_json where salescode = '" + mHoldCode + "' "
                ));
                if (tempCnt > 0) {
                    tempPrintYN = "Y";
//                    strDelSqlQuery = " update salon_sales_kitchenprintingdata_json set printedyn = 'Y' where salescode = '" + MainMiddleService.mHoldCode + "' ";
//                    strInsertQueryVec.addElement(strDelSqlQuery);
                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기2" + "\n");
                }

                GlobalMemberValues.logWrite("printlogjjjwhy", "tempPrintYN : " + tempPrintYN + "\n");

                // 01132023
                jsonroot_kitchen.put("holdcode", mHoldCode);


                // 01132023 ------------------------------------------------------------------------------------------
                jsonroot_kitchen.put("holdcode", mHoldCode);
                // ---------------------------------------------------------------------------------------------------

                GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기5 : " + jsonroot_kitchen.toString() + "\n");

                // 11102023
                if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonroot_kitchen.toString())) {
                    strDelSqlQuery = " insert into salon_sales_kitchenprintingdata_json " +
                            " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                            "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot_kitchen.toString(), 0) + "', " +
                            " '" + tempPrintYN + "'" +
                            " ) ";
                    strInsertQueryVec.addElement(strDelSqlQuery);
                }

            }
        }
        // ---------------------------------------------------------------------------



        /** salon_store_restaurant_table_split 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_split where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** salon_store_restaurant_table_merge 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_merge where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** salon_store_restaurant_table_peoplecnt 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_peoplecnt where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** temp_salecart 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** temp_salecart_deliveryinfo 테이블에서 해당 주문건을 삭제한다. ******************************************/
        strDelSqlQuery = "delete from temp_salecart_deliveryinfo where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** temp_salecart_optionadd 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart_optionadd where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** temp_salecart_optionadd_imsi 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart_optionadd_imsi where holdcode = '" + mHoldCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);
        /*********************************************************************************************************/

        /** 고객이 Walk in 이 아닌 경우 member1 테이블의 lastvisitForSale 의 값을 구매일시로 수정한다. **************/
        if (!GlobalMemberValues.isStrEmpty(temp_customerId)) {
            //strDelSqlQuery = "update member1 set lastvisitForSale = datetime('now', 'localtime') " +
            //        " where uid = '" + sales_customerId + "' ";
            strDelSqlQuery = "insert into member_salevisit (" +
                    " uid, name, lastvisitForSale " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerId, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerName, 0) + "', " +
                    " datetime('now', 'localtime') " +
                    ")";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart_del2,salon_newcartcheck_bystation 테이블 처리 ********************************************/
        if (!GlobalMemberValues.isStrEmpty(mHoldCode) && !mHoldCode.equals("NOHOLDCODE")) {
            strDelSqlQuery = "insert into temp_salecart_del2 ( " +
                    " holdcode, stcode, tableidx " +
                    " ) values ( " +
                    " '" + mHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + tempTableIdx + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strDelSqlQuery);

            // 해당 스테이션 외의 다른 스테이션의 salon_newcartcheck_bystation 데이터 지우기
            strDelSqlQuery = " delete from salon_newcartcheck_bystation where " +
                    " holdcode = '" + mHoldCode + "' " +
                    " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*******************************************************************************************************/


        /** 결제수단별 영수증 출력 JSON 만들기 ****************************************************************/
        jsonroot.put("cashtendered", "0");
        jsonroot.put("change", "0");
        jsonroot.put("creditcardtendered", "0");
        jsonroot.put("giftcardtendered", "0");

        jsonroot.put("checktendered", GlobalMemberValues.getStringFormatNumber(tempCheckAmount, "2"));
        jsonroot.put("pointtendered", "0");

        /*****************************************************************************************************/

        // JSON ----------------------------------------------------------------------
        // 프린트 타입   (1 : customer            2 : merchant                3 : 둘다)
        jsonroot.put("receiptprinttype", "3");
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("receiptfooter", GlobalMemberValues.getReceiptFooter());
        // ---------------------------------------------------------------------------

        // json 저장
        strDelSqlQuery = "update salon_sales set " +
                " receiptJson = '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot.toString(),0) + "' " +
                " where salescode = '" + mSalesCode + "' ";
        strInsertQueryVec.addElement(strDelSqlQuery);

        // bill pay 관련 json 에 데이터 저장
        jsonroot.put("billtype", "");
        jsonroot.put("billpayamount", "");
        jsonroot.put("billcartidx", "");
        jsonroot.put("billtotalamount", "");

        /** 멀티스테이션을 위한 영수증/주방프린팅용 json 저장. ********************************************************/
        // 영수증프린트 Json 저장
        String tempReceiptJson = jsonroot.toString();
        if (!GlobalMemberValues.isStrEmpty(tempReceiptJson)) {
            strDelSqlQuery = "insert into salon_sales_receipt_json (" +
                    " salesCode, scode, sidx, stcode, jsonstr " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempReceiptJson, 0) + "' " +
                    ")";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }

        // 주방프린트 Json 저장
        //String tempKitchenJson = jsonroot_kitchen.toString();
        if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {
            int insIsCloudUpload = 0;
            if (!GlobalMemberValues.isCloudKitchenPrinter()) {
                insIsCloudUpload = 1;
            }

            strDelSqlQuery = "insert into salon_sales_kitchen_json (" +
                    " salesCode, scode, sidx, stcode, jsonstr, isCloudUpload " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot_kitchen.toString(), 0) + "', " +
                    " '" + insIsCloudUpload + "' " +
                    ")";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        // 로그 파일 남기기
        GlobalMemberValues.backupSaleJsonData(jsonroot);

        if (strInsertQueryVec != null) {
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("paymentdbexelogjjj", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                mSalesCode = "";
            } else {
                GlobalMemberValues.eloCfdScreenViewInit();
                GlobalMemberValues.showMsgOnPaymentFinishment("Thanks for your purchase");
            }
        }

        return mSalesCode;
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (proDial != null && proDial.isShowing()) {
                proDial.dismiss();
            }

            if (mActivity != null) {
                mActivity.recreate();
            }

            if (TableSaleMain.mActivity != null) {
                mActivity.recreate();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null){
            executorService.shutdown();
        }
    }
}
