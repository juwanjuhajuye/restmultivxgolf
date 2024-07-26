package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class SettingsSystemReset extends Activity {
    static Activity mActivity;
    static Context context;
    public static Context insContext;

    // DB 객체 선언
    static DatabaseInit dbInit;

    public ProgressDialog proDial;

    private LinearLayout parentLn;
    Intent mIntent;

    private CheckBox systemreset_all, systemreset_sales, systemreset_customer, systemreset_point;
    private CheckBox systemreset_menu, systemreset_product, systemreset_giftcard;
    private Switch systemreset_cloud;

    private Button systemreset_v, systemreset_close;

    String[] mCheckedCB = null;

    Vector<String> mResetResultMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_system_reset);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 90) * 30;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.settingsSystemResetLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            //mSalesCode = mIntent.getStringExtra("salesCode");
            //GlobalMemberValues.logWrite("tipCardList", "넘겨받은 SalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            initValue();
            initMsg();
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
        GlobalMemberValues.logWrite("tipCardListLog", "tipCardList 실행 - 3" + "\n");

        setContents();
    }

    public void setContents() {
        mActivity = this;
        context = this;
        dbInit = new DatabaseInit(this);

        systemreset_v = (Button)findViewById(R.id.systemreset_v);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            systemreset_v.setText("");
            systemreset_v.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            systemreset_v.setTextSize(
                    systemreset_v.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        systemreset_close = (Button)findViewById(R.id.systemreset_close);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            systemreset_close.setText("");
            systemreset_close.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            systemreset_close.setTextSize(
                    systemreset_close.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        systemreset_v.setOnClickListener(systemresetBtnClickListener);
        systemreset_close.setOnClickListener(systemresetBtnClickListener);

        systemreset_all = (CheckBox)findViewById(R.id.systemreset_all);
        systemreset_sales = (CheckBox)findViewById(R.id.systemreset_sales);
        systemreset_customer = (CheckBox)findViewById(R.id.systemreset_customer);
        systemreset_point = (CheckBox)findViewById(R.id.systemreset_point);
        systemreset_menu = (CheckBox)findViewById(R.id.systemreset_menu);
        systemreset_product = (CheckBox)findViewById(R.id.systemreset_product);
        systemreset_giftcard = (CheckBox)findViewById(R.id.systemreset_giftcard);

        systemreset_all.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_sales.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_customer.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_point.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_menu.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_product.setOnCheckedChangeListener(systemresetCBChangeListner);
        systemreset_giftcard.setOnCheckedChangeListener(systemresetCBChangeListner);

        systemreset_cloud = (Switch) findViewById(R.id.systemreset_cloud);
        systemreset_cloud.setOnCheckedChangeListener(systemtresetSWChangeListner);

        initValue();
        initMsg();
    }

    Switch.OnCheckedChangeListener systemtresetSWChangeListner = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                GlobalMemberValues.logWrite("cloudresetlog", "클라우드 체크여부 :  Y" + "\n");

                if (GlobalMemberValues.GLOBALNETWORKSTATUS == 0) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                    buttonView.setChecked(false);
                    return;
                }

                if (!GlobalMemberValues.isOnline().equals("00")) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                    buttonView.setChecked(false);
                    return;
                }
            }
        }
    };

    CheckBox.OnCheckedChangeListener systemresetCBChangeListner = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String checkedItemTagValue = buttonView.getTag().toString();
            if (isChecked) {
                mCheckedCB[GlobalMemberValues.getIntAtString(checkedItemTagValue)] = "Y";
            } else {
                mCheckedCB[GlobalMemberValues.getIntAtString(checkedItemTagValue)] = "N";
            }
        }
    };

    View.OnClickListener systemresetBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.systemreset_close : {
                    finishReset();

                    break;
                }
                case R.id.systemreset_v : {
                    int checkedItemCount = 0;
                    for (int i = 0; i < 7; i++) {
                        if (mCheckedCB[i] == "Y" || mCheckedCB[i].equals("Y")) {
                            checkedItemCount++;
                        }
                    }
                    if (checkedItemCount > 0) {
                        new AlertDialog.Builder(context)
                                .setTitle("System Initialize")
                                .setMessage("Initialized data can not be restored\nDo you want to reset application?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                initSystem();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Check item to initialize", "Close");
                    }

                    break;
                }
            }
        }
    };

    public void initSystem() {
        Vector<String> mResetResultMsg = null;

        // 프로그래스 바를 실행~
        proDial = ProgressDialog.show(this, "INITIALIZE", "Initializing data...", true, false);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                initSalesData();
                initCustomerInformation();
                initCustomerPointInformation();
                initMenu();
                initProduct();
                initGiftCard();
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public void initSalesData() {
        if (mCheckedCB[1].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "세일데이터" + "\n");

            String initTitleStr = "sales";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            strInsertQueryVec = getSaleInitQueryVector("Y");

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_salesdatadelete_incloud initApi = new API_salesdatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public static boolean initSalesDataByOut() {
        boolean returnValue = false;
        Vector<String> strInsertQueryVec = null;
        // 데이터 초기화
        strInsertQueryVec = getSaleInitQueryVector("N");

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
        }
        String returnResult = "";
        // 트랜잭션으로 DB 처리한다.
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
        String returnSalesCode = "";
        if (returnResult == "N" || returnResult == "") {
            returnValue = false;
        } else {
            returnValue = true;
        }

        return returnValue;
    }

    public static Vector<String> getSaleInitQueryVector(String paramAllDelete) {
        Vector<String> strInsertQueryVec = new Vector<String>();

        if (GlobalMemberValues.isStrEmpty(paramAllDelete)) {
            paramAllDelete = "Y";
        }
        String addQuery1 = "";
        String addQuery2 = "";
        String addQuery3 = "";
        if (paramAllDelete == "N" || paramAllDelete.equals("N")) {
            String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -3650);
            String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -15);

            addQuery1 = " where strftime('%Y-%m-%d', wdate) between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
            addQuery2 = " where strftime('%Y-%m-%d', saledate) between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
            addQuery3 = " where strftime('%Y-%m-%d', uploaddate) between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
        }

        // 데이터 초기화
        String tempDeleteDataSql = "delete from temp_salecart" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql);
        String tempDeleteDataSql1 = "delete from salon_sales" + addQuery2;
        strInsertQueryVec.addElement(tempDeleteDataSql1);
        String tempDeleteDataSql2 = "delete from salon_sales_detail" + addQuery2;
        strInsertQueryVec.addElement(tempDeleteDataSql2);
        String tempDeleteDataSql3 = "delete from salon_sales_return" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql3);
        String tempDeleteDataSql4 = "delete from salon_sales_card" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql4);
        String tempDeleteDataSql5 = "delete from salon_sales_tip" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql5);
        String tempDeleteDataSql6 = "delete from salon_sales_batch" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql6);
        String tempDeleteDataSql7 = "delete from salon_sales_batch_json" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql7);
        String tempDeleteDataSql8 = "delete from salon_sales_cashintout_history" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql8);
        String tempDeleteDataSql9 = "delete from salon_sales_cashintout_history_cashlist" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql9);
        String tempDeleteDataSql10 = "delete from salon_sales_cashout_json" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql10);
        String tempDeleteDataSql11 = "delete from salon_sales_endofday_json" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql11);
        String tempDeleteDataSql12 = "delete from salon_sales_cashout_emp" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql12);

        String tempDeleteDataSql13 = "delete from temp_salecart_del" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql13);
        String tempDeleteDataSql14 = "delete from salon_sales_batch" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql14);
        String tempDeleteDataSql15 = "delete from salon_sales_customerordernumber" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql15);
        String tempDeleteDataSql16 = "delete from salon_sales_customerpagernumber" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql16);
        String tempDeleteDataSql17 = "delete from salon_sales_kitchen_json" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql17);
        String tempDeleteDataSql18 = "delete from salon_sales_receipt_json" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql18);
        // sign 이미지 데이테 베이스 삭제 안함
        // 083123
