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

import java.util.ArrayList;

public class Setting_Customer_Receipt extends Activity{

    LinearLayout temp_customer_receipt_ln;

    Switch sw_setting_popup_customer_header;
    Switch sw_setting_popup_customer_stationnumber;
    Switch sw_setting_popup_customer_casher;
    Switch sw_setting_popup_customer_ordernumber;
    Switch sw_setting_popup_customer_ordertype;
    Switch sw_setting_popup_customer_ordertypefont;
    Switch sw_setting_popup_customer_ordertypealignment;
    Switch sw_setting_popup_customer_tablenumber;
    Switch sw_setting_popup_customer_tablenumberfont;
    Switch sw_setting_popup_customer_pagernumber;
    Switch sw_setting_popup_customer_pagernumberfont;
    Switch sw_setting_popup_customer_pagernumberalignment;
    Switch sw_setting_popup_customer_ordermemo;
    Switch sw_setting_popup_customer_itemmemo;
    Switch sw_setting_popup_customer_displayitemline;
    Switch sw_setting_popup_customer_displayvoiditem;
    Switch sw_setting_popup_customer_guestinfo;
    Switch sw_setting_popup_customer_quantitysumarize;
    Switch sw_setting_popup_customer_topfeedrowcount;

    Spinner stationnumber_type_spinner, stationnumber_size_spinner, ordernumber_size_spinner, totalamount_size_spinner, suggestedtip_type_spinner, suggestedtip_size_spinner;
    Switch sw_setting_popup_customer_ordernumber_position;
    ArrayList<String> str_arr_station_type, str_arr_station_size, str_arr_ordernumber_size, str_arr_totalamount_size, str_arr_suggestedtip_type, str_arr_suggestedtip_size;
    ArrayAdapter<String> adapter_station_type, adapter_station_size, adapter_order_size, adapter_totalamount_size, adapter_suggestedtip_type, adapter_suggestedtip_size;
    public static String selected_station_type_Spinner = GlobalMemberValues.RECEIPT_TEXT_TYPE[0];
    public static String selected_station_size_Spinner = GlobalMemberValues.RECEIPT_TEXT_SIZE[0];
    public static String selected_order_number_size = GlobalMemberValues.RECEIPT_TEXT_SIZE[0];
    public static String selected_totalamount_size = GlobalMemberValues.RECEIPT_TEXT_SIZE[0];
    public static String selected_suggestedtip_type_Spinner = GlobalMemberValues.RECEIPT_TEXT_TYPE[0];
    public static String selected_suggestedtip_size_Spinner = GlobalMemberValues.RECEIPT_TEXT_SIZE[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_setting_customer_copy);
        this.setFinishOnTouchOutside(false);


