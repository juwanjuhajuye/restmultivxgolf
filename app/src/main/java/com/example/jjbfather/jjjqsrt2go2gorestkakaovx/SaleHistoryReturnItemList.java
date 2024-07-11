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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleHistoryReturnItemList extends Activity {
    static Activity mActivity;
    Context context;
    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    Intent mIntent;

    // salesCode 값
    String mSalesCode = "";
    ArrayList<String> mSelectedItemIdxArrayList;
    ArrayList<String> mSelectedTipIdxArrayList;

    Button saleHistoryReturnItemSubmitButton, saleHistoryReturnItemCloseButton;

    LinearLayout saleHistoryReturnItemListSaleListLinearLayout;
    TextView saleHistoryReturnItemListBalanceTotalAmount;

    TextView saleHistoryReturnItemTitleTextView;

    TextView saleHistoryReturnItemListTitleTextView1, saleHistoryReturnItemListTitleTextView2, saleHistoryReturnItemListTitleTextView3;
    TextView saleHistoryReturnItemTotalAmountTextView;

    double mTotalAmount = 0.0;
    double mTotalTipAmount = 0.0;
    double mPickupDeliveryFee = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_return_item_list);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.saleHistoryReturnItemListLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        context = this;
        mActivity = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 salesCode 값
            mSalesCode = mIntent.getStringExtra("selectedSalesCode");
            GlobalMemberValues.logWrite("salesHistoryReturn", "넘겨받은 selectedSalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
    }

    public void setContents() {
        saleHistoryReturnItemTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnItemTitleTextView);
        saleHistoryReturnItemTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnItemTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnItemListTitleTextView1 = (TextView)findViewById(R.id.saleHistoryReturnItemListTitleTextView1);
        saleHistoryReturnItemListTitleTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnItemListTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnItemListTitleTextView2 = (TextView)findViewById(R.id.saleHistoryReturnItemListTitleTextView2);
        saleHistoryReturnItemListTitleTextView2.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnItemListTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnItemListTitleTextView3 = (TextView)findViewById(R.id.saleHistoryReturnItemListTitleTextView3);
        saleHistoryReturnItemListTitleTextView3.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnItemListTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnItemTotalAmountTextView = (TextView)findViewById(R.id.saleHistoryReturnItemTotalAmountTextView);
        saleHistoryReturnItemTotalAmountTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnItemTotalAmountTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnItemSubmitButton = (Button)findViewById(R.id.saleHistoryReturnItemSubmitButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnItemSubmitButton.setText("");
            saleHistoryReturnItemSubmitButton.setBackgroundResource(R.drawable.ab_imagebutton_returnitemlist_enter);
        } else {
            saleHistoryReturnItemSubmitButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnItemSubmitButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnItemCloseButton = (Button)findViewById(R.id.saleHistoryReturnItemCloseButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnItemCloseButton.setText("");
            saleHistoryReturnItemCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_returnitemlist_close);
        } else {
            saleHistoryReturnItemCloseButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnItemCloseButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryReturnItemSubmitButton.setOnClickListener(saleHistoryItemListBtnClick);
        saleHistoryReturnItemCloseButton.setOnClickListener(saleHistoryItemListBtnClick);

        saleHistoryReturnItemListSaleListLinearLayout = (LinearLayout)findViewById(R.id.saleHistoryReturnItemListSaleListLinearLayout);
        saleHistoryReturnItemListBalanceTotalAmount = (TextView)findViewById(R.id.saleHistoryReturnItemListBalanceTotalAmount);

        mSelectedItemIdxArrayList = new ArrayList<String>();
        mSelectedTipIdxArrayList = new ArrayList<String>();

        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);


        // salon_sales_detail 데이터 출력 ---------------------------------------------------------------------------------------------------
        ResultSet salonSalesDetailCursor;
        String salonSalesDetailQuery = "";
        salonSalesDetailQuery = "select categoryName, itemIdx, itemName, qty, salesBalPriceAmount, taxAmount, totalAmount, pointAmount, " +
                " employeeIdx, employeeName, saveType, isQuickSale, status, idx " +
                " from salon_sales_detail " +
                " where delyn = 'N' " +
                " and salesCode = '" + mSalesCode + "' " +
                " order by employeeName asc, idx asc ";
        salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
        int listCount = 1;
        try {
            while (salonSalesDetailCursor.next()) {
                String strIdxAmount = GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13) + "||" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6);

                LinearLayout subNewLn = new LinearLayout(this);
                subNewLn.setLayoutParams(subNewLnLayoutParams);
                subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                subNewLn.setBackgroundColor(Color.parseColor("#ffffff"));

                // 체크박스
                CheckBox subNewCb1 = new CheckBox(this);
                subNewCb1.setLayoutParams(subNewTvLayoutParams);
                //subNewCb1.setWidth(GlobalMemberValues.getDisplayWidth(this) / 28);
                subNewCb1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewCb1.setTag(strIdxAmount);
                subNewCb1.setPadding(5, 5, 5, 5);
                subNewCb1.setHighlightColor(Color.parseColor("#000000"));
                if (GlobalMemberValues.resultDB_checkNull_int(salonSalesDetailCursor,12) == 1 || GlobalMemberValues.resultDB_checkNull_int(salonSalesDetailCursor,12) == 3 ) {
                    subNewCb1.setEnabled(false);
                    subNewCb1.setHighlightColor(Color.parseColor("#b8b8b8"));
                    //subNewCb1.setBackgroundColor(Color.parseColor("#a1a1a1"));
                } else {
                    subNewCb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String tempSalonSalesDetailStatus = buttonView.getTag().toString();
                            String tempSplitStr[] = null;
                            tempSplitStr = tempSalonSalesDetailStatus.split(GlobalMemberValues.STRSPLITTER1);
                            if (!GlobalMemberValues.isStrEmpty(tempSalonSalesDetailStatus)) {
                                if (isChecked) {
                                    // 먼저 void 처리할 세일즈에서 기프트카드가 있는지 확인하고, 사용한 이력이 있는 기프트카드이면 void 처리를 못하게 한다.
                                    if (!SaleHistory.checkUsedGiftCardInSales(tempSplitStr[0], "RETURN")) {
                                        GlobalMemberValues.displayDialog(context, "Waraning", "The gift card balance is less than the amount sold", "Close");
                                        buttonView.setChecked(false);
                                        return;
                                    }
                                    mSelectedItemIdxArrayList.add(tempSplitStr[0]);
                                    mTotalAmount += GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                } else {
                                    mSelectedItemIdxArrayList.remove(tempSplitStr[0]);
                                    mTotalAmount -= GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                }
                            }

                            //String tempMTotalAmount = mTotalAmount + "";
                            //tempMTotalAmount = GlobalMemberValues.getReplaceText(tempMTotalAmount, "-", "");
                            saleHistoryReturnItemListBalanceTotalAmount.setText(GlobalMemberValues.getStringFormatNumber(mTotalAmount, "2"));
                            saleHistoryReturnItemListBalanceTotalAmount.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
                        }
                    });
                }
                subNewLn.addView(subNewCb1);

                // 서비스(item) 이름
                TextView subNewTv2 = new TextView(this);
                subNewTv2.setLayoutParams(subNewTvLayoutParams);
                //subNewTv2.setWidth(GlobalMemberValues.getDisplayWidth(this) / 3);
                subNewTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 8.0f));
                subNewTv2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                subNewTv2.setPadding(0, 5, 5, 5);
                subNewTv2.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 1));
                subNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv2);

                // 수량
                TextView subNewTv3 = new TextView(this);
                subNewTv3.setLayoutParams(subNewTvLayoutParams);
                //subNewTv3.setWidth(GlobalMemberValues.getDisplayWidth(this) / 28);
                subNewTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewTv3.setGravity(Gravity.CENTER);
                subNewTv3.setPadding(5, 0, 5, 0);
                subNewTv3.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 1));
                subNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv3);

                // Amount (세금미포함)
                TextView subNewTv4 = new TextView(this);
                subNewTv4.setLayoutParams(subNewTvLayoutParams);
                //subNewTv4.setWidth(GlobalMemberValues.getDisplayWidth(this) / 16);
                subNewTv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.3f));
                subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                subNewTv4.setPadding(0, 0, 5, 0);
                subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble(
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 1)));
                subNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv4);

                saleHistoryReturnItemListSaleListLinearLayout.addView(subNewLn);

                listCount++;
            }
        } catch (Exception e) {

        }

        //07052024 close resultset
        try {
            if(!salonSalesDetailCursor.isClosed()){
                salonSalesDetailCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // ----------------------------------------------------------------------------------------------------------------------------------

        // pick up or delivery fee 데이터 출력 -----------------------------------------------------------------------------------------------
        ResultSet salonSalesFeeCursor;
        String salonSalesFeeQuery = "";
        salonSalesFeeQuery = "select deliverypickupfee " +
                " from salon_sales " +
                " where salesCode = '" + mSalesCode + "' ";
        salonSalesFeeCursor = MssqlDatabase.getResultSetValue(salonSalesFeeQuery);
        int feeListCount = 1;
        try {
            if (salonSalesFeeCursor.getRow() > 0 && salonSalesFeeCursor.first()) {
                String strIdxAmount = "" + "||" + GlobalMemberValues.resultDB_checkNull_double(salonSalesFeeCursor,0);

                LinearLayout subNewLn = new LinearLayout(this);
                subNewLn.setLayoutParams(subNewLnLayoutParams);
                subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                subNewLn.setBackgroundColor(Color.parseColor("#ffffff"));

                // 체크박스
                CheckBox subNewCb1 = new CheckBox(this);
                subNewCb1.setLayoutParams(subNewTvLayoutParams);
                //subNewCb1.setWidth(GlobalMemberValues.getDisplayWidth(this) / 28);
                subNewCb1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewCb1.setTag(strIdxAmount);
                subNewCb1.setPadding(5, 5, 5, 5);
                subNewCb1.setHighlightColor(Color.parseColor("#000000"));
                subNewCb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String tempSalonSalesDetailStatus = buttonView.getTag().toString();
                        String tempSplitStr[] = null;
                        tempSplitStr = tempSalonSalesDetailStatus.split(GlobalMemberValues.STRSPLITTER1);
                        if (!GlobalMemberValues.isStrEmpty(tempSalonSalesDetailStatus)) {
                            if (isChecked) {
                                mTotalAmount += GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                mPickupDeliveryFee = GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                            } else {
                                mTotalAmount -= GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                mPickupDeliveryFee = 0.0;
                            }
                        }
                        saleHistoryReturnItemListBalanceTotalAmount.setText(GlobalMemberValues.getStringFormatNumber(mTotalAmount, "2"));
                        saleHistoryReturnItemListBalanceTotalAmount.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
                    }
                });

                subNewLn.addView(subNewCb1);

                // pick up / delivery fee
                TextView subNewTv2 = new TextView(this);
                subNewTv2.setLayoutParams(subNewTvLayoutParams);
                //subNewTv2.setWidth(GlobalMemberValues.getDisplayWidth(this) / 3);
                subNewTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 8.0f));
                subNewTv2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                subNewTv2.setPadding(0, 5, 5, 5);
                subNewTv2.setText("Pickup or Delivery Fee");
                subNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv2.setTextColor(Color.parseColor("#6c9d2b"));
                subNewLn.addView(subNewTv2);

                // 수량
                TextView subNewTv3 = new TextView(this);
                subNewTv3.setLayoutParams(subNewTvLayoutParams);
                //subNewTv3.setWidth(GlobalMemberValues.getDisplayWidth(this) / 28);
                subNewTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewTv3.setGravity(Gravity.CENTER);
                subNewTv3.setPadding(5, 0, 5, 0);
                subNewTv3.setText("-");
                subNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv3);

                // Amount (세금미포함)
                TextView subNewTv4 = new TextView(this);
                subNewTv4.setLayoutParams(subNewTvLayoutParams);
                //subNewTv4.setWidth(GlobalMemberValues.getDisplayWidth(this) / 16);
                subNewTv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.3f));
                subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                subNewTv4.setPadding(0, 0, 5, 0);
                subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble(
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesFeeCursor,0), 1)));
                subNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv4);

                saleHistoryReturnItemListSaleListLinearLayout.addView(subNewLn);

                feeListCount++;
            }
        } catch (Exception e) {

        }

        //07052024 close resultset
        try {
            if(!salonSalesFeeCursor.isClosed()){
                salonSalesFeeCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // ----------------------------------------------------------------------------------------------------------------------------------

        // salon_sales_tip 데이터 출력 ---------------------------------------------------------------------------------------------------
        ResultSet salonSalesTipCursor;
        String salonSalesTipQuery = "";
        salonSalesTipQuery = "select idx, employeeName, (usedCash + usedCard) as tipAmount, cardCom " +
                " from salon_sales_tip " +
                " where salesCode = '" + mSalesCode + "' " +
                " order by wdate asc ";
        salonSalesTipCursor = MssqlDatabase.getResultSetValue(salonSalesTipQuery);
        int tipListCount = 1;
        try {
            while (salonSalesTipCursor.next()) {
                String strIdxAmount = GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,0) + "||" + GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,2);

                LinearLayout subNewLn = new LinearLayout(this);
                subNewLn.setLayoutParams(subNewLnLayoutParams);
                subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                subNewLn.setBackgroundColor(Color.parseColor("#ffffff"));

                // 체크박스
                CheckBox subNewCb1 = new CheckBox(this);
                subNewCb1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewCb1.setTag(strIdxAmount);
                subNewCb1.setPadding(5, 5, 5, 5);
                subNewCb1.setHighlightColor(Color.parseColor("#000000"));
                subNewCb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String tempSalonSalesDetailStatus = buttonView.getTag().toString();
                        String tempSplitStr[] = null;
                        tempSplitStr = tempSalonSalesDetailStatus.split(GlobalMemberValues.STRSPLITTER1);
                        if (!GlobalMemberValues.isStrEmpty(tempSalonSalesDetailStatus)) {
                            if (isChecked) {
                                mSelectedTipIdxArrayList.add(tempSplitStr[0]);
                                mTotalAmount += GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                mTotalTipAmount += GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                            } else {
                                mSelectedTipIdxArrayList.remove(tempSplitStr[0]);
                                mTotalAmount -= GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                                mTotalTipAmount -= GlobalMemberValues.getDoubleAtString(tempSplitStr[1]);
                            }
                        }
                        saleHistoryReturnItemListBalanceTotalAmount.setText(GlobalMemberValues.getStringFormatNumber(mTotalAmount, "2"));
                        saleHistoryReturnItemListBalanceTotalAmount.setTextSize(GlobalMemberValues.globalAddFontSize() +
                                saleHistoryReturnItemListBalanceTotalAmount.getTextSize() * GlobalMemberValues.getGlobalFontSize()
                        );
                    }
                });

                subNewLn.addView(subNewCb1);

                // 팁
                TextView subNewTv2 = new TextView(this);
                subNewTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 8.0f));
                subNewTv2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                subNewTv2.setPadding(0, 5, 5, 5);
                subNewTv2.setText("Tip Amount");
                subNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv2.setTextColor(Color.parseColor("#1760b9"));
                subNewLn.addView(subNewTv2);

                // 수량
                TextView subNewTv3 = new TextView(this);
                subNewTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                subNewTv3.setGravity(Gravity.CENTER);
                subNewTv3.setPadding(5, 0, 5, 0);
                subNewTv3.setText("-");
                subNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv3);

                // Amount (세금미포함)
                TextView subNewTv4 = new TextView(this);
                subNewTv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.3f));
                subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                subNewTv4.setPadding(0, 0, 5, 0);
                subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble(
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,2), 1)));
                subNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
                subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                subNewLn.addView(subNewTv4);

                saleHistoryReturnItemListSaleListLinearLayout.addView(subNewLn);

                tipListCount++;
            }
        } catch (Exception e){

        }

        //07052024 close resultset
        try {
            if(!salonSalesTipCursor.isClosed()){
                salonSalesTipCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ----------------------------------------------------------------------------------------------------------------------------------
    }

    View.OnClickListener saleHistoryItemListBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saleHistoryReturnItemSubmitButton : {
                    //if ((mSelectedItemIdxArrayList != null && mSelectedItemIdxArrayList.size() > 0) || (mSelectedTipIdxArrayList != null && mSelectedTipIdxArrayList.size() > 0))
                    if (mSelectedItemIdxArrayList != null && mSelectedItemIdxArrayList.size() > 0) {
                        Intent saleHistoryReturnIntent = new Intent(getApplicationContext(), SaleHistoryReturn.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                        saleHistoryReturnIntent.putExtra("selectedSalesCode", mSalesCode);
                        saleHistoryReturnIntent.putExtra("selectedItemTotalAmount", mTotalAmount);
                        saleHistoryReturnIntent.putExtra("selectedItemIdxArrayList", mSelectedItemIdxArrayList);
                        saleHistoryReturnIntent.putExtra("selectedTipIdxArrayList", mSelectedTipIdxArrayList);
                        saleHistoryReturnIntent.putExtra("selectedTipAmount", mTotalTipAmount);
                        saleHistoryReturnIntent.putExtra("selectedPickupDeliveryFeeAmount", mPickupDeliveryFee);
                        // -------------------------------------------------------------------------------------
                        startActivity(saleHistoryReturnIntent);

                        // 초기화 후 자신의 창은 닫는다.
                        //setInit();
                        //finish();
                    } else {
                        GlobalMemberValues.displayDialog(context, "Waraning", "Choose item to return", "Close");
                        return;
                    }
                    break;
                }
                case R.id.saleHistoryReturnItemCloseButton : {
                    setInit();
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
            }

        }
    };

    // 초기화 메소드
    private void setInit() {
        mSalesCode = "";
        mSelectedItemIdxArrayList = null;
        mTotalAmount = 0.0;
    }

}