//        String tempDeleteDataSql19 = "delete from salon_sales_signedimg" + addQuery1;
//        strInsertQueryVec.addElement(tempDeleteDataSql19);
        String tempDeleteDataSql20 = "delete from salon_sales_tip" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql20);

        String tempDeleteDataSql21 = "delete from salon_sales_kitchenprintingdata_json" + addQuery3;
        strInsertQueryVec.addElement(tempDeleteDataSql21);

        // card status db 삭제 추가.
        String tempDeleteDataSql22 = "delete from card_processing_data" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql22);


        // 06172024
        // salon_sales_togodeliveryfee 삭제 추가
        String tempDeleteDataSql_J01 = "delete from salon_sales_togodeliveryfee" + addQuery1;
        strInsertQueryVec.addElement(tempDeleteDataSql_J01);



        return strInsertQueryVec;
    }

    public void initCustomerInformation() {
        if (mCheckedCB[2].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "고객데이터" + "\n");

            String initTitleStr = "customers";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            String tempDeleteDataSql = "delete from member_mileage";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            String tempDeleteDataSql1 = "delete from member1";
            strInsertQueryVec.addElement(tempDeleteDataSql1);
            String tempDeleteDataSql2 = "delete from member2";
            strInsertQueryVec.addElement(tempDeleteDataSql2);
            String tempDeleteDataSql3 = "delete from salon_member";
            strInsertQueryVec.addElement(tempDeleteDataSql3);
            String tempDeleteDataSql4 = "delete from member_salevisit";
            strInsertQueryVec.addElement(tempDeleteDataSql4);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            GlobalMemberValues.logWrite("cloudresetlog", "returnResult : " + returnResult + "\n");
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                GlobalMemberValues.logWrite("cloudresetlog", "여기..." + "\n");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        GlobalMemberValues.logWrite("cloudresetlog", "클라우드 초기화 부분 실행되고 있음.." + "\n");

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_customerdatadelete_incloud initApi = new API_customerdatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public void initCustomerPointInformation() {
        if (mCheckedCB[3].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "포이트데이터" + "\n");

            String initTitleStr = "customers' points";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            String tempDeleteDataSql = " update salon_member set mileage = 0 ";
            strInsertQueryVec.addElement(tempDeleteDataSql);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_customerpointdatadelete_incloud initApi = new API_customerpointdatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public void initMenu() {
        if (mCheckedCB[4].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "서비스메뉴데이터" + "\n");

            String initTitleStr = "service menus";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            String tempDeleteDataSql = "";
            //tempDeleteDataSql = "update salon_storeservice_sub set delyn = 'Y' ";
            tempDeleteDataSql = "delete from salon_storeservice_main ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_sub ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_sub_order ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_sub_setmenu ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_option_btn ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_option ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_option_item ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_additional ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_storeservice_sub_timemenuprice ";
            strInsertQueryVec.addElement(tempDeleteDataSql);
            tempDeleteDataSql = "delete from salon_store_tare ";
            strInsertQueryVec.addElement(tempDeleteDataSql);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_menudatadelete_incloud initApi = new API_menudatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public void initProduct() {
        if (mCheckedCB[5].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "상품데이터" + "\n");

            String initTitleStr = "products";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            //String tempDeleteDataSql = "update salon_storeproduct set delyn = 'Y' ";
            String tempDeleteDataSql = "delete from salon_storeproduct";
            strInsertQueryVec.addElement(tempDeleteDataSql);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_productdatadelete_incloud initApi = new API_productdatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public void initGiftCard() {
        if (mCheckedCB[6].equals("Y") || mCheckedCB[0].equals("Y")) {
            GlobalMemberValues.logWrite("initdatabasesection", "기프트카드데이터" + "\n");

            String initTitleStr = "gift cards";

            Vector<String> strInsertQueryVec = new Vector<String>();

            // 데이터 초기화
            //String tempDeleteDataSql = "update salon_storegiftcard set delyn = 'Y' ";
            String tempDeleteDataSql = "delete from salon_storegiftcard";
            strInsertQueryVec.addElement(tempDeleteDataSql);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            String returnSalesCode = "";
            if (returnResult == "N" || returnResult == "") {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : failed (DB Error)");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Database Error", "Close");
            } else {
                mResetResultMsg.addElement("Initialized " + initTitleStr + " data : Success");
                //GlobalMemberValues.displayDialog(context, "Sales Data Delete", "Successfully Deleted", "Close");

                if (systemreset_cloud.isChecked()) {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            return;
                        }

                        // 먼저 API 를 통해 클라우드에 있는 세일데이터를 삭제한다.
                        String returnValue = "";
                        API_giftcarddatadelete_incloud initApi = new API_giftcarddatadelete_incloud();
                        initApi.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (initApi.mFlag) {
                                returnValue = initApi.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            returnValue = "";
                        }

                        if (returnValue.equals("00")) {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : Success");
                        } else {
                            mResetResultMsg.addElement("Initialized " + initTitleStr + " data in cloud : failed");
                        }
                    }
                }
            }
        }
    }

    public void showResultMsg() {
        String resultMsg = "";
        if (mResetResultMsg.size() > 0) {
            for (String tempResultMsg : mResetResultMsg) {
                resultMsg += "- " + tempResultMsg + "\n";
            }
            GlobalMemberValues.displayDialog(context, "Initialize Result Message", resultMsg, "Close");
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            showResultMsg();
            proDial.dismiss();
            initValue();
        }
    };

    public void finishReset() {
        if (mResetResultMsg != null && mResetResultMsg.size() > 0) {            // 항목 한개이상 초기화했을 경우
            new AlertDialog.Builder(context)
                    .setTitle("INITIALIZE")
                    .setMessage("Would you like to reload the ‘POS System’ to reflect the updated information?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Settings.mActivity.finish();
                                    initValue();
                                    initMsg();
                                    mActivity.finish();
                                    if (GlobalMemberValues.isUseFadeInOut()) {
                                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                                    }
                                    //mActivity.recreate();

                                    // 메인 로그아웃 관련 --------------------------
                                    GlobalMemberValues.ITEMCANCELAPPLY = 1;
                                    GlobalMemberValues.MAINRECREATEYN = "Y";
                                    //MainActivity.mActivity.recreate();
                                    MainActivity.employeeLogout();
                                    //MainActivity.mActivity.recreate();
                                    // ------------------------------------------
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        } else {
            initValue();
            initMsg();
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public void initValue() {
        systemreset_all.setChecked(false);
        systemreset_sales.setChecked(false);
        systemreset_customer.setChecked(false);
        systemreset_point.setChecked(false);
        systemreset_menu.setChecked(false);
        systemreset_product.setChecked(false);
        systemreset_giftcard.setChecked(false);

        systemreset_cloud.setChecked(false);

        mCheckedCB = new String[8];
        for (int i = 0; i < 7; i++) {
            mCheckedCB[i] = "N";
        }
    }

    public void initMsg() {
        mResetResultMsg = new Vector<String>();
        mResetResultMsg.removeAllElements();
    }
}
