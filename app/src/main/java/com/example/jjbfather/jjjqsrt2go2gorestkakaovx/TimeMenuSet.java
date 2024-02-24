
package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class TimeMenuSet extends Activity {
    final String TAG = "CashInOutPopupKeypadLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    private LinearLayout breakfastLn, lunchLn, dinnerLn, nightLn;

    private Button closeBtn;
    private Button breakfastBtn, lunchBtn, dinnerBtn, nightBtn;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_time_menu_set);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 50;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 30;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 30;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            //mTvNum = mIntent.getStringExtra("tvnum");
            //mGetNowAmt = mIntent.getStringExtra("nowamt");
            /*******************************************************************************************/
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        GlobalMemberValues.logWrite("wanhayelog", "GlobalMemberValues.isEmployeeLoginPopup : " + GlobalMemberValues.isEmployeeLoginPopup + "\n");

        if (GlobalMemberValues.isEmployeeLoginPopup) {
            closeActivity();
        }

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        breakfastLn = (LinearLayout)findViewById(R.id.breakfastLn);
        lunchLn = (LinearLayout)findViewById(R.id.lunchLn);
        dinnerLn = (LinearLayout)findViewById(R.id.dinnerLn);
        nightLn = (LinearLayout)findViewById(R.id.nightLn);

        closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common);

        breakfastBtn = (Button)findViewById(R.id.breakfastBtn);
        breakfastBtn.setTextSize(
                breakfastBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        lunchBtn = (Button)findViewById(R.id.lunchBtn);
        lunchBtn.setTextSize(
                lunchBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        dinnerBtn = (Button)findViewById(R.id.dinnerBtn);
        dinnerBtn.setTextSize(
                dinnerBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        nightBtn = (Button)findViewById(R.id.nightBtn);
        nightBtn.setTextSize(
                nightBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        closeBtn.setOnClickListener(timemenuBtnClickListener);
        breakfastBtn.setOnClickListener(timemenuBtnClickListener);
        lunchBtn.setOnClickListener(timemenuBtnClickListener);
        dinnerBtn.setOnClickListener(timemenuBtnClickListener);
        nightBtn.setOnClickListener(timemenuBtnClickListener);


        if (GlobalMemberValues.SELECTED_TIME_CODEVALUE.isEmpty()) {
            breakfastLn.setBackgroundColor(0);
            lunchLn.setBackgroundColor(0);
            dinnerLn.setBackgroundColor(0);
            nightLn.setBackgroundColor(0);

            breakfastBtn.setTextColor(Color.parseColor("#ffffff"));
            lunchBtn.setTextColor(Color.parseColor("#ffffff"));
            dinnerBtn.setTextColor(Color.parseColor("#ffffff"));
            nightBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            switch (GlobalMemberValues.SELECTED_TIME_CODEVALUE) {
                case "m" : {
                    breakfastLn.setBackgroundColor(Color.parseColor("#ffffff"));
                    breakfastBtn.setTextColor(Color.parseColor("#000000"));
                    break;
                }
                case "a" : {
                    lunchLn.setBackgroundColor(Color.parseColor("#ffffff"));
                    lunchBtn.setTextColor(Color.parseColor("#000000"));
                    break;
                }
                case "e" : {
                    dinnerLn.setBackgroundColor(Color.parseColor("#ffffff"));
                    dinnerBtn.setTextColor(Color.parseColor("#000000"));
                    break;
                }
                case "n" : {
                    nightLn.setBackgroundColor(Color.parseColor("#ffffff"));
                    nightBtn.setTextColor(Color.parseColor("#000000"));
                    break;
                }
            }
        }

        if (GlobalMemberValues.isOpenedTimeMenuPopup) {
            closeActivity();
        } else {
            GlobalMemberValues.isOpenedTimeMenuPopup = true;
        }
    }

    View.OnClickListener timemenuBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.breakfastBtn : {
                    changeTimeMenu("m");
                    break;
                }

                case R.id.lunchBtn : {
                    changeTimeMenu("a");
                    break;
                }

                case R.id.dinnerBtn : {
                    changeTimeMenu("e");
                    break;
                }

                case R.id.nightBtn : {
                    changeTimeMenu("n");
                    break;
                }

                case R.id.closeBtn : {
                    closeActivity();

                    break;
                }
            }
        }
    };

    private void changeTimeMenu(String paramTimeCode) {
        GlobalMemberValues.setTimeMenuCodeValue(paramTimeCode);

        // 메인 서비스를 리로드한다 --------------------------------------------------------------------------------
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(MainActivity.mContext);
        MainTopCategory mainTopCate = new MainTopCategory(MainActivity.mActivity, MainActivity.mContext, dataAtSqlite, 0);
        mainTopCate.setTopCategory();
        // ------------------------------------------------------------------------------------------------------

        GlobalMemberValues.setTimeMenuBottomTv(paramTimeCode);

        closeActivity();
    }

    private void closeActivity() {
        GlobalMemberValues.isOpenedTimeMenuPopup = false;

        mActivity.finish();
        // 초기화
        //setInit();
        // 키패드(키보드) 감추기
        //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }
}
