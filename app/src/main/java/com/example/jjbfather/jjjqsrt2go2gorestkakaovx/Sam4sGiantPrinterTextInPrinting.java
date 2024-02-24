package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.Gravity;

import com.printer.posbank.Printer;
import com.sam4s.printer.Sam4sBuilder;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sam4sGiantPrinterTextInPrinting {


    public static Sam4sBuilder makingTextForKitchen(final JSONObject data, final String paramPrintNum) {
        GlobalMemberValues.logWrite("cloverprintinglog", "받은 데이터 : " + data.toString() + "\n");

        final String[] str_receiptno = {""};
        final String[] str_ordertype = {""};

        Sam4sBuilder builder = null;

        try {
            builder = new Sam4sBuilder("ELLIX", Sam4sBuilder.LANG_KO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기 A" + data.toString() + "\n");

        final Sam4sBuilder finalBuilder = builder;
        Thread thread = new Thread(){
            @SuppressLint("LongLogTag")
            @Override
            public void run(){

                try {
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
                    str_saletime = GlobalMemberValues.getDataInJsonData(data, "saletime");
                    str_receiptno[0] = GlobalMemberValues.getDataInJsonData(data, "receiptno");

                    str_saleitemlist = GlobalMemberValues.getDataInJsonData(data, "saleitemlist");

                    str_customername = GlobalMemberValues.getDataInJsonData(data, "customername");
                    str_customerphonenum = GlobalMemberValues.getDataInJsonData(data, "customerphonenum");
                    str_customeraddress = GlobalMemberValues.getDataInJsonData(data, "customeraddress");

                    str_deliverytakeaway = GlobalMemberValues.getDataInJsonData(data, "deliverytakeaway");
                    str_deliverydate = GlobalMemberValues.getDataInJsonData(data, "deliverydate");

                    str_ordertype[0] = GlobalMemberValues.getDataInJsonData(data, "ordertype");

                    str_customermemo = GlobalMemberValues.getDataInJsonData(data, "customermemo");

                    if (data.toString().contains("receiptfooter")) {
                        str_receiptfooter = GlobalMemberValues.getDataInJsonData(data, "receiptfooter");
                    } else {
                        str_receiptfooter = "";
                    }

                    if (data.toString().contains("customerordernumber")) {
                        str_customerOrderNumber = GlobalMemberValues.getDataInJsonData(data, "customerordernumber");
                    } else {
                        str_customerOrderNumber = "";
                    }

                    if (data.toString().contains("customerpagernumber")) {
                        str_customerPagerNumber = GlobalMemberValues.getDataInJsonData(data, "customerpagernumber");
                    } else {
                        str_customerPagerNumber = "";
                    }

                    if (data.toString().contains("phoneorder")) {
                        str_phoneOrder = GlobalMemberValues.getDataInJsonData(data, "phoneorder");
                    } else {
                        str_phoneOrder = "";
                    }

                    if (data.toString().contains("phoneordernumber")) {
                        str_phoneOrderNumber = GlobalMemberValues.getDataInJsonData(data, "phoneordernumber");
                    } else {
                        str_phoneOrderNumber = "";
                    }

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

                            if (GlobalMemberValues.isKitchenPrinting(paramPrintNum, tempItemIdx)) {
                                tempItemCount++;
                            }
                        }
                    }
                    // ----------------------------------------------------------------------------------------------------------------------

                    GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기 B" + data.toString() + "\n");

                    if (tempItemCount > 0) {
                        String orderTypeStr = "";
                        if (str_ordertype[0].equals("POS")) {
                            orderTypeStr = "POS ORDER";
                        } else {
                            orderTypeStr = "WEB ORDER";
                        }

                        String deliveryTakeawayStr = "";
                        if (str_deliverytakeaway.equals("D")) {
                            deliveryTakeawayStr = "DELIVERY (" + str_deliverydate + ")";
                        } else {
                            if (str_deliverytakeaway.equals("T")) {
                                deliveryTakeawayStr = "TO GO";
                            } else {
                                deliveryTakeawayStr = "HERE";
                            }
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_phoneOrder) && str_phoneOrder.equals("Y")) {
                            str_customerOrderNumber = str_phoneOrderNumber;
                        }

                        if (!GlobalMemberValues.isStrEmpty(str_customerPagerNumber)) {
                            String tempTitleTxt = "";
                            float tempAddTextSize = 0;
                            if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                                //tempTitleTxt = "Pager ";
                                //tempTitleTxt = "";
                                tempAddTextSize = 20;
                            } else {
                                tempAddTextSize = 20;
                            }

//                            TextView pagerNoTv = new TextView(MainActivity.mContext);
//                            pagerNoTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//                            pagerNoTv.setGravity(Gravity.CENTER);
//                            pagerNoTv.setText(tempTitleTxt + GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber);
//                            pagerNoTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 40 + tempAddTextSize);
//                            GlobalMemberValues.setTextStyleOnClover(pagerNoTv);
//                            printingLn.addView(pagerNoTv);
                            finalBuilder.addTextAlign(Sam4sBuilder.ALIGN_CENTER);
                            finalBuilder.addTextSize(3,3);
                            finalBuilder.addText("\n"+ tempTitleTxt + GlobalMemberValues.PAGERNUMBERHEADERTXT + str_customerPagerNumber + "\n");
                            finalBuilder.addText("\n");

                            // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                            printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 30));
                            // ---------------------------------------------------------------------------------------------------------------------------------
                        }

//                        TextView kitchentitleTv = new TextView(MainActivity.mContext);
//                        kitchentitleTv.setLayoutParams(matchParentParams);
//                        kitchentitleTv.setGravity(Gravity.CENTER);
//                        kitchentitleTv.setText("*** Kitchen ***");
//                        kitchentitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                        GlobalMemberValues.setTextStyleOnClover(kitchentitleTv);
//                        printingLn.addView(kitchentitleTv);
                        finalBuilder.addTextSize(1,1);
                        finalBuilder.addTextAlign(Sam4sBuilder.ALIGN_CENTER);
                        finalBuilder.addText("\n*** Kitchen ***\n");

//                        TextView posordertitleTv = new TextView(MainActivity.mContext);
//                        posordertitleTv.setLayoutParams(matchParentParams);
//                        posordertitleTv.setGravity(Gravity.CENTER);
//                        posordertitleTv.setText("[ " + orderTypeStr + " ]");
//                        posordertitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                        GlobalMemberValues.setTextStyleOnClover(posordertitleTv);
//                        printingLn.addView(posordertitleTv);
                        finalBuilder.addText("[ " + orderTypeStr + " ]\n");
                        finalBuilder.addText(" \n");

                        // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                        printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 40));
                        // ---------------------------------------------------------------------------------------------------------------------------------

                        GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기 C" + data.toString() + "\n");

                        String tempTitleTxt1 = "";
                        int tempAddTextSize = 0;
                        int tempTextAlign = Gravity.LEFT;
                        int tempAddHeight = 0;
                        if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                            tempAddTextSize = 0;
                            //tempTitleTxt1 = " : ";
                        } else {
                            tempAddTextSize = 0;
                            tempTextAlign = Gravity.CENTER_HORIZONTAL;
                            tempAddHeight = 10;
                        }

                        if (GlobalMemberValues.isKitchenOrderNumberSectionViewOn()) {
                            // Receiving ------------------------------------------------------------------------------------------------------------
//                            LinearLayout receivingLn = new LinearLayout(MainActivity.mContext);
//                            receivingLn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80 + tempAddHeight));
//                            receivingLn.setOrientation(LinearLayout.HORIZONTAL);

                            /**
                             if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                             TextView receivingLeftTv = new TextView(MainActivity.mContext);
                             receivingLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                             receivingLeftTv.setText("Receiving");
                             receivingLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 10 + tempAddTextSize);
                             GlobalMemberValues.setTextStyleOnClover(receivingLeftTv);
                             receivingLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                             receivingLn.addView(receivingLeftTv);
                             }
                             **/

//                            TextView receivingRightTv = new TextView(MainActivity.mContext);
//                            receivingRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//                            receivingRightTv.setText( tempTitleTxt1 + deliveryTakeawayStr);
//                            //receivingRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 10 + tempAddTextSize);
//                            receivingRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 50);
//                            GlobalMemberValues.setTextStyleOnClover(receivingRightTv);
//                            receivingRightTv.setPaintFlags(receivingRightTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
//
//                            receivingRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                            receivingLn.addView(receivingRightTv);

                            finalBuilder.addTextSize(3,3);
                            finalBuilder.addText(tempTitleTxt1 + deliveryTakeawayStr + "\n");

//                            printingLn.addView(receivingLn);
                            // --------------------------------------------------------------------------------------------------------------------

                            // Order No. ------------------------------------------------------------------------------------------------------------
//                            LinearLayout ordernumLn = new LinearLayout(MainActivity.mContext);
//                            ordernumLn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50 + tempAddHeight));
//                            ordernumLn.setOrientation(LinearLayout.HORIZONTAL);

                            /**
                             if (GlobalMemberValues.getKitchenPrinterTextSize().equals("R")) {
                             TextView ordernumLeftTv = new TextView(MainActivity.mContext);
                             ordernumLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                             ordernumLeftTv.setText("Order No.");
                             ordernumLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 20 + tempAddTextSize);
                             GlobalMemberValues.setTextStyleOnClover(ordernumLeftTv);
                             ordernumLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                             ordernumLn.addView(ordernumLeftTv);
                             }
                             **/

//                            TextView ordernumRightTv = new TextView(MainActivity.mContext);
//                            ordernumRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//                            ordernumRightTv.setText(tempTitleTxt1 + "# " + str_customerOrderNumber);
//                            ordernumRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 20 + tempAddTextSize);
//                            GlobalMemberValues.setTextStyleOnClover(ordernumRightTv);
//                            ordernumRightTv.setPaintFlags(receivingRightTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
//
//                            ordernumRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                            ordernumLn.addView(ordernumRightTv);
                            finalBuilder.addTextSize(2,2);
                            finalBuilder.addText(tempTitleTxt1 + "# " + str_customerOrderNumber );

//                            printingLn.addView(ordernumLn);
                            // --------------------------------------------------------------------------------------------------------------------

                            // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                            printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 40));
                            // ---------------------------------------------------------------------------------------------------------------------------------
                        }

                        // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
//                        printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                        finalBuilder.addTextSize(1,1);
                        finalBuilder.addText("\n \n");
                        finalBuilder.addText("------------------------------------------"); // 42개 1줄.
                        // ---------------------------------------------------------------------------------------------------------------------------------

                        GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기 D" + data.toString() + "\n");

                        /**
                         TextView itemlisttitleTv = new TextView(MainActivity.mContext);
                         itemlisttitleTv.setLayoutParams(matchParentParams);
                         itemlisttitleTv.setGravity(Gravity.CENTER);
                         itemlisttitleTv.setText("Item List");
                         itemlisttitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
                         GlobalMemberValues.setTextStyleOnClover(itemlisttitleTv);
                         printingLn.addView(itemlisttitleTv);

                         // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
                         printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                         // ---------------------------------------------------------------------------------------------------------------------------------
                         **/

                        GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기서 데이터는...: " + data.toString() + "\n");

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

                                GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "data : " + data.toString() + "\n");
                                GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "tempItemIdx : " + tempItemIdx + "\n");

                                if (GlobalMemberValues.isKitchenPrinting(paramPrintNum, tempItemIdx)) {
                                    GlobalMemberValues.logWrite("kitchenprinterdatalogprintnum", "여기들어옴..."+ "\n");

                                    String tempQtyItemName = tempItemQty + "   ";
//                                    TextView itemnameTv = new TextView(MainActivity.mContext);
//                                    itemnameTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                    itemnameTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                    itemnameTv.setMaxLines(2);
                                    int tempHeight = GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 10 + tempAddTextSize;
                                    if (tempQtyItemName.length() + tempItemName.length() > 30 || GlobalMemberValues.getSizeToString(tempQtyItemName + tempItemName) > 30) {
                                        tempHeight = (tempHeight * 2) + 8;
                                    }
//                                    itemnameTv.setHeight(tempHeight);
//                                itemnameTv.setText(tempQtyItemName + designTextForItemForKitchen(tempItemName, 29, tempQtyItemName.length()));
//                                    itemnameTv.setText(tempQtyItemName + tempItemName);
//                                    itemnameTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 10 + tempAddTextSize);
//                                    GlobalMemberValues.setTextStyleOnClover(itemnameTv);
//                                    printingLn.addView(itemnameTv);

                                    finalBuilder.addText("\n"+tempQtyItemName + tempItemName);
                                    finalBuilder.addTextAlign(Sam4sBuilder.ALIGN_LEFT);

                                    // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                                    printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 20));
                                    // ---------------------------------------------------------------------------------------------------------------------------------


                                    if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                        /**
                                         TextView titleTv = new TextView(MainActivity.mContext);
                                         titleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                         titleTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                                         titleTv.setText("--- Option ---");
                                         titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
                                         GlobalMemberValues.setTextStyleOnClover(titleTv);
                                         printingLn.addView(titleTv);
                                         **/

//                                        TextView contentsTv = new TextView(MainActivity.mContext);
//                                        contentsTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        contentsTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        contentsTv.setText(GlobalMemberValues.getModifierTxtForKitchen2(tempOptionTxt, 6));
//                                        contentsTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(contentsTv);
//                                        printingLn.addView(contentsTv);
                                        finalBuilder.addText(GlobalMemberValues.getModifierTxtForKitchen2("\n" +tempOptionTxt, 6));
                                    }
                                    if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
//                                        TextView titleTv = new TextView(MainActivity.mContext);
//                                        titleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        titleTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        titleTv.setText("--- Add Ingredients ---");
//                                        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(titleTv);
//                                        printingLn.addView(titleTv);
                                        finalBuilder.addText("\n--- Add Ingredients ---");

//                                        TextView contentsTv = new TextView(MainActivity.mContext);
//                                        contentsTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        contentsTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        contentsTv.setText(GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt1, 6));
//                                        contentsTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(contentsTv);
//                                        printingLn.addView(contentsTv);
                                        finalBuilder.addText("\n"+GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt1, 6));
                                    }
                                    if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
