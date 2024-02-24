package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class BillExecute {
    public static String makeQueryByInsert_Bill(String paramHoldCode, String paramTableIdx,
                                                double paramAmount, String paramCartIdxs, String paramBillSplitType) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramHoldCode) && paramAmount > 0) {

            // 04212023
            double gratuityIns = 0;
            double subtotalIns = 0;
            double taxIns = 0;
            double totalamountIns = 0;

            double dcextra_Ins = 0;

            // 05152023
            double dcextraIns = 0;

            switch (BillSplitMerge.str_Billsplit_Type) {
                case "0" : {
                    int regCnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(" select count(*) from bill_list where holdcode = '" + paramHoldCode + "' ")
                    );

                    String tempSql = "";
                    String tempCartIdxArr[] = null;
                    tempCartIdxArr = paramCartIdxs.split(",");


                    // 05172023
                    // commongratuity 할인된 금액을 할인금액에 더하기
                    tempSql = " select sum(selectedDcExtraPrice) from temp_salecart where holdcode = '" + paramHoldCode + "' " +
                            " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
                    double dcextra_total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));
                    if (dcextra_total > 0) {
                        dcextra_Ins = dcextra_total / GlobalMemberValues.getDoubleAtString(BillSplitMerge.mBillSplitCount + "");
                        dcextra_Ins = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(dcextra_Ins, "2"));
                    }

                    // 05152023
                    // dcextra total --------------------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                        for (int i = 0; i < tempCartIdxArr.length; i++) {
                            dcextraIns += GlobalMemberValues.getDoubleAtString(
                                    MssqlDatabase.getResultSetValueToString(
                                            " select (selectedDcExtraPrice) from temp_salecart where idx = '" + tempCartIdxArr[i] + "' ")
                            );
                            dcextraIns = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(dcextraIns, "2"));
                        }
                    }
                    // dcextra total --------------------------------------------------------------------------------------------------

                    if ((BillSplitMerge.mBillSplitCount - regCnt) == 1) {
                        dcextra_Ins = dcextra_total - (dcextra_Ins * (BillSplitMerge.mBillSplitCount - 1));
                    }

                    dcextraIns = dcextraIns + dcextra_Ins;
                    // gratuity --------------------------------------------------------------------------------------------------

                    // sub total --------------------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                        for (int i = 0; i < tempCartIdxArr.length; i++) {
                            subtotalIns += GlobalMemberValues.getDoubleAtString(
                                    MssqlDatabase.getResultSetValueToString(
                                            " select sPriceBalAmount_org * sQty from temp_salecart where idx = '" + tempCartIdxArr[i] + "' ")
                            );
                            subtotalIns = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(subtotalIns, "2"));
                        }
                    }
                    // sub total --------------------------------------------------------------------------------------------------


                    // tax total --------------------------------------------------------------------------------------------------
                    if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                        for (int i = 0; i < tempCartIdxArr.length; i++) {
                            taxIns += GlobalMemberValues.getDoubleAtString(
                                    MssqlDatabase.getResultSetValueToString(
                                            " select sTaxAmount from temp_salecart where idx = '" + tempCartIdxArr[i] + "' ")
                            );
                            taxIns = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(taxIns, "2"));
                        }
                    }
                    // tax total --------------------------------------------------------------------------------------------------


                    // 05182023
                    // 아이템 금액 비율별 gratuity ------------------------------------------------------------------------------------
                    // 해당 주문에 common gratuity 가 있는지 확인해서 분할한다.
                    tempSql = " select sTotalAmount_org from temp_salecart where holdcode = '" + paramHoldCode + "' " +
                            " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
                    GlobalMemberValues.logWrite("billgratuitylog", "tempSql : " + tempSql + "\n");
                    double gratuityTotal = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));

                    GlobalMemberValues.logWrite("billgratuitylog", "gratuityTotal : " + gratuityTotal + "\n");
                    if (gratuityTotal > 0) {
                        tempSql = " select sum(sTotalAmount) from temp_salecart where holdcode = '" + paramHoldCode + "' " +
                                " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') ";
                        GlobalMemberValues.logWrite("billgratuitylog", "tempSql : " + tempSql + "\n");

                        double totalAmount_incart = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));

                        double itemtotal_bycartidx = 0;
                        if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                            for (int i = 0; i < tempCartIdxArr.length; i++) {
                                itemtotal_bycartidx += GlobalMemberValues.getDoubleAtString(
                                        MssqlDatabase.getResultSetValueToString(
                                                " select sTotalAmount from temp_salecart where idx = '" + tempCartIdxArr[i] + "' ")
                                );
                            }
                            double amtRate = 0.0;

                            GlobalMemberValues.logWrite("billgratuitygraalog", "itemtotal_bycartidx : " + itemtotal_bycartidx + "\n");
                            GlobalMemberValues.logWrite("billgratuitygraalog", "totalAmount_incart : " + totalAmount_incart + "\n");

                            if (itemtotal_bycartidx > 0 && totalAmount_incart > 0) {
                                amtRate = (double)itemtotal_bycartidx / (double)totalAmount_incart;
                                amtRate = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(amtRate, "2"));
                            }

                            GlobalMemberValues.logWrite("billgratuitygraalog", "amtRate : " + amtRate + "\n");

                            if ((BillSplitMerge.mBillSplitCount - regCnt) == 1) {
                                // 현재까지 등록된 gratuity 를 구한다.
                                tempSql = " select sum(gratuityamt) from bill_list where holdcode = '" + paramHoldCode + "' ";
                                double sumGratuity = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));
                                gratuityIns = gratuityTotal - sumGratuity;
                            } else {
                                // gratuityIns
                                gratuityIns = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((gratuityTotal * amtRate), "2"));
                            }

                            GlobalMemberValues.logWrite("billgratuitygraalog", "gratuityIns : " + gratuityIns + "\n");
                        }
                    }
                    // 아이템 금액 비율별 gratuity ------------------------------------------------------------------------------------

                    // totalamountIns = paramAmount;
                    totalamountIns = subtotalIns + taxIns + gratuityIns - dcextraIns;

                    paramAmount = totalamountIns;

                    break;
                }
                case "1" : {
                    if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                        String tempValue[] = null;
                        tempValue = paramCartIdxs.split("-jjj-");
                        gratuityIns = GlobalMemberValues.getDoubleAtString(tempValue[0]);
                        subtotalIns = GlobalMemberValues.getDoubleAtString(tempValue[1]);
                        taxIns = GlobalMemberValues.getDoubleAtString(tempValue[2]);

                        // 05152023
                        dcextraIns = GlobalMemberValues.getDoubleAtString(tempValue[3]);
                    }

                    totalamountIns = paramAmount;
                }

                // 05012023
                case "2" : {
                    if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                        String tempValue[] = null;
                        tempValue = paramCartIdxs.split("-jjj-");
                        gratuityIns = GlobalMemberValues.getDoubleAtString(tempValue[0]);
                        subtotalIns = GlobalMemberValues.getDoubleAtString(tempValue[1]);
                        taxIns = GlobalMemberValues.getDoubleAtString(tempValue[2]);
                    }

                    totalamountIns = paramAmount;
                }
            }
            // bill split 시 균등분배하면 아이템이 안나오는것 추적 0515
            returnValue = " insert into bill_list ( " +
                    " holdcode, sidx, stcode, billamount, tableidx, cartidxs, billsplittype, gratuityamt, subtotalamt, taxamt, totalamountAmt, dcextraAmt " +
                    " ) values ( " +
                    " '" + paramHoldCode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + paramAmount + "', " +
                    " '" + GlobalMemberValues.getReplaceText(paramTableIdx, "T", "") + "', " +
                    " '" + paramCartIdxs + "', " +
                    " '" + paramBillSplitType + "', " +
                    " '" + gratuityIns + "', " +
                    " '" + subtotalIns + "', " +
                    " '" + taxIns + "', " +
                    " '" + totalamountIns + "', " +
                    " '" + dcextraIns + "' " +
                    " ) ";
        }

        return returnValue;
    }

    // 메뉴별로 split
    public static String doInsertByMenu_Bill(String paramHoldCode, String paramTableIdx,
                                             double paramAmount, String paramCartIdxs) {
        String returnValue = "N";
        if (!GlobalMemberValues.isStrEmpty(paramHoldCode) && !GlobalMemberValues.isStrEmpty(paramTableIdx)
                && !GlobalMemberValues.isStrEmpty(paramCartIdxs) && paramAmount > 0) {
            Vector<String> vec = new Vector<String>();

            String tempSql = makeQueryByInsert_Bill(paramHoldCode, paramTableIdx, paramAmount, paramCartIdxs, "0");
            vec.add(tempSql);
            for (String tempQuery : vec) {
                GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
            }
            returnValue = MssqlDatabase.executeTransaction(vec);
        }




        // 05182023
        if (returnValue == "Y" || returnValue.equals("Y")) {
            if (!GlobalMemberValues.isStrEmpty(paramCartIdxs)) {
                Vector<String> vec2 = new Vector<String>();
                String tempSql2 = "";
                String newBillIdx = MssqlDatabase.getResultSetValueToString(" select top 1 idx from bill_list order by idx desc ");
                if (!GlobalMemberValues.isStrEmpty(newBillIdx)) {
                    String tempCartIdxArr[] = null;
                    tempCartIdxArr = paramCartIdxs.split(",");
                    for (int i = 0; i < tempCartIdxArr.length; i++) {
                        tempSql2 = " update temp_salecart set billidx_byitemsplit = '" + newBillIdx + "' where idx = '" + tempCartIdxArr[i] + "' ";
                        vec2.add(tempSql2);
                    }

                    for (String tempQuery : vec2) {
                        GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
                    }
                    String returnValue2 = "N";
                    returnValue2 = MssqlDatabase.executeTransaction(vec2);
                }
            }
        }




        TableSaleMain.mSelectedIdxArrListInCart.clear();
        return returnValue;
    }

    // Evenly split
    public static String doInsertByEvenly_Bill(String paramHoldCode, String paramTableIdx,
                                             double paramTotalAmount, int paramPeopleQty) {
        String returnValue = "N";

        GlobalMemberValues.logWrite("totalamtjjjlog", "paramTotalAmount1 : " + paramTotalAmount + "\n");

        if (!GlobalMemberValues.isStrEmpty(paramHoldCode) && !GlobalMemberValues.isStrEmpty(paramTableIdx)
                && paramTotalAmount > 0 && paramPeopleQty > 0) {
            Vector<String> vec = new Vector<String>();
            String tempSql = "";

            tempSql = " delete from bill_list where holdcode = '" + paramHoldCode + "' ";
            vec.add(tempSql);


            // 05152023
            // 전체 discount, extra 금액
            tempSql = " select sum(selectedDcExtraPrice) from temp_salecart where holdcode = '" + paramHoldCode + "' ";
            double dcextra_Total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));


            // 04212023
            tempSql = " select sTotalAmount_org from temp_salecart where holdcode = '" + paramHoldCode + "' " +
                    " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
            double gratuity_Total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));
            double subtotal_Total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sPriceBalAmount_org * sQty) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' " +
                                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') "
                    )
            );
            double tax_Total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sTaxAmount) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' " +
                                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') "
                    )
            );

            paramTotalAmount = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sTotalAmount) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' "
                    )
            );

