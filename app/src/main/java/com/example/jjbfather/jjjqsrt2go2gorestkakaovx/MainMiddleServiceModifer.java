package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class MainMiddleServiceModifer extends Activity {
    private static final String TAG = "MainMiddleServiceModifier";

    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private LinearLayout modifierButtonLayout1, modifierButtonLayout2;

    private LinearLayout combineLn1, combineLn2;

    private LinearLayout viewbottomLn1, viewbottomLn2, viewbottomLn3;

    private Button optionaddPopupVButton, optionaddPopupCloseButton, saveOptionAddBtn;
    private Button viewallbtn, hideallbtn;

    private Button qtyupbtn, qtydownbtn, modifierinitbtn;

    private TextView modifierclearbtn;

    private TextView memotokitchenclearbtn;

    private TextView qtyTv;

    private ImageView optionaddPopupServiceImageView;
    private TextView optionaddPopupServiceNameTextView;
    private TextView optionaddPopupPriceTextView, optionaddPopupAddPriceTextView;
    private TextView optionaddPopupAmountTextView, optionaddPopupTxtTextView;
    private TextView modifierValueTv;
    private TextView bottomtv1, bottomtv2, bottomtv3;

    private TextView modifierListTitleTv1, modifierListTitleTv2;

    private TextView memotokitchentitleTv;

    public static TextView memotokitchenEv;

    ScrollView optionBtnListScrollView;
    private static LinearLayout optionBtnListLinearLayout;

    //    ScrollView optionaddPopupOAListScrollView;
    private static LinearLayout optionaddPopupOAListLinearLayout;

    //ScrollView optionAddCartListScrollView;
    //private static LinearLayout optionAddCartListLinearLayout;

    // jihun add
    private static ListView optionAddCartListLinearLayout_list;
    public OptionAddCartListLinearLayout_list_adapter optionAddCartListLinearLayout_list_adapter;

    //10232024 Modifier button array
    private ArrayList<Button> modifierButtonArray = new ArrayList<Button>();
    private Button currSelectedModifierButton = null;
    private View currSelectedOptionCategory = null;
    private Boolean changedSelectedModifierButton = false;


    Intent mIntent;
    String mServiceIdx = "";

    private double mBasicPrice = 0;
    private double mAmountPrice = 0;

    private double mNowPrice = 0;
    private double mAddPrice = 0;

    private String mSelectedModifierButtonTagValue = "";

    private String mSelectedOptionCategoryNameValue = "";                    // Option 이름 (옵션 카테고리이름)
    private String mSelectedOptionAddButtonTagValue = "";

    private String mSelectedOptionCategoryIdxValue = "";                    // Option 카테고리 idx (옵션 카테고리이름)
    private String mSelectedOptionItemIdxValue = "";                         // 선택한 Option 아이템 idx

    private String mModifierIdx = "";
    private String mOptionTxt = "";

    private String mCombineType = "A";      // A : 버튼과 옵션 조합형                 B : 옵션만 선택형

    // 선택필수인 옵션중 선택한 옵션의 Service Idx
    private Vector<String> mSvcIdxInRequiredOptions;

    private String getModifierCode = "";
    private String getPosition = "";
    private int getPositionInt = -1;

    private String mModifierCode = "";

    TemporarySaleCart parentTemporarySaleCart;

    GetDataAtSQLite dataAtSqlite;

    private LinearLayout[] optionLnArr = null;

    //
    private ExpandableListView mExpandablelistView;
    private List<String[]> listDataHeader;
    private HashMap<Integer, List<String[]>> listDataChild;
    public ExpandableListAdapter mExpandableListAdapter;

    // jihun park add
    private boolean isEloPro = false;

    // Discount / Extra 가 적용되었을 경우에 사용할 values
    String mDCExtra_CouponNum = "";
    String mDCExtra_Type = "";
    String mDCExtra_EachAll = "";
    String mDCExtra_Rate = "";
    String mDCExtra_RateType = "";

    // 삭제하는 cart 의 position 값
    int mTempDeletedPositionInCart = -1;

    boolean hasOptions = true;

    boolean b_itemRemoving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        if (GlobalMemberValues.is_customerMain){
            setContentView(R.layout.activity_customer_main_middle_service_modifer);
            int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this));
            int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this));

//            if (GlobalMemberValues.thisTabletRealWidth > 1280) {
//                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this));
//            }
//            if (GlobalMemberValues.thisTabletRealHeight > 800) {
//                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this));
//            }
//            LinearLayout.LayoutParams parentLnParams
//                    = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
//            parentLnParams.setMargins(0, 0, 0, 0);
//
            parentLn = (LinearLayout)findViewById(R.id.optionaddPopupLinearLayout);
//            parentLn.setLayoutParams(parentLnParams);
        } else {
            setContentView(R.layout.activity_main_middle_service_modifer);
            int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 90;
            int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;

            if (GlobalMemberValues.thisTabletRealWidth > 1280) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 90;
            }
            if (GlobalMemberValues.thisTabletRealHeight > 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
            }
            LinearLayout.LayoutParams parentLnParams
                    = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
            parentLnParams.setMargins(0, 0, 0, 0);

            parentLn = (LinearLayout)findViewById(R.id.optionaddPopupLinearLayout);
            parentLn.setLayoutParams(parentLnParams);
        }

        this.setFinishOnTouchOutside(false);

//        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 90;
//        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
//
//        if (GlobalMemberValues.thisTabletRealWidth > 1280) {
//            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 90;
//        }
//        if (GlobalMemberValues.thisTabletRealHeight > 800) {
//            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
//        }

        /**
         if (GlobalMemberValues.mDevicePAX) {
         parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 92;
         parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 97;
         }
         **/



        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            mServiceIdx = mIntent.getStringExtra("ServiceIdx");
            getModifierCode = mIntent.getStringExtra("modifiercode");
            getPosition = mIntent.getStringExtra("position");
            // 부모로부터 받은 TemporarySaleCart 객체
            parentTemporarySaleCart = (TemporarySaleCart)mIntent.getSerializableExtra("ParentTemporarySaleCartInstance");

            if (GlobalMemberValues.isStrEmpty(getPosition)) {
                getPositionInt = -1;
            } else {
                getPositionInt = GlobalMemberValues.getIntAtString(getPosition);
            }

            GlobalMemberValues.logWrite(TAG + "J", "넘겨받은 Service Idx : " + mServiceIdx + "\n");
            GlobalMemberValues.logWrite(TAG + "J", "넘겨받은 Modifier Code : " + getModifierCode + "\n");
            GlobalMemberValues.logWrite(TAG + "J", "넘겨받은 Position : " + getPosition + "\n");


            if (parentTemporarySaleCart != null) {
                mDCExtra_CouponNum = parentTemporarySaleCart.couponNumber;
                mDCExtra_Type = parentTemporarySaleCart.selectedDcExtraType;
                mDCExtra_EachAll = parentTemporarySaleCart.selectedDcExtraAllEach;

                String eachDcExtraStr = parentTemporarySaleCart.eachDcExtraStr;
                if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.eachDcExtraStr)) {
                    eachDcExtraStr = GlobalMemberValues.getReplaceText(eachDcExtraStr, "Each Discount", "");
                    eachDcExtraStr = GlobalMemberValues.getReplaceText(eachDcExtraStr, " ", "");
                    if (eachDcExtraStr.indexOf("%") > -1) {
                        mDCExtra_RateType = "%";
                    }
                    if (eachDcExtraStr.indexOf("$") > -1) {
                        mDCExtra_RateType = "$";
                    }
                    mDCExtra_Rate = GlobalMemberValues.getReplaceText(eachDcExtraStr, "%", "");
                    mDCExtra_Rate = GlobalMemberValues.getReplaceText(eachDcExtraStr, "$", "");
                }

                GlobalMemberValues.logWrite(TAG + "JJJ", "mDCExtra_CouponNum : " + mDCExtra_CouponNum + "\n");
                GlobalMemberValues.logWrite(TAG + "JJJ", "mDCExtra_Type : " + mDCExtra_Type + "\n");
                GlobalMemberValues.logWrite(TAG + "JJJ", "mDCExtra_EachAll : " + mDCExtra_EachAll + "\n");
                GlobalMemberValues.logWrite(TAG + "JJJ", "mDCExtra_RateType : " + mDCExtra_RateType + "\n");
                GlobalMemberValues.logWrite(TAG + "JJJ", "mDCExtra_Rate : " + mDCExtra_Rate + "\n");
            }

            /*******************************************************************************************/
        } else {
            finish();

            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_right);
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setContents();
                        isEloPro = GlobalMemberValues.isEloPro();
                        setContents2();
                    }
                });
