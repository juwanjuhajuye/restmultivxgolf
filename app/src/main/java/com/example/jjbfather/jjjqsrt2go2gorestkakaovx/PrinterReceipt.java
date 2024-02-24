package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.printer.posbank.Printer;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by byullbam on 16. 2. 23..
 */
public class PrinterReceipt {

    public PrinterReceipt() {
        // 프린트에 사용된 언어 설정
        String getPrintLang = MainActivity.mDbInit.dbExecuteReadReturnString("select printlanguage from salon_storestationsettings_deviceprinter");
        if (!GlobalMemberValues.isStrEmpty(getPrintLang)) {
            switch (getPrintLang) {
                case "EN" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.ENGLISH;
                    break;
                }
                case "KO" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.KOREAN;
                    break;
                }
                case "JP" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.JAPANESE;
                    break;
                }
                case "CH" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.CHINESE;
                    break;
                }
                case "FR" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.FRENCH;
                    break;
                }
                case "IT" : {
                    GlobalMemberValues.PRINTLOCALELANG = Locale.ITALIAN;
                    break;
                }
            }
        } else {
            GlobalMemberValues.PRINTLOCALELANG = Locale.ENGLISH;
        }
    }

    public void PrintBatchStarPrint (final Context context, final JSONObject data)
    {

        Thread thread = new Thread() {
            @Override
            public void run() {

                String str_saledate = "";
                String str_service = "";
                String str_product = "";
                String str_giftcard = "";
                String str_discount = "";
                String str_void = "";
                String str_netsales = "";
                String str_refund = "";
                String str_tax = "";
                String str_grosssales = "";
                String str_cash = "";
                String str_card = "";
                String str_cardvisa = "";
                String str_cardmaster = ""; // "0||0.00"
                String str_cardamex = "";
                String str_carddiscover = "";
                String str_cardoffcard = "";
                String str_giftcard2 = "";
                String str_point = "";

                String str_cnt_visa = "";
                String str_cnt_master = "";
                String str_cnt_amex = "";
                String str_cnt_discover = "";
                String str_cnt_offcard = "";


                String str_emp_empsalesempname = "";
                String str_emp_empsalesqty = "";
                String str_emp_empsalestip = "";
                String str_emp_empsalescomm = "";
                String str_emp_empsalessalesamt = "";

                ArrayList<byte[]> list = new ArrayList<byte[]>();

                list.add(new byte[]{0x1b, 0x1d, 0x61, 0x01}); // Alignment (center)
                list.add(new byte[]{0x06, 0x09, 0x1b, 0x69, 0x01, 0x01});

                list.add("[CLOSING REPORT]\r\n\r\n".getBytes());

                list.add(new byte[]{0x1b, 0x69, 0x00, 0x00}); // Cancel Character Expansion

                list.add(new byte[]{0x1b, 0x44, 0x02, 0x10, 0x22, 0x00}); // Set horizontal tab

                String nowDate = getDate();
                String nowTime = getTime();

                try {
                    str_saledate = data.getString("saledate");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                list.add(new byte[]{0x1b, 0x44, 0x02, 0x10, 0x22, 0x00}); // Set horizontal tab
                list.add("Sale Date: ".getBytes());
                list.add(str_saledate.getBytes());


//                list.add("\t\tTime: ".getBytes());
//                list.add(nowTime.getBytes());

                list.add("\n================================================\r\n".getBytes());
                list.add("   Emp.Name      Qty    Tip    Comm   Sale.Amt\r\n".getBytes());
                list.add("------------------------------------------------\r\n\r\n".getBytes());

                Log.e("PrinterClass",data.toString());

                JSONArray arr_empSaleList = new JSONArray();
                try {
                    arr_empSaleList = data.getJSONArray("employeesaleslist");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (arr_empSaleList.length() != 0) {
                    // employeelist not null!
                    int cnt = arr_empSaleList.length();
                    for (int i = 0; i < cnt; i++) {

                        try {
                            JSONObject dic_jobc = arr_empSaleList.getJSONObject(i);
                            str_emp_empsalesempname = dic_jobc.getString("empsalesempname");
                            str_emp_empsalesqty = dic_jobc.getString("empsalesqty");
                            str_emp_empsalestip = dic_jobc.getString("empsalestip");
                            str_emp_empsalescomm = dic_jobc.getString("empsalescomm");
                            str_emp_empsalessalesamt = dic_jobc.getString("empsalessalesamt");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 인쇄실행해야 하는 부분.

                        list.add(new byte[]{0x1b, 0x1d, 0x61, 0x00}); // Alignment

                        list.add(new byte[]{0x1b, 0x44, 0x02, 0x10, 0x22, 0x00}); // Set horizontal tab

                        list.add(str_emp_empsalesempname.getBytes());
                        list.add(new byte[]{0x09});
                        list.add(spaceAddString_qty(str_emp_empsalesqty).getBytes());
                        list.add(spaceAddString_tip(str_emp_empsalestip).getBytes());
                        list.add(spaceAddString_tip(str_emp_empsalescomm).getBytes());
                        list.add(spaceAddString_total(str_emp_empsalessalesamt).getBytes());
                        list.add("\r\n".getBytes());

                    }

                    list.add("\n================================================\r\n".getBytes());
                }


                try {
                    str_service = data.getString("service");
                    str_product = data.getString("product");
                    str_giftcard = data.getString("giftcard");
                    str_discount = data.getString("discount");
                    str_void = data.getString("void");

                    str_netsales = data.getString("netsales");
                    str_refund = data.getString("refund");
                    str_tax = data.getString("tax");
                    str_grosssales = data.getString("grosssales");

                    str_cash = data.getString("cash");
                    str_card = data.getString("card");
                    str_cardvisa = data.getString("cardvisa");
                    str_cnt_visa = cardReadCount(data.getString("cardvisa"));
                    str_cardmaster = data.getString("cardmaster");
                    str_cnt_master = cardReadCount(data.getString("cardmaster"));
                    str_cardamex = data.getString("cardamex");
                    str_cnt_amex = cardReadCount(data.getString("cardamex"));
                    str_carddiscover = data.getString("carddiscover");
                    str_cnt_discover = cardReadCount(data.getString("carddiscover"));
                    str_cardoffcard = data.getString("cardoffcard");
                    str_cnt_offcard = cardReadCount(data.getString("cardoffcard"));

                    str_giftcard2 = data.getString("giftcard2");
                    str_point = data.getString("point");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                list.add("SERVICE \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_service).getBytes());
                list.add("\n".getBytes());
                list.add("PRODUCT \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_product).getBytes());
                list.add("\n".getBytes());
                list.add("GIFT CARD \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_giftcard).getBytes());
                list.add("\n".getBytes());
                list.add("DISCOUNT \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_discount).getBytes());
                list.add("\n".getBytes());
                list.add("VOID \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_void).getBytes());
                list.add("\n================================================\r\n".getBytes());

                list.add("NET SALES \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_netsales).getBytes());
                list.add("REFUND \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_refund).getBytes());
                list.add("TAX \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_tax).getBytes());
                list.add("GROSS SALES \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_grosssales).getBytes());
                list.add("\n================================================\r\n".getBytes());

                list.add("CASH \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_cash).getBytes());
                list.add("CREDIT CARD \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_card).getBytes());

                list.add("               VISA            ".getBytes());
                list.add(str_cnt_visa.getBytes());
                list.add("     ".getBytes());
                list.add(spaceAddString_total(cardAmountString(str_cardvisa)).getBytes());
                list.add("\n".getBytes());
                list.add("             MASTER            ".getBytes());
                list.add(str_cnt_master.getBytes());
                list.add("     ".getBytes());
                list.add(spaceAddString_total(cardAmountString(str_cardmaster)).getBytes());
                list.add("\n".getBytes());
                list.add("               AMEX            ".getBytes());
                list.add(str_cnt_amex.getBytes());
                list.add("     ".getBytes());
                list.add(spaceAddString_total(cardAmountString(str_cardamex)).getBytes());
                list.add("\n".getBytes());
                list.add("           DISCOVER            ".getBytes());
                list.add(str_cnt_discover.getBytes());
                list.add("     ".getBytes());
                list.add(spaceAddString_total(cardAmountString(str_carddiscover)).getBytes());
                list.add("\n".getBytes());
                list.add("           OFF CARD            ".getBytes());
                list.add(str_cnt_offcard.getBytes());
                list.add("     ".getBytes());
                list.add(spaceAddString_total(cardAmountString(str_cardoffcard)).getBytes());
                list.add("\n".getBytes());

                list.add("GIFT CARD \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_giftcard2).getBytes());
                list.add("POINT \u0009\u0009   ".getBytes());
                list.add(spaceAddString_total(str_point).getBytes());
                list.add("\n================================================\r\n".getBytes());

                list.add("Printed \u0009            ".getBytes());
                list.add(nowTime.getBytes());
                list.add("  ".getBytes());
                list.add(nowDate.getBytes());

                list.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
                list.add(new byte[]{0x07}); // Kick cash drawer

                sendCommand(list, context);
            }
        };
        thread.start();
    }

    public void PrintPaymentStarPrint (final Context context, final JSONObject data)
    {
        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){
                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemtaxexempt = "";

                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";
                String str_customername = "";

                String str_receiptprinttype = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_change = data.getString("change");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");


                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction"))
                    {
                        jsonArray_creditcardtransaction = data.getJSONArray("creditcardtransaction");
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }


                ArrayList<byte[]> list = new ArrayList<byte[]>();

                list.add(new byte[]{0x1b, 0x1d, 0x61, 0x01}); // Alignment (center)
//                list.add(new byte[]{0x06, 0x09, 0x1b, 0x69, 0x01, 0x01}); 글씨 크기 확장.
                list.add("*** ".getBytes());
                list.add(str_storename.getBytes());
                list.add(" ***\r\n\r\n".getBytes());
//                list.add(new byte[]{0x1b, 0x69, 0x00, 0x00}); // Cancel Character Expansion

                list.add(new byte[]{0x1b, 0x44, 0x02, 0x10, 0x22, 0x00}); // Set horizontal tab

                //list.add("ADDRESS\n".getBytes());
                list.add((str_storeaddress1 + " ").getBytes());
                list.add((str_storeaddress2 + " ").getBytes());
                list.add("\n".getBytes());
                list.add(str_storecity.getBytes());
                //list.add(",".getBytes());
                list.add((" " + str_storestate).getBytes());
                //list.add(" ".getBytes());
                //list.add(str_storecity.getBytes());
                list.add((" " + str_storezip).getBytes());
                list.add("\n(TEL) ".getBytes());
                list.add(telNumberExch(str_storephone).getBytes());
                // 여기에 전화번호 치환 넣기
                list.add("\n".getBytes());

                list.add(new byte[] { 0x1b, 0x1d, 0x61, 0x00 }); // Alignment
                list.add("\nEmployee Name : ".getBytes());
                list.add(str_employeename.getBytes());
                list.add("\nCustomer Name    : ".getBytes());
                list.add(str_customername.getBytes());
                //list.add("\nReceipt No. : ".getBytes());
                list.add(str_receiptno.getBytes());
                list.add("\nSale Date : ".getBytes());
                list.add(str_saledate.getBytes());
                list.add("\nSale Time : ".getBytes());
                list.add(str_saletime.getBytes());
                list.add("\n".getBytes());

                list.add("------------------------------------------------\r\n".getBytes());
                list.add(" Item Name            Qty      Price     Amount \r\n".getBytes());
                list.add("------------------------------------------------\r\n".getBytes());

                if (jsonArray_saleitemlist.length() != 0)
                {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++)
                    {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        list.add(Payment_stringBackSpace_Exch(21, str_itemname).getBytes()); // 21자.
                        list.add(Payment_stringFowardSpace_Exch(4, str_itemqty).getBytes());
                        list.add(Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice)).getBytes());
                        list.add(Payment_stringFowardSpace_Exch(11, str_itemamount).getBytes());
                        list.add(Payment_stringFowardSpace_Exch(11, str_itemtaxexempt).getBytes());
                        list.add("\n".getBytes());
                    }
                }
                list.add("------------------------------------------------\r\n".getBytes());
                list.add("Sub Total \t\t  ".getBytes());
                list.add(Payment_stringFowardSpace_Exch(12,str_subtotal).getBytes());
                list.add("\n".getBytes());
                list.add("Tax \t\t  ".getBytes());
                list.add(Payment_stringFowardSpace_Exch(12,str_tax).getBytes());
                list.add("\n".getBytes());
                list.add("Total Amount \t\t  ".getBytes());
                list.add(Payment_stringFowardSpace_Exch(12,str_totalamount).getBytes());
                list.add("\n".getBytes());
                list.add("------------------------------------------------\r\n".getBytes());

                if (str_creditcardtendered.length() != 0)
                {
                    list.add("Credit Card Tendered \t\t  ".getBytes());
                    list.add(Payment_stringFowardSpace_Exch(12,str_creditcardtendered).getBytes());
                    list.add("\n".getBytes());
                }

                list.add("Cash Tendered \t\t  ".getBytes());
                list.add(Payment_stringFowardSpace_Exch(12,str_cashtendered).getBytes());
                list.add("\n".getBytes());
                list.add("Change \t\t  ".getBytes());
                list.add(Payment_stringFowardSpace_Exch(12,str_change).getBytes());
                if (str_giftcardtendered.toString() == "0") {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    list.add("\n".getBytes());
                    list.add("Change \t\t  ".getBytes());
                    list.add(Payment_stringFowardSpace_Exch(12,str_giftcardtendered).getBytes());
                }
                list.add("------------------------------------------------\r\n\n".getBytes());

                ArrayList<byte[]> merchant_list = (ArrayList<byte[]>) list.clone();
                ArrayList<byte[]> customer_list = (ArrayList<byte[]>) list.clone();

                // card
                if (jsonArray_creditcardtransaction.length() != 0)
                {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);
                            str_cardchangeamount = dic_card_item.getString("chargeamount");
                            str_cardtype = dic_card_item.getString("cardtype");

                            str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            str_cardauthnumber = dic_card_item.getString("cardauthnumber");


                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            }
                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            }
                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        merchant_list.add("******** Credit Card Transaction *********\r\n".getBytes());
                        customer_list.add("******** Credit Card Transaction *********\r\n".getBytes());
                        merchant_list.add("Card Type \t : ".getBytes());
                        customer_list.add("Card Type \t : ".getBytes());
                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
                        merchant_list.add("\n".getBytes());
                        customer_list.add("\n".getBytes());
                        merchant_list.add("Credit Card# \t : ".getBytes());
                        customer_list.add("Credit Card# \t : ".getBytes());
                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
                        customer_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
                        merchant_list.add("\n".getBytes());
                        customer_list.add("\n".getBytes());
                        merchant_list.add("Charge Amount \t : ".getBytes());
                        customer_list.add("Charge Amount \t : ".getBytes());
                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
                        merchant_list.add("\n".getBytes());
                        customer_list.add("\n".getBytes());
                        merchant_list.add("Card Auth# \t : ".getBytes());
                        customer_list.add("Card Auth# \t : ".getBytes());
                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
                        merchant_list.add("\n".getBytes());
                        customer_list.add("\n".getBytes());
                        merchant_list.add("Card Ref# \t : ".getBytes());
                        customer_list.add("Card Ref# \t : ".getBytes());
                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
                        merchant_list.add("\n".getBytes());
                        customer_list.add("\n".getBytes());

                        if (str_cardaid.length() != 0){
                            merchant_list.add("AID \t : ".getBytes());
                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
                            merchant_list.add("\n".getBytes());
                            customer_list.add("AID \t : ".getBytes());
                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
                            customer_list.add("\n".getBytes());
                        }
                        if (str_cardtvr.length() != 0){
                            merchant_list.add("TSI \t : ".getBytes());
                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
                            merchant_list.add("\n".getBytes());
                            customer_list.add("TSI \t : ".getBytes());
                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
                            customer_list.add("\n".getBytes());
                        }
                        if (str_cardtsi.length() != 0){
                            merchant_list.add("TVR \t : ".getBytes());
                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
                            merchant_list.add("\n".getBytes());
                            customer_list.add("TVR \t : ".getBytes());
                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
                            customer_list.add("\n".getBytes());
                        }
                        merchant_list.add("------------------------------------------------\r\n\n".getBytes());
                        customer_list.add("------------------------------------------------\r\n\n".getBytes());


                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            //merchant copy
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchant_list.add("\t              Tip : _________________________  \n".getBytes());
                                merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            //customer copy
                            //merchant copy
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchant_list.add("\t              Tip : _________________________  \n".getBytes());
                                merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            }
                        } else {

                        }

                    }
                }
                customer_list.add("################# Customer Copy ################\r\n".getBytes());
                customer_list.add("     ** Thank you. Come again soon. **\r\n".getBytes());
                customer_list.add("             ".getBytes());
                customer_list.add(getDate().getBytes());
                customer_list.add(" ".getBytes());
                customer_list.add(getTime().getBytes());
                customer_list.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
                customer_list.add(new byte[]{0x07}); // Kick cash drawer


                merchant_list.add("################# Merchant Copy ################\r\n".getBytes());
//                merchant_list.add("     ** Thank you. Come again soon. **\r\n".getBytes());
                merchant_list.add("             ".getBytes());
                merchant_list.add(getDate().getBytes());
                merchant_list.add(" ".getBytes());
                merchant_list.add(getTime().getBytes());
                merchant_list.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
                merchant_list.add(new byte[]{0x07}); // Kick cash drawer


                if (str_receiptprinttype.toString() == "1"){
                    sendCommand(customer_list, context);
                } else if (str_receiptprinttype.toString() == "2"){
                    sendCommand(merchant_list, context);
                } else if (str_receiptprinttype.toString() == "3"){
                    sendCommand(customer_list, context);
                    sendCommand(merchant_list, context);
                } else {
                    sendCommand(customer_list, context);
                }
            }
        };
        thread.start();


    }

    @SuppressLint("LongLogTag")
    private void sendCommand(ArrayList<byte[]> byteList, Context context)
    {
        StarIOPort port = null;
        try {
			/*
			 * using StarIOPort3.1.jar (support USB Port) Android OS Version: upper 2.2
			 */

            port = StarIOPort.getPort("TCP:" + GlobalMemberValues.getNetworkPrinterIpPort(), "", 10000, context);
//            port = StarIOPort.getPort("TCP:192.168.0.110", "", 10000);
			/*
			 * using StarIOPort.jar Android OS Version: under 2.1 port = StarIOPort.getPort(portName, portSettings, 10000);
			 */
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

			/*
			 * Using Begin / End Checked Block method When sending large amounts of raster data,
			 * adjust the value in the timeout in the "StarIOPort.getPort" in order to prevent
			 * "timeout" of the "endCheckedBlock method" while a printing.
			 *
			 * If receipt print is success but timeout error occurs(Show message which is "There
			 * was no response of the printer within the timeout period." ), need to change value
			 * of timeout more longer in "StarIOPort.getPort" method.
			 * (e.g.) 10000 -> 30000
			 */
            StarPrinterStatus status = port.beginCheckedBlock();

            if (true == status.offline) {
                throw new StarIOPortException("A printer is offline");
            }

            byte[] commandToSendToPrinter = convertFromListByteArrayTobyteArray(byteList);
            port.writePort(commandToSendToPrinter, 0, commandToSendToPrinter.length);

            port.setEndCheckedBlockTimeoutMillis(30000);// Change the timeout time of endCheckedBlock method.
            status = port.endCheckedBlock();

            if (status.coverOpen == true) {
                throw new StarIOPortException("Printer cover is open");
            } else if (status.receiptPaperEmpty == true) {
                throw new StarIOPortException("Receipt paper is empty");
            } else if (status.offline == true) {
                throw new StarIOPortException("Printer is offline");
            }
        } catch (StarIOPortException e) {
//            Builder dialog = new AlertDialog.Builder(context);
//            dialog.setNegativeButton("OK", null);
//            AlertDialog alert = dialog.create();
//            alert.setTitle("Failure");
//            alert.setMessage(e.getMessage());
//            alert.setCancelable(false);
//            alert.show();
            Log.e("byullbam StarIOPortError",e.toString());
        } finally {
            if (port != null) {
                try {
                    StarIOPort.releasePort(port);
                } catch (StarIOPortException e) {
                }
            }
        }
    }
    private static byte[] convertFromListByteArrayTobyteArray(List<byte[]> ByteArray) {
        int dataLength = 0;
        for (int i = 0; i < ByteArray.size(); i++) {
            dataLength += ByteArray.get(i).length;
        }

        int distPosition = 0;
        byte[] byteArray = new byte[dataLength];
        for (int i = 0; i < ByteArray.size(); i++) {
            System.arraycopy(ByteArray.get(i), 0, byteArray, distPosition, ByteArray.get(i).length);
            distPosition += ByteArray.get(i).length;
        }

        return byteArray;
    }

    private String getDate(){
        String nowDate = "";
        Date curDate;
        String     strCurTime;
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        curDate = new Date();
        strCurTime = sdf.format(curDate);
        System.out.println(strCurTime);
        nowDate = strCurTime;
        return nowDate;
    }
    private String getTime(){
        String nowTime = "";
        Date     curDate;
        String     strCurTime;
        SimpleDateFormat  sdf;
        sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        curDate = new Date();
        strCurTime = sdf.format(curDate);
        System.out.println(strCurTime);
        nowTime = strCurTime;
        return nowTime;
    }

    private String spaceAddString_qty(String instr)
    {
        String return_str = "";

        instr = GlobalMemberValues.getDecodeString(instr);

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength == 1)
        {
            instr = "    " + instr;
        } else if (strLength == 2)
        {
            instr = "   " + instr;
        } else if (strLength == 3)
        {
            instr = "  " + instr;
        } else
        {

        }
        return_str = instr;
        return return_str;
    }
    private String spaceAddString_tip(String instr)
    {
        String return_str = "";

        instr = GlobalMemberValues.getDecodeString(instr);

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength == 4)
        {
            instr = "    " + instr;
        } else if (strLength == 5)
        {
            instr = "   " + instr;
        } else if (strLength == 6)
        {
            instr = "  " + instr;
        } else
        {
            instr = " " + instr;
        }

        return_str = instr;
        return return_str;
    }
    private String spaceAddString_total(String instr)
    {
        String return_str = "";

        instr = GlobalMemberValues.getDecodeString(instr);

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength == 4)
        {
            instr = "       " + instr;
        } else if (strLength == 5)
        {
            instr = "      " + instr;
        } else if (strLength == 6)
        {
            instr = "     " + instr;
        } else if (strLength == 7)
        {
            instr = "    " + instr;
        } else if (strLength == 8)
        {
            instr = "   " + instr;
        } else if (strLength == 9)
        {
            instr = "  " + instr;
        } else
        {
            instr = " " + instr;
        }

        return_str = instr;
        return return_str;
    }

    private String cardReadCount(String instr)
    {
        String return_str = "0";
        String[] arr = instr.split("\\|\\|");
        if (arr[0].toString().equals(""))
        {

        }else
        {
            return_str = arr[0].toString();
        }
        return return_str;
    }
    private String cardAmountString(String instr)
    {
        String return_str = "0.00";
        String[] arr = instr.split("\\|\\|");
        if (arr.length == 0 ||arr.length == 1) {
            return_str = "0.00";
        } else {
            if (arr[1].toString().equals("")) {
                return_str = "0.00";
            } else {
                return_str = arr[1].toString();
            }
        }
        return return_str;
    }
    @SuppressLint("LongLogTag")
    private String telNumberExch(String instr)
    {
        String return_str = "";
        String temp_str = "";
        String temp_str2 = "";
        temp_str = instr.replace("-","");
        Log.e("temp_strtemp_strtemp_strtemp_str",temp_str);
        if (temp_str.length() == 11)
        {
            temp_str2 = temp_str.substring(0,1) + "-" + temp_str.substring(1,4) + "-" + temp_str.substring(4,7) + "-" + temp_str.substring(7,11);
        } else if (temp_str.length() == 10)
        {
            temp_str2 = temp_str.substring(0,3) + "-" + temp_str.substring(3,6) + "-" + temp_str.substring(6,10);
        } else
        {

        }
        return_str = temp_str2;
        return return_str;
    }
    private String Payment_stringBackSpace_Exch(int stringLength ,String instr)
    {
        String return_str = "";
        String temp_str = "";

        if (!GlobalMemberValues.isStrEmpty(instr)) {
            instr = GlobalMemberValues.getDecodeString(instr);

            int strLength = GlobalMemberValues.getSizeToString(instr);

            if (strLength > stringLength) {
                //temp_str = instr.substring(0,stringLength) + " ";
                temp_str = GlobalMemberValues.getJJJSubString(instr, 0, stringLength) + " ";
            } else {
                int i_space = stringLength - strLength;
                StringBuilder temp_build = new StringBuilder(instr);
                temp_str = temp_build.toString();
            }

            //return_str = strLength + "-" + temp_str;
            return_str = temp_str;
        }

        return return_str;
    }
    private String Payment_stringFowardSpace_Exch(int stringLength ,String instr)
    {
        String return_str = "";
        String temp_str = "";

        instr = GlobalMemberValues.getDecodeString(instr);

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength > stringLength) {
//            temp_str = instr.substring(0,stringLength);
            temp_str = substringByBytes(instr,2,21);
        } else {
            int i_space = stringLength - strLength;
            StringBuilder temp_build = new StringBuilder("");
            for (int i = 0; i < i_space; i++){
                temp_build.append(" ");
            }
            temp_build.append(instr);
            temp_str = temp_build.toString();
        }

        return_str = temp_str;
        return return_str;
    }

    private String substringByBytes(String str, int beginBytes, int endBytes) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (beginBytes < 0) {
            beginBytes = 0;
        }
        if (endBytes < 1) {
            return "";
        }
        int len = str.length();

        int beginIndex = -1;
        int endIndex = 0;

        int curBytes = 0;
        String ch = null;
        for (int i = 0; i < len; i++) {
            ch = str.substring(i, i + 1);
            curBytes += ch.getBytes().length;

            if (beginIndex == -1 && curBytes >= beginBytes) {
                beginIndex = i;
            }
            if (curBytes > endBytes) {
                break;
            } else {
                endIndex = i + 1;
            }
        }
        return str.substring(beginIndex, endIndex);
    }


    public String combineStringForVoidPrint(String paramStr) {
        String returnValue = "";
        double tempParamStr = GlobalMemberValues.getDoubleAtString(paramStr);
        if (tempParamStr == 0) {
            returnValue = paramStr;
        } else {
            tempParamStr = tempParamStr * -1;
            returnValue = GlobalMemberValues.getStringFormatNumber(tempParamStr, "2");
        }

        return returnValue;
    }

    public void openCashDrawerPosbank(){

        if (PosbankPrinter.mPrinter == null){
            return;
        }
        Thread thread = new Thread(){
            @Override
            public void run(){
                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);
                GlobalMemberValues.disconnectPrinter();
            }
        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }


    public void printBatchPosbank(final JSONObject data) {

        if (PosbankPrinter.mPrinter == null){
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {


                int alignment = Printer.ALIGNMENT_LEFT;
//        Printer.ALIGNMENT_LEFT
//        Printer.ALIGNMENT_CENTER
//        Printer.ALIGNMENT_RIGHT
                int size = 0;
                int attribute = 0;

                String textlist = "";

                String str_saledate = "";
                String str_service = "";
                String str_product = "";
                String str_giftcard = "";
                String str_discount = "";
                String str_void = "";
                String str_netsales = "";
                String str_refund = "";
                String str_tax = "";
                String str_grosssales = "";
                String str_cash = "";
                String str_card = "";
                String str_cardvisa = "";
                String str_cardmaster = ""; // "0||0.00"
                String str_cardamex = "";
                String str_carddiscover = "";
                String str_cardoffcard = "";
                String str_giftcard2 = "";
                String str_point = "";
                String str_other = "";

                String str_cnt_visa = "";
                String str_cnt_master = "";
                String str_cnt_amex = "";
                String str_cnt_discover = "";
                String str_cnt_offcard = "";


                String str_emp_empsalesempname = "";
                String str_emp_empsalesqty = "";
                String str_emp_empsalestip = "";
                String str_emp_empsalescomm = "";
                String str_emp_empsalessalesamt = "";

                String str_cardtip = "";
                String str_pickupfee = "";
                String str_deliveryfee = "";

                textlist +=  "[CLOSING REPORT]\r\n\r\n";
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 16|1, false);

                String nowDate = getDate();
                String nowTime = getTime();

                try {
                    str_saledate = data.getString("saledate");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                textlist = "Sale Date: " + str_saledate;
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 0, false);

//                list.add("\t\tTime: ".getBytes());
//                list.add(nowTime.getBytes());

                textlist = "";
                textlist += "\n==========================================\r\n";
                textlist += "Emp.Name                 Tip      Sale.Amt\r\n";
                //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                textlist += "------------------------------------------\r\n\r\n";

                Log.e("PrinterClass",data.toString());

                JSONArray arr_empSaleList = new JSONArray();
                try {
                    arr_empSaleList = data.getJSONArray("employeesaleslist");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (arr_empSaleList.length() != 0) {
                    // employeelist not null!
                    int cnt = arr_empSaleList.length();
                    for (int i = 0; i < cnt; i++) {

                        try {
                            JSONObject dic_jobc = arr_empSaleList.getJSONObject(i);
                            str_emp_empsalesempname = dic_jobc.getString("empsalesempname");
                            str_emp_empsalesqty = dic_jobc.getString("empsalesqty");
                            str_emp_empsalestip = dic_jobc.getString("empsalestip");
                            str_emp_empsalescomm = dic_jobc.getString("empsalescomm");
                            str_emp_empsalessalesamt = dic_jobc.getString("empsalessalesamt");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 인쇄실행해야 하는 부분.


                        textlist += strlengthcut(str_emp_empsalesempname,19);
//                        textlist += "\t";
                        //textlist += spaceAddString_qty(str_emp_empsalesqty);
                        textlist += spaceAddString_tip(str_emp_empsalestip);
                        //textlist += spaceAddString_tip(str_emp_empsalescomm);
                        textlist += (spaceAddString_total(str_emp_empsalessalesamt)) + "\r\n";

                    }

                    textlist += "\n==========================================\r\n";
                }


                try {
                    str_service = data.getString("service");
                    str_product = data.getString("product");
                    str_giftcard = data.getString("giftcard");
                    str_discount = data.getString("discount");
                    str_void = data.getString("void");

                    str_netsales = data.getString("netsales");
                    str_refund = data.getString("refund");
                    str_tax = data.getString("tax");
                    str_grosssales = data.getString("grosssales");

                    str_cash = data.getString("cash");
                    str_card = data.getString("card");
                    str_cardvisa = data.getString("cardvisa");
                    str_cnt_visa = cardReadCount(data.getString("cardvisa"));
                    str_cardmaster = data.getString("cardmaster");
                    str_cnt_master = cardReadCount(data.getString("cardmaster"));
                    str_cardamex = data.getString("cardamex");
                    str_cnt_amex = cardReadCount(data.getString("cardamex"));
                    str_carddiscover = data.getString("carddiscover");
                    str_cnt_discover = cardReadCount(data.getString("carddiscover"));
                    str_cardoffcard = data.getString("cardoffcard");
                    str_cnt_offcard = cardReadCount(data.getString("cardoffcard"));

                    str_giftcard2 = data.getString("giftcard2");
                    str_point = data.getString("point");
                    str_other = data.getString("other");

                    str_cardtip = data.getString("cardtip");
                    str_pickupfee = data.getString("pickupfee");
                    str_deliveryfee = data.getString("deliveryfee");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                textlist += "SERVICE \t\t       ";
                textlist += spaceAddString_total(str_service);
                textlist +="\n";
                textlist += "PRODUCT \t\t       ";
                textlist += spaceAddString_total(str_product);
                textlist += "\n";
                textlist += "GIFT CARD \t\t       ";
                textlist += spaceAddString_total(str_giftcard);
                textlist += "\n";
                textlist += "DISCOUNT \t\t       ";
                textlist += spaceAddString_total(str_discount);
                textlist += "\n";
                textlist += "PICK UP FEE \t\t    ";
                textlist += spaceAddString_total(str_pickupfee);
                textlist += "\n";
                textlist += "DELIVERY FEE \t\t   ";
                textlist += spaceAddString_total(str_deliveryfee);
                textlist += "\n";
                textlist += "VOID \t\t\t       ";
                textlist += spaceAddString_total(str_void);
                textlist += "\n==========================================\r\n";
//
                textlist += "NET SALES \t\t       ";
                textlist += spaceAddString_total(str_netsales);
                textlist += "REFUND \t\t\t       ";
                textlist += spaceAddString_total(str_refund);
                textlist += "TAX \t\t\t       ";
                textlist += spaceAddString_total(str_tax);
                textlist += "GROSS SALES \t\t       ";
                textlist += spaceAddString_total(str_grosssales);
                textlist += "\n==========================================\r\n";

                textlist += "CASH \t\t\t       ";
                textlist += spaceAddString_total(str_cash);
                textlist += "CREDIT CARD \t\t       ";
                textlist += spaceAddString_total(str_card);
                textlist += "CREDIT CARD TIP \t\t   ";
                textlist += spaceAddString_total(str_cardtip);
                textlist += "         VISA            ";
                textlist += str_cnt_visa + "     ";
                textlist += spaceAddString_total(cardAmountString(str_cardvisa)) + "\n";
                textlist += "       MASTER            ";
                textlist += str_cnt_master + "     ";
                textlist += spaceAddString_total(cardAmountString(str_cardmaster)) + "\n";
                textlist += "         AMEX            ";
                textlist += str_cnt_amex + "     ";
                textlist += spaceAddString_total(cardAmountString(str_cardamex)) +"\n";
                textlist += "     DISCOVER            ";
                textlist += str_cnt_discover + "     ";
                textlist += spaceAddString_total(cardAmountString(str_carddiscover)) +"\n";
                textlist += "     OFF CARD            ";
                textlist += str_cnt_offcard + "     ";
                textlist += spaceAddString_total(cardAmountString(str_cardoffcard)) + "\n";

                textlist += "GIFT CARD REDEEMED \t\t";
                textlist += spaceAddString_total(str_giftcard2) + "\n";
                textlist += "POINT \t\t\t       ";
                textlist += spaceAddString_total(str_point) + "\n";
                textlist += "OTHER \t\t\t       ";
                textlist += spaceAddString_total(str_other);
                textlist += "\n==========================================\r\n";
                textlist += "Printed \t      ";
                textlist += nowTime + "  " + nowDate;


//                sendCommand(list, context);
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, size, false);
                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);
                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }

        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        //GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public void printCashOutPosbank(final JSONObject data) {

        if (PosbankPrinter.mPrinter == null){
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                int alignment = Printer.ALIGNMENT_LEFT;
//        Printer.ALIGNMENT_LEFT
//        Printer.ALIGNMENT_CENTER
//        Printer.ALIGNMENT_RIGHT
                int size = 0;
                int attribute = 0;

                String textlist = "";

                String str_cashinoutdate = "";

                String str_totalsaleamount = "";
                String str_commissionamount = "";
                String str_deliverypickupfeeamount = "";
                String str_tipamount = "";
                String str_tipvoidamount = "";

                String str_foodsaleamount = "";
                String str_productsaleamount = "";
                String str_giftcardsaleamount = "";
                String str_discountamount = "";
                String str_voidamount = "";
                String str_netsalesamount = "";
                String str_refundamount = "";
                String str_taxamount = "";
                String str_grosssalesamount = "";

                String str_cashintotal = "";
                String str_salescashtotal = "";
                String str_allcashtotal = "";
                String str_salechecktotal = "";
                String str_salecardtotal = "";
                String str_cardvisa = "";
                String str_cardmaster = ""; // "0||0.00"
                String str_cardamex = "";
                String str_carddiscover = "";
                String str_cardoffcard = "";
                String str_salegiftcardtotal = "";
                String str_salepointtotal = "";

                String str_cnt_visa = "";
                String str_cnt_master = "";
                String str_cnt_amex = "";
                String str_cnt_discover = "";
                String str_cnt_offcard = "";


                String str_emp_loginouttype = "";
                String str_emp_loginoutdate = "";

                String str_emp_cashinreason = "";
                String str_emp_cashinmoney = "";

                String str_cashinoutemployeenameyn = "";
                String str_cashinoutemployeename = "";

                String str_cashouttotal = "";
                String str_overshorttxt = "";
                String str_overshortamt = "";
                String str_salecardtiptotal = "";
                String str_endcashtotal = "";

                String str_pickupfeeamount = "";
                String str_deliveryfeeamount = "";

                String str_startingcashprintyn = "";

                str_startingcashprintyn = GlobalMemberValues.getDataInJsonData(data, "startingcashprintyn");
                if (GlobalMemberValues.isStrEmpty(str_startingcashprintyn)) {
                    str_startingcashprintyn = "N";
                }

                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    textlist =  "[CASHOUT REPORT]\r\n\r\n";
                    GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 16|1, false);
                } else {
                    textlist =  "[STARTING CASH REPORT]\r\n\r\n";
                    GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 16|1, false);
                }



                str_cashinoutemployeenameyn = GlobalMemberValues.getDataInJsonData(data, "cashinoutemployeenameyn");
                if (str_cashinoutemployeenameyn == "Y" || str_cashinoutemployeenameyn.equals("Y")) {
                    // Admin 이 직원별로 Cash In/Out 기록을 출력할 때 ------------------------------------
                    str_cashinoutemployeename = GlobalMemberValues.getDataInJsonData(data, "employeename");
                    textlist = "Employee Name : " + str_cashinoutemployeename;
                    GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 0, false);
                    // ---------------------------------------------------------------------------------
                } else {
                    textlist = "Employee Name : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "\n";
                    GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 0, false);
                }

                String nowDate = getDate();
                String nowTime = getTime();

                str_cashinoutdate = GlobalMemberValues.getDataInJsonData(data, "cashinoutdate");

                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    textlist = "\nCash Out Date : " + str_cashinoutdate;
                    GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 0, false);
                }

