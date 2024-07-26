package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class Discount {
    static Activity mActivity;
    static Context context;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    static DatabaseInit dbInit;

    // 쿠폰번호
    static String mCouponNumberTextValue = "";

    // Rate 값
    static String mRateEditTextValue = "";

    // 쿠폰번호
    static String mCouponNumber = "";

    // 고객 및 직원정보 관련 객체 선언
    static String insCustomerId = "";
    static String insCustomerName = "";
    static String insCustomerPhone = "";
    static String insEmpIdx = "";
    static String insEmpName = "";

    private LinearLayout couponBtnLn;

    private TextView discountExtraTitleEditText;

    private Button closeBtn;

    private Button discountSuButton1,discountSuButton2,discountSuButton3;
    private Button discountSuButton4,discountSuButton5,discountSuButton6;
    private Button discountSuButton7,discountSuButton8,discountSuButton9;
    private Button discountSuButton0,discountSuButton00,discountSuButtonBack;
    private Button discountSuButtonV;
    private Button qrcodeOpenButton;
    private Button discountCouponCheckBtn;

    public static ToggleButton discountRateTypeButton;

    public static Switch discountCouponYNSwitch;
    public static Switch discountDiscountExtraSwitch;
    public static Switch discountAllEachSwitch;

    public static EditText discountCouponNumberEditText;
    public static EditText discountRateEditText;

    public TextView discountInformationTitleTextView, discountInformationContentsTextView;

    TextView discountCouponYNTitleTextView, discountCouponNumberTitleTextView;
    TextView discountCouponDCExtraTitleTextView, discountCouponAllEachTitleTextView;
    TextView discountCouponRateTitleTextView;

    public static String couponNumberEtInputType = "K";     // K : Key in     P : Popup 에서 값 넘김

    public static Vector<String> mCouponNumberVec;

    public static ProgressDialog checkingProDial;

    public static String mCouponApplyType = "B";

    public static String mCouponUsingYN = "N";

    public static String mCouponReturnValue = "";

    // discount button
    private ScrollView discountbtnSv;
    private LinearLayout discountbtnLn;

    // discount button 관련 추가
    public static String tempDiscountButtonName = "";
    public static Vector<String> mDiscountButtonVec;

    public static double tempTotalDiscountExtraAmountForAllDCEX = 0;

    // 01052024
    public static String mCouponName = "";

    public Discount(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // discount 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setOnClickListener(discountBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT.setOnClickListener(discountBtnClickListener);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        // 쿠폰번호 담아놓는 벡터 생성
        mCouponNumberVec = new Vector<String>();
    }

    public void setContents() {
        // 01052024
        mCouponName = "";

        couponNumberEtInputType = "K";

        int numberBackground = R.drawable.ab_imagebutton_discountextra_number;
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            numberBackground = R.drawable.ab_imagebutton_discountextra_number_lite;
            GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR = "#0054d5";
        }


        couponBtnLn = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT.findViewWithTag("couponBtnLnTag");

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCloseBtnTag");
        closeBtn.setOnClickListener(discountBtnClickListener);
        closeBtn.setText("");
        closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);

        discountExtraTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountExtraTitleEditTextTag");
        discountExtraTitleEditText.setText("");
        discountExtraTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_discountextra_title);

        // 소제목 TextView 객체들 -----------------------------------------------------------------------------------
        discountCouponYNTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponYNTitleTextViewTag");
        discountCouponYNTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        discountCouponNumberTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponNumberTitleTextViewTag");
        discountCouponNumberTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        discountCouponDCExtraTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponDCExtraTitleTextViewTag");
        discountCouponDCExtraTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        discountCouponAllEachTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponAllEachTitleTextViewTag");
        discountCouponAllEachTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);

        discountCouponRateTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponRateTitleTextViewTag");
        discountCouponRateTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        // --------------------------------------------------------------------------------------------------------

        // switch 버튼 객체
        discountCouponYNSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponYNSwitchTag");
        discountCouponYNSwitch.setOnCheckedChangeListener(couponUseCheck);

        discountDiscountExtraSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountDiscountExtraSwitchTag");
        discountAllEachSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountAllEachSwitchTag");

        // EditText 객체
        discountCouponNumberEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponNumberEditTextTag");
        //discountCouponNumberEditText.setOnClickListener(mEditTextClick);
        discountCouponNumberEditText.setOnTouchListener(mEditTextTouch);
        discountCouponNumberEditText.addTextChangedListener(textChanged);


        discountRateEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountRateEditTextTag");
        discountRateEditText.setOnClickListener(mEditTextClick);
        discountRateEditText.setOnTouchListener(mEditTextTouch);


        // TextView 객체
        discountInformationTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountInformationTitleTextViewTag");
        discountInformationContentsTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountInformationContentsTextViewTag");

        // Button 객체
        discountRateTypeButton = (ToggleButton)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountRateTypeButtonTag");
        discountRateTypeButton.setOnCheckedChangeListener(discountToggleBtnChangeListener);

        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        discountSuButton1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
                            * 1.8f
            );
            discountSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton1.setBackgroundResource(numberBackground);
        } else {
            discountSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton2.setBackgroundResource(numberBackground);
        } else {
            discountSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton3.setBackgroundResource(numberBackground);
        } else {
            discountSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton4.setBackgroundResource(numberBackground);
        } else {
            discountSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton5.setBackgroundResource(numberBackground);
        } else {
            discountSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton6.setBackgroundResource(numberBackground);
        } else {
            discountSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton7.setBackgroundResource(numberBackground);
        } else {
            discountSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton8 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton8.setBackgroundResource(numberBackground);
        } else {
            discountSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton9 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton9.setBackgroundResource(numberBackground);
        } else {
            discountSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton0 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton0.setBackgroundResource(numberBackground);
        } else {
            discountSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButton00 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            discountSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            discountSuButton00.setBackgroundResource(numberBackground);
        } else {
            discountSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        discountSuButtonBack = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButtonBack.setText("");
            discountSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_discountextra_delete);
        }
        discountSuButtonV = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            discountSuButtonV.setText("");
            discountSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_discountextra_enter);
        }

        qrcodeOpenButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("qrcodeOpenButtonTag");
        qrcodeOpenButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);

        discountCouponCheckBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountCouponCheckBtnTag");
        discountCouponCheckBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        discountSuButton1.setOnClickListener(discountBtnClickListener);
        discountSuButton2.setOnClickListener(discountBtnClickListener);
        discountSuButton3.setOnClickListener(discountBtnClickListener);
        discountSuButton4.setOnClickListener(discountBtnClickListener);
        discountSuButton5.setOnClickListener(discountBtnClickListener);
        discountSuButton6.setOnClickListener(discountBtnClickListener);
        discountSuButton7.setOnClickListener(discountBtnClickListener);
        discountSuButton8.setOnClickListener(discountBtnClickListener);
        discountSuButton9.setOnClickListener(discountBtnClickListener);
        discountSuButton0.setOnClickListener(discountBtnClickListener);
        discountSuButton00.setOnClickListener(discountBtnClickListener);
        discountSuButtonBack.setOnClickListener(discountBtnClickListener);
        discountSuButtonV.setOnClickListener(discountBtnClickListener);
        qrcodeOpenButton.setOnClickListener(discountBtnClickListener);

        discountCouponCheckBtn.setOnClickListener(discountBtnClickListener);


        // 스크롤뷰 객체 생성
        discountbtnSv = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountbtnSvTag");
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        discountbtnLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT
                .findViewWithTag("discountbtnLnTag");
        /***********************************************************************************************************/

        // discount button
        setDiscountButton();

        setInit();
    }

    // discount button
    public void setDiscountButton() {
        discountbtnLn.removeAllViews();

        int btnHeight = 60;
        if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                btnHeight = 40;
            }
        } else {
            if (GlobalMemberValues.thisTabletRealWidth < 800) {
                btnHeight = 40;
            }
        }

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewBtnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
        subNewBtnLayoutParams.setMargins(0, 0, 0, 0);

        // LinearLayout 객체 생성
        LinearLayout newLn = new LinearLayout(MainActivity.mContext);
        newLn.setLayoutParams(newLnLayoutParams);
        newLn.setOrientation(LinearLayout.VERTICAL);
        newLn.setPadding(0, 0, 0, 0);

        String tempSql = " select name, discount, discounttype from salon_storediscountbutton " +
                " where useyn = 'Y' " +
                " and delyn = 'N' " +
                " order by sortnum asc, discount asc ";
        GlobalMemberValues.logWrite("discountbuttonlog", "tempSql : " + tempSql + "\n");
        Cursor otherpaymentcompanyCursor = dbInit.dbExecuteRead(tempSql);
        while (otherpaymentcompanyCursor.moveToNext()) {
            final String tempName = GlobalMemberValues.getDBTextAfterChecked(otherpaymentcompanyCursor.getString(0), 1);
            final String tempDiscount = GlobalMemberValues.getDBTextAfterChecked(otherpaymentcompanyCursor.getString(1), 1);
            final String tempDiscounttype = GlobalMemberValues.getDBTextAfterChecked(otherpaymentcompanyCursor.getString(2), 1);
            if (!GlobalMemberValues.isStrEmpty(tempDiscount)) {
                LinearLayout subNewLn = new LinearLayout(MainActivity.mContext);
                subNewLn.setLayoutParams(subNewLnLayoutParams);
                subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                subNewLn.setPadding(0, 0, 0, 14);

                String tempBtnValue = "";
                if (tempDiscounttype.equals("%")) {
                    tempBtnValue = tempName + " (" + tempDiscount + tempDiscounttype + ")";
                } else {
                    tempBtnValue = tempName + " (" + tempDiscounttype + tempDiscount + ")";
                }

                Button subNewBtn = new Button(MainActivity.mContext);
                subNewBtn.setLayoutParams(subNewBtnLayoutParams);
                subNewBtn.setGravity(Gravity.CENTER);
                subNewBtn.setAllCaps(false);
                subNewBtn.setText(tempBtnValue);
                subNewBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
                subNewBtn.setTextColor(Color.parseColor("#ffffff"));
                subNewBtn.setPaintFlags(subNewBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

                //subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay_selected);
                //subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay);
                subNewBtn.setBackgroundResource(R.drawable.button_selector_category4);

                subNewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDiscountButton(tempDiscount, tempDiscounttype, tempName);
                    }
                });
                subNewLn.addView(subNewBtn);
                newLn.addView(subNewLn);
            }
        }
        discountbtnLn.addView(newLn);
    }

    // discount button
    public void selectDiscountButton(String paramDiscount, String paramDiscounttype, String paramDiscountName) {
        if (!GlobalMemberValues.isStrEmpty(paramDiscount)) {
            if (GlobalMemberValues.isStrEmpty(paramDiscounttype)) {
                paramDiscounttype = "%";
            }

            if (paramDiscounttype.equals("%")) {
                discountRateTypeButton.setChecked(true);
            } else {
                discountRateTypeButton.setChecked(false);
            }

            discountRateEditText.setText(paramDiscount);

            applyCoupon(paramDiscountName);
        }
    }

    TextWatcher textChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //GlobalMemberValues.displayDialog(context, "Warning", "22222", "Close");
            //String tempS = s.toString();
            //tempS = GlobalMemberValues.getReplaceText(tempS, "\n", "jjj");
            //GlobalMemberValues.logWrite("couponkeyinlog", tempS + "\n");
            String tempCouponNumber = discountCouponNumberEditText.getText().toString();
            GlobalMemberValues.logWrite("coupon_check_log", "쿠폰번호 : " + tempCouponNumber + "\n");
            if (!GlobalMemberValues.isStrEmpty(tempCouponNumber)) {
                if (tempCouponNumber.indexOf("\n") > -1) {
                    GlobalMemberValues.logWrite("coupon_check_log", "checkCoupon 실행...000" + "\n");
                    checkCoupon();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public static void setDiscountAfterCheckedCoupon(String paramCouponNumber, String paramCouponReturnValue, Context paramContext) {
        //GlobalMemberValues.displayDialog(context, "Warning", "333333", "Close");
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {       // 인터넷연결 체크
            if (!GlobalMemberValues.isOnline().equals("00")) {
                if (discountCouponYNSwitch.isChecked()) {
                    //GlobalMemberValues.displayDialog(context, "Warning", "Network is not connected internet", "Close");
                    GlobalMemberValues.openNetworkNotConnected();
                }
            } else {
                // 쿠폰정보 받아서 저장할 객체
                GlobalMemberValues.logWrite("couponcheckvec", "mCouponNumberVec size : " + mCouponNumberVec.size() + "\n");
                if (mCouponNumberVec.indexOf(paramCouponNumber) == -1) { // 사용하지 않은 쿠폰일 경우
                    GlobalMemberValues.logWrite("coupon_check_log", "paramCouponReturnValue : " + paramCouponReturnValue + "\n");
                    if (!GlobalMemberValues.isStrEmpty(paramCouponReturnValue)) {
                        String[] strCouponInfoArr = paramCouponReturnValue.split(GlobalMemberValues.STRSPLITTER1);
                        String offType = strCouponInfoArr[0];
                        String discountValue = strCouponInfoArr[1];
                        String startDate = strCouponInfoArr[2];
                        String endDate = strCouponInfoArr[3];
                        String usedyn = strCouponInfoArr[4];

                        // 01052024
                        String couponName = strCouponInfoArr[7];
                        if (GlobalMemberValues.isStrEmpty(couponName)) {
                            couponName = "";
                        } else {
                            couponName = GlobalMemberValues.getReplaceText(couponName, "&#37;", "%");
                        }

                        String applytype = strCouponInfoArr[9];
                        if (GlobalMemberValues.isStrEmpty(applytype)) {
                            applytype = "B";
                        }

                        mCouponApplyType = applytype;

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
                            mCouponUsingYN = "Y";

                            // 01052024
                            mCouponName = couponName;

                            //GlobalMemberValues.displayDialog(context, "Coupon Check", paramCouponReturnValue, "Close");
                            //discountDiscountExtraSwitch.setChecked(true);
                            //discountAllEachSwitch.setChecked(false);
                            String tempRateValue = GlobalMemberValues.getStringFormatNumber(
                                    GlobalMemberValues.getDoubleAtString(discountValue), "2");
                            discountRateEditText.setText(tempRateValue);
                            mRateEditTextValue = GlobalMemberValues.getReplaceText(tempRateValue, ".", "");
                            discountRateTypeButton.setText(offType);
                            String tempOffType = offType + "A";
                            if (tempOffType.equals("%A")) {
                                discountRateTypeButton.setChecked(true);
                                discountRateTypeButton.setBackgroundColor(Color.parseColor("#b5a917"));
                            } else {
                                discountRateTypeButton.setChecked(false);
                                discountRateTypeButton.setBackgroundColor(Color.parseColor("#278e24"));
                            }
                            GlobalMemberValues.setKeyPadHide(paramContext, discountCouponNumberEditText);
                            mCouponNumber = paramCouponNumber;

                            // 쿠폰 바로 적용일 때..
                            if (GlobalMemberValues.isCouponImmediatelyApply()) {
                                GlobalMemberValues.logWrite("couponapplytypelog", "coupon apply type : " + applytype + "\n");

                                // 01052024
                                applyCoupon(couponName);
                            }
                        } else {                        // 검색결과 쿠폰사용이 안될 때
                            if (noCouponInfoReason > 0) {
                                String dialogStr = "";
                                switch (noCouponInfoReason) {
                                    case 1 : {
                                        dialogStr = "No Coupon Information [1]";
                                        break;
                                    }
                                    case 2 : {
                                        dialogStr = "Coupon date is not available";
                                        break;
                                    }
                                    case 3 : {
                                        dialogStr = "Used coupon";
                                        break;
                                    }
                                    default : {
                                        dialogStr = "No Coupon Information [2]";
                                        break;
                                    }
                                }
                                setInitCoupon();
                                closeProgressDialog();
                                GlobalMemberValues.displayDialog(paramContext, "Coupon Check", dialogStr, "Close");
                                return;
                            }
                        }
                    } else {
                        setInitCoupon();
                        closeProgressDialog();
                        GlobalMemberValues.displayDialog(paramContext, "Coupon Check", "No Coupon Information [3]", "Close");
                        return;
                    }
                } else {                                                        // 사용된 쿠폰일 경우
                    setInitCoupon();
                    if (discountCouponYNSwitch.isChecked()) {
                        GlobalMemberValues.displayDialog(paramContext, "Warning", "Used Coupon", "Close");
                    }
                }
            }
        } else {
            setInitCoupon();
            if (discountCouponYNSwitch.isChecked()) {
                //GlobalMemberValues.displayDialog(context, "Warning", "Network is not connected internet", "Close");
                GlobalMemberValues.openNetworkNotConnected();
            }
        }
    }

    Switch.OnCheckedChangeListener couponUseCheck = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // Elo 일 경우 바코드 스캐너 활성화
                if (GlobalMemberValues.isDeviceElo()) {
                    GlobalMemberValues.eloBarcodeScannerOn();
                }
                discountCouponNumberEditText.setEnabled(true);
                discountCouponNumberEditText.requestFocus();

                // QR 코드 버튼 보이기
                qrcodeOpenButton.setVisibility(View.VISIBLE);
                couponBtnLn.setVisibility(View.VISIBLE);

                int checkNetwork = GlobalMemberValues.GLOBALNETWORKSTATUS;
                //checkNetwork = 1;
                if (checkNetwork > 0) {
                    if (!GlobalMemberValues.isOnline().equals("00")) {
                        GlobalMemberValues.showDialogNoInternet(context);
                        return;
                    }
                    discountCouponNumberEditText.setBackgroundResource(R.drawable.roundlayout_background_discount);
                    discountCouponNumberEditText.setEnabled(true);
                    discountCouponNumberEditText.requestFocus();
                } else {
                    setInitCoupon();
                    //discountCouponNumberEditText.setFocusable(false);
                    mRateEditTextValue = "";
                    discountCouponNumberEditText.clearFocus();
                    //GlobalMemberValues.displayDialog(context, "Warning", "Network is not connected internet", "Close");
                    GlobalMemberValues.openNetworkNotConnected();
                }

                // 키패드(키보드) 감추기
                GlobalMemberValues.setKeyPadHide(context, discountCouponNumberEditText);
                // 커서를 먼저 맨 뒤로...
                GlobalMemberValues.setCursorLastDigit(discountCouponNumberEditText);
            } else {
                // Elo 일 경우 바코드 스캐너 비활성화
                if (GlobalMemberValues.isDeviceElo()) {
                    GlobalMemberValues.eloBarcodeScannerOff();
                }

                // QR 코드 버튼 가리기
                qrcodeOpenButton.setVisibility(View.GONE);
                couponBtnLn.setVisibility(View.INVISIBLE);

                setInitCoupon();
                discountRateEditText.setText("");
                mRateEditTextValue = "";
                setInit();
                //discountCouponNumberEditText.setFocusable(false);
            }

        }
    };

    View.OnClickListener mEditTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.discountRateEditText : {
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    GlobalMemberValues.logWrite("discount", "Price Click\n");
                    break;
                }
            }
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.discountCouponNumberEditText : {
                    GlobalMemberValues.setKeyPadHide(context, discountCouponNumberEditText);
                    discountCouponNumberEditText.requestFocus();
                    discountCouponNumberEditText.setSelection(discountCouponNumberEditText.length());

                    break;
                }
                case R.id.discountRateEditText : {
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    discountRateEditText.requestFocus();
                    discountRateEditText.setSelection(discountRateEditText.length());

                    break;
                }
            }

            return true;
        }
    };

    ToggleButton.OnCheckedChangeListener discountToggleBtnChangeListener =
            new ToggleButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogsSave.saveLogsInDB(62);
                    if (mCouponUsingYN.equals("N") || mCouponUsingYN == "N") {
                        mRateEditTextValue = "0";
//                        discountRateEditText.setText("");
                        if (discountRateTypeButton.isChecked()) {
                            discountRateTypeButton.setTextColor(Color.parseColor("#ffffff"));
                        } else {
                            discountRateTypeButton.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                }
            };

    View.OnClickListener discountBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qrcodeOpenButton : {
                    if (GlobalMemberValues.isDeviceElo() || GlobalMemberValues.isDeviceClover()) {
                        if (GlobalMemberValues.isDeviceElo()) {
                            discountCouponNumberEditText.requestFocus();
                            GlobalMemberValues.eloBarcodeScannerOn();
                        }
                        if (GlobalMemberValues.isDeviceClover()) {
                            if (GlobalMemberValues.isDeviceClover()) {
                                GlobalMemberValues.cloverBarcodeScannerOn();
                            }
                        }
                    } else {
                        //GlobalMemberValues.displayDialog(context, "QR Code Reader", "Under Construction", "Close");

                        // 카메라 권한 체크 후 QR 코드 리더 열기
                        //AppPermissionCheck.checkPermissionCameraState();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            AppPermissionCheck2 appPermissionCheck2 = new AppPermissionCheck2(MainActivity.mActivity, MainActivity.mContext);
                            appPermissionCheck2.permissionCheck(MainActivity.mActivity, MainActivity.mContext, "CAMERA", "N");
                        } else {
                            GlobalMemberValues.openQRCodeReader(MainActivity.mActivity, MainActivity.mContext, MainActivity.CALL_ZXING_RESULT_DISCOUNT);
                        }
                    }

                    break;
                }

                case R.id.button_main_side_discount :
                case R.id.mainRightBottom_Discount : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<10>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------
                    LogsSave.saveLogsInDB(102);
                    if (MainMiddleService.mGeneralArrayList.size() > 0) {

                        // 선택된 항목중에 할인/추가 를 적용한 아이템이 있는지 확인한다. ------------------------------------------------
                        int tempDcExCount = 0;
                        int tempSize = MainMiddleService.mGeneralArrayList.size();
                        for(int i = 0; i < tempSize; i++){
                            // jihun discount extra 중복추가 막기.
                            if(MainMiddleService.isCheckedConfrim[i]) {
                                TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(i);
                                String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                                if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                                    tempDcExCount++;
                                }
                            }
                        }

                        if (tempDcExCount > 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You cannot discount or extra\n" +
                                    "because selected item(s) includes 'Discount' or 'Extra'", "Close");
                            return;
                        }
                        // --------------------------------------------------------------------------------------------------------

                        GlobalMemberValues.setFrameLayoutVisibleChange("discount");
                        setContents();

                        // Lite 버전 관련
                        if (GlobalMemberValues.isLiteVersion()) {
                            // 하단버튼 초기화
                            GlobalMemberValues.setInitMainBottomButtonBg();
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setBackgroundResource(R.drawable.aa_images_main_discount_rollover_lite);
                        }

                    } else {
                        GlobalMemberValues.displayDialog(context, "Discount / Extra", "Select a service / product", "Close");
                    }

                    break;
                }
                case R.id.discountCloseBtn : {
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOff();
                    }
                    if (GlobalMemberValues.isDeviceClover()) {
                        GlobalMemberValues.cloverBarcodeScannerOff();
                    }

                    GlobalMemberValues.setFrameLayoutVisibleChange("main");

                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton1 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton2 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton3 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton4 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton5 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton6 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton7 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton8 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton9 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton0 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButton00 : {
                    LogsSave.saveLogsInDB(63);
                    numberButtonClick(discountSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                    break;
                }
                case R.id.discountSuButtonBack : {
                    LogsSave.saveLogsInDB(64);
                    if (discountCouponNumberEditText.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mCouponNumberTextValue);
                        if (!GlobalMemberValues.isStrEmpty(mCouponNumberTextValue)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mCouponNumberTextValue = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mCouponNumberTextValue)) {
                                mCouponNumberTextValue = "";
                            }
                        } else {
                            mCouponNumberTextValue = "";
                        }

                        discountCouponNumberEditText.setText(mCouponNumberTextValue);

                        GlobalMemberValues.logWrite("GIFTCARD", "mCouponNumberTextValue : " + mCouponNumberTextValue + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, discountCouponNumberEditText);
                        // 커서를 먼저 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(discountCouponNumberEditText);
                    }

                    if (discountRateEditText.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mRateEditTextValue);
                        if (!GlobalMemberValues.isStrEmpty(mRateEditTextValue)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mRateEditTextValue = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mRateEditTextValue)) {
                                mRateEditTextValue = "0";
                            }
                        } else {
                            mRateEditTextValue = "0";
                        }

                        String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mRateEditTextValue) * 0.01), "2");
                        discountRateEditText.setText(inputSu);

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
                        // 커서를 먼저 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(discountRateEditText);
                    }

                    break;
                }
                case R.id.discountSuButtonV : {
                    LogsSave.saveLogsInDB(65);

                    // 01052024
                    String tempCouponName = "";
                    if (!GlobalMemberValues.isStrEmpty(mCouponName)) {
                        tempCouponName = mCouponName;
                    }
                    mCouponName = "";

                    applyCoupon(tempCouponName);

                    break;
                }

                case R.id.discountCouponCheckBtn : {
                    checkCoupon();
                    break;
                }
            }
        }
    };

    public static void checkCoupon() {
        GlobalMemberValues.logWrite("coupon_check_log", "checkCoupon 실행..." + "\n");

        String tempDiscountCouponNumber = discountCouponNumberEditText.getText().toString();
        //GlobalMemberValues.displayDialog(context, "Info", "tempDiscountCouponNumber : " + tempDiscountCouponNumber, "Close");
        //CouponDetail.getCouponDetailValue(tempDiscountCouponNumber);
        //if (tempDiscountCouponNumber.length() == 14 && couponNumberEtInputType.equals("K")) {
        // }

        tempDiscountCouponNumber = GlobalMemberValues.getReplaceText(tempDiscountCouponNumber, "\n", "");
        discountCouponNumberEditText.setText(tempDiscountCouponNumber);

        if (GlobalMemberValues.isStrEmpty(tempDiscountCouponNumber)) {
            GlobalMemberValues.displayDialog(context, "","No Coupon Code...", "Close");
            return;
        }

        if (CouponDetail.openCount == 0) {
            // 프로그래스 바를 실행~
            /**
             checkingProDial = ProgressDialog.show(
             MainActivity.mContext, " Checking Coupon","Checking the coupon...(No." + mCouponNumber + ")", true, false
             );
             **/
            checkingProDial = new JJJCustomAnimationDialog(mActivity);
            checkingProDial.show();

            mCouponReturnValue = "";

            final String finalTempDiscountCouponNumber = tempDiscountCouponNumber;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    getCouponDetailValue(finalTempDiscountCouponNumber);
                    // ---------------------------------------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    memcheckhandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();

            /**
             Intent couponDetailIntent = new Intent(MainActivity.mContext.getApplicationContext(), CouponDetail.class);
             couponDetailIntent.putExtra("couponnumber", tempDiscountCouponNumber);
             mActivity.startActivity(couponDetailIntent);
             mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
             **/
        }
    }

    private static Handler memcheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            closeProgressDialog();
            // -------------------------------------------------------------------------------------
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            //GlobalMemberValues.displayDialog(context, "",mCouponReturnValue, "Close");
            if (!GlobalMemberValues.isCouponInfoView()) {
                CouponDetail.openCount = 0;
                setDiscountAfterCheckedCoupon(mCouponNumber, mCouponReturnValue, MainActivity.mContext);
            } else {
                Intent couponDetailIntent = new Intent(MainActivity.mContext.getApplicationContext(), CouponDetail.class);
                couponDetailIntent.putExtra("couponnumber", discountCouponNumberEditText.getText().toString());
                couponDetailIntent.putExtra("couponreturnvalue", mCouponReturnValue);
                mActivity.startActivity(couponDetailIntent);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
            }

            mCouponReturnValue = "";
            // -------------------------------------------------------------------------------------
        }
    };

    public static void getCouponDetailValue(String paramCouponNumber) {
        String returnValue = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {       // 인터넷연결 체크
            if (!GlobalMemberValues.isOnline().equals("00")) {
                // 인터넷 미연결...
                returnValue = "";
            } else {
                // 쿠폰번호(barcode)가 14 자리 일경우
                if (paramCouponNumber.length() > 3) {
                    // 쿠폰정보 받아서 저장할 객체
                    if (mCouponNumberVec.indexOf(paramCouponNumber) == -1) { // 현재 장바구니에 담기지 않은, 사용하지 않은 쿠폰일 경우
                        // 실시간 쿠폰정보 체크
                        APIDownLoad_coupon_check apidowncouponcheck = new APIDownLoad_coupon_check(paramCouponNumber);
                        apidowncouponcheck.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (apidowncouponcheck.mFlag) {
                                returnValue = apidowncouponcheck.strReturn;
                            }
                        } catch (InterruptedException e) {
                            returnValue = e.getMessage().toString();
                        }
                    } else {                                                        // 사용된 쿠폰일 경우
                        returnValue = "Used Coupon";
                    }
                }
            }
        } else {
            returnValue = "Network is not connected internet";
        }

        mCouponReturnValue = returnValue;
    }

    // Discount Button 관련 추가 (paramDiscountName)
    public static void applyCoupon(String paramDiscountName) {

        // Discount 또는 Extra 할 비율 또는 금액
        String tempRate = discountRateEditText.getText().toString();
        GlobalMemberValues.logWrite("discountbtnlog", "tempRate : " + tempRate + "\n");
        if (GlobalMemberValues.isStrEmpty(tempRate)) {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter rate", "Close");
            return;
        }
        if (MainMiddleService.mGeneralArrayList.size() > 0) {   // 메인 좌측 Sale Cart 에 아이템에 있을 경우에만..
            // 선택된 항목중에 할인/추가 를 적용한 아이템이 있는지 확인한다. -----------------------------------------------------
            int tempDcExCount = 0;
            int tempSize = MainMiddleService.mGeneralArrayList.size();
            for(int i = 0; i < tempSize; i++){
                if(MainMiddleService.isCheckedConfrim[i]){
                    TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(i);
                    String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                    if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                        tempDcExCount++;
                    }
                }
            }
            if (tempDcExCount > 0) {
                GlobalMemberValues.displayDialog(context, "Warning", "You cannot discount or extra\n" +
                        "because selected item(s) includes 'Discount' or 'Extra'", "Close");
                return;
            }
            // --------------------------------------------------------------------------------------------------------------

            // 쿠폰번호
            String tempCouponNumber = mCouponNumber;
            double tempRateDouble = Double.parseDouble(tempRate);
            if (!GlobalMemberValues.isStrEmpty(tempRate)) {
                // Discount 또는 Extra 방법 (%, $)
                String tempRateType = "%";
                if (discountRateTypeButton.isChecked()) {
                    tempRateType = "%";
                } else {
                    tempRateType = "$";
                }

                // Discount 또는 Extra
                String tempDcExtraType = "DC";
                String tempDcExtraStr = "";
                String tempMSaveType = "";          // TemporarySaleCart mSaveType 에 저장할 때 DC / Extra 여부 저장
                if (discountDiscountExtraSwitch.isChecked()) {
                    tempDcExtraType = "DC";
                    tempDcExtraStr = "DISCOUNT";
                    tempMSaveType = "8";
                } else {
                    tempDcExtraType = "EX";
                    tempDcExtraStr = "EXTRA";
                    tempMSaveType = "9";
                }

                String tempDcExtraAllEach = "ALL";
                if (discountAllEachSwitch.isChecked()) {
                    tempDcExtraAllEach = "ALL";
                } else {
                    tempDcExtraAllEach = "EACH";
                }

                GlobalMemberValues.logWrite("discountlog", "tempDcExtraAllEach : " + tempDcExtraAllEach + "\n");

                // Discount Button 관련 추가
                tempDiscountButtonName = paramDiscountName;

                if (tempDcExtraAllEach.equals("ALL")) {
                    GlobalMemberValues.logWrite("discountlog", "All 실행..." + "\n");
                    /** All Discount 일 경우 **************************************************************************/

                    // jihun 220628 추가
                    if (MainMiddleService.mGeneralArrayList.size() > 0) {
                        // 모든 항목중에 할인/추가 를 적용한 아이템이 있는지 확인한다. ------------------------------------------------
                        int allCheck_tempDcExCount = 0;
                        int allCheck_tempSize = MainMiddleService.mGeneralArrayList.size();
                        for(int i = 0; i < allCheck_tempSize; i++){
//                            if(MainMiddleService.isCheckedConfrim[i]) {
                            TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(i);
                            String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                            if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                                allCheck_tempDcExCount++;
//                                }
                            }
                        }

                        if (allCheck_tempDcExCount > 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You cannot discount or extra\n" +
                                    "because selected item(s) includes 'Discount' or 'Extra'", "Close");
                            return;
                        }
                        // --------------------------------------------------------------------------------------------------------
                    }
                    // jihun 220628 추가


                    int tempArrayListSize = MainMiddleService.mGeneralArrayList.size();
                    GlobalMemberValues.logWrite("discountlog", "tempArrayListSize : " + tempArrayListSize + "\n");
                    // 리스트뷰에 담긴 항목이 있을 경우에만..
                    if (tempArrayListSize > 0) {
                        String tempTotalAmount = "0";
                        double tempTotalAmountDbl = 0;
                        //tempTotalAmount = GlobalMemberValues.getReplaceText(GlobalMemberValues.getReplaceText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString(), "$", ""), ",","");
                        // DC/Extra 처리할 총액 구하기
                        int appliedItemCnt = 0;
                        for (int i = 0; i < MainMiddleService.mGeneralArrayList.size(); i++) {
                            TemporarySaleCart tempTempSaleCart1 = MainMiddleService.mGeneralArrayList.get(i);

                            if (isCheckDiscountApply(tempTempSaleCart1, tempMSaveType)) {
                                tempTotalAmountDbl += GlobalMemberValues.getDoubleAtString(tempTempSaleCart1.mSPriceBalAmount);
                                appliedItemCnt++;
                            }
                        }

                        tempTotalAmount = String.valueOf(tempTotalAmountDbl);

                        int tempLvCnt = 0;
                        for (int i = 0; i < MainMiddleService.mGeneralArrayList.size(); i++) {
                            TemporarySaleCart tempTempSaleCart2 = MainMiddleService.mGeneralArrayList.get(i);

                            if (isCheckDiscountApply(tempTempSaleCart2, tempMSaveType)) {
                                tempLvCnt++;

                                int addLvFlag = 1;
                                if (appliedItemCnt == tempLvCnt) {
                                    addLvFlag = 0;
                                }

                                setDiscountExtra(tempTempSaleCart2, mCouponNumber, tempRate, tempRateType,
                                        tempDcExtraType, tempDcExtraStr, tempMSaveType, tempDcExtraAllEach, tempTotalAmount, addLvFlag, -1);
                            }
                        }
                    }
                    /**************************************************************************************************/

                } else {
                    GlobalMemberValues.logWrite("discountlog", "Each 실행..." + "\n");

                    /** Each Discount 일 경우 **************************************************************************/
                    setDiscountExtraPrev(tempRate, tempRateType, tempDcExtraType, tempDcExtraStr, tempMSaveType, tempDcExtraAllEach);
                    /*************************************************************************************************/
                }
            } else {
                GlobalMemberValues.displayDialog(context, "Discount / Extra", "Enter Coupon # or Rate", "Close");
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Discount / Extra", "Choose Item", "Close");
        }

        GlobalMemberValues.logWrite("pointchecklog", "mCouponNumber : " + mCouponNumber + "\n");
        mCouponNumberVec.addElement(mCouponNumber);

        MainMiddleService.selectedPosition = -1;
        GlobalMemberValues.setFrameLayoutVisibleChange("main");
        // 초기화
        setInit();
        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, discountRateEditText);
    }

    private static void setDiscountExtraPrev(String paramTempRate, String paramTempRateType,
                                             String paramTempDcExtraType, String paramTempDcExtraStr, String paramTempMSaveType,
                                             String paramTempDcExtraAllEach) {

        if (GlobalMemberValues.isMultiCheckOnCart()) {
            int tempSize = MainMiddleService.mGeneralArrayList.size();
            if (tempSize == 0) {
                return;
            }

            boolean[] copyIsCheckedConfrim = MainMiddleService.isCheckedConfrim;

            int checkedItemCount = 0;
            for(int i = 0; i < tempSize; i++){
                if(copyIsCheckedConfrim[i]){
                    checkedItemCount++;
                }
            }
            if (MainMiddleService.mGeneralArrayList.size() > 0 && checkedItemCount > 0) {
                boolean tempLastItem = false;
                int tempCount = 0;
                for(int j = (tempSize - 1); j >= 0; j--){
                    if(copyIsCheckedConfrim[j]){
                        tempCount++;
                        if (checkedItemCount == tempCount) {
                            tempLastItem = true;
                        } else {
                            tempLastItem = false;
                        }
                        //GlobalMemberValues.logWrite("multicheckedlog", "j : " + j + "\n");
                        //MainMiddleService.deleteItem(j, tempLastItem);

                        GlobalMemberValues.logWrite("multicheckedlogDc", "-------------------------------------------" + "\n");

                        GlobalMemberValues.logWrite("multicheckedlogDc", "j : " + j + "\n");

                        setDiscountExtraItem(paramTempRate, paramTempRateType, paramTempDcExtraType, paramTempDcExtraStr,
                                paramTempMSaveType, paramTempDcExtraAllEach, j);
                    }
                }
            }
        } else {
            if (MainMiddleService.selectedPosition != -1) {     // 선택된 아이템이 있을 경우에만
                setDiscountExtraItem(paramTempRate, paramTempRateType, paramTempDcExtraType, paramTempDcExtraStr,
                        paramTempMSaveType, paramTempDcExtraAllEach, MainMiddleService.selectedPosition);
            } else {
                GlobalMemberValues.displayDialog(context, "Discount / Extra", "Choose Item", "Close");
                return;
            }
        }

    }

    public static void setDiscountExtraItem(String paramTempRate, String paramTempRateType,
                                            String paramTempDcExtraType, String paramTempDcExtraStr, String paramTempMSaveType,
                                            String paramTempDcExtraAllEach, int paramPosition) {
        if (!GlobalMemberValues.isStrEmpty(paramPosition + "") && paramPosition != -1) {
            TemporarySaleCart tempTempSaleCart
                    = MainMiddleService.mGeneralArrayList.get(paramPosition);
            if (isCheckDiscountApply(tempTempSaleCart, paramTempMSaveType)) {

                double tempTempSaleCartDcExtraPrice = GlobalMemberValues.getDoubleAtString(tempTempSaleCart.selectedDcExtraPrice);

                if (tempTempSaleCartDcExtraPrice > 0 || tempTempSaleCartDcExtraPrice < 0) {
                    if (!GlobalMemberValues.isMultiCheckOnCart()) {
                        GlobalMemberValues.displayDialog(context, "Discount / Extra", "This item is already applied discount or extra", "Close");
                        return;
                    }
                } else {
                    GlobalMemberValues.logWrite("multicheckedlogDc", "여기실행1" + "\n");

                    String tempTotalAmount = tempTempSaleCart.mSPriceBalAmount;
                    setDiscountExtra(tempTempSaleCart, mCouponNumber, paramTempRate, paramTempRateType,
                            paramTempDcExtraType, paramTempDcExtraStr, paramTempMSaveType, paramTempDcExtraAllEach, tempTotalAmount, 0, paramPosition);
                }
            } else {
                GlobalMemberValues.displayDialog(context, "Discount / Extra", "The item you selected can not be discounted\n(" + tempTempSaleCart.mSvcName + ")", "Close");
            }

        }
    }

    private void numberButtonClick(Button btn) {
        if (discountCouponNumberEditText.isFocused()) {
            couponNumberEtInputType = "K";

            GlobalMemberValues.setKeyPadHide(context, discountCouponNumberEditText);

            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());

            sb.append(mCouponNumberTextValue).append(btn.getText().toString());
            mCouponNumberTextValue = sb.toString();
            discountCouponNumberEditText.setText(mCouponNumberTextValue);

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(discountCouponNumberEditText);
        }

        if (discountRateEditText.isFocused()) {
            GlobalMemberValues.setKeyPadHide(context, discountRateEditText);

            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mRateEditTextValue.length() < 12) {
                sb.append(mRateEditTextValue).append(btn.getText().toString());
                Long tempNumber = Long.parseLong(sb.toString());
                double tempLimitSu = 100;
                if (discountRateTypeButton.isChecked()) {
                    tempLimitSu = 100.0;
                } else {
                    tempLimitSu = 100000;
                }

                if ((tempNumber * 0.01) <= tempLimitSu) {
                    mRateEditTextValue = String.valueOf(tempNumber);
                    String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mRateEditTextValue) * 0.01), "2");
                    discountRateEditText.setText(inputSu);
                    GlobalMemberValues.logWrite("discount_mRateEditTextValue", "mRateEditTextValue : " + mRateEditTextValue + "\n");
                }
            }

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(discountRateEditText);
        }
    }

    // Coupon 선택 초기화
    private static void setInitCoupon() {
        GlobalMemberValues.setKeyPadHide(MainActivity.mContext, discountCouponNumberEditText);

        couponNumberEtInputType = "K";

        mCouponNumberTextValue = "";

        discountCouponYNSwitch.setChecked(false);
        discountCouponNumberEditText.setBackgroundResource(R.drawable.roundlayout_background_discount_gray);
        //discountCouponNumberEditText.setEnabled(false);

        //discountCouponNumberEditText.setFocusable(false);
        mRateEditTextValue = "";
        mCouponNumber = "";

        mCouponApplyType = "B";
    }

    // 초기화 메소드
    private static void setInit() {
        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, discountCouponNumberEditText);
        GlobalMemberValues.setKeyPadHide(context, discountRateEditText);

        mRateEditTextValue = "";

        discountRateEditText.setText("");
        //setEmptyCouponNumber();

        //discountCouponYNSwitch.setText("No");
        discountCouponYNSwitch.setChecked(false);
        //discountDiscountExtraSwitch.setText("Discount");
        discountDiscountExtraSwitch.setChecked(true);
        //discountAllEachSwitch.setText("Each");
        discountAllEachSwitch.setChecked(true);

        discountRateTypeButton.setText("%");
        discountRateTypeButton.setChecked(true);
        discountRateTypeButton.setBackgroundColor(Color.parseColor("#b5a917"));

        insCustomerId = "";
        insCustomerName = "";
        insCustomerPhone = "";
        insEmpIdx = "";
        insEmpName = "";

        mCouponApplyType = "B";

        mCouponNumberVec.removeAllElements();
        Discount.mCouponNumberVec.clear();

        mCouponUsingYN = "N";

        // Discount Button 관련 추가
        tempDiscountButtonName = "";

        tempTotalDiscountExtraAmountForAllDCEX = 0;
    }

    public static void setEmptyCouponNumber() {
        discountCouponNumberEditText.setText("");
        mCouponNumberTextValue = "";
    }

    public static void setCustomerEmployeeInfo() {
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            insCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
            insCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
            insCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
        }
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
            insEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            insEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
        }
    }


    public static void setDiscountExtra(TemporarySaleCart paramTempSaleCart,
                                        String paramMCouponNumber, String paramTempRate, String paramTempRateType,
                                        String paramTempDcExtraType, String paramTempDcExtraStr, String paramTempMSaveType,
                                        String paramTempDcExtraAllEach, String paramTotalAmount, int paramAddLvFlag, int paramAddPosition) {
        String tempCouponNumber = paramMCouponNumber;                   // 쿠폰번호
        String tempRate = paramTempRate;                                // 할인/추가 비율 또는 금액
        String tempRateType = paramTempRateType;                        // 할인/추가 타입 (비율, 금액)
        String tempDcExtraType = paramTempDcExtraType;                  // 할인 또는 추가
        String tempDcExtraStr = paramTempDcExtraStr;                    // 할인 또는 추가 텍스트 (Discount, Extra)
        String tempMSaveType = paramTempMSaveType;                      // 할인 또는 추가 구분자 (8 : DC     9 : Extra)
        String tempDcExtraAllEach = paramTempDcExtraAllEach;            // Each 또는 All
        String tempTotalAmount = paramTotalAmount;                      // SubTotal
        int tempAddLvFlag = paramAddLvFlag;                             // 리스트뷰에 추가할지 여부

        String tempNowCouponNum = discountCouponNumberEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(tempCouponNumber) && !GlobalMemberValues.isStrEmpty(tempNowCouponNum)) {
            tempCouponNumber = tempNowCouponNum;
        }

        // 소수점 보정.
        tempRate = GlobalMemberValues.getStringFormatNumber(tempRate,"2");

        double tempTotalAmountDouble = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempTotalAmount, "2"));

        double tempRateDouble = GlobalMemberValues.getDoubleAtString(paramTempRate);

        TemporarySaleCart tempTempSaleCart = paramTempSaleCart;

        // 저장타입 임시저장 ( 0 : 서비스            1 : 상품             2 : 기프트카드 )
        String tempSaveType = tempTempSaleCart.mSaveType;

        // 선택된 아이템의 Amount 구하기
        String tempPriceForDiscountExtraStr = "0";
        tempPriceForDiscountExtraStr = tempTempSaleCart.mSPriceBalAmount;

        double tempPriceForDiscountExtra = 0.0;
        tempPriceForDiscountExtra = GlobalMemberValues.getDoubleAtString(tempPriceForDiscountExtraStr);

        //GlobalMemberValues.logWrite("discounttext", "item sub total : " + tempPriceForDiscountExtra + "\n");

        // 금액이 0 이상일 경우에만 실행한다.
        if (!GlobalMemberValues.isStrEmpty(tempTotalAmount) && tempTotalAmountDouble > 0
                && !GlobalMemberValues.isStrEmpty(tempPriceForDiscountExtraStr)
                && ((tempPriceForDiscountExtra > 0 || tempPriceForDiscountExtra < 0) || tempTotalAmountDouble != 0)) {
            // Discount 또는 Extra 할 금액
            double tempDiscountExtraPrice = 0.0;
            if (tempRateType == "%") {
                tempDiscountExtraPrice = tempPriceForDiscountExtra * tempRateDouble * 0.01;
            } else {
                // 할인/추가 가 비율이 아닌 금액일 경우 전체금액 대비 대상항목의 금액비율로 계산한다.
                double percentage = (double) tempPriceForDiscountExtra / tempTotalAmountDouble * 100;
                double d_value = tempTotalAmountDouble - tempRateDouble;
                double result = d_value * percentage / 100.0;
                tempDiscountExtraPrice = tempPriceForDiscountExtra - result;

//                tempDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((tempPriceForDiscountExtra / tempTotalAmountDouble), "2")) * tempRateDouble;
            }

            GlobalMemberValues.logWrite("discounttext2", "tempDiscountExtraPrice - 1 : " + tempDiscountExtraPrice + "\n");

            if (tempDiscountExtraPrice != 0) {
                //tempDiscountExtraPrice = Math.round(tempDiscountExtraPrice * 100) / 100.0;
                tempDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempDiscountExtraPrice, "2"));
            }

            GlobalMemberValues.logWrite("discounttext2", "tempDiscountExtraPrice - 2 : " + tempDiscountExtraPrice + "\n");

            // ALL 일 경우
            if (tempDcExtraAllEach.equals("ALL")) {
                tempTotalDiscountExtraAmountForAllDCEX += tempDiscountExtraPrice;
                if (tempAddLvFlag == 0) {
                    // 원래 DC, EXTRA 해야할 금액을 구한다.
                    double tempOrginDiscountExtraAmount = 0;
                    if (tempRateType == "%") {
                        tempOrginDiscountExtraAmount = tempTotalAmountDouble * tempRateDouble * 0.01;
                    } else {
                        // 할인/추가 가 비율이 아닌 금액일 경우 전체금액 대비 대상항목의 금액비율로 계산한다.
                        tempOrginDiscountExtraAmount = tempRateDouble;
                    }

                    tempOrginDiscountExtraAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempOrginDiscountExtraAmount, "2"));

                    // 원래 DC, EXTRA 할 금액과 개별계산뒤의 DC, EXTRA 합산금액이 다를 경우
                    if (tempOrginDiscountExtraAmount != tempTotalDiscountExtraAmountForAllDCEX) {
                        double tempAddMinusAmount = tempOrginDiscountExtraAmount - tempTotalDiscountExtraAmountForAllDCEX;

                        tempDiscountExtraPrice += tempAddMinusAmount;
                    }

                    GlobalMemberValues.logWrite("discounttext2", "tempDiscountExtraPrice - 3 : " + tempDiscountExtraPrice + "\n");

                    tempDiscountExtraPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempDiscountExtraPrice, "2"));
                }
            }

            GlobalMemberValues.logWrite("discounttext2", "tempDiscountExtraPrice - 4 : " + tempDiscountExtraPrice + "\n");


            // 원래금액에서 할인액을 뺀 금액
            double tempPriceAmount = 0.0;
            if (tempDcExtraType.equals("DC")) {
                if (tempPriceForDiscountExtra < 0) {
                    tempPriceAmount = tempPriceForDiscountExtra - (tempDiscountExtraPrice * -1);
                } else {
                    tempPriceAmount = tempPriceForDiscountExtra - tempDiscountExtraPrice;
                }
            } else {
                if (tempPriceForDiscountExtra < 0) {
                    tempPriceAmount = tempPriceForDiscountExtra + (tempDiscountExtraPrice * -1);
                } else {
                    tempPriceAmount = tempPriceForDiscountExtra + tempDiscountExtraPrice;
                }
            }

            GlobalMemberValues.logWrite("discounttext", "tempPriceAmount2: " + tempPriceAmount + "\n");

            if (tempPriceAmount != 0) {
                //tempDiscountExtraPrice = Math.round(tempDiscountExtraPrice * 100) / 100.0;
                //tempPriceAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempPriceAmount, "2"));
            }

            // Tax 재설정
            // Tax 값이 0 인 경우 Tax Exempt 를 한 경우이므로, 재설정이 필요없다.
            double tempTaxAmount = 0.0;
            double tempTaxAmountSaleCart = GlobalMemberValues.getDoubleAtString(tempTempSaleCart.mSTaxAmount);
            boolean is_tax_multiYN = GlobalMemberValues.isTaxTypeMulti("S", tempTempSaleCart.mSvcidx);
            if (tempTaxAmountSaleCart > 0) {
                switch (tempSaveType) {
                    case "0" : {
                        if (is_tax_multiYN){
                            tempTaxAmount = tempTaxAmountSaleCart - (tempTaxAmountSaleCart * tempRateDouble * 0.01);
                        } else {
                            tempTaxAmount = tempPriceAmount * GlobalMemberValues.STORE_SERVICE_TAX * 0.01;
                        }
                        break;
                    }
                    case "1" : {
                        tempTaxAmount = tempPriceAmount * GlobalMemberValues.STORE_PRODUCT_TAX * 0.01;
                        break;
                    }

                }

                GlobalMemberValues.logWrite("DiscountExtra", "변경된 세금 : " + tempTaxAmount + "\n");
            }

            tempTaxAmount= GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempTaxAmount, "2"));

            // Commission 재설정
            String tempCommissionRatioType = tempTempSaleCart.mCommissionRatioType;
            double tempCommissionAmount = 0.0;
            double tempCommissionRatioDouble = GlobalMemberValues.getDoubleAtString(tempTempSaleCart.mCommissionRatio);
            tempCommissionAmount = tempPriceAmount * tempCommissionRatioDouble * 0.01;

            // Point 재설정
            //GlobalMemberValues.logWrite("pointchecklog", "tempTempSaleCart.mPointRatio : " + tempTempSaleCart.mPointRatio + "\n");
            //GlobalMemberValues.logWrite("pointchecklog", "tempPriceAmount : " + tempPriceAmount + "\n");
            double tempPointAmount = 0.0;
            double tempPointRatioDouble = GlobalMemberValues.getDoubleAtString(tempTempSaleCart.mPointRatio);
            tempPointAmount = tempPriceAmount * tempPointRatioDouble * 0.01;

            // temp_salecart DB 테이블 수정 ------------------------------------------------
            String tempUpdateAddSql_dcextype = "";
            if (GlobalMemberValues.isStrEmpty(tempTempSaleCart.selectedDcExtraType)) {
                tempUpdateAddSql_dcextype = " selectedDcExtraType = '" + tempDcExtraType + "', ";
            }

            String tempUpdateAddSql_dcexprice = "";
            tempUpdateAddSql_dcexprice = " selectedDcExtraPrice = '" + String.valueOf(tempDiscountExtraPrice) + "', ";