//                                        TextView titleTv = new TextView(MainActivity.mContext);
//                                        titleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        titleTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        titleTv.setText("--- Add Ingredients 2 ---");
//                                        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(titleTv);
//                                        printingLn.addView(titleTv);
                                        finalBuilder.addText("\n--- Add Ingredients 2 ---");

//                                        TextView contentsTv = new TextView(MainActivity.mContext);
//                                        contentsTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        contentsTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        contentsTv.setText(GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt2, 6));
//                                        contentsTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(contentsTv);
//                                        printingLn.addView(contentsTv);
                                        finalBuilder.addText("\n"+GlobalMemberValues.getModifierTxtForKitchen2(tempAdditionalTxt2, 6));
                                    }
                                    if (!GlobalMemberValues.isStrEmpty(tempKitchenMemo) && !tempKitchenMemo.equals("nokitchenmemo")) {
//                                        TextView titleTv = new TextView(MainActivity.mContext);
//                                        titleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        titleTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        titleTv.setText("--- Special Request ---");
//                                        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(titleTv);
//                                        printingLn.addView(titleTv);
                                        finalBuilder.addText("\n--- Special Request ---");

//                                        TextView contentsTv = new TextView(MainActivity.mContext);
//                                        contentsTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                        contentsTv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
//                                        contentsTv.setText(GlobalMemberValues.getModifierTxtForKitchen(tempKitchenMemo));
//                                        contentsTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
//                                        GlobalMemberValues.setTextStyleOnClover(contentsTv);
//                                        printingLn.addView(contentsTv);
                                        finalBuilder.addText("\n"+GlobalMemberValues.getModifierTxtForKitchen(tempKitchenMemo));

                                    }
                                    finalBuilder.addTextAlign(Sam4sBuilder.ALIGN_RIGHT);


                                    /**
                                     TextView titleTv = new TextView(MainActivity.mContext);
                                     titleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                     titleTv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                                     titleTv.setText("Qty : " + tempItemQty);
                                     titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 5 + tempAddTextSize);
                                     GlobalMemberValues.setTextStyleOnClover(titleTv);
                                     printingLn.addView(titleTv);
                                     **/

                                    // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
