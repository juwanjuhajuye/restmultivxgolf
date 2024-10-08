package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.usb.UsbManager;
import android.icu.util.Calendar;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.sdk.v1.printer.PrinterConnector;
import com.clover.sdk.v3.connector.IDisplayConnector;
import com.clover.sdk.v3.connector.IDisplayConnectorListener;
import com.clover.sdk.v3.order.DisplayLineItem;
import com.clover.sdk.v3.order.DisplayOrder;
import com.clover.sdk.v3.scanner.BarcodeResult;
import com.clover.sdk.v3.scanner.BarcodeScanner;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.BillSplitMerge;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.EmployeeKeyIn;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ActivityMonitor;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapterFactory;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.serial.FTDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TablePeopleCnt;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleBillPrint;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSplittedList;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.elo.device.DeviceManager;
import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;


import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.pax.poslink.peripheries.POSLinkScanner;

//import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {
    public static Activity mActivity;

    private final String dbName = "JJJQSRDB";
    private DatabaseHandler dbHandler;

    public static Context mContext;

    public static GetDataAtSQLite dataAtSqlite;

    public static ProgressDialog proDial;

    public static ProgressDialog proDial2;

    public static int mTempFlag = 0;

    private boolean mDbTableUpdate = false;

    // GCM 관련
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    // Notification 관련
    public static boolean isOpenApplication = false;

    private static final String TAG = "MainActivityLog";

    public static DatabaseInit mDbInit;

    static String resultPhoneCallPermission = "N";
    static String resultReadStoragePermission = "N";
    static String resultWriteStoragePermission = "N";

    public static String deviceUsableCheckResult = "00";

    public static final int CALL_ZXING_RESULT_DISCOUNT = 1234;
    public static final int CALL_MAINACTIVITY_TABLESALE = 1235;

    public static String mCloseAlert = "Y";

    public static String mTimerPossible = "N";

    // Elo 관련 ---------------------------------------------
    public static ProductInfo mProductInfo;
    public static ApiAdapter mApiAdapter;

    public enum PrinterMake {mSTAR, mRONGTA};
    public static PrinterMake mMyPrinter;

    public static TextView bottomTimeMenuTv;

    public static ProgressDialog proCustomDial;
    // ------------------------------------------------------

    // Clover 관련 ------------------------------------------
    // 1-1. 바코드 스캐너 관련 선언
    public static BarcodeScanner barcodeScanner_clover;
    public static BroadcastReceiver barcodeReceiver_clover;

    // 2-1. 프린터를 관련 선언
    public static Account account_clover;
    public static PrinterConnector printerConnector_clover;

    // 3-1. 디스플레이 관련 선언
    public static IDisplayConnector displayConnector_clover;
    public static List<DisplayLineItem> items_clover = new ArrayList<DisplayLineItem>();
    public static DisplayOrder displayOrder_clover = new DisplayOrder();

    public static AssetManager mAsset = null;

    // 프린트 용 폰트
    public static Typeface mTypeFace_clover;
    // ------------------------------------------------------

    // jihun park subdisplay
    public static PaxPresentation mPaxPresentation;
    public static MediaRouter mMediaRouter;

    //jihun pax scanner
    public static POSLinkScanner posLinkScanner;

    /** 알수없는 예외처리 */
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    LinearLayout directButtonsLinearLayout, directButtonsLinearLayout2;
    Button saveorderButton;


    // 저울 시리얼 연동관련 ---------------------------------------------------
    public static FTDriver mSerial;
    public static UsbReceiver mUsbReceiver;

    public static final String ACTION_USB_PERMISSION = "kr.co.andante.mobiledgs.USB_PERMISSION";

    public static Boolean SHOW_DEBUG = false;

    public static int mBaudrate;

    public static String scaleTypeStr = "W";
    // ------------------------------------------------------------------------

    // 블루투스
    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    public static final int REQUEST_ENABLE_BT = 3;

    public static BlueToothPrinterService mBluetooth_PrinterService = null;
    public static BluetoothAdapter mBluetoothAdapter = null;
    public static String mConnectedDeviceName = null;

    public static boolean b_bluetooth_connected_YN = false;

    public static Handler handler_loading_popup = new Handler();

    InformationPopup informationPopup;

    private JJJ_CustomAlert customAlert;

    private PopupIpInput popupIpInput;

    static String temp_str_salecart = "";
    static int temp_str_salecart_cnt = 0;

    static boolean b_customer_place_to_order = false;

    //
    RelativeLayout main_grid_relative_view;
    LinearLayout main_grid_relative_view_loading;
    Animation Quick_LeftAnim;
    Animation Quick_RightAnim;
    public RecyclerView quick_table_grid_list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         // 알수없는 예외처리 ---------------------------------------------------------------------
         mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
         if (!(mUncaughtExceptionHandler instanceof UncaughtExceptionHandlerApplication)) {
         Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerApplication());
         }
         // ---------------------------------------------------------------------------------------
         **/

        // 선택된 zone 초기화
        GlobalMemberValues.mGlobal_selectedZoneIdx = "";

        // 시스템 재시작 관련 ----------------------------------------------------------------------
        Intent errorIntent = getIntent();
        String tempErrorMsg = "";
        if (errorIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            tempErrorMsg = errorIntent.getStringExtra("stoperrormsg");
            /*******************************************************************************************/

            if (!GlobalMemberValues.isStrEmpty(tempErrorMsg)) {
                GlobalMemberValues.displayDialog(this.getApplicationContext(), GlobalMemberValues.ANDROIDPOSNAME,
                        "The system has been restarted for the following reason\n(" + tempErrorMsg + ")", "Close");
            }

            //GlobalMemberValues.APPOPENCOUNT++;
        }
        // ---------------------------------------------------------------------------------------

        // 액션바 안보이게
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        mActivity = this;
        mContext = this;

        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        GlobalMemberValues.setActivityOrientation(this, this);
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        // app kill service
        startService(new Intent(this, ForecdTerminationService.class));

        // 상단 Status Bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (GlobalMemberValues.isLiteVersion()) {
            setContentView(R.layout.activity_main_lite);
        } else if (GlobalMemberValues.is_customerMain){
            setContentView(R.layout.activity_main_customer);
        } else {
            setContentView(R.layout.activity_main);
        }

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = dm.widthPixels;

        int height = dm.heightPixels;

        // Notification 관련 isOpenApplication 값 설정 --------------------------
        isOpenApplication = true;
        // ---------------------------------------------------------------------

        mDbInit = new DatabaseInit(this);   // DatabaseInit 객체 생성

        // mssql 연결 및 테이블 생성 ---------------------------------------------------------------------------
        GlobalMemberValues.setConnectMSSQL();                           // 연결

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            GlobalMemberValues.logWrite("mssqllog", "mssql 여기진입..." + "\n");

            if (GlobalMemberValues.mssql_ip.equals("0.0.0.0")){
                GlobalMemberValues.logWrite("mssqllog", "mssql ip null!" + "\n");
            } else {
                if (GlobalMemberValues.setConnectMssql()) {
                    // 테이블 생성
                    MssqlCreateTables.createTablesForMSSQL();
                    // 테이블 수정
                    MssqlAlterTables.alterTablesForMSSQL();
                }
            }

        }
        // --------------------------------------------------------------------------------------------------

        // 권한 확인 및 설정 ----------------------------------------------------------------------------------
        // 파일 쓰기
        //checkAppPermission(mActivity, mContext, "FILEWRITE");
        // --------------------------------------------------------------------------------------------------

        //바탕화면 아이콘 생성 (shortcut) 관련 -----------------------------------------------------------------
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        pref.getString("check", "");
        if(pref.getString("check", "").isEmpty()) {
            Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
            shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            shortcutIntent.setClassName(this, getClass().getName());
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher));
            intent.putExtra("dupl", false);
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            sendBroadcast(intent);
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("check", "exist");
        editor.commit();
        // ---------------------------------------------------------------------------------------------------

        // os 버전 체크
        // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
        GlobalMemberValues.setFileAccessPermission(mActivity, mContext);

        // 초기 해상도 구하기 ----------------------------------------------------------------------------------
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        if (metrics.widthPixels > metrics.heightPixels) {
            GlobalMemberValues.thisTabletRealWidth = metrics.heightPixels;
            GlobalMemberValues.thisTabletRealHeight = metrics.widthPixels;
        } else {
            GlobalMemberValues.thisTabletRealWidth = metrics.widthPixels;
            GlobalMemberValues.thisTabletRealHeight = metrics.heightPixels;
        }

        GlobalMemberValues.logWrite("mainwidthheight", "width : " + GlobalMemberValues.thisTabletRealWidth + "\n");
        GlobalMemberValues.logWrite("mainwidthheight", "height : " + GlobalMemberValues.thisTabletRealHeight + "\n");
        // ---------------------------------------------------------------------------------------------------

        // 인터넷 상태 체크 TextView
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS = (TextView)findViewById(R.id.topNetworkStatus);
        // 최초 인터넷 체크
        GlobalMemberValues.GLOBALNETWORKSTATUS = NetworkUtil.getConnectivityStatus(mContext);

        // Settings 값 가져와서 Global 변수에 담기
        setSettingsInformationInGlobalValues();

        // 화면 디스플레이 계속 켜짐 유지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 앱 Instance ID 가 없을 때 가져온다.
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.APP_INSTANCE_ID)) {
            GlobalMemberValues.getInstanceIdToken();
        }

        //스플래쉬 액티비티를 띄운다.
        if (GlobalMemberValues.SPLASHCOUNT == 0) {
            if (GlobalMemberValues.GLOBAL_SPLASHUSE == 0) {
                startActivity(new Intent(this, Splash.class));
            }

            GlobalMemberValues.SPLASHCOUNT++;

            mDbTableUpdate = true;
        }
        GlobalMemberValues.logWrite("APPOPENCOUNT", "APPOPENCOUNT : " + GlobalMemberValues.APPOPENCOUNT + "\n");

        // setDatabaseAndApiDataDownloadThread(int paramStatus, int actionType)
        // paramStatus       --- 0 : 메소드 실행             1 : 실행안함
        // actionType        --- 0 : DB 테이블 먼저 삭제     1 : 삭제안함
        GlobalMemberValues.MSYNCDATATYPE = "MAIN";

        // 현재 시간대 코드값을 설정한다.
        GlobalMemberValues.setTimeMenuCodeValue("a");

        // GlobalMemberValues.GLOBAL_DOWNLOADDATA 의 값이
        // 0 이면 다운로드버튼 클릭시         1 이면 앱 오픈시 에
        // 클라우드에서 데이터를 다운로드 하기 때문에
        // 값이 0 일경우 다운로드버튼 클릭시에만 데이터를 다운로드 하므로
        // APPOPENCOUNT 를 증가시켜 오픈시에 데이터를 다운로드 하지 않도록 한다.
        if (GlobalMemberValues.GLOBAL_DOWNLOADDATA == 0) {
            GlobalMemberValues.APPOPENCOUNT++;
        }

        // Elo 관련
        setEloInit();

        setDatabaseAndApiDataDownloadThread(GlobalMemberValues.APPOPENCOUNT, GlobalMemberValues.INSERTDATAAFTERDELETE);

        GlobalMemberValues.setCloudKitchenPrintingValue();

        //jihun park sub display
        mMediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);

        // printer 재연결
        Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
        giantPrinter.kitchenPrint_connect_test();


        // socket port 번호 있을때 setserver!
        //GlobalMemberValues.setSocketServer();

        // 블루투스
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetooth_PrinterService = new BlueToothPrinterService(getApplicationContext(), mHandler);

        // 타임메뉴 셋
        timeMenu_getServiceTime();

        //07032024 before connection set database ip address
        String tempSqlQuery = "select mssqldbip, databasename, databasepass, mobilehost, cloudhost from salon_storestationsettings_system";
        Cursor settingsSystemCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        String tempIp = "";
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            tempIp = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);

            if (GlobalMemberValues.isStrEmpty(tempIp)) {
                tempIp = "0.0.0.0";
            } else {
                GlobalMemberValues.mssql_ip = tempIp;
            }
        }

        // 092022
        // 로그 DB 특정기간 삭제
        LogsSave.setInitDB();
        // 로그 날짜 확인후 삭제 // 기능 넣기

        // App 자동 종료 스케쥴러.
        AppCloseScheduler appCloseScheduler = new AppCloseScheduler();
        appCloseScheduler.scheduleTask();

        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        //new ProgressTask(progressBar).execute();

        ///////
        main_grid_relative_view = (RelativeLayout)findViewById(R.id.main_grid_relative_view);
        main_grid_relative_view_loading = (LinearLayout)findViewById(R.id.main_grid_relative_view_loading);

        Quick_LeftAnim = AnimationUtils.loadAnimation(this, R.anim.act_out_left);
        Quick_RightAnim = AnimationUtils.loadAnimation(this, R.anim.act_in_right);
        MainActivity.SlidingTogoViewAnimationListener slidingTogoViewAnimationListener = new MainActivity.SlidingTogoViewAnimationListener();
        Quick_LeftAnim.setAnimationListener(slidingTogoViewAnimationListener);
        Quick_RightAnim.setAnimationListener(slidingTogoViewAnimationListener);
        quick_table_grid_list = (RecyclerView)findViewById(R.id.main_quick_table_gridview);
    }

    public static void setEloInit() {
        try {
            mProductInfo = DeviceManager.getPlatformInfo();
            if (mProductInfo == null) {
                return;
            }
            EloPlatform platform = mProductInfo.eloPlatform;
            mApiAdapter = ApiAdapterFactory.getInstance(mContext).getApiAdapter(platform);
            if (mApiAdapter == null) {
                // We're not running on a supported platform.  This is a common customer scenario where
                // the APK may be written for multiple vendor platforms.  In our case, we can't do much
                //Toast.makeText(mContext, "Cannot find support for this platform", Toast.LENGTH_LONG).show();
                return;
            } else {
                GlobalMemberValues.mDeviceEloYN = "Y";
            }

            switch (platform) {  // TODO: Base off detection, not platform
                case PAYPOINT_1:
                case PAYPOINT_REFRESH:
                    mMyPrinter = PrinterMake.mRONGTA;
                    break;
                case PAYPOINT_2:
                    mMyPrinter = PrinterMake.mSTAR;
                    break;
                default:
                    mMyPrinter = null;
            }
        } catch (Exception e) {
            GlobalMemberValues.displayDialog(mContext, "ERROR", e.getMessage().toLowerCase(), "Close");
        }
    }

    protected void setDatabaseAndApiDataDownloadThread(int paramStatus, int actionType) {
        final int PARAMSTATUS = paramStatus;
        final int PARAMACTIOINTYPE = actionType;

        GlobalMemberValues.logWrite(TAG, "프로그래스바 실행전... \n");
        if ((mActivity != null) && (!mActivity.isFinishing())) {
            // 프로그래스 바를 실행~
            proDial = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, GlobalMemberValues.DOWNLOAD_PROGRESS, true, false);
            GlobalMemberValues.logWrite(TAG, "프로그래스바 실행후... \n");
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 화면재개 지연시간 초기화
                GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                // DB 생성 및 처리 관련 메소드
                // setDatabaseAndApiDataDownload(int paramStatus, int actionType)
                // paramStatus       --- 0 : 메소드 실행             1 : 실행안함
                // actionType        --- 0 : DB 테이블 먼저 삭제     1 : 삭제안함
                setDatabaseAndApiDataDownload(PARAMSTATUS, PARAMACTIOINTYPE, 0);
                // ---------------------------------------------------------------------------------

                GlobalMemberValues.logWrite("screendelaytimelog", "여기..........2 : " + GlobalMemberValues.RESTARTSCREEN_DELYTIME);

                try {
                    Thread.sleep(GlobalMemberValues.getIntAtString(GlobalMemberValues.RESTARTSCREEN_DELYTIME));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    /** Database 초기화 및 API 데이터 다운로드 및 등록 **********************************/
    public static void setDatabaseAndApiDataDownload(int paramStatus, int actionType, int internetCheckType) {
        if (paramStatus == 0 && NetworkUtil.getConnectivityStatus(mContext) > 0) {
            DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
            /**
             if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
             if (actionType == 0) {
             switch (GlobalMemberValues.DROPTABLETYPE) {
             case 0 : {
             dbInit.dropAllDatabaseTables(GlobalMemberValues.dbTableNameGroup);           // 전체 테이블 삭제
             break;
             }
             case 1 : {
             dbInit.dropAllDatabaseTables(GlobalMemberValues.dbTableNameGroupForDrop);    // 클라우드 관련 테이블만 삭제
             break;
             }
             }
             // .................................................
             }
             }
             **/

            int basicTableCnt = dbInit.checkTable("basic_pos_information");
            if (basicTableCnt > 0) {        // 이미 승인받은 경우이므로
                // dbInit.dropAllDatabaseTables(GlobalMemberValues.dbTableNameGroupForDrop);    // 클라우드 관련 테이블만 삭제
            } else {
                dbInit.dropAllDatabaseTables(GlobalMemberValues.dbTableNameGroup);           // 전체 테이블 삭제
            }

            dbInit.initDatabaseTables();                    // 데이터베이스 생성 및 테이블 생성, 추가, 삭제 및 컬럼 추가, 삭제

            int tempReturnInt = setBasicInformationInit();
            if (tempReturnInt == 0) {
                openIntroRealDemoChoice();
            } else {
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    // 먼저 salon_member 의 고객포인트를 임시저장소(temp_mileagecart) 에 저장한다.
                    boolean tempBoolean = GlobalMemberValues.saveTempMileageCart();
                    if (!tempBoolean) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Error", "Close");
                        return;
                    }

                    // dbInit.insertDatabaseAfterAPIDownload();        // 데이터베이스 테이블 데이터 등록 (insert)  --> 이전에 사용하던것
                    // dbInit.insertDatabaseAfterAPIDownloadByItemData(테이블 Array, 테이블 그룹명)
                    // 전체 다운로드시 서비스 이미지도 다운로드 해야 하므로
                    // "테이블 그룹명" 에 service 를 할당......
                    dbInit.insertDownloadDataInDatabase(GlobalMemberValues.dbTableNameGroupForApi, "service", internetCheckType);
                }
                setBasicInformationInit();
                setStoreInformationInit();                         // 스토어 및 시스템정보를 초기화한다.
                setSettingsInformation();

                dbInit.closeDBHanlder();
                //GlobalMemberValues.logWrite("MainActivity", "정상완료\n");
            }

            mTempFlag = 0;
        } else {
            GlobalMemberValues.logWrite("mainActivityLog", "여기 시작" + "\n");
            DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
            int basicTableCnt = dbInit.checkTable("basic_pos_information");
            if (basicTableCnt > 0) {        // 이미 승인받은 경우이므로
                mTempFlag = 0;
                GlobalMemberValues.logWrite("mainActivityLog", "승인되었음" + "\n");
                setBasicInformationInit();
                setStoreInformationInit();                         // 스토어 및 시스템정보를 초기화한다.
                setSettingsInformation();
            } else {
                mTempFlag = 1;
                openIntroRealDemoChoice();
            }
        }
    }
    /*******************************************************************************/

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlag == 0) {
                GlobalMemberValues.logWrite("SendDataToTOrderEvent", "api download done!");
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                setCommonInit();
                // -------------------------------------------------------------------------------------

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GlobalMemberValues.copyMileageFromTempMileageCart();

                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
                proDial.dismiss();
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
                // -------------------------------------------------------------------------------------

                //06032024 Send data to TOrder after download completes
                if(GlobalMemberValues.isTOrderUse()){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SendDataToTOrderEvent tOrderDataSender = new SendDataToTOrderEvent();
                    tOrderDataSender.execute();
                }
            }
        }
    };

    public void setCommonInit() {
        setMainProcess();
        setBasicInformationInit();
        setStoreInformationInit();
        setSettingsInformation();

        // 직원 로그인 체크
        checkEmployeeLoginStatus();

        // Elo CFD 스크린 초기화
        GlobalMemberValues.eloCfdScreenViewInit();

        // 타임메뉴 사용여부
        GlobalMemberValues.setTimeMenuUseYN();

        // 타임메뉴 체크 시간 설정
        GlobalMemberValues.setTimeMenuCheckTime();

        // 타임메뉴 설정창 자동오픈 여부값 설정
        GlobalMemberValues.setTimeMenuSetAutoOpenValue();

        // 현재 타임메뉴(TimeMenu) 시간대를 설정한다.
        GlobalMemberValues.setSelectedTimeCodevalue();

        // 인터넷 상태 체크
        runTimer_checkOnline();

        // 타이머를 돌려 설정한 오전/오후/저녁/밤에 따라 메인 액티비티를 새로 시작한다.
        if (GlobalMemberValues.TIMEMENUSET_AUTOOPEN.equals("Y") || GlobalMemberValues.TIMEMENUSET_AUTOOPEN == "Y"
                || GlobalMemberValues.TIMEMENUSET_AUTOOPEN.equals("A") || GlobalMemberValues.TIMEMENUSET_AUTOOPEN == "A") {
            runTimer_timemenu();
        }

        // 현재 시간대 코드값을 설정한다.
        GlobalMemberValues.setTimeMenuCodeValue("a");

        // 신규 웹오더 체크
        if (GlobalMemberValues.isOnlineOrderUseYN()) {
            runTimer_newWebOrder();
        }

        // 01172024
        // 신규 테이블오더 체크
        if (GlobalMemberValues.isTableOrderUseYN()) {
            runTimer_newTableOrder();
        }

        GlobalMemberValues gm = new GlobalMemberValues();

        if (GlobalMemberValues.isOnlineOrderUseYN() && gm.isCurbsideOrder()) {
            runTimer_pushCurside();
        }

        if (GlobalMemberValues.isOnlineOrderUseYN() && gm.isSideMenuOrder()) {
            runTimer_pushNewSideMenu();
        }

        if (GlobalMemberValues.isOnlineOrderUseYN() && gm.isPOSWebPay()) {
            runTimer_newTablePay();
        }

        // 멀티 프린팅시 주방프린터 데이터 클라우드 전송
        if (GlobalMemberValues.isCloudKitchenPrinter()) {
            runTimer_cloudKitchenPrinter();
        }

        GlobalMemberValues.APPOPENCOUNT++;

        GlobalMemberValues.setBooleanDevice();

        //04182024 Send POST request to TOrder that device program started
        if(GlobalMemberValues.isTOrderUse()){
            GlobalMemberValues.sendTOrderAPIProgramStart();
        }

    }

    public static void runTimer_checkOnline() {
        /**
         String tempDeviceKind = mDbInit.dbExecuteReadReturnString("select devicekind from salon_storestationsettings_system");
         if (GlobalMemberValues.isStrEmpty(tempDeviceKind)) {
         tempDeviceKind = "";
         }

         if (tempDeviceKind == "Elo" || tempDeviceKind.equals("Elo")) {
         // 30초마다 타이머를 돌린다. ----------------------------------------------------------------
         TimerTask tt_checkonline = new TimerTask() {
        @Override
        public void run() {
        Message message = timerhandler_checkOnline.obtainMessage();
        timerhandler_checkOnline.sendMessage(message);
        }
        };

         /////////// / Timer 생성 //////////////
         Timer timer = new Timer();
         timer.schedule(tt_checkonline, 0, 3000);
         //////////////////////////////////////
         // ----------------------------------------------------------------------------------------
         GlobalMemberValues.TIMER_ONLINECHECK_START_YN = "Y";
         } else {
         GlobalMemberValues.TIMER_ONLINECHECK_START_YN = "N";
         }
         **/
        if (GlobalMemberValues.TIMER_ONLINECHECK_START_YN == "N" || GlobalMemberValues.TIMER_ONLINECHECK_START_YN.equals("N")) {
            // 10초마다 타이머를 돌린다. ----------------------------------------------------------------
            TimerTask tt_checkonline = new TimerTask() {
                @Override
                public void run() {
                    Message message = timerhandler_checkOnline.obtainMessage();
                    timerhandler_checkOnline.sendMessage(message);
                }
            };

            /////////// / Timer 생성 //////////////
            Timer timer = new Timer();
            timer.schedule(tt_checkonline, 0, 30000);
            //////////////////////////////////////
            // ----------------------------------------------------------------------------------------
            GlobalMemberValues.TIMER_ONLINECHECK_START_YN = "Y";
        }
    }

    public static final Handler timerhandler_checkOnline = new Handler() {
        public void handleMessage(Message msg) {
            /**
             String tempDeviceKind = mDbInit.dbExecuteReadReturnString("select devicekind from salon_storestationsettings_system");
             if (GlobalMemberValues.isStrEmpty(tempDeviceKind)) {
             tempDeviceKind = "";
             }
             if (tempDeviceKind == "Elo" || tempDeviceKind.equals("Elo")) {
             //GlobalMemberValues.displayDialog(mContext, "Service", "Online Check Service...", "Close");
             // 인터넷 연결 체크
             GlobalMemberValues.checkOnlineService(mContext, mActivity);
             }
             **/

            // 인터넷 연결 체크
            GlobalMemberValues.checkOnlineService(mContext, mActivity);
        }
    };

    public void runTimer_timemenu() {
        // 설정된 분마다 타이머를 돌린다. -------------------------------------------------------------
        TimerTask tt_timemenu = new TimerTask() {
            @Override
            public void run() {
                //Log.e("1번태스크카운터:", String.valueOf(counter));
                //counter++;

                Message message = timerhandler_timemenu.obtainMessage();
                timerhandler_timemenu.sendMessage(message);
            }
        };

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES + "")
                || GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES == 0) {
            GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES = 10;
        }

        /////////// / Timer 생성 //////////////
        Timer timer = new Timer();
        timer.schedule(tt_timemenu, 0, (GlobalMemberValues.TIMEMENUCHECKTIME_MINUTES * 60000));
        //////////////////////////////////////
        // ----------------------------------------------------------------------------------------
        GlobalMemberValues.TIMER_TIMEMENU_START_YN = "Y";
    }

    final Handler timerhandler_timemenu = new Handler()
    {
        public void handleMessage(Message msg)
        {
//            GlobalMemberValues.openTimeMenuSelectPopupAuto();
//            GlobalMemberValues.logWrite("autotimemenulog", "여기실행... nowTimeCodeValue : " + GlobalMemberValues.NOW_TIME_CODEVALUE + "\n");
        }
    };

    public static void runTimer_newWebOrder() {
        String tempPushtype = mDbInit.dbExecuteReadReturnString("select pushtype from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(tempPushtype)) {
            tempPushtype = "1";
        }

        if (tempPushtype == "2" || tempPushtype.equals("2")) {
            // 30초마다 타이머를 돌린다. ----------------------------------------------------------------
            if (false) {
                Message message = timerhandler_newWebOrder.obtainMessage();
                timerhandler_newWebOrder.sendMessage(message);
            } else {
                TimerTask tt_checkweborder = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = timerhandler_newWebOrder.obtainMessage();
                        timerhandler_newWebOrder.sendMessage(message);
                    }
                };
                ///////// / Timer 생성 //////////////
                Timer timer = new Timer();
                timer.schedule(tt_checkweborder, 0, 30000);
                ////////////////////////////////////
            }

            // ----------------------------------------------------------------------------------------
            GlobalMemberValues.TIMER_ONLINEORDERSCHECK_START_YN = "Y";
        } else {
            GlobalMemberValues.TIMER_ONLINEORDERSCHECK_START_YN = "N";
        }
    }

    public static final Handler timerhandler_newWebOrder = new Handler() {
        public void handleMessage(Message msg)
        {
            String tempPushReceiveYN = mDbInit.dbExecuteReadReturnString(
                    "select pushreceiveyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ");
            if (GlobalMemberValues.isStrEmpty(tempPushReceiveYN)) {
                tempPushReceiveYN = "Y";
            }
            GlobalMemberValues.logWrite("pushreceivelog", "tempPushReceiveYN : " + tempPushReceiveYN + "\n");
            // 해당 스테이션이 푸시메시지를 받는다는 설정일 때만...
            if (tempPushReceiveYN == "Y" || tempPushReceiveYN.equals("Y")) {
                String tempPushtype = mDbInit.dbExecuteReadReturnString("select pushtype from salon_storestationsettings_system");
                GlobalMemberValues.logWrite("pushreceivelog", "tempPushtype : " + tempPushtype + "\n");
                if (GlobalMemberValues.isStrEmpty(tempPushtype)) {
                    tempPushtype = "1";
                }
                if (tempPushtype == "2" || tempPushtype.equals("2")) {
                    // 신규 웹오더 체크
                    GlobalMemberValues.checkNewWebOrdersService(mContext, mActivity);
                }
            }
        }
    };



    // 01172024
    public static void runTimer_newTableOrder() {
        // 30초마다 타이머를 돌린다. ----------------------------------------------------------------
        if (false) {
            Message message = timerhandler_newTableOrder.obtainMessage();
            timerhandler_newTableOrder.sendMessage(message);
        } else {
            TimerTask tt_checktableorder = new TimerTask() {
                @Override
                public void run() {
                    Message message = timerhandler_newTableOrder.obtainMessage();
                    timerhandler_newTableOrder.sendMessage(message);
                }
            };
            ///////// / Timer 생성 //////////////
            Timer timer = new Timer();
            timer.schedule(tt_checktableorder, 0, 5000);
            ////////////////////////////////////
        }

        // ----------------------------------------------------------------------------------------
        GlobalMemberValues.TIMER_ONLINEORDERSCHECK_START_YN = "Y";
    }
    // 01172024
    public static final Handler timerhandler_newTableOrder = new Handler() {
        public void handleMessage(Message msg)
        {
            // 신규 Table Order 체크
            GlobalMemberValues.checkTableOrder(mContext, mActivity);
        }
    };




    public static void runTimer_newTablePay() {
        GlobalMemberValues gm = new GlobalMemberValues();
        if (gm.isPOSWebPay()) {
            // 초마다 타이머를 돌린다. ----------------------------------------------------------------
            TimerTask tt_checktablepay = new TimerTask() {
                @Override
                public void run() {
                    Message message = timerhandler_newTablePay.obtainMessage();
                    timerhandler_newTablePay.sendMessage(message);
                }
            };

            /////////// / Timer 생성 //////////////
            Timer timer = new Timer();
            timer.schedule(tt_checktablepay, 0, 5000);
            //////////////////////////////////////
            // ----------------------------------------------------------------------------------------
            GlobalMemberValues.TIMER_TABLEPAY_START_YN = "Y";
        } else {
            GlobalMemberValues.TIMER_TABLEPAY_START_YN = "N";
        }
    }

    public static final Handler timerhandler_newTablePay = new Handler() {
        public void handleMessage(Message msg)
        {
            GlobalMemberValues gm = new GlobalMemberValues();
            GlobalMemberValues.logWrite("newtablepaystr", "isOrdersInTable() : " + gm.isOrdersInTable() + "\n");
            // 해당 스테이션이 푸시메시지를 받는다는 설정일 때만...
            if (gm.isPOSWebPay() && gm.isOrdersInTable()) {
                // 신규 Table Pay 체크
                GlobalMemberValues.checkNewTablePay(mContext, mActivity);
            }
        }
    };


    public static void runTimer_pushCurside() {
        GlobalMemberValues gm = new GlobalMemberValues();
        if (gm.isCurbsideOrder()) {
            TimerTask tt_checkweborder = new TimerTask() {
                @Override
                public void run() {
                    Message message = timerhandler_pushCurside.obtainMessage();
                    timerhandler_pushCurside.sendMessage(message);
                }
            };

            /////////// / Timer 생성 //////////////
            Timer timer = new Timer();
            timer.schedule(tt_checkweborder, 0, 15000);
            //////////////////////////////////////

            GlobalMemberValues.TIMER_CURBSIDE_START_YN = "Y";
        } else {
            GlobalMemberValues.TIMER_CURBSIDE_START_YN = "N";
        }
    }

    public static final Handler timerhandler_pushCurside = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String tempPushReceiveYN = mDbInit.dbExecuteReadReturnString(
                    "select pushreceiveyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE + "' ");
            if (GlobalMemberValues.isStrEmpty(tempPushReceiveYN)) {
                tempPushReceiveYN = "Y";
            }
            GlobalMemberValues.logWrite("pushreceivelog", "tempPushReceiveYN : " + tempPushReceiveYN + "\n");
            // 해당 스테이션이 푸시메시지를 받는다는 설정일 때만...
            if (tempPushReceiveYN == "Y" || tempPushReceiveYN.equals("Y")) {
                String tempPushtype = mDbInit.dbExecuteReadReturnString("select pushtype from salon_storestationsettings_system");
                GlobalMemberValues.logWrite("pushreceivelog", "tempPushtype : " + tempPushtype + "\n");
                if (GlobalMemberValues.isStrEmpty(tempPushtype)) {
                    tempPushtype = "1";
                }
                if (tempPushtype == "2" || tempPushtype.equals("2")) {
                    GlobalMemberValues gm = new GlobalMemberValues();
                    if (gm.isCurbsideOrder()) {
                        GlobalMemberValues.checkCursideService(mContext,mActivity);
                    }
                }
            }
        }
    };

    public static void runTimer_pushNewSideMenu() {
        GlobalMemberValues gm = new GlobalMemberValues();
        if (gm.isSideMenuOrder()) {
            TimerTask tt_checkSideMenu = new TimerTask() {
                @Override
                public void run() {
                    Message message = timerhandler_newSideMenuOrder.obtainMessage();
                    timerhandler_newSideMenuOrder.sendMessage(message);
                }
            };

            /////////// / Timer 생성 //////////////
            Timer timer = new Timer();
            timer.schedule(tt_checkSideMenu, 0, 15000);
            //////////////////////////////////////

            GlobalMemberValues.TIMER_SIDEMENU_START_YN = "Y";
        } else {
            GlobalMemberValues.TIMER_SIDEMENU_START_YN = "N";
        }
    }

    public static final Handler timerhandler_newSideMenuOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            GlobalMemberValues gm = new GlobalMemberValues();
            if (gm.isSideMenuOrder()) {
                GlobalMemberValues.checkNewSideOrderService(mContext,mActivity);
            }
        }
    };

    public static void runTimer_cloudKitchenPrinter() {
        // 5초마다 타이머를 돌린다. ----------------------------------------------------------------
        TimerTask tt_cloudkitchenprinter = new TimerTask() {
            @Override
            public void run() {
                Message message = timerhandler_cloudkitchenprinter.obtainMessage();
                timerhandler_cloudkitchenprinter.sendMessage(message);
            }
        };

        /////////// / Timer 생성 //////////////
        Timer timer = new Timer();
        timer.schedule(tt_cloudkitchenprinter, 0, 300000);
        //////////////////////////////////////
        // ----------------------------------------------------------------------------------------
    }

    public static final Handler timerhandler_cloudkitchenprinter = new Handler() {
        public void handleMessage(Message msg)
        {
            //String tempCloudKitchenPrinterUrl = GlobalMemberValues.getCloudKitchenPrinterUrl();
            //GlobalMemberValues.logWrite("cloudkitchenprinterurllog", "tempCloudKitchenPrinterUrl : " + tempCloudKitchenPrinterUrl + "\n");
            // 클라우드 주방프린터 일경우 아래 실행
            GlobalMemberValues.logWrite("cloudkitchenprinterurllog", "여기실행1" + "\n");
            if (GlobalMemberValues.CLOUDKITCHENPRINTING.equals("Y")) {
                if (GlobalMemberValues.isPossibleMssqlInfo()) {

                } else {
                    // 주방프린터 데이터 클라우드 전송
                    GlobalMemberValues.sendKitchenPrintingDataToCloud(mContext, mActivity);
                    GlobalMemberValues.logWrite("cloudkitchenprinterurllog", "여기실행2" + "\n");
                }
            }
        }
    };

    public static void openIntroRealDemoChoice() {
        // 230530 크래시 수정
        Context context = null;
        if (MainActivity.mActivity.getApplicationContext() == null){
            context = MainActivity.mContext.getApplicationContext();
        } else {
            context = MainActivity.mActivity.getApplicationContext();
        }
        Intent introRealDemoIntent = new Intent(context, IntroRealDemoChoice.class);
        // 230530
        mActivity.startActivity(introRealDemoIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
        mActivity.finish();
    }

    public static void checkEmployeeLoginStatus() {
        // 승인받은 스테이션인지 체크
        if (GlobalMemberValues.STATION_CODE == "") {
            openIntroRealDemoChoice();
            return;
        }

        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.LOGIN_EMPLOYEE_ID)) {
            GlobalMemberValues.setCustomerInfoInit();
            GlobalMemberValues.setEmployeeInfoInit();
            GlobalMemberValues.ITEMCANCELAPPLY = 0;
            MainMiddleService.mHoldCode = "NOHOLDCODE";

            if (!GlobalMemberValues.b_isLoginView){
                Intent intentEmployeeLogin = new Intent(mContext.getApplicationContext(), Employee_Login.class);
//                mActivity.startActivity(intentEmployeeLogin);
                mActivity.startActivityForResult(intentEmployeeLogin, CALL_MAINACTIVITY_TABLESALE);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
            }

        } else {
            if (!GlobalMemberValues.LOGIN_EMPLOYEE_ID.toUpperCase().equals("ADMIN")) {
                GlobalMemberValues.openCashInOutPopup(mActivity, mContext.getApplicationContext());
            }
        }

        MainMiddleService.setEmptyInSaleCart(true);
    }

    public static void setEmployeeLogout() {
        if ((mActivity != null) && (!mActivity.isFinishing())) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("Employee Logout")
                    .setMessage("Do you want to logout?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            employeeLogout();
                        }
                    }).show();
        }
    }

    public static void employeeLogout() {
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            GlobalMemberValues.LOGIN_EMPLOYEE_ID = "";
            checkEmployeeLoginStatus();
        } else {
            Vector<String> strInsertQueryVec = new Vector<String>();

            String sqlQuery = "insert into salon_employee_loginout_history ( " +
                    " scode, sidx, stcode, employeeIdx, employeeName, loginouttype " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "', " +
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "', " +
                    " '1' " +
                    " ) ";

            strInsertQueryVec.addElement(sqlQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("EmpLoginLog", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
            String returnResult = "";
            returnResult = mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            } else {
                // 로그인창에서 오픈한 Command 창이 열려 있을 경우 close 한다.
                if ((BackOfficeCommand.mActivity_BackOfficeCommand != null) && (!BackOfficeCommand.mActivity_BackOfficeCommand.isFinishing())) {
                    BackOfficeCommand.mActivity_BackOfficeCommand.finish();
                }

                GlobalMemberValues.LOGIN_EMPLOYEE_ID = "";
                checkEmployeeLoginStatus();
            }
        }
    }










    public static int setSettingsInformation() {
        int returnValue;
        String strQuerySelecSettingsInfo, strSettingsInfoCnt;
        int settingsCnt = 0;
        int settingsTableCnt = 0;

        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성

        // 쿼리문자열을 담을 벡터 변수생성
        Vector<String> strInsertQueryVec = new Vector<String>();

        // salon_storestationsettings_system
        settingsTableCnt = dbInit.checkTable("salon_storestationsettings_system");
        if (settingsTableCnt > 0) {
            strQuerySelecSettingsInfo = "select count(idx) from salon_storestationsettings_system";
            strSettingsInfoCnt = dbInit.dbExecuteReadReturnString(strQuerySelecSettingsInfo);
            settingsCnt = GlobalMemberValues.getIntAtString(strSettingsInfoCnt);
            if (settingsCnt == 0) {
                String insQuery = "insert into salon_storestationsettings_system (" +
                        " scode, sidx, stcode, splashuse, inreverse, cloudurl, downloaddata, databasebackup, clockinouttype, departmentviewyn, " +
                        " mssqlsyncyn, mssqldbip, databasename, databasepass, mobilehost, cloudhost " +
                        " ) values (" +
                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + 0 + "', " +              // 스플래쉬 화면 사용유무 (0 : 사용     1 : 사용안함)
                        " '" + 2 + "', " +              // 화면 방향 (0 : 정방향       1 : 역방향         2 : 자동회전)
                        " '" + "" + "', " +             // 클라우드 연동 URL
                        " '" + 1 + "', " +              // 데이터 다운로드 시점 (0 : 다운로드 버튼 클릭시   1 : 앱 오픈시)
                        " '" + 2 + "', " +              // DB 백업유무 (0 : 백업안함    1 : Tender 시     2 : 앱 종료시)
                        " '" + 1 + "', " +              // Clock In Out 타입 (0 : 오프라인    1 : 온라인)
                        " '" + "N" + "', " +             // 직원선택부분에서 부서를 먼저 보여줄지 여부 (Y / N)
                        " '" + GlobalMemberValues.mssql_sync + "', " +
                        " '" + GlobalMemberValues.mssql_ip + "', " +
                        " '" + GlobalMemberValues.mssql_db + "', " +
                        " '" + GlobalMemberValues.mssql_pw + "', " +
                        " '" + GlobalMemberValues.MOBILE_HOST + "', " +
                        " '" + GlobalMemberValues.CLOUD_HOST + "' " +
                        " ) ";
                strInsertQueryVec.addElement(insQuery);

                for (int i = 1; i < 6; i++) {
                    insQuery = "insert into salon_storestationsettings_system_receipt (" +
                            " scode, sidx, stcode, receipt_type " +
                            " ) values (" +
                            " '" + GlobalMemberValues.SALON_CODE + "', " +
                            " '" + GlobalMemberValues.STORE_INDEX + "', " +
                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                            " '" + i + "' " +
                            " ) ";
                    strInsertQueryVec.addElement(insQuery);
                }
            }
        }

        // salon_storestationsettings_deviceprinter - receipt printer
        settingsTableCnt = dbInit.checkTable("salon_storestationsettings_deviceprinter");
        if (settingsTableCnt > 0) {
            strQuerySelecSettingsInfo = "select count(idx) from salon_storestationsettings_deviceprinter";
            strSettingsInfoCnt = dbInit.dbExecuteReadReturnString(strQuerySelecSettingsInfo);
            settingsCnt = GlobalMemberValues.getIntAtString(strSettingsInfoCnt);
            if (settingsCnt == 0) {
                String insQuery = "insert into salon_storestationsettings_deviceprinter (" +
                        " scode, sidx, stcode, " +
                        " printername, autoreceipt, receiptfontsize, receiptpapercount, receiptfooter, " +
                        " tenderuseforzerobalance, autodiscountuseforonlycash, autodiscountuseforonlycashrate, " +
                        " customercarddigitpositionalign, customercarddigitpositionstart, customercarddigitpositioncount, " +
                        " giftcarddigitpositionalign, giftcarddigitpositionstart, giftcarddigitpositioncount, " +
                        " employeecommissionpolicy, managerpwduseforvoid, cashdraweronoffonsalemode, voidprint, returnprint, printlanguage " +
                        " ) values (" +
                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +

                        " '" + "No Printer" + "', " +                 // 프린터 이름
                        " '" + 0 + "', " +                  // 영수증 출력(프린터, 이메일) 여부 (0 : 발송      1 : 발송하지 않음)
                        " '" + 15 + "', " +                 // 영수증 프린터 폰트 사이즈
                        " '" + 1 + "', " +                  // 영수증 출력 수량
                        " '" + "" + "', " +                 // 영수증 하단 정보

                        " '" + 0 + "', " +                  // 금액 zero 시 결제(Tender) 사용여부 (0 : 제로금액 결제가능     1 : 제로금액 결제안됨)
                        " '" + 1 + "', " +                  // 현금으로만 결제시 자동할인 여부 (0 : 할인사용         1 : 할인안함)
                        " '" + 0.0 + "', " +                // 현금으로만 결제시 자동할인 비율

                        " '" + "L" + "', " +                // 고객카드에서 읽어올 코드값 시작부분 (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)
                        " '" + 0 + "', " +                  // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값
                        " '" + 4 + "', " +                  // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값부터 글자수)

                        " '" + "L" + "', " +                // 기프트카드에서 읽어올 코드값 시작부분 (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)
                        " '" + 0 + "', " +                  // 기프트카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값
                        " '" + 4 + "', " +                  // 기프트  카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값부터 글자수)

                        " '" + 1 + "', " +                  // 직원 커미션 정책
                        // 0 : 직원커미션만 적용
                        // 1 : 직원커미션 + 서비스커미션
                        " '" + 0 + "', " +                  // Void 시 매니저 비밀번호 입력여부 (0 : 비밀번호 입력          1 : 비밀번호 입력하지 않음)
                        " '" + 0 + "', " +                   // 세일모드에서 돈통 오픈버튼 배치여부 (0 : 배치         1 : 배치안함)
                        " '" + 0 + "', " +                   // Void 시 프린트 여부 (0 : 프린트         1 : 프린트안함)
                        " '" + 0 + "', " +                   // Return 시 프린트 여부 (0 : 프린트         1 : 프린트안함)
                        " '" + "EN" + "' " +               // 프린트 사용언어
                        " ) ";
                strInsertQueryVec.addElement(insQuery);
            }
        }

        // 프린터 관련 데이터 저장 --- salon_storestationsettings_deviceprinter - kitchen printer
        for (int i = 2; i < 7; i++) {
            String tempTblName = "";
            tempTblName = "salon_storestationsettings_deviceprinter" + i;

            settingsTableCnt = dbInit.checkTable(tempTblName);
            if (settingsTableCnt > 0) {
                strQuerySelecSettingsInfo = "select count(idx) from " + tempTblName;
                strSettingsInfoCnt = dbInit.dbExecuteReadReturnString(strQuerySelecSettingsInfo);
                settingsCnt = GlobalMemberValues.getIntAtString(strSettingsInfoCnt);
                if (settingsCnt == 0) {
                    String insQuery = "insert into " + tempTblName + " (" +
                            " scode, sidx, stcode, " +
                            " printername, autoreceipt, receiptfontsize, receiptpapercount, receiptfooter, " +
                            " tenderuseforzerobalance, autodiscountuseforonlycash, autodiscountuseforonlycashrate, " +
                            " customercarddigitpositionalign, customercarddigitpositionstart, customercarddigitpositioncount, " +
                            " giftcarddigitpositionalign, giftcarddigitpositionstart, giftcarddigitpositioncount, " +
                            " kitchenprinting, printlanguage " +
                            " ) values (" +
                            " '" + GlobalMemberValues.SALON_CODE + "', " +
                            " '" + GlobalMemberValues.STORE_INDEX + "', " +
                            " '" + GlobalMemberValues.STATION_CODE + "', " +

                            " '" + "No Printer" + "', " +                 // 프린터 이름
                            " '" + 0 + "', " +                  // 영수증 출력(프린터, 이메일) 여부 (0 : 발송      1 : 발송하지 않음)
                            " '" + 15 + "', " +                 // 영수증 프린터 폰트 사이즈
                            " '" + 1 + "', " +                  // 영수증 출력 수량
                            " '" + "" + "', " +                 // 영수증 하단 정보

                            " '" + 0 + "', " +                  // 금액 zero 시 결제(Tender) 사용여부 (0 : 제로금액 결제가능     1 : 제로금액 결제안됨)
                            " '" + 1 + "', " +                  // 현금으로만 결제시 자동할인 여부 (0 : 할인사용         1 : 할인안함)
                            " '" + 0.0 + "', " +                // 현금으로만 결제시 자동할인 비율

                            " '" + "L" + "', " +                // 고객카드에서 읽어올 코드값 시작부분 (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)
                            " '" + 0 + "', " +                  // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값
                            " '" + 4 + "', " +                  // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값부터 글자수)

                            " '" + "L" + "', " +                // 기프트카드에서 읽어올 코드값 시작부분 (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)
                            " '" + 0 + "', " +                  // 기프트카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값
                            " '" + 4 + "', " +                  // 기프트  카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을 선택하였을 경우의 시작값부터 글자수)

                            " '" + 0 + "', " +                   // kitchen 프린트 사용여부 (0 : 프린트         1 : 프린트안함)
                            " '" + "EN" + "' " +               // 프린트 사용언어
                            " ) ";
                    strInsertQueryVec.addElement(insQuery);
                }
            }
        }

        // salon_storestationsettings_paymentgateway
        settingsTableCnt = dbInit.checkTable("salon_storestationsettings_paymentgateway");
        if (settingsTableCnt > 0) {
            strQuerySelecSettingsInfo = "select count(idx) from salon_storestationsettings_paymentgateway";
            strSettingsInfoCnt = dbInit.dbExecuteReadReturnString(strQuerySelecSettingsInfo);
            settingsCnt = GlobalMemberValues.getIntAtString(strSettingsInfoCnt);
            if (settingsCnt == 0) {
                String insQuery = "insert into salon_storestationsettings_paymentgateway (" +
                        " scode, sidx, stcode, cardchargesystemuse, paymentgateway, paymentgatewayid, paymentgatewaypwd, tipuse, " +
                        " networkip1, networkip2, networkip3, networkip4, networkport " +
                        " ) values (" +
                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                        " '" + GlobalMemberValues.STORE_INDEX + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + 1 + "', " +              // 포스연동 카드단말기 사용유무 0 : 사용 (연동 터미널 및 독립 터미널 사용) 1 : 사용안함 (독립형 터미널만사용)
                        " '" + 1 + "', " +              // PG 종류 (0 : PPM01, 1 : PPM04, 2 : NZ Payment, 3 : others)
                        " '" + "" + "', " +             // PG ID
                        " '" + "" + "', " +             // PG PWD
                        " '" + 1 + "', " +              // "팁에 카드결제사용 여부 (0 : 사용      1 : 사용안함)"
                        " '" + "0" + "', " +             // IP1
                        " '" + "0" + "', " +             // IP2
                        " '" + "0" + "', " +             // IP3
                        " '" + "0" + "', " +             // IP4
                        " '" + "" + "' " +             // IP1
                        " ) ";
                strInsertQueryVec.addElement(insQuery);
            }
        }

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("MainActivitySettingsInfo", "query : " + tempQuery + "\n");
        }

        GlobalMemberValues.logWrite("MainActivitySettingsInfo", "size : " + strInsertQueryVec.size() + "\n");

        if (strInsertQueryVec.size() > 0) {
            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                returnValue = 0;
            } else {
                returnValue = 1;
            }
        } else {
            returnValue = 0;
        }

        mTimerPossible = "Y";

        return returnValue;
    }

    public static void setSettingsInformationInGlobalValues() {
        int settingsTableCnt = 0;
        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성

        dbInit.initDatabaseTables();

        /** Settings System **********************************************************************************/
        settingsTableCnt = dbInit.checkTable("salon_storestationsettings_system");
        if (settingsTableCnt > 0) {
            String strQuery = "select splashuse, inreverse, cloudurl, downloaddata, databasebackup, departmentviewyn, showcostafterdcextra, " +
                    " cardislast " +
                    " from salon_storestationsettings_system ";
            Cursor settingsSystemCursor = dbInit.dbExecuteRead(strQuery);
            if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
                String tempSplashUse = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
                String tempInReverse = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
                String tempCloudUrl = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);
                String tempdownloadData = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);
                String tempDatabaseBackup = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(4), 1);
                String tempDepartmentviewyn = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(5), 1);
                String tempShowcostafterdcextra = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(6), 1);
                String tempCardislast = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(7), 1);

                // 커미션 ratio  구하기
                String tempCommissionratioType = dbInit.dbExecuteReadReturnString(
                        "select employeecommissionpolicy from salon_storestationsettings_deviceprinter");

                if (!GlobalMemberValues.isStrEmpty(tempSplashUse)) {
                    GlobalMemberValues.GLOBAL_SPLASHUSE = GlobalMemberValues.getIntAtString(tempSplashUse);
                }
                if (!GlobalMemberValues.isStrEmpty(tempInReverse)) {
                    GlobalMemberValues.PORTRAITORLANDSCAPE = GlobalMemberValues.getIntAtString(tempInReverse);
                }
                if (!GlobalMemberValues.isStrEmpty(tempCloudUrl)) {
                    if (tempCloudUrl.toLowerCase().indexOf("http://") == -1 || tempCloudUrl.toLowerCase().indexOf("https://") == -1) {
                        if (GlobalMemberValues.CLOUD_SERVER_URL_BASIC.toLowerCase().indexOf("http://") > -1) {
                            tempCloudUrl = "http://" + tempCloudUrl;
                        }
                        if (GlobalMemberValues.CLOUD_SERVER_URL_BASIC.toLowerCase().indexOf("https://") > -1) {
                            tempCloudUrl = "https://" + tempCloudUrl;
                        }
                    }
                    GlobalMemberValues.CLOUD_SERVER_URL = tempCloudUrl;
                }
                if (!GlobalMemberValues.isStrEmpty(tempdownloadData)) {
                    GlobalMemberValues.GLOBAL_DOWNLOADDATA = GlobalMemberValues.getIntAtString(tempdownloadData);
                }
                if (!GlobalMemberValues.isStrEmpty(tempDatabaseBackup)) {
                    GlobalMemberValues.GLOBAL_DATABASEBACKUP = GlobalMemberValues.getIntAtString(tempDatabaseBackup);
                }
                if (!GlobalMemberValues.isStrEmpty(tempCommissionratioType)) {
                    GlobalMemberValues.GLOBAL_COMMISSIONRATIOTYPE = GlobalMemberValues.getIntAtString(tempCommissionratioType);
                }

                if (!GlobalMemberValues.isStrEmpty(tempShowcostafterdcextra)) {
                    GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA = tempShowcostafterdcextra;
                } else {
                    GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA = "N";
                }

                if (!GlobalMemberValues.isStrEmpty(tempCardislast)) {
                    GlobalMemberValues.GLOBAL_CARDISLAST = tempCardislast;
                } else {
                    GlobalMemberValues.GLOBAL_CARDISLAST = "N";
                }
            }
        }
        /******************************************************************************************************/

    }

    public static int setBasicInformationInit() {
        int returnInt = 0;

        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성~

        if (dbInit == null) {
            dbInit = MainActivity.mDbInit;
        }

        Cursor dbCursor;
        // 포스 기본정보 -------------------------------------------------------------------------------------------------
        String tempSid, tempScode, tempSname, tempSidx, tempStcode, tempSerialNumber, tempStname;
        String strBasicPosInformationQuery = "select sid, scode, sidx, stcode, serialNumber, stname, sname " +
                " from basic_pos_information ";
        dbCursor = dbInit.dbExecuteRead(strBasicPosInformationQuery);
        if (dbCursor == null) {
            GlobalMemberValues.APPOPENCOUNT = 0;
            GlobalMemberValues.SPLASHCOUNT = 0;
            GlobalMemberValues.DROPTABLETYPE = 1;
            GlobalMemberValues.INSERTDATAAFTERDELETE = 0;
            return 0;
        }
        if (dbCursor.getCount() > 0 && dbCursor.moveToFirst()) {
            tempSid = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(0), 1);
            tempScode = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(1), 1);
            tempSname = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(6), 1);
            tempSidx = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(2), 1);
            tempStcode = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(3), 1);
            tempSerialNumber = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(4), 1);
            tempStname = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(5), 1);

            GlobalMemberValues.SALON_SID = tempSid;
            GlobalMemberValues.SALON_CODE = tempScode;
            GlobalMemberValues.SALON_NAME = tempSname;
            GlobalMemberValues.STORE_INDEX = tempSidx;
            GlobalMemberValues.STATION_CODE = tempStcode;
            GlobalMemberValues.SERIAL_NUMBER = tempSerialNumber;
            GlobalMemberValues.STATION_NAME = tempStname;

            returnInt = 1;
        } else {
            GlobalMemberValues.APPOPENCOUNT = 0;
            GlobalMemberValues.SPLASHCOUNT = 0;
            GlobalMemberValues.DROPTABLETYPE = 1;
            GlobalMemberValues.INSERTDATAAFTERDELETE = 0;
            returnInt = 0;
        }
        // -------------------------------------------------------------------------------------------------------------

        return returnInt;
    }


    public static void setStoreInformationInit() {
        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
        Cursor dbCursor;

        // 스토어 정보 -------------------------------------------------------------------------------------------------
        String tempManagerpwd, tempTax1, tempTax2, tempTax3, tempRspuseyn, tempRsrsprate, tempCategorysu, tempTax4, tempTax5;
        String strSalonStoreGeneralQuery = "select managerpwd, tax1, tax2, tax3, rspuseyn, rsprate, categorysu, tax4, tax5 " +
                " from salon_storegeneral where sidx = '" + GlobalMemberValues.STORE_INDEX + "' and delyn = 'N' ";
        dbCursor = dbInit.dbExecuteRead(strSalonStoreGeneralQuery);
        if (dbCursor.getCount() > 0 && dbCursor.moveToFirst()) {
            tempManagerpwd = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(0), 1);
            tempTax1 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(1), 1);
            tempTax2 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(2), 1);
            tempTax3 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(3), 1);
            tempRspuseyn = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(4), 1);
            if (GlobalMemberValues.isStrEmpty(tempRspuseyn)) {
                tempRspuseyn = "N";
            }
            tempRsrsprate = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(5), 1);
            if (GlobalMemberValues.isStrEmpty(tempRsrsprate)) {
                tempRsrsprate = "0.0";
            }
            tempCategorysu = dbCursor.getString(6);
            tempTax4 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(7), 1);
            tempTax5 = GlobalMemberValues.getDBTextAfterChecked(dbCursor.getString(8), 1);

            GlobalMemberValues.STORE_ADMIN_PWD = tempManagerpwd;
            GlobalMemberValues.STORE_SERVICE_TAX = GlobalMemberValues.getDoubleAtString(tempTax1);
            GlobalMemberValues.STORE_PRODUCT_TAX = GlobalMemberValues.getDoubleAtString(tempTax2);

            GlobalMemberValues.STORE_FOOD_TAX1 = GlobalMemberValues.getDoubleAtString(tempTax3);
            GlobalMemberValues.STORE_FOOD_TAX2 = GlobalMemberValues.getDoubleAtString(tempTax4);
            GlobalMemberValues.STORE_FOOD_TAX3 = GlobalMemberValues.getDoubleAtString(tempTax5);

            GlobalMemberValues.REWARDSALADUSEYN = tempRspuseyn;
            GlobalMemberValues.REWARDSALADRATIO = GlobalMemberValues.getDoubleAtString(tempRsrsprate);
            GlobalMemberValues.STOREMAXCATEGORYSU = GlobalMemberValues.getIntAtString(tempCategorysu);
            GlobalMemberValues.STOREMAXSERVICESU = 60;
            GlobalMemberValues.STOREMAXGIFTCARDSU = 20;

            // Quick Sale 커미션 관련
            GlobalMemberValues.QUICKSALE_COMMISSIONRATIO = 0.00;
            // Quick Sale 커미션 관련
            GlobalMemberValues.QUICKSALE_COMMISSIONRATIOTYPE = "%";
            // Quick Sale 포인트 관련
            GlobalMemberValues.QUICKSALE_POINTRATIO = 0.00;

            // Product 커미션 관련
            GlobalMemberValues.PRODUCT_COMMISSIONRATIO = 0.00;
            // Product 커미션 관련
            GlobalMemberValues.PRODUCT_COMMISSIONRATIOTYPE = "%";
            // Product 포인트 관련
            GlobalMemberValues.PRODUCT_POINTRATIO = 0.00;

            // Giftcard 커미션 관련
            GlobalMemberValues.GIFTCARD_COMMISSIONRATIO = 2.00;
            // Giftcard 커미션 관련
            GlobalMemberValues.GIFTCARD_COMMISSIONRATIOTYPE = "%";

        }
        // -------------------------------------------------------------------------------------------------------------
    }

    public void setMainProcess() {
        // 권한 확인 및 설정 ----------------------------------------------------------------------------------
        // 파일 읽기
        //checkAppPermission(mActivity, mContext, "FILEWRITE");
        // --------------------------------------------------------------------------------------------------

        mActivity = this;
        // 태블릿 해상도 구해서 글로벌 변수에 넣기 (TABLE_WIDTH, TABLE_HEIGHT)
        setDisplayValue();
        // SQLite 에서 데이터 가져오는 클래스 객체생성
        dataAtSqlite = new GetDataAtSQLite(this);

        // 데이터베이스 테이블 추가, 수정, 삭제 등 처리
        updateDatabaseTables();

        // 뷰 객체 생성 및 static 변수 저장
        setMainContent();

        // 리스트 뷰 초기화 ----------------------------------------------------------------------------
        // 앱을 오픈을 한 경우에만..
        // 최초 오픈일 경우에는 객체가 생성 및 메모리에 할당되지 않았으므로
        // 반드시 오픈을 한번 이상 한 경우에만 초기화 작업을 진행한다.
        //if (GlobalMemberValues.APPOPENCOUNT > 0) {
        //    MainMiddleService.clearListView();
        //}
        // ---------------------------------------------------------------------------------------------

        // 직원정보 ADMIN 으로 초기화 ------------------------------------------------------------------
        // !!! 반드시 setMainContent 를 실행하고 호출해야 함.
        // 직원 로그인이 안되어 있을 경우에만..
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.LOGIN_EMPLOYEE_ID)) {
            GlobalMemberValues.setEmployeeInfoInit();
        }
        /**
         if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null) {
         GlobalMemberValues.setEmployeeInfoInit();
         }
         **/
        // ---------------------------------------------------------------------------------------------

        // 고객정보 Walk In 으로 초기화 ----------------------------------------------------------------
        // !!! 반드시 setMainContent 를 실행하고 호출해야 함.
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO == null) {
            GlobalMemberValues.setCustomerInfoInit();
        }
        // ---------------------------------------------------------------------------------------------

        /** 상단 카테고리 & 중간 서비스 배치하기 ******************************************************************/
        // 상단 카테고리 및 중간서비스 배치하기 배치하기 ------------------------------------------
        // 상단 카테고리 배치가 완료되면 setTopCategory 메소드 끝 부분에서 서비스 메뉴 배치 메소드가 호출된다.
        // MainTopCategory 객체 생성시 생성자 인자중 마지막인자
        // 0 : MainTopCateogory 생성시 MainMiddleService 객체 생성
        // 1 : MainTopCateogory 생성시 MainMiddleService 객체 생성하지 않음
        MainTopCategory mainTopCate = new MainTopCategory(this, this, dataAtSqlite, 0);
        mainTopCate.setTopCategory();
        // ------------------------------------------------------------------------------------
        // 중간 서비스 배치하기 -------------------------------------------------------------------
        //MainMiddleService mainMdlService = new MainMiddleService(this, dataAtSqlite, middleServiceLnl);
        //mainMdlService.setMiddleService("", "");
        // ------------------------------------------------------------------------------------
        /****************************************************************************************************/

        /** 상단 살롱 이름/로고, 앱로고, 디지털 시계, 버튼들 관련 **************************/
        AppTopBar appTopInformation = new AppTopBar(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 좌측 상단 Cust 버튼 클릭 관련 ***********************************************/
        CustomerSearch custSearch = new CustomerSearch(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Employee 버튼 클릭 관련 ***********************************************/
        //EmployeeSelection empSelection = new EmployeeSelection(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Quick Sale 버튼 클릭 관련 ***********************************************/
        QuickSale quickSale = new QuickSale(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Product Sale 버튼 클릭 관련 ***********************************************/
        //ProductSale productSale = new ProductSale(this, this, dataAtSqlite); --> Product Sale 을 거쳐서 올경우
        ProductList productList = new ProductList(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Gift Card 버튼 클릭 관련 ***********************************************/
        GiftCard giftCard = new GiftCard(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Discount 버튼 클릭 관련 ***********************************************/
        Discount discount = new Discount(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 좌측 하단 Pay 버튼 클릭 관련 ***********************************************/
        Payment payment = new Payment(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 상단 Command Button 버튼 클릭 관련 ****************************************/
        CommandButton commandButton = new CommandButton(this, this, dataAtSqlite);
        /***********************************************************************************/

        /** 우측 하단 Menu Search 버튼 클릭 관련 ***********************************************/
        MenuSearch menusearch = new MenuSearch(this, this, dataAtSqlite);
        /***********************************************************************************/



        /**  앱 업데이트 ********************************************************************************************/
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                return;
            }
            // 0 : FTP 업데이트             1 : 플레이스토어 업데이트
            if (GlobalMemberValues.APPUPDATETYPE > 0) {
                AppUpdate.appUpdateFromPlayStore(mActivity, mContext);
            } else {
                AppUpdate.appUpdateFromCloudServer(mActivity, mContext);
            }
        }
        /**********************************************************************************************************/

        // 디바이스 사용유무 체크
        String strSerialNumber = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        checkDeviceUsable(strSerialNumber);
    }

    // Discount 관련처리
    public static void doDiscountOpen() {
        Discount discount = new Discount(mActivity, mContext, dataAtSqlite);
        discount.setContents();
    }

    public static void checkDeviceUsable(String paramSerialNumber) {
        /**  해당 디바이스 사용가능 여부 *****************************************************************************/
        deviceUsableCheckResult = "00";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                return;
            }
            if (!GlobalMemberValues.isOnline2().equals("00")) {
                return;
            }
            final String secureAndroidId = paramSerialNumber;
            GlobalMemberValues.logWrite("AppUsableCheckLog", "S / N : " + secureAndroidId + "\n");
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                    // 사용가능
                    GlobalMemberValues.ISPOSSIBLETHISSTATION = true;

                    API_deviceuseable_check apicheckInstance = new API_deviceuseable_check(secureAndroidId);
                    apicheckInstance.execute(null, null, null);
                    deviceUsableCheckResult = "00";
                    try {
                        Thread.sleep(3000); //3초마다 실행
                        if (apicheckInstance.mFlag) {
                            deviceUsableCheckResult = apicheckInstance.mApiReturnCode;
                        }
                    } catch (InterruptedException e) {
                        deviceUsableCheckResult = "00";
                    }
                    GlobalMemberValues.logWrite("AppUsableCheckLog", "deviceUsableCheckResult : " + deviceUsableCheckResult + "\n");
                    // ---------------------------------------------------------------------------------
                    // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                    appCheckhandler.sendEmptyMessage(0);
                    // ---------------------------------------------------------------------------------
                }
            });
            thread.start();
        }
        /**********************************************************************************************************/
    }

    // 스테이션 유효성 체크하여 초기화 여부 진행
    public static Handler appCheckhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (GlobalMemberValues.isStrEmpty(deviceUsableCheckResult)) {
//                SettingsSystem.resetApplication();
                GlobalMemberValues.ISPOSSIBLETHISSTATION = true;
            } else {
                if (deviceUsableCheckResult == "00" || deviceUsableCheckResult.equals("00")) {
                    // 사용가능
                    GlobalMemberValues.ISPOSSIBLETHISSTATION = true;
                } else {
                    GlobalMemberValues.ISPOSSIBLETHISSTATION = true;
                    if (deviceUsableCheckResult == "56" || deviceUsableCheckResult.equals("56")) {
                        GlobalMemberValues.logWrite("stationchecklog", "deviceUsableCheckResult : " + deviceUsableCheckResult + "\n");
                        // SettingsSystem.resetApplication();
                        //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "This station is an invalid device\nIf this message still appears, contact Navyzebra", "Close");
                        GlobalMemberValues.ISPOSSIBLETHISSTATION = false;
                    }
                }
            }
        }
    };


    // 데이터베이스 테이블 추가
    public void updateDatabaseTables() {
        if (mDbTableUpdate) {
            // 테이블 추가 생성
            DatabaseInit dbInit = new DatabaseInit(mContext);
            dbInit.initDatabaseTables();
            GlobalMemberValues.logWrite("MainActivityTableAdd", "테이블 추가 실행" + "\n");
        }
    }

    /** 뷰 객체 생성 후 static 변수에 저장 ********************************************************************/
    public void setMainContent() {
        // 프레임 레이아웃에 있는 레이아웃들 객체 생성 -------------------------------------------------------------------------
        // ROOT 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUT_ROOT = (RelativeLayout)findViewById(R.id.rootLn);
        // 메인 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN = (LinearLayout)findViewById(R.id.frameLayout_main);
        // Employee Selection 레d이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION = (LinearLayout)findViewById(R.id.frameLayout_employeeSelection);
        // Customer Search 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_CUSTOMERSEARCH = (LinearLayout)findViewById(R.id.frameLayout_customerSearch);
        // Quick Sale 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_QUICKSALE = (LinearLayout)findViewById(R.id.frameLayout_quickSale);
        // Product Sale 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTSALE =  (LinearLayout)findViewById(R.id.frameLayout_productSale);
        // Product List 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PRODUCTLIST =  (LinearLayout)findViewById(R.id.frameLayout_productList);
        // Gift Card 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_GIFTCARD =  (LinearLayout)findViewById(R.id.frameLayout_giftCard);
        // Discount 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_DISCOUNT =  (LinearLayout)findViewById(R.id.frameLayout_discount);
        // Payment 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT =  (LinearLayout)findViewById(R.id.frameLayout_payment);
        // Command Button 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON =  (LinearLayout)findViewById(R.id.frameLayout_commandButton);
        // Menu Search 레이아웃
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MENUSEARCH =  (LinearLayout)findViewById(R.id.frameLayout_menusearch);

        // -------------------------------------------------------------------------------------------------------------

        // Time Menu 타임메뉴 시간대 표시 TextView
        bottomTimeMenuTv = (TextView)findViewById(R.id.bottomTimeMenuTv);

        // 메인 프레임 색상변경을 위한 LinearLayout 객체들 생성 -------------------------------------------------------------
        GlobalMemberValues.GLOBAL_MAINMIDDLELEFTLN =  (LinearLayout)findViewById(R.id.mainMiddleLeftLn);
        GlobalMemberValues.GLOBAL_MAINMIDDLELEFTTOPLN =  (LinearLayout)findViewById(R.id.mainMiddleLeftTopLn);
        GlobalMemberValues.GLOBAL_MAINBOTTOMLN =  (LinearLayout)findViewById(R.id.mainBottomLn);
        // --------------------------------------------------------------------------------------------------------------

        // 메인 우측 상단 버튼 객체 선언 ----------------------------------
        // 앱 상단 BAR LinearLayout 객체
        GlobalMemberValues.GLOBAL_APPTOPBAR = (LinearLayout)findViewById(R.id.appTopBarLinearLayout);
        if (GlobalMemberValues.thisTabletRealWidth < 1100) {
            if (GlobalMemberValues.GLOBAL_APPTOPBAR != null){
                GlobalMemberValues.GLOBAL_APPTOPBAR.setPadding(10, 5, 5, 5);// crashlytics Attempt to invoke virtual method 'void android.view.View.setPadding(int, int, int, int)' on a null object reference
            }
        }

        if (GlobalMemberValues.isDeviceTabletPCFromDB()){
            GlobalMemberValues.GLOBAL_APPTOPBAR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,80));
        }

        // 인터넷 상태 체크 TextView
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPNETWORKSTATUS = (TextView)findViewById(R.id.topNetworkStatus);
        // 클라우드 이동버튼
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1 = (Button)findViewById(R.id.topButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setBackgroundResource(R.drawable.ab_imagebutton_main_cloud);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        //  Command 옵션버튼
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3 = (Button)findViewById(R.id.topButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setBackgroundResource(R.drawable.ab_imagebutton_main_command);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // System Setting 버튼
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2 = (Button)findViewById(R.id.topButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setBackgroundResource(R.drawable.ab_imagebutton_setting);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setBackgroundResource(R.drawable.ab_imagebutton_setting_lite);
        }

        // 앱 종료 버튼
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4 = (Button)findViewById(R.id.topButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setBackgroundResource(R.drawable.ab_imagebutton_main_close);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setBackgroundResource(R.drawable.ab_imagebutton_main_close_lite);
        }
        // ----------------------------------------------------------------

        // 메인 좌측 상단 Employee Information 관련 객체
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO = (TextView)findViewById(R.id.topEmployeeInfo);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setTextSize(26 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setOnClickListener(directButtonClickListener);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO_LN = (LinearLayout) findViewById(R.id.topEmployeeInfo_ln);

        if (GlobalMemberValues.getStationType().equals("Q")) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO_LN.setVisibility(View.GONE);
        }


        // 메인 좌측 상단 CUST 버튼객체
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER = (Button)findViewById(R.id.mainTopCustButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setBackgroundResource(R.drawable.ab_imagebutton_main_cust);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        if (GlobalMemberValues.isCRMUse()){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setVisibility(View.VISIBLE);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCUSTOMER.setVisibility(View.GONE);
        }

        // 메인 좌측 상단 Customer 이름 TextView 객체
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME = (TextView)findViewById(R.id.customerInfoTvCustomerName);
//        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
//                + GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.getTextSize() * GlobalMemberValues.getGlobalFontSize()
//        );
        // jihun 0812 크기 고정
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERNAME.setTextSize(16);
        // 메인 좌측 상단 Customer 전화번호 TextView 객체
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTEXTVIEWCUSTOMERPHONE = (TextView)findViewById(R.id.customerInfoTvCustomerPhone);
        // 메인 좌측 상단 HOLD 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD = (Button)findViewById(R.id.directButtonHoldButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setBackgroundResource(R.drawable.ab_imagebutton_main_hold);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                    + GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.getTextSize() * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 메인 좌측 상단 RECALL 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL = (Button)findViewById(R.id.directButtonRecallButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setBackgroundResource(R.drawable.ab_imagebutton_main_recall
            );
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setOnClickListener(directButtonClickListener);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setOnClickListener(directButtonClickListener);

        // 메인 리스트뷰 타이틀 텍스트 객체 생성 --------------------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW1 = (TextView)findViewById(R.id.mainItemListTitleTextView1);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW1.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() +
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW1.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW2 = (TextView)findViewById(R.id.mainItemListTitleTextView2);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() + 14 * GlobalMemberValues.getGlobalFontSize()
        );
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW3 = (TextView)findViewById(R.id.mainItemListTitleTextView3);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW3.setTextSize(22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW4 = (TextView)findViewById(R.id.mainItemListTitleTextView4);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW4.setTextSize(22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW5 = (TextView)findViewById(R.id.mainItemListTitleTextView5);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW5.setTextSize(22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6 = (TextView)findViewById(R.id.mainItemListTitleTextView6);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() +
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX = (CheckBox) findViewById(R.id.mainItemAllSelect);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX.setOnCheckedChangeListener(mainItemCheckListener);
        // 멀티선택관련 추가 ----------------------------------------------------------------------------------------
        if (!GlobalMemberValues.isMultiCheckOnCart()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6.setVisibility(View.GONE);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEMLISTTITLETEXTVIEW6.setVisibility(View.VISIBLE);
        }
        // --------------------------------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------------------------------------------
        // 메인 리스트뷰 객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW = (ListView)findViewById(R.id.mainSaleCartList);


        // 메인 리스트뷰 아래 Sub Total 타이틀 객체 생성
        TextView mainSubTotalTitleTextView = (TextView)findViewById(R.id.mainSubTotalTitleTextView);
        mainSubTotalTitleTextView.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        // 메인 리스트뷰 아래 Common gratuity 타이틀 객체 생성
        TextView mainCommonGratuityTitleTextView = (TextView)findViewById(R.id.mainCommonGratuityTitleTextView);
        mainCommonGratuityTitleTextView.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        // 메인 리스트뷰 아래 Tax 타이틀 객체 생성
        TextView mainTaxTitleTextView = (TextView)findViewById(R.id.mainTaxTitleTextView);
        mainTaxTitleTextView.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        // 메인 좌측 하단 Sub Total TextView 객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW = (TextView)findViewById(R.id.mainSubTotalValue);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        // 메인 좌측 하단 Common Gratuity 객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW = (TextView)findViewById(R.id.mainCommonGratuityValue);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
            mainCommonGratuityTitleTextView.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setVisibility(View.GONE);
        } else {
            mainCommonGratuityTitleTextView.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setVisibility(View.VISIBLE);
        }

        // 메인 좌측 하단 Tax TextView 객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW = (TextView)findViewById(R.id.mainTaxValue);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        // 메인 좌측 하단 Delivery / Pick Up Fee 관련 ---------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPLN = (LinearLayout) findViewById(R.id.deliveryPickupFeeLn);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPLN.setVisibility(View.GONE);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPTEXTTV = (TextView)findViewById(R.id.addValueText);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPTEXTTV.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV = (TextView)findViewById(R.id.addValuePrice);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        // ---------------------------------------------------------------------------------------------------------------

        // 07.18.2022 - add pay for cash, card
        // 메인 좌측 하단 Add Pay 관련 ---------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLN = (LinearLayout) findViewById(R.id.addpayincludedLn);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLN.setVisibility(View.GONE);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLNTV = (TextView)findViewById(R.id.addpayincludedTv);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINADDPAYLNTV.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + 20 * GlobalMemberValues.getGlobalFontSize()
        );

        // 07.18.2022 - add pay for cash, card
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentAddPayLnTag");
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYLN.setVisibility(View.GONE);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_PAYMENT
                .findViewWithTag("paymentAddPayTvTag");
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PAYMENTADDPAYTV.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
                + 20 * GlobalMemberValues.getGlobalFontSize()
        );
        // ---------------------------------------------------------------------------------------------------------------

        // 메인 좌측 하단 Total 객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW = (TextView)findViewById(R.id.mainTotalValue);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setTextSize(40 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // 메인 BIll 관련 --------------------------------------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLLN = (LinearLayout) findViewById(R.id.mainBillLn);

        // 메인 좌측 리스트뷰 아래 Bill 결제관련 객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW = (TextView)findViewById(R.id.mainTotalValueForBill);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.setTextSize(GlobalMemberValues.globalAddFontSize() + 36
                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        // 메인 좌측 리스트뷰 아래 Bill Of 관련 객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTOFTEXTVIEW = (TextView)findViewById(R.id.mainTotalValueForBillOf);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTOFTEXTVIEW.setTextSize(GlobalMemberValues.globalAddFontSize() + 20
                * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        // ---------------------------------------------------------------------------------------------------------------

        // 메인 좌측 하단 Cancel 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL = (Button)findViewById(R.id.mainSaleCartButton_Cancel);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setBackgroundResource(R.drawable.ab_imagebutton_main_cancel2);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 메인 좌측 하단 TOGO 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO = (Button)findViewById(R.id.mainSaleCartButton_togo);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.setBackgroundResource(R.drawable.ab_imagebutton_main_left_b_togo);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 메인 좌측 하단 DELIVERY 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY_TOGO_LN = (LinearLayout)findViewById(R.id.mainSaleCartButton_delivery_togo_ln);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY = (Button)findViewById(R.id.mainSaleCartButton_delivery);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.setBackgroundResource(R.drawable.ab_imagebutton_main_left_b_delivery);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        if (GlobalMemberValues.isToGoSale()){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.setVisibility(View.GONE);
        }


        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT = (Button)findViewById(R.id.mainSaleCartButton_billprint);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setBackgroundResource(R.drawable.ab_imagebutton_main_left_b_billprint);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 메인 좌측 하단 GENERAL MODIFIER 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER = (Button)findViewById(R.id.general_modifier);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER.setBackgroundResource(R.drawable.ab_imagebutton_main_left_b_general_modifier);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setBackgroundResource(R.drawable.ab_imagebutton_main_cancel_lite);
        }

        // 메인 좌측 하단 DELETE 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE = (Button)findViewById(R.id.mainSaleCartButton_Delete);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setBackgroundResource(R.drawable.ab_imagebutton_main_delete2);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setBackgroundResource(R.drawable.ab_imagebutton_main_delete_lite);
        }

        // 메인 좌측 하단 QTY 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY = (Button)findViewById(R.id.mainSaleCartButton_Qty);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setBackgroundResource(R.drawable.ab_imagebutton_main_qty);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 073120
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN = (LinearLayout)findViewById(R.id.mainSaleCartButton_Qty_count);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS = (Button)findViewById(R.id.mainSaleCartButton_Qty_plus);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setBackgroundResource(R.drawable.ab_imagebutton_main_qty_plus2);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS = (Button)findViewById(R.id.mainSaleCartButton_Qty_min);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setBackgroundResource(R.drawable.ab_imagebutton_main_qty_minus2);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 073120

        (MainActivity.this).runOnUiThread(new Runnable(){
            @Override
            public void run() {
                // qty menual
                if (GlobalMemberValues.isPossibleManualQty()){
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.GONE);
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setVisibility(View.VISIBLE);
                } else {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.VISIBLE);
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setVisibility(View.GONE);
                }
            }
        });


        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setBackgroundResource(R.drawable.ab_imagebutton_main_qty_lite);
        }

        // 메인 좌측 하단 PAY 버튼객체 생성
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT = (Button)findViewById(R.id.mainSaleCartButton_Payment);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        if (GlobalMemberValues.isPaymentPossible()){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setVisibility(View.VISIBLE);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setVisibility(View.INVISIBLE);
        }

        if (GlobalMemberValues.getStationType() == "R" || GlobalMemberValues.getStationType().equals("R")) {
            // restaurant not
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
        }

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment_lite);
        }

        // 메인 우측 하단 메뉴 뷰 210611
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW = findViewById(R.id.main_main_bottom_menus_ln);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW = findViewById(R.id.main_main_side_menu_ln);
        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
            if (!GlobalMemberValues.now_saletypeisrestaurant) {
//                btn_table_sale.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW.setVisibility(View.GONE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW.setVisibility(View.VISIBLE);
            } else {
//                btn_table_sale.setVisibility(View.GONE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW.setVisibility(View.GONE);
            }
        } else {
//            btn_table_sale.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW.setVisibility(View.GONE);
        }

        if (GlobalMemberValues.is_customerMain){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW.setVisibility(View.GONE);
        }


        // 메인 우측 하단 EMPLOYEE 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE = (Button)findViewById(R.id.mainRightBottom_Employee);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setBackgroundResource(R.drawable.ab_imagebutton_main_employeelogout);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setOnClickListener(directButtonClickListener);

        // 메인 우측 하단 EMPLOYEE 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE = (Button)findViewById(R.id.mainRightBottom_Employee);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setBackgroundResource(R.drawable.ab_imagebutton_main_employeelogout);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setOnClickListener(directButtonClickListener);

        // 메인 우측 하단 DISCOUNT 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT = (Button)findViewById(R.id.mainRightBottom_Discount);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setBackgroundResource(R.drawable.ab_imagebutton_main_discount);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDISCOUNT.setBackgroundResource(R.drawable.ab_imagebutton_main_discount_lite);
        }


        // 메인 우측 하단 QUICK SALE 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE = (Button)findViewById(R.id.mainRightBottom_QuickSale);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setBackgroundResource(R.drawable.ab_imagebutton_main_quicksale);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQUICKSALE.setBackgroundResource(R.drawable.ab_imagebutton_main_quicksale_lite);
        }

        // 메인 우측 하단 PRODUCT 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT = (Button)findViewById(R.id.mainRightBottom_Product);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.setBackgroundResource(R.drawable.ab_imagebutton_main_product);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPRODUCT.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        // 메인 우측 하단 GIFT CARD 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD = (Button)findViewById(R.id.mainRightBottom_GiftCard);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD.setBackgroundResource(R.drawable.ab_imagebutton_main_giftcard);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONGIFTCARD.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 메인 우측 하단 MENU SEARCH 버튼객체 선언
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH = (Button)findViewById(R.id.mainRightBottom_menusearch);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.setText("");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.setBackgroundResource(R.drawable.ab_imagebutton_main_menusearch);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMENUSEARCH.setOnClickListener(directButtonClickListener);

        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            // 메인 우측 하단 COMMAND 버튼객체 선언
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND = (Button)findViewById(R.id.mainRightBottom_Command);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND.setBackgroundResource(R.drawable.ab_imagebutton_main_command_lite);
            // 메인 우측 하단 MAIN SALE 버튼객체 선언
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE = (Button)findViewById(R.id.mainRightBottom_MainSale);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setBackgroundResource(R.drawable.ab_imagebutton_main_mainsale_lite);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setOnClickListener(directButtonClickListener);

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setBackgroundResource(R.drawable.aa_images_main_main_rollover_lite);
        }


        // 메인 우측 상단 카테고리 부모뷰(LinearLayout) 객체 생성
        // 부모객체를 생성 findViewWithTag 를 사용하여 하위 카테고리 버튼들의 객체를 가져올 수 있다.
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT = (LinearLayout)findViewById(R.id.topCategoryLinearLayout);
//        if (GlobalMemberValues.isThisDevicePosbank){
//            LinearLayout.LayoutParams parentLnParams
//                    = new LinearLayout.LayoutParams(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.getWidth(), GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.getHeight() + 50);
//            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.setLayoutParams(parentLnParams);
//        }

        if (GlobalMemberValues.is_customerMain){
            LinearLayout main_main_middle_empty1 = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN.findViewWithTag("main_main_middle_empty1");
            LinearLayout main_main_middle_line = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN.findViewWithTag("main_main_middle_line");
            LinearLayout main_main_middle_empty2 = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_MAIN.findViewWithTag("main_main_middle_empty2");
            main_main_middle_empty1.setVisibility(View.GONE);
            main_main_middle_line.setVisibility(View.GONE);
            main_main_middle_empty2.setVisibility(View.GONE);
        }


        // 메인 우측 중앙 서비스 부모뷰(LinearLayout) 객체 생성
        // 부모객체를 생성 findViewWithTag 를 사용하여 하위 서비스 버튼들의 객체를 가져올 수 있다.
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT = (LinearLayout)findViewById(R.id.middleServiceLinearLayout);

        // 메인 하단 고객정보 관련 TextView 객체 선언
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_LASTVISIT = (TextView)findViewById(R.id.bottomTextViewMemLastVisit);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_LASTVISIT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICMAINBOTTOMTEXTSIZE
        );
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT = (TextView)findViewById(R.id.bottomTextViewMemPoint);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICMAINBOTTOMTEXTSIZE
        );
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_DOB = (TextView)findViewById(R.id.bottomTextViewMemDob);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_DOB.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICMAINBOTTOMTEXTSIZE
        );
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE = (TextView)findViewById(R.id.bottomTextViewMemNote);
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_NOTE.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICMAINBOTTOMTEXTSIZE
        );

        // 메인 하단 포스 버전정보 TextView 객체 선언
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSIONINFO = (TextView)findViewById(R.id.bottomTextViewPOSVersionInfo);
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSIONINFO.setTextSize(GlobalMemberValues.globalAddFontSize() +
                GlobalMemberValues.BASICMAINBOTTOMTEXTSIZE
        );
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSIONINFO.setText("NTEP CC : 20-028 / v." + GlobalMemberValues.getAppVersionName(mContext));
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSIONINFO.setOnClickListener(directButtonClickListener);

        // version btn
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSION_IMG_BTN = (ImageButton)findViewById(R.id.main_info_btn);
        GlobalMemberValues.GLOBAL_BOTTOMPOS_VERSION_IMG_BTN.setOnClickListener(directButtonClickListener);

        // Employee Selection 관련 객체 생성
        //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_EMPLOYEESELECTION_LINEARLAYOUTEMPLOYEELIST
        //= (LinearLayout)findViewById(R.id.linearLayoutEmployeeList);

        GlobalMemberValues.GLOBAL_MAINTOPLN1 = (LinearLayout)findViewById(R.id.directButtonsLinearLayout);
        GlobalMemberValues.GLOBAL_MAINTOPLN2 = (LinearLayout)findViewById(R.id.directButtonsLinearLayout2);

        // 06.01.2022 ----------------------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN = (LinearLayout)findViewById(R.id.mainPeopleCntLn);
        GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setOnClickListener(directButtonClickListener);

        GlobalMemberValues.GLOBAL_MAINPEOPLECNTTOPTV = (TextView)findViewById(R.id.peopleCntTopText);
