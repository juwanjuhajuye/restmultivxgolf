package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
public class MenuSearch {
    Activity mActivity;
    static Context context;
    GetDataAtSQLite dataAtSqlite;
    Button closeBtn;
    Button barcodeBtn;

    TextView menusearchTitleEditText;

    TextView menusearchViewItemProductName, menusearchViewItemProductDescription;
    TextView menusearchViewItemProductPrice, menusearchViewItemSalePrice;

    // 검색부분이 있는 LinearLayout
    LinearLayout menusearchLinearLayout1;
    // 상품리스트가 있는 LinearLayout
    LinearLayout menusearchLinearLayout2;

    // 선택한 상품의 TemporaryProductInfo 객체
    TemporaryServiceInfo selectedItemMenuInfo;

    // ListView 에 상품항목 붙일 때 필요한 객체들
    TemporaryServiceInfo mTempMenuSearch;
    public ArrayList<TemporaryServiceInfo> mGeneralArrayList;
    public MenuSearchInfoAdapter mMenuSearchInfoAdapter;

    // 상품정보 보여지는 리스트뷰
    ListView mProductInfoListView;

    // 검색부분 관련객체들
    Spinner mMenuSearchInfoSpinner;
    static EditText mProductInfoEditText;
    Button menusearchSHButton;
    ArrayList<String> mGeneralArrayListForSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
//    String selectedItemSpinner = "QR Code";
    String selectedItemSpinner = "Name";

    Cursor mesusearchCursor;

    // DB 객체 선언
    DatabaseInit dbInit;

    // 고객 및 직원정보 관련 객체 선언
    String insCustomerId = "";
    String insCustomerName = "";
    String insCustomerPhone = "";

    String insEmpIdx = "";
    String insEmpName = "";

    static String onmenusearchYN = "N";

    private boolean gotScanValue = false;
    private Timer timer = new Timer();
    private final long DELAY = 500; // in ms

    public MenuSearch(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // 메인 하단 Product 버튼 클릭 리스너 연결
        // Menu Search 을 거쳐서 와야 할 경우 아래 구문 삭제
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.setOnClickListener(menusearchBtnClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH.setOnClickListener(menusearchBtnClickListener);

        // 상품리스트 보여지는 ListView 객체 생성
        mProductInfoListView = (ListView) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.findViewWithTag("menusearchListViewTag");

        // 검색부분 관련 ----------------------------------------------------------------------------------------
        // 검색항목 Spinner 객체 생성 및 항목연결
        mMenuSearchInfoSpinner = (Spinner) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.findViewWithTag("menusearchSpinnerTag");
//        String[] strSearchItemList = {"QR Code", "Name"};
        String[] strSearchItemList = {"Name", "QR Code"};
        mGeneralArrayListForSpinner = new ArrayList<String>();
        for (int i = 0; i < strSearchItemList.length; i++) {
            mGeneralArrayListForSpinner.add(strSearchItemList[i]);
        }
        mSpinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mGeneralArrayListForSpinner);

        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMenuSearchInfoSpinner.setAdapter(mSpinnerAdapter);
        mMenuSearchInfoSpinner.setOnItemSelectedListener(mSpinnerItemSelectedListener);

        // 검색 EditText 객체 생성
        mProductInfoEditText = (EditText) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.findViewWithTag("menusearchEditTextTag");
        mProductInfoEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + 16);
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
        menusearchSHButton = (Button) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.findViewWithTag("menusearchSHButtonTag");
        menusearchSHButton.setOnClickListener(menusearchBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            menusearchSHButton.setText("");
            menusearchSHButton.setBackgroundResource(R.drawable.ab_imagebutton_productlist_search);
        } else {
            menusearchSHButton.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() +
                    menusearchSHButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // ------------------------------------------------------------------------------------------------------

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchCloseBtnTag");
        closeBtn.setOnClickListener(menusearchBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        barcodeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("barcodeBtnTag");
        barcodeBtn.setOnClickListener(menusearchBtnClickListener);

        menusearchTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchTitleEditTextTag");
        menusearchTitleEditText.setOnClickListener(menusearchBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            menusearchTitleEditText.setText("");
            menusearchTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_menusearch_title);
        } else {
            menusearchTitleEditText.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() +
                    menusearchTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        menusearchViewItemProductName = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchViewItemProductNameTag");
        menusearchViewItemProductName.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + menusearchViewItemProductName.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        menusearchViewItemProductDescription = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchViewItemProductDescriptionTag");
        menusearchViewItemProductDescription.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + menusearchViewItemProductDescription.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        menusearchViewItemProductPrice = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchViewItemProductPriceTag");
        menusearchViewItemProductPrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + menusearchViewItemProductPrice.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        menusearchViewItemSalePrice = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH
                .findViewWithTag("menusearchViewItemSalePriceTag");
        menusearchViewItemSalePrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + menusearchViewItemSalePrice.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

//        selectedItemSpinner = "QR Code";
        selectedItemSpinner = "Name";
        onmenusearchYN = "N";

        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
    }


    /** 상품리스트 배치하기 ********************************************************/
    public void getFoodSearch(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery, String paramSearchTxt) {
        // 상품정보 가져오기 (GetDataAtSQLite 클래스의 getProductInfo 메소드를 통해 가져온다)
        mesusearchCursor = dataAtSqlite.getFoodSearch(paramSearchQuery, paramGroupbyQuery, paramOrderbyQuery, paramSearchTxt);
        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryServiceInfo>();
        int custCount = 0;
        while (mesusearchCursor.moveToNext()) {
            String tempProductName = mesusearchCursor.getString(2);
            // 상품 이름이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempProductName, " ", ""))) {
                //TemporaryServiceInfo 객체 생성시 전달할 값을 String 배열로 만든다.

                String fullFoodName = GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(2), 1);
                String foodname2 = GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(15), 1);
                String foodname3 = GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(16), 1);
                if (!GlobalMemberValues.isStrEmpty(foodname2)) {
                    fullFoodName += " " + foodname2;
                }
                if (!GlobalMemberValues.isStrEmpty(foodname3)) {
                    fullFoodName += " " + foodname3;
                }