//                                    printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                                    finalBuilder.addText("\n \n");
                                    finalBuilder.addText("------------------------------------------"); // 42개 1줄.
                                    // ---------------------------------------------------------------------------------------------------------------------------------
                                }
                            }
                        }

                        // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                        printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 20));
                        // ---------------------------------------------------------------------------------------------------------------------------------

                        if (!GlobalMemberValues.isStrEmpty(str_customermemo)) {
//                            TextView noteTv = new TextView(MainActivity.mContext);
//                            noteTv.setLayoutParams(matchParentParams);
//                            noteTv.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//                            noteTv.setText("[Note]");
//                            noteTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                            GlobalMemberValues.setTextStyleOnClover(noteTv);
//                            printingLn.addView(noteTv);
                            finalBuilder.addText("\n"+"[Note]");

//                            TextView memotextTv = new TextView(MainActivity.mContext);
//                            memotextTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            memotextTv.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//                            memotextTv.setText(str_customermemo);
//                            memotextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                            GlobalMemberValues.setTextStyleOnClover(memotextTv);
//                            printingLn.addView(memotextTv);
                            finalBuilder.addText("\n" + str_customermemo);

                            // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
//                            printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                            finalBuilder.addText("\n--------------------------------------------------------------------------------------");
                            // ---------------------------------------------------------------------------------------------------------------------------------
                        }

                        // receipt footer 프린터 ----------------------------------------------------------------------------------
                        String footer_kitchen = GlobalMemberValues.getReceiptFooterKitchen2();
                        if (!GlobalMemberValues.isStrEmpty(footer_kitchen)) {
//                            TextView footerTv = new TextView(MainActivity.mContext);
//                            footerTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            footerTv.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//                            footerTv.setText(footer_kitchen);
//                            footerTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                            GlobalMemberValues.setTextStyleOnClover(footerTv);
//                            printingLn.addView(footerTv);
                            finalBuilder.addText("\n" + footer_kitchen);

                            // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
//                            printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                            finalBuilder.addText("\n--------------------------------------------------------------------------------------");
                            // ---------------------------------------------------------------------------------------------------------------------------------

                        }
                        // --------------------------------------------------------------------------------------------------------

                        if (str_phoneOrder == "Y" || str_phoneOrder.equals("Y")) {
                        } else {
                            // Receipt No. ------------------------------------------------------------------------------------------------------------
//                            LinearLayout phonenumLn = new LinearLayout(MainActivity.mContext);
//                            phonenumLn.setLayoutParams(matchParentParams);
//                            phonenumLn.setOrientation(LinearLayout.HORIZONTAL);

//                            TextView phonenumLeftTv = new TextView(MainActivity.mContext);
//                            phonenumLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                            phonenumLeftTv.setText("Receipt No.");
//                            phonenumLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                            GlobalMemberValues.setTextStyleOnClover(phonenumLeftTv);
//                            phonenumLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
//                            phonenumLn.addView(phonenumLeftTv);
                            finalBuilder.addText("\n"+"Receipt No.");

//                            TextView phonenumRightTv = new TextView(MainActivity.mContext);
//                            phonenumRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                            phonenumRightTv.setText(" : " + str_receiptno[0]);
//                            phonenumRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                            GlobalMemberValues.setTextStyleOnClover(phonenumRightTv);
//                            phonenumRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                            phonenumLn.addView(phonenumRightTv);
                            finalBuilder.addText(" : " + str_receiptno[0]);

//                            printingLn.addView(phonenumLn);
                            // --------------------------------------------------------------------------------------------------------------------
                        }

                        if (GlobalMemberValues.mCustomerInfoShowYN) {
                            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(str_customername, " ", ""))) {
                                // Customer ------------------------------------------------------------------------------------------------------------
//                                LinearLayout customerLn = new LinearLayout(MainActivity.mContext);
//                                customerLn.setLayoutParams(matchParentParams);
//                                customerLn.setOrientation(LinearLayout.HORIZONTAL);

//                                TextView customerLeftTv = new TextView(MainActivity.mContext);
//                                customerLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                                customerLeftTv.setText("Customer");
//                                customerLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(customerLeftTv);
//                                customerLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
//                                customerLn.addView(customerLeftTv);
                                finalBuilder.addText("\n"+"Customer");

//                                TextView customerRightTv = new TextView(MainActivity.mContext);
//                                customerRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                                customerRightTv.setText(" : " + str_customername);
//                                customerRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(customerRightTv);
//                                customerRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                                customerLn.addView(customerRightTv);
                                finalBuilder.addText(" : " + str_customername);

//                                printingLn.addView(customerLn);
                                // --------------------------------------------------------------------------------------------------------------------
                            }

                            if (!GlobalMemberValues.isStrEmpty(str_customerphonenum)) {
                                // Phone No. ------------------------------------------------------------------------------------------------------------
//                                LinearLayout phonenumLn = new LinearLayout(MainActivity.mContext);
//                                phonenumLn.setLayoutParams(matchParentParams);
//                                phonenumLn.setOrientation(LinearLayout.HORIZONTAL);
//
//                                TextView phonenumLeftTv = new TextView(MainActivity.mContext);
//                                phonenumLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                                phonenumLeftTv.setText("Phone No.");
//                                phonenumLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(phonenumLeftTv);
//                                phonenumLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
//                                phonenumLn.addView(phonenumLeftTv);
                                finalBuilder.addText("\n"+"Phone No.");

//                                TextView phonenumRightTv = new TextView(MainActivity.mContext);
//                                phonenumRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                                phonenumRightTv.setText(" : " + telNumberExch(str_customerphonenum));
//                                phonenumRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(phonenumRightTv);
//                                phonenumRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                                phonenumLn.addView(phonenumRightTv);
                                finalBuilder.addText(" : " + telNumberExch(str_customerphonenum));

//                                printingLn.addView(phonenumLn);
                                // --------------------------------------------------------------------------------------------------------------------
                            }
                            if (!GlobalMemberValues.isStrEmpty(str_customeraddress)) {
                                // Address ------------------------------------------------------------------------------------------------------------
//                                TextView addressTv = new TextView(MainActivity.mContext);
//                                addressTv.setLayoutParams(matchParentParams);
//                                addressTv.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//                                addressTv.setText("<Address>");
//                                addressTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(addressTv);
//                                printingLn.addView(addressTv);
                                finalBuilder.addText("\n"+"<Address>");
                                // --------------------------------------------------------------------------------------------------------------------
                                // Address Contents ------------------------------------------------------------------------------------------------------------
//                                TextView addresscontentsTv = new TextView(MainActivity.mContext);
//                                addresscontentsTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                addresscontentsTv.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//                                addresscontentsTv.setText(str_customeraddress);
//                                addresscontentsTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                                GlobalMemberValues.setTextStyleOnClover(addresscontentsTv);
//                                printingLn.addView(addresscontentsTv);
                                finalBuilder.addText(str_customeraddress);
                                // --------------------------------------------------------------------------------------------------------------------

                                // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
//                                printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                                finalBuilder.addText("\n--------------------------------------------------------------------------------------");
                                // ---------------------------------------------------------------------------------------------------------------------------------
                            }
                        }

                        // Printed Time ------------------------------------------------------------------------------------------------------------
