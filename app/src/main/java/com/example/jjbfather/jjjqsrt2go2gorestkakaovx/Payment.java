package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class Payment {
    static Activity mActivity;
    static Context context;
    public static Context insContext;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    public static DatabaseInit dbInit;

    private TextView paymentTitleEditText;

    private Button closeBtn;

    private Button paymentSuButton1,paymentSuButton2,paymentSuButton3;
    private Button paymentSuButton4,paymentSuButton5,paymentSuButton6;
    private Button paymentSuButton7,paymentSuButton8,paymentSuButton9;
    private Button paymentSuButton0,paymentSuButton00,paymentSuButtonBack;
    private static Button paymentSuButtonV;

    private Button billsplitinpayment;

    private TextView modifysaledatebtn;

    private Button paymentCalculatorBtn;

    public static TextView paymentTopGetFoodTypeTv;

    public static TextView paymentBalanceEditText;
    public static TextView paymentTenderedEditText;
    public static TextView paymentChangeEditText;

    public static TextView paymentCashEditText;
    public static TextView paymentCardEditText;
    public static TextView paymentCheckEditText;
    public static TextView paymentPointEditText;

    public static TextView employeeInfoTv1;
    public static TextView employeeInfoTv2;
    public static TextView gettypeTv1;
    public static TextView pagerNumTv1;
    public static TextView pagerNumTv2;
    public static TextView pagerNumEv;
    public static TextView pagerHeader;

    // 기프트카드 사용액은 static 으로 한다.
    public static TextView paymentGiftCardEditText;

    public static Switch paymentReceiptSwitch;
    public static Switch paymentOfflineSwitch;

    private ListView paymentEmployeeListView;

    public static LinearLayout paymentCashLinearLayout;
    public static LinearLayout paymentCardLinearLayout;
    public static LinearLayout paymentGiftCardLinearLayout;
    public static LinearLayout paymentCheckLinearLayout;
    public static LinearLayout paymentPointLinearLayout;

    public static LinearLayout pagernumLn;

    private TextView paymentBalanceTitleTextView, paymentTenderedTitleTextView, paymentChangeTitleTextView;

    private TextView paymentCashTitleTextView, paymentCardTitleTextView, paymentGiftCardTitleTextView;
    private TextView paymentPointTitleTextView;

    public static TextView paymentCheckTitleTextView;

    public static TextView mFocustEditText;

    public static LinearLayout cashpadLn;

    public static Button cashEnterLeftBtn1, cashEnterLeftBtn2, cashEnterLeftBtn3, cashEnterLeftBtn4;
    public static Button cashEnterLeftBtn5, cashEnterLeftBtn6, cashEnterLeftBtn7;

    public static Button cardstatusbtn;

    // 임시저장값
    static String mTempPriceValue = "";

    // 임시저장값2 (Pager 용)
    static String mTempPriceValue2 = "";

    // 사용할 결제수단
    static String mIsPaymentTypeCash       = "N";
    static String mIsPaymentTypeCard       = "N";
    static String mIsPaymentTypeGiftCard   = "N";
    static String mIsPaymentTypeCheck      = "N";
    static String mIsPaymentTypePoint      = "N";

    // ListView 에 고객항목 붙일 때 필요한 객체들
    TemporaryPaymentEmployeeInfo mTempPaymentEmployee;
    public static ArrayList<TemporaryPaymentEmployeeInfo> mGeneralArrayList;
    public static PaymentEmployeeAdapter mPaymentEmployeeAdapter;


    // 전체 결제해야 될 금액
    public static double mTotalPayValue = 0.0;

    // payment 타입 선택 및 결제금액 입력후 잔액담는 변수
    public static double mBalanceToPay = 0.0;

    // payment 타입중 Card 와 Point 선택시 저장되는 변수
    public static double mBalanceToPayForCardPoint = 0.0;

    // Point 결제수단 선택시 현재 선택된 고객의 포인트를 저장할 객체 선언
    public static double mCustomerPoint = 0.0;

    // 사용한 기프트카드 정보
    public static String mGiftCardNumberUsed = "";

    // 쿼리문 저장용 객체
    static String strInsSqlQuery = "";
    static String strUpdSqlQuery = "";
    static String strDelSqlQuery = "";
    static String strInsSqlQuery_item = "";
    static String strUpdSqlQuery_item = "";

    // 카드결제시 Tid 번호
    static String mCardTidNumber = "";

    // 결제완료후 생성된 SalesCode
    static String mSalesCode;

    // 카드결제 선택시에만 사용되는 객체
    static String mCardUseYN = "N";

    // 영수증 출력을 위한 JSON 객체선언
    static JSONObject jsonroot = null;
    static JSONObject jsonrootForPaymentReview = null;

    // 주방영수증 출력을 위한 JSON 객체선언
    public static JSONObject jsonroot_kitchen = null;

    // PaymentCreditCard 클랙스에서 카드정보를 저장한 JSONObject 클래스의 jsontmp 인스턴스를 저장하기 위한 JSONArray 객체
    // 클래스명으로 접근하기 위해 static 으로 선언
    public static JSONArray mJsonList;

    // 영수증 이메일 발송여부
    static String receiptYN = "";

    // 카드결제가 최종결제가 아닐때, 카드결제가 있는지 여부
    public static String mCardPaidYN = "N";

    // Pax Void 처리를 위한 ArrayList
    public static ArrayList<String> mCardPaymentInfoArrayList;

    public static String mTextChnage = "0.00";
    public static String mTextCustomerId = "";

    public static String mCustomerEmail = "";

    public static String mCheckCompany = "";

    public static String mPhoneOrder = "";

    // 쿼리문자열을 담을 벡터 변수생성
    static Vector<String> strInsertQueryVec = null;

    public static ProgressDialog pmProDial;

    public static boolean isaveDBInPayment = false;

    // 버튼 클릭시간.
    private long mLastClickTime = 0;

    public Payment(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // payment 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setOnClickListener(paymentBtnClickListener);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        // EditText 최초 포커스를 Tendered 로 한다.
        // mFocustEditText = paymentTenderedEditText;

        // 서명이미지 삭제전 기다리는 시간 초기화
        GlobalMemberValues.DELETEWAITING = 0;
        GlobalMemberValues.logWrite("waitingtime", "기다리는 시간_in payment : " + GlobalMemberValues.DELETEWAITING + "\n");

        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        // 주방프린터 로딩창상황 초기화
        GlobalMemberValues.mKitchenLoading = false;

        // 06.03.2022
        GlobalMemberValues.isOpenPayment = false;
    }

    public void setContents() {

        int numberBackground = R.drawable.ab_imagebutton_discountextra_number;
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            numberBackground = R.drawable.ab_imagebutton_discountextra_number_lite;
            GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR = "#0054d5";
        }

        // Merchant 영수증만 프린트 할지 여부
        GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "N";

        // 세일후 백업여부
        GlobalMemberValues.BACKUPAFTERSALE = "N";

        jsonroot = null;
        mJsonList = null;
        jsonroot_kitchen = null;
        jsonrootForPaymentReview = null;
        GlobalMemberValues.mReReceiptprintYN = "N";

        mTextChnage = "0.00";
        mTextCustomerId = "";
        mCustomerEmail = "";

        //GlobalMemberValues.logWrite("printerMacAddress", "mac address : " + PosbankPrinter.mPrinter.getMacAddress() + "\n");
        //GlobalMemberValues.logWrite("printerMacAddress", "Printer.STATE_CONNECTED : " + Printer.MESSAGE_STATE_CHANGE + "\n");

        /** 주문코드 생성 *********************************************************************************************/
        // 세일즈코드값 (초기주문이므로 앞에 구분자가 정상주문인 K 로 한다.)
        String sales_salesCode = "K" + GlobalMemberValues.makeSalesCode();
        mSalesCode = sales_salesCode;

        // bill pay 일 경우
        if (GlobalMemberValues.isPaymentByBills) {
            String billSalesCode = MssqlDatabase.getResultSetValueToString(
                    " select top 1 salescode from bill_list_paid " +
                            " where holdcode = '" + GlobalMemberValues.mSelectedHoldCodeForBillPay + "' " +
                            " order by idx asc "
            );
            if (!GlobalMemberValues.isStrEmpty(billSalesCode)) {
                mSalesCode = billSalesCode;
            }

            GlobalMemberValues.mHoldCodeForBillPay_on = GlobalMemberValues.mSelectedHoldCodeForBillPay;
            GlobalMemberValues.mTableIdxOnBillPay_on = GlobalMemberValues.mSelectedTableIdxOnBillPay;
            GlobalMemberValues.mSubTableNumOnBillPay_on = GlobalMemberValues.mSelectedSubTableNumOnBillPay;
        }
        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCloseBtnTag");
        closeBtn.setOnClickListener(paymentBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        // 06.03.2022 --------------------------------------------------------------------------
        closeBtn.setVisibility(View.VISIBLE);
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCode_byRepay)) {
//            if (GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN1 != null) {
//                GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN1.setVisibility(View.INVISIBLE);
//            }
//            if (GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN2 != null) {
//                GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN2.setVisibility(View.INVISIBLE);
//            }
//            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY != null) {
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setVisibility(View.INVISIBLE);
//            }
//            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN != null) {
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.INVISIBLE);
//            }
//            if (GlobalMemberValues.GLOBAL_directButtonsLinearLayout3 != null) {
//                GlobalMemberValues.GLOBAL_directButtonsLinearLayout3.setVisibility(View.INVISIBLE);
//            }
//            if (GlobalMemberValues.GLOBAL_MAINTOPLN2 != null) {
//                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.INVISIBLE);
//            }
            closeBtn.setVisibility(View.INVISIBLE);
        }
        // -------------------------------------------------------------------------------------
        /***********************************************************************************************************/

        paymentTopGetFoodTypeTv = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentTopGetFoodTypeTvTag");
        paymentTopGetFoodTypeTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        if (!GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)) {
            String tempDeliTakeType = MssqlDatabase.getResultSetValueToString(
                    "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + MainMiddleService.mHoldCode + "' "
            );
            if (!GlobalMemberValues.isStrEmpty(tempDeliTakeType)) {
                switch (tempDeliTakeType) {
                    case "H" : {
                        paymentTopGetFoodTypeTv.setText("Here");
                        break;
                    }
                    case "T" : {
                        paymentTopGetFoodTypeTv.setText("To Go");
                        break;
                    }
                    case "D" : {
                        paymentTopGetFoodTypeTv.setText("Delivery");
                        break;
                    }
                }
            } else {
                paymentTopGetFoodTypeTv.setText("");
            }
        } else {
            paymentTopGetFoodTypeTv.setText("");
        }

        // 06.03.2022 -----------------------------------------------------------------
        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            if (GlobalMemberValues.isToGoSale()) {
                paymentTopGetFoodTypeTv.setText("To Go");
            } else {
                paymentTopGetFoodTypeTv.setText("Here");
            }
        }
        // ----------------------------------------------------------------------------


        paymentTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentTitleEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentTitleEditText.setText("");
            paymentTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_payment_title);
        } else {
            paymentTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        paymentBalanceTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentBalanceTitleTextViewTag");
        paymentBalanceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentTenderedTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentTenderedTitleTextViewTag");
        paymentTenderedTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentChangeTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentChangeTitleTextViewTag");
        paymentChangeTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);



        paymentCashTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCashTitleTextViewTag");
        paymentCashTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentCardTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCardTitleTextViewTag");
        paymentCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentGiftCardTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentGiftCardTitleTextViewTag");
        paymentGiftCardTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentCheckTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCheckTitleTextViewTag");
        paymentCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        paymentPointTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentPointTitleTextViewTag");
        paymentPointTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);


        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        paymentSuButton1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton1.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton2.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton3.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton4.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton5.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton6.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton7.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton8 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton8.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton9 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton9.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton0 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton0.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButton00 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            paymentSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            paymentSuButton00.setBackgroundResource(numberBackground);
        } else {
            paymentSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        paymentSuButtonBack = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButtonBack.setText("");
            paymentSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_payment_delete);
        }
        paymentSuButtonV = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            paymentSuButtonV.setText("");
            paymentSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_payment_enter);
        }

        paymentCalculatorBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCalculatorBtnTag");
        paymentCalculatorBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        modifysaledatebtn = (TextView) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("modifysaledatebtnTag");
        modifysaledatebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        if (GlobalMemberValues.ISSALEDATEMODIFY) {
            modifysaledatebtn.setVisibility(View.VISIBLE);
        } else {
            modifysaledatebtn.setVisibility(View.GONE);
        }
        settingSaleDateInPayment(GlobalMemberValues.ISSALEDATEMODIFY);

        billsplitinpayment = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("billsplitinpaymentTag");
        billsplitinpayment.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.2f
        );


        paymentSuButton1.setOnClickListener(paymentBtnClickListener);
        paymentSuButton2.setOnClickListener(paymentBtnClickListener);
        paymentSuButton3.setOnClickListener(paymentBtnClickListener);
        paymentSuButton4.setOnClickListener(paymentBtnClickListener);
        paymentSuButton5.setOnClickListener(paymentBtnClickListener);
        paymentSuButton6.setOnClickListener(paymentBtnClickListener);
        paymentSuButton7.setOnClickListener(paymentBtnClickListener);
        paymentSuButton8.setOnClickListener(paymentBtnClickListener);
        paymentSuButton9.setOnClickListener(paymentBtnClickListener);
        paymentSuButton0.setOnClickListener(paymentBtnClickListener);
        paymentSuButton00.setOnClickListener(paymentBtnClickListener);
        paymentSuButtonBack.setOnClickListener(paymentBtnClickListener);
        paymentSuButtonV.setOnClickListener(paymentBtnClickListener);
        paymentCalculatorBtn.setOnClickListener(paymentBtnClickListener);
        modifysaledatebtn.setOnClickListener(paymentBtnClickListener);
        billsplitinpayment.setOnClickListener(paymentBtnClickListener);
        /***********************************************************************************************************/

        /** Balance, Tendered, Change, Cash, Card, Gift Card, Check, Point  EditText 객체 생성 및 리스너 연결 **********/
        paymentBalanceEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentBalanceEditTextTag");
        paymentBalanceEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        paymentTenderedEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentTenderedEditTextTag");
        paymentTenderedEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        paymentChangeEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentChangeEditTextTag");
        paymentChangeEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 18);

        paymentCashEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCashEditTextTag");
        paymentCashEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);

        paymentCardEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCardEditTextTag");
        paymentCardEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);

        paymentGiftCardEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentGiftCardEditTextTag");
        paymentGiftCardEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);

        paymentCheckEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCheckEditTextTag");
        paymentCheckEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);

        paymentPointEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentPointEditTextTag");
        paymentPointEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 22);


        employeeInfoTv1 = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("employeeInfoTv1Tag");
        employeeInfoTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        employeeInfoTv2 = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("employeeInfoTv2Tag");
        employeeInfoTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        gettypeTv1 = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("gettypeTv1Tag");
        gettypeTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        pagerNumTv1 = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("pagerNumTv1Tag");
        pagerNumTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        pagerNumTv2 = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("pagerNumTv2Tag");
        pagerNumTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        pagerNumEv = (TextView) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("pagerNumEvTag");
        pagerNumEv.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        pagerNumEv.setOnTouchListener(mLinearLayoutTouch);

        pagerHeader = (TextView) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("pagerHeaderTag");
        pagerHeader.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);


        paymentBalanceEditText.setOnTouchListener(mEditTextTouch);

        paymentTenderedEditText.setOnTouchListener(mEditTextTouch);

        paymentChangeEditText.setOnTouchListener(mEditTextTouch);


        /**
         paymentCashEditText.setOnTouchListener(mEditTextTouch);
         paymentCashEditText.setOnClickListener(mEditTextClick);

         paymentCardEditText.setOnTouchListener(mEditTextTouch);
         paymentCardEditText.setOnClickListener(mEditTextClick);

         paymentGiftCardEditText.setOnTouchListener(mEditTextTouch);
         paymentGiftCardEditText.setOnClickListener(mEditTextClick);

         paymentCheckEditText.setOnTouchListener(mEditTextTouch);
         paymentCheckEditText.setOnClickListener(mEditTextClick);

         paymentPointEditText.setOnTouchListener(mEditTextTouch);
         paymentPointEditText.setOnClickListener(mEditTextClick);
         **/
        /***********************************************************************************************************/

        /** Receipt, Offline 객체 생성  및 리스너 연결 *************************************************************/
        paymentReceiptSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentReceiptSwitchTag");
        paymentReceiptSwitch.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSWITCHTEXTSIZE);
        paymentReceiptSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //setPrinterConnectionAfterCheckStatus();
                }
            }
        });
        String tempAutoreceipt = dbInit.dbExecuteReadReturnString(
                " select autoreceipt from salon_storestationsettings_deviceprinter "
        );

        GlobalMemberValues.logWrite("PaymentPrinterLog", "tempAutoreceipt : " + tempAutoreceipt + "...\n");

        if (tempAutoreceipt == "0" || tempAutoreceipt.equals("0")) {
            paymentReceiptSwitch.setChecked(true);

            /**
             // 자동 영수증 프린팅일 경우 프린터 연결을 시도한다.
             String tempPrinterName = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);
             GlobalMemberValues.setConnectPrinterDevice(MainActivity.mContext, tempPrinterName, "MAIN", false);
             **/
            GlobalMemberValues.logWrite("PaymentPrinterLog", "체크됨...\n");
        } else {
            paymentReceiptSwitch.setChecked(false);
            GlobalMemberValues.logWrite("PaymentPrinterLog", "체크안됨...\n");
        }

        paymentOfflineSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentOfflineSwitchTag");
        paymentOfflineSwitch.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSWITCHTEXTSIZE);
        String tempCardchargesystemuse = dbInit.dbExecuteReadReturnString(
                " select cardchargesystemuse from salon_storestationsettings_paymentgateway "
        );
        if (tempCardchargesystemuse == "0" || tempCardchargesystemuse.equals("0")) {
            paymentOfflineSwitch.setChecked(true);
            paymentOfflineSwitch.setEnabled(true);
            paymentOfflineSwitch.setOnCheckedChangeListener(mSwitchCheckedChange);
        } else {
            paymentOfflineSwitch.setChecked(false);
            paymentOfflineSwitch.setOnCheckedChangeListener(mSwitchCheckedChangeOnDisabled);
            //paymentOfflineSwitch.setEnabled(false);
        }
        /***********************************************************************************************************/

        /** Employee ListView 객체 생성  *****************************************************************************/
        /**
         paymentEmployeeListView = (ListView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
         .findViewWithTag("paymentEmployeeListViewTag");
         **/
        /***********************************************************************************************************/

        /** Balance, Tendered, Change, Cash, Card, Gift Card, Check, Point  LinearLayout 객체 생성 및 리스너 연결 *****/
        paymentCashLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCashLinearLayoutTag");
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentCashLinearLayout.setPadding(5,5,5,5);
        }
        paymentCardLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCardLinearLayoutTag");
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentCardLinearLayout.setPadding(5,5,5,5);
        }
        paymentGiftCardLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentGiftCardLinearLayoutTag");
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentGiftCardLinearLayout.setPadding(5,5,5,5);
        }
        paymentCheckLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentCheckLinearLayoutTag");
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentCheckLinearLayout.setPadding(5,5,5,5);
        }
        paymentPointLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentPointLinearLayoutTag");
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            paymentPointLinearLayout.setPadding(5,5,5,5);
        }

        paymentCashLinearLayout.setOnTouchListener(mLinearLayoutTouch);

        paymentCardLinearLayout.setOnTouchListener(mLinearLayoutTouch);

        paymentGiftCardLinearLayout.setOnTouchListener(mLinearLayoutTouch);

        paymentCheckLinearLayout.setOnTouchListener(mLinearLayoutTouch);

        paymentPointLinearLayout.setOnTouchListener(mLinearLayoutTouch);

        pagernumLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("pagernumLnTag");

        //
        String s_isPaymentPossibleByType = GlobalMemberValues.isPaymentPossibleByTypeToString();

        if (!s_isPaymentPossibleByType.contains("cash")){
            paymentCashLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            paymentCashLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!s_isPaymentPossibleByType.contains("creditcard")){
            paymentOfflineSwitch.setVisibility(View.INVISIBLE);
            paymentCardLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            paymentOfflineSwitch.setVisibility(View.VISIBLE);
            paymentCardLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!s_isPaymentPossibleByType.contains("giftcard")){
            paymentGiftCardLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            paymentGiftCardLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!s_isPaymentPossibleByType.contains("point")){
            paymentPointLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            paymentPointLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!s_isPaymentPossibleByType.contains("check")){
            paymentCheckLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            paymentCheckLinearLayout.setVisibility(View.VISIBLE);
        }
        //

        /***********************************************************************************************************/

        /** Cash Pad 버튼 *******************************************************************************************/
        cashEnterLeftBtn1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn1Tag");
        cashEnterLeftBtn1.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn1.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn2Tag");
        cashEnterLeftBtn2.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn2.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn3Tag");
        cashEnterLeftBtn3.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn3.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn4Tag");
        cashEnterLeftBtn4.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn4.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn5Tag");
        cashEnterLeftBtn5.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn5.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn6Tag");
        cashEnterLeftBtn6.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn6.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cashEnterLeftBtn7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cashEnterLeftBtn7Tag");
        cashEnterLeftBtn7.setOnClickListener(paymentBtnClickListener);
        cashEnterLeftBtn7.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        cardstatusbtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.findViewWithTag("cardstatusbtnTag");
        cardstatusbtn.setOnClickListener(paymentBtnClickListener);
        cardstatusbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        if (!GlobalMemberValues.isCardStatusSaveUse()) {
            cardstatusbtn.setVisibility(View.GONE);
        } else {
            cardstatusbtn.setVisibility(View.VISIBLE);
        }

        /***********************************************************************************************************/

        // EditText 최초 포커스를 Tendered 로 한다.
        // mFocustEditText = mFocustEditText;

        cashpadLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("cashpadLnTag");

        // 객체 및 색상 초기화
        setInit();

        // 주방프린트가 진행된 주문의 Salescode 초기화
        GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = "";

        // Balance 및 Change 초기 셋팅
        setPaymentPrice();
        GlobalMemberValues.logWrite("PaymentTest", "mTotalPayValue : " + mTotalPayValue + "\n");
        GlobalMemberValues.logWrite("PaymentTest", "mBalanceToPay : " + mBalanceToPay + "\n");

        // Pager 관련 셋팅
        if (GlobalMemberValues.isPagerUse()) {
            pagernumLn.setVisibility(View.VISIBLE);
            if (GlobalMemberValues.isPagerNumberAuto()) {
                pagerHeader.setText(GlobalMemberValues.PAGERNUMBERHEADERTXT);
                pagerNumEv.setText(GlobalMemberValues.getCustomerPagerNewNumber());
            } else {
                pagerNumEv.setText("");
            }

        } else {
            pagernumLn.setVisibility(View.INVISIBLE);
        }

        // 직원별 Amount ListView 셋팅
        setPaymentEmployeeInfo();

        // 영수증 프린트시 Tax Exempt 텍스트 (T/E) 사용여부
        String tempTaxExemptTxtUseYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select taxexemptprintingyn from salon_storestationsettings_deviceprinter "
        );
        if (GlobalMemberValues.isStrEmpty(tempTaxExemptTxtUseYN)) {
            tempTaxExemptTxtUseYN = "Y";
        }
        if (tempTaxExemptTxtUseYN == "Y" || tempTaxExemptTxtUseYN.equals("Y")) {
            GlobalMemberValues.mUseTaxExemptTxtYN = true;
        } else {
            GlobalMemberValues.mUseTaxExemptTxtYN = false;
        }

        // 주문시 고객정보 입력페이지 보여줄지 여부
        GlobalMemberValues.mCustomerInfoShowYN = GlobalMemberValues.isCustomerInfoShow();

        // 소켓 키친 프린터일 경우 재연결 시도
        //GlobalMemberValues.reconnectSocket();
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
                    if (!GlobalMemberValues.isStrEmpty(paymentCardEditText.getText().toString())) {
                        paymentCardEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                        setPaymentPrice();

                        String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                        setOpenPaymentCreditCardActivity(tempBalance);
                    }
                }
            } else {
                // salon_sales_card 테이블에 저장된 데이터가 있는지 체크해 보고
                // 저장된 데이터가 있으면 카드결제가 일어난 이후이기 때문에 on / off 변경을 못하게 한다.
                int tempSalonSalesCardCount = GlobalMemberValues.getIntAtString(
                        dbInit.dbExecuteReadReturnString(
                                "select count(idx) from salon_sales_card where salesCode = '" + mSalesCode + "' "));
                if (tempSalonSalesCardCount > 0) {
                    paymentOfflineSwitch.setChecked(true);
                    GlobalMemberValues.displayDialog(context, "Waraning", "You can't change on/off switch", "Close");
                }
            }
        }
    };

    public void settingSaleDateInPayment(boolean paramValue) {
        if (paramValue) {
            String tempHtmlText = "";
            if (GlobalMemberValues.isDifferentSaleDateToday()) {
                tempHtmlText = "TODAY : <font color=\"#0054d5\">" + GlobalMemberValues.STR_NOW_DATE + "</font>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "SALE DATE  : <font color=\"#f20e02\">" + GlobalMemberValues.SETTING_SALE_DATE + "</font>";
            } else {
                tempHtmlText = "TODAY : <font color=\"#0054d5\">" + GlobalMemberValues.STR_NOW_DATE + "</font>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "SALE DATE  : <font color=\"#0054d5\">" + GlobalMemberValues.SETTING_SALE_DATE + "</font>";
            }

            modifysaledatebtn.setText(Html.fromHtml(tempHtmlText));
        }
    }

    public void changeSaleDate() {
        String tempSaleDate = GlobalMemberValues.SETTING_SALE_DATE;
        openDatePickerDialog(tempSaleDate);
    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(MainActivity.mContext, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }

            GlobalMemberValues.SETTING_SALE_DATE = tempMonth + "-" + tempDay + "-" + tempYear;

            settingSaleDateInPayment(GlobalMemberValues.ISSALEDATEMODIFY);
        }
    };

    public static void openGetFoodTypeIntent(String paramOpenType) {
        Intent getFoodTypeIntent = new Intent(MainActivity.mContext.getApplicationContext(), SelectGetFoodType.class);
        getFoodTypeIntent.putExtra("payyn", paramOpenType);
        mActivity.startActivity(getFoodTypeIntent);
//        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }

    }

    public void setPaymentView() {
        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        // ADMIN 접속일 경우 선택못하게..
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            GlobalMemberValues.displayDialog(context, "Warning", "'ADMIN' can not proceed. Choose a employee", "Close");
            return;
        }

        // Product List 창을 열어서 보여지고 있을 때
        if (ProductList.onProductListYN == "Y" || ProductList.onProductListYN.equals("Y")) {
            //GlobalMemberValues.displayDialog(context, "Payment", "Please close the product list window", "Close");
            //ProductList.setmProductInfoEditTextInit();
            return;
        }

        if (mCardPaidYN == "N" || mCardPaidYN.equals("N")) {
            if (MainMiddleService.mGeneralArrayList.size() > 0) {
                //GlobalMemberValues.setFrameLayoutVisibleChange("payment");
                //GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED = "Y";
                openGetFoodTypeIntent("Y");
                setContents();

                GlobalMemberValues.setInitMainBottomButtonBg();
            } else {
                GlobalMemberValues.displayDialog(context, "Payment", "There is no item to pay", "Close");
            }
        } else {
            setGroupVoidCardStart();
        }
    }

    View.OnClickListener paymentBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.billsplitinpayment : {
                    LogsSave.saveLogsInDB(204);
                    TableSaleMain.openBillSplitMerge();
                    break;
                }
                case R.id.cardstatusbtn : {
                    int i_statusCnt = 0;
                    i_statusCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select count(idx) from card_processing_data where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
                    ));
                    if (i_statusCnt == 0){
                        GlobalMemberValues.displayDialog(context, "Warning", "No Data", "Close");
                        return;
                    }
                    Intent creditCardStatusIntent = new Intent(MainActivity.mContext.getApplicationContext(), CreditCardStatusActivity.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------

                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(creditCardStatusIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
                    }

                    break;
                }
                case R.id.mainSaleCartButton_Payment : {
                    // 중복 클릭 방지.
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    LogsSave.saveLogsInDB(97);
                    mLastClickTime = SystemClock.elapsedRealtime();

//                    MainMiddleService.mGeneralArrayList_copy = MainMiddleService.mGeneralArrayList;

                    // temp_salecart 수정 제크. 032024
                    if (MainActivity.temp_str_salecart_cnt == MainMiddleService.mGeneralArrayList.size()) {
                        // 수정 안됨.
                    } else {
                        // 수정 됨.
                        GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                                "There are changes to your order history.\nPlease print to kitchen the changes first", "Close");
                        return;
                    }
                    // temp_salecart 수정 제크.


                    GlobalMemberValues.mIsClickPayment = true;


                    // 10092023 ------------------------------------------------------------
                    int qtycount_now = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select sum(sQty) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' "
                    ));
                    int qtycount_onbill = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select top 1 qtycount from salon_billprinted_itemqty where holdcode = '" + MainMiddleService.mHoldCode + "' " +
                                    " order by idx desc "
                    ));

                    if (qtycount_onbill > 0) {
                        int diffCount = qtycount_now - qtycount_onbill;
                        if (diffCount != 0) {
                            String strType = "";
                            if (diffCount > 0) {
                                strType = "added";
                            } else {
                                strType = "removed";
                            }
                            String strMsg = "There is a menu " + strType + " after bill printing\nDo you want to continue?";
                            new AlertDialog.Builder(context)
                                    .setTitle("Warning")
                                    .setMessage(strMsg)
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    setPaymentView();
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            GlobalMemberValues.mIsClickPayment = false;
                                        }
                                    })
                                    .show();
                        } else {
                            setPaymentView();
                        }
                    } else {
                        setPaymentView();
                    }
                    // 10092023 ------------------------------------------------------------


                    break;
                }
                case R.id.paymentCloseBtn : {
                    LogsSave.saveLogsInDB(202);
                    // 07.18.2022 - add pay for cash, card
                    setAddDeleteAddPay(false);

                    if (GlobalMemberValues.isPaymentByBills) {
                        //GlobalMemberValues.displayDialog(context, "Payment", "Choose Item", "Close");
                        return;
                    }

                    if (mCardPaidYN == "N" || mCardPaidYN.equals("N")) {
                        GlobalMemberValues.setFrameLayoutVisibleChange("main");

                        // 초기화
                        setInit();
                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    } else {
                        setGroupVoidCardStart();
                    }

                    // 수령타입별때 tax free 일 경우
                    String tempDeliTakeType = MssqlDatabase.getResultSetValueToString(
                            "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + MainMiddleService.mHoldCode + "' "
                    );
                    if (GlobalMemberValues.isTaxFreeByGetType(tempDeliTakeType)) {
                        GlobalMemberValues.setUndoTaxFreeOnAllItems();
                    }
                    break;
                }
                case R.id.paymentSuButton1 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton2 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton3 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton4 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton5 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton6 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton7 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton8 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton9 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton0 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButton00 : {
                    LogsSave.saveLogsInDB(205);
                    numberButtonClick(paymentSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentSuButtonBack : {
                    LogsSave.saveLogsInDB(206);
                    if (mFocustEditText == pagerNumEv) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mTempPriceValue2);
                        if (!GlobalMemberValues.isStrEmpty(mTempPriceValue2)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mTempPriceValue2 = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mTempPriceValue2)) {
                                mTempPriceValue2 = "0";
                            }
                        } else {
                            mTempPriceValue2 = "0";
                        }

                        String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue2) * 1), "0");
                        mFocustEditText.setText(inputSu);
                    } else {
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
                        mFocustEditText.setText(inputSu);

                        setSelectedLinearLayoutBGColor(1);

                        setPaymentPrice();

                        GlobalMemberValues.logWrite("Payment", "mTempPriceValue : " + mTempPriceValue + "\n");
                    }

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);

                    break;
                }
                case R.id.paymentSuButtonV : {
                    paymentComplite_LogSave(false);
                    paymentSuButtonV.setEnabled(false);

                    try {
                        setFinishProcess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
                    break;
                }
                case R.id.paymentCalculatorBtn : {
                    LogsSave.saveLogsInDB(203);
                    openCalculator();
                    break;
                }

                case R.id.modifysaledatebtn : {
                    changeSaleDate();
                    break;
                }

                // 왼쪽버튼 터치(클릭)시
                case R.id.cashEnterLeftBtn1 : {
                    setCashAmountEv(cashEnterLeftBtn1.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn2 : {
                    setCashAmountEv(cashEnterLeftBtn2.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn3 : {
                    setCashAmountEv(cashEnterLeftBtn3.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn4 : {
                    setCashAmountEv(cashEnterLeftBtn4.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn5 : {
                    setCashAmountEv(cashEnterLeftBtn5.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn6 : {
                    setCashAmountEv(cashEnterLeftBtn6.getText().toString());
                    break;
                }
                case R.id.cashEnterLeftBtn7 : {
                    setCashAmountEv(cashEnterLeftBtn7.getText().toString());
                    break;
                }
            }
        }
    };

    public static void setFinishProcess() throws JSONException {
        GlobalMemberValues.logWrite("PaymentPrinterLog", "여기실행...1\n");

        if (MainMiddleService.mGeneralArrayList.size() == 0) {
            GlobalMemberValues.displayDialog(context, "Payment", "Choose Item", "Close");
            GlobalMemberValues.setFrameLayoutVisibleChange("main");
            paymentSuButtonV.setEnabled(true);
            return;
        }

        String tempChange = paymentChangeEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempChange)
                || GlobalMemberValues.getDoubleAtString(tempChange) < 0) {
            GlobalMemberValues.displayDialog(context, "Payment", "Amount is not enough", "Close");
            paymentSuButtonV.setEnabled(true);
            return;
        }

        if ((mActivity != null) && (!mActivity.isFinishing())) {
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Processing....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
                // Set a click listener for progress dialog cancel button
                @Override
                public void onClick(DialogInterface dialog, int which){
                    // dismiss the progress dialog
                    pd.dismiss();
                }
            });
            pmProDial = pd;
            pmProDial.show();
//            pmProDial = ProgressDialog.show(context, "", "Processing...", true, false);
        }

        // DB 처리 -----------------------------------------------------------------------------
        String returnSalesCode = setPaymentProcess();
        if (GlobalMemberValues.isStrEmpty(returnSalesCode)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            paymentSuButtonV.setEnabled(true);
            return;
        } else {
            // 07.19.2022 - add pay for cash, card
            GlobalMemberValues.deleteCartLastItemForAddPay(GlobalMemberValues.getAddPayData());

            // 리스트뷰를 비운다.
            MainMiddleService.clearListView();


        }
        // -------------------------------------------------------------------------------------

        // DB 전체 백업 -------------------------------------------------------------------------
        //if (GlobalMemberValues.GLOBAL_DATABASEBACKUP == 1) {
        //    CommandButton.backupDatabase(false);
        //}
        // -------------------------------------------------------------------------------------

        // 직원정보 초기화 ---------------------------------------------------------------------
        //GlobalMemberValues.setEmployeeInfoInit();
        // -------------------------------------------------------------------------------------

        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.

        if (!GlobalMemberValues.isCustomerSelectReceipt()) {
            if (GlobalMemberValues.isUploadOnlySaledData()) {
                GlobalMemberValues.RECEIPTNOFORUPLOAD = mSalesCode;
            }
            GlobalMemberValues.logWrite("uploaddatalog", "GlobalMemberValues.RECEIPTNOFORUPLOAD - 1 : " + GlobalMemberValues.RECEIPTNOFORUPLOAD + "\n");
            // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
            GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);
        }

//        // 서명이미지 파일 지정한 기간의 파일 삭제 --------------------------------------------------
        // 083123 주석처리 제거 (재활성)
        GlobalMemberValues.logWrite("SignatureImageDeleteLog", "서명이미지 파일지정한 기간의 파일 삭제시작\n");
        GlobalMemberValues.deleteSignatureImageOnSales(context, mActivity);
//        // -------------------------------------------------------------------------------------

        MainMiddleService.mHoldCode = "NOHOLDCODE";
        MainMiddleService.selectedPosition = -1;
        GlobalMemberValues.setFrameLayoutVisibleChange("main");
        // 초기화
        //setInit();
        // point 제거
        GlobalMemberValues.POINT_EARNED = 0.0;

        // 102022 이곳에
        GlobalMemberValues.setGoneForCardCashPayView();

        GlobalMemberValues.mSplit_transaction_id = "";
    }

    public static void openPaymentReview(Context paramContext) {
        // 고객이 영수증 발급 타입을 선택하는 경우에는 Payment Review 를 띄우지 않는다.
        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            return;
        }

        if (paymentCashEditText == null) return;    // pushorder kitchen print 시 openPaymentReview 를 들어와서 paymentCashEditText 가 null 이라서 carsh

        double tempCashAmt = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());

        Intent paymentReviewIntent = new Intent(paramContext, PaymentReview.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        paymentReviewIntent.putExtra("PaymentCashAmount", tempCashAmt);
        paymentReviewIntent.putExtra("PaymentChangeAmount", mTextChnage);
        paymentReviewIntent.putExtra("receiptYN", receiptYN);
        paymentReviewIntent.putExtra("GiftCardNumberUsed", mGiftCardNumberUsed);
        paymentReviewIntent.putExtra("CustomerEmail", mTextCustomerId);
        paymentReviewIntent.putExtra("CustomerEmailReal", mCustomerEmail);
        paymentReviewIntent.putExtra("PhoneOrder", mPhoneOrder);
        if (jsonrootForPaymentReview != null) {
            paymentReviewIntent.putExtra("JsonReceipt", jsonrootForPaymentReview.toString());
        }
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(paymentReviewIntent);
        //mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);

//        GlobalMemberValues.logWrite("paymentreviewextra", "PaymentChangeAmount : " + mTextChnage + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "receiptYN : " + mTextChnage + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "mGiftCardNumberUsed : " + mGiftCardNumberUsed + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "mTextCustomerId : " + mTextCustomerId + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "JsonReceipt : " + jsonrootForPaymentReview.toString() + "\n");
    }

    public static void openPaymentReview_NoReceipt(Context paramContext) {

        double tempCashAmt = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());

        Intent paymentReviewIntent = new Intent(paramContext, PaymentReview.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        paymentReviewIntent.putExtra("PaymentCashAmount", tempCashAmt);
        paymentReviewIntent.putExtra("PaymentChangeAmount", mTextChnage);
        paymentReviewIntent.putExtra("receiptYN", receiptYN);
        paymentReviewIntent.putExtra("GiftCardNumberUsed", mGiftCardNumberUsed);
        paymentReviewIntent.putExtra("CustomerEmail", mTextCustomerId);
        paymentReviewIntent.putExtra("CustomerEmailReal", mCustomerEmail);
        paymentReviewIntent.putExtra("PhoneOrder", mPhoneOrder);
        if (jsonrootForPaymentReview != null) {
            paymentReviewIntent.putExtra("JsonReceipt", jsonrootForPaymentReview.toString());
        }
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(paymentReviewIntent);
        //mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);

//        GlobalMemberValues.logWrite("paymentreviewextra", "PaymentChangeAmount : " + mTextChnage + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "receiptYN : " + mTextChnage + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "mGiftCardNumberUsed : " + mGiftCardNumberUsed + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "mTextCustomerId : " + mTextCustomerId + "\n");
//        GlobalMemberValues.logWrite("paymentreviewextra", "JsonReceipt : " + jsonrootForPaymentReview.toString() + "\n");
    }

    public static void setPaymentPrice() {
        double paymentEvValueCash       = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());
        double paymentEvValueCard       = GlobalMemberValues.getDoubleAtString(paymentCardEditText.getText().toString());
        double paymentEvValueGiftCard   = GlobalMemberValues.getDoubleAtString(paymentGiftCardEditText.getText().toString());
        double paymentEvValueCheck      = GlobalMemberValues.getDoubleAtString(paymentCheckEditText.getText().toString());
        double paymentEvValuePoint      = GlobalMemberValues.getDoubleAtString(paymentPointEditText.getText().toString());

        String tempTotalValue = "";
        if (GlobalMemberValues.isPaymentByBills) {
            tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.getText().toString();
        } else {
            tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString();
        }

        double totalBalance = GlobalMemberValues.getDoubleAtString(tempTotalValue);
        double paymentEvTendered = GlobalMemberValues.getDoubleAtString(paymentTenderedEditText.getText().toString());
        double paymentEvChange = GlobalMemberValues.getDoubleAtString(paymentChangeEditText.getText().toString());

        paymentEvTendered = paymentEvValueCash + paymentEvValueCard + paymentEvValueGiftCard + paymentEvValueCheck + paymentEvValuePoint;
        paymentEvChange = (paymentEvTendered - totalBalance);

        paymentBalanceEditText.setText(GlobalMemberValues.getStringFormatNumber(totalBalance, "2"));
        paymentTenderedEditText.setText(GlobalMemberValues.getStringFormatNumber(paymentEvTendered, "2"));
        paymentChangeEditText.setText(GlobalMemberValues.getStringFormatNumber(paymentEvChange, "2"));

        // mBalanceToPay 셋팅
        mBalanceToPay = paymentEvChange;

        // mTotalPayValue 셋팅
        mTotalPayValue = totalBalance;
    }

    private void numberButtonClick(Button btn) {

        if (mFocustEditText == null) {
            GlobalMemberValues.displayDialog(context, "Payment", "Choose Payment Type", "Close");
            return;
        }

        if (mFocustEditText == pagerNumEv) {
            // Pager No. 입력 관련 --------------------------------------------------------------------------------------------------
            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mTempPriceValue2.length() < 3) {
                sb.append(mTempPriceValue2).append(btn.getText().toString());
                Long tempNumber = Long.parseLong(sb.toString());
                mTempPriceValue2 = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue2) * 1), "0");

                mFocustEditText.setText(inputSu);
            }
            // ----------------------------------------------------------------------------------------------------------------------
        }  else {
            if (mCardUseYN.equals("Y") && mBalanceToPay < 0 && paymentOfflineSwitch.isChecked()) {
                //GlobalMemberValues.displayDialog(context, "TEMPORARY", "카드결제창 나타남.........", "Close");
                String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                setOpenPaymentCreditCardActivity(tempBalance);
                mBalanceToPayForCardPoint = mBalanceToPay;

                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mTempPriceValue.length() < 12) {
                sb.append(mTempPriceValue).append(btn.getText().toString());
                Long tempNumber = Long.parseLong(sb.toString());
                mTempPriceValue = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mTempPriceValue) * 0.01), "2");

                if (mFocustEditText == paymentPointEditText) {
                    if (GlobalMemberValues.getDoubleAtString(inputSu) > mCustomerPoint) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Remaining Point is not enough", "Close");
                        return;
                    }
                }

                if (mFocustEditText == paymentCardEditText || mFocustEditText == paymentPointEditText) {
                    if (GlobalMemberValues.getDoubleAtString(inputSu) > (mBalanceToPayForCardPoint*-1)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Amount is more than Balance", "Close");
                        return;
                    }
                }
                mFocustEditText.setText(inputSu);
                setSelectedLinearLayoutBGColor(1);
            }
            setPaymentPrice();
        }
    }

    public static void openCashPadOnSliding(boolean paramValue, double paramPrice) {
        if (!GlobalMemberValues.isCashPadUse()) {
            return;
        }
        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_right);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        if (paramValue) {
            double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentBalanceEditText.getText().toString());
            if (tempBalanceValue > 0) {
                cashpadLn.setVisibility(View.VISIBLE);
                cashpadLn.setAnimation(animation1);

                GlobalMemberValues.logWrite("cashpadvaluelog", "paramPrice : " + paramPrice + "\n");
                setCashPad(paramPrice);
            }
        } else {
            cashpadLn.setVisibility(View.GONE);
            //cashpadLn.setAnimation(animation1);
        }
    }

    public static void setCashPad(double paramPrice) {
        double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentBalanceEditText.getText().toString());
        if (tempBalanceValue > 0 && !GlobalMemberValues.isStrEmpty(paramPrice + "")) {
            // 오른쪽 버튼 값 만들기 ----------------------------------------------------------
            String baseValue = "";
            baseValue = paramPrice + "";

            String setValues[] = {"", "", "", "", "", "", "", ""};

            // 값1
            double tempDblRightBtnValue1 = GlobalMemberValues.getDoubleAtString(baseValue);

            // 값2
            String tempRightBtnValue2 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 1), "0");
            double tempDblRightBtnValue2 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue2) * 1;

            String tempRightBtnValue3 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 5), "0");
            double tempDblRightBtnValue3 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue3) * 5;

            String tempRightBtnValue4 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 10), "0");
            double tempDblRightBtnValue4 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue4) * 10;

            String tempRightBtnValue5 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 20), "0");
            double tempDblRightBtnValue5 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue5) * 20;

            String tempRightBtnValue6 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 50), "0");
            double tempDblRightBtnValue6 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue6) * 50;

            String tempRightBtnValue7 = GlobalMemberValues.getStringFormatNumber(
                    Math.ceil(GlobalMemberValues.getDoubleAtString(baseValue) / 100), "0");
            double tempDblRightBtnValue7 = GlobalMemberValues.getDoubleAtString(tempRightBtnValue7) * 100;

            int tempArrCnt = 1;
            setValues[0] = tempDblRightBtnValue1 + "";
            if (tempDblRightBtnValue1 != tempDblRightBtnValue2) {
                setValues[tempArrCnt] = tempDblRightBtnValue2 + "";
                tempArrCnt++;
            }
            if (tempDblRightBtnValue2 != tempDblRightBtnValue3) {
                setValues[tempArrCnt] = tempDblRightBtnValue3 + "";
                tempArrCnt++;
            }
            if (tempDblRightBtnValue3 != tempDblRightBtnValue4) {
                setValues[tempArrCnt] = tempDblRightBtnValue4 + "";
                tempArrCnt++;
            }
            if (tempDblRightBtnValue4 != tempDblRightBtnValue5) {
                setValues[tempArrCnt] = tempDblRightBtnValue5 + "";
                tempArrCnt++;
            }
            if (tempDblRightBtnValue5 != tempDblRightBtnValue6) {
                setValues[tempArrCnt] = tempDblRightBtnValue6 + "";
                tempArrCnt++;
            }
            if (tempDblRightBtnValue6 != tempDblRightBtnValue7) {
                setValues[tempArrCnt] = tempDblRightBtnValue7 + "";
                tempArrCnt++;
            }

            for (int i = 0; i < 7; i++) {
                GlobalMemberValues.logWrite("cashpadvaluelog", "value" + i + " : " + setValues[i] + "\n");
            }

            cashEnterLeftBtn1.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[0]));

            if (!GlobalMemberValues.isStrEmpty(setValues[1])) {
                cashEnterLeftBtn2.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[1]));
            } else {
                cashEnterLeftBtn2.setText("");
                cashEnterLeftBtn2.setVisibility(View.INVISIBLE);
            }

            if (!GlobalMemberValues.isStrEmpty(setValues[2])) {
                cashEnterLeftBtn3.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[2]));
            } else {
                cashEnterLeftBtn3.setText("");
                cashEnterLeftBtn3.setVisibility(View.INVISIBLE);
            }

            if (!GlobalMemberValues.isStrEmpty(setValues[3])) {
                cashEnterLeftBtn4.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[3]));
            } else {
                cashEnterLeftBtn4.setText("");
                cashEnterLeftBtn4.setVisibility(View.INVISIBLE);
            }

            if (!GlobalMemberValues.isStrEmpty(setValues[4])) {
                cashEnterLeftBtn5.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[4]));
            } else {
                cashEnterLeftBtn5.setText("");
                cashEnterLeftBtn5.setVisibility(View.INVISIBLE);
            }

            if (!GlobalMemberValues.isStrEmpty(setValues[5])) {
                cashEnterLeftBtn6.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[5]));
            } else {
                cashEnterLeftBtn6.setText("");
                cashEnterLeftBtn6.setVisibility(View.INVISIBLE);
            }

            if (!GlobalMemberValues.isStrEmpty(setValues[6])) {
                cashEnterLeftBtn7.setText("$" + GlobalMemberValues.getCommaStringForDouble(setValues[6]));
            } else {
                cashEnterLeftBtn7.setText("");
                cashEnterLeftBtn7.setVisibility(View.INVISIBLE);
            }
        }
    }


    private void setCashAmountEv(String paramValue) {
        paymentCashEditText.setText(GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.getDoubleAtString(paramValue) + ""));

        double tempInsCash = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString()) * -1;

        TextView focusTv = paymentCashEditText;
        setPaymentTypeEditText(focusTv, tempInsCash);

        setPaymentPrice();
    }

    public static void setPaymentTypeEditText(TextView paramEt, double insPayAmount) {
        if (mFocustEditText == paymentPointEditText) {
            if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                mCustomerPoint
                        = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memMileage);
                if (mCustomerPoint == 0) {
                    paymentPointLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                } else {
                    if (mCustomerPoint < (insPayAmount*-1)) {
                        paramEt.setText(GlobalMemberValues.getStringFormatNumber(mCustomerPoint, "2"));
                    } else {
                        paramEt.setText(GlobalMemberValues.getStringFormatNumber(insPayAmount * -1, "2"));
                    }
                }
            } else {
                paymentPointLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
            }
        } else {
            double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentBalanceEditText.getText().toString());
            double tempInsPayAmount = insPayAmount * -1;
            if (tempBalanceValue == 0) {
                tempInsPayAmount = 0.0;
            }
            paramEt.setText(GlobalMemberValues.getStringFormatNumber(tempInsPayAmount, "2"));
        }
        GlobalMemberValues.logWrite("PaymentTestResult", "insPayAmount : " + GlobalMemberValues.getStringFormatNumber(insPayAmount, "2"));
    }

    // 07.18.2022 - add pay for cash, card
    // 09282023
    public static void setAddDeleteAddPay(boolean paramValue) {
        GlobalMemberValues.deleteCartLastItemForAddPay(GlobalMemberValues.getAddPayData());

        if (paramValue) {
            if (GlobalMemberValues.getGiftCardItemsCountInCart() == 0) {
                boolean getValue = false;

                if (GlobalMemberValues.getTaxExemptItemsCountInCart() > 0) {
                    if (GlobalMemberValues.getAllItemsCountInCart() == GlobalMemberValues.getTaxExemptItemsCountInCart()) {
                        getValue = true;
                    }
                } else {
                    getValue = true;
                }

                // 10202023
                if (GlobalMemberValues.getAddPayType().equals("A")) {
                    getValue = false;
                }

                if (getValue) {
                    GlobalMemberValues.addCartLastItemForAddPay(GlobalMemberValues.getAddPayData());
                }
            }
        }

        setPaymentPrice();
    }

    // 07.16.2022
    public static void setClickCashLn() {
        // 07.18.2022 - add pay for cash, card
        setAddDeleteAddPay(false);

        pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext);

        mFocustEditText = paymentCashEditText;
        if (mIsPaymentTypeCash == "N") {
            // 07.18.2022 - add pay for cash, card
            if (GlobalMemberValues.getAddPayType().equals("B")) {
                setAddDeleteAddPay(true);
            }

            mIsPaymentTypeCash = "Y";
            double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentBalanceEditText.getText().toString());
            if (tempBalanceValue == 0) {
                if (mBalanceToPay <= 0) {
                    setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                    setPaymentPrice();

                    /**
                     if (GlobalMemberValues.isCashPadUse()) {
                     openCashPad();
                     } else {
                     setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                     setPaymentPrice();
                     }
                     **/
                }
            } else {
                if (mBalanceToPay < 0) {
                    setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                    setPaymentPrice();

                    /**
                     if (GlobalMemberValues.isCashPadUse()) {
                     openCashPad();
                     } else {
                     setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                     setPaymentPrice();
                     }
                     **/
                }

                // cash pad 오픈
                openCashPadOnSliding(true, GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString()));
            }
        } else {
            // 07.18.2022 - add pay for cash, card
            setAddDeleteAddPay(false);

            mIsPaymentTypeCash = "N";
            paymentCashLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
            paymentCashEditText.setText("");

            setPaymentPrice();

            // cash pad 오픈여부
            openCashPadOnSliding(false, 0);
        }
        mCardUseYN = "N";
    }

    public static void setClickCardLn(View v) {
        // 07.18.2022 - add pay for cash, card
        setAddDeleteAddPay(false);

        pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext);

        openCashPadOnSliding(false, 0);

        mFocustEditText = paymentCardEditText;
        if (mIsPaymentTypeCard == "N") {
            // 07.18.2022 - add pay for cash, card
            if (GlobalMemberValues.getAddPayType().equals("C")) {
                setAddDeleteAddPay(true);
            }

            mIsPaymentTypeCard = "Y";
            mCardUseYN = "Y";
            if (mBalanceToPay < 0) {
                if (paymentOfflineSwitch.isChecked()) {
                    //GlobalMemberValues.displayDialog(context, "TEMPORARY", "카드결제창 나타남.........", "Close");
                    String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                    setOpenPaymentCreditCardActivity(tempBalance);
                    mBalanceToPayForCardPoint = mBalanceToPay;
                } else {
                    setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                    mBalanceToPayForCardPoint = mBalanceToPay;
                }
            }
        } else {
            if (mCardPaidYN == "N" || mCardPaidYN.equals("N")) {
                mIsPaymentTypeCard = "N";
                mCardUseYN = "N";
                v.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentCardEditText.setText("");
                mBalanceToPayForCardPoint = 0.0;
            } else {
                setGroupVoidCardStart();
            }
        }
        setPaymentPrice();
    }



    // 09282023
    public static void setClickGiftCardLn(View v) {
        // 07.18.2022 - add pay for cash, card
        // 10212023 - 아래 메소드 주석처리
        // setAddDeleteAddPay(true);

        pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext);

        openCashPadOnSliding(false, 0);

        mFocustEditText = paymentGiftCardEditText;
        if (mIsPaymentTypeGiftCard == "N") {
            // 10212023
            if (GlobalMemberValues.getAddPayType().equals("B")) {
                setAddDeleteAddPay(true);
            }

            mIsPaymentTypeGiftCard = "Y";
            if (mBalanceToPay < 0) {
                //setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                String tempBalance = GlobalMemberValues.getStringFormatNumber(mBalanceToPay * -1, "2");
                setOpenPaymentGiftCard(tempBalance);
            }
        } else {
            // 10212023
            setAddDeleteAddPay(false);

            mIsPaymentTypeGiftCard = "N";
            v.setBackgroundResource(R.drawable.roundlayout_line_noround);
            paymentGiftCardEditText.setText("");
        }
        mCardUseYN = "N";
        setPaymentPrice();

    }

    // 09282023
    // 09282023
    public static void setClickPointLn(View v) {

        // 07.18.2022 - add pay for cash, card
        // 10212023 - 아래 메소드 주석처리
        // setAddDeleteAddPay(true);

        pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext);

        openCashPadOnSliding(false, 0);

        mFocustEditText = paymentPointEditText;
        if (mIsPaymentTypePoint == "N") {
            // 10212023
            if (GlobalMemberValues.getAddPayType().equals("B")) {
                setAddDeleteAddPay(true);
            }

            mIsPaymentTypePoint = "Y";
            if (mBalanceToPay < 0) {
                setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                mBalanceToPayForCardPoint = mBalanceToPay;
            }
        } else {
            // 10212023
            setAddDeleteAddPay(false);

            mIsPaymentTypePoint = "N";
            v.setBackgroundResource(R.drawable.roundlayout_line_noround);
            paymentPointEditText.setText("");
            mBalanceToPayForCardPoint = 0.0;
        }
        mCardUseYN = "N";
        setPaymentPrice();
    }




    View.OnTouchListener mLinearLayoutTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setSelectedLinearLayoutBGColor(0);
            if (mBalanceToPay < 0) {
                v.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                mTempPriceValue = "";
            }
            switch (v.getId()) {
                case R.id.paymentCashLinearLayout : {
                    // 07.16.2022
                    setClickCashLn();

                    break;
                }
                case R.id.paymentCardLinearLayout : {
                    // 05112023
                    setClickCardLn(v);

                    break;
                }
                case R.id.paymentGiftCardLinearLayout : {

                    // 09282023
                    setClickGiftCardLn(v);


                    break;
                }
                case R.id.paymentCheckLinearLayout : {
                    // 07.18.2022 - add pay for cash, card
                    // 10212023 - 아래 메소드 주석처리
                    // setAddDeleteAddPay(true);


                    pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext);

                    openCashPadOnSliding(false, 0);

                    mFocustEditText = paymentCheckEditText;

                    if (mIsPaymentTypeCheck == "N") {
                        Intent otherpaymentIntent = new Intent(context.getApplicationContext(), PaymentSelectOtherPay.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        //otherpaymentIntent.putExtra("ParentMainMiddleService", this.getClass());
                        otherpaymentIntent.putExtra("selectedpayment", mCheckCompany);
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(otherpaymentIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    } else {
                        // 10212023
                        setAddDeleteAddPay(false);

                        mIsPaymentTypeCheck = "N";
                        v.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        paymentCheckEditText.setText("");
                        mCheckCompany = "";

                        paymentCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
                        paymentCheckTitleTextView.setText("OTHER");

                        mCardUseYN = "N";
                        setPaymentPrice();
                    }

                    break;
                }
                case R.id.paymentPointLinearLayout : {
                    // 02242024 - 추가작업 ---------------------------------------------------------------------
                    // 포인트로 처리할 수 있는 최대/최소 포인트

                    // 02242024 - 추가작업 ---------------------------------------------------------------------


                    // 09282023
                    setClickPointLn(v);

                    break;
                }

                case R.id.pagerNumEv : {
                    mTempPriceValue2 = "";
                    pagerNumEv.setText("");
                    mFocustEditText = pagerNumEv;
                    pagerNumEv.setBackgroundResource(R.drawable.roundlayout_pagerno_edittext2);
                    break;
                }
            }


            return false;
        }
    };

    View.OnClickListener mEditTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setPaymentEditTextForClick(v);
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setPaymentEditTextForClick(v);
            return true;
        }
    };

    public static void openCashPad() {
        Intent paymentCashPad = new Intent(context.getApplicationContext(), PaymentCashPad.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        paymentCashPad.putExtra("paymentBalanceToPay", (mBalanceToPay * -1) + "");
        GlobalMemberValues.logWrite("cashpadlog", "mBalanceToPay : " + mBalanceToPay + "\n");
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(paymentCashPad);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_left);
        }
    }

    public static void openCalculator() {
        Intent paymentCalculator = new Intent(context.getApplicationContext(), PaymentCalculator.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        String tempValue = paymentBalanceEditText.getText().toString();
        paymentCalculator.putExtra("paymentBalanceToPay", tempValue);
        //paymentCalculator.putExtra("paymentBalanceToPay", (mBalanceToPay * -1) + "");
        GlobalMemberValues.logWrite("cashpadlog", "tempValue : " + tempValue + "\n");
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(paymentCalculator);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_right);
        }

    }

    public void setPaymentEditTextForClick(View v) {
        if (!(v.getId() == R.id.paymentBalanceEditText || v.getId() == R.id.paymentTenderedEditText
                || v.getId() == R.id.paymentChangeEditText)) {
            // 현재 터치된 EditText 객체저장
            EditText tempThisEditText = (EditText)v;
            // 임시사용 LinearLayout
            LinearLayout tempLn = null;
            mFocustEditText = tempThisEditText;
            // 키패드(키보드) 안보이게...
            GlobalMemberValues.setKeyPadHide(context, mFocustEditText);
            mTempPriceValue = "";
            setSelectedLinearLayoutBGColor(0);
            switch (v.getId()) {
                case R.id.paymentCashEditText : {
                    tempLn = paymentCashLinearLayout;
                    if (mIsPaymentTypeCash == "N") {
                        mIsPaymentTypeCash = "Y";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                        if (mBalanceToPay < 0) {
                            setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                        }
                    } else {
                        mIsPaymentTypeCash = "N";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        tempThisEditText.setText("");
                    }
                    setPaymentPrice();
                    break;
                }
                case R.id.paymentCardEditText : {
                    tempLn = paymentCardLinearLayout;
                    if (mIsPaymentTypeCard == "N") {
                        mIsPaymentTypeCard = "Y";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                        if (mBalanceToPay < 0) {
                            setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                            mBalanceToPayForCardPoint = mBalanceToPay;
                        }
                    } else {
                        mIsPaymentTypeCard = "N";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        tempThisEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setPaymentPrice();
                    break;
                }
                case R.id.paymentGiftCardEditText : {
                    tempLn = paymentGiftCardLinearLayout;
                    if (mIsPaymentTypeGiftCard == "N") {
                        mIsPaymentTypeGiftCard = "Y";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                        if (mBalanceToPay < 0) {
                            setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                        }
                    } else {
                        mIsPaymentTypeGiftCard = "N";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        tempThisEditText.setText("");
                    }
                    setPaymentPrice();
                    break;
                }
                case R.id.paymentCheckEditText : {
                    tempLn = paymentCheckLinearLayout;
                    if (mIsPaymentTypeCheck == "N") {
                        mIsPaymentTypeCheck = "Y";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                        if (mBalanceToPay < 0) {
                            setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                        }
                    } else {
                        mIsPaymentTypeCheck = "N";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        tempThisEditText.setText("");
                    }
                    setPaymentPrice();
                    break;
                }
                case R.id.paymentPointEditText : {
                    tempLn = paymentPointLinearLayout;
                    if (mIsPaymentTypePoint == "N") {
                        mIsPaymentTypePoint = "Y";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_whitebule2);
                        if (mBalanceToPay < 0) {
                            setPaymentTypeEditText(mFocustEditText, mBalanceToPay);
                            mBalanceToPayForCardPoint = mBalanceToPay;
                        }
                    } else {
                        mIsPaymentTypePoint = "N";
                        tempLn.setBackgroundResource(R.drawable.roundlayout_line_noround);
                        tempThisEditText.setText("");
                        mBalanceToPayForCardPoint = 0.0;
                    }
                    setPaymentPrice();
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
                case R.id.paymentCashEditText : {
                    paymentCashLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.paymentCardEditText : {
                    paymentCardLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.paymentGiftCardEditText : {
                    paymentGiftCardLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.paymentCheckEditText : {
                    paymentCheckLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
                case R.id.paymentPointEditText : {
                    paymentPointLinearLayout.setBackgroundResource(tempColorValueBg);
                    break;
                }
            }

        } else {
            //String tempColorValue = GlobalMemberValues.PAYMENT_SELECTEDPAYMENTTYPE_BACKGROUNDCOLOR;
            int tempColorValueBg = R.drawable.roundlayout_line_whitebule;
            // Cash ----------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(paymentCashEditText.getText().toString())
                    && !(paymentCashEditText.getText().toString().equals("0.00"))) {
                paymentCashLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                paymentCashLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentCashEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Card ----------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(paymentCardEditText.getText().toString())
                    && !(paymentCardEditText.getText().toString().equals("0.00"))) {
                paymentCardLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                paymentCardLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentCardEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // GiftCard ------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(paymentGiftCardEditText.getText().toString())
                    && !(paymentGiftCardEditText.getText().toString().equals("0.00"))) {
                paymentGiftCardLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                paymentGiftCardLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentGiftCardEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Check ---------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(paymentCheckEditText.getText().toString())
                    && !(paymentCheckEditText.getText().toString().equals("0.00"))) {
                paymentCheckLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                paymentCheckLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentCheckEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------
            // Point ---------------------------------------------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(paymentPointEditText.getText().toString())
                    && !(paymentPointEditText.getText().toString().equals("0.00"))) {
                paymentPointLinearLayout.setBackgroundResource(tempColorValueBg);
            } else {
                paymentPointLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
                paymentPointEditText.setText("");
            }
            // ---------------------------------------------------------------------------------------------------------------

        }
    }


    /** 관련직원 리스트 배치하기 ********************************************************/
    public void setPaymentEmployeeInfo() {
        employeeInfoTv2.setText(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName);
        /**
         // 해당 주문과 관련된 직원정보 및 금액 가져오기
         String strQuery = "select empIdx, empName, sum(sPriceBalAmount) as BalAmount, holdcode from temp_salecart " +
         " where holdcode = '" + MainMiddleService.mHoldCode + "' " +
         " group by empidx order by sum(sPriceBalAmount) desc ";
         Cursor paymentEmployeeCursor = dbInit.dbExecuteRead(strQuery);
         // ArrayList 객체 생성
         mGeneralArrayList = new ArrayList<TemporaryPaymentEmployeeInfo>();
         while (paymentEmployeeCursor.moveToNext()) {
         String tempEmpName = paymentEmployeeCursor.getString(1);
         // 직원 이름이 있을 경우에만
         if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempEmpName, " ", ""))) {
         //TemporaryCustomerInfo 객체 생성시 전달할 값을 String 배열로 만든다.
         String paramsTempPaymentEmployeeArray[] = {
         String.valueOf(paymentEmployeeCursor.getInt(0)),
         GlobalMemberValues.getDBTextAfterChecked(paymentEmployeeCursor.getString(1), 1),
         GlobalMemberValues.getDBTextAfterChecked(paymentEmployeeCursor.getString(2), 1),
         GlobalMemberValues.getDBTextAfterChecked(paymentEmployeeCursor.getString(3), 1)
         };
         mTempPaymentEmployee = new TemporaryPaymentEmployeeInfo(paramsTempPaymentEmployeeArray);
         mGeneralArrayList.add(mTempPaymentEmployee);
         }
         }
         mPaymentEmployeeAdapter = new PaymentEmployeeAdapter(context, R.layout.payment_employee, mGeneralArrayList);
         paymentEmployeeListView.setAdapter(mPaymentEmployeeAdapter);
         **/
    }
    /*******************************************************************************/

    // 기프트카드 팝업창 띄우기
    // 09282023 (static 으로 변경)
    public static void setOpenPaymentGiftCard(String paramAmount) {
        Intent paymentGiftCardIntent = new Intent(context.getApplicationContext(), PaymentGiftCard.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentGiftCardIntent.putExtra("ParentMainMiddleService", this.getClass());
        paymentGiftCardIntent.putExtra("paymentGiftCardPopupAmount", paramAmount);
        // -------------------------------------------------------------------------------------

        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
        mActivity.startActivity(paymentGiftCardIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    // 결제처리 메소드
    public static String setPaymentProcess() throws JSONException {
        // 현금 cash 사용이 있을 경우 돈통부터 연다
        double imsiCash = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());
        if (!GlobalMemberValues.isStrEmpty(imsiCash + "") && imsiCash > 0) {
            GlobalMemberValues.b_isDrawerOpen = true;
            CommandButton.openCashDrawer();
        }

        // Restaurant 관련
        if (GlobalMemberValues.now_saletypeisrestaurant) {
            GlobalMemberValues.now_iskitchenprinting = false;
            GlobalMemberValues.now_iskitchenprinting_after = false;

            if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
                GlobalMemberValues.now_iskitchenprinting = true;
                GlobalMemberValues.now_iskitchenprinting_after = true;
            }
        }

        // 레스토랑이지만 togo 주문의 경우 kitchen print 를 해야하지 않나? 라는 의문이 드는 시점.
        // GlobalMemberValues.now_iskitchenprinting = true;
        //

        // 06.10.2022
        // holdcode 로 현재 tableidx 를 가져온다.
        String tableidx_fordel = TableSaleMain.geTableidxBytHoldCode(MainMiddleService.mHoldCode);
        tableidx_fordel = GlobalMemberValues.getReplaceText(tableidx_fordel, " ", "");

        // 최상단 json객체
        jsonroot = new JSONObject();
        // 주방프린터용 json 객체
        jsonroot_kitchen = new JSONObject();

        // json 배열목록을 담아줄 jsonarray 객체생성
        JSONArray jsonList = new JSONArray();
        // 가상DB목록을 JSON배열목록에 출력하기 위한 임의의 JSON오브젝트
        JSONObject jsontmp = null;

        // JSON ----------------------------------------------------------------------
        jsonroot.put("receiptno", mSalesCode);
        jsonroot_kitchen.put("receiptno", mSalesCode);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());

        jsonroot_kitchen.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
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

        jsonroot.put("storename", GlobalMemberValues.getDBTextAfterChecked(storeNameForReceipt,0));  // JSON
        jsonroot.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot.put("storecity", storeCityForReceipt);  // JSON
        jsonroot.put("storestate", storeStateForReceipt);  // JSON
        jsonroot.put("storezip", storeZipForReceipt);  // JSON
        jsonroot.put("storephone", storePhoneForReceipt);  // JSON
        /******************************************************************/

        String tempEmployeeSalesDataForJson = "";

        String returnResult = "";

        /** 결제수단별 결제비율 ***********************************************************************************/
        double useTotalCashRatio = 0.0;
        double useTotalCardRatio = 0.0;
        double useTotalGiftCardRatio = 0.0;
        double useTotalCheckRatio = 0.0;
        double useTotalPointRatio = 0.0;

        String str_useTotalCashRatio = "0.00";
        String str_useTotalCardRatio = "0.00";
        String str_useTotalGiftCardRatio = "0.00";
        String str_useTotalCheckRatio = "0.00";
        String str_useTotalPointRatio = "0.00";

        /**********************************************************************************************************/

        /** 영수증 발행 YES / NO  정보 ****************************************************************************/
        if (paymentReceiptSwitch.isChecked()) {
            receiptYN = "Y";
        } else {
            receiptYN = "N";
        }
        /**********************************************************************************************************/

        /** 카드결제 ON/OFF 정보 **********************************************************************************/
        String cardOnOffInfo = "";
        if (paymentOfflineSwitch.isChecked()) {
            cardOnOffInfo = "on";
        } else {
            cardOnOffInfo = "off";
        }
        /**********************************************************************************************************/

        /** 사용한 결제수단 및 금액저장 ***************************************************************************/
        String temp_total_balance = paymentBalanceEditText.getText().toString();
        double temp_total_balance_double = GlobalMemberValues.getDoubleAtString(temp_total_balance);
        if (temp_total_balance_double < 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "Balance is not available", "Close");
        }

        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount1 = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CASH");
            double tempBillAmount2 = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CARD");
            double tempBillAmount3 = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"GIFTCARD");
            double tempBillAmount4 = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CHECK");
            double tempBillAmount5 = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"POINT");

            double tempBillTotal = tempBillAmount1 + tempBillAmount2 + tempBillAmount3 + tempBillAmount4 + tempBillAmount5;

            temp_total_balance_double += tempBillTotal;
        }


        // Change
        double temp_double_change = GlobalMemberValues.getDoubleAtString(paymentChangeEditText.getText().toString());
//        double ins_double_change = 0;
//        if (GlobalMemberValues.isLastBillList()) {
//            double tempChangeAmount = GlobalMemberValues.getDoubleAtString(
//                    MssqlDatabase.getResultSetValueToString(
//                            " select sum(changeamount) from bill_list_paid " +
//                                    " where salescode = '" + mSalesCode + "' and paytype = 'CASH' "
//                    )
//            );
//
//            if (tempChangeAmount > 0) {
//                ins_double_change = temp_double_change + tempChangeAmount;
//            }
//
//            GlobalMemberValues.logWrite("savejjjlog", "change : " + temp_double_change + "\n");
//        }

        // Cash
        double tempCashAmount = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());
        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CASH");
            if (tempBillAmount > 0) {
                tempCashAmount += tempBillAmount;
            }

            GlobalMemberValues.logWrite("savejjjlog", "cash : " + tempCashAmount + "\n");
        }
        double orginInputCashAmount = tempCashAmount;
        if (tempCashAmount > 0) {
            // 거스름돈이 있을 경우 현금(cash) 입력값에서 거스름돈(change) 를 뺀다.
            if (temp_double_change > 0) {
                tempCashAmount = tempCashAmount - temp_double_change;
            }
            mIsPaymentTypeCash = "Y";
            useTotalCashRatio = tempCashAmount / temp_total_balance_double;
            str_useTotalCashRatio = GlobalMemberValues.setDoubleToString(useTotalCashRatio, 4);

        } else {
            mIsPaymentTypeCash = "N";
        }

        // Card
        double tempCardAmount = GlobalMemberValues.getDoubleAtString(paymentCardEditText.getText().toString());
        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CARD");
            if (tempBillAmount > 0) {
                tempCardAmount += tempBillAmount;
            }

            GlobalMemberValues.logWrite("savejjjlog", "card : " + tempCardAmount + "\n");
        }
        if (tempCardAmount > 0) {
            mIsPaymentTypeCard = "Y";
            useTotalCardRatio = tempCardAmount / temp_total_balance_double;
            str_useTotalCardRatio = GlobalMemberValues.setDoubleToString(useTotalCardRatio, 4);
        } else {
            mIsPaymentTypeCard = "N";
        }

        // GiftCard
        double tempGiftCardAmount = GlobalMemberValues.getDoubleAtString(paymentGiftCardEditText.getText().toString());
        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"GIFTCARD");
            if (tempBillAmount > 0) {
                tempGiftCardAmount += tempBillAmount;
            }

            GlobalMemberValues.logWrite("savejjjlog", "gift card : " + tempGiftCardAmount + "\n");
        }
        if (tempGiftCardAmount > 0) {
            mIsPaymentTypeGiftCard = "Y";
            useTotalGiftCardRatio = tempGiftCardAmount / temp_total_balance_double;
            str_useTotalGiftCardRatio = GlobalMemberValues.setDoubleToString(useTotalGiftCardRatio, 4);
        } else {
            mIsPaymentTypeGiftCard = "N";
            mGiftCardNumberUsed = "";
        }

        // Check
        double tempCheckAmount = GlobalMemberValues.getDoubleAtString(paymentCheckEditText.getText().toString());
        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"CHECK");
            if (tempBillAmount > 0) {
                tempCheckAmount += tempBillAmount;
            }

            GlobalMemberValues.logWrite("savejjjlog", "check : " + tempCheckAmount + "\n");
        }
        if (tempCheckAmount > 0) {
            mIsPaymentTypeCheck = "Y";
            useTotalCheckRatio = tempCheckAmount / temp_total_balance_double;
            str_useTotalCheckRatio = GlobalMemberValues.setDoubleToString(useTotalCheckRatio, 4);
        } else {
            mIsPaymentTypeCheck = "N";
        }

        // Point
        double tempPointAmount = GlobalMemberValues.getDoubleAtString(paymentPointEditText.getText().toString());
        if (GlobalMemberValues.isLastBillList()) {
            double tempBillAmount = GlobalMemberValues.getBillPaidAmountByPayType(mSalesCode,"POINT");
            if (tempBillAmount > 0) {
                tempPointAmount += tempBillAmount;
            }

            GlobalMemberValues.logWrite("savejjjlog", "point : " + tempPointAmount + "\n");
        }
        if (tempPointAmount > 0) {
            mIsPaymentTypePoint = "Y";
            useTotalPointRatio = tempPointAmount / temp_total_balance_double;
            str_useTotalPointRatio = GlobalMemberValues.setDoubleToString(useTotalPointRatio, 4);
        } else {
            mIsPaymentTypePoint = "N";
        }
        /*********************************************************************************************************/

        // 05.09.2022 -----------------------------------------------------------------------------------
        // 결제금액중 other payment (check payment) 금액이 있고, 해당 other payment 의 결제 비중이 100 % 이고,
        // GlobalMemberValues.isOtherpayreceiptprinting()
        if (tempCheckAmount > 0 && useTotalCheckRatio == 1.0 && !GlobalMemberValues.isOtherpayreceiptprinting()) {
            receiptYN = "N";
            GlobalMemberValues.logWrite("jjjreceiptynjjjlog", "receiptYN : " + receiptYN + "\n");
        }
        // ----------------------------------------------------------------------------------------------

        // 결제수단이 모두 N 일 때
        if (mIsPaymentTypeCash == "N" && mIsPaymentTypeCard == "N" && mIsPaymentTypeGiftCard == "N"
                && mIsPaymentTypeCheck == "N" && mIsPaymentTypePoint == "N") {
            double tempBalanceValue = GlobalMemberValues.getDoubleAtString(paymentBalanceEditText.getText().toString());
            if (tempBalanceValue > 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "Choose Payment Type", "Close");
            }
        }

        /** salon_sales 테이블에 필요한 값 ************************************************************************/
        // HoldCode
        String sales_holdCode = MainMiddleService.mHoldCode;
        // 예약코드 (추후 예약관련 기능이 추가되면 작업)
        String sales_reservationCode = "";
        // 스토어인덱스
        String sales_sidx = GlobalMemberValues.STORE_INDEX;
        // 스테이션 코드
        String sales_stcode = GlobalMemberValues.STATION_CODE;
        // 총수량
        int sales_qty = 0;
        // 실결제금액총액 (세금이전)
        double sales_salesBalPriceAmount = 0.0;
        // 총 세금액
        double sales_taxAmount = 0.0;
        // 전체 결제금액 (세금포함)
        double sales_totalAmount = temp_total_balance_double;

        int sales_isBalCashUse = 1;
        if (mIsPaymentTypeCash == "Y") {
            sales_isBalCashUse = 0;
        }
        int sales_isBalCardUse = 1;
        if (mIsPaymentTypeCard == "Y") {
            sales_isBalCardUse = 0;
        }
        int sales_isBalGiftCardUse = 1;
        if (mIsPaymentTypeGiftCard == "Y") {
            sales_isBalGiftCardUse = 0;
        }
        int sales_isBalCheckUse = 1;
        if (mIsPaymentTypeCheck == "Y") {
            sales_isBalCheckUse = 0;
        }
        int sales_isBalPointUse = 1;
        if (mIsPaymentTypePoint == "Y") {
            sales_isBalPointUse = 0;
        }

        // 커미션 지불총액
        double sales_commissionAmount = 0.0;
        // 포인트 지불총액
        double sales_pointAmount = 0.0;


        // 고객아이디
        String sales_customerId = "";
        // 고객이름
        String sales_customerName = "";
        // 고객전화번호
        String sales_customerPhone = "";
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            sales_customerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
            sales_customerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
            sales_customerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
        }

        // 고객코드 --------------------------------------------------------------------------------------------
        String sales_customerPosCode = "";
        if (!GlobalMemberValues.isStrEmpty(sales_customerId)) {
            String tempQuery_ = "select pos_mem_code from salon_member where uid = '" + sales_customerId + "' ";
            sales_customerPosCode = dbInit.dbExecuteReadReturnString(tempQuery_);
        }
        // ------------------------------------------------------------------------------------------------------
        // 사용한 기프트카드번호
        String sales_giftcardNumberUsed = mGiftCardNumberUsed;
        // 기프트 카드 사용한 금액
        String sales_giftcardPriceUsed = paymentGiftCardEditText.getText().toString();
        // 전체 할인/추가한 금액
        double totalDiscountExtraPrice = 0.0;
        // All Discount 또는 Extra 금액
        String allDiscountExtraPrice = "0.0";
        // All Discount 또는 Extra 문자열
        String allDiscountExtraStr = "";
        // Each Discount 또는 Extra 금액
        double eachDiscountExtraPrice = 0.0;
        // 전체 할인금액
        double totalDiscountPrice = 0.0;
        // 전체 추가금액
        double totalExtraPrice = 0.0;

        // 클라우드 업로드 여부
        int isCloudUpload = 0;          // 0 : 업로드 이전      1 : 업로드 완료
        /*********************************************************************************************************/

        // 쿼리문자열을 담을 벡터 변수생성
        strInsertQueryVec = null;
        strInsertQueryVec = new Vector<String>();

        // 아이템별 포인트 합계 금액구하기
        double tempSavedPointTotal = 0;
        for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
            String item_pointAmount = tempSaleCart.mSPointAmount;
            // 전체주문 포인트 총액
            tempSavedPointTotal += GlobalMemberValues.getDoubleAtString(item_pointAmount);
        }

        /** 포인트 적립전 포인트로 결제한 내역이 있는지 체크하여 ****************************************************/
        /** 결제한 내역이 있을 경우 결제한 내역비율만큼은 적립포인트에서 차감하여 지급한다 ****************************/
        double savePointAmount_forsales = 0.0;
        if (!GlobalMemberValues.isStrEmpty(sales_customerId) && tempSavedPointTotal > 0) {
            double savePointAmount = 0.0;

            // 포인트 결제가 포함되어 있을 경우 포인트를 지급할지 여부 체크
            String tempSavePointInPointPay = dbInit.dbExecuteReadReturnString(
                    "select pointpaysavepointyn from salon_storestationsettings_system");
            if (GlobalMemberValues.isStrEmpty(tempSavePointInPointPay)) {
                tempSavePointInPointPay = "N";
            }

            if (tempSavePointInPointPay == "N" || tempSavePointInPointPay.equals("N")) {
                savePointAmount = tempSavedPointTotal - (tempSavedPointTotal * useTotalPointRatio);
            } else {
                savePointAmount = tempSavedPointTotal;
            }

            if (!GlobalMemberValues.isStrEmpty(savePointAmount + "") && savePointAmount > 0 && !GlobalMemberValues.isStrEmpty(sales_customerId)) {
                // 02242024 - 추가작업 ---------------------------------------------------------------------
                // 회원 레벨별 포인트 비율 ----------------------------------------------------------------------
                // grade 부터 구한다.
                String tempGrade = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select grade from member1 where uid = '" + sales_customerId + "' "
                );
                double memPointRatio = 1.0;
                if (!GlobalMemberValues.isStrEmpty(tempGrade)) {
                    memPointRatio = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select pointratio from salon_storememberlevel where idx = '" + tempGrade + "' "
                    ));
                }
                savePointAmount = savePointAmount * memPointRatio;
                // 02242024 - 추가작업 ---------------------------------------------------------------------


                strUpdSqlQuery = "update salon_member set mileage = mileage + " + GlobalMemberValues.getStringFormatNumber(savePointAmount, "2") +
                        " where uid = '" + sales_customerId + "' ";
                strInsertQueryVec.addElement(strUpdSqlQuery);

                String temmpEmpName = "";
                if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                    temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                }

                strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                        ") values ( " +
                        " '" + "Stored by Sales - " + mSalesCode + "', " +
                        " '" + GlobalMemberValues.getStringFormatNumber(savePointAmount, "2") + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                        " '" + "1" + "', " +            // 1: 적립        2 : 사용
                        " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                        " '" + GlobalMemberValues.getDBTextAfterChecked(temmpEmpName, 0) + "', " +
                        " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strInsSqlQuery);
            }

            savePointAmount_forsales = savePointAmount;
            // 210111 추가 earned
            GlobalMemberValues.POINT_EARNED = savePointAmount_forsales;
        }
        // point
        jsonroot.put("earnedpoint",GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.POINT_EARNED,"2"));
        /*********************************************************************************************************/

        // 세일일시를 지정했을 경우
        String sqlQuery_add1 = "";
        String sqlQuery_add2 = "";
        if (GlobalMemberValues.isDifferentSaleDateToday()) {
            String tempSearchDate = "";
            String[] tempSettingSaleDateArr = GlobalMemberValues.SETTING_SALE_DATE.split("-");
            if (tempSettingSaleDateArr.length > 0) {
                tempSearchDate = tempSettingSaleDateArr[2] + "-" + tempSettingSaleDateArr[0] + "-" + tempSettingSaleDateArr[1];

                sqlQuery_add1 = ", saleDate ";
                sqlQuery_add2 = ", DATETIME('" + tempSearchDate + " 23:59:59') ";
            }
        }

        // 테이블에서 주문한 내역인지 여부
        boolean isOrderedOnTable = false;
        int tempTbidxCnt = 0;

        /** salon_sales_detail 테이블 저장 관련 *******************************************************************/
        double tempTotalAmountOrg = 0.0;
        double tempTaxAmountOrg = 0.0;

        double tempSalesAmount = 0.0;                   // Discount, Extra 반영전 상품 총액
        String employeeNamesForJSON = "";

        int itemDetailCount = 0;
        String itemDetailText = "";
        sales_pointAmount = 0;
        for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
            // 카테고리코드(인덱스)
            String item_categoryCode = tempSaleCart.mMidx;
            String temp_item_categoryName = GlobalMemberValues.getDBTextAfterChecked(tempSaleCart.mSvcCategoryName, 0);
            String item_categoryName = GlobalMemberValues.getDBTextAfterChecked(tempSaleCart.mSvcCategoryName, 0);
            String item_categoryName2 = "";
            if (temp_item_categoryName.contains("-AAA-")){
                item_categoryName = temp_item_categoryName.split("-AAA-")[0];
                item_categoryName2 = temp_item_categoryName.split("-AAA-")[1];
            }

            String item_itemIdx = tempSaleCart.mSvcidx;
            String item_itemName = GlobalMemberValues.getDBTextAfterChecked(tempSaleCart.mSvcName, 0);
            String item_itemFileName = tempSaleCart.mSvcFileName;
            String item_itemFilePath = tempSaleCart.mSvcFilePath;
            String item_servicePositionNo = tempSaleCart.mSvcPositionNo;
            String item_qty = tempSaleCart.mSQty;

            String item_salesOrgPrice = tempSaleCart.mSvcOrgPrice;
            String item_salesPrice = tempSaleCart.mSPrice;
            String item_salesPriceAmount = tempSaleCart.mSPriceAmount;

            String item_salesBalPrice = "0";
            String item_salesBalPriceAmount = tempSaleCart.mSPriceBalAmount;
            double item_salesBalPriceAmount_double = GlobalMemberValues.getDoubleAtString(item_salesBalPriceAmount);
            if (item_salesBalPriceAmount_double > 0 && GlobalMemberValues.getIntAtString(item_qty) > 0) {
                item_salesBalPrice = GlobalMemberValues.getStringFormatNumber(
                        item_salesBalPriceAmount_double / GlobalMemberValues.getIntAtString(item_qty), "2");
            }

            String item_sales_tax = tempSaleCart.mSTax;
            String item_sales_taxAmount = tempSaleCart.mSTaxAmount;

            String item_sales_taxAmount_org = tempSaleCart.mSTaxAmount_org;

            if (!GlobalMemberValues.isStrEmpty(item_sales_taxAmount_org)) {
                if (GlobalMemberValues.isCheckDouble(item_sales_taxAmount_org)) {
                    double dbl_item_sales_taxAmount_org = GlobalMemberValues.getDoubleAtString(item_sales_taxAmount_org);
                    if (!Double.isInfinite(dbl_item_sales_taxAmount_org) && !Double.isNaN(dbl_item_sales_taxAmount_org)) {
                        tempTaxAmountOrg += dbl_item_sales_taxAmount_org;
                    }
                }
            }

            String item_sales_totalAmount = tempSaleCart.mSTotalAmount;

            String item_sales_totalAmount_org = tempSaleCart.mSTotalAmount_org;

            if (!GlobalMemberValues.isStrEmpty(item_sales_totalAmount_org)) {
                if (GlobalMemberValues.isCheckDouble(item_sales_totalAmount_org)) {
                    double dbl_item_sales_totalAmount_org = GlobalMemberValues.getDoubleAtString(item_sales_totalAmount_org);
                    if (!Double.isInfinite(dbl_item_sales_totalAmount_org) && !Double.isNaN(dbl_item_sales_totalAmount_org)) {
                        tempTotalAmountOrg += dbl_item_sales_totalAmount_org * GlobalMemberValues.getIntAtString(item_qty);
                    }
                }
            }

            String item_commissionRatioType = tempSaleCart.mCommissionRatioType;
            String item_commissionRatio = tempSaleCart.mCommissionRatio;
            String item_commission = tempSaleCart.mSCommission;
            String item_commissionAmount = tempSaleCart.mSCommissionAmount;

            String item_pointRatio = tempSaleCart.mPointRatio;
            String item_point = tempSaleCart.mSPoint;
            String item_pointAmount = tempSaleCart.mSPointAmount;

            // 포인트 재계산 ---------------------------------------------------------------------------------------------------------------------
            // 실제 지급될 포인트 총액대비하여 현재 아이템의 포인트를 계산한다.
            double tempItemPointAmount = 0;
            if (savePointAmount_forsales > 0 && GlobalMemberValues.getDoubleAtString(item_pointAmount) > 0) {
                tempItemPointAmount = savePointAmount_forsales * (GlobalMemberValues.getDoubleAtString(item_pointAmount) / tempSavedPointTotal);
            }
            item_pointAmount = String.valueOf(tempItemPointAmount);
            // ------------------------------------------------------------------------------------------------------------------------------------

            int item_isSale = 1;
            if (tempSaleCart.mSSaleYN == "Y") {
                item_isSale = 0;
            }

            String item_employeeIdx = tempSaleCart.mEmpIdx;
            String item_employeeName = tempSaleCart.mEmpName;
            // JSON 객체 저장용 직원이름 ------------------------
            if (!GlobalMemberValues.isStrEmpty(employeeNamesForJSON)) {
                if (employeeNamesForJSON.indexOf(item_employeeName) == -1) {
                    employeeNamesForJSON = employeeNamesForJSON  + ", " + item_employeeName;
                }
            } else {
                employeeNamesForJSON = item_employeeName;
            }
            // ------------------------------------------------

            String item_giftcardNumberToSave = tempSaleCart.mGiftCardNumber;
            String item_giftcardSavePriceToSave = tempSaleCart.mGiftCardSavePrice;

            GlobalMemberValues.logWrite("GiftCardInfo", "payment - giftcardnumber : " + item_giftcardNumberToSave + "\n");
            GlobalMemberValues.logWrite("GiftCardInfo", "payment - giftcardprice : " + item_giftcardSavePriceToSave + "\n");

            String item_couponNumber = tempSaleCart.couponNumber;

            String item_eachDiscountExtraPrice = tempSaleCart.selectedDcExtraPrice;
            String item_eachDiscountExtraType = tempSaleCart.selectedDcExtraType;

            String item_eachDiscountExtraStr = tempSaleCart.eachDcExtraStr;

            GlobalMemberValues.logWrite("discountextralogjjj_1", "-------------- " + itemDetailCount + "-----------------------" + "\n");
            GlobalMemberValues.logWrite("discountextralogjjj_1", "item_eachDiscountExtraPrice : " + item_eachDiscountExtraPrice + "\n");
            GlobalMemberValues.logWrite("discountextralogjjj_1", "item_eachDiscountExtraType : " + item_eachDiscountExtraType + "\n");
            GlobalMemberValues.logWrite("discountextralogjjj_1", "eachDcExtraStr : " + item_eachDiscountExtraStr + "\n");

            String item_saveType = tempSaleCart.mSaveType;
            String item_isQuickSale = tempSaleCart.mQuickSaleYN;

            String item_isQuickKitchenPrintingYN = tempSaleCart.mQuickSaleKitchenPrintingYN;

            sales_reservationCode = tempSaleCart.mRcode;            // 예약코드 (예약정보에서 가져왔을 경우)

            String item_selectedDcExtraParentTempSaleCartIdx = tempSaleCart.selectedDcExtraParentTempSaleCartIdx;

            String item_mTaxExempt = tempSaleCart.mTaxExempt;
            if (GlobalMemberValues.isStrEmpty(item_mTaxExempt)) {
                item_mTaxExempt = "";
            }

            String item_optionTxt = tempSaleCart.optionTxt;
            String item_optionPrice = tempSaleCart.optionprice;

            String item_addtionalTxt1 = tempSaleCart.additionalTxt1;
            String item_addtionalPrice1 = tempSaleCart.additionalprice1;

            String item_addtionalTxt2 = tempSaleCart.additionalTxt2;
            String item_addtionalPrice2 = tempSaleCart.additionalprice2;

            String item_kitchenmemo = tempSaleCart.kitchenMemo;
            if (GlobalMemberValues.isStrEmpty(item_kitchenmemo)) {
                item_kitchenmemo = "nokitchenmemo";
            }

            String item_discountbuttonname = tempSaleCart.discountButtonName;
            if (GlobalMemberValues.isStrEmpty(item_discountbuttonname)) {
                item_discountbuttonname = "";
            }

            String dcextraforreturn = GlobalMemberValues.getDoubleAtString(tempSaleCart.mDcExtraForReturn) + "";

            GlobalMemberValues.logWrite("discountchecklog", "dcextraforreturn : " + dcextraforreturn + "\n");

            String temp_kitchenPrintYN = dbInit.dbExecuteReadReturnString(
                    "select kitchenprintyn from salon_storeservice_sub where idx = '" + item_itemIdx + "' ");
            if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                temp_kitchenPrintYN = "N";
            }

            /**
             String temp_kitchenprintnum = "0";
             if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y")) {
             temp_kitchenprintnum = dbInit.dbExecuteReadReturnString(
             "select kitchenprintnum from salon_storeservice_sub where idx = '" + item_itemIdx + "' ");
             }
             if (GlobalMemberValues.isStrEmpty(temp_kitchenprintnum)) {
             temp_kitchenprintnum = "0";
             }
             **/

            String item_tempSaleCartIdx = tempSaleCart.tempSaleCartIdx;     // temp_salecart idx 값


            // discount 관련 추가 - 11102021

            // tableidx, mergednum, subtablenum, billnum
            // selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice
            String item_tableidx = "", item_mergednum = "", item_subtablenum = "", item_billnum = "", item_togodelitype = "H";
            String item_selectedDcExtraAllEach = "" , item_selectedDcExtraType = "", item_dcextratype = "", item_dcextravalue = "", item_selectedDcExtraPrice = "", item_labelPrintedYN = "";
            String item_togotype = "";
            String item_kitchenprintedyn = "";
            if (!GlobalMemberValues.isStrEmpty(item_tempSaleCartIdx)) {
                // db 처리전에 먼저 temp_salecart_ordered 테이블에 데이터를 저장한다. (temp_salecart_ordered : 추후 결제수단 변경을 통한 재결제를 위한 테이블)
                GlobalMemberValues.saveTempSaleCartOrdered(item_tempSaleCartIdx);

                String strTempSaleCartSql = " select tableidx, mergednum, subtablenum, billnum, togodelitype, " +
                        " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, labelprintedyn, togotype, kitchenprintedyn " +
                        " from temp_salecart " +
                        " where idx = '" + item_tempSaleCartIdx + "' ";
                ResultSet tempCursor = MssqlDatabase.getResultSetValue(strTempSaleCartSql);
                try {
                    while (tempCursor.next()) {
                        item_tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);
                        item_mergednum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,1), 1);
                        item_subtablenum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,2), 1);
                        item_billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,3), 1);
                        item_togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,4), 1);

                        item_selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,5), 1);
                        item_selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,6), 1);
                        item_dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,7), 1);
                        item_dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,8), 1), "2");
                        item_selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,9), 1), "2");

                        item_labelPrintedYN = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,10), 1);

                        item_togotype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,11), 1);

                        item_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,12), 1);

                    }
                    tempCursor.close();
                } catch (Exception e) {
                }
            }

            if (!GlobalMemberValues.isStrEmpty(item_tableidx)) {
                //isOrderedOnTable = true;
                tempTbidxCnt++;
            }

            String temp_kitchenprintnum = "0";
            if (item_isQuickSale == "Y" || item_isQuickSale.equals("Y")) {
                if (item_isQuickKitchenPrintingYN == "Y" || item_isQuickKitchenPrintingYN.equals("Y")) {
                    temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumberForQuickSale(item_categoryCode);
                } else {
                    temp_kitchenprintnum = "NOPRINT";
                }
            } else {
                temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(item_itemIdx);
            }

            // 적립할 기프트카드 금액
            double saveGiftCardMoney
                    = GlobalMemberValues.getDoubleAtString(item_giftcardSavePriceToSave) * GlobalMemberValues.getDoubleAtString(item_qty);

            // 0 : 정상주문      1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소
            int item_status = 0;

            // Discount, Extra 가 아닌 item 만 저장한다.
            String tempUpperItemName = item_itemName.toUpperCase();
            GlobalMemberValues.logWrite("paymentlogtest", "tempUpperItemName : " + tempUpperItemName + "\n");
            GlobalMemberValues.logWrite("paymentlogtest", "selectedDcExtraParentTempSaleCartIdx : " + item_selectedDcExtraParentTempSaleCartIdx + "\n");
            //if (tempUpperItemName.indexOf("DISCOUNT") == -1 && tempUpperItemName.indexOf("EXTRA") == -1 )     // 이전에 사용하던 것..
            if (GlobalMemberValues.isStrEmpty(item_selectedDcExtraParentTempSaleCartIdx)
                    && tempUpperItemName.indexOf("ALL DISCOUNT") == -1 && tempUpperItemName.indexOf("ALL EXTRA") == -1 ) {

                // 현재 주문의 배송타입(식사장소타입)이 T, D 일 경우에는 item_togodelitype 을 해당 정보로 저장한다.
                String tempDeliTakeType = MssqlDatabase.getResultSetValueToString(
                        "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + MainMiddleService.mHoldCode + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempDeliTakeType)) {
                    tempDeliTakeType = "H";
                }
                if (!tempDeliTakeType.equals("H")) {
                    item_togodelitype = tempDeliTakeType;
                }

                // togo delivery 추가
                switch (tempSaleCart.mServiceType) {
                    case "TO GO" :
                        item_togodelitype = "T";
                        break;
                    case "DELIVERY" :
                        item_togodelitype = "D";
                        break;
                    case "HERE" :
                        item_togodelitype = "H";
                        break;
                }


                // 05052023
                String tempWmuseyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select wmuseyn from salon_storeservice_sub where idx = '" + item_itemIdx + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempWmuseyn)) {
                    tempWmuseyn = "N";
                }


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
                        " serverIdx, serverName, " +
                        " giftcardNumberToSave, giftcardSavePriceToSave, " +
                        " couponNumber, " +
                        " eachDiscountExtraPrice, eachDiscountExtraType, eachDiscountExtraStr, " +
                        " dcextraforreturn, " +
                        " saveType, isQuickSale, isSale, status, isCloudUpload,  " +
                        " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                        " checkcompany, memoToKitchen, discountbuttonname, " +
                        " tableidx, mergednum, subtablenum, billnum, tablename, togodelitype, togotype, labelPrintedYN, kitchenPrintedYN, wmodyn " + sqlQuery_add1 +
                        " ) values ( " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_holdCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_reservationCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_sidx,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_stcode,0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_categoryCode, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_categoryName, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemFileName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemFilePath, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_servicePositionNo, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_qty,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesOrgPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesPriceAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesBalPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_salesBalPriceAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_tax, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_taxAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_sales_totalAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionRatioType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionRatio, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commission, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_commissionAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_pointRatio, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_point, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_pointAmount, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerPhone, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerPosCode, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_giftcardNumberToSave, 0) + "', " +
                        " '" + saveGiftCardMoney + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_couponNumber, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraPrice, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_eachDiscountExtraStr, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(dcextraforreturn, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_saveType, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_isQuickSale, 0) + "', " +
                        " '" + item_isSale + "', " +
                        " '" + item_status + "', " +
                        " '" + isCloudUpload + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_optionTxt, 0) + "', " +
                        " '" + item_optionPrice + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_addtionalTxt1, 0) + "', " +
                        " '" + item_addtionalPrice1 + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_addtionalTxt2, 0) + "', " +
                        " '" + item_addtionalPrice2 + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(mCheckCompany, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_kitchenmemo, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_discountbuttonname, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_tableidx, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_mergednum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_subtablenum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_billnum, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(TableSaleMain.mTableName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(item_togodelitype, 0) + "', " +

                        " '" + item_togotype + "', " +

                        " '" + item_labelPrintedYN + "', " +
                        " '" + item_kitchenprintedyn + "', " +

                        // 05052023
                        " '" + GlobalMemberValues.getDBTextAfterChecked(tempWmuseyn, 0) + "' " +

                        sqlQuery_add2 +             // 세일일시 지정일 때...
                        ")";

                // 전체주문 총수량
                sales_qty += GlobalMemberValues.getIntAtString(item_qty);
                // 전체주문 구매총액 (세금 미포함)
                sales_salesBalPriceAmount += item_salesBalPriceAmount_double;
                // 전체주문 세금총액
                sales_taxAmount += GlobalMemberValues.getDoubleAtString(item_sales_taxAmount);
                // 전체주문 커미션총액
                sales_commissionAmount += GlobalMemberValues.getDoubleAtString(item_commissionAmount);
                // 전체주문 포인트 총액
                sales_pointAmount += GlobalMemberValues.getDoubleAtString(item_pointAmount);
                // 전체주문 할인/추가 총액
                if (item_eachDiscountExtraType.equals("DC")) {
                    totalDiscountExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                    totalDiscountPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                } else {
                    totalDiscountExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                    totalExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                }
                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalDiscountExtraPrice : " + totalDiscountExtraPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalDiscountPrice : " + totalDiscountPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalExtraPrice : " + totalExtraPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "eachDcExtraStr : " + item_eachDiscountExtraStr + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");

                GlobalMemberValues.logWrite("PAYMENTINSERTSQL", "INS QUERY : " + strInsSqlQuery_item);

                // salon_sales_detail 입력쿼리를 백터 strInsertQueryVec 에 담는다.
                if (GlobalMemberValues.isLastBillList()) {
                    strInsertQueryVec.addElement(strInsSqlQuery_item);
                }

                // 상품(Product) 구매시 현재수량(onhand) 에서 구매수량만큼 차감한다. ---------------
                if (item_saveType == "1" || item_saveType.equals("1")) {
                    strUpdSqlQuery_item = " update salon_storeproduct set onhand = onhand - " + GlobalMemberValues.getIntAtString(item_qty) +
                            " where idx = '" + item_itemIdx + "' ";
                    if (GlobalMemberValues.isLastBillList()) {
                        strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    }

                }
                // ---------------------------------------------------------------------------

                // 사용한 쿠폰이 있을 경우 쿠폰을 사용처리한다. ------------------------------------
                if (!GlobalMemberValues.isStrEmpty(item_couponNumber)) {
                    strUpdSqlQuery_item = "update coupon_issue_history set " +
                            " usedyn = 'Y' " +
                            " where barcode = '" + item_couponNumber + "' ";
                    strInsertQueryVec.addElement(strUpdSqlQuery_item);
                }
                // ---------------------------------------------------------------------------------

                // 적립할 기프트카드가 있으면 해당 기프트카드를 등록한다. ----------------------------------------------------------
                if (!GlobalMemberValues.isStrEmpty(item_giftcardNumberToSave)) {
                    String tempFirstSaveGiftCard_YN = "N";
                    String tempGiftCardName = "";

                    String existGiftCardNumber = dbInit.dbExecuteReadReturnString(
                            "select idx from salon_storegiftcard_number where gcNumber = '" + GlobalMemberValues.getDBTextAfterChecked(item_giftcardNumberToSave, 0) + "' ");
                    // 기프트카드가 존재하지 않을 경우에만 등록처리한다.
                    if (GlobalMemberValues.isStrEmpty(existGiftCardNumber)) {
                        // salon_storegiftcard_number 테이블 저장
                        strInsSqlQuery_item = "insert into salon_storegiftcard_number (" +
                                " sidx, gcNumber, customerId, customerName, remainingPrice " +
                                " ) values ( " +
                                " '" + sales_sidx + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(item_giftcardNumberToSave, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                                " '" + saveGiftCardMoney + "' " +
                                ")";
                        strInsertQueryVec.addElement(strInsSqlQuery_item);

                        tempFirstSaveGiftCard_YN = "Y";
                        tempGiftCardName = item_itemName;
                    } else { // 존재하는 기프트카드이면 발란스 수정(추가)
                        strUpdSqlQuery_item = "update salon_storegiftcard_number set " +
                                " remainingPrice = remainingPrice + " + saveGiftCardMoney +
                                " where gcNumber = '" + item_giftcardNumberToSave + "' ";
                        strInsertQueryVec.addElement(strUpdSqlQuery_item);
                    }

                    // salon_storegiftcard_number_history 테이블 저장
                    strInsSqlQuery_item = "insert into salon_storegiftcard_number_history (" +
                            " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                            " customerId, customerName, addUsePrice, addUseType, codeforupload, firstsaveyn, giftcardname, salesCode " +
                            " ) values ( " +
                            " '" + sales_sidx + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(item_giftcardNumberToSave, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(item_itemIdx, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeIdx, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(item_employeeName, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                            " '" + saveGiftCardMoney + "', " +
                            " '" + "A" + "', " +         // 기프트카드 구매이므로 적립인 'A' 로 저장 (사용시 'U')
                            " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempFirstSaveGiftCard_YN, 0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempGiftCardName, 0) + "', " +
                            " '" + mSalesCode + "' " +
                            ")";
                    strInsertQueryVec.addElement(strInsSqlQuery_item);
                }

                jsontmp = new JSONObject();
                jsontmp.put("itemcartidx", item_itemIdx);
                jsontmp.put("itemname", item_itemName);
                jsontmp.put("itemqty", item_qty);
                jsontmp.put("itemprice", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(item_salesPrice), "2"));
                jsontmp.put("itemamount", GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(item_salesPriceAmount), "2"));
                jsontmp.put("itemdcextratype", item_eachDiscountExtraType);
                jsontmp.put("itemdcextraprice", item_eachDiscountExtraPrice);
                jsontmp.put("itemCategoryName", item_categoryName);
                jsontmp.put("itemCategoryName2", item_categoryName2);
                jsontmp.put("billcartidx",item_tempSaleCartIdx);

                if (!GlobalMemberValues.isStrEmpty(item_eachDiscountExtraStr)) {
                    jsontmp.put("itemdcextrastr", item_eachDiscountExtraStr);
                } else {
                    jsontmp.put("itemdcextrastr", "");
                }
                GlobalMemberValues.logWrite("paymentlogtest", "itemdcextrastr : " + item_eachDiscountExtraStr + "\n");

                jsontmp.put("itemtaxexempt", item_mTaxExempt);

                // 옵션 & 추가사항
                jsontmp.put("optiontxt", item_optionTxt);
                jsontmp.put("optionprice", item_optionPrice);
                jsontmp.put("additionaltxt1", item_addtionalTxt1);
                jsontmp.put("additionalprice1", item_addtionalPrice1);
                jsontmp.put("additionaltxt2", item_addtionalTxt2);
                jsontmp.put("additionalprice2", item_addtionalPrice2);
                jsontmp.put("kitchenmemo", item_kitchenmemo);
                jsontmp.put("togodelitype", item_togodelitype);
                jsontmp.put("labelprintedyn", item_labelPrintedYN);

                // 04062023
                jsontmp.put("itemcategoryidx", item_categoryCode);

                GlobalMemberValues.logWrite("kitchenMemoJsonLog", "item string : " + jsontmp.toString() + "\n");

                jsonList.put(jsontmp);

                // 주방프린팅 여부와 주방프린터 번호 구하기
                String tempKitchenPrinterNumber = "0";
                if (item_isQuickSale == "Y" || item_isQuickSale.equals("Y")) {
                    if (item_isQuickKitchenPrintingYN == "Y" || item_isQuickKitchenPrintingYN.equals("Y")) {
                        tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumberForQuickSale(item_categoryCode);
                    } else {
                        tempKitchenPrinterNumber = "NOPRINT";
                    }
                } else {
                    tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(item_itemIdx);
                }

                tempSalesAmount += GlobalMemberValues.getDoubleAtString(item_salesPriceAmount);

                // 메뉴 아이템별 수령방법 구하기
                String temp_togodelitype = "H";
                if (!GlobalMemberValues.isStrEmpty(item_selectedDcExtraParentTempSaleCartIdx)) {
                    temp_togodelitype = MssqlDatabase.getResultSetValueToString(
                            " select togodelitype from temp_salecart where idx = '" + item_selectedDcExtraParentTempSaleCartIdx + "' "
                    );
                }

                // 08102023
                // alt name 구하기
                String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicenamealt from salon_storeservice_sub where idx = '" + item_itemIdx + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                    temp_itemName_alt = "";
                }

                String item_itemname_optionadd = item_itemName +
                        "-ANNIETTASU-" + item_optionTxt +
                        "-ANNIETTASU-" + item_addtionalTxt1 +
                        "-ANNIETTASU-" + item_addtionalTxt2 +
                        "-ANNIETTASU-" + item_itemIdx +
                        "-ANNIETTASU-" + tempKitchenPrinterNumber +
                        "-ANNIETTASU-" + item_kitchenmemo +
                        // discount 관련 추가 - 11102021
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(item_optionPrice, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(item_addtionalPrice1, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(item_addtionalPrice2, "2") +
                        "-ANNIETTASU-" + item_selectedDcExtraAllEach +       // ALL 또는 EACH
                        "-ANNIETTASU-" + item_selectedDcExtraType +          // DC 또는 EX
                        "-ANNIETTASU-" + item_dcextratype +          // $ 또는 %
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(item_dcextravalue, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(item_selectedDcExtraPrice, "2") +
                        "-ANNIETTASU-" + item_togodelitype +
                        "-ANNIETTASU-" + item_categoryName +

                        // 08102023
                        "-ANNIETTASU-" + "" +          // additem

                        "-ANNIETTASU-" + item_labelPrintedYN +        // 라벨 프린트 했는지 YN

                        // 08102023
                        "-ANNIETTASU-" + temp_itemName_alt;

                GlobalMemberValues.logWrite("savetypelog", "savetype : " + item_saveType + "\n");
                GlobalMemberValues.logWrite("item_itemname_optionaddlog", "item_itemname_optionadd : " + item_itemname_optionadd + "\n");
                GlobalMemberValues.logWrite("item_itemname_optionaddlog", "quick sale : " + item_isQuickSale + "\n");
                GlobalMemberValues.logWrite("item_itemname_optionaddlog", "quick sale : " + item_categoryCode + "\n");

                // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                if (item_saveType == "0" || item_saveType.equals("0")) {
                    if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y") || item_isQuickSale == "Y" || item_isQuickSale.equals("Y")) {
                        String tempItemDetailTxt = item_itemname_optionadd + "-JJJ-" + item_qty +
                                "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + item_categoryName + "-JJJ-" + "" +
                                "-JJJ-" + item_salesPrice + "-JJJ-" + item_salesPriceAmount + "-JJJ-" + item_sales_tax + "-JJJ-" + item_sales_taxAmount +
                                "-JJJ-" + item_optionPrice + "-JJJ-" + item_addtionalPrice1 + "-JJJ-" + item_addtionalPrice2 + "-JJJ-" + temp_togodelitype;
                        if (itemDetailCount == 0) {
                            itemDetailText = tempItemDetailTxt;
                        } else {
                            itemDetailText = itemDetailText + "-WANHAYE-" + tempItemDetailTxt;
                        }
                        itemDetailCount++;
                    }
                }

                GlobalMemberValues.logWrite("quickkitchenprintinglog", "temp_kitchenprintnum : " + temp_kitchenprintnum + "\n");
                //GlobalMemberValues.logWrite("savetypelog", "item_isQuickSale : " + item_isQuickSale + "\n");
                //GlobalMemberValues.logWrite("savetypelog", "itemDetailText : " + itemDetailText + "\n");

                // ------------------------------------------------------------------------------------------------------------------
            } else {
                if (tempUpperItemName.indexOf("ALL DISCOUNT") > -1 || tempUpperItemName.indexOf("ALL EXTRA") > -1 ) {
                    allDiscountExtraPrice = item_eachDiscountExtraPrice;
                    allDiscountExtraStr = item_itemName;
                    GlobalMemberValues.logWrite("paymentlogtest", "allDiscountExtraPrice : " + allDiscountExtraPrice + "\n");

                    if (item_eachDiscountExtraType.equals("DC")) {
                        totalDiscountPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);
                        totalExtraPrice = 0.0;
                    } else {
                        totalDiscountPrice = 0.0;
                        totalExtraPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);
                    }
                }
                if (tempUpperItemName.indexOf("EACH DISCOUNT") > -1 || tempUpperItemName.indexOf("EACH EXTRA") > -1 ) {
                    eachDiscountExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                    GlobalMemberValues.logWrite("paymentlogtest", "eachDiscountExtraPrice : " + eachDiscountExtraPrice + "\n");

                    if (item_eachDiscountExtraType.equals("DC")) {
                        totalDiscountExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                        totalDiscountPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                    } else {
                        totalDiscountExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                        totalExtraPrice += GlobalMemberValues.getDoubleAtString(item_eachDiscountExtraPrice);
                    }
                }

                // 전체주문 할인/추가 총액

                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalDiscountExtraPrice : " + totalDiscountExtraPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalDiscountPrice : " + totalDiscountPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "totalExtraPrice : " + totalExtraPrice + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "eachDcExtraStr : " + item_eachDiscountExtraStr + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");
                GlobalMemberValues.logWrite("discountextralogjjj_1", "---------------------------------------" + "\n");
            }

        }

        if (tempTbidxCnt > 0) {
            isOrderedOnTable = true;
        }
        /*********************************************************************************************************/

