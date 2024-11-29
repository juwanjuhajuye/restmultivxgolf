package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.magtek.MagStripeCardParser;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.util.ArrayList;
import java.util.Vector;

public class GiftCardBalanceCheck extends Activity {
    Activity activity;
    Context context;

    private LinearLayout parentLn;

    private LinearLayout giftCardBalanceCheckKeypadLn, giftCardBalanceCheckHistoryLn, giftcardeditLn;

    private Button giftCardBalanceCheck_suButton1, giftCardBalanceCheck_suButton2, giftCardBalanceCheck_suButton3;
    private Button giftCardBalanceCheck_suButton4, giftCardBalanceCheck_suButton5, giftCardBalanceCheck_suButton6;
    private Button giftCardBalanceCheck_suButton7, giftCardBalanceCheck_suButton8, giftCardBalanceCheck_suButton9;
    private Button giftCardBalanceCheck_suButton0, giftCardBalanceCheck_suButton00, giftCardBalanceCheck_suButtonBack;
    private Button giftCardBalanceCheck_suButtonV, giftCardBalanceCheck_suButtonX, giftCardBalanceCheck_suButtonX2;

    private Button giftcardBalanceCheckHistoryBackButton, giftcardBalanceCheckHistoryEditButton;

    private EditText giftCardNumberEv, giftcardeditEv;

    private TextView quantityTitleTextView;

    private TextView giftCardNumberTv, giftCardUsedTv, giftCardBalaceTv;
    private TextView giftCardUsedAmountTv, giftCardBalanceAmountTv;
    private TextView giftCardHistoryTitleDateTv, giftCardHistoryTitleAddedTv, giftCardHistoryTitleUsedTv;

    // 기프트카드 적립/사용 히스토리 리스트뷰
    private ListView giftCardHistoryListView;

    // 웹뷰 (WebView) 관련 ------------------------------------------
    private WebView giftcardHistoryWv;
    private final Handler handler = new Handler();
    ProgressDialog dialog;
    private String mCurrentUrl;
    // --------------------------------------------------------------

    private RadioGroup giftcardAddUseRadioGroup;
    private RadioButton giftcardAddRadioButton, giftcardUseRadioButton;

    String mGiftcardAddUseRadioBtnValue = "1";