//                list.add("\t\tTime: ".getBytes());
//                list.add(nowTime.getBytes());

                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    // 직원 로그인/아웃 기록 출력 --------------------------------------------------------
                    textlist = "";
                    textlist += "\n\n[Log in/out data]\r\n";
                    textlist += "==========================================\r\n";
                    textlist += "Type                       Date\r\n";
                    //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                    textlist += "------------------------------------------\r\n";

                    Log.e("PrinterClass",data.toString());
                    GlobalMemberValues.logWrite("cashoutjsonlog", "json2 : " + data.toString() + "\n");

                    JSONArray arr_empLoginoutList = new JSONArray();
                    try {
                        arr_empLoginoutList = data.getJSONArray("employeeloginoutlog");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (arr_empLoginoutList.length() != 0) {
                        // employeelist not null!
                        int cnt = arr_empLoginoutList.length();
                        for (int i = 0; i < cnt; i++) {

                            try {
                                JSONObject dic_jobc = arr_empLoginoutList.getJSONObject(i);
                                str_emp_loginouttype = dic_jobc.getString("loginouttype");
                                str_emp_loginoutdate = dic_jobc.getString("loginoutdatetime");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // 인쇄실행해야 하는 부분.
                            textlist += str_emp_loginouttype + " \t   ";
                            textlist += strlengthcut(str_emp_loginoutdate,20) + "\r\n";
                        }
                        textlist += "==========================================\r\n";
                    }
                    // ---------------------------------------------------------------------------------
                }

                // 직원 Cash In 기록 출력 -----------------------------------------------------------
                textlist += "\n[Cash in data]\r\n";
                textlist += "==========================================\r\n";
                //textlist += "Reason                            Amount\r\n";
                //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                //textlist += "------------------------------------------\r\n";

                Log.e("PrinterClass",data.toString());

                JSONArray arr_empCashinList = new JSONArray();
                try {
                    arr_empCashinList = data.getJSONArray("employeecashinlog");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (arr_empCashinList.length() != 0) {
                    // employeelist not null!
                    int cnt = arr_empCashinList.length();
                    for (int i = 0; i < cnt; i++) {

                        try {
                            JSONObject dic_jobc = arr_empCashinList.getJSONObject(i);
                            str_emp_cashinmoney = dic_jobc.getString("cashinmoney");
                            str_emp_cashinreason = dic_jobc.getString("cashinreason");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 인쇄실행해야 하는 부분.
                        textlist += str_emp_cashinreason + "  :  ";
                        textlist += spaceAddString_total(str_emp_cashinmoney) +"\n";
                    }
                    textlist += "==========================================\r\n";
                }
                // ---------------------------------------------------------------------------------

                str_totalsaleamount = GlobalMemberValues.getDataInJsonData(data, "totalsaleamount");
                str_commissionamount = GlobalMemberValues.getDataInJsonData(data, "commissionamount");
                str_deliverypickupfeeamount = GlobalMemberValues.getDataInJsonData(data, "deliverypickupfeeamount");
                str_tipamount = GlobalMemberValues.getDataInJsonData(data, "tipamount");

                str_foodsaleamount = GlobalMemberValues.getDataInJsonData(data, "foodsaleamount");
                str_productsaleamount = GlobalMemberValues.getDataInJsonData(data, "productsaleamount");
                str_giftcardsaleamount = GlobalMemberValues.getDataInJsonData(data, "giftcardsaleamount");
                str_discountamount = GlobalMemberValues.getDataInJsonData(data, "discountamount");
                str_voidamount = GlobalMemberValues.getDataInJsonData(data, "voidamount");

                str_netsalesamount = GlobalMemberValues.getDataInJsonData(data, "netsalesamount");
                str_refundamount = GlobalMemberValues.getDataInJsonData(data, "refundamount");
                str_taxamount = GlobalMemberValues.getDataInJsonData(data, "taxamount");
                str_grosssalesamount = GlobalMemberValues.getDataInJsonData(data, "grosssalesamount");

                str_cashintotal = GlobalMemberValues.getDataInJsonData(data, "cashintotal");
                str_salescashtotal = GlobalMemberValues.getDataInJsonData(data, "salescashtotal");
                str_allcashtotal = GlobalMemberValues.getDataInJsonData(data, "allcashtotal");

                str_salechecktotal = GlobalMemberValues.getDataInJsonData(data, "salechecktotal");

                str_salecardtotal = GlobalMemberValues.getDataInJsonData(data, "salecardtotal");
                str_cardvisa = GlobalMemberValues.getDataInJsonData(data, "cardvisa");
                str_cnt_visa = cardReadCount(GlobalMemberValues.getDataInJsonData(data, "cardvisa"));
                str_cardmaster = GlobalMemberValues.getDataInJsonData(data, "cardmaster");
                str_cnt_master = cardReadCount(GlobalMemberValues.getDataInJsonData(data, "cardmaster"));
                str_cardamex = GlobalMemberValues.getDataInJsonData(data, "cardamex");
                str_cnt_amex = cardReadCount(GlobalMemberValues.getDataInJsonData(data, "cardamex"));
                str_carddiscover = GlobalMemberValues.getDataInJsonData(data, "carddiscover");
                str_cnt_discover = cardReadCount(GlobalMemberValues.getDataInJsonData(data, "carddiscover"));
                str_cardoffcard = GlobalMemberValues.getDataInJsonData(data, "cardoffcard");
                str_cnt_offcard = cardReadCount(GlobalMemberValues.getDataInJsonData(data, "cardoffcard"));

                str_salegiftcardtotal = GlobalMemberValues.getDataInJsonData(data, "salegiftcardtotal");
                str_salepointtotal = GlobalMemberValues.getDataInJsonData(data, "salepointtotal");

                str_cashouttotal = GlobalMemberValues.getDataInJsonData(data, "cashouttotal");
                str_overshorttxt = GlobalMemberValues.getDataInJsonData(data, "overshorttxt");
                str_overshortamt = GlobalMemberValues.getDataInJsonData(data, "overshortamt");
                str_salecardtiptotal = GlobalMemberValues.getDataInJsonData(data, "salecardtiptotal");
                str_endcashtotal = GlobalMemberValues.getDataInJsonData(data, "endcashtotal");

                str_pickupfeeamount = GlobalMemberValues.getDataInJsonData(data, "pickupfeeamount");
                str_deliveryfeeamount = GlobalMemberValues.getDataInJsonData(data, "deliveryfeeamount");

                GlobalMemberValues.logWrite("cashoutjsonlog", "str_totalsaleamount : " + str_totalsaleamount + "\n");

                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    textlist += "SALES TOTAL \t\t       ";
                    textlist += spaceAddString_total(str_totalsaleamount);
                    textlist +="\n";
                    //textlist += "COMMISSION \t\t       ";
                    //textlist += spaceAddString_total(str_commissionamount);
                    //textlist += "\n";
                    textlist += "DELIVERY/PICKUP \t       ";
                    textlist += spaceAddString_total(str_deliverypickupfeeamount);
                    textlist += "\n";
                    textlist += "TIP \t\t\t       ";
                    textlist += spaceAddString_total(str_tipamount);
                    textlist += "\n";
                    textlist += "PICK UP FEE\t\t\t";
                    textlist += spaceAddString_total(str_pickupfeeamount);
                    textlist += "\n";
                    textlist += "DELIVERY FEE\t\t\t";
                    textlist += spaceAddString_total(str_deliveryfeeamount);
                    textlist += "\n==========================================\r\n";

                    textlist += "FOOD \t\t\t       ";
                    textlist += spaceAddString_total(str_foodsaleamount);
                    textlist +="\n";
                    textlist += "PRODUCT \t\t       ";
                    textlist += spaceAddString_total(str_productsaleamount);
                    textlist += "\n";
                    textlist += "GIFT CARD \t\t       ";
                    textlist += spaceAddString_total(str_giftcardsaleamount);
                    textlist += "\n";
                    textlist += "DISCOUNT \t\t       ";
                    textlist += spaceAddString_total(str_discountamount);
                    textlist += "\n";
                    textlist += "VOID \t\t\t       ";
                    textlist += spaceAddString_total(str_voidamount);
                    textlist += "VOID TIP \t\t       ";
                    textlist += spaceAddString_total(str_tipvoidamount);
                    textlist += "\n==========================================\r\n";
//
                    textlist += "NET SALES \t\t       ";
                    textlist += spaceAddString_total(str_netsalesamount);
                    textlist += "REFUND \t\t\t       ";
                    textlist += spaceAddString_total(str_refundamount);
                    textlist += "TAX \t\t\t       ";
                    textlist += spaceAddString_total(str_taxamount);
                    textlist += "GROSS SALES \t\t       ";
                    textlist += spaceAddString_total(str_grosssalesamount);
                    textlist += "\n==========================================\r\n";

                    textlist += "SALES CASH \t\t       ";
                    textlist += spaceAddString_total(str_salescashtotal);
                    textlist += "CASH IN TOTAL \t\t       ";
                    textlist += spaceAddString_total(str_cashintotal);
                    textlist += "CASH OUT TOTAL \t\t       ";
                    textlist += spaceAddString_total(str_cashouttotal);
                    textlist += "ALL CASH TOTAL \t\t       ";
                    textlist += spaceAddString_total(str_allcashtotal);
                    textlist += "END CASH       \t\t       ";
                    textlist += spaceAddString_total(str_endcashtotal);
                    textlist += str_overshorttxt.toUpperCase() + "      \t\t       ";
                    textlist += spaceAddString_total(str_overshortamt);
                    textlist += "\n==========================================\r\n";

                    textlist += "CREDIT CARD \t\t       ";
                    textlist += spaceAddString_total(str_salecardtotal);
                    textlist += "CREDIT CARD TIP \t\t   ";
                    textlist += spaceAddString_total(str_salecardtiptotal);
                    textlist += "         VISA            ";
                    textlist += str_cnt_visa + "     ";
                    textlist += spaceAddString_total(cardAmountString(str_cardvisa)) + "\n";
                    textlist += "       MASTER            ";
                    textlist += str_cnt_master + "     ";
                    textlist += spaceAddString_total(cardAmountString(str_cardmaster)) + "\n";
                    textlist += "         AMEX            ";
                    textlist += str_cnt_amex + "     ";
                    textlist += spaceAddString_total(cardAmountString(str_cardamex)) +"\n";
                    textlist += "     DISCOVER            ";
                    textlist += str_cnt_discover + "     ";
                    textlist += spaceAddString_total(cardAmountString(str_carddiscover)) +"\n";
                    textlist += "     OFF CARD            ";
                    textlist += str_cnt_offcard + "     ";
                    textlist += spaceAddString_total(cardAmountString(str_cardoffcard)) + "\n";

                    textlist += "CHECK \t\t\t       ";
                    textlist += spaceAddString_total(str_salechecktotal);
                    textlist += "GIFT CARD REDEEMED ";
                    textlist += spaceAddString_total(str_salegiftcardtotal);
                    textlist += "POINT \t\t\t       ";
                    textlist += spaceAddString_total(str_salepointtotal);
                    textlist += "\n==========================================\r\n";
                }

                textlist += "Printed \t      ";
                textlist += nowTime + "  " + nowDate;

//                sendCommand(list, context);
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, size, false);
                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);
                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }

        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public void printBatchDetailPosbank(final JSONObject data) {

        if (PosbankPrinter.mPrinter == null){
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {


                int alignment = Printer.ALIGNMENT_LEFT;
//        Printer.ALIGNMENT_LEFT
//        Printer.ALIGNMENT_CENTER
//        Printer.ALIGNMENT_RIGHT
                int size = 0;
                int attribute = 0;

                String textlist = "";

                String str_batchnumber = "";
                String str_totalcardcount = "";
                String str_totalcardtip = "";
                String str_totalcardamount = "";

                String str_visaamount = "";
                String str_masteramount = "";
                String str_amexamount = "";
                String str_discoveramount = "";

                String str_batchdate =  "";


                textlist +=  ">>> BATCH SUMMARY <<<\r\n\r\n";
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, 16|1, false);

                String nowDate = getDate();
                String nowTime = getTime();

                textlist = "";
                try {
                    if (data.toString().contains("BatchNumber")){
                        str_batchnumber = data.getString("BatchNumber");
                    }

                    if (data.toString().contains("CreditCount")){
                        str_totalcardcount = data.getString("CreditCount");
                    }

                    if (data.toString().contains("totalcardtip")){
                        str_totalcardtip = data.getString("totalcardtip");
                    }

                    if (data.toString().contains("CreditAmount")){
                        str_totalcardamount = data.getString("CreditAmount");
                    }

                    if (data.toString().contains("visaamount")){
                        str_visaamount = data.getString("visaamount");
                    }

                    if (data.toString().contains("masteramount")){
                        str_masteramount = data.getString("masteramount");
                    }

                    if (data.toString().contains("amexamount")){
                        str_amexamount = data.getString("amexamount");
                    }

                    if (data.toString().contains("discoveramount")){
                        str_discoveramount = data.getString("discoveramount");
                    }

                    if (data.toString().contains("BatchDate")){
                        str_batchdate = data.getString("BatchDate");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                textlist += "BATCH # : \t ";
                textlist += spaceAddString_total(GlobalMemberValues.getCommaStringForInteger(str_batchnumber));
                textlist +="\n";
                textlist += "TOTAL CARD COUNT \t\t        ";
                textlist += spaceAddString_total(str_totalcardcount);
                textlist += "\n";
                textlist += "TOTAL CARD AMT.\t\t       ";
                textlist += spaceAddString_total(str_totalcardamount);
                textlist += "\n";
                textlist += "TOTAL CARD TIP \t\t       ";
                textlist += spaceAddString_total(str_totalcardtip);
                textlist += "\n============== TYPE OF CARD ==============\r\n";//
                if (!GlobalMemberValues.isStrEmpty(str_visaamount)) {
                    textlist += "     VISA \t\t       ";
                    textlist += spaceAddString_total(str_visaamount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_masteramount)) {
                    textlist += "\n";
                    textlist += "     MASTER \t\t       ";
                    textlist += spaceAddString_total(str_masteramount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_amexamount)) {
                    textlist += "\n";
                    textlist += "     AMEX \t\t       ";
                    textlist += spaceAddString_total(str_amexamount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_discoveramount)) {
                    textlist += "\n";
                    textlist += "     DISCOVER \t\t       ";
                    textlist += spaceAddString_total(str_discoveramount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_totalcardtip)) {
                    textlist += "\n";
                    textlist += "     TIP \t\t       ";
                    textlist += spaceAddString_total(str_totalcardtip);
                }
                textlist += "\n==========================================\r\n";
                textlist += "Batched Time\t       ";
                textlist += str_batchdate;
                textlist += "\nPrinted Time\t       ";
                textlist += nowDate + " " + nowTime;

//                sendCommand(list, context);
                GlobalMemberValues.posbankPrintText(textlist, alignment, attribute, size, false);
                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);
                //PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }

        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public void printSalesReceiptPosbank(final JSONObject data) {
        GlobalMemberValues.logWrite("receiptjsonlog", "json data : " + data.toString() + "\n");

        if (PosbankPrinter.mPrinter == null){
            return;
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final double[] mTotalTipAmount = {0};

        Thread thread1 = new Thread() {
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;
                String main_Text = "";

                String titleText1 = "";
                String titleText2 = "";

                String customerText = "";
                String merchantText = "";

                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemdcextrastr = "";
                String str_itemtaxexempt = "";
                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardsalonsalescardidx = "";
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_checktendered = "";
                String str_pointtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_alldiscountextrstr = "";
                String str_totalextradiscountprice="";

                String str_receiptprinttype = "";

                String str_cardsalesignatureimageyn = "N";

                String str_signatureTxt = "";

                String str_reprintyn = "N";

                String str_tipamount = "";

                String str_cardtipamount = "0";

                String str_deliverytakeaway = "";
                String str_deliverypickupfee = "";

                String str_receiptfooter = "";

                String str_customerOrderNumber = "";
                String str_customerPagerNumber = "";

                String str_checkcompany = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_change = data.getString("change");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_checktendered = data.getString("checktendered");
                    str_pointtendered = data.getString("pointtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");

                    str_alldiscountextrstr = data.getString("alldiscountextrstr");

                    str_totalextradiscountprice = data.getString("totalextradiscountprice");

                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction"))
                    {
                        jsonArray_creditcardtransaction = data.getJSONArray("creditcardtransaction");
                    }
                    if (data.toString().contains("reprintyn")) {
                        str_reprintyn = data.getString("reprintyn");
                    } else {
                        str_reprintyn = "N";
                    }

                    if (data.toString().contains("tipamount")) {
                        str_tipamount = data.getString("tipamount");
                    } else {
                        str_tipamount = "";
                    }

                    if (data.toString().contains("customerphonenum")) {
                        str_customerphonenum = data.getString("customerphonenum");
                    } else {
                        str_customerphonenum = "";
                    }

                    if (data.toString().contains("customeraddress")) {
                        str_customeraddress = data.getString("customeraddress");
                    } else {
                        str_customeraddress = "";
                    }

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = data.getString("customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                if (jsonArray_creditcardtransaction.length() != 0) {
                    titleText1 =  "*** " + str_storename + " ***\n";
                    titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip + "\n(TEL) "+ telNumberExch(str_storephone) + "\n";

                    String[] tempEmpNames = str_employeename.split(",");
                    String tempEmpStr = "";
                    GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                    if (tempEmpNames.length > 1) {
                        tempEmpStr = "\n[Employee Name]\n";
                    } else {
                        tempEmpStr = "\nEmployee Name : ";
                    }

                    titleText2 = tempEmpStr + str_employeename + "\nCustomer Name    : " + str_customername + "\nReceipt No. : "
                            + str_receiptno + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";
                    //titleText2 = tempEmpStr + str_employeename + "\nCustomer Name    : " + str_customername + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";

                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {

                        // 프린트 실행 (1) ------------------------------------------------------------
                        GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                        GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                        // ---------------------------------------------------------------------------

                        String strFilePath = "";        // 서명 이미지 경로

                        customerText = "";
                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);
                            str_cardchangeamount = dic_card_item.getString("chargeamount");
                            str_cardtype = dic_card_item.getString("cardtype");

                            str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            str_cardauthnumber = dic_card_item.getString("cardauthnumber");

                            if (dic_card_item.toString().contains("cardsalesignatureimageyn")) {
                                str_cardsalesignatureimageyn = dic_card_item.getString("cardsalesignatureimageyn");
                            } else {
                                str_cardsalesignatureimageyn = "N";
                            }

                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            } else {
                                str_cardaid = "";
                            }

                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            } else {
                                str_cardtvr = "";
                            }

                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            } else {
                                str_cardtsi = "";
                            }

                            if (dic_card_item.toString().contains("cardsalonsalescardidx")){
                                str_cardsalonsalescardidx = dic_card_item.getString("cardsalonsalescardidx");
                            } else {
                                str_cardsalonsalescardidx = "";
                            }

                            if (dic_card_item.toString().contains("cardtipamount")){
                                str_cardtipamount = dic_card_item.getString("cardtipamount");
                            } else {
                                str_cardtipamount = "0";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        merchantText += "******** Credit Card Transaction *********\r\n";
                        customerText += "******** Credit Card Transaction *********\r\n";

                        merchantText += "Card Type \t : ";
                        customerText += "Card Type \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";

                        merchantText +="Credit Card# \t : ";
                        customerText +="Credit Card# \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";

                        merchantText +="Charge Amount \t : ";
                        customerText +="Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardchangeamount) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardchangeamount) + "\n";

                        merchantText +="Card Auth# \t : ";
                        customerText +="Card Auth# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";

                        merchantText +="Card Ref# \t : ";
                        customerText +="Card Ref# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";

                        if (str_cardaid.length() != 0){
                            merchantText += "AID \t : ";
                            customerText += "AID \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                        }
                        if (str_cardtvr.length() != 0){
                            merchantText += "TSI \t : ";
                            customerText += "TSI \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";

                        }
                        if (str_cardtsi.length() != 0){
                            merchantText += "TVR \t : ";
                            customerText += "TVR \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";

                        }
                        merchantText += "------------------------------------------\r\n";
                        customerText += "------------------------------------------\r\n";

                        str_receiptprinttype = "3";

                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            //merchant copy
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : ___________________  \n";
                                merchantText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ______________________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            //customer copy
                            //merchant copy
                            if (GlobalMemberValues.getDoubleAtString(str_cardtipamount) > 0) {
                                double tempTotalAmount =
                                        GlobalMemberValues.getDoubleAtString(str_cardchangeamount) + GlobalMemberValues.getDoubleAtString(str_cardtipamount);
                                customerText += "\t         Tip : " + GlobalMemberValues.getCommaStringForDouble(str_cardtipamount + "") + "  \n";
                                customerText += " Grand Total Amount  : " + GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + "") + "\r\n\r";

                                mTotalTipAmount[0] = mTotalTipAmount[0] + GlobalMemberValues.getDoubleAtString(str_cardtipamount);
                            } else {
                                if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                    customerText += "\t         Tip : ___________________  \n";
                                    customerText += " Grand Total Amount  : ___________________\r\n\r";
                                }
                            }

                            // 프린트 실행 (2) --------------------------------------------------------------------
                            GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                            // -----------------------------------------------------------------------------------
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                str_signatureTxt = "\n\r\n";
                                str_signatureTxt += " Signature ______________________________\r\n\r\n";
                            }

                            // 서명 이미지 프린트 -------------------------------------------------------------------------------------------------
                            strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) + "/sign_0_0000_0.png";
                            // 서명 이미지가 있을 경우, 서명이미지 프린트는 리프린트가 아닐 경우에만...
                            if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                if (str_cardsalesignatureimageyn == "Y" || str_cardsalesignatureimageyn.equals("Y")) {
                                    if (!GlobalMemberValues.isStrEmpty(str_cardsalonsalescardidx) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardauthnumber) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardrefnumber)) {
                                        strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) +
                                                "/sign_" + str_cardsalonsalescardidx + "_" + str_cardauthnumber + "_" + str_cardrefnumber + ".png";
                                    }
                                }
                            }

                            GlobalMemberValues.logWrite("signatureimagepath", "strFilePath : " + strFilePath + "\n");

                            if (new File(strFilePath).exists()) {
                                str_signatureTxt = "";

                                if (GlobalMemberValues.isSignedPrintYN()) {
                                    //PosbankPrinter.mPrinter.printBitmap(BitmapFactory.decodeFile(strFilePath), 1, 300, 77, false, true, false);
                                    PosbankPrinter.mPrinter.printBitmap(strFilePath, Printer.ALIGNMENT_CENTER, 300, 13, false, true, false);
                                    //str_signatureTxt = "\r\n";
                                }
                            } else {
                                // 프린트 실행 (2) --------------------------------------------------------------------
                                str_signatureTxt = "\n\r        ";
                                str_signatureTxt += "         \r\n";
                                str_signatureTxt += "         \r\n";
                                str_signatureTxt += "         \r\n";
                                //GlobalMemberValues.posbankPrintText(str_signatureTxt, alignment, attribute, size, false);
                                // -----------------------------------------------------------------------------------
                            }
                            // -----------------------------------------------------------------------------------------------------------------

                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                str_signatureTxt += " Signature ______________________________\r\n\r\n";
                            }

                            // 프린트 실행 (3) --------------------------------------------------------------------
                            GlobalMemberValues.posbankPrintText(str_signatureTxt, alignment, attribute, size, false);
                            // -----------------------------------------------------------------------------------
                        }

                        // receipt footer 프린터 ----------------------------------------------------------------------------------
                        if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                            GlobalMemberValues.posbankPrintText(str_receiptfooter, 1, 0, 0, false);
                        }
                        // --------------------------------------------------------------------------------------------------------

                        customerText = "############## Merchant Copy #############\r\n";
                        customerText += "     ** Thank you. Come again soon. **\r\n";
                        customerText += "             ";
                        customerText += getDate() + " " + getTime();


                        // merchant 프린트 -----------------------------------------------------------------------
                        GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                        GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                        PosbankPrinter.mPrinter.cutPaper(true);
                        // --------------------------------------------------------------------------------------
                    }
                }

                // 프린터 연결을 끊는다.
                //GlobalMemberValues.disconnectPrinter();
            }
        };

        final ArrayList<String> tempArrListStrFile = new ArrayList<String>();
        Thread thread2 = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;
                String main_Text = "";

                String titleText1 = "";
                String titleText2 = "";

                String customerText = "";
                String merchantText = "";
                String merchantText2 = "";

                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemdcextrastr = "";
                String str_itemtaxexempt = "";
                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardsalonsalescardidx = "";
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_checktendered = "";
                String str_pointtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_alldiscountextrstr = "";
                String str_totalextradiscountprice="";

                String str_receiptprinttype = "";

                String str_cardsalesignatureimageyn = "N";

                String str_signatureTxt = "";

                String str_reprintyn = "N";

                String str_tipamount = "";

                String str_cardtipamount = "0";

                String str_optiontxt = "";
                String str_optionprice = "0.0";
                String str_additionaltxt1 = "";
                String str_additionalprice1 = "0.0";
                String str_additionaltxt2 = "";
                String str_additionalprice2 = "0.0";
                String str_kitchenmemo = "";

                String str_deliverytakeaway = "";
                String str_deliverypickupfee = "";

                String str_receiptfooter = "";

                String str_customerOrderNumber = "";
                String str_customerPagerNumber = "";

                String str_checkcompany = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_change = data.getString("change");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_checktendered = data.getString("checktendered");
                    str_pointtendered = data.getString("pointtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");

                    str_alldiscountextrstr = data.getString("alldiscountextrstr");

                    str_totalextradiscountprice = data.getString("totalextradiscountprice");

                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction"))
                    {
                        jsonArray_creditcardtransaction = data.getJSONArray("creditcardtransaction");
                    }
                    if (data.toString().contains("reprintyn")) {
                        str_reprintyn = data.getString("reprintyn");
                    } else {
                        str_reprintyn = "N";
                    }

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }
                    if (data.toString().contains("deliverypickupfee")) {
                        str_deliverypickupfee = data.getString("deliverypickupfee");
                    } else {
                        str_deliverypickupfee = "";
                    }

                    if (data.toString().contains("receiptfooter")) {
                        str_receiptfooter = data.getString("receiptfooter");
                    } else {
                        str_receiptfooter = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = data.getString("customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
                    }

                    if (data.toString().contains("checkcompany")) {
                        str_checkcompany = data.getString("checkcompany");
                    } else {
                        str_checkcompany = "";
                    }

                    if (data.toString().contains("tipamount")) {
                        str_tipamount = data.getString("tipamount");
                    } else {
                        str_tipamount = "";
                    }

                    if (data.toString().contains("customerphonenum")) {
                        str_customerphonenum = data.getString("customerphonenum");
                    } else {
                        str_customerphonenum = "";
                    }

                    if (data.toString().contains("customeraddress")) {
                        str_customeraddress = data.getString("customeraddress");
                    } else {
                        str_customeraddress = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                titleText1 =  "*** " + str_storename + " ***\n";
                titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip + "\n(TEL) "+ telNumberExch(str_storephone) + "\n";
                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "------------------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\nOrder No. : " + str_customerOrderNumber;

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        String tempDeliveryTakeaway = "HERE";
                        if (!GlobalMemberValues.isStrEmpty(str_deliverytakeaway)) {
                            switch (str_deliverytakeaway) {
                                case "H" : {
                                    tempDeliveryTakeaway = "HERE";
                                    break;
                                }
                                case "T" : {
                                    tempDeliveryTakeaway = "PICK UP";
                                    break;
                                }
                                case "D" : {
                                    tempDeliveryTakeaway = "DELIVERY";
                                    break;
                                }
                            }
                        }
                        titleText1 += "\nPick Up Type : " + tempDeliveryTakeaway + "\n";

                        if (!GlobalMemberValues.isStrEmpty(str_customername)) {
                            titleText1 += "Customer Name : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            titleText1 += "<Address>\n" + str_customeraddress + "\n";
                        }
                    }

                    titleText1 += "------------------------------------------\r";
                }
                // -----------------------------------------------------------------------------------

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt No. : "
                        + str_receiptno + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "------------------------------------------\r\n";
                        //"Item Name        Qty     Price      Amount\r\n" +
                        //"------------------------------------------\r\n";

                if (jsonArray_saleitemlist.length() != 0)
                {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++)
                    {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemdcextrastr = dic_item.getString("itemdcextrastr");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");

                            str_optiontxt = dic_item.getString("optiontxt");
                            str_optionprice = dic_item.getString("optionprice");
                            str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            str_additionalprice1 = dic_item.getString("additionalprice1");
                            str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            str_additionalprice2 = dic_item.getString("additionalprice2");

                            if (dic_item.toString().contains("kitchenmemo")) {
                                str_kitchenmemo = dic_item.getString("kitchenmemo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }

                        /**
                        main_Text += Payment_stringFowardSpace_Exch(16, str_itemname) +
                                Payment_stringFowardSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                                Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";
                         **/

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                            double tempItemAmount = GlobalMemberValues.getDoubleAtString(str_itemamount);
                            if (GlobalMemberValues.MODIFIER_PRICEVIEW) {
                                double tempOptionPriceSum = GlobalMemberValues.getDoubleAtString(str_optionprice)
                                        + GlobalMemberValues.getDoubleAtString(str_additionalprice1) + GlobalMemberValues.getDoubleAtString(str_additionalprice2);
                                tempItemAmount -= tempOptionPriceSum;

                                str_itemamount = tempItemAmount + "";
                            }
                        }

                        main_Text += Payment_stringBackSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(27, str_itemname) +
                                Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";

                        if (GlobalMemberValues.isModifierPrintYN()) {
                            if (!GlobalMemberValues.isStrEmpty(str_optiontxt) ||
                                    !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                String str_option_additional_txt = "";
                                if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                                    str_option_additional_txt = "" + GlobalMemberValues.getModifierTxt(str_itemqty, str_optiontxt);
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt1)) {
                                    str_option_additional_txt += "[Add Ingredients]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt1) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                    str_option_additional_txt += "[Add Ingredients2]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt2) + "\n";
                                }
                                main_Text += "" + str_option_additional_txt;
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "   [Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      "+str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "------------------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "------------------------------------------\r\n" +
                        "Sub Total \t\t      ";
                main_Text +=  Payment_stringFowardSpace_Exch(12,str_subtotal) + "\n";

                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t      ";
                        } else {
                            main_Text += "Extra Total 	\t\t      ";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,str_totalextradiscountprice) + "\n";
                    }
                }
                main_Text +=  "Tax \t\t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_tax) + "\n";
                if (!GlobalMemberValues.isStrEmpty(str_deliverypickupfee) && GlobalMemberValues.getDoubleAtString(str_deliverypickupfee) > 0) {
                    if (str_deliverytakeaway.equals("D")) {
                        main_Text +=  "Delivery Fee \t\t      ";
                    } else {
                        main_Text +=  "Pick Up Fee \t\t      ";
                    }
                    main_Text += Payment_stringFowardSpace_Exch(12,str_deliverypickupfee) + "\n";
                }
                main_Text +=  "Total Amount \t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_totalamount) + "\n";
                main_Text += "------------------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Credit Card Tendered \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_creditcardtendered) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_giftcardtendered) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_checktendered) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_pointtendered) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_cashtendered) + "\n";
                }

                main_Text += "Change \t\t              ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_change) + "\n";
                main_Text += "------------------------------------------\r\n";
                if (str_reprintyn == "Y" || str_reprintyn.equals("Y")) {
                    if (!GlobalMemberValues.isStrEmpty(str_tipamount) && GlobalMemberValues.getDoubleAtString(str_tipamount) > 0) {
                        main_Text += "Tip \t\t              ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_tipamount) + "\n";
                        main_Text += "------------------------------------------\r\n";
                    }
                } else {
                    if (mTotalTipAmount[0] > 0) {
                        main_Text += "Tip \t\t              ";
                        main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(mTotalTipAmount[0] + "")) + "\n";
                        main_Text += "------------------------------------------\r\n";

                    }
                }

                // 프린트 실행 (1) ------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.posbankPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n\n", 1, attribute, 2, false);
                }
                GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                // ---------------------------------------------------------------------------


