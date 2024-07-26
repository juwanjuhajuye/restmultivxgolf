package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Bundle;
import androidx.annotation.IdRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class MainMiddleServiceOptionAdd extends Activity {
    private static final String TAG = "MainMiddleServiceOptionAddLog";

    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button optionaddPopupVButton, optionaddPopupCloseButton;
    private Button viewallbtn, hideallbtn;

    private ImageView optionaddPopupServiceImageView;
    private TextView optionaddPopupServiceNameTextView;
    private TextView optionaddPopupPriceTextView;
    private TextView optionaddPopupAmountTextView, optionaddPopupTxtTextView;
    private TextView pricetextview, amounttextview;

    ScrollView optionaddPopupOAListScrollView;
    private static LinearLayout optionaddPopupOAListLinearLayout;

    Intent mIntent;
    String mServiceIdx = "";

    private double mBasicPrice = 0;
    private double mAmountPrice = 0;

    private double nowPrice = 0;

    private Vector<String> mOptionVectorForRB;
    private String[] mCheckedOptionArrForRB;

    private Vector<String> mOptionVectorForCB;
    private String[] mCheckedOptionArrForCB;

    private Vector<String> mAdditionalVectorForAD;
    private String[] mCheckedAdditionalArrForAD;

    // 선택필수인 옵션중 선택한 옵션의 Service Idx
    private Vector<String> mSvcIdxInRequiredOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_main_middle_service_option_add);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
        if (GlobalMemberValues.thisTabletRealWidth > 1280) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 82;
        }
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 95;
        if (GlobalMemberValues.thisTabletRealHeight > 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 92;
        }

        // Device 가 Elo 일 때...
        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
        } else {
            if (GlobalMemberValues.isDeviceElo()) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
            } else {
                if (GlobalMemberValues.thisTabletRealHeight == 1920 && GlobalMemberValues.thisTabletRealWidth == 1032) {
                    parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
                }
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.optionaddPopupLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            mServiceIdx = mIntent.getStringExtra("ServiceIdx");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 Service Idx : " + mServiceIdx + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_right);
            }
        }

        setContents();
    }
    private void setContents() {
        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(mContext);

        // 스크롤뷰 객체 생성
        optionaddPopupOAListScrollView = (ScrollView)findViewById(R.id.optionaddPopupOAListScrollView);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        optionaddPopupOAListLinearLayout = (LinearLayout)findViewById(R.id.optionaddPopupOAListLinearLayout);


        optionaddPopupVButton = (Button) findViewById(R.id.optionaddPopupVButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            optionaddPopupVButton.setText("");
            optionaddPopupVButton.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_enter);
        } else {
            optionaddPopupVButton.setTextSize(
                    optionaddPopupVButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        optionaddPopupCloseButton = (Button)findViewById(R.id.optionaddPopupCloseButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            optionaddPopupCloseButton.setText("");
            optionaddPopupCloseButton.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            optionaddPopupCloseButton.setTextSize(
                    optionaddPopupCloseButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        viewallbtn = (Button)findViewById(R.id.viewallbtn);
        hideallbtn = (Button)findViewById(R.id.hideallbtn);

        optionaddPopupVButton.setOnClickListener(optionaddPopupButtonClickListener);
        optionaddPopupCloseButton.setOnClickListener(optionaddPopupButtonClickListener);
        viewallbtn.setOnClickListener(optionaddPopupButtonClickListener);
        hideallbtn.setOnClickListener(optionaddPopupButtonClickListener);

        optionaddPopupServiceImageView = (ImageView) findViewById(R.id.optionaddPopupServiceImageView);

        optionaddPopupServiceNameTextView = (TextView) findViewById(R.id.optionaddPopupServiceNameTextView);

        optionaddPopupPriceTextView = (TextView) findViewById(R.id.optionaddPopupPriceTextView);
        optionaddPopupPriceTextView.setTextSize(
                optionaddPopupPriceTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        optionaddPopupAmountTextView = (TextView) findViewById(R.id.optionaddPopupAmountTextView);
        optionaddPopupAmountTextView.setTextSize(
                optionaddPopupAmountTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        optionaddPopupTxtTextView = (TextView) findViewById(R.id.optionaddPopupTxtTextView);
        optionaddPopupTxtTextView.setTextSize(optionaddPopupTxtTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pricetextview = (TextView) findViewById(R.id.pricetextview);
        pricetextview.setTextSize(pricetextview.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        amounttextview = (TextView) findViewById(R.id.amounttextview);
        amounttextview.setTextSize(amounttextview.getTextSize() * GlobalMemberValues.getGlobalFontSize());


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

            nowPrice = GlobalMemberValues.getDoubleAtString(tempServicePrice);

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
                    tempTimeMenuPrice = GlobalMemberValues.getDoubleAtString(
                            MainActivity.mDbInit.dbExecuteReadReturnString(
                                    " select " + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "_" + GlobalMemberValues.getWeekDayNum() +
                                            " from salon_storeservice_sub_timemenuprice where svcidx = '" + mServiceIdx + "' ")
                    );
                }

                GlobalMemberValues.logWrite("timemenulog", "tempTimeMenuPrice :  " + tempTimeMenuPrice + "\n");

                if (tempTimeMenuPrice > 0) {
                    nowPrice = tempTimeMenuPrice;
                }
            }
            // --------------------------------------------------------------------------------------------------------------------------

            String tempPriceTxt = "";

            // 세일 기간이고, 세일가격에 값이 있을 때  ------------------------------
            if ((!GlobalMemberValues.isStrEmpty(tempSaleStart)
                    && DateMethodClass.getDiffDayCount(tempSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                    && (!GlobalMemberValues.isStrEmpty(tempSaleEnd)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, tempSaleEnd) >= 0)
                    &&!GlobalMemberValues.isStrEmpty(tempSalePrice)) {

                nowPrice = GlobalMemberValues.getDoubleAtString(tempSalePrice);

                tempPriceTxt = "$" + GlobalMemberValues.getCommaStringForDouble(tempServicePrice) +
                        "-->" + "$" + GlobalMemberValues.getCommaStringForDouble(tempSalePrice);


            } else {
                tempPriceTxt = "$" + GlobalMemberValues.getCommaStringForDouble(nowPrice + "");
            }

            optionaddPopupPriceTextView.setText(tempPriceTxt);
            optionaddPopupAmountTextView.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempSalePrice));

            mBasicPrice = GlobalMemberValues.getDoubleAtString(tempSalePrice);

            /** 다운로드한 서비스 이미지가 있는지 체크한다. ********************************************/
            // 220823 요청에 의한 메뉴이미지 삭제처리.
//            Boolean isBgImage = false;
//            String strFilePath = "";
//            if (!GlobalMemberValues.isStrEmpty(tempStrFilePath)) {
//                // 이미지 경로 + 파일명 만들기
//                strFilePath = Environment.getExternalStorageDirectory().toString() +
//                        "/" + GlobalMemberValues.STATION_CODE +
//                        "/" + GlobalMemberValues.FOLDER_SERVICEIMAGE +
//                        "/serviceimg_" + mServiceIdx + ".png";
//                if (new File(strFilePath).exists() == false) {
//                    //GlobalMemberValues.logWrite("savedFileCheck", "파일이 없습니다.");
//                    isBgImage = false;
//                } else {
//                    isBgImage = true;
//                }
//            } else {
//                isBgImage = false;
//            }
//
//            optionaddPopupServiceImageView.setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃 크기에 이미지를 맞춘다.
//            if (!isBgImage) {
//                optionaddPopupServiceImageView.setVisibility(View.GONE);
//                //optionaddPopupServiceImageView.setImageResource(R.drawable.aa_images_servicenoimage);
//            } else {
//                // Bitmap 으로 변환
//                Bitmap serviceBgImageBm = BitmapFactory.decodeFile(strFilePath);
//                optionaddPopupServiceImageView.setImageBitmap(serviceBgImageBm);
//                //optionaddPopupServiceImageView.setPadding(3, 3, 3, 3);
//            }
            /****************************************************************************************/

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

        if (MainActivity.proCustomDial != null) {
            MainActivity.proCustomDial.dismiss();
        }
    }

    View.OnClickListener optionaddPopupButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.optionaddPopupVButton : {
                    /**
                    int vSizeForRB = mCheckedOptionArrForRB.length;
                    for (int i = 0; i < vSizeForRB; i++) {
                        GlobalMemberValues.logWrite("OAValue", "Option RadioButton value : " + mCheckedOptionArrForRB[i].toString() + "\n");
                    }

                    int vSizeForCB = mCheckedOptionArrForCB.length;
                    for (int i = 0; i < vSizeForCB; i++) {
                        GlobalMemberValues.logWrite("OAValue", "Option CheckBox value : " + mCheckedOptionArrForCB[i].toString() + "\n");
                    }

                    int vSizeForAD = mCheckedAdditionalArrForAD.length;
                    for (int i = 0; i < vSizeForAD; i++) {
                        GlobalMemberValues.logWrite("OAValue", "Additional value : " + mCheckedAdditionalArrForAD[i].toString() + "\n");
                    }
                     **/

                    // 먼저 required 인 항목을 선택했는지 체크
                    if (!checkRequiredOption()) {
                        return;
                    }

                    ArrayList<String> returnArrayList = calOptionAdditional();
                    if (returnArrayList != null) {
                        MainMiddleService.mOptionTxt = returnArrayList.get(0);
                        MainMiddleService.mOptionPrice = returnArrayList.get(1);
                        MainMiddleService.mAdditionalTxt1 = returnArrayList.get(2);
                        MainMiddleService.mAdditionalprice1 = returnArrayList.get(3);
                        MainMiddleService.mAdditionalTxt2 = returnArrayList.get(4);
                        MainMiddleService.mAdditionalprice2 = returnArrayList.get(5);

                        GlobalMemberValues.str_logsaveIn_MenuNames = MainMiddleService.mOptionTxt;
                        LogsSave.saveLogsInDB(99);

                        MainMiddleService.animServices(MainMiddleService.mTouchedView, MainMiddleService.mTouchedArr);

                        finish();
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                        }
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "No selected option or add ingredients", "Close");
                    }
                    break;
                }
                case R.id.optionaddPopupCloseButton : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }
                    break;
                }

                case R.id.viewallbtn : {
                    setOptionAdditional("viewall");
                    break;
                }
                case R.id.hideallbtn : {
                    setOptionAdditional("hideall");
                    break;
                }
            }
        }
    };

    private boolean checkRequiredOption() {
        boolean returnValue = true;

        if (mSvcIdxInRequiredOptions != null) {
            Cursor optionCursor;
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

            if (tempRequiredCount > 0) {
                if (mSvcIdxInRequiredOptions.size() == 0) {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Choose option", "Close");
                    returnValue = false;
                } else {
                    strQuery = " select idx, optionname from salon_storeservice_option " +
                            " where svcIdx = '" + mServiceIdx + "' " +
                            " and optionuseyn = 'Y' " +
                            " and optionpilsuyn = 'Y' " +
                            " order by sortnum asc, optionname asc, idx desc";
                    optionCursor = dbInit.dbExecuteRead(strQuery);
                    while (optionCursor.moveToNext()) {
                        String getOptionIdx = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(0), 1);
                        String getOptionName = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(1), 1);
                        if (mSvcIdxInRequiredOptions.indexOf(getOptionIdx) == -1) {
                            GlobalMemberValues.displayDialog(mContext, "Warning", "Choose <" + getOptionName + ">'s item", "Close");
                            returnValue = false;
                            break;
                        }
                    }
                }
            }
        }

        return returnValue;
    }

    private void setOptionAdditional(String paramOpenType) {
        // 뷰 추가전 먼저 초기화(삭제)한다.
        optionaddPopupOAListLinearLayout.removeAllViews();

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 10, 0, 10);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams statusLnLayoutParams1
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        statusLnLayoutParams1.setMargins(0, 0, 0, 0);

        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
        LinearLayout.LayoutParams statusTvLayoutParams2
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusTvLayoutParams2.setMargins(0, 0, 0, 0);

        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
        LinearLayout.LayoutParams statusTvLayoutParams3
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusTvLayoutParams3.setMargins(10, 0, 10, 0);

        // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
        LinearLayout.LayoutParams statusTvLayoutParams5
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusTvLayoutParams5.setMargins(10, 10, 10, 0);

        int optionCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option where svcidx = " + mServiceIdx));
        int additionalCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_additional where svcidx = " + mServiceIdx));

        if (optionCount > 0 || additionalCount > 0) {
            String strQuery = "";

            // Option 이 있을 때
            if (optionCount > 0) {
                Cursor optionCursor;
                Cursor optionItemCursor;

                /**
                TextView optionTitleTv = new TextView(mContext);
                optionTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                optionTitleTv.setPadding(10, 5, 10, 5);
                optionTitleTv.setText("MODIFIER");
                if (GlobalMemberValues.isEloPro()) {
                    optionTitleTv.setTextSize(22);
                }
                optionTitleTv.setTextColor(Color.parseColor("#3e3d42"));
                optionTitleTv.setPaintFlags(optionTitleTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                optionTitleTv.setBackgroundResource(R.drawable.roundlayout_optiontitle2);
                optionTitleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                optionaddPopupOAListLinearLayout.addView(optionTitleTv);
                 **/

                /** 단일선택 (RadioButton) 관련 ***********************************************************************************************/
                // 옵션값 담을 벡터 객체 생성 및 크기지정
                strQuery = " select count(idx) from salon_storeservice_option_item " +
                        " where optionIdx in " +
                        " (select idx from salon_storeservice_option where svcIdx = '" + mServiceIdx + "' and optionuseyn = 'Y' and optiontype = 'S') " +
                        " and itemuseyn = 'Y' ";
                int tempVectorSizeForRB = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(strQuery));
                mOptionVectorForRB = new Vector<String>();            // RadioButton 옵션값을 담을 Vector
                mOptionVectorForRB.setSize(tempVectorSizeForRB);

                // 선택한 옵션값 담을 배열 객체 생성 및 크기지정
                strQuery = " select count(idx) from salon_storeservice_option " +
                        " where svcIdx = '" + mServiceIdx + "' " +
                        " and optionuseyn = 'Y' and optiontype = 'S' ";
                int tempRadioButtonCheckedVectorSize = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(strQuery));
                mCheckedOptionArrForRB = new String[tempRadioButtonCheckedVectorSize];
                /****************************************************************************************************************************/

                /** 다중선택 (Checkbox) 관련 *************************************************************************************************/
                // 옵션값 담을 벡터 객체 생성 및 크기지정
                strQuery = " select count(idx) from salon_storeservice_option_item " +
                        " where optionIdx in " +
                        " (select idx from salon_storeservice_option where svcIdx = '" + mServiceIdx + "' and optionuseyn = 'Y' and optiontype = 'M') " +
                        " and itemuseyn = 'Y' ";
                int tempVectorSizeForCB = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(strQuery));
                mOptionVectorForCB = new Vector<String>();            // Checkbox 옵션값을 담을 Vector
                mOptionVectorForCB.setSize(tempVectorSizeForCB);
                mCheckedOptionArrForCB = new String[tempVectorSizeForCB];
                /****************************************************************************************************************************/

                int tempOptionCount = 0;

                int optionIndexForRB = 0;
                int optionItemIndexForRB = 0;

                int optionIndexForCB = 0;
                int optionItemIndexForCB = 0;

                mSvcIdxInRequiredOptions = new Vector<String>();

                strQuery = " select idx, optionname, optionpilsuyn, optiontype from salon_storeservice_option " +
                        " where svcIdx = '" + mServiceIdx + "' " +
                        " and optionuseyn = 'Y' " +
                        " order by sortnum asc, optionname asc, idx desc";
                optionCursor = dbInit.dbExecuteRead(strQuery);
                while (optionCursor.moveToNext()) {
                    // LinearLayout 객체 생성
                    LinearLayout newLn = new LinearLayout(mContext);
                    newLn.setLayoutParams(newLnLayoutParams);
                    newLn.setOrientation(LinearLayout.VERTICAL);
                    //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                    //newLn.setBackgroundResource(saleListBg);
                    newLn.setPadding(8, 8, 8, 8);

                    String getOptionIdx = "";
                    String getOptionName = "";
                    String getOptionPilsuYN = "";
                    String getOptionType = "";

                    getOptionIdx = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(0), 1);
                    getOptionName = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(1), 1);
                    getOptionPilsuYN = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(2), 1);
                    getOptionType = GlobalMemberValues.getDBTextAfterChecked(optionCursor.getString(3), 1);

                    String requiredTxt = "";
                    if (GlobalMemberValues.isStrEmpty(getOptionPilsuYN)) {
                        getOptionPilsuYN = "Y";
                    }

                    if (GlobalMemberValues.isStrEmpty(getOptionType)) {
                        getOptionType = "S";
                    }

                    LinearLayout optionLn = new LinearLayout(mContext);
                    optionLn.setLayoutParams(statusLnLayoutParams1);
                    optionLn.setOrientation(LinearLayout.VERTICAL);
                    //optionLn.setBackgroundColor(Color.parseColor("#f8f8f8"));

                    if (getOptionPilsuYN == "Y" || getOptionPilsuYN.equals("Y")) {
                        requiredTxt = " - required";
                    } else {
                        requiredTxt = "";
                    }

                    String str = getOptionName + requiredTxt;
                    SpannableStringBuilder ssb = new SpannableStringBuilder(str);
                    if (getOptionPilsuYN == "Y" || getOptionPilsuYN.equals("Y")) {
                        int tempStartRequiredTxt = str.indexOf("- required");
                        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#0054d5")),
                                tempStartRequiredTxt, tempStartRequiredTxt + 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        GlobalMemberValues.logWrite(TAG + "2", "tempStartRequiredTxt : " + tempStartRequiredTxt + "\n");
                    }


                    // 옵션 Sub Title ------------------------------------------------------------------------------------------------------------
                    LinearLayout optionSubTitleLn = new LinearLayout(mContext);
                    optionSubTitleLn.setLayoutParams(statusLnLayoutParams1);
                    optionSubTitleLn.setOrientation(LinearLayout.HORIZONTAL);
                    optionSubTitleLn.setBackgroundResource(R.drawable.roundlayout_optiontitle2);

                    TextView optionSubTitleTv = new TextView(mContext);
                    optionSubTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    optionSubTitleTv.setPadding(8, 15, 8, 15);
                    optionSubTitleTv.setText(ssb);
                    if (GlobalMemberValues.isEloPro()) {
                        optionSubTitleTv.setTextSize(22);
                    } else {
                        optionSubTitleTv.setTextSize(20);
                    }
                    optionSubTitleTv.setTextColor(Color.parseColor("#3e3d42"));
                    optionSubTitleTv.setPaintFlags(optionSubTitleTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    //optionSubTitleTv.setBackgroundResource(R.drawable.roundlayout_optiontitle);
                    optionSubTitleTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                    optionSubTitleLn.addView(optionSubTitleTv);


                    LinearLayout optionviewhideLn = new LinearLayout(mContext);
                    optionviewhideLn.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    optionviewhideLn.setOrientation(LinearLayout.HORIZONTAL);
                    optionviewhideLn.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    optionviewhideLn.setPadding(10, 10, 10, 10);

                    final TextView optionSubTitleTv2 = new TextView(mContext);
                    optionSubTitleTv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_view);
                    optionSubTitleTv2.setLayoutParams(statusTvLayoutParams2);
                    optionviewhideLn.addView(optionSubTitleTv2);

                    optionSubTitleLn.addView(optionviewhideLn);

                    optionLn.addView(optionSubTitleLn);
                    // --------------------------------------------------------------------------------------------------------------------------

                    if (getOptionType == "S" || getOptionType.equals("S")) {                        // 단일선택

                        if (mCheckedOptionArrForRB.length > 0) {
                            // 선택 옵션값 초기화
                            mCheckedOptionArrForRB[optionIndexForRB] = "";
                        }

                        final RadioGroup radioGroupForRB = new RadioGroup(mContext);
                        radioGroupForRB.setLayoutParams(statusTvLayoutParams2);
                        radioGroupForRB.setPadding(10, 10, 10, 0);
                        radioGroupForRB.setTag(optionIndexForRB);

                        if (getOptionPilsuYN == "N" || getOptionPilsuYN.equals("N")) {
                            // 라디오버튼 생성 --------------------------------------------
                            RadioButton radioButtonForRB = new RadioButton(mContext);
                            radioButtonForRB.setTag(optionItemIndexForRB);
                            radioButtonForRB.setText("Not selected");
                            radioButtonForRB.setTextColor(Color.parseColor("#3e3d42"));
                            radioButtonForRB.setTextSize(16);
                            if (GlobalMemberValues.isEloPro()) {
                                radioButtonForRB.setTextSize(20);
                            }
                            // -----------------------------------------------------------
                            radioGroupForRB.addView(radioButtonForRB);
                        }

                        strQuery = " select itemname, itemprice from salon_storeservice_option_item " +
                                " where optionidx = '" + getOptionIdx + "' and itemuseyn = 'Y' " +
                                " order by sortnum asc ";
                        GlobalMemberValues.logWrite("modifiertestlog", "sql : " + strQuery + "\n");
                        optionItemCursor = dbInit.dbExecuteRead(strQuery);
                        while (optionItemCursor.moveToNext()) {
                            String getOptionItemNameForRB = "";
                            String getOptionItemPriceStrForRB = "";
                            double getOptionItemPriceForRB = 0;
                            String getOptionItemNamePriceStrForRB = "";

                            getOptionItemNameForRB = GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(0), 1);
                            getOptionItemPriceStrForRB = GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(1), 1);
                            getOptionItemPriceForRB = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(1), 1));

                            getOptionItemNamePriceStrForRB = getOptionItemNameForRB + "-JJJ-" + getOptionItemPriceForRB;

                            String tempRadioButtonValueForRB = getOptionItemNameForRB;
                            if (GlobalMemberValues.getDoubleAtString(getOptionItemPriceStrForRB) > 0) {
                                tempRadioButtonValueForRB += " ($" + GlobalMemberValues.getCommaStringForDouble(getOptionItemPriceStrForRB) + ")";
                            }

                            // 라디오버튼 생성 --------------------------------------------
                            RadioButton radioButtonForRB = new RadioButton(mContext);
                            radioButtonForRB.setTag(optionItemIndexForRB);
                            radioButtonForRB.setText(tempRadioButtonValueForRB);
                            radioButtonForRB.setTextColor(Color.parseColor("#3e3d42"));
                            radioButtonForRB.setTextSize(16);
                            if (GlobalMemberValues.isEloPro()) {
                                radioButtonForRB.setTextSize(20);
                            }
                            // -----------------------------------------------------------
                            radioGroupForRB.addView(radioButtonForRB);

                            GlobalMemberValues.logWrite("OptionValueChecked", "Tag() : " + optionItemIndexForRB + "\n");
                            GlobalMemberValues.logWrite("OptionValueChecked", "OptionValue : " + getOptionItemNamePriceStrForRB + "\n");

                            mOptionVectorForRB.add(optionItemIndexForRB, getOptionItemNamePriceStrForRB);

                            GlobalMemberValues.logWrite("OptionValueChecked", "mRadioButtonOptionVector : " + mOptionVectorForRB.get(optionItemIndexForRB) + "\n");
                            optionItemIndexForRB++;
                        }

                        final int tempOptionIndexForRB = optionIndexForRB;
                        final String finalGetOptionIdx = getOptionIdx;
                        radioGroupForRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                int tempCheckedRadioGroupLocation = GlobalMemberValues.getIntAtString(group.getTag().toString());
                                if (tempCheckedRadioGroupLocation == tempOptionIndexForRB) {
                                    RadioButton tempRadioButton = (RadioButton)findViewById(checkedId);
                                    if (mOptionVectorForRB == null || mOptionVectorForRB.isEmpty()){

                                    } else {
                                        String getOptionValue = mOptionVectorForRB.get(GlobalMemberValues.getIntAtString(tempRadioButton.getTag().toString()));
                                        GlobalMemberValues.logWrite("OptionValueChecked", "checked getTag() : " + tempRadioButton.getTag().toString() + "\n");
                                        GlobalMemberValues.logWrite("OptionValueChecked", "checked getOptionValue : " + getOptionValue + "\n");

                                        mCheckedOptionArrForRB[tempCheckedRadioGroupLocation] = getOptionValue;

                                        calOptionAdditional();
                                        //GlobalMemberValues.displayDialog(mContext, "Info", "Radio Group Index : " + tempCheckedRadioGroupLocation
                                        //      + "\nOption Value : " + mCheckedOptionArrForRB[tempCheckedRadioGroupLocation], "Close");

                                        mSvcIdxInRequiredOptions.remove(finalGetOptionIdx);
                                        if (tempRadioButton.isChecked()) {
                                            mSvcIdxInRequiredOptions.addElement(finalGetOptionIdx);
                                        }
                                    }
                                }
                            }
                        });

                        switch (paramOpenType) {
                            case "viewall" : {
                                radioGroupForRB.setVisibility(View.VISIBLE);
                                break;
                            }
                            case "hideall" : {
                                radioGroupForRB.setVisibility(View.GONE);
                                break;
                            }
                            default : {
                                if (tempOptionCount == 0) {
                                    radioGroupForRB.setVisibility(View.VISIBLE);
                                } else {
                                    radioGroupForRB.setVisibility(View.GONE);
                                }
                                break;
                            }

                        }

                        optionLn.addView(radioGroupForRB);
                        optionIndexForRB++;

                        optionLn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (radioGroupForRB.getVisibility() == View.GONE) {
                                    radioGroupForRB.setVisibility(View.VISIBLE);
                                    optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_hide);
                                } else {
                                    radioGroupForRB.setVisibility(View.GONE);
                                    optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_view);
                                }
                            }
                        });

                        if (radioGroupForRB.getVisibility() == View.GONE) {
                            optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_view);
                        } else {
                            optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_hide);
                        }

                    } else {                                                                        // 다중선택
                        // Checkbox 를 담을 LinearLayout 생성
                        final LinearLayout checkboxLn = new LinearLayout(mContext);
                        checkboxLn.setLayoutParams(newLnLayoutParams);
                        checkboxLn.setOrientation(LinearLayout.VERTICAL);
                        //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
                        //newLn.setBackgroundResource(saleListBg);
                        checkboxLn.setPadding(8, 8, 8, 8);

                        strQuery = " select itemname, itemprice from salon_storeservice_option_item " +
                                " where optionidx = '" + getOptionIdx + "' and itemuseyn = 'Y' " +
                                " order by sortnum asc ";
                        optionItemCursor = dbInit.dbExecuteRead(strQuery);
                        int tempCheckBoxCount = 0;
                        while (optionItemCursor.moveToNext()) {
                            if (mCheckedOptionArrForRB.length > 0) {
                                // 선택 옵션값 초기화
                                mCheckedOptionArrForCB[optionItemIndexForCB] = "";
                            }

                            String getOptionItemNameForCB = "";
                            String getOptionItemPriceStrForCB = "";
                            double getOptionItemPriceForCB = 0;
                            String getOptionItemNamePriceStrForCB = "";

                            getOptionItemNameForCB = GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(0), 1);
                            getOptionItemPriceStrForCB = GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(1), 1);
                            getOptionItemPriceForCB = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(optionItemCursor.getString(1), 1));

                            getOptionItemNamePriceStrForCB = getOptionItemNameForCB + "-JJJ-" + getOptionItemPriceForCB;

                            String tempRadioButtonValueForCB = getOptionItemNameForCB;
                            if (GlobalMemberValues.getDoubleAtString(getOptionItemPriceStrForCB) > 0) {
                                tempRadioButtonValueForCB += " ($" + GlobalMemberValues.getCommaStringForDouble(getOptionItemPriceStrForCB) + ")";
                            }

                            // 체크박스 생성 ----------------------------------------------
                            // 체크박스
                            CheckBox checkBox = new CheckBox(mContext);
                            if (tempCheckBoxCount == 0) {
                                checkBox.setLayoutParams(statusTvLayoutParams5);
                            } else {
                                checkBox.setLayoutParams(statusTvLayoutParams3);
                            }

                            checkBox.setPadding(0, 0, 0, 0);
                            checkBox.setTag(optionItemIndexForCB);
                            checkBox.setText(tempRadioButtonValueForCB);
                            checkBox.setTextColor(Color.parseColor("#3e3d42"));
                            checkBox.setHighlightColor(Color.parseColor("#3e3d42"));
                            checkBox.setTextSize(16);
                            if (GlobalMemberValues.isEloPro()) {
                                checkBox.setTextSize(20);
                            }

                            final String finalGetOptionIdx1 = getOptionIdx;
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    mSvcIdxInRequiredOptions.remove(finalGetOptionIdx1);

                                    int checkIndex = GlobalMemberValues.getIntAtString(buttonView.getTag().toString());
                                    if (isChecked) {
                                        mCheckedOptionArrForCB[checkIndex] = mOptionVectorForCB.get(checkIndex);
                                        mSvcIdxInRequiredOptions.addElement(finalGetOptionIdx1);
                                    } else {
                                        mCheckedOptionArrForCB[checkIndex] = "";
                                    }

                                    calOptionAdditional();
                                }
                            });
                            // -----------------------------------------------------------
                            checkboxLn.addView(checkBox);

                            GlobalMemberValues.logWrite("OptionValueChecked", "Tag() : " + optionItemIndexForCB + "\n");
                            GlobalMemberValues.logWrite("OptionValueChecked", "OptionValue : " + getOptionItemNamePriceStrForCB + "\n");

                            mOptionVectorForCB.add(optionItemIndexForCB, getOptionItemNamePriceStrForCB);

                            optionItemIndexForCB++;
                            tempCheckBoxCount++;
                        }

                        switch (paramOpenType) {
                            case "viewall" : {
                                checkboxLn.setVisibility(View.VISIBLE);
                                break;
                            }
                            case "hideall" : {
                                checkboxLn.setVisibility(View.GONE);
                                break;
                            }
                            default : {
                                if (tempOptionCount == 0) {
                                    checkboxLn.setVisibility(View.VISIBLE);
                                } else {
                                    checkboxLn.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }

                        optionLn.addView(checkboxLn);
                        final int tempOptionIndexForCB = optionIndexForCB;
                        optionIndexForCB++;

                        optionLn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkboxLn.getVisibility() == View.GONE) {
                                    checkboxLn.setVisibility(View.VISIBLE);
                                    optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_hide);
                                } else {
                                    checkboxLn.setVisibility(View.GONE);
                                    optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_view);
                                }
                            }
                        });

                        if (checkboxLn.getVisibility() == View.GONE) {
                            optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_view);
                        } else {
                            optionSubTitleTv2.setBackgroundResource(R.drawable.ab_imagebutton_itemmodifier_hide);
                        }
                    }

                    tempOptionCount++;

                    newLn.addView(optionLn);
                    optionaddPopupOAListLinearLayout.addView(newLn);
                }
            }

            // 추가사항이 있을 때
            if (additionalCount > 0) {
                Cursor additionalCursor;

                TextView additionalTitleTv = new TextView(mContext);
                additionalTitleTv.setLayoutParams(statusTvLayoutParams2);
                additionalTitleTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                additionalTitleTv.setPadding(10, 5, 10, 5);
                additionalTitleTv.setText("Add Ingredients");
                if (GlobalMemberValues.isEloPro()) {
                    additionalTitleTv.setTextSize(20);
                }
                additionalTitleTv.setTextColor(Color.parseColor("#3e3d42"));
                additionalTitleTv.setPaintFlags(additionalTitleTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                additionalTitleTv.setBackgroundResource(R.drawable.roundlayout_optiontitle2);
                additionalTitleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                optionaddPopupOAListLinearLayout.addView(additionalTitleTv);

                LinearLayout additionalLn = new LinearLayout(mContext);
                additionalLn.setLayoutParams(statusLnLayoutParams1);
                additionalLn.setOrientation(LinearLayout.VERTICAL);
                //additionalLn.setBackgroundColor(Color.parseColor("#f8f8f8"));

                /** 다중선택 (Checkbox) 관련 *************************************************************************************************/
                // 옵션값 담을 벡터 객체 생성 및 크기지정
                strQuery = " select count(idx) from salon_storeservice_additional " +
                        " where svcIdx = '" + mServiceIdx + "' " +
                        " and adduseyn = 'Y' ";
                int tempVectorSizeForAD = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(strQuery));
                mAdditionalVectorForAD = new Vector<String>();            // Checkbox 옵션값을 담을 Vector
                mAdditionalVectorForAD.setSize(tempVectorSizeForAD);
                mCheckedAdditionalArrForAD = new String[tempVectorSizeForAD];
                GlobalMemberValues.logWrite("additionalItemValue", "전체 Additional 갯수 : " + tempVectorSizeForAD + "\n");
                /****************************************************************************************************************************/

                int additionalIndexForAD = 0;

                strQuery = " select idx, addname, addprice from salon_storeservice_additional " +
                        " where svcIdx = '" + mServiceIdx + "' " +
                        " and adduseyn = 'Y' " +
                        " order by sortnum asc, addname asc, idx desc";
                additionalCursor = dbInit.dbExecuteRead(strQuery);
                while (additionalCursor.moveToNext()) {
                    // 선택 옵션값 초기화
                    mCheckedAdditionalArrForAD[additionalIndexForAD] = "";

                    String getAdditionalIdx = "";
                    String getAdditionalName = "";
                    String getAdditionalPrice = "";
                    double getAdditionalPriceDbl = 0;

                    getAdditionalIdx = GlobalMemberValues.getDBTextAfterChecked(additionalCursor.getString(0), 1);
                    getAdditionalName = GlobalMemberValues.getDBTextAfterChecked(additionalCursor.getString(1), 1);
                    getAdditionalPrice = GlobalMemberValues.getDBTextAfterChecked(additionalCursor.getString(2), 1);
                    getAdditionalPriceDbl = GlobalMemberValues.getDoubleAtString(getAdditionalPrice);

                    String getAdditionalNamePriceStrForAD = getAdditionalName + "-JJJ-" + getAdditionalPrice;

                    String tempAdditionalValueForAD = getAdditionalName;
                    if (GlobalMemberValues.getDoubleAtString(getAdditionalPrice) > 0) {
                        tempAdditionalValueForAD += " ($" + GlobalMemberValues.getCommaStringForDouble(getAdditionalPrice) + ")";
                    }

                    mAdditionalVectorForAD.add(additionalIndexForAD, getAdditionalNamePriceStrForAD);

                    GlobalMemberValues.logWrite("additionalItemValue", "additionalIndexForAD : " + additionalIndexForAD + "\n");
                    GlobalMemberValues.logWrite("additionalItemValue", "mAdditionalVectorForAD(additionalIndexForAD) : " + mAdditionalVectorForAD.get(additionalIndexForAD) + "\n");


                    // 체크박스 생성 ----------------------------------------------
                    // 체크박스
                    CheckBox checkBox = new CheckBox(mContext);
                    if (additionalIndexForAD == 0) {
                        checkBox.setLayoutParams(statusTvLayoutParams5);
                    } else {
                        checkBox.setLayoutParams(statusTvLayoutParams3);
                    }
                    checkBox.setPadding(0, 0, 0, 0);
                    checkBox.setTag(additionalIndexForAD);
                    checkBox.setText(tempAdditionalValueForAD);
                    checkBox.setTextColor(Color.parseColor("#3e3d42"));
                    checkBox.setTextSize(16);
                    if (GlobalMemberValues.isEloPro()) {
                        checkBox.setTextSize(20);
                    }
                    checkBox.setHighlightColor(Color.parseColor("#3e3d42"));

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            int checkIndex = GlobalMemberValues.getIntAtString(buttonView.getTag().toString());
                            GlobalMemberValues.logWrite("additionalItemValue", "checkIndex : " + checkIndex + "\n");
                            if (isChecked) {
                                mCheckedAdditionalArrForAD[checkIndex] = mAdditionalVectorForAD.get(checkIndex);
                                GlobalMemberValues.logWrite("additionalItemValue", "mAdditionalVectorForAD.get(checkIndex) : " + mAdditionalVectorForAD.get(checkIndex) + "\n");
                            } else {
                                mCheckedAdditionalArrForAD[checkIndex] = "";
                            }
                            GlobalMemberValues.logWrite("additionalItemValue", "checked Value : " + mCheckedAdditionalArrForAD[checkIndex] + "\n");

                            calOptionAdditional();
                        }
                    });
                    // -----------------------------------------------------------
                    additionalLn.addView(checkBox);
                    additionalIndexForAD++;
                }
                optionaddPopupOAListLinearLayout.addView(additionalLn);
            }
        }
    }

    private ArrayList<String> calOptionAdditional() {
        ArrayList<String> returnArrList;
        returnArrList = new ArrayList<String>();

        String returnOptionTxtForRB = "";
        String returnOptionTxtForCB = "";
        String returnAdditionalTxt = "";

        double returnOptionPriceForRB = 0;
        double returnOptionPriceForCB = 0;
        double returnAdditionalPrice = 0;

        if (mCheckedOptionArrForRB != null) {
            int vSizeForRB = mCheckedOptionArrForRB.length;
            for (int i = 0; i < vSizeForRB; i++) {
                //GlobalMemberValues.logWrite("OAValue", "Option RadioButton value : " + mCheckedOptionArrForRB[i].toString() + "\n");

                String tempCheckedOptionArrForRB = "";
                if (mCheckedOptionArrForRB[i] != null) {
                    tempCheckedOptionArrForRB = mCheckedOptionArrForRB[i].toString();
                }
                if (!GlobalMemberValues.isStrEmpty(tempCheckedOptionArrForRB)) {
                    String tempCheckedOptionArrSplitForRB[] = null;
                    tempCheckedOptionArrSplitForRB = tempCheckedOptionArrForRB.split("-JJJ-");

                    returnOptionPriceForRB += GlobalMemberValues.getDoubleAtString(tempCheckedOptionArrSplitForRB[1]);

                    String tempOptionPriceTxtForRB = tempCheckedOptionArrSplitForRB[0];
                    if (GlobalMemberValues.getDoubleAtString(tempCheckedOptionArrSplitForRB[1]) > 0) {
                        tempOptionPriceTxtForRB += "($" + GlobalMemberValues.getCommaStringForDouble(tempCheckedOptionArrSplitForRB[1]) + ")";
                    }
                    if (i == 0) {
                        returnOptionTxtForRB = tempOptionPriceTxtForRB;
                    } else {
                        returnOptionTxtForRB += ", " + tempOptionPriceTxtForRB;
                    }
                }
            }
        }

        if (mCheckedOptionArrForCB != null) {
            int vSizeForCB = mCheckedOptionArrForCB.length;
            for (int i = 0; i < vSizeForCB; i++) {
                if (!GlobalMemberValues.isStrEmpty(mCheckedOptionArrForCB[i])) {
                    String tempCheckedOptionArrSplitForCB[] = null;
                    tempCheckedOptionArrSplitForCB = mCheckedOptionArrForCB[i].split("-JJJ-");

                    returnOptionPriceForCB += GlobalMemberValues.getDoubleAtString(tempCheckedOptionArrSplitForCB[1]);

                    String tempOptionPriceTxtForCB = tempCheckedOptionArrSplitForCB[0];
                    if (GlobalMemberValues.getDoubleAtString(tempCheckedOptionArrSplitForCB[1]) > 0) {
                        tempOptionPriceTxtForCB += "($" + GlobalMemberValues.getCommaStringForDouble(tempCheckedOptionArrSplitForCB[1]) + ")";
                    }

                    if (i == 0) {
                        returnOptionTxtForCB = tempOptionPriceTxtForCB;
                    } else {
                        if (!GlobalMemberValues.isStrEmpty(returnOptionTxtForCB)) {
                            returnOptionTxtForCB += ", " + tempOptionPriceTxtForCB;
                        } else {
                            returnOptionTxtForCB += tempOptionPriceTxtForCB;
                        }
                    }
                }
            }
        }

        if (mCheckedAdditionalArrForAD != null) {
            int vSizeForAD = mCheckedAdditionalArrForAD.length;
            for (int i = 0; i < vSizeForAD; i++) {
                GlobalMemberValues.logWrite("OAValue", "Additional value : " + mCheckedAdditionalArrForAD[i].toString() + "\n");

                String tempCheckedAdditionalArr = mCheckedAdditionalArrForAD[i].toString();
                if (!GlobalMemberValues.isStrEmpty(tempCheckedAdditionalArr)) {
                    String tempCheckedAdditionalArrSplit[] = null;
                    tempCheckedAdditionalArrSplit = tempCheckedAdditionalArr.split("-JJJ-");

                    returnAdditionalPrice += GlobalMemberValues.getDoubleAtString(tempCheckedAdditionalArrSplit[1]);

                    String tempAdditionalPriceTxt = tempCheckedAdditionalArrSplit[0];
                    if (GlobalMemberValues.getDoubleAtString(tempCheckedAdditionalArrSplit[1]) > 0) {
                        tempAdditionalPriceTxt += "($" + GlobalMemberValues.getCommaStringForDouble(tempCheckedAdditionalArrSplit[1]) + ")";
                    }

                    if (i == 0) {
                        returnAdditionalTxt = tempAdditionalPriceTxt;
                    } else {
                        if (!GlobalMemberValues.isStrEmpty(returnAdditionalTxt)) {
                            returnAdditionalTxt += ", " + tempAdditionalPriceTxt;
                        } else {
                            returnAdditionalTxt += tempAdditionalPriceTxt;
                        }
                    }
                }
            }
        }

        GlobalMemberValues.logWrite("OAValue", "returnOptionTxtForRB -------------- : " + returnOptionTxtForRB + "\n");
        GlobalMemberValues.logWrite("OAValue", "returnOptionTxtForCB -------------- : " + returnOptionTxtForCB + "\n");
        GlobalMemberValues.logWrite("OAValue", "returnAdditionalTxt -------------- : " + returnAdditionalTxt + "\n");

        String allOptionTxt = "";
        String tempAllOptionTxt = "";
        if (!GlobalMemberValues.isStrEmpty(returnOptionTxtForRB)) {
            allOptionTxt = returnOptionTxtForRB;
        }
        if (!GlobalMemberValues.isStrEmpty(allOptionTxt)) {
            if (!GlobalMemberValues.isStrEmpty(returnOptionTxtForCB)) {
                allOptionTxt += ", " + returnOptionTxtForCB;
            }
        } else {
            if (!GlobalMemberValues.isStrEmpty(returnOptionTxtForCB)) {
                allOptionTxt += returnOptionTxtForCB;
            }
        }
        if (!GlobalMemberValues.isStrEmpty(allOptionTxt)) {
            tempAllOptionTxt = allOptionTxt;
            //allOptionTxt = "[Option]\n" + allOptionTxt;
            allOptionTxt = "" + allOptionTxt;
        }
        GlobalMemberValues.logWrite("OAValue", "allOptionTxt -------------- : \n" + allOptionTxt + "\n");

        String allAdditionalTxt = "";
        if (!GlobalMemberValues.isStrEmpty(returnAdditionalTxt)) {
            //allAdditionalTxt = "[Add Ingredients]\n" + returnAdditionalTxt;
            allAdditionalTxt = "\n\n" + returnAdditionalTxt;
        }
        GlobalMemberValues.logWrite("OAValue", "returnAdditionalTxt -------------- : \n" + returnAdditionalTxt + "\n");

        String allOptionAdditionalTxt = "";
        if (!GlobalMemberValues.isStrEmpty(allOptionTxt)) {
            allOptionAdditionalTxt = allOptionTxt;
            if (!GlobalMemberValues.isStrEmpty(allAdditionalTxt)) {
                allOptionAdditionalTxt += "\n\n" + allAdditionalTxt;
            }
        } else {
            allOptionAdditionalTxt = allAdditionalTxt;
        }

        GlobalMemberValues.logWrite("OAValue", "allOptionAdditionalTxt -------------- : \n" + allOptionAdditionalTxt + "\n");

        if (!GlobalMemberValues.isStrEmpty(allOptionAdditionalTxt)) {
            optionaddPopupTxtTextView.setText("");
            allOptionAdditionalTxt = GlobalMemberValues.getReplaceText(allOptionAdditionalTxt, ", ", "\n");
            allOptionAdditionalTxt = GlobalMemberValues.getReplaceText(allOptionAdditionalTxt, "($", "  ($");
            optionaddPopupTxtTextView.append(allOptionAdditionalTxt);
        }

        mAmountPrice = mBasicPrice + returnOptionPriceForRB + returnOptionPriceForCB + returnAdditionalPrice;
        optionaddPopupAmountTextView.setText("$" + GlobalMemberValues.getCommaStringForDouble(mAmountPrice + ""));

        returnArrList.add(0, tempAllOptionTxt);
        returnArrList.add(1, (returnOptionPriceForRB + returnOptionPriceForCB + ""));
        returnArrList.add(2, returnAdditionalTxt);
        returnArrList.add(3, (returnAdditionalPrice + ""));
        returnArrList.add(4, "");
        returnArrList.add(5, "0");

        return returnArrList;
    }
}