//                startingHandler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    private Handler startingHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            setContents2();
            // -------------------------------------------------------------------------------------
        }
    };

    private void setContents() {
        hideKeyboard();
        float f_fontsize = GlobalMemberValues.getGlobalFontSize();
        int i_16 = (int) (16 * f_fontsize);
        int i_20 = (int) (20 * f_fontsize);
        int i_22 = (int) (22 * f_fontsize);
        int i_24 = (int) (24 * f_fontsize);
        int i_26 = (int) (26 * f_fontsize);
        int i_30 = (int) (30 * f_fontsize);
        int i_34 = (int) (34 * f_fontsize);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(mContext);

        //setInitDbAll();

        dataAtSqlite = new GetDataAtSQLite(this);

        mSvcIdxInRequiredOptions = new Vector<String>();

        modifierButtonLayout1 = (LinearLayout) findViewById(R.id.modifierButtonLayout1);
        modifierButtonLayout2 = (LinearLayout) findViewById(R.id.modifierButtonLayout2);

        combineLn1 = (LinearLayout) findViewById(R.id.combineLn1);
        combineLn2 = (LinearLayout) findViewById(R.id.combineLn2);

        viewbottomLn1 = (LinearLayout) findViewById(R.id.viewbottomLn1);
        viewbottomLn2 = (LinearLayout) findViewById(R.id.viewbottomLn2);
        viewbottomLn3 = (LinearLayout) findViewById(R.id.viewbottomLn3);

        String tempButtonmodifierbottomviewyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select buttonmodifierbottomviewyn from salon_storestationsettings_system ");
        if (GlobalMemberValues.isStrEmpty(tempButtonmodifierbottomviewyn)) {
            tempButtonmodifierbottomviewyn = "Y";
        }
        if (tempButtonmodifierbottomviewyn == "Y" || tempButtonmodifierbottomviewyn.equals("Y")) {
            viewbottomLn1.setVisibility(View.VISIBLE);
            viewbottomLn2.setVisibility(View.VISIBLE);
            viewbottomLn3.setVisibility(View.VISIBLE);
        } else {
            viewbottomLn1.setVisibility(View.GONE);
            viewbottomLn2.setVisibility(View.GONE);
            viewbottomLn3.setVisibility(View.GONE);
        }

        // 스크롤뷰 객체 생성
        optionBtnListScrollView = (ScrollView)findViewById(R.id.optionBtnListScrollView);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        optionBtnListLinearLayout = (LinearLayout)findViewById(R.id.optionBtnListLinearLayout);

        // 스크롤뷰 객체 생성
//        optionaddPopupOAListScrollView = (ScrollView)findViewById(R.id.optionaddPopupOAListScrollView);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        optionaddPopupOAListLinearLayout = (LinearLayout)findViewById(R.id.optionaddPopupOAListLinearLayout);

        // 스크롤뷰 객체 생성
        //optionAddCartListScrollView = (ScrollView)findViewById(R.id.optionAddCartListScrollView);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        //optionAddCartListLinearLayout = (LinearLayout)findViewById(R.id.optionAddCartListLinearLayout);

        // jihun add
        optionAddCartListLinearLayout_list = (ListView)findViewById(R.id.optionAddCartListLinearLayout_list);


        optionaddPopupVButton = (Button) findViewById(R.id.optionaddPopupVButton);

        optionaddPopupCloseButton = (Button)findViewById(R.id.optionaddPopupCloseButton);


        if (GlobalMemberValues.is_customerMain){

        } else {
            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                optionaddPopupCloseButton.setText("");
                optionaddPopupCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
            } else {
                optionaddPopupCloseButton.setTextSize(i_20);
            }
            if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
                optionaddPopupVButton.setText("");
                optionaddPopupVButton.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_enter);
            } else {
                optionaddPopupVButton.setTextSize(i_20);
            }
        }

        saveOptionAddBtn = (Button)findViewById(R.id.saveOptionAddBtn);

        modifierclearbtn = (TextView) findViewById(R.id.modifierclearbtn);
        modifierclearbtn.setTextSize(i_24);

        memotokitchenclearbtn = (TextView) findViewById(R.id.memotokitchenclearbtn);
        memotokitchenclearbtn.setTextSize(i_24 + GlobalMemberValues.globalAddFontSizeForPAX());

        qtyTv = (TextView) findViewById(R.id.qtyTv);
        qtyTv.setTextSize(i_26 + GlobalMemberValues.globalAddFontSizeForPAX());

        viewallbtn = (Button)findViewById(R.id.viewallbtn);
        hideallbtn = (Button)findViewById(R.id.hideallbtn);

        //viewallbtn.setVisibility(View.GONE);
        //hideallbtn.setVisibility(View.GONE);

        qtyupbtn = (Button)findViewById(R.id.qtyupbtn);

        qtydownbtn = (Button)findViewById(R.id.qtydownbtn);

        modifierinitbtn = (Button)findViewById(R.id.modifierinitbtn);
        modifierinitbtn.setTextSize(i_24 + GlobalMemberValues.globalAddFontSizeForPAX());

        optionaddPopupVButton.setOnClickListener(onSingleClickListener);

        optionaddPopupCloseButton.setOnClickListener(optionaddPopupButtonClickListener);
        modifierclearbtn.setOnClickListener(optionaddPopupButtonClickListener);
        saveOptionAddBtn.setOnClickListener(optionaddPopupButtonClickListener);
        memotokitchenclearbtn.setOnClickListener(optionaddPopupButtonClickListener);

        viewallbtn.setOnClickListener(optionaddPopupButtonClickListener);
        hideallbtn.setOnClickListener(optionaddPopupButtonClickListener);

        qtyupbtn.setOnClickListener(optionaddPopupButtonClickListener);
        qtydownbtn.setOnClickListener(optionaddPopupButtonClickListener);
        modifierinitbtn.setOnClickListener(optionaddPopupButtonClickListener);

        optionaddPopupServiceImageView = (ImageView) findViewById(R.id.optionaddPopupServiceImageView);

        optionaddPopupServiceNameTextView = (TextView) findViewById(R.id.optionaddPopupServiceNameTextView);
        optionaddPopupPriceTextView = (TextView) findViewById(R.id.optionaddPopupPriceTextView);
        optionaddPopupAddPriceTextView = (TextView) findViewById(R.id.optionaddPopupAddPriceTextView);
        optionaddPopupAmountTextView = (TextView) findViewById(R.id.optionaddPopupAmountTextView);
        optionaddPopupTxtTextView = (TextView) findViewById(R.id.optionaddPopupTxtTextView);

        optionaddPopupServiceNameTextView = (TextView) findViewById(R.id.optionaddPopupServiceNameTextView);
        optionaddPopupServiceNameTextView.setTextSize(i_30 + GlobalMemberValues.globalAddFontSizeForPAX());

        modifierValueTv = (TextView) findViewById(R.id.modifierValueTv);
        modifierValueTv.setTextSize(i_22 + GlobalMemberValues.globalAddFontSizeForPAX());

        memotokitchentitleTv = (TextView) findViewById(R.id.memotokitchentitleTv);
        memotokitchentitleTv.setTextSize(i_20 + GlobalMemberValues.globalAddFontSizeForPAX());

        memotokitchenEv = (TextView) findViewById(R.id.memotokitchenEv);
        memotokitchenEv.setTextSize(i_22 + GlobalMemberValues.globalAddFontSizeForPAX());
        memotokitchenEv.setText("");
        memotokitchenEv.setOnClickListener(optionaddPopupButtonClickListener);

        modifierListTitleTv1 = (TextView) findViewById(R.id.modifierListTitleTv1);
        modifierListTitleTv1.setTextSize(i_16 + GlobalMemberValues.globalAddFontSizeForPAX());

        modifierListTitleTv2 = (TextView) findViewById(R.id.modifierListTitleTv2);
        modifierListTitleTv2.setTextSize(i_16 + GlobalMemberValues.globalAddFontSizeForPAX());

        bottomtv1 = (TextView) findViewById(R.id.bottomtv1);
        bottomtv1.setTextSize(i_22 + GlobalMemberValues.globalAddFontSizeForPAX());

        bottomtv2 = (TextView) findViewById(R.id.bottomtv2);
        bottomtv2.setTextSize(i_22 + GlobalMemberValues.globalAddFontSizeForPAX());

        bottomtv3 = (TextView) findViewById(R.id.bottomtv3);
        bottomtv3.setTextSize(i_22 + GlobalMemberValues.globalAddFontSizeForPAX());

        optionaddPopupPriceTextView = (TextView) findViewById(R.id.optionaddPopupPriceTextView);
        optionaddPopupPriceTextView.setTextSize(i_30 + GlobalMemberValues.globalAddFontSizeForPAX());

        optionaddPopupAddPriceTextView = (TextView) findViewById(R.id.optionaddPopupAddPriceTextView);
        optionaddPopupAddPriceTextView.setTextSize(i_30 + GlobalMemberValues.globalAddFontSizeForPAX());

        optionaddPopupAmountTextView = (TextView) findViewById(R.id.optionaddPopupAmountTextView);
        optionaddPopupAmountTextView.setTextSize(i_34 + GlobalMemberValues.globalAddFontSizeForPAX());
    }

    private void setContents2() {
        optionAddCartListLinearLayout_list.requestFocusFromTouch();

        if (!GlobalMemberValues.isStrEmpty(getModifierCode)) {
            mModifierCode = getModifierCode;
            // 수정일 경우
            transDataFromImsi(mModifierCode, "real");
        } else {
            // Modifier Code  생성
            mModifierCode = "MF" + GlobalMemberValues.makeSalesCode();
        }
        GlobalMemberValues.logWrite(TAG + "J", "mModifierCode : " + mModifierCode + "\n");

        if (parentTemporarySaleCart != null) {
            String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
            if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                String tempKitchenMemo = MainActivity.mDbInit.dbExecuteReadReturnString("select memoToKitchen from temp_salecart where idx = '" + tempSaleCartIdx + "' ");
                memotokitchenEv.setText(tempKitchenMemo);

                String tempRegQty = MainActivity.mDbInit.dbExecuteReadReturnString("select sQty from temp_salecart where idx = '" + tempSaleCartIdx + "' ");
                qtyTv.setText(tempRegQty);
            }
        }

        String strQuery = "select midx, servicename, service_price, subServiceTime, " +
                " pointratio, saleprice, salestart, saleend, strFilePath, servicename2, servicename3 " +
                " from salon_storeservice_sub " +
                " where idx = '" + mServiceIdx + "' ";
        GlobalMemberValues.logWrite(TAG, "Query : " + strQuery + "\n");
        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String tempMidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
            String tempServiceName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
            String tempServicePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
            String tempServiceTime = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1);
            String tempPointRatio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
            String tempSalePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
            String tempSaleStart = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1);
            String tempSaleEnd = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1);
            String tempStrFilePath = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1);
            String tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1);
            String tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1);

            mNowPrice = GlobalMemberValues.getDoubleAtString(tempServicePrice);

            //11192024 if editing a dynamic price item, item price in db is zero, but item price in shopping
            //cart is not
            if (GlobalMemberValues.getDoubleAtString(tempServicePrice) == 0.0){
                if(Double.parseDouble(MainMiddleService.mTouchedArr[9]) != 0.0){
                    mNowPrice = GlobalMemberValues.getDoubleAtString(MainMiddleService.mTouchedArr[9]);
                }
            }

            String tempServiceNameFull = tempServiceName;
            if (!GlobalMemberValues.isStrEmpty(tempServiceName2)) {
                tempServiceNameFull += " " + tempServiceName2;
            }
            if (!GlobalMemberValues.isStrEmpty(tempServiceName3)) {
                tempServiceNameFull += " " + tempServiceName3;
            }

            optionaddPopupServiceNameTextView.setText(tempServiceNameFull);

            // Time Menu 타임메뉴 관련 ----------------------------------------------------------------------------------------------------
            if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
                if (MainActivity.mDbInit != null) {
                    // Time Menu 일 경우 타임별 가격으로 한다.
                    String tempTimemenuYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select timemenuyn from salon_storeservice_sub where idx = '" + mServiceIdx + "' ");
                    if (GlobalMemberValues.isStrEmpty(tempTimemenuYN)) {
                        tempTimemenuYN = "N";
                    }

                    double tempTimeMenuPrice = 0;
                    if (tempTimemenuYN.equals("Y") || tempTimemenuYN == "Y") {
                        //GlobalMemberValues.logWrite("timemenulog", "tempTimemenuYN :  " + " select " + GlobalMemberValues.NOW_TIME_CODEVALUE  + "_" + GlobalMemberValues.getWeekDayNum() +
                        //" from salon_storeservice_sub_timemenuprice where svcidx = '" + mServiceIdx + "' " + "\n");
                        if (GlobalMemberValues.SELECTED_TIME_CODEVALUE == null) {
                            GlobalMemberValues.SELECTED_TIME_CODEVALUE = "a";
                        }
                        tempTimeMenuPrice = GlobalMemberValues.getDoubleAtString(
                                MainActivity.mDbInit.dbExecuteReadReturnString(
                                        " select " + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "_" + GlobalMemberValues.getWeekDayNum() +
                                                " from salon_storeservice_sub_timemenuprice where svcidx = '" + mServiceIdx + "' ")
                        );
                    }

                    GlobalMemberValues.logWrite("timemenulog", "tempTimeMenuPrice :  " + tempTimeMenuPrice + "\n");

                    if (tempTimeMenuPrice > 0) {
                        mNowPrice = tempTimeMenuPrice;
                    }
                }
            }
            // --------------------------------------------------------------------------------------------------------------------------

            String tempPriceTxt = "";
            String tempPriceTxt2 = "";

            // 세일 기간이고, 세일가격에 값이 있을 때  ------------------------------
            if ((!GlobalMemberValues.isStrEmpty(tempSaleStart)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.getReplaceText(tempSaleStart, "/", "-"), GlobalMemberValues.STR_NOW_DATE) >= 0)
                    && (!GlobalMemberValues.isStrEmpty(tempSaleEnd)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, GlobalMemberValues.getReplaceText(tempSaleEnd, "/","-")) >= 0)
                    &&!GlobalMemberValues.isStrEmpty(tempSalePrice)) {

                mNowPrice = GlobalMemberValues.getDoubleAtString(tempSalePrice);

                tempPriceTxt = "" + GlobalMemberValues.getCommaStringForDouble(tempServicePrice) +
                        "-->" + "" + GlobalMemberValues.getCommaStringForDouble(tempSalePrice);

                tempPriceTxt2 = mNowPrice + "";

            } else {
                tempPriceTxt = "" + GlobalMemberValues.getCommaStringForDouble(tempServicePrice);
                tempPriceTxt2 = tempServicePrice;
            }

            //11192024
            //If the item had a dynamically entered price
            String dynamicprice = mIntent.getStringExtra("dynamicprice");
            if(dynamicprice != null && !dynamicprice.equals("")){
                mNowPrice = GlobalMemberValues.getDoubleAtString(mIntent.getStringExtra("dynamicprice"));
            }

            setPrice(mNowPrice, 0);

            mBasicPrice = GlobalMemberValues.getDoubleAtString(tempPriceTxt2);

            /** 다운로드한 서비스 이미지가 있는지 체크한다. ********************************************/
            if (GlobalMemberValues.is_customerMain){
                // 220823 요청에 의한 메뉴이미지 삭제처리.
                Boolean isBgImage = false;
                String strFilePath = "";
                if (!GlobalMemberValues.isStrEmpty(tempStrFilePath)) {
                    // 이미지 경로 + 파일명 만들기
                    strFilePath = Environment.getExternalStorageDirectory().toString() +
                            "/" + GlobalMemberValues.STATION_CODE +
                            "/" + GlobalMemberValues.FOLDER_SERVICEIMAGE +
                            "/serviceimg_" + mServiceIdx + ".png";
                    if (new File(strFilePath).exists() == false) {
                        //GlobalMemberValues.logWrite("savedFileCheck", "파일이 없습니다.");
                        isBgImage = false;
                    } else {
                        isBgImage = true;
                    }
                } else {
                    isBgImage = false;
                }


                if (!isBgImage) {
                    optionaddPopupServiceImageView.setVisibility(View.GONE);
                    //optionaddPopupServiceImageView.setImageResource(R.drawable.aa_images_servicenoimage);
                } else {
                    // Bitmap 으로 변환
                    Bitmap serviceBgImageBm = BitmapFactory.decodeFile(strFilePath);
                    optionaddPopupServiceImageView.setImageBitmap(serviceBgImageBm);
                    //optionaddPopupServiceImageView.setPadding(3, 3, 3, 3);
                }
            }

            /****************************************************************************************/

            setModifierButton();

            // modifier view type 가져오기
            String modifierviewtype = dbInit.dbExecuteReadReturnString(
                    " select modifierviewtype from salon_storegeneral ");
            if (GlobalMemberValues.isStrEmpty(modifierviewtype)) {
                modifierviewtype = "";
            }

            setOptionAdditional(modifierviewtype);



        } else {
            optionaddPopupServiceNameTextView.setText("");
            optionaddPopupPriceTextView.setText("");
            optionaddPopupAmountTextView.setText("");
        }

        cursor.close();