//                        LinearLayout printedtimeLn = new LinearLayout(MainActivity.mContext);
//                        printedtimeLn.setLayoutParams(matchParentParams);
//                        printedtimeLn.setOrientation(LinearLayout.HORIZONTAL);
//
//                        TextView printedtimeLeftTv = new TextView(MainActivity.mContext);
//                        printedtimeLeftTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                        printedtimeLeftTv.setText("Printed Time");
//                        printedtimeLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                        GlobalMemberValues.setTextStyleOnClover(printedtimeLeftTv);
//                        printedtimeLeftTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
//                        printedtimeLn.addView(printedtimeLeftTv);
                        finalBuilder.addText("\n"+"Printed Time");

//                        TextView printedtimeRightTv = new TextView(MainActivity.mContext);
//                        printedtimeRightTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                        printedtimeRightTv.setText(" : " + getDate() + " " + getTime());
//                        printedtimeRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                        GlobalMemberValues.setTextStyleOnClover(printedtimeRightTv);
//                        printedtimeRightTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
//                        printedtimeLn.addView(printedtimeRightTv);
                        finalBuilder.addText(" : " + getDate() + " " + getTime());

//                        printingLn.addView(printedtimeLn);
                        // --------------------------------------------------------------------------------------------------------------------

                        // 빈공간 -----------------------------------------------------------------------------------------------------------------------
//                        printingLn.addView(GlobalMemberValues.getSpaceZoneViewForClover(MainActivity.mContext, 30));
                        // ---------------------------------------------------------------------------------------------------------------------------------
                    }
                    GlobalMemberValues.setKitchenPrintedChangeStatus(str_receiptno[0], str_ordertype[0]);
                    GlobalMemberValues.setPhoneorderKitchenPrinted(str_receiptno[0]);
                }catch (Exception e){

                }

            }
        };

        thread.start();
        try {
            thread.join();
        } catch(InterruptedException e) {}

        return finalBuilder;
    }
    public static String getDate(){
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
    public static String getTime(){
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
    @SuppressLint("LongLogTag")
    private static String telNumberExch(String instr)
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
}
