package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;

public class Setting_Kitchen_Receipt extends Activity{

    LinearLayout temp_kitchen_receipt_ln;

    Switch sw_setting_popup_kitchen_header;
    Switch sw_setting_popup_kitchen_stationnumber;
    Switch sw_setting_popup_kitchen_casher;
    Switch sw_setting_popup_kitchen_ordernumber;
    Switch sw_setting_popup_kitchen_ordertype;
    Switch sw_setting_popup_kitchen_ordertypefont;
    Switch sw_setting_popup_kitchen_ordertypealignment;
    Switch sw_setting_popup_kitchen_tablenumber;
    Switch sw_setting_popup_kitchen_tablenumberfont;
    Switch sw_setting_popup_kitchen_pagernumber;
    Switch sw_setting_popup_kitchen_pagernumberfont;
    Switch sw_setting_popup_kitchen_pagernumberalignment;
    Switch sw_setting_popup_kitchen_ordermemo;
    Switch sw_setting_popup_kitchen_itemmemo;
    Switch sw_setting_popup_kitchen_displayitemline;
    Switch sw_setting_popup_kitchen_displayvoiditem;
    Switch sw_setting_popup_kitchen_guestinfo;
    Switch sw_setting_popup_kitchen_quantitysumarize;
    Switch sw_setting_popup_kitchen_topfeedrowcount;

    Spinner sp_setting_popup_kitchen_topfeedcount;
    ArrayList<String> mGeneralArrayListForSpinner = null;
    String selectedItemSpinner = "1";

    //

