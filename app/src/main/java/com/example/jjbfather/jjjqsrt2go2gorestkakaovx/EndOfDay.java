package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class EndOfDay extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private LinearLayout parentLn;

    private static LinearLayout batchSummaryClosingReportLinearLayout;
    private LinearLayout batchSummaryEmployeeListLinearLayout;

    private static LinearLayout endofdayreportln;

    Intent mIntent;

    private Button closeBtn;

    private Button batchSummaryDateSearchButton;
    private static Button batchSummaryBatchButton;
    private Button batchSummaryPrintButton, batchedListBtn, endofdayexecbtn;

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

    static JSONObject jsonForPrinting = null;

    static boolean isEndofdayAdjusted = true;

    static String mEOD_Date = "";

    // Online_Kiosk_관련
    public static ProgressDialog onlineKioskProdial;
    public static String mOnlineKioskData = "";
    public static String mSearchDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_end_of_day);
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

        mEOD_Date = "";

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

        endofdayexecbtn = (Button)parentLn
                .findViewWithTag("endofdayexecbtnTag");

        batchedListBtn = (Button)parentLn
                .findViewWithTag("batchedListBtnTag");
        batchedListBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                batchedListBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            LinearLayout batchsummaryLn2 = (LinearLayout)findViewById(R.id.batchsummaryLn);

            batchsummaryLn2.setVisibility(View.GONE);
        }

        batchSummaryDateSearchButton.setOnClickListener(batchSummaryBtnClickListener);
        batchSummaryBatchButton.setOnClickListener(batchSummaryBtnClickListener);
        batchSummaryPrintButton.setOnClickListener(batchSummaryBtnClickListener);
        endofdayexecbtn.setOnClickListener(batchSummaryBtnClickListener);
        batchedListBtn.setOnClickListener(batchSummaryBtnClickListener);
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

        if (GlobalMemberValues.isStrEmpty(batchSummaryDateSearchTextView.getText().toString())) {
            batchSummaryDateSearchTextView.setText(strSearchDate);
        }

        try {
            setSummary(strSearchDate);
        } catch (JSONException e) {
            e.printStackTrace();
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
            //setBatchSales(context, mActivity, false);
            //finishActivity();
        }
    }

    // Online_Kiosk_관련 =========================================================================================
    public static String getOnlineKioskDataFromApi(Context context, String paramSearchDate) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = GlobalMemberValues.MASTER_PWD;
            } else {
                mSearchDate = paramSearchDate;
                API_Online_Kiosk_Data_ForEOD apicheckInstance = new API_Online_Kiosk_Data_ForEOD(paramSearchDate);
                apicheckInstance.execute(null, null, null);

                int while_count = 0;
                while (apicheckInstance.mReturnValue.isEmpty()){
                    if (while_count == 10)break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while_count++;
                }
                returnValue = apicheckInstance.mReturnValue;

//                try {
//                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//                    if (apicheckInstance.mFlag) {
//                        returnValue = apicheckInstance.mReturnValue;
//                    }
//                } catch (InterruptedException e) {
//                    returnValue = "";
//                }

                GlobalMemberValues.logWrite("API_Online_Kiosk_Data_ForEOD_Log", "returnValue : " + returnValue + "\n");
            }
        } else {
            //GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    public static void getOnlineKioskData(String paramSearchDate) {
        // 프로그래스 바를 실행~
        onlineKioskProdial = ProgressDialog.show(
                context, "System Check", "The system is downloading data of the online and kiosk from the cloud", true, false
        );
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                String tempValue = getOnlineKioskDataFromApi(context, paramSearchDate);
                mOnlineKioskData = tempValue;

                GlobalMemberValues.logWrite("API_Online_Kiosk_Data_ForEOD_Log", "mOnlineKioskData : " + mOnlineKioskData + "\n");
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                masterPwdcheckhandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private static Handler masterPwdcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            GlobalMemberValues.logWrite("API_Online_Kiosk_Data_ForEOD_Log2", "가져온데이터 : " + mOnlineKioskData + "\n");

            try {
                setSummaryExe(mSearchDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            onlineKioskProdial.dismiss();
            // -------------------------------------------------------------------------------------
        }
    };
    // ==========================================================================================================

    public static void setSummary(String paramSearchDate) throws JSONException {
        // Online_Kiosk_관련
        if (GlobalMemberValues.isOnlineOrderUseYN()) {
            getOnlineKioskData(paramSearchDate);
        } else {
            setSummaryExe(paramSearchDate);
        }
    }

    // Online_Kiosk_관련 =====================================================================
    public static void setSummaryExe(String paramSearchDate) throws JSONException {

        batchSummaryClosingReportLinearLayout.setVisibility(View.GONE);
        endofdayreportln.setVisibility(View.VISIBLE);

        JSONObject jsonroot = null;

        String tempSql = " select jsonstr from salon_sales_endofday_json " +
                " where endofdayDate = '" + paramSearchDate + "' " +
                " order by idx desc ";

        GlobalMemberValues.logWrite("eodtestlog", "tempSql : " + tempSql + "\n");

        String tempJson = MssqlDatabase.getResultSetValueToString(tempSql);
        if (!GlobalMemberValues.isStrEmpty(tempJson)) {
            jsonroot = new JSONObject(tempJson);
            jsonroot.put("previous_endofday", paramSearchDate);
            GlobalMemberValues.logWrite("eodtestlog", "여기출력1" + "\n");
            isEndofdayAdjusted = true;
        } else {
            jsonroot = MakingJsonForCashOutEndofDay.getMakingJson("Y", GlobalMemberValues.getNewEndOfDay(), "");
            //jsonroot = CashInOutPopup.setCashOutAndEOD("Y", "Y", "N");
            jsonroot.put("previous_endofday", "");
            GlobalMemberValues.logWrite("eodtestlog", "여기출력2" + "\n");
            isEndofdayAdjusted = false;
        }


        // Online_Kiosk_관련 -----------------------------------------------------
        String[] onlineDataArr = {"", "", "", "", ""};
        String[] kioskDataArr = {"", "", "", "", ""};

        // 10112023
        String thirdpartyDatas = "";

        if (!GlobalMemberValues.isStrEmpty(mOnlineKioskData)) {
//            String[] tempSplitData = mOnlineKioskData.split("-JJJ-");
            String[] tempSplitData = mOnlineKioskData.split("-J-");
            // Online 데이터
            if (!GlobalMemberValues.isStrEmpty(tempSplitData[0])) {
//                String[] tempOnlineData = tempSplitData[0].split("-J-");
//                onlineDataArr[0] = tempOnlineData[0];
//                onlineDataArr[1] = tempOnlineData[1];
//                onlineDataArr[2] = tempOnlineData[2];
//                onlineDataArr[3] = tempOnlineData[3];
//                onlineDataArr[4] = tempOnlineData[4];
                onlineDataArr[0] = tempSplitData[0];    // transactions
                onlineDataArr[1] = tempSplitData[1];    // sales total
                onlineDataArr[2] = tempSplitData[2];    // refund
                onlineDataArr[3] = tempSplitData[3];    // tip amount
                onlineDataArr[4] = tempSplitData[4];    // total amount

            }
            // Kiosk 데이터
            if (!GlobalMemberValues.isStrEmpty(tempSplitData[5])) {
//                String[] tempKioskData = tempSplitData[1].split("-J-");
//                kioskDataArr[0] = tempKioskData[0];
//                kioskDataArr[1] = tempKioskData[1];
//                kioskDataArr[2] = tempKioskData[2];
//                kioskDataArr[3] = tempKioskData[3];
//                kioskDataArr[4] = tempKioskData[4];
                kioskDataArr[0] = tempSplitData[5]; // transactions
                kioskDataArr[1] = tempSplitData[6]; // sales total
                kioskDataArr[2] = tempSplitData[7]; // refund
                kioskDataArr[3] = tempSplitData[8]; // tip amount
                kioskDataArr[4] = tempSplitData[9]; // total amount
            }


            // 10112023
            // third party 데이터
            if (tempSplitData.length > 10) {
                if (!GlobalMemberValues.isStrEmpty(tempSplitData[10])) {
                    thirdpartyDatas = tempSplitData[10];
                }
            }

            jsonroot.put("onlinekioskdatayn", "Y");

            // 받아온 부분을 salesbytendertypes_totaltransaction, salesbytendertypes_totalamount 에 추가 적용
            if (!GlobalMemberValues.isStrEmpty(tempSplitData[0]) && !GlobalMemberValues.isStrEmpty(tempSplitData[5])){
                String temp_salesbytendertypes_totaltransaction = jsonroot.getString("salesbytendertypes_totaltransaction");
                String temp_salesbytendertypes_totalamount = jsonroot.getString("salesbytendertypes_totalamount");
                int addup_temp_salesbytendertypes_totaltransaction = Integer.parseInt(temp_salesbytendertypes_totaltransaction) + Integer.parseInt(onlineDataArr[0]) + Integer.parseInt(kioskDataArr[0]);
                Double addup_temp_salesbytendertypes_totalamount = GlobalMemberValues.getDoubleAtString(temp_salesbytendertypes_totalamount) + GlobalMemberValues.getDoubleAtString(onlineDataArr[4]) + GlobalMemberValues.getDoubleAtString(kioskDataArr[4]);
                String str_addup_temp_salesbytendertypes_totalamount = GlobalMemberValues.getStringFormatNumber(addup_temp_salesbytendertypes_totalamount,"2");

                jsonroot.put("salesbytendertypes_totaltransaction", String.valueOf(addup_temp_salesbytendertypes_totaltransaction));
                jsonroot.put("salesbytendertypes_totalamount", str_addup_temp_salesbytendertypes_totalamount);
            }

        } else {
            jsonroot.put("onlinekioskdatayn", "N");
        }

        // Online 데이터
        jsonroot.put("onlinedata_transactions", onlineDataArr[0]);
        jsonroot.put("onlinedata_salestotal", onlineDataArr[1]);
        jsonroot.put("onlinedata_refund", onlineDataArr[2]);
        jsonroot.put("onlinedata_tipamount", onlineDataArr[3]);
        jsonroot.put("onlinedata_totalamount", onlineDataArr[4]);

        // Kiosk 데이터
        jsonroot.put("kioskdata_transactions", kioskDataArr[0]);
        jsonroot.put("kioskdata_salestotal", kioskDataArr[1]);
        jsonroot.put("kioskdata_refund", kioskDataArr[2]);
        jsonroot.put("kioskdata_tipamount", kioskDataArr[3]);
        jsonroot.put("kioskdata_totalamount", kioskDataArr[4]);


        // 10112023
        // third party 데이터
        jsonroot.put("thirdparty_data", thirdpartyDatas);

        // ----------------------------------------------------------------------

        LinearLayout getJsonLn = null;
        getJsonLn = CloverMakingViewInPrinting.makingLinearLayoutForCashInOut(jsonroot, "Y");

        GlobalMemberValues.logWrite("API_Online_Kiosk_Data_ForEOD_Log3", "json 데이터 : " + jsonroot.toString() + "\n");

        endofdayreportln.removeAllViews();
        endofdayreportln.addView(getJsonLn);

        jsonForPrinting = jsonroot;

        // Batch 버튼 활성/비활성 관련
        setBatchButtonStatus();
    }
    // ======================================================================================



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
                    LogsSave.saveLogsInDB(41);
                    setSearchByDate();

                    break;
                }
                case R.id.batchSummaryBatchButton : {

                    //GlobalMemberValues.displayDialog(context, "Information", "Under Construction", "Close");
                    setBatchSales(context, mActivity, false);
                    break;
                }
                case R.id.batchSummaryPrintButton : {
                    /**
                    try {
                        CashInOutPopup.setCashOutAndEOD("Y", "Y", "Y");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                     **/
                    if (!isEndofdayAdjusted) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You can print only sale lists processed EOD", "Close");
                        return;
                    }

                    if (jsonForPrinting != null) {
                        LogsSave.saveLogsInDB(42);
                        GlobalMemberValues.printReceiptByJHP(jsonForPrinting, context, "cashout");
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "No data", "Close");
                        return;
                    }

                    break;
                }
                case R.id.endofdayexecbtn : {
                    LogsSave.saveLogsInDB(43);
                    endOfDayExec();

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
            }
        }
    };

    public void setSearchByDate() {
        String tempSearchDate = batchSummaryDateSearchTextView.getText().toString();
        String tempSql = " select count(*) from salon_sales_endofday_json " +
                " where endofdayDate = '" + tempSearchDate + "' ";
        int tempCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempSql));
        if (tempCount == 0) {
            GlobalMemberValues.displayDialog(context, "Warning",
                    "There is no end of day data for the searched date", "Close");
            return;
        }

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
    }

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

        // paramIsFromCashInOut 의 값이 true 이면 배치결과 팝업창이 나타나지 않는다..
        mIsFromCashInOut = paramIsFromCashInOut;

        Intent intent = new Intent(paramContext, JJJ_PaxBatch.class);
        // 객체 및 데이터 전달하기 ---------------------------------------------------------------
        //intent.putExtra("cardtendertype", "CREDIT");
        // -------------------------------------------------------------------------------------
        paramActivity.startActivity(intent);
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

            if (salonSalesCardCount > 1) {
                //strBatchCountMsg = "There are " + salonSalesCardCount + " items to batch";
            } else {
                //strBatchCountMsg = "There is " + salonSalesCardCount + " item to batch";
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

            //strBatchCountMsg = "There is no item to batch";
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


    public static void endOfDayExec() {
        Intent intent = new Intent(context, EndOfDaySelectDate.class);
        // 객체 및 데이터 전달하기 ---------------------------------------------------------------
        intent.putExtra("parameoddate", GlobalMemberValues.STR_NOW_DATE);
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(intent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
        }
//        new AlertDialog.Builder(mActivity)
//                .setTitle("END OF DAY")
//                .setMessage("Do you want to end of day?")
//                .setNegativeButton("No", null)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        setEndOfDayAfterCheck();
//                    }
//                }).show();
    }

    public static void setEndOfDayAfterCheck() {
        // 선택한 일자로 End of day 처리된 내역이 있는지 확인한다.
        // 선택한 일자가 없으면 오늘 일자로
        String strSearchDate = mEOD_Date;
        if (GlobalMemberValues.isStrEmpty(strSearchDate)) {
            strSearchDate = GlobalMemberValues.STR_NOW_DATE;
        }
        String tempSql = " select count(*) from salon_sales_endofday_json " +
                " where endofdayDate = '" + strSearchDate + "' ";
        //GlobalMemberValues.logWrite("eodtestlog", "tempSql : " + tempSql + "\n");
        int tempCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempSql));
        if (tempCount > 0) {
            GlobalMemberValues.displayDialog(context, "Warning",
                    "There is an EOD already processed.\nEOD can only be done once a day", "Close");
            return;
        }

        // endofday number 구하기 -----------------------------------------------------------------------------------
        int newEndofdayNum = GlobalMemberValues.getNewEndOfDay();
        // ----------------------------------------------------------------------------------------------------------

        // 가장 먼저 Cash In/Out 처리된 내역이 있는지 확인
        int cashoutHistoryCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from salon_sales_cashout_json " +
                        " where cashoutNum > 0 and endofdayNum = '" + newEndofdayNum + "' "
        ));

        if (cashoutHistoryCnt == 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "There is no history to end of day.\nPlease cash out first.", "Close");
            return;
        } else {
            try {
                final JSONObject jsonObject = CashInOutPopup.setCashOutAndEOD("Y", "N", "Y");

                // eod 완료 /
                // 053023
                GlobalMemberValues.serviceStartSendDataToCloud(MainActivity.mContext,MainActivity.mActivity);
                // 053023

                new AlertDialog.Builder(mActivity)
                        .setTitle("Printing")
                        .setMessage("Would you print the end of day report?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              GlobalMemberValues.printReceiptByJHP(jsonObject, context, "cashout");
                            }
                        }).show();



                // 영수증 이미지 폴더 삭제
                GlobalMemberValues.deleteImageForPrinting();

                // setSummary 실행해서 json 데이터 다시 가져옴
                setSummary(strSearchDate);

                if (GlobalMemberValues.isDataBase5mb()){
                    if (GlobalMemberValues.b_no_dbcompact){
                        // cashinout popup 에서 db compact 를 진행하지 않은경우 popup 창 띄우지 않음.
                    } else {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("NZ ANDROID")
                                .setMessage("Database capacity exceeded " + GlobalMemberValues.init_capacity_db + "mb. Would you initialize the sale data after database backup?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
                                                GlobalMemberValues.startDatabaseCheckBackup();
                                            }
                                        })
                                .show();
                    }
                    GlobalMemberValues.b_no_dbcompact = false;
                }

                // 직원 로그아웃
                //CashInOutPopup.empLogout("N");
                //mActivity.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        // End of day Json 데이터 클라우드 전송
        GlobalMemberValues.setSendEndOfDayJsonToCloud(context, mActivity);

        // Cashout Json 데이터 클라우드 전송
        GlobalMemberValues.setSendCashOutJsonToCloud(context, mActivity);

        mEOD_Date = "";
        mIsFromCashInOut = false;
        paramAutoCloseYN = "N";
        mActivity.setResult(RESULT_OK);
        mActivity.finish();
    }
}
