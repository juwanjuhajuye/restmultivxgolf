package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.DialogFragment;

import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.BillSplitMerge;
import com.pax.poslink.CommSetting;
import com.pax.poslink.LogSetting;
import com.pax.poslink.PaymentRequest;
import com.pax.poslink.PaymentResponse;
import com.pax.poslink.PosLink;
import com.pax.poslink.ProcessTransResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class JJJ_PaxPay extends BaseFragmentActivity implements View.OnClickListener, View.OnTouchListener, ProgressDialogFragment.OnSetListener {
    private static final String TAG = "PaxPaymentClass"; // log label

    static Activity mActivity = null;
    static Context mContext = null;

    private String sigfilepath = null;

    private String mLast_Request_Tender = null;

    private String mLast_Request_Trans = null;

    private EditText mRequest_ExtData = null; // Extended data component for
    // request

    private TextView mSaveSigfilepath = null;

    // jihun park
    private PosLink poslink = new PosLink();
    //    private PosLink poslink = new PosLink(this, Context.class);

    private static ProcessTransResult ptr;

    Intent mIntent;
    String mGetCardTenderType = "";
    String mGetAmountToPay = "0";
    String mGetProcessType = "";
    String mGetVoidTypeReturnYN = "N";
    String mGetRefNum = "";
    String mGetECRRefNum = "";
    String mGetSalonSalesCardIdx = "";          // Void 를 위한 salon_sales_card 테이블 인덱스
    String mGetPriceAmountForVoid = "";
    String mGetSelectedCardTagName = "";
    String mGetGroupVoidCount = "";
    String mGetGroupVoidYN = "N";

    // force
    String mForce = "";
    //

    String mGetCardComp = "";                   // tip 카드결제일 경우 기 결제된 카드종류(회사)
    String mGetCardFourDigits = "";             // tip 카드결제일 경우 기 결제된 카드의 뒤 4자리 숫자

    String mGetFromSignPadYN = "N";                 // 싸인패드에서 넘어온 값인지 여부

    String mGetPaymentPaidYN = "N";                 // 결제부분에서 보이드 처리요청일경우

    // Key in 관련 정보
    String mCardExtData = "";               // Keyin 일 경우 카드정보 조합

    String mGetAdjustedynexe = "N";                 // Tip 일괄처리일 경우

    String mGettiponetimeyn = "N";                 // Tip 처리를 한번만 실행할지 여부


    // 결과값 리턴관련 ------------------------------------------------------------------
    JSONObject xmlMade = null;

    String returncodefromdevice = "";
    String authnumber = "";
    String cardcompay = "";
    String lastfourdigits = "";
    String referencenumber = "";
    String emvaid = "";
    String emvtsi = "";
    String emvtvr = "";
    // add up PLNameOnCard
    String plnameoncard = "";
    // add up amount due
    String amountDue = "";
    // --------------------------------------------------------------------------------

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.TRANSACTION_SUCCESSED:
                    try {
                        PaymentResponse response = (PaymentResponse)msg.obj;
                        setPaymentResponse(response);
                        if(response.SignData.length()!=0 &&response.SigFileName.length() !=0 ) {

                            if(mSaveSigfilepath.length() == 0 ) {
                                poslink.appDataFolder = getApplicationContext().getFilesDir().getAbsolutePath() ;
                                String folder = poslink.appDataFolder+"/img/receipt";
                                File fileDis = new File(folder);
                                fileDis.mkdirs();
                                mSaveSigfilepath.setText(folder);
                            }
                            String tmp= (String) mSaveSigfilepath.getText();
                            sigfilepath =tmp +"/"+response.SigFileName+".png";
                            try {
                                int ret = ConvertSigToPic(response.SignData,"png", sigfilepath);
                                Log.e(TAG, "SignData" + response.SignData);
                                Log.e(TAG, "Signfile:" +sigfilepath);
                                Log.e(TAG, "ret" +ret);
                                if(ret < 0)
                                    Toast.makeText(getApplicationContext(), "save image fail", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), "save image sucess", Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "ERROR", e.getMessage().toLowerCase(), "Close");
                    }

                    GlobalMemberValues.logWrite(TAG, "TRANSACTION_SUCCESSED......." + "\n");
                    break;
                case Constant.TRANSACTION_TIMEOOUT:
                    try {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Warning", "Card processing is timeout", "Close");
                    } catch (Exception e) {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "ERROR", e.getMessage().toLowerCase(), "Close");
                    }

                case Constant.TRANSACTION_FAILURE:
                    try {
                        String title = msg.getData().getString(Constant.DIALOG_TITLE);
                        String message = msg.getData().getString(Constant.DIALOG_MESSAGE);
                        //PopupDialogFragment.newInstance(title, message, true, true).show(getSupportFragmentManager(), "payment_failure");
                        //getSupportFragmentManager().beginTransaction().commit();
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Processing Error1", message, "Close");
                    } catch (Exception e) {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "ERROR", e.getMessage().toLowerCase(), "Close");
                    }

                    GlobalMemberValues.logWrite(TAG, "TRANSACTION_FAILURE......." + "\n");
                    break;
                case Constant.TRANSACTION_STATUS:
                    try {
                        String sTitle = "REPORTED STATUS";
                        String sMessage = msg.obj.toString();
                        //PopupDialogFragment.newInstance(sTitle, sMessage, false, true).show(getSupportFragmentManager(), "payment_status");
                        //getSupportFragmentManager().beginTransaction().commit();
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Processing Error2", sMessage, "Close");

                    } catch (Exception e) {
                        GlobalMemberValues.displayDialog(PaymentCreditCard.context, "ERROR", e.getMessage().toLowerCase(), "Close");
                    }
                    GlobalMemberValues.logWrite(TAG, "TRANSACTION_STATUS......." + "\n");
                    break;
            }

            finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjjpax_pay);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mGetCardTenderType = mIntent.getStringExtra("cardtendertype");
            mGetProcessType = mIntent.getStringExtra("processtype");
            mGetVoidTypeReturnYN = mIntent.getStringExtra("voidtypereturnyn");
            mGetAmountToPay = mIntent.getStringExtra("amounttopay");
            mGetRefNum = mIntent.getStringExtra("refnum");
            if (GlobalMemberValues.isStrEmpty(mGetRefNum)) {
                mGetRefNum = "";
            }
            mGetECRRefNum = mGetRefNum;
            if (GlobalMemberValues.isStrEmpty(mGetECRRefNum)) {
                mGetECRRefNum = "1";
            }
            mGetSalonSalesCardIdx = mIntent.getStringExtra("salonsalescardidx");
            mGetPriceAmountForVoid = mIntent.getStringExtra("priceamountforvoid");
            mGetSelectedCardTagName = mIntent.getStringExtra("selectedcardtagname");
            mGetGroupVoidCount = mIntent.getStringExtra("groupvoidcount");
            mGetGroupVoidYN = mIntent.getStringExtra("groupvoidyn");

            mGetCardComp = mIntent.getStringExtra("cardcom");
            mGetCardFourDigits = mIntent.getStringExtra("lastfourdigits");

            mGetFromSignPadYN = mIntent.getStringExtra("fromsignpad");

            mGetPaymentPaidYN = mIntent.getStringExtra("paymentpaidyn");

            mGetAdjustedynexe = mIntent.getStringExtra("adjustedynexe");
            if (GlobalMemberValues.isStrEmpty(mGetAdjustedynexe)) {
                mGetAdjustedynexe = "N";
            }

            mGettiponetimeyn = mIntent.getStringExtra("tiponetimeyn");
            if (GlobalMemberValues.isStrEmpty(mGettiponetimeyn)) {
                mGettiponetimeyn = "N";
            }

            // force
            mForce = mIntent.getStringExtra("set_force");

            GlobalMemberValues.logWrite("onetimetipadjustmentlog", "여기실행4" + "\n");

            GlobalMemberValues.logWrite("onetimetipadjustmentlog", "mGettiponetimeyn : " + mGettiponetimeyn + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetCardTenderType : " + mGetCardTenderType + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetAmountToPay : " + mGetAmountToPay + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetProcessType : " + mGetProcessType + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetRefNum : " + mGetRefNum + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetECRRefNum : " + mGetECRRefNum + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetSalonSalesCardIdx : " + mGetSalonSalesCardIdx + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetPriceAmountForVoid : " + mGetPriceAmountForVoid + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetSelectedCardTagName : " + mGetSelectedCardTagName + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetGroupVoidCount : " + mGetGroupVoidCount + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetGroupVoidYN : " + mGetGroupVoidYN + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetCardComp : " + mGetCardComp + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetCardFourDigits : " + mGetCardFourDigits + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetFromSignPadYN : " + mGetFromSignPadYN + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetPaymentPaidYN : " + mGetPaymentPaidYN + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetAdjustedynexe : " + mGetAdjustedynexe + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGettiponetimeyn : " + mGettiponetimeyn + "\n");

            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetCardNumber : " + PaymentCreditCard.mKeyinCardNumber + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetExpDate : " + PaymentCreditCard.mKeyinExpDate + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetCVVNumber : " + PaymentCreditCard.mKeyinCVVNumber + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetAddress : " + PaymentCreditCard.mKeyinAddr + "\n");
            GlobalMemberValues.logWrite(TAG + "1", "넘겨받은 mGetZipcode : " + PaymentCreditCard.mKeyinZip + "\n");
            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        initPaymentResponse();
        setTask();

        paymentProcess(mGetProcessType);
    }

    // 카드 status 관련 ---------------------------------------------------------------------------------------
    private void saveCardTryData(String paramAmount, String paramProcessType) {
        if (GlobalMemberValues.getDoubleAtString(paramAmount) == 0) {
            return;
        }

        double dblAmount = GlobalMemberValues.getDoubleAtString(paramAmount) * 0.01;

        String insAmount = dblAmount + "";

        String tempCustomer_id = "";
        String tempCustomer_name = "";
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            tempCustomer_id = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
            tempCustomer_name = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
        }
        String tempEmp_idx = "";
        String tempEmp_name = "";
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
            tempEmp_idx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            tempEmp_name = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
        }

        String get_items = GlobalMemberValues.getMenuItemsInCart();
        if (!GlobalMemberValues.isStrEmpty(get_items)) {
            String item_split[] = get_items.split("-WANHAYE-");
            String item_all = item_split[0];
            String item_list = item_split[1];
            String strQuery = " insert into card_processing_data ( " +
                    " holdcode, sidx, stcode, " +
                    " customerId, customerName, employeeIdx, employeeName," +
                    " menuitems_list, menuitems_all, amount, procestype, resultcode, resultmsg " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(MainMiddleService.mHoldCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempCustomer_id,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempCustomer_name,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempEmp_idx,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(tempEmp_name,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_list,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(item_all,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insAmount,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(paramProcessType,0) + "', " +
                    " '', " +
                    " 'PENDDING' " +
                    " ) ";

            Vector<String> vec = new Vector<String >();
            vec.add(strQuery);

            String returnCode = "";
            returnCode = MssqlDatabase.executeTransaction(vec);

            if (returnCode.equals("N")) {
            } else {
            }
        }
    }
    // ----------------------------------------------------------------------------------------------------------




    /**
     private void initJob() {
     Button payStrat = (Button)findViewById(R.id.payStart);
     payStrat.setOnClickListener(new Button.OnClickListener() {
    @Override
    public void onClick(View v) {
    paymentProcess("SALE");
    }
    });
     }
     **/

    private void setCommSetting(){
        ConfigPGInfo pgInfo = new ConfigPGInfo();

        String networkIp = pgInfo.cfnetworkip;
        String networkPort = pgInfo.cfnetworkport;
        String commtype = pgInfo.cfcommtype;
        if (GlobalMemberValues.isStrEmpty(commtype)) {
            if (GlobalMemberValues.isDevicePAXFromDB()) {
                commtype = "USB";
            } else {
                commtype = "TCP";
            }
        }

        String tempNetworkIP = networkIp;
        String tempSerialPort = "";
        String tempBaudRate = "";
        String tempNetworkPort = networkPort;
        boolean tempProxyValue = false;

        if (commtype.equals("USB")) {
            tempSerialPort = "COM1";
            tempBaudRate = "9600";
            tempNetworkIP = "";
            tempNetworkPort = "10009";
            tempProxyValue = true;
        }

        GlobalMemberValues.logWrite("paxcommsettinglog", "commtype : " + commtype + "\n");
        GlobalMemberValues.logWrite("paxcommsettinglog", "tempSerialPort : " + tempSerialPort + "\n");
        GlobalMemberValues.logWrite("paxcommsettinglog", "tempBaudRate : " + tempBaudRate + "\n");
        GlobalMemberValues.logWrite("paxcommsettinglog", "tempNetworkPort : " + tempNetworkPort + "\n");

        //create commsetting object
        String iniFile = mContext.getFilesDir().getAbsolutePath() + "/" + SettingINI.FILENAME;
        CommSetting commset = SettingINI.getCommSettingFromFile(iniFile);

        //initialization value  for comsetting's attribute
        //commset.setType("TCP");
        commset.setType(commtype);

        commset.setTimeOut("60000");

        //commset.setSerialPort("COM1");
        //commset.setSerialPort("");
        commset.setSerialPort(tempSerialPort);

        //commset.setBaudRate("9600");
        //commset.setBaudRate("");
        commset.setBaudRate(tempBaudRate);

        //commset.setDestIP("192.168.0.59");                        // 192.168.0.59
        commset.setDestIP(tempNetworkIP);                           // 192.168.0.59

        //commset.setDestPort("10009");                             // 10009
        commset.setDestPort(tempNetworkPort);                       // 10009

        commset.setMacAddr("");

        commset.setEnableProxy(tempProxyValue);

        //01022024 enable PAX logging and saving log file
        LogSetting.setLogMode(true);  // Open or close log
        LogSetting.setLevel(LogSetting.LOGLEVEL.DEBUG);  // Set log level
        LogSetting.setLogFileName("POSLinkLog");  // Set the file name of output log
        LogSetting.setOutputPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/POSLOG");  // Set path for output log
        LogSetting.setLogDays("30");  // Keep log for 30 days

        /**
         //initialization value  for comsetting's attribute
         commset.setType(mEdtCommType.getSelectedItem().toString());
         commset.setTimeOut(mEdtTimeOut.getText().toString());
         commset.setSerialPort(mEdtSerialPort.getText().toString());
         commset.setBaudRate(mEdtBaudRate.getSelectedItem().toString());
         commset.setDestIP(mEdtDestIP.getText().toString());
         commset.setDestPort(mEdtDestPort.getText().toString());
         commset.setMacAddr(mEdtMacAddr.getText().toString());
         **/

        GlobalMemberValues.logWrite(TAG + "JJJ", "coms.CommType=" + commset.getType() + "; coms.TimeOut=" + commset.getTimeOut()
                + "; SerialPort=" + commset.getSerialPort() + "; coms.BaudRate=" + commset.getBaudRate()
                + "; coms.DestIP=" + commset.getDestIP() + "; coms.DestPort=" + commset.getDestPort()
                + "; coms.MacAddr=" + commset.getMacAddr() + "; coms.EnableProxy=" + commset.isEnableProxy());

        //POSLinkAndroid.initPOSListener(mContext, commset);
        SettingINI.saveCommSettingToFile(iniFile, commset);
    }

    public void paymentProcess(String paramProcessType) {
        // PAX 2018 버전에 추가된 사항
        initPOSLink();
        setCommSetting();

        switch (paramProcessType) {
            case "SALE": {
                // 카드 status 관련
                // SALE 일 경우 카드결제 처리전에 카드결제 시도했다는 정보를 저장한다.
                if (GlobalMemberValues.isCardStatusSaveUse()) {
                    saveCardTryData(mGetAmountToPay, mGetProcessType);
                }

                processPaxPayment();

                break;
            }

            case "VOID": {
                processPaxPayment();

                break;
            }

            case "RETURN": {
                processPaxPayment();

                break;
            }

            case "ADJUST": {
                processPaxPayment();

                break;
            }

            /**
             case R.id.payment_request_set_extdata:
             Intent intent1 = new Intent(this, PaymentExtDataActivity.class);
             Bundle bundle1 = new Bundle();
             bundle1.putString("Payment_ExtData", mRequest_ExtData.getText().toString());
             intent1.putExtras(bundle1);
             startActivityForResult(intent1, Constant.PAYMENT_EXTDATA_RESULT);
             break;
             case R.id.payment_show_receipt:
             PaymentResponse response = poslink.PaymentResponse;
             if (response == null || (!response.ResultCode.contains("000000") && !response.ResultCode.contains("000100"))) {
             Toast.makeText(PaxPayment.this, "No Receipt!!", Toast.LENGTH_LONG).show();
             } else if (response.ResultCode.contains("000100")) {
             Toast.makeText(PaxPayment.this, "Don't Print Receipt For Decline", Toast.LENGTH_LONG).show();
             } else {
             String receipt = generateReceipt();
             Intent intent = new Intent(this, PaymentReceiptActivity.class);
             Bundle bundle = new Bundle();
             bundle.putString("Payment_Receipt", receipt);
             intent.putExtras(bundle);
             startActivity(intent);
             }
             break;
             case R.id.payment_request_filebrowser:
             String Dir = mSaveSigfilepath.getText().toString();
             File f = new File(Dir);
             if (!f.exists()) {
             Dir = Environment.getExternalStorageDirectory().toString();
             } else {
             if (f.isFile())
             Dir = Dir.substring(0, Dir.lastIndexOf("/"));
             }
             Intent intent2 = new Intent(this, DirectoryLogActivity.class);
             Bundle bundle2 = new Bundle();
             bundle2.putString("FilePath", Dir);
             intent2.putExtras(bundle2);
             startActivityForResult(intent2, Constant.MANAGE_SAVE_IMAGE_RESULT);
             break;
             **/
        }
    }

    // PAX 2018 버전에 추가된 사항
    private void initPOSLink() {
        JJJ_PaxPay_POSLinkCreatorWrapper.createSync(getApplicationContext(), new JJJ_PaxPay_AppThreadPool.FinishInMainThreadCallback<PosLink>() {
            @Override
            public void onFinish(PosLink result) {
                poslink = result;
            }
        });
    }

    public void processPaxPayment() {
        initPaymentResponse();

        // as processTrans is block,so we should put it in an async task
        mTask = new AsyncPosLinkTask(this);
        dataFragment.setData(mTask);
        mTask.execute();

        GlobalMemberValues.logWrite(TAG, "mTask.execute 지나옴.." + "\n");
    }

    public void retry_processPaxPayment() {
        initPaymentResponse();

        mForce = "Y";
        // as processTrans is block,so we should put it in an async task
        mTask = new AsyncPosLinkTask(this);
        dataFragment.setData(mTask);
        mTask.execute();

        GlobalMemberValues.logWrite(TAG, "mTask.execute 지나옴.." + "\n");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if(mTask!= null)
            mTask.setActivity(null);
        super.onSaveInstanceState(outState);
    }

    /**
     * get component from XML and set default value
     */

    /**
     * get component from XML and set default value
     */

    private int lastReportedStatus = -1;
    final Runnable MyRunnable = new Runnable() {

        public void run() {
            int status;

            try {
                Thread.sleep(0);

                status = poslink.GetReportedStatus();
                if (status != lastReportedStatus) {
                    switch (status) {
                        case 0:
                            Message msg0 = new Message();
                            msg0.what = Constant.TRANSACTION_STATUS;
                            msg0.obj = "Ready for CARD INPUT";
                            mHandler.sendMessage(msg0);
                            break;
                        case 1:
                            Message msg1 = new Message();
                            msg1.what = Constant.TRANSACTION_STATUS;
                            msg1.obj = "Ready for PIN ENTRY";
                            mHandler.sendMessage(msg1);
                            break;
                        case 2:
                            Message msg2 = new Message();
                            msg2.what = Constant.TRANSACTION_STATUS;
                            msg2.obj = "Ready for SIGNATURE";
                            mHandler.sendMessage(msg2);
                            break;
                        case 3:
                            Message msg3 = new Message();
                            msg3.what = Constant.TRANSACTION_STATUS;
                            msg3.obj = "Ready for ONLINE PROCESSING";
                            mHandler.sendMessage(msg3);
                            break;
                        case 4:
                            Message msg4 = new Message();
                            msg4.what = Constant.TRANSACTION_STATUS;
                            msg4.obj = "Ready for NEW CARD INPUT";
                            mHandler.sendMessage(msg4);
                            break;
                        default:
                            break;
                    }

                    lastReportedStatus = status;

                    GlobalMemberValues.logWrite("lastReportedStatusLog", "lastReportedStatus : " + lastReportedStatus + "\n");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     */
    private void process() {
        PaymentRequest payrequest = new PaymentRequest();
        setPaymentRequest(payrequest);
        //GlobalMemberValues.logWrite(TAG, "payrequest.TenderType" + payrequest.TenderType + ", " + "payrequest.TransType" + payrequest.TransType);

        // set the folder to save the "comsetting.ini" file
        poslink.appDataFolder = this.getApplicationContext().getFilesDir().getAbsolutePath();
        poslink.PaymentRequest = payrequest;

        poslink.SetCommSetting(SettingINI.getCommSettingFromFile(poslink.appDataFolder + "/" + SettingINI.FILENAME));
        mHandler.postDelayed(MyRunnable, 100);

        GlobalMemberValues.logWrite(TAG, "process 진행중 1" + "\n");

        try {
            Thread.sleep(100);
            // ProcessTrans is Blocking call, will return when the transaction is
            // complete.
            ptr = poslink.ProcessTrans();
            mHandler.removeCallbacks(MyRunnable);

            GlobalMemberValues.logWrite(TAG, "process 진행중 2" + "\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * set payment request value where the value from input text.
     */
    private void setPaymentRequest(PaymentRequest payrequest) {
        String tempAddr = "";
        String tempZip = "";
        if (!GlobalMemberValues.isStrEmpty(PaymentCreditCard.mKeyinCardNumber)) {
            mCardExtData = "<CVV>" + PaymentCreditCard.mKeyinCVVNumber + "</CVV><ExpDate>" + PaymentCreditCard.mKeyinExpDate + "</ExpDate><Account>" + PaymentCreditCard.mKeyinCardNumber + "</Account><CPMode>1</CPMode>";
            tempAddr = "Wanhaye";
            tempZip = "50901";
        } else {
            mCardExtData = "";
            PaymentCreditCard.setInitKeyInData();
        }

        if (!GlobalMemberValues.isStrEmpty(mForce)){
            if (mForce.equals("Y")){
                mCardExtData = mCardExtData + "<Force>T</Force>";
            }
        }

        //payrequest.TenderType = payrequest.ParseTenderType(getStringFromEdit(mRequest_Tender));
        //payrequest.TransType = payrequest.ParseTransType(getStringFromEdit(mRequest_Trans));
        mLast_Request_Tender = mGetCardTenderType;
        payrequest.TenderType = payrequest.ParseTenderType(mLast_Request_Tender);
        mLast_Request_Trans = mGetProcessType;
        payrequest.TransType = payrequest.ParseTransType(mLast_Request_Trans);
        payrequest.Amount = mGetAmountToPay;
        /* 20120203 add CashBackAmt start */
        payrequest.CashBackAmt = "";
        /* 20120203 add CashBackAmt end */
        payrequest.ClerkID = "";
        payrequest.TipAmt = "";
        payrequest.TaxAmt = "";
        payrequest.FuelAmt = "";
        payrequest.Street = tempAddr;
        payrequest.Street2 = "";
        payrequest.Zip = tempZip;
        payrequest.SurchargeAmt = "";
        payrequest.PONum = "";
        payrequest.OrigRefNum = mGetRefNum;
        payrequest.InvNum = "";
        payrequest.ECRRefNum = mGetECRRefNum;             // 반드시 값이 있어야 함...
        payrequest.ECRTransID = "";
        payrequest.AuthCode = "";
        payrequest.ExtData = mCardExtData;

//        GlobalMemberValues.logWrite(TAG + "2", "PaymentCreditCard.mKeyinCardNumber : " + PaymentCreditCard.mKeyinCardNumber + "\n");
//        GlobalMemberValues.logWrite(TAG + "2", "PaymentCreditCard.mKeyinExpDate : " + PaymentCreditCard.mKeyinExpDate + "\n");
//        GlobalMemberValues.logWrite(TAG + "2", "PaymentCreditCard.mKeyinCVVNumber : " + PaymentCreditCard.mKeyinCVVNumber + "\n");
//        GlobalMemberValues.logWrite(TAG + "2", "PaymentCreditCard.mKeyinAddr : " + PaymentCreditCard.mKeyinAddr + "\n");
//        GlobalMemberValues.logWrite(TAG + "2", "PaymentCreditCard.mKeyinZip : " + PaymentCreditCard.mKeyinZip + "\n");
//        GlobalMemberValues.logWrite(TAG + "2", "mCardExtData : " + mCardExtData + "\n");
    }

    /**
     * set response value in textview.
     *
     * @param response ""
     */
    private void setPaymentResponse(PaymentResponse response) {
        /**
         mResponse_ResultCode.setText(response.ResultCode);
         mResponse_ResultTxt.setText(response.ResultTxt);
         mResponse_AuthCode.setText(response.AuthCode);
         mResponse_ApprovedAmount.setText(response.ApprovedAmount);
         mResponse_AvsResponse.setText(response.AvsResponse);
         mResponse_BogusAccountNum.setText(response.BogusAccountNum);
         mResponse_CardType.setText(response.CardType);
         mResponse_CvResponse.setText(response.CvResponse);
         mResponse_HostCode.setText(response.HostCode);
         mResponse_HostResponse.setText(response.HostResponse);
         mResponse_Message.setText(response.Message);
         mResponse_RefNum.setText(response.RefNum);
         mResponse_RemainingBalance.setText(response.RemainingBalance);
         mResponse_ExtraBalance.setText(response.ExtraBalance);
         mResponse_RequestedAmount.setText(response.RequestedAmount);
         mResponse_Timestamp.setText(response.Timestamp);
         mResponse_SigFileName.setText(response.SigFileName);
         mResponse_SigData.setText(response.SignData);
         mResponse_ExtDAta.setText(response.ExtData);
         **/

        PaymentCreditCard.pax_processingResult = response.ResultCode;
        PaymentCreditCard.pax_processingResultTxt = response.ResultTxt;
        PaymentCreditCard.pax_exeData = response.ExtData;

        GlobalMemberValues.logWrite("processingerrorlog", "response.ResultCode : " + response.ResultCode + "\n");
        GlobalMemberValues.logWrite("processingerrorlog", "response.ResultTxt : " + response.ResultTxt + "\n");


        // 카드 status 관련 --------------------------------------------------------------------------------
        // response 된 카드결제 결과값을 해당 주문에 저장한다.
        if (GlobalMemberValues.isCardStatusSaveUse()) {
            if (mGetProcessType.equals("SALE")) {
                String tempResultCode = "";
                if (response.ResultCode.equals("000000")) {
                    tempResultCode = "00";
                } else {
                    tempResultCode = "99";
                }

                String tempCardStatusIdx = MssqlDatabase.getResultSetValueToString(
                        " select top 1 idx from card_processing_data " +
                                " where stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                " order by idx desc "
                );
                String strQuery = " update card_processing_data set " +
                        " resultcode = '" + GlobalMemberValues.getDBTextAfterChecked(tempResultCode,0) + "', " +
                        " resultmsg = '" + GlobalMemberValues.getDBTextAfterChecked(response.ResultTxt,0) + "', " +
                        " tid = '" + GlobalMemberValues.getDBTextAfterChecked(response.AuthCode,0) + "', " +
                        " refnum = '" + GlobalMemberValues.getDBTextAfterChecked(response.RefNum,0) + "', " +
                        " cardcom = '" + GlobalMemberValues.getDBTextAfterChecked(mGetCardComp,0) + "', " +
                        " holdername = '" + GlobalMemberValues.getDBTextAfterChecked("",0) + "' " +
                        " where idx = '" + tempCardStatusIdx + "' ";

                Vector<String> vec = new Vector<String >();
                vec.add(strQuery);

                String returnCode = "";
                returnCode = MssqlDatabase.executeTransaction(vec);

                if (returnCode.equals("N")) {
                } else {
                }
            }
        }
        // ------------------------------------------------------------------------------------------------

        if (response.ResultCode.equals("100003")) {
            //GlobalMemberValues.displayDialog(JJJ_SignPad.mContext, "Warning", "Remove Card Please...", "Close");
            // Remove Card 인텐트 오픈
            Intent removeCardIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_RemoveCard.class);
            mActivity.startActivity(removeCardIntent);
            //mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);

            if (JJJ_SignPad.proDial != null) {
                JJJ_SignPad.proDial.cancel();
                JJJ_SignPad.proDial.dismiss();
            }

            return;
        }

        switch (mGetProcessType) {
            case "SALE" : {
                // 07.13.2022 추가 =====================================================================================
                // Gift Card 발란스 체크 기능 ============================================================================
                // 기프트카드일 경우에는
                // 결제후에 승인된 금액(getApprovedAmount) 이 결제해야할 금액(getRequestedAmount) 보다 적을 경우
                // 카드결제가 완료되지 않도록 한다.
                double getRemainingBalance = 0;
                double getApprovedAmount = 0;
                double getRequestedAmount = 0;

//                getRemainingBalance = 200;
//                getApprovedAmount = 500;
//                getRemainingBalance = GlobalMemberValues.getIntAtString(response.RemainingBalance);
                getApprovedAmount = GlobalMemberValues.getIntAtString(response.ApprovedAmount);

                // 테스트용, 테스트완료후 아래 내용 주석처리할 것
                //getApprovedAmount = 5;

//                double getExtraBalance = GlobalMemberValues.getIntAtString(response.ExtraBalance);
                getRequestedAmount = GlobalMemberValues.getIntAtString(response.RequestedAmount);

                getRemainingBalance = getRequestedAmount - getApprovedAmount;

//                String tempStr = "* Remaining balance : " + getRemainingBalance + "\n" +
////                        "* Extra balance : " + getExtraBalance + "\n\n" +
//                        "* Requested amount : " + getRequestedAmount + "\n" +
//                        "* Approved amount : " + getApprovedAmount + "\n\n" +
//                        "* Total price : " + mGetAmountToPay + "\n";
//                GlobalMemberValues.displayDialog(PaymentCreditCard.context, "", tempStr, "Close");


                getRemainingBalance = getRemainingBalance  * 0.01;
                getApprovedAmount = getApprovedAmount * 0.01;
                getRequestedAmount = getRequestedAmount * 0.01;

                if (getApprovedAmount != getRequestedAmount && getApprovedAmount < getRequestedAmount) {
//                    String tempBalMsg = "Partial payment was made because the balance on your gift card was insufficient" +
//                            "\nPlease pay extra (Paid : $" + GlobalMemberValues.getCommaStringForDouble(getApprovedAmount + "") +
//                            ", Remaining Balance : $" + GlobalMemberValues.getCommaStringForDouble(getRemainingBalance + "") + ")";
                    String tempBalMsg = "APPROVED : $" + GlobalMemberValues.getCommaStringForDouble(getApprovedAmount + "") + "\n" +
                            "REMAINING BALAMCE : $" + GlobalMemberValues.getCommaStringForDouble(getRemainingBalance + "") + "\n\n" +
                            "PLEASE PAY REMAINING BALANCE";
//                    GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Balance Check", tempBalMsg, "Close");

                    GlobalMemberValues.mNotPayYNOnCard = true;
                    GlobalMemberValues.mNotPayYNOnCardMsg = tempBalMsg;

                    PaymentCreditCard.paymentCreditCardProcessingAmountTextView.setText(getApprovedAmount + "");
                }
                // =====================================================================================================

                xmlStringParsing(response.ResultCode, response.AuthCode, response.CardType, response.BogusAccountNum,
                        response.RefNum, response.ExtData, response.ResultTxt);
                break;
            }
            case "VOID" : {
                if (GlobalMemberValues.isStrEmpty(mGetVoidTypeReturnYN)) {
                    mGetVoidTypeReturnYN = "N";
                }

                if (GlobalMemberValues.isStrEmpty(mGetGroupVoidYN)) {
                    mGetGroupVoidYN = "N";
                }

                if (GlobalMemberValues.isStrEmpty(mGetPaymentPaidYN)) {
                    mGetPaymentPaidYN = "N";
                }

                if (mGetVoidTypeReturnYN == "Y" || mGetVoidTypeReturnYN.equals("Y")) {
                    voidProcessForReturn(response.ResultCode, mGetSalonSalesCardIdx, mGetPriceAmountForVoid, mGetSelectedCardTagName);
                } else {
                    if (mGetGroupVoidYN.equals("Y") || mGetGroupVoidYN == "Y") {
                        // 세일즈 전체에 대한 void 처리일 때
                        groupVoidProcess(response.ResultCode, mGetGroupVoidCount);
                    } else {
                        // 개별 카드에 대한 void 처리일 때
                        voidProcess(response.ResultCode, mGetSalonSalesCardIdx, mGetPriceAmountForVoid, mGetSelectedCardTagName);
                    }
                }

                break;
            }
            case "RETURN" : {
                xmlStringParsingForReturn(response.ResultCode, response.AuthCode, response.CardType, response.BogusAccountNum,
                        response.RefNum, response.ExtData);
                break;
            }
            case "ADJUST": {
                tipAdjust(response.ResultCode, mGetAmountToPay);
                break;
            }
        }
    }

    public void tipAdjust(String paramReturnCode, String paramTipPriceAmount) {
        if (paramReturnCode.equals("000000")) {
            paramReturnCode = "00";
        }
        GlobalMemberValues.logWrite("tipcardcom", "팁 카드발행사 : " + mGetCardComp + "\n");

        if (mGetFromSignPadYN == "Y" || mGetFromSignPadYN.equals("Y")) {
            double tipPriceAmount = GlobalMemberValues.getDoubleAtString(paramTipPriceAmount) * 0.01;
            JJJ_SignPad.databaseProcessAfterCardProcess(paramReturnCode, Payment.mSalesCode, mGetSalonSalesCardIdx, tipPriceAmount, mGetCardComp);
        } else {
            if (mGetAdjustedynexe == "" || mGetAdjustedynexe.equals("N")) {
                SaleHistoryTipAdjustment.creditCardReturnValue = paramReturnCode;
                double tipPriceAmount = GlobalMemberValues.getDoubleAtString(paramTipPriceAmount) * 0.01;
                SaleHistoryTipAdjustment.databaseProcessAfterCardProcess(tipPriceAmount, mGetCardComp);
            } else {
                GlobalMemberValues.logWrite("tipadjustedynlog3", "paramReturnCode : " + paramReturnCode + "\n");
                BatchSummary.mTipAdjusctResulf = paramReturnCode;
                BatchSummary.tipDbExe(mGettiponetimeyn);
            }
        }
    }

    public void voidProcess(String paramReturnCode, String paramSalonSalesCardIdx,
                            String paramPriceAmountForVoid, String paramTagValue) {
        if (paramReturnCode.equals("000000")) {
            paramReturnCode = "00";
        }
        PaymentCreditCard.pax_processingResult = paramReturnCode;
        if (PaymentCreditCard.mCardPaymentJsonArray != null) {
            PaymentCreditCard.mCardPaymentJsonArray[GlobalMemberValues.getIntAtString(paramSalonSalesCardIdx)] = null;
        }
        // 9999 수정
//        // jihun 0709
//        if (PaymentCreditCard.mCardPaymentJsonArrayList != null){
//            for (int z = 0; z < PaymentCreditCard.mCardPaymentJsonArrayList.size(); z++){
//                JSONObject tempJson;
//                tempJson = PaymentCreditCard.mCardPaymentJsonArrayList.get(z);
//                try {
//                    if (tempJson.getString("cardsalonsalescardidx") == paramSalonSalesCardIdx){
//                        PaymentCreditCard.mCardPaymentJsonArrayList.remove(z);
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        //
        if (GlobalMemberValues.mOnVoidForBillPay) {
            GlobalMemberValues.mOnVoidForBillPay = false;
            GlobalMemberValues.displayDialog(BillSplitMerge.mContext, "Void Bill", "Successfully voided", "Close");
            BillSplitMerge.setVoidCardOnBill(paramReturnCode, paramSalonSalesCardIdx, paramPriceAmountForVoid);
        } else {
            // 06.07.2022
            if (GlobalMemberValues.mOnVoidForPartial) {
                GlobalMemberValues.mOnVoidForPartial = false;
                VoidCardList.setVoidCardOnPartial(paramReturnCode, paramSalonSalesCardIdx, paramPriceAmountForVoid);
            } else {
                PaymentCreditCard.setVoidCard(paramReturnCode, paramSalonSalesCardIdx, paramPriceAmountForVoid);
            }

        }

    }

    public void voidProcessForReturn(String paramReturnCode, String paramSalonSalesCardIdx,
                                     String paramPriceAmountForVoid, String paramTagValue) {
        if (paramReturnCode.equals("000000")) {
            paramReturnCode = "00";
        }
        SaleHistoryReturnCreditCard.pax_processingResult = paramReturnCode;

        SaleHistoryReturnCreditCard.setVoidCard(paramReturnCode, paramSalonSalesCardIdx, paramPriceAmountForVoid, paramTagValue);
    }

    public void groupVoidProcess(String paramReturnCode, String paramGroupVoidCount) {
        if (paramReturnCode.equals("000000")) {
            paramReturnCode = "00";
        }

        if (mGetPaymentPaidYN == "Y" || mGetPaymentPaidYN.equals("Y")) {
            Payment.setGroupVoid(paramGroupVoidCount);
        } else {
            SaleHistory.setGroupVoid(paramGroupVoidCount);
        }
    }

    public void xmlStringParsing(String paramReturnCode, String paramAuthcode, String paramCardComp,
                                 String paramLastFourDigits, String paramRefNum, String paramXmlString, String paramResultTxt) {
        //String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<order><item>Mouse</item></order>";
        String xml = paramXmlString;
        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<paxresult>" + xml + "</paxresult>";

        returncodefromdevice = paramReturnCode;
        if (returncodefromdevice.equals("000000")) {
            returncodefromdevice = "00";
        }
        authnumber = paramAuthcode;

        lastfourdigits = paramLastFourDigits;
        referencenumber = paramRefNum;
        emvaid = "";
        emvtsi = "";
        emvtvr = "";

        plnameoncard = "";

        String tempCardCom = "";
        if (paramCardComp.equals("VISA")) {
            tempCardCom = "V";
        }
        if (paramCardComp.equals("AMEX")) {
            tempCardCom = "A";
        }
        if (paramCardComp.equals("MASTERCARD") || paramCardComp.equals("MC")) {
            tempCardCom = "M";
        }
        if (paramCardComp.equals("DISCOVER") || paramCardComp.equals("DISC")) {
            tempCardCom = "D";
        }
        cardcompay = tempCardCom;

        NodeList items = null;
        Node item = null;
        Node text = null;

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            Element paxresult = doc.getDocumentElement();

            if (paxresult != null) {
                // TVR
                items = paxresult.getElementsByTagName("TVR");
                item = items.item(0);
                text = item.getFirstChild();
                emvtvr = text.getNodeValue();

                // AID
                items = paxresult.getElementsByTagName("AID");
                item = items.item(0);
                text = item.getFirstChild();
                emvaid = text.getNodeValue();

                // AID
                items = paxresult.getElementsByTagName("TSI");
                item = items.item(0);
                text = item.getFirstChild();
                emvtsi = text.getNodeValue();

                // add up jihun PLNameOnCard
                items = paxresult.getElementsByTagName("PLNameOnCard");
                item = items.item(0);
                text = item.getFirstChild();
                plnameoncard = text.getNodeValue();
                plnameoncard = plnameoncard.replaceAll(" ", "");
                plnameoncard = plnameoncard.replaceAll("/", " ");
                plnameoncard = plnameoncard.replaceAll("'", " ");

                // add up jihun 210120
                items = paxresult.getElementsByTagName("AmountDue");
                item = items.item(0);
                text = item.getFirstChild();
                amountDue = text.getNodeValue();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("PaymentXmlResult", "returncodefromdevice : " + returncodefromdevice + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "authnumber : " + authnumber + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "cardcompay : " + cardcompay + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "lastfourdigits : " + lastfourdigits + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "referencenumber : " + referencenumber + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvaid : " + emvaid + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvtsi : " + emvtsi + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvtvr : " + emvtvr + "\n");

        GlobalMemberValues.logWrite("PaymentXmlResult", "plnameoncard : " + plnameoncard + "\n");

        GlobalMemberValues.logWrite("PaymentXmlResult", "amountDue : " + amountDue + "\n");

        xmlMade = null;
        // 카드 결제처리
        xmlMade = new JSONObject();
        try {
            xmlMade.put("returncodefromdevice", returncodefromdevice);
            xmlMade.put("authnumber", authnumber);
            xmlMade.put("cardcompay", cardcompay);
            xmlMade.put("lastfourdigits", lastfourdigits);
            xmlMade.put("referencenumber", referencenumber);
            xmlMade.put("emvaid", emvaid);
            xmlMade.put("emvtsi", emvtsi);
            xmlMade.put("emvtvr", emvtvr);
            xmlMade.put("plnameoncard", plnameoncard);
            xmlMade.put("amountdue", amountDue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("PaymentXmlResult", "xml : " + xmlMade.toString() + "\n");

        PaymentCreditCard.creditCardReturnValueJson = xmlMade;

        if (returncodefromdevice.equals("00") || returncodefromdevice == "00") {
            try {
                PaymentCreditCard.setAddSalonSalesCardList();
            } catch (JSONException e) {
                GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Error Message", e.toString(), "Close");
                e.printStackTrace();
            }
        } else {
            String btn_text1 = "<h1>" + "FORCE DUPLICATE PROCESS\n" + "</h1>" +
                    "<font size='15'>" + "2nd Charge will occur to customer" + "</font>";
            String btn_text2 = "<h1>" + "DO NOT FORCE DUPLICATE\n" + "</h1>" +
                    "<font size='15'>" + "1st Charge will link to this order" + "</font>";

            Html.fromHtml(btn_text1);
            if (paramResultTxt.equals("DUP TRANSACTION")){
                showAlertDialogButtonClicked(PaymentCreditCard.context,
                        "Duplicate Transaction Detected",
                        Html.fromHtml(btn_text1),
                        Html.fromHtml(btn_text2),
                        "Cancel");
            } else {
                GlobalMemberValues.displayDialog(PaymentCreditCard.context, "Waraning", paramResultTxt, "Close");
            }
        }
    }

    public void xmlStringParsingForReturn(String paramReturnCode, String paramAuthcode, String paramCardComp,
                                          String paramLastFourDigits, String paramRefNum, String paramXmlString) {
        //String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<order><item>Mouse</item></order>";
        String xml = paramXmlString;
        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<paxresult>" + xml + "</paxresult>";

        returncodefromdevice = paramReturnCode;
        if (returncodefromdevice.equals("000000")) {
            returncodefromdevice = "00";
        }
        authnumber = paramAuthcode;

        lastfourdigits = paramLastFourDigits;
        referencenumber = paramRefNum;
        emvaid = "";
        emvtsi = "";
        emvtvr = "";

        String tempCardCom = "";
        if (paramCardComp.equals("VISA")) {
            tempCardCom = "V";
        }
        if (paramCardComp.equals("AMEX")) {
            tempCardCom = "A";
        }
        if (paramCardComp.equals("MASTERCARD") || paramCardComp.equals("MC")) {
            tempCardCom = "M";
        }
        if (paramCardComp.equals("DISCOVER") || paramCardComp.equals("DISC")) {
            tempCardCom = "D";
        }
        cardcompay = tempCardCom;

        NodeList items = null;
        Node item = null;
        Node text = null;

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            Element paxresult = doc.getDocumentElement();

            // TVR
            items = paxresult.getElementsByTagName("TVR");
            item = items.item(0);
            text = item.getFirstChild();
            emvtvr = text.getNodeValue();

            // AID
            items = paxresult.getElementsByTagName("AID");
            item = items.item(0);
            text = item.getFirstChild();
            emvaid = text.getNodeValue();

            // AID
            items = paxresult.getElementsByTagName("TSI");
            item = items.item(0);
            text = item.getFirstChild();
            emvtsi = text.getNodeValue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("PaymentXmlResult", "returncodefromdevice : " + returncodefromdevice + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "authnumber : " + authnumber + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "cardcompay : " + cardcompay + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "lastfourdigits : " + lastfourdigits + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "referencenumber : " + referencenumber + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvaid : " + emvaid + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvtsi : " + emvtsi + "\n");
        GlobalMemberValues.logWrite("PaymentXmlResult", "emvtvr : " + emvtvr + "\n");

        xmlMade = null;
        // 카드 결제처리
        xmlMade = new JSONObject();
        try {
            xmlMade.put("returncodefromdevice", returncodefromdevice);
            xmlMade.put("authnumber", authnumber);
            xmlMade.put("cardcompay", cardcompay);
            xmlMade.put("lastfourdigits", lastfourdigits);
            xmlMade.put("referencenumber", referencenumber);
            xmlMade.put("emvaid", emvaid);
            xmlMade.put("emvtsi", emvtsi);
            xmlMade.put("emvtvr", emvtvr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalMemberValues.logWrite("PaymentXmlResult", "xml : " + xmlMade.toString() + "\n");

        SaleHistoryReturnCreditCard.creditCardReturnValueJson = xmlMade;

        if (returncodefromdevice.equals("00") || returncodefromdevice == "00") {
            try {
                SaleHistoryReturnCreditCard.setAddSalonSalesCardList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * clear response value of textview.
     */
    private void initPaymentResponse() {
        PaymentCreditCard.creditCardReturnValueJson = null;
        PaymentCreditCard.pax_processingResult = "";
        PaymentCreditCard.pax_processingResultTxt = "";
        PaymentCreditCard.pax_exeData = "";

        xmlMade = null;

        returncodefromdevice = "";
        authnumber = "";
        cardcompay = "";
        lastfourdigits = "";
        referencenumber = "";
        emvaid = "";
        emvtsi = "";
        emvtvr = "";

        mForce = "";

        /**
         mResponse_ResultCode.setText("");
         mResponse_ResultTxt.setText("");
         mResponse_AuthCode.setText("");
         mResponse_ApprovedAmount.setText("");
         mResponse_AvsResponse.setText("");
         mResponse_BogusAccountNum.setText("");
         mResponse_CardType.setText("");
         mResponse_CvResponse.setText("");
         mResponse_HostCode.setText("");
         mResponse_HostResponse.setText("");
         mResponse_Message.setText("");
         mResponse_RefNum.setText("");
         mResponse_RemainingBalance.setText("");
         mResponse_RequestedAmount.setText("");
         mResponse_Timestamp.setText("");
         mResponse_SigData.setText("");
         mResponse_SigFileName.setText("");
         mResponse_ExtDAta.setText("");
         **/
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.PAYMENT_EXTDATA_RESULT:
                switch (resultCode) {
                    case RESULT_OK:
                        mRequest_ExtData.setText(data.getStringExtra("Payment_ExtData"));
                        break;
                    default:
                        break;
                }
                break;
            case Constant.MANAGE_SAVE_IMAGE_RESULT:
                switch (resultCode) {
                    case RESULT_OK:
                        mSaveSigfilepath.setText(data.getStringExtra("FilePath"));
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        // processTransactions
        process();
        GlobalMemberValues.logWrite(TAG, "process(); 지나옴.." + "\n");
    }

    /* 20120209 QC Bug No30 CombBox Start */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    /* 20120209 QC Bug No30 CombBox End */

    @Override
    public void onSetListener(ProgressDialog dialog, boolean cancelable, boolean enDismiss)
    {
        if(cancelable) {
            dialog.setButton("Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    poslink.CancelTrans();
                    finish();
                }
            });
        }
        if(enDismiss) {
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    poslink.CancelTrans();
                    fileList();
                }
            });
        }
    }

    @Override
    public void onTaskCompleted()
    {
        dataFragment.removeData();
        // There will be 2 separate results that you must handle. First is the
        // ProcessTransResult, this will give you the result of the
        // request to call poslink. PaymentResponse should only be checked if
        // ProcessTransResultCode.Code == OK.
        if (ptr.Code == ProcessTransResult.ProcessTransResultCode.OK) {
            // PaymentResponse is the result of the batch transaction to the
            // server.
            PaymentResponse response = poslink.PaymentResponse;
            Message msg = new Message();
            msg.what = Constant.TRANSACTION_SUCCESSED;
            msg.obj = response;
            mHandler.sendMessage(msg);
            GlobalMemberValues.logWrite(TAG, "Transaction succeed!");

        } else if (ptr.Code == ProcessTransResult.ProcessTransResultCode.TimeOut) {

            Message msg = new Message();
            msg.what = Constant.TRANSACTION_TIMEOOUT;
            Bundle b = new Bundle();
            b.putString(Constant.DIALOG_TITLE, String.valueOf(ptr.Code));
            b.putString(Constant.DIALOG_MESSAGE, ptr.Msg);
            msg.setData(b);
            mHandler.sendMessage(msg);

            Log.e(TAG, "Transaction TimeOut! " + String.valueOf(ptr.Code));
            Log.e(TAG, "Transaction TimeOut! " + ptr.Msg);

        } else {
            Message msg = new Message();
            msg.what = Constant.TRANSACTION_FAILURE;
            Bundle b = new Bundle();
            b.putString(Constant.DIALOG_TITLE, String.valueOf(ptr.Code));
            b.putString(Constant.DIALOG_MESSAGE, ptr.Msg);
            msg.setData(b);
            mHandler.sendMessage(msg);

            Log.e(TAG, "Transaction Error! " + String.valueOf(ptr.Code));
            Log.e(TAG, "Transaction Error! " + ptr.Msg);
        }

        //01022024
        LogSetting.setLogMode(false);
    }

    @Override
    public DialogFragment createDialog()
    {
        return ProgressDialogFragment.newInstance(getResources().getString(R.string.payment_process_prompt),false, false);
//        return ProgressDialogFragment.newInstance(getResources().getString(R.string.payment_process_prompt),true, false);
    }

    private Bitmap generateBmp(String data){
        String div = "\\^";
        String signature_divide[] = data.split(div);
        String x;
        String y;
        String x_y;

        int magin =10;
        int minx;
        int miny;
        int newWidth;
        int newHeight;

        ArrayList<Integer> xVal =new ArrayList<Integer>(signature_divide.length);
        ArrayList<Integer> yVal =new ArrayList<Integer>(signature_divide.length);
        System.out.println("size ="+signature_divide.length);
        for(int i=0; i<signature_divide.length-1; i++)
        {
            try
            {
                x_y = signature_divide[i];
                int pos = x_y.indexOf(",");
                x = x_y.substring(0, pos);
                y = x_y.substring(pos+1);
                if(Integer.parseInt(y)==65535)
                    continue;
                xVal.add(Integer.parseInt(x));
                yVal.add(Integer.parseInt(y));
                //if(Integer.parseInt(x) <= 480 && Integer.parseInt(y) <= 320)
                //bmp.setPixel(Integer.parseInt(x), Integer.parseInt(y), Color.BLACK);

            }catch(Exception e)
            {
                //e.printStackTrace(); ignore correct point
            }
        }

        Collections.sort(yVal);
        Collections.sort(xVal);

        minx = Integer.parseInt(xVal.get(0).toString());
        miny = Integer.parseInt(yVal.get(0).toString());

        System.out.println("minx="+minx);
        System.out.println("miny="+miny);
        newWidth = Integer.parseInt(xVal.get(xVal.size()-1).toString()) - minx +1 +magin*2;
        newHeight = Integer.parseInt(yVal.get(yVal.size()-1).toString()) - miny +1 + magin*2;

        System.out.println("widht1 = "+newWidth);
        System.out.println("height1 = "+newHeight);

        //BufferedImage bimage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
        Bitmap bmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        //set background white
        for(int i=0;i<newWidth;i++)
            for(int j=0;j<newHeight;j++)
                bmp.setPixel(i, j, Color.WHITE);


        //connect dots
        Point p1 = new Point();
        Point p2 = new Point();
        Paint blackPen = new Paint();
        blackPen.setColor(Color.BLACK);
        blackPen.setStrokeWidth(2);

        Canvas canvas = new Canvas();
        canvas.setBitmap(bmp);
        for(int i=1;i<signature_divide.length-1;i++)
        {
            x_y =  signature_divide[i-1];
            int pos = signature_divide[i-1].indexOf(",");
            y = x_y.substring(pos + 1);
            x = x_y.substring(0, pos);
            if(Integer.parseInt(y) == 65535)
                continue;
            p1.x = Integer.parseInt(x) + magin - minx;
            p1.y = Integer.parseInt(y) + magin - miny;

            x_y =  signature_divide[i];
            pos = signature_divide[i].indexOf(",");
            y = x_y.substring(pos + 1);
            x = x_y.substring(0, pos);
            if(Integer.parseInt(y) == 65535)
                continue;
            p2.x = Integer.parseInt(x)+ magin - minx;
            p2.y = Integer.parseInt(y)+ magin - miny;

            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, blackPen);

        }
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();
        return bmp;
    }

    public int ConvertSigToPic(String sigdata, String type, String outFile) throws IOException
    {
        if(sigdata.length() == 0)
            return -1;
        if(outFile.length() == 0)
            return -2;

        Bitmap bmp = generateBmp(sigdata);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(type.length()==0 || type.toLowerCase().equals("bmp"))
        {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        else if(type.toLowerCase().equals("ico"))
        {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        else if(type.toLowerCase().equals("jpg"))
        {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        else if(type.toLowerCase().equals("png"))
        {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        else
        {
            return -4;   //fail
        }

        try {
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -5;
        }

        return 0 ;  //sucess
    }
    public void showAlertDialogButtonClicked(Context context, String title ,CharSequence btntext1, CharSequence btntext2, CharSequence btntext3) {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_3btn_vertical,null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        Button btn1 = customLayout.findViewById(R.id.custom_row_tx1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMemberValues.isCardStatusSaveUse()) {
                    saveCardTryData(mGetAmountToPay, mGetProcessType);
                }
                retry_processPaxPayment();
                PaymentCreditCard.getmProgress_handler_start.sendEmptyMessage(0);
                dialog.dismiss();
            }
        });
        btn1.setText(btntext1);
        Button btn2 = customLayout.findViewById(R.id.custom_row_tx2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
                dialog.dismiss();
            }
        });
        btn2.setText(btntext2);
        Button btn3 = customLayout.findViewById(R.id.custom_row_tx3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn3.setText(btntext3);

        dialog.show();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mGetAmountToPay.isEmpty()) return;
            double dblAmount = GlobalMemberValues.getDoubleAtString(mGetAmountToPay) * 0.01;
            String insAmount = dblAmount + "";

            String query = " select top 1 idx from card_processing_data ORDER BY idx desc ";
            String return_str = MssqlDatabase.getResultSetValueToString(query);
            if (return_str.isEmpty()) return;
            CreditCardStatusActivity.setForceSale(return_str, insAmount);
        }
    };
}
