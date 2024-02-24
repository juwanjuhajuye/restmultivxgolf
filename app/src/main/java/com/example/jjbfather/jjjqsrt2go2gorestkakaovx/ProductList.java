package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class ProductList {
    Activity mActivity;
    static Context context;
    GetDataAtSQLite dataAtSqlite;
    Button closeBtn;
    Button barcodeBtn;

    TextView productListTitleEditText;

    TextView productListViewItemProductName, productListViewItemProductDescription;
    TextView productListViewItemProductPrice, productListViewItemSalePrice;

    // 검색부분이 있는 LinearLayout
    LinearLayout productListLinearLayout1;
    // 상품리스트가 있는 LinearLayout
    LinearLayout productListLinearLayout2;

    // 선택한 상품의 TemporaryProductInfo 객체
    TemporaryProductInfo selectedItemProductInfo;

    // ListView 에 상품항목 붙일 때 필요한 객체들
    TemporaryProductInfo mTempProductInfo;
    public ArrayList<TemporaryProductInfo> mGeneralArrayList;
    public ProductInfoAdapter mProductInfoAdapter;

    // 상품정보 보여지는 리스트뷰
    ListView mProductInfoListView;

    // 검색부분 관련객체들
    static Spinner mProductInfoSpinner;
    static EditText mProductInfoEditText;
    Button productListSHButton;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    static String selectedItemSpinner = "Barcode";

    Cursor productInfoCursor;

    // DB 객체 선언
    DatabaseInit dbInit;

    // 고객 및 직원정보 관련 객체 선언
    String insCustomerId = "";
    String insCustomerName = "";
    String insCustomerPhone = "";

    String insEmpIdx = "";
    String insEmpName = "";

    static String onProductListYN = "N";

    private boolean gotScanValue = false;
    private Timer timer = new Timer();
    private long DELAY = 500; // in ms

    public ProductList(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // 메인 하단 Product 버튼 클릭 리스너 연결
        // Product Sale 을 거쳐서 와야 할 경우 아래 구문 삭제
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.setOnClickListener(ProductBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT.setOnClickListener(ProductBtnClickListener);

        // 상품리스트 보여지는 ListView 객체 생성
        mProductInfoListView = (ListView) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.findViewWithTag("productListListViewTag");

        // 검색부분 관련 ----------------------------------------------------------------------------------------
        // 검색항목 Spinner 객체 생성 및 항목연결
        mProductInfoSpinner = (Spinner) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.findViewWithTag("productListSpinnerTag");
        String[] strSearchItemList = {"Barcode", "Name", "Description", "Price"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < strSearchItemList.length; i++) {
            mGeneralArrayListForSpinner.add(strSearchItemList[i]);
        }
        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProductInfoSpinner.setAdapter(mSpinnerAdapter);
        mProductInfoSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);

        // 검색 EditText 객체 생성
        mProductInfoEditText = (EditText) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.findViewWithTag("productListEditTextTag");
        mProductInfoEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        mProductInfoEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        mProductInfoEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        //mProductInfoEditText.setFocusableInTouchMode(true);
        mProductInfoEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        searchProductInfo("N", "");
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        mProductInfoEditText.addTextChangedListener(editTextChangeListener);
        mProductInfoEditText.requestFocus();

        // 검색 Button 객체 생성
        productListSHButton = (Button) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.findViewWithTag("productListSHButtonTag");
        productListSHButton.setOnClickListener(ProductBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            productListSHButton.setText("");
            productListSHButton.setBackgroundResource(R.drawable.ab_imagebutton_productlist_search);
        } else {
            productListSHButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    productListSHButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // ------------------------------------------------------------------------------------------------------

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListCloseBtnTag");
        closeBtn.setOnClickListener(ProductBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        barcodeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("barcodeBtnTag");
        barcodeBtn.setOnClickListener(ProductBtnClickListener);

        productListTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListTitleEditTextTag");
        productListTitleEditText.setOnClickListener(ProductBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            productListTitleEditText.setText("");
            productListTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_productlist_title);
        } else {
            productListTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    productListTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        productListViewItemProductName = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListViewItemProductNameTag");
        productListViewItemProductName.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + productListViewItemProductName.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        productListViewItemProductDescription = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListViewItemProductDescriptionTag");
        productListViewItemProductDescription.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + productListViewItemProductDescription.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        productListViewItemProductPrice = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListViewItemProductPriceTag");
        productListViewItemProductPrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + productListViewItemProductPrice.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        productListViewItemSalePrice = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST
                .findViewWithTag("productListViewItemSalePriceTag");
        productListViewItemSalePrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + productListViewItemSalePrice.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        selectedItemSpinner = "Barcode";
        onProductListYN = "N";

        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);

        // 스캔 DELEY 조정
        if (GlobalMemberValues.getThisPOSDevice().equals("Tablet PC")){
            DELAY = 800;
        } else {
            DELAY = 500;
        }
    }


    /** 상품리스트 배치하기 ********************************************************/
    public void setProductInfo(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        // 상품정보 가져오기 (GetDataAtSQLite 클래스의 getProductInfo 메소드를 통해 가져온다)
        productInfoCursor = dataAtSqlite.getProductInfo(paramSearchQuery, paramGroupbyQuery, paramOrderbyQuery);
        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryProductInfo>();
        int custCount = 0;
        while (productInfoCursor.moveToNext()) {
            String tempProductName = productInfoCursor.getString(2);
            // 상품 이름이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempProductName, " ", ""))) {
                //TemporaryProductInfo 객체 생성시 전달할 값을 String 배열로 만든다.
                String paramsTempProductinfoArray[] = {
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
                mTempProductInfo = new TemporaryProductInfo(paramsTempProductinfoArray);
                mGeneralArrayList.add(mTempProductInfo);
                custCount++;
            }
        }
        mProductInfoAdapter = new ProductInfoAdapter(context, R.layout.product_search_list, mGeneralArrayList);
        mProductInfoListView.setAdapter(mProductInfoAdapter);

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        mProductInfoListView.setOnItemClickListener(mProductInfoItemClickListener);
    }
    /*******************************************************************************/

    private void selectProductOnList(int paramPosition, boolean isListViewItemTouch) {
        GlobalMemberValues.logWrite("cloverproductlog", "여기실행.." + "\n");

        selectedItemProductInfo = mGeneralArrayList.get(paramPosition);

        // 상품(Product) 수량이 설정해놓은 미니멈 수량 미만일 때 아림창을 보여줄지 여부에 따라..
        if (GlobalMemberValues.isAlertInProductMininumQty()) {
            if (GlobalMemberValues.isUnderThanMinimumQtyInProduct(selectedItemProductInfo.pdIdx)) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "",
                        "The quantity of the selected items is less than the minimum quantity set", "Close");
            }
        }

        // common gratuity 관련
        GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

        // MainMiddleService.insertTempSaleCart 메소드 전달용 String 배열을 받는다.
        String strArrForInsert[] = getStringArrForInsertTempSaleCart(selectedItemProductInfo);
        // 받아온 String 배열을 MainMiddleService.insertTempSaleCart 메소드에 전달하여
        // Scale Cart 리스트에 배치한다.
        MainMiddleService.insertTempSaleCart(strArrForInsert);

        // common gratuity 관련
        GlobalMemberValues.addCartLastItemForCommonGratuityUse();

        setmProductInfoEditTextInit();

        selectedItemSpinner = "Barcode";

        if (!isListViewItemTouch) {
            searchProductInfo("N", "wanhayejjjanniettasufamily");
        }

        //GlobalMemberValues.setFrameLayoutVisibleChange("main");
        //closeProductList();

        // Elo 일 경우 바코드 스캐너 활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOn();
        }
    }

    /** 리스트뷰의 항목을 클릭할 때 발생하는 OnClickListenr ********************************************/
    // 짧게(일반적인) 한번 클릭할 때
    AdapterView.OnItemClickListener mProductInfoItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // GlobalMemberValues.GLOBAL_ProductINFO = mGeneralArrayList.get(position);
            GlobalMemberValues.logWrite("cloverproductlog", "여기실행..1" + "\n");
            selectProductOnList(position, true);
        }
    };
    /*******************************************************************************/

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite("ProductInfo", "Selected Spinner Item : " + selectedItemSpinner + "\n");
            setProductInfo("", "", "");

            setmProductInfoEditTextInit();

            if (((TextView)parent.getChildAt(0)) != null) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3e3d42"));
                ((TextView)parent.getChildAt(0)).setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + 16);
            }

            if (position == 0){
                mProductInfoEditText.setInputType(0);
            } else {
                mProductInfoEditText.setInputType(1);
            }

            // 키보드 안보이게 --------------------------------------------------------------------------------------
