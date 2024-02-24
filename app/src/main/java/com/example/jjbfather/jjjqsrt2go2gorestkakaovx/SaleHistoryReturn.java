package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class SaleHistoryReturn extends Activity {
    static Activity mActivity;
    static Context context;
    public static Context insContext;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    static DatabaseInit dbInit;

    LinearLayout parentLn;

    private TextView saleHistoryReturnPaymentUseCashTextView;
    private TextView saleHistoryReturnPaymentUseCardTextView;
    private TextView saleHistoryReturnPaymentUseGiftCardTextView;
    private TextView saleHistoryReturnPaymentUseCheckTextView;
    private TextView saleHistoryReturnPaymentUsePointTextView;

    private TextView saleHistoryReturnPaymentEditText;

    private Button closeBtn;

    private Button saleHistoryReturnSuButton1,saleHistoryReturnSuButton2,saleHistoryReturnSuButton3;
    private Button saleHistoryReturnSuButton4,saleHistoryReturnSuButton5,saleHistoryReturnSuButton6;
    private Button saleHistoryReturnSuButton7,saleHistoryReturnSuButton8,saleHistoryReturnSuButton9;
    private Button saleHistoryReturnSuButton0,saleHistoryReturnSuButton00,saleHistoryReturnSuButtonBack;
    private Button saleHistoryReturnSuButtonV;

    private static TextView saleHistoryReturnBalanceEditText;
    private static TextView saleHistoryReturnTenderedEditText;
    private static TextView saleHistoryReturnChangeEditText;

    private static TextView saleHistoryReturnCashEditText,saleHistoryReturnGiftCardEditText;
    private static TextView saleHistoryReturnCheckEditText,saleHistoryReturnPointEditText;

    public static TextView saleHistoryReturnCardEditText;

    private ListView saleHistoryReturnEmployeeListView;

    private static LinearLayout saleHistoryReturnCashLinearLayout;
    private static LinearLayout saleHistoryReturnCardLinearLayout;
    private static LinearLayout saleHistoryReturnGiftCardLinearLayout;
    private static LinearLayout saleHistoryReturnCheckLinearLayout;
    private static LinearLayout saleHistoryReturnPointLinearLayout;

    private TextView saleHistoryReturnPaymentPaytypeTitleTextView;
    private TextView saleHistoryReturnPaymentPaytypeSubTitleTextView1, saleHistoryReturnPaymentPaytypeSubTitleTextView2;
    private TextView saleHistoryReturnPaymentPaytypeSubTitleTextView3, saleHistoryReturnPaymentPaytypeSubTitleTextView4;
    private TextView saleHistoryReturnPaymentPaytypeSubTitleTextView5;
    private TextView saleHistoryReturnPaymentBalanceTitleTextView;
    private TextView saleHistoryReturnPaymentTenderedTitleTextView;
    private TextView saleHistoryReturnPaymentChangeTitleTextView;

    private TextView saleHistoryReturnCashTitleTextView, saleHistoryReturnCardTitleTextView;
    private TextView saleHistoryReturnGiftCardTitleTextView, saleHistoryReturnCheckTitleTextView;
    private TextView saleHistoryReturnPointTitleTextView;

    private static Switch saleHistoryReturnOfflineSwitch;

    public static TextView mFocustEditText;

    // 임시저장값
    static String mTempPriceValue = "";

    // 사용할 결제수단
    static String mIssaleHistoryReturnTypeCash       = "N";
    static String mIssaleHistoryReturnTypeCard       = "N";
    static String mIssaleHistoryReturnTypeGiftCard   = "N";
    static String mIssaleHistoryReturnTypeCheck      = "N";
    static String mIssaleHistoryReturnTypePoint      = "N";

    // 전체 결제해야 될 금액
    public static double mTotalPayValue = 0.0;

    // saleHistoryReturn 타입 선택 및 결제금액 입력후 잔액담는 변수
    public static double mBalanceToPay = 0.0;

    // saleHistoryReturn 타입중 Card 와 Point 선택시 저장되는 변수
    public double mBalanceToPayForCardPoint = 0.0;

    // Point 결제수단 선택시 현재 선택된 고객의 포인트를 저장할 객체 선언
    public double mCustomerPoint = 0.0;

    // 사용한 기프트카드 정보
    public static String mGiftCardNumberUsed = "";

    // 쿼리문 저장용 객체
    static String strInsSqlQuery = "";
    static String strUpdSqlQuery = "";
    static String strDelSqlQuery = "";
    static String strInsSqlQuery_item = "";
    static String strUpdSqlQuery_item = "";

    Intent mIntent;

    static String mSalesCode = "";
    static ArrayList<String> mSelectedItemIdxArrayList;
    static ArrayList<String> mSelectedTipIdxArrayList;
    static double mTotalAmount = 0.0;
    static double mTotalTipAmount = 0.0;
    static double mPickupDeliveryFee = 0.0;

    // salon_sales 테이블 관련 정보 ------------------------------------------------
    int mIsTotalCashUse = 1;
    int mIsTotalCardUse = 1;
    int mIsTotalGiftCardUse = 1;
    int mIsTotalCheckUse = 1;
    int mIsTotalPointUse = 1;

    double mUseTotalCashAmount = 0.0;
    double mUseTotalCardAmount = 0.0;
    double mUseTotalGiftCardAmount = 0.0;
    double mUseTotalCheckAmount = 0.0;
    double mUseTotalPointAmount = 0.0;

    static String mCustomerId;
    // 카드결제시 Tid 번호
    static String mCardTidNumber = "";

    // 외부결제 업체
    static String mCheckCompany = "";
    // ------------------------------------------------------------------------------

    String returnResultAfterReturn = "";

    // 카드결제 선택시에만 사용되는 객체
    String mCardUseYN = "N";

    // PaymentCreditCard 클랙스에서 카드정보를 저장한 JSONObject 클래스의 jsontmp 인스턴스를 저장하기 위한 JSONArray 객체
    // 클래스명으로 접근하기 위해 static 으로 선언
    public static JSONArray mJsonList = new JSONArray();

    // 영수증 출력을 위한 JSON 객체선언
    static JSONObject jsonroot = null;

    // salon_sales_detail, salon_sales_return, salon_sales_card 를 바인딩해줄 리턴코드
    static String mStrReturnCode = "";

    static String mGetGiftCardBalanceInfo = "";
    static String mGetMemberMileage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history_return);
        this.setFinishOnTouchOutside(false);

        this.mActivity = this;
        this.context = this;

		this.dataAtSqlite = new GetDataAtSQLite(this);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);



        setContents();
    }

    public void setContents() {
        /** 인텐트에서 받은 Extra 값 저장 ***************************************************************************/
        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 salesCode 값
            mSalesCode = mIntent.getStringExtra("selectedSalesCode");
            mTotalAmount = mIntent.getDoubleExtra("selectedItemTotalAmount", 0.0);
            mSelectedItemIdxArrayList = mIntent.getStringArrayListExtra("selectedItemIdxArrayList");
            mSelectedTipIdxArrayList = mIntent.getStringArrayListExtra("selectedTipIdxArrayList");
            mTotalTipAmount = mIntent.getDoubleExtra("selectedTipAmount", 0.0);
            mPickupDeliveryFee = mIntent.getDoubleExtra("selectedPickupDeliveryFeeAmount", 0.0);
            GlobalMemberValues.logWrite("salesHistoryReturn", "넘겨받은 selectedSalesCode : " + mSalesCode + "\n");
            GlobalMemberValues.logWrite("salesHistoryReturn", "넘겨받은 mTotalAmount : " + mTotalAmount + "\n");

            GlobalMemberValues.logWrite("salesHistoryReturn", "넘겨받은 mPickupDeliveryFee : " + mPickupDeliveryFee + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }
        /***********************************************************************************************************/

        /** salon_sales 테이블 필드값 저장 *************************************************************************/
        String salonSalesQuery = "select " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " customerId, checkcompany " +
                " from salon_sales where salesCode = '" + mSalesCode + "' ";
        ResultSet salonSalesCursor = MssqlDatabase.getResultSetValue(salonSalesQuery);
        try{
            while (salonSalesCursor.next()){
                mIsTotalCashUse = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,0);
                mIsTotalCardUse = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,1);
                mIsTotalGiftCardUse = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,2);
                mIsTotalCheckUse = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,3);
                mIsTotalPointUse = GlobalMemberValues.resultDB_checkNull_int(salonSalesCursor,4);

                mUseTotalCashAmount = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,5);
                mUseTotalCardAmount = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,6);
                mUseTotalGiftCardAmount = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,7);
                mUseTotalCheckAmount = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,8);
                mUseTotalPointAmount = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,9);

                mCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,10), 1);
                mCheckCompany = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,11), 1);
            }
//            if (true) {
//
//
//            } else {
//                GlobalMemberValues.displayDialog(context, "Warning", "Error - No Data", "Close");
//                return;
//            }
        }catch (Exception e){

        }


        /***********************************************************************************************************/

        /** salon_sales_tip 테이블 필드값 저장 **********************************************************************/
        String salonSalesTipQuery = " select usedCash, usedCard " +
                " from salon_sales_tip where salesCode = '" + mSalesCode + "' ";
        ResultSet salonSalesTipCursor = MssqlDatabase.getResultSetValue(salonSalesTipQuery);
        try {
//            salonSalesTipCursor.last();
//            int rowcount = salonSalesTipCursor.getRow();
//            salonSalesTipCursor.first();
            while (salonSalesCursor.next()){
                double tempDblTipCash = 0.0;
                double tempDblTipCard = 0.0;
                tempDblTipCash = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,0);
                tempDblTipCard = GlobalMemberValues.resultDB_checkNull_double(salonSalesCursor,1);

                mUseTotalCashAmount += tempDblTipCash;
                mUseTotalCardAmount += tempDblTipCard;
            }
