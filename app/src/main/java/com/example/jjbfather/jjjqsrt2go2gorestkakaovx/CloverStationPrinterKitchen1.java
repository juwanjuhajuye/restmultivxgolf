package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.PrinterConnector;
import com.elo.device.ProductInfo;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by byullbam on 16. 2. 23..
 */
public class CloverStationPrinterKitchen1 {
    Context mContext;

    public ProductInfo productInfo;
    private ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    private EloPrinterReceipt.PrinterMake myPrinter;


    public CloverStationPrinterKitchen1(Context paramContext) {
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

    public void printKitchenCloverStation(final JSONObject data) {
        // 제일 첫번째 실행되는 PrinterKitchen1 에서만 아래 부분 초기화
        GlobalMemberValues.mKitchenPrintedQty = 0;

        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
        GlobalMemberValues.openCloseKitchenPrintLoading(true, "1");

        try {
            GlobalMemberValues.logWrite("waitingtime", "기다리는 시간1 : " + GlobalMemberValues.DELETEWAITING + "\n");
            Thread.sleep(2000); // 0.5 초 기다림
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data, "1");

        if (getPrintingLn != null) {
            GlobalMemberValues.printingViewOnClover(getPrintingLn);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------

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

}
