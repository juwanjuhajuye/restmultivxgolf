package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

import org.json.JSONObject;

/**
 * Created by byullbam on 16. 2. 23..
 */
public class EloProPrinterKitchenByView5 {
    Context mContext;

    public ProductInfo productInfo;
    private ApiAdapter apiAdapter;

    public enum PrinterMake {STAR, RONGTA};
    private EloPrinterReceipt.PrinterMake myPrinter;


    public EloProPrinterKitchenByView5(Context paramContext) {

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

    public void printKitchenEloPro(final JSONObject data) {
        // 제일 첫번째 실행되는 PrinterKitchen1 에서만 아래 부분 초기화
        GlobalMemberValues.mKitchenPrintedQty = 0;

        GlobalMemberValues.mKitchenPrintedQty++;

        // 키친프린터 로딩창 띄움...
        GlobalMemberValues.openCloseKitchenPrintLoading(true, "5");

        String strMadeFilePath = "";

        String receiptNo = GlobalMemberValues.getDataInJsonData(data, "receiptno");
        String orderType = GlobalMemberValues.getDataInJsonData(data, "ordertype");

        // ---------------------------------------------------------------------------------------------------------------------------------------------------
        LinearLayout getPrintingLn = null;
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayoutForKitchen(data, "5");

        if (GlobalMemberValues.isPossibleKitchenPrinting(data, "5") && getPrintingLn != null) {
            if (getPrintingLn != null) {
//                    GlobalMemberValues.printingViewOnPax(mContext,getPrintingLn);
                for (int temp = 0; temp < GlobalMemberValues.getKitchenPrintingCount("5"); temp++){
                    GlobalMemberValues.printingViewOnElo(mContext,getPrintingLn,apiAdapter);
                    GlobalMemberValues.eloProCutPaper(apiAdapter);
                }
                GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
                GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);
            }
//            // 리턴받은 LinearLayout 으로 이미지생성
//            strMadeFilePath = GlobalMemberValues.makeImageFromLayout(getPrintingLn, receiptNo);
//            // 프린터2 실행 ---------------------------------------------------------------------------------------------
//            if (new File(strMadeFilePath).exists()) {
//                // 이미지 프린팅
//                GlobalMemberValues.eloProPrintToFilePath(strMadeFilePath, apiAdapter);
//
//                GlobalMemberValues.eloProCutPaper(apiAdapter);
//
//                GlobalMemberValues.setKitchenPrintedChangeStatus(receiptNo, orderType);
//                GlobalMemberValues.setPhoneorderKitchenPrinted(receiptNo);
//            }

            // ----------------------------------------------------------------------------------------------------------
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
        //GlobalMemberValues.printReceiptByKitchen5(data, MainActivity.mContext, "kitchen5");

        // 영수증 이미지 삭제 ---------------------------------------------------------------------------------------
        /**
         try {
         Thread.sleep(2000); // 0.5 초 기다림
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         if (new File(strMadeFilePath).exists()) {
         if (!GlobalMemberValues.isStrEmpty(strMadeFilePath) && new File(strMadeFilePath).exists()) {
         new File(strMadeFilePath).delete();
         }
         }
         **/
        // ------------------------------------------------------------------------------------------------------------

        //Payment.openPaymentReview(Payment.context);
    }

    public void printTestElo(final JSONObject data) {
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
            GlobalMemberValues.printingViewOnElo(mContext,getPrintingLn,apiAdapter);
            GlobalMemberValues.eloProCutPaper(apiAdapter);
        }
        getPrintingLn = null;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
    }

}
