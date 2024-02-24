package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONException;

import java.util.Vector;

public class SelectGetFoodType extends Activity {
    private static final String TAG = "SelectGetFoodTypeLog";

    static Activity mActivity;
    static Context mContext;

    // DB 객체 선언
    static DatabaseInit dbInit;

    private LinearLayout parentLn;
    private LinearLayout hereln, togoln, deliveryln;
    private LinearLayout herespaceln, togospaceln;
    private Button ordertypeCloseBtn, herebtn, togobtn, deliverybtn;

    private CheckBox phoneorderCheckbox, checkprintCheckbox;

    Intent mIntent;

    static String mPayYN = "";

    static String mGetHoldCode = "";

    static String mCheckedPhoneorder = "N";
    static String mCheckedCheckPrint = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_select_get_food_type);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
        } else {
//            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 48;
//            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 45;
//            if (GlobalMemberValues.thisTabletRealHeight < 800) {
//                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 45;
//            }
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 60;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.selectGetFoodLn);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            mPayYN = mIntent.getStringExtra("payyn");
            GlobalMemberValues.logWrite(TAG, "mPayYN : " + mPayYN + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_right);
            }
        }

        mGetHoldCode = MainMiddleService.mHoldCode;

        setContents();
    }

    private void setContents() {
        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        mCheckedPhoneorder = "N";
        mCheckedCheckPrint = "N";

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(mContext);

        ordertypeCloseBtn = (Button)findViewById(R.id.ordertypeCloseBtn);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            ordertypeCloseBtn.setText("");
//            ordertypeCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common);
        } else {
            ordertypeCloseBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    ordertypeCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        phoneorderCheckbox = (CheckBox) findViewById(R.id.phoneorderCheckbox);
        phoneorderCheckbox.setHighlightColor(Color.parseColor("#000000"));
        phoneorderCheckbox.setOnCheckedChangeListener(checkedPhoneOrder);
        if (!GlobalMemberValues.isStrEmpty(mPayYN) && mPayYN.equals("Y")) {
            phoneorderCheckbox.setText("   Phone Order");
            phoneorderCheckbox.setEnabled(false);
        } else {
            phoneorderCheckbox.setText("   Phone Order (Print in the kitchen)");
            if (!GlobalMemberValues.isPhoneOrder()) {
                phoneorderCheckbox.setEnabled(false);
            } else {
                phoneorderCheckbox.setEnabled(true);
            }
        }

        checkprintCheckbox = (CheckBox) findViewById(R.id.checkprintCheckbox);
        checkprintCheckbox.setHighlightColor(Color.parseColor("#000000"));
        checkprintCheckbox.setOnCheckedChangeListener(checkedPhoneOrder);
        if (!GlobalMemberValues.isStrEmpty(mPayYN) && mPayYN.equals("Y")) {
            checkprintCheckbox.setText("   Check Print");
            checkprintCheckbox.setEnabled(false);
        } else {
            checkprintCheckbox.setText("   Check Print (Print on receipt printer)");
            if (!GlobalMemberValues.isPhoneOrder()) {
                checkprintCheckbox.setEnabled(false);
            } else {
                checkprintCheckbox.setEnabled(true);
            }
        }

        herebtn = (Button) findViewById(R.id.herebtn);
        togobtn = (Button) findViewById(R.id.togobtn);
        deliverybtn = (Button) findViewById(R.id.deliverybtn);

        ordertypeCloseBtn.setOnClickListener(getFoodButtonClickLinstener);
        herebtn.setOnClickListener(getFoodButtonClickLinstener);
        togobtn.setOnClickListener(getFoodButtonClickLinstener);
        deliverybtn.setOnClickListener(getFoodButtonClickLinstener);

        hereln = (LinearLayout)findViewById(R.id.hereln);
        togoln = (LinearLayout)findViewById(R.id.togoln);
        deliveryln = (LinearLayout)findViewById(R.id.deliveryln);

        hereln.setBackgroundColor(Color.parseColor("#00000000"));
        togoln.setBackgroundColor(Color.parseColor("#00000000"));
        deliveryln.setBackgroundColor(Color.parseColor("#00000000"));

        herespaceln = (LinearLayout)findViewById(R.id.herespaceln);
//        togospaceln = (LinearLayout)findViewById(R.id.togospaceln);

        if (!GlobalMemberValues.isStrEmpty(mGetHoldCode)) {
            // 먼저 temp_salecart 에 해당 mGetHoldCode 로 저장된 아이템이 있는지 체크한다.
            int tempItemCount = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString("select count(idx) from temp_salecart where holdcode = '" + mGetHoldCode + "' ")
            );
            if (tempItemCount == 0) {
                String returnDbResult = "0";
                Vector<String> strQueryVec = new Vector<String>();
                String strQuery_ = "";

                // 다음으로 특정 Holdcode 로 저장되어 있는 것들 삭제
                strQuery_ = "delete from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' " ;
                strQueryVec.addElement(strQuery_);

                for (String tempQuery : strQueryVec) {
                    GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                returnDbResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);

                // DB 에서 삭제가 정상적으로 되었을 경우에만 (리턴값이 "0" 일경우)
                if (returnDbResult == "N" || returnDbResult == "") {
                } else {
                }
            } else {
                String tempDeliTakeType = MssqlDatabase.getResultSetValueToString(
                        "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' "
                );
                if (!GlobalMemberValues.isStrEmpty(tempDeliTakeType)) {
                    switch (tempDeliTakeType) {
                        case "H" : {
                            hereln.setBackgroundColor(Color.parseColor("#ededed"));
                            break;
                        }
                        case "T" : {
                            togoln.setBackgroundColor(Color.parseColor("#ededed"));
                            break;
                        }
                        case "D" : {
                            deliveryln.setBackgroundColor(Color.parseColor("#ededed"));
                            break;
                        }
                    }
                }

                String tempPhoneorder = MssqlDatabase.getResultSetValueToString(
                        "select phoneorder from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempPhoneorder)) {
                    tempPhoneorder = "N";
                }

                if (tempPhoneorder.equals("Y") || tempPhoneorder == "Y") {
                    phoneorderCheckbox.setChecked(true);
                    checkprintCheckbox.setChecked(true);
                } else {
                    phoneorderCheckbox.setChecked(false);
                    checkprintCheckbox.setChecked(false);
                }
            }
        }

        if (!GlobalMemberValues.isPickupTypeHere()) {
            hereln.setVisibility(View.INVISIBLE);
            herespaceln.setVisibility(View.INVISIBLE);
        } else {
            hereln.setVisibility(View.VISIBLE);
            herespaceln.setVisibility(View.VISIBLE);
        }

        if (!GlobalMemberValues.isPickupTypeToGo()) {
            togoln.setVisibility(View.INVISIBLE);
//            togospaceln.setVisibility(View.INVISIBLE);
        } else {
            togoln.setVisibility(View.VISIBLE);
//            togospaceln.setVisibility(View.VISIBLE);
        }

        if (!GlobalMemberValues.isPickupTypeDelivery()) {
            deliveryln.setVisibility(View.INVISIBLE);
        } else {
            deliveryln.setVisibility(View.VISIBLE);
        }

        // 수령방법이 2개 미만일 때...
        if (!GlobalMemberValues.isPhoneOrder()) {
            if (GlobalMemberValues.getCountPickupType() < 2) {
                exePopup();
            }
        } else {
            if (!GlobalMemberValues.isStrEmpty(mPayYN) && mPayYN.equals("Y")) {
                exePopup();
            } else if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
                exePopup();
            }
        }

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            togospaceln.setVisibility(View.GONE);
            deliveryln.setVisibility(View.GONE);
        }

        // Restaurant 관련