//              // card
                if (jsonArray_creditcardtransaction.length() != 0)
                {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        String strFilePath = "";        // 서명 이미지 경로

                        customerText = "";

                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);
                            str_cardchangeamount = dic_card_item.getString("chargeamount");
                            str_cardtype = dic_card_item.getString("cardtype");

                            str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            str_cardauthnumber = dic_card_item.getString("cardauthnumber");

                            if (dic_card_item.toString().contains("cardsalesignatureimageyn")) {
                                str_cardsalesignatureimageyn = dic_card_item.getString("cardsalesignatureimageyn");
                            } else {
                                str_cardsalesignatureimageyn = "N";
                            }

                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            } else {
                                str_cardaid = "";
                            }

                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            } else {
                                str_cardtvr = "";
                            }

                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            } else {
                                str_cardtsi = "";
                            }

                            if (dic_card_item.toString().contains("cardsalonsalescardidx")){
                                str_cardsalonsalescardidx = dic_card_item.getString("cardsalonsalescardidx");
                            } else {
                                str_cardsalonsalescardidx = "";
                            }

                            if (dic_card_item.toString().contains("cardtipamount")){
                                str_cardtipamount = dic_card_item.getString("cardtipamount");
                            } else {
                                str_cardtipamount = "0";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        customerText += "******** Credit Card Transaction *********\r\n";

                        customerText += "Card Type \t : ";

                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";

                        customerText +="Credit Card# \t : ";

                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";

                        customerText +="Charge Amount \t : ";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardchangeamount) + "\n";

                        customerText +="Card Auth# \t : ";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";

                        customerText +="Card Ref# \t : ";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";

                        if (str_cardaid.length() != 0){
                            customerText += "AID \t : ";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                        }
                        if (str_cardtvr.length() != 0){
                            customerText += "TSI \t : ";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";

                        }
                        if (str_cardtsi.length() != 0){
                            customerText += "TVR \t : ";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";
                        }
                        customerText += "------------------------------------------\r\n";

                        // 프린트 실행 (2) --------------------------------------------------------------------
                        GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                        // -----------------------------------------------------------------------------------

                        str_receiptprinttype = "3";

                        if (GlobalMemberValues.isSignedPrintSeperately()) {
                            // 서명 이미지 프린트 -------------------------------------------------------------------------------------------------
                            strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) + "/sign_0_0000_0.png";
                            // 서명 이미지가 있을 경우, 서명이미지 프린트는 리프린트가 아닐 경우에만...
                            if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                if (str_cardsalesignatureimageyn == "Y" || str_cardsalesignatureimageyn.equals("Y")) {
                                    if (!GlobalMemberValues.isStrEmpty(str_cardsalonsalescardidx) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardauthnumber) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardrefnumber)) {
                                        strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) +
                                                "/sign_" + str_cardsalonsalescardidx + "_" + str_cardauthnumber + "_" + str_cardrefnumber + ".png";
                                    }
                                }
                            }
                            GlobalMemberValues.logWrite("signatureimagepath", "strFilePath : " + strFilePath + "\n");
                            if (new File(strFilePath).exists()) {
                                tempArrListStrFile.add(strFilePath);
                            }
                        } else {
                            String cardsignedtxt =  "";

                            //customer copy
                            //merchant copy
                            if (GlobalMemberValues.getDoubleAtString(str_cardtipamount) > 0) {
                                double tempTotalAmount =
                                        GlobalMemberValues.getDoubleAtString(str_cardchangeamount) + GlobalMemberValues.getDoubleAtString(str_cardtipamount);
                                cardsignedtxt += "\t         Tip : " + GlobalMemberValues.getCommaStringForDouble(str_cardtipamount + "") + "  \n";
                                cardsignedtxt += " Grand Total Amount  : " + GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + "") + "\r\n\r";

                                mTotalTipAmount[0] = mTotalTipAmount[0] + GlobalMemberValues.getDoubleAtString(str_cardtipamount);
                            } else {
                                if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                    cardsignedtxt += "\t         Tip : ___________________  \n";
                                    cardsignedtxt += " Grand Total Amount  : ___________________\r\n\r";
                                }
                            }

                            // 프린트 실행 (2) --------------------------------------------------------------------
                            GlobalMemberValues.posbankPrintText(cardsignedtxt, alignment, attribute, size, false);
                            // -----------------------------------------------------------------------------------

                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                str_signatureTxt = "\n\r\n";
                                str_signatureTxt += " Signature ______________________________\r\n\r\n";
                            }

                            // 서명 이미지 프린트 -------------------------------------------------------------------------------------------------
                            strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) + "/sign_0_0000_0.png";
                            // 서명 이미지가 있을 경우, 서명이미지 프린트는 리프린트가 아닐 경우에만...
                            if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                if (str_cardsalesignatureimageyn == "Y" || str_cardsalesignatureimageyn.equals("Y")) {
                                    if (!GlobalMemberValues.isStrEmpty(str_cardsalonsalescardidx) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardauthnumber) &&
                                            !GlobalMemberValues.isStrEmpty(str_cardrefnumber)) {
                                        strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) +
                                                "/sign_" + str_cardsalonsalescardidx + "_" + str_cardauthnumber + "_" + str_cardrefnumber + ".png";
                                    }
                                }
                            }

                            GlobalMemberValues.logWrite("signatureimagepath", "strFilePath : " + strFilePath + "\n");

                            if (new File(strFilePath).exists()) {
                                str_signatureTxt = "";

                                if (GlobalMemberValues.isSignedPrintYN()) {
                                    //PosbankPrinter.mPrinter.printBitmap(BitmapFactory.decodeFile(strFilePath), 1, 300, 77, false, true, false);
                                    PosbankPrinter.mPrinter.printBitmap(strFilePath, Printer.ALIGNMENT_CENTER, 300, 13, false, true, false);
                                    //str_signatureTxt = "\r\n";
                                }
                            } else {
                                // 프린트 실행 (2) --------------------------------------------------------------------
                                str_signatureTxt = "\n\r        ";
                                str_signatureTxt += "         \r\n";
                                str_signatureTxt += "         \r\n";
                                str_signatureTxt += "         \r\n";
                                //GlobalMemberValues.posbankPrintText(str_signatureTxt, alignment, attribute, size, false);
                                // -----------------------------------------------------------------------------------
                            }
                            // -----------------------------------------------------------------------------------------------------------------

                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                str_signatureTxt += " Signature ______________________________\r\n\r\n";
                            }

                            // 프린트 실행 (3) --------------------------------------------------------------------
                            GlobalMemberValues.posbankPrintText(str_signatureTxt, alignment, attribute, size, false);
                            // -----------------------------------------------------------------------------------
                        }

                    }
                }

                customerText = "############## Merchant Copy #############\r\n";
                customerText += "     ** Thank you. Come again soon. **\r\n";
                customerText += "             ";
                customerText += getDate() + " " + getTime();

                // customer 프린트 -----------------------------------------------------------------------
                // receipt footer 프린터 ----------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                    GlobalMemberValues.posbankPrintText(str_receiptfooter, 1, 0, 0, false);
                }
                // --------------------------------------------------------------------------------------------------------

                GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);
                // --------------------------------------------------------------------------------------

                if (GlobalMemberValues.isCustomerSelectReceipt()) {
                    // 프린터 연결을 끊는다.
                    GlobalMemberValues.disconnectPrinter();
                }

                GlobalMemberValues.logWrite("ONLYMERCHANTPRINT", "ONLY_MERCHANTRECEIPT_PRINT : " + GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT + "\n");
            }
        };

        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant")) {
            if (GlobalMemberValues.isSignedPrintSeperately()) {
                thread1.start();
                try {
                    thread1.join();
                } catch(InterruptedException e) {}
            }
            thread2.start();
            try {
                thread2.join();
            } catch(InterruptedException e) {}

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 프린트 완료되면 저장된 전자서명이미지 삭제한다.
            if (tempArrListStrFile.size() > 0) {
                for (String tempStrFilePath : tempArrListStrFile) {
                    if (!GlobalMemberValues.isStrEmpty(tempStrFilePath) && new File(tempStrFilePath).exists()) {
                        new File(tempStrFilePath).delete();
                    }
                }
            }
        }

        // Customer 용 프린트 실행
        printSalesReceiptPosbank2(data);
    }

    public void printSalesReceiptPosbank2(final JSONObject data) {
        if (PosbankPrinter.mPrinter == null){
            return;
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(300); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final double[] mTotalTipAmount = {0};

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;
                String main_Text = "";

                String titleText1 = "";
                String titleText2 = "";

                String customerText = "";
                String merchantText = "";
                String merchantText2 = "";

                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemdcextrastr = "";
                String str_itemtaxexempt = "";
                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardsalonsalescardidx = "";
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_checktendered = "";
                String str_pointtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_alldiscountextrstr = "";
                String str_totalextradiscountprice="";

                String str_receiptprinttype = "";

                String str_cardsalesignatureimageyn = "N";

                String str_signatureTxt = "";

                String str_reprintyn = "N";

                String str_tipamount = "";

                String str_cardtipamount = "0";

                String str_optiontxt = "";
                String str_optionprice = "0.0";
                String str_additionaltxt1 = "";
                String str_additionalprice1 = "0.0";
                String str_additionaltxt2 = "";
                String str_additionalprice2 = "0.0";
                String str_kitchenmemo = "";

                String str_deliverytakeaway = "";
                String str_deliverypickupfee = "";

                String str_receiptfooter = "";

                String str_customerOrderNumber = "";
                String str_customerPagerNumber = "";

                String str_checkcompany = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_change = data.getString("change");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_checktendered = data.getString("checktendered");
                    str_pointtendered = data.getString("pointtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");

                    str_alldiscountextrstr = data.getString("alldiscountextrstr");

                    str_totalextradiscountprice = data.getString("totalextradiscountprice");

                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction"))
                    {
                        jsonArray_creditcardtransaction = data.getJSONArray("creditcardtransaction");
                    }
                    if (data.toString().contains("reprintyn")) {
                        str_reprintyn = data.getString("reprintyn");
                    } else {
                        str_reprintyn = "N";
                    }

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }
                    if (data.toString().contains("deliverypickupfee")) {
                        str_deliverypickupfee = data.getString("deliverypickupfee");
                    } else {
                        str_deliverypickupfee = "";
                    }

                    if (data.toString().contains("receiptfooter")) {
                        str_receiptfooter = data.getString("receiptfooter");
                    } else {
                        str_receiptfooter = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = data.getString("customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
                    }

                    if (data.toString().contains("checkcompany")) {
                        str_checkcompany = data.getString("checkcompany");
                    } else {
                        str_checkcompany = "";
                    }

                    if (data.toString().contains("tipamount")) {
                        str_tipamount = data.getString("tipamount");
                    } else {
                        str_tipamount = "";
                    }

                    if (data.toString().contains("customerphonenum")) {
                        str_customerphonenum = data.getString("customerphonenum");
                    } else {
                        str_customerphonenum = "";
                    }

                    if (data.toString().contains("customeraddress")) {
                        str_customeraddress = data.getString("customeraddress");
                    } else {
                        str_customeraddress = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                titleText1 =  "*** " + str_storename + " ***\n";
                titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip + "\n(TEL) "+ telNumberExch(str_storephone) + "\n";
                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "------------------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\nOrder No. : " + str_customerOrderNumber;

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        String tempDeliveryTakeaway = "HERE";
                        if (!GlobalMemberValues.isStrEmpty(str_deliverytakeaway)) {
                            switch (str_deliverytakeaway) {
                                case "H" : {
                                    tempDeliveryTakeaway = "HERE";
                                    break;
                                }
                                case "T" : {
                                    tempDeliveryTakeaway = "PICK UP";
                                    break;
                                }
                                case "D" : {
                                    tempDeliveryTakeaway = "DELIVERY";
                                    break;
                                }
                            }
                        }
                        titleText1 += "\nPick Up Type : " + tempDeliveryTakeaway + "\n";

                        if (!GlobalMemberValues.isStrEmpty(str_customername)) {
                            titleText1 += "Customer Name : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            titleText1 += "<Address>\n" + str_customeraddress + "\n";
                        }
                    }

                    titleText1 += "------------------------------------------\r";
                }
                // -----------------------------------------------------------------------------------

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt No. : "
                        + str_receiptno + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "------------------------------------------\r\n";
                //"Item Name        Qty     Price      Amount\r\n" +
                //"------------------------------------------\r\n";

                if (jsonArray_saleitemlist.length() != 0)
                {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++)
                    {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemdcextrastr = dic_item.getString("itemdcextrastr");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");

                            str_optiontxt = dic_item.getString("optiontxt");
                            str_optionprice = dic_item.getString("optionprice");
                            str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            str_additionalprice1 = dic_item.getString("additionalprice1");
                            str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            str_additionalprice2 = dic_item.getString("additionalprice2");

                            if (dic_item.toString().contains("kitchenmemo")) {
                                str_kitchenmemo = dic_item.getString("kitchenmemo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }
                        /**
                         main_Text += Payment_stringFowardSpace_Exch(16, str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";
                         **/

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                            double tempItemAmount = GlobalMemberValues.getDoubleAtString(str_itemamount);
                            if (GlobalMemberValues.MODIFIER_PRICEVIEW) {
                                double tempOptionPriceSum = GlobalMemberValues.getDoubleAtString(str_optionprice)
                                        + GlobalMemberValues.getDoubleAtString(str_additionalprice1) + GlobalMemberValues.getDoubleAtString(str_additionalprice2);
                                tempItemAmount -= tempOptionPriceSum;

                                str_itemamount = tempItemAmount + "";
                            }
                        }

                        main_Text += Payment_stringBackSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(27, str_itemname) +
                                Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";

                        if (GlobalMemberValues.isModifierPrintYN()) {
                            if (!GlobalMemberValues.isStrEmpty(str_optiontxt) ||
                                    !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                String str_option_additional_txt = "";
                                if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                                    str_option_additional_txt = "" + GlobalMemberValues.getModifierTxt(str_itemqty, str_optiontxt);
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt1)) {
                                    str_option_additional_txt += "[Add Ingredients]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt1) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                    str_option_additional_txt += "[Add Ingredients2]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt2) + "\n";
                                }
                                main_Text += "" + str_option_additional_txt;
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "   [Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      "+str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "------------------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "------------------------------------------\r\n" +
                        "Sub Total \t\t      ";
                main_Text +=  Payment_stringFowardSpace_Exch(12,str_subtotal) + "\n";

                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t      ";
                        } else {
                            main_Text += "Extra Total 	\t\t      ";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,str_totalextradiscountprice) + "\n";
                    }
                }
                main_Text +=  "Tax \t\t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_tax) + "\n";
                if (!GlobalMemberValues.isStrEmpty(str_deliverypickupfee) && GlobalMemberValues.getDoubleAtString(str_deliverypickupfee) > 0) {
                    if (str_deliverytakeaway.equals("D")) {
                        main_Text +=  "Delivery Fee \t\t      ";
                    } else {
                        main_Text +=  "Pick Up Fee \t\t      ";
                    }
                    main_Text += Payment_stringFowardSpace_Exch(12,str_deliverypickupfee) + "\n";
                }
                main_Text +=  "Total Amount \t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_totalamount) + "\n";
                main_Text += "------------------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Credit Card Tendered \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_creditcardtendered) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_giftcardtendered) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_checktendered) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_pointtendered) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_cashtendered) + "\n";
                }

                main_Text += "Change \t\t              ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_change) + "\n";
                main_Text += "------------------------------------------\r\n";
                if (str_reprintyn == "Y" || str_reprintyn.equals("Y")) {
                    if (!GlobalMemberValues.isStrEmpty(str_tipamount) && GlobalMemberValues.getDoubleAtString(str_tipamount) > 0) {
                        main_Text += "Tip \t\t              ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_tipamount) + "\n";
                        main_Text += "------------------------------------------\r\n";
                    }
                } else {
                    if (mTotalTipAmount[0] > 0) {
                        main_Text += "Tip \t\t              ";
                        main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(mTotalTipAmount[0] + "")) + "\n";
                        main_Text += "------------------------------------------\r\n";

                    }
                }

                // 프린트 실행 (1) ------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.posbankPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n\n", 1, attribute, 2, false);
                }
                GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                // ---------------------------------------------------------------------------

