package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;
import com.printer.posbank.Printer;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by byullbam on 16. 2. 23..
 */
public class EloProPrinterKitchen1 {
    Context mContext;

    public ProductInfo productInfo;
    private ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    private EloPrinterReceipt.PrinterMake myPrinter;


    public EloProPrinterKitchen1(Context paramContext) {
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
                    myPrinter = EloPrinterReceipt.PrinterMake.RONGTA;
                    break;
                case PAYPOINT_2:
                    myPrinter = EloPrinterReceipt.PrinterMake.STAR;
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

    public void printKitchenEloPro(final JSONObject data) {
        GlobalMemberValues.logWrite("phoneorderprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");
        GlobalMemberValues.logWrite("phoneorderprintlog", "phoneorderholecode2 : " + GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT + "\n");

        // 제일 첫번째 실행되는 PrinterKitchen1 에서만 아래 부분 초기화
        GlobalMemberValues.mKitchenPrintedQty = 0;

        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
        GlobalMemberValues.openCloseKitchenPrintLoading(true, "1");

        if (productInfo == null) {
            if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
                GlobalMemberValues.initPhoneOrderValues();

                // 키친프린터 로딩창 종료...
                GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
            }

            // 프린터2 실행
            GlobalMemberValues.printReceiptByKitchen2(data, MainActivity.mContext, "kitchen2");

            return;
        }

        //GlobalMemberValues.logWrite("kitchenPrintLog", "프린트안 json : " + data.toString() + "\n");
        //GlobalMemberValues.displayDialog(MainActivity.mContext, "kitchenreprint", "paramJsonroot : " + data.toString(), "Close");
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

                String str_reprintyn = "N";

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

                GlobalMemberValues.logWrite("kitchendataprintlog", "customerordernumber111 : " + str_customerOrderNumber + "\n");


                // 먼저 kitchen printer1 에 해당되는 food 가 있는지 확인한다. ---------------------------------------------------------------
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

                        if (GlobalMemberValues.isKitchenPrinting("1", tempItemIdx)) {
                            tempItemCount++;
                        }
                    }
                }
                // ----------------------------------------------------------------------------------------------------------------------

                GlobalMemberValues.logWrite("kitchenPrintLogWanhayetxt", "tempItemCount : " + tempItemCount + "\n");

                if (tempItemCount > 0) {
                    int tempAddTextSize = 0;
                    if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                        tempAddTextSize = 0;
                    } else {
                        tempAddTextSize = 20;
                    }

                    String orderTypeStr = "";
                    if (str_ordertype[0].equals("POS")) {
                        orderTypeStr = "POS ORDER";
                    } else {
                        orderTypeStr = "WEB ORDER";
                    }

                    String deliveryTakeawayStr = "";
                    if (str_deliverytakeaway.equals("D")) {
                        deliveryTakeawayStr = "Devliery (" + str_deliverydate + ")";
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
                    GlobalMemberValues.logWrite("kitchendataprintlog", "customerordernumber222 : " + str_customerOrderNumber + "\n");

                    if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                            GlobalMemberValues.eloProPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n", apiAdapter, "N", "CENTER", 60 + tempAddTextSize);
                        } else {
                            GlobalMemberValues.eloProPrintText(GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n", apiAdapter, "N", "CENTER", 60 + tempAddTextSize);
                        }
                    }

                    titleText1 =  "*** Kitchen ***\n";
                    titleText1 +=  "[ " + orderTypeStr + " ]\n";
                    titleText1 +=  " \n";
                    GlobalMemberValues.eloProPrintText(titleText1, apiAdapter, "N", "CENTER", 25);

                    if (GlobalMemberValues.isKitchenOrderNumberSectionViewOn()) {
                        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                            titleText2 = "Order No. : # " + str_customerOrderNumber + "\n";
                            titleText2 += "Receiving : " + deliveryTakeawayStr + "\n";
                            GlobalMemberValues.eloProPrintText(titleText2, apiAdapter, "N", "LEFT", 35 + tempAddTextSize);
                        } else {
                            titleText2 = "# " + str_customerOrderNumber + "\n";
                            titleText2 += "" + deliveryTakeawayStr + "\n";
                            GlobalMemberValues.eloProPrintText(titleText2, apiAdapter, "N", "CENTER", 35 + tempAddTextSize);
                        }
                    }

                    /**
                    titleText3 = "-------------------------------------\r\n" +
                            " Item List\r\n" +
                            "-------------------------------------\r\n";
                     **/
                    titleText3 = "-------------------------------------\r\n";

                    GlobalMemberValues.eloProPrintText(titleText3, apiAdapter, "N", "LEFT", 25);

                    if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                        String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                        for (int i = 0; i < strOrderItemsList.length; i++) {
                            String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                            // 상품명, 수량 정보 --------------------------------------------------------------
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
                            // ---------------------------------------------------------------------

                            if (GlobalMemberValues.isKitchenPrinting("1", tempItemIdx)) {
                                main_Text = tempItemQty + " " + Payment_stringBackSpace_Exch2(100, tempItemName);
                                if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                                    GlobalMemberValues.eloProPrintText(main_Text, apiAdapter, "N", "LEFT", 38 + tempAddTextSize);
                                } else {
                                    GlobalMemberValues.eloProPrintText(main_Text, apiAdapter, "N", "LEFT", 38 + tempAddTextSize);
                                }
                                main_Text = "";
                                if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                    main_Text += GlobalMemberValues.getModifierTxtForKitchen(tempOptionTxt);
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                    main_Text += "--- Add Ingredients ---\n" + GlobalMemberValues.getModifierTxtForKitchen(tempAdditionalTxt1) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                                    main_Text += "--- Add Ingredients 2 ---\n" + GlobalMemberValues.getModifierTxtForKitchen(tempAdditionalTxt2) + "\n";
                                }
                                if (!GlobalMemberValues.isStrEmpty(tempKitchenMemo) && !tempKitchenMemo.equals("nokitchenmemo")) {
                                    main_Text += "--- Special Request ---\n" + GlobalMemberValues.getModifierTxtForKitchen(tempKitchenMemo);
                                }
                                if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                                    GlobalMemberValues.eloProPrintText(main_Text, apiAdapter, "N", "LEFT", 32 + tempAddTextSize);
                                } else {
                                    GlobalMemberValues.eloProPrintText(main_Text, apiAdapter, "N", "LEFT", 32 + tempAddTextSize);
                                }

                                main_Text = "";
                                //main_Text += "Qty : " + Payment_stringFowardSpace_Exch(3, tempItemQty) + "\n";
                                if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                                    main_Text +=  "-------------------------------------\r\n";
                                } else {
                                    main_Text +=  "------------------------\r\n";
                                }
                                GlobalMemberValues.eloProPrintText(main_Text, apiAdapter, "N", "LEFT", 25);
                            }
                        }
                    }

                    if (!GlobalMemberValues.isStrEmpty(str_customermemo)) {
                        memo_Text = "[Note]" + "\n";
                        GlobalMemberValues.eloProPrintText(memo_Text, apiAdapter, "N", "LEFT", 25);
                        memo_Text = str_customermemo + "\n";
                        GlobalMemberValues.eloProPrintText(memo_Text, apiAdapter, "N", "LEFT", 25);
                        memo_Text = "-------------------------------------\r\n";
                        GlobalMemberValues.eloProPrintText(memo_Text, apiAdapter, "N", "LEFT", 25);
                    }

                    // receipt footer 프린터 ----------------------------------------------------------------------------------
                    String footer_kitchen = GlobalMemberValues.getReceiptFooterKitchen2();
                    if (!GlobalMemberValues.isStrEmpty(footer_kitchen)) {
                        GlobalMemberValues.eloProPrintText(footer_kitchen, apiAdapter, "N", "LEFT", 25);
                        GlobalMemberValues.eloProPrintText("-------------------------------------\r\n", apiAdapter, "N", "LEFT", 25);
                    }
                    // --------------------------------------------------------------------------------------------------------

                    if (str_phoneOrder == "Y" || str_phoneOrder.equals("Y")) {
                    } else {
                        //customerText = "Receipt No. : " + str_receiptno[0] + "\n";
                    }

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

                    customerText += "Printed Time : " + getDate() + " " + getTime();
                    GlobalMemberValues.eloProPrintText(customerText, apiAdapter, "N", "LEFT", 25);

                    GlobalMemberValues.eloProPrintText("\n", apiAdapter, "N", "LEFT", 25);

                    GlobalMemberValues.eloProCutPaper(apiAdapter);
                }

                GlobalMemberValues.setKitchenPrintedChangeStatus(str_receiptno[0], str_ordertype[0]);

                GlobalMemberValues.setPhoneorderKitchenPrinted(str_receiptno[0]);
            }
        };
        thread.start();

        // 현재까지 주방프린터가 실행된 수와 전체 셋팅된 주방프린터의 수가 같을 때..
        if (GlobalMemberValues.mKitchenPrintedQty == GlobalMemberValues.getSettingKitchenPrinterQty()) {
            GlobalMemberValues.setKitchenPrinterValues();

            // 키친프린터 로딩창 종료...
            GlobalMemberValues.openCloseKitchenPrintLoading(false, "");
        }

        // 프린터2 실행
        GlobalMemberValues.printReceiptByKitchen2(data, MainActivity.mContext, "kitchen2");

        //Payment.openPaymentReview(Payment.context);
    }

    public void printTestEloPro(final JSONObject data) {
        if (productInfo != null) {
            GlobalMemberValues.eloProPrintText("test print\ntest print\ntest print\ntest print\ntest print", apiAdapter, "N", "LEFT", 25);
            GlobalMemberValues.eloProCutPaper(apiAdapter);
            GlobalMemberValues.eloOpenDrawer(apiAdapter);
        }
    }

}
