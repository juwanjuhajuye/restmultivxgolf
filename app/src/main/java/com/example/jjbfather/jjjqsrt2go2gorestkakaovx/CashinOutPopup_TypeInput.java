package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class CashinOutPopup_TypeInput extends Activity {
    final String TAG = "CashinOutPopup_TypeInput";

    Activity mActivity;
    Context context;

    TextView cashAmtTv01,cashAmtTv02,cashAmtTv03,cashAmtTv04,cashAmtTv05,cashAmtTv06;
    TextView cashAmtTv07,cashAmtTv08,cashAmtTv09,cashAmtTv10,cashAmtTv11,cashAmtTv12;
    TextView cashAmtTv13,cashAmtTv14,cashAmtTv15,cashAmtTv16;

    static TextView cashSuEv01,cashSuEv02,cashSuEv03,cashSuEv04,cashSuEv05,cashSuEv06;
    static TextView cashSuEv07,cashSuEv08,cashSuEv09,cashSuEv10,cashSuEv11,cashSuEv12;
    static TextView cashSuEv13,cashSuEv14,cashSuEv15,cashSuEv16;

    ImageButton suInitBtn, Cashinout_typeinput_close_btn;

    LinearLayout cashSuLn01,cashSuLn02,cashSuLn03,cashSuLn04,cashSuLn05,cashSuLn06;
    LinearLayout cashSuLn07,cashSuLn08,cashSuLn09,cashSuLn10,cashSuLn11,cashSuLn12;
    LinearLayout cashSuLn13,cashSuLn14,cashSuLn15,cashSuLn16;

    Button cashinout_typeinput_save_btn;

    String str_totalValueStr = "";

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.cashinout_typeinput_popup);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        setContents();

    }

    private void setContents(){
        cashAmtTv01 = (TextView)findViewById(R.id.cashAmtTv01);
        cashAmtTv01.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv01.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv02 = (TextView)findViewById(R.id.cashAmtTv02);
        cashAmtTv02.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv02.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv03 = (TextView)findViewById(R.id.cashAmtTv03);
        cashAmtTv03.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv03.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv04 = (TextView)findViewById(R.id.cashAmtTv04);
        cashAmtTv04.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv04.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv05 = (TextView)findViewById(R.id.cashAmtTv05);
        cashAmtTv05.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv05.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv06 = (TextView)findViewById(R.id.cashAmtTv06);
        cashAmtTv06.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv06.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv07 = (TextView)findViewById(R.id.cashAmtTv07);
        cashAmtTv07.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv07.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv08 = (TextView)findViewById(R.id.cashAmtTv08);
        cashAmtTv08.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv08.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv09 = (TextView)findViewById(R.id.cashAmtTv09);
        cashAmtTv09.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv09.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv10 = (TextView)findViewById(R.id.cashAmtTv10);
        cashAmtTv10.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv10.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv11 = (TextView)findViewById(R.id.cashAmtTv11);
        cashAmtTv11.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv11.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashAmtTv12 = (TextView)findViewById(R.id.cashAmtTv12);
        cashAmtTv12.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv12.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        cashAmtTv13 = (TextView)findViewById(R.id.cashAmtTv13);
        cashAmtTv13.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv13.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashAmtTv14 = (TextView)findViewById(R.id.cashAmtTv14);
        cashAmtTv14.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv14.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashAmtTv15 = (TextView)findViewById(R.id.cashAmtTv15);
        cashAmtTv15.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv15.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashAmtTv16 = (TextView)findViewById(R.id.cashAmtTv16);
        cashAmtTv16.setTextSize(GlobalMemberValues.globalAddFontSize() + cashAmtTv16.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        cashSuEv01 = (TextView)findViewById(R.id.cashSuEv01);
        cashSuEv01.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv01.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv02 = (TextView)findViewById(R.id.cashSuEv02);
        cashSuEv02.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv02.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv03 = (TextView)findViewById(R.id.cashSuEv03);
        cashSuEv03.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv03.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv04 = (TextView)findViewById(R.id.cashSuEv04);
        cashSuEv04.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv04.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv05 = (TextView)findViewById(R.id.cashSuEv05);
        cashSuEv05.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv05.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv06 = (TextView)findViewById(R.id.cashSuEv06);
        cashSuEv06.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv06.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv07 = (TextView)findViewById(R.id.cashSuEv07);
        cashSuEv07.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv07.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv08 = (TextView)findViewById(R.id.cashSuEv08);
        cashSuEv08.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv08.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv09 = (TextView)findViewById(R.id.cashSuEv09);
        cashSuEv09.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv09.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv10 = (TextView)findViewById(R.id.cashSuEv10);
        cashSuEv10.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv10.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv11 = (TextView)findViewById(R.id.cashSuEv11);
        cashSuEv11.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv11.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv12 = (TextView)findViewById(R.id.cashSuEv12);
        cashSuEv12.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv12.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        cashSuEv13 = (TextView)findViewById(R.id.cashSuEv13);
        cashSuEv13.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv13.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashSuEv14 = (TextView)findViewById(R.id.cashSuEv14);
        cashSuEv14.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv14.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashSuEv15 = (TextView)findViewById(R.id.cashSuEv15);
        cashSuEv15.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv15.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cashSuEv16 = (TextView)findViewById(R.id.cashSuEv16);
        cashSuEv16.setTextSize(GlobalMemberValues.globalAddFontSize() + cashSuEv16.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());



        cashSuLn01 = (LinearLayout) findViewById(R.id.cashSuLn01);
        cashSuLn02 = (LinearLayout) findViewById(R.id.cashSuLn02);
        cashSuLn03 = (LinearLayout) findViewById(R.id.cashSuLn03);
        cashSuLn04 = (LinearLayout) findViewById(R.id.cashSuLn04);
        cashSuLn05 = (LinearLayout) findViewById(R.id.cashSuLn05);
        cashSuLn06 = (LinearLayout) findViewById(R.id.cashSuLn06);
        cashSuLn07 = (LinearLayout) findViewById(R.id.cashSuLn07);
        cashSuLn08 = (LinearLayout) findViewById(R.id.cashSuLn08);
        cashSuLn09 = (LinearLayout) findViewById(R.id.cashSuLn09);
        cashSuLn10 = (LinearLayout) findViewById(R.id.cashSuLn10);
        cashSuLn11 = (LinearLayout) findViewById(R.id.cashSuLn11);
        cashSuLn12 = (LinearLayout) findViewById(R.id.cashSuLn12);

        cashSuLn13 = (LinearLayout) findViewById(R.id.cashSuLn13);
        cashSuLn14 = (LinearLayout) findViewById(R.id.cashSuLn14);
        cashSuLn15 = (LinearLayout) findViewById(R.id.cashSuLn15);
        cashSuLn16 = (LinearLayout) findViewById(R.id.cashSuLn16);

        cashSuEv01.addTextChangedListener(evChangeValue);
        cashSuEv02.addTextChangedListener(evChangeValue);
        cashSuEv03.addTextChangedListener(evChangeValue);
        cashSuEv04.addTextChangedListener(evChangeValue);
        cashSuEv05.addTextChangedListener(evChangeValue);
        cashSuEv06.addTextChangedListener(evChangeValue);
        cashSuEv07.addTextChangedListener(evChangeValue);
        cashSuEv08.addTextChangedListener(evChangeValue);
        cashSuEv09.addTextChangedListener(evChangeValue);
        cashSuEv10.addTextChangedListener(evChangeValue);
        cashSuEv11.addTextChangedListener(evChangeValue);
        cashSuEv12.addTextChangedListener(evChangeValue);

        cashSuEv13.addTextChangedListener(evChangeValue);
        cashSuEv14.addTextChangedListener(evChangeValue);
        cashSuEv15.addTextChangedListener(evChangeValue);
        cashSuEv16.addTextChangedListener(evChangeValue);

        cashSuEv01.setOnClickListener(evClick);
        cashSuEv02.setOnClickListener(evClick);
        cashSuEv03.setOnClickListener(evClick);
        cashSuEv04.setOnClickListener(evClick);
        cashSuEv05.setOnClickListener(evClick);
        cashSuEv06.setOnClickListener(evClick);
        cashSuEv07.setOnClickListener(evClick);
        cashSuEv08.setOnClickListener(evClick);
        cashSuEv09.setOnClickListener(evClick);
        cashSuEv10.setOnClickListener(evClick);
        cashSuEv11.setOnClickListener(evClick);
        cashSuEv12.setOnClickListener(evClick);

        cashSuEv13.setOnClickListener(evClick);
        cashSuEv14.setOnClickListener(evClick);
        cashSuEv15.setOnClickListener(evClick);
        cashSuEv16.setOnClickListener(evClick);

        cashSuLn01.setOnClickListener(evClick);
        cashSuLn02.setOnClickListener(evClick);
        cashSuLn03.setOnClickListener(evClick);
        cashSuLn04.setOnClickListener(evClick);
        cashSuLn05.setOnClickListener(evClick);
        cashSuLn06.setOnClickListener(evClick);
        cashSuLn07.setOnClickListener(evClick);
        cashSuLn08.setOnClickListener(evClick);
        cashSuLn09.setOnClickListener(evClick);
        cashSuLn10.setOnClickListener(evClick);
        cashSuLn11.setOnClickListener(evClick);
        cashSuLn12.setOnClickListener(evClick);

        cashSuLn13.setOnClickListener(evClick);
        cashSuLn14.setOnClickListener(evClick);
        cashSuLn15.setOnClickListener(evClick);
        cashSuLn16.setOnClickListener(evClick);

        suInitBtn = (ImageButton) findViewById(R.id.suInitBtn);

        suInitBtn.setOnClickListener(loginBtnClickListener);

        cashinout_typeinput_save_btn = (Button) findViewById(R.id.cashinout_typeinput_save_btn);
        cashinout_typeinput_save_btn.setOnClickListener(loginBtnClickListener);

        Cashinout_typeinput_close_btn = (ImageButton) findViewById(R.id.Cashinout_typeinput_close_btn);
        Cashinout_typeinput_close_btn.setOnClickListener(loginBtnClickListener);
    }

    View.OnClickListener evClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tvnum = v.getTag().toString();

            Intent cashInOutKeyPadIntent = new Intent(context.getApplicationContext(), CashInOutPopupKeypad.class);
            cashInOutKeyPadIntent.putExtra("tvnum", tvnum);
            if (tvnum.equals("1000")) {
//                String nowamt = cashInOutAmountTv.getText().toString();
//                cashInOutKeyPadIntent.putExtra("nowamt", nowamt);
            }
            mActivity.startActivity(cashInOutKeyPadIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
            }
        }
    };

    TextWatcher evChangeValue = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double evValue1 = GlobalMemberValues.getDoubleAtString(cashSuEv01.getText().toString());
            double evValue2 = GlobalMemberValues.getDoubleAtString(cashSuEv02.getText().toString());
            double evValue3 = GlobalMemberValues.getDoubleAtString(cashSuEv03.getText().toString());
            double evValue4 = GlobalMemberValues.getDoubleAtString(cashSuEv04.getText().toString());
            double evValue5 = GlobalMemberValues.getDoubleAtString(cashSuEv05.getText().toString());
            double evValue6 = GlobalMemberValues.getDoubleAtString(cashSuEv06.getText().toString());
            double evValue7 = GlobalMemberValues.getDoubleAtString(cashSuEv07.getText().toString());
            double evValue8 = GlobalMemberValues.getDoubleAtString(cashSuEv08.getText().toString());
            double evValue9 = GlobalMemberValues.getDoubleAtString(cashSuEv09.getText().toString());
            double evValue10 = GlobalMemberValues.getDoubleAtString(cashSuEv10.getText().toString());
            double evValue11 = GlobalMemberValues.getDoubleAtString(cashSuEv11.getText().toString());
            double evValue12 = GlobalMemberValues.getDoubleAtString(cashSuEv12.getText().toString());

            double evValue13 = GlobalMemberValues.getDoubleAtString(cashSuEv13.getText().toString());
            double evValue14 = GlobalMemberValues.getDoubleAtString(cashSuEv14.getText().toString());
            double evValue15 = GlobalMemberValues.getDoubleAtString(cashSuEv15.getText().toString());
            double evValue16 = GlobalMemberValues.getDoubleAtString(cashSuEv16.getText().toString());

            String evValueStr1 = GlobalMemberValues.getCommaStringForDouble(evValue1 * 100 + "");
            String evValueStr2 = GlobalMemberValues.getCommaStringForDouble(evValue2 * 50 + "");
            String evValueStr3 = GlobalMemberValues.getCommaStringForDouble(evValue3 * 20 + "");
            String evValueStr4 = GlobalMemberValues.getCommaStringForDouble(evValue4 * 10 + "");
            String evValueStr5 = GlobalMemberValues.getCommaStringForDouble(evValue5 * 5 + "");
            String evValueStr6 = GlobalMemberValues.getCommaStringForDouble(evValue6 * 2 + "");
            String evValueStr7 = GlobalMemberValues.getCommaStringForDouble(evValue7 * 1 + "");
            String evValueStr8 = GlobalMemberValues.getCommaStringForDouble(evValue8 * 0.5 + "");
            String evValueStr9 = GlobalMemberValues.getCommaStringForDouble(evValue9 * 0.25 + "");
            String evValueStr10 = GlobalMemberValues.getCommaStringForDouble(evValue10 * 0.1 + "");
            String evValueStr11 = GlobalMemberValues.getCommaStringForDouble(evValue11 * 0.05 + "");
            String evValueStr12 = GlobalMemberValues.getCommaStringForDouble(evValue12 * 0.01 + "");

            String evValueStr13 = GlobalMemberValues.getCommaStringForDouble((evValue13 * 0.01) * 50 + "");    // 여기에 롤 당 들어가는 갯수 곱해주기.
            String evValueStr14 = GlobalMemberValues.getCommaStringForDouble((evValue14 * 0.05) * 40 + "");
            String evValueStr15 = GlobalMemberValues.getCommaStringForDouble((evValue15 * 0.25) * 40 + "");
            String evValueStr16 = GlobalMemberValues.getCommaStringForDouble((evValue16 * 0.10) * 50 + "");

            cashAmtTv01.setText(evValueStr1);
            cashAmtTv02.setText(evValueStr2);
            cashAmtTv03.setText(evValueStr3);
            cashAmtTv04.setText(evValueStr4);
            cashAmtTv05.setText(evValueStr5);
            cashAmtTv06.setText(evValueStr6);
            cashAmtTv07.setText(evValueStr7);
            cashAmtTv08.setText(evValueStr8);
            cashAmtTv09.setText(evValueStr9);
            cashAmtTv10.setText(evValueStr10);
            cashAmtTv11.setText(evValueStr11);
            cashAmtTv12.setText(evValueStr12);

            cashAmtTv13.setText(evValueStr13);
            cashAmtTv14.setText(evValueStr14);
            cashAmtTv15.setText(evValueStr15);
            cashAmtTv16.setText(evValueStr16);

            double totalValue = (evValue1 * 100)+(evValue2 * 50)+(evValue3 * 20)+(evValue4 * 10)+(evValue5 * 5)+(evValue6 * 2)
                    +(evValue7 * 1)+(evValue8 * 0.5)+(evValue9 * 0.25)+(evValue10 * 0.1)+(evValue11 * 0.05)+(evValue12 * 0.01)
                    + ((evValue13 * 0.01)*50)+((evValue14 * 0.05)*40)+((evValue15 * 0.25)*40)+((evValue16 * 0.1)*50);   // 여기에 롤 당 들어가는 갯수 곱해주기.
            String totalValueStr = GlobalMemberValues.getStringFormatNumber(totalValue, "2");