//              // card
                if (jsonArray_creditcardtransaction.length() != 0)
                {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        String strFilePath = "";        // 서명 이미지 경로

                        customerText = "";

                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);
                            str_cardchangeamount = dic_card_item.getString("chargeamount");
                            str_cardtype = dic_card_item.getString("cardtype");

                            str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            str_cardauthnumber = dic_card_item.getString("cardauthnumber");

                            if (dic_card_item.toString().contains("cardsalesignatureimageyn")) {
                                str_cardsalesignatureimageyn = dic_card_item.getString("cardsalesignatureimageyn");
                            } else {
                                str_cardsalesignatureimageyn = "N";
                            }

                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            } else {
                                str_cardaid = "";
                            }

                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            } else {
                                str_cardtvr = "";
                            }

                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            } else {
                                str_cardtsi = "";
                            }

                            if (dic_card_item.toString().contains("cardsalonsalescardidx")){
                                str_cardsalonsalescardidx = dic_card_item.getString("cardsalonsalescardidx");
                            } else {
                                str_cardsalonsalescardidx = "";
                            }

                            if (dic_card_item.toString().contains("cardtipamount")){
                                str_cardtipamount = dic_card_item.getString("cardtipamount");
                            } else {
                                str_cardtipamount = "0";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        merchantText += "******** Credit Card Transaction *********\r\n";

                        merchantText += "Card Type \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";

                        merchantText +="Credit Card# \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";

                        merchantText +="Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardchangeamount) + "\n";

                        merchantText +="Card Auth# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";

                        merchantText +="Card Ref# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";

                        if (str_cardaid.length() != 0){
                            merchantText += "AID \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                        }
                        if (str_cardtvr.length() != 0){
                            merchantText += "TSI \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";
                        }
                        if (str_cardtsi.length() != 0){
                            merchantText += "TVR \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";
                        }
                        merchantText += "------------------------------------------\r\n";


                        String cardsignedtxt =  "";

                        //customer copy
                        //merchant copy
                        if (GlobalMemberValues.getDoubleAtString(str_cardtipamount) > 0) {
                            double tempTotalAmount =
                                    GlobalMemberValues.getDoubleAtString(str_cardchangeamount) + GlobalMemberValues.getDoubleAtString(str_cardtipamount);

                            merchantText += "Tip\t                   ";
                            merchantText += Payment_stringFowardSpace_Exch(12,GlobalMemberValues.getCommaStringForDouble(str_cardtipamount + "")) + "\n";
                            merchantText += "Grand Total Amount\t    ";
                            merchantText += Payment_stringFowardSpace_Exch(12,GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + "")) + "\n";
                            merchantText += "------------------------------------------\r\n";

                        } else {
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                cardsignedtxt += "\t         Tip : ___________________  \n";
                                cardsignedtxt += " Grand Total Amount  : ___________________\r\n\r";
                            }
                        }
                    }
                }

                GlobalMemberValues.posbankPrintText(merchantText, alignment, attribute, size, false);

                // receipt footer 프린터 ----------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                    GlobalMemberValues.posbankPrintText(str_receiptfooter, 1, 0, 0, false);
                }
                // --------------------------------------------------------------------------------------------------------

                merchantText2 = "############## Customer Copy #############\r\n";
                merchantText2 += "     ** Thank you. Come again soon. **\r\n";
                merchantText2 += "             ";
                merchantText2 += getDate() + " " + getTime();

                GlobalMemberValues.posbankPrintText(merchantText2, alignment, attribute, size, false);
                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);

                // 돈통 열기 cash drawer
                if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