//        GlobalMemberValues.GLOBAL_MAINPEOPLECNTTOPTV.setTextSize(
//                GlobalMemberValues.GLOBAL_MAINPEOPLECNTTOPTV.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
//        );

        GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV = (TextView)findViewById(R.id.peopleCntText);
        GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV.setTextSize(
                GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        // ----------------------------------------------------------------------------------------------

        if (GlobalMemberValues.is_customerMain){

        }

        // 06.03.2022 ----------------------------------------------------------------------------------
        GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN1 = (LinearLayout)findViewById(R.id.mainleftbottomLn1);
        GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN2 = (LinearLayout)findViewById(R.id.mainleftbottomLn2);
        GlobalMemberValues.GLOBAL_directButtonsLinearLayout3 = (LinearLayout)findViewById(R.id.directButtonsLinearLayout3);
        // ----------------------------------------------------------------------------------------------

        if(GlobalMemberValues.is_customerMain){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_TABLENAME = (TextView)findViewById(R.id.customer_main_tablename);

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_PLACETOORDER = (Button) findViewById(R.id.customer_main_place_to_order);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CANCEL = (Button) findViewById(R.id.customer_main_cancel);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_MYORDER = (Button) findViewById(R.id.customer_main_my_order);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CALL = (Button) findViewById(R.id.customer_main_call);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_SLEEP = (ImageButton) findViewById(R.id.customer_main_imgbtn_sleep);

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_PLACETOORDER.setOnClickListener(customer_main_btn_OnClickListener);
//            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CANCEL.setOnClickListener(customer_main_btn_OnClickListener);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_MYORDER.setOnClickListener(customer_main_btn_OnClickListener);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CALL.setOnClickListener(customer_main_btn_OnClickListener);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_SLEEP.setOnLongClickListener(customer_main_sleep_OnClickListener);
        }


        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER = (Button)findViewById(R.id.saveorderButton);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setBackgroundResource(R.drawable.ab_imagebutton_main_saveorder);

        GlobalMemberValues.logWrite("mainppcntjjjlog", "value : " + GlobalMemberValues.now_saletypeisrestaurant + "\n");

        if (GlobalMemberValues.now_saletypeisrestaurant) {
            GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
        } else {
//            GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.GONE);
//            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2_sizeup);
            GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
        }
        if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);

            // 220902
            GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN2.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_directButtonsLinearLayout3.setVisibility(View.GONE);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);

            // 220902
            GlobalMemberValues.GLOBAL_MAINLEFTBOTTOMLN2.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_directButtonsLinearLayout3.setVisibility(View.VISIBLE);
        }

        //
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2 = (LinearLayout)findViewById(R.id.main_top_left_customer_info_ln2);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1 = (LinearLayout)findViewById(R.id.main_top_left_customer_info_ln1);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_NAME = (TextView)findViewById(R.id.main_top_left_customer_info_name);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE = (TextView)findViewById(R.id.main_top_left_customer_info_phone);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_ONLINE_SHOW = (TextView)findViewById(R.id.main_top_left_customer_info_online_order_kind);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO3 = (LinearLayout)findViewById(R.id.main_top_left_customer_info_ln3);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST = (Button)findViewById(R.id.main_top_left_order_list);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_NAME.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_ONLINE_SHOW.setTextSize(30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        if (GlobalMemberValues.isToGoSale()){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.GONE);

            String temp_str = GlobalMemberValues.getCustomerInfoByTableIdx();
            String[] temp_arr = temp_str.split("-JJJ-");
            if (temp_arr.length > 0){
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_NAME.setText(temp_arr[0]);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE.setText(temp_arr[1]);
            }

        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.VISIBLE);
        }



        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY = (TextView)findViewById(R.id.mainOrderQTYtext);
        // 04242023
        // 06072023
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY != null) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY.setTextSize(
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }



        // table name set
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME = (TextView) findViewById(R.id.main_selected_table);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME.setTextSize(26 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        GlobalMemberValues.GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN = (LinearLayout) findViewById(R.id.main_selected_table_split_ln);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_DIVIDER = (Button)findViewById(R.id.main_adddivider);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_DIVIDER.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO = (Button)findViewById(R.id.main_name_info);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY = (Button)findViewById(R.id.main_add_gratuity);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY.setOnClickListener(directButtonClickListener);


        // main side buttons
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TABLE = (LinearLayout)findViewById(R.id.button_main_side_table);

        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TABLE.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER = (LinearLayout)findViewById(R.id.button_main_side_server);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER.setOnClickListener(directButtonClickListener);
//        if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER.setVisibility(View.INVISIBLE);
//        } else {
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER.setVisibility(View.VISIBLE);
//        }
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT = (LinearLayout)findViewById(R.id.button_main_side_discount);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT.setOnClickListener(directButtonClickListener);    // discount.class 로 이동.
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_QUICK = (LinearLayout)findViewById(R.id.button_main_side_quick);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_QUICK.setOnClickListener(directButtonClickListener);   // quicksale.class 로 이동.
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT = (LinearLayout)findViewById(R.id.button_main_side_product);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT.setOnClickListener(directButtonClickListener);     // productlist.class 로 이동.
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD = (LinearLayout)findViewById(R.id.button_main_side_giftcard);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD.setOnClickListener(directButtonClickListener);    // giftcard.class 로 이동.
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GC_BALANCE = (LinearLayout)findViewById(R.id.button_main_side_gc_balance);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GC_BALANCE.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU = (LinearLayout)findViewById(R.id.button_main_side_timemenu);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setOnClickListener(directButtonClickListener);
        if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y")){
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.VISIBLE);
        } else {
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.GONE);
        }
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH = (LinearLayout)findViewById(R.id.button_main_side_menusearch);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH.setOnClickListener(directButtonClickListener);  // menusearch.class 로 이동.
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGOUT = (LinearLayout)findViewById(R.id.button_main_side_logout);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGOUT.setOnClickListener(directButtonClickListener);

        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGLIST = (LinearLayout)findViewById(R.id.button_main_side_loglist);