//        setSelectedValueInit();
        modifierValueTv.setText("");
        mSelectedModifierButtonTagValue = "";
        mSelectedOptionAddButtonTagValue = "";

        setSelectedModifierOptionAdd();
    }

    private boolean transDataFromImsi(String paramModifierCode, String paramFromType) {
        boolean returnValue = true;

        String returnResult = "";

        String fromTableStr1 = "temp_salecart_optionadd";
        String fromTableStr2 = "temp_salecart_optionadd_imsi";
        if (paramFromType.equals("imsi")) {
            fromTableStr1 = "temp_salecart_optionadd_imsi";
            fromTableStr2 = "temp_salecart_optionadd";
        }

        Vector<String> strInsertQueryVec = new Vector<String>();

        // 먼제 기존에 있던 데이터는 삭제한다.
        String deleteQuery = " delete from " + fromTableStr2 + " where modifiercode = '" + paramModifierCode + "' ";
        strInsertQueryVec.addElement(deleteQuery);

        String strQuery = " ";
        String[] strArrSaleCartModifier = dataAtSqlite.getSaleCartModifier(fromTableStr1, paramModifierCode);

        if (strArrSaleCartModifier == null) {
            hasOptions = false;
        } else {
            hasOptions = true;
        }

        if (strArrSaleCartModifier != null && strArrSaleCartModifier.length > 0) {
            for (int scmi = 0; scmi < strArrSaleCartModifier.length; scmi++) {
                String[] strSaleCartModifier = strArrSaleCartModifier[scmi].split(GlobalMemberValues.STRSPLITTER1);

                strQuery = " insert into " + fromTableStr2 + " (" +
                        " holdcode, sidx, stcode, svcIdx, modifiername, optionaddname, modifieroptionaddfullname, " +
                        " modifierprice, optionaddprice, optioncategoryidx, optionitemidx, optioncategoryname, modifiercode, qty " +
                        " ) values ( " +
                        " '" + strSaleCartModifier[0] + "', " +     // holdcode
                        " '" + strSaleCartModifier[1] + "', " +     // sidx
                        " '" + strSaleCartModifier[2] + "', " +     // stcode
                        " '" + strSaleCartModifier[3] + "', " +     // svcidx
                        " '" + GlobalMemberValues.getDBTextAfterChecked(strSaleCartModifier[4],0) + "', " +     // modifiername
                        " '" + GlobalMemberValues.getDBTextAfterChecked(strSaleCartModifier[5],0) + "', " +     // option add name
                        " '" + GlobalMemberValues.getDBTextAfterChecked(strSaleCartModifier[6],0) + "', " +     // modifier option add full name
                        " '" + strSaleCartModifier[7] + "', " +     // modifier price
                        " '" + strSaleCartModifier[8] + "', " +     // option add price
                        " '" + strSaleCartModifier[9] + "', " +     // option category idx
                        " '" + strSaleCartModifier[10] + "', " +    // option item idx
                        " '" + GlobalMemberValues.getDBTextAfterChecked(strSaleCartModifier[11],0) + "', " +    // option category name
                        " '" + strSaleCartModifier[12] + "', " +
                        " '" + strSaleCartModifier[13] + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strQuery);

                if (paramFromType.equals("real")) {
                    if (!GlobalMemberValues.isStrEmpty(strSaleCartModifier[9])) {
                        //GlobalMemberValues.logWrite("chkjjjlog", "여기1" + "\n");
                        mSvcIdxInRequiredOptions.remove(strSaleCartModifier[9]);
                        mSvcIdxInRequiredOptions.addElement(strSaleCartModifier[9]);
                    }
                }
            }
        }

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            returnValue = false;
        } else {
            returnValue = true;
        }

        return returnValue;
    }

    private void hideKeyboard() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

    }

    JJJ_OnSingleClickListener onSingleClickListener = new JJJ_OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.optionaddPopupVButton : {
                    // 먼저 required 인 항목을 선택했는지 체크
                    if (!checkRequiredOption()) {
                        return;
                    }
                    submitAdd();

                    break;
                }
            }
        }
    };

    View.OnClickListener optionaddPopupButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.modifierclearbtn : {
                    LogsSave.saveLogsInDB(59);
                    setSelectedValueInit();
                    break;
                }
                case R.id.saveOptionAddBtn : {
                    LogsSave.saveLogsInDB(60);
                    saveOptionAdd();
                    break;
                }
                case R.id.optionaddPopupCloseButton : {
                    LogsSave.saveLogsInDB(61);
                    closeModifierPage();
                    break;
                }
                case R.id.viewallbtn : {
                    LogsSave.saveLogsInDB(51);
                    setViewHideOption("viewall");
                    break;
                }
                case R.id.hideallbtn : {
                    LogsSave.saveLogsInDB(52);
                    setViewHideOption("hideall");
                    break;
                }
                case R.id.qtyupbtn : {
                    LogsSave.saveLogsInDB(57);
                    qtyEdit("U");
                    break;
                }
                case R.id.qtydownbtn: {
                    LogsSave.saveLogsInDB(58);
                    qtyEdit("D");
                    break;
                }
                case R.id.modifierinitbtn : {
                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Modifier Init.")
                                .setMessage("Do you want to delete all modifiers?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setInitDbAll();
                                    }
                                }).show();
                    }

                    break;
                }
                case R.id.memotokitchenclearbtn : {
                    memotokitchenEv.setText("");
                    break;
                }
                case R.id.memotokitchenEv : {
                    LogsSave.saveLogsInDB(53);
                    openKitchenMemo();
                    break;
                }

            }
        }
    };

    private void setViewHideOption(final String paramValue) {
        if (listDataHeader == null) return;
        if (listDataHeader.size() > 0 ) {
            for (int i = 0; i < listDataHeader.size(); i++){
                if (paramValue.equals("viewall")) {
                    mExpandablelistView.expandGroup(i);
                } else {
                    mExpandablelistView.collapseGroup(i);
                }
            }
        }
    }

    private void qtyEdit(String paramType) {
        String nowQty = qtyTv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(nowQty)) {
            nowQty = "1";
        }
        int nowQtyInt = GlobalMemberValues.getIntAtString(nowQty);
        if (paramType.equals("U") || paramType == "U") {
            if (nowQtyInt < 99) {
                nowQtyInt++;
            }
        } else {
            if (nowQtyInt > 1) {
                nowQtyInt--;
            }
        }

        GlobalMemberValues.logWrite("qtyupdownlog", "qty : " + nowQty + "\n");

        qtyTv.setText(nowQtyInt + "");
        setPrice(mNowPrice, mAddPrice);
    }

    private void openKitchenMemo() {
//        setViewHideOption("hideall");

        String tempKitchenMemo = memotokitchenEv.getText().toString();

        Intent kitchenMemoIntent = new Intent(MainActivity.mContext, MemoToKitchen.class);
        kitchenMemoIntent.putExtra("kitchenmemotype", "modifier");
        kitchenMemoIntent.putExtra("regmemotxt", tempKitchenMemo);
        mActivity.startActivity(kitchenMemoIntent);

        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    private void setModifierButton() {
        // 뷰 추가전 먼저 초기화(삭제)한다.
        //optionBtnListLinearLayout.removeAllViews();

        GlobalMemberValues.logWrite("mCombineTypelog", "mCombineType : " + mCombineType + "\n");

        int modifierbuttonCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option_btn where svcidx = " + mServiceIdx + " and useyn = 'Y' "));
        if (modifierbuttonCount > 0) {
            mCombineType = "A";

            modifierButtonLayout1.setVisibility(View.VISIBLE);
            modifierButtonLayout2.setVisibility(View.VISIBLE);

            //11192024 view no longer needed, so no need to turn it visible
            //combineLn1.setVisibility(View.VISIBLE);
            //combineLn2.setVisibility(View.VISIBLE);

            int btnHeight = 100;
            if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
                if (GlobalMemberValues.thisTabletRealHeight < 800) {
                    btnHeight = 70;
                }
            } else {
                if (GlobalMemberValues.thisTabletRealWidth < 800) {
                    btnHeight = 70;
                }
            }

            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams newLnLayoutParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            newLnLayoutParams.setMargins(0, 0, 0, 15);
            // Sub LinearLayout 파라미터 설정
            LinearLayout.LayoutParams subNewLnLayoutParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            subNewLnLayoutParams.setMargins(0, 0, 0, 0);
            // Sub TextView 파라미터 설정
            LinearLayout.LayoutParams subNewBtnLayoutParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
            subNewBtnLayoutParams.setMargins(0, 0, 0, 5);

            Button subNewBtn = null;

            String[] strArrModifierButton = dataAtSqlite.getModifierButton(mServiceIdx, modifierbuttonCount);

            for (int mbi = 0; mbi < strArrModifierButton.length; mbi++) {
                String[] strModifierButton = strArrModifierButton[mbi].split(GlobalMemberValues.STRSPLITTER1);

                String tempBtnname = strModifierButton[1];
                String tempColorcode = strModifierButton[2];
                String tempPrice = strModifierButton[3];
                String tempIgnorePrice = strModifierButton[4];

                if (GlobalMemberValues.isStrEmpty(tempColorcode)) {
                    tempColorcode = "R";
                }

                if (GlobalMemberValues.isStrEmpty(tempPrice)) {
                    tempPrice = "0";
                }

                if (GlobalMemberValues.isStrEmpty(tempIgnorePrice)) {
                    tempIgnorePrice = "N";
                }

                if (tempIgnorePrice == "Y" || tempIgnorePrice.equals("Y")) {
                    tempPrice = "0";
                }

                int btnBackground = 0;
                switch (tempColorcode) {
                    case "R" : {
                        btnBackground = R.drawable.button_selector_modifierbutton_red;
                        break;
                    }
                    case "B" : {
                        btnBackground = R.drawable.button_selector_modifierbutton_blue;
                        break;
                    }
                    case "G" : {
                        btnBackground = R.drawable.button_selector_modifierbutton_gray;
                        break;
                    }
                }

                String tempBtnStr = tempBtnname;

                if (tempIgnorePrice == "Y" || tempIgnorePrice.equals("Y")) {
                    tempBtnStr += "\n(Ignore Pri.)";
                } else {
                    if (GlobalMemberValues.getDoubleAtString(tempPrice) > 0
                            || GlobalMemberValues.getDoubleAtString(tempPrice) < 0 ) {
                        tempBtnStr += "\n(" + GlobalMemberValues.getCommaStringForDouble(tempPrice) + ")";
                    }
                }

                String tagStr = tempBtnname + "-JJJ-" + tempPrice + "-JJJ-" + tempColorcode + "-JJJ-" + tempIgnorePrice;

                GlobalMemberValues.logWrite("modifierbtnvaluelog", "tagStr  : " + tagStr + "\n");

                subNewBtn = new Button(mContext);
                subNewBtn.setLayoutParams(newLnLayoutParams);
                subNewBtn.setGravity(Gravity.CENTER);
                subNewBtn.setText(tempBtnStr);
                if (isEloPro) {
                    subNewBtn.setTextSize(20);
                } else {
                    subNewBtn.setTextSize(18);
                }
                subNewBtn.setTextColor(Color.parseColor("#3e3d42"));
                subNewBtn.setPaintFlags(subNewBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                subNewBtn.setBackgroundResource(btnBackground);
                subNewBtn.setTag(tagStr);

                final String buttonColor = tempColorcode;

                subNewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //GlobalMemberValues.displayDialog(mContext, "Info", "Tag Value : " + v.getTag().toString(), "Close");
                        Button thisButton = (Button) v;

                        //10232024
                        if (currSelectedModifierButton == v){
                            currSelectedModifierButton = null;
                            setSelectedValueInit();

                            switch (buttonColor) {
                                case "R": {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_red);
                                    break;
                                }
                                case "B": {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_blue);
                                    break;
                                }
                                case "G": {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_gray);
                                    break;
                                }
                            }
                            thisButton.setTextColor(Color.BLACK);
                        } else {
                            changedSelectedModifierButton = true;
                            currSelectedModifierButton = (Button) v;
                            mSelectedModifierButtonTagValue = v.getTag().toString();
                            //10232024
                            switch (buttonColor) {
                                case "R" : {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_red_highlighted);
                                    break;
                                }
                                case "B" : {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_blue_highlighted);
                                    break;
                                }
                                case "G" : {
                                    v.setBackgroundResource(R.drawable.button_selector_modifierbutton_gray_highlighted);
                                    break;
                                }
                            }

                            thisButton.setTextColor(Color.WHITE);

                            //deselect all other buttons
                            for(int i=0; i < modifierButtonArray.size(); i++){
                                if(modifierButtonArray.get(i) != v){
                                    modifierButtonArray.get(i).setBackgroundResource(R.drawable.button_selector_modifierbutton_gray);
                                    modifierButtonArray.get(i).setTextColor(Color.BLACK);
                                }
                            }
                        }

                        //11152024 update selected option button if modifier is changed
                        mExpandableListAdapter.notifyDataSetChanged();
                    }
                });

                modifierButtonArray.add(subNewBtn);
                optionBtnListLinearLayout.addView(subNewBtn);
            }


        } else {
            mCombineType = "B";

            modifierButtonLayout1.setVisibility(View.GONE);
            modifierButtonLayout2.setVisibility(View.GONE);

            combineLn1.setVisibility(View.GONE);
            combineLn1.setVisibility(View.GONE);
        }
    }

    private void setOptionAdditional(String paramOpenType) {
        int fontsize1 = 20;
        int fontsize2 = 22;
        if (isEloPro) {
            fontsize1 = 20;
        } else {
            fontsize1 = 14;
        }

        // 뷰 추가전 먼저 초기화(삭제)한다.
        optionaddPopupOAListLinearLayout.removeAllViews();


        int btnHeight = 80;
        if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                btnHeight = 55;
            }
        } else {
            if (GlobalMemberValues.thisTabletRealWidth < 800) {
                btnHeight = 55;
            }
        }

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newLnLayoutParams.setMargins(0, 10, 0, 10);

        LinearLayout.LayoutParams newLnLayoutParams2
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newLnLayoutParams2.setMargins(0, 10, 0, 10);

        LinearLayout.LayoutParams optionLnLayoutParams1
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        optionLnLayoutParams1.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams statusLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusLnLayoutParams.setMargins(0, 0, 0, 20);

        LinearLayout.LayoutParams statusLnLayoutParamsSub
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusLnLayoutParamsSub.setMargins(0, 0, 0, 26);

        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
        LinearLayout.LayoutParams statusTvLayoutParams2
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusTvLayoutParams2.setMargins(0, 0, 0, 0);

        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
        LinearLayout.LayoutParams statusTvLayoutParams3
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
        statusTvLayoutParams3.setMargins(10, 10, 10, 0);

        // Option Item Button
        LinearLayout.LayoutParams optionItemButtnParams
                = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        optionItemButtnParams.setMargins(0, 0, 10, 0);



        int optionCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option where svcidx = " + mServiceIdx + " and optionuseyn = 'Y' "));
        int additionalCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_additional where svcidx = " + mServiceIdx + " and adduseyn = 'Y'  "));

        if (optionCount > 0 || additionalCount > 0) {
            String strQuery = "";

            // Option 이 있을 때
            if (optionCount > 0) {

                optionLnArr = new LinearLayout[optionCount];

                int tempOptionCount = 0;

                String[] strArrModifierOption = dataAtSqlite.getModifierOption(mServiceIdx, optionCount);

                //jj
                listDataHeader = new ArrayList<String[]>();
                listDataChild = new HashMap<Integer, List<String[]>>();

                ArrayList<String> optionDataArrayList = new ArrayList<String>();

                for (int moi = 0; moi < strArrModifierOption.length; moi++) {
                    optionDataArrayList.add(moi, strArrModifierOption[moi]);

                    String[] strModifierOption = strArrModifierOption[moi].split(GlobalMemberValues.STRSPLITTER1);
                    listDataHeader.add(strArrModifierOption[moi].split(GlobalMemberValues.STRSPLITTER1));
                    List<String[]> child = new ArrayList<String[]>();

                    String getOptionIdx = strModifierOption[0];
                    String[] strArrModifierOptionItem = dataAtSqlite.getModifierOptionItem(getOptionIdx);

                    if (strArrModifierOptionItem != null && strArrModifierOptionItem.length > 0) {
                        for (int moii = 0; moii < strArrModifierOptionItem.length; moii++) {

                            String[] strModifierOptionItem = strArrModifierOptionItem[moii].split(GlobalMemberValues.STRSPLITTER1);
                            child.add(strModifierOptionItem); // item name
                        }
                    }
                    listDataChild.put(moi,child);
                }

                mExpandablelistView = (ExpandableListView) findViewById(R.id.expandablelistview);
                // setting list adapter
                mExpandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
                mExpandablelistView.setAdapter(mExpandableListAdapter);

                for (int i = 0; i < listDataHeader.size(); i++){
                    String tempValue = optionDataArrayList.get(i);
                    String[] strModifierOption = tempValue.split(GlobalMemberValues.STRSPLITTER1);
                    GlobalMemberValues.logWrite(TAG + "JJJ3", "value : " + tempValue + "\n");

                    String tempAutoViewYN = strModifierOption[4];
                    if (GlobalMemberValues.isStrEmpty(tempAutoViewYN)) {
                        tempAutoViewYN = "N";
                    }

                    if (GlobalMemberValues.is_customerMain){
                        mExpandablelistView.expandGroup(i);
                    } else {
                        if (tempAutoViewYN == "Y" || tempAutoViewYN.equals("Y")) {
                            mExpandablelistView.expandGroup(i);
                        } else {
                            mExpandablelistView.collapseGroup(i);
                        }
                    }



                }

                mExpandablelistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        // 이부분이 헤더부분.
                        return false; // This way the expander cannot be collapsed
                    }
                });

                mExpandableListAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean checkRequiredOption() {
        boolean returnValue = true;

        if (mSvcIdxInRequiredOptions != null) {
            String strQuery = "";

            strQuery = " select count(idx) from salon_storeservice_option " +
                    " where svcIdx = '" + mServiceIdx + "' " +
                    " and optionuseyn = 'Y' " +
                    " and optionpilsuyn = 'Y' ";
            int tempRequiredCount = GlobalMemberValues.getIntAtString(
                    dbInit.dbExecuteReadReturnString(strQuery)
            );

            GlobalMemberValues.logWrite(TAG + "1", "tempRequiredCount : " + tempRequiredCount + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "mSvcIdxInRequiredOptions.size() : " + mSvcIdxInRequiredOptions.size() + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "mSvcIdxInRequiredOptions : " + mSvcIdxInRequiredOptions.toString() + "\n");

            if (tempRequiredCount > 0) {
                if (mSvcIdxInRequiredOptions.size() == 0) {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Choose option", "Close");
                    returnValue = false;
                } else {
                    String msgContents = "";
                    Cursor optionCursor;

                    strQuery = " select idx, optionname, minval, maxval, maxsumval from salon_storeservice_option " +
                            " where svcIdx = '" + mServiceIdx + "' " +
                            " and optionuseyn = 'Y' " +
                            " and optionpilsuyn = 'Y' " +
                            " order by sortnum asc, optionname asc, idx desc";
                    optionCursor = dbInit.dbExecuteRead(strQuery);
                    while (optionCursor.moveToNext()) {
                        String getOptionIdx = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(0), 1);
                        String getOptionName = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(1), 1);
                        int getMinval = optionCursor.getInt(2);
                        int getMaxval = optionCursor.getInt(3);
                        int getMaxsumval = optionCursor.getInt(4);

//                        GlobalMemberValues.logWrite("jjjmodlogg", "getOptionName  : " + getOptionName + "\n");
//                        GlobalMemberValues.logWrite("jjjmodlogg", "getMinval  : " + getMinval + "\n");
//                        GlobalMemberValues.logWrite("jjjmodlogg", "getMaxval  : " + getMaxval + "\n");
//                        GlobalMemberValues.logWrite("jjjmodlogg", "getMaxsumval  : " + getMaxsumval + "\n\n\n");

                        if (mSvcIdxInRequiredOptions.indexOf(getOptionIdx) == -1) {
                            msgContents = "Choose <" + getOptionName + ">'s item";
                            GlobalMemberValues.displayDialog(mContext, "Warning", msgContents, "Close");
                            returnValue = false;
                            break;
                        } else {
                            for (String tempOptionIdx : mSvcIdxInRequiredOptions) {
                                int[] getValues = getTotalandCount(getOptionIdx);

                                int tempSumQty = getValues[0];
//                                GlobalMemberValues.logWrite("jjjmodlog", "tempSumQty  : " + tempSumQty + "\n");
                                if (tempSumQty > getMaxsumval) {
                                    msgContents = " in <" + getOptionName + ">, You can select up to " + getMaxsumval + " in total";
                                    GlobalMemberValues.displayDialog(mContext, "Warning",
                                            msgContents, "Close");
                                    returnValue = false;
                                    break;
                                }

                                int tempQty = getValues[1];
//                                GlobalMemberValues.logWrite("jjjmodlog", "tempQty  : " + tempQty + "\n");
                                if (tempQty > getMaxval) {
                                    msgContents = "You can select up to " + getMaxval + " in <" + getOptionName + ">";
                                    GlobalMemberValues.displayDialog(mContext, "Warning",
                                            msgContents, "Close");
                                    returnValue = false;
                                    break;
                                }

                                if (tempQty < getMinval) {
                                    msgContents = "You have to select at least " + getMinval + " item(s) in <" + getOptionName + ">";
                                    GlobalMemberValues.displayDialog(mContext, "Warning",
                                            msgContents, "Close");
                                    returnValue = false;
                                    break;
                                }
                            }
                        }
                    }
                    optionCursor.close();
                }
            }
        }

        return returnValue;
    }

    public int[] getMinMaxMaxsumVal(String paramOptionIdx) {
        int[] returnValue = new int[3];
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = 0;
        }

        String strQuery = " select count(idx) from salon_storeservice_option " +
                " where idx = '" + paramOptionIdx + "' " +
                " and optionuseyn = 'Y' ";
        int tempCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));

        if (tempCnt > 0) {
            Cursor optionCursor;
            strQuery = " select minval, maxval, maxsumval from salon_storeservice_option " +
                    " where idx = '" + paramOptionIdx + "' " +
                    " and optionuseyn = 'Y' ";
            optionCursor = dbInit.dbExecuteRead(strQuery);
            if (optionCursor.getCount() > 0 && optionCursor.moveToFirst()) {
                int getMinval = optionCursor.getInt(0);
                int getMaxval = optionCursor.getInt(1);
                int getMaxsumval = optionCursor.getInt(2);

                returnValue[0] = getMinval;
                returnValue[1] = getMaxval;
                returnValue[2] = getMaxsumval;
            }
            optionCursor.close();
        }
        return returnValue;
    }

    public int[] getTotalandCount(String paramOptionIdx) {
        String strQuery = "";

        strQuery = " select sum(A.qty) from temp_salecart_optionadd_imsi A " +
                " left join salon_storeservice_option B on A.optioncategoryidx = B.idx " +
                " left join salon_storeservice_option_item C on A.optionitemidx = C.idx " +
                " where  A.modifiercode = '" + mModifierCode + "' and optioncategoryidx = '" + paramOptionIdx + "' ";
        int tempSumQty = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(strQuery)
        );

        strQuery = " select count(A.idx) from temp_salecart_optionadd_imsi A " +
                " left join salon_storeservice_option B on A.optioncategoryidx = B.idx " +
                " left join salon_storeservice_option_item C on A.optionitemidx = C.idx " +
                " where  A.modifiercode = '" + mModifierCode + "' and optioncategoryidx = '" + paramOptionIdx + "' ";
        int tempQty = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(strQuery)
        );

        int[] returnValue = new int[2];
        returnValue[0] = tempSumQty;
        returnValue[1] = tempQty;

        return returnValue;
    }

    private void setAddModifier() {
        // min, max, maxsum 체크 ============================================================
        // 먼저 선택한 option item 이 이미 선택된 아이템인지 확인한다.

        //11152024 if mCombineType == A (modifier + option), search by currently selected modifier too.
        //ie. if option limit is 4 max, modifierA + option can go up to 4, AND modifierB + option can go up to 4.
        int tempOptionItemCnt = 0;
        if (Objects.equals(mCombineType, "A")){
            //11152024 mSelectedModifierButtonTagValue is in format of "Premium-JJJ-1-JJJ-B-JJJ-N"
            String modifierName = mSelectedModifierButtonTagValue.split("-JJJ-")[0];

            tempOptionItemCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select count(*) from temp_salecart_optionadd_imsi " +
                            " where modifiercode = '" + mModifierCode + "' " +
                            " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' " +
                            " and modifiername = '" + modifierName + "' "
            ));
        } else {
            tempOptionItemCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select count(*) from temp_salecart_optionadd_imsi " +
                            " where modifiercode = '" + mModifierCode + "' " +
                            " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' " +
                            " and optionitemidx = '" + mSelectedOptionItemIdxValue + "' "
            ));
        }


        String msgContents = "";

        String getOptionName = mSelectedOptionCategoryNameValue;

        GlobalMemberValues.logWrite("jjjmodlog3", "option idx  : " + mSelectedOptionCategoryIdxValue + "\n");

        int[] getValues = getTotalandCount(mSelectedOptionCategoryIdxValue);
        int tempSumQty = getValues[0] + 1;
        int tempQty = getValues[1] + 1;

        int[] getOptionValues = getMinMaxMaxsumVal(mSelectedOptionCategoryIdxValue);
        int getMinval = getOptionValues[0];
        int getMaxval = getOptionValues[1];
        int getMaxsumval = getOptionValues[2];

        //11152024 if modifier + option format use different comparison to check a options max limit
        if (Objects.equals(mCombineType, "A")){
            if (tempOptionItemCnt > getMaxsumval) {
                msgContents = " in <" + getOptionName + ">, You can select up to " + getMaxsumval + " in total";
                GlobalMemberValues.displayDialog(mContext, "Warning",
                        msgContents, "Close");
                return;
            }
        } else {
            if (tempOptionItemCnt == 0 && tempSumQty > getMaxsumval) {
                msgContents = " in <" + getOptionName + ">, You can select up to " + getMaxsumval + " in total";
                GlobalMemberValues.displayDialog(mContext, "Warning",
                        msgContents, "Close");
                return;
            }

//                                GlobalMemberValues.logWrite("jjjmodlog", "tempQty  : " + tempQty + "\n");
            if (tempOptionItemCnt == 0 && tempQty > getMaxval) {
                msgContents = "You can select up to " + getMaxval + " in <" + getOptionName + ">";
                GlobalMemberValues.displayDialog(mContext, "Warning",
                        msgContents, "Close");
                return;
            }
        }

        // modi 재선택시 아이템 선택취소하기위해서 주석처리