                String tempSearchFullName = fullFoodName;
                if (!GlobalMemberValues.isStrEmpty(paramSearchTxt)) {
                    tempSearchFullName = GlobalMemberValues.getReplaceText(fullFoodName.toUpperCase(), paramSearchTxt.toUpperCase(), "<font color=\"#f20e02\">" + paramSearchTxt.toUpperCase() + "</font>");
                }

                String paramsTempMenuInfoArray[] = {
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(0), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(1), 1),
                        fullFoodName,
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(3), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(4), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(5), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(6), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(7), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(8), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(9), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(10), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(11), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(12), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(13), 1),
                        GlobalMemberValues.getDBTextAfterChecked(mesusearchCursor.getString(14), 1),
                        tempSearchFullName
                };
                mTempMenuSearch = new TemporaryServiceInfo(paramsTempMenuInfoArray[0], paramsTempMenuInfoArray[1],
                        paramsTempMenuInfoArray[2], paramsTempMenuInfoArray[3], paramsTempMenuInfoArray[4], paramsTempMenuInfoArray[5],
                        paramsTempMenuInfoArray[6], paramsTempMenuInfoArray[7], paramsTempMenuInfoArray[8], paramsTempMenuInfoArray[9], paramsTempMenuInfoArray[10],
                        paramsTempMenuInfoArray[11], paramsTempMenuInfoArray[12], paramsTempMenuInfoArray[13], paramsTempMenuInfoArray[14], paramsTempMenuInfoArray[15]);
                mGeneralArrayList.add(mTempMenuSearch);
                custCount++;
            }
        }
        mMenuSearchInfoAdapter = new MenuSearchInfoAdapter(context, R.layout.menu_search_list, mGeneralArrayList);
        mProductInfoListView.setAdapter(mMenuSearchInfoAdapter);

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        mProductInfoListView.setOnItemClickListener(mMenuSearchItemClickListener);
    }
    /*******************************************************************************/

    private void selectMenuOnList(int paramPosition, boolean isListViewItemTouch) {
        GlobalMemberValues.logWrite("cloverproductlog", "여기실행.." + "\n");

        selectedItemMenuInfo = mGeneralArrayList.get(paramPosition);

        MainMiddleService.selectService(selectedItemMenuInfo, MainMiddleService.mTouchedView_fromMenuSearch);

        /**
        // MainMiddleService.insertTempSaleCart 메소드 전달용 String 배열을 받는다.
        String strArrForInsert[] = getStringArrForInsertTempSaleCart(selectedItemMenuInfo);
        // 받아온 String 배열을 MainMiddleService.insertTempSaleCart 메소드에 전달하여
        // Scale Cart 리스트에 배치한다.
        MainMiddleService.insertTempSaleCart(strArrForInsert);
         **/


        setmProductInfoEditTextInit();

        //mMenuSearchInfoSpinner.setSelection(0);
        //selectedItemSpinner = "Nmae";

        if (!isListViewItemTouch) {
            //searchProductInfo("N", "wanhayejjjanniettasufamily");
        }

        //GlobalMemberValues.setFrameLayoutVisibleChange("main");
        //closemenusearch();

        // Elo 일 경우 바코드 스캐너 활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOn();
        }
    }

    /** 리스트뷰의 항목을 클릭할 때 발생하는 OnClickListenr ********************************************/
    // 짧게(일반적인) 한번 클릭할 때
    AdapterView.OnItemClickListener mMenuSearchItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // GlobalMemberValues.GLOBAL_ProductINFO = mGeneralArrayList.get(position);
            GlobalMemberValues.logWrite("cloverproductlog", "여기실행..1" + "\n");
            selectMenuOnList(position, true);
        }
    };
    /*******************************************************************************/

    /** Spinner 의 항목을 클릭할 때 발생하는 리스너 ********************************************/
    AdapterView.OnItemSelectedListener mSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedItemSpinner = mGeneralArrayListForSpinner.get(position);
            GlobalMemberValues.logWrite("ProductInfo", "Selected Spinner Item : " + selectedItemSpinner + "\n");
            getFoodSearch("", "", "", "");

            setmProductInfoEditTextInit();

            if (((TextView)parent.getChildAt(0)) != null) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#3e3d42"));
                ((TextView)parent.getChildAt(0)).setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + 16);
            }

            // 키보드 안보이게 --------------------------------------------------------------------------------------
            GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
            // ------------------------------------------------------------------------------------------------------
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    /*******************************************************************************/

    View.OnClickListener menusearchBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_main_side_menusearch :
                case R.id.mainRightBottom_menusearch : {
                    GlobalMemberValues.logWrite("menusearchlog", "여기진입..." + "\n");
                    LogsSave.saveLogsInDB(107);
                    GlobalMemberValues.setFrameLayoutVisibleChange("menusearch");
                    getFoodSearch("", "", "", "");

                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    setmProductInfoEditTextInit();

                    onmenusearchYN = "Y";

                    break;
                }
                case R.id.barcodeBtn : {
                    setBarcodeQRCodeScan();
                    break;
                }
                case R.id.menusearchCloseBtn : {
                    LogsSave.saveLogsInDB(79);
                    closemenusearch();
                    break;
                }
                case R.id.menusearchSHButton : {
                    LogsSave.saveLogsInDB(80);
                    searchProductInfo("N", "");
                    break;
                }
            }
        }
    };

    private void setBarcodeQRCodeScan() {
        // Elo 일 경우 바코드 스캐너 활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOn();
        }

        if (GlobalMemberValues.isDeviceClover()) {
            GlobalMemberValues.cloverBarcodeScannerOn();
        }

        // jihun park
        GlobalMemberValues.paxScannerConnect(MainActivity.mContext);

        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
        mMenuSearchInfoSpinner.setSelection(0);
