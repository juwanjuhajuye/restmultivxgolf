package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.receiptsetting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CloverMakingViewInPrinting;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting_Server_Receipt extends Activity{

    LinearLayout temp_server_receipt_ln;

    Switch sw_setting_popup_server_header;
    Switch sw_setting_popup_server_stationnumber;
    Switch sw_setting_popup_server_casher;
    Switch sw_setting_popup_server_ordernumber;
    Switch sw_setting_popup_server_ordertype;
    Switch sw_setting_popup_server_ordertypefont;
    Switch sw_setting_popup_server_ordertypealignment;
    Switch sw_setting_popup_server_tablenumber;
    Switch sw_setting_popup_server_tablenumberfont;
    Switch sw_setting_popup_server_pagernumber;
    Switch sw_setting_popup_server_pagernumberfont;
    Switch sw_setting_popup_server_pagernumberalignment;
    Switch sw_setting_popup_server_ordermemo;
    Switch sw_setting_popup_server_itemmemo;
    Switch sw_setting_popup_server_displayitemline;
    Switch sw_setting_popup_server_displayvoiditem;
    Switch sw_setting_popup_server_guestinfo;
    Switch sw_setting_popup_server_quantitysumarize;
    Switch sw_setting_popup_server_topfeedrowcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_setting_server_copy);
        this.setFinishOnTouchOutside(false);


        setContents();
        setBillPrintLn();
    }

    private void setContents(){

        temp_server_receipt_ln = (LinearLayout)findViewById(R.id.temp_server_receipt_ln);

        sw_setting_popup_server_header = (Switch)findViewById(R.id.sw_setting_popup_server_header);
        sw_setting_popup_server_stationnumber = (Switch)findViewById(R.id.sw_setting_popup_server_stationnumber);
        sw_setting_popup_server_casher = (Switch)findViewById(R.id.sw_setting_popup_server_casher);
        sw_setting_popup_server_ordernumber = (Switch)findViewById(R.id.sw_setting_popup_server_ordernumber);
        sw_setting_popup_server_ordertype = (Switch)findViewById(R.id.sw_setting_popup_server_ordertype);
        sw_setting_popup_server_ordertypefont = (Switch)findViewById(R.id.sw_setting_popup_server_ordertypefont);
        sw_setting_popup_server_ordertypealignment = (Switch)findViewById(R.id.sw_setting_popup_server_ordertypealignment);
        sw_setting_popup_server_tablenumber = (Switch)findViewById(R.id.sw_setting_popup_server_tablenumber);
        sw_setting_popup_server_tablenumberfont = (Switch)findViewById(R.id.sw_setting_popup_server_tablenumberfont);
        sw_setting_popup_server_pagernumber = (Switch)findViewById(R.id.sw_setting_popup_server_pagernumber);
        sw_setting_popup_server_pagernumberfont = (Switch)findViewById(R.id.sw_setting_popup_server_pagernumberfont);
        sw_setting_popup_server_pagernumberalignment = (Switch)findViewById(R.id.sw_setting_popup_server_pagernumberalignment);
        sw_setting_popup_server_ordermemo = (Switch)findViewById(R.id.sw_setting_popup_server_ordermemo);
        sw_setting_popup_server_itemmemo = (Switch)findViewById(R.id.sw_setting_popup_server_itemmemo);
        sw_setting_popup_server_displayitemline = (Switch)findViewById(R.id.sw_setting_popup_server_displayitemline);
        sw_setting_popup_server_displayvoiditem = (Switch)findViewById(R.id.sw_setting_popup_server_displayvoiditem);
        sw_setting_popup_server_guestinfo = (Switch)findViewById(R.id.sw_setting_popup_server_guestinfo);
        sw_setting_popup_server_quantitysumarize = (Switch)findViewById(R.id.sw_setting_popup_server_quantitysumarize);
        sw_setting_popup_server_topfeedrowcount = (Switch)findViewById(R.id.sw_setting_popup_server_topfeedrowcount);

        sw_setting_popup_server_header.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_stationnumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_casher.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_ordernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_ordertype.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_ordertypefont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_ordertypealignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_tablenumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_tablenumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_pagernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_pagernumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_pagernumberalignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_ordermemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_itemmemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_displayitemline.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_displayvoiditem.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_guestinfo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_quantitysumarize.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_server_topfeedrowcount.setOnCheckedChangeListener(onCheckedChangeListener);


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


        str_header_yn = GlobalMemberValues.getValueOnReceiptSetting("header_yn","5");
        if (str_header_yn.equals("Y")){
            sw_setting_popup_server_header.setChecked(true);
        } else {
            sw_setting_popup_server_header.setChecked(false);
        }
        str_stationnumber_yn = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_yn","5");
        if (str_stationnumber_yn.equals("Y")){
            sw_setting_popup_server_stationnumber.setChecked(true);
        } else {
            sw_setting_popup_server_stationnumber.setChecked(false);
        }
        str_casher_yn = GlobalMemberValues.getValueOnReceiptSetting("cashier_yn","5");
        if (str_casher_yn.equals("Y")){
            sw_setting_popup_server_casher.setChecked(true);
        } else {
            sw_setting_popup_server_casher.setChecked(false);
        }
        str_ordernumber = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_yn","5");
        if (str_ordernumber.equals("Y")){
            sw_setting_popup_server_ordernumber.setChecked(true);
        } else {
            sw_setting_popup_server_ordernumber.setChecked(false);
        }
        str_ordertype_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertype_yn","5");
        if (str_ordertype_yn.equals("Y")){
            sw_setting_popup_server_ordertype.setChecked(true);
        } else {
            sw_setting_popup_server_ordertype.setChecked(false);
        }
        str_ordertypefont_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertypefont_yn","5");
        if (str_ordertypefont_yn.equals("Y")){
            sw_setting_popup_server_ordertypefont.setChecked(true);
        } else {
            sw_setting_popup_server_ordertypefont.setChecked(false);
        }
        str_ordertypealignment = GlobalMemberValues.getValueOnReceiptSetting("ordertypealignment","5");
        if (str_ordertypealignment.equals("T")){
            sw_setting_popup_server_ordertypealignment.setChecked(true);
        } else {
            sw_setting_popup_server_ordertypealignment.setChecked(false);
        }
        str_tablenumber_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumber_yn","5");
        if (str_tablenumber_yn.equals("Y")){
            sw_setting_popup_server_tablenumber.setChecked(true);
        } else {
            sw_setting_popup_server_tablenumber.setChecked(false);
        }
        str_tablenumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumberfont_yn","5");
        if (str_tablenumberfont_yn.equals("Y")){
            sw_setting_popup_server_tablenumberfont.setChecked(true);
        } else {
            sw_setting_popup_server_tablenumberfont.setChecked(false);
        }
        str_pagernumber_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumber_yn","5");
        if (str_pagernumber_yn.equals("Y")){
            sw_setting_popup_server_pagernumber.setChecked(true);
        } else {
            sw_setting_popup_server_pagernumber.setChecked(false);
        }
        str_pagernumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumberfont_yn","5");
        if (str_pagernumberfont_yn.equals("Y")){
            sw_setting_popup_server_pagernumberfont.setChecked(true);
        } else {
            sw_setting_popup_server_pagernumberfont.setChecked(false);
        }
        str_pagernumberalignment = GlobalMemberValues.getValueOnReceiptSetting("pagernumberalignment","5");
        if (str_pagernumberalignment.equals("T")){
            sw_setting_popup_server_pagernumberalignment.setChecked(true);
        } else {
            sw_setting_popup_server_pagernumberalignment.setChecked(false);
        }
        str_ordermemo_yn = GlobalMemberValues.getValueOnReceiptSetting("ordermemo_yn","5");
        if (str_ordermemo_yn.equals("Y")){
            sw_setting_popup_server_ordermemo.setChecked(true);
        } else {
            sw_setting_popup_server_ordermemo.setChecked(false);
        }
        str_item_memo_yn = GlobalMemberValues.getValueOnReceiptSetting("itemmemo_yn","5");
        if (str_item_memo_yn.equals("Y")){
            sw_setting_popup_server_itemmemo.setChecked(true);
        } else {
            sw_setting_popup_server_itemmemo.setChecked(false);
        }
        str_displayitemline_yn = GlobalMemberValues.getValueOnReceiptSetting("displayitemline_yn","5");
        if (str_displayitemline_yn.equals("Y")){
            sw_setting_popup_server_displayitemline.setChecked(true);
        } else {
            sw_setting_popup_server_displayitemline.setChecked(false);
        }
        str_displayvoiditem_yn = GlobalMemberValues.getValueOnReceiptSetting("displayvoiditem_yn","5");
        if (str_displayvoiditem_yn.equals("Y")){
            sw_setting_popup_server_displayvoiditem.setChecked(true);
        } else {
            sw_setting_popup_server_displayvoiditem.setChecked(false);
        }
        str_guestinfo_yn = GlobalMemberValues.getValueOnReceiptSetting("guestinfo_yn","5");
        if (str_guestinfo_yn.equals("Y")){
            sw_setting_popup_server_guestinfo.setChecked(true);
        } else {
            sw_setting_popup_server_guestinfo.setChecked(false);
        }
        str_quantitysumarize_yn = GlobalMemberValues.getValueOnReceiptSetting("quantitysumarize_yn","5");
        if (str_quantitysumarize_yn.equals("Y")){
            sw_setting_popup_server_quantitysumarize.setChecked(true);
        } else {
            sw_setting_popup_server_quantitysumarize.setChecked(false);
        }
        str_topfeedrowcount_yn = GlobalMemberValues.getValueOnReceiptSetting("topfeedrowcount_yn","5");
        if (str_topfeedrowcount_yn.equals("Y")){
            sw_setting_popup_server_topfeedrowcount.setChecked(true);
        } else {
            sw_setting_popup_server_topfeedrowcount.setChecked(false);
        }

    }


    public void setBillPrintLn() {

        JSONObject jsonObject = null;
        try {
            String temp_json = "{\"receiptno\":\"K090620210YQ472HDX0\",\"saledate\":\"09\\/06\\/2021\",\"saletime\":\"22:28:27\",\"storename\":\"육대장 05호점\",\"storeaddress1\":\"21281 S Western Ave.\",\"storeaddress2\":\"\",\"storecity\":\"Torrance\",\"storestate\":\"CA\",\"storezip\":\"11355\",\"storephone\":\"01025469356\",\"earnedpoint\":\"0.00\",\"saleitemlist\":[{\"itemcartidx\":\"9569\",\"itemname\":\"육칼면  ORIGINAL YUKGAE\",\"itemqty\":\"2\",\"itemprice\":\"14.99\",\"itemamount\":\"29.98\",\"itemdcextratype\":\"\",\"itemdcextraprice\":\"\",\"itemCategoryName\":\"ENTREES\",\"itemCategoryName2\":\"\",\"billcartidx\":\"3759\",\"itemdcextrastr\":\"\",\"itemtaxexempt\":\"\",\"optiontxt\":\"\",\"optionprice\":\"0.0\",\"additionaltxt1\":\"\",\"additionalprice1\":\"0.0\",\"additionaltxt2\":\"\",\"additionalprice2\":\"0.0\",\"kitchenmemo\":\"nokitchenmemo\"}],\"employeename\":\"jihun\",\"serverphonenum\":\"Walk In\",\"serveraddress\":\"\",\"servername\":\" \",\"alldiscountextraprice\":\"0.00\",\"alldiscountextrstr\":\"\",\"totalextradiscountprice\":\"0.00\",\"totaldiscountprice\":\"0.00\",\"totalextraprice\":\"0.00\",\"subtotal\":\"29.98\",\"tax\":\"1.50\",\"totalamount\":\"31.48\",\"deliverytakeaway\":\"T\",\"deliverypickupfee\":\"0.00\",\"serverordernumber\":\"2-P0001\",\"serverpagernumber\":\"\",\"servermemo\":\"\",\"restaurant_tablename\":\"\",\"restaurant_tablepeoplecnt\":0,\"cashtendered\":\"31.48\",\"change\":\"0\",\"creditcardtendered\":\"0\",\"giftcardtendered\":\"0\",\"checktendered\":\"0\",\"pointtendered\":\"0\",\"receiptprinttype\":\"3\",\"receiptfooter\":\"\"}";
            jsonObject = new JSONObject(temp_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {

            LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject,"testPrintView","");
            temp_server_receipt_ln.removeAllViews();
            temp_server_receipt_ln.addView(getJsonLn);
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
                case R.id.sw_setting_popup_server_header :
                    GlobalMemberValues.setValueOnReceiptSetting("header_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_stationnumber :
                    GlobalMemberValues.setValueOnReceiptSetting("stationnumber_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_casher :
                    GlobalMemberValues.setValueOnReceiptSetting("cashier_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_ordernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("ordernumber_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_ordertype :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertype_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_ordertypefont :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypefont_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_ordertypealignment :
                    String str_tb = "";
                    if (isChecked){
                        str_tb = "T";
                    } else {
                        str_tb = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypealignment",str_tb,"5");
                    break;
                case R.id.sw_setting_popup_server_tablenumber :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumber_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_tablenumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumberfont_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_pagernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumber_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_pagernumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberfont_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_pagernumberalignment :
                    String str_tb2 = "";
                    if (isChecked){
                        str_tb2 = "T";
                    } else {
                        str_tb2 = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberalignment",str_tb2,"5");
                    break;
                case R.id.sw_setting_popup_server_ordermemo :
                    GlobalMemberValues.setValueOnReceiptSetting("ordermemo_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_itemmemo :
                    GlobalMemberValues.setValueOnReceiptSetting("itemmemo_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_displayitemline :
                    GlobalMemberValues.setValueOnReceiptSetting("displayitemline_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_displayvoiditem :
                    GlobalMemberValues.setValueOnReceiptSetting("displayvoiditem_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_guestinfo :
                    GlobalMemberValues.setValueOnReceiptSetting("guestinfo_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_quantitysumarize :
                    GlobalMemberValues.setValueOnReceiptSetting("quantitysumarize_yn",str_yn,"5");
                    break;
                case R.id.sw_setting_popup_server_topfeedrowcount :
                    GlobalMemberValues.setValueOnReceiptSetting("topfeedrowcount_yn",str_yn,"5");
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