//        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGLIST.setVisibility(View.INVISIBLE);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGLIST.setOnClickListener(directButtonClickListener);





        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DIVIDER = (LinearLayout)findViewById(R.id.button_main_side_add_divider);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DIVIDER.setOnClickListener(directButtonClickListener);
//        if (GlobalMemberValues.isUseDividerLine()){
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DIVIDER.setVisibility(View.VISIBLE);
//        } else {
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DIVIDER.setVisibility(View.GONE);
//        }
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_BILLPRINT = (LinearLayout)findViewById(R.id.button_main_side_billprint);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_BILLPRINT.setOnClickListener(directButtonClickListener);

        // 220902
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_HOLD = (LinearLayout)findViewById(R.id.button_main_side_hold);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_HOLD.setOnClickListener(directButtonClickListener);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_RECALL = (LinearLayout)findViewById(R.id.button_main_side_recall);
        GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_RECALL.setOnClickListener(directButtonClickListener);
        if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_HOLD.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_RECALL.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TABLE.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER.setVisibility(View.GONE);
        } else {
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_HOLD.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_RECALL.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TABLE.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_SERVER.setVisibility(View.VISIBLE);
        }



        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_TABLE = (TextView)findViewById(R.id.text_main_side_table);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_TABLE.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_SERVER = (TextView)findViewById(R.id.text_main_side_server);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_SERVER.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        if (GlobalMemberValues.getStationType() == "R" || GlobalMemberValues.getStationType().equals("R")) {
            // restaurant not
            GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_SERVER.setVisibility(View.VISIBLE);
        } else {
            GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_SERVER.setVisibility(View.INVISIBLE);
        }
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_DISCOUNT = (TextView)findViewById(R.id.text_main_side_discount);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_DISCOUNT.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_QUICK = (TextView)findViewById(R.id.text_main_side_quick);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_QUICK.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_PRODUCT = (TextView)findViewById(R.id.text_main_side_product);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_PRODUCT.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_GIFTCARD = (TextView)findViewById(R.id.text_main_side_giftcard);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_GIFTCARD.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_GC_BALANCE = (TextView)findViewById(R.id.text_main_side_gc_balance);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_GC_BALANCE.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_TIMEMENU = (TextView)findViewById(R.id.text_main_side_timemenu);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_TIMEMENU.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_MENUSEARCH = (TextView)findViewById(R.id.text_main_side_menusearch);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_MENUSEARCH.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_LOGOUT = (TextView)findViewById(R.id.text_main_side_logout);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_LOGOUT.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_DIVIDER = (TextView)findViewById(R.id.text_main_side_add_divider);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_DIVIDER.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_BILLPRINT = (TextView)findViewById(R.id.text_main_side_billprint);
//        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_BILLPRINT.setText(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_LOGLIST = (TextView)findViewById(R.id.text_main_side_loglist);
//        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_LOGLIST.setVisibility(View.INVISIBLE);
        GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_SIDE_LOGLIST.setTextSize(GlobalMemberValues.BASIC_MAIN_RIGHT_SIDE_BUTTON_TEXT_SIZE);

        // GCM 관련 --------------------------------
        registBroadcastReceiver();
        // ----------------------------------------

        // 푸시메시지 인텐트로 넘어온 값이 있을 경우 ---------------------------------------------------------------------
        Intent mIntent;
        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            String pushIntentOpenType = mIntent.getStringExtra("pushIntentOpenType");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 pushIntentOpenType : " + pushIntentOpenType);
            if (!GlobalMemberValues.isStrEmpty(pushIntentOpenType)) {
                switch (pushIntentOpenType) {
                    case "reservation" : {
                        String reservtationCode = mIntent.getStringExtra("pushReservtationCode");
                        GlobalMemberValues.logWrite(TAG, "넘겨받은 pushReservtationCode : " + reservtationCode);
                        Intent subIntent = new Intent(mContext, ReservationWebPage.class);

                        subIntent.putExtra("pushIntentOpenType", "reservation");
                        subIntent.putExtra("pushReservtationCode", reservtationCode);
                        startActivity(subIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                        break;
                    }
                }
            }
            /*******************************************************************************************/
        }
        // ----------------------------------------------------------------------------------------------------------


        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        GlobalMemberValues.GLOBAL_LAYOUT_ROOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(GlobalMemberValues.GLOBAL_LAYOUT_ROOT.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        /**
         // 프린터 설정
         String tempPrinterName = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);
         GlobalMemberValues.setConnectPrinterDevice(mContext, tempPrinterName, "MAIN", true);
         **/
        // IP 설정 ----------------------------------------------------------------------------------------------------------------------------------------------
        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp = GlobalMemberValues.getNetworkPrinterIp(mContext);
        GlobalMemberValues.NETWORKPRINTERIP = tempNetworkPrinterIp;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Receipt)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp2 = GlobalMemberValues.getNetworkPrinterIp2(mContext);
        GlobalMemberValues.NETWORKPRINTERIP2 = tempNetworkPrinterIp2;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP2 + "\n");

        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp3 = GlobalMemberValues.getNetworkPrinterIp3(mContext);
        GlobalMemberValues.NETWORKPRINTERIP3 = tempNetworkPrinterIp3;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP3 + "\n");

        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp4 = GlobalMemberValues.getNetworkPrinterIp4(mContext);
        GlobalMemberValues.NETWORKPRINTERIP4 = tempNetworkPrinterIp4;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP4 + "\n");

        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp5 = GlobalMemberValues.getNetworkPrinterIp5(mContext);
        GlobalMemberValues.NETWORKPRINTERIP5 = tempNetworkPrinterIp5;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP5 + "\n");

        // 네트워크 프린터 IP 가져오기
        String tempNetworkPrinterIp6 = GlobalMemberValues.getNetworkPrinterIp6(mContext);
        GlobalMemberValues.NETWORKPRINTERIP6 = tempNetworkPrinterIp6;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP6 + "\n");

        String tempNetworkPrinterIp_master = GlobalMemberValues.getNetworkPrinterIpMaster(mContext);
        GlobalMemberValues.NETWORKPRINTERIP_MASTER = tempNetworkPrinterIp_master;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Master)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERIP + "\n");

        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        // PORT 설정 ----------------------------------------------------------------------------------------------------------------------------------------------
        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort = GlobalMemberValues.getNetworkPrinterPort(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT = tempNetworkPrinterPort;
        GlobalMemberValues.logWrite("MainNETWORKPRINTERPORT (Receipt)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT + "\n");

        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort2 = GlobalMemberValues.getNetworkPrinterPort2(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT2 = tempNetworkPrinterPort2;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT2 + "\n");

        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort3 = GlobalMemberValues.getNetworkPrinterPort3(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT3 = tempNetworkPrinterPort3;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT3 + "\n");

        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort4 = GlobalMemberValues.getNetworkPrinterPort4(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT4 = tempNetworkPrinterPort4;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT4 + "\n");

        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort5 = GlobalMemberValues.getNetworkPrinterPort5(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT5 = tempNetworkPrinterPort5;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT5 + "\n");

        // 네트워크 프린터 PORT 가져오기
        String tempNetworkPrinterPort6 = GlobalMemberValues.getNetworkPrinterPort6(mContext);
        GlobalMemberValues.NETWORKPRINTERPORT6 = tempNetworkPrinterPort6;
        GlobalMemberValues.logWrite("MainNetworkPrinterIp (Kitchen)", "printer ip : " + GlobalMemberValues.NETWORKPRINTERPORT6 + "\n");
        // ---------------------------------------------------------------------------------------------------------------------------------------------------


        // 메인 프레임 색상 설정
        GlobalMemberValues.GLOBAL_APPTOPBAR.setBackgroundColor(Color.parseColor(GlobalMemberValues.MAINFRAMECOLOR));
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_APPTOPBAR.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        //GlobalMemberValues.GLOBAL_MAINMIDDLELEFTLN.setBackgroundColor(Color.parseColor(GlobalMemberValues.MAINFRAMECOLOR));
        //GlobalMemberValues.GLOBAL_MAINMIDDLELEFTTOPLN.setBackgroundColor(Color.parseColor(GlobalMemberValues.MAINFRAMECOLOR));
        GlobalMemberValues.GLOBAL_MAINBOTTOMLN.setBackgroundColor(Color.parseColor(GlobalMemberValues.MAINFRAMECOLOR));

        //02232024 Hide parts of layout for QSR mode.
        if (GlobalMemberValues.getStationType().equals("Q")) {
            LinearLayout main_selected_table_linear_layout = (LinearLayout) findViewById(R.id.main_selected_table_linear_layout);
            LinearLayout mainOrderQTYLn = (LinearLayout) findViewById(R.id.mainOrderQTYLn);

            mainOrderQTYLn.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setVisibility(View.INVISIBLE);
            main_selected_table_linear_layout.setVisibility(View.INVISIBLE);
        }

        //02232024 adjust side view buttons to be visible when station type is QSR.
        if (GlobalMemberValues.getStationType().equals("Q")) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_SIDE_VIEW.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_BOTTOM_MENUS_VIEW.setVisibility(View.GONE);
        }
    }
    /***********************************************************************************/

    View.OnClickListener directButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainRightBottom_MainSale : {
                    //GlobalMemberValues.displayDialog(context, "", "여기!!!!!", "Close");
                    if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                    } else {
                        GlobalMemberValues.setFrameLayoutVisibleChange("main");

                        // Lite 버전 관련
                        if (GlobalMemberValues.isLiteVersion()) {
                            // 하단버튼 초기화
                            GlobalMemberValues.setInitMainBottomButtonBg();
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONMAINSALE.setBackgroundResource(R.drawable.aa_images_main_main_rollover_lite);
                        }
                    }
                    break;

                }
                case R.id.directButtonHoldButton : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    CommandButton.setHoldSalesCheck();
                    break;
                }
                case R.id.directButtonRecallButton: {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    CommandButton.setRecallCheck();
                    break;
                }

                case R.id.topEmployeeInfo : {
                    /**
                     if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                     } else {
                     GlobalMemberValues.setFrameLayoutVisibleChange("employeeSelection");
                     EmployeeSelection emp = new EmployeeSelection(mActivity, mContext, dataAtSqlite);
                     emp.setDepartEmpSwitch();
                     }
                     **/

//                    setEmployeeLogout();
//                    if (EmployeeSelectionPopup.openCount == 0) {
//                        Intent employeeSelectionPopup = new Intent(mContext.getApplicationContext(), EmployeeSelectionPopup.class);
//                        employeeSelectionPopup.putExtra("main_side","Y");
//                        mActivity.startActivity(employeeSelectionPopup);
//                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
//                    }
                    GlobalMemberValues gm = new GlobalMemberValues();
                    if (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R")) {
                        Intent intent = new Intent(mContext.getApplicationContext(), EmployeeKeyIn.class);
                        intent.putExtra("main_side","M");
                        intent.putExtra("table_idx_arr_list", TableSaleMain.mTableIdxArrList);
                        mActivity.startActivity(intent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                        }
                    }





                    break;
                }

                case R.id.mainRightBottom_Employee : {
                    setEmployeeLogout();

                    break;
                }
                case R.id.main_info_btn : {
                    informationPopup = new InformationPopup(mContext,GlobalMemberValues.getAppVersionName(mContext),info_close_listener);
                    informationPopup.show();

                    break;
                }

                case R.id.bottomTextViewPOSVersionInfo : {
                    informationPopup = new InformationPopup(mContext,GlobalMemberValues.getAppVersionName(mContext),info_close_listener);
                    informationPopup.show();

                    break;
                }

                case R.id.saveorderButton : {
                    LogsSave.saveLogsInDB(93);

                    // 09302024
                    if (MainMiddleService.mGeneralArrayList.size() > 0) {
                        // 09302024
                        if (GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)) {
                            MainMiddleService.mHoldCode = MainMiddleService.mGeneralArrayList.get(0).mHoldCode;
                        }
                    }

                    //SelectGetFoodType.openHereToGoInfoIntent("T");
                    GlobalMemberValues.mIsClickSendToKitchen = true;
                    Payment.openGetFoodTypeIntent("");
                    //clickSendToKitchen();
                    TableSaleMain.mSelectedTablesArrList.clear();


                    // 07212024 - TOrder Send Data
                    // GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity);


                    break;
                }
                case R.id.button_main_side_table : {
                    LogsSave.saveLogsInDB(100);

                    if (MainMiddleService.mGeneralArrayList.size() > 0) {
                        // 09302024
                        if (GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)) {
                            MainMiddleService.mHoldCode = MainMiddleService.mGeneralArrayList.get(0).mHoldCode;
                        }


                        // 07282024 --------------------------------------
                        //SelectGetFoodType.openHereToGoInfoIntent("T");
                        GlobalMemberValues.mIsClickSendToKitchen = true;
                        Payment.openGetFoodTypeIntent("");
                        //clickSendToKitchen();
                        TableSaleMain.mSelectedTablesArrList.clear();
                        // 07282024 --------------------------------------
                    } else {
                        // 메인 뷰에서 테이블 Close 후 다른 새 테이블로 들어왔을때 Cart List 가 초기화되지 않는 현상이 있어 추가함.
                        MainMiddleService.initList();
                        GlobalMemberValues.openRestaurantTable();

                    }


                    // 07282024
                    // 아래 if 구문을 모두 주석처리

