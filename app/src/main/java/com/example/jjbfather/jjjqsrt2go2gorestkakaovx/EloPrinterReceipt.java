package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;
import com.printer.posbank.Printer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by byullbam on 16. 2. 23..
 */
public class EloPrinterReceipt {
    Context mContext;

    public ProductInfo productInfo;
    private ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    private PrinterMake myPrinter;

    int mCardPaymentCount = 0;

    public EloPrinterReceipt(Context paramContext) {
        mContext = paramContext;
        if (mContext == null) {
            mContext = Payment.context;
        }
        if (mContext == null) {
            mContext = MainActivity.mContext;
        }

        if (MainActivity.mProductInfo == null) {
            MainActivity.setEloInit();
        }

        productInfo = MainActivity.mProductInfo;

        if (productInfo != null) {
            EloPlatform platform = productInfo.eloPlatform;

            if (MainActivity.mApiAdapter == null) {
                MainActivity.setEloInit();
            }
            apiAdapter = MainActivity.mApiAdapter;

            if (apiAdapter == null) {
                // We're not running on a supported platform.  This is a common customer scenario where
                // the APK may be written for multiple vendor platforms.  In our case, we can't do much
                Toast.makeText(paramContext, "Cannot find support for this platform", Toast.LENGTH_LONG).show();
                return;
            }

            switch (platform) {  // TODO: Base off detection, not platform
                case PAYPOINT_1:
                case PAYPOINT_REFRESH:
                    myPrinter = PrinterMake.RONGTA;
                    break;
                case PAYPOINT_2:
                    myPrinter = PrinterMake.STAR;
                    break;
                default:
                    myPrinter = null;
            }
        }
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

        if (!GlobalMemberValues.isStrEmpty(instr)) {
            instr = GlobalMemberValues.getDecodeString(instr);

            int strLength = GlobalMemberValues.getSizeToString(instr);

            if (strLength > stringLength) {
                temp_str = instr.substring(0,stringLength);
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
        }

        return return_str;
    }

    public String combineStringForVoidPrint(String paramStr) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramStr)) {
            double tempParamStr = GlobalMemberValues.getDoubleAtString(paramStr);
            if (tempParamStr == 0) {
                returnValue = paramStr;
            } else {
                tempParamStr = tempParamStr * -1;
                returnValue = GlobalMemberValues.getStringFormatNumber(tempParamStr, "2");
            }
        }

        return returnValue;
    }

    public void openCashDrawerElo(){
        GlobalMemberValues.eloOpenDrawer(apiAdapter);
    }

    public void printBatchElo(final JSONObject data) {

        if (productInfo == null) {
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
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);

                String nowDate = getDate();
                String nowTime = getTime();

                try {
                    str_saledate = data.getString("saledate");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                textlist = "Sale Date: " + str_saledate;
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);

//                list.add("\t\tTime: ".getBytes());
//                list.add(nowTime.getBytes());

                textlist = "";
                textlist += "\n================================\r\n";
                textlist += "Emp.Name         Tip    Sale.Amt\r\n";
                //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                textlist += "--------------------------------\r\n\r\n";

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
                        textlist += Payment_stringBackSpace_Exch(15, str_emp_empsalesempname);
                        textlist += Payment_stringBackSpace_Exch(5, str_emp_empsalestip);
                        //textlist += Payment_stringBackSpace_Exch(5, str_emp_empsalescomm);
                        textlist += (Payment_stringBackSpace_Exch(11, str_emp_empsalessalesamt)) + "\r\n";

                    }

                    textlist += "\n================================\r\n";
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

                textlist += "SERVICE \t\t            ";
                textlist += Payment_stringFowardSpace_Exch(12, str_service);
                textlist +="\n";
                textlist += "PRODUCT \t\t            ";
                textlist += Payment_stringFowardSpace_Exch(12, str_product);
                textlist += "\n";
                textlist += "GIFT CARD \t\t          ";
                textlist += Payment_stringFowardSpace_Exch(12, str_giftcard);
                textlist += "\n";
                textlist += "DISCOUNT \t\t           ";
                textlist += Payment_stringFowardSpace_Exch(12, str_discount);
                textlist += "\n";
                textlist += "PICK UP FEE \t\t        ";
                textlist += Payment_stringFowardSpace_Exch(12, str_pickupfee);
                textlist += "\n";
                textlist += "DELIVERY FEE \t\t       ";
                textlist += Payment_stringFowardSpace_Exch(12, str_deliveryfee);
                textlist += "\n";
                textlist += "VOID \t\t               ";
                textlist += Payment_stringFowardSpace_Exch(12, str_void);
                textlist += "\n================================\r\n";
//
                textlist += "NET SALES \t\t          ";
                textlist += Payment_stringFowardSpace_Exch(12, str_netsales);
                textlist += "REFUND \t\t             ";
                textlist += Payment_stringFowardSpace_Exch(12, str_refund);
                textlist += "TAX \t\t                ";
                textlist += Payment_stringFowardSpace_Exch(12, str_tax);
                textlist += "GROSS SALES \t\t        ";
                textlist += Payment_stringFowardSpace_Exch(12, str_grosssales);
                textlist += "\n================================\r\n";

                textlist += "CASH \t\t               ";
                textlist += Payment_stringFowardSpace_Exch(12, str_cash);
                textlist += "CREDIT CARD \t\t        ";
                textlist += Payment_stringFowardSpace_Exch(12, str_card);
                textlist += "CREDIT CARD TIP \t\t    ";
                textlist += Payment_stringFowardSpace_Exch(12, str_cardtip);
                textlist += "       VISA \t\t        ";
                textlist += str_cnt_visa + "   ";
                textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardvisa)) + "\n";
                textlist += "     MASTER \t\t        ";
                textlist += str_cnt_master + "   ";
                textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardmaster)) + "\n";
                textlist += "       AMEX \t\t        ";
                textlist += str_cnt_amex + "   ";
                textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardamex)) +"\n";
                textlist += "   DISCOVER \t\t        ";
                textlist += str_cnt_discover + "   ";
                textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_carddiscover)) +"\n";
                textlist += "   OFF CARD \t\t        ";
                textlist += str_cnt_offcard + "   ";
                textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardoffcard)) + "\n";

                textlist += "GIFT CARD REDEEMED \t\t ";
                textlist += Payment_stringFowardSpace_Exch(12, str_giftcard2) + "\n";
                textlist += "POINT \t\t              ";
                textlist += Payment_stringFowardSpace_Exch(12, str_point) + "\n";
                textlist += "OTHER \t\t              ";
                textlist += Payment_stringFowardSpace_Exch(12, str_other);
                textlist += "\n================================\r\n";
                textlist += "Printed \t ";
                textlist += nowTime + "  " + nowDate;


