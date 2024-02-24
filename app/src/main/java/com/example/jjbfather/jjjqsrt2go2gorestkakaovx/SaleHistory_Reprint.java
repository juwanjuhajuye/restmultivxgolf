package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ScrollView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SaleHistory_Reprint extends Activity {
    static Activity mActivity;
    Context mContext;

    final String TAG = "PaymentCustomerSelectReceipt";

    // DB 객체 선언
    DatabaseInit dbInit;

    private TextView paymentReviewChangeTextView;
    private TextView paymentReviewGiftcardBalanceTextView;

    private Button closeBtn;
    private Button noreceiptButton, paperreceiptButton, emailreceiptButton, smsreceiptButton;
    private Button customerreceiptButton, merchantreceiptButton;
    private Button customerselectmenuprint;

    private LinearLayout parentLn, reprint_receipt_Ln;
    private ScrollView reprint_receipt_scroll;

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

    String mVoidReturnYN = "N";


    public static String htmlStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_sale_history__reprint);
        this.setFinishOnTouchOutside(false);
        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 45;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.paymnetReviewLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        jsonReceipt = null;

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            String jsonReceiptString = mIntent.getStringExtra("JsonReceipt");
            if (!GlobalMemberValues.isStrEmpty(jsonReceiptString)) {
                try {
                    jsonReceipt = new JSONObject(jsonReceiptString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mVoidReturnYN = mIntent.getStringExtra("voidreturnyn");
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

        setContents();
    }

    public void setContents() {
        // 돈통열기 cashdrawer
        CommandButton.openCashDrawer();

        // Receipt Printing 타입 초기화
        GlobalMemberValues.RECEIPTPRINTTYPE = "";

        GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.customerselectreceiptCloseBtn);
        closeBtn.setOnClickListener(paymentReviewBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // receipt
        GlobalMemberValues.B_ON_REPRINT = true;
        reprint_receipt_scroll = (ScrollView)findViewById(R.id.reprint_receipt_scroll);
        reprint_receipt_scroll.setVerticalScrollBarEnabled(false);



//        reprint_receipt_Ln = (LinearLayout)findViewById(R.id.reprint_receipt);
        LinearLayout getPrintingLn = null;
        getPrintingLn = CloverMakingViewInPrinting.makingLinearLayout(jsonReceipt, "sales", "customer");
        int px = 510;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

        getPrintingLn.setDrawingCacheEnabled(true);
        getPrintingLn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());
        getPrintingLn.setGravity(Gravity.CENTER);

        Bitmap bitMap = Bitmap.createBitmap(px, getPrintingLn.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        getPrintingLn.draw(canvas);
        reprint_receipt_scroll.addView(getPrintingLn);
        GlobalMemberValues.B_ON_REPRINT = false;

        // Email 버튼 및 리스너 연결
        customerreceiptButton = (Button)findViewById(R.id.customerreceiptButton);
        merchantreceiptButton = (Button)findViewById(R.id.merchantreceiptButton);

        customerreceiptButton.setOnClickListener(paymentReviewBtnClickListener);
        merchantreceiptButton.setOnClickListener(paymentReviewBtnClickListener);

        customerselectmenuprint = (Button)findViewById(R.id.customerselectmenuprint);
        customerselectmenuprint.setOnClickListener(paymentReviewBtnClickListener);

        /***********************************************************************************************************/
    }

    View.OnClickListener paymentReviewBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempType = "payment";
            if (mVoidReturnYN != null) {
                if (mVoidReturnYN == "Y" || mVoidReturnYN.equals("Y")) {
                    tempType = "void";
                }
            }

            switch (v.getId()) {
                case R.id.customerselectreceiptCloseBtn : {
                    finishPayment();
                    break;
                }
                case R.id.noreceiptButton : {
                    finishPayment();
                    break;
                }
                case R.id.customerreceiptButton : {
                    GlobalMemberValues.mReReceiptprintYN = "Y";
                    GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");
                    break;
                }
                case R.id.merchantreceiptButton : {
                    GlobalMemberValues.mReReceiptprintYN = "Y";
                    GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
                    GlobalMemberValues.printReceiptByJHP(jsonReceipt, mContext, "payment");
                    break;
                }
                case R.id.customerselectmenuprint : {
                    GlobalMemberValues.b_isMenuPrinted = false;
                    GlobalMemberValues.printReceiptByJHP_menu(jsonReceipt,mContext,"menu_print");
                }
            }
        }
    };

    public static void finishPayment() {
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }
}
