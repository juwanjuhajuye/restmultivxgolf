package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableSplittedList extends Activity {
    static Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    private ScrollView appcompanySv;
    private LinearLayout appcompanyLn;

    TextView titletv;
    ImageButton table_main_table_split_close;


    Intent mIntent;

    static String mTableIdx = "";
    static String mFrom = "";

    int mSubTableCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_splitted_list);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;

        parentLn = (LinearLayout)findViewById(R.id.table_main_table_split_ln);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mTableIdx = mIntent.getStringExtra("selectedTableIdx");
            mFrom = mIntent.getStringExtra("from");
            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mTableIdx : " + mTableIdx + "\n");
            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mFrom : " + mFrom + "\n");
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

    private void setContent() {
        TableSaleMain.selectedSubTableHoldCode = "";

        // 테이블이 split 되어 있는지 확인
        mSubTableCount = TableSaleMain.getTableSplitCount(mTableIdx);

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        titletv = (TextView)findViewById(R.id.titletv);
        titletv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + titletv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        table_main_table_split_close = (ImageButton)findViewById(R.id.table_main_table_split_close);
        table_main_table_split_close.setOnClickListener(btnClickListener);

        // 스크롤뷰 객체 생성
        appcompanySv = (ScrollView)findViewById(R.id.appcompanySv);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        appcompanyLn = (LinearLayout)findViewById(R.id.appcompanyLn);

        setTableSplittedList();
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.table_main_table_split_close : {
                    closeActivity();
                    break;
                }
            }
        }
    };

    private void setTableSplittedList() {
        appcompanyLn.removeAllViews();

        int btnHeight = 140;
        if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                btnHeight = 100;
            }
        } else {
            if (GlobalMemberValues.thisTabletRealWidth < 800) {
                btnHeight = 100;
            }
        }

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewBtnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
        subNewBtnLayoutParams.setMargins(0, 0, 0, 0);

        // LinearLayout 객체 생성
        LinearLayout newLn = new LinearLayout(mContext);
        newLn.setLayoutParams(newLnLayoutParams);
        newLn.setOrientation(LinearLayout.VERTICAL);
        newLn.setPadding(0, 0, 0, 0);

        String strQuery = " select subtablenum, holdcode from salon_store_restaurant_table_split " +
                " where tableidx like '%" + mTableIdx + "%' " +
                " order by subtablenum asc ";
        GlobalMemberValues.logWrite("splittedlog", "sql : " + strQuery + "\n");

        ResultSet splittedCursor = MssqlDatabase.getResultSetValue(strQuery);
//        Cursor splittedCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        try {
            while (splittedCursor.next()) {
                String tempSubTableNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(splittedCursor,0), 1);
                String tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(splittedCursor,1), 1);
                if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                    // 주문금액
                    double mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                            " select sum(sTotalAmount) from temp_salecart where holdcode = '" + tempHoldCode + "' "
                    ));
                    String amountStr = "";
                    if (mTableAmount > 0) {
                        amountStr = " ($" + GlobalMemberValues.getCommaStringForDouble(mTableAmount + "") + ")";
                    }

                    LinearLayout subNewLn = new LinearLayout(mContext);
                    subNewLn.setLayoutParams(subNewLnLayoutParams);
                    subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                    subNewLn.setPadding(0, 0, 0, 8);

                    Button subNewBtn = new Button(mContext);
                    subNewBtn.setLayoutParams(subNewBtnLayoutParams);
                    subNewBtn.setGravity(Gravity.CENTER);
                    subNewBtn.setText("SEAT #" + tempSubTableNum + amountStr);
                    subNewBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
                    subNewBtn.setTextColor(Color.parseColor("#ffffff"));
                    subNewBtn.setPaintFlags(subNewBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay_selected);

                    subNewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectSubTable(tempSubTableNum, tempHoldCode);
                        }
                    });
                    subNewLn.addView(subNewBtn);
                    newLn.addView(subNewLn);
                }
            }
            appcompanyLn.addView(newLn);
        } catch (Exception e){

        }

        //07052024 close resultset
        try {
            if(!splittedCursor.isClosed()){
                splittedCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void selectSubTable(String paramSubTableNum, String paramHoldCode) {
        //MainMiddleService.mHoldCode = GlobalMemberValues.makeHoldCode();
        ArrayList<String> mArr = new ArrayList<String>();
        mArr.add(mTableIdx);

        closeActivity();

        TableSaleMain.mSubTableNum = paramSubTableNum;
        TableSaleMain.selectedSubTableHoldCode = paramHoldCode;

        //TableSaleMain.setInitValues();
        switch (mFrom) {
            case "order" : {
                GlobalMemberValues.logWrite("taesooanniejjjlog", "mTableIdx : " + mTableIdx + "\n");
                GlobalMemberValues.logWrite("taesooanniejjjlog", "paramHoldCode : " + paramHoldCode + "\n");
                GlobalMemberValues.logWrite("taesooanniejjjlog (TSL)", "paramSubTableNum : " + paramSubTableNum + "\n");

                TableSaleMain.setOrderStart(mArr, false, false);
                break;
            }
            case "billsplit" : {
                TableSaleMain.openBillSplit();
                break;
            }
            case "billprint" : {
                TableSaleMain.openBillPrint("N");
                break;
            }
            case "kitchenprinting" : {
                TableSaleMain.kitchenPrint(paramHoldCode, true);
                break;
            }
            case "detailinfo" : {
                TableSaleMain.openDetailInfo();
                break;
            }
        }
    }

    public static void closeActivity() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }
}