//                    PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);
                    try {
                        if (data.get("totalamount").equals(data.get("creditcardtendered"))){

                        } else {
                            PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }
        };

        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer")) {
            thread.start();
        }

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();

        // reprint 일 경우...
        if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
            GlobalMemberValues.mReReceiptprintYN = "N";
            return;
        }

        // 결제 리뷰창 오픈
        Payment.openPaymentReview(Payment.context);

        // 고객이 영수증 발급타입을 선택하는 경우가 아닐 때에만 주방프린트 실행
        // (고객이 영수증 발급타입을 선택하는 경우에는 앞에서 주방프린트가 실행되었음)
        int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
        if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
            try {
                Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String tempSalesCode = "";
            try {
                tempSalesCode = Payment.jsonroot_kitchen.getString("receiptno");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                // 주방프린트 하기 --------------------------------------------------------------------
                GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, Payment.context, "kitchen1");
                // -----------------------------------------------------------------------------------
            } else {
                if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                        // 주방프린트 하기 --------------------------------------------------------------------
                        GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                        // -----------------------------------------------------------------------------------
                    }
                }
            }
        }

        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            GlobalMemberValues.logWrite("autoprintreceiptlog", "GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING : " + GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING + "\n");
            if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {

                if (GlobalMemberValues.RECEIPTPRINTTYPE.equals("")) {
                    PaymentCustomerSelectReceipt.finishPayment();
                }

                GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
            }
        }
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {

        }
    };


    public void printVoidReceiptPosbank(final JSONObject data) {
        if (PosbankPrinter.mPrinter == null){
            return;
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                int alignment = Printer.ALIGNMENT_LEFT;
//        Printer.ALIGNMENT_LEFT
//        Printer.ALIGNMENT_CENTER
//        Printer.ALIGNMENT_RIGHT
                int size = 0;
                int attribute = 0;
                String main_Text = "";

                String titleText1 = "";
                String titleText2 = "";

                String customerText = "";
                String merchantText = "";

                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemdcextrastr = "";
                String str_itemtaxexempt = "";
                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_checktendered = "";
                String str_pointtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_alldiscountextrstr = "";
                String str_totalextradiscountprice="";

                String str_receiptprinttype = "";

                String str_optiontxt = "";
                String str_optionprice = "0.0";
                String str_additionaltxt1 = "";
                String str_additionalprice1 = "0.0";
                String str_additionaltxt2 = "";
                String str_additionalprice2 = "0.0";
                String str_kitchenmemo = "";

                String str_deliverytakeaway = "";
                String str_deliverypickupfee = "";

                String str_customerOrderNumber = "";

                String str_checkcompany = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_change = data.getString("change");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_checktendered = data.getString("checktendered");
                    str_pointtendered = data.getString("pointtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");

                    str_alldiscountextrstr = data.getString("alldiscountextrstr");

                    str_totalextradiscountprice = data.getString("totalextradiscountprice");


                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction"))
                    {
                        jsonArray_creditcardtransaction = data.getJSONArray("creditcardtransaction");
                    }

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }
                    if (data.toString().contains("deliverypickupfee")) {
                        str_deliverypickupfee = data.getString("deliverypickupfee");
                    } else {
                        str_deliverypickupfee = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("checkcompany")) {
                        str_checkcompany = data.getString("checkcompany");
                    } else {
                        str_checkcompany = "";
                    }

                    if (data.toString().contains("customerphonenum")) {
                        str_customerphonenum = data.getString("customerphonenum");
                    } else {
                        str_customerphonenum = "";
                    }

                    if (data.toString().contains("customeraddress")) {
                        str_customeraddress = data.getString("customeraddress");
                    } else {
                        str_customeraddress = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                titleText1 =  "*** " + str_storename + " ***\n";
                titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip +
                        "\n(TEL) "+ telNumberExch(str_storephone) + "\n" +
                        "\n";
                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "------------------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\nOrder No. : " + str_customerOrderNumber;

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        String tempDeliveryTakeaway = "HERE";
                        if (!GlobalMemberValues.isStrEmpty(str_deliverytakeaway)) {
                            switch (str_deliverytakeaway) {
                                case "H" : {
                                    tempDeliveryTakeaway = "HERE";
                                    break;
                                }
                                case "T" : {
                                    tempDeliveryTakeaway = "PICK UP";
                                    break;
                                }
                                case "D" : {
                                    tempDeliveryTakeaway = "DELIVERY";
                                    break;
                                }
                            }
                        }
                        titleText1 += "\nPick Up Type : " + tempDeliveryTakeaway + "\n";

                        if (!GlobalMemberValues.isStrEmpty(str_customername)) {
                            titleText1 += "Customer Name : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            titleText1 += "<Address>\n" + str_customeraddress + "\n";
                        }
                    }

                    titleText1 += "------------------------------------------\r";
                }
                // -----------------------------------------------------------------------------------
                titleText1 += "\n" +
                        ">>>>>>>>>>>>>>>>   VOID   <<<<<<<<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt No. : "
                        + str_receiptno + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "------------------------------------------\r\n";
                //"Item Name        Qty     Price      Amount\r\n" +
                //"------------------------------------------\r\n";