//            if (rowcount > 0 ) {
//
//            }
        } catch (Exception e){

        }

        /***********************************************************************************************************/

        /** 좌측상단 PAYMENT 요약 관련 TextView 객체 생성 및 값 할당 ***********************************************/
        saleHistoryReturnPaymentUseCashTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentUseCashTextView);
        saleHistoryReturnPaymentUseCardTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentUseCardTextView);
        saleHistoryReturnPaymentUseGiftCardTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentUseGiftCardTextView);
        saleHistoryReturnPaymentUseCheckTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentUseCheckTextView);
        saleHistoryReturnPaymentUsePointTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentUsePointTextView);

        saleHistoryReturnPaymentUseCashTextView.setText(GlobalMemberValues.getStringFormatNumber(mUseTotalCashAmount, "2"));
        saleHistoryReturnPaymentUseCardTextView.setText(GlobalMemberValues.getStringFormatNumber(mUseTotalCardAmount, "2"));
        saleHistoryReturnPaymentUseGiftCardTextView.setText(GlobalMemberValues.getStringFormatNumber(mUseTotalGiftCardAmount, "2"));
        saleHistoryReturnPaymentUseCheckTextView.setText(GlobalMemberValues.getStringFormatNumber(mUseTotalCheckAmount, "2"));
        saleHistoryReturnPaymentUsePointTextView.setText(GlobalMemberValues.getStringFormatNumber(mUseTotalPointAmount, "2"));

        saleHistoryReturnPaymentUseCashTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPaymentUseCashTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnPaymentUseCardTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPaymentUseCardTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnPaymentUseGiftCardTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPaymentUseGiftCardTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnPaymentUseCheckTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPaymentUseCheckTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnPaymentUsePointTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPaymentUsePointTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        /***********************************************************************************************************/

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(context) / 9) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(context) / 10) * 9;
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

        parentLn = (LinearLayout)findViewById(R.id.frameLayout_saleHistoryReturn);
        parentLn.setLayoutParams(parentLnParams);


        saleHistoryReturnPaymentPaytypeTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeTitleTextView);
        saleHistoryReturnPaymentPaytypeTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentPaytypeSubTitleTextView1 = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeSubTitleTextView1);
        saleHistoryReturnPaymentPaytypeSubTitleTextView1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeSubTitleTextView1.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentPaytypeSubTitleTextView2 = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeSubTitleTextView2);
        saleHistoryReturnPaymentPaytypeSubTitleTextView2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeSubTitleTextView2.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentPaytypeSubTitleTextView3 = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeSubTitleTextView3);
        saleHistoryReturnPaymentPaytypeSubTitleTextView3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeSubTitleTextView3.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentPaytypeSubTitleTextView4 = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeSubTitleTextView4);
        saleHistoryReturnPaymentPaytypeSubTitleTextView4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeSubTitleTextView4.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentPaytypeSubTitleTextView5 = (TextView)findViewById(R.id.saleHistoryReturnPaymentPaytypeSubTitleTextView5);
        saleHistoryReturnPaymentPaytypeSubTitleTextView5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentPaytypeSubTitleTextView5.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentBalanceTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentBalanceTitleTextView);
        saleHistoryReturnPaymentBalanceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentBalanceTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentTenderedTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentTenderedTitleTextView);
        saleHistoryReturnPaymentTenderedTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentTenderedTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPaymentChangeTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnPaymentChangeTitleTextView);
        saleHistoryReturnPaymentChangeTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPaymentChangeTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnCashTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnCashTitleTextView);
        saleHistoryReturnCashTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnCashTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnCardTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnCardTitleTextView);
        saleHistoryReturnCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnCardTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnGiftCardTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnGiftCardTitleTextView);
        saleHistoryReturnGiftCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnGiftCardTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnCheckTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnCheckTitleTextView);
        saleHistoryReturnCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnCheckTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnPointTitleTextView = (TextView)findViewById(R.id.saleHistoryReturnPointTitleTextView);
        saleHistoryReturnPointTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                saleHistoryReturnPointTitleTextView.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        saleHistoryReturnOfflineSwitch = (Switch)findViewById(R.id.saleHistoryReturnOfflineSwitch);
        saleHistoryReturnOfflineSwitch.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSWITCHTEXTSIZE);
        String tempCardchargesystemuse = dbInit.dbExecuteReadReturnString(
                " select cardchargesystemuse from salon_storestationsettings_paymentgateway "
        );
        if (tempCardchargesystemuse == "0" || tempCardchargesystemuse.equals("0")) {
            saleHistoryReturnOfflineSwitch.setChecked(true);
            saleHistoryReturnOfflineSwitch.setEnabled(true);
            saleHistoryReturnOfflineSwitch.setOnCheckedChangeListener(mSwitchCheckedChange);
        } else {
            saleHistoryReturnOfflineSwitch.setChecked(false);
            saleHistoryReturnOfflineSwitch.setOnCheckedChangeListener(mSwitchCheckedChangeOnDisabled);
            //saleHistoryReturnOfflineSwitch.setEnabled(false);
        }

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("saleHistoryReturnCloseBtnTag");
        closeBtn.setOnClickListener(saleHistoryReturnBtnClickListener);
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

        saleHistoryReturnPaymentEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnPaymentEditTextTag");
        saleHistoryReturnPaymentEditText.setTextSize(GlobalMemberValues.globalAddFontSize() +
                saleHistoryReturnPaymentEditText.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        saleHistoryReturnSuButton1 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton2 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton3 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton4 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton5 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton6 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton7 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton8 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton9 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton0 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButton00 = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            saleHistoryReturnSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            saleHistoryReturnSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_number);
        } else {
            saleHistoryReturnSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButtonBack = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButtonBack.setText("");
            saleHistoryReturnSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_delete);
        } else {
            saleHistoryReturnSuButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        saleHistoryReturnSuButtonV = (Button)parentLn
                .findViewWithTag("saleHistoryReturnSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryReturnSuButtonV.setText("");
            saleHistoryReturnSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_returnpayment_enter);
        } else {
            saleHistoryReturnSuButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    saleHistoryReturnSuButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryReturnSuButton1.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton2.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton3.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton4.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton5.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton6.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton7.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton8.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton9.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton0.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButton00.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButtonBack.setOnClickListener(saleHistoryReturnBtnClickListener);
        saleHistoryReturnSuButtonV.setOnClickListener(saleHistoryReturnBtnClickListener);
        /***********************************************************************************************************/

        /** Balance, Tendered, Change, Cash, Card, Gift Card, Check, Point  EditText 객체 생성 및 리스너 연결 **********/
        saleHistoryReturnBalanceEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnBalanceEditTextTag");
        saleHistoryReturnBalanceEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnBalanceEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnTenderedEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnTenderedEditTextTag");
        saleHistoryReturnTenderedEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnTenderedEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        saleHistoryReturnChangeEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnChangeEditTextTag");
        saleHistoryReturnChangeEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnChangeEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnCashEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnCashEditTextTag");
        saleHistoryReturnCashEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnCashEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnCardEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnCardEditTextTag");
        saleHistoryReturnCardEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnCardEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnGiftCardEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnGiftCardEditTextTag");
        saleHistoryReturnGiftCardEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnGiftCardEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnCheckEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnCheckEditTextTag");
        saleHistoryReturnCheckEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnCheckEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnPointEditText = (TextView)parentLn
                .findViewWithTag("saleHistoryReturnPointEditTextTag");
        saleHistoryReturnPointEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + saleHistoryReturnPointEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        saleHistoryReturnBalanceEditText.setOnTouchListener(mEditTextTouch);

        saleHistoryReturnTenderedEditText.setOnTouchListener(mEditTextTouch);

        saleHistoryReturnChangeEditText.setOnTouchListener(mEditTextTouch);


        /**
        saleHistoryReturnCashEditText.setOnTouchListener(mEditTextTouch);
        saleHistoryReturnCashEditText.setOnClickListener(mEditTextClick);

        saleHistoryReturnCardEditText.setOnTouchListener(mEditTextTouch);
        saleHistoryReturnCardEditText.setOnClickListener(mEditTextClick);

        saleHistoryReturnGiftCardEditText.setOnTouchListener(mEditTextTouch);
        saleHistoryReturnGiftCardEditText.setOnClickListener(mEditTextClick);

        saleHistoryReturnCheckEditText.setOnTouchListener(mEditTextTouch);
        saleHistoryReturnCheckEditText.setOnClickListener(mEditTextClick);

        saleHistoryReturnPointEditText.setOnTouchListener(mEditTextTouch);
        saleHistoryReturnPointEditText.setOnClickListener(mEditTextClick);
         **/
        /***********************************************************************************************************/

        /** Employee ListView 객체 생성  *****************************************************************************/
        saleHistoryReturnEmployeeListView = (ListView)parentLn
                .findViewWithTag("saleHistoryReturnEmployeeListViewTag");
        /***********************************************************************************************************/

        /** Balance, Tendered, Change, Cash, Card, Gift Card, Check, Point  LinearLayout 객체 생성 및 리스너 연결 *****/
        saleHistoryReturnCashLinearLayout = (LinearLayout)parentLn
                .findViewWithTag("saleHistoryReturnCashLinearLayoutTag");
        saleHistoryReturnCardLinearLayout = (LinearLayout)parentLn
                .findViewWithTag("saleHistoryReturnCardLinearLayoutTag");
        saleHistoryReturnGiftCardLinearLayout = (LinearLayout)parentLn
                .findViewWithTag("saleHistoryReturnGiftCardLinearLayoutTag");
        saleHistoryReturnCheckLinearLayout = (LinearLayout)parentLn
                .findViewWithTag("saleHistoryReturnCheckLinearLayoutTag");
        saleHistoryReturnPointLinearLayout = (LinearLayout)parentLn
                .findViewWithTag("saleHistoryReturnPointLinearLayoutTag");

        if (mIsTotalCashUse == 0 && mUseTotalCashAmount > 0) {
            saleHistoryReturnCashLinearLayout.setOnTouchListener(mLinearLayoutTouch);
        }
        if (mIsTotalCardUse == 0 && mUseTotalCardAmount > 0) {
            saleHistoryReturnCardLinearLayout.setOnTouchListener(mLinearLayoutTouch);
        }
        if (mIsTotalGiftCardUse == 0 && mUseTotalGiftCardAmount > 0) {
            saleHistoryReturnGiftCardLinearLayout.setOnTouchListener(mLinearLayoutTouch);
        }
        if (mIsTotalCheckUse == 0 && mUseTotalCheckAmount > 0) {
            saleHistoryReturnCheckLinearLayout.setOnTouchListener(mLinearLayoutTouch);
        }
        if (mIsTotalPointUse == 0 && mUseTotalPointAmount > 0) {
            saleHistoryReturnPointLinearLayout.setOnTouchListener(mLinearLayoutTouch);
        }
        /***********************************************************************************************************/

        if (!GlobalMemberValues.isStrEmpty(mCheckCompany)) {
            saleHistoryReturnPaymentPaytypeSubTitleTextView4.setText(mCheckCompany);

            saleHistoryReturnCheckTitleTextView.setText(mCheckCompany);
            saleHistoryReturnCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
        }

        // EditText 최초 포커스를 Tendered 로 한다.
        // mFocustEditText = mFocustEditText;

        // 객체 및 색상 초기화
        setInit();

        // salon_sales_detail, salon_sales_return, salon_sales_card 를 바인딩해줄 리턴코드 생성
        mStrReturnCode = GlobalMemberValues.makeReturnCode();

        // Balance 및 Change 초기 셋팅
        setSaleHistoryReturnPrice();
        GlobalMemberValues.logWrite("saleHistoryReturnTest", "mTotalPayValue : " + mTotalPayValue + "\n");
        GlobalMemberValues.logWrite("saleHistoryReturnTest", "mBalanceToPay : " + mBalanceToPay + "\n");
    }


    Switch.OnCheckedChangeListener mSwitchCheckedChangeOnDisabled = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) buttonView.setChecked(false);
            GlobalMemberValues.displayDialog(context, "Information",
                    "You can change whether or not to use online credit cards\nin the settings.", "Close");
        }
    };

    Switch.OnCheckedChangeListener mSwitchCheckedChange = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (mCardUseYN == "Y" || mCardUseYN.equals("Y")) {
                    if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnCardEditText.getText().toString())) {
                        saleHistoryReturnCardEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                        setSaleHistoryReturnPrice();
                        String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                        GlobalMemberValues.logWrite("salehistoryreturnforcard", "mBalanceToPay : " + tempBalance + "\n");
                        setOpenSaleHistoryReturnCreditCardActivity(tempBalance);
                    }
                }
            } else {
                /**
                // salon_sales_card 테이블에 저장된 데이터가 있는지 체크해 보고
                // 저장된 데이터가 있으면 카드결제가 일어난 이후이기 때문에 on / off 변경을 못하게 한다.

                int tempSalonSalesCardCount = GlobalMemberValues.getIntAtString(
                        dbInit.dbExecuteReadReturnString(
                                "select count(idx) from salon_sales_card where salesCode = '" + mSalesCode + "' "));
                if (tempSalonSalesCardCount > 0) {
                    saleHistoryReturnOfflineSwitch.setChecked(true);
                    GlobalMemberValues.displayDialog(context, "Waraning", "You can't change on/off switch", "Close");
                }
                 **/
            }
        }
    };

    public void setOpenSaleHistoryReturnCreditCardActivity(String paramAmount) {
        String tempReturnSalesCode = "C" + mSalesCode.substring(1);
        String strThrowAmount = paramAmount;

        //GlobalMemberValues.logWrite("salehistoryreturnforcard", "return SalesCode : " + tempReturnSalesCode + "\n");
        //GlobalMemberValues.logWrite("salehistoryreturnforcard", "paramAmount : " + paramAmount + "\n");

        String strUsedCardAmount = saleHistoryReturnPaymentUseCardTextView.getText().toString();

        double dblThrowAmount = GlobalMemberValues.getDoubleAtString(strThrowAmount);
        double dblUsedCardAmount = GlobalMemberValues.getDoubleAtString(strUsedCardAmount);

        if (dblThrowAmount > dblUsedCardAmount) {
            GlobalMemberValues.displayDialog(context, "Waraning", "The amount to return is $" + dblThrowAmount + "\n"
                    + "There is more than the amount paid by credit card", "Close");
        } else {
            Intent saleHistoryReturnCreditCardIntent = new Intent(context.getApplicationContext(), SaleHistoryReturnCreditCard.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
            saleHistoryReturnCreditCardIntent.putExtra("returnSalesCode", tempReturnSalesCode);
            saleHistoryReturnCreditCardIntent.putExtra("returnCreditCardPopupAmount", strThrowAmount);
            // -------------------------------------------------------------------------------------
            insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
            mActivity.startActivity(saleHistoryReturnCreditCardIntent);
        }
    }

    View.OnClickListener saleHistoryReturnBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saleHistoryReturnCloseBtn : {
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);

                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.saleHistoryReturnSuButton1 : {
                    numberButtonClick(saleHistoryReturnSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton2 : {
                    numberButtonClick(saleHistoryReturnSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton3 : {
                    numberButtonClick(saleHistoryReturnSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton4 : {
                    numberButtonClick(saleHistoryReturnSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton5 : {
                    numberButtonClick(saleHistoryReturnSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton6 : {
                    numberButtonClick(saleHistoryReturnSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton7 : {
                    numberButtonClick(saleHistoryReturnSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton8 : {
                    numberButtonClick(saleHistoryReturnSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton9 : {
                    numberButtonClick(saleHistoryReturnSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton0 : {
                    numberButtonClick(saleHistoryReturnSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButton00 : {
                    numberButtonClick(saleHistoryReturnSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.saleHistoryReturnSuButtonBack : {
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
                    if (inputSu.isEmpty() || inputSu == null){
                        inputSu = "0.00";
                    }
                    mFocustEditText.setText(inputSu);

                    setSelectedLinearLayoutBGColor(1);

                    setSaleHistoryReturnPrice();

                    GlobalMemberValues.logWrite("saleHistoryReturn", "mTempPriceValue : " + mTempPriceValue + "\n");

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);

                    break;
                }
                case R.id.saleHistoryReturnSuButtonV : {
                    setReturnFinishProcess(0);
                    break;
                }
            }
        }
    };

    public static void setReturnFinishProcess(int paramFlag) {
        double tempReturnChangeAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnChangeEditText.getText().toString());
        double returnChangeAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnChangeAmount, "2"));
        if (mSelectedItemIdxArrayList == null && mSelectedItemIdxArrayList.size() == 0) {
            GlobalMemberValues.displayDialog(context, "Waraning", "There is no item to return", "Close");
            return;
        }
        if (returnChangeAmount < 0.0 || returnChangeAmount < 0) {
            GlobalMemberValues.displayDialog(context, "Waraning", "Tendered amount is not enough", "Close");
            return;
        }

        // ------------------------------------------------------------------------------------------------------------------------
        // paramFlag : 0    리턴을 진행할지 여부를 묻는다
        // paramFlag : 1    카드결제가 완료되었으므로 바로 리턴을 진행한다.
        if (paramFlag == 0) {
            new AlertDialog.Builder(context)
                    .setTitle("Item Delete")
                    .setMessage("Are you sure you want to return?")
                            //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        setReturnSalesItem();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    GlobalMemberValues.RECEIPTNOFORUPLOAD = "";
                                    // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
                                    GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        } else {
            try {
                setReturnSalesItem();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            GlobalMemberValues.RECEIPTNOFORUPLOAD = "";
            // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
            GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);

            //GlobalMemberValues.displayDialog(context, "Information", "Return processed", "Close");
        }
        // ------------------------------------------------------------------------------------------------------------------------
    }

    public static void setReturnSalesItem() throws JSONException {
//        setReturnSalesItem_sqllite();
        // 최상단 json객체
        jsonroot = new JSONObject();
        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp = null;

        // JSON ----------------------------------------------------------------------
        jsonroot.put("receiptno", mSalesCode);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
        // ---------------------------------------------------------------------------

        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        String strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = dbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }


        jsonroot.put("storename", storeNameForReceipt);  // JSON
        jsonroot.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot.put("storecity", storeCityForReceipt);  // JSON
        jsonroot.put("storestate", storeStateForReceipt);  // JSON
        jsonroot.put("storezip", storeZipForReceipt);  // JSON
        jsonroot.put("storephone", storePhoneForReceipt);  // JSON
        /******************************************************************/

        ResultSet salonSalesDetailCursor;
        String salonSalesDetailQuery;
        Vector<String> strInsertQueryVec = new Vector<String>();

        // Return 코드 생성
        String strReturnCode = mStrReturnCode;

        GlobalMemberValues.logWrite("salesReturnCode", strReturnCode + "\n");

        // Cash Return 금액
        double tempReturnCashAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCashEditText.getText().toString());
        double returnCashAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnCashAmount, "2"));
        // Card Return 금액
        double tempReturnCardAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCardEditText.getText().toString());
        double returnCardAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnCardAmount, "2"));
        // GiftCard Return 금액
        double tempReturnGiftCardAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnGiftCardEditText.getText().toString());
        double returnGiftCardAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnGiftCardAmount, "2"));
        // Check Return 금액
        double tempReturnCheckAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCheckEditText.getText().toString());
        double returnCheckAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnCheckAmount, "2"));
        // Point Return 금액
        double tempReturnPointAmount = GlobalMemberValues.getDoubleAtString(saleHistoryReturnPointEditText.getText().toString());
        double returnPointAmount = GlobalMemberValues.getDoubleAtString(
                GlobalMemberValues.getStringFormatNumber(tempReturnPointAmount, "2"));

        GlobalMemberValues.logWrite("ReturnInformation", "returnCashAmount1 : " + saleHistoryReturnCashEditText.getText().toString() + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnCardAmount1 : " + saleHistoryReturnCardEditText.getText().toString() + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnGiftCardAmount1 : " + saleHistoryReturnGiftCardEditText.getText().toString() + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnCheckAmount1 : " + saleHistoryReturnCheckEditText.getText().toString() + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnPointAmount1 : " + saleHistoryReturnPointEditText.getText().toString() + "\n");

        GlobalMemberValues.logWrite("ReturnInformation", "returnCashAmount2 : " + returnCashAmount + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnCardAmount2 : " + returnCardAmount + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnGiftCardAmount2 : " + returnGiftCardAmount + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnCheckAmount2 : " + returnCheckAmount + "\n");
        GlobalMemberValues.logWrite("ReturnInformation", "returnPointAmount2 : " + returnPointAmount + "\n");

        GlobalMemberValues.logWrite("ReturnInformation", "mCustomerId : " + mCustomerId + "\n");

        String item_employeeIdx = "";
        String item_employeeName = "";
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
            item_employeeIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            item_employeeName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
        }

        String salesCodeForReturn = "C" + mSalesCode.substring(1);

        /** salon_sales_detail 테이블 수정 및 리턴관련 새 데이터 등록 *************************************************************/
        double tempSalesAmount = 0.0;
        double sales_taxAmount = 0.0;
        double sales_totalAmount = 0.0;

        // 서비스(푸드) 리턴처리 ------------------------------------------------------------------------------------------------
        // 보이드리턴관련
        int itemDetailCount = 0;
        String itemDetailText = "";

        String employeeNamesForJSON = "";
        String tempCustomerName = "";

        String tempHoldcode = "";
        for (String salesDetailIdx : mSelectedItemIdxArrayList) {
            GlobalMemberValues.logWrite("salonSalesDetailIdx", "salon_sales_detail Idx : " + salesDetailIdx + "\n");
            salonSalesDetailQuery = "select salesCode, holdCode, reservationCode, sidx, stcode, categoryCode, categoryName, " +
                    " itemIdx, itemName, itemFileName, itemFilePath, servicePositionNo, qty, salesOrgPrice, salesPrice,  " +
                    " salesPriceAmount, salesBalPrice, salesBalPriceAmount, tax, taxAmount, totalAmount, commissionRatioType, " +
                    " commissionRatio, commission, commissionAmount, pointRatio, point, pointAmount, customerId, customerName,  " +
                    " customerPhone, customerPosCode, employeeIdx, employeeName, giftcardNumberToSave, giftcardSavePriceToSave,  " +
                    " couponNumber, eachDiscountExtraPrice, eachDiscountExtraType, saveType, isQuickSale, isSale, isCloudUpload, status, idx, " +
                    " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, dcextraforreturn " +
                    " from salon_sales_detail " +
                    " where idx = '" + salesDetailIdx + "' ";
            salonSalesDetailCursor = MssqlDatabase.getResultSetValue(salonSalesDetailQuery);
            try {
//                salonSalesDetailCursor.last();
//                int rowcount = salonSalesDetailCursor.getRow();
//                salonSalesDetailCursor.first();
                while (salonSalesDetailCursor.next()) {
                    strUpdSqlQuery_item = "update salon_sales set " +
                            " totalDiscountPrice = totalDiscountPrice - " +
                            (GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,51), 1)) * -1) + " " +
                            " where salescode = '" + mSalesCode + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery_item);


                    strUpdSqlQuery_item = "update salon_sales_detail set status = 3 where idx = '" + salesDetailIdx + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery_item);

                    /** Void 관련하여 salon_sales_detail 테이블 입력 *******************************************************************/
                    String parentSalesIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,44), 1);
                    // 클라우드 업로드 여부
                    int isCloudUpload = 0;
                    // 상태 (1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소)
                    int item_status = 3;
                    strInsSqlQuery_item = "insert into salon_sales_detail (" +
                            " salesCode, holdCode, reservationCode, sidx, stcode, " +
                            " categoryCode, categoryName, " +
                            " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
                            " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
                            " salesBalPrice, salesBalPriceAmount, " +
                            " tax, taxAmount, " +
                            " totalAmount, " +
                            " commissionRatioType, commissionRatio, commission, commissionAmount, " +
                            " pointRatio, point, pointAmount, " +
                            " customerId, customerName, customerPhone, customerPosCode, " +
                            " employeeIdx, employeeName, " +
                            " giftcardNumberToSave, giftcardSavePriceToSave, " +
                            " couponNumber, " +
                            " eachDiscountExtraPrice, eachDiscountExtraType, " +
                            " saveType, isQuickSale, isSale, isCloudUpload, status, parentSalesCode, parentSalesIdx, " +
                            " returnCode, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, dcextraforreturn, frommssql " +
                            " ) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(salesCodeForReturn, 0)+ "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,2), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,3), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,4), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,5), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,9), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,10), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,11), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,13) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,14) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,15) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,18) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,19) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,20) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,21), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,22) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,23) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,24) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,25) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,26) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,27) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,28), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,30), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,31), 0) + "', " +
