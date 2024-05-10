package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;

import com.clover.connector.sdk.v3.DisplayConnector;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.printer.Category;
import com.clover.sdk.v1.printer.Printer;
import com.clover.sdk.v1.printer.PrinterConnector;
import com.clover.sdk.v1.printer.job.ImagePrintJob;
import com.clover.sdk.v3.order.DisplayLineItem;
import com.clover.sdk.v3.scanner.BarcodeScanner;
import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.epson.epos2.Epos2Exception;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.StarPrintStart;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pax.poslink.peripheries.POSLinkCashDrawer;
import com.pax.poslink.peripheries.POSLinkPrinter;
import com.pax.poslink.peripheries.POSLinkScanner;
import com.pax.poslink.peripheries.ProcessResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.example.jjbfather.jjjqsrt2go2gorestkakaovx.SettingsSystemReset.initSalesDataByOut;

// 구글 플레이 서비스 구글플레이서비스 (Google Play Service)
//import com.google.firebase.iid.FirebaseInstanceId;


public class GlobalMemberValues {
    // 데이터 자동 백업 초기화 기준 용량 mb
    public static int init_capacity_db = 50;
    // 데이터 Cash out 진행시 DB Compact 관련 Alert 에서 No 눌렀을시 EndofDay 에서 Alert 창이 나오지 않도록 하는 boolean
    public static boolean b_no_dbcompact = false;

    /** 제품명 ****************************************************/
    public static String ANDROIDPOSNAME = "RMultiT";
    /*************************************************************/

    /** 제품타입 ****************************************************/
    public static String DEVICEPRODUCTTYPE = "";
    /*************************************************************/

    /** Company 타입 *********************************************/
    public static boolean IS_COM_FRANCHISE = false;
    public static boolean IS_COM_CHAINSTORE = false;
    /*************************************************************/


    // Cloud Server URL - Basic (클라우드 URL 기본값)

    /** 2go2go 관련 *************************************************************************************/
    // Store Server ---------------------------------------------------------------------------
    // real
    public static String DATABASE_NAME = "JJJQSRDBMULTI_VX";           // DATABASE 명
    public static String mssql_pw = "DhksGkDP@02)";                       // DATABASE 비번

    // 모바일 host
    public static String MOBILE_HOST = "vxgolfm";

    // Domain 정보
    public static String CLOUD_HOST = "kakaovx";

    public static String CLOUD_FTP_IP = "110.234.18.134";
    public static String CLOUD_SERVER_URL_BASIC = "https://" + CLOUD_HOST + ".2go2go.com/";
    public static String CLOUD_SERVER_URL = "https://" + CLOUD_HOST + ".2go2go.com/";
    public static String CLOUD_SERVER_URL_FORHTTPS = "https://" + CLOUD_HOST + ".2go2go.com/";

    // FTP 설정값
    public static String M_FTPIP = CLOUD_FTP_IP;
    public static int M_FTPPORT = 5253;
    public static String M_FTPID = "nzsalondbftp";
    public static String M_FTPPWD = "wndhkswngkwndP@01^";
    public static String M_FTPENCODING = "UTF-8";
    public static String M_FTPBASCIDIR = "";       // FTP 기본 디렉토리
    //------------------------------------------------------------------------------------------------

//    // Test Server ---------------------------------------------------------------------------
//    // test
//    public static String DATABASE_NAME = "JJJQSRDBMULTI_VX";             // DATABASE 명
//    public static String mssql_pw = "DhksGkDP@02)";                           // DATABASE 비번
//
//    // 모바일 host
//    public static String MOBILE_HOST = "restaurantm";
//
//    // Domain 정보
//    public static String CLOUD_HOST = "rcloud";
//    public static String CLOUD_FTP_IP = "110.234.18.134";
//    public static String CLOUD_SERVER_URL_BASIC = "https://" + CLOUD_HOST + ".2go2go.com/";
//    public static String CLOUD_SERVER_URL = "https://" + CLOUD_HOST + ".2go2go.com/";
//    public static String CLOUD_SERVER_URL_FORHTTPS = "https://" + CLOUD_HOST + ".2go2go.com/";
//
//    // FTP 설정값
//    public static String M_FTPIP = CLOUD_FTP_IP;
//    public static int M_FTPPORT = 5252;
//    public static String M_FTPID = "nzsalondbftp";
//    public static String M_FTPPWD = "wndhkswngkwndP@01^";
//    public static String M_FTPENCODING = "UTF-8";
//    public static String M_FTPBASCIDIR = "";       // FTP 기본 디렉토리
//    //------------------------------------------------------------------------------------------------
    /**************************************************************************************************/

    /** mssql 관련 변수 ********************************************/
    // MSSQL 관련 설정값
    public static String mssql_useyn = "Y";
    public static String mssql_ip = "0.0.0.0";
    public static String mssql_db = GlobalMemberValues.DATABASE_NAME;
    public static String mssql_id = "wanhayedb";
    public static String mssql_sync = "N";
    /*************************************************************/


    public static String datadownloadingyn = "N";

    /** 카드결제 시뮬레이션 관련 정보 ******************************/
    // Y : 카드 디바이스가 연결안된 테스트버전     N : 카드 디바이스가 연결된 실제버전
    public static String CARD_DEVICE_TESTVERSION_YN = "N";

    public static String PAXCARDYN = "Y";
    /*************************************************************/

    /** 데모버전 스테이션 코드 정보 ******************************/
    public static String DEMO_STATION_CODE = "ST01GQFGG04436531";
    /*************************************************************/

    /** App Instance ID ******************************************/
    public static String APP_INSTANCE_ID = "";
    /*************************************************************/

    /** Push 팝업 카운트 ******************************************/
    public static int PUSH_POPUP_COUNT = 0;
    /*************************************************************/

    /** Store 정보 ***********************************************/
    // Salon 아이디 (sid)
    public static String SALON_SID= "";
    // Salon 코드 (scode)
    public static String SALON_CODE = "";
    // Store 인덱스 (sidx)
    public static String STORE_INDEX = "";
    // Station 코드 (stcode)
    public static String STATION_CODE = "";
    // Station 이름 (stname)
    public static String STATION_NAME = "";
    // 시리얼 번호 (SerialNumber)
    public static String SERIAL_NUMBER = "";

    // Salon Name / LOGO
    public static String SALON_NAME = "";
    public static String SALON_LOGOIMAGE = "";
    // Store Tax
    public static double STORE_SERVICE_TAX = 0.0;
    public static double STORE_PRODUCT_TAX = 0.0;
    public static double STORE_FOOD_TAX1 = 0.0;
    public static double STORE_FOOD_TAX2 = 0.0;
    public static double STORE_FOOD_TAX3 = 0.0;
    // Store POS Admin 비밀번호
    public static String STORE_ADMIN_PWD = "";
    // 스토어 최대 카테고리 갯수
    public static int STOREMAXCATEGORYSU = 0;
    // 스토어 최대 서비스 갯수
    public static int STOREMAXSERVICESU = 0;
    // 스토어 최대 기프트카드 상품갯수
    public static int STOREMAXGIFTCARDSU = 0;
    // 리워드 샐러드 사용유무 (Y : 사용      N : 미사용)
    public static String  REWARDSALADUSEYN = "N";
    public static double REWARDSALADRATIO = 0.0;

    // Quick Sale 커미션 관련
    public static double QUICKSALE_COMMISSIONRATIO = 0.0;
    // Quick Sale 커미션 관련
    public static String QUICKSALE_COMMISSIONRATIOTYPE = "%";
    // Quick Sale 포인트 관련
    public static Double QUICKSALE_POINTRATIO = 0.0;

    // Product 커미션 관련
    public static double PRODUCT_COMMISSIONRATIO = 0.0;
    // Product 커미션 관련
    public static String PRODUCT_COMMISSIONRATIOTYPE = "%";
    // Product 포인트 관련
    public static Double PRODUCT_POINTRATIO = 0.0;

    // Giftcard 커미션 관련
    public static double GIFTCARD_COMMISSIONRATIO = 0.0;
    // Giftcard 커미션 관련
    public static String GIFTCARD_COMMISSIONRATIOTYPE = "%";

    // payment Giftcard 사용시 남은 금액 저장
    public static double GIFTCARD_AFTER_BALANCE = 0.0;
    // payment Giftcard 사용시 카드 번호 저장
    public static String GIFTCARD_USE_NUMBER = "";

    // Customer Point 사용시 남은 금액
    public static double POINT_AFTER_BALANCE = 0.0;
    // Customer Point 사용시 고객 ID
    public static String POINT_USED_CUSTOMER_NAME = "";
    // Customer Earned Point
    public static double POINT_EARNED = 0.0;

    // Merchant 영수증만 프린트 할지 여부
    public static String ONLY_MERCHANTRECEIPT_PRINT = "N";

    // 키친프린터 클라우드 프린팅 여부
    public static String CLOUDKITCHENPRINTING = "N";

    // Receipt Printing 타입 (Customer, Merchant)
    public static String RECEIPTPRINTTYPE = "";

    // 주방프린트가 진행된 주문의 Salescode
    public static String SALESCODEPRINTEDINKITCHEN = "";

    // 고객 검색 Type
    public static String CUSTOMERSEARCHSPINNER = "All";
    /*************************************************************/

    /** Employee Login 정보 **************************************/
    public static String LOGIN_EMPLOYEE_ID = "";
    /*************************************************************/

    // Server Login 정보
    public static String SERVER_IDX = "";
    public static String SERVER_ID = "";
    public static String SERVER_NAME = "";

    /** 전자서명 이미지 저장 폴더 ***********************************/
    //public static String SIGNEDIMAGE_FOLDER = Environment.DIRECTORY_PICTURES + "/signedimg";
    public static String SIGNEDIMAGE_FOLDER = Environment.DIRECTORY_PICTURES;
    /*************************************************************/

    /** 세일후 백업인지 여부 **************************************/
    public static String BACKUPAFTERSALE = "N";
    /*************************************************************/

    /** 안드로이드 포스 시스템 정보 ****************************************************************************/
    // 앱 업데이트 방식
    static int APPUPDATETYPE = 0;        // 0 : FTP 업데이트       1 : 플레이스토어

    // 앱 오픈 횟수 (오픈 횟수에 따라 데이터 다운로드 실행여부 결정됨)
    public static int APPOPENCOUNT = 0;

    // 스플래쉬 액티비티 오픈여부를 위한 값
    // 0 일 경우에만 띄움
    // MainActivity 에서 한번 띄울 때 값을 1 증가시키고
    // 앱을 종료할 때 값을 0 으로 초기화
    static int SPLASHCOUNT = 0;

    // 클라우드에서 테이블을 삭제후 등록할 것인지 여부 (테이블이 수정되었을 경우에만 0 으로 한다!!!
    // 0 : 삭제후 등록            1 : 삭제하지 않고 등록
    static int INSERTDATAAFTERDELETE = 0;

    // 기존 테이블 삭제하고, 새로 테이블을 생성할 때 타입
    // 0 : 전체테이블 삭제                      (배열명 : dbTableNameGroup)
    // 1 : 클라우드 관련 테이블만 삭제할 경우     (배열명 : dbTableNameGroupForDrop)
    static int DROPTABLETYPE = 0;

    // 직원(Employee)TableLayout 에서 보여질 항목 최대수
    public static int EmployeeItem_Count_At_TableLayout = 100;

    // 해상도
    public static int TABLE_WIDTH;
    public static int TABLE_HEIGHT;

    // 날짜관련 변수 - 현재일
    public static String STR_NOW_DATE
            = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

    // 시간관련 변수 - 현재시간 (H:M)
    public static String STR_NOW_TIME
            = DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet();

    // 메인 Sale Cart 리스트 폰트 색상
    //  1. 서비스 색상
    public static String STR_MAINSALECART_TEXTCOLOR_SERVICE = "#3e3d42";
    //  2. 상품 색상
    public static String STR_MAINSALECART_TEXTCOLOR_PRODUCT = "#2676d7";
    //  3. 기프트카드 색상
    public static String STR_MAINSALECART_TEXTCOLOR_GIFTCARD = "#6a9728";
    //  4. Discount / Extra 색상
    public static String STR_MAINSALECART_TEXTCOLOR_DISCOUNT = "#c11c45";

    // 날짜 스타일
    public static String STR_DATESTYLE = "MM-dd-yyyy";

    // 날짜 스타일
    public static String STR_DATESTYLE_KOR = "yyyy-MM-dd";

    // 액티비티 화면 비율 값
    // 0 : 가로       1 : 역가로
    public static int PORTRAITORLANDSCAPE = 0;

    // 인터넷 연결 상태
    public static int GLOBALNETWORKSTATUS = 0;

    // 다운로드 하는 페이지 위치값
    public static String MSYNCDATATYPE = "MAIN";

    // 저울 사용여부
    public static boolean isScaleUSE = false;

    // 저울 무게 단위 .0 true .00 flase
    public static boolean is_decimalpoint_twice = false;

    // 카드결제시 Tip 을 줬는지 여부
    public static boolean ispaidtip = false;

    // label printer 출력했는지 여부
    public static boolean is_printed_label = false;

    // push sound
    public static boolean isSoundContinue = true;


    // Cloud URL
    public static String CLOUD_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "posweb.asp?stcode=";
    // Cloud URL
    public static String CLOUD_WEB_URL_NOTOP = GlobalMemberValues.CLOUD_SERVER_URL + "login_result_pos.asp?stcode=";
    // Cloud Logout URL
    public static String CLOUD_WEB_LOGOUT_URL = GlobalMemberValues.CLOUD_SERVER_URL + "posweb_logout.asp?stcode=";
    // Reservation URL
    public static String RESERVATION_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "reservation/index.asp?";
    // Clock In-Out URL
    // public static String CLOCKINOUT_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist.asp?";
    public static String CLOCKINOUT_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist_test.asp?";
    // Clock In-Out URL2
    public static String CLOCKINOUT_WEB_URL2 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert/clockinout_ok.asp?";
    // Clock In-Out URL3
    public static String CLOCKINOUT_WEB_URL3 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert_edit/edit_ok.asp?";
    // Clock In-Out URL4
    public static String CLOCKINOUT_WEB_URL4 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert_edit/delete_ok.asp?";
    // Clock In-Out URL5
    // public static String CLOCKINOUT_WEB_URL5 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist.asp?";
    public static String CLOCKINOUT_WEB_URL5 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist_test.asp?";
    // API - 연결문자열
    public static String API_WEB = GlobalMemberValues.CLOUD_SERVER_URL + "API/";
    // API - API 연동 웹페이지
    public static String API_WEB_URL = GlobalMemberValues.API_WEB + "API_DOWNLOAD_ForAndroid.asp";
    // API - 온라인 주문데이터 리스트
    public static String API_WEBORDER_URL2 = GlobalMemberValues.API_WEB + "API_OrdersList_ForAndroid.asp";
    // API - 온라인 주문데이터
    public static String API_WEBORDER_URL = GlobalMemberValues.API_WEB + "API_Orders_ForAndroid.asp";

    // jihun add 190913
    public static boolean b_one_run_thread = false;
    private static final long MIN_CLICK_INTERVAL=2000;
    private static long mLastClickTime;
    static boolean b_socket_connected = false;

    // 프린터 종류
    public static String[] PRINTERGROUP = {
//            "No Printer","PosBank", "Elo", "Clover Station", "Clover Mini", "PAX", "Giant-100", "Epson-T88"
            "No Printer","Elo","PAX","Epson-T88", "STAR"
    };
    public static String[] MASTER_PRINTERGROUP = {
//            "No Printer","PosBank", "Elo", "Clover Station", "Clover Mini", "PAX", "Giant-100", "Epson-T88"
            "No Printer","Epson-T88", "STAR"
    };

    public static String[] PRINTERGROUPKITCHEN = {
//            "No Printer","PosBank", "Elo", "Clover Station", "Clover Mini", "PAX", "Giant-100", "Epson-T88"
            "No Printer","Elo","PAX","Epson-T88", "STAR"
    };

    public static String[] PRINTERGROUP_LABEL = {
            "No Printer","Epson-L90(40mm)","Epson-L90(58mm)"
//            "No Printer","PosBank", "Elo", "Clover Station", "Clover Mini", "PAX", "Giant-100", "Epson-T88"
    };

    // 프린터 언어 종류
    public static String[] PRINTLANGUAGE = {
            "English","English + Korean", "English + Japanese", "English + Chinese", "English + French", "English + Italian"
    };

    // 디바이스(Device) 종류
    public static String[] DEVICEGROUP = {
            "Tablet PC", "Elo", "Clover", "PAX", "POSBANK", "Sunmi"
    };

    //
    public static String s_str_serverSocketIP = "";
    public static int s_i_serverSocketPort = 0;

    // Payment Gateway 종류
    public static String[] PAYMENTGATEWAYGROUP = {
            "Ingenico ICT220",
            "PAX POSLink"
    };

    // Comm Type 종류
    public static String[] COMMTYPE = {
            "UART","TCP","BLUETOOTH","SSL","HTTP","HTTPS","USB","AIDL"
    };

    // 영수증 Text Type
    public static String[] RECEIPT_TEXT_TYPE = {
            "NORMAL","BOLD","ITALIC"
    };

    // 영수증 Text Size
    public static String[] RECEIPT_TEXT_SIZE = {
            "SMALL","MEDIUM","LARGE","X-LARGE","2X-LARGE"
    };

    // 버튼 이미지 사용여부
    public static int IMAGEBUTTONYN = 0;        // 0 : 사용        1 : 사용안함

    // 숫자패드 텍스트 크기 기본값
    public static int BASICNUMBERTEXTSIZE = 14;
    // 메인 하단 고객정보 및 버전정보 텍스트 크기 기본값
    public static int BASICMAINBOTTOMTEXTSIZE = 13;
    // 메인 리스트뷰 서비스 리스트 텍스트 크기 기본값
    public static int BASICITEMLISTTEXTSIZE = 18;
    // 메인 리스트뷰 서비스 리스트 텍스트 크기 기본값2
    public static int BASICITEMLISTTEXTSIZE2 = 16;
    // 메인 사이드 메뉴 우측 버튼 Text 크기
    public static int BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE = 16;
    // 메인 상단 카테고리 텍스트 크기 기본값
    public static int BASICCATEGORYNAMETEXTSIZE = 14;
    // 메인 중앙 서비스 텍스트 크기 기본값
    public static float BASICSERVICENAMETEXTSIZE = 14;
    // 메인 상단 카테고리 텍스트 크기 기본값
    public static int BASICCATEGORYNAMETEXTSIZE_FORELO = 18;
    // 메인 중앙 서비스 텍스트 크기 기본값
    public static float BASICSERVICENAMETEXTSIZE_FORELO = 18;
    // 기프트카드 세일 텍스트 크기 기본값
    public static int BASICGIFTCARDSALETEXTSIZE = 12;
    // Customer Search 리스트뷰 텍스트 크기 기본값
    public static int BASICCUSTOMERSEARCHTEXTSIZE = 12;
    // Product List 리스트뷰 텍스트 크기 기본값
    public static int BASICPRODUCTLISTTEXTSIZE = 12;
    // Payment 리스트뷰 텍스트 크기 기본값
    public static int BASICPAYMENTEMPLOYEELISTTTEXTSIZE = 12;
    // Hold/Recall Customer Info 텍스트 크기 기본값
    public static int BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE = 12;
    // Hold/Recall 리스트 텍스트 크기 기본값
    public static int BASICHOLDRECALLLISTTTEXTSIZE = 12;
    // Sale History 리스트 텍스트 크기 기본값
    public static int BASICSALEHISTORYLISTTTEXTSIZE = 18;
    // Sale History Return Item 리스트 텍스트 크기 기본값
    public static int BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE = 14;
    // Clock In/Out 리스트 텍스트 크기 기본값
    public static int BASICCLOCKINOUTLISTTTEXTSIZE = 12;
    // Clock In/Out Employee 리스트 텍스트 크기 기본값
    public static int BASICCLOCKINOUTEMPLOYEELISTTTEXTSIZE = 12;
    // RadioButton 텍스트 기본값
    public static int BASICRADIOBUTTONTEXTSIZE = 12;
    // Switch 텍스트 기본값
    public static int BASICSWITCHTEXTSIZE = 12;
    // Other Payment 텍스트 크기 기본값
    public static int OTHERPAYMENTTEXTSIZE = 12;

    // 레이어 프린팅에서 modifier 앞에 몇칸 띄우는지
    public static int MODIFIERSPACESUONLAYOUTPRINTING = 10;

    // Push 푸시메시지를 위한 App Instance 를 API 로 클라우드에 전송여부
    public static int APPINSTANCEIDUP = 0;          // 0 : 미전송    1 : 전송완료

    // MainActivity recreate 하는 경우 아래 메소드 실행여부
    // 0 : 실행안함           1 : 실행함
    // MainMiddleService.clearListView(), GlobalMemberValues.setCustomerInfoInit(), GlobalMemberValues.setEmployeeInfoInit()
    public static int ITEMCANCELAPPLY = 0;

    public static boolean isProcessCreditCard = false;

    // Gmail 계정정보
    public static String GMAILACCOUNT = "";         // @gmail.com 포함
    public static String GMAILID = "";
    public static String GMAILPWD = "";

    // 클라우드 데이터 다운로드시 로딩 인텐트 보여줄지 여부
    public static String LOADINGINTENTUSE = "1";         // 0 : 미사용(메인 프로그래스바 오픈)      1 : 로딩 인텐트 사용

    // 이미지 다운로드 완료후 기다리는 시간
    // (데이터 다운로드가 완료되도 이미지 다운로드가 끝날 때까지는 시간이 조금더 걸리므로..)
    public static int WAITSECAFTERDOWNLOAD = 5;

    // 서비스 중지전 기다리는 시간
    // (중지할 때 업로드 서비스가 실행되고 있을 수 있으므로..)
    public static int WAITTOSTOPSERVICE = 3;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS = false;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS2 = false;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS3 = false;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS4 = false;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS5 = false;

    // 프린터 연결상태
    public static boolean PRINTERCONNECTEDSTATUS6 = false;

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP = "";

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP2 = "";

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP3 = "";

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP4 = "";

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP5 = "";

    // 연결된 네트워크 프린터 IP
    public static String NETWORKPRINTERIP6 = "";

    // Master Printer IP
    public static String NETWORKPRINTERIP_MASTER = "";

    // 스테이션 IP
    public static String STATION_IP = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT2 = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT3 = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT4 = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT5 = "";

    // 연결된 네트워크 프린터 포트
    public static String NETWORKPRINTERPORT6 = "";


    // 아이템(메뉴) 선택시 애니메이션 여부
    public static String ITEMADDANIMATIONYN = "N";


    // 메인 Recreate 인지 여부
    public static String MAINRECREATEYN = "N";

    // 버튼형 옵션에서 한줄에 보여질 버튼 갯수
    public static int ROWITEMS_ONBUTTONOPTION = 5;

    // 다른 직원이 주문을 추가, 삭제, 수정할 때 비밀번호를 물어볼지 여부
    public static String mPasswordYN_inMod = "Y";

    // to go 주문시 타입 (C : Call In    W : Walk In)
    public static String mToGoType = "";


    //////// Service 시작, 종료 관련 --------------------------------------------

    // POS 세일 JSON
    // POS 세일 JSON 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_SALEJSON = null;
    // Service 를 실행시킨 Activity - POS 세일 JSON 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_SALEJSON = null;

    // Endofday Json 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_ENDOFDAYJSON = null;
    // Service 를 실행시킨 Activity - Endofday Json 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_ENDOFDAYJSON = null;

    // Cashout Json 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_CASHOUTJSON = null;
    // Service 를 실행시킨 Activity - Cashout Json 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_CASHOUTJSON = null;

    // 장바구니데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_CART = null;
    // Service 를 실행시킨 Activity - 장바구니데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_CART = null;

    // 장바구니데이터 삭제 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_CARTDEL = null;
    // Service 를 실행시킨 Activity - 장바구니데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_CARTDEL = null;

    // 세일데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_SALE = null;
    // Service 를 실행시킨 Activity - 세일데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_SALE = null;

    // 팁 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_TIP = null;
    // Service 를 실행시킨 Activity - 팁 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_TIP = null;

    // MemberMileage 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY = null;
    // MemberMileage 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY = null;

    // GiftCard Number History 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY = null;
    // GiftCard Number History 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY = null;

    // 신규 웹오더 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWWEBORDER = null;
    // 신규 웹오더 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER = null;

    // 신규 테이블페이 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWTABLEPAY = null;
    // 신규 테이블페이 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY = null;

    // 01172024
    // 신규 테이블오더 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWTABLEORDER = null;
    // 신규 테이블오더 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWTABLEORDER = null;

    // 신규 Curbside 오더 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_CURSIDE = null;
    // 신규 Curbside 오더 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_CURSIDE = null;

    // 신규 테이블 오더 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWSIDEORDER = null;
    // 신규 테이블 오더 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER = null;


    // 인터넷 체크 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_ONLINECHECK = null;
    // 인터넷 체크 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK = null;

    // 주방프린팅 데이터 관련 서비스 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWKICHENPRINTINGDATA = null;
    // 주방프린팅 데이터 관련 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWKICHENPRINTINGDATA = null;

    // Sale Done SMS 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_SALEDONESMS = null;
    // Sale Done SMS 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_SALEDONESMS = null;

    // 데이터베이스 백업관련 인텐트
    public static Intent CURRENTSERVICEINTENT_DBBACKUP = null;
    // 데이터베이스 백업관련
    public static Activity CURRENTACTIVITYOPENEDSERVICE_DBBACKUP = null;

    // Local Printing 관련 인텐트
    public static Intent CURRENTSERVICEINTENT_PRINTING = null;
    // Local Printing 관련
    public static Activity CURRENTACTIVITYOPENEDSERVICE_PRINTING = null;

    // Kitchen Printing 관련 인텐트
    public static Intent CURRENTSERVICEINTENT_PRINTING_KITCHEN = null;
    // Kitchen Printing 관련
    public static Activity CURRENTACTIVITYOPENEDSERVICE_PRINTING_KITCHEN  = null;


    // One Time Tip Adjustment 관련 인텐트
    public static Intent CURRENTSERVICEINTENT_ONETIMETIPADJUSTMENT = null;
    // One Time Tip Adjustment 관련
    public static Activity CURRENTACTIVITYOPENEDSERVICE_ONETIMETIPADJUSTMENT  = null;

    // 팁 데이터 전송관련 인텐트
    public static Intent CURRENTSERVICEINTENT_SAVEDATAINDB = null;
    // Service 를 실행시킨 Activity - 팁 데이터 전송
    public static Activity CURRENTACTIVITYOPENEDSERVICE_SAVEDATAINDB = null;


    // 다른 스테이션 주문체크 관련 인텐트
    public static Intent CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION = null;
    // 다른 스테이션 주문체크 관련 서비스
    public static Activity CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION = null;

    // 첫 주문시 true 재주문시 false
    public static boolean B_FIRSTORDER_YN = false;


    // Dual Display 지원여부
    public static boolean ISDUALDISPLAYPOSSIBLE = false;

    // 영수증에 $ 표시 여부
    public static boolean ISDISPLAYPRICEDOLLAR = false;

    // 업로드할 세일 receipt no.
    public static String RECEIPTNOFORUPLOAD = "";

    // -------------------------------------------------------------------

    /** 업로드 데이터 체크하기 위한 서비스관련 ********************************************/
    // Sale 관련
    public static Intent CURRENTSERVICEINTENT_1 = null;
    public static Activity CURRENTACTIVITYOPENEDSERVICE_1 = null;

    // Tip 관련
    public static Intent CURRENTSERVICEINTENT_2 = null;
    public static Activity CURRENTACTIVITYOPENEDSERVICE_2 = null;

    // Member Mileage (고객포인트) 관련
    public static Intent CURRENTSERVICEINTENT_3 = null;
    public static Activity CURRENTACTIVITYOPENEDSERVICE_3 = null;

    // Gift Card History 관련
    public static Intent CURRENTSERVICEINTENT_4 = null;
    public static Activity CURRENTACTIVITYOPENEDSERVICE_4 = null;

    /*********************************************************************************/

    // 데이터다운로드 DB 처리형태
    // 0 : 쿼리를 모아서 한번에 처리
    // 1 : 항목별로 다운로드받을 때 DB 에 처리
    public static int downloadDataInsUpdDelType = 1;

    // 메인 프레임 색상
    public static String MAINFRAMECOLOR = "#232531";

    // 프린트 언어
    public static Locale PRINTLOCALELANG = Locale.ENGLISH;

    // 서명이미지 삭제전 기다리는 시간
    public static int DELETEWAITING = 0;

    // 타이머 시작여부
    public static String TIMER_TIMEMENU_START_YN = "N";
    public static String TIMER_ONLINECHECK_START_YN = "N";
    public static String TIMER_ONLINEORDERSCHECK_START_YN = "N";
    public static String TIMER_KITCHENPRINTINGDATATOCLOUD_YN = "N";

    public static String TIMER_TABLEPAY_START_YN = "N";
    public static String TIMER_CURBSIDE_START_YN = "N";
    public static String TIMER_SIDEMENU_START_YN = "N";

    public static String TIMER_NEWCARTCHECKBYSTATION_START_YN = "N";
    /*******************************************************************************************************/

    // 05.31.2022 ---------------------------------------------------------------------
    // repay 관련 변수
    public static boolean isRepay = false;
    public static boolean isOpenPayment = false;
    public static String mTableIdx_byRepay = "";
    public static String mHoldCode_byRepay = "";
    public static int mPeopleCnt_byRepay = 0;

    public static boolean isRepay2 = false;

    // 04222023
    public static boolean isRepay3 = false;
    // --------------------------------------------------------------------------------

    // 클라우드 프린팅 사용시 키친 프린팅 데이터 업로드할지 여부
    public static boolean isUploadKitchenPrintingData = false;

    // SaleHistory, SaleHistory_web 에서 실행하는 리프린트의 경우
    public static String mReReceiptprintYN = "N";
    public static String mRekitchenprintYN = "N";

    // Online Order의 경우 Y
    public static String mOnlineOrder = "N";

    public static int mKitchenPrintedQty = 0;

    // 주방프린터 로딩창상황
    public static boolean mKitchenLoading = false;

    // Sale Done SMS 를 위한 salesCode 값 변수
    public static String mSalesCode_SALEDONESMS = "";


    // 주문취소 키친프린팅인지 여부
    public static String mCancelKitchenPrinting = "N";

    // cancel 버튼 클릭여부
    public static String mCancelBtnClickYN = "N";

    // QSR 버전일 경우 TABLE 메인 화면을 몇번 열었는지 숫자
    public static int mOpenTableMainCount = 0;

    /** Settings - System 값 ***********************************************/
    // Splash Use
    public static int GLOBAL_SPLASHUSE = 0;                         // 0 : 사용         1 : 사용안함
    // Download Data
    public static int GLOBAL_DOWNLOADDATA = 1;                      // 0 : 다운로드버튼 클릭시         1 : 앱 오픈시
    // Database Backup
    public static int GLOBAL_DATABASEBACKUP = 0;                    // 0 : 백업안함    1 : 텐더시   2 : 앱 종료시
    // CommissionratioType
    public static int GLOBAL_COMMISSIONRATIOTYPE = 1;               // 0 : 직원 커미션만 부여    1 : 직원 + 서비스메뉴 커미션 부여
    // departmentviewyn
    public static String GLOBAL_DEPARTMENTVIEWYN = "N";             // 직원선택부분에서 부서를 먼저 보여줄지 여부

    // discount, extra 처리하더라도 원가를 그대로 보여줄지 여부
    public static String GLOBAL_SHOWCOSTAFTERDCEXTRA = "N";           // Y : 원가대로 보여줌           N : 원가에 DC/EXTRA 한 것을 반영하여 보여줌

    // Database Backup 을 결제(세일즈)할때 하는 경우인지 여부
    public static boolean GLOBAL_DATABASEBACKUPINTENDER = false;

    // 카드결제가 완료되면 결제가 완료되는지 여부
    public static String GLOBAL_CARDISLAST = "N";

    // Push Type
    public static String GLOBAL_PUSHTYPE = "1";                     // 0 : 푸시안받음     1 : 구글 푸시         2 : 클라우드 푸시

    // 자동 영수증 프린팅 (Auto Receipt Printing)
    public static String GLOBAL_AUTORECEIPTPRINTING = "N";          // N : 자동영수증 프린팅 안됨          Y : 자동 영수증 프린팅됨

    // 기기가 Elo 인지 여부
    public static boolean isThisDeviceElo = false;

    // 기기가 Clover 인지 여부
    public static boolean isThisDeviceClover = false;

    // 기기가 POSBANK 인지 여부
    public static boolean isThisDevicePosbank = false;

    // Clover 바코드 스캔시 입력할 View
    public static String BARCODESCANVALUE_ONCLOVER = "";

    // Clover 클로버 프린팅 텍스트 font size, Color
    public static int PRINTINGFONTSIZE_ONCLOVER = 30;
    public static int PRINTINGPAPERSIZE_ONCLOVER = 384;
    public static int PRINTINGPAPERSIZE_ON_REPRINT = 510;
    public static boolean B_ON_REPRINT = false;

    public static int PRINTINGFONTSIZE_TABLESALEVIEW = 20;

    public static String PRINTINGFONTCOLOR_ONCLOVER = "#000000";

    public static float PRINTINGITMETITLE1_ONCLOVER = 1.0f;
    public static float PRINTINGITMETITLE2_ONCLOVER = 0.2f;
    public static float PRINTINGITMETITLE3_ONCLOVER = 0.5f;
    public static float PRINTINGITMETITLE4_ONCLOVER = 0.4f;

    // 카드결제전 인터넷 연결체크여부
    public static boolean ISCHECK_BEFORE_CARDPAY = true;

    // 카드결제시 Key In 가능여부
    public static boolean CARD_KEY_IN = false;

    // 카드 프로세싱 스텝
    public static int CARD_PROCESSING_STEP = 0;

    // 카드결제 Tip 처리시 프로세싱을 할지 여부 (true : 프로세싱    false : DB 저장후 batch 때 일괄처리)
    public static boolean CARD_TIP_PROCESSING = false;

    // 세일일시 변경 가능여부
    public static boolean ISSALEDATEMODIFY = false;
    // 설정된 세일일자 (기본값 : 현재일자)
    public static String SETTING_SALE_DATE
            = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

    // sale history 한 페이지당 데이터 수
    public static int PAGEDATACOUNNT_FORSALEHISTORY = 15;

    // Modifier Option 갯수
    public static int MODIFIER_OPTION_SU = 5;

    // Modifier 가격 보일지 여부
    public static boolean MODIFIER_PRICEVIEW = false;

    // 결제후 change 창 오픈완료여부
    public static boolean isPageLoadingFinished = false;

    // Scale 사용할 때 측량가능 저울 최소 무게값
    public static double SCALE_MINWEIGHT = 0;
    // Scale 사용할 때 측량가능 저울 최대 무게값
    public static double SCALE_MAXWEIGHT = 0;

    // Restaurant 관련 ------------------------------------------------
    public static boolean now_saletypeisrestaurant = false;
    public static boolean now_iskitchenprinting = true;
    public static boolean now_iskitchenprinting_after = true;
    public static int tablesalemain_open_cnt = 0;
    public static int tablesalemain_open_cnt2 = 0;

    public static String isKitchenReprinting = "N";
    // ----------------------------------------------------------------

    // 07.16.2022 -----------------------------------------------------
    // 카드결제시 잔액부족으로 결제안됐는지 여부
    public static boolean mNotPayYNOnCard = false;
    // 카드결제시 잔액부족일 때 메시지
    public static String mNotPayYNOnCardMsg = "";
    // ----------------------------------------------------------------

    // 직원이 바뀔경우 - 패스워드를 물어볼지 말지.
    public static String mPossibleYN_inMod = "Y";

    // 07.18.2022 - add pay for cash, card ----------------------------
    // 메시지
    public static String mAddPayMsgForCard = "";
    // ----------------------------------------------------------------

    /*************************************************************/

    // TableSaleMain 에 사용할 임시 ArrayList
    public static ArrayList<String> mArrListForTSM = null;

    // 선택한 테이블 idx 임시저장
    public static String mSelectedTableIdx = "";

    // 레스토랑일 경우 장바구니에서 메뉴 삭제시 임시로 담아 놓을 temp_salecart 의 idx (키친프린트용)
    public static String mDeletedSaleCartIdx = "";

    /** 다운로드 이미지 폴더 **************************************/
    public static String FOLDER_SERVICEIMAGE = "serviceimage";
    /*************************************************************/

    /** 공통 Text ************************************************/
    public static String DOWNLOAD_PROGRESS = "POS data is downloading from the Cloud Server...";
    /*************************************************************/

    /** 타임메뉴 Time Menu 관련 코드값 ******************************/
    public static String TIMEMENUUSEYN = "Y";                   // 타임메뉴 사용여부
    public static String TIMEMENUSET_AUTOOPEN = "N";            // 타임메뉴 설정창 자동오픈 여부
    public static String NOW_TIME_CODEVALUE = "a";              // 현재 시간대 코드값
    public static String SELECTED_TIME_CODEVALUE = "a";         // 현재 선택된 시간대 코드값
    public static boolean isOpenedTimeMenuPopup = false;        // 타임메뉴 시간대 선택팝업창 오픈여부
    public static boolean isEmployeeLoginPopup = false;         // 현재 페이지가 직원로그인 팝업창인지 여부
    public static int TIMEMENUCHECKTIME_MINUTES = 10;                   // 타임메뉴 체크 인터벌 시간
    /*************************************************************/

    /** Push 에서 키친프린트 여부 *********************************/
    public static String KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";
    /*************************************************************/

    /** 고객포인트(마일리지) 다운받을 고객아이디 **********************/
    public static String MMH_DOWNLOAD_UID = "";
    /*************************************************************/

    /** Download 다운로드시 화면재개 지연시간 *************************/
    public static String RESTARTSCREEN_DELYTIME = "0";
    /*************************************************************/

    // Pager Number Header Text
    public static String PAGERNUMBERHEADERTXT = "";

    // 유효한 스테이션인지 여부
    public static boolean ISPOSSIBLETHISSTATION = true;

    // 테이블보드의 우측 메뉴 보여졌는지 여부
    public static boolean isShowQuickMenusInTableBoard = false;


    // 05152023
    // 라벨 리프린트 여부
    public static boolean isLabelReprint = false;

    // 230817
    public static boolean is_billprint_in_payment = false;

    // bill 결제시 필요변수 -----------------------------------------------------------------------
    public static boolean isPaymentByBills = false;                     // bill 결제하는지 여부

    public static String mBill_Holdcode = "";

    public static String mBill_Idx = "";                                   // bill_list 테이블의 idx

    public static String mBillSplitType = "";                           // bill split type

    public static String mCartIdxsOnBillPay = "";                       // bill split type 이 0 일 경우 cart idx
    public static String mSelectedHoldCodeForBillPay = "";

    public static String mSelectedTableIdxOnBillPay = "";               // 선택한 table idx
    public static String mSelectedSubTableNumOnBillPay = "";            // 선택한 테이블의 sub num

    public static double mPayTotalAmountOnBill = 0.0;                   // bill 결제할 총 금액

    public static double mPayAmountOnBill = 0.0;                        // bill 결제하는 금액

    public static boolean isBillPayContinue = false;                    // bill pay 를 이어서 계속할지 여부

    public static String mSplit_transaction_id = "";

    // bill pay 를 계속 진행하게 하기 위한 변수
    public static String mHoldCodeForBillPay_on = "";
    public static String mTableIdxOnBillPay_on = "";
    public static String mSubTableNumOnBillPay_on = "";

    public static boolean mIsOnPaymentProcessForBillPay = false;

    public static String mCardSalesIdxForBillPay = "";      // 카드결제시 salon_sales_card 의 idx
    public static boolean mOnVoidForBillPay = false;      // Bill Merge Split Print 창에서 카드결제 void 여부
    // -----------------------------------------------------------------------------------------

    // 06.07.2022 ------------------------------
    public static boolean mOnVoidForPartial = false;
    // -----------------------------------------


    // 02172023
    // 테이블오더 Table Order 관련 ------------------------------------------------
    public static Boolean is_customerMain = false;
    public static Boolean is_myorderOpen = false;

    // Presentation ImageView, VideoView 를 포함하고 있는 LinearLayout 객체 선언
    public static LinearLayout GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN;

    // Presentation ImageView 객체 선언
    public static ImageView GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_IMAGEVIEW;

    // Presentation VideoView 객체 선언
    public static VideoView GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW;
    // --------------------------------------------------------------------------


    // 04292024
    // 레스토랑 포스를 QSR 로 사용여부 -----------------------------------------------
    public static boolean isQSRPOSonRestaurantPOS = true;
    // --------------------------------------------------------------------------



    // Common Gratuity 명
    public static String mCommonGratuityName = "Common Gratuity";

    // 메인 액티비티에서 Table Sale Main 을 오픈할 지 여부
    public static boolean isOpenTableSaleMain = false;
    // Hold 여부
    public static boolean isHold = false;

    // 카드 status 관련
    // 카드결제시 카드시도정보를 저장할지 여부
    public static boolean mCardPayStatusSave = true;

    /** Final 변수 ***********************************************/
    // 마스터 비밀번호
    public static final String MASTER_PWD = "111305";

    // API - Thread 시간
    public static final int API_THREAD_TIME = 1000;
    // API - 업로드 할 경우 기다리는 시간
    public static final int API_UPLOAD_THREAD_TIME = 3500;
    // API - 웹 DB 에서 XML 만드는 유형 (type)
    public static final String API_WEB_DB_XML_TYPE = "3";
    // Category Color
    public static String[] CATEGORYCOLORVALUE = {
            "#a0a0a0",
            "#f8b452",
            "#b06cd0",
            "#5c8cb4",
            "#a8bc58",
            "#8ababc",
            "#de7676",
            "#fc78c4"
    };
    // Category Color
    public static String[] CATEGORYCOLORVALUE_BG = {
            "#f1eeee",
            "#ffe8c7",
            "#eccffa",
            "#cbe4f9",
            "#f8fee1",
            "#e4f5f6",
            "#fee7e7",
            "#fee6f4"
    };
    // Cateogory nonclick / click xml
    /**
     public static int[] CATEGORYCOLORXMLVALUE = {
     R.drawable.button_selector_category1,
     R.drawable.button_selector_category2,
     R.drawable.button_selector_category3,
     R.drawable.button_selector_category4,
     R.drawable.button_selector_category5,
     R.drawable.button_selector_category6,
     R.drawable.button_selector_category7,
     R.drawable.button_selector_category8
     };
     **/
    public static int[] CATEGORYCOLORXMLVALUE = {
            R.drawable.ab_imagebutton_category1,
            R.drawable.ab_imagebutton_category2,
            R.drawable.ab_imagebutton_category3,
            R.drawable.ab_imagebutton_category4,
            R.drawable.ab_imagebutton_category5,
            R.drawable.ab_imagebutton_category6,
            R.drawable.ab_imagebutton_category7,
            R.drawable.ab_imagebutton_category8
    };

    // Lite 버전 관련
    public static int[] CATEGORYCOLORXMLVALUE_LITE = {
            R.drawable.ab_imagebutton_category1_lite,
            R.drawable.ab_imagebutton_category2_lite,
            R.drawable.ab_imagebutton_category3_lite,
            R.drawable.ab_imagebutton_category4_lite,
            R.drawable.ab_imagebutton_category5_lite,
            R.drawable.ab_imagebutton_category6_lite,
            R.drawable.ab_imagebutton_category7_lite,
            R.drawable.ab_imagebutton_category8_lite
    };
    // Cateogory nonclick / click xml ----- 배경이 이미지인 버튼일 경우 투명버튼으로..
    public static int[] CATEGORYCOLORTRANSXMLVALUE = {
            R.drawable.button_selector_category_trans1,
            R.drawable.button_selector_category_trans2,
            R.drawable.button_selector_category_trans3,
            R.drawable.button_selector_category_trans4,
            R.drawable.button_selector_category_trans5,
            R.drawable.button_selector_category_trans6,
            R.drawable.button_selector_category_trans7,
            R.drawable.button_selector_category_trans8
    };

    // merged table color
    public static String[] MERGEDTABLECOLOR = {
            "#f8b452",
            "#b06cd0",
            "#5c8cb4",
            "#a8bc58",
            "#8ababc",
            "#de7676",
            "#fc78c4",
            "#ffe8c7",
            "#eccffa",
            "#cbe4f9",
            "#f8fee1",
            "#e4f5f6",
            "#fee7e7",
            "#fee6f4"
    };


    // 카테고리 버튼 색상 기본값
    public static int CATEGORYCOLORXMLVALUE_DEFAULT = R.drawable.aa_images_menubg_ffffff;
    // 카테고리 버튼 색상 기본값 ----- 배경이 이미지인 버튼일 경우 투명버튼으로..
    public static int CATEGORYCOLORXMLVALUE_DEFAULT_TRANS = R.drawable.button_selector_category_default_trans;

    // Split 구분자 문자 - 반드시 역슬러시 두개를 넣어야 함!!!!!!!!!!!!!!!!!!
    public static final String STRSPLITTER1 = "\\|\\|";
    public static final String STRSPLITTER2 = "\\|\\|\\|";

    public static final String STRSPLITTER_ORDERITEM1 = "-WANHAYE-";        // 웹오더 아이템 구분선1
    public static final String STRSPLITTER_ORDERITEM2 = "-JJJ-";            // 웹오더 아이템 구분선2
    public static final String STRSPLITTER_ORDERITEM3 = "-ANNIETTASU-";     // 웹오더 아이템 구분선3

    // 카테고리 눌렸을 때 색상
    public static final String CATEGORY_NOCLICKBACKGROUNDCOLOR1 = "#ffffff";
    public static final String CATEGORY_ONCLICKBACKGROUNDCOLOR1 = "#db1e48";
    public static final String CATEGORY_NOCLICKTEXTCOLOR1 = "#3e3d42";
    public static final String CATEGORY_ONCLICKTEXTCOLOR1 = "#3e3d42";

    // 기프트카드 상품 눌렸을 때 색상
    public static final String GIFTCARD_NOCLICKBACKGROUNDCOLOR1 = "#ffffff";
    public static final String GIFTCARD_ONCLICKBACKGROUNDCOLOR1 = "#d0e1fb";
    public static final String GIFTCARD_NOCLICKTEXTCOLOR1 = "#3e3d42";
    public static final String GIFTCARD_ONCLICKTEXTCOLOR1 = "#0054d5";

    // SaleCart Discount / Extra 색상
    public static final String SALECART_DISCOUNTBACKGROUNDCOLOR = "#ba1507";
    public static final String SALECART_EXTRABACKGROUNDCOLOR = "#14428d";

    // 리스트뷰(ListView) 경계선색
    public static final String LISTVIEW_DIVIDEDLINE_COLOR = "#efefef";

    // 숫자번호버튼 텍스트 색
    public static String GLOBAL_NUMBERBUTTON_COLOR = "#ffffff";

    // 서브액티비티 구분값
    public static final int ACT_SUB_QTYEDITPOPUP = 0;                   // 수량(Qty) 변경 관련 팝업 액티비티

    // Payment 에서 결제수단 클릭했을 때 해당 LinearLayout 변경할 배경색
    public static final String PAYMENT_NOSELECTPAYMENTTYPE_BACKGROUNDCOLOR = "#ffffff";     // 선택전 색상
    public static final String PAYMENT_SELECTPAYMENTTYPE_BACKGROUNDCOLOR = "#204e77";       // 선택시 색상
    public static final String PAYMENT_SELECTEDPAYMENTTYPE_BACKGROUNDCOLOR = "#3589c8";       // 선택후 색상

    // Sale History Text 색상
    public static final String SALESHISTORYLIST_TEXTCOLOR = "#3a3a3a";
    // Sale History salon_sales LinearLayout 배경색
    public static final String[] SALEHISTORYSALONSALESBGCOLOR = {"#cbe3ef", "#d7d7d7"};
    // Sale History salon_sales_detail 리스트 배경색
    public static final String[] SALEHISTORYSALONSALEDETAILSBGCOLOR = {"#ffffff", "#ebebeb"};

    // Recall 리스트 배경색
    public static final String RECALLLISTBGCOLOR = "#3e3d42";
    // Recall 리스트 Text 색상
    public static final String RECALLLIST_TEXTCOLOR_CUSTOMER = "#0054d5";
    // Recall 리스트 Text 색상
    public static final String RECALLLIST_TEXTCOLOR = "#3e3d42";

    // Batch & Summary Text 색상
    public static final String BATCHSUMMARY_TEXTCOLOR = "#3e3d42";

    // 리스트뷰 리스트 텍스트 색상
    public static final String BASICCOMMONLISTVIEWTEXT = "#3e3e3e";

    // Return 리턴금액 Text 색상
    public static final String RETURNPRICEAMOUNT_TEXTCOLOR = "#e30924";

    // 듀얼디스플레이 광고 이미지 또는 동영상 저장 경루
    public static final File ADFILELOCALPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    // 프린팅 전용앱 패키지명
    public static final String PRINTINGAPPPACKAGENAME = "com.example.jjbfather.jjjprinter";


    // State / Province
    public static final String[][] STATEPROVINCE = {
            {"", "----- Select State -----"},
            {"AE", "Armed Forces - Europe"},
            {"AP", "Armed Forces - Pacific"},
            {"AL", "Alabama"},
            {"AK", "Alaska"},
            {"AZ", "Arizona"},
            {"AR", "Arkansas"},
            {"CA", "California"},
            {"CO", "Colorado"},
            {"CT", "Connecticut"},
            {"DE", "Delaware"},
            {"DC", "District of Columbia"},
            {"FL", "Florida"},
            {"GA", "Georgia"},
            {"HI", "Hawaii"},
            {"ID", "Idaho"},
            {"IL", "Illinois"},
            {"IN", "Indiana"},
            {"IA", "Iowa"},
            {"KS", "Kansas"},
            {"KY", "Kentucky"},
            {"LA", "Louisiana"},
            {"ME", "Maine"},
            {"MD", "Maryland"},
            {"MA", "Massachusetts"},
            {"MI", "Michigan"},
            {"MN", "Minnesota"},
            {"MS", "Mississippi"},
            {"MO", "Missouri"},
            {"MT", "Montana"},
            {"NE", "Nebraska"},
            {"NV", "Nevada"},
            {"NH", "New Hampshire"},
            {"NJ", "New Jersey"},
            {"NM", "New Mexico"},
            {"NY", "New York"},
            {"NC", "North Carolina"},
            {"ND", "North Dakota"},
            {"OH", "Ohio"},
            {"OK", "Oklahoma"},
            {"OR", "Oregon"},
            {"PA", "Pennsylvania"},
            {"PR", "Puerto Rico"},
            {"RI", "Rhode Island"},
            {"SC", "South Carolina"},
            {"SD", "South Dakota"},
            {"TN", "Tennessee"},
            {"TX", "Texas"},
            {"UT", "Utah"},
            {"VT", "Vermont"},
            {"VA", "Virginia"},
            {"WA", "Washington"},
            {"WV", "West Virginia"},
            {"WI", "Wisconsin"},
            {"WY", "Wyoming"}
    };
    /*************************************************************/

    /** Phone Order 관련 변수 *************************************/
    public static String PHONEORDERYN = "N";
    public static String PHONEORDER_FORCE_KITCHENPRINT = "N";
    public static String PHONEORDER_HOLDCODE = "";
    /*************************************************************/


    /** 레이아웃 변수 모음 ***************************************/
    //--- 레이어 객체는 선언만 하고 생성은 메인에서 한다. ----------------

    // 프레임 레이아웃에 있는 레이아웃들 객체 선언 ----------------------------
    // ROOT 레이아웃 (RelativeLayout)
    public static RelativeLayout GLOBAL_LAYOUT_ROOT;
    // 메인 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN;
    // Employee Selection 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION;
    // Customer Search 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH;
    // Quick Sale 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE;
    // Product Sale 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE;
    // Product List 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST;
    // Gift Card 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD;
    // Discount 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT;
    // Discount 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT;
    // Command Button 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON;
    // Menu Search 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH;
    // ----------------------------------------------------------------

    // 메인 우측 상단 버튼 객체 선언 ----------------------------------
    // 인터넷 상태 체크 TextView
    public static TextView GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS;
    // 클라우드 이동버튼
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1;
    // Command 옵션버튼
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2;
    // System Setting 버튼
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3;
    // 앱 종료 버튼
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4;
    // ----------------------------------------------------------------

    // 메인 좌측 상단 Employee Information 관련 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO;
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO_LN;
    // 메인 좌측 상단 CUST 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER;
    // 메인 좌측 상단 Customer 이름 TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME;
    // 메인 좌측 상단 Customer 전화번호 TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE;
    // 메인 좌측 상단 HOLD 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD;
    // 메인 좌측 상단 RECALL 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL;
    // 메인 좌측 상단 SAVE ORDER 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER;

    // 메인 좌측 상단 PUSH TEXTVIEW 버튼
    public static Button GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON;

    // Customer Main
    public static TextView GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_TABLENAME;
    public static Button GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_PLACETOORDER;
    public static Button GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CANCEL;
    public static Button GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_MYORDER;
    public static Button GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CALL;
    public static ImageButton GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_SLEEP;

    // 메인 좌측 상단 togo order 용 고객 정보 리니어 레이아웃
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2;
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1;
    public static TextView GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_NAME;
    public static TextView GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE;
    public static TextView GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_ONLINE_SHOW;

    // 메인 좌측상단 Total Order QTY
    public static TextView GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY;

    // 메인 리스트뷰 타이틀 텍스트 객체 생성
    public static TextView GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW1, GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW2;
    public static TextView GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW3, GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW4;
    public static TextView GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW5, GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6;
    public static CheckBox GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX;
    public static boolean ALL_CHECKBOX_INIT;
    // 메인 좌측 리스트뷰 (ListView) 객체 선언
    public static ListView GLOBAL_LAYOUTMEMBER_MAINLISTVIEW;
    // 메인 좌측 리스트뷰 아래 Sub Total TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW;
    // 메인 좌측 리스트뷰 아래 Common Gratuity 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW;
    // 메인 좌측 리스트뷰 아래 Tax TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW;
    // 메인 좌측 리스트뷰 아래 Delivery / Pickup Fee 관련 객체 선언 ------------------
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPLN;
    // 메인 좌측 리스트뷰 아래 Delivery / Pickup Fee 객체 선언 - Text
    public static TextView GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPTEXTTV;
    // 메인 좌측 리스트뷰 아래 Delivery / Pickup Fee 객체 선언 - Price
    public static TextView GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV;
    // -----------------------------------------------------------------------------

    // 07.18.2022 - add pay for cash, card ---------------------------------------
    // 메인 좌측 리스트뷰 아래 Add Pay 관련 객체 선언 ------------------
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINADDPAYLN;
    // 메인 좌측 리스트뷰 아래 Add Pay 객체 선언 - Text
    public static TextView GLOBAL_LAYOUTMEMBER_MAINADDPAYLNTV;

    public static LinearLayout GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN;
    public static TextView GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV;

    public static RelativeLayout GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT;
    public static TextView GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV;
    // ----------------------------------------------------------------------------

    // 메인 좌측 리스트뷰 아래 Total TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW;

    // Bill - 메인 좌측 리스트뷰 아래 Bill 결제관련 LinearLayout
    public static LinearLayout GLOBAL_LAYOUTMEMBER_BILLLN;
    // Bill - 메인 좌측 리스트뷰 아래 Bill 결제관련 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW;
    // Bill - 메인 좌측 리스트뷰 아래 Bill 옆 Of 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_BILLAMOUNTOFTEXTVIEW;

    // 메인 좌측 하단 Cancel 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL;
    // 메인 좌측 하단 QTY 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE;
    // 메인 좌측 하단 DELETE 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY;
    // 0073120
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN;
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS;
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS;

    // 메인 좌측 하단 togo, delivery 추가
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO;
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY;
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY_TOGO_LN;

    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT;

    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER;

    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_DIVIDER;
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO;
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY;

    // 0073120
    // 메인 좌측 하단 PAY 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT;

    // 메인 우측 하단 EMPLOYEE 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE;
    // 메인 우측 하단 DISCOUNT 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT;
    // 메인 우측 하단 QUICK SALE 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE;
    // 메인 우측 하단 PRODUCT 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT;
    // 메인 우측 하단 GIFT CARD 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD;
    // 메인 우측 하단 COMMAND 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND;
    // 메인 우측 하단 MAIN SALE 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE;
    // 메인 우측 하단 MENU SEARCH 버튼객체 선언
    public static Button GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH;

    // 메인 중앙 하단 MUNES VIEW
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW;

    // jihun park Presentation View ListView
    public static ListView GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW;
    // Sub Total TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW;
    // Tax TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW;
    // Total TextView 객체 선언
    public static TextView GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW;

    // Presentation ImageView, VideoView 를 포함하고 있는 LinearLayout 객체 선언
    public static LinearLayout GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN;

    // Presentation ImageView 객체 선언
    public static ImageView GLOBAL_LAYOUTMEMBER_PRESENTATION_IMAGEVIEW;

    // Presentation VideoView 객체 선언
    public static VideoView GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW;

    // Presentation Delivery / Pickup Fee 관련 객체 선언 ------------------
    public static RelativeLayout GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN;
    // Presentation Delivery / Pickup Fee 객체 선언 - Text
    public static TextView GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPTEXTTV;
    // Presentation Delivery / Pickup Fee 객체 선언 - Price
    public static TextView GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPPRICETV;

    // 메인 상단 Bar LinearLayout 객체
    public static LinearLayout GLOBAL_APPTOPBAR;

    // 메인 우측 상단 카테고리 버튼의 부모(LinearLayout) 객체 선언
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT;
    // 메인 우측 중앙 서비스 버튼의 부모(LinearLayout) 객체 선언
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT;


    // 메인 하단 고객정보 관련 TextView 객체 선언
    public static TextView GLOBAL_BOTTOMMEMBER_LASTVISIT;
    public static TextView GLOBAL_BOTTOMMEMBER_POINT;
    public static TextView GLOBAL_BOTTOMMEMBER_DOB;
    public static TextView GLOBAL_BOTTOMMEMBER_NOTE;

    // 메인 하단 포스 버전정보 TextView 객체 선언
    public static TextView GLOBAL_BOTTOMPOS_VERSIONINFO;
    public static ImageButton GLOBAL_BOTTOMPOS_VERSION_IMG_BTN;

    // 메인 프레임 색상변경을 위한 LinearLayout 객체들 선언
    public static LinearLayout GLOBAL_MAINMIDDLELEFTLN;
    public static LinearLayout GLOBAL_MAINMIDDLELEFTTOPLN;
    public static LinearLayout GLOBAL_MAINBOTTOMLN;

    // hold, recall, saveorder 버튼 파트 관련
    public static LinearLayout GLOBAL_MAINTOPLN1;
    public static LinearLayout GLOBAL_MAINTOPLN2;

    // 06.01.2022 -------------------------------------------
    public static LinearLayout GLOBAL_MAINPEOPLECNTLN;
    public static TextView GLOBAL_MAINPEOPLECNTTOPTV;
    public static TextView GLOBAL_MAINPEOPLECNTTV;
    // ------------------------------------------------------

    // 06.03.2022 -------------------------------------------
    public static LinearLayout GLOBAL_MAINLEFTBOTTOMLN1;
    public static LinearLayout GLOBAL_MAINLEFTBOTTOMLN2;
    public static LinearLayout GLOBAL_directButtonsLinearLayout3;
    // ------------------------------------------------------


    // Payment 관련 객체 선언 - "PAY" 버튼 클릭시 다른 버튼들을 클릭하지 못하게 하기위한 변수
    //                        "PAY" 버튼 클릭시 값을 Y 로 수정
    public static String GLOBAL_PAYBUTTONCLICKED = "N";

    // main side button
    public static LinearLayout GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW;

    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_TABLE;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_SERVER;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_QUICK;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_PRODUCT;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_GC_BALANCE;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_LOGOUT;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_DIVIDER;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_BILLPRINT;

    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_LOGLIST;
    // 220902
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_HOLD;
    public static LinearLayout GLOBAL_BUTTON_MAIN_SIDE_RECALL;

    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_TABLE;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_SERVER;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_DISCOUNT;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_QUICK;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_PRODUCT;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_GIFTCARD;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_GC_BALANCE;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_TIMEMENU;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_MENUSEARCH;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_LOGOUT;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_DIVIDER;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_BILLPRINT;
    public static TextView GLOBAL_TEXTVIEW_MAIN_SIDE_LOGLIST;

    public static TextView GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME;
    public static LinearLayout GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN;
    public static String GLOBAL_TEXT_MAIN_TOP_TABLE_NAME = "";

    /*************************************************************/

    /** 디바이스가 Elo 인지 여부 **********************************/
    public static String mDeviceEloYN = "N";
    /********************************************************************/

    /** 디바이스가 Clover 인지 여부 **********************************/
    public static boolean mDevicePAX = false;
    /********************************************************************/
    public static boolean mDeviceSunmi = false;

    public static boolean mDeviceTabletPC = false;

    /** 프린터 종료 *****************************************************/
    public static String GLOBAL_PRINTERNAME = "";
    /********************************************************************/

    // Employee Selection 관련 객체 선언
    public static LinearLayout GLOBAL_LAYOUTMEMBER_EMPLOYEESELECTION_LINEARLAYOUTEMPLOYEELIST;

    // SaleHistory 검색관련 변수
    public static String sh_startdate = "";
    public static String sh_enddate = "";
    public static String sh_deliverytakeaway = "";
    public static String sh_keyword = "";

    public static  String sh_fromCommand = "N";
    public static  String shweb_fromCommand = "N";
    /*************************************************************/

    public static int kitchenprinter1_papercount = 1;
    public static int kitchenprinter2_papercount = 1;
    public static int kitchenprinter3_papercount = 1;
    public static int kitchenprinter4_papercount = 1;
    public static int kitchenprinter5_papercount = 1;

    public static String kitchenprinting_type = "I";                // I : 이미지 프린팅          T : 텍스트 프린팅

    public static String kitchenprintername1 = "";
    public static String kitchenprintername2 = "";
    public static String kitchenprintername3 = "";
    public static String kitchenprintername4 = "";
    public static String kitchenprintername5 = "";

    // 영수증 프린트시 Tax Exempt (T/E) 텏스트 사용여부
    public static boolean mUseTaxExemptTxtYN = true;

    // 메뉴 프린팅 했는지 여부
    public static boolean b_isMenuPrinted = false;

    // 주문시 고객정보 입력페이지 보여줄지 여부
    public static boolean mCustomerInfoShowYN = false;

    // drawer 를 open 했는지 여부
    public static boolean b_isDrawerOpen = false;

    /** 메인 액티비티 FrameLayout 중에서 현재 visible 로 되어 있는 레이어******/
    public static String visibleLayoutName = "main";
    /********************************************************************/


    /** 직원정보 관련 변수 *************************************************/
    public static TemporaryEmployeeInfo GLOBAL_EMPLOYEEINFO;
    /********************************************************************/

    /** 고객정보 관련 변수 *************************************************/
    public static TemporaryCustomerInfo GLOBAL_CUSTOMERINFO;
    /********************************************************************/

    // 메인 하단에 있는 send to kitchen 버튼 클릭한 경우인지 여부
    public static boolean mIsClickSendToKitchen = false;

    // 메인 하단에 있는 payment 버튼 클릭한 경우인지 여부
    public static boolean mIsClickPayment = false;

    // 키친프린팅시에 고객정보를 임시로 저장하는 변수
    public static String mTempCustomerInfo = "";

    /** 태블릿 해상도 *************************************************/
    // 기본 해상도
    public static int tabletBasicWidth = 800;
    public static int tabletBasicHeight = 1280;

    // 사용중인 현재 태블릿 해상도
    public static int thisTabletRealWidth = 800;
    public static int thisTabletRealHeight = 1280;

    // 로그인창
    public static boolean b_isLoginView = false;
    // Cash In 이후 Cash Out 하지 않은 직원이 있을 경우 다른 직원선택이 안되도록 한다. ------------- 이 값이 true 일 경우 b_isNotComplite_CashIn true
    public static boolean b_isNotComplite_CashIn = false;

    public static AlertDialog ftp_upload_Dialog;
    public static boolean b_ftp_dialog_showing = false;

    //
    public static AsyncTask<Integer, String, Integer> mjjj_progressBar;
    public static ProgressBar mProgressBar;

    //
    public static ProgressBarDialog progressBarDialog;
    //
    public static boolean b_datadownload_not_complete = false;

    // table select tablename
    public static String str_selected_table_name;

    // LabelPrinter Buzzer Count
    public static int LabelPrinterBuzzerCount = 0;

    // Main Memo
    public static String m_temp_mainMemo = "";


    public static int getDisplayWidth(Context paramCxt) {
        int retInt = 0;
        Display display = ((WindowManager)paramCxt.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        retInt = display.getWidth();
        return retInt;
    }
    public static int getDisplayHeiheight(Context paramCxt) {
        int retInt = 0;
        Display display = ((WindowManager)paramCxt.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        retInt = display.getHeight();
        return retInt;
    }
    /********************************************************************/

    /** Credit Card Error Code 값 ***************************************/
    public static String getCreditCardErrors(String paramErrCode){
        String retMsgStr = "Credit card processing is failure";
        switch (paramErrCode) {
            case "-1" : {
                retMsgStr = "Connection Error (Terminal is not connected)";
                break;
            }
        }

        return retMsgStr;
    };
    /********************************************************************/

    // 전체 테이블명 배열 (basic_pos_information 테이블 제외)
    public static String[] dbTableNameGroup = {
            "salon_storestationinfo",
            "salon_storeservice_sub_setmenu",
            "salon_storeservice_sub_order",
            "salon_storeservice_sub",
            "salon_storeservice_sub_timemenuprice",
            "salon_storeservice_main_order",
            "salon_storeservice_main",
            "salon_storeproduct_ipkohistory",
            "salon_storeproduct",
            "salon_storephoto",
            "salon_storePG",
            "salon_storeinfo_worktime_forPOS",
            "salon_storeinfo_worktime",
            "salon_storeinfo",
            "salon_storegiftcard",
            "salon_storegeneral",
            "salon_store_restaurant_table",
            "salon_store_restaurant_tablezone",
            "salon_storeemployee_workweekday",
            "salon_storeemployee_worktime",
            "salon_storeemployee_loginlog",
            "salon_storeemployee",
            "salon_storepart",
            "salon_storepart_employee",
            "salon_member",
            "salon_info2",
            "salon_info1",
            "salon_general",
            "reservation_time",
            "reservation_service",
            "reservation",
            "pg_info",
            "member2",
            "member1",
            "member_mileage",
            "member_memo",
            "coupon_registration_no",
            "coupon_mailingpush_send",
            "coupon_issue_history",
            "coupon_imgtype",
            "coupon",
            "admin",
            "temp_salecart",
            "temp_salecart_ordered",
            "temp_salecart_del",
            "temp_salecart_del2",
            "salon_storegiftcard_number",
            "salon_storegiftcard_number_history",
            "salon_sales_detail",
            "salon_sales",
            "salon_sales_tip",
            "salon_sales_batch",
            "salon_sales_batch_json",
            "salon_clockinout",
            "sync_data_log",
            "salon_sales_card",
            "salon_sales_return",
            "salon_storestationsettings_system",
            "salon_storestationsettings_deviceprinter",
            "salon_storestationsettings_deviceprinter2",
            "salon_storestationsettings_deviceprinter3",
            "salon_storestationsettings_deviceprinter4",
            "salon_storestationsettings_deviceprinter5",
            "salon_storestationsettings_deviceprinter6",
            "salon_storestationsettings_paymentgateway",
            "clouddbbackup_log",
            "temp_salecart_deliveryinfo",
            "salon_sales_web_push_realtime",
            "salon_storeservice_option_btn",
            "salon_storeservice_option",
            "salon_storeservice_option_item",
            "salon_storeservice_additional",
            "salon_storeservice_commonmodifier",
            "salon_storeservice_commonmodifier_item",
            "salon_sales_cashintout_history",
            "salon_employee_loginout_history",
            "member_salevisit",
            "salon_store_cashinoutreason",
            "salon_sales_cashout_json",
            "salon_sales_endofday_json",
            "salon_sales_cashout_emp",
            "salon_sales_kitchenprintingdata",
            "salon_sales_customerordernumber",
            "salon_sales_phoneordernumber",
            "salon_store_deliveryappcompany",
            "temp_salecart_optionadd",
            "temp_salecart_optionadd_imsi",
            "temp_salecart_optionadd_gm",
            "temp_salecart_optionadd_imsi_gm",
            "salon_sales_receipt_json",
            "salon_sales_kitchen_json",
            "salon_sales_cashintout_history_cashlist",
            "salon_sales_customerpagernumber",
            "salon_sales_signedimg",
            "temp_mileagecart",
            "salon_storediscountbutton",
            "salon_storespecialrequest",

            // 02192024
            "salon_storememberlevel",


            // 11012023
            "salon_storeitemdeletereason",

            "salon_store_tare",
            "salon_connectedbluetoothinfo",
            "salon_store_restaurant_table_split",
            "salon_store_restaurant_table_peoplecnt",
            "salon_store_restaurant_table_merge",
            "salon_store_curbsidepickup",
            "salon_store_curbsidenewsidemenu",
            "salon_sales_kitchenprintingdata_json",

            // 01172024
            "salon_sales_kitchenprintingdata_json_torder",

            "salon_sales_return_byemplyee",
            "salon_newcartcheck_bystation",

            // 04252023
            "salon_newcartcheck_bystation2",
            "salon_table_statuschange",

            "bill_list",
            "bill_list_paid",
            "card_processing_data",
            "salon_storestationsettings_system_receipt",

            "salon_storestationsettings_deviceprinter_label1",
            "salon_storestationsettings_deviceprinter_label2",
            "salon_storestationsettings_deviceprinter_label3",
            "salon_storestationsettings_deviceprinter_label4",
            "salon_storestationsettings_deviceprinter_label5",
            "salon_storesmstextsample",

            "salon_storestationsettings_deviceprinter_master",

            "salon_sales_tip_split",

            "btn_logs",

            "bill_list_receipt_json",            // 01052023

            // 05152023
            "temp_salecart_2",
            "labelprinted_json",


            // 08092023
            "salon_storeemployeerole",


            // 10272023
            "salon_itemdeletereason"


            // "basic_pos_information" <--- 이 테이블은 기본정보를 가지고 있기 때문에 삭제하면 안된다.
            //                              단, 디바이스 초기화를 위한 메뉴가 있을 경우에는 삭제가능하다.
    };

    // 테이블 삭제후 테이블 생성한 후 데이터 등록시 삭제할 테이블명 배열
    public static String[] dbTableNameGroupForDrop = {
            "salon_storestationinfo",
            "salon_storeservice_sub_setmenu",
            "salon_storeservice_sub_order",
            "salon_storeservice_sub",
            "salon_storeservice_sub_timemenuprice",
            "salon_storeservice_main_order",
            "salon_storeservice_main",
            "salon_storeproduct_ipkohistory",
            "salon_storeproduct",
            "salon_storephoto",
            "salon_storePG",
            "salon_storeinfo_worktime_forPOS",
            "salon_storeinfo_worktime",
            "salon_storeinfo",
            "salon_storegiftcard",
            "salon_storegeneral",
            "salon_store_restaurant_table",
            "salon_store_restaurant_tablezone",
            "salon_storeemployee_workweekday",
            "salon_storeemployee_worktime",
            "salon_storeemployee_loginlog",
            "salon_storeemployee",
            "salon_storepart",
            "salon_storepart_employee",
            "salon_member",
            "salon_info2",
            "salon_info1",
            "salon_general",
            "reservation_time",
            "reservation_service",
            "reservation",
            "pg_info",
            "member2",
            "member1",
            "member_mileage",
            "member_memo",
            "coupon_registration_no",
            "coupon_mailingpush_send",
            "coupon_issue_history",
            "coupon_imgtype",
            "coupon",
            "admin",
            "clouddbbackup_log",
            "salon_store_deliveryappcompany",
            "salon_storeservice_option_btn",
            "salon_storeservice_option",
            "salon_storeservice_option_item",
            "salon_storeservice_additional",
            "salon_storeservice_commonmodifier",
            "salon_storeservice_commonmodifier_item",
            "salon_storediscountbutton",
            "salon_storespecialrequest",

            // 02192024
            "salon_storememberlevel",


            // 11012023
            "salon_storeitemdeletereason",

            "salon_store_tare",
            "salon_storesmstextsample",

            // 08092023
            "salon_storeemployeerole"
    };

    // API 를 통한 데이터 다운로드할 테이블명
    public static String[] dbTableNameGroupForApi = {
            "salon_storegeneral",
            "salon_store_restaurant_table",
            "salon_store_restaurant_tablezone",
            "salon_storeinfo",
            "salon_storeinfo_worktime",
            "salon_storeinfo_worktime_forPOS",
            "salon_storestationinfo",
            "salon_storeemployee",
            "salon_storeproduct_ipkohistory",
            "salon_storeemployee_worktime",
            "salon_storeemployee_workweekday",
            "salon_storepart",
            "salon_storepart_employee",
            "salon_storePG",
            "salon_storegiftcard",
            "coupon",
            "coupon_imgtype",
            "coupon_issue_history",
            "salon_storeproduct",
            "salon_member",
            "member1",
            "member2",
            "salon_storeservice_main",
            "salon_storeservice_sub",
            "salon_storeservice_sub_setmenu",
            "salon_storeservice_sub_timemenuprice",
            "salon_storememosel",
            "salon_storeservice_option_btn",
            "salon_storeservice_option",
            "salon_storeservice_option_item",
            "salon_storeservice_additional",
            "salon_storeservice_commonmodifier",
            "salon_storeservice_commonmodifier_item",
            "salon_store_cashinoutreason",
            "salon_sales_cashout_json",
            "salon_sales_endofday_json",
            "salon_sales_cashout_emp",
            "salon_sales_kitchenprintingdata",
            "salon_store_deliveryappcompany",
            "member_mileage",
            "salon_storediscountbutton",
            "salon_storespecialrequest",

            // 02192024
            "salon_storememberlevel",


            // 11012023
            "salon_storeitemdeletereason",

            "salon_store_tare",
            "salon_connectedbluetoothinfo",
            "salon_store_restaurant_table_split",
            "salon_store_restaurant_table_peoplecnt",
            "salon_store_restaurant_table_merge",
            "salon_store_curbsidepickup",
            "salon_store_curbsidenewsidemenu",
            "salon_storesmstextsample",

            // 08092023
            "salon_storeemployeerole"
    };

    // API 를 통한 데이터 다운로드할 테이블명
    public static String[] dbTableNameGroupForApi_new = {
            "salon_info1", "salon_info2", "salon_general",
            "salon_storeinfo",
            "salon_storestationinfo", "salon_storegeneral", "salon_store_restaurant_table", "salon_store_restaurant_tablezone",
            "salon_storeinfo_worktime", "salon_storeinfo_worktime_forPOS",
            "salon_store_cashinoutreason", "salon_store_deliveryappcompany",
            "salon_storediscountbutton",
            "salon_storespecialrequest",

            // 02192024
            "salon_storememberlevel",


            // 11012023
            "salon_storeitemdeletereason",

            "salon_storesmstextsample",

            "salon_storeemployee", "salon_storeemployee_worktime", "salon_storeemployee_workweekday", "salon_storepart", "salon_storepart_employee",

            // 08092023
            "salon_storeemployeerole",

            "salon_storeservice_main",
            "salon_storeservice_sub", "salon_storeservice_sub_order",
            "salon_storeservice_sub_setmenu",
            "salon_storeservice_option_btn", "salon_storeservice_option", "salon_storeservice_option_item", "salon_storeservice_additional",
            "salon_storeservice_commonmodifier", "salon_storeservice_commonmodifier_item",
            "salon_storeservice_sub_timemenuprice", "salon_store_tare",

            "salon_storeproduct", "salon_storeproduct_ipkohistory",

            "salon_storegiftcard",

            "member1", "member2",
            "salon_member", "salon_storememosel",

            "coupon", "coupon_imgtype",
            "coupon_issue_history"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - STORE
    public static String[] dbTableNameGroupForApi_store = {
            "salon_info1", "salon_info2", "salon_general",
            "salon_storeinfo",
            "salon_storestationinfo", "salon_storegeneral", "salon_store_restaurant_table", "salon_store_restaurant_tablezone",
            "salon_storeinfo_worktime", "salon_storeinfo_worktime_forPOS",
            "salon_store_cashinoutreason", "salon_store_deliveryappcompany",
            "salon_storediscountbutton", "salon_storespecialrequest",

            // 02192024
            "salon_storememberlevel",


            // 11012023
            "salon_storeitemdeletereason",

            "salon_storesmstextsample"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - EMPLOYEE
    public static String[] dbTableNameGroupForApi_employee = {
            "salon_storeemployee", "salon_storeemployee_worktime", "salon_storeemployee_workweekday", "salon_storepart", "salon_storepart_employee",
            // 08092023
            "salon_storeemployeerole"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - FOOD
    public static String[] dbTableNameGroupForApi_food = {
            "salon_storeservice_main",
            "salon_storeservice_sub", "salon_storeservice_sub_order",
            "salon_storeservice_sub_setmenu",
            "salon_storeservice_option_btn", "salon_storeservice_option", "salon_storeservice_option_item", "salon_storeservice_additional",
            "salon_storeservice_commonmodifier", "salon_storeservice_commonmodifier_item",
            "salon_storeservice_sub_timemenuprice", "salon_store_tare"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - PRODUCT
    public static String[] dbTableNameGroupForApi_product = {
            "salon_storeproduct", "salon_storeproduct_ipkohistory"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - GIFT CARD
    public static String[] dbTableNameGroupForApi_giftcard = {
            "salon_storegiftcard"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - MEMBER
    public static String[] dbTableNameGroupForApi_member = {
            "member1", "member2",
            "salon_member", "salon_storememosel"
    };

    // API 를 통한 데이터 다운로드할 테이블명 - COUPON
    public static String[] dbTableNameGroupForApi_coupon = {
            "coupon", "coupon_imgtype",
            "coupon_issue_history"
    };


    // log save 용 변수
    public static String str_logsaveIn_MenuNames = "";
    public static String str_logsaveIn_CategoryNames = "";

    // 문자열이 공백인지 체크
    public static boolean isStrEmpty(String str) {
        boolean returnValue = true;
        if (str == "" || str == null || str.equals("")) {
            returnValue = true;
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    // 문자열이 공백여부 체크하여 문자열 리턴
    public static String getConvertText(String str) {
        String returnText = "";
        if (isStrEmpty(str)) {
            returnText = "";
        } else {
            returnText = str;
        }
        return returnText;
    }

    // 문자열이 공백여부 체크하여 공백일 경우 파라미터로 넘어온 retValue 값 리턴
    public static String getConvertTextReturn(String str, String retValue) {
        String returnText = "";
        if (isStrEmpty(str)) {
            returnText = retValue;
        } else {
            returnText = str;
        }
        return returnText;
    }

    // 문자열을 Integer 형으로 변환
    public static int getIntAtString(String str) {
        int returnIntResult = 0;
        if (isStrEmpty(str)) {
            returnIntResult = 0;
        } else {
            str = getReplaceText(getReplaceText(str, "$", ""), ",", "");
            if (isCheckInteger(str)) {
                returnIntResult = Integer.parseInt(str);
            } else {
                returnIntResult = 0;
            }
        }
        return returnIntResult;
    }

    // 문자열을 integer 형으로 변환 + 소숫점 자리 삭제.
    public static int getIntAtString2(String str) {
        int returnIntResult = 0;
        if (isStrEmpty(str)) {
            returnIntResult = 0;
        } else {
            str = getReplaceText(getReplaceText(getReplaceText(str,".",""), "$", ""), ",", "");
            if (isCheckInteger(str)) {
                returnIntResult = Integer.parseInt(str);
            } else {
                returnIntResult = 0;
            }
        }
        return returnIntResult;
    }

    // 문자열을 Double 형으로 변환
    public static Double getDoubleAtString(String str) {
        Double returnDoubleResult = 0.0;
        if (isStrEmpty(str)) {
            returnDoubleResult = 0.0;
        } else {
            str = getReplaceText(getReplaceText(str, "$", ""), ",", "");
            if (isCheckDouble(str)) {
                returnDoubleResult = Double.parseDouble(str);
            } else {
                returnDoubleResult = 0.0;
            }
        }
        return returnDoubleResult;
    }

    // Double 숫자에 천단위로 콤마넣기
    public static String getCommaStringForDouble(String paramStr) {
        String returnStr = "";
        if (!isStrEmpty(paramStr)) {
            paramStr = getReplaceText(getReplaceText(paramStr, "$", ""), ",", "");
            double tempDbl = Double.parseDouble(paramStr);
            tempDbl = Double.parseDouble(getStringFormatNumber(tempDbl, "2"));
            DecimalFormat df = new DecimalFormat("###,###.##");
            returnStr = df.format(tempDbl);
            if (returnStr.indexOf(".") == -1) {
                returnStr = returnStr + ".00";
            } else {
                String[] tempReturnStr = returnStr.split("\\.");
                String tempFirstStr = tempReturnStr[0];
                String tempSecondStr = tempReturnStr[1];
                if (tempSecondStr.length() < 2) {
                    tempSecondStr = tempSecondStr + "0";
                }
                returnStr = tempFirstStr + "." + tempSecondStr;
            }
        }
        if (isStrEmpty(returnStr)) {
            returnStr = "0.00";
        }
        return returnStr;
    }
    // Double 숫자에 천단위로 콤마넣기 .0 단위
    public static String getCommaStringForDouble_forScale(String paramStr) {
        String returnStr = "";
        if (!isStrEmpty(paramStr)) {
            paramStr = getReplaceText(getReplaceText(paramStr, "$", ""), ",", "");
            double tempDbl = Double.parseDouble(paramStr);
            tempDbl = Double.parseDouble(getStringFormatNumber(tempDbl, "1"));
            DecimalFormat df = new DecimalFormat("###,###.#");
            returnStr = df.format(tempDbl);
            if (returnStr.indexOf(".") == -1) {
                returnStr = returnStr + ".0";
            } else {
                String[] tempReturnStr = returnStr.split("\\.");
                String tempFirstStr = tempReturnStr[0];
                String tempSecondStr = tempReturnStr[1];

                returnStr = tempFirstStr + "." + tempSecondStr;
            }
        }
        if (isStrEmpty(returnStr)) {
            returnStr = "0.0";
        }
        return returnStr;
    }

    // Long 숫자에 천단위로 콤마넣기
    public static String getCommaStringForLong(String paramStr) {
        String returnStr = "";
        if (!isStrEmpty(paramStr)) {
            paramStr = getReplaceText(getReplaceText(paramStr, "$", ""), ",", "");
            Long tempLng = Long.parseLong(paramStr);
            DecimalFormat df = new DecimalFormat("###,###.##");
            returnStr = df.format(tempLng);
            if (returnStr.indexOf(".") == -1) {
                returnStr = returnStr + ".00";
            }
        }
        return returnStr;
    }

    // Integer 숫자에 천단위로 콤마넣기
    public static String getCommaStringForInteger(String paramStr) {
        String returnStr = "";
        if (!isStrEmpty(paramStr)) {
            paramStr = getReplaceText(getReplaceText(paramStr, "$", ""), ",", "");
            int tempLng = Integer.parseInt(paramStr);
            DecimalFormat df = new DecimalFormat("###,###.##");
            returnStr = df.format(tempLng);
        }
        return returnStr;
    }

    public static String getStringFormatNumber(Object obj, String su) {
        /**
         String returnStr = "0";
         if (obj != "") {
         double tempValue = GlobalMemberValues.getDoubleAtString(obj + "");
         returnStr = String.format("%." + su + "f", tempValue);
         }

         return returnStr;
         **/

        String returnStr = "0";

        if (obj != "") {
            String suFlag = "+";

            if (GlobalMemberValues.isStrEmpty(su)) {
                su = "2";
            }
            int intSu = GlobalMemberValues.getIntAtString(su);

            double dblObj = GlobalMemberValues.getDoubleAtString(obj.toString());

            if (dblObj < 0) {
                suFlag = "-";
                dblObj = dblObj * -1;
            }

            double tempDblValue = Math.pow(10.0, intSu);
            GlobalMemberValues.logWrite("getStringFormatNumberlog2", "tempDblValue : " + tempDblValue + "\n");

            double dblValue1 = GlobalMemberValues.getDoubleAtString(String.format("%.0f", tempDblValue));
            GlobalMemberValues.logWrite("getStringFormatNumberlog2", "dblValue1 : " + dblValue1 + "\n");

            double dblValue2 = GlobalMemberValues.getDoubleAtString(String.format("%.1f", tempDblValue));
            GlobalMemberValues.logWrite("getStringFormatNumberlog2", "dblValue2 : " + dblValue2 + "\n");

            double tempDbl = Math.round(dblObj * dblValue1) / dblValue2;

            if (suFlag.equals("-")) {
                tempDbl = tempDbl * -1;
            }

            returnStr = String.format("%." + su + "f", tempDbl);

            GlobalMemberValues.logWrite("getStringFormatNumberlog", "tempDbl : " + tempDbl + "\n");
            GlobalMemberValues.logWrite("getStringFormatNumberlog", "returnStr : " + returnStr + "\n");
        }

        return returnStr;
    }

    // 문자 변환
    public static String getReplaceText(String tempStr, String str1, String str2) {
        String returnStr = "";
        if (!GlobalMemberValues.isStrEmpty(tempStr)) {
            returnStr = tempStr.replace(str1, str2);
        }
        return returnStr;
    }

    // DB 에 삽입할 문자 체크 및 결과값 리턴
    public static String getDBTextAfterChecked(String tempStr, int cType) {
        String returnStr = "";
        if (cType == 0) {               // DB 입력/수정 용
            if (!GlobalMemberValues.isStrEmpty(tempStr)) {
                tempStr = getReplaceText(tempStr, "'''", "'");
                tempStr = getReplaceText(tempStr, "''", "'");
                returnStr = getReplaceText(tempStr, "'", "''");
            }
        } else {                        // DB 에서 추출시
            if (!GlobalMemberValues.isStrEmpty(tempStr)) {
                returnStr = getReplaceText(tempStr, "'''", "'");
                returnStr = getReplaceText(tempStr, "''", "'");
            }
        }
        return returnStr;
    }

    // 키패드(키보드) 감추기
    public static void setKeyPadHide(Context cxt, View view) {
        if (cxt != null && view != null) {
            InputMethodManager imm = (InputMethodManager)cxt.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 키패드(키보드) 보여주기
    public static void setKeyPadShow(Context cxt, View view) {
        if (cxt != null && view != null) {
            InputMethodManager imm = (InputMethodManager)cxt.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    // 메인 프레임레이아웃 visible /invisible 상태 변경
    public static void setFrameLayoutVisibleChange(String paramFrmLayout) {
        Animation animation1;
        Animation animation2;

        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.frame_in_left);
        animation2 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.frame_in_right);

        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(100);
        animation2.setDuration(50);

        // 클로버 Clover 관련
        GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "";
        if (GlobalMemberValues.isDeviceClover()) {
            GlobalMemberValues.cloverBarcodeScannerOff();
        }

        // 먼저 현재 visible 된 레이아웃을 invisible 시킨다.
        ProductList.onProductListYN = "N";
        switch (visibleLayoutName) {
            case "main" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN.setVisibility(View.GONE);
                break;
            }
            case "employeeSelection" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION.setVisibility(View.GONE);
                break;
            }
            case "customerSearch" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.setVisibility(View.GONE);
                break;
            }
            case "quickSale" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE.setVisibility(View.GONE);
                break;
            }
            case "productSale" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE.setVisibility(View.GONE);
                break;
            }
            case "productList" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.setVisibility(View.GONE);
                break;
            }
            case "giftcard" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD.setVisibility(View.GONE);
                break;
            }
            case "discount" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT.setVisibility(View.GONE);
                break;
            }
            case "payment" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.setVisibility(View.GONE);
                break;
            }
            case "commandButton" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.setVisibility(View.GONE);
                break;
            }
            case "menusearch" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.setVisibility(View.GONE);
                break;
            }

        }

        // 파라미터로 받은 레이아웃 이름에 해당되는 레이아웃을 visible 시킨다.
        switch (paramFrmLayout) {
            case "main" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN.setVisibility(View.VISIBLE);
                GLOBAL_PAYBUTTONCLICKED = "N";

                Payment.setDeliveryPickUpLn("", 0);

                // Lite 버전 관련
                if (GlobalMemberValues.isLiteVersion()) {
                    // 하단버튼 초기화
                    GlobalMemberValues.setInitMainBottomButtonBg();
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setBackgroundResource(R.drawable.aa_images_main_main_rollover_lite);
                }

                break;
            }
            case "employeeSelection" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION.setAnimation(animation1);
                }

                break;
            }
            case "customerSearch" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH.setAnimation(animation1);
                }

                GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "customersearch";
                break;
            }
            case "quickSale" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE.setAnimation(animation1);
                }

                break;
            }
            case "productSale" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE.setAnimation(animation1);
                }

                break;
            }
            case "productList" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST.setAnimation(animation1);
                }

                ProductList.onProductListYN = "Y";

                GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "productlist";
                break;
            }
            case "giftcard" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD.setAnimation(animation1);
                }

                GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "giftcard";
                break;
            }
            case "discount" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT.setAnimation(animation1);
                }

                GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "discount";
                break;
            }
            case "payment" : {
                // 해당 GlobalMemberValues.openAddPayCustomerSelectPopup(); 가 두번 불림 그래서 임시 주석처리함 082423
//                GlobalMemberValues.openAddPayCustomerSelectPopup();

                // 팁을 줬는지 여부를 false 로 변경
                GlobalMemberValues.ispaidtip = false;

                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT.setAnimation(animation1);
                }

                try {
                    if (MainActivity.mApiAdapter != null) {
                        if (!MainActivity.mApiAdapter.hasPaper()) {
                            //GlobalMemberValues.displayDialog(MainActivity.mContext, "", "There is no paper for printing", "Close");
                        }
                    } else {
                        MainActivity.setEloInit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 다이렉트 카드결제 사용여부에 따라...
                String tempCarddirectyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select carddirectyn from salon_storestationsettings_system "
                );
                if (GlobalMemberValues.isStrEmpty(tempCarddirectyn)) {
                    tempCarddirectyn = "N";
                }
                if (tempCarddirectyn == "Y" || tempCarddirectyn.equals("Y")) {
                    // 결제방법 선택창 오픈
                    Payment.openSelectPayActivity();
                }

                break;
            }
            case "commandButton" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.setAnimation(animation1);
                }

                break;
            }
            case "menusearch" : {
                GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.setVisibility(View.VISIBLE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH.setAnimation(animation1);
                }

                MenuSearch.onmenusearchYN = "Y";

                GlobalMemberValues.BARCODESCANVALUE_ONCLOVER = "menusearch";
                break;
            }

        }

        visibleLayoutName = paramFrmLayout;
    }

    public static void displayDialog(Context paramContext, String paramTitle, String paramMsg, String paramButtonTxt) {
        if (paramContext != null) {
            try {
                new AlertDialog.Builder(paramContext)
                        .setTitle(paramTitle)
                        .setMessage(paramMsg)
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton(paramButtonTxt,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        dialog.dismiss();
                                    }
                                })
                        .show();
            } catch (Exception e) {
            }
        }
    }

    public static void displayDialog3Btn(Context paramContext, String paramTitle, String paramMsg, String paramButtonTxt_btn1, String paramButtonTxt_btn2,String paramButtonTxt_btn3) {
        if (paramContext != null) {
            try {
                new AlertDialog.Builder(paramContext)
                        .setTitle(paramTitle)
                        .setMessage(paramMsg)
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton(paramButtonTxt_btn1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        dialog.dismiss();
                                    }
                                })
                        .setNeutralButton(paramButtonTxt_btn2,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(paramButtonTxt_btn3,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        dialog.dismiss();

                                        Intent creditCardStatusIntent = new Intent(MainActivity.mContext.getApplicationContext(), CreditCardStatusActivity.class);
                                        paramContext.startActivity(creditCardStatusIntent);
//                                        if (GlobalMemberValues.isUseFadeInOut()) {
//                                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
//                                        }
                                    }
                                })
                        .show();
            } catch (Exception e) {
            }
        }
    }

    public static void ftpUpLoaddisplayDialog(Context paramContext, String paramTitle, String paramMsg, String paramButtonTxt) {
        if (paramContext != null) {
            if (ftp_upload_Dialog != null){
                if (!ftp_upload_Dialog.isShowing() && b_ftp_dialog_showing == false) {
                    b_ftp_dialog_showing = true;
                    ftp_upload_Dialog.show();
                }
            } else {
                ftp_upload_Dialog = new AlertDialog.Builder(paramContext)
                        .setTitle(paramTitle)
                        .setMessage(paramMsg)
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton(paramButtonTxt,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //

                                        ftp_upload_Dialog.cancel();
                                    }
                                }).create();
                if (!ftp_upload_Dialog.isShowing() && b_ftp_dialog_showing == false) {
                    b_ftp_dialog_showing = true;
                    ftp_upload_Dialog.show();
                } else {

                }
            }

        }
    }

    public static String makeSalesCode() {
        String returnSalesCode = "";

        // 오늘 일시 가져오기
        String tempYear = DateMethodClass.nowYearGet();
        String tempDay = DateMethodClass.nowDayGet();
        String tempMonth = DateMethodClass.nowMonthGet();
        String tempHour = DateMethodClass.nowHourGet();
        String tempMinute = DateMethodClass.nowMinuteGet();
        String tempSec = DateMethodClass.nowSecondGet();

        StringBuffer buf =new StringBuffer();
        buf.append(tempYear).append(tempMonth).append(tempDay).append(tempHour).append(tempMinute).append(tempSec);

//        Random rnd = new Random();
//        for(int i = 0; i < 10; i++) {
//            if(rnd.nextBoolean()) {
//                buf.append((char)((int)(rnd.nextInt(26))+65));
//            } else {
//                buf.append((rnd.nextInt(10)));
//            }
//        }
//        returnSalesCode = buf.toString();

        returnSalesCode = buf.toString();

        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            returnSalesCode = returnSalesCode + TableSaleMain.mTableIdxArrList.get(0);
        } else {
            if (TableSaleMain.mSelectedTablesArrList != null && TableSaleMain.mSelectedTablesArrList.size() > 0) {
                returnSalesCode = returnSalesCode + TableSaleMain.mSelectedTablesArrList.get(0);
            }
        }

        GlobalMemberValues.logWrite("holdsalescodejjjlog", "value : " + returnSalesCode + "\n");

        return returnSalesCode;
    }

    public static String makeReturnCode() {
        String returnReturnCode = "";
        returnReturnCode = "R" + makeSalesCode();
        return returnReturnCode;
    }

    public static String makeHoldCodePre() {
        String returnHoldCode = "";

        // 오늘 일시 가져오기
        String tempYear = DateMethodClass.nowYearGet();
        String tempDay = DateMethodClass.nowDayGet();
        String tempMonth = DateMethodClass.nowMonthGet();
        String tempHour = DateMethodClass.nowHourGet();
        String tempMinute = DateMethodClass.nowMinuteGet();
        String tempSec = DateMethodClass.nowSecondGet();


        StringBuffer buf =new StringBuffer();
        buf.append(tempYear).append(tempMonth).append(tempDay).append(tempHour).append(tempMinute).append(tempSec);
//        Random rnd = new Random();
//        for(int i = 0; i < 15; i++) {
//            if(rnd.nextBoolean()) {
//                buf.append((char)((int)(rnd.nextInt(26))+65));
//            } else {
//                buf.append((rnd.nextInt(10)));
//            }
//        }
//        returnHoldCode = buf.toString();

        returnHoldCode = buf.toString();

        return returnHoldCode;
    }

    public static String makeHoldCode() {
        String returnHoldCode = "";

        returnHoldCode = GlobalMemberValues.makeHoldCodePre();

        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            returnHoldCode = returnHoldCode + TableSaleMain.mTableIdxArrList.get(0);
        } else {
            if (TableSaleMain.mSelectedTablesArrList != null && TableSaleMain.mSelectedTablesArrList.size() > 0) {
                returnHoldCode = returnHoldCode + TableSaleMain.mSelectedTablesArrList.get(0);
            }
        }

        GlobalMemberValues.logWrite("holdsalescodejjjlog", "value : " + returnHoldCode + "\n");

        return returnHoldCode;
    }

    public static String makeHoldCodeByTableIdx(String paramTableIdx, int paramSubTableNum) {
        String returnHoldCode = "";

        returnHoldCode = GlobalMemberValues.makeHoldCodePre() + paramTableIdx + paramSubTableNum;

        GlobalMemberValues.logWrite("holdsalescodejjjlog2", "value : " + returnHoldCode + "\n");

        return returnHoldCode;
    }

    public static String makeSplitTransactionCode() {
        String returnSalesCode = "";

        // 오늘 날짜 가져오기
        String tempDay = DateMethodClass.nowDayGet();
        String tempMonth = DateMethodClass.nowMonthGet();
        String tempYear = DateMethodClass.nowYearGet();

        StringBuffer buf =new StringBuffer();
        buf.append(tempMonth).append(tempDay).append(tempYear);
        Random rnd = new Random();
        for(int i = 0; i < 10; i++) {
            if(rnd.nextBoolean()) {
                buf.append((char)((int)(rnd.nextInt(26))+65));
            } else {
                buf.append((rnd.nextInt(10)));
            }
        }
        returnSalesCode = buf.toString();

        return returnSalesCode;
    }

    // 02032024
    public static String makeTOrderCodeForPOSOrder() {
        String returnHoldCode = "";

        // 오늘 일시 가져오기
        String tempYear = DateMethodClass.nowYearGet();
        String tempDay = DateMethodClass.nowDayGet();
        String tempMonth = DateMethodClass.nowMonthGet();
        String tempHour = DateMethodClass.nowHourGet();
        String tempMinute = DateMethodClass.nowMinuteGet();
        String tempSec = DateMethodClass.nowSecondGet();


        StringBuffer buf =new StringBuffer();
        buf.append(tempYear).append(tempMonth).append(tempDay).append(tempHour).append(tempMinute).append(tempSec);

        returnHoldCode = buf.toString();

        returnHoldCode = "P" + returnHoldCode;

        return returnHoldCode;
    }

    public static String getSaveType(String tempType) {
        String retStr = "";
        if (!GlobalMemberValues.isStrEmpty(tempType)) {
            switch (tempType) {
                case "0" : {
                    retStr = "S";
                    break;
                }
                case "1" : {
                    retStr = "P";
                    break;
                }
                case "2" : {
                    retStr = "G";
                    break;
                }
            }
        }

        return retStr;
    }

    public static String convertDate(String parmaDate) {
        String returnDate = "";

        if (!GlobalMemberValues.isStrEmpty(parmaDate))
        {
            // 먼저 paramDate 문자열의 유효성 검사
            String tempSplitStr[] = null;
            if (parmaDate.indexOf("-") > -1) {
                tempSplitStr = parmaDate.split("-");
            }
            if (parmaDate.indexOf("/") > -1) {
                tempSplitStr = parmaDate.split("/");
            }
            if (parmaDate.indexOf(".") > -1) {
                tempSplitStr = parmaDate.split(".");
            }
            if (tempSplitStr.length == 3) {
                // tempSplitStr 의 첫번째 문자열의 길이가 4 일 때 --> mm-dd-yyyy 로 변환
                // tempSplitStr 의 첫번째 문자열의 길이가 2 일 때 --> yyyy-mm-dd 로 변환
                if (tempSplitStr[0].length() == 4) {
                    returnDate = tempSplitStr[1] + "-" + tempSplitStr[2] + "-" + tempSplitStr[0];
                }
                if (tempSplitStr[0].length() == 2) {
                    returnDate = tempSplitStr[2] + "-" + tempSplitStr[0] + "-" + tempSplitStr[1];
                }

            } else {
                returnDate = "";
            }
        }
        return returnDate;
    }

    public static void setActivityOrientation(Activity paramActivity, Context paramContext) {
        //GlobalMemberValues.logWrite("PORTRAITORLANDSCAPEValue", "value : " + GlobalMemberValues.PORTRAITORLANDSCAPE + "\n");
        DatabaseInit dbInit = new DatabaseInit(paramContext);   // DatabaseInit 객체 생성
        int settingsSystemTableCnt = dbInit.checkTable("salon_storestationsettings_system");
        if (settingsSystemTableCnt > 0) {
            int landScape = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString("select inreverse from salon_storestationsettings_system"));
            switch (landScape) {
                case 0 : {
                    paramActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                }
                case 1 : {
                    paramActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
                }
                case 2 : {
                    paramActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    break;
                }
            }
        } else {
            paramActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public static String getNetworkPrinterIpPort() {
        String returnValue = "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String strQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4, networkprinterport " +
                " from salon_storestationsettings_deviceprinter ";
        Cursor settingsDevicePrinterCursor = dbInit.dbExecuteRead(strQuery);
        if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
            String tempNetworkprinterip1 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
            String tempNetworkprinterip2 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
            String tempNetworkprinterip3 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);
            String tempNetworkprinterip4 = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(3), 1);
            String tempNetworkprinterport = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(4), 1);

            if (!GlobalMemberValues.isStrEmpty(tempNetworkprinterip1) && !GlobalMemberValues.isStrEmpty(tempNetworkprinterip2) &&
                    !GlobalMemberValues.isStrEmpty(tempNetworkprinterip3) && !GlobalMemberValues.isStrEmpty(tempNetworkprinterip4)) {
                returnValue = tempNetworkprinterip1 + "." + tempNetworkprinterip2 + "." + tempNetworkprinterip3 + "." + tempNetworkprinterip4;
                if (!GlobalMemberValues.isStrEmpty(tempNetworkprinterport)) {
                    returnValue += ":" + tempNetworkprinterport;
                }
            }
        }

        return returnValue;
    }



    public static void setCloseAndroidApp(final Activity paramActivity) {
        if (!paramActivity.isFinishing()) {

            new AlertDialog.Builder(paramActivity)
                    .setTitle("QSRt")
                    .setMessage("")
                    .setNegativeButton("RESTART",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    setRestartAndroidAppMethod(paramActivity);
                                }
                            })
                    .setPositiveButton("SHUT DOWN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setCloseAndroidAppMethod(paramActivity);
                        }
                    })
                    .setNeutralButton("CANCEL", null)
                    .show();
        }
    }

    public static void setCloseAndroidAppMethod(final Activity paramActivity) {
        // jihun add
//        if (isDataBase5mb()){
//            new AlertDialog.Builder(paramActivity)
//                    .setTitle("NZ ANDROID")
//                    .setMessage("Database capacity exceeded " + GlobalMemberValues.init_capacity_db + "mb. Would you initialize the sale data after database backup?")
//                    //.setIcon(R.drawable.ic_launcher)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startDatabaseCheckBackup();
//
//                                    GlobalMemberValues.doMethodInClose(paramActivity);
//                                }
//                            })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            GlobalMemberValues.doMethodInClose(paramActivity);
//                        }
//                    })
//                    .show();
//        } else {
//            GlobalMemberValues.doMethodInClose(paramActivity);
//        }
        GlobalMemberValues.doMethodInClose(paramActivity);
//        if (GlobalMemberValues.getAppVersionName(MainActivity.mContext).contains("beta")) {
//
//        } else {
//            GlobalMemberValues.doMethodInClose(paramActivity);
//        }
    }

    public static void disconnectPrinter() {
        String tempPrinterName = getSavedPrinterName(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart();
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter.mPrinter != null) {
                    PosbankPrinter.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS = false;
                }
            }
        }
        PRINTERCONNECTEDSTATUS = false;
    }

    public static void disconnectPrinter2() {
        String tempPrinterName = getSavedPrinterNameForKitchen2(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart("kitchen1");
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter2.mPrinter != null) {
                    PosbankPrinter2.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS2 = false;
                }
            }
        }
        PRINTERCONNECTEDSTATUS2 = false;
    }

    public static void disconnectPrinter3() {
        String tempPrinterName = getSavedPrinterNameForKitchen3(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart("kitchen2");
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog", "disconnectPrinter3() 진입전" + "\n");
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter3.mPrinter != null) {
                    PosbankPrinter3.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS3 = false;
                    GlobalMemberValues.logWrite("secondkitchenlog", "disconnectPrinter3() 시행완료" + "\n");
                }
            }
        }
        PRINTERCONNECTEDSTATUS3 = false;
    }

    public static void disconnectPrinter4() {
        String tempPrinterName = getSavedPrinterNameForKitchen4(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart("kitchen3");
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog4", "disconnectPrinter4() 진입전" + "\n");
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter4.mPrinter != null) {
                    PosbankPrinter4.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS4 = false;
                    GlobalMemberValues.logWrite("secondkitchenlog4", "disconnectPrinter4() 시행완료" + "\n");
                }
            }
        }
        PRINTERCONNECTEDSTATUS4 = false;
    }

    public static void disconnectPrinter5() {
        String tempPrinterName = getSavedPrinterNameForKitchen5(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart("kitchen4");
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog5", "disconnectPrinter5() 진입전" + "\n");
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter5.mPrinter != null) {
                    PosbankPrinter5.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS5 = false;
                    GlobalMemberValues.logWrite("secondkitchenlog5", "disconnectPrinter5() 시행완료" + "\n");
                }
            }
        }
        PRINTERCONNECTEDSTATUS5 = false;
    }

    public static void disconnectPrinter6() {
        String tempPrinterName = getSavedPrinterNameForKitchen6(MainActivity.mContext);
        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart("kitchen5");
                starPrintStart.disconnect();
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog6", "disconnectPrinter6() 진입전" + "\n");
                // 포스뱅크 A11 프린터 해제
                if (PosbankPrinter6.mPrinter != null) {
                    PosbankPrinter6.mPrinter.disconnect();
                    GlobalMemberValues.PRINTERCONNECTEDSTATUS6 = false;
                    GlobalMemberValues.logWrite("secondkitchenlog6", "disconnectPrinter6() 시행완료" + "\n");
                }
            }
        }
        PRINTERCONNECTEDSTATUS6 = false;
    }

    public static void setCustomerInfoInit() {
        // 글로벌 static 고객정보 변수에 담는다.
        GlobalMemberValues.GLOBAL_CUSTOMERINFO = null;
        // 메인 좌측 상단 고객정보 TextView 에 표기한다.
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setText("Walk In");
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setText("");
        // 하단 고객정보에 넣기 전에 먼저 초기화한다.
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_LASTVISIT.setText("Last Visit : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("Point : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_DOB.setText("DOB : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE.setText("Note : ");

        // 고객명 배경색 초기화
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setBackgroundColor(Color.parseColor("#00ffffff"));

        // 고객메모 버튼을 고객선택 버튼으로 원복
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setBackgroundResource(R.drawable.ab_imagebutton_main_cust);
    }

    public static void setEmployeeInfoInit() {
        TemporaryEmployeeInfo tempEmpInfo = new TemporaryEmployeeInfo("", "ADMIN", "41", "", "", "", "", "", "", "", "", "0");
        GLOBAL_EMPLOYEEINFO = tempEmpInfo;
        GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText("ADMIN");
        GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setVisibility(View.VISIBLE);
    }

    public static void openCloudWeb(Context context, Activity mActivity) {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");

            // cloud 1.0 연결
            //Intent cloudWebPageIntent = new Intent(context.getApplicationContext(), CloudBackOffice.class);
            // cloud 3.0 연결
            Intent cloudWebPageIntent = new Intent(context.getApplicationContext(), CloudBackOffice_New.class);

            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(cloudWebPageIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        } else {
            // GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
            Intent cloudWebPageAtNotConnectionIntent = new Intent(context.getApplicationContext(), CloudWebPageAtNotConnection.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(cloudWebPageAtNotConnectionIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public static String getSyncDataLastTime(DatabaseInit paramDbinit) {
        String returnData = "";
        returnData = getDBTextAfterChecked(paramDbinit.dbExecuteReadReturnString(
                "select strftime('%m-%d-%Y %H:%M:%S', wdate) from sync_data_log order by idx desc limit 1"), 1);
        return returnData;
    }

    public static void serviceStartSendDataToCloud(Context paramContext, Activity paramActivity) {
        GlobalMemberValues.stopBackgroundService();

        if (!GlobalMemberValues.isSaleDataUploadPause()) {
            GlobalMemberValues gm = new GlobalMemberValues();
            if (gm.isPOSWebPay() &&
                    (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                // 장바구니데이터 클라우드 전송
                GlobalMemberValues.setSendCartToCloud(paramContext, paramActivity);
                // 장바구니데이터 삭제 클라우드 전송
                GlobalMemberValues.setSendCartDeleteToCloud(paramContext, paramActivity);
            }

            // 세일데이터 클라우드 전송
            GlobalMemberValues.logWrite("saledatauploadtest", "세일데이터 업로드 시작!\n");
            GlobalMemberValues.setSendSalesDataToCloud(paramContext, paramActivity);

            // Gift Card Number History 클라우드 전송
            GlobalMemberValues.setSendGiftCardNumberHistoryToCloud(paramContext, paramActivity);

            // Member Mileage History 클라우드 전성
            GlobalMemberValues.setSendMemberMileageHistoryToCloud(paramContext, paramActivity);

            // Tip 데이터 클라우드 전송
            GlobalMemberValues.setSendTipsToCloud(paramContext, paramActivity);

            // Cashout Json 데이터 클라우드 전송
            GlobalMemberValues.setSendCashOutJsonToCloud(paramContext, paramActivity);
            // End of day Json 데이터 클라우드 전송
            GlobalMemberValues.setSendEndOfDayJsonToCloud(paramContext, paramActivity);

            // POS 세일 JSON
            GlobalMemberValues.setSendSaleJsonToCloud(paramContext, paramActivity);
        }
    }

    // POS 세일 JSON
    public static void setSendSaleJsonToCloud(Context paramContext, Activity paramActivity) {
        Intent tempIntent = new Intent(paramContext.getApplicationContext(), UploadSaleJsonToCloud.class);
        CURRENTSERVICEINTENT_SALEJSON = tempIntent;                      // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_SALEJSON = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(tempIntent);
    }

    public static void setSendEndOfDayJsonToCloud(Context paramContext, Activity paramActivity) {
        Intent tempIntent = new Intent(paramContext.getApplicationContext(), UploadEndofdayJsonToCloud.class);
        CURRENTSERVICEINTENT_ENDOFDAYJSON = tempIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_ENDOFDAYJSON = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(tempIntent);
    }

    public static void setSendCashOutJsonToCloud(Context paramContext, Activity paramActivity) {
        Intent tempIntent = new Intent(paramContext.getApplicationContext(), UploadCashoutJsonToCloud.class);
        CURRENTSERVICEINTENT_CASHOUTJSON = tempIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_CASHOUTJSON = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(tempIntent);
    }

    public static void setSendCartToCloud(Context paramContext, Activity paramActivity) {
        Intent tempIntent = new Intent(paramContext.getApplicationContext(), UploadCartDataToCloud.class);
        //uploadSalesDataToCloudIntent.putExtra("exSalesCode", returnSalesCode);
        //mActivity.startActivity(uploadSalesDataToCloudIntent);
        //sendSalesDataToCloud(returnSalesCode);
        CURRENTSERVICEINTENT_CART = tempIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_CART = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(tempIntent);
        GlobalMemberValues.logWrite("uploadcartdatalog", "cart 여기실행됨-1." + "\n");
    }

    public static void setSendCartDeleteToCloud(Context paramContext, Activity paramActivity) {
        Intent tempIntent = new Intent(paramContext.getApplicationContext(), UploadCartDataDeleteToCloud.class);
        //uploadSalesDataToCloudIntent.putExtra("exSalesCode", returnSalesCode);
        //mActivity.startActivity(uploadSalesDataToCloudIntent);
        //sendSalesDataToCloud(returnSalesCode);
        CURRENTSERVICEINTENT_CARTDEL = tempIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_CARTDEL = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(tempIntent);
        GlobalMemberValues.logWrite("uploadcartdatalog", "cart 여기실행됨-1." + "\n");
    }

    public static void setSendSalesDataToCloud(Context paramContext, Activity paramActivity) {
        Intent uploadSalesDataToCloudIntent = new Intent(paramContext.getApplicationContext(), UploadSalesDataToCloud.class);
        //uploadSalesDataToCloudIntent.putExtra("exSalesCode", returnSalesCode);
        //mActivity.startActivity(uploadSalesDataToCloudIntent);
        //sendSalesDataToCloud(returnSalesCode);
        CURRENTSERVICEINTENT_SALE = uploadSalesDataToCloudIntent;    // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_SALE = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadSalesDataToCloudIntent);

        GlobalMemberValues.logWrite("saledatauploadtest", "세일데이터 업로드 시작2!\n");
    }

    public static void setSendTipsToCloud(Context paramContext, Activity paramActivity) {
        Intent uploadTipoCloudIntent = new Intent(paramContext.getApplicationContext(), UploadTipsDataToCloud.class);
        //uploadSalesDataToCloudIntent.putExtra("exSalesCode", returnSalesCode);
        //mActivity.startActivity(uploadSalesDataToCloudIntent);
        //sendSalesDataToCloud(returnSalesCode);
        CURRENTSERVICEINTENT_TIP = uploadTipoCloudIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_TIP = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadTipoCloudIntent);
        GlobalMemberValues.logWrite("UploadTipsDataToCloud", "여기실행됨-1." + "\n");
    }

    public static void setSendMemberMileageHistoryToCloud(Context paramContext, Activity paramActivity) {
        Intent uploadMemberMileageHistoryCloudIntent = new Intent(paramContext.getApplicationContext(), UploadMemberMileageHistoryToCloud_Service.class);
        CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY = uploadMemberMileageHistoryCloudIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadMemberMileageHistoryCloudIntent);
        GlobalMemberValues.logWrite("membermileageuploadlog", "여기실행됨-1." + "\n");
    }

    public static void setSendGiftCardNumberHistoryToCloud(Context paramContext, Activity paramActivity) {
        Intent uploadIntent = new Intent(paramContext.getApplicationContext(), UploadGiftCardHistoryToCloud_Service.class);
        CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadIntent);
        GlobalMemberValues.logWrite("giftcardhistoryuploadlog", "여기실행됨-1." + "\n");
    }

    public static void serviceStartCheckingUploadDataInCloud(Context paramContext, Activity paramActivity) {
        GlobalMemberValues.checkingDataInCloud_Sale(paramContext, paramActivity);

        GlobalMemberValues.checkingDataInCloud_Tip(paramContext, paramActivity);

        GlobalMemberValues.checkingDataInCloud_MemberMileage(paramContext, paramActivity);

        GlobalMemberValues.checkingDataInCloud_GiftCardHistory(paramContext, paramActivity);
    }

    public static void checkingDataInCloud_Sale(Context paramContext, Activity paramActivity) {
        Intent uploadIntent = new Intent(paramContext.getApplicationContext(), CheckingUploadDataOnSale_Service.class);
        CURRENTSERVICEINTENT_1 = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_1 = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadIntent);
        GlobalMemberValues.logWrite("checkingdatalog", "여기실행됨(Sale)-1." + "\n");
    }

    public static void checkingDataInCloud_Tip(Context paramContext, Activity paramActivity) {
        Intent uploadIntent = new Intent(paramContext.getApplicationContext(), CheckingUploadDataOnTip_Service.class);
        CURRENTSERVICEINTENT_2 = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_2 = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadIntent);
        GlobalMemberValues.logWrite("checkingdatalog", "여기실행됨(Tip)-1." + "\n");
    }

    public static void checkingDataInCloud_MemberMileage(Context paramContext, Activity paramActivity) {
        Intent uploadIntent = new Intent(paramContext.getApplicationContext(), CheckingUploadDataOnMemberMileage_Service.class);
        CURRENTSERVICEINTENT_3 = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_3 = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadIntent);
        GlobalMemberValues.logWrite("checkingdatalog", "여기실행됨(MemberMileage)-1." + "\n");
    }

    public static void checkingDataInCloud_GiftCardHistory(Context paramContext, Activity paramActivity) {
        Intent uploadIntent = new Intent(paramContext.getApplicationContext(), CheckingUploadDataOnGiftCardHistory_Service.class);
        CURRENTSERVICEINTENT_4 = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_4 = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(uploadIntent);
        GlobalMemberValues.logWrite("checkingdatalog", "여기실행됨(GiftCardHistory)-1." + "\n");
    }

    public static void setSendSaleDoneSMSToCloud(Context paramContext, Activity paramActivity, String paramSalesCode) {
        if (!GlobalMemberValues.isStrEmpty(paramSalesCode)) {
            mSalesCode_SALEDONESMS = paramSalesCode;
            Intent uploadIntent = new Intent(paramContext.getApplicationContext(), UploadSaleDoneSMS_Service.class);
            CURRENTSERVICEINTENT_SALEDONESMS = uploadIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_SALEDONESMS = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
            paramActivity.startService(uploadIntent);
            GlobalMemberValues.logWrite("setSendSaleDoneSMSToCloudLog", "여기실행됨-1." + "\n");
        }
    }

    public static float globalAddFontSizeForPAX() {
        float returnValue = 0.0f;
        if (GlobalMemberValues.mDevicePAX) {
            returnValue = 4.0f;
        }
        return returnValue;
    }

    public static float getGlobalFontSize() {
        //int thisTabletWidth = GlobalMemberValues.getDisplayWidth(MainActivity.mContext);
        float tabletWidthRatio = 0.7f;

        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            tabletWidthRatio = 1.0f;
        } else if (GlobalMemberValues.isDeviceElo()) {
            tabletWidthRatio = 1.0f;
        } else if (GlobalMemberValues.mDevicePAX) {
            tabletWidthRatio = 0.5f;
        } else if (GlobalMemberValues.mDeviceSunmi){
            tabletWidthRatio = 0.7f;
        } else if (GlobalMemberValues.mDeviceTabletPC){
            tabletWidthRatio = 0.5f;
        } else {
            tabletWidthRatio = 0.7f;
        }

        GlobalMemberValues.logWrite("fontsizeratio", "thisTabletRealHeight : " + GlobalMemberValues.thisTabletRealHeight + "\n");
        GlobalMemberValues.logWrite("fontsizeratio", "tabletBasicHeight : " + GlobalMemberValues.tabletBasicHeight + "\n");
        GlobalMemberValues.logWrite("fontsizeratio", "tabletWidthRatio : " + tabletWidthRatio + "\n");
        //tabletWidthRatio = 1.0f;
        return tabletWidthRatio;
    }

    public static void printLabel1to5(JSONArray temp_array){
        // 해당 아이템이 labelprintedyn n 이면 출력하고 y 로 바꿔주고
        // 해당 아이템이 labelprintedyn y 이면 출력하지 말것.

        // 라벨 프린터가 설정이 되어 있다면. 해당 ip 로 출력해야함
        String str_1connectAddress = "";
        String qurey = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label1";
        Cursor qurey_result = MainActivity.mDbInit.dbExecuteRead(qurey);
        if (qurey_result.getCount() > 0 && qurey_result.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(1), 1);
                if (is_usb.equals("1")){
                    str_1connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result.getString(5), 1);
                    str_1connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }

                JSONArray array = labelPrint_printNumber(temp_array, "1");

                if (array != null && array.length() > 0){
                    EpsonLabelPrinter1 epsonLabelPrinter1 = new EpsonLabelPrinter1(MainActivity.mContext);
                    epsonLabelPrinter1.runPrintReceiptSequence_array(array, str_1connectAddress, printername);
                }

            }

        }

        // 라벨 프린터가 설정이 되어 있다면. 해당 ip 로 출력해야함
        String str_2connectAddress = "";
        String qurey2 = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label2";
        Cursor qurey_result2 = MainActivity.mDbInit.dbExecuteRead(qurey2);
        if (qurey_result2.getCount() > 0 && qurey_result2.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(1), 1);
                if (is_usb.equals("1")){
                    str_2connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result2.getString(5), 1);
                    str_2connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }

                JSONArray array = labelPrint_printNumber(temp_array, "2");

                if (array != null && array.length() > 0){
                    EpsonLabelPrinter2 epsonLabelPrinter2 = new EpsonLabelPrinter2(MainActivity.mContext);
                    epsonLabelPrinter2.runPrintReceiptSequence_array(array, str_2connectAddress, printername);
                }
            }

        }

        // 라벨 프린터가 설정이 되어 있다면. 해당 ip 로 출력해야함
        String str_3connectAddress = "";
        String qurey3 = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label3";
        Cursor qurey_result3 = MainActivity.mDbInit.dbExecuteRead(qurey3);
        if (qurey_result3.getCount() > 0 && qurey_result3.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(1), 1);
                if (is_usb.equals("1")){
                    str_3connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result3.getString(5), 1);
                    str_3connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }

                JSONArray array = labelPrint_printNumber(temp_array, "3");

                if (array != null && array.length() > 0){
                    EpsonLabelPrinter3 epsonLabelPrinter3 = new EpsonLabelPrinter3(MainActivity.mContext);
                    epsonLabelPrinter3.runPrintReceiptSequence_array(array, str_3connectAddress, printername);
                }
            }

        }

        // 라벨 프린터가 설정이 되어 있다면. 해당 ip 로 출력해야함
        String str_4connectAddress = "";
        String qurey4 = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label4";
        Cursor qurey_result4 = MainActivity.mDbInit.dbExecuteRead(qurey4);
        if (qurey_result4.getCount() > 0 && qurey_result4.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(1), 1);
                if (is_usb.equals("1")){
                    str_4connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result4.getString(5), 1);
                    str_4connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }

                JSONArray array = labelPrint_printNumber(temp_array, "4");

                if (array != null && array.length() > 0){
                    EpsonLabelPrinter4 epsonLabelPrinter4 = new EpsonLabelPrinter4(MainActivity.mContext);
                    epsonLabelPrinter4.runPrintReceiptSequence_array(array, str_4connectAddress, printername);
                }
            }

        }

        // 라벨 프린터가 설정이 되어 있다면. 해당 ip 로 출력해야함
        String str_5connectAddress = "";
        String qurey5 = "SELECT printername, connectusb, networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 FROM salon_storestationsettings_deviceprinter_label5";
        Cursor qurey_result5 = MainActivity.mDbInit.dbExecuteRead(qurey5);
        if (qurey_result5.getCount() > 0 && qurey_result5.moveToFirst()) {
            String printername = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(0), 1);
            if (printername.isEmpty() || printername.equals("NO PRINTER")) {

            } else {
                String is_usb = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(1), 1);
                if (is_usb.equals("1")){
                    str_5connectAddress = "USB:";
                } else {
                    String ip1 = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(2), 1);
                    String ip2 = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(3), 1);
                    String ip3 = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(4), 1);
                    String ip4 = GlobalMemberValues.getDBTextAfterChecked(qurey_result5.getString(5), 1);
                    str_5connectAddress = "TCP:" + ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                }
                JSONArray array = labelPrint_printNumber(temp_array, "5");

                if (array != null && array.length() > 0){
                    EpsonLabelPrinter5 epsonLabelPrinter5 = new EpsonLabelPrinter5(MainActivity.mContext);
                    epsonLabelPrinter5.runPrintReceiptSequence_array(array, str_5connectAddress, printername);
                }

            }

        }
    }

    public static void printLabel(JSONObject paramJsonroot){
        JSONArray temp_array = new JSONArray();
        temp_array = labelPrint_menuSplit(paramJsonroot);
        if (temp_array != null && temp_array.length() != 0){
            printLabel1to5(temp_array);
        }
    }

    public static void printReceiptByJHP(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        GlobalMemberValues.disconnectPrinter();

        // 이곳에서 프린트 연동을 하면된다.
        // 파라미터 paramJsonroot 가 세일즈 데이터를 가지고 있는 JSON 객체다
        // paramReceiptPrintType 타입에 따라서 구현하면 된다.
        if (paramContext == null) {
            paramContext = MainActivity.mContext;
        }

        Context context = paramContext;



//
        if (paramReceiptPrintType.equals("payment") && GlobalMemberValues.mReReceiptprintYN != "Y" || GlobalMemberValues.mOnlineOrder == "Y"){
            if (GlobalMemberValues.is_printed_label == false){
                if (isUseLabelPrinter() && paramJsonroot != null){

                    printLabel(paramJsonroot);

                    // 레스토랑 전용 옵션.
                    if (isCustomerSelectReceipt()){
                        GlobalMemberValues.is_printed_label = true;
                    }

                }
                GlobalMemberValues.mOnlineOrder = "N";
            }
        }
        //

        String tempPrinterName = getSavedPrinterName(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart();
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                PosBankPrinterStart posBankPrinterStart = new PosBankPrinterStart(paramContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart.setConnect();

                /**
                 switch (paramReceiptPrintType) {
                 case "batchsummary" : {
                 GlobalMemberValues.logWrite("inconnectmethod", "Batch & Summary 에서 클릭... : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

                 PrinterReceipt printerReceipt = new PrinterReceipt();
                 //                      printerReceipt.PrintBatchStarPrint(context,paramJsonroot);
                 printerReceipt.printBatchPosbank(paramJsonroot);
                 break;
                 }
                 case "payment" : {
                 GlobalMemberValues.logWrite("inconnectmethod", "결제버튼 클릭... : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

                 // 이곳에 결제시 프린트를 구현하면 된다....
                 PrinterReceipt printerReceipt = new PrinterReceipt();
                 //                      printerReceipt.PrintPaymentStarPrint(context,paramJsonroot);
                 printerReceipt.printSalesReceiptPosbank(paramJsonroot);

                 break;
                 }
                 }

                 **/
                break;
            }
            case "Elo" : {
                EloPrinterStart.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                if (paramJsonroot != null) {
                    GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                }
                CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                // jihun park add TODO
                if (paramJsonroot != null) {
                    GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                }
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext,paramJsonroot,paramReceiptPrintType);
                break;
            }
            case "Giant-100":{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connect(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88":{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);

                                // 032224 bill print 시 epson 외 다른 프린트가 table main 으로 이동하지 않아서 아래라인으로 코드 이동함.
//                                if (paramReceiptPrintType.equals("phoneordercheckprint")){
//                                    if (epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType, Payment.jsonroot_kitchen)){
//                                        if (TableSaleMain.mSelectedTablesArrList != null && TableSaleMain.mSelectedTablesArrList.size() != 0){
//                                            GlobalMemberValues.setChangeBillPrintedStatus(TableSaleMain.mSelectedTablesArrList.get(0).toString(), TableSaleMain.mSubTableNum, true);
//                                        } else if (!TableSaleMain.mSelectedTableIDX.isEmpty()){
//                                            GlobalMemberValues.setChangeBillPrintedStatus(TableSaleMain.mSelectedTableIDX, TableSaleMain.mSubTableNum, true);
//                                            TableSaleMain.mSelectedTableIDX = "";
//                                        }
//
//                                    }
//                                } else {
//                                    epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType, Payment.jsonroot_kitchen);
//                                }
                                // 032224 bill print 시 epson 외 다른 프린트가 table main 으로 이동하지 않아서 아래라인으로 코드 이동함.
                                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType, Payment.jsonroot_kitchen);

                                b_temp = true;
                            }else {
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } while (!b_temp);
                    }
                }).start();

//                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);
//                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }

            case "No Printer" : {
                if (paramReceiptPrintType.equals("payment")) {
                    int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                            "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
                    if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
                        if (Payment.jsonroot_kitchen == null){
                            Payment.jsonroot_kitchen = new JSONObject();
                        }
                        try {
                            if (GlobalMemberValues.getReceiptFooterKitchen2() == null){

                            } else {
                                if (Payment.jsonroot_kitchen != null) {
                                    Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
                                } else {
                                    Payment.jsonroot_kitchen = new JSONObject();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String tempSalesCode = "";
                        try {
                            tempSalesCode = Payment.jsonroot_kitchen.getString("receiptno");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                            GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                            // 주방프린트 하기 ----------------------------------------------------------------
                            GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                            // -------------------------------------------------------------------------------
                        } else {
                            if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                                GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                                GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                                        || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                                    GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                                    // 주방프린트 하기 ----------------------------------------------------------------
                                    GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                                    // -------------------------------------------------------------------------------
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        // 032224 bill print 시 epson 외 다른 프린트가 table main 으로 이동하지 않아서 아래라인으로 코드 이동함.
        if (paramReceiptPrintType.equals("phoneordercheckprint")){
            if (TableSaleMain.mSelectedTablesArrList != null && TableSaleMain.mSelectedTablesArrList.size() != 0){
                GlobalMemberValues.setChangeBillPrintedStatus(TableSaleMain.mSelectedTablesArrList.get(0).toString(), TableSaleMain.mSubTableNum, true);
            } else if (!TableSaleMain.mSelectedTableIDX.isEmpty()){
                GlobalMemberValues.setChangeBillPrintedStatus(TableSaleMain.mSelectedTableIDX, TableSaleMain.mSubTableNum, true);
                TableSaleMain.mSelectedTableIDX = "";
            }
        }
        // 032224 bill print 시 epson 외 다른 프린트가 table main 으로 이동하지 않아서 아래라인으로 코드 이동함.

        if (getUseYNMasterPrinter(MainActivity.mContext).equals("1")){
            // master printing 실행
            String tempKindPrint = getSavedMasterPrinterKind(MainActivity.mContext);    // 0, 1, 2
            if (tempKindPrint.equals("0"))printMaster(paramJsonroot,paramContext,paramReceiptPrintType, "0");
        }

    }

    public static void printMaster(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType, String kitchenNum){
        String tempMasterPrinterName = getSavedMasterPrinterName(MainActivity.mContext);
        String tempKindPrint = getSavedMasterPrinterKind(MainActivity.mContext);    // 0, 1, 2

        if (tempKindPrint.equals("0")) {
            switch (tempMasterPrinterName) {
                case "STAR" : {
                    StarPrintStart starPrintStart = new StarPrintStart();
                    starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                    break;
                }
                case "PosBank" : {
                    PosBankPrinterStart posBankPrinterStart = new PosBankPrinterStart(paramContext, paramJsonroot, paramReceiptPrintType);
                    posBankPrinterStart.setConnect();
                    break;
                }
                case "Elo" : {
                    EloPrinterStart.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                    break;
                }
                case "Clover Station" : {
                    if (paramJsonroot != null) {
                        GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                    }
                    CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                    break;
                }
                case "Clover Mini" : {
                    CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                    break;
                }
                case "PAX" : {
                    // jihun park add TODO
                    if (paramJsonroot != null) {
                        GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                    }
                    PaxPrinterStart.paxPrintSwitch(MainActivity.mContext,paramJsonroot,paramReceiptPrintType);
                    break;
                }
                case "Giant-100":{
                    Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                    giantPrinter.connect(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                    break;
                }
                case "Epson-T88":{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean b_temp = false;
                            do {
                                if (EpsonReceiptPrintMaster.mPrinter == null){
                                    EpsonReceiptPrintMaster epsonReceiptPrint = new EpsonReceiptPrintMaster(MainActivity.mContext);
                                    epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType, Payment.jsonroot_kitchen, "N", "0");
                                    b_temp = true;
                                }else {
                                }
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } while (!b_temp);
                        }
                    }).start();

//                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);
//                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                    break;
                }
            }
        } else if (tempKindPrint.equals("1")){
            if (paramJsonroot == null) return;
            if (kitchenNum.equals("0")) return;
            switch (tempMasterPrinterName) {
                case "Epson-T88":{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean b_temp = false;
                            do {
                                if (EpsonReceiptPrintMaster.mPrinter == null){
                                    EpsonReceiptPrintMaster epsonReceiptPrint = new EpsonReceiptPrintMaster(MainActivity.mContext);
                                    epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,"master_kitchen", Payment.jsonroot_kitchen, "Y", kitchenNum);
                                    b_temp = true;
                                }else {
                                }
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } while (!b_temp);
                        }
                    }).start();

//                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);
//                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                    break;
                }
            }
        }
    }

    public static void printReceiptByJHP_menu(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {

        if (b_isMenuPrinted){
            return;
        }

        GlobalMemberValues.disconnectPrinter();

        // 이곳에서 프린트 연동을 하면된다.
        // 파라미터 paramJsonroot 가 세일즈 데이터를 가지고 있는 JSON 객체다
        // paramReceiptPrintType 타입에 따라서 구현하면 된다.
        if (paramContext == null) {
            paramContext = MainActivity.mContext;
        }

        Context context = paramContext;

        String tempPrinterName = getSavedPrinterName(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart();
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                PosBankPrinterStart posBankPrinterStart = new PosBankPrinterStart(paramContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart.setConnect();

                /**
                 switch (paramReceiptPrintType) {
                 case "batchsummary" : {
                 GlobalMemberValues.logWrite("inconnectmethod", "Batch & Summary 에서 클릭... : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

                 PrinterReceipt printerReceipt = new PrinterReceipt();
                 //                      printerReceipt.PrintBatchStarPrint(context,paramJsonroot);
                 printerReceipt.printBatchPosbank(paramJsonroot);
                 break;
                 }
                 case "payment" : {
                 GlobalMemberValues.logWrite("inconnectmethod", "결제버튼 클릭... : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

                 // 이곳에 결제시 프린트를 구현하면 된다....
                 PrinterReceipt printerReceipt = new PrinterReceipt();
                 //                      printerReceipt.PrintPaymentStarPrint(context,paramJsonroot);
                 printerReceipt.printSalesReceiptPosbank(paramJsonroot);

                 break;
                 }
                 }

                 **/
                break;
            }
            case "Elo" : {
                EloPrinterStart.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                if (paramJsonroot != null) {
                    GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                }
                CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                // jihun park add TODO
                if (paramJsonroot != null) {
                    GlobalMemberValues.logWrite("cloverprintinglog", "paramJsonroot : " + paramJsonroot.toString() + "\n");
                }
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext,paramJsonroot,paramReceiptPrintType);
                break;
            }
            case "Giant-100":{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connect(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88":{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);
                                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType, Payment.jsonroot_kitchen);
                                b_temp = true;
                            }else {
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } while (!b_temp);
                    }
                }).start();

//                EpsonReceiptPrint epsonReceiptPrint = new EpsonReceiptPrint(MainActivity.mContext);
//                epsonReceiptPrint.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }

            case "No Printer" : {
                if (paramReceiptPrintType.equals("payment")) {
                    int tempKitchenPrintingValue = GlobalMemberValues.getIntAtString(Payment.dbInit.dbExecuteReadReturnString(
                            "select kitchenprinting from salon_storestationsettings_deviceprinter2"));
                    if (!GlobalMemberValues.isCloudKitchenPrinter() && tempKitchenPrintingValue == 0) {
                        try {
                            Payment.jsonroot_kitchen.put("receiptfooter", GlobalMemberValues.getReceiptFooterKitchen2());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String tempSalesCode = "";
                        try {
                            tempSalesCode = Payment.jsonroot_kitchen.getString("receiptno");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                            GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                            // 주방프린트 하기 ----------------------------------------------------------------
                            GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                            // -------------------------------------------------------------------------------
                        } else {
                            if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {
                                GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "tempSalesCode : " + tempSalesCode + "\n");
                                GlobalMemberValues.logWrite("SALESCODEPRINTEDINKITCHENLOG", "SALESCODEPRINTEDINKITCHEN : " + GlobalMemberValues.SALESCODEPRINTEDINKITCHEN + "\n");

                                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                                        || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {
                                    GlobalMemberValues.SALESCODEPRINTEDINKITCHEN = tempSalesCode;
                                    // 주방프린트 하기 ----------------------------------------------------------------
                                    GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, MainActivity.mContext, "kitchen1");
                                    // -------------------------------------------------------------------------------
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        b_isMenuPrinted = true;
    }



    public synchronized static void printGateByKitchen(JSONObject paramJsonroot, Context paramContext, String paramReceiptPrintType) {
        // Restaurant 관련
        if (!GlobalMemberValues.now_iskitchenprinting) {
            return;
        }

        // 20230204
        // 230531 크래시 수정
        String temp_holecode = "";
        temp_holecode = paramJsonroot.optString("holdcode", "");
        // 230531 크래시 수정
        if (getSalonSalesDetail_KitchenPrintedYN(temp_holecode)){
            return;
        }
        if (getSalonSalesDetail_LabelPrintedYN(temp_holecode)){
            return;
        }
        // 20230204

        // table bill split pay 중에는 kitchen printing 안되도록.
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCodeForBillPay_on)) {
            return;
        }

        // 주방 프린팅이 클라우드가 아닌 로컬일 경우에만..
        if (!GlobalMemberValues.isCloudKitchenPrinter()) {
            if (paramJsonroot == null) {
                return;
            }
            String tempSalesCodeJ = GlobalMemberValues.getDataInJsonData(paramJsonroot, "receiptno");
            if (!GlobalMemberValues.isStrEmpty(tempSalesCodeJ)) {
                if (GlobalMemberValues.isKitchenPrintedOnSalonSales(tempSalesCodeJ)) {
                    return;
                }
            }
            GlobalMemberValues.logWrite("printGateByKitchenlog", "json : " + paramJsonroot.toString() + "\n");

            // 중복된 프린터인지 확인 ------------------------------------------------------------------------------------------
            String tempPrinterName[] = new String[5];
            tempPrinterName[0] = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
            tempPrinterName[1] = GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext);
            tempPrinterName[2] = GlobalMemberValues.getSavedPrinterNameForKitchen4(MainActivity.mContext);
            tempPrinterName[3] = GlobalMemberValues.getSavedPrinterNameForKitchen5(MainActivity.mContext);
            tempPrinterName[4] = GlobalMemberValues.getSavedPrinterNameForKitchen6(MainActivity.mContext);
            // 1115 싱글과 다른 부분 발견???
            String tempPrinterIpPort[] = new String[5];
            tempPrinterIpPort[0] = GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext) + ":" + GlobalMemberValues.getNetworkPrinterPort6(MainActivity.mContext);
            tempPrinterIpPort[1] = GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext) + ":" + GlobalMemberValues.getNetworkPrinterPort6(MainActivity.mContext);
            tempPrinterIpPort[2] = GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext) + ":" + GlobalMemberValues.getNetworkPrinterPort6(MainActivity.mContext);
            tempPrinterIpPort[3] = GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext) + ":" + GlobalMemberValues.getNetworkPrinterPort6(MainActivity.mContext);
            tempPrinterIpPort[4] = GlobalMemberValues.getNetworkPrinterIp6(MainActivity.mContext) + ":" + GlobalMemberValues.getNetworkPrinterPort6(MainActivity.mContext);

            boolean isDuplicatedPrinter[] = new boolean[tempPrinterName.length];
            for (int i = 0; i < isDuplicatedPrinter.length; i++) {
                isDuplicatedPrinter[i] = false;

                String tempP = tempPrinterName[i].toLowerCase();
                if (!GlobalMemberValues.isStrEmpty(tempP) && !tempP.equals("no printer")) {
                    if (!tempP.equals("elo") && !tempP.equals("pax")) {
                        for (int j = 0; j < tempPrinterIpPort.length; j++) {
                            if (tempPrinterIpPort[i] == tempPrinterIpPort[j]) {
                                isDuplicatedPrinter[i] = true;
                            }
                        }
                    }
                } else {
                    tempPrinterName[i] = "";
                }
            }
            // -------------------------------------------------------------------------------------------------------------------

            if (GlobalMemberValues.PHONEORDERYN.equals("Y") && GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT.equals("N")) {
                //GlobalMemberValues.initPhoneOrderValues();

                // salon_sales 테이블에 키친프린터 했다는 정보저장 --------------------------------
                String str_receiptno = "";
                String str_ordertype = "";
                try {
                    str_receiptno = paramJsonroot.getString("receiptno");
                    str_ordertype = paramJsonroot.getString("ordertype");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GlobalMemberValues.setKitchenPrintedChangeStatus(str_receiptno, str_ordertype);
                // ---------------------------------------------------------------------------

                if (tempPrinterName[0].equals("Elo")) {
                    if (GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO == "N") {
                        Payment.openPaymentReview(MainActivity.mContext);
                    }
                    GlobalMemberValues.KITCHENPRINT_FROM_PUSH_FOR_ELO = "N";
                }
            }

            // 소켓 프린팅일 경우
            if (GlobalMemberValues.isSocketKitchenPrinter()){
                try {
                    paramJsonroot.put("SCODE", GlobalMemberValues.SALON_CODE);
                    paramJsonroot.put("SIDX", GlobalMemberValues.STORE_INDEX);
                    paramJsonroot.put("STCODE", GlobalMemberValues.STATION_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                connectSocketServerToPrint(paramJsonroot);
                return;
            }

            // 블루투스 프린팅일 경우
            if (GlobalMemberValues.isBluetoothKitchenPrinter()) {
                try {
                    paramJsonroot.put("SCODE", GlobalMemberValues.SALON_CODE);
                    paramJsonroot.put("SIDX", GlobalMemberValues.STORE_INDEX);
                    paramJsonroot.put("STCODE", GlobalMemberValues.STATION_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String tempJsonData = paramJsonroot.toString();

                bluetooth_sendMessage(tempJsonData);

                return;
            }

            // 프린팅 전용앱으로 프린팅할 경우
            if (GlobalMemberValues.isUseOtherPrintingAppInKitchenPrinting()) {
                try {
                    paramJsonroot.put("SCODE", GlobalMemberValues.SALON_CODE);
                    paramJsonroot.put("SIDX", GlobalMemberValues.STORE_INDEX);
                    paramJsonroot.put("STCODE", GlobalMemberValues.STATION_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String tempJsonData = paramJsonroot.toString();

                Intent intent = new Intent("com.example.jjbfather.jjjprinter.server");
//                    intent.putExtra(JJJ_BroadcastReceiver.KEY_DATE, "com.example.jjbfather.jjjprinter.server");
                intent.putExtra("jsondata", tempJsonData);
                paramContext.sendBroadcast(intent);

                // 키친 프린팅 데이터를 데이터베이스 저장
//                    boolean saveResult = GlobalMemberValues.saveKitchenPrintingDataFromOtherApp(paramContext, tempScode, tempSidx, tempStcode, tempSalesCode, paramJsonroot.toString());

                return;
            }

            GlobalMemberValues.logWrite("recallkitchenprintlog", "GlobalMemberValues.PHONEORDERYN : " + GlobalMemberValues.PHONEORDERYN + "\n");
            GlobalMemberValues.logWrite("recallkitchenprintlog", "GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT : " + GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT + "\n");

            // Phone Order 를 hold 하면서 키친프린터를 했을 경우에는 키친프린터를 실행하지 않는다.
            if (GlobalMemberValues.PHONEORDERYN.equals("Y") && GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT.equals("N")) {
                GlobalMemberValues.initPhoneOrderValues();
            } else {
                for (int i = 0; i < tempPrinterName.length; i++) {
                    if (!GlobalMemberValues.isStrEmpty(tempPrinterName[i])) {

                        // 중복등록된 프린터일 경우 1초정도 딜레이를 준다.
                        if (isDuplicatedPrinter[i]) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                            }
                        }

                        if (GlobalMemberValues.PHONEORDERYN.equals("Y") || GlobalMemberValues.mRekitchenprintYN.equals("Y")) {
                            // 폰 오더일 경우
                        } else {
                            if (GlobalMemberValues.isCustomerSelectReceipt()) {
                                String tempSalesCode = "";
                                try {
                                    if (Payment.jsonroot_kitchen != null) {
                                        tempSalesCode = Payment.jsonroot_kitchen.getString("receiptno");
                                    } else {
                                        tempSalesCode = paramJsonroot.getString("receiptno");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)
                                        || !tempSalesCode.equals(GlobalMemberValues.SALESCODEPRINTEDINKITCHEN)) {

                                } else {
                                    if (GlobalMemberValues.isSamePrinterInReceiptAndKitchen()) {

                                    } else {
                                        return;
                                    }
                                }
                            }
                        }


                        switch (i + "JJJ") {
                            case "0JJJ" : {
                                if (GlobalMemberValues.isPossibleKitchenPrinting(paramJsonroot, "1")) {
                                    GlobalMemberValues.printReceiptByKitchen1(paramJsonroot, paramContext, "kitchen1");
                                    GlobalMemberValues.logWrite("possibleprintlog", "여기1" + "\n");
                                }

                                break;
                            }
                            case "1JJJ" : {
                                if (GlobalMemberValues.isPossibleKitchenPrinting(paramJsonroot, "2")) {
                                    GlobalMemberValues.printReceiptByKitchen2(paramJsonroot, paramContext, "kitchen2");
                                    GlobalMemberValues.logWrite("possibleprintlog", "여기2" + "\n");
                                }

                                break;
                            }
                            case "2JJJ" : {
                                if (GlobalMemberValues.isPossibleKitchenPrinting(paramJsonroot, "3")) {
                                    GlobalMemberValues.printReceiptByKitchen3(paramJsonroot, paramContext, "kitchen3");
                                    GlobalMemberValues.logWrite("possibleprintlog", "여기3" + "\n");
                                }

                                break;
                            }
                            case "3JJJ" : {
                                if (GlobalMemberValues.isPossibleKitchenPrinting(paramJsonroot, "4")) {
                                    GlobalMemberValues.printReceiptByKitchen4(paramJsonroot, paramContext, "kitchen4");
                                    GlobalMemberValues.logWrite("possibleprintlog", "여기4" + "\n");
                                }

                                break;
                            }
                            case "4JJJ" : {
                                if (GlobalMemberValues.isPossibleKitchenPrinting(paramJsonroot, "5")) {
                                    GlobalMemberValues.printReceiptByKitchen5(paramJsonroot, paramContext, "kitchen5");
                                    GlobalMemberValues.logWrite("possibleprintlog", "여기5" + "\n");
                                }

                                break;
                            }
                        }
                        if (getUseYNMasterPrinter(MainActivity.mContext).equals("1")){
                            String tempkitchenNum = String.valueOf(i + 1);
                            String tempKindPrint = getSavedMasterPrinterKind(MainActivity.mContext);    // 0, 1, 2
                            if (tempKindPrint.equals("1")) GlobalMemberValues.printMaster(paramJsonroot, paramContext, "master_kitchen", tempkitchenNum);
                        }

                    }
                }
            }
        }
    }

    public synchronized static void printReceiptByKitchen1(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        // 주방 프린팅이 클라우드가 아닌 로컬일 경우에만..
        if (!GlobalMemberValues.isCloudKitchenPrinter()) {
            if (paramJsonroot == null) {
                //return;
            } else {
                GlobalMemberValues.logWrite("jjjkitchenprinterlog", "json1 : " + paramJsonroot.toString() + "\n");
            }
            //GlobalMemberValues.allDisconnectKitchenPrinter();
            GlobalMemberValues.disconnectPrinter2();

            String tempPrinterName = getSavedPrinterNameForKitchen2(MainActivity.mContext);

            switch (tempPrinterName) {
                case "STAR" : {
                    StarPrintStart starPrintStart = new StarPrintStart(paramReceiptPrintType);
                    starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                    break;
                }
                case "PosBank" : {
                    PosBankPrinterStart2 posBankPrinterStart2 = new PosBankPrinterStart2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                    posBankPrinterStart2.setConnect();
                    break;
                }
                case "Elo" : {
                    EloPrinterStart2.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                    break;
                }
                case "Clover Station" : {
                    CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                    break;
                }
                case "Clover Mini" : {
                    CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                    break;
                }
                case "PAX" : {
                    PaxPrinterStart.paxPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                    break;
                }
                case "Giant-100" :{
                    Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                    giantPrinter.connectKitchen1(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                    break;
                }
                case "Epson-T88" :{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean b_temp = false;
                            do {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (    EpsonReceiptPrint.mPrinter == null &&
                                        EpsonPrinterKitchen1.mPrinter == null &&
                                        EpsonPrinterKitchen2.mPrinter == null &&
                                        EpsonPrinterKitchen3.mPrinter == null &&
                                        EpsonPrinterKitchen4.mPrinter == null &&
                                        EpsonPrinterKitchen5.mPrinter == null)
                                {
                                    EpsonPrinterKitchen1 epsonPrinterKitchen1 = new EpsonPrinterKitchen1(MainActivity.mContext);
                                    epsonPrinterKitchen1.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                                    b_temp = true;
                                }else {
                                }
                            } while (!b_temp);
                        }
                    }).start();
//                    EpsonPrinterKitchen1 epsonPrinterKitchen1 = new EpsonPrinterKitchen1(MainActivity.mContext);
//                    epsonPrinterKitchen1.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                    break;
                }
            }
        }
    }

    public synchronized static void printReceiptByKitchen2(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        //GlobalMemberValues.allDisconnectKitchenPrinter();
        if (paramJsonroot == null) {
            //return;
        } else {
            GlobalMemberValues.logWrite("jjjkitchenprinterlog", "json2 : " + paramJsonroot.toString() + "\n");
        }
        GlobalMemberValues.disconnectPrinter3();

        String tempPrinterName = getSavedPrinterNameForKitchen3(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart(paramReceiptPrintType);
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog", "printReceiptByKitchen2 들어옴" + "\n");
                PosBankPrinterStart2 posBankPrinterStart2 = new PosBankPrinterStart2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart2.setConnect();
                break;
            }
            case "Elo" : {
                EloPrinterStart2.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Giant-100" :{
                GlobalMemberValues.logWrite("jjjkitchenprinterlog", "여기왔습니다..." + "\n");
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connectKitchen2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88" :{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonPrinterKitchen2 epsonPrinterKitchen2 = new EpsonPrinterKitchen2(MainActivity.mContext);
                                epsonPrinterKitchen2.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                                b_temp = true;
                            }else {
                            }
                        } while (!b_temp);
                    }
                }).start();
//                EpsonPrinterKitchen2 epsonPrinterKitchen2 = new EpsonPrinterKitchen2(MainActivity.mContext);
//                epsonPrinterKitchen2.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }
        }
    }

    public synchronized static void printReceiptByKitchen3(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        if (paramJsonroot == null) {
            //return;
        } else {
            GlobalMemberValues.logWrite("jjjkitchenprinterlog", "json3 : " + paramJsonroot.toString() + "\n");
        }
        GlobalMemberValues.disconnectPrinter4();

        String tempPrinterName = getSavedPrinterNameForKitchen4(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart(paramReceiptPrintType);
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog", "printReceiptByKitchen2 들어옴" + "\n");
                PosBankPrinterStart2 posBankPrinterStart2 = new PosBankPrinterStart2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart2.setConnect();
                break;
            }
            case "Elo" : {
                EloPrinterStart2.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Giant-100" :{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connectKitchen3(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88" :{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonPrinterKitchen3 epsonPrinterKitchen3 = new EpsonPrinterKitchen3(MainActivity.mContext);
                                epsonPrinterKitchen3.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                                b_temp = true;
                            }else {
                            }
                        } while (!b_temp);
                    }
                }).start();
//                EpsonPrinterKitchen3 epsonPrinterKitchen3 = new EpsonPrinterKitchen3(MainActivity.mContext);
//                epsonPrinterKitchen3.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }
        }
    }

    public synchronized static void printReceiptByKitchen4(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        //GlobalMemberValues.allDisconnectKitchenPrinter();
        if (paramJsonroot == null) {
            //return;
        } else {
            GlobalMemberValues.logWrite("jjjkitchenprinterlog", "json4 : " + paramJsonroot.toString() + "\n");
        }
        GlobalMemberValues.disconnectPrinter5();

        String tempPrinterName = getSavedPrinterNameForKitchen5(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart(paramReceiptPrintType);
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog", "printReceiptByKitchen2 들어옴" + "\n");
                PosBankPrinterStart2 posBankPrinterStart2 = new PosBankPrinterStart2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart2.setConnect();
                break;
            }
            case "Elo" : {
                EloPrinterStart2.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Giant-100" :{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connectKitchen4(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88" :{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonPrinterKitchen4 epsonPrinterKitchen4 = new EpsonPrinterKitchen4(MainActivity.mContext);
                                epsonPrinterKitchen4.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                                b_temp = true;
                            }else {
                            }
                        } while (!b_temp);
                    }
                }).start();
//                EpsonPrinterKitchen4 epsonPrinterKitchen4 = new EpsonPrinterKitchen4(MainActivity.mContext);
//                epsonPrinterKitchen4.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }
        }
    }

    public synchronized static void printReceiptByKitchen5(final JSONObject paramJsonroot, Context paramContext, final String paramReceiptPrintType) {
        //GlobalMemberValues.allDisconnectKitchenPrinter();
        if (paramJsonroot == null) {
            //return;
        } else {
            GlobalMemberValues.logWrite("jjjkitchenprinterlog", "json5 : " + paramJsonroot.toString() + "\n");
        }
        GlobalMemberValues.disconnectPrinter6();

        String tempPrinterName = getSavedPrinterNameForKitchen6(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR" : {
                StarPrintStart starPrintStart = new StarPrintStart(paramReceiptPrintType);
                starPrintStart.run(paramReceiptPrintType,paramJsonroot);
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.logWrite("secondkitchenlog", "printReceiptByKitchen2 들어옴" + "\n");
                PosBankPrinterStart2 posBankPrinterStart2 = new PosBankPrinterStart2(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                posBankPrinterStart2.setConnect();
                break;
            }
            case "Elo" : {
                EloPrinterStart2.eloPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);

                break;
            }
            case "Clover Station" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "station", paramReceiptPrintType);
                break;
            }
            case "Clover Mini" : {
                CloverPrinterStart2.cloverPrintSwitch(paramContext, paramJsonroot, "mini", paramReceiptPrintType);
                break;
            }
            case "PAX" : {
                PaxPrinterStart.paxPrintSwitch(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Giant-100" :{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connectKitchen5(MainActivity.mContext, paramJsonroot, paramReceiptPrintType);
                break;
            }
            case "Epson-T88" :{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b_temp = false;
                        do {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (    EpsonReceiptPrint.mPrinter == null &&
                                    EpsonPrinterKitchen1.mPrinter == null &&
                                    EpsonPrinterKitchen2.mPrinter == null &&
                                    EpsonPrinterKitchen3.mPrinter == null &&
                                    EpsonPrinterKitchen4.mPrinter == null &&
                                    EpsonPrinterKitchen5.mPrinter == null)
                            {
                                EpsonPrinterKitchen5 epsonPrinterKitchen5 = new EpsonPrinterKitchen5(MainActivity.mContext);
                                epsonPrinterKitchen5.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                                b_temp = true;
                            }else {
                            }
                        } while (!b_temp);
                    }
                }).start();
//                EpsonPrinterKitchen5 epsonPrinterKitchen5 = new EpsonPrinterKitchen5(MainActivity.mContext);
//                epsonPrinterKitchen5.runPrintReceiptSequence(paramJsonroot,paramReceiptPrintType);
                break;
            }
        }
    }

    // API 를 이용해 Cloud 로 App Instance ID 전송 메소드
    public static String uploadAppInstanceId(Context paramContext, String strParams) {
        String returnValue = "";
        if (!isStrEmpty(strParams)) {
            API_appinstanceidupload_tocloud apiAppInstanceIdIns
                    = new API_appinstanceidupload_tocloud(paramContext, strParams);
            apiAppInstanceIdIns.execute(null, null, null);
            try {
                Thread.sleep(API_THREAD_TIME); //1초마다 실행
                if (apiAppInstanceIdIns.mFlag) {
                    returnValue = apiAppInstanceIdIns.mReturnValue;
                }
            } catch (InterruptedException e) {
                //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
            }
        } else {
            displayDialog(paramContext, "Waraning", "No App Instance ID Information", "Close");
        }

        return returnValue;
    }

    // API 로 AppInstanceId 를 전송하여 등록하는 메소드
    public static void sendAppInstanceIdByApi(Context paramContext, String paramAppInstanceId) {
        GlobalMemberValues.logWrite("AppInstanceIDUp", "APPINSTANCEIDUP : " + GlobalMemberValues.APPINSTANCEIDUP + "\n");
        // 클라우드에 App Instance ID 전송안됐을 경우에만 (GlobalMemberValues.APPINSTANCEIDUP 값이 0 일경우)
        if (GlobalMemberValues.APPINSTANCEIDUP == 0) {
            // API 로 Instance ID 전송 ---------------------------------------------------------------------
            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                String strParams = "";
                strParams = "sidx=" + GlobalMemberValues.STORE_INDEX +
                        "&stcode=" + GlobalMemberValues.STATION_CODE.toUpperCase() +
                        "&appinstanceid=" + paramAppInstanceId;
                GlobalMemberValues.logWrite("sendAppInstanceIdByApi", "strParams : " + strParams + "\n");
                String returnValue = "";
                returnValue = GlobalMemberValues.uploadAppInstanceId(paramContext, strParams);
                GlobalMemberValues.logWrite("sendAppInstanceIdByApi", "returnValue : " + returnValue + "\n");
                if (GlobalMemberValues.isStrEmpty(returnValue)) {
                    //GlobalMemberValues.displayDialog(paramContext, "Warning", "Error", "Close");
                    //Toast.makeText(paramContext, "App Instance ID Upload Error", Toast.LENGTH_SHORT).show();
                } else {
                    switch (returnValue) {
                        case "success" : {
                            //Toast.makeText(paramContext, "App Instance ID is Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            GlobalMemberValues.APPINSTANCEIDUP = 1;
                            break;
                        }
                        case "noemp" : {
                            //Toast.makeText(paramContext, "No Employee Information", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "nostore" : {
                            //Toast.makeText(paramContext, "No Store Information", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "nostation" : {
                            //Toast.makeText(paramContext, "No Station Informatior", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "dberror" : {
                            //Toast.makeText(paramContext, "Cloud Upload Error", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            } else {
                //Toast.makeText(paramContext, "Network is not connection", Toast.LENGTH_SHORT).show();
            }
            // ----------------------------------------------------------------------------------------------
        }
    }

    public static boolean isServiceRunningCheck(Context paramContext, String paramPackageName) {
        boolean returnValue = false;
        ActivityManager manager = (ActivityManager)paramContext.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            GlobalMemberValues.logWrite("ServiceActiveCheck", "실행중인 앱 : " + service.service.getClassName() + "\n");
            if (paramPackageName.equals(service.service.getClassName())) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public static String getItemTaxFreeYN(Context paramContext, String paramItemType, String paramItemIdx) {
        String tempTaxFreeYN = "N";
        try {
            DatabaseInit dbInit = new DatabaseInit(paramContext);   // DatabaseInit 객체 생성
            String strQuery = "";
            switch (paramItemType) {
                case "S" : {        // 서비스
                    strQuery = "select taxfreeyn from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
                    break;
                }
                case "P" : {        // 상품
                    strQuery = "select taxfreeyn from salon_storeproduct where idx = '" + paramItemIdx + "' ";
                    break;
                }
            }
            GlobalMemberValues.logWrite("getItemTaxFreeYN", "strQuery : " + strQuery + "\n");
            if (!GlobalMemberValues.isStrEmpty(strQuery)) {
                tempTaxFreeYN = dbInit.dbExecuteReadReturnString(strQuery);
            }
            if (GlobalMemberValues.isStrEmpty(tempTaxFreeYN)) {
                tempTaxFreeYN = "N";
            }

            GlobalMemberValues.logWrite("getItemTaxFreeYN", "taxfree : " + tempTaxFreeYN + "\n");
        } catch (Exception e) {
        }

        return tempTaxFreeYN;
    }

    public static String getItemTaxFreeYNForQuickSale(Context paramContext, String paramCategoryIdx) {
        String tempTaxFreeYN = "N";
        try {
            if (GlobalMemberValues.isStrEmpty(paramCategoryIdx)) {
                tempTaxFreeYN = "N";
            } else {
                DatabaseInit dbInit = new DatabaseInit(paramContext);   // DatabaseInit 객체 생성
                String strQuery = "select taxfreeyn from salon_storeservice_main where idx = '" + paramCategoryIdx + "' ";
                GlobalMemberValues.logWrite("getItemTaxFreeYNForQuickSale", "strQuery : " + strQuery + "\n");
                if (!GlobalMemberValues.isStrEmpty(strQuery)) {
                    tempTaxFreeYN = dbInit.dbExecuteReadReturnString(strQuery);
                }
                if (GlobalMemberValues.isStrEmpty(tempTaxFreeYN)) {
                    tempTaxFreeYN = "N";
                }

                GlobalMemberValues.logWrite("getItemTaxFreeYNForQuickSale", "taxfree : " + tempTaxFreeYN + "\n");
            }
        } catch (Exception e) {
        }

        return tempTaxFreeYN;
    }

    public static void setTextViewColorPartial(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void setFinishLoadingIntent() {
        if (LoadingIntent.ldProDial != null) {
            LoadingIntent.ldProDial.dismiss();
        }
        if (LoadingIntent.mActivity != null) {
            LoadingIntent loadingIntent = (LoadingIntent) LoadingIntent.mActivity;
            loadingIntent.finish();

            LoadingIntent.mActivity.finish();
        }
    }

    public static void setCursorLastDigit(EditText paramEditText) {
        paramEditText.setSelection(paramEditText.length());
    }

    public static String getCheckCloudBackupYN(String paramStcode) {
        String returnValue = "N";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(paramStcode)) {
                API_cloudbackup_check apicheckInstance = new API_cloudbackup_check(paramStcode);
                apicheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apicheckInstance.mFlag) {
                        returnValue = apicheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            } else {
                returnValue = "N";
            }
        } else {
            returnValue = "N";
        }
        return returnValue;
    }

    public static void openNetworkNotConnected() {
        Intent cloudWebPageAtNotConnectionIntent = new Intent(MainActivity.mContext.getApplicationContext(), CloudWebPageAtNotConnection.class);
        MainActivity.mActivity.startActivity(cloudWebPageAtNotConnectionIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public static void setConnectPrinterDevice(Context paramContext, String paramPrinterName, String paramParent, boolean paramDialog) {
        if (!GlobalMemberValues.isStrEmpty(paramPrinterName)) {
            switch (paramPrinterName) {
                case "PosBank" : {
                    PosbankPrinter posbankPrinter = new PosbankPrinter(paramContext, paramParent);
                    posbankPrinter.setConnect(paramDialog);
                    break;
                }
            }
        }
    }

    // 프린터 연결상태 유무에 상관없이 무조건 연결시도
    public static void setConnectPrinterDeviceForce(Context paramContext, String paramPrinterName, String paramParent, boolean paramDialog) {
        if (!GlobalMemberValues.isStrEmpty(paramPrinterName)) {
            switch (paramPrinterName) {
                case "PosBank" : {
                    PosbankPrinter posbankPrinter = new PosbankPrinter(paramContext, paramParent);
                    posbankPrinter.setConnectForce();
                    break;
                }
            }
        }
    }

    public static String getSavedPrinterName(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter";
        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedMasterPrinterName(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter_master";
        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedMasterPrinterKind(Context paramContext) {
        String tempPrinterKind = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printreceiptkind from salon_storestationsettings_deviceprinter_master";
        if (MainActivity.mDbInit == null){
            tempPrinterKind = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterKind = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterKind;
    }

    public static String getSavedPrinterNameForKitchen2(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter2";

        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedPrinterNameForKitchen3(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter3";

        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedPrinterNameForKitchen4(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter4";

        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedPrinterNameForKitchen5(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter5";
        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getSavedPrinterNameForKitchen6(Context paramContext) {
        String tempPrinterName = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter6";
        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static String getNetworkPrinterIp(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIpMaster(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter_master";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIp2(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter2";
        Cursor settingsPrinterCursor = dbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIp3(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter3";
        Cursor settingsPrinterCursor = dbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIp4(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter4";
        Cursor settingsPrinterCursor = dbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIp5(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter5";
        Cursor settingsPrinterCursor = dbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }

    public static String getNetworkPrinterIp6(Context paramContext) {
        String tempPrinterIp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4 from salon_storestationsettings_deviceprinter6";
        Cursor settingsPrinterCursor = dbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(1), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(2), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(3), 1);

            tempPrinterIp = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
        }

        return tempPrinterIp;
    }


    public static String getNetworkPrinterPort(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }


    public static String getNetworkPrinterPort2(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter2 ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }

    public static String getNetworkPrinterPort3(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter3 ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }

    public static String getNetworkPrinterPort4(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter4 ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }

    public static String getNetworkPrinterPort5(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter5 ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }

    public static String getNetworkPrinterPort6(Context paramContext) {
        String tempPrinterPort = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select networkprinterport from salon_storestationsettings_deviceprinter6 ";
        Cursor settingsPrinterCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsPrinterCursor.getCount() > 0 && settingsPrinterCursor.moveToFirst()) {
            String tempPort = GlobalMemberValues.getDBTextAfterChecked(settingsPrinterCursor.getString(0), 1);

            tempPrinterPort = tempPort;
        }

        return tempPrinterPort;
    }


    public static final void logWrite(String TAG, String message) {
        Log.i(TAG, message + "\n");
    }

    public static String checkGiftCardBalance(Context paramContext, String paramGiftCardNumber) {
        String returnValue = "0.00";

        DatabaseInit dbinit = new DatabaseInit(paramContext);
        String tempSql = "select remainingPrice from salon_storegiftcard_number " +
                " where gcNumber = '" + paramGiftCardNumber + "' ";
        String tempNowRemainingPrice = dbinit.dbExecuteReadReturnString(tempSql);
        //GlobalMemberValues.logWrite("giftcardNumber", "잔액 : " + tempNowRemainingPrice + "\n");
        if (!GlobalMemberValues.isStrEmpty(tempNowRemainingPrice)) {
            returnValue = GlobalMemberValues.getCommaStringForDouble(tempNowRemainingPrice);
        }

        return returnValue;
    }

    public static String checkGiftCardUsedAmount(Context paramContext, String paramGiftCardNumber) {
        String returnValue = "0.00";

        DatabaseInit dbinit = new DatabaseInit(paramContext);
        String tempSql = "select sum(addUsePrice) from salon_storegiftcard_number_history " +
                " where gcNumber = '" + paramGiftCardNumber + "' and addUseType = 'U' ";
        String tempGiftCardUsedAmount = dbinit.dbExecuteReadReturnString(tempSql);
        //GlobalMemberValues.logWrite("giftcardNumber", "사용액 : " + tempGiftCardUsedAmount + "\n");
        if (!GlobalMemberValues.isStrEmpty(tempGiftCardUsedAmount)) {
            returnValue = GlobalMemberValues.getCommaStringForDouble(tempGiftCardUsedAmount);
        }

        return returnValue;
    }

    public static void setUseableOrNotEditText(EditText et, boolean useable) {
        et.setClickable(useable);
        et.setEnabled(useable);
        et.setFocusable(useable);
        et.setFocusableInTouchMode(useable);
    }

    public static void setCustomerSelected(TemporaryCustomerInfo selectedItemCustomerInfo) {
        // 글로벌 static 고객정보 변수에 담는다.
        GlobalMemberValues.GLOBAL_CUSTOMERINFO = selectedItemCustomerInfo;

        // 메인 좌측 상단 고객정보 TextView 에 표기한다.
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextColor(Color.parseColor("#3e3d42"));
        // jihun 0731
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextSize(16);
//        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextSize(GlobalMemberValues.globalAddFontSize());

//        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
//                + GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.getTextSize() * GlobalMemberValues.getGlobalFontSize()
//        );

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setText(selectedItemCustomerInfo.memName);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setTextColor(Color.parseColor("#9396a6"));
        // jihun 0731
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setTextSize(14);
//        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
//        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setText("Phone # : " + selectedItemCustomerInfo.memPhone);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE.setText(selectedItemCustomerInfo.memPhone);

        // 하단 고객정보에 넣기 전에 먼저 초기화한다.
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_LASTVISIT.setText("Last Visit : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("Point : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_DOB.setText("DOB : ");
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE.setText("Note : ");

        // 하단 고객정보에 넣는다.
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_LASTVISIT.append(" " + selectedItemCustomerInfo.memLastvisitForSale);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.append(" " + GlobalMemberValues.getCommaStringForDouble(selectedItemCustomerInfo.memMileage));
        String memDOB = "";                            // 생일정보 조합
        if (!GlobalMemberValues.isStrEmpty(selectedItemCustomerInfo.memBMonth)
                && !GlobalMemberValues.isStrEmpty(selectedItemCustomerInfo.memBday)
                && !GlobalMemberValues.isStrEmpty(selectedItemCustomerInfo.memBYear)) {
            memDOB = selectedItemCustomerInfo.memBMonth + "/" +
                    selectedItemCustomerInfo.memBday + "/" + selectedItemCustomerInfo.memBYear;
        }
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_DOB.append(" " + memDOB);
        //GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE.append(" " + selectedItemCustomerInfo.memMemo);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE.append(" " + customerLastMemo(selectedItemCustomerInfo.memUid));

        // 고객명 배경에 색상넣고, 고객선택 버튼을 고객메모 버튼으로 변경
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setBackgroundResource(R.drawable.roundlayout_selectedcustomer);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setBackgroundResource(R.drawable.ab_imagebutton_main_custmemo);
    }


    public static void showDialogNoInternet(Context paramContext) {
        displayDialog(paramContext, "Warning", "Although connected to the network, " +
                "Internet is not connected\nCheck your internet connection", "Close");
    }

    public static String isOnline() {
        String returnValue = "00";

        API_serverconnection_check apicheckInstance = new API_serverconnection_check();
        apicheckInstance.execute(null, null, null);
        try {
            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
            if (apicheckInstance.mFlag) {
                returnValue = apicheckInstance.mReturnValue;
            }
        } catch (InterruptedException e) {
            returnValue = "";
        }

        GlobalMemberValues.logWrite("NetworkCheckAPI", "returnValue : " + returnValue + "\n");
        return returnValue;
    }

    public static String isOnline2() {
        String returnValue = "99";

        API_serverconnection_check apicheckInstance = new API_serverconnection_check();
        apicheckInstance.execute(null, null, null);
        try {
            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
            if (apicheckInstance.mFlag) {
                returnValue = apicheckInstance.mReturnValue;
            }
        } catch (InterruptedException e) {
            returnValue = "";
        }

        GlobalMemberValues.logWrite("NetworkCheckAPI2", "returnValue : " + returnValue + "\n");
        return returnValue;
    }

    public static String getDataAfterMemberCheck(Context context, String paramUid) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = "";
            } else {
                API_member_check apicheckInstance = new API_member_check(paramUid);
                apicheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apicheckInstance.mFlag) {
                        returnValue = apicheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    returnValue = "";
                }

                GlobalMemberValues.logWrite("MemberCheckAPI", "returnValue : " + returnValue + "\n");
            }
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    public static String getDataAfterMemberShipCardNumberCheck(Context context, String paramMemUid, String paramMemCardNum) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = "";
            } else {
                GlobalMemberValues.logWrite("MembershipCardNumberCheckAPI", "paramMemUid : " + paramMemUid + "\n");
                GlobalMemberValues.logWrite("MembershipCardNumberCheckAPI", "paramMemCardNum : " + paramMemCardNum + "\n");
                if (!GlobalMemberValues.isStrEmpty(paramMemUid) && !GlobalMemberValues.isStrEmpty(paramMemCardNum)) {
                    API_membershipcardnumber_check apicheckInstance = new API_membershipcardnumber_check(paramMemUid, paramMemCardNum);
                    apicheckInstance.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME * 2);       // 2초마다 실행
                        if (apicheckInstance.mFlag) {
                            returnValue = apicheckInstance.mReturnValue;
                        }
                    } catch (InterruptedException e) {
                        returnValue = "";
                    }

                    GlobalMemberValues.logWrite("MembershipCardNumberCheckAPI", "returnValue : " + returnValue + "\n");
                }
            }
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    public static String getDataAfterMemberCheckForDeliveryTakeaway(Context context, String paramUid) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = "";
            } else {
                API_member_check_for_deliverytakeaway apicheckInstance = new API_member_check_for_deliverytakeaway(paramUid);
                apicheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apicheckInstance.mFlag) {
                        returnValue = apicheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    returnValue = "";
                }

                GlobalMemberValues.logWrite("getDataAfterMemberCheckForDeliveryTakeaway", "returnValue : " + returnValue + "\n");
            }
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    public static String getAppVersionName(Context paramContext) {
        String returnValue = "";
        try {
            returnValue = paramContext.getApplicationContext().getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public static String getAppVersionCode() {
        String returnValue = "";
        try {
            returnValue = MainActivity.mContext.getApplicationContext().getPackageManager().getPackageInfo(MainActivity.mContext.getPackageName(), 0).versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public static int saveBackupLog() {
        int returnValue = 0;

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String returnResult = "";
        int maxNum = 0;
        // 먼저 최종 num 값을 구한다.
        int lastNum = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                "select num from clouddbbackup_log order by idx desc"
        ));
        maxNum = lastNum + 1;

        Vector<String> strInsertQueryVec = new Vector<String>();
        String strQuery = " insert into clouddbbackup_log ( " +
                " sidx, stcode, num " +
                " ) values ( " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " '" + maxNum + "' " +
                " ) ";
        strInsertQueryVec.addElement(strQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("clouddbbackuplog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            returnValue = 0;
        } else {
            returnValue = maxNum;
        }

        return returnValue;
    }

    public static String getEncoderUtf8(String paramString) {
        String returnValue = "";
        if (!isStrEmpty(paramString)) {
            try {
                returnValue = java.net.URLEncoder.encode(new String(paramString.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                GlobalMemberValues.logWrite("getEncoderUtf8", "error msg : " + e.getMessage().toString() + "\n");
                e.printStackTrace();
            }
        }

        return returnValue;
    }

    public static void openCustomerSearch() {
        // 선택고객 초기화
        setCustomerInfoInit();
        // 고객선택창 오픈
        setFrameLayoutVisibleChange("customerSearch");
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(MainActivity.mContext);
        CustomerSearch custSearch = new CustomerSearch(MainActivity.mActivity, MainActivity.mContext, dataAtSqlite);
        custSearch.setCustomerInfo("", "", "");
    }

    public static void openQRCodeReader(Activity paramActivity, Context paramContext, int paramResultFinal) {
        //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        Intent intent = new Intent(paramContext, QRCodeScan.class );
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        paramActivity.startActivityForResult(intent, paramResultFinal);
    }

    public static String customerLastMemo(String paramUid) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramUid)) {
            String strQuery = "select memo from member_memo " +
                    " where uid = '" + GlobalMemberValues.getDBTextAfterChecked(paramUid, 0) + "' " +
                    " order by wdate desc limit 1 ";
            String lastMemo = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
            if (!GlobalMemberValues.isStrEmpty(lastMemo)) {
                returnValue = lastMemo;
            }
        }
        return returnValue;
    }


    public static int getSizeToString(String str) {

        int su = 0;
        int en = 0;
        int ko = 0;
        int etc = 0;

        char[] tempChar = str.toCharArray();

        /**
         for(int i = 0; i < tempChar.length; i ++) {
         if(tempChar[i] >= 'A' && tempChar[i] <= 'z') {
         en++;
         }else if(tempChar[i] >= '\uAC00' && tempChar[i] <= '\uD7A3') {
         ko++;
         ko++;
         }else{
         etc++;
         }
         }
         **/

        for(int i = 0; i < tempChar.length; i ++) {
            char ch = tempChar[i];

            if (ch >= '0' && ch <= '9') {
                su++;
            } else if (ch >= 'a' && ch <= 'z') {
                en++;
            } else if (ch >= 'A' && ch <= 'Z') {
                en++;
            } else if (ch >= '가' && ch <= '힣') {
                ko++;
                ko++;
            } else {
                etc++;
            }
        }

        return (su + en + ko + etc);
    }

    public static String getJJJSubString(String strData, int iStartPos, int iByteLength) {
        byte[] bytTemp = null;
        int iRealStart = 0;
        int iRealEnd = 0;
        int iLength = 0;
        int iChar = 0;

        try {
            // UTF-8로 변환하는경우 한글 2Byte, 기타 1Byte로 떨어짐
            bytTemp = strData.getBytes("EUC-KR");
            iLength = bytTemp.length;

            for(int iIndex = 0; iIndex < iLength; iIndex++) {
                if(iStartPos <= iIndex) {
                    break;
                }
                iChar = (int)bytTemp[iIndex];
                if((iChar > 127)|| (iChar < 0)) {
                    // 한글의 경우(2byte 통과처리)
                    // 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
                    iRealStart++;
                    iIndex++;
                } else {
                    // 기타 글씨(1Byte 통과처리)
                    iRealStart++;
                }
            }

            iRealEnd = iRealStart;
            int iEndLength = iRealStart + iByteLength;
            for(int iIndex = iRealStart; iIndex < iEndLength; iIndex++)
            {
                iChar = (int)bytTemp[iIndex];
                if((iChar > 127)|| (iChar < 0)) {
                    // 한글의 경우(2byte 통과처리)
                    // 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
                    iRealEnd++;
                    iIndex++;
                } else {
                    // 기타 글씨(1Byte 통과처리)
                    iRealEnd++;
                }
            }
        } catch(Exception e) {
            //
            Log.d("DEBUG",e.getMessage());
        }

        return strData.substring(iRealStart, iRealEnd);
    }

    public static String getDecodeString(String paramString) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramString)) {
            try {
                paramString = GlobalMemberValues.getEncodingTxt(paramString);

                returnValue = URLDecoder.decode(paramString, "UTF-8");

                returnValue = GlobalMemberValues.getDecodingTxt(returnValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }

    public static void posbankPrintText(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void posbankPrintText2(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter2.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void posbankPrintText3(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter3.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void posbankPrintText4(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter4.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void posbankPrintText5(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter5.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void posbankPrintText6(String text, int alignment, int attribute, int size, boolean getResponse) {
        PosbankPrinter6.mPrinter.printText(text, alignment, attribute, size, PRINTLOCALELANG, getResponse);
    }

    public static void eloOpenDrawer(ApiAdapter apiAdapter) {
        try {
            apiAdapter.openCashDrawer();
        } catch (RuntimeException e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public static void cloverOpenDrawer() {

    }

    public static void paxOpenDrawer() {
        ProcessResult result = POSLinkCashDrawer.getInstance(MainActivity.mContext).open();
        if (!result.getCode().equals(ProcessResult.CODE_OK)) {
        }
    }

    public static void eloPrintText(String paramText, EloPrinterReceipt.PrinterMake myPrinter, ApiAdapter apiAdapter) {
        if (!GlobalMemberValues.isStrEmpty(paramText)) {
            try {
                apiAdapter.print(paramText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eloPrintToFilePath(String paramFilePath, EloPrinterReceipt.PrinterMake myPrinter, ApiAdapter apiAdapter) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = 1;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(paramFilePath, opts);

        try {
            apiAdapter.print(GlobalMemberValues.bitmapToByteArrayFunc(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eloProPrintText(String paramText, ApiAdapter apiAdapter, String paramCutYN, String paramAlignment, int paramTextSize) {
        try {
            apiAdapter.print(EloProPrintMake.getPrintContentsForEloPro(paramText, paramCutYN, paramAlignment, paramTextSize));
        } catch (RuntimeException | IOException e) {
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void eloProCutPaper(ApiAdapter apiAdapter) {
        eloProPrintText("", apiAdapter, "Y", "LEFT", 25);
    }

    public static void eloProPrintToFilePath(String paramFilePath, ApiAdapter apiAdapter) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = 1;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(paramFilePath, opts);

        try {
            apiAdapter.print(EloProPrintMakeImage.getPrintContentsForEloPro(bitmap));
        } catch (RuntimeException | IOException e) {
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void setFileAccessPermission(Activity paramActivity, Context paramContext) {
        // os 버전 체크
        // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent appPerUseActivity = new Intent(paramContext.getApplicationContext(), AppPermissionPage.class);
            paramActivity.startActivity(appPerUseActivity);
        }
    }

    public static void setKitchenPrintedChangeStatus(String paramSalesCode, String paramOrderType) {
        if (paramOrderType.equals("POS")) {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = " update salon_sales set kitchenprintedyn = 'Y' where salesCode = '" + paramSalesCode + "' ";
            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("kitchenprintedchangestatus", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                // GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                if (!MainActivity.mActivity.isFinishing()) {
//                    Toast.makeText(MainActivity.mContext, "Database Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                GlobalMemberValues.logWrite("kitchenprintedchangestatus", "성공" + "\n");
            }
        } else {
            final String tempSalesCode = paramSalesCode;
            final String[] mApiReturnValue = {""};
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    API_weborder_kitchenprinted apiWebOrderReturn = new API_weborder_kitchenprinted(tempSalesCode);
                    apiWebOrderReturn.execute(null, null, null);
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                        if (apiWebOrderReturn.mFlag) {
                            mApiReturnValue[0] = apiWebOrderReturn.mApiReturnCode;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            if (mApiReturnValue[0].equals("00")) {
            } else {
                //Toast.makeText(MainActivity.mContext, "Cloud API Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int checkBatchedSales(DatabaseInit paramDbInit, String paramSalesCode) {
        int tempBatchSaleCount = 0;
        tempBatchSaleCount = GlobalMemberValues.getIntAtString(paramDbInit.dbExecuteReadReturnString(
                "select count(idx) from salon_sales where salesCode = '" + paramSalesCode + "' " +
                        " and salesCode in (select salesCode from salon_sales_batch where delyn = 'N') "
        ));

        return tempBatchSaleCount;
    }

    public static String deleteMemberInfoAfterCheckingMember(Vector<String> paramVector) {
        String returnString = "N";

        for (String tempUid : paramVector) {
            GlobalMemberValues.logWrite("InDeletingMemberAfterCheckingMember", "downloaded customer id : " + tempUid + "\n");
        }
        Vector<String> deleteQueryVec = new Vector<String>();
        String sqlQuery = " select uid from member1 ";
        Cursor tempCustomerCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
        while (tempCustomerCursor.moveToNext()) {
            String tempGetUid = GlobalMemberValues.getDBTextAfterChecked(tempCustomerCursor.getString(0), 1);
            int schVectorIndex = paramVector.indexOf(tempGetUid);
            if (schVectorIndex != -1) {
            } else {
                sqlQuery = "delete from member_mileage where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from member1 where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from member2 where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from reservation where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from salon_member where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from coupon_issue_history where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from member_memo where uid = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from temp_salecart where customerId = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from salon_storegiftcard_number where customerId = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from salon_storegiftcard_number_history where customerId = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from salon_sales_detail where customerId = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);

                sqlQuery = "delete from salon_sales where customerId = '" + tempGetUid +"' ";
                deleteQueryVec.addElement(sqlQuery);
            }
        }

        // 트랜잭션으로 DB 처리한다.
        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(deleteQueryVec);
        if (returnResult == "N" || returnResult == "") {
            returnString = "N";
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우
            returnString = "Y";
            GlobalMemberValues.logWrite("InDeletingMemberAfterCheckingMember", "회원체크후 없는 회원 삭제 - 정상처리" + "\n");
        }

        return returnString;
    }

    public static String getEncodingAsMD5(String paramPwd) throws Exception {
        StringBuffer sbuf = new StringBuffer();

        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        mDigest.update(paramPwd.getBytes());

        byte[] msgStr = mDigest.digest() ;

        for(int i=0; i < msgStr.length; i++){
            String tmpEncTxt = Integer.toHexString((int)msgStr[i] & 0x00ff) ;
            sbuf.append(tmpEncTxt) ;
        }

        return sbuf.toString() ;
    }

    public static void setTimeMenuCodeValue(String paramTimeCodValue) {
        GlobalMemberValues.SELECTED_TIME_CODEVALUE = paramTimeCodValue;
    }

    public static void setNowTimeCodeValue() {

        int nowTime1 = GlobalMemberValues.getIntAtString(DateMethodClass.nowHourGet());
        int nowTime2 = GlobalMemberValues.getIntAtString(DateMethodClass.nowMinuteGet());
        int nowTime = 0;
        if (nowTime2 < 10) {
            nowTime = GlobalMemberValues.getIntAtString(nowTime1 + "0" + nowTime2);
        } else {
            nowTime = GlobalMemberValues.getIntAtString(nowTime1 + "" + nowTime2);
        }

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성

        String mStartTxt = dbInit.dbExecuteReadReturnString("select mstart from salon_storegeneral");
        String aStartTxt = dbInit.dbExecuteReadReturnString("select astart from salon_storegeneral");
        String eStartTxt = dbInit.dbExecuteReadReturnString("select estart from salon_storegeneral");
        String nStartTxt = dbInit.dbExecuteReadReturnString("select nstart from salon_storegeneral");

        if (GlobalMemberValues.isStrEmpty(mStartTxt)) {
            mStartTxt = "600";
        } else {
            if (mStartTxt.length() < 3) {
                mStartTxt = mStartTxt + "00";
            }
        }
        if (GlobalMemberValues.isStrEmpty(aStartTxt)) {
            aStartTxt = "1200";
        } else {
            if (aStartTxt.length() < 3) {
                aStartTxt = aStartTxt + "00";
            }
        }
        if (GlobalMemberValues.isStrEmpty(eStartTxt)) {
            eStartTxt = "1800";
        } else {
            if (eStartTxt.length() < 3) {
                eStartTxt = eStartTxt + "00";
            }
        }
        if (GlobalMemberValues.isStrEmpty(nStartTxt)) {
            nStartTxt = "0";
        } else {
            if (nStartTxt == "2400" || nStartTxt.equals("2400")) {
                nStartTxt = "0";
            } else {
                if (nStartTxt.length() < 3) {
                    nStartTxt = nStartTxt + "00";
                }
            }
        }

        int mStartInt = GlobalMemberValues.getIntAtString(mStartTxt);
        int aStartInt = GlobalMemberValues.getIntAtString(aStartTxt);
        int eStartInt = GlobalMemberValues.getIntAtString(eStartTxt);
        int nStartInt = GlobalMemberValues.getIntAtString(nStartTxt);

        if (nStartInt > mStartInt) {
            if (nowTime >= nStartInt && nowTime < (mStartInt + 2400)) {
                GlobalMemberValues.NOW_TIME_CODEVALUE = "n";
            }
            if ((nowTime + 2400) >= nStartInt && nowTime < mStartInt) {
                GlobalMemberValues.NOW_TIME_CODEVALUE = "n";
            }
        } else {
            if (nowTime >= nStartInt && nowTime < mStartInt) {
                GlobalMemberValues.NOW_TIME_CODEVALUE = "n";
            }
        }
        if (nowTime >= mStartInt && nowTime < aStartInt) {
            GlobalMemberValues.NOW_TIME_CODEVALUE = "m";
        }
        if (nowTime >= aStartInt && nowTime < eStartInt) {
            GlobalMemberValues.NOW_TIME_CODEVALUE = "a";
        }
//        if (nowTime >= eStartInt) {
//            GlobalMemberValues.NOW_TIME_CODEVALUE = "e";
//        }
        // jihun park add
        if (nowTime >= eStartInt && nowTime < nStartInt){
            GlobalMemberValues.NOW_TIME_CODEVALUE = "e";
        }
        if (nowTime >= nStartInt && nowTime < mStartInt){
            GlobalMemberValues.NOW_TIME_CODEVALUE = "n";
        }


        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Now Time : " + nowTime + "\n");
        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Morning Start : " + mStartInt + "\n");
        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Afternoon Start : " + aStartInt + "\n");
        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Evening Start : " + eStartInt + "\n");
        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Night Start : " + nStartInt + "\n");
        GlobalMemberValues.logWrite("TimeCodeValueTxt", "Now Time Code : " + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "\n");

    }

    public static String changeBrLetter(String paramTxt) {
        String returnString = "";
        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            returnString = GlobalMemberValues.getReplaceText(paramTxt, " | ", "\n");
            returnString = GlobalMemberValues.getReplaceText(returnString, "|", "\n");
        }
        return returnString;
    }

    public static boolean checkEmployeePermission(String paramEmpIdx, String paramEmpName, String paramPermission) {
        boolean returnValue = false;

        if (GlobalMemberValues.isStrEmpty(paramEmpIdx) && paramEmpName.equals("ADMIN") || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName == "ADMIN") {
            returnValue = true;
        } else {
            String getPermission = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select permission from salon_storeemployee where idx = '" + paramEmpIdx + "' "
            );

            if (!GlobalMemberValues.isStrEmpty(getPermission)) {
                if (getPermission.contains(paramPermission)) {
                    returnValue = true;
                } else {
                    returnValue = false;
                }
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }

    public static byte[] bitmapToByteArrayFunc(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    public static void eloCfdScreenViewOn(final String line1, final String line2) {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Elo.mApiAdapter.cfdSetBacklight(true);
                            Elo.mApiAdapter.cfdClear();
                            Elo.mApiAdapter.cfdSetLine(1, line1);
                            Elo.mApiAdapter.cfdSetLine(2, line2);
                        }
                    });
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }

    public static void eloCfdScreenViewInit() {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            //Elo.mApiAdapter.cfdSetBacklight(false);
                            //Elo.mApiAdapter.cfdClear();
                            Elo.mApiAdapter.cfdSetBacklight(true);
                            Elo.mApiAdapter.cfdClear();
                            Elo.mApiAdapter.cfdSetLine(1, "Welcome to");
                            Elo.mApiAdapter.cfdSetLine(2, GlobalMemberValues.SALON_NAME);
                        }
                    });
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }

    public static void eloCfdScreenViewOff() {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Elo.mApiAdapter.cfdSetBacklight(false);
                            Elo.mApiAdapter.cfdClear();
                        }
                    });
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }

    public static void eloBarcodeScannerOn() {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    Elo.mApiAdapter.setBarCodeReaderKbdMode();
                    turnBcrOff(Elo.mApiAdapter);
                    turnBcrOn(Elo.mApiAdapter);
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }

    public static void eloBarcodeScannerOff() {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    turnBcrOn(Elo.mApiAdapter);
                    turnBcrOff(Elo.mApiAdapter);
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }

    public static void turnBcrOn(ApiAdapter paramApiAdapter) {
        if (!paramApiAdapter.isBarCodeReaderEnabled()) {
            paramApiAdapter.setBarCodeReaderEnabled(true);
        }
    }

    public static void turnBcrOff(ApiAdapter paramApiAdapter) {
        if (paramApiAdapter.isBarCodeReaderEnabled()) {
            paramApiAdapter.setBarCodeReaderEnabled(false);
        }
    }

    // 구글 플레이 서비스 구글플레이서비스 (Google Play Service)
    public static void getInstanceIdToken() {
        //GlobalMemberValues.APP_INSTANCE_ID = FirebaseInstanceId.getInstance().getToken();
    }

    public static void checkOnlineService(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            try {
                if (CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK != null && CURRENTSERVICEINTENT_ONLINECHECK != null) {
                    CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK.stopService(CURRENTSERVICEINTENT_ONLINECHECK);
                }

                Intent checkOnlineStatus = new Intent(paramContext.getApplicationContext(), CheckOnlineStatus.class);
                CURRENTSERVICEINTENT_ONLINECHECK = checkOnlineStatus;    // 실행되는 서비스 인텐트를 저장해둔다.
                CURRENTACTIVITYOPENEDSERVICE_ONLINECHECK = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//                paramActivity.startService(checkOnlineStatus);

                // 인터넷 연결 상태 이미지 변경
                switch (GlobalMemberValues.GLOBALNETWORKSTATUS) {
                    case 0 : {
                        GlobalMemberValues.changeNetworkUI("NOT CONNECTED", R.drawable.aa_images_main_disconnect);
                        break;
                    }
                    case 1 : {
                        GlobalMemberValues.changeNetworkUI("WIFI", R.drawable.aa_images_main_wifi);
                        break;
                    }
                    case 2 : {
                        GlobalMemberValues.changeNetworkUI("3G/LTE", R.drawable.aa_images_main_lte);
                        break;
                    }
                    case 3 : {
                        GlobalMemberValues.changeNetworkUI("ONLINE", R.drawable.aa_images_main_online);
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public static void checkNewWebOrdersService(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER != null && CURRENTSERVICEINTENT_NEWWEBORDER != null) {
//                CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER.stopService(CURRENTSERVICEINTENT_NEWWEBORDER);
            }

            Intent newWebOrderService = new Intent(paramContext.getApplicationContext(), CheckWebOrdersInCloudService.class);
            CURRENTSERVICEINTENT_NEWWEBORDER = newWebOrderService;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWWEBORDER = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(newWebOrderService);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(newWebOrderService);
            } else {
                paramActivity.startService(newWebOrderService);
            }
        }
    }

    // 01172024
    public static void checkTableOrder(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWTABLEORDER != null && CURRENTSERVICEINTENT_NEWTABLEORDER != null) {

            }

            Intent intent = new Intent(paramContext.getApplicationContext(), CheckNewTableOrderInCloudService.class);
            CURRENTSERVICEINTENT_NEWTABLEORDER = intent;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWTABLEORDER = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(intent);
            } else {
                paramActivity.startService(intent);
            }
        }
    }


    public static void checkNewTablePay(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY != null && CURRENTSERVICEINTENT_NEWTABLEPAY != null) {
//                CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY.stopService(CURRENTSERVICEINTENT_NEWTABLEPAY);
            }

            Intent intent = new Intent(paramContext.getApplicationContext(), CheckNewTablePayInCloudService.class);
            CURRENTSERVICEINTENT_NEWTABLEPAY = intent;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(intent);
            } else {
                paramActivity.startService(intent);
            }
        }
    }

    public static void checkCursideService(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_CURSIDE != null && CURRENTSERVICEINTENT_CURSIDE != null) {
//                CURRENTACTIVITYOPENEDSERVICE_CURSIDE.stopService(CURRENTSERVICEINTENT_CURSIDE);
            }

            Intent newCursideService = new Intent(paramContext.getApplicationContext(), CheckCursideInCloudService.class);
            CURRENTSERVICEINTENT_CURSIDE = newCursideService;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_CURSIDE = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(newCursideService);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(newCursideService);
            } else {
                paramActivity.startService(newCursideService);
            }
        }
    }

    public static void checkNewSideOrderService(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER != null && CURRENTSERVICEINTENT_NEWSIDEORDER != null) {
//                CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER.stopService(CURRENTSERVICEINTENT_NEWSIDEORDER);
            }

            Intent checkNewSideMenuOrderCloudService = new Intent(paramContext.getApplicationContext(), CheckNewSideMenuOrderCloudService.class);
            CURRENTSERVICEINTENT_NEWSIDEORDER = checkNewSideMenuOrderCloudService;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWSIDEORDER = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(checkNewSideMenuOrderCloudService);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(checkNewSideMenuOrderCloudService);
            } else {
                paramActivity.startService(checkNewSideMenuOrderCloudService);
            }
        }
    }

    public static void newCartCheckByStation(Context paramContext, Activity paramActivity) {
        if (paramContext != null && paramActivity != null) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION != null && CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION != null) {
//                CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION.stopService(CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION);
            }

            Intent nccbs = new Intent(paramContext.getApplicationContext(), NewCartCheckByStation.class);
            CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION = nccbs;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
//            paramActivity.startService(nccbs);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                paramActivity.startForegroundService(nccbs);
            } else {
                paramActivity.startService(nccbs);
            }
        }
    }

    public static void openNewWebOrderList(String paramOrdersStr) {
        if (!GlobalMemberValues.isStrEmpty(paramOrdersStr)) {
            String[] ordersArr = paramOrdersStr.split("-JJJFAM-");
            GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "전체문자열 : " + paramOrdersStr + "\n");

            for (int i = 0; i < ordersArr.length; i++) {
                GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "주문하나 문자열 : " + ordersArr[i] + "\n");

                // 08302023 -------------------------------------------------------
                String onlinetype = "W";
                String ordervalue = "";

                String[] ordersvalue_s = ordersArr[i].split("-why-");
                ordervalue = ordersvalue_s[0];
                if (ordersvalue_s.length > 1) {
                    onlinetype = ordersvalue_s[1];
                }

                String[] ordersContentsArr = ordervalue.split(GlobalMemberValues.STRSPLITTER1);
                // ------------------------------------------------------------------



                String webOrdersSalesCode = ordersContentsArr[0];
                String webOrdersCustomerId = ordersContentsArr[1];
                String webOrdersCustomerName = ordersContentsArr[2];
                String webOrdersCustomerPhone = ordersContentsArr[3];
                String webOrdersDeliveryday = ordersContentsArr[4];
                String webOrdersDeliverytime = ordersContentsArr[5];
                String webOrdersDeliveryDate = ordersContentsArr[6];
                String webOrdersDeliveryTakeaway = ordersContentsArr[7];
                String webOrdersSaleDate = ordersContentsArr[8];
                String webOrdersSaleItems = ordersContentsArr[9];
                String webOrdersMemo = ordersContentsArr[10];
                String webOrdersCustomerOrderNumber = ordersContentsArr[11];

                String webOrdersTablename = "";
                if (ordersContentsArr.length >= 12) {
                    webOrdersTablename = ordersContentsArr[12];
                }

                String webOrdersTableidx = "";
                if (ordersContentsArr.length >= 13) {
                    webOrdersTableidx = ordersContentsArr[13];
                }

                // 10112023
                String webOrdersOrderfrom = "";
                if (ordersContentsArr.length >= 14) {
                    webOrdersOrderfrom = ordersContentsArr[14];
                }
                String webOrdersSalescodethirdparty = "";
                if (ordersContentsArr.length >= 15) {
                    webOrdersSalescodethirdparty = ordersContentsArr[15];
                }


                String returnResult = "";
                String strQuery = "";
                // salon_sales_web_push_realtime 테이블 저장
                Vector<String> strQueryVec = new Vector<String>();

                //먼저 같은 SalesCode 로 저장된 값을 삭제
                strQuery = "delete from salon_sales_web_push_realtime where salesCode = '"+ webOrdersSalesCode + "' ";
                strQueryVec.addElement(strQuery);

                strQuery = "insert into salon_sales_web_push_realtime (" +
                        " salesCode, scode, sidx, customerId, customerName, customerPhone, deliveryday, deliverytime, deliverydate, deliverytakeaway, saleDate, customerordernumber, tablename, tableidx," +

                        // 08302023
                        " onlinetype, " +

                        // 10112023
                        " orderfrom, salescodethirdparty " +

                        " ) values ( " +
                        " '" + webOrdersSalesCode + "', " +
                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + webOrdersCustomerId + "', " +
                        " '" + webOrdersCustomerName + "', " +
                        " '" + webOrdersCustomerPhone + "', " +
                        " '" + webOrdersDeliveryday + "', " +
                        " '" + webOrdersDeliverytime + "', " +
                        " '" + webOrdersDeliveryDate + "', " +
                        " '" + webOrdersDeliveryTakeaway + "', " +
                        " '" + webOrdersSaleDate + "', " +
                        " '" + webOrdersCustomerOrderNumber + "', " +
                        " '" + webOrdersTablename + "', " +
                        " '" + webOrdersTableidx + "', " +

                        // 08302023
                        " '" + onlinetype + "', " +

                        // 10112023
                        " '" + webOrdersOrderfrom + "', " +
                        " '" + webOrdersSalescodethirdparty + "' " +

                        " ) ";
                strQueryVec.addElement(strQuery);
                for (String tempQuery : strQueryVec) {
                    GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "query : " + tempQuery + "\n");
                }
                DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
                GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "DB 입력결과 : " + returnResult + "\n");
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                } else { // 정상처리일 경우 팝업창 오픈
                    if (GlobalMemberValues.isPushMsgReceiving() && GlobalMemberValues.isOnlineOrderUseYN()) {
                        Intent pushIntent = new Intent(MainActivity.mContext, PushPopupForWebOrders.class);
                        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                        pushIntent.putExtra("webOrdersSalesCode", webOrdersSalesCode);

                        pushIntent.putExtra("webOrdersCustomerId", webOrdersCustomerId);
                        pushIntent.putExtra("webOrdersCustomerName", webOrdersCustomerName);
                        pushIntent.putExtra("webOrdersCustomerPhone", webOrdersCustomerPhone);

                        pushIntent.putExtra("webOrdersDeliveryDay", webOrdersDeliveryday);
                        pushIntent.putExtra("webOrdersDeliveryTime", webOrdersDeliverytime);
                        pushIntent.putExtra("webOrdersDeliveryDate", webOrdersDeliveryDate);
                        pushIntent.putExtra("webOrdersDeliveryTakeaway", webOrdersDeliveryTakeaway);

                        pushIntent.putExtra("webOrdersSaleDate", webOrdersSaleDate);
                        pushIntent.putExtra("webOrdersSaleItems", webOrdersSaleItems);
                        pushIntent.putExtra("webOrdersMemo", webOrdersMemo);

                        pushIntent.putExtra("webOrdersCustomerOrderNumber", webOrdersCustomerOrderNumber);
                        pushIntent.putExtra("webOrdersTableName",webOrdersTablename);
                        pushIntent.putExtra("webOrdersTableIdx",webOrdersTableidx);

                        // 08302023
                        pushIntent.putExtra("webOrdersOnlineType",onlinetype);

                        // 10112023
                        pushIntent.putExtra("webOrdersOrderfrom",webOrdersOrderfrom);
                        pushIntent.putExtra("webOrdersSalescodethirdparty",webOrdersSalescodethirdparty);

                        MainActivity.mActivity.startActivity(pushIntent);
                    }
                }
            }
        }
    }

    public static void openCursideStr(String paramOrdersStr) {
        if (!GlobalMemberValues.isStrEmpty(paramOrdersStr)) {
            String[] ordersArr = paramOrdersStr.split("-JJJFAM-");
            GlobalMemberValues.logWrite("openCursideStr", "전체문자열 : " + paramOrdersStr + "\n");

            for (int i = 0; i < ordersArr.length; i++) {
                GlobalMemberValues.logWrite("openCursideStr", "주문하나 문자열 : " + ordersArr[i] + "\n");

                String[] ordersContentsArr = ordersArr[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                String curbside_carinfo = ordersContentsArr[0];
                String curbside_calltime = ordersContentsArr[1];
                String curbside_cus_phone = ordersContentsArr[2];
                String curbside_salecode = ordersContentsArr[3];
                String curbside_ordernum = ordersContentsArr[4];

                GlobalMemberValues gm = new GlobalMemberValues();
                if (GlobalMemberValues.getCurbsidePickupCount(curbside_salecode) == 0 && gm.isCurbsideOrder()) {
                    Intent pushIntent = new Intent(MainActivity.mContext, PushPopupForCustomerArrive.class);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    pushIntent.putExtra("curbside_carinfo", curbside_carinfo);
                    pushIntent.putExtra("curbside_calltime", curbside_calltime.replace("&nbsp;"," "));
                    pushIntent.putExtra("curbside_cus_phone", curbside_cus_phone);
                    pushIntent.putExtra("curbside_salecode", curbside_salecode);
                    pushIntent.putExtra("curbside_ordernum", curbside_ordernum);

                    MainActivity.mActivity.startActivity(pushIntent);

                }

            }
        }
    }


    public static void openNewSideMenuStr(String paramOrdersStr) {
        if (!GlobalMemberValues.isStrEmpty(paramOrdersStr)) {
            String[] ordersArr = paramOrdersStr.split("-JJJFAM-");
            GlobalMemberValues.logWrite("openNewSideMenuStr", "전체문자열 : " + paramOrdersStr + "\n");

            for (int i = 0; i < ordersArr.length; i++) {
                GlobalMemberValues.logWrite("openNewSideMenuStr", "주문하나 문자열 : " + ordersArr[i] + "\n");

                String[] ordersContentsArr = ordersArr[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                String sideMenu_index = ordersContentsArr[0];
                String sideMenu_salecode = ordersContentsArr[1];
                String sideMenu_tableidx = ordersContentsArr[2];
                String sideMenu_tablename = ordersContentsArr[3];
                String sideMenu_ordertime = ordersContentsArr[4];
                String sideMenu_ordernum = ordersContentsArr[5];
                String sideMenu_orderdetail = ordersContentsArr[6];

                GlobalMemberValues gm = new GlobalMemberValues();
                if (GlobalMemberValues.getCurbsideNewSideMenuCount_sidemenuidx(sideMenu_index) == 0 && gm.isSideMenuOrder()) {
                    Intent pushIntent = new Intent(MainActivity.mContext, PushPopupForCurbNewSideMenu.class);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    pushIntent.putExtra("sideMenu_index", sideMenu_index);
                    pushIntent.putExtra("sideMenu_salecode", sideMenu_salecode);
                    pushIntent.putExtra("sideMenu_tableidx", sideMenu_tableidx);
                    pushIntent.putExtra("sideMenu_tablename", sideMenu_tablename);
                    pushIntent.putExtra("sideMenu_ordertime", sideMenu_ordertime.replace("&nbsp;"," "));
                    pushIntent.putExtra("sideMenu_ordernum", sideMenu_ordernum);
                    pushIntent.putExtra("sideMenu_orderdetail", sideMenu_orderdetail.replace("&nbsp;"," "));

                    MainActivity.mActivity.startActivity(pushIntent);

                }

            }
        }
    }


    // 01172024
    public static void openNewTableOrderStr(String paramOrdersStr) {
        GlobalMemberValues.logWrite("openNewSideMenuStr", "전체문자열 : " + paramOrdersStr + "\n");
        if (!GlobalMemberValues.isStrEmpty(paramOrdersStr)) {
            String[] ordersArr = paramOrdersStr.split("-JJJFAM-");
            for (int k = 0; k < ordersArr.length; k++) {
                GlobalMemberValues.logWrite("openNewSideMenuStr", "주문하나 문자열 : " + ordersArr[k] + "\n");

                String datas = ordersArr[k];

                if (!GlobalMemberValues.isStrEmpty(datas)) {
                    String[] ordersArr2 = datas.split("-JJJFAM2-");
                    String clouddbIdx = ordersArr2[0];

                    // 먼저 해당 데이터가 이미 저장처리된 데이터인지 확인한다.
                    int clouddbidx_cnt = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from salon_sales_kitchenprintingdata_json_torder where clouddbIdx = '" + clouddbIdx + "' "
                            )
                    );

                    GlobalMemberValues.logWrite("openNewSideMenuStr2", "clouddbidx_cnt : " + clouddbidx_cnt + "\n");

                    if (clouddbidx_cnt == 0) {
                        String jsonstr = ordersArr2[1];

                        GlobalMemberValues.logWrite("openNewSideMenuStr2", "jsonstr : " + jsonstr + "\n");

                        if (!GlobalMemberValues.isStrEmpty(jsonstr)) {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(jsonstr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (json != null) {
                                GlobalMemberValues.logWrite("openNewSideMenuStr3", "jsonstr : " + json.toString() + "\n");

                                Vector<String> dbVec = new Vector<String>();

                                String receiptno = "";
                                String saledate = "";
                                String saletime = "";
                                String restaurant_tableidx = "";
                                String restaurant_tablename = "";
                                String restaurant_tablepeoplecnt = "";
                                String restaurant_tableholdcode = "";
                                String pastholdcode = "";
                                String customerid = "";
                                String customername = "";
                                String deliverytakeaway = "";
                                String deliverydate = "";
                                String ordertype = "";
                                String customermemo = "";
                                String phoneorder = "";
                                String phoneordernumber = "";
                                String storename = "";
                                String storeaddress1 = "";
                                String storeaddress2 = "";
                                String storecity = "";
                                String storestate = "";
                                String storezip = "";
                                String storephone = "";
                                String customerphonenum = "";
                                String customeraddress = "";
                                String canceldeleteyn = "";
                                String reprintyn = "";
                                String holdcode = "";

                                // item
                                String str_saleitemlist = "";

                                String itemName = "";
                                String optionTxt = "";
                                String additionalTxt1 = "";
                                String additionalTxt2 = "";
                                String itemIdx = "";
                                String kitchenprintnum = "";
                                String nokitchenmemo = "";
                                String kitchenmemo = "";
                                String soldoutmemo = "";


                                String qty = "";
                                String categoryname = "";
                                String detail_idx = "";
                                String salesPrice = "";
                                String tax = "";
                                String optionprice = "";
                                String additionalprice1 = "";
                                String additionalprice2 = "";
                                String tordercode = "";



                                receiptno = GlobalMemberValues.getDataInJsonData(json, "receiptno");
                                saledate  = GlobalMemberValues.getDataInJsonData(json, "saledate");
                                saletime = GlobalMemberValues.getDataInJsonData(json, "saletime");
                                restaurant_tableidx = GlobalMemberValues.getDataInJsonData(json, "restaurant_tableidx");
                                restaurant_tablename = GlobalMemberValues.getDataInJsonData(json, "restaurant_tablename");
                                restaurant_tablepeoplecnt = GlobalMemberValues.getDataInJsonData(json, "restaurant_tablepeoplecnt");
                                restaurant_tableholdcode = GlobalMemberValues.getDataInJsonData(json, "restaurant_tableholdcode");
                                pastholdcode = GlobalMemberValues.getDataInJsonData(json, "pastholdcode");
                                customername = GlobalMemberValues.getDataInJsonData(json, "customername");
                                deliverytakeaway = GlobalMemberValues.getDataInJsonData(json, "deliverytakeaway");
                                deliverydate = GlobalMemberValues.getDataInJsonData(json, "deliverydate");
                                ordertype = GlobalMemberValues.getDataInJsonData(json, "ordertype");
                                customermemo = GlobalMemberValues.getDataInJsonData(json, "customermemo");
                                phoneorder = GlobalMemberValues.getDataInJsonData(json, "phoneorder");
                                phoneordernumber = GlobalMemberValues.getDataInJsonData(json, "phoneordernumber");
                                storename = GlobalMemberValues.getDataInJsonData(json, "storename");
                                storeaddress1 = GlobalMemberValues.getDataInJsonData(json, "storeaddress1");
                                storeaddress2 = GlobalMemberValues.getDataInJsonData(json, "storeaddress2");
                                storecity = GlobalMemberValues.getDataInJsonData(json, "storecity");
                                storestate = GlobalMemberValues.getDataInJsonData(json, "storestate");
                                storezip = GlobalMemberValues.getDataInJsonData(json, "storezip");
                                storephone = GlobalMemberValues.getDataInJsonData(json, "storephone");
                                customerphonenum = GlobalMemberValues.getDataInJsonData(json, "customerphonenum");
                                customeraddress = GlobalMemberValues.getDataInJsonData(json, "customeraddress");
                                canceldeleteyn = GlobalMemberValues.getDataInJsonData(json, "canceldeleteyn");
                                reprintyn = GlobalMemberValues.getDataInJsonData(json, "reprintyn");
                                holdcode = GlobalMemberValues.getDataInJsonData(json, "holdcode");

                                str_saleitemlist = GlobalMemberValues.getDataInJsonData(json, "saleitemlist");


                                String strQuery = "";

                                int tempItemCount = 0;
                                if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {

                                    GlobalMemberValues.logWrite("openNewSideMenuStr3", "saleitemlist : " + str_saleitemlist + "\n");

                                    String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                                    for (int i = 0; i < strOrderItemsList.length; i++) {
                                        GlobalMemberValues.logWrite("openNewSideMenuStr5", "strOrderItemsList[i] : " + strOrderItemsList[i] + "\n");

                                        String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                                        GlobalMemberValues.logWrite("jjjkcprintlog", "strOrderItems.length : " + strOrderItems.length + "\n");

                                        // 상품명, 수량 정보 ------------------------------------------------------------------------
                                        qty = strOrderItems[1];
                                        kitchenprintnum = strOrderItems[2];
                                        categoryname = strOrderItems[3];
                                        detail_idx = strOrderItems[4];
                                        salesPrice = strOrderItems[5];
                                        tax = strOrderItems[6];
                                        optionprice = strOrderItems[7];
                                        additionalprice1 = strOrderItems[8];
                                        if (strOrderItems.length > 9) {
                                            additionalprice2 = strOrderItems[9];
                                        }
                                        if (strOrderItems.length > 10) {
                                            tordercode = strOrderItems[10];
                                        }

                                        String tempItemNameOptionAdd = strOrderItems[0];
                                        GlobalMemberValues.logWrite("jjjkcprintlog", "tempItemNameOptionAdd : " + tempItemNameOptionAdd + "\n");
                                        String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);

                                        GlobalMemberValues.logWrite("jjjkcprintlog", "strItemNAmeOptionAdd.length : " + strItemNAmeOptionAdd.length + "\n");

                                        itemName = strItemNAmeOptionAdd[0];
                                        optionTxt = strItemNAmeOptionAdd[1];
                                        additionalTxt1 = strItemNAmeOptionAdd[2];
                                        additionalTxt2 = strItemNAmeOptionAdd[3];
                                        itemIdx = strItemNAmeOptionAdd[4];
                                        if (strItemNAmeOptionAdd.length > 5) {
                                            kitchenprintnum = strItemNAmeOptionAdd[5];
                                        }
                                        if (strItemNAmeOptionAdd.length > 6) {
                                            nokitchenmemo = strItemNAmeOptionAdd[6];
                                        }
                                        if (strItemNAmeOptionAdd.length > 7) {
                                            kitchenmemo = strItemNAmeOptionAdd[7];
                                        }
                                        if (strItemNAmeOptionAdd.length > 8) {
                                            soldoutmemo = strItemNAmeOptionAdd[8];
                                        }

                                        GlobalMemberValues.logWrite("logcheckjjj", "체크1" + "\n");

                                        if (GlobalMemberValues.isStrEmpty(categoryname)) {
                                            qty = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(categoryname)) {
                                            categoryname = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(salesPrice)) {
                                            salesPrice = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(tax)) {
                                            tax = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(optionprice)) {
                                            optionprice = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(additionalprice1)) {
                                            additionalprice1 = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(additionalprice2)) {
                                            additionalprice2 = "0";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(itemName)) {
                                            itemName = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(optionTxt)) {
                                            optionTxt = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(additionalTxt1)) {
                                            additionalTxt1 = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(additionalTxt2)) {
                                            additionalTxt2 = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(kitchenprintnum)) {
                                            kitchenprintnum = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(nokitchenmemo)) {
                                            nokitchenmemo = "nokitchenmemo";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(kitchenmemo)) {
                                            kitchenmemo = "";
                                        }
                                        if (GlobalMemberValues.isStrEmpty(soldoutmemo)) {
                                            soldoutmemo = "";
                                        }

                                        GlobalMemberValues.logWrite("logcheckjjj", "체크2" + "\n");

                                        double sPriceAmount = GlobalMemberValues.getDoubleAtString(salesPrice) * GlobalMemberValues.getIntAtString(qty);
                                        double sTaxAmount = GlobalMemberValues.getDoubleAtString(tax) * GlobalMemberValues.getIntAtString(qty);
                                        double sTotalAmount = sPriceAmount + sTaxAmount;

                                        String midx = "";
                                        String svcPositionNo = "";
                                        String svcOrgPrice = "";
                                        String svcCategoryColor = "";
                                        strQuery = " select a.midx, a.positionNo, a.service_price from salon_storeservice_sub a " +
                                                " where a.idx = '" + itemIdx + "' ";
                                        Cursor itemCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
                                        if (itemCursor.getCount() > 0 && itemCursor.moveToFirst()) {
                                            midx = GlobalMemberValues.getDBTextAfterChecked(itemCursor.getString(0), 1);
                                            svcPositionNo = GlobalMemberValues.getDBTextAfterChecked(itemCursor.getString(1), 1);
                                            svcOrgPrice = GlobalMemberValues.getDBTextAfterChecked(itemCursor.getString(2), 1);
                                        }

                                        String svcFileName, svcFilePath, empName, giftcardNumber, sCommissionRatioType, reservationCode, modifieridx, modifiercode, togotype;
                                        svcFileName = svcFilePath = empName = giftcardNumber = sCommissionRatioType = reservationCode = modifieridx = modifiercode = togotype = "";

                                        String svcSetMenuYN = "Y";
                                        Double sCommission, sPoint, sCommissionAmount, sPointAmount;
                                        sCommission = sPoint = sCommissionAmount = sPointAmount = 0.00;

                                        String sSaleYN = "N";
                                        int saveType = 0;
                                        int empIdx = 0;
                                        String quickSaleYN = "N";
                                        Double giftcardSavePrice = 0.00;
                                        int sCommissionRatio = 0;
                                        int sPointRatio = 0;
                                        double sPriceBalAmount = sTotalAmount;
                                        double sPriceBalAmount_org = sPriceAmount;
                                        double sTaxAmount_org = sTaxAmount;
                                        double sTotalAmount_org = sTotalAmount;
                                        int sCommissionAmount_org = 0;
                                        int sPointAmount_org = 0;
                                        int subtablenum = 0;
                                        int mergednum = 0;

                                        String tempStationCode = ordertype;

                                        GlobalMemberValues.logWrite("logcheckjjj", "체크3" + "\n");

                                        strQuery = " insert into temp_salecart ( " +
                                                " holdcode, sidx, stcode, midx, svcIdx,  " +
                                                " svcName, svcFileName, svcFilePath, svcPositionNo, svcOrgPrice," +
                                                " svcSetMenuYN, sPrice, sTax, sQty, sPriceAmount," +
                                                " sTaxAmount, sTotalAmount, sCommission, sPoint, sCommissionAmount, " +
                                                " sPointAmount, sSaleYN, customerId, customerName, customerPhoneNo, " +
                                                " saveType, empIdx, empName, quickSaleYN, svcCategoryName, " +
                                                " giftcardNumber, giftcardSavePrice, sCommissionRatioType, sCommissionRatio, sPointRatio,  " +
                                                " sPriceBalAmount, svcCategoryColor, reservationCode, optionTxt, optionprice, " +
                                                " additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, modifieridx, " +
                                                " modifiercode, memoToKitchen, " +
                                                " sPriceBalAmount_org, sTaxAmount_org, sTotalAmount_org, sCommissionAmount_org, sPointAmount_org, " +
                                                " tableidx, subtablenum, togotype, " +
                                                " mergednum, isCloudUpload, tordercode " +

                                                " ) values ( " +

                                                " '" + holdcode + "', " +
                                                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                                                " '" + tempStationCode + "', " +
                                                " '" + midx + "', " +
                                                " '" + itemIdx + "', " +

                                                " '" + GlobalMemberValues.getDBTextAfterChecked(itemName, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(svcFileName, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(svcFilePath, 0) + "', " +
                                                " '" + svcPositionNo + "', " +
                                                " '" + svcOrgPrice + "', " +

                                                " '" + svcSetMenuYN + "', " +
                                                " '" + salesPrice + "', " +
                                                " '" + tax + "', " +
                                                " '" + qty + "', " +
                                                " '" + sPriceAmount + "', " +

                                                " '" + sTaxAmount + "', " +
                                                " '" + sPriceBalAmount + "', " +
                                                " '" + sCommission + "', " +
                                                " '" + sPoint + "', " +
                                                " '" + sCommissionAmount + "', " +

                                                " '" + sPointAmount + "', " +
                                                " '" + sSaleYN + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(customerid, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(customername, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(customerphonenum, 0) + "', " +

                                                " '" + saveType + "', " +
                                                " '" + empIdx + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(empName, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(quickSaleYN, 0) + "', " +
                                                " '" + GlobalMemberValues.getDBTextAfterChecked(categoryname, 0) + "', " +

                                                " '" + GlobalMemberValues.getDBTextAfterChecked(giftcardNumber, 0) + "', " +
                                                " '" + giftcardSavePrice + "', " +
                                                " '" + sCommissionRatioType + "', " +
                                                " '" + sCommissionRatio + "', " +
                                                " '" + sPointRatio + "', " +

                                                " '" + sPriceAmount + "', " +
                                                " '" + svcCategoryColor + "', " +
                                                " '" + reservationCode + "', " +
                                                " '" + optionTxt + "', " +
                                                " '" + optionprice + "', " +

                                                " '" + additionalTxt1 + "', " +
                                                " '" + additionalprice1 + "', " +
                                                " '" + additionalTxt2 + "', " +
                                                " '" + additionalprice2 + "', " +
                                                " '" + modifieridx + "', " +

                                                " '" + modifiercode + "', " +
                                                " '" + kitchenmemo + "', " +

                                                " '" + sPriceBalAmount_org + "', " +
                                                " '" + sTaxAmount_org + "', " +
                                                " '" + sTotalAmount_org + "', " +
                                                " '" + sCommissionAmount_org + "', " +
                                                " '" + sPointAmount_org + "', " +
                                                " '" + restaurant_tableidx + "', " +
                                                " '" + TableSaleMain.mSubTableNum + "', " +
                                                " '" + GlobalMemberValues.mToGoType + "', " +
                                                " '" + mergednum + "', " +

                                                " '1', " +      // 02242024 - 추가작업

                                                " '" + GlobalMemberValues.getDBTextAfterChecked(tordercode, 0) + "' " +

                                                " ) ";
                                        dbVec.addElement(strQuery);

                                        GlobalMemberValues.logWrite("logcheckjjj", "체크4" + "\n");

                                        GlobalMemberValues.logWrite("openNewSideMenuStr5", "strQuery : " + strQuery + "\n");

                                        // -----------------------------------------------------------------------------------------
                                    }




                                    // 02242024 - 추가작업
                                    strQuery = " delete from salon_store_restaurant_table_peoplecnt where tableidx like '%" + restaurant_tableidx + "%' ";
                                    dbVec.addElement(strQuery);
                                    strQuery = " insert into salon_store_restaurant_table_peoplecnt " +
                                            " (tableidx, holdcode, peoplecnt) values ( " +
                                            "'" + restaurant_tableidx + "', " +
                                            "'" + holdcode + "', " +
                                            "'" + restaurant_tablepeoplecnt + "' " +
                                            " )";
                                    dbVec.addElement(strQuery);




                                    strQuery = " insert into salon_sales_kitchenprintingdata_json_torder (" +
                                            " salesCode, scode, sidx, jsonstr, tableidx, tablename, orderfrom, clouddbidx " +
                                            " ) values ( " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(holdcode, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(jsonstr, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(restaurant_tableidx, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(restaurant_tablename, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(ordertype, 0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(clouddbIdx, 0) + "' " +
                                            " ) ";
                                    dbVec.addElement(strQuery);


                                    strQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                            " (salesCode, scode, sidx, stcode, jsonstr, printedyn) values ( " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(holdcode,0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                                            "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                                            " '" + GlobalMemberValues.getDBTextAfterChecked(jsonstr, 0) + "', " +
                                            " 'N' " +
                                            " ) ";
                                    dbVec.addElement(strQuery);
                                }

                                // 데이터베이스 처리
                                for (String tempQuery : dbVec) {
                                    GlobalMemberValues.logWrite("torderquerydblogjjj", "query : " + tempQuery + "\n");
                                }
                                // 트랜잭션으로 DB 처리한다.
                                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(dbVec);
                                if (returnResult == "N" || returnResult == "") {
                                    GlobalMemberValues.logWrite("torderquerydblogjjj", "query returned error");
                                } else { // 정상처리일 경우


                                    // 02242024 - 추가작업 ---------------------------------------------------------------------
                                    TableSaleMain.isAfterMerge = true;
                                    MainMiddleService.mHoldCode = holdcode;

                                    // common gratuity 관련
                                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
                                    // common gratuity 관련
                                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                                    MainMiddleService.mHoldCode = "";
                                    TableSaleMain.isAfterMerge = false;

                                    GlobalMemberValues gm = new GlobalMemberValues();
                                    if (gm.isPOSWebPay() && (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                                        GlobalMemberValues.logWrite("uploadcartdatalog", "장바구니데이터 업로드 진입" + "\n");
                                        GlobalMemberValues.setSendCartToCloud(MainActivity.mContext, MainActivity.mActivity);
                                    }
                                    // 02242024 - 추가작업 ---------------------------------------------------------------------





                                    GlobalMemberValues.logWrite("torderquerydblogjjj", "query executed correctly");
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void openNewTablePayStr(String paramOrdersStr) {
        if (!GlobalMemberValues.isStrEmpty(paramOrdersStr)) {
            String[] ordersArr = paramOrdersStr.split("-JJJFAM-");
            GlobalMemberValues.logWrite("openNewSideMenuStr", "전체문자열 : " + paramOrdersStr + "\n");

            for (int i = 0; i < ordersArr.length; i++) {
                GlobalMemberValues.logWrite("openNewSideMenuStr", "주문하나 문자열 : " + ordersArr[i] + "\n");

                String[] ordersContentsArr = ordersArr[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                String tablepay_salescode = ordersContentsArr[0];
                String tablepay_holdcode = ordersContentsArr[1];
                String tablepay_totalpay = ordersContentsArr[2];
                String tablepay_tablename = ordersContentsArr[3];
                String tablepay_tableidx = ordersContentsArr[4];

                GlobalMemberValues gm = new GlobalMemberValues();
                if (GlobalMemberValues.getTableOrderByHoldCode(tablepay_holdcode) > 0 && gm.isPOSWebPay()) {
                    Intent pushIntent = new Intent(MainActivity.mContext, PushPopupForTablePay.class);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    pushIntent.putExtra("tablepay_salescode", tablepay_salescode);
                    pushIntent.putExtra("tablepay_holdcode", tablepay_holdcode);
                    pushIntent.putExtra("tablepay_totalpay", tablepay_totalpay);
                    pushIntent.putExtra("tablepay_tablename", tablepay_tablename);
                    pushIntent.putExtra("tablepay_tableidx", tablepay_tableidx);


                    MainActivity.mActivity.startActivity(pushIntent);

                }

            }
        }
    }


    public static boolean isOnlineInternet(String paramHost) {
        CheckConnectInternetByGoogle cc = new CheckConnectInternetByGoogle(paramHost);
        cc.start();
        try{
            cc.join();
            return cc.isSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void changeNetworkUI(String paramStatus, int imageDrawable) {
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.logWrite("newreservationcheckstr", "tempStatus : " + paramStatus + "\n");

            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS.setText("");
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS.setBackgroundResource(imageDrawable);

            }
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS.setText(paramStatus);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS.setTextSize(GlobalMemberValues.globalAddFontSize() +
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS.getTextSize()
                                * GlobalMemberValues.getGlobalFontSize()
                );
            }
        }
    }

    public static String getReceiptFooter() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static String getReceiptFooterKitchen2() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter2";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static String getReceiptFooterKitchen3() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter3";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static String getReceiptFooterKitchen4() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter4";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static String getReceiptFooterKitchen5() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter5";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static String getReceiptFooterKitchen6() {
        String returnStr = "";
        String tempSqlQuery = "select receiptfooter from salon_storestationsettings_deviceprinter6";
        String receiptfooter = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (!GlobalMemberValues.isStrEmpty(receiptfooter)) {
            returnStr = receiptfooter + "\r\n";
        }
        return returnStr;
    }

    public static boolean isEloPrinterReceipt() {
        boolean returnValue = false;
        String tempPrinterName = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);
        if (tempPrinterName.equals("Elo")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isEloPrinterKitchen() {
        boolean returnValue = false;
        String tempPrinterName = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
        if (tempPrinterName.equals("Elo")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isEloPrinterKitchen2() {
        boolean returnValue = false;
        String tempPrinterName = GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext);
        if (tempPrinterName.equals("Elo")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isEloPrinterAll() {
        boolean returnValue = false;
        if (isEloPrinterReceipt() && isEloPrinterKitchen()) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isDeviceEloFromDB() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempDeviceElo = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempDeviceElo.equals("Elo")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static boolean isDeviceSunmiFromDB() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempDeviceElo = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempDeviceElo.equals("Sunmi")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static boolean isDevicePosbankFromDB() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempDeviceElo = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempDeviceElo.equals("POSBANK")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static boolean isDeviceElo() {
        return GlobalMemberValues.isThisDeviceElo;
    }

    public static boolean isDeviceClover() {
        return GlobalMemberValues.isThisDeviceClover;
    }

    public static boolean isDevicePAXFromDB() {
        boolean returnValue = false;
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempValue.toUpperCase().equals("PAX")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static boolean isDeviceTabletPCFromDB() {
        boolean returnValue = false;
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempValue.equals("Tablet PC")) {
                returnValue = true;
                mDeviceTabletPC = true;
            } else {
                mDeviceTabletPC = false;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

//    public static boolean isDeviceSunmi(){ return GlobalMemberValues.mDeviceSunmi;}



    public static boolean isPickupTypeHere() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempType = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select picktype_here from salon_storegeneral"), 1);
        if (tempType == "Y" || tempType.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isPickupTypeToGo() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempType = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select picktype_togo from salon_storegeneral"), 1);
        if (tempType == "Y" || tempType.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isPickupTypeDelivery() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempType = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select picktype_delivery from salon_storegeneral"), 1);
        if (tempType == "Y" || tempType.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isPhoneOrder() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempType = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select phoneorder from salon_storegeneral"), 1);
        if (tempType == "Y" || tempType.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static int getCountPickupType() {
        int returnValue = 0;
        if (isPickupTypeHere()) {
            returnValue++;
        }
        if (isPickupTypeToGo()) {
            returnValue++;
        }
        if (isPickupTypeDelivery()) {
            returnValue++;
        }
        return returnValue;
    }

    public static boolean isTipUse() {
        boolean returnValue = false;
        String tempTipUse = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select tipuse from salon_storestationsettings_paymentgateway");
        int vTipUse = 1;
        if (!GlobalMemberValues.isStrEmpty(tempTipUse)) {
            vTipUse = GlobalMemberValues.getIntAtString(tempTipUse);
        }
        if (vTipUse == 0) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    public static boolean isTiplineOntheReceipt() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempTiplineOntheReceipt = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tiplineonreceipt from salon_storestationsettings_paymentgateway"), 1);
        if (GlobalMemberValues.isStrEmpty(tempTiplineOntheReceipt)) {
            tempTiplineOntheReceipt = "N";
        }
        if (tempTiplineOntheReceipt == "Y" || tempTiplineOntheReceipt.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCustomerInfoShow() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempCustomerInfoShow = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select customerinfoshow from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempCustomerInfoShow)) {
            tempCustomerInfoShow = "N";
        }
        if (tempCustomerInfoShow == "Y" || tempCustomerInfoShow.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }


    public static void eloOpenDrawerAtOtherDevice() {
        if (GlobalMemberValues.isDeviceElo()) {
            try {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    GlobalMemberValues.eloOpenDrawer(Elo.mApiAdapter);
                }
            } catch (Exception e) {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
            }
        }
    }


    public static boolean isCustomerSelectReceipt() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select customerselectreceipt from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isSignedPrintYN() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempSignedPrintYN = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select signatureprintyn from salon_storestationsettings_deviceprinter"), 1);
        if (GlobalMemberValues.isStrEmpty(tempSignedPrintYN)) {
            tempSignedPrintYN = "N";
        }
        if (tempSignedPrintYN == "Y" || tempSignedPrintYN.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isSignedPrintSeperately() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempSignedPrintSeperately = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select signatureprintseperately from salon_storestationsettings_deviceprinter"), 1);
        if (GlobalMemberValues.isStrEmpty(tempSignedPrintSeperately)) {
            tempSignedPrintSeperately = "N";
        }
        if (tempSignedPrintSeperately == "Y" || tempSignedPrintSeperately.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static String getCustomerOrderNewNumber(String paramSalesCode) {
        String returnValue = "";

        String strQuery =  "";
        String newCustomerOrderNumber = "";

        DatabaseInit dbInit = new DatabaseInit(Payment.context);

        // 오늘날짜로 검색
        /**
         String dateSearchQuery = " strftime('%m-%d-%Y', wdate) between '" + GlobalMemberValues.STR_NOW_DATE + "' " +
         // strftime('%m-%d-%Y', a.saleDate, 'localtime') 가 아닌
         // 그냥 a.saleDate 로 검색하게 되면 yyyy-mm-dd 로 해야하는데,
         // 그렇게 할 경우 searchEndDate 에 하루를 더해야 함...
         // "' and '" + DateMethodClass.getCustomEditDate(searchEndDate, 0, 0, 1) + "' ";
         " and '" + GlobalMemberValues.STR_NOW_DATE + "' ";
         **/
        String dateSearchQuery = " strftime('%m-%d-%Y', wdate) = '" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet() + "' ";

        // 오늘날짜의 최대 Customer Order Number 구하기
        strQuery = " select customerordernumber from salon_sales_customerordernumber " +
                " where " + dateSearchQuery +
                " order by idx desc limit 1 ";
        String maxCustomerOrderNumber = "";
        maxCustomerOrderNumber = dbInit.dbExecuteReadReturnString(strQuery);

        if (GlobalMemberValues.isStrEmpty(maxCustomerOrderNumber)) {
            maxCustomerOrderNumber = "0000";
        } else {
            // 오늘날짜의 주문에서 Customer Order Number 가 9999 이상일 경우 초기화 (0000)
            if (GlobalMemberValues.getIntAtString(maxCustomerOrderNumber) > 9998) {
                maxCustomerOrderNumber = "0000";
            }
        }

        int intNewCustomerOrderNumber = GlobalMemberValues.getIntAtString(maxCustomerOrderNumber) + 1;
        String tempNewCustomerOrderNumberForString = intNewCustomerOrderNumber + "";

        String tempStr = "";
        for (int i = 0; i < (4 - tempNewCustomerOrderNumberForString.length()); i++) {
            tempStr += "0";
        }

        newCustomerOrderNumber = tempStr + intNewCustomerOrderNumber;

        String returnResult = "";
        Vector<String> strInsertQueryVec = new Vector<String>();
        strQuery = " insert into salon_sales_customerordernumber ( " +
                " scode, sidx, stcode, salesCode, customerordernumber " +
                " ) values ( " +
                " '" + GlobalMemberValues.SALON_CODE + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " '" + paramSalesCode + "', " +
                " '" + newCustomerOrderNumber + "' " +
                " ) ";
        strInsertQueryVec.addElement(strQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("customerordernumberlog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            returnValue = "";
        } else {
            returnValue = "P" + newCustomerOrderNumber;
        }

        return returnValue;
    }

    public static String getCustomerOrderNumber(String paramSalesCode) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempCustomerOrderNumber = dbInit.dbExecuteReadReturnString(
                "select customerordernumber from salon_sales_customerordernumber " +
                        " where salesCode = '" + paramSalesCode + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempCustomerOrderNumber)) {
            returnData = tempCustomerOrderNumber;
        } else {
            returnData = "";
        }

        return returnData;
    }

    public static String getPhoneOrderNewNumber(String paramSalesCode) {
        String returnValue = "";

        String headTxt = "PHONE_";
        if (GlobalMemberValues.now_saletypeisrestaurant) {
            headTxt = "R_";
        }

        String strQuery =  "";
        String newPhoneOrderNumber = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);

        // 먼저 해당 salesCode 에 해당되는 phoneordernumber 아 있는지 확인
        String phoneOrderNumber = dbInit.dbExecuteReadReturnString(
                "select phoneordernumber from salon_sales_phoneordernumber where salesCode = '" + paramSalesCode + "' ");

        GlobalMemberValues.logWrite("phoneordernumberlog", "phoneOrderNumber : " + phoneOrderNumber + "\n");

        if (GlobalMemberValues.isStrEmpty(phoneOrderNumber)) {
            // 오늘날짜로 검색
            String dateSearchQuery = " strftime('%m-%d-%Y', wdate) = '" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet() + "' ";

            // 오늘날짜의 최대 Customer Order Number 구하기
            strQuery = " select phoneordernumber from salon_sales_phoneordernumber " +
                    " where " + dateSearchQuery +
                    " order by idx desc limit 1 ";
            String maxPhoneOrderNumber = "";
            maxPhoneOrderNumber = dbInit.dbExecuteReadReturnString(strQuery);

            if (GlobalMemberValues.isStrEmpty(maxPhoneOrderNumber)) {
                maxPhoneOrderNumber = "0000";
            } else {
                // 오늘날짜의 주문에서 Customer Order Number 가 9999 이상일 경우 초기화 (0000)
                if (GlobalMemberValues.getIntAtString(maxPhoneOrderNumber) > 9998) {
                    maxPhoneOrderNumber = "0000";
                }
            }

            int intNewPhoneOrderNumber = GlobalMemberValues.getIntAtString(maxPhoneOrderNumber) + 1;
            String tempNewPhoneOrderNumberForString = intNewPhoneOrderNumber + "";

            String tempStr = "";
            for (int i = 0; i < (4 - tempNewPhoneOrderNumberForString.length()); i++) {
                tempStr += "0";
            }

            newPhoneOrderNumber = tempStr + intNewPhoneOrderNumber;

            GlobalMemberValues.logWrite("phoneordernumberlog", "newPhoneOrderNumber : " + newPhoneOrderNumber + "\n");

            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            strQuery = " insert into salon_sales_phoneordernumber ( " +
                    " scode, sidx, stcode, salesCode, phoneordernumber " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                    " '" + paramSalesCode + "', " +
                    " '" + newPhoneOrderNumber + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strQuery);

            strQuery = " update temp_salecart_deliveryinfo set " +
                    " phoneorder = 'Y', " +
                    " phoneordernumber = '" + newPhoneOrderNumber + "' " +
                    " where holdcode = '" + paramSalesCode + "' ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("phoneordernumberlog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                returnValue = "";
            } else {
                returnValue = headTxt + newPhoneOrderNumber;
            }
        } else {
            returnValue = headTxt + phoneOrderNumber;
        }

        GlobalMemberValues.logWrite("phoneordernumberlog", "returnValue : " + returnValue + "\n");

        return returnValue;
    }

    public static String getMSRCardNumber(String paramType, String paramValue) {
        String returnData = "";

        if (!GlobalMemberValues.isStrEmpty(paramType) && !GlobalMemberValues.isStrEmpty(paramValue)) {
            String getStartColumnName = "";
            String getCountColumnName = "";

            switch (paramType) {
                case "employee" : {
                    getStartColumnName = "empcardstartnum";
                    getCountColumnName = "empcardcountnum";
                    break;
                }
                case "giftcard" : {
                    getStartColumnName = "giftcardstartnum";
                    getCountColumnName = "giftcardcountnum";
                    break;
                }
            }

            if (!GlobalMemberValues.isStrEmpty(getStartColumnName) && !GlobalMemberValues.isStrEmpty(getCountColumnName)) {
                int tempStrLength = paramValue.length();

                DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);

                String tempStartNumber = dbInit.dbExecuteReadReturnString(
                        "select " + getStartColumnName + " from salon_storestationsettings_system "
                );
                if (GlobalMemberValues.isStrEmpty(tempStartNumber)) {
                    tempStartNumber = "1";
                } else {
                    if (GlobalMemberValues.getIntAtString(tempStartNumber) <= 0) {
                        tempStartNumber = "1";
                    }
                }
                int startNumber = GlobalMemberValues.getIntAtString(tempStartNumber);

                String tempCountNumber = dbInit.dbExecuteReadReturnString(
                        "select " + getCountColumnName + " from salon_storestationsettings_system "
                );
                if (GlobalMemberValues.isStrEmpty(tempCountNumber)) {
                    tempCountNumber = "0";
                }
                int countNumber = GlobalMemberValues.getIntAtString(tempCountNumber);

                /**
                 GlobalMemberValues.displayDialog(MainActivity.mContext, "Info",
                 "paramValue : " + paramValue + "\n" +
                 "tempStrLength : " + tempStrLength + "\n" +
                 "startNumber : " + startNumber + "\n" +
                 "countNumber : " + countNumber + "\n"
                 , "Close");
                 **/

                if (tempStrLength >= startNumber) {
                    int realStartNumber = startNumber - 1;
                    if (countNumber > 0) {
                        int realLastCharIndex = realStartNumber + countNumber;
                        if (realLastCharIndex >= tempStrLength) {
                            returnData = paramValue.substring(realStartNumber);
                        } else {
                            returnData = paramValue.substring(realStartNumber, (realLastCharIndex));
                        }
                    } else {
                        returnData = paramValue.substring(realStartNumber);
                    }
                }
            }
        }

        return returnData;
    }

    public static JSONObject phoneorderPrintingJson(String paramSalesCode, String paramPrintingType, String paramTableInfos) throws JSONException {
        GlobalMemberValues.logWrite("jjjptlog", "여기jjj123" + "\n");

        GlobalMemberValues.logWrite("phoneorderPrintKitchenlog", "paramSalesCode : " + paramSalesCode + "\n");

        GlobalMemberValues.logWrite("phoneorderjsonlog", "tableInfos : " + paramTableInfos + "\n");

        JSONObject jsonroot_kitchen = null;
        jsonroot_kitchen = new JSONObject();

        jsonroot_kitchen.put("receiptno", paramSalesCode);
        jsonroot_kitchen.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());

        boolean isAddSql = true;
        switch (paramPrintingType) {
            case "A" : {
                isAddSql = true;
                break;
            }
            case "R" : {
                isAddSql = true;
                break;
            }
            case "K" : {
                isAddSql = false;
                break;
            }
        }

        String addSql = "";
        String tableName = "";
        String peopleCnt = "";

        String mergednum = "";
        String tableidx = "";
        String subtablenum = "";
        String billnum = "";

        if (!GlobalMemberValues.isStrEmpty(paramTableInfos)) {
            String[] paramTableInfosSplt = paramTableInfos.split("-JJJ-");
            mergednum = paramTableInfosSplt[0];
            tableidx = paramTableInfosSplt[1];
            subtablenum = paramTableInfosSplt[2];
            if (GlobalMemberValues.isStrEmpty(subtablenum)) {
                subtablenum = "1";
            }
            billnum = paramTableInfosSplt[3];
            if (GlobalMemberValues.isStrEmpty(billnum)) {
                billnum = "1";
            }

            if (GlobalMemberValues.getIntAtString(mergednum) == 0) {
                tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(tableidx, "T", "") + "' ");
                if (TableSaleMain.getTableSplitCount(tableidx) > 1) {
                    tableName = tableName + " (S - " + subtablenum + ")";
                }
            } else {
                String mergednumstr = "0" + mergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tableName = mergednumstr;
            }
            peopleCnt = TableSaleMain.getTablePeopleCntByHoldCode(paramSalesCode) + "";
        }

        // Restaurant 관련 데이터 ----------------------------------------------------
        jsonroot_kitchen.put("restaurant_tableidx", tableidx);
        jsonroot_kitchen.put("restaurant_tablename", tableName);
        jsonroot_kitchen.put("restaurant_tablepeoplecnt", peopleCnt);

        jsonroot_kitchen.put("restaurant_tableholdcode", paramSalesCode);
        // ---------------------------------------------------------------------------

        if (isAddSql && !GlobalMemberValues.isStrEmpty(paramTableInfos)) {
            if (GlobalMemberValues.getIntAtString(mergednum) > 0) {
                addSql = " and mergednum = '" + mergednum + "' ";
            } else {
                addSql = " and tableIdx like '%" + tableidx + "%' and subtablenum = '" + subtablenum + "' ";
            }

            addSql = " and billnum = '" + billnum + "' ";
        }

        String addSql2 = "";
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mDeletedSaleCartIdx)) {
//            addSql2 = " and idx = '" + GlobalMemberValues.mDeletedSaleCartIdx + "' ";
            addSql2 = " and idx = '" + GlobalMemberValues.mDeletedSaleCartIdx + "' and kitchenprintedyn = 'Y'";
        }

        if (GlobalMemberValues.mCancelBtnClickYN == "Y" || GlobalMemberValues.mCancelBtnClickYN.equals("Y")) {
            addSql2 = " and idx = '999999999' ";
        }

        // 최초로 키친 프린팅 하는 것인지 확인
        int tempKitchenPrintedItemCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(*) from temp_salecart where holdcode = '" + paramSalesCode + "' and kitchenprintedyn = 'Y' "
        ));

        String tempSaleCartQuery = "";
        tempSaleCartQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " midx, svcIdx, " +
                " svcName, svcFileName, svcFilePath, " +
                " sPrice, sPrice, '', '', " +
                " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                " svcPositionNo, svcSetMenuYN, " +
                " customerId, customerName, customerPhoneNo,  " +
                " saveType, empIdx, empName, quickSaleYN, " +
                " svcCategoryName, " +
                " giftcardNumber, giftcardSavePrice, " +
                " idx, svcCategoryColor, taxExempt, " +
                " reservationCode, " +
                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                " sPriceAmount, sTaxAmount, sTotalAmount, memoToKitchen, kitchenprintedyn, " + // 42
                " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, " + // 43, 44, 45, 46, 47
                " togodelitype, " +
                " stax, labelprintedyn," + // 50 labelprintedyn
                // 04192023
                " pastholdcode " +
                " from temp_salecart " +
                " where holdcode = '" + paramSalesCode + "' " + addSql + addSql2 +
                " order by idx asc";

        GlobalMemberValues.logWrite("jjjwanhayelog2", "tempSaleCartQuery : " + tempSaleCartQuery + "\n");

        // 04192023
        String pastHoldCodeStr = "";

        String itemDetailText = "";
        int itemDetailCount = 0;
        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempSaleCartQuery);
        try {
            while (tempSaleCartCursor.next()) {
                String item_qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);

                String item_itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1);

                String item_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1);

                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                String temp_optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1);
                String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                String temp_additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1);
                String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                String temp_additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1);

                String temp_sPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1);
                String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);
                String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                String temp_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);

                String temp_isQuickSale = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1);

                String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1);

                String temp_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);

                String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);
                String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1),"2");
                String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1), "2");

                String togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1);

                String temp_categoryname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1);

                String temp_stax = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,49), 1), "2");

                String labelprintedYN = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,50), 1);

                // 04192023
                String pastHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,51), 1);

                if (GlobalMemberValues.isStrEmpty(togodelitype)) {
                    togodelitype = "H";
                }

                if (GlobalMemberValues.isStrEmpty(temp_memoToKitchen)) {
                    temp_memoToKitchen = "nokitchenmemo";
                }

                if (GlobalMemberValues.isStrEmpty(temp_kitchenprintedyn)) {
                    temp_kitchenprintedyn = "N";
                }

                // 주방프린팅 여부와 주방프린터 번호 구하기
                String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                String temp_kitchenPrintYN = "N";
                if (item_saveType == "0" || item_saveType.equals("0")) {
                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                        temp_kitchenPrintYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                            temp_kitchenPrintYN = "N";
                        }
                    }
//                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
//                        temp_kitchenPrintYN = MssqlDatabase.getResultSetValueToString(
//                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
//                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
//                            temp_kitchenPrintYN = "N";
//                        }
//                    }
                } else {
                    // 푸드가 아닐 경우 주방프린팅 실행여부는 Y 로 하더라도
                    // 키친프린팅 번호를 99 로 해서 키친프린팅이 되지 않도록 한다.
                    temp_kitchenPrintYN = "Y";
                    tempKitchenPrinterNumber = "99";
                }

                // Common Gratuity 일 경우에는 temp_kitchenPrintYN 을 Y 로 처리한다
                if (item_itemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                    temp_kitchenPrintYN = "Y";
                }

                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPrice : " + temp_sPrice + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPriceAmount : " + temp_sPriceAmount + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTaxAmount : " + temp_sTaxAmount + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTotalAmount : " + temp_sTotalAmount + "\n");

                boolean isPrintItem = true;
                String str_additem = "N";
                if (Recall.mKitchenPrintOnRecall == "Y" || Recall.mKitchenPrintOnRecall.equals("Y")) {
                    isPrintItem = true;
                    if (!temp_kitchenprintedyn.equals("N")) {
                        isPrintItem = true;
                    } else {
                        isPrintItem = false;
                        str_additem = "Y";
                    }
                } else {
                    switch (paramPrintingType) {
                        case "A" : {

                            if (!temp_kitchenprintedyn.equals("N")) {
                                isPrintItem = true;
                            } else {
                                isPrintItem = false;
//                            isPrintItem = true;
                                str_additem = "Y";
                            }
                            break;
                        }
                        case "R" : {
                            isPrintItem = true;
                            break;
                        }
                        case "K" : {
                            if (!temp_kitchenprintedyn.equals("N")) {
                                isPrintItem = true;
                            } else {
                                isPrintItem = false;
                                str_additem = "Y";
                            }
                            break;
                        }
                    }
                }

//            item_itemName = str_test_add + item_itemName;
                if (tempKitchenPrintedItemCnt == 0) {
                    str_additem = "N";
                }

                // common gratuity
                if (item_itemName.equals(GlobalMemberValues.mCommonGratuityName)){
                    temp_sTaxAmount = "0.00";
                    temp_stax = "0.00";
                }


                // 08102023
                // alt name 구하기
                String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                    temp_itemName_alt = "";
                }

                String item_itemname_optionadd = item_itemName +
                        "-ANNIETTASU-" + temp_optionTxt +
                        "-ANNIETTASU-" + temp_additionalTxt1 +
                        "-ANNIETTASU-" + temp_additionalTxt2 +
                        "-ANNIETTASU-" + temp_itemIdx +
                        "-ANNIETTASU-" + tempKitchenPrinterNumber +
                        "-ANNIETTASU-" + temp_memoToKitchen +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_optionprice, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice1, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice2, "2") +
                        "-ANNIETTASU-" + selectedDcExtraAllEach +
                        "-ANNIETTASU-" + selectedDcExtraType +
                        "-ANNIETTASU-" + dcextratype +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(dcextravalue, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(selectedDcExtraPrice, "2") +
                        "-ANNIETTASU-" + togodelitype +
                        "-ANNIETTASU-" + temp_categoryname +
                        "-ANNIETTASU-" + str_additem +
                        "-ANNIETTASU-" + labelprintedYN +

                        // 08102023
                        "-ANNIETTASU-" + temp_itemName_alt;

                GlobalMemberValues.logWrite("phoneorddermodifierlog", "item_itemname_optionadd : " + item_itemname_optionadd + "\n");

                GlobalMemberValues.logWrite("jjjwanhayelog", "mKitchenPrintOnRecall : " + Recall.mKitchenPrintOnRecall + "\n");
                GlobalMemberValues.logWrite("jjjwanhayelog", "temp_kitchenPrintYN : " + temp_kitchenPrintYN + "\n");

                GlobalMemberValues.logWrite("jjjtogodelitypelog", "* togodelitype : " + togodelitype + "\n");


                if (isPrintItem) {

                }

                String temp_kitchenprintnum = "0";
                temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y") || temp_isQuickSale == "Y" || temp_isQuickSale.equals("Y")) {

                    if (itemDetailCount == 0) {
//                            itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
//                                    "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_sTaxAmount + "-JJJ-" + temp_sTotalAmount;
                        itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
                                "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                        // 04192023
                        pastHoldCodeStr = pastHoldCode;
                    } else {
                        itemDetailText += "-WANHAYE-" + item_itemname_optionadd + "-JJJ-" + item_qty +
                                "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                        // 04192023
                        pastHoldCodeStr += "," + pastHoldCode;
                    }
                    itemDetailCount++;
                }
            }
            tempSaleCartCursor.close();
        } catch (Exception e){

        }

        GlobalMemberValues.logWrite("jjjwanhayelog", "itemDetailText : " + itemDetailText + "\n");

        jsonroot_kitchen.put("saleitemlist", itemDetailText);

        // 04192023
        jsonroot_kitchen.put("pastholdcode", pastHoldCodeStr);


        // temp_salecart_deliveryinfo 테이블에서 배송관련정보 가져온다.
        String temp_customerId = "";
        String temp_customerName = "";
        String temp_customerPhone = "";
        String temp_customerEmail = "";
        String temp_customerAddr1 = "";
        String temp_customerAddr2 = "";
        String temp_customerCity = "";
        String temp_customerState = "";
        String temp_customerZip = "";
        String temp_deliveryday = "";
        String temp_deliverytime = "";
        String temp_deliverydate = "";
        String temp_deliverytakeaway = "";
        String temp_customermemo = "";
        String temp_customerAddrAll = "";

        String strQuery = "select customerId, customerName, customerPhone, customerEmail, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo " +
                " from temp_salecart_deliveryinfo " +
                " where holdcode = '" + paramSalesCode + "' ";
        GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
        ResultSet deliverytakeawayInfoCursor = MssqlDatabase.getResultSetValue(strQuery);
        try {
            while (deliverytakeawayInfoCursor.next()){
                temp_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,0), 1);
                temp_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,1), 1);
                temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,2), 1);
                temp_customerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,3), 1);

                temp_customerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,4), 1);
                temp_customerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,5), 1);
                temp_customerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,6), 1);
                temp_customerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,7), 1);
                temp_customerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,8), 1);

                temp_deliveryday = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,9), 1);
                temp_deliverytime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,10), 1);
                temp_deliverydate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,11), 1);
                temp_deliverytakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,12), 1);
                if (!GlobalMemberValues.isToGoSale()) {
                    temp_deliverytakeaway = "H";
                }

                temp_customermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,13), 1);

                if (!GlobalMemberValues.isStrEmpty(temp_customerAddr1)
                        || !GlobalMemberValues.isStrEmpty(temp_customerAddr2)
                        || !GlobalMemberValues.isStrEmpty(temp_customerCity)
                        || !GlobalMemberValues.isStrEmpty(temp_customerState)
                        || !GlobalMemberValues.isStrEmpty(temp_customerZip)) {

                    temp_customerAddrAll = temp_customerAddr1;

                    if (!GlobalMemberValues.isStrEmpty(temp_customerAddr2)) {
                        temp_customerAddrAll += " " + temp_customerAddr2;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerCity)) {
                        temp_customerAddrAll += "\n" + temp_customerCity;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerState)) {
                        temp_customerAddrAll += ", " + temp_customerState;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerZip)) {
                        temp_customerAddrAll += " " + temp_customerZip;
                    }
                }
            }
            deliverytakeawayInfoCursor.close();
//            if (deliverytakeawayInfoCursor.getCount() > 0 && deliverytakeawayInfoCursor.moveToFirst()) {
//
//            }
        }catch (Exception e){

        }

        GlobalMemberValues.logWrite("tempcustmemojjjinfo", "GlobalMemberValues.mTempCustomerInfo...2 : " + GlobalMemberValues.mTempCustomerInfo + "\n");
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mTempCustomerInfo)) {
            String tempCustValue[] = GlobalMemberValues.mTempCustomerInfo.split("-JJJ-");

            temp_customerPhone = tempCustValue[0];
            if (tempCustValue.length > 1) {
                temp_customerName = tempCustValue[1];
            }
            if (tempCustValue.length > 2) {
                temp_customermemo = tempCustValue[2];
            }

            GlobalMemberValues.mTempCustomerInfo = "";
        }

        GlobalMemberValues.logWrite("customerinfojjjlog", "temp_customerPhone : " + temp_customerPhone + "\n");

        jsonroot_kitchen.put("customername", temp_customerName);

        jsonroot_kitchen.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot_kitchen.put("deliverydate", temp_deliverydate);
        jsonroot_kitchen.put("ordertype", "POS");

        jsonroot_kitchen.put("customermemo", temp_customermemo);

        jsonroot_kitchen.put("phoneorder", "Y");

        jsonroot_kitchen.put("phoneordernumber", GlobalMemberValues.getPhoneOrderNewNumber(paramSalesCode));

        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }

        jsonroot_kitchen.put("storename", storeNameForReceipt);  // JSON
        jsonroot_kitchen.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot_kitchen.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot_kitchen.put("storecity", storeCityForReceipt);  // JSON
        jsonroot_kitchen.put("storestate", storeStateForReceipt);  // JSON
        jsonroot_kitchen.put("storezip", storeZipForReceipt);  // JSON
        jsonroot_kitchen.put("storephone", storePhoneForReceipt);  // JSON

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot_kitchen.put("customerphonenum", temp_customerPhone);
        jsonroot_kitchen.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        /******************************************************************/

        GlobalMemberValues.PHONEORDER_HOLDCODE = paramSalesCode;

        GlobalMemberValues.logWrite("jsonjjjptlog", "json 값111 : " + jsonroot_kitchen.toString() + "\n");

        return jsonroot_kitchen;
    }

    public static JSONObject TableSaleMenuPrintingJson(String paramSalesCode, String paramPrintingType, String paramTableInfos, String param_splitmerge_total, String[] paramSplitMergeInfo) throws JSONException {
        GlobalMemberValues.logWrite("jjjptlog", "여기jjj" + "\n");

        GlobalMemberValues.logWrite("phoneorderPrintKitchenlog", "paramSalesCode : " + paramSalesCode + "\n");

        GlobalMemberValues.logWrite("phoneorderjsonlog", "tableInfos : " + paramTableInfos + "\n");

        JSONObject jsonroot_kitchen = null;
        jsonroot_kitchen = new JSONObject();

        jsonroot_kitchen.put("receiptno", paramSalesCode);
        jsonroot_kitchen.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());

        boolean isAddSql = true;
        switch (paramPrintingType) {
            case "A" : {
                isAddSql = true;
                break;
            }
            case "R" : {
                isAddSql = true;
                break;
            }
            case "K" : {
                isAddSql = false;
                break;
            }
        }

        String addSql = "";
        String tableName = "";
        String peopleCnt = "";

        String mergednum = "";
        String tableidx = "";
        String subtablenum = "";
        String billnum = "";

        if (!GlobalMemberValues.isStrEmpty(paramTableInfos)) {
            String[] paramTableInfosSplt = paramTableInfos.split("-JJJ-");
            mergednum = paramTableInfosSplt[0];
            tableidx = paramTableInfosSplt[1];
            subtablenum = paramTableInfosSplt[2];
            if (GlobalMemberValues.isStrEmpty(subtablenum)) {
                subtablenum = "1";
            }
            billnum = paramTableInfosSplt[3];
            if (GlobalMemberValues.isStrEmpty(billnum)) {
                billnum = "1";
            }

            if (GlobalMemberValues.getIntAtString(mergednum) == 0) {
                tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(tableidx, "T", "") + "' ");
                if (TableSaleMain.getTableSplitCount(tableidx) > 1) {
                    tableName = tableName + " (S - " + subtablenum + ")";
                }
            } else {
                String mergednumstr = "0" + mergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tableName = mergednumstr;
            }
            peopleCnt = TableSaleMain.getTablePeopleCntByHoldCode(paramSalesCode) + "";
        }

        // Restaurant 관련 데이터 ----------------------------------------------------
        jsonroot_kitchen.put("restaurant_tableidx", tableidx);
        jsonroot_kitchen.put("restaurant_tablename", tableName);
        jsonroot_kitchen.put("restaurant_tablepeoplecnt", peopleCnt);

        jsonroot_kitchen.put("restaurant_tableholdcode", paramSalesCode);
        // ---------------------------------------------------------------------------

        if (isAddSql && !GlobalMemberValues.isStrEmpty(paramTableInfos)) {
            if (GlobalMemberValues.getIntAtString(mergednum) > 0) {
                addSql = " and mergednum = '" + mergednum + "' ";
            } else {
                addSql = " and tableIdx like '%" + tableidx + "%' and subtablenum = '" + subtablenum + "' ";
            }

            addSql = " and billnum = '" + billnum + "' ";
        }

        String addSql2 = "";
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mDeletedSaleCartIdx)) {
            addSql2 = " and idx = '" + GlobalMemberValues.mDeletedSaleCartIdx + "' ";
        }

        String item_qty = "";
        String tempJsonStr = "";
        String itemDetailText = "";
        int itemDetailCount = 0;
        JSONObject tempJson;

        String[] cartidxs = null;

        int i_view_kind = 0;

        /////////////////////////////
        double d_param_splitmerge_total = GlobalMemberValues.getDoubleAtString(param_splitmerge_total);
        if (paramSplitMergeInfo != null) {
            if (paramSplitMergeInfo.length >= 4) {
                cartidxs = paramSplitMergeInfo[4].split(",");
                if (cartidxs != null){
                    if (cartidxs.length > 0){
                        if (!GlobalMemberValues.isStrEmpty(cartidxs[0])){
                            i_view_kind = 1;
                        } else {
                            i_view_kind = 0;
                        }
                    } else {
                        i_view_kind = 0;
                    }
                } else {
                    i_view_kind = 0;
                }
            } else {
                i_view_kind = 0;
            }
        } else {
            i_view_kind = 0;
        }

        // 04192023
        String pastHoldCodeStr = "";

        if (i_view_kind == 0) {
            String tempSaleCartQuery = "";
            tempSaleCartQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                    " midx, svcIdx, " +
                    " svcName, svcFileName, svcFilePath, " +
                    " sPrice, sPrice, '', '', " +
                    " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                    " svcPositionNo, svcSetMenuYN, " +
                    " customerId, customerName, customerPhoneNo,  " +
                    " saveType, empIdx, empName, quickSaleYN, " +
                    " svcCategoryName, " +
                    " giftcardNumber, giftcardSavePrice, " +
                    " idx, svcCategoryColor, taxExempt, " +
                    " reservationCode, " +
                    " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                    " sPriceAmount, sTaxAmount, sTotalAmount, memoToKitchen, kitchenprintedyn, " + // 42
                    " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, " +
                    " togodelitype, " +
                    " stax, " +
                    // 04192023
                    " pastholdcode " +
                    " from temp_salecart " +
                    " where holdcode = '" + paramSalesCode + "' " + addSql + addSql2 +
                    " order by idx asc";

            GlobalMemberValues.logWrite("jjjptlog", "sql : " + tempSaleCartQuery + "\n");
//            String itemDetailText = "";
//            int itemDetailCount = 0;
            ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempSaleCartQuery);
            try {
                // 04192023
                int tempCursorCnt = 0;

                while (tempSaleCartCursor.next()) {
                    item_qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);

                    String item_itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1);

                    String item_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1);

                    // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                    String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                    String temp_optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1);
                    String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                    String temp_additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1);
                    String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                    String temp_additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1);

                    String temp_sPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1);
                    String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                    String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);
                    String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                    String temp_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);

                    String temp_isQuickSale = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1);

                    String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1);

                    String temp_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);

                    String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);
                    String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                    String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                    String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1),"2");
                    String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1), "2");

                    String togodelitype = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1), "2");
                    if (GlobalMemberValues.isStrEmpty(togodelitype)) {
                        togodelitype = "H";
                    }

                    String temp_categoryname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1);

                    String temp_stax = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,49), 1), "2");

                    // 04192023
                    String pastholdcode = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,50), 1), "2");
                    if (tempCursorCnt == 0) {
                        pastHoldCodeStr = pastholdcode;
                    } else {
                        pastHoldCodeStr += "," + pastholdcode;
                    }


                    if (GlobalMemberValues.isStrEmpty(temp_memoToKitchen)) {
                        temp_memoToKitchen = "nokitchenmemo";
                    }

                    if (GlobalMemberValues.isStrEmpty(temp_kitchenprintedyn)) {
                        temp_kitchenprintedyn = "N";
                    }

                    // 주방프린팅 여부와 주방프린터 번호 구하기
                    String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                    String temp_kitchenPrintYN = "N";
                    if (item_saveType == "0" || item_saveType.equals("0")) {
                        if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                            temp_kitchenPrintYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                                    "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                            if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                                temp_kitchenPrintYN = "N";
                            }
                        }
//                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
//                        temp_kitchenPrintYN = MssqlDatabase.getResultSetValueToString(
//                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
//                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
//                            temp_kitchenPrintYN = "N";
//                        }
//                    }
                    } else {
                        // 푸드가 아닐 경우 주방프린팅 실행여부는 Y 로 하더라도
                        // 키친프린팅 번호를 99 로 해서 키친프린팅이 되지 않도록 한다.
                        temp_kitchenPrintYN = "Y";
                        tempKitchenPrinterNumber = "99";
                    }

                    // Common Gratuity 일 경우에는 temp_kitchenPrintYN 을 Y 로 처리한다
                    if (item_itemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                        temp_kitchenPrintYN = "Y";
                    }

                    GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPrice : " + temp_sPrice + "\n");
                    GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPriceAmount : " + temp_sPriceAmount + "\n");
                    GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTaxAmount : " + temp_sTaxAmount + "\n");
                    GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTotalAmount : " + temp_sTotalAmount + "\n");


                    // 08102023
                    // alt name 구하기
                    String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                    );
                    if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                        temp_itemName_alt = "";
                    }

                    String item_itemname_optionadd = item_itemName +
                            "-ANNIETTASU-" + temp_optionTxt +
                            "-ANNIETTASU-" + temp_additionalTxt1 +
                            "-ANNIETTASU-" + temp_additionalTxt2 +
                            "-ANNIETTASU-" + temp_itemIdx +
                            "-ANNIETTASU-" + tempKitchenPrinterNumber +
                            "-ANNIETTASU-" + temp_memoToKitchen +
                            "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_optionprice, "2") +
                            "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice1, "2") +
                            "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice2, "2") +
                            "-ANNIETTASU-" + selectedDcExtraAllEach +
                            "-ANNIETTASU-" + selectedDcExtraType +
                            "-ANNIETTASU-" + dcextratype +
                            "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(dcextravalue, "2") +
                            "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(selectedDcExtraPrice, "2") +
                            "-ANNIETTASU-" + togodelitype +
                            "-ANNIETTASU-" + temp_categoryname +

                            // 08102023
                            "-ANNIETTASU-" + "" +          // additem
                            "-ANNIETTASU-" + "" +       // labelprinterYN

                            // 08102023
                            "-ANNIETTASU-" + temp_itemName_alt;

                    GlobalMemberValues.logWrite("phoneorddermodifierlog", "item_itemname_optionadd : " + item_itemname_optionadd + "\n");

                    boolean isPrintItem = true;
                    if (Recall.mKitchenPrintOnRecall == "Y" || Recall.mKitchenPrintOnRecall.equals("Y")) {
                        isPrintItem = true;
                    } else {
                        switch (paramPrintingType) {
                            case "A" : {
                                isPrintItem = true;
                                break;
                            }
                            case "R" : {
                                isPrintItem = true;
                                break;
                            }
                            case "K" : {
                                if (temp_kitchenprintedyn.equals("N")) {
                                    isPrintItem = true;
                                } else {
                                    isPrintItem = false;
                                }
                                break;
                            }
                        }
                    }

                    String temp_kitchenprintnum = "0";
                    temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                    if (isPrintItem) {
                        // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                        if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y") || temp_isQuickSale == "Y" || temp_isQuickSale.equals("Y")) {
                            if (itemDetailCount == 0) {
                                itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
                                        "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                        "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                        "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                            } else {
                                itemDetailText += "-WANHAYE-" + item_itemname_optionadd + "-JJJ-" + item_qty +
                                        "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                        "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                        "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;
                            }
                            itemDetailCount++;
                        }
                    }

                    // 04192023
                    tempCursorCnt++;
                }
                tempSaleCartCursor.close();
            }catch (Exception e){

            }
        } else if (i_view_kind == 1) {
            cartidxs = paramSplitMergeInfo[4].split(",");
            for(String str_cartidx : cartidxs) {

                String tempCartidxQuery = "";
                tempCartidxQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                        " midx, svcIdx, " +
                        " svcName, svcFileName, svcFilePath, " +
                        " sPrice, sPrice, '', '', " +
                        " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                        " svcPositionNo, svcSetMenuYN, " +
                        " customerId, customerName, customerPhoneNo,  " +
                        " saveType, empIdx, empName, quickSaleYN, " +
                        " svcCategoryName, " +
                        " giftcardNumber, giftcardSavePrice, " +
                        " idx, svcCategoryColor, taxExempt, " +
                        " reservationCode, " +
                        " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                        " sPriceAmount, sTaxAmount, sTotalAmount, memoToKitchen, kitchenprintedyn, " + // 42
                        " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, " +
                        " togodelitype, " +
                        " stax, " +
                        // 04192023
                        " pastholdcode " +
                        " from temp_salecart " +
                        " where idx = '" + str_cartidx + "' ";

                GlobalMemberValues.logWrite("jjjptlog", "sql : " + tempCartidxQuery + "\n");

                ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempCartidxQuery);
                try {
                    // 04192023
                    int tempCursorCnt = 0;
                    while (tempSaleCartCursor.next()) {
                        item_qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);


                        String item_itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1);

                        String item_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1);

                        // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                        String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                        String temp_optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1);
                        String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                        String temp_additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1);
                        String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                        String temp_additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1);

                        String temp_sPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1);
                        String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                        String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);
                        String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                        String temp_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);

                        String temp_isQuickSale = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1);

                        String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1);

                        String temp_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);

                        String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);
                        String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                        String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                        String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1),"2");
                        String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1), "2");

                        String togodelitype = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1), "2");
                        if (GlobalMemberValues.isStrEmpty(togodelitype)) {
                            togodelitype = "H";
                        }

                        String temp_categoryname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1);

                        String temp_stax = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,49), 1), "2");

                        // 04192023
                        String pastholdcode = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,50), 1), "2");
                        if (tempCursorCnt == 0) {
                            pastHoldCodeStr = pastholdcode;
                        } else {
                            pastHoldCodeStr += "," + pastholdcode;
                        }

                        if (GlobalMemberValues.isStrEmpty(temp_memoToKitchen)) {
                            temp_memoToKitchen = "nokitchenmemo";
                        }

                        if (GlobalMemberValues.isStrEmpty(temp_kitchenprintedyn)) {
                            temp_kitchenprintedyn = "N";
                        }

                        // 주방프린팅 여부와 주방프린터 번호 구하기
                        String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                        String temp_kitchenPrintYN = "N";
                        if (item_saveType == "0" || item_saveType.equals("0")) {
                            if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                                temp_kitchenPrintYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                                        "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                                if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                                    temp_kitchenPrintYN = "N";
                                }
                            }
//                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
//                        temp_kitchenPrintYN = MssqlDatabase.getResultSetValueToString(
//                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
//                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
//                            temp_kitchenPrintYN = "N";
//                        }
//                    }
                        } else {
                            // 푸드가 아닐 경우 주방프린팅 실행여부는 Y 로 하더라도
                            // 키친프린팅 번호를 99 로 해서 키친프린팅이 되지 않도록 한다.
                            temp_kitchenPrintYN = "Y";
                            tempKitchenPrinterNumber = "99";
                        }

                        // Common Gratuity 일 경우에는 temp_kitchenPrintYN 을 Y 로 처리한다
                        if (item_itemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                            temp_kitchenPrintYN = "Y";
                        }

                        GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPrice : " + temp_sPrice + "\n");
                        GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPriceAmount : " + temp_sPriceAmount + "\n");
                        GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTaxAmount : " + temp_sTaxAmount + "\n");
                        GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTotalAmount : " + temp_sTotalAmount + "\n");


                        // 08102023
                        // alt name 구하기
                        String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                        );
                        if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                            temp_itemName_alt = "";
                        }


                        String item_itemname_optionadd = item_itemName +
                                "-ANNIETTASU-" + temp_optionTxt +
                                "-ANNIETTASU-" + temp_additionalTxt1 +
                                "-ANNIETTASU-" + temp_additionalTxt2 +
                                "-ANNIETTASU-" + temp_itemIdx +
                                "-ANNIETTASU-" + tempKitchenPrinterNumber +
                                "-ANNIETTASU-" + temp_memoToKitchen +
                                "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_optionprice, "2") +
                                "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice1, "2") +
                                "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice2, "2") +
                                "-ANNIETTASU-" + selectedDcExtraAllEach +
                                "-ANNIETTASU-" + selectedDcExtraType +
                                "-ANNIETTASU-" + dcextratype +
                                "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(dcextravalue, "2") +
                                "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(selectedDcExtraPrice, "2") +
                                "-ANNIETTASU-" + togodelitype +
                                "-ANNIETTASU-" + temp_categoryname +

                                // 08102023
                                "-ANNIETTASU-" + "" +          // additem
                                "-ANNIETTASU-" + "" +       // labelprinterYN

                                // 08102023
                                "-ANNIETTASU-" + temp_itemName_alt;

                        GlobalMemberValues.logWrite("phoneorddermodifierlog", "item_itemname_optionadd : " + item_itemname_optionadd + "\n");

                        boolean isPrintItem = true;
                        if (Recall.mKitchenPrintOnRecall == "Y" || Recall.mKitchenPrintOnRecall.equals("Y")) {
                            isPrintItem = true;
                        } else {
                            switch (paramPrintingType) {
                                case "A" : {
                                    isPrintItem = true;
                                    break;
                                }
                                case "R" : {
                                    isPrintItem = true;
                                    break;
                                }
                                case "K" : {
                                    if (temp_kitchenprintedyn.equals("N")) {
                                        isPrintItem = true;
                                    } else {
                                        isPrintItem = false;
                                    }
                                    break;
                                }
                            }
                        }


                        String temp_kitchenprintnum = "0";
                        temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);


                        if (isPrintItem) {
                            // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                            if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y") || temp_isQuickSale == "Y" || temp_isQuickSale.equals("Y")) {
                                if (itemDetailCount == 0) {
//                                    itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
//                                            "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_sTaxAmount + "-JJJ-" + temp_sTotalAmount;
                                    itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
                                            "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                            "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                            "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                                } else {
                                    itemDetailText += "-WANHAYE-" + item_itemname_optionadd + "-JJJ-" + item_qty +
                                            "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                            "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                            "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;
                                }
                                itemDetailCount++;
                            }
                        }

//                        tempJsonStr = item_qty;

                        tempCursorCnt++;

                    }
                    tempSaleCartCursor.close();
                } catch (Exception e){

                }
            }
        }

        /////////////////////////////




        GlobalMemberValues.logWrite("phoneorderitemlog", "itemDetailText : " + itemDetailText + "\n");

        jsonroot_kitchen.put("saleitemlist", itemDetailText);

        // 04192023
        jsonroot_kitchen.put("pastholdcode", pastHoldCodeStr);


        // temp_salecart_deliveryinfo 테이블에서 배송관련정보 가져온다.
        String temp_customerId = "";
        String temp_customerName = "";
        String temp_customerPhone = "";
        String temp_customerEmail = "";
        String temp_customerAddr1 = "";
        String temp_customerAddr2 = "";
        String temp_customerCity = "";
        String temp_customerState = "";
        String temp_customerZip = "";
        String temp_deliveryday = "";
        String temp_deliverytime = "";
        String temp_deliverydate = "";
        String temp_deliverytakeaway = "";
        String temp_customermemo = "";
        String temp_customerAddrAll = "";

        String strQuery = "select customerId, customerName, customerPhone, customerEmail, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo " +
                " from temp_salecart_deliveryinfo " +
                " where holdcode = '" + paramSalesCode + "' ";
        GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
        ResultSet deliverytakeawayInfoCursor = MssqlDatabase.getResultSetValue(strQuery);
        try {
            while (deliverytakeawayInfoCursor.next()){
                temp_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,0), 1);
                temp_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,1), 1);
                temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,2), 1);
                temp_customerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,3), 1);

                temp_customerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,4), 1);
                temp_customerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,5), 1);
                temp_customerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,6), 1);
                temp_customerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,7), 1);
                temp_customerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,8), 1);

                temp_deliveryday = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,9), 1);
                temp_deliverytime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,10), 1);
                temp_deliverydate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,11), 1);
                temp_deliverytakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,12), 1);
                if (!GlobalMemberValues.isToGoSale()) {
                    temp_deliverytakeaway = "H";
                }

                temp_customermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,13), 1);

                if (!GlobalMemberValues.isStrEmpty(temp_customerAddr1)
                        || !GlobalMemberValues.isStrEmpty(temp_customerAddr2)
                        || !GlobalMemberValues.isStrEmpty(temp_customerCity)
                        || !GlobalMemberValues.isStrEmpty(temp_customerState)
                        || !GlobalMemberValues.isStrEmpty(temp_customerZip)) {

                    temp_customerAddrAll = temp_customerAddr1;

                    if (!GlobalMemberValues.isStrEmpty(temp_customerAddr2)) {
                        temp_customerAddrAll += " " + temp_customerAddr2;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerCity)) {
                        temp_customerAddrAll += "\n" + temp_customerCity;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerState)) {
                        temp_customerAddrAll += ", " + temp_customerState;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerZip)) {
                        temp_customerAddrAll += " " + temp_customerZip;
                    }
                }
            }
            deliverytakeawayInfoCursor.close();
//            if (deliverytakeawayInfoCursor.getCount() > 0 && deliverytakeawayInfoCursor.moveToFirst()) {
//
//            }
        }catch (Exception e){

        }

        GlobalMemberValues.logWrite("tempcustmemojjjinfo", "GlobalMemberValues.mTempCustomerInfo...3 : " + GlobalMemberValues.mTempCustomerInfo + "\n");
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mTempCustomerInfo)) {
            String tempCustValue[] = GlobalMemberValues.mTempCustomerInfo.split("-JJJ-");

            temp_customerPhone = tempCustValue[0];
            if (tempCustValue.length > 1) {
                temp_customerName = tempCustValue[1];
            }
            if (tempCustValue.length > 2) {
                temp_customermemo = tempCustValue[2];
            }

            GlobalMemberValues.mTempCustomerInfo = "";
        }

        jsonroot_kitchen.put("customername", temp_customerName);

        jsonroot_kitchen.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot_kitchen.put("deliverydate", temp_deliverydate);
        jsonroot_kitchen.put("ordertype", "POS");

        jsonroot_kitchen.put("customermemo", temp_customermemo);

        jsonroot_kitchen.put("phoneorder", "Y");

        jsonroot_kitchen.put("phoneordernumber", GlobalMemberValues.getPhoneOrderNewNumber(paramSalesCode));

        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }

        strQuery = "select sTotalAmount " +
                " from temp_salecart " +
                " where holdcode = '" + paramSalesCode + "' ";
        ResultSet sTotalAmountCursor = MssqlDatabase.getResultSetValue(strQuery);
        double temp_stotalAmount = 0.00;
        try {
            while (sTotalAmountCursor.next()) {
                String totalamount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(sTotalAmountCursor, 0), 1);
                temp_stotalAmount += GlobalMemberValues.getDoubleAtString(totalamount);
            }
            sTotalAmountCursor.close();
        } catch (Exception e){

        }

        jsonroot_kitchen.put("allitem_totalamount", GlobalMemberValues.getStringFormatNumber(temp_stotalAmount, "2"));

        jsonroot_kitchen.put("storename", storeNameForReceipt);  // JSON
        jsonroot_kitchen.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot_kitchen.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot_kitchen.put("storecity", storeCityForReceipt);  // JSON
        jsonroot_kitchen.put("storestate", storeStateForReceipt);  // JSON
        jsonroot_kitchen.put("storezip", storeZipForReceipt);  // JSON
        jsonroot_kitchen.put("storephone", storePhoneForReceipt);  // JSON

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot_kitchen.put("customerphonenum", temp_customerPhone);
        jsonroot_kitchen.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        /******************************************************************/

        GlobalMemberValues.PHONEORDER_HOLDCODE = paramSalesCode;

        GlobalMemberValues.logWrite("jsonjjjptlog", "json 값222 : " + jsonroot_kitchen.toString() + "\n");

        return jsonroot_kitchen;
    }

    // 03112022
    public static JSONObject getKitchenPrintingJsonForWindows(String paramSalesCode, String paramTableInfos, String paramDelCartIdx) throws JSONException {
        JSONObject jsonroot_kitchen = null;
        jsonroot_kitchen = new JSONObject();

        jsonroot_kitchen.put("receiptno", paramSalesCode);
        jsonroot_kitchen.put("saledate", DateMethodClass.nowMonthGet() + "/" + DateMethodClass.nowDayGet() + "/" + DateMethodClass.nowYearGet());
        jsonroot_kitchen.put("saletime", DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet() + ":" + DateMethodClass.nowSecondGet());

        boolean isAddSql = false;

        String addSql = "";
        String tableName = "";
        String peopleCnt = "";

        String mergednum = "";
        String tableidx = "";
        String subtablenum = "";
        String billnum = "";

        if (!GlobalMemberValues.isStrEmpty(paramTableInfos)) {
            String[] paramTableInfosSplt = paramTableInfos.split("-JJJ-");
            mergednum = paramTableInfosSplt[0];
            tableidx = paramTableInfosSplt[1];
            subtablenum = paramTableInfosSplt[2];
            if (GlobalMemberValues.isStrEmpty(subtablenum)) {
                subtablenum = "1";
            }
            billnum = paramTableInfosSplt[3];
            if (GlobalMemberValues.isStrEmpty(billnum)) {
                billnum = "1";
            }

            if (GlobalMemberValues.getIntAtString(mergednum) == 0) {
                tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(tableidx, "T", "") + "' ");
                if (TableSaleMain.getTableSplitCount(tableidx) > 1) {
                    tableName = tableName + " (S - " + subtablenum + ")";
                }
            } else {
                String mergednumstr = "0" + mergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tableName = mergednumstr;
            }
            peopleCnt = TableSaleMain.getTablePeopleCntByHoldCode(paramSalesCode) + "";
        }

        // Restaurant 관련 데이터 ----------------------------------------------------
        jsonroot_kitchen.put("restaurant_tableidx", tableidx);
        jsonroot_kitchen.put("restaurant_tablename", tableName);
        jsonroot_kitchen.put("restaurant_tablepeoplecnt", peopleCnt);

        jsonroot_kitchen.put("restaurant_tableholdcode", paramSalesCode);
        // ---------------------------------------------------------------------------

        // 최초로 키친 프린팅 하는 것인지 확인
        int tempKitchenPrintedItemCnt = 0;

        String tempSaleCartQuery = "";
        tempSaleCartQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " midx, svcIdx, " +
                " svcName, svcFileName, svcFilePath, " +
                " sPrice, sPrice, '', '', " +
                " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                " svcPositionNo, svcSetMenuYN, " +
                " customerId, customerName, customerPhoneNo,  " +
                " saveType, empIdx, empName, quickSaleYN, " +
                " svcCategoryName, " +
                " giftcardNumber, giftcardSavePrice, " +
                " idx, svcCategoryColor, taxExempt, " +
                " reservationCode, " +
                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                " sPriceAmount, sTaxAmount, sTotalAmount, memoToKitchen, kitchenprintedyn, " + // 42
                " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice, " + // 43, 44, 45, 46, 47
                " togodelitype, " +
                " stax, labelprintedyn, " +
                // 04192023
                " pastholdcode " +
                " " +
                " from temp_salecart " +
                " where holdcode = '" + paramSalesCode + "' " +
                " and not(idx = '" + paramDelCartIdx + "') " +
                " order by idx asc";

        GlobalMemberValues.logWrite("jjjwanhayelog333555", "tempSaleCartQuery : " + tempSaleCartQuery + "\n");

        // 04192023
        String pastHoldCodeStr = "";

        String itemDetailText = "";
        int itemDetailCount = 0;
        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempSaleCartQuery);
        try {
            while (tempSaleCartCursor.next()) {
                String item_qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);

                String item_itemName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1);

                String item_saveType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1);

                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                String temp_optionprice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1);
                String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                String temp_additionalprice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1);
                String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                String temp_additionalprice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1);

                String temp_sPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1);
                String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);
                String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                String temp_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);

                String temp_isQuickSale = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1);

                String temp_itemIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1);

                String temp_kitchenprintedyn = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);

                String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);
                String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1),"2");
                String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1), "2");

                String togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1);

                String temp_categoryname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1);

                String temp_stax = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,49), 1), "2");

                String temp_labelprintedYN = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,50), 1), "2");

                // 04192023
                String pastholdcode = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,51), 1), "2");

                if (GlobalMemberValues.isStrEmpty(togodelitype)) {
                    togodelitype = "H";
                }

                if (GlobalMemberValues.isStrEmpty(temp_memoToKitchen)) {
                    temp_memoToKitchen = "nokitchenmemo";
                }

                if (GlobalMemberValues.isStrEmpty(temp_kitchenprintedyn)) {
                    temp_kitchenprintedyn = "N";
                }

                // 주방프린팅 여부와 주방프린터 번호 구하기
                String tempKitchenPrinterNumber = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                String temp_kitchenPrintYN = "N";
                if (item_saveType == "0" || item_saveType.equals("0")) {
                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
                        temp_kitchenPrintYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
                            temp_kitchenPrintYN = "N";
                        }
                    }
//                    if (!GlobalMemberValues.isStrEmpty(temp_itemIdx)) {
//                        temp_kitchenPrintYN = MssqlDatabase.getResultSetValueToString(
//                                "select kitchenprintyn from salon_storeservice_sub where idx = '" + temp_itemIdx + "' ");
//                        if (GlobalMemberValues.isStrEmpty(temp_kitchenPrintYN)) {
//                            temp_kitchenPrintYN = "N";
//                        }
//                    }
                } else {
                    // 푸드가 아닐 경우 주방프린팅 실행여부는 Y 로 하더라도
                    // 키친프린팅 번호를 99 로 해서 키친프린팅이 되지 않도록 한다.
                    temp_kitchenPrintYN = "Y";
                    tempKitchenPrinterNumber = "99";
                }

                // Common Gratuity 일 경우에는 temp_kitchenPrintYN 을 Y 로 처리한다
                if (item_itemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                    temp_kitchenPrintYN = "Y";
                }

                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPrice : " + temp_sPrice + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sPriceAmount : " + temp_sPriceAmount + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTaxAmount : " + temp_sTaxAmount + "\n");
                GlobalMemberValues.logWrite("checkandkitchenprintlog", "* temp_sTotalAmount : " + temp_sTotalAmount + "\n");

                boolean isPrintItem = true;
                String str_additem = "N";
                if (!temp_kitchenprintedyn.equals("N")) {
                    isPrintItem = true;
                } else {
                    isPrintItem = false;
                    str_additem = "Y";
                }

//            item_itemName = str_test_add + item_itemName;
                if (tempKitchenPrintedItemCnt == 0) {
                    str_additem = "N";
                }


                // 08102023
                // alt name 구하기
                String temp_itemName_alt = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicenamealt from salon_storeservice_sub where idx = '" + temp_itemIdx + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_itemName_alt)) {
                    temp_itemName_alt = "";
                }

                String item_itemname_optionadd = item_itemName +
                        "-ANNIETTASU-" + temp_optionTxt +
                        "-ANNIETTASU-" + temp_additionalTxt1 +
                        "-ANNIETTASU-" + temp_additionalTxt2 +
                        "-ANNIETTASU-" + temp_itemIdx +
                        "-ANNIETTASU-" + tempKitchenPrinterNumber +
                        "-ANNIETTASU-" + temp_memoToKitchen +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_optionprice, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice1, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(temp_additionalprice2, "2") +
                        "-ANNIETTASU-" + selectedDcExtraAllEach +
                        "-ANNIETTASU-" + selectedDcExtraType +
                        "-ANNIETTASU-" + dcextratype +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(dcextravalue, "2") +
                        "-ANNIETTASU-" + GlobalMemberValues.getStringFormatNumber(selectedDcExtraPrice, "2") +
                        "-ANNIETTASU-" + togodelitype +
                        "-ANNIETTASU-" + temp_categoryname +
                        "-ANNIETTASU-" + str_additem +
                        "-ANNIETTASU-" + temp_labelprintedYN +

                        // 08102023
                        "-ANNIETTASU-" + temp_itemName_alt;

                GlobalMemberValues.logWrite("phoneorddermodifierlog", "item_itemname_optionadd : " + item_itemname_optionadd + "\n");

                GlobalMemberValues.logWrite("jjjwanhayelog", "mKitchenPrintOnRecall : " + Recall.mKitchenPrintOnRecall + "\n");
                GlobalMemberValues.logWrite("jjjwanhayelog", "temp_kitchenPrintYN : " + temp_kitchenPrintYN + "\n");

                GlobalMemberValues.logWrite("jjjtogodelitypelog", "* togodelitype : " + togodelitype + "\n");


                String temp_kitchenprintnum = "0";
                temp_kitchenprintnum = GlobalMemberValues.getKitchenPrinterNumber(temp_itemIdx);

                // 푸드(서비스) 일때에만 키친프린트할 항목을 저장한다..
                if (temp_kitchenPrintYN == "Y" || temp_kitchenPrintYN.equals("Y") || temp_isQuickSale == "Y" || temp_isQuickSale.equals("Y")) {
                    if (itemDetailCount == 0) {
//                            itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
//                                    "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_sTaxAmount + "-JJJ-" + temp_sTotalAmount;
                        itemDetailText = item_itemname_optionadd + "-JJJ-" + item_qty +
                                "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                        // 04192023
                        pastHoldCodeStr = pastholdcode;
                    } else {
                        itemDetailText += "-WANHAYE-" + item_itemname_optionadd + "-JJJ-" + item_qty +
                                "-JJJ-" + temp_kitchenprintnum + "-JJJ-" + temp_categoryname + "-JJJ-" + "" +
                                "-JJJ-" + temp_sPrice + "-JJJ-" + temp_sPriceAmount + "-JJJ-" + temp_stax + "-JJJ-" + temp_sTaxAmount +
                                "-JJJ-" + temp_optionprice + "-JJJ-" + temp_additionalprice1 + "-JJJ-" + temp_additionalprice2 + "-JJJ-" + togodelitype;

                        // 04192023
                        pastHoldCodeStr += pastholdcode;
                    }
                    itemDetailCount++;
                }
            }
            tempSaleCartCursor.close();
        } catch (Exception e){

        }

        GlobalMemberValues.logWrite("jjjwanhayelog", "itemDetailText : " + itemDetailText + "\n");

        jsonroot_kitchen.put("saleitemlist", itemDetailText);

        // 04192023
        jsonroot_kitchen.put("pastholdcode", pastHoldCodeStr);


        // temp_salecart_deliveryinfo 테이블에서 배송관련정보 가져온다.
        String temp_customerId = "";
        String temp_customerName = "";
        String temp_customerPhone = "";
        String temp_customerEmail = "";
        String temp_customerAddr1 = "";
        String temp_customerAddr2 = "";
        String temp_customerCity = "";
        String temp_customerState = "";
        String temp_customerZip = "";
        String temp_deliveryday = "";
        String temp_deliverytime = "";
        String temp_deliverydate = "";
        String temp_deliverytakeaway = "";
        String temp_customermemo = "";
        String temp_customerAddrAll = "";

        String strQuery = "select customerId, customerName, customerPhone, customerEmail, " +
                " customerAddr1, customerAddr2, customerCity, customerState, customerZip, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, " +
                " customermemo " +
                " from temp_salecart_deliveryinfo " +
                " where holdcode = '" + paramSalesCode + "' ";
        GlobalMemberValues.logWrite("paymentlog", "strQuery2 : " + strQuery + "\n");
        ResultSet deliverytakeawayInfoCursor = MssqlDatabase.getResultSetValue(strQuery);
        try {
            while (deliverytakeawayInfoCursor.next()){
                temp_customerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,0), 1);
                temp_customerName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,1), 1);
                temp_customerPhone = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,2), 1);
                temp_customerEmail = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,3), 1);

                temp_customerAddr1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,4), 1);
                temp_customerAddr2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,5), 1);
                temp_customerCity = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,6), 1);
                temp_customerState = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,7), 1);
                temp_customerZip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,8), 1);

                temp_deliveryday = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,9), 1);
                temp_deliverytime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,10), 1);
                temp_deliverydate = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,11), 1);
                temp_deliverytakeaway = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,12), 1);
                if (!GlobalMemberValues.isToGoSale()) {
                    temp_deliverytakeaway = "H";
                }


                temp_customermemo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(deliverytakeawayInfoCursor,13), 1);

                if (!GlobalMemberValues.isStrEmpty(temp_customerAddr1)
                        || !GlobalMemberValues.isStrEmpty(temp_customerAddr2)
                        || !GlobalMemberValues.isStrEmpty(temp_customerCity)
                        || !GlobalMemberValues.isStrEmpty(temp_customerState)
                        || !GlobalMemberValues.isStrEmpty(temp_customerZip)) {

                    temp_customerAddrAll = temp_customerAddr1;

                    if (!GlobalMemberValues.isStrEmpty(temp_customerAddr2)) {
                        temp_customerAddrAll += " " + temp_customerAddr2;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerCity)) {
                        temp_customerAddrAll += "\n" + temp_customerCity;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerState)) {
                        temp_customerAddrAll += ", " + temp_customerState;
                    }
                    if (!GlobalMemberValues.isStrEmpty(temp_customerZip)) {
                        temp_customerAddrAll += " " + temp_customerZip;
                    }
                }
            }
            deliverytakeawayInfoCursor.close();
//            if (deliverytakeawayInfoCursor.getCount() > 0 && deliverytakeawayInfoCursor.moveToFirst()) {
//
//            }
        }catch (Exception e){

        }

        GlobalMemberValues.logWrite("tempcustmemojjjinfo", "GlobalMemberValues.mTempCustomerInfo...2 : " + GlobalMemberValues.mTempCustomerInfo + "\n");
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mTempCustomerInfo)) {
            String tempCustValue[] = GlobalMemberValues.mTempCustomerInfo.split("-JJJ-");

            temp_customerPhone = tempCustValue[0];
            if (tempCustValue.length > 1) {
                temp_customerName = tempCustValue[1];
            }
            if (tempCustValue.length > 2) {
                temp_customermemo = tempCustValue[2];
            }

            GlobalMemberValues.mTempCustomerInfo = "";
        }

        GlobalMemberValues.logWrite("customerinfojjjlog", "temp_customerPhone : " + temp_customerPhone + "\n");

        jsonroot_kitchen.put("customername", temp_customerName);

        jsonroot_kitchen.put("deliverytakeaway", temp_deliverytakeaway);
        jsonroot_kitchen.put("deliverydate", temp_deliverydate);
        jsonroot_kitchen.put("ordertype", "POS");

        jsonroot_kitchen.put("customermemo", temp_customermemo);

        jsonroot_kitchen.put("phoneorder", "Y");

        jsonroot_kitchen.put("phoneordernumber", GlobalMemberValues.getPhoneOrderNewNumber(paramSalesCode));

        /** Store 정보 추출 ************************************************/
        String storeNameForReceipt = "";
        String storeAddressForReceipt1 = "";
        String storeAddressForReceipt2 = "";
        String storeCityForReceipt = "";
        String storeStateForReceipt = "";
        String storeZipForReceipt = "";
        String storePhoneForReceipt = "";

        String storeNameForReceipt2 = "";

        strQuery = "select " +
                " name, addr1, addr2, city, state, zip, phone, name2 " +
                " from salon_storeinfo ";
        Cursor paymentStoreInfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (paymentStoreInfoCursor.getCount() > 0 && paymentStoreInfoCursor.moveToFirst()) {
            storeNameForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(0), 1);
            storeAddressForReceipt1 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(1), 1);
            storeAddressForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(2), 1);
            storeCityForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(3), 1);
            storeStateForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(4), 1);
            storeZipForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(5), 1);
            storePhoneForReceipt = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(6), 1);

            storeNameForReceipt2 = GlobalMemberValues.getDBTextAfterChecked(paymentStoreInfoCursor.getString(7), 1);

            if (!GlobalMemberValues.isStrEmpty(storeNameForReceipt2)) {
                storeNameForReceipt = storeNameForReceipt2;
            }
        }

        jsonroot_kitchen.put("storename", storeNameForReceipt);  // JSON
        jsonroot_kitchen.put("storeaddress1", storeAddressForReceipt1);  // JSON
        jsonroot_kitchen.put("storeaddress2", storeAddressForReceipt2);  // JSON
        jsonroot_kitchen.put("storecity", storeCityForReceipt);  // JSON
        jsonroot_kitchen.put("storestate", storeStateForReceipt);  // JSON
        jsonroot_kitchen.put("storezip", storeZipForReceipt);  // JSON
        jsonroot_kitchen.put("storephone", storePhoneForReceipt);  // JSON

        // JSON (고객 전화번호, 고객 배송지 주소) ---------------------------------------
        jsonroot_kitchen.put("customerphonenum", temp_customerPhone);
        jsonroot_kitchen.put("customeraddress", temp_customerAddrAll);
        // ---------------------------------------------------------------------------

        /******************************************************************/


        if (jsonroot_kitchen != null && jsonroot_kitchen.length() > 0) {
            jsonroot_kitchen.put("canceldeleteyn", "Y");
//            if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
//                jsonroot_kitchen.put("canceldeleteyn", "Y");
//            } else {
//                jsonroot_kitchen.put("canceldeleteyn", "N");
//            }

            String tempReprint = "N";
            if (Recall.mKitchenReprint != null && Recall.mKitchenReprint.equals("Y")) {
                tempReprint = "Y";
            }
            GlobalMemberValues.logWrite("reprintlogjjjwhy", "tempReprint : " + tempReprint + "\n");
            jsonroot_kitchen.put("reprintyn", tempReprint);
        }


        GlobalMemberValues.PHONEORDER_HOLDCODE = paramSalesCode;

        GlobalMemberValues.logWrite("jsonjjjptlog", "json 값111 : " + jsonroot_kitchen.toString() + "\n");

        return jsonroot_kitchen;
    }

    public static void phoneorderPrinting(String paramSalesCode, String paramPrintingType, String paramTableInfos) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject = GlobalMemberValues.phoneorderPrintingJson(paramSalesCode, paramPrintingType, paramTableInfos);

        String item_list = "";


        if (jsonObject != null && jsonObject.length() > 0) {
            if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
                jsonObject.put("canceldeleteyn", "Y");
            } else {
                jsonObject.put("canceldeleteyn", "N");
            }
            item_list = jsonObject.optString("saleitemlist","");

            String tempReprint = "N";
            if (Recall.mKitchenReprint != null && Recall.mKitchenReprint.equals("Y")) {
                tempReprint = "Y";
            }
            GlobalMemberValues.logWrite("reprintlogjjjwhy", "tempReprint : " + tempReprint + "\n");
            jsonObject.put("reprintyn", tempReprint);
        }

        Recall.mKitchenReprint = "N";

        // 윈도우 프린팅을 위한 mssql 저장을 실행한다. -------------------------------------------------------
        // 03112022
        // salon_sales_kitchenprintingdata_json 테이블에 동일한 salescode 로 된 printedyn 의 값이 N 인 데이터가 있는지 체크하고,
        // 있으면 기존것은 지우고, 아래 쿼리 실행한다.
        String strQuery = "";
        Vector<String> strInsertQueryVec = new Vector<String>();
        String tempIdxOfKitchenPrintingData = GlobalMemberValues.getIdxOfSameDataOnKitchenPrintingData(paramSalesCode);
        if (!GlobalMemberValues.isStrEmpty(tempIdxOfKitchenPrintingData)) {
            strQuery = " delete from salon_sales_kitchenprintingdata_json where idx = '" + tempIdxOfKitchenPrintingData + "' ";
            strInsertQueryVec.addElement(strQuery);
        }

        // 01132023
        jsonObject.put("holdcode", paramSalesCode);

        GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기1 : " + jsonObject.toString() + "\n");


        // 11102023
        if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonObject.toString())) {
            strQuery = " insert into salon_sales_kitchenprintingdata_json " +
                    " (salesCode, scode, sidx, stcode, jsonstr, reprintyn) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(paramSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonObject.toString(), 0) + "', " +
                    " '" + GlobalMemberValues.isKitchenReprinting + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strQuery);
        }

        // 초기화
        GlobalMemberValues.isKitchenReprinting = "N";

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("salon_sales_kitchenprintingdata_json_log", "query : " + tempQuery + "\n");
        }

        if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
            if (item_list != null && !GlobalMemberValues.isStrEmpty(item_list)) {
//        if (jsonObject != null && !GlobalMemberValues.isStrEmpty(jsonObject.toString())) {
                //jsonObject.put("QSRTKIND", "R");
//                MssqlDatabase.executeTransactionByQuery(strQuery);
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            }
        } else {
            if (GlobalMemberValues.mCancelBtnClickYN == "Y" || GlobalMemberValues.mCancelBtnClickYN.equals("Y")) {
//                MssqlDatabase.executeTransactionByQuery(strQuery);
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            }
        }
        // --------------------------------------------------------------------------------------------


        GlobalMemberValues.PHONEORDERYN = "Y";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorderlog : " + jsonObject.toString() + "\n");

        // Label print
        if (isUseLabelPrinter()){
            // json 쪼개기..
            JSONObject label_jsonobject = new JSONObject();
            for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
                String      key     = iterator.next();
                JSONObject  value   = jsonObject.optJSONObject(key);

                try {
                    label_jsonobject.put(key, value);
                } catch ( JSONException e ) {
                    //TODO process exception
                }
            }
            String str_saleitemlist = null;
            try {
                str_saleitemlist = jsonObject.getString("saleitemlist");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                JSONArray array_orderItemList = new JSONArray();
                for (int i = 0; i < strOrderItemsList.length; i++) {
                    String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);
                    JSONObject jsonObject_item = new JSONObject();

                    // 상품명, 수량 정보 --------------------------------------------------------------------
                    String tempItemNameOptionAdd = strOrderItems[0];
                    String tempItemQty = strOrderItems[1];
                    String tempPrice = strOrderItems[2];
                    String tempPriceAmount = strOrderItems[3];
                    String tempTaxAmount = strOrderItems[4];
                    String tempTotalAmount = strOrderItems[5];

                    GlobalMemberValues.logWrite("saleitemstrarray", "tempItemNameOptionAdd : " + tempItemNameOptionAdd + "\n");

                    String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
                    String tempItemName = strItemNAmeOptionAdd[0];
                    String tempOptionTxt = "";
                    String tempAdditionalTxt1 = "";
                    String tempAdditionalTxt2 = "";
                    String tempItemIdx = "";
                    String tempKitchenMemo = "";
                    String tempOptionPrice = "";
                    String temptemp_additionalprice1 = "";
                    String temptemp_additionalprice2 = "";
                    String selectedDcExtraAllEach = "";
                    String selectedDcExtraType = "";
                    String dcextratype = "";
                    String dcextravalue = "";
                    String selectedDcExtraPrice = "";
                    String printedLabel = "";
                    String additem = "";
                    try {
                        jsonObject_item.put("itemname", tempItemName);
                        if (strItemNAmeOptionAdd.length > 1) {
                            tempOptionTxt = strItemNAmeOptionAdd[1];
                            jsonObject_item.put("optiontxt", tempOptionTxt);
                            if (strItemNAmeOptionAdd.length > 2) {
                                tempAdditionalTxt1 = strItemNAmeOptionAdd[2];
                                jsonObject_item.put("additionaltxt1", tempAdditionalTxt1);
                            }
                            if (strItemNAmeOptionAdd.length > 3) {
                                tempAdditionalTxt2 = strItemNAmeOptionAdd[3];
                                jsonObject_item.put("additionaltxt2", tempAdditionalTxt2);
                            }
                            if (strItemNAmeOptionAdd.length > 4) {
                                tempItemIdx = strItemNAmeOptionAdd[4];
                                jsonObject_item.put("itemsvcidx", tempItemIdx);
                            }
                            if (strItemNAmeOptionAdd.length > 5) {
                                jsonObject_item.put("itemqty", tempItemQty);
                            }
                            if (strItemNAmeOptionAdd.length > 6) {
                                tempKitchenMemo = strItemNAmeOptionAdd[6];
                                jsonObject_item.put("kitchenmemo", tempKitchenMemo);
                            }
                            if (strItemNAmeOptionAdd.length > 7) {
                                tempOptionPrice = strItemNAmeOptionAdd[7];
                                jsonObject_item.put("optionprice", tempOptionPrice);
                            }
                            if (strItemNAmeOptionAdd.length > 8) {
                                temptemp_additionalprice1 = strItemNAmeOptionAdd[8];
                                jsonObject_item.put("additionalprice1", temptemp_additionalprice1);
                            }
                            if (strItemNAmeOptionAdd.length > 9) {
                                temptemp_additionalprice2 = strItemNAmeOptionAdd[9];
                                jsonObject_item.put("additionalprice2", temptemp_additionalprice2);
                            }
                            // discount / extra
                            if (strItemNAmeOptionAdd.length > 10) {
                                selectedDcExtraAllEach = strItemNAmeOptionAdd[10];
                                jsonObject_item.put("", selectedDcExtraAllEach);
                            }
                            if (strItemNAmeOptionAdd.length > 11) {
                                selectedDcExtraType = strItemNAmeOptionAdd[11];
                                jsonObject_item.put("", selectedDcExtraType);
                            }
                            if (strItemNAmeOptionAdd.length > 12) {
                                dcextratype = strItemNAmeOptionAdd[12];
                                jsonObject_item.put("", dcextratype);
                            }
                            if (strItemNAmeOptionAdd.length > 13) {
                                dcextravalue = strItemNAmeOptionAdd[13];
                                jsonObject_item.put("", dcextravalue);
                            }
                            if (strItemNAmeOptionAdd.length > 14) {
                                selectedDcExtraPrice = strItemNAmeOptionAdd[14];
                                jsonObject_item.put("", selectedDcExtraPrice);
                            }
                            if (strItemNAmeOptionAdd.length > 17) {
                                additem = strItemNAmeOptionAdd[17];
                                jsonObject_item.put("additem", additem);
                            }
                            if (strItemNAmeOptionAdd.length > 18) {
                                printedLabel = strItemNAmeOptionAdd[18];
                            }
                            jsonObject_item.put("receiptno", jsonObject.get("receiptno"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (printedLabel.equals("N")){
                        array_orderItemList.put(jsonObject_item);
                    }
                }
                try {
                    label_jsonobject.put("saleitemlist",array_orderItemList);
                    label_jsonobject.put("phoneordernumber",jsonObject.get("phoneordernumber"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONArray temp_array = new JSONArray();
            temp_array = GlobalMemberValues.labelPrint_menuSplit(label_jsonobject);
            if (temp_array != null && temp_array.length() != 0){
//                            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//                            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");
                GlobalMemberValues.printLabel1to5(temp_array);
            }
        }
        // Label print

        if (paramPrintingType.equals("A")) {
            GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "phoneordercheckprint");
            GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
        } else {
            if (paramPrintingType.equals("R")) {
                GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "phoneordercheckprint");
            }
            if (paramPrintingType.equals("K")) {
                GlobalMemberValues.logWrite("phoneordercheckprintlog", "여기왔음..." + "\n");
                GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
            }
        }
    }

    public static void TableBillPrinting(String paramSalesCode, String paramPrintingType, String paramTableInfos, JSONObject temp_json) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject = temp_json;

        String item_list = "";
        String strQuery = "";

        if (jsonObject != null && jsonObject.length() > 0) {
            if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
                jsonObject.put("canceldeleteyn", "Y");
            } else {
                jsonObject.put("canceldeleteyn", "N");
            }

            item_list = jsonObject.optString("saleitemlist","");


            // 05172023
            jsonObject.put("isbillprintingjjj", "Y");
        }

        // 윈도우 프린팅을 위한 mssql 저장을 실행한다. -------------------------------------------------------
        // 03112022
        // salon_sales_kitchenprintingdata_json 테이블에 동일한 salescode 로 된 printedyn 의 값이 N 인 데이터가 있는지 체크하고,
        // 있으면 기존것은 지우고, 아래 쿼리 실행한다.
        Vector<String> strInsertQueryVec = new Vector<String>();
        String tempIdxOfKitchenPrintingData = GlobalMemberValues.getIdxOfSameDataOnKitchenPrintingData(paramSalesCode);
        if (!GlobalMemberValues.isStrEmpty(tempIdxOfKitchenPrintingData)) {
            strQuery = " delete from salon_sales_kitchenprintingdata_json where idx = '" + tempIdxOfKitchenPrintingData + "' ";
            strInsertQueryVec.addElement(strQuery);
        }

        // 01132023
        jsonObject.put("holdcode", paramSalesCode);

        GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기2 : " + jsonObject.toString() + "\n");


        // 10092023 --------------------------------------------------------------------------
        int qtycount = 0;
        // 메뉴 전체 수량 구하기
        qtycount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select sum(sQty) from temp_salecart where holdcode = '" + paramSalesCode + "' "
        ));
        strQuery = " insert into salon_billprinted_itemqty " +
                " (stcode, holdcode, qtycount) values ( " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                " '" + GlobalMemberValues.getDBTextAfterChecked(paramSalesCode, 0) + "', " +
                " '" + qtycount + "' " +
                " ) ";
        strInsertQueryVec.addElement(strQuery);
        // 10092023 --------------------------------------------------------------------------


        // 03182024
        // 아래 부분을 삭제할 것 (멀티, handheld, vx golf)
//        // 11102023
//        if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonObject.toString())) {
//            strQuery = " insert into salon_sales_kitchenprintingdata_json " +
//                    " (salesCode, scode, sidx, stcode, jsonstr, reprintyn) values ( " +
//                    " '" + GlobalMemberValues.getDBTextAfterChecked(paramSalesCode,0) + "', " +
//                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
//                    "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
//                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
//                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonObject.toString(), 0) + "', " +
//                    " '" + GlobalMemberValues.isKitchenReprinting + "' " +
//                    " ) ";
//            strInsertQueryVec.addElement(strQuery);
//        }

        // 초기화
        GlobalMemberValues.isKitchenReprinting = "N";

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("salon_sales_kitchenprintingdata_json_log", "query : " + tempQuery + "\n");
        }

        if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
            if (item_list != null && !GlobalMemberValues.isStrEmpty(item_list)) {
//        if (jsonObject != null && !GlobalMemberValues.isStrEmpty(jsonObject.toString())) {
                //jsonObject.put("QSRTKIND", "R");
//                MssqlDatabase.executeTransactionByQuery(insQuery);
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            }
        } else {
            if (GlobalMemberValues.mCancelBtnClickYN == "Y" || GlobalMemberValues.mCancelBtnClickYN.equals("Y")) {
//                MssqlDatabase.executeTransactionByQuery(insQuery);
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            }
        }
        // --------------------------------------------------------------------------------------------


        GlobalMemberValues.PHONEORDERYN = "Y";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorderlog : " + jsonObject.toString() + "\n");

        if (paramPrintingType.equals("A")) {
            GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "phoneordercheckprint");
            GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
        } else {
            if (paramPrintingType.equals("R")) {
                GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "phoneordercheckprint");
            }
            if (paramPrintingType.equals("K")) {
                GlobalMemberValues.logWrite("phoneordercheckprintlog", "여기왔음..." + "\n");
                GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
            }
        }
    }

    public static void tableMainPrinting(String paramSalesCode, String paramPrintingType, String paramTableInfos,  String param_splitmerge_total, String[] paramSplitMergeInfo, String str_Billsplit_Type, JSONObject jsonObject) throws JSONException {



        GlobalMemberValues.PHONEORDERYN = "Y";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorderlog : " + jsonObject.toString() + "\n");

        if (paramPrintingType.equals("A")) {
            GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "tablemain_checkprint");
            GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
        } else {
            if (paramPrintingType.equals("R")) {
                GlobalMemberValues.printReceiptByJHP(jsonObject, MainActivity.mContext, "tablemain_checkprint");
            }
            if (paramPrintingType.equals("K")) {
                GlobalMemberValues.logWrite("phoneordercheckprintlog", "여기왔음..." + "\n");
                GlobalMemberValues.printGateByKitchen(jsonObject, MainActivity.mContext, "kitchen1");
            }
        }
    }

    public static void phoneorderPrinting_label(String paramSalesCode, String paramPrintingType, String paramTableInfos) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject = GlobalMemberValues.phoneorderPrintingJson(paramSalesCode, paramPrintingType, paramTableInfos);

        String item_list = "";

        if (jsonObject != null && jsonObject.length() > 0) {
            if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
                jsonObject.put("canceldeleteyn", "Y");
            } else {
                jsonObject.put("canceldeleteyn", "N");
            }

            item_list = jsonObject.optString("saleitemlist","");

            String tempReprint = "N";
//            if (Recall.mKitchenReprint != null && Recall.mKitchenReprint.equals("Y")) {
//                tempReprint = "Y";
//            }
            GlobalMemberValues.logWrite("reprintlogjjjwhy", "tempReprint : " + tempReprint + "\n");
            jsonObject.put("reprintyn", tempReprint);
        }

        // 01132023
        jsonObject.put("holdcode", paramSalesCode);

        GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기3 : " + jsonObject.toString() + "\n");

        // 윈도우 프린팅을 위한 mssql 저장을 실행한다. -------------------------------------------------------
        // 11102023
        String insQuery = "";
        if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonObject.toString())) {
            insQuery = " insert into salon_sales_kitchenprintingdata_json " +
                    " (salesCode, scode, sidx, stcode, jsonstr) values ( " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(paramSalesCode,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                    "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                    " '" + GlobalMemberValues.getDBTextAfterChecked(jsonObject.toString(), 0) + "' " +
                    " ) ";
        }

        if (GlobalMemberValues.mCancelKitchenPrinting == "N" || GlobalMemberValues.mCancelKitchenPrinting.equals("N")) {
            if (item_list != null && !GlobalMemberValues.isStrEmpty(item_list)) {
//        if (jsonObject != null && !GlobalMemberValues.isStrEmpty(jsonObject.toString())) {
                //jsonObject.put("QSRTKIND", "R");
                MainActivity.mDbInit.dbExecuteWriteReturnValue(insQuery);
            }
        } else {
//            if (GlobalMemberValues.mCancelBtnClickYN == "Y" || GlobalMemberValues.mCancelBtnClickYN.equals("Y")) {
//                MainActivity.mDbInit.dbExecuteWriteReturnValue(insQuery);
//            }
        }

        GlobalMemberValues.PHONEORDERYN = "Y";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "Y";

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorder2 : " + GlobalMemberValues.PHONEORDERYN + "\n");

        GlobalMemberValues.logWrite("phoneordercheckprintlog", "phoneorderlog : " + jsonObject.toString() + "\n");

        // Label print
        if (isUseLabelPrinter()){
            // json 쪼개기..
            JSONObject label_jsonobject = new JSONObject();
            for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
                String      key     = iterator.next();
                JSONObject  value   = jsonObject.optJSONObject(key);

                try {
                    label_jsonobject.put(key, value);
                } catch ( JSONException e ) {
                    //TODO process exception
                }
            }
            String str_saleitemlist = null;
            try {
                str_saleitemlist = jsonObject.getString("saleitemlist");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                JSONArray array_orderItemList = new JSONArray();
                for (int i = 0; i < strOrderItemsList.length; i++) {
                    String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);
                    JSONObject jsonObject_item = new JSONObject();

                    // 상품명, 수량 정보 --------------------------------------------------------------------
                    String tempItemNameOptionAdd = strOrderItems[0];
                    String tempItemQty = strOrderItems[1];
                    String tempPrice = strOrderItems[2];
                    String tempPriceAmount = strOrderItems[3];
                    String tempTaxAmount = strOrderItems[4];
                    String tempTotalAmount = strOrderItems[5];

                    GlobalMemberValues.logWrite("saleitemstrarray", "tempItemNameOptionAdd : " + tempItemNameOptionAdd + "\n");

                    String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
                    String tempItemName = strItemNAmeOptionAdd[0];
                    String tempOptionTxt = "";
                    String tempAdditionalTxt1 = "";
                    String tempAdditionalTxt2 = "";
                    String tempItemIdx = "";
                    String tempKitchenMemo = "";
                    String tempOptionPrice = "";
                    String temptemp_additionalprice1 = "";
                    String temptemp_additionalprice2 = "";
                    String selectedDcExtraAllEach = "";
                    String selectedDcExtraType = "";
                    String dcextratype = "";
                    String dcextravalue = "";
                    String selectedDcExtraPrice = "";
                    try {
                        jsonObject_item.put("itemname", tempItemName);
                        if (strItemNAmeOptionAdd.length > 1) {
                            tempOptionTxt = strItemNAmeOptionAdd[1];
                            jsonObject_item.put("optiontxt", tempOptionTxt);
                            if (strItemNAmeOptionAdd.length > 2) {
                                tempAdditionalTxt1 = strItemNAmeOptionAdd[2];
                                jsonObject_item.put("additionaltxt1", tempAdditionalTxt1);
                            }
                            if (strItemNAmeOptionAdd.length > 3) {
                                tempAdditionalTxt2 = strItemNAmeOptionAdd[3];
                                jsonObject_item.put("additionaltxt2", tempAdditionalTxt2);
                            }
                            if (strItemNAmeOptionAdd.length > 4) {
                                tempItemIdx = strItemNAmeOptionAdd[4];
                                jsonObject_item.put("itemsvcidx", tempItemIdx);
                            }
                            if (strItemNAmeOptionAdd.length > 5) {
                                jsonObject_item.put("itemqty", tempItemQty);
                            }
                            if (strItemNAmeOptionAdd.length > 6) {
                                tempKitchenMemo = strItemNAmeOptionAdd[6];
                                jsonObject_item.put("kitchenmemo", tempKitchenMemo);
                            }
                            if (strItemNAmeOptionAdd.length > 7) {
                                tempOptionPrice = strItemNAmeOptionAdd[7];
                                jsonObject_item.put("optionprice", tempOptionPrice);
                            }
                            if (strItemNAmeOptionAdd.length > 8) {
                                temptemp_additionalprice1 = strItemNAmeOptionAdd[8];
                                jsonObject_item.put("additionalprice1", temptemp_additionalprice1);
                            }
                            if (strItemNAmeOptionAdd.length > 9) {
                                temptemp_additionalprice2 = strItemNAmeOptionAdd[9];
                                jsonObject_item.put("additionalprice2", temptemp_additionalprice2);
                            }
                            // discount / extra
                            if (strItemNAmeOptionAdd.length > 10) {
                                selectedDcExtraAllEach = strItemNAmeOptionAdd[10];
                                jsonObject_item.put("", selectedDcExtraAllEach);
                            }
                            if (strItemNAmeOptionAdd.length > 11) {
                                selectedDcExtraType = strItemNAmeOptionAdd[11];
                                jsonObject_item.put("", selectedDcExtraType);
                            }
                            if (strItemNAmeOptionAdd.length > 12) {
                                dcextratype = strItemNAmeOptionAdd[12];
                                jsonObject_item.put("", dcextratype);
                            }
                            if (strItemNAmeOptionAdd.length > 13) {
                                dcextravalue = strItemNAmeOptionAdd[13];
                                jsonObject_item.put("", dcextravalue);
                            }
                            if (strItemNAmeOptionAdd.length > 14) {
                                selectedDcExtraPrice = strItemNAmeOptionAdd[14];
                                jsonObject_item.put("", selectedDcExtraPrice);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    array_orderItemList.put(jsonObject_item);
                }
                try {
                    label_jsonobject.put("saleitemlist",array_orderItemList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONArray temp_array = new JSONArray();
            temp_array = GlobalMemberValues.labelPrint_menuSplit(label_jsonobject);
            if (temp_array != null && temp_array.length() != 0){
//            EpsonLabelPrinter epsonLabelPrinter = new EpsonLabelPrinter(MainActivity.mContext);
//            epsonLabelPrinter.runPrintReceiptSequence_array(temp_array, "USB:");

                printLabel1to5(temp_array);
            }
        }
        // Label print
    }

    public static void setPhoneorderLabelPrinted(String paramSalesCode) {
        String strQuery =  "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String returnResult = "";
        Vector<String> strInsertQueryVec = new Vector<String>();

        strQuery = " update temp_salecart set labelprintedyn = 'Y' where holdcode = '" + paramSalesCode + "' ";
        strInsertQueryVec.addElement(strQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("setPhoneorderLabelPrintedlog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
        }
    }

    public static void setPhoneorderKitchenPrinted(String paramSalesCode) {
        String strQuery =  "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String returnResult = "";
        Vector<String> strInsertQueryVec = new Vector<String>();

        strQuery = " update temp_salecart_deliveryinfo set kitchenprintedyn = 'Y' where holdcode = '" + paramSalesCode + "' ";
        strInsertQueryVec.addElement(strQuery);

        strQuery = " update temp_salecart set kitchenprintedyn = 'Y' where holdcode = '" + paramSalesCode + "' ";
        strInsertQueryVec.addElement(strQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("setPhoneorderKitchenPrintedlog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
        }
    }

    public static String getPhoneorderKitchenPrintedYN(String paramSalesCode) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempPhoneOrderKitchenPrintedYN = MssqlDatabase.getResultSetValueToString(
                "select kitchenprintedyn from temp_salecart " +
                        " where holdcode = '" + paramSalesCode + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempPhoneOrderKitchenPrintedYN)) {
            returnData = tempPhoneOrderKitchenPrintedYN;
        } else {
            returnData = "N";
        }

        return returnData;
    }

    public static String getPhoneorderLabelPrintedYN(String paramSalesCode) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempPhoneOrderKitchenPrintedYN = dbInit.dbExecuteReadReturnString(
                "select labelprintedyn from temp_salecart " +
                        " where holdcode = '" + paramSalesCode + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempPhoneOrderKitchenPrintedYN)) {
            returnData = tempPhoneOrderKitchenPrintedYN;
        } else {
            returnData = "N";
        }

        return returnData;
    }

    public static String getSaleCartKitchenPrintedYN(String cart_idx) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempPhoneOrderKitchenPrintedYN = MssqlDatabase.getResultSetValueToString(
                "select kitchenprintedyn from temp_salecart " +
                        " where idx = '" + cart_idx + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempPhoneOrderKitchenPrintedYN)) {
            returnData = tempPhoneOrderKitchenPrintedYN;
        } else {
            returnData = "N";
        }

        return returnData;
    }

    public static String getPhoneOrderYN(String paramSalesCode) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempPhoneOrderYN = dbInit.dbExecuteReadReturnString(
                "select phoneorder from temp_salecart_deliveryinfo " +
                        " where holdcode = '" + paramSalesCode + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempPhoneOrderYN)) {
            returnData = tempPhoneOrderYN;
        } else {
            returnData = "N";
        }

        return returnData;
    }

    public static String getFoodModifierType(String paramIdx) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempModifierType = dbInit.dbExecuteReadReturnString(
                "select modifiertype from salon_storeservice_sub " +
                        " where idx = '" + paramIdx + "' "
        );

        if (GlobalMemberValues.isStrEmpty(tempModifierType)) {
            tempModifierType = "A";
        }
        returnData = tempModifierType;

        GlobalMemberValues.logWrite("modifiertypelog", "mod type : " + returnData + "\n");

        return returnData;
    }

    public static String getMasterPwdAfterCloudCheck(Context context, String paramSalonCode) {
        String returnValue = "";

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(context);
                returnValue = GlobalMemberValues.MASTER_PWD;
            } else {
                API_masterpwd apicheckInstance = new API_masterpwd();
                apicheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apicheckInstance.mFlag) {
                        returnValue = apicheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    returnValue = "";
                }

                GlobalMemberValues.logWrite("MemberCheckAPI", "returnValue : " + returnValue + "\n");
            }
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }

        return returnValue;
    }

    public static boolean isModifierPrintYN() {
        boolean returnData = false;

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempModifierPrintYN = dbInit.dbExecuteReadReturnString(
                " select modifierprintyn from salon_storestationsettings_deviceprinter "
        );

        if (!GlobalMemberValues.isStrEmpty(tempModifierPrintYN)) {
            if (tempModifierPrintYN.equals("Y")) {
                returnData = true;
            } else {
                returnData = false;
            }

        } else {
            returnData = false;
        }

        return returnData;
    }

    public static String getModifierTxt(String paramQty, String paramTxt) {
        String returnValue = "";

        returnValue = GlobalMemberValues.getModifierStringValue(paramQty, paramTxt, "R", 0);

        return returnValue;
    }

    public static String getModifierTxt2(String paramQty, String paramTxt, int paramAddSpaceSu) {
        String returnValue = "";

        returnValue = GlobalMemberValues.getModifierStringValue(paramQty, paramTxt, "R", paramAddSpaceSu);

        return returnValue;
    }

    public static String getModifierTxtForKitchen(String paramTxt) {
        String returnValue = "";

        returnValue = GlobalMemberValues.getModifierStringValue("", paramTxt, "K", 0);

        return returnValue;
    }

    public static String getModifierTxtForKitchen2(String paramTxt, int paramAddSpaceSu) {
        String returnValue = "";

        returnValue = GlobalMemberValues.getModifierStringValue("", paramTxt, "K", paramAddSpaceSu);

        return returnValue;
    }

    public static String getModifierStringValue(String paramQty, String paramTxt, String paramPrintType, int paramAddSpaceSu) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            if (GlobalMemberValues.isStrEmpty(paramQty)) {
                paramQty = "1";
            }
            int qtyInt = GlobalMemberValues.getIntAtString(paramQty);

            String[] paramTxtArr = paramTxt.split(", ");

            String printingTxt = "";

            for (int jjj = 0; jjj < paramTxtArr.length; jjj++) {
                String tempTxt = paramTxtArr[jjj];
                String splitArr[] = {"", ""};
                if (tempTxt.indexOf("(+") > -1 || tempTxt.indexOf("(-") > -1) {
                    GlobalMemberValues.logWrite("printingtestlogjjj", "tempTxt : " + tempTxt + "\n");

                    tempTxt = getReplaceText(tempTxt, "(+", "-JJJ-+");
                    tempTxt = getReplaceText(tempTxt, "(-", "-JJJ--");

                    String[] tempArr = tempTxt.split("-JJJ-");
                    splitArr[0] = tempArr[0];
                    if (tempArr.length > 1) {
                        splitArr[1] = tempArr[1];
                        splitArr[1] = getReplaceText(splitArr[1], ")", "");
                    } else {
                        splitArr[1] = "";
                    }

                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[0] : " + splitArr[0] + "\n");
                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[1] : " + splitArr[1] + "\n");
                } else {
                    splitArr[0] = tempTxt;
                    splitArr[1] = "";
                }

                String addPrice = "";
                if (!GlobalMemberValues.isStrEmpty(splitArr[1])) {
                    double addPriceDbl = GlobalMemberValues.getDoubleAtString(splitArr[1]) * qtyInt;
                    addPrice = GlobalMemberValues.getCommaStringForDouble(addPriceDbl + "") + "";
                }

                String tempStr = "";
                if (jjj < (paramTxtArr.length - 1)) {
                    tempStr = "\n";
                }

                if (paramPrintType == "R" || paramPrintType.equals("R")) {
                    if (!GlobalMemberValues.MODIFIER_PRICEVIEW) {
                        addPrice = "";
                    }
                    printingTxt += Payment_stringBackSpace_Exch(3 + paramAddSpaceSu, " ") +
                            Payment_stringBackSpace_Exch((36 - paramAddSpaceSu), splitArr[0]) +
                            Payment_stringFowardSpace_Exch(6, addPrice) + tempStr;
                } else {
                    printingTxt += Payment_stringBackSpace_Exch(3 + paramAddSpaceSu, " ") +
                            Payment_stringBackSpace_Exch((36 - paramAddSpaceSu), splitArr[0]) + tempStr;
                }
            }

            returnValue = printingTxt;
        }

        return returnValue;
    }

    public static String setStringCutLineInKitchenReceipt(String str_content){
        int str_max = 29;
        int i_tempOptionTxt_Length = str_content.length();
        int loopCnt = i_tempOptionTxt_Length / str_max + 1;
        String str_tempOptionTxt = "";
        for (int i = 0; i < loopCnt; i++) {
            int lastIndex = (i + 1) * str_max;
            //글자길이보다 긴 lastIndex를 설정하면 StringIndexOutOfBoundsException 오류가 발생하므로 if문으로 분기
            if(i_tempOptionTxt_Length > lastIndex){
                str_tempOptionTxt += GlobalMemberValues.getModifierTxtForKitchen2(str_content.substring(i * str_max, lastIndex) + "\n", 6);
            }else{
                str_tempOptionTxt += GlobalMemberValues.getModifierTxtForKitchen2(str_content.substring(i * str_max), 6);
            }
        }
        return str_tempOptionTxt;
    }

    public static String getModifierTxtForHTML(String paramTxt) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            returnValue = getReplaceText(paramTxt, ", ", "<br>");
        }

        return returnValue;
    }

    public static double getTax1InStoreGeneral() {
        double returnValue = 0.0;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tax1 = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tax1 from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(tax1)) {
            tax1 = "0";
        }
        returnValue = GlobalMemberValues.getDoubleAtString(tax1);
        return returnValue;
    }

    public static double getTax2InStoreGeneral() {
        double returnValue = 0.0;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tax2 = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tax2 from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(tax2)) {
            tax2 = "0";
        }
        returnValue = GlobalMemberValues.getDoubleAtString(tax2);
        return returnValue;
    }

    public static String getPrintTxtCenterAlignment(String paramTxt, int paramBase) {
        String returnString = paramTxt;

        paramTxt = GlobalMemberValues.getDecodeString(paramTxt);

        int strLength = GlobalMemberValues.getSizeToString(paramTxt);
        if (strLength < (paramBase + 1)) {
            int spaceCount = paramBase - strLength;
            if (spaceCount > 0) {
                int spaceCountHalf = spaceCount / 2;
                String leftTrimStr = "";
                String rightTrimStr = "";
                for (int i = 0; i < spaceCountHalf; i++) {
                    leftTrimStr += " ";
                    rightTrimStr += " ";
                }
                paramTxt = leftTrimStr + paramTxt + rightTrimStr;
                returnString = paramTxt;
            } else {
                returnString = paramTxt;
            }
        } else {
            paramTxt = paramTxt.substring((paramBase - 1));
            returnString = paramTxt;
        }

        return returnString;
    }

    public static String getPrintTxtCenterAlignment2(String paramTxt, int paramBase) {
        String returnString = paramTxt;

        paramTxt = GlobalMemberValues.getDecodeString(paramTxt);
        int strLength = GlobalMemberValues.getSizeToString(paramTxt);

        if (paramBase > strLength) {
            if (strLength < (paramBase + 1)) {
                int spaceCount = paramBase - strLength;
                if (spaceCount > 0) {
                    int spaceCountHalf = spaceCount / 2;
                    String leftTrimStr = "";
                    String rightTrimStr = "";
                    for (int i = 0; i < spaceCountHalf; i++) {
                        leftTrimStr += " ";
                        rightTrimStr += " ";
                    }
                    paramTxt = leftTrimStr + paramTxt + rightTrimStr;
                    returnString = paramTxt;
                } else {
                    returnString = paramTxt;
                }
            } else {
                paramTxt = paramTxt.substring((paramBase - 1));
                returnString = paramTxt;
            }
        } else {
            returnString = paramTxt;
        }

        return returnString;
    }

    public static boolean isKitchenPrinting(String paramKitchenPrinterNumber, String paramItemIdx) {
        boolean returnvalue = false;

        if (!GlobalMemberValues.isStrEmpty(paramItemIdx)) {
            String strQuery = "";
            strQuery = "select kitchenprintyn from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
            String tempkitchenprintyn = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempkitchenprintyn)) {
                tempkitchenprintyn = "Y";
            }
            if (tempkitchenprintyn == "Y" || tempkitchenprintyn.equals("Y")) {
                strQuery = "select kitchenprintnum from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
                String tempKitchenprintnum = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
                if (GlobalMemberValues.isStrEmpty(tempKitchenprintnum)) {
                    tempKitchenprintnum = "1";
                }
                // 키친프린팅이 메뉴별 한번만 가능할 때 사용하던 거...
                /**
                 if (paramKitchenPrinterNumber.equals(tempKitchenprintnum) || paramKitchenPrinterNumber == tempKitchenprintnum) {
                 returnvalue = true;
                 }
                 **/

                GlobalMemberValues.logWrite("kitchenprintnumberlog", "tempKitchenprintnum : " + tempKitchenprintnum + "\n");
                GlobalMemberValues.logWrite("kitchenprintnumberlog", "paramKitchenPrinterNumber : " + paramKitchenPrinterNumber + "\n");

                // 키친프린팅이 메뉴별 여러만 가능할 때 사용
                if (tempKitchenprintnum.indexOf(paramKitchenPrinterNumber) > -1) {
                    returnvalue = true;
                }
            } else {
                returnvalue = false;
            }
        }

        return returnvalue;
    }

    public static String getKitchenPrinterNumber(String paramItemIdx) {
        String returnvalue = "NOPRINT";

        if (!GlobalMemberValues.isStrEmpty(paramItemIdx)) {
            String strQuery = "";
            strQuery = "select kitchenprintyn from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
            String tempkitchenprintyn = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempkitchenprintyn)) {
                tempkitchenprintyn = "Y";
            }
            if (tempkitchenprintyn == "Y" || tempkitchenprintyn.equals("Y")) {
                strQuery = "select kitchenprintnum from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
                String tempKitchenprintnum = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
                if (GlobalMemberValues.isStrEmpty(tempKitchenprintnum)) {
                    returnvalue = "1";
                } else {
                    returnvalue = tempKitchenprintnum;
                }
            } else {
                returnvalue = "NOPRINT";
            }
        }

        return returnvalue;
    }

    public static String getKitchenPrinterNumberForQuickSale(String paramItemCategoryIdx) {
        String returnvalue = "NOPRINT";

        if (!GlobalMemberValues.isStrEmpty(paramItemCategoryIdx)) {
            String strQuery = "";
            strQuery = "select kitchenprintnum from salon_storeservice_main where idx = '" + paramItemCategoryIdx + "' ";
            String tempKitchenprintnum = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
            if (GlobalMemberValues.isStrEmpty(tempKitchenprintnum)) {
                returnvalue = "1";
            } else {
                returnvalue = tempKitchenprintnum;
            }
        } else {
            returnvalue = "1";
        }

        return returnvalue;
    }


    public static String getSettingKitchenPrinter(String paramKitchenNum) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramKitchenNum)) {
            switch (paramKitchenNum) {
                case "1" : {
                    returnValue = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
                    break;
                }
                case "2" : {
                    returnValue = GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext);
                    break;
                }
                case "3" : {
                    returnValue = GlobalMemberValues.getSavedPrinterNameForKitchen4(MainActivity.mContext);
                    break;
                }
                case "4" : {
                    returnValue = GlobalMemberValues.getSavedPrinterNameForKitchen5(MainActivity.mContext);
                    break;
                }
                case "5" : {
                    returnValue = GlobalMemberValues.getSavedPrinterNameForKitchen6(MainActivity.mContext);
                    break;
                }
            }
        }

        return returnValue;
    }

    public static boolean isSettingKitchenPrinter(String paramKitchenNum) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramKitchenNum)) {
            String tempKitchenPrinterName = "";
            tempKitchenPrinterName = GlobalMemberValues.getSettingKitchenPrinter(paramKitchenNum);
            if (!GlobalMemberValues.isStrEmpty(tempKitchenPrinterName) && !tempKitchenPrinterName.equals("No Printer")) {
                returnValue = true;
            }
        }

        return returnValue;
    }

    public static boolean isSettingLabelPrinter(String paramLabelNum) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramLabelNum)) {
            String tempLabelPrinter = "";
            tempLabelPrinter = GlobalMemberValues.getSettingLabelPrinter(paramLabelNum);
            if (!GlobalMemberValues.isStrEmpty(tempLabelPrinter) && !tempLabelPrinter.equals("No Printer")) {
                returnValue = true;
            }
        }

        return returnValue;
    }
    public static String getSettingLabelPrinter(String paramLabelNum) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramLabelNum)) {
            returnValue = GlobalMemberValues.getSavedPrinterNameForLabel(MainActivity.mContext, paramLabelNum);
        }

        return returnValue;
    }
    public static String getSavedPrinterNameForLabel(Context paramContext, String printerNum) {
        String tempPrinterName = "";
        if (printerNum.isEmpty()) return "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter_label" + printerNum;

        if (MainActivity.mDbInit == null){
            tempPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            tempPrinterName = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return tempPrinterName;
    }

    public static int getSettingKitchenPrinterQty() {
        int returnValue = 0;

        if (GlobalMemberValues.isSettingKitchenPrinter("1")) {
            returnValue++;
        }
        if (GlobalMemberValues.isSettingKitchenPrinter("2")) {
            returnValue++;
        }
        if (GlobalMemberValues.isSettingKitchenPrinter("3")) {
            returnValue++;
        }
        if (GlobalMemberValues.isSettingKitchenPrinter("4")) {
            returnValue++;
        }
        if (GlobalMemberValues.isSettingKitchenPrinter("5")) {
            returnValue++;
        }

        return returnValue;
    }

    public static void setKitchenPrinterValues() {
        GlobalMemberValues.PHONEORDERYN = "N";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "N";
        GlobalMemberValues.PHONEORDER_HOLDCODE = "";

        if (Recall.mKitchenPrintOnRecall.equals("Y")) {
            if (Recall.mActivity != null) {
                Recall.mActivity.recreate();
            }
            Recall.mKitchenPrintOnRecall = "N";
        }

        GlobalMemberValues.logWrite("kitchenPrintLog2", "re kitchen print : " + GlobalMemberValues.mRekitchenprintYN + "\n");
        // reprint 일 경우...
        if (GlobalMemberValues.mRekitchenprintYN == "Y" || GlobalMemberValues.mRekitchenprintYN.equals("Y")) {
            GlobalMemberValues.mRekitchenprintYN = "N";
            return;
        } else {
            if (!GlobalMemberValues.isCustomerSelectReceipt()) {
                if (GlobalMemberValues.GLOBAL_DATABASEBACKUP == 1) {
                    GlobalMemberValues.GLOBAL_DATABASEBACKUPINTENDER = true;
                    CommandButton.backupDatabase(false);
                }
            }
        }
    }

    public static void openCloseKitchenPrintLoading(boolean paramIs, String paramKitchenPrinterNum) {
/**
 if (paramIs) {
 if (!GlobalMemberValues.mKitchenLoading) {
 Intent kitchenPrinterLoadingIntent = new Intent(MainActivity.mContext.getApplicationContext(), KitchenPrinterLoading.class);
 MainActivity.mActivity.startActivity(kitchenPrinterLoadingIntent);
 MainActivity.mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_in_top);
 } else {
 if (KitchenPrinterLoading.kitchenprinterloadingtv != null) {
 KitchenPrinterLoading.kitchenprinterloadingtv.setText("Kitchen Printer --- " + paramKitchenPrinterNum);
 }
 }
 } else {
 if (GlobalMemberValues.mKitchenLoading) {
 GlobalMemberValues.logWrite("kitchenPrintLogWanhaye", "로딩창 닫음에 들어옴.." + "\n");
 KitchenPrinterLoading.closeActivity();
 }
 }
 **/
    }

    public static void initPhoneOrderValues() {
        Recall.mKitchenPrintOnRecall = "N";
        GlobalMemberValues.PHONEORDERYN = "N";
        GlobalMemberValues.PHONEORDER_FORCE_KITCHENPRINT = "N";
        GlobalMemberValues.PHONEORDER_HOLDCODE = "";
    }

    public static void allDisconnectKitchenPrinter() {
        GlobalMemberValues.disconnectPrinter2();
        GlobalMemberValues.disconnectPrinter3();
        GlobalMemberValues.disconnectPrinter4();
        GlobalMemberValues.disconnectPrinter5();
        GlobalMemberValues.disconnectPrinter6();
    }

    public static boolean checkConnectedPosBankKitchPrinterByIp(String paramExceptNum, String paramIp1, String paramIp2, String paramIp3, String paramIp4) {
        boolean returnValue = false;

        int tempCount = 0;

        if (!GlobalMemberValues.isStrEmpty(paramExceptNum)) {
            for (int i = 2; i < 7; i++) {
                if (("J"+i) != ("J"+(paramExceptNum))) {
                    String strQuery = " select count(idx) from salon_storestationsettings_deviceprinter" + i +
                            " where printername = 'PosBank' " +
                            " and networkprinterip1 = '" + paramIp1 + "' " +
                            " and networkprinterip2 = '" + paramIp2 + "' " +
                            " and networkprinterip3 = '" + paramIp3 + "' " +
                            " and networkprinterip4 = '" + paramIp4 + "' ";
                    int printerCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
                    if (printerCnt > 0) {
                        tempCount++;
                    }
                }

            }
        }

        if (tempCount > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    public static void setNoPrinterInKitchenPrinter() {
        Vector<String> strInsertQueryVec = new Vector<String>();
        for (int i = 3; i < 7; i++) {
            String strQuery = " update salon_storestationsettings_deviceprinter" + i + " set " +
                    " printername = '" + GlobalMemberValues.PRINTERGROUPKITCHEN[0] + "', " +
                    " networkprinterip1 = '', " +
                    " networkprinterip2 = '', " +
                    " networkprinterip3 = '', " +
                    " networkprinterip4 = '' ";

            strInsertQueryVec.addElement(strQuery);
        }

        if (strInsertQueryVec.size() > 0) {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String returnResult = "";
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                //
            } else {

            }
        }
    }

    public static boolean isPushMsgReceiving() {
        boolean returnValue = false;

        String strQuery = "select pushreceiveyn from salon_storestationinfo " +
                " where stcode = '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "' ";

        String pushreceiveyn = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
        if (GlobalMemberValues.isStrEmpty(pushreceiveyn)) {
            pushreceiveyn = "N";
        }

        if (pushreceiveyn == "Y" || pushreceiveyn.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static int getCurbsidePickupCount(String paramSalesCode) {
        int returnValue = 0;

//        String strQuery = "select count(*) from salon_store_curbsidepickup " +
//                " where salesCode = '" + paramSalesCode + "' ";
//
//        String tempCnt = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
//        returnValue = GlobalMemberValues.getIntAtString(tempCnt);

        return returnValue;
    }

    public static int getCurbsideNewSideMenuCount(String paramSalesCode) {
        int returnValue = 0;

        String strQuery = "select count(*) from salon_store_curbsidenewsidemenu " +
                " where salesCode = '" + paramSalesCode + "' ";

        String tempCnt = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
        returnValue = GlobalMemberValues.getIntAtString(tempCnt);

        return returnValue;
    }

    public static int getCurbsideNewSideMenuCount_sidemenuidx(String paramSalesCode) {
        int returnValue = 0;

        String strQuery = "select count(*) from salon_store_curbsidenewsidemenu " +
                " where sidemenuidx = '" + paramSalesCode + "' ";

        String tempSidemenuidx = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
        returnValue = GlobalMemberValues.getIntAtString(tempSidemenuidx);

        return returnValue;
    }

    public static int getTableOrderByHoldCode(String paramHoldCode) {
        int returnValue = 0;

        String strQuery = "select count(*) from temp_salecart " +
                " where holdcode = '" + paramHoldCode + "' ";

        String returnCount = MssqlDatabase.getResultSetValueToString(strQuery);
        returnValue = GlobalMemberValues.getIntAtString(returnCount);

        return returnValue;
    }

    public static String getStationCode() {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempStcode = dbInit.dbExecuteReadReturnString("select stcode from basic_pos_information ");

        if (GlobalMemberValues.isStrEmpty(tempStcode)) {
            tempStcode = "";
        }
        returnData = tempStcode;

        return returnData;
    }

    public static String getStationNumber() {
        String returnData = "";

        String tempStcode = GlobalMemberValues.STATION_CODE;
        if (!GlobalMemberValues.isStrEmpty(tempStcode)) {
            String tempStNum = GlobalMemberValues.getJJJSubString(tempStcode, 2, 2);
            returnData = GlobalMemberValues.getIntAtString(tempStNum) + "";
        }

        return returnData;
    }

    public static boolean isCashPadUse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성

        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select cashpaduse from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isCashDrawerOpenOnReceipt() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select cashdraweropenonreceipt from salon_storestationsettings_deviceprinter"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCloudKitchenPrinter() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select cloudkitchenprinteryn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static String getCloudKitchenPrinterUrl() {
        String returnValue = "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select cloudkitchenprinterurl from salon_storestationsettings_system"), 1);
        if (!GlobalMemberValues.isCloudKitchenPrinter()) {
            tempGetValue = "";
        } else {
            if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
                tempGetValue = GlobalMemberValues.API_WEB + "API_KitchenPrintData.asp";
            }
        }
        returnValue = tempGetValue;

        return returnValue;
    }

    public static void sendKitchenPrintingDataToCloud(Context paramContext, Activity paramActivity) {
        if (isUploadKitchenPrintingData) {
            if (CURRENTACTIVITYOPENEDSERVICE_NEWKICHENPRINTINGDATA != null && CURRENTSERVICEINTENT_NEWKICHENPRINTINGDATA != null) {
                CURRENTACTIVITYOPENEDSERVICE_NEWKICHENPRINTINGDATA.stopService(CURRENTSERVICEINTENT_NEWKICHENPRINTINGDATA);
            }

            Intent kitchenPrintToCloudService = new Intent(paramContext.getApplicationContext(), UploadKitchenPrintingDataToCloud.class);
            CURRENTSERVICEINTENT_NEWKICHENPRINTINGDATA = kitchenPrintToCloudService;    // 실행되는 서비스 인텐트를 저장해둔다.
            CURRENTACTIVITYOPENEDSERVICE_NEWKICHENPRINTINGDATA = paramActivity;         // 서비스를 실행시킨 액티비티를 저장해 둔다.
            paramActivity.startService(kitchenPrintToCloudService);
        }
    }

    public static void setKitchenDataUploadResult(String paramIdx) {
        // 성공
        if (!GlobalMemberValues.isStrEmpty(paramIdx)) {
            Vector<String > apiUpdateQueryVec = new Vector<String>();

            String strQuery = "";
            strQuery = "update salon_sales_kitchen_json set isCloudUpload = 1 " +
                    " where idx = '" + paramIdx + "' ";
            apiUpdateQueryVec.addElement(strQuery);

            for (String tempQuery : apiUpdateQueryVec) {
                GlobalMemberValues.logWrite("kitchendatauploadresultlog", "query : " + tempQuery + "\n");
            }

            String returnResult = "";
            // 트랜잭션으로 DB 처리한다.
            returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {
            } else {
            }
        }
    }

    public static void setCloudKitchenPrintingValue() {
        if (GlobalMemberValues.isCloudKitchenPrinter()) {
            GlobalMemberValues.CLOUDKITCHENPRINTING = "Y";
        } else {
            GlobalMemberValues.CLOUDKITCHENPRINTING = "N";
        }
    }

    public static boolean isNewKitchenDataToUpload() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select count(idx) from salon_sales_kitchen_json where isCloudUpload = 0"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "0";
        }

        if (GlobalMemberValues.getIntAtString(tempGetValue) > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    public static void openCashInOutPopup(Activity paramActivity, Context paramContext) {
        if (CashInOutPopup.getCashInout() == 0) {
            MainActivity.proCustomDial = new JJJCustomAnimationDialog(MainActivity.mContext);
            MainActivity.proCustomDial.show();

            Intent intentCashInOutPopup = new Intent(paramContext.getApplicationContext(), CashInOutPopup.class);
            paramActivity.startActivity(intentCashInOutPopup);
            if (GlobalMemberValues.isUseFadeInOut()) {
                paramActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
    }

    public static boolean isAutoReceiptPrinting() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select autoreceiptprintingyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isEloPro() {
        boolean returnValue = false;

        Context mContext = MainActivity.mContext;

        String eloPrintType = "Ordinary";

        if (MainActivity.mProductInfo == null) {
            MainActivity.setEloInit();
        }

        ProductInfo productInfo = MainActivity.mProductInfo;

        if (productInfo != null) {
            EloPlatform platform = productInfo.eloPlatform;
            if (MainActivity.mApiAdapter == null) {
                MainActivity.setEloInit();
            }
            ApiAdapter apiAdapter = MainActivity.mApiAdapter;

            if (apiAdapter == null) {
                returnValue = false;
            }

            switch (platform) {  // TODO: Base off detection, not platform
                case PAYPOINT_1:
                case PAYPOINT_REFRESH:
                    eloPrintType = "Ordinary";
                    returnValue = false;
                    break;
                case PAYPOINT_2:
                    eloPrintType = "Pro";
                    returnValue = true;
                    break;
                default:
                    eloPrintType = "Ordinary";
                    returnValue = false;
            }
        }

        return returnValue;
    }

    public static boolean isCouponInfoView() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select couponinfoview from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isOnlineOrderUseYN() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select onlineorderuseyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void setTimeMenuSetAutoOpenValue() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select timemenuautoreload from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }

        GlobalMemberValues.TIMEMENUSET_AUTOOPEN = tempGetValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getWeekDayNum() {
        int num = 0;
        Calendar cal = null;

        cal = Calendar.getInstance();
        num = cal.get(Calendar.DAY_OF_WEEK) - 1;

        GlobalMemberValues.logWrite("weekdaylog", "weekday : " + num + "\n");


        return num;
    }

    public static void openTimeMenuSelectPopup() {
        if (!GlobalMemberValues.isOpenedTimeMenuPopup) {
            // 타임메뉴 선택창 오픈
            if (MainActivity.mContext != null){
                Intent timeMenuIntent = new Intent(MainActivity.mContext.getApplicationContext(), TimeMenuSet.class);
                MainActivity.mActivity.startActivity(timeMenuIntent);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    MainActivity.mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                }
            }
        }
    }

    public static void openTimeMenuSelectPopupAuto() {
        if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
            GlobalMemberValues.setNowTimeCodeValue();
            if (!GlobalMemberValues.SELECTED_TIME_CODEVALUE.equals(GlobalMemberValues.NOW_TIME_CODEVALUE)) {
                if (GlobalMemberValues.TIMEMENUSET_AUTOOPEN.equals("A") || GlobalMemberValues.TIMEMENUSET_AUTOOPEN == "A") {
                    GlobalMemberValues.setTimeMenuCodeValue(GlobalMemberValues.NOW_TIME_CODEVALUE);
                    GlobalMemberValues.setTimeMenuBottomTv(GlobalMemberValues.NOW_TIME_CODEVALUE);
                    // 메인 서비스를 리로드한다 --------------------------------------------------------------------------------
                    GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(MainActivity.mContext);
                    MainTopCategory mainTopCate = new MainTopCategory(MainActivity.mActivity, MainActivity.mContext, dataAtSqlite, 0);
                    mainTopCate.setTopCategory();
                    // ------------------------------------------------------------------------------------------------------
                } else {
                    openTimeMenuSelectPopup();
                }
            }
        }
    }

    public static void setTimeMenuBottomTv(String paramTimeCode) {
        if (MainActivity.bottomTimeMenuTv != null) {
            if (!GlobalMemberValues.isStrEmpty(paramTimeCode)) {
                MainActivity.bottomTimeMenuTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);

                switch (paramTimeCode) {
                    case "m" : {
                        MainActivity.bottomTimeMenuTv.setText(" Breakfast Time ");
                        MainActivity.bottomTimeMenuTv.setTextColor(Color.parseColor("#a8bc58"));
                        MainActivity.bottomTimeMenuTv.setBackgroundResource(R.drawable.roundlayout_line_timemenu1);
                        break;
                    }
                    case "a" : {
                        MainActivity.bottomTimeMenuTv.setText(" Lunch Time ");
                        MainActivity.bottomTimeMenuTv.setTextColor(Color.parseColor("#de7676"));
                        MainActivity.bottomTimeMenuTv.setBackgroundResource(R.drawable.roundlayout_line_timemenu2);
                        break;
                    }
                    case "e" : {
                        MainActivity.bottomTimeMenuTv.setText(" Dinner Time ");
                        MainActivity.bottomTimeMenuTv.setTextColor(Color.parseColor("#5c8cb4"));
                        MainActivity.bottomTimeMenuTv.setBackgroundResource(R.drawable.roundlayout_line_timemenu3);
                        break;
                    }
                    case "n" : {
                        MainActivity.bottomTimeMenuTv.setText(" Night Time ");
                        MainActivity.bottomTimeMenuTv.setTextColor(Color.parseColor("#a0a0a0"));
                        MainActivity.bottomTimeMenuTv.setBackgroundResource(R.drawable.roundlayout_line_timemenu4);
                        break;
                    }
                }
            } else {
                MainActivity.bottomTimeMenuTv.setText("");
                MainActivity.bottomTimeMenuTv.setBackgroundColor(Color.parseColor("#232531"));
            }
        }
    }

    public static void setTimeMenuUseYN() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select timemenuuseyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "Y";
        }

        GlobalMemberValues.TIMEMENUUSEYN = tempGetValue;
    }

    public static boolean isCouponImmediatelyApply() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select couponimmediately from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "Y";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void setSelectedTimeCodevalue() {
        if (GlobalMemberValues.TIMEMENUUSEYN == "Y" || GlobalMemberValues.TIMEMENUUSEYN.equals("Y")) {
            // 현재 타임메뉴(TimeMenu) 시간대를 설정한다.
            GlobalMemberValues.setNowTimeCodeValue();
            if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.SELECTED_TIME_CODEVALUE)) {
                GlobalMemberValues.setTimeMenuCodeValue(GlobalMemberValues.NOW_TIME_CODEVALUE);
                GlobalMemberValues.setTimeMenuBottomTv(GlobalMemberValues.NOW_TIME_CODEVALUE);
                // 메인 서비스를 리로드한다 --------------------------------------------------------------------------------
                GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(MainActivity.mContext);
                MainTopCategory mainTopCate = new MainTopCategory(MainActivity.mActivity, MainActivity.mContext, dataAtSqlite, 0);
                mainTopCate.setTopCategory();
                // ------------------------------------------------------------------------------------------------------
            }
        }
    }

    public static void setTimeMenuCheckTime() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select timemenuchecktime from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "10";
        }

        GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES = GlobalMemberValues.getIntAtString(tempGetValue);
    }

    public static String getCustomerPagerNewNumber() {
        String strQuery =  "";
        String newCustomerPagerNumber = "";

        DatabaseInit dbInit = new DatabaseInit(Payment.context);

        // 오늘날짜로 검색
        String dateSearchQuery = " strftime('%m-%d-%Y', wdate) = '" + DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet() + "' ";

        // 오늘날짜의 최대 Customer Order Number 구하기
        strQuery = " select customerpagernumber from salon_sales_customerpagernumber " +
                " where " + dateSearchQuery +
                " order by idx desc limit 1 ";
        String maxCustomerPagerNumber = "";
        maxCustomerPagerNumber = dbInit.dbExecuteReadReturnString(strQuery);

        int startPagerNum = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select startpagernum from salon_storestationsettings_system")
        );
        if (startPagerNum > 0) {
            startPagerNum -= 1;
        }

        if (GlobalMemberValues.isStrEmpty(maxCustomerPagerNumber)) {
            maxCustomerPagerNumber = startPagerNum + "";
        } else {
            String maxCustomerPagerNumberOnSettings = dbInit.dbExecuteReadReturnString(
                    "select maxpagernum from salon_storestationsettings_system");
            if (GlobalMemberValues.isStrEmpty(maxCustomerPagerNumberOnSettings)) {
                maxCustomerPagerNumberOnSettings = "50";
            }
            // 오늘날짜의 주문에서 Customer Pager Number 가 Settings 에서 설정한 페이저 최대값 이상일 경우 초기화 (0000)
            if (GlobalMemberValues.getIntAtString(maxCustomerPagerNumber)
                    > (GlobalMemberValues.getIntAtString(maxCustomerPagerNumberOnSettings) -1)) {
                //                maxCustomerPagerNumber = "00";
                maxCustomerPagerNumber = startPagerNum + "";
            }
        }

        int intNewCustomerPagerNumber = GlobalMemberValues.getIntAtString(maxCustomerPagerNumber) + 1;
        String tempNewCustomerPagerNumberForString = intNewCustomerPagerNumber + "";

        String tempStr = "";
        /**
         for (int i = 0; i < (2 - tempNewCustomerPagerNumberForString.length()); i++) {
         tempStr += "0";
         }
         **/

        newCustomerPagerNumber = tempStr + intNewCustomerPagerNumber;

        // 07.30.2022
        // 자릿수
        int pagerNumberofDigits = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select pagernumofdigits from salon_storestationsettings_system "
        ));
        String addStr = "";
        if (pagerNumberofDigits > 0 && pagerNumberofDigits > newCustomerPagerNumber.length()) {
            int tempN = pagerNumberofDigits - newCustomerPagerNumber.length();
            for (int i = 0; i < tempN; i++) {
                addStr += "0";
            }
        }
        newCustomerPagerNumber = addStr + newCustomerPagerNumber;

        return newCustomerPagerNumber;
    }

    public static void setCustomerPagerNewNumber(String paramSalesCode, String paramCustomerPagerNumber) {
        String returnValue = "";
        DatabaseInit dbInit = new DatabaseInit(Payment.context);
        String returnResult = "";
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strQuery = " insert into salon_sales_customerpagernumber ( " +
                " scode, sidx, stcode, salesCode, customerpagernumber " +
                " ) values ( " +
                " '" + GlobalMemberValues.SALON_CODE + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                " '" + paramSalesCode + "', " +
                " '" + paramCustomerPagerNumber + "' " +
                " ) ";
        strInsertQueryVec.addElement(strQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("customerpagernumberlog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
        }
    }


    public static String getCustomerPagerNumber(String paramSalesCode) {
        String returnData = "";

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
        String tempCustomerPagerNumber = dbInit.dbExecuteReadReturnString(
                "select customerpagernumber from salon_sales_customerpagernumber " +
                        " where salesCode = '" + paramSalesCode + "' "
        );

        if (!GlobalMemberValues.isStrEmpty(tempCustomerPagerNumber)) {
            returnData = tempCustomerPagerNumber;
        } else {
            returnData = "";
        }

        return returnData;
    }

    public static boolean isPagerUse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select pageruseyn from salon_storestationsettings_system"), 1);
        if (tempValue.equals("Y") || tempValue == "Y") {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isPagerNumberAuto() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select pagernumberautoyn from salon_storestationsettings_system"), 1);
        if (tempValue.equals("Y") || tempValue == "Y") {
            returnValue = true;
        }
        return returnValue;
    }

    // 영수증 프린터와 키친프린터의 종류가 같은지 여부
    public static boolean isSamePrinterInReceiptAndKitchen() {
        boolean returnValue = false;
        if (!GlobalMemberValues.isCloudKitchenPrinter()) {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String tempSqlQuery = "";

            // 영수증 프린터 이름
            String tempReceiptPrinterName = "";
            tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter";
            tempReceiptPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);

            // 주방 프린터 이름
            String tempKitchenPrinterName = "";
            tempSqlQuery = "select printername from salon_storestationsettings_deviceprinter2";
            tempKitchenPrinterName = dbInit.dbExecuteReadReturnString(tempSqlQuery);

            if (!GlobalMemberValues.isStrEmpty(tempReceiptPrinterName) && !GlobalMemberValues.isStrEmpty(tempKitchenPrinterName)) {
                if (tempReceiptPrinterName.toUpperCase().equals(tempKitchenPrinterName.toUpperCase())) {
                    returnValue = true;

                    GlobalMemberValues.logWrite("sameprintchecklog", "printer name : " + tempReceiptPrinterName + "\n");

                    // 프린터 종류가 POSBANK 일 경우
                    if (tempReceiptPrinterName.toUpperCase().equals("POSBANK")) {

                        // 영수증 프린터 IP4 값
                        String tempReceiptPrinterIp4 = "";
                        tempSqlQuery = "select networkprinterip4 from salon_storestationsettings_deviceprinter";
                        tempReceiptPrinterIp4 = dbInit.dbExecuteReadReturnString(tempSqlQuery);

                        // 주방 프린터 IP4 값
                        String tempKitchenPrinterIp4 = "";
                        tempSqlQuery = "select networkprinterip4 from salon_storestationsettings_deviceprinter2";
                        tempKitchenPrinterIp4 = dbInit.dbExecuteReadReturnString(tempSqlQuery);

                        GlobalMemberValues.logWrite("sameprintchecklog", "receipt ip4 : " + tempReceiptPrinterIp4 + "\n");
                        GlobalMemberValues.logWrite("sameprintchecklog", "kitchen ip4 : " + tempKitchenPrinterIp4 + "\n");

                        if (!tempReceiptPrinterIp4.equals(tempKitchenPrinterIp4)) {
                            returnValue = false;
                        }
                    }
                }
            }
        }
        return returnValue;
    }

    public static String getChangedStringAfterEnterStr(String paramTxt) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            returnValue = GlobalMemberValues.getReplaceText(paramTxt, "\n", " ");
        }

        return returnValue;
    }

    public static boolean isBigScreen() {
        boolean returnValue = false;
        if (GlobalMemberValues.isDeviceElo()) {
            returnValue = true;
        }

        return returnValue;
    }

    public static void saveSignedImageNameInSales(String paramSalesCode, String paramFileName) {
        DatabaseInit dbInit = new DatabaseInit(Payment.context);
        String returnResult = "";
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strQuery = " insert into salon_sales_signedimg ( " +
                " sidx, salesCode, signedimgdir " +
                " ) values ( " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + paramSalesCode + "', " +
                " '" + paramFileName + "' " +
                " ) ";
        strInsertQueryVec.addElement(strQuery);
        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("saveSignedImageNameInSaleslog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
        }
    }

    public static void deleteSignatureImageOnSales(Context paramContext, Activity paramActivity) {
        Intent signatureDeleteIntent = new Intent(paramContext.getApplicationContext(), SignatureImageDelete.class);
        CURRENTSERVICEINTENT_SALE = signatureDeleteIntent;           // 실행되는 서비스 인텐트를 저장해둔다.
        CURRENTACTIVITYOPENEDSERVICE_SALE = paramActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.
        paramActivity.startService(signatureDeleteIntent);
    }

    public static boolean isMileageDonwloadedData(String paramWebSeno) {
        boolean returnValue = false;

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String strQuery = "select seno from member_mileage where idxfromweb = '" + paramWebSeno + "' ";
        Cursor mmhCursor = dbInit.dbExecuteRead(strQuery);
        if (mmhCursor.getCount() > 0 && mmhCursor.moveToFirst()) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isAlertInProductMininumQty() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempCustomerInfoShow = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select productminalertyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempCustomerInfoShow)) {
            tempCustomerInfoShow = "N";
        }
        if (tempCustomerInfoShow == "Y" || tempCustomerInfoShow.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isUnderThanMinimumQtyInProduct(String paramIdx) {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempOnHand = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select onhand from salon_storeproduct where idx = '" + paramIdx + "' "), 1);
        String tempProductalertqty = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select productalertqty from salon_storeproduct where idx = '" + paramIdx + "' "), 1);
        if (GlobalMemberValues.getIntAtString(tempOnHand) < GlobalMemberValues.getIntAtString(tempProductalertqty)) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean saveTempMileageCart() {
        boolean returnValue = false;

        // 먼저 temp_mileagecart 를 비운다.
        Vector<String> deleteQueryVec = new Vector<String>();
        String sqlQuery = "delete from temp_mileagecart";
        deleteQueryVec.addElement(sqlQuery);

        // 트랜잭션으로 DB 처리한다.
        String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(deleteQueryVec);

        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else { // 정상처리일 경우
            Vector<String> insQueryVec = new Vector<String>();

            // temp_mileagecart 에 저장
            String queryStr = " select uid, mileage from salon_member ";
            Cursor customerCursor = MainActivity.mDbInit.dbExecuteRead(queryStr);

            String insQuery = "";

            while (customerCursor.moveToNext()) {
                String tempGetUid = GlobalMemberValues.getDBTextAfterChecked(customerCursor.getString(0), 1);

                double tempGetMileage = GlobalMemberValues.getDoubleAtString(
                        GlobalMemberValues.getDBTextAfterChecked(customerCursor.getString(1), 1));

                if (!GlobalMemberValues.isStrEmpty(tempGetUid)) {
                    insQuery = "insert into temp_mileagecart (uid, mileage) values (" +
                            " '" + tempGetUid +"', " +
                            " '" + tempGetMileage +"' " +
                            " )";
                    insQueryVec.addElement(insQuery);
                }
            }

            for (String tempQuery : insQueryVec) {
                GlobalMemberValues.logWrite("tempmileagecartlog", "insert query : " + tempQuery + "\n");
            }

            if (insQueryVec.size() > 0) {
                String returnResult2 = "";
                returnResult2 = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(insQueryVec);
                if (returnResult2 == "N" || returnResult2 == "") {
                    returnValue = false;
                } else {
                    returnValue = true;
                }
            } else {
                returnValue = true;
            }

        }

        return returnValue;
    }

    public static boolean copyMileageFromTempMileageCart() {
        boolean returnValue = false;

        if (!GlobalMemberValues.IS_COM_FRANCHISE) {
            // 다운로드 받은 salon_member 의 데이터에서 mileage 가 모두 0 일 경우에만 temp_mileagecart 에서 마일리지를 복사해 넣는다..
            int tempCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select count(uid) from salon_member where mileage > 0 "
            ));

            GlobalMemberValues.logWrite("tempmileagecartlog", "tempCount : " + tempCount + "\n");

            // mileage 가 0보다 큰 것이 하나라도 있을 경우 아래부분 실행안한다...
            if (tempCount > 0) {
                // temp_mileagecart 를 비운다.
                Vector<String> deleteQueryVec = new Vector<String>();
                String delQuery = "delete from temp_mileagecart";
                deleteQueryVec.addElement(delQuery);

                // 트랜잭션으로 DB 처리한다.
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(deleteQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    return false;
                } else { // 정상처리일 경우
                    return true;
                }
            }

            Vector<String> updQueryVec = new Vector<String>();

            // temp_mileagecart 에 저장
            String updQuery = "";
            String sqlQuery = " select uid, mileage from temp_mileagecart ";
            Cursor cartCursor = MainActivity.mDbInit.dbExecuteRead(sqlQuery);
            while (cartCursor.moveToNext()) {
                String tempGetUid = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(0), 1);
                double tempGetMileage = GlobalMemberValues.getDoubleAtString(
                        GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(1), 1));
                if (!GlobalMemberValues.isStrEmpty(tempGetUid)) {
                    updQuery = " update salon_member set mileage = '" + tempGetMileage + "' where uid = '" + tempGetUid + "' ";
                    updQueryVec.addElement(updQuery);
                }
            }

            for (String tempQuery : updQueryVec) {
                GlobalMemberValues.logWrite("tempmileagecartlog", "update query : " + tempQuery + "\n");
            }

            if (updQueryVec.size() > 0) {
                String returnResult2 = "";
                returnResult2 = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(updQueryVec);
                if (returnResult2 == "N" || returnResult2 == "") {
                    returnValue = false;
                } else {
                    // temp_mileagecart 를 비운다.
                    Vector<String> deleteQueryVec = new Vector<String>();
                    sqlQuery = "delete from temp_mileagecart";
                    deleteQueryVec.addElement(sqlQuery);

                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(deleteQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                    } else { // 정상처리일 경우
                        returnValue = true;
                    }
                }
            }
        }

        return returnValue;
    }

    public static int getCategoryBackgroundOver(String paramColor) {
        int returnValue = 0;
        if (!GlobalMemberValues.isStrEmpty(paramColor)) {
            switch (paramColor) {
                case "#a0a0a0" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover1;
                    break;
                }
                case "#f8b452" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover2;
                    break;
                }
                case "#b06cd0" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover3;
                    break;
                }
                case "#5c8cb4" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover4;
                    break;
                }
                case "#a8bc58" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover5;
                    break;
                }
                case "#8ababc" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover6;
                    break;
                }
                case "#de7676" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover7;
                    break;
                }
                case "#fc78c4" : {
                    returnValue = R.drawable.roundlayout_button_newcategoryover8;
                    break;
                }
            }
        } else {
            returnValue = R.drawable.roundlayout_button_topcategory;
        }

        return returnValue;
    }

    public static int getCountSaleCart(String paramHoldCode) {
        int returnValue = 0;

        returnValue = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select count(idx) from temp_salecart where holdcode = '" + paramHoldCode + "' " ));

        return returnValue;
    }

    /** Clover 클로버 관련 *******************************************************************************************************/
    public static float globalAddFontSize() {
        float returnValue = 0.0f;
        if (GlobalMemberValues.isDeviceElo()) {
            returnValue = 4.0f;
        } else {
            if (GlobalMemberValues.mDevicePAX) {
                returnValue = 0.0f;
            } else {
                returnValue = 0.0f;
            }
        }

        return returnValue;
    }

    public static void setBooleanDevice() {
        GlobalMemberValues.isThisDeviceElo = GlobalMemberValues.isDeviceEloFromDB();
        GlobalMemberValues.isThisDeviceClover = GlobalMemberValues.isDeviceCloverFromDB();
        GlobalMemberValues.isThisDevicePosbank = GlobalMemberValues.isDevicePosbankFromDB();
    }

    public static boolean isDeviceCloverFromDB() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempDeviceClover = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);
            if (tempDeviceClover.equals("Clover")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static void setCloverConnect(Context paramContext) {
        if (GlobalMemberValues.isDeviceClover()) {
            // 바코드 스캐너 생성
            if (MainActivity.barcodeScanner_clover == null) {
                GlobalMemberValues.logWrite("cloverproductlog", "바코드 생성됨" + "\n");
                MainActivity.barcodeScanner_clover = new BarcodeScanner(paramContext);
            }

            // 프린터 관련 생성
            if (MainActivity.account_clover == null) {
                MainActivity.account_clover = CloverAccount.getAccount(paramContext);
            }
            if (MainActivity.printerConnector_clover == null) {
                MainActivity.printerConnector_clover = new PrinterConnector(paramContext, MainActivity.account_clover, null);
            }

            // 디스플레이 관련 생성
            if (MainActivity.displayConnector_clover == null) {
                MainActivity.displayConnector_clover = new DisplayConnector(paramContext, MainActivity.account_clover, MainActivity.dcListener);
            }
        }
    }

    public static void setCloverDisconnect(Context paramContext) {
        if (GlobalMemberValues.isDeviceClover()) {
            if (MainActivity.printerConnector_clover != null) {
                MainActivity.printerConnector_clover.disconnect();
                MainActivity.printerConnector_clover = null;
            }
            if (MainActivity.displayConnector_clover != null) {
                MainActivity.displayConnector_clover.dispose();
            }
        }
    }

    public static Printer getCloverPrinter() {
        try {
            List<Printer> printers = MainActivity.printerConnector_clover.getPrinters(Category.RECEIPT);
            if (printers != null && !printers.isEmpty()) {
                return printers.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setOrderListOnCloverDisplay(String paramSalesCode) {
        if (GlobalMemberValues.isThisDeviceClover && MainActivity.displayConnector_clover != null) {
            GlobalMemberValues.logWrite("displayonclover", "paramSalesCode : " + paramSalesCode + "");

            MainActivity.items_clover.clear();

            double total_subtotal = 0.0;
            double total_tax = 0.0;
            double total_total = 0.0;

            String strIdx = "";
            String strSvcName = "";
            String strPriceAmount = "";
            String strSTaxAmount = "";
            String strSTotalAmount = "";

            String strQuery = "select idx, svcName, sPriceAmount, sTaxAmount, sTotalAmount " +
                    " from temp_salecart " +
                    " where holdcode = '" + paramSalesCode + "' ";
            Cursor tempSaleCartCursor;
            tempSaleCartCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            int tempSaleCartCount = 0;
            while (tempSaleCartCursor.moveToNext()) {
                strIdx = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(0), 1);
                strSvcName = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(1), 1);
                strPriceAmount = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(2), 1);
                strSTaxAmount = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(3), 1);
                strSTotalAmount = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(4), 1);

                total_subtotal += GlobalMemberValues.getDoubleAtString(strPriceAmount);
                total_tax += GlobalMemberValues.getDoubleAtString(strSTaxAmount);
                total_total += GlobalMemberValues.getDoubleAtString(strSTotalAmount);

                MainActivity.items_clover.add(new DisplayLineItem("{\"id\":\"" + strIdx + "\",\"name\":\"" + strSvcName +
                        "\",\"price\":\"$" + GlobalMemberValues.getCommaStringForDouble(strPriceAmount) + "\"}"));
                MainActivity.displayOrder_clover.setLineItems(MainActivity.items_clover);

                tempSaleCartCount++;
            }

            double tempPickupDeliveryFee = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString());
            String tempPickUpDeliveryStr =GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPTEXTTV.getText().toString();

            if (tempPickupDeliveryFee > 0) {
                MainActivity.items_clover.add(new DisplayLineItem("{\"id\":\"" + "pickupdelivery" + "\",\"name\":\"" + tempPickUpDeliveryStr +
                        "\",\"price\":\"$" + GlobalMemberValues.getCommaStringForDouble(tempPickupDeliveryFee + "") + "\"}"));
                MainActivity.displayOrder_clover.setLineItems(MainActivity.items_clover);
            }

            String tempSubTotal = GlobalMemberValues.getReplaceText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString(), "$", "");
            String tempTax = GlobalMemberValues.getReplaceText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText().toString(), "$", "");
            String tempTotal = GlobalMemberValues.getReplaceText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString(), "$", "");
            MainActivity.displayOrder_clover.setSubtotal(tempSubTotal);
            MainActivity.displayOrder_clover.setTax(tempTax);
            MainActivity.displayOrder_clover.setTotal(tempTotal);

            MainActivity.displayConnector_clover.showDisplayOrder(MainActivity.displayOrder_clover);

            if (tempSaleCartCount == 0) {
                GlobalMemberValues.clearCloverDisplay();
            }
        }
    }

    public static void setMsgOnCloverDisplay(String paramMsg) {
        if (MainActivity.displayConnector_clover != null) {
            MainActivity.displayConnector_clover.showMessage(paramMsg);
        }
    }

    public static void clearCloverDisplay() {
        if (MainActivity.displayConnector_clover != null) {
            MainActivity.displayConnector_clover.showWelcomeScreen();
        }
    }

    public static void showMsgOnPaymentFinishment(String paramMsg) {
//        GlobalMemberValues.setMsgOnCloverDisplay(paramMsg);
//        try {
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        GlobalMemberValues.clearCloverDisplay();
    }

    public static void cloverBarcodeScannerOn() {
        Bundle extras = new Bundle();
        extras.putBoolean(Intents.EXTRA_LED_ON, false);
        if (MainActivity.barcodeScanner_clover != null) {
            MainActivity.barcodeScanner_clover.executeStartScan(extras);
        }
    }

    public static void cloverBarcodeScannerOff() {
        if (MainActivity.barcodeScanner_clover != null) {
            MainActivity.barcodeScanner_clover.executeStopScan(null);
        }
    }

    public static void oepnRotateScreenPopup(Activity paramActivity, Context paramContext, String paramExtra) {
        Intent rotateScreenIntent = new Intent(paramContext.getApplicationContext(), PaymentRotateScreen.class);
        rotateScreenIntent.putExtra("getclosingselectreceiptpageyn", paramExtra);
        paramActivity.startActivity(rotateScreenIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            paramActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    public static void printingViewOnClover(LinearLayout paramView) {
        //setTextView(paramView);
        //GlobalMemberValues.logWrite("printLnLog", paramView.toString() + "\n");

        GlobalMemberValues.logWrite("printLnLog", "width : " + paramView.getWidth() + "\n");
        GlobalMemberValues.logWrite("printLnLog", "height : " + paramView.getHeight() + "\n");

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

        View emptyView = new View(MainActivity.mContext);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
        paramView.addView(emptyView);

        //setTextView(v);
        paramView.setDrawingCacheEnabled(true);
        paramView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());

        //Log.d("MainActivity", "===================="+v.getWidth() + ":" + px);

        Bitmap bitMap = Bitmap.createBitmap(px, paramView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        paramView.draw(canvas);
        if(bitMap != null) {
            //ivTest.setImageBitmap(bitMap); // 직접 화면에서 확인하려면 주석 해제후 확인 가능
            new ImagePrintJob.Builder().bitmap(bitMap).maxWidth().build().print(MainActivity.mContext, MainActivity.account_clover);
        }
        paramView.setDrawingCacheEnabled(false);
    }

    public static void printingOnClover(String str, int fontSize) {
        GlobalMemberValues.logWrite("cloverpintinglog", "str : " + str + "\n");

        String regex = "\\[\\[(.*)\\]\\]"; // 정규식
        //Typeface myTypeface = Typeface.createFromAsset(MainActivity.mAsset, "fonts/RobotoCondensed-Regular.ttf");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String[] arr = pattern.split(str);
        int i = 0;
        LinearLayout ll = new LinearLayout(MainActivity.mContext);
        ll.setLayoutParams(new LinearLayout.LayoutParams(604, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        while (matcher.find()) {
            String path = matcher.group(1);
            TextView tv = new TextView(MainActivity.mContext);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setTextColor(Color.BLACK);
            tv.setIncludeFontPadding(false);
            tv.getPaint().setAntiAlias(false);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            tv.setText(arr[i++]);
            ll.addView(tv);
            ImageView iv = new ImageView(MainActivity.mContext);
            iv.setImageBitmap(BitmapFactory.decodeFile(path));
            ll.addView(iv);
        }
        if (arr.length == i+1) { // 이미지 이후 텍스트가 있다면 출력해야 함.
            TextView tv = new TextView(MainActivity.mContext);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setTextColor(Color.BLACK);
            tv.setIncludeFontPadding(false);
            tv.getPaint().setAntiAlias(false);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            tv.setText(arr[i++]);
            ll.addView(tv);
        }

        ll.setDrawingCacheEnabled(true);
        ll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        ll.layout(0, 0, ll.getMeasuredWidth(), ll.getMeasuredHeight());

        Bitmap bitMap = Bitmap.createBitmap(604, ll.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        ll.draw(canvas);
        if(bitMap != null) {
            //ivTest.setImageBitmap(bitMap); // 직접 화면에서 확인하려면 주석 해제후 확인 가능
            new ImagePrintJob.Builder().bitmap(bitMap).maxWidth().build().print(MainActivity.mContext, MainActivity.account_clover);
        }
        ll.setDrawingCacheEnabled(false);
    }

    public static void cloverPrintText(String paramText) {
        GlobalMemberValues.printingOnClover(paramText, 30);
        /**
         if (!GlobalMemberValues.isStrEmpty(paramText)) {
         new TextPrintJob.Builder().text(paramText).build().print(MainActivity.mContext, MainActivity.account_clover);
         }
         **/
    }

    public static void cloverPrintToFilePath(String paramFilePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = 1;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(paramFilePath, opts);

        //final String pathName = "/storage/emulated/0/ST01GRBWG16034042/serviceimage/serviceimg_13711.png"; // 내부이미지 경로만 변경하면 됨.
        final String pathName = paramFilePath;
        new AsyncTask<Void, Void, Pair<Printer,Bitmap>>() {
            @Override
            protected Pair<Printer,Bitmap> doInBackground(Void... voids) {
                try {
                    Bitmap b = BitmapFactory.decodeFile(pathName);
                    Printer p = GlobalMemberValues.getCloverPrinter();
                    return Pair.create(p, b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Pair<Printer,Bitmap> result) {
                if (result == null || result.first == null || result.second == null) {
                    if (!MainActivity.mActivity.isFinishing()) {
//                        Toast.makeText(MainActivity.mContext, "No Image", Toast.LENGTH_SHORT).show();
                    }
                    //return;
                }
                new ImagePrintJob.Builder().bitmap(result.second).maxWidth().build().print(MainActivity.mContext, MainActivity.account_clover, result.first);
            }
        }.execute();
    }

    public static void cloverProCutPaper() {

    }

    public static boolean isCloverPrinterAll() {
        boolean returnValue = false;
        if (isCloverPrinterReceipt() && isCloverPrinterKitchen()) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCloverPrinterReceipt() {
        boolean returnValue = false;
        String tempPrinterName = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);
        if (tempPrinterName.equals("Clover Station") || tempPrinterName.equals("Clover Mini")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCloverPrinterKitchen() {
        boolean returnValue = false;
        String tempPrinterName = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
        if (tempPrinterName.equals("Clover Station") || tempPrinterName.equals("Clover Mini")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void setTextStyleOnClover(TextView paramTv) {
        paramTv.setTypeface(MainActivity.mTypeFace_clover);
        paramTv.setTextColor(Color.parseColor(GlobalMemberValues.PRINTINGFONTCOLOR_ONCLOVER));
        paramTv.setIncludeFontPadding(false);
        paramTv.getPaint().setAntiAlias(false);
    }

    /********************************************************************************************************************************/

    public static TextView getDotLineViewForClover(Context paramContext) {
        TextView dotLineTv1 = new TextView(MainActivity.mContext);
        dotLineTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
        dotLineTv1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        //dotLineTv1.setText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        dotLineTv1.setText("--------------------------------------------------------------------------------------");
        dotLineTv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
        GlobalMemberValues.setTextStyleOnClover(dotLineTv1);

        return dotLineTv1;
    }

    public static TextView getDotLineViewForCloverSmall(Context paramContext) {
        TextView dotLineTv1 = new TextView(MainActivity.mContext);
        dotLineTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
        dotLineTv1.setGravity(Gravity.RIGHT|Gravity.TOP);
        //dotLineTv1.setText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        dotLineTv1.setText("--------------------------------------------------------------------------");
        dotLineTv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
        GlobalMemberValues.setTextStyleOnClover(dotLineTv1);

        return dotLineTv1;
    }

    public static TextView getDotLineViewForCloverMedium(Context paramContext) {
        TextView dotLineTv1 = new TextView(MainActivity.mContext);
        dotLineTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
        dotLineTv1.setGravity(Gravity.RIGHT|Gravity.TOP);
        //dotLineTv1.setText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        dotLineTv1.setText("----------------------------------------------------------------------------------");
        dotLineTv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
        GlobalMemberValues.setTextStyleOnClover(dotLineTv1);

        return dotLineTv1;
    }

    public static TextView getDoubleDotLineViewForClover(Context paramContext) {
        TextView dotLineTv1 = new TextView(MainActivity.mContext);
        dotLineTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
        dotLineTv1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        dotLineTv1.setText("======================================================================================");
        dotLineTv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
        GlobalMemberValues.setTextStyleOnClover(dotLineTv1);

        return dotLineTv1;
    }

    public static TextView getSpaceZoneViewForClover(Context paramContext, int paramValue) {
        TextView spacezoneTv = new TextView(MainActivity.mContext);
        spacezoneTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, paramValue));
        spacezoneTv.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        spacezoneTv.setText("  ");
        spacezoneTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
        GlobalMemberValues.setTextStyleOnClover(spacezoneTv);

        return spacezoneTv;
    }

    public static String getDataInJsonData(JSONObject paramJsonData, String paramKey) {
        String returnValue = "";

        if (paramJsonData != null) {
            // key가 존재하는지 체크한다.
            if(paramJsonData.has(paramKey)) {
                try {
                    returnValue = paramJsonData.getString(paramKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return returnValue;
    }

    public static boolean isLiteVersion() {
        boolean returnValue = false;
        if (GlobalMemberValues.DEVICEPRODUCTTYPE.equals("LITE")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void setInitMainBottomButtonBg() {
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setBackgroundResource(R.drawable.ab_imagebutton_main_discount_lite);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND.setBackgroundResource(R.drawable.ab_imagebutton_main_command_lite);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setBackgroundResource(R.drawable.ab_imagebutton_main_quicksale_lite);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setBackgroundResource(R.drawable.ab_imagebutton_main_mainsale_lite);
        }
    }

    public static boolean isKitchenPrintedOnSalonSales(String paramSalesCode) {
        boolean returnValue = false;

        String kitchenprintedyn_inSalonSales = MainActivity.mDbInit.dbExecuteReadReturnString("select kitchenprintedyn from salon_sales where salesCode = '" + paramSalesCode + "' ");
        if (GlobalMemberValues.isStrEmpty(kitchenprintedyn_inSalonSales)) {
            kitchenprintedyn_inSalonSales = "N";
        }

        if (kitchenprintedyn_inSalonSales.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isKitchenPrintedOnDeliveryInfo(String paramHoldCode) {
        boolean returnValue = false;

        String kitchenprintedyn_inDeliveryInfo = MainActivity.mDbInit.dbExecuteReadReturnString("select kitchenprintedyn from temp_salecart_deliveryinfo where holdcode = '" + paramHoldCode + "' ");
        if (GlobalMemberValues.isStrEmpty(kitchenprintedyn_inDeliveryInfo)) {
            kitchenprintedyn_inDeliveryInfo = "N";
        }

        if (kitchenprintedyn_inDeliveryInfo.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isKitchenPrinted(String paramSalesCode) {
        boolean returnValue = false;

        String tempHoldcode = MainActivity.mDbInit.dbExecuteReadReturnString("select holdcode from salon_sales where salesCode = '" + paramSalesCode + "' ");

        if (GlobalMemberValues.isKitchenPrintedOnSalonSales(paramSalesCode) || GlobalMemberValues.isKitchenPrintedOnDeliveryInfo(tempHoldcode)) {
            returnValue = true;
        }

        return returnValue;
    }

    public static String getThisPOSDevice() {
        String returnValue = "Tablet PC";

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            returnValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select devicekind from salon_storestationsettings_system"), 1);

            if (GlobalMemberValues.isStrEmpty(returnValue)) {
                returnValue = "Tablet PC";
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static void setComFranchise() {
        // 프렌차이즈 여부
        String comFranchiseyn = "N";
        comFranchiseyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select franchiseyn from salon_storegeneral"
        );
        if (GlobalMemberValues.isStrEmpty(comFranchiseyn)) {
            comFranchiseyn = "N";
        }
        GlobalMemberValues.logWrite("franchiseynlog", "comFranchiseyn : " + comFranchiseyn + "\n");
        if (comFranchiseyn == "Y" || comFranchiseyn.equals("Y")) {
            GlobalMemberValues.IS_COM_FRANCHISE = true;
        } else {
            GlobalMemberValues.IS_COM_FRANCHISE = false;
        }

        // 체인점 여부
        String comStoreType = "";
        comStoreType = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select storetype from salon_storegeneral"
        );
        if (GlobalMemberValues.isStrEmpty(comStoreType)) {
            comStoreType = "J1";
        }
        GlobalMemberValues.logWrite("franchiseynlog", "comStoreType : " + comStoreType + "\n");
        if (comStoreType.equals("J0")) {
            GlobalMemberValues.IS_COM_CHAINSTORE = false;
        } else {
            GlobalMemberValues.IS_COM_CHAINSTORE = true;
        }

        GlobalMemberValues.setMileageSyncCloudOnDB(GlobalMemberValues.IS_COM_CHAINSTORE);
    }

    public static String getSearchDateString(DatabaseInit paramDbInit, String paramColumnName) {
        String returnValue = "";
        // 업로드 할 기준일수 구하기 -----------------------------------------------------------------------------------------------------------------------
        String tempAgoDays = paramDbInit.dbExecuteReadReturnString("select daystouploaddata from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempAgoDays)) {
            tempAgoDays = "10";
        }
        // 업로드 할 데이터 기준일
        String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, (GlobalMemberValues.getIntAtString(tempAgoDays) * -1));
        String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 1);

        returnValue = " strftime('%Y-%m-%d', " + paramColumnName + ") between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";
        // -------------------------------------------------------------------------------------------------------------------------------------------------
        return returnValue;
    }

    public static String getSearchDateString2(DatabaseInit paramDbInit, String paramColumnName) {
        String returnValue = "";

        String tempAgoDays = "365";

        String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, (GlobalMemberValues.getIntAtString(tempAgoDays) * -1));
        String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -7);

        returnValue = " strftime('%Y-%m-%d', " + paramColumnName + ") between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' ";

        return returnValue;
    }

    public static void setMileageSyncCloudOnDB(boolean paramValue) {
        String syncYN = "N";
        if (paramValue) {
            syncYN = "Y";
        }

        Vector<String > apiUpdateQueryVec = new Vector<String>();
        String strQuery = "";
        strQuery = "update salon_storestationsettings_system set mileagesyncinselectcustomer = '" + syncYN + "' ";
        apiUpdateQueryVec.addElement(strQuery);
        for (String tempQuery : apiUpdateQueryVec) {
            GlobalMemberValues.logWrite("mileagesyncinselectcustomerlog", "query : " + tempQuery + "\n");
        }
        String returnResult = "";
        // 트랜잭션으로 DB 처리한다.
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
        if (returnResult == "N" || returnResult == "") {
        } else {
        }
    }

    public static String getCodeForUpload(String paramType) {
        String returnValue = "";
        switch (paramType) {
            case "giftcard" : {
                returnValue = "GC" + GlobalMemberValues.makeSalesCode();
                break;
            }
            case "mileage" : {
                returnValue = "MG" + GlobalMemberValues.makeSalesCode();
                break;
            }
            case "tip" : {
                returnValue = "TP" + GlobalMemberValues.makeSalesCode();
                break;
            }
        }
        return returnValue;
    }

    public static boolean isSignOnMinPay(String paramTotalAmount) {
        boolean returnValue = true;

        String tempMinPayForSign = MainActivity.mDbInit.dbExecuteReadReturnString(" select minpayforsign from salon_storegeneral ");
        if (GlobalMemberValues.isStrEmpty(tempMinPayForSign)) {
            tempMinPayForSign = "0";
        }
        double minpayforsignDbl = GlobalMemberValues.getDoubleAtString(tempMinPayForSign);
        double totalpayDbl = GlobalMemberValues.getDoubleAtString(paramTotalAmount);

        if (totalpayDbl < minpayforsignDbl) {
            returnValue = false;
        }

        return returnValue;
    }

    public static boolean isTaxFreeByGetType(String paramGetType) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramGetType)) {
            String tempSql = "";
            switch (paramGetType) {
                case "H" : {
                    tempSql = " select heretaxfreeyn from salon_storegeneral ";
                    break;
                }
                case "T" : {
                    tempSql = " select togotaxfreeyn from salon_storegeneral ";
                    break;
                }
                case "D" : {
                    tempSql = " select deliverytaxfreeyn from salon_storegeneral ";
                    break;
                }
                case "N" : {        // Dine in
                    tempSql = " select dineintaxfreeyn from salon_storegeneral ";
                    break;
                }
            }

            if (!GlobalMemberValues.isStrEmpty(tempSql)) {
                String tempTaxFreeYN = MainActivity.mDbInit.dbExecuteReadReturnString(tempSql);
                if (GlobalMemberValues.isStrEmpty(tempTaxFreeYN)) {
                    tempTaxFreeYN = "N";
                }
                if (tempTaxFreeYN == "Y" || tempTaxFreeYN.equals("Y")) {
                    returnValue = true;
                }
            }
        }

        return returnValue;
    }

    public static void setTaxFreeOnAllItems(String paramGetType) {
        ArrayList<TemporarySaleCart> mArrList = MainMiddleService.mGeneralArrayList;
        if (mArrList != null && mArrList.size() > 0) {
            for (int i = 0; i < mArrList.size(); i++) {
                if (GlobalMemberValues.isPossibleTaxFreeOnToGo(mArrList.get(i).mSvcidx, paramGetType)) {
                    CommandButton.setTaxExempt(i);
                }
            }
        }
    }

    public static void setUndoTaxFreeOnAllItems() {
        ArrayList<TemporarySaleCart> mArrList = MainMiddleService.mGeneralArrayList;
        if (mArrList != null && mArrList.size() > 0) {
            for (int i = 0; i < mArrList.size(); i++) {
                CommandButton.setUndoTaxExemptItem(i);
            }
        }
    }

    public static boolean isPossibleTaxFreeOnToGo(String paramSvcIdx, String paramGetType) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramSvcIdx)) {
            String taxexemptbytotalyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select taxexemptbytotalyn from salon_storegeneral ");
            if (GlobalMemberValues.isStrEmpty(taxexemptbytotalyn)) {
                taxexemptbytotalyn = "Y";
            }

            GlobalMemberValues.logWrite("GetTypeTaxExemptLog", "paramSvcIdx : " + paramSvcIdx + "\n");

            GlobalMemberValues.logWrite("GetTypeTaxExemptLog", "전체아이템 Tax Free 적용여부 : " + taxexemptbytotalyn + "\n");

            if (taxexemptbytotalyn == "Y" || taxexemptbytotalyn.equals("Y")) {
                returnValue = true;
            } else {
                // 아이템별 tax free 일경우
                // 해당 아이템이 배송수단에 따라 tax free 인지 확인

                // 수령타입별때 tax free 일 경우
                //String tempDeliTakeType = MainActivity.mDbInit.dbExecuteReadReturnString(
                //        "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + MainMiddleService.mHoldCode + "' "
                //);
                String tempDeliTakeType = paramGetType;

                GlobalMemberValues.logWrite("GetTypeTaxExemptLog", "tempDeliTakeType : " + tempDeliTakeType + "\n");

                if (!GlobalMemberValues.isStrEmpty(tempDeliTakeType)) {
                    String tempGetTypeStr = "";
                    switch (tempDeliTakeType) {
                        case "H" : {
                            tempGetTypeStr = "here";
                            break;
                        }
                        case "T" : {
                            tempGetTypeStr = "togo";
                            break;
                        }
                        case "D" : {
                            tempGetTypeStr = "delivery";
                            break;
                        }
                        case "N" : {
                            tempGetTypeStr = "dinein";
                            break;
                        }
                    }
                    if (!GlobalMemberValues.isStrEmpty(tempGetTypeStr)) {
                        String tempFoodTaxexemptvalues = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select taxexemptvalues from salon_storeservice_sub where idx = '" + paramSvcIdx + "' ");
                        if (!GlobalMemberValues.isStrEmpty(tempFoodTaxexemptvalues)) {
                            GlobalMemberValues.logWrite("GetTypeTaxExemptLog", "선택한 수령방법 : " + tempGetTypeStr + "\n");
                            GlobalMemberValues.logWrite("GetTypeTaxExemptLog", "아이템의 Taxexemptvalues : " + tempFoodTaxexemptvalues + "\n\n");

                            if (tempFoodTaxexemptvalues.indexOf(tempGetTypeStr) > -1) {
                                returnValue = true;
                            }
                        }
                    }
                }
            }

            /**
             if (!GlobalMemberValues.isStrEmpty(togotaxfreetype)) {
             String tempSaveType = "S";
             switch (paramSaveType) {
             case "0" : {
             tempSaveType = "S";
             break;
             }
             case "1" : {
             tempSaveType = "P";
             break;
             }
             case "2" : {
             tempSaveType = "G";
             break;
             }
             }

             if (togotaxfreetype.indexOf(tempSaveType) > -1) {
             returnValue = true;
             }
             }
             **/
        }

        return returnValue;
    }

    public static String getKitchenPrinterTextSize() {
        String returnValue = "R";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select kitchenprintfontsize from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            returnValue = "R";
        } else {
            returnValue = tempGetValue;
        }

        return returnValue;
    }

    public static boolean isOrderNumberSectionViewOn() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select ordernumbersectionviewyn from salon_storestationsettings_deviceprinter"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "Y";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    public static boolean isKitchenOrderNumberSectionViewOn() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select kitchenordernumbersectionviewyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "Y";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    public static String getEncodingTxt(String paramTxt) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            paramTxt = GlobalMemberValues.getReplaceText(paramTxt, "%", "%25");
            paramTxt = GlobalMemberValues.getReplaceText(paramTxt, "&", "%26");
            paramTxt = GlobalMemberValues.getReplaceText(paramTxt, "!", "%21");

            returnValue = paramTxt;
        }

        return returnValue;
    }

    public static String getDecodingTxt(String paramTxt) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            paramTxt = GlobalMemberValues.getReplaceText(paramTxt, "%25", "%");
            returnValue = paramTxt;
        }
        return returnValue;
    }

    public static boolean isCheckInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isCheckDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void setCheckInternetBeforPayProcess() {
        String tempValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select ischeckbeforecardpay from salon_storestationsettings_paymentgateway"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            GlobalMemberValues.ISCHECK_BEFORE_CARDPAY = true;
        } else {
            GlobalMemberValues.ISCHECK_BEFORE_CARDPAY = false;
        }
    }

    public static boolean isTaxTypeMulti(String paramItemType, String paramItemIdx) {
        boolean returnValue = false;
        try {
            DatabaseInit dbInit = MainActivity.mDbInit;
            String strQuery = "";
            switch (paramItemType) {
                case "S" : {        // 서비스
                    strQuery = "select taxtype from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
                    break;
                }
                case "P" : {        // 상품
                    //strQuery = "select taxtype from salon_storeproduct where idx = '" + paramItemIdx + "' ";
                    break;
                }
            }

            String tempValue = "S";

            if (!GlobalMemberValues.isStrEmpty(strQuery)) {
                tempValue = dbInit.dbExecuteReadReturnString(strQuery);
            }
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "S";
            }

            if (tempValue == "M" || tempValue.equals("M")) {
                returnValue = true;
            }
            GlobalMemberValues.logWrite("getTaxType", "isTaxTypeMulti : " + returnValue + "\n");
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static double getItemTaxInMultiTax(String paramItemType, String paramItemIdx) {
        double returnValue = 0.0;
        try {
            DatabaseInit dbInit = MainActivity.mDbInit;
            String strQuery = "";
            switch (paramItemType) {
                case "S" : {        // 서비스
                    strQuery = "select taxvalues from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
                    break;
                }
                case "P" : {        // 상품
                    //strQuery = "select taxfreeyn from salon_storeproduct where idx = '" + paramItemIdx + "' ";
                    break;
                }
            }

            double tempTaxTotal = 0.0;

            String tempTaxValues = "";

            if (!GlobalMemberValues.isStrEmpty(strQuery)) {
                tempTaxValues = dbInit.dbExecuteReadReturnString(strQuery);
            }

            GlobalMemberValues.logWrite("getItemTaxInMultiTax", "tempTaxValues : " + tempTaxValues + "\n");
            GlobalMemberValues.logWrite("getItemTaxInMultiTax", "GlobalMemberValues.STORE_FOOD_TAX1 : " + GlobalMemberValues.STORE_FOOD_TAX1 + "\n");
            GlobalMemberValues.logWrite("getItemTaxInMultiTax", "GlobalMemberValues.STORE_FOOD_TAX2 : " + GlobalMemberValues.STORE_FOOD_TAX2 + "\n");
            GlobalMemberValues.logWrite("getItemTaxInMultiTax", "GlobalMemberValues.STORE_FOOD_TAX3 : " + GlobalMemberValues.STORE_FOOD_TAX3 + "\n");


            if (GlobalMemberValues.isStrEmpty(tempTaxValues)) {
                returnValue = 0.0;
            } else {
                if (tempTaxValues.indexOf("[1]") > -1) {
                    tempTaxTotal += GlobalMemberValues.STORE_FOOD_TAX1;
                }
                if (tempTaxValues.indexOf("[2]") > -1) {
                    tempTaxTotal += GlobalMemberValues.STORE_FOOD_TAX2;
                }
                if (tempTaxValues.indexOf("[3]") > -1) {
                    tempTaxTotal += GlobalMemberValues.STORE_FOOD_TAX3;
                }

                returnValue = tempTaxTotal;
            }

            GlobalMemberValues.logWrite("getItemTaxInMultiTax", "getItemTaxInMultiTax : " + returnValue + "\n");
        } catch (Exception e) {
        }

        return returnValue;
    }

    public static boolean isAutoBatchInCashInOut(DatabaseInit paramDbInit) {
        boolean returnValue = false;

        String tempBatchincashoutyn = paramDbInit.dbExecuteReadReturnString(
                "select batchincashoutyn from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempBatchincashoutyn)) {
            tempBatchincashoutyn = "N";
        }

        if (tempBatchincashoutyn == "Y" || tempBatchincashoutyn.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static void checkSaleModifyYN() {
        String tempValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select saledatemodifyyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            GlobalMemberValues.ISSALEDATEMODIFY = true;
        } else {
            GlobalMemberValues.ISSALEDATEMODIFY = false;
        }
    }

    public static boolean isDifferentSaleDateToday() {
        boolean returnValue = false;

        if (GlobalMemberValues.ISSALEDATEMODIFY) {
            if (GlobalMemberValues.STR_NOW_DATE.equals(GlobalMemberValues.SETTING_SALE_DATE)) {
                returnValue = false;
            } else {
                returnValue = true;
            }
        }

        return returnValue;
    }

    public static String Payment_stringBackSpace_Exch(int stringLength ,String instr) {
        String return_str = "";
        String temp_str = "";

        if (!GlobalMemberValues.isStrEmpty(instr)) {
            instr = GlobalMemberValues.getReplaceText(instr, "  ", " ");

            instr = GlobalMemberValues.getDecodeString(instr);

            int strLength = GlobalMemberValues.getSizeToString(instr);

            if (strLength > stringLength) {
                //temp_str = instr.substring(0,stringLength) + " ";
                temp_str = GlobalMemberValues.getJJJSubString(instr, 0, stringLength) + "";
            } else {
                String spaceStr = "";
                int i_space = stringLength - strLength;
                StringBuilder temp_build = new StringBuilder(instr);
                for (int i = 0; i < i_space; i++){
                    spaceStr += " ";
                }
                temp_str = temp_build.toString() + spaceStr;
            }

            //return_str = strLength + "-" + temp_str;
            return_str = temp_str;
        }

        return return_str;
    }

    public static String Payment_stringFowardSpace_Exch(int stringLength ,String instr) {
        String return_str = "";
        String temp_str = "";

        if (!GlobalMemberValues.isStrEmpty(instr)) {
            instr = GlobalMemberValues.getReplaceText(instr, "  ", " ");

            instr = GlobalMemberValues.getDecodeString(instr);

            int strLength = GlobalMemberValues.getSizeToString(instr);

            if (strLength > stringLength) {
                //temp_str = instr.substring(0,stringLength);
                temp_str =  " " + GlobalMemberValues.getJJJSubString(instr, 0, stringLength);
            } else {
                int i_space = stringLength - strLength;
                StringBuilder temp_build = new StringBuilder("");
                for (int i = 0; i < i_space; i++){
                    temp_build.append(" ");
                }
                temp_build.append(instr);
                temp_str = temp_build.toString();
            }

            return_str = temp_str;
        }

        return return_str;
    }

    public static void setModifierPriceViewYN() {
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select modifierpriceviewyn from salon_storestationsettings_system"), 1);

            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "N";
            }

            if (tempValue == "Y" || tempValue.equals("Y")) {
                GlobalMemberValues.MODIFIER_PRICEVIEW = true;
            } else {
                GlobalMemberValues.MODIFIER_PRICEVIEW = false;
            }
        } catch (Exception e) {
        }
    }

    public static String makeImageFromLayout(View paramView, String paramFileName) {
        String returnImageFilePath = "";

        if (paramView != null) {
            LinearLayout getPrintingLn = null;
            // ---------------------------------------------------------------------------------------------------------------------------------------------------
            getPrintingLn = (LinearLayout)paramView;

            getPrintingLn.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다

            // LinearLayout to View 변환.
            getPrintingLn.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            getPrintingLn.layout(0, 0, getPrintingLn.getMeasuredWidth(), getPrintingLn.getMeasuredHeight());

            if (getPrintingLn != null) {
                if (getPrintingLn.getDrawingCache() != null) {
                    // View to Bitmap 변환.
                    Bitmap screenBitmap = Bitmap.createBitmap(getPrintingLn.getDrawingCache());

                    // 저장파일전체경로
                    String strFilePath = "";

                    // 파일 저장할 준비.
                    String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String imgSavePath = imgPath + "/tempprintingimg";
                    File tempfile = new File(imgSavePath);  //Pictures폴더 screenshot.png 파일
                    FileOutputStream os = null;
                    if (!tempfile.isDirectory()) tempfile.mkdirs(); // 파일 경로 확인 및 생성.
                    try{
                        os = new FileOutputStream(imgSavePath +"/"+ paramFileName + ".png");
                        screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
                        os.close();

                        strFilePath = imgSavePath +"/"+ paramFileName + ".png";
                    } catch (IOException e){
                        e.printStackTrace();

                    }
                    getPrintingLn.setDrawingCacheEnabled(false);

                    if (new File(strFilePath).exists()) {
                        returnImageFilePath = strFilePath;
                    }
                }
            }
        }

        return  returnImageFilePath;
    }

    public static void setPagerNumberHeaderTxt() {
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = GlobalMemberValues.getPagerNumberHeaderTxt();
            if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                GlobalMemberValues.PAGERNUMBERHEADERTXT = tempValue;
            } else {
                GlobalMemberValues.PAGERNUMBERHEADERTXT = "";
            }
        } catch (Exception e) {
        }
    }

    public static String getPagerNumberHeaderTxt() {
        String returnValue = "";

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            returnValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select pagernumberheadertxt from salon_storestationsettings_system"), 1);
        } catch (Exception e) {
            returnValue = "";
        }

        return returnValue;
    }


    // jihun.park add 0513
    public static void printingViewOnPax(Context context, LinearLayout paramView){

        GlobalMemberValues.logWrite("printLnLog", "width : " + paramView.getWidth() + "\n");
        GlobalMemberValues.logWrite("printLnLog", "height : " + paramView.getHeight() + "\n");

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

        View emptyView = new View(MainActivity.mContext);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
        paramView.addView(emptyView);

        //setTextView(v);
        paramView.setDrawingCacheEnabled(true);
        paramView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());

        //Log.d("MainActivity", "===================="+v.getWidth() + ":" + px);

        Bitmap bitMap = Bitmap.createBitmap(px, paramView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        paramView.draw(canvas);
        /**
         if(bitMap != null) {
         //ivTest.setImageBitmap(bitMap); // 직접 화면에서 확인하려면 주석 해제후 확인 가능
         new ImagePrintJob.Builder().bitmap(bitMap).maxWidth().build().print(MainActivity.mContext, MainActivity.account_clover);
         }
         **/
        paramView.setDrawingCacheEnabled(false);

        POSLinkPrinter.getInstance(context).print(bitMap, POSLinkPrinter.CutMode.FULL_PAPER_CUT, new POSLinkPrinter.PrintListener() {
            @Override
            public void onSuccess() {
//                        dismissDialog(processingDialog, printFinish);
            }

            @Override
            public void onError(ProcessResult processResult) {
//                        dismissDialog(processingDialog, printFinish);
//                        toastError(processResult.getMessage());
            }
        });

//        AppThreadPool.getInstance().runInBackground(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        });
    }

    // jihun.park add 0513
    public static Bitmap printingViewOnEpson(LinearLayout paramView){

        GlobalMemberValues.logWrite("printLnLog", "width : " + paramView.getWidth() + "\n");
        GlobalMemberValues.logWrite("printLnLog", "height : " + paramView.getHeight() + "\n");

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

        View emptyView = new View(MainActivity.mContext);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
        paramView.addView(emptyView);

        //setTextView(v);
        paramView.setDrawingCacheEnabled(true);
        paramView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());

        //Log.d("MainActivity", "===================="+v.getWidth() + ":" + px);

        Bitmap bitMap = Bitmap.createBitmap(px, paramView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        paramView.draw(canvas);
        /**
         if(bitMap != null) {
         //ivTest.setImageBitmap(bitMap); // 직접 화면에서 확인하려면 주석 해제후 확인 가능
         new ImagePrintJob.Builder().bitmap(bitMap).maxWidth().build().print(MainActivity.mContext, MainActivity.account_clover);
         }
         **/
        paramView.setDrawingCacheEnabled(false);

        return bitMap;

    }

    public static void printingViewOnElo(Context context, LinearLayout paramView, ApiAdapter apiAdapter){
        GlobalMemberValues.logWrite("printLnLog", "width : " + paramView.getWidth() + "\n");
        GlobalMemberValues.logWrite("printLnLog", "height : " + paramView.getHeight() + "\n");


        int px = 576;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, MainActivity.mContext.getResources().getDisplayMetrics());

        View emptyView = new View(MainActivity.mContext);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(px, LinearLayout.LayoutParams.MATCH_PARENT));
        paramView.addView(emptyView);

        //setTextView(v);
        paramView.setDrawingCacheEnabled(true);
        paramView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());

        //Log.d("MainActivity", "===================="+v.getWidth() + ":" + px);

        Bitmap bitMap = Bitmap.createBitmap(px, paramView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        canvas.drawColor(Color.WHITE);
        paramView.draw(canvas);

        paramView.setDrawingCacheEnabled(false);
//
//        //
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = false;
//        opts.inSampleSize = 1;
//        opts.inPreferredConfig = Bitmap.Config.RGB_565;


        try {
            apiAdapter.print(EloProPrintMakeImage.getPrintContentsForEloPro(bitMap));
        } catch (RuntimeException | IOException e) {
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static String getSpaceString(int paramSpaceSu) {
        String returnValue = "";

        if (paramSpaceSu > 0) {
            for (int jjj = 0; jjj < paramSpaceSu; jjj++) {
                returnValue += " ";
            }
        }

        return returnValue;
    }

    public static void setIsDualDisplayPossible() {
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select dualdisplaypossible from salon_storestationsettings_system"), 1);
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "N";
            }

            if (tempValue == "Y" || tempValue.equals("Y")) {
                GlobalMemberValues.ISDUALDISPLAYPOSSIBLE = true;
            } else {
                GlobalMemberValues.ISDUALDISPLAYPOSSIBLE = false;
            }
        } catch (Exception e) {
        }
    }

    public static String getModifierName(String paramTxt) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            if (paramTxt.indexOf("(+") > -1 || paramTxt.indexOf("(-") > -1) {
                paramTxt = getReplaceText(paramTxt, "(+", "-JJJ-+");
                paramTxt = getReplaceText(paramTxt, "(-", "-JJJ--");

                String[] tempArr = paramTxt.split("-JJJ-");
                returnValue = tempArr[0];
            } else {
                returnValue = paramTxt;
            }
        }

        return returnValue;
    }

    public static void dismissPresentationView() {
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE
                && MainActivity.mPaxPresentation != null && MainActivity.mPaxPresentation.isShowing()) {
            MainActivity.mPaxPresentation.dismiss();
        }
    }

    public static void showPresentationView() {
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE && MainActivity.mPaxPresentation != null) {
            MainActivity.mPaxPresentation.show();
            String adType = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdptype from salon_storegeneral ");
            if (GlobalMemberValues.isStrEmpty(adType)) {
                adType = "0";
            }
            if (adType == "0" || adType.equals("0")) {
                GlobalMemberValues.logWrite("adimagelogjjj2", "여기실행됨4... " + "\n");
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.start();
                GlobalMemberValues.logWrite("adimagelogjjj2", "여기실행됨5... " + "\n");
            }
            // 230608
            if (MainMiddleService.mPresentationCartAdapter != null && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.setAdapter(MainMiddleService.mPresentationCartAdapter);
                MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();

                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText());
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW != null){
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW.setText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText());
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null){
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText());
                }
            }
            // 230608

        }
    }

    // jihun pax scanner
    public static void paxBarcodeScannerOn(final Context context){
        MainActivity.posLinkScanner = POSLinkScanner.getPOSLinkScanner(context,POSLinkScanner.ScannerType.REAR);
        ProcessResult result = MainActivity.posLinkScanner.open();
        if (result.getCode().equals(ProcessResult.CODE_OK)) {
            MainActivity.posLinkScanner.start(new POSLinkScanner.ScannerListener() {
                @Override
                public void onRead(String s) {
                    GlobalMemberValues.logWrite("paxscannerresult", "scan on result : " + s + "\n");
//                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFinish() {
                    if (MainActivity.posLinkScanner == null) {
//                        Toast.makeText(context, "You have not opened scanner", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ProcessResult result = MainActivity.posLinkScanner.close();
                    if (result.getCode().equals(ProcessResult.CODE_OK)) {
                        GlobalMemberValues.logWrite("paxscannerresult", "scan on result : " + result.getMessage() + "\n");
                    }
                }
            });
        }
    }

    // jihun pax 원형 입니다.
    public static void paxScannerConnect(Context context){
        MainActivity.posLinkScanner = POSLinkScanner.getPOSLinkScanner(context, POSLinkScanner.ScannerType.REAR);
        ProcessResult result = MainActivity.posLinkScanner.open();
        if (result.getCode().equals(ProcessResult.CODE_OK)) {
            // 연결 성공
        } else {
            //연결 실패시
        }
    }
    public static String paxGetScanResult(Context context){
        final String[] resultText = {""};
        if (MainActivity.posLinkScanner == null) {
            // 스케너 연결 안되어있을경우.
            return "";
        }
        MainActivity.posLinkScanner.start(new POSLinkScanner.ScannerListener() {
            @Override
            public void onRead(String s) {
                // 스켄 성공시 실행.
                resultText[0] = s;
            }
            @Override
            public void onFinish() {
                // 스케닝 후 실행되는 함수.
            }
        });
        return resultText[0];
    }
    public static void paxScannerClose(){
        if (MainActivity.posLinkScanner == null) {
            // 연결되어있지 않을때.
            return;
        }
        ProcessResult result = MainActivity.posLinkScanner.close();
        if (result.getCode().equals(ProcessResult.CODE_OK)) {
            // close 성공시.
        }else {
            // close 실패시.
        }
    }

    public static void setIsPriceDollarDisplay() {
        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select pricedollaryn from salon_storestationsettings_system"), 1);
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "N";
            }

            if (tempValue == "Y" || tempValue.equals("Y")) {
                GlobalMemberValues.ISDISPLAYPRICEDOLLAR = true;
            } else {
                GlobalMemberValues.ISDISPLAYPRICEDOLLAR = false;
            }
        } catch (Exception e) {
        }
    }

    // 디렉토리 및 파일 전체 삭제하기
    public static void deleteDirEmpty(String paramDirName) {
        //String path = Environment.getExternalStorageDirectory().toString() + paramDirName;

        File dir    = new File(paramDirName);
        File[] childFileList = dir.listFiles();

        if (dir.exists()) {
            for (File childFile : childFileList) {
                if (childFile.isDirectory()) {
                    deleteDirEmpty(childFile.getAbsolutePath());    //하위 디렉토리
                } else {
                    childFile.delete();    //하위 파일
                }
            }
            dir.delete();
        }
    }

    public static void deleteImageForPrinting() {
        String imgDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String imgPath = imgDir + "/tempprintingimg";

        GlobalMemberValues.deleteDirEmpty(imgPath);
    }

    public static void backupSaleJsonData(JSONObject jsonObject) {
//        // 30일 이전 text 파일 지우기 위치 변경
//        deleteSaleJsonData();

        /////////////////////// 파일 쓰기 ///////////////////////
        String receiptno = "";
        try {
            receiptno = jsonObject.getString("receiptno");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 파일 생성
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/qsrt_sale_data"); // 저장 경로
        // 폴더 생성
        if(!saveFile.exists()){ // 폴더 없을 경우
            saveFile.mkdir(); // 폴더 생성
        }
        try {
            long now = System.currentTimeMillis(); // 현재시간 받아오기
            Date date = new Date(now); // Date 객체 생성
            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
            String nowTime = sdf.format(date);

            BufferedWriter buf = new BufferedWriter(new FileWriter(saveFile+"/" + nowTime + "_" + receiptno + ".txt", true));
            buf.append(nowTime + " "); // 날짜 쓰기
            buf.append(jsonObject.toString()); // 파일 쓰기
            buf.newLine(); // 개행
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteSaleJsonData(){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/qsrt_sale_data"); // 저장 경로
        File[] childFileList = dir.listFiles();

        if (dir.exists()) {
            for (File childFile : childFileList) {
                if (childFile.isDirectory()) {
                    deleteDirEmpty(childFile.getAbsolutePath());    //하위 디렉토리
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
                    long now = System.currentTimeMillis();
                    Date nowDate = new Date(now);
                    Date temp_Date = null;
                    String fileName = childFile.getName();
                    fileName = fileName.substring(0,8);
                    try {
                        temp_Date = dateFormat.parse(fileName);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long calDate = nowDate.getTime() - temp_Date.getTime();
                    // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
                    // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
                    long calDateDays = calDate / ( 24*60*60*1000);
                    calDateDays = Math.abs(calDateDays);
                    if (calDateDays > 30) {
                        childFile.delete();
                    }
                }
            }
//            dir.delete();
        }
    }

    public static boolean isPossibleKitchenPrinting(JSONObject paramData, String paramPrintNum) {
        boolean returnValue = false;

        if (paramData != null) {
            String str_saleitemlist = GlobalMemberValues.getDataInJsonData(paramData, "saleitemlist");

            int tempItemCount = 0;
            if (!GlobalMemberValues.isStrEmpty(str_saleitemlist)) {
                String[] strOrderItemsList = str_saleitemlist.split(GlobalMemberValues.STRSPLITTER_ORDERITEM1);
                for (int i = 0; i < strOrderItemsList.length; i++) {
                    String[] strOrderItems = strOrderItemsList[i].split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);

                    String tempItemNameOptionAdd = strOrderItems[0];
                    String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);

                    String tempItemIdx = "";
                    if (strItemNAmeOptionAdd.length > 4) {
                        tempItemIdx = strItemNAmeOptionAdd[4];
                    }

                    String tempKitchenPrintingNum = "";
                    if (strItemNAmeOptionAdd.length > 5) {
                        tempKitchenPrintingNum = strItemNAmeOptionAdd[5];
                    }

                    GlobalMemberValues.logWrite("possibleprintlog", "paramPrintNum : " + paramPrintNum + "\n");
                    GlobalMemberValues.logWrite("possibleprintlog", "tempKitchenPrintingNum : " + tempKitchenPrintingNum + "\n");

                    if (tempKitchenPrintingNum.indexOf(paramPrintNum) > -1) {
                        tempItemCount++;
                    }
                }
            }

            if (tempItemCount > 0) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }

    public static void printingExecute() {
        if (GlobalMemberValues.isCustomerSelectReceipt()) {
            /**
             boolean b_isCardSale = false;
             if (Payment.jsonrootForPaymentReview != null) {
             try {
             JSONArray j_arr_cardtransactionList =  (JSONArray)Payment.jsonrootForPaymentReview.get("creditcardtransaction");
             if (j_arr_cardtransactionList.length() > 0) b_isCardSale = true;
             } catch (JSONException e) {
             e.printStackTrace();
             }
             }
             //
             **/
            GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "N";
            if (GlobalMemberValues.isAutoReceiptPrinting()) {
                GlobalMemberValues.GLOBAL_AUTORECEIPTPRINTING = "Y";
                // 230109
                String tempReceipttypeonnoselect = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select receipttypeonnoselect from salon_storestationsettings_system "
                );
                switch (tempReceipttypeonnoselect) {
                    case "A" : {
                        GlobalMemberValues.RECEIPTPRINTTYPE = "custmerc";
                        break;
                    }
                    case "C" : {
                        GlobalMemberValues.RECEIPTPRINTTYPE = "customer";
                        break;
                    }
                    case "M" : {
                        GlobalMemberValues.RECEIPTPRINTTYPE = "merchant";
                        break;
                    }
                    default : {
                        GlobalMemberValues.RECEIPTPRINTTYPE = "";
                        break;
                    }
                }
                //
                GlobalMemberValues.printReceiptByJHP(Payment.jsonrootForPaymentReview, Payment.context, "payment");
            }
        } else {
//            GlobalMemberValues.printReceiptByJHP(Payment.jsonroot, Payment.context, "payment");
            GlobalMemberValues.printReceiptByJHP(Payment.jsonrootForPaymentReview, Payment.context, "payment");

        }
        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        if (!(globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R"))){
            GlobalMemberValues.printReceiptByJHP_menu(Payment.jsonrootForPaymentReview, Payment.context, "menu_print");
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING != null && GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING);
    }


    public static void printingKitchenExecute() {
        GlobalMemberValues.printGateByKitchen(Payment.jsonroot_kitchen, Payment.context, "kitchen1");

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING_KITCHEN != null && GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING_KITCHEN != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_PRINTING_KITCHEN.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_PRINTING_KITCHEN);
    }


    public static String addModifierValueInLn(LinearLayout paramLn, String paramQty, String paramTxt, String itemPrice, boolean paramIsBool, boolean isOnlineOrder) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            // 가격표시앞에 $ 표시여부
            String tempDollarStr = "";
            if (GlobalMemberValues.ISDISPLAYPRICEDOLLAR) {
                tempDollarStr = "$";
            }

            if (GlobalMemberValues.isStrEmpty(paramQty)) {
                paramQty = "1";
            }
            int qtyInt = GlobalMemberValues.getIntAtString(paramQty);

            String[] paramTxtArr = paramTxt.split(", ");

            String printingTxt = "";

            // 아이템 모디파이어 가격 한번만 표시하게.
            boolean b_itemPriceShow = true;

            for (int jjj = 0; jjj < paramTxtArr.length; jjj++) {
                LinearLayout saleitemlistLn2 = new LinearLayout(MainActivity.mContext);
                saleitemlistLn2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                saleitemlistLn2.setWeightSum(10.0f);
                saleitemlistLn2.setOrientation(LinearLayout.HORIZONTAL);

                String tempTxt = paramTxtArr[jjj];
                String splitArr[] = {"", ""};
                if (tempTxt.indexOf("(+") > -1 || tempTxt.indexOf("(-") > -1) {
                    GlobalMemberValues.logWrite("printingtestlogjjj", "tempTxt : " + tempTxt + "\n");

                    tempTxt = getReplaceText(tempTxt, "(+", "-JJJ-+");
                    tempTxt = getReplaceText(tempTxt, "(-", "-JJJ--");

                    String[] tempArr = tempTxt.split("-JJJ-");
                    splitArr[0] = tempArr[0];
                    if (tempArr.length > 1) {
                        splitArr[1] = tempArr[1];
                        splitArr[1] = getReplaceText(splitArr[1], ")", "");
                    } else {
                        splitArr[1] = "";
                    }

                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[0] : " + splitArr[0] + "\n");
                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[1] : " + splitArr[1] + "\n");
                } else {
                    splitArr[0] = tempTxt;
                    splitArr[1] = "";
                }

                String addPrice = "";
                if (!itemPrice.equals("NOTPRICE") && GlobalMemberValues.MODIFIER_PRICEVIEW) {
                    if (!GlobalMemberValues.isStrEmpty(splitArr[1])) {
                        double addPriceDbl = GlobalMemberValues.getDoubleAtString(splitArr[1]) * qtyInt;
                        if (addPriceDbl > 0) {

                            // 07082023
                            String getItemPriceTemp = GlobalMemberValues.getItemPriceRateOnReceipt(addPriceDbl + "");
                            if (paramIsBool) {
                                if (getItemPriceTemp.equals("NOSHOW")) {
                                    addPrice = "";
                                } else {
                                    addPrice = "" + tempDollarStr + GlobalMemberValues.getCommaStringForDouble(getItemPriceTemp) + "";
                                }
                            } else {
                                addPrice = "" + tempDollarStr + GlobalMemberValues.getCommaStringForDouble(addPriceDbl + "") + "";
                            }


                        } else {
                            addPrice = "";
                        }
                    }
                }

                if (isOnlineOrder){
                    addPrice = "";
                }

                String tempStr = "";
                if (jjj < (paramTxtArr.length - 1)) {
                    tempStr = "\n";
                }

                int tempHeight = 34;

                /**
                 if (splitArr[0].length() > 30) {
                 tempHeight = (tempHeight * 2) + 4;
                 }
                 **/
//                if (splitArr[0].length() > 30 || GlobalMemberValues.getSizeToString(splitArr[0]) > 30) {
//                    tempHeight = (tempHeight * 2) + 24;
//                }

                for (int z = 25; z <= GlobalMemberValues.getSizeToString(splitArr[0]) ; z += 26){
                    tempHeight += 34;
                }

                // 새 순서대로... (qty           item name              amount)
                TextView saleItemTvContents2 = new TextView(MainActivity.mContext);
                saleItemTvContents2.setGravity(Gravity.TOP | Gravity.LEFT);
                saleItemTvContents2.setText("          ");

                saleItemTvContents2.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
                GlobalMemberValues.setTextStyleOnClover(saleItemTvContents2);
                saleItemTvContents2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeight, 2.0f));//, GlobalMemberValues.PRINTINGITMETITLE2_ONCLOVER + 0.1f));
                saleitemlistLn2.addView(saleItemTvContents2);

                String itemname = splitArr[0];

                TextView saleItemTvContents1 = new TextView(MainActivity.mContext);
                saleItemTvContents1.setGravity(Gravity.TOP | Gravity.LEFT);
                //saleItemTvContents1.setText(Payment_stringBackSpace_Exch(30, splitArr[0]));
                //saleItemTvContents1.setText(CloverMakingViewInPrinting.designTextForItem(splitArr[0], 30));
                saleItemTvContents1.setText(itemname);

                saleItemTvContents1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
                GlobalMemberValues.setTextStyleOnClover(saleItemTvContents1);
                saleItemTvContents1.setLayoutParams(new LinearLayout.LayoutParams(320, tempHeight, 5.5f));//, (GlobalMemberValues.PRINTINGITMETITLE1_ONCLOVER + GlobalMemberValues.PRINTINGITMETITLE3_ONCLOVER - 0.3f)));
                saleitemlistLn2.addView(saleItemTvContents1);

                TextView saleItemTvContents4 = new TextView(MainActivity.mContext);
                saleItemTvContents4.setGravity(Gravity.TOP | Gravity.RIGHT);
                saleItemTvContents4.setText(addPrice);
                saleItemTvContents4.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
                GlobalMemberValues.setTextStyleOnClover(saleItemTvContents4);
                saleItemTvContents4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tempHeight, 2.5f));//, GlobalMemberValues.PRINTINGITMETITLE4_ONCLOVER));
                saleitemlistLn2.addView(saleItemTvContents4);

                // --------------------------------------------------------------------------------------------------------------------------------
                paramLn.addView(saleitemlistLn2);
            }
//            if (GlobalMemberValues.MODIFIER_PRICEVIEW){
//                LinearLayout saleitemlistLn2 = new LinearLayout(MainActivity.mContext);
//                saleitemlistLn2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                saleitemlistLn2.setOrientation(LinearLayout.HORIZONTAL);
//                if (b_itemPriceShow){
//                    if (itemPrice.equals("") || itemPrice.isEmpty()){
//                        itemPrice = "0.00";
//                    }
//                    TextView saleItemTvContents1 = new TextView(MainActivity.mContext);
//                    saleItemTvContents1.setGravity(Gravity.TOP | Gravity.RIGHT);
//                    //saleItemTvContents1.setText(Payment_stringBackSpace_Exch(30, splitArr[0]));
//                    //saleItemTvContents1.setText(CloverMakingViewInPrinting.designTextForItem(splitArr[0], 30));
//                    saleItemTvContents1.setText("option price\t");
//                    saleItemTvContents1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                    GlobalMemberValues.setTextStyleOnClover(saleItemTvContents1);
//                    saleItemTvContents1.setLayoutParams(new LinearLayout.LayoutParams(0, 34, (GlobalMemberValues.PRINTINGITMETITLE1_ONCLOVER + GlobalMemberValues.PRINTINGITMETITLE3_ONCLOVER - 0.1f)));
//                    saleitemlistLn2.addView(saleItemTvContents1);
//
//                    TextView saleItemTvContents5 = new TextView(MainActivity.mContext);
//                    saleItemTvContents5.setGravity(Gravity.TOP | Gravity.RIGHT);
//                    saleItemTvContents5.setText(tempDollarStr + getStringFormatNumber(Double.parseDouble(itemPrice) * qtyInt,"2"));
//                    saleItemTvContents5.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER);
//                    GlobalMemberValues.setTextStyleOnClover(saleItemTvContents5);
//                    saleItemTvContents5.setLayoutParams(new LinearLayout.LayoutParams(0, 34, GlobalMemberValues.PRINTINGITMETITLE4_ONCLOVER));
//                    saleitemlistLn2.addView(saleItemTvContents5);
//                    b_itemPriceShow = false;
//                }
//                paramLn.addView(saleitemlistLn2);
//            }

            returnValue = printingTxt;
        }

        return returnValue;
    }

    public static String addModifierValueInLn_noPrice(LinearLayout paramLn, String paramQty, String paramTxt, int fontsizeup) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            // 가격표시앞에 $ 표시여부
            String tempDollarStr = "";

            if (GlobalMemberValues.isStrEmpty(paramQty)) {
                paramQty = "1";
            }
            int qtyInt = GlobalMemberValues.getIntAtString(paramQty);

            String[] paramTxtArr = paramTxt.split(", ");

            String printingTxt = "";

            // 아이템 모디파이어 가격 한번만 표시하게.
            boolean b_itemPriceShow = true;

            for (int jjj = 0; jjj < paramTxtArr.length; jjj++) {
                LinearLayout saleitemlistLn2 = new LinearLayout(MainActivity.mContext);
                saleitemlistLn2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                saleitemlistLn2.setOrientation(LinearLayout.HORIZONTAL);

                String tempTxt = paramTxtArr[jjj];
                String splitArr[] = {"", ""};
                if (tempTxt.indexOf("(+") > -1 || tempTxt.indexOf("(-") > -1) {
                    GlobalMemberValues.logWrite("printingtestlogjjj", "tempTxt : " + tempTxt + "\n");

                    tempTxt = getReplaceText(tempTxt, "(+", "-JJJ-+");
                    tempTxt = getReplaceText(tempTxt, "(-", "-JJJ--");

                    String[] tempArr = tempTxt.split("-JJJ-");
                    splitArr[0] = tempArr[0];
                    if (tempArr.length > 1) {
                        splitArr[1] = tempArr[1];
                        splitArr[1] = getReplaceText(splitArr[1], ")", "");
                    } else {
                        splitArr[1] = "";
                    }

                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[0] : " + splitArr[0] + "\n");
                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[1] : " + splitArr[1] + "\n");
                } else {
                    splitArr[0] = tempTxt;
                    splitArr[1] = "";
                }

                if (splitArr[0].indexOf("(1ea)") > -1){
                    splitArr[0] = splitArr[0].replace("(1ea)", "");
                } else {
                    if (splitArr[0].indexOf("(") > -1){
                        splitArr[0] = splitArr[0].replace("(", "x ");
                        splitArr[0] = splitArr[0].replace("ea)", "");
                    }
                }



                String addPrice = "";
                String tempStr = "";
                if (jjj < (paramTxtArr.length - 1)) {
                    tempStr = "\n";
                }

                int tempHeight = 36 + fontsizeup;

                /**
                 if (splitArr[0].length() > 30) {
                 tempHeight = (tempHeight * 2) + 4;
                 }
                 **/
                if (splitArr[0].length() > 30 || GlobalMemberValues.getSizeToString(splitArr[0]) > 30) {
                    tempHeight = (tempHeight * 2) + 8;
                }

                // 새 순서대로... (qty           item name              amount)
                TextView saleItemTvContents2 = new TextView(MainActivity.mContext);
                saleItemTvContents2.setGravity(Gravity.TOP | Gravity.LEFT);
                saleItemTvContents2.setText("    ");
                saleItemTvContents2.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + fontsizeup);
                GlobalMemberValues.setTextStyleOnClover(saleItemTvContents2);
                saleItemTvContents2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//, GlobalMemberValues.PRINTINGITMETITLE2_ONCLOVER));
                saleitemlistLn2.addView(saleItemTvContents2);

                TextView saleItemTvContents1 = new TextView(MainActivity.mContext);
                saleItemTvContents1.setGravity(Gravity.TOP | Gravity.LEFT);
                //saleItemTvContents1.setText(Payment_stringBackSpace_Exch(30, splitArr[0]));
                //saleItemTvContents1.setText(CloverMakingViewInPrinting.designTextForItem(splitArr[0], 30));
                saleItemTvContents1.setText(splitArr[0]);
                saleItemTvContents1.setPaintFlags(saleItemTvContents1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                saleItemTvContents1.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + fontsizeup);
                GlobalMemberValues.setTextStyleOnClover(saleItemTvContents1);
                saleItemTvContents1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//, (GlobalMemberValues.PRINTINGITMETITLE1_ONCLOVER + GlobalMemberValues.PRINTINGITMETITLE3_ONCLOVER + 0.1f)));
                saleitemlistLn2.addView(saleItemTvContents1);


                // --------------------------------------------------------------------------------------------------------------------------------
                paramLn.addView(saleitemlistLn2);
            }

            returnValue = printingTxt;
        }

        return returnValue;
    }

    public static String addModifierValueInLn_noPrice_label_txt(StringBuilder textData, String paramQty, String paramTxt, int fontsizeup, com.epson.epos2.printer.Printer mPrinter) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            // 가격표시앞에 $ 표시여부
            String tempDollarStr = "";

            if (GlobalMemberValues.isStrEmpty(paramQty)) {
                paramQty = "1";
            }
            int qtyInt = GlobalMemberValues.getIntAtString(paramQty);

            String[] paramTxtArr = paramTxt.split(", ");

            String printingTxt = "";

            // 아이템 모디파이어 가격 한번만 표시하게.
            boolean b_itemPriceShow = true;

            for (int jjj = 0; jjj < paramTxtArr.length; jjj++) {


                String tempTxt = paramTxtArr[jjj];
                String splitArr[] = {"", ""};
                if (tempTxt.indexOf("(+") > -1 || tempTxt.indexOf("(-") > -1) {
                    GlobalMemberValues.logWrite("printingtestlogjjj", "tempTxt : " + tempTxt + "\n");

                    tempTxt = getReplaceText(tempTxt, "(+", "-JJJ-+");
                    tempTxt = getReplaceText(tempTxt, "(-", "-JJJ--");

                    String[] tempArr = tempTxt.split("-JJJ-");
                    splitArr[0] = tempArr[0];
                    if (tempArr.length > 1) {
                        splitArr[1] = tempArr[1];
                        splitArr[1] = getReplaceText(splitArr[1], ")", "");
                    } else {
                        splitArr[1] = "";
                    }

                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[0] : " + splitArr[0] + "\n");
                    GlobalMemberValues.logWrite("printingtestlogjjj", "splitArr[1] : " + splitArr[1] + "\n");
                } else {
                    splitArr[0] = tempTxt;
                    splitArr[1] = "";
                }

                if (splitArr[0].indexOf("(1ea)") > -1){
                    splitArr[0] = splitArr[0].replace("(1ea)", "");
                } else {
                    if (splitArr[0].indexOf("(") > -1){
                        splitArr[0] = splitArr[0].replace("(", "x ");
                        splitArr[0] = splitArr[0].replace("ea)", "");
                    }
                }



                String addPrice = "";
                String tempStr = "";
                if (jjj < (paramTxtArr.length - 1)) {
                    tempStr = "\n";
                }

                int tempHeight = 36 + fontsizeup;

                /**
                 if (splitArr[0].length() > 30) {
                 tempHeight = (tempHeight * 2) + 4;
                 }
                 **/
                if (splitArr[0].length() > 30 || GlobalMemberValues.getSizeToString(splitArr[0]) > 30) {
                    tempHeight = (tempHeight * 2) + 8;
                }

                textData.append(splitArr[0]+"\n");
                try {
                    mPrinter.addText(textData.toString());
                    textData.delete(0, textData.length());
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }



                // --------------------------------------------------------------------------------------------------------------------------------
            }

            returnValue = printingTxt;
        }

        return returnValue;
    }

    public static void openThankYouPage(Context paramContext, Activity paramActivity) {
        String tempValue = "";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select thankyoupageyn from salon_storestationsettings_system "
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }

        if (tempValue == "Y" || tempValue.equals("Y")) {
            Intent intent = new Intent(paramContext.getApplicationContext(), PaymentThankYou.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            // -------------------------------------------------------------------------------------
            paramActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                paramActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
            }
        }
    }

    // 네트워크 프린트 연결없이 로컬프린팅만 되는지 여부
    public static boolean isOnlyLocalPrinting() {
        boolean returnValue = true;

        // 영수증 프린터 종류
        String tempReceiptPrinter = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);

        // 키친프린터 종류1
        String tempKitchenPrinter1 = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
        // 키친프린터 종류2
        String tempKitchenPrinter2 = GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext);
        // 키친프린터 종류3
        String tempKitchenPrinter3 = GlobalMemberValues.getSavedPrinterNameForKitchen4(MainActivity.mContext);
        // 키친프린터 종류4
        String tempKitchenPrinter4 = GlobalMemberValues.getSavedPrinterNameForKitchen5(MainActivity.mContext);
        // 키친프린터 종류5
        String tempKitchenPrinter5 = GlobalMemberValues.getSavedPrinterNameForKitchen6(MainActivity.mContext);

        /**
         if (tempReceiptPrinter.equals("PosBank")) {
         returnValue = false;
         }
         if (tempKitchenPrinter1.equals("PosBank")) {
         returnValue = false;
         }
         if (tempKitchenPrinter2.equals("PosBank")) {
         returnValue = false;
         }
         if (tempKitchenPrinter3.equals("PosBank")) {
         returnValue = false;
         }
         if (tempKitchenPrinter4.equals("PosBank")) {
         returnValue = false;
         }
         if (tempKitchenPrinter5.equals("PosBank")) {
         returnValue = false;
         }
         **/

        return returnValue;
    }

    public static int getKitchenPrintingCount(String paramKitchenPrinterNum) {
        int returnValue = 1;

        if (!GlobalMemberValues.isStrEmpty(paramKitchenPrinterNum)) {
            int printcount = GlobalMemberValues.kitchenprinter1_papercount;
            switch (paramKitchenPrinterNum) {
                case "1" : {
                    printcount = GlobalMemberValues.kitchenprinter1_papercount;
                    break;
                }
                case "2" : {
                    printcount = GlobalMemberValues.kitchenprinter2_papercount;
                    break;
                }
                case "3" : {
                    printcount = GlobalMemberValues.kitchenprinter3_papercount;
                    break;
                }
                case "4" : {
                    printcount = GlobalMemberValues.kitchenprinter4_papercount;
                    break;
                }
                case "5" : {
                    printcount = GlobalMemberValues.kitchenprinter5_papercount;
                    break;
                }
            }

            /**
             String count_str = MainActivity.mDbInit.dbExecuteReadReturnString(" select receiptpapercount from " + tempPrinterTableName);
             if (!TextUtils.isEmpty(count_str)){
             returnValue = GlobalMemberValues.getIntAtString(count_str);
             }
             **/

            returnValue = printcount;

        }

        return returnValue;
    }

    public static int getKitchenPrintingCount2(String paramKitchenPrinterNum) {
        int returnValue = 1;

        if (!GlobalMemberValues.isStrEmpty(paramKitchenPrinterNum)) {
            String tempPrinterTableName = "salon_storestationsettings_deviceprinter2";
            switch (paramKitchenPrinterNum) {
                case "1" : {
                    tempPrinterTableName = "salon_storestationsettings_deviceprinter2";
                    break;
                }
                case "2" : {
                    tempPrinterTableName = "salon_storestationsettings_deviceprinter3";
                    break;
                }
                case "3" : {
                    tempPrinterTableName = "salon_storestationsettings_deviceprinter4";
                    break;
                }
                case "4" : {
                    tempPrinterTableName = "salon_storestationsettings_deviceprinter5";
                    break;
                }
                case "5" : {
                    tempPrinterTableName = "salon_storestationsettings_deviceprinter6";
                    break;
                }
            }

            String count_str = MainActivity.mDbInit.dbExecuteReadReturnString(" select receiptpapercount from " + tempPrinterTableName);
            if (!TextUtils.isEmpty(count_str)){
                returnValue = GlobalMemberValues.getIntAtString(count_str);
            }

        }

        return returnValue;
    }

    public static boolean isUploadOnlySaledData() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempData = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select uploadynonlysalesdata from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempData)) {
            tempData = "N";
        }
        if (tempData == "Y" || tempData.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void stopBackgroundService() {
        // 서비스 중지 -------------------------------------------------------------------------------------------------------------------------------
        // POS 세일 JSON
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEJSON != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALEJSON != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEJSON.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALEJSON);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CARTDEL != null && GlobalMemberValues.CURRENTSERVICEINTENT_CARTDEL != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CARTDEL.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_CARTDEL);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CART != null && GlobalMemberValues.CURRENTSERVICEINTENT_CART != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_CART.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_CART);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALE != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALE.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALE);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_TIP != null && GlobalMemberValues.CURRENTSERVICEINTENT_TIP != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_TIP.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_TIP);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY != null && GlobalMemberValues.CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_MEMBERMILEAGEHISTORY.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_MEMBERMILEAGEHISTORY);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY != null && GlobalMemberValues.CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY != null) {
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_GIFTCARDNUMBERHISTORY.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_GIFTCARDNUMBERHISTORY);
        }
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_DBBACKUP != null && GlobalMemberValues.CURRENTSERVICEINTENT_DBBACKUP != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_DBBACKUP.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_DBBACKUP);
        // -------------------------------------------------------------------------------------------------------------------------------------------
    }

    public static void setAppValuesInitForClose(Activity paramActivity) {
        // 일부 데이터 초기화
        GlobalMemberValues.initDatabaseDataInClosingApp();

        GlobalMemberValues.logWrite("GLOBAL_DATABASEBACKUP_VALUE", "value : " + GlobalMemberValues.GLOBAL_DATABASEBACKUP + "\n");
        if (GlobalMemberValues.GLOBAL_DATABASEBACKUP == 2) {
            CommandButton.backupDatabase(false);
        }
        // TODO Auto-generated method stub
        MainMiddleService.mGeneralArrayList = null;
        MainMiddleService.mSaleCartAdapter = null;

        MainMiddleService.listViewCount = 0;

        MainMiddleService.selectedPosition = -1;

        SPLASHCOUNT = 0;

        APPOPENCOUNT = 0;

        // 영수증 이미지 폴더 삭제
        GlobalMemberValues.deleteImageForPrinting();

        disconnectPrinter();
        disconnectPrinter2();
        disconnectPrinter3();
        disconnectPrinter4();
        disconnectPrinter5();
        disconnectPrinter6();

        // 231004 추가
        // 30일 이전 text 파일 지우기
        deleteSaleJsonData();
        // 231004 추가

        // 서비스 종료 - Sale, Tip
        GlobalMemberValues.stopBackgroundService();

        eloCfdScreenViewOff();

        // Clover 클로버 관련
        if (GlobalMemberValues.isDeviceClover()) {
            GlobalMemberValues.clearCloverDisplay();
        }
    }

    public static void setRestartAndroidAppMethod(final Activity paramActivity) {
        // jihun add
        if (false){
//        if (isDataBase5mb()){
            new AlertDialog.Builder(paramActivity)
                    .setTitle("NZ ANDROID")
                    .setMessage("Database capacity exceeded " + GlobalMemberValues.init_capacity_db + "mb. Would you initialize the sale data after database backup?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startDatabaseCheckBackup();
                                    doMethodInRestart(paramActivity);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            doMethodInRestart(paramActivity);
                        }
                    })
                    .show();
        } else {
            doMethodInRestart(paramActivity);
        }
    }

    public static void doMethodInRestart(Activity paramActivity) {
        setAppValuesInitForClose(paramActivity);

        paramActivity.moveTaskToBack(true);
        //paramActivity.finish();
        //android.os.Process.killProcess(android.os.Process.myPid());

        //ActivityCompat.finishAffinity(paramActivity);
        restartApplication(paramActivity);
    }

    public static void doMethodInClose(Activity paramActivity) {
        setAppValuesInitForClose(paramActivity);

        if (paramActivity != null) {
            paramActivity.moveTaskToBack(true);
            paramActivity.finish();
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void restartApplication(Activity paramActivity) {
        Intent mStartActivity = new Intent(MainActivity.mContext, MainActivity.class);
        int mPendingIntentId = 101216;
        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.mContext, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)MainActivity.mContext.getSystemService(ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    public static boolean isAutoSaleDataUpload() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempData = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select autosaledatauploadyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempData)) {
            tempData = "N";
        }
        if (tempData == "Y" || tempData.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isSocketKitchenPrinter() {
        boolean returnValue = false;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select cloudkitchenprinteryn from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "S" || tempGetValue.equals("S")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static String getSocketServerIp() {
        String returnValue = "";

        // 서버 / 클라이언트 정보
        String vServerstationip1 = "";
        String vServerstationip2 = "";
        String vServerstationip3 = "";
        String vServerstationip4 = "";

        String strQuery = "select serverstationip1, serverstationip2, serverstationip3, serverstationip4 from salon_storestationsettings_system ";
        Cursor settingsSystemCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            vServerstationip1 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
            vServerstationip2 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
            vServerstationip3 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);
            vServerstationip4 = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);
        }

        returnValue = vServerstationip1 + "." + vServerstationip2 + "." + vServerstationip3 + "." + vServerstationip4;

        return returnValue;
    }

    public static String getSocketServerPort() {
        String returnValue = "";

        // 서버 / 클라이언트 정보
        String vServerstationport = "";

        String strQuery = "select serverstationport from salon_storestationsettings_system ";
        Cursor settingsSystemCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            vServerstationport = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
        }

        returnValue = vServerstationport;

        return returnValue;
    }

    // 소켓 통신용 서버만들기 메소드
    public static void setSocketServer() {
        // 포트번호 가져오기..
        String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select serverport from salon_storestationsettings_system"), 1);
        if (!GlobalMemberValues.isStrEmpty(tempValue)) {
            // 이곳에서 이 스테이션을 소켓통신용 서버로 만든다. (Set Server)

//            Sam4sSocketPrinter sam4sSocketPrinter = new Sam4sSocketPrinter();
            Sam4sSocketPrinter.setSocketPrinterServerStart(Integer.parseInt(tempValue));


        }
    }

    // 소켓통신 키친프린터를 사용할 경우 소켓서버스케이션으로 연결
    public static void connectSocketServer() {
        if (GlobalMemberValues.isSocketKitchenPrinter()) {

            String serverIp = GlobalMemberValues.getSocketServerIp();
            String serverPort = GlobalMemberValues.getSocketServerPort();

            if (!GlobalMemberValues.isStrEmpty(serverIp) && !GlobalMemberValues.isStrEmpty(serverPort)) {
                // 이곳에서 소켓프린터용 서버스테이션으로 연겷나다.

//                Sam4sSocketPrinter sam4sSocketPrinter = new Sam4sSocketPrinter();
                long currentClickTime= SystemClock.uptimeMillis();
                long elapsedTime=currentClickTime-mLastClickTime;
                mLastClickTime=currentClickTime;

                // 중복 클릭인 경우
                if(elapsedTime<=MIN_CLICK_INTERVAL){
                    return;
                } else {
                    Sam4sSocketPrinter.connectServer(serverIp,Integer.parseInt(serverPort));
                    Sam4sSocketPrinter.reconnect_check();
                }
                //
            }
        }
    }

    public static void connectSocketServerToPrint(final JSONObject jsonObject) {
//        if (GlobalMemberValues.isSocketKitchenPrinter()) {
//
//            String serverIp = GlobalMemberValues.getSocketServerIp();
//            String serverPort = GlobalMemberValues.getSocketServerPort();
//
//            if (!GlobalMemberValues.isStrEmpty(serverIp) && !GlobalMemberValues.isStrEmpty(serverPort)) {
//                // 이곳에서 소켓프린터용 서버스테이션으로 연겷나다.
//
////                Sam4sSocketPrinter sam4sSocketPrinter = new Sam4sSocketPrinter();
//                Sam4sSocketPrinter.connectServerToPrint(serverIp,Integer.parseInt(serverPort),jsonObject);
//
//            }
//        }


        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
//                Sam4sSocketPrinter sam4sSocketPrinter = new Sam4sSocketPrinter();
//                sam4sSocketPrinter.connectServerToPrint(GlobalMemberValues.getSocketServerIp(),Integer.parseInt(GlobalMemberValues.getSocketServerPort()),jsonObject);
                Sam4sSocketPrinter.connect_json_toMessage(jsonObject);
            }
        };
        thread.start();

    }

    public static void reconnectSocket() {
        if (GlobalMemberValues.isSocketKitchenPrinter()) {
            String ip_num = GlobalMemberValues.getSocketServerIp();
            String temp_port_num = GlobalMemberValues.getSocketServerPort();
            if (!GlobalMemberValues.isStrEmpty(ip_num) && !GlobalMemberValues.isStrEmpty(temp_port_num)) {
                int port_num = GlobalMemberValues.getIntAtString(temp_port_num);

                Socket socket = null;
                try {
                    socket = new Socket(ip_num, port_num);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (socket != null) {
                    if (socket.isConnected() && ! socket.isClosed()) {
                    } else {
//                        Sam4sSocketPrinter samSocket = new Sam4sSocketPrinter();
                        Sam4sSocketPrinter.connectServer(ip_num, port_num);
                    }
                }
            }
        }
    }

    public static void makeToastText(String msg){
        if (SettingsDeviceKitchen.context != null) {
            GlobalMemberValues.displayDialog(SettingsDeviceKitchen.context, "",
                    msg, "Close");
        }
//        Toast.makeText(MainActivity.mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public static boolean isCloudBackupInTenderBakcup() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select cloudbackupintenderbackupyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void jjjOpenUsbSerial() {
        if (MainActivity.mUsbReceiver == null) {
            MainActivity.mUsbReceiver.openUsbSerial();
            GlobalMemberValues.logWrite("HDJ", "여기 진입합...2");
        }
    }

    public static void jjjCloseUsbSerial() {
        if (MainActivity.mUsbReceiver == null) {
            MainActivity.mUsbReceiver.closeUsbSerial();
        }
    }

    public static int getNewEndOfDay() {
//        // endofday number 구하기 -----------------------------------------------------------------------------------
////        String sqlQuery = "select endofdayNum from salon_sales_endofday_json " +
////                " order by idx desc " +
////                " limit 1 ";
//        String sqlQuery = "select top 1 endofdayNum from salon_sales_endofday_json " +
//                " order by idx desc ";
//        String str_return = "";
//        // mDbinit 가 null 이라서 오류 발생하는 경우 추측.. (CashInout 에서 들어옴)
//        str_return = MssqlDatabase.getResultSetValueToString(sqlQuery);
//        if (str_return == null || str_return == "") str_return = "0";
//        int newEndofdayNum = GlobalMemberValues.getIntAtString(str_return) + 1;
//        // ----------------------------------------------------------------------------------------------------------
//
//        return newEndofdayNum;




        String nowDateStr = GlobalMemberValues.getReplaceText(GlobalMemberValues.STR_NOW_DATE, "-", "");

        // endofday number 구하기 -----------------------------------------------------------------------------------
        String sqlQuery = "select top 1 endofdayNum from salon_sales_endofday_json " +
                " order by idx desc ";
//        String str_return = MainActivity.mDbInit.dbExecuteReadReturnString(sqlQuery);
        String str_return = MssqlDatabase.getResultSetValueToString(sqlQuery);
        String tempEndofNum = "";
        if (str_return == null || str_return == "") {
            tempEndofNum = "1" + nowDateStr + "1";

        } else {

            if (str_return.length() < 8) {
                tempEndofNum = "1" + nowDateStr + "1";

            } else {
                String getDateInDB = GlobalMemberValues.getJJJSubString(str_return, 1, 8);
                String tempNum = GlobalMemberValues.getJJJSubString(str_return, 9, 1);
                if (nowDateStr.equals(getDateInDB)) {
                    tempEndofNum = "1" + getDateInDB + (GlobalMemberValues.getIntAtString(tempNum) + 1);
                } else {
                    tempEndofNum = "1" + nowDateStr + "1";
                }
            }
        }
        // ----------------------------------------------------------------------------------------------------------

        return GlobalMemberValues.getIntAtString(tempEndofNum);
    }

    public static boolean isUseOtherPrintingAppInKitchenPrinting() {
        boolean returnValue = false;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select cloudkitchenprinteryn from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "P" || tempGetValue.equals("P")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean saveKitchenPrintingDataFromOtherApp(
            Context paramContext, String paramScode, String paramSidx, String paramStcode, String paramSalesCode, String paramKitchenData) {
        boolean returnValue = false;

        DatabaseInit dbInit = new DatabaseInit(paramContext);

        // 먼저 동일한 데이터중 프린팅이 안된 데이터일 경우에만 저장한다..
        int tempCnt = GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
                " select count(idx) from salon_sales_kitchenprintingdata " +
                        " where stcode = '" + paramStcode + "' " +
                        " and salesCode = '" + paramSalesCode + "' " +
                        " and printedyn = 'N' " +
                        " and deleteyn = 'N' "
        ));

        if (tempCnt == 0) {
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = " insert into salon_sales_kitchenprintingdata ( " +
                    " scode, sidx, stcode, salesCode, jsonstr " +
                    " ) values ( " +
                    " '" + paramScode + "', " +
                    " '" + paramSidx + "', " +
                    " '" + paramStcode + "', " +
                    " '" + paramSalesCode + "', " +
                    " '" + paramKitchenData + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("saveKitchenPrintingDataFromOtherAppLog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                returnValue = false;
            } else {
                returnValue = true;
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    /** 키친프린팅용앱에 사용되는 메소드들 시작 ****************************************************************************************************************/

    /**
     // 키친프린팅 타이머 시작 ------------------------------------------------------------------------------------------------------------------------
     public static void runTimer_kitchenprinting() {
     // 30초마다 타이머를 돌린다. ----------------------------------------------------------------
     TimerTask tt_checkweborder = new TimerTask() {
    @Override
    public void run() {
    Message message = timerhandler_kitchenprinting.obtainMessage();
    timerhandler_kitchenprinting.sendMessage(message);
    }
    };

     /////////// / Timer 생성 //////////////
     Timer timer = new Timer();
     timer.schedule(tt_checkweborder, 0, 10000);
     //////////////////////////////////////
     // ----------------------------------------------------------------------------------------
     }

     public static final Handler timerhandler_kitchenprinting = new Handler() {
     public void handleMessage(Message msg)
     {
     Context timerContext = MainActivity.mContext;
     String getDatas = GlobalMemberValues.getDataForKitchenPrinting(timerContext);

     if (!GlobalMemberValues.isStrEmpty(getDatas)) {
     String getDataArr[] = getDatas.split("-JJJWHY-");
     String tempIdx = "";
     String tempJsonData = "";
     if (getDataArr.length > 0) {
     tempIdx = getDataArr[0];
     }
     if (getDataArr.length > 1) {
     tempJsonData = getDataArr[1];
     }

     if (!GlobalMemberValues.isStrEmpty(tempJsonData)) {
     JSONObject jsondata = null;
     try {
     jsondata = new JSONObject(tempJsonData);
     } catch (JSONException e) {
     e.printStackTrace();
     }
     GlobalMemberValues.printGateByKitchen(jsondata, timerContext, "kitchen1");

     // 프린팅 처리
     GlobalMemberValues.setPrintedKitchenPrinting(timerContext, tempIdx);
     }
     }

     }
     };
     // 키친프린팅 타이머 끝 ------------------------------------------------------------------------------------------------------------------------
     **/


    public static void setPrintedKitchenPrinting(Context paramContext, String paramIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramIdx)) {
            String strQuery =  "";
            DatabaseInit dbInit = new DatabaseInit(paramContext);
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            strQuery = " update salon_sales_kitchenprintingdata set " +
                    " printedyn = 'Y' " +
                    " where idx = '" + paramIdx + "' ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("setPrintedKitchenPrintinglog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {
            }
        }
    }

    public static String getDataForKitchenPrinting(Context paramContext) {
        String returnValue = "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String strQuery = "select idx, jsonstr " +
                " from salon_sales_kitchenprintingdata " +
                " where printedyn = 'N' and delyn = 'N' " +
                " order by idx asc limit 1 ";
        Cursor getDataCursor = dbInit.dbExecuteRead(strQuery);
        if (getDataCursor.getCount() > 0 && getDataCursor.moveToFirst()) {
            String tempIdx = GlobalMemberValues.getDBTextAfterChecked(getDataCursor.getString(0), 1);
            String tempJsonStr = GlobalMemberValues.getDBTextAfterChecked(getDataCursor.getString(1), 1);

            if (!GlobalMemberValues.isStrEmpty(tempIdx) && !GlobalMemberValues.isStrEmpty(tempJsonStr)) {
                returnValue = tempIdx + "-JJJWHY-" + tempJsonStr;
            }
        }

        return returnValue;
    }

    public static String getLocalKitchenPrintingConnectingType() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select localkitchenprintingtype from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }

        return tempGetValue;
    }

    /** 키친프린팅용앱에 사용되는 메소드들 끝 ******************************************************************************************************************/

    // 외부앱 설치 확인
    public static boolean isInstalledTheApp(Context paramContext, String paramPackageName) {
        boolean isExist = false;

        PackageManager packMgr = paramContext.getPackageManager();
        List<ResolveInfo> instApps;
        Intent tempIntent = new Intent(Intent.ACTION_MAIN, null);
        tempIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        instApps = packMgr.queryIntentActivities(tempIntent, 0);

        try {
            for (int i = 0; i < instApps.size(); i++) {
                if(instApps.get(i).activityInfo.packageName.startsWith(paramPackageName)){
                    isExist = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            isExist = false;
        }

        return isExist;
    }

    // 외부앱 실행중인지 확인
    public static boolean isRunTheApp(Context paramContext, String paramPackageName) {
        boolean returnValue = false;

        ActivityManager am = (ActivityManager)paramContext.getSystemService(Activity.ACTIVITY_SERVICE);
        String strPackage = "";
        List<ActivityManager.RunningAppProcessInfo> proceses = null;
        proceses = am.getRunningAppProcesses();

        //프로세서 전체를 반복
        for (ActivityManager.RunningAppProcessInfo process : proceses) {
            if(process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                strPackage = process.processName;
                // package 이름과 동일하다면
                if (strPackage.equals(paramPackageName)) {
                    returnValue = true;
                    break;
                }
            }
        }

        return returnValue;
    }


    public static boolean isBluetoothKitchenPrinter() {
        boolean returnValue = false;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select cloudkitchenprinteryn from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "B" || tempGetValue.equals("B")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void bluetooth_sendMessage(String paramJsonData) {
        // Check that we're actually connected before trying anything
        if (MainActivity.mBluetooth_PrinterService.getState() != BlueToothPrinterService.STATE_CONNECTED) {
//            Toast.makeText(MainActivity.mContext, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (paramJsonData.length() > 0) {
            // Get the paramJsonData bytes and tell the BluetoothChatService to write
            byte[] send = paramJsonData.getBytes();
            MainActivity.mBluetooth_PrinterService.write(send);
        }
    }

    public static void bluetooth_saveMacAddress(Activity paramActivity, String address) {
        SharedPreferences pref = paramActivity.getSharedPreferences("bluetooth_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("bluetooth_mac_address", address);
        editor.commit();

        // 연결된 블루투스 정보 저장
        GlobalMemberValues.setSaveConnectedBluetoothDeviceInfo(address);
    }

    public static void setSaveConnectedBluetoothDeviceInfo(String paramAddress) {
        DatabaseInit dbInit = MainActivity.mDbInit;

        if (!GlobalMemberValues.isStrEmpty(paramAddress)) {
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "";

            strQuery = " delete from salon_connectedbluetoothinfo " +
                    " where deviceaddress = '" + paramAddress + "' ";
            strInsertQueryVec.addElement(strQuery);

            strQuery = " insert into salon_connectedbluetoothinfo ( " +
                    " deviceaddress " +
                    " ) values ( " +
                    " '" + paramAddress + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("setSaveConnectedBluetoothDeviceInfolog", "query : " + tempQuery + "\n");
            }
            if (strInsertQueryVec.size() > 0) {
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                } else {
                    GlobalMemberValues.logWrite("setSaveConnectedBluetoothDeviceInfolog","연결된 블루투스 정보 저장완료" + "\n");
                }
            }
        }
    }

    public static String getConnectedBluetoothInfo() {
        String returnValue = "";
        returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(" select deviceaddress from salon_connectedbluetoothinfo order by idx desc limit 1 ");
        if (returnValue.equals("0")) {
            returnValue = "";
        }
        returnValue = returnValue + "";
        return returnValue;
    }

    public static void setKitchenPrintingType() {
        String getValue = MainActivity.mDbInit.dbExecuteReadReturnString(" select kitchenprintingtexttype from salon_storestationsettings_system ");
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "I";
        }

        GlobalMemberValues.kitchenprinting_type = getValue;
    }

    public static void initDatabaseDataInClosingApp() {
        DatabaseInit dbInit = MainActivity.mDbInit;

        if (dbInit != null) {
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "";

            String schDateStr = GlobalMemberValues.getSearchDateString2(MainActivity.mDbInit, "wdate");

            strQuery = " delete from temp_salecart_optionadd where " + schDateStr;
            strInsertQueryVec.addElement(strQuery);

            strQuery = " delete from temp_salecart_optionadd_imsi where " + schDateStr;
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("initDatabaseDataInClosingApplog", "query : " + tempQuery + "\n");
            }
            if (strInsertQueryVec.size() > 0) {
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                } else {
                    //GlobalMemberValues.logWrite("setSaveConnectedBluetoothDeviceInfolog","연결된 블루투스 정보 저장완료" + "\n");
                }
            }
        }
    }

    public static void downloadImageFromCloud(String paramItemGroup) {
        switch (paramItemGroup) {
            case "store" : {
                // 스토어 데이터 다운로드일 경우 스토어정보를 초기화한다.
                MainActivity.setStoreInformationInit();
                break;
            }
            case "service" : {
                // 서비스/카테고리 데이터 다운로드일 경우 이미지를 다운로드한다.
                GlobalMemberValues.logWrite("InGettingQuery", "여기 실행이오...1" + "\n");
                ImageDownload imgDownload = new ImageDownload();
                imgDownload.getDownloadCloudFiles("service");
                GlobalMemberValues.logWrite("InGettingQuery", "여기 실행이오...2" + "\n");
            }
            case "member" : {
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITSECAFTERDOWNLOAD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getTipAdjustedN() {
        int returnValue = 0;
        String strQuery = " select count(idx) from salon_sales_tip where adjustedyn = 'N' " +
                " and salesCode not in (select salesCode from salon_sales_batch where delyn = 'N') " +
                " and substr(salesCode, 2) not in (select substr(salesCode, 2) from salon_sales_card where substr(salesCode, 1, 1) = 'V') ";
        returnValue = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(strQuery)
        );

        return returnValue;
    }

    public static void exeOneTimeTipAdjustment() {
        GlobalMemberValues.logWrite("onetimetipadjustmentlog", "여기실행2" + "\n");
        BatchSummary.tipAdjustInBatch("Y");

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_ONETIMETIPADJUSTMENT != null && GlobalMemberValues.CURRENTSERVICEINTENT_ONETIMETIPADJUSTMENT != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_ONETIMETIPADJUSTMENT.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_ONETIMETIPADJUSTMENT);
    }

    public void setOneTimeTipAdjustment() {
        // One Time Tip Adjustment 를 백그라운드에서 (서비스에서) 실행한다. -------------------------------------------------------------------
        // Service 에서 프린팅 되도록 처리한다..
        Intent tempIntent = new Intent(MainActivity.mContext.getApplicationContext(), JJJ_TipProcessing_onService.class);
        GlobalMemberValues.CURRENTSERVICEINTENT_ONETIMETIPADJUSTMENT = tempIntent;                             // 실행되는 서비스 인텐트를 저장해둔다.
        GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_ONETIMETIPADJUSTMENT = MainActivity.mActivity;         // 서비스를 실행시킨 액티비티를 저장해 둔다.
        MainActivity.mActivity.startService(tempIntent);
        // ------------------------------------------------------------------------------------------------------------------------------------
    }

    public String[] getTImeMenuTimes() {
        String[] returnValue = null;

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String strQuery = "select mstart, astart, estart, nstart from salon_storegeneral ";
        Cursor valueCursor = dbInit.dbExecuteRead(strQuery);
        if (valueCursor.getCount() > 0 && valueCursor.moveToFirst()) {
            String mStartTxt = GlobalMemberValues.getDBTextAfterChecked(valueCursor.getString(0), 1);
            String aStartTxt = GlobalMemberValues.getDBTextAfterChecked(valueCursor.getString(1), 1);
            String eStartTxt = GlobalMemberValues.getDBTextAfterChecked(valueCursor.getString(2), 1);
            String nStartTxt = GlobalMemberValues.getDBTextAfterChecked(valueCursor.getString(3), 1);

            if (GlobalMemberValues.isStrEmpty(mStartTxt)) {
                mStartTxt = "600";
            } else {
                if (mStartTxt.length() < 3) {
                    mStartTxt = mStartTxt + "00";
                }
            }
            if (GlobalMemberValues.isStrEmpty(aStartTxt)) {
                aStartTxt = "1200";
            } else {
                if (aStartTxt.length() < 3) {
                    aStartTxt = aStartTxt + "00";
                }
            }
            if (GlobalMemberValues.isStrEmpty(eStartTxt)) {
                eStartTxt = "1800";
            } else {
                if (eStartTxt.length() < 3) {
                    eStartTxt = eStartTxt + "00";
                }
            }
            if (GlobalMemberValues.isStrEmpty(nStartTxt)) {
                nStartTxt = "0";
            } else {
                if (nStartTxt == "2400" || nStartTxt.equals("2400")) {
                    nStartTxt = "0";
                } else {
                    if (nStartTxt.length() < 3) {
                        nStartTxt = nStartTxt + "00";
                    }
                }
            }

            if (mStartTxt.length() < 4) {
                for (int i = 0; i < (4 - mStartTxt.length()); i++) {
                    mStartTxt = "0" + mStartTxt;
                }
            }
            if (aStartTxt.length() < 4) {
                for (int i = 0; i < (4 - aStartTxt.length()); i++) {
                    aStartTxt = "0" + aStartTxt;
                }
            }
            if (eStartTxt.length() < 4) {
                for (int i = 0; i < (4 - eStartTxt.length()); i++) {
                    eStartTxt = "0" + eStartTxt;
                }
            }
            if (nStartTxt.length() < 4) {
                for (int i = 0; i < (4 - nStartTxt.length()); i++) {
                    nStartTxt = "0" + nStartTxt;
                }
            }

            returnValue = new String[] {mStartTxt, aStartTxt, eStartTxt, nStartTxt};
        } else {
            returnValue = new String[] {"0600", "1200", "1800", "0000"};
        }

        return returnValue;
    }

    // 멀티선택관련 추가
    public static boolean isMultiCheckOnCart() {
        return true;
    }

    public void updateSaleCartDB(TemporaryCustomerInfo paramTemporaryCustomerInfo) {
        Vector<String> strUpdateQueryVec = new Vector<String>();
        String strUpdateQuery = "";

        strUpdateQuery = "update temp_salecart set " +
                " customerId = '" + paramTemporaryCustomerInfo.memUid + "', " +
                " customerName = '" + paramTemporaryCustomerInfo.memName + "', " +
                " customerPhoneNo = '" + paramTemporaryCustomerInfo.memPhone + "' " +
                " where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        strUpdateQueryVec.addElement(strUpdateQuery);

        for (String tempQuery : strUpdateQueryVec) {
            GlobalMemberValues.logWrite("updateSaleCartDBQuery", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String returnResult = "";
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            return;
        } else {
            // 고객정보 변수할당
            GlobalMemberValues.setCustomerSelected(paramTemporaryCustomerInfo);
        }
    }

    public String getPOSType() {
        String returnData = "";
        returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select postype from salon_storegeneral "), 1);
        if (GlobalMemberValues.isStrEmpty(returnData)) {
            returnData = "Q";
        }
        return returnData;
    }

    public String[] getRestaurantTableZone() {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(idx) from salon_store_restaurant_tablezone " +
                " where deleteyn = 'N' and useyn = 'Y' ";
        int dataCount = 0;
        if (MainActivity.mDbInit != null) {
            dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        }
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx, zonename, tablecounts, zonetype from salon_store_restaurant_tablezone " +
                    " where deleteyn = 'N' and useyn = 'Y' " +
                    " order by idx asc";

            int zoneCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String zonename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String tablecounts = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String zonetype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);

                getArr[zoneCount] = idx + "-JJJ-" + zonename + "-JJJ-" + tablecounts + "-JJJ-" + zonetype + "-JJJ-" + "END";

                zoneCount++;
            }

            returnValue = getArr;
        }

        return returnValue;
    }

    public static String[] getRestaurantTable(String paramZoneIdx) {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(*) from salon_store_restaurant_table " +
                " where zoneidx = '" + paramZoneIdx + "'  and deleteyn = 'N' and useyn = 'Y' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx, tablename, capacity, colorvalue, tabletype, chargeridx, pagernum, xvaluerate, yvaluerate, " +
                    " boxtype, boxkinds, size " +
                    " from salon_store_restaurant_table " +
                    " where zoneidx = '" + paramZoneIdx + "' and deleteyn = 'N' and useyn = 'Y' " +
                    " and (quicksaleyn = 'N' or quicksaleyn is null or quicksaleyn = '') " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String tablename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String capacity = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String colorvalue = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
                String tabletype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
                String chargeridx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(5), 1);
                String pagernum = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(6), 1);
                String xvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(7), 1);
                String yvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(8), 1);
                String boxtype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(9), 1);
                String boxkinds = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(10), 1);
                String size = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(11), 1);

                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx("T"+idx, TableSaleMain.mSubTableNum);
                String customerName = MssqlDatabase.getResultSetValueToString(
                        " select customerName from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );

                getArr[tableCount] = idx + "-JJJ-" + tablename + "-JJJ-" + capacity + "-JJJ-" + colorvalue + "-JJJ-" + tabletype +
                        "-JJJ-" + chargeridx + "-JJJ-" + pagernum + "-JJJ-" + xvaluerate + "-JJJ-" + yvaluerate +
                        "-JJJ-" + boxtype + "-JJJ-" + boxkinds + "-JJJ-" + customerName + "-JJJ-" + customerPhone +
                        "-JJJ-" + size + "-JJJ-" + "END";

                GlobalMemberValues.logWrite("tablejjjlog", "arr : " + getArr[tableCount] + "\n");

                tableCount++;
            }

            returnValue = getArr;
        }

        return returnValue;
    }

    public String[] getRestaurantTable_checklist(String paramZoneIdx) {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(idx) from salon_store_restaurant_table " +
                " where zoneidx = '" + paramZoneIdx + "'  and deleteyn = 'N' and useyn = 'Y' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx, tablename, capacity, colorvalue, tabletype, chargeridx, pagernum, xvaluerate, yvaluerate, " +
                    " boxtype, boxkinds " +
                    " from salon_store_restaurant_table " +
                    " where zoneidx = '" + paramZoneIdx + "' and deleteyn = 'N' and useyn = 'Y' " +
                    " and (quicksaleyn = 'N' or quicksaleyn is null or quicksaleyn = '') " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String tablename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String capacity = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String colorvalue = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
                String tabletype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
                String chargeridx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(5), 1);
                String pagernum = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(6), 1);
                String xvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(7), 1);
                String yvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(8), 1);
                String boxtype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(9), 1);
                String boxkinds = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(10), 1);

                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx("T"+idx, TableSaleMain.mSubTableNum);
                String customerName = MssqlDatabase.getResultSetValueToString(
                        " select customerName from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );

                double mTableAmount = 0.0;
                int mSubTableCount = TableSaleMain.getTableSplitCount(idx);

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
                    for (int wj = 0; wj < mSubTableCount; wj++) {
                        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                                " select holdcode from salon_store_restaurant_table_split " +
                                        " where tableidx like '%" + idx + "%' and subtablenum = '" + (wj + 1) + "' "
                        );

                        if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                            mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                    " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                            ));
                        }
                    }
                } else {                                                 // Table split 이 아닌 경우
                    String temp_holdcode = TableSaleMain.getHoldCodeByTableidx(idx, TableSaleMain.mSubTableNum);
//                    GlobalMemberValues.logWrite("strQueryLog", "query : " + " select holdcode from temp_salecart where tableidx like '%" + tableidx + "%' " + "\n");

                    if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                        mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                        ));
                    }
                }

                if (mTableAmount > 0.00){
                    getArr[tableCount] = idx + "-JJJ-" + tablename + "-JJJ-" + capacity + "-JJJ-" + colorvalue + "-JJJ-" + tabletype +
                            "-JJJ-" + chargeridx + "-JJJ-" + pagernum + "-JJJ-" + xvaluerate + "-JJJ-" + yvaluerate +
                            "-JJJ-" + boxtype + "-JJJ-" + boxkinds + "-JJJ-" + customerName + "-JJJ-" + customerPhone + "-JJJ-" + "END";
                    tableCount++;
                }

//                GlobalMemberValues.logWrite("tablejjjlog", "arr : " + getArr[tableCount] + "\n");


            }

            String[] new_getArr = new String[tableCount];

            for (int i = 0; new_getArr.length > i ; i++){
                new_getArr[i] = getArr[i];
            }

            returnValue = new_getArr;
        }

        return returnValue;
    }

    public static String[] getRestaurantTableForQuick() {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(idx) from salon_store_restaurant_table " +
                " where quicksaleyn = 'Y' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx, tablename, capacity, colorvalue, tabletype, chargeridx, pagernum, xvaluerate, yvaluerate, " +
                    " boxtype, boxkinds " +
                    " from salon_store_restaurant_table " +
                    " where quicksaleyn = 'Y' " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String tablename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String capacity = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String colorvalue = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
                String tabletype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
                String chargeridx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(5), 1);
                String pagernum = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(6), 1);
                String xvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(7), 1);
                String yvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(8), 1);
                String boxtype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(9), 1);
                String boxkinds = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(10), 1);

                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx("T"+idx, TableSaleMain.mSubTableNum);
                String customerName = MssqlDatabase.getResultSetValueToString(
                        " select customerName from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );

                getArr[tableCount] = idx + "-JJJ-" + tablename + "-JJJ-" + capacity + "-JJJ-" + colorvalue + "-JJJ-" + tabletype +
                        "-JJJ-" + chargeridx + "-JJJ-" + pagernum + "-JJJ-" + xvaluerate + "-JJJ-" + yvaluerate +
                        "-JJJ-" + boxtype + "-JJJ-" + boxkinds  + "-JJJ-" + customerName  + "-JJJ-" + customerPhone + "-JJJ-" + "END";

                GlobalMemberValues.logWrite("tablejjjlog", "arr : " + getArr[tableCount] + "\n");

                tableCount++;
            }

            returnValue = getArr;
        }

        return returnValue;
    }

    public String[] getRestaurantTableForQuick_checklist() {
        String[] returnValue = null;

        String strQuery = "";
        strQuery = "select count(idx) from salon_store_restaurant_table " +
                " where quicksaleyn = 'Y' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx, tablename, capacity, colorvalue, tabletype, chargeridx, pagernum, xvaluerate, yvaluerate, " +
                    " boxtype, boxkinds " +
                    " from salon_store_restaurant_table " +
                    " where quicksaleyn = 'Y' " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCount = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                String tablename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
                String capacity = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
                String colorvalue = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
                String tabletype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
                String chargeridx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(5), 1);
                String pagernum = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(6), 1);
                String xvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(7), 1);
                String yvaluerate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(8), 1);
                String boxtype = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(9), 1);
                String boxkinds = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(10), 1);

                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx("T"+idx, TableSaleMain.mSubTableNum);
                String customerName = MssqlDatabase.getResultSetValueToString(
                        " select customerName from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );

                double mTableAmount = 0.0;
                int mSubTableCount = TableSaleMain.getTableSplitCount(idx);

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
                    for (int wj = 0; wj < mSubTableCount; wj++) {
                        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                                " select holdcode from salon_store_restaurant_table_split " +
                                        " where tableidx like '%" + idx + "%' and subtablenum = '" + (wj + 1) + "' "
                        );

                        if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                            mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                    " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                            ));
                        }
                    }
                } else {                                                 // Table split 이 아닌 경우
                    String temp_holdcode = TableSaleMain.getHoldCodeByTableidx(idx, TableSaleMain.mSubTableNum);
//                    GlobalMemberValues.logWrite("strQueryLog", "query : " + " select holdcode from temp_salecart where tableidx like '%" + tableidx + "%' " + "\n");

                    if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                        mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                        ));
                    }
                }

                if (mTableAmount > 0.00){
                    getArr[tableCount] = idx + "-JJJ-" + tablename + "-JJJ-" + capacity + "-JJJ-" + colorvalue + "-JJJ-" + tabletype +
                            "-JJJ-" + chargeridx + "-JJJ-" + pagernum + "-JJJ-" + xvaluerate + "-JJJ-" + yvaluerate +
                            "-JJJ-" + boxtype + "-JJJ-" + boxkinds  + "-JJJ-" + customerName  + "-JJJ-" + customerPhone + "-JJJ-" + "END";

                    GlobalMemberValues.logWrite("tablejjjlog", "arr : " + getArr[tableCount] + "\n");

                    tableCount++;
                }
            }

            String[] new_getArr = new String[tableCount];

            for (int i = 0; new_getArr.length > i ; i++){
                new_getArr[i] = getArr[i];
            }

            returnValue = new_getArr;
        }

        return returnValue;
    }

    public static String[] s_str_tableinfo = null;

    // 선택한 Table Sale Main 에서의 Zone Idx
    public static String mGlobal_selectedZoneIdx = null;

    // jihun park progressbar
    public static void loadingProgressBarUpdate(final int contentSizeMax, final int contentSizeState){
//        if (LoadingIntent.mProgressBar != null){
//            LoadingIntent.mProgressBar_handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    final int f_contentSizeMax = contentSizeMax -1;
//                    LoadingIntent.mProgressBar.setProgress((int)((double)contentSizeState/ (double)f_contentSizeMax * 100.0 ));
//                    Log.e("jihunpark","" + ((double)contentSizeState/ (double)f_contentSizeMax * 100.0 ));
//                    Log.e("jihunpark","" + (f_contentSizeMax) + "    " + contentSizeState);
//                    // Show the progress on TextView
//                }
//            });
//        }

        if (GlobalMemberValues.progressBarDialog != null){
            ProgressBarDialog.mProgressBar_handler.post(new Runnable() {
                @Override
                public void run() {
                    final int f_contentSizeMax = contentSizeMax -1;
                    progressBarDialog.setProgress((int)((double)contentSizeState/ (double)f_contentSizeMax * 100.0 ));
                    Log.e("jihunpark","" + ((double)contentSizeState/ (double)f_contentSizeMax * 100.0 ));
                    Log.e("jihunpark","" + (f_contentSizeMax) + "    " + contentSizeState);
                    // Show the progress on TextView
                }
            });
        }

    }

    public static void openRestaurantTable() {
        boolean isopen = true;

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {

            // Table Sale 화면 선택창
//                    Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), TableSaleSelect.class);
//                    mActivity.startActivity(intent);

            GlobalMemberValues.logWrite("opencountjjjlog", "count : " + GlobalMemberValues.mOpenTableMainCount + "\n");

            if (GlobalMemberValues.getStationType().toUpperCase() == "R" || GlobalMemberValues.getStationType().toUpperCase().equals("R")) {
                isopen = true;
            } else {
                if (GlobalMemberValues.mOpenTableMainCount == 0) {
                    isopen = true;
                } else {
                    isopen = false;
                }
            }

            GlobalMemberValues.isOpenTableSaleMain = false;

            if (isopen) {
                Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), TableSaleMain.class);
                MainActivity.mActivity.startActivity(intent);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
                if (MainMiddleService.mGeneralArrayList != null
                        && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW != null){
                    MainMiddleService.initList();
                }

                GlobalMemberValues.logWrite("endtimechecklog", "인텐트 만들어서 테이블창 띄웠음" + "\n");

            }

            GlobalMemberValues.mOpenTableMainCount++     ;
        }
    }

    public boolean isDataInDBTable(String paramTableName, String paramIdx) {
        boolean returnValue = false;
        if (GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select count(idx) from " + paramTableName + " where idx = '" + paramIdx + "' ")) > 0) {
            returnValue = true;
        }
        return returnValue;
    }

    public boolean isPOSWebPay()  {
        boolean returnValue = false;
//        String returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
//                " select webpayuseyn from salon_storegeneral "), 1);
//        if (GlobalMemberValues.isStrEmpty(returnData)) {
//            returnData = "N";
//        }
//
//        if (returnData == "Y" || returnData.equals("Y")) {
//            returnValue = true;
//        }
        // 02032024
        returnValue = GlobalMemberValues.isTableOrderUseYN();
        return returnValue;
    }

    public boolean isCurbsideOrder() {
        boolean returnValue = false;
        String returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select curbsidepickupyn from salon_storegeneral "), 1);
        if (GlobalMemberValues.isStrEmpty(returnData)) {
            returnData = "N";
        }

        if (returnData == "Y" || returnData.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public boolean isSideMenuOrder() {
        boolean returnValue = false;
        String returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select sideorderyn from salon_storegeneral "), 1);
        if (GlobalMemberValues.isStrEmpty(returnData)) {
            returnData = "N";
        }

        if (returnData == "Y" || returnData.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public boolean isOrdersInTable() {
        boolean returnValue = false;

        // 업로드 할 데이터 기준일
        String date1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -1);
        String date2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 1);
        // sqlite
        //String datesql = " and strftime('%Y-%m-%d', wdate) between '" + date1 + "' and '" + date2 + "' ";
        // mssql
        String datesql = " and wdate between dateadd(DAY, -1, '" + GlobalMemberValues.STR_NOW_DATE + "') " +
                " and dateadd(DAY, 1, '" + GlobalMemberValues.STR_NOW_DATE + "') ";

        String strQuery = " select count(*) from temp_salecart " +
                " where not(tableidx = '' or tableidx is null) " + datesql +
                " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' ";
        GlobalMemberValues.logWrite("newtablepaystr", "strQuery : " + strQuery + "\n");

        int returnData = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));
//        int returnData = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (returnData > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    public static Bitmap generateRQCode(String contents){

        Bitmap bitmap = null ;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            /* Encode to utf-8 */
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE,300,300, hints);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    public static String getSystemPaymentTimeout() {
        String returnValue = "";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String text = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select timeout from salon_storestationsettings_paymentgateway"), 1);
        if (GlobalMemberValues.isStrEmpty(text)) {
            text = "1";
        }
        int temp = getIntAtString(text);
        temp = temp * 60 * 1000;
        returnValue = String.valueOf(temp);
        return returnValue;
    }




    // mssql 관련 method ==============================================================================
    public static boolean isPossibleMssqlInfo() {
        if (GlobalMemberValues.mssql_useyn == "Y" || GlobalMemberValues.mssql_useyn.equals("Y")) {
            if (GlobalMemberValues.isStrEmpty(mssql_ip)) {
                return false;
            }
            if (GlobalMemberValues.isStrEmpty(mssql_db)) {
                return false;
            }
            if (GlobalMemberValues.isStrEmpty(mssql_id)) {
                return false;
            }
            if (GlobalMemberValues.isStrEmpty(mssql_pw)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean setConnectMssql() {
        if( MssqlDatabase.tryConnect(true) ){
            return true;
        } else {
            return false;
        }
    }

    public static void setConnectMSSQL() {
        if (GlobalMemberValues.setConnectMssql()) {
            //Toast.makeText(MainActivity.mContext.getApplicationContext(), "-------------- MSSQL Success --------------", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.mContext.getApplicationContext(), "-------------- MSSQL faliure --------------", Toast.LENGTH_SHORT).show();
        }
    }

    public static Long getFileSize(String filepath){
        String size = "";
        Long l_size = 0l;

        File mfile = new File(filepath);
        if (mfile.exists()){
            long lFileSize = mfile.length();
            size = Long.toString(lFileSize) + "bytes";
            l_size = lFileSize;
        } else {
            size = "File not exist";
        }
        return l_size;
    }


    // ================================================================================================

    public static void startDatabaseCheckBackup(){
        String tempPackagename = MainActivity.mContext.getPackageName();
//        File tempFile = MainActivity.mContext.getDatabasePath(GlobalMemberValues.DATABASE_NAME);
//        String tempDbPath = tempFile.getPath();
//        Long l_dbsize = getFileSize(tempDbPath);
//        Log.e("databaseSize",l_dbsize + "");

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            long now = System.currentTimeMillis();
            Date mDate = new Date(now);

            SimpleDateFormat simpleDate = new SimpleDateFormat("MMddyyyy");
            String date_text = simpleDate.format(mDate);

            if (sd.canWrite()) {
                File BackupDir = new File(sd, "Download");
                BackupDir.mkdir();

                File currentDB = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME);
                File backupDB = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME + "_" + date_text);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
//                    Toast.makeText(MainActivity.mContext, "Database backup is Complite", Toast.LENGTH_SHORT).show();

                if (initSalesDataByOut()){
//                        Toast.makeText(MainActivity.mContext, "SaleData reset Complite", Toast.LENGTH_SHORT).show();
                } else {
//                        Toast.makeText(MainActivity.mContext, "SaleData reset failed", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            if (!MainActivity.mActivity.isFinishing()) {
//                    Toast.makeText(MainActivity.mContext, "Database backup is failed", Toast.LENGTH_SHORT).show();
                closeProgress();
            }
            GlobalMemberValues.logWrite("commandButtonDatabase", "에러메시지 : " + e.getMessage().toString() + "\n");
        }

//        if (l_dbsize > (GlobalMemberValues.init_capacity_db * 1048576)) {
//
//        } else {
//
//        }

    }

    public static void closeProgress(){
        if (!MainActivity.mActivity.isFinishing()) {
            Toast.makeText(MainActivity.mContext, "Database backup is failed", Toast.LENGTH_SHORT).show();
        }
        if (CashInOutPopup.mProgressDialog != null){
            CashInOutPopup.mProgressDialog.dismiss();
        }
        if (Employee_Login.mProgressDialog != null){
            Employee_Login.mProgressDialog.dismiss();
        }

    }

    public static boolean isDataBase5mb(){
        String tempPackagename = MainActivity.mContext.getPackageName();
        File tempFile = MainActivity.mContext.getDatabasePath(GlobalMemberValues.DATABASE_NAME);
        String tempDbPath = tempFile.getPath();
        Long l_dbsize = getFileSize(tempDbPath);
        Log.e("databaseSize",l_dbsize + "");
        GlobalMemberValues.init_capacity_db = GlobalMemberValues.getInitDatabseCapacity();
        boolean b_return = false;

        if (l_dbsize > (GlobalMemberValues.init_capacity_db * 1048576)) {
            b_return = true;
        } else {
            b_return = false;
        }
        return b_return;
    }

    public static void setCardTryYNInTempSaleCart(String paramValue) {
        // temp_salecart 의 cardtryyn 의 값을 Y 로 변경 ====================================================================
        Vector<String> tempVec = new Vector<String>();
        String tempSql = " update temp_salecart set cardtryyn = '" + paramValue + "' " +
                " where holdcode = '" + MainMiddleService.mHoldCode + "' ";
        tempVec.addElement(tempSql);

        for (String _query : tempVec) {
            GlobalMemberValues.logWrite("TEMPSALECARTCARDTRYYNLOG", "쿼리 : " + _query + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        String tempResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(tempVec);
        // ===============================================================================================================
    }

    public static String resultDB_checkNull_string(ResultSet rs, int c){
        String str_return = "";

        try{
            str_return = rs.getString(c + 1);
        }catch (Exception e){
            str_return = "";
        }

        return str_return;
    }
    public static int resultDB_checkNull_int(ResultSet rs, int c){
        int i_return = 0;

        try{
            i_return = rs.getInt(c + 1);
        }catch (Exception e){
            i_return = 0;
        }

        return i_return;
    }
    public static double resultDB_checkNull_double(ResultSet rs, int c){
        double i_return = 0;

        try{
            i_return = rs.getDouble(c + 1);
        }catch (Exception e){
            i_return = 0;
        }

        return i_return;
    }

    public static boolean isVoidReturnKitchenPrinting() {
        boolean returnValue = false;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select vrkitchenprinting from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isOnlineOrderAutoReceiptPrinting() {
        boolean returnValue = false;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select autoweborderprinting from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static int getInitDatabseCapacity() {
        int returnValue = 50;
        String tempGetValue = MainActivity.mDbInit.dbExecuteReadReturnString("select initcapacity from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "50";
        }
        returnValue = GlobalMemberValues.getIntAtString(tempGetValue);
        return returnValue;
    }
    public static String getCustomerPointRemaining(Double usePoint){
        Double chargePoint = 0.0;
        if (usePoint == 0){
            chargePoint = getDoubleAtString(GLOBAL_CUSTOMERINFO.memMileage) + GlobalMemberValues.POINT_EARNED;
        } else {
            chargePoint = getDoubleAtString(GLOBAL_CUSTOMERINFO.memMileage) - usePoint;
        }
        return getCommaStringForDouble(String.valueOf(chargePoint));
    }
    public static boolean getQtyaddupYN() {
        boolean returnValue = true;
        String temp_qtyaddupyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select qtyaddupyn from salon_storestationsettings_system "
        );
        if (GlobalMemberValues.isStrEmpty(temp_qtyaddupyn)) {
            returnValue = true;
        } else {
            if (temp_qtyaddupyn.equals("Y")){
                returnValue = true;
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }
    public static boolean getTipaddSalehistoryYN() {
        boolean returnValue = true;
        String temp_tipaddsalehistoryyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select tipaddhistoryvisibleyn from salon_storestationsettings_system "
        );
        if (GlobalMemberValues.isStrEmpty(temp_tipaddsalehistoryyn)) {
            returnValue = true;
        } else {
            if (temp_tipaddsalehistoryyn.equals("Y")){
                returnValue = true;
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }
    public static double getTax3InStoreGeneral() {
        double returnValue = 0.0;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String taxValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tax3 from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(taxValue)) {
            taxValue = "0";
        }
        returnValue = GlobalMemberValues.getDoubleAtString(taxValue);
        return returnValue;
    }

    public static double getTax4InStoreGeneral() {
        double returnValue = 0.0;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String taxValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tax4 from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(taxValue)) {
            taxValue = "0";
        }
        returnValue = GlobalMemberValues.getDoubleAtString(taxValue);
        return returnValue;
    }

    public static double getTax5InStoreGeneral() {
        double returnValue = 0.0;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String taxValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tax5 from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(taxValue)) {
            taxValue = "0";
        }
        returnValue = GlobalMemberValues.getDoubleAtString(taxValue);
        return returnValue;
    }

    public static String setUniCodeStringReplace(String str){
        if (str == null) return "";
        if (str.contains("&#37;")){
            str = str.replace("&#37;","%");
        }
        if (str.contains("&#33;")){
            str = str.replace("&#33;","!");
        }
        if (str.contains("&#35;")){
            str = str.replace("&#35;","#");
        }
        if (str.contains("&#36;")){
            str = str.replace("&#36;","$");
        }
        if (str.contains("&#37;")){
            str = str.replace("&#37;","%");
        }

        return str;
    }

    public static void setPasswordyninmod() {
        String tempValue = "";
        String tempSqlQuery = "select passwordyninmod from salon_storestationsettings_system";
        if (MainActivity.mDbInit == null){
        } else {
            tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        GlobalMemberValues.mPasswordYN_inMod = tempValue;
    }

    public static String setDoubleToString(Double aDouble, int i_decimal){
        BigDecimal bigDecimal = new BigDecimal(aDouble).setScale(i_decimal, RoundingMode.HALF_EVEN);
        String str_double = bigDecimal.toString();

        return str_double;
    }

    public static boolean isLastBillList() {
        boolean returnValue = true;

        if (GlobalMemberValues.isPaymentByBills) {
            String tempQuery = " select count(*) from bill_list where " +
                    " holdcode = N'" + GlobalMemberValues.mSelectedHoldCodeForBillPay + "'" +
                    " and paidyn = N'N' ";
            GlobalMemberValues.logWrite("jjjbilllog", "tempQuery : " + tempQuery + "\n");
            int remainBillCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
            GlobalMemberValues.logWrite("jjjbilllog", "remainBillCnt : " + remainBillCnt + "\n");

            if (remainBillCnt > 1) {
                returnValue = false;
            }
        }

        GlobalMemberValues.logWrite("billlistjjjlog", "returnvalue : " + returnValue + "\n");

        return returnValue;
    }

    public static double getBillPaidAmountByPayType(String paramSalesCode, String paramPayType) {
        double returnValue = 0;

        if (!GlobalMemberValues.isStrEmpty(paramPayType)) {
            String tempQuery = " select sum(paidamount) from bill_list_paid where " +
                    " salescode = '" + paramSalesCode + "' and paytype = '" + paramPayType + "'";
            returnValue = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
        }
        GlobalMemberValues.logWrite("billlistjjjlog", "paidamount : " + returnValue + "\n");

        return returnValue;
    }

    public static double getBillAmountByHoldCode(String paramHoldcode) {
        double returnValue = 0;

        if (!GlobalMemberValues.isStrEmpty(paramHoldcode)) {
            String tempQuery = " select sum(billamount) from bill_list where " +
                    " holdcode = '" + paramHoldcode + "' ";
            returnValue = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
        }
        GlobalMemberValues.logWrite("billlistjjjlog", "billamount : " + returnValue + "\n");

        return returnValue;
    }

    public static boolean isShowPagerNumberOnExchangeReceipt() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select pagernumbershowonpickuporder from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }
    public static boolean isShowOrderNumberOnExchangeReceipt() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select ordernumbershowonpickuporder from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isCardStatusSaveUse() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select cardstatususe from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    // 카드 status 관련 ----------------------------------------------------------------------------------
    public static String getMenuItemsInCart() {
        String returnValue = "";

        int cnt = 0;
        String ins_item_all = "";
        String ins_item_list = "";
        for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
            String item_itemName = GlobalMemberValues.getDBTextAfterChecked(tempSaleCart.mSvcName, 0);
            String item_optionTxt = tempSaleCart.optionTxt;
            String item_addtionalTxt1 = tempSaleCart.additionalTxt1;
            String item_addtionalTxt2 = tempSaleCart.additionalTxt2;

            String item_all = item_itemName;
            if (!GlobalMemberValues.isStrEmpty(item_optionTxt)) {
                item_all += "@n@[MODIFIER]@n@" + item_optionTxt;
            }
            if (!GlobalMemberValues.isStrEmpty(item_addtionalTxt1)) {
                item_all += "@n@[ADDITIONAL]@n@" + item_addtionalTxt1;
            }
            if (!GlobalMemberValues.isStrEmpty(item_addtionalTxt2)) {
                item_all += "@n@[ADDITIONAL2]@n@" + item_addtionalTxt2;
            }

            if (cnt > 0) {
                ins_item_all += "@n@@n@@n@";
            } else {
                ins_item_list = item_itemName;
            }
            ins_item_all += item_all;

            cnt++;
        }

        if (!GlobalMemberValues.isStrEmpty(ins_item_all)) {
            returnValue = ins_item_all + "-WANHAYE-" + ins_item_list;
        }

        return returnValue;
    }
    // -------------------------------------------------------------------------------------------------

    public static boolean isPossibleManualQty() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select qtyinsyn from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isViewCustomerNumbers() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select customernumberviewyn from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static ArrayList<String> getPaidListBySaleCode(String paramSalesCode) {
        ArrayList<String> returnArrayList = null;

        if (!GlobalMemberValues.isStrEmpty(paramSalesCode)) {
            returnArrayList = new ArrayList<String>();

            String get_idx = "";
            String get_paidamount = "";
            String get_changeamount = "";
            String get_paytype = "";
            String get_cardsalesidx = "";
            String get_cardLastFourDigitNumbers = "";
            // 07202023
            String get_billidx = "";

            String returnStr = "";

            String strQuery = " select A.idx, A.paidamount, A.changeamount, A.paytype, A.cardsalesidx, A.billidx from bill_list_paid A " +
                    " where " +
                    " A.salesCode = '" + paramSalesCode + "' " +
                    " order by A.paytype asc ";
            ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (resultSet.next()) {
                    get_idx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                    get_paidamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                    get_changeamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                    get_paytype = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);
                    get_cardsalesidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 4);
                    // 07202023
                    get_billidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 5);

                    if (GlobalMemberValues.isStrEmpty(get_paidamount)) {
                        get_paidamount = "0";
                    }
                    if (GlobalMemberValues.isStrEmpty(get_changeamount)) {
                        get_changeamount = "0";
                    }

                    get_cardLastFourDigitNumbers = MssqlDatabase.getResultSetValueToString(
                            " select cardLastFourDigitNumbers from salon_sales_card where idx = '" + get_cardsalesidx + "' "
                    );

                    returnStr = get_idx +
                            "-JJJ-" + GlobalMemberValues.getDoubleAtString(get_paidamount) +
                            "-JJJ-" + GlobalMemberValues.getDoubleAtString(get_changeamount) +
                            "-JJJ-" + get_paytype +
                            "-JJJ-" + get_cardLastFourDigitNumbers +
                            // 07202023
                            "-JJJ-" + get_billidx;
                    returnArrayList.add(returnStr);
                }
                resultSet.close();
            } catch (Exception e) {
            }
        }

        return returnArrayList;
    }

    public static ArrayList<String> getTipListBySaleCode(String paramSalesCode) {
        ArrayList<String> returnArrayList = null;

        if (!GlobalMemberValues.isStrEmpty(paramSalesCode)) {
            returnArrayList = new ArrayList<String>();

            String get_idx = "";
            String get_tipamount = "";
            String get_paytype = "";
            String get_cardsalesidx = "";
            String get_cardLastFourDigitNumbers = "";

            String returnStr = "";

            String strQuery = " select A.idx, A.tipamount, A.paytype, A.cardsalesidx from salon_sales_tip_split A " +
                    " where " +
                    " A.salesCode = '" + paramSalesCode + "' " +
                    " order by A.paytype asc ";
            ResultSet resultSet = MssqlDatabase.getResultSetValue(strQuery);
            try {
                while (resultSet.next()) {
                    get_idx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                    get_tipamount = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                    get_paytype = GlobalMemberValues.resultDB_checkNull_string(resultSet, 2);
                    get_cardsalesidx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 3);

                    if (GlobalMemberValues.isStrEmpty(get_tipamount)) {
                        get_tipamount = "0";
                    }

                    get_cardLastFourDigitNumbers = MssqlDatabase.getResultSetValueToString(
                            " select cardLastFourDigitNumbers from salon_sales_card where idx = '" + get_cardsalesidx + "' "
                    );

                    returnStr = get_idx +
                            "-JJJ-" + GlobalMemberValues.getDoubleAtString(get_tipamount) +
                            "-JJJ-" + get_paytype +
                            "-JJJ-" + get_cardLastFourDigitNumbers;
                    returnArrayList.add(returnStr);
                }
                resultSet.close();
            } catch (Exception e) {
            }
        }

        return returnArrayList;
    }

    public static boolean isUseFadeInOut() {
        return false;
    }

    public static String getNowTableName() {
        String returnValue = "";
        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            String tableIdx = TableSaleMain.mTableIdxArrList.get(0).toString();
            if (!GlobalMemberValues.isStrEmpty(tableIdx)) {
                tableIdx = tableIdx.replace("T","");
                returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select tablename from salon_store_restaurant_table where idx = '" + tableIdx + "' ");
            }
        }

        return returnValue;
    }

    public static ArrayList<String> getSpecialRequest() {
        ArrayList<String> returnArrayList = null;
        returnArrayList = new ArrayList<String>();

        String get_name = "";
        String tempSql = " select name from salon_storespecialrequest " +
                " where useyn = 'Y' " +
                " and delyn = 'N' " +
                " order by idx asc ";
        //" order by sortnum asc ";
        GlobalMemberValues.logWrite("specialrequestlog", "tempSql : " + tempSql + "\n");
        Cursor tempCursor = MainActivity.mDbInit.dbExecuteRead(tempSql);
        while (tempCursor.moveToNext()) {
            get_name = GlobalMemberValues.getDBTextAfterChecked(tempCursor.getString(0), 1);
            returnArrayList.add(get_name);
        }

        return returnArrayList;
    }

    public static boolean isCashInOutPossible() {
        boolean returnValue = false;
        String temp_stationcode = GlobalMemberValues.getStationCode();
        String tempValue = "";
        if (temp_stationcode.isEmpty()){
            tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select cashinoutyn from salon_storestationinfo"
            );
        }else {
            tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select cashinoutyn from salon_storestationinfo where stcode = '" + temp_stationcode + "'"
            );
        }

        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isPaymentPossible() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select paymentyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean isPaymentPossibleByType(String paramPaymentType) {
        boolean returnValue = false;

        if (GlobalMemberValues.isStrEmpty(paramPaymentType)) {
            returnValue = false;
        } else {
            String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select paymentyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
            );
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "Y";
            }
            if (tempValue.equals("Y")) {
                String tempValue2 = MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select paymenttype from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempValue2)) {
                    tempValue2 = "cash,creditcard,giftcard,point,check";
                }
                if (tempValue2.contains(paramPaymentType)) {
                    returnValue = true;
                } else {
                    returnValue = false;
                }
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }
    // boolean 에서 String 으로 변경함. jihun 210826
    public static String isPaymentPossibleByTypeToString() {
//        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select paymentyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            String tempValue2 = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select paymenttype from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "
            );
            if (GlobalMemberValues.isStrEmpty(tempValue2)) {
                tempValue2 = "cash,creditcard,giftcard,point,check";
            }
            return tempValue2;
//                if (tempValue2.contains(paramPaymentType)) {
//                    returnValue = true;
//                } else {
//                    returnValue = false;
//                }
        } else {
            tempValue = "";
        }

        return tempValue;
    }

    // 직원코드 사용여부
    public static boolean isServerCodeUse() {
        boolean returnValue = false;

        if (MainActivity.mDbInit != null) {
            String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select servercodeuse from salon_storestationsettings_system"
            );
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "Y";
            }
            if (tempValue.equals("Y")) {
                returnValue = true;
            }
        }

        return returnValue;
    }


    public static boolean isServerPasswordUse() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select serverpassworduse from salon_storestationsettings_system"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean deleteCartLastItemForCommonGratuityUse() {
        boolean is_deleteCommonGratuity = false;
        // common gratuity 를 사용하는 경우 =======================================================================================
        String tempGratuityUseYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select gratuityuseyn from salon_storegeneral "
        );
        if (GlobalMemberValues.isStrEmpty(tempGratuityUseYN)) {
            tempGratuityUseYN = "N";
        }
        if (tempGratuityUseYN == "Y" || tempGratuityUseYN.equals("Y")) {
            double tempGratuityvalue = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select gratuityvalue from salon_storegeneral "
            ));

            if (tempGratuityvalue > 0) {
                // 기준인원을 충족하는지 확인한다.
                // 해당 테이블의 현재 인원
                int tempCustomerCnt = TableSaleMain.getTablePeopleCntByHoldCode(MainMiddleService.mHoldCode);
                int tempGratuitycustomercount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select gratuitycustomercount from salon_storegeneral "
                ));

                // 장바구니에 담기전에 먼저 common gratuity 부분을 삭제한다.
                if (tempCustomerCnt >= tempGratuitycustomercount &&
                        MainMiddleService.mGeneralArrayList != null && MainMiddleService.mGeneralArrayList.size() > 1) {
                    for (int i = 0; i < MainMiddleService.mGeneralArrayList.size(); i++) {
                        // int delIndex = MainMiddleService.mGeneralArrayList.size() - 1;
                        String tempItemName = MainMiddleService.mGeneralArrayList.get(i).mSvcName;
                        if (tempItemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                            is_deleteCommonGratuity = true;
                            MainMiddleService.deleteItem((i), true, false, "");
                        }
                    }

                }
            }
        }
        // ====================================================================================================================
        return is_deleteCommonGratuity;
    }

    public static void addCartLastItemForCommonGratuityUse() {
        // common gratuity 를 사용하는 경우 =======================================================================================
        String tempGratuityUseYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select gratuityuseyn from salon_storegeneral "
        );
        if (GlobalMemberValues.isStrEmpty(tempGratuityUseYN)) {
            tempGratuityUseYN = "N";
        }
        if (tempGratuityUseYN == "Y" || tempGratuityUseYN.equals("Y")) {
            String tempGratuitytype = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select gratuitytype from salon_storegeneral "
            );
            if (GlobalMemberValues.isStrEmpty(tempGratuitytype)) {
                tempGratuitytype = "%";
            }
            double tempGratuityvalue = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select gratuityvalue from salon_storegeneral "
            ));

            if (tempGratuityvalue > 0) {
                if (GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)) {
                    String paramTableidx = "";
                    if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
                        paramTableidx = TableSaleMain.mTableIdxArrList.get(0);
                    }
                    MainMiddleService.mHoldCode = TableSaleMain.getHoldCodeByTableidx(paramTableidx, "1");
                }


                // 기준인원을 충족하는지 확인한다.
                // 해당 테이블의 현재 인원
                GlobalMemberValues.logWrite("customercntjjj", "MainMiddleService.mHoldCode : " + MainMiddleService.mHoldCode + "\n");
                int tempCustomerCnt = TableSaleMain.getTablePeopleCntByHoldCode(MainMiddleService.mHoldCode);
                int tempGratuitycustomercount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select gratuitycustomercount from salon_storegeneral "
                ));

                GlobalMemberValues.logWrite("customercntjjj", "customer count : " + tempCustomerCnt + "\n");

                if (tempCustomerCnt >= tempGratuitycustomercount) {
                    // 장바구니에 common gratuity 가 담겨져 있지 않는 경우에만 아래 실행
                    int tempCnt = 0;
                    for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
                        String tempItemName = tempSaleCart.mSvcName;
                        if (tempItemName.equals(GlobalMemberValues.mCommonGratuityName)) {
                            tempCnt++;
                        }
                    }
//                tempCnt = GlobalMemberValues.getIntAtString(
//                        MssqlDatabase.executeTransactionByQuery(
//                                " select count(*) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "'" +
//                                        " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' "
//                        )
//                );
                    GlobalMemberValues.logWrite("commongratuitylogjjj", "tempCnt : " + tempCnt + "\n");

                    if (tempCnt > 0) {
                        return;
                    }

                    String tempEmpIdx = "";
                    String tempEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        tempEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                        tempEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }

                    String tempCustomerId = "";
                    String tempCustomerName = "";
                    String tempCustomerPhone = "";
                    if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                        tempCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                        tempCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                        tempCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                    }

                    String tempSqlQuery = "";
                    String tempTotalValue = "";
                    if (GlobalMemberValues.getCommonGratuityType().equals("AT")) {
                        tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString();

                        if (GlobalMemberValues.getDoubleAtString(tempTotalValue) == 0) {
                            tempSqlQuery = " select sum(sTotalAmount) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' ";

                            tempTotalValue = MssqlDatabase.getResultSetValueToString(tempSqlQuery) + "";
                        }

                        GlobalMemberValues.logWrite("gratuitylogjjj", "여기..1 : " + tempTotalValue + "\n");
                    } else {
                        tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString();

                        if (GlobalMemberValues.getDoubleAtString(tempTotalValue) == 0) {
                            tempSqlQuery = " select sum(sPriceAmount) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' ";
                            tempTotalValue = MssqlDatabase.getResultSetValueToString(tempSqlQuery) + "";
                        }

                        GlobalMemberValues.logWrite("gratuitylogjjj", "여기..2 : " + tempTotalValue + "\n");
                    }

                    String gratuity_val = "";
                    if (tempGratuitytype.equals("$")) {
                        gratuity_val = tempGratuityvalue + "";
                    } else {
                        gratuity_val = GlobalMemberValues.getDoubleAtString(tempTotalValue) * (tempGratuityvalue * 0.01) + "";
                    }
                    gratuity_val = GlobalMemberValues.getStringFormatNumber(gratuity_val, "2");

                    String paramsString[] = {
                            "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                            "0", "0",
                            GlobalMemberValues.mCommonGratuityName, "", "",
                            gratuity_val, gratuity_val, "", "",
                            "0", "", "0",
                            "", "N",
                            tempCustomerId, tempCustomerName, tempCustomerPhone, "0", tempEmpIdx, tempEmpName, "N", GlobalMemberValues.mCommonGratuityName};

                    String maxIdxAfterDbInsert = "";
                    //maxIdxAfterDbInsert = insertTempSaleCartForQuickSale(paramsString);

                    MainMiddleService.mQuickSaleYN = "Y";

                    // 09262023
                    if (TableSaleMain.isAfterMerge || MainMiddleService.mGeneralArrayList.size() > 0){
                        GlobalMemberValues.logWrite("gratuitylogjjj55555", "여기.1 : " + "\n");
                        MainMiddleService.insertTempSaleCart(paramsString);
                    }
                }
            }
        }
        // ====================================================================================================================
    }

    public static String addCartLastItemForCommonGratuityUse_intable(String str_holecode, String str_table_menu_total) {
        String str_return = "";
        // common gratuity 를 사용하는 경우 =======================================================================================
        String tempGratuityUseYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select gratuityuseyn from salon_storegeneral "
        );
        if (GlobalMemberValues.isStrEmpty(tempGratuityUseYN)) {
            tempGratuityUseYN = "N";
        }
        if (tempGratuityUseYN == "Y" || tempGratuityUseYN.equals("Y")) {
            String tempGratuitytype = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select gratuitytype from salon_storegeneral "
            );
            if (GlobalMemberValues.isStrEmpty(tempGratuitytype)) {
                tempGratuitytype = "%";
            }
            double tempGratuityvalue = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select gratuityvalue from salon_storegeneral "
            ));

            if (tempGratuityvalue > 0) {
                // 기준인원을 충족하는지 확인한다.
                // 해당 테이블의 현재 인원
                int tempCustomerCnt = TableSaleMain.getTablePeopleCntByHoldCode(str_holecode);
                int tempGratuitycustomercount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select gratuitycustomercount from salon_storegeneral "
                ));

                GlobalMemberValues.logWrite("customercntjjj", "customer count : " + tempCustomerCnt + "\n");

                if (tempCustomerCnt >= tempGratuitycustomercount) {

                    String tempEmpIdx = "";
                    String tempEmpName = "";
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        tempEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                        tempEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                    }

                    String tempCustomerId = "";
                    String tempCustomerName = "";
                    String tempCustomerPhone = "";
                    if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                        tempCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                        tempCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                        tempCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                    }

                    String tempTotalValue = str_table_menu_total;

                    String gratuity_val = "";
                    if (tempGratuitytype.equals("$")) {
                        gratuity_val = tempGratuityvalue + "";
                    } else {
                        gratuity_val = GlobalMemberValues.getDoubleAtString(tempTotalValue) * (tempGratuityvalue * 0.01) + "";
                    }
                    gratuity_val = GlobalMemberValues.getStringFormatNumber(gratuity_val, "2");

                    String paramsString[] = {
                            "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                            "0", "0",
                            GlobalMemberValues.mCommonGratuityName, "", "",
                            gratuity_val, gratuity_val, "", "",
                            "0", "", "0",
                            "", "N",
                            tempCustomerId, tempCustomerName, tempCustomerPhone, "0", tempEmpIdx, tempEmpName, "N", GlobalMemberValues.mCommonGratuityName};

                    String item_itemname_optionadd = "-WANHAYE-" +
                            GlobalMemberValues.mCommonGratuityName +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-" +
                            "-ANNIETTASU-";
                    String itemDetailText = item_itemname_optionadd + "-JJJ-" + "1" +
                            "-JJJ-" + gratuity_val + "-JJJ-" + gratuity_val + "-JJJ-" + "0.0" + "-JJJ-" + gratuity_val;

                    return str_return = itemDetailText;

                }
            }
        }
        return str_return;
        // ====================================================================================================================
    }


    public static String getValueOnReceiptSetting(String paramColumn, String paramPrintingtype) {
        String returnValue = "Y";
        returnValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select " + paramColumn + " from salon_storestationsettings_system_receipt " +
                        " where receipt_type = '" + paramPrintingtype + "' "), 1);
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "Y";
        }
        return returnValue;
    }

    public static String getValueOnReceiptSetting2(String paramColumn, String paramPrintingtype) {
        String returnValue = "T";
        returnValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select " + paramColumn + " from salon_storestationsettings_system_receipt " +
                        " where receipt_type = '" + paramPrintingtype + "' "), 1);
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "T";
        }
        return returnValue;
    }

    public static void setValueOnReceiptSetting(String paramColumn, String paramValue, String paramPrintingtype) {
        String returnResult = "";
        Vector<String> vec = new Vector<String>();

        String strQuery = "select count(*) from salon_storestationsettings_system_receipt where receipt_type = "+ " '" + paramPrintingtype + "' ";
        String tempCnt = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
        if (GlobalMemberValues.getIntAtString(tempCnt) == 0) {
            strQuery = "insert into salon_storestationsettings_system_receipt (" +
                    " scode, sidx, stcode, receipt_type " +
                    " ) values (" +
                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + paramPrintingtype + "' " +
                    " ) ";
            vec.addElement(strQuery);
        }

        strQuery = " update salon_storestationsettings_system_receipt set " +
                paramColumn + " = '" + paramValue + "' where receipt_type = "+ " '" + paramPrintingtype + "' ";
        vec.addElement(strQuery);
        for (String tempQuery : vec) {
            GlobalMemberValues.logWrite("setValueOnReceiptSettinglog", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(vec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
        } else {
            //GlobalMemberValues.logWrite("setValueOnReceiptSettinglog", "성공" + "\n");
        }
    }

    public static boolean getValueOnReceiptMerCusShowYN(String paramColumn, String strMerCus){
        boolean b_return = false;

        String paramPrintingtype = "";
        if (strMerCus.equals("customer")){
            paramPrintingtype = "1";
        } else {
            paramPrintingtype = "2";
        }

        String returnValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select " + paramColumn + " from salon_storestationsettings_system_receipt " +
                        " where receipt_type = '" + paramPrintingtype + "' "), 1);
        if (!returnValue.isEmpty()){
            switch (returnValue){
                case "Y" :
                    b_return = true;
                    break;
                case "N" :
                    b_return = false;
                    break;
                case "T" :
                    b_return = true;
                    break;
                case "B" :
                    b_return = false;
                    break;
            }
        }

        return b_return;
    }

    public static void setCloudAddr() {
        GlobalMemberValues.CLOUD_SERVER_URL_BASIC = "https://" + GlobalMemberValues.CLOUD_HOST + ".2go2go.com/";
        GlobalMemberValues.CLOUD_SERVER_URL = "https://" + GlobalMemberValues.CLOUD_HOST + ".2go2go.com/";
        GlobalMemberValues.CLOUD_SERVER_URL_FORHTTPS = "https://" + GlobalMemberValues.CLOUD_HOST + ".2go2go.com/";

        // Cloud URL
        GlobalMemberValues.CLOUD_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "posweb.asp?stcode=";
        // Cloud URL
        GlobalMemberValues.CLOUD_WEB_URL_NOTOP = GlobalMemberValues.CLOUD_SERVER_URL + "login_result_pos.asp?stcode=";
        // Cloud Logout URL
        GlobalMemberValues.CLOUD_WEB_LOGOUT_URL = GlobalMemberValues.CLOUD_SERVER_URL + "posweb_logout.asp?stcode=";
        // Reservation URL
        GlobalMemberValues.RESERVATION_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "reservation/index.asp?";
        // Clock In-Out URL
        // GlobalMemberValues.CLOCKINOUT_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist.asp?";
        GlobalMemberValues.CLOCKINOUT_WEB_URL = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist_test.asp?";
        // Clock In-Out URL2
        GlobalMemberValues.CLOCKINOUT_WEB_URL2 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert/clockinout_ok.asp?";
        // Clock In-Out URL3
        GlobalMemberValues.CLOCKINOUT_WEB_URL3 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert_edit/edit_ok.asp?";
        // Clock In-Out URL4
        GlobalMemberValues.CLOCKINOUT_WEB_URL4 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/insert_edit/delete_ok.asp?";
        // Clock In-Out URL5
        // GlobalMemberValues.CLOCKINOUT_WEB_URL5 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist.asp?";
        GlobalMemberValues.CLOCKINOUT_WEB_URL5 = GlobalMemberValues.CLOUD_SERVER_URL + "clockinout/contents/sublist_test.asp?";
        // API - 연결문자열
        GlobalMemberValues.API_WEB = GlobalMemberValues.CLOUD_SERVER_URL + "API/";
        // API - API 연동 웹페이지
        GlobalMemberValues.API_WEB_URL = GlobalMemberValues.API_WEB + "API_DOWNLOAD_ForAndroid.asp";
        // API - 온라인 주문데이터 리스트
        GlobalMemberValues.API_WEBORDER_URL2 = GlobalMemberValues.API_WEB + "API_OrdersList_ForAndroid.asp";
        // API - 온라인 주문데이터
        GlobalMemberValues.API_WEBORDER_URL = GlobalMemberValues.API_WEB + "API_Orders_ForAndroid.asp";
    }

    public static ArrayList<String> getTipPersents(){
        ArrayList<String> ret_str = new ArrayList<String>();
        String str_tip1 = "";
        String str_tip2 = "";
        String str_tip3 = "";
        String str_tip4 = "";

        String strQuery = "select tipselect1, tipselect2, tipselect3, tipselect4 " +
                " from salon_storestationsettings_paymentgateway";
        Cursor tempSaleCartCursor;
        tempSaleCartCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        int tempSaleCartCount = 0;
        while (tempSaleCartCursor.moveToNext()) {
            str_tip1 = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(0), 1);
            str_tip2 = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(1), 1);
            str_tip3 = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(2), 1);
            str_tip4 = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(3), 1);
        }
        if (!str_tip1.isEmpty()) {
            ret_str.add(str_tip1);
        }
        if (!str_tip2.isEmpty()) {
            ret_str.add(str_tip2);
        }
        if (!str_tip3.isEmpty()) {
            ret_str.add(str_tip3);
        }
        if (!str_tip4.isEmpty()) {
            ret_str.add(str_tip4);
        }
        return ret_str;
    }

    public static boolean isPickupSelectPopup() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select pickuptypepopupuseyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCategoryPrinting() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select printingcategoryyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isCashoutreportPrintingByItemsNum(String itemnum) {
        boolean returnValue = false;
        if (!GlobalMemberValues.isStrEmpty(itemnum)) {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select cashoutreportitems from salon_storegeneral"), 1);
            if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                if (tempValue.indexOf(itemnum) > -1) {
                    returnValue = true;
                }
            } else {
                returnValue = false;
            }
        }
        return returnValue;
    }

    public static boolean isCustomer_info_here_yn(){
        boolean temp_b = false;
        String temp_yn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select customer_info_here_yn from salon_storestationsettings_system "
        );
        if (temp_yn.equals("Y")){
            temp_b = true;
        } else {
            temp_b = false;
        }

        return temp_b;
    }

    public static boolean isCustomer_info_togo_yn(){
        boolean temp_b = false;
        String temp_yn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select customer_info_togo_yn from salon_storestationsettings_system "
        );
        if (temp_yn.equals("Y")){
            temp_b = true;
        } else {
            temp_b = false;
        }

        return temp_b;
    }

    public static boolean isCustomer_info_delivery_yn(){
        boolean temp_b = false;
        String temp_yn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select customer_info_delivery_yn from salon_storestationsettings_system "
        );
        if (temp_yn.equals("Y")){
            temp_b = true;
        } else {
            temp_b = false;
        }

        return temp_b;
    }

    public static boolean isUseDividerLine(){
        boolean temp_b = false;
        String temp_yn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select divideruseyn from salon_storegeneral "
        );
        if (temp_yn.equals("Y")){
            temp_b = true;
        } else {
            temp_b = false;
        }
        return temp_b;
    }

    public static boolean isRealTable(String paramTableIdx) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramTableIdx)) {
            paramTableIdx = GlobalMemberValues.getReplaceText(paramTableIdx, "T", "");

            String tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select quicksaleyn from salon_store_restaurant_table where idx = '" + paramTableIdx + "' "
            );

            if (GlobalMemberValues.isStrEmpty(tempQuickSaleyn)) {
                tempQuickSaleyn = "N";
            }

            if (tempQuickSaleyn.equals("N")) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    public static boolean isRealTable_tablename(String paramTableName) {
        boolean returnValue = false;

        if (!GlobalMemberValues.isStrEmpty(paramTableName)) {

            String tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select quicksaleyn from salon_store_restaurant_table where tablename = '" + paramTableName + "' "
            );

            if (GlobalMemberValues.isStrEmpty(tempQuickSaleyn)) {
                tempQuickSaleyn = "N";
            }

            if (tempQuickSaleyn.equals("N")) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }


    public static JSONArray labelPrint_menuSplit(JSONObject jsonObject){

        JSONArray jsonArray_split_object = new JSONArray();
        JSONArray jsonArray_saleitemlist = new JSONArray();
        JSONArray jsonArray_split_object_return = new JSONArray();
        try {
            if (jsonObject.getJSONArray("saleitemlist") != null) {
                jsonArray_saleitemlist = jsonObject.getJSONArray("saleitemlist");
                JSONArray temp_array = new JSONArray();
                for (int l = 0 ; l < jsonArray_saleitemlist.length() ; l++){
                    String temp_str = "";
                    if (jsonArray_saleitemlist.getJSONObject(l).toString().contains("itemsvcidx")){
                        temp_str = jsonArray_saleitemlist.getJSONObject(l).getString("itemsvcidx");
                    } else if (jsonArray_saleitemlist.getJSONObject(l).toString().contains("itemcartidx")){
                        temp_str = jsonArray_saleitemlist.getJSONObject(l).getString("itemcartidx");
                    } else {
                        temp_str = jsonArray_saleitemlist.getJSONObject(l).getString("itemidx");
                    }

                    String getPrinterNum = "";
                    if (temp_str == "0" || temp_str.equals("0")) {
                        String tempCateIdx = GlobalMemberValues.getDataInJsonData(jsonArray_saleitemlist.getJSONObject(l), "itemcategoryidx");
                        temp_str = tempCateIdx;
                        getPrinterNum = getLabelPrinterNumForQuickSale(tempCateIdx);
                    }

                    if (isLabelPrinting(temp_str) || !getPrinterNum.equals("")){
                        // labelPrinting 가 Y 일 경우에만 Label Printing 함..
                        String temp_labelprintedyn = "";
                        if (jsonArray_saleitemlist.getJSONObject(l).toString().contains("labelprintedyn")){
                            temp_labelprintedyn = jsonArray_saleitemlist.getJSONObject(l).getString("labelprintedyn");
                        }

                        if (temp_labelprintedyn.equals("Y")){

                        } else {
                            if (jsonArray_saleitemlist.getJSONObject(l).getString("itemname").equals(GlobalMemberValues.mCommonGratuityName)){

                            } else {
                                jsonArray_saleitemlist.getJSONObject(l).put("labelprintedyn", "Y");
                                temp_array.put(jsonArray_saleitemlist.get(l));
                            }
                        }

                    }

                }
                jsonArray_saleitemlist = temp_array;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray_saleitemlist.length() != 0) {
            int cnt = jsonArray_saleitemlist.length();
            int item_number = 0;
            for (int i = 0; i < cnt; i++) {
                try {
                    JSONObject temp_jsonObject = new JSONObject(jsonObject.toString());
                    temp_jsonObject.remove("saleitemlist");

                    JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);

                    String item_cnt = dic_item.getString("itemqty");
                    for (int z = 0; z < Integer.parseInt(item_cnt); z++){
                        JSONObject temp_jsonObject_newitem = new JSONObject(jsonObject.toString());
                        temp_jsonObject_newitem.remove("saleitemlist");
                        JSONArray temp_jsonArray = new JSONArray();
                        temp_jsonArray.put(dic_item);
                        temp_jsonObject_newitem.put("saleitemlist",temp_jsonArray);
                        String itemcnt = (item_number + 1) + "";
                        temp_jsonObject_newitem.put("item_num_forLabel", itemcnt);
                        temp_jsonObject_newitem.put("item_total_num_forLabel", cnt + "");

                        if (jsonObject.toString().contains("deliverydate")){
                            temp_jsonObject_newitem.put("deliverydate",jsonObject.get("deliverydate"));
                        }
                        //str_itemqty = dic_item.getString("itemqty");

                        jsonArray_split_object.put(temp_jsonObject_newitem);
                        item_number++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < jsonArray_split_object.length(); j++){
                try {
                    JSONObject temp = jsonArray_split_object.getJSONObject(j);
                    temp.put("item_total_num_forLabel", jsonArray_split_object.length() + "");
                    jsonArray_split_object_return.put(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        return jsonArray_split_object_return;
    }

    public static JSONArray labelPrint_printNumber(JSONArray jsonArray_saleitemlist, String printerNumber){
        JSONArray jsonArray_split_object_return = new JSONArray();
        if (jsonArray_saleitemlist.length() != 0) {
            int cnt = jsonArray_saleitemlist.length();

            for (int i = 0; i < cnt; i++) {
                try {
                    JSONObject dic_item = jsonArray_saleitemlist.getJSONObject(i);

                    JSONArray saleitemlist = dic_item.getJSONArray("saleitemlist");
                    JSONObject itemJsonobject = saleitemlist.getJSONObject(0);

                    String itemsvcidx = "";//itemJsonobject.getString("itemcartidx");

                    if (itemJsonobject.toString().contains("itemsvcidx")){
                        itemsvcidx = itemJsonobject.getString("itemsvcidx");
                    } else if (itemJsonobject.toString().contains("itemcartidx")){
                        itemsvcidx = itemJsonobject.getString("itemcartidx");
                    } else {
                        itemsvcidx = itemJsonobject.getString("itemidx");
                    }

                    String getPrinterNum = "";
                    if (itemsvcidx == "0" || itemsvcidx.equals("0")) {
                        String tempCateIdx = GlobalMemberValues.getDataInJsonData(itemJsonobject, "itemcategoryidx");
                        getPrinterNum = getLabelPrinterNumForQuickSale(tempCateIdx);
                    } else {
                        getPrinterNum = getLabelPrinterNum(itemsvcidx);
                    }

//                    String getPrinterNum = getLabelPrinterNum(itemsvcidx);
                    if (printerNumber.equals(getPrinterNum)){
                        jsonArray_split_object_return.put(dic_item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray_split_object_return;
    }

    public static boolean isUseLabelPrinter() {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select labelprinteruse from salon_storegeneral"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static String getMenuNameForLabel(String paramSvidx) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramSvidx)) {
            returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select nameforlabel from salon_storeservice_sub where idx = '" + paramSvidx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(returnValue)) {
//                String tempValue2 = MainActivity.mDbInit.dbExecuteReadReturnString(
//                        " select servicenamefull from salon_storeservice_sub where idx = '" + paramSvidx + "' "
//                );
                String tempValue2 = "";
                Cursor serviceCursor = MainActivity.mDbInit.dbExecuteRead(" select servicename, servicename2, servicename3 from salon_storeservice_sub where idx = '" + paramSvidx + "' ");
                if (serviceCursor.getCount() > 0 && serviceCursor.moveToFirst()) {
                    String tempServiceName = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(0), 1);
                    String tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(1), 1);
                    String tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(2), 1);
                    tempValue2 = tempServiceName + " " + tempServiceName2 + " " + tempServiceName3;
                }
                serviceCursor.close();


                if (!GlobalMemberValues.isStrEmpty(tempValue2)) {
                    returnValue = tempValue2.substring(0, 20).toString();
                }
            }
        }

        return returnValue;
    }

    public static String getCustomerInfoByTableIdx() {
        String returnValue = "";

        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            String paramTableidx = TableSaleMain.mTableIdxArrList.get(0);
            if (!GlobalMemberValues.isStrEmpty(paramTableidx)) {
                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx(paramTableidx, TableSaleMain.mSubTableNum);
                String customerName = MssqlDatabase.getResultSetValueToString(
                        " select customerName from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );
                String customerPhone = MssqlDatabase.getResultSetValueToString(
                        " select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + tempHoldCode + "' "
                );

                returnValue = customerName + "-JJJ-" + customerPhone + "-JJJ-" + "END";
            }
        }

        return returnValue;
    }

    public static boolean isToGoSale() {
        boolean returnValue = false;

        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            GlobalMemberValues.logWrite("jjjwhylogjjj", "여기...3" + "\n");

            GlobalMemberValues.logWrite("jjjwanhayejjj", "여기..." + "\n");
            GlobalMemberValues.logWrite("jjjwanhayejjj", "table idx : " + TableSaleMain.mTableIdxArrList.toString() + "\n");
            returnValue = false;
            String tempQuickSaleyn = "N";
            String tempTableIdx = "";
            if (TableSaleMain.mTableIdxArrList.size() > 1) {
                int tempi = 0;
                for (int i = 0; i < TableSaleMain.mTableIdxArrList.size(); i++) {
                    tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mTableIdxArrList.get(i), "T", "");
                    tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                    );
                    if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                        tempi++;
                    }
                }
                if (tempi > 0) {
                    returnValue = true;
                }
            } else {
                tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mTableIdxArrList.get(0), "T", "");
                tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                );
                if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                    returnValue = true;
                }
            }
        }

        return returnValue;
    }

    public static String getStationType() {
        String returnData = "";
        returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select sttype from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' "), 1);
        if (GlobalMemberValues.isStrEmpty(returnData)) {
            returnData = "R";
        }
        return returnData;
    }

    public static boolean isDynamicPrice(String paramServiceIdx) {
        boolean returnValue = false;

        String dynamicpriceyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select dynamicpriceyn from salon_storeservice_sub where idx = '" + paramServiceIdx + "' "
        );

        if (GlobalMemberValues.isStrEmpty(dynamicpriceyn)) {
            dynamicpriceyn = "N";
        }

        if (dynamicpriceyn == "Y" || dynamicpriceyn.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static boolean hasDividerLineAfter(String paramHoldcode, String paramSaleCartIdx) {
        boolean returnValue = false;

        String sqlQuery = " select count(*) from temp_salecart " +
                " where holdcode = '" + paramHoldcode + "'" +
                " and idx > " + paramSaleCartIdx +
                " and svcName like '%=====%' ";
        int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(sqlQuery));
        if (tempCnt > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    public static int countChar(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

    public static boolean isShowPriceOnMobile(String paramItemIdx) {
        boolean returnValue = false;

        String showynifzero_m = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select showynifzero_m from salon_storeservice_sub where idx = '" + paramItemIdx + "' "
        );

        if (GlobalMemberValues.isStrEmpty(showynifzero_m)) {
            showynifzero_m = "Y";
        }

        if (showynifzero_m == "Y" || showynifzero_m.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static String getIdxOfSameDataOnKitchenPrintingData(String paramSalesCode) {
        String returnValue = "";

        String strQuery = " select idx from salon_sales_kitchenprintingdata_json" +
                " where salescode = '" + paramSalesCode + "' and printedyn = 'N' ";
        returnValue = MssqlDatabase.getResultSetValueToString(strQuery);
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "";
        }
        return returnValue;
    }

    public static boolean isPrintingEmployeeInfoOnReceipt() {
        boolean returnValue = false;
        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString("select empinfoprintingyn from salon_storestationsettings_deviceprinter");
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    public static boolean isPrintingMenuListOnReceipt() {
        boolean returnValue = false;
        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString("select menulistprintingyn from salon_storestationsettings_deviceprinter");
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    public static boolean isLabelPrinting(String paramItemIdx) {
        boolean returnValue = false;

        String tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select labelprinteruse from salon_storegeneral"
        );
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }

        if (tempValue.equals("Y") || tempValue == "Y") {
            String labelprintyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select labelprintyn from salon_storeservice_sub where idx = '" + paramItemIdx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(labelprintyn)) {
                labelprintyn = "N";
            }
            if (labelprintyn == "Y" || labelprintyn.equals("Y")) {
                returnValue = true;
            }
        }

        return returnValue;
    }

    public static String getLabelPrinterNum(String paramItemIdx) {
        String returnValue = "";
        if (GlobalMemberValues.isLabelPrinting(paramItemIdx)) {
            String labelprintnum = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select labelprintnum from salon_storeservice_sub where idx = '" + paramItemIdx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(labelprintnum)) {
                labelprintnum = "";
            }

            returnValue = labelprintnum;
        }

        return returnValue;
    }

    public static void sendSMSMsgSend(Context paramContext, String paramOrderNum, String paramPhoneNum, String paramMsg) {
        String returnValue = "99";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(paramContext);
                return;
            } else {
                if (GlobalMemberValues.isStrEmpty(paramOrderNum)) {
                    GlobalMemberValues.displayDialog(paramContext, "SMS Message", "Invalid order#", "Close");
                    return;
                }
                if (GlobalMemberValues.isStrEmpty(paramPhoneNum)) {
                    GlobalMemberValues.displayDialog(paramContext, "SMS Message", "Enter phone#", "Close");
                    return;
                }
                if (GlobalMemberValues.isStrEmpty(paramMsg)) {
                    GlobalMemberValues.displayDialog(paramContext, "SMS Message", "Enter message", "Close");
                    return;
                }

                API_SMS_Send_Msg apicheckInstance = new API_SMS_Send_Msg(paramOrderNum, paramPhoneNum, paramMsg);
                apicheckInstance.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apicheckInstance.mFlag) {
                        returnValue = apicheckInstance.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    returnValue = "";
                }

                GlobalMemberValues.logWrite("API_SMS_Send_Msg_Log", "returnValue : " + returnValue + "\n");
            }
        } else {
            //GlobalMemberValues.openNetworkNotConnected();
        }

        if (returnValue.equals("00")) {
            GlobalMemberValues.displayDialog(paramContext, "SMS Message", "Successfully sent message", "Close");
        }
    }


    public static boolean isOpenmsgwhendeletemenu() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select openmsgwhendeletemenu_yn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isOtherpayreceiptprinting() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select otherpayprinting_yn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isTipprintingwhentogo() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tipprintingwhentogo_yn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isSignPadUse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select signpaduseyn from salon_storestationsettings_paymentgateway"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }

        GlobalMemberValues.logWrite("signpaddusejjjlog", "여기에..1 : " + returnValue + "\n");

        if (!returnValue) {
            GlobalMemberValues.logWrite("signpaddusejjjlog", "여기에..2 : " + GlobalMemberValues.isTipprintingwhentogo() + "\n");
            GlobalMemberValues.logWrite("signpaddusejjjlog", "TableSaleMain.mSelectedTablesArrList : " + TableSaleMain.mSelectedTablesArrList.toString() + "\n");
            if (!GlobalMemberValues.isTipprintingwhentogo()) {
                String tempQuickSaleyn = "N";
                String tempTableIdx = "";
                int tempi = 0;
                if (TableSaleMain.mSelectedTablesArrList.size() > 0) {
                    for (int i = 0; i < TableSaleMain.mSelectedTablesArrList.size(); i++) {
                        tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mSelectedTablesArrList.get(i), "T", "");
                        tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                        );
                        if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                            tempi++;
                        }
                    }
                } else {
                    if (TableSaleMain.mTableIdxArrList.size() > 0) {
                        for (int i = 0; i < TableSaleMain.mTableIdxArrList.size(); i++) {
                            tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mTableIdxArrList.get(i), "T", "");
                            tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                    " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                            );
                            if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                                tempi++;
                            }
                        }
                    }
                }

                if (tempi > 0) {
                    returnValue = true;
                }
            }
        }

        return returnValue;
    }


    public static String getSuggestionTipType() {
        String returnValue = "AT";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        returnValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select suggestiontiptype from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "AT";
        }
        return returnValue;
    }

    public static String getCommonGratuityType() {
        String returnValue = "AT";
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        returnValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select commongratuitytype from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "AT";
        }
        return returnValue;
    }


    public static boolean isBillPrintPopupOpen() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select billprintpopupyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static void saveTempSaleCartOrdered(String paramIdx) {
        int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(*) from temp_salecart_ordered where idx = '" + paramIdx + "' "
        ));
        if (tempCnt == 0) {
            MainActivity.mDbInit.dbExecuteWriteReturnValue(" insert into temp_salecart_ordered select * from temp_salecart where idx = '" + paramIdx + "' ");
        }
    }

    // 05.31.2022
    public static void saveTempSaleCartForRepay(Context paramContext, String paramSalesCode) {
        if (!GlobalMemberValues.isStrEmpty(paramSalesCode)) {
            String tempHoldCode = MssqlDatabase.getResultSetValueToString(" select holdcode from salon_sales where salescode = '" + paramSalesCode + "' ");
            String tempTableIdx = MssqlDatabase.getResultSetValueToString(" select tableidx from salon_sales_detail where salesCode = '" + paramSalesCode + "' ");
            String tempPeopleCnt = MssqlDatabase.getResultSetValueToString(" select tablepeoplecnt from salon_sales where salescode = '" + paramSalesCode + "' ");
            double tempPeopleCnt_dbl = GlobalMemberValues.getDoubleAtString(tempPeopleCnt);
            int tempPeopleCnt_int = (int)Math.round(tempPeopleCnt_dbl);
            GlobalMemberValues.logWrite("splitsqllog", "tempPeopleCnt0 : " + tempPeopleCnt_int + "\n");

            Vector<String> strInsertQueryVec = new Vector<String>();

            String tempStr = " holdcode, sidx, stcode, midx, svcIdx, svcName, svcFileName, svcFilePath, svcPositionNo, svcOrgPrice, svcSetMenuYN, " +
                    " sPrice, sTax, sQty, sPriceAmount, sTaxAmount, sTotalAmount, sCommission, sPoint, sCommissionAmount, sPointAmount, sSaleYN, " +
                    " customerId, customerName, customerPhoneNo, wdate, saveType, empIdx, empName, quickSaleYN, svcCategoryName, " +
                    " giftcardNumber,  giftcardSavePrice, selectedDcExtraPrice, selectedDcExtraType, selectedDcExtraAllEach, couponNumber, " +
                    " sCommissionRatioType, sCommissionRatio, sPointRatio, sPriceBalAmount, svcCategoryColor, taxExempt,reservationCode, " +
                    " optionTxt, optionprice,  additionalTxt1, additionalprice1,  additionalTxt2, additionalprice2, memoToKitchen, " +
                    " discountbuttonname, modifieridx, modifiercode, sPriceBalAmount_org, sTaxAmount_org, sTotalAmount_org, sCommissionAmount_org, sPointAmount_org, " +
                    " tableidx, billtag, mergednum, subtablenum, billnum, kitchenprintedyn, isCloudUpload, cardtryyn, dcextratype, dcextravalue, togodelitype, labelprintedyn, togotype, " +
                    " pastholdcode, billprintedyn," +
                    " billidx_byitemsplit, " +
                    // 03192024
                    " tordercode ";
            String tempSql = " insert into temp_salecart (" + tempStr + ") " +
                    " select " + tempStr + " from temp_salecart_ordered where holdcode = '" + tempHoldCode + "' ";
            strInsertQueryVec.addElement(tempSql);

            tempSql = " delete from temp_salecart_ordered where holdcode = '" + tempHoldCode + "' ";
            strInsertQueryVec.addElement(tempSql);


            // 05182023
            // 05172023
            // repay 일 경우 bill_list 의 paidyn 을 N 으로 처리
            tempSql = " update bill_list set paidyn = 'N', cartidxs = '' where holdcode = '" + tempHoldCode + "' ";
            strInsertQueryVec.addElement(tempSql);

            // 05182023
            tempSql = " delete from bill_list_paid where holdcode = '" + tempHoldCode + "' ";
            strInsertQueryVec.addElement(tempSql);



            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("repaymethodjjjlog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(paramContext, "", "Database Error (saveTempSaleCartForRepay)", "Close");
            } else {
                GlobalMemberValues.mTableIdx_byRepay = tempTableIdx;
                GlobalMemberValues.mHoldCode_byRepay = tempHoldCode;
                GlobalMemberValues.mPeopleCnt_byRepay = tempPeopleCnt_int;
//                GlobalMemberValues.logWrite("splitsqllog", "tempTableIdx1 : " + GlobalMemberValues.mTableIdx_byRepay + "\n");
//                GlobalMemberValues.logWrite("splitsqllog", "tempHoldCode1 : " + GlobalMemberValues.mHoldCode_byRepay + "\n");
//                GlobalMemberValues.logWrite("splitsqllog", "tempPeopleCnt1 : " + GlobalMemberValues.mPeopleCnt_byRepay + "\n");

                TableSaleMain.mTablePeopleCnt = 0;

                TableSaleMain.mSelectedTablesArrList.remove(tempTableIdx);
                TableSaleMain.mSelectedTablesArrList.add(tempTableIdx);

                TableSaleMain.setOrderStart(TableSaleMain.mSelectedTablesArrList, false, true);

                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    //jihun park sub display
                    PaxPresentation.setLogo();
                    MainActivity.updatePresentation();
                }
                GlobalMemberValues.mIsClickPayment = true;
//                // payment 프레임 보여주기
//                Payment payment = new Payment(MainActivity.mActivity, MainActivity.mContext, MainActivity.dataAtSqlite);
//                payment.setPaymentView();



                // 05182023 ----------------------------------------------------------------------------------------
                String tempSaleCartIdx, billidx_byitemsplit;
                String getCartIdxs = "";
                tempSql = " select idx, billidx_byitemsplit from temp_salecart where holdcode = '" + tempHoldCode + "' ";
                ResultSet resultSet = MssqlDatabase.getResultSetValue(tempSql);
                try {
                    while (resultSet.next()) {
                        tempSaleCartIdx = GlobalMemberValues.resultDB_checkNull_string(resultSet, 0);
                        billidx_byitemsplit = GlobalMemberValues.resultDB_checkNull_string(resultSet, 1);
                        // bill_list 에 cartidxs 가 저장된게 있는지여부
                        getCartIdxs = MssqlDatabase.getResultSetValueToString(
                                " select cartidxs from bill_list where idx = '" + billidx_byitemsplit + "' "
                        );
                        if (GlobalMemberValues.isStrEmpty(getCartIdxs)) {
                            tempSql = " update bill_list set cartidxs = '" + tempSaleCartIdx + "' where idx = '" + billidx_byitemsplit + "' ";
                        } else {
                            tempSaleCartIdx = getCartIdxs + "," + tempSaleCartIdx;
                            tempSql = " update bill_list set cartidxs = '" + tempSaleCartIdx + "' where idx = '" + billidx_byitemsplit + "' ";
                        }

                        GlobalMemberValues.logWrite("repaymethodjjjlog", "query : " + tempSql + "\n");

                        MainActivity.mDbInit.dbExecuteWriteReturnValue(tempSql);
                    }
                    resultSet.close();
                } catch (Exception e) {
                }
                // 05182023 ----------------------------------------------------------------------------------------



            }
        } else {
            GlobalMemberValues.displayDialog(paramContext, "", "Invalid Access", "Close");
        }
    }

    // 092022
    public static Vector<String> getVectorForVoid(String paramSalonSalesCardIdx, String paramBillPaidIdx, String paramBillIdx) {
        Vector<String> returnValueVec = null;

        returnValueVec = new Vector<String>();

        String strDeleteQuery = "";

//        // 092022 --------------------------------------------------------------------------------------
//        // void 한 금액만큼 salon_sales_detail 에서 차감
//
//        String strQuery = "";
//
//        String temp_salesCode = "";
//        String temp_holdcode = "";
//        String temp_ordernum = "";
//        String temp_paidamount = "";
//        String temp_paytype = "";
//
//        strQuery = "select salescode, holdcode, ordernum, paidamount, paytype from bill_list_paid where idx = '" + paramBillPaidIdx + "' ";
//        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(strQuery);
//        try {
//            while (tempSaleCartCursor.next()) {
//                temp_salesCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 0), 1);
//                temp_holdcode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 1), 1);
//                temp_ordernum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 2), 1);
//                temp_paidamount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 3), 1);
//                temp_paytype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 4), 1);
//
//                temp_paidamount = "-" + temp_paidamount;
//            }
//        } catch (Exception e){
//        }
//
//        String temp_employeeIdx = "";
//        String temp_employeeName = "";
//        String temp_serverIdx = "";
//        String temp_serverName = "";
//        String temp_tableidx = "";
//        String temp_tablename = "";
//        String temp_togodelitype = "";
//
//        strQuery = "select top 1 employeeIdx, employeeName, serverIdx, serverName, tableidx, tablename, togodelitype from salon_sales_detail where salescode = '" + temp_salesCode + "' " +
//                " order by idx asc ";
//        ResultSet tempSaleCartCursor2 = MssqlDatabase.getResultSetValue(strQuery);
//        try {
//            while (tempSaleCartCursor2.next()) {
//                temp_employeeIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 0), 1);
//                temp_employeeName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 1), 1);
//                temp_serverIdx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 2), 1);
//                temp_serverName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 3), 1);
//                temp_tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 4), 1);
//                temp_tablename = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 5), 1);
//                temp_togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor2, 6), 1);
//            }
//        } catch (Exception e){
//        }
//
//        strDeleteQuery = "insert into salon_sales_detail (" +
//                " salesCode, holdCode, reservationCode, sidx, stcode, " +
//                " categoryCode, categoryName, " +
//                " itemidx, itemName, itemFileName, itemFilePath, servicePositionNo, " +
//                " qty, salesOrgPrice, salesPrice, salesPriceAmount, " +
//                " salesBalPrice, salesBalPriceAmount, " +
//                " tax, taxAmount, " +
//                " totalAmount, " +
//                " commissionRatioType, commissionRatio, commission, commissionAmount, " +
//                " pointRatio, point, pointAmount, " +
//                " customerId, customerName, customerPhone, customerPosCode, " +
//                " employeeIdx, employeeName, " +
//                " serverIdx, serverName, " +
//                " giftcardNumberToSave, giftcardSavePriceToSave, " +
//                " couponNumber, " +
//                " eachDiscountExtraPrice, eachDiscountExtraType, eachDiscountExtraStr, " +
//                " dcextraforreturn, " +
//                " saveType, isQuickSale, isSale, status, isCloudUpload,  " +
//                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
//                " checkcompany, memoToKitchen, discountbuttonname, " +
//                " tableidx, mergednum, subtablenum, billnum, tablename, togodelitype " +
//                " ) values ( " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_salesCode,0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_holdcode,0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("",0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("Partial Void (" + temp_paidamount, 0) + ")', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("1",0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_paidamount, 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_employeeIdx, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_employeeName, 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_serverIdx, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_serverName, 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + "0" + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("0", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("N", 0) + "', " +
//                " '" + "1" + "', " +
//                " '" + "0" + "', " +
//                " '" + "0" + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + "0" + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + "0" + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + "0" + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_tableidx, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked("1", 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_tablename, 0) + "', " +
//                " '" + GlobalMemberValues.getDBTextAfterChecked(temp_togodelitype, 0) + "' " +
//                ")";
//        returnValueVec.addElement(strDeleteQuery);
//        // ---------------------------------------------------------------------------------------------

        strDeleteQuery = "delete from salon_sales_card where idx = '" + paramSalonSalesCardIdx + "' ";
        returnValueVec.addElement(strDeleteQuery);

        strDeleteQuery = "delete from bill_list_paid where idx = '" + paramBillPaidIdx + "' ";
        returnValueVec.addElement(strDeleteQuery);

        strDeleteQuery = "update bill_list set paidyn = 'N' where idx = '" + paramBillIdx + "' ";
        returnValueVec.addElement(strDeleteQuery);

        return returnValueVec;
    }

    public static String getTableIdxWithoutStringT(String paramTableIdx) {
        String returnValue = "";
        returnValue = GlobalMemberValues.getReplaceText(paramTableIdx, "T", "");
        return returnValue;
    }

    public static boolean isCustomBillSplitUse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select custombillsplituseyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean isMultistationsyncuse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select multistationsyncuseyn from salon_storegeneral"), 1);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    // 07.18.2022 - add pay for cash, card
    public static String getAddPayType() {
        String returnValue = "A";

        returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select addpaytype from salon_storegeneral"
        );
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "A";
        }

//        returnValue = "C";

        return returnValue;
    }

    // 07.18.2022 - add pay for cash, card
    public static String[] getAddPayData() {
        String[] returnValue = {"%", "0", "", "AT"};

        String strQuery = "select addpaymoneytype, addpaymoney, addpayname, addpaytaxtype " +
                " from salon_storegeneral ";
        Cursor settingsDevicePrinterCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
            String addpaymoneytype = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
            String addpaymoney = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
            String addpayname = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);
            String addpaytaxtype = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(3), 1);

            if (GlobalMemberValues.isStrEmpty(addpaymoneytype)) {
                addpaymoneytype = "%";
            }
            if (GlobalMemberValues.isStrEmpty(addpaymoney)) {
                addpaymoney = "0";
            }
            if (GlobalMemberValues.isStrEmpty(addpayname)) {
                addpayname = "";
            }
            if (GlobalMemberValues.isStrEmpty(addpaytaxtype)) {
                addpaytaxtype = "AT";
            }

            returnValue[0] = addpaymoneytype;
            returnValue[1] = addpaymoney;
            returnValue[2] = addpayname;
            returnValue[3] = addpaytaxtype;
        }

//        String[] returnValue = {"%", "10", "Cash Discount", "AT"};

        return returnValue;
    }

    // 07.18.2022 - add pay for cash, card
    public static void deleteCartLastItemForAddPay(String[] paramAddPayValue) {
        if (paramAddPayValue != null && paramAddPayValue.length == 4) {
            String addpaymoney = paramAddPayValue[1];

            double tempValue = GlobalMemberValues.getDoubleAtString(addpaymoney);
            if (tempValue > 0) {
                int tempCustomerCnt = TableSaleMain.getTablePeopleCntByHoldCode(MainMiddleService.mHoldCode);

                // 장바구니에 담기전에 먼저 add pay 부분을 삭제한다.
                if (MainMiddleService.mGeneralArrayList != null && MainMiddleService.mGeneralArrayList.size() > 1) {
                    int delIndex = MainMiddleService.mGeneralArrayList.size() - 1;
                    String tempItemName = MainMiddleService.mGeneralArrayList.get(delIndex).mSvcName;
                    if (tempItemName.equals(paramAddPayValue[2])) {
                        MainMiddleService.deleteItem((delIndex), true, false, "");
                    }
                }

                // 102022 이곳에
                GlobalMemberValues.setGoneForCardCashPayView();

            }
        }
    }

    // 102022
    public static void setGoneForCardCashPayView() {
        GlobalMemberValues.mAddPayMsgForCard = "";

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLN.setVisibility(View.GONE);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLNTV.setText("");

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN.setVisibility(View.GONE);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV.setText("");

        if (PaymentCreditCard.paymnetcreidtcartaddpayLn != null) {
            PaymentCreditCard.paymnetcreidtcartaddpayLn.setVisibility(View.GONE);
        }
        if (PaymentCreditCard.paymnetcreidtcartaddpayTv != null) {
            PaymentCreditCard.paymnetcreidtcartaddpayTv.setText("");
        }

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT != null) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT.setVisibility(View.GONE);
        }
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV != null) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV.setText("");
        }
    }

    // 07.18.2022 - add pay for cash, card
    public static void addCartLastItemForAddPay(String[] paramAddPayValue) {
        if (paramAddPayValue != null && paramAddPayValue.length == 4) {
            String tempAddPayType = paramAddPayValue[0];
            double tempAddPayMoney = GlobalMemberValues.getDoubleAtString(paramAddPayValue[1]);
            if (tempAddPayMoney > 0) {

                // 장바구니에 Add Pay 가 담겨져 있지 않는 경우에만 아래 실행
                int tempCnt = 0;
                for (TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
                    String tempItemName = tempSaleCart.mSvcName;
                    if (tempItemName.equals(paramAddPayValue[2])) {
                        tempCnt++;
                    }
                }

                GlobalMemberValues.logWrite("addpaylogjjj", "tempCnt : " + tempCnt + "\n");

                if (tempCnt > 0) {
                    return;
                }

                String tempEmpIdx = "";
                String tempEmpName = "";
                if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                    tempEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                    tempEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                }

                String tempCustomerId = "";
                String tempCustomerName = "";
                String tempCustomerPhone = "";
                if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
                    tempCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
                    tempCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
                    tempCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
                }

                // 10182022
                double tempAddMinusPDF = 0.0;

                String tempTotalValue = "";
                if (paramAddPayValue[3].equals("AT")) {
                    tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString();
                    GlobalMemberValues.logWrite("addpaylogjjj", "여기..1 : " + tempTotalValue + "\n");

                    tempAddMinusPDF = 0.0;
                } else {
                    tempTotalValue = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString();
                    GlobalMemberValues.logWrite("addpaylogjjj", "여기..2 : " + tempTotalValue + "\n");

                    // 10182022
                    tempAddMinusPDF = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString());
                }

                String addpay_val = "";
                if (tempAddPayType.equals("$")) {
                    addpay_val = tempAddPayMoney + "";
                } else {
                    // 10172022
                    addpay_val = (GlobalMemberValues.getDoubleAtString(tempTotalValue) + tempAddMinusPDF) * (tempAddPayMoney * 0.01) + "";
                }
                addpay_val = GlobalMemberValues.getStringFormatNumber(addpay_val, "2");

                if (GlobalMemberValues.getAddPayType().equals("B")) {
                    addpay_val = "-" + addpay_val;
                }

                String paramsString[] = {
                        "1", MainMiddleService.mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE,
                        "0", "0",
                        paramAddPayValue[2], "", "",
                        addpay_val, addpay_val, "", "",
                        "0", "", "0",
                        "", "N",
                        tempCustomerId, tempCustomerName, tempCustomerPhone, "0", tempEmpIdx, tempEmpName, "N", paramAddPayValue[2]};

                MainMiddleService.mQuickSaleYN = "Y";
                MainMiddleService.insertTempSaleCart(paramsString);

                String tempMmm = "included";
                if (GlobalMemberValues.getAddPayType().equals("B")) {
                    tempMmm = "excluded";
                }
                String addpaymoney = GlobalMemberValues.getReplaceText(addpay_val, "-", "");
                String tempMsg = "(" + tempMmm + " " + paramAddPayValue[2] + " $" + GlobalMemberValues.getCommaStringForDouble(addpaymoney) + ")";


                GlobalMemberValues.mAddPayMsgForCard = tempMsg;

                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLN.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLNTV.setText(tempMsg);

                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN.setVisibility(View.VISIBLE);
                }

                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV.setText(tempMsg);
                }

                if (PaymentCreditCard.paymnetcreidtcartaddpayLn != null) {
                    PaymentCreditCard.paymnetcreidtcartaddpayLn.setVisibility(View.VISIBLE);
                }
                if (PaymentCreditCard.paymnetcreidtcartaddpayTv != null) {
                    PaymentCreditCard.paymnetcreidtcartaddpayTv.setText(tempMsg);
                }

                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT.setVisibility(View.VISIBLE);
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV.setText(tempMsg);
                }
            }
        }
    }

    public static boolean isCustomerOnlineCheck() {
        boolean returnValue = false;
        String tempValue = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select customeronlinecheckyn from salon_storestationsettings_system"), 1);
        if (tempValue == "Y" || tempValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }

    public static String getStoreName() {
        String returnValue = "";

        returnValue = MainActivity.mDbInit.dbExecuteReadReturnString("select name from salon_storeinfo ");
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = GlobalMemberValues.SALON_NAME;
        }

        return returnValue;
    }

    public static String getStoreNameEN() {
        String returnValue = "";

        returnValue = MainActivity.mDbInit.dbExecuteReadReturnString("select name_en from salon_storeinfo ");
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "";
        }

        return returnValue;
    }

    public static String getStoreName_sName() {
        String returnValue = "";

        returnValue = MainActivity.mDbInit.dbExecuteReadReturnString("select sname from basic_pos_information ");
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = GlobalMemberValues.SALON_NAME;
        }

        return returnValue;
    }

    public static boolean isEODPossible() {
        boolean returnValue = false;
        String temp_stationcode = GlobalMemberValues.getStationCode();
        String tempValue = "";
        if (temp_stationcode.isEmpty()){
            tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select eodyn from salon_storestationinfo"
            );
        }else {
            tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select eodyn from salon_storestationinfo where stcode = '" + temp_stationcode + "'"
            );
        }

        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "Y";
        }
        if (tempValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }

    public static String getUseYNMasterPrinter(Context paramContext) {
        String temp = "";
        DatabaseInit dbInit = new DatabaseInit(paramContext);
        String tempSqlQuery = "select masteruseyn from salon_storestationsettings_deviceprinter_master";
        if (MainActivity.mDbInit == null){
            temp = dbInit.dbExecuteReadReturnString(tempSqlQuery);
        } else {
            temp = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        }

        return temp;
    }

    public static boolean selectPhoneOrderGetFoodType(){
        boolean b_return = false;

        return true;
    }

    public static JSONArray edit_equl_array_item_merge(JSONArray jsonArray){
        JSONArray temp_jsonArray = new JSONArray();
        JSONArray newJSONArray = new JSONArray();

        if (jsonArray != null && jsonArray.length() >= 0){
            for (int z = 0; z < jsonArray.length(); z++) {
                try {
                    JSONObject original = jsonArray.getJSONObject(z);
                    JSONObject clone = new JSONObject();

                    for ( Iterator<String> iterator = original.keys(); iterator.hasNext(); ) {
                        String      key     = iterator.next();
                        Object      value   = original.opt(key);

                        try {
                            clone.put(key, value);
                        } catch ( JSONException e ) {
                            //TODO process exception
                        }
                    }

                    newJSONArray.put(clone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        boolean is_add_item = true;
        if (jsonArray != null && jsonArray.length() >= 0){

            try {
                for (int z = 0; z < jsonArray.length(); z++) {
                    JSONObject dic_item = jsonArray.getJSONObject(z);
                    JSONObject orign_dic_item = newJSONArray.getJSONObject(z);
                    is_add_item = true;
                    for (int j = 0; j < jsonArray.length(); j++) {
                        if (z == j) {
                        } else {
                            String temp1_idx = dic_item.getString("itemcartidx");
                            String temp1_name = dic_item.getString("itemname");
                            String temp1_price = dic_item.getString("itemprice");
                            String temp1_amount = dic_item.getString("itemamount");
                            String temp1_optprice = dic_item.getString("optionprice");
                            String temp1_addprice1 = dic_item.getString("additionalprice1");
                            String temp1_addprice2 = dic_item.getString("additionalprice2");
                            String temp1_itemdcextraprice = dic_item.getString("itemdcextraprice");
                            String temp1_optname = dic_item.getString("optiontxt");
                            String temp1_additionaltxt1 = dic_item.getString("additionaltxt1");
                            String temp1_additionaltxt2 = dic_item.getString("additionaltxt2");
                            String temp1_kitchenmemo = dic_item.getString("kitchenmemo");

                            JSONObject temp_dic_item = jsonArray.getJSONObject(j);
                            String temp2_idx = temp_dic_item.getString("itemcartidx");
                            String temp2_name = temp_dic_item.getString("itemname");
                            String temp2_price = temp_dic_item.getString("itemprice");
                            String temp2_amount = temp_dic_item.getString("itemamount");
                            String temp2_optprice = temp_dic_item.getString("optionprice");
                            String temp2_addprice1 = temp_dic_item.getString("additionalprice1");
                            String temp2_addprice2 = temp_dic_item.getString("additionalprice2");
                            String temp2_itemdcextraprice = temp_dic_item.getString("itemdcextraprice");
                            String temp2_optname = temp_dic_item.getString("optiontxt");
                            String temp2_additionaltxt1 = temp_dic_item.getString("additionaltxt1");
                            String temp2_additionaltxt2 = temp_dic_item.getString("additionaltxt2");
                            String temp2_kitchenmemo = temp_dic_item.getString("kitchenmemo");

                            String temp2_qty = temp_dic_item.getString("itemqty");

                            if (
                                    temp1_idx.equals(temp2_idx) &&
                                            temp1_name.equals(temp2_name) &&
                                            temp1_price.equals(temp2_price) &&
                                            temp1_amount.equals(temp2_amount) &&
                                            temp1_optprice.equals(temp2_optprice) &&
                                            temp1_addprice1.equals(temp2_addprice1) &&
                                            temp1_addprice2.equals(temp2_addprice2) &&
                                            temp1_itemdcextraprice.equals(temp2_itemdcextraprice) &&
                                            temp1_optname.equals(temp2_optname) &&
                                            temp1_additionaltxt1.equals(temp2_additionaltxt1) &&
                                            temp1_additionaltxt2.equals(temp2_additionaltxt2) &&
                                            temp1_kitchenmemo.equals(temp2_kitchenmemo)){

                                if (z > j){
                                    // 아이템 중복 방지.
                                    is_add_item = false;
                                } else {
                                    String temp1_qty = orign_dic_item.getString("itemqty");
                                    int i_item_qty = Integer.parseInt(temp1_qty);
                                    int i_item2_qty = Integer.parseInt(temp2_qty);

                                    String origin_price = orign_dic_item.getString("itemprice");
                                    String origin_amount = orign_dic_item.getString("itemamount");
                                    String origin_optprice = orign_dic_item.getString("optionprice");
                                    String origin_addprice1 = orign_dic_item.getString("additionalprice1");
                                    String origin_addprice2 = orign_dic_item.getString("additionalprice2");
                                    String origin_itemdcextraprice = orign_dic_item.getString("itemdcextraprice");

                                    double item_price = GlobalMemberValues.getDoubleAtString(temp1_price) + GlobalMemberValues.getDoubleAtString(origin_price);
                                    double item_amount = GlobalMemberValues.getDoubleAtString(temp1_amount) + GlobalMemberValues.getDoubleAtString(origin_amount);
                                    double item_optprice = GlobalMemberValues.getDoubleAtString(temp1_optprice) + GlobalMemberValues.getDoubleAtString(origin_optprice);
                                    double item_addprice1 = GlobalMemberValues.getDoubleAtString(temp1_addprice1) + GlobalMemberValues.getDoubleAtString(origin_addprice1);
                                    double item_addprice2 = GlobalMemberValues.getDoubleAtString(temp1_addprice2) + GlobalMemberValues.getDoubleAtString(origin_addprice2);
                                    double item_itemdcextraprice = GlobalMemberValues.getDoubleAtString(temp2_itemdcextraprice) + GlobalMemberValues.getDoubleAtString(origin_itemdcextraprice);

                                    String sub_qty_str = String.valueOf(i_item_qty + i_item2_qty);
                                    String sub_price_str = GlobalMemberValues.setDoubleToString(item_price,2);
                                    String sub_amount_str = GlobalMemberValues.setDoubleToString(item_amount,2);
                                    String sub_optprice_str = GlobalMemberValues.setDoubleToString(item_optprice,2);
                                    String sub_addprice1_str = GlobalMemberValues.setDoubleToString(item_addprice1,2);
                                    String sub_addprice2_str = GlobalMemberValues.setDoubleToString(item_addprice2,2);
                                    String sub_itemdcextraprice_str = GlobalMemberValues.setDoubleToString(item_itemdcextraprice,2);

                                    orign_dic_item.put("itemqty", sub_qty_str);
                                    orign_dic_item.put("itemprice", sub_price_str);
                                    orign_dic_item.put("itemamount", sub_amount_str);
                                    orign_dic_item.put("optionprice", sub_optprice_str);
                                    orign_dic_item.put("additionalprice1", sub_addprice1_str);
                                    orign_dic_item.put("additionalprice2", sub_addprice2_str);
                                    orign_dic_item.put("itemdcextraprice", sub_itemdcextraprice_str);

                                }
                            } else {
                            }
                        }
                    }
                    if (is_add_item){
                        temp_jsonArray.put(orign_dic_item);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (temp_jsonArray == null || temp_jsonArray.length() == 0){
            return jsonArray;
        } else {
            return temp_jsonArray;
        }

    }

    public static JSONArray edit_equl_array_item_merge_stringarray(JSONArray jsonArray){
        JSONArray temp_jsonArray = new JSONArray();
        JSONArray newJSONArray = new JSONArray();

        if (jsonArray != null && jsonArray.length() >= 0){
            for (int z = 0; z < jsonArray.length(); z++) {
                try {
                    String original = jsonArray.getString(z);
                    String clone = original;

                    newJSONArray.put(clone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        boolean is_add_item = true;
        if (jsonArray != null && jsonArray.length() >= 0){

            try {
                for (int z = 0; z < jsonArray.length(); z++) {
                    String str_item = jsonArray.getString(z);
                    String[] strOrderItems = str_item.split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);
                    String orign_str_item = newJSONArray.getString(z);
                    String[] orign_strOrderItems = orign_str_item.split(GlobalMemberValues.STRSPLITTER_ORDERITEM2);
                    is_add_item = true;
                    for (int j = 0; j < jsonArray.length(); j++) {
                        if (z == j) {
                        } else {

                            String tempItemNameOptionAdd = strOrderItems[0];
                            String tempItemQty = strOrderItems[1];
                            String tempPrice = strOrderItems[4];
                            String tempPriceAmount = strOrderItems[5];
                            String tempTaxAmount = "0.0";
//                        if (strOrderItems.length > 7) {
//                            tempTaxAmount = strOrderItems[7];
//                        }
                            if (strOrderItems.length > 8) {
                                tempTaxAmount = strOrderItems[8];
                            }
                            String tempTotalAmount = strOrderItems[4];

                            String[] strItemNAmeOptionAdd = tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
                            String tempItemName = strItemNAmeOptionAdd[0];
                            String tempOptionTxt = "";
                            String tempAdditionalTxt1 = "";
                            String tempAdditionalTxt2 = "";
                            String tempItemIdx = "";
                            String tempKitchenMemo = "";
                            String tempOptionPrice = "";
                            String temptemp_additionalprice1 = "";
                            String temptemp_additionalprice2 = "";
                            String selectedDcExtraAllEach = "";
                            String selectedDcExtraType = "";
                            String dcextratype = "";
                            String dcextravalue = "";
                            String selectedDcExtraPrice = "";

                            if (strItemNAmeOptionAdd.length > 1) {
                                tempOptionTxt = strItemNAmeOptionAdd[1];
                                if (strItemNAmeOptionAdd.length > 2) {
                                    tempAdditionalTxt1 = strItemNAmeOptionAdd[2];
                                }
                                if (strItemNAmeOptionAdd.length > 3) {
                                    tempAdditionalTxt2 = strItemNAmeOptionAdd[3];
                                }
                                if (strItemNAmeOptionAdd.length > 4) {
                                    tempItemIdx = strItemNAmeOptionAdd[4];
                                }
                                if (strItemNAmeOptionAdd.length > 6) {
                                    tempKitchenMemo = strItemNAmeOptionAdd[6];
                                }
                                if (strItemNAmeOptionAdd.length > 7) {
                                    tempOptionPrice = strItemNAmeOptionAdd[7];
                                }
                                if (strItemNAmeOptionAdd.length > 8) {
                                    temptemp_additionalprice1 = strItemNAmeOptionAdd[8];
                                }
                                if (strItemNAmeOptionAdd.length > 9) {
                                    temptemp_additionalprice2 = strItemNAmeOptionAdd[9];
                                }
                                // discount / extra
                                if (strItemNAmeOptionAdd.length > 10) {
                                    selectedDcExtraAllEach = strItemNAmeOptionAdd[10];
                                }
                                if (strItemNAmeOptionAdd.length > 11) {
                                    selectedDcExtraType = strItemNAmeOptionAdd[11];
                                }
                                if (strItemNAmeOptionAdd.length > 12) {
                                    dcextratype = strItemNAmeOptionAdd[12];
                                }
                                if (strItemNAmeOptionAdd.length > 13) {
                                    dcextravalue = strItemNAmeOptionAdd[13];
                                }
                                if (strItemNAmeOptionAdd.length > 14) {
                                    selectedDcExtraPrice = strItemNAmeOptionAdd[14];
                                }
                            }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                            String orign_tempItemNameOptionAdd = orign_strOrderItems[0];
                            String orign_tempItemQty = orign_strOrderItems[1];
                            String orign_tempPrice = orign_strOrderItems[4];
                            String orign_tempTotalAmount = orign_strOrderItems[4];
                            String orign_tempPriceAmount = orign_strOrderItems[5];
                            String orign_tempTaxAmount = "0.0";

                            if (strOrderItems.length > 8) {
                                orign_tempTaxAmount = orign_strOrderItems[8];
                            }

                            String[] orign_strItemNAmeOptionAdd = orign_tempItemNameOptionAdd.split(GlobalMemberValues.STRSPLITTER_ORDERITEM3);
                            String orign_tempItemName = orign_strItemNAmeOptionAdd[0];
                            String orign_tempOptionPrice = "";
                            String orign_temptemp_additionalprice1 = "";
                            String orign_temptemp_additionalprice2 = "";


                            if (orign_strItemNAmeOptionAdd.length > 7) {
                                orign_tempOptionPrice = orign_strItemNAmeOptionAdd[7];
                            }
                            if (orign_strItemNAmeOptionAdd.length > 8) {
                                orign_temptemp_additionalprice1 = orign_strItemNAmeOptionAdd[8];
                            }
                            if (orign_strItemNAmeOptionAdd.length > 9) {
                                orign_temptemp_additionalprice2 = orign_strItemNAmeOptionAdd[9];
                            }


                            if (tempItemName.equals(orign_tempItemName) &&
                                    tempPrice.equals(orign_tempPrice) &&
                                    tempTotalAmount.equals(orign_tempTotalAmount) &&
                                    tempPriceAmount.equals(orign_tempPriceAmount) &&
                                    tempOptionPrice.equals(orign_tempOptionPrice) &&
                                    temptemp_additionalprice1.equals(orign_temptemp_additionalprice1) &&
                                    temptemp_additionalprice2.equals(orign_temptemp_additionalprice2)){

                                if (z > j){
                                    // 아이템 중복 방지.
                                    is_add_item = false;
                                } else {
                                    int i_item_qty = Integer.parseInt(tempItemQty);
                                    int i_item2_qty = Integer.parseInt(orign_tempItemQty);

                                    double item_price = GlobalMemberValues.getDoubleAtString(tempPrice) + GlobalMemberValues.getDoubleAtString(orign_tempPrice);
                                    double item_amount = GlobalMemberValues.getDoubleAtString(tempPriceAmount) + GlobalMemberValues.getDoubleAtString(orign_tempPriceAmount);
                                    double item_TotalAmount = GlobalMemberValues.getDoubleAtString(tempTotalAmount) + GlobalMemberValues.getDoubleAtString(orign_tempTotalAmount);
                                    double item_optprice = GlobalMemberValues.getDoubleAtString(tempOptionPrice) + GlobalMemberValues.getDoubleAtString(orign_tempOptionPrice);
                                    double item_addprice1 = GlobalMemberValues.getDoubleAtString(temptemp_additionalprice1) + GlobalMemberValues.getDoubleAtString(orign_temptemp_additionalprice1);
                                    double item_addprice2 = GlobalMemberValues.getDoubleAtString(temptemp_additionalprice2) + GlobalMemberValues.getDoubleAtString(orign_temptemp_additionalprice2);
                                    double item_tax = GlobalMemberValues.getDoubleAtString(tempTaxAmount) + GlobalMemberValues.getDoubleAtString(orign_tempTaxAmount);


                                    String sub_qty_str = String.valueOf(i_item_qty + i_item2_qty);
                                    String sub_price_str = GlobalMemberValues.setDoubleToString(item_price,2);
                                    String sub_amount_str = GlobalMemberValues.setDoubleToString(item_amount,2);
                                    String sub_optprice_str = GlobalMemberValues.setDoubleToString(item_optprice,2);
                                    String sub_addprice1_str = GlobalMemberValues.setDoubleToString(item_addprice1,2);
                                    String sub_addprice2_str = GlobalMemberValues.setDoubleToString(item_addprice2,2);
                                    String sub_item_TotalAmount = GlobalMemberValues.setDoubleToString(item_TotalAmount,2);
                                    String sub_item_tax = GlobalMemberValues.setDoubleToString(item_tax,2);


                                    String temp = orign_strItemNAmeOptionAdd[0] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[1] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[2] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[3] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[4] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[5] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + tempKitchenMemo + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + sub_optprice_str + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + sub_addprice1_str + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + sub_addprice2_str + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + selectedDcExtraAllEach + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + selectedDcExtraType + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + dcextratype + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + dcextravalue + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + selectedDcExtraPrice + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[15] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[16] + GlobalMemberValues.STRSPLITTER_ORDERITEM3
                                            + orign_strItemNAmeOptionAdd[17] + GlobalMemberValues.STRSPLITTER_ORDERITEM3;
                                    String temp2 = temp + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + sub_qty_str + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[2] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[3] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + sub_price_str + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + sub_amount_str + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[6] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[7] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + sub_item_tax + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[9] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[10] + GlobalMemberValues.STRSPLITTER_ORDERITEM2
                                            + orign_strOrderItems[11] + GlobalMemberValues.STRSPLITTER_ORDERITEM2;

                                    orign_strOrderItems[z] = temp2;
                                }
                            } else {
                            }
                        }
                    }
                    if (is_add_item){
                        temp_jsonArray.put(orign_strOrderItems);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return temp_jsonArray;
    }

    public static void deleteSignature(Context paramContext) {
        String returnResult = "";

        DatabaseInit dbInit = new DatabaseInit(paramContext);;
        String strQuery = "select idx, signedimgdir from salon_sales_signedimg ";

        GlobalMemberValues.logWrite("Globalmembervalues", "select query : " + strQuery + "\n");
        Cursor delSignatureCursor = dbInit.dbExecuteRead(strQuery);
        while (delSignatureCursor.moveToNext()) {
            String tempIdx = delSignatureCursor.getString(0);
            String tempSignatureImageDir = delSignatureCursor.getString(1);

            // 서명이미지 삭제
            if (!GlobalMemberValues.isStrEmpty(tempSignatureImageDir) && new File(tempSignatureImageDir).exists()) {
                new File(tempSignatureImageDir).delete();
            }

            // 서명파일 삭제가 정상적으로 진행되면 delyn 값을 'Y' 로 처리
            if (!GlobalMemberValues.isStrEmpty(tempSignatureImageDir) && !(new File(tempSignatureImageDir).exists())) {
                Vector<String> strDeleteQueryVec = new Vector<String>();
                strQuery = " delete from salon_sales_signedimg " +
                        " where idx = '" + tempIdx + "' ";
                strDeleteQueryVec.addElement(strQuery);
                for (String tempQuery : strDeleteQueryVec) {
                    GlobalMemberValues.logWrite("Globalmembervalues", "delete query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strDeleteQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
                    GlobalMemberValues.logWrite("Globalmembervalues", "Custom Error Msg : Database Error" + "\n");
                } else {
                }
            }
        }

        // sign_ 으로 시작하고 .png 로 끝나는 파일 Pic 폴더에서 모두 삭제.
        String strFilePath = Environment.getExternalStoragePublicDirectory(GlobalMemberValues.SIGNEDIMAGE_FOLDER) + "/";

        File directory = new File(strFilePath);
        File[] files = directory.listFiles();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                for (int i=0; i< files.length; i++) {
                    if (files[i].getName().contains("sign_") && files[i].getName().endsWith(".png")){
                        String del_name = strFilePath + files[i].getName();
                        try{
                            new File(del_name).delete();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        thread.start();

//        for (int i=0; i< files.length; i++) {
//            if (files[i].getName().contains("sign_") && files[i].getName().endsWith(".png")){
//                String del_name = strFilePath + files[i].getName();
//                try{
//                    new File(del_name).delete();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//        }
    }

    // 01052023
    public static String makeBillCode() {
        String returnReturnCode = "";
        returnReturnCode = "BL" + makeSalesCode();
        return returnReturnCode;
    }

    // 01132023
    public static String setChangeDoubleMinus(String paramTxt) {
        String returnValue = "";

        if (!GlobalMemberValues.isStrEmpty(paramTxt)) {
            paramTxt = GlobalMemberValues.getReplaceText(paramTxt, "--", "");
            returnValue = paramTxt;
        }

        return returnValue;
    }

    // 20230204
    public static String getPaymentResultKitchenPrinted(Context context, String mReceiptNum){


//        if (MainMiddleService.mGeneralArrayList_copy != null){
//
//        }


        String searchStartDate = "";
        String searchEndDate = "";
        String searchCustomerText = mReceiptNum;
        String salonSalesKitchenPrintedYN = "";

        if (!GlobalMemberValues.isStrEmpty(mReceiptNum)) {
            if (GlobalMemberValues.isStrEmpty(searchStartDate)) {
                searchStartDate = "2010-01-01";
            }
            if (GlobalMemberValues.isStrEmpty(searchEndDate)) {
                searchEndDate = "2050-12-31";
            }
        }

        String dateSearchQuery = "";
        dateSearchQuery = " saleDate between  '" + searchStartDate + "' " +
                // strftime('%m-%d-%Y', a.saleDate, 'localtime') 가 아닌
                // 그냥 a.saleDate 로 검색하게 되면 yyyy-mm-dd 로 해야하는데,
                // 그렇게 할 경우 searchEndDate 에 하루를 더해야 함...
                // "' and '" + DateMethodClass.getCustomEditDate(searchEndDate, 0, 0, 1) + "' ";
                " and '" + searchEndDate + "' ";

        if (!GlobalMemberValues.isStrEmpty(dateSearchQuery)) {
            dateSearchQuery += " and ";
        }

        String customerSearchQuery = "";
        customerSearchQuery = " ( " +
                " a.holdCode like '%" + GlobalMemberValues.getDBTextAfterChecked(searchCustomerText, 0) + "%' " +
                " ) ";

        final String saleHistorySearchQuery = dateSearchQuery + customerSearchQuery;


        ResultSet salonSalesCursor, salonSalesDetailCursor, salonSalesTipCursor;

        // 세일정보 가져오기 (GetDataAtSQLite 클래스의 getSaleHistory 메소드를 통해 가져온다)
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(context);
        salonSalesCursor = dataAtSqlite.getSaleHistory(saleHistorySearchQuery, "","");


        try {
            while (salonSalesCursor.next()) {
                String dbSalesCode = GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor, 1);
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(dbSalesCode, " ", ""))) {

                    salonSalesKitchenPrintedYN = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(salonSalesCursor, 36), 1);
                }
            }
            salonSalesCursor.close();
        }catch (Exception e){
            Log.e("",e.toString());
        }
        return salonSalesKitchenPrintedYN;
    }

    public static boolean getSalonSalesDetail_KitchenPrintedYN(String paramSalesCode) {

        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue("select kitchenPrintedYN from salon_sales_detail where holdcode = '" + paramSalesCode + "'");
        try {
            while (tempSaleCartCursor.next()) {
                String isPrinted = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);
                if (isPrinted.equals("Y")){
                    return true;
                }
            }
            tempSaleCartCursor.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static boolean getSalonSalesDetail_LabelPrintedYN(String paramSalesCode) {

        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue("select labelPrintedYN from salon_sales_detail where holdcode = '" + paramSalesCode + "'");
        try {
            while (tempSaleCartCursor.next()) {
                String isPrinted = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1);
                if (isPrinted.equals("Y")){
                    return true;
                }
            }
            tempSaleCartCursor.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static boolean isSaleDataUploadPause() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String tempValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select saledatauploadpauseyn from salon_storestationsettings_system"), 1);
            if (GlobalMemberValues.isStrEmpty(tempValue)) {
                tempValue = "N";
            }
            if (tempValue.equals("Y")) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        } catch (Exception e) {
        }

        if (GlobalMemberValues.isProcessCreditCard) {
            returnValue = true;
        }

        return returnValue;
    }

    public static String getLabelPrinterNumForQuickSale(String paramCateIdx) {
        String returnValue = "";
        if (!GlobalMemberValues.isStrEmpty(paramCateIdx)) {
            String labelprintnum = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select labelprintnum from salon_storeservice_main where idx = '" + paramCateIdx + "' "
            );
            if (GlobalMemberValues.isStrEmpty(labelprintnum)) {
                labelprintnum = "";
            }

            returnValue = labelprintnum;
        }

        return returnValue;
    }
    // 04.14.2023 - add pay name
    public static String getAddPayName() {
        String returnValue = "";

        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        if (dbInit != null) {
            returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select addpayname from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "";
        }

        return returnValue;
    }


    // 04222023
    public static void setChangeBillPrintedStatus(String paramTableidx, String paramSubTableNum, boolean paramRefresh) {
        String strQuery =  "";

        if (!GlobalMemberValues.isStrEmpty(paramTableidx)) {
            paramTableidx = GlobalMemberValues.getReplaceText(paramTableidx, "T", "");
            paramTableidx = "T" + paramTableidx + "T";

            if (GlobalMemberValues.isStrEmpty(paramSubTableNum)) {
                paramSubTableNum = "1";
            }

            String tempHoldCode = TableSaleMain.getHoldCodeByTableidx(paramTableidx, paramSubTableNum);

            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();

//            strQuery = " update temp_salecart set billprintedyn = 'Y' where holdcode = '" + tempHoldCode + "' ";
            // 05032023 -----------------------------------------------------------------------------------------------
            // salon_billprinted 테이블에 저장하기 전에 동일한 스테이션, 동일한 holdcode 로 저장된 데이터는 지운다.
            strQuery = " delete from salon_billprinted where " +
                    " holdcode = '" + tempHoldCode + "' " +
                    " and stcode = '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "' ";
            strInsertQueryVec.addElement(strQuery);
            // 05032023 -----------------------------------------------------------------------------------------------


            // 05012023
            strQuery = " insert into salon_billprinted ( " +
                    " holdcode, stcode " +
                    " ) values ( " +
                    " '" + tempHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "' " +
                    " ) ";

            strInsertQueryVec.addElement(strQuery);


            // 05032023 -----------------------------------------------------------------------------------------------
            // 이전에 저장된 동일한 데이터는 삭제한다.
            strQuery = " delete from salon_table_statuschange where " +
                    " holdcode = '" + tempHoldCode + "' and ctype = 'billprint' ";
            strInsertQueryVec.addElement(strQuery);
            strQuery = " delete from salon_newcartcheck_bystation2 where " +
                    " holdcode = '" + tempHoldCode + "' and ctype = 'billprint' ";
            strInsertQueryVec.addElement(strQuery);

            // bill print 된 정보를 새로고침을 위한 테이블에 저장
            strQuery = " insert into salon_table_statuschange (holdcode, stcode, tableidx, delyn, ctype) values ( " +
                    " '" + tempHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + paramTableidx + "', " +
                    " '" + "N" + "', " +
                    " '" + "billprint" + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strQuery);
            // 05032023 -----------------------------------------------------------------------------------------------

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("billprintedynlogjjj", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error (changee bill printed status)", "Close");
            } else {
                // 05042023
                if (paramRefresh) {
                    // 05012023
                    TableSaleMain.onRefreshAtOutside();
                }
            }
        }
    }

    // 04222023
    public static boolean getBillPrintedStatus(String paramTableidx, String paramSubTableNum) {
        boolean returnResult = false;

        if (!GlobalMemberValues.isStrEmpty(paramTableidx)) {
            if (GlobalMemberValues.isStrEmpty(paramSubTableNum)) {
                paramSubTableNum = "1";
            }

            paramTableidx = GlobalMemberValues.getReplaceText(paramTableidx, "T", "");
            paramTableidx = "T" + paramTableidx + "T";

            String tempHoldCode = TableSaleMain.getHoldCodeByTableidx(paramTableidx, paramSubTableNum);

            int getCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(*) from salon_billprinted " +
                            " where holdcode = '" + tempHoldCode + "' "
            ));
            if (getCnt > 0) {
                returnResult = true;
            }
        }

        return returnResult;
    }

    public static boolean isTimeDisplayonTable() {
        boolean returnValue = false;

        try {
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
            String getValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                    "select timeviewontableyn from salon_storestationsettings_system"), 1);
            if (GlobalMemberValues.isStrEmpty(getValue)) {
                getValue = "N";
            }
            if (getValue.equals("Y")) {
                returnValue = true;
            }
        } catch (Exception e) {
        }

        return returnValue;
    }

    // 05042023
    public static String[] getWingmanData() {
        String[] returnValue = {"N", "", ""};

        String strQuery = "select wmuseyn, wmapiip, wmoptionstsr " +
                " from salon_storegeneral ";
        Cursor settingsDevicePrinterCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
            String wmuseyn = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
            String wmapiip = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
            String wmoptionstsr = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);

            if (GlobalMemberValues.isStrEmpty(wmuseyn)) {
                wmuseyn = "N";
            }
            if (GlobalMemberValues.isStrEmpty(wmapiip)) {
                wmapiip = "";
            }
            if (GlobalMemberValues.isStrEmpty(wmoptionstsr)) {
                wmoptionstsr = "";
            }

            returnValue[0] = wmuseyn;
            returnValue[1] = wmapiip;
            returnValue[2] = wmoptionstsr;
        }

        return returnValue;
    }

    // 05052023
    public static void dataSendToWingman() {
        // 05042023 ------------------------------------------------------------------------------------------
        // wingman 로봇팔 연동 관련
        if (GlobalMemberValues.getWingmanData()[0] == "Y" || GlobalMemberValues.getWingmanData()[0].equals("Y")) {
            // wingman 서버 ip
            String wmserverip = GlobalMemberValues.getWingmanData()[1];
            String wmoptionstsr = GlobalMemberValues.getWingmanData()[2];

            // wmserverip = "192.168.0.1";
            if (!GlobalMemberValues.isStrEmpty(wmserverip)) {
                String[] wmoptionstsr_arr = wmoptionstsr.split(",");

                Vector<String> wmvec = new Vector<String>();
                String resultMsgGroup = "";
                String idx, qty, optiontxt, beancode, salescode;
                ResultSet wmcursor;

                String strQuery = "select idx, qty, optionTxt, salescode " +
                        " from salon_sales_detail " +
                        " where wmodyn = 'Y' " +
                        " and wmodresult = 'N' " +
                        // 05112023
                        " and status = 0 " +
                        " order by idx asc ";

                GlobalMemberValues.logWrite("wingmanlogjjj", "strQuery : " + strQuery + "\n");

                wmcursor = MssqlDatabase.getResultSetValue(strQuery);
                int wmcnt = 0;
                try {
                    while (wmcursor.next()) {
                        idx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(wmcursor,0), 1);
                        qty = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(wmcursor,1), 1);
                        optiontxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(wmcursor,2), 1);

                        salescode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(wmcursor,3), 1);

                        GlobalMemberValues.logWrite("wingmanlogjjj", "optiontxt : " + optiontxt + "\n");

                        if (!GlobalMemberValues.isStrEmpty(optiontxt)) {
                            optiontxt = GlobalMemberValues.getReplaceText(optiontxt, ", ", ",");

                            String[] optxtArr = optiontxt.split(",");


                            // 05112023 --------------------------------------------------------------------------------------------
                            // wingman 관련 modifier 가져오기
                            String tempBeancode = "";
                            int tempJ = 999;

                            GlobalMemberValues.logWrite("wmjjjlog", "optiontxt : " + optiontxt + "\n");

                            for (int jjj_i = 0; jjj_i < optxtArr.length; jjj_i++) {
                                GlobalMemberValues.logWrite("wmjjjlog", "optxtArr[" + jjj_i + "] : " + optxtArr[jjj_i] + "\n");

                                String[] tempBeancodeArr = optxtArr[jjj_i].split(" ");
                                tempBeancode = tempBeancodeArr[0];

                                tempBeancode = GlobalMemberValues.getReplaceText(tempBeancode, " ", "");

                                GlobalMemberValues.logWrite("wmjjjlog", "tempBeancode1 : " + tempBeancode + "\n");

                                if (!GlobalMemberValues.isStrEmpty(tempBeancode)) {
                                    for (int jjj_j = 0; jjj_j < wmoptionstsr_arr.length; jjj_j++) {
                                        GlobalMemberValues.logWrite("wmjjjlog", "wmoptionstsr_arr[" + jjj_j + "] : " + wmoptionstsr_arr[jjj_j] + "\n");

                                        if (tempBeancode.equals(wmoptionstsr_arr[jjj_j])) {
                                            tempJ = jjj_i;
                                        }
                                    }
                                }
                            }

                            GlobalMemberValues.logWrite("wmjjjlog", "tempJ : " + tempJ + "\n");

                            // 05112023 --------------------------------------------------------------------------------------------


                            if (tempJ != 999) {

                                String beancodeStr = optxtArr[tempJ];
                                if (!GlobalMemberValues.isStrEmpty(beancodeStr)) {
                                    String[] beancodeArr = beancodeStr.split(" ");
                                    beancode = beancodeArr[0];

                                    String wmurl = "http://" + wmserverip + ":8000/orders/" + salescode + "-" + idx + "/" + beancode + "/" + qty;
                                    GlobalMemberValues.logWrite("wingmanlogjjj", "wmurl : " + wmurl + "\n");

                                    if (!GlobalMemberValues.isStrEmpty(beancode)) {
                                        // 테스트 URL
                                        // wmurl = "https://csecu.2go2go.com/test_wm_api.asp";

                                        String resultStr = "";
                                        try {
                                            resultStr = new WingmanTask(wmurl).execute().get();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }
                                        GlobalMemberValues.logWrite("wingmanlogjjj", "데이터 전송 결과 : " + resultStr + "\n");

                                        if (!GlobalMemberValues.isStrEmpty(resultStr)) {
                                            JSONObject json = null;
                                            String resultMsg = "";
                                            try {
                                                json = new JSONObject(resultStr);
                                                resultMsg = json.getString("result");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (resultMsg.equals("success")) {
                                                // wingman 처리결과 salon_sales_detail 에 반영
                                                // 05102023 수정
                                                strQuery = " update salon_sales_detail set " +
                                                        " wmodresult = 'Y', wmodresultmsg = '" + resultMsg + "', " +
                                                        " wmurl = '" + wmurl + "' " +
                                                        " where idx = '" + idx + "' ";
                                                wmvec.addElement(strQuery);
                                            } else {
                                                // 05082023
                                                // wingman 처리결과 에러메시지 salon_sales_detail 에 반영
                                                // 05102023 수정
                                                strQuery = " update salon_sales_detail set " +
                                                        " wmodresult = 'N', wmodresultmsg = '" + resultMsg + "', " +
                                                        " wmurl = '" + wmurl + "' " +
                                                        " where idx = '" + idx + "' ";
                                                wmvec.addElement(strQuery);

                                                if (wmcnt == 0) {
                                                    resultMsgGroup = resultMsg;
                                                } else {
                                                    resultMsgGroup += ", " + resultMsg;
                                                }
                                            }
                                        } else {
                                            // 05112023
                                            strQuery = " update salon_sales_detail set " +
                                                    " wmodresult = 'N', wmodresultmsg = 'No return msg', " +
                                                    " wmurl = '" + wmurl + "' " +
                                                    " where idx = '" + idx + "' ";
                                            wmvec.addElement(strQuery);
                                        }
                                        wmcnt++;
                                    } else {
                                        // 05112023
                                        strQuery = " update salon_sales_detail set " +
                                                " wmodresult = 'N', wmodresultmsg = 'url error', " +
                                                " wmurl = '' " +
                                                " where idx = '" + idx + "' ";
                                        wmvec.addElement(strQuery);
                                    }
                                }
                            }
                        }
                    }
                    wmcursor.close();
                } catch (Exception e){
                }

                for (String tempQuery : wmvec) {
                    GlobalMemberValues.logWrite("wingmanlogjjj", "query : " + tempQuery + "\n");
                }

                GlobalMemberValues.logWrite("wingmanlogjjj", "==========================================" + "\n");


                // String returnResult = MssqlDatabase.executeTransaction(wmvec);
                String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(wmvec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Wingman Warning", "Database Error", "Close");
                } else {
                }

                if (!GlobalMemberValues.isStrEmpty(resultMsgGroup)) {
                    // wingman api 연동결과 오류메시지 출력
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Wingman Warning", resultMsgGroup, "Close");
                }
            }
        }
        // 05042023 ------------------------------------------------------------------------------------------
    }


    // 05.11.2023 - add pay for cash, card 고객선택창 오픈 여부
    public static boolean isOpenCustomerPopupForAddPay() {
        boolean returnValue = false;

        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "";
        if (dbInit != null) {
            getValue = dbInit.dbExecuteReadReturnString(
                    "select addpaytype from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "A";
        }

        String getValue2 = "";
        if (dbInit != null) {
            getValue2 = dbInit.dbExecuteReadReturnString(
                    "select addpaycustomerselectyn from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue2)) {
            getValue2 = "N";
        }

        if (!getValue.equals("A") && getValue2.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }


    // 09282023
    // 05112023
    // add pay customer select popup
    public static void openAddPayCustomerSelectPopup() {
// 09282023
        String[] getAddPayValue = GlobalMemberValues.getAddPayData();
        if (getAddPayValue != null && getAddPayValue.length == 4) {
            String getAddpaytaxtype = getAddPayValue[3];

            if (getAddpaytaxtype.equals("AT")) {
            } else {

                if (GlobalMemberValues.getTaxExemptItemsCountInCart() > 0
                        && GlobalMemberValues.getAllItemsCountInCart() != GlobalMemberValues.getTaxExemptItemsCountInCart()) {
                    String getAddpayname = getAddPayValue[2];

                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                            "'" + getAddpayname + "' cannot be applied if duty-free goods are included.", "Close");

                    return;
                }

                if (GlobalMemberValues.getGiftCardItemsCountInCart() > 0) {
                    if (GlobalMemberValues.getAllItemsCountInCart() != GlobalMemberValues.getGiftCardItemsCountInCart()) {
                        String getAddpayname = getAddPayValue[2];

                        GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                                "'" + getAddpayname + "' cannot be applied if gift card are included.", "Close");
                    }

                    return;
                }


            }
        }


        // 09282023
        Payment.setAddDeleteAddPay(false);

        // 05112023 -----------------------------------------------------------------------------
        // Add pay customer popup 오픈
        if (GlobalMemberValues.isOpenCustomerPopupForAddPay()) {
            Intent addpayPopup = new Intent(MainActivity.mContext.getApplicationContext(), AddPayCustomerSelectPopup.class);
            MainActivity.mActivity.startActivity(addpayPopup);
            if (GlobalMemberValues.isUseFadeInOut()) {
                MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }
        // 05112023 -----------------------------------------------------------------------------
    }



    // 05152023 - 라벨프린팅 됐는지 여부
    public static boolean isPossibleLabelPrintingByJson(JSONObject paramJson) {
        boolean returnValue = false;

        if (paramJson != null) {
            String jsonstr = paramJson.toString();
            if (!GlobalMemberValues.isStrEmpty(jsonstr)) {
                DatabaseInit dbInit;
                dbInit = MainActivity.mDbInit;
                if (dbInit == null) {
                    dbInit = new DatabaseInit(MainActivity.mContext);
                }

                int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(*) from labelprinted_json " +
                                " where stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                " and jsonstr = '" + jsonstr + "' "
                ));

                if (tempCnt > 0) {
                    returnValue = false;
                } else {
                    returnValue = true;
                }
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    // 05152023 - 라벨프린팅후 labelprinted_json 에 저장
    public static boolean getResultSaveLabelPrintingData(JSONObject paramJson) {
        boolean returnValue = false;

        if (paramJson != null) {
            String jsonstr = paramJson.toString();
            if (!GlobalMemberValues.isStrEmpty(jsonstr)) {
                DatabaseInit dbInit;
                dbInit = MainActivity.mDbInit;
                if (dbInit == null) {
                    dbInit = new DatabaseInit(MainActivity.mContext);
                }

                String returnResult = "";
                Vector<String> strInsertQueryVec = new Vector<String>();
                String strQuery = " insert into labelprinted_json ( " +
                        " scode, sidx, stcode, jsonstr " +
                        " ) values ( " +
                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                        " '" + jsonstr + "' " +
                        " ) ";
                strInsertQueryVec.addElement(strQuery);

                for (String tempQuery : strInsertQueryVec) {
                    GlobalMemberValues.logWrite("getResultSaveLabelPrintingDataJJJLog", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    returnValue = false;
                } else {
                    returnValue = true;
                }
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    public static int get_web_push_realtime_n_cnt(){

        int temp_cnt = 0;
        try {
            temp_cnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString("select count(idx) from salon_sales_web_push_realtime where viewyn = 'N' "));
        } catch (Exception e) {
            e.printStackTrace();
            temp_cnt = 0;
        }

        return temp_cnt;
    }


    // 06212023
    public static String isShowItemPriceOnReceiptForAddPay() {
        String returnValue = "Y";

        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "Y";
        if (dbInit != null) {
            getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select addpayitempriceshowyn from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "Y";
        }

        if (GlobalMemberValues.isOpenCustomerPopupForAddPay() && getValue.equals("Y")) {
            getValue = "A";
        }

        returnValue = getValue;

        return returnValue;
    }

    // 06212023
    // Label Buzzer Count
    public static int getLabelPrinterBuzzerCount(){
        SharedPreferences pref = MainActivity.mContext.getSharedPreferences("labelprinter_info", MODE_PRIVATE);
        String temp_printer_buzzer_count = pref.getString("labelprinter_info_printer_buzzer_count","1");
        return Integer.parseInt(temp_printer_buzzer_count);
    }

    // 06212023
    public static String getItemPriceRateOnReceipt(String paramValue) {
        String returnValue = "";

        switch (GlobalMemberValues.isShowItemPriceOnReceiptForAddPay()) {
            case "N" : {
                returnValue = "";
                break;
            }
            case "Y" : {
                returnValue = paramValue;
                break;
            }
            case "A" : {

                if (GlobalMemberValues.isShowCashDCValue()) {
                    returnValue = paramValue;
                } else {
                    String strQuery = "select addpaymoneytype, addpaymoney, addpaytype " +
                            " from salon_storegeneral ";
                    Cursor settingsDevicePrinterCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
                    if (settingsDevicePrinterCursor.getCount() > 0 && settingsDevicePrinterCursor.moveToFirst()) {
                        double itemPrice = GlobalMemberValues.getDoubleAtString(paramValue);


                        String addpaymoneytype = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(0), 1);
                        String addpaymoney = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(1), 1);
                        String addpaytype = GlobalMemberValues.getDBTextAfterChecked(settingsDevicePrinterCursor.getString(2), 1);

                        if (GlobalMemberValues.isStrEmpty(addpaymoneytype)) {
                            addpaymoneytype = "%";
                        }
                        if (GlobalMemberValues.isStrEmpty(addpaymoney)) {
                            addpaymoney = "0";
                        }
                        if (GlobalMemberValues.isStrEmpty(addpaytype)) {
                            addpaytype = "A";
                        }

                        if (!addpaytype.equals("A")
                                && addpaymoneytype.equals("%")
                                && GlobalMemberValues.getDoubleAtString(addpaymoney) > 0) {
                            double addPayValue = itemPrice * (GlobalMemberValues.getDoubleAtString(addpaymoney) * 0.01);

                            double getValue = 0.0;

                            if (addpaytype.equals("B")) {
                                getValue = (itemPrice - addPayValue);
                            } else {
                                getValue = itemPrice + addPayValue;
                            }

                            returnValue = GlobalMemberValues.getCommaStringForDouble(getValue + "");
                        }
                    }
                }



                break;
            }
        }

        return returnValue;
    }

    // 06212023
    public static boolean isCashInOutStartEndUse() {
        boolean returnValue = false;

        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "Y";
        if (dbInit != null) {
            getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select cashinoutstartendcashyn from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "Y";
        }

        if (getValue.equals("Y")) {
            returnValue = true;
        }

        return returnValue;
    }


    // 08012023
    public static boolean isCRMUse() {
        boolean returnValue = false;

        //  아래내용은 구현 예정
        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "Y";
        if (dbInit != null) {
            getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select crmuseyn from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "Y";
        }

        if (getValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }

        return returnValue;
    }
    public static boolean isShowCashDCValue() {
        boolean returnValue = true;

        // C 추가 110123
        if (GlobalMemberValues.getAddPayType().equals("B") || GlobalMemberValues.getAddPayType().equals("C")) {
            //  아래내용은 구현 예정
            DatabaseInit dbInit;
            dbInit = MainActivity.mDbInit;
            if (dbInit == null) {
                dbInit = new DatabaseInit(MainActivity.mContext);
            }
            String getValue = "Y";
            if (dbInit != null) {
                getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select cashdcshowonreceiptyn from salon_storegeneral"
                );
            }
            if (GlobalMemberValues.isStrEmpty(getValue)) {
                getValue = "Y";
            }

            if (getValue.equals("Y")) {
                returnValue = true;
            } else {
                returnValue = false;
            }

        } else {
            returnValue = false;
        }

        return returnValue;
    }
    public static boolean isShowTaxValue() {
        boolean returnValue = true;

        String tempValue = GlobalMemberValues.getAddPayType();
        if (tempValue.equals("B") || tempValue.equals("C")) {
            //  아래내용은 구현 예정
            DatabaseInit dbInit;
            dbInit = MainActivity.mDbInit;
            if (dbInit == null) {
                dbInit = new DatabaseInit(MainActivity.mContext);
            }
            String getValue = "Y";
            if (dbInit != null) {
                getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select cashdctaxshowyn from salon_storegeneral"
                );
            }
            if (GlobalMemberValues.isStrEmpty(getValue)) {
                getValue = "Y";
            }

            if (getValue.equals("Y")) {
                returnValue = true;
            } else {
                returnValue = false;
            }


        } else {
            returnValue = false;
        }

        return returnValue;
    }
    public static String getCashDCValue() {
        String returnValue = "";

        if (GlobalMemberValues.getAddPayType().equals("B")) {
            String[] getValue = GlobalMemberValues.getAddPayData();
            if (getValue[0].equals("$")) {
                returnValue = "(Discount " + getValue[0] + GlobalMemberValues.getDoubleAtString(getValue[1]) + ")";
            } else {
                returnValue = "(Discount " + GlobalMemberValues.getDoubleAtString(getValue[1]) + getValue[0] + ")";
            }

        } else {
            returnValue = "";
        }

        return returnValue;
    }
    public static boolean isDeleteGratuity() {
        boolean returnValue = false;

        //  아래내용은 구현 예정
        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "Y";
        if (dbInit != null) {
            getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select gratuitydelyn from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "Y";
        }

        if (getValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }


        return returnValue;
    }



    public static String getALTServiceName(String paramItemIdx) {
        String returnvalue = "";

        if (!GlobalMemberValues.isStrEmpty(paramItemIdx)) {
            String strQuery = "";
            strQuery = "select servicenamealt from salon_storeservice_sub where idx = '" + paramItemIdx + "' ";
            String getValue = MainActivity.mDbInit.dbExecuteReadReturnString(strQuery);
            if (GlobalMemberValues.isStrEmpty(getValue)) {
                getValue = "";
            } else {
                returnvalue = getValue;
            }
        }

        return returnvalue;
    }

    public static void setMainTotalOrderCountTextview(){

        String returnCode = MssqlDatabase.getResultSetValueToString("select sum(sQty) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' and not(svcIdx = '0') and not(svcName LIKE '%=======%')");

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY != null){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY.setText(returnCode);
        }
    }

    public static void getMainCustomerPeopleCnt(String mHoldCode){
        String returnCode = MssqlDatabase.getResultSetValueToString("select peoplecnt from salon_store_restaurant_table_peoplecnt where holdcode = '" + mHoldCode + "'");
        if (GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV != null){
            GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV.setText(returnCode);
        }
    }


    // 09.19.2023
    public static boolean isAddPayNameOnCustomPopup() {
        boolean returnResult = true;

        String returnValue = "Y";

        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        if (dbInit != null) {
            returnValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select addpaynameoncustompopup from salon_storegeneral"
            );
        }
        if (GlobalMemberValues.isStrEmpty(returnValue)) {
            returnValue = "Y";
        }

        if (returnValue.equals("N")) {
            returnResult = false;
        }

        return returnResult;
    }


    // 09282023
    public static int getAllItemsCountInCart() {
        int returnValue = 0;

        returnValue = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select count(*) from temp_salecart where" +
                                " holdcode = '" + MainMiddleService.mHoldCode + "' " +
                                " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') "
                )
        );

        return returnValue;
    }

    public static int getTaxExemptItemsCountInCart() {
        int returnValue = 0;

        returnValue = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select count(*) from temp_salecart where" +
                                " holdcode = '" + MainMiddleService.mHoldCode + "' " +
                                " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                                " and taxExempt = 'Y' "
                )
        );

        return returnValue;
    }

    public static int getGiftCardItemsCountInCart() {
        int returnValue = 0;

        returnValue = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(
                        " select count(*) from temp_salecart where" +
                                " holdcode = '" + MainMiddleService.mHoldCode + "' " +
                                " and not(svcName = '" + GlobalMemberValues.mCommonGratuityName + "') " +
                                " and savetype = '2' "
                )
        );

        return returnValue;
    }


    // 10272023
    public static boolean isSavingItemDeleteReason() {
        boolean returnValue = false;

        //  아래내용은 구현 예정
        DatabaseInit dbInit;
        dbInit = MainActivity.mDbInit;
        if (dbInit == null) {
            dbInit = new DatabaseInit(MainActivity.mContext);
        }
        String getValue = "N";
        if (dbInit != null) {
            getValue = MainActivity.mDbInit.dbExecuteReadReturnString(
                    "select itemdeletereasonyn from salon_storestationsettings_system"
            );
        }
        if (GlobalMemberValues.isStrEmpty(getValue)) {
            getValue = "N";
        }

        if (getValue.equals("Y")) {
            returnValue = true;
        } else {
            returnValue = false;
        }

        return returnValue;
    }


    // 11012023
    public static ArrayList<String> getItemDeleteReason() {
        ArrayList<String> returnArrayList = null;
        returnArrayList = new ArrayList<String>();

        String get_name = "";
        String tempSql = " select name from salon_storeitemdeletereason " +
                " where useyn = 'Y' " +
                " and delyn = 'N' " +
                " order by idx asc ";
        //" order by sortnum asc ";
        GlobalMemberValues.logWrite("specialrequestlog", "tempSql : " + tempSql + "\n");
        Cursor tempCursor = MainActivity.mDbInit.dbExecuteRead(tempSql);
        while (tempCursor.moveToNext()) {
            get_name = GlobalMemberValues.getDBTextAfterChecked(tempCursor.getString(0), 1);
            returnArrayList.add(get_name);
        }

        return returnArrayList;
    }

    // 11102023
    public static boolean isPossibleSavingKitchenPrintingDataJson(String paramJsonData) {
        boolean returnValue = true;
        if (!GlobalMemberValues.isStrEmpty(paramJsonData)) {
            try {
                JSONObject json = new JSONObject(paramJsonData);
                String canceldeleteyn = GlobalMemberValues.getDataInJsonData(json, "canceldeleteyn");
                String isbillprintingjjj = GlobalMemberValues.getDataInJsonData(json, "isbillprintingjjj");
                if (canceldeleteyn.equals("N") && isbillprintingjjj.equals("Y")) {
                    returnValue = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                returnValue = true;
            }
        }
        return returnValue;
    }

    // 01172024
    public static boolean isTableOrderUseYN() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String tempGetValue = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select tableorderuseyn from salon_storestationsettings_system"), 1);
        if (GlobalMemberValues.isStrEmpty(tempGetValue)) {
            tempGetValue = "N";
        }
        if (tempGetValue == "Y" || tempGetValue.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }



    // 0325204
    // T-Order 사용여부
    public static boolean isTOrderUse() {
        boolean returnValue = false;
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String getData = getDBTextAfterChecked(dbInit.dbExecuteReadReturnString(
                "select torderuseyn from salon_storegeneral"), 1);
        if (getData == "Y" || getData.equals("Y")) {
            returnValue = true;
        }
        return returnValue;
    }
    // T-Order Partner Key
    public String getTOrderKey() {
        String returnData = "";
        returnData = getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select torderkey from salon_storegeneral "), 1);
        if (GlobalMemberValues.isStrEmpty(returnData)) {
            returnData = "";
        }
        return returnData;
    }
}