//        if (tempOptionItemCnt > 0 && tempSumQty > getMaxsumval) {
//            msgContents = " in <" + getOptionName + ">, You can select up to " + getMaxsumval + " in total";
//            GlobalMemberValues.displayDialog(mContext, "Warning",
//                    msgContents, "Close");
//            return;
//        }
//
////                                GlobalMemberValues.logWrite("jjjmodlog", "tempQty  : " + tempQty + "\n");
//        if (tempOptionItemCnt == 0 && tempQty > getMaxval) {
//            msgContents = "You can select up to " + getMaxval + " in <" + getOptionName + ">";
//            GlobalMemberValues.displayDialog(mContext, "Warning",
//                    msgContents, "Close");
//            return;
//        }
        // modi 재선택시 아이템 선택취소하기위해서 주석처리


//        if (tempQty < getMinval) {
//            msgContents = "You have to select at least " + getMinval + " item(s) in <" + getOptionName + ">";
//            GlobalMemberValues.displayDialog(mContext, "Warning",
//                    msgContents, "Close");
//            return;
//        }
        // =================================================================================

        String selectedModifierButtonName = "";
        String selectedModifierButtonPrice = "";
        String selectedModifierButtonColor = "";
        String selectedModifierButtonNamePrice = "";
        String selectedModifierButtonIgnorePrice = "";
        if (!GlobalMemberValues.isStrEmpty(mSelectedModifierButtonTagValue)) {
            String selectedModifierButtonArr[] = null;
            selectedModifierButtonArr = mSelectedModifierButtonTagValue.split("-JJJ-");
            selectedModifierButtonName = selectedModifierButtonArr[0];
            selectedModifierButtonPrice = selectedModifierButtonArr[1];
            selectedModifierButtonColor = selectedModifierButtonArr[2];
            selectedModifierButtonIgnorePrice = selectedModifierButtonArr[3];

            GlobalMemberValues.logWrite("modifierbtnvaluelog", "mSelectedModifierButtonTagValue  : " + mSelectedModifierButtonTagValue + "\n");

            if (GlobalMemberValues.isStrEmpty(selectedModifierButtonIgnorePrice)) {
                selectedModifierButtonIgnorePrice = "N";
            }

            String tempPriceStr = "";
            double tempPrice = GlobalMemberValues.getDoubleAtString(selectedModifierButtonPrice);
            if (tempPrice < 0) {
                tempPriceStr = "(-" + (tempPrice * -1) + ")";
            } else {
                if (tempPrice > 0) {
                    tempPriceStr = "(+" + tempPrice + ")";
                }
            }
            if (selectedModifierButtonIgnorePrice == "Y" || selectedModifierButtonIgnorePrice.equals("Y")) {
                tempPriceStr = "(Ignore Pri.)";
            }

            selectedModifierButtonNamePrice = selectedModifierButtonName + tempPriceStr;
        }

        String selectedOptionAddButtonName = "";
        String selectedOptionAddButtonPrice = "";
        String selectedOptionAddButtonNamePrice = "";
        if (!GlobalMemberValues.isStrEmpty(mSelectedOptionAddButtonTagValue)) {
            String selectedOptionAddButtonArr[] = null;
            selectedOptionAddButtonArr = mSelectedOptionAddButtonTagValue.split("-JJJ-");
            selectedOptionAddButtonName = selectedOptionAddButtonArr[0];
            selectedOptionAddButtonPrice = selectedOptionAddButtonArr[1];

            if (selectedModifierButtonIgnorePrice == "Y" || selectedModifierButtonIgnorePrice.equals("Y")) {
                selectedOptionAddButtonPrice = "0";
            }
            GlobalMemberValues.logWrite("modifierbtnvaluelog", "selectedModifierButtonIgnorePrice  : " + selectedModifierButtonIgnorePrice + "\n");
            GlobalMemberValues.logWrite("modifierbtnvaluelog", "selectedOptionAddButtonPrice  : " + selectedOptionAddButtonPrice + "\n");

            String tempPriceStr = "";
            if (selectedModifierButtonIgnorePrice == "Y" || selectedModifierButtonIgnorePrice.equals("Y")) {
                tempPriceStr = "";
            } else {
                double tempPrice = GlobalMemberValues.getDoubleAtString(selectedOptionAddButtonPrice);
                if (tempPrice < 0) {
                    tempPriceStr = "(-" + (tempPrice * -1) + ")";
                } else {
                    if (tempPrice > 0) {
                        tempPriceStr = "(+" + tempPrice + ")";
                    }
                }
            }

            selectedOptionAddButtonNamePrice = selectedOptionAddButtonName + tempPriceStr;
        }

        String tempSelectedModOptionAddName = "";

        if (!GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                && !GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
            tempSelectedModOptionAddName = selectedOptionAddButtonNamePrice + " + " + selectedModifierButtonNamePrice;
        } else {
            if (!GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                    && GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
                tempSelectedModOptionAddName = selectedModifierButtonNamePrice;
            }
            if (GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                    && !GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
                tempSelectedModOptionAddName = selectedOptionAddButtonNamePrice;
            }
        }

        modifierValueTv.setText(tempSelectedModOptionAddName);


        saveOptionAdd();


    }

    private void setSelectedModifierOptionAdd() {
        // 뷰 추가전 먼저 초기화(삭제)한다.
//        optionAddCartListLinearLayout.removeAllViews();

        int btnHeight = 70;
        if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                btnHeight = 45;
            }
        } else {
            if (GlobalMemberValues.thisTabletRealWidth < 800) {
                btnHeight = 45;
            }
        }

        Cursor cartCursor;

        String sortQuery = "";
        //11152024 if modifer + option setting, sort alphabetically
        if(mCombineType == "A"){
            sortQuery = " order by A.modifiername asc, B.sortnum";
        } else {
            sortQuery = " order by B.sortnum asc";
        }

        String strQuery = " select A.idx, A.modifiername, A.optionaddname, A.modifieroptionaddfullname, A.modifierprice, A.optionaddprice, A.optioncategoryidx, A.optioncategoryname, A.qty " +
                " from temp_salecart_optionadd_imsi A " +
                " left join salon_storeservice_option B on A.optioncategoryidx = B.idx " +
                " left join salon_storeservice_option_item C on A.optionitemidx = C.idx " +
                //" where  holdcode = '" + paramHoldCode + "' and svcIdx = '" + paramSvcIdx + "' " +
                " where  A.modifiercode = '" + mModifierCode + "' " +
                sortQuery;
        cartCursor = dbInit.dbExecuteRead(strQuery);

        String tempPrevOptCateIdx = "";
        double totalAddPrice = 0.0;
        int tempCount = 0;
        List<String[]> arr_strings = new ArrayList<String[]>();
        while (cartCursor.moveToNext()) {

            String getIdx = "";
            String getModifiername = "";
            String getOptionaddname = "";
            String getModifieroptionaddfullname = "";
            String getModifierprice = "";
            String getOptionaddprice = "";
            String getOptCateIdx = "";
            String getOptCateName = "";
            String getQty = "";

            getIdx = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(0), 1);
            getModifiername = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(1), 1);
            getOptionaddname = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(2), 1);
            getModifieroptionaddfullname = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(3), 1);
            getModifierprice = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(4), 1);
            getOptionaddprice = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(5), 1);
            getOptCateIdx = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(6), 1);
            getOptCateName = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(7), 1);
            getQty = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(8), 1);

            if (GlobalMemberValues.isStrEmpty(getQty)) {
                getQty = "1";
            }

            arr_strings.add(new String[]{
                    getIdx,
                    getModifiername,
                    getOptionaddname,
                    getModifieroptionaddfullname,
                    getModifierprice,
                    getOptionaddprice,
                    getOptCateIdx,
                    getOptCateName,
                    getQty
            });

            GlobalMemberValues.logWrite("modaddminuslog", "getQty : " + getQty + "\n");