//                        " '" + GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(32), 0) + "', " +
//                        " '" + GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(33), 0) + "', " +
                            " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,34), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,35) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,36), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,37), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,38), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,40), 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,41), 0) + "', " +
                            " '" + isCloudUpload + "', " +
                            " '" + item_status + "', " +
                            " '" + mSalesCode + "', " +
                            " '" + parentSalesIdx + "', " +
                            " '" + strReturnCode + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,45), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,46) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,47), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,48) + "', ") +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,49), 0) + "', " +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,50) + "', ") +
                            GlobalMemberValues.setChangeDoubleMinus(" '-" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,51) + "', ") +
                            " 'Y' " +
                            ")";
                    // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
                    strInsertQueryVec.addElement(strInsSqlQuery_item);
                    /*******************************************************************************************************************/

                    /** 구매한 상품(Product)이 있으면 Return 처리한 수량만큼 재고(onhand)에 추가한다 ******************************************/
                    if (GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39) == "1" || GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39).equals("1")) {
                        strInsSqlQuery_item = " update salon_storeproduct set onhand = onhand + " + GlobalMemberValues.getIntAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12)) +
                                " where idx = '" + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7) + "' ";
                        strInsertQueryVec.addElement(strInsSqlQuery_item);
                    }
                    /*******************************************************************************************************************/

                    /** 적립된 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 차감한다 **************************************/
                    double tempSavedPointAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,27));
                    GlobalMemberValues.logWrite("ReturnInformation", "상품별 적립포인트 : " + tempSavedPointAmount + "\n");
                    if (!GlobalMemberValues.isStrEmpty(mCustomerId) && tempSavedPointAmount > 0) {
                        strUpdSqlQuery = "update salon_member set mileage = mileage - " + tempSavedPointAmount +
                                " where uid = '" + mCustomerId + "' ";
                        strInsertQueryVec.addElement(strUpdSqlQuery);

                        strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                                " ) values ( " +
                                " '" + "Return Minus by Sales - [" + mSalesCode + "] " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8), 1) + "', " +
                                " '" + tempSavedPointAmount + "', " +
                                " '" + mCustomerId + "', " +
                                " '" + "2" + "', " +            // 1: 적립        2 : 사용
                                " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                                //" '" + item_employeeName + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                                " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                                " ) ";
                        strInsertQueryVec.addElement(strInsSqlQuery);
                    }
                    /*******************************************************************************************************************/

                    // JSON 객체 저장용 직원이름 ------------------------
                    if (!GlobalMemberValues.isStrEmpty(employeeNamesForJSON)) {
                        if (employeeNamesForJSON.indexOf(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,33)) == -1) {
                            employeeNamesForJSON = employeeNamesForJSON  + ", " + GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,33);
                        }
                    } else {
                        employeeNamesForJSON = GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,33);
                    }
                    // ------------------------------------------------

                    jsontmp = new JSONObject();
                    jsontmp.put("itemname", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8));
                    jsontmp.put("itemqty", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12));
                    jsontmp.put("itemprice", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16));
                    jsontmp.put("itemamount", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17));

                    jsontmp.put("optiontxt", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,45));
                    jsontmp.put("optionprice", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,46));
                    jsontmp.put("additionaltxt1", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,47));
                    jsontmp.put("additionalprice1", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,48));
                    jsontmp.put("additionaltxt2", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,49));
                    jsontmp.put("additionalprice2", GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,50));

                    jsonList.put(jsontmp);

                    // 보이드리턴관련 --------------------------------------------------------------------------------------------------
                    String temp_itemIdx = GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,7);
                    GlobalMemberValues.logWrite("returnkitchenlog", "temp_itemIdx : " + temp_itemIdx + "\n");

                    // 주방프린팅 여부와 주방프린터 번호 구하기
                    String tempKitchenPrinterNumber = "0";
                    tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);


                    // 08102023
                    // alt name 구하기
                    String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                    );
                    if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                        temp_itemName_alt = "";
                    }

                    String item_itemname_optionadd = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,8), 1) +
                            "-ANNIETTASU-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,45), 1) +
                            "-ANNIETTASU-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,47), 1) +
                            "-ANNIETTASU-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,49), 1) +
                            "-ANNIETTASU-" + temp_itemIdx +
                            "-ANNIETTASU-" + tempKitchenPrinterNumber +
                            "-ANNIETTASU-" + "" +

                            // 08102023
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +       // ALL 또는 EACH
                            "-ANNIETTASU-" + "" +          // DC 또는 EX
                            "-ANNIETTASU-" + "" +          // $ 또는 %
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +
                            "-ANNIETTASU-" + "" +          // additem
                            "-ANNIETTASU-" + "" +        // 라벨 프린트 했는지 YN

                            // 08102023
                            "-ANNIETTASU-" + temp_itemName_alt;


                    String item_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,39), 1);

                    String temp_kitchenPrintYN = dbInit.dbExecuteReadReturnString(
                            "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                    if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                        temp_kitchenPrintYN = "N";
                    }

                    String temp_kitchenprintnum = "0";
                    temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                    GlobalMemberValues.logWrite("returnkitchenlog", "item_saveType : " + item_saveType + "\n");
                    GlobalMemberValues.logWrite("returnkitchenlog", "temp_kitchenPrintYN : " + temp_kitchenPrintYN + "\n");
                    GlobalMemberValues.logWrite("returnkitchenlog", "item_saveType : " + item_saveType + "\n");

                    // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                    if (item_saveType == "0" || item_saveType.equals("0")) {
                        if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y")) {
                            String tempStr = item_itemname_optionadd +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,12), 1) +
                                    "-JJJ-" + temp_kitchenprintnum +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,6), 1) +
                                    "-JJJ-" + "" +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,16), 1) +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,18), 1) +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,46), 1) +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,48), 1) +
                                    "-JJJ-" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,50), 1);
                            if (itemDetailCount == 0) {
                                itemDetailText = tempStr;
                            } else {
                                itemDetailText = itemDetailText + "-WANHAYE-" + tempStr;
                            }
                            itemDetailCount++;
                        }
                    }
                    // --------------------------------------------------------------------------------------------------------------------

                    tempSalesAmount = tempSalesAmount + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,17));
                    sales_taxAmount = sales_taxAmount + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,19));
                    sales_totalAmount = sales_totalAmount + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,20));

                    tempCustomerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,29), 1);
                }
