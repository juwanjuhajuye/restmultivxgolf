package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class PaymentEmailReceipt extends Activity {
    Activity mActivity;
    Context mContext;

    final String TAG = "PaymentReviewClass";

    // DB 객체 선언
    DatabaseInit dbInit;

    private Button closeBtn;
    private Button paymentReviewEmailButton1,paymentReviewEmailButton2,paymentReviewEmailButton3, paymentReviewEmailButton4;
    private Button paymentReviewEmailButton5,paymentReviewEmailButton6,paymentReviewEmailButton7,paymentReviewEmailButton8;
    private Button paymentReviewConfirmButton;

    private EditText paymentReviewEditTextEmailAddr1, paymentReviewEditTextEmailAddr2;

    private LinearLayout parentLn;

    Intent mIntent;

    // 인텐트로 넘겨받은 값
    JSONObject jsonReceipt = null;

    String customerId = "";
    String customerEmail = "";


    public static String htmlStr = "";

    String mailSendResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        mContext = this;

        int parentLnWidth = 0;
        int parentLnHeight = 0;

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 8;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 7;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 7;
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        if (GlobalMemberValues.isDeviceClover()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            // orientation 에 따라 레이아웃 xml 동적으로 적용.
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_payment_email_receipt2);

                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 10;
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 10;
            } else {
                setContentView(R.layout.activity_payment_email_receipt);
            } // end if
        } else {
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                setContentView(R.layout.activity_payment_email_receipt3);
            } else {
                setContentView(R.layout.activity_payment_email_receipt);
            }

        }
        this.setFinishOnTouchOutside(false);


        parentLn = (LinearLayout)findViewById(R.id.paymnetReviewLinearLayout);

        /**
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn.setLayoutParams(parentLnParams);
        **/

        jsonReceipt = null;

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
            customerId = mIntent.getStringExtra("CustomerEmail");
            customerEmail = mIntent.getStringExtra("CustomerEmailReal");

            GlobalMemberValues.logWrite(TAG, "넘겨받은 customerId : " + customerId + "\n");
            GlobalMemberValues.logWrite(TAG, "customerEmail : " + customerEmail + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 jsonReceipt : " + jsonReceipt + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite(TAG, "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finishPayment();
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        // 고객 이메일 가져오기
        if (GlobalMemberValues.isStrEmpty(customerEmail)) {
            if (!GlobalMemberValues.isStrEmpty(customerId)) {
                customerEmail = dbInit.dbExecuteReadReturnString("select email from member2 where uid = '" + customerId + "' ");
            }
        }

        setContents();
    }

    public void setContents() {
        // 102022 이곳에
        GlobalMemberValues.setGoneForCardCashPayView();

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

        // Payment 초기화
        if (Payment.mActivity != null) {
            Payment.setInit();
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

        // receipt_no (salescode) 를 json 으로부터 가져와서
        // salon_sales 테이블의 receiptJson 에 넘겨받은 json 을 저장한다.
        String updatedResult = setReceiptJsonAtSalonSales();
        GlobalMemberValues.logWrite("LogAboutreceiptJson", "updatedResult : " + updatedResult + "\n");

        // 영수증 html 읽어오기 ---------------------------------------------------------------------------------
        String js_creditcardtendered = null;
        try {
            js_creditcardtendered = jsonReceipt.getString("creditcardtendered");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String htmlUrlAddress = "";
        if (!GlobalMemberValues.isStrEmpty(js_creditcardtendered)
                && GlobalMemberValues.getDoubleAtString(js_creditcardtendered) > 0) {
            htmlUrlAddress = GlobalMemberValues.CLOUD_SERVER_URL + "receipt/posreceipthtml_cashcard.html";
        } else {
            htmlUrlAddress = GlobalMemberValues.CLOUD_SERVER_URL + "receipt/posreceipthtml_cash.html";
        }

        // 웹에서 영수증용 html 읽어오기
        HtmlRead htmlRead = new HtmlRead();
        htmlRead.readHTML(htmlUrlAddress);

        GlobalMemberValues.logWrite(TAG + "1", "html url : " + htmlUrlAddress + "\n");
        GlobalMemberValues.logWrite(TAG + "1", "read html : " + htmlStr + "\n");
        // ----------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.paymnetReviewCloseBtn);
        closeBtn.setOnClickListener(paymentReviewBtnClickListener);

        // Email 버튼 및 리스너 연결
        paymentReviewEmailButton1 = (Button)findViewById(R.id.paymentReviewEmailButton1);
        paymentReviewEmailButton2 = (Button)findViewById(R.id.paymentReviewEmailButton2);
        paymentReviewEmailButton3 = (Button)findViewById(R.id.paymentReviewEmailButton3);
        paymentReviewEmailButton4 = (Button)findViewById(R.id.paymentReviewEmailButton4);
        paymentReviewEmailButton5 = (Button)findViewById(R.id.paymentReviewEmailButton5);
        paymentReviewEmailButton6 = (Button)findViewById(R.id.paymentReviewEmailButton6);
        paymentReviewEmailButton7 = (Button)findViewById(R.id.paymentReviewEmailButton7);
        paymentReviewEmailButton8 = (Button)findViewById(R.id.paymentReviewEmailButton8);

        paymentReviewEmailButton1.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton2.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton3.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton4.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton5.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton6.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton7.setOnClickListener(paymentReviewBtnClickListener);
        paymentReviewEmailButton8.setOnClickListener(paymentReviewBtnClickListener);

        paymentReviewConfirmButton = (Button)findViewById(R.id.paymentReviewConfirmButton);
        paymentReviewConfirmButton.setOnClickListener(paymentReviewBtnClickListener);

        paymentReviewEditTextEmailAddr1 = (EditText)findViewById(R.id.paymentReviewEditTextEmailAddr1);
        paymentReviewEditTextEmailAddr2 = (EditText)findViewById(R.id.paymentReviewEditTextEmailAddr2);

        paymentReviewEditTextEmailAddr1.setOnTouchListener(touchEditText);
        paymentReviewEditTextEmailAddr2.setOnTouchListener(touchEditText);
        /***********************************************************************************************************/

        // 객체 및 색상 초기화
        setInit();

        if (!GlobalMemberValues.isStrEmpty(customerEmail)) {
            String[] customerEmailsplit = customerEmail.split("\\@");
            GlobalMemberValues.logWrite(TAG, "customerEmailsplit.length : " + customerEmailsplit.length + "\n");
            if (customerEmailsplit.length == 2) {
                GlobalMemberValues.logWrite(TAG, "customerEmailsplit[0] : " + customerEmailsplit[0] + "\n");
                GlobalMemberValues.logWrite(TAG, "customerEmailsplit[1] : " + customerEmailsplit[1] + "\n");
                paymentReviewEditTextEmailAddr1.setText(customerEmailsplit[0]);
                paymentReviewEditTextEmailAddr2.setText(customerEmailsplit[1]);
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setEmailAddress2("");
        }


        // Merchant 용 영수증 프린팅 ----------------------------------------------------------------
        if (PaymentCustomerSelectReceipt.tempJsonReceipt != null) {
            GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "Y";
            GlobalMemberValues.printReceiptByJHP(PaymentCustomerSelectReceipt.tempJsonReceipt, mContext, "payment");
        }
        // ----------------------------------------------------------------------------------------
    }

    View.OnTouchListener touchEditText = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.requestFocus();
            return false;
        }
    };

    public String setReceiptJsonAtSalonSales() {
        String returnValue = "N";
        if (jsonReceipt != null) {
            if (jsonReceipt.toString().contains("receiptno")){
                String tempSalesCode = "";
                try {
                    tempSalesCode = jsonReceipt.getString("receiptno");
                } catch (JSONException e) {
                    tempSalesCode = "";
                    e.printStackTrace();
                }

                if (!GlobalMemberValues.isStrEmpty(tempSalesCode)) {
                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String strSql = "update salon_sales set " +
                            " receiptJson = '" + jsonReceipt.toString() + "' " +
                            " where salescode = '" + tempSalesCode + "' ";
                    strUpdateQueryVec.addElement(strSql);
                    for (String tempQuery : strUpdateQueryVec) {
                        GlobalMemberValues.logWrite("SalonSalesTableUpdate", "query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);

                    if (returnResult == "N" || returnResult == "") {
                        returnValue = "N";
                    } else {
                        returnValue = "Y";
                    }
                }
            }
        }

        return returnValue;
    }

    View.OnClickListener paymentReviewBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.paymnetReviewCloseBtn : {
                    finishPayment();

                    break;
                }
                case R.id.paymentReviewEmailButton1 : {
                    String tempEmailAddr2 = paymentReviewEmailButton1.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton2 : {
                    String tempEmailAddr2 = paymentReviewEmailButton2.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton3 : {
                    String tempEmailAddr2 = paymentReviewEmailButton3.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton4 : {
                    String tempEmailAddr2 = paymentReviewEmailButton4.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton5 : {
                    String tempEmailAddr2 = paymentReviewEmailButton5.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton6 : {
                    String tempEmailAddr2 = paymentReviewEmailButton6.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton7 : {
                    String tempEmailAddr2 = paymentReviewEmailButton7.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }
                case R.id.paymentReviewEmailButton8 : {
                    String tempEmailAddr2 = paymentReviewEmailButton8.getTag().toString();
                    setEmailAddress2(tempEmailAddr2);
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr2);
                    break;
                }

                case R.id.paymentReviewConfirmButton : {
                    sendEmail();
                    break;
                }

            }
        }
    };

    private void setEmailAddress2(String paramEmailAddr2) {
        paymentReviewEditTextEmailAddr2.setText(paramEmailAddr2);

        paymentReviewEditTextEmailAddr1.setEnabled(true);
        paymentReviewEditTextEmailAddr2.setEnabled(true);

        if (!GlobalMemberValues.isStrEmpty(paramEmailAddr2)) {
            paymentReviewEditTextEmailAddr1.requestFocus();
            paymentReviewEditTextEmailAddr1.setCursorVisible(true);

            GlobalMemberValues.setKeyPadShow(getApplicationContext(), paymentReviewEditTextEmailAddr1);
        } else {
            //paymentReviewEditTextEmailAddr2.requestFocus();
            paymentReviewEditTextEmailAddr2.setCursorVisible(true);

            GlobalMemberValues.setKeyPadShow(getApplicationContext(), paymentReviewEditTextEmailAddr2);
        }
    }

    private void sendEmail() {
        if (!GlobalMemberValues.isStrEmpty(paymentReviewEditTextEmailAddr1.getText().toString())
                && !GlobalMemberValues.isStrEmpty(paymentReviewEditTextEmailAddr2.getText().toString())) {
            // 인터넷이 연결되어있을 경우...
            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                // 발송용 Gmail 을 등록한 것이 있는지 체크한다.
                String strQuery = "select gmailId, gmailPwd " +
                        " from salon_storestationsettings_system ";
                Cursor settingsSystemCursor = dbInit.dbExecuteRead(strQuery);
                if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
                    String insEmailId = "";
                    String tempEmailId = paymentReviewEditTextEmailAddr1.getText().toString();
                    if (tempEmailId.indexOf("@") > -1) {
                        String[] strSplitStrArr = tempEmailId.split("@");
                        insEmailId = GlobalMemberValues.getReplaceText(strSplitStrArr[0], " ", "");
                    } else {
                        insEmailId = GlobalMemberValues.getReplaceText(tempEmailId, " ", "");
                    }

                    String insEmailCom = "";
                    String tempEmailCom = paymentReviewEditTextEmailAddr2.getText().toString();
                    if (tempEmailCom.indexOf("@") > -1) {
                        String[] strSplitStrArr2 = tempEmailCom.split("@");
                        insEmailCom = GlobalMemberValues.getReplaceText(strSplitStrArr2[1], " ", "");
                    } else {
                        insEmailCom = GlobalMemberValues.getReplaceText(tempEmailCom, " ", "");
                    }

                    String tempToEmailAddress
                            = insEmailId + "@" + insEmailCom;
                    String tempGmailId = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
                    String tempGmailPwd = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
                    if (!GlobalMemberValues.isStrEmpty(tempGmailId)
                            && !GlobalMemberValues.isStrEmpty(tempGmailPwd)) {
                        sendReceiptEmail(tempGmailId, tempGmailPwd, tempToEmailAddress);
                    } else {
                        //alertDialogForFinishPayment("There is no 'Store Gmail Account'\nDo you want finish the payment without sending the receipt?");
                        GlobalMemberValues.displayDialog(mContext, "Warning", "There is no 'Store Gmail Account' " +
                                "\nDo you want finish the payment without sending the receipt?", "Close");
                    }
                } else {
                    //alertDialogForFinishPayment("There is no 'Store Gmail Account'\nDo you want finish the payment without sending the receipt?");
                    GlobalMemberValues.displayDialog(mContext, "Warning", "There is no 'Store Gmail Account'" +
                            "\nDo you want finish the payment without sending the receipt?", "Close");
                }
            } else {
                //alertDialogForFinishPayment("Network is unavailable\nDo you want finish the payment without sending the receipt?");
                GlobalMemberValues.displayDialog(mContext, "Warning", "Network is unavailable" +
                        "\nDo you want finish the payment without sending the receipt?", "Close");
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Enter email address", "Close");
        }
    }

    private void alertDialogForFinishPayment(String paramMsg) {
        new AlertDialog.Builder(mActivity)
                .setTitle("Warning")
                .setMessage(paramMsg)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishPayment();
                    }
                }).show();
    }

    private void sendReceiptEmail(String paramGmailId, String paramGmailPwd, final String paramToEmail) {
        final String tempFromGEmailAddress = paramGmailId + "@gmail.com";
        GlobalMemberValues.GMAILACCOUNT = tempFromGEmailAddress;
        GlobalMemberValues.GMAILID = paramGmailId;
        GlobalMemberValues.GMAILPWD = paramGmailPwd;

        final String tempEmailTitle = "You've received the receipt from " + GlobalMemberValues.SALON_NAME;
        //final String tempEmailContents = "영수증 내용입니다";
        String tempEmailContents = "";
        try {
            tempEmailContents = getEmailContentsInJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!GlobalMemberValues.isStrEmpty(tempEmailContents)) {
            final String finalTempEmailContents = tempEmailContents;
            new Thread() {
                public void run() {
//                    Gmail gmail = new Gmail();
//                    gmail.gmailSend(mContext, GlobalMemberValues.GMAILACCOUNT, paramToEmail, tempEmailTitle, finalTempEmailContents);
                    try {
                        GMailSender gMailSender = new GMailSender(GlobalMemberValues.GMAILID, GlobalMemberValues.GMAILPWD);
//                        GMailSender.sendMail(제목, 본문내용, 받는사람);
                        gMailSender.sendMail(tempEmailTitle,finalTempEmailContents,paramToEmail);

                    } catch (SendFailedException e) {
//                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                        GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 실패..........\n");
                        GlobalMemberValues.logWrite(TAG, e.toString());
                    } catch (MessagingException e) {
//                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                        GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 실패..........\n");
                        GlobalMemberValues.logWrite(TAG, e.toString());
                    } catch (Exception e) {
                        GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 실패..........\n");
                        GlobalMemberValues.logWrite(TAG, e.toString());
                        e.printStackTrace();
                    }
                    GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 성공\n");
                }
            }.start();
            new AlertDialog.Builder(mContext)
                    .setTitle("Information")
                    .setMessage("Receipt Email Successfully sent")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finishPayment();
                                    PaymentCustomerSelectReceipt.finishPayment();
                                }
                            })
                    .show();
        } else {
            alertDialogForFinishPayment("Failed to send email (No Html)" +
                    "\nDo you want to finish the payment without sending the receipt?");
            //GlobalMemberValues.displayDialog(mContext, "Information", "Failed to send email (No Html)", "Close");
            //Toast.makeText(mContext, "Failed to send email (No Html)", Toast.LENGTH_SHORT).show();
        }
    }

    private String getEmailContentsInJson() throws JSONException {
        String returnTxt = "";

        String js_storename = "";
        String js_storeaddress1 = "";
        String js_storeaddress2 = "";
        String js_storecity = "";
        String js_storestate = "";
        String js_storezip = "";
        String storeaddress = "";
        String js_storephone = "";

        String js_deliverypickupfee = "";
        String js_deliverytakeaway = "";
        String js_customerordernumber = "";

        String js_employeename = "";
        String js_customername = "";
        String js_receiptno = "";

        String js_subtotal = "";
        String js_tax = "";
        String js_totalamount = "";
        String js_creditcardtendered = "";
        String js_cashtendered = "";
        String js_giftcardtendered = "";
        String js_checktendered = "";
        String js_pointtendered = "";
        String js_change = "";

        String js_alldiscountextra = "";                // All Discount, Extra 의 총합
        String js_alldiscountextrastr = "";             // All Discount, Extra 문자열
        String js_discountextra = "";                   // All, Each Discount, Extra 의 총합
        String js_discount = "";                        // All, Each Discount 총합
        String js_extra = "";                           // All, Each Extra 총합

        String txt_deliverytakeaway = "HERE";

        if (jsonReceipt != null) {
            js_storename = jsonReceipt.getString("storename");
            js_storeaddress1 = jsonReceipt.getString("storeaddress1");
            js_storeaddress2 = jsonReceipt.getString("storeaddress2");
            js_storecity = jsonReceipt.getString("storecity");
            js_storestate = jsonReceipt.getString("storestate");
            js_storezip = jsonReceipt.getString("storezip");
            storeaddress = js_storecity + ", " + js_storestate + " " + js_storezip;
            js_storephone = jsonReceipt.getString("storephone");
            if (!GlobalMemberValues.isStrEmpty(js_storephone)) {
                if (js_storephone.length() > 10) {
                    String js_storephone1 = js_storephone.substring(0, 1);
                    String js_storephone2 = js_storephone.substring(1, 4);
                    String js_storephone3 = js_storephone.substring(4, 7);
                    String js_storephone4 = js_storephone.substring(7, js_storephone.length());
                    js_storephone = js_storephone1 + "-" + js_storephone2 + "-" + js_storephone3 + "-" + js_storephone4;
                } else {
                    String js_storephone1 = "";
                    if (js_storephone.length() > 3) {
                        js_storephone1 = js_storephone.substring(0, 3);
                    }

                    String js_storephone2 = "";
                    if (js_storephone.length() > 6) {
                        js_storephone2 = js_storephone.substring(3, 6);
                    }

                    String js_storephone3 = "";
                    if (js_storephone.length() > 7) {
                        js_storephone3 = js_storephone.substring(6, js_storephone.length());
                    }

                    js_storephone = js_storephone1 + "-" + js_storephone2 + "-" + js_storephone3;
                }
            }

            js_employeename = jsonReceipt.getString("employeename");
            js_customername = jsonReceipt.getString("customername");
            js_receiptno = jsonReceipt.getString("receiptno");

            js_subtotal = jsonReceipt.getString("subtotal");
            js_tax = jsonReceipt.getString("tax");
            js_totalamount = jsonReceipt.getString("totalamount");
            js_creditcardtendered = jsonReceipt.getString("creditcardtendered");
            js_cashtendered = jsonReceipt.getString("cashtendered");
            js_giftcardtendered = jsonReceipt.getString("giftcardtendered");
            js_checktendered = jsonReceipt.getString("checktendered");
            js_pointtendered = jsonReceipt.getString("pointtendered");
            js_change = jsonReceipt.getString("change");

            js_alldiscountextra = jsonReceipt.getString("alldiscountextraprice");
            js_alldiscountextrastr = jsonReceipt.getString("alldiscountextrstr");
            js_discountextra = jsonReceipt.getString("totalextradiscountprice");
            js_discount = jsonReceipt.getString("totaldiscountprice");
            js_extra = jsonReceipt.getString("totalextraprice");

            if (jsonReceipt.toString().contains("deliverypickupfee")) {
                js_deliverypickupfee = jsonReceipt.getString("deliverypickupfee");
            } else {
                js_deliverypickupfee = "";
            }
            if (GlobalMemberValues.isStrEmpty(js_deliverypickupfee)) {
                js_deliverypickupfee = "0";
            }

            if (jsonReceipt.toString().contains("deliverytakeaway")) {
                js_deliverytakeaway = jsonReceipt.getString("deliverytakeaway");
            } else {
                js_deliverytakeaway = "";
            }
            if (GlobalMemberValues.isStrEmpty(js_deliverytakeaway)) {
                js_deliverytakeaway = "T";
            }

            if (jsonReceipt.toString().contains("customerordernumber")) {
                js_customerordernumber = jsonReceipt.getString("customerordernumber");
            } else {
                js_customerordernumber = "";
            }

            switch (js_deliverytakeaway) {
                case "H" : {
                    txt_deliverytakeaway =  "HERE";
                    break;
                }
                case "T" : {
                    txt_deliverytakeaway =  "PICK UP";
                    break;
                }
                case "D" : {
                    txt_deliverytakeaway =  "DELIVERY";
                    break;
                }
            }

            if (!GlobalMemberValues.isStrEmpty(htmlStr)) {
                GlobalMemberValues.logWrite(TAG, "html1 : " + htmlStr + "\n");

                // 스토어(company) 이름
                if (!GlobalMemberValues.isStrEmpty(js_storename)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCompanyName]", js_storename);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCompanyName]", "");
                }
                // 스토어 주소
                if (!GlobalMemberValues.isStrEmpty(storeaddress)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtAddress]", storeaddress);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtAddress]", "");
                }
                // 스토어 전화번호
                if (!GlobalMemberValues.isStrEmpty(js_storename)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtPhonenumber]", js_storephone);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtPhonenumber]", "");
                }
                // 직원이름
                if (!GlobalMemberValues.isStrEmpty(js_employeename)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtEmployeeName]", js_employeename);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtEmployeeName]", "");
                }
                // 고객명
                if (!GlobalMemberValues.isStrEmpty(js_customername)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCustomerName]", js_customername);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCustomerName]", "");
                }
                // 영수증 번호
                if (!GlobalMemberValues.isStrEmpty(js_receiptno)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtReceiptNo]", js_receiptno);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtReceiptNo]", "");
                }
                // Order No.
                if (!GlobalMemberValues.isStrEmpty(js_customerordernumber)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtOrderNo]", js_customerordernumber);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtOrderNo]", "");
                }
                // Pickup Type
                if (!GlobalMemberValues.isStrEmpty(txt_deliverytakeaway)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtpickuptype]", txt_deliverytakeaway);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtpickuptype]", "");
                }
                // Pickup / Delivery Fee
                if (GlobalMemberValues.getDoubleAtString(js_deliverypickupfee) > 0) {
                    String tempDeliveryTakeaway = "";
                    if (js_deliverytakeaway.equals("D")) {
                        tempDeliveryTakeaway = "Delivery Fee";
                    } else {
                        tempDeliveryTakeaway = "Pick Up Fee";
                    }

                    String tempTableStr = "";
                    tempTableStr += "<tr>";
                    tempTableStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 60%;\">" + tempDeliveryTakeaway + "</td>";
                    tempTableStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                    tempTableStr += "<td style=\"text-align: right; padding-top: 3px; padding-bottom: 3px; width: 35%;\">" + GlobalMemberValues.getCommaStringForDouble(js_deliverypickupfee) + "</td>";
                    tempTableStr += "</tr>";

                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDeliveryPickupfee]", tempTableStr);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDeliveryPickupfee]", "");
                }

                // 결제일시 구하기 ------------------------------------------------------------------------------------------------
                long now;
                Date date;
                now = System.currentTimeMillis();
                date = new Date(now);
                SimpleDateFormat sdfnow = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String strnow = sdfnow.format(date);
                htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtSaleDate]", strnow);
                // ---------------------------------------------------------------------------------------------------------------

                String itemListStr = "";
                JSONArray js_saleItemJsonArray = jsonReceipt.getJSONArray("saleitemlist");
                if (js_saleItemJsonArray.length() > 0) {
                    itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; font-size: 11px;\">";
                    for (int i = 0; i < js_saleItemJsonArray.length(); i++) {
                        JSONObject itemList = js_saleItemJsonArray.getJSONObject(i);
                        String subjs_itemName = itemList.getString("itemname");
                        String subjs_itemQty = itemList.getString("itemqty");
                        String subjs_itemPrice = itemList.getString("itemprice");
                        String subjs_itemAmount = itemList.getString("itemamount");
                        String subjs_itemdcextratype = itemList.getString("itemdcextratype");
                        String subjs_itemdcextraprice = itemList.getString("itemdcextraprice");
                        String subjs_itemdcextrastr = itemList.getString("itemdcextrastr");

                        String subjs_optionTxt = itemList.getString("optiontxt");
                        String subjs_optionprice = itemList.getString("optionprice");
                        String subjs_additionalTxt1 = itemList.getString("additionaltxt1");
                        String subjs_additionalprice1 = itemList.getString("additionalprice1");
                        String subjs_additionalTxt2 = itemList.getString("additionaltxt2");
                        String subjs_additionalprice2 = itemList.getString("additionalprice2");

                        itemListStr += "<tr>";

                        itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; padding-left: 5px; width: 45%;\">" + subjs_itemName + "</td>";
                        itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 5px; width: 15%;\">" + subjs_itemQty + "</td>";
                        itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 5px; width: 20%;\">" + subjs_itemPrice + "</td>";
                        itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 5px; width: 20%;\">" + subjs_itemAmount + "</td>";

                        itemListStr += "</tr>";

                        if (!GlobalMemberValues.isStrEmpty(subjs_optionTxt)) {
                            itemListStr += "<tr>";
                            itemListStr += "<td colspan=\"4\">";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 90%;\">[Option]</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 70%;\">" + GlobalMemberValues.getModifierTxtForHTML(subjs_optionTxt) + "</td>";
                            itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 10px; width: 20%;\">(" + GlobalMemberValues.getCommaStringForDouble(subjs_optionprice) + ")</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "</td>";
                            itemListStr += "</tr>";
                        }

                        if (!GlobalMemberValues.isStrEmpty(subjs_additionalTxt1)) {
                            itemListStr += "<tr>";
                            itemListStr += "<td colspan=\"4\">";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 90%;\">[Add-on]</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 70%;\">" + GlobalMemberValues.getModifierTxtForHTML(subjs_additionalTxt1) + "</td>";
                            itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 10px; width: 20%;\">(" + GlobalMemberValues.getCommaStringForDouble(subjs_additionalprice1) + ")</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "</td>";
                            itemListStr += "</tr>";
                        }

                        if (!GlobalMemberValues.isStrEmpty(subjs_additionalTxt2)) {
                            itemListStr += "<tr>";
                            itemListStr += "<td colspan=\"4\">";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 90%;\">[Add-on2]</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 70%;\">" + GlobalMemberValues.getModifierTxtForHTML(subjs_additionalTxt2) + "</td>";
                            itemListStr += "<td style=\"text-align: right; padding-top: 1px; padding-bottom: 1px; padding-right: 10px; width: 20%;\">(" + GlobalMemberValues.getCommaStringForDouble(subjs_additionalprice2) + ")</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "</td>";
                            itemListStr += "</tr>";
                        }

                        double itemDcExtraPriceDouble = GlobalMemberValues.getDoubleAtString(subjs_itemdcextraprice);
                        if (!GlobalMemberValues.isStrEmpty(subjs_itemdcextrastr) &&
                                (itemDcExtraPriceDouble > 0 || itemDcExtraPriceDouble < 0)) {
                            String tempStrItemDcExtraType = "Discount";
                            if (subjs_itemdcextratype.equals("EX")) {
                                tempStrItemDcExtraType = "Extra";
                                subjs_itemdcextraprice = "" + subjs_itemdcextraprice;
                            }
                            if (subjs_itemdcextratype.equals("DC")) {
                                tempStrItemDcExtraType = "Discount";
                                subjs_itemdcextraprice = "-" + subjs_itemdcextraprice;
                            }

                            itemListStr += "<tr>";
                            itemListStr += "<td colspan=\"4\">";

                            itemListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; font-size: 11px;\">";
                            itemListStr += "<tr>";
                            itemListStr += "<td style=\"text-align: center; padding-top: 1px; padding-bottom: 1px; width: 10%;\"></td>";
                            itemListStr += "<td style=\"text-align: left; padding-top: 1px; padding-bottom: 1px; width: 90%;\">" + subjs_itemdcextrastr + "</td>";
                            itemListStr += "</tr>";
                            itemListStr += "</table>";

                            itemListStr += "</td>";
                            itemListStr += "</tr>";
                        }

                    }
                    itemListStr += "</table>";

                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[htmlItemList]", itemListStr);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[htmlItemList]", "");
                }


                // Sub Total
                if (!GlobalMemberValues.isStrEmpty(js_subtotal)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtSubTotal]", js_subtotal);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtSubTotal]", "");
                }

                // All Discount Extra
                if (!GlobalMemberValues.isStrEmpty(js_alldiscountextra) && !GlobalMemberValues.isStrEmpty(js_alldiscountextrastr)) {
                    /**
                     String tempAllDcExtraStr = "All Discount";
                     double temp_js_alldiscountextra = GlobalMemberValues.getDoubleAtString(js_alldiscountextra);

                     if (temp_js_alldiscountextra > 0) {
                     tempAllDcExtraStr = "All Extra";
                     }
                     if (temp_js_alldiscountextra < 0) {
                     tempAllDcExtraStr = "All Discount";
                     }
                     **/
                    String tempTableStr = "";
                    tempTableStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; font-size: 11px;\">";
                    tempTableStr += "<tr>";
                    tempTableStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 60%;\">" + js_alldiscountextrastr + "</td>";
                    tempTableStr += "</tr>";
                    tempTableStr += "</table>";
                    tempTableStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; margin-bottom: 3px;\">";
                    tempTableStr += "<tr>";
                    tempTableStr += "<td style=\"border-top: 1px dashed #4e4e4e;\"></td>";
                    tempTableStr += "</tr>";
                    tempTableStr += "</table>";
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtAllDiscountExtra]", tempTableStr);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtAllDiscountExtra]", "");
                }

                // Total Extra - Discount
                if (!GlobalMemberValues.isStrEmpty(js_discountextra)) {
                    String tempDcExtraStr = "D/C Total";
                    double temp_js_discountextra = GlobalMemberValues.getDoubleAtString(js_discountextra);
                    if (temp_js_discountextra > 0 || temp_js_discountextra < 0) {
                        if (temp_js_discountextra > 0) {
                            tempDcExtraStr = "Extra Total";
                        }
                        if (temp_js_discountextra < 0) {
                            tempDcExtraStr = "D/C Total";
                        }
                        String tempTableStr = "";
                        tempTableStr += "<tr>";
                        tempTableStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 60%;\">" + tempDcExtraStr + "</td>";
                        tempTableStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                        tempTableStr += "<td style=\"text-align: right; padding-top: 3px; padding-bottom: 3px; width: 35%;\">" + js_discountextra + "</td>";
                        tempTableStr += "</tr>";
                        htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDiscountExtraTotal]", tempTableStr);
                    } else {
                        htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDiscountExtraTotal]", "");
                    }
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDiscountExtraTotal]", "");
                }

                // Tax
                if (!GlobalMemberValues.isStrEmpty(js_tax)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtTax]", js_tax);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtTax]", "");
                }
                // Total Amount
                if (!GlobalMemberValues.isStrEmpty(js_totalamount)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtTotalAmount]", js_totalamount);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtTotalAmount]", "");
                }
                // Cash Tendered
                if (!GlobalMemberValues.isStrEmpty(js_cashtendered)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCashTendered]", js_cashtendered);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCashTendered]", "");
                }
                // Credit Card Tendered
                if (!GlobalMemberValues.isStrEmpty(js_creditcardtendered)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCreditCardTendered]", js_creditcardtendered);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCreditCardTendered]", "");
                }
                // Gift Card Tendered
                if (!GlobalMemberValues.isStrEmpty(js_giftcardtendered)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtGiftCardTendered]", js_giftcardtendered);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtGiftCardTendered]", "");
                }
                // Check Tendered
                if (!GlobalMemberValues.isStrEmpty(js_checktendered) && GlobalMemberValues.getDoubleAtString(js_checktendered) > 0.0) {
                    String tempTableStr = "";
                    tempTableStr += "<tr>";
                    tempTableStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 60%;\">Other Tendered</td>";
                    tempTableStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                    tempTableStr += "<td style=\"text-align: right; padding-top: 3px; padding-bottom: 3px; width: 35%;\">" + js_checktendered + "</td>";
                    tempTableStr += "</tr>";
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCheckTendered]", tempTableStr);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtCheckTendered]", "");
                }
                // Point Tendered
                if (!GlobalMemberValues.isStrEmpty(js_pointtendered) && GlobalMemberValues.getDoubleAtString(js_pointtendered) > 0.0) {
                    String tempTableStr = "";
                    tempTableStr += "<tr>";
                    tempTableStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 60%;\">Point Tendered</td>";
                    tempTableStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                    tempTableStr += "<td style=\"text-align: right; padding-top: 3px; padding-bottom: 3px; width: 35%;\">" + js_pointtendered + "</td>";
                    tempTableStr += "</tr>";
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtPointTendered]", tempTableStr);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtPointTendered]", "");
                }
                // Change
                if (!GlobalMemberValues.isStrEmpty(js_change)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtChange]", js_change);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtChange]", "");
                }

                // Discount
                if (!GlobalMemberValues.isStrEmpty(js_discount)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDiscount]", js_discount);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtDiscount]", "");
                }
                // Extra
                if (!GlobalMemberValues.isStrEmpty(js_extra)) {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtExtra]", js_extra);
                } else {
                    htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[txtExtra]", "");
                }

                String creditCardListStr = "";
                if (jsonReceipt.toString().contains("creditcardtransaction")) {
                    JSONArray js_creditcardJsonArray = jsonReceipt.getJSONArray("creditcardtransaction");
                    if (js_creditcardJsonArray.length() > 0) {
                        creditCardListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 20px;\">";
                        creditCardListStr += "<tr>";
                        creditCardListStr += "<td></td>";
                        creditCardListStr += "</tr>";
                        creditCardListStr += "</table>";
                        for (int i = 0; i < js_creditcardJsonArray.length(); i++) {
                            creditCardListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; margin-bottom: 3px;\">";
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 25%;\">**********</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding: 3px; width: 50%;\">Credit Card Transaction</td>";
                            creditCardListStr += "<td style=\"text-align: right; padding-top: 3px; padding-bottom: 3px; width: 25%;\">**********</td>";
                            creditCardListStr += "</tr>";
                            creditCardListStr += "</table>";

                            JSONObject creditcardList = js_creditcardJsonArray.getJSONObject(i);

                            String subjs_cardtype = creditcardList.getString("cardtype");
                            String subjs_creditcardnumber = creditcardList.getString("creditcardnumber");
                            String subjs_chargeamount = creditcardList.getString("chargeamount");
                            String subjs_cardauthnumber = creditcardList.getString("cardauthnumber");
                            String subjs_cardrefnumber = creditcardList.getString("cardrefnumber");

                            // EMV 관련 데이터 --------------------------------------------
                            String subjs_cardaid = "";
                            if (creditcardList.toString().contains("cardaid")){
                                subjs_cardaid = creditcardList.getString("cardaid");
                            }
                            String subjs_cardtvr = "";
                            if (creditcardList.toString().contains("cardtvr")){
                                subjs_cardtvr = creditcardList.getString("cardtvr");
                            }
                            String subjs_cardtsi = "";
                            if (creditcardList.toString().contains("cardtsi")){
                                subjs_cardtsi = creditcardList.getString("cardtsi");
                            }
                            // -----------------------------------------------------------

                            creditCardListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; font-size: 11px;\">";

                            // Card Type
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">Card Type</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardtype + "</td>";
                            creditCardListStr += "</tr>";

                            // Card Number
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">Credit Card#</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_creditcardnumber + "</td>";
                            creditCardListStr += "</tr>";

                            // Charge Amount
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">Charge Amount</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_chargeamount + "</td>";
                            creditCardListStr += "</tr>";

                            // Card Auth#
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">Card Auth#</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardauthnumber + "</td>";
                            creditCardListStr += "</tr>";

                            // Card Ref#
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">Card Ref#</td>";
                            creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                            creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardrefnumber + "</td>";
                            creditCardListStr += "</tr>";

                            /** 점주에게 메일 보낼 때... EMV 관련 데이터
                             // AID
                             creditCardListStr += "<tr>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">AID</td>";
                             creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardaid + "</td>";
                             creditCardListStr += "</tr>";
                             // TSI
                             creditCardListStr += "<tr>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">TSI</td>";
                             creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardtsi + "</td>";
                             creditCardListStr += "</tr>";
                             // TVR
                             creditCardListStr += "<tr>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 40%;\">TVR</td>";
                             creditCardListStr += "<td style=\"text-align: center; padding-top: 3px; padding-bottom: 3px; width: 5%;\">:</td>";
                             creditCardListStr += "<td style=\"text-align: left; padding-top: 3px; padding-bottom: 3px; width: 55%;\">" + subjs_cardtvr + "</td>";
                             creditCardListStr += "</tr>";
                             **/

                            creditCardListStr += "</tr>";
                            creditCardListStr += "</table>";

                            creditCardListStr += "<table border=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; margin-top: 3px; margin-bottom: 20px;\">";
                            creditCardListStr += "<tr>";
                            creditCardListStr += "<td style=\"border-top: 1px dashed #4e4e4e;\"></td>";
                            creditCardListStr += "</tr>";
                            creditCardListStr += "</table>";
                        }
                    }
                }
                GlobalMemberValues.logWrite("CardReceiptHtml", "Html : " + creditCardListStr + "\n");
                htmlStr = GlobalMemberValues.getReplaceText(htmlStr, "[htmlCreditCardList]", creditCardListStr);

                returnTxt = htmlStr;
            }
        }

        return returnTxt;
    }

    // 초기화 메소드
    private void setInit() {
        paymentReviewEditTextEmailAddr1.setText("");
        paymentReviewEditTextEmailAddr2.setText("");
    }

    private void finishPayment() {
        Payment.setInit();

        PaymentCustomerSelectReceipt.tempJsonReceipt = null;

        if (GlobalMemberValues.isDeviceClover() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GlobalMemberValues.oepnRotateScreenPopup(mActivity, mContext, "N");
        } else {
            mActivity.finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }
}