//            cashInOutAmountTv.setText(totalValueStr);
            jsonArray = new JSONArray();
            jsonArray.put(cashSuEv01.getText().toString());
            jsonArray.put(cashSuEv02.getText().toString());
            jsonArray.put(cashSuEv03.getText().toString());
            jsonArray.put(cashSuEv04.getText().toString());
            jsonArray.put(cashSuEv05.getText().toString());
            jsonArray.put(cashSuEv06.getText().toString());
            jsonArray.put(cashSuEv07.getText().toString());
            jsonArray.put(cashSuEv08.getText().toString());
            jsonArray.put(cashSuEv09.getText().toString());
            jsonArray.put(cashSuEv10.getText().toString());
            jsonArray.put(cashSuEv11.getText().toString());
            jsonArray.put(cashSuEv12.getText().toString());
            jsonArray.put(cashSuEv13.getText().toString());
            jsonArray.put(cashSuEv14.getText().toString());
            jsonArray.put(cashSuEv15.getText().toString());
            jsonArray.put(cashSuEv16.getText().toString());

            str_totalValueStr = totalValueStr;
            CashInOutPopup.setCashInOutAmountTv(totalValueStr, jsonArray);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void cashSuEvInit() {
        cashSuEv01.setText("");
        cashSuEv02.setText("");
        cashSuEv03.setText("");
        cashSuEv04.setText("");
        cashSuEv05.setText("");
        cashSuEv06.setText("");
        cashSuEv07.setText("");
        cashSuEv08.setText("");
        cashSuEv09.setText("");
        cashSuEv10.setText("");
        cashSuEv11.setText("");
        cashSuEv12.setText("");
        cashSuEv13.setText("");
        cashSuEv14.setText("");
        cashSuEv15.setText("");
        cashSuEv16.setText("");
    }

    View.OnClickListener loginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashInOutPoupuPreviousListTv : {
                    Intent intent = new Intent(context, CashInOutPopupPreviousList.class);
                    // 객체 및 데이터 전달하기 ---------------------------------------------------------------
                    //intent.putExtra("openbatchedlist", "Y");
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.suInitBtn : {
                    LogsSave.saveLogsInDB(44);
                    cashSuEvInit();
                    break;
                }
                case R.id.cashinout_inputtype : {
                    Intent intent = new Intent(context.getApplicationContext(), CashinOutPopup_TypeInput.class);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }
                    break;
                }
                case R.id.cashinout_typeinput_save_btn : {
                    if (jsonArray == null) jsonArray = new JSONArray();
                    CashInOutPopup.setCashInOutAmountTv(str_totalValueStr, jsonArray);

                    finish();
                    break;
                }
                case R.id.Cashinout_typeinput_close_btn : {
                    finish();
                }
            }
        }
    };
}