    Spinner sp_setting_popup_kitchen_bottomfeedrowcount;
    String selected_bottomItemSpinner = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_setting_kitchen_copy);
        this.setFinishOnTouchOutside(false);


        setContents();
        setBillPrintLn();
    }

    private void setContents(){

        temp_kitchen_receipt_ln = (LinearLayout)findViewById(R.id.temp_kitchen_receipt_ln);

        sw_setting_popup_kitchen_header = (Switch)findViewById(R.id.sw_setting_popup_kitchen_header);
        sw_setting_popup_kitchen_header.setVisibility(View.GONE);
        sw_setting_popup_kitchen_stationnumber = (Switch)findViewById(R.id.sw_setting_popup_kitchen_stationnumber);
        sw_setting_popup_kitchen_stationnumber.setVisibility(View.GONE);
        sw_setting_popup_kitchen_casher = (Switch)findViewById(R.id.sw_setting_popup_kitchen_casher);
        sw_setting_popup_kitchen_casher.setVisibility(View.GONE);
        sw_setting_popup_kitchen_ordernumber = (Switch)findViewById(R.id.sw_setting_popup_kitchen_ordernumber);
        sw_setting_popup_kitchen_ordertype = (Switch)findViewById(R.id.sw_setting_popup_kitchen_ordertype);
        sw_setting_popup_kitchen_ordertypefont = (Switch)findViewById(R.id.sw_setting_popup_kitchen_ordertypefont);
        sw_setting_popup_kitchen_ordertypefont.setVisibility(View.GONE);
        sw_setting_popup_kitchen_ordertypealignment = (Switch)findViewById(R.id.sw_setting_popup_kitchen_ordertypealignment);
        sw_setting_popup_kitchen_tablenumber = (Switch)findViewById(R.id.sw_setting_popup_kitchen_tablenumber);
        sw_setting_popup_kitchen_tablenumberfont = (Switch)findViewById(R.id.sw_setting_popup_kitchen_tablenumberfont);
        sw_setting_popup_kitchen_tablenumberfont.setVisibility(View.GONE);
        sw_setting_popup_kitchen_pagernumber = (Switch)findViewById(R.id.sw_setting_popup_kitchen_pagernumber);
        sw_setting_popup_kitchen_pagernumberfont = (Switch)findViewById(R.id.sw_setting_popup_kitchen_pagernumberfont);
        sw_setting_popup_kitchen_pagernumberfont.setVisibility(View.GONE);
        sw_setting_popup_kitchen_pagernumberalignment = (Switch)findViewById(R.id.sw_setting_popup_kitchen_pagernumberalignment);
        sw_setting_popup_kitchen_ordermemo = (Switch)findViewById(R.id.sw_setting_popup_kitchen_ordermemo);
        sw_setting_popup_kitchen_itemmemo = (Switch)findViewById(R.id.sw_setting_popup_kitchen_itemmemo);
        sw_setting_popup_kitchen_displayitemline = (Switch)findViewById(R.id.sw_setting_popup_kitchen_displayitemline);
        sw_setting_popup_kitchen_displayitemline.setVisibility(View.GONE);
        sw_setting_popup_kitchen_displayvoiditem = (Switch)findViewById(R.id.sw_setting_popup_kitchen_displayvoiditem);
        sw_setting_popup_kitchen_displayvoiditem.setVisibility(View.GONE);
        sw_setting_popup_kitchen_guestinfo = (Switch)findViewById(R.id.sw_setting_popup_kitchen_guestinfo);
        sw_setting_popup_kitchen_quantitysumarize = (Switch)findViewById(R.id.sw_setting_popup_kitchen_quantitysumarize);


        sp_setting_popup_kitchen_topfeedcount = (Spinner)findViewById(R.id.sp_setting_popup_kitchen_topfeedcount);
        String[] billIndexList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < billIndexList.length; i++) {
            mGeneralArrayListForSpinner.add(billIndexList[i]);
        }

        sp_setting_popup_kitchen_bottomfeedrowcount = (Spinner)findViewById(R.id.sp_setting_popup_kitchen_bottomfeedrowcount);

        ArrayAdapter<String> mSpinnerTopAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerTopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_setting_popup_kitchen_topfeedcount.setAdapter(mSpinnerTopAdapter);
        sp_setting_popup_kitchen_topfeedcount.setOnItemSelectedListener(mSpinnerTopItemSelectedListener);




        ArrayAdapter<String> mSpinnerBottomAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerBottomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_setting_popup_kitchen_bottomfeedrowcount.setAdapter(mSpinnerBottomAdapter);
        sp_setting_popup_kitchen_bottomfeedrowcount.setOnItemSelectedListener(mSpinnerBottomItemSelectedListener);


        sw_setting_popup_kitchen_header.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_stationnumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_casher.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_ordernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_ordertype.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_ordertypefont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_ordertypealignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_tablenumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_tablenumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_pagernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_pagernumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_pagernumberalignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_ordermemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_itemmemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_displayitemline.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_displayvoiditem.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_guestinfo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_kitchen_quantitysumarize.setOnCheckedChangeListener(onCheckedChangeListener);


        String str_header_yn = "";
        String str_stationnumber_yn = "";
        String str_casher_yn = "";
        String str_ordernumber = "";
        String str_ordertype_yn = "";
        String str_ordertypefont_yn = "";
        String str_ordertypealignment = "";
        String str_tablenumber_yn = "";
        String str_tablenumberfont_yn = "";
        String str_pagernumber_yn = "";
        String str_pagernumberfont_yn = "";
        String str_pagernumberalignment = "";
        String str_ordermemo_yn = "";
        String str_item_memo_yn = "";
        String str_displayitemline_yn = "";
        String str_displayvoiditem_yn = "";
        String str_guestinfo_yn = "";
        String str_quantitysumarize_yn = "";
        String str_topfeedrowcount_yn = "";

        String str_bottomfeedrowcount = "";


        str_header_yn = GlobalMemberValues.getValueOnReceiptSetting("header_yn","3");
        if (str_header_yn.equals("Y")){
            sw_setting_popup_kitchen_header.setChecked(true);
        } else {
            sw_setting_popup_kitchen_header.setChecked(false);
        }
        str_stationnumber_yn = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_yn","3");
        if (str_stationnumber_yn.equals("Y")){
            sw_setting_popup_kitchen_stationnumber.setChecked(true);
        } else {
            sw_setting_popup_kitchen_stationnumber.setChecked(false);
        }
        str_casher_yn = GlobalMemberValues.getValueOnReceiptSetting("cashier_yn","3");
        if (str_casher_yn.equals("Y")){
            sw_setting_popup_kitchen_casher.setChecked(true);
        } else {
            sw_setting_popup_kitchen_casher.setChecked(false);
        }
        str_ordernumber = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_yn","3");
        if (str_ordernumber.equals("Y")){
            sw_setting_popup_kitchen_ordernumber.setChecked(true);
        } else {
            sw_setting_popup_kitchen_ordernumber.setChecked(false);
        }
        str_ordertype_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertype_yn","3");
        if (str_ordertype_yn.equals("Y")){
            sw_setting_popup_kitchen_ordertype.setChecked(true);
        } else {
            sw_setting_popup_kitchen_ordertype.setChecked(false);
        }
        str_ordertypefont_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertypefont_yn","3");
        if (str_ordertypefont_yn.equals("Y")){
            sw_setting_popup_kitchen_ordertypefont.setChecked(true);
        } else {
            sw_setting_popup_kitchen_ordertypefont.setChecked(false);
        }
        str_ordertypealignment = GlobalMemberValues.getValueOnReceiptSetting("ordertypealignment","3");
        if (str_ordertypealignment.equals("T")){
            sw_setting_popup_kitchen_ordertypealignment.setChecked(true);
        } else {
            sw_setting_popup_kitchen_ordertypealignment.setChecked(false);
        }
        str_tablenumber_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumber_yn","3");
        if (str_tablenumber_yn.equals("Y")){
            sw_setting_popup_kitchen_tablenumber.setChecked(true);
        } else {
            sw_setting_popup_kitchen_tablenumber.setChecked(false);
        }
        str_tablenumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumberfont_yn","3");
        if (str_tablenumberfont_yn.equals("Y")){
            sw_setting_popup_kitchen_tablenumberfont.setChecked(true);
        } else {
            sw_setting_popup_kitchen_tablenumberfont.setChecked(false);
        }
        str_pagernumber_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumber_yn","3");
        if (str_pagernumber_yn.equals("Y")){
            sw_setting_popup_kitchen_pagernumber.setChecked(true);
        } else {
            sw_setting_popup_kitchen_pagernumber.setChecked(false);
        }
        str_pagernumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumberfont_yn","3");
        if (str_pagernumberfont_yn.equals("Y")){
            sw_setting_popup_kitchen_pagernumberfont.setChecked(true);
        } else {
            sw_setting_popup_kitchen_pagernumberfont.setChecked(false);
        }
        str_pagernumberalignment = GlobalMemberValues.getValueOnReceiptSetting("pagernumberalignment","3");
        if (str_pagernumberalignment.equals("T")){
            sw_setting_popup_kitchen_pagernumberalignment.setChecked(true);
        } else {
            sw_setting_popup_kitchen_pagernumberalignment.setChecked(false);
        }
        str_ordermemo_yn = GlobalMemberValues.getValueOnReceiptSetting("ordermemo_yn","3");
        if (str_ordermemo_yn.equals("Y")){
            sw_setting_popup_kitchen_ordermemo.setChecked(true);
        } else {
            sw_setting_popup_kitchen_ordermemo.setChecked(false);
        }
        str_item_memo_yn = GlobalMemberValues.getValueOnReceiptSetting("itemmemo_yn","3");
        if (str_item_memo_yn.equals("Y")){
            sw_setting_popup_kitchen_itemmemo.setChecked(true);
        } else {
            sw_setting_popup_kitchen_itemmemo.setChecked(false);
        }
        str_displayitemline_yn = GlobalMemberValues.getValueOnReceiptSetting("displayitemline_yn","3");
        if (str_displayitemline_yn.equals("Y")){
            sw_setting_popup_kitchen_displayitemline.setChecked(true);
        } else {
            sw_setting_popup_kitchen_displayitemline.setChecked(false);
        }
        str_displayvoiditem_yn = GlobalMemberValues.getValueOnReceiptSetting("displayvoiditem_yn","3");
        if (str_displayvoiditem_yn.equals("Y")){
            sw_setting_popup_kitchen_displayvoiditem.setChecked(true);
        } else {
            sw_setting_popup_kitchen_displayvoiditem.setChecked(false);
        }
        str_guestinfo_yn = GlobalMemberValues.getValueOnReceiptSetting("guestinfo_yn","3");
        if (str_guestinfo_yn.equals("Y")){
            sw_setting_popup_kitchen_guestinfo.setChecked(true);
        } else {
            sw_setting_popup_kitchen_guestinfo.setChecked(false);
        }
        str_quantitysumarize_yn = GlobalMemberValues.getValueOnReceiptSetting("quantitysumarize_yn","3");
        if (str_quantitysumarize_yn.equals("Y")){
            sw_setting_popup_kitchen_quantitysumarize.setChecked(true);
        } else {
            sw_setting_popup_kitchen_quantitysumarize.setChecked(false);
        }
        str_topfeedrowcount_yn = GlobalMemberValues.getValueOnReceiptSetting("topfeedrowcount_yn","3");
        if (!str_topfeedrowcount_yn.isEmpty()){
            if (StringUtil.isNumeric(str_topfeedrowcount_yn)){
                // 숫자일경우
                sp_setting_popup_kitchen_topfeedcount.setSelection(Integer.parseInt(str_topfeedrowcount_yn) - 1);
            } else {
                sp_setting_popup_kitchen_topfeedcount.setSelection(0);
            }
        }

        str_bottomfeedrowcount = GlobalMemberValues.getValueOnReceiptSetting("bottomfeedrowcount_yn","3");
        if (!str_bottomfeedrowcount.isEmpty()){
            if (StringUtil.isNumeric(str_bottomfeedrowcount)){
                // 숫자일경우
                sp_setting_popup_kitchen_bottomfeedrowcount.setSelection(Integer.parseInt(str_bottomfeedrowcount) - 1);
            } else {
                sp_setting_popup_kitchen_bottomfeedrowcount.setSelection(0);
            }
        }
    }


    public void setBillPrintLn() {

        JSONObject jsonObject = null;
        try {
            String temp_json = "{\"receiptno\":\"K09082021PNAP473UP6\",\"saledate\":\"09\\/08\\/2021\",\"saletime\":\"06:32:26\",\"saleitemlist\":\"옛날전통 육개장 ORIGINAL YUKGAE-ANNIETTASU--ANNIETTASU--ANNIETTASU--ANNIETTASU-9570-ANNIETTASU-1-ANNIETTASU-nokitchenmemo-ANNIETTASU--ANNIETTASU--JJJ-1-JJJ-1-JJJ-ENTREES-JJJ--JJJ-12.99-JJJ-0.65-JJJ-0.00-JJJ-0.00-JJJ-0.00\",\"customername\":\" \",\"deliverytakeaway\":\"T\",\"deliverydate\":\"2021-09-08 06:32:17\",\"ordertype\":\"POS\",\"customermemo\":\"\",\"customerordernumber\":\"1-P0005\",\"customerpagernumber\":\"7\",\"customerpagerheader\":\"\",\"checkcompany\":\"\",\"phoneordernumber\":\"\",\"customerphonenum\":\"Walk In\",\"customeraddress\":\"\",\"restaurant_tablename\":\"\",\"restaurant_tablepeoplecnt\":0,\"receiptfooter\":\"\"}";
            jsonObject = new JSONObject(temp_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {

            LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(jsonObject,"1");
            getJsonLn.setLayoutParams(new LinearLayout.LayoutParams(571, LinearLayout.LayoutParams.WRAP_CONTENT));
            temp_kitchen_receipt_ln.removeAllViews();
            temp_kitchen_receipt_ln.addView(getJsonLn);
        }
    }


    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String str_yn = "";
            if (isChecked){
                str_yn = "Y";
            } else {
                str_yn = "N";
            }
            switch (buttonView.getId()){
                case R.id.sw_setting_popup_kitchen_header :
                    GlobalMemberValues.setValueOnReceiptSetting("header_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_stationnumber :
                    GlobalMemberValues.setValueOnReceiptSetting("stationnumber_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_casher :
                    GlobalMemberValues.setValueOnReceiptSetting("cashier_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_ordernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("ordernumber_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_ordertype :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertype_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_ordertypefont :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypefont_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_ordertypealignment :
                    String str_tb = "";
                    if (isChecked){
                        str_tb = "T";
                    } else {
                        str_tb = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypealignment",str_tb,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_tablenumber :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumber_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_tablenumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumberfont_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_pagernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumber_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_pagernumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberfont_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_pagernumberalignment :
                    String str_tb2 = "";
                    if (isChecked){
                        str_tb2 = "T";
                    } else {
                        str_tb2 = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberalignment",str_tb2,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_ordermemo :
                    GlobalMemberValues.setValueOnReceiptSetting("ordermemo_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_itemmemo :
                    GlobalMemberValues.setValueOnReceiptSetting("itemmemo_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_displayitemline :
                    GlobalMemberValues.setValueOnReceiptSetting("displayitemline_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_displayvoiditem :
                    GlobalMemberValues.setValueOnReceiptSetting("displayvoiditem_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_guestinfo :
                    GlobalMemberValues.setValueOnReceiptSetting("guestinfo_yn",str_yn,"3");
                    break;
                case R.id.sw_setting_popup_kitchen_quantitysumarize :
                    GlobalMemberValues.setValueOnReceiptSetting("quantitysumarize_yn",str_yn,"3");
                    break;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setBillPrintLn();
                }
            });
        }
    };
    AdapterView.OnItemSelectedListener mSpinnerTopItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.setValueOnReceiptSetting("topfeedrowcount_yn",selectedItemSpinner,"3");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setBillPrintLn();
                }
            });
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mSpinnerBottomItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.setValueOnReceiptSetting("bottomfeedrowcount_yn",selectedItemSpinner,"3");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setBillPrintLn();
                }
            });
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

}
