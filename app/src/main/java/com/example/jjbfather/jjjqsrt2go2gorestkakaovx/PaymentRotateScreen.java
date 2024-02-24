package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PaymentRotateScreen extends Activity {
    static Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    Intent mIntent;

    String mGetClosingSelectReceiptPageYN = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        mContext = this;

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 7;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 3;

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_rotate_screen);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mGetClosingSelectReceiptPageYN = mIntent.getStringExtra("getclosingselectreceiptpageyn");
            /*******************************************************************************************/
            GlobalMemberValues.logWrite("rotatelog", "mGetClosingSelectReceiptPageYN : " + mGetClosingSelectReceiptPageYN + "\n");
        } else {
            mActivity.finish();
        }

        if (GlobalMemberValues.isDeviceClover()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            // orientation 에 따라 레이아웃 xml 동적으로 적용.
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 7;
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 3;
            } else {
                // 클로버에서 고객이 영수증 선택히 창을 다시 머천트 쪽으로 돌렸을 때
                // 설정창에서 cardmerchantreceipt 이 off 일 경우
                // 고객 영수증 선택창을 닫는다.
                if (mGetClosingSelectReceiptPageYN.equals("Y")) {
                    String tempCardMerchantReceiptYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                            "select cardmerchantreceiptyn from salon_storestationsettings_deviceprinter"
                    );
                    if (GlobalMemberValues.isStrEmpty(tempCardMerchantReceiptYN)) {
                        tempCardMerchantReceiptYN = "N";
                    }
                    if (tempCardMerchantReceiptYN.equals("N")) {
                        PaymentCustomerSelectReceipt.mActivity.finish();
                    }
                }
                mActivity.finish();
            } // end if
        } else {
            mActivity.finish();
        }
        this.setFinishOnTouchOutside(false);

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.paymnetRotateScreenLinearLayout);
        parentLn.setLayoutParams(parentLnParams);
    }
}
