package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DynamicPrice extends Activity {
    final String TAG = "DynamicPriceLog";

    private LinearLayout parentLn;

    private Button dynamicPrice_suButton1,dynamicPrice_suButton2,dynamicPrice_suButton3;
    private Button dynamicPrice_suButton4,dynamicPrice_suButton5,dynamicPrice_suButton6;
    private Button dynamicPrice_suButton7,dynamicPrice_suButton8,dynamicPrice_suButton9;
    private Button dynamicPrice_suButton0,dynamicPrice_suButton00,dynamicPrice_suButtonBack;
    private Button dynamicPrice_suButtonV,dynamicPrice_suButtonX;

    private EditText dynamicPrice_price;

    private TextView dynamicPriceTitleTextView;

    String mDynamicPriceEtValue = "";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    String[] paramsString = null;

    Activity activity;
    Context context;

    public ProgressDialog masterpwdCheckProDial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_dynamic_price);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 3;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 7;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.dynamicPriceLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        activity = this;
        context = this;
        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            paramsString = mIntent.getStringArrayExtra("paramsString");
            /*******************************************************************************************/
        } else {
            finish();
        }
        setLayoutContent();

    }

    public void setLayoutContent() {
        dynamicPriceTitleTextView = (TextView)findViewById(R.id.dynamicPriceTitleTextView);
        dynamicPriceTitleTextView.setTextSize(dynamicPriceTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        if (paramsString != null ||paramsString.length != 0) {
            dynamicPriceTitleTextView.setText(paramsString[6] + " \nDynamic Price");
        }

        dynamicPrice_suButton1 = (Button)findViewById(R.id.dynamicPrice_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton1.setTextSize(
                    dynamicPrice_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton1.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton1.setTextSize(
                    dynamicPrice_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton2 = (Button)findViewById(R.id.dynamicPrice_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton2.setTextSize(
                    dynamicPrice_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton2.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton2.setTextSize(
                    dynamicPrice_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton3 = (Button)findViewById(R.id.dynamicPrice_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton3.setTextSize(
                    dynamicPrice_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton3.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton3.setTextSize(
                    dynamicPrice_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton4 = (Button)findViewById(R.id.dynamicPrice_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton4.setTextSize(
                    dynamicPrice_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton4.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton4.setTextSize(
                    dynamicPrice_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton5 = (Button)findViewById(R.id.dynamicPrice_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton5.setTextSize(
                    dynamicPrice_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton5.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton5.setTextSize(
                    dynamicPrice_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton6 = (Button)findViewById(R.id.dynamicPrice_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton6.setTextSize(
                    dynamicPrice_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton6.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton6.setTextSize(
                    dynamicPrice_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton7 = (Button)findViewById(R.id.dynamicPrice_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton7.setTextSize(
                    dynamicPrice_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton7.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton7.setTextSize(
                    dynamicPrice_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton8 = (Button)findViewById(R.id.dynamicPrice_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton8.setTextSize(
                    dynamicPrice_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton8.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton8.setTextSize(
                    dynamicPrice_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton9 = (Button)findViewById(R.id.dynamicPrice_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton9.setTextSize(
                    dynamicPrice_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton9.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton9.setTextSize(
                    dynamicPrice_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton0 = (Button)findViewById(R.id.dynamicPrice_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton0.setTextSize(
                    dynamicPrice_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton0.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton0.setTextSize(
                    dynamicPrice_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButton00 = (Button)findViewById(R.id.dynamicPrice_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButton00.setTextSize(
                    dynamicPrice_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            dynamicPrice_suButton00.setTextColor(Color.parseColor("#37383D"));
            dynamicPrice_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_number);
        } else {
            dynamicPrice_suButton00.setTextSize(
                    dynamicPrice_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButtonBack = (Button)findViewById(R.id.dynamicPrice_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButtonBack.setText("");
            dynamicPrice_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_delete);
        } else {
            dynamicPrice_suButtonBack.setTextSize(
                    dynamicPrice_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButtonV = (Button)findViewById(R.id.dynamicPrice_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButtonV.setText("");
            dynamicPrice_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_enter);
        } else {
            dynamicPrice_suButtonV.setTextSize(
                    dynamicPrice_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        dynamicPrice_suButtonX = (Button)findViewById(R.id.dynamicPrice_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            dynamicPrice_suButtonX.setText("");
            dynamicPrice_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_adminpassword_close);
        } else {
            dynamicPrice_suButtonX.setTextSize(
                    dynamicPrice_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        dynamicPrice_suButton1.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton2.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton3.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton4.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton5.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton6.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton7.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton8.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton9.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton0.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButton00.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButtonBack.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButtonV.setOnClickListener(dynamicPriceButtonClickListener);
        dynamicPrice_suButtonX.setOnClickListener(dynamicPriceButtonClickListener);

        dynamicPrice_price = (EditText)findViewById(R.id.dynamicPrice_price);
        dynamicPrice_price.setOnTouchListener(mTouchDynamicPriceTvTouchListener);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), dynamicPrice_price);
    }

    View.OnClickListener dynamicPriceButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dynamicPrice_suButton1 : {
                    numberButtonClick(dynamicPrice_suButton1);
                    break;
                }
                case R.id.dynamicPrice_suButton2 : {
                    numberButtonClick(dynamicPrice_suButton2);
                    break;
                }
                case R.id.dynamicPrice_suButton3 : {
                    numberButtonClick(dynamicPrice_suButton3);
                    break;
                }
                case R.id.dynamicPrice_suButton4 : {
                    numberButtonClick(dynamicPrice_suButton4);
                    break;
                }
                case R.id.dynamicPrice_suButton5 : {
                    numberButtonClick(dynamicPrice_suButton5);
                    break;
                }
                case R.id.dynamicPrice_suButton6 : {
                    numberButtonClick(dynamicPrice_suButton6);
                    break;
                }
                case R.id.dynamicPrice_suButton7 : {
                    numberButtonClick(dynamicPrice_suButton7);
                    break;
                }
                case R.id.dynamicPrice_suButton8 : {
                    numberButtonClick(dynamicPrice_suButton8);
                    break;
                }
                case R.id.dynamicPrice_suButton9 : {
                    numberButtonClick(dynamicPrice_suButton9);
                    break;
                }
                case R.id.dynamicPrice_suButton0 : {
                    numberButtonClick(dynamicPrice_suButton0);
                    break;
                }
                case R.id.dynamicPrice_suButton00 : {
                    numberButtonClick(dynamicPrice_suButton00);
                    break;
                }
                case R.id.dynamicPrice_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mDynamicPriceEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mDynamicPriceEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mDynamicPriceEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mDynamicPriceEtValue)) {
                            mDynamicPriceEtValue = "";
                        }
                    } else {
                        mDynamicPriceEtValue = "";
                    }
                    dynamicPrice_price.setText(mDynamicPriceEtValue);
                    break;
                }
                case R.id.dynamicPrice_suButtonV : {
                    setExecuteClassMethod();
                    break;
                }
                case R.id.dynamicPrice_suButtonX : {
                    finish();
                    break;
                }
            }
        }
    };


    public void setExecuteClassMethod() {
        String str_dynamicPrice_price = dynamicPrice_price.getText().toString();

        if (str_dynamicPrice_price.equals("0.00") || str_dynamicPrice_price.isEmpty()) {
            GlobalMemberValues.displayDialog(this, "Warning", "Wrong Price", "Close");
            mDynamicPriceEtValue = "";
            dynamicPrice_price.setText("");

        } else {
            paramsString[9] = dynamicPrice_price.getText().toString();
            MainMiddleService.dynamicPriceSubmit(paramsString);
            finish();
        }
    }


    View.OnTouchListener mTouchDynamicPriceTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            dynamicPrice_price.setFocusable(true);
            dynamicPrice_price.selectAll();
            // 키보드 안보이게
            GlobalMemberValues.setKeyPadHide(getApplication(), dynamicPrice_price);
            //adminPassword_qty.hasFocus();
            return true;
        }
    };

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mDynamicPriceEtValue.length() < 12) {
            sb.append(mDynamicPriceEtValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            mDynamicPriceEtValue = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mDynamicPriceEtValue) * 0.01), "2");
            dynamicPrice_price.setText(inputSu);
        }

        // 커서를 맨 뒤로...
        GlobalMemberValues.setCursorLastDigit(dynamicPrice_price);

    }


}