/**
 if (MainMiddleService.mGeneralArrayList.size() > 0) {
 //07182024 adjust way of getting string value of mGeneralArrayList
 StringBuilder mGeneralArrayListString = new StringBuilder();
 //                        for(TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList){
 //                            mGeneralArrayListString.append(tempSaleCart.returnTempCartString());
 //                        }

 for(TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList) {
 if (tempSaleCart.returnTempCartString().toLowerCase().contains("discount") ||
 tempSaleCart.returnTempCartString().toLowerCase().contains(GlobalMemberValues.mCommonGratuityName.toLowerCase())) {
 } else {
 mGeneralArrayListString.append(tempSaleCart.returnTempCartString());
 }
 }

 if(mGeneralArrayListString.toString().equals(MainActivity.temp_str_salecart)){
 //if ((MainMiddleService.mGeneralArrayList.toString().equals(temp_str_salecart))) {
 MainMiddleService.initList();
 GlobalMemberValues.openRestaurantTable();
 } else {
 //07182024 this part isn't needed anymore.
 //                            if (temp_str_salecart_cnt > 0) {
 //                                if (temp_str_salecart_cnt == MainMiddleService.mGeneralArrayList.size()) {
 //                                    MainMiddleService.initList();
 //                                    GlobalMemberValues.openRestaurantTable();
 //                                } else {
 //                                    GlobalMemberValues.displayDialog(mContext, "Warning",
 //                                            "There is an added menu\nPlease print the kitchen or delete the added menu", "Close");
 //                                }
 //                                return;
 //                            }

 Popup_to_go_table_3btn popup_to_go_table = new Popup_to_go_table_3btn(
 mContext, "","There is an ordered menu. Would you like to print into the kitchen?", new CustomDialogClickListener() {
@Override
public void onPositiveClick() {
String tempHoldCode = MainMiddleService.mHoldCode;
CommandButton.setHoldSales("");
// bill 프린팅 여부
GlobalMemberValues.openRestaurantTable();
// 키친 프린팅 실행
TableSaleMain.kitchenPrint(tempHoldCode, false);
}

@Override
public void onMiddClick() {

String tempHoldCode = MainMiddleService.mHoldCode;
CommandButton.setHoldSales("");
// bill 프린팅 여부
GlobalMemberValues.openRestaurantTable();

//////

//                            GlobalMemberValues.mCancelBtnClickYN = "Y";
//                            setEmptyInSaleCart(false);
}

@Override
public void onNegativeClick() {
GlobalMemberValues.mCancelBtnClickYN = "Y";
MainMiddleService.setEmptyInSaleCart(false);
}
});
 popup_to_go_table.setCanceledOnTouchOutside(true);
 popup_to_go_table.setCancelable(true);
 popup_to_go_table.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
 popup_to_go_table.show();

 }
 //                    if (false) {
 //                        new AlertDialog.Builder(mActivity)
 //                                .setTitle("There is an ordered menu")
 //                                .setMessage("Would you like to go to the table board?")
 //                                .setNegativeButton("No", null)
 //                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
 //                                    @Override
 //                                    public void onClick(DialogInterface dialog, int which) {
 //
 //
 //                                        String tempHoldCode = MainMiddleService.mHoldCode;
 //
 //                                        CommandButton.setHoldSales("");
 //
 //                                        // bill 프린팅 여부
 //                                        setBillKitchenPrintingInSaveOrder(tempHoldCode);
 //
 //                                        // 키친 프린팅 실행
 //                                        TableSaleMain.kitchenPrint(tempHoldCode, false);
 //
 ////                                        MainMiddleService.clearOnlyListView();
 //
 ////                                        Intent intent = new Intent(MainActivity.mContext, TableSaleMain.class);
 ////                                        mActivity.startActivity(intent);
 ////                                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
 //
 //                                    }
 //                                }).show();



 } else {
 // 메인 뷰에서 테이블 Close 후 다른 새 테이블로 들어왔을때 Cart List 가 초기화되지 않는 현상이 있어 추가함.
 MainMiddleService.initList();
 GlobalMemberValues.openRestaurantTable();
 }

 **/





                    TableSaleMain.isClickCommandOnTable = false;


                    // 07212024 - TOrder Send Data
                    // GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity);


                    break;
                }
                case R.id.button_main_side_server : {
                    LogsSave.saveLogsInDB(101);
                    Intent intent = new Intent(mContext.getApplicationContext(), EmployeeKeyIn.class);
                    intent.putExtra("main_side","M");
                    intent.putExtra("table_idx_arr_list", TableSaleMain.mTableIdxArrList);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }

//                    if (EmployeeSelectionPopup.openCount == 0) {
//                        Intent employeeSelectionPopup = new Intent(mContext.getApplicationContext(), EmployeeSelectionPopup.class);
//                        employeeSelectionPopup.putExtra("main_side","Y");
//                        mActivity.startActivity(employeeSelectionPopup);
//                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
//                    }
                    break;
                }

                case R.id.button_main_side_gc_balance : {
                    Intent GiftCardBalanceCheck = new Intent(MainActivity.mContext, com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GiftCardBalanceCheck.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(GiftCardBalanceCheck);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.button_main_side_timemenu : {
                    if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
                        GlobalMemberValues.setNowTimeCodeValue();
                        GlobalMemberValues.openTimeMenuSelectPopup();
                    } else {
                        GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                                "Please change whether to use the time menu in the [ Settings > System ] to 'Active'", "Close");
                    }
                    break;
                }

                case R.id.button_main_side_logout : {
                    LogsSave.saveLogsInDB(108);
                    setEmployeeLogout();
                    break;
                }

                case R.id.button_main_side_add_divider : {
                    MainMiddleService.setInsertDividerLine();
                    break;
                }
                case R.id.main_adddivider : {
                    LogsSave.saveLogsInDB(95);
                    MainMiddleService.setInsertDividerLine();
                    break;
                }
                case R.id.main_name_info : {
                    Payment.openGetFoodTypeIntent("");
                    break;
                }
                case R.id.button_main_side_billprint:{
                    TableSaleMain.openBillPrint("Y");
                    break;
                }
                case R.id.main_add_gratuity:{
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                    break;
                }

                // 06.01.2022 -------------------------------------------------------------------------------------------------
                case R.id.mainPeopleCntLn:{
                    if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
                        String tempTableIdx = TableSaleMain.mTableIdxArrList.get(0);
                        Intent intent = new Intent(mContext.getApplicationContext(), TablePeopleCnt.class);
                        intent.putExtra("selectedTableIdx", tempTableIdx);
                        intent.putExtra("selectedHoldCode", TableSaleMain.getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum));
                        intent.putExtra("frommain", "Y");
                        mActivity.startActivity(intent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    }

                    break;
                }
                // ------------------------------------------------------------------------------------------------------------
                case R.id.button_main_side_hold:
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    CommandButton.setHoldSalesCheck();
                    break;
                case R.id.button_main_side_recall:
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    CommandButton.setRecallCheck();
                    break;

                case R.id.button_main_side_loglist :
                    Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), LogHistory.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    // -------------------------------------------------------------------------------------
                    intent.putExtra("holdcode",MainMiddleService.mHoldCode);
                    startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }

                    break;
            }
        }
    };

    View.OnClickListener customer_main_btn_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customer_main_place_to_order: {

                    View dialogView = getLayoutInflater().inflate(R.layout.customer_main_placetoorder_dialog, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogView);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    Button ok_btn = dialogView.findViewById(R.id.ok_btn);
                    ok_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            b_customer_place_to_order = true;
                            if (MainMiddleService.mGeneralArrayList == null || MainMiddleService.mGeneralArrayList.size() <= 0) return;
                            Intent myorder = new Intent(mContext.getApplicationContext(), CustomerMainMyOrderActivity.class);
                            myorder.putExtra("gratuity",GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.getText().toString());
                            myorder.putExtra("subtotal",GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.getText().toString());
                            myorder.putExtra("tax",GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.getText().toString());
                            myorder.putExtra("total",GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString());
                            mActivity.startActivity(myorder);

                            GlobalMemberValues.mIsClickSendToKitchen = true;
                            Payment.openGetFoodTypeIntent("");

                            alertDialog.dismiss();
                        }
                    });

                    Button cancle_btn = dialogView.findViewById(R.id.cancle_btn);
                    cancle_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    break;
                }
                case R.id.customer_main_cancel: {
                    break;
                }
                case R.id.customer_main_my_order: {

                    break;
                }
                case R.id.customer_main_call: {
                    break;
                }
            }
        }
    };

    View.OnLongClickListener customer_main_sleep_OnClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if ((mActivity != null) && (!mActivity.isFinishing())) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("Logout")
                        .setMessage("Do you want to logout?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                employeeLogout();
                            }
                        }).show();
            }
            return false;
        }
    };

    public static void clickSendToKitchen() {
        GlobalMemberValues.logWrite("orderkitchenprintinglog", "holdcode : " + MainMiddleService.mHoldCode + "\n");

        String tempHoldCode = MainMiddleService.mHoldCode;

        CommandButton.setHoldSales("");



        // 키친 프린팅 실행
        Recall.mKitchenPrintOnRecall = "Y";             // Recall.mKitchenPrintOnRecall 의 값이 N 일 경우 프린트 안된 것만 프린트됨
        GlobalMemberValues.mCancelKitchenPrinting = "N";
        GlobalMemberValues.mDeletedSaleCartIdx = "";

//                    TableSaleMain.kitchenPrint(tempHoldCode, false);

        if (GlobalMemberValues.B_FIRSTORDER_YN){
            Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
            TableSaleMain.kitchenPrint(tempHoldCode, false);
            // bill 프린팅 여부
            setBillKitchenPrintingInSaveOrder(tempHoldCode);
        } else {
            if ((mActivity != null) && (!mActivity.isFinishing())) {
//                            new AlertDialog.Builder(mContext)
//                                    .setTitle("Kitchen Printing")
//                                    .setMessage("Are you sure you want to reprint in the kitchen?")
//                                    //.setIcon(R.drawable.ic_launcher)
//                                    .setPositiveButton("Yes",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
//                                                    TableSaleMain.kitchenPrint(tempHoldCode, false);
//                                                    // bill 프린팅 여부
//                                                    setBillKitchenPrintingInSaveOrder(tempHoldCode);
//                                                }
//                                            })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
//                                                // bill 프린팅 여부
//                                                setBillKitchenPrintingInSaveOrder(tempHoldCode);
//                                            }
//                                        }
//                                    })
//                                    .show();

                GlobalMemberValues.logWrite("orderkitchenprintinglog", "여기 왔음..." + "\n");

                Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
                TableSaleMain.kitchenPrint(tempHoldCode, false);
                // bill 프린팅 여부
                setBillKitchenPrintingInSaveOrder(tempHoldCode);

//                Popup_to_go_table popup_to_go_table = new Popup_to_go_table(mContext, "Kitchen Printing","Are you sure you want to reprint in the kitchen?", new CustomDialogClickListener(){
//                    @Override
//                    public void onPositiveClick() {
//                        Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
//                        TableSaleMain.kitchenPrint(tempHoldCode, false);
//                        // bill 프린팅 여부
//                        setBillKitchenPrintingInSaveOrder(tempHoldCode);
//                    }
//                    @Override
//                    public void onNegativeClick() {
//                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
//                            // bill 프린팅 여부
//                            setBillKitchenPrintingInSaveOrder(tempHoldCode);
//                        }
//                    }
//                });
//                popup_to_go_table.setCanceledOnTouchOutside(true);
//                popup_to_go_table.setCancelable(true);
//                popup_to_go_table.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                popup_to_go_table.show();
            }
        }
    }

    private static void setBillKitchenPrintingInSaveOrder(String paramHoldCode) {
//        if ((mActivity != null) && (!mActivity.isFinishing())) {
//            new AlertDialog.Builder(mContext)
//                    .setTitle("")
////                    .setMessage("Bill Printing")
//                    .setMessage("Do you want to print Bill?")
//                    //.setIcon(R.drawable.ic_launcher)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Bill 프린팅 실행
//                                    try {
//                                        String mTableIdx = GlobalMemberValues.mSelectedTableIdx;
//                                        int mergednum = GlobalMemberValues.getIntAtString(
//                                                MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + mTableIdx + "%' ")
//                                        );
//
//                                        String strQuery = "";
//                                        if (mergednum > 0) {
//                                            strQuery = "select top 1 billnum from temp_salecart " +
//                                                    " where mergednum = '" + mergednum + "' " +
//                                                    " order by billnum asc";
//                                        } else {
//                                            strQuery = "select top 1 billnum from temp_salecart " +
//                                                    " where tableIdx like '%" + mTableIdx + "%' and subtablenum = '1' " +
//                                                    " order by billnum asc";
//                                        }
//
//                                        String tempBillNum = MssqlDatabase.getResultSetValueToString(strQuery);
//                                        if (GlobalMemberValues.isStrEmpty(tempBillNum)) {
//                                            tempBillNum = "1";
//                                        }
//
//                                        String tableInfos = mergednum + "-JJJ-" + mTableIdx + "-JJJ-" + TableSaleMain.mSubTableNum + "-JJJ-" + tempBillNum + "-JJJ-" + "END";
//                                        GlobalMemberValues.phoneorderPrinting(paramHoldCode, "R", tableInfos);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    openTableMain();
//                                }
//                            })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            openTableMain();
//                        }
//                    })
//                    .show();
//        }

        if (GlobalMemberValues.is_customerMain){

        } else {
            // 230731 // Tablesalemain 진입전 Mainmiddleservice 초기화 추가.
            MainMiddleService.initList();
            GlobalMemberValues.openRestaurantTable();
        }

    }

    private View.OnClickListener jjj_positive = new View.OnClickListener() {
        public void onClick(View v) {

            customAlert.dismiss();
        }
    };

    private View.OnClickListener jjj_negative = new View.OnClickListener() {
        public void onClick(View v) {

            customAlert.dismiss();
        }
    };

    /**
     public static void checkAppPermission(Activity paramActivity, Context paramContext, String paramCheckType) {
     // 파일읽기 권한 체크
     switch (paramCheckType) {
     // 전화걸기 관련
     case "PHONECALL" : {
     resultPhoneCallPermission = "N";
     AppPermissionCheckClass.checkPermission(paramActivity, paramContext, Manifest.permission.CALL_PHONE);
     if (AppPermissionCheckClass.mTempPermissionThreadFlag == 0) {
     Log.i("PermissionTestResult", "result : " + AppPermissionCheckClass.returnPermissionValue + "\n");
     }
     break;
     }

     // 파일읽기 관련
     case "FILEREAD" : {
     resultReadStoragePermission = "N";
     AppPermissionCheckClass.checkPermission(paramActivity, paramContext, Manifest.permission.READ_EXTERNAL_STORAGE);
     if (AppPermissionCheckClass.mTempPermissionThreadFlag == 0) {
     Log.i("PermissionTestResult", "result : " + AppPermissionCheckClass.returnPermissionValue + "\n");
     }
     break;
     }

     // 파일쓰기 관련
     case "FILEWRITE" : {
     resultWriteStoragePermission = "N";
     AppPermissionCheckClass.checkPermission(paramActivity, paramContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
     if (AppPermissionCheckClass.mTempPermissionThreadFlag == 0) {
     Log.i("PermissionTestResult", "result : " + AppPermissionCheckClass.returnPermissionValue + "\n");
     }
     break;
     }

     }
     }
     **/

    public void setDisplayValue() {
        DisplayMetrics displayMt = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMt);
        GlobalMemberValues.TABLE_WIDTH  = displayMt.widthPixels;
        GlobalMemberValues.TABLE_HEIGHT = displayMt.heightPixels;

        GlobalMemberValues.logWrite("DisplayValue", "Width : " + GlobalMemberValues.TABLE_WIDTH + "\n");
        GlobalMemberValues.logWrite("DisplayValue", "Height : " + GlobalMemberValues.TABLE_HEIGHT + "\n");
    }

    // 바로가기 아이콘 생성 메소드
    private void addShortcut(Context context) {
        Intent shortcutIntent = new Intent();
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.setClassName(context, getClass().getName());
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,iconResource);
        intent.putExtra("duplicate", false);
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        sendBroadcast(intent);
    }

    /** GCM 관련 ***********************************************************************************/

    // 06.01.2022 ----------------------------------------------------------------------------
    // 고객 수 버튼 관련
    public static void setMainPeopleCntLn() {
        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
            GlobalMemberValues.logWrite("mainppcntjjjlog", "여기1" + "\n");
            if (GlobalMemberValues.isViewCustomerNumbers()) {

                GlobalMemberValues.logWrite("mainppcntjjjlog", "여기2" + "\n");

                if (GlobalMemberValues.isToGoSale()) {
                    GlobalMemberValues.logWrite("mainppcntjjjlog", "여기3_1" + "\n");

                    GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setVisibility(View.GONE);
                } else {
                    GlobalMemberValues.logWrite("mainppcntjjjlog", "여기3_2" + "\n");

                    GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setVisibility(View.VISIBLE);

                    // salon_store_restaurant_table_peoplecnt 테이블에서 고객수 구하기
                    String tempTableIdx = TableSaleMain.mTableIdxArrList.get(0);

                    String tempCustCnt = MssqlDatabase.getResultSetValueToString(
                            " select peoplecnt from salon_store_restaurant_table_peoplecnt " +
                                    " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum) + "' "
                    );
                    GlobalMemberValues.getMainCustomerPeopleCnt(TableSaleMain.getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum));

                }
            } else {
                if (GlobalMemberValues.isToGoSale()) {
                    GlobalMemberValues.logWrite("mainppcntjjjlog", "여기3_1" + "\n");

                    GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setVisibility(View.GONE);
                } else {
                    GlobalMemberValues.logWrite("mainppcntjjjlog", "여기3_2" + "\n");

                    GlobalMemberValues.GLOBAL_MAINPEOPLECNTLN.setVisibility(View.VISIBLE);

                    // salon_store_restaurant_table_peoplecnt 테이블에서 고객수 구하기
                    String tempTableIdx = TableSaleMain.mTableIdxArrList.get(0);

                    String tempCustCnt = MssqlDatabase.getResultSetValueToString(
                            " select peoplecnt from salon_store_restaurant_table_peoplecnt " +
                                    " where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum) + "' "
                    );
                    GlobalMemberValues.getMainCustomerPeopleCnt(TableSaleMain.getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum));

                }

            }
        } else {
            if (GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV != null){
                GlobalMemberValues.GLOBAL_MAINPEOPLECNTTV.setText("");
            }
        }

    }
    // --------------------------------------------------------------------------------------------


    //화면에서 앱이 동작할 때 LocalBoardcastManager를 등록합니다.
    @Override
    protected void onResume() {
        super.onResume();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("registrationComplete"));
        //proDial2 = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, "System is loading...", true, false);

        GlobalMemberValues.logWrite("endtimechecklog", "안드로이드 버전 : " + Build.VERSION.SDK_INT + "\n");
        GlobalMemberValues.logWrite("endtimechecklog", "여기3" + "\n");
        GlobalMemberValues.logWrite("starttimelog", "on resume 시작....\n");