//            arr_strings.add()
            double tempAddPrice = (GlobalMemberValues.getDoubleAtString(getModifierprice) + GlobalMemberValues.getDoubleAtString(getOptionaddprice)) * GlobalMemberValues.getIntAtString(getQty);
            GlobalMemberValues.logWrite("modaddminuslog", "tempAddPrice : " + tempAddPrice + "\n");
            totalAddPrice += tempAddPrice;

            String getModifierOptionName = "";
            if (!GlobalMemberValues.isStrEmpty(getModifiername)
                    && !GlobalMemberValues.isStrEmpty(getOptionaddname)) {
                getModifierOptionName = getModifiername + " " + getOptionaddname;
            } else {
                if (!GlobalMemberValues.isStrEmpty(getModifiername)
                        && GlobalMemberValues.isStrEmpty(getOptionaddname)) {
                    getModifierOptionName = getModifiername;
                }
                if (GlobalMemberValues.isStrEmpty(getModifiername)
                        && !GlobalMemberValues.isStrEmpty(getOptionaddname)) {
                    getModifierOptionName = getOptionaddname;
                }
            }

            String tempModifierqtyviewyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select modifierqtyviewyn from salon_storestationsettings_system "
            );
            if (GlobalMemberValues.isStrEmpty(tempModifierqtyviewyn)) {
                tempModifierqtyviewyn = "Y";
            }

            //GlobalMemberValues.logWrite("modifierqtyynlog", "tempModifierqtyviewyn : " + tempModifierqtyviewyn + "\n");

            if (GlobalMemberValues.getIntAtString(getQty) > 0 && (tempModifierqtyviewyn == "Y" || tempModifierqtyviewyn.equals("Y"))) {
                getModifierOptionName += " (" + getQty + "ea)";
            }

            String tempPriceStr = "";
            // jihun 082820
            tempAddPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempAddPrice,"2"));
            if (tempAddPrice < 0) {
                tempPriceStr = " (-" + (tempAddPrice * -1) + ")";
            } else {
                if (tempAddPrice > 0) {
                    tempPriceStr = " (+" + tempAddPrice + ")";
                }
            }

            if (tempCount > 0) {
                mModifierIdx += "," + getIdx;
                mOptionTxt += ", " + getModifierOptionName + tempPriceStr;
            } else {
                mModifierIdx = getIdx;
                mOptionTxt = getModifierOptionName + tempPriceStr;
            }

            tempCount++;
        }
        cartCursor.close();
