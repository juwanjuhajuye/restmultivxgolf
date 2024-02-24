package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
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

public class BatchListDetail extends Activity {
    final String TAG = "BatchDetailLog";

    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    static DatabaseInit dbInit;

    Intent mIntent;

    private LinearLayout parentLn;
    private LinearLayout batchDetailLn, batchedListLn;
    private LinearLayout batchedListScrollLn;

    private Button batchDetailCloseBtn, batchDetailPrintBtn, batchedListBtn;
    private TextView batchNumberTv, batchTotalCardCountTv, batchTotalCardTipTv, batchTotalCardAmountTv;
    private TextView batchVisaAmountTv, batchMasterAmountTv, batchAmexAmountTv, batchDiscoverAmountTv, batchTipAmountTv;
    private TextView batchTimeTv, batchDetailTitleTv;

    private LinearLayout visaLn, masterLn, amexLn, discoverLn;

    JSONObject mGetJson = null;
    String mGetOpenBatchedList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_batch_detail);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.batchDetailParentLn);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mGetOpenBatchedList = mIntent.getStringExtra("openbatchedlist");
            try {
                if (!GlobalMemberValues.isStrEmpty(mIntent.getStringExtra("batchJson"))) {
                    mGetJson = new JSONObject(mIntent.getStringExtra("batchJson"));

                    GlobalMemberValues.logWrite("BatchDetailLog", "Json : " + mGetJson.toString() + "\n");
                } else {
                    mGetJson = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*******************************************************************************************/
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

        batchDetailLn = (LinearLayout)findViewById(R.id.batchDetailLn);
        batchedListLn = (LinearLayout)findViewById(R.id.batchedListLn);

        batchedListScrollLn = (LinearLayout)findViewById(R.id.batchedListScrollLn);

        batchNumberTv = (TextView)findViewById(R.id.batchNumberTv);
        batchNumberTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchNumberTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchTotalCardCountTv = (TextView)findViewById(R.id.batchTotalCardCountTv);
        batchTotalCardCountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchTotalCardCountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchTotalCardTipTv = (TextView)findViewById(R.id.batchTotalCardTipTv);
        batchTotalCardTipTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchTotalCardTipTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchTotalCardAmountTv = (TextView)findViewById(R.id.batchTotalCardAmountTv);
        batchTotalCardAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchTotalCardAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchVisaAmountTv = (TextView)findViewById(R.id.batchVisaAmountTv);
        batchVisaAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchVisaAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchMasterAmountTv = (TextView)findViewById(R.id.batchMasterAmountTv);
        batchMasterAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchMasterAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchAmexAmountTv = (TextView)findViewById(R.id.batchAmexAmountTv);
        batchAmexAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchAmexAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchDiscoverAmountTv = (TextView)findViewById(R.id.batchDiscoverAmountTv);
        batchDiscoverAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchDiscoverAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchTipAmountTv = (TextView)findViewById(R.id.batchTipAmountTv);
        batchTipAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchTipAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchTimeTv = (TextView)findViewById(R.id.batchTimeTv);
        batchTimeTv.setTextSize(GlobalMemberValues.globalAddFontSize() + batchTimeTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        batchDetailTitleTv = (TextView)findViewById(R.id.batchDetailTitleTv);
        batchDetailTitleTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        /** 닫기버튼 객체 생성 및 클릭 리스너 연결 *********************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        batchDetailCloseBtn = (Button)parentLn.findViewWithTag("batchDetailCloseBtnTag");
        batchDetailCloseBtn.setOnClickListener(batchDetailButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchDetailCloseBtn.setText("");
            batchDetailCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            batchDetailCloseBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    batchDetailCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        /** 프린트 버튼 객체 생성 및 클릭 리스너 연결 ******************************************************************/
        batchDetailPrintBtn = (Button)parentLn.findViewWithTag("batchDetailPrintBtnTag");
        batchDetailPrintBtn.setOnClickListener(batchDetailButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchDetailPrintBtn.setText("");
            batchDetailPrintBtn.setBackgroundResource(R.drawable.ab_imagebutton_batchsummary_print);
        } else {
            batchDetailPrintBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    batchDetailPrintBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        /***********************************************************************************************************/

        /** 프린트 버튼 객체 생성 및 클릭 리스너 연결 ******************************************************************/
        batchedListBtn = (Button)parentLn.findViewWithTag("batchedListBtnTag");
        batchedListBtn.setOnClickListener(batchDetailButtonClick);
        batchedListBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                batchedListBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        /***********************************************************************************************************/

        visaLn = (LinearLayout) findViewById(R.id.visaLn);
        masterLn = (LinearLayout) findViewById(R.id.masterLn);
        amexLn = (LinearLayout) findViewById(R.id.amexLn);
        discoverLn = (LinearLayout) findViewById(R.id.discoverLn);


        if (!GlobalMemberValues.isStrEmpty(mGetOpenBatchedList)
                && mGetOpenBatchedList.equals("Y")) {
            viewBatchedList();
        } else {
            viewBatchDetail();
        }
    }

    public void viewBatchDetail() throws JSONException {
        // -------------------------------------------------------------------------------------
        batchedListLn.setVisibility(View.GONE);

        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        batchDetailLn.setVisibility(View.VISIBLE);
        batchDetailLn.setAnimation(animation1);

        batchDetailPrintBtn.setVisibility(View.VISIBLE);
        // -------------------------------------------------------------------------------------

        String getBatchNumber = "";
        String getTotalCardCount = "";
        String getTotalCardAmount = "";
        String getTotalTipAmount = "";
        String getVisaAmount = "";
        String getMasterAmount = "";
        String getAmexAmount = "";
        String getDiscoverAmount = "";
        String getBatchDate = "";

        if (mGetJson != null && !GlobalMemberValues.isStrEmpty(mGetJson.toString())) {
            if (mGetJson.toString().contains("BatchNumber")){
                getBatchNumber = mGetJson.getString("BatchNumber").toString();
            }
            if (mGetJson.toString().contains("CreditCount")){
                getTotalCardCount = mGetJson.getString("CreditCount").toString();
            }
            if (mGetJson.toString().contains("CreditAmount")){
                getTotalCardAmount = mGetJson.getString("CreditAmount").toString();
            }
            if (mGetJson.toString().contains("totalcardtip")){
                getTotalTipAmount = mGetJson.getString("totalcardtip").toString();
            }
            if (mGetJson.toString().contains("visaamount")){
                getVisaAmount = mGetJson.getString("visaamount").toString();
            }
            if (mGetJson.toString().contains("masteramount")){
                getMasterAmount = mGetJson.getString("masteramount").toString();
            }
            if (mGetJson.toString().contains("amexamount")){
                getAmexAmount = mGetJson.getString("amexamount").toString();
            }
            if (mGetJson.toString().contains("discoveramount")){
                getDiscoverAmount = mGetJson.getString("discoveramount").toString();
            }
            if (mGetJson.toString().contains("BatchDate")){
                getBatchDate = mGetJson.getString("BatchDate").toString();
            }
        }

        batchNumberTv.setText(getBatchNumber);
        batchTotalCardCountTv.setText(getTotalCardCount);
        batchTotalCardAmountTv.setText(getTotalCardAmount);
        batchTotalCardTipTv.setText(getTotalTipAmount);

        batchVisaAmountTv.setText(getVisaAmount);
        batchMasterAmountTv.setText(getMasterAmount);
        batchAmexAmountTv.setText(getAmexAmount);
        batchDiscoverAmountTv.setText(getDiscoverAmount);

        batchTipAmountTv.setText(getTotalTipAmount);

        batchTimeTv.setText(getBatchDate);

        if (GlobalMemberValues.isStrEmpty(getVisaAmount)) {
            visaLn.setVisibility(View.GONE);
        }
        if (GlobalMemberValues.isStrEmpty(getMasterAmount)) {
            masterLn.setVisibility(View.GONE);
        }
        if (GlobalMemberValues.isStrEmpty(getAmexAmount)) {
            amexLn.setVisibility(View.GONE);
        }
        if (GlobalMemberValues.isStrEmpty(getDiscoverAmount)) {
            discoverLn.setVisibility(View.GONE);
        }
    }

    private void viewBatchedList() throws JSONException {
        // -------------------------------------------------------------------------------------
        batchDetailLn.setVisibility(View.GONE);

        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        batchedListLn.setVisibility(View.VISIBLE);
        batchedListLn.setAnimation(animation1);

        batchDetailPrintBtn.setVisibility(View.GONE);
        // -------------------------------------------------------------------------------------

        batchedListScrollLn.removeAllViews();

        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 2, 0, 2);

        // ------------------------------------------------------------------------------------------------------------
        // LinearLayout 객체 생성
        LinearLayout newTitleLn = new LinearLayout(this);
        newTitleLn.setLayoutParams(newLnLayoutParams);
        newTitleLn.setOrientation(LinearLayout.HORIZONTAL);
        newTitleLn.setPadding(0, 5, 0, 5);
        newTitleLn.setBackgroundColor(Color.parseColor("#f0f0f0"));

        // Total Card Count
        TextView totalCardCountTitleTv = new TextView(this);
        totalCardCountTitleTv.setText("Card Count");
        totalCardCountTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        totalCardCountTitleTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
        totalCardCountTitleTv.setTextColor(Color.parseColor("#3e3d42"));
        totalCardCountTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        totalCardCountTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        newTitleLn.addView(totalCardCountTitleTv);

        // Card Card Amount
        TextView totalCardAmountTitleTv = new TextView(this);
        totalCardAmountTitleTv.setText("Card Amount");
        totalCardAmountTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        totalCardAmountTitleTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
        totalCardAmountTitleTv.setTextColor(Color.parseColor("#3e3d42"));
        totalCardAmountTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        totalCardAmountTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        newTitleLn.addView(totalCardAmountTitleTv);

        // Total Card Tip
        TextView totalCardTitleTip = new TextView(this);
        totalCardTitleTip.setText("Card Tip");
        totalCardTitleTip.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        totalCardTitleTip.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
        totalCardTitleTip.setTextColor(Color.parseColor("#3e3d42"));
        totalCardTitleTip.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        totalCardTitleTip.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        newTitleLn.addView(totalCardTitleTip);

        // Batched Date
        TextView batchDateNewTitleTv = new TextView(this);
        batchDateNewTitleTv.setText("Batched Date");
        batchDateNewTitleTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
        batchDateNewTitleTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
        batchDateNewTitleTv.setTextColor(Color.parseColor("#3e3d42"));
        batchDateNewTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        batchDateNewTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.1f));
        newTitleLn.addView(batchDateNewTitleTv);

        batchedListScrollLn.addView(newTitleLn);
        // ------------------------------------------------------------------------------------------------------------

        JSONObject jsonStrObj = null;
        String strCardCount = "";
        String strCardAmount = "";
        String strCardTip = "";
        String strBatchDate = "";
        Cursor batchedList;
        String strQuery = "";

        strQuery = "select jsonstr from salon_sales_batch_json " +
                " where delyn = 'N' order by idx desc limit 50 ";
        GlobalMemberValues.logWrite(TAG, "query : " + strQuery + "\n");
        batchedList = dbInit.dbExecuteRead(strQuery);
        while (batchedList.moveToNext()) {
            String jsonStr = GlobalMemberValues.getDBTextAfterChecked(batchedList.getString(0), 1);
            if (!GlobalMemberValues.isStrEmpty(jsonStr)) {
                jsonStrObj = new JSONObject(jsonStr);
                if (jsonStrObj.toString().contains("BatchDate")){
                    strCardCount = jsonStrObj.getString("CreditCount").toString();
                    strCardAmount = jsonStrObj.getString("CreditAmount").toString();
                    strCardTip = jsonStrObj.getString("totalcardtip").toString();
                    strBatchDate = jsonStrObj.getString("BatchDate").toString();

                    // LinearLayout 객체 생성
                    LinearLayout newLn = new LinearLayout(this);
                    newLn.setLayoutParams(newLnLayoutParams);
                    newLn.setOrientation(LinearLayout.HORIZONTAL);
                    newLn.setPadding(0, 8, 0, 8);
                    newLn.setBackgroundResource(R.drawable.roundlayout_background_batchedlist);

                    // Total Card Count
                    TextView totalCardCountTv = new TextView(this);
                    totalCardCountTv.setText(strCardCount);
                    totalCardCountTv.setTag(jsonStr);
                    totalCardCountTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    totalCardCountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
                    totalCardCountTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    totalCardCountTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    newLn.addView(totalCardCountTv);

                    // Total Card Amount
                    TextView totalCardAmountTv = new TextView(this);
                    totalCardAmountTv.setText(strCardAmount);
                    totalCardAmountTv.setTag(jsonStr);
                    totalCardAmountTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    totalCardAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
                    totalCardAmountTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    totalCardAmountTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    newLn.addView(totalCardAmountTv);

                    // Total Card Tip
                    TextView totalCardTip = new TextView(this);
                    totalCardTip.setText(strCardTip);
                    totalCardTip.setTag(jsonStr);
                    totalCardTip.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    totalCardTip.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
                    totalCardTip.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    totalCardTip.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    newLn.addView(totalCardTip);

                    // Batched Date
                    TextView batchDateNewTv = new TextView(this);
                    batchDateNewTv.setText(strBatchDate);
                    batchDateNewTv.setTag(jsonStr);
                    batchDateNewTv.setTextColor(Color.parseColor(GlobalMemberValues.BATCHSUMMARY_TEXTCOLOR));
                    batchDateNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12.0f);
                    batchDateNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    batchDateNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.1f));
                    newLn.addView(batchDateNewTv);

                    batchDateNewTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setBackgroundColor(Color.parseColor("#fac7f2"));
                            try {
                                mGetJson = new JSONObject(v.getTag().toString());
                                mGetOpenBatchedList = "";
                                viewBatchDetail();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    batchedListScrollLn.addView(newLn);
                }
            }
        }
    }

    View.OnClickListener batchDetailButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.batchDetailCloseBtn : {
                    if (GlobalMemberValues.isAutoBatchInCashInOut(MainActivity.mDbInit)
                            && BatchSummary.mActivity != null && !BatchSummary.mActivity.isFinishing()) {
                        BatchSummary.finishActivity();
                    }
                    finish();
                    break;
                }
                case R.id.batchDetailPrintBtn : {
                    try {
                        mGetJson.put("receiptprinttype", "3");
                        GlobalMemberValues.printReceiptByJHP(mGetJson, mContext, "batchdetail");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.batchedListBtn : {
                    try {
                        mGetOpenBatchedList = "Y";
                        viewBatchedList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    };
}
