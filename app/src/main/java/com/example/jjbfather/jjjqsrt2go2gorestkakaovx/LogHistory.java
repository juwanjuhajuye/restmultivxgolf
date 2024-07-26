package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.util.ArrayList;

public class LogHistory extends Activity {

   ImageButton ibtn_close, ibtn_search;
   ListView log_list;
   EditText etxt_search_txt;
   TextView txt_date;

   ArrayList<ArrayList> arr_list;
   LogListAdapter logListAdapter;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_loghistory);
      Intent intent = getIntent();
      setContent();
      setListview();

      if (intent != null) {
         String salescode = intent.getStringExtra("salescode");
         String holdcode = intent.getStringExtra("holdcode");
         if (salescode != null) {
            // Sale History List 에 SaleCode 로 Log DB 검색이 제대로 되지 않아서
            // SalesCode 로 salon_sales HoldCode 를 가져와 HoldCode 로 Log DB 를 검색.
            String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select holdCode from salon_sales where salesCode = '" + salescode + "'");

//            salescode = salescode.substring(1);
            etxt_search_txt.setText(tempValue);
         } else if (holdcode != null){
            holdcode = holdcode.substring(1);
            etxt_search_txt.setText(holdcode);
         }

         ibtn_search.callOnClick();
      } else {

      }
   }

   private void setContent(){
      ibtn_close = findViewById(R.id.log_history_close);
      ibtn_close.setOnClickListener(onCloseClickListener);
      txt_date = findViewById(R.id.log_history_date_tv);
      txt_date.setOnClickListener(onDateClickListener);


      log_list = findViewById(R.id.log_list);

      etxt_search_txt = findViewById(R.id.log_history_search_txt);
      ibtn_search = findViewById(R.id.log_history_search_btn);
      ibtn_search.setOnClickListener(onSearchBtnClickListener);


   }

   private void setListview(){

      arr_list = new ArrayList<>();

      String strQuery = "select scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, wdate, salescode from btn_logs order by wdate desc";
      ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
      try {
         while (resultSet.next()){
            String tempscode = GlobalMemberValues.resultDB_checkNull_string(resultSet,0);
            String tempsidx = GlobalMemberValues.resultDB_checkNull_string(resultSet,1);
            String tempstcode = GlobalMemberValues.resultDB_checkNull_string(resultSet,2);
            String tempbtnpagename = GlobalMemberValues.resultDB_checkNull_string(resultSet,3);
            String tempbtnname = GlobalMemberValues.resultDB_checkNull_string(resultSet,4);
            String tempbtnlogkor = GlobalMemberValues.resultDB_checkNull_string(resultSet,5);
            String tempbtnlogeng = GlobalMemberValues.resultDB_checkNull_string(resultSet,6);
            String tempemployeeIdx = GlobalMemberValues.resultDB_checkNull_string(resultSet,7);
            String tempemployeeName = GlobalMemberValues.resultDB_checkNull_string(resultSet,8);
            String tempwdate = GlobalMemberValues.resultDB_checkNull_string(resultSet,9);
            String tempSalesCode = GlobalMemberValues.resultDB_checkNull_string(resultSet,10);

            ArrayList<String> temp_arr = new ArrayList<>();
            if (tempscode != null) temp_arr.add(tempscode);
            if (tempsidx != null) temp_arr.add(tempsidx);
            if (tempstcode != null) temp_arr.add(tempstcode);
            if (tempbtnpagename != null) temp_arr.add(tempbtnpagename);
            if (tempbtnname != null) temp_arr.add(tempbtnname);
            if (tempbtnlogkor != null) temp_arr.add(tempbtnlogkor);
            if (tempbtnlogeng != null) temp_arr.add(tempbtnlogeng);
            if (tempemployeeIdx != null) temp_arr.add(tempemployeeIdx);
            if (tempemployeeName != null) temp_arr.add(tempemployeeName);
            if (tempwdate != null) temp_arr.add(tempwdate);
            if (tempSalesCode != null) temp_arr.add(tempSalesCode);

            if (!temp_arr.isEmpty()){
               arr_list.add(temp_arr);
            }

         }
      }catch (Exception e){

      }


//      Cursor cursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
//      while (cursor.moveToNext()) {
//         String tempscode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
//         String tempsidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
//         String tempstcode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
//         String tempbtnpagename = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1);
//         String tempbtnname = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
//         String tempbtnlogkor = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
//         String tempbtnlogeng = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1);
//         String tempemployeeIdx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1);
//         String tempemployeeName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1);
//         String tempwdate = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1);
//         String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1);
//
//         ArrayList<String> temp_arr = new ArrayList<>();
//         if (tempscode != null) temp_arr.add(tempscode);
//         if (tempsidx != null) temp_arr.add(tempsidx);
//         if (tempstcode != null) temp_arr.add(tempstcode);
//         if (tempbtnpagename != null) temp_arr.add(tempbtnpagename);
//         if (tempbtnname != null) temp_arr.add(tempbtnname);
//         if (tempbtnlogkor != null) temp_arr.add(tempbtnlogkor);
//         if (tempbtnlogeng != null) temp_arr.add(tempbtnlogeng);
//         if (tempemployeeIdx != null) temp_arr.add(tempemployeeIdx);
//         if (tempemployeeName != null) temp_arr.add(tempemployeeName);
//         if (tempwdate != null) temp_arr.add(tempwdate);
//         if (tempSalesCode != null) temp_arr.add(tempSalesCode);
//
//         if (!temp_arr.isEmpty()){
//            arr_list.add(temp_arr);
//         }
//
//      }

      logListAdapter = new LogListAdapter(this.getApplicationContext(),arr_list);
      log_list.setAdapter(logListAdapter);
   }

   public void openDatePickerDialog(String paramDate) {
      String tempSplitStr[] = null;
      tempSplitStr = paramDate.split("-");
      int tempMonth = Integer.parseInt(tempSplitStr[1]);
      int tempDay = Integer.parseInt(tempSplitStr[2]);
      int tempYear = Integer.parseInt(tempSplitStr[0]);
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
         txt_date.setText(tempYear + "-" + tempMonth + "-" + tempDay);

         if (txt_date.getText().length() != 0){
//            String query = "select scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, wdate, salescode from btn_logs WHERE wdate BETWEEN '" + txt_date.getText() + "' AND date('" + txt_date.getText() +"', '+1 days' ) order by wdate desc";
            String query = "select scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, wdate, salescode from btn_logs WHERE wdate BETWEEN '" + txt_date.getText() + "' AND DATEADD(DAY, 1, '" + txt_date.getText() +"') order by wdate desc";
            setListviewSearch(query);
         }

      }
   };


   private void setListviewSearch(String strQuery) {

      arr_list = new ArrayList<>();

      ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
      try {
         while (resultSet.next()) {
            String tempscode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
            String tempsidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
            String tempstcode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
            String tempbtnpagename = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);
            String tempbtnname = GlobalMemberValues.resultDB_checkNull_string(resultSet, 4);
            String tempbtnlogkor = GlobalMemberValues.resultDB_checkNull_string(resultSet, 5);
            String tempbtnlogeng = GlobalMemberValues.resultDB_checkNull_string(resultSet, 6);
            String tempemployeeIdx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 7);
            String tempemployeeName = GlobalMemberValues.resultDB_checkNull_string(resultSet, 8);
            String tempwdate = GlobalMemberValues.resultDB_checkNull_string(resultSet, 9);
            String tempSalesCode = GlobalMemberValues.resultDB_checkNull_string(resultSet, 10);

            ArrayList<String> temp_arr = new ArrayList<>();
            if (tempscode != null) temp_arr.add(tempscode);
            if (tempsidx != null) temp_arr.add(tempsidx);
            if (tempstcode != null) temp_arr.add(tempstcode);
            if (tempbtnpagename != null) temp_arr.add(tempbtnpagename);
            if (tempbtnname != null) temp_arr.add(tempbtnname);
            if (tempbtnlogkor != null) temp_arr.add(tempbtnlogkor);
            if (tempbtnlogeng != null) temp_arr.add(tempbtnlogeng);
            if (tempemployeeIdx != null) temp_arr.add(tempemployeeIdx);
            if (tempemployeeName != null) temp_arr.add(tempemployeeName);
            if (tempwdate != null) temp_arr.add(tempwdate);
            if (tempSalesCode != null) temp_arr.add(tempSalesCode);

            if (!temp_arr.isEmpty()) {
               arr_list.add(temp_arr);
            }
         }
      } catch (Exception e){

      }

