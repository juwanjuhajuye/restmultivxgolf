package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class SettingsPaymentGateway extends Activity {
    static Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private Switch cardchargesystemuseSwitch, tipuseSwitch, signaturePadSwitch, tipSelectionOnSignaturePadSwitch;
    private Switch tiplineOnReceiptSwitch, ischeckbeforecardpaySwitch, keyinynSwitch, tipprocessingynSwitch;
    private Spinner paymentgatewaySpinner, commtypeSpinner;
    private EditText paymentgatewayidEditText, paymentgatewaypwdEditText;
    private Button saveSettingsPaymentGatewayButton;

    private TextView settingsPaymentGatewayTitleTextView1, settingsPaymentGatewayTitleTextView2;
    private TextView settingsPaymentGatewayTitleTextView3, settingsPaymentGatewayTitleTextView4;
    private TextView settingsPaymentGatewayTitleTextView5, settingsPaymentGatewayTitleTextView6;
    private TextView settingsPaymentGatewayTitleTextView7, settingsPaymentGatewayTitleTextView8;
    private TextView settingsPaymentGatewayTitleTextView9, settingsPaymentGatewayTitleTextView10;
    private TextView settingsPaymentGatewayTitleTextView11, settingsPaymentGatewayTitleTextView12;
    private TextView settingsPaymentGatewayTitleTextView13, settingsPaymentGatewayTitleTextView14;
    private TextView settingsPaymentGatewayTitleTextView15;

    // 07182024
    // 카드결제 기기등록관련
    private TextView settingsPaymentGatewayTitleTextView16;
    public static TextView devicenumTextView;
    private Button devicenumTextBtn;

    private EditText networkIpTextView1, networkIpTextView2, networkIpTextView3, networkIpTextView4;
    private EditText networkPortTextView;
    private EditText signatureimagedeletedaysagoEv;

    private EditText setting_payment_timeout_Ev;

    private TextView tipselect1, tipselect2, tipselect3, tipselect4;

    private TextView settingsPaymentGatewaySubTitleTextView1, settingsPaymentGatewaySubTitleTextView2;

    private Button signatureimagedeletedaysagoEv_del_btn;

    ArrayList<String> mGeneralArrayListForSpinnerCardChargeSystemUse;
    ArrayAdapter<String> mSpinnerAdapterCardChargeSystemUse;
    int selectedItemPaymentGatewaySpinner = 1;

    ArrayList<String> mGeneralArrayListForSpinnerCommtype;
    ArrayAdapter<String> mSpinnerAdapterCommtype;
    int selectedCommtypeSpinner = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_payment_gateway);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.settingsSystemLn);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        mActivity = this;
        context = this;

        int vCardchargesystemuse = 1;
        int vPaymentgateway = 1;
        String vPaymentgatewayid = "";
        String vPaymentgatewaypwd = "";
        int vTipuse = 1;

        String vSignaturePad = "N";
        String vTipselectonsignature = "N";

        String vTiplineOntheReceipt = "N";

        String vIscheckbeforecardpay = "N";

        String vKeyinyn = "N";

        String vTipprocessingyn = "N";

        String vNetworkip1 = "";
        String vNetworkip2 = "";
        String vNetworkip3 = "";
        String vNetworkip4 = "";
        String vNetworkport = "";

        String vSignatureimagedeletedaysago = "";

        String vTipselect1 = "";
        String vTipselect2 = "";
        String vTipselect3 = "";
        String vTipselect4 = "";

        String vTimeout = "";

        int vCommtype = 1;

        String strQuery = "select cardchargesystemuse, paymentgateway, paymentgatewayid, paymentgatewaypwd, tipuse, " +
                " networkip1, networkip2, networkip3, networkip4, networkport, signpaduseyn, tipselectonsignature, " +
                " tipselect1, tipselect2, tipselect3, tipselect4, " +
                " tiplineonreceipt, signatureimagedeletedaysago, ischeckbeforecardpay, commtype, keyinyn, tipprocessingyn, timeout " +
                " from salon_storestationsettings_paymentgateway ";
        Cursor settingsPaymentGatewayCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsPaymentGatewayCursor.getCount() > 0 && settingsPaymentGatewayCursor.moveToFirst()) {
            String tempCardchargesystemuse = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(0), 1);
            String tempPaymentgateway = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(1), 1);
            String tempPaymentgatewayid = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(2), 1);
            String tempPaymentgatewaypwd = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(3), 1);
            String tempTipuse = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(4), 1);
            String tempNetworkip1 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(5), 1);
            String tempNetworkip2 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(6), 1);
            String tempNetworkip3 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(7), 1);
            String tempNetworkip4 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(8), 1);
            String tempNetworkport = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(9), 1);
            String tempSignaturePad = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(10), 1);
            String tempTipselectonsignature = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(11), 1);
            String tempTipselect1 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(12), 1);
            String tempTipselect2 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(13), 1);
            String tempTipselect3 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(14), 1);
            String tempTipselect4 = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(15), 1);
            String tempTiplineOntheReceipt = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(16), 1);
            String tempSignatureimagedeletedaysago = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(17), 1);
            String tempIscheckbeforecardpay = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(18), 1);
            String tempCommtype = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(19), 1);
            String tempKeyinyn = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(20), 1);
            String tempTipprocessingyn = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(21), 1);
            String tempTimeout = GlobalMemberValues.getDBTextAfterChecked(settingsPaymentGatewayCursor.getString(22), 1);

            vNetworkip1 = tempNetworkip1;
            vNetworkip2 = tempNetworkip2;
            vNetworkip3 = tempNetworkip3;
            vNetworkip4 = tempNetworkip4;
            vNetworkport = tempNetworkport;

            vSignatureimagedeletedaysago = tempSignatureimagedeletedaysago;

            if (!GlobalMemberValues.isStrEmpty(tempCardchargesystemuse)) {
                vCardchargesystemuse = GlobalMemberValues.getIntAtString(tempCardchargesystemuse);
            }
            if (!GlobalMemberValues.isStrEmpty(tempPaymentgateway)) {
                vPaymentgateway = GlobalMemberValues.getIntAtString(tempPaymentgateway);
            }
            if (!GlobalMemberValues.isStrEmpty(tempPaymentgatewayid)) {
                vPaymentgatewayid = tempPaymentgatewayid;
            }
            if (!GlobalMemberValues.isStrEmpty(tempPaymentgatewaypwd)) {
                vPaymentgatewaypwd = tempPaymentgatewaypwd;
            }
            if (!GlobalMemberValues.isStrEmpty(tempTipuse)) {
                vTipuse = GlobalMemberValues.getIntAtString(tempTipuse);
            }

            if (!GlobalMemberValues.isStrEmpty(tempIscheckbeforecardpay)) {
                vIscheckbeforecardpay = tempIscheckbeforecardpay;
            } else {
                vIscheckbeforecardpay = "N";
            }

            if (GlobalMemberValues.isStrEmpty(tempSignaturePad)) {
                tempSignaturePad = "N";
            }
            vSignaturePad = tempSignaturePad;

            if (GlobalMemberValues.isStrEmpty(tempTipselectonsignature)) {
                tempTipselectonsignature = "N";
            }
            vTipselectonsignature = tempTipselectonsignature;

            if (GlobalMemberValues.isStrEmpty(tempTipselect1)) {
                tempTipselect1 = "5.0";
            }
            vTipselect1 = tempTipselect1;

            if (GlobalMemberValues.isStrEmpty(tempTipselect2)) {
                tempTipselect2 = "10.0";
            }
            vTipselect2 = tempTipselect2;

            if (GlobalMemberValues.isStrEmpty(tempTipselect3)) {
                tempTipselect3 = "15.0";
            }
            vTipselect3 = tempTipselect3;

            if (GlobalMemberValues.isStrEmpty(tempTipselect4)) {
                tempTipselect4 = "20.0";
            }
            vTipselect4 = tempTipselect4;

            if (GlobalMemberValues.isStrEmpty(tempTiplineOntheReceipt)) {
                tempTiplineOntheReceipt = "N";
            }
            vTiplineOntheReceipt = tempTiplineOntheReceipt;

            if (!GlobalMemberValues.isStrEmpty(tempCommtype)) {
                vCommtype = GlobalMemberValues.getIntAtString(tempCommtype);
            }

            if (!GlobalMemberValues.isStrEmpty(tempKeyinyn)) {
                vKeyinyn = tempKeyinyn;
            } else {
                vKeyinyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempTipprocessingyn)) {
                vTipprocessingyn = tempTipprocessingyn;
            } else {
                vTipprocessingyn = "N";
            }

            if (!GlobalMemberValues.isStrEmpty(tempTimeout)){
                vTimeout = tempTimeout;
            } else {
                vTimeout = "1";
            }

        }

        // Card Charge System Use
        cardchargesystemuseSwitch = (Switch)findViewById(R.id.cardchargesystemuseSwitch);
        if (vCardchargesystemuse == 0) {
            cardchargesystemuseSwitch.setChecked(true);
        } else {
            cardchargesystemuseSwitch.setChecked(false);
        }

        // Payment Gateway ----------------------------------------------------------------------------------------
        paymentgatewaySpinner = (Spinner)findViewById(R.id.paymentgatewaySpinner);
        mGeneralArrayListForSpinnerCardChargeSystemUse = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.PAYMENTGATEWAYGROUP.length; i++) {
            mGeneralArrayListForSpinnerCardChargeSystemUse.add(GlobalMemberValues.PAYMENTGATEWAYGROUP[i]);
        }
        mSpinnerAdapterCardChargeSystemUse = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerCardChargeSystemUse);
        mSpinnerAdapterCardChargeSystemUse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentgatewaySpinner.setAdapter(mSpinnerAdapterCardChargeSystemUse);
        paymentgatewaySpinner.setSelection(vPaymentgateway);
        paymentgatewaySpinner.setOnItemSelectedListener(mCardChargeUseSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // Comm Type ----------------------------------------------------------------------------------------
        commtypeSpinner = (Spinner)findViewById(R.id.commtypeSpinner);
        mGeneralArrayListForSpinnerCommtype = new ArrayList<String>();
        for (int i = 0; i < GlobalMemberValues.COMMTYPE.length; i++) {
            mGeneralArrayListForSpinnerCommtype.add(GlobalMemberValues.COMMTYPE[i]);
        }
        mSpinnerAdapterCommtype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinnerCommtype);
        mSpinnerAdapterCommtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commtypeSpinner.setAdapter(mSpinnerAdapterCommtype);
        commtypeSpinner.setSelection(vCommtype);
        commtypeSpinner.setOnItemSelectedListener(mCommtypeSpinnerItemSelectedListener);
        // ---------------------------------------------------------------------------------------------------------

        // paymentgatewayid
        paymentgatewayidEditText = (EditText)findViewById(R.id.paymentgatewayidEditText);
        paymentgatewayidEditText.setText(vPaymentgatewayid);
        paymentgatewayidEditText.setTextSize(paymentgatewayidEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // paymentgatewaypwd
        paymentgatewaypwdEditText = (EditText)findViewById(R.id.paymentgatewaypwdEditText);
        paymentgatewaypwdEditText.setText(vPaymentgatewaypwd);
        paymentgatewaypwdEditText.setTextSize(paymentgatewaypwdEditText.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Network IP 1
        networkIpTextView1 = (EditText)findViewById(R.id.networkIpTextView1);
        networkIpTextView1.setText(vNetworkip1);
        networkIpTextView1.setTextSize(networkIpTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 2
        networkIpTextView2 = (EditText)findViewById(R.id.networkIpTextView2);
        networkIpTextView2.setText(vNetworkip2);
        networkIpTextView2.setTextSize(networkIpTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 3
        networkIpTextView3 = (EditText)findViewById(R.id.networkIpTextView3);
        networkIpTextView3.setText(vNetworkip3);
        networkIpTextView3.setTextSize(networkIpTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP 4
        networkIpTextView4 = (EditText)findViewById(R.id.networkIpTextView4);
        networkIpTextView4.setText(vNetworkip4);
        networkIpTextView4.setTextSize(networkIpTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // Network IP Port
        networkPortTextView = (EditText)findViewById(R.id.networkPortTextView);
        networkPortTextView.setText(vNetworkport);
        networkPortTextView.setTextSize(networkPortTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Signature Image Delete Ago
        signatureimagedeletedaysagoEv = (EditText)findViewById(R.id.signatureimagedeletedaysagoEv);
        signatureimagedeletedaysagoEv.setText(vSignatureimagedeletedaysago);
        signatureimagedeletedaysagoEv.setTextSize(signatureimagedeletedaysagoEv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tip Selection 1
        tipselect1 = (EditText)findViewById(R.id.tipselect1);
        tipselect1.setText(vTipselect1);
        tipselect1.setTextSize(tipselect1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tip Selection 2
        tipselect2 = (EditText)findViewById(R.id.tipselect2);
        tipselect2.setText(vTipselect2);
        tipselect2.setTextSize(tipselect2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tip Selection 3
        tipselect3 = (EditText)findViewById(R.id.tipselect3);
        tipselect3.setText(vTipselect3);
        tipselect3.setTextSize(tipselect3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tip Selection 4
        tipselect4 = (EditText)findViewById(R.id.tipselect4);
        tipselect4.setText(vTipselect4);
        tipselect4.setTextSize(tipselect4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // Tip Use
        tipuseSwitch = (Switch)findViewById(R.id.tipuseSwitch);
        if (vTipuse == 0) {
            tipuseSwitch.setChecked(true);
        } else {
            tipuseSwitch.setChecked(false);
        }

        // Signature Pad Use Y/N
        signaturePadSwitch = (Switch)findViewById(R.id.signaturePadSwitch);
        if (vSignaturePad == "Y" || vSignaturePad.equals("Y")) {
            signaturePadSwitch.setChecked(true);
        } else {
            signaturePadSwitch.setChecked(false);
        }

        // Tip Selection On Signature Pad Use Y/N
        tipSelectionOnSignaturePadSwitch = (Switch)findViewById(R.id.tipSelectionOnSignaturePadSwitch);
        if (vTipselectonsignature == "Y" || vTipselectonsignature.equals("Y")) {
            tipSelectionOnSignaturePadSwitch.setChecked(true);
        } else {
            tipSelectionOnSignaturePadSwitch.setChecked(false);
        }

        // Tip Line On the Receipt Y/N
        tiplineOnReceiptSwitch = (Switch)findViewById(R.id.tiplineOnReceiptSwitch);
        if (vTiplineOntheReceipt == "Y" || vTiplineOntheReceipt.equals("Y")) {
            tiplineOnReceiptSwitch.setChecked(true);
        } else {
            tiplineOnReceiptSwitch.setChecked(false);
        }

        // Check the internet before pay process Y/N
        ischeckbeforecardpaySwitch = (Switch)findViewById(R.id.ischeckbeforecardpaySwitch);
        if (vIscheckbeforecardpay == "Y" || vIscheckbeforecardpay.equals("Y")) {
            ischeckbeforecardpaySwitch.setChecked(true);
        } else {
            ischeckbeforecardpaySwitch.setChecked(false);
        }

        // Key in Y/N
        keyinynSwitch = (Switch)findViewById(R.id.keyinynSwitch);
        if (vKeyinyn == "Y" || vKeyinyn.equals("Y")) {
            keyinynSwitch.setChecked(true);
        } else {
            keyinynSwitch.setChecked(false);
        }

        // Tip Processing
        tipprocessingynSwitch = (Switch)findViewById(R.id.tipprocessingynSwitch);
        if (vTipprocessingyn == "Y" || vTipprocessingyn.equals("Y")) {
            tipprocessingynSwitch.setChecked(true);
        } else {
            tipprocessingynSwitch.setChecked(false);
        }

        saveSettingsPaymentGatewayButton = (Button)findViewById(R.id.saveSettingsPaymentGatewayButton);
        saveSettingsPaymentGatewayButton.setOnClickListener(mButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saveSettingsPaymentGatewayButton.setText("");
            saveSettingsPaymentGatewayButton.setBackgroundResource(R.drawable.ab_imagebutton_settings_enter);
        } else {
            saveSettingsPaymentGatewayButton.setTextSize(
                    saveSettingsPaymentGatewayButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        settingsPaymentGatewayTitleTextView1 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView1);
        settingsPaymentGatewayTitleTextView1.setTextSize(settingsPaymentGatewayTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView2 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView2);
        settingsPaymentGatewayTitleTextView2.setTextSize(settingsPaymentGatewayTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView3 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView3);
        settingsPaymentGatewayTitleTextView3.setTextSize(settingsPaymentGatewayTitleTextView3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView4 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView4);
        settingsPaymentGatewayTitleTextView4.setTextSize(settingsPaymentGatewayTitleTextView4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView5 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView5);
        settingsPaymentGatewayTitleTextView5.setTextSize(settingsPaymentGatewayTitleTextView5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView6 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView6);
        settingsPaymentGatewayTitleTextView6.setTextSize(settingsPaymentGatewayTitleTextView6.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView7 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView7);
        settingsPaymentGatewayTitleTextView7.setTextSize(settingsPaymentGatewayTitleTextView7.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView8 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView8);
        settingsPaymentGatewayTitleTextView8.setTextSize(settingsPaymentGatewayTitleTextView8.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView9 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView9);
        settingsPaymentGatewayTitleTextView9.setTextSize(settingsPaymentGatewayTitleTextView9.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView10 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView10);
        settingsPaymentGatewayTitleTextView10.setTextSize(settingsPaymentGatewayTitleTextView10.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView11 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView11);
        settingsPaymentGatewayTitleTextView11.setTextSize(settingsPaymentGatewayTitleTextView11.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView12 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView12);
        settingsPaymentGatewayTitleTextView12.setTextSize(settingsPaymentGatewayTitleTextView12.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView13 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView13);
        settingsPaymentGatewayTitleTextView13.setTextSize(settingsPaymentGatewayTitleTextView13.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView14 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView14);
        settingsPaymentGatewayTitleTextView14.setTextSize(settingsPaymentGatewayTitleTextView14.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewayTitleTextView15 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView15);
        settingsPaymentGatewayTitleTextView15.setTextSize(settingsPaymentGatewayTitleTextView15.getTextSize() * GlobalMemberValues.getGlobalFontSize());



        // 07182024 ----------------------------------------------------------------------------------------------
        // 카드결제 기기등록관련
        settingsPaymentGatewayTitleTextView16 = (TextView)findViewById(R.id.settingsPaymentGatewayTitleTextView16);
        settingsPaymentGatewayTitleTextView16.setTextSize(settingsPaymentGatewayTitleTextView16.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        devicenumTextView = (TextView)findViewById(R.id.devicenumTextView);
        devicenumTextView.setTextSize(devicenumTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        devicenumTextBtn = (Button)findViewById(R.id.devicenumTextBtn);
        devicenumTextBtn.setOnClickListener(mButtonClick);
//        devicenumTextBtn.setTextSize(
//                devicenumTextBtn.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );

        String deviceNumPGIP = "";
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getPGDeviceNum())) {
            deviceNumPGIP = GlobalMemberValues.getPGDeviceNum() + " (IP : " + GlobalMemberValues.getPGIP(GlobalMemberValues.getPGDeviceNum()) + ") ";
        }

        devicenumTextView.setText(deviceNumPGIP);
        // 07182024 ----------------------------------------------------------------------------------------------


        settingsPaymentGatewaySubTitleTextView1 = (TextView)findViewById(R.id.settingsPaymentGatewaySubTitleTextView1);
        settingsPaymentGatewaySubTitleTextView1.setTextSize(settingsPaymentGatewaySubTitleTextView1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        settingsPaymentGatewaySubTitleTextView2 = (TextView)findViewById(R.id.settingsPaymentGatewaySubTitleTextView2);
        settingsPaymentGatewaySubTitleTextView2.setTextSize(settingsPaymentGatewaySubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        setting_payment_timeout_Ev = (EditText)findViewById(R.id.setting_payment_timeout);
        setting_payment_timeout_Ev.setTextSize(settingsPaymentGatewaySubTitleTextView2.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        setting_payment_timeout_Ev.setText(vTimeout);

        signatureimagedeletedaysagoEv_del_btn = (Button) findViewById(R.id.signatureimagedeletedaysagoEv_del_btn);
        signatureimagedeletedaysagoEv_del_btn.setOnClickListener(msignatureimagedeletedaysagoEv_del_btn);



        // 07242026 ------------------------------
        LinearLayout networkipLn1 = (LinearLayout) findViewById(R.id.networkipLn1);
        networkipLn1.setVisibility(View.GONE);
        LinearLayout networkipLn2 = (LinearLayout) findViewById(R.id.networkipLn2);
        networkipLn2.setVisibility(View.GONE);
        // 07242026 ------------------------------
    }

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveSettingsPaymentGatewayButton : {
                    int resultValue = setSettingsPaymentGateway();
                    if (resultValue > 0) {
                        GlobalMemberValues.displayDialog(context, "Settings - Payment Gateway", "Successfully Updated", "Close");
                    } else {
                        GlobalMemberValues.displayDialog(context, "Settings - Payment Gateway", "Update Failure", "Close");
                    }

                    break;
                }
                case R.id.devicenumTextBtn : {

                    Intent pgDeviceIntent = new Intent(MainActivity.mContext, SettingsPaymentGatewayDevices.class);
                    mActivity.startActivity(pgDeviceIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
                    }

                    break;
                }
            }
        }
    };

    View.OnClickListener msignatureimagedeletedaysagoEv_del_btn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new AlertDialog.Builder(context)
                    .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                    .setMessage("Would you delete all signature Img?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            GlobalMemberValues.deleteSignature(context);
                        }
                    })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                    .show();
        }
    };


    public int setSettingsPaymentGateway() {
        int insCardchargesystemuse = 1;
        int insPaymentgateway = 1;
        String insPaymentgatewayid = "";
        String insPaymentgatewaypwd = "";
        int insTipuse = 0;

        String insNetworkip1 = "";
        String insNetworkip2 = "";
        String insNetworkip3 = "";
        String insNetworkip4 = "";
        String insNetworkport = "";

        String insSignaturePad = "";

        String insSignatureimagedeletedaysago = "";

        String insTipselectonsignature = "";
        String insTipSelect1 = "";
        String insTipSelect2 = "";
        String insTipSelect3 = "";
        String insTipSelect4 = "";

        String insTiplineOntheReceipt = "N";

        String insIscheckbeforecardpay = "N";

        String insKeyinyn = "N";

        String insTipprocessingyn = "N";

        int insCommtype = 1;

        String insTimeout = "";

        if (cardchargesystemuseSwitch.isChecked()) {
            insCardchargesystemuse = 0;
        } else {
            insCardchargesystemuse = 1;
        }

        insPaymentgateway = selectedItemPaymentGatewaySpinner;
        insPaymentgatewayid = paymentgatewayidEditText.getText().toString();
        insPaymentgatewaypwd = paymentgatewaypwdEditText.getText().toString();

        insNetworkip1 = networkIpTextView1.getText().toString();
        insNetworkip2 = networkIpTextView2.getText().toString();
        insNetworkip3 = networkIpTextView3.getText().toString();
        insNetworkip4 = networkIpTextView4.getText().toString();
        insNetworkport = networkPortTextView.getText().toString();

        insCommtype = selectedCommtypeSpinner;

        insSignatureimagedeletedaysago = signatureimagedeletedaysagoEv.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insSignatureimagedeletedaysago)) {
            insSignatureimagedeletedaysago = "180";
        }

        if (tipuseSwitch.isChecked()) {
            insTipuse = 0;
        } else {
            insTipuse = 1;
        }

        if (signaturePadSwitch.isChecked()) {
            insSignaturePad = "Y";
        } else {
            insSignaturePad = "N";
        }

        if (tipSelectionOnSignaturePadSwitch.isChecked()) {
            insTipselectonsignature = "Y";
        } else {
            insTipselectonsignature = "N";
        }

        insTipSelect1 = tipselect1.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTipSelect1)) {
            insTipSelect1 = "5.0";
        }
        insTipSelect2 = tipselect2.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTipSelect2)) {
            insTipSelect2 = "5.0";
        }
        insTipSelect3 = tipselect3.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTipSelect3)) {
            insTipSelect3 = "5.0";
        }
        insTipSelect4 = tipselect4.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTipSelect4)) {
            insTipSelect4 = "5.0";
        }

        if (tiplineOnReceiptSwitch.isChecked()) {
            insTiplineOntheReceipt = "Y";
        } else {
            insTiplineOntheReceipt = "N";
        }

        if (ischeckbeforecardpaySwitch.isChecked()) {
            insIscheckbeforecardpay = "Y";
        } else {
            insIscheckbeforecardpay = "N";
        }

        if (keyinynSwitch.isChecked()) {
            insKeyinyn = "Y";
        } else {
            insKeyinyn = "N";
        }

        if (tipprocessingynSwitch.isChecked()) {
            insTipprocessingyn = "Y";
        } else {
            insTipprocessingyn = "N";
        }

        insTimeout = setting_payment_timeout_Ev.getText().toString();
        if (GlobalMemberValues.isStrEmpty(insTimeout)){
            insTimeout = "1";
        }

        int returnValue = 0;

        // PG 가 PAX POSLink 이면 port 값을 반드시 입력받도록 한다.
        if (insPaymentgateway == 1) {

            // 쿼리문자열을 담을 벡터 변수생성
            Vector<String> strUpdateQueryVec = new Vector<String>();
            String updStrQuery = "update salon_storestationsettings_paymentgateway set " +
                    " cardchargesystemuse = '" + insCardchargesystemuse + "', " +
                    " paymentgateway = '" + insPaymentgateway + "', " +
                    " paymentgatewayid = '" + insPaymentgatewayid + "', " +
                    " paymentgatewaypwd = '" + insPaymentgatewaypwd + "', " +
                    " tipuse = '" + insTipuse + "', " +
                    " networkip1 = '" + insNetworkip1 + "', " +
                    " networkip2 = '" + insNetworkip2 + "', " +
                    " networkip3 = '" + insNetworkip3 + "', " +
                    " networkip4 = '" + insNetworkip4 + "', " +
                    " networkport = '" + insNetworkport + "', " +
                    " signpaduseyn = '" + insSignaturePad + "', " +
                    " tipselectonsignature = '" + insTipselectonsignature + "', " +
                    " tipselect1 = '" + insTipSelect1 + "', " +
                    " tipselect2 = '" + insTipSelect2 + "', " +
                    " tipselect3 = '" + insTipSelect3 + "', " +
                    " tipselect4 = '" + insTipSelect4 + "', " +
                    " tiplineonreceipt = '" + insTiplineOntheReceipt + "', " +
                    " signatureimagedeletedaysago = '" + insSignatureimagedeletedaysago + "', " +
                    " ischeckbeforecardpay = '" + insIscheckbeforecardpay + "', " +
                    " commtype = '" + insCommtype + "', " +
                    " keyinyn = '" + insKeyinyn + "', " +
                    " tipprocessingyn = '" + insTipprocessingyn + "', " +
                    " timeout = '" + insTimeout + "', " +
                    " mdate = datetime('now') ";
            strUpdateQueryVec.addElement(updStrQuery);

            for (String tempQuery : strUpdateQueryVec) {
                GlobalMemberValues.logWrite("SettingsSystemLog", "query : " + tempQuery + "\n");
            }

            if (strUpdateQueryVec.size() > 0) {
                // 트랜잭션으로 DB 처리한다.
                String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    returnValue = 0;
                } else {
                    returnValue = 1;
                }
            } else {
                returnValue = 0;
            }

            // Global Static 변수에 저장해야 할 경우 아래에 코딩.....
            if (returnValue > 0) {
                if (insIscheckbeforecardpay == "Y" || insIscheckbeforecardpay.equals("Y")) {
                    GlobalMemberValues.ISCHECK_BEFORE_CARDPAY = true;
                } else {
                    GlobalMemberValues.ISCHECK_BEFORE_CARDPAY = false;
                }

                if (insKeyinyn == "Y" || insKeyinyn.equals("Y")) {
                    GlobalMemberValues.CARD_KEY_IN = true;
                } else {
                    GlobalMemberValues.CARD_KEY_IN = false;
                }

                if (insTipprocessingyn == "Y" || insTipprocessingyn.equals("Y")) {
                    GlobalMemberValues.CARD_TIP_PROCESSING = true;
                } else {
                    GlobalMemberValues.CARD_TIP_PROCESSING = false;
                }
            }
        }

        return returnValue;
    }


    AdapterView.OnItemSelectedListener mCardChargeUseSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemPaymentGatewaySpinner = position;
            GlobalMemberValues.logWrite("SYSTEMPAYMENTGATEWAY", "Selected Payment Gateway : " + selectedItemPaymentGatewaySpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener mCommtypeSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedCommtypeSpinner = position;
            GlobalMemberValues.logWrite("SYSTEMPAYMENTGATEWAY", "Selected Comm Type : " + selectedCommtypeSpinner + "\n");
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

}
