package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Setting_Check_Receipt extends Activity{

    LinearLayout temp_check_receipt_ln;

    Switch sw_setting_popup_check_header;
    Switch sw_setting_popup_check_stationnumber;
    Switch sw_setting_popup_check_casher;
    Switch sw_setting_popup_check_ordernumber;
    Switch sw_setting_popup_check_ordertype;
    Switch sw_setting_popup_check_ordertypefont;
    Switch sw_setting_popup_check_ordertypealignment;
    Switch sw_setting_popup_check_tablenumber;
    Switch sw_setting_popup_check_tablenumberfont;
    Switch sw_setting_popup_check_pagernumber;
    Switch sw_setting_popup_check_pagernumberfont;
    Switch sw_setting_popup_check_pagernumberalignment;
    Switch sw_setting_popup_check_ordermemo;
    Switch sw_setting_popup_check_itemmemo;
    Switch sw_setting_popup_check_displayitemline;
    Switch sw_setting_popup_check_displayvoiditem;
    Switch sw_setting_popup_check_guestinfo;
    Switch sw_setting_popup_check_quantitysumarize;
    Switch sw_setting_popup_check_topfeedrowcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_setting_check_copy);
        this.setFinishOnTouchOutside(false);


        setContents();
        setBillPrintLn();
    }

    private void setContents(){

        temp_check_receipt_ln = (LinearLayout)findViewById(R.id.temp_check_receipt_ln);

        sw_setting_popup_check_header = (Switch)findViewById(R.id.sw_setting_popup_check_header);
        sw_setting_popup_check_header.setVisibility(View.GONE);
        sw_setting_popup_check_stationnumber = (Switch)findViewById(R.id.sw_setting_popup_check_stationnumber);
        sw_setting_popup_check_stationnumber.setVisibility(View.GONE);
        sw_setting_popup_check_casher = (Switch)findViewById(R.id.sw_setting_popup_check_casher);
        sw_setting_popup_check_casher.setVisibility(View.GONE);
            sw_setting_popup_check_ordernumber = (Switch)findViewById(R.id.sw_setting_popup_check_ordernumber);
        sw_setting_popup_check_ordertype = (Switch)findViewById(R.id.sw_setting_popup_check_ordertype);
        sw_setting_popup_check_ordertype.setVisibility(View.GONE);
        sw_setting_popup_check_ordertypefont = (Switch)findViewById(R.id.sw_setting_popup_check_ordertypefont);
        sw_setting_popup_check_ordertypefont.setVisibility(View.GONE);
        sw_setting_popup_check_ordertypealignment = (Switch)findViewById(R.id.sw_setting_popup_check_ordertypealignment);
        sw_setting_popup_check_ordertypealignment.setVisibility(View.GONE);
            sw_setting_popup_check_tablenumber = (Switch)findViewById(R.id.sw_setting_popup_check_tablenumber);
        sw_setting_popup_check_tablenumberfont = (Switch)findViewById(R.id.sw_setting_popup_check_tablenumberfont);
        sw_setting_popup_check_tablenumberfont.setVisibility(View.GONE);
            sw_setting_popup_check_pagernumber = (Switch)findViewById(R.id.sw_setting_popup_check_pagernumber);
        sw_setting_popup_check_pagernumberfont = (Switch)findViewById(R.id.sw_setting_popup_check_pagernumberfont);
        sw_setting_popup_check_pagernumberfont.setVisibility(View.GONE);
            sw_setting_popup_check_pagernumberalignment = (Switch)findViewById(R.id.sw_setting_popup_check_pagernumberalignment);
            sw_setting_popup_check_ordermemo = (Switch)findViewById(R.id.sw_setting_popup_check_ordermemo);
            sw_setting_popup_check_itemmemo = (Switch)findViewById(R.id.sw_setting_popup_check_itemmemo);
        sw_setting_popup_check_displayitemline = (Switch)findViewById(R.id.sw_setting_popup_check_displayitemline);
        sw_setting_popup_check_displayitemline.setVisibility(View.GONE);
        sw_setting_popup_check_displayvoiditem = (Switch)findViewById(R.id.sw_setting_popup_check_displayvoiditem);
        sw_setting_popup_check_displayvoiditem.setVisibility(View.GONE);
        sw_setting_popup_check_guestinfo = (Switch)findViewById(R.id.sw_setting_popup_check_guestinfo);
        sw_setting_popup_check_guestinfo.setVisibility(View.GONE);
        sw_setting_popup_check_quantitysumarize = (Switch)findViewById(R.id.sw_setting_popup_check_quantitysumarize);
        sw_setting_popup_check_quantitysumarize.setVisibility(View.GONE);
        sw_setting_popup_check_topfeedrowcount = (Switch)findViewById(R.id.sw_setting_popup_check_topfeedrowcount);
        sw_setting_popup_check_topfeedrowcount.setVisibility(View.GONE);

        sw_setting_popup_check_header.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_stationnumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_casher.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_ordernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_ordertype.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_ordertypefont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_ordertypealignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_tablenumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_tablenumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_pagernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_pagernumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_pagernumberalignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_ordermemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_itemmemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_displayitemline.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_displayvoiditem.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_guestinfo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_quantitysumarize.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_check_topfeedrowcount.setOnCheckedChangeListener(onCheckedChangeListener);


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


        str_header_yn = GlobalMemberValues.getValueOnReceiptSetting("header_yn","4");
        if (str_header_yn.equals("Y")){
            sw_setting_popup_check_header.setChecked(true);
        } else {
            sw_setting_popup_check_header.setChecked(false);
        }
        str_stationnumber_yn = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_yn","4");
        if (str_stationnumber_yn.equals("Y")){
            sw_setting_popup_check_stationnumber.setChecked(true);
        } else {
            sw_setting_popup_check_stationnumber.setChecked(false);
        }
        str_casher_yn = GlobalMemberValues.getValueOnReceiptSetting("cashier_yn","4");
        if (str_casher_yn.equals("Y")){
            sw_setting_popup_check_casher.setChecked(true);
        } else {
            sw_setting_popup_check_casher.setChecked(false);
        }
        str_ordernumber = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_yn","4");
        if (str_ordernumber.equals("Y")){
            sw_setting_popup_check_ordernumber.setChecked(true);
        } else {
            sw_setting_popup_check_ordernumber.setChecked(false);
        }
        str_ordertype_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertype_yn","4");
        if (str_ordertype_yn.equals("Y")){
            sw_setting_popup_check_ordertype.setChecked(true);
        } else {
            sw_setting_popup_check_ordertype.setChecked(false);
        }
        str_ordertypefont_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertypefont_yn","4");
        if (str_ordertypefont_yn.equals("Y")){
            sw_setting_popup_check_ordertypefont.setChecked(true);
        } else {
            sw_setting_popup_check_ordertypefont.setChecked(false);
        }
        str_ordertypealignment = GlobalMemberValues.getValueOnReceiptSetting("ordertypealignment","4");
        if (str_ordertypealignment.equals("T")){
            sw_setting_popup_check_ordertypealignment.setChecked(true);
        } else {
            sw_setting_popup_check_ordertypealignment.setChecked(false);
        }
        str_tablenumber_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumber_yn","4");
        if (str_tablenumber_yn.equals("Y")){
            sw_setting_popup_check_tablenumber.setChecked(true);
        } else {
            sw_setting_popup_check_tablenumber.setChecked(false);
        }
        str_tablenumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumberfont_yn","4");
        if (str_tablenumberfont_yn.equals("Y")){
            sw_setting_popup_check_tablenumberfont.setChecked(true);
        } else {
            sw_setting_popup_check_tablenumberfont.setChecked(false);
        }
        str_pagernumber_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumber_yn","4");
        if (str_pagernumber_yn.equals("Y")){
            sw_setting_popup_check_pagernumber.setChecked(true);
        } else {
            sw_setting_popup_check_pagernumber.setChecked(false);
        }
        str_pagernumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumberfont_yn","4");
        if (str_pagernumberfont_yn.equals("Y")){
            sw_setting_popup_check_pagernumberfont.setChecked(true);
        } else {
            sw_setting_popup_check_pagernumberfont.setChecked(false);
        }
        str_pagernumberalignment = GlobalMemberValues.getValueOnReceiptSetting("pagernumberalignment","4");
        if (str_pagernumberalignment.equals("T")){
            sw_setting_popup_check_pagernumberalignment.setChecked(true);
        } else {
            sw_setting_popup_check_pagernumberalignment.setChecked(false);
        }
        str_ordermemo_yn = GlobalMemberValues.getValueOnReceiptSetting("ordermemo_yn","4");
        if (str_ordermemo_yn.equals("Y")){
            sw_setting_popup_check_ordermemo.setChecked(true);
        } else {
            sw_setting_popup_check_ordermemo.setChecked(false);
        }
        str_item_memo_yn = GlobalMemberValues.getValueOnReceiptSetting("itemmemo_yn","4");
        if (str_item_memo_yn.equals("Y")){
            sw_setting_popup_check_itemmemo.setChecked(true);
        } else {
            sw_setting_popup_check_itemmemo.setChecked(false);
        }
        str_displayitemline_yn = GlobalMemberValues.getValueOnReceiptSetting("displayitemline_yn","4");
        if (str_displayitemline_yn.equals("Y")){
            sw_setting_popup_check_displayitemline.setChecked(true);
        } else {
            sw_setting_popup_check_displayitemline.setChecked(false);
        }
        str_displayvoiditem_yn = GlobalMemberValues.getValueOnReceiptSetting("displayvoiditem_yn","4");
        if (str_displayvoiditem_yn.equals("Y")){
            sw_setting_popup_check_displayvoiditem.setChecked(true);
        } else {
            sw_setting_popup_check_displayvoiditem.setChecked(false);
        }
        str_guestinfo_yn = GlobalMemberValues.getValueOnReceiptSetting("guestinfo_yn","4");
        if (str_guestinfo_yn.equals("Y")){
            sw_setting_popup_check_guestinfo.setChecked(true);
        } else {
            sw_setting_popup_check_guestinfo.setChecked(false);
        }
        str_quantitysumarize_yn = GlobalMemberValues.getValueOnReceiptSetting("quantitysumarize_yn","4");
        if (str_quantitysumarize_yn.equals("Y")){
            sw_setting_popup_check_quantitysumarize.setChecked(true);
        } else {
            sw_setting_popup_check_quantitysumarize.setChecked(false);
        }
        str_topfeedrowcount_yn = GlobalMemberValues.getValueOnReceiptSetting("topfeedrowcount_yn","4");
        if (str_topfeedrowcount_yn.equals("Y")){
            sw_setting_popup_check_topfeedrowcount.setChecked(true);
        } else {
            sw_setting_popup_check_topfeedrowcount.setChecked(false);
        }

    }


    public void setBillPrintLn() {

        JSONObject jsonObject = null;
        try {
            String temp_json = "{\"receiptno\":\"K09082021K9I8649X1T\",\"saledate\":\"09\\/08\\/2021\",\"saletime\":\"10:18:13\",\"storename\":\"육대장 05호점\",\"storeaddress1\":\"21281 S Western Ave.\",\"storeaddress2\":\"\",\"storecity\":\"Torrance\",\"storestate\":\"CA\",\"storezip\":\"11355\",\"storephone\":\"01025469356\",\"earnedpoint\":\"0.00\",\"saleitemlist\":[{\"itemcartidx\":\"9570\",\"itemname\":\"옛날전통 육개장 ORIGINAL YUKGAE\",\"itemqty\":\"1\",\"itemprice\":\"12.99\",\"itemamount\":\"12.99\",\"itemdcextratype\":\"\",\"itemdcextraprice\":\"\",\"itemCategoryName\":\"ENTREES\",\"itemCategoryName2\":\"\",\"billcartidx\":\"3775\",\"itemdcextrastr\":\"\",\"itemtaxexempt\":\"N\",\"optiontxt\":\"\",\"optionprice\":\"0.00\",\"additionaltxt1\":\"\",\"additionalprice1\":\"0.00\",\"additionaltxt2\":\"\",\"additionalprice2\":\"0.00\",\"kitchenmemo\":\"nokitchenmemo\"}],\"employeename\":\"jihun\",\"customerphonenum\":\"Walk In\",\"customeraddress\":\"\",\"customername\":\" \",\"alldiscountextraprice\":\"0.00\",\"alldiscountextrstr\":\"\",\"totalextradiscountprice\":\"0.00\",\"totaldiscountprice\":\"0.00\",\"totalextraprice\":\"0.00\",\"subtotal\":\"12.99\",\"tax\":\"0.65\",\"totalamount\":\"13.64\",\"deliverytakeaway\":\"T\",\"deliverypickupfee\":\"0.00\",\"customerordernumber\":\"1-P0006\",\"customerpagernumber\":\"8\",\"customermemo\":\"\",\"restaurant_tablename\":\"\",\"restaurant_tablepeoplecnt\":0,\"cashtendered\":\"13.64\",\"change\":\"0\",\"creditcardtendered\":\"0\",\"giftcardtendered\":\"0\",\"checktendered\":\"0\",\"pointtendered\":\"0\",\"receiptprinttype\":\"3\",\"receiptfooter\":\"\",\"billtype\":\"\",\"billpayamount\":0,\"billcartidx\":\"\",\"billtotalamount\":0}";
            jsonObject = new JSONObject(temp_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {

//            LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject,"testPrintView","");
            ArrayList<LinearLayout> getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout_menu(jsonObject);
            getPrintingLn.get(0).setLayoutParams(new LinearLayout.LayoutParams(571, LinearLayout.LayoutParams.WRAP_CONTENT));
            temp_check_receipt_ln.removeAllViews();
            temp_check_receipt_ln.addView(getPrintingLn.get(0));
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
                case R.id.sw_setting_popup_check_header :
                    GlobalMemberValues.setValueOnReceiptSetting("header_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_stationnumber :
                    GlobalMemberValues.setValueOnReceiptSetting("stationnumber_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_casher :
                    GlobalMemberValues.setValueOnReceiptSetting("cashier_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_ordernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("ordernumber_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_ordertype :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertype_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_ordertypefont :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypefont_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_ordertypealignment :
                    String str_tb = "";
                    if (isChecked){
                        str_tb = "T";
                    } else {
                        str_tb = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypealignment",str_tb,"4");
                    break;
                case R.id.sw_setting_popup_check_tablenumber :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumber_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_tablenumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumberfont_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_pagernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumber_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_pagernumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberfont_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_pagernumberalignment :
                    String str_tb2 = "";
                    if (isChecked){
                        str_tb2 = "T";
                    } else {
                        str_tb2 = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberalignment",str_tb2,"4");
                    break;
                case R.id.sw_setting_popup_check_ordermemo :
                    GlobalMemberValues.setValueOnReceiptSetting("ordermemo_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_itemmemo :
                    GlobalMemberValues.setValueOnReceiptSetting("itemmemo_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_displayitemline :
                    GlobalMemberValues.setValueOnReceiptSetting("displayitemline_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_displayvoiditem :
                    GlobalMemberValues.setValueOnReceiptSetting("displayvoiditem_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_guestinfo :
                    GlobalMemberValues.setValueOnReceiptSetting("guestinfo_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_quantitysumarize :
                    GlobalMemberValues.setValueOnReceiptSetting("quantitysumarize_yn",str_yn,"4");
                    break;
                case R.id.sw_setting_popup_check_topfeedrowcount :
                    GlobalMemberValues.setValueOnReceiptSetting("topfeedrowcount_yn",str_yn,"4");
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

}