//
                if (jsonArray_saleitemlist.length() != 0)
                {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++)
                    {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemdcextrastr = dic_item.getString("itemdcextrastr");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");

                            str_optiontxt = dic_item.getString("optiontxt");
                            str_optionprice = dic_item.getString("optionprice");
                            str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            str_additionalprice1 = dic_item.getString("additionalprice1");
                            str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            str_additionalprice2 = dic_item.getString("additionalprice2");

                            if (dic_item.toString().contains("kitchenmemo")) {
                                str_kitchenmemo = dic_item.getString("kitchenmemo");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        list.add(Payment_stringBackSpace_Exch(21, str_itemname).getBytes()); // 21자.
//                        list.add(Payment_stringFowardSpace_Exch(4, str_itemqty).getBytes());
//                        list.add(Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice)).getBytes());
//                        list.add(Payment_stringFowardSpace_Exch(11, str_itemamount).getBytes());
//                        list.add("\n".getBytes());
                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }
                        /**
                         main_Text += Payment_stringFowardSpace_Exch(16, str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";
                         **/

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                            double tempItemAmount = GlobalMemberValues.getDoubleAtString(str_itemamount);
                            if (GlobalMemberValues.MODIFIER_PRICEVIEW) {
                                double tempOptionPriceSum = GlobalMemberValues.getDoubleAtString(str_optionprice)
                                        + GlobalMemberValues.getDoubleAtString(str_additionalprice1) + GlobalMemberValues.getDoubleAtString(str_additionalprice2);
                                tempItemAmount -= tempOptionPriceSum;

                                str_itemamount = tempItemAmount + "";
                            }
                        }

                        main_Text += Payment_stringBackSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(27, str_itemname) +
                                Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";

                        if (GlobalMemberValues.isModifierPrintYN()) {
                            if (!GlobalMemberValues.isStrEmpty(str_optiontxt) ||
                                    !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                String str_option_additional_txt = "";
                                if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                                    str_option_additional_txt = "" + GlobalMemberValues.getModifierTxt(str_itemqty, str_optiontxt);
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt1)) {
                                    str_option_additional_txt += "[Add Ingredients]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt1) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                    str_option_additional_txt += "[Add Ingredients2]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt2) + "\n";
                                }
                                main_Text += "" + str_option_additional_txt;
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "   [Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      "+str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "------------------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }


//                list.add("------------------------------------------------\r\n".getBytes());
//                list.add("Sub Total \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_subtotal).getBytes());
//                list.add("\n".getBytes());
//                list.add("Tax \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_tax).getBytes());
//                list.add("\n".getBytes());
//                list.add("Total Amount \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_totalamount).getBytes());
//                list.add("\n".getBytes());
//                list.add("------------------------------------------------\r\n".getBytes());

                main_Text += "------------------------------------------\r\n" +
                        "Sub Total \t\t      ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t      ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total 	\t\t      ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_tax)) + "\n";

                if (!GlobalMemberValues.isStrEmpty(str_deliverypickupfee) && GlobalMemberValues.getDoubleAtString(str_deliverypickupfee) > 0) {
                    if (str_deliverytakeaway.equals("D")) {
                        main_Text +=  "Delivery Fee \t\t      ";
                    } else {
                        main_Text +=  "Pick Up Fee \t\t      ";
                    }
                    main_Text += Payment_stringFowardSpace_Exch(12,combineStringForVoidPrint(str_deliverypickupfee)) + "\n";
                }

                main_Text +=  "Total Amount \t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_totalamount)) + "\n";

                main_Text += "------------------------------------------\r\n";