//                if (rowcount > 0) {
//
//                }

                String dbHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesDetailCursor,1), 0);
                if (!GlobalMemberValues.isStrEmpty(dbHoldCode)) {
                    tempHoldcode = dbHoldCode;
                }

            }catch (Exception e){

            }

        }

        // 보이드리턴관련
        // 키친프린팅 데이터 가져오기
        if (GlobalMemberValues.isVoidReturnKitchenPrinting()) {
            JSONObject tempKitchenJson = null;
            String tempJsonStr_kitchen = "";
            String tempKitchenQuery = "";
            tempKitchenQuery = " select jsonstr from salon_sales_kitchenprintingdata_json where salesCode = '" + mSalesCode + "' ";
            tempJsonStr_kitchen = MssqlDatabase.getResultSetValueToString(tempKitchenQuery);
            if (!GlobalMemberValues.isStrEmpty(tempJsonStr_kitchen)) {
                tempJsonStr_kitchen = GlobalMemberValues.getReplaceText(tempJsonStr_kitchen, "\"receiptno\":\"K", "\"receiptno\":\"C");
                tempKitchenJson = new JSONObject(tempJsonStr_kitchen);



                if (tempKitchenJson != null) {
                    JSONObject newTempJson = new JSONObject();

                    String str_saledate = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "saledate");
                    String str_saletime = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "saletime");
                    String str_receiptno = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "receiptno");
                    //String str_saleitemlist = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "saleitemlist");
                    String str_customername = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customername");
                    String str_customerphonenum = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customerphonenum");
                    String str_customeraddress = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customeraddress");
                    String str_deliverytakeaway = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "deliverytakeaway");
                    String str_deliverydate = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "deliverydate");
                    String str_ordertype = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "ordertype");
                    String str_customermemo = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customermemo");
                    // Restaurant 관련
                    String str_restaurant_tablename = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "restaurant_tablename");
                    String str_restaurant_tablepeoplecnt = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "restaurant_tablepeoplecnt");

                    String str_receiptfooter = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "receiptfooter");
                    String str_customerOrderNumber = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customerordernumber");
                    String str_customerPagerNumber = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customerpagernumber");
                    String str_customerPagerHeader = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "customerpagerheader");
                    String str_phoneOrder = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "phoneorder");
                    String str_phoneOrderNumber = GlobalMemberValues.getDataInJsonData(tempKitchenJson, "phoneordernumber");
                    String str_reprint_CustomerPhoneNum = GlobalMemberValues.getDataInJsonData(tempKitchenJson,"customerphonenum");


                    newTempJson.put("saledate", str_saledate);
                    newTempJson.put("saletime", str_saletime);
                    newTempJson.put("receiptno", str_receiptno);
                    newTempJson.put("customername", str_customername);
                    newTempJson.put("customerphonenum", str_customerphonenum);
                    newTempJson.put("customeraddress", str_customeraddress);
                    newTempJson.put("deliverytakeaway", str_deliverytakeaway);
                    newTempJson.put("deliverydate", str_deliverydate);
                    newTempJson.put("ordertype", str_ordertype);
                    newTempJson.put("customermemo", str_customermemo);
                    newTempJson.put("restaurant_tablename", str_restaurant_tablename);
                    newTempJson.put("restaurant_tablepeoplecnt", str_restaurant_tablepeoplecnt);

                    newTempJson.put("receiptfooter", str_receiptfooter);
                    newTempJson.put("customerordernumber", str_customerOrderNumber);
                    newTempJson.put("customerpagernumber", str_customerPagerNumber);
                    newTempJson.put("phoneorder", str_phoneOrder);
                    newTempJson.put("phoneordernumber", str_phoneOrderNumber);
                    newTempJson.put("customerphonenum", str_reprint_CustomerPhoneNum);

                    newTempJson.put("customerpagerheader", str_customerPagerHeader);

                    GlobalMemberValues.logWrite("returnkitchenlog", "itemDetailText : " + itemDetailText + "\n");

                    // 아이템 리스트 수정
                    newTempJson.put("saleitemlist", itemDetailText);


                    // 01132023 ------------------------------------------------------------------------------------------
                    newTempJson.put("holdcode", tempHoldcode);
                    // ---------------------------------------------------------------------------------------------------


                    if (GlobalMemberValues.mssql_useyn == "Y" || GlobalMemberValues.mssql_useyn.equals("Y")) {
                        strInsSqlQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                " (salesCode, scode, sidx, stcode, jsonstr) values ( " +
                                " '" + salesCodeForReturn + "', " +
                                " '" + GlobalMemberValues.SALON_CODE + "', " +
                                "  " + GlobalMemberValues.STORE_INDEX + ", " +
                                " '" + GlobalMemberValues.STATION_CODE + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(newTempJson.toString(), 0) + "' " +
                                " ) ";
                        strInsertQueryVec.addElement(strInsSqlQuery);
                    }
                }
            }
        }

        // ---------------------------------------------------------------------------------------------------------------------

        // 팁 Tip 리턴처리 ------------------------------------------------------------------------------------------------------
        for (String salesTipIdx : mSelectedTipIdxArrayList) {
            strDelSqlQuery = "delete from salon_sales_tip where idx = '" + salesTipIdx + "' ";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        // ---------------------------------------------------------------------------------------------------------------------
        /***************************************************************************************************************************/

        // JSON (리턴된 팁 Tip ) -----------------------------------------------------
        jsonroot.put("returntipamount", (mTotalTipAmount + ""));
        // ---------------------------------------------------------------------------

        // JSON (리턴된 Pickup or Delivery Fee ) -------------------------------------
        jsonroot.put("returnpickupdeliveryfee", (mPickupDeliveryFee + ""));
        // ---------------------------------------------------------------------------

        // JSON (구매 서비스 리스트) ---------------------------------------------------
        jsonroot.put("saleitemlist", jsonList);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("employeename", employeeNamesForJSON);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("subtotal", GlobalMemberValues.getStringFormatNumber(tempSalesAmount, "2"));
        jsonroot.put("tax", GlobalMemberValues.getStringFormatNumber(sales_taxAmount, "2"));
        jsonroot.put("totalamount", GlobalMemberValues.getStringFormatNumber(sales_totalAmount, "2"));
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        if (GlobalMemberValues.isStrEmpty(tempCustomerName)) {
            tempCustomerName = "Walk In";
        }
        jsonroot.put("customername", tempCustomerName);
        // ---------------------------------------------------------------------------

        /** 사용한 포인트 또는 기프트 카드로 리턴(Return) 해준 금액이 있는 경우 재적립 *********************************************/
        // 기프트카드로 리턴해 주는 경우 --------------------------------------------------------------------------------------
        if (returnGiftCardAmount > 0 || returnGiftCardAmount > 0.0) {
            ResultSet salonSalesCursor;
            String salonSalesQuery = "select customerId, customerName, giftcardNumberUsed from salon_sales where salesCode = '" + mSalesCode + "' ";
            salonSalesCursor = MssqlDatabase.getResultSetValue(salonSalesQuery);
            try {
//                salonSalesCursor.last();
//                int rowcount = salonSalesCursor.getRow();
//                salonSalesCursor.first();
                while (salonSalesCursor.next()){
                    String sales_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,0), 1);
                    String sales_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,1), 1);
                    String sales_giftcardNumberUsed = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor,2), 1);
                    String sales_giftcardPriceUsed = GlobalMemberValues.getStringFormatNumber(returnGiftCardAmount, "2");

                    // salon_storegiftcard_number 테이블 수정
                    strUpdSqlQuery = "update salon_storegiftcard_number set " +
                            " remainingPrice = remainingPrice + " + GlobalMemberValues.getDoubleAtString(sales_giftcardPriceUsed) + " " +
                            " where gcNumber = '" + sales_giftcardNumberUsed + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery);

                    // salon_storegiftcard_number_history 테이블 저장
                    strInsSqlQuery = "insert into salon_storegiftcard_number_history (" +
                            " sidx, gcNumber, saleItemIdx, empIdx, empName, customerId, customerName, addUsePrice, addUseType, addUseMemo, salesCode, codeforupload " +
                            " ) values ( " +
                            " '" + GlobalMemberValues.STORE_INDEX + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                            " '" + "" + "', " +