//        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
//        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
//            if (GlobalMemberValues.now_saletypeisrestaurant) {
//                openHereToGoInfoIntent("T");
//            }
//        }

        // phone order, kitchen printing 기본으로 체크해 놓을 것
        phoneorderCheckbox.setChecked(true);
        mCheckedPhoneorder = "N";
        checkprintCheckbox.setVisibility(View.VISIBLE);
        checkprintCheckbox.setChecked(true);
        mCheckedCheckPrint = "Y";

        // phone 오더가 아니고
        // 퀵 세일시 pick up 타입 팝업창 사용여부가 N (false) 일 경우
        // 테이블 주문인지 확인
        String tempGetType = "H";
        String tempQuickSaleyn = "N";
        String tempTableIdx = "";
        if (TableSaleMain.mSelectedTablesArrList.size() > 0) {
            int tempi = 0;
            for (int i = 0; i < TableSaleMain.mSelectedTablesArrList.size(); i++) {
                tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mSelectedTablesArrList.get(i), "T", "");
                tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                );
                if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                    tempi++;
                }
            }
            if (tempi > 0) {
                tempGetType = "T";
            }
        } else {
            tempGetType = "T";
        }

        if (!GlobalMemberValues.isHold) {
            String tempPhoneOrderNum = MssqlDatabase.getResultSetValueToString(
                    " select phoneordernumber from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' " +
                            " and not(phoneordernumber = '' or phoneordernumber is null) ");
            if (GlobalMemberValues.isStrEmpty(tempPhoneOrderNum)) {
                if (!GlobalMemberValues.isPickupSelectPopup()) {
                    GlobalMemberValues.logWrite("jjjtestlogjjj", "여기1" + "\n");

                    openHereToGoInfoIntent(tempGetType);
                }
            } else {
                GlobalMemberValues.logWrite("jjjtestlogjjj", "여기2" + "\n");

                mCheckedPhoneorder = "Y";
                openHereToGoInfoIntent(tempGetType);
            }
        } else {
            GlobalMemberValues.logWrite("jjjtestlogjjj", "여기3" + "\n");

            mCheckedPhoneorder = "Y";
            GlobalMemberValues.isHold = false;
            openHereToGoInfoIntent(tempGetType);
        }

    }

    private void exePopup() {
        if (GlobalMemberValues.getCountPickupType() == 0) {
            openHereToGoInfoIntent("H");
        } else {
            if (GlobalMemberValues.getCountPickupType() < 2) {
                if (GlobalMemberValues.isPickupTypeHere()) {
                    openHereToGoInfoIntent("H");
                }
                if (GlobalMemberValues.isPickupTypeToGo()) {
                    openHereToGoInfoIntent("T");
                }
                if (GlobalMemberValues.isPickupTypeDelivery()) {
                    openDeliveryInfoIntent();
                }
            }
        }
    }

    CheckBox.OnCheckedChangeListener checkedPhoneOrder = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.phoneorderCheckbox : {
                    if (isChecked) {
                        mCheckedPhoneorder = "Y";
                        checkprintCheckbox.setVisibility(View.VISIBLE);
                    } else {
                        mCheckedPhoneorder = "N";
                        checkprintCheckbox.setVisibility(View.INVISIBLE);
                    }
                    break;
                }
                case R.id.checkprintCheckbox : {
                    if (isChecked) {
                        mCheckedCheckPrint = "Y";
                    } else {
                        mCheckedCheckPrint = "N";
                    }
                    break;
                }
            }

        }
    };

    View.OnClickListener getFoodButtonClickLinstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.herebtn : {
                    openHereToGoInfoIntent("H");
                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        //jihun park sub display
                        PaxPresentation.is_logo_full = true;
                        MainActivity.updatePresentation();
                    }
                    break;
                }
                case R.id.togobtn : {
                    openHereToGoInfoIntent("T");

                    break;
                }
                case R.id.deliverybtn : {
                    openDeliveryInfoIntent();
                    break;
                }
                case R.id.ordertypeCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }
                    break;
                }
            }
        }
    };

    public void openDeliveryInfoIntent() {
        Intent deliverytakeawayIntent = new Intent(MainActivity.mContext.getApplicationContext(), DeliveryTakeawayInfo.class);
        GlobalMemberValues.logWrite("PayYNCCC", "mPayYN : " + mPayYN + "\n");
        deliverytakeawayIntent.putExtra("payyn", mPayYN);
        mActivity.startActivity(deliverytakeawayIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
        mActivity.finish();
    }

    public static void openHereToGoInfoIntent(String paramHereToGoType) {
        Intent hereToGoInfoIntent = new Intent(MainActivity.mContext.getApplicationContext(), HereToGoInfo.class);
        GlobalMemberValues.logWrite("heretogotypelog", "heretogotype : " + paramHereToGoType + "\n");
        hereToGoInfoIntent.putExtra("heretogotype", paramHereToGoType);
        mActivity.startActivity(hereToGoInfoIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
        mActivity.finish();
    }

    public static void insPickUpInfo(String paramGetFoodType, String paramCustomerId, String paramCustomerName, String paramCustomerEmail, String paramCustomerMemo) {

        Vector<String> mStrInsertQueryVec = new Vector<String>();

        String tempCustPhoneNum = "";
        String tempCustName = "";
        String tempCustEmail = paramCustomerEmail;

        if (!GlobalMemberValues.isStrEmpty(paramCustomerId)) {
            if (paramCustomerId.equals("Walk In")) {
                tempCustPhoneNum = "Walk In";
                tempCustName = paramCustomerName;
            } else {
                if (!GlobalMemberValues.isStrEmpty(paramCustomerName)) {
                    //tempCustPhoneNum = paramCustomerId;
                    if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null){
                        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone != null && !GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone)) {
                            tempCustPhoneNum = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                        } else {
                            tempCustPhoneNum = paramCustomerId;
                        }
                    } else {
                        tempCustPhoneNum = paramCustomerId;
                    }

                    tempCustName = paramCustomerName;
                }
            }
        } else {
            if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                tempCustPhoneNum = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                tempCustName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
            } else {
                tempCustPhoneNum = "Walk In";
                tempCustName = paramCustomerName;
            }
        }

        if (GlobalMemberValues.isStrEmpty(tempCustEmail)) {
            if (!GlobalMemberValues.isStrEmpty(tempCustPhoneNum)) {
                tempCustEmail = MssqlDatabase.getResultSetValueToString(" select email from member2 where uid = '" + paramCustomerId + "' ");
            }
        }

        GlobalMemberValues.logWrite(TAG, "tempCustName : " + tempCustName + "\n");

        String insCustomerPhoneNum = tempCustPhoneNum;
        String insCustomerName = tempCustName;
        String insCustomerEmail = tempCustEmail;
        String insDeliveryDay = "";
        String insDeliveryTime = "";
        String insAddr1 = "";
        String insAddr2 = "";
        String insCity = "";
        String insState = "";
        String insZip = "";
        String insCustomerMemo = paramCustomerMemo;

        String insUpdStrQuery = "";

        // 먼저 해당 holdcode 로 저장된 데이터가 있는지 확인한다.
        if (GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                "select count(idx) from temp_salecart_deliveryinfo where holdcode = '" + mGetHoldCode + "' ")) > 0) {
            insUpdStrQuery = "update temp_salecart_deliveryinfo set " +
                    " customerId = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " customerName = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "', " +
                    " customerPhone = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " customerEmail = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerEmail, 0) + "', " +
                    " customerAddr1 = '" + GlobalMemberValues.getDBTextAfterChecked(insAddr1, 0) + "', " +
                    " customerAddr2 = '" + GlobalMemberValues.getDBTextAfterChecked(insAddr2, 0) + "', " +
                    " customerCity = '" + GlobalMemberValues.getDBTextAfterChecked(insCity, 0) + "', " +
                    " customerState = '" + GlobalMemberValues.getDBTextAfterChecked(insState, 0) + "', " +
                    " customerZip = '" + GlobalMemberValues.getDBTextAfterChecked(insZip, 0) + "', " +
