package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentCustomerSelectReceipt extends Activity {
    static Activity mActivity;
    static Context mContext;

    final String TAG = "PaymentCustomerSelectReceipt";

    // DB 객체 선언
    DatabaseInit dbInit;

    private TextView paymentReviewChangeTextView;
    private TextView paymentReviewGiftcardBalanceTextView;

    private static Button closeBtn;
    private Button noreceiptButton, paperreceiptButton, emailreceiptButton, smsreceiptButton;
    private Button customerreceiptButton, merchantreceiptButton, customermerchantreceiptButton;

    private Button split_merchantreceiptButton;

    private LinearLayout parentLn;
    private LinearLayout receiptLn1, receiptLn2;

    Intent mIntent;

    // 인텐트로 넘겨받은 값
    String paymentChangeAmount = "0.0";
    JSONObject jsonReceipt = null;
    String receiptYN = "";
    String giftCardNumberUsed = "";
    String customerId = "";
    String customerEmail = "";
    String customerPhoneNo = "";
    String phoneOrder = "";

    // SMS, Email 창에서 사용할 JSONObject
    public static JSONObject tempJsonReceipt = null;

    public static String htmlStr = "";

    public static String mSalesCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int parentLnWidth = 0;
        int parentLnHeight = 0;

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 8;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 8;
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        if (GlobalMemberValues.isDeviceClover()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            // orientation 에 따라 레이아웃 xml 동적으로 적용.
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_payment_customer_select_receipt);
            } else {
                setContentView(R.layout.activity_payment_customer_select_receipt2);

                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 10;
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 10;
            } // end if
        } else {
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                setContentView(R.layout.activity_payment_customer_select_receipt3);
            } else {
                setContentView(R.layout.activity_payment_customer_select_receipt);
            }
        }
        this.setFinishOnTouchOutside(false);

        parentLn = (LinearLayout)findViewById(R.id.paymnetReviewLinearLayout);

        /**
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn.setLayoutParams(parentLnParams);
         **/

        jsonReceipt = null;

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            String jsonReceiptString = mIntent.getStringExtra("JsonReceipt");
            if (!GlobalMemberValues.isStrEmpty(jsonReceiptString)) {
                try {
                    jsonReceipt = new JSONObject(jsonReceiptString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            receiptYN = mIntent.getStringExtra("receiptYN");
            giftCardNumberUsed = mIntent.getStringExtra("GiftCardNumberUsed");
            // 이메일이 곧 아이디임.. (이전버전에는 이메일이 아이디였음......)
            customerId = mIntent.getStringExtra("CustomerEmail");
            customerEmail = mIntent.getStringExtra("CustomerEmailReal");
            customerPhoneNo = mIntent.getStringExtra("CustomerPhoneNo");
            phoneOrder = mIntent.getStringExtra("PhoneOrder");

            GlobalMemberValues.logWrite(TAG, "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 receiptYN : " + receiptYN + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 giftCardNumberUsed : " + giftCardNumberUsed + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerId : " + customerId + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerEmail : " + customerEmail + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerPhoneNo : " + customerPhoneNo + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 phoneOrder : " + phoneOrder + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 jsonReceipt : " + jsonReceipt + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite(TAG, "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        // 고객 이메일 가져오기
        if (GlobalMemberValues.isStrEmpty(customerEmail)) {
            if (!GlobalMemberValues.isStrEmpty(customerId)) {
                customerEmail = dbInit.dbExecuteReadReturnString("select email from member2 where uid = '" + customerId + "' ");
            }
        }

        setContents();

        // finish 에서 옮겨옴.
        if (!Payment.isaveDBInPayment) {
            // 세일 데이터 DB 처리
            Payment.setDBProcess();
        }
    }

    public void setContents() {
        // 07212024 - TOrder Send Data
        // GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity);


        // 102022 이곳에
        GlobalMemberValues.setGoneForCardCashPayView();

        // 돈통열기 cashdrawer
        //CommandButton.openCashDrawer();

        // Receipt Printing 타입 초기화
        GlobalMemberValues.RECEIPTPRINTTYPE = "";

        GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
        /**
        if (GlobalMemberValues.isAutoReceiptPrinting()) {
            GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "Y";
            GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");
        }
         **/

        /**
        String tempSettingKitchenPrinter1 = GlobalMemberValues.getSettingKitchenPrinter("1");
        if (!tempSettingKitchenPrinter1.toUpperCase().equals("POSBANK")) {
            CommandButton.openCashDrawer();
        }
         **/

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        if (phoneOrder.equals("Y") || phoneOrder == "Y") {
            // salon_sales 테이블에 키친프린터 했다는 정보저장 --------------------------------
            String str_receiptno = "";
            try {
                str_receiptno = jsonReceipt.getString("receiptno");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GlobalMemberValues.logWrite("phoneorderkitchenprintedlog5", "str_receiptno : " + str_receiptno + "\n");
            GlobalMemberValues.setKitchenPrintedChangeStatus(str_receiptno, "POS");
            // ---------------------------------------------------------------------------
        }

        // receipt_no (salescode) 를 json 으로부터 가져와서
        // salon_sales 테이블의 receiptJson 에 넘겨받은 json 을 저장한다.
//        String updatedResult = setReceiptJsonAtSalonSales();
//        GlobalMemberValues.logWrite("LogAboutreceiptJson", "updatedResult : " + updatedResult + "\n");

        // 영수증 html 읽어오기 ---------------------------------------------------------------------------------
        String js_creditcardtendered = null;
        try {
            js_creditcardtendered = jsonReceipt.getString("creditcardtendered");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String htmlUrlAddress = "";
        if (!GlobalMemberValues.isStrEmpty(js_creditcardtendered)
                && GlobalMemberValues.getDoubleAtString(js_creditcardtendered) > 0) {
            htmlUrlAddress = GlobalMemberValues.CLOUD_SERVER_URL + "receipt/posreceipthtml_cashcard.html";
        } else {
            htmlUrlAddress = GlobalMemberValues.CLOUD_SERVER_URL + "receipt/posreceipthtml_cash.html";
        }

        // 웹에서 영수증용 html 읽어오기
        HtmlRead htmlRead = new HtmlRead();
        htmlRead.readHTML(htmlUrlAddress);

        GlobalMemberValues.logWrite(TAG, "read html : " + htmlStr + "\n");
        // ----------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        paymentReviewChangeTextView = (TextView)findViewById(R.id.paymentReviewChangeTextView);
        paymentReviewGiftcardBalanceTextView = (TextView)findViewById(R.id.paymentReviewGiftcardBalanceTextView);

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.customerselectreceiptCloseBtn);
        closeBtn.setOnClickListener(paymentReviewBtnClickListener);

        // Email 버튼 및 리스너 연결
        noreceiptButton = (Button)findViewById(R.id.noreceiptButton);
        paperreceiptButton = (Button)findViewById(R.id.paperreceiptButton);
        customerreceiptButton = (Button)findViewById(R.id.customerreceiptButton);
        merchantreceiptButton = (Button)findViewById(R.id.merchantreceiptButton);
        split_merchantreceiptButton = (Button)findViewById(R.id.split_merchantreceiptButton);
        customermerchantreceiptButton = (Button)findViewById(R.id.customermerchantreceiptButton);
        emailreceiptButton = (Button)findViewById(R.id.emailreceiptButton);
        smsreceiptButton = (Button)findViewById(R.id.smsreceiptButton);

        noreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        paperreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        customerreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        merchantreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        split_merchantreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        customermerchantreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        emailreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        smsreceiptButton.setOnClickListener(paymentReviewBtnClickListener);

        receiptLn1 = (LinearLayout)findViewById(R.id.receiptLn1);
        receiptLn2 = (LinearLayout)findViewById(R.id.receiptLn2);

        receiptLn1.setVisibility(View.GONE);
        receiptLn2.setVisibility(View.VISIBLE);

        /**
        if (GlobalMemberValues.isDeviceElo()) {
            if (GlobalMemberValues.isEloPro()) {
                receiptLn1.setVisibility(View.GONE);
                receiptLn2.setVisibility(View.VISIBLE);
            } else {
                receiptLn1.setVisibility(View.VISIBLE);
                receiptLn2.setVisibility(View.GONE);
            }
        } else {
            receiptLn1.setVisibility(View.VISIBLE);
            receiptLn2.setVisibility(View.GONE);
        }
         **/

        /***********************************************************************************************************/

        // 잔액 (Change) 표시
        String tempChange = "$" + GlobalMemberValues.getCommaStringForDouble(paymentChangeAmount);
        paymentReviewChangeTextView.setText(tempChange);

        if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            paymentReviewChangeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 50 + GlobalMemberValues.globalAddFontSizeForPAX());
        } else {
            if (GlobalMemberValues.isBigScreen()) {
                paymentReviewChangeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 50 + GlobalMemberValues.globalAddFontSizeForPAX());
            } else {
                paymentReviewChangeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 50 + GlobalMemberValues.globalAddFontSizeForPAX());
            }
        }

        // 기프트카드 잔액 표시 (기프타카드를 사용하였을 경우에만..)
        if (!GlobalMemberValues.isStrEmpty(giftCardNumberUsed)) {
            String tempSql = "select remainingPrice from salon_storegiftcard_number " +
                    " where gcNumber = '" + giftCardNumberUsed + "' ";
            String balanceGiftCard = dbInit.dbExecuteReadReturnString(tempSql);
            if (!GlobalMemberValues.isStrEmpty(balanceGiftCard)) {
                String tempBalanceGiftcardMsg = "Giftcard remaining balance is " +
//                        " $" + GlobalMemberValues.getCommaStringForDouble(balanceGiftCard) +
                        " $" + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.GIFTCARD_AFTER_BALANCE, "2") +
                        " (#" + giftCardNumberUsed + ")";
                paymentReviewGiftcardBalanceTextView.setText(tempBalanceGiftcardMsg);
            }
        }

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            LinearLayout chargeSpaceLn = (LinearLayout)findViewById(R.id.chargeSpaceLn);
            chargeSpaceLn.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,  1.0f));
        }

        // jihun park 0812 Merchant 영수증 프린팅 막음.
        // 듀얼 디스플레이를 사용하는 경우 Change 부분이 나오는 텍스트뷰를 터치하면 merchant 영수증이 프린팅 되도록 한다.