//        // JSON itemCategoryName 별 정렬
//        ArrayList<JSONObject> array = new ArrayList<JSONObject>();
//        for (int i = 0; i < jsonList.length(); i++) {
//            try {
//                array.add(jsonList.getJSONObject(i));
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        Collections.sort(array, new Comparator<JSONObject>() {
//
//            @Override
//            public int compare(JSONObject lhs, JSONObject rhs) {
//                // TODO Auto-generated method stub
//                try {
//                    return (lhs.getString("itemCategoryName").toLowerCase().compareTo(rhs.getString("itemCategoryName").toLowerCase()));
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    return 0;
//                }
//            }
//        });
//        JSONArray jsonArray = new JSONArray();
//        for (int i = 0; i < array.size(); i++) {
//            try {
//                jsonArray.put(array.get(i));
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        jsonList = jsonArray;
//        // JSON itemCategoryName 별 정렬

        // JSON (구매 서비스 리스트) ---------------------------------------------------
        jsonroot.put("saleitemlist", jsonList);

        jsonroot_kitchen.put("saleitemlist", itemDetailText);
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("employeename", employeeNamesForJSON);
        // ---------------------------------------------------------------------------

        /** Table Idx 구하기 **************************************************************************************/
        String tempTableIdx = MssqlDatabase.getResultSetValueToString(
                " select tableidx from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "'"
        );
        if (GlobalMemberValues.isStrEmpty(tempTableIdx)) {
            tempTableIdx = "";
        }
        /*********************************************************************************************************/

        /** salon_sales_detail 테이블 저장 관련 *******************************************************************/
        // temp_salecart_deliveryinfo 테이블에서 배송관련정보 가져온다.
        String temp_customerId = "";
        String temp_customerName = "";
        String temp_customerPhone = "";
        String temp_customerEmail = "";
        String temp_customerAddr1 = "";
        String temp_customerAddr2 = "";
        String temp_customerCity = "";
        String temp_customerState = "";
        String temp_customerZip = "";
        String temp_deliveryday = "";
        String temp_deliverytime = "";
        String temp_deliverydate = "";
        String temp_deliverytakeaway = "";
        String temp_customermemo = "";
        String temp_phoneorder = "";
        String temp_phoneordernumber = "";
        String temp_customerAddrAll = "";
        String temp_kichenprintyn = "";

        strQuery = "select customerId, customerName, customerPhone, customerEmail, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo, phoneorder, phoneordernumber, kitchenprintedyn " +
                " from temp_salecart_deliveryinfo " +
                " where holdcode = '" + sales_holdCode + "' ";
        GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
        ResultSet deliverytakeawayInfoCursor = MssqlDatabase.getResultSetValue(strQuery);
        while (true){
            try {
                if (!deliverytakeawayInfoCursor.next()) break;
                if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                    temp_customerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                    temp_customerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                    temp_customerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                } else {
                    temp_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,0), 1);
                    temp_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,1), 1);
                    temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,2), 1);
                }
