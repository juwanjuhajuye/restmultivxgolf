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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class TableSplit extends Activity {
    Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    TextView titletv;
    Button counttableBtn1, counttableBtn2, counttableBtn3, counttableBtn4;
    ImageButton table_main_table_split_close;

    Intent mIntent;

    String mTableIdx = "";
    int mSubTableCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_split);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;

        parentLn = (LinearLayout)findViewById(R.id.table_main_table_split_ln);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mTableIdx = mIntent.getStringExtra("selectedTableIdx");
            GlobalMemberValues.logWrite("selectedTableIdxLog", "넘겨받은 mTableIdx : " + mTableIdx + "\n");
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

    private void setContent(){
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

        counttableBtn1 = (Button) findViewById(R.id.counttableBtn1);
        counttableBtn1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + counttableBtn1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        if (mSubTableCount == 2) {
            counttableBtn1.setBackgroundResource(R.drawable.button_selector_bluebutton2);
        }

        counttableBtn2 = (Button) findViewById(R.id.counttableBtn2);
        counttableBtn2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + counttableBtn2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        if (mSubTableCount == 3) {
            counttableBtn2.setBackgroundResource(R.drawable.button_selector_bluebutton2);
        }

        counttableBtn3 = (Button) findViewById(R.id.counttableBtn3);
        counttableBtn3.setTextSize(GlobalMemberValues.globalAddFontSize()
                + counttableBtn3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        if (mSubTableCount == 4) {
            counttableBtn3.setBackgroundResource(R.drawable.button_selector_bluebutton2);
        }

        counttableBtn4 = (Button) findViewById(R.id.counttableBtn4);
        counttableBtn4.setTextSize(GlobalMemberValues.globalAddFontSize()
                + counttableBtn4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        if (mSubTableCount == 5) {
            counttableBtn4.setBackgroundResource(R.drawable.button_selector_bluebutton2);
        }

        table_main_table_split_close = (ImageButton)findViewById(R.id.table_main_table_split_close);


        counttableBtn1.setOnClickListener(btnClickListener);
        counttableBtn2.setOnClickListener(btnClickListener);
        counttableBtn3.setOnClickListener(btnClickListener);
        counttableBtn4.setOnClickListener(btnClickListener);
        table_main_table_split_close.setOnClickListener(btnClickListener);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.counttableBtn1 : {
                    setTableSplitAlert(2);
                    break;
                }
                case R.id.counttableBtn2 : {
                    setTableSplitAlert(3);
                    break;
                }
                case R.id.counttableBtn3 : {
                    setTableSplitAlert(4);
                    break;
                }
                case R.id.counttableBtn4 : {
                    setTableSplitAlert(5);
                    break;
                }
                case R.id.table_main_table_split_close : {
                    closeActivity();
                    break;
                }
            }
        }
    };

    public void setTableSplitAlert(int paramTableCount) {
        if (paramTableCount > 0) {
            if (paramTableCount < mSubTableCount) {
                if ((mActivity != null) && (!mActivity.isFinishing())) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Table Merge")
                            .setMessage("It is less than the number of tables splitted. Would you like to continue?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            setTableSplit(paramTableCount);
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                            .show();
                }
            } else {
                if (paramTableCount == mSubTableCount) {
                    closeActivity();
                    return;
                }
                if (paramTableCount > mSubTableCount) {
                    setTableSplit(paramTableCount);
                }
            }

        }
    }

    public void setTableSplit(int paramTableCount) {
        if (paramTableCount > 0) {
            String strQuery = "";
            Vector<String> queryVec = new Vector<String>();

            strQuery = "delete from salon_store_restaurant_table_split where tableidx like '" + mTableIdx + "' ";
            queryVec.addElement(strQuery);

            for (int jjj = 0; jjj < paramTableCount; jjj++) {
                String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                        " select holdcode from temp_salecart where tableidx like '%" + mTableIdx + "%' and subtablenum = '" + (jjj + 1) + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                    temp_holdcode = GlobalMemberValues.makeHoldCodeByTableIdx(mTableIdx, (jjj + 1));
                }

                strQuery = " insert into salon_store_restaurant_table_split " +
                        " (tableidx, subtablenum, holdcode) values ( " +
                        "'" + mTableIdx + "', " +
                        "'" + (jjj + 1) + "', " +
                        "'" + temp_holdcode + "' " +
                        " )";
                queryVec.addElement(strQuery);
            }

            if (queryVec.size() > 0) {
                for (String tempQuery : queryVec) {
                    GlobalMemberValues.logWrite("splitsqllog", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(queryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {
                    TableSaleMain.isFocusViewTable = true;
                    closeActivity();
                }
            }
        }
    }

    public void closeActivity() {
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