//        optionAddCartListLinearLayout.addView(newLn);


        // jihun add

        OptionAddCartListLinearLayout_list_adapter optionAddCartListLinearLayout_list_adapter = new OptionAddCartListLinearLayout_list_adapter(mContext, arr_strings);
        optionAddCartListLinearLayout_list.setAdapter(optionAddCartListLinearLayout_list_adapter);
        optionAddCartListLinearLayout_list_adapter.notifyDataSetChanged();

        if (mTempDeletedPositionInCart > -1) {
            optionAddCartListLinearLayout_list.setSelection((mTempDeletedPositionInCart));
        } else {
            optionAddCartListLinearLayout_list.setSelection(arr_strings.size());
        }
        mTempDeletedPositionInCart = -1;

        mAddPrice = totalAddPrice;
        setPrice(mNowPrice, totalAddPrice);

        GlobalMemberValues.logWrite("modaddminuslog", "totalAddPrice : " + totalAddPrice + "\n");
        GlobalMemberValues.logWrite("modaddminuslog", "mAddPrice : " + mAddPrice + "\n");
    }

    private void saveOptionAdd() {
        String returnValue = "";

        if (GlobalMemberValues.isStrEmpty(mSelectedOptionAddButtonTagValue)) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Select Option", "Close");
            return;
        }

        String checkOptionItemQuery = "";
        int checkItemCount = 0;

        //01102024
        //Get the name of the extra modifier for the option that is about to be added.
        String[] selectedModifierButtonValueArr = null;
        selectedModifierButtonValueArr = mSelectedModifierButtonTagValue.split("-JJJ-");
        String selectedModifierName = "";
        if (selectedModifierButtonValueArr != null){
            selectedModifierName = selectedModifierButtonValueArr[0];
        }


        // 한 옵션에서 중복되어 선택된 것이 있는지 확인
        // 있으면 삭제
        checkOptionItemQuery = " select idx from temp_salecart_optionadd_imsi " +
                //" where holdcode = '" + MainMiddleService.mHoldCode + "' and svcIdx = '" + mServiceIdx + "' " +
                " where modifiercode = '" + mModifierCode + "' " +
                //01102024 also check if the option has the same modifier
                " and modifiername = '" + selectedModifierName + "' " +
                " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' and optionitemidx = '" + mSelectedOptionItemIdxValue + "' ";
        String tempImsiIdx = dbInit.dbExecuteReadReturnString(checkOptionItemQuery);
        if (!GlobalMemberValues.isStrEmpty(tempImsiIdx)) {
            //GlobalMemberValues.displayDialog(mContext, "Warning", "You have already selected this item", "Close");
            //return;
            // jihun park addup 12192019 - 아이템 중복 선택시 실행되는 부분으로 확인
            b_itemRemoving = true;
            GlobalMemberValues.logWrite("modifiertestlog", "여기실행..");

            // 아이템 삭제.
            setInitDbItem(tempImsiIdx);
//            setAddMInusDbItem(tempImsiIdx, "A");

        } else {
            //01102024  Check how many OPTIONS ITEMS are currently selected for the OPTION
            //11152024 if mCombineType == "A", modifier + option setting also check for modifier selected.
            String checkOptionItemCountQuery = "";
            if (Objects.equals(mCombineType, "A")){
                String modifierName = "";
                if(!mSelectedModifierButtonTagValue.equals("")){
                    modifierName = mSelectedModifierButtonTagValue.split("-JJJ-")[0];
                }

                checkOptionItemCountQuery =
                        " select count(*) from temp_salecart_optionadd_imsi " +
                                " where modifiercode = '" + mModifierCode + "' " +
                                " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' " +
                                " and modifiername = '" + modifierName + "' ";
            } else {
                checkOptionItemCountQuery = " select COUNT(idx) from temp_salecart_optionadd_imsi " +
                        " where modifiercode = '" + mModifierCode + "' " +
                        " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "'";

            }

            String tempOptionItemCount = dbInit.dbExecuteReadReturnString(checkOptionItemCountQuery);

            String checkOptionItemLimitQuery = " select maxval from salon_storeservice_option " +
                    " where idx = '" + mSelectedOptionCategoryIdxValue + "' " +
                    " and optionuseyn = 'Y'";
            Cursor optionCursor = dbInit.dbExecuteRead(checkOptionItemLimitQuery);
            int tempOptionItemLimitCount = 100;
            while (optionCursor.moveToNext()) {
                tempOptionItemLimitCount = optionCursor.getInt(0);
            }

            if(Integer.parseInt(tempOptionItemCount) >= tempOptionItemLimitCount){
                String msgContents = "in <" + selectedModifierName + ">, You can select up to " + tempOptionItemLimitCount + " in total";
                GlobalMemberValues.displayDialog(mContext, "Warning",
                        msgContents, "Close");
                return;
            }


            // single 타입의 옵션에서 멀티로 선택하는지 체크
            String tempOptiontype = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select optiontype from salon_storeservice_option where idx = '" + mSelectedOptionCategoryIdxValue + "' "
            );
            if (tempOptiontype == "S" || tempOptiontype.equals("S")) {      // 단일 선택일 경우
                /**
                 checkOptionItemQuery = " select count(idx) from temp_salecart_optionadd_imsi " +
                 //" where holdcode = '" + MainMiddleService.mHoldCode + "' and svcIdx = '" + mServiceIdx + "' " +
                 " where modifiercode = '" + mModifierCode + "' " +
                 " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' ";
                 checkItemCount = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(checkOptionItemQuery));
                 if (checkItemCount > 0) {
                 //GlobalMemberValues.displayDialog(mContext, "Warning", "You can only select one of these modifier", "Close");
                 //return;

                 }
                 **/
                checkOptionItemQuery = " select idx from temp_salecart_optionadd_imsi " +
                        //" where holdcode = '" + MainMiddleService.mHoldCode + "' and svcIdx = '" + mServiceIdx + "' " +
                        " where modifiercode = '" + mModifierCode + "' " +
                        " and optioncategoryidx = '" + mSelectedOptionCategoryIdxValue + "' ";
                String tempItemIdx = dbInit.dbExecuteReadReturnString(checkOptionItemQuery);
                if (!GlobalMemberValues.isStrEmpty(tempItemIdx)) {
                    setInitDbItem(tempItemIdx);
                }
            } else {                                                        // 멀티 선택일 경우
            }

            String selectedModifierButtonName = "";
            String selectedModifierButtonPrice = "";
            String selectedModifierButtonColor = "";
            String selectedModifierButtonIgnorePrice = "";

            String selectedModifierButtonNamePrice = "";

            if (!GlobalMemberValues.isStrEmpty(mSelectedModifierButtonTagValue)) {
                String selectedModifierButtonArr[] = null;
                selectedModifierButtonArr = mSelectedModifierButtonTagValue.split("-JJJ-");
                selectedModifierButtonName = selectedModifierButtonArr[0];
                selectedModifierButtonPrice = selectedModifierButtonArr[1];
                selectedModifierButtonColor = selectedModifierButtonArr[2];
                selectedModifierButtonIgnorePrice = selectedModifierButtonArr[3];

                if (selectedModifierButtonIgnorePrice == "Y" || selectedModifierButtonIgnorePrice.equals("Y")) {
                    selectedModifierButtonPrice = "0";
                }

                String tempPriceStr = "";
                double tempPrice = GlobalMemberValues.getDoubleAtString(selectedModifierButtonPrice);
                if (tempPrice < 0) {
                    tempPriceStr = "(-" + (tempPrice * -1) + ")";
                } else {
                    if (tempPrice > 0) {
                        tempPriceStr = "(+" + tempPrice + ")";
                    }
                }

                selectedModifierButtonNamePrice = selectedModifierButtonName + tempPriceStr;
            }

            String selectedOptionAddButtonName = "";
            String selectedOptionAddButtonPrice = "";
            String selectedOptionAddButtonNamePrice = "";
            if (!GlobalMemberValues.isStrEmpty(mSelectedOptionAddButtonTagValue)) {
                String selectedOptionAddButtonArr[] = null;
                selectedOptionAddButtonArr = mSelectedOptionAddButtonTagValue.split("-JJJ-");
                selectedOptionAddButtonName = selectedOptionAddButtonArr[0];
                selectedOptionAddButtonPrice = selectedOptionAddButtonArr[1];

                if (selectedModifierButtonIgnorePrice == "Y" || selectedModifierButtonIgnorePrice.equals("Y")) {
                    selectedOptionAddButtonPrice = "0";
                }

                String tempPriceStr = "";
                double tempPrice = GlobalMemberValues.getDoubleAtString(selectedOptionAddButtonPrice);
                if (tempPrice < 0) {
                    tempPriceStr = "(-" + (tempPrice * -1) + ")";
                } else {
                    if (tempPrice > 0) {
                        tempPriceStr = "(+" + tempPrice + ")";
                    }
                }

                selectedOptionAddButtonNamePrice = selectedOptionAddButtonName + tempPriceStr;
            }

            String tempSelectedModOptionAddName = "";

            if (!GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                    && !GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
                tempSelectedModOptionAddName = selectedModifierButtonNamePrice + " + " + selectedOptionAddButtonNamePrice;
            } else {
                if (!GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                        && GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
                    tempSelectedModOptionAddName = selectedModifierButtonNamePrice;
                }
                if (GlobalMemberValues.isStrEmpty(selectedModifierButtonName)
                        && !GlobalMemberValues.isStrEmpty(selectedOptionAddButtonName)) {
                    tempSelectedModOptionAddName = selectedOptionAddButtonNamePrice;
                }
            }

            // 트랜잭션 처리후 결과값 저장 객체
            String returnResult = "";
            // DB 쿼리저장용 백터 객체 생성
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "";

            strQuery = " insert into temp_salecart_optionadd_imsi (" +
                    " holdcode, sidx, stcode, svcIdx, " +
                    " modifiername, optionaddname, modifieroptionaddfullname, modifierprice, optionaddprice, " +
                    " optioncategoryidx, optionitemidx, optioncategoryname, " +
                    " modifiercode " +
                    " ) values ( " +

                    " '" + MainMiddleService.mHoldCode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + mServiceIdx + "', " +

                    " '" + GlobalMemberValues.getDBTextAfterChecked(selectedModifierButtonName,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(selectedOptionAddButtonName,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempSelectedModOptionAddName,0) + "', " +
                    " '" + GlobalMemberValues.getDoubleAtString(selectedModifierButtonPrice) + "', " +
                    " '" + GlobalMemberValues.getDoubleAtString(selectedOptionAddButtonPrice) + "', " +

                    " '" + mSelectedOptionCategoryIdxValue + "', " +
                    " '" + mSelectedOptionItemIdxValue + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSelectedOptionCategoryNameValue,0) + "', " +

                    " '" + mModifierCode + "' " +

                    " ) ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                return;
            } else {
                if (!GlobalMemberValues.isStrEmpty(mSelectedOptionItemIdxValue)) {
                    TextView mTempButton = (TextView)findViewById(GlobalMemberValues.getIntAtString(mSelectedOptionItemIdxValue));
                    if (mTempButton != null) {
                        if (GlobalMemberValues.is_customerMain){
                            mTempButton.setBackgroundResource(R.drawable.roundlayout_button_custom_option_selected);
                            mTempButton.setTextColor(Color.parseColor("#ff8137"));
                        } else {
                            mTempButton.setBackgroundResource(R.drawable.button_selector_optionbutton_selected);
                            mTempButton.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                }

                //11192024 keep selected modifier selected even after adding modifier + option item.
                //setSelectedValueInit();
                setSelectedModifierOptionAdd();
            }
        }
    }

    private void setPrice(double paramNowPrice, double paramAddPrice) {
        String tempQty = qtyTv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempQty)) {
            tempQty = "1";
        }
        int qty = GlobalMemberValues.getIntAtString(tempQty);

        paramNowPrice = paramNowPrice * qty;
        paramAddPrice = paramAddPrice * qty;

        optionaddPopupPriceTextView.setText(GlobalMemberValues.getCommaStringForDouble(paramNowPrice + ""));
        optionaddPopupAddPriceTextView.setText(GlobalMemberValues.getCommaStringForDouble(paramAddPrice + ""));
        optionaddPopupAmountTextView.setText(GlobalMemberValues.getCommaStringForDouble((paramNowPrice + paramAddPrice) + ""));
    }

    private void setInitDbItem(String paramIdx) {
        // 삭제전에 option item idx 값을 가져온다..
        String tempOptinoItemIdx = dbInit.dbExecuteReadReturnString(
                " select optionitemidx from temp_salecart_optionadd_imsi where idx = '" + paramIdx +"' ");

        // 삭제전에 option idx 값을 가져온다..
        String tempOptinoIdx = dbInit.dbExecuteReadReturnString(
                " select optioncategoryidx from temp_salecart_optionadd_imsi where idx = '" + paramIdx +"' ");

        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";
        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strDeleteQuery = "";

        strDeleteQuery = "delete from temp_salecart_optionadd_imsi " +
                " where idx = '" + paramIdx +"' ";
        strInsertQueryVec.addElement(strDeleteQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            return;
        } else {
            if (!GlobalMemberValues.isStrEmpty(tempOptinoItemIdx)) {
                TextView mTempButton = (TextView)findViewById(GlobalMemberValues.getIntAtString(tempOptinoItemIdx));
                if (mTempButton != null) {
                    mTempButton.setBackgroundResource(R.drawable.button_selector_optionbutton);
                    mTempButton.setTextColor(Color.parseColor("#1356ff"));
                }
            }

            if (!GlobalMemberValues.isStrEmpty(tempOptinoIdx)) {
                int categoryIdxCount = GlobalMemberValues.getIntAtString(
                        MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select count(idx) from temp_salecart_optionadd_imsi " +
                                        " where modifiercode = '" + mModifierCode + "' and optioncategoryidx = '" + tempOptinoIdx + "' "
                        )
                );
                GlobalMemberValues.logWrite("optioncategorylog", "categoryIdxCount : " + categoryIdxCount + "\n");
                if (categoryIdxCount == 0) {
                    //GlobalMemberValues.logWrite("chkjjjlog", "여기2" + "\n");
                    mSvcIdxInRequiredOptions.remove(tempOptinoIdx);
                }
            }
            setSelectedModifierOptionAdd();
        }
    }

    private void setAddMInusDbItem(String paramIdx, String paramType) {
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";
        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strDeleteQuery = "";

        String tempSqlQuery = "";
        if (paramType == "A" || paramType.equals("A")) {
            tempSqlQuery = " qty = qty + 1 ";
        } else {
            tempSqlQuery = " qty = qty - 1 ";
        }

        strDeleteQuery = " update temp_salecart_optionadd_imsi set " +
                tempSqlQuery +
                " where idx = '" + paramIdx +"' ";
        strInsertQueryVec.addElement(strDeleteQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            return;
        } else {
            setSelectedModifierOptionAdd();
        }
    }

    private String getDbItemQty(String paramIdx) {
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";
        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strQuery = "";

        strQuery = "select sum(qty) from temp_salecart_optionadd_imsi where optioncategoryidx = '" + paramIdx +"' and modifiercode = '" + mModifierCode + "' ";
//        strInsertQueryVec.addElement(strQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteReadReturnString(strQuery);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            return "";
        } else {
            return returnResult;
        }
    }


    private void setInitDbAll() {
        String strQuery = " select idx from temp_salecart_optionadd_imsi where  modifiercode = '" + mModifierCode + "' ";
        Cursor cartCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);

        while (cartCursor.moveToNext()) {
            String tempIdx = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(0), 1);

            setInitDbItem(tempIdx);
        }
        cartCursor.close();

        /**
         // 트랜잭션 처리후 결과값 저장 객체
         String returnResult = "";
         // DB 쿼리저장용 백터 객체 생성
         Vector<String> strInsertQueryVec = new Vector<String>();
         String strDeleteQuery = "";

         strDeleteQuery = "delete from temp_salecart_optionadd_imsi " +
         " where modifiercode = '" + mModifierCode +"' ";
         //" where holdcode = '" + MainMiddleService.mHoldCode + "' and svcIdx = '" + mServiceIdx +"' ";
         strInsertQueryVec.addElement(strDeleteQuery);

         for (String tempQuery : strInsertQueryVec) {
         GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
         }
         // 트랜잭션으로 DB 처리한다.
         returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
         if (returnResult == "N" || returnResult == "") {
         GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
         return;
         } else {
         modifierButtonInit();
         mSvcIdxInRequiredOptions.removeAllElements();
         setSelectedModifierOptionAdd();
         }
         **/
    }

    private void modifierButtonInit() {
        String strQuery = " select optionitemidx from temp_salecart_optionadd_imsi where  modifiercode = '" + mModifierCode + "' ";
        Cursor cartCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);

        while (cartCursor.moveToNext()) {
            String tempItemIdx = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(0), 1);
            GlobalMemberValues.logWrite("selecteditemidxlog", "delete idx : " + tempItemIdx + "\n");

            TextView mTempButton = (TextView)findViewById(GlobalMemberValues.getIntAtString(tempItemIdx));
            if (mTempButton != null) {
                mTempButton.setBackgroundResource(R.drawable.button_selector_optionbutton);
                mTempButton.setTextColor(Color.parseColor("#1356ff"));
            }
        }

        cartCursor.close();

    }

    private void submitAdd() {
        double dblAmount = GlobalMemberValues.getDoubleAtString(optionaddPopupAmountTextView.getText().toString());
        if (dblAmount < 0) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Invalid Price", "Close");
            return;
        }

        if (transDataFromImsi(mModifierCode, "imsi")) {
            /**
             String tempAddPrice = optionaddPopupAddPriceTextView.getText().toString();
             tempAddPrice = GlobalMemberValues.getReplaceText(tempAddPrice, ",", "");
             double dblAddPrice = GlobalMemberValues.getDoubleAtString(tempAddPrice);
             **/

            int optionaddCnt = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                    " select count(idx) from temp_salecart_optionadd_imsi where modifiercode = '" + getModifierCode + "' "));

            int optionaddCnt2 = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                    " select count(idx) from temp_salecart_optionadd where modifiercode = '" + getModifierCode + "' "));

            GlobalMemberValues.logWrite("modifierMJJJ", "optionaddCnt : " + optionaddCnt + "\n");
            GlobalMemberValues.logWrite("modifierMJJJ", "optionaddCnt2 : " + optionaddCnt2 + "\n");


            MainMiddleService.mModifierCode = mModifierCode;
            MainMiddleService.mModifierIdx = mModifierIdx;
            MainMiddleService.mAdditionalTxt1 = "";
            MainMiddleService.mAdditionalprice1 = "";
            if (getPositionInt != -1) {
                MainMiddleService.mAdditionalTxt2 = MainMiddleService.mGeneralArrayList.get(getPositionInt).additionalTxt2;
                MainMiddleService.mAdditionalprice2 = MainMiddleService.mGeneralArrayList.get(getPositionInt).additionalprice2;
            } else {
                MainMiddleService.mAdditionalTxt2 = "";
                MainMiddleService.mAdditionalprice2 = "";
            }

            MainMiddleService.mMemoToKitchen = memotokitchenEv.getText().toString();
            if (hasOptions) {
                MainMiddleService.mOptionTxt = mOptionTxt;
                MainMiddleService.mOptionPrice = mAddPrice + "";
            } else {
                MainMiddleService.mOptionTxt = "";
                MainMiddleService.mOptionPrice = "";
            }


            // common gratuity 관련
            GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();


            GlobalMemberValues.logWrite("modifierMJJJ", "mOptionTxt : " + mOptionTxt + "\n");

            if (!GlobalMemberValues.isStrEmpty(getPosition)) {
                GlobalMemberValues.logWrite(TAG + "J", "처리할 Position : " + getPositionInt + "\n");
                if (getPositionInt > -1) {
                    MainMiddleService.deleteItem(getPositionInt, false, false, "");
                }
            }

            String tempQty = qtyTv.getText().toString();
            if (GlobalMemberValues.isStrEmpty(tempQty)) {
                tempQty = "1";
            }

            makeTouchedArr(tempQty);
            MainMiddleService.animServices(MainMiddleService.mTouchedView, MainMiddleService.mTouchedArr);

            closeModifierPage();

            // common gratuity 관련
            GlobalMemberValues.addCartLastItemForCommonGratuityUse();

            GlobalMemberValues.str_logsaveIn_MenuNames = mOptionTxt;
            LogsSave.saveLogsInDB(99);
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "System Error", "Close");
        }
    }

    private void makeTouchedArr(String paramQty) {
        String sQty = paramQty;
        String sHoldCode = MainMiddleService.mHoldCode;

        String strQuery = "select a.idx, a.midx, a.servicename, a.positionNo, a.strFileName, a.strFilePath, " +
                " a.service_price, a.commissionratioType, a.commissionratio, a.pointratio, " +
                " a.saleprice, a.salestart, a.saleend, a.activeyn,  " +
                " b.servicename as castegoryName, a.servicename2, a.servicename3, a.colorNo " +
                " from salon_storeservice_sub as a " +
                " left outer join salon_storeservice_main as b on a.midx = b.idx " +
                " where a.delyn = 'N' " +
                " and a.idx = '" + mServiceIdx + "' ";
        Cursor serviceCursor = dbInit.dbExecuteRead(strQuery);
        if (serviceCursor.getCount() > 0 && serviceCursor.moveToFirst()) {
            String tempSvcIdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(0), 1);
            String tempSvcMdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(1), 1);
            String tempSvcServiceName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(2), 1);
            String tempSvcStrFileName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(4), 1);
            String tempSvcStrFilePath = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(5), 1);
            String tempSvcServicePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(6), 1);
            String tempSvcSalePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(10), 1);
            String tempSvcSaleStart = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(11), 1);
            String tempSvcSaleEnd = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(12), 1);
            String tempSvcCommissionRatio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(8), 1);
            String tempSvcCommissionRatioType = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(7), 1);
            String tempSvcPointRatio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(9), 1);
            String tempSvcPositionNo = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(3), 1);
            String tempSvcSetMenuYN = "N";

            String tempSvcCategoryName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(14), 1);

            String tempSvcCategoryColorNo = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select colorNo from salon_storeservice_main where idx = '" + tempSvcMdx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(tempSvcCategoryColorNo)) {
                tempSvcCategoryColorNo = "1";
            }
            String tempSvcCategoryColor = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(tempSvcCategoryColorNo) - 1];

            String tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(15), 1);
            String tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(16), 1);
            if (!GlobalMemberValues.isStrEmpty(tempServiceName2)) {
                tempSvcServiceName += "\n" + tempServiceName2;
            }
            if (!GlobalMemberValues.isStrEmpty(tempServiceName3)) {
                tempSvcServiceName += "\n" + tempServiceName3;
            }

            String sCustomerId = MainMiddleService.insCustomerId;
            String sCustomerName = MainMiddleService.insCustomerName;
            String sCustomerPhone = MainMiddleService.insCustomerPhone;

            // 직원정보 구하기
            String sEmpIdx = MainMiddleService.insEmpIdx;
            String sEmpName = MainMiddleService.insEmpName;

            //11192024 if dynamic price is being used
            String dynamicprice = mIntent.getStringExtra("dynamicprice");
            if(dynamicprice != null && !dynamicprice.equals("")){
                tempSvcServicePrice = dynamicprice;
            }

            //11192024 if editing dynamic price item
            if (GlobalMemberValues.getDoubleAtString(tempSvcServicePrice) == 0.0 && mNowPrice != 0.0){
                tempSvcServicePrice = Double.toString(mNowPrice);
            }

            String paramsString[] = {
                    sQty, sHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, tempSvcMdx, tempSvcIdx,
                    tempSvcServiceName, tempSvcStrFileName, tempSvcStrFilePath,
                    tempSvcServicePrice, tempSvcSalePrice, tempSvcSaleStart, tempSvcSaleEnd,
                    tempSvcCommissionRatio, tempSvcCommissionRatioType, tempSvcPointRatio,
                    tempSvcPositionNo, tempSvcSetMenuYN,
                    sCustomerId, sCustomerName, sCustomerPhone, "0", sEmpIdx, sEmpName, "N",
                    tempSvcCategoryName, "", "", "", tempSvcCategoryColor
            };

            MainMiddleService.mTouchedArr = paramsString;
        }
        serviceCursor.close();

    }

    private void closeModifierPage() {
        optionBtnListLinearLayout.removeAllViews();
        this.finish();

        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
        }
    }

    private void setSelectedValueInit() {
        if (MainActivity.proCustomDial != null) {
            MainActivity.proCustomDial.dismiss();
        }

        modifierValueTv.setText("");
        mSelectedModifierButtonTagValue = "";
        mSelectedOptionAddButtonTagValue = "";
    }

    @Override
    protected void onPause() {
        super.onPause();

        parentLn.setVisibility(View.GONE);

        GlobalMemberValues.logWrite("lifecyclelog", "on pause 실행됨...");
    }

    @Override
    protected void onStop() {
        super.onStop();

        GlobalMemberValues.logWrite("lifecyclelog", "on stop 실행됨...");
    }

    @Override
    protected void onStart() {
        super.onStart();

        GlobalMemberValues.logWrite("lifecyclelog", "on start 실행됨...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        parentLn.setVisibility(View.VISIBLE);

        GlobalMemberValues.logWrite("lifecyclelog", "on resume 실행됨...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        GlobalMemberValues.logWrite("lifecyclelog", "on restart 실행됨...");
    }

    class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context mContex;
        private List<String[]> mListDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<Integer, List<String[]>> mListDataChild;
        private LayoutInflater mInflater;


        public ExpandableListAdapter(Context context, List<String[]> listDataHeader,
                                     HashMap<Integer, List<String[]>> listChildData) {
            mContex = context;
            mListDataHeader = listDataHeader;
            mListDataChild = listChildData;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getGroupCount() {
            return mListDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mListDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String[] headerTitle = (String[])getGroup(groupPosition);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.expandablelist_group, null);
            }

            String getOptionIdx = "";
            if (headerTitle.length > 0) {
                getOptionIdx = headerTitle[0];
            }
            String getOptionName = "";
            if (headerTitle.length > 1) {
                getOptionName = headerTitle[1];
            }
            String getOptionPilsuYN = "";
            if (headerTitle.length > 2) {
                getOptionPilsuYN = headerTitle[2];
            }
            String getOptionType = "";
            if (headerTitle.length > 3) {
                getOptionType = headerTitle[3];
            }
            String getAutoViewInPosYN = "";
            if (headerTitle.length > 4) {
                getAutoViewInPosYN = headerTitle[4];
            }

//        GlobalMemberValues.logWrite("optoinautoviewynlog", "getAutoViewInPosYN : " + getAutoViewInPosYN + "\n");

            String tempOptionTypeTxt = "Single";
            if (getOptionType == "S" || getOptionType.equals("S")) {
                tempOptionTypeTxt = "Single";
            } else {
                tempOptionTypeTxt = "Multi";
            }

            String requiredTxt = "";
            if (GlobalMemberValues.isStrEmpty(getOptionPilsuYN)) {
                getOptionPilsuYN = "Y";
            }
            if (getOptionPilsuYN == "Y" || getOptionPilsuYN.equals("Y")) {
                requiredTxt = " (Required)";
            } else {
                requiredTxt = "";
            }
            if (GlobalMemberValues.isStrEmpty(getAutoViewInPosYN)) {
                getAutoViewInPosYN = "N";
            }

            int[] tempMinMaxMaxsumval = getMinMaxMaxsumVal(getOptionIdx);
            int tempMinval = tempMinMaxMaxsumval[0];
            int tempMaxval = tempMinMaxMaxsumval[1];
            int tempMaxsumval = tempMinMaxMaxsumval[2];


//        optionTv = new TextView(mContext);
//        optionTv.setLayoutParams(statusLnLayoutParams);
//        optionTv.setBackgroundResource(R.drawable.button_selector_modifieroptiontitle);
//        optionTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//        optionTv.setPadding(8, 15, 8, 15);
//        optionTv.setText(ssb);
//        optionTv.setTextSize(fontsize2);
//
//        optionTv.setTextColor(Color.parseColor("#3e3d42"));
//        optionTv.setPaintFlags(optionTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


            TextView headerLabel = (TextView) convertView.findViewById(R.id.tv_header1);
            headerLabel.setTextSize(26 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            headerLabel.setText(headerTitle[1]);

            TextView headerLabe2 = (TextView) convertView.findViewById(R.id.tv_header2);
            headerLabe2.setTextSize(18 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            headerLabe2.setText("Min Qty Limit : " + tempMinval);

            TextView headerLabe3 = (TextView) convertView.findViewById(R.id.tv_header3);
            headerLabe3.setTextSize(18 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            headerLabe3.setText("Max Qty Limit : " + tempMaxval);

            TextView headerLabe4 = (TextView) convertView.findViewById(R.id.tv_header4);
            headerLabe4.setTextSize(18 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            headerLabe4.setText("Individual Qty Limit : " + tempMaxsumval);

            if (GlobalMemberValues.is_customerMain){
                headerLabel.setTextSize(30);
                headerLabe2.setVisibility(View.GONE);
                headerLabe3.setVisibility(View.GONE);
                headerLabe4.setVisibility(View.GONE);
            } else {

            }

//        convertView.setBackgroundResource(R.drawable.button_selector_modifieroptiontitle);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.grid_view, null);
            }
            CustomGridView gridView = (CustomGridView) convertView.findViewById(R.id.GridView_toolbar);

            gridView.setNumColumns(GlobalMemberValues.MODIFIER_OPTION_SU);// gridView.setGravity(Gravity.CENTER);//
            gridView.setHorizontalSpacing(10);// SimpleAdapter adapter =

//        GridAdapter adapter = new GridAdapter(mContex, mListDataChild.get(groupPosition).toArray(new String[mListDataChild.get(groupPosition).size()]));
            GridAdapter adapter = new GridAdapter(mContex, mListDataHeader.get(groupPosition) ,mListDataChild.get(groupPosition));

            gridView.setAdapter(adapter);// Adapter

            int adapterSize = adapter.getCount();

            for (int size = 0; size < adapterSize; size++) {
                RelativeLayout relativeLayout = (RelativeLayout) adapter.getView(size, null, gridView);
                if (relativeLayout != null) {
                    TextView textView = (TextView) relativeLayout.getChildAt(0);
                    textView.measure(0, 0);
                }
//            totalHeight += textView.getMeasuredHeight();
            }

            int basicHeight = 150;
            if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
                basicHeight = 120;
            } else if (GlobalMemberValues.mDevicePAX) {
                basicHeight = 150;
            } else if (GlobalMemberValues.isThisDeviceClover) {
                basicHeight = 150;
            }

            basicHeight = 170;

            int tempMok = adapterSize / GlobalMemberValues.MODIFIER_OPTION_SU;
            int tempNamuji = adapterSize % GlobalMemberValues.MODIFIER_OPTION_SU;
            int totalHeight = 0;
            if (tempNamuji == 0) {
                totalHeight = basicHeight * (tempMok);
            } else {
                totalHeight = basicHeight + (basicHeight * (tempMok));
            }

            gridView.SetHeight(totalHeight);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GridAdapter extends BaseAdapter {

        private Context mContext;
        private final List<String[]> mMobileValues;
        private String[] mGroupArr;

        public GridAdapter(Context context, String[] groupArr, List<String[]> mobileValues) {
            mContext = context;
            mMobileValues = mobileValues;
            mGroupArr = groupArr;
        }

        @Override
        public int getCount() {
            return mMobileValues.size();
        }

        @Override
        public String getItem(int position) {
            return mMobileValues.get(position)[0];
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (true) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item, parent, false);
                holder = new ViewHolder();

                holder.text_name = (TextView) convertView.findViewById(R.id.tv_subTitle);
                holder.text_name.setTextSize(22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String[] strModifierOptionItem = mMobileValues.get(position);// mListDataChild.get(groupPosition).get(childPosition);

            String getOptionItemNameForRB = "";
            String getOptionItemPriceStrForRB = "";
            double getOptionItemPriceForRB = 0;
            String getOptionItemNamePriceStrForRB = "";

            String getOptionItemIdx = "";

            getOptionItemNameForRB = strModifierOptionItem[0];
            getOptionItemPriceStrForRB = strModifierOptionItem[1];
            getOptionItemPriceForRB = GlobalMemberValues.getDoubleAtString(strModifierOptionItem[1]);
            getOptionItemIdx = strModifierOptionItem[2];

            getOptionItemNamePriceStrForRB = getOptionItemNameForRB + "-JJJ-" + getOptionItemPriceForRB;

            //String tempRadioButtonValueForRB = getOptionItemNameForRB + " ($" + GlobalMemberValues.getCommaStringForDouble(getOptionItemPriceStrForRB) + ")";
            String tempRadioButtonValueForRB = getOptionItemNameForRB;
            if (GlobalMemberValues.getDoubleAtString(getOptionItemPriceStrForRB) > 0) {
                tempRadioButtonValueForRB += "\n($" + GlobalMemberValues.getCommaStringForDouble(getOptionItemPriceStrForRB) + ")";
            }

            int tempThisButtonCount = 0;

            //11192024 if using modifier + option setting, take modifier into consideration when updating selected/unselected option buttons.
            if (Objects.equals(mCombineType, "A")) {
                String currModifierName = mSelectedModifierButtonTagValue.split("-JJJ-")[0];

                tempThisButtonCount = GlobalMemberValues.getIntAtString(
                        MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select count(idx) from temp_salecart_optionadd_imsi " +
                                        " where  modifiercode = '" + mModifierCode + "' " +
                                        " and optioncategoryidx = '" + mGroupArr[0] + "' and optionitemidx = '" + getOptionItemIdx + "' " +
                                        " and modifiername = '" + currModifierName + "'")
                );
            } else {
                tempThisButtonCount = GlobalMemberValues.getIntAtString(
                        MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select count(idx) from temp_salecart_optionadd_imsi " +
                                        " where  modifiercode = '" + mModifierCode + "' " +
                                        " and optioncategoryidx = '" + mGroupArr[0] + "' and optionitemidx = '" + getOptionItemIdx + "' ")
                );
            }



            if (GlobalMemberValues.is_customerMain){
                if (tempThisButtonCount == 0) {
                    holder.text_name.setBackgroundResource(R.drawable.button_selector_customer_optionbutton);
                    holder.text_name.setTextColor(Color.parseColor("#000000"));
                } else {
                    GlobalMemberValues.logWrite("selecteditemidxlog", "selected idx : " + getOptionItemIdx + "\n");

                    holder.text_name.setBackgroundResource(R.drawable.roundlayout_button_custom_option_selected);
                    holder.text_name.setTextColor(Color.parseColor("#ff8137"));
                }
            } else {
                if (tempThisButtonCount == 0) {
                    holder.text_name.setBackgroundResource(R.drawable.button_selector_optionbutton);
                    holder.text_name.setTextColor(Color.parseColor("#1356ff"));
                } else {
                    GlobalMemberValues.logWrite("selecteditemidxlog", "selected idx : " + getOptionItemIdx + "\n");

                    holder.text_name.setBackgroundResource(R.drawable.button_selector_optionbutton_selected);
                    holder.text_name.setTextColor(Color.parseColor("#ffffff"));
                }
            }

//            optionSubBtn.setPadding(8, 3, 8, 3);
            holder.text_name.setId(GlobalMemberValues.getIntAtString(getOptionItemIdx));
            holder.text_name.setTag(getOptionItemNamePriceStrForRB);

            if (tempRadioButtonValueForRB.contains("-J-")){
                tempRadioButtonValueForRB = tempRadioButtonValueForRB.replace("-J-","\n");
            }

            holder.text_name.setText(tempRadioButtonValueForRB);

            int tvHeight = 100;
            if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
                tvHeight = 100;
            } else if (GlobalMemberValues.mDevicePAX) {
                tvHeight = 110;
            } else {
                tvHeight = 100;
            }
            holder.text_name.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, tvHeight));

            final String finalGetOptionIdx = mGroupArr[0];
            final String finalGetOptionName = mGroupArr[1];
            final String finalGetOptionItemIdx = getOptionItemIdx;
            holder.text_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedOptionCategoryIdxValue = finalGetOptionIdx;
                    mSelectedOptionItemIdxValue = finalGetOptionItemIdx;

                    mSelectedOptionCategoryNameValue = finalGetOptionName;
                    mSelectedOptionAddButtonTagValue = v.getTag().toString();
                    setAddModifier();

                    if (b_itemRemoving){
                        // jihun park addup 12192019 - 아이템 중복 선택시 실행되는 부분으로 확인시 아이템 재 추가 하지 않음.
                        //GlobalMemberValues.logWrite("chkjjjlog", "여기3" + "\n");
                        //mSvcIdxInRequiredOptions.remove(finalGetOptionIdx);

                    } else {
                        //GlobalMemberValues.logWrite("chkjjjlog", "여기4" + "\n");
                        mSvcIdxInRequiredOptions.remove(finalGetOptionIdx);
                        mSvcIdxInRequiredOptions.addElement(finalGetOptionIdx);
                    }
                    b_itemRemoving = false;


                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView text_name;
        }
    }

    public class OptionAddCartListLinearLayout_list_adapter extends BaseAdapter {
        Context context;
        List<String[]> mData;

        public OptionAddCartListLinearLayout_list_adapter(Context context, List<String[]> arr_strings) {
            this.mData = arr_strings;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.mData.size();
        }

        @Override
        public Object getItem(int i) {
            return this.mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            // 장바구니의 카테고리 이름 TextView
            LinearLayout.LayoutParams cateNameParams1
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cateNameParams1.setMargins(0, 15, 0, 5);

            CartViewHolder holder = new CartViewHolder();
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (GlobalMemberValues.is_customerMain){
                    convertView=inflater.inflate(R.layout.cart_item_customer, viewGroup,false);
                } else {
                    convertView=inflater.inflate(R.layout.cart_item, viewGroup,false);
                }

//                CartViewHolder holder = new CartViewHolder();
                holder.tx1 = (TextView)convertView.findViewById(R.id.cartlistitemgrouptitle);
                holder.tx2 = (TextView)convertView.findViewById(R.id.cartModOptionAdditionalTv);
                holder.tx3 = (TextView)convertView.findViewById(R.id.cartPriceTv);
                holder.tx4 = (TextView)convertView.findViewById(R.id.cartDelTv);
                holder.tx5 = (TextView)convertView.findViewById(R.id.cartAddTv);
                holder.tx6 = (TextView)convertView.findViewById(R.id.cartMinusTv);
                holder.tx7 = (TextView)convertView.findViewById(R.id.cartEaTv);
                holder.layout = (LinearLayout)convertView.findViewById(R.id.cartRl);
                holder.layout.setPadding(0, 0, 0, 10);

                convertView.setTag(holder);
            } else {
                holder = (CartViewHolder)convertView.getTag();
            }

            String[] data = mData.get(i);

            String getIdx = "";
            String getModifiername = "";
            String getOptionaddname = "";
            String getModifieroptionaddfullname = "";
            String getModifierprice = "";
            String getOptionaddprice = "";
            String getOptCateIdx = "";
            String getOptCateName = "";
            String getQty = "";

            getIdx = GlobalMemberValues.getDBTextAfterChecked(data[0], 1);
            getModifiername = GlobalMemberValues.getDBTextAfterChecked(data[1], 1);
            getOptionaddname = GlobalMemberValues.getDBTextAfterChecked(data[2], 1);
            getModifieroptionaddfullname = GlobalMemberValues.getDBTextAfterChecked(data[3], 1);
            getModifierprice = GlobalMemberValues.getDBTextAfterChecked(data[4], 1);
            getOptionaddprice = GlobalMemberValues.getDBTextAfterChecked(data[5], 1);
            getOptCateIdx = GlobalMemberValues.getDBTextAfterChecked(data[6], 1);
            getOptCateName = GlobalMemberValues.getDBTextAfterChecked(data[7], 1);
            getQty = GlobalMemberValues.getDBTextAfterChecked(data[8], 1);

            double tempAddPrice = (GlobalMemberValues.getDoubleAtString(getModifierprice) + GlobalMemberValues.getDoubleAtString(getOptionaddprice)) * GlobalMemberValues.getIntAtString(getQty);

            if (!GlobalMemberValues.isStrEmpty(getOptCateIdx)) {
                if (GlobalMemberValues.is_customerMain){
                    holder.tx1 = (TextView)convertView.findViewById(R.id.cartlistitemgrouptitle);
                    holder.tx1.setLayoutParams(cateNameParams1);
                    holder.tx1.setText(getOptCateName);
                    if (isEloPro) {
                        holder.tx1.setTextSize(22);
                    } else {
                        holder.tx1.setTextSize(25);
                    }
                    holder.tx1.setTextColor(Color.parseColor("#ffffff"));
                    holder.tx1.setPadding(10, 10, 10, 10);
                    holder.tx1.setBackgroundColor(Color.parseColor("#898989"));
                } else {
                    holder.tx1 = (TextView)convertView.findViewById(R.id.cartlistitemgrouptitle);
                    holder.tx1.setLayoutParams(cateNameParams1);
                    holder.tx1.setText(getOptCateName);
                    if (isEloPro) {
                        holder.tx1.setTextSize(22);
                    } else {
                        holder.tx1.setTextSize(16 + GlobalMemberValues.globalAddFontSizeForPAX());
                    }
                    holder.tx1.setTextColor(Color.parseColor("#1356ff"));
                    holder.tx1.setPadding(10, 10, 10, 10);
                    holder.tx1.setBackgroundResource(R.drawable.roundlayout_optiontitle2);
                }


                if (i != 0){
                    String a = mData.get(i - 1)[6];
                    String b = mData.get(i)[6];
                    if (a.equals(b)) {
                        holder.tx1.setVisibility(View.GONE);
                    } else {
                        holder.tx1.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.tx1.setVisibility(View.VISIBLE);
                }
            }

            String addPriceColorStr = "#000000";
            if (tempAddPrice < 0) {
                addPriceColorStr = "#f71611";
            }

            holder.tx2 = (TextView)convertView.findViewById(R.id.cartModOptionAdditionalTv);
//            cartModOptionAdditionalTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            int modifierbuttonCount = GlobalMemberValues.getIntAtString(
                    dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option_btn where svcidx = " + mServiceIdx + " and useyn = 'Y' "));
            GlobalMemberValues.logWrite("mCombineTypelog", "modifierbuttonCount : " + modifierbuttonCount + "\n");
            String modModifierfullname = getModifieroptionaddfullname;
            if (modifierbuttonCount == 0) {
                modModifierfullname = GlobalMemberValues.getModifierName(getModifieroptionaddfullname);
            }
            holder.tx2.setText(modModifierfullname);

            if (isEloPro) {
                holder.tx2.setTextSize(22);
            } else {
                holder.tx2.setTextSize(14 + GlobalMemberValues.globalAddFontSizeForPAX());
            }




            String valueAddPrice = GlobalMemberValues.getCommaStringForDouble(tempAddPrice + "");

            /**
             if (tempAddPrice > 0) {
             valueAddPrice = GlobalMemberValues.getCommaStringForDouble(tempAddPrice + "");
             } else {
             valueAddPrice = ""
             }
             **/

            holder.tx3 = (TextView)convertView.findViewById(R.id.cartPriceTv);
            holder.tx3.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            holder.tx3.setPadding(5, 5, 5, 5);
            holder.tx3.setText(valueAddPrice);
            if (isEloPro) {
                holder.tx3.setTextSize(22);
            } else {
                holder.tx3.setTextSize(14 + GlobalMemberValues.globalAddFontSizeForPAX());
            }
            holder.tx3.setTextColor(Color.parseColor(addPriceColorStr));


            // tx4 ---------------------------------------------------------------------------------
            holder.tx4 = (TextView)convertView.findViewById(R.id.cartDelTv);
            holder.tx4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tx4.setPadding(5, 5, 0, 5);
            holder.tx4.setTag(getIdx);
//            holder.tx4.setText(" X ");
//            if (isEloPro) {
//                holder.tx4.setTextSize(22);
//            } else {
//                holder.tx4.setTextSize(14 + GlobalMemberValues.globalAddFontSizeForPAX());
//            }
//            holder.tx4.setTextColor(Color.parseColor("#6b6c68"));
            holder.tx4.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i > 1) {
                        mTempDeletedPositionInCart = i - 1;
                    }
                    setInitDbItem(v.getTag().toString());
                }
            });
            // -------------------------------------------------------------------------------------

            // tx5 ---------------------------------------------------------------------------------
            holder.tx5 = (TextView)convertView.findViewById(R.id.cartAddTv);
            holder.tx5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tx5.setPadding(5, 5, 0, 5);
            holder.tx5.setTag(getIdx);