//            temp_customerId = GlobalMemberValues.getDBTextAfterChecked(deliverytakeawayInfoCursor.getString(0), 1);
//            temp_customerName = GlobalMemberValues.getDBTextAfterChecked(deliverytakeawayInfoCursor.getString(1), 1);
//            temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(deliverytakeawayInfoCursor.getString(2), 1);

                temp_customerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,3), 1);
                mCustomerEmail = temp_customerEmail;

                temp_customerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,4), 1);
                temp_customerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,5), 1);
                temp_customerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,6), 1);
                temp_customerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,7), 1);
                temp_customerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,8), 1);

                temp_deliveryday = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,9), 1);
                temp_deliverytime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,10), 1);
                temp_deliverydate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,11), 1);
                temp_deliverytakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,12), 1);
                if (!GlobalMemberValues.isToGoSale()) {
                    temp_deliverytakeaway = "H";
                }

                temp_customermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,13), 1);

                temp_phoneorder = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,14), 1);
                temp_phoneordernumber = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,15), 1);

                temp_kichenprintyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,16), 1);

                if (GlobalMemberValues.isStrEmpty(temp_phoneorder)) {
                    temp_phoneorder = "N";
                }

                if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                    temp_phoneorder = "N";
                    temp_phoneordernumber = "";
                }

                mPhoneOrder = temp_phoneorder;
                GlobalMemberValues.PHONEORDERYN = temp_phoneorder;
                GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "N";

                if (!GlobalMemberValues.isStrEmpty(temp_customerAddr1)
                        || !GlobalMemberValues.isStrEmpty(temp_customerAddr2)
                        || !GlobalMemberValues.isStrEmpty(temp_customerCity)
                        || !GlobalMemberValues.isStrEmpty(temp_customerState)
                        || !GlobalMemberValues.isStrEmpty(temp_customerZip)) {

                    temp_customerAddrAll = temp_customerAddr1;

                    if (!GlobalMemberValues.isStrEmpty(temp_customerAddr2)) {
                        temp_customerAddrAll += "\n" + temp_customerAddr2;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerCity)) {
                        temp_customerAddrAll += "\n" + temp_customerCity;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerState)) {
                        temp_customerAddrAll += ", " + temp_customerState;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerZip)) {
                        temp_customerAddrAll += " " + temp_customerZip;
                    }
                }
