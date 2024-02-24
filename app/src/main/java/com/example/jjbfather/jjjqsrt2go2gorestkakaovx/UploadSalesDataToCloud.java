package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.IBinder;

import java.sql.ResultSet;
import java.util.Vector;

public class UploadSalesDataToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    // SalesCode
    String receivedSalesCode;

    public UploadSalesDataToCloud() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = Payment.dbInit;
        receivedSalesCode = Payment.mSalesCode;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = Payment.dbInit;
        receivedSalesCode = Payment.mSalesCode;

        //Thread mThread = new Thread(this);
        //mThread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            // 05052023
            // Wingman 로봇팔에 데이터 전송
            GlobalMemberValues.dataSendToWingman();

            //sendSalesDataToCloud(receivedSalesCode);
            if (!GlobalMemberValues.isSaleDataUploadPause()) {
                sendSalesDataToCloud();
            } else {
                // 서비스 중지
                if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALE != null)
                    GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALE);
            }
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(Payment.context, "Warning", e.getMessage().toString(), "Close");
        }
    }

    public String getNumericChangeValue(String paramValue) {
        String returnValue = paramValue;
        if (!GlobalMemberValues.isStrEmpty(paramValue)) {
            paramValue = GlobalMemberValues.getReplaceText(paramValue, " ", "");
            String tempHeadValues = GlobalMemberValues.getJJJSubString(paramValue, 0, 2);
            if (tempHeadValues.equals("--")) {
                double tempValue = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getReplaceText(paramValue, "--", ""));
                returnValue = tempValue + "";
            }
        }
        return returnValue;
    }

    public void sendSalesDataToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0 && !GlobalMemberValues.isSaleDataUploadPause()) {
            // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
            String schDateStr = GlobalMemberValues.getSearchDateString(dbInitForUploadCloud, "saledate");
            // -------------------------------------------------------------------------------------------------------------------------------------------------

            String addSqlStr = "";
            if (GlobalMemberValues.isUploadOnlySaledData() && !GlobalMemberValues.isStrEmpty(GlobalMemberValues.RECEIPTNOFORUPLOAD)) {
                addSqlStr = " and salesCode = '" + GlobalMemberValues.RECEIPTNOFORUPLOAD + "' ";
            }

            String strSalonSalesDetailSalesCode = "select salesCode, parentSalesCode, status, idx, parentSalesIdx, returnCode, holdcode, frommssql " +
                    " from salon_sales_detail " +
                    " where isCloudUpload = 0 " +
                    " and " + schDateStr + addSqlStr +
                    " order by idx asc";
            GlobalMemberValues.logWrite("uploaddatalog", "sql : " + strSalonSalesDetailSalesCode + "\n");

            Cursor salonSalesDetailSalesCodeCursor = dbInitForUploadCloud.dbExecuteRead(strSalonSalesDetailSalesCode);
            while (salonSalesDetailSalesCodeCursor.moveToNext()) {
                String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(0), 1);
                String tempParentSalesCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(1), 1);
                String tempSalesDetailStatus = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(2), 1);
                String tempSalesDetailIdx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(3), 1);
                String tempParentSalesIdx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(4), 1);
                String tempReturnCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(5), 1);
                String tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(6), 1);
                String tempFrommssql  = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailSalesCodeCursor.getString(7), 1);

                String tempSchSalesCode = tempSalesCode;
                String tempSchSalesidx = tempSalesDetailIdx;

                if (GlobalMemberValues.isStrEmpty(tempFrommssql)) {
                    tempFrommssql = "N";
                }

                String mathFlag = "";
                if (!GlobalMemberValues.isStrEmpty(tempParentSalesCode)) {
                    if (tempFrommssql == "N" || tempFrommssql.equals("N")) {
                        tempSchSalesCode = tempParentSalesCode;
                        mathFlag = "-";
                    }
                    GlobalMemberValues.logWrite("tempParentSalesCode", "ParentSalesCode : " + tempParentSalesCode + "\n");
                }
                if (!GlobalMemberValues.isStrEmpty(tempParentSalesIdx)) {
                    if (tempFrommssql == "N" || tempFrommssql.equals("N")) {
                        tempSchSalesidx = tempParentSalesIdx;
                        mathFlag = "-";
                    }
                    GlobalMemberValues.logWrite("tempParentSalesCode", "tempParentSalesIdx : " + tempParentSalesIdx + "\n");
                }
                if (!GlobalMemberValues.isStrEmpty(tempSalesCode)) {
                    Vector<String> apiVec = new Vector<String>();

                    String tempApi_SCODE = GlobalMemberValues.SALON_CODE;
                    String tempApi_SIDX = GlobalMemberValues.STORE_INDEX;
                    String tempApi_IDG_COD = tempSalesCode;

                    String tempApi_HoldCode = tempHoldCode;

                    GlobalMemberValues.logWrite("voidtestsaleslog", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("voidtestsaleslog", "tempApi_IDG_COD : " + tempApi_IDG_COD + "\n");

                    // 고객 전화번호 ---------------------------------------------------------------------------------------
                    String tempApi_customerphonenumber = "";
                    tempApi_customerphonenumber = dbInitForUploadCloud.dbExecuteReadReturnString(
                            "select customerId from salon_sales where salesCode = '" + tempSalesCode + "' " );
                    if (GlobalMemberValues.isStrEmpty(tempApi_customerphonenumber)) {
                        tempApi_customerphonenumber = "";
                    }
                    if (tempApi_customerphonenumber.toLowerCase().equals("walk in")) {
                        tempApi_customerphonenumber = "";
                    }
                    // -----------------------------------------------------------------------------------------------------

                    // void 일 경우 cancelreason ----------------------------------------------------------------------------
                    String tempApi_cancelreason = "";
                    GlobalMemberValues.logWrite("ApiMStrUrlUpdateLogJJJ", "tempSalesCode : " + tempSalesCode + "\n");
                    if (tempSalesCode.substring(0, 1).equals("V")) {
                        String temp_SalesCode = "K" + tempSalesCode.substring(1);
                        GlobalMemberValues.logWrite("ApiMStrUrlUpdateLogJJJ", "temp_SalesCode : " + temp_SalesCode + "\n");
                        tempApi_cancelreason = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select cancelreason from salon_sales where salesCode = '" + temp_SalesCode + "' and not(cancelreason = '' or cancelreason is null) " );
                        if (GlobalMemberValues.isStrEmpty(tempApi_cancelreason)) {
                            tempApi_cancelreason = "";
                        }

                        GlobalMemberValues.logWrite("ApiMStrUrlUpdateLogJJJ", "tempApi_cancelreason : " + tempApi_cancelreason + "\n");
                    }
                    // -----------------------------------------------------------------------------------------------------

                    // 주문번호에 해당되는 전체 주문건수
                    double SalesDetailCnt = GlobalMemberValues.getDoubleAtString(
                            dbInitForUploadCloud.dbExecuteReadReturnString("select count(idx) from salon_sales_detail where salesCode = '" + tempSchSalesCode + "' "));

                    // 고객수 구하기 ----------------------------------------------------------------------------------
                    double tempApi_CUS_CNT = 1.0 / SalesDetailCnt;
                    // ------------------------------------------------------------------------------------------------

                    // 결제종류 및 비율 가져오기 ----------------------------------------------------------------------
                    String useTotalCashRatio = "0.0";
                    String useTotalCardRatio = "0.0";
                    String useTotalGiftCardRatio = "0.0";
                    String useTotalCheckRatio = "0.0";
                    String useTotalPointRatio = "0.0";
                    String tempApi_TEN_TY1 = "";
                    String tempApi_TEN_TY2 = "";
                    String tempApi_TEN_TY3 = "";
                    String tempApi_TEN_TY4 = "";
                    String tempApi_TEN_TY5 = "";
                    String tempApi_SER_YMD = "";
                    String tempApi_SER_MON = "";

                    String totalCashPayUsed = "0";
                    String totalCardPayUsed = "0";
                    String totalGiftCardPayUsed = "0";
                    String totalCheckPayUsed = "0";
                    String totalPointPayUsed = "0";

                    String totalCardPayUsedByVisa = "0";
                    String totalCardPayUsedByMaster = "0";
                    String totalCardPayUsedByAmex = "0";
                    String totalCardPayUsedByDiscover = "0";
                    String totalCardPayUsedByOffLine = "0";

                    String totalDiscountExtraPrice = "0";
                    String totalDiscountPrice = "0";
                    String totalExtraPrice = "0";
                    String allDiscountExtraPrice = "0";


                    String totalChangeMoney = "0";

                    String strSaleSales = "";
                    if (tempSalesDetailStatus == "3" || tempSalesDetailStatus.equals("3")) {
                        strSaleSales = "select distinct " +
                                " A.isTotalCashUse, A.isTotalCardUse, A.isTotalGiftCardUse, A.isTotalCheckUse, A.isTotalPointUse, " +
                                " A.useTotalCashRatio, A.useTotalCardRatio, A.useTotalGiftCardRatio, A.useTotalCheckRatio, A.useTotalPointRatio " +
                                " from salon_sales_return A left join salon_sales_detail B on A.returnCode = B.returnCode " +
                                " where A.returnCode = '" + tempReturnCode + "' ";
                    } else {
                        String tempSchSalesCode2 = tempSchSalesCode;
                        if (tempFrommssql == "Y" || tempFrommssql.equals("Y")) {
                            tempSchSalesCode2 = "K" + tempSchSalesCode.substring(1);
                        }
                        strSaleSales = "select " +
                                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio " +
                                " from salon_sales where salesCode = '" + tempSchSalesCode2 + "' ";
                    }

                    /** 결제방법(수단)별 결제금액 및 전체할인/추가금액 **********************************************************/
                    GlobalMemberValues.logWrite("paymethodsql", "tempSalesCode : " + tempSalesCode + "\n");

                    String tempSalesCodeK = "K" + tempSalesCode.substring(1);
                    String tempHeadOfSalesCode = tempSalesCode.substring(0, 1);


                    // 거스름돈(잔돈) 금액 ----------------------------------------------------------------------------------
                    totalChangeMoney = dbInitForUploadCloud.dbExecuteReadReturnString(
                            "select changeMoney from salon_sales where salesCode = '" + tempSalesCode + "' " );
                    if (GlobalMemberValues.isStrEmpty(totalChangeMoney)) {
                        totalChangeMoney = "0";
                    }
                    // -----------------------------------------------------------------------------------------------------

                    if (tempHeadOfSalesCode == "C" || tempHeadOfSalesCode.equals("C")) {            // 리턴일 때
                        // 현금 결제 금액 ----------------------------------------------------------------------------------
                        totalCashPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCashAmount from salon_sales_return where returnCode = '" + tempReturnCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCashPayUsed)) {
                            totalCashPayUsed = "0";
                        } else {
                            totalCashPayUsed = getNumericChangeValue("-" + totalCashPayUsed);
                        }
                        // -------------------------------------------------------------------------------------------------
                        // 카드 결제 금액 ----------------------------------------------------------------------------------
                        totalCardPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCardAmount from salon_sales_return where returnCode = '" + tempReturnCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCardPayUsed)) {
                            totalCardPayUsed = "0";
                        } else {
                            totalCardPayUsed = getNumericChangeValue("-" + totalCardPayUsed);
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 기프트카드 결제 금액 --------------------------------------------------------------------------------
                        totalGiftCardPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalGiftCardAmount from salon_sales_return where returnCode = '" + tempReturnCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalGiftCardPayUsed)) {
                            totalGiftCardPayUsed = "0";
                        } else {
                            totalGiftCardPayUsed = getNumericChangeValue("-" + totalGiftCardPayUsed);
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 체크 결제 금액 --------------------------------------------------------------------------------------
                        totalCheckPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCheckAmount from salon_sales_return where returnCode = '" + tempReturnCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCheckPayUsed)) {
                            totalCheckPayUsed = "0";
                        } else {
                            totalCheckPayUsed = getNumericChangeValue("-" + totalCheckPayUsed);
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 포인트 결제 금액 ------------------------------------------------------------------------------------
                        totalPointPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalPointAmount from salon_sales_return where returnCode = '" + tempReturnCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalPointPayUsed)) {
                            totalPointPayUsed = "0";
                        } else {
                            totalPointPayUsed = getNumericChangeValue("-" + totalPointPayUsed);
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 카드결제금액이 있을 경우 (0 이 아닐 경우)
                        // 카드사별 결제금액을 구한다.
                        if (GlobalMemberValues.getDoubleAtString(totalCardPayUsed) > 0 || GlobalMemberValues.getDoubleAtString(totalCardPayUsed) < 0 ) {
                            // Visa 카드 결제금액
                            totalCardPayUsedByVisa = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where returnCode = '" + tempReturnCode + "' and cardCom = 'V' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByVisa)) {
                                totalCardPayUsedByVisa = "0";
                            }
                            // Master 카드 결제금액
                            totalCardPayUsedByMaster = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where returnCode = '" + tempReturnCode + "' and cardCom = 'M' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByMaster)) {
                                totalCardPayUsedByMaster = "0";
                            }
                            // Amex 카드 결제금액
                            totalCardPayUsedByAmex = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where returnCode = '" + tempReturnCode + "' and cardCom = 'A' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByAmex)) {
                                totalCardPayUsedByAmex = "0";
                            }
                            // Discover 카드 결제금액
                            totalCardPayUsedByDiscover = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where returnCode = '" + tempReturnCode + "' and cardCom = 'D' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByDiscover)) {
                                totalCardPayUsedByDiscover = "0";
                            }
                            // Off Line 카드 결제금액
                            totalCardPayUsedByOffLine = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where returnCode = '" + tempReturnCode + "' and cardCom = 'OFFLINECARD' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByOffLine)) {
                                totalCardPayUsedByOffLine = "0";
                            }
                        }
                    } else {                                                                        // 세일 또는 보이드 일 때
                        // 현금 결제 금액 --------------------------------------------------------------------------------------
                        totalCashPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCashAmount from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCashPayUsed)) {
                            totalCashPayUsed = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 카드 결제 금액 --------------------------------------------------------------------------------------
                        totalCardPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCardAmount from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCardPayUsed)) {
                            totalCardPayUsed = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 기프트카드 결제 금액 --------------------------------------------------------------------------------
                        totalGiftCardPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalGiftCardAmount from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalGiftCardPayUsed)) {
                            totalGiftCardPayUsed = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 체크 결제 금액 --------------------------------------------------------------------------------------
                        totalCheckPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalCheckAmount from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalCheckPayUsed)) {
                            totalCheckPayUsed = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------
                        // 포인트 결제 금액 ------------------------------------------------------------------------------------
                        totalPointPayUsed = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select useTotalPointAmount from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalPointPayUsed)) {
                            totalPointPayUsed = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        if (tempHeadOfSalesCode == "V" || tempHeadOfSalesCode.equals("V")) {
                            if (totalCashPayUsed != "0" || !totalCashPayUsed.equals("0")) {
                                totalCashPayUsed = getNumericChangeValue("-" + totalCashPayUsed);
                            }
                            if (totalCardPayUsed != "0" || !totalCardPayUsed.equals("0")) {
                                totalCardPayUsed = getNumericChangeValue("-" + totalCardPayUsed);
                            }
                            if (totalGiftCardPayUsed != "0" || !totalGiftCardPayUsed.equals("0")) {
                                totalGiftCardPayUsed = getNumericChangeValue("-" + totalGiftCardPayUsed);
                            }
                            if (totalCheckPayUsed != "0" || !totalCheckPayUsed.equals("0")) {
                                totalCheckPayUsed = getNumericChangeValue("-" + totalCheckPayUsed);
                            }
                            if (totalPointPayUsed != "0" || !totalPointPayUsed.equals("0")) {
                                totalPointPayUsed = getNumericChangeValue("-" + totalPointPayUsed);
                            }
                        }

                        // 카드결제금액이 있을 경우 (0 이 아닐 경우)
                        // 카드사별 결제금액을 구한다.
                        if (GlobalMemberValues.getDoubleAtString(totalCardPayUsed) > 0 || GlobalMemberValues.getDoubleAtString(totalCardPayUsed) < 0 ) {
                            // Visa 카드 결제금액
                            totalCardPayUsedByVisa = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where salesCode = '" + tempSalesCode + "' and cardCom = 'V' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByVisa)) {
                                totalCardPayUsedByVisa = "0";
                            }
                            // Master 카드 결제금액
                            totalCardPayUsedByMaster = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where salesCode = '" + tempSalesCode + "' and cardCom = 'M' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByMaster)) {
                                totalCardPayUsedByMaster = "0";
                            }
                            // Amex 카드 결제금액
                            totalCardPayUsedByAmex = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where salesCode = '" + tempSalesCode + "' and cardCom = 'A' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByAmex)) {
                                totalCardPayUsedByAmex = "0";
                            }
                            // Discover 카드 결제금액
                            totalCardPayUsedByDiscover = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where salesCode = '" + tempSalesCode + "' and cardCom = 'D' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByDiscover)) {
                                totalCardPayUsedByDiscover = "0";
                            }
                            // Off Line 카드 결제금액
                            totalCardPayUsedByOffLine = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select sum(priceAmount) from salon_sales_card where salesCode = '" + tempSalesCode + "' and cardCom = 'OFFLINECARD' and delyn = 'N' " );
                            if (GlobalMemberValues.isStrEmpty(totalCardPayUsedByOffLine)) {
                                totalCardPayUsedByOffLine = "0";
                            }
                        }
                    }
                    /*******************************************************************************************************/

                    /** 할인 / 추가 금액 관련 *******************************************************************************/
                    if (tempSalesCode.substring(0, 1).equals("K") || tempSalesCode.substring(0, 1).equals("V")) {       // 세일 또는 보이드일때
                        // 전체 할인/추가 금액 ---------------------------------------------------------------------------------
                        totalDiscountExtraPrice = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select totalDiscountExtraPrice from salon_sales where salesCode = 'K" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalDiscountExtraPrice)) {
                            totalDiscountExtraPrice = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 전체 할인(개별) 금액 ---------------------------------------------------------------------------------
                        totalDiscountPrice = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select totalDiscountPrice from salon_sales where salesCode = 'K" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalDiscountPrice)) {
                            totalDiscountPrice = "0";
                        }

                        GlobalMemberValues.logWrite("totaldclog", "totaldc1 : " + totalDiscountPrice + "\n");
                        // -----------------------------------------------------------------------------------------------------

                        // 전체 추가(개별) 금액 ---------------------------------------------------------------------------------
                        totalExtraPrice = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select totalExtraPrice from salon_sales where salesCode = 'K" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalExtraPrice)) {
                            totalExtraPrice = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // All Discount / Extra 금액 ----------------------------------------------------------------------------
                        allDiscountExtraPrice = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select allDiscountExtraPrice from salon_sales where salesCode = 'K" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(allDiscountExtraPrice)) {
                            allDiscountExtraPrice = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 보이드 일 때
                        if (tempHeadOfSalesCode == "V" || tempHeadOfSalesCode.equals("V")) {
                            double temp_totalDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(totalDiscountExtraPrice);
                            temp_totalDiscountExtraPrice = temp_totalDiscountExtraPrice * -1;

                            double temp_totalDiscountPrice = GlobalMemberValues.getDoubleAtString(totalDiscountPrice);
                            temp_totalDiscountPrice = temp_totalDiscountPrice * -1;

                            double temp_totalExtraPrice = GlobalMemberValues.getDoubleAtString(totalExtraPrice);
                            temp_totalExtraPrice = temp_totalExtraPrice * -1;

                            double temp_allDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);
                            temp_allDiscountExtraPrice = temp_allDiscountExtraPrice * -1;

                            totalDiscountExtraPrice = temp_totalDiscountExtraPrice + "";
                            totalDiscountPrice = temp_totalDiscountPrice + "";
                            totalExtraPrice = temp_totalExtraPrice + "";
                            allDiscountExtraPrice = temp_allDiscountExtraPrice + "";

                            GlobalMemberValues.logWrite("totaldclog", "totaldc2 : " + totalDiscountPrice + "\n");
                        }
                    } else {                                                                            // 리턴일 때
                        // 전체 할인/추가 종류 ---------------------------------------------------------------------------------
                        String totalDiscountExtraType = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select eachDiscountExtraType from salon_sales_detail where salesCode = '" + tempSalesCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalDiscountExtraType)) {
                            totalDiscountExtraType = "";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 전체 할인/추가 금액 ---------------------------------------------------------------------------------
                        totalDiscountExtraPrice = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select eachDiscountExtraPrice from salon_sales_detail where salesCode = '" + tempSalesCode + "' " );
                        if (GlobalMemberValues.isStrEmpty(totalDiscountExtraPrice)) {
                            totalDiscountExtraPrice = "0";
                        }

                        if (GlobalMemberValues.getDoubleAtString(totalDiscountExtraPrice) != 0) {
                            if (totalDiscountExtraType.equals("DC")) {
                                totalDiscountExtraPrice = getNumericChangeValue("-" + totalDiscountExtraPrice);
                            }
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 전체 추가(개별) 금액 ---------------------------------------------------------------------------------
                        if (GlobalMemberValues.getDoubleAtString(totalDiscountExtraPrice) > 0) {
                            totalExtraPrice = totalDiscountExtraPrice;
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 전체 할인(개별) 금액 ---------------------------------------------------------------------------------
                        if (GlobalMemberValues.getDoubleAtString(totalDiscountExtraPrice) < 0) {
                            totalDiscountPrice = totalDiscountExtraPrice;
                        }

                        GlobalMemberValues.logWrite("totaldclog", "totaldc3 : " + totalDiscountPrice + "\n");
                        // -----------------------------------------------------------------------------------------------------

                        // All Discount / Extra 금액 ----------------------------------------------------------------------------
                        allDiscountExtraPrice = "0";
                        // -----------------------------------------------------------------------------------------------------

                        double temp_totalDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(totalDiscountExtraPrice);
                        temp_totalDiscountExtraPrice = temp_totalDiscountExtraPrice * -1;

                        double temp_totalDiscountPrice = GlobalMemberValues.getDoubleAtString(totalDiscountPrice);
                        temp_totalDiscountPrice = temp_totalDiscountPrice * -1;

                        double temp_totalExtraPrice = GlobalMemberValues.getDoubleAtString(totalExtraPrice);
                        temp_totalExtraPrice = temp_totalExtraPrice * -1;

                        double temp_allDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);
                        temp_allDiscountExtraPrice = temp_allDiscountExtraPrice * -1;

                        totalDiscountExtraPrice = temp_totalDiscountExtraPrice + "";
                        totalDiscountPrice = temp_totalDiscountPrice + "";
                        totalExtraPrice = temp_totalExtraPrice + "";
                        allDiscountExtraPrice = temp_allDiscountExtraPrice + "";

                        GlobalMemberValues.logWrite("totaldclog", "totaldc4 : " + totalDiscountPrice + "\n");
                    }
                    /*******************************************************************************************************/

                    GlobalMemberValues.logWrite("cloudSalonSalesQuery", "query : " + strSaleSales + "\n");
                    Cursor salonSalesCursor;
                    salonSalesCursor = dbInitForUploadCloud.dbExecuteRead(strSaleSales);
                    if (salonSalesCursor.getCount() > 0 && salonSalesCursor.moveToFirst()) {
                        if (GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(0), 1) == "0"
                                || GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(0), 1).equals("0")) {
                            tempApi_TEN_TY1 = "1";
                        } else {
                            tempApi_TEN_TY1 = "0";
                        }
                        if (GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(1), 1) == "0"
                                || GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(1), 1).equals("0")) {
                            tempApi_TEN_TY3 = "1";
                        } else {
                            tempApi_TEN_TY3 = "0";
                        }
                        if (GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(2), 1) == "0"
                                || GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(2), 1).equals("0")) {
                            tempApi_TEN_TY5 = "1";
                        } else {
                            tempApi_TEN_TY5 = "0";
                        }
                        if (GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(3), 1) == "0"
                                || GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(3), 1).equals("0")) {
                            tempApi_TEN_TY2 = "1";
                        } else {
                            tempApi_TEN_TY2 = "0";
                        }
                        if (GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(4), 1) == "0"
                                || GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(4), 1).equals("0")) {
                            tempApi_TEN_TY4 = "1";
                        } else {
                            tempApi_TEN_TY4 = "0";
                        }

                        useTotalCashRatio = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(5), 1);
                        useTotalCardRatio = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(6), 1);
                        useTotalGiftCardRatio = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(7), 1);
                        useTotalCheckRatio = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(8), 1);
                        useTotalPointRatio = GlobalMemberValues.getDBTextAfterChecked(salonSalesCursor.getString(9), 1);

                        // 주문일자 구하기 ----------------------------------------------------------------------------
                        // String strDateQuery = "select saleDate, strftime('%m-%Y', saleDate) from salon_sales
                        // tempSalesDetailIdxwhere salesCode = '" + tempSalesCode + "' ";
                        GlobalMemberValues.logWrite("saledatelogidx", "tempSalesDetailIdx : " + tempSalesDetailIdx + "\n");
                        GlobalMemberValues.logWrite("saledatelogidx", "tempSchSalesidx : " + tempSchSalesidx + "\n");
                        String strDateQuery = "select saleDate, strftime('%m-%Y', saleDate) from salon_sales_detail where idx = '" + tempSalesDetailIdx + "' ";
                        Cursor salonSalesDateCursor = dbInitForUploadCloud.dbExecuteRead(strDateQuery);
                        if (salonSalesDateCursor.getCount() > 0 && salonSalesDateCursor.moveToFirst()) {
                            tempApi_SER_YMD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDateCursor.getString(0), 1);
                            tempApi_SER_MON = GlobalMemberValues.getDBTextAfterChecked(salonSalesDateCursor.getString(1), 1);
                            tempApi_SER_MON = GlobalMemberValues.getReplaceText(tempApi_SER_MON, "-", "");
                            GlobalMemberValues.logWrite("tempApi_SER_YMD", "tempApi_SER_YMD : " + tempApi_SER_YMD + "\n");
                            GlobalMemberValues.logWrite("tempApi_SER_MON", "tempApi_SER_MON : " + tempApi_SER_MON + "\n");
                        }

                        // ---------------------------------------------------------------------------------------------
                    }
                    // ---------------------------------------------------------------------------------------------------

                    String strSaleSalesDetail = "select idx, saleDate, strftime('%m-%Y', saleDate), " +
                            " employeeIdx, employeeName, " +
                            " customerId, customerName, customerPhone, " +
                            " itemIdx, itemName, " +
                            " salesPriceAmount, salesBalPriceAmount, taxAmount, qty, " +
                            " commissionAmount, point, " +
                            " giftcardSavePriceToSave, categoryName, saveType, couponNumber, reservationCode, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, discountbuttonname, " +
                            " tablename, tableidx, dcextraforreturn, " +
                            " serverIdx, serverName, togodelitype, totalamount  " +
                            " from salon_sales_detail " +
                            " where idx = '" + tempSchSalesidx + "' " +
                            " order by idx asc ";
                    GlobalMemberValues.logWrite("strSaleSalesDetail123", "strSaleSalesDetail : " + strSaleSalesDetail + "\n");
                    GlobalMemberValues.logWrite("strSaleSalesDetail123", "selectIdx : " + tempSchSalesidx + "\n");
                    GlobalMemberValues.logWrite("strSaleSalesDetail123", "updateIdx : " + tempSalesDetailIdx + "\n");
                    Cursor salonSalesDetailCursor;
                    String salonSalesDetailUpdQuery = "";
                    salonSalesDetailCursor = dbInitForUploadCloud.dbExecuteRead(strSaleSalesDetail);
                    if (salonSalesDetailCursor.getCount() > 0 && salonSalesDetailCursor.moveToFirst()) {
                        String tempApi_EMP_COD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(3), 1);
                        String tempApi_EMP_NAM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(4), 1);

                        String tempApi_CUS_COD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(5), 1);
                        String tempApi_CUS_NAM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(6), 1);
                        String tempApi_PHN_NUM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(7), 1);
                        String tempApi_CEL_NUM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(7), 1);
                        String tempApi_EMA_COD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(5), 1);

                        String tempApi_SER_IDN = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(8), 1);
                        String tempApi_SER_NAM = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(9), 1);

                        String tempApi_SAL_AMT_imsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(10), 1);
                        String tempApi_BAL_AMT_imsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(11), 1);
                        String tempApi_TAX_AMT_imsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(12), 1);
                        String tempApi_QTY_CNT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1);
                        String tempApi_QTY_CNT2 = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1);

                        GlobalMemberValues.logWrite("uploaddatastring", "DB 에서 가져온 세금총액 : " + tempApi_TAX_AMT_imsi + "\n");

                        String tempApi_SAL_AMT = "0.0";
                        String tempApi_BAL_AMT = "0.0";
                        String tempApi_TAX_AMT = "0.0";
                        String tempApi_TAX_TOT = "0.0";

                        // 11142022
                        String tempApi_TOT_TOT = "0.0";

                        double tempApi_QTY_CNT_dbl = GlobalMemberValues.getDoubleAtString(tempApi_QTY_CNT);
                        if (tempApi_QTY_CNT_dbl > 0) {
                            double tempApi_SAL_AMT_imsi_dbl = GlobalMemberValues.getDoubleAtString(getNumericChangeValue(tempApi_SAL_AMT_imsi));
                            double tempApi_BAL_AMT_imsi_dbl = GlobalMemberValues.getDoubleAtString(getNumericChangeValue(tempApi_BAL_AMT_imsi));
                            double tempApi_TAX_AMT_imsi_dbl = GlobalMemberValues.getDoubleAtString(getNumericChangeValue(tempApi_TAX_AMT_imsi));

                            GlobalMemberValues.logWrite("uploaddatastring", "dbl 로 변환한 세금총액 : " + tempApi_TAX_AMT_imsi_dbl + "\n");

                            if (tempApi_SAL_AMT_imsi_dbl != 0) {
                                double tempApi_SAL_AMT_dbl = tempApi_SAL_AMT_imsi_dbl / tempApi_QTY_CNT_dbl;
                                tempApi_SAL_AMT = GlobalMemberValues.getStringFormatNumber(tempApi_SAL_AMT_dbl, "2");
                            }
                            if (tempApi_BAL_AMT_imsi_dbl != 0) {
                                double tempApi_BAL_AMT_dbl = tempApi_BAL_AMT_imsi_dbl / tempApi_QTY_CNT_dbl;
                                tempApi_BAL_AMT = GlobalMemberValues.getStringFormatNumber(tempApi_BAL_AMT_dbl, "2");
                            }
                            if (tempApi_TAX_AMT_imsi_dbl != 0) {
                                double tempApi_TAX_AMT_dbl = tempApi_TAX_AMT_imsi_dbl / tempApi_QTY_CNT_dbl;
                                tempApi_TAX_AMT = GlobalMemberValues.getStringFormatNumber(tempApi_TAX_AMT_dbl, "2");
                            }

                            tempApi_TAX_TOT = GlobalMemberValues.getStringFormatNumber(tempApi_TAX_AMT_imsi_dbl, "2");

                            GlobalMemberValues.logWrite("uploaddatastring", "dbl 로 변환한 세금총액을 수량으로 나눈 것 : " + tempApi_TAX_AMT + "\n");
                        }



                        // Cash 사용금액
                        double tempApi_TEN_RT1 = 0.0;
                        if (tempApi_TEN_TY1 == "1") {
                            tempApi_TEN_RT1 = ((GlobalMemberValues.getDoubleAtString(tempApi_BAL_AMT) * GlobalMemberValues.getIntAtString(tempApi_QTY_CNT))
                                    + GlobalMemberValues.getDoubleAtString(tempApi_TAX_TOT))
                                    * GlobalMemberValues.getDoubleAtString(useTotalCashRatio);
                        }
                        // Check 사용금액
                        double tempApi_TEN_RT2 = 0.0;
                        if (tempApi_TEN_TY2 == "1") {
                            tempApi_TEN_RT2 = ((GlobalMemberValues.getDoubleAtString(tempApi_BAL_AMT) * GlobalMemberValues.getIntAtString(tempApi_QTY_CNT))
                                    + GlobalMemberValues.getDoubleAtString(tempApi_TAX_TOT))
                                    * GlobalMemberValues.getDoubleAtString(useTotalCheckRatio);
                        }
                        // Card 사용금액
                        double tempApi_TEN_RT3 = 0.0;
                        if (tempApi_TEN_TY3 == "1") {
                            tempApi_TEN_RT3 = ((GlobalMemberValues.getDoubleAtString(tempApi_BAL_AMT) * GlobalMemberValues.getIntAtString(tempApi_QTY_CNT))
                                    + GlobalMemberValues.getDoubleAtString(tempApi_TAX_TOT))
                                    * GlobalMemberValues.getDoubleAtString(useTotalCardRatio);
                        }
                        // Point 사용금액
                        double tempApi_TEN_RT4 = 0.0;
                        if (tempApi_TEN_TY4 == "1") {
                            tempApi_TEN_RT4 = ((GlobalMemberValues.getDoubleAtString(tempApi_BAL_AMT) * GlobalMemberValues.getIntAtString(tempApi_QTY_CNT))
                                    + GlobalMemberValues.getDoubleAtString(tempApi_TAX_TOT))
                                    * GlobalMemberValues.getDoubleAtString(useTotalPointRatio);
                        }
                        // GiftCard 사용금액
                        double tempApi_TEN_RT5 = 0.0;
                        if (tempApi_TEN_TY5 == "1") {
                            tempApi_TEN_RT5 = ((GlobalMemberValues.getDoubleAtString(tempApi_BAL_AMT) * GlobalMemberValues.getIntAtString(tempApi_QTY_CNT))
                                    + GlobalMemberValues.getDoubleAtString(tempApi_TAX_TOT))
                                    * GlobalMemberValues.getDoubleAtString(useTotalGiftCardRatio);
                        }

                        String tempApi_COM_AMT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(14), 1);
                        String tempApi_PNT_AMT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(15), 1);

                        // 구매한 기프트카드 금액
                        String tempApi_GIF_AMT = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(16), 1);

                        /** TIP_AMT 구하기 ********************************************************************************************************/
                        /**
                         // 해당직원의 주문건수 구하기
                         String tempEmpSalesCntStr = dbInitForUploadCloud.dbExecuteReadReturnString(
                         "select count(idx) from salon_sales_detail where salesCode = '" + tempSchSalesCode + "' " +
                         " and (employeeIdx = '" + tempApi_EMP_COD + "' or employeeName = '" + tempApi_EMP_NAM + "' )");
                         double tempEmpSalesCnt = GlobalMemberValues.getDoubleAtString(tempEmpSalesCntStr);
                         // 해당직원의 팁 금액 구하기
                         String tempEmpTipAmtStr = dbInitForUploadCloud.dbExecuteReadReturnString(
                         "select (usedCash + usedCard) from salon_sales_tip where salesCode = '" + tempSchSalesCode + "' " +
                         " and (employeeIdx = '" + tempApi_EMP_COD + "' or employeeName = '" + tempApi_EMP_NAM + "' )");
                         double tempEmpTipAmt = GlobalMemberValues.getDoubleAtString(tempEmpTipAmtStr);

                         String tempApi_TIP_AMT = "0.0";
                         if (tempEmpTipAmt != 0) {
                         double tempApiTip = tempEmpTipAmt / tempEmpSalesCnt;
                         tempApi_TIP_AMT = String.valueOf(tempApiTip);
                         }
                         **/
                        String tempApi_TIP_AMT = "0.0";
                        /**************************************************************************************************************************/

                        String tempApi_IDN_COD = tempApi_IDG_COD + "|" + GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(0), 1);

                        String tempApi_CAT_COD = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(17), 1);

                        String tempApi_CUS_MIL = "0";
                        double tempApi_CUS_MIL_DBL = 0;

                        if (GlobalMemberValues.isStrEmpty(tempApi_EMP_COD)) {
                            tempApi_EMP_COD = "E0001";
                        }

                        if (GlobalMemberValues.isStrEmpty(tempApi_CUS_COD)) {
                            tempApi_CUS_COD = "C0001";
                        } else {
                            String tempApi_CUS_MIL_s = dbInitForUploadCloud.dbExecuteReadReturnString(
                                    "select mileage from salon_member where uid = '" + tempApi_CUS_COD + "' "
                            );
                            tempApi_CUS_MIL_DBL = GlobalMemberValues.getDoubleAtString(tempApi_CUS_MIL_s);
                            tempApi_CUS_MIL = "" + tempApi_CUS_MIL_DBL;
                        }

                        if (GlobalMemberValues.isStrEmpty(tempApi_CUS_COD)) {
                            tempApi_CUS_COD = "C0001";
                        }

                        if (GlobalMemberValues.isStrEmpty(tempApi_CUS_NAM)) {
                            tempApi_CUS_NAM = "Walk in";
                        }

                        String tempSaveType = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(18), 1);
                        String tempHeaderType = "";
                        switch (tempSaveType) {
                            case "0" : {
                                tempHeaderType = "S";
                                break;
                            }
                            case "1" : {
                                tempHeaderType = "P";
                                break;
                            }
                            case "2" : {
                                tempHeaderType = "G";
                                break;
                            }
                        }

                        String tempCouponNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(19), 1);

                        String tempReservationCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(20), 1);

                        String tempOptionTxt = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(21), 1);
                        String tempOptionPrice = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(22), 1);
                        String tempAdditionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(23), 1);
                        String tempAdditionalPrice1 = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(24), 1);
                        String tempAdditionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(25), 1);
                        String tempAdditionalPrice2 = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(26), 1);
                        String tempDiscountButtonName = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(27), 1);

                        String tempTablename = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(28), 1);
                        String tempTableidx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(29), 1);

                        String tempDcExtraForReturn = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(30), 1);

                        String tempServerIdx = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(31), 1);
                        String tempServerName = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(32), 1);

                        String tempTogodelitype = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(33), 1);

                        // 11142022
                        String tempApi_TOTAL_AMT_imsi = GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(34), 1);
                        tempApi_TOT_TOT = GlobalMemberValues.getStringFormatNumber(getNumericChangeValue(tempApi_TOTAL_AMT_imsi), "2");