//            Cursor cursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
//      while (cursor.moveToNext()) {
//         String tempscode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
//         String tempsidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
//         String tempstcode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
//         String tempbtnpagename = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1);
//         String tempbtnname = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
//         String tempbtnlogkor = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
//         String tempbtnlogeng = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1);
//         String tempemployeeIdx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1);
//         String tempemployeeName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1);
//         String tempwdate = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1);
//         String tempsalesCode = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1);
//
//         ArrayList<String> temp_arr = new ArrayList<>();
//         if (tempscode != null) temp_arr.add(tempscode);
//         if (tempsidx != null) temp_arr.add(tempsidx);
//         if (tempstcode != null) temp_arr.add(tempstcode);
//         if (tempbtnpagename != null) temp_arr.add(tempbtnpagename);
//         if (tempbtnname != null) temp_arr.add(tempbtnname);
//         if (tempbtnlogkor != null) temp_arr.add(tempbtnlogkor);
//         if (tempbtnlogeng != null) temp_arr.add(tempbtnlogeng);
//         if (tempemployeeIdx != null) temp_arr.add(tempemployeeIdx);
//         if (tempemployeeName != null) temp_arr.add(tempemployeeName);
//         if (tempwdate != null) temp_arr.add(tempwdate);
//         if (tempsalesCode != null) temp_arr.add(tempsalesCode);
//
//         if (!temp_arr.isEmpty()) {
//            arr_list.add(temp_arr);
//         }
//      }

      logListAdapter = new LogListAdapter(this.getApplicationContext(),arr_list);
      log_list.setAdapter(logListAdapter);
   }

   View.OnClickListener onSearchBtnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         if (!etxt_search_txt.getText().toString().isEmpty()){

            String date = "";
            if (txt_date.getText().length() != 0){
//               date = "wdate BETWEEN '" + txt_date.getText() + "' AND date('" + txt_date.getText() +"', '+1 days' ) and ";
               date = "wdate BETWEEN '" + txt_date.getText() + "' AND DATEADD(DAY, 1, '" + txt_date.getText() +"') and ";
            }

            String paramSearchValue = etxt_search_txt.getText().toString();
            String query2  = " ( " +
                    " scode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or sidx like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or stcode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or btnpagename like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or btnname like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or btnlogkor like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or btnlogeng like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or employeeIdx like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or employeeName like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or salescode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " or holdcode like '%" + GlobalMemberValues.getDBTextAfterChecked(paramSearchValue, 0) + "%' " +
                    " ) ";
            String query = "select scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, wdate, salescode, holdcode from btn_logs WHERE " + date + query2 + " order by wdate desc";

            setListviewSearch(query);
         }
      }
   };

   View.OnClickListener onDateClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         String tempClockInOutSearchDay = txt_date.getText().toString();
         if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
            tempClockInOutSearchDay = DateMethodClass.nowYearGet() + "-" +DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet();
         }
         openDatePickerDialog(tempClockInOutSearchDay);
      }
   };

   View.OnClickListener onCloseClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         switch (v.getId()){
            case R.id.log_history_close:
               finish();
               break;
         }
      }
   };
}
