package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class ClockInOutWebPage extends Activity {
    static Activity mActivity;
    Context context;

    private final Handler handler = new Handler();

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private WebView clockinoutWebPageWebView;

    private LinearLayout clockinoutWebPageTopBarLinearLayout1, clockinoutWebPageTopBarLinearLayout2;

    private String mCurrentUrl;

    Intent mIntent;

    String mSelectedClockInOutIdxs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_clock_in_out_web_page);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
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

        parentLn = (LinearLayout)findViewById(R.id.clockinoutWebPageLinearLayout);
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
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();
    }

    public void setContents() {

        clockinoutWebPageTopBarLinearLayout1 = (LinearLayout) findViewById(R.id.clockinoutWebPageTopBarLinearLayout1);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            clockinoutWebPageTopBarLinearLayout1.setPadding(5, 5, 5, 5);
        }
        clockinoutWebPageTopBarLinearLayout2 = (LinearLayout) findViewById(R.id.clockinoutWebPageTopBarLinearLayout2);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            clockinoutWebPageTopBarLinearLayout2.setPadding(5, 5, 5, 5);
        }


        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.clockinoutWebPageCloseBtn);
        closeBtn.setOnClickListener(clockinoutWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        /***********************************************************************************************************/

        clockinoutWebPageWebView = (WebView)findViewById(R.id.clockinoutWebPageWebView);

        //스크롤바 없애기
        clockinoutWebPageWebView.setVerticalScrollBarEnabled(false);
        clockinoutWebPageWebView.setHorizontalScrollBarEnabled(false);


        WebSettings set = clockinoutWebPageWebView.getSettings();
        // 웹뷰 속도개선
        clockinoutWebPageWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        set.setRenderPriority(WebSettings.RenderPriority.HIGH);
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);


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
        clockinoutWebPageWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메서드를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.웹뷰이름(여기서는 msalonAndroidClockinout 로 했음).setMessage("메시지 내용");
        clockinoutWebPageWebView.addJavascriptInterface(new AndroidBridge(), "msalonAndroidClockinout");

        //alert 안될경우 아래를 넣어준다.
        final Context myApp = this;
        clockinoutWebPageWebView.setWebChromeClient(new WebChromeClient() {
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

        clockinoutWebPageWebView.loadUrl(GlobalMemberValues.CLOCKINOUT_WEB_URL +
                "scode=" + GlobalMemberValues.SALON_CODE +
                "&sidx=" + GlobalMemberValues.STORE_INDEX +
                "&stcode=" + GlobalMemberValues.STATION_CODE +
                "&frompos=Y");
        clockinoutWebPageWebView.setWebViewClient(new WebViewClientClass());
        mCurrentUrl = null;
    }


    View.OnClickListener clockinoutWebPageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clockinoutWebPageCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(ReservationWebPage.this, msg, Toast.LENGTH_SHORT).show();
                    //GlobalMemberValues.displayDialog(context, "Inforamtion", msg, "Close");
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        mSelectedClockInOutIdxs = msg;
                    }
                }
            });
        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /**
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
            }
            **/

            view.loadUrl(url);
            mCurrentUrl = url;
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);
            String urlString = clockinoutWebPageWebView.getUrl().toString();
            if (urlString.indexOf("step_viewReservation") == -1) {
                //getReservationButton.setVisibility(View.INVISIBLE);
            } else {
                //getReservationButton.setVisibility(View.VISIBLE);
            }
            //GlobalMemberValues.displayDialog(context, "Warninig", urlString, "Close");
            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String urlString = clockinoutWebPageWebView.getUrl().toString();
            if (urlString.indexOf("step_viewReservation") == -1) {
                //getReservationButton.setVisibility(View.INVISIBLE);
            } else {
                //getReservationButton.setVisibility(View.VISIBLE);
            }
            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
        }
    }

    @Override
    //Back 버튼 클릭시 뒤로가기 추가 뒤로가기 더이상 없으면 종료
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && clockinoutWebPageWebView.canGoBack()) {
            clockinoutWebPageWebView.goBack();
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
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    }
                }).show();
    }


    // 자동회전시 새로고침 방지 --------------------------------------------
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        clockinoutWebPageWebView.saveState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        clockinoutWebPageWebView.restoreState(savedInstanceState);
    }
    // -------------------------------------------------------------------

}
