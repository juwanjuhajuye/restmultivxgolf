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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class Settings extends ActivityGroup {
    static Activity mActivity;
    Context context;

    private LinearLayout parentLn;
    private Button closeBtn;

    private LinearLayout settingsSystemLinearLayout;
    private LinearLayout settingsDevicePrinterLinearLayout, settingsDevicePrinterLinearLayout2;
    private LinearLayout settingsPaymentGatewayLinearLayout, settingsDeviceMasterPrinterLinearLayout;

    private TextView settingsTitleTextView, storestationinfoTextView;

    Intent mIntent;

    public TabHost settingsTabHost;

    // Tab builder 객체
    public TabHost.TabSpec mSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 9;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
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

        settingsTitleTextView = (TextView)findViewById(R.id.settingsTitleTextView);
        settingsTitleTextView.setTextSize(settingsTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        storestationinfoTextView = (TextView)findViewById(R.id.storestationinfoTextView);
        storestationinfoTextView.setTextSize(storestationinfoTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Station 정보 가져와 storestationinfoTextView 에 기재하기 ----------------------------------------------
        String tempMainYN = "";
        String tempPushReceiveYN = "";
        String strQuery = "select mainYN, pushreceiveyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "' ";
        Cursor stationinfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (stationinfoCursor.getCount() > 0 && stationinfoCursor.moveToFirst()) {
            tempMainYN = GlobalMemberValues.getDBTextAfterChecked(stationinfoCursor.getString(0), 1);
            tempPushReceiveYN = GlobalMemberValues.getDBTextAfterChecked(stationinfoCursor.getString(1), 1);
            if (GlobalMemberValues.isStrEmpty(tempMainYN)) {
                tempMainYN = "N";
            }
            if (GlobalMemberValues.isStrEmpty(tempPushReceiveYN)) {
                tempPushReceiveYN = "Y";
            }
        }

        String tempStoreName = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select name from salon_storeinfo "
        );
        String storestationinfoStr = "";
        storestationinfoStr = "Store : <font color=\"#15addf\">" + tempStoreName + "</font>" +
                " / Store Code : <font color=\"#15addf\">" + GlobalMemberValues.STORE_INDEX + "</font>" +
                " / Station Code : <font color=\"#15addf\">" + GlobalMemberValues.STATION_CODE + "</font>";
        if (tempPushReceiveYN == "Y" || tempPushReceiveYN.equals("Y")) {
            storestationinfoStr += " / Push : <font color=\"#15addf\">Receiving</font>";
        } else {
            storestationinfoStr += " / Push : <font color=\"#15addf\">Not Receiving</font>";
        }
        if (tempMainYN == "Y" || tempMainYN.equals("Y")) {
            storestationinfoStr += " / <font color=\"#fb719d\">Main Station</font>";
        }
        GlobalMemberValues.logWrite("storestationinfolog", "SERIAL_NUMBER : " + GlobalMemberValues.SERIAL_NUMBER + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "tempMainYN : " + tempMainYN + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "tempPushReceiveYN : " + tempPushReceiveYN + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "store / station info : " + storestationinfoStr + "\n");

        storestationinfoTextView.setText(Html.fromHtml(storestationinfoStr));
        // ---------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.settingsCloseBtn);
        closeBtn.setOnClickListener(settingsBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/


        settingsSystemLinearLayout = (LinearLayout)findViewById(R.id.settingsSystemLinearLayout);
        settingsDevicePrinterLinearLayout = (LinearLayout)findViewById(R.id.settingsDevicePrinterLinearLayout);
        settingsDevicePrinterLinearLayout2 = (LinearLayout)findViewById(R.id.settingsDevicePrinterLinearLayout2);
        settingsDeviceMasterPrinterLinearLayout = (LinearLayout)findViewById(R.id.settingsDeviceMasterPrinterLinearLayout);
        settingsPaymentGatewayLinearLayout = (LinearLayout)findViewById(R.id.settingsPaymentGatewayLinearLayout);

        settingsTabHost = (TabHost)findViewById(R.id.settingsTabHost);
        settingsTabHost.setup(this.getLocalActivityManager());        // findViewById를 이용해 TabHost인스턴스를 얻은경우 꼭 호출 필요

        // System 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab1");                // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_system));
            mSpec.setIndicator("");                                    // Tab 타이틀
        } else {
            mSpec.setIndicator("System");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsSystemLinearLayout);         // Tab 페이지
        mSpec.setContent(new Intent(this, SettingsSystem.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // Device & Printer 탭 페이지 셋팅 - Receipt
        mSpec = settingsTabHost.newTabSpec("Tab2");                // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_device));
            mSpec.setIndicator("");                                              // Tab 타이틀
        } else {
            mSpec.setIndicator("Device & Printer (Receipt)");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout);  // Tab 페이지
        mSpec.setContent(new Intent(this, SettingsDevicePrinter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // Device & Printer 탭 페이지 셋팅 - Label
        mSpec = settingsTabHost.newTabSpec("Tab3");                // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_device));
            mSpec.setIndicator("");                                              // Tab 타이틀
        } else {
            mSpec.setIndicator("Device & Printer (Label)");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout);  // Tab 페이지
        mSpec.setContent(new Intent(this, SettingDeviceLabelPrinter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록


        // Device & Printer 탭 페이지 셋팅 - Kitchen
        mSpec = settingsTabHost.newTabSpec("Tab4");                // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_device));
            mSpec.setIndicator("");                                              // Tab 타이틀
        } else {
            mSpec.setIndicator("Device & Printer (Kitchen)");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout2);  // Tab 페이지
        mSpec.setContent(new Intent(this, SettingsDeviceKitchen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록

        // 220914 추가
        // Master Printer 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab5");                // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_device));
            mSpec.setIndicator("");                                              // Tab 타이틀
        } else {
            mSpec.setIndicator("Device & Printer (Master)");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsDevicePrinterLinearLayout2);  // Tab 페이지
        mSpec.setContent(new Intent(this, SettingsDeviceMasterPrinter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                             // Tab 등록
        // 220914 추가


        // Payment Gateway 탭 페이지 셋팅
        mSpec = settingsTabHost.newTabSpec("Tab6");                 // Tab Builder 객체 생성
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //mSpec.setIndicator("", getResources().getDrawable(R.drawable.ab_imagebutton_settings_gateway));
            mSpec.setIndicator("");                                             // Tab 타이틀
        } else {
            mSpec.setIndicator("Payment Gateway");                              // Tab 타이틀
        }
        //mSpec.setContent(R.id.settingsPaymentGatewayLinearLayout);  // Tab 페이지
        mSpec.setContent(new Intent(this, SettingsPaymentGateway.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        settingsTabHost.addTab(mSpec);                              // Tab 등록

        TabWidget tempTabWidget = settingsTabHost.getTabWidget();

        for (int i = 0; i < settingsTabHost.getTabWidget().getChildCount(); i++) {
            RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(i);
            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                switch (i) {
                    case 0 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system_disable);
                        break;
                    }
                    case 1 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable);
                        break;
                    }
                    case 2 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_label_disable);
                        break;
                    }
                    case 3 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable2);
                        break;
                    }
                    case 4 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.aa_images_settings_device_master_disable);
                        break;
                    }
                    case 5 : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
                        break;
                    }
                }
            } else {
                TextView tv = (TextView)relLayout.getChildAt(1);
                tv.setTextSize(20);
                tv.setTextColor(Color.parseColor("#ffffff"));
            }
            //tv.setTextColor 혹은 tv.setTextAppearance를 호출.

            // 탭 버튼 배경색 변경
            //tempTabWidget.getChildAt(i).setBackgroundColor(Color.parseColor("#a7a030"));
            tempTabWidget.getChildAt(i).setBackgroundResource(R.drawable.button_selector_settings);
        }



        /**
         tempTabWidget.getChildAt(0).setBackgroundColor(Color.parseColor("#6775f3"));
         tempTabWidget.getChildAt(1).setBackgroundColor(Color.parseColor("#ec564a"));
         tempTabWidget.getChildAt(2).setBackgroundColor(Color.parseColor("#87d81b"));
         **/

        // 처음 Tab 을 보여줌
        tempTabWidget.setCurrentTab(0);
        //tempTabWidget.getChildAt(0).setBackgroundColor(Color.parseColor("#ece9e6"));
        tempTabWidget.getChildAt(0).setBackgroundResource(R.drawable.button_selector_settings_selected);
        RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system);
        } else {
            TextView tv = (TextView)relLayout.getChildAt(1);
            tv.setTextSize(20);
            tv.setTextColor(Color.parseColor("#433637"));
        }

        settingsTabHost.setOnTabChangedListener(tabChanged);
    }

    TabHost.OnTabChangeListener tabChanged = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            for (int i = 0; i < settingsTabHost.getTabWidget().getChildCount(); i++) {
                RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(i);
                if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                    switch (i) {
                        case 0 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system_disable);
                            break;
                        }
                        case 1 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable);
                            break;
                        }
                        case 2 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_label_disable);
                            break;
                        }
                        case 3 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device_disable2);
                            break;
                        }
                        case 4 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.aa_images_settings_device_master_disable);
                            break;
                        }
                        case 5 : {
                            relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway_disable);
                            break;
                        }
                    }
                } else {
                    TextView tv = (TextView)relLayout.getChildAt(1);
                    tv.setTextSize(20);
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }
                //settingsTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#a7a030"));
                settingsTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.button_selector_settings);
            }
            settingsTabHost.getTabWidget().getChildAt(settingsTabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_selector_settings_selected);
            RelativeLayout relLayout = (RelativeLayout)settingsTabHost.getTabWidget().getChildAt(settingsTabHost.getCurrentTab());
            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                switch (tabId) {
                    case "Tab1" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_system);
                        break;
                    }
                    case "Tab2" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device);
                        break;
                    }
                    case "Tab3" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_label);
                        break;
                    }
                    case "Tab4" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_device2);
                        break;
                    }
                    case "Tab5" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.aa_images_settings_device_master_select);
                        break;
                    }
                    case "Tab6" : {
                        relLayout.getChildAt(0).setBackgroundResource(R.drawable.ab_imagebutton_settings_gateway);
                        break;
                    }
                }
            } else {
                TextView tv = (TextView)relLayout.getChildAt(1);
                tv.setTextSize(20);
                tv.setTextColor(Color.parseColor("#433637"));
            }
        }
    };

    View.OnClickListener settingsBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.settingsCloseBtn : {
                    finish();
                    break;
                }
            }
        }
    };
}
