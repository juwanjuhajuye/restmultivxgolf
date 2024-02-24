package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PaymentThankYou extends Activity {
    Activity mActivity;
    Context mContext;

    final String TAG = "PaymentThankYouClassLog";

    LinearLayout parentLn;

    private TextView thankyoumsgTv;
    private TextView companyTv;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_thank_you);
        this.setFinishOnTouchOutside(false);


        parentLn = (LinearLayout)findViewById(R.id.contentsLn);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            //paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            //GlobalMemberValues.logWrite(TAG, "넘겨받은 jsonReceipt : " + jsonReceipt + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite(TAG, "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // ---------------------------------------------------------------------------------------------------

        thankyoumsgTv = (TextView) findViewById(R.id.thankyoumsgTv);
        companyTv = (TextView) findViewById(R.id.companyTv);

        companyTv.setText(GlobalMemberValues.SALON_NAME);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);
    }
}