//            if (tempDcExtraAllEach.equals("EACH")) {
//                tempUpdateAddSql_dcexprice = " selectedDcExtraPrice = '" + String.valueOf(tempDiscountExtraPrice) + "', ";
//            }

            double tempTotal = tempPriceAmount + tempTaxAmount;

            if (tempTotal <= 0) {
                tempPriceAmount = 0.0;
            }

            if (tempTotal <= 0) {
                tempTaxAmount = 0.0;
            }

            if (tempTotal <= 0) {
                tempCommissionAmount = 0.0;
            }

            if (tempTotal <= 0) {
                tempPointAmount = 0.0;
            }

            // temp_salecart idx 값 구하기
            String tempSaleCartidx = tempTempSaleCart.tempSaleCartIdx;
            String strUpdateQuery = "update temp_salecart set " +
                    " sPriceBalAmount = '" + GlobalMemberValues.getStringFormatNumber(tempPriceAmount, "2") + "', " +
                    " sTaxAmount = '" + GlobalMemberValues.getStringFormatNumber(tempTaxAmount, "2") + "', " +
                    " sTotalAmount = '" + GlobalMemberValues.getStringFormatNumber(tempTotal, "2") + "', " +
                    " sCommissionAmount = '" + GlobalMemberValues.getStringFormatNumber(tempCommissionAmount, "2") + "', " +
                    " sPointAmount = '" + GlobalMemberValues.getStringFormatNumber(tempPointAmount, "2") + "', " +
                    tempUpdateAddSql_dcexprice +
                    tempUpdateAddSql_dcextype +
                    " selectedDcExtraAllEach = '" + tempDcExtraAllEach + "', " +        // Discount 관련처리
                    " couponNumber = '" + tempCouponNumber + "', " +
                    " discountbuttonname = '" + tempDiscountButtonName + "', " +         // Discount Button 추가 관련
                    " dcextratype = '" + tempRateType + "', " +                          // Discount 관련처리
                    " dcextravalue = '" + tempRateDouble + "', " +                        // Discount 관련처리
                    " isCloudUpload = 0 " +
                    " where idx = '" + tempSaleCartidx + "' ";
            GlobalMemberValues.logWrite("pointchecklog", "query : " + strUpdateQuery + "\n");
            GlobalMemberValues.logWrite("pointchecklog", "mCouponNumberVec : " + mCouponNumberVec.toString() + "\n");
            String returnCode = dbInit.dbExecuteWriteReturnValue(strUpdateQuery);
            // ---------------------------------------------------------------------------
            String tempEachDiscountExtraStr = "";
            // DB 수정이 정상적으로 되었을 경우에만..
            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                GlobalMemberValues.logWrite("couponnumberchecklog", "tempNowCouponNum : " + tempNowCouponNum + "\n");
                if (!GlobalMemberValues.isStrEmpty(tempNowCouponNum)) {
                    mCouponNumberVec.addElement(tempNowCouponNum);
                }
                GlobalMemberValues.logWrite("couponnumberchecklog", "mCouponNumberVec : " + mCouponNumberVec.toString() + "\n");
                setEmptyCouponNumber();

                // TemporarySaleCart 객체 수정 -------------------------------------------------
                // TemporarySaleCart 의 selectedDcExtraPrice 변수 수정

                tempTempSaleCart.mDcExtraForReturn = GlobalMemberValues.getStringFormatNumber(tempDiscountExtraPrice, "2");

                GlobalMemberValues.logWrite("discountchecklog", "mDcExtraForReturn : " + tempTempSaleCart.mDcExtraForReturn + "\n");

                if (tempDcExtraAllEach.equals("EACH")) {
                    // 개별 할인/추가 일 경우에만 금액수정을 한다.
                    tempTempSaleCart.selectedDcExtraPrice
                            = GlobalMemberValues.getStringFormatNumber(tempDiscountExtraPrice, "2");
                    String tempDcExType = "";
                    if (tempDcExtraType.equals("DC")) {
                        tempDcExType = "Each Discount";
                    } else {
                        tempDcExType = "Each Extra";
                    }

                    // 08.16.2022
                    if (!GlobalMemberValues.isStrEmpty(tempDiscountButtonName)) {
                        tempDcExType += "(" + tempDiscountButtonName + ")";
                    }

                    if (tempRateType.equals("%")) {
                        tempEachDiscountExtraStr = tempDcExType + " " + tempRate + "%";
                    } else {
                        tempEachDiscountExtraStr = tempDcExType + " $" + tempRate;
                    }

                    tempTempSaleCart.eachDcExtraStr = tempEachDiscountExtraStr;
                } else {
                    String tempDcExType = "";
                    if (tempDcExtraType.equals("DC")) {
                        tempDcExType = "All Discount";
                    } else {
                        tempDcExType = "All Extra";
                    }

                    // 08.16.2022
                    if (!GlobalMemberValues.isStrEmpty(tempDiscountButtonName)) {
                        tempDcExType += "(" + tempDiscountButtonName + ")";
                    }

                    if (tempRateType.equals("%")) {
                        tempEachDiscountExtraStr = tempDcExType + " " + tempRate + "%";
                    } else {
                        tempEachDiscountExtraStr = tempDcExType + " $" + tempRate;
                    }
                    //tempTempSaleCart.eachDcExtraStr = tempEachDiscountExtraStr;
                }

                if (GlobalMemberValues.isStrEmpty(tempTempSaleCart.selectedDcExtraParentTempSaleCartIdx)) {
                    double totalAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempPriceAmount, "2"))
                            + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempTaxAmount, "2"));

                    // TemporarySaleCart 의 couponNumber 변수 수정
                    tempTempSaleCart.couponNumber = tempCouponNumber;
                    // TemporarySaleCart 의 mSPriceBalAmount 변수 수정
                    tempTempSaleCart.mSPriceBalAmount = GlobalMemberValues.getStringFormatNumber(tempPriceAmount, "2");
                    // TemporarySaleCart 의 mSTaxAmount 변수 수정
                    tempTempSaleCart.mSTaxAmount = GlobalMemberValues.getStringFormatNumber(tempTaxAmount, "2");
                    // TemporarySaleCart 의 mSTotalAmount 변수 수정
                    tempTempSaleCart.mSTotalAmount = GlobalMemberValues.getStringFormatNumber(totalAmount, "2") + "";
                    // TemporarySaleCart 의 mSCommission 변수 수정
                    tempTempSaleCart.mSCommission = GlobalMemberValues.getStringFormatNumber(tempCommissionAmount, "2");
                    // TemporarySaleCart 의 mSPointAmount 변수 수정
                    tempTempSaleCart.mSPointAmount = GlobalMemberValues.getStringFormatNumber(tempPointAmount, "2");
                }

                // TemporarySaleCart 의 selectedDcExtraType 변수 수정
                if (GlobalMemberValues.isStrEmpty(tempTempSaleCart.selectedDcExtraType)) {
                    tempTempSaleCart.selectedDcExtraType = tempDcExtraType;
                    // TemporarySaleCart 의 selectedDcExtraType 변수 수정
                    tempTempSaleCart.selectedDcExtraAllEach = tempDcExtraAllEach;
                }

                // Discount Button 관련 추가
                // TemporarySaleCart 의 discountButtonName 변수 수정
                if (!GlobalMemberValues.isStrEmpty(tempDiscountButtonName)) {
                    tempTempSaleCart.discountButtonName = tempDiscountButtonName;
                }

                // ---------------------------------------------------------------------------

                String insTempDcExtraPrice = GlobalMemberValues.getStringFormatNumber(tempDiscountExtraPrice, "2");
                if (tempDcExtraType.equals("DC")) {
                    insTempDcExtraPrice = "-" + insTempDcExtraPrice;
                }

                // 리스트뷰에 아이템 추가 ----------------------------------------------------------
                if (tempAddLvFlag == 0) {
                    // 고객 및 직원 정보 셋팅--------------------
                    setCustomerEmployeeInfo();
                    // --------------------------------------

                    int addPosition = paramAddPosition + 1;
                    String insTempDcExtraPriceStr = insTempDcExtraPrice;

                    if (tempDcExtraAllEach.equals("ALL")) {
                        addPosition = 9999;
                        double discountExtraPriceForDisplay = 0.0;
                        if (tempRateType == "%") {
                            discountExtraPriceForDisplay = tempTotalAmountDouble * tempRateDouble * 0.01;

                            // addup jihun
                            /**
                             for (int i = 0; i < MainMiddleService.mGeneralArrayList.size(); i++) {
                             TemporarySaleCart tempTempSaleCart2 = MainMiddleService.mGeneralArrayList.get(i);

                             double item_amount = GlobalMemberValues.getDoubleAtString(tempTempSaleCart2.mSPrice);

                             double temp_item_amount = item_amount * tempRateDouble * 0.01;

                             //discountExtraPriceForDisplay += Math.round(temp_item_amount * 100)/100.0;
                             discountExtraPriceForDisplay += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(temp_item_amount, "2"));
                             }
                             **/
                        } else {
                            discountExtraPriceForDisplay = tempRateDouble;
                        }
                        if (tempDcExtraType.equals("DC")) {
                            insTempDcExtraPriceStr = "-" + discountExtraPriceForDisplay;
                        } else {
                            insTempDcExtraPriceStr = "" + discountExtraPriceForDisplay;
                        }

                        GlobalMemberValues.logWrite("discounttext", "insTempDcExtraPriceStr : " + insTempDcExtraPriceStr + "\n");

                        MainMiddleService.mIsAllDiscount = "Y";
                    } else {
                        if (MainMiddleService.discountExtraPositionVector == null) {
                            MainMiddleService.discountExtraPositionVector = new Vector<String>();
                        }
                        MainMiddleService.discountExtraPositionVector.addElement("dcex_" + String.valueOf(paramAddPosition));
                        GlobalMemberValues.logWrite("MainMiddleService",
                                "DiscountExtraPosition index of : " + "dcex_" + String.valueOf(paramAddPosition) + "\n");
                    }

                    // 10272022
                    // common gratuity 관련
                    // GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                    String paramsTempSaleCartArr[] = {
                            MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,"", "",
                            tempEachDiscountExtraStr,"", "", "","", "", "","", "", "","",
                            insTempDcExtraPriceStr,      // Total 에 Discount / Extra Price 를 넣는다.
                            "", "", "", "","", insCustomerId, insCustomerName,insCustomerPhone, tempMSaveType, insEmpIdx, insEmpName,
                            "", "","", "", "",
                            insTempDcExtraPriceStr, tempDcExtraType, tempDcExtraAllEach, tempCouponNumber, tempSaleCartidx,
                            "", "", "", insTempDcExtraPriceStr
                    };

                    GlobalMemberValues.logWrite("multicheckedlogDc", "여기실행2" + "\n");

                    TemporarySaleCart tempSaleC = new TemporarySaleCart(paramsTempSaleCartArr);
                    // 할인/추가 Discount / Extra 를 적용한 TemporarySaleCart 객체
                    tempSaleC.selectedDcExtraParentTemporarySaleCart = tempTempSaleCart;

                    MainMiddleService.addListViewItemForDiscountExtra(tempSaleC, addPosition);

                    MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                    // 10272022
                    // common gratuity 관련
                    // GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                }
                // ----------------------------------------------------------------------------


                // 07112024
                GlobalMemberValues gm = new GlobalMemberValues();
                if (gm.isPOSWebPay() && (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                    GlobalMemberValues.logWrite("discountdatauploadlogjjj", "장바구니데이터 업로드 진입" + "\n");
                    GlobalMemberValues.setSendCartToCloud(MainActivity.mContext, MainActivity.mActivity);
                }

            }
        }
    }

    private static boolean isCheckDiscountApply(TemporarySaleCart paramTempSaleCart, String paramMSaveType) {
        boolean returnValue = false;

        GlobalMemberValues.logWrite("discountapplylog", "paramMSaveType : " + paramMSaveType + "\n");
        GlobalMemberValues.logWrite("discountapplylog", "mCouponApplyType : " + mCouponApplyType + "\n");

        if (paramMSaveType.equals("9")) {
            returnValue = true;
        } else {
            String tSaveType = paramTempSaleCart.mSaveType;

            if (!GlobalMemberValues.isStrEmpty(mCouponApplyType) && !mCouponApplyType.equals("B")) {
                String tempCouponApplyType = "";
                if (mCouponApplyType.equals("S")) {
                    tempCouponApplyType = "0";
                } else {
                    tempCouponApplyType = "1";
                }

                GlobalMemberValues.logWrite("discountapplylog", "tempCouponApplyType : " + tempCouponApplyType + "\n");
                GlobalMemberValues.logWrite("discountapplylog", "tSaveType : " + tSaveType + "\n");

                if (!("JJJ" + tSaveType).equals("JJJ" + tempCouponApplyType)) {
                    returnValue = false;
                } else {
                    // 해당 서비스(푸드) 또는 상품이 Discount 가 가능한지 여부를 파악한다.
                    returnValue = isCheckDiscountApplyOnServiceProduct(paramTempSaleCart.mSvcidx, tSaveType);
                }
            } else {
                // 해당 서비스(푸드) 또는 상품이 Discount 가 가능한지 여부를 파악한다.
                returnValue = isCheckDiscountApplyOnServiceProduct(paramTempSaleCart.mSvcidx, tSaveType);
            }
        }

        GlobalMemberValues.logWrite("discountapplylog", "returnValue : " + returnValue + "\n");

        return returnValue;
    }

    private static boolean isCheckDiscountApplyOnServiceProduct(String paramSvcIdx, String paramSaveType) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramSvcIdx)) {
            if (GlobalMemberValues.isStrEmpty(paramSaveType)) {
                paramSaveType = "0";
            }

            String tempTbName = "salon_storeservice_sub";
            if (paramSaveType.equals("0") || paramSaveType == "0") {
                tempTbName = "salon_storeservice_sub";
            } else {
                tempTbName = "salon_storeproduct";
            }

            String dcapplyyn = MainActivity.mDbInit.dbExecuteReadReturnString("select dcapplyyn from " + tempTbName +
                    " where idx = '" + paramSvcIdx + "' ");

            GlobalMemberValues.logWrite("discountapplylog", "sqlquery : " + "select dcapplyyn from " + tempTbName +
                    " where idx = '" + paramSvcIdx + "' " + "\n");

            if (GlobalMemberValues.isStrEmpty(dcapplyyn)) {
                dcapplyyn = "Y";
            }

            if (dcapplyyn.equals("Y") || dcapplyyn == "Y") {
                returnValue = true;
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }

    public static void closeProgressDialog() {
        // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
        if (Discount.checkingProDial !=null && Discount.checkingProDial.isShowing()) {
            Discount.checkingProDial.cancel();
        }
        // -------------------------------------------------------------------------------------
    }

    public static void cancelDiscountExtra(TemporarySaleCart paramTemporarySaleCart, String paramParentSaleCartIdx,
                                           int paramSelectedPosition, String paramAllEachType, String paramHoldCode) {
        GlobalMemberValues.logWrite("discountcancellog", "paramParentSaleCartIdx : " + paramParentSaleCartIdx + "\n");
        GlobalMemberValues.logWrite("discountcancellog", "paramSelectedPosition : " + paramSelectedPosition + "\n");

        if (GlobalMemberValues.isStrEmpty(paramAllEachType)) {
            paramAllEachType = "ALL";
        } else {
            paramAllEachType = paramAllEachType.toUpperCase();
        }

        GlobalMemberValues.logWrite("discountcancellog", "paramAllEachType : " + paramAllEachType + "\n");
        GlobalMemberValues.logWrite("discountcancellog", "paramHoldCode : " + paramHoldCode + "\n");

        String tempSearchQuery = "";
        boolean isPossible = true;
        if (paramAllEachType.equals("ALL")) {
            tempSearchQuery = " holdcode = '" + paramHoldCode + "' ";
        } else {
            if (!GlobalMemberValues.isStrEmpty(paramParentSaleCartIdx) && !GlobalMemberValues.isStrEmpty(paramSelectedPosition + "")) {
                isPossible = true;
            } else {
                isPossible = false;
            }
            tempSearchQuery = " idx = '" + paramParentSaleCartIdx + "' ";
        }

        if (isPossible) {
            boolean is_delete_commonGratuity = false;
            // 10272022
            // common gratuity 관련
            is_delete_commonGratuity = GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();


            String returnResult = "";

            Vector<String> strQueryVec = new Vector<String>();

            // Discount / Extra 처리된 부분을 원래대로 돌려놓는다.
            String sqlQuery = "";
            sqlQuery = " update temp_salecart set " +
                    " sPriceBalAmount = sPriceBalAmount_org * sQty, " +
                    " sTaxAmount = sTaxAmount_org * sQty, " +
                    " sTotalAmount = sTotalAmount_org * sQty, " +
                    " sCommissionAmount = sCommissionAmount_org * sQty, " +
                    " sPointAmount = sPointAmount_org  * sQty, " +
                    " selectedDcExtraPrice = 0, " +
                    " selectedDcExtraType = '', " +
                    " selectedDcExtraAllEach = '', " +
                    " dcextravalue = 0, " +
                    " isCloudUpload = 0 " +
                    " where " + tempSearchQuery;

            // salon_sales_detail 입력쿼리를 백터 strQueryVec 에 담는다.
            strQueryVec.addElement(sqlQuery);

            for (String tempQuery : strQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
            GlobalMemberValues.logWrite("dbinsertlog", "returnResult : " + returnResult + "\n");
            if (returnResult == "N" || returnResult == "") {
            } else {
                if (paramTemporarySaleCart != null && paramAllEachType.equals("EACH")) {
                    String tempQty = paramTemporarySaleCart.mSQty;
                    if (GlobalMemberValues.isStrEmpty(tempQty)) {
                        tempQty = "1";
                    }
                    int tempQtyInt = GlobalMemberValues.getIntAtString(tempQty);

                    paramTemporarySaleCart.selectedDcExtraPrice = "";
                    paramTemporarySaleCart.eachDcExtraStr = "";
                    paramTemporarySaleCart.couponNumber = "";

                    // TemporarySaleCart 의 mSPriceBalAmount 변수 수정
                    paramTemporarySaleCart.mSPriceBalAmount
                            = GlobalMemberValues.getDoubleAtString(paramTemporarySaleCart.mSPriceBalAmount_org) * tempQtyInt + "";

                    // TemporarySaleCart 의 mSTaxAmount 변수 수정
                    paramTemporarySaleCart.mSTaxAmount
                            = GlobalMemberValues.getDoubleAtString(paramTemporarySaleCart.mSTaxAmount_org) * tempQtyInt + "";

                    // TemporarySaleCart 의 mSTotalAmount 변수 수정
                    paramTemporarySaleCart.mSTotalAmount
                            = GlobalMemberValues.getDoubleAtString(paramTemporarySaleCart.mSTotalAmount_org) * tempQtyInt + "";

                    // TemporarySaleCart 의 mSCommission 변수 수정
                    paramTemporarySaleCart.mSCommission
                            = GlobalMemberValues.getDoubleAtString(paramTemporarySaleCart.mSCommissionAmount_org) * tempQtyInt + "";

                    // TemporarySaleCart 의 mSPointAmount 변수 수정
                    paramTemporarySaleCart.mSPointAmount
                            = GlobalMemberValues.getDoubleAtString(paramTemporarySaleCart.mSPointAmount_org) * tempQtyInt + "";

                    paramTemporarySaleCart.selectedDcExtraType = "";
                    // TemporarySaleCart 의 selectedDcExtraType 변수 수정
                    paramTemporarySaleCart.selectedDcExtraAllEach = "";
                    paramTemporarySaleCart.discountButtonName = "";

                    paramTemporarySaleCart.mDcExtraForReturn = "0";
                }

                if (paramAllEachType.equals("ALL")) {
                    for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
                        String tempQty = tempSaleCart.mSQty;
                        if (GlobalMemberValues.isStrEmpty(tempQty)) {
                            tempQty = "1";
                        }
                        int tempQtyInt = GlobalMemberValues.getIntAtString(tempQty);

                        tempSaleCart.selectedDcExtraPrice = "";
                        tempSaleCart.eachDcExtraStr = "";
                        tempSaleCart.couponNumber = "";


                        // TemporarySaleCart 의 mSPriceBalAmount 변수 수정
                        if (!tempSaleCart.mSPriceBalAmount_org.matches("[+-]?\\d*(\\.\\d+)?")){
                            tempSaleCart.mSPriceBalAmount_org = "0.00";
                        }
                        if (!tempSaleCart.mSTaxAmount_org.matches("[+-]?\\d*(\\.\\d+)?")){
                            tempSaleCart.mSTaxAmount_org = "0.00";
                        }
                        if (!tempSaleCart.mSTotalAmount_org.matches("[+-]?\\d*(\\.\\d+)?")){
                            tempSaleCart.mSTotalAmount_org = "0.00";
                        }
                        if (!tempSaleCart.mSCommissionAmount_org.matches("[+-]?\\d*(\\.\\d+)?")){
                            tempSaleCart.mSCommissionAmount_org = "0.00";
                        }
                        if (!tempSaleCart.mSPointAmount_org.matches("[+-]?\\d*(\\.\\d+)?")){
                            tempSaleCart.mSPointAmount_org = "0.00";
                        }

                        tempSaleCart.mSPriceBalAmount
                                = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSPriceBalAmount_org) * tempQtyInt + "";

                        // TemporarySaleCart 의 mSTaxAmount 변수 수정
                        tempSaleCart.mSTaxAmount
                                = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSTaxAmount_org) * tempQtyInt + "";

                        // TemporarySaleCart 의 mSTotalAmount 변수 수정
                        tempSaleCart.mSTotalAmount
                                = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSTotalAmount_org) * tempQtyInt + "";

                        // TemporarySaleCart 의 mSCommission 변수 수정
                        tempSaleCart.mSCommission
                                = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSCommissionAmount_org) * tempQtyInt + "";

                        // TemporarySaleCart 의 mSPointAmount 변수 수정
                        tempSaleCart.mSPointAmount
                                = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSPointAmount_org) * tempQtyInt + "";


                        tempSaleCart.selectedDcExtraType = "";
                        // TemporarySaleCart 의 selectedDcExtraType 변수 수정
                        tempSaleCart.selectedDcExtraAllEach = "";
                        tempSaleCart.discountButtonName = "";

                        tempSaleCart.mDcExtraForReturn = "0";
                    }

                    MainMiddleService.mIsAllDiscount = "N";
                }
                if (is_delete_commonGratuity && paramSelectedPosition > 0 && paramAllEachType.equals("ALL")) paramSelectedPosition = paramSelectedPosition -1;
                MainMiddleService.deleteItem(paramSelectedPosition, false, false, "");

                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();        // 추가된 항목을 Adapter 에 알림
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();
                }
                // getCalcSubTotalTaxTotalValue 메소드로 구해온 값들을 Sub Total, Tax, Total 텍스트뷰에 넣는다.
                MainMiddleService.setCalcSubTotalTaxTotalValue(MainMiddleService.getCalcSubTotalTaxTotalValue(MainMiddleService.mGeneralArrayList));


                // 07112024
                GlobalMemberValues gm = new GlobalMemberValues();
                if (gm.isPOSWebPay() && (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                    GlobalMemberValues.logWrite("discountdatauploadlogjjj", "장바구니데이터 업로드 진입" + "\n");
                    GlobalMemberValues.setSendCartToCloud(MainActivity.mContext, MainActivity.mActivity);
                }
            }


            // 10272022
            // common gratuity 관련
            GlobalMemberValues.addCartLastItemForCommonGratuityUse();
        }
    }
}