//                deliverytakeawayInfoCursor.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
//        if (deliverytakeawayInfoCursor.getCount() > 0 && deliverytakeawayInfoCursor.moveToFirst()) {
//
//        }
        GlobalMemberValues.PHONEORDER_HOLDCODE = sales_holdCode;

        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null &&
                GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid)) {
            strQuery = "select customerId, customerName " +
                    " from temp_salecart " +
                    " where holdcode = '" + sales_holdCode + "' ";
            GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
            Cursor tempsalecartCursor = dbInit.dbExecuteRead(strQuery);
            if (tempsalecartCursor.getCount() > 0 && tempsalecartCursor.moveToFirst()) {
                temp_customerId = GlobalMemberValues.getDBTextAfterChecked(tempsalecartCursor.getString(0), 1);
                temp_customerName = GlobalMemberValues.getDBTextAfterChecked(tempsalecartCursor.getString(1), 1);
            }
        }

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot.put("customerphonenum", temp_customerPhone);
        jsonroot.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        /** 사용한 포인트가 있고 고객이 지정되었을 경우 해당 고객의 포인트를 차감한다. ***************************/
        if (!GlobalMemberValues.isStrEmpty(sales_customerId) && tempPointAmount > 0) {
            strUpdSqlQuery = "update salon_member set mileage = mileage - " + GlobalMemberValues.getStringFormatNumber(tempPointAmount, "2") +
                    " where uid = '" + sales_customerId + "' ";
            strInsertQueryVec.addElement(strUpdSqlQuery);

            String temmpEmpName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                temmpEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }
            strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                    " ) values ( " +
                    " '" + "Used by Sales - " + mSalesCode + "', " +
                    " '" + GlobalMemberValues.getStringFormatNumber(tempPointAmount, "2") + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                    " '" + "2" + "', " +            // 1: 적립        2 : 사용
                    " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                    " '" + GlobalMemberValues.getDBTextAfterChecked(temmpEmpName, 0) + "', " +
                    " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strInsSqlQuery);
        }
        /*********************************************************************************************************/

        /** salon_sales 테이블 저장 관련 *******************************************************************/
        double tempDeliveryPickUpFee = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString());

        GlobalMemberValues.logWrite("jjjphoneodynlog", "temp_phoneorder : " + temp_phoneorder + "\n");
        GlobalMemberValues.logWrite("jjjphoneodynlog", "temp_phoneordernumber : " + temp_phoneordernumber + "\n");

        // 고객 주문번호
        String customerOrderNumber = "";
        if (temp_phoneorder.equals("Y")) {
            if (!GlobalMemberValues.isStrEmpty(temp_phoneordernumber)) {
                customerOrderNumber = "PHONE_" + temp_phoneordernumber;
            } else {
                customerOrderNumber = GlobalMemberValues.getCustomerOrderNewNumber(mSalesCode);
            }
        } else {
            customerOrderNumber = GlobalMemberValues.getCustomerOrderNewNumber(mSalesCode);
        }

        // 고객용 주문번호에 스테이션 번호 연결
        if (!GlobalMemberValues.isStrEmpty(customerOrderNumber)) {
            customerOrderNumber = GlobalMemberValues.getStationNumber() + "-" + customerOrderNumber;
        }


        // bill pay 일 경우
        String savedCustomerOrderNum = "";
        if (GlobalMemberValues.isPaymentByBills) {
            savedCustomerOrderNum = MssqlDatabase.getResultSetValueToString(
                    " select top 1 ordernum from bill_list_paid " +
                            " where holdcode = '" + GlobalMemberValues.mSelectedHoldCodeForBillPay + "' " +
                            " order by idx asc "
            );
            if (!GlobalMemberValues.isStrEmpty(savedCustomerOrderNum)) {
                customerOrderNumber = savedCustomerOrderNum;
            }
        }

        // 고객 페이저 번호
        String customerPagerNumber = "";
        String tempPagerNum = pagerNumEv.getText().toString();
        /**
         if (GlobalMemberValues.isStrEmpty(tempPagerNum)) {
         tempPagerNum = "1";
         }
         **/
        customerPagerNumber = tempPagerNum;

        double dbl_allDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(allDiscountExtraPrice);

        double totalAllEachDcExPrice = dbl_allDiscountExtraPrice + totalDiscountExtraPrice;

        GlobalMemberValues.logWrite("discountresultjjjlog", "totalAllEachDcExPrice : " + (totalAllEachDcExPrice * -1) + "\n");
        GlobalMemberValues.logWrite("discountresultjjjlog", "tempTotalAmountOrg : " + tempTotalAmountOrg + "\n");

        // 전체 결제금액보다 Discount 된 금액이 많을 경우
        // 전체 결제금액은 mSTotalAmount_org 합산금액으로 가져온다.
        // (Discount 하면서 mSTotalAmount 은 할인금액이 반영되었기 때문에...)
        if ((totalAllEachDcExPrice * -1) > tempTotalAmountOrg) {
            mTextChnage = "0.0";
            sales_totalAmount = 0;

            if (dbl_allDiscountExtraPrice < 0 && totalDiscountExtraPrice < 0) {
                totalDiscountExtraPrice = 0.0;
                eachDiscountExtraPrice = 0.0;
                totalDiscountPrice = 0.0;
            }

            dbl_allDiscountExtraPrice = tempTotalAmountOrg * -1;
            totalAllEachDcExPrice = dbl_allDiscountExtraPrice;
        }

        GlobalMemberValues.logWrite("discountextralogjjj_2", "totalAllEachDcExPrice : " + totalAllEachDcExPrice + "\n");
        GlobalMemberValues.logWrite("discountextralogjjj_2", "totalDiscountPrice : " + totalDiscountPrice + "\n");
        GlobalMemberValues.logWrite("discountextralogjjj_2", "totalExtraPrice : " + totalExtraPrice + "\n");
        GlobalMemberValues.logWrite("discountextralogjjj_2", "eachDiscountExtraPrice : " + eachDiscountExtraPrice + "\n");
        GlobalMemberValues.logWrite("discountextralogjjj_2", "totalAllEachDcExPrice : " + totalAllEachDcExPrice + "\n");

        // totalAllEachDcExPrice 총합
        if (eachDiscountExtraPrice < 0) {
            totalAllEachDcExPrice += eachDiscountExtraPrice;
            totalDiscountPrice += eachDiscountExtraPrice;
        }

        // 카드결제시 단말기 ip
        ConfigPGInfo pgInfo = new ConfigPGInfo();
        String salepg_ip = pgInfo.cfnetworkip;

        // 11182022
        // togotype 가져오기
        String tempTogoType = MssqlDatabase.getResultSetValueToString(
                " select togotype from temp_salecart where holdcode = '" + sales_holdCode + "' "
        );

        // 0 : 정상주문      1 : 배치전 void
        int sales_status = 0;
        // salon_sales 입력 쿼리
        strInsSqlQuery = "insert into salon_sales (" +
                " salesCode, holdCode, reservationCode, sidx, stcode, " +
                " qty, " +
                " salesBalPriceAmount, taxAmount, totalAmount, " +
                " isTotalCashUse, isTotalCardUse, isTotalGiftCardUse, isTotalCheckUse, isTotalPointUse, " +
                " useTotalCashAmount, useTotalCardAmount, useTotalGiftCardAmount, useTotalCheckAmount, useTotalPointAmount, " +
                " useTotalCashRatio, useTotalCardRatio, useTotalGiftCardRatio, useTotalCheckRatio, useTotalPointRatio, " +
                " useTotalCashAmountAfterReturned, useTotalCardAmountAfterReturned, useTotalGiftCardAmountAfterReturned, useTotalCheckAmountAfterReturned, useTotalPointAmountAfterReturned, " +
                " commissionAmount, pointAmount, " +
                " customerId, customerName, customerPhone, customerEmail, customerPosCode, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