//
                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Credit Card Tendered \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

//                list.add("------------------------------------------------\r\n\n".getBytes());
                main_Text += "------------------------------------------\r\n";


//
//                ArrayList<byte[]> merchant_list = (ArrayList<byte[]>) list.clone();
//                ArrayList<byte[]> customer_list = (ArrayList<byte[]>) list.clone();
//
//                // card
                if (jsonArray_creditcardtransaction.length() != 0)
                {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);
                            str_cardchangeamount = dic_card_item.getString("chargeamount");
                            str_cardtype = dic_card_item.getString("cardtype");

                            str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            str_cardauthnumber = dic_card_item.getString("cardauthnumber");


                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            }
                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            }
                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        merchantText += "******** Credit Card Transaction *********\r\n";
                        customerText += "******** Credit Card Transaction *********\r\n";

//                        merchant_list.add("******** Credit Card Transaction *********\r\n".getBytes());
//                        customer_list.add("******** Credit Card Transaction *********\r\n".getBytes());
//                        merchant_list.add("Card Type \t : ".getBytes());
//                        customer_list.add("Card Type \t : ".getBytes());
                        merchantText += "Card Type \t : ";
                        customerText += "Card Type \t : ";

//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());

//                        merchant_list.add("Credit Card# \t : ".getBytes());
//                        customer_list.add("Credit Card# \t : ".getBytes());
                        merchantText +="Credit Card# \t : ";
                        customerText +="Credit Card# \t : ";
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());


//                        merchant_list.add("Charge Amount \t : ".getBytes());
//                        customer_list.add("Charge Amount \t : ".getBytes());
                        merchantText +="Charge Amount \t : ";
                        customerText +="Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());


//                        merchant_list.add("Card Auth# \t : ".getBytes());
//                        customer_list.add("Card Auth# \t : ".getBytes());
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());
                        merchantText +="Card Auth# \t : ";
                        customerText +="Card Auth# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";


//                        merchant_list.add("Card Ref# \t : ".getBytes());
//                        customer_list.add("Card Ref# \t : ".getBytes());
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());
                        merchantText +="Card Ref# \t : ";
                        customerText +="Card Ref# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";

                        if (str_cardaid.length() != 0){
//                            merchant_list.add("AID \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("AID \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
//                            customer_list.add("\n".getBytes());
                            merchantText += "AID \t : ";
                            customerText += "AID \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                        }
                        if (str_cardtvr.length() != 0){
//                            merchant_list.add("TSI \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("TSI \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
//                            customer_list.add("\n".getBytes());

                            merchantText += "TSI \t : ";
                            customerText += "TSI \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";

                        }
                        if (str_cardtsi.length() != 0){
//                            merchant_list.add("TVR \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("TVR \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
//                            customer_list.add("\n".getBytes());

                            merchantText += "TVR \t : ";
                            customerText += "TVR \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";

                        }
//                        merchant_list.add("------------------------------------------------\r\n\n".getBytes());
//                        customer_list.add("------------------------------------------------\r\n\n".getBytes());
                        merchantText += "------------------------------------------\r\n";
                        customerText += "------------------------------------------\r\n";



                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            //merchant copy
//                            merchant_list.add("\t              Tip : _________________________  \n".getBytes());
//                            merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
//                            merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : ___________________  \n";
                                merchantText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ______________________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            //customer copy
                            //merchant copy
//                            merchant_list.add("\t              Tip : _________________________  \n".getBytes());
//                            merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
//                            merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : ___________________  \n";
                                customerText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ______________________________\r\n\r\n";
                            }
                        } else {

                        }

                    }
                }

//                customerText += "\t         Tip : ___________________  \n";
//                customerText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
//                customerText += " Signature ______________________________\r\n\r\n";

//                customer_list.add("################# Customer Copy ################\r\n".getBytes());
//                customer_list.add("     ** Thank you. Come again soon. **\r\n".getBytes());
//                customer_list.add("             ".getBytes());
//                customer_list.add(getDate().getBytes());
//                customer_list.add(" ".getBytes());
//                customer_list.add(getTime().getBytes());
//                customer_list.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
//                customer_list.add(new byte[]{0x07}); // Kick cash drawer

                customerText += "############## Customer Copy #############\r\n";
                customerText += "     ** Thank you. Come again soon. **\r\n";
                customerText += "             ";
                customerText += getDate() + " " + getTime();



//
//
//                merchant_list.add("################# Merchant Copy ################\r\n".getBytes());
////                merchant_list.add("     ** Thank you. Come again soon. **\r\n".getBytes());
//                merchant_list.add("             ".getBytes());
//                merchant_list.add(getDate().getBytes());
//                merchant_list.add(" ".getBytes());
//                merchant_list.add(getTime().getBytes());
//                merchant_list.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
//                merchant_list.add(new byte[]{0x07}); // Kick cash drawer

                merchantText += "############## Merchant Copy #############\r\n";
                merchantText += "     ** Thank you. Come again soon. **\r\n";
                merchantText += "             ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(merchantText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);

                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(merchantText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else {
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                }

//                GlobalMemberValues.posbankPrintText(main_Text, alignment, attribute, size, false);
                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }
        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public void printReturnReceiptPosbank(final JSONObject data) {
        if (PosbankPrinter.mPrinter == null){
            return;
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                int alignment = Printer.ALIGNMENT_LEFT;
//        Printer.ALIGNMENT_LEFT
//        Printer.ALIGNMENT_CENTER
//        Printer.ALIGNMENT_RIGHT
                int size = 0;
                int attribute = 0;
                String main_Text = "";

                String titleText1 = "";
                String titleText2 = "";

                String customerText = "";
                String merchantText = "";

                String str_saledate = "";
                String str_receiptno = "";
                String str_change = "";
                String str_cashtendered = "";
                String str_employeename = "";
                JSONArray jsonArray_saleitemlist = new JSONArray();
                String str_itemqty = "";
                String str_itemname = "";
                String str_itemamount = "";
                String str_itemprice = "";
                String str_itemdcextrastr = "";
                String str_itemtaxexempt = "";
                String str_subtotal = "";
                JSONArray jsonArray_creditcardtransaction = new JSONArray();
                String str_cardchangeamount = "";
                String str_cardtype = "";
                String str_cardaid = "";
                String str_cardtvr = "";
                String str_cardtsi = "";
                String str_creditcardnumber = "";
                String str_cardrefnumber = "";
                String str_cardauthnumber = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";
                String str_giftcardtendered = "";
                String str_tax = "";
                String str_creditcardtendered = "";
                String str_checktendered = "";
                String str_pointtendered = "";
                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_saletime = "";
                String str_storephone = "";
                String str_totalamount = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_alldiscountextrstr = "";
                String str_totalextradiscountprice="";

                String str_receiptprinttype = "";

                String str_optiontxt = "";
                String str_optionprice = "0.0";
                String str_additionaltxt1 = "";
                String str_additionalprice1 = "0.0";
                String str_additionaltxt2 = "";
                String str_additionalprice2 = "0.0";
                String str_kitchenmemo = "";

                String str_returntipamount = "";

                String str_returnpickupdeliveryfee = "";

                String str_customerOrderNumber = "";

                String str_checkcompany = "";

                String str_deliverytakeaway = "";

                try {
                    str_saledate = data.getString("saledate");
                    str_receiptno = data.getString("receiptno");
                    str_cashtendered = data.getString("cashtendered");
                    str_employeename = data.getString("employeename");
                    str_subtotal = data.getString("subtotal");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");
                    str_giftcardtendered = data.getString("giftcardtendered");
                    str_tax = data.getString("tax");
                    str_creditcardtendered = data.getString("creditcardtendered");
                    str_checktendered = data.getString("checktendered");
                    str_pointtendered = data.getString("pointtendered");
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_saletime = data.getString("saletime");
                    str_storephone = data.getString("storephone");
                    str_totalamount = data.getString("totalamount");
                    str_customername = data.getString("customername");

                    str_receiptprinttype = data.getString("receiptprinttype");

                    if (data.getJSONArray("saleitemlist") != null)
                    {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }

                    if (data.toString().contains("returntipamount")) {
                        str_returntipamount = data.getString("returntipamount");
                    } else {
                        str_returntipamount = "";
                    }

                    if (data.toString().contains("returnpickupdeliveryfee")) {
                        str_returnpickupdeliveryfee = data.getString("returnpickupdeliveryfee");
                    } else {
                        str_returnpickupdeliveryfee = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("checkcompany")) {
                        str_checkcompany = data.getString("checkcompany");
                    } else {
                        str_checkcompany = "";
                    }

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }

                    if (data.toString().contains("customerphonenum")) {
                        str_customerphonenum = data.getString("customerphonenum");
                    } else {
                        str_customerphonenum = "";
                    }

                    if (data.toString().contains("customeraddress")) {
                        str_customeraddress = data.getString("customeraddress");
                    } else {
                        str_customeraddress = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                if (GlobalMemberValues.isStrEmpty(str_customerOrderNumber)) {
                    str_customerOrderNumber = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select customerordernumber from salon_sales where salesCode = '" + str_receiptno + "' ");
                }

                titleText1 =  "*** " + str_storename + " ***\n";
                titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip +
                        "\n(TEL) "+ telNumberExch(str_storephone) + "\n";
                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "------------------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\nOrder No. : " + str_customerOrderNumber;

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        String tempDeliveryTakeaway = "HERE";
                        if (!GlobalMemberValues.isStrEmpty(str_deliverytakeaway)) {
                            switch (str_deliverytakeaway) {
                                case "H" : {
                                    tempDeliveryTakeaway = "HERE";
                                    break;
                                }
                                case "T" : {
                                    tempDeliveryTakeaway = "PICK UP";
                                    break;
                                }
                                case "D" : {
                                    tempDeliveryTakeaway = "DELIVERY";
                                    break;
                                }
                            }
                        }
                        titleText1 += "\nPick Up Type : " + tempDeliveryTakeaway + "\n";

                        if (!GlobalMemberValues.isStrEmpty(str_customername)) {
                            titleText1 += "Customer Name : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            titleText1 += "<Address>\n" + str_customeraddress + "\n";
                        }
                    }

                    titleText1 += "------------------------------------------\r";
                }
                // -----------------------------------------------------------------------------------
                titleText1 += "\n" +
                        ">>>>>>>>>>>>>>>   RETURN   <<<<<<<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt No. : "
                        + str_receiptno + "\nRefund Date : " + str_saledate + "\nRefund Time : " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nRefund Date : " + str_saledate + "\nRefund Time : " + str_saletime + "\n";

                main_Text += "------------------------------------------\r\n";
                //"Item Name        Qty     Price      Amount\r\n" +
                //"------------------------------------------\r\n";
//
                if (jsonArray_saleitemlist.length() != 0)
                {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++)
                    {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");

                            str_optiontxt = dic_item.getString("optiontxt");
                            str_optionprice = dic_item.getString("optionprice");
                            str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            str_additionalprice1 = dic_item.getString("additionalprice1");
                            str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            str_additionalprice2 = dic_item.getString("additionalprice2");

                            if (dic_item.toString().contains("kitchenmemo")) {
                                str_kitchenmemo = dic_item.getString("kitchenmemo");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        list.add(Payment_stringBackSpace_Exch(21, str_itemname).getBytes()); // 21자.
//                        list.add(Payment_stringFowardSpace_Exch(4, str_itemqty).getBytes());
//                        list.add(Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice)).getBytes());
//                        list.add(Payment_stringFowardSpace_Exch(11, str_itemamount).getBytes());
//                        list.add("\n".getBytes());
                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }
                        /**
                         main_Text += Payment_stringFowardSpace_Exch(16, str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";
                         **/

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                            double tempItemAmount = GlobalMemberValues.getDoubleAtString(str_itemamount);
                            if (GlobalMemberValues.MODIFIER_PRICEVIEW) {
                                double tempOptionPriceSum = GlobalMemberValues.getDoubleAtString(str_optionprice)
                                        + GlobalMemberValues.getDoubleAtString(str_additionalprice1) + GlobalMemberValues.getDoubleAtString(str_additionalprice2);
                                tempItemAmount -= tempOptionPriceSum;

                                str_itemamount = tempItemAmount + "";
                            }
                        }

                        main_Text += Payment_stringBackSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(27, str_itemname) +
                                Payment_stringFowardSpace_Exch(11, str_itemamount)+ "\n";

                        if (GlobalMemberValues.isModifierPrintYN()) {
                            if (!GlobalMemberValues.isStrEmpty(str_optiontxt) ||
                                    !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                String str_option_additional_txt = "";
                                if (!GlobalMemberValues.isStrEmpty(str_optiontxt)) {
                                    str_option_additional_txt = "" + GlobalMemberValues.getModifierTxt(str_itemqty, str_optiontxt);
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt1)) {
                                    str_option_additional_txt += "[Add Ingredients]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt1) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(str_additionaltxt2)) {
                                    str_option_additional_txt += "[Add Ingredients2]\n"+ GlobalMemberValues.getModifierTxt(str_itemqty, str_additionaltxt2) + "\n";
                                }
                                main_Text += "" + str_option_additional_txt;
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "   [Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      "+str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "------------------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }


//                list.add("------------------------------------------------\r\n".getBytes());
//                list.add("Sub Total \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_subtotal).getBytes());
//                list.add("\n".getBytes());
//                list.add("Tax \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_tax).getBytes());
//                list.add("\n".getBytes());
//                list.add("Total Amount \t\t  ".getBytes());
//                list.add(Payment_stringFowardSpace_Exch(12,str_totalamount).getBytes());
//                list.add("\n".getBytes());
//                list.add("------------------------------------------------\r\n".getBytes());

                main_Text += "------------------------------------------\r\n" +
                        "Sub Total \t\t      ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t      ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total 	\t\t      ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_tax)) + "\n";

                main_Text +=  "Total Amount \t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_totalamount)) + "\n";

                main_Text += "------------------------------------------\r\n";

                if (!GlobalMemberValues.isStrEmpty(str_returntipamount)) {
                    main_Text += "Tip \t\t\t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returntipamount)) + "\n";
                    main_Text += "------------------------------------------\r\n";
                }

                if (!GlobalMemberValues.isStrEmpty(str_returnpickupdeliveryfee)) {
                    main_Text += "Pickup/Delivery Fee \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returnpickupdeliveryfee)) + "\n";
                    main_Text += "------------------------------------------\r\n";
                }

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Credit Card Tendered \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered  \t      ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

                main_Text += "------------------------------------------\r\n";


//
//                ArrayList<byte[]> merchant_list = (ArrayList<byte[]>) list.clone();
//                ArrayList<byte[]> customer_list = (ArrayList<byte[]>) list.clone();
//
//                // card
                if (jsonArray_creditcardtransaction.length() != 0)
                {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        try {
                            JSONObject dic_card_item = jsonArray_creditcardtransaction.getJSONObject(i);

                            if (dic_card_item.toString().contains("chargeamount")){
                                str_cardchangeamount = dic_card_item.getString("chargeamount");
                            }
                            if (dic_card_item.toString().contains("cardtype")){
                                str_cardtype = dic_card_item.getString("cardtype");
                            }
                            if (dic_card_item.toString().contains("creditcardnumber")){
                                str_creditcardnumber = dic_card_item.getString("creditcardnumber");
                            }
                            if (dic_card_item.toString().contains("cardrefnumber")){
                                str_cardrefnumber = dic_card_item.getString("cardrefnumber");
                            }
                            if (dic_card_item.toString().contains("cardauthnumber")){
                                str_cardauthnumber = dic_card_item.getString("cardauthnumber");
                            }
                            if (dic_card_item.toString().contains("aaa")){
                                str_cardtype = dic_card_item.getString("aaa");
                            }
                            if (dic_card_item.toString().contains("aaa")){
                                str_cardtype = dic_card_item.getString("aaa");
                            }
                            if (dic_card_item.toString().contains("cardaid")){
                                str_cardaid = dic_card_item.getString("cardaid");
                            }
                            if (dic_card_item.toString().contains("cardtvr")){
                                str_cardtvr = dic_card_item.getString("cardtvr");
                            }
                            if (dic_card_item.toString().contains("cardtsi")){
                                str_cardtsi = dic_card_item.getString("cardtsi");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        merchantText += "******** Credit Card Transaction *********\r\n";
                        customerText += "******** Credit Card Transaction *********\r\n";

//                        merchant_list.add("******** Credit Card Transaction *********\r\n".getBytes());
//                        customer_list.add("******** Credit Card Transaction *********\r\n".getBytes());
//                        merchant_list.add("Card Type \t : ".getBytes());
//                        customer_list.add("Card Type \t : ".getBytes());
                        merchantText += "Card Type \t : ";
                        customerText += "Card Type \t : ";

//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtype).getBytes());
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());

//                        merchant_list.add("Credit Card# \t : ".getBytes());
//                        customer_list.add("Credit Card# \t : ".getBytes());
                        merchantText +="Credit Card# \t : ";
                        customerText +="Credit Card# \t : ";
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_creditcardnumber).getBytes());
                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());


//                        merchant_list.add("Charge Amount \t : ".getBytes());
//                        customer_list.add("Charge Amount \t : ".getBytes());
                        merchantText +="Charge Amount \t : ";
                        customerText +="Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardchangeamount).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());


//                        merchant_list.add("Card Auth# \t : ".getBytes());
//                        customer_list.add("Card Auth# \t : ".getBytes());
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardauthnumber).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());
                        merchantText +="Card Auth# \t : ";
                        customerText +="Card Auth# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardauthnumber) + "\n";


