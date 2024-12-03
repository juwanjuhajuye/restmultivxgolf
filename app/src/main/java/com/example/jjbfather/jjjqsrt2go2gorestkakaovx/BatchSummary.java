package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class BatchSummary extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private LinearLayout parentLn;

    private LinearLayout batchSummaryClosingReportLinearLayout;
    private LinearLayout batchSummaryEmployeeListLinearLayout;

    private LinearLayout endofdayreportln;

    Intent mIntent;

    private Button closeBtn;

    private Button batchSummaryDateSearchButton;
    private static Button batchSummaryBatchButton;
    private Button batchSummaryPrintButton, batchedListBtn;
    private static Button manualBatchBtn;

    private TextView batchSummaryDateSearchTextView;

    private TextView batchSummarySaleDateTextView;
    private TextView batchSummaryServiceTextView, batchSummaryProductTextView, batchSummaryGiftCardTextView;
    private TextView batchSummaryDiscountTextView, batchSummaryVoidTextView;
    private TextView batchSummaryPickupFeeTextView, batchSummaryDeliveryFeeTextView;

    private TextView batchSummaryNetSalesTextView,batchSummaryRefundTextView;
    private TextView batchSummaryTaxTextView,batchSummaryGrossSalesTextView;

    private TextView batchSummaryCashAmountTextView;

    private TextView batchSummaryCreditCardAmountTextView, batchSummaryCreditCardTipAmountTextView;
    private TextView batchSummaryCreditCardVisaCountTextView, batchSummaryCreditCardVisaAmountTextView;
    private TextView batchSummaryCreditCardMasterCountTextView, batchSummaryCreditCardMasterAmountTextView;
    private TextView batchSummaryCreditCardAmexCountTextView, batchSummaryCreditCardAmexAmountTextView;
    private TextView batchSummaryCreditCardDiscoverCountTextView, batchSummaryCreditCardDiscoverAmountTextView;
    private TextView batchSummaryCreditCardOffCardCountTextView, batchSummaryCreditCardOffCardAmountTextView;

    private TextView batchSummaryGiftCardAmountTextView;
    private TextView batchSummaryPointAmountTextView;
    private TextView batchSummaryOtherAmountTextView;

    private TextView batchSummaryPrintedDateTextView;
    private static TextView batchSummaryNoBatchListTextView;

    private TextView batchSummaryTitleTextView;
    private TextView batchSummaryDateTitleTextView;

    private TextView reportTitleTv;

    TextView mSelectedDateTextView;

    JSONObject jsonroot = null;

    static boolean mIsFromCashInOut = false;

    static String paramAutoCloseYN = "N";

    static String mTipIdx = "";
    static String mTipAdjusctResulf = "";

    static Context tempContext = null;
    static Activity tempActivity = null;
    static boolean tempIsFromCashInOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_batch_summary);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.batchSummaryLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            paramAutoCloseYN = mIntent.getStringExtra("autocloseyn");
            GlobalMemberValues.logWrite("batchsummaryjjj", "넘겨받은 paramAutoCloseYN : " + paramAutoCloseYN + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finishActivity();
        }

        setContents();
    }

    public void setContents() {
        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE  = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        mIsFromCashInOut = false;

        mActivity = this;
        context = this;
        dbInit = MainActivity.mDbInit;

        batchSummaryTitleTextView = (TextView)findViewById(R.id.batchSummaryTitleTextView);
        batchSummaryTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + batchSummaryTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchSummaryDateTitleTextView = (TextView)findViewById(R.id.batchSummaryDateTitleTextView);
        batchSummaryDateTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + batchSummaryDateTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("batchSummaryCloseBtnTag");
        closeBtn.setOnClickListener(batchSummaryBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        batchSummaryDateSearchButton = (Button)parentLn
                .findViewWithTag("batchSummaryDateSearchButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchSummaryDateSearchButton.setText("");
            batchSummaryDateSearchButton.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_search);
        } else {
            batchSummaryDateSearchButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    batchSummaryDateSearchButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        batchSummaryBatchButton = (Button)parentLn
                .findViewWithTag("batchSummaryBatchButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchSummaryBatchButton.setText("");
            batchSummaryBatchButton.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_batch);
        } else {
            batchSummaryBatchButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    batchSummaryBatchButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        batchSummaryPrintButton = (Button)parentLn
                .findViewWithTag("batchSummaryPrintButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchSummaryPrintButton.setText("");
            batchSummaryPrintButton.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_print);
        } else {
            batchSummaryPrintButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    batchSummaryPrintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        batchedListBtn = (Button)parentLn
                .findViewWithTag("batchedListBtnTag");
        batchedListBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                batchedListBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        // LITE 버전 관련
//        if (GlobalMemberValues.isLiteVersion()) {
//            LinearLayout batchsummaryLn2 = (LinearLayout)findViewById(R.id.batchsummaryLn);
//
//            batchsummaryLn2.setVisibility(View.GONE);
//        }

        manualBatchBtn = (Button)parentLn.findViewWithTag("manualBatchBtnTag");
        manualBatchBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + manualBatchBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchSummaryDateSearchButton.setOnClickListener(batchSummaryBtnClickListener);
        batchSummaryBatchButton.setOnClickListener(batchSummaryBtnClickListener);
        batchSummaryPrintButton.setOnClickListener(batchSummaryBtnClickListener);
        batchedListBtn.setOnClickListener(batchSummaryBtnClickListener);
        manualBatchBtn.setOnClickListener(batchSummaryBtnClickListener);
        /***********************************************************************************************************/

        /** TextView 객체 생성 및 리스너 연결 **********************************************************************/
        reportTitleTv = (TextView)findViewById(R.id.reportTitleTv);
        reportTitleTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        batchSummaryDateSearchTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryDateSearchTextViewTag");
        batchSummaryDateSearchTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        batchSummaryDateSearchTextView.setOnClickListener(batchSummaryBtnClickListener);

        batchSummarySaleDateTextView = (TextView)parentLn
                .findViewWithTag("batchSummarySaleDateTextViewTag");
        batchSummaryServiceTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryServiceTextViewTag");
        batchSummaryProductTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryProductTextViewTag");
        batchSummaryGiftCardTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryGiftCardTextViewTag");
        batchSummaryDiscountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryDiscountTextViewTag");
        batchSummaryPickupFeeTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryPickupFeeTextViewTag");
        batchSummaryDeliveryFeeTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryDeliveryFeeTextViewTag");
        batchSummaryVoidTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryVoidTextViewTag");

        batchSummaryNetSalesTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryNetSalesTextViewTag");
        batchSummaryRefundTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryRefundTextViewTag");
        batchSummaryTaxTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryTaxTextViewTag");
        batchSummaryGrossSalesTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryGrossSalesTextViewTag");

        batchSummaryCashAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCashAmountTextViewTag");

        batchSummaryCreditCardAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardAmountTextViewTag");
        batchSummaryCreditCardTipAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardTipAmountTextViewTag");
        batchSummaryCreditCardVisaCountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardVisaCountTextViewTag");
        batchSummaryCreditCardVisaAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardVisaAmountTextViewTag");
        batchSummaryCreditCardMasterCountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardMasterCountTextViewTag");
        batchSummaryCreditCardMasterAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardMasterAmountTextViewTag");
        batchSummaryCreditCardAmexCountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardAmexCountTextViewTag");
        batchSummaryCreditCardAmexAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardAmexAmountTextViewTag");
        batchSummaryCreditCardDiscoverCountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardDiscoverCountTextViewTag");
        batchSummaryCreditCardDiscoverAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardDiscoverAmountTextViewTag");
        batchSummaryCreditCardOffCardCountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardOffCardCountTextViewTag");
        batchSummaryCreditCardOffCardAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryCreditCardOffCardAmountTextViewTag");
        batchSummaryGiftCardAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryGiftCardAmountTextViewTag");
        batchSummaryPointAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryPointAmountTextViewTag");
        batchSummaryOtherAmountTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryOtherAmountTextViewTag");
        batchSummaryPrintedDateTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryPrintedDateTextViewTag");

        batchSummaryNoBatchListTextView = (TextView)parentLn
                .findViewWithTag("batchSummaryNoBatchListTextViewTag");
        batchSummaryNoBatchListTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + batchSummaryNoBatchListTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        /***********************************************************************************************************/

        // Batch 버튼 활성/비활성 관련
        setBatchButtonStatus();

        // 객체 및 색상 초기화
        setInit();

        // Closing Report 창 LinearLayout 객체 - 이전에 사용
        batchSummaryClosingReportLinearLayout = (LinearLayout)findViewById(R.id.batchSummaryClosingReportLinearLayout);

        // Closing Report 창 LinearLayout 객체 - 최근 사용
        endofdayreportln = (LinearLayout)findViewById(R.id.endofdayreportln);

        // ScrollView 에 속한 첫번째 LinearLayout 객체
        batchSummaryEmployeeListLinearLayout = (LinearLayout)findViewById(R.id.batchSummaryEmployeeListLinearLayout);

        //String strSearchDate = DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowYearGet();
        //String strSearchDate = "01-06-2016";
        String strSearchDate = GlobalMemberValues.STR_NOW_DATE;
        try {
            setSummary(strSearchDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (GlobalMemberValues.isStrEmpty(batchSummaryDateSearchTextView.getText().toString())) {
            batchSummaryDateSearchTextView.setText(strSearchDate);
        }

        /**
        // 다음버전에서는 아래부분을 지우고 배포한다... ------------------------------------------------------------------
        // 세일데이터에서 void 처리된 내역들의 employee 정보를
        // 최초 세일한 employee 로 수정한다.
        String jjjreturnResult = "";
        Vector<String> strUpdateQueryVecJ = new Vector<String>();
        Cursor tempCursorJ = null;
        String tempSqlQueryJ = "";
        String jjjtempSalesCode = "";
        String jjjtempSalesCodeK = "";

        String jTempSqlQuery = " select salesCode from salon_sales_detail " +
                " where delyn = 'N' " +
                " and (substr(salesCode, 1, 1) = 'V' or substr(salesCode, 1, 1) = 'C') ";
        Cursor jTempCursor = MainActivity.mDbInit.dbExecuteRead(jTempSqlQuery);
        while (jTempCursor.moveToNext()) {
            jjjtempSalesCode = jTempCursor.getString(0);
            if (!GlobalMemberValues.isStrEmpty(jjjtempSalesCode)) {
                jjjtempSalesCodeK = "K" + jjjtempSalesCode.substring(1);
                String strQuery = "select employeeIdx, employeeName from salon_sales_detail " +
                        " where salesCode = '" + jjjtempSalesCodeK + "' ";
                tempCursorJ = MainActivity.mDbInit.dbExecuteRead(strQuery);
                if (tempCursorJ.getCount() > 0 && tempCursorJ.moveToFirst()) {
                    tempSqlQueryJ = " update salon_sales_detail set " +
                            " employeeIdx = '" + tempCursorJ.getString(0) + "', " +
                            " employeeName = '" + tempCursorJ.getString(1) + "' " +
                            " where salesCode = '" + jjjtempSalesCode + "' ";
                    strUpdateQueryVecJ.addElement(tempSqlQueryJ);
                }
            }
        }
        for (String tempQuery : strUpdateQueryVecJ) {
            GlobalMemberValues.logWrite("jjjBatchSummaryUpdateLog", "query : " + tempQuery + "\n");
        }
        if (strUpdateQueryVecJ.size() > 0) {
            jjjreturnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVecJ);
            String returnSalesCode = "";
            if (jjjreturnResult == "N" || jjjreturnResult == "") {
                jjjreturnResult = "N";
            } else {
                jjjreturnResult = "Y";

                try {
                    setSummary(strSearchDate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (GlobalMemberValues.isStrEmpty(batchSummaryDateSearchTextView.getText().toString())) {
                    batchSummaryDateSearchTextView.setText(strSearchDate);
                }
            }
        }
        // --------------------------------------------------------------------------------------------------------
         **/

        if (paramAutoCloseYN == "Y" || paramAutoCloseYN.equals("Y")) {
            setBatchSales(context, mActivity, false);
            //finishActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.proCustomDial != null) {
            MainActivity.proCustomDial.dismiss();
        }
    }

    public void setSummaryOld(String paramSearchDate) throws JSONException {
        // 최상단 json객체
        jsonroot = new JSONObject();
        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp = null;

        String tempEmployeeSalesDataForJson = "";

        String strSearchQuery = "";

        batchSummaryEmployeeListLinearLayout.removeAllViews();
        setInit();

        // Sale Date ----------------------------------------------------------
        batchSummarySaleDateTextView.setText(paramSearchDate);
        jsonroot.put("saledate", paramSearchDate);  // JSON
        // --------------------------------------------------------------------

        if (!GlobalMemberValues.isStrEmpty(paramSearchDate)) {
            paramSearchDate = DateMethodClass.getCustomEditDate(paramSearchDate, 0, 0, 0);
        }

        // Employee Sale List ------------------------------------------------------------------------------------------------
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 0);

        Cursor empSalesCursor;
        strSearchQuery = "select A.employeeName, sum(A.qty), sum(round(A.commissionAmount, 2)), sum(round(A.totalAmount, 2)), A.employeeIdx " +
                " from salon_sales_detail A " +
                " where strftime('%Y-%m-%d', A.saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and A.delyn = 'N' " +
                " group by A.employeeIdx order by A.employeeName asc ";
        GlobalMemberValues.logWrite("batchsummary", "query : " + strSearchQuery + "\n");
        if (MainActivity.mDbInit == null) {
            MainActivity.mDbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        }
        empSalesCursor = MainActivity.mDbInit.dbExecuteRead(strSearchQuery);
        while (empSalesCursor.moveToNext()) {
            String tempEmployeeName = GlobalMemberValues.getDBTextAfterChecked(empSalesCursor.getString(0), 1);
            String tempTotalQty = GlobalMemberValues.getDBTextAfterChecked(empSalesCursor.getString(1), 1);
            double tempTotalCommissionAmountDouble = empSalesCursor.getDouble(2);
            String tempTotalCommissionAmount = GlobalMemberValues.getStringFormatNumber(tempTotalCommissionAmountDouble, "2");
            double tempTotalTotalAmountDouble = empSalesCursor.getDouble(3);
            String tempTotalTotalAmount = GlobalMemberValues.getStringFormatNumber(tempTotalTotalAmountDouble, "2");

            String tempEmployeeIdx = GlobalMemberValues.getDBTextAfterChecked(empSalesCursor.getString(4), 1);

            // 팁 구하기 ------------------------------------------------------------------------------------------
            String tipQuery = " select sum(round((B.usedCash + B.usedCard), 2))  from salon_sales_tip B where ";
            if (GlobalMemberValues.isStrEmpty(tempEmployeeIdx)) {
                tipQuery += " B.employeeName = '" + tempEmployeeName + "' ";
            } else {
                tipQuery += " B.employeeIdx = '" + tempEmployeeIdx + "' ";
            }
            tipQuery += " and B.salesCode not in (select salesCode from salon_sales where not(status = 0)) " +
                    " and B.salesCode in ( " +
                    " select A.salesCode from salon_sales_detail A " +
                    " where strftime('%Y-%m-%d', A.saleDate) between '" + paramSearchDate + "'" +
                    " and '" + paramSearchDate + "' " +
                    " and A.delyn = 'N' " +
                    " ) " +
                    " order by B.employeeName asc ";
            GlobalMemberValues.logWrite("tipAmountLog", "tipQuery : " + tipQuery + "\n");

            double tempTotalTipAmountDouble = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(tipQuery));
            String tempTotalTipAmount = GlobalMemberValues.getStringFormatNumber(tempTotalTipAmountDouble, "2");
            if (GlobalMemberValues.isStrEmpty(tempTotalTipAmount)) {
                tempTotalTipAmount = " - ";
            }
            // --------------------------------------------------------------------------------------------------

            // Qty 구하기
            strSearchQuery = "select sum(qty) " +
                    " from salon_sales_detail " +
                    " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                    " and '" + paramSearchDate + "' " +
                    " and delyn = 'N' " +
                    " and substr(salesCode, 1, 1) = 'K' ";
            int totalQty1 = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
            // Qty 구하기
            strSearchQuery = "select sum(qty) " +
                    " from salon_sales_detail " +
                    " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                    " and '" + paramSearchDate + "' " +
                    " and delyn = 'N' " +
                    " and (substr(salesCode, 1, 1) = 'V' or substr(salesCode, 1, 1) = 'C') ";
            int totalQty2 = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
            int totalQty = totalQty1 - totalQty2;
            String strTotalQty = GlobalMemberValues.getCommaStringForInteger(String.valueOf(totalQty));

            // LinearLayout 객체 생성
            LinearLayout newLn = new LinearLayout(this);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.HORIZONTAL);
            newLn.setPadding(0, 5, 0, 5);
            //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

            // Emp. Name
            TextView empSalesSummaryTv1 = new TextView(this);
            empSalesSummaryTv1.setText(tempEmployeeName);
            empSalesSummaryTv1.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
            empSalesSummaryTv1.setTextSize(12.0f);
            empSalesSummaryTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            empSalesSummaryTv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3.5f));
            newLn.addView(empSalesSummaryTv1);

            // Qty
            /**
             TextView empSalesSummaryTv2 = new TextView(this);
             empSalesSummaryTv2.setText(tempTotalQty);
             empSalesSummaryTv2.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
             empSalesSummaryTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
             empSalesSummaryTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
             empSalesSummaryTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.6f));
             newLn.addView(empSalesSummaryTv2);
             **/

            // Tip
            TextView empSalesSummaryTv3 = new TextView(this);
            empSalesSummaryTv3.setText(tempTotalTipAmount);
            empSalesSummaryTv3.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
            empSalesSummaryTv3.setTextSize(12.0f);
            empSalesSummaryTv3.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            empSalesSummaryTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            newLn.addView(empSalesSummaryTv3);

            // Comm
            /**
            TextView empSalesSummaryTv4 = new TextView(this);
            empSalesSummaryTv4.setText(tempTotalCommissionAmount);
            empSalesSummaryTv4.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
            empSalesSummaryTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
            empSalesSummaryTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            empSalesSummaryTv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.2f));
            newLn.addView(empSalesSummaryTv4);
            **/

            // Sale Amt
            TextView empSalesSummaryTv5 = new TextView(this);
            empSalesSummaryTv5.setText(tempTotalTotalAmount);
            empSalesSummaryTv5.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
            empSalesSummaryTv5.setTextSize(12.0f);
            empSalesSummaryTv5.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            empSalesSummaryTv5.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.4f));
            newLn.addView(empSalesSummaryTv5);

            batchSummaryEmployeeListLinearLayout.addView(newLn);
            // -------------------------------------------------------------------------------------------------------------------

            //tempEmployeeSalesDataForJson = empSalesSummaryTv1 + "||" + empSalesSummaryTv2 + "||" +
            //empSalesSummaryTv3 + "||" + empSalesSummaryTv4 + "||" + empSalesSummaryTv5;

            jsontmp = new JSONObject();
            jsontmp.put("empsalesempname", tempEmployeeName);
            jsontmp.put("empsalesqty", strTotalQty);
            jsontmp.put("empsalestip", tempTotalTipAmount);
            jsontmp.put("empsalescomm", tempTotalCommissionAmount);
            jsontmp.put("empsalessalesamt", tempTotalTotalAmount);

            jsonList.put(jsontmp);
        }
        empSalesCursor.close();

        // Employee 별 Sales 리스트를 Json 객체에 담는다.
        jsonroot.put("employeesaleslist", jsonList);

        // Service -----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and saveType = '0' " +
                " and delyn = 'N' ";
        String tempService = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryServiceTextView.setText(tempService);
        jsonroot.put("service", tempService);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Product -----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and saveType = '1' " +
                " and delyn = 'N' ";
        String tempProduct = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryProductTextView.setText(tempProduct);
        jsonroot.put("product", tempProduct);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Gift Card ---------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and saveType = '2' " +
                " and delyn = 'N' ";
        String tempGiftCard = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryGiftCardTextView.setText(tempGiftCard);
        jsonroot.put("giftcard", tempGiftCard);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Discount ----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(eachDiscountExtraPrice, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'K' ";
        String tempDiscount = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        if (GlobalMemberValues.getDoubleAtString(tempDiscount) > 0) {
            tempDiscount = "-" + tempDiscount;
        }
        batchSummaryDiscountTextView.setText(tempDiscount);
        jsonroot.put("discount", tempDiscount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // PICK UP FEE -------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and deliverytakeaway = 'T' ";
        String tempPickupFee = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryPickupFeeTextView.setText(tempPickupFee);
        jsonroot.put("pickupfee", tempPickupFee);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // PICK UP FEE -------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(deliverypickupfee, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 " +
                " and deliverytakeaway = 'D' ";
        String tempDeliveryfee = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryDeliveryFeeTextView.setText(tempDeliveryfee);
        jsonroot.put("deliveryfee", tempDeliveryfee);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Void --------------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'V' ";
        String tempVoid = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryVoidTextView.setText(tempVoid);
        jsonroot.put("void", tempVoid);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Net Sales -----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round((totalAmount - taxAmount), 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and not(savetype = '2') " +
                " and delyn = 'N' ";
        String tempNetSales = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryNetSalesTextView.setText(tempNetSales);
        jsonroot.put("netsales", tempNetSales);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Refund (Return) ---------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and substr(salesCode, 1, 1) = 'C' ";
        String tempRefund = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryRefundTextView.setText(tempRefund);
        jsonroot.put("refund", tempRefund);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // TAX ---------------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(taxAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' ";
        String tempTax = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryTaxTextView.setText(tempTax);
        jsonroot.put("tax", tempTax);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Gross Sales -------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(totalAmount, 2)) from salon_sales_detail " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and not(saveType = '2') " +
                " and delyn = 'N' ";
        String tempGrossSales = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryGrossSalesTextView.setText(tempGrossSales);
        jsonroot.put("grosssales", tempGrossSales);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Cash --------------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(useTotalCashAmountAfterReturned, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempCash = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCashAmountTextView.setText(tempCash);
        jsonroot.put("cash", tempCash);  // JSON
        // -------------------------------------------------------------------------------------------------------------------


        // Card --------------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCard = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardAmountTextView.setText(tempCard);
        jsonroot.put("card", tempCard);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card Tip ----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(usedCard, 2)) from salon_sales_tip " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and salesCode in (select salesCode from salon_sales_card where not(status = '1') ) " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') " +
                " and salesCode in (select salesCode from salon_sales_detail where delyn = 'N' ) ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardTip = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardTipAmountTextView.setText(tempCardTip);
        jsonroot.put("cardtip", tempCardTip);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card - VISA -------------------------------------------------------------------------------------------------------
        // 횟수합계
        strSearchQuery = "select count(priceAmount) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'V'" +
                " and status = '0' " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardVisaCnt = GlobalMemberValues.getCommaStringForInteger(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardVisaCountTextView.setText(tempCardVisaCnt);
        // 금액합계
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'V'" +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardVisa = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardVisaAmountTextView.setText(tempCardVisa);

        jsonroot.put("cardvisa", tempCardVisaCnt + "||" + tempCardVisa);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card - MASTER -------------------------------------------------------------------------------------------------------
        // 횟수합계
        strSearchQuery = "select count(priceAmount) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'M'" +
                " and status = '0' " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardMasterCnt = GlobalMemberValues.getCommaStringForInteger(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardMasterCountTextView.setText(tempCardMasterCnt);
        // 금액합계
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'M'" +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardMaster = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardMasterAmountTextView.setText(tempCardMaster);

        jsonroot.put("cardmaster", tempCardMasterCnt + "||" + tempCardMaster);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card - AMERICAN EXPRESS -------------------------------------------------------------------------------------------
        // 횟수합계
        strSearchQuery = "select count(priceAmount) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'A'" +
                " and status = '0' " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardAmexCnt = GlobalMemberValues.getCommaStringForInteger(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardAmexCountTextView.setText(tempCardAmexCnt);
        // 금액합계
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'A'" +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardAMex = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardAmexAmountTextView.setText(tempCardAMex);

        jsonroot.put("cardamex", tempCardAmexCnt + "||" + tempCardAMex);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card - DISCOVER ---------------------------------------------------------------------------------------------------
        // 횟수합계
        strSearchQuery = "select count(priceAmount) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'D'" +
                " and status = '0' " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardDiscoverCnt = GlobalMemberValues.getCommaStringForInteger(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardDiscoverCountTextView.setText(tempCardDiscoverCnt);
        // 금액합계
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'D'" +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardDiscover = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardDiscoverAmountTextView.setText(tempCardDiscover);

        jsonroot.put("carddiscover", tempCardDiscoverCnt + "||" + tempCardDiscover);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Card - OFFLINECARD ------------------------------------------------------------------------------------------------
        // 횟수합계
        strSearchQuery = "select count(priceAmount) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'OFFLINECARD'" +
                " and status = '0' " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardOfflinecardCnt = GlobalMemberValues.getCommaStringForInteger(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardOffCardCountTextView.setText(tempCardOfflinecardCnt);
        // 금액합계
        strSearchQuery = "select sum(round(priceAmount, 2)) from salon_sales_card " +
                " where strftime('%Y-%m-%d', wdate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and cardCom = 'OFFLINECARD'" +
                " and not(status = '1') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        // GlobalMemberValues.logWrite("batchsummaryCardQuery", "query : " + strSearchQuery + "\n");
        String tempCardOfflinecard = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryCreditCardOffCardAmountTextView.setText(tempCardOfflinecard);

        jsonroot.put("cardoffcard", tempCardOfflinecardCnt + "||" + tempCardOfflinecard);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // GiftCard ----------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(useTotalGiftCardAmountAfterReturned, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempGiftCardAmount = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryGiftCardAmountTextView.setText(tempGiftCardAmount);

        jsonroot.put("giftcard2", tempGiftCardAmount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Point -------------------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(useTotalPointAmountAfterReturned, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempGiftPoint = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryPointAmountTextView.setText(tempGiftPoint);

        jsonroot.put("point", tempGiftPoint);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Other (Check) -----------------------------------------------------------------------------------------------------
        strSearchQuery = "select sum(round(useTotalCheckAmountAfterReturned, 2)) from salon_sales " +
                " where strftime('%Y-%m-%d', saleDate) between '" + paramSearchDate + "'" +
                " and '" + paramSearchDate + "' " +
                " and delyn = 'N' " +
                " and status = 0 ";
        String tempOtherAmount = GlobalMemberValues.getCommaStringForDouble(MainActivity.mDbInit.dbExecuteReadReturnString(strSearchQuery));
        batchSummaryOtherAmountTextView.setText(tempOtherAmount);

        jsonroot.put("other", tempOtherAmount);  // JSON
        // -------------------------------------------------------------------------------------------------------------------

        // Batch 버튼 활성/비활성 관련
        setBatchButtonStatus();

        GlobalMemberValues.logWrite("batchsummaryjson", "결과물 : " + jsonroot + "\n");
    }

    public void setSummary(String paramSearchDate) throws JSONException {
        batchSummaryClosingReportLinearLayout.setVisibility(View.GONE);
        endofdayreportln.setVisibility(View.VISIBLE);

        JSONObject jsonroot = null;

        /**
        String tempJson = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select jsonstr from salon_sales_endofday_json " +
                        " where endofdayDate = '" + paramSearchDate + "' "
        );
        if (!GlobalMemberValues.isStrEmpty(tempJson)) {
            jsonroot = new JSONObject(tempJson);
        } else {
            //jsonroot = MakingJsonForCashOutEndofDay.getMakingJson("Y", newEndofdayNum, "");
            jsonroot = CashInOutPopup.setCashOutAndEOD("N", "Y", "N");
        }
         **/

        //jsonroot = MakingJsonForCashOutEndofDay.getMakingJson("Y", newEndofdayNum, "");
        jsonroot = CashInOutPopup.setCashOutAndEOD("N", "Y", "N");

        LinearLayout getJsonLn = null;
        getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(jsonroot, "Y");

        endofdayreportln.removeAllViews();
        endofdayreportln.addView(getJsonLn);

        // Batch 버튼 활성/비활성 관련
        setBatchButtonStatus();
    }



    View.OnClickListener batchSummaryBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.batchSummaryCloseBtn : {
                    finishActivity();
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    break;
                }
                case R.id.batchSummaryDateSearchTextView : {
                    String tempbatchSummarySearchDay = batchSummaryDateSearchTextView.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempbatchSummarySearchDay)) {
                        tempbatchSummarySearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = batchSummaryDateSearchTextView;
                    openDatePickerDialog(tempbatchSummarySearchDay);

                    break;
                }
                case R.id.batchSummaryDateSearchButton : {
                    String strSearchDate = batchSummaryDateSearchTextView.getText().toString();
                    if (!GlobalMemberValues.isStrEmpty(strSearchDate)) {
                        try {
                            setSummary(strSearchDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        GlobalMemberValues.displayDialog(context, "Summary", "Enter date to search", "Close");
                    }

                    break;
                }
                case R.id.batchSummaryBatchButton : {

                    //GlobalMemberValues.displayDialog(context, "Information", "Under Construction", "Close");
                    setBatchSales(context, mActivity, false);
                    break;
                }
                case R.id.batchSummaryPrintButton : {
                    //GlobalMemberValues.displayDialog(context, "Information", "Under Construction", "Close");
                    //GlobalMemberValues.printReceiptByJHP(jsonroot, context, "batchsummary");
                    try {
                        CashInOutPopup.setCashOutAndEOD("N", "Y", "Y");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /**
                    new AlertDialog.Builder(mActivity)
                            .setTitle("END OF DAY")
                            .setMessage("Do you want to end of day?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setEndOfDayAfterCheck();
                                }
                            }).show();
                     **/


                    break;
                }
                case R.id.batchedListBtn : {
                    Intent intent = new Intent(context, BatchListDetail.class);
                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                    intent.putExtra("openbatchedlist", "Y");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(intent);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.manualBatchBtn : {
                    Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "batchsummary_syncmanualbatch");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(adminPasswordIntent);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
            }
        }
    };

    public static void setBatchSales(Context paramContext, Activity paramActivity, boolean paramIsFromCashInOut) {
        // 가장 먼저 인터넷 체크를 한다.
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                return;
            }
        } else {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.openNetworkNotConnected();
                return;
            }
        }

        tempContext = paramContext;
        tempActivity = paramActivity;
        tempIsFromCashInOut = paramIsFromCashInOut;


        if (GlobalMemberValues.CARD_TIP_PROCESSING) {
            setBatchProcessing(paramContext, paramActivity, paramIsFromCashInOut);
            tempContext = null;
            tempActivity = null;
            tempIsFromCashInOut = false;
        } else {
            tipBatchProcess(paramContext, paramActivity, paramIsFromCashInOut);
        }
    }

    public static void tipBatchProcess(Context paramContext, Activity paramActivity, boolean paramIsFromCashInOut) {
        // 모든 팁이 프로세싱 처리되었으면 바로 Batch 처리
        if (GlobalMemberValues.getTipAdjustedN() == 0) {
            setBatchProcessing(paramContext, paramActivity, paramIsFromCashInOut);
            tempContext = null;
            tempActivity = null;
            tempIsFromCashInOut = false;
            return;
        } else {        // adjustedyn 이 N 인 데이터가 있을 경우 (팁 프로세싱이 처리안된 데이터가 있을 경우)
            tipAdjustInBatch("N");
        }
    }

    public static void tipAdjustInBatch(String paramOneTimeYN) {
        DatabaseInit tempDbInit = dbInit;
        Activity tempActivity = mActivity;
        Context tempContext = context;
        if (paramOneTimeYN == "Y" || paramOneTimeYN.equals("Y")) {
            tempDbInit = MainActivity.mDbInit;
            tempActivity = MainActivity.mActivity;
            tempContext = MainActivity.mContext;
        }

        String tipSalesCode = "";
        String tipUsedCard = "";
        String tipCardCom = "";
        String tipRefnum = "";

        String strQuery = "";

        strQuery = "select idx, salesCode, usedCard, cardCom, refnum from salon_sales_tip " +
                " where adjustedyn = 'N' and usedCard > 0 " +
                " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') " +
                " order by idx desc limit 1 ";
        Cursor salesTipCursor;
        salesTipCursor = tempDbInit.dbExecuteRead(strQuery);
        if (salesTipCursor.getCount() > 0 && salesTipCursor.moveToFirst()) {
            String tipIdx = GlobalMemberValues.getDBTextAfterChecked(salesTipCursor.getString(0), 1);
            tipSalesCode = GlobalMemberValues.getDBTextAfterChecked(salesTipCursor.getString(1), 1);
            tipUsedCard = GlobalMemberValues.getDBTextAfterChecked(salesTipCursor.getString(2), 1);
            tipCardCom = GlobalMemberValues.getDBTextAfterChecked(salesTipCursor.getString(3), 1);
            tipRefnum = GlobalMemberValues.getDBTextAfterChecked(salesTipCursor.getString(4), 1);

            if (GlobalMemberValues.getDoubleAtString(tipUsedCard) > 0) {
                tipUsedCard = GlobalMemberValues.getStringFormatNumber(tipUsedCard, "2");
            }

            mTipIdx = tipIdx;
        }
        salesTipCursor.close();

        GlobalMemberValues.logWrite("onetimetipadjustmentlog", "여기실행3" + "\n");
        GlobalMemberValues.logWrite("tipadjustedynlog2", "tipRefnum : " + tipRefnum + "\n");
        GlobalMemberValues.logWrite("tipadjustedynlog2", "tipUsedCard : " + tipUsedCard + "\n");

        if (!GlobalMemberValues.isStrEmpty(tipRefnum) && !GlobalMemberValues.isStrEmpty(tipUsedCard)
                && GlobalMemberValues.getDoubleAtString(tipUsedCard) > 0) {
            Intent intent = new Intent(tempContext, JJJ_PaxPay.class);
            // 객체 및 데이터 전달하기 ---------------------------------------------------------------
            intent.putExtra("cardtendertype", "CREDIT");
            intent.putExtra("processtype", "ADJUST");
            intent.putExtra("amounttopay", String.valueOf(GlobalMemberValues.getIntAtString2(tipUsedCard)));
            intent.putExtra("cardcom", tipCardCom);
            intent.putExtra("lastfourdigits", "");
            intent.putExtra("refnum", tipRefnum);
            intent.putExtra("ecrrefnum", tipSalesCode);
            intent.putExtra("fromsignpad", "N");
            intent.putExtra("adjustedynexe", "Y");
            intent.putExtra("tiponetimeyn", paramOneTimeYN);           // 한번만 실행할지 여부
            // -------------------------------------------------------------------------------------
            tempActivity.startActivity(intent);
        }
    }

    public static void tipDbExe(String paramTipOneTimeYN) {
        GlobalMemberValues.logWrite("tipadjustedynlog", "mTipAdjusctResulf : " + mTipAdjusctResulf + "\n");

//            if (mTipAdjusctResulf.equals("00")) {
//            }
        Vector<String> tipAdjustVec = new Vector<String>();
        String strQuery = " update salon_sales_tip set adjustedyn = 'Y' where idx = '" + mTipIdx + "' ";
        tipAdjustVec.addElement(strQuery);
        for (String tempQuery : tipAdjustVec) {
            GlobalMemberValues.logWrite("tipadjustedynlog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(tipAdjustVec);
        if (returnResult == "N" || returnResult == "") {
        } else {
//                    GlobalMemberValues.logWrite("kitchenprintedchangestatus", "성공" + "\n");
        }

        mTipIdx = "";
        mTipAdjusctResulf = "";

        // 팁처리가 한번 실행이 아닌 계속실행일 경우에만 아래를 실행
        if (GlobalMemberValues.isStrEmpty(paramTipOneTimeYN) || paramTipOneTimeYN == "N" || paramTipOneTimeYN.equals("N")) {
            tipBatchProcess(tempContext, tempActivity, tempIsFromCashInOut);
        }
    }

    public static void setBatchProcessing(Context paramContext, Activity paramActivity, boolean paramIsFromCashInOut) {
        // paramIsFromCashInOut 의 값이 true 이면 배치결과 팝업창이 나타나지 않는다..
        mIsFromCashInOut = paramIsFromCashInOut;

        Intent intent = new Intent(paramContext, JJJ_PaxBatch.class);
        // 객체 및 데이터 전달하기 ---------------------------------------------------------------
        //intent.putExtra("cardtendertype", "CREDIT");
        // -------------------------------------------------------------------------------------
        paramActivity.startActivity(intent);
    }

    public static void setBatchSaleResult(JSONObject paramJson) throws JSONException {
        if (paramJson != null) {
            DatabaseInit tempMDbInit = MainActivity.mDbInit;

            JSONObject getJson = paramJson;
            if (getJson.toString().contains("ResultCode")){
                if (getJson.getString("ResultCode") == "000000" || getJson.getString("ResultCode").equals("000000")) {
                    // 배치처리한 tip 총액
                    String tipAmountForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(usedCard, 2)) from salon_sales_tip " +
                                    " where salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and salesCode in (select salesCode from salon_sales_card where delyn = 'N' and status = '0') " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "
                    );

                    // Visa 카드 배치처리한 총액
                    String visaAmountForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(priceAmount, 2)) from salon_sales_card " +
                                    " where cardCom = 'V' " +
                                    " and delyn = 'N' " +
                                    " and not(cardCom = 'OFFLINECARD') " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "
                    );

                    // Master 카드 배치처리한 총액
                    String masterAmountForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(priceAmount, 2)) from salon_sales_card " +
                                    " where cardCom = 'M' " +
                                    " and delyn = 'N' " +
                                    " and not(cardCom = 'OFFLINECARD') " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "
                    );

                    // Amex 카드 배치처리한 총액
                    String amexAmountForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(priceAmount, 2)) from salon_sales_card " +
                                    " where cardCom = 'A' " +
                                    " and delyn = 'N' " +
                                    " and not(cardCom = 'OFFLINECARD') " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "
                    );

                    // Discover 카드 배치처리한 총액
                    String discoverAmountForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(priceAmount, 2)) from salon_sales_card " +
                                    " where cardCom = 'D' " +
                                    " and delyn = 'N' " +
                                    " and not(cardCom = 'OFFLINECARD') " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "
                    );

                    // 카드 Pickup / Delivery fee
                    String pickupDeliveryFeeForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(deliverypickupfee, 2)) from salon_sales " +
                                    " where salesCode in ( " +
                                    " select salesCode from salon_sales_card " +
                                    " where " +
                                    " delyn = 'N' " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "+
                                    " ) "
                    );

                    // 카드 Pickup fee
                    String pickupFeeForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(deliverypickupfee, 2)) from salon_sales " +
                                    " where salesCode in ( " +
                                    " select salesCode from salon_sales_card " +
                                    " where " +
                                    " deliverytakeaway = 'T' " +
                                    " and delyn = 'N' " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "+
                                    " ) "
                    );

                    // 카드 Delivery fee
                    String deliveryFeeForBatch = tempMDbInit.dbExecuteReadReturnString(
                            " select sum(round(deliverypickupfee, 2)) from salon_sales " +
                                    " where salesCode in ( " +
                                    " select salesCode from salon_sales_card " +
                                    " where " +
                                    " deliverytakeaway = 'D' " +
                                    " and delyn = 'N' " +
                                    " and status = '0' " +
                                    " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                                    " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') "+
                                    " ) "
                    );

                    getJson.put("totalcardtip", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(tipAmountForBatch), "2"));
                    getJson.put("visaamount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(visaAmountForBatch), "2"));
                    getJson.put("masteramount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(masterAmountForBatch), "2"));
                    getJson.put("amexamount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(amexAmountForBatch), "2"));
                    getJson.put("discoveramount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(discoverAmountForBatch), "2"));
                    getJson.put("pickupdeliveryfee", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(pickupDeliveryFeeForBatch), "2"));
                    getJson.put("pickupfee", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(pickupFeeForBatch), "2"));
                    getJson.put("deliveryfee", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(deliveryFeeForBatch), "2"));

                    // 데이터베이스 배치 처리
                    String strQuery = "";
                    String strInsQuery = "";
                    ArrayList<String> salesCodeGroupArrayList = new ArrayList<String>();
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    strQuery = "select salesCode from salon_sales_card " +
                            " where delyn = 'N' " +
                            " and not(cardCom = 'OFFLINECARD') " +
                            " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                            " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') " +
                            " order by idx asc ";
                    Cursor salonSalesBatchCursor = tempMDbInit.dbExecuteRead(strQuery);
                    while (salonSalesBatchCursor.moveToNext()) {
                        String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesBatchCursor.getString(0), 1);
                        if (!salesCodeGroupArrayList.contains(tempSalesCode)) {
                            strInsQuery = " insert into salon_sales_batch (salesCode) values ( " +
                                    " '" + tempSalesCode + "' " +
                                    " ) ";
                            strInsertQueryVec.addElement(strInsQuery);
                            salesCodeGroupArrayList.add(tempSalesCode);
                        }
                    }

                    // salon_sales_batch_json 테이블 저장
                    String batchJsonStr = GlobalMemberValues.getDBTextAfterChecked(getJson.toString(), 0);
                    strInsQuery = " insert into salon_sales_batch_json (jsonstr) values ( " +
                            " '" + batchJsonStr + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(strInsQuery);

                    for (String tempQuery : strInsertQueryVec) {
                        GlobalMemberValues.logWrite("salonSalesBatchLog", "query : " + tempQuery + "\n");
                    }

                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = tempMDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                    } else {
                        GlobalMemberValues.logWrite("salonSalesBatchLog", "returnResult : " + returnResult + "\n");

                        // Batch 버튼 비활성화
                        setBatchButtonStatus();

                        if (!mIsFromCashInOut) {
                            // DB 정상처리
                            Intent intent = new Intent(context, BatchListDetail.class);
                            // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                            intent.putExtra("batchJson", getJson.toString());
                            // -------------------------------------------------------------------------------------
                            mActivity.startActivity(intent);
                            if (GlobalMemberValues.isUseFadeInOut()) {
                                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
                            }
                        } else {
                        }
                    }
                } else {
                    if (getJson.getString("ResultCode") == "100023"
                            || getJson.getString("ResultCode").equals("100023")) {

                        GlobalMemberValues.displayDialog(context, "Warning", "Not Found to Batch", "Close");

                        // 배치할 것이 없으면 현재 배치로 상태변경이 안된 데이터베이스의 주문내역을 임의로 배치 처리
                        // 데이터베이스 배치 처리
                        setSyncAfterManualBatch();
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Batch Error (System Error - " + getJson.getString("ResultCode") + ")", "Close");
                    }
                }
            } else {
                GlobalMemberValues.displayDialog(context, "Warning", "Batch Error (No Return Value)", "Close");
            }
        }
    }

    public static void callSyncAfterManualBatch() {
        new AlertDialog.Builder(mActivity)
                .setTitle("Sync. Data")
                .setMessage("Synchronized data cannot be batched later.\n" +
                        "Do you want to synchronize the data batched manually?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setSyncAfterManualBatch();
                    }
                }).show();
    }

    public static void setSyncAfterManualBatch() {
        DatabaseInit tempMDbInit = MainActivity.mDbInit;

        // 배치할 것이 없으면 현재 배치로 상태변경이 안된 데이터베이스의 주문내역을 임의로 배치 처리
        // 데이터베이스 배치 처리
        String strQuery = "";
        String strInsQuery = "";
        ArrayList<String> salesCodeGroupArrayList = new ArrayList<String>();
        Vector<String> strInsertQueryVec = new Vector<String>();
        strQuery = "select salesCode from salon_sales_card " +
                " where delyn = 'N' " +
                " and not(cardCom = 'OFFLINECARD') " +
                " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N' ) " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') " +
                " order by idx asc ";
        Cursor salonSalesBatchCursor = tempMDbInit.dbExecuteRead(strQuery);
        while (salonSalesBatchCursor.moveToNext()) {
            String tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(salonSalesBatchCursor.getString(0), 1);
            if (!salesCodeGroupArrayList.contains(tempSalesCode)) {
                strInsQuery = " insert into salon_sales_batch (salesCode) values ( " +
                        " '" + tempSalesCode + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsQuery);
                salesCodeGroupArrayList.add(tempSalesCode);
            }
        }

        // 팁도 모두 adjust 된 것으로 처리한다.
        strInsQuery = " update salon_sales_tip set adjustedyn = 'Y' ";
        strInsertQueryVec.addElement(strInsQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("salonSalesBatchLog", "query : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = tempMDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else {
            GlobalMemberValues.logWrite("salonSalesBatchLog", "returnResult : " + returnResult + "\n");
            // Batch 버튼 비활성화
            setBatchButtonStatus();
        }
    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(this, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    public static void setBatchButtonStatus() {
        if (mIsFromCashInOut) {
            return;
        }
        /** Batch 버튼 관련 *****************************************************************************************/
        // salon_sales_card 테이블을 조회하여
        // 배치가 안된 카드결제가 있을 경우 Batch 버튼을 활성화
        // 배치가 안된 카드결제가 없을 경우 Batch 버튼을 비활성화
        String strBatchCountMsg = "";
        int salonSalesCardCount = getCountToBatch(MainActivity.mDbInit);
        if (salonSalesCardCount > 0) {
            if (batchSummaryBatchButton != null) {
                batchSummaryBatchButton.setEnabled(true);
                if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                    batchSummaryBatchButton.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_batch);
                } else {
                    batchSummaryBatchButton.setBackgroundResource(R.drawable.button_selector_cloud_download);
                }
            }

            if (manualBatchBtn != null) {
                manualBatchBtn.setEnabled(true);
                manualBatchBtn.setTextColor(Color.parseColor("#036cd6"));
            }

            if (salonSalesCardCount > 1) {
                strBatchCountMsg = "There are " + salonSalesCardCount + " items to batch";
            } else {
                strBatchCountMsg = "There is " + salonSalesCardCount + " item to batch";
            }
            if (batchSummaryNoBatchListTextView != null) {
                batchSummaryNoBatchListTextView.setTextColor(Color.parseColor("#5C8CB4"));
            }
        } else {
            if (batchSummaryBatchButton != null) {
                batchSummaryBatchButton.setEnabled(false);
                if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                    batchSummaryBatchButton.setBackgroundResource(R.drawable.aa_images_batchsummary_batch_rollover);
                } else {
                    batchSummaryBatchButton.setBackgroundResource(R.drawable.button_selector_gray);
                }
            }

            if (manualBatchBtn != null) {
                manualBatchBtn.setEnabled(false);
                manualBatchBtn.setTextColor(Color.parseColor("#ffffff"));
            }

            strBatchCountMsg = "There is no item to batch";
            if (batchSummaryNoBatchListTextView != null) {
                batchSummaryNoBatchListTextView.setTextColor(Color.parseColor("#858585"));
            }
        }
        if (batchSummaryNoBatchListTextView != null) {
            batchSummaryNoBatchListTextView.setText(strBatchCountMsg);
        }
        /***********************************************************************************************************/
    }

    public static int getCountToBatch(DatabaseInit paramDbInit) {
        int returnValue = 0;

        String strSearchSql = " select count(*) from salon_sales_card " +
                " where delyn = 'N' and status = '0' " +
                " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        if (paramDbInit == null) {
            paramDbInit = MainActivity.mDbInit;
        }
        if (paramDbInit != null) {
            returnValue = GlobalMemberValues.getIntAtString(paramDbInit.dbExecuteReadReturnString(strSearchSql));
        }

        return returnValue;
    }

    // 초기화 메소드
    private void setInit() {
        mIsFromCashInOut = false;

        batchSummaryDateSearchTextView.setText("");
        batchSummarySaleDateTextView.setText("");
        batchSummaryServiceTextView.setText("");
        batchSummaryProductTextView.setText("");
        batchSummaryGiftCardTextView.setText("");
        batchSummaryDiscountTextView.setText("");
        batchSummaryVoidTextView.setText("");
        batchSummaryNetSalesTextView.setText("");
        batchSummaryRefundTextView.setText("");
        batchSummaryTaxTextView.setText("");
        batchSummaryGrossSalesTextView.setText("");
        batchSummaryCashAmountTextView.setText("");
        batchSummaryCreditCardAmountTextView.setText("");
        batchSummaryCreditCardVisaCountTextView.setText("");
        batchSummaryCreditCardVisaAmountTextView.setText("");
        batchSummaryCreditCardMasterCountTextView.setText("");
        batchSummaryCreditCardMasterAmountTextView.setText("");
        batchSummaryCreditCardAmexCountTextView.setText("");
        batchSummaryCreditCardAmexAmountTextView.setText("");
        batchSummaryCreditCardDiscoverCountTextView.setText("");
        batchSummaryCreditCardDiscoverAmountTextView.setText("");
        batchSummaryCreditCardOffCardCountTextView.setText("");
        batchSummaryCreditCardOffCardAmountTextView.setText("");
        batchSummaryGiftCardAmountTextView.setText("");
        batchSummaryPointAmountTextView.setText("");
        batchSummaryPrintedDateTextView.setText("");
    }

    public static void finishActivity() {
        // 영수증 이미지 폴더 삭제
        GlobalMemberValues.deleteImageForPrinting();

        mIsFromCashInOut = false;
        paramAutoCloseYN = "N";
        mActivity.finish();
    }
}