//                " deliveryday, deliverytime, deliverydate, " +
                " deliverytakeaway, " +
                " customermemo, " +
                " giftcardNumberUsed, giftcardPriceUsed, " +
                " totalDiscountExtraPrice, totalDiscountPrice, totalExtraPrice, eachDiscountExtraPrice, allDiscountExtraPrice, allDiscountExtraStr, " +
                " cardTidNumber, " +
                " isCloudUpload, " +
                " status, " +
                " changeMoney, " +
                " employeeIdx, employeeName, " +
                " serverIdx, serverName, " +
                " deliverypickupfee, " +
                " checkcompany, phoneorder, customerordernumber, customerpagernumber, " +
                " tablename, tablepeoplecnt, salepg_ip, togotype " + sqlQuery_add1 +
                " ) values ( " +

                " '" + mSalesCode + "', " +
                " '" + sales_holdCode + "', " +
                " '" + sales_reservationCode + "', " +
                " '" + sales_sidx + "', " +
                " '" + sales_stcode + "', " +

                " '" + sales_qty + "', " +

                " '" + sales_salesBalPriceAmount + "', " +
                " '" + sales_taxAmount + "', " +
                " '" + sales_totalAmount + "', " +

                " '" + sales_isBalCashUse + "', " +
                " '" + sales_isBalCardUse + "', " +
                " '" + sales_isBalGiftCardUse + "', " +
                " '" + sales_isBalCheckUse + "', " +
                " '" + sales_isBalPointUse + "', " +

                " '" + tempCashAmount + "', " +
                " '" + tempCardAmount + "', " +
                " '" + tempGiftCardAmount + "', " +
                " '" + tempCheckAmount + "', " +
                " '" + tempPointAmount + "', " +

                " '" + str_useTotalCashRatio + "', " +
                " '" + str_useTotalCardRatio + "', " +
                " '" + str_useTotalGiftCardRatio + "', " +
                " '" + str_useTotalCheckRatio + "', " +
                " '" + str_useTotalPointRatio + "', " +

                " '" + tempCashAmount + "', " +
                " '" + tempCardAmount + "', " +
                " '" + tempGiftCardAmount + "', " +
                " '" + tempCheckAmount + "', " +
                " '" + tempPointAmount + "', " +

                " '" + sales_commissionAmount + "', " +
                " '" + savePointAmount_forsales + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerId, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerName, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerPhone, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerEmail, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerPosCode, 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerAddr1, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerAddr2, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerCity, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerState, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customerZip, 0) + "', " +