//            holder.tx5.setText(" + ");
//            if (isEloPro) {
//                holder.tx5.setTextSize(30);
//            } else {
//                holder.tx5.setTextSize(22 + GlobalMemberValues.globalAddFontSizeForPAX());
//            }
//            holder.tx5.setTextColor(Color.parseColor("#2727ed"));
            final String finalGetQty1 = getQty;
            String finalGetOptCateIdx = getOptCateIdx;
            holder.tx5.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] tempMinMaxMaxsumval = getMinMaxMaxsumVal(finalGetOptCateIdx);
//                    int tempMinval = tempMinMaxMaxsumval[0];
//                    int tempMaxval = tempMinMaxMaxsumval[1];
                    int tempMaxsumval = tempMinMaxMaxsumval[2];
                    String temp_qty = getDbItemQty(finalGetOptCateIdx);
                    if (GlobalMemberValues.isStrEmpty(temp_qty)) {
                        temp_qty = "0";
                    }

                    if (GlobalMemberValues.getIntAtString(temp_qty) >= tempMaxsumval){
                        new AlertDialog.Builder(mContext)
                                .setTitle("")
                                .setMessage("You have exceeded the sum you can select.")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                .show();
                        return;
                    }
                    if (tempMaxsumval > Integer.parseInt(finalGetQty1)){
                        if (i > 1) {
                            mTempDeletedPositionInCart = i - 1;
                        }
                        setAddMInusDbItem(v.getTag().toString(), "A");
                    }
                }
            });
            // -------------------------------------------------------------------------------------

            // tx6 ---------------------------------------------------------------------------------
            holder.tx6 = (TextView)convertView.findViewById(R.id.cartMinusTv);
            holder.tx6.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tx6.setPadding(5, 5, 0, 5);
            holder.tx6.setTag(getIdx);