//            GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
            // ------------------------------------------------------------------------------------------------------
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    View.OnClickListener ProductBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //case R.id.mainTopCustButton : { --> Product Sale 을 거쳐서 올경우
                case R.id.button_main_side_product :
                case R.id.mainRightBottom_Product : {
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------
                    LogsSave.saveLogsInDB(104);
                    GlobalMemberValues.setFrameLayoutVisibleChange("productList");
                    setProductInfo("", "", "");

                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    setmProductInfoEditTextInit();

                    onProductListYN = "Y";

                    break;
                }
                case R.id.barcodeBtn : {
                    setBarcodeQRCodeScan();
                    break;
                }
                case R.id.productListCloseBtn : {
                    closeProductList();
                    break;
                }
                case R.id.productListSHButton : {
                    searchProductInfo("N", "");
                    break;
                }
            }
        }
    };

    // 12292022 - static 으로 수정
    public static void setBarcodeQRCodeScan() {
        // Elo 일 경우 바코드 스캐너 활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOn();
        }

        if (GlobalMemberValues.isDeviceClover()) {
            GlobalMemberValues.cloverBarcodeScannerOn();
        }

        // 키패드(키보드) 감추기
        if (selectedItemSpinner.equals("Barcode")){
            GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
            mProductInfoEditText.setInputType(0);
            mProductInfoSpinner.setSelection(0);
//            selectedItemSpinner = "Barcode";
        }


        //mProductInfoEditText.setFocusableInTouchMode(true);
        setmProductInfoEditTextInit();

        // 12292022
        mProductInfoEditText.requestFocus();
    }

    private void closeProductList() {
        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
        // GlobalMemberValues.setFrameLayoutVisibleChange("productSale"); --> Product Sale 을 거쳐서 올경우
        GlobalMemberValues.setFrameLayoutVisibleChange("main");
        mProductInfoEditText.setText("");

        mProductInfoSpinner.setSelection(0);
        selectedItemSpinner = "Barcode";

        onProductListYN = "N";

        // 바코드 스캐너 비활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOff();
        }
        if (GlobalMemberValues.isDeviceClover()) {
            GlobalMemberValues.cloverBarcodeScannerOff();
        }
    }

    TextWatcher editTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (GlobalMemberValues.isDeviceElo()) {
                if(gotScanValue){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            MainActivity.mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProductInfoEditText.setText("");
                                }
                            });
                        }

                    }, DELAY);

                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (GlobalMemberValues.isDeviceElo()) {
                gotScanValue = false;
                if(timer != null) {
                    timer.cancel();
                }
            }

            //GlobalMemberValues.displayDialog(context, "Warning", "22222", "Close");
            String tempProductInfoTxt = mProductInfoEditText.getText().toString();