//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliveryday, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverytime, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverydate, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_deliverytakeaway, 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_customermemo, 0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +

                " '" + GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalDiscountPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalExtraPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(eachDiscountExtraPrice, "2") + "', " +
                " '" + GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2") + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(allDiscountExtraStr,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(mCardTidNumber,0) + "', " +

                " '" + isCloudUpload + "', " +

                " '" + sales_status + "', " +

                " '" + temp_double_change + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME,0) + "', " +

                " '" + tempDeliveryPickUpFee + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(mCheckCompany,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_phoneorder,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(customerOrderNumber,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(customerPagerNumber,0) + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(TableSaleMain.mTableName, 0) + "', " +
                " '" + TableSaleMain.mTablePeopleCnt + "', " +

                " '" + GlobalMemberValues.getDBTextAfterChecked(salepg_ip,0) + "', " +

                " '" + tempTogoType + "' " +

                sqlQuery_add2 +
                ")";
        // salon_sales 입력쿼리를 백터 strInsertQueryVec 에 담는다.
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strInsSqlQuery);
        }
        /*********************************************************************************************************/

        /** salon_sales_customerpagernumber 테이블 저장 관련 ********************************************************/
        if (GlobalMemberValues.isPagerUse()) {
            if (!GlobalMemberValues.isStrEmpty(customerPagerNumber)) {
                strInsSqlQuery = " insert into salon_sales_customerpagernumber ( " +
                        " scode, sidx, stcode, salesCode, customerpagernumber " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE.toUpperCase(),0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(customerPagerNumber, 0) + "' " +
                        " ) ";
                if (GlobalMemberValues.isLastBillList()) {
                    strInsertQueryVec.addElement(strInsSqlQuery);
                }

            }
        } else {
            customerPagerNumber = "";
        }
        /*********************************************************************************************************/

        // JSON ----------------------------------------------------------------------
        if (GlobalMemberValues.isStrEmpty(temp_customerName)) {
            if (!GlobalMemberValues.isStrEmpty(sales_customerName)) {
                jsonroot.put("customername", sales_customerName);
                jsonroot_kitchen.put("customername", sales_customerName);
            } else {
                jsonroot.put("customername", "walk in");
                jsonroot_kitchen.put("customername", "WALK IN");
            }
        } else {
            jsonroot.put("customername", temp_customerName);
            jsonroot_kitchen.put("customername", temp_customerName);
        }

        if (!GlobalMemberValues.isStrEmpty(sales_customerId)){
            jsonroot.put("customerid",sales_customerId);
            jsonroot.put("customer_point_remaining" ,GlobalMemberValues.getCustomerPointRemaining(tempPointAmount));
//            Log.e("point test customer_point_remaining", GlobalMemberValues.getCustomerPointRemaining(tempPointAmount));
        }
        if (tempPointAmount != 0.0){
//            jsonroot.put("customer_point_remaining" ,GlobalMemberValues.getCustomerPointRemaining(tempPointAmount));
        }


        // All Discount / Extra
        // JSON ----------------------------------------------------------------------
        jsonroot.put("alldiscountextraprice", GlobalMemberValues.getStringFormatNumber(dbl_allDiscountExtraPrice, "2"));
        if (!GlobalMemberValues.isStrEmpty(allDiscountExtraStr)) {
            jsonroot.put("alldiscountextrstr", allDiscountExtraStr);
        } else {
            jsonroot.put("alldiscountextrstr", "");
        }
        // ---------------------------------------------------------------------------

        // Extra - Discount 의 값
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totalextradiscountprice", GlobalMemberValues.getStringFormatNumber(totalAllEachDcExPrice, "2"));
        // ---------------------------------------------------------------------------

        // Discount
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totaldiscountprice", GlobalMemberValues.getStringFormatNumber(totalDiscountPrice, "2"));
        // ---------------------------------------------------------------------------

        // Extra
        // JSON ----------------------------------------------------------------------
        jsonroot.put("totalextraprice", GlobalMemberValues.getStringFormatNumber(totalExtraPrice, "2"));
        // ---------------------------------------------------------------------------

        double sendTaxAmount = sales_taxAmount;
        if (sales_taxAmount == 0 && tempTaxAmountOrg > 0) {
            //sendTaxAmount = tempTaxAmountOrg;
        }

        jsonroot.put("subtotal", GlobalMemberValues.getStringFormatNumber(tempSalesAmount, "2"));
        jsonroot.put("tax", GlobalMemberValues.getStringFormatNumber(sendTaxAmount, "2"));
        jsonroot.put("totalamount", GlobalMemberValues.getStringFormatNumber(sales_totalAmount, "2"));

        jsonroot.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot.put("deliverypickupfee", GlobalMemberValues.getStringFormatNumber(tempDeliveryPickUpFee, "2"));

        jsonroot.put("customerordernumber", customerOrderNumber);
        jsonroot.put("customerpagernumber", customerPagerNumber);

        jsonroot.put("customermemo", temp_customermemo);
        jsonroot.put("checkcompany", mCheckCompany);

        jsonroot_kitchen.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot_kitchen.put("deliverydate", temp_deliverydate);
        jsonroot_kitchen.put("ordertype", "POS");

        jsonroot_kitchen.put("customermemo", temp_customermemo);

        jsonroot_kitchen.put("customerordernumber", customerOrderNumber);
        jsonroot_kitchen.put("customerpagernumber", customerPagerNumber);

        jsonroot_kitchen.put("customerpagerheader", GlobalMemberValues.PAGERNUMBERHEADERTXT);

        jsonroot_kitchen.put("checkcompany", mCheckCompany);

        jsonroot_kitchen.put("phoneordernumber", temp_phoneordernumber);

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot_kitchen.put("customerphonenum", temp_customerPhone);
        jsonroot_kitchen.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        // Restaurant 관련 데이터 ----------------------------------------------------
        jsonroot.put("restaurant_tablename", TableSaleMain.mTableName);
        jsonroot.put("restaurant_tablepeoplecnt", TableSaleMain.mTablePeopleCnt);
        jsonroot_kitchen.put("restaurant_tablename", TableSaleMain.mTableName);
        jsonroot_kitchen.put("restaurant_tablepeoplecnt", TableSaleMain.mTablePeopleCnt);
        // ---------------------------------------------------------------------------

        // mssql 을 사용할 경우 mssql 데이터베이스의 salon_sales_kitchenprintingdata_json 테이블에 데이터 저장
        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {

                GlobalMemberValues.logWrite("mssqljjjlog2", "쿼리 저장준비.." + "\n");
                GlobalMemberValues.logWrite("mssqljjjlog2", "주방json : " + jsonroot_kitchen.toString() + "\n");

                String tempPrintYN = "N";
//                if (!isOrderedOnTable) {
//                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기0" + "\n");
//                    tempPrintYN = "N";
//
//                    GlobalMemberValues.logWrite("jjjktplogjjj", "printyn1 : " + tempPrintYN + "\n");
//                } else {
//                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기1-1" + "\n");
//                    if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
//                        GlobalMemberValues.logWrite("printlogjjjwhy", "여기1-2" + "\n");
//                        String tempQuickSaleyn = "N";
//                        tempTableIdx = GlobalMemberValues.getReplaceText(tempTableIdx, "T", "");
//                        tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
//                                " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
//                        );
//                        if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
//                            tempPrintYN = "N";
//                        }
//
//                        GlobalMemberValues.logWrite("jjjktplogjjj", "printyn2 : " + tempPrintYN + "\n");
//                    }
//                }

                // salon_sales_kitchenprintingdata_json 에 데이터가 있는지 확인
//                int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
//                        " select count(*) from salon_sales_kitchenprintingdata_json where salescode = '" + MainMiddleService.mHoldCode + "' "
//                ));
//                if (tempCnt > 0) {
//                    tempPrintYN = "Y";
////                    strDelSqlQuery = " update salon_sales_kitchenprintingdata_json set printedyn = 'Y' where salescode = '" + MainMiddleService.mHoldCode + "' ";
////                    strInsertQueryVec.addElement(strDelSqlQuery);
//                    GlobalMemberValues.logWrite("printlogjjjwhy", "여기2" + "\n");
//
//                    GlobalMemberValues.logWrite("jjjktplogjjj", "printyn3 : " + tempPrintYN + "\n");
//                } else {
//                    if (tempPrintYN.equals("Y")) {
//                        tempPrintYN = "N";
//
//                        GlobalMemberValues.logWrite("jjjktplogjjj", "printyn4 : " + tempPrintYN + "\n");
//                    }
//
//                    GlobalMemberValues.logWrite("jjjktplogjjj", "printyn5 : " + tempPrintYN + "\n");
//                }

                // 06.07.2022 -----------------------------
                if (GlobalMemberValues.isRepay2) {
                    tempPrintYN = "Y";
                }
                GlobalMemberValues.isRepay2 = false;
                // -----------------------------------------

                // 01132023
                jsonroot_kitchen.put("holdcode", sales_holdCode);

                // 04222023
                if (GlobalMemberValues.isRepay3) {
                    tempPrintYN = "Y";
                }

                GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기4 : " + jsonroot_kitchen.toString() + "\n");


                // 03202024
                // payment 시에는 salon_sales_kitchenprintingdata_json 에 데이터 쌓지 않도록 처리해야 하므로
                // tempPrintYN 을 무조건 Y 로 처리
                tempPrintYN = "Y";


                // 11102023
                if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonroot_kitchen.toString())) {
                    strDelSqlQuery = " insert into salon_sales_kitchenprintingdata_json " +
                            " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                            "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot_kitchen.toString(), 0) + "', " +
                            " '" + tempPrintYN + "'" +
                            " ) ";
                }

                // 04222023
                GlobalMemberValues.isRepay3 = false;

                if (GlobalMemberValues.isLastBillList()) {
                    strInsertQueryVec.addElement(strDelSqlQuery);
                }

            }
        }

        // ---------------------------------------------------------------------------


        /** 카드 결제가 있는데 offline 결제일 경우 ***************************************************************/
        if (cardOnOffInfo.equals("off") && mIsPaymentTypeCard == "Y") {
            strInsSqlQuery = "insert into salon_sales_card (" +
                    " salesCode, tid, sidx, stcode, cardCom, priceAmount, insertSwipeKeyin, status, " +
                    " employeeIdx, employeeName,  " +
                    " serverIdx, serverName  " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                    " '', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_sidx,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_stcode,0) + "', " +
                    " 'OFFLINECARD', " +
                    " '" + tempCardAmount + "', " +
                    " '', " +
                    " '3', " +                   // 오프라인 카드는 주문과 동시에 배치된것으로 간주
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "' " +
                    ")";
            // salon_sales_card 입력쿼리를 백터 strInsertQueryVec 에 담는다.
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strInsSqlQuery);
            }
        }
        /*********************************************************************************************************/

        /** 사용한 기프트카드가 있으면 해당 기프트카드의 발란스 수정 및 히스토리에 등록한다. *********************/
        if (!GlobalMemberValues.isStrEmpty(sales_giftcardNumberUsed)) {
            // salon_storegiftcard_number 테이블 저장
            strUpdSqlQuery = "update salon_storegiftcard_number set " +
                    " remainingPrice = remainingPrice - " + GlobalMemberValues.getDoubleAtString(sales_giftcardPriceUsed) + " " +
                    " where gcNumber = '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed,0) + "' ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strUpdSqlQuery);
            }

            String sales_employeeIdx = "";
            String sales_employeeName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                sales_employeeIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                sales_employeeName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }

            // salon_storegiftcard_number_history 테이블 저장
            strInsSqlQuery = "insert into salon_storegiftcard_number_history (" +
                    " sidx, gcNumber, saleItemIdx, empIdx, empName, serverIdx, serverName, " +
                    " customerId, customerName, addUsePrice, addUseType, salesCode, codeforupload " +
                    " ) values ( " +
                    " '" + sales_sidx + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardNumberUsed, 0) + "', " +
                    " '" + "" + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_employeeIdx, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_employeeName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_IDX, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SERVER_NAME, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_giftcardPriceUsed, 0) + "', " +
                    " '" + "U" + "', " +               // 기프트카드 사용이므로 적립인 'U' 로 저장 (적립시 'A')
                    " '" + mSalesCode + "', " +         // 세일즈코드
                    " '" + GlobalMemberValues.getCodeForUpload("giftcard") + "' " +
                    ")";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strInsSqlQuery);
            }


            // JSON ----------------------------------------------------------
            //jsontmp = new JSONObject();
            //jsontmp.put("giftcardusednumber", sales_giftcardNumberUsed);
            //jsontmp.put("giftcardusedprice", sales_giftcardPriceUsed);
            //jsonList.put(jsontmp);
            // ---------------------------------------------------------------

            // JSON (기프트카드 사용정보 리스트) ---------------------------------------------------
            //jsonroot.put("giftcardusedinfo", jsonList);
            // ---------------------------------------------------------------------------
        }
        /*********************************************************************************************************/

        /** salon_store_restaurant_table_split 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_split where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** salon_store_restaurant_table_merge 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_merge where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** salon_store_restaurant_table_peoplecnt 테이블 삭제한다. **************************************************/
        strDelSqlQuery = "delete from salon_store_restaurant_table_peoplecnt where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart_ordered 테이블에서 1주일 전 데이터는 삭제한다 ***********************************************/
        String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -1000);
        String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -7);
        strDelSqlQuery = "delete from temp_salecart_ordered where wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
        GlobalMemberValues.logWrite("jjjdelsqljjjlog", "sql : " + strDelSqlQuery + "\n");
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart 테이블에서 해당 주문건을 삭제한다. (테이블 idx 로 삭제) *****************************************************/
//        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
//            for (int i = 0; i < TableSaleMain.mTableIdxArrList.size(); i++) {
//                strDelSqlQuery = "delete from temp_salecart where tableidx = '" + TableSaleMain.mTableIdxArrList.get(i) + "' " +
//                        " and subtablenum = '" + TableSaleMain.mSubTableNum + "' ";
//                if (GlobalMemberValues.isLastBillList()) {
//                    strInsertQueryVec.addElement(strDelSqlQuery);
//                }
//            }
//        }

        if (!GlobalMemberValues.isStrEmpty(tableidx_fordel)) {
            strDelSqlQuery = "delete from temp_salecart where tableidx = '" + tableidx_fordel + "' " +
                    " and subtablenum = '" + TableSaleMain.mSubTableNum + "' ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strDelSqlQuery);
            }
        }
        /*********************************************************************************************************/


        /** temp_salecart_deliveryinfo 테이블에서 해당 주문건을 삭제한다. ******************************************/
        strDelSqlQuery = "delete from temp_salecart_deliveryinfo where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart_optionadd 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart_optionadd where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart_optionadd_imsi 테이블에서 해당 주문건을 삭제한다. *****************************************************/
        strDelSqlQuery = "delete from temp_salecart_optionadd_imsi where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** 고객이 Walk in 이 아닌 경우 member1 테이블의 lastvisitForSale 의 값을 구매일시로 수정한다. **************/
        if (!GlobalMemberValues.isStrEmpty(sales_customerId)) {
            //strDelSqlQuery = "update member1 set lastvisitForSale = datetime('now', 'localtime') " +
            //        " where uid = '" + sales_customerId + "' ";
            strDelSqlQuery = "insert into member_salevisit (" +
                    " uid, name, lastvisitForSale " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerId, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(sales_customerName, 0) + "', " +
                    " datetime('now', 'localtime') " +
                    ")";
            strInsertQueryVec.addElement(strDelSqlQuery);
        }
        /*********************************************************************************************************/

        /** temp_salecart_del2,salon_newcartcheck_bystation 테이블 처리 ********************************************/
        if (!GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)
                && !MainMiddleService.mHoldCode.equals("NOHOLDCODE")) {
            strDelSqlQuery = "insert into temp_salecart_del2 ( " +
                    " holdcode, stcode, tableidx " +
                    " ) values ( " +
                    " '" + MainMiddleService.mHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + tempTableIdx + "' " +
                    " ) ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strDelSqlQuery);
            }

            // 해당 스테이션 외의 다른 스테이션의 salon_newcartcheck_bystation 데이터 지우기
            strDelSqlQuery = " delete from salon_newcartcheck_bystation where " +
                    " holdcode = '" + MainMiddleService.mHoldCode + "' " +
                    " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strDelSqlQuery);
            }
        }
        /*******************************************************************************************************/


        /** 결제수단별 영수증 출력 JSON 만들기 ****************************************************************/
        // Cash 결제가 있을 경우
        if (orginInputCashAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("cashtendered", GlobalMemberValues.getStringFormatNumber(orginInputCashAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("cashtendered", "0");
            // ---------------------------------------------------------------------------
        }
        // Change (거스름돈) 있을 경우
        if (temp_double_change > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("change", GlobalMemberValues.getStringFormatNumber(temp_double_change, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("change", "0");
            // ---------------------------------------------------------------------------
        }

        // 카드결제가 있을 경우
        if (tempCardAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("creditcardtendered", GlobalMemberValues.getStringFormatNumber(tempCardAmount, "2"));
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
        if (tempGiftCardAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("giftcardtendered", GlobalMemberValues.getStringFormatNumber(tempGiftCardAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("giftcardtendered", "0");
            // ---------------------------------------------------------------------------
        }

        // Check 결제가 있을 경우
        if (tempCheckAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("checktendered", GlobalMemberValues.getStringFormatNumber(tempCheckAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("checktendered", "0");
            // ---------------------------------------------------------------------------
        }

        // Point 결제가 있을 경우
        if (tempPointAmount > 0) {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("pointtendered", GlobalMemberValues.getStringFormatNumber(tempPointAmount, "2"));
            // ---------------------------------------------------------------------------
        } else {
            // JSON ----------------------------------------------------------------------
            jsonroot.put("pointtendered", "0");
            // ---------------------------------------------------------------------------
        }

        /*****************************************************************************************************/

        // JSON ----------------------------------------------------------------------
        // 프린트 타입   (1 : customer            2 : merchant                3 : 둘다)
        jsonroot.put("receiptprinttype", "3");
        // ---------------------------------------------------------------------------

        // JSON ----------------------------------------------------------------------
        jsonroot.put("receiptfooter", GlobalMemberValues.getReceiptFooter());
        // ---------------------------------------------------------------------------

        jsonrootForPaymentReview = jsonroot;

        // json 저장
        strDelSqlQuery = "update salon_sales set " +
                " receiptJson = '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot.toString(),0) + "' " +
                " where salescode = '" + mSalesCode + "' ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strDelSqlQuery);
        }

        // bill pay 관련 json 에 데이터 저장
        jsonroot.put("billtype", GlobalMemberValues.mBillSplitType);
        jsonroot.put("billpayamount", GlobalMemberValues.mPayAmountOnBill);
        jsonroot.put("billcartidx", GlobalMemberValues.mCartIdxsOnBillPay);
        jsonroot.put("billtotalamount", GlobalMemberValues.getBillAmountByHoldCode(GlobalMemberValues.mSelectedHoldCodeForBillPay));
        jsonroot.put("bill_list_idx", GlobalMemberValues.mBill_Idx);
        jsonroot.put("bill_list_holdcode", GlobalMemberValues.mSelectedHoldCodeForBillPay);

        /** 멀티스테이션을 위한 영수증/주방프린팅용 json 저장. ********************************************************/
        // 영수증프린트 Json 저장
        String tempReceiptJson = jsonroot.toString();
        if (!GlobalMemberValues.isStrEmpty(tempReceiptJson)) {
            strDelSqlQuery = "insert into salon_sales_receipt_json (" +
                    " salesCode, scode, sidx, stcode, jsonstr " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempReceiptJson, 0) + "' " +
                    ")";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strDelSqlQuery);
            }
        }

        // 주방프린트 Json 저장
        //String tempKitchenJson = jsonroot_kitchen.toString();
        if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {
            int insIsCloudUpload = 0;
            if (!GlobalMemberValues.isCloudKitchenPrinter()) {
                insIsCloudUpload = 1;
            }

            strDelSqlQuery = "insert into salon_sales_kitchen_json (" +
                    " salesCode, scode, sidx, stcode, jsonstr, isCloudUpload " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonroot_kitchen.toString(), 0) + "', " +
                    " '" + insIsCloudUpload + "' " +
                    ")";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strDelSqlQuery);
            }

        }
        /*********************************************************************************************************/

        String pay_type = "";
        if (tempCashAmount > 0) {
            pay_type = "CASH";
        }
        if (tempCardAmount > 0) {
            pay_type = "CARD";
        }
        if (tempGiftCardAmount > 0) {
            pay_type = "GIFTCARD";
        }
        if (tempCheckAmount > 0) {
            pay_type = "CHECK";
        }
        if (tempPointAmount > 0) {
            pay_type = "POINT";
        }

        // bill pay 일 경우
        if (GlobalMemberValues.isPaymentByBills) {
            int tempBillCnt = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString(
                            " select count(*) from bill_list_paid where billidx = '" + GlobalMemberValues.mBill_Idx + "' "
                    )
            );
            GlobalMemberValues.logWrite("billcntjjjlog", "tempBillCnt : " + tempBillCnt + "\n");
            if (tempBillCnt == 0) {
                // 01052023
                String nBillCode = GlobalMemberValues.makeBillCode();

                String str_split_transaction_id = "";

                if (GlobalMemberValues.mSplit_transaction_id.isEmpty()){
                    str_split_transaction_id = GlobalMemberValues.makeSplitTransactionCode();
                } else {
                    str_split_transaction_id = GlobalMemberValues.mSplit_transaction_id;
                }

                GlobalMemberValues.logWrite("billcntjjjlog", "여기실행" + "\n");

                GlobalMemberValues.logWrite("billpaysalescardidxlogjjj", "card sales idx1 : " + GlobalMemberValues.mCardSalesIdxForBillPay + "\n");

                strDelSqlQuery = " insert into bill_list_paid ( " +
                        " salescode, holdcode, sidx, stcode, billidx, paidamount, paytype, changeamount, ordernum, cardsalesidx, billcode, split_transaction_id " +
                        " ) values ( " +
                        " '" + mSalesCode + "', " +
                        " '" + GlobalMemberValues.mSelectedHoldCodeForBillPay + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + GlobalMemberValues.mBill_Idx + "', " +
                        " '" + GlobalMemberValues.mPayAmountOnBill + "', " +
                        " '" + pay_type + "', " +
                        " '" + temp_double_change + "', " +
                        " '" + customerOrderNumber + "', " +
                        " '" + GlobalMemberValues.mCardSalesIdxForBillPay + "', " +
                        " '" + nBillCode + "', " +
                        " '" + str_split_transaction_id + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strDelSqlQuery);

                GlobalMemberValues.mCardSalesIdxForBillPay = "";

                // 01052023
                if (!GlobalMemberValues.isStrEmpty(tempReceiptJson)) {
                    strDelSqlQuery = "insert into bill_list_receipt_json (" +
                            " salesCode, scode, sidx, stcode, jsonstr, billcode " +
                            " ) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(mSalesCode,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempReceiptJson, 0) + "', " +
                            " '" + nBillCode + "' " +
                            ")";
                    strInsertQueryVec.addElement(strDelSqlQuery);
                }
            }

            GlobalMemberValues.logWrite("billpaysalescardidxlogjjj", "card sales idx2 : " + GlobalMemberValues.mCardSalesIdxForBillPay + "\n");

            if (GlobalMemberValues.isLastBillList()) {
                // split last 결제시 금액 나오지 않는 현상때문에 주석처리
//                strDelSqlQuery = " delete from bill_list where holdcode = '" + GlobalMemberValues.mSelectedHoldCodeForBillPay + "' ";
                strDelSqlQuery = " update bill_list set paidyn = 'Y' where idx = '" + GlobalMemberValues.mBill_Idx + "' ";
            } else {
                strDelSqlQuery = " update bill_list set paidyn = 'Y' where idx = '" + GlobalMemberValues.mBill_Idx + "' ";
            }
            strInsertQueryVec.addElement(strDelSqlQuery);
        }



        // 로그 파일 남기기
        GlobalMemberValues.backupSaleJsonData(jsonroot);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("paymentdbexelogjjj", "query : " + tempQuery + "\n");
        }

        // 고객정보 초기화 ---------------------------------------------------------------------
        // 고객정보 초기화전에 고객아이디(이메일)를 저장해둔다
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null
                && !GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid)) {
            mTextCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
        }
        GlobalMemberValues.setCustomerInfoInit();
        // -------------------------------------------------------------------------------------

        String tempChange = paymentChangeEditText.getText().toString();
        mTextChnage = tempChange;

        // 영수증 프린트 하기 ------------------------------------------------------------------
        if (receiptYN.equals("Y")) {
            // 돈통열기 cashdrawer
            if (!GlobalMemberValues.isStrEmpty(tempCashAmount + "") && tempCashAmount > 0) {
                //CommandButton.openCashDrawer();
            }

            // 고객이 영수증 발급타입을 선택할 수 있는지 여부
            if (GlobalMemberValues.isCustomerSelectReceipt()) {
                GlobalMemberValues.logWrite("printingtestjjj", "여기실행되요..1 \n");

                final String finalTemp_customerPhone = temp_customerPhone;
                final String finalTemp_phoneorder = temp_phoneorder;
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        Intent paymentcustomerselectreceiptIntent = new Intent(context.getApplicationContext(), PaymentCustomerSelectReceipt.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        paymentcustomerselectreceiptIntent.putExtra("PaymentChangeAmount", mTextChnage);
                        paymentcustomerselectreceiptIntent.putExtra("receiptYN", receiptYN);
                        paymentcustomerselectreceiptIntent.putExtra("GiftCardNumberUsed", mGiftCardNumberUsed);
                        paymentcustomerselectreceiptIntent.putExtra("CustomerEmail", mTextCustomerId);
                        paymentcustomerselectreceiptIntent.putExtra("CustomerEmailReal", mCustomerEmail);
                        paymentcustomerselectreceiptIntent.putExtra("CustomerPhoneNo", finalTemp_customerPhone);
                        paymentcustomerselectreceiptIntent.putExtra("PhoneOrder", finalTemp_phoneorder);
                        if (jsonrootForPaymentReview != null) {
                            paymentcustomerselectreceiptIntent.putExtra("JsonReceipt", jsonrootForPaymentReview.toString());
                        }
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(paymentcustomerselectreceiptIntent);
                        //mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);

                        GlobalMemberValues.logWrite("pageonenorderlog", "여기1" + "\n");

                        // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                        paymentcustomerselectHandler.sendEmptyMessage(0);
                        // ---------------------------------------------------------------------------------
                    }
                });
                thread.start();

            } else {
                // 데이터베이스 저장
                isaveDBInPayment = true;
                setDBProcess();
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        if (GlobalMemberValues.getSavedPrinterName(Payment.context).toLowerCase().equals("no printer")
                                || GlobalMemberValues.getSavedPrinterName(Payment.context).toLowerCase().equals("star")) {
                            Payment.openPaymentReview(Payment.context);
                        } else {
                            String tempReceipttypeonnoselect = MainActivity.mDbInit.dbExecuteReadReturnString(
                                    " select receipttypeonnoselect from salon_storestationsettings_system "
                            );
                            switch (tempReceipttypeonnoselect) {
                                case "A" : {
                                    GlobalMemberValues.RECEIPTPRINTTYPE = "custmerc";
                                    break;
                                }
                                case "C" : {
                                    GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
                                    break;
                                }
                                case "M" : {
                                    GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
                                    break;
                                }
                                default : {
                                    GlobalMemberValues.RECEIPTPRINTTYPE = "";
                                    break;
                                }
                            }
                            //GlobalMemberValues.printReceiptByJHP(jsonroot, context, "payment");
                        }

                        // 여기 billsplitmerge 오류시 들어옴.. 쓸데없이 키친프린트 실행됨 수정해야함.

                        // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                        paymentcustomerselectHandler2.sendEmptyMessage(0);
                        // ---------------------------------------------------------------------------------
                    }
                });
                thread.start();
            }
        } else {
            //
            if (GlobalMemberValues.isCustomerSelectReceipt()) {
                openPaymentReview_NoReceipt(context);
            }
            //
            if (GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                    "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
                printKitchenOnly();
            } else {
                openPaymentReview(context);
            }

            // 데이터베이스 저장
            isaveDBInPayment = true;
            setDBProcess();

            PaymentCustomerSelectReceipt.finishPaymentPrev();
        }
        // -----------------------------------------------------------------------------------

        // 고객 영수증 선택 모드일 때는 돈통이 열리도록 한다.
        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            //    CommandButton.openCashDrawer();
        }

        GlobalMemberValues.logWrite("paymentJsonResult", "여기에.." + "\n");
        GlobalMemberValues.logWrite("paymentJsonResult", "결과물 : " + jsonroot + "\n");

        // 트랜잭션으로 DB 처리한다.