//                sendCommand(list, context);
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);

                //GlobalMemberValues.eloOpenDrawer(apiAdapter);
            }

        };
        thread.start();

    }

    public void printCashOutElo(final JSONObject data) {

        if (productInfo == null){
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
                    GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                } else {
                    textlist =  "[STARTING CASH REPORT]\r\n\r\n";
                    GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                }

                str_cashinoutemployeenameyn = GlobalMemberValues.getDataInJsonData(data, "cashinoutemployeenameyn");
                if (str_cashinoutemployeenameyn == "Y" || str_cashinoutemployeenameyn.equals("Y")) {
                    // Admin 이 직원별로 Cash In/Out 기록을 출력할 때 ------------------------------------
                    str_cashinoutemployeename = GlobalMemberValues.getDataInJsonData(data, "employeename");
                    textlist = "Employee Name : " + str_cashinoutemployeename;
                    GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                    // ---------------------------------------------------------------------------------
                } else {
                    textlist = "Employee Name : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "\n";
                    GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                }

                String nowDate = getDate();
                String nowTime = getTime();

                str_cashinoutdate = GlobalMemberValues.getDataInJsonData(data, "cashinoutdate");
                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    textlist = "\nCash Out Date : " + str_cashinoutdate;
                    GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                }

//                list.add("\t\tTime: ".getBytes());
//                list.add(nowTime.getBytes());

                if (str_startingcashprintyn == "N" || str_startingcashprintyn.equals("N")) {
                    // 직원 로그인/아웃 기록 출력 --------------------------------------------------------
                    textlist = "";
                    textlist += "\n\n[Log in/out data]\r\n";
                    textlist += "================================\r\n";
                    textlist += "Type                Date        \r\n";
                    //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                    textlist += "--------------------------------\r\n";

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
                        textlist += "================================\r\n";
                    }
                    // ---------------------------------------------------------------------------------
                }

                // 직원 Cash In 기록 출력 -----------------------------------------------------------
                textlist += "\n[Cash in data]\r\n";
                textlist += "================================\r\n";
                //textlist += "Reason                            Amount\r\n";
                //textlist += "Emp.Name     Qty   Tip   Comm   Sale.Amt\r\n";
                //textlist += "--------------------------------\r\n";

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
                    textlist += "================================\r\n";
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
                    textlist += "SALES TOTAL \t\t        ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_totalsaleamount);
                    textlist += "\n";
                    //textlist += "COMMISSION \t\t         ";
                    //textlist += Payment_stringFowardSpace_Exch(12, str_commissionamount);
                    //textlist += "\n";
                    textlist += "DELIVERY/PICKUP \t      ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_deliverypickupfeeamount);
                    textlist += "\n";
                    textlist += "TIP \t\t                ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_tipamount);
                    textlist += "\n";
                    textlist += "PICK UP FEE \t\t        ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_pickupfeeamount);
                    textlist += "\n";
                    textlist += "DELIVERY FEE \t\t       ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_deliveryfeeamount);
                    textlist += "\n================================\r\n";

                    textlist += "FOOD \t\t               ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_foodsaleamount);
                    textlist += "\n";
                    textlist += "PRODUCT \t\t            ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_productsaleamount);
                    textlist += "\n";
                    textlist += "GIFT CARD \t\t          ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_giftcardsaleamount);
                    textlist += "\n";
                    textlist += "DISCOUNT \t\t           ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_discountamount);
                    textlist += "\n";
                    textlist += "VOID \t\t               ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_voidamount);
                    textlist += "\n================================\r\n";
//
                    textlist += "NET SALES \t\t          ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_netsalesamount);
                    textlist += "\n";
                    textlist += "REFUND \t\t             ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_refundamount);
                    textlist += "\n";
                    textlist += "TAX \t\t                ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_taxamount);
                    textlist += "\n";
                    textlist += "GROSS SALES \t\t        ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_grosssalesamount);
                    textlist += "\n================================\r\n";

                    textlist += "SALES CASH \t\t         ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salescashtotal);
                    textlist += "\n";
                    textlist += "CASH IN TOTAL \t\t      ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_cashintotal);
                    textlist += "\n";
                    textlist += "CASH OUT TOTAL \t\t      ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_cashouttotal);
                    textlist += "\n";
                    textlist += "ALL CASH TOTAL \t\t     ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_allcashtotal);
                    textlist += "END CASH       \t\t     ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_endcashtotal);
                    textlist += "\n";
                    textlist += str_overshorttxt.toUpperCase() + "      \t\t     ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_overshortamt);
                    textlist += "\n================================\r\n";

                    textlist += "CREDIT CARD \t\t        ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salecardtotal) + "\n";
                    textlist += "CREDIT CARD TIP \t\t    ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salecardtiptotal) + "\n";
                    textlist += "       VISA \t\t        ";
                    textlist += str_cnt_visa + "   ";
                    textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardvisa)) + "\n";
                    textlist += "     MASTER \t\t        ";
                    textlist += str_cnt_master + "   ";
                    textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardmaster)) + "\n";
                    textlist += "       AMEX \t\t        ";
                    textlist += str_cnt_amex + "   ";
                    textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardamex)) +"\n";
                    textlist += "   DISCOVER \t\t        ";
                    textlist += str_cnt_discover + "   ";
                    textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_carddiscover)) +"\n";
                    textlist += "   OFF CARD \t\t        ";
                    textlist += str_cnt_offcard + "   ";
                    textlist += Payment_stringFowardSpace_Exch(8, cardAmountString(str_cardoffcard)) + "\n";

                    textlist += "OTHER \t\t              ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salechecktotal);
                    textlist += "\n";
                    textlist += "GIFT CARD REDEEMED \t\t ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salegiftcardtotal);
                    textlist += "\n";
                    textlist += "POINT \t\t              ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_salepointtotal);
                    textlist += "\n================================\r\n";
                }

                textlist += "Printed   \t";
                textlist += nowTime + "  " + nowDate;

//                sendCommand(list, context);
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
            }

        };
        thread.start();

    }

    public void printBatchDetailElo(final JSONObject data) {

        if (productInfo == null){
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
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);

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
                textlist += "BATCH # : \t\t          ";
                textlist += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForInteger(str_batchnumber));
                textlist +="\n";
                textlist += "TOTAL CARD COUNT \t\t   ";
                textlist += Payment_stringFowardSpace_Exch(12, str_totalcardcount);
                textlist += "\n";
                textlist += "TOTAL CARD AMT.\t\t     ";
                textlist += Payment_stringFowardSpace_Exch(12, str_totalcardamount);
                textlist += "\n";
                textlist += "TOTAL CARD TIP \t\t     ";
                textlist += Payment_stringFowardSpace_Exch(12, str_totalcardtip);
                textlist += "\n========= TYPE OF CARD =========\r\n";//
                if (!GlobalMemberValues.isStrEmpty(str_visaamount)) {
                    textlist += "     VISA \t\t          ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_visaamount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_masteramount)) {
                    textlist += "\n";
                    textlist += "     MASTER \t\t        ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_masteramount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_amexamount)) {
                    textlist += "\n";
                    textlist += "     AMEX \t\t          ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_amexamount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_discoveramount)) {
                    textlist += "\n";
                    textlist += "     DISCOVER \t\t      ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_discoveramount);
                }
                if (!GlobalMemberValues.isStrEmpty(str_totalcardtip)) {
                    textlist += "\n";
                    textlist += "     TIP \t\t           ";
                    textlist += Payment_stringFowardSpace_Exch(12, str_totalcardtip);
                }
                textlist += "\n================================\r\n";
                textlist += "Batched : ";
                textlist += str_batchdate;
                textlist += "\nPrinted : ";
                textlist += nowDate + " " + nowTime;