//        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
//            paymentReviewChangeTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    merchantReceiptPrinting();
//                }
//            });
//        }

        if (jsonReceipt.toString().contains("receiptno")) {
            try {
                mSalesCode = jsonReceipt.getString("receiptno");
                GlobalMemberValues.logWrite("uploaddatalog", "mSalesCode : " + mSalesCode + "\n");
            } catch (JSONException e) {
                mSalesCode = "";
                e.printStackTrace();
            }
        }

        GlobalMemberValues.logWrite("pageonenorderlog", "여기실행은 언제...1" + "\n");

        // 리스트뷰를 비운다.
        MainMiddleService.clearListView();

        // 프레젠테이션 닫기
        GlobalMemberValues.dismissPresentationView();

        // Payment 초기화
        Payment.setInit();

        GlobalMemberValues.logWrite("pageonenorderlog", "여기실행은 언제...2" + "\n");

    }

    public String setReceiptJsonAtSalonSales() {
        String returnValue = "Y";
        if (jsonReceipt != null) {
            if (jsonReceipt.toString().contains("receiptno")){
                String tempSalesCode = "";
                try {
                    tempSalesCode = jsonReceipt.getString("receiptno");
                } catch (JSONException e) {
                    tempSalesCode = "";
                    e.printStackTrace();
                }

                if (!GlobalMemberValues.isStrEmpty(tempSalesCode)) {
//                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String strSql = "update salon_sales set " +
                            " receiptJson = '" + jsonReceipt.toString() + "' " +
                            " where salescode = '" + tempSalesCode + "' ";
                    Payment.strInsertQueryVec.addElement(strSql);
                    //strUpdateQueryVec.addElement(strSql);
//                    for (String tempQuery : Payment.strInsertQueryVec) {
//                        GlobalMemberValues.logWrite("SalonSalesTableUpdate", "query : " + tempQuery + "\n");
//                    }
                    // 트랜잭션으로 DB 처리한다.
                    //String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);

                    returnValue = "Y";
//                    if (returnResult == "N" || returnResult == "") {
//                        GlobalMemberValues.logWrite("SalonSalesTableUpdate", "실패" + "\n");
//                        returnValue = "N";
//                    } else {
//                        GlobalMemberValues.logWrite("SalonSalesTableUpdate", "성공" + "\n");
//                        returnValue = "Y";
//                    }
                }
            }
        }

        return returnValue;
    }

    View.OnClickListener paymentReviewBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerselectreceiptCloseBtn : {
                    if (closeBtn != null) {
                        closeBtn.setEnabled(false);
                    }

                    // 키친 프린터 실행 ----------------------------------------------------------------------------
                    // 키친 프린터가 실행되지 않았을 경우에만 실행
                    String tempSalesCode = "";
                    tempSalesCode = GlobalMemberValues.getDataInJsonData(Payment.jsonroot_kitchen, "receiptno");

                    GlobalMemberValues.logWrite("kitchenprintedlogs", "tempSalesCode : " + tempSalesCode + "\n");
                    GlobalMemberValues.logWrite("kitchenprintedlogs", "GlobalMemberValues.SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");
                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                            || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                        if (!GlobalMemberValues.isKitchenPrinted(tempSalesCode)) {
                            GlobalMemberValues.logWrite("kitchenprintedlogs", "여기실행됨..." + "\n");

                            GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                            // 주방프린트 하기 ----------------------------------------------------------------
                            GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                            // -------------------------------------------------------------------------------
                        }
                    }
                    //---------------------------------------------------------------------------------------------
                    //------------------------------------- 메뉴 프린트 실행

                    GlobalMemberValues globalMemberValues = new GlobalMemberValues();
                    if (!(globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R"))){
                        GlobalMemberValues.printReceiptByJHP_menu(jsonReceipt, mContext, "menu_print");
                    }
                    //--------------------------------------------------------------------------------------------

                    if (GlobalMemberValues.isUseLabelPrinter() && GlobalMemberValues.is_printed_label == false){
                        // json 쪼개기..
                        JSONArray temp_array = new JSONArray();
                        if (Payment.jsonrootForPaymentReview != null){
                            temp_array = GlobalMemberValues.labelPrint_menuSplit(Payment.jsonrootForPaymentReview);
                        } else {
                            temp_array = GlobalMemberValues.labelPrint_menuSplit(jsonReceipt);
                        }
                        if (temp_array != null && temp_array.length() != 0){
//            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");

                            GlobalMemberValues.printLabel1to5(temp_array);
                        }
                    }
                    GlobalMemberValues.is_printed_label = true;
                    GlobalMemberValues.mOnlineOrder = "N";

                    finishPayment();

                    break;
                }
                case R.id.noreceiptButton : {

                    if (GlobalMemberValues.isUseLabelPrinter()){
                        // json 쪼개기..
                        JSONArray temp_array = new JSONArray();
                        if (Payment.jsonrootForPaymentReview != null){
                            temp_array = GlobalMemberValues.labelPrint_menuSplit(Payment.jsonrootForPaymentReview);
                        } else {
                            temp_array = GlobalMemberValues.labelPrint_menuSplit(jsonReceipt);
                        }
                        if (temp_array != null && temp_array.length() != 0){
//            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");

                            GlobalMemberValues.printLabel1to5(temp_array);
                        }
                    }
                    GlobalMemberValues.is_printed_label = true;

                    if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "Y");
                    } else {
                        finishPayment();
                    }

                    break;
                }
                case R.id.paperreceiptButton : {
                    GlobalMemberValues.RECEIPTPRINTTYPE = "";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");

                    if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "Y");
                    }

                    break;
                }
                case R.id.customerreceiptButton : {
                    if (customerreceiptButton != null) {
                        customerreceiptButton.setEnabled(false);
                    }

                    GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");

                    if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "Y");
                    }

                    finishPayment();
                    break;
                }
                case R.id.merchantreceiptButton : {
                    if (merchantreceiptButton != null) {
                        merchantreceiptButton.setEnabled(false);
                    }

                    merchantReceiptPrinting();
                    break;
                }
                case R.id.customermerchantreceiptButton : {
                    if (customermerchantreceiptButton != null) {
                        customermerchantreceiptButton.setEnabled(false);
                    }

                    GlobalMemberValues.RECEIPTPRINTTYPE = "custmerc";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");

                    GlobalMemberValues.is_printed_label = true;

                    if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "Y");
                    }
                    break;
                }
                case R.id.emailreceiptButton : {
                    tempJsonReceipt = jsonReceipt;

                    Intent paymentcustomerselectreceiptIntent = new Intent(mContext.getApplicationContext(), PaymentEmailReceipt.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    paymentcustomerselectreceiptIntent.putExtra("CustomerEmail", customerId);
                    paymentcustomerselectreceiptIntent.putExtra("CustomerEmailReal", customerEmail);
                    if (jsonReceipt != null) {
                        paymentcustomerselectreceiptIntent.putExtra("JsonReceipt", jsonReceipt.toString());
                    }
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(paymentcustomerselectreceiptIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    /**
                     * 이메일 영수증 보내기 창에서 실행되도록 수정 - 05/24/2019
                     // Merchant 용 영수증 프린팅 ----------------------------------------------------------------
                     GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "Y";
                     GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");
                     // ----------------------------------------------------------------------------------------
                     **/

                    break;
                }
                case R.id.smsreceiptButton: {
                    tempJsonReceipt = jsonReceipt;

                    Intent paymentcustomerselectreceiptIntent = new Intent(mContext.getApplicationContext(), PaymentSMSReceipt.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    paymentcustomerselectreceiptIntent.putExtra("CustomerId", customerId);
                    paymentcustomerselectreceiptIntent.putExtra("CustomerPhoneNo", customerPhoneNo);
                    if (jsonReceipt != null) {
                        paymentcustomerselectreceiptIntent.putExtra("JsonReceipt", jsonReceipt.toString());
                    }
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(paymentcustomerselectreceiptIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    /**
                     * SMS 영수증 보내기 창에서 실행되도록 수정 - 05/24/2019
                    // Merchant 용 영수증 프린팅 ----------------------------------------------------------------
                    GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "Y";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");
                    // ----------------------------------------------------------------------------------------
                     **/

                    break;
                }
                case R.id.split_merchantreceiptButton: {
                    merchantReceiptPrinting();
                    break;
                }
            }
        }
    };

    public void merchantReceiptPrinting() {
        GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
        GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");

        GlobalMemberValues.is_printed_label = true;

        if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "Y");
        }
    }

    public static void finishPaymentPrev() {
        // Restaurant 관련 ---------------------------
        GlobalMemberValues.now_iskitchenprinting = true;
        GlobalMemberValues.is_printed_label = false;

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
            TableSaleMain.setInitValuesForRestaurant();
            TableSaleMain.setInitValues();

            //globalMemberValues.openRestaurantTable();
            GlobalMemberValues.isOpenTableSaleMain = true;

            GlobalMemberValues.mIsOnPaymentProcessForBillPay = true;
            GlobalMemberValues.logWrite("billpaycontinuelogjjj", "on payment review : " + GlobalMemberValues.mIsOnPaymentProcessForBillPay + "\n");
        }
        // -------------------------------------------