//        String tempjjjstr = "t07212022j";
//        GlobalMemberValues.logWrite("jjjstrtestjjjlog", "getValue1 : " + GlobalMemberValues.getJJJSubString(tempjjjstr, 1, 8) + "\n");
//        GlobalMemberValues.logWrite("jjjstrtestjjjlog", "getValue1 : " + GlobalMemberValues.getJJJSubString(tempjjjstr, 9, 1) + "\n");


        mActivity = this;
        mContext = this;
        GlobalMemberValues.b_isLoginView = false;
        GlobalMemberValues.b_isMenuPrinted = false;

        GlobalMemberValues.mIsClickSendToKitchen = false;
        GlobalMemberValues.mIsClickPayment = false;

        GlobalMemberValues.mTempCustomerInfo = "";

        // 08.16.2022
        GlobalMemberValues.isDeviceTabletPCFromDB();

        // 테이블 zone idx 를 global zone idx 에 저장
        GlobalMemberValues.mGlobal_selectedZoneIdx = TableSaleMain.mSelectedZoneIdx;

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX != null){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX.isChecked()){
                GlobalMemberValues.ALL_CHECKBOX_INIT = true;
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX.setChecked(false);
            }
        }



        // 05202024
        if (GlobalMemberValues.isTOrderUse()) {
            if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() > 0) {
                String tableIdx = TableSaleMain.mTableIdxArrList.get(0).toString();
                if (!GlobalMemberValues.isStrEmpty(tableIdx)) {
                    tableIdx = tableIdx.replace("T", "");
                }

                GlobalMemberValues.logWrite("settableidxincloudstr", "tableIdx : " + tableIdx + "\n");

                GlobalMemberValues.SAVEORDELETE = "ins";
                GlobalMemberValues.setTableIdxInCloud(mContext, mActivity);
            }
        }





        // 장바구니 메뉴가 담길때만 send to kitchen 보이게
        if (MainMiddleService.mSaleCartAdapter == null){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null)
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
//            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT != null)
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.INVISIBLE);
        } else {
            if (MainMiddleService.mGeneralArrayList != null){
                //07182024 stop changing bill print button visibility
                if (MainMiddleService.mGeneralArrayList.size() == 0) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null)
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
                    //if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT != null)
                    //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.INVISIBLE);
                } else {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
                    //if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT != null)
                    //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.VISIBLE);
                }
            } else {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null)
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
                //if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT != null)
                //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.INVISIBLE);
            }
        }

        if (TableSaleMain.isClickCommandOnTable){
            // table main 에서 command 버튼으로 mainactivity 를 들어온경우 hold, recall 버튼은 숨긴다
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setVisibility(View.INVISIBLE);
        }

        // mssql 연결 및 테이블 생성 ---------------------------------------------------------------------------
        GlobalMemberValues.setConnectMSSQL();                           // 연결

//        if (GlobalMemberValues.isPossibleMssqlInfo()) {
//            GlobalMemberValues.logWrite("mssqllog", "mssql 여기진입...2" + "\n");
//
//            if (GlobalMemberValues.setConnectMssql()) {
//                // 테이블 생성
//                MssqlCreateTables.createTablesForMSSQL();
//                // 테이블 수정
//                MssqlAlterTables.alterTablesForMSSQL();
//            }
//        }
        // --------------------------------------------------------------------------------------------------

        if (GlobalMemberValues.is_customerMain){
            if (GlobalMemberValues.str_selected_table_name != null && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_TABLENAME != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_TABLENAME.setText(GlobalMemberValues.str_selected_table_name);
            }
        }


        // 06.01.2022
        setMainPeopleCntLn();

        boolean isOpenTableBoard = true;

        GlobalMemberValues.logWrite("billpaycontinuelogjjj", "on main : " + GlobalMemberValues.mIsOnPaymentProcessForBillPay + "\n");

        // bill pay 가 진행중인데, bill pay 가 완료되지 않았을 경우 ====================================================
        // bill pay 을 계속 진행할 수 있도록 bill split/merge 창을 오픈한다.
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCodeForBillPay_on)
                && GlobalMemberValues.mIsOnPaymentProcessForBillPay) {
//            GlobalMemberValues.logWrite("billpaycontinuelogjjj", "GlobalMemberValues.mHoldCodeForBillPay_on : " + GlobalMemberValues.mHoldCodeForBillPay_on + "\n");
//            GlobalMemberValues.logWrite("billpaycontinuelogjjj", "GlobalMemberValues.mTableIdxOnBillPay_on : " + GlobalMemberValues.mTableIdxOnBillPay_on + "\n");
//            GlobalMemberValues.logWrite("billpaycontinuelogjjj", "GlobalMemberValues.mSubTableNumOnBillPay_on : " + GlobalMemberValues.mSubTableNumOnBillPay_on + "\n");
            String tempQuery = " select count(*) from bill_list where " +
                    " holdcode = N'" + GlobalMemberValues.mHoldCodeForBillPay_on + "'" +
                    " and paidyn = N'N' ";
            GlobalMemberValues.logWrite("jjjbilllog", "tempQuery : " + tempQuery + "\n");
            int remainBillCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
            if (remainBillCnt > 0) {
                TableSaleMain.mSelectedTablesArrList.clear();
                TableSaleMain.mSelectedTablesArrList.add(GlobalMemberValues.mTableIdxOnBillPay_on);

                TableSaleMain.mSubTableNum = GlobalMemberValues.mSubTableNumOnBillPay_on;

                TableSaleMain.openBillSplitMerge();

                GlobalMemberValues.logWrite("billopenjjjj", "bill 창 오픈됨" + "\n");

                isOpenTableBoard = false;

                GlobalMemberValues.mHoldCodeForBillPay_on = "";
                GlobalMemberValues.mTableIdxOnBillPay_on = "";
                GlobalMemberValues.mSubTableNumOnBillPay_on = "";
            } else {
                GlobalMemberValues.isPaymentByBills = false;
                isOpenTableBoard = true;
            }
        } else {
            isOpenTableBoard = true;
        }

        // =======================================================================================================

        if (GlobalMemberValues.isOpenTableSaleMain && isOpenTableBoard) {
            GlobalMemberValues.logWrite("endtimechecklog", "테이블창 오픈" + "\n");
            GlobalMemberValues.openRestaurantTable();
        }

        GlobalMemberValues.mIsOnPaymentProcessForBillPay = false;

        // Restaurant 관련
        GlobalMemberValues.now_iskitchenprinting = true;

        if (JJJ_SignPad.proDial != null && JJJ_SignPad.proDial.isShowing()) {
            JJJ_SignPad.proDial.dismiss();
        }

//        if (MainActivity.proCustomDial != null && MainActivity.proCustomDial.isShowing()) {
        if (MainActivity.proCustomDial != null) {
            GlobalMemberValues.logWrite("starttimelog", "on resume 진행중....\n");
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            MainActivity.proCustomDial.dismiss();
        }

        // Elo 관련
        if (mApiAdapter == null) {
            //setEloInit();
        }
        if (mApiAdapter != null) {
            try {
                mApiAdapter.getActivityMonitor().onActivityEvent(ActivityMonitor.EVENT_ON_RESUME);
            } catch (Exception e) {
                GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
            }
        }

        // Clover 관련
        GlobalMemberValues.setCloverConnect(mContext);
        mTypeFace_clover = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");

        //jihun park sub display
        mMediaRouter.addCallback(MediaRouter.ROUTE_TYPE_LIVE_VIDEO, mMediaRouterCallback);
        updatePresentation();

        // 프린터 종류
        GlobalMemberValues.GLOBAL_PRINTERNAME = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);
        switch (GlobalMemberValues.GLOBAL_PRINTERNAME) {
            case "Clover Station" : {
                GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER = 30;
                GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER = 384;
                break;
            }
            case "Clover Mini" : {
                GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER = 24;
                GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER = 384;
                break;
            }
        }

        if (GlobalMemberValues.isDeviceClover()) {
            if (GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode)
                    || GlobalMemberValues.getCountSaleCart(MainMiddleService.mHoldCode) == 0) {
                GlobalMemberValues.clearCloverDisplay();
            }
        }

        // PAX 관련
        GlobalMemberValues.mDevicePAX = GlobalMemberValues.isDevicePAXFromDB();

        // 1-2. 바코드 스캐닝 처리 -------------------------------------------------------------------------------------
        if (GlobalMemberValues.isDeviceClover()) {
            MainActivity.barcodeReceiver_clover = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    BarcodeResult barcodeResult = new BarcodeResult(intent);
                    if (barcodeResult.isBarcodeAction()) {
                        String barcode = intent.getStringExtra("Barcode");
                        if (barcode != null) {
                            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.BARCODESCANVALUE_ONCLOVER)) {
                                GlobalMemberValues.logWrite("cloverbarcodescan", "now : " + GlobalMemberValues.BARCODESCANVALUE_ONCLOVER + "\n");
                                switch (GlobalMemberValues.BARCODESCANVALUE_ONCLOVER) {
                                    case "productlist" : {
                                        ProductList.mProductInfoEditText.setText("");
                                        ProductList.mProductInfoEditText.setText(barcode);
                                        break;
                                    }
                                    case "discount" : {
                                        Discount.discountCouponNumberEditText.setText("");
                                        Discount.discountCouponNumberEditText.setText(barcode);
                                        break;
                                    }
                                    case "giftcard" : {
                                        GiftCard.mGiftCardNumberEditText.setText("");
                                        GiftCard.mGiftCardNumberEditText.setText(barcode);
                                        break;
                                    }
                                    case "customersearch" : {
                                        CustomerSearch.mCustomerInfoEditText.setText("");
                                        CustomerSearch.mCustomerInfoEditText.setText(barcode);
                                        break;
                                    }
                                }
                            }

                            //ProductList.mProductInfoEditText.setNextFocusDownId(R.id.topSalonNameLogo);
                        }
                    }
                }
            };
            registerReceiver(MainActivity.barcodeReceiver_clover, new IntentFilter(BarcodeResult.INTENT_ACTION));
        }
        // ------------------------------------------------------------------------------------------------------------

        // BatchSummary 객체생성
        /**
         if (GlobalMemberValues.APPOPENCOUNT == 0) {
         if (GlobalMemberValues.isAutoBatchInCashInOut(mDbInit)) {
         CommandButton.openBatchSummary("Y");
         }
         }
         **/

        // 현재 타임메뉴(TimeMenu) 시간대를 설정한다.
        GlobalMemberValues.setSelectedTimeCodevalue();

        // 앱 Instance ID 가 없을 때 가져온다.
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.APP_INSTANCE_ID)) {
            GlobalMemberValues.getInstanceIdToken();
        }

        // MainActivity 가 다른 곳에서 데이터다운로드 후 recreate 된 경우
        if (GlobalMemberValues.ITEMCANCELAPPLY == 1) {
            MainMiddleService.clearListView();
            GlobalMemberValues.setCustomerInfoInit();
            GlobalMemberValues.setEmployeeInfoInit();

            GlobalMemberValues.logWrite("MainActivityLogTest", "여기 Resume1 \n");
        }

        // MainActivity 가 다른 곳에서 데이터다운로드 후 recreate 된 경우
        // GlobalMemberValues.ITEMCANCELAPPLY 을 0 으로 돌려놓는다...
        GlobalMemberValues.ITEMCANCELAPPLY = 0;

        // Notification 관련 isOpenApplication 값 설정 --------------------------
        isOpenApplication = true;
        // ---------------------------------------------------------------------

        /**
         GlobalMemberValues.displayDialog(mContext, "Info", "Instance ID 1 : " + GlobalMemberValues.APP_INSTANCE_ID
         + "\nInstance ID 2 : " + FirebaseInstanceId.getInstance().getToken(), "Close");
         **/

        GlobalMemberValues.logWrite("MainActivityResume", "재시작...\n");
        // API 로 Instance ID 전송 ---------------------------------------------------------------------
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.APP_INSTANCE_ID)) {
            if (GlobalMemberValues.APPINSTANCEIDUP == 0) {
                GlobalMemberValues.sendAppInstanceIdByApi(
                        MainActivity.mContext, GlobalMemberValues.APP_INSTANCE_ID);
                GlobalMemberValues.logWrite("MainActivityResume", "여기실행..\n");
                GlobalMemberValues.logWrite("MainActivityLogTest", "여기 Resume2 \n");
            }
        }
        // ----------------------------------------------------------------------------------------------

        // 서명이미지 삭제전 기다리는 시간 초기화
        GlobalMemberValues.DELETEWAITING = 0;
        GlobalMemberValues.logWrite("waitingtime", "기다리는 시간_in main : " + GlobalMemberValues.DELETEWAITING + "\n");

        // 타이머가 실행되지 않았으면 실행한다.
        /**
         if (GlobalMemberValues.TIMER_ONLINECHECK_START_YN == "N" || GlobalMemberValues.TIMER_ONLINECHECK_START_YN.equals("N")) {
         runTimer_checkOnline();
         }
         **/

        if (GlobalMemberValues.TIMEMENUSET_AUTOOPEN.equals("Y") || GlobalMemberValues.TIMEMENUSET_AUTOOPEN == "Y"
                || GlobalMemberValues.TIMEMENUSET_AUTOOPEN.equals("A") || GlobalMemberValues.TIMEMENUSET_AUTOOPEN == "A") {
            if (GlobalMemberValues.TIMER_TIMEMENU_START_YN == "N" || GlobalMemberValues.TIMER_TIMEMENU_START_YN.equals("N")) {
                runTimer_timemenu();
            }
        }
        if (GlobalMemberValues.isOnlineOrderUseYN()) {
            if (GlobalMemberValues.TIMER_ONLINEORDERSCHECK_START_YN == "N" || GlobalMemberValues.TIMER_ONLINEORDERSCHECK_START_YN.equals("N")) {
                runTimer_newWebOrder();
            }
        }

        GlobalMemberValues gm = new GlobalMemberValues();
        if (gm.isPOSWebPay()) {
            if (GlobalMemberValues.TIMER_TABLEPAY_START_YN == "N" || GlobalMemberValues.TIMER_TABLEPAY_START_YN.equals("N")) {
                runTimer_newTablePay();
            }
        }
        if (GlobalMemberValues.isCloudKitchenPrinter()) {
            if (GlobalMemberValues.TIMER_KITCHENPRINTINGDATATOCLOUD_YN == "N" || GlobalMemberValues.TIMER_KITCHENPRINTINGDATATOCLOUD_YN.equals("N")) {
                // 멀티 프린팅시 주방프린터 데이터 클라우드 전송
                runTimer_cloudKitchenPrinter();
            }
        }
        if (gm.isCurbsideOrder()) {
            if (GlobalMemberValues.TIMER_CURBSIDE_START_YN == "N" || GlobalMemberValues.TIMER_CURBSIDE_START_YN.equals("N")) {
                runTimer_pushCurside();
            }
        }
        if (gm.isSideMenuOrder()) {
            if (GlobalMemberValues.TIMER_SIDEMENU_START_YN == "N" || GlobalMemberValues.TIMER_SIDEMENU_START_YN.equals("N")) {
                runTimer_pushNewSideMenu();
            }
        }

        // 현재 날짜 리프레쉬
        GlobalMemberValues.STR_NOW_DATE = DateMethodClass.nowMonthGet() + "-" + DateMethodClass.nowDayGet() + "-" + DateMethodClass.nowYearGet();

        // 네트워크 상태 체크
//        GlobalMemberValues.checkOnlineService(mContext, mActivity);

        if (ProductList.onProductListYN == "Y" || ProductList.onProductListYN.equals("Y")) {
            // 키보드 안보이게 --------------------------------------------------------------------------------------
            GlobalMemberValues.setKeyPadHide(ProductList.context, ProductList.mProductInfoEditText);
            // ------------------------------------------------------------------------------------------------------
        }

        /**
         View cView = getCurrentFocus();
         if (cView != null) {
         GlobalMemberValues.displayDialog(mContext, "info", "focus view : " + cView.getTag().toString(), "Close");
         }
         **/

        // 스테이션코드 가져오기
        GlobalMemberValues.STATION_CODE = GlobalMemberValues.getStationCode();

        // Merchant 영수증만 프린트 할지 여부
        GlobalMemberValues.ONLY_MERCHANTRECEIPT_PRINT = "N";

        GlobalMemberValues.setCloudKitchenPrintingValue();

        if (GlobalMemberValues.MAINRECREATEYN.equals("Y")) {
            GlobalMemberValues.MAINRECREATEYN = "N";
            setCommonInit();

            GlobalMemberValues.logWrite("LOGIN_EMPLOYEE_ID_LOG", "GlobalMemberValues.LOGIN_EMPLOYEE_ID2 : " + GlobalMemberValues.LOGIN_EMPLOYEE_ID + "\n");
            GlobalMemberValues.logWrite("LOGIN_EMPLOYEE_ID_LOG", "CashInOutPopup.getCashInout()2 : " + CashInOutPopup.getCashInout() + "\n");
        }

        // 타임메뉴 사용여부
        GlobalMemberValues.setTimeMenuUseYN();

        // 타임메뉴 설정창 자동오픈 여부값 설정
        GlobalMemberValues.setTimeMenuSetAutoOpenValue();

        GlobalMemberValues.isEmployeeLoginPopup = false;
        GlobalMemberValues.isOpenedTimeMenuPopup = false;

        GlobalMemberValues.logWrite("wanhayelog", "GlobalMemberValues.isEmployeeLoginPopup : " + GlobalMemberValues.isEmployeeLoginPopup + "\n");
        GlobalMemberValues.logWrite("wanhayelog", "GlobalMemberValues.isOpenedTimeMenuPopup : " + GlobalMemberValues.isOpenedTimeMenuPopup + "\n");

        // 데이터베이스 백업
        if (GlobalMemberValues.BACKUPAFTERSALE == "Y" || GlobalMemberValues.BACKUPAFTERSALE.equals("Y")) {
            GlobalMemberValues.BACKUPAFTERSALE = "N";
            if (GlobalMemberValues.GLOBAL_DATABASEBACKUP == 1) {
                /**
                 try {
                 Thread.sleep(2000);        // 2초
                 } catch (InterruptedException e) {
                 e.printStackTrace();
                 }
                 **/

                GlobalMemberValues.logWrite("commandButtonDatabase", "여기실행됨..." + "\n");
                CommandButton.backupDatabase(false);
            }
        }
        GlobalMemberValues.BACKUPAFTERSALE = "N";

        // 업로드되었지만, 업로드표시를 안한 데이터 체크하여 처리
        //GlobalMemberValues.serviceStartCheckingUploadDataInCloud(mContext, mActivity);

        GlobalMemberValues.setBooleanDevice();

        mAsset = getAssets();

//        // 프렌차이즈인지 여부를 설정한다.
//        GlobalMemberValues.setComFranchise();

        // Download 다운로드시 화면재개 지연시간 초기화
        //GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

        // 카드 프로세싱 전 인터넷 체크여부 설정
        GlobalMemberValues.setCheckInternetBeforPayProcess();

        // 세일 일시변경 가능여부
        GlobalMemberValues.checkSaleModifyYN();

        // store general
        setStoreInformationInit();

        CloudBackOffice.mProcessDownload = "N";
        CloudBackOffice.mCategoryIdx = "";

        // Modifier 가격 보여줄지 여부 설정
        GlobalMemberValues.setModifierPriceViewYN();

        GlobalMemberValues.setPagerNumberHeaderTxt();

        GlobalMemberValues.setIsDualDisplayPossible();

        GlobalMemberValues.setIsPriceDollarDisplay();

        // 주문시 고객정보 입력페이지 보여줄지 여부
        GlobalMemberValues.mCustomerInfoShowYN = GlobalMemberValues.isCustomerInfoShow();

        // 예약한 시간에 자동 재시작
        //alarmAppRestart();

        // 프레젠테이션 열기
        GlobalMemberValues.showPresentationView();

        if (GlobalMemberValues.isScaleUSE) {
            // Serial 통신 설정
            setUseSerial();
            // Serial 통신 연결
            GlobalMemberValues.jjjOpenUsbSerial();
        }

        // socket port 번호 있을때 setserver!
        GlobalMemberValues.setSocketServer();

        // 소켓통신으로 키친프린팅 데이터 전송시
        // 서버로의 커텍트 시도
        GlobalMemberValues.connectSocketServer();

        if (GlobalMemberValues.isBluetoothKitchenPrinter()) {
            // 블루투스
            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }

            if (mBluetooth_PrinterService != null) {
                // Only if the state is STATE_NONE, do we know that we haven't started already
                if (mBluetooth_PrinterService.getState() == BlueToothPrinterService.STATE_NONE) {
                    // Start the Bluetooth chat services
                    mBluetooth_PrinterService.start();
                }
            } else {
                // Initialize the BluetoothChatService to perform bluetooth connections
                mBluetooth_PrinterService = new BlueToothPrinterService(getApplicationContext(), mHandler);
            }

            // 연결된 블루투스 정보 가져와서 재연결하기
            //SharedPreferences pref = getSharedPreferences("bluetooth_info", MODE_PRIVATE);
            //String temp_macAddress = pref.getString("bluetooth_mac_address", "");
            String temp_macAddress = GlobalMemberValues.getConnectedBluetoothInfo();
            GlobalMemberValues.logWrite("bluetoothconnectedlog", "temp_macAddress : " + temp_macAddress + "\n");
            if (!GlobalMemberValues.isStrEmpty(temp_macAddress)) {
                if (b_bluetooth_connected_YN) return;
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(temp_macAddress);
                // Attempt to connect to the device
                mBluetooth_PrinterService.connect(device, true);
            }
        }

        // 키친 프린터별 프린트수량 설정
        GlobalMemberValues.kitchenprinter1_papercount = GlobalMemberValues.getKitchenPrintingCount2("1");
        GlobalMemberValues.kitchenprinter2_papercount = GlobalMemberValues.getKitchenPrintingCount2("2");
        GlobalMemberValues.kitchenprinter3_papercount = GlobalMemberValues.getKitchenPrintingCount2("3");
        GlobalMemberValues.kitchenprinter4_papercount = GlobalMemberValues.getKitchenPrintingCount2("4");
        GlobalMemberValues.kitchenprinter5_papercount = GlobalMemberValues.getKitchenPrintingCount2("5");

        // 키친프린팅 텍스트 타입 설정
        GlobalMemberValues.setKitchenPrintingType();

        GlobalMemberValues.kitchenprintername1 = GlobalMemberValues.getSavedPrinterNameForKitchen2(MainActivity.mContext);
        GlobalMemberValues.kitchenprintername2 = GlobalMemberValues.getSavedPrinterNameForKitchen3(MainActivity.mContext);
        GlobalMemberValues.kitchenprintername3 = GlobalMemberValues.getSavedPrinterNameForKitchen4(MainActivity.mContext);
        GlobalMemberValues.kitchenprintername4 = GlobalMemberValues.getSavedPrinterNameForKitchen5(MainActivity.mContext);
        GlobalMemberValues.kitchenprintername5 = GlobalMemberValues.getSavedPrinterNameForKitchen6(MainActivity.mContext);



        String tempSqlQuery = "";
        String tempValue = "";

        // 04302024
        // 레스토랑 포스에서 QSR POS 로 사용할지 여부
        tempSqlQuery = "select qsronrestaurantyn from salon_storegeneral";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            GlobalMemberValues.isQSRPOSonRestaurantPOS = true;
        } else {
            GlobalMemberValues.isQSRPOSonRestaurantPOS = false;
        }

        // 아이템(메뉴) 추가시 애니메이션 사용여부
        tempSqlQuery = "select itemanimationyn from salon_storestationsettings_system";
        GlobalMemberValues.ITEMADDANIMATIONYN = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.ITEMADDANIMATIONYN)) {
            GlobalMemberValues.ITEMADDANIMATIONYN = "N";
        }

        // 저울 Scale 사용시 최소무게값
        tempSqlQuery = "select scaleminweight from salon_storegeneral";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "0";
        }
        GlobalMemberValues.SCALE_MINWEIGHT = GlobalMemberValues.getDoubleAtString(tempValue);

        // 저울 Scale 사용시 최소무게값
        tempSqlQuery = "select scaleminweight from salon_storegeneral";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "0";
        }
        GlobalMemberValues.SCALE_MINWEIGHT = GlobalMemberValues.getDoubleAtString(tempValue);

        // 저울 Scale 사용시 최대무게값
        tempSqlQuery = "select scalemaxweight from salon_storegeneral";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "0";
        }
        GlobalMemberValues.SCALE_MAXWEIGHT = GlobalMemberValues.getDoubleAtString(tempValue);

        // 유효하지 않은 스테이션일 경우
        if (!GlobalMemberValues.ISPOSSIBLETHISSTATION) {
//            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
//                    "This station is an invalid device\nIf this message still appears, contact Navyzebra", "Close");
        }

        // 카드관련 정보 ----------------------------------------------------------------------------------
        // KEY IN
        tempSqlQuery = "select keyinyn from salon_storestationsettings_paymentgateway";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            GlobalMemberValues.CARD_KEY_IN = true;
        } else {
            GlobalMemberValues.CARD_KEY_IN = false;
        }

        // TIP PROCESSING
        tempSqlQuery = "select tipprocessingyn from salon_storestationsettings_paymentgateway";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        if (tempValue == "Y" || tempValue.equals("Y")) {
            GlobalMemberValues.CARD_TIP_PROCESSING = true;
        } else {
            GlobalMemberValues.CARD_TIP_PROCESSING = false;
        }
        // ------------------------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("starttimelog", "on resume 끝....\n");