//        selectedItemSpinner = "QR Code";
        selectedItemSpinner = "Name";

        //mProductInfoEditText.setFocusableInTouchMode(true);
        setmProductInfoEditTextInit();
    }

    private void closemenusearch() {
        // 키패드(키보드) 감추기
        GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
        // GlobalMemberValues.setFrameLayoutVisibleChange("productSale"); --> Menu Search 을 거쳐서 올경우
        GlobalMemberValues.setFrameLayoutVisibleChange("main");
        mProductInfoEditText.setText("");

        mMenuSearchInfoSpinner.setSelection(0);
//        selectedItemSpinner = "QR Code";
        selectedItemSpinner = "Name";

        onmenusearchYN = "N";

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
            if (!GlobalMemberValues.isStrEmpty(tempProductInfoTxt)) {
                if (selectedItemSpinner.equals("QR Code")) {
                    searchProductInfo("Y", tempProductInfoTxt);
                }
            }

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
                if (selectedItemSpinner.equals("QR Code") && s.length() >= 8) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
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
                    }, 300);
                }
            }
        }
    };

    public void searchProductInfo(String paramType, String paramSchTxt) {
        String schSearchText = paramSchTxt;
        if (GlobalMemberValues.isStrEmpty(paramSchTxt)) {
            schSearchText = GlobalMemberValues.getDBTextAfterChecked(mProductInfoEditText.getText().toString(), 0);
        }

        if (!selectedItemSpinner.equals("QR Code")) {
            if (gotScanValue) {
                // 키보드 안보이게 --------------------------------------------------------------------------------------
                GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
                // ------------------------------------------------------------------------------------------------------
            }
        }

        String schSearchStr = "";
        switch (selectedItemSpinner) {
            case "QR Code" : {
                schSearchStr = " a.barcode like '" + schSearchText + "' ";
                //schSearchStr = " a.idx like '" + schSearchText + "' ";
                break;
            }
            case "Name" : {
                schSearchStr = " ( " +
                        " a.servicename like '%" + schSearchText + "%' " +
                        /**
                        " or " +
                        " a.servicename2 like '%" + schSearchText + "%' " +
                        " or " +
                        " a.servicename3 like '%" + schSearchText + "%' " +
                         **/
                        " or " +
                        " (a.servicename || ' ' || a.servicename2 || ' ' || a.servicename3) like '%" + schSearchText + "%' " +
                        " ) ";
                break;
            }
            /**
            case "Price" : {
                schSearchStr = " a.price = '" + schSearchText + "' ";
                break;
            }
             **/
        }

        getFoodSearch(schSearchStr, "", "", schSearchText);

        if (schSearchText.equals("wanhayejjjanniettasufamily")) {
            setBarcodeQRCodeScan();
        }

        if (selectedItemSpinner.equals("QR Code")) {
            if (mGeneralArrayList.size() > 0) {
                // 키보드 안보이게 --------------------------------------------------------------------------------------
                GlobalMemberValues.setKeyPadHide(context, mProductInfoEditText);
                // ------------------------------------------------------------------------------------------------------

                if (paramType == "Y" || paramType.equals("Y")) {
                    GlobalMemberValues.logWrite("cloverproductlog", "여기실행..0" + "\n");
                    selectMenuOnList(0, false);
                }
            }
        }
    }


    public String[] getStringArrForInsertTempSaleCart(TemporaryServiceInfo paramTempProductInfo) {
        TemporaryServiceInfo getTempProductInfo = paramTempProductInfo;
        double insPdPrice = 0.00;
        double insPdOrgPrice = 0.00;          // 세일이전 원가
        if (!GlobalMemberValues.isStrEmpty(getTempProductInfo.svcServicePrice)) {
            insPdPrice = GlobalMemberValues.getDoubleAtString(getTempProductInfo.svcServicePrice);
            insPdOrgPrice = insPdPrice;
        }

        String insPdSaleYN = "N";
        // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
        if ((!GlobalMemberValues.isStrEmpty(getTempProductInfo.svcSaleStart)
                && DateMethodClass.getDiffDayCount(getTempProductInfo.svcSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                && (!GlobalMemberValues.isStrEmpty(getTempProductInfo.svcSaleEnd)
                && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, getTempProductInfo.svcSaleEnd) >= 0)
                &&!GlobalMemberValues.isStrEmpty(getTempProductInfo.svcSalePrice)) {
            insPdPrice = GlobalMemberValues.getDoubleAtString(getTempProductInfo.svcSalePrice);
            insPdSaleYN = "Y";
        }
        // -------------------------------------------------------------------------------------

        setCustomerEmployeeInfo();

        // MainMiddleService 클래스의 mInsParamForInsertTempSaleCart 메소드에 전달할 파라미터 배열저장
        String[] mInsParamForInsertTempSaleCart;
        mInsParamForInsertTempSaleCart = new String[]{
                "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                "", getTempProductInfo.svcIdx,
                getTempProductInfo.svcServiceName, getTempProductInfo.svcStrFileName, getTempProductInfo.svcStrFilePath,
                getTempProductInfo.svcServicePrice, getTempProductInfo.svcSalePrice, getTempProductInfo.svcSaleStart,
                getTempProductInfo.svcSaleEnd, String.valueOf(GlobalMemberValues.PRODUCT_COMMISSIONRATIO),
                GlobalMemberValues.PRODUCT_COMMISSIONRATIOTYPE, String.valueOf(getTempProductInfo.svcPointRatio),
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
        if (MenuSearch.onmenusearchYN == "Y" || MenuSearch.onmenusearchYN.equals("Y")) {
            mProductInfoEditText.setText("");
            mProductInfoEditText.requestFocus();
        }
    }


}
