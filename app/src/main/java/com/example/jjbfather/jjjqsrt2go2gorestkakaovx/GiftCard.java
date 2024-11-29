package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripeCardParser;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class GiftCard {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    DatabaseInit dbInit;

    // 기프트카드 인덱스
    static String mGiftCardIdx;
    // 기프트카드 이름
    String mGiftCardName;
    // 기프트카드 가격
    static String mGiftCardPrice;
    // 기프트카드 Pointratio
    // 기프트카드 Pointratio
    String mGiftCardPointratio;

    // 기프트카드 번호
    static String mGiftCardNumber;


    // Service / Product 여부
    String saveType;

    static String mCardNumberEditTextValue = "";
    String mPriceEditTextValue = "";

    // 잔액
    static double nowRemainingPrice = 0.0;

    // 기프트카드 가격 임시저장 객체
    static double tempGcPriceDouble;

    Button closeBtn;
    Button preClickButton;

    TextView giftcardTitleEditText;

    private Button giftCardSuButton1,giftCardSuButton2,giftCardSuButton3;
    private Button giftCardSuButton4,giftCardSuButton5,giftCardSuButton6;
    private Button giftCardSuButton7,giftCardSuButton8,giftCardSuButton9;
    private Button giftCardSuButton0,giftCardSuButton00,giftCardSuButtonBack;
    private Button giftCardSuButtonV;
    private Button mGiftCardNumberButton;
    private Button giftCardBalanceBtn;

    static EditText mGiftCardNumberEditText;
    EditText mGiftCardPriceEditText;
    EditText mGiftCardRemainingEditText;

    TextView giftcardNumberTitleTextView, giftcardPriceTitleTextView, giftcardRemainingTitleTextView;

    public String mGiftCardMSR = "Y";

    // 프랜차이즈 관련 추가
    ProgressDialog dialog;
    String mGetGiftCardBalanceInfo = "";

    public GiftCard(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // QuickSale 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD.setOnClickListener(giftCardBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD.setOnClickListener(giftCardBtnClickListener);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        // saveType 기본설정 (기프트카드로 설정)
        saveType = "2";
    }

    public void setContents() {
        // 초기화
        saveType = "2";
        mGiftCardIdx = "";
        mGiftCardName = "";
        mGiftCardPrice = "";
        mGiftCardNumber = "";
        mGiftCardPointratio = "";

        mPriceEditTextValue = "";
        preClickButton = null;

        mCardNumberEditTextValue = "";

        mGiftCardMSR = "Y";

        // MSR 활성화
        try {
            //magSwiper(MainActivity.mActivity, MainActivity.mContext);
        } catch (Exception e) {
        }

        /** 기프트카드 배치 ****************************************************************************************/
        // 기프트카드 배열 가져오기 (GetDataAtSQLite 클래스의 getGiftCardInfo 메소드를 통해 가져온다)
        String[] strArrGiftCardMenu = dataAtSqlite.getGiftCardInfo();

        String strGiftCardInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempGiftCardName = "";       // 기프트카드 상품명 임시저장용 객체 선언

        String[] tempGiftCardInfoArr = null;     // TemporaryGiftCardInfo 객체 생성용 String 배열

        // 해당 스토어의 최대 기프트카드 상품수(GlobalMemberValues.STOREMAXGIFTCARDSU) 만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.STOREMAXGIFTCARDSU; i++){
            if (!GlobalMemberValues.isStrEmpty(strArrGiftCardMenu[i])) {
                GlobalMemberValues.logWrite("GIFTCARD", "Data : " + strArrGiftCardMenu[i] + "\n");
                strGiftCardInfo = strArrGiftCardMenu[i];
                String[] strGiftCardInfoArr = strGiftCardInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempGiftCardName = strGiftCardInfoArr[9];
                if (!GlobalMemberValues.isStrEmpty(tempGiftCardName)) {         // 기프트카드 상품명이 있을 경우에만..
                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    Button giftCardNameBtn
                            = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                            .findViewWithTag("giftCardMenuButtonTag" + (i+1));
                    TextView giftCardNameTv
                            = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                            .findViewWithTag("giftCardMenuTextViewTag" + (i+1));

                    giftCardNameBtn.setText(strGiftCardInfoArr[9]);
                    giftCardNameBtn.setBackgroundResource(R.drawable.roundlayout_button_topcategory);
                    giftCardNameBtn.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKTEXTCOLOR1));
                    giftCardNameBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

                    giftCardNameTv.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKBACKGROUNDCOLOR1));

                    // TemporaryGiftCardInfo 객체 생성용 배열 생성
                    tempGiftCardInfoArr = new String[]{strGiftCardInfoArr[0], strGiftCardInfoArr[1], strGiftCardInfoArr[2],
                            strGiftCardInfoArr[3],strGiftCardInfoArr[4], strGiftCardInfoArr[5], strGiftCardInfoArr[6],
                            strGiftCardInfoArr[7],strGiftCardInfoArr[8], strGiftCardInfoArr[9]};

                    // GiftCard 의 정보를 임시로 저장하기 위한 클래스인 TemporaryGiftCardInfo 를 이용하여 카테고리정보 저장
                    TemporaryGiftCardInfo tempGiftCardInfo = new TemporaryGiftCardInfo(tempGiftCardInfoArr);
                    // 위에서 생성한 클래스를 Tag 값으로 저장 (setTag)
                    // setTag 로 객체 저장시 카테고리버튼의 아이디를 KEY 값으로 사용한다.
                    if (tempGiftCardInfo != null) {
                        giftCardNameBtn.setTag(giftCardNameBtn.getId(), tempGiftCardInfo);
                    }

                    // 카테고리 클릭시 이벤트
                    giftCardNameBtn.setOnClickListener(mGiftCardButtonListner);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopGiftCard", "카테고리정보 " + i + " : " + strArrGiftCardMenu[i] + "\n");
            }
        }
        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardCloseBtnTag");
        closeBtn.setOnClickListener(giftCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        giftcardTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardTitleEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftcardTitleEditText.setText("");
            giftcardTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_title);
        } else {
            giftcardTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    giftcardTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 기프트카드번호 EditText 객체 생성 및 리스너 연결
        mGiftCardNumberEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardNumberEditTextTag");
        //mGiftCardNumberEditText.setOnClickListener(mEditTextClick);
        mGiftCardNumberEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        mGiftCardNumberEditText.setOnTouchListener(mEditTextTouch);
        mGiftCardNumberEditText.setOnFocusChangeListener(mCardNumberFocusChangeListener);
        mGiftCardNumberEditText.requestFocus();

        //mGiftCardNumberEditText.setOnTouchListener(mEditTextTouch);
        // 기프트카드번호 검색 버튼 객체 생성 및 리스너 연결
        mGiftCardNumberButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardNumberButtonTag");
        mGiftCardNumberButton.setOnClickListener(giftCardBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            mGiftCardNumberButton.setText("");
            mGiftCardNumberButton.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_search);
        } else {
            mGiftCardNumberButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    mGiftCardNumberButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 가격 EditText 객체 생성 및 리스너 연결
        mGiftCardPriceEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardPriceEditTextTag");
        //mGiftCardPriceEditText.setOnClickListener(mEditTextClick);
        mGiftCardPriceEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        mGiftCardPriceEditText.setOnTouchListener(mEditTextTouch);
        // 잔액(Remaining) EditText 객체 생성 및 리스너 연결
        mGiftCardRemainingEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardRemainingEditTextTag");
//        /mGiftCardRemainingEditText.setOnClickListener(mEditTextClick);
        mGiftCardRemainingEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        mGiftCardRemainingEditText.setOnTouchListener(mEditTextTouch);

        giftcardNumberTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardNumberTitleTextViewTag");
        giftcardNumberTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        giftcardPriceTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardPriceTitleTextViewTag");
        giftcardPriceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        giftcardRemainingTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftcardRemainingTitleTextViewTag");
        giftcardRemainingTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        giftCardSuButton1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton8 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton9 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton0 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButton00 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            giftCardSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_number);
        } else {
            giftCardSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        giftCardSuButtonBack = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButtonBack.setText("");
            giftCardSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_delete);
        }
        giftCardSuButtonV = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardSuButtonV.setText("");
            giftCardSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_giftcardsale_enter);
        }

        giftCardSuButton1.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton2.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton3.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton4.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton5.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton6.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton7.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton8.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton9.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton0.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButton00.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButtonBack.setOnClickListener(giftCardBtnClickListener);
        giftCardSuButtonV.setOnClickListener(giftCardBtnClickListener);
        /***********************************************************************************************************/

        // EditText 초기화
        mGiftCardNumberEditText.setText("");
        mGiftCardPriceEditText.setText("");
        mGiftCardRemainingEditText.setText("");

        GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
        GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
        GlobalMemberValues.setKeyPadHide(context, mGiftCardRemainingEditText);

        giftCardBalanceBtn = (Button)mActivity.findViewById(R.id.giftCardBalanceBtn);
        giftCardBalanceBtn.setOnClickListener(giftCardBtnClickListener);

        // 상단 Reload 버튼
        Button giftCardNameBtn_reload
                = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardMenuButtonTag_reload");
        TextView giftCardNameTv_reload
                = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD
                .findViewWithTag("giftCardMenuTextViewTag_reload");
        giftCardNameBtn_reload.setText("RELOAD");
        String[] tempGiftcard_reloadinfo = "0||0||0||0||||||||||||Gift Card Reload".split(GlobalMemberValues.STRSPLITTER1);
        giftCardNameBtn_reload.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKBACKGROUNDCOLOR1));
        giftCardNameBtn_reload.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKTEXTCOLOR1));
        giftCardNameBtn_reload.setTextSize(GlobalMemberValues.BASICGIFTCARDSALETEXTSIZE);
        giftCardNameTv_reload.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKBACKGROUNDCOLOR1));
        giftCardNameBtn_reload.setOnClickListener(mGiftCardReloadButtonListner);

        giftCardNameBtn_reload.setText(tempGiftcard_reloadinfo[9]);
        giftCardNameBtn_reload.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKBACKGROUNDCOLOR1));
        giftCardNameBtn_reload.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKTEXTCOLOR1));
        giftCardNameBtn_reload.setTextSize(GlobalMemberValues.BASICGIFTCARDSALETEXTSIZE);

        giftCardNameTv_reload.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKBACKGROUNDCOLOR1));
    }

    View.OnFocusChangeListener mCardNumberFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //GlobalMemberValues.displayDialog(context, "","포커스 이동...", "Close");
                String getMSRData = mGiftCardNumberEditText.getText().toString();
                if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);
                    //GlobalMemberValues.displayDialog(context, "","reading value : " + getMSRData, "Close");
                    mGiftCardNumberEditText.setText(getMSRData);
                    setGiftCardPriceAndRemaining();
                }
            }
        }
    };

    View.OnClickListener mGiftCardButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // 클릭한 카테고리 버튼이 바로 이전에 클릭된 버튼 (preClickButton) 이 아닐 경우에만..
            if (true) {
                LogsSave.saveLogsInDB(75);
                if (preClickButton != null) {
                    /** 전에 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    preClickButton.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKBACKGROUNDCOLOR1));
                    preClickButton.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKTEXTCOLOR1));
                    /*****************************************************************************************/
                }

                // 현재 클릭한 카테고리버튼에 Tag 저장되어 있는 TemporaryGiftCard 객체를 가져온다.
                TemporaryGiftCardInfo tempGc = (TemporaryGiftCardInfo)btn.getTag(btn.getId());
                // ★★★★★★★★반드시 해당 객체가 있는지 체크해 준다★★★★★★★★
                if (tempGc != null) {
                    /** 현재 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    btn.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKBACKGROUNDCOLOR1));
                    btn.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKTEXTCOLOR1));
                    /*****************************************************************************************/

                    if (tempGc.gcName != null) {
                        //GlobalMemberValues.logWrite("GiftCard-TempInstance", "카테고리 인덱스 : " + paramCateIdx + "\n");
                        mGiftCardIdx = tempGc.gcIdx;
                        mGiftCardName = tempGc.gcName;
                        mGiftCardPrice = tempGc.gcPrice;
                        mGiftCardPointratio = tempGc.gcPointRatio;

                        double insGcPrice = GlobalMemberValues.getDoubleAtString(tempGc.gcPrice);

                        String insPdSaleYN = "N";
                        // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
                        if ((!GlobalMemberValues.isStrEmpty(tempGc.gcSaleStart)
                                && DateMethodClass.getDiffDayCount(tempGc.gcSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                                && (!GlobalMemberValues.isStrEmpty(tempGc.gcSaleEnd)
                                && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, tempGc.gcSaleEnd) >= 0)
                                &&!GlobalMemberValues.isStrEmpty(tempGc.gcSalePrice)) {
                            GlobalMemberValues.logWrite("GIFTCARD", "SALE PRICE : " + tempGc.gcSalePrice + "\n");
                            insGcPrice = GlobalMemberValues.getDoubleAtString(tempGc.gcSalePrice);
                            insPdSaleYN = "Y";
                        }
                        // -------------------------------------------------------------------------------------
                        // 기프트카드 상품선택시 기프트카드번호 및 잔액은 무조건 초기화
                        nowRemainingPrice = 0.0;
                        mGiftCardNumber = "";
                        mGiftCardRemainingEditText.setText("");
                        mGiftCardNumberEditText.setText("");

                        mCardNumberEditTextValue = "";

                        mPriceEditTextValue = "";

                        mGiftCardPriceEditText.setText(GlobalMemberValues.getStringFormatNumber(insGcPrice, "2"));
                    } else {
                        //
                    }

                    // 현재 클릭한 버튼객체를 preClickButton 에 담는다.
                    preClickButton = btn;
                }
            }
        }
    };

    View.OnClickListener giftCardBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_main_side_giftcard :
                case R.id.mainRightBottom_GiftCard : {
                    if (!GlobalMemberValues.checkEmployeePermission( TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<21>")){
                        GlobalMemberValues.displayDialog(mActivity, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------
                    LogsSave.saveLogsInDB(105);
                    GlobalMemberValues.setFrameLayoutVisibleChange("giftcard");
                    setContents();
                    break;
                }
                case R.id.giftCardCloseBtn : {
                    LogsSave.saveLogsInDB(73);
                    setInit();

                    preClickButton = null;

                    if (GlobalMemberValues.isDeviceClover()) {
                        GlobalMemberValues.cloverBarcodeScannerOff();
                    }

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);

                    GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    break;
                }
                case R.id.giftcardNumberButton : {
                    LogsSave.saveLogsInDB(74);
                    setGiftCardPriceAndRemaining();
                    break;
                }
                case R.id.giftCardSuButton1 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton2 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton3 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton4 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton5 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton6 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton7 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton8 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton9 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton0 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButton00 : {
                    LogsSave.saveLogsInDB(76);
                    numberButtonClick(giftCardSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    break;
                }
                case R.id.giftCardSuButtonBack : {
                    LogsSave.saveLogsInDB(77);
                    // 카드번호 EditText
                    if (mGiftCardNumberEditText.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mCardNumberEditTextValue);
                        if (!GlobalMemberValues.isStrEmpty(mCardNumberEditTextValue)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mCardNumberEditTextValue = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mCardNumberEditTextValue)) {
                                mCardNumberEditTextValue = "";
                            }
                        } else {
                            mCardNumberEditTextValue = "";
                        }

                        mGiftCardNumberEditText.setText(mCardNumberEditTextValue);

                        GlobalMemberValues.logWrite("GIFTCARD", "mCardNumberEditTextValue : " + mCardNumberEditTextValue + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                        // 커서를 먼저 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(mGiftCardNumberEditText);
                    }


                    // 가격 EditText
                    if (mGiftCardPriceEditText.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mPriceEditTextValue);
                        if (!GlobalMemberValues.isStrEmpty(mPriceEditTextValue)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mPriceEditTextValue = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mPriceEditTextValue)) {
                                mPriceEditTextValue = "0";
                            }
                        } else {
                            mPriceEditTextValue = "0";
                        }

                        String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
                        mGiftCardPriceEditText.setText(inputSu);

                        GlobalMemberValues.logWrite("GIFTCARD", "mPriceEditTextValue : " + mPriceEditTextValue + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                        // 커서를 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(mGiftCardPriceEditText);
                    }

                    break;
                }
                case R.id.giftCardSuButtonV : {

                    double tempRemaining = GlobalMemberValues.getDoubleAtString(mGiftCardRemainingEditText.getText().toString());
                    if (tempRemaining == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please touch the search button", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(78);
                    String tempGifcardNumber = mGiftCardNumberEditText.getText().toString();
                    if (!GlobalMemberValues.isStrEmpty(tempGifcardNumber)) {
                        // 먼저 좌측 cart 리스트에 구매하려는 기프트카드와 번호가 일치하는 기프트카드가 있는지 체크한다. ----------------------
                        int tempSavedGiftCardNumberCount = 0;
                        for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
                            String item_giftcardNumberToSave = tempSaleCart.mGiftCardNumber;
                            if (tempGifcardNumber.equals(item_giftcardNumberToSave) || tempGifcardNumber == item_giftcardNumberToSave) {
                                tempSavedGiftCardNumberCount++;
                            }
                        }
                        if (tempSavedGiftCardNumberCount > 0) {
                            GlobalMemberValues.displayDialog(context, "Warning",
                                    "This gift card number is on the sale list\nPlease enter another number", "Close");
                            mGiftCardNumberEditText.setText("");
                            mCardNumberEditTextValue = "";
                            return;
                        }
                        // -----------------------------------------------------------------------------------------------------------

                        // 구매금액이 0.00 이면 구매불가능하게 처리
                        double tempGiftCardPriceEditText = GlobalMemberValues.getDoubleAtString(mGiftCardPriceEditText.getText().toString());
                        if (tempGiftCardPriceEditText == 0.00) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Enter giftcard price", "Close");
                            mGiftCardPriceEditText.requestFocus();
                            return;
                        }

                        String sHoldCode = MainMiddleService.mHoldCode;                      // 홀드코드 (처음저장시에는 값이 없음)
                        String sQty = "1";                          // 수량 (처음저장시 무조건 1)
                        String sSaveType = saveType;                     // 0 : 서비스       1: 상품          2: 기프트 카드

                        String insCastegoryIdx = "";                                // 카테고리 인덱스
                        String insSvcIdx = mGiftCardIdx;                       // Gift Card 상품 인덱스

                        String insServiceProductName = "GIFTCARD - " + mGiftCardName;
                        String insStrFileName = "";
                        String insStrFilePath = "";

                        String insPrice
                                = GlobalMemberValues.getReplaceText(mGiftCardPriceEditText.getText().toString(), "$", "");
                        String insSalePrice = "0";
                        String insSaleStart = "";
                        String insSaleEnd = "";

                        String insCommissionRatio = String.valueOf(GlobalMemberValues.GIFTCARD_COMMISSIONRATIO);
                        String insCommissionType = String.valueOf(GlobalMemberValues.GIFTCARD_COMMISSIONRATIOTYPE);
                        String insPointRatio = mGiftCardPointratio;

                        String insPositoinNo = mGiftCardPointratio;
                        String insSetMenyYN = "N";

                        String insQuickSaleYN = "N";                  // quick sale 여부

                        String insCategoryName = "GIFTCARD";

                        String insGiftCardNumber = tempGifcardNumber;
                        String insGiftCardSavePrice = GlobalMemberValues.getStringFormatNumber(tempGcPriceDouble, "2");

                        String insCustomerId = "";
                        String insCustomerName = "";
                        String insCustomerPhone = "";
                        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                            insCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                            insCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                            insCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                        }

                        String insEmpIdx = "";
                        String insEmpName = "";
                        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                            insEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                            insEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                        }

                        if (GlobalMemberValues.isStrEmpty(insServiceProductName)) {
                            insServiceProductName = mGiftCardName;
                        }

                        // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                        String paramsString[] = {
                                sQty, sHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, insCastegoryIdx, insSvcIdx,
                                insServiceProductName, insStrFileName, insStrFilePath,
                                insPrice, insSalePrice, insSaleStart, insSaleEnd,
                                insCommissionRatio, insCommissionType, insPointRatio,
                                insPositoinNo, insSetMenyYN,
                                insCustomerId, insCustomerName, insCustomerPhone, sSaveType, insEmpIdx, insEmpName, insQuickSaleYN, insCategoryName,
                                insGiftCardNumber, insGiftCardSavePrice};

                        // common gratuity 관련
                        GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                        MainMiddleService.insertTempSaleCart(paramsString);

                        // common gratuity 관련
                        GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                        //saveType = "0";

                        mGiftCardIdx = "";
                        mGiftCardName = "";
                        mGiftCardNumber = "";
                        mGiftCardPrice = "";
                        mGiftCardPointratio = "";

                        mPriceEditTextValue = "";
                        mGiftCardNumberEditText.setText("");
                        mGiftCardPriceEditText.setText("");
                        mGiftCardRemainingEditText.setText("");

                        mCardNumberEditTextValue = "";



                        //preClickButton = null;
                    }
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);

                    break;
                }
                case R.id.giftCardBalanceBtn :{
                    LogsSave.saveLogsInDB(72);
                    Intent GiftCardBalanceCheck = new Intent(MainActivity.mContext, com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GiftCardBalanceCheck.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(GiftCardBalanceCheck);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };


    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.giftcardPriceEditText : {
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardPriceEditText);
                    mGiftCardPriceEditText.requestFocus();
                    mGiftCardPriceEditText.setSelection(mGiftCardPriceEditText.length());

                    break;
                }
                case R.id.giftcardNumberEditText : {
                    GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
                    mGiftCardNumberEditText.requestFocus();
                    mGiftCardNumberEditText.setSelection(mGiftCardNumberEditText.length());

                    break;
                }
            }

            return true;
        }
    };

    private void numberButtonClick(Button btn) {
        // 카드번호 EditText
        if (mGiftCardNumberEditText.isFocused()) {
            GlobalMemberValues.setKeyPadHide(context, mGiftCardNumberEditText);
            //GlobalMemberValues.displayDialog(context, "Info", "여기..", "Close");

            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());

            sb.append(mCardNumberEditTextValue).append(btn.getText().toString());
            mCardNumberEditTextValue = sb.toString();
            mGiftCardNumberEditText.setText(mCardNumberEditTextValue);

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(mGiftCardNumberEditText);
        }

        // 가격 EditText
        if (mGiftCardPriceEditText.isFocused()) {
            // 기프트카드번호가 있고 잔액확인이 끝났을 때 price 를 변경못하게 하는 경우 아래코드 사용...
            /**
             if (GlobalMemberValues.isStrEmpty(mGiftCardNumber) && nowRemainingPrice == 0.0) {
             StringBuilder sb = new StringBuilder();
             sb.delete(0, sb.toString().length());
             if (mPriceEditTextValue.length() < 12) {
             sb.append(mPriceEditTextValue).append(btn.getText().toString());
             Long tempNumber = Long.parseLong(sb.toString());
             mPriceEditTextValue = String.valueOf(tempNumber);
             String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
             mGiftCardPriceEditText.setText(inputSu);
             }
             }
             **/
            // 기프트카드번호가 있고 잔액확인이 끝났을 경우에도 price 를 변경할 수 있는 경우 아래 코드 사용...
            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mPriceEditTextValue.length() < 12) {
                sb.append(mPriceEditTextValue).append(btn.getText().toString());
                Long tempNumber = Long.parseLong(sb.toString());
                mPriceEditTextValue = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
                mGiftCardPriceEditText.setText(inputSu);
            }

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(mGiftCardPriceEditText);
        }
    }

    public void setGiftCardPriceAndRemaining() {
        String tempGcNumber = mGiftCardNumberEditText.getText().toString();
        if (!GlobalMemberValues.isStrEmpty(tempGcNumber)) {
            if (!GlobalMemberValues.isStrEmpty(mGiftCardIdx)) {
                // 기프트카드 번호
                tempGcNumber = mGiftCardNumberEditText.getText().toString();
                // 기프트카드 판매가격
                tempGcPriceDouble = GlobalMemberValues.getDoubleAtString(mGiftCardPrice);
                if (tempGcPriceDouble == 0) {
                    tempGcPriceDouble = GlobalMemberValues.getDoubleAtString(mGiftCardPriceEditText.getText().toString());
                }
                if (tempGcPriceDouble == 0) {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Enter price", "Close");
                    return;
                }

                String tempNowRemainingPrice = "";

                if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                    dialog = new JJJCustomAnimationDialog(mActivity);
                    dialog.show();
                    final String finalTempGcNumber = tempGcNumber;
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                if (!GlobalMemberValues.isOnline().equals("00")) {
                                    GlobalMemberValues.showDialogNoInternet(context);
                                } else {
                                    API_download_giftcardbalance apiGiftCardBalanceInfoDownload = new API_download_giftcardbalance(finalTempGcNumber);
                                    apiGiftCardBalanceInfoDownload.execute(null, null, null);
                                    try {
                                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                        if (apiGiftCardBalanceInfoDownload.mFlag) {
                                            mGetGiftCardBalanceInfo = apiGiftCardBalanceInfoDownload.mReturnValue;
                                        }
                                    } catch (InterruptedException e) {
                                        mGetGiftCardBalanceInfo = "";
                                    }
                                }
                            }
                            // ---------------------------------------------------------------------------------
                            // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                            giftCardBalanceInfoDownloadHandler.sendEmptyMessage(0);
                            // ---------------------------------------------------------------------------------
                        }
                    });
                    thread.start();
                } else {
                    String tempSql = "select remainingPrice from salon_storegiftcard_number " +
                            " where gcNumber = '" + tempGcNumber + "' ";
                    tempNowRemainingPrice = MainActivity.mDbInit.dbExecuteReadReturnString(tempSql);
                    //GlobalMemberValues.logWrite("GiftCard Remaining", "잔액 : " + tempNowRemainingPrice + "\n");
                    setGiftCardBalance(tempNowRemainingPrice);
                }

            } else {
                new AlertDialog.Builder(MainActivity.mContext)
                        .setTitle("Gift Card")
                        .setMessage("Choose Giftcard")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                        .show();
            }
        }
        mGiftCardNumber = tempGcNumber;

        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(MainActivity.mContext, mGiftCardNumberEditText);
        GlobalMemberValues.setKeyPadHide(MainActivity.mContext, mGiftCardPriceEditText);
    }

    private Handler giftCardBalanceInfoDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetGiftCardBalanceInfo)) {
                // -------------------------------------------------------------------------------------
                String[] arrGiftCardBalanceInfo = mGetGiftCardBalanceInfo.split(GlobalMemberValues.STRSPLITTER2);
                int tempGiftCardBalanceCnt = 0;
                if (arrGiftCardBalanceInfo.length > 0) {
                    tempGiftCardBalanceCnt = GlobalMemberValues.getIntAtString(arrGiftCardBalanceInfo[0]);
                }
                String tempGiftCardBalance = "";
                if (arrGiftCardBalanceInfo.length > 1) {
                    tempGiftCardBalance = arrGiftCardBalanceInfo[1];
                }
                if (tempGiftCardBalanceCnt == 0) {
                    tempGiftCardBalance = "";
                }

                setGiftCardBalance(tempGiftCardBalance);

                // 프로그래스바를 사라지게 함 -------------------------------------------------------
                if (dialog != null || dialog.isShowing()) {
                    dialog.dismiss();
                }
                // -------------------------------------------------------------------------------------

                Toast.makeText(context, "Customer points have been synced with the cloud", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void setGiftCardBalance(String paramNowRemainingPrice) {
        if (!GlobalMemberValues.isStrEmpty(paramNowRemainingPrice)) {
            double remainingPriceDouble = GlobalMemberValues.getDoubleAtString(paramNowRemainingPrice);
            nowRemainingPrice = remainingPriceDouble;

            new AlertDialog.Builder(MainActivity.mContext)
                    .setTitle("Gift Card Already Exist")
                    .setMessage("Do you want to add?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mGiftCardRemainingEditText.setText(GlobalMemberValues
                                            .getStringFormatNumber((nowRemainingPrice + tempGcPriceDouble), "2"));

                                    //mGiftCardRemainingEditText.setText("");
                                    //mGiftCardNumberEditText.setText("");
                                    //mCardNumberEditTextValue = "";
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // NO 를 누를경우 카드번호 / 잔액 초기화
                            mGiftCardRemainingEditText.setText("");
                            mGiftCardNumberEditText.setText("");
                            mCardNumberEditTextValue = "";
                        }
                    })
                    .show();
        } else {
            mGiftCardRemainingEditText.setText(GlobalMemberValues
                    .getStringFormatNumber((nowRemainingPrice + tempGcPriceDouble), "2"));
        }
    }


    public void magSwiper(final Activity paramActivity, final Context paramContext) {
        paramActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MagStripDriver magStripDriver;

                magStripDriver = new MagStripDriver(MainActivity.mContext);
                magStripDriver.startDevice(); //Start the MagStripe Reader
                magStripDriver.registerMagStripeListener(new MagStripDriver.MagStripeListener() { //MageStripe Reader's Listener for notifying various events.

                    @Override
                    public void OnDeviceDisconnected() { //Fired when the Device has been Disconnected.
                        //  Toast.makeText(getActivity(), "Magnetic-Stripe Device Disconnected !", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnDeviceConnected() { //Fired when the Device has been Connected.
                        // TODO Auto-generated method stub
                        // Toast.makeText(getActivity(), "Magnetic-Stripe Device Connected !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCardSwiped(String cardData) { //Fired when a card has been swiped on the device.
                        try {
                            MagStripeCardParser mParser = new MagStripeCardParser(cardData); //Instance of card swipe reader
                            GlobalMemberValues.displayDialog(paramContext, "Info","cardData : " + cardData, "Close");

                            if(mParser.isDataParse()){
                                if(mParser.hasTrack1()){
                                    String getMSRData = mParser.getAccountNumber();

                                    GlobalMemberValues.displayDialog(paramContext, "Info","getMSRData : " + getMSRData, "Close");

                                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);

                                    if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                                        mGiftCardNumberEditText.setText("11111");
                                        setGiftCardPriceAndRemaining();
                                    }

                                    //paramTextView.setText(getMSRData); //Populate the edittext with the card number
                                }
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }


    // EditText 초기화 메소드
    public void setInit() {
        saveType = "2";
        mGiftCardIdx = "";
        mGiftCardName = "";
        mGiftCardNumber = "";
        mGiftCardPrice = "";
        mGiftCardPointratio = "";

        mCardNumberEditTextValue = "";

        mPriceEditTextValue = "";
        mGiftCardNumberEditText.setText("");
        mGiftCardPriceEditText.setText("");
        mGiftCardRemainingEditText.setText("");

        mGiftCardIdx = "";

        nowRemainingPrice = 0.0;

        mGiftCardMSR = "N";

    }

    View.OnClickListener mGiftCardReloadButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // 클릭한 카테고리 버튼이 바로 이전에 클릭된 버튼 (preClickButton) 이 아닐 경우에만..
            if (btn != preClickButton) {
                if (preClickButton != null) {
                    /** 전에 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    preClickButton.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKBACKGROUNDCOLOR1));
                    preClickButton.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_NOCLICKTEXTCOLOR1));
                    /*****************************************************************************************/
                }

                // 현재 클릭한 카테고리버튼에 Tag 저장되어 있는 TemporaryGiftCard 객체를 가져온다.
                TemporaryGiftCardInfo tempGc = (TemporaryGiftCardInfo)btn.getTag(btn.getId());
                // ★★★★★★★★반드시 해당 객체가 있는지 체크해 준다★★★★★★★★
                if (tempGc != null) {
                    /** 현재 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    btn.setBackgroundColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKBACKGROUNDCOLOR1));
                    btn.setTextColor(Color.parseColor(GlobalMemberValues.GIFTCARD_ONCLICKTEXTCOLOR1));
                    /*****************************************************************************************/

                    if (tempGc.gcName != null) {
                        //GlobalMemberValues.logWrite("GiftCard-TempInstance", "카테고리 인덱스 : " + paramCateIdx + "\n");
                        mGiftCardIdx = tempGc.gcIdx;
                        mGiftCardName = tempGc.gcName;
                        mGiftCardPrice = tempGc.gcPrice;
                        mGiftCardPointratio = tempGc.gcPointRatio;

                        double insGcPrice = GlobalMemberValues.getDoubleAtString(tempGc.gcPrice);

                        String insPdSaleYN = "N";
                        // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
                        if ((!GlobalMemberValues.isStrEmpty(tempGc.gcSaleStart)
                                && DateMethodClass.getDiffDayCount(tempGc.gcSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                                && (!GlobalMemberValues.isStrEmpty(tempGc.gcSaleEnd)
                                && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, tempGc.gcSaleEnd) >= 0)
                                &&!GlobalMemberValues.isStrEmpty(tempGc.gcSalePrice)) {
                            GlobalMemberValues.logWrite("GIFTCARD", "SALE PRICE : " + tempGc.gcSalePrice + "\n");
                            insGcPrice = GlobalMemberValues.getDoubleAtString(tempGc.gcSalePrice);
                            insPdSaleYN = "Y";
                        }
                        // -------------------------------------------------------------------------------------
                        // 기프트카드 상품선택시 기프트카드번호 및 잔액은 무조건 초기화
                        nowRemainingPrice = 0.0;
                        mGiftCardNumber = "";
                        mGiftCardRemainingEditText.setText("");
                        mGiftCardNumberEditText.setText("");

                        mCardNumberEditTextValue = "";

                        mPriceEditTextValue = "";

                        mGiftCardPriceEditText.setText(GlobalMemberValues.getStringFormatNumber(insGcPrice, "2"));
                    } else {
                        //
                    }

                    // 현재 클릭한 버튼객체를 preClickButton 에 담는다.
                    preClickButton = btn;
                }
            }
        }
    };
}