//        if (proDial2 != null && proDial2.isShowing()) {
//            proDial2.dismiss();
//            proDial2 = null;
//        }

        tempSqlQuery = "select mssqlsyncyn from salon_storestationsettings_system";
        tempValue = MainActivity.mDbInit.dbExecuteReadReturnString(tempSqlQuery);
        if (GlobalMemberValues.isStrEmpty(tempValue)) {
            tempValue = "N";
        }
        GlobalMemberValues.mssql_sync = tempValue;

        // 변수 저장 -----------------------------------------------------------------------------------------------------------------------
        String tempIp = "0.0.0.0";
        String tempDbName = GlobalMemberValues.DATABASE_NAME;
        String tempDbPass = "DhksGkDP@02)";
        String tempMobileHost = "yukdaejangm";
        String tempCloudHost = "yukdaejangcloud";

        // 10082024
        String tempDBCodeName = "";

        tempSqlQuery = "select mssqldbip, databasename, databasepass, mobilehost, cloudhost, " +
                // 10082024
                " dbcodename " +
                " from salon_storestationsettings_system";
        Cursor settingsSystemCursor = MainActivity.mDbInit.dbExecuteRead(tempSqlQuery);
        if (settingsSystemCursor.getCount() > 0 && settingsSystemCursor.moveToFirst()) {
            tempIp = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(0), 1);
            tempDbName = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(1), 1);
            tempDbPass = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(2), 1);
            tempMobileHost = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(3), 1);
            tempCloudHost = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(4), 1);

            // 10082024
            tempDBCodeName = GlobalMemberValues.getDBTextAfterChecked(settingsSystemCursor.getString(5), 1);

            if (GlobalMemberValues.isStrEmpty(tempIp)) {
                tempIp = "0.0.0.0";
            }
            if (GlobalMemberValues.isStrEmpty(tempDbName)) {
                tempDbName = GlobalMemberValues.DATABASE_NAME;
            }
            if (GlobalMemberValues.isStrEmpty(tempDbPass)) {
                tempDbPass = "DhksGkDP@02)";
            }
            if (GlobalMemberValues.isStrEmpty(tempMobileHost)) {
                tempMobileHost = "yukdaejangm";
            }
            if (GlobalMemberValues.isStrEmpty(tempCloudHost)) {
                tempCloudHost = "yukdaejangcloud";
            }

            // 10082024
            if (GlobalMemberValues.isStrEmpty(tempDBCodeName)) {
                tempDBCodeName = "";
            }
        }
        GlobalMemberValues.mssql_ip = tempIp;
        GlobalMemberValues.mssql_db = tempDbName;
        GlobalMemberValues.mssql_pw = tempDbPass;
        GlobalMemberValues.MOBILE_HOST = tempMobileHost;
        GlobalMemberValues.CLOUD_HOST = tempCloudHost;

        // 10082024
        GlobalMemberValues.M_DBCODENAME = tempDBCodeName;

        GlobalMemberValues.setCloudAddr();
        // -----------------------------------------------------------------------------------------------------------------------------

        GlobalMemberValues.logWrite("mssqlsynclog", "GlobalMemberValues.mssql_ip : " + GlobalMemberValues.mssql_ip + "\n");

        GlobalMemberValues.mCancelKitchenPrinting = "N";
        GlobalMemberValues.mArrListForTSM = null;
        GlobalMemberValues.mDeletedSaleCartIdx = "";

        GlobalMemberValues.mCancelBtnClickYN = "N";

        // 데이터 다운로드여부 초기화
        GlobalMemberValues.datadownloadingyn = "N";

        GlobalMemberValues.init_capacity_db = GlobalMemberValues.getInitDatabseCapacity();

        // 다른 직원이 주문을 추가, 삭제, 수정할 때 비밀번호를 물어볼지 여부
        GlobalMemberValues.setPasswordyninmod();

        GlobalMemberValues.GLOBAL_TEXT_MAIN_TOP_TABLE_NAME = GlobalMemberValues.getNowTableName();
        if (!GlobalMemberValues.GLOBAL_TEXT_MAIN_TOP_TABLE_NAME.isEmpty() && GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME != null){
            GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME.setText(GlobalMemberValues.GLOBAL_TEXT_MAIN_TOP_TABLE_NAME);
            GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME.setVisibility(View.VISIBLE);
        } else {
            if (GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME != null){
                GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME.setVisibility(View.INVISIBLE);
            }
        }

        if (TableSaleMain.mTableIdxArrList != null && TableSaleMain.mTableIdxArrList.size() != 0){
            int mSubTableCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(idx) from salon_store_restaurant_table_split where tableidx like '%" + TableSaleMain.mTableIdxArrList.get(0) + "%' "
            ));
            // 스플릿 되어있는지 확인 후 array 생성.
            if (mSubTableCount > 0 && mSubTableCount > 1) {
                GlobalMemberValues.GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN.removeAllViews();
                GlobalMemberValues.GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_TEXTVIEW_MAIN_TOP_TABLE_NAME.setVisibility(View.GONE);
                String strQuery = " select subtablenum, holdcode from salon_store_restaurant_table_split " +
                        " where tableidx like '%" + TableSaleMain.mTableIdxArrList.get(0) + "%' " +
                        " order by subtablenum asc ";
                GlobalMemberValues.logWrite("splittedlog", "sql : " + strQuery + "\n");

//                GlobalMemberValues.logWrite("jjjsubtbjjjlog", "mSubTableNum : " + TableSaleMain.mSubTableNum + "\n");

                ResultSet splittedCursor = MssqlDatabase.getResultSetValue(strQuery);
                try {
                    // 02182023
                    int spttbnum = 1;
                    while (splittedCursor.next()) {
                        String tempSubTableNum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(splittedCursor,0), 1);
                        String tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(splittedCursor,1), 1);
                        if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                            layoutParams.setMargins(3,0,3,0);
                            Button subNewBtn = new Button(mContext);
                            subNewBtn.setLayoutParams(layoutParams);
                            subNewBtn.setGravity(Gravity.CENTER);
                            subNewBtn.setText("S"+tempSubTableNum);
                            subNewBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 10);
                            subNewBtn.setPaintFlags(subNewBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

                            GlobalMemberValues.logWrite("jjjsubtbjjjlog", "mSubTableNum : " + TableSaleMain.mSubTableNum + "\n");
                            GlobalMemberValues.logWrite("jjjsubtbjjjlog", "spttbnum : " + spttbnum + "\n");

                            if ((TableSaleMain.mSubTableNum + "J").equals(spttbnum + "J")) {
                                subNewBtn.setTextColor(Color.parseColor("#0054D5"));
                                subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay);
                            } else {
                                subNewBtn.setTextColor(Color.parseColor("#ffffff"));
                                subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay_selected);
                            }

                            subNewBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MainMiddleService.initList();
                                    TableSplittedList.selectSubTable(tempSubTableNum, tempHoldCode);
                                }
                            });
                            GlobalMemberValues.GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN.addView(subNewBtn);

                            spttbnum++;
                        }
                    }
                    //07052024 close resultset
                    splittedCursor.close();
                } catch (Exception e){

                }
            } else {
                GlobalMemberValues.GLOBAL_MAIN_TOP_TABLE_SPLIT_NAME_LN.setVisibility(View.GONE);
            }
        }

        GlobalMemberValues.GLOBAL_TEXT_MAIN_TOP_TABLE_NAME = "";

        (MainActivity.this).runOnUiThread(new Runnable(){
            @Override
            public void run() {
                // qty menual
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN != null &&
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY != null){
                    if (GlobalMemberValues.isPossibleManualQty()){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.GONE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setVisibility(View.VISIBLE);
                    } else {
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.VISIBLE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setVisibility(View.GONE);
                    }
                }

                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1 != null
                        && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2 != null
                        && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY_TOGO_LN != null)
                {
                    if (GlobalMemberValues.isToGoSale()){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1.setVisibility(View.GONE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2.setVisibility(View.VISIBLE);
//                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.INVISIBLE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY_TOGO_LN.setVisibility(View.INVISIBLE);

                        String temp_str = GlobalMemberValues.getCustomerInfoByTableIdx();
                        String[] temp_arr = temp_str.split("-JJJ-");
                        if (temp_arr.length > 0){
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_NAME.setText(temp_arr[0]);
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE.setText(temp_arr[1]);
                            if (temp_arr[1].equals("Online")){
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_ONLINE_SHOW.setText("Online");
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_PHONE.setText("");
                            } else {
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO_ONLINE_SHOW.setText("");
                            }
                        }

                    } else {
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO2.setVisibility(View.GONE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1.setVisibility(View.VISIBLE);
//                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS_MINUS_LN.setVisibility(View.VISIBLE);
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY_TOGO_LN.setVisibility(View.VISIBLE);

                        if (GlobalMemberValues.isQSRPOSonRestaurantPOS){
                            //05172024
                            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TABLE.setVisibility(View.GONE);
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO1.setVisibility(View.GONE);
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO3.setVisibility(View.VISIBLE);
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (MainMiddleService.mSaleCartAdapter != null){
                                        if (MainMiddleService.mSaleCartAdapter.getCount() != 0){
                                            GlobalMemberValues.displayDialog(mContext, "Warning", "There is a menu in the shopping cart.", "Close");
                                            return;
                                        }
                                    }
                                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.isSelected()){
                                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.setSelected(false);
//                                        quick_table_popup_btn.setSelected(false);
                                        main_grid_relative_view.setVisibility(View.INVISIBLE);
                                        main_grid_relative_view_loading.setVisibility(View.VISIBLE);
                                        main_grid_relative_view.startAnimation(Quick_LeftAnim);
                                    } else {
                                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.setSelected(true);
//                                        quick_table_popup_btn.setSelected(true);
                                        main_grid_relative_view.startAnimation(Quick_RightAnim);
                                        main_grid_relative_view.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_CUSTOMER_INFO3.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });


        String tempDeviceKind = MainActivity.mDbInit.dbExecuteReadReturnString("select devicekind from salon_storestationsettings_system");
        if (!GlobalMemberValues.isStrEmpty(tempDeviceKind)) {
            switch (tempDeviceKind.toUpperCase()) {
                case "PAX" : {
                    GlobalMemberValues.mDevicePAX = true;
                    GlobalMemberValues.mDeviceSunmi = false;
                    break;
                }
                case "SUNMI" : {
                    GlobalMemberValues.mDeviceSunmi = true;
                    GlobalMemberValues.mDevicePAX = false;
                    break;
                }
            }
        }



        // to go 주문일 경우 name & info 버튼을 보여준다. ----------------------------------------------------------------
        GlobalMemberValues.logWrite("jjjwanhayejjjlog", "GlobalMemberValues.isCustomerInfoShow() : " + GlobalMemberValues.isCustomerInfoShow() + "\n");

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO != null) {
            if (GlobalMemberValues.isCustomerInfoShow()){
                String tempQuickSaleyn = "N";
                String tempTableIdx = "";
                int tempi = 0;

                if (TableSaleMain.mSelectedTablesArrList != null) {
                    GlobalMemberValues.logWrite("jjjwanhayejjjlog", "여기.." + "\n");
                    for (int i = 0; i < TableSaleMain.mSelectedTablesArrList.size(); i++) {
                        tempTableIdx = GlobalMemberValues.getReplaceText(TableSaleMain.mSelectedTablesArrList.get(i), "T", "");
                        tempQuickSaleyn = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select quicksaleyn from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
                        );
                        if (!GlobalMemberValues.isStrEmpty(tempQuickSaleyn) && tempQuickSaleyn.equals("Y")) {
                            tempi++;
                        }
                    }
                }
                GlobalMemberValues.logWrite("jjjwanhayejjjlog", "tempi : " + tempi + "\n");
                if (tempi > 0) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setVisibility(View.VISIBLE);
                } else {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setVisibility(View.INVISIBLE);
                }
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setVisibility(View.INVISIBLE);
            }
        }

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY != null && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO != null){
            if (GlobalMemberValues.isToGoSale()){
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY.setVisibility(View.GONE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setVisibility(View.VISIBLE);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_ADD_GRATUITY.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_NAME_INFO.setVisibility(View.GONE);
            }
        }

        GlobalMemberValues.mCardSalesIdxForBillPay = "";

        // -----------------------------------------------------------------------------------------------------------

        // 05.02.2022 ----------------------------------------------
        if (TableSaleMain.isDiscountAdjust) {
            if (TableSaleMain.mSelected_index_for_isCheckedConfrim != null && TableSaleMain.mSelected_index_for_isCheckedConfrim.size() > 0) {
                for (String tempConfirmIdx : TableSaleMain.mSelected_index_for_isCheckedConfrim) {
                    MainMiddleService.isCheckedConfrim[GlobalMemberValues.getIntAtString(tempConfirmIdx)] = true;
                }
            }

            GlobalMemberValues.logWrite("jjjdiscountadjustjjjlog", "여기에서 discount 실행됨..." + "\n");
            Discount.applyCoupon("");
        }
        TableSaleMain.isDiscountAdjust = false;
        TableSaleMain.mSelected_index_for_isCheckedConfrim = null;
        // ---------------------------------------------------------

        //THIS IS TO STOP MODIFER SCREEN ENTERING AND BACK TO STOP UPDATING THE ORIGINAL... need better implementaion
        if (GlobalMemberValues.is_modifier_add){
            GlobalMemberValues.is_modifier_add = false;
        } else {
            if (MainMiddleService.mGeneralArrayList != null) {
                //07182024 fix bug, this toString method doesn't actually return a comparable to String,
                //it returns an array of tempSaleCart object, so if the tempSaleCart changes, it doesn't
                //actually reflect that, only when an entire tempSaleCart is added or deleted.
                //temp_str_salecart = MainMiddleService.mGeneralArrayList.toString();
                temp_str_salecart = "";
                for(TemporarySaleCart tempSaleCart : MainMiddleService.mGeneralArrayList){
                    // discount, commonGratuity 제외함 072227
                    if (tempSaleCart.returnTempCartString().toLowerCase().contains("discount") ||
                            tempSaleCart.returnTempCartString().toLowerCase().contains(GlobalMemberValues.mCommonGratuityName.toLowerCase())) {
                    } else {
                        temp_str_salecart = temp_str_salecart + tempSaleCart.returnTempCartString();
                    }
//                    temp_str_salecart = temp_str_salecart + tempSaleCart.returnTempCartString();
                }
                temp_str_salecart_cnt = MainMiddleService.mGeneralArrayList.size();
            } else {
                temp_str_salecart = "";
                temp_str_salecart_cnt = 0;
            }
        }


        // 06.03.2022 ----------------------------------------------------------------------------------------
        if (GlobalMemberValues.isOpenPayment) {
            // payment 프레임 보여주기
            Payment payment = new Payment(MainActivity.mActivity, MainActivity.mContext, MainActivity.dataAtSqlite);
            payment.setPaymentView();

            GlobalMemberValues.isRepay2 = true;
        }
        // --------------------------------------------------------------------------------------------------

        // 06.02.2022 ---------------------------------------
        GlobalMemberValues.mTableIdx_byRepay = "";
        GlobalMemberValues.mHoldCode_byRepay = "";
        GlobalMemberValues.mPeopleCnt_byRepay = 0;
        // --------------------------------------------------

        // 06.07.200
        GlobalMemberValues.mOnVoidForPartial = false;

        GlobalMemberValues.logWrite("endtimechecklog", "여기4" + "\n");

        if (TableSaleBillPrint.mActivity != null && !TableSaleBillPrint.mActivity.isFinishing()) {
            TableSaleBillPrint.mActivity.finish();
        }

    }

    //BroadcastReceiver 함수입니다.
    public void registBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(action.equals("registrationComplete")) {
                    //mProgressBar.setVisibility(ProgressBar.GONE);
                    //mButton.setText("Registration Complete");
                    //mButton.setTextColor(Color.RED);
                    //mButton.setEnabled(false);
                    String token = intent.getStringExtra("token");
                    //mTextView.setText(token);
                }
            }
        };
    }
    /***********************************************************************************************/

    /** Override 메소드 ****************************************************************************/
    @Override
    //종료처리시 종료할지 물어보기 추가
    public void onBackPressed() {
        //GlobalMemberValues.setCloseAndroidApp(mActivity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //화면에서 사라지면 등록된 LocalBoardcast를 모두 해제합니다.
    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

        // Elo 관련
        if (mApiAdapter == null) {
            //setEloInit();
        }
        if (mApiAdapter != null) {
            try {
                mApiAdapter.getActivityMonitor().onActivityEvent(ActivityMonitor.EVENT_ON_PAUSE);
            } catch (Exception e) {
                GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
            }
        }

        // Clover 관련
        if (GlobalMemberValues.isDeviceClover()) {
            if (barcodeReceiver_clover != null) {
                unregisterReceiver(barcodeReceiver_clover);
            }
        }

        // jihun park sub display
        mMediaRouter.removeCallback(mMediaRouterCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Elo 관련
        if (mApiAdapter == null) {
            //setEloInit();
        }
        if (mApiAdapter != null) {
            try {
                mApiAdapter.getActivityMonitor().onActivityEvent(ActivityMonitor.EVENT_ON_START);
            } catch (Exception e) {
                GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
            }
        }

        // Clover 관련
        GlobalMemberValues.setCloverConnect(mContext);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Elo 관련
        if (mApiAdapter == null) {
            //setEloInit();
        }
        if (mApiAdapter != null) {
            try {
                mApiAdapter.getActivityMonitor().onActivityEvent(ActivityMonitor.EVENT_ON_STOP);
            } catch (Exception e) {
                GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
            }
        }

        // jihun park sub display
        if (mPaxPresentation != null) {
            mPaxPresentation.dismiss();
            mPaxPresentation = null;
        }

    }

    @Override
    protected void onDestroy() {
        // 앱이 종료되면 Notification 관련 isOpenApplication 의 값을 false 로 변경한다..
        // Notification 관련 isOpenApplication 값 설정 --------------------------
        isOpenApplication = false;
        // ---------------------------------------------------------------------

        if (printerConnector_clover != null) {
            printerConnector_clover.disconnect();
            printerConnector_clover = null;
        }
        if (displayConnector_clover != null) {
            displayConnector_clover.dispose();
        }

        if (GlobalMemberValues.isScaleUSE) {
            if (mUsbReceiver != null) {
                // usb serial, scale 관련 ---------
                mUsbReceiver.closeUsbSerial();
                unregisterReceiver(mUsbReceiver);
                // --------------------------------
            }
        }

        if (proDial != null && proDial.isShowing()) {
            proDial.dismiss();
        }

        super.onDestroy();
    }

    //화면변환시(가로,세로) Reload방지 --------------------------------------------------
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }
    // -------------------------------------------------------------------------------
    /***********************************************************************************************/

    // QR 코드 응답부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CALL_ZXING_RESULT_DISCOUNT) {
            if(resultCode == Activity.RESULT_OK) {
                String qrCodeResult = data.getStringExtra("SCAN_RESULT");
                //GlobalMemberValues.displayDialog(mContext, "QRCode Value", "QRCODE : " + qrCodeResult, "Close");
                //Discount.setDiscountAfterCheckedCoupon(qrCodeResult, MainActivity.mContext);
                if (CouponDetail.openCount == 0) {
                    Intent couponDetailIntent = new Intent(mContext.getApplicationContext(), CouponDetail.class);
                    couponDetailIntent.putExtra("couponnumber", qrCodeResult);
                    mActivity.startActivity(couponDetailIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }
            }
        }

        // jihun park table sale 추가
        else if (requestCode == CALL_MAINACTIVITY_TABLESALE) {
            if (resultCode == Activity.RESULT_OK){
                GlobalMemberValues globalMemberValues = new GlobalMemberValues();
                //GlobalMemberValues.openRestaurantTable();
            }
        }

        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
            if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName)) {
                GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName = "";
            }

            if (!GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName.equals("ADMIN")){
            } else {
                CommandButton commandButton = new CommandButton(this, this, dataAtSqlite);
                GlobalMemberValues.setFrameLayoutVisibleChange("commandButton");
                commandButton.setContents();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 알람에 의한 앱 재시작
    public static void alarmAppRestart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 알람1  시작 ---------------------------------------------------------------------------------
            // 알람매니저 설정
            AlarmManager alarm_manager1 = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);

            // Calendar 객체 생성
            Calendar calendar1 = Calendar.getInstance();

            // 알람리시버 intent 생성
            final Intent my_intent1 = new Intent(mContext, JJJ_AppRestart_OnAlarm.class);

            // calendar에 시간 셋팅
            calendar1.set(Calendar.HOUR_OF_DAY, 3);
            //calendar1.set(Calendar.MINUTE, 19);
            //calendar1.set(Calendar.SECOND, 10);
            //calendar1.set(Calendar.MILLISECOND, 0);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(mContext,0, my_intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            // 알람셋팅
            alarm_manager1.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        }
    }

    final static IDisplayConnectorListener dcListener = new IDisplayConnectorListener() {
        @Override
        public void onDeviceDisconnected() {
            Log.d(TAG, "onDeviceDisconnected");
        }

        @Override
        public void onDeviceConnected() {
            Log.d(TAG, "onDeviceConnected");
        }
    };

    // 알수 없는 에러발생 관련 class
    class UncaughtExceptionHandlerApplication implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            GlobalMemberValues.displayDialog(mContext, "ERROR", "System Error... System is rebooted\n(" + ex.getMessage() + ")", "Close");

            Intent restartIntent = new Intent(getApplicationContext(), MainActivity.class);
            restartIntent.putExtra("stoperrormsg", ex.getMessage());
            PendingIntent runner = PendingIntent.getActivity(getApplicationContext(), 99, restartIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 100, runner);
            System.exit(2);
            // 직접 예외를 처리하지 않고 DefaultUncaughtException으로 넘긴다.
            mUncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }

    // jihun park sub display
    public static void updatePresentation() {
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            // BEGIN_INCLUDE(updatePresentationInit)
            // Get the selected route for live video
            MediaRouter.RouteInfo selectedRoute = mMediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);

            // Get its Display if a valid route has been selected
            Display selectedDisplay = null;
            if (selectedRoute != null) {
                selectedDisplay = selectedRoute.getPresentationDisplay();
            }
            // END_INCLUDE(updatePresentationInit)

            // BEGIN_INCLUDE(updatePresentationDismiss)
            /*
             * Dismiss the current presentation if the display has changed or no new
             * route has been selected
             */
            if (mPaxPresentation != null && mPaxPresentation.getDisplay() != selectedDisplay) {
                mPaxPresentation.dismiss();
                mPaxPresentation = null;
            }
            // END_INCLUDE(updatePresentationDismiss)

            // BEGIN_INCLUDE(updatePresentationNew)
            /*
             * Show a new presentation if the previous one has been dismissed and a
             * route has been selected.
             */
            if (mPaxPresentation == null && selectedDisplay != null) {

                // Initialise a new Presentation for the Display
                mPaxPresentation = new PaxPresentation(mContext, selectedDisplay);
                mPaxPresentation.setOnDismissListener(mOnDismissListener);

                // Try to show the presentation, this might fail if the display has
                // gone away in the mean time
                try {
                    //mPaxPresentation.show();
                    GlobalMemberValues.showPresentationView();
                } catch (WindowManager.InvalidDisplayException ex) {
                    // Couldn't show presentation - display was already removed
                    mPaxPresentation = null;
                }
            }
            // END_INCLUDE(updatePresentationNew)
        }
    }

    // USB SERIAL, Scale 관련
    public void setUseSerial() {
        mSerial = new FTDriver((UsbManager) getSystemService(Context.USB_SERVICE));

        if (mSerial != null) {
            // listen for new devices
            mUsbReceiver = new UsbReceiver(this, mSerial);

            IntentFilter filter = new IntentFilter();
            filter.addAction (UsbManager.ACTION_USB_DEVICE_ATTACHED);
            filter.addAction (UsbManager.ACTION_USB_DEVICE_DETACHED);
            registerReceiver (mUsbReceiver, filter);

            // load default baud rate
            mBaudrate = mUsbReceiver.loadDefaultBaudrate();

            // for requesting permission
            // setPermissionIntent() before begin()
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            mSerial.setPermissionIntent(permissionIntent);

            if (SHOW_DEBUG) {
                Log.d(TAG, "FTDriver beginning");
            }

            if (mSerial.begin(mBaudrate)) {
                if (SHOW_DEBUG) {
                    Log.d(TAG, "FTDriver began");
                }
                mUsbReceiver.loadDefaultSettingValues();
                mUsbReceiver.mainloop();
            } else {
                if (SHOW_DEBUG) {
                    Log.d(TAG, "FTDriver no connection");
                }
                //Toast.makeText(this, "no connection", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private final MediaRouter.SimpleCallback mMediaRouterCallback = new MediaRouter.SimpleCallback() {

        // BEGIN_INCLUDE(SimpleCallback)
        /**
         * A new route has been selected as active. Disable the current
         * route and enable the new one.
         */
        @Override
        public void onRouteSelected(MediaRouter router, int type, MediaRouter.RouteInfo info) {
            updatePresentation();
        }

        /**
         * The route has been unselected.
         */
        @Override
        public void onRouteUnselected(MediaRouter router, int type, MediaRouter.RouteInfo info) {
            updatePresentation();

        }

        /**
         * The route's presentation display has changed. This callback
         * is called when the presentation has been activated, removed
         * or its properties have changed.
         */
        @Override
        public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo info) {
            updatePresentation();
        }
        // END_INCLUDE(SimpleCallback)
    };
    private static final DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (dialog == mPaxPresentation) {
                mPaxPresentation = null;
            }
        }
    };




    // 블루투스 관련 --------------------------------------------------------------------------------
    // 이곳에 블루투스 관련된 코드를 넣을 것
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BlueToothConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BlueToothPrinterService.STATE_CONNECTED:
//                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
//                            mConversationArrayAdapter.clear();
                            b_bluetooth_connected_YN = true;
                            break;
                        case BlueToothPrinterService.STATE_CONNECTING:
