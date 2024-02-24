package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by byullbam on 18. 5. 30..
 */
public class PrinterKitchen4 {
    public PrinterKitchen4() {
        // 프린트에 사용된 언어 설정
        String getPrintLang = MainActivity.mDbInit.dbExecuteReadReturnString("select printlanguage from salon_storestationsettings_deviceprinter4");
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
        if (arr[1].toString().equals(""))
        {
            return_str = "0.00";
        }else
        {
            return_str = arr[1].toString();
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

        instr = GlobalMemberValues.getDecodeString(instr);
        GlobalMemberValues.logWrite("instrLog", "instr : " + instr + "\n");

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength > stringLength) {
            temp_str = instr.substring(0,stringLength) + " ";
        } else {
            int i_space = stringLength - strLength;
            StringBuilder temp_build = new StringBuilder(instr);
            for (int i = 0; i <= i_space; i++){
                temp_build.append(" ");
            }
            temp_str = temp_build.toString();
        }

        return_str = temp_str;
        return return_str;
    }
    private String Payment_stringBackSpace_Exch2(int stringLength ,String instr)
    {
        String return_str = "";
        String temp_str = "";

        instr = GlobalMemberValues.getDecodeString(instr);
        GlobalMemberValues.logWrite("instrLog", "instr : " + instr + "\n");

        int strLength = GlobalMemberValues.getSizeToString(instr);

        if (strLength > stringLength) {
            temp_str = instr.substring(0,stringLength) + " ";
        } else {
            int i_space = stringLength - strLength;
            StringBuilder temp_build = new StringBuilder(instr);
            temp_str = temp_build.toString();
        }

        return_str = temp_str;
        return return_str;
    }
    private String Payment_stringFowardSpace_Exch(int stringLength ,String instr)
    {
        String return_str = "";
        String temp_str = "";

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
        return return_str;
    }

    public void printKitchenPosbank(final JSONObject data) {
        GlobalMemberValues.logWrite("phoneorderprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");
        GlobalMemberValues.logWrite("phoneorderprintlog", "phoneorderholecode2 : " + GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT + "\n");

        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
        GlobalMemberValues.openCloseKitchenPrintLoading(true, "4");

        if (PosbankPrinter5.mPrinter == null){
            if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
                GlobalMemberValues.initPhoneOrderValues();

                // 키친프린터 로딩창 종료...
                GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
            }

            // 프린터(숫자) 실행
            GlobalMemberValues.printReceiptByKitchen5(data, MainActivity.mContext, "kitchen5");

            return;
        }

        GlobalMemberValues.logWrite("kitchenPrintLog2", "프린트안 json -- 4 : " + data.toString() + "\n");

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(2000); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final String[] str_receiptno = {""};
        final String[] str_ordertype = {""};

        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                int alignment = Printer.ALIGNMENT_LEFT;
                int size = 0;
                int attribute = 0;

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

                str_saledate = GlobalMemberValues.getDataInJsonData(data, "saledate");
                str_saletime = GlobalMemberValues.getDataInJsonData(data,"saletime");
                str_receiptno[0] = GlobalMemberValues.getDataInJsonData(data,"receiptno");
                str_saleitemlist = GlobalMemberValues.getDataInJsonData(data,"saleitemlist");
                str_customername = GlobalMemberValues.getDataInJsonData(data,"customername");
                str_customerphonenum = GlobalMemberValues.getDataInJsonData(data,"customerphonenum");
                str_customeraddress = GlobalMemberValues.getDataInJsonData(data,"customeraddress");
                str_deliverytakeaway = GlobalMemberValues.getDataInJsonData(data,"deliverytakeaway");
                str_deliverydate = GlobalMemberValues.getDataInJsonData(data,"deliverydate");
                str_ordertype[0] = GlobalMemberValues.getDataInJsonData(data,"ordertype");
                str_customermemo = GlobalMemberValues.getDataInJsonData(data,"customermemo");
                str_receiptfooter = GlobalMemberValues.getDataInJsonData(data,"receiptfooter");
                str_customerOrderNumber = GlobalMemberValues.getDataInJsonData(data,"customerordernumber");
                str_customerPagerNumber = GlobalMemberValues.getDataInJsonData(data,"customerpagernumber");
                str_phoneOrder = GlobalMemberValues.getDataInJsonData(data,"phoneorder");
                str_phoneOrderNumber = GlobalMemberValues.getDataInJsonData(data,"phoneordernumber");