//                sendCommand(list, context);
                GlobalMemberValues.eloPrintText(textlist, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
            }

        };
        thread.start();

    }

    private void ShowDialogInElo(final JSONObject paramJsonData, String paramTitle, String paramContents,
                                 String paramYesButtonText, String paramNoButtonText,
                                 final String paramYesActionType, final String paramNoActionType) {

        LayoutInflater dialog = LayoutInflater.from(mContext);
        final View dialogLayout = dialog.inflate(R.layout.jjjdialog, null);
        //final Dialog myDialog = new Dialog(Payment.context);

        final Dialog myDialog = new Dialog(mContext,
                android.R.style.Theme_NoTitleBar_Fullscreen|android.R.style.Theme_Translucent);

        /** 화면크기 조정하는 ...
         WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
         Window window = myDialog.getWindow();
         layoutParams.copyFrom(window.getAttributes());
         layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
         layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
         window.setAttributes(layoutParams);
         **/

        myDialog.setTitle(paramTitle);
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        TextView jjjdialogContentsTextView = (TextView)dialogLayout.findViewById(R.id.jjjdialogContentsTextView);
        jjjdialogContentsTextView.setText(paramContents);

        Button jjjdialogNoBtn = (Button)dialogLayout.findViewById(R.id.jjjdialogNoBtn);
        jjjdialogNoBtn.setText(paramNoButtonText);
        jjjdialogNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NO 눌렀을 때
                switch (paramNoActionType) {
                    case "openpaymentreview1" : {
                        // Reprint 가 아닐 경우에만...
                        if (GlobalMemberValues.mReReceiptprintYN == "N" || GlobalMemberValues.mReReceiptprintYN.equals("N")) {
                            executeKitchenPrint(paramJsonData);
                            if (GlobalMemberValues.isCustomerSelectReceipt()) {
                                if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {
                                    PaymentCustomerSelectReceipt.finishPayment();
                                    GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
                                }
                            }
                        }
                        break;
                    }
                    case "openpaymentreview2" : {
                        // Reprint 가 아닐 경우에만...
                        if (GlobalMemberValues.mReReceiptprintYN == "N" || GlobalMemberValues.mReReceiptprintYN.equals("N")) {
                            Payment.openPaymentReview(mContext);
                            if (GlobalMemberValues.isCustomerSelectReceipt()) {
                                if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {
                                    PaymentCustomerSelectReceipt.finishPayment();
                                    GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
                                }
                            }
                        }
                        break;
                    }
                    case "openpaymentreview_void" : {
                        break;
                    }
                    case "openpaymentreview_return" : {
                        SaleHistory.mActivity.recreate();
                        break;
                    }
                }
                myDialog.cancel();
            }
        });

        Button jjjdialogYesBtn = (Button)dialogLayout.findViewById(R.id.jjjdialogYesBtn);
        jjjdialogYesBtn.setText(paramYesButtonText);
        jjjdialogYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NEXT 눌렀을 때
                switch (paramYesActionType) {
                    case "merchantcopy" : {
                        printSalesReceiptElo1(paramJsonData);
                        break;
                    }
                    case "customercopy" : {
                        printSalesReceiptElo2(paramJsonData);
                        break;
                    }
                    case "customercopy_void" : {
                        printVoidReceiptElo2(paramJsonData);
                        break;
                    }
                    case "customercopy_return" : {
                        printReturnReceiptElo2(paramJsonData);
                        break;
                    }
                    case "kitchenprint" : {
                        if (GlobalMemberValues.isCustomerSelectReceipt()) {
                            return;
                        }

                        if (GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                                "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
                            try {
                                Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                        } else {
                            GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";
                            Payment.openPaymentReview(mContext);
                        }

                        break;
                    }
                }
                myDialog.cancel();
            }
        });
    }



    public void printSalesReceiptElo(final JSONObject data) {
        if (productInfo == null) {
            return;
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final double[] mTotalTipAmount = {0};

        mCardPaymentCount = 0;

        Thread thread1 = new Thread(){
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

                if (jsonArray_creditcardtransaction.length() != 0) {
                    titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                    titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                            + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                            + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                    String[] tempEmpNames = str_employeename.split(",");
                    String tempEmpStr = "";
                    GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                    if (tempEmpNames.length > 1) {
                        tempEmpStr = "\n[Employee Name]\n";
                    } else {
                        tempEmpStr = "\nEmployee Name : ";
                    }

                    titleText2 = tempEmpStr + str_employeename + "\nCustomer Name : " + str_customername + "\nReceipt# : "
                            + str_receiptno + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";
                    //titleText2 = tempEmpStr + str_employeename + "\nCustomer Name : " + str_customername + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";

                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        // 프린트 실행 (1) ------------------------------------------------------------
                        GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                        GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
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

                        merchantText += "*** Credit Card Transaction ****\r\n";
                        customerText += "*** Credit Card Transaction ****\r\n";

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
                        merchantText += "--------------------------------\r\n";
                        customerText += "--------------------------------\r\n";

                        str_receiptprinttype = "3";


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
                                customerText += "\t         Tip : __________________\n";
                                customerText += " Grand Total : _________________\r\n\r";
                            }
                        }

                        // 프린트 실행 (2) --------------------------------------------------------------------
                        GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                        // -----------------------------------------------------------------------------------

                        if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                            str_signatureTxt = "\n\r\n";
                            str_signatureTxt += " Signature ____________________\r\n\r\n";
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
                            // 프린트 실행 (2) --------------------------------------------------------------------
                            str_signatureTxt = "\n\r        ";
                            str_signatureTxt += "         \r\n";
                            GlobalMemberValues.eloPrintText(str_signatureTxt, myPrinter, apiAdapter);
                            // -----------------------------------------------------------------------------------

                            str_signatureTxt = "";

                            if (GlobalMemberValues.isSignedPrintYN()) {
                                // 서명 이미지 출력
                                GlobalMemberValues.eloPrintToFilePath(strFilePath, myPrinter, apiAdapter);

                                if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                    str_signatureTxt = "\r\n";
                                    str_signatureTxt += " Signature ____________________\r\n\r\n";
                                }
                            }
                        } else {
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // -----------------------------------------------------------------------------------------------------------------

                        // 프린트 실행 (3) --------------------------------------------------------------------
                        GlobalMemberValues.eloPrintText(str_signatureTxt, myPrinter, apiAdapter);
                        // -----------------------------------------------------------------------------------

                        // receipt footer 프린터 ----------------------------------------------------------------------------------
                        if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                            GlobalMemberValues.eloPrintText(GlobalMemberValues.getPrintTxtCenterAlignment(str_receiptfooter, 32), myPrinter, apiAdapter);
                        }
                        // --------------------------------------------------------------------------------------------------------

                        customerText = "######### Merchant Copy ########\r\n";
                        customerText += "* Thank you. Come again soon. *\r\n";
                        customerText += "printed : ";
                        customerText += getDate() + " " + getTime();

                        // merchant 프린트 -----------------------------------------------------------------------
                        GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                        GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                        // --------------------------------------------------------------------------------------

                        mCardPaymentCount++;
                    }
                }
            }
        };

        if (GlobalMemberValues.isSignedPrintSeperately()) {
            thread1.start();
            try {
                thread1.join();
            } catch(InterruptedException e) {}

            // 어느정도(GlobalMemberValues.DELETEWAITING) 기다렸다가 아래 서명파일 삭제
            try {
                GlobalMemberValues.logWrite("waitingtime", "기다리는 시간2 : " + GlobalMemberValues.DELETEWAITING + "\n");
                Thread.sleep(GlobalMemberValues.DELETEWAITING);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (mCardPaymentCount > 0 && GlobalMemberValues.isSignedPrintSeperately()) {
            // 커스텀 Alert 띄우기.. ----------------------------------------------------------------
            JSONObject tempDialogJsonData = data;
            String tempDialogTitle = "Next Printing";
            String tempDialogContents = "Do you want to print next? (merchant copy)";
            String tempDialogYesActionType = "merchantcopy";
            String tempDialogNoActionType = "openpaymentreview1";
            String tempDialogYesButtonText = "PRINT MERCHANT COPY";
            String tempDialogNoButtonText = "STOP";
            ShowDialogInElo(
                    tempDialogJsonData,
                    tempDialogTitle,
                    tempDialogContents,
                    tempDialogYesButtonText,
                    tempDialogNoButtonText,
                    tempDialogYesActionType,
                    tempDialogNoActionType);
            // -------------------------------------------------------------------------------------
        } else {
            printSalesReceiptElo1(data);
        }
    }

    public void printSalesReceiptElo1(final JSONObject data) {
        if (productInfo == null) {
            return;
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final double[] mTotalTipAmount = {0};

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

                    if (data.getJSONArray("saleitemlist") != null) {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction")) {
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

                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.eloPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n", myPrinter, apiAdapter);
                }

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
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

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                        // " Item      Qty   Price    Amount\r\n" +
                        // "--------------------------------\r\n";

                if (jsonArray_saleitemlist.length() != 0) {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++) {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemdcextrastr = dic_item.getString("itemdcextrastr");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");

                            if (dic_item.toString().contains("optiontxt")) {
                                str_optiontxt = dic_item.getString("optiontxt");
                            }
                            if (dic_item.toString().contains("optionprice")) {
                                str_optionprice = dic_item.getString("optionprice");
                            }
                            if (dic_item.toString().contains("additionaltxt1")) {
                                str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            }
                            if (dic_item.toString().contains("additionalprice1")) {
                                str_additionalprice1 = dic_item.getString("additionalprice1");
                            }
                            if (dic_item.toString().contains("additionaltxt2")) {
                                str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            }
                            if (dic_item.toString().contains("additionalprice2")) {
                                str_additionalprice2 = dic_item.getString("additionalprice2");
                            }

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
                        main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                                Payment_stringFowardSpace_Exch(3, str_itemqty) +
                                Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      " + str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12,str_subtotal) + "\n";

                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,str_totalextradiscountprice) + "\n";
                    }
                }
                main_Text +=  "Tax \t\t               ";
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
                main_Text += "--------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_creditcardtendered) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_giftcardtendered) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_checktendered) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_pointtendered) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_cashtendered) + "\n";
                }

                main_Text += "Change \t\t            ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_change) + "\n";
                main_Text += "--------------------------------\r\n";
                if (str_reprintyn == "Y" || str_reprintyn.equals("Y")) {
                    if (!GlobalMemberValues.isStrEmpty(str_tipamount) && GlobalMemberValues.getDoubleAtString(str_tipamount) > 0) {
                        main_Text += "Tip \t\t               ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_tipamount) + "\n";
                        main_Text += "--------------------------------\r\n";
                    }
                } else {
                    if (mTotalTipAmount[0] > 0) {
                        main_Text += "Tip \t\t               ";
                        main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(mTotalTipAmount[0] + "")) + "\n";
                        main_Text += "--------------------------------\r\n";
                    }
                }

                // 프린트 실행 (1) ------------------------------------------------------------
                GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                // ---------------------------------------------------------------------------

                // card
                if (jsonArray_creditcardtransaction.length() != 0) {
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

                        customerText += "*** Credit Card Transaction ****\r\n";

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
                        customerText += "--------------------------------\r\n";

                        // 프린트 실행 (2) --------------------------------------------------------------------
                        GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
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

                                        if (new File(strFilePath).exists()) {
                                            tempArrListStrFile.add(strFilePath);
                                        }
                                    }
                                }
                            }
                            GlobalMemberValues.logWrite("signatureimagepath", "strFilePath : " + strFilePath + "\n");
                        } else {
                            String cardsignedtxt =  "";

                            //customer copy
                            //merchant copy
                            if (GlobalMemberValues.getDoubleAtString(str_cardtipamount) > 0) {
                                double tempTotalAmount =
                                        GlobalMemberValues.getDoubleAtString(str_cardchangeamount) + GlobalMemberValues.getDoubleAtString(str_cardtipamount);
                                if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                    cardsignedtxt += "Tip    \t\t            ";
                                    cardsignedtxt += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(str_cardtipamount + "")) + "\n";

                                    cardsignedtxt += "Grand Total Amount \t\t";
                                    cardsignedtxt += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + "")) + "\n";
                                }

                                mTotalTipAmount[0] = mTotalTipAmount[0] + GlobalMemberValues.getDoubleAtString(str_cardtipamount);

                            } else {
                                if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                    if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                        cardsignedtxt += "\t         Tip : __________________\n";
                                        cardsignedtxt += " Grand Total : _________________\r\n\r";
                                    }
                                }
                            }

                            // 프린트 실행 (2) --------------------------------------------------------------------
                            GlobalMemberValues.eloPrintText(cardsignedtxt, myPrinter, apiAdapter);
                            // -----------------------------------------------------------------------------------

                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                str_signatureTxt = "\n\r\n";
                                str_signatureTxt += " Signature ____________________\r\n\r\n";
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
                                // 프린트 실행 (2) --------------------------------------------------------------------
                                str_signatureTxt = "\n\r        ";
                                str_signatureTxt += "         \r\n";

                                if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                    GlobalMemberValues.eloPrintText(str_signatureTxt, myPrinter, apiAdapter);
                                }
                                // -----------------------------------------------------------------------------------

                                str_signatureTxt = "";

                                if (GlobalMemberValues.isSignedPrintYN()) {
                                    // 서명 이미지 출력
                                    GlobalMemberValues.eloPrintToFilePath(strFilePath, myPrinter, apiAdapter);

                                    if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                        str_signatureTxt = "\r\n";
                                        str_signatureTxt += " Signature ____________________\r\n\r\n";
                                    }
                                }
                            } else {
                                try {
                                    Thread.sleep(2500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            // -----------------------------------------------------------------------------------------------------------------

                            // 프린트 실행 (3) --------------------------------------------------------------------
                            if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                                GlobalMemberValues.eloPrintText(str_signatureTxt, myPrinter, apiAdapter);
                            }
                            // -----------------------------------------------------------------------------------
                        }
                    }
                }

                customerText = "######### Merchant Copy ########\r\n";
                customerText += "* Thank you. Come again soon. *\r\n";
                customerText += "printed : ";
                customerText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                // receipt footer 프린터 ----------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                    GlobalMemberValues.eloPrintText(GlobalMemberValues.getPrintTxtCenterAlignment(str_receiptfooter, 32), myPrinter, apiAdapter);
                }
                // --------------------------------------------------------------------------------------------------------

                // customer 프린트 -----------------------------------------------------------------------
                GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                // --------------------------------------------------------------------------------------

                // 돈통 열기 cash drawer
                if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
                    try {
                        if (data.get("totalamount").equals(data.get("creditcardtendered"))){

                        } else {
                            GlobalMemberValues.eloOpenDrawer(apiAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    GlobalMemberValues.eloOpenDrawer(apiAdapter);
                }
            }
        };

        thread2.start();
        try {
            thread2.join();
        } catch(InterruptedException e) {}

        // 어느정도(GlobalMemberValues.DELETEWAITING) 기다렸다가 아래 서명파일 삭제
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간2 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(GlobalMemberValues.DELETEWAITING);
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

        // 커스텀 Alert 띄우기.. ----------------------------------------------------------------
        JSONObject tempDialogJsonData = data;
        String tempDialogTitle = "Next Printing";
        String tempDialogContents = "Do you want to print next? (customer copy)";
        String tempDialogYesActionType = "customercopy";
        String tempDialogNoActionType = "openpaymentreview1";
        String tempDialogYesButtonText = "PRINT CUSTOMER COPY";
        String tempDialogNoButtonText = "STOP";
        ShowDialogInElo(
                tempDialogJsonData,
                tempDialogTitle,
                tempDialogContents,
                tempDialogYesButtonText,
                tempDialogNoButtonText,
                tempDialogYesActionType,
                tempDialogNoActionType);
        // -------------------------------------------------------------------------------------
    }

    public void printSalesReceiptElo2(final JSONObject data) {
        if (productInfo == null) {
            return;
        }

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final double[] mTotalTipAmount = {0};

        final ArrayList<String> tempArrListStrFile = new ArrayList<String>();
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

                    if (data.getJSONArray("saleitemlist") != null) {
                        jsonArray_saleitemlist = data.getJSONArray("saleitemlist");
                    }
                    if (data.toString().contains("creditcardtransaction")) {
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

                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.eloPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(str_customerPagerNumber + "\n", myPrinter, apiAdapter);
                }

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
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

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                // " Item      Qty   Price    Amount\r\n" +
                // "--------------------------------\r\n";

                if (jsonArray_saleitemlist.length() != 0) {
                    int cnt = jsonArray_saleitemlist.length();
                    for (int i = 0; i < cnt; i++) {
                        try {
                            JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);
                            str_itemqty = dic_item.getString("itemqty");
                            str_itemname = dic_item.getString("itemname");
                            str_itemamount = dic_item.getString("itemamount");
                            str_itemprice = dic_item.getString("itemprice");
                            str_itemdcextrastr = dic_item.getString("itemdcextrastr");
                            str_itemtaxexempt = dic_item.getString("itemtaxexempt");

                            if (dic_item.toString().contains("optiontxt")) {
                                str_optiontxt = dic_item.getString("optiontxt");
                            }
                            if (dic_item.toString().contains("optionprice")) {
                                str_optionprice = dic_item.getString("optionprice");
                            }
                            if (dic_item.toString().contains("additionaltxt1")) {
                                str_additionaltxt1 = dic_item.getString("additionaltxt1");
                            }
                            if (dic_item.toString().contains("additionalprice1")) {
                                str_additionalprice1 = dic_item.getString("additionalprice1");
                            }
                            if (dic_item.toString().contains("additionaltxt2")) {
                                str_additionaltxt2 = dic_item.getString("additionaltxt2");
                            }
                            if (dic_item.toString().contains("additionalprice2")) {
                                str_additionalprice2 = dic_item.getString("additionalprice2");
                            }

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
                         main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
                            main_Text += "" + str_kitchenmemo_txt + "\n";
                        }

                        if (TextUtils.isEmpty(str_itemdcextrastr)){
                        }else {
                            main_Text += "      " + str_itemdcextrastr + "\n";
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_optiontxt) || !GlobalMemberValues.isStrEmpty(str_additionaltxt1) || !GlobalMemberValues.isStrEmpty(str_additionaltxt2)
                                || !GlobalMemberValues.isStrEmpty(str_kitchenmemo_txt) || !GlobalMemberValues.isStrEmpty(str_itemdcextrastr)) {
                            main_Text += "\n";
                        }
                    }
                }

                if (TextUtils.isEmpty(str_alldiscountextrstr)){
                }else {
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12,str_subtotal) + "\n";

                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,str_totalextradiscountprice) + "\n";
                    }
                }
                main_Text +=  "Tax \t\t               ";
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
                main_Text += "--------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_creditcardtendered) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_giftcardtendered) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_checktendered) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_pointtendered) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12,str_cashtendered) + "\n";
                }

                main_Text += "Change \t\t            ";
                main_Text += Payment_stringFowardSpace_Exch(12,str_change) + "\n";
                main_Text += "--------------------------------\r\n";
                if (str_reprintyn == "Y" || str_reprintyn.equals("Y")) {
                    if (!GlobalMemberValues.isStrEmpty(str_tipamount) && GlobalMemberValues.getDoubleAtString(str_tipamount) > 0) {
                        main_Text += "Tip \t\t               ";
                        main_Text += Payment_stringFowardSpace_Exch(12,str_tipamount) + "\n";
                        main_Text += "--------------------------------\r\n";
                    }
                } else {
                    if (mTotalTipAmount[0] > 0) {
                        main_Text += "Tip \t\t               ";
                        main_Text += Payment_stringFowardSpace_Exch(12, GlobalMemberValues.getCommaStringForDouble(mTotalTipAmount[0] + "")) + "\n";
                        main_Text += "--------------------------------\r\n";
                    }
                }

                // card
                if (jsonArray_creditcardtransaction.length() != 0) {
                    int cnt = jsonArray_creditcardtransaction.length();
                    for (int i = 0; i < cnt; i++) {
                        String strFilePath = "";        // 서명 이미지 경로

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

                        merchantText += "*** Credit Card Transaction ****\r\n";

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
                        merchantText += "--------------------------------\r\n";

                        // 프린트 실행 (2) --------------------------------------------------------------------
                        //GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                        // -----------------------------------------------------------------------------------

                        str_receiptprinttype = "3";


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

                                    if (new File(strFilePath).exists()) {
                                        tempArrListStrFile.add(strFilePath);
                                    }
                                }
                            }
                        }
                        GlobalMemberValues.logWrite("signatureimagepath", "strFilePath : " + strFilePath + "\n");

                        if (str_reprintyn == "N" || str_reprintyn.equals("N")) {
                            if (GlobalMemberValues.getDoubleAtString(str_cardtipamount) > 0) {
                                merchantText += "Tip \t\t               ";
                                merchantText += Payment_stringFowardSpace_Exch(12,GlobalMemberValues.getCommaStringForDouble(str_cardtipamount)) + "\n";
                                merchantText += "--------------------------------\r\n";
                            }
                        }
                    }
                }

                merchantText2 = "######### Customer Copy ########\r\n";
                merchantText2 += "* Thank you. Come again soon. *\r\n";
                merchantText2 += "printed : ";
                merchantText2 += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                // merchant 프린트 -----------------------------------------------------------------------
                GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);

                GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);

                // receipt footer 프린터 ----------------------------------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(str_receiptfooter)) {
                    GlobalMemberValues.eloPrintText(GlobalMemberValues.getPrintTxtCenterAlignment(str_receiptfooter, 32), myPrinter, apiAdapter);
                }
                // --------------------------------------------------------------------------------------------------------

                GlobalMemberValues.eloPrintText(merchantText2, myPrinter, apiAdapter);
                GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                // --------------------------------------------------------------------------------------
            }
        };

        // 돈통 열기 cash drawer
        if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
