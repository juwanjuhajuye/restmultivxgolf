package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CloudBackOffice extends Activity {
    static Activity mActivity;
    static Context context;

    private final Handler handler = new Handler();

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private Button cloudBackOfficeDownloadButton;
    private TextView cloudBackOfficeSyncDataTimeTextView;
    private WebView cloudBackOfficeview;

    private LinearLayout cloudBackOfficeTopBarLinearLayout1, cloudBackOfficeTopBarLinearLayout2;

    private Button cloudBackDashBoardButton, cloudBackStoreButton, cloudBackEmployeeButton;
    private Button cloudBackServiceButton, cloudBackProductButton, cloudBackGiftcardButton;
    private Button cloudBackMemberButton, cloudBackCouponButton, cloudBackReportButton;
    private Button cloudBackSaleDataUploadtButton;

    private Button cloudBackOfficeDeskTopCloudButton;
    public static Button cloudBackOfficeItemDownloadButton;

    private String mCurrentUrl;

    Intent mIntent;

    private Button mCurrentBtn = null;

    ProgressDialog dialog;

    String mCurrentSyncButtonStr = "";

    public static ProgressDialog itemProDial;

    public int mTempFlagItemDown = 0;

    public int mCloudPageViewCount = 0;          // 클라우드 메뉴 오픈 횟수
    public int mDownloadCount = 0;               // 항목별 다운로드한 횟수

    // 웹에서 input type="file" 관련 가능하게 하기 위한 값들... ---------------------
    private static final int FILECHOOSER_RESULTCODE   = 1;
    private final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;
    private ValueCallback<Uri> mUploadMessage = null;
    private ValueCallback<Uri[]> filePathCallbackLollipop;

    private static final int READ_REQUEST_CODE = 42;
    // --------------------------------------------------------------------------

    // 카테고리별 다운로드를 위한 변수
    public static String mProcessDownload = "N";
    public static String mCategoryIdx = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cloud_back_office);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            closeActivity();
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        GlobalMemberValues.logWrite("displanylogdata", "width : " + GlobalMemberValues.thisTabletRealWidth + "\n");
        GlobalMemberValues.logWrite("displanylogdata", "height : " + GlobalMemberValues.thisTabletRealHeight + "\n");

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 99;
        if (GlobalMemberValues.thisTabletRealWidth > 1280) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
        }
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 95;
        if (GlobalMemberValues.thisTabletRealHeight > 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 92;
        }

        // Device 가 Elo 일 때...
        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
        } else {
            if (GlobalMemberValues.isDeviceElo()) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
            } else {
                if (GlobalMemberValues.thisTabletRealHeight == 1920 && GlobalMemberValues.thisTabletRealWidth == 1032) {
                    parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
                }
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.cloudBackOfficeLinearLayout);
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
            closeActivity();
        }

        setContents();
    }

    public void setContents() {
        cloudBackOfficeTopBarLinearLayout1 = (LinearLayout) findViewById(R.id.cloudBackOfficeTopBarLinearLayout1);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            cloudBackOfficeTopBarLinearLayout1.setPadding(5, 5, 5, 5);
        }
        cloudBackOfficeTopBarLinearLayout2 = (LinearLayout) findViewById(R.id.cloudBackOfficeTopBarLinearLayout2);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            cloudBackOfficeTopBarLinearLayout2.setPadding(5, 5, 5, 5);
        }

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.cloudBackOfficeCloseBtn);
        closeBtn.setOnClickListener(cloudBackOfficeBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        // 다운로드 버튼 생성 및 클릭 리스너 연결
        cloudBackOfficeDownloadButton = (Button) findViewById(R.id.cloudBackOfficeDownloadButton);
        cloudBackOfficeDownloadButton.setOnClickListener(cloudBackOfficeBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudBackOfficeDownloadButton.setText("");
            cloudBackOfficeDownloadButton.setBackgroundResource(R.drawable.ab_imagebutton_cloud_appdown);
        } else {
            cloudBackOfficeDownloadButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    cloudBackOfficeDownloadButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        // 최종 싱크데이터 일시 TextView 생성 ---------------------------------------------------------
        cloudBackOfficeSyncDataTimeTextView = (TextView)findViewById(R.id.cloudBackOfficeSyncDataTimeTextView);
        String syncDataLastTime = GlobalMemberValues.getSyncDataLastTime(dbInit);
        if (!GlobalMemberValues.isStrEmpty(syncDataLastTime)) {
            cloudBackOfficeSyncDataTimeTextView.setText("LAST SYNC : " + syncDataLastTime);
            cloudBackOfficeSyncDataTimeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + cloudBackOfficeSyncDataTimeTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        }
        // --------------------------------------------------------------------------------------------

        cloudBackDashBoardButton = (Button) findViewById(R.id.cloudBackDashBoardButton);
        cloudBackDashBoardButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackStoreButton = (Button) findViewById(R.id.cloudBackStoreButton);
        cloudBackStoreButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackEmployeeButton = (Button) findViewById(R.id.cloudBackEmployeeButton);
        cloudBackEmployeeButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackServiceButton = (Button) findViewById(R.id.cloudBackServiceButton);
        cloudBackServiceButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackProductButton = (Button) findViewById(R.id.cloudBackProductButton);
        cloudBackProductButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackGiftcardButton = (Button) findViewById(R.id.cloudBackGiftcardButton);
        cloudBackGiftcardButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackMemberButton = (Button) findViewById(R.id.cloudBackMemberButton);
        cloudBackMemberButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackCouponButton = (Button) findViewById(R.id.cloudBackCouponButton);
        cloudBackCouponButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackReportButton = (Button) findViewById(R.id.cloudBackReportButton);
        cloudBackReportButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackSaleDataUploadtButton = (Button) findViewById(R.id.cloudBackSaleDataUploadtButton);
        cloudBackSaleDataUploadtButton.setOnClickListener(cloudBackOfficeBtnClickListener);

        cloudBackOfficeItemDownloadButton = (Button) findViewById(R.id.cloudBackOfficeItemDownloadButton);
        cloudBackOfficeItemDownloadButton.setText("");
        cloudBackOfficeItemDownloadButton.setOnClickListener(cloudBackOfficeBtnClickListener);


        cloudBackOfficeDeskTopCloudButton = (Button) findViewById(R.id.cloudBackOfficeDeskTopCloudButton);
        cloudBackOfficeDeskTopCloudButton.setOnClickListener(cloudBackOfficeBtnClickListener);
        cloudBackOfficeDeskTopCloudButton.setVisibility(View.GONE);


        /***********************************************************************************************************/

        cloudBackOfficeview = (WebView)findViewById(R.id.cloudBackOfficeview);

        /**
         // 쿠키 삭제 ----------------------------------------------------------------------------------------------
         CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(cloudBackOfficeview.getContext());
         CookieManager cookieManager = CookieManager.getInstance();
         cookieManager.setAcceptCookie(true);
         cookieManager.removeSessionCookie();
         cookieManager.removeAllCookie();
         cookieSyncManager.sync();
         // -------------------------------------------------------------------------------------------------------
         **/

        //스크롤바 없애기
        cloudBackOfficeview.setVerticalScrollBarEnabled(false);
        cloudBackOfficeview.setHorizontalScrollBarEnabled(false);

        cloudBackOfficeview.setWebViewClient(new WebViewClient());
        WebSettings set = cloudBackOfficeview.getSettings();

        // 화면 꽉 차게
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        set.setLoadWithOverviewMode(true);
        set.setUseWideViewPort(true);
        cloudBackOfficeview.setInitialScale(1);
        //자바스크립트 가능하게
        set.setJavaScriptEnabled(true);
        //웹뷰 멀티터치 가능하게
        set.setBuiltInZoomControls(true);
        set.setSupportZoom(true);
        //Local Storage, sessionStorage 유효화
        set.setDomStorageEnabled(true);
        //window.open 메서드 이용할 때의 동작 설정
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        //아래것 사용하면 팝업이 사용안됨
        //set.setSupportMultipleWindows(true);

        //API Level 16 부터 이용 가능
        try {
            set.setAllowUniversalAccessFromFileURLs(true);
        } catch(Exception e) {
            //아무것도 하지 않는다.
        }

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메소두를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.thisWebViewName.setMessage("메시지 내용");
        cloudBackOfficeview.addJavascriptInterface(new AndroidBridge(), "cloudBackOffice");

        cloudBackOfficeview.setWebChromeClient(wcc);

        cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_WEB_URL_NOTOP + GlobalMemberValues.STATION_CODE + "&empid=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid);
        GlobalMemberValues.logWrite("webviewurladdress", "cloud url : " + GlobalMemberValues.CLOUD_WEB_URL_NOTOP + GlobalMemberValues.STATION_CODE + "&empid=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid + "\n");
        cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

        setButtonBg(cloudBackDashBoardButton);
        setItemDownloadButtonBg("");

        mCurrentUrl = null;

        mCloudPageViewCount = 0;
        mDownloadCount = 0;
    }

    WebChromeClient wcc = new WebChromeClient() {
        // For Android 3.0-

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            GlobalMemberValues.logWrite("FileChooserTest", "여기1\n");

            mUploadMessage = uploadMsg;
            //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            //i.setType("*/*");
            CloudBackOffice.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            GlobalMemberValues.logWrite("FileChooserTest", "여기2\n");

            mUploadMessage = uploadMsg;
            //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            //i.setType("image/*");
            i.setType("*/*");
            CloudBackOffice.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }


        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            //openFileChooser( uploadMsg, "" );
            mUploadMessage = uploadMsg;
            //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            CloudBackOffice.this.startActivityForResult( Intent.createChooser( i, "File Chooser" ), FILECHOOSER_RESULTCODE);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            Log.d("MainActivity", "5.0+");
            if (filePathCallbackLollipop != null) {
                filePathCallbackLollipop.onReceiveValue(null);
                filePathCallbackLollipop = null;
            }
            filePathCallbackLollipop = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            //Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            CloudBackOffice.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_LOLLIPOP_REQ_CODE);

            GlobalMemberValues.logWrite("FileChooserTest", "여기4\n");

            return true;
        }


        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            new AlertDialog
                    .Builder(context)
                    .setTitle("AlertDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,String message, final JsResult result) {
            new AlertDialog.Builder(context)
                    .setTitle("ConfirmDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();

            return true;
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        GlobalMemberValues.logWrite("activeresultvalue", "requestCode : " + requestCode + "\n");
        GlobalMemberValues.logWrite("activeresultvalue", "resultCode : " + resultCode + "\n");
        GlobalMemberValues.logWrite("activeresultvalue", "RESULT_OK : " + RESULT_OK + "\n");

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_LOLLIPOP_REQ_CODE) {
            if (filePathCallbackLollipop == null) return ;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            }
            filePathCallbackLollipop = null;
        }

    }

    View.OnClickListener cloudBackOfficeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cloudBackOfficeCloseBtn : {
                    if (mDownloadCount > 0) {           // 항목별 다운로드를 한번이상 했을 경우
                        new AlertDialog.Builder(context)
                                .setTitle("Data Download")
                                .setMessage("You must log out to reflect the downloaded data.\nAre you sure you want to sign out?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                GlobalMemberValues.ITEMCANCELAPPLY = 1;
                                                GlobalMemberValues.MAINRECREATEYN = "Y";
                                                //MainActivity.mActivity.recreate();
                                                MainActivity.employeeLogout();
                                                closeActivity();

                                                if (mCurrentSyncButtonStr.equals("member")) {
                                                    try {
                                                        Thread.sleep(5000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    GlobalMemberValues.copyMileageFromTempMileageCart();
                                                }
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    } else {
                        closeActivity();
                    }
                    break;
                }

                case R.id.cloudBackDashBoardButton : {
                    String tempUrl = "main.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackStoreButton : {
                    String tempUrl = "stores/store_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackEmployeeButton : {
                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    String tempUrl = "employees/employee_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackServiceButton : {
                    String tempUrl = "menus/list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackProductButton : {
                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    String tempUrl = "product/product_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackGiftcardButton : {
                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    String tempUrl = "giftcard/giftcard_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackMemberButton : {
                    // Elo 일 경우 바코드 스캐너 활성화
                    if (GlobalMemberValues.isDeviceElo()) {
                        GlobalMemberValues.eloBarcodeScannerOn();
                    }

                    String tempUrl = "member/member_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackCouponButton : {
                    String tempUrl = "coupon/coupon_list.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackReportButton : {
                    String tempUrl = "report/dailyreport.asp";
                    cloudBackOfficeview.loadUrl(GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl);
                    GlobalMemberValues.logWrite("webviewurladdress", "touch url : " + GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS + tempUrl + "\n");
                    cloudBackOfficeview.setWebViewClient(new WebViewClientClass());

                    mCloudPageViewCount++;

                    break;
                }

                case R.id.cloudBackOfficeDownloadButton : {
                    // 테이블 추가, 수정, 삭제 및 컬럼 추가, 삭제 -------------------------------------------
                    DatabaseInit dbInit = new DatabaseInit(context);
                    dbInit.initDatabaseTables();
                    // ----------------------------------------------------------------------------------

                    // 인터넷 연결되었을 경우에만
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            return;
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("Data Download")
                                .setMessage("POS data will be replaced by Cloud Data\nand Cloud Page will be closed\nDo you want to download?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                closeActivity();
                                                GlobalMemberValues.MSYNCDATATYPE = "ITEM_DOWNLOAD";
                                                CommandButton.setDatabaseAndApiDataDownloadThread(0, GlobalMemberValues.INSERTDATAAFTERDELETE);
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                    }

                    break;
                }

                case R.id.cloudBackOfficeItemDownloadButton : {
                    checkDownloadType();
                    break;
                }

                case R.id.cloudBackOfficeDeskTopCloudButton : {
                    String tempUrl = GlobalMemberValues.CLOUD_WEB_URL_NOTOP + GlobalMemberValues.STATION_CODE + "&notopyn=N";
                    openBrowser(tempUrl);
                    break;
                }

                case R.id.cloudBackSaleDataUploadtButton : {
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
                                                salesDataUpload(tempTimeToUpload);
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

    public static void salesDataUpload(double paramTimeToUpload) {
        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(context, mActivity);

        itemProDial = ProgressDialog.show(context, "Sale Data Upload", "Sale Data is Uploading...", true, false);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                itemProDial.dismiss();
                GlobalMemberValues.displayDialog(context, "Sale Data Upload", "Sale data uploaded", "Close");
            }
        }, (long)(paramTimeToUpload * 1000));
    }
    public static void salesDataUploadInCommand(double paramTimeToUpload, final Context context1) {
        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(CommandDownloadData.mActivity.getApplication().getApplicationContext(), CommandDownloadData.mActivity);

        final ProgressDialog itemProDial_temp = ProgressDialog.show(context1, "Sale Data Upload", "Sale Data is Uploading...", true, false);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                itemProDial_temp.dismiss();
                GlobalMemberValues.displayDialog(context1, "Sale Data Upload", "Sale data uploaded", "Close");
            }
        }, (long)(paramTimeToUpload * 1000));
    }

    public void checkDownloadType() {
        if (mProcessDownload == "Y" || mProcessDownload.equals("Y")) {
            syncDataFromCloudByItem();
        } else {
            String tempType = cloudBackOfficeItemDownloadButton.getText().toString();
            if (!GlobalMemberValues.isStrEmpty(tempType)) {
                if (tempType.equals("SYNCHRONIZE MENU DATA")) {
                    if (!GlobalMemberValues.isStrEmpty(mCategoryIdx)) {
                        syncDataFromCloudByItem();
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("")
                                .setMessage("Touch <Yes> to download the entire menu, or touch <No> to download by category")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mCategoryIdx = "";
                                                cloudBackOfficeItemDownloadButton.setText("SYNCHRONIZE MENU DATA");

                                                syncDataFromCloudByItem();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent tempIntent = new Intent(MainActivity.mContext.getApplicationContext(), CloudBackOfficeCategoryDownload.class);
                                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                        //tempIntent.putExtra("ParentMainMiddleService", this.getClass());
                                        //tempIntent.putExtra("saleHistoryTagValue", "");
                                        // -------------------------------------------------------------------------------------
                                        mActivity.startActivity(tempIntent);
                                        if (GlobalMemberValues.isUseFadeInOut()) {
                                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                        }
                                    }
                                })
                                .show();
                    }
                } else {
                    syncDataFromCloudByItem();
                }
            }
        }
    }

    public void syncDataFromCloudByItem() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(mCurrentSyncButtonStr)) {
                // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
                CommandButton.backupDatabase(false);

                if (mCurrentSyncButtonStr.equals("member")) {
                    // 백업전 먼저 salon_member 의 고객포인트를 임시저장소(temp_mileagecart) 에 저장한다.
                    boolean tempBoolean = GlobalMemberValues.saveTempMileageCart();
                    if (!tempBoolean) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Error", "Close");
                        return;
                    }
                }

                String[] tblNameArr = null;
                String tempTblMsg = "";

                switch (mCurrentSyncButtonStr) {
                    case "store" : {
                        tblNameArr = new String[]{
                                "salon_info1", "salon_info2", "salon_general",
                                "salon_storeinfo",
                                "salon_storestationinfo", "salon_storegeneral",
                                "salon_storeinfo_worktime", "salon_storeinfo_worktime_forPOS",
                                "salon_store_cashinoutreason", "salon_store_deliveryappcompany",
                                "salon_storediscountbutton"
                        };
                        tempTblMsg = "store";
                        break;
                    }
                    case "employee" : {
                        tblNameArr = new String[]{
                                "salon_storeemployee", "salon_storeemployee_worktime", "salon_storeemployee_workweekday", "salon_storepart"
                        };
                        tempTblMsg = "employee";
                        break;
                    }
                    case "service" : {
                        tblNameArr = new String[]{
                                "salon_storeservice_main",
                                "salon_storeservice_sub", "salon_storeservice_sub_order",
                                "salon_storeservice_sub_setmenu",
                                "salon_storeservice_option_btn", "salon_storeservice_option", "salon_storeservice_option_item", "salon_storeservice_additional",
                                "salon_storeservice_sub_timemenuprice"
                        };
                        tempTblMsg = "service";
                        break;
                    }
                    case "product" : {
                        tblNameArr = new String[]{
                                "salon_storeproduct", "salon_storeproduct_ipkohistory"
                        };
                        tempTblMsg = "product";
                        break;
                    }
                    case "giftcard" : {
                        tblNameArr = new String[]{
                                "salon_storegiftcard"
                        };
                        tempTblMsg = "giftcard";
                        break;
                    }
                    case "member" : {
                        tblNameArr = new String[]{
                                "member1", "member2",
                                "salon_member", "salon_storememosel"
                        };
                        tempTblMsg = "member";
                        break;
                    }
                    case "coupon" : {
                        tblNameArr = new String[]{
                                "coupon", "coupon_imgtype",
                                "coupon_issue_history"
                        };
                        tempTblMsg = "coupon";
                        break;
                    }
                }

                final String[] finalTblNameArr = tblNameArr;
                final String finalTempTblMsg = tempTblMsg;

                new AlertDialog.Builder(context)
                        .setTitle("Data Download")
                        .setMessage("POS data will be replaced by Cloud Data\nDo you want to download?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        syncDataFromCloudByItemExe(finalTblNameArr, finalTempTblMsg);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            } else {
                mCurrentSyncButtonStr = "";
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
        }
    }

    // 항목별 API 데이터 다운로드 ------------------------------------------------------------------------
    public void syncDataFromCloudByItemExe(String[] paramTblArr, final String paramTblMsg) {

        final String[] tblNameArr = paramTblArr;
        final String tempTblMsg = paramTblMsg;

        if (tblNameArr != null) {
            // 프로그래스 바를 실행~
            itemProDial = ProgressDialog.show(
                    context, tempTblMsg.toUpperCase() + " DATA DOWNLOAD", tempTblMsg.toUpperCase() + " Data is Downloading...", true, false
            );
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 화면재개 지연시간 초기화
                    GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

                    // 1. 처리가 오래걸리는 부분 실행 -------------------------------------------------
                    syncDataFromCloudByItemExe2(tblNameArr, tempTblMsg);
                    // -----------------------------------------------------------------------------

                    try {
                        Thread.sleep(GlobalMemberValues.getIntAtString(GlobalMemberValues.RESTARTSCREEN_DELYTIME));
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

    public void syncDataFromCloudByItemExe2(String[] paramTblArr, String paramTblMsg) {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            dbInit.insertDownloadDataInDatabase(paramTblArr, paramTblMsg, 1);
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
        }
    }

    private Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            // -------------------------------------------------------------------------------------
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            itemProDial.dismiss();
            mCategoryIdx = "";
            mProcessDownload = "N";
            // -------------------------------------------------------------------------------------
        }
    };


    private void setButtonBg(Button paramTouchBtn) {
        cloudBackDashBoardButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_dashboard);
        cloudBackStoreButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_store);
        cloudBackEmployeeButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_employee);
        cloudBackServiceButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_service);
        cloudBackProductButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_product);
        cloudBackGiftcardButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_giftcard);
        cloudBackMemberButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_member);
        cloudBackCouponButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_coupon);
        cloudBackReportButton.setBackgroundResource(R.drawable.ab_imagebutton_cloudback_report);

        // 터치한 버튼의 배경을 rollover 이미지로 변경
        switch (paramTouchBtn.getId()) {
            case R.id.cloudBackDashBoardButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_dashboard_over);
                break;
            }
            case R.id.cloudBackStoreButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_store_over);
                break;
            }
            case R.id.cloudBackEmployeeButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_employee_over);
                break;
            }
            case R.id.cloudBackServiceButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_service_over);
                break;
            }
            case R.id.cloudBackProductButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_product_over);
                break;
            }
            case R.id.cloudBackGiftcardButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_giftcard_over);
                break;
            }
            case R.id.cloudBackMemberButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_member_over);
                break;
            }
            case R.id.cloudBackCouponButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_coupon_over);
                break;
            }
            case R.id.cloudBackReportButton : {
                paramTouchBtn.setBackgroundResource(R.drawable.aa_images_cloudback_report_over);
                break;
            }
        }
    }

    private void setItemDownloadButtonBg(String paramTxt) {
        if (GlobalMemberValues.isStrEmpty(paramTxt)) {
            cloudBackOfficeItemDownloadButton.setVisibility(View.GONE);
            mCategoryIdx = "";
        } else {
            cloudBackOfficeItemDownloadButton.setVisibility(View.VISIBLE);
            cloudBackOfficeItemDownloadButton.setText(paramTxt);
            //cloudBackOfficeItemDownloadButton.setVisibility(View.GONE);

            mCategoryIdx = "";
            mProcessDownload = "N";
        }
    }

    private void setLeftSelectedMenu(String paramUrl) {
        if (!GlobalMemberValues.isStrEmpty(paramUrl)) {
            String checkStr = GlobalMemberValues.getReplaceText(paramUrl, GlobalMemberValues.CLOUD_SERVER_URL, "");

            if (checkStr == "" || checkStr.indexOf("main.asp") != -1) {
                setButtonBg(cloudBackDashBoardButton);
                setItemDownloadButtonBg("");
                mCurrentSyncButtonStr = "dashboard";
            } else if (checkStr.indexOf("salon/") != -1 || checkStr.indexOf("stores/") != -1
                    || checkStr.indexOf("store_general/") != -1 || checkStr.indexOf("station/") != -1) {
                setButtonBg(cloudBackStoreButton);
                setItemDownloadButtonBg("SYNCHRONIZE STORE DATA");
                mCurrentSyncButtonStr = "store";
            } else if (checkStr.indexOf("employees/") != -1) {
                setButtonBg(cloudBackEmployeeButton);
                setItemDownloadButtonBg("SYNCHRONIZE EMPLOYEE DATA");
                mCurrentSyncButtonStr = "employee";
            } else if (checkStr.indexOf("menus/") != -1) {
                setButtonBg(cloudBackServiceButton);
                setItemDownloadButtonBg("SYNCHRONIZE MENU DATA");
                mCurrentSyncButtonStr = "service";
            } else if (checkStr.indexOf("product/") != -1) {
                setButtonBg(cloudBackProductButton);
                setItemDownloadButtonBg("SYNCHRONIZE PRODUCT DATA");
                mCurrentSyncButtonStr = "product";
            } else if (checkStr.indexOf("giftcard/") != -1) {
                setButtonBg(cloudBackGiftcardButton);
                setItemDownloadButtonBg("SYNCHRONIZE GIFTCARD DATA");
                mCurrentSyncButtonStr = "giftcard";
            } else if (checkStr.indexOf("member/") != -1 || checkStr.indexOf("member_birthday/") != -1) {
                setButtonBg(cloudBackMemberButton);
                setItemDownloadButtonBg("SYNCHRONIZE MEMBER DATA");
                mCurrentSyncButtonStr = "member";
            } else if (checkStr.indexOf("coupon/") != -1) {
                setButtonBg(cloudBackCouponButton);
                setItemDownloadButtonBg("SYNCHRONIZE COUPON DATA");
                mCurrentSyncButtonStr = "coupon";
            } else if (checkStr.indexOf("report/") != -1) {
                setButtonBg(cloudBackReportButton);
                setItemDownloadButtonBg("");
                mCurrentSyncButtonStr = "report";
            } else {

            }
        }

    }

    public void openBrowser(String paramUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(paramUrl));
        startActivity(intent);
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) {
            handler.post(new Runnable() {
                public void run() {
                    if (arg.equals("Y") || arg == "Y") {
                        mActivity.finish();
                        //GlobalMemberValues.displayDialog(context, "Warning", "You are not authorized to access cloud", "Close");
                        Toast.makeText(CloudBackOffice.this, "You are not authorized to access cloud", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        @JavascriptInterface
        public void openBrowserUrl(final String paramUrl) {
            openBrowser(paramUrl);
        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
            }
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);

            GlobalMemberValues.logWrite("webcloudurl", "url : " + url + "\n");

            // 클라우드 웹페이지에 따라 좌측 메뉴의 선택메뉴를 지정한다.
            setLeftSelectedMenu(url);

            if (url.indexOf("poswebnotop.asp") == -1 && mCloudPageViewCount > 0) {
                //GlobalMemberValues.logWrite("webcloudurl", "url.indexOf(main.asp) : " + url.indexOf("main.asp") + "\n");
                //GlobalMemberValues.logWrite("webcloudurl", "여기실행...\n");

                /**
                dialog = new ProgressDialog(mActivity);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Navyzebra Salon POS Cloud");
                dialog.setMessage("Page Loading...");
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                 **/

                dialog = new JJJCustomAnimationDialog(mActivity);
                dialog.show();
            }

            String urlString = cloudBackOfficeview.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {
                cloudBackOfficeDownloadButton.setVisibility(View.VISIBLE);
                cloudBackOfficeSyncDataTimeTextView.setVisibility(View.VISIBLE);
            } else {
                cloudBackOfficeDownloadButton.setVisibility(View.INVISIBLE);
                cloudBackOfficeSyncDataTimeTextView.setVisibility(View.INVISIBLE);
            }
            //GlobalMemberValues.displayDialog(context, "Warninig", urlString, "Close");
        }

        // 페이지 로딩시
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(mActivity, "Web Error : " + description, Toast.LENGTH_SHORT).show();
            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }

            String urlString = cloudBackOfficeview.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {
                cloudBackOfficeDownloadButton.setVisibility(View.VISIBLE);
                cloudBackOfficeSyncDataTimeTextView.setVisibility(View.VISIBLE);
            } else {
                cloudBackOfficeDownloadButton.setVisibility(View.INVISIBLE);
                cloudBackOfficeSyncDataTimeTextView.setVisibility(View.INVISIBLE);
            }
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
    }

    @Override
    //Back 버튼 클릭시 뒤로가기 추가 뒤로가기 더이상 없으면 종료
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && cloudBackOfficeview.canGoBack()) {
            cloudBackOfficeview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    //종료처리시 종료할지 물어보기 추가
    public void onBackPressed() {
        if (mDownloadCount > 0) {           // 항목별 다운로드를 한번이상 했을 경우
            new AlertDialog.Builder(context)
                    .setTitle("Data Download")
                    .setMessage("Are you quit?\nYou reload 'POS System' to reflect the updated information?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.mActivity.recreate();
                                    closeActivity();
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Are you quit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            closeActivity();
                        }
                    }).show();
        }
    }

    public static void openDialog() {
        GlobalMemberValues.displayDialog(context, "Menu Download", "Click the <SYNCRONIZE> button at the bottom right to download", "Close");
    }

    private void closeActivity() {
        mActivity.finish();
        // Elo 일 경우 바코드 스캐너 활성화
        if (GlobalMemberValues.isDeviceElo()) {
            GlobalMemberValues.eloBarcodeScannerOff();
        }
    }

    // 자동회전시 새로고침 방지 --------------------------------------------
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        cloudBackOfficeview.saveState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        cloudBackOfficeview.restoreState(savedInstanceState);
    }
    // -------------------------------------------------------------------
}