//        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
//        GlobalMemberValues.logWrite("dbinsertlog", "returnResult : " + returnResult + "\n");
//        String returnSalesCode = "";
//        if (returnResult == "N" || returnResult == "") {
//            returnSalesCode = "";
//        } else {
//            returnSalesCode = mSalesCode;
//
//            GlobalMemberValues.eloCfdScreenViewInit();
//            GlobalMemberValues.showMsgOnPaymentFinishment("Thanks for your purchase");
//        }
        return mSalesCode;
    }

    static Handler paymentcustomerselectHandler = new Handler() {
        public void handleMessage(Message msg) {
            GlobalMemberValues.logWrite("pageonenorderlog", "여기2" + "\n");
            // 프린트를 서비스로 실행..
            if (GlobalMemberValues.isOnlyLocalPrinting()) {
                // 로컬에서만 프린팅 될 경우
                // Service 에서 프린팅 되도록 처리한다..
                printing_service_exec();
            } else {                                                    // 네트워크 프린터가 연결되어 있을 경우
                // 프린팅 실행
                //GlobalMemberValues.printingExecute();
            }

            // 영수증 프린터와 주방프린터의 종류가 다를 경우에만 주방프린터를 실행한다.
            if (!GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                GlobalMemberValues.logWrite("pageonenorderlog", "여기4" + "\n");
                printKitchenOnly();
            }

            // 데이터베이스 저장
            //setDBProcess();
        }
    };

    static Handler paymentcustomerselectHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            // 프린트를 서비스로 실행..
            if (GlobalMemberValues.isOnlyLocalPrinting()) {
                // 로컬에서만 프린팅 될 경우
                // Service 에서 프린팅 되도록 처리한다..
                printing_service_exec();
            } else {                                                    // 네트워크 프린터가 연결되어 있을 경우
                // 프린팅 실행
                //GlobalMemberValues.printingExecute();
            }

            // 데이터베이스 저장
            //setDBProcess();
        }
    };

    // 데이터베이스 저장 메소드
    public static void setDBProcess() {
        GlobalMemberValues.logWrite("WANHAYEEXEDBLOG10JJJ", "여기라구요..." + "\n");

        if (Payment.strInsertQueryVec != null) {
            for (String _query : Payment.strInsertQueryVec) {
                GlobalMemberValues.logWrite("WANHAYEEXEDBLOG10JJJ", "쿼리 : " + _query + "\n----------------------" + "\n");
            }

            // bill 관련
            boolean isPossible2 = true;
            if (GlobalMemberValues.isPaymentByBills) {
                int tempBillCnt = GlobalMemberValues.getIntAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select count(*) from bill_list_paid where billidx = '" + GlobalMemberValues.mBill_Idx + "' "
                        )
                );
                if (tempBillCnt == 0) {
                    isPossible2 = true;
                } else {
                    isPossible2 = false;
                }
            }

            if (isPossible2) {
                Intent uploadTipoCloudIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_SaleDataSaveInDB_Service.class);
                GlobalMemberValues.CURRENTSERVICEINTENT_SAVEDATAINDB = uploadTipoCloudIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SAVEDATAINDB = MainActivity.mActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
                MainActivity.mActivity.startService(uploadTipoCloudIntent);
            }
        }
    }

    public static void printing_service_exec() {
        Intent tempIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_Printing.class);
        GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING = tempIntent;                             // 실행되는 서비스 인텐트를 저장해둔다.
        GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING = MainActivity.mActivity;         // 서비스를 실행시킨 액티비티를 저장해 둔다.
        MainActivity.mActivity.startService(tempIntent);

        GlobalMemberValues.logWrite("pageonenorderlog", "여기3" + "\n");
    }

    public static void printKitchenOnly() {
        // 키친 프린터가 Elo Pro 가 아닌 일반 Elo 일 경우 Payment Review 창을 띄우지 않는다.. ----------------------
        String tempOpenReviewYN = "Y";
        if (GlobalMemberValues.isEloPrinterKitchen()) {
            if (MainActivity.mProductInfo == null) {
                MainActivity.setEloInit();
            }
            ProductInfo productInfo = MainActivity.mProductInfo;
            if (productInfo != null) {
                EloPlatform platform = productInfo.eloPlatform;
                if (MainActivity.mApiAdapter == null) {
                    MainActivity.setEloInit();
                }

                switch (platform) {  // TODO: Base off detection, not platform
                    case PAYPOINT_1:
                        break;
                    case PAYPOINT_REFRESH:
                        tempOpenReviewYN = "N";
                        break;
                    case PAYPOINT_2:
                        //
                        break;
                    default:
                        //
                        break;
                }
            }
        }
        if (tempOpenReviewYN == "Y" || tempOpenReviewYN.equals("Y")) {
            openPaymentReview(context);
        }
        // --------------------------------------------------------------------------------------------------

        //CommandButton.openCashDrawer();
        GlobalMemberValues.logWrite("kitchenprintlog", "jsonsr : " + jsonroot_kitchen.toString() + "\n");

        // 키친 프린터를 서비스로 실행
        if (GlobalMemberValues.isOnlyLocalPrinting()) {
            GlobalMemberValues.logWrite("printKitchenOnly", "여기실행됨..1 \n");
            // 로컬에서만 프린팅 될 경우
            // Service 에서 프린팅 되도록 처리한다..
            if (!GlobalMemberValues.now_saletypeisrestaurant) {
                Intent tempIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_PrintingKitchen.class);
                GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING_KITCHEN = tempIntent;                             // 실행되는 서비스 인텐트를 저장해둔다.
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING_KITCHEN = MainActivity.mActivity;         // 서비스를 실행시킨 액티비티를 저장해 둔다.
                MainActivity.mActivity.startService(tempIntent);
                GlobalMemberValues.logWrite("pageonenorderlog", "여기5" + "\n");
            }
        } else {                                                    // 네트워크 프린터가 연결되어 있을 경우
            // 프린팅 실행
            //GlobalMemberValues.printGateByKitchen(jsonroot_kitchen, context, "kitchen1");
        }
    }

    public static void setOpenPaymentCreditCardActivity(String paramAmount) {
        openCreditCardActivity(mSalesCode, paramAmount, "");

        /**
         GlobalMemberValues.logWrite("setOpenPaymentCreditCardActivity", "paramAmount : " + paramAmount + "\n");

         Intent selectintent = new Intent(context.getApplicationContext(), PaymentCreditCardSelect.class);
         // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
         //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
         selectintent.putExtra("tempSalesCode", mSalesCode);
         selectintent.putExtra("paymentCreditCardPopupAmount", paramAmount);
         // -------------------------------------------------------------------------------------
         insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
         mActivity.startActivity(selectintent);
         mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
         **/
    }

    public static void openCreditCardActivity(String paramSalesCode, String paramAmount, String paramExeType) {
        Intent paymentCreditCardIntent = new Intent(MainActivity.mContext.getApplicationContext(), PaymentCreditCard.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        paymentCreditCardIntent.putExtra("tempSalesCode", paramSalesCode);
        paymentCreditCardIntent.putExtra("paymentCreditCardPopupAmount", paramAmount);
        paymentCreditCardIntent.putExtra("exetype", paramExeType);
        // -------------------------------------------------------------------------------------
        //insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
        mActivity.startActivity(paymentCreditCardIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    public static void openSelectPayActivity() {

        Intent selectintent = new Intent(MainActivity.mContext.getApplicationContext(), PaymentSelectPay.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        String tempBalance = GlobalMemberValues.getStringFormatNumber(Payment.mBalanceToPay * -1, "2");
        //paymentReviewIntent.putExtra("ParentMainMiddleService", this.getClass());
        selectintent.putExtra("tempSalesCode", Payment.mSalesCode);
        selectintent.putExtra("paymentCreditCardPopupAmount", tempBalance);
        // -------------------------------------------------------------------------------------
        MainActivity.mActivity.startActivity(selectintent);
        //MainActivity.mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
    }


    public static void setGroupVoidCardStart() {
        new AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage("You have a history of the cards you paid. Do you want to void?")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                setGroupVoidCard();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
                .show();
    }

    public static void setGroupVoidCard() {
        mCardPaymentInfoArrayList = null;
        mCardPaymentInfoArrayList = getCreditCardItemsArrayListForPax();
        String tempCardPaymentInfo = "";
        if (mCardPaymentInfoArrayList.size() > 0) {
            tempCardPaymentInfo = mCardPaymentInfoArrayList.get(0);
            if (!GlobalMemberValues.isStrEmpty(tempCardPaymentInfo)) {
                String[] strCardPaymentInfoArr = tempCardPaymentInfo.split(GlobalMemberValues.STRSPLITTER2);
                String tempIdx = strCardPaymentInfoArr[0];
                String tempRefNum = strCardPaymentInfoArr[1];
                String tempTid = strCardPaymentInfoArr[2];
                String tempPriceAmount = strCardPaymentInfoArr[3];
                if (!GlobalMemberValues.isStrEmpty(tempTid)) {
                    openJJJPaxPayForGroupVoid(tempRefNum, tempIdx, tempPriceAmount, "0");
                }
            }
        } else {
            setGroupVoidCardDBPorcess();
        }
    }

    public static void setGroupVoid(String paramGroupVoidCount) {
        int tempGroupVoidCount = GlobalMemberValues.getIntAtString(paramGroupVoidCount);
        int cardPaymentInfoArrayListCount = mCardPaymentInfoArrayList.size();
        if (cardPaymentInfoArrayListCount > (tempGroupVoidCount + 1)) {
            mCardPaymentInfoArrayList = null;
            mCardPaymentInfoArrayList = getCreditCardItemsArrayListForPax();
            String tempCardPaymentInfo = mCardPaymentInfoArrayList.get(tempGroupVoidCount + 1);
            if (!GlobalMemberValues.isStrEmpty(tempCardPaymentInfo)) {
                String[] strCardPaymentInfoArr = tempCardPaymentInfo.split(GlobalMemberValues.STRSPLITTER2);
                String tempIdx = strCardPaymentInfoArr[0];
                String tempRefNum = strCardPaymentInfoArr[1];
                String tempTid = strCardPaymentInfoArr[2];
                String tempPriceAmount = strCardPaymentInfoArr[3];
                if (!GlobalMemberValues.isStrEmpty(tempTid)) {
                    openJJJPaxPayForGroupVoid(tempRefNum, tempIdx, tempPriceAmount, String.valueOf(tempGroupVoidCount + 1));
                }
            }
        } else {
            setGroupVoidCardDBPorcess();
        }
    }

    public static void openJJJPaxPayForGroupVoid(String paramRefNum, String paramTagValue, String paramPriceAmount, String paramGroupVoidCount) {
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

        Intent intent = new Intent(context, JJJ_PaxPay.class);
        // 객체 및 데이터 전달하기 ---------------------------------------------------------------
        intent.putExtra("cardtendertype", "CREDIT");
        intent.putExtra("processtype", "VOID");
        intent.putExtra("amounttopay", "");
        intent.putExtra("refnum", paramRefNum);
        intent.putExtra("ecrrefnum", mSalesCode);
        intent.putExtra("salonsalescardidx", paramTagValue);
        intent.putExtra("priceamountforvoid", paramPriceAmount);
        intent.putExtra("selectedcardtagname", paramTagValue);
        intent.putExtra("groupvoidcount", paramGroupVoidCount);
        intent.putExtra("groupvoidyn", "Y");
        intent.putExtra("paymentpaidyn", "Y");
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(intent);
    }

    public static ArrayList<String> getCreditCardItemsArrayListForPax() {
        ArrayList<String> cardPaymentInfoArrayList = new ArrayList<String>();

        String selectedSalesCode = "";
        selectedSalesCode = mSalesCode;
        String salonSalesCardQuery = "";
        // 온라인 카드 결제가 있으면 카드 API 에서 void 처리후 salon_sales_card 에서 void 처리한다. ---------------------------------------------------
        salonSalesCardQuery = "select idx, cardRefNumber, tid, priceAmount " +
                " from salon_sales_card " +
                " where salesCode = '" + selectedSalesCode + "' " +
                " and not(cardCom = 'OFFLINECARD') ";
        Cursor salonSalesCardCursor;
        salonSalesCardCursor = dbInit.dbExecuteRead(salonSalesCardQuery);
        while (salonSalesCardCursor.moveToNext()) {
            String salonSalesCard_idx = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(0), 1);
            String salonSalesCard_cardRefNumber = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(1), 1);
            String salonSalesCard_tid = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(2), 1);
            String salonSalesCard_priceAmount = GlobalMemberValues.getDBTextAfterChecked(salonSalesCardCursor.getString(3), 1);

            String cardPaymentInfo = salonSalesCard_idx + "|||" + salonSalesCard_cardRefNumber + "|||" + salonSalesCard_tid + "|||" + salonSalesCard_priceAmount;
            cardPaymentInfoArrayList.add(cardPaymentInfo);
        }
        salonSalesCardCursor.close();
        //setCreditCardProcessVoid(arrayRefNumbers);
        // --------------------------------------------------------------------------------------------------------------------------------------

        return cardPaymentInfoArrayList;
    }

    public static void setGroupVoidCardDBPorcess() {
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String strDeleteQuery = "";
        strDeleteQuery = "delete from salon_sales_card where salesCode = '" + mSalesCode + "' ";
        strUpdateQueryVec.addElement(strDeleteQuery);
        strDeleteQuery = "delete from salon_sales_tip where salesCode = '" + mSalesCode + "' ";
        strUpdateQueryVec.addElement(strDeleteQuery);
        for (String tempQuery : strUpdateQueryVec) {
            GlobalMemberValues.logWrite("PaymentCreditcardQueryString", "delete query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
        if (returnResult == "N" || returnResult == "") {        // DB (salon_sales_card 테이블) 입력실패
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
        } else {                                                // DB (salon_sales_card 테이블) 입력성공
            mCardPaidYN = "N";
            mJsonList = null;

            mFocustEditText = paymentCardEditText;

            mIsPaymentTypeCard = "N";
            mCardUseYN = "N";
            paymentCardLinearLayout.setBackgroundResource(R.drawable.roundlayout_line_noround);
            paymentCardEditText.setText("");
            mBalanceToPayForCardPoint = 0.0;

            setPaymentPrice();

            mCardPaymentInfoArrayList = null;
        }
    }

    public static void setDeliveryPickUpLn(String paramDeliveryPickUpTxt, double paramDeliveryPickupFee) {
        if (paramDeliveryPickupFee > 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPLN.setVisibility(View.VISIBLE);
            // jihun delivery togo fee 추가
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN.setVisibility(View.VISIBLE);
                }
            }
            // jihun delivery togo fee 추가 끝
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPLN.setVisibility(View.GONE);
            // jihun delivery togo fee 추가
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN.setVisibility(View.GONE);
                }
            }
            // jihun delivery togo fee 추가 끝
        }
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPTEXTTV.setText(paramDeliveryPickUpTxt);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.setText("$" + GlobalMemberValues.getCommaStringForDouble(paramDeliveryPickupFee + ""));
        double tempSubtotal = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString());
        double tempTax = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText().toString());
        double tempCommonGratuity = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.getText().toString());
        double tempTotal = tempSubtotal + tempTax + paramDeliveryPickupFee + tempCommonGratuity;
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempTotal + ""));

        // jihun delivery togo fee 추가
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPTEXTTV != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPTEXTTV.setText(paramDeliveryPickUpTxt);
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPPRICETV != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPPRICETV.setText("$" + GlobalMemberValues.getCommaStringForDouble(paramDeliveryPickupFee + ""));
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText(GlobalMemberValues.getCommaStringForDouble(tempTotal + ""));
        }
        // jihun delivery togo fee 추가// 끝

        if (paramDeliveryPickupFee > 0) {
            String strLine1 = GlobalMemberValues.getCommaStringForDouble((tempSubtotal + tempTax) + "")
                    + " + " + GlobalMemberValues.getCommaStringForDouble(paramDeliveryPickupFee + "");
            String strLine2 = "Total " + GlobalMemberValues.getCommaStringForDouble(tempTotal + "");
            GlobalMemberValues.eloCfdScreenViewOn(strLine1, strLine2);
        }
    }

    public void checkEpsonPrinterKitchen(){
        if (GlobalMemberValues.kitchenprintername1.equals("Epson-T88")){
            EpsonPrinterKitchen1 epsonPrinterKitchen1 = new EpsonPrinterKitchen1(context);
            epsonPrinterKitchen1.connectTestPrinter();
        }
        if (GlobalMemberValues.kitchenprintername2.equals("Epson-T88")){
            EpsonPrinterKitchen2 epsonPrinterKitchen2 = new EpsonPrinterKitchen2(context);
            epsonPrinterKitchen2.connectTestPrinter();
        }
        if (GlobalMemberValues.kitchenprintername3.equals("Epson-T88")){
            EpsonPrinterKitchen3 epsonPrinterKitchen3 = new EpsonPrinterKitchen3(context);
            epsonPrinterKitchen3.connectTestPrinter();
        }
        if (GlobalMemberValues.kitchenprintername4.equals("Epson-T88")){
            EpsonPrinterKitchen4 epsonPrinterKitchen4 = new EpsonPrinterKitchen4(context);
            epsonPrinterKitchen4.connectTestPrinter();
        }
        if (GlobalMemberValues.kitchenprintername5.equals("Epson-T88")){
            EpsonPrinterKitchen5 epsonPrinterKitchen5 = new EpsonPrinterKitchen5(context);
            epsonPrinterKitchen5.connectTestPrinter();
        }
    }

    // 초기화 메소드
    public static void setInit() {
        mFocustEditText = paymentCashEditText;

        if (Payment.pmProDial != null && Payment.pmProDial.isShowing()) {
            Payment.pmProDial.dismiss();
        }

        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, mFocustEditText);

        if (paymentBalanceEditText == null) {
            paymentBalanceEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentBalanceEditTextTag");
        }
        if (paymentTenderedEditText == null) {
            paymentTenderedEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentTenderedEditTextTag");
        }
        if (paymentChangeEditText == null) {
            paymentChangeEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentChangeEditTextTag");
        }
        if (paymentCashEditText == null) {
            paymentCashEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentCashEditTextTag");
        }
        if (paymentCardEditText == null) {
            paymentCardEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentCardEditTextTag");
        }
        if (paymentGiftCardEditText == null) {
            paymentGiftCardEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentGiftCardEditTextTag");
        }
        if (paymentCheckEditText == null) {
            paymentCheckEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentCheckEditTextTag");
        }
        if (paymentPointEditText == null) {
            paymentPointEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                    .findViewWithTag("paymentPointEditTextTag");
        }

        paymentBalanceEditText.setText("");
        paymentTenderedEditText.setText("");
        paymentChangeEditText.setText("");
        paymentCashEditText.setText("");
        paymentCardEditText.setText("");
        paymentGiftCardEditText.setText("");
        paymentCheckEditText.setText("");
        paymentPointEditText.setText("");

        //paymentReceiptSwitch.setChecked(true);
        //paymentOfflineSwitch.setChecked(true);

        // 임시저장값
        mTempPriceValue = "";
        mTempPriceValue2 = "";

        // 사용할 결제수단
        mIsPaymentTypeCash       = "N";
        mIsPaymentTypeCard       = "N";
        mIsPaymentTypeGiftCard   = "N";
        mIsPaymentTypeCheck      = "N";
        mIsPaymentTypePoint      = "N";

        // 결제수단 LinearLayout 색상 초기화
        setSelectedLinearLayoutBGColor(0);

        // 카드결제 Tid 번호 초기화
        mCardTidNumber = "";

        // JSON 초기화
        jsonroot = null;

        mCardPaidYN = "N";
        GlobalMemberValues.logWrite("payidynlog", "Paymnet SetInit 에서 mCardPaidYN 을 N 으로변경함" + "\n");

        mCardUseYN = "N";

        mBalanceToPayForCardPoint = 0.0;

        mCardPaymentInfoArrayList = null;

        paymentSuButtonV.setEnabled(true);

        GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";

        paymentCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        paymentCheckTitleTextView.setText("OTHER");

        mCheckCompany = "";

        // Cash Pad 관련 부분 초기화
        cashpadLn.setVisibility(View.GONE);

        //mTextChnage = "0.00";
        //mTextCustomerId = "";

        pagerNumEv.setText("");

        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        GlobalMemberValues.logWrite("payidynlog", "여기실행됨... paymnet init();" + "\n");

        Payment.isaveDBInPayment = false;
    }

    public static void paymentComplite_LogSave(boolean isCardFinish){
        double tempCashAmt = GlobalMemberValues.getDoubleAtString(paymentCashEditText.getText().toString());
        double tempCardAmt = GlobalMemberValues.getDoubleAtString(paymentCardEditText.getText().toString());
        double tempGiftCardAmt = GlobalMemberValues.getDoubleAtString(paymentGiftCardEditText.getText().toString());
        double tempPointAmt = GlobalMemberValues.getDoubleAtString(paymentPointEditText.getText().toString());
        double tempCheckAmt = GlobalMemberValues.getDoubleAtString(paymentCheckEditText.getText().toString());
        LogsSave.setPaymentKind(tempCashAmt, tempCardAmt, tempGiftCardAmt, tempPointAmt, tempCheckAmt, isCardFinish);

        LogsSave.saveLogsInDB(207);
    }

}