//            paramTotalAmount = subtotal_Total + tax_Total + gratuity_Total - dcextra_Total;

            GlobalMemberValues.logWrite("totalamtjjjlog", "paramTotalAmount2 : " + paramTotalAmount + "\n");

            GlobalMemberValues.logWrite("billevensplitlogjjj", "gratuity_Total : " + gratuity_Total + "\n");
            GlobalMemberValues.logWrite("billevensplitlogjjj", "subtotal_Total : " + subtotal_Total + "\n");
            GlobalMemberValues.logWrite("billevensplitlogjjj", "tax_Total : " + tax_Total + "\n");
            GlobalMemberValues.logWrite("billevensplitlogjjj", "paramTotalAmount : " + paramTotalAmount + "\n");

            double tempTotalAmount = 0;

            // 04212023
            double tempTotal_gratuity = 0;
            double tempTotal_subtotal = 0;
            double tempTotal_tax = 0;

            // 05152023
            double tempTotal_dcextra = 0;

            GlobalMemberValues.logWrite("billevensplitlogjjj", "========================================" + "\n");

            for (int i = 0; i < paramPeopleQty; i++) {
                // 05152023
                double tempDcExtra = 0;
                if (dcextra_Total > 0) {
                    tempDcExtra = dcextra_Total / paramPeopleQty;
                }
                String tempDcExtraStr = String.format("%.2f", tempDcExtra);
                tempTotal_dcextra += GlobalMemberValues.getDoubleAtString(tempDcExtraStr);


                // 04212023 ----------------------------------------------------------------
                double tempGratuity = gratuity_Total / paramPeopleQty;
                String tempGratuityStr = String.format("%.2f", tempGratuity);
                tempTotal_gratuity += GlobalMemberValues.getDoubleAtString(tempGratuityStr);

                double tempSubtotal = subtotal_Total / paramPeopleQty;
                String tempSubtotalStr = String.format("%.2f", tempSubtotal);
                tempTotal_subtotal += GlobalMemberValues.getDoubleAtString(tempSubtotalStr);

                double tempTax = tax_Total / paramPeopleQty;
                String tempTaxStr = String.format("%.2f", tempTax);
                tempTotal_tax += GlobalMemberValues.getDoubleAtString(tempTaxStr);
                // 04212023 ----------------------------------------------------------------

                String tempAmount = "";
                double tempAmountDbl = GlobalMemberValues.getDoubleAtString(tempGratuityStr)
                        + GlobalMemberValues.getDoubleAtString(tempSubtotalStr)
                        + GlobalMemberValues.getDoubleAtString(tempTaxStr)
                        - GlobalMemberValues.getDoubleAtString(tempDcExtraStr);

                tempAmount = String.format("%.2f", tempAmountDbl);
                tempTotalAmount += GlobalMemberValues.getDoubleAtString(tempAmount);

                if (i == (paramPeopleQty -1)) {
                    double diffValue = paramTotalAmount - tempTotalAmount;
                    double tempTotal = GlobalMemberValues.getDoubleAtString(tempAmount) + diffValue;
                    tempAmount = String.format("%.2f", tempTotal);

                    // 04212023 ----------------------------------------------------------------
                    double diffValue2 = gratuity_Total - tempTotal_gratuity;
                    double insGratuity = GlobalMemberValues.getDoubleAtString(tempGratuityStr) + diffValue2;
                    tempGratuityStr = String.format("%.2f", insGratuity);

                    double diffValue3 = subtotal_Total - tempTotal_subtotal;
                    double insSubtotal = GlobalMemberValues.getDoubleAtString(tempSubtotalStr) + diffValue3;
                    tempSubtotalStr = String.format("%.2f", insSubtotal);

                    double diffValue4 = tax_Total - tempTotal_tax;
                    double insTax = GlobalMemberValues.getDoubleAtString(tempTaxStr) + diffValue4;
                    tempTaxStr = String.format("%.2f", insTax);


                    // 05152023
                    double diffValue5 = dcextra_Total - tempTotal_dcextra;
                    double insDcExtraTotal = GlobalMemberValues.getDoubleAtString(tempDcExtraStr) + diffValue5;
                    tempDcExtraStr = String.format("%.2f", insDcExtraTotal);


                    String insStr = tempGratuityStr + "-jjj-" + tempSubtotalStr + "-jjj-" + tempTaxStr + "-jjj-"
                            // 05152023
                            + tempDcExtraStr;
                    // 04212023 ----------------------------------------------------------------

                    tempSql = makeQueryByInsert_Bill(paramHoldCode, paramTableIdx,
                            GlobalMemberValues.getDoubleAtString(tempAmount), insStr, "1");
                    vec.add(tempSql);
                } else {
                    // 04212023 ----------------------------------------------------------------
                    String insStr = tempGratuityStr + "-jjj-" + tempSubtotalStr + "-jjj-" +tempTaxStr + "-jjj-"
                            // 05152023
                            + tempDcExtraStr;
                    // 04212023 ----------------------------------------------------------------

                    tempSql = makeQueryByInsert_Bill(paramHoldCode, paramTableIdx,
                            GlobalMemberValues.getDoubleAtString(tempAmount), insStr, "1");
                    vec.add(tempSql);
                }
                GlobalMemberValues.logWrite("billevensplitlogjjj", "gratuity amount : " + tempGratuityStr + "\n");
                GlobalMemberValues.logWrite("billevensplitlogjjj", "subtotal amount : " + tempSubtotalStr + "\n");
                GlobalMemberValues.logWrite("billevensplitlogjjj", "tax amount : " + tempTaxStr + "\n");
                GlobalMemberValues.logWrite("billevensplitlogjjj", "total amount : " + tempAmount + "\n");
                GlobalMemberValues.logWrite("billevensplitlogjjj", "-------------------------------------" + "\n");
            }

            GlobalMemberValues.logWrite("billevensplitlogjjj", "========================================" + "\n");

            for (String tempQuery : vec) {
                GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
            }
            returnValue = MssqlDatabase.executeTransaction(vec);
        }

        return returnValue;
    }

    // Amount split
    public static String doInsertByAmount_Bill(String paramHoldCode, String paramTableIdx, double paramAmount) {
        String returnValue = "N";
        if (!GlobalMemberValues.isStrEmpty(paramHoldCode) && !GlobalMemberValues.isStrEmpty(paramTableIdx) && paramAmount > 0) {
            // 0512023 -------------------------------------------------------------------------------------
            String tempSql = "";

            // 총액구하기
            // 05012023
            tempSql = " select sTotalAmount_org from temp_salecart where holdcode = '" + paramHoldCode + "' " +
                    " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
            double gratuity_Total = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempSql));
            double subtotal_Total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sPriceBalAmount_org * sQty) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' " +
                                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') "
                    )
            );
            double tax_Total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sTaxAmount) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' " +
                                    " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') "
                    )
            );
            double totalamount_total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(sTotalAmount) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' "
                    )
            );


            // 05152023
            double dcextra_Total = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(selectedDcExtraPrice) from temp_salecart " +
                                    " where holdcode = '" + paramHoldCode + "' "
                    )
            );


            // 현재까지 등록한 bill_list 의 정보를 가져온다.
            double saved_totalamount = GlobalMemberValues.getDoubleAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select sum(totalamountAmt) from bill_list " +
                                    " where holdcode = '" + paramHoldCode + "' "
                    )
            );

            // 마지막 저장인지 여부
            boolean isLastBill = false;
            if ((saved_totalamount + paramAmount) == totalamount_total) {
                isLastBill = true;
            }

            double ins_gratuity = 0;
            double ins_tax = 0;
            double ins_subtotal = 0;

            // 05152023
            double ins_dcextra = 0;

            if (!isLastBill) {                  // 마지막 bill 이 아닐 경우
                // 입력하는 amount 를 총액의 비율로 구한다.
                double amtRate = 0.0;
                if (paramAmount > 0 && totalamount_total > 0) {
                    amtRate = (double)paramAmount / (double)totalamount_total;
                    amtRate = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(amtRate, "2"));
                }