//                    " deliveryday = '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryDay, 0) + "', " +
//                    " deliverytime = '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryTime, 0) + "', " +
//                    " deliverydate = '" + insDeliveryDay + " " + insDeliveryTime + ":00" + "', " +
                    " deliverytakeaway = '" + GlobalMemberValues.getDBTextAfterChecked(paramGetFoodType, 0) + "', " +
                    " customermemo = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerMemo, 0) + "', " +
                    " phoneorder = '" + mCheckedPhoneorder + "' " +
                    " where holdcode = '" + mGetHoldCode + "' ";
        } else {
            insUpdStrQuery = " insert into temp_salecart_deliveryinfo ("+
                    " holdcode, sidx, stcode, " +
                    " customerId, customerName, customerPhone, customerEmail, " +
                    " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
//                    " deliveryday, deliverytime, deliverydate, " +
                    " deliverytakeaway, " +
                    " customermemo, phoneorder " +
                    " ) values ( " +
                    " '" + mGetHoldCode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +

                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerEmail, 0) + "', "
                    +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insAddr1, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insAddr2, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCity, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insState, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insZip, 0) + "', " +

//                    " '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryDay, 0) + "', " +
//                    " '" + GlobalMemberValues.getDBTextAfterChecked(insDeliveryTime, 0) + "', " +
//                    " '" + insDeliveryDay + " " + insDeliveryTime + ":00" + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(paramGetFoodType, 0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerMemo, 0) + "', " +
                    " '" + mCheckedPhoneorder + "' " +
                    ")";
        }
        mStrInsertQueryVec.addElement(insUpdStrQuery);

        insUpdStrQuery = " update temp_salecart set " +
                " customerId = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNum, 0) + "', " +
                " customerName = '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "' " +
                " where holdcode = '" + mGetHoldCode + "' ";
        mStrInsertQueryVec.addElement(insUpdStrQuery);

        for (String tempQuery : mStrInsertQueryVec) {
            GlobalMemberValues.logWrite(TAG, "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(mStrInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Waraning", "Database Error", "Close");
        } else {
            // 등록성공
            //GlobalMemberValues.displayDialog(context, "Information", "Successfully Registered", "Close");
            if (!GlobalMemberValues.isStrEmpty(mPayYN) && mPayYN.equals("Y")) {
                GlobalMemberValues.logWrite("jjjwanhayelogjjj", "여기에...1" + "\n");

                GlobalMemberValues.setFrameLayoutVisibleChange("payment");
                GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED = "Y";

                if (mPayYN == "Y" || mPayYN.equals("Y")) {
                    if (paramGetFoodType.equals("T")) {
                        // Pickup (Togo) 포장 비용이 있을 경우
                        double store_pickupcharge = GlobalMemberValues.getDoubleAtString(
                                MssqlDatabase.getResultSetValueToString("select pickupcharge from salon_storegeneral")
                        );
                        Payment.setDeliveryPickUpLn("ToGo(PickUp) Fee ", store_pickupcharge);
                    } else {
                        Payment.setDeliveryPickUpLn("ToGo(PickUp) Fee ", 0);
                    }
                } else {
                    Payment.setDeliveryPickUpLn("", 0);
                }

                // 결제창 상단 수령방법 기재 --------------------------------------------------------------
                if (Payment.paymentTopGetFoodTypeTv != null) {
                    if (!GlobalMemberValues.isStrEmpty(paramGetFoodType)) {
                        switch (paramGetFoodType) {
                            case "H" : {
                                Payment.paymentTopGetFoodTypeTv.setText("Here");
                                break;
                            }
                            case "T" : {
                                Payment.paymentTopGetFoodTypeTv.setText("To Go");
                                break;
                            }
                            case "D" : {
                                Payment.paymentTopGetFoodTypeTv.setText("Delivery");
                                break;
                            }
                        }
                    } else {
                        Payment.paymentTopGetFoodTypeTv.setText("");
                    }
                }
                // --------------------------------------------------------------------------------------

                // 06.03.2022 -----------------------------------------------------------------
                if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
                    if (GlobalMemberValues.isToGoSale()) {
                        Payment.paymentTopGetFoodTypeTv.setText("To Go");
                    } else {
                        Payment.paymentTopGetFoodTypeTv.setText("Here");
                    }
                }
                // ----------------------------------------------------------------------------


                // Payment 창에 금액 업데이트
                Payment.setPaymentPrice();

                GlobalMemberValues.setOrderListOnCloverDisplay(MainMiddleService.mHoldCode);

            } else {
                GlobalMemberValues.logWrite("jjjwanhayelogjjj", "여기에...2" + "\n");

                if (GlobalMemberValues.mIsClickSendToKitchen) {
                    //SelectGetFoodType.receiptKitchenPrinting1(mGetHoldCode);
                    GlobalMemberValues.setCustomerInfoInit();
                    MainMiddleService.clearOnlyListView();
                }

            }
            mActivity.finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }
    }

    public static void receiptKitchenPrinting1(String paramHoldCode) {
        if (SelectGetFoodType.mCheckedPhoneorder == "Y" || SelectGetFoodType.mCheckedPhoneorder.equals("Y")
                || SelectGetFoodType.mCheckedCheckPrint == "Y" || SelectGetFoodType.mCheckedCheckPrint.equals("Y")) {
            String tempStr = "";
            String tempPrintType = "K";         // A : 간이영수증&주방프린팅 모두           K : 주방프린터만              R : 간이영수증만
            if (SelectGetFoodType.mCheckedPhoneorder.equals("Y") && SelectGetFoodType.mCheckedCheckPrint.equals("Y")) {
                tempStr = "receipt and kitchen";
                tempPrintType = "A";
            } else {
                if (SelectGetFoodType.mCheckedPhoneorder.equals("Y")) {
                    tempStr = "kitchen";
                    tempPrintType = "K";
                }
                if (SelectGetFoodType.mCheckedCheckPrint.equals("Y")) {
                    tempStr = "receipt";
                    tempPrintType = "R";
                }
            }
            kitchenPrint(paramHoldCode, tempStr, tempPrintType);
        }
    }

    public static void kitchenPrint(final String paramHoldCode, String paramStr, final String paramPrintingType) {
        if (!GlobalMemberValues.isKitchenPrintedOnDeliveryInfo(paramHoldCode)) {
            // 현재 액티비티는 모두 닫히므로 context 를 반드시 MainActivity.mContext 로 해야 함.
            new AlertDialog.Builder(MainActivity.mContext)
                    .setTitle("Kitchen Printer")
                    .setMessage("Would you print the order list on " + paramStr + " printer?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        GlobalMemberValues.phoneorderPrinting(paramHoldCode, paramPrintingType, "");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (phoneorderCheckbox != null){
//            boolean b_temp = false;
//            if (GlobalMemberValues.getCountPickupType() == 0) {
//                b_temp = true;
//            } else {
//                if (GlobalMemberValues.getCountPickupType() < 2) {
//                    if (GlobalMemberValues.isPickupTypeHere()) {
//                        b_temp = true;
//                    }
//                    if (GlobalMemberValues.isPickupTypeToGo()) {
//                        b_temp = true;
//                    }
//                    if (GlobalMemberValues.isPickupTypeDelivery()) {
//                        b_temp = true;
//                    }
//                }
//            }
//
//            if (GlobalMemberValues.selectPhoneOrderGetFoodType()){
//                if (b_temp){
//
//                } else {
//                    phoneorderCheckbox.setEnabled(true);
//                    phoneorderCheckbox.setChecked(true);
//
//                    checkprintCheckbox.setEnabled(true);
//                    checkprintCheckbox.setChecked(true);
//                }
//            }
//        }

    }
}
