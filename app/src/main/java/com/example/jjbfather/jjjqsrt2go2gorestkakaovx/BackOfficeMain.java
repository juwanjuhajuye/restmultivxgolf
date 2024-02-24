package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class BackOfficeMain extends Activity {
    static Activity mActivity;
    Context context;
    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private Button backOfficeMainStoreButton, backOfficeMainEmployeeButton, backOfficeMainCategoryButton;
    private Button backOfficeMainServiceButton, backOfficeMainProductButton, backOfficeMainGiftCardButton;
    private Button backOfficeMainCustomerButton;

    private RadioGroup backOfficeMainDataUploadTypeRadioGroup;
    private RadioButton backOfficeMainDataUploadTypeRadioButton1, backOfficeMainDataUploadTypeRadioButton2, backOfficeMainDataUploadTypeRadioButton3;

    Intent mIntent;

    int mSynchronizationType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_back_office_main);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 9;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.backOfficeMainLinearLayout);
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
        mActivity = this;
        context = this;

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.backOfficeMainCloseBtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_cloud_close);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 이미지 받기전까지는 텍스트 버튼으로...
        int btnTypeInt = GlobalMemberValues.IMAGEBUTTONYN;
        btnTypeInt = 1;

        backOfficeMainStoreButton = (Button)findViewById(R.id.backOfficeMainStoreButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainStoreButton.setTextSize(
                    backOfficeMainStoreButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainEmployeeButton = (Button)findViewById(R.id.backOfficeMainEmployeeButton);
        if (btnTypeInt == 0) {
            //backOfficeMainEmployeeButton.setText("");
            //backOfficeMainEmployeeButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainEmployeeButton.setTextSize(
                    backOfficeMainEmployeeButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainCategoryButton = (Button)findViewById(R.id.backOfficeMainCategoryButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainCategoryButton.setTextSize(
                    backOfficeMainCategoryButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainServiceButton = (Button)findViewById(R.id.backOfficeMainServiceButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainServiceButton.setTextSize(
                    backOfficeMainServiceButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainProductButton = (Button)findViewById(R.id.backOfficeMainProductButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainProductButton.setTextSize(
                    backOfficeMainProductButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainGiftCardButton = (Button)findViewById(R.id.backOfficeMainGiftCardButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainGiftCardButton.setTextSize(
                    backOfficeMainGiftCardButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        backOfficeMainCustomerButton = (Button)findViewById(R.id.backOfficeMainCustomerButton);
        if (btnTypeInt == 0) {
            //backOfficeMainStoreButton.setText("");
            //backOfficeMainStoreButton.setBackgroundResource(R.drawable.);
        } else {
            backOfficeMainCustomerButton.setTextSize(
                    backOfficeMainCustomerButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }


        int vSynchronizationType = 0;
        String tempSqlQuery = "select synchronizationType from salon_storestationsettings_system";
        vSynchronizationType = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(tempSqlQuery));

        closeBtn.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainStoreButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainEmployeeButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainCategoryButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainServiceButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainProductButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainGiftCardButton.setOnClickListener(backOfficeMainBtnClickListener);
        backOfficeMainCustomerButton.setOnClickListener(backOfficeMainBtnClickListener);


        backOfficeMainDataUploadTypeRadioGroup = (RadioGroup)findViewById(R.id.backOfficeMainDataUploadTypeRadioGroup);
        backOfficeMainDataUploadTypeRadioGroup.setOnCheckedChangeListener(checkSyncType);

        backOfficeMainDataUploadTypeRadioButton1 = (RadioButton)findViewById(R.id.backOfficeMainDataUploadTypeRadioButton1);
        backOfficeMainDataUploadTypeRadioButton1.setTextSize(backOfficeMainDataUploadTypeRadioButton1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        backOfficeMainDataUploadTypeRadioButton2 = (RadioButton)findViewById(R.id.backOfficeMainDataUploadTypeRadioButton2);
        backOfficeMainDataUploadTypeRadioButton2.setTextSize(backOfficeMainDataUploadTypeRadioButton2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        backOfficeMainDataUploadTypeRadioButton3 = (RadioButton)findViewById(R.id.backOfficeMainDataUploadTypeRadioButton3);
        backOfficeMainDataUploadTypeRadioButton3.setTextSize(backOfficeMainDataUploadTypeRadioButton3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        switch (vSynchronizationType) {
            case 0 : {
                backOfficeMainDataUploadTypeRadioButton1.setChecked(true);
                break;
            }
            case 1 : {
                backOfficeMainDataUploadTypeRadioButton2.setChecked(true);
                break;
            }
            case 2 : {
                backOfficeMainDataUploadTypeRadioButton3.setChecked(true);
                break;
            }
        }

        /***********************************************************************************************************/
    }

    View.OnClickListener backOfficeMainBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backOfficeMainCloseBtn : {
                    finish();
                    break;
                }
                case R.id.backOfficeMainStoreButton : {
                    Intent backOfficeStoreIntent = new Intent(context.getApplicationContext(), BackOfficeStore.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //backOfficeStoreIntent.putExtra("openClassMethod", "command_backofficemain");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(backOfficeStoreIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
            }
        }
    };

    RadioGroup.OnCheckedChangeListener checkSyncType = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getId() == R.id.backOfficeMainDataUploadTypeRadioGroup) {
                switch (checkedId) {
                    case R.id.backOfficeMainDataUploadTypeRadioButton1 : {
                        mSynchronizationType = 0;
                        break;
                    }
                    case R.id.backOfficeMainDataUploadTypeRadioButton2 : {
                        mSynchronizationType = 1;
                        break;
                    }
                    case R.id.backOfficeMainDataUploadTypeRadioButton3 : {
                        mSynchronizationType = 2;
                        break;
                    }
                }
                GlobalMemberValues.logWrite("BackOfficeMain", "SyncType : " + mSynchronizationType + "\n");
            }
        }
    };

}
