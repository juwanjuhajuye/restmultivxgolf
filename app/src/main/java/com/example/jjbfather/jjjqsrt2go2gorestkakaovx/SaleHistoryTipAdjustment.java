package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.byullbam.restapi.RestApiConfig;
import com.byullbam.restapi.RestApiTipAdd;
import com.pax.poslink.PaymentRequest;
import com.pax.poslink.PaymentResponse;

import net.sourceforge.jtds.jdbc.MSSqlServerInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SaleHistoryTipAdjustment extends Activity {
    static Activity mActivity;
    static Context context;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private Button closeBtn;

    private Button saleHistoryTipAdjustmentSuButton1,saleHistoryTipAdjustmentSuButton2,saleHistoryTipAdjustmentSuButton3;
    private Button saleHistoryTipAdjustmentSuButton4,saleHistoryTipAdjustmentSuButton5,saleHistoryTipAdjustmentSuButton6;
    private Button saleHistoryTipAdjustmentSuButton7,saleHistoryTipAdjustmentSuButton8,saleHistoryTipAdjustmentSuButton9;
    private Button saleHistoryTipAdjustmentSuButton0,saleHistoryTipAdjustmentSuButton00,saleHistoryTipAdjustmentSuButtonBack;
    private Button saleHistoryTipAdjustmentSuButtonV;

    private static TextView saleHistoryTipAdjustmentSalesCodeTextView;
    private static TextView saleHistoryTipAdjustmentEmployeeTextView;

    public static EditText saleHistoryTipAdjustmentTipAmountEditText;
    public static TextView saleHistoryTipAdjustmentTotalTipAmountEditText;
    public static TextView saleHistoryTipAdjustmentOrderNoTextView;
    public static TextView saleHistoryTipTotalTotalTipAmountEditText;

    private RadioGroup saleHistoryTipAdjustmentPayTypeRadioGroup;
    public static RadioButton saleHistoryTipAdjustmentCashRadioButton, saleHistoryTipAdjustmentCardRadioButton;

    private TextView saleHistoryTipAdjustmentTitleTextView;
    private TextView saleHistoryTipAdjustmentSubTitleTextView1;
    private TextView saleHistoryTipAdjustmentSubTitleTextView2;
    private TextView saleHistoryTipAdjustmentSubTitleTextView3;
    private TextView saleHistoryTipAdjustmentSubTitleTextView4;
    private TextView saleHistoryTipAdjustmentSubTitleTextView5;
    private TextView saleHistoryTipAdjustmentSubTitleTextView6;

    public static TextView saleHistoryTipAdjustmentCardImageTextView, saleHistoryTipAdjustmentCardNumberTextView;

    private LinearLayout parentLn;

    Intent mIntent;

    static String mSalesCode = "";
    static String mSalonSalesDetailIdx = "";
    static String mEmpIdx = "";
    static String mEmpName = "";
    static String mPayType = "";

    // 주문건의 지불타입 객체
    String mIsCashPayOfSales = "N";
    String mIsCardPayOfSales = "N";

    // 임시저장값
    public static String mTempPriceValue = "";

    // salon_sales_tip 테이블에 insert 할지 update 할지 구분짓는 객체 선언
    static String dbTableExecuteType = "INS";          // INS : insert        UPD : update
    static String salonSalesTipidx = "";               // salon_sales_tip 테이블에 해당직원의 데이터가 있을 경우 해당 salon_sales_tip 테이블의 idx 값
    static String salonSalesTipidx_insqlite = "";      // sqlite 에 저장된 salon_sales_tip 의 idx 값

    // 팁 사용시 카드 사용여부
    String mTipuse = "";

    // 팁 지불할 카드관련 정보 ----------------------------------------------
//    public static String mSelectedSalonSalesCardIdx = "";
    public static String mSelectedSalonSalesCard_Split_transaction_id = "";
    public static String mSelectedSalonSalesCardCardCom = "";
    public static String mSelectedSalonSalesCardTipAmount = "";
    public static String mSelectedSalonSalesCardCardLastFourDigitNumbers = "";
    public static String mSelectedSalonSalesCardCardRefNumber = "";
    public static String mSelectedSalonSalesCardCardEmvAid = "";
    public static String mSelectedSalonSalesCardCardEmvTsi = "";
    public static String mSelectedSalonSalesCardCardEmvTvr = "";

    public static String mSelectedSalonSalesCardCardOffCardYN = "N";

    // 12212022
    public String mSelectedSalonSalesCardPriceAmount = "";
    // -------------------------------------------------------------------

    // Cash 팁 사용금액
    String mUsedTipByCash = "";

    // 카드결제 연동 관련 변수 -------------------------------
    public static ProgressDialog proDial;   // 프로그래스바
    static String creditCardReturnValue = "";           // 결제처리후 리턴값
    double modifyPayAmount = 0.0;           // 추가/감산 처리할 금액
    // ----------------------------------------------------

    // 터미널 연결정보 --------------------------------------
    public static String paymentGateway = "";
    public static String networkIp = "";
    public static String networkPort = "";
    // ----------------------------------------------------
    public boolean is_history_list = false;

    public static Integer i_salehistory_row_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_tip_adjustment);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 3;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.saleHistoryTipAdjustmentLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mSalesCode = mIntent.getStringExtra("salesCode");
            mSalonSalesDetailIdx = mIntent.getStringExtra("salonSalesDetailIdx");
            is_history_list = mIntent.getBooleanExtra("is_history_list", false);
            i_salehistory_row_num = mIntent.getIntExtra("list_row_number",0);
            GlobalMemberValues.logWrite("saleHistoryTipAdjustmentGetExtra", "넘겨받은 SalesCode : " + mSalesCode + "\n");
            GlobalMemberValues.logWrite("saleHistoryTipAdjustmentGetExtra", "넘겨받은 salonSalesDetailIdx : " + mSalonSalesDetailIdx + "\n");
            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        mSelectedSalonSalesCardPriceAmount = "";

        mActivity = this;
        context = this;

        ConfigPGInfo pgInfo = new ConfigPGInfo();
        paymentGateway = pgInfo.cfpaymentgateway;
        networkIp = pgInfo.cfnetworkip;
        networkPort = pgInfo.cfnetworkport;

        mSelectedSalonSalesCard_Split_transaction_id = "";
        mSelectedSalonSalesCardCardCom = "";
        mSelectedSalonSalesCardTipAmount = "";
        mSelectedSalonSalesCardCardLastFourDigitNumbers = "";
        mSelectedSalonSalesCardCardRefNumber = "";
        mSelectedSalonSalesCardCardEmvAid = "";
        mSelectedSalonSalesCardCardEmvTsi = "";
        mSelectedSalonSalesCardCardEmvTvr = "";

        dbInit = new DatabaseInit(this);

        mTipuse = dbInit.dbExecuteReadReturnString(
                " select tipuse from salon_storestationsettings_paymentgateway "
        );

        saleHistoryTipAdjustmentTitleTextView = (TextView)findViewById(R.id.saleHistoryTipAdjustmentTitleTextView);
        saleHistoryTipAdjustmentTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView1 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView1);
        saleHistoryTipAdjustmentSubTitleTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView2 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView2);
        saleHistoryTipAdjustmentSubTitleTextView2.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView3 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView3);
        saleHistoryTipAdjustmentSubTitleTextView3.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView4 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView4);
        saleHistoryTipAdjustmentSubTitleTextView4.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView5 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView5);
        saleHistoryTipAdjustmentSubTitleTextView5.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentSubTitleTextView6 = (TextView)findViewById(R.id.saleHistoryTipAdjustmentSubTitleTextView6);
        saleHistoryTipAdjustmentSubTitleTextView6.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSubTitleTextView6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentCardImageTextView = (TextView)findViewById(R.id.saleHistoryTipAdjustmentCardImageTextView);

        saleHistoryTipAdjustmentCardNumberTextView = (TextView)findViewById(R.id.saleHistoryTipAdjustmentCardNumberTextView);
        saleHistoryTipAdjustmentCardNumberTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentCardNumberTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentCloseBtnTag");
        closeBtn.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        saleHistoryTipAdjustmentSuButton1 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton1.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton2 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton2.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton3 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton3.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton4 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton4.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton5 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton5.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton6 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton6.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton7 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton7.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton8 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton8.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton9 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton9.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton0 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton0.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButton00 = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            saleHistoryTipAdjustmentSuButton00.setTextColor(Color.parseColor("#0054d5"));
            saleHistoryTipAdjustmentSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_number);
        } else {
            saleHistoryTipAdjustmentSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButtonBack = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButtonBack.setText("");
            saleHistoryTipAdjustmentSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_delete);
        } else {
            saleHistoryTipAdjustmentSuButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryTipAdjustmentSuButtonV = (Button)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryTipAdjustmentSuButtonV.setText("");
            saleHistoryTipAdjustmentSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_enter);
        } else {
            saleHistoryTipAdjustmentSuButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryTipAdjustmentSuButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryTipAdjustmentSuButton1.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton2.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton3.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton4.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton5.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton6.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton7.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton8.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton9.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton0.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButton00.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButtonBack.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        saleHistoryTipAdjustmentSuButtonV.setOnClickListener(saleHistoryTipAdjustmentBtnClickListener);
        /***********************************************************************************************************/

        /** sales #, employee, tip Amount 객체 생성 ****************************************************************/
        saleHistoryTipAdjustmentSalesCodeTextView = (TextView)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentSalesCodeTextViewTag");
        saleHistoryTipAdjustmentSalesCodeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentSalesCodeTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentEmployeeTextView = (TextView)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentEmployeeTextViewTag");
        saleHistoryTipAdjustmentEmployeeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentEmployeeTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentTipAmountEditText = (EditText)parentLn
                .findViewWithTag("saleHistoryTipAdjustmentTipAmountEditTextTag");
        saleHistoryTipAdjustmentTipAmountEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentTipAmountEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentTotalTipAmountEditText = (TextView) parentLn
                .findViewWithTag("saleHistoryTipAdjustmentTotalTipAmountEditTextTag");
        saleHistoryTipAdjustmentTotalTipAmountEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentTotalTipAmountEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentOrderNoTextView = (TextView) parentLn
                .findViewWithTag("saleHistoryTipAdjustmentOrderNoTextViewTag");
        saleHistoryTipAdjustmentOrderNoTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentOrderNoTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipTotalTotalTipAmountEditText = (TextView) parentLn
                .findViewWithTag("saleHistoryTipTotalTotalTipAmountEditTextTag");
        saleHistoryTipTotalTotalTipAmountEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipTotalTotalTipAmountEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentTipAmountEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 키패드(키보드) 감추기
                GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);
            }
        });
        /***********************************************************************************************************/

        /** RadioGroup 객체 생성 및 리스너 연결 ********************************************************************/
        // 먼저 해당주문건의 지불타입을 가져온다.
        String salonSalesQuery = "select " +
                " isTotalCashUse, isTotalCardUse, useTotalCashAmount, useTotalCardAmount " +
                " from salon_sales where salesCode = '" + mSalesCode + "' ";
        GlobalMemberValues.logWrite("saleHistoryTipAdjustmentPay", "Query : " + salonSalesQuery + "\n");
        //Cursor salonSalesCursor = dbInit.dbExecuteRead(salonSalesQuery);
        ResultSet salonSalesCursor = MssqlDatabase.getResultSetValue(salonSalesQuery);
        try {
            if (salonSalesCursor.next()) {
                if (salonSalesCursor.getInt(1) == 0 && salonSalesCursor.getDouble(3) > 0) {
                    mIsCashPayOfSales = "Y";
                }
                if (salonSalesCursor.getInt(2) == 0 && salonSalesCursor.getDouble(4) > 0) {
                    mIsCardPayOfSales = "Y";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //07052024 close resultset
        try {
            if(!salonSalesCursor.isClosed()){
                salonSalesCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // RadioGroup 객체생성 및 리스너 연결
        saleHistoryTipAdjustmentPayTypeRadioGroup = (RadioGroup)findViewById(R.id.saleHistoryTipAdjustmentPayTypeRadioGroup);

        // RadioButton 객체생성
        saleHistoryTipAdjustmentCashRadioButton = (RadioButton)findViewById(R.id.saleHistoryTipAdjustmentCashRadioButton);
        saleHistoryTipAdjustmentCashRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentCashRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryTipAdjustmentCardRadioButton = (RadioButton)findViewById(R.id.saleHistoryTipAdjustmentCardRadioButton);
        saleHistoryTipAdjustmentCardRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryTipAdjustmentCardRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        GlobalMemberValues.logWrite("saleHistoryTipAdjustmentPay", "CASH : " + mIsCashPayOfSales + "\n");
        GlobalMemberValues.logWrite("saleHistoryTipAdjustmentPay", "CARD : " + mIsCardPayOfSales + "\n");

        // 주문건이 카드결제한 금액이 없을 경우 카드선택 RadioButton 을 비활성화 한다.
        if (mIsCashPayOfSales == "Y" && mIsCardPayOfSales == "N") {
            //saleHistoryTipAdjustmentCardRadioButton.setEnabled(false);
        }

        // 05.04.2022 ----------------------------------------------------------------------------------------------------------
        if (!GlobalMemberValues.isStrEmpty(mSalesCode)) {
            // tip total 구하기
            GlobalMemberValues.logWrite("jjjlogtipjjjlog", " select (usedCash + usedCard) from salon_sales_tip where salesCode = '" + mSalesCode + "' " + "\n");
            double totalTipAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                    " select (usedCash + usedCard) from salon_sales_tip where salesCode = '" + mSalesCode + "' "
            ));
            saleHistoryTipAdjustmentTotalTipAmountEditText.setText(GlobalMemberValues.getStringFormatNumber(totalTipAmount, "2"));

            String customerOrderNum = MssqlDatabase.getResultSetValueToString(
                    " select customerordernumber from salon_sales where salesCode = '" + mSalesCode + "' "
            );
            saleHistoryTipAdjustmentOrderNoTextView.setText(customerOrderNum);

            // Total Amount 구하기
            double totalamount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                    " select totalAmount from salon_sales where salescode = '" + mSalesCode + "' ")
            );
            double totalamountWithTip = totalamount + totalTipAmount;

            // 12212022 ============================================================================
            String paidPriceAmount = mSelectedSalonSalesCardPriceAmount;
            if (GlobalMemberValues.isStrEmpty(paidPriceAmount)) {
                paidPriceAmount = "0";
            }
            String paidTipAmount = saleHistoryTipAdjustmentTipAmountEditText.getText().toString();
            if (GlobalMemberValues.isStrEmpty(paidTipAmount)) {
                paidTipAmount = "0";
            }
            double paidPriceTipAmount = GlobalMemberValues.getDoubleAtString(paidPriceAmount) + GlobalMemberValues.getDoubleAtString(paidTipAmount);
            if (paidPriceTipAmount > 0) {
                totalamountWithTip = paidPriceTipAmount;
            }
            // =====================================================================================

            saleHistoryTipTotalTotalTipAmountEditText.setText(GlobalMemberValues.getStringFormatNumber((totalamountWithTip + ""), "2"));
        }
        //----------------------------------------------------------------------------------------------------------------------

        // 최초에는 CASH 를 선택하게 한다.
        //saleHistoryTipAdjustmentPayTypeRadioGroup.check(saleHistoryTipAdjustmentCashRadioButton.getId());
        //mPayType = "CASH";ㄴ
        // Card 선택
        // 라디오 버튼 숨김으로 변경 (카드선택 고정) 071724
        saleHistoryTipAdjustmentPayTypeRadioGroup.check(saleHistoryTipAdjustmentCardRadioButton.getId());
        mPayType = "CARD";
        openTipCardListActivity();
        /***********************************************************************************************************/

        /** 부모로부터 전달받은 값 및 초기값 할당 ****************************************************************/
        saleHistoryTipAdjustmentSalesCodeTextView.setText(mSalesCode);
        String salonSalesDetailQuery = "select " +
                " employeeIdx, employeeName " +
                " from salon_sales_detail where idx = '" + mSalonSalesDetailIdx + "' ";
        GlobalMemberValues.logWrite("salehistoryidxlog", "idx : " + mSalonSalesDetailIdx + "\n");
        //Cursor salonSalesDetailCursor = dbInit.dbExecuteRead(salonSalesDetailQuery);
        ResultSet salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
        try {
            if (salonSalesDetailCursor.next()) {
                mEmpIdx = GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,0);
                mEmpName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 1);
                saleHistoryTipAdjustmentEmployeeTextView.setText(mEmpName);

                // 해당 주문건의 해당 직원에게 지급된 팁이 있는지 확인한다. --------------------------------------------
                double tempUsedCashTip = 0.0;
                double tempUsedCardTip = 0.0;

                String salonSalesTipQuery = "select " +
                        " idx, usedCash, usedCard " +
                        " from salon_sales_tip where salesCode = '" + mSalesCode + "' and employeeIdx = '" + mEmpIdx + "' ";
                //Cursor salonSalesTipCursor = dbInit.dbExecuteRead(salonSalesTipQuery);
                ResultSet salonSalesTipCursor = MssqlDatabase.getResultSetValue(salonSalesTipQuery);
                try {
                    if (salonSalesTipCursor.next()) {

                        tempUsedCashTip = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,1));
                        tempUsedCardTip = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,2));

                        GlobalMemberValues.logWrite("tipcashcardvaluelogjjj", "cash tip : " + tempUsedCashTip + "\n");
                        GlobalMemberValues.logWrite("tipcashcardvaluelogjjj", "card tip : " + tempUsedCardTip + "\n");

                        if (tempUsedCashTip > 0) {
                            saleHistoryTipAdjustmentPayTypeRadioGroup.check(saleHistoryTipAdjustmentCashRadioButton.getId());
                            mPayType = "CASH";
                            mTempPriceValue = GlobalMemberValues.getReplaceText(GlobalMemberValues.getStringFormatNumber(tempUsedCashTip, "2"), ".", "");
                            //saleHistoryTipAdjustmentTipAmountEditText.setText(GlobalMemberValues.getStringFormatNumber(tempUsedCashTip, "2"));

                            mUsedTipByCash = GlobalMemberValues.getStringFormatNumber(tempUsedCashTip, "2");

                            // 카드선택 비활성화
                            //saleHistoryTipAdjustmentCardRadioButton.setEnabled(false);
                        }
                        if (tempUsedCardTip > 0) {
                            // 카드 팁 총액
                            double tempUsedCardTipAmount = GlobalMemberValues.getDoubleAtString(
                                    MssqlDatabase.getResultSetValueToString(
                                            " select sum(usedCard) from salon_sales_tip " +
                                                    " where salesCode = '" + mSalesCode + "' and employeeIdx = '" + mEmpIdx + "' "
                                    )
                            );
                            saleHistoryTipAdjustmentPayTypeRadioGroup.check(saleHistoryTipAdjustmentCardRadioButton.getId());
                            saleHistoryTipAdjustmentCardRadioButton.setOnClickListener(mClickCardRadioButton);
                            mPayType = "CARD";
                            mTempPriceValue = GlobalMemberValues.getReplaceText(GlobalMemberValues.getStringFormatNumber(tempUsedCardTipAmount, "2"), ".", "");
                            //saleHistoryTipAdjustmentTipAmountEditText.setText(GlobalMemberValues.getStringFormatNumber(tempUsedCardTipAmount, "2"));

                            // 현금선택 비활성화
                            saleHistoryTipAdjustmentCashRadioButton.setEnabled(false);
                            openTipCardListActivity();
                        }

                        // salon_sales_tip 테이블에 해당 주문건의 해당직원에 해당되는 데이터가 있으므로 "수정하는 것"으로 처리
                        // INS : insert         UPD : update
                        dbTableExecuteType = "UPD";
                        salonSalesTipidx = GlobalMemberValues.resultDB_checkNull_string(salonSalesTipCursor,0);
                        GlobalMemberValues.logWrite("salonSalesTipidx", "idx : " + salonSalesTipidx + "\n");

                        if (!GlobalMemberValues.isStrEmpty(salonSalesTipidx)) {
                            String tempQuery = "select idx from salon_sales_tip where salesCode = '" + mSalesCode + "' and employeeIdx = '" + mEmpIdx + "' ";
                            salonSalesTipidx_insqlite = MainActivity.mDbInit.dbExecuteReadReturnString(tempQuery);
                            GlobalMemberValues.logWrite("sqlitetipidxlog", "idx : " + salonSalesTipidx_insqlite + "\n");
                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //07052024 close resultset
                try {
                    if(!salonSalesTipCursor.isClosed()){
                        salonSalesTipCursor.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // -----------------------------------------------------------------------------------------------------
            } else {
                GlobalMemberValues.displayDialog(context, "Warning", "Invalid Access [No Employee Info (1)]", "Close");
                finish();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //07052024 close resultset
        try {
            if(!salonSalesDetailCursor.isClosed()){
                salonSalesDetailCursor.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /***********************************************************************************************************/

        saleHistoryTipAdjustmentPayTypeRadioGroup.setOnCheckedChangeListener(mRadioChange);

        GlobalMemberValues.logWrite("salonSalesTipidx", "dbTableExecuteType : " + dbTableExecuteType + "\n");

        //
        // 배치된 주문인지 확인
        if (!GlobalMemberValues.isStrEmpty(mSalesCode)) {
            int tempBatchSaleCount = 0;
            tempBatchSaleCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    "select count(idx) from salon_sales where salesCode = '" + mSalesCode + "' " +
                            " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
            ));
            if (tempBatchSaleCount > 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "Batched Order can not be void", "Close");
                return;
            }
        }
    }


    RadioButton.OnClickListener mClickCardRadioButton = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            openTipCardListActivity();
        }
    };

    RadioGroup.OnCheckedChangeListener mRadioChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getId() == R.id.saleHistoryTipAdjustmentPayTypeRadioGroup) {
                switch (checkedId) {
                    case R.id.saleHistoryTipAdjustmentCashRadioButton : {
                        mPayType = "CASH";

                        mTempPriceValue = "";

                        saleHistoryTipAdjustmentCardImageTextView.setBackgroundColor(Color.parseColor("#e9ebef"));
                        saleHistoryTipAdjustmentCardNumberTextView.setText("");

                        mSelectedSalonSalesCard_Split_transaction_id = "";
                        mSelectedSalonSalesCardCardCom = "";
                        mSelectedSalonSalesCardTipAmount = "";
                        mSelectedSalonSalesCardCardLastFourDigitNumbers = "";
                        mSelectedSalonSalesCardCardRefNumber = "";
                        mSelectedSalonSalesCardCardEmvAid = "";
                        mSelectedSalonSalesCardCardEmvTsi = "";
                        mSelectedSalonSalesCardCardEmvTvr = "";

                        saleHistoryTipAdjustmentTipAmountEditText.setText(mUsedTipByCash);

                        break;
                    }
                    case R.id.saleHistoryTipAdjustmentCardRadioButton : {
                        openTipCardListActivity();
                        break;
                    }
                }
            }
        }
    };

    private void openTipCardListActivity() {
        if (mTipuse == "0" || mTipuse.equals("0")) {
            mPayType = "CARD";

            // 결제된 카드가 오프라인 카드인지 체크
            String tempCardCom = MssqlDatabase.getResultSetValueToString(
                    "select cardCom from salon_sales_card where salesCode = '" + mSalesCode + "'");
            if (!GlobalMemberValues.isStrEmpty(tempCardCom) && !tempCardCom.equals("OFFLINECARD")) {
                mSelectedSalonSalesCardCardOffCardYN = "N";

                // 카드결제가 2개 이상 결제가 되어있을 경우 카드선택하는 액티비티를 오픈하고 -------------------------------------------------
                // 1개일 경우에는 해당카드를 자동으로 선택한다. ----------------------------------------------------------------------------
                String salonSalesDetailQuery = "select count(idx) from salon_sales_card " +
                        " where salesCode = '" + mSalesCode + "' and status < 1 ";
                int cardPaymentCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(salonSalesDetailQuery));
                if (cardPaymentCount > 1) {         // 카드결제가 2개 이상일 경우
                    Intent tipCardList = new Intent(getApplicationContext(), TipCardList.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ---------------------------------------------
                    tipCardList.putExtra("salesCode", mSalesCode);
                    // -------------------------------------------------------------------------------------
                    startActivity(tipCardList);
                } else {                            // 카드결제가 1개만 있을 경우
                    String tempSql = "select split_transaction_id from salon_sales_card " +
                            " where salesCode = '" + mSalesCode + "' and status < 1 ";
                    String cardPayment_Split_transaction_id = MssqlDatabase.getResultSetValueToString(tempSql);
                    if (!GlobalMemberValues.isStrEmpty(cardPayment_Split_transaction_id)) {
                        String strQuery = " select cardCom, tipAmount, cardLastFourDigitNumbers, " +
                                " cardRefNumber, cardEmvAid, cardEmvTsi, cardEmvTvr, " +
                                " priceAmount " +            // 12212022
                                " from salon_sales_card " +
                                " where split_transaction_id = '" + cardPayment_Split_transaction_id + "' ";
                        //Cursor salonSalesCardCursor = dbInit.dbExecuteRead(strQuery);
                        ResultSet salonSalesCardCursor = MssqlDatabase.getResultSetValue(strQuery);
                        try {
                            if (salonSalesCardCursor.next()) {

                                String vCardCom = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,0), 1);
                                String vTipAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,1), 1);
                                String vCardLastFourDigitNumbers = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,2), 1);
                                String vCardRefNumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,3), 1);
                                String vCardEmvAid = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,4), 1);
                                String vCardEmvTsi = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,5), 1);
                                String vCardEmvTvr = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,6), 1);
                                // 12212022
                                String vPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCardCursor,7), 1);

                                if (!GlobalMemberValues.isStrEmpty(vCardRefNumber)) {
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCard_Split_transaction_id = cardPayment_Split_transaction_id;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardCom = vCardCom;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardTipAmount = vTipAmount;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardLastFourDigitNumbers = vCardLastFourDigitNumbers;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardRefNumber = vCardRefNumber;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvAid = vCardEmvAid;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvTsi = vCardEmvTsi;
                                    SaleHistoryTipAdjustment.mSelectedSalonSalesCardCardEmvTvr = vCardEmvTvr;
                                    // 12212022
                                    mSelectedSalonSalesCardPriceAmount = vPriceAmount;


                                    if (GlobalMemberValues.getDoubleAtString(vTipAmount) > 0) {
                                        SaleHistoryTipAdjustment.saleHistoryTipAdjustmentTipAmountEditText.setText(
                                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(vTipAmount), "2")
                                        );
                                    }

                                    String vSuValue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(vTipAmount), "2");
                                    SaleHistoryTipAdjustment.mTempPriceValue
                                            = GlobalMemberValues.getReplaceText(vSuValue, ".", "");

                                    SaleHistoryTipAdjustment.saleHistoryTipAdjustmentCardNumberTextView.setText(
                                            "**** **** **** " + vCardLastFourDigitNumbers
                                    );

                                    GlobalMemberValues.logWrite("selectedCardCom", "선택카드사 : " + vCardCom + "\n");

                                    int tempCardImgId = R.drawable.aa_images_cardimg_visa;
                                    switch (vCardCom) {
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
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
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
                // -----------------------------------------------------------------------------------------------------------------------
            } else {
                mSelectedSalonSalesCardCardOffCardYN = "Y";
                String tempSql = "select split_transaction_id from salon_sales_card " +
                        " where salesCode = '" + mSalesCode + "' and status < 1 ";
                String cardPayment_split_transaction_id = MssqlDatabase.getResultSetValueToString(tempSql);
                if (!GlobalMemberValues.isStrEmpty(cardPayment_split_transaction_id)) {
                    mSelectedSalonSalesCard_Split_transaction_id = cardPayment_split_transaction_id;
                }
            }
            GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 2" + "\n");

        } else {
            saleHistoryTipAdjustmentCashRadioButton.setChecked(true);
            GlobalMemberValues.displayDialog(context, "Information",
                    "You can change whether or not to use online credit cards\nin the settings.", "Close");
        }
    }

    View.OnClickListener saleHistoryTipAdjustmentBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saleHistoryTipAdjustmentCloseBtn : {
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);

                    finish();
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton1 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton2 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton3 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton4 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton5 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton6 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton7 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton8 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton9 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton0 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButton00 : {
                    numberButtonClick(saleHistoryTipAdjustmentSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButtonBack : {
                    StringBuilder sb = new StringBuilder();
                    sb.delete(0, sb.toString().length());
                    sb.append(mTempPriceValue);
                    if (!GlobalMemberValues.isStrEmpty(mTempPriceValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mTempPriceValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mTempPriceValue)) {
                            mTempPriceValue = "0";
                        }
                    } else {
                        mTempPriceValue = "0";
                    }

                    String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * 0.01), "2");
                    saleHistoryTipAdjustmentTipAmountEditText.setText(inputSu);

                    GlobalMemberValues.logWrite("QuickSale", "mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);
                    break;
                }
                case R.id.saleHistoryTipAdjustmentSuButtonV : {
                    if (GlobalMemberValues.isStrEmpty(mPayType)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Check cash or card", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mSalesCode)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Invalid Access [No Sales #]", "Close");
                        return;
                    }
                    if (GlobalMemberValues.isStrEmpty(mSalonSalesDetailIdx)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Invalid Access [No Selected Item]", "Close");
                        return;
                    }

                    String strQuery = "";

                    String tempTipAmount = saleHistoryTipAdjustmentTipAmountEditText.getText().toString();

                    String tipResultCode = "00";

                    Vector<String> strQueryVec = new Vector<String>();
                    switch (mPayType) {
                        case "CASH" : {
                            if (dbTableExecuteType.equals("INS")) {
                                strQuery = "insert into salon_sales_tip (" +
                                        " sidx, salesCode, employeeIdx, employeeName, usedCash, codeforupload, isCloudUpload " +
                                        " ) values (" +
                                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                        " '" + mSalesCode + "', " +
                                        " '" + mEmpIdx + "', " +
                                        " '" + GlobalMemberValues.getDBTextAfterChecked(mEmpName, 0) + "', " +
                                        " '" + tempTipAmount + "', " +
                                        " '" + GlobalMemberValues.getCodeForUpload("tip") + "', " +
                                        " '" + "0" + "' " +
                                        " ) ";
                            } else {
                                strQuery = "update salon_sales_tip set " +
                                        " usedCash = '" + tempTipAmount + "', " +
                                        " isCloudUpload = 0 " +
                                        " where salesCode = '" + mSalesCode + "' ";

                                // 05.09.2022
                                // 팁 데이터 업로드를 위한 sqlite 에 데이터 수정
                                String strQuery2 = "update salon_sales_tip set " +
                                        " usedCash = '" + tempTipAmount + "', " +
                                        " isCloudUpload = 0 " +
                                        " where salesCode = '" + mSalesCode + "' ";
                                GlobalMemberValues.logWrite("sqlitetipidxlog", "strQuery2 : " + strQuery2 + "\n");
                                MainActivity.mDbInit.dbExecuteWriteReturnValueOnlySqlite(strQuery2);
                            }
                            strQueryVec.addElement(strQuery);


                            for (String sql : strQueryVec) {
                                GlobalMemberValues.logWrite("SalonSalesTipQueryVec", "query : " + sql + "\n");
                            }

                            // 트랜잭션으로 DB 처리한다.
                            String returnResult = "";
                            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
                            if (returnResult == "N" || returnResult == "") {
                                GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                // 초기화
                                setInit();
                                // 키패드(키보드) 감추기
                                GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);
                                finish();
                            } else { // 정상처리일 경우 Sale History 리스트 리로드
                                // 팁 데이터 클라우드 전송 ------------------------------------------------------------
                                GlobalMemberValues.setSendTipsToCloud(context, mActivity);
                                // -------------------------------------------------------------------------------------
                                GlobalMemberValues.logWrite("UploadTipsDataToCloud", "여기실행됨 - 2." + "\n");
                                // 초기화
                                setInit();
                                // 키패드(키보드) 감추기
                                GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);
                                if (!is_history_list){
                                    SaleHistory.mActivity.recreate();
                                }
                                mActivity.finish();
                                //setSearchSalesHistory();
                            }

                            break;
                        }
                        case "CARD" : {
                            // 카드결제 처리 ----------------------------------------------------------
                            GlobalMemberValues.logWrite("tipbycreditcard", "카드처리할 금액 : " + tempTipAmount + "\n");
                            if (mSelectedSalonSalesCardCardOffCardYN == "N" || mSelectedSalonSalesCardCardOffCardYN.equals("N")) {
                                switch (paymentGateway) {
                                    // Ingenico ICT200 인지니코
                                    case "0": {
                                        setCreditCardProcessModifyForIngenico(GlobalMemberValues.getDoubleAtString(tempTipAmount));
                                        break;
                                    }
                                    // PAX
                                    case "1": {
                                        setCreditCardProcessModifyForPax();
                                        break;
                                    }
                                }
                                // ----------------------------------------------------------------------
                            } else {
                                creditCardReturnValue = "00";
                                databaseProcessAfterCardProcess(GlobalMemberValues.getDoubleAtString(tempTipAmount), "OFFLINE");
                            }
                            break;
                        }
                    }

                    break;
                }
            }
        }
    };

    public static void setCreditCardProcessModifyForPax() {
        if (!GlobalMemberValues.isStrEmpty(mSelectedSalonSalesCardCardRefNumber)) {
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


            // 07222024 ------------------------------------------------------------
            String sales_pgdevicenum = MssqlDatabase.getResultSetValueToString(
                    " select pgdevicenum from salon_sales where salescode = '" + mSalesCode + "' "
            );
            String sales_pgip = "";
            String sales_pgport = "";
            if (!GlobalMemberValues.isStrEmpty(sales_pgdevicenum)) {
                sales_pgip = MssqlDatabase.getResultSetValueToString(
                        " select networkip from salon_pgip where pgdevicenum = '" + sales_pgdevicenum + "' "
                );
                sales_pgport = MssqlDatabase.getResultSetValueToString(
                        " select networkport from salon_pgip where pgdevicenum = '" + sales_pgdevicenum + "' "
                );
            }
            // 07222024 ------------------------------------------------------------


            Intent intent = new Intent(context, JJJ_PaxPay.class);
            // 객체 및 데이터 전달하기 ---------------------------------------------------------------
            intent.putExtra("cardtendertype", "CREDIT");
            intent.putExtra("processtype", "ADJUST");
            intent.putExtra("amounttopay",
                    String.valueOf(GlobalMemberValues.getIntAtString2(saleHistoryTipAdjustmentTipAmountEditText.getText().toString()))
            );
            intent.putExtra("cardcom", mSelectedSalonSalesCardCardCom);
            intent.putExtra("lastfourdigits", mSelectedSalonSalesCardCardLastFourDigitNumbers);
            intent.putExtra("refnum", mSelectedSalonSalesCardCardRefNumber);
            intent.putExtra("ecrrefnum", Payment.mSalesCode);
            intent.putExtra("fromsignpad", "N");


            // 07222024
            intent.putExtra("sales_pgip", sales_pgip);
            intent.putExtra("sales_pgport", sales_pgport);


            GlobalMemberValues.logWrite("cardtipadjustment", "card comp : " + mSelectedSalonSalesCardCardCom + "\n");
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(intent);
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Invalid Access [No Employee Info (2)]", "Close");
        }
    }

    public void setCreditCardProcessModifyForIngenico(double paramTipAmount) {
        // 결제할 금액
        final double PRICEAMT = paramTipAmount;

        // 프로그래스 바를 실행~
        proDial = ProgressDialog.show(this, "NAVYZEBRA MSALON ANDROID", "Credit Card Processing...", true, false);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                /** 1. 처리가 오래걸리는 부분 실행 *****************************************************/
                if (GlobalMemberValues.CARD_DEVICE_TESTVERSION_YN == "Y") {     // 카드 단말기 없는 테스트버전일 떄..
                    creditCardReturnValue = "00";
                    modifyPayAmount = PRICEAMT;
                } else {

                    // 카드 단말기 있는 실제버전일 때..
                    // 카드 결제처리
                    //                PayByCreditCard payByCreditCard = new PayByCreditCard();
                    //                creditCardReturnValue = payByCreditCard.creditCardPaymentByTerminalForModify(mSelectedSalonSalesCardCardRefNumber, PRICEAMT);
                    //                GlobalMemberValues.logWrite("tipbycreditcard", "카드처리결과 : " + creditCardReturnValue + "\n");
                    //                modifyPayAmount = PRICEAMT;
                    //                GlobalMemberValues.logWrite("tipbycreditcard", "카드처리후 넘겨받은 금액 : " + modifyPayAmount + "\n");

                    RestApiTipAdd.TipAddCallbackEvent tipAddCallbackEvent = new RestApiTipAdd.TipAddCallbackEvent() {
                        @Override
                        public void TipAddcallback(JSONObject jsonObject) {
                            Log.e("TipAddCallBack!!!!!",jsonObject.toString());
                            try {
                                creditCardReturnValue = jsonObject.getString("returncodefromdevice");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            GlobalMemberValues.logWrite("tipbycreditcard", "카드처리결과 : " + creditCardReturnValue + "\n");
                            modifyPayAmount = PRICEAMT;
                            GlobalMemberValues.logWrite("tipbycreditcard", "카드처리후 넘겨받은 금액 : " + modifyPayAmount + "\n");

                            /*************************************************************************************/
                            /** 2. 작업이 끝나면 이 핸들러를 호출 **************************************************/
                            handler.sendEmptyMessage(0);
                            /*************************************************************************************/
                        }
                    };
                    ConfigPGInfo pgInfo = new ConfigPGInfo();
                    String networkIp = pgInfo.cfnetworkip;
                    String networkPort = pgInfo.cfnetworkport;

                    RestApiTipAdd tipAdd = new RestApiTipAdd(tipAddCallbackEvent);
                    RestApiConfig config = new RestApiConfig();
                    config.setIpnumber(networkIp);
                    config.setPortnumber(networkPort);
                    tipAdd.RequestTipAdd(GlobalMemberValues.getIntAtString2(String.valueOf(PRICEAMT)), "");
                }
                /** 2. 작업이 끝나면 이 핸들러를 호출 **************************************************/
                // 테스트시에만..
                if (GlobalMemberValues.CARD_DEVICE_TESTVERSION_YN == "Y") {
                    handler.sendEmptyMessage(0);
                }
                /*************************************************************************************/
            }
        });
        thread.start();
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업(카드 API 연동)이 끝난후 처리해야할 부분을 넣음. ---------------
            GlobalMemberValues.logWrite("tipbycreditcard", "--- 시간이 걸리는 작업이 끝난뒤 처리할 메소드 시작 -------\n");
            databaseProcessAfterCardProcess(modifyPayAmount, mSelectedSalonSalesCardCardCom);
            GlobalMemberValues.logWrite("tipbycreditcard", "--- 시간이 걸리는 작업이 끝난뒤 처리할 메소드 완료 -------\n");
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            proDial.dismiss();
            // -------------------------------------------------------------------------------------
        }
    };

    public static void databaseProcessAfterCardProcess(double paramModPriceAmount, String paramCardCom) {
        String tipResultCode = "";
        String strQuery = "";

        GlobalMemberValues.logWrite("tipbycreditcard", "DB 처리전 카드처리결과 값 확인 : " + creditCardReturnValue + "\n");

        if (!creditCardReturnValue.equals("00")) {
            tipResultCode = "99";
            GlobalMemberValues.displayDialog(context, "Warning", "Credit Card Processing Error", "Close");
            return;
        } else {
            tipResultCode = "00";
            /**
             // 원래 팁금액 가져오기 (카드) ----------------------------------------------------------------------
             String tempSql = "select usedCard from salon_sales_tip where idx = '" + salonSalesTipidx + "' ";
             String originTipAmountStr = dbInit.dbExecuteReadReturnString(tempSql);
             // -----------------------------------------------------------------------------------------------
             **/

            Vector<String> strQueryVec = new Vector<String>();

            strQuery = "update salon_sales_card set " +
                    " tipAmount = " + paramModPriceAmount + " " +
                    " where split_transaction_id = '" + mSelectedSalonSalesCard_Split_transaction_id + "' ";
            GlobalMemberValues.logWrite("SaleHistoryTipAdjustmentLog", "strQuery : " + strQuery + "\n");
            strQueryVec.addElement(strQuery);

            if (dbTableExecuteType.equals("INS")) {
                strQuery = "insert into salon_sales_tip (" +
                        " sidx, salesCode, employeeIdx, employeeName, usedCard, cardCom, codeforupload, isCloudUpload, refnum " +
                        " ) values (" +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + mSalesCode + "', " +
                        " '" + mEmpIdx + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(mEmpName, 0) + "', " +
                        " '" + paramModPriceAmount + "', " +
                        " '" + paramCardCom + "', " +
                        " '" + GlobalMemberValues.getCodeForUpload("tip") + "', " +
                        " '" + "0" + "', " +
                        " '" + mSelectedSalonSalesCardCardRefNumber + "' " +
                        " ) ";
            } else {
                double tempTipamount = GlobalMemberValues.getDoubleAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select sum(tipamount) from salon_sales_card where salescode = '" + mSalesCode + "' and not(split_transaction_id = '" + mSelectedSalonSalesCard_Split_transaction_id + "') "
                        )
                );
                double tipamount = tempTipamount + paramModPriceAmount;


                strQuery = "update salon_sales_tip set " +
                        " usedCard = " + tipamount + ", " +
                        " isCloudUpload = '0' " +
                        " where salesCode = '" + mSalesCode + "' ";

//                String mssql_strQuery = "update salon_sales_tip set " +
//                        " usedCard = " + tipamount + ", " +
//                        " isCloudUpload = '0' " +
//                        " where idx = '" + salonSalesTipidx + "' ";
//                MssqlDatabase.executeTransactionByQuery(mssql_strQuery);



                // 05.09.2022
                // 팁 데이터 업로드를 위한 sqlite 에 데이터 수정
                String strQuery2 = "update salon_sales_tip set " +
                        " usedCard = '" + tipamount + "', " +
                        " isCloudUpload = 0 " +
                        " where salesCode = '" + mSalesCode + "' ";
                GlobalMemberValues.logWrite("sqlitetipidxlog", "strQuery2 : " + strQuery2 + "\n");
                MainActivity.mDbInit.dbExecuteWriteReturnValueOnlySqlite(strQuery2);

            }
            strQueryVec.addElement(strQuery);

            // 처리결과에 따라 DB 처리 --------------------------------------------------------------------
            if (tipResultCode.equals("00")) {   // 성공 (00)
                for (String sql : strQueryVec) {
                    GlobalMemberValues.logWrite("tipbycreditcard", "DB 처리할 query : " + sql + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);
                    //mActivity.finish();
                } else { // 정상처리일 경우 Sale History 리스트 리로드
                    setCardTipSplitSave(paramModPriceAmount);
                }
            } else {    //실패
                return;
            }
            // ------------------------------------------------------------------------------------------
        }
    }

    // 04.27.2022 추가 --------------------------------------------------------------------------------------------
    public static void setCardTipSplitSave(double paramModPriceAmount) {

        // billpaididx 구하기
        String transaction_id = MssqlDatabase.getResultSetValueToString(
                " select idx from bill_list_paid where split_transaction_id = '" + mSelectedSalonSalesCard_Split_transaction_id + "' "
        );

        String cardsalestipidx = "";
        if (GlobalMemberValues.isStrEmpty(salonSalesTipidx)) {
            // cardsalestipidx 구하기
            String tempSql = " select top 1 idx from salon_sales_tip where salescode = '" + mSalesCode + "' order by idx desc ";
            String tempTipidx = MssqlDatabase.getResultSetValueToString(tempSql);
            GlobalMemberValues.logWrite("carttipjjjlogjjj", "sql : " + tempSql + "\n");
            if (GlobalMemberValues.isStrEmpty(tempTipidx)) {
                tempTipidx = "1";
            }

            cardsalestipidx = tempTipidx;
        } else {
            cardsalestipidx = salonSalesTipidx;
        }

        // 신규입력인지여부 확인
        String tempTipSplitIdx = MssqlDatabase.getResultSetValueToString(
                " select idx from salon_sales_tip_split where salescode = '" + mSalesCode + "' " +
                        " and split_transaction_id = '" + mSelectedSalonSalesCard_Split_transaction_id + "' "
        );

        Vector<String> strQueryVec = new Vector<String>();
        // salon_sales_tip_split 에 저장
        String strQuery = "";
        if (GlobalMemberValues.isStrEmpty(tempTipSplitIdx)) {
            strQuery = "insert into salon_sales_tip_split (" +
                    " salesCode, sidx, stcode, billpaididx, cardsalesidx, cardsalestipidx, tipamount, paytype, split_transaction_id " +
                    " ) values (" +
                    " '" + mSalesCode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + "0" + "', " +
                    " '" + "0" + "', " +
                    " '" + cardsalestipidx + "', " +
                    " '" + paramModPriceAmount + "', " +
                    " '" + mPayType + "', " +
                    " '" + mSelectedSalonSalesCard_Split_transaction_id + "' " +
                    " ) ";
        } else {
            strQuery = " update salon_sales_tip_split set" +
                    " tipamount = '" + paramModPriceAmount + "' " +
                    " where split_transaction_id = '" + mSelectedSalonSalesCard_Split_transaction_id + "' ";
        }

        GlobalMemberValues.logWrite("tipinsuplogjjj", "sql : " + strQuery + "\n");
        strQueryVec.addElement(strQuery);

        String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우 Sale History 리스트 리로드
            // 팁 데이터 클라우드 전송 ------------------------------------------------------------
            GlobalMemberValues.setSendTipsToCloud(context, mActivity);
            // -------------------------------------------------------------------------------------
            GlobalMemberValues.logWrite("UploadTipsDataToCloud", "여기실행됨 - 2." + "\n");
            // 초기화
            setInit();
            // 키패드(키보드) 감추기
            GlobalMemberValues.setKeyPadHide(context, saleHistoryTipAdjustmentTipAmountEditText);

            if (SaleHistory.mActivity != null) {
                SaleHistory.mActivity.recreate();
            }

//            mActivity.recreate();
            mActivity.finish();
            //setSearchSalesHistory();
        }
    }
    // -----------------------------------------------------------------------------------------------------------

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mTempPriceValue.length() < 12) {
            sb.append(mTempPriceValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            mTempPriceValue = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * 0.01), "2");
            saleHistoryTipAdjustmentTipAmountEditText.setText(inputSu);
            //GlobalMemberValues.logWrite("mTempPriceValue111", "mTempPriceValue : " + mTempPriceValue + "\n");
        }
    }

    // 초기화 메소드
    private static void setInit() {
        saleHistoryTipAdjustmentSalesCodeTextView.setText("");
        saleHistoryTipAdjustmentEmployeeTextView.setText("");
        saleHistoryTipAdjustmentTipAmountEditText.setText("0.0");

        saleHistoryTipAdjustmentCardNumberTextView.setText("");

        mSalesCode = "";
        mSalonSalesDetailIdx = "";

        mTempPriceValue = "";

        mSelectedSalonSalesCard_Split_transaction_id = "";
        mSelectedSalonSalesCardCardCom = "";
        mSelectedSalonSalesCardTipAmount = "";
        mSelectedSalonSalesCardCardLastFourDigitNumbers = "";
        mSelectedSalonSalesCardCardRefNumber = "";
        mSelectedSalonSalesCardCardEmvAid = "";
        mSelectedSalonSalesCardCardEmvTsi = "";
        mSelectedSalonSalesCardCardEmvTvr = "";

        creditCardReturnValue = "";
        salonSalesTipidx = "";

        dbTableExecuteType = "INS";          // INS : insert        UPD : update
        mEmpIdx = "";
        mEmpName = "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (is_history_list){
            Intent intent = new Intent();
            intent.setAction(SaleHistoryList.SaleHistoryListReceiver.ACTION);
            intent.putExtra("TipAddPopup", "close");
            intent.putExtra("list_row_number", i_salehistory_row_num);
            context.sendBroadcast(intent);
        }
    }
}
