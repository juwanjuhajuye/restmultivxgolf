package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class ReservationWebPage extends Activity {
    static Activity mActivity;
    Context context;

    private final Handler handler = new Handler();

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;
    private Button closeBtn;
    private WebView reservationWebView;
    private Button getReservationButton;

    private LinearLayout reservationWebTopBarLinearLayout1, reservationWebTopBarLinearLayout2;

    private String mCurrentUrl;

    ProgressDialog dialog;

    Intent mIntent;

    String mSelectedReservationServiceIdxs;

    String pushIntentOpenType, pushReservtationCode;

    boolean getReservationButtonVisible = false;

    private static final String TAG = "ReservatonWebPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_reservation_web_page);
        this.setFinishOnTouchOutside(false);

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
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 98;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.reservationWebPageLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            pushIntentOpenType = mIntent.getStringExtra("pushIntentOpenType");
            pushReservtationCode = mIntent.getStringExtra("pushReservtationCode");
            Log.d(TAG, "넘겨받은 pushIntentOpenType : " + pushIntentOpenType);
            Log.d(TAG, "넘겨받은 pushReservtationCode : " + pushReservtationCode);
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setContents();
    }

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setContents() {
        reservationWebTopBarLinearLayout1 = (LinearLayout) findViewById(R.id.reservationWebTopBarLinearLayout1);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            reservationWebTopBarLinearLayout1.setPadding(5, 5, 5, 5);
        }
        reservationWebTopBarLinearLayout2 = (LinearLayout) findViewById(R.id.reservationWebTopBarLinearLayout2);
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            reservationWebTopBarLinearLayout2.setPadding(5, 5, 5, 5);
        }


        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button) findViewById(R.id.reservationWebPageCloseBtn);
        closeBtn.setOnClickListener(reservationWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        getReservationButton = (Button) findViewById(R.id.getReservationButton);
        getReservationButton.setOnClickListener(reservationWebPageBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            getReservationButton.setText("");
            getReservationButton.setBackgroundResource(R.drawable.ab_imagebutton_reservation_reservationdown);
        } else {
            getReservationButton.setTextSize(
                    getReservationButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        reservationWebView = (WebView)findViewById(R.id.reservationWebView);

        // 스크롤바 없애기
        reservationWebView.setVerticalScrollBarEnabled(false);
        reservationWebView.setHorizontalScrollBarEnabled(false);


        // -----------------------------------------------------------------------
        WebSettings set = reservationWebView.getSettings();

        // 화면 오른쪽 하단 확대/축소 버튼 안보이게 처리
        set.setDisplayZoomControls(false);

        // 웹뷰 속도개선
        reservationWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
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
        // -----------------------------------------------------------------------

        reservationWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메소드를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해 둔 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.웹뷰이름(여기서는 msalonAndroidReservation 로 했음).setMessage("메시지 내용");
        reservationWebView.addJavascriptInterface(new AndroidBridge(), "msalonAndroidReservation");

        //alert 안될경우 아래를 넣어준다.
        final Context myApp = this;
        reservationWebView.setWebChromeClient(new WebChromeClient() {
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


        String reservationWebUrl = GlobalMemberValues.RESERVATION_WEB_URL +
                "scode=" + GlobalMemberValues.SALON_CODE + "&sidx=" + GlobalMemberValues.STORE_INDEX;
        if (!GlobalMemberValues.isStrEmpty(pushIntentOpenType) && pushIntentOpenType.equals("reservation")) {
            // notification 의 setNumber 의 값을 초기화한다.
            // 구글 플레이 서비스 구글플레이서비스 (Google Play Service)
            //FirebaseMessagingService.notiNumbersForReservation = 0;
            // notification 삭제
            NotificationManager mNotificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(9123);

            reservationWebUrl = reservationWebUrl + "&rcode=" + pushReservtationCode;
        }
        Log.d(TAG, "url : " + reservationWebUrl);
        reservationWebView.loadUrl(reservationWebUrl);
        reservationWebView.setWebViewClient(new WebViewClientClass());
        mCurrentUrl = null;
    }

    View.OnClickListener reservationWebPageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reservationWebPageCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.getReservationButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedReservationServiceIdxs)) {
                        GlobalMemberValues.displayDialog(context, "Warninig", "No Reservation Information", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Get Reservation")
                            .setMessage("Do you want to get this reservation?")
                                    //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            getReservationInformation();
                                            finish();
                                            if (GlobalMemberValues.isUseFadeInOut()) {
                                                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                                            }
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                            .show();
                    break;
                }
            }
        }
    };

    public void getReservationInformation() {
        if (!GlobalMemberValues.isStrEmpty(mSelectedReservationServiceIdxs)) {
            GlobalMemberValues.logWrite("getReservationInformationMethod", "예약정보 : " + mSelectedReservationServiceIdxs + "\n");

            String[] strReservedInfoGroup = mSelectedReservationServiceIdxs.split("J7577101216J");
            String strReservedServiceInfo = strReservedInfoGroup[0];

            GlobalMemberValues.logWrite("getReservationInformationMethod", "예약 서비스정보 : " + strReservedServiceInfo + "\n");

            //GlobalMemberValues.displayDialog(context, "Info", "Reservation Code : " + mSelectedReservationServiceIdxs, "Close");
            // 시작시간 & "|||" & 종료시간 & "|||" & 고객아이디(이메일) & "|||" & 고객명 & "|||" & 고객메모 & "|||" & 고객전화번호 & "|||" & 예약서비스idx
            String[] strReservationInfo = strReservedServiceInfo.split(GlobalMemberValues.STRSPLITTER2);
            String tempStartTime = strReservationInfo[0];
            String tempEndTime = strReservationInfo[1];
            String tempCustomerId = strReservationInfo[2];
            String tempCustomerName = strReservationInfo[3];
            String tempCustomerMemo = strReservationInfo[4];
            String tempCustomerPhone = strReservationInfo[5];
            String tempReservedServiceIdxs = strReservationInfo[6];
            String tempEmpIdx = strReservationInfo[7];
            String tempRcode = strReservationInfo[8];

            if (!GlobalMemberValues.isStrEmpty(tempReservedServiceIdxs)) {
                // 현재 리스트를 Hold 처리한다.
                CommandButton.setHoldSales("");

                // 직원정보 셋팅 ----------------------------------
                setEmployeeInfo(tempEmpIdx);
                // ------------------------------------------------

                String tempHoldCode = GlobalMemberValues.makeHoldCode();
                Cursor serviceCursor;
                String maxIdxAfterDbInsert = "";
                String insEmpIdx = "", insEmpName = "";
                if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                    insEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                    insEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                }
                String[] strServiceIdxs = tempReservedServiceIdxs.split(GlobalMemberValues.STRSPLITTER1);
                for (int i = 0; i < strServiceIdxs.length; i++) {
                    String tempSvcIdx = strServiceIdxs[i];
                    if (!GlobalMemberValues.isStrEmpty(tempSvcIdx)) {
                        String strServiceQuery = "select a.idx, a.midx, a.servicename, a.positionNo, a.strFileName, a.strFilePath, " +
                                " a.service_price, a.commissionratioType, a.commissionratio, a.pointratio, " +
                                " a.saleprice, a.salestart, a.saleend, a.setmenuYN,  " +
                                " b.servicename as castegoryName, " +
                                " b.colorNo as castegoryColorNo" +
                                " from salon_storeservice_sub as a " +
                                " left outer join salon_storeservice_main as b on a.midx = b.idx " +
                                " where a.delyn = 'N' " +
                                " and a.idx = '" + strServiceIdxs[i] + "' " +
                                " and not(a.activeyn = 'N') " +
                                " and b.sidx='" + GlobalMemberValues.STORE_INDEX + "' " +
                                " and a.positionNo > 0 and not(a.positionNo is null or a.positionNo = '')";
                        serviceCursor = dbInit.dbExecuteRead(strServiceQuery);
                        if (serviceCursor.getCount() > 0 && serviceCursor.moveToFirst()) {

                            String insIdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(0), 1);
                            String insMidx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(1), 1);
                            String insServiceName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(2), 1);
                            String insPositionNo = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(3), 1);
                            String insStrFileName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(4), 1);
                            String insStrFilePath = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(5), 1);
                            String insServicePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(6), 1);
                            String insCommissionType = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(7), 1);
                            String insCommissionratio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(8), 1);
                            String insPointRatio = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(9), 1);
                            String insSalePrice = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(10), 1);
                            String insSaleStart = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(11), 1);
                            String insSaleEnd = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(12), 1);
                            String insSetMenuYN = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(13), 1);
                            String insCategoryName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(14), 1);
                            String insCategoryColorNo = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(15), 1);
                            // 카테고리 칼라값 구하기
                            String insCategoryColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[0];
                            if (!GlobalMemberValues.isStrEmpty(insCategoryColorNo)) {
                                insCategoryColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(insCategoryColorNo)-1];
                            }

                            String[] paramsString = new String[32];
                            paramsString[0] = "1";
                            paramsString[1] = tempHoldCode;        // Hold Code 생성하여 저장
                            paramsString[2] = GlobalMemberValues.STORE_INDEX;
                            paramsString[3] = GlobalMemberValues.STATION_CODE;
                            paramsString[4] = insMidx;
                            paramsString[5] = insIdx;
                            paramsString[6] = insServiceName;
                            paramsString[7] = insStrFileName;
                            paramsString[8] = insStrFilePath;
                            paramsString[9] = insServicePrice;
                            paramsString[10] = insSalePrice;
                            paramsString[11] = insSaleStart;
                            paramsString[12] = insSaleEnd;
                            paramsString[13] = insCommissionratio;
                            paramsString[14] = insCommissionType;
                            paramsString[15] = insPointRatio;
                            paramsString[16] = insPositionNo;
                            paramsString[17] = insSetMenuYN;
                            paramsString[18] = tempCustomerId;
                            paramsString[19] = tempCustomerName;
                            paramsString[20] = tempCustomerPhone;
                            paramsString[21] = "0";        // saveType
                            paramsString[22] = insEmpIdx;
                            paramsString[23] = insEmpName;
                            paramsString[24] = "N";
                            paramsString[25] = insCategoryName;
                            paramsString[26] = "";
                            paramsString[27] = "";
                            paramsString[28] = "";
                            paramsString[29] = insCategoryColorValue;
                            paramsString[30] = "";
                            paramsString[31] = tempRcode;

                            // common gratuity 관련
                            GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                            maxIdxAfterDbInsert = MainMiddleService.insertTempSaleCart(paramsString);

                            // common gratuity 관련
                            GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                        }
                    }
                }
                MainMiddleService.mHoldCode = tempHoldCode;

                GlobalMemberValues.logWrite("getReservationInformationMethod", "예약손님 UID : " + tempCustomerId + "\n");

                // 고객정보 배치
                if (!GlobalMemberValues.isStrEmpty(tempCustomerId)
                        && !GlobalMemberValues.isStrEmpty(strReservedInfoGroup[1])) {

                    String strReservedMemberInfo = strReservedInfoGroup[1];
                    GlobalMemberValues.logWrite("getReservationInformationMethod", "예약손님 정보 : " + strReservedMemberInfo + "\n");

                    Vector<String> strInsUpdDelQueryVec = new Vector<String>();
                    String insUpdDelQuery = "";

                    String[] arrMemeberInfo = strReservedMemberInfo.split(GlobalMemberValues.STRSPLITTER2);
                    String newName = arrMemeberInfo[0];
                    String newUid = arrMemeberInfo[1];
                    String newPassword = arrMemeberInfo[2];
                    String newGender = arrMemeberInfo[3];
                    String newEmail = arrMemeberInfo[4];
                    String newAddr1 = arrMemeberInfo[5];
                    String newAddr2 = arrMemeberInfo[6];
                    String newCity = arrMemeberInfo[7];
                    String newState = arrMemeberInfo[8];
                    String newZip = arrMemeberInfo[9];
                    String newBYear = arrMemeberInfo[10];
                    String newBMonth = arrMemeberInfo[11];
                    String newBDay = arrMemeberInfo[12];

                    // 먼저 신규회원인지 체크한후 신규회원일 경우 안드로이드 포스에 저장한다.
                    int newMemCount1 = GlobalMemberValues.getIntAtString(
                            dbInit.dbExecuteReadReturnString(" select count(uid) from member1 where uid = '" + newUid + "' ")
                    );
                    int newMemCount2 = GlobalMemberValues.getIntAtString(
                            dbInit.dbExecuteReadReturnString(" select count(uid) from member2 where uid = '" + newUid + "' ")
                    );
                    int newSalonMemCount = GlobalMemberValues.getIntAtString(
                            dbInit.dbExecuteReadReturnString(" select count(uid) from salon_member where uid = '" + newUid + "' ")
                    );

                    if (newMemCount1 == 0) {
                        // member1 테이블 저장
                        insUpdDelQuery = "insert into member1 (uid, name, password, delyn, gender, grade, emailreceiveyn) values (" +
                                " '" + newUid + "', " +
                                " '" + newName + "', " +
                                " '" + newPassword + "', " +
                                " 'n', " +
                                " '" + newGender + "', " +
                                " '1', " +
                                " 'Y' " +
                                " ) ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    } else {
                        // member1 테이블 수정
                        insUpdDelQuery = "update member1 set " +
                                " name = '" + newName + "', " +
                                " password = '" + newPassword + "', " +
                                " delyn = 'n', " +
                                " gender = '" + newGender + "' " +
                                " where uid = '" + newUid + "' ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    }

                    // member2 테이블 저장
                    if (newMemCount2 == 0) {
                        insUpdDelQuery = "insert into member2 (uid, addr1, addr2, state, city, zip, country, byear, bmonth, bday, phone, mobile, email) values (" +
                                " '" + newUid + "', " +
                                " '" + newAddr1 + "', " +
                                " '" + newAddr2 + "', " +
                                " '" + newState + "', " +
                                " '" + newCity + "', " +
                                " '" + newZip + "', " +
                                " 'United States', " +
                                " '" + newBYear + "', " +
                                " '" + newBMonth + "', " +
                                " '" + newBDay + "', " +
                                " '" + newUid + "', " +
                                " '" + newUid + "', " +
                                " '" + newEmail + "' " +
                                " ) ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    } else {
                        insUpdDelQuery = "update member2 set " +
                                " addr1 = '" + newAddr1 + "', " +
                                " addr2 = '" + newAddr2 + "', " +
                                " state = '" + newState + "', " +
                                " city = '" + newCity + "', " +
                                " zip = '" + newZip + "', " +
                                " byear = '" + newBYear + "', " +
                                " bmonth = '" + newBMonth + "', " +
                                " bday = '" + newBDay + "', " +
                                " phone = '" + newUid + "', " +
                                " mobile = '" + newUid + "', " +
                                " email = '" + newEmail + "' " +
                                " where uid = '" + newUid + "' ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    }

                    // salon_member 테이블 저장
                    if (newSalonMemCount == 0) {
                        insUpdDelQuery = "insert into salon_member (uid, scode, sidx) values (" +
                                " '" + newUid + "', " +
                                " '" + GlobalMemberValues.SALON_CODE + "', " +
                                " '" + GlobalMemberValues.STORE_INDEX + "' " +
                                " ) ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    } else {
                        insUpdDelQuery = "update salon_member set " +
                                " scode = '" + GlobalMemberValues.SALON_CODE + "', " +
                                " sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                                " where uid = '" + newUid + "' ";
                        strInsUpdDelQueryVec.addElement(insUpdDelQuery);
                    }

                    for (String tempQuery : strInsUpdDelQueryVec) {
                        GlobalMemberValues.logWrite("ReservationWebPageLog", "query : " + tempQuery + "\n");
                    }

                    String returnResult = "";
                    // 트랜잭션으로 DB 처리한다.
                    returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsUpdDelQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.logWrite("ReservationWebPageLog", "Database Error (Customer data)" + "\n");
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error (Customer data)", "Close");
                        return;
                    } else {
                        GlobalMemberValues.logWrite("ReservationWebPageLog", "완료" + "\n");
                        Recall.setCustomerInfoAtOutClass(newUid, dbInit);
                    }
                }
            }
        }
    }

    public void setEmployeeInfo(String paramEmpIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramEmpIdx)) {
            Cursor empCursor;
            String strEmpQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                    " a.pos_emp_code, a.eid, a.pwd, a.eLevel, a.sidx, a.commissionratio " +
                    " from salon_storeemployee a" +
                    " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                    " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                    " and idx = '" + paramEmpIdx + "' ";

            String tempCommissionratio = "0";
            empCursor = dbInit.dbExecuteRead(strEmpQuery);
            if (empCursor.getCount() > 0 && empCursor.moveToFirst()) {
                tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(12), 1);
                if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                    tempCommissionratio = "0";
                }
                GlobalMemberValues.GLOBAL_EMPLOYEEINFO = new TemporaryEmployeeInfo(
                        paramEmpIdx,                                                                    // emp idx
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(1), 1),            // emp name
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(2), 1),            // emp age
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(3), 1),            // emp phone
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(4), 1),            // emp email
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(5), 1),            // emp photo
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(6), 1),            // emp wdate
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(7), 1),            // emp poscode
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(8), 1),            // emp id
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(9), 1),            // emp pwd
                        GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(10), 1),           // emp level
                        tempCommissionratio                                                             // emp commissionratio
                );
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText(GlobalMemberValues.getDBTextAfterChecked(empCursor.getString(1), 1));
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setVisibility(View.VISIBLE);

            }
        }

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(ReservationWebPage.this, msg, Toast.LENGTH_SHORT).show();
                    //GlobalMemberValues.displayDialog(context, "Inforamtion", msg, "Close");
                    mSelectedReservationServiceIdxs = "";
                    getReservationButton.setVisibility(View.INVISIBLE);
                    getReservationButtonVisible = false;
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        mSelectedReservationServiceIdxs = msg;
                        getReservationButton.setVisibility(View.VISIBLE);
                        getReservationButtonVisible = true;
                    }
                }
            });
        }
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            GlobalMemberValues.logWrite("ReservationCurrentUrl", "mCurrentUrl : " + url + "\n");
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
                GlobalMemberValues.logWrite("ReservationCurrentUrl", "mCurrentUrl : " + mCurrentUrl + "\n");
            }
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            GlobalMemberValues.logWrite("ReservationCurrentUrl", "History: " + url );
            super.doUpdateVisitedHistory(view, url, isReload);

            // frameset 으로 되어 있는 구조에서는 onPageStarted 가 작동하지 않는다.. ------
            // 해서 doUpdateVisitedHistory 메소드에 아래 로딩바를 구현...  ---------------
            if (!dialog.isShowing()) {
                dialog = new ProgressDialog(mActivity);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Reservation Back Office");
                dialog.setMessage("Page Loading...");
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
            // -----------------------------------------------------------------------

            String urlString = url;

            if (urlString.indexOf("step_viewReservation") == -1) {
                if (getReservationButtonVisible) {
                    if (urlString.indexOf("unverified_list") == -1) {
                        getReservationButton.setVisibility(View.INVISIBLE);
                        getReservationButtonVisible = false;
                    } else {
                        getReservationButton.setVisibility(View.VISIBLE);
                        getReservationButtonVisible = true;
                    }
                }
            } else {
                getReservationButton.setVisibility(View.VISIBLE);
                getReservationButtonVisible = true;
            }
            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");

        }

        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);

            GlobalMemberValues.logWrite("nowURLAddressIndexOf", "onPageStarted - url : " + url.toString() + "\n");

            //GlobalMemberValues.logWrite("webcloudurl", "url.indexOf(main.asp) : " + url.indexOf("main.asp") + "\n");
            //GlobalMemberValues.logWrite("webcloudurl", "여기실행...\n");
            dialog = new ProgressDialog(mActivity);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Reservation Back Office");
            dialog.setMessage("Page Loading...");
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
            /**
            if (getReservationButtonViewFlag == 0) {
                String urlString = reservationWebView.getUrl().toString();
                if (urlString.indexOf("step_viewReservation") == -1) {
                    getReservationButton.setVisibility(View.INVISIBLE);
                } else {
                    getReservationButton.setVisibility(View.VISIBLE);
                }
                //GlobalMemberValues.displayDialog(context, "Warninig", urlString, "Close");
                GlobalMemberValues.logWrite("nowURLAddressIndexOf", "url : " + url + "\n");
                GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
            }
             **/
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

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

            /**
            if (getReservationButtonViewFlag == 0) {
                String urlString = reservationWebView.getUrl().toString();
                if (urlString.indexOf("step_viewReservation") == -1) {
                    getReservationButton.setVisibility(View.INVISIBLE);
                } else {
                    getReservationButton.setVisibility(View.VISIBLE);
                }
                GlobalMemberValues.logWrite("nowURLAddressIndexOf", "indexof count : " + urlString.indexOf("step_viewReservation") + "\n");
            }
             **/
        }
    }

    @Override
    //Back 버튼 클릭시 뒤로가기 추가 뒤로가기 더이상 없으면 종료
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && reservationWebView.canGoBack()) {
            reservationWebView.goBack();
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
        reservationWebView.saveState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        reservationWebView.restoreState(savedInstanceState);
    }
    // -------------------------------------------------------------------
}
