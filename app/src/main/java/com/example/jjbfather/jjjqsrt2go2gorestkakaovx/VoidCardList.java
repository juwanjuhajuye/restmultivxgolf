package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class VoidCardList extends Activity {
    public static Activity mActivity;
    public static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;

    private LinearLayout tipCardListCardListLinearLayout;
    private Button tipCardListSelectSubmitButton;

    Intent mIntent;

    static String mSalesCode = "";

    public String mSelectedSalonSalesCardIdx = "";
    public String mSelectedPayType = "CARD";
    public LinearLayout mSelectedLn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_void_card_list);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 90) * 30;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.tipCardListLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mSalesCode = mIntent.getStringExtra("salesCode");
            GlobalMemberValues.logWrite("tipCardList", "넘겨받은 SalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 3" + "\n");

        setContents();
    }

    public void setContents() {
        mActivity = this;
        context = this;

        dbInit = new DatabaseInit(this);

        GlobalMemberValues.mOnVoidForBillPay = false;

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("tipCardListCloseBtnTag");
        closeBtn.setOnClickListener(tipCardListBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_close);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        tipCardListSelectSubmitButton = (Button)parentLn
                .findViewWithTag("tipCardListSelectSubmitButtonTag");
        tipCardListSelectSubmitButton.setOnClickListener(tipCardListBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            tipCardListSelectSubmitButton.setText("");
            tipCardListSelectSubmitButton.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_enter);
        } else {
            tipCardListSelectSubmitButton.setTextSize(
                    tipCardListSelectSubmitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        tipCardListCardListLinearLayout = (LinearLayout)findViewById(R.id.tipCardListCardListLinearLayout);

        // 06.07.2022 ------------------------------------------------------------------------------------------
        // bill_list_paid 에 저장된 데이터가 없으면 SaleHistory.startVoidProcess() 실행후에
        // 해당 창을 닫는다
        int billListPaidCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(*) from bill_list_paid where salesCode = '" + mSalesCode + "' "
        ));

        if (billListPaidCnt == 0) {
            if (mActivity != null) {
                mActivity.finish();
            }
        }
        // -----------------------------------------------------------------------------------------------------


        /** 결제시 사용한 카드 리스트 ******************************************************************************/
        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 10);

        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);

        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);

        //Cursor tipCardListCursor;
        ResultSet tipCardListCursor;

        String salonSalesDetailQuery = "select B.idx, B.tid, B.cardCom, B.priceAmount, B.tipAmount, B.insertSwipeKeyin, " +
                " B.cardLastFourDigitNumbers, B.cardRefNumber, B.cardEmvAid, B.cardEmvTsi, B.cardEmvTvr, A.paytype, A.paidamount, A.idx " +
                " from bill_list_paid A left join salon_sales_card B on A.cardsalesidx = B.idx  " +
                " where A.salesCode = '" + mSalesCode + "' ";

        tipCardListCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
        try {
            while (tipCardListCursor.next()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 0), 0);
                String tempTid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 1), 0);
                String tempCardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 2), 0);
                //String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 3), 0);
                String tempTipAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 4), 0);
                String tempInsertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 5), 0);
                String tempCardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 6), 0);
                String tempCardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 7), 0);
                String tempCardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 8), 0);
                String tempCardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 9), 0);
                String tempCardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 10), 0);

                String tempPayType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 11), 0);
                if (GlobalMemberValues.isStrEmpty(tempPayType)) {
                    tempPayType = "CARD";
                }

                String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 12), 0);

                String tempBillPaidIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 13), 0);

                Typeface typeface_monserrat_bold = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    typeface_monserrat_bold = mActivity.getResources().getFont(R.font.montserratbold);
                } else {
                    typeface_monserrat_bold = ResourcesCompat.getFont(context, R.font.montserratbold);
                }

                // LinearLayout 객체 생성
                LinearLayout newLn = new LinearLayout(this);
                newLn.setLayoutParams(newLnLayoutParams);
                newLn.setOrientation(LinearLayout.VERTICAL);
                newLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                if (tempPayType.equals("CARD")) {
                    newLn.setTag(tempIdx);
                } else {
                    newLn.setTag(tempBillPaidIdx);
                }
                newLn.setPadding(15, 15, 15, 15);

                String finalTempPayType = tempPayType;
                newLn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectedSalonSalesCardIdx == v.getTag().toString()) {
                            mSelectedLn = null;
                            mSelectedSalonSalesCardIdx = "";
                            mSelectedPayType = "";
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                        } else {
                            // 앞서 선택한 LinearLayout 객체의 배경색을 초기화
                            if (mSelectedLn != null) {
                                mSelectedLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                            }
                            mSelectedLn = (LinearLayout) v;
                            mSelectedSalonSalesCardIdx = v.getTag().toString();
                            mSelectedPayType = finalTempPayType;
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist_select);
                        }
                    }
                });
                if (tempPayType.equals("CARD")) {
                    // 이미지뷰 객체 생성 및 붙이기 --------------------------------------------------
                    int tempCardImgId = R.drawable.aa_images_cardimg_visa;     // 결제된 카드의 종류 이미지 주소 (기본값을 Visa 로 한다.)
                    ImageView cardComImage = new ImageView(this);
                    switch (tempCardCom) {
                        case "V" : {
                            tempCardImgId = R.drawable.aa_images_cardimg_visa;
                            break;
                        }
                        case "M" : {
                            tempCardImgId = R.drawable.aa_images_cardimg_master;
                            break;
                        }
                        case "A" : {
                            tempCardImgId = R.drawable.aa_images_cardimg_amex;
                            break;
                        }
                        case "D" : {
                            tempCardImgId = R.drawable.aa_images_cardimg_discover;
                            break;
                        }
                    }
                    cardComImage.setImageResource(tempCardImgId);
                    cardComImage.setMaxWidth(50);
                    newLn.addView(cardComImage);
                    // -------------------------------------------------------------------------------

                    // 카드번호 -----------------------------------------------------------------------
                    tempCardLastFourDigitNumbers = "**** **** **** " + tempCardLastFourDigitNumbers;
                    TextView cardNumberTextView = new TextView(this);
                    cardNumberTextView.setLayoutParams(subNewTvLayoutParams);
                    cardNumberTextView.setGravity(Gravity.CENTER);
                    cardNumberTextView.setPadding(0, 0, 0, 0);
                    cardNumberTextView.setText(tempCardLastFourDigitNumbers);
                    cardNumberTextView.setTextSize(18);
                    cardNumberTextView.setTextColor(Color.parseColor("#505050"));
                    newLn.addView(cardNumberTextView);
                    // -------------------------------------------------------------------------------
                } else {
                    // 카드번호 -----------------------------------------------------------------------
                    TextView cardNumberTextView = new TextView(this);
                    cardNumberTextView.setLayoutParams(subNewTvLayoutParams);
                    cardNumberTextView.setGravity(Gravity.CENTER);
                    cardNumberTextView.setPadding(0, 0, 0, 0);
                    cardNumberTextView.setText("CASH SALE");
                    cardNumberTextView.setTextSize(36);
                    cardNumberTextView.setTypeface(typeface_monserrat_bold);
                    cardNumberTextView.setTextColor(Color.parseColor("#505050"));
                    newLn.addView(cardNumberTextView);
                    // -------------------------------------------------------------------------------
                }

                // Sale 결제금액 -----------------------------------------------------------------------
                TextView saleAmountTextView = new TextView(this);
                saleAmountTextView.setLayoutParams(subNewTvLayoutParams);
                saleAmountTextView.setGravity(Gravity.CENTER);
                saleAmountTextView.setPadding(0, 0, 0, 0);
                saleAmountTextView.setText("SALE : $" + GlobalMemberValues.getStringFormatNumber(
                        GlobalMemberValues.getDoubleAtString(tempPriceAmount), "2"));
                saleAmountTextView.setTextSize(16);
                saleAmountTextView.setTextColor(Color.parseColor("#505050"));
                newLn.addView(saleAmountTextView);
                // -------------------------------------------------------------------------------

                if (tempPayType.equals("CARD")) {
                    // Tip 결제금액 -----------------------------------------------------------------------
                    TextView tipAmountTextView = new TextView(this);
                    tipAmountTextView.setLayoutParams(subNewTvLayoutParams);
                    tipAmountTextView.setGravity(Gravity.CENTER);
                    tipAmountTextView.setPadding(0, 0, 0, 0);
                    tipAmountTextView.setText("TIP : $" + GlobalMemberValues.getStringFormatNumber(
                            GlobalMemberValues.getDoubleAtString(tempTipAmount), "2"));
                    tipAmountTextView.setTextSize(22);
                    tipAmountTextView.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR_CUSTOMER));
                    newLn.addView(tipAmountTextView);
                    // -------------------------------------------------------------------------------
                }


                tipCardListCardListLinearLayout.addView(newLn);
            }
        } catch (Exception e) {
        }

        //07052024 close resultset
        try {
            if(!tipCardListCursor.isClosed()){
                tipCardListCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /***********************************************************************************************************/
    }


    View.OnClickListener tipCardListBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tipCardListCloseBtn : {
                    if (SaleHistoryTipAdjustment.mPayType.equals("CASH")) {
                        SaleHistoryTipAdjustment.saleHistoryTipAdjustmentCashRadioButton.setChecked(true);
                        SaleHistoryTipAdjustment.mPayType = "CASH";
                    }
                    finish();
                    break;
                }
                case R.id.tipCardListSelectSubmitButton : {
                    if (mSelectedPayType.equals("CARD")) {
                        chooseCardForVoid();
                    } else {
                        chooseCashForVoid();
                    }

                    break;
                }
            }
        }
    };

    public void chooseCashForVoid() {
        String returnResult = "";

        String tempBillPaidIdx = mSelectedSalonSalesCardIdx;
        String tempBillIdx = MssqlDatabase.getResultSetValueToString("select billidx from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");
        String tempHoldCode = MssqlDatabase.getResultSetValueToString("select holdcode from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");

        String tempSalesCode = MssqlDatabase.getResultSetValueToString("select salesCode from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");

        Vector<String> strUpdateQueryVec = new Vector<String>();
        String strDeleteQuery = "";

        strDeleteQuery = "delete from bill_list_paid where idx = '" + tempBillPaidIdx + "' ";
        strUpdateQueryVec.addElement(strDeleteQuery);

        // 트랜잭션으로 DB 처리한다.
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
        if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
            if (context != null) {
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            }
        } else {
            if (getBillListPaidCntAfterAllVoidCheck() > 0) {
                if (SaleHistory.mActivity != null) {
                    SaleHistory.mActivity.recreate();
                }
                if (VoidCardList.mActivity != null) {
                    VoidCardList.mActivity.recreate();
                }
            }

        }
    }


    public void chooseCardForVoid() {
        if (!GlobalMemberValues.isStrEmpty(this.mSelectedSalonSalesCardIdx)) {
            String strQuery = " select cardCom, tipAmount, cardLastFourDigitNumbers, " +
                    " cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr, idx, priceAmount " +
                    " from salon_sales_card " +
                    " where idx = '" + this.mSelectedSalonSalesCardIdx + "' ";
            //Cursor salonSalesCardCursor = dbInit.dbExecuteRead(strQuery);
            ResultSet salonSalesCardCursor = MssqlDatabase.getResultSetValue(strQuery);
            GlobalMemberValues.logWrite("salescardlogjjj", "strQuery : " + strQuery + "\n");
            try {
                while (salonSalesCardCursor.next()) {
                    String tempCardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 0), 1);
                    String tempTipAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 1), 1);
                    String tempCardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 2), 1);
                    String tempCardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 3), 1);
                    String tempCardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 4), 1);
                    String tempCardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 5), 1);
                    String tempCardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 6), 1);
                    String tempCardIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 7), 1);
                    String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor, 8), 1);


                    if (GlobalMemberValues.isStrEmpty(tempCardIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Index Error (Null)", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(tempCardRefNumber)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Reference Number Error (Null)", "Close");
                        return;
                    }

                    if (GlobalMemberValues.ISCHECK_BEFORE_CARDPAY) {
                        // 가장 먼저 인터넷 체크를 한다.
                        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                            if (!GlobalMemberValues.isOnline2().equals("00")) {
                                GlobalMemberValues.showDialogNoInternet(context);
                                return;
                            }
                        } else {
                            if (!GlobalMemberValues.isOnline2().equals("00")) {
                                GlobalMemberValues.openNetworkNotConnected();
                                return;
                            }
                        }
                    }

                    // 먼저 전자서명이 있는지 체크후 있으면 삭제한다.
                    String tempAuthNum = MssqlDatabase.getResultSetValueToString(
                            "select tid from salon_sales_card where idx = '" + tempCardIdx + "' "
                    );
                    if (!GlobalMemberValues.isStrEmpty(tempAuthNum) && !GlobalMemberValues.isStrEmpty(tempCardRefNumber)) {
                        String strFilePath = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES) + "/sign_" + tempCardIdx + "_" + tempAuthNum + "_" + tempCardRefNumber + ".png";
                        if (!GlobalMemberValues.isStrEmpty(strFilePath)) {
                            if (new File(strFilePath).exists() == true) {
                                new File(strFilePath).delete();
                            }
                        }
                    }

                    GlobalMemberValues.mOnVoidForPartial = true;

                    GlobalMemberValues.logWrite("jjjbillvoidlogjjj2", "refnum : " + tempCardRefNumber + "\n");
                    GlobalMemberValues.logWrite("jjjbillvoidlogjjj2", "priceamountforvoid : " + tempPriceAmount + "\n");

                    Intent intent = new Intent(context, JJJ_PaxPay.class);
                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                    intent.putExtra("cardtendertype", "CREDIT");
                    intent.putExtra("processtype", "VOID");
                    intent.putExtra("amounttopay", "");
                    intent.putExtra("refnum", tempCardRefNumber);
                    intent.putExtra("ecrrefnum", mSalesCode);
                    intent.putExtra("salonsalescardidx", tempCardIdx);
                    intent.putExtra("priceamountforvoid", tempPriceAmount);
                    intent.putExtra("selectedcardtagname", tempCardIdx);
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(intent);

                }
            } catch (Exception e) {
            }

            //07052024 close resultset
            try {
                if(!salonSalesCardCursor.isClosed()){
                    salonSalesCardCursor.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void setVoidCardOnPartial(String paramResultCode, String paramSalonSalesCardIdx, String paramPriceAmount) {
        GlobalMemberValues.mOnVoidForPartial = false;

        if (paramResultCode.equals("00") || paramResultCode == "00") {
            String returnResult = "";

            String tempBillPaidIdx = MssqlDatabase.getResultSetValueToString("select idx from bill_list_paid where cardsalesidx = '" + paramSalonSalesCardIdx + "' ");
            String tempBillIdx = MssqlDatabase.getResultSetValueToString("select billidx from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");
            String tempHoldCode = MssqlDatabase.getResultSetValueToString("select holdcode from bill_list_paid where idx = '" + tempBillPaidIdx + "' ");

            String tempSalesCode = MssqlDatabase.getResultSetValueToString("select salesCode from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ");

            Vector<String> strUpdateQueryVec = new Vector<String>();
            String strDeleteQuery = "";

            strUpdateQueryVec = GlobalMemberValues.getVectorForVoid(paramSalonSalesCardIdx, tempBillPaidIdx, tempBillIdx);

//            strDeleteQuery = "delete from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ";
//            strUpdateQueryVec.addElement(strDeleteQuery);
//
//            strDeleteQuery = "delete from bill_list_paid where idx = '" + tempBillPaidIdx + "' ";
//            strUpdateQueryVec.addElement(strDeleteQuery);
//
//            strDeleteQuery = "update bill_list set paidyn = 'N' where idx = '" + tempBillIdx + "' ";
//            strUpdateQueryVec.addElement(strDeleteQuery);

            if (JJJ_SignPad.mVoidOnSignPad == "N" || JJJ_SignPad.mVoidOnSignPad.equals("N")) {
                strDeleteQuery = "delete from salon_sales_tip where salesCode = '" + tempSalesCode + "' ";
                strUpdateQueryVec.addElement(strDeleteQuery);
                JJJ_SignPad.mVoidOnSignPad = "N";
            }
            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "delete query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                if (context != null) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
                }
            } else {                                                // DB (salon_sales_card 테이블) 입력성공
                // 092022
                GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);

                if (getBillListPaidCntAfterAllVoidCheck() > 0) {
                    if (SaleHistory.mActivity != null) {
                        SaleHistory.mActivity.recreate();
                    }
                    if (VoidCardList.mActivity != null) {
                        VoidCardList.mActivity.recreate();
                    }
                }
            }
        } else {
            if (context != null) {
                GlobalMemberValues.displayDialog(context, "Warning", "Void processing error", "Close");
            }
        }
    }

    // 06.07.2022 ------------------------------------------------------------------------------------------
    public static int getBillListPaidCntAfterAllVoidCheck() {
        int returnValue = 0;
        // bill_list_paid 에 저장된 데이터가 없으면 SaleHistory.startVoidProcess() 실행후에
        // 해당 창을 닫는다
        returnValue = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(*) from bill_list_paid where salesCode = '" + mSalesCode + "' "
        ));

        if (returnValue == 0) {
            SaleHistory.setVoidSalesDatabaseProcess();

            if (mActivity != null) {
                mActivity.finish();
            }
        }

        return returnValue;
    }
    // -----------------------------------------------------------------------------------------------------

}
