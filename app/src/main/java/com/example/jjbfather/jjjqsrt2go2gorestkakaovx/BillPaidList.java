package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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

import org.json.JSONObject;

import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;

public class BillPaidList extends Activity {
    public static Activity mActivity;
    public static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;

    private LinearLayout billPaidListCardListLinearLayout;

    Intent mIntent;

    static String mSalesCode = "";

    public String mSelectedSalonSalesCardIdx = "";
    public String mSelectedPayType = "CARD";
    public LinearLayout mSelectedLn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_bill_paid_list);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 90) * 30;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.billPaidListLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mSalesCode = mIntent.getStringExtra("salesCode");
            GlobalMemberValues.logWrite("billPaidList", "넘겨받은 SalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        GlobalMemberValues.logWrite("billPaidListLog", "billPaidList 실행 - 3" + "\n");

        setContents();
    }

    public void setContents() {
        mActivity = this;
        context = this;

        dbInit = new DatabaseInit(this);

        GlobalMemberValues.mOnVoidForBillPay = false;

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("billPaidListCloseBtnTag");
        closeBtn.setOnClickListener(billPaidListBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_close);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        billPaidListCardListLinearLayout = (LinearLayout)findViewById(R.id.billPaidListCardListLinearLayout);

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

        //Cursor billPaidListCursor;
        ResultSet billPaidListCursor;

        String salonSalesDetailQuery = "select B.idx, B.tid, B.cardCom, B.priceAmount, B.tipAmount, B.insertSwipeKeyin, " +
                " B.cardLastFourDigitNumbers, B.cardRefNumber, B.cardEmvAid, B.cardEmvTsi, B.cardEmvTvr, A.paytype, A.paidamount, A.idx, A.billcode," +
                // 07202023
                " A.billidx " +
                " from bill_list_paid A left join salon_sales_card B on A.cardsalesidx = B.idx  " +
                " where A.salesCode = '" + mSalesCode + "' ";

        billPaidListCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
        try {
            while (billPaidListCursor.next()) {
                String tempIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 0), 0);
                String tempTid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 1), 0);
                String tempCardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 2), 0);
                //String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 3), 0);
                String tempTipAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 4), 0);
                String tempInsertSwipeKeyin = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 5), 0);
                String tempCardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 6), 0);
                String tempCardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 7), 0);
                String tempCardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 8), 0);
                String tempCardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 9), 0);
                String tempCardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 10), 0);

                String tempPayType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 11), 0);
                if (GlobalMemberValues.isStrEmpty(tempPayType)) {
                    tempPayType = "CARD";
                }

                String tempPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 12), 0);

                String tempBillPaidIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 13), 0);

                String tempBillCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 14), 0);

                // 07202023
                String tempBillidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(billPaidListCursor, 15), 0);

                String jsonRootStr = "";
                if (!GlobalMemberValues.isStrEmpty(tempBillCode)) {
                    jsonRootStr = MssqlDatabase.getResultSetValueToString(
                            " select jsonstr from bill_list_receipt_json where billcode = '" + tempBillCode + "' "
                    );
                }

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

                // 01102023 -------------------------------------------------------------------------------------------------------------
                JSONObject jsonroot = new JSONObject(jsonRootStr);
                jsonroot.put("reprintyn", "Y");

                // 01102023
                // 해당 결제에 팁이 있는지 확인
                String tipamount = MssqlDatabase.getResultSetValueToString(
                        " select tipamount from salon_sales_tip_split where billpaididx = '" + tempBillPaidIdx + "' "
                );
                if (GlobalMemberValues.getDoubleAtString(tipamount) > 0) {
                    jsonroot.put("tipamount", tipamount);
                } else {
                    // 결제할 때 지불한 tip 이 있는지 확인한다.
                    tipamount = MssqlDatabase.getResultSetValueToString(
                            " select tipamount from salon_sales_card where idx = '" + tempIdx + "' "
                    );
                    if (GlobalMemberValues.getDoubleAtString(tipamount) > 0) {
                        jsonroot.put("tipamount", tipamount);
                    }
                }

                GlobalMemberValues.logWrite("tipamountjjjlog", " select tipamount from salon_sales_tip_split where billpaididx = '" + tempBillPaidIdx + "' " + "\n");
                GlobalMemberValues.logWrite("tipamountjjjlog", "tipamount : " + tipamount + "\n");
                String finalJsonRootStr = jsonroot.toString();
                // ----------------------------------------------------------------------------------------------------------------------


                newLn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openReceiptView(finalJsonRootStr);
                    }
                });


                // 07202023
                // split pay no. (billidx) --------------------------------------------------------
                TextView splitpaynumTextView = new TextView(this);
                splitpaynumTextView.setLayoutParams(subNewTvLayoutParams);
                splitpaynumTextView.setGravity(Gravity.CENTER);
                splitpaynumTextView.setPadding(0, 0, 0, 0);
                splitpaynumTextView.setText("Split Pay No. " + tempBillidx);
                splitpaynumTextView.setTextSize(18);
                splitpaynumTextView.setTextColor(Color.parseColor("#ffffff"));
                splitpaynumTextView.setBackgroundColor(Color.parseColor("#d6c561"));
                newLn.addView(splitpaynumTextView);
                // -------------------------------------------------------------------------------


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


                billPaidListCardListLinearLayout.addView(newLn);
            }
            //07052024 close resultset
            billPaidListCursor.close();
        } catch (Exception e) {
        }

        /***********************************************************************************************************/
    }

    public void openReceiptView(String paramJson) {
        Intent salehistoryReprintIntent = new Intent(context.getApplicationContext(), SaleHistory_Reprint.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        salehistoryReprintIntent.putExtra("JsonReceipt", paramJson.toString());
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(salehistoryReprintIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    View.OnClickListener billPaidListBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.billPaidListCloseBtn : {
                    finish();
                    break;
                }
            }
        }
    };

}