//            holder.tx6.setText(" - ");
//            if (isEloPro) {
//                holder.tx6.setTextSize(30);
//            } else {
//                holder.tx6.setTextSize(22 + GlobalMemberValues.globalAddFontSizeForPAX());
//            }
//            holder.tx6.setTextColor(Color.parseColor("#fa200e"));
            final String finalGetQty2 = getQty;
            holder.tx6.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i > 1) {
                        mTempDeletedPositionInCart = i - 1;
                    }
                    if (GlobalMemberValues.getIntAtString(finalGetQty2) > 1) {
                        setAddMInusDbItem(v.getTag().toString(), "M");
                    }

                }
            });
            // -------------------------------------------------------------------------------------






            holder.tx7 = (TextView)convertView.findViewById(R.id.cartEaTv);
            holder.tx7.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.tx7.setPadding(5, 5, 5, 5);
            holder.tx7.setText(getQty + "ea");
            if (isEloPro) {
                holder.tx7.setTextSize(22);
            } else {
                holder.tx7.setTextSize(14 + GlobalMemberValues.globalAddFontSizeForPAX());
            }

            holder.tx7.setTextColor(Color.parseColor(addPriceColorStr));


            if (GlobalMemberValues.is_customerMain){
                holder.tx1.setTextColor(Color.parseColor("#ffffff"));
                holder.tx2.setTextColor(Color.parseColor("#ffffff"));
                holder.tx3.setTextColor(Color.parseColor("#ffffff"));
                holder.tx4.setTextColor(Color.parseColor("#ffffff"));
                holder.tx5.setTextColor(Color.parseColor("#ffffff"));
                holder.tx6.setTextColor(Color.parseColor("#ffffff"));

                holder.tx5.setBackgroundResource(R.drawable.tablesale_billsplitmerge_plus_off);
                holder.tx6.setBackgroundResource(R.drawable.tablesale_billsplitmerge_min_off);
            }


            GlobalMemberValues.logWrite(TAG + "TAJJJ", "position value : " + i + "\n");



            return convertView;
        }
    }

    public static class CartViewHolder {
        TextView tx1;
        TextView tx2;
        TextView tx3;
        TextView tx4;
        TextView tx5;
        TextView tx6;
        TextView tx7;
        LinearLayout layout;
    }
}