//            GlobalMemberValues.eloOpenDrawer(apiAdapter);
            try {
                if (data.get("totalamount").equals(data.get("creditcardtendered"))){

                } else {
                    GlobalMemberValues.eloOpenDrawer(apiAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // merchant 영수증만 프린트할 경우에는 customer 용 프린트를 실행하지 않는다.
        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
            thread.start();
        }

        // reprint 일 경우...
        if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
            GlobalMemberValues.mReReceiptprintYN = "N";
            return;
        }

        // 키친프린터 실행
        executeKitchenPrint(data);

        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {
                PaymentCustomerSelectReceipt.finishPayment();
                GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
            }
        }
    }

    public void executeKitchenPrint(JSONObject paramData) {
        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            return;
        }

        if (GlobalMemberValues.PHONEORDERYN.equals("Y") && GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT.equals("N")) {
            GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";
            Payment.openPaymentReview(MainActivity.mContext);
            return;
        }

        if (GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {

            try {
                Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 영수증 프린팅과 키친 프린팅이 모두 Elo 일 때... -------------------------------------------------------------
            if (GlobalMemberValues.isEloPrinterAll()) {
                // 커스텀 Alert 띄우기.. ----------------------------------------------------------------
                JSONObject tempDialogJsonData = paramData;
                String tempDialogTitle = "Next Printing";
                String tempDialogContents = "Do you want to print next in kitchen?";
                String tempDialogYesActionType = "kitchenprint";
                String tempDialogNoActionType = "openpaymentreview2";
                String tempDialogYesButtonText = "KITCHEN PRINT";
                String tempDialogNoButtonText = "STOP";
                ShowDialogInElo(
                        tempDialogJsonData,
                        tempDialogTitle,
                        tempDialogContents,
                        tempDialogYesButtonText,
                        tempDialogNoButtonText,
                        tempDialogYesActionType,
                        tempDialogNoActionType);
                // -------------------------------------------------------------------------------------

            } else {
                if (!GlobalMemberValues.isEloPrinterKitchen()) {
                    Payment.openPaymentReview(mContext);
                    GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, mContext, "kitchen1");
                }
            }
        } else {
            GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";
            Payment.openPaymentReview(mContext);
        }
    }

    public void printVoidReceiptElo(final JSONObject data) {
        if (productInfo == null){
            return;
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
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
                String str_customerPagerNumber = "";

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

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = data.getString("customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
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

                if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                    GlobalMemberValues.eloPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(str_customerPagerNumber + "\n", myPrinter, apiAdapter);
                }

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
                }
                // -----------------------------------------------------------------------------------

                titleText1 += "\n" +
                        ">>>>>>>>>>>   VOID   <<<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                // " Item      Qty   Price    Amount\r\n" +
                // "--------------------------------\r\n";
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
                         main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                                main_Text += ""+str_option_additional_txt + "\n";
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
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
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t               ";

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

                main_Text += "--------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

                main_Text += "--------------------------------\r\n";

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

                        merchantText += "*** Credit Card Transaction ****\r\n";
                        customerText += "*** Credit Card Transaction ****\r\n";

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
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";

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
                        merchantText += "--------------------------------\r\n";
                        customerText += "--------------------------------\r\n";



                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : __________________  \n";
                                merchantText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ____________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : __________________  \n";
                                customerText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ____________________\r\n\r\n";
                            }
                        } else {

                        }
                    }
                }

                customerText += "######### Customer Copy ########\r\n";
                customerText += "* Thank you. Come again soon. *\r\n";
                customerText += "printed : ";
                customerText += getDate() + " " + getTime();

                merchantText += "######### Merchant Copy ########\r\n";
                merchantText += "* Thank you. Come again soon. *\r\n";
                merchantText += "printed : ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else {
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                }

                GlobalMemberValues.eloOpenDrawer(apiAdapter);
            }
        };
        thread.start();

        // 커스텀 Alert 띄우기.. ----------------------------------------------------------------
        JSONObject tempDialogJsonData = data;
        String tempDialogTitle = "Next Printing";
        String tempDialogContents = "Do you want to print next? (customer copy)";
        String tempDialogYesActionType = "customercopy_void";
        String tempDialogNoActionType = "openpaymentreview_void";
        String tempDialogYesButtonText = "PRINT CUSTOMER COPY (VOID)";
        String tempDialogNoButtonText = "STOP";
        ShowDialogInElo(
                tempDialogJsonData,
                tempDialogTitle,
                tempDialogContents,
                tempDialogYesButtonText,
                tempDialogNoButtonText,
                tempDialogYesActionType,
                tempDialogNoActionType);
        // -------------------------------------------------------------------------------------
    }

    public void printVoidReceiptElo2(final JSONObject data) {
        if (productInfo == null){
            return;
        }

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
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

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
                }
                // -----------------------------------------------------------------------------------
                titleText1 += "\n" +
                        ">>>>>>>>>>>   VOID   <<<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nSale Date     : " + str_saledate + " " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                // " Item      Qty   Price    Amount\r\n" +
                // "--------------------------------\r\n";
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
                         main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                                main_Text += ""+str_option_additional_txt + "\n";
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
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
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t               ";

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

                main_Text += "--------------------------------\r\n";

                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

                main_Text += "--------------------------------\r\n";

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

                        merchantText += "*** Credit Card Transaction ****\r\n";
                        customerText += "*** Credit Card Transaction ****\r\n";

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
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";

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
                        merchantText += "--------------------------------\r\n";
                        customerText += "--------------------------------\r\n";



                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : __________________  \n";
                                merchantText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ____________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : __________________  \n";
                                customerText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ____________________\r\n\r\n";
                            }
                        } else {

                        }
                    }
                }

                customerText += "######### Customer Copy ########\r\n";
                customerText += "* Thank you. Come again soon. *\r\n";
                customerText += "printed : ";
                customerText += getDate() + " " + getTime();

                merchantText += "######### Merchant Copy ########\r\n";
                merchantText += "* Thank you. Come again soon. *\r\n";
                merchantText += "printed : ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else {
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                }

                GlobalMemberValues.eloOpenDrawer(apiAdapter);
            }
        };
        thread.start();
    }

    public void printReturnReceiptElo(final JSONObject data) {
        if (productInfo == null){
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

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
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

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
                }
                // -----------------------------------------------------------------------------------
                titleText1 += "\n" +
                        ">>>>>>>>>>   RETURN   <<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name     : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nRefund Date   : " + str_saledate + "\nRefund Time   : " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nRefund Date   : " + str_saledate + "\nRefund Time   : " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                // " Item      Qty   Price    Amount\r\n" +
                // "--------------------------------\r\n";
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

                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }
                        /**
                         main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                                main_Text += ""+str_option_additional_txt + "\n";
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
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
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t               ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_tax)) + "\n";

                main_Text +=  "Total Amount \t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_totalamount)) + "\n";

                main_Text += "--------------------------------\r\n";

                if (!GlobalMemberValues.isStrEmpty(str_returntipamount)) {
                    main_Text += "Tip \t\t               ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returntipamount)) + "\n";
                    main_Text += "--------------------------------\r\n";
                }

                if (!GlobalMemberValues.isStrEmpty(str_returnpickupdeliveryfee)) {
                    main_Text += "Pickup/Delivery Fee \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returnpickupdeliveryfee)) + "\n";
                    main_Text += "--------------------------------\r\n";
                }
//
                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

                main_Text += "--------------------------------\r\n";
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

                        merchantText += "*** Credit Card Transaction ****\r\n";
                        customerText += "*** Credit Card Transaction ****\r\n";

                        merchantText += "Card Type \t : ";
                        customerText += "Card Type \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";

                        merchantText +="Credit Card# \t : ";
                        customerText +="Credit Card# \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";

                        merchantText += "Charge Amount \t : ";
                        customerText += "Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";



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
                        merchantText += "--------------------------------\r\n";
                        customerText += "--------------------------------\r\n";

                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : __________________  \n";
                                merchantText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ____________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : __________________  \n";
                                customerText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ____________________\r\n\r\n";
                            }
                        } else {

                        }
                    }
                }

                customerText += "######### Merchant Copy ########\r\n";
                customerText += "* Thank you. Come again soon. *\r\n";
                customerText += "printed : ";
                customerText += getDate() + " " + getTime();

                merchantText += "######### Customer Copy ########\r\n";
                merchantText += "* Thank you. Come again soon. *\r\n";
                merchantText += "printed : ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else {
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                }

                GlobalMemberValues.eloOpenDrawer(apiAdapter);
            }
        };
        thread.start();

        // 커스텀 Alert 띄우기.. ----------------------------------------------------------------
        JSONObject tempDialogJsonData = data;
        String tempDialogTitle = "Next Printing";
        String tempDialogContents = "Do you want to print next? (customer copy)";
        String tempDialogYesActionType = "customercopy_return";
        String tempDialogNoActionType = "openpaymentreview_return";
        String tempDialogYesButtonText = "PRINT CUSTOMER COPY (RETURN)";
        String tempDialogNoButtonText = "STOP";
        ShowDialogInElo(
                tempDialogJsonData,
                tempDialogTitle,
                tempDialogContents,
                tempDialogYesButtonText,
                tempDialogNoButtonText,
                tempDialogYesActionType,
                tempDialogNoActionType);
        // -------------------------------------------------------------------------------------
    }

    public void printReturnReceiptElo2(final JSONObject data) {
        if (productInfo == null){
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

                    if (data.toString().contains("deliverytakeaway")) {
                        str_deliverytakeaway = data.getString("deliverytakeaway");
                    } else {
                        str_deliverytakeaway = "T";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = data.getString("customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
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

                titleText1 =  GlobalMemberValues.getPrintTxtCenterAlignment("*** " + str_storename + " ***", 32) + "\n";
                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(str_storeaddress1 + " "+ str_storeaddress2, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment(str_storecity + " " + str_storestate + " "+ str_storezip, 32) + "\n"
                        + GlobalMemberValues.getPrintTxtCenterAlignment("(TEL) "+ telNumberExch(str_storephone), 32) + "\n";

                // 주문번호, 수령타입 관련 --------------------------------------------------------------
                titleText1 += "--------------------------------\r";

                if (GlobalMemberValues.isOrderNumberSectionViewOn()) {
                    titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Order No. : " + str_customerOrderNumber, 32);

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
                        titleText1 += "\n" + GlobalMemberValues.getPrintTxtCenterAlignment("Pick Up Type : " + tempDeliveryTakeaway, 32) + "\n";

                        titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Customer Name : " + str_customername, 32) + "\n";
                        if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment("Phone No. : " + telNumberExch(str_customerphonenum), 32) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            String tempCustomerAddress[] = str_customeraddress.split("\n");
                            titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[0], 32) + "\n";
                            if (tempCustomerAddress.length > 1) {
                                titleText1 += GlobalMemberValues.getPrintTxtCenterAlignment(tempCustomerAddress[1], 32) + "\n";
                            }
                        }
                    }

                    titleText1 += "--------------------------------\r";
                }
                // -----------------------------------------------------------------------------------
                titleText1 += "\n" +
                        ">>>>>>>>>>   RETURN   <<<<<<<<<<\r\n" +
                        "\n";

                String[] tempEmpNames = str_employeename.split(",");
                String tempEmpStr = "";
                GlobalMemberValues.logWrite("EmpCountLog", "emp count : " + tempEmpNames.length + "\n");
                if (tempEmpNames.length > 1) {
                    tempEmpStr = "\n[Employee Name]\n";
                } else {
                    tempEmpStr = "\nEmployee Name     : ";
                }

                titleText2 = tempEmpStr + str_employeename + "\nReceipt# : "
                        + str_receiptno + "\nRefund Date   : " + str_saledate + "\nRefund Time   : " + str_saletime + "\n";
                //titleText2 = tempEmpStr + str_employeename + "\nRefund Date   : " + str_saledate + "\nRefund Time   : " + str_saletime + "\n";

                main_Text += "--------------------------------\r\n";
                // " Item      Qty   Price    Amount\r\n" +
                // "--------------------------------\r\n";

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

                        if (!GlobalMemberValues.isStrEmpty(str_itemtaxexempt) && str_itemtaxexempt.equals("Y")) {
                            if (GlobalMemberValues.mUseTaxExemptTxtYN) {
                                str_itemname = "[T/E]" + str_itemname;
                            }
                        } else {
                            str_itemname = str_itemname;
                        }
                        /**
                         main_Text += Payment_stringBackSpace_Exch(10,str_itemname) +
                         Payment_stringFowardSpace_Exch(3, str_itemqty) +
                         Payment_stringFowardSpace_Exch(9, GlobalMemberValues.getCommaStringForDouble(str_itemprice))+
                         Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";
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
                                Payment_stringBackSpace_Exch(17,str_itemname) +
                                Payment_stringFowardSpace_Exch(9, str_itemamount)+ "\n";

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
                                main_Text += ""+str_option_additional_txt + "\n";
                            }
                        }

                        String str_kitchenmemo_txt = "";
                        if (!GlobalMemberValues.isStrEmpty(str_kitchenmemo) && !str_kitchenmemo.equals("nokitchenmemo")) {
                            str_kitchenmemo_txt += "[Kitchen Memo]\n" + GlobalMemberValues.getModifierTxt(str_itemqty, str_kitchenmemo);
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
                    main_Text += "--------------------------------\r\n";
                    main_Text += str_alldiscountextrstr + "\n";
                }

                main_Text += "--------------------------------\r\n" +
                        "Sub Total \t\t         ";
                main_Text +=  Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_subtotal)) + "\n";


                if (TextUtils.isEmpty(str_totalextradiscountprice)){

                }else {
                    if (GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) > 0 ||
                            GlobalMemberValues.getDoubleAtString(str_totalextradiscountprice) < 0) {
                        String flagDcEx = "";
                        if (str_totalextradiscountprice.contains("-")){
                            main_Text += "D/C Total \t\t         ";
                            flagDcEx = "";
                        } else {
                            main_Text += "Extra Total \t\t         ";
                            flagDcEx = "-";
                        }
                        main_Text += Payment_stringFowardSpace_Exch(12,(flagDcEx + str_totalextradiscountprice)) + "\n";
                    }

                }

                main_Text +=  "Tax \t\t               ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_tax)) + "\n";

                main_Text +=  "Total Amount \t\t      ";

                main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_totalamount)) + "\n";

                main_Text += "--------------------------------\r\n";

                if (!GlobalMemberValues.isStrEmpty(str_returntipamount)) {
                    main_Text += "Tip \t\t               ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returntipamount)) + "\n";
                    main_Text += "--------------------------------\r\n";
                }

                if (!GlobalMemberValues.isStrEmpty(str_returnpickupdeliveryfee)) {
                    main_Text += "Pickup/Delivery Fee \t      ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_returnpickupdeliveryfee)) + "\n";
                    main_Text += "--------------------------------\r\n";
                }
//
                if (str_creditcardtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_creditcardtendered) > 0)
                {
                    main_Text += "Card Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_creditcardtendered)) + "\n";
                }

                if (str_giftcardtendered.toString() == "0" || TextUtils.isEmpty(str_giftcardtendered)) {
                    // gift card balance 프린팅 할 필요 없음.
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_giftcardtendered) > 0) {
                        main_Text += "Gift Card Tendered \t\t";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_giftcardtendered)) + "\n";
                    }
                }
                if (str_checktendered.toString() == "0" || TextUtils.isEmpty(str_checktendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_checktendered) > 0) {
                        main_Text += "Other Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_checktendered)) + "\n";
                    }
                }
                if (str_pointtendered.toString() == "0" || TextUtils.isEmpty(str_pointtendered)) {
                }
                else {
                    if (GlobalMemberValues.getDoubleAtString(str_pointtendered) > 0) {
                        main_Text += "Point Tendered \t\t    ";
                        main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_pointtendered)) + "\n";
                    }
                }
                if (str_cashtendered.length() != 0 && GlobalMemberValues.getDoubleAtString(str_cashtendered) > 0) {
                    main_Text += "Cash Tendered \t\t     ";
                    main_Text += Payment_stringFowardSpace_Exch(12, combineStringForVoidPrint(str_cashtendered)) + "\n";
                }

                main_Text += "--------------------------------\r\n";
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

                        merchantText += "*** Credit Card Transaction ****\r\n";
                        customerText += "*** Credit Card Transaction ****\r\n";

                        merchantText += "Card Type \t : ";
                        customerText += "Card Type \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_cardtype) + "\n";

                        merchantText +="Credit Card# \t : ";
                        customerText +="Credit Card# \t : ";

                        merchantText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16,str_creditcardnumber) + "\n";

                        merchantText += "Charge Amount \t : ";
                        customerText += "Charge Amount \t : ";
                        merchantText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";
                        customerText += Payment_stringBackSpace_Exch(16, combineStringForVoidPrint(str_cardchangeamount)) + "\n";



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
                        merchantText += "--------------------------------\r\n";
                        customerText += "--------------------------------\r\n";

                        if (str_receiptprinttype.toString() == "1"){
                            //customer copy

                        } else if (str_receiptprinttype.toString() == "2"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                merchantText += "\t         Tip : __________________  \n";
                                merchantText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                merchantText += " Signature ____________________\r\n\r\n";
                            }

                        } else if (str_receiptprinttype.toString() == "3"){
                            if (GlobalMemberValues.isTiplineOntheReceipt()) {
                                customerText += "\t         Tip : __________________  \n";
                                customerText += " Grand Total : ________________\r\n\r\n\r\n";
                            }
                            if (GlobalMemberValues.isSignOnMinPay(str_cardchangeamount)) {
                                customerText += " Signature ____________________\r\n\r\n";
                            }
                        } else {

                        }
                    }
                }

                customerText += "######### Merchant Copy ########\r\n";
                customerText += "* Thank you. Come again soon. *\r\n";
                customerText += "printed : ";
                customerText += getDate() + " " + getTime();

                merchantText += "######### Customer Copy ########\r\n";
                merchantText += "* Thank you. Come again soon. *\r\n";
                merchantText += "printed : ";
                merchantText += getDate() + " " + getTime();

                str_receiptprinttype = "3";

                if (str_receiptprinttype.toString() == "1"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "2"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else if (str_receiptprinttype.toString() == "3"){
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(merchantText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                } else {
                    GlobalMemberValues.eloPrintText(titleText1, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(titleText2, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(main_Text, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText(customerText, myPrinter, apiAdapter);
                    GlobalMemberValues.eloPrintText("\n\n\n\n\n", myPrinter, apiAdapter);
                }

                GlobalMemberValues.eloOpenDrawer(apiAdapter);
            }
        };
        thread.start();

        SaleHistory.mActivity.recreate();
    }

    public void printTestElo(final JSONObject data) {
        if (productInfo != null) {
            GlobalMemberValues.eloPrintText("test print\ntest print\ntest print\ntest print\ntest print", myPrinter, apiAdapter);
            GlobalMemberValues.eloOpenDrawer(apiAdapter);
        }
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
