package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class ProductSale {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    DatabaseInit dbInit;

    // 카테고리 인덱스
    String mCategoryIdx;

    String mPriceEditTextValue = "";

    Button closeBtn;
    Button preClickButton;

//    private Button productSaleSuButton1,productSaleSuButton2,productSaleSuButton3;
//    private Button productSaleSuButton4,productSaleSuButton5,productSaleSuButton6;
//    private Button productSaleSuButton7,productSaleSuButton8,productSaleSuButton9;
//    private Button productSaleSuButton0,productSaleSuButton00,productSaleSuButtonBack;
    private Button productSaleSuButtonList, productSaleSuButtonV;
    private Button mProductNumberSearchButton;

    EditText mProductNumberEditText, mProductNameEditText, mProductPriceEditText;

    Cursor productInfoCursor;               // 상품정보 가져와 담는 cursor

    String[] mInsParamForInsertTempSaleCart;

    // 고객 및 직원정보 관련 객체 선언
    String insCustomerId = "";
    String insCustomerName = "";
    String insCustomerPhone = "";

    String insEmpIdx = "";
    String insEmpName = "";


    public ProductSale(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // Product Sale 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.setOnClickListener(productSaleBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT.setOnClickListener(productSaleBtnClickListener);


        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);
        mInsParamForInsertTempSaleCart = null;
    }

    public void setContents() {
        // 초기화
        mCategoryIdx = "";
        mPriceEditTextValue = "";
        preClickButton = null;
        mInsParamForInsertTempSaleCart = null;

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleCloseBtnTag");
        closeBtn.setOnClickListener(productSaleBtnClickListener);

        // 상품번호 EditText 객체 생성 및 리스너 연결
        mProductNumberEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleProductNumberEditTextTag");
        // 상품번호 검색 버튼 객체 생성 및 리스너 연결
        mProductNumberSearchButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleProductNumberSearchButtonTag");
        mProductNumberSearchButton.setOnClickListener(productSaleBtnClickListener);

        // 상품명 EditText 객체 생성 및 리스너 연결
        mProductNameEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleProductNameEditTextTag");
        mProductNameEditText.setOnClickListener(mEditTextClick);
        mProductNameEditText.setOnTouchListener(mEditTextTouch);
        // 가격 EditText 객체 생성 및 리스너 연결
        mProductPriceEditText = (EditText)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleProductPriceEditTextTag");
        mProductPriceEditText.setOnClickListener(mEditTextClick);
        mProductPriceEditText.setOnTouchListener(mEditTextTouch);
        /***********************************************************************************************************/

        /** 숫자 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        /**
        productSaleSuButton1 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag1");
        productSaleSuButton2 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag2");
        productSaleSuButton3 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag3");
        productSaleSuButton4 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag4");
        productSaleSuButton5 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag5");
        productSaleSuButton6 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag6");
        productSaleSuButton7 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag7");
        productSaleSuButton8 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag8");
        productSaleSuButton9 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag9");
        productSaleSuButton0 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag0");
        productSaleSuButton00 = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTag00");
        productSaleSuButtonBack = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTagBack");
         productSaleSuButton1.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton2.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton3.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton4.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton5.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton6.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton7.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton8.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton9.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton0.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButton00.setOnClickListener(productSaleBtnClickListener);
         productSaleSuButtonBack.setOnClickListener(productSaleBtnClickListener);

         **/
        productSaleSuButtonList = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTagList");
        productSaleSuButtonV = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE
                .findViewWithTag("productSaleSuButtonTagV");

        productSaleSuButtonList.setOnClickListener(productSaleBtnClickListener);
        productSaleSuButtonV.setOnClickListener(productSaleBtnClickListener);
        /***********************************************************************************************************/

        // 초기화
        mProductNumberEditText.setText("");
        mProductNameEditText.setText("");
        mProductPriceEditText.setText("");

    }

    View.OnClickListener productSaleBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_main_side_product :
                case R.id.mainRightBottom_Product : {
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------
                    LogsSave.saveLogsInDB(104);
                    GlobalMemberValues.setFrameLayoutVisibleChange("productSale");
                    setContents();
                    break;
                }
                case R.id.productSaleCloseBtn : {
                    mCategoryIdx = "";
                    mPriceEditTextValue = "";
                    mProductNumberEditText.setText("");
                    mProductNameEditText.setText("");
                    mProductPriceEditText.setText("");
                    preClickButton = null;
                    mInsParamForInsertTempSaleCart = null;

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mProductPriceEditText);
                    GlobalMemberValues.setKeyPadHide(context, mProductNameEditText);

                    GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    break;
                }
                /**
                case R.id.productSaleSuButton1 : {
                    numberButtonClick(productSaleSuButton1);
                    break;
                }
                case R.id.productSaleSuButton2 : {
                    numberButtonClick(productSaleSuButton2);
                    break;
                }
                case R.id.productSaleSuButton3 : {
                    numberButtonClick(productSaleSuButton3);
                    break;
                }
                case R.id.productSaleSuButton4 : {
                    numberButtonClick(productSaleSuButton4);
                    break;
                }
                case R.id.productSaleSuButton5 : {
                    numberButtonClick(productSaleSuButton5);
                    break;
                }
                case R.id.productSaleSuButton6 : {
                    numberButtonClick(productSaleSuButton6);
                    break;
                }
                case R.id.productSaleSuButton7 : {
                    numberButtonClick(productSaleSuButton7);
                    break;
                }
                case R.id.productSaleSuButton8 : {
                    numberButtonClick(productSaleSuButton8);
                    break;
                }
                case R.id.productSaleSuButton9 : {
                    numberButtonClick(productSaleSuButton9);
                    break;
                }
                case R.id.productSaleSuButton0 : {
                    numberButtonClick(productSaleSuButton0);
                    break;
                }
                case R.id.productSaleSuButton00 : {
                    numberButtonClick(productSaleSuButton00);
                    break;
                }
                case R.id.productSaleSuButtonBack : {
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
                    mProductPriceEditText.setText(inputSu);

                    GlobalMemberValues.logWrite("ProductSale", "mPriceEditTextValue : " + mPriceEditTextValue + "\n");
                    break;
                }
                 **/
                case R.id.productSaleSuButtonV : {
                    if (!GlobalMemberValues.isStrEmpty(mProductNameEditText.getText().toString())) {
                        // common gratuity 관련
                        GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                        MainMiddleService.insertTempSaleCart(mInsParamForInsertTempSaleCart);
                        GlobalMemberValues.setFrameLayoutVisibleChange("main");

                        // common gratuity 관련
                        GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                        mCategoryIdx = "";
                        mPriceEditTextValue = "";
                        mProductNumberEditText.setText("");
                        mProductNameEditText.setText("");
                        mProductPriceEditText.setText("");
                        preClickButton = null;
                        mInsParamForInsertTempSaleCart = null;
                    }

                    break;
                }
                case R.id.productSaleSuButtonList : {
                    GlobalMemberValues.setFrameLayoutVisibleChange("productList");
                    ProductList productList = new ProductList(mActivity, context, dataAtSqlite);

                    mCategoryIdx = "";
                    mPriceEditTextValue = "";
                    mProductNumberEditText.setText("");
                    mProductNameEditText.setText("");
                    mProductPriceEditText.setText("");
                    preClickButton = null;
                    mInsParamForInsertTempSaleCart = null;

                    break;
                }
                case R.id.productSaleProductNumberSearchButton : {          // 상품번호 검색버튼 클릭시
                    String schStr = mProductNumberEditText.getText().toString();
                    TemporaryProductInfo getTempProductInfo = getProductInfoByProductCode(schStr);
                    if (getTempProductInfo != null) {
                        LogsSave.saveLogsInDB(71);
                        mProductNameEditText.setText(getTempProductInfo.pdName);

                        double insPdPrice = 0.00;
                        double insPdOrgPrice = 0.00;          // 세일이전 원가
                        if (!GlobalMemberValues.isStrEmpty(getTempProductInfo.pdPrice)) {
                            GlobalMemberValues.logWrite("productinfo", "pdprice : " + getTempProductInfo.pdPrice + "\n");
                            insPdPrice = GlobalMemberValues.getDoubleAtString(getTempProductInfo.pdPrice);
                            insPdOrgPrice = insPdPrice;
                        }

                        String insPdSaleYN = "N";
                        // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
                        if ((!GlobalMemberValues.isStrEmpty(getTempProductInfo.pdSaleStart)
                                && DateMethodClass.getDiffDayCount(getTempProductInfo.pdSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                                && (!GlobalMemberValues.isStrEmpty(getTempProductInfo.pdSaleEnd)
                                && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, getTempProductInfo.pdSaleEnd) >= 0)
                                &&!GlobalMemberValues.isStrEmpty(getTempProductInfo.pdSalePrice)) {
                            insPdPrice = GlobalMemberValues.getDoubleAtString(getTempProductInfo.pdSalePrice);
                            insPdSaleYN = "Y";
                        }
                        // -------------------------------------------------------------------------------------

                        mProductPriceEditText.setText(GlobalMemberValues.getCommaStringForDouble(String.valueOf(insPdPrice)));

                        setCustomerEmployeeInfo();
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

                        // MainMiddleService 클래스의 mInsParamForInsertTempSaleCart 메소드에 전달할 파라미터 배열저장
                        mInsParamForInsertTempSaleCart = new String[]{
                                "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                                "", getTempProductInfo.pdIdx,
                                getTempProductInfo.pdName, getTempProductInfo.pdStrFileName, getTempProductInfo.pdStrFilePath,
                                getTempProductInfo.pdPrice, getTempProductInfo.pdSalePrice, getTempProductInfo.pdSaleStart,
                                getTempProductInfo.pdSaleEnd, String.valueOf(GlobalMemberValues.PRODUCT_COMMISSIONRATIO),
                                GlobalMemberValues.PRODUCT_COMMISSIONRATIOTYPE, String.valueOf(GlobalMemberValues.PRODUCT_POINTRATIO),
                                "", "N", insCustomerId, insCustomerName, insCustomerPhone, "1", insEmpIdx, insEmpName, "N", ""
                        };
                    }

                    // 키패드(키보드) 감추기
                    GlobalMemberValues.setKeyPadHide(context, mProductPriceEditText);
                    GlobalMemberValues.setKeyPadHide(context, mProductNameEditText);


                    break;

                }

            }
        }
    };

    public TemporaryProductInfo getProductInfoByProductCode(String paramProductCode) {
        TemporaryProductInfo returnTempProductInfo = null;
        String searchQuery = " a.productCode = '" + paramProductCode + "' ";
        // 상품정보 가져오기 (GetDataAtSQLite 클래스의 getProductInfo 메소드를 통해 가져온다)
        productInfoCursor = dataAtSqlite.getProductInfo(searchQuery, "", "");
        if (productInfoCursor.getCount() > 0 && productInfoCursor.moveToFirst()) {
            String tempPdName = productInfoCursor.getString(2);
            // 고객 이름이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempPdName, " ", ""))) {
                String[] returnStr = new String[]{
                        String.valueOf(productInfoCursor.getInt(0)),
                        String.valueOf(productInfoCursor.getInt(1)),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(2), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(3), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(4), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(5), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(6), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(7), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(8), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(9), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(10), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(11), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(12), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(13), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(14), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(15), 1),
                        GlobalMemberValues.getDBTextAfterChecked(productInfoCursor.getString(16), 1)
                };

                returnTempProductInfo = new TemporaryProductInfo(returnStr);
            }
        }
        return returnTempProductInfo;
    }

    View.OnClickListener mEditTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.productSaleProductPriceEditText : {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mProductPriceEditText.getWindowToken(), 0);
                    GlobalMemberValues.logWrite("QuickSale", "Price Touch\n");

                    break;
                }
                case R.id.productSaleProductNameEditText : {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mProductNameEditText.getWindowToken(), 0);

                    break;
                }
            }
        }
    };

    View.OnTouchListener mEditTextTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.productSaleProductPriceEditText : {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mProductPriceEditText.getWindowToken(), 0);
                    GlobalMemberValues.logWrite("QuickSale", "Price Touch\n");

                    break;
                }
                case R.id.productSaleProductNameEditText : {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mProductNameEditText.getWindowToken(), 0);

                    break;
                }
            }



            return true;
        }
    };

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mPriceEditTextValue.length() < 12) {
            sb.append(mPriceEditTextValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            mPriceEditTextValue = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mPriceEditTextValue) * 0.01), "2");
            mProductPriceEditText.setText(inputSu);
        }
    }

    public void setCustomerEmployeeInfo() {
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
}