        setContents();
        setBillPrintLn();
    }

    private void setContents(){

        temp_customer_receipt_ln = (LinearLayout)findViewById(R.id.temp_customer_receipt_ln);

        sw_setting_popup_customer_header = (Switch)findViewById(R.id.sw_setting_popup_customer_header);
        sw_setting_popup_customer_stationnumber = (Switch)findViewById(R.id.sw_setting_popup_customer_stationnumber);
        sw_setting_popup_customer_casher = (Switch)findViewById(R.id.sw_setting_popup_customer_casher);
        sw_setting_popup_customer_ordernumber = (Switch)findViewById(R.id.sw_setting_popup_customer_ordernumber);
        sw_setting_popup_customer_ordertype = (Switch)findViewById(R.id.sw_setting_popup_customer_ordertype);
        sw_setting_popup_customer_ordertypefont = (Switch)findViewById(R.id.sw_setting_popup_customer_ordertypefont);
        sw_setting_popup_customer_ordertypefont.setVisibility(View.GONE);
        sw_setting_popup_customer_ordertypealignment = (Switch)findViewById(R.id.sw_setting_popup_customer_ordertypealignment);
        sw_setting_popup_customer_tablenumber = (Switch)findViewById(R.id.sw_setting_popup_customer_tablenumber);
        sw_setting_popup_customer_tablenumberfont = (Switch)findViewById(R.id.sw_setting_popup_customer_tablenumberfont);
        sw_setting_popup_customer_tablenumberfont.setVisibility(View.GONE);
        sw_setting_popup_customer_pagernumber = (Switch)findViewById(R.id.sw_setting_popup_customer_pagernumber);
        sw_setting_popup_customer_pagernumberfont = (Switch)findViewById(R.id.sw_setting_popup_customer_pagernumberfont);
        sw_setting_popup_customer_pagernumberfont.setVisibility(View.GONE);
        sw_setting_popup_customer_pagernumberalignment = (Switch)findViewById(R.id.sw_setting_popup_customer_pagernumberalignment);
        sw_setting_popup_customer_ordermemo = (Switch)findViewById(R.id.sw_setting_popup_customer_ordermemo);
        sw_setting_popup_customer_itemmemo = (Switch)findViewById(R.id.sw_setting_popup_customer_itemmemo);
        sw_setting_popup_customer_displayitemline = (Switch)findViewById(R.id.sw_setting_popup_customer_displayitemline);
        sw_setting_popup_customer_displayvoiditem = (Switch)findViewById(R.id.sw_setting_popup_customer_displayvoiditem);
        sw_setting_popup_customer_guestinfo = (Switch)findViewById(R.id.sw_setting_popup_customer_guestinfo);
        sw_setting_popup_customer_quantitysumarize = (Switch)findViewById(R.id.sw_setting_popup_customer_quantitysumarize);
        sw_setting_popup_customer_topfeedrowcount = (Switch)findViewById(R.id.sw_setting_popup_customer_topfeedrowcount);
        sw_setting_popup_customer_topfeedrowcount.setVisibility(View.GONE);

        sw_setting_popup_customer_header.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_stationnumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_casher.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_ordernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_ordertype.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_ordertypefont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_ordertypealignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_tablenumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_tablenumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_pagernumber.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_pagernumberfont.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_pagernumberalignment.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_ordermemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_itemmemo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_displayitemline.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_displayvoiditem.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_guestinfo.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_quantitysumarize.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_setting_popup_customer_topfeedrowcount.setOnCheckedChangeListener(onCheckedChangeListener);


        stationnumber_type_spinner = (Spinner)findViewById(R.id.stationnumber_type_spinner);
        str_arr_station_type = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_TYPE.length; i++) {
            str_arr_station_type.add(GlobalMemberValues.RECEIPT_TEXT_TYPE[i]);
        }
        adapter_station_type = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_station_type);
        adapter_station_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationnumber_type_spinner.setAdapter(adapter_station_type);
        stationnumber_type_spinner.setOnItemSelectedListener(mStation_Type_SelectedListener);

        int tempPNPosition = 0;