//                        merchant_list.add("Card Ref# \t : ".getBytes());
//                        customer_list.add("Card Ref# \t : ".getBytes());
//                        merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
//                        customer_list.add(Payment_stringBackSpace_Exch(16,str_cardrefnumber).getBytes());
//                        merchant_list.add("\n".getBytes());
//                        customer_list.add("\n".getBytes());
                        merchantText +="Card Ref# \t : ";
                        customerText +="Card Ref# \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardrefnumber) + "\n";

                        if (str_cardaid.length() != 0){
//                            merchant_list.add("AID \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("AID \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardaid).getBytes());
//                            customer_list.add("\n".getBytes());
                            merchantText += "AID \t : ";
                            customerText += "AID \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardaid) + "\n";
                        }
                        if (str_cardtvr.length() != 0){
//                            merchant_list.add("TSI \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("TSI \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtvr).getBytes());
//                            customer_list.add("\n".getBytes());

                            merchantText += "TSI \t : ";
                            customerText += "TSI \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtvr) + "\n";

                        }
                        if (str_cardtsi.length() != 0){
//                            merchant_list.add("TVR \t : ".getBytes());
//                            merchant_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
//                            merchant_list.add("\n".getBytes());
//                            customer_list.add("TVR \t : ".getBytes());
//                            customer_list.add(Payment_stringBackSpace_Exch(16,str_cardtsi).getBytes());
//                            customer_list.add("\n".getBytes());

                            merchantText += "TVR \t : ";
                            customerText += "TVR \t : ";
                            merchantText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";
                            customerText += Payment_stringBackSpace_Exch(16,str_cardtsi) + "\n";

                        }
//                        merchant_list.add("------------------------------------------------\r\n\n".getBytes());
//                        customer_list.add("------------------------------------------------\r\n\n".getBytes());
                        merchantText += "------------------------------------------\r\n";
                        customerText += "------------------------------------------\r\n";



                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            //merchant copy
//                            merchant_list.add("\t              Tip : _________________________  \n".getBytes());
//                            merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
//                            merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : ___________________  \n";
                                merchantText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ______________________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            //customer copy
                            //merchant copy
//                            merchant_list.add("\t              Tip : _________________________  \n".getBytes());
//                            merchant_list.add(" Grand Total Amount : _________________________\r\n\r\n\r\n".getBytes());
//                            merchant_list.add(" Signature ____________________________________\r\n\r\n".getBytes());
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : ___________________  \n";
                                customerText += " Grand Total Amount : ___________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ______________________________\r\n\r\n";
                            }
                        } else {

                        }

                    }
                }

                customerText = "############## Merchant Copy #############\r\n";
                customerText += "     ** Thank you. Come again soon. **\r\n";
                customerText += "             ";
                customerText += getDate() + " " + getTime();

                merchantText += "############## Customer Copy #############\r\n";
                merchantText += "     ** Thank you. Come again soon. **\r\n";
                merchantText += "             ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(merchantText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);

                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(merchantText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                } else {
                    GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(titleText2, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);
                    GlobalMemberValues.posbankPrintText(customerText, alignment, attribute, size, false);
                    GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                    PosbankPrinter.mPrinter.cutPaper(true);
                }

//                GlobalMemberValues.posbankPrintText(main_Text, alignment, attribute, size, false);
                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }
        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public void printPhoneOrderCheckPrint(final JSONObject data) {
        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");
        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorderholecode2 : " + GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT + "\n");

        if (PosbankPrinter.mPrinter == null){
            return;
        }

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "프린트안 json -- 1 : " + data.toString() + "\n");

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(1000); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;

                String str_receiptno = "";
                String str_ordertype = "";

                String titleText1 = "";
                String titleText2 = "";
                String titleText3 = "";
                String main_Text = "";

                String memo_Text = "";

                String customerText = "";

                String str_saledate = "";
                String str_saletime = "";

                String str_saleitemlist = "";

                String str_customername = "";
                String str_customerphonenum = "";
                String str_customeraddress = "";

                String str_deliverytakeaway = "";
                String str_deliverydate = "";

                String str_customermemo = "";

                String str_kitchenreprintyn = "N";

                String str_receiptfooter = "";

                String str_customerOrderNumber = "";
                String str_customerPagerNumber = "";

                String str_phoneOrder = "";
                String str_phoneOrderNumber = "";
                String str_phoneorderholecode = "";

                String str_storestate = "";
                String str_storename = "";
                String str_storecity = "";
                String str_storezip = "";
                String str_storephone = "";
                String str_storeaddress2 = "";
                String str_storeaddress1 = "";

                try {
                    str_storestate = data.getString("storestate");
                    str_storename  = data.getString("storename");
                    str_storecity = data.getString("storecity");
                    str_storezip = data.getString("storezip");
                    str_storeaddress2 = data.getString("storeaddress2");
                    str_storeaddress1 = data.getString("storeaddress1");

                    str_saledate = data.getString("saledate");
                    str_saletime = data.getString("saletime");
                    str_receiptno = data.getString("receiptno");

                    str_saleitemlist = data.getString("saleitemlist");

                    str_customername = data.getString("customername");
                    str_customerphonenum = data.getString("customerphonenum");
                    str_customeraddress = data.getString("customeraddress");

                    str_deliverytakeaway = data.getString("deliverytakeaway");
                    str_deliverydate = data.getString("deliverydate");

                    str_ordertype = data.getString("ordertype");

                    str_customermemo = data.getString("customermemo");


                    if (data.toString().contains("receiptfooter")) {
                        str_receiptfooter = data.getString("receiptfooter");
                    } else {
                        str_receiptfooter = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = data.getString("customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
                    }

                    if (data.toString().contains("phoneorder")) {
                        str_phoneOrder = data.getString("phoneorder");
                    } else {
                        str_phoneOrder = "";
                    }

                    if (data.toString().contains("phoneordernumber")) {
                        str_phoneOrderNumber = data.getString("phoneordernumber");
                    } else {
                        str_phoneOrderNumber = "";
                    }

                } catch (JSONException e){
                    Log.e("PaymentPrint get str err!!!", e.toString());
                }

                if (!GlobalMemberValues.isStrEmpty(str_phoneOrder) && str_phoneOrder.equals("Y")) {
                    str_customerOrderNumber = str_phoneOrderNumber;
                }

                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.posbankPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n\n", 1, attribute, 2, false);
                }

                titleText1 =  "*** " + str_storename + " ***\n";
                titleText1 += str_storeaddress1 + " "+ str_storeaddress2+ "\n" + str_storecity + " " + str_storestate + " "+ str_storezip + "\n(TEL) "+ telNumberExch(str_storephone) + "\n";
                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "------------------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\nOrder No. : " + str_customerOrderNumber;

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        String tempDeliveryTakeaway = "HERE";
                        if (!GlobalMemberValues.isStrEmpty(str_deliverytakeaway)) {
                            switch (str_deliverytakeaway) {
                                case "H" : {
                                    tempDeliveryTakeaway = "HERE";
                                    break;
                                }
                                case "T" : {
                                    tempDeliveryTakeaway = "PICK UP";
                                    break;
                                }
                                case "D" : {
                                    tempDeliveryTakeaway = "DELIVERY";
                                    break;
                                }
                            }
                        }
                        titleText1 += "\nPick Up Type : " + tempDeliveryTakeaway + "\n";

                        if (!GlobalMemberValues.isStrEmpty(str_customername)) {
                            titleText1 += "Customer Name : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            titleText1 += "<Address>\n" + str_customeraddress + "\n";
                        }
                    }

                    titleText1 += "------------------------------------------\r";
                }
                // -----------------------------------------------------------------------------------

                titleText1 += "\nReceipt No. : "
                        + str_receiptno + "\nSale Date : " + str_saledate + " " + str_saletime + "\n";
                //titleText1 += "\nSale Date : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "------------------------------------------\r\n";
                //"Item Name        Qty     Price      Amount\r\n" +
                //"------------------------------------------\r\n";

                GlobalMemberValues.posbankPrintText(titleText1, 1, attribute, 0, false);

                double pickupDeliveryFee = 0;
                double subTotal = 0;
                double subTax = 0;
                double totalAmount = 0;
                if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                    String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                    for (int i = 0; i < strOrderItemsList.length; i++) {
                        String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                        // 상품명, 수량 정보 ------------------------------------------------------------------------
                        String tempItemNameOptionAdd = strOrderItems[0];
                        String tempItemQty = strOrderItems[1];
                        String tempPrice = strOrderItems[2];
                        String tempPriceAmount = strOrderItems[3];
                        String tempTaxAmount = strOrderItems[4];
                        String tempTotalAmount = strOrderItems[5];

                        GlobalMemberValues.logWrite("saleitemstrarray", "tempItemNameOptionAdd : " + tempItemNameOptionAdd + "\n");

                        String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
                        String tempItemName = strItemNAmeOptionAdd[0];
                        String tempOptionTxt = "";
                        String tempAdditionalTxt1 = "";
                        String tempAdditionalTxt2 = "";
                        String tempItemIdx = "";
                        String tempKitchenMemo = "";
                        if (strItemNAmeOptionAdd.length > 1) {
                            tempOptionTxt = strItemNAmeOptionAdd[1];
                            if (strItemNAmeOptionAdd.length > 2) {
                                tempAdditionalTxt1 = strItemNAmeOptionAdd[2];
                            }
                            if (strItemNAmeOptionAdd.length > 3) {
                                tempAdditionalTxt2 = strItemNAmeOptionAdd[3];
                            }
                            if (strItemNAmeOptionAdd.length > 4) {
                                tempItemIdx = strItemNAmeOptionAdd[4];
                            }
                            if (strItemNAmeOptionAdd.length > 6) {
                                tempKitchenMemo = strItemNAmeOptionAdd[6];
                            }
                        }
                        // -----------------------------------------------------------------------------------------

                        main_Text += Payment_stringFowardSpace_Exch(16, tempItemName) +
                                Payment_stringFowardSpace_Exch(3, tempItemQty) +
                                Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(tempPrice))+
                                Payment_stringFowardSpace_Exch(11, GlobalMemberValues.getCommaStringForDouble(tempPriceAmount))+ "\n";

                        if (GlobalMemberValues.isModifierPrintYN()) {
                            if (!GlobalMemberValues.isStrEmpty(tempOptionTxt) ||
                                    !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1) || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                String str_option_additional_txt = "";
                                if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                    str_option_additional_txt = "" + GlobalMemberValues.getModifierTxt(tempItemQty, tempOptionTxt) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                    str_option_additional_txt += "\n[Add Ingredients]\n" + GlobalMemberValues.getModifierTxt(tempItemQty, tempAdditionalTxt1);
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                                    str_option_additional_txt += "\n[Add Ingredients2]\n" + GlobalMemberValues.getModifierTxt(tempItemQty, tempAdditionalTxt2);
                                }
                                main_Text += "" + str_option_additional_txt;
                            }
                        }

                        subTotal += GlobalMemberValues.getDoubleAtString(tempPriceAmount);
                        subTax += GlobalMemberValues.getDoubleAtString(tempTaxAmount);
                        totalAmount += GlobalMemberValues.getDoubleAtString(tempPriceAmount) + GlobalMemberValues.getDoubleAtString(tempTaxAmount);;
                    }
                }

                if (str_deliverytakeaway.equals("D")) {
                    // 기본 배송비가 얼마인지 구한다.
                    double store_deliverycharge = GlobalMemberValues.getDoubleAtString(
                            MainActivity.mDbInit.dbExecuteReadReturnString("select deliverycharge from salon_storegeneral")
                    );

                    // 배송비 무료 기준금액
                    double store_deliveryfreemoney = GlobalMemberValues.getDoubleAtString(
                            MainActivity.mDbInit.dbExecuteReadReturnString("select deliveryfree from salon_storegeneral")
                    );

                    if (totalAmount > 0) {
                        if (totalAmount < store_deliveryfreemoney) {
                            pickupDeliveryFee = store_deliverycharge;
                        }
                    }
                } else {
                    if (str_deliverytakeaway.equals("T")) {
                        // Pickup (Togo) 포장 비용이 있을 경우
                        double store_pickupcharge = GlobalMemberValues.getDoubleAtString(
                                MainActivity.mDbInit.dbExecuteReadReturnString("select pickupcharge from salon_storegeneral")
                        );
                        pickupDeliveryFee = store_pickupcharge;
                    }
                }

                totalAmount += pickupDeliveryFee;

                main_Text += "------------------------------------------\r\n";
                main_Text += "Sub Total \t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(subTotal + "")) + "\n";

                main_Text += "Tax \t\t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(subTax + "")) + "\n";
                if (pickupDeliveryFee > 0) {
                    if (str_deliverytakeaway.equals("D")) {
                        main_Text += "Delivery Fee \t\t      ";
                    } else {
                        main_Text += "Pick Up Fee \t\t      ";
                    }
                    main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(pickupDeliveryFee + "")) + "\n";
                }
                main_Text +=  "Total Amount \t\t      ";
                main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(totalAmount + "")) + "\n";
                main_Text += "------------------------------------------\r\n";

                GlobalMemberValues.posbankPrintText(main_Text, 0, attribute, 0, false);

                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReceiptFooter())) {
                    GlobalMemberValues.posbankPrintText(GlobalMemberValues.getReceiptFooter(), 1, 0, 0, false);
                }

                customerText = "Printed Time " + getDate() + " " + getTime() + "\n\n";
                GlobalMemberValues.posbankPrintText(customerText, 0, attribute, 0, false);

                GlobalMemberValues.posbankPrintText("\n\n\n\n\n", alignment, attribute, size, false);
                PosbankPrinter.mPrinter.cutPaper(true);
                //PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);

                // 프린터 연결을 끊는다.
                GlobalMemberValues.disconnectPrinter();
            }
        };
        thread.start();
    }

    public void printTestPosbank(final JSONObject data) {
        if (PosbankPrinter.mPrinter == null){
            return;
        }
        Thread thread = new Thread(){
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;

                String testTxt = "";

                if (data != null) {
                    testTxt = data.toString();
                }

                if (GlobalMemberValues.isStrEmpty(testTxt)) {
                    testTxt = "TEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\nTEST PRINTED.............." +
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "\nTEST PRINTED..............";
                }

                GlobalMemberValues.posbankPrintText(testTxt, 1, attribute, 0, false);
                PosbankPrinter.mPrinter.cutPaper(true);

                PosbankPrinter.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);
                GlobalMemberValues.disconnectPrinter();
            }
        };
        thread.start();

        // Device 가 Elo 일 경우 Elo 돈통 오픈
        GlobalMemberValues.eloOpenDrawerAtOtherDevice();
    }

    public String strlengthcut(String str,int len){
        if (!TextUtils.isEmpty(str)){
            if (str.length() > len){
                str = str.substring(0,len);
                str = str + "..";
            } else if (str.length() == len){
                str = str + "  ";
            } else {
                str = str + "\t      ";
            }
        }
        return str;
    }

}
