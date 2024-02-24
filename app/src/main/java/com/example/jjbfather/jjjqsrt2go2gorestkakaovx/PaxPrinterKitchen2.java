package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.widget.LinearLayout;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.PrinterConnector;
import com.elo.device.ProductInfo;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

import org.json.JSONObject;

public class PaxPrinterKitchen2 {
    Context mContext;

    public ProductInfo productInfo;
    private ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    private EloPrinterReceipt.PrinterMake myPrinter;


    public PaxPrinterKitchen2(Context paramContext) {
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

    public void printKitchenPax(final JSONObject data) {
        GlobalMemberValues.logWrite("kitchenprinterdatalog", "data : " + data.toString() + "\n");

        // 제일 첫번째 실행되는 PrinterKitchen1 에서만 아래 부분 초기화
        GlobalMemberValues.mKitchenPrintedQty = 0;

        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
//        GlobalMemberValues.openCloseKitchenPrintLoading(true, "1");

        LinearLayout getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data, "2");

        if (GlobalMemberValues.isPossibleKitchenPrinting(data, "2") && getPrintingLn != null) {
//            GlobalMemberValues.printingViewOnClover(getPrintingLn);
            for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("2"); temp++){
                GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
            }

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
        //GlobalMemberValues.printReceiptByKitchen3(data, MainActivity.mContext, "kitchen3");

        //Payment.openPaymentReview(Payment.context);
    }

    public void printTestPaxStation(final JSONObject data) {
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
}