//        for (int i = 0; i < GlobalMemberValues.DEVICEGROUP.length; i++) {
//            if (vDeviceKind.equals(GlobalMemberValues.DEVICEGROUP[i])) {
//                tempPNPosition = i;
//            }
//        }
//
//        mSavedDeviceName = vDeviceKind;
//        stationnumber_type_spinner.setSelection(tempPNPosition);

        //

        stationnumber_size_spinner = (Spinner)findViewById(R.id.stationnumber_size_spinner);
        str_arr_station_size = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_SIZE.length; i++) {
            str_arr_station_size.add(GlobalMemberValues.RECEIPT_TEXT_SIZE[i]);
        }
        adapter_station_size = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_station_size);
        adapter_station_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationnumber_size_spinner.setAdapter(adapter_station_size);
        stationnumber_size_spinner.setOnItemSelectedListener(mStation_Size_SelectedListener);

        int tempSizePosition = 0;

        ordernumber_size_spinner = (Spinner)findViewById(R.id.ordernumber_size_spinner);
        str_arr_ordernumber_size = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_SIZE.length; i++) {
            str_arr_ordernumber_size.add(GlobalMemberValues.RECEIPT_TEXT_SIZE[i]);
        }
        adapter_order_size = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_ordernumber_size);
        adapter_order_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ordernumber_size_spinner.setAdapter(adapter_order_size);
        ordernumber_size_spinner.setOnItemSelectedListener(mStation_ordernumber_Size_SelectedListener);

        sw_setting_popup_customer_ordernumber_position = (Switch)findViewById(R.id.sw_setting_popup_customer_orderpositionalignment);
        sw_setting_popup_customer_ordernumber_position.setOnCheckedChangeListener(onCheckedChangeListener);

        totalamount_size_spinner = (Spinner)findViewById(R.id.totalamount_size_spinner);
        str_arr_totalamount_size = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_SIZE.length; i++) {
            str_arr_totalamount_size.add(GlobalMemberValues.RECEIPT_TEXT_SIZE[i]);
        }
        adapter_totalamount_size = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_totalamount_size);
        adapter_totalamount_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalamount_size_spinner.setAdapter(adapter_totalamount_size);
        totalamount_size_spinner.setOnItemSelectedListener(mStation_totalamount_Size_SelectedListener);


        suggestedtip_type_spinner = (Spinner)findViewById(R.id.suggestedtip_type_spinner);
        str_arr_suggestedtip_type = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_TYPE.length; i++) {
            str_arr_suggestedtip_type.add(GlobalMemberValues.RECEIPT_TEXT_TYPE[i]);
        }
        adapter_suggestedtip_type = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_suggestedtip_type);
        adapter_suggestedtip_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suggestedtip_type_spinner.setAdapter(adapter_suggestedtip_type);
        suggestedtip_type_spinner.setOnItemSelectedListener(mStation_suggestedtip_type_SelectedListener);


        suggestedtip_size_spinner = (Spinner)findViewById(R.id.suggestedtip_size_spinner);
        str_arr_suggestedtip_size = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.RECEIPT_TEXT_SIZE.length; i++) {
            str_arr_suggestedtip_size.add(GlobalMemberValues.RECEIPT_TEXT_SIZE[i]);
        }
        adapter_suggestedtip_size = new ArrayAdapter<String>(Setting_Customer_Receipt.this, android.R.layout.simple_spinner_item, str_arr_suggestedtip_size);
        adapter_suggestedtip_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suggestedtip_size_spinner.setAdapter(adapter_suggestedtip_size);
        suggestedtip_size_spinner.setOnItemSelectedListener(mStation_suggestedtip_Size_SelectedListener);





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

        String str_ordernumber_alignment = "";


        str_header_yn = GlobalMemberValues.getValueOnReceiptSetting("header_yn","1");
        if (str_header_yn.equals("Y")){
            sw_setting_popup_customer_header.setChecked(true);
        } else {
            sw_setting_popup_customer_header.setChecked(false);
        }
        str_stationnumber_yn = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_yn","1");
        if (str_stationnumber_yn.equals("Y")){
            sw_setting_popup_customer_stationnumber.setChecked(true);
        } else {
            sw_setting_popup_customer_stationnumber.setChecked(false);
        }
        str_casher_yn = GlobalMemberValues.getValueOnReceiptSetting("cashier_yn","1");
        if (str_casher_yn.equals("Y")){
            sw_setting_popup_customer_casher.setChecked(true);
        } else {
            sw_setting_popup_customer_casher.setChecked(false);
        }
        str_ordernumber = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_yn","1");
        if (str_ordernumber.equals("Y")){
            sw_setting_popup_customer_ordernumber.setChecked(true);
        } else {
            sw_setting_popup_customer_ordernumber.setChecked(false);
        }
        str_ordertype_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertype_yn","1");
        if (str_ordertype_yn.equals("Y")){
            sw_setting_popup_customer_ordertype.setChecked(true);
        } else {
            sw_setting_popup_customer_ordertype.setChecked(false);
        }
        str_ordertypefont_yn = GlobalMemberValues.getValueOnReceiptSetting("ordertypefont_yn","1");
        if (str_ordertypefont_yn.equals("Y")){
            sw_setting_popup_customer_ordertypefont.setChecked(true);
        } else {
            sw_setting_popup_customer_ordertypefont.setChecked(false);
        }
        str_ordertypealignment = GlobalMemberValues.getValueOnReceiptSetting("ordertypealignment","1");
        if (str_ordertypealignment.equals("T")){
            sw_setting_popup_customer_ordertypealignment.setChecked(true);
        } else {
            sw_setting_popup_customer_ordertypealignment.setChecked(false);
        }
        str_tablenumber_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumber_yn","1");
        if (str_tablenumber_yn.equals("Y")){
            sw_setting_popup_customer_tablenumber.setChecked(true);
        } else {
            sw_setting_popup_customer_tablenumber.setChecked(false);
        }
        str_tablenumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("tablenumberfont_yn","1");
        if (str_tablenumberfont_yn.equals("Y")){
            sw_setting_popup_customer_tablenumberfont.setChecked(true);
        } else {
            sw_setting_popup_customer_tablenumberfont.setChecked(false);
        }
        str_pagernumber_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumber_yn","1");
        if (str_pagernumber_yn.equals("Y")){
            sw_setting_popup_customer_pagernumber.setChecked(true);
        } else {
            sw_setting_popup_customer_pagernumber.setChecked(false);
        }
        str_pagernumberfont_yn = GlobalMemberValues.getValueOnReceiptSetting("pagernumberfont_yn","1");
        if (str_pagernumberfont_yn.equals("Y")){
            sw_setting_popup_customer_pagernumberfont.setChecked(true);
        } else {
            sw_setting_popup_customer_pagernumberfont.setChecked(false);
        }
        str_pagernumberalignment = GlobalMemberValues.getValueOnReceiptSetting("pagernumberalignment","1");
        if (str_pagernumberalignment.equals("T")){
            sw_setting_popup_customer_pagernumberalignment.setChecked(true);
        } else {
            sw_setting_popup_customer_pagernumberalignment.setChecked(false);
        }
        str_ordermemo_yn = GlobalMemberValues.getValueOnReceiptSetting("ordermemo_yn","1");
        if (str_ordermemo_yn.equals("Y")){
            sw_setting_popup_customer_ordermemo.setChecked(true);
        } else {
            sw_setting_popup_customer_ordermemo.setChecked(false);
        }
        str_item_memo_yn = GlobalMemberValues.getValueOnReceiptSetting("itemmemo_yn","1");
        if (str_item_memo_yn.equals("Y")){
            sw_setting_popup_customer_itemmemo.setChecked(true);
        } else {
            sw_setting_popup_customer_itemmemo.setChecked(false);
        }
        str_displayitemline_yn = GlobalMemberValues.getValueOnReceiptSetting("displayitemline_yn","1");
        if (str_displayitemline_yn.equals("Y")){
            sw_setting_popup_customer_displayitemline.setChecked(true);
        } else {
            sw_setting_popup_customer_displayitemline.setChecked(false);
        }
        str_displayvoiditem_yn = GlobalMemberValues.getValueOnReceiptSetting("displayvoiditem_yn","1");
        if (str_displayvoiditem_yn.equals("Y")){
            sw_setting_popup_customer_displayvoiditem.setChecked(true);
        } else {
            sw_setting_popup_customer_displayvoiditem.setChecked(false);
        }
        str_guestinfo_yn = GlobalMemberValues.getValueOnReceiptSetting("guestinfo_yn","1");
        if (str_guestinfo_yn.equals("Y")){
            sw_setting_popup_customer_guestinfo.setChecked(true);
        } else {
            sw_setting_popup_customer_guestinfo.setChecked(false);
        }
        str_quantitysumarize_yn = GlobalMemberValues.getValueOnReceiptSetting("quantitysumarize_yn","1");
        if (str_quantitysumarize_yn.equals("Y")){
            sw_setting_popup_customer_quantitysumarize.setChecked(true);
        } else {
            sw_setting_popup_customer_quantitysumarize.setChecked(false);
        }
        str_topfeedrowcount_yn = GlobalMemberValues.getValueOnReceiptSetting("topfeedrowcount_yn","1");
        if (str_topfeedrowcount_yn.equals("Y")){
            sw_setting_popup_customer_topfeedrowcount.setChecked(true);
        } else {
            sw_setting_popup_customer_topfeedrowcount.setChecked(false);
        }

        String str_station_type = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_type","1");
        if (str_station_type.equals("Y")){
            str_station_type = "1";
        }
        switch (str_station_type){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
        }
        stationnumber_type_spinner.setSelection(Integer.parseInt(str_station_type) - 1);

        String str_station_size = GlobalMemberValues.getValueOnReceiptSetting("stationnumber_size","1");
        if (str_station_size.equals("Y")){
            str_station_size = "1";
        }
        switch (str_station_size){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
        }
        stationnumber_size_spinner.setSelection(Integer.parseInt(str_station_size) - 1);

        String str_ordernumber_size = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_type","1");
        if (str_ordernumber_size.equals("Y")){
            str_ordernumber_size = "1";
        }
        switch (str_ordernumber_size){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
        }
        ordernumber_size_spinner.setSelection(Integer.parseInt(str_ordernumber_size) - 1);

        str_ordernumber_alignment = GlobalMemberValues.getValueOnReceiptSetting("ordernumber_position","1");
        if (str_ordernumber_alignment.equals("T") || str_ordernumber_alignment.equals("1")){
            sw_setting_popup_customer_ordernumber_position.setChecked(true);
        } else {
            sw_setting_popup_customer_ordernumber_position.setChecked(false);
        }

        String str_totalamount_size = GlobalMemberValues.getValueOnReceiptSetting("totalamount_size","1");
        if (str_totalamount_size.equals("Y")){
            str_totalamount_size = "1";
        }
        switch (str_totalamount_size){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
        }
        totalamount_size_spinner.setSelection(Integer.parseInt(str_totalamount_size) - 1);


        String str_suggestedtip_type = GlobalMemberValues.getValueOnReceiptSetting("tip_type","1");
        if (str_suggestedtip_type.equals("Y")){
            str_suggestedtip_type = "1";
        }
        switch (str_suggestedtip_type){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
        }
        suggestedtip_type_spinner.setSelection(Integer.parseInt(str_suggestedtip_type) - 1);

        String str_suggestedtip_size = GlobalMemberValues.getValueOnReceiptSetting("tip_size","1");
        if (str_suggestedtip_size.equals("Y")){
            str_suggestedtip_size = "1";
        }
        switch (str_suggestedtip_size){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
        }
        suggestedtip_size_spinner.setSelection(Integer.parseInt(str_suggestedtip_size) - 1);

    }


    public void setBillPrintLn() {

        JSONObject jsonObject = null;
        try {
            String temp_json = "{\"receiptno\":\"K09082021K9I8649X1T\",\"saledate\":\"09\\/08\\/2021\",\"saletime\":\"10:18:13\",\"storename\":\"육대장 05호점\",\"storeaddress1\":\"21281 S Western Ave.\",\"storeaddress2\":\"\",\"storecity\":\"Torrance\",\"storestate\":\"CA\",\"storezip\":\"11355\",\"storephone\":\"01025469356\",\"earnedpoint\":\"0.00\",\"saleitemlist\":[{\"itemcartidx\":\"9570\",\"itemname\":\"옛날전통 육개장 ORIGINAL YUKGAE\",\"itemqty\":\"1\",\"itemprice\":\"12.99\",\"itemamount\":\"12.99\",\"itemdcextratype\":\"\",\"itemdcextraprice\":\"\",\"itemCategoryName\":\"ENTREES\",\"itemCategoryName2\":\"\",\"billcartidx\":\"3775\",\"itemdcextrastr\":\"\",\"itemtaxexempt\":\"N\",\"optiontxt\":\"\",\"optionprice\":\"0.00\",\"additionaltxt1\":\"\",\"additionalprice1\":\"0.00\",\"additionaltxt2\":\"\",\"additionalprice2\":\"0.00\",\"kitchenmemo\":\"nokitchenmemo\"}],\"employeename\":\"jihun\",\"customerphonenum\":\"Walk In\",\"customeraddress\":\"customer address\",\"customername\":\"customer jihun \",\"alldiscountextraprice\":\"0.00\",\"alldiscountextrstr\":\"\",\"totalextradiscountprice\":\"0.00\",\"totaldiscountprice\":\"0.00\",\"totalextraprice\":\"0.00\",\"subtotal\":\"12.99\",\"tax\":\"0.65\",\"totalamount\":\"13.64\",\"deliverytakeaway\":\"T\",\"deliverypickupfee\":\"0.00\",\"customerordernumber\":\"1-P0006\",\"customerpagernumber\":\"8\",\"customermemo\":\"\",\"restaurant_tablename\":\"TB32\",\"restaurant_tablepeoplecnt\":0,\"cashtendered\":\"13.64\",\"change\":\"0\",\"creditcardtendered\":\"0\",\"giftcardtendered\":\"0\",\"checktendered\":\"0\",\"pointtendered\":\"0\",\"receiptprinttype\":\"3\",\"receiptfooter\":\"\",\"billtype\":\"\",\"billpayamount\":0,\"billcartidx\":\"\",\"billtotalamount\":0}";
            jsonObject = new JSONObject(temp_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {

            LinearLayout getJsonLn = CloverMakingViewInPrinting.makingLinearLayout(jsonObject,"testPrintView","customer");
            temp_customer_receipt_ln.removeAllViews();
            temp_customer_receipt_ln.addView(getJsonLn);
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
                case R.id.sw_setting_popup_customer_header :
                    GlobalMemberValues.setValueOnReceiptSetting("header_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_stationnumber :
                    GlobalMemberValues.setValueOnReceiptSetting("stationnumber_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_casher :
                    GlobalMemberValues.setValueOnReceiptSetting("cashier_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_ordernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("ordernumber_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_ordertype :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertype_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_ordertypefont :
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypefont_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_ordertypealignment :
                    String str_tb = "";
                    if (isChecked){
                        str_tb = "T";
                    } else {
                        str_tb = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("ordertypealignment",str_tb,"1");
                    break;
                case R.id.sw_setting_popup_customer_tablenumber :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumber_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_tablenumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("tablenumberfont_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_pagernumber :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumber_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_pagernumberfont :
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberfont_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_pagernumberalignment :
                    String str_tb2 = "";
                    if (isChecked){
                        str_tb2 = "T";
                    } else {
                        str_tb2 = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("pagernumberalignment",str_tb2,"1");
                    break;
                case R.id.sw_setting_popup_customer_ordermemo :
                    GlobalMemberValues.setValueOnReceiptSetting("ordermemo_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_itemmemo :
                    GlobalMemberValues.setValueOnReceiptSetting("itemmemo_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_displayitemline :
                    GlobalMemberValues.setValueOnReceiptSetting("displayitemline_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_displayvoiditem :
                    GlobalMemberValues.setValueOnReceiptSetting("displayvoiditem_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_guestinfo :
                    GlobalMemberValues.setValueOnReceiptSetting("guestinfo_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_quantitysumarize :
                    GlobalMemberValues.setValueOnReceiptSetting("quantitysumarize_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_topfeedrowcount :
                    GlobalMemberValues.setValueOnReceiptSetting("topfeedrowcount_yn",str_yn,"1");
                    break;
                case R.id.sw_setting_popup_customer_orderpositionalignment :
                    String str_tb3 = "";
                    if (isChecked){
                        str_tb3 = "T";
                    } else {
                        str_tb3 = "B";
                    }
                    GlobalMemberValues.setValueOnReceiptSetting("ordernumber_position",str_tb3,"1");
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

    AdapterView.OnItemSelectedListener mStation_Size_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_station_size_Spinner = str_arr_station_size.get(position);
            switch (selected_station_size_Spinner){
                case "SMALL":
                    selected_station_size_Spinner = "1";
                    break;
                case "MEDIUM":
                    selected_station_size_Spinner = "2";
                    break;
                case "LARGE":
                    selected_station_size_Spinner = "3";
                    break;
                case "X-LARGE":
                    selected_station_size_Spinner = "4";
                    break;
                case "2X-LARGE":
                    selected_station_size_Spinner = "5";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("stationnumber_size",selected_station_size_Spinner,"1");

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

    AdapterView.OnItemSelectedListener mStation_ordernumber_Size_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_order_number_size = str_arr_ordernumber_size.get(position);
            switch (selected_order_number_size){
                case "SMALL":
                    selected_order_number_size = "1";
                    break;
                case "MEDIUM":
                    selected_order_number_size = "2";
                    break;
                case "LARGE":
                    selected_order_number_size = "3";
                    break;
                case "X-LARGE":
                    selected_order_number_size = "4";
                    break;
                case "2X-LARGE":
                    selected_order_number_size = "5";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("ordernumber_type",selected_order_number_size,"1");

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

    AdapterView.OnItemSelectedListener mStation_totalamount_Size_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_totalamount_size = str_arr_totalamount_size.get(position);
            switch (selected_totalamount_size){
                case "SMALL":
                    selected_totalamount_size = "1";
                    break;
                case "MEDIUM":
                    selected_totalamount_size = "2";
                    break;
                case "LARGE":
                    selected_totalamount_size = "3";
                    break;
                case "X-LARGE":
                    selected_totalamount_size = "4";
                    break;
                case "2X-LARGE":
                    selected_totalamount_size = "5";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("totalamount_size",selected_totalamount_size,"1");

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

    AdapterView.OnItemSelectedListener mStation_suggestedtip_Size_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_suggestedtip_size_Spinner = str_arr_suggestedtip_size.get(position);
            switch (selected_suggestedtip_size_Spinner){
                case "SMALL":
                    selected_suggestedtip_size_Spinner = "1";
                    break;
                case "MEDIUM":
                    selected_suggestedtip_size_Spinner = "2";
                    break;
                case "LARGE":
                    selected_suggestedtip_size_Spinner = "3";
                    break;
                case "X-LARGE":
                    selected_suggestedtip_size_Spinner = "4";
                    break;
                case "2X-LARGE":
                    selected_suggestedtip_size_Spinner = "5";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("tip_size",selected_suggestedtip_size_Spinner,"1");

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

    AdapterView.OnItemSelectedListener mStation_suggestedtip_type_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_suggestedtip_type_Spinner = str_arr_suggestedtip_type.get(position);
            switch (selected_suggestedtip_type_Spinner){
                case "NORMAL":
                    selected_suggestedtip_type_Spinner = "1";
                    break;
                case "BOLD":
                    selected_suggestedtip_type_Spinner = "2";
                    break;
                case "ITALIC":
                    selected_suggestedtip_type_Spinner = "3";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("tip_type",selected_suggestedtip_type_Spinner,"1");

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

    AdapterView.OnItemSelectedListener mStation_Type_SelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_station_type_Spinner = str_arr_station_type.get(position);
            switch (selected_station_type_Spinner){
                case "NORMAL":
                    selected_station_type_Spinner = "1";
                    break;
                case "BOLD":
                    selected_station_type_Spinner = "2";
                    break;
                case "ITALIC":
                    selected_station_type_Spinner = "3";
                    break;
            }
            GlobalMemberValues.setValueOnReceiptSetting("stationnumber_type",selected_station_type_Spinner,"1");

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
