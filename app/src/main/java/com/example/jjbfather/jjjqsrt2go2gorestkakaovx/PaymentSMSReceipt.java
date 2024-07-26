package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

//import cz.msebera.android.httpclient.HttpResponse;
//import cz.msebera.android.httpclient.NameValuePair;
//import cz.msebera.android.httpclient.client.ClientProtocolException;
//import cz.msebera.android.httpclient.client.HttpClient;
//import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
//import cz.msebera.android.httpclient.client.methods.HttpPost;
//import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
//import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class PaymentSMSReceipt extends Activity {
    static final String TAG = "PaymentSMSReceipt";

    static Activity activity;
    static Context context;

    private LinearLayout parentLn;

    private Button smsreceipt_suButton1,smsreceipt_suButton2,smsreceipt_suButton3;
    private Button smsreceipt_suButton4,smsreceipt_suButton5,smsreceipt_suButton6;
    private Button smsreceipt_suButton7,smsreceipt_suButton8,smsreceipt_suButton9;
    private Button smsreceipt_suButton0,smsreceipt_suButtonBack;
    private Button smsreceipt_suButtonV,smsreceipt_suButtonX;

    private Button backButton;

    private EditText customerPhoneNumberEv;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    // 인텐트로 넘겨받은 값
    JSONObject jsonReceipt = null;

    String customerId = "";
    String customerPhoneNo = "";

    int smsSentResult = 0;
    String smsSentResultStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        context = this;

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 6;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 4;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 7;
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        if (GlobalMemberValues.isDeviceClover()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            // orientation 에 따라 레이아웃 xml 동적으로 적용.
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_payment_smsreceipt2);

                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 10;
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 10;
            } else {
                setContentView(R.layout.activity_payment_smsreceipt);
            } // end if
        } else {
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                setContentView(R.layout.activity_payment_smsreceipt3);
            } else {
                setContentView(R.layout.activity_payment_smsreceipt);
            }

        }
        this.setFinishOnTouchOutside(false);

        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        }

        parentLn = (LinearLayout)findViewById(R.id.smsreceiptLinearLayout);

        /**
         LinearLayout.LayoutParams parentLnParams
         = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
         parentLnParams.setMargins(0, 0, 0, 0);

         parentLn.setLayoutParams(parentLnParams);
         **/

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            String jsonReceiptString = mIntent.getStringExtra("JsonReceipt");
            if (!GlobalMemberValues.isStrEmpty(jsonReceiptString)) {
                try {
                    jsonReceipt = new JSONObject(jsonReceiptString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 이메일이 곧 아이디임.. (이전버전에는 이메일이 아이디였음......)
            customerId = mIntent.getStringExtra("customerId");
            customerPhoneNo = mIntent.getStringExtra("CustomerPhoneNo");
            customerPhoneNo = GlobalMemberValues.getReplaceText(customerPhoneNo, " ", "");
            customerPhoneNo = GlobalMemberValues.getReplaceText(customerPhoneNo, "-", "");
            customerPhoneNo = GlobalMemberValues.getReplaceText(customerPhoneNo, ".", "");

            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerId : " + customerId + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerPhoneNo : " + customerPhoneNo + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 jsonReceipt : " + jsonReceipt + "\n");
            /*******************************************************************************************/
        } else {
            finishPayment();
        }
    }

    public void setLayoutContent() {
        // 프레젠테이션 닫기
        GlobalMemberValues.dismissPresentationView();

        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            LinearLayout emptyLn01, emptyLn02, emptyLn03, emptyLn04;

            emptyLn01 = (LinearLayout) findViewById(R.id.emptyLn01);
            emptyLn02 = (LinearLayout) findViewById(R.id.emptyLn02);
            emptyLn03 = (LinearLayout) findViewById(R.id.emptyLn03);
            emptyLn04 = (LinearLayout) findViewById(R.id.emptyLn04);

            emptyLn01.setVisibility(View.GONE);
            emptyLn02.setVisibility(View.GONE);
            emptyLn03.setVisibility(View.GONE);
            emptyLn04.setVisibility(View.GONE);
        }

        int numberBackground = R.drawable.ab_imagebutton_discountextra_number;
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            numberBackground = R.drawable.ab_imagebutton_discountextra_number_lite;
            GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR = "#0054d5";
        }

        smsreceipt_suButton1 = (Button)findViewById(R.id.smsreceipt_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton1.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton1.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton1.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton2 = (Button)findViewById(R.id.smsreceipt_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton2.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton2.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton2.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton3 = (Button)findViewById(R.id.smsreceipt_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton3.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton3.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton3.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton4 = (Button)findViewById(R.id.smsreceipt_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton4.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton4.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton4.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton5 = (Button)findViewById(R.id.smsreceipt_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton5.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton5.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton5.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton6 = (Button)findViewById(R.id.smsreceipt_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton6.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton6.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton6.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton7 = (Button)findViewById(R.id.smsreceipt_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton7.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton7.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton7.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton8 = (Button)findViewById(R.id.smsreceipt_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton8.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton8.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton8.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton9 = (Button)findViewById(R.id.smsreceipt_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton9.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton9.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton9.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButton0 = (Button)findViewById(R.id.smsreceipt_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButton0.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            smsreceipt_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            smsreceipt_suButton0.setBackgroundResource(numberBackground);
        } else {
            smsreceipt_suButton0.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButtonBack = (Button)findViewById(R.id.smsreceipt_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButtonBack.setText("");
            smsreceipt_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_smsreceiptdelete);
        } else {
            smsreceipt_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButtonV = (Button)findViewById(R.id.smsreceipt_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            smsreceipt_suButtonV.setText("");
        } else {
            smsreceipt_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSizeForPAX() +
                    smsreceipt_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        smsreceipt_suButtonX = (Button)findViewById(R.id.smsreceipt_suButtonX);

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);

        smsreceipt_suButton1.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton2.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton3.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton4.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton5.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton6.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton7.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton8.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton9.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButton0.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButtonBack.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButtonV.setOnClickListener(smsreceiptButtonClickListener);
        smsreceipt_suButtonX.setOnClickListener(smsreceiptButtonClickListener);

        backButton.setOnClickListener(smsreceiptButtonClickListener);

        customerPhoneNumberEv = (EditText)findViewById(R.id.customerPhoneNumberEv);
        customerPhoneNumberEv.setText(customerPhoneNo);
        customerPhoneNumberEv.setOnTouchListener(mTouchsmsreceiptTvTouchListener);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), customerPhoneNumberEv);

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            LinearLayout sendsmsvSpaceLn = (LinearLayout)findViewById(R.id.sendsmsvSpaceLn);
            sendsmsvSpaceLn.setVisibility(View.GONE);
            //sendsmsvBtnTr.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,  1.3f));
        }

        // jihun 0812 Merchant 영수증 프린팅 막음
        // Merchant 용 영수증 프린팅 ----------------------------------------------------------------
//        if (PaymentCustomerSelectReceipt.tempJsonReceipt != null) {
//            GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "Y";
//            GlobalMemberValues.printReceiptByJHP(PaymentCustomerSelectReceipt.tempJsonReceipt, context, "payment");
//        }
        // ----------------------------------------------------------------------------------------
    }

    View.OnClickListener smsreceiptButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.smsreceipt_suButton1 : {
                    numberButtonClick(smsreceipt_suButton1);
                    break;
                }
                case R.id.smsreceipt_suButton2 : {
                    numberButtonClick(smsreceipt_suButton2);
                    break;
                }
                case R.id.smsreceipt_suButton3 : {
                    numberButtonClick(smsreceipt_suButton3);
                    break;
                }
                case R.id.smsreceipt_suButton4 : {
                    numberButtonClick(smsreceipt_suButton4);
                    break;
                }
                case R.id.smsreceipt_suButton5 : {
                    numberButtonClick(smsreceipt_suButton5);
                    break;
                }
                case R.id.smsreceipt_suButton6 : {
                    numberButtonClick(smsreceipt_suButton6);
                    break;
                }
                case R.id.smsreceipt_suButton7 : {
                    numberButtonClick(smsreceipt_suButton7);
                    break;
                }
                case R.id.smsreceipt_suButton8 : {
                    numberButtonClick(smsreceipt_suButton8);
                    break;
                }
                case R.id.smsreceipt_suButton9 : {
                    numberButtonClick(smsreceipt_suButton9);
                    break;
                }
                case R.id.smsreceipt_suButton0 : {
                    numberButtonClick(smsreceipt_suButton0);
                    break;
                }
                case R.id.smsreceipt_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mQtyEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mQtyEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                            mQtyEtValue = "";
                        }
                    } else {
                        mQtyEtValue = "";
                    }
                    customerPhoneNumberEv.setText(mQtyEtValue);

                    // 커서를 맨 뒤로...
                    GlobalMemberValues.setCursorLastDigit(customerPhoneNumberEv);
                    break;
                }
                case R.id.smsreceipt_suButtonX : {
                    init();
                    finishPayment();
                    break;
                }
                case R.id.smsreceipt_suButtonV : {
                    final String toPhoneNumber = customerPhoneNumberEv.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(toPhoneNumber)) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Enter Your Phone Number", "Close");
                        return;
                    }

                    if (jsonReceipt == null || GlobalMemberValues.isStrEmpty(jsonReceipt.toString())) {
                        GlobalMemberValues.displayDialog(context, "Warning", "No Receipt Data", "Close");
                        return;
                    }

                    try {
                        jsonReceipt.put("smsreceiptsidx", GlobalMemberValues.STORE_INDEX);
                        jsonReceipt.put("smsreceiptphonenumber", toPhoneNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!activity.isFinishing()) {
                        new AlertDialog.Builder(activity)
                                .setTitle("Warning")
                                .setMessage("Would you like to receive the receipt on your phone?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //sendReceiptDataToCloud(toPhoneNumber);
                                        sendJsonDataToCloud(jsonReceipt.toString());
                                    }
                                }).show();
                    }

                    break;
                }
                case R.id.backButton : {
                    finish();

                    break;
                }
            }
        }
    };

    private void numberButtonClick(Button btn) {
        if (mQtyEtValue.length() < 17) {
            sb.delete(0, sb.toString().length());
            sb.append(mQtyEtValue).append(btn.getText().toString());
            mQtyEtValue = sb.toString();
            customerPhoneNumberEv.setText(mQtyEtValue);
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(customerPhoneNumberEv);
        }
    }

    View.OnTouchListener mTouchsmsreceiptTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            GlobalMemberValues.setKeyPadHide(context, customerPhoneNumberEv);
            customerPhoneNumberEv.requestFocus();
            customerPhoneNumberEv.setSelection(customerPhoneNumberEv.length());
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(customerPhoneNumberEv);
            return true;
        }
    };

    /*
     Post 방식으로 Http 전송하기
    */