//            if (!GlobalMemberValues.isStrEmpty(tempProductInfoTxt)) {
//                if (selectedItemSpinner.equals("Barcode")) {
//                    searchProductInfo("Y", tempProductInfoTxt);
//                }
//            } else {
//                setProductInfo("","","");
//            }

            textin_Handler.removeMessages(0);
            //GlobalMemberValues.displayDialog(context, "Warning", "22222", "Close");
            textin_Handler.sendEmptyMessageDelayed(0,200);

            if(!gotScanValue){
                if (selectedItemSpinner.equals("Name")) {
                    searchProductInfo("Y", tempProductInfoTxt);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (GlobalMemberValues.isDeviceElo()) {
                //avoid triggering event when text is too short
                if (selectedItemSpinner.equals("Barcode") && s.length() >= 8) {
                    timer = new Timer();
                    timer.schedule( new TimerTask() {
                        @Override
                        public void run() {
                            MainActivity.mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //barcodeOutput.setEnabled(false);
                                    setBarcodeQRCodeScan();
                                }
                            });
                            gotScanValue = true;
                        }
                    }, 100);
                }
            }
        }
    };

    private Handler textin_Handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tempProductInfoTxt = mProductInfoEditText.getText().toString();
            if (!GlobalMemberValues.isStrEmpty(tempProductInfoTxt)) {
                if (selectedItemSpinner.equals("Barcode")) {
                    searchProductInfo("Y", tempProductInfoTxt);
                }
            } else {
                setProductInfo("","","");
            }

        }
    };

    public void searchProductInfo(String paramType, String paramSchTxt) {
        String schSearchText = paramSchTxt;
        if (GlobalMemberValues.isStrEmpty(paramSchTxt)) {
            schSearchText = GlobalMemberValues.getDBTextAfterChecked(mProductInfoEditText.getText().toString(), 0);
        }

        if (!selectedItemSpinner.equals("Barcode")) {
            // 키보드 안보이게 --------------------------------------------------------------------------------------
//            GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
            // ------------------------------------------------------------------------------------------------------
        }

        String schSearchStr = "";
        switch (selectedItemSpinner) {
            case "Barcode" : {
                schSearchStr = " a.productCode like '" + schSearchText + "' ";
                break;
            }
            case "Name" : {
                schSearchStr = " a.name like '%" + schSearchText + "%' ";
                break;
            }
            case "Description" : {
                schSearchStr = " a.description like '%" + schSearchText + "%' ";
                break;
            }
            case "Price" : {
                schSearchStr = " a.price = '" + schSearchText + "' ";
                break;
            }
        }
        setProductInfo(schSearchStr, "", "");

        if (schSearchText.equals("wanhayejjjanniettasufamily")) {
            setBarcodeQRCodeScan();
        }

        if (selectedItemSpinner.equals("Barcode")) {
            if (mGeneralArrayList.size() > 0) {
                // 키보드 안보이게 --------------------------------------------------------------------------------------
                GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
                // ------------------------------------------------------------------------------------------------------

                if (paramType == "Y" || paramType.equals("Y")) {
                    GlobalMemberValues.logWrite("cloverproductlog", "여기실행..0" + "\n");
                    selectProductOnList(0, false);
                }
            }
        }
    }


    public String[] getStringArrForInsertTempSaleCart(TemporaryProductInfo paramTempProductInfo) {
        TemporaryProductInfo getTempProductInfo = paramTempProductInfo;
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

        setCustomerEmployeeInfo();

        // MainMiddleService 클래스의 mInsParamForInsertTempSaleCart 메소드에 전달할 파라미터 배열저장
        String[] mInsParamForInsertTempSaleCart;
        mInsParamForInsertTempSaleCart = new String[]{
                "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                "", getTempProductInfo.pdIdx,
                getTempProductInfo.pdName, getTempProductInfo.pdStrFileName, getTempProductInfo.pdStrFilePath,
                getTempProductInfo.pdPrice, getTempProductInfo.pdSalePrice, getTempProductInfo.pdSaleStart,
                getTempProductInfo.pdSaleEnd, String.valueOf(GlobalMemberValues.PRODUCT_COMMISSIONRATIO),
                GlobalMemberValues.PRODUCT_COMMISSIONRATIOTYPE, String.valueOf(getTempProductInfo.pdPointRatio),
                "", "N", insCustomerId, insCustomerName, insCustomerPhone, "1", insEmpIdx, insEmpName, "N", ""
        };

        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);

        return mInsParamForInsertTempSaleCart;
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

    public static void setmProductInfoEditTextInit() {
        if (ProductList.onProductListYN == "Y" || ProductList.onProductListYN.equals("Y")) {
            mProductInfoEditText.setText("");
            mProductInfoEditText.requestFocus();
        }
    }
}
