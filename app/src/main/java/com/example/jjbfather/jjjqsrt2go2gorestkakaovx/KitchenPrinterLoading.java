package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class KitchenPrinterLoading extends Activity {
    final String TAG = "KitchenPrinterLoadingLog";

    static Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    static TextView kitchenprinterloadingtv, kitchenprinterloadingcentertv;
    ScrollView realTimeWebOrdersListScrollView;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_kitchen_printer_loading);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.kitchenLoadingLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // mCustomerValue = mIntent.getStringExtra("CustomerValue");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

                GlobalMemberValues.mKitchenLoading = false;
                kitchenprinterloadingtv = null;
            }
        });
        // ---------------------------------------------------------------------------------------------------

        GlobalMemberValues.mKitchenLoading = true;

        kitchenprinterloadingtv = (TextView) findViewById(R.id.kitchenprinterloadingtv);
        kitchenprinterloadingtv.setTextSize(kitchenprinterloadingtv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        kitchenprinterloadingtv.setText("Kitchen Printer --- 1");

        kitchenprinterloadingcentertv = (TextView) findViewById(R.id.kitchenprinterloadingcentertv);
        kitchenprinterloadingcentertv.setTextSize(kitchenprinterloadingcentertv.getTextSize() * GlobalMemberValues.getGlobalFontSize());
    }

    public static void closeActivity() {
        GlobalMemberValues.mKitchenLoading = false;
        KitchenPrinterLoading.kitchenprinterloadingtv = null;

        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_in_top);
        }

    }


    protected void onStart() {
        super.onStart();

        GlobalMemberValues.mKitchenLoading = true;
    }

    @Override
    protected void onDestroy() {

        GlobalMemberValues.mKitchenLoading = false;
        kitchenprinterloadingtv = null;

        super.onDestroy();
    }

}