//                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
//                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                            " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +
                            " '" + "A" + "', " +         // 사용된 기프트카드의 void 처리로 재적립이므로 적립인 'A' 로 저장 (사용처리시 'U')
                            " '" + "RETURN" + "', " +
                            " '" + mSalesCode + "', " +   // 세일즈코드
                            " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                            ")";
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }
//                if (rowcount > 0) {
//
//                }
            } catch (Exception e){

            }
        }
        // --------------------------------------------------------------------------------------------------------------------
        // 고객이 사용한 포인트로 리턴해 주는 경우 ----------------------------------------------------------------------------
        if (returnPointAmount > 0 || returnPointAmount > 0.0) {
            ResultSet salonSalesCursorForMileage;
            String salonSalesQueryForMileage = "select customerId, customerName from salon_sales where salesCode = '" + mSalesCode + "' ";
            salonSalesCursorForMileage = MssqlDatabase.getResultSetValue(salonSalesQueryForMileage);
            try {
//                salonSalesCursorForMileage.last();
//                int rowcount = salonSalesCursorForMileage.getRow();
//                salonSalesCursorForMileage.first();
                while (salonSalesCursorForMileage.next()){
                    double tempUsedPointAmount = returnPointAmount;
                    String tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursorForMileage,0), 1);

                    // 사용한 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 다시 해당금액만큼 더한다. -------------------------
                    if (!GlobalMemberValues.isStrEmpty(tempCustomerId) && tempUsedPointAmount > 0) {
                        strUpdSqlQuery = "update salon_member set mileage = mileage + " + tempUsedPointAmount +
                                " where uid = '" + tempCustomerId + "' ";
                        strInsertQueryVec.addElement(strUpdSqlQuery);

                        strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                                " ) values ( " +
                                " '" + "Return Restored by Sales - " + mSalesCode + "', " +
                                " '" + tempUsedPointAmount + "', " +
                                " '" + tempCustomerId + "', " +
                                " '" + "1" + "', " +            // 1: 적립        2 : 사용
                                " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                                //" '" + item_employeeName + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                                " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                                " ) ";
                        strInsertQueryVec.addElement(strInsSqlQuery);
                    }
                }
