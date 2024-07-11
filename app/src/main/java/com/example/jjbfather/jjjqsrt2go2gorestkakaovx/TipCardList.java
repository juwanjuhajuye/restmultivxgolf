package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipCardList extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;

    private LinearLayout tipCardListCardListLinearLayout;
    private Button tipCardListSelectSubmitButton;

    Intent mIntent;

    String mSalesCode = "";

    public String mSelectedSalonSalesCardIdx = "";
    public LinearLayout mSelectedLn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_tip_card_list);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 90) * 25;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 6;
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

        String salonSalesDetailQuery = "select idx, tid, cardCom, priceAmount, tipAmount, insertSwipeKeyin, " +
                " cardLastFourDigitNumbers, cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr, split_transaction_id " +
                " from salon_sales_card " +
                " where salesCode = '" + mSalesCode + "' and status < 1 ";

        tipCardListCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
        try {
            while (tipCardListCursor.next()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 0), 0);
                String tempTid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 1), 0);
                String tempCardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 2), 0);
                String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 3), 0);
                String tempTipAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 4), 0);
                String tempInsertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 5), 0);
                String tempCardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 6), 0);
                String tempCardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 7), 0);
                String tempCardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 8), 0);
                String tempCardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 9), 0);
                String tempCardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 10), 0);
                String tempSplit_transaction_id = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tipCardListCursor, 11), 0);

                // LinearLayout 객체 생성
                LinearLayout newLn = new LinearLayout(this);
                newLn.setLayoutParams(newLnLayoutParams);
                newLn.setOrientation(LinearLayout.VERTICAL);
                newLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                newLn.setTag(tempSplit_transaction_id);
                newLn.setPadding(15, 15, 15, 15);

                newLn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectedSalonSalesCardIdx == v.getTag().toString()) {
                            mSelectedLn = null;
                            mSelectedSalonSalesCardIdx = "";
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                        } else {
                            // 앞서 선택한 LinearLayout 객체의 배경색을 초기화
                            if (mSelectedLn != null) {
                                mSelectedLn.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist);
                            }
                            mSelectedLn = (LinearLayout) v;
                            mSelectedSalonSalesCardIdx = v.getTag().toString();
                            v.setBackgroundResource(R.drawable.roundlayout_background_paymentcreditcard_salonsalescardlist_select);
                        }
                    }
                });

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
                    // 이곳에 처리할 함수 넣기..
                    chooseCardForTip();
                    break;
                }
            }
        }
    };


    public void chooseCardForTip() {
        if (!GlobalMemberValues.isStrEmpty(this.mSelectedSalonSalesCardIdx)) {
            String strQuery = " select cardCom, tipAmount, cardLastFourDigitNumbers, " +
                    " cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr" +
                    " from salon_sales_card " +
                    " where split_transaction_id = '" + this.mSelectedSalonSalesCardIdx + "' ";
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

                    if (!GlobalMemberValues.isStrEmpty(tempCardRefNumber)) {
//                        String tempTipAmount = MssqlDatabase.getResultSetValueToString(
//                                " select tipamount from salon_sales_tip_split where cardsalesidx = '" + this.mSelectedSalonSalesCardIdx + "' "
//                        );

                        SaleHistoryTipAdjustment.mSelectedSalonSalesCard_Split_transaction_id  = this.mSelectedSalonSalesCardIdx;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardCom = tempCardCom;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardTipAmount = tempTipAmount;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardLastFourDigitNumbers = tempCardLastFourDigitNumbers;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardRefNumber = tempCardRefNumber;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvAid = tempCardEmvAid;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvTsi = tempCardEmvTsi;
                        SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvTvr = tempCardEmvTvr;

                        SaleHistoryTipAdjustment.saleHistoryTipAdjustmentTipAmountEditText.setText("0.0");
                        if (GlobalMemberValues.getDoubleAtString(tempTipAmount) > 0) {
                            SaleHistoryTipAdjustment.saleHistoryTipAdjustmentTipAmountEditText.setText(
                                    GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(tempTipAmount), "2")
                            );
                        }

                        String tempSuValue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(tempTipAmount), "2");
                        SaleHistoryTipAdjustment.mTempPriceValue
                                = GlobalMemberValues.getReplaceText(tempSuValue, ".", "");

                        SaleHistoryTipAdjustment.saleHistoryTipAdjustmentCardNumberTextView.setText(
                                "**** **** **** " + tempCardLastFourDigitNumbers
                        );

                        // salonSalesTipidx 값 구하기
                        String tempSalonSalesTipIdx = MssqlDatabase.getResultSetValueToString(
                                " select idx from salon_sales_tip where salesCode = '" + SaleHistoryTipAdjustment.mSalesCode+ "' " +
                                        " and employeeIdx = '" + SaleHistoryTipAdjustment.mEmpIdx + "' " +
                                        " and usedCard = '" + tempTipAmount + "' "
                        );
                        if (!GlobalMemberValues.isStrEmpty(tempSalonSalesTipIdx)) {
                            SaleHistoryTipAdjustment.salonSalesTipidx = tempSalonSalesTipIdx;
                        }

                        GlobalMemberValues.logWrite("selectedCardCom", "선택카드사 : " + tempCardCom + "\n");

                        int tempCardImgId = R.drawable.aa_images_cardimg_visa;
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
                        SaleHistoryTipAdjustment.saleHistoryTipAdjustmentCardImageTextView.setBackgroundResource(tempCardImgId);
                        finish();
                    }


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
}