                // 먼저 kitchen printer4 에 해당되는 food 가 있는지 확인한다. ---------------------------------------------------------------
                int tempItemCount = 0;
                if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                    String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                    for (int i = 0; i < strOrderItemsList.length; i++) {
                        String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                        // 상품명, 수량 정보 ------------------------------------------------------------------------
                        String tempItemNameOptionAdd = strOrderItems[0];
                        String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);

                        String tempItemIdx = "";
                        if (strItemNAmeOptionAdd.length > 4) {
                            tempItemIdx = strItemNAmeOptionAdd[4];
                        }
                        // -----------------------------------------------------------------------------------------

                        if (GlobalMemberValues.isKitchenPrinting("4", tempItemIdx)) {
                            tempItemCount++;
                        }
                    }
                }
                // ----------------------------------------------------------------------------------------------------------------------

                if (tempItemCount > 0) {
                    int tempAddTextSize = 0;
                    if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                    } else {
                        tempAddTextSize = 1;
                    }

                    String orderTypeStr = "";
                    if (str_ordertype[0].equals("POS")) {
                        orderTypeStr = "POS ORDER";
                    } else {
                        orderTypeStr = "WEB ORDER";
                    }

                    String deliveryTakeawayStr = "";
                    if (str_deliverytakeaway.equals("D")) {
                        deliveryTakeawayStr = "Delivery (" + str_deliverydate + ")";
                    } else {
                        if (str_deliverytakeaway.equals("T")) {
                            deliveryTakeawayStr = "Pick Up";
                        } else {
                            deliveryTakeawayStr = "Here   ";
                        }
                    }

                    if (!GlobalMemberValues.isStrEmpty(str_phoneOrder) && str_phoneOrder.equals("Y")) {
                        str_customerOrderNumber = str_phoneOrderNumber;
                    }

                    if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                            GlobalMemberValues.posbankPrintText5(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n\n", 1, attribute, 3, false);
                        } else {
                            GlobalMemberValues.posbankPrintText5(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n\n", 1, attribute, 3 + tempAddTextSize, false);
                        }
                    }

                    titleText1 =  "*** Kitchen ***\n";
                    titleText1 +=  "[ " + orderTypeStr + " ]\n";
                    titleText1 +=  " \n";
                    GlobalMemberValues.posbankPrintText5(titleText1, 1, attribute, 0, false);

                    if (GlobalMemberValues.isKitchenOrderNumberSectionViewOn()) {
                        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                            titleText2 = "Order No. : # " + str_customerOrderNumber + "\n";
                            titleText2 += "Receiving Type : " + deliveryTakeawayStr + "\n";
                        } else {
                            titleText2 = "No. : # " + str_customerOrderNumber + "\n";
                            titleText2 += "" + deliveryTakeawayStr + "\n";
                        }
                        GlobalMemberValues.posbankPrintText5(titleText2, 0, attribute, 1 + tempAddTextSize, false);
                    }

                    /**
                     titleText3 = "------------------------------------------\r\n" +
                     " Item List                                \r\n" +
                     "------------------------------------------\r\n";
                     **/
                    titleText3 = "------------------------------------------\r\n";
                    GlobalMemberValues.posbankPrintText5(titleText3, 1, attribute, 0, false);

                    if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                        String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                        for (int i = 0; i < strOrderItemsList.length; i++) {
                            String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                            // 상품명, 수량 정보 ------------------------------------------------------------------------
                            String tempItemNameOptionAdd = strOrderItems[0];
                            String tempItemQty = strOrderItems[1];

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
                            if (GlobalMemberValues.isKitchenPrinting("4", tempItemIdx)) {
                                main_Text += tempItemQty + "   " + Payment_stringBackSpace_Exch2(100, tempItemName) + "\n";
                                if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                    main_Text += GlobalMemberValues.getModifierTxtForKitchen2(tempOptionTxt, 2);
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                    main_Text += "--- Add Ingredients ---\n" + GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt1, 2) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                                    main_Text += "--- Add Ingredients 2 ---\n" + GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt2, 2) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempKitchenMemo) && !tempKitchenMemo.equals("nokitchenmemo")) {
                                    main_Text += "--- Special Request ---\n" + GlobalMemberValues.getModifierTxtForKitchen2(tempKitchenMemo, 2);
                                }
                                //main_Text += "Qty : " + Payment_stringFowardSpace_Exch(3, tempItemQty) + "\n";
                                main_Text +=  "------------------------------------------\r\n";
                            }
                        }
                        GlobalMemberValues.posbankPrintText5(main_Text, 0, attribute, 1 + tempAddTextSize, false);
                        //main_Text += "------------------------------------------\r\n";
                        //GlobalMemberValues.posbankPrintText5(main_Text, 1, attribute, 0, false);
                    }

                    if (!GlobalMemberValues.isStrEmpty(str_customermemo)) {
                        memo_Text = "[Note]" + "\n";
                        GlobalMemberValues.posbankPrintText5(memo_Text, 0, attribute, 0, false);
                        memo_Text = str_customermemo + "\n";
                        GlobalMemberValues.posbankPrintText5(memo_Text, 0, attribute, 1, false);
                        memo_Text = "------------------------------------------\r\n";
                        GlobalMemberValues.posbankPrintText5(memo_Text, 1, attribute, 0, false);
                    }

                    // receipt footer 프린터 ----------------------------------------------------------------------------------
                    String footer_kitchen = GlobalMemberValues.getReceiptFooterKitchen5();
                    if (!GlobalMemberValues.isStrEmpty(footer_kitchen)) {
                        GlobalMemberValues.posbankPrintText5(footer_kitchen, 1, 0, 0, false);
                        GlobalMemberValues.posbankPrintText5("------------------------------------------\r\n", 1, 0, 0, false);
                    }
                    // --------------------------------------------------------------------------------------------------------


                    /**
                     if (str_phoneOrder == "Y" || str_phoneOrder.equals("Y")) {
                     } else {
                     customerText = "Receipt No. : " + str_receiptno[0] + "\n";
                     }
                     **/

                    if (GlobalMemberValues.mCustomerInfoShowYN) {
                        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(str_customername, " ", ""))) {
                            customerText += "Customer : " + str_customername + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(str_customerphonenum, " ", ""))) {
                            customerText += "Phone No. : " + telNumberExch(str_customerphonenum) + "\n";
                        }
                        if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                            customerText += "Address ----------------------------------\n" + str_customeraddress + "\n------------------------------------------\n";
                        }
                    }

                    customerText += "Printed Time " + getDate() + " " + getTime();
                    GlobalMemberValues.posbankPrintText5(customerText, 0, attribute, 0, false);

                    GlobalMemberValues.posbankPrintText5("\n\n\n\n\n\n\n\n\n\n", alignment, attribute, size, false);

                    PosbankPrinter5.mPrinter.cutPaper(true);

                    // 주방프린터를 진행한 주문건의 주문번호 저장 ------------------------------------------
                    GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = str_receiptno[0];
                    GlobalMemberValues.logWrite("kitchenprintedlogs", "salescode : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");
                    // -------------------------------------------------------------------------------

                }

                GlobalMemberValues.disconnectPrinter5();

                GlobalMemberValues.setKitchenPrintedChangeStatus(str_receiptno[0], str_ordertype[0]);

                GlobalMemberValues.setPhoneorderKitchenPrinted(str_receiptno[0]);
            }
        };
        thread.start();

        GlobalMemberValues.logWrite("kitchenPrintLogWanhaye", "지금까지 프린트 된 주방프린터 수 : " + GlobalMemberValues.mKitchenPrintedQty + "\n");
        GlobalMemberValues.logWrite("kitchenPrintLogWanhaye", "전체 셋팅된 주방프린터 수 : " + GlobalMemberValues.getSettingKitchenPrinterQty() + "\n");

        // 현재까지 주방프린터가 실행된 수와 전체 셋팅된 주방프린터의 수가 같을 때..
        if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
            GlobalMemberValues.setKitchenPrinterValues();

            // 키친프린터 로딩창 종료...
            GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
        }

        // 프린터(숫자) 실행
        GlobalMemberValues.printReceiptByKitchen5(data, MainActivity.mContext, "kitchen5");

    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

    public void printTestPosbank(final JSONObject data) {
        GlobalMemberValues.logWrite("secondkitchenlog", "PrinterKitchen2 printTestPosbank 진입" + "\n");
        if (PosbankPrinter5.mPrinter == null){
            return;
        }
        GlobalMemberValues.logWrite("secondkitchenlog", "PrinterKitchen2 printTestPosbank 진입2" + "\n");

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
                    testTxt = "No Kitchen Printed Data (4)" +
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "\nThe End (Wanhaye)..............";
                }

                GlobalMemberValues.posbankPrintText5(testTxt, 1, attribute, 0, false);
                PosbankPrinter5.mPrinter.cutPaper(true);

                //PosbankPrinter5.mPrinter.kickOutDrawer(Printer.DRAWER_CONNECTOR_PIN2);
                GlobalMemberValues.disconnectPrinter5();

            }
        };
        thread.start();
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