//            GlobalMemberValues.logWrite("amtratelog", "275 / 1000 : " + (double)275/(double)1000 + "\n");
                GlobalMemberValues.logWrite("amtratelog", "amtRate : " + amtRate + "\n");

                ins_gratuity = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((gratuity_Total * amtRate), "2"));
                ins_tax = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((tax_Total * amtRate), "2"));
                ins_subtotal = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((subtotal_Total * amtRate), "2"));

                // 05152023
                ins_dcextra = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((dcextra_Total * amtRate), "2"));
            } else {                            // 마지막 bill 일 경우
                // 현재까지 입력된 gratuity
                double saved_gratuity = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(gratuityamt) from bill_list " +
                                        " where holdcode = '" + paramHoldCode + "' "
                        )
                );
                ins_gratuity = (double)gratuity_Total - (double)saved_gratuity;

                // 현재까지 입력된 tax
                double saved_tax = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(taxamt) from bill_list " +
                                        " where holdcode = '" + paramHoldCode + "' "
                        )
                );
                ins_tax = (double)tax_Total - (double)saved_tax;

                // 현재까지 입력된 subtotal
                double saved_subtotal = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(subtotalamt) from bill_list " +
                                        " where holdcode = '" + paramHoldCode + "' "
                        )
                );
                ins_subtotal = (double)subtotal_Total - (double)saved_subtotal;

                // 05152023
                // 현재까지 입력된 dcextra
                double saved_dcextra = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(dcextraAmt) from bill_list " +
                                        " where holdcode = '" + paramHoldCode + "' "
                        )
                );
                ins_dcextra = (double)dcextra_Total - (double)saved_dcextra;
            }

            // 05152023
            String insStr = ins_gratuity + "-jjj-" + ins_subtotal + "-jjj-" + ins_tax + "-jjj-" + ins_dcextra;
            // 0512023 -------------------------------------------------------------------------------------

            Vector<String> vec = new Vector<String>();
            tempSql = makeQueryByInsert_Bill(paramHoldCode, paramTableIdx, paramAmount, insStr, "2");
            vec.add(tempSql);
            for (String tempQuery : vec) {
                GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
            }
            returnValue = MssqlDatabase.executeTransaction(vec);
        }

        return returnValue;
    }

    public static ArrayList<String> getBillList(String paramHoldCode, String paramTableIdx, String paramBillSplitType) {
        ArrayList<String> returnArrayList = null;
        if (!GlobalMemberValues.isStrEmpty(paramHoldCode) && !GlobalMemberValues.isStrEmpty(paramTableIdx) && !GlobalMemberValues.isStrEmpty(paramBillSplitType)) {
            returnArrayList = new ArrayList<String>();

            String get_idx = "";
            String get_billamount = "";
            String get_cartidxs = "";
            String get_tableidx = "";
            String get_tablename = "";
            String get_menuqty = "";
            String get_paidyn = "";

            String returnStr = "";

            String strQuery = " select A.idx, A.billamount, A.cartidxs, A.tableidx, A.paidyn from bill_list A " +
                    " where " +
                    " A.tableidx = '" + GlobalMemberValues.getReplaceText(paramTableIdx, "T", "") + "' " +
                    " and A.holdcode = '" + paramHoldCode + "' " +
                    " and A.billsplittype = '" + paramBillSplitType + "'" +
                    " order by A.idx asc ";
            ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (resultSet.next()) {
                    get_idx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                    get_billamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                    get_cartidxs = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                    get_tableidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);
                    get_paidyn = GlobalMemberValues.resultDB_checkNull_string(resultSet, 4);
                    if (GlobalMemberValues.isStrEmpty(get_paidyn)) {
                        get_paidyn = "N";
                    }
                    get_tablename = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(get_tableidx, "T", "") + "' "
                    );

                    get_menuqty = "0";
                    if (paramBillSplitType.equals("0")) {
                       if (!GlobalMemberValues.isStrEmpty(get_cartidxs)) {
                           String[] tempSplit = get_cartidxs.split(",");
                           get_menuqty = tempSplit.length + "";
                       }
                    } else {
                        get_menuqty = MssqlDatabase.getResultSetValueToString(
                                " select count(*) from temp_salecart where holdcode = '" + paramHoldCode + "' "
                        );
                    }

                    returnStr = (get_tablename + "-" + get_idx) + "-JJJ-" + get_menuqty +
                            "-JJJ-" + GlobalMemberValues.getDoubleAtString(get_billamount) +
                            "-JJJ-" + get_idx +
                            "-JJJ-" + get_cartidxs +
                            "-JJJ-" + get_paidyn;
                    returnArrayList.add(returnStr);
                }
            } catch (Exception e) {
            }
        }
        return returnArrayList;
    }

    public static String deleteBillSplit(String paramHoldCode) {
        Vector<String> vec = new Vector<String>();
        String tempSql = " delete from bill_list where holdcode = '" + paramHoldCode + "' ";
        vec.add(tempSql);
        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
        }
        String returnValue = MssqlDatabase.executeTransaction(vec);

        return returnValue;
    }



    public static String mergeBillSplit(Context paramContext, String paramBillListIdxs, String paramBillSplitType) {
        String returnValue = "N";

        if (!GlobalMemberValues.isStrEmpty(paramBillListIdxs)) {
            String tempStr[] = paramBillListIdxs.split(",");
            int tempCnt = tempStr.length;
            if (tempCnt < 2) {
                GlobalMemberValues.displayDialog(paramContext, "Check Merge", "Please select 2 or more to merge", "Close");
                returnValue = "N";
            } else {
                String tempSql = "";
                String baseIdx = tempStr[0];

                double totalBillAmount = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(billamount) from bill_list where idx in (" + paramBillListIdxs + ") "
                        )
                );

                if (paramBillSplitType.equals("0")) {
                    String cartIdxs_all = "";
                    for (int i = 0; i < tempCnt; i++) {
                        if (i > 0) {
                            cartIdxs_all += ",";
                        }
                        String tempCartIdxs = MssqlDatabase.getResultSetValueToString(
                                " select cartidxs from bill_list where idx = '" + tempStr[i] + "' "
                        );
                        cartIdxs_all += tempCartIdxs;
                    }

                    tempSql = " update from bill_list set billamount = '" + totalBillAmount + "', " +
                            " cartidxs = '" + cartIdxs_all + "' " +
                            " where idx = '" + baseIdx + "' ";
                } else {
                    tempSql = " update from bill_list set billamount = '" + totalBillAmount + "' where idx = '" + baseIdx + "' ";
                }

                Vector<String> vec = new Vector<String>();
                vec.add(tempSql);
                for (String tempQuery : vec) {
                    GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
                }
                returnValue = MssqlDatabase.executeTransaction(vec);
            }
        }

        return returnValue;
    }

    public static String deleteSelectedBillSplit(Context paramContext, String paramBillListIdxs) {
        String returnValue = "N";

        if (!GlobalMemberValues.isStrEmpty(paramBillListIdxs)) {
            String tempStr[] = paramBillListIdxs.split(",");
            int tempCnt = tempStr.length;
            if (tempCnt < 1) {
                GlobalMemberValues.displayDialog(paramContext, "Check Delete", "Please select 1 or more to delete", "Close");
                returnValue = "N";
            } else {
                String tempSql = "";

                Vector<String> vec = new Vector<String>();

                for (int i = 0; i < tempCnt; i++) {
                    tempSql = " delete from bill_list where idx = '" + tempStr[i] + "' ";
                    vec.add(tempSql);
                }

                for (String tempQuery : vec) {
                    GlobalMemberValues.logWrite("billquerystringjjj", "query : " + tempQuery + "\n");
                }
                returnValue = MssqlDatabase.executeTransaction(vec);
            }
        }

        return returnValue;
    }

    public static void closeCheckAmount(Activity paramActivity, Context paramContext,
                                        String paramHoldCode, double paramTotalAmount) {
        String returnValue = "N";

        double totalBillAmount = GlobalMemberValues.getDoubleAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select sum(billamount) from bill_list where holdcode = '" + paramHoldCode + "' "
                )
        );

        if (totalBillAmount == 0) {
            returnValue = "Y";
        } else {
            if (totalBillAmount < paramTotalAmount) {
                returnValue = "N";
            } else {
                returnValue = "Y";
            }
        }

        if (returnValue.equals("Y")) {
            BillSplitMerge.setInitValuesForBillPay();

            GlobalMemberValues.isOpenTableSaleMain = true;

            paramActivity.finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                paramActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        } else {
            new AlertDialog.Builder(paramContext)
                    .setTitle("Warning")
                    .setMessage("The amount of the split check does not match the total amount" +
                            "\nWould you like to delete all bills?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    BillSplitMerge.setInitValuesForBillPay();
                                    deleteBillSplit(paramHoldCode);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

//            GlobalMemberValues.displayDialog(paramContext, "Warning",
//                    "The amount of the split check does not match the total amount", "Close");
        }
    }

    public static boolean isInBillList_byCartIdx(String paramCartIdx) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramCartIdx)) {
            String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                    " select distinct holdcode from temp_salecart where idx = '" + paramCartIdx + "' "
            );

            String strQuery = " select cartidxs from bill_list " +
                    " where holdcode = '" + temp_holdcode + "' ";
            ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
            try {
                int tempCnt = 0;
                String tempCartIdxsAll = "";
                String get_cartidxs = "";
                while (resultSet.next()) {
                    get_cartidxs = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);

                    if (tempCnt > 0) {
                        tempCartIdxsAll += ",";
                    }
                    tempCartIdxsAll += get_cartidxs;

                    tempCnt++;
                }

                int inCnt = 0;
                if (!GlobalMemberValues.isStrEmpty(tempCartIdxsAll)) {
                    String arrCartIdxs[] = tempCartIdxsAll.split(",");
                    for (int i = 0; i < arrCartIdxs.length; i++) {
                        if (("JJJ" + paramCartIdx).equals("JJJ" + arrCartIdxs[i])) {
                            inCnt++;
                        }
                    }
                }

                if (inCnt > 0) {
                    returnValue = true;
                }
            } catch (Exception e) {
            }
        }

        return returnValue;
    }
}