//        onCreate 에서 옮겨옴.
//        if (!Payment.isaveDBInPayment) {
//            // 세일 데이터 DB 처리
//            Payment.setDBProcess();
//        }

        Payment.setInit();

        // 프레젠테이션 열기
        //GlobalMemberValues.showPresentationView();

        // 리스트뷰를 비운다.
        MainMiddleService.clearListView();

        if (GlobalMemberValues.isUploadOnlySaledData()) {
            GlobalMemberValues.logWrite("uploaddatalog", "mSalesCode : " + mSalesCode + "\n");
            GlobalMemberValues.RECEIPTNOFORUPLOAD = mSalesCode;
            GlobalMemberValues.logWrite("uploaddatalog", "GlobalMemberValues.RECEIPTNOFORUPLOAD - 2 : " + GlobalMemberValues.RECEIPTNOFORUPLOAD + "\n");
        }
        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(Payment.context, Payment.mActivity);

        // 세일후 백업여부
        GlobalMemberValues.BACKUPAFTERSALE = "Y";

        GlobalMemberValues.CARD_PROCESSING_STEP = 1;

        if (GlobalMemberValues.ispaidtip) {
            // One Time Tip Adjustment 실행
            GlobalMemberValues globalmem = new GlobalMemberValues();
            globalmem.setOneTimeTipAdjustment();

            // 팁을 줬는지 여부를 false 로 변경
            GlobalMemberValues.ispaidtip = false;
        }
    }

    public static void finishPayment() {
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            //jihun park sub display
            MainActivity.updatePresentation();
        }


        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            GlobalMemberValues.openThankYouPage(mContext, mActivity);
            mActivity.finish();
        } else {
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
            mActivity.finish();

        }

        finishPaymentPrev();

    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalMemberValues.logWrite("jihun park test", "PaymentCustomerSelectReceipt OnResume");

        if (Payment.pmProDial != null && Payment.pmProDial.isShowing()) {
            Payment.pmProDial.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalMemberValues.logWrite("jihun park test", "PaymentCustomerSelectReceipt OnDestory");

        if (Payment.pmProDial != null && Payment.pmProDial.isShowing()) {
            Payment.pmProDial.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishPayment();
    }
}
