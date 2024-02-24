package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class CommandDownloadData extends Activity {
    static Activity mActivity;
    Context context;

    private LinearLayout parentLn, categoryLn;

    private Button alldatabtn, storebtn, employeebtn, productbtn;
    private Button giftcardbtn, memberbtn, couponbtn, foodbtn;
    private Button allcategorybtn, uploadDatabtn;
    private Button submitbtn, closebtn;

    Intent mIntent;

    private String[] mTableGroup = {"", "", "", "", "", "", "", ""};
    private ArrayList<String> mTableArrList = null;

    private String[] mCategoryIdx = null;

    private boolean isAllCategoryBtn = false;

    private ArrayList<Button> mCategoryBtns = null;

    public ProgressDialog itemProDial;
    public int mDownloadCount = 0;               // 항목별 다운로드한 횟수

    // DB 객체 선언
    DatabaseInit dbInit;

    public Button[] mBtnArr = {
            alldatabtn,
            storebtn,
            employeebtn,
            productbtn,
            giftcardbtn,
            memberbtn,
            couponbtn,
            foodbtn
    };

    public String[] mGroupNames = {
            "ALL", "STORE", "EMPLOYEE", "PRODUCT", "GIFTCARD", "MEMBER", "COUPON", "FOOD"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_command_download_data);
        this.setFinishOnTouchOutside(false);
        mActivity = this;
        context = this;

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            closeActivity(true);
        }

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
        if (GlobalMemberValues.thisTabletRealWidth > 1280) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
        }
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 75;
        if (GlobalMemberValues.thisTabletRealHeight > 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 75;
        }

        // Device 가 Elo 일 때...
        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
        } else {
            if (GlobalMemberValues.isDeviceElo()) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 85;
            } else {
                if (GlobalMemberValues.thisTabletRealHeight == 1920 && GlobalMemberValues.thisTabletRealWidth == 1032) {
                    parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 75;
                }
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.commanddownloaddataLn);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            closeActivity(true);
        }

        setContents();
    }

    public void setContents() {
        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        mTableArrList = new ArrayList<String>();
        mTableArrList.clear();

        mCategoryIdx = new String[GlobalMemberValues.STOREMAXCATEGORYSU];
        categoryIdxInit();

        mCategoryBtns = new ArrayList<Button>();

        categoryLn = (LinearLayout)findViewById(R.id.categoryLn);
        categoryLn.setVisibility(View.GONE);

        alldatabtn = (Button) findViewById(R.id.alldatabtn);
        alldatabtn.setTextSize(GlobalMemberValues.globalAddFontSize() + alldatabtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        //alldatabtn.setBackgroundResource(R.drawable.button_selector_download1);
        mBtnArr[0] = alldatabtn;

        storebtn = (Button) findViewById(R.id.storebtn);
        storebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + storebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[1] = storebtn;

        employeebtn = (Button) findViewById(R.id.employeebtn);
        employeebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + employeebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[2] = employeebtn;

        productbtn = (Button) findViewById(R.id.productbtn);
        productbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + productbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[3] = productbtn;

        giftcardbtn = (Button) findViewById(R.id.giftcardbtn);
        giftcardbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + giftcardbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[4] = giftcardbtn;

        memberbtn = (Button) findViewById(R.id.memberbtn);
        memberbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + memberbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[5] = memberbtn;

        couponbtn = (Button) findViewById(R.id.couponbtn);
        couponbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + couponbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[6] = couponbtn;

        foodbtn = (Button) findViewById(R.id.foodbtn);
        foodbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + foodbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        mBtnArr[7] = foodbtn;

        allcategorybtn = (Button) findViewById(R.id.allcategorybtn);
        allcategorybtn.setTextSize(GlobalMemberValues.globalAddFontSize() + allcategorybtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        submitbtn = (Button) findViewById(R.id.submitbtn);
        closebtn = (Button) findViewById(R.id.closebtn);

        uploadDatabtn = (Button) findViewById(R.id.uploadDatabtn);
        uploadDatabtn.setTextSize(GlobalMemberValues.globalAddFontSize() + uploadDatabtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        SpannableStringBuilder ssb = new SpannableStringBuilder(uploadDatabtn.getText().toString());
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ea0000")), 11, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        uploadDatabtn.setOnClickListener(btnClickListener);

        alldatabtn.setOnClickListener(btnClickListener);
        storebtn.setOnClickListener(btnClickListener);
        employeebtn.setOnClickListener(btnClickListener);
        productbtn.setOnClickListener(btnClickListener);
        giftcardbtn.setOnClickListener(btnClickListener);
        memberbtn.setOnClickListener(btnClickListener);
        couponbtn.setOnClickListener(btnClickListener);
        foodbtn.setOnClickListener(btnClickListener);

        allcategorybtn.setOnClickListener(btnClickListener);
        submitbtn.setOnClickListener(btnClickListener);
        closebtn.setOnClickListener(btnClickListener);


        setTopCategory();
    }

    /** 상단 카테고리 배치하기 ********************************************************/
    public void setTopCategory() {
        // 카테고리 배열 가져오기 (GetDataAtSQLite 클래스의 getCategoryInfo 메소드를 통해 가져온다)
        String[] strArrCategory = MainActivity.dataAtSqlite.getCategoryInfo();

        String strCateInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempCategoryPositionNo = "";     // 임시 PositionNo 를 저장할 객체 선언

        float categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE;
        if (GlobalMemberValues.isDeviceElo()) {
            categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE_FORELO;
        }

        // 해당 스토어의 최대 카테고리수(GlobalMemberValues.STOREMAXCATEGORYSU) 만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++) {
            if (strArrCategory[i] != null && strArrCategory[i] != "" && !strArrCategory[i].equals("")) {
                strCateInfo = strArrCategory[i];
                String[] strCateInfoArr = strCateInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempCategoryPositionNo = strCateInfoArr[3];
                if (Integer.parseInt(tempCategoryPositionNo) > 0) {         // 포지션값이 0 이상 값일때에만
                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    LinearLayout topCategoryLn = (LinearLayout) parentLn.findViewWithTag("topCategoryLnTag" + tempCategoryPositionNo);
                    //topCategoryLn.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));

                    Button topCategoryBtn = (Button)parentLn.findViewWithTag("topCategoryButtonTag" + tempCategoryPositionNo);
                    topCategoryBtn.setAllCaps(false);
                    topCategoryBtn.setText(GlobalMemberValues.changeBrLetter(strCateInfoArr[1]));
                    //topCategoryBtn.setTextSize(15);
                    topCategoryBtn.setTextSize(categoryBtnTextSize);

                    topCategoryBtn.setBackgroundResource(R.drawable.button_selector_download1);
                    topCategoryBtn.setTextColor(Color.parseColor("#034dd3"));

                    topCategoryBtn.setTag(strCateInfoArr[0] + "-JJJ-" + i);

                    mCategoryBtns.add(topCategoryBtn);

                    // 카테고리 클릭시 이벤트
                    topCategoryBtn.setOnClickListener(mCategoryButtonListner);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrCategory[i] + "\n");
            }
        }
    }
    /*******************************************************************************/

    View.OnClickListener mCategoryButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;

            String tempCategoryIdxStr = btn.getTag().toString();
            String[] tempReturnStr = tempCategoryIdxStr.split("-JJJ-");
            String tempCategoryIdx = tempReturnStr[0];
            int tempIndex = GlobalMemberValues.getIntAtString(tempReturnStr[1]);

            //GlobalMemberValues.displayDialog(context, "Warning", tempCategoryIdx, "Close");

            if (mCategoryIdx != null) {
                if (!GlobalMemberValues.isStrEmpty(tempCategoryIdx)) {
                    if (tempCategoryIdx.equals(mCategoryIdx[tempIndex])) {
                        btn.setBackgroundResource(R.drawable.button_selector_download1);
                        btn.setTextColor(Color.parseColor("#034dd3"));
                        mCategoryIdx[tempIndex] = "";

                        GlobalMemberValues.logWrite("downloadtestjjjlog", "기존거 삭제 " + "\n");
                    } else {
                        btn.setBackgroundResource(R.drawable.button_selector_download2);
                        btn.setTextColor(Color.parseColor("#ffffff"));
                        mCategoryIdx[tempIndex] = tempCategoryIdx;

                        GlobalMemberValues.logWrite("downloadtestjjjlog", "새로 추가" + "\n");
                    }

                    // 카테고리중 하나라도 선택되면
                    // food 버튼도 홣성화 한다.
                    int selectCategoryCnt = 0;
                    for (int i = 0; i < mCategoryIdx.length; i++) {
                        if (!GlobalMemberValues.isStrEmpty(mCategoryIdx[i])) {
                            selectCategoryCnt++;
                        }
                    }
                    if (selectCategoryCnt > 0) {
                        foodbtn.setBackgroundResource(R.drawable.button_selector_download2);
                        mTableGroup[7] = "FOOD";
                        foodbtn.setTextColor(Color.parseColor("#ffffff"));
                        categoryLn.setVisibility(View.VISIBLE);

                        // All Category 버튼은 선택해제한다..
                        isAllCategoryBtn = false;
                        allcategorybtn.setBackgroundResource(R.drawable.button_selector_download1);
                        allcategorybtn.setTextColor(Color.parseColor("#034dd3"));
                    } else {
                        foodbtn.setBackgroundResource(R.drawable.button_selector_download1);
                        foodbtn.setTextColor(Color.parseColor("#034dd3"));
                        mTableGroup[7] = "";
                        categoryLn.setVisibility(View.GONE);

                        // All Category 버튼은 선택한다..
                        categoryIdxInit();
                        isAllCategoryBtn = true;
                        allcategorybtn.setBackgroundResource(R.drawable.button_selector_download2);
                        allcategorybtn.setTextColor(Color.parseColor("#ffffff"));
                    }
                }
            }
        }
    };

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closebtn : {
                    closeActivity(true);

                    break;
                }
                case R.id.alldatabtn : {
                    setDownload("ALL", 0, alldatabtn);
                    break;
                }
                case R.id.storebtn : {
                    setDownload("STORE", 1, storebtn);
                    break;
                }
                case R.id.employeebtn : {
                    setDownload("EMPLOYEE", 2, employeebtn);
                    break;
                }
                case R.id.productbtn : {
                    setDownload("PRODUCT", 3, productbtn);
                    break;
                }
                case R.id.giftcardbtn : {
                    setDownload("GIFTCARD", 4, giftcardbtn);
                    break;
                }
                case R.id.memberbtn : {
                    setDownload("MEMBER", 5, memberbtn);
                    break;
                }
                case R.id.couponbtn : {
                    setDownload("COUPON", 6, couponbtn);
                    break;
                }
                case R.id.foodbtn : {
                    setDownload("FOOD", 7, foodbtn);
                    break;
                }

                case R.id.submitbtn : {
                    int mTblGroupCnt = 0;
                    for (int i = 0; i < mTableGroup.length; i++) {
                        String tempTblGroup = mTableGroup[i];
                        if (!GlobalMemberValues.isStrEmpty(tempTblGroup)) {
                            mTblGroupCnt++;
                        }
                    }

                    if (mTblGroupCnt == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please select a section to download", "Close");
                        return;
                    }

                    if ((MainActivity.mActivity != null) && (!MainActivity.mActivity.isFinishing())) {
                        new AlertDialog.Builder(context)
                                .setTitle("Data Download")
                                .setMessage("POS data will be replaced by Cloud Data\nDo you want to download?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                submitDownloadData();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    }

                    break;
                }
                case R.id.allcategorybtn : {
                    clickAllCategoryBtn();
                    break;
                }
                case R.id.uploadDatabtn : {
                    // 업로드할 데이터가 얼마나 되는지 체크

                    int dataCountToUpload = GlobalMemberValues.getIntAtString(
                            MainActivity.mDbInit.dbExecuteReadReturnString(" select count(idx) from salon_sales_detail where isCloudUpload = 0 ")
                    );

                    if (dataCountToUpload > 0) {
                        final double tempTimeToUpload = dataCountToUpload * 3;

                        double tempMin = 0;
                        double tempSec = 0;
                        if (tempTimeToUpload > 60) {
                            tempMin = tempTimeToUpload / 60;
                            tempSec = tempTimeToUpload % 60;
                        } else {
                            tempMin = 0;
                            tempSec = tempTimeToUpload;
                        }

                        String tempMinSec = "";
                        if (tempMin > 0) {
                            tempMinSec = tempMin + "min(s) " + tempSec + "sec(s)";
                        } else {
                            tempMinSec = tempSec + "sec(s)";
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("Sale Data Upload To Cloud")
                                .setMessage("There are " + dataCountToUpload + " data to upload to the cloud\nIt takes about " + tempMinSec +
                                        "\nOther functions cannot be used while data is uploading" + "\nUpload the sale data?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                CloudBackOffice cloudBackOffice = new CloudBackOffice();
                                                cloudBackOffice.salesDataUploadInCommand(tempTimeToUpload,context);
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    } else {
                        GlobalMemberValues.displayDialog(context, "", "There is no sale data to upload", "Close");
                    }
                    break;
                }
            }
        }
    };

    public void setDownload(String paramType, int paramIndex, Button paramBtn) {
        if (paramType.equals("ALL")) {
            if (!GlobalMemberValues.isStrEmpty(mTableGroup[0]) && mTableGroup[0].equals("ALL")) {
                setAllButtonStatus(false);
                for (int i = 0; i < mTableGroup.length; i++) {
                    mTableGroup[i] = "";
                }
                categoryLn.setVisibility(View.GONE);
            } else {
                setAllButtonStatus(true);
                for (int i = 0; i < mGroupNames.length; i++) {
                    mTableGroup[i] = mGroupNames[i];
                }
                categoryLn.setVisibility(View.VISIBLE);
            }
        } else {
            // ALL 이 아닌 개별 선택이므로 ALL 부분은 비활성화 한다. -----------------------
            mTableGroup[0] = "";
            alldatabtn.setBackgroundResource(R.drawable.button_selector_download1);
            alldatabtn.setTextColor(Color.parseColor("#034dd3"));
            // -----------------------------------------------------------------------------

            String tempTblGroup = mTableGroup[paramIndex].toUpperCase();
            if (!GlobalMemberValues.isStrEmpty(tempTblGroup)) {
                paramBtn.setBackgroundResource(R.drawable.button_selector_download1);
                paramBtn.setTextColor(Color.parseColor("#034dd3"));
                mTableGroup[paramIndex] = "";
                if (paramIndex == 7) {
                    categoryLn.setVisibility(View.GONE);
                }
            } else {
                paramBtn.setBackgroundResource(R.drawable.button_selector_download2);
                mTableGroup[paramIndex] = paramType;
                paramBtn.setTextColor(Color.parseColor("#ffffff"));
                if (paramIndex == 7) {
                    categoryLn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void submitDownloadData() {
        int mTblGroupCnt = 0;
        for (int i = 0; i < mTableGroup.length; i++) {
            String tempTblGroup = mTableGroup[i];
            if (!GlobalMemberValues.isStrEmpty(tempTblGroup)) {
                mTblGroupCnt++;
            }
        }

        if (mTblGroupCnt == 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "Please select a section to download", "Close");
        } else {
            if (!GlobalMemberValues.isStrEmpty(mTableGroup[0]) && mTableGroup[0].equals("ALL")) {
                setTableArrayList("ALL");
            } else {
                for (int i = 0; i < mTableGroup.length; i++) {
                    String tempTblGroup = mTableGroup[i];
                    if (!GlobalMemberValues.isStrEmpty(tempTblGroup)) {
                        setTableArrayList(tempTblGroup);
                    }
                }
            }

            // jihun progress Bar 로 변경
            // 프로그래스 바를 실행~
//            itemProDial = ProgressDialog.show(context, " DATA DOWNLOAD", "Data is Downloading...", true, false);
            GlobalMemberValues.progressBarDialog = new ProgressBarDialog(context, "Data is Downloading...");
            GlobalMemberValues.progressBarDialog.show();
            // jihun progress Bar 로 변경

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 화면재개 지연시간 초기화
                    GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

                    // 1. 처리가 오래걸리는 부분 실행 -------------------------------------------------
                    executeDownloadData();

                    if (mTableGroup[7].equals("FOOD")) {
                        GlobalMemberValues.downloadImageFromCloud("service");
                    }
                    // -----------------------------------------------------------------------------

                    GlobalMemberValues.logWrite("screendelaytimelog", "여기........1 : " + GlobalMemberValues.RESTARTSCREEN_DELYTIME);
                    try {
                        Thread.sleep(1000 + GlobalMemberValues.getIntAtString(GlobalMemberValues.RESTARTSCREEN_DELYTIME));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    mDownloadCount++;
                    itemdownhandler.sendEmptyMessage(0);
                    // -----------------------------------------------------------------------------
                }
            });
            thread.start();


        }
    }

    public void executeDownloadData() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            int arrSize = mTableArrList.size();
            String[] tblArr = new String[arrSize];
            int i = 0;
            for (String tempStr : mTableArrList) {
                if (i < arrSize) {
                    if (!GlobalMemberValues.isStrEmpty(tempStr)) {
                        tblArr[i] = tempStr;
                        i++;

                        GlobalMemberValues.logWrite("downloadtestjjjlog", "tempStr : " + tempStr + "\n");
                    }
                }
            }

            dbInit.insertDownloadDataInDatabase(tblArr, "", 1);
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
        }
    }

    private Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------

            // jihun progressBar 추가.