//                            setStatus(R.string.title_connecting);
                            break;
                        case BlueToothPrinterService.STATE_LISTEN:
                        case BlueToothPrinterService.STATE_NONE:
//                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case BlueToothConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
//                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    //Toast.makeText(getApplicationContext(), "Send Data...."+writeMessage, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Send Data....", Toast.LENGTH_SHORT).show();
                    break;
                case BlueToothConstants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    //Toast.makeText(getApplicationContext(), "받음!"+readMessage, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Received Data...", Toast.LENGTH_SHORT).show();
                    break;
                case BlueToothConstants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(BlueToothConstants.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                    // 연결된 블루투스 기기의 mac address 저장
                    GlobalMemberValues.bluetooth_saveMacAddress(mActivity, msg.getData().getString("macaddress"));
                    break;
                case BlueToothConstants.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BlueToothConstants.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void timeMenu_getServiceTime(){
        GlobalMemberValues.logWrite("autotimemenulogjjj", "여기실행..11111" + "\n");

        Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), AlarmReceiver.class);
        Intent intent2 = new Intent(MainActivity.mContext.getApplicationContext(), AlarmReceiver2.class);
        Intent intent3 = new Intent(MainActivity.mContext.getApplicationContext(), AlarmReceiver3.class);
        Intent intent4 = new Intent(MainActivity.mContext.getApplicationContext(), AlarmReceiver4.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.mContext.getApplicationContext(), 9001, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.mContext.getApplicationContext(), 9002, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(MainActivity.mContext.getApplicationContext(), 9003, intent3, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(MainActivity.mContext.getApplicationContext(), 9004, intent4, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)MainActivity.mContext.getSystemService(ALARM_SERVICE);

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        String[] menutimes = globalMemberValues.getTImeMenuTimes();
        //String[] menutimes = {"0830", "0845", "0900", "0915"};

        if (menutimes.length != 0) {
            for (int j = 0; j < menutimes.length; j++) {
                String timestr = menutimes[j];
                String hourstr = timestr.substring(0, 2);
                String minstr = timestr.substring(2);

//                GlobalMemberValues.logWrite("autotimemenulogjjj", "timestr : " + timestr + "\n");
//                GlobalMemberValues.logWrite("autotimemenulogjjj", "hourstr : " + hourstr + "\n");
//                GlobalMemberValues.logWrite("autotimemenulogjjj", "minstr : " + minstr + "\n");
                long aTime = System.currentTimeMillis();
                long bTime = 0;
                Calendar calendar = null;
                if (false) {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY, (GlobalMemberValues.getIntAtString(hourstr)));
//                    calendar.set(Calendar.MINUTE, (GlobalMemberValues.getIntAtString(minstr)));
//                    calendar.set(Calendar.SECOND, 0);
//                    calendar.set(Calendar.MILLISECOND, 0);
//
//                    bTime = calendar.getTimeInMillis();
                } else {
                    Date mDate = new Date(aTime);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                    String getTime = simpleDate.format(mDate);

                    String str_date = getTime + " " + hourstr + ":" + minstr;
                    String pattern = "yyyy-MM-dd HH:mm";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);

                    Date trans_date = null;
                    try {
                        trans_date = formatter.parse(str_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    bTime = trans_date.getTime();

                }


                long interval = 1000 * 60 * 60  * 24; // 하루



//                Date a = new Date(aTime);
//                Date b = new Date(bTime);
//                GlobalMemberValues.logWrite("paxdatelog", "a : " + a.toString() + "\n");
//                GlobalMemberValues.logWrite("paxdatelog", "b : " + b.toString() + "\n");

                //만일 내가 설정한 시간이 현재 시간보다 작다면 알람이 바로 울려버리기 때문에 이미 시간이 지난 알람은 다음날 울려야 한다.
                while(aTime > bTime){
                    bTime += interval;
                }

                switch (j) {
                    case 0 : {
//                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, bTime, interval, pendingIntent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,bTime,pendingIntent);
                        }
                        break;
                    }

                    case 1 : {
//                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, bTime, interval, pendingIntent2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,bTime,pendingIntent2);
                        }
                        break;
                    }

                    case 2 : {
//                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, bTime, interval, pendingIntent3);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,bTime,pendingIntent3);
                        }
                        break;
                    }

                    case 3 : {
//                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, bTime, interval, pendingIntent4);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,bTime,pendingIntent4);
                        }
                        break;
                    }

                }


            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        GlobalMemberValues.logWrite("starttimelog", "여기에...\n");
    }


    View.OnClickListener info_close_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogsSave.saveLogsInDB(110);
            informationPopup.cancel();
        }
    };

    //

    // ----------------------------------------------------------------------------------------------

    CompoundButton.OnCheckedChangeListener mainItemCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (GlobalMemberValues.ALL_CHECKBOX_INIT){
                GlobalMemberValues.ALL_CHECKBOX_INIT = false;
                return;
            }
            if (MainMiddleService.mSaleCartAdapter != null){

                MainMiddleService.setAllSelectedPosition();
                // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();
                }

//                for (int i = 0 ; MainMiddleService.mGeneralArrayList.size() > i ; i++ ){
//                    MainMiddleService.setSelectedPosition(i);
//                    MainMiddleService.setCheckedItems(i);
//                }
            }
        }
    };

    public void viewTableSettignsForQuick() {
//        setClearSelectedTableIdx(true);
//
//        // 초기화
//        table_main_view.removeAllViews();

//        GlobalMemberValues gm = new GlobalMemberValues();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] tableinfo = GlobalMemberValues.getRestaurantTableForQuick();
                GlobalMemberValues.s_str_tableinfo = tableinfo;
                // 저장된 테이블이 있을 경우에만..
                if (tableinfo != null && tableinfo.length > 0) {
                    MainActivity.QuickViewHolder temp_quickTable_gridListAdapter = new MainActivity.QuickViewHolder(MainActivity.mContext, tableinfo);
                    quick_table_grid_list.setLayoutManager(new GridLayoutManager(MainActivity.mContext, 3));
                    RecyclerView.ItemAnimator animator = quick_table_grid_list.getItemAnimator();
                    if (animator instanceof SimpleItemAnimator) {
                        ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
                    }
                    quick_table_grid_list.setAdapter(temp_quickTable_gridListAdapter);
//                    quick_table_grid_list.deferNotifyDataSetChanged();
                }
            }
        });
    }

    public static void setMainBillPrintButtonVisible(boolean is_visible){
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT == null) return;
        MainActivity.mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (is_visible){
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.VISIBLE);
                } else {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private class SlidingTogoViewAnimationListener implements Animation.AnimationListener{

        public void onAnimationEnd(Animation animation){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.isSelected()){
                viewTableSettignsForQuick();
            }

            main_grid_relative_view_loading.setVisibility(View.INVISIBLE);

        }
        @Override
        public void onAnimationStart(Animation animation) {
            main_grid_relative_view_loading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

//    private View.OnClickListener onQuickTableClickTable = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String str = (String) v.getTag();
//            Toast.makeText(TableSaleMain.this, str, Toast.LENGTH_SHORT).show();
//        }
//    };




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (main_grid_relative_view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)) {
            int scrcoords[] = new int[2];
            main_grid_relative_view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + main_grid_relative_view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + main_grid_relative_view.getTop() - scrcoords[1];
            if (x < main_grid_relative_view.getLeft() || x > main_grid_relative_view.getRight() || y < main_grid_relative_view.getTop() || y > main_grid_relative_view.getBottom()){
                if (main_grid_relative_view.getVisibility() == View.VISIBLE){
//                    quick_table_popup_btn.setSelected(false);
                    main_grid_relative_view.setVisibility(View.GONE);
                    main_grid_relative_view.startAnimation(Quick_LeftAnim);
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.setSelected(false);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void uploadTableDataCloudExe() {
        // 클라우드 업로드 ----------------------------------------------------------------------------------------
        // 10초후 실행
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        GlobalMemberValues gm = new GlobalMemberValues();
        if (gm.isPOSWebPay() &&
                (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
            // 장바구니데이터 클라우드 전송
            GlobalMemberValues.setSendCartToCloud(mContext, mActivity);
            // 장바구니데이터 삭제 클라우드 전송
            GlobalMemberValues.setSendCartDeleteToCloud(mContext, mActivity);
        }
        // -----------------------------------------------------------------------------------------------------
    }

    public class QuickViewHolder extends RecyclerView.Adapter<MainActivity.QuickViewHolder.ViewHolder> {

        boolean is_last_in_order = false;
        boolean b = false;

        private String[] mData = new String[0];
        private LayoutInflater mInflater;

        // Data is passed into the constructor
        public QuickViewHolder(Context context, String[] data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // Inflates the cell layout from xml when needed
        @Override
        public MainActivity.QuickViewHolder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.quick_table_grid_row, parent, false);
            //            View view = LayoutInflater.from(context)
//                    .inflate(R.layout.quick_table_grid_row, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 120));
            MainActivity.QuickViewHolder.ViewHolder viewHolder = new MainActivity.QuickViewHolder.ViewHolder(view);
            return viewHolder;
        }

        // Binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(MainActivity.QuickViewHolder.ViewHolder holder, int position) {




            String[] tablearr = null;
            String tableidx = "";
            String tablename = "";
            String capacity = "";
            String colorvalue = "#5351fb";
            String tabletype = "";
            String chargeridx = "";
            String pagernum = "";
            String xvaluerate = "";
            String yvaluerate = "";
            String tableordercnt = "";
            String boxtype = "";
            String boxkinds = "";
            String customername = "";
            String customerphonenum = "";
            String togotype = "";

            tablearr = mData[position].split("-JJJ-");

            tableidx = tablearr[0];
            tablename = tablearr[1];
            capacity = tablearr[2];
            colorvalue = tablearr[3];
            tabletype = tablearr[4];
            chargeridx = tablearr[5];
            pagernum = tablearr[6];
            xvaluerate = tablearr[7];
            yvaluerate = tablearr[8];
            boxtype = tablearr[9];
            boxkinds = tablearr[10];
            customername = tablearr[11];
            customerphonenum = tablearr[12];

            if (GlobalMemberValues.isStrEmpty(boxtype)) {
                boxtype = "0";
            }
            if (GlobalMemberValues.isStrEmpty(boxkinds)) {
                boxkinds = "table";
            }

            if (GlobalMemberValues.isStrEmpty(colorvalue)) {
                colorvalue = "#5351fb";
            }

            if (GlobalMemberValues.isStrEmpty(capacity)) {
                capacity = "0";
            }

            holder.textview1.setText(tablename);


            tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
            TableSaleMain.mAllTablesArrList.add(tableidx);

//             테이블이 split 되어 있는지 확인
            int subTableCount = 0;//TableSaleMain.getTableSplitCount(tableidx);

            // 0802 추가한 부분인데 // 이부분을 활성화 하면 초기 로딩이 조금 느려짐 // Quick row 에 스플릿 된 주문인지 표시해줌.
            // bill_list 에 해당 테이블의 데이터가 있을 경우 (bill split 이 있을 경우)
            int getBillCnt = 0;
            if (!tableidx.isEmpty()) {
                String tempHoldCode = TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);
                subTableCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(*) from bill_list where holdcode = '" + tempHoldCode + "' "
                ));
            }

            if (subTableCount > 0 && subTableCount > 1) {
                String tempSubTableStr = subTableCount + "ea";
                tablename = tempSubTableStr + " Split";
//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                holder.textview1.setText(tablename);
            }

            String tableTxtColor = "#ffffff";

            String getHoldCodeTemp = TableSaleMain.getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

            int mergednum = 0;

            // mergednum -------------------------------------------------------------------------
            holder.textview2.setTextSize(30);
            holder.textview2.setText(customername);
            holder.textview3.setText(customerphonenum);


            boolean isOrderInTable = false;

            // 주문시간 add
            String strQuery_temp = " select top 1 wdate, mergednum, togotype from temp_salecart " +
                    " where holdcode = '" + getHoldCodeTemp + "' order by wdate asc";

            ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
            String ordereddateValue = "";
            try {
                while (timeCursor.next()){
                    String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                    ordereddateValue = getTime;

                    mergednum = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,1), 1));

                    togotype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,2), 1);

                    isOrderInTable = true;
                }
                timeCursor.close();
            } catch (Exception e) {
            }

            if (!GlobalMemberValues.isStrEmpty(togotype)) {
                switch (togotype) {
                    case "C" : {
                        if (customerphonenum.equals("Walk In")) {
                            holder.textview3.setText("Call In");
                        } else {
                            holder.textview3.setText(customerphonenum + "(Call In)");
                        }

                        break;
                    }
                    case "W" : {
                        if (!customerphonenum.equals("Walk In")) {
                            holder.textview3.setText(customerphonenum + "(Walk In)");
                        }
                        break;
                    }
                }
            }

            String date1 = ordereddateValue;

            if (GlobalMemberValues.isTimeDisplayonTable()){
//                holder.textview4 = timeupdate_in_textview(holder.textview4,date1);
            }



//            textview4.setText("");
            if (mergednum > 0) {
                if (TableSaleMain.getTableCountByHoldcode(getHoldCodeTemp) == 1
                        && TableSaleMain.getTableCountByTableidxInMergedTable(tableidx) < 2) {
                    String newHoldCode = GlobalMemberValues.makeHoldCode();
                    Vector<String> updateVector = new Vector<String>();
                    String strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '0', isCloudUpload = 0  " +
                            " where tableidx like '%" + tableidx + "%' ";
                    updateVector.addElement(strQuery);

                    strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tableidx + "%' ";
                    updateVector.addElement(strQuery);

                    for (String tempQuery : updateVector) {
                        //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                    }

                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MssqlDatabase.executeTransaction(updateVector);
                    if (returnResult == "N" || returnResult == "") {
                        //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                        //return;
                    } else {
                        // 클라우드 업로드
                        uploadTableDataCloudExe();
                    }
                } else {


                    String mergednumstr = "0" + mergednum;
                    mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                    tablename = "Merged\n(" + mergednumstr + ")";

                    holder.textview1.setText(tablename);
//                    textview1.setTextColor(Color.parseColor(tableTxtColor));
                    int tablecolornum = mergednum % 7;
//                    textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                    holder.textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                    holder.textview2.setText("   Merged Table   ");
                    holder.textview2.setTextSize(20);
                    holder.textview2.setTextColor(Color.parseColor("#ffffff"));
                    holder.qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
                }
            } else {
                if (TableSaleMain.getTableCountByTableidxInMergedTable(tableidx) > 1) {
                    String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(tableidx);
                    if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {




                        mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(tableidx);

                        String mergednumstr = "0" + mergednum;
                        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                        tablename = "Merged\n(" + mergednumstr + ")";

                        holder.textview1.setText(tablename);
//                       textview1.setTextColor(Color.parseColor(tableTxtColor));

                        int tablecolornum = mergednum % 7;
//                        textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                        holder.textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                        holder.textview2.setText("   Merged Table   ");
                        holder.textview2.setTextSize(20);
                        holder.textview2.setTextColor(Color.parseColor("#ffffff"));
                        holder.qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
                    }
                } else {
                    Vector<String> deleteVec = new Vector<String>();
                    String strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tableidx + "%' ";
                    deleteVec.addElement(strQuery);

                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = MssqlDatabase.executeTransaction(deleteVec);
                    if (returnResult == "N" || returnResult == "") {
                        //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                        //return;
                    } else {
                    }
                }
            }

            int tableordercnt_int = 0;
            if (isOrderInTable){
                holder.qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_sel);

                // 해당 테이블 주문메뉴 수
//            tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' ");
//                tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' ");
//                GlobalMemberValues.getIntAtString(tableordercnt);
                GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

                is_last_in_order = true;

            } else {
                is_last_in_order = false;
                holder.qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_not_sel);

            }


            if (GlobalMemberValues.isDeviceSunmiFromDB()){
                holder.textview1.setTextSize(10);
                holder.textview2.setTextSize(22);
                holder.textview3.setTextSize(16);
            }


            final String finalTablename = tablename;
            final String finalTableidx = tableidx;
            final String finalTableTxtColor = tableTxtColor;
            boolean is_useable = false;

            if (is_last_in_order){
                is_useable = true;
            } else {
                if (b){
                    is_useable = false;
                } else {
                    b = true;
                    is_useable = true;
                }
            }


            if (is_useable){
                holder.qt_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (true){
                            // 빈 칸 선택시

                            if (true){
                                // 선택한 셀이 첫번째 셀인지.
                            } else {
                                return;
                            }
                        }
                        BillSplitMerge.setInitValuesForBillPay();

                        tableCell_singleClick(view, holder.qt_row, holder.qt_row, finalTableidx,
                                finalTablename, finalTableTxtColor, tableordercnt_int, "Q");

                        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                            //jihun park sub display
                            PaxPresentation.unSetLogo();
                            MainActivity.updatePresentation();
                        }
                    }
                });
            } else {

            }


            // table double touch


            // table longclick
            holder.qt_row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    tableCell_longClick(v);
                    return false;
                }
            });
            holder.qt_row.setTag(tableidx);


        }

        // Total number of cells
        @Override
        public int getItemCount() {
            return mData.length;
        }

        // Stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            LinearLayout qt_row;
            TextView textview2 ;
            TextView textview1 ;
            TextView textview3 ;
            TextView textview4 ;

            public ViewHolder(View itemView) {
                super(itemView);
                qt_row = itemView.findViewById(R.id.quick_table_row_ln);
                textview2 = itemView.findViewById(R.id.quick_table_text2);
                textview1 = itemView.findViewById(R.id.quick_table_text1);
                textview3 = itemView.findViewById(R.id.quick_table_text3);
                textview4 = itemView.findViewById(R.id.quick_table_text4);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                onItemClick(view, getAdapterPosition());
            }
        }

        // Convenience method for getting data at click position
        public String getItem(int id) {
            return mData[id];
        }

        // Method that executes your code for the action received
        public void onItemClick(View view, int position) {
            Log.i("TAG", "You clicked number " + getItem(position).toString() + ", which is at cell position " + position);
        }
    }

    public void tableCell_singleClick(View view, LinearLayout parentTableLn, LinearLayout table_row3,
                                      String finalTableidx, String finalTablename, String finalTableTxtColor, int tableordercnt,
                                      String paramTableBoardType) {
        int mTablePeopleCnt = 0;

        TextView title = view.findViewById(R.id.main_table_row_title);

        if (paramTableBoardType.equals("Q")){
            title = view.findViewById(R.id.quick_table_text1);
        }

        title.setTextColor(Color.parseColor(finalTableTxtColor));
        TableSaleMain.mSelectedTablesArrList.remove(finalTableidx);
        title.setText(finalTablename);
        GlobalMemberValues.str_selected_table_name = finalTablename;

        GlobalMemberValues.logWrite("kimwanhayejjjlog", "여기2 : " + TableSaleMain.mSelectedTablesArrList.toString() + "\n");

        if (table_row3.isSelected()) {
            table_row3.setSelected(false);
            if (GlobalMemberValues.isShowQuickMenusInTableBoard) {
                switch (paramTableBoardType) {
                    case "0" : {
                        break;
                    }
                    case "A" : {
                        parentTableLn.setBackgroundResource(R.drawable.roundlayout_table);
                        break;
                    }
                    case "B" : {
                        if (tableordercnt > 0) {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5_2);
                        } else {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5);
                        }
                        break;
                    }
                    case "C" : {
                        break;
                    }
                    case "Q" : {
                        if (tableordercnt > 0) {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_quick_grid_sel);
                        } else {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_quick_grid_not_sel);
                        }
                        break;
                    }
                }
            }

        } else {
            if (!TableSaleMain.getCheckSelectedStatusByOtherStations(finalTableidx)) {
                GlobalMemberValues.displayDialog(mContext, "Warning",
                        "This table is being used by another station", "Close");
                return;
            }

            table_row3.setSelected(true);

            if (GlobalMemberValues.isShowQuickMenusInTableBoard) {
                switch (paramTableBoardType) {
                    case "0" : {
                        break;
                    }
                    case "A" : {
                        parentTableLn.setBackgroundResource(R.drawable.roundlayout_table_selected);
                        break;
                    }
                    case "B" : {
                        if (tableordercnt > 0) {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table_selected5_2);
                        } else {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table_selected5);
                        }
                        break;
                    }
                    case "C" : {
                        break;
                    }
                    case "Q" : {
                        if (tableordercnt > 0) {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table_selected5_2);
                        } else {
                            parentTableLn.setBackgroundResource(R.drawable.roundlayout_table_selected5);
                        }
                        break;
                    }
                }

                title.setText("Sel.");
                title.setTextColor(Color.parseColor("#ffffff"));

                //Toast.makeText(getApplicationContext(), "Table " + finalI + " select!", Toast.LENGTH_SHORT).show();
            }

            TableSaleMain.mSelectedTablesArrList.remove(finalTableidx);
            TableSaleMain.mSelectedTablesArrList.add(finalTableidx);
        }

        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            TableSaleMain.mSelectedTablesArrList.remove(finalTableidx);
            TableSaleMain.mSelectedTablesArrList.add(finalTableidx);
        }

        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "선택된 값 ===============================" + "\n");
        for (String tempidx : TableSaleMain.mSelectedTablesArrList) {
            GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "값 : " + tempidx + "\n");
        }
        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "=========================================" + "\n");

//        if (waitDouble == true) {
//            // single click
//            waitDouble = false;
//            doubleClick_finalTableidx = finalTableidx;
//            Thread thread = new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        sleep(DOUBLE_CLICK_TIME);
//                        if (waitDouble == false) {
//                            waitDouble = true;
//                            //single click event
//
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            thread.start();
//        } else {
//            waitDouble = true;
//
//            if (doubleClick_finalTableidx != finalTableidx){
//                return;
//            }
//
//            //double click event
//            int mSubTableCount = getTableSplitCount(finalTableidx);
//
//            if (mSubTableCount > 0 && mSubTableCount > 1) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
//                builder.setTitle("merge split?");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                                            setTwoSplit(finalTableidx);
//                        setTableMerge(finalTableidx);
//                        //viewTableSettigns(mSelectedZoneIdx);
//                        setInitValues();
//                        // 클라우드 업로드
//                        uploadTableDataCloudExe();
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
//                            setClearSelectedTableIdx(true);
//                        }
//                    }
//                });
//                builder.create();
//                builder.show();
//                setInitValues();
//                return;
//            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
//                builder.setTitle("Table Split");
//                builder.setTitle("Would you like to split this table into two?");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        setTwoSplit(finalTableidx);
//                        //viewTableSettigns(mSelectedZoneIdx);
//                        setInitValues();
//                        // 클라우드 업로드
//                        uploadTableDataCloudExe();
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
//                            setClearSelectedTableIdx(true);
//                        }
//                    }
//                });
//                builder.create();
//                builder.show();
//            }
//        }

        TableSaleMain.doCellSelectEvent();

        // 우측 퀵메뉴가 보여지지 않는 상태일때는 세일 화면으로 바로 이동한다.
        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            TableSaleMain.goSalesMain();
            if (MainMiddleService.mSaleCartAdapter != null) MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();        // 추가된 항목을 Adapter 에 알림
        }
    }
}
