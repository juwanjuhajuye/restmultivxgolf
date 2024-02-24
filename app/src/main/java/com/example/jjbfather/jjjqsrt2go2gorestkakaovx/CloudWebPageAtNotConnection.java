package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CloudWebPageAtNotConnection extends Activity {
    private LinearLayout parentLn;
    private Button closeBtn;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cloud_web_page_at_not_connection);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 50;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 40;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.cloudWebPageAtNotConnectionLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.cloudWebPageAtNotConnectionCloseBtn);
        closeBtn.setOnClickListener(cloudWebPageBtnClickListener);
        /***********************************************************************************************************/

    }

    View.OnClickListener cloudWebPageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cloudWebPageAtNotConnectionCloseBtn : {
                    finish();
                    break;
                }
            }
        }
    };

}
