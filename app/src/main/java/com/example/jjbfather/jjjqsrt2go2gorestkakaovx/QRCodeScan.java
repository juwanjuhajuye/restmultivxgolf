package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.journeyapps.barcodescanner.CaptureActivity;

public class QRCodeScan extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar ab = getActionBar();
        //ab.setTitle("QR / Barcode Scan");
        GlobalMemberValues.setActivityOrientation(this, this);
        // 상단 Status Bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_qrcode_scan);
        this.setFinishOnTouchOutside(false);


        // 액티비티 사이즈조절 ----------------------------------------------------------------------------------
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (GlobalMemberValues.getDisplayWidth(this) * 0.8); //Display 사이즈의 %
        if (GlobalMemberValues.thisTabletRealWidth > 1200) {
            width = (int) (GlobalMemberValues.getDisplayWidth(this) * 0.5); //Display 사이즈의 %
        }
        int height = (int) (GlobalMemberValues.getDisplayHeiheight(this) * 0.8);  //Display 사이즈의 %
        if (GlobalMemberValues.thisTabletRealHeight > 800) {
            height = (int) (GlobalMemberValues.getDisplayHeiheight(this) * 0.65);  //Display 사이즈의 %
        }
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        // ----------------------------------------------------------------------------------------------------

        /**
         //버튼 리소스를 뷰로 전개
        View myButtonLayout = getLayoutInflater().inflate(R.layout.qrcode_actionbar, null);
        //액션바의 인스턴스 생성
        ActionBar ab = getActionBar();
        //액션바의 커스텀 영역에 버튼 뷰 추가
        ab.setCustomView(myButtonLayout);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        **/
/**
        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 8;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        }
 **/


        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(width, height);
        newLnLayoutParams.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams subNewLnLayoutParams1
                = new LinearLayout.LayoutParams(width, (height / 100) * 10);
        subNewLnLayoutParams1.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams subNewLnLayoutParams2
                = new LinearLayout.LayoutParams(width, (height / 100) * 70);
        subNewLnLayoutParams2.setMargins(0, 0, 0, 0);

        LinearLayout parentLn = new LinearLayout(this);
        parentLn.setPadding(0, 0, 0, 0);
        parentLn.setBackgroundColor(Color.parseColor("#00ff0000"));
        parentLn.setOrientation(LinearLayout.VERTICAL);

        /**
         Button btn = new Button(this);
         btn.setText("Button...");
         btn.setLayoutParams(subNewLnLayoutParams1);
         parentLn.addView(btn);

         Button btn1 = new Button(this);
         btn1.setText("Button...1");
         btn1.setLayoutParams(subNewLnLayoutParams1);
         parentLn.addView(btn1);

         Button btn2 = new Button(this);
         btn2.setText("Button...2");
         btn2.setLayoutParams(subNewLnLayoutParams1);
         parentLn.addView(btn2);
         **/


        LinearLayout newLn = new LinearLayout(this);
        newLn.setLayoutParams(newLnLayoutParams);
        newLn.setBackgroundColor(Color.parseColor("#00ff0000"));
        newLn.setOrientation(LinearLayout.VERTICAL);
        //newLn.setBackgroundColor(Color.parseColor("#00ff0000"));
        //newLn.setTag(dbHoldCode);

        // 상단 LinearLayout ---------------------------------------
        LinearLayout subLn1 = new LinearLayout(this);
        subLn1.setLayoutParams(subNewLnLayoutParams1);
        subLn1.setBackgroundColor(Color.parseColor("#fcfdfc"));

        subLn1.setPadding(10, 5, 5, 5);
        subLn1.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams tvParam
                = new LinearLayout.LayoutParams((GlobalMemberValues.getDisplayWidth(this) / 100) * 10,
                (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 8);
        tvParam.setMargins(0, 0, 0, 0);

        TextView topTv = new TextView(this);
        topTv.setBackgroundResource(R.drawable.jjj_scan_qrcode);

        subLn1.addView(topTv);

        newLn.addView(subLn1);
        // ---------------------------------------------------------

        // 가운데 LinearLayout -------------------------------------
        LinearLayout subLn2 = new LinearLayout(this);
        subLn2.setLayoutParams(subNewLnLayoutParams2);
        //subLn2.setBackgroundColor(Color.parseColor("#ffffff"));

        /**
         LinearLayout subLn2_left = new LinearLayout(this);
         subLn2_left.setLayoutParams(subMiddleSubLeftRight);
         subLn2_left.setBackgroundColor(Color.parseColor("#ffffff"));
         subLn2.addView(subLn2_left);

         LinearLayout subLn2_middle = new LinearLayout(this);
         subLn2_middle.setLayoutParams(subMiddleSubMiddle);
         subLn2.addView(subLn2_middle);

         LinearLayout subLn2_right = new LinearLayout(this);
         subLn2_right.setLayoutParams(subMiddleSubLeftRight);
         subLn2_right.setBackgroundColor(Color.parseColor("#ffffff"));
         subLn2.addView(subLn2_right);
         **/

        newLn.addView(subLn2);
        // ---------------------------------------------------------

        // 하단 LinearLayout ---------------------------------------
        LinearLayout subLn3 = new LinearLayout(this);
        subLn3.setLayoutParams(subNewLnLayoutParams1);
        subLn3.setBackgroundColor(Color.parseColor("#fcfdfc"));

        subLn3.setPadding(5, 5, 5, 5);
        subLn3.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams btnParam
                = new LinearLayout.LayoutParams((GlobalMemberValues.getDisplayWidth(this) / 100) * 10,
                (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 5);
        btnParam.setMargins(0, 0, 0, 0);

        Button cancelBtn = new Button(this);
        cancelBtn.setLayoutParams(btnParam);

        cancelBtn.setBackgroundResource(R.drawable.button_selector_cancel);
        cancelBtn.setOnClickListener(clickbtn);

        subLn3.addView(cancelBtn);

        newLn.addView(subLn3);

        // ---------------------------------------------------------

        parentLn.addView(newLn);

        this.addContentView(parentLn, newLnLayoutParams);
    }

    Button.OnClickListener clickbtn = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
