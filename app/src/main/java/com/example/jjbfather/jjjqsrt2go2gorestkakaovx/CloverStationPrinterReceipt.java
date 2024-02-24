package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.PrinterConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CloverStationPrinterReceipt {
    Context mContext;

    String mPrintTextAll = "";

    public CloverStationPrinterReceipt(Context paramContext) {
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
                String spaceStr = "";
                int i_space = stringLength - strLength;
                StringBuilder temp_build = new StringBuilder(instr);
                for (int i = 0; i < i_space; i++){
                    spaceStr += " ";
                }
                temp_str = temp_build.toString() + spaceStr;
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

    public void openCashDrawerCloverStation(){
        GlobalMemberValues.cloverOpenDrawer();
    }

    public void printBatchCloverStation(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatch(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

    }

    public void printCashOutCloverStation(final JSONObject data) {
        try {
        GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
        Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(data, "");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printBatchDetailCloverStation(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForBatchDetail(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printSalesReceiptCloverStation(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogsale", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
            getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "sales", "merchant");

            if (getPrintingLn != null) {
                GlobalMemberValues.printingViewOnClover(getPrintingLn);
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
                    GlobalMemberValues.printingViewOnClover(getPrintingLn);
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
//            GlobalMemberValues.cloverOpenDrawer();
            try {
                if (data.get("totalamount").equals(data.get("creditcardtendered"))){

                } else {
                    GlobalMemberValues.cloverOpenDrawer();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public void printVoidReceiptCloverStation(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogvoid", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "void", "merchant");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
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
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "void", "customer");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

    }


    public void printReturnReceiptCloverStation(final JSONObject data) {

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogreturn", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;

        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(data, "return", "merchant");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
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
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        SaleHistory.mActivity.recreate();
    }

    public void printPhoneOrderCheckPrintCloverStation(final JSONObject data) {
        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(500); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("cloverprintinglogphoneorder", "받은 데이터 : " + data.toString() + "\n");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForPhoneOrder(data);

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

    public void printTestCloverStation(final JSONObject data) {
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
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
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
