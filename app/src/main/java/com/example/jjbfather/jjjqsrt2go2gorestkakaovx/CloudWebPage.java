package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CloudWebPage extends Activity {
    static Activity mActivity;
    Context context;

    private final Handler handler = new Handler();

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private Button cloudWebDownloadButton, cloudWebPageLogoutButton;
    private TextView cloudWebSyncDataTimeTextView;
    private WebView cloudWebview;

    private LinearLayout cloudWebTopBarLinearLayout1, cloudWebTopBarLinearLayout2;

    private String mCurrentUrl;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cloud_web_page);
        this.setFinishOnTouchOutside(false);

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            finish();
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 98;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 90;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.cloudWebPageLinearLayout);
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
            finish();
        }

        setContents();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setContents() {
        mActivity = this;
        context = this;

        cloudWebTopBarLinearLayout1 = (LinearLayout) findViewById(R.id.cloudWebTopBarLinearLayout1);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            cloudWebTopBarLinearLayout1.setPadding(5, 5, 5, 5);
        }
        cloudWebTopBarLinearLayout2 = (LinearLayout) findViewById(R.id.cloudWebTopBarLinearLayout2);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            cloudWebTopBarLinearLayout2.setPadding(5, 5, 5, 5);
        }

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.cloudWebPageCloseBtn);
        closeBtn.setOnClickListener(cloudWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_cloud_close);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 다운로드 버튼 생성 및 클릭 리스너 연결
        cloudWebDownloadButton = (Button) findViewById(R.id.cloudWebDownloadButton);
        cloudWebDownloadButton.setOnClickListener(cloudWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudWebDownloadButton.setText("");
            cloudWebDownloadButton.setBackgroundResource(R.drawable.ab_imagebutton_cloud_appdown);
        } else {
            cloudWebDownloadButton.setTextSize(
                    cloudWebDownloadButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 최종 싱크데이터 일시 TextView 생성 ---------------------------------------------------------
        cloudWebSyncDataTimeTextView = (TextView)findViewById(R.id.cloudWebSyncDataTimeTextView);
        String syncDataLastTime = GlobalMemberValues.getSyncDataLastTime(dbInit);
        if (!GlobalMemberValues.isStrEmpty(syncDataLastTime)) {
            cloudWebSyncDataTimeTextView.setText("LAST SYNCHRONIZED : " + syncDataLastTime);
            cloudWebSyncDataTimeTextView.setTextSize(cloudWebSyncDataTimeTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        }
        // --------------------------------------------------------------------------------------------

        // 로그아웃 버튼 생성 및 클릭 리스너 연결
        cloudWebPageLogoutButton = (Button) findViewById(R.id.cloudWebPageLogoutButton);
        cloudWebPageLogoutButton.setOnClickListener(cloudWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudWebPageLogoutButton.setText("");
            cloudWebPageLogoutButton.setBackgroundResource(R.drawable.ab_imagebutton_cloud_logout);
        } else {
            cloudWebPageLogoutButton.setTextSize(
                    cloudWebPageLogoutButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        cloudWebview = (WebView)findViewById(R.id.cloudWebview);

        /**
        // 쿠키 삭제 ----------------------------------------------------------------------------------------------
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(cloudWebview.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();
        // -------------------------------------------------------------------------------------------------------
         **/

        //스크롤바 없애기
        cloudWebview.setVerticalScrollBarEnabled(false);
        cloudWebview.setHorizontalScrollBarEnabled(false);

        cloudWebview.setWebViewClient(new WebViewClient());
        WebSettings set = cloudWebview.getSettings();

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
        cloudWebview.addJavascriptInterface(new AndroidBridge(), "thisWebViewName");

        //alert 안될경우 아래를 넣어준다.
        final Context myApp = this;
        cloudWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog
                        .Builder(myApp)
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
        });

        cloudWebview.loadUrl(GlobalMemberValues.CLOUD_WEB_URL + GlobalMemberValues.STATION_CODE);
        //GlobalMemberValues.logWrite("webviewurladdress", "cloud url : " + GlobalMemberValues.CLOUD_WEB_URL + GlobalMemberValues.STATION_CODE + "\n");
        cloudWebview.setWebViewClient(new WebViewClientClass());
        mCurrentUrl = null;
    }

    View.OnClickListener cloudWebPageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cloudWebPageCloseBtn : {
                    finish();
                    break;
                }
                case R.id.cloudWebDownloadButton : {
                    // 인터넷 연결되었을 경우에만
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        new AlertDialog.Builder(context)
                                .setTitle("Data Download")
                                .setMessage("POS data will be replaced by Cloud Data\nand Cloud Page will be closed\nDo you want to download?")
                                        //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                GlobalMemberValues.MSYNCDATATYPE = "CLOUD_DOWNLOAD";
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
                case R.id.cloudWebPageLogoutButton: {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        new AlertDialog.Builder(context)
                                .setTitle("Cloud Logout")
                                .setMessage("Are you logout cloud?")
                                        //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                GlobalMemberValues.logWrite("cloudwebpagelog", "station code : " + GlobalMemberValues.STATION_CODE + "\n");
                                                GlobalMemberValues.logWrite("cloudwebpagelog", "logout url : " + GlobalMemberValues.CLOUD_WEB_LOGOUT_URL +
                                                        GlobalMemberValues.STATION_CODE + "\n");
                                                cloudWebview.loadUrl(GlobalMemberValues.CLOUD_WEB_LOGOUT_URL + GlobalMemberValues.STATION_CODE);
                                                cloudWebview.setWebViewClient(new WebViewClientClass());
                                                mCurrentUrl = null;
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
            }
        }
    };

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) {
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(CloudWebPage.this, arg, Toast.LENGTH_SHORT).show();
                }
            });
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
            String urlString = cloudWebview.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {
                cloudWebPageLogoutButton.setVisibility(View.VISIBLE);
                cloudWebDownloadButton.setVisibility(View.VISIBLE);
                cloudWebSyncDataTimeTextView.setVisibility(View.VISIBLE);
            } else {
                cloudWebPageLogoutButton.setVisibility(View.INVISIBLE);
                cloudWebDownloadButton.setVisibility(View.INVISIBLE);
                cloudWebSyncDataTimeTextView.setVisibility(View.INVISIBLE);
            }
            //GlobalMemberValues.displayDialog(context, "Warninig", urlString, "Close");
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String urlString = cloudWebview.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {
                cloudWebPageLogoutButton.setVisibility(View.VISIBLE);
                cloudWebDownloadButton.setVisibility(View.VISIBLE);
                cloudWebSyncDataTimeTextView.setVisibility(View.VISIBLE);
            } else {
                cloudWebPageLogoutButton.setVisibility(View.INVISIBLE);
                cloudWebDownloadButton.setVisibility(View.INVISIBLE);
                cloudWebSyncDataTimeTextView.setVisibility(View.INVISIBLE);
            }
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
    }

    @Override
    //Back 버튼 클릭시 뒤로가기 추가 뒤로가기 더이상 없으면 종료
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && cloudWebview.canGoBack()) {
            cloudWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    //종료처리시 종료할지 물어보기 추가
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you quit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();
    }


}
