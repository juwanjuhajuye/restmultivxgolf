package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class PushPopupForWebOrders extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;
    private Button pushForWebOrdersDetailViewButton;

    private TextView pushWebOrdersSalesCodeTextView, pushWebOrdersCustomerTextView;
    private TextView pushWebOrdersPhoneTextView, pushWebOrdersSalesDateTextView;
    private TextView pushWebOrdersDeliveryTakeawayTextView, customerOrderNumTextView;
    private TextView pushForWebOrderTimeTo_title, pushForWebOrderTimeTo;
    private TextView pushForWebOrderTableIdx;
    private TextView pushForWebOrderTableName;
    private LinearLayout pushForWebOrderTableName_ln;

    // 08302023
    private LinearLayout pushForWebOrderOnlineType_ln;
    private TextView pushForWebOrderOnlineType;
    private String webOrdersOnlineType;

    private String webOrdersSalesCode, webOrdersCustomerId, webOrdersCustomerName, webOrdersCustomerPhone;
    private String webOrdersDeliveryDay, webOrdersDeliveryTime, webOrdersDeliveryDate, webOrdersDeliveryTakeaway;
    private String webOrdersSaleDate, webOrdersSaleItems, webOrdersMemo, webOrdersCustomerOrderNumber, webOrdersTableName, webOrdersTableIdx;
    private String webOrdersDeliveryTakeawayStr;

    // 10112023
    private String webOrdersOrderfrom;
    private String webOrdersSalescodethirdparty;

    private String tailMsg;

    public static String mSelectedReceiptJsonStr = "";

    Intent mIntent;
    // alram
    public final static int REPEAT_DELAY = 2000;
    public static Sound_timerHandler popup_sound_handler;

    private static final String TAG = "PushPopupForWebOrders";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_push_popup_for_web_orders);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 40;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.pushForReservationLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            webOrdersSalesCode = mIntent.getStringExtra("webOrdersSalesCode");
            webOrdersCustomerId = mIntent.getStringExtra("webOrdersCustomerName");
            webOrdersCustomerName = mIntent.getStringExtra("webOrdersCustomerName");
            webOrdersCustomerPhone = mIntent.getStringExtra("webOrdersCustomerPhone");
            webOrdersDeliveryDay = mIntent.getStringExtra("webOrdersDeliveryDay");
            webOrdersDeliveryTime = mIntent.getStringExtra("webOrdersDeliveryTime");
            webOrdersDeliveryDate = mIntent.getStringExtra("webOrdersDeliveryDate");
            webOrdersDeliveryTakeaway = mIntent.getStringExtra("webOrdersDeliveryTakeaway");
            webOrdersSaleDate = mIntent.getStringExtra("webOrdersSaleDate");
            webOrdersSaleItems = mIntent.getStringExtra("webOrdersSaleItems");
            webOrdersMemo = mIntent.getStringExtra("webOrdersMemo");
            webOrdersCustomerOrderNumber = mIntent.getStringExtra("webOrdersCustomerOrderNumber");
            webOrdersTableName = mIntent.getStringExtra("webOrdersTableName");
            webOrdersTableIdx = mIntent.getStringExtra("webOrdersTableIdx");

            // 10112023
            webOrdersOrderfrom = mIntent.getStringExtra("webOrdersOrderfrom");
            webOrdersSalescodethirdparty = mIntent.getStringExtra("webOrdersSalescodethirdparty");

            // 08302023
            webOrdersOnlineType = mIntent.getStringExtra("webOrdersOnlineType");
            if (GlobalMemberValues.isStrEmpty(webOrdersOnlineType)) {
                webOrdersOnlineType = "W";
            }

            webOrdersSaleItems = GlobalMemberValues.getReplaceText(webOrdersSaleItems, "JUWANJUHAJUYE", "-ANNIETTASU-");

            Log.d(TAG, "webOrdersSalesCode: " + webOrdersSalesCode);
            Log.d(TAG, "webOrdersCustomerName: " + webOrdersCustomerName);
            Log.d(TAG, "webOrdersPhone: " + webOrdersCustomerPhone);
            Log.d(TAG, "webOrdersDeliveryTakeaway: " + webOrdersDeliveryTakeaway);
            Log.d(TAG, "webOrdersSaleDate: " + webOrdersSaleDate);
            Log.d(TAG, "webOrdersSaleItems: " + webOrdersSaleItems);
            Log.d(TAG, "webOrdersMemo: " + webOrdersMemo);
            Log.d(TAG, "webOrdersCustomerOrderNumber: " + webOrdersCustomerOrderNumber);
            Log.d(TAG,"webOrdersTableName: " + webOrdersTableName);
            Log.d(TAG,"webOrdersTableIdx: " + webOrdersTableIdx);
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
//        startNotificationSound();
        popup_sound_handler = new Sound_timerHandler();
        GlobalMemberValues.isSoundContinue = true;
        popup_sound_handler.sendEmptyMessage(1);
    }

    public void setContents() {
        if (GlobalMemberValues.isStrEmpty(webOrdersDeliveryTakeaway)) {
            webOrdersDeliveryTakeaway = "T";
        }

        String webOrdersDeliveryTakeawayStr = "To Go";
        String strDeliveryTakeawayColor = "#f85c8e";
        if (webOrdersDeliveryTakeaway.equals("D")) {
            webOrdersDeliveryTakeawayStr = "Delivery (" + webOrdersSaleDate + ")";
            strDeliveryTakeawayColor = "#23b3c2";
        } else {
            if (webOrdersDeliveryTakeaway.equals("H")) {
                webOrdersDeliveryTakeawayStr = "In-Store";
                strDeliveryTakeawayColor = "#75b92b";
            } else {
                webOrdersDeliveryTakeawayStr = "To Go";
                strDeliveryTakeawayColor = "#f85c8e";
            }
        }

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(pushForWebOrdersButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        pushForWebOrderTimeTo_title = (TextView)findViewById(R.id.pushForWebOrderTimeTo_title);
        if (!GlobalMemberValues.isStrEmpty(webOrdersDeliveryTakeaway)){
            if (webOrdersDeliveryTakeaway.equals("T")){
                pushForWebOrderTimeTo_title.setText("Time to Pickup");
            } else if (webOrdersDeliveryTakeaway.equals("D")){
                pushForWebOrderTimeTo_title.setText("Time to Delivery");
            }
        }



        // 08302023
        pushForWebOrderOnlineType_ln = (LinearLayout)findViewById(R.id.pushForWebOrderOnlineType_ln);
        pushForWebOrderOnlineType = (TextView)findViewById(R.id.pushForWebOrderOnlineType);
        String webOrdersOnlineType_str = "Web Order";
        switch (webOrdersOnlineType) {
            case "W" : {
                webOrdersOnlineType_str = "Web Order";
                break;
            }
            case "M" : {
                webOrdersOnlineType_str = "Mobile Order";
                break;
            }
            case "K" : {
                webOrdersOnlineType_str = "Kiosk Order";
                break;
            }
            case "T" : {
                webOrdersOnlineType_str = "OTTER";

                // 10112023
                if (!GlobalMemberValues.isStrEmpty(webOrdersOrderfrom)) {
                    webOrdersOnlineType_str = webOrdersOrderfrom.toUpperCase();
                }
                if (!GlobalMemberValues.isStrEmpty(webOrdersSalescodethirdparty)) {
                    webOrdersOnlineType_str += " (" + webOrdersSalescodethirdparty + ")";
                }


                break;
            }
        }
        pushForWebOrderOnlineType.setText(webOrdersOnlineType_str);




        pushForWebOrderTableName_ln = (LinearLayout)findViewById(R.id.pushForWebOrderTableName_ln);
        if (GlobalMemberValues.isStrEmpty(webOrdersTableName)){
            pushForWebOrderTableName_ln.setVisibility(View.GONE);
        } else {
            pushForWebOrderTableName_ln.setVisibility(View.VISIBLE);
            pushForWebOrderTableName = (TextView)findViewById(R.id.pushForWebOrderTableName);
            pushForWebOrderTableName.setText(webOrdersTableName);
            pushForWebOrderTableIdx = (TextView)findViewById(R.id.pushForWebOrderTableIdx);
            pushForWebOrderTableIdx.setText(webOrdersTableIdx);
        }

        pushForWebOrderTimeTo = (TextView)findViewById(R.id.pushForWebOrderTimeTo);
        pushForWebOrderTimeTo.setText(webOrdersDeliveryDate);

        pushForWebOrdersDetailViewButton = (Button)findViewById(R.id.pushForWebOrdersDetailViewButton);
        pushForWebOrdersDetailViewButton.setOnClickListener(pushForWebOrdersButtonClickListener);

        pushWebOrdersSalesCodeTextView = (TextView) findViewById(R.id.pushWebOrdersSalesCodeTextView);
        pushWebOrdersSalesCodeTextView.setText(webOrdersSalesCode);

        customerOrderNumTextView = (TextView) findViewById(R.id.customerOrderNumTextView);
        customerOrderNumTextView.setText(webOrdersCustomerOrderNumber);

        pushWebOrdersCustomerTextView = (TextView) findViewById(R.id.pushWebOrdersCustomerTextView);
        pushWebOrdersCustomerTextView.setText(webOrdersCustomerName);

        pushWebOrdersPhoneTextView = (TextView) findViewById(R.id.pushWebOrdersPhoneTextView);
        pushWebOrdersPhoneTextView.setText(webOrdersCustomerPhone);

        pushWebOrdersDeliveryTakeawayTextView = (TextView) findViewById(R.id.pushWebOrdersDeliveryTakeawayTextView);
        pushWebOrdersDeliveryTakeawayTextView.setTextColor(Color.parseColor(strDeliveryTakeawayColor));
        pushWebOrdersDeliveryTakeawayTextView.setText(webOrdersDeliveryTakeawayStr);

        pushWebOrdersSalesDateTextView = (TextView) findViewById(R.id.pushWebOrdersSalesDateTextView);
        pushWebOrdersSalesDateTextView.setText(webOrdersSaleDate);

        // 주방프린트 하기 --------------------------------------------------------------------
        // 메인스테이션 일 경우에만 주방프린트한다...
        String tempMainYN = "";
        if (MainActivity.mDbInit != null){
            if (GlobalMemberValues.STATION_CODE != null) {
                if (MainActivity.mDbInit.dbExecuteReadReturnString("select mainYN from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ") != null){
                    tempMainYN = MainActivity.mDbInit.dbExecuteReadReturnString("select mainYN from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ");
                }
            }
        }
        if (GlobalMemberValues.isStrEmpty(tempMainYN)) {
            tempMainYN = "N";
        }
        GlobalMemberValues.logWrite("mainstationynlog", "tempMainYN : " + tempMainYN + "\n");
        if (tempMainYN == "Y" || tempMainYN.equals("Y")) {
            if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                    "select kitchenprinting from salon_storestationsettings_deviceprinter2")) == 0) {
                JSONObject jsonroot_kitchen = new JSONObject();
                try {
                    jsonroot_kitchen.put("receiptno", webOrdersSalesCode);
                    jsonroot_kitchen.put("saledate", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                    jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());
                    jsonroot_kitchen.put("customername", webOrdersCustomerName);
                    jsonroot_kitchen.put("saleitemlist", webOrdersSaleItems);
                    jsonroot_kitchen.put("deliverytakeaway", webOrdersDeliveryTakeaway);
                    jsonroot_kitchen.put("deliverydate", webOrdersDeliveryDate);
                    jsonroot_kitchen.put("ordertype", "WEB");
                    jsonroot_kitchen.put("customermemo", webOrdersMemo);
                    jsonroot_kitchen.put("customerordernumber", webOrdersCustomerOrderNumber);
                    jsonroot_kitchen.put("restaurant_tablename",webOrdersTableName);
                    jsonroot_kitchen.put("pushdatayn","Y");

                    // 10112023
                    jsonroot_kitchen.put("orderfrom",webOrdersOrderfrom);
                    jsonroot_kitchen.put("salescodethirdparty",webOrdersSalescodethirdparty);

                } catch (JSONException e) {
                    e.printStackTrace();
                }





                Vector<String> updateVec = new Vector<String>();

                if (!GlobalMemberValues.isStrEmpty(jsonroot_kitchen.toString())) {
                    int tempKtprintedynCnt = 0;
                    tempKtprintedynCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select count(*) from salon_sales_web_push_realtime " +
                                    " where salescode = '" + webOrdersSalesCode + "' and kitchenprintedyn = 'Y' "));

                    if (tempKtprintedynCnt == 0) {
                        GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "Y";
                        GlobalMemberValues.printGateByKitchen(jsonroot_kitchen, mContext, "kitchen1");



                        // 09152023
                        String strQuery = " update salon_sales_web_push_realtime set " +
                                " kitchenprintedyn = 'Y' " +
                                " where salescode = '" + webOrdersSalesCode + "' ";

                        updateVec.addElement(strQuery);
                    }

                }
                if (GlobalMemberValues.isOnlineOrderAutoReceiptPrinting()){
                    int tempRtprintedynCnt = 0;
                    tempRtprintedynCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select count(*) from salon_sales_web_push_realtime " +
                                    " where salescode = '" + webOrdersSalesCode + "' and receiptprintedyn = 'Y' "));

                    if (tempRtprintedynCnt == 0) {
                        printReceipt();

                        // 09152023
                        String strQuery = " update salon_sales_web_push_realtime set " +
                                " receiptprintedyn = 'Y' " +
                                " where salescode = '" + webOrdersSalesCode + "' ";

                        updateVec.addElement(strQuery);
                    }
                }

                if (updateVec != null && updateVec.size() > 0) {
                    String returnResult = "";
                    returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(updateVec);
                    if (returnResult == "N" || returnResult == "") {
                        //
                    } else {
                    }
                }






            }
        }
        // -----------------------------------------------------------------------------------
    }

    public void startNotificationSound(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    public void openSaleHistoryWeb() {
        String returnResult = "";
        String strQuery = "";
        // salon_sales_web_push_realtime 테이블 저장
        Vector<String> strQueryVec = new Vector<String>();

        //먼저 같은 SalesCode 로 저장된 값을 삭제
        strQuery = "delete from salon_sales_web_push_realtime where salesCode = '"+ webOrdersSalesCode + "' ";
        strQueryVec.addElement(strQuery);
        for (String tempQuery : strQueryVec) {
            GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "query : " + tempQuery + "\n");
        }
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database error. Try again", "Close");
        } else { // 정상처리일 경우 팝업창 오픈
            Intent intent;
            if (GlobalMemberValues.isServiceRunningCheck(mContext, "com.example.jjbfather.jjjqsr")) {
                if (MainActivity.isOpenApplication) {
                    GlobalMemberValues.shweb_fromCommand = "Y";
                    intent = new Intent(mContext, SaleHistory_web.class);
                    Log.d(TAG, "여기실행1");
                } else {
                    intent = new Intent(mContext, MainActivity.class);
                    Log.d(TAG, "여기실행2");
                }
            } else {
                if (MainActivity.isOpenApplication) {
                    GlobalMemberValues.shweb_fromCommand = "Y";
                    intent = new Intent(mContext, SaleHistory_web.class);
                    Log.d(TAG, "여기실행3");
                } else {
                    intent = mContext.getPackageManager().getLaunchIntentForPackage("com.example.jjbfather.jjjqsr");
                    //intent = new Intent(this, MainActivity.class);
                    Log.d(TAG, "여기실행4");
                }
            }
            SaleHistory_web.mDeliveryTakeawayValueFromOutActivity = webOrdersDeliveryTakeaway;
            intent.putExtra("pushIntentOpenType", "pushweborders");
            intent.putExtra("pushSalesCode", webOrdersSalesCode);
            intent.putExtra("pushDeliveryTakeaway", webOrdersDeliveryTakeaway);
            intent.putExtra("pushOrderType", webOrdersCustomerOrderNumber.substring(0,1));

            // 10112023
            intent.putExtra("pushOrderfrom", webOrdersOrderfrom);
            intent.putExtra("pushSalescodethirdparty", webOrdersSalescodethirdparty);

            startActivity(intent);

            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }
    }

    View.OnClickListener pushForWebOrdersButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pushForWebOrdersDetailViewButton : {
                    popup_sound_handler.removeMessages(1);
                    openSaleHistoryWeb();
                    break;
                }
                case R.id.closeButton : {
                    popup_sound_handler.removeMessages(1);
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
            }
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popup_sound_handler.removeMessages(1);
    }

    public void printReceipt() {
        GlobalMemberValues.logWrite("LogAboutreceiptJson", "여기실행.. " + "\n");

        if (GlobalMemberValues.isStrEmpty(webOrdersSalesCode)) {
            return;
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    if (!GlobalMemberValues.isOnline().equals("00")) {
//                        GlobalMemberValues.showDialogNoInternet(context);
                    } else {
                        API_download_weborders_receiptjson apiWebOrdersReceiptJson = new API_download_weborders_receiptjson(webOrdersSalesCode);
                        apiWebOrdersReceiptJson.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (apiWebOrdersReceiptJson.mFlag) {
                                mSelectedReceiptJsonStr = apiWebOrdersReceiptJson.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            mSelectedReceiptJsonStr = "";
                        }
                    }
                }
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                receiptJsonHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public static Handler receiptJsonHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mSelectedReceiptJsonStr)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음.
                String receiptJson = mSelectedReceiptJsonStr;
                GlobalMemberValues.logWrite("LogAboutreceiptJson", "receiptJson : " + receiptJson + "\n");

                if (!GlobalMemberValues.isStrEmpty(receiptJson)) {
                    JSONObject jsonroot = null;
                    try {
                        jsonroot = new JSONObject(receiptJson);
                        jsonroot.put("reprintyn", "N");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Label print
//                    if (GlobalMemberValues.isUseLabelPrinter()){
//                        // json 쪼개기..
//                        JSONArray temp_array = new JSONArray();
//                        temp_array = GlobalMemberValues.labelPrint_menuSplit(jsonroot);
//                        if (temp_array != null && temp_array.length() != 0){
////                            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
////                            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");
//                            GlobalMemberValues.printLabel1to5(temp_array);
//                        }
//                    }
                    // Label print

                    if (jsonroot != null) {
                        GlobalMemberValues.mReReceiptprintYN = "Y";
                        GlobalMemberValues.mOnlineOrder = "Y";
                        // Merchant 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
                        if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("merchant") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                            GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
                            GlobalMemberValues.printReceiptByJHP(jsonroot, MainActivity.mContext, "payment");
                        }
                        // Customer 영수증 프린트 ----------------------------------------------------------------------------------------------------------------------------
                        if (GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT == "N" || GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT.equals("N")) {
                            if (GlobalMemberValues.RECEIPTPRINTTYPE == "" || GlobalMemberValues.RECEIPTPRINTTYPE.equals("customer") || GlobalMemberValues.RECEIPTPRINTTYPE.equals("custmerc")) {
                                GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
                                GlobalMemberValues.printReceiptByJHP(jsonroot, MainActivity.mContext, "payment");
                            }
                        }
                    }
                } else {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Waraning", "There is no data to reprint", "Close");
                }
            }
        }
    };

}
