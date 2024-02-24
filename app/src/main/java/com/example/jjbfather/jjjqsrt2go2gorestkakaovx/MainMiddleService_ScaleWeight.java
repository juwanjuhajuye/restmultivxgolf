package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner; 
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class MainMiddleService_ScaleWeight extends Activity {
    final String TAG = "PaymentCalculatorLog";

    static Activity mActivity;
    static Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    Intent mIntent;
    static String mServiceIdx = "";

    GetDataAtSQLite dataAtSqlite;

    public static LinearLayout readyLn, weightLn;

    private TextView itemnametv;
    public static TextView itemprice, itemweighttv, infoTv, itempriceperweight, itemscaleunittv;

    private ImageButton weightcancelbtn;
    public static Button weightorderbtn;

    private static CheckBox notarecheck;

    static double mItemPrice = 0;
    static double mTareTotalWeight = 0;
    static double mTareTotalWeight_org = 0;
    static double mPerWeight = 0;

    static String mScaleunit = "oz";
    static String mPricePerWeight = "";

    static boolean isWeightItemOnScale = false;

    static double mItemPureWeight = 0;

    static String optionTxtScale = "";

    static double mRealItemPrice = 0;

    private String getModifierCode = "";

    public static double preScaleWeight = 0.0;

    public static String mNoTareYN = "N";

    // 092022
    private Spinner tareqtySpinner;
    ArrayList<String> mGeneralArrayListForTareqtySpinner;
    ArrayAdapter<String> mSpinnerAdapterTareQty;
    public static int mSelectedTareCnt = 0;
    public static double mTareweight = 0;

    // 102022
    public static String mAutoWeigh = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_mainmiddleservice_scale_weight);
        this.setFinishOnTouchOutside(false);


        mActivity = this;
        mContext = this;        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(mContext);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mServiceIdx = mIntent.getStringExtra("ServiceIdx");
            /*******************************************************************************************/
        } else {
            closeActivity();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // 메뉴버튼 활성화
        if (MainMiddleService.preClickButton != null) {
            MainMiddleService.preClickButton.setEnabled(true);
        }

        // 무게를 가져오는 소숫점 자리 설정
        String scaledecimaltwiceyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select scaledecimaltwiceyn from salon_storegeneral "
        );
        if (GlobalMemberValues.isStrEmpty(scaledecimaltwiceyn)) {
            scaledecimaltwiceyn = "N";
        }
        if (scaledecimaltwiceyn == "Y" || scaledecimaltwiceyn.equals("Y")) {
            GlobalMemberValues.is_decimalpoint_twice = true;
        } else {
            GlobalMemberValues.is_decimalpoint_twice = false;
        }

        mNoTareYN = "N";

        readyLn = (LinearLayout) findViewById(R.id.readyLn);
        readyLn.setVisibility(View.VISIBLE);

        weightLn = (LinearLayout) findViewById(R.id.weightLn);
        weightLn.setVisibility(View.GONE);


        itemnametv = (TextView) findViewById(R.id.itemnametv);
        itemnametv.setTextSize(itemnametv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        infoTv = (TextView) findViewById(R.id.infoTv);
        infoTv.setTextSize(infoTv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        itemprice = (TextView) findViewById(R.id.itemprice);
        itemprice.setTextSize(itemprice.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        itempriceperweight = (TextView) findViewById(R.id.itempriceperweight);
        itempriceperweight.setTextSize(itempriceperweight.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        itemweighttv = (TextView) findViewById(R.id.itemweighttv);
        itemweighttv.setTextSize(itemweighttv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        itemscaleunittv = (TextView) findViewById(R.id.itemscaleunittv);
        itemscaleunittv.setTextSize(itemscaleunittv.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        weightcancelbtn = (ImageButton) findViewById(R.id.weightcancelbtn);
//        weightcancelbtn.setTextSize(weightcancelbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        weightcancelbtn.setOnClickListener(mButtonClick);

        weightorderbtn = (Button) findViewById(R.id.weightorderbtn);
        weightorderbtn.setTextSize(weightorderbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        weightorderbtn.setOnClickListener(mButtonClick);

        notarecheck = (CheckBox)findViewById(R.id.notarecheck);
        notarecheck.setTextSize(notarecheck.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        notarecheck.setOnCheckedChangeListener(scaleChkChangedListener);



        // 092022
        // Tare Qty 선택 --------------------------------------------------------------------------------------------
        int temp_tareqty = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select tareqty from salon_storeservice_sub where idx = '" + mServiceIdx + "' "));
        double temp_taretotalweight = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select taretotalweight from salon_storeservice_sub where idx = '" + mServiceIdx + "' "));

        tareqtySpinner = (Spinner)findViewById(R.id.tareqtySpinner);

        if (temp_taretotalweight > 0) {
            String tempEa = "";
            mGeneralArrayListForTareqtySpinner = new ArrayList<String>();
            for (int i = 0; i <= temp_tareqty; i++) {
                if (i == 0) {
                    tempEa = "No Tare";
                } else {
                    tempEa = "Tare " + i + "ea";
                }

                mGeneralArrayListForTareqtySpinner.add(tempEa);
            }
            mSpinnerAdapterTareQty = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mGeneralArrayListForTareqtySpinner);
            mSpinnerAdapterTareQty.setDropDownViewResource(R.layout.scale_spiiner);
            tareqtySpinner.setAdapter(mSpinnerAdapterTareQty);

            tareqtySpinner.setOnItemSelectedListener(tareqtySpinnerItemSelectedListener);

            if (temp_tareqty > 0) {
                mSelectedTareCnt = 1;
                tareqtySpinner.setSelection(1);
            } else {
                mSelectedTareCnt = 0;
                tareqtySpinner.setSelection(0);
            }
        } else {
            tareqtySpinner.setVisibility(View.GONE);
        }
        // ---------------------------------------------------------------------------------------------------------



        initData();

        String strQuery = "select midx, servicename, service_price, subServiceTime, " +
                " pointratio, saleprice, salestart, saleend, strFilePath, servicename2, servicename3, " +
                " tareidx, tareweight, tareqty, taretotalweight, perweight " +
                " from salon_storeservice_sub " +
                " where idx = '" + mServiceIdx + "' ";
        GlobalMemberValues.logWrite(TAG, "Query : " + strQuery + "\n");
        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String tempMidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
            String tempServiceName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
            String tempServicePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
            String tempServiceTime = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1);
            String tempPointRatio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
            String tempSalePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
            String tempSaleStart = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1);
            String tempSaleEnd = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1);
            String tempStrFilePath = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1);
            String tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1);
            String tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1);

            String tempTareIdx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(11), 1);
            String tempTareWeight = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
            String tempTareQty = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13), 1);
            String tempTareTotalWeight = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(14), 1);
            String tempPerWeight = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(15), 1);

            // 092022
            mTareweight = GlobalMemberValues.getDoubleAtString(tempTareWeight);


            mItemPrice = GlobalMemberValues.getDoubleAtString(tempServicePrice);

            if (!GlobalMemberValues.isStrEmpty(tempTareTotalWeight)) {
                mTareTotalWeight = GlobalMemberValues.getDoubleAtString(tempTareTotalWeight);
                mTareTotalWeight_org = mTareTotalWeight;

                // 092022
                mTareTotalWeight = 0;
                mTareTotalWeight_org = 0;
            } else {
                mTareTotalWeight = 0;
                mTareTotalWeight_org = 0;
            }

            if (!GlobalMemberValues.isStrEmpty(tempPerWeight)) {
                mPerWeight = GlobalMemberValues.getDoubleAtString(tempPerWeight);
            } else {
                mPerWeight = 1;
            }

            String mPerWeightStr = "";
            if (mPerWeight > 1) {
                mPerWeightStr = mPerWeight + "";
            } else {
                mPerWeightStr = "";
            }

            String tempServiceNameFull = tempServiceName;
            if (!GlobalMemberValues.isStrEmpty(tempServiceName2)) {
                tempServiceNameFull += " " + tempServiceName2;
            }
            if (!GlobalMemberValues.isStrEmpty(tempServiceName3)) {
                tempServiceNameFull += " " + tempServiceName3;
            }

            itemnametv.setText(tempServiceNameFull);
            itemprice.setText("$" + GlobalMemberValues.getStringFormatNumber(tempServicePrice, "2"));

            mScaleunit = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select scaleunit from salon_storegeneral "
            );
            if (GlobalMemberValues.isStrEmpty(mScaleunit)) {
                mScaleunit = "oz";
            }
            mScaleunit = mScaleunit.toLowerCase();

            itemscaleunittv.setText(mScaleunit);

            mPricePerWeight = " @ " + "$" + GlobalMemberValues.getStringFormatNumber(tempServicePrice, "2") + "/" + mPerWeightStr + mScaleunit + "";

            itempriceperweight.setText(mPricePerWeight);
        }

        // 102022
        // auto weigh 일 경우
        mAutoWeigh = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select scaleautoweighyn from salon_storegeneral ");
        if (GlobalMemberValues.isStrEmpty(mAutoWeigh)) {
            mAutoWeigh = "N";
        }
        if (mAutoWeigh == "Y" || mAutoWeigh.equals("Y")) {
            MainActivity.mUsbReceiver.writeDataToSerial(MainActivity.scaleTypeStr);

            parentLn.setVisibility(View.GONE);
        }
    }


    // 092022
    AdapterView.OnItemSelectedListener tareqtySpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mSelectedTareCnt = position;
            GlobalMemberValues.logWrite("tarecntlogjjj", "mSelectedTareCnt : " + mSelectedTareCnt + "\n");

            String tempValue = itemweighttv.getText().toString();

            if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                if (mSelectedTareCnt == 0) {
                    calOnScaleWeight(tempValue, "N", "N");
                } else {
                    calOnScaleWeight(tempValue, "N", "Y");
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.weightcancelbtn : {
                    closeActivity();
                    break;
                }

                case R.id.weightorderbtn : {
                    GlobalMemberValues.logWrite("jjjscaleweightlog", "이전 무게 : " + preScaleWeight + "\n");

                    MainActivity.mUsbReceiver.writeDataToSerial(MainActivity.scaleTypeStr);

                    break;
                }

            }
        }
    };


    public static void addItemToCart() {
        GlobalMemberValues.logWrite("jjjscaleweightlog2", "담기전 무게 : " + itemweighttv.getText().toString() + "\n");


        //String mOptionTxt = mItemPureWeight + " OZ";
        String mOptionTxt = optionTxtScale + " " + mPricePerWeight;

        // 신규 등록
        MainMiddleService.mModifierCode = "";
        MainMiddleService.mModifierIdx = "";
        MainMiddleService.mOptionTxt = mOptionTxt;
        MainMiddleService.mOptionPrice = 0.0 + "";
        MainMiddleService.mAdditionalTxt1 = "";
        MainMiddleService.mAdditionalprice1 = "";
        MainMiddleService.mAdditionalTxt2 = "";
        MainMiddleService.mAdditionalprice2 = "";

        MainMiddleService.mMemoToKitchen = "";

        String tempQty = "1";
        makeTouchedArr(tempQty);
        MainMiddleService.animServices(MainMiddleService.mTouchedView, MainMiddleService.mTouchedArr);

        closeActivity();
    }

    public static void makeTouchedArr(String paramQty) {
        String sQty = paramQty;
        String sHoldCode = MainMiddleService.mHoldCode;

        String strQuery = "select a.idx, a.midx, a.servicename, a.positionNo, a.strFileName, a.strFilePath, " +
                " a.service_price, a.commissionratioType, a.commissionratio, a.pointratio, " +
                " a.saleprice, a.salestart, a.saleend, a.activeyn,  " +
                " b.servicename as castegoryName, a.servicename2, a.servicename3, a.colorNo " +
                " from salon_storeservice_sub as a " +
                " left outer join salon_storeservice_main as b on a.midx = b.idx " +
                " where a.delyn = 'N' " +
                " and a.idx = '" + mServiceIdx + "' ";
        Cursor serviceCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (serviceCursor.getCount() > 0 && serviceCursor.moveToFirst()) {
            String tempSvcIdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(0), 1);
            String tempSvcMdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(1), 1);
            String tempSvcServiceName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(2), 1);
            String tempSvcStrFileName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(4), 1);
            String tempSvcStrFilePath = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(5), 1);
            String tempSvcServicePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(6), 1);
            String tempSvcSalePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(10), 1);
            String tempSvcSaleStart = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(11), 1);
            String tempSvcSaleEnd = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(12), 1);
            String tempSvcCommissionRatio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(8), 1);
            String tempSvcCommissionRatioType = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(7), 1);
            String tempSvcPointRatio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(9), 1);
            String tempSvcPositionNo = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(3), 1);
            String tempSvcSetMenuYN = "N";

            String tempSvcCategoryName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(14), 1);

            String tempSvcCategoryColorNo = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select colorNo from salon_storeservice_main where idx = '" + tempSvcMdx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(tempSvcCategoryColorNo)) {
                tempSvcCategoryColorNo = "1";
            }
            String tempSvcCategoryColor = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(tempSvcCategoryColorNo) - 1];

            String tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(15), 1);
            String tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(16), 1);
            if (!GlobalMemberValues.isStrEmpty(tempServiceName2)) {
                tempSvcServiceName += "\n" + tempServiceName2;
            }
            if (!GlobalMemberValues.isStrEmpty(tempServiceName3)) {
                tempSvcServiceName += "\n" + tempServiceName3;
            }

            String sCustomerId = MainMiddleService.insCustomerId;
            String sCustomerName = MainMiddleService.insCustomerName;
            String sCustomerPhone = MainMiddleService.insCustomerPhone;

            // 직원정보 구하기
            String sEmpIdx = MainMiddleService.insEmpIdx;
            String sEmpName = MainMiddleService.insEmpName;

            String paramsString[] = {
                    sQty, sHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, tempSvcMdx, tempSvcIdx,
                    tempSvcServiceName, tempSvcStrFileName, tempSvcStrFilePath,
                    (mRealItemPrice + ""), (mRealItemPrice + ""), tempSvcSaleStart, tempSvcSaleEnd,
                    tempSvcCommissionRatio, tempSvcCommissionRatioType, tempSvcPointRatio,
                    tempSvcPositionNo, tempSvcSetMenuYN,
                    sCustomerId, sCustomerName, sCustomerPhone, "0", sEmpIdx, sEmpName, "N",
                    tempSvcCategoryName, "", "", "", tempSvcCategoryColor
            };

            MainMiddleService.mTouchedArr = paramsString;
        }
        serviceCursor.close();

    }

    public static void calOnScaleWeight(String paramWeight, String paramAddOrderYN, String paramNoTareYN) {
        if (!GlobalMemberValues.isStrEmpty(paramWeight)) {
            double itemWeightDbl = GlobalMemberValues.getDoubleAtString(paramWeight);

            if (itemWeightDbl > GlobalMemberValues.SCALE_MAXWEIGHT || itemWeightDbl < GlobalMemberValues.SCALE_MINWEIGHT) {


                // 102022
                if (mAutoWeigh == "Y" || mAutoWeigh.equals("Y")) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Warning")
                            .setMessage("Out of weight range")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Close",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            closeActivity();
                                        }
                                    })
                            .show();

                } else {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Out of weight range", "Close");
                }


                return;
            }

            GlobalMemberValues.logWrite("jjjcalweight", "paramNoTareYN : " + paramNoTareYN + "\n");

            if (paramNoTareYN == "Y" || paramNoTareYN.equals("Y")) {
                mTareTotalWeight = 0;
            } else {
                mTareTotalWeight = mTareTotalWeight_org;
            }

            // 092022
            mTareTotalWeight = mTareweight * mSelectedTareCnt;

            GlobalMemberValues.logWrite("jjjcalweight", "mTareTotalWeight : " + mTareTotalWeight + "\n");

            // 실제 아이템 무게값 가져오기
            mItemPureWeight = itemWeightDbl - mTareTotalWeight;

            if (mItemPureWeight > 0) {
                readyLn.setVisibility(View.GONE);
                weightLn.setVisibility(View.VISIBLE);

                //GlobalMemberValues.logWrite("weightlog", "mItemPureWeight1 : " + mItemPureWeight + "\n");

                // 소수점 두자리까지
                mItemPureWeight = Math.round(mItemPureWeight * 100) / 100.0;

                //GlobalMemberValues.logWrite("weightlog", "mItemPureWeight2 : " + mItemPureWeight + "\n");

                //GlobalMemberValues.logWrite("weightlog", "GlobalMemberValues.getCommaStringForDouble((mItemPureWeight) : " + GlobalMemberValues.getCommaStringForDouble((mItemPureWeight) + "") + "\n");

                // 전체 무게값을 보여주는 경우
                itemweighttv.setText(paramWeight);
//                itemweighttv.setText(GlobalMemberValues.getCommaStringForDouble((itemWeightDbl) + "") + "");

                // 전체 무게에서 Tare 무게값을 뺀 값을 보여주는 경우
                //itemweighttv.setText(GlobalMemberValues.getCommaStringForDouble((mItemPureWeight) + "") + "");

                weightorderbtn.setBackgroundResource(R.drawable.button_selector_addtoorder);
                weightorderbtn.setText("Add to Order");

                //GlobalMemberValues.logWrite("weightlog", "mPerWeight : " + mPerWeight + "\n");

                // 계산해야할 무게가져오기
                double realCalWeight = (double)mItemPureWeight / (double)mPerWeight;

                //GlobalMemberValues.logWrite("weightlog", "realCalWeight : " + realCalWeight + "\n");

                mRealItemPrice = mItemPrice * realCalWeight;

                if (mItemPrice >= 0 || realCalWeight >= 0){
                    double temp_mItemPrice = mItemPrice * 100;
                    double temp_realCalWeight = realCalWeight * 100;
                    double temp_mRealItemPrice = temp_mItemPrice * temp_realCalWeight;
                    mRealItemPrice = Double.parseDouble(String.format("%.2f",temp_mRealItemPrice/10000));
                }

                //GlobalMemberValues.logWrite("weightlog", "mRealItemPrice : " + mRealItemPrice + "\n");

                itemprice.setText("$" + GlobalMemberValues.getCommaStringForDouble((mRealItemPrice) + ""));

                //GlobalMemberValues.logWrite("weightlog", "GlobalMemberValues.getCommaStringForDouble((mRealItemPrice) : " + GlobalMemberValues.getCommaStringForDouble((mRealItemPrice) + "") + "\n");

                if (GlobalMemberValues.is_decimalpoint_twice) {// .0 .00 분기
                    optionTxtScale = "TOTAL(" + GlobalMemberValues.getCommaStringForDouble((itemWeightDbl) + "") + "oz)" +
                            " - TARE(" + GlobalMemberValues.getCommaStringForDouble(mTareTotalWeight + "") + "oz)" +
                            " = " + GlobalMemberValues.getCommaStringForDouble((mItemPureWeight) + "") + "oz";
                } else {
                    optionTxtScale = "TOTAL(" + GlobalMemberValues.getCommaStringForDouble_forScale((itemWeightDbl) + "") + "oz)" +
                            " - TARE(" + GlobalMemberValues.getCommaStringForDouble_forScale(mTareTotalWeight + "") + "oz)" +
                            " = " + GlobalMemberValues.getCommaStringForDouble_forScale((mItemPureWeight) + "") + "oz";
                }

                // 저울에 무게를 측량한 이후로 처리
                isWeightItemOnScale = true;

                if (preScaleWeight > 0) {
                    if (preScaleWeight != itemWeightDbl) {
                        // 측량한 값을 가져와서 담아놓는다.
                        preScaleWeight = itemWeightDbl;
                        GlobalMemberValues.logWrite("jjjscaleweightlog2", "여기실행1 : " + itemweighttv.getText().toString() + "\n");
                    } else {
                        GlobalMemberValues.logWrite("jjjscaleweightlog2", "여기실행2 : " + itemweighttv.getText().toString() + "\n");
                        if (paramAddOrderYN == "Y" || paramAddOrderYN.equals("Y")) {
                            addItemToCart();
                        }
                    }
                } else {
                    GlobalMemberValues.logWrite("jjjscaleweightlog2", "여기실행3 : " + itemweighttv.getText().toString() + "\n");
                    preScaleWeight = itemWeightDbl;
                }

                // Serial 통신 끊기
                //GlobalMemberValues.jjjCloseUsbSerial();




                // 102022
                // auto weigh 일 경우
//                GlobalMemberValues.logWrite("jjjlogautoweighlog", "scaleautoweighyn : " + mAutoWeigh + "\n");
                if (mAutoWeigh == "Y" || mAutoWeigh.equals("Y")) {
                    addItemToCart();
                }



            } else {


                // 102022
                if (mAutoWeigh == "Y" || mAutoWeigh.equals("Y")) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Scale Info")
                            .setMessage("Nothing on the scale or the tare weight is heavier than the item weight")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Close",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            closeActivity();
                                        }
                                    })
                            .show();

                } else {
                    GlobalMemberValues.displayDialog(mContext, "Scale Info", "Nothing on the scale or the tare weight is heavier than the item weight", "Close");
                }



                /**
                 infoTv.setTextColor(Color.parseColor("#eb1010"));
                try {
                    Thread.sleep(1500); //1초마다 실행
                } catch (InterruptedException e) {
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
                infoTv.setTextColor(Color.parseColor("#0c69f5"));
                 **/

            }
        } else {


            // 102022
            if (mAutoWeigh == "Y" || mAutoWeigh.equals("Y")) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Warning")
                        .setMessage("The scale is not fixed or the weighing item is not fixed")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        closeActivity();
                                    }
                                })
                        .show();

            } else {
                GlobalMemberValues.displayDialog(mContext, "Warning", "The scale is not fixed or the weighing item is not fixed", "Close");
            }


        }
    }

    CheckBox.OnCheckedChangeListener scaleChkChangedListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String tempValue = itemweighttv.getText().toString();
            if (isChecked) {
                mNoTareYN = "Y";
                calOnScaleWeight(tempValue, "N", "Y");
            } else {
                mNoTareYN = "N";
                calOnScaleWeight(tempValue, "N", "N");
            }
        }
    };


    private static void closeActivity() {
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    public static void initData() {
        mItemPrice = 0;
        mTareTotalWeight = 0;
        mPerWeight = 0;

        mItemPureWeight = 0;

        mRealItemPrice = 0;

        preScaleWeight = 0;

        isWeightItemOnScale = false;

        itemweighttv.setText("");
        itemprice.setText("");

        notarecheck.setChecked(false);
    }


}
