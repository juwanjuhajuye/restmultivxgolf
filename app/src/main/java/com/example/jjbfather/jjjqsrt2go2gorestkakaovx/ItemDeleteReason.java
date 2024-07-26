package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class ItemDeleteReason extends Activity {
    final String TAG = "MemoToKitchenLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView titleTv;
    private EditText kitchenMemoEv;
    private Button kitchenMemoBtn, kitchenMemoClearBtn, kitchenMemoCloseBtn, kitchenMemoNextBtn;

    Intent mIntent;

    ArrayList<String> mArraySpinner;

    String str_is_delete_clear;
    TableLayout tableLayout;
    ArrayList<String> arrayList_selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_itemdeletereason);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {

            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/

            str_is_delete_clear = mIntent.getStringExtra("is_delete_clear");

            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        titleTv = (TextView) findViewById(R.id.titleTv);
        titleTv.setTextSize(
                titleTv.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        tableLayout = (TableLayout) findViewById(R.id.kitchen_memo_table_ln);

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        kitchenMemoCloseBtn = (Button) findViewById(R.id.kitchenMemoCloseBtn);
        kitchenMemoCloseBtn.setOnClickListener(kitchenMemoButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            kitchenMemoCloseBtn.setText("");
            kitchenMemoCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            kitchenMemoCloseBtn.setTextSize(
                    kitchenMemoCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        kitchenMemoBtn = (Button)findViewById(R.id.kitchenMemoBtn);
        kitchenMemoBtn.setOnClickListener(kitchenMemoButtonClickListener);
        kitchenMemoBtn.setTextSize(
                kitchenMemoBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        kitchenMemoClearBtn = (Button)findViewById(R.id.kitchenMemoClearBtn);
        kitchenMemoClearBtn.setOnClickListener(kitchenMemoButtonClickListener);
        kitchenMemoClearBtn.setTextSize(
                kitchenMemoClearBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        kitchenMemoNextBtn = (Button)findViewById(R.id.kitchenMemoNextBtn);
        kitchenMemoNextBtn.setOnClickListener(kitchenMemoButtonClickListener);
        kitchenMemoNextBtn.setTextSize(
                kitchenMemoNextBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        kitchenMemoEv = (EditText)findViewById(R.id.kitchenMemoEv);
        kitchenMemoEv.setTextSize(
                kitchenMemoEv.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );
        kitchenMemoEv.setText("");
        kitchenMemoEv.setOnTouchListener(kitchenMemoEvTouchListener);
        kitchenMemoEv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
//        kitchenMemoEv.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        kitchenMemoEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        insertDeleteReason();
                        break;
                    case EditorInfo.IME_ACTION_DONE:
                        insertDeleteReason();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

//        if (mKitchenmemotype.equals("modifier")) {
//            kitchenMemoEv.setText(mRegmemotxt);
//        } else {
//            String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
//            if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
//                String tempKitchenMemo = MssqlDatabase.getResultSetValueToString("select memoToKitchen from temp_salecart where idx = '" + tempSaleCartIdx + "' ");
//                kitchenMemoEv.setText(tempKitchenMemo);
//            }
//        }
        getSelectReasonArray();


        setTableLayout(false);


    }

    private void setTableLayout(boolean b_setClear){
        mArraySpinner = new ArrayList<String>();

        // 11012023
        mArraySpinner = GlobalMemberValues.getItemDeleteReason();

        if (b_setClear){
            tableLayout.removeAllViews();
            arrayList_selected.clear();
        }

        if (mArraySpinner.size() == 0){
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
            row.setMinimumHeight(70);
            row.setLayoutParams(lp);
            Button addBtn = new Button(this);
            addBtn.setBackgroundResource(R.drawable.roundlayout_white_gray);
            addBtn.setGravity(Gravity.CENTER);
            addBtn.setText("no item");
            addBtn.setPadding(5,5,5,0);
            addBtn.setMinHeight(70);
            row.addView(addBtn);
            tableLayout.setStretchAllColumns(true);
            tableLayout.addView(row,0);

        } else {

            int rowcnt = (mArraySpinner.size() / 5 );
            rowcnt = rowcnt + 1;
            for (int x = 0 ; rowcnt > x ; x++){

                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                row.setMinimumHeight(100);
                row.setLayoutParams(lp);

                int cnt = 0;

                for (int z = x * 5 ; mArraySpinner.size() > z ; z++){
                    Button addBtn = new Button(this);

                    addBtn.setBackgroundResource(R.drawable.ab_button_memotokitchen_reason);

                    addBtn.setGravity(Gravity.CENTER);
                    addBtn.setText(mArraySpinner.get(z));
                    addBtn.setTag(z);
                    addBtn.setOnClickListener(reason_Listener);
                    addBtn.setPadding(5,5,5,0);
                    addBtn.setMinHeight(100);

                    if (arrayList_selected.contains(mArraySpinner.get(z))){
                        addBtn.setSelected(true);
                    }

                    row.addView(addBtn);
                    if (cnt == 4) break;
                    cnt++;
                }
                tableLayout.setScrollContainer(true);
                tableLayout.setStretchAllColumns(true);
                tableLayout.addView(row,x);

            }
        }
    }

    View.OnTouchListener kitchenMemoEvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    private void setMemoClearEditText() {
        kitchenMemoEv.setSelection(0);
        kitchenMemoEv.setText("");
        GlobalMemberValues.setUseableOrNotEditText(kitchenMemoEv, true);
        kitchenMemoEv.setBackgroundResource(R.drawable.roundlayout_background_custmemo);
        kitchenMemoEv.requestFocus();
    }

    View.OnClickListener kitchenMemoButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kitchenMemoCloseBtn : {
                    LogsSave.saveLogsInDB(112);
                    closeActivity();
                }
                case R.id.kitchenMemoBtn : {
                    LogsSave.saveLogsInDB(113);
                    insertDeleteReason();
                    break;
                }
                case R.id.kitchenMemoClearBtn : {
                    LogsSave.saveLogsInDB(114);
                    kitchenMemoEv.setText("");
                    kitchenMemoEv.requestFocus();
                    setTableLayout(true);
                    break;
                }
                case R.id.kitchenMemoNextBtn: {
                    LogsSave.saveLogsInDB(115);
                    String temp = kitchenMemoEv.getText().toString();
                    if (!temp.isEmpty()) {
                        kitchenMemoEv.append("\n");
                        kitchenMemoEv.setSelection(kitchenMemoEv.getText().length());
                    }

                    break;
                }
            }
        }
    };

    private void insertDeleteReason() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

        String tempMemoToKitchen = kitchenMemoEv.getText().toString();

        if (!GlobalMemberValues.isStrEmpty(tempMemoToKitchen)) {
            Vector<String> strUpdateQueryVec = new Vector<String>();

            String serverIdx = GlobalMemberValues.SERVER_IDX;
            if (GlobalMemberValues.isStrEmpty(serverIdx)) {
                serverIdx = "";
            }

            String serverName = GlobalMemberValues.SERVER_NAME;
            if (GlobalMemberValues.isStrEmpty(serverName)) {
                serverName = "";
            }

            String strQuery = " insert into salon_itemdeletereason (stcode, holdcode, serveridx, servername, reason) values ( " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + MainMiddleService.mHoldCode + "', " +
                    " '" + serverIdx + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(serverName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempMemoToKitchen, 0) + "' " +
                    " ) ";
            strUpdateQueryVec.addElement(strQuery);
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
            }
            String returnResult = "";
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // 아래에 아이템 Delete 또는 Cancel 처리하는 기능을 넣을것
                // =======================================================================================

                if (str_is_delete_clear.equals("delete")){
                    MainMiddleService.item_delete_action();
                } else if (str_is_delete_clear.equals("clear")){
                    MainMiddleService.item_delete_action_all();
                }


                // =======================================================================================



                closeActivity();
            }

        } else {

        }


    }

    private void closeActivity() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_top);
        }
    }

    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3866e0"));

            String selectedItemSpinner = mArraySpinner.get(position);
            kitchenMemoEv.setText(selectedItemSpinner);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    View.OnClickListener reason_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null){

                if (v.isSelected()){
                    v.setSelected(false);

                } else {
                    v.setSelected(true);
                }
                setSelectReasonArray((int)v.getTag(), v.isSelected());



            }
        }
    };

    private void setSelectReasonArray(int selectTag, boolean isSelected){
        if (isSelected){
            arrayList_selected.add(mArraySpinner.get(selectTag));

            String temp = mArraySpinner.get(selectTag);

            if (kitchenMemoEv.getText().toString().length() > 0) {
                kitchenMemoEv.setText(kitchenMemoEv.getText().toString() + "," + temp);
            } else {
                kitchenMemoEv.setText(temp);
            }
            kitchenMemoEv.setSelection(kitchenMemoEv.getText().length());
        } else {
            int a = arrayList_selected.indexOf(mArraySpinner.get(selectTag));
            arrayList_selected.remove(a);

            String temp = mArraySpinner.get(selectTag);

            String temp_old = kitchenMemoEv.getText().toString();

            temp_old = temp_old.replace(temp,"");

            if (temp_old.length() > 0){
                if (temp_old.substring(0,1).equals(",")) temp_old = temp_old.substring(1,temp_old.length());
                if (temp_old.substring(temp_old.length()-1).equals(",")) temp_old = temp_old.substring(0,temp_old.length()-1);
            }

            kitchenMemoEv.setText(temp_old);
            kitchenMemoEv.setSelection(kitchenMemoEv.getText().length());
        }

//        String temp = mArraySpinner.get(selectTag);
//
//        kitchenMemoEv.setText(kitchenMemoEv.getText().toString() + "," + temp);
//        kitchenMemoEv.setSelection(kitchenMemoEv.getText().length());

//        for (int a = 0; arrayList_selected.size() > a ; a++){
//            String str = arrayList_selected.get(a);
//            if (kitchenMemoEv.getText().toString().isEmpty()) {
//                kitchenMemoEv.setText(str);
//            } else {
//                kitchenMemoEv.setText(kitchenMemoEv.getText().toString() + "," + str);
//                kitchenMemoEv.setSelection(kitchenMemoEv.getText().length());
//            }
//        }
    }

    private void getSelectReasonArray(){
        if (kitchenMemoEv.getText().toString() == null) return;
        String[] arrayList = kitchenMemoEv.getText().toString().split(",");
        if(arrayList != null) {
            for (int a = 0 ; arrayList.length > a ; a++){
                arrayList_selected.add(arrayList[a]);
            }
        }
    }
}