//                        tempApi_TOT_TOT = tempApi_TOTAL_AMT_imsi;

                        GlobalMemberValues.logWrite("jjjvaluejjjlog", "tot of db : " + tempApi_TOTAL_AMT_imsi + "\n");
                        GlobalMemberValues.logWrite("jjjvaluejjjlog", "tot of format : " + tempApi_TOT_TOT + "\n");


                        if (GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                            tempOptionTxt = "";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempOptionPrice)) {
                            tempOptionPrice = "0";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                            tempAdditionalTxt1 = "";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempAdditionalPrice1)) {
                            tempAdditionalPrice1 = "0";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                            tempAdditionalTxt2 = "";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempAdditionalPrice2)) {
                            tempAdditionalPrice2 = "0";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempDiscountButtonName)) {
                            tempDiscountButtonName = "";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempTablename)) {
                            tempTablename = "";
                        }
                        if (GlobalMemberValues.isStrEmpty(tempTableidx)) {
                            tempTableidx = "0";
                        } else {
                            String quicksaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                    " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableidx + "' ");
                            if (GlobalMemberValues.isStrEmpty(quicksaleyn)) {
                                tempTableidx = "0";
                            } else {
                                if (quicksaleyn.equals("N")) {
                                    quicksaleyn = "0";
                                }
                            }
                        }
                        if (GlobalMemberValues.isStrEmpty(tempTogodelitype)) {
                            tempTogodelitype = "H";
                        }


                        tempApi_SER_IDN = tempHeaderType + tempApi_SER_IDN;

                        // 수령방법 --------------------------------------------------------------------------------------------
                        String tempDeliveryTakeway = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select deliverytakeaway from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempDeliveryTakeway)) {
                            tempDeliveryTakeway = "T";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // Delivery / Pickup Fee -------------------------------------------------------------------------------
                        String tempDeliveryPickupFee = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select deliverypickupfee from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempDeliveryPickupFee)) {
                            tempDeliveryPickupFee = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // Check Company (외부배달회사) --------------------------------------------------------------------------
                        String tempCheckCompany = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select checkcompany from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempCheckCompany)) {
                            tempCheckCompany = "";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // Phone Order 여부 -------------------------------------------------------------------------------------
                        String tempPhoneOrder = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select phoneorder from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempPhoneOrder)) {
                            tempPhoneOrder = "N";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // Customer Order --------------------------------------------------------------------------------------
                        String tempCustomerOrderNumber = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select customerordernumber from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempCustomerOrderNumber)) {
                            tempCustomerOrderNumber = "";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // Customer Order --------------------------------------------------------------------------------------
                        String tempCustomerPagerNumber = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select customerpagernumber from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempCustomerPagerNumber)) {
                            tempCustomerPagerNumber = "";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 버전이름 가져오기 -----------------------------------------------------------------------------------
                        PackageInfo pi = null;
                        try {
                            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                        }
                        String posVersionName = pi.versionName;
                        int posVersionCode = pi.versionCode;
                        // -----------------------------------------------------------------------------------------------------

                        // POS 기기 이름 가져오기 ------------------------------------------------------------------------------
                        String thisPosDeviceName = GlobalMemberValues.getThisPOSDevice();
                        if (GlobalMemberValues.isStrEmpty(thisPosDeviceName)) {
                            thisPosDeviceName = "Tablet PC";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 리턴된 Delivery / Pickup Fee -------------------------------------------------------------------------------
                        String tempReturnedDeliveryPickupFee = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select returneddeliverypickupfee from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempReturnedDeliveryPickupFee)) {
                            tempReturnedDeliveryPickupFee = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 리턴된 TIP --------------------------------------------------------------------------------------------
                        String tempReturnedtip = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select returnedtip from salon_sales where salesCode = '" + tempSalesCodeK + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempReturnedtip)) {
                            tempReturnedtip = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 테이블에서 식사한 사람 수 ------------------------------------------------------------------------------
                        String tempTablePeopleCnt = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select tablepeoplecnt from salon_sales where substr(salesCode, 2) = '" + tempSalesCode.substring(1) + "' " );
                        if (GlobalMemberValues.isStrEmpty(tempTablePeopleCnt)) {
                            tempTablePeopleCnt = "0";
                        }
                        // -----------------------------------------------------------------------------------------------------

                        // 카드결제관련 정보 ---------------------------------------------------------------------------------------
                        // Auth #
                        String authnumber = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select tid from salon_sales_card where salesCode = '" + tempSalesCode + "' and delyn = 'N' " );
                        if (GlobalMemberValues.isStrEmpty(authnumber)) {
                            authnumber = "";
                        }
                        // last 4 digit
                        String lastfourdigits = dbInitForUploadCloud.dbExecuteReadReturnString(
                                "select cardLastFourDigitNumbers from salon_sales_card where salesCode = '" + tempSalesCode + "' and delyn = 'N' " );
                        if (GlobalMemberValues.isStrEmpty(lastfourdigits)) {
                            lastfourdigits = "";
                        }
                        // -----------------------------------------------------------------------------------------------------


                        // 11082023 -------------------------------------------------------------------------------------------
                        // item delete reason 정보
                        String deleteReasonStr = "";
                        ResultSet rset;
                        String idrSql = " select serveridx, servername, reason from salon_itemdeletereason " +
                                " where holdcode = '" + tempApi_HoldCode + "' ";
                        rset = MssqlDatabase.getResultSetValue(idrSql);
                        try {
                            int idr_cnt = 0;
                            while (rset.next()) {
                                String idr_serveridx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(rset, 0), 0);
                                String idr_servername = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(rset, 1), 0);
                                String idr_reason = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(rset, 2), 0);

                                if (idr_cnt > 0) {
                                    deleteReasonStr += "-J3-";
                                }
                                deleteReasonStr += idr_serveridx + "-J-" + GlobalMemberValues.getEncoderUtf8(idr_servername) + "-J-" + GlobalMemberValues.getEncoderUtf8(idr_reason);

                                idr_cnt++;
                            }
                            rset.close();
                        } catch (Exception e) {
                        }
                        // 11082023 -------------------------------------------------------------------------------------------


                        String apiParametersStr = "SCODE=" + tempApi_SCODE +
                                "&SIDX=" + tempApi_SIDX +
                                "&IDG_COD=" + tempApi_IDG_COD +
                                "&IDN_COD=" + tempApi_IDN_COD +
                                "&SER_YMD=" + tempApi_SER_YMD +
                                "&SER_MON=" + tempApi_SER_MON +
                                "&EMP_COD=" + tempApi_EMP_COD +
                                "&EMP_NAM=" + GlobalMemberValues.getEncoderUtf8(tempApi_EMP_NAM) +
                                "&CUS_COD=" + tempApi_CUS_COD +
                                "&CUS_NAM=" + GlobalMemberValues.getEncoderUtf8(tempApi_CUS_NAM) +
                                "&PHN_NUM=" + tempApi_PHN_NUM +
                                "&CEL_NUM=" + tempApi_CEL_NUM +
                                "&EMA_COD=" + tempApi_EMA_COD +
                                "&SER_IDN=" + tempApi_SER_IDN +
                                "&SER_NAM=" + GlobalMemberValues.getEncoderUtf8(tempApi_SER_NAM) +
                                "&SAL_AMT=" + getNumericChangeValue(mathFlag + tempApi_SAL_AMT) +
                                "&BAL_AMT=" + getNumericChangeValue(mathFlag + tempApi_BAL_AMT) +
                                "&TAX_AMT=" + getNumericChangeValue(mathFlag + tempApi_TAX_AMT) +
                                "&TAX_TOT=" + getNumericChangeValue(mathFlag + tempApi_TAX_TOT) +
                                "&QTY_CNT=" + tempApi_QTY_CNT +
                                "&QTY_CNT2=" + tempApi_QTY_CNT2 +
                                "&TEN_TY1=" + tempApi_TEN_TY1 +
                                "&TEN_TY2=" + tempApi_TEN_TY2 +
                                "&TEN_TY3=" + tempApi_TEN_TY3 +
                                "&TEN_TY4=" + tempApi_TEN_TY4 +
                                "&TEN_TY5=" + tempApi_TEN_TY5 +
                                "&TEN_RT1=" + getNumericChangeValue(mathFlag + tempApi_TEN_RT1) +
                                "&TEN_RT2=" + getNumericChangeValue(mathFlag + tempApi_TEN_RT2) +
                                "&TEN_RT3=" + getNumericChangeValue(mathFlag + tempApi_TEN_RT3) +
                                "&TEN_RT4=" + getNumericChangeValue(mathFlag + tempApi_TEN_RT4) +
                                "&TEN_RT5=" + getNumericChangeValue(mathFlag + tempApi_TEN_RT5) +
                                "&PNT_AMT=" + getNumericChangeValue(mathFlag + tempApi_PNT_AMT) +
                                "&GIF_AMT=" + getNumericChangeValue(mathFlag + tempApi_GIF_AMT) +
                                "&COM_AMT=" + getNumericChangeValue(mathFlag + tempApi_COM_AMT) +
                                "&TIP_AMT=" + getNumericChangeValue(mathFlag + tempApi_TIP_AMT) +
                                "&CUS_CNT=" + getNumericChangeValue(mathFlag + tempApi_CUS_CNT) +
                                "&CAT_COD=" + GlobalMemberValues.getEncoderUtf8(tempApi_CAT_COD) +
                                "&STATION_COD=" + GlobalMemberValues.STATION_CODE +
                                "&COUPONNUM=" + tempCouponNumber +
                                "&RCODE=" + tempReservationCode +
                                "&CASH_PAY=" + totalCashPayUsed +
                                "&CARD_PAY=" + totalCardPayUsed +
                                "&GIFTCARD_PAY=" + totalGiftCardPayUsed +
                                "&CHECK_PAY=" + totalCheckPayUsed +
                                "&POINT_PAY=" + totalPointPayUsed +
                                "&TOTAL_DCEXTRA=" + totalDiscountExtraPrice +
                                "&RETURNCODE=" + tempReturnCode +
                                "&CARD_PAY_VISA=" + totalCardPayUsedByVisa +
                                "&CARD_PAY_MASTER=" + totalCardPayUsedByMaster +
                                "&CARD_PAY_AMEX=" + totalCardPayUsedByAmex +
                                "&CARD_PAY_DISCOVER=" + totalCardPayUsedByDiscover +
                                "&CARD_PAY_OFFLINE=" + totalCardPayUsedByOffLine +
                                "&CHANGEMONEY=" + totalChangeMoney +
                                "&TOTALDC=" + totalDiscountPrice +
                                "&TOTALEXTRA=" + totalExtraPrice +
                                "&ALLDCEXTRA=" + allDiscountExtraPrice +
                                "&CUS_MIL=" + tempApi_CUS_MIL +
                                "&OPTIONTXT=" + tempOptionTxt +
                                "&OPTIONPRICE=" + tempOptionPrice +
                                "&ADDITIONALTXT1=" + tempAdditionalTxt1 +
                                "&ADDITIONALPRICE1=" + tempAdditionalPrice1 +
                                "&ADDITIONALTXT2=" + tempAdditionalTxt2 +
                                "&ADDITIONALPRICE2=" + tempAdditionalPrice2 +
                                "&DELIVERYTAKEAWAY=" + tempDeliveryTakeway +
                                "&DELIVERYPICKUPFEE=" + tempDeliveryPickupFee +
                                "&CHECKCOM=" + tempCheckCompany +
                                "&PHONEORDER=" + tempPhoneOrder +
                                "&CUSTOMERORDERNUM=" + tempCustomerOrderNumber +
                                "&CUSTOMERPAGERNUM=" + tempCustomerPagerNumber +
                                "&POSVERSION=" + posVersionName +
                                "&POSTYPE=" + GlobalMemberValues.DEVICEPRODUCTTYPE +
                                "&POSDEVICE=" + thisPosDeviceName +
                                "&DISCOUNTBUTTONNAME=" + tempDiscountButtonName +
                                "&CUSTOMERPHONENUMBER=" + tempApi_customerphonenumber +
                                "&RETURNEDDELIVERYPICKUPFEE=" + tempReturnedDeliveryPickupFee +
                                "&RETURNEDTIP=" + tempReturnedtip +
                                "&TABLENAME=" + tempTablename +
                                "&TABLEIDX=" + tempTableidx +
                                "&TABLEPEOPLECNT=" + tempTablePeopleCnt +
                                "&HOLDCODE=" + GlobalMemberValues.getEncoderUtf8(tempApi_HoldCode) +
                                "&DCEXTRAFORRETURN=" + GlobalMemberValues.getEncoderUtf8(tempDcExtraForReturn) +
                                "&CARDAUTHNUMBER=" + authnumber +
                                "&CARDLASTFOURDIGITS=" + lastfourdigits +
                                "&CANCELREASON=" + tempApi_cancelreason +
                                "&SERVERIDX=" + tempServerIdx +
                                "&SERVERNAME=" + GlobalMemberValues.getEncoderUtf8(tempServerName) +
                                "&TOGODELITYPE=" + GlobalMemberValues.getEncoderUtf8(tempTogodelitype) +
                                "&TOT_TOT=" + getNumericChangeValue(mathFlag + tempApi_TOT_TOT) +
                                // 11082023
                                "&DELETEREASON=" + deleteReasonStr;

                        salonSalesDetailUpdQuery = "update salon_sales_detail set isCloudUpload = 1 " +
                                " where idx = '" + tempSalesDetailIdx + "' ";
                        apiVec.addElement(apiParametersStr + "|||" + salonSalesDetailUpdQuery);
                        GlobalMemberValues.logWrite("uploaddatastring", "data string : " + apiParametersStr + "\n");
                    }

                    if (apiVec.size() > 0 && !GlobalMemberValues.isSaleDataUploadPause()) {
                        Vector<String > apiUpdateQueryVec = new Vector<String>();
                        for (String params : apiVec) {
                            GlobalMemberValues.logWrite("cloudSendSaleDataParameter", "params : " + params + "\n");
                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                String[] tempParams = params.split(GlobalMemberValues.STRSPLITTER2);
                                // API 로 넘기는 문자열에는 공백이 있으면 안되므로
                                // 공백부분을 ||| 으로 처리하고
                                // 웹 API 처리하는 부분에서 ||| 을 공백으로 치환한다.
                                String paramStr = GlobalMemberValues.getReplaceText(tempParams[0], " ", "|||");
                                String returnValue = sendSalesDataByApi(paramStr);
                                GlobalMemberValues.logWrite("cloudSendSaleDataReturnCodeValue", "returnValue : " + returnValue + "\n");
                                if (!GlobalMemberValues.isStrEmpty(returnValue)) {
                                    apiUpdateQueryVec.addElement(tempParams[1]);
                                }
                            }
                        }

                        for (String strQuery : apiUpdateQueryVec) {
                            GlobalMemberValues.logWrite("cloudSendSaleDataUpdateQuery", "query : " + strQuery + "\n");
                        }
                        String returnResult = "";
                        // 트랜잭션으로 DB 처리한다.
                        returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                        } else { // 정상처리일 경우 Sale History 리스트 리로드
                            // 정상처리일 경우 현재 구동된 서비스를 중지한다.
                            // 서비스 중지 전 3초간 기다린다.
                            try {
                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITTOSTOPSERVICE);        // 3초
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALE != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALE);
    }


    public String sendSalesDataByApi(String strParams) {
        GlobalMemberValues.logWrite("paramsInSendSalesDataByApi", "strParams : " + strParams + "\n");
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(strParams)) {
                API_salesdataupload_tocloud apiSalesDataInstance = new API_salesdataupload_tocloud(strParams);
                apiSalesDataInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiSalesDataInstance.mFlag) {
                        returnValue = apiSalesDataInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        return returnValue;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