//                if (rowcount > 0 ) {
//
//                    // --------------------------------------------------------------------------------------------------------------------
//                }
            } catch (Exception e) {

            }
        }
        // --------------------------------------------------------------------------------------------------------------------
        // salon_sales 테이블의 결제수단별 사용금액 수정 ----------------------------------------------------------------------


        strUpdSqlQuery = "update salon_sales set " +
                " useTotalCashAmountAfterReturned = useTotalCashAmountAfterReturned - " + returnCashAmount + ", " +
                " useTotalCardAmountAfterReturned = useTotalCardAmountAfterReturned - " + returnCardAmount + ", " +
                " useTotalGiftCardAmountAfterReturned = useTotalGiftCardAmountAfterReturned - " + returnGiftCardAmount + ", " +
                " useTotalCheckAmountAfterReturned = useTotalCheckAmountAfterReturned - " + returnCheckAmount + ", " +
                " useTotalPointAmountAfterReturned = useTotalPointAmountAfterReturned - " + returnPointAmount;
        if (mPickupDeliveryFee > 0) {
            strUpdSqlQuery += ", deliverypickupfee = deliverypickupfee - " + mPickupDeliveryFee + "";
            strUpdSqlQuery += ", returneddeliverypickupfee = " + (mPickupDeliveryFee * -1) + "";
        }
        if (mTotalTipAmount > 0) {
            strUpdSqlQuery += ", returnedtip = " + (mTotalTipAmount * -1) + "";
        }
        strUpdSqlQuery += " where salesCode = '" + mSalesCode + "' ";
        GlobalMemberValues.logWrite("updateslaesdblog", "strUpdSqlQuery : " + strUpdSqlQuery + "\n");
        strInsertQueryVec.addElement(strUpdSqlQuery);
        // --------------------------------------------------------------------------------------------------------------------

        // 세일한 직원과 주문한 직원이 다를 경우 salon_sales_return_byemplyee 에 저장 -----------------------------------------------
        String nowEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "J";
        String saleEmpIdx = MssqlDatabase.getResultSetValueToString(
                " select employeeidx from salon_sales where salescode = '" + mSalesCode + "' ") + "J";

        if (!nowEmpIdx.equals(saleEmpIdx)) {
            strUpdSqlQuery = " insert into salon_sales_return_byemplyee (salesCode, sidx, stcode, " +
                    " realreturnprice_cash, realreturnprice_card, realreturnprice_gc, realreturnprice_check, realreturnprice_point, " +
                    " employeeIdx, employeeName) values ( " +
                    " '" + salesCodeForReturn + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + returnCashAmount + "', " +
                    " '" + returnCardAmount + "', " +
                    " '" + returnGiftCardAmount + "', " +
                    " '" + returnCheckAmount + "', " +
                    " '" + returnPointAmount + "', " +
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strUpdSqlQuery);
        }
        // --------------------------------------------------------------------------------------------------------------------

        // salon_sales_detail_return 테이블의 결제수단별 금액 넣기----------------------------------------------------------------------
        // 전체 리턴 금액
        double tempTotalReturnAmount = returnCashAmount + returnCardAmount + returnGiftCardAmount + returnCheckAmount + returnPointAmount;

        double useTotalCashRatio = 0.0;
        double useTotalCardRatio = 0.0;
        double useTotalGiftCardRatio = 0.0;
        double useTotalCheckRatio = 0.0;
        double useTotalPointRatio = 0.0;

        // 사용할 결제수단
        String mIsPaymentTypeCash       = "1";
        String mIsPaymentTypeCard       = "1";
        String mIsPaymentTypeGiftCard   = "1";
        String mIsPaymentTypeCheck      = "1";
        String mIsPaymentTypePoint      = "1";

        // Cash
        if (returnCashAmount > 0) {
            mIsPaymentTypeCash = "0";
            useTotalCashRatio = returnCashAmount / tempTotalReturnAmount;
        } else {
            mIsPaymentTypeCash = "1";
        }
        // Card
        if (returnCardAmount > 0) {
            mIsPaymentTypeCard = "0";
            useTotalCardRatio = returnCardAmount / tempTotalReturnAmount;
        } else {
            mIsPaymentTypeCard = "1";
        }
        // GiftCard
        if (returnGiftCardAmount > 0) {
            mIsPaymentTypeGiftCard = "0";
            useTotalGiftCardRatio = returnGiftCardAmount / tempTotalReturnAmount;
        } else {
            mIsPaymentTypeGiftCard = "1";
        }
        // Check
        if (returnCheckAmount > 0) {
            mIsPaymentTypeCheck = "0";
            useTotalCheckRatio = returnCheckAmount / tempTotalReturnAmount;
        } else {
            mIsPaymentTypeCheck = "1";
        }
        // Point
        if (returnPointAmount > 0) {
            mIsPaymentTypePoint = "0";
            useTotalPointRatio = returnPointAmount / tempTotalReturnAmount;
        } else {
            mIsPaymentTypePoint = "1";
        }

        strUpdSqlQuery = "insert into salon_sales_return (" +
                " salesCode, returnCode, sidx, stcode, " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio " +
                ") values (" +

                " '" + salesCodeForReturn + "', " +
                " '" + strReturnCode + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +

                " '" + mIsPaymentTypeCash + "', " +
                " '" + mIsPaymentTypeCard + "', " +
                " '" + mIsPaymentTypeGiftCard + "', " +
                " '" + mIsPaymentTypeCheck + "', " +
                " '" + mIsPaymentTypePoint + "', " +

                " '" + returnCashAmount + "', " +
                " '" + returnCardAmount + "', " +
                " '" + returnGiftCardAmount + "', " +
                " '" + returnCheckAmount + "', " +
                " '" + returnPointAmount + "', " +

                " '" + useTotalCashRatio + "', " +
                " '" + useTotalCardRatio + "', " +
                " '" + useTotalGiftCardRatio + "', " +
                " '" + useTotalCheckRatio + "', " +
                " '" + useTotalPointRatio + "' " +

                ")";
        strInsertQueryVec.addElement(strUpdSqlQuery);
        // --------------------------------------------------------------------------------------------------------------------

        // Cash 결제가 있을 경우
        if (returnCashAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("cashtendered", GlobalMemberValues.getStringFormatNumber(returnCashAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("cashtendered", "0");
            // ---------------------------------------------------------------------------
        }

        // 카드결제가 있을 경우
        if (returnCardAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("creditcardtendered", GlobalMemberValues.getStringFormatNumber(returnCardAmount, "2"));
            // ---------------------------------------------------------------------------

            // JSON (결제한 카드정보 리스트) ---------------------------------------------------
            jsonroot.put("creditcardtransaction", mJsonList);
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("creditcardtendered", "0");
            // ---------------------------------------------------------------------------
        }
        //GlobalMemberValues.logWrite("creditcardamount", "card amount : " + tempCardAmount + "\n");

        // 기프트카드 결제가 있을 경우
        if (returnGiftCardAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("giftcardtendered", GlobalMemberValues.getStringFormatNumber(returnGiftCardAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("giftcardtendered", "0");
            // ---------------------------------------------------------------------------
        }

        // Check 결제가 있을 경우
        if (returnCheckAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("checktendered", GlobalMemberValues.getStringFormatNumber(returnCheckAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("checktendered", "0");
            // ---------------------------------------------------------------------------
        }

        // Point 결제가 있을 경우
        if (returnPointAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("pointtendered", GlobalMemberValues.getStringFormatNumber(returnPointAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("pointtendered", "0");
            // ---------------------------------------------------------------------------
        }

        // 오프라인 카드 결제가 있으면 salon_sales_card 에서 Return 처리한다. ---------------------------------------------------------------------
        ResultSet salonSalesCardCursor;
        String salonSalesCardQuery = "select priceAmount from salon_sales_card " +
                " where salesCode = '" + mSalesCode + "' " +
                " and cardCom = 'OFFLINECARD' ";
        salonSalesCardCursor = MssqlDatabase.getResultSetValue(salonSalesCardQuery);
        try {
//            salonSalesCardCursor.last();
//            int rowcount = salonSalesCardCursor.getRow();
//            salonSalesCardCursor.first();
            while (salonSalesCardCursor.next()){
                String salonSalesCard_priceAmountStr = "";
                if (returnCardAmount > 0) {
                    salonSalesCard_priceAmountStr = "-" + returnCardAmount;
                } else {
                    salonSalesCard_priceAmountStr = "0.00";
                }

                // 원래 sale 의 직원 idx, name 가져오기
                String tempEmpIdx = MssqlDatabase.getResultSetValueToString(
                        " select employeeIdx from salon_sales_card where salesCode = 'K" + mSalesCode.substring(1) + "' "
                );
                String tempEmpName = MssqlDatabase.getResultSetValueToString(
                        " select employeeName from salon_sales_card where salesCode = 'K" + mSalesCode.substring(1) + "' "
                );

                strInsSqlQuery = "insert into salon_sales_card (" +
                        " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, returnCode, employeeIdx, employeeName " +
                        " ) values ( " +
                        "'" + salesCodeForReturn + "', " +
                        "'" + "" + "', " +
                        "'" + GlobalMemberValues.STORE_INDEX + "', " +
                        "'" + GlobalMemberValues.STATION_CODE + "', " +
                        "'" + "OFFLINECARD" + "', " +
                        "'" + salonSalesCard_priceAmountStr + "', " +
                        "'" + "" + "', " +
                        "'" + "3" + "', " +
                        "'" + mStrReturnCode + "', " +
//                    "'" + tempEmpIdx + "', " +
//                    "'" + tempEmpName + "' " +
                        " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
//            if (rowcount > 0) {
//
//            }
        } catch (Exception e) {

        }

        // --------------------------------------------------------------------------------------------------------------------------------------
        /***************************************************************************************************************************/

        for (String sql : strInsertQueryVec) {
            GlobalMemberValues.logWrite("SalesReturnQueryGoGoGo", sql + "\n");
        }

        // JSON ----------------------------------------------------------------------
        // 프린트 타입   (1 : customer            2 : merchant                3 : 둘다)
        jsonroot.put("receiptprinttype", "3");
        // ---------------------------------------------------------------------------

        // 영수증 프린트 하기 ------------------------------------------------------------------
        // Void 처리시 프린트 여부
        String returnprint = MssqlDatabase.getResultSetValueToString(
                " select returnprint from salon_storestationsettings_deviceprinter "
        );
        GlobalMemberValues.logWrite("VoidReturnPrint", "returnprint : " + returnprint + "\n");
        if (returnprint.equals("0") || returnprint == "0") {
            Context prmContext;
            if (GlobalMemberValues.isEloPrinterReceipt()) {
                prmContext = SaleHistory.context;
            } else {
                prmContext = context;
            }

            // giftCardBalanceInfoDownloadHandler 쪽으로 이동.
//            GlobalMemberValues.printReceiptByJHP(jsonroot, prmContext, "return");

//            CommandButton.openCashDrawer();

            String str_cashtendered = "";
            str_cashtendered = jsonroot.optString("cashtendered","0.0");
            double d_str_cashtendered = GlobalMemberValues.getDoubleAtString(str_cashtendered);
            if (!GlobalMemberValues.isStrEmpty(d_str_cashtendered + "") && d_str_cashtendered > 0) {
                CommandButton.openCashDrawer();
            }

        }
        // -----------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("returnJsonResult", "여기에.." + "\n");
        GlobalMemberValues.logWrite("returnJsonResult", "결과물 : " + jsonroot + "\n");

        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
//        returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우 Sale History 리스트 리로드
            // 클라우드 키친 프린팅일 경우 ----------------------------------------------------------------------------------------------
            if (GlobalMemberValues.isCloudKitchenPrinter()) {
                // 주방프린터 데이터 클라우드 전송
                GlobalMemberValues.sendKitchenPrintingDataToCloud(MainActivity.mContext, mActivity);
            }
            // ---------------------------------------------------------------------------------------------------------------------

            // 정상처리일경우 - LogSave
            LogsSave.saveLogsInDB(271,mSalesCode);

            // 0624
            String getUsedGiftCardNum = "";
            String getUsedpointName = "";
            String strselSqlQuery_giftcardNum = "select giftcardNumberUsed from salon_sales where salesCode = '" + mSalesCode + "'";
            getUsedGiftCardNum = dbInit.dbExecuteReadReturnString(strselSqlQuery_giftcardNum);
            GlobalMemberValues.GIFTCARD_USE_NUMBER = getUsedGiftCardNum;

            String strselSqlQuery_point = "select customerId from salon_sales where salesCode = '" + mSalesCode + "'";
            getUsedpointName = dbInit.dbExecuteReadReturnString(strselSqlQuery_point);

            String strselSqlQuery_point_earned = "select pointAmount from salon_sales where salesCode = '" + mSalesCode + "'";
            String getUsedpoint_earned = dbInit.dbExecuteReadReturnString(strselSqlQuery_point_earned);
            if (!getUsedpoint_earned.isEmpty() || !getUsedpoint_earned.equals("0.0")) {
                GlobalMemberValues.POINT_EARNED = GlobalMemberValues.getDoubleAtString(getUsedpoint_earned);
            }

            if (!getUsedGiftCardNum.isEmpty()){


                final String finalTempGcNumber = getUsedGiftCardNum;
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

            } else if (!getUsedpointName.isEmpty() || !getUsedpointName.equals("Walk In")){
                String finalGetUsedpointName = getUsedpointName;
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                            if (!GlobalMemberValues.isOnline().equals("00")) {
                                GlobalMemberValues.showDialogNoInternet(context);
                                mGetMemberMileage = "";
                            } else {
                                API_download_membermileage apiMemMileageDownload = new API_download_membermileage(finalGetUsedpointName);
                                apiMemMileageDownload.execute(null, null, null);
                                try {
                                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                                    if (apiMemMileageDownload.mFlag) {
                                        mGetMemberMileage = apiMemMileageDownload.mReturnValue;
                                    }
                                } catch (InterruptedException e) {
                                    mGetMemberMileage = "";
                                }
                            }
                        }
                        GlobalMemberValues.POINT_USED_CUSTOMER_NAME = finalGetUsedpointName;
                        // ---------------------------------------------------------------------------------
                        // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                        memMileageDownloadHandler.sendEmptyMessage(0);
                        // ---------------------------------------------------------------------------------
                    }
                });
                thread.start();
            } else {
                // 210107 추가
                GlobalMemberValues.printReceiptByJHP(jsonroot, context, "return");
            }

            // 0624

            // 초기화
            setInit();
            // 키패드(키보드) 감추기
            GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
            SaleHistoryReturnItemList.mActivity.finish();
            //SaleHistoryReturnItemList.mActivity.recreate();

            if (GlobalMemberValues.isEloPrinterReceipt()) {
                //
            } else {
                SaleHistory.mActivity.recreate();
            }

            mActivity.finish();
            //setSearchSalesHistory();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }
    }

    public static void setSaleHistoryReturnPrice() {
        double saleHistoryReturnEvValueCash       = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCashEditText.getText().toString());
        double saleHistoryReturnEvValueCard       = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCardEditText.getText().toString());
        double saleHistoryReturnEvValueGiftCard   = GlobalMemberValues.getDoubleAtString(saleHistoryReturnGiftCardEditText.getText().toString());
        double saleHistoryReturnEvValueCheck      = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCheckEditText.getText().toString());
        double saleHistoryReturnEvValuePoint      = GlobalMemberValues.getDoubleAtString(saleHistoryReturnPointEditText.getText().toString());

        String tempTotalValue = GlobalMemberValues.getStringFormatNumber(mTotalAmount, "2");
        double totalBalance = GlobalMemberValues.getDoubleAtString(tempTotalValue);
        double saleHistoryReturnEvTendered = GlobalMemberValues.getDoubleAtString(saleHistoryReturnTenderedEditText.getText().toString());
        double saleHistoryReturnEvChange = GlobalMemberValues.getDoubleAtString(saleHistoryReturnChangeEditText.getText().toString());

        saleHistoryReturnEvTendered = saleHistoryReturnEvValueCash + saleHistoryReturnEvValueCard + saleHistoryReturnEvValueGiftCard + saleHistoryReturnEvValueCheck + saleHistoryReturnEvValuePoint;
        saleHistoryReturnEvChange = (saleHistoryReturnEvTendered - totalBalance);

        saleHistoryReturnBalanceEditText.setText(GlobalMemberValues.getStringFormatNumber(totalBalance, "2"));
        saleHistoryReturnTenderedEditText.setText(GlobalMemberValues.getStringFormatNumber(saleHistoryReturnEvTendered, "2"));
        saleHistoryReturnChangeEditText.setText(GlobalMemberValues.getStringFormatNumber(saleHistoryReturnEvChange, "2"));

        // mBalanceToPay 셋팅
        mBalanceToPay = saleHistoryReturnEvChange;

        // mTotalPayValue 셋팅
        mTotalPayValue = totalBalance;
    }

    private void numberButtonClick(Button btn) {
        if (mFocustEditText == null) {
            GlobalMemberValues.displayDialog(context, "Warning", "Choose Return Payment Type", "Close");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mTempPriceValue.length() < 12) {
            sb.append(mTempPriceValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            mTempPriceValue = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * 0.01), "2");

            double tempUsedAmount = 0.0;
            switch (mFocustEditText.getId()) {
                case R.id.saleHistoryReturnCashEditText : {
                    tempUsedAmount = mUseTotalCashAmount;
                    break;
                }
                case R.id.saleHistoryReturnCardEditText : {
                    tempUsedAmount = mUseTotalCardAmount;
                    break;
                }
                case R.id.saleHistoryReturnGiftCardEditText : {
                    tempUsedAmount = mUseTotalGiftCardAmount;
                    break;
                }
                case R.id.saleHistoryReturnCheckEditText : {
                    tempUsedAmount = mUseTotalCheckAmount;
                    break;
                }
                case R.id.saleHistoryReturnPointEditText : {
                    tempUsedAmount = mUseTotalPointAmount;
                    break;
                }
            }

            double tempNowUse = getNowPaymentUse(mFocustEditText);
            double tempNowBalance = GlobalMemberValues.getDoubleAtString(
                    GlobalMemberValues.getStringFormatNumber((mTotalAmount - tempNowUse), "2"));

            GlobalMemberValues.logWrite("SalehistoryCalc22", "----------------------------------------\n");
            GlobalMemberValues.logWrite("SalehistoryCalc22", "inputSu : " + inputSu + "\n");
            GlobalMemberValues.logWrite("SalehistoryCalc22", "tempNowBalance : " + tempNowBalance + "\n");
            GlobalMemberValues.logWrite("SalehistoryCalc22", "----------------------------------------\n");

            if (tempUsedAmount <= tempNowBalance) {
                if (GlobalMemberValues.getDoubleAtString(inputSu) > (tempUsedAmount)) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Invalid Amount", "Close");
                    inputSu = "";
                    mTempPriceValue = "";
                }
            } else {
                if (GlobalMemberValues.getDoubleAtString(inputSu) > (tempNowBalance)) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Invalid Amount", "Close");
                    inputSu = "";
                    mTempPriceValue = "";
                }
            }

            mFocustEditText.setText(inputSu);
            setSelectedLinearLayoutBGColor(1);
        }
        setSaleHistoryReturnPrice();
    }

    private double getNowPaymentUse(TextView tempEt) {
        double returnUse = 0.0;

        double tempNowCash = 0.0;
        double tempNowCard = 0.0;
        double tempNowGiftCard = 0.0;
        double tempNowCheck = 0.0;
        double tempNowPoint = 0.0;

        if (tempEt.getId() != saleHistoryReturnCashEditText.getId()) {
            tempNowCash = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCashEditText.getText().toString());
        }
        if (tempEt.getId() != saleHistoryReturnCardEditText.getId()) {
            tempNowCard = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCardEditText.getText().toString());
        }
        if (tempEt.getId() != saleHistoryReturnGiftCardEditText.getId()) {
            tempNowGiftCard = GlobalMemberValues.getDoubleAtString(saleHistoryReturnGiftCardEditText.getText().toString());
        }
        if (tempEt.getId() != saleHistoryReturnCheckEditText.getId()) {
            tempNowCheck = GlobalMemberValues.getDoubleAtString(saleHistoryReturnCheckEditText.getText().toString());
        }
        if (tempEt.getId() != saleHistoryReturnPointEditText.getId()) {
            tempNowPoint = GlobalMemberValues.getDoubleAtString(saleHistoryReturnPointEditText.getText().toString());
        }

        returnUse = tempNowCash + tempNowCard + tempNowGiftCard + tempNowCheck + tempNowPoint;

        return returnUse;
    }

    private void setSaleHistoryReturnTypeEditText(TextView paramEt) {
        double tempBalanceToPay = mBalanceToPay * -1;
        double tempUsedAmount = 0.0;
        switch (paramEt.getId()) {
            case R.id.saleHistoryReturnCashEditText : {
                tempUsedAmount = mUseTotalCashAmount;
                break;
            }
            case R.id.saleHistoryReturnCardEditText : {
                tempUsedAmount = mUseTotalCardAmount;
                break;
            }
            case R.id.saleHistoryReturnGiftCardEditText : {
                tempUsedAmount = mUseTotalGiftCardAmount;
                break;
            }
            case R.id.saleHistoryReturnCheckEditText : {
                tempUsedAmount = mUseTotalCheckAmount;
                break;
            }
            case R.id.saleHistoryReturnPointEditText : {
                tempUsedAmount = mUseTotalPointAmount;
                break;
            }
        }
        if (tempUsedAmount <= tempBalanceToPay) {
            paramEt.setText(GlobalMemberValues.getStringFormatNumber(tempUsedAmount, "2"));
        } else {
            paramEt.setText(GlobalMemberValues.getStringFormatNumber(tempBalanceToPay, "2"));
        }
        GlobalMemberValues.logWrite("saleHistoryReturnTestResult", "mBalanceToPay : " + GlobalMemberValues.getStringFormatNumber(mBalanceToPay, "2"));
    }

    View.OnTouchListener mLinearLayoutTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setSelectedLinearLayoutBGColor(0);
            if (mBalanceToPay < 0) {
                //v.setBackgroundColor(Color.parseColor(GlobalMemberValues.PAYMENT_SELECTPAYMENTTYPE_BACKGROUNDCOLOR));
                v.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                mTempPriceValue = "";
            }
            int tempColorValueBg = R.drawable.roundlayout_line_noround;
            switch (v.getId()) {
                case R.id.saleHistoryReturnCashLinearLayout : {
                    mFocustEditText = saleHistoryReturnCashEditText;
                    if (mIssaleHistoryReturnTypeCash == "N") {
                        mIssaleHistoryReturnTypeCash = "Y";
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeCash = "N";
                        v.setBackgroundResource(tempColorValueBg);
                        saleHistoryReturnCashEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnCardLinearLayout : {
                    mFocustEditText = saleHistoryReturnCardEditText;
                    if (mIssaleHistoryReturnTypeCard == "N") {
                        mIssaleHistoryReturnTypeCard = "Y";
                        mCardUseYN = "Y";
                        if (mBalanceToPay < 0) {
                            if (saleHistoryReturnOfflineSwitch.isChecked()) {
                                //GlobalMemberValues.displayDialog(context, "TEMPORARY", "카드결제창 나타남.........", "Close");
                                String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                                GlobalMemberValues.logWrite("salehistoryreturnforcard", "mBalanceToPay : " + tempBalance + "\n");
                                setOpenSaleHistoryReturnCreditCardActivity(tempBalance);
                                mBalanceToPayForCardPoint = mBalanceToPay;
                            } else {
                                setSaleHistoryReturnTypeEditText(mFocustEditText);
                                mBalanceToPayForCardPoint = mBalanceToPay;
                            }
                        }
                        /**
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                         **/
                    } else {
                        mIssaleHistoryReturnTypeCard = "N";
                        mCardUseYN = "N";
                        v.setBackgroundResource(tempColorValueBg);
                        saleHistoryReturnCardEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnGiftCardLinearLayout : {
                    mFocustEditText = saleHistoryReturnGiftCardEditText;
                    if (mIssaleHistoryReturnTypeGiftCard == "N") {
                        mIssaleHistoryReturnTypeGiftCard = "Y";
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeGiftCard = "N";
                        v.setBackgroundResource(tempColorValueBg);
                        saleHistoryReturnGiftCardEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnCheckLinearLayout : {
                    mFocustEditText = saleHistoryReturnCheckEditText;
                    if (mIssaleHistoryReturnTypeCheck == "N") {
                        mIssaleHistoryReturnTypeCheck = "Y";
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeCheck = "N";
                        v.setBackgroundResource(tempColorValueBg);
                        saleHistoryReturnCheckEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnPointLinearLayout : {
                    mFocustEditText = saleHistoryReturnPointEditText;
                    if (mIssaleHistoryReturnTypePoint == "N") {
                        mIssaleHistoryReturnTypePoint = "Y";
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypePoint = "N";
                        v.setBackgroundResource(tempColorValueBg);
                        saleHistoryReturnPointEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
            }


            return false;
        }
    };

    View.OnClickListener mEditTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setsaleHistoryReturnEditTextForClick(v);
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setsaleHistoryReturnEditTextForClick(v);
            return true;
        }
    };

    public void setsaleHistoryReturnEditTextForClick(View v) {
        if (!(v.getId() == R.id.saleHistoryReturnBalanceEditText || v.getId() == R.id.saleHistoryReturnTenderedEditText
                || v.getId() == R.id.saleHistoryReturnChangeEditText)) {
            // 현재 터치된 EditText 객체저장
            TextView tempThisEditText = (TextView)v;
            // 임시사용 LinearLayout
            LinearLayout tempLn = null;
            mFocustEditText = tempThisEditText;
            // 키패드(키보드) 안보이게...
            GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
            mTempPriceValue = "";
            setSelectedLinearLayoutBGColor(0);


            int tempColorValueBg = R.drawable.roundlayout_line_noround;
            int tempColorValueBg2 = R.drawable.roundlayout_line_whitebule2;

            switch (v.getId()) {
                case R.id.saleHistoryReturnCashEditText : {
                    tempLn = saleHistoryReturnCashLinearLayout;
                    if (mIssaleHistoryReturnTypeCash == "N") {
                        mIssaleHistoryReturnTypeCash = "Y";
                        tempLn.setBackgroundResource(tempColorValueBg2);
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeCash = "N";
                        tempLn.setBackgroundResource(tempColorValueBg);
                        tempThisEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnCardEditText : {
                    tempLn = saleHistoryReturnCardLinearLayout;
                    if (mIssaleHistoryReturnTypeCard == "N") {
                        mIssaleHistoryReturnTypeCard = "Y";
                        tempLn.setBackgroundResource(tempColorValueBg2);
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeCard = "N";
                        tempLn.setBackgroundResource(tempColorValueBg);
                        tempThisEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnGiftCardEditText : {
                    tempLn = saleHistoryReturnGiftCardLinearLayout;
                    if (mIssaleHistoryReturnTypeGiftCard == "N") {
                        mIssaleHistoryReturnTypeGiftCard = "Y";
                        tempLn.setBackgroundResource(tempColorValueBg2);
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeGiftCard = "N";
                        tempLn.setBackgroundResource(tempColorValueBg);
                        tempThisEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnCheckEditText : {
                    tempLn = saleHistoryReturnCheckLinearLayout;
                    if (mIssaleHistoryReturnTypeCheck == "N") {
                        mIssaleHistoryReturnTypeCheck = "Y";
                        tempLn.setBackgroundResource(tempColorValueBg2);
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypeCheck = "N";
                        tempLn.setBackgroundResource(tempColorValueBg);
                        tempThisEditText.setText("");
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
                case R.id.saleHistoryReturnPointEditText : {
                    tempLn = saleHistoryReturnPointLinearLayout;
                    if (mIssaleHistoryReturnTypePoint == "N") {
                        mIssaleHistoryReturnTypePoint = "Y";
                        tempLn.setBackgroundResource(tempColorValueBg2);
                        if (mBalanceToPay < 0) {
                            setSaleHistoryReturnTypeEditText(mFocustEditText);
                        }
                    } else {
                        mIssaleHistoryReturnTypePoint = "N";
                        tempLn.setBackgroundResource(tempColorValueBg);
                        tempThisEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setSaleHistoryReturnPrice();
                    break;
                }
            }
        }
    }


    public static void setSelectedLinearLayoutBGColor(int paramType) {
        if (paramType == 1) {
            //String tempColorValue = GlobalMemberValues.PAYMENT_SELECTPAYMENTTYPE_BACKGROUNDCOLOR;
            int tempColorValueBg = R.drawable.roundlayout_line_whitebule2;
            switch (mFocustEditText.getId()) {
                case R.id.saleHistoryReturnCashEditText : {
                    saleHistoryReturnCashLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.saleHistoryReturnCardEditText : {
                    saleHistoryReturnCardLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.saleHistoryReturnGiftCardEditText : {
                    saleHistoryReturnGiftCardLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.saleHistoryReturnCheckEditText : {
                    saleHistoryReturnCheckLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.saleHistoryReturnPointEditText : {
                    saleHistoryReturnPointLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
            }
        } else {
            //String tempColorValue = GlobalMemberValues.PAYMENT_SELECTEDPAYMENTTYPE_BACKGROUNDCOLOR;
            int tempColorValueBg = R.drawable.roundlayout_line_whitebule;

            // Cash ----------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnCashEditText.getText().toString())
                    && !(saleHistoryReturnCashEditText.getText().toString().equals("0.00"))) {
                saleHistoryReturnCashLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                saleHistoryReturnCashLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                saleHistoryReturnCashEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Card ----------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnCardEditText.getText().toString())
                    && !(saleHistoryReturnCardEditText.getText().toString().equals("0.00"))) {
                saleHistoryReturnCardLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                saleHistoryReturnCardLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                saleHistoryReturnCardEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // GiftCard ------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnGiftCardEditText.getText().toString())
                    && !(saleHistoryReturnGiftCardEditText.getText().toString().equals("0.00"))) {
                saleHistoryReturnGiftCardLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                saleHistoryReturnGiftCardLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                saleHistoryReturnGiftCardEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Check ---------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnCheckEditText.getText().toString())
                    && !(saleHistoryReturnCheckEditText.getText().toString().equals("0.00"))) {
                saleHistoryReturnCheckLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                saleHistoryReturnCheckLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                saleHistoryReturnCheckEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Point ---------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(saleHistoryReturnPointEditText.getText().toString())
                    && !(saleHistoryReturnPointEditText.getText().toString().equals("0.00"))) {
                saleHistoryReturnPointLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                saleHistoryReturnPointLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                saleHistoryReturnPointEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
        }
    }

    private static Handler giftCardBalanceInfoDownloadHandler = new Handler() {
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

//                tempGiftCardBalance;
                Log.e("tempGiftCardBalance : ", tempGiftCardBalance);

//                setGiftCardBalance(tempGiftCardBalance);
                GlobalMemberValues.GIFTCARD_AFTER_BALANCE = GlobalMemberValues.getDoubleAtString(tempGiftCardBalance);

                // 210107 추가
                GlobalMemberValues.printReceiptByJHP(jsonroot, context, "return");

                // 프로그래스바를 사라지게 함 -------------------------------------------------------
                // -------------------------------------------------------------------------------------

            }
        }
    };

    private static Handler memMileageDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetMemberMileage)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                GlobalMemberValues.POINT_AFTER_BALANCE = GlobalMemberValues.getDoubleAtString(mGetMemberMileage);
                GlobalMemberValues.printReceiptByJHP(jsonroot, context, "return");
            } else {
                GlobalMemberValues.printReceiptByJHP(jsonroot, context, "return");
            }
        }
    };

    // 초기화 메소드
    private static void setInit() {
        saleHistoryReturnBalanceEditText.setText("");
        saleHistoryReturnTenderedEditText.setText("");
        saleHistoryReturnChangeEditText.setText("");
        saleHistoryReturnCashEditText.setText("");
        saleHistoryReturnCardEditText.setText("");
        saleHistoryReturnGiftCardEditText.setText("");
        saleHistoryReturnCheckEditText.setText("");
        saleHistoryReturnPointEditText.setText("");

        // 임시저장값
        mTempPriceValue = "";

        // 사용할 결제수단
        mIssaleHistoryReturnTypeCash       = "N";
        mIssaleHistoryReturnTypeCard       = "N";
        mIssaleHistoryReturnTypeGiftCard   = "N";
        mIssaleHistoryReturnTypeCheck      = "N";
        mIssaleHistoryReturnTypePoint      = "N";

        // 결제수단 LinearLayout 색상 초기화
        setSelectedLinearLayoutBGColor(0);

        // 카드결제 Tid 번호 초기화
        mCardTidNumber = "";

        // 리턴코드 초기화
        mStrReturnCode = "";

        mCheckCompany = "";

        mJsonList = new JSONArray();
    }
}
