package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CashInOutPopupPreviousListAdmin extends Activity {
    final String TAG = "previousListAdminLog";

    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    static DatabaseInit dbInit;

    Intent mIntent;

    private LinearLayout parentLn;

    private LinearLayout previousListScrollLn;

    private Button previousListCloseBtn, previousListPrintBtn;

    LinearLayout mSelectedLn;

    JSONObject mGetJson = null;
    String mSelectedEmployeeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out_popup_previous_list_admin);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 75;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 75;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.previousListParentLn);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            //mCustomerValue = mIntent.getStringExtra("CustomerValue");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        try {
            setContents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setContents() throws JSONException {
        mActivity = this;
        mContext = this;
        dbInit = new DatabaseInit(this);

        previousListScrollLn = (LinearLayout)findViewById(R.id.previousListScrollLn);

        /** 닫기버튼 객체 생성 및 클릭 리스너 연결 *********************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        previousListCloseBtn = (Button)findViewById(R.id.previousListCloseBtn);
        previousListCloseBtn.setOnClickListener(previousListButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            previousListCloseBtn.setText("");
            previousListCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            previousListCloseBtn.setTextSize(
                    previousListCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        /** 프린트 버튼 객체 생성 및 클릭 리스너 연결 ******************************************************************/
        previousListPrintBtn = (Button)findViewById(R.id.previousListPrintBtn);
        previousListPrintBtn.setOnClickListener(previousListButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            previousListPrintBtn.setText("");
            previousListPrintBtn.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_print);
        } else {
            previousListPrintBtn.setTextSize(
                    previousListPrintBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        viewPreviousList();
    }

    private void viewPreviousList() throws JSONException {
        // -------------------------------------------------------------------------------------
        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        previousListScrollLn.setVisibility(View.VISIBLE);
        previousListScrollLn.setAnimation(animation1);
        // -------------------------------------------------------------------------------------

        previousListScrollLn.removeAllViews();

        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 2, 0, 2);

        // ------------------------------------------------------------------------------------------------------------
        // LinearLayout 객체 생성
        LinearLayout newTitleLn = new LinearLayout(this);
        newTitleLn.setLayoutParams(newLnLayoutParams);
        newTitleLn.setOrientation(LinearLayout.HORIZONTAL);
        newTitleLn.setPadding(0, 5, 0, 5);
        newTitleLn.setBackgroundColor(Color.parseColor("#85838b"));

        // Cashout Number
        TextView cashoutNumTitleTv = new TextView(this);
        cashoutNumTitleTv.setText("No.");
        cashoutNumTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        cashoutNumTitleTv.setTextSize(12.0f);
        cashoutNumTitleTv.setTextColor(Color.parseColor("#3e3d42"));
        cashoutNumTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        cashoutNumTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
        newTitleLn.addView(cashoutNumTitleTv);

        // All Cash Total
        TextView allCashTotalTitleTv = new TextView(this);
        allCashTotalTitleTv.setText("Cash Total");
        allCashTotalTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        allCashTotalTitleTv.setTextSize(12.0f);
        allCashTotalTitleTv.setTextColor(Color.parseColor("#3e3d42"));
        allCashTotalTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        allCashTotalTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
        newTitleLn.addView(allCashTotalTitleTv);

        // Cashout Date
        TextView cashoutDateTitleTip = new TextView(this);
        cashoutDateTitleTip.setText("Date");
        cashoutDateTitleTip.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        cashoutDateTitleTip.setTextSize(12.0f);
        cashoutDateTitleTip.setTextColor(Color.parseColor("#3e3d42"));
        cashoutDateTitleTip.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        cashoutDateTitleTip.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 6.0f));
        newTitleLn.addView(cashoutDateTitleTip);

        previousListScrollLn.addView(newTitleLn);
        // ------------------------------------------------------------------------------------------------------------

        JSONObject jsonStrObj = null;

        String strCashoutNum = "";
        String strCashIn = "";
        String strSalesCash = "";
        String strAllCashTotal = "";
        String strCashoutDate = "";

        String strEmployeeName = "";

        String jsonStr = "";

        Cursor cashoutListCursor;
        String strQuery = "";

        strQuery = "select cashoutNum, jsonstr, employeeName from salon_sales_cashout_json " +
                " where " +
                " cashoutnum > 0 " +
                " and delyn = 'N' " +
                " order by idx desc limit 100 ";
        GlobalMemberValues.logWrite(TAG, "query : " + strQuery + "\n");
        cashoutListCursor = dbInit.dbExecuteRead(strQuery);
        while (cashoutListCursor.moveToNext()) {
            strCashoutNum = GlobalMemberValues.getDBTextAfterChecked(cashoutListCursor.getString(0), 1);
            jsonStr = GlobalMemberValues.getDBTextAfterChecked(cashoutListCursor.getString(1), 1);
            strEmployeeName = GlobalMemberValues.getDBTextAfterChecked(cashoutListCursor.getString(2), 1);
            GlobalMemberValues.logWrite(TAG, "jsonStr : " + jsonStr + "\n");
            if (!GlobalMemberValues.isStrEmpty(jsonStr)) {
                jsonStrObj = new JSONObject(jsonStr);
                if (jsonStrObj.toString().contains("salesoverview_cashoutdate")){
                    strAllCashTotal = jsonStrObj.getString("salesbytendertypes_cashtotalamount").toString();
                    strCashoutDate = jsonStrObj.getString("salesoverview_cashoutdate").toString();

                    // LinearLayout 객체 생성
                    LinearLayout newLn = new LinearLayout(this);
                    newLn.setLayoutParams(newLnLayoutParams);
                    newLn.setOrientation(LinearLayout.HORIZONTAL);
                    newLn.setTag(jsonStr);
                    newLn.setPadding(0, 8, 0, 8);
                    newLn.setBackgroundResource(R.drawable.roundlayout_background_batchedlist);


                    // Employee
                    TextView cashoutNumTv = new TextView(this);
                    cashoutNumTv.setText(strEmployeeName);
                    cashoutNumTv.setTag(jsonStr);
                    cashoutNumTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    cashoutNumTv.setTextSize(12.0f);
                    cashoutNumTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    cashoutNumTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                    newLn.addView(cashoutNumTv);

                    // All Cash Total
                    TextView allCashTotalTv = new TextView(this);
                    allCashTotalTv.setText(strAllCashTotal);
                    allCashTotalTv.setTag(jsonStr);
                    allCashTotalTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    allCashTotalTv.setTextSize(12.0f);
                    allCashTotalTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    allCashTotalTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                    newLn.addView(allCashTotalTv);

                    // Cashout Date
                    TextView cashoutDateTv = new TextView(this);
                    cashoutDateTv.setText(strCashoutDate);
                    cashoutDateTv.setTag(jsonStr);
                    cashoutDateTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    cashoutDateTv.setTextSize(12.0f);
                    cashoutDateTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    cashoutDateTv.setPadding(0, 0, 10, 0);
                    cashoutDateTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 6.0f));
                    newLn.addView(cashoutDateTv);


                    final String finalStrEmployeeName = strEmployeeName;
                    newLn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mSelectedLn != null) {
                                mSelectedLn.setBackgroundResource(R.drawable.roundlayout_background_batchedlist);
                            }
                            mSelectedLn = (LinearLayout)v;
                            v.setBackgroundColor(Color.parseColor("#fac7f2"));
                            try {
                                mSelectedEmployeeName = finalStrEmployeeName;
                                mGetJson = new JSONObject(v.getTag().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    previousListScrollLn.addView(newLn);
                }
            }
        }
    }

    private void printSelectedCashOut() {
        new android.app.AlertDialog.Builder(mContext)
                .setTitle("Print")
                .setMessage("Print the selected?")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    mGetJson.put("cashinoutemployeenameyn", "Y");
                                    mGetJson.put("employeename", mSelectedEmployeeName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                GlobalMemberValues.printReceiptByJHP(mGetJson, mContext, "cashout");
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                .show();
    }

    View.OnClickListener previousListButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.previousListCloseBtn : {
                    finish();
                    break;
                }
                case R.id.previousListPrintBtn : {
                    if (mGetJson != null) {
                        printSelectedCashOut();
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Select a line to print", "Close");
                    }

                    break;
                }

            }
        }
    };
}
