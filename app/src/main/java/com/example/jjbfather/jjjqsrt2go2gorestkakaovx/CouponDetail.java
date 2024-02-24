package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CouponDetail extends Activity {
    private static final String TAG = "CouponDetaillog";

    static Activity mActivity;
    Context mContext;

    public static int openCount = 0;

    private LinearLayout parentLn;

    public static Button couponDetailCloseBtn, couponDetailApplyButton;

    public static LinearLayout couponResultLn1, couponResultLn2;

    public static TextView couponDetailCouponNameTextView;
    public static TextView couponDetailCustomerIdTextView;
    public static TextView couponDetailCustomerNameTextView;
    public static TextView couponDetailCouponNumberTextView, couponDetailDiscountTextView;
    public static TextView couponDetailExpirationDateTextView, couponDetailApplyTypeTextView;
    public static TextView couponResultTextView;

    Intent mIntent;
    static String mCouponNumber = "";
    static String mCouponReturnValue = "";

    public static String mCouponCheckResult = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_coupon_detail);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 50;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 45;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 45;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.couponDetailLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            mCouponNumber = mIntent.getStringExtra("couponnumber");
            mCouponReturnValue = mIntent.getStringExtra("couponreturnvalue");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 coupon number : " + mCouponNumber + "\n");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 coupon return value : " + mCouponReturnValue + "\n");
            /*******************************************************************************************/
        } else {
            finish();
        }

        openCount++;

        setContents();
    }

    private void setContents() {
        mActivity = this;
        mContext = this;

        mCouponCheckResult = "N";

        Discount.setEmptyCouponNumber();

        couponResultLn1 = (LinearLayout) findViewById(R.id.couponResultLn1);
        couponResultLn2 = (LinearLayout) findViewById(R.id.couponResultLn2);

        couponDetailApplyButton = (Button) findViewById(R.id.couponDetailApplyButton);
        couponDetailApplyButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        couponDetailApplyButton.setOnClickListener(CouponDetailButtonClickListener);

        couponDetailCouponNameTextView = (TextView) findViewById(R.id.couponDetailCouponNameTextView);

        couponDetailCustomerIdTextView = (TextView) findViewById(R.id.couponDetailCustomerIdTextView);
        couponDetailCustomerNameTextView = (TextView) findViewById(R.id.couponDetailCustomerNameTextView);
        couponDetailCouponNumberTextView = (TextView) findViewById(R.id.couponDetailCouponNumberTextView);
        couponDetailDiscountTextView = (TextView) findViewById(R.id.couponDetailDiscountTextView);
        couponDetailExpirationDateTextView = (TextView) findViewById(R.id.couponDetailExpirationDateTextView);
        couponDetailApplyTypeTextView = (TextView) findViewById(R.id.couponDetailApplyTypeTextView);
        couponResultTextView = (TextView) findViewById(R.id.couponResultTextView);

        couponDetailCloseBtn = (Button)findViewById(R.id.couponDetailCloseBtn);
        couponDetailCloseBtn.setOnClickListener(CouponDetailButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            couponDetailCloseBtn.setText("");
            couponDetailCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            couponDetailCloseBtn.setTextSize(
                    couponDetailCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        //getCouponDetailValue(mCouponNumber);          // 이전에 사용하던 메소드
        getCouponDetailValueNew(mCouponNumber, mCouponReturnValue);
    }

    public static void getCouponDetailValueNew(String paramCouponNumber, String paramCouponReturnValue) {
        //GlobalMemberValues.displayDialog(context, "Warning", "333333", "Close");
        if (Discount.mCouponNumberVec.indexOf(paramCouponNumber) == -1) { // 현재 장바구니에 담기지 않은, 사용하지 않은 쿠폰일 경우
            // 실시간 쿠폰정보 체크
            APIDownLoad_coupon_check apidowncouponcheck = new APIDownLoad_coupon_check(paramCouponNumber);
            apidowncouponcheck.execute(null, null, null);
            try {
                Thread.sleep(3000); //1초마다 실행
                if (apidowncouponcheck.mFlag) {
                    paramCouponReturnValue = apidowncouponcheck.strReturn;
                    if (!GlobalMemberValues.isStrEmpty(paramCouponReturnValue)) {
                        GlobalMemberValues.logWrite("CouponDetailLog", paramCouponReturnValue + "\n");

                        String[] strCouponInfoArr = paramCouponReturnValue.split(GlobalMemberValues.STRSPLITTER1);
                        String offType = strCouponInfoArr[0];
                        String discountValue = strCouponInfoArr[1];
                        String startDate = strCouponInfoArr[2];
                        String endDate = strCouponInfoArr[3];
                        String usedyn = strCouponInfoArr[4];
                        String userid = strCouponInfoArr[5];
                        String username = strCouponInfoArr[6];
                        String coupon_name = strCouponInfoArr[7];
                        String barcode = strCouponInfoArr[8];
                        String applytype = strCouponInfoArr[9];

                        boolean isCouponPossible = false;
                        int noCouponInfoReason = 0;     // 1 : 쿠폰정보가 정확하지 않음,     2 : 쿠폰날짜 지났음,    3 : 이미 사용한 쿠폰

                        if (!GlobalMemberValues.isStrEmpty(offType) && !GlobalMemberValues.isStrEmpty(discountValue)
                                && !GlobalMemberValues.isStrEmpty(usedyn)) {

                            if (usedyn == "N" || usedyn.equals("N")) {
                                if (!GlobalMemberValues.isStrEmpty(startDate) && !GlobalMemberValues.isStrEmpty(endDate)) {
                                    if (startDate.length() > 10 && endDate.length() > 10) {
                                        String tempNowDate =
                                                DateMethodClass.nowYearGet() + "-" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet();
                                        startDate = startDate.substring(0, 10);
                                        endDate = endDate.substring(0, 10);

                                        long tempDiffDays1 = DateMethodClass.getDiffDaysOfDate(tempNowDate, startDate);
                                        long tempDiffDays2 = DateMethodClass.getDiffDaysOfDate(tempNowDate, endDate);

                                        if (tempDiffDays1 <= 0 && tempDiffDays2 >= 0) {
                                            isCouponPossible = true;
                                        } else {
                                            isCouponPossible = false;
                                            noCouponInfoReason = 2;     // 쿠폰사용가능 기간이 아님
                                        }
                                    } else {
                                        isCouponPossible = true;
                                    }
                                } else {
                                    isCouponPossible = true;
                                }
                            } else {
                                isCouponPossible = false;
                                noCouponInfoReason = 3;     // 3 : 이미 사용된 쿠폰
                            }

                        } else {
                            isCouponPossible = false;
                            noCouponInfoReason = 1;        // 1 : 쿠폰정보가 정확하지 않음 (정보누락)
                        }

                        if (isCouponPossible) {         // 검색결과 쿠폰이 사용가능할 때
                            mCouponCheckResult = "Y";
                            couponResultLn1.setVisibility(View.VISIBLE);
                            couponResultLn2.setVisibility(View.GONE);

                            couponDetailApplyButton.setText("Discount Apply");

                            //GlobalMemberValues.displayDialog(context, "Coupon Check", paramCouponReturnValue, "Close");
                            String tempRateValue = GlobalMemberValues.getStringFormatNumber(
                                    GlobalMemberValues.getDoubleAtString(discountValue), "2");
                            String tempOffType = offType + "A";
                            String tempDcStr = "";
                            if (tempOffType.equals("%A")) {
                                tempDcStr = tempRateValue + "%";
                            } else {
                                tempDcStr = "$" + tempRateValue;
                            }

                            if (GlobalMemberValues.isStrEmpty(applytype)) {
                                applytype = "B";
                            }

                            String strApplyType = "";
                            switch (applytype) {
                                case "B" : {
                                    strApplyType = "Food, Product";
                                    break;
                                }
                                case "S" : {
                                    strApplyType = "Food";
                                    break;
                                }
                                case "P" : {
                                    strApplyType = "Product";
                                    break;
                                }
                            }

                            coupon_name = GlobalMemberValues.setUniCodeStringReplace(coupon_name);

                            couponDetailCouponNameTextView.setText(coupon_name);
                            couponDetailCustomerIdTextView.setText(userid);
                            couponDetailCustomerNameTextView.setText(username);
                            couponDetailCouponNumberTextView.setText(barcode);
                            couponDetailDiscountTextView.setText(tempDcStr);
                            couponDetailExpirationDateTextView.setText(startDate + "~" + endDate);
                            couponDetailApplyTypeTextView.setText(strApplyType);

                            if (!GlobalMemberValues.isCouponInfoView()) {
                                openCount = 0;
                                setCouponDiscount();
                            }

                        } else {                        // 검색결과 쿠폰사용이 안될 때
                            if (noCouponInfoReason > 0) {
                                String dialogStr = "";
                                switch (noCouponInfoReason) {
                                    case 1 : {
                                        dialogStr = "No Coupon Information [4]";
                                        break;
                                    }
                                    case 2 : {
                                        dialogStr = "This coupon is invalid or this coupon has expired";
                                        break;
                                    }
                                    case 3 : {
                                        dialogStr = "Used Coupon";
                                        break;
                                    }
                                    default : {
                                        dialogStr = "No Coupon Information [5]";
                                        break;
                                    }
                                }
                                setCouponResultTextView(dialogStr, "99");
                                dismissProDial();
                                //GlobalMemberValues.displayDialog(paramContext, "Coupon Check", dialogStr, "Close");
                                return;
                            }
                        }
                    } else {
                        setCouponResultTextView("No Coupon Information [6]", "99");
                        dismissProDial();
                        //GlobalMemberValues.displayDialog(paramContext, "Coupon Check", "No Coupon Information", "Close");
                        return;
                    }
                }
            } catch (InterruptedException e) {
                setCouponResultTextView("Thread Error : " + e.getMessage(), "99");
                //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
            }
        } else {                                                        // 사용된 쿠폰일 경우
            setCouponResultTextView("Used Coupon", "99");
            /**
             if (discountCouponYNSwitch.isChecked()) {
             GlobalMemberValues.displayDialog(paramContext, "Warning", "Used Coupon", "Close");
             }
             **/
        }
        dismissProDial();
    }

    public static void setCouponResultTextView(String paramString, String paramResult) {
        if (!paramResult.equals("00")) {
            mCouponCheckResult = "N";
            couponDetailApplyButton.setText("Close");

            couponDetailCouponNameTextView.setText("Invalid Coupon");

            couponResultLn1.setVisibility(View.GONE);
            couponResultLn2.setVisibility(View.VISIBLE);
            couponResultTextView.setText(paramString);
        }

    }

    View.OnClickListener CouponDetailButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.couponDetailApplyButton : {
                    openCount = 0;
                    if (mCouponCheckResult.equals("Y") || mCouponCheckResult == "Y") {
                        setCouponDiscount();
                    } else {
                        mActivity.finish();
                    }
                    break;
                }
                case R.id.couponDetailCloseBtn : {
                    openCount = 0;
                    mActivity.finish();
                    break;
                }
            }
        }
    };

    public static void dismissProDial() {
        // 프로그래스바를 사라지게 함 -------------------------------------------------------------
        /**
        if (Discount.checkingProDial != null) {
            Discount.checkingProDial.dismiss();
        }
         **/
        if (Discount.checkingProDial !=null && Discount.checkingProDial.isShowing()) {
            Discount.checkingProDial.cancel();
        }
        // -------------------------------------------------------------------------------------
    }

    public static void setCouponDiscount() {
        Discount.couponNumberEtInputType = "P";
        Discount.discountCouponNumberEditText.setText(mCouponNumber);
        Discount.setDiscountAfterCheckedCoupon(mCouponNumber, mCouponReturnValue, MainActivity.mContext);
        mActivity.finish();
    }

    // 사용안하는 메소드
    public static void getCouponDetailValue(String paramCouponNumber) {
        //GlobalMemberValues.displayDialog(context, "Warning", "333333", "Close");
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {       // 인터넷연결 체크
            if (!GlobalMemberValues.isOnline().equals("00")) {
                // 인터넷 미연결...

            } else {
                // 쿠폰번호(barcode)가 14 자리 일경우
                if (paramCouponNumber.length() > 3) {
                    // 쿠폰정보 받아서 저장할 객체
                    String tempReturnCouponInformation = "";
                    if (Discount.mCouponNumberVec.indexOf(paramCouponNumber) == -1) { // 현재 장바구니에 담기지 않은, 사용하지 않은 쿠폰일 경우
                        // 실시간 쿠폰정보 체크
                        APIDownLoad_coupon_check apidowncouponcheck = new APIDownLoad_coupon_check(paramCouponNumber);
                        apidowncouponcheck.execute(null, null, null);
                        try {
                            Thread.sleep(3000); //1초마다 실행
                            if (apidowncouponcheck.mFlag) {
                                tempReturnCouponInformation = apidowncouponcheck.strReturn;
                                if (!GlobalMemberValues.isStrEmpty(tempReturnCouponInformation)) {
                                    GlobalMemberValues.logWrite("CouponDetailLog", tempReturnCouponInformation + "\n");

                                    String[] strCouponInfoArr = tempReturnCouponInformation.split(GlobalMemberValues.STRSPLITTER1);
                                    String offType = strCouponInfoArr[0];
                                    String discountValue = strCouponInfoArr[1];
                                    String startDate = strCouponInfoArr[2];
                                    String endDate = strCouponInfoArr[3];
                                    String usedyn = strCouponInfoArr[4];
                                    String userid = strCouponInfoArr[5];
                                    String username = strCouponInfoArr[6];
                                    String coupon_name = strCouponInfoArr[7];
                                    String barcode = strCouponInfoArr[8];
                                    String applytype = strCouponInfoArr[9];

                                    boolean isCouponPossible = false;
                                    int noCouponInfoReason = 0;     // 1 : 쿠폰정보가 정확하지 않음,     2 : 쿠폰날짜 지났음,    3 : 이미 사용한 쿠폰

                                    if (!GlobalMemberValues.isStrEmpty(offType) && !GlobalMemberValues.isStrEmpty(discountValue)
                                            && !GlobalMemberValues.isStrEmpty(usedyn)) {

                                        if (usedyn == "N" || usedyn.equals("N")) {
                                            if (!GlobalMemberValues.isStrEmpty(startDate) && !GlobalMemberValues.isStrEmpty(endDate)) {
                                                if (startDate.length() > 10 && endDate.length() > 10) {
                                                    String tempNowDate =
                                                            DateMethodClass.nowYearGet() + "-" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet();
                                                    startDate = startDate.substring(0, 10);
                                                    endDate = endDate.substring(0, 10);

                                                    long tempDiffDays1 = DateMethodClass.getDiffDaysOfDate(tempNowDate, startDate);
                                                    long tempDiffDays2 = DateMethodClass.getDiffDaysOfDate(tempNowDate, endDate);

                                                    if (tempDiffDays1 <= 0 && tempDiffDays2 >= 0) {
                                                        isCouponPossible = true;
                                                    } else {
                                                        isCouponPossible = false;
                                                        noCouponInfoReason = 2;     // 쿠폰사용가능 기간이 아님
                                                    }
                                                } else {
                                                    isCouponPossible = true;
                                                }
                                            } else {
                                                isCouponPossible = true;
                                            }
                                        } else {
                                            isCouponPossible = false;
                                            noCouponInfoReason = 3;     // 3 : 이미 사용된 쿠폰
                                        }

                                    } else {
                                        isCouponPossible = false;
                                        noCouponInfoReason = 1;        // 1 : 쿠폰정보가 정확하지 않음 (정보누락)
                                    }

                                    if (isCouponPossible) {         // 검색결과 쿠폰이 사용가능할 때
                                        mCouponCheckResult = "Y";
                                        couponResultLn1.setVisibility(View.VISIBLE);
                                        couponResultLn2.setVisibility(View.GONE);

                                        couponDetailApplyButton.setText("Discount Apply");

                                        //GlobalMemberValues.displayDialog(context, "Coupon Check", tempReturnCouponInformation, "Close");
                                        String tempRateValue = GlobalMemberValues.getStringFormatNumber(
                                                GlobalMemberValues.getDoubleAtString(discountValue), "2");
                                        String tempOffType = offType + "A";
                                        String tempDcStr = "";
                                        if (tempOffType.equals("%A")) {
                                            tempDcStr = tempRateValue + "%";
                                        } else {
                                            tempDcStr = "$" + tempRateValue;
                                        }

                                        if (GlobalMemberValues.isStrEmpty(applytype)) {
                                            applytype = "B";
                                        }

                                        String strApplyType = "";
                                        switch (applytype) {
                                            case "B" : {
                                                strApplyType = "Food, Product";
                                                break;
                                            }
                                            case "S" : {
                                                strApplyType = "Food";
                                                break;
                                            }
                                            case "P" : {
                                                strApplyType = "Product";
                                                break;
                                            }
                                        }

                                        couponDetailCouponNameTextView.setText(coupon_name);
                                        couponDetailCustomerIdTextView.setText(userid);
                                        couponDetailCustomerNameTextView.setText(username);
                                        couponDetailCouponNumberTextView.setText(barcode);
                                        couponDetailDiscountTextView.setText(tempDcStr);
                                        couponDetailExpirationDateTextView.setText(startDate + "~" + endDate);
                                        couponDetailApplyTypeTextView.setText(strApplyType);

                                        if (!GlobalMemberValues.isCouponInfoView()) {
                                            openCount = 0;
                                            setCouponDiscount();
                                        }

                                    } else {                        // 검색결과 쿠폰사용이 안될 때
                                        if (noCouponInfoReason > 0) {
                                            String dialogStr = "";
                                            switch (noCouponInfoReason) {
                                                case 1 : {
                                                    dialogStr = "No Coupon Information";
                                                    break;
                                                }
                                                case 2 : {
                                                    dialogStr = "This coupon is invalid or this coupon has expired";
                                                    break;
                                                }
                                                case 3 : {
                                                    dialogStr = "Used Coupon";
                                                    break;
                                                }
                                                default : {
                                                    dialogStr = "No Coupon Information";
                                                    break;
                                                }
                                            }
                                            setCouponResultTextView(dialogStr, "99");
                                            dismissProDial();
                                            //GlobalMemberValues.displayDialog(paramContext, "Coupon Check", dialogStr, "Close");
                                            return;
                                        }
                                    }
                                } else {
                                    setCouponResultTextView("No Coupon Information", "99");
                                    dismissProDial();
                                    //GlobalMemberValues.displayDialog(paramContext, "Coupon Check", "No Coupon Information", "Close");
                                    return;
                                }
                            }
                        } catch (InterruptedException e) {
                            setCouponResultTextView("Thread Error : " + e.getMessage(), "99");
                            //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                        }
                    } else {                                                        // 사용된 쿠폰일 경우
                        setCouponResultTextView("Used Coupon", "99");
                        /**
                         if (discountCouponYNSwitch.isChecked()) {
                         GlobalMemberValues.displayDialog(paramContext, "Warning", "Used Coupon", "Close");
                         }
                         **/
                    }
                }
            }
        } else {
            setCouponResultTextView("Network is not connected internet", "99");
            /**
             if (discountCouponYNSwitch.isChecked()) {
             //GlobalMemberValues.displayDialog(context, "Warning", "Network is not connected internet", "Close");
             GlobalMemberValues.openNetworkNotConnected();
             }
             **/
        }

        dismissProDial();
    }
}