//            itemProDial.dismiss();
            if (GlobalMemberValues.progressBarDialog.isShowing()){
                GlobalMemberValues.progressBarDialog.dismiss();
            }
            // jihun progressBar 추가.
            categoryIdxInit();

            GlobalMemberValues.ITEMCANCELAPPLY = 1;
            GlobalMemberValues.MAINRECREATEYN = "Y";
            //MainActivity.mActivity.recreate();
//            if (GlobalMemberValues.b_isLoginView){
//
//            } else {
//                MainActivity.employeeLogout();
//            }
            // -------------------------------------------------------------------------------------

            MainActivity.employeeLogout();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GlobalMemberValues.logWrite("downloaddatalogjjj", "GlobalMemberValues.IS_COM_FRANCHISE : " + GlobalMemberValues.IS_COM_FRANCHISE + "\n");

            closeActivity(false);

            if (!GlobalMemberValues.IS_COM_FRANCHISE) {
                GlobalMemberValues.copyMileageFromTempMileageCart();
            }
        }
    };

    public void clickAllCategoryBtn() {
        if (isAllCategoryBtn) {
            isAllCategoryBtn = false;
            allcategorybtn.setBackgroundResource(R.drawable.button_selector_download1);
            allcategorybtn.setTextColor(Color.parseColor("#034dd3"));

            allCategorySelect(false);
        } else {
            categoryIdxInit();
            isAllCategoryBtn = true;
            allcategorybtn.setBackgroundResource(R.drawable.button_selector_download2);
            allcategorybtn.setTextColor(Color.parseColor("#ffffff"));

            allCategorySelect(true);
        }
    }

    public void setTableArrayList(String paramTblType) {
        if (paramTblType.equals("ALL")) {
            String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_new;
            int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
            for (int i = 0; i < allTableSize ; i++) {
                mTableArrList.add(dbTableArr[i]);
            }
        } else {
            switch (paramTblType) {
                case "STORE" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_store;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "EMPLOYEE" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_employee;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "FOOD" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_food;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "PRODUCT" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_product;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "GIFTCARD" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_giftcard;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "MEMBER" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_member;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
                case "COUPON" : {
                    String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi_coupon;
                    int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
                    for (int i = 0; i < allTableSize ; i++) {
                        mTableArrList.add(dbTableArr[i]);
                    }
                    break;
                }
            }
        }

        if (mCategoryIdx != null && mCategoryIdx.length > 0) {
            String tempCategoryIdxs = "";
            for (int i = 0; i < mCategoryIdx.length; i++) {
                if (!GlobalMemberValues.isStrEmpty(mCategoryIdx[i])) {
                    tempCategoryIdxs += mCategoryIdx[i];
                    tempCategoryIdxs += ",";
                }
            }

            String sendCategoryIdxs = "";
            if (!GlobalMemberValues.isStrEmpty(tempCategoryIdxs)) {
                String[] tempReturnStr = tempCategoryIdxs.split(",");
                for (int i = 0; i < tempReturnStr.length; i++) {
                    sendCategoryIdxs += tempReturnStr[i];
                    if (i < (tempReturnStr.length - 1)) {
                        sendCategoryIdxs += "JJJ";
                    }
                }
            }

            CloudBackOffice.mCategoryIdx = sendCategoryIdxs;
            GlobalMemberValues.logWrite("downloadtestjjjlog", "CloudBackOffice.mCategoryIdx : " + CloudBackOffice.mCategoryIdx + "\n");
        }
    }

    public void setAllButtonStatus(boolean paramIsRollover) {
        if (paramIsRollover) {
            for (int i = 0; i < mBtnArr.length; i++) {
                mBtnArr[i].setBackgroundResource(R.drawable.button_selector_download2);
                mBtnArr[i].setTextColor(Color.parseColor("#ffffff"));
            }
        } else {
            for (int i = 0; i < mBtnArr.length; i++) {
                mBtnArr[i].setBackgroundResource(R.drawable.button_selector_download1);
                mBtnArr[i].setTextColor(Color.parseColor("#034dd3"));
            }
        }
    }

    public void categoryIdxInit() {
        if (mCategoryIdx != null) {
            for (int i = 0; i < mCategoryIdx.length; i++) {
                mCategoryIdx[i] = "";
            }
        }
    }

    public void allCategorySelect(boolean paramType) {
        if (mCategoryBtns != null) {
            Button topCategoryBtn;
            for (int i = 0; i < mCategoryBtns.size(); i++) {
                topCategoryBtn = mCategoryBtns.get(i);
                if (paramType) {
                    topCategoryBtn.setBackgroundResource(R.drawable.button_selector_download2);
                    topCategoryBtn.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    topCategoryBtn.setBackgroundResource(R.drawable.button_selector_download1);
                    topCategoryBtn.setTextColor(Color.parseColor("#034dd3"));
                }
            }
        }
    }

    private void closeActivity(boolean isProdialClose) {
        if ((BackOfficeCommand.mActivity_BackOfficeCommand != null) && (!BackOfficeCommand.mActivity_BackOfficeCommand.isFinishing())) {
            BackOfficeCommand.mActivity_BackOfficeCommand.finish();
        }

        if (isProdialClose) {
            if (MainActivity.proCustomDial != null && MainActivity.proCustomDial.isShowing()) {
                MainActivity.proCustomDial.dismiss();
            }
        }

        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

}
