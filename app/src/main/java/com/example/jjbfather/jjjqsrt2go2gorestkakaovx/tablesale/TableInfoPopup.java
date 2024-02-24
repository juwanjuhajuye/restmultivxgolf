package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DatabaseInit;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;


public class TableInfoPopup extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;

    private TextView table_info_name,table_info_capacity,table_info_menu_total;
    private ListView table_info_menu;

    private String str_table_name, str_table_capacity, str_table_menu, str_table_menu_total;


    Intent mIntent;

    private static final String TAG = "TableInfoPopup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_tableinfo_popup);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 40;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.table_infopopup_main_ln);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값

            str_table_name = mIntent.getStringExtra("table_info_name");
            str_table_capacity = mIntent.getStringExtra("table_info_capacity");
            str_table_menu = mIntent.getStringExtra("table_info_menu");
            str_table_menu_total = mIntent.getStringExtra("table_info_menu_total");

//            webOrdersSaleItems = GlobalMemberValues.getReplaceText(webOrdersSaleItems, "JUWANJUHAJUYE", "-ANNIETTASU-");

            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
//        startNotificationSound();

    }

    public void setContents() {

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(table_info_clicklistener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        ArrayList<String> m_arr_string = TableDetailInfo.getItemList(str_table_name, "1");




        table_info_name = (TextView)findViewById(R.id.table_info_name);
        table_info_name.setText(str_table_name);
        table_info_capacity = (TextView)findViewById(R.id.table_info_capacity);
        table_info_capacity.setText(str_table_capacity);

        table_info_menu = (ListView)findViewById(R.id.table_info_menu);
        table_info_menu.setAdapter(null);      // 초기화
        table_info_menu.setAdapter(new TableInforPopupMenuListAdapter(m_arr_string));

//        table_info_menu.setText(str_table_menu);
        table_info_menu_total = (TextView)findViewById(R.id.table_info_menu_total);
        table_info_menu_total.setText(str_table_menu_total);

        // -----------------------------------------------------------------------------------
    }


    View.OnClickListener table_info_clicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeButton : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
            }
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