//    private void sendReceiptDataToCloud(final String paramCustomerPhoneNo) {
//        if (GlobalMemberValues.isStrEmpty(paramCustomerPhoneNo)) {
//            GlobalMemberValues.displayDialog(context, "Warning", "Enter Your Phone Number", "Close");
//            return;
//        }
//
//        if (jsonReceipt == null || GlobalMemberValues.isStrEmpty(jsonReceipt.toString())) {
//            GlobalMemberValues.displayDialog(context, "Warning", "No Receipt Data", "Close");
//            return;
//        }
//
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                GlobalMemberValues.logWrite(TAG, "url :" + GlobalMemberValues.API_WEB + "API_SMS_Receipt.asp" + "\n");
//
//                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
//                HttpClient client = new DefaultHttpClient();
//                HttpPost post = new HttpPost(GlobalMemberValues.API_WEB + "API_SMS_Receipt.asp");
//                ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();
//
//                String tempSalesCode = "";
//                try {
//                    tempSalesCode = jsonReceipt.getString("receiptno");
//                } catch (JSONException e) {
//                    tempSalesCode = "";
//                    e.printStackTrace();
//                }
//
//                try {
//                    String tempJsonStr = jsonReceipt.toString();
//                    tempJsonStr = GlobalMemberValues.getReplaceText(tempJsonStr, "\\/", "j0j");
//                    tempJsonStr = GlobalMemberValues.getReplaceText(tempJsonStr, "{", "j1j");
//                    tempJsonStr = GlobalMemberValues.getReplaceText(tempJsonStr, "}", "j2j");
//                    tempJsonStr = GlobalMemberValues.getReplaceText(tempJsonStr, "[", "j3j");
//                    tempJsonStr = GlobalMemberValues.getReplaceText(tempJsonStr, "]", "j4j");
//                    GlobalMemberValues.logWrite(TAG, "tempJsonStr :" + tempJsonStr + "\n");
//
//                    //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
//                    nameValues.add(new BasicNameValuePair("salesCode", URLDecoder.decode(tempSalesCode, "UTF-8")));
//                    nameValues.add(new BasicNameValuePair("sidx", URLDecoder.decode(GlobalMemberValues.STORE_INDEX, "UTF-8")));
//                    nameValues.add(new BasicNameValuePair("tophonenum", URLDecoder.decode(paramCustomerPhoneNo, "UTF-8")));
//                    nameValues.add(new BasicNameValuePair("jsonstr", URLDecoder.decode(tempJsonStr, "UTF-8")));
//
//                    //HttpPost에 넘길 값을들 Set해주기
//                    post.setEntity(new UrlEncodedFormEntity(nameValues, "UTF-8"));
//                } catch (UnsupportedEncodingException ex) {
//                    Log.e("Insert Log", ex.toString());
//                }
//
//                // 전송결과값 (0 아니면 에러)
//                int smsResult = 0;
//                try {
//                    //설정한 URL을 실행시키기
//                    HttpResponse response = client.execute(post);
//                    //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
//                    //Log.i("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());
//                    GlobalMemberValues.logWrite(TAG, "response.getStatusCode:" + response.getStatusLine().getStatusCode() + "\n");
//
//                    if (response.getStatusLine().getStatusCode() == 200) {
//                        smsResult = 0;
//                    } else {
//                        smsResult = 1;
//                    }
//                } catch (ClientProtocolException e) {
//                    smsResult = 2;
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    smsResult = 3;
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    smsResult = 4;
//                    e.printStackTrace();
//                }
//
//                smsSentResult = smsResult;
//                // ---------------------------------------------------------------------------------
//
//
//                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
//                handler.sendEmptyMessage(0);
//                // ---------------------------------------------------------------------------------
//            }
//        });
//        thread.start();
//    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (smsSentResult > 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "SMS receipt failure -- [CODE : " + smsSentResult + "]", "Close");
            } else {
                GlobalMemberValues.displayDialog(context, "SMS Receipt", "The receipt has been sent successfully", "Close");
            }

        }
    };

    public void sendJsonDataToCloud(final String JsonMsg) {
        GlobalMemberValues.logWrite(TAG, "JsonMsg : " + JsonMsg + "\n");
        smsSentResult = 99;
        smsSentResultStr = "";

        Thread thread = new Thread(new Runnable() {
            public void run() {
                GlobalMemberValues.logWrite(TAG, "url :" + GlobalMemberValues.API_WEB + "API_SMS_Receipt.asp" + "\n");

                OutputStream os = null;
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                HttpURLConnection conn = null;
                String response = "";

                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                try {
                    URL url = new URL(GlobalMemberValues.API_WEB + "API_SMS_Receipt.asp");

                    conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5 * 1000);
                    conn.setReadTimeout(5 * 1000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);;

                    os = conn.getOutputStream();
                    os.write(JsonMsg.getBytes());
                    os.flush();

                    int responseCode = conn.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK) {

                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];
                        byte[] byteData = null;
                        int nLength = 0;
                        while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            baos.write(byteBuffer, 0, nLength);
                        }
                        byteData = baos.toByteArray();
                        response = new String(byteData);
                        GlobalMemberValues.logWrite(TAG, "DATA response = " + response + "\n");

                        smsSentResult = 0;
                        smsSentResultStr = response;
                    }

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    smsSentResult = 1;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    smsSentResult = 2;
                } catch ( Exception e ) {
                    GlobalMemberValues.logWrite(TAG, "error msg :" + e.toString() + "\n");
                    smsSentResult = 3;
                }
                // ---------------------------------------------------------------------------------

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handlersms.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    Handler handlersms = new Handler() {
        public void handleMessage(Message msg) {
            if (smsSentResult > 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "SMS receipt failure -- [CODE : " + smsSentResult + "]", "Close");
            } else {
                if (!PaymentSMSReceipt.this.isFinishing()) {
                    new AlertDialog.Builder(context)
                            .setTitle("SMS Receipt")
                            .setMessage("The receipt has been sent successfully")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Close",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finishPayment();
                                            PaymentCustomerSelectReceipt.finishPayment();
                                        }
                                    })
                            .show();
                }
            }

        }
    };

    private void finishPayment() {
        PaymentCustomerSelectReceipt.tempJsonReceipt = null;

        if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GlobalMemberValues.oepnRotateScreenPopup(activity, context, "N");
        } else {
            activity.finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    private void init() {
        mQtyEtValue = "";
    }
}
