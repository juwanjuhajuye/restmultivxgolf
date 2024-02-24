package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomPopupActivity extends Activity {
    private LinearLayout parentLn;
    private ImageButton closeBtn;

    Intent mIntent;

    private String title, text;

    private TextView tv_title, tv_text;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_custom_popup);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 50;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 30;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.activity_custom_popup_ln);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            title = mIntent.getStringExtra("title");
            text = mIntent.getStringExtra("text");
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
        closeBtn = (ImageButton)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(onClickListener);

        tv_title = (TextView) findViewById(R.id.custom_popup_title);
        tv_text = (TextView) findViewById(R.id.custom_popup_text);
        tv_title.setText(title);
        tv_text.setText(text);
        /***********************************************************************************************************/

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBtn : {
                    finish();
                    break;
                }
            }
        }
    };

}
