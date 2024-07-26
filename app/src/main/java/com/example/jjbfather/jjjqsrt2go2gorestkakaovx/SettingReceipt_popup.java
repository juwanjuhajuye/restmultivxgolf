package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting.Setting_Check_Receipt;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting.Setting_Customer_Receipt;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting.Setting_Kitchen_Receipt;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting.Setting_Merchant_Receipt;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting.Setting_Server_Receipt;

public class SettingReceipt_popup extends ActivityGroup {
    static Activity mActivity;
    Context context;

    private LinearLayout parentLn;
    private ImageButton closeBtn;

    private LinearLayout settingsSystemLinearLayout;
    private LinearLayout settingsDevicePrinterLinearLayout, settingsDevicePrinterLinearLayout2;
    private LinearLayout settingsPaymentGatewayLinearLayout;



    Intent mIntent;

    public TabHost settingsTabHost;

    // Tab builder 객체
    public TabHost.TabSpec mSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_setting_receipt_popup);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 8;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 100;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.settingsLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        /**
         final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.tabContentLn);
         mainLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        }
        });
         **/
        // ---------------------------------------------------------------------------------------------------

        mActivity = this;
        context = this;

        // ---------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (ImageButton) findViewById(R.id.setting_poup_closeBtn);
        closeBtn.setOnClickListener(settingsBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {

            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        }
        /***********************************************************************************************************/


        settingsSystemLinearLayout = (LinearLayout)findViewById(R.id.settingsSystemLinearLayout);
        settingsDevicePrinterLinearLayout = (LinearLayout)findViewById(R.id.settingsDevicePrinterLinearLayout);
        settingsDevicePrinterLinearLayout2 = (LinearLayout)findViewById(R.id.settingsDevicePrinterLinearLayout2);
        settingsPaymentGatewayLinearLayout = (LinearLayout)findViewById(R.id.settingsPaymentGatewayLinearLayout);

        settingsTabHost = (TabHost)findViewById(R.id.settingsTabHost);
        settingsTabHost.setup(this.getLocalActivityManager());        // findViewById를 이용해 TabHost인스턴스를 얻은경우 꼭 호출 필요

        // System 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab1");                // Tab Builder 객체 생성
        mSpec.setIndicator("Customer Copy");                              // Tab 타이틀
        //mSpec.setContent(R.id.settingsSystemLinearLayout);         // Tab 페이지
        mSpec.setContent(new Intent(this, Setting_Customer_Receipt.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // Device & Printer 탭 페이지 셋팅 - Receipt
        mSpec = settingsTabHost.newTabSpec("Tab2");                // Tab Builder 객체 생성
        mSpec.setIndicator("Merchant Copy");                              // Tab 타이틀
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout);  // Tab 페이지
        mSpec.setContent(new Intent(this, Setting_Merchant_Receipt.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // Device & Printer 탭 페이지 셋팅 - Kitchen
        mSpec = settingsTabHost.newTabSpec("Tab3");                // Tab Builder 객체 생성
        mSpec.setIndicator("Kitchen Copy");
        //mSpec.setContent(R.id.settingsPaymentGatewayLinearLayout);  // Tab 페이지
        mSpec.setContent(new Intent(this, Setting_Kitchen_Receipt.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // Payment Gateway 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab4");                 // Tab Builder 객체 생성
        mSpec.setIndicator("Check Copy");                              // Tab 타이틀
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout2);  // Tab 페이지
        mSpec.setContent(new Intent(this, Setting_Check_Receipt.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                              // Tab 등록

        // System 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab5");                // Tab Builder 객체 생성
        mSpec.setIndicator("Server Copy");                              // Tab 타이틀
        //mSpec.setContent(R.id.settingsSystemLinearLayout);         // Tab 페이지
        mSpec.setContent(new Intent(this, Setting_Server_Receipt.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        TabWidget tempTabWidget = settingsTabHost.getTabWidget();
        for (int i = 0; i < settingsTabHost.getTabWidget().getChildCount(); i++) {
//            RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(i);
//            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//                switch (i) {
//                    case 0 : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system_disable);
//                        break;
//                    }
//                    case 1 : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable);
//                        break;
//                    }
//                    case 2 : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable2);
//                        break;
//                    }
//                    case 3 : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
//                        break;
//                    }
//                    case 4 : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
//                        break;
//                    }
//                }
//            } else {
//                TextView tv = (TextView)relLayout.getChildAt(1);
//                tv.setTextSize(20);
//                tv.setTextColor(Color.parseColor("#ffffff"));
//            }
            //tv.setTextColor 혹은 tv.setTextAppearance를 호출.

            // 탭 버튼 배경색 변경
            //tempTabWidget.getChildAt(i).setBackgroundColor(Color.parseColor("#a7a030"));
            tempTabWidget.getChildAt(i).setBackgroundResource(R.drawable.button_selector_settings_popup_selected);
        }


        /**
         tempTabWidget.getChildAt(0).setBackgroundColor(Color.parseColor("#6775f3"));
         tempTabWidget.getChildAt(1).setBackgroundColor(Color.parseColor("#ec564a"));
         tempTabWidget.getChildAt(2).setBackgroundColor(Color.parseColor("#87d81b"));
         **/

        // 처음 Tab 을 보여줌
        tempTabWidget.setCurrentTab(0);
        //tempTabWidget.getChildAt(0).setBackgroundColor(Color.parseColor("#ece9e6"));
        tempTabWidget.getChildAt(0).setBackgroundResource(R.drawable.button_selector_settings_popup_selected);
//        RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(0);
//        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system);
//        } else {
//            TextView tv = (TextView)relLayout.getChildAt(1);
//            tv.setTextSize(20);
//            tv.setTextColor(Color.parseColor("#433637"));
//        }

        settingsTabHost.setOnTabChangedListener(tabChanged);



    }

    TabHost.OnTabChangeListener tabChanged = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
//            for (int i = 0; i < settingsTabHost.getTabWidget().getChildCount(); i++) {
//                RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(i);
//                if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//                    switch (i) {
//                        case 0 : {
//                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system_disable);
//                            break;
//                        }
//                        case 1 : {
//                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable);
//                            break;
//                        }
//                        case 2 : {
//                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable2);
//                            break;
//                        }
//                        case 3 : {
//                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
//                            break;
//                        }
//                        case 4 : {
//                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
//                            break;
//                        }
//                    }
//                } else {
//                    TextView tv = (TextView)relLayout.getChildAt(1);
//                    tv.setTextSize(20);
//                    tv.setTextColor(Color.parseColor("#ffffff"));
//                }
//                //settingsTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#a7a030"));
//                settingsTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.button_selector_settings);
//            }
            settingsTabHost.getTabWidget().getChildAt(settingsTabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_selector_settings_popup_selected);
//            RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(settingsTabHost.getCurrentTab());
//            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
//                switch (tabId) {
//                    case "Tab1" : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system);
//                        break;
//                    }
//                    case "Tab2" : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device);
//                        break;
//                    }
//                    case "Tab3" : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device2);
//                        break;
//                    }
//                    case "Tab4" : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway);
//                        break;
//                    }
//                    case "Tab5" : {
//                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway);
//                        break;
//                    }
//                }
//            } else {
//                TextView tv = (TextView)relLayout.getChildAt(1);
//                tv.setTextSize(20);
//                tv.setTextColor(Color.parseColor("#433637"));
//            }
        }
    };

    View.OnClickListener settingsBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_poup_closeBtn : {
                    finish();
                    break;
                }
            }
        }
    };
}
