package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.PrinterConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaxPrinterReceipt {
    Context mContext;

    String mPrintTextAll = "";

    public PaxPrinterReceipt(Context paramContext) {
        mContext = paramContext;
        if (mContext == null) {
            mContext = Payment.context;
        }
        if (mContext == null) {
            mContext = MainActivity.mContext;
        }

        MainActivity.account_clover = CloverAccount.getAccount(paramContext);
        MainActivity.printerConnector_clover = new PrinterConnector(paramContext, MainActivity.account_clover, null);
    }

    public void openCashDrawerPax(){
        GlobalMemberValues.paxOpenDrawer();
    }

    public void printBatchPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatch(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

    }

    public void printCashOutPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(data, "");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printStartingCashPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForStartingCash(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }


    public void printBatchDetailPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatchDetail(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printSalesReceiptPax(final JSONObject data) {
        /**
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         **/
        GlobalMemberValues.logWrite("paxprintinglogsale", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
            getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "sales", "merchant");

            if (getPrintingLn != null) {
                GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
            }
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------


        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
            if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "sales", "customer");
                if (getPrintingLn != null) {
                    GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
                }
            }
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        // reprint 일 경우...
        if (GlobalMemberValues.mReReceiptprintYN == "Y" || GlobalMemberValues.mReReceiptprintYN.equals("Y")) {
            GlobalMemberValues.mReReceiptprintYN = "N";
            return;
        }

        // 돈통 열기 cash drawer
        if (GlobalMemberValues.isCashDrawerOpenOnReceipt()) {
            try {
                if (data.get("totalamount").equals(data.get("creditcardtendered"))){

                } else {
                    GlobalMemberValues.paxOpenDrawer();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            GlobalMemberValues.paxOpenDrawer();
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
                // 주방프린트 하기 ----------------------------------------------------------------
                GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                // -------------------------------------------------------------------------------
            } else {
                if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                        // 주방프린트 하기 ----------------------------------------------------------------
                        GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                        // -------------------------------------------------------------------------------
                    }
                }
            }
        }

        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            if (GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING == "N" || GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING.equals("N")) {

                if (GlobalMemberValues.RECEIPTPRINTTYPE.equals("")) {
                    PaymentCustomerSelectReceipt.finishPayment();
                }
                GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
            }
        }

    }

    public void printVoidReceiptPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogvoid", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "void", "merchant");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------


        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Customer 영수증 프린트 -----------------------------------------------`-----------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "void", "customer");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

    }


    public void printReturnReceiptPax(final JSONObject data) {

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogreturn", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "return", "merchant");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "return", "customer");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        SaleHistory.mActivity.recreate();
    }

    public void printPhoneOrderCheckPrintPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printTablemain_CheckPrintPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("paxprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTableSaleView(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }


    public void printTestPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForTest(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printMenuPax(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        ArrayList<LinearLayout> getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout_menu(data);

        if (getPrintingLn != null) {
            for (int i = 0 ; i < getPrintingLn.size() ; i++){
                GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn.get(i));
            }

        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
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
