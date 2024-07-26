package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class MemoToKitchen extends Activity {
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

    TemporarySaleCart parentTemporarySaleCart;
    String parentSelectedPosition;

    String mKitchenmemotype = "";
    String mRegmemotxt = "";

    Spinner spinner_memoReason;
    ArrayAdapter<String> mSpinnerAdapter;
    ArrayList<String> mArraySpinner;

    Button btn_reason1, btn_reason2, btn_reason3, btn_reason4, btn_reason5;

    TableLayout tableLayout;
    ArrayList<String> arrayList_selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_memo_to_kitchen);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {

            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            parentTemporarySaleCart = (TemporarySaleCart)mIntent.getSerializableExtra("ParentTemporarySaleCartInstance");
            // 부모로부터 받은 선택포시션 값
            parentSelectedPosition = mIntent.getStringExtra("ParentSaleCartPosition");
            // 부모로부터 받은 키친메모 타입
            mKitchenmemotype = mIntent.getStringExtra("kitchenmemotype");
            // 부모로부터 받은 등록된 키친메모
            mRegmemotxt = mIntent.getStringExtra("regmemotxt");
            /*******************************************************************************************/

            if (GlobalMemberValues.isStrEmpty(mKitchenmemotype)) {
                mKitchenmemotype = "";
            }
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
        if (parentTemporarySaleCart != null) {
            if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSvcName)) {
                titleTv.setText("MEMO (" + parentTemporarySaleCart.mSvcName + ")");
            }
        }

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
                        insertKitchenMemo();
                        break;
                    case EditorInfo.IME_ACTION_DONE:
                        insertKitchenMemo();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        if (mKitchenmemotype.equals("modifier")) {
            kitchenMemoEv.setText(mRegmemotxt);
        } else {
            String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
            if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                String tempKitchenMemo = MssqlDatabase.getResultSetValueToString("select memoToKitchen from temp_salecart where idx = '" + tempSaleCartIdx + "' ");
                kitchenMemoEv.setText(tempKitchenMemo);
            }
        }
        getSelectReasonArray();
//        btn_reason1 = (Button)findViewById(R.id.memo_to_kitchen_btn1);
//        btn_reason1.setOnClickListener(reason_Listener);
//        btn_reason2 = (Button)findViewById(R.id.memo_to_kitchen_btn2);
//        btn_reason2.setOnClickListener(reason_Listener);
//        btn_reason3 = (Button)findViewById(R.id.memo_to_kitchen_btn3);
//        btn_reason3.setOnClickListener(reason_Listener);
//        btn_reason4 = (Button)findViewById(R.id.memo_to_kitchen_btn4);
//        btn_reason4.setOnClickListener(reason_Listener);
//        btn_reason5 = (Button)findViewById(R.id.memo_to_kitchen_btn5);
//        btn_reason5.setOnClickListener(reason_Listener);


        setTableLayout(false);

//        mArraySpinner = new ArrayList<String>();
//        mArraySpinner = GlobalMemberValues.getSpecialRequest();
//
//
//        if (mArraySpinner.size() == 0){
//            TableRow row= new TableRow(this);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//            row.setMinimumHeight(70);
//            row.setLayoutParams(lp);
//            Button addBtn = new Button(this);
//            addBtn.setBackgroundResource(R.drawable.roundlayout_white_gray);
//            addBtn.setGravity(Gravity.CENTER);
//            addBtn.setText("no item");
//            addBtn.setPadding(5,5,5,0);
//            addBtn.setMinHeight(70);
//            row.addView(addBtn);
//            tableLayout.setStretchAllColumns(true);
//            tableLayout.addView(row,0);
//
//        } else {
//
//            int rowcnt = (mArraySpinner.size() / 5 );
//            rowcnt = rowcnt + 1;
//            for (int x = 0 ; rowcnt > x ; x++){
//
//                TableRow row= new TableRow(this);
//                TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//                row.setMinimumHeight(100);
//                row.setLayoutParams(lp);
//
//                int cnt = 0;
//
//                for (int z = x * 5 ; mArraySpinner.size() > z ; z++){
//                    Button addBtn = new Button(this);
//
//                    addBtn.setBackgroundResource(R.drawable.ab_button_memotokitchen_reason);
//
//                    addBtn.setGravity(Gravity.CENTER);
//                    addBtn.setText(mArraySpinner.get(z));
//                    addBtn.setTag(z);
//                    addBtn.setOnClickListener(reason_Listener);
//                    addBtn.setPadding(5,5,5,0);
//                    addBtn.setMinHeight(100);
//
//                    if (arrayList_selected.contains(mArraySpinner.get(z))){
//                        addBtn.setSelected(true);
//                    }
//
//                    row.addView(addBtn);
//                    if (cnt == 4) break;
//                    cnt++;
//                }
//                tableLayout.setScrollContainer(true);
//                tableLayout.setStretchAllColumns(true);
//                tableLayout.addView(row,x);
//
//            }
//        }




//        mArraySpinner = new ArrayList<String>();
//        mArraySpinner = GlobalMemberValues.getSpecialRequest();
//        if (mArraySpinner.size() == 0){
//            mArraySpinner.add("no item");
//        }
//        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mArraySpinner);
//        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_memoReason = (Spinner)findViewById(R.id.spinner_memoReason);
//        spinner_memoReason.setAdapter(mSpinnerAdapter);
//        if (mArraySpinner.contains("no item")){
//            spinner_memoReason.setSelection(0);
//        } else {
//            spinner_memoReason.setOnItemSelectedListener(mSpinnerItemSelectedListener);
//        }

    }

    private void setTableLayout(boolean b_setClear){
        mArraySpinner = new ArrayList<String>();
        mArraySpinner = GlobalMemberValues.getSpecialRequest();

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
                    insertKitchenMemo();
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

//                    kitchenMemoEv.sendAccessibilityEvent(KeyEvent.KEYCODE_ENTER);
//                    kitchenMemoEv.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
                    break;
                }
            }
        }
    };

    private void insertKitchenMemo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

        String tempMemoToKitchen = kitchenMemoEv.getText().toString();

        if (mKitchenmemotype.equals("modifier")) {
            MainMiddleServiceModifer.memotokitchenEv.setText(tempMemoToKitchen);
            closeActivity();
        } else {
            String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
            if (GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                GlobalMemberValues.displayDialog(context, "Warning", "Error..", "Close");
                return;
            }

            GlobalMemberValues.logWrite(TAG, "tempMemoToKitchen : " + tempMemoToKitchen + "\n");

            Vector<String> strUpdateQueryVec = new Vector<String>();
            String strQuery = "";
            strQuery = " update temp_salecart set " +
                    " memoToKitchen = '" + tempMemoToKitchen + "' " +
                    " where idx = '" + tempSaleCartIdx + "' ";
            strUpdateQueryVec.addElement(strQuery);
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
            }
            String returnResult = "";
            returnResult = MssqlDatabase.executeTransaction(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                parentTemporarySaleCart.kitchenMemo = tempMemoToKitchen;
                // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                MainMiddleService.mGeneralArrayList.set(Integer.parseInt(parentSelectedPosition), parentTemporarySaleCart);
                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                // jihun park add up
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();
                }
                closeActivity();
            }
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
