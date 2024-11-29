package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class QuickSale {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    DatabaseInit dbInit;

    // 카테고리 인덱스
    String mCategoryIdx;
    // 카테고리 이름
    String mCategoryName;

    // Service / Product 여부
    String saveType;

    String mPriceEditTextValue = "";

    Button closeBtn;
    Button preClickButton;

    private TextView quickSaleTitleEditText;
    private TextView quickSaleServiceProductNameTitleTextView, quickSalePriceTitleTextView;

    private Button quickSaleSuButton1,quickSaleSuButton2,quickSaleSuButton3;
    private Button quickSaleSuButton4,quickSaleSuButton5,quickSaleSuButton6;
    private Button quickSaleSuButton7,quickSaleSuButton8,quickSaleSuButton9;
    private Button quickSaleSuButton0,quickSaleSuButton00,quickSaleSuButtonBack;
    private Button quickSaleSuButtonV;

    private Switch quickkitchenprintynSwitch;

    RadioGroup mRadioGroup;
    RadioButton quickSaleServiceRadioButton, quickSaleProductRadioButton;
    EditText mServiceProductNameEditText, mPrice;

    public QuickSale(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // QuickSale 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setOnClickListener(quickSaleBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_QUICK.setOnClickListener(quickSaleBtnClickListener);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        // saveType 기본설정 (서비스로 설정)
        saveType = "0";
    }

    public void setContents() {
        // 초기화
        saveType = "0";
        mCategoryIdx = "";
        mCategoryName = "";
        mPriceEditTextValue = "";
        preClickButton = null;

        /** 카테고리 배치 ****************************************************************************************/
        // 카테고리 배열 가져오기 (GetDataAtSQLite 클래스의 getCategoryInfo 메소드를 통해 가져온다)
        String[] strArrCategory = dataAtSqlite.getCategoryInfo();

        String strCateInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempCategoryPositionNo = "";     // 임시 PositionNo 를 저장할 객체 선언

        MainTopCategory mainTopCate = new MainTopCategory(mActivity, context, dataAtSqlite, 1);
        String firstCateIdx = mainTopCate.getFirstCategoryIdx();
        mCategoryIdx = firstCateIdx;

        // 첫번째 카테고리의 배경색을 선언 및 초기화
        String firstCateBGColor = GlobalMemberValues.CATEGORYCOLORVALUE[0];

        String firstCategoryName = "";
        // 해당 스토어의 최대 카테고리수(GlobalMemberValues.STOREMAXCATEGORYSU) 만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++){
            if (strArrCategory[i] != null && strArrCategory[i] != "" && !strArrCategory[i].equals("")) {
                strCateInfo = strArrCategory[i];
                String[] strCateInfoArr = strCateInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempCategoryPositionNo = strCateInfoArr[3];
                if (Integer.parseInt(tempCategoryPositionNo) > 0) {         // 포지션값이 0 이상 값일때에만
                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    Button quickSaleCategoryBtn
                            = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                            .findViewWithTag("quickSaleCategoryButtonTag" + tempCategoryPositionNo);
                    TextView quickSaleCategoryTv
                            = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                            .findViewWithTag("quickSaleCategoryTextViewTag" + tempCategoryPositionNo);

                    quickSaleCategoryBtn.setText(strCateInfoArr[1]);
                    quickSaleCategoryBtn.setBackgroundResource(R.drawable.roundlayout_button_topcategory);
                    quickSaleCategoryBtn.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKTEXTCOLOR1));

                    if (i == 0) {
                        firstCategoryName = strCateInfoArr[1];
                    }

                    //quickSaleCategoryBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
                    quickSaleCategoryBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

                    quickSaleCategoryBtn.setPadding(3, 3, 3, 3);

                    quickSaleCategoryTv.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));

                    /**
                    // 첫번째 카테고리일 경우 버튼 배경색을 TextView 배경색과 동일하게 한다. (눌려진 느낌)
                    if (strCateInfoArr[0].equals(firstCateIdx)) {
                        quickSaleCategoryBtn.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));
                        quickSaleCategoryBtn.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_ONCLICKTEXTCOLOR1));
                        preClickButton = quickSaleCategoryBtn;
                        firstCateBGColor = strCateInfoArr[2];
                    }
                     **/

                    // Category 의 정보를 임시로 저장하기 위한 클래스인 TemporaryCategoryInfo 를 이용하여 카테고리정보 저장
                    TemporaryCategoryInfo tempCategoryInfo
                            = new TemporaryCategoryInfo(strCateInfoArr[0], strCateInfoArr[1], strCateInfoArr[2], strCateInfoArr[3]);
                    // 위에서 생성한 클래스를 Tag 값으로 저장 (setTag)
                    // setTag 로 객체 저장시 카테고리버튼의 아이디를 KEY 값으로 사용한다.
                    if (tempCategoryInfo != null) {
                        quickSaleCategoryBtn.setTag(quickSaleCategoryBtn.getId(), tempCategoryInfo);
                    }

                    // 카테고리 클릭시 이벤트
                    quickSaleCategoryBtn.setOnClickListener(mCategoryButtonListner);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrCategory[i] + "\n");
            }
        }
        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleCloseBtnTag");
        closeBtn.setOnClickListener(quickSaleBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        quickSaleTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleTitleEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleTitleEditText.setText("");
            quickSaleTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_title);
        } else {
            quickSaleTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    quickSaleTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        quickSaleServiceProductNameTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleServiceProductNameTitleTextViewTag");
        quickSaleServiceProductNameTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        quickSalePriceTitleTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSalePriceTitleTextViewTag");
        quickSalePriceTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);


        // 라디오 버튼 생성 및 리스너 연결
        mRadioGroup = (RadioGroup)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleServiceProductRadioGroupTag");
        mRadioGroup.setOnCheckedChangeListener(mRadioChange);

        quickSaleServiceRadioButton = (RadioButton)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleServiceRadioButtonTag");
        quickSaleServiceRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        quickSaleProductRadioButton = (RadioButton)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleProductRadioButtonTag");
        quickSaleProductRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);

        // 서비스 또는 상품명 EditText 객체 생성 및 리스너 연결
        mServiceProductNameEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleServiceProductNameEditTextTag");
        //mServiceProductNameEditText.setOnTouchListener(mEditTextTouch);
        //mServiceProductNameEditText.setText(firstCategoryName);

        // 가격 EditText 객체 생성 및 리스너 연결
        mPrice = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSalePriceEditTextTag");
        mPrice.setTextSize(GlobalMemberValues.globalAddFontSize() + 20);
        mPrice.setOnTouchListener(mEditTextTouch);
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        quickSaleSuButton1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag1");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton1.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag2");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton2.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag3");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton3.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag4");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton4.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag5");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton5.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag6");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton6.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag7");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton7.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton8 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag8");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton8.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton9 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag9");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton9.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton0 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag0");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton0.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButton00 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTag00");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE * 1.8f
            );
            quickSaleSuButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            quickSaleSuButton00.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_number);
        } else {
            quickSaleSuButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        quickSaleSuButtonBack = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTagBack");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButtonBack.setText("");
            quickSaleSuButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_delete);
        }
        quickSaleSuButtonV = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickSaleSuButtonTagV");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            quickSaleSuButtonV.setText("");
            quickSaleSuButtonV.setBackgroundResource(R.drawable.ab_imagebutton_quicksale_enter);
        }

        quickSaleSuButton1.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton2.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton3.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton4.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton5.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton6.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton7.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton8.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton9.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton0.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButton00.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButtonBack.setOnClickListener(quickSaleBtnClickListener);
        quickSaleSuButtonV.setOnClickListener(quickSaleBtnClickListener);
        /***********************************************************************************************************/

        quickkitchenprintynSwitch = (Switch)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE
                .findViewWithTag("quickkitchenprintynSwitchTag");

        quickkitchenprintynSwitch.setChecked(true);

        // 초기화
        mRadioGroup.check(R.id.quickSaleServiceRadioButton);
        mServiceProductNameEditText.setText("");
        mPrice.setText("");

        GlobalMemberValues.setKeyPadHide(context, mServiceProductNameEditText);
        GlobalMemberValues.setKeyPadHide(context, mPrice);
    }

    View.OnClickListener mCategoryButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // 클릭한 카테고리 버튼이 바로 이전에 클릭된 버튼 (preClickButton) 이 아닐 경우에만..
            if (btn != preClickButton) {
                LogsSave.saveLogsInDB(66);
                // 먼저 선택된 카테고리 명을 Clear...
                mServiceProductNameEditText.setText("");

                /** 전에 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                if (preClickButton != null) {
                    preClickButton.setBackgroundColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKBACKGROUNDCOLOR1));
                    preClickButton.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKTEXTCOLOR1));
                }
                /*****************************************************************************************/

                // 현재 클릭한 카테고리버튼에 Tag 저장되어 있는 TemporaryCategory 객체를 가져온다.
                TemporaryCategoryInfo tempCif = (TemporaryCategoryInfo)btn.getTag(btn.getId());
                // ★★★★★★★★반드시 해당 객체가 있는지 체크해 준다★★★★★★★★
                if (tempCif != null) {
                    /** 현재 클릭한 카테고리의 Color 값을 저장한다.****************************************************/
                    String tempCateColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[0];
                    if (!GlobalMemberValues.isStrEmpty(tempCif.categoryColorNo)) {
                        tempCateColorValue = tempCif.categoryColorNo;
                        //GlobalMemberValues.logWrite("CategoryColorNo", "Color : " + tempCif.categoryColorNo);
                    }
                    /*****************************************************************************************/

                    /** 현재 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    btn.setBackgroundColor(Color.parseColor(tempCateColorValue));
                    btn.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_ONCLICKTEXTCOLOR1));
                    /*****************************************************************************************/

                    if (tempCif.categoryIdx != null) {
                        //GlobalMemberValues.logWrite("MainTopCategory-TempInstance", "카테고리 인덱스 : " + paramCateIdx + "\n");
                        mCategoryIdx = tempCif.categoryIdx;
                        mCategoryName = tempCif.categoryName;
                    } else {
                        //
                    }

                    // 현재 클릭한 버튼객체를 preClickButton 에 담는다.
                    preClickButton = btn;

                    // 상품명 입력이 없을 경우
                    if (!GlobalMemberValues.isStrEmpty(mCategoryName) &&
                            GlobalMemberValues.isStrEmpty(mServiceProductNameEditText.getText().toString())) {
                        mServiceProductNameEditText.setText(mCategoryName);
                    }

                    mPrice.requestFocus();
                }
            }
        }
    };

    View.OnClickListener quickSaleBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_main_side_quick :
                case R.id.mainRightBottom_QuickSale : {
                    if (!GlobalMemberValues.checkEmployeePermission( TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<20>")){
                        GlobalMemberValues.displayDialog(mActivity, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------
                    LogsSave.saveLogsInDB(103);
                    GlobalMemberValues.setFrameLayoutVisibleChange("quickSale");
                    setContents();

                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        // 하단버튼 초기화
                        GlobalMemberValues.setInitMainBottomButtonBg();
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setBackgroundResource(R.drawable.aa_images_main_quicksale_rollover_lite);
                    }
                    break;
                }
                case R.id.quickSaleCloseBtn : {
                    saveType = "0";
                    mCategoryIdx = "";
                    mCategoryName = "";
                    mPriceEditTextValue = "";
                    mRadioGroup.check(R.id.quickSaleServiceRadioButton);
                    mServiceProductNameEditText.setText("");
                    mPrice.setText("");
                    preClickButton = null;

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);

                    GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    break;
                }
                case R.id.quickSaleSuButton1 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton1);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton2 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton2);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton3 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton3);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton4 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton4);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton5 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton5);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton6 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton6);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton7 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton7);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton8 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton8);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton9 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton9);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton0 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton0);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButton00 : {
                    LogsSave.saveLogsInDB(67);
                    numberButtonClick(quickSaleSuButton00);

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);
                    break;
                }
                case R.id.quickSaleSuButtonBack : {
                    LogsSave.saveLogsInDB(68);
                    if (mPrice.isFocused()) {
                        StringBuilder sb = new StringBuilder();
                        sb.delete(0, sb.toString().length());
                        sb.append(mPriceEditTextValue);
                        if (!GlobalMemberValues.isStrEmpty(mPriceEditTextValue)) {
                            sb.delete((sb.toString().length()-1), sb.toString().length());
                            mPriceEditTextValue = sb.toString();
                            if (GlobalMemberValues.isStrEmpty(mPriceEditTextValue)) {
                                mPriceEditTextValue = "0";
                            }
                        } else {
                            mPriceEditTextValue = "0";
                        }

                        String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
                        mPrice.setText(inputSu);

                        GlobalMemberValues.logWrite("QuickSale", "mPriceEditTextValue : " + mPriceEditTextValue + "\n");

                        // 키패드(키보드) 감추기
                        GlobalMemberValues.setKeyPadHide(context, mPrice);
                        // 커서를 맨 뒤로...
                        GlobalMemberValues.setCursorLastDigit(mPrice);
                    }

                    break;
                }
                case R.id.quickSaleSuButtonV : {
                    if (GlobalMemberValues.isStrEmpty(mServiceProductNameEditText.getText().toString())) {
                        GlobalMemberValues.displayDialog(context, "Warning",
                                "Enter Category / Product Name", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isStrEmpty(mPrice.getText().toString())) {
                        GlobalMemberValues.displayDialog(context, "Warning",
                                "Enter Price", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(69);
                    String sHoldCode = MainMiddleService.mHoldCode;                      // 홀드코드 (처음저장시에는 값이 없음)
                    String sQty = "1";                          // 수량 (처음저장시 무조건 1)
                    String sSaveType = saveType;                     // 0 : 서비스       1: 상품          2: 기프트 카드

                    String insCastegoryIdx = mCategoryIdx;                                // 카테고리 인덱스
                    String insSvcIdx = "0";                       // Quick Sale 의 경우에는 서비스 인덱스가 무조건 0

                    String insServiceProductName = mServiceProductNameEditText.getText().toString();
                    String insStrFileName = "";
                    String insStrFilePath = "";

                    String insPrice
                            = GlobalMemberValues.getReplaceText(mPrice.getText().toString(), "$", "");
                    String insSalePrice = "0";
                    String insSaleStart = "";
                    String insSaleEnd = "";

                    //String insCommissionRatio = String.valueOf(GlobalMemberValues.QUICKSALE_COMMISSIONRATIO);
                    String tempCommissionRatio = dbInit.dbExecuteReadReturnString("select commissionratio from salon_storeservice_main where idx = '"+ insCastegoryIdx + "' ");
                    String insCommissionRatio = tempCommissionRatio;
                    String insCommissionType = String.valueOf(GlobalMemberValues.QUICKSALE_COMMISSIONRATIOTYPE);

                    //String insPointRatio = String.valueOf(GlobalMemberValues.QUICKSALE_POINTRATIO);
                    String tempPointratio = dbInit.dbExecuteReadReturnString("select pointratio from salon_storeservice_main where idx = '"+ insCastegoryIdx + "' ");
                    String insPointRatio = tempPointratio;

                    String insPositoinNo = "";
                    String insSetMenyYN = "N";

                    String insQuickSaleYN = "Y";                  // quick sale 여부

                    String insCategoryName = "QUICK SALE";

                    String insCustomerId = "";
                    String insCustomerName = "";
                    String insCustomerPhone = "";
                    if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                        insCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                        insCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                        insCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                    }

                    String insEmpIdx = "";
                    String insEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        insEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                        insEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }

                    if (GlobalMemberValues.isStrEmpty(insServiceProductName)) {
                        insServiceProductName = mCategoryName;
                    }

                    String tempQuickKitchenPrintingYN = "N";
                    if (quickkitchenprintynSwitch.isChecked()) {
                        tempQuickKitchenPrintingYN = "Y";
                    }

                    MainMiddleService.mQuickSaleKitchenPrintingYN = tempQuickKitchenPrintingYN;

                    // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                    String paramsString[] = {
                            sQty, sHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, insCastegoryIdx, insSvcIdx,
                            insServiceProductName, insStrFileName, insStrFilePath,
                            insPrice, insSalePrice, insSaleStart, insSaleEnd,
                            insCommissionRatio, insCommissionType, insPointRatio,
                            insPositoinNo, insSetMenyYN,
                            insCustomerId, insCustomerName, insCustomerPhone, sSaveType, insEmpIdx, insEmpName, insQuickSaleYN, insCategoryName};

                    String maxIdxAfterDbInsert = "";
                    //maxIdxAfterDbInsert = insertTempSaleCartForQuickSale(paramsString);

                    // common gratuity 관련
                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                    MainMiddleService.mQuickSaleYN = "Y";
                    MainMiddleService.insertTempSaleCart(paramsString);

                    // common gratuity 관련
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                    //saveType = "0";
                    //mCategoryIdx = "";
                    //mCategoryName = "";
                    mPriceEditTextValue = "";
                    mServiceProductNameEditText.setText("");
                    mPrice.setText("");
                    //preClickButton = null;

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mPrice);

                    break;
                }
            }
        }
    };


    RadioGroup.OnCheckedChangeListener mRadioChange
            = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getId() == R.id.quickSaleServiceProductRadioGroup) {
                switch (checkedId) {
                    case R.id.quickSaleServiceRadioButton : {
                        saveType = "0";
                        break;
                    }
                    case R.id.quickSaleProductRadioButton : {
                        saveType = "1";
                        break;
                    }
                }
            }
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mPrice.requestFocus();

            // 키패드(키보드) 감추기
            GlobalMemberValues.setKeyPadHide(context, mPrice);
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(mPrice);

            GlobalMemberValues.logWrite("QuickSale", "Price Touch\n");

            return true;
        }
    };

    private void numberButtonClick(Button btn) {
        if (mPrice.isFocused()) {
            StringBuilder sb = new StringBuilder();
            sb.delete(0, sb.toString().length());
            if (mPriceEditTextValue.length() < 12) {
                sb.append(mPriceEditTextValue).append(btn.getText().toString());
                Long tempNumber = Long.parseLong(sb.toString());
                mPriceEditTextValue = String.valueOf(tempNumber);
                String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
                mPrice.setText(inputSu);
            }

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(mPrice);
        }
    }
}