    // ListView 에 고객항목 붙일 때 필요한 객체들
    TemporaryGiftCardHistoryList mTempGcHistoryList;
    public static ArrayList<TemporaryGiftCardHistoryList> mGeneralArrayList;
    public static GiftCardHistoryListAdapter mGiftCardHistoryListAdapter;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    MagStripDriver magStripDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_gift_card_balance_check);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 4;

        if (GlobalMemberValues.mDevicePAX) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 5;
        }

        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 10;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout) findViewById(R.id.giftCardBalanceCheckLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        activity = this;
        context = this;

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 선택포시션 값
            //parentSelectedPosition = mIntent.getStringExtra("ParentSaleCartPosition");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void setLayoutContent() {
        // MSR 활성화
        try {
            //magSwiper(this, context.getApplicationContext());
        } catch (Exception e) {
        }

        giftCardBalanceCheckKeypadLn = (LinearLayout) findViewById(R.id.giftCardBalanceCheckKeypadLn);
        giftCardBalanceCheckHistoryLn = (LinearLayout) findViewById(R.id.giftCardBalanceCheckHistoryLn);
        giftcardeditLn = (LinearLayout) findViewById(R.id.giftcardeditLn);

        giftCardBalanceCheck_suButton1 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton2 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton3 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton4 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton5 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton6 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton7 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton8 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton9 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton0 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButton00 = (Button) findViewById(R.id.giftCardBalanceCheck_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            giftCardBalanceCheck_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            giftCardBalanceCheck_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            giftCardBalanceCheck_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButtonBack = (Button) findViewById(R.id.giftCardBalanceCheck_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButtonBack.setText("");
            giftCardBalanceCheck_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            giftCardBalanceCheck_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButtonV = (Button) findViewById(R.id.giftCardBalanceCheck_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButtonV.setText("");
            giftCardBalanceCheck_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            giftCardBalanceCheck_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        giftCardBalanceCheck_suButtonX = (Button) findViewById(R.id.giftCardBalanceCheck_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            giftCardBalanceCheck_suButtonX.setText("");
            giftCardBalanceCheck_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            giftCardBalanceCheck_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    giftCardBalanceCheck_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        giftcardBalanceCheckHistoryBackButton = (Button) findViewById(R.id.giftcardBalanceCheckHistoryBackButton);
        giftcardBalanceCheckHistoryEditButton = (Button) findViewById(R.id.giftcardBalanceCheckHistoryEditButton);

        giftCardBalanceCheck_suButtonX2 = (Button) findViewById(R.id.giftCardBalanceCheck_suButtonX2);

        giftCardBalanceCheck_suButton1.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton2.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton3.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton4.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton5.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton6.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton7.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton8.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton9.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton0.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButton00.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButtonBack.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButtonV.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButtonX.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftCardBalanceCheck_suButtonX2.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftcardBalanceCheckHistoryBackButton.setOnClickListener(giftCardBalanceCheckButtonClickListener);
        giftcardBalanceCheckHistoryEditButton.setOnClickListener(giftCardBalanceCheckButtonClickListener);

        giftCardNumberEv = (EditText) findViewById(R.id.giftCardNumberEv);
        giftCardNumberEv.requestFocus();

        giftcardeditEv = (EditText) findViewById(R.id.giftcardeditEv);

        giftCardNumberEv.setOnTouchListener(mTouchgiftCardBalanceCheckTvTouchListener);
        giftCardNumberEv.setOnFocusChangeListener(mCardNumberFocusChangeListener);

        quantityTitleTextView = (TextView) findViewById(R.id.quantityTitleTextView);
        quantityTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + quantityTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardNumberTv = (TextView) findViewById(R.id.giftCardNumberTv);
        giftCardNumberTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardNumberTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardUsedTv = (TextView) findViewById(R.id.giftCardUsedTv);
        giftCardUsedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardUsedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardBalaceTv = (TextView) findViewById(R.id.giftCardBalaceTv);
        giftCardBalaceTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardBalaceTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardUsedAmountTv = (TextView) findViewById(R.id.giftCardUsedAmountTv);
        giftCardUsedAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardUsedAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardBalanceAmountTv = (TextView) findViewById(R.id.giftCardBalanceAmountTv);
        giftCardBalanceAmountTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardBalanceAmountTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardHistoryTitleDateTv = (TextView) findViewById(R.id.giftCardHistoryTitleDateTv);
        giftCardHistoryTitleDateTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardHistoryTitleDateTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardHistoryTitleAddedTv = (TextView) findViewById(R.id.giftCardHistoryTitleAddedTv);
        giftCardHistoryTitleAddedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardHistoryTitleAddedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardHistoryTitleUsedTv = (TextView) findViewById(R.id.giftCardHistoryTitleUsedTv);
        giftCardHistoryTitleUsedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardHistoryTitleUsedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // 히스토리 리스트 보여지는 ListView 객체 생성 - POS 용
        giftCardHistoryListView = (ListView) findViewById(R.id.giftCardHistoryListView);

        // 히스토리 리스트 보여지는 ListView 객체 생성 - WEB 용 -------------------------------------------------------------------------
        giftcardHistoryWv = (WebView)findViewById(R.id.giftcardHistoryWv);

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            giftCardHistoryListView.setVisibility(View.GONE);
            giftcardHistoryWv.setVisibility(View.VISIBLE);
        }

        //스크롤바 없애기
        giftcardHistoryWv.setVerticalScrollBarEnabled(false);
        giftcardHistoryWv.setHorizontalScrollBarEnabled(false);

        giftcardHistoryWv.setWebViewClient(new WebViewClient());
        WebSettings set = giftcardHistoryWv.getSettings();

        // 화면 꽉 차게
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        set.setLoadWithOverviewMode(true);
        set.setUseWideViewPort(true);
        giftcardHistoryWv.setInitialScale(1);
        //자바스크립트 가능하게
        set.setJavaScriptEnabled(true);
        //웹뷰 멀티터치 가능하게
        set.setBuiltInZoomControls(true);
        set.setSupportZoom(true);
        //Local Storage, sessionStorage 유효화
        set.setDomStorageEnabled(true);
        //window.open 메서드 이용할 때의 동작 설정
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        //아래것 사용하면 팝업이 사용안됨
        //set.setSupportMultipleWindows(true);

        //API Level 16 부터 이용 가능
        try {
            set.setAllowUniversalAccessFromFileURLs(true);
        } catch(Exception e) {
            //아무것도 하지 않는다.
        }

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메소두를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.thisWebViewName.setMessage("메시지 내용");
        giftcardHistoryWv.addJavascriptInterface(new AndroidBridge(), "giftcardbalancecheck");
        giftcardHistoryWv.setWebChromeClient(wcc);
        giftcardHistoryWv.setWebViewClient(new WebViewClientClass());

        mCurrentUrl = null;
        // -------------------------------------------------------------------------------------------------------------------------------

        giftcardAddUseRadioGroup = (RadioGroup) findViewById(R.id.giftcardAddUseRadioGroup);
        giftcardAddUseRadioGroup.setOnCheckedChangeListener(checkAddUse);

        giftcardAddRadioButton = (RadioButton) findViewById(R.id.giftcardAddRadioButton);
        giftcardAddRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + giftcardAddRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftcardUseRadioButton = (RadioButton) findViewById(R.id.giftcardUseRadioButton);
        giftcardUseRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + giftcardUseRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), giftCardNumberEv);
        GlobalMemberValues.setKeyPadHide(getApplication(), giftcardeditEv);
    }

    RadioGroup.OnCheckedChangeListener checkAddUse = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.giftcardAddUseRadioGroup: {
                    switch (checkedId) {
                        // Add
                        case R.id.giftcardAddRadioButton: {
                            mGiftcardAddUseRadioBtnValue = "1";
                            break;
                        }
                        // Use
                        case R.id.giftcardUseRadioButton: {
                            mGiftcardAddUseRadioBtnValue = "2";
                            break;
                        }
                    }
                    break;
                }
            }
        }
    };

    View.OnFocusChangeListener mCardNumberFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //GlobalMemberValues.displayDialog(context, "","포커스 이동...", "Close");
                String getMSRData = giftCardNumberEv.getText().toString();
                if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);
                    //GlobalMemberValues.displayDialog(context, "","reading value : " + getMSRData, "Close");
                    giftCardNumberEv.setText(getMSRData);
                    setGiftCardBalanceUsedTextView("");
                }
            }
        }
    };

    View.OnClickListener giftCardBalanceCheckButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.giftCardBalanceCheck_suButton1: {
                    numberButtonClick(giftCardBalanceCheck_suButton1);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton2: {
                    numberButtonClick(giftCardBalanceCheck_suButton2);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton3: {
                    numberButtonClick(giftCardBalanceCheck_suButton3);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton4: {
                    numberButtonClick(giftCardBalanceCheck_suButton4);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton5: {
                    numberButtonClick(giftCardBalanceCheck_suButton5);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton6: {
                    numberButtonClick(giftCardBalanceCheck_suButton6);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton7: {
                    numberButtonClick(giftCardBalanceCheck_suButton7);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton8: {
                    numberButtonClick(giftCardBalanceCheck_suButton8);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton9: {
                    numberButtonClick(giftCardBalanceCheck_suButton9);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton0: {
                    numberButtonClick(giftCardBalanceCheck_suButton0);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButton00: {
                    numberButtonClick(giftCardBalanceCheck_suButton00);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButtonBack: {
                    sb.delete(0, sb.toString().length());
                    sb.append(mQtyEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                        sb.delete((sb.toString().length() - 1), sb.toString().length());
                        mQtyEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                            mQtyEtValue = "";
                        }
                    } else {
                        mQtyEtValue = "";
                    }
                    giftCardNumberEv.setText(mQtyEtValue);

                    // 커서를 맨 뒤로...
                    GlobalMemberValues.setCursorLastDigit(giftCardNumberEv);
                    break;
                }
                case R.id.giftCardBalanceCheck_suButtonV: {
                    setGiftCardBalanceUsedTextView("");
                    break;
                }
                case R.id.giftCardBalanceCheck_suButtonX: {
                    init();
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.giftCardBalanceCheck_suButtonX2: {
                    init();
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.giftcardBalanceCheckHistoryBackButton: {
                    showKeypad();
                    break;
                }
                case R.id.giftcardBalanceCheckHistoryEditButton: {
                    editGiftCard();
                    break;
                }
            }
        }
    };

    private void editGiftCard() {
        String tempGiftCardNum = giftCardNumberEv.getText().toString();
        String tempEditValue = giftcardeditEv.getText().toString();
        if (!GlobalMemberValues.isStrEmpty(tempGiftCardNum) && !GlobalMemberValues.isStrEmpty(tempEditValue)) {
            String tempFlag = "+";
            String tempFlag2 = "A";
            if (mGiftcardAddUseRadioBtnValue.equals("2") || mGiftcardAddUseRadioBtnValue == "2") {
                tempFlag = "-";
                tempFlag2 = "U";
            }

            Vector<String> strQueryVec = new Vector<String>();
            String strSqlQuery = "";

            // 먼저 salon_storegiftcard_number 에 저장된 기프트카드인지 확인한다.
            int giftcardCnt = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select count(idx) from salon_storegiftcard_number where gcNumber = '" + tempGiftCardNum + "' "
                    )
            );

            if (giftcardCnt == 0) {                 // 신규 등록
                strSqlQuery = "insert into salon_storegiftcard_number (" +
                        " sidx, gcNumber, customerId, customerName, remainingPrice " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(tempGiftCardNum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                        " '" + tempFlag + tempEditValue + "' " +
                        ")";
                strQueryVec.addElement(strSqlQuery);
            } else {                                // 수정 등록
                // salon_storegiftcard_number 테이블 저장
                strSqlQuery = "update salon_storegiftcard_number set " +
                        " remainingPrice = remainingPrice " + tempFlag + GlobalMemberValues.getDoubleAtString(tempEditValue) + " " +
                        " where gcNumber = '" + tempGiftCardNum + "' ";
                strQueryVec.addElement(strSqlQuery);
            }

            String sales_employeeIdx = "";
            String sales_employeeName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                sales_employeeIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                sales_employeeName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }

            // salon_storegiftcard_number_history 테이블 저장
            strSqlQuery = "insert into salon_storegiftcard_number_history (" +
                    " sidx, gcNumber, saleItemIdx, empIdx, empName, customerId, customerName, addUsePrice, addUseType, salesCode, codeforupload " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempGiftCardNum, 0) + "', " +
                    " '" + "" + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_employeeIdx, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_employeeName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempEditValue, 0) + "', " +
                    " '" + tempFlag2 + "', " +               // 기프트카드 사용이므로 적립인 'U' 로 저장 (적립시 'A')
                    " '" + "" + "', " +                      // 세일즈코드
                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                    ")";
            strQueryVec.addElement(strSqlQuery);

            for (String tempQuery : strQueryVec) {
                GlobalMemberValues.logWrite("giftcardeditlog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
            if (returnResult == "N" || returnResult == "") {
            } else {
                String tempMaxIdx = MainActivity.mDbInit.dbExecuteReadReturnString("select idx from salon_storegiftcard_number_history order by idx desc limit 1");
                String tempCodeForUpload = MainActivity.mDbInit.dbExecuteReadReturnString("select codeforupload from salon_storegiftcard_number_history order by idx desc limit 1");
                String tempParamValue = tempGiftCardNum + "-JJJ-" + sales_employeeIdx + "-JJJ-" + sales_employeeName +
                        "-JJJ-" + tempEditValue + "-JJJ-" + tempFlag2 + "-JJJ-" + tempCodeForUpload + "-JJJ-" + tempMaxIdx;
                setGiftCardBalanceUsedTextView(tempParamValue);

                // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
                //GlobalMemberValues.serviceStartSendDataToCloud(context, activity);
            }
        }
    }

    private void showKeypad() {
        init();
        giftCardBalanceCheckHistoryLn.setVisibility(View.GONE);

        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        giftCardBalanceCheckKeypadLn.setVisibility(View.VISIBLE);
        giftCardBalanceCheckKeypadLn.setAnimation(animation1);

        showHideEditLn(false);
    }

    private void showHideEditLn(boolean paramValue) {
        if (paramValue) {
            // 권한체크
            if (GlobalMemberValues.checkEmployeePermission(
                    TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<9>")) {
                giftcardeditLn.setVisibility(View.VISIBLE);
            }
        } else {
            giftcardeditLn.setVisibility(View.GONE);
        }
    }

    private void setGiftCardBalanceUsedTextView(String paramValue) {
        String evGiftCardNumber = giftCardNumberEv.getText().toString();
        GlobalMemberValues.logWrite("giftcardNumber", "giftcard number : " + evGiftCardNumber + "\n");
        if (!GlobalMemberValues.isStrEmpty(evGiftCardNumber)) {
            if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                String getGcNumber = giftCardNumberEv.getText().toString();

                String historyUrl = GlobalMemberValues.CLOUD_SERVER_URL + "NZCGIFT/";

                if (!GlobalMemberValues.isStrEmpty(paramValue)) {
                    historyUrl += "webview_giftcard_edit.asp?";

                    String[] arrValue = paramValue.split("-JJJ-");

                    String tempEmpIdx = "";
                    if (arrValue.length > 1) {
                        tempEmpIdx = arrValue[1];
                    }
                    String tempEmpName = "";
                    if (arrValue.length > 2) {
                        tempEmpName = arrValue[2];
                    }
                    String tempAddUsePrice = "";
                    if (arrValue.length > 3) {
                        tempAddUsePrice = arrValue[3];
                    }
                    String tempAddUseType = "";
                    if (arrValue.length > 4) {
                        tempAddUseType = arrValue[4];
                    }
                    String tempCodeForUpload = "";
                    if (arrValue.length > 5) {
                        tempCodeForUpload = arrValue[5];
                    }
                    String tempMaxIdx = "";
                    if (arrValue.length > 6) {
                        tempMaxIdx = arrValue[6];
                    }

                    historyUrl += "scode=" + GlobalMemberValues.SALON_CODE +
                            "&sidx=" + GlobalMemberValues.STORE_INDEX +
                            "&stcode=" + GlobalMemberValues.STATION_CODE +
                            "&gcnumber=" + getGcNumber +
                            "&idx=" + tempMaxIdx +
                            "&saleitemidx=" + "" +
                            "&empidx=" + tempEmpIdx +
                            "&empname=" + tempEmpName +
                            "&customerid=" + "" +
                            "&customername=" + "" +
                            "&adduseprice=" + tempAddUsePrice +
                            "&addusetype=" + tempAddUseType +
                            "&salescode=" + "" +
                            "&addUseMemo=modified by admin" +
                            "&codefrompos=" + tempCodeForUpload;
                } else {
                    // 01022024
                    // gift card type 에 필요한 작업
                    // sidx 추가
                    historyUrl += "webview_giftcard_history.asp?";
                    historyUrl += "scode=" + GlobalMemberValues.SALON_CODE +
                            "&sidx=" + GlobalMemberValues.STORE_INDEX +
                            "&gcnumber=" + getGcNumber;
                }
                historyUrl = GlobalMemberValues.getReplaceText(historyUrl, " ", "|||");

                giftcardHistoryWv.loadUrl(historyUrl);
                GlobalMemberValues.logWrite("historywebaddresslog", "cloud url : " + historyUrl + "\n");
            } else {
                // 현재까지 사용한 금액
                String giftCardUsedAmount = GlobalMemberValues.checkGiftCardUsedAmount(context, evGiftCardNumber);
                GlobalMemberValues.logWrite("giftcardNumber", "사용액 : " + giftCardUsedAmount + "\n");
                giftCardUsedAmountTv.setText(GlobalMemberValues.getCommaStringForDouble(giftCardUsedAmount));

                // 현재 잔액
                String giftCardBalance = GlobalMemberValues.checkGiftCardBalance(context, evGiftCardNumber);
                GlobalMemberValues.logWrite("giftcardNumber", "잔액 : " + giftCardBalance + "\n");
                giftCardBalanceAmountTv.setText(GlobalMemberValues.getCommaStringForDouble(giftCardBalance));
            }

            // 기프트카드 사용/적립 히스토리 보여주기
            giftCardBalanceCheckKeypadLn.setVisibility(View.GONE);

            Animation animation1;
            // 애니메이션 옵션 저장
            animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
            //Animation animation = new AlphaAnimation(0, 1);
            animation1.setDuration(200);
            giftCardBalanceCheckHistoryLn.setVisibility(View.VISIBLE);
            giftCardBalanceCheckHistoryLn.setAnimation(animation1);

            setGiftCardHistoryListView(evGiftCardNumber);

            showHideEditLn(true);
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter Giftcard Number", "Close");
            giftCardBalanceAmountTv.setText("0.00");
            giftCardBalanceAmountTv.setText("0.00");
        }
    }

    private void numberButtonClick(Button btn) {
        if (mQtyEtValue.length() < 17) {
            sb.delete(0, sb.toString().length());
            sb.append(mQtyEtValue).append(btn.getText().toString());
            mQtyEtValue = sb.toString();
            giftCardNumberEv.setText(mQtyEtValue);
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(giftCardNumberEv);
        }
    }

    View.OnTouchListener mTouchgiftCardBalanceCheckTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            GlobalMemberValues.setKeyPadHide(context, giftCardNumberEv);
            giftCardNumberEv.requestFocus();
            giftCardNumberEv.setSelection(giftCardNumberEv.length());
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(giftCardNumberEv);
            showKeypad();
            return true;
        }
    };

    /**
     * 기프트카드 적립/사용 리스트 배치하기
     ********************************************************/
    public void setGiftCardHistoryListView(String paramGifcCardNumber) {
        DatabaseInit dbInit = new DatabaseInit(context);
        String strQuery = "select idx, wdate, addUsePrice, addUseType from salon_storegiftcard_number_history " +
                " where gcNumber = '" + paramGifcCardNumber + "' " +
                " order by wdate desc ";
        Cursor giftCardHistoryCursor = dbInit.dbExecuteRead(strQuery);
        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryGiftCardHistoryList>();
        while (giftCardHistoryCursor.moveToNext()) {
            String tempAddUsePrice = giftCardHistoryCursor.getString(2);
            if (!GlobalMemberValues.isStrEmpty(tempAddUsePrice)) {
                String paramsTempGiftCardHistoryListArray[] = {
                        GlobalMemberValues.getDBTextAfterChecked(giftCardHistoryCursor.getString(0), 1),
                        GlobalMemberValues.getDBTextAfterChecked(giftCardHistoryCursor.getString(1), 1),
                        GlobalMemberValues.getDBTextAfterChecked(giftCardHistoryCursor.getString(2), 1),
                        GlobalMemberValues.getDBTextAfterChecked(giftCardHistoryCursor.getString(3), 1),
                };
                mTempGcHistoryList = new TemporaryGiftCardHistoryList(paramsTempGiftCardHistoryListArray);
                mGeneralArrayList.add(mTempGcHistoryList);
            }
        }
        mGiftCardHistoryListAdapter = new GiftCardHistoryListAdapter(context, R.layout.giftcard_history_list, mGeneralArrayList);
        giftCardHistoryListView.setAdapter(mGiftCardHistoryListAdapter);

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        //giftCardHistoryListView.setOnItemClickListener(mCustomerInfoItemClickListener);
    }

    /*******************************************************************************/

    public void magSwiper(final Activity paramActivity, final Context paramContext) {
        paramActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                magStripDriver = new MagStripDriver(paramContext);
                magStripDriver.startDevice(); //Start the MagStripe Reader
                magStripDriver.registerMagStripeListener(new MagStripDriver.MagStripeListener() { //MageStripe Reader's Listener for notifying various events.

                    @Override
                    public void OnDeviceDisconnected() { //Fired when the Device has been Disconnected.
                        //  Toast.makeText(getActivity(), "Magnetic-Stripe Device Disconnected !", Toast.LENGTH_SHORT).show();
                        GlobalMemberValues.displayDialog(paramContext, "Info", "Magnetic-Stripe Device Disconnected !", "Close");
                    }

                    @Override
                    public void OnDeviceConnected() { //Fired when the Device has been Connected.
                        // TODO Auto-generated method stub
                        // Toast.makeText(getActivity(), "Magnetic-Stripe Device Connected !", Toast.LENGTH_SHORT).show();
                        GlobalMemberValues.displayDialog(paramContext, "Info", "Magnetic-Stripe Device Connected !", "Close");
                    }

                    @Override
                    public void OnCardSwiped(String cardData) { //Fired when a card has been swiped on the device.
                        try {
                            MagStripeCardParser mParser = new MagStripeCardParser(cardData); //Instance of card swipe reader
                            GlobalMemberValues.displayDialog(paramContext, "Info", "cardData : " + cardData, "Close");

                            if (mParser.isDataParse()) {
                                if (mParser.hasTrack1()) {
                                    String getMSRData = mParser.getAccountNumber();

                                    GlobalMemberValues.displayDialog(paramContext, "Info", "getMSRData : " + getMSRData, "Close");

                                    getMSRData = GlobalMemberValues.getMSRCardNumber("giftcard", getMSRData);

                                    if (!GlobalMemberValues.isStrEmpty(getMSRData)) {
                                        giftCardNumberEv.setText("11111");
                                        setGiftCardBalanceUsedTextView("");
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

    private void setGiftCardBalance(String paramValue) {
        if (GlobalMemberValues.IS_COM_CHAINSTORE && !GlobalMemberValues.isStrEmpty(paramValue)) {
            GlobalMemberValues.logWrite("historywebaddresslog", "paramValue : " + paramValue + "\n");

            String tempAddedBalance = "0.0";
            String tempUsedBalance = "0.0";
            String tempTotalBalance = "0.0";
            String[] arrrGiftCardBalanceValue = paramValue.split(GlobalMemberValues.STRSPLITTER2);
            if (arrrGiftCardBalanceValue.length > 0) {
                tempAddedBalance = arrrGiftCardBalanceValue[0];
            }
            if (arrrGiftCardBalanceValue.length > 1) {
                tempUsedBalance = arrrGiftCardBalanceValue[1];
            }
            if (arrrGiftCardBalanceValue.length > 2) {
                tempTotalBalance = arrrGiftCardBalanceValue[2];
            }

            giftCardUsedAmountTv.setText(GlobalMemberValues.getCommaStringForDouble(tempUsedBalance));
            giftCardBalanceAmountTv.setText(GlobalMemberValues.getCommaStringForDouble(tempTotalBalance));
        }
    }

    private void init() {
        giftCardNumberEv.setText("");
        giftcardeditEv.setText("");
        giftCardUsedAmountTv.setText("0.00");
        giftCardBalanceAmountTv.setText(("0.00"));
        mQtyEtValue = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("registrationComplete"));

        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(context, activity);
    }










    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
            }
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);

            GlobalMemberValues.logWrite("webcloudurl", "url : " + url + "\n");

            dialog = new JJJCustomAnimationDialog(activity);
            dialog.show();
        }

        // 페이지 로딩시
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, "Web Error : " + description, Toast.LENGTH_SHORT).show();
            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }

            String urlString = giftcardHistoryWv.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {

            } else {

            }
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
    }


    WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog
                    .Builder(context)
                    .setTitle("AlertDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,String message, final JsResult result) {
            new AlertDialog.Builder(context)
                    .setTitle("ConfirmDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();

            return true;
        }

    };

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(GiftCardBalanceCheck.this, msg, Toast.LENGTH_SHORT).show();
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        setGiftCardBalance(msg);
                        GlobalMemberValues.logWrite("historywebaddresslog", "msg : " + msg + "\n");
                    }
                }
            });
        }
    }
}