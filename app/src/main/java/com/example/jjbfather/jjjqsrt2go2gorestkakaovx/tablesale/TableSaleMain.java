package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.AdminPassword;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.BayReservationViewer;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CommandButton;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CustomProgressDialog;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DateMethodClass;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.Discount;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.EmployeeSelectionPopup;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.LogsSave;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.OutlineTextView;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.PaxPresentation;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.Payment;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.Recall;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.VerticalTextView;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.WingmanTask;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class TableSaleMain extends Activity {
    final String TAG = "TableSaleMainLog";

    Button btn_table_main_close;
    Button btn_side1,btn_side2,btn_side3,btn_side4,btn_side5,btn_side6,btn_side7,btn_side8,btn_side9,btn_side10, btn_side12;
    // side button 추가
    Button btn_side_qr_print, btn_side_qr_on, btn_side_qr_off;

    // 08092023
    Button btn_side13;

    Button table_main_btn_checklist;
    ImageButton btn_refresh;
    OutlineTextView btn_side11;
    ImageButton tableorderbtn;
    FrameLayout table_main_view;

    public static Activity mActivity;
    public static Context mContext;

    JSONObject temp_price;
    JSONArray temp_arr_price;

    // 04302024
    LinearLayout tablesalemainactivity;

    LinearLayout floorLn;

    int main_100_width = 0;
    int main_100_height = 0;
    int parentLnWidth = 0;
    int parentLnHeight = 0;

    private Long mLastClickTime = 0L;

    public static String mSelectedZoneIdx = "";
    Button mSelectedZoneBtn = null;

    public static ArrayList<String> mAllTablesArrList;
    public static ArrayList<String> mSelectedTablesArrList = null;

    public static String mSelectedTableIDX = "";

    public static ArrayList<String> mTableIdxArrList = null;
    public static String mSubTableNum = "1";
    public static String mMergedNum = "0";
    public static String mTableName = "";
    public static int mTablePeopleCnt = 0;

    static ArrayList<String> mSelectedIdxArrListInCart = null;

    static JSONArray mBillSplitMerge_menulist_array = null;

    static String selectedSubTableHoldCode = "";

    static Intent mIntent;

    String actiontype = "";

    private boolean waitDouble = true;
    private static final int DOUBLE_CLICK_TIME = 500; // double click timer

    //private float f_fontsize_forPAX = GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX();
    private float f_fontsize_forPAX = GlobalMemberValues.getGlobalFontSize();
    private float f_globalFontSize = GlobalMemberValues.globalAddFontSize();

    String doubleClick_finalTableidx = "";

    public Button table_main_btn_side_showup;
    public ImageButton table_main_btn_side_close;
    public LinearLayout table_main_side_view;
    public ScrollView table_main_side_scrollview;

    boolean isPageOpen = false;
    Animation LeftAnim;
    Animation RightAnim;

    public VerticalTextView table_main_btn_side_showup_quick_table_recyclerview;
    //    public Button quick_table_order_btn, quick_table_order_callin_btn, quick_table_popup_btn;
    Animation Quick_LeftAnim;
    Animation Quick_RightAnim;

    int mTempCount = 0;

    public static boolean isFocusViewTable = false;

    public ProgressDialog itemProDial;

    public static String mLastCheckCartIdx = "";
    public static String mLastCheckCartIdx_Del = "";

    public int i_anim_kind = 0;

    public RecyclerView quick_table_grid_list;

    public static boolean isClickCommandOnTable = false;

    public static String[] tableinfo = null;

    public static String[] togo_table_info = null;

    CustomProgressDialog customProgressDialog;

    private ImageView table_main_back_logo;

    // 05.02.2022
    public static ArrayList<String> mSelected_index_for_isCheckedConfrim = null;
    public static boolean isDiscountAdjust = false;

    QuickTable_GridListAdapter quickTable_gridListAdapter;

    RelativeLayout table_main_grid_relative_view;
    LinearLayout table_main_grid_relative_view_loading;

    // 09262023
    public static boolean isAfterMerge = false;

    // 10162024 VX Golf 버튼 추가
    public Button table_main_reservation_btn, table_main_logout_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalMemberValues.logWrite("endtimechecklog", "여긴 테이블창0" + "\n");

        // 상단 Status Bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_main);
        this.setFinishOnTouchOutside(false);


        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 값
            actiontype = mIntent.getStringExtra("actiontype");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 actiontype : " + actiontype + "\n");
            /*******************************************************************************************/
        } else {
            closeTableSaleMain();
        }

        mActivity = this;
        mContext = this;

        // 우측 퀵메뉴가 보여지고 있는지 여부
        GlobalMemberValues.isShowQuickMenusInTableBoard = false;

        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mGlobal_selectedZoneIdx)) {
            mSelectedZoneIdx = GlobalMemberValues.mGlobal_selectedZoneIdx;
        }

        // MainActivity - List 초기화
        // 053023
        if (MainActivity.mActivity != null){
            MainMiddleService.initList();
        }
        // 053023

//        // 로딩관련 =======================================================
//        //로딩창 객체 생성
//        customProgressDialog = new CustomProgressDialog(mContext);
//        //로딩창을 투명하게
//        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        customProgressDialog.show();
//        // ===============================================================


        // 윙맨 wingman
//        String resultText = "";
//        try {
//            resultText = new WingmanTask().execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        GlobalMemberValues.logWrite("wingmanlogjjj", "result : " + resultText + "\n");



        GlobalMemberValues.logWrite("endtimechecklog", "여긴 테이블창1" + "\n");

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalMemberValues.logWrite("endtimechecklog", "여긴 테이블창2" + "\n");
                        setContent();
                    }
                });

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                startingHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler startingHandler = new Handler() {
        public void handleMessage(Message msg) {
            GlobalMemberValues.logWrite("typeopenjjjlog", "type : " + GlobalMemberValues.getStationType() + "\n");
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            GlobalMemberValues.logWrite("endtimechecklog", "여긴 테이블창3" + "\n");

            if (GlobalMemberValues.getStationType() == "R" || GlobalMemberValues.getStationType().equals("R")) {
                setContent2();
            } else {
                closeTableSaleMain();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btn_refresh != null) {
                        btn_refresh.setVisibility(View.VISIBLE);
                    }
                }
            }, 2500);// 0.6초 정도 딜레이를 준 후 시작
            // -------------------------------------------------------------------------------------

            // 로딩관련
//            if (customProgressDialog != null && customProgressDialog.isShowing()) {
//                customProgressDialog.dismiss();
//            }

        }
    };

    private void setContent() {
        // 07212024 - TOrder Send Data
        GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity, "");


        // 09262023
        isAfterMerge = false;

        // 07.19.2022 - add pay for cash, card
        GlobalMemberValues.deleteCartLastItemForAddPay(GlobalMemberValues.getAddPayData());

        // 05.02.2022 ---------------------------------
        isDiscountAdjust = false;
        mSelected_index_for_isCheckedConfrim = null;
        // --------------------------------------------

        // 04222023
        GlobalMemberValues.isRepay3 = false;

        mSelectedTablesArrList = new ArrayList<String>();

        if (mSelectedTablesArrList != null) {
            mSelectedTablesArrList.clear();
        }
        if (mTableIdxArrList != null) {
            mTableIdxArrList.clear();
        }

        GlobalMemberValues.isOpenTableSaleMain = false;

        isFocusViewTable = false;
        mTempCount = 0;

        BillSplitMerge.setInitValuesForBillPay();

        mTablePeopleCnt = 0;

        if (GlobalMemberValues.isThisDeviceElo) {
            f_fontsize_forPAX = 0.5f;
        }

        mAllTablesArrList = new ArrayList<String>();
        mAllTablesArrList.clear();

        selectedSubTableHoldCode = "";
        mTablePeopleCnt = 0;
        GlobalMemberValues.now_saletypeisrestaurant = true;

        // 04302024
        tablesalemainactivity = (LinearLayout)findViewById(R.id.tablesalemainactivity);
        if (GlobalMemberValues.isQSRPOSonRestaurantPOS) {
            tablesalemainactivity.setVisibility(View.GONE);
        } else {
            tablesalemainactivity.setVisibility(View.VISIBLE);
        }

        floorLn = (LinearLayout)findViewById(R.id.floorLn);

        tableorderbtn = (ImageButton)findViewById(R.id.tableorderbtn);
        tableorderbtn.setOnClickListener(clickListener);

        btn_refresh = (ImageButton)findViewById(R.id.table_main_refresh_btn);
        btn_refresh.setOnClickListener(clickListener);
        btn_refresh.setVisibility(View.INVISIBLE);

        btn_side1 = (Button)findViewById(R.id.table_main_btn_side1);
        btn_side1.setTextSize(f_globalFontSize
                + btn_side1.getTextSize() * f_fontsize_forPAX);
        btn_side1.setOnClickListener(clickListener);

        btn_side2 = (Button)findViewById(R.id.table_main_btn_side2);
        btn_side2.setTextSize(f_globalFontSize
                + btn_side2.getTextSize() * f_fontsize_forPAX);
        btn_side2.setOnClickListener(clickListener);

        btn_side3 = (Button)findViewById(R.id.table_main_btn_side3);
        btn_side3.setTextSize(f_globalFontSize
                + btn_side3.getTextSize() * f_fontsize_forPAX);
        btn_side3.setOnClickListener(clickListener);

//        btn_side4 = (Button)findViewById(R.id.table_main_btn_side4);
//        btn_side4.setTextSize(f_globalFontSize
//                + btn_side4.getTextSize() * f_fontsize_forPAX);
//        btn_side4.setOnClickListener(clickListener);

        btn_side5 = (Button)findViewById(R.id.table_main_btn_side5);
        btn_side5.setTextSize(f_globalFontSize
                + btn_side5.getTextSize() * f_fontsize_forPAX);
        btn_side5.setOnClickListener(clickListener);

        btn_side6 = (Button)findViewById(R.id.table_main_btn_side6);
        btn_side6.setTextSize(f_globalFontSize
                + btn_side6.getTextSize() * f_fontsize_forPAX);
        btn_side6.setOnClickListener(clickListener);

        btn_side7 = (Button)findViewById(R.id.table_main_btn_side7);
        btn_side7.setTextSize(f_globalFontSize
                + btn_side7.getTextSize() * f_fontsize_forPAX);
        btn_side7.setOnClickListener(clickListener);

        btn_side8 = (Button)findViewById(R.id.table_main_btn_side8);
        btn_side8.setTextSize(f_globalFontSize
                + btn_side8.getTextSize() * f_fontsize_forPAX);
        btn_side8.setOnClickListener(clickListener);

        btn_side9 = (Button)findViewById(R.id.table_main_btn_side9);
        btn_side9.setTextSize(f_globalFontSize
                + btn_side9.getTextSize() * f_fontsize_forPAX);
        btn_side9.setOnClickListener(clickListener);

        btn_side10 = (Button)findViewById(R.id.table_main_btn_side10);
        btn_side10.setTextSize(f_globalFontSize
                + btn_side10.getTextSize() * f_fontsize_forPAX);
        btn_side10.setOnClickListener(clickListener);

        btn_side11 = (OutlineTextView)findViewById(R.id.table_main_btn_side11);
        btn_side11.setTextSize(f_globalFontSize
                + btn_side11.getTextSize() * f_fontsize_forPAX);
        btn_side11.setOnClickListener(clickListener);

        //
        btn_side12 = (Button) findViewById(R.id.table_main_btn_side12);
        btn_side12.setTextSize(f_globalFontSize + btn_side12.getTextSize() * f_fontsize_forPAX);
        btn_side12.setOnClickListener(clickListener);

        // 08092023
        btn_side13 = (Button) findViewById(R.id.table_main_btn_side13);
        btn_side13.setTextSize(f_globalFontSize + btn_side13.getTextSize() * f_fontsize_forPAX);
        btn_side13.setOnClickListener(clickListener);

        if (!GlobalMemberValues.isOnlineOrderUseYN()){
            btn_side9.setBackgroundResource(R.drawable.table_main_imagebutton_bot);
            btn_side12.setVisibility(View.GONE);
        } else {
            btn_side9.setBackgroundResource(R.drawable.table_main_imagebutton_mid);
            btn_side12.setVisibility(View.VISIBLE);
        }

        btn_side_qr_print = (Button) findViewById(R.id.table_main_btn_side_qr_print);
        btn_side_qr_print.setTextSize(f_globalFontSize + btn_side_qr_print.getTextSize() * f_fontsize_forPAX);
        btn_side_qr_print.setOnClickListener(clickListener);
        btn_side_qr_on = (Button) findViewById(R.id.table_main_btn_side_qr_on);
        btn_side_qr_on.setTextSize(f_globalFontSize + btn_side_qr_on.getTextSize() * f_fontsize_forPAX);
        btn_side_qr_on.setOnClickListener(clickListener);
        btn_side_qr_off = (Button) findViewById(R.id.table_main_btn_side_qr_off);
        btn_side_qr_off.setTextSize(f_globalFontSize + btn_side_qr_off.getTextSize() * f_fontsize_forPAX);
        btn_side_qr_off.setOnClickListener(clickListener);

        if (GlobalMemberValues.isMobileTableOrder()){
            btn_side_qr_print.setVisibility(View.VISIBLE);
            btn_side_qr_on.setVisibility(View.VISIBLE);
            btn_side_qr_off.setVisibility(View.VISIBLE);
        } else {
            btn_side_qr_print.setVisibility(View.GONE);
            btn_side_qr_on.setVisibility(View.GONE);
            btn_side_qr_off.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------------
        table_main_btn_side_showup = findViewById(R.id.table_main_btn_side_showup);
        table_main_btn_side_showup.setOnClickListener(clickListener);
        table_main_btn_side_close = findViewById(R.id.table_main_btn_side_close);
        table_main_btn_side_close.setOnClickListener(clickListener);
        table_main_btn_side_close.setVisibility(View.INVISIBLE);
//        table_main_side_view = findViewById(R.id.table_main_side_view);
        table_main_side_scrollview = findViewById(R.id.table_main_side_scrollview);
        LeftAnim = AnimationUtils.loadAnimation(this, R.anim.table_main_side_slide_show);
        RightAnim = AnimationUtils.loadAnimation(this, R.anim.table_main_side_slide_hide);
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        LeftAnim.setAnimationListener(animListener);
        RightAnim.setAnimationListener(animListener);
        //

        table_main_btn_side_showup_quick_table_recyclerview = findViewById(R.id.table_main_btn_side_showup_quick_table_recyclerview);
        table_main_btn_side_showup_quick_table_recyclerview.setOnClickListener(clickListener);

//        quick_table_order_btn = findViewById(R.id.quick_table_order_btn);
//        quick_table_order_btn.setTextSize(f_globalFontSize
//                + quick_table_order_btn.getTextSize() * f_fontsize_forPAX);
//        quick_table_order_btn.setOnClickListener(clickListener);

//        quick_table_order_callin_btn = findViewById(R.id.quick_table_order_callin_btn);
//        quick_table_order_callin_btn.setTextSize(f_globalFontSize
//                + quick_table_order_callin_btn.getTextSize() * f_fontsize_forPAX);
//        quick_table_order_callin_btn.setOnClickListener(clickListener);

//        quick_table_popup_btn = findViewById(R.id.quick_table_popup_btn);
//        quick_table_popup_btn.setTextSize(f_globalFontSize
//                + quick_table_popup_btn.getTextSize() * f_fontsize_forPAX);
//        quick_table_popup_btn.setOnClickListener(clickListener);

        // 10162024
        table_main_reservation_btn = findViewById(R.id.table_main_reservation_btn);
        table_main_reservation_btn.setOnClickListener(clickListener);
        table_main_logout_btn = findViewById(R.id.table_main_logout_btn);
        table_main_logout_btn.setOnClickListener(clickListener);

        Quick_LeftAnim = AnimationUtils.loadAnimation(this, R.anim.act_out_left);
        Quick_RightAnim = AnimationUtils.loadAnimation(this, R.anim.act_in_right);
        SlidingTogoViewAnimationListener slidingTogoViewAnimationListener = new SlidingTogoViewAnimationListener();
//        Quick_LeftAnim.setAnimationListener(slidingTogoViewAnimationListener);
        Quick_RightAnim.setAnimationListener(slidingTogoViewAnimationListener);

        btn_table_main_close = (Button) findViewById(R.id.table_main_close_btn);
        btn_table_main_close.setTextSize(f_globalFontSize
                + btn_table_main_close.getTextSize() * f_fontsize_forPAX);
        btn_table_main_close.setOnClickListener(clickListener);

        table_main_view = (FrameLayout) findViewById(R.id.table_main_apsolute_view);


        quick_table_grid_list = (RecyclerView)findViewById(R.id.table_main_quick_table_gridview);

        table_main_btn_checklist = findViewById(R.id.table_main_checklist);
        table_main_btn_checklist.setTextSize(f_globalFontSize
                + table_main_btn_checklist.getTextSize() * f_fontsize_forPAX);
        table_main_btn_checklist.setOnClickListener(clickListener);

        table_main_back_logo = (ImageView)findViewById(R.id.table_main_back_logo);

        table_main_grid_relative_view = (RelativeLayout)findViewById(R.id.table_main_grid_relative_view);
        table_main_grid_relative_view_loading = (LinearLayout)findViewById(R.id.table_main_grid_relative_view_loading);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                File path = GlobalMemberValues.ADFILELOCALPATH;
                File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
                String adImage = isFile.getPath();
                if (new File(adImage).exists()) {
//                    table_main_back_logo = new ImageView(mContext);
//                        logoimg.setLayoutParams(new LinearLayout.LayoutParams(576, 200));
//                logoimg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                    table_main_back_logo.setImageBitmap(BitmapFactory.decodeFile(isFile.getAbsolutePath()));
                    //logoimg.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    // ------------------------------------------------------------------------------------------------------------------------------
                }
            }
        });

//        File path = GlobalMemberValues.ADFILELOCALPATH;
//        File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
//        String adImage = isFile.getPath();
//        if (new File(adImage).exists()) {
//            table_main_back_logo = new ImageView(MainActivity.mContext);
////                        logoimg.setLayoutParams(new LinearLayout.LayoutParams(576, 200));
////                logoimg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//            table_main_back_logo.setImageBitmap(BitmapFactory.decodeFile(isFile.getAbsolutePath()));
//            //logoimg.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//            // ------------------------------------------------------------------------------------------------------------------------------
//        }
//        // 로고 프린팅
//        String logoprintingonreceiptyn = MainActivity.mDbInit.dbExecuteReadReturnString(
//                " select logoprintingonreceiptyn from salon_storestationsettings_deviceprinter ");
//        if (GlobalMemberValues.isStrEmpty(logoprintingonreceiptyn)) {
//            logoprintingonreceiptyn = "N";
//        }
//        if (logoprintingonreceiptyn == "Y" || logoprintingonreceiptyn.equals("Y")) {
//
//        }


        if (GlobalMemberValues.isQSRPOSonRestaurantPOS) {
            GlobalMemberValues.mToGoType = "C";
            goSaleMainForToGo();
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                //jihun park sub display
                PaxPresentation.unSetLogo();
                MainActivity.updatePresentation();
            }
        }


        if (GlobalMemberValues.checkServerCodeUseYN()){
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx.equals(GlobalMemberValues.SERVER_IDX)){

            } else {
                // 072424
                // SERVER IDX, NAME 초기화
                GlobalMemberValues.SERVER_IDX = "";
                GlobalMemberValues.SERVER_NAME = "";
                GlobalMemberValues.SERVER_ID = "";
            }

        } else {

        }
    }


    private void setContent2() {
        // 테이블 보드에 적용된 장바구니(temp_salecart)의 idx 최대값을 구한다.
        mLastCheckCartIdx = MssqlDatabase.getResultSetValueToString(
                " select top 1 idx from temp_salecart order by idx desc "
        );
        mLastCheckCartIdx_Del = MssqlDatabase.getResultSetValueToString(
                " select top 1 idx from temp_salecart_del2 order by idx desc "
        );

        // bill pay 가 진행중인데, bill pay 가 완료되지 않았을 경우 ====================================================
//        // bill pay 을 계속 진행할 수 있도록 bill split/merge 창을 오픈한다.
//        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCodeForBillPay_on)) {
//            GlobalMemberValues.logWrite("billpaycontinuelog", "GlobalMemberValues.mHoldCodeForBillPay_on : " + GlobalMemberValues.mHoldCodeForBillPay_on + "\n");
//            GlobalMemberValues.logWrite("billpaycontinuelog", "GlobalMemberValues.mTableIdxOnBillPay_on : " + GlobalMemberValues.mTableIdxOnBillPay_on + "\n");
//            GlobalMemberValues.logWrite("billpaycontinuelog", "GlobalMemberValues.mSubTableNumOnBillPay_on : " + GlobalMemberValues.mSubTableNumOnBillPay_on + "\n");
//            String tempQuery = " select count(*) from bill_list where " +
//                    " holdcode = N'" + GlobalMemberValues.mHoldCodeForBillPay_on + "'" +
//                    " and paidyn = N'N' ";
//            GlobalMemberValues.logWrite("jjjbilllog", "tempQuery : " + tempQuery + "\n");
//            int remainBillCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempQuery));
//            if (remainBillCnt > 0) {
//                mSelectedTablesArrList.clear();
//                mSelectedTablesArrList.add(GlobalMemberValues.mTableIdxOnBillPay_on);
//
//                mSubTableNum = GlobalMemberValues.mSubTableNumOnBillPay_on;
//
//                openBillSplitMerge();
//            } else {
//                GlobalMemberValues.isPaymentByBills = false;
//            }
//        } else {
//            GlobalMemberValues.logWrite("billpaycontinuelog", "mHoldCodeForBillPay_on 값 없음!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            GlobalMemberValues.isPaymentByBills = false;
//        }

        GlobalMemberValues.mIsOnPaymentProcessForBillPay = false;
        GlobalMemberValues.mHoldCodeForBillPay_on = "";
        GlobalMemberValues.mTableIdxOnBillPay_on = "";
        GlobalMemberValues.mSubTableNumOnBillPay_on = "";
        // =======================================================================================================


        setInitValues();
        setInitValuesSub();

        viewZoneSettigns();

//        viewTableSettignsForQuick();

        // 다른 곳에서 해당 activity 를 오픈하면서 어떤 작업을 수행해야 하는 경우..
        if (!GlobalMemberValues.isStrEmpty(actiontype)) {
            switch (actiontype) {
                case "cleartable" : {
                    GlobalMemberValues.logWrite(TAG, "여기실행됨..." + "\n");

                    if (GlobalMemberValues.mArrListForTSM != null && GlobalMemberValues.mArrListForTSM.size() > 0) {
                        GlobalMemberValues.logWrite("TableSaleMainLog",
                                "선택한 tableidx : " + GlobalMemberValues.mArrListForTSM.toString() + "\n");

                        setClearTable(GlobalMemberValues.mArrListForTSM, false);
                    }
                    break;
                }
            }
        }

        // 06.20.2022
        if (GlobalMemberValues.isMultistationsyncuse()) {
            if (GlobalMemberValues.TIMER_NEWCARTCHECKBYSTATION_START_YN == "N" || GlobalMemberValues.TIMER_NEWCARTCHECKBYSTATION_START_YN.equals("N")) {
                runTimer_newCartCheckByStation();
            }
        }
    }

    public static void runTimer_newCartCheckByStation() {
        TimerTask tt_checkweborder = new TimerTask() {
            @Override
            public void run() {
                Message message = timerhandler_newCartCheckByStation.obtainMessage();
                timerhandler_newCartCheckByStation.sendMessage(message);
            }
        };

        /////////// / Timer 생성 //////////////
        Timer timer = new Timer();
        timer.schedule(tt_checkweborder, 0, 5000);
        //////////////////////////////////////

        GlobalMemberValues.TIMER_NEWCARTCHECKBYSTATION_START_YN = "Y";
    }

    public static final Handler timerhandler_newCartCheckByStation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GlobalMemberValues.newCartCheckByStation(mContext,mActivity);
        }
    };


    public static void goSalesMain() {
        int selectedTableCnt = mSelectedTablesArrList.size();
        if (selectedTableCnt > 0) {
            GlobalMemberValues.mSelectedTableIdx = TableSaleMain.mSelectedTablesArrList.get(0);
            GlobalMemberValues.logWrite("selectedtableidxlog", "tableidx : " + GlobalMemberValues.mSelectedTableIdx + "\n");

            if (selectedTableCnt > 1) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                mAllTablesArrList.clear();

                setInitValues();
                setInitValuesSub();
            } else {
                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = getTableSplitCount(mSelectedTablesArrList.get(0));

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    openSplittedTableList("order");
                } else {
                    GlobalMemberValues.logWrite("jjjcustjjjlog", "goSalesMain 에서 시작" + "\n");
                    setOrderStart(mSelectedTablesArrList, false, false);
                }
            }

        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Pleas select a table", "Close");
        }
    }



    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tableorderbtn: {
                    goSalesMain();

                    break;
                }
                case R.id.table_main_btn_side1: {
                    LogsSave.saveLogsInDB(244);
                    doBillPrint();

                    break;
                }
                case R.id.table_main_btn_side2: {
                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<18>")){
                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(245);
                    doBillSplitMerge();

                    break;
                }
                case R.id.table_main_btn_side3: {
                    doKitchenReprint(true);

                    break;
                }
//                case R.id.table_main_btn_side4: {
//                    break;
//                }
                case R.id.table_main_btn_side5: {
                    LogsSave.saveLogsInDB(246);
                    doOpenDetailInfo();

                    break;
                }
                case R.id.table_main_btn_side6: {
                    LogsSave.saveLogsInDB(247);
                    if (mSelectedTablesArrList.size() == 2) {
                        checkTransferTable();
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select two tables", "Close");
                    }

                    break;
                }
                case R.id.table_main_btn_side7: {

                    Intent adminPasswordIntent = new Intent(mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "alltableclear");
                    adminPasswordIntent.putExtra("passwordType", "masterpwd");
                    // -------------------------------------------------------------------------------------
//                    insContext = mContext;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivityForResult(adminPasswordIntent, 9898);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.table_main_close_btn: {
                    LogsSave.saveLogsInDB(234);
                    setCloseActivity(true);
                    if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                    } else {
                        TableSaleMain.isClickCommandOnTable = true;
                        GlobalMemberValues.setFrameLayoutVisibleChange("commandButton");
                        CommandButton cb = new CommandButton(MainActivity.mActivity, MainActivity.mContext, MainActivity.dataAtSqlite);
                        cb.setContents();
                        //setContents();
                    }
                    break;
                }

                case R.id.table_main_checklist : {
                    LogsSave.saveLogsInDB(233);
                    Intent checkListIntent = new Intent(mContext.getApplicationContext(), TableCheckList.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------

                    // -------------------------------------------------------------------------------------
//                    insContext = mContext;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(checkListIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }

                case R.id.table_main_btn_side8: {
                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<19>")){
                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    if (mSelectedTablesArrList.size() > 0) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {
                            // 선택된 테이블이 1개이고, 선택된 테이블이 Merge 된 테이블이면 다시 merge 하지 못한다.
                            if (mSelectedTablesArrList.size() == 1 && getTableCountByTableidxInMergedTable(mSelectedTablesArrList.get(0)) > 0) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Merged tables cannot be merged again.", "Close");
                                setInitValues();
                                return;
                            }

                            // 선택된 테이블이 1개이고, 선택된 테이블이 split 된 테이블이 아닌 일반 테이블이면 merge 하지 못한다.
                            if (mSelectedTablesArrList.size() == 1 && getTableSplitCount(mSelectedTablesArrList.get(0)) == 0) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select 2 or more tables to merge.", "Close");
                                setInitValues();
                                return;
                            }

                            // 선택된 테이블중에 bill split 이 되거나 bill split 후 결제된 내역이 있는지 확인한다. ---------------------------------------------
                            String tempHoldCode = getHoldCodeByTableidx(mSelectedTablesArrList.get(0), TableSaleMain.mSubTableNum);
                            int tempBillCnt = GlobalMemberValues.getIntAtString(
                                    MssqlDatabase.getResultSetValueToString(" select count(*) from bill_list where holdcode = '" + tempHoldCode + "' ")
                            );
                            int tempBillPaidCnt = GlobalMemberValues.getIntAtString(
                                    MssqlDatabase.getResultSetValueToString(" select count(*) from bill_list_paid where holdcode = '" + tempHoldCode + "' ")
                            );
                            if (isSplitBillOrPaid()) {
                                GlobalMemberValues.displayDialog(mContext, "Warning",
                                        "Tables with split bills or partially paid cannot be merged.", "Close");
                                setInitValues();
                                return;
                            }
                            // ----------------------------------------------------------------------------------------------------------------------
                            LogsSave.saveLogsInDB(249);

                            new AlertDialog.Builder(mActivity)
                                    .setTitle("Table Merge")
                                    .setMessage("Do you want to merge the selected tables?")
                                    //.setIcon(R.drawable.ic_launcher)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    setTableMerge("");
                                                }
                                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            setClearSelectedTableIdx(false);
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a tables to merge", "Close");
                    }

                    break;
                }


                // 08092023
                case R.id.table_main_btn_side13: {
                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<19>")){
                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    if (mSelectedTablesArrList.size() > 0) {

                        // 선택된 테이블이 1개이고, 선택된 테이블이 Merge 된 테이블이면 다시 combine 하지 못한다.
                        if (mSelectedTablesArrList.size() == 1 && getTableCountByTableidxInMergedTable(mSelectedTablesArrList.get(0)) > 0) {
                            GlobalMemberValues.displayDialog(mContext, "Warning", "Merged tables cannot be combined again.", "Close");
                            setInitValues();
                            return;
                        }

                        // 선택된 테이블이 1개이고, 선택된 테이블이 split 된 테이블이 아닌 일반 테이블이면 combine 하지 못한다.
                        if (mSelectedTablesArrList.size() == 1 && getTableSplitCount(mSelectedTablesArrList.get(0)) == 0) {
                            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select 2 or more tables to combine.", "Close");
                            setInitValues();
                            return;
                        }

                        // 선택된 테이블중에 bill split 이 되거나 bill split 후 결제된 내역이 있는지 확인한다. ---------------------------------------------
                        if (isSplitBillOrPaid()) {
                            GlobalMemberValues.displayDialog(mContext, "Warning",
                                    "Tables with split bills or partially paid cannot be combined.", "Close");
                            setInitValues();
                            return;
                        }
                        // ----------------------------------------------------------------------------------------------------------------------
                        LogsSave.saveLogsInDB(249);

                        Intent intent = new Intent(getApplicationContext(), TableCombine.class);
                        startActivity(intent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }

                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a tables to combine", "Close");
                    }

                    break;
                }


                case R.id.table_main_btn_side9: {
                    LogsSave.saveLogsInDB(250);
                    doTableClear(mSelectedTablesArrList);

                    break;
                }

                case R.id.table_main_btn_side10: {
                    LogsSave.saveLogsInDB(248);
                    doTableSplit();

                    break;
                }
                case R.id.table_main_btn_side11 : {
                    setCloseActivity(true);
                    break;
                }
                case R.id.table_main_btn_side12 : {
                    GlobalMemberValues.isSoundContinue = false;
                    break;
                }
                case R.id.table_main_refresh_btn : {
                    // viewTableSettigns(mSelectedZoneIdx);
                    reLoadTableSaleMain();
                    break;
                }

                case R.id.table_main_btn_side_showup : {
                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<15>")){
                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(236);
                    i_anim_kind = 0;

                    //viewTableSettigns(mSelectedZoneIdx);

//                    Animation anim = AnimationUtils.loadAnimation
//                            (getApplicationContext(), // 현재화면의 제어권자
//                                    R.anim.table_main_side_slide_show);   // 에니메이션 설정 파일
//                    table_main_side_scrollview.requestLayout();
                    table_main_side_scrollview.setVisibility(View.VISIBLE);
                    table_main_side_scrollview.startAnimation(LeftAnim);
//                    TranslateAnimation translateAnimation = new TranslateAnimation(
//
//                            Animation.RELATIVE_TO_SELF,0,
//
//                            Animation.RELATIVE_TO_SELF, 0,
//
//                            Animation.RELATIVE_TO_SELF,500,
//
//                            Animation.RELATIVE_TO_SELF,0);
//
//                    translateAnimation.setDuration(500);
//                    table_main_side_scrollview.startAnimation(translateAnimation);

                    table_main_btn_side_showup.setVisibility(View.INVISIBLE);
                    table_main_btn_side_close.setVisibility(View.VISIBLE);

                    // 우측 퀵메뉴가 보여지고 있는지 여부
                    GlobalMemberValues.isShowQuickMenusInTableBoard = true;

                    break;
                }
                case R.id.table_main_btn_side_close : {
                    i_anim_kind = 0;
//                    Animation anim = AnimationUtils.loadAnimation
//                            (getApplicationContext(), // 현재화면의 제어권자
//                                    R.anim.table_main_side_slide_hide);   // 에니메이션 설정 파일
//                    table_main_side_scrollview.requestLayout();

                    table_main_side_scrollview.startAnimation(RightAnim);

//                    TranslateAnimation translateAnimation = new TranslateAnimation(
//
//                            Animation.RELATIVE_TO_SELF,0,
//
//                            Animation.RELATIVE_TO_SELF, 0,
//
//                            Animation.RELATIVE_TO_SELF,-500,
//
//                            Animation.RELATIVE_TO_SELF,0);
//
//                    translateAnimation.setDuration(500);
//
//                    table_main_side_scrollview.startAnimation(translateAnimation);


                    table_main_btn_side_showup.setVisibility(View.VISIBLE);
                    table_main_btn_side_close.setVisibility(View.INVISIBLE);

                    // 우측 퀵메뉴가 보여지고 있는지 여부
                    GlobalMemberValues.isShowQuickMenusInTableBoard = false;

                    viewTableSettigns(mSelectedZoneIdx);
                    break;
                }

//                case R.id.quick_table_order_callin_btn : {
//                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<17>")){
//                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
//                        return;
//                    }
//                    GlobalMemberValues.mToGoType = "C";
//                    goSaleMainForToGo();
//                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
//                        //jihun park sub display
//                        PaxPresentation.unSetLogo();
//                        MainActivity.updatePresentation();
//                    }
//                    break;
//                }

//                case R.id.quick_table_order_btn : {
//                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<17>")){
//                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
//                        return;
//                    }
//                    GlobalMemberValues.mToGoType = "W";
//                    goSaleMainForToGo();
//                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
//                        //jihun park sub display
//                        PaxPresentation.unSetLogo();
//                        MainActivity.updatePresentation();
//                    }
//                    break;
//                }


//                case R.id.quick_table_popup_btn : {
//                    LogsSave.saveLogsInDB(235);
//                    if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<17>")){
//                        GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
//                        return;
//                    }
////                    break;
//                }

//                case R.id.table_main_btn_side_showup_quick_table_recyclerview : {
//                    i_anim_kind = 1;
//
//                    if (table_main_grid_relative_view.getVisibility() == View.VISIBLE){
//                        quick_table_popup_btn.setSelected(false);
//                        table_main_grid_relative_view.setVisibility(View.INVISIBLE);
//                        table_main_grid_relative_view_loading.setVisibility(View.VISIBLE);
//                        table_main_grid_relative_view.startAnimation(Quick_LeftAnim);
//                    } else {
//                        quick_table_popup_btn.setSelected(true);
//                        table_main_grid_relative_view.startAnimation(Quick_RightAnim);
//                        table_main_grid_relative_view.setVisibility(View.VISIBLE);
//                    }
//
//                    break;
//                }

                case R.id.table_main_btn_side_qr_on:

                    if (mSelectedTablesArrList.size() > 0) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {


                            if (mSelectedTablesArrList.size() > 1) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                                setInitValues();
                                return;
                            }

                            if (GlobalMemberValues.isChangedQRCodeOrderStatus(mSelectedTablesArrList.get(0), "Y")){
                                GlobalMemberValues.displayDialog(mContext, "QR Code","Changed QR Code order status to On", "Close");
                            } else {
                                GlobalMemberValues.displayDialog(mContext, "QR Code","Failed to change QR Code order status to On", "Close");
                            }
                        }
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
                    }

                    break;
                case R.id.table_main_btn_side_qr_off:

                    if (mSelectedTablesArrList.size() > 0) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {


                            if (mSelectedTablesArrList.size() > 1) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                                setInitValues();
                                return;
                            }

                            if (GlobalMemberValues.isChangedQRCodeOrderStatus(mSelectedTablesArrList.get(0), "N")){
                                GlobalMemberValues.displayDialog(mContext, "QR Code","Changed QR Code order status to Off", "Close");
                            } else {
                                GlobalMemberValues.displayDialog(mContext, "QR Code","Failed to change QR Code order status to Off", "Close");
                            }
                        }
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
                    }

                    break;
                case R.id.table_main_btn_side_qr_print:
                    if (mSelectedTablesArrList.size() > 0) {
                        if ((mActivity != null) && (!mActivity.isFinishing())) {


                            if (mSelectedTablesArrList.size() > 1) {
                                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                                setInitValues();
                                return;
                            }

                            // print!
                            GlobalMemberValues.printQRCode(mContext,mSelectedTablesArrList.get(0));

                        }
                    } else {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
                    }

                    break;

                case R.id.table_main_reservation_btn:
                    Intent bayReservationWindowIntent = new Intent(MainActivity.mContext.getApplicationContext(), BayReservationViewer.class);
                    //10172024 add intent to differentiate between activity started from table window or command window
                    bayReservationWindowIntent.putExtra("OpenedFromTable", true);
                    mActivity.startActivity(bayReservationWindowIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                case R.id.table_main_logout_btn:
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Employee Logout")
                            .setMessage("Do you want to logout?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.employeeLogout();
                                }
                            }).show();
                    break;


            }
        }
    };

    public void goSaleMainForToGo() {
        String strQuery = "";
        strQuery = "select count(idx) from salon_store_restaurant_table " +
                " where quicksaleyn = 'Y' ";
        int dataCount = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(strQuery));
        if (dataCount > 0) {
            String getArr[] = new String[dataCount];

            strQuery = "select idx " +
                    " from salon_store_restaurant_table " +
                    " where quicksaleyn = 'Y' " +
                    " order by idx asc";

            GlobalMemberValues.logWrite("tablejjjlog", "sql : " + strQuery + "\n");

            int tableCountOnSales = 0;
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            while (dataCursor.moveToNext()) {
                String tableidx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";

                String getHoldCodeTemp = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

                int tableordercnt = GlobalMemberValues.getIntAtString(
                        MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' ")
                );
                if (tableordercnt == 0) {
                    mSelectedTablesArrList.remove(tableidx);
                    mSelectedTablesArrList.add(tableidx);

                    doCellSelectEvent();
                    goSalesMain();

                    tableCountOnSales = 0;

                    break;
                } else {
                    tableCountOnSales++;
                }
            }
            dataCursor.close();
            if (dataCount == tableCountOnSales || tableCountOnSales > 0) {
                GlobalMemberValues.displayDialog(mContext, "", "There are no empty space to togo\nPlease clear the to go list", "Close");
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "", "There are no tables to togo", "Close");
        }
    }

    public static boolean isSplitBillOrPaid() {
        boolean returnValue = false;
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            String tempHoldCode = getHoldCodeByTableidx(mSelectedTablesArrList.get(0), TableSaleMain.mSubTableNum);
            int tempBillCnt = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString(" select count(*) from bill_list where holdcode = '" + tempHoldCode + "' ")
            );
            int tempBillPaidCnt = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString(" select count(*) from bill_list_paid where holdcode = '" + tempHoldCode + "' ")
            );

            if (tempBillCnt > 0 || tempBillPaidCnt > 0) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public void doBillPrint() {
        if (mSelectedTablesArrList.size() > 0) {
            if (mSelectedTablesArrList.size() > 1) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                setInitValues();
                return;
            } else {
                // 주문이 있는 테이블만 처리하도록
                int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(idx) from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "
                ));
                if (tableOrderCount == 0) {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "There is no order history in the selected table", "Close");
                    setInitValues();
                    return;
                }

                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = getTableSplitCount(mSelectedTablesArrList.get(0));

                // selected table idx bill print 때문에 생성
                mSelectedTableIDX = mSelectedTablesArrList.get(0);

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    openSplittedTableList("billprint");
                } else {
                    String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                            " select distinct holdcode from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "
                    );

                    // 10092023
                    setGratuity(temp_holdcode);

                    int billCount = GlobalMemberValues.getIntAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from bill_list where holdcode = '" + temp_holdcode + "' "
                            )
                    );
                    if (billCount > 0) {
                        doBillSplitMerge();
                    } else {
                        openBillPrint("N");
                    }
                }
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
            setInitValues();
            return;
        }
    }

    public void doOpenDetailInfo() {
        // 주문이 있는 테이블만 처리하도록
        if (mSelectedTablesArrList.size() == 0) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
            return;
        }

        int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "
        ));
        if (tableOrderCount == 0) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "There is no order history in the selected table", "Close");
            setInitValues();
            return;
        }

        if (mSelectedTablesArrList.size() > 0) {
            if (mSelectedTablesArrList.size() > 1) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                setInitValues();
                return;
            } else {
                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = getTableSplitCount(mSelectedTablesArrList.get(0));

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    openSplittedTableList("detailinfo");
                } else {
                    openDetailInfo();
                }
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
            setInitValues();
            return;
        }
    }

    public void doTableClear(ArrayList<String> paramArr) {
        GlobalMemberValues.logWrite("jjjclearlog", "mSelectedTablesArrList2 : " + paramArr.toString() + "\n");
        if ((mActivity != null) && (!mActivity.isFinishing())) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("Table Clear")
                    .setMessage("Do you want to empty the selected tables?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    GlobalMemberValues.logWrite("jjjclearlog", "mSelectedTablesArrList3 : " + paramArr.toString() + "\n");
                                    setClearTable(paramArr, true);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                setClearSelectedTableIdx(true);
                            }
                        }
                    })
                    .show();
        }
    }

    public void doKitchenReprint(boolean paramIsOnBillPrint) {
        if (TableSaleMain.mSelectedTablesArrList.size() > 0) {
            if (TableSaleMain.mSelectedTablesArrList.size() > 1) {
                GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "Please select only one table", "Close");
                TableSaleMain.setInitValues();
                return;
            } else {
                // 주문이 있는 테이블만 처리하도록
                int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(idx) from temp_salecart where tableidx like '%" + TableSaleMain.mSelectedTablesArrList.get(0) + "%' "
                ));
                if (tableOrderCount == 0) {
                    GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "There is no order history in the selected table", "Close");
                    TableSaleMain.setInitValues();
                    return;
                }

                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = TableSaleMain.getTableSplitCount(TableSaleMain.mSelectedTablesArrList.get(0));

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    TableSaleMain.openSplittedTableList("kitchenprinting");
                } else {
                    GlobalMemberValues.isKitchenReprinting = "K";

                    // 키친프린팅 실행
                    TableSaleMain.kitchenPrint(getHoldCodeByTableidx(TableSaleMain.mSelectedTablesArrList.get(0), TableSaleMain.mSubTableNum), true);

                    if (paramIsOnBillPrint) {
                        viewTableSettigns(TableSaleMain.mSelectedZoneIdx);
                    }
                }
            }
            TableSaleMain.setInitValues();
        } else {
            GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "Please select a table", "Close");
            TableSaleMain.setInitValues();
            return;
        }
    }

    public void doTableSplit() {
        if (mSelectedTablesArrList.size() > 0) {
            // Merge 된 테이블은 split 하지 못한다.
            if (mSelectedTablesArrList.size() == 1 && getTableCountByTableidxInMergedTable(mSelectedTablesArrList.get(0)) > 0) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Select all merged tables if the table is already joined", "Close");
                setInitValues();
                return;
            }
            setTableSplit();
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
            return;
        }
    }

    public void doBillSplitMerge() {
        if (mSelectedTablesArrList.size() > 0) {
            if (mSelectedTablesArrList.size() > 1) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                setInitValues();
                return;
            } else {
                // 주문이 있는 테이블만 처리하도록
                int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(idx) from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "
                ));
                if (tableOrderCount == 0) {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "There is no order history in the selected table", "Close");
                    setInitValues();
                    return;
                }

                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = getTableSplitCount(mSelectedTablesArrList.get(0));

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    openSplittedTableList("billsplit");
                } else {
                    openBillSplit();
                }
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Please select a table", "Close");
            setInitValues();
            return;
        }
    }

    public void viewZoneSettigns() {
        // -------------------------------------------------------------------------------------
        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        floorLn.setVisibility(View.VISIBLE);
        floorLn.setAnimation(animation1);
        // -------------------------------------------------------------------------------------

        floorLn.removeAllViews();

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
        String[] tableZone = globalMemberValues.getRestaurantTableZone();
        if (tableZone != null && tableZone.length > 0) {
            String[] tablearr = null;
            String zoneidx = "";
            String zonename = "";
            String tablecounts = "";
            String zonetype = "";

            int zonei = 0;
            for (int i = 0; i < tableZone.length; i++) {
                tablearr = tableZone[i].split("-JJJ-");
                zoneidx = tablearr[0];
                zonename = tablearr[1];
                tablecounts = tablearr[2];
                zonetype = tablearr[3];

                if (!GlobalMemberValues.isStrEmpty(zonename)) {
                    // Button
                    Button floorBtn = new Button(this);
                    floorBtn.setText(zonename);
                    floorBtn.setTag(zoneidx);
                    floorBtn.setTextColor(Color.parseColor("#ffffff"));
                    floorBtn.setTextSize(26.0f);
                    floorBtn.setTextSize(f_globalFontSize
                            + floorBtn.getTextSize() * f_fontsize_forPAX);
                    floorBtn.setPaintFlags(floorBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    floorBtn.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams linearLayout =new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                    linearLayout.setMargins(2,0,2,0);
                    floorBtn.setLayoutParams(linearLayout);
                    floorBtn.setBackgroundResource(R.drawable.button_selector_tablemain_floor_btn);
//                    floorBtn.setBackgroundResource(R.drawable.ab_imagebutton_zonebutton);
                    floorBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 기존에 선택된 버튼이 있을 경우
                            // 버튼의 이미지를 원래이미지로 원복한다.
                            if (mSelectedZoneBtn != null) {
                                mSelectedZoneBtn.setBackgroundResource(R.drawable.button_selector_tablemain_floor_btn);
//                                mSelectedZoneBtn.setBackgroundResource(R.drawable.ab_imagebutton_zonebutton);
                            }
                            mSelectedZoneBtn = (Button)v;

                            // 선택된 zone 초기화
                            GlobalMemberValues.mGlobal_selectedZoneIdx = "";

                            mAllTablesArrList.clear();
                            // 테이블 선택 초기화
                            setInitValues();

                            GlobalMemberValues.tablesalemain_open_cnt = 0;

                            String tempZoneIdx = v.getTag().toString();
                            viewTableSettigns(tempZoneIdx);
                        }
                    });
                    floorLn.addView(floorBtn);

                    if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mGlobal_selectedZoneIdx)) {
                        GlobalMemberValues.logWrite("zonejjjlog3", "글로벌멤버변수가 있음..." + "\n");

                        GlobalMemberValues.logWrite("zonejjjlog3", "GlobalMemberValues.mGlobal_selectedZoneIdx : " + GlobalMemberValues.mGlobal_selectedZoneIdx + "\n");
                        GlobalMemberValues.logWrite("zonejjjlog3", "zoneidx : " + zoneidx + "\n");

                        if (GlobalMemberValues.mGlobal_selectedZoneIdx.equals(zoneidx)) {
                            mSelectedZoneBtn = floorBtn;
                            floorBtn.setBackgroundResource(R.drawable.button_selector_tablemain_floor_btn);
//                            floorBtn.setBackgroundResource(R.drawable.ab_imagebutton_zonebutton2);
                        }
                    } else {
                        if (zonei == 0) {
                            mSelectedZoneIdx = zoneidx;
                            GlobalMemberValues.mGlobal_selectedZoneIdx = zoneidx;

                            mSelectedZoneBtn = floorBtn;
                            mSelectedZoneIdx = zoneidx;
                            floorBtn.setBackgroundResource(R.drawable.button_selector_tablemain_floor_btn);
//                            floorBtn.setBackgroundResource(R.drawable.ab_imagebutton_zonebutton2);
                        }
                    }

//                    if (zonei == 0) {
//                        mSelectedZoneIdx = zoneidx;
//                        GlobalMemberValues.mGlobal_selectedZoneIdx = zoneidx;
//                    }

                    zonei++;

                    tablearr = null;
                    zoneidx = "";
                    zonename = "";
                    tablecounts = "";
                    zonetype = "";
                }
            }
        }
    }

    public void viewTableSettigns(String paramZoneIdx) {
        setClearSelectedTableIdx(true);

        GlobalMemberValues.logWrite("zonejjjlog", "여기실행됨..." + "\n");
        GlobalMemberValues.logWrite("zonejjjlog", "paramZoneIdx : " + paramZoneIdx + "\n");

        // 선택한 zone idx 저장
        mSelectedZoneIdx = paramZoneIdx;
        GlobalMemberValues.mGlobal_selectedZoneIdx = paramZoneIdx;

        if (mSelectedZoneBtn != null) {
            mSelectedZoneBtn.setBackgroundResource(R.drawable.roundlayout_btn_floor_main_on);
//            mSelectedZoneBtn.setBackgroundResource(R.drawable.ab_imagebutton_zonebutton2);
        }

        // 초기화
        if (table_main_view != null) table_main_view.removeAllViews();

        GlobalMemberValues gm = new GlobalMemberValues();

        if (GlobalMemberValues.tablesalemain_open_cnt == 0) {
            tableinfo = gm.getRestaurantTable(paramZoneIdx);
            GlobalMemberValues.logWrite("tablesalemainmakelogjjj", "여기실행1 : " + GlobalMemberValues.tablesalemain_open_cnt + "\n");
        }
        GlobalMemberValues.logWrite("tablesalemainmakelogjjj", "여기실행2 : " + GlobalMemberValues.tablesalemain_open_cnt + "\n");
        GlobalMemberValues.tablesalemain_open_cnt++;

        //GlobalMemberValues.logWrite("zonejjjlog", "tableinfo.length : " + tableinfo.length + "\n");

        gm.s_str_tableinfo = null;
        gm.s_str_tableinfo = tableinfo;

        // 저장된 테이블이 있을 경우에만..
        if (tableinfo != null && tableinfo.length > 0) {
            // Table Board Type 구하기
            String tempTableBoardType = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select tableboardtype from salon_storestationsettings_system "
            );
            if (GlobalMemberValues.isStrEmpty(tempTableBoardType)) {
                tempTableBoardType = "1";
            }

            GlobalMemberValues.logWrite("zonejjjlog", "tempTableBoardType : " + tempTableBoardType + "\n");

            switch (tempTableBoardType) {
                case "A" : {
                    break;
                }
                case "1" : {
                    viewTableSettingsTypeA(tableinfo);
                    break;
                }
                case "2" : {
                    GlobalMemberValues.logWrite("zonejjjlog", "여기실행됨...J" + "\n");
                    viewTableSettingsTypeB(tableinfo);
                    break;
                }
                case "3" : {
                    viewTableSettingsTypeC(tableinfo);
                    break;
                }
            }
        }
//        viewTableSettignsForQuick();
        setInitOnFocus();
    }



    public void viewTableSettignsForQuick() {
//        setClearSelectedTableIdx(true);
//
//        // 초기화
//        table_main_view.removeAllViews();

//        GlobalMemberValues gm = new GlobalMemberValues();


        String[] tableinfo = GlobalMemberValues.getRestaurantTableForQuick();

//        gm.s_str_tableinfo = null;
        GlobalMemberValues.s_str_tableinfo = tableinfo;

        // 저장된 테이블이 있을 경우에만..
        if (tableinfo != null && tableinfo.length > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    QuickViewHolder temp_quickTable_gridListAdapter = new QuickViewHolder(TableSaleMain.mContext, tableinfo);
                    quick_table_grid_list.setLayoutManager(new GridLayoutManager(TableSaleMain.mContext,3));
                    RecyclerView.ItemAnimator animator = quick_table_grid_list.getItemAnimator();
                    if (animator instanceof SimpleItemAnimator) {
                        ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
                    }
                    quick_table_grid_list.setAdapter(temp_quickTable_gridListAdapter);
//                    quick_table_grid_list.deferNotifyDataSetChanged();
                }
            });

        }
    }

    public class QuickViewHolder extends RecyclerView.Adapter<QuickViewHolder.ViewHolder> {

        private String[] mData = new String[0];
        private LayoutInflater mInflater;

        // Data is passed into the constructor
        public QuickViewHolder(Context context, String[] data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // Inflates the cell layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.quick_table_grid_row, parent, false);
            //            View view = LayoutInflater.from(context)
//                    .inflate(R.layout.quick_table_grid_row, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 120));
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        // Binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {




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
            mAllTablesArrList.add(tableidx);

            // 테이블이 split 되어 있는지 확인
            int subTableCount = getTableSplitCount(tableidx);

            if (subTableCount > 0 && subTableCount > 1) {
                String tempSubTableStr = subTableCount + "ea";
                tablename = tempSubTableStr + " Split";
//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                holder.textview1.setText(tablename);
            }

            String tableTxtColor = "#ffffff";

            String getHoldCodeTemp = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

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
                holder.textview4 = timeupdate_in_textview(holder.textview4,date1);
            }



//            textview4.setText("");
            if (mergednum > 0) {
                if (getTableCountByHoldcode(getHoldCodeTemp) == 1
                        && getTableCountByTableidxInMergedTable(tableidx) < 2) {
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
                if (getTableCountByTableidxInMergedTable(tableidx) > 1) {
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
                tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' ");
                GlobalMemberValues.getIntAtString(tableordercnt);
                GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

            } else {
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
            holder.qt_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

            // table double touch


            // table longclick
            holder.qt_row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tableCell_longClick(v);
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


    public class QuickTable_GridListAdapter implements ListAdapter {
        public Context context;
        public String[] itemList;

        public QuickTable_GridListAdapter(Context context, String[] itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return this.itemList.length;
        }

        @Override
        public Object getItem(int position) {
            return this.itemList[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.quick_table_grid_row, parent, false);
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 120));

            LinearLayout qt_row = view.findViewById(R.id.quick_table_row_ln);
            TextView textview2 = view.findViewById(R.id.quick_table_text2);
            TextView textview1 = view.findViewById(R.id.quick_table_text1);
            TextView textview3 = view.findViewById(R.id.quick_table_text3);
            TextView textview4 = view.findViewById(R.id.quick_table_text4);

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

            tablearr = itemList[position].split("-JJJ-");

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

            textview1.setText(tablename);


            tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
            mAllTablesArrList.add(tableidx);

            // 테이블이 split 되어 있는지 확인
            int subTableCount = getTableSplitCount(tableidx);

            if (subTableCount > 0 && subTableCount > 1) {
                String tempSubTableStr = subTableCount + "ea";
                tablename = tempSubTableStr + " Split";
//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                textview1.setText(tablename);
            }

            String tableTxtColor = "#ffffff";

            String getHoldCodeTemp = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

            int mergednum = 0;

            // mergednum -------------------------------------------------------------------------
            textview2.setTextSize(30);
            textview2.setText(customername);
            textview3.setText(customerphonenum);


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
                            textview3.setText("Call In");
                        } else {
                            textview3.setText(customerphonenum + "(Call In)");
                        }

                        break;
                    }
                    case "W" : {
                        if (!customerphonenum.equals("Walk In")) {
                            textview3.setText(customerphonenum + "(Walk In)");
                        }
                        break;
                    }
                }
            }

            String date1 = ordereddateValue;

            if (GlobalMemberValues.isTimeDisplayonTable()){
                textview4 = timeupdate_in_textview(textview4,date1);
            }



//            textview4.setText("");
            if (mergednum > 0) {
                if (getTableCountByHoldcode(getHoldCodeTemp) == 1
                        && getTableCountByTableidxInMergedTable(tableidx) < 2) {
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

                    textview1.setText(tablename);
//                    textview1.setTextColor(Color.parseColor(tableTxtColor));
                    int tablecolornum = mergednum % 7;
//                    textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                    textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                    textview2.setText("   Merged Table   ");
                    textview2.setTextSize(20);
                    textview2.setTextColor(Color.parseColor("#ffffff"));
                    qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
                }
            } else {
                if (getTableCountByTableidxInMergedTable(tableidx) > 1) {
                    String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(tableidx);
                    if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {




                        mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(tableidx);

                        String mergednumstr = "0" + mergednum;
                        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                        tablename = "Merged\n(" + mergednumstr + ")";

                        textview1.setText(tablename);
//                       textview1.setTextColor(Color.parseColor(tableTxtColor));

                        int tablecolornum = mergednum % 7;
//                        textview1.setTextColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));

                        textview2.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                        textview2.setText("   Merged Table   ");
                        textview2.setTextSize(20);
                        textview2.setTextColor(Color.parseColor("#ffffff"));
                        qt_row.setBackgroundResource(R.drawable.table_quicktable_btn_back_on);
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
                qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_sel);

                // 해당 테이블 주문메뉴 수
//            tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' ");
                tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' ");
                GlobalMemberValues.getIntAtString(tableordercnt);
                GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

            } else {
                qt_row.setBackgroundResource(R.drawable.roundlayout_quick_grid_not_sel);
            }


            if (GlobalMemberValues.isDeviceSunmiFromDB()){
                textview1.setTextSize(10);
                textview2.setTextSize(22);
                textview3.setTextSize(16);
            }


            final String finalTablename = tablename;
            final String finalTableidx = tableidx;
            final String finalTableTxtColor = tableTxtColor;
            qt_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BillSplitMerge.setInitValuesForBillPay();

                    tableCell_singleClick(view, qt_row, qt_row, finalTableidx,
                            finalTablename, finalTableTxtColor, tableordercnt_int, "Q");

                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        //jihun park sub display
                        PaxPresentation.unSetLogo();
                        MainActivity.updatePresentation();
                    }
                }
            });

            // table double touch


            // table longclick
            qt_row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tableCell_longClick(v);
                    return false;
                }
            });
            qt_row.setTag(tableidx);



            return view;

//            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

    }


    public static void onRefreshAtOutside() {
        if (!TableSaleMain.isClickCommandOnTable){
            reLoadTableSaleMain();
        }
    }

    public static void reLoadTableSaleMain() {
//        Intent intent = TableSaleMain.mActivity.getIntent();
//        TableSaleMain.mActivity.startActivity(intent);
//        TableSaleMain.mActivity.finish();
        Activity tempActivity = mActivity;

        mActivity.overridePendingTransition(0, 0);//인텐트 효과 없애기
        //Intent intent = getIntent(); //인텐트
        mActivity.startActivity(mIntent); //액티비티 열기
        mActivity.overridePendingTransition(0, 0);//인텐트 효과 없애기

        tempActivity.finish();//인텐트 종료
    }

    public void tableCell_singleClick(View view, LinearLayout parentTableLn, LinearLayout table_row3,
                                      String finalTableidx, String finalTablename, String finalTableTxtColor, int tableordercnt,
                                      String paramTableBoardType) {
        //08282024 perform a quick db transaction to see if db is connected if not, put a warning message and exit method.
        String returnResult = MssqlDatabase.getResultSetValueToString("select top 1 sid from basic_pos_information");

        if (returnResult.equals("")) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
            return;
        }

        mTablePeopleCnt = 0;

        TextView title = view.findViewById(R.id.main_table_row_title);

        if (paramTableBoardType.equals("Q")){
            title = view.findViewById(R.id.quick_table_text1);
        }

        title.setTextColor(Color.parseColor(finalTableTxtColor));
        mSelectedTablesArrList.remove(finalTableidx);
        title.setText(finalTablename);
        GlobalMemberValues.str_selected_table_name = finalTablename;

        GlobalMemberValues.logWrite("kimwanhayejjjlog", "여기2 : " + mSelectedTablesArrList.toString() + "\n");

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
            if (!getCheckSelectedStatusByOtherStations(finalTableidx)) {
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

            mSelectedTablesArrList.remove(finalTableidx);
            mSelectedTablesArrList.add(finalTableidx);
        }

        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            mSelectedTablesArrList.remove(finalTableidx);
            mSelectedTablesArrList.add(finalTableidx);
        }

        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "선택된 값 ===============================" + "\n");
        for (String tempidx : mSelectedTablesArrList) {
            GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "값 : " + tempidx + "\n");
        }
        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "=========================================" + "\n");

        if (waitDouble == true) {
            // single click
            waitDouble = false;
            doubleClick_finalTableidx = finalTableidx;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(DOUBLE_CLICK_TIME);
                        if (waitDouble == false) {
                            waitDouble = true;
                            //single click event

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        } else {
            waitDouble = true;

            if (doubleClick_finalTableidx != finalTableidx){
                return;
            }

            //double click event
            int mSubTableCount = getTableSplitCount(finalTableidx);

            if (mSubTableCount > 0 && mSubTableCount > 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
                builder.setTitle("merge split?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                            setTwoSplit(finalTableidx);
                        setTableMerge(finalTableidx);
                        //viewTableSettigns(mSelectedZoneIdx);
                        setInitValues();
                        // 클라우드 업로드
                        uploadTableDataCloudExe();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                            setClearSelectedTableIdx(true);
                        }
                    }
                });
                builder.create();
                builder.show();
                setInitValues();
                return;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
                builder.setTitle("Table Split");
                builder.setTitle("Would you like to split this table into two?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTwoSplit(finalTableidx);
                        //viewTableSettigns(mSelectedZoneIdx);
                        setInitValues();
                        // 클라우드 업로드
                        uploadTableDataCloudExe();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                            setClearSelectedTableIdx(true);
                        }
                    }
                });
                builder.create();
                builder.show();
            }
        }

        doCellSelectEvent();

        // 우측 퀵메뉴가 보여지지 않는 상태일때는 세일 화면으로 바로 이동한다.
        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            //10292024 show dual display for dine in also
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                PaxPresentation.unSetLogo();
                MainActivity.updatePresentation();
            }
            goSalesMain();

        }
    }

    public void tableCell_longClick(View v) {
//                            ClipData clip = ClipData.newPlainText(finalTablename, finalTablename);
//                            //드래그할 데이터, 섀도우 지정, 드래그 앤 드롭 관련 데이터를 가지는 객체 지정, 0
//
//                            v.startDrag(clip, new View.DragShadowBuilder(v), v, 0);

        String touchup_table_idx = (String)v.getTag();
//                            String drop_target_table_idx = (String)table_row.getTag();

        setClearSelectedTableIdx(true);
        mSelectedTablesArrList.add(touchup_table_idx);
//                            mSelectedTablesArrList.add(drop_target_table_idx);


        final List<String> ListItems = new ArrayList<>();

        ListItems.add("Order / Payment");
        ListItems.add("Bill Print");
        ListItems.add("Bill Split/Merge/Print");
        ListItems.add("Detail Information");
        if (table_main_grid_relative_view.getVisibility() == View.VISIBLE){

        } else{
            ListItems.add("Table Split");
        }
        if (GlobalMemberValues.isMobileTableOrder()){
            if (GlobalMemberValues.getMobileTableOrderType().equals("B")){
                ListItems.add("QR Code Order Printing (Fixed)");
            } else {
                ListItems.add("QR Code Order Printing");
            }
            ListItems.add("QR Code Order On");
            ListItems.add("QR Code Order Off");
        } else {

        }
//        ListItems.add("Kitchen Print");
//        ListItems.add("Table Clear");
        ListItems.add("Close");





        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
        builder.setTitle(touchup_table_idx);

        // 커스텀 어댑터 사용
        Table_list_popup_adapter adapter = new Table_list_popup_adapter(TableSaleMain.this, ListItems, touchup_table_idx);
        builder.setAdapter(adapter, (dialog, which) -> {
            // 항목 클릭 시 동작

        });

        builder.setCancelable(false);

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setClearSelectedTableIdx(true);
            }
        });

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                //Toast.makeText(TableSaleMain.this, selectedText, Toast.LENGTH_SHORT).show();

                switch (selectedText){
                    case "Order / Payment" :
                        startOrderPayment(touchup_table_idx);
                        break;
                    case "Bill Print" :
                        doBillPrint();
                        break;
                    case "Bill Split/Merge/Print" :
                        if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<18>")){
                            GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                            return;
                        }
//                        doBillSplitMerge();
                        openBillSplitMerge();

                        break;
                    case "Detail Information" :

                        doOpenDetailInfo();
                        break;
                    case "Table Split" :
                        // split
                        doTableSplit();


                        break;
                    case "Kitchen Print" :

//                        // 키친프린팅 실행
//                        doKitchenReprint(true);

                        break;
//                    case "Table Clear" :
//                        GlobalMemberValues.logWrite("jjjclearlog", "touchup_table_idx : " + touchup_table_idx + "\n");
//
//                        setClearSelectedTableIdx(true);
//                        mSelectedTablesArrList.add(touchup_table_idx);
//
//                        GlobalMemberValues.logWrite("jjjclearlog", "mSelectedTablesArrList1 : " + mSelectedTablesArrList.toString() + "\n");
//
//                        doTableClear(mSelectedTablesArrList);
//
////                                            setInitValues();
//                        break;
                    case "QR Code Order Printing (Fixed)":
                    case "QR Code Order Printing":
                        //Print
                        GlobalMemberValues.printQRCode(mContext,touchup_table_idx);
                        break;
                    case "QR Code Order On":
                        if (GlobalMemberValues.isChangedQRCodeOrderStatus(touchup_table_idx, "Y")){
                            GlobalMemberValues.displayDialog(mContext, "QR Code","Changed QR Code order status to On", "Close");
                        } else {
                            GlobalMemberValues.displayDialog(mContext, "QR Code","Failed to change QR Code order status to On", "Close");
                        }
                        break;
                    case "QR Code Order Off" :
                        if (GlobalMemberValues.isChangedQRCodeOrderStatus(touchup_table_idx, "N")){
                            GlobalMemberValues.displayDialog(mContext, "QR Code","Changed QR Code order status to Off", "Close");
                        } else {
                            GlobalMemberValues.displayDialog(mContext, "QR Code", "Failed to change QR Code order status to Off", "Close");
                        }
                        break;
                    case "Close" :
                        setClearSelectedTableIdx(true);
                        break;
                }
            }
        });
        builder.show();
    }


    public static void startOrderPayment(String touchup_table_idx){
        setClearSelectedTableIdx(true);
        mSelectedTablesArrList.add(touchup_table_idx);

        int selectedTableCnt = mSelectedTablesArrList.size();
        if (selectedTableCnt > 0) {
            GlobalMemberValues.mSelectedTableIdx = TableSaleMain.mSelectedTablesArrList.get(0);
            GlobalMemberValues.logWrite("selectedtableidxlog", "tableidx : " + GlobalMemberValues.mSelectedTableIdx + "\n");

            if (selectedTableCnt > 1) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select only one table", "Close");
                mAllTablesArrList.clear();

                setInitValues();
                setInitValuesSub();
            } else {
                // 테이블이 split 되어 있는지 확인
                int mSubTableCount = getTableSplitCount(mSelectedTablesArrList.get(0));

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // 테이블이 split 되어 있을 경우
                    openSplittedTableList("order");
                } else {
                    GlobalMemberValues.logWrite("jjjcustjjjlog", "startOrderPayment 에서 시작" + "\n");
                    setOrderStart(mSelectedTablesArrList, false, false);
                }
            }

        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Pleas select a table", "Close");
        }
    }

    public static boolean getCheckSelectedStatusByOtherStations(String paramTableIdx) {
        boolean returnValue = false;

        String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -1);
        String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 2);

        String strQuery = " select count(*) from salon_selectedtableidx_bystation " +
                " where tableidx like '" + paramTableIdx + "' " +
                " and wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "' " +
                " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
        int tempCount = GlobalMemberValues.getIntAtString(
                MssqlDatabase.getResultSetValueToString(strQuery)
        );

        if (tempCount > 0) {
            returnValue = false;
        } else {
            returnValue = true;
        }

        return returnValue;
    }

    public static void  doCellSelectEvent() {
        Vector<String> insUpDelVec = new Vector<String>();
        String strQuery = "";

        strQuery = " delete from salon_selectedtableidx_bystation where stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
        insUpDelVec.addElement(strQuery);

        GlobalMemberValues.logWrite("kimwanhayejjjlog", "여기 : " + mSelectedTablesArrList.toString() + "\n");

        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            for (String tempTableidx : mSelectedTablesArrList) {
                strQuery = " insert into salon_selectedtableidx_bystation (stcode, tableidx) values ( " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + tempTableidx + "' " +
                        " )";
                insUpDelVec.addElement(strQuery);
            }
        }

        if (insUpDelVec != null && insUpDelVec.size() > 0) {
            for (String tempQuery : insUpDelVec) {
                GlobalMemberValues.logWrite("jjjinssqllog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(insUpDelVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                return;
            } else {
            }
        }
    }

    // Quick 주문 테이블
    public void viewTableSettingsTypeForQuick(String[] paramTableInfo) {
        if (paramTableInfo == null) return;
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

        for (int i = 0; i < paramTableInfo.length; i++) {
            tablearr = paramTableInfo[i].split("-JJJ-");

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


            double xvaluerate_dbl = GlobalMemberValues.getDoubleAtString(xvaluerate);
            double yvaluerate_dbl = GlobalMemberValues.getDoubleAtString(yvaluerate);

            double xvalue = 0;
            if (xvaluerate_dbl > 0) {
                xvalue = main_100_width * xvaluerate_dbl * 0.01;
            }

            double yvalue = 0;
            if (yvaluerate_dbl > 0) {
                yvalue = main_100_height * yvaluerate_dbl * 0.01;
            }


            // 테이블 만들기 ------------------------------------------------------------------------------------------------------------
            LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            final LinearLayout table_row3 = (LinearLayout)mInflater.inflate(R.layout.table_main_table_5, table_main_view, false);

            table_row3.setX((float)xvalue);
            table_row3.setY((float)yvalue);

            ImageView table_icon_iv = table_row3.findViewById(R.id.table_main_table_image);
            table_icon_iv.setImageResource(R.drawable.ic_tables_rectangle);

            int lnWidth = 180;
            int lnHeight = 200;

            lnWidth = main_100_width / 13;
            lnHeight = main_100_height / 8;

            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams tableLnParams
                    = new LinearLayout.LayoutParams(lnWidth, lnHeight);
            LinearLayout toptableLn = table_row3.findViewById(R.id.toptableLn);
            toptableLn.setLayoutParams(tableLnParams);

            LinearLayout parentTableLn = table_row3.findViewById(R.id.parentTableLn);
            parentTableLn.setBackgroundColor(Color.parseColor(colorvalue));

            LinearLayout tableLn = table_row3.findViewById(R.id.tableLn);
            //tableLn.setBackgroundColor(Color.parseColor(colorvalue));

            TextView table_row3_name = table_row3.findViewById(R.id.main_table_row_title);
            table_row3_name.setTextSize(f_globalFontSize + 20 * f_fontsize_forPAX);
            table_row3_name.setText(tablename);

            TextView table_main_bill_count = table_row3.findViewById(R.id.table_main_bill_count);
            table_main_bill_count.setTextSize(f_globalFontSize + 14 * f_fontsize_forPAX);
            table_main_bill_count.setText("");

            TextView totalordersTv = table_row3.findViewById(R.id.totalordersTv);
            totalordersTv.setTextSize(f_globalFontSize + 16 * f_fontsize_forPAX);
            //totalordersTv.setText("");

            tableidx =
                    "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
            mAllTablesArrList.add(tableidx);

            // 테이블이 split 되어 있는지 확인
            int subTableCount = getTableSplitCount(tableidx);

            if (subTableCount > 0 && subTableCount > 1) {
                String tempSubTableStr = subTableCount + "ea";
                tablename = tempSubTableStr + " Split";
//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                table_row3_name.setText(tablename);
            }

            String tableTxtColor = "#ffffff";

            // 해당 테이블 주문메뉴 수
//            tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' ");
            tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeByTableidx(tableidx, mSubTableNum) + "' ");
            int tableordercnt_int = GlobalMemberValues.getIntAtString(tableordercnt);

            GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

            //
            if (tableordercnt_int > 0){
                parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5_2);
            } else {
                parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5);
            }

            final String finalTablename = tablename;
            final String finalTableidx = tableidx;
            final String finalTableTxtColor = tableTxtColor;
            table_row3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogsSave.saveLogsInDB(232);
                    BillSplitMerge.setInitValuesForBillPay();

                    tableCell_singleClick(view, parentTableLn, table_row3, finalTableidx,
                            finalTablename, finalTableTxtColor, tableordercnt_int, "B");
                }
            });

            // table double touch


            // table longclick
            table_row3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tableCell_longClick(v);
                    return false;
                }
            });
            table_row3.setTag(tableidx);

            if (table_main_view != null && table_row3 != null) {
                table_main_view.addView(table_row3);
            }
            // ------------------------------------------------------------------------------------------------------------------
        }
    }


    public void viewTableSettingsTypeA(String[] paramTableInfo) {
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
        String boxtype = "";
        String boxkinds = "";

        for (int i = 0; i < paramTableInfo.length; i++) {
            tablearr = paramTableInfo[i].split("-JJJ-");

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


            double xvaluerate_dbl = GlobalMemberValues.getDoubleAtString(xvaluerate);
            double yvaluerate_dbl = GlobalMemberValues.getDoubleAtString(yvaluerate);

            double xvalue = 0;
            if (xvaluerate_dbl > 0) {
                xvalue = main_100_width * xvaluerate_dbl * 0.01;
            }

            double yvalue = 0;
            if (yvaluerate_dbl > 0) {
                yvalue = main_100_height * yvaluerate_dbl * 0.01;
            }

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvaluerate : " + xvaluerate + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvaluerate : " + yvaluerate + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_width : " + main_100_width + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_height : " + main_100_height + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvalue : " + xvalue + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvalue : " + yvalue + "\n\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n\n\n\n");

            if (boxkinds.equals("table")){
                // 테이블 만들기 ------------------------------------------------------------------------------------------------------------
                LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                final LinearLayout table_row3 = (LinearLayout)mInflater.inflate(R.layout.table_main_table_2, table_main_view, false);

                table_row3.setX((float)xvalue);
                table_row3.setY((float)yvalue);

                ImageView table_icon_iv = table_row3.findViewById(R.id.table_main_table_image);
                table_icon_iv.setImageResource(R.drawable.ic_tables_rectangle);
//            switch (tabletype) {
//                case "circle" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_circle);
//                    break;
//                }
//                case "hexagon" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_hexagon);
//                    break;
//                }
//                case "rectangle" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_rectangle);
//                    break;
//                }
//                case "rhombus" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_rhombus);
//                    break;
//                }
//                case "round" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_round);
//                    break;
//                }
//                case "square" : {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_square);
//                    break;
//                }
//                default: {
//                    table_icon_iv.setImageResource(R.drawable.ic_tables_circle);
//                    break;
//                }
//            }

                int lnWidth = 180;
                int lnHeight = 200;

                lnWidth = main_100_width / 13;
                lnHeight = main_100_height / 8;

                // LinearLayout 파라미터 설정
                LinearLayout.LayoutParams tableLnParams
                        = new LinearLayout.LayoutParams(lnWidth, lnHeight);
                LinearLayout toptableLn = table_row3.findViewById(R.id.toptableLn);
                toptableLn.setLayoutParams(tableLnParams);

                LinearLayout tableLn = table_row3.findViewById(R.id.tableLn);
                tableLn.setBackgroundColor(Color.parseColor(colorvalue));

                TextView table_row3_name = table_row3.findViewById(R.id.main_table_row_title);
                table_row3_name.setTextSize(f_globalFontSize + 18 * f_fontsize_forPAX);
                table_row3_name.setText(tablename);

                TextView table_main_bill_count = table_row3.findViewById(R.id.table_main_bill_count);
                table_main_bill_count.setTextSize(f_globalFontSize + 14 * f_fontsize_forPAX);
                table_main_bill_count.setText("");

                TextView totalordersTv = table_row3.findViewById(R.id.totalordersTv);
                totalordersTv.setTextSize(f_globalFontSize + 16 * f_fontsize_forPAX);
                //totalordersTv.setText("");

                tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
                mAllTablesArrList.add(tableidx);

                // 테이블이 split 되어 있는지 확인
                int subTableCount = getTableSplitCount(tableidx);

                if (subTableCount > 0 && subTableCount > 1) {
                    String tempSubTableStr = subTableCount + "ea";
                    for (int jjj = 0; jjj < subTableCount; jjj++) {
                        tempSubTableStr += " " + (jjj + 1);
                    }

                    String splited_new_name = "S1";
                    for (int q = 2 ; subTableCount >= q ; q++){
                        splited_new_name += "|S" + String.valueOf(q);
                    }
//                    table_main_bill_count.setText("Split : " + tempSubTableStr);
                    table_main_bill_count.setText(splited_new_name);
                }

                String tableTxtColor = "#ffffff";
                // mergednum -------------------------------------------------------------------------
                int mergednum = GlobalMemberValues.getIntAtString(
                        MssqlDatabase.getResultSetValueToString("select mergednum from temp_salecart where tableidx like '%" + tableidx + "%' ")
                );
                if (mergednum > 0) {
                    if (getTableCountByHoldcode(getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum)) == 1
                            && getTableCountByTableidxInMergedTable(tableidx) < 2) {
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
                        tableTxtColor = "#e8e215";

                        String mergednumstr = "0" + mergednum;
                        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                        tablename = "Merged\n(" + mergednumstr + ")";

                        table_row3_name.setText(tablename);
                        table_row3_name.setTextColor(Color.parseColor(tableTxtColor));

                        int tablecolornum = mergednum % 7;

                        table_main_bill_count.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                        table_main_bill_count.setText("   Merged Table   ");
                        table_main_bill_count.setTextColor(Color.parseColor("#ffffff"));
                    }
                } else {
                    if (getTableCountByTableidxInMergedTable(tableidx) > 1) {
                        String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(tableidx);
                        if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {


                            tableTxtColor = "#e8e215";

                            mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(tableidx);

                            String mergednumstr = "0" + mergednum;
                            mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                            tablename = "Merged\n(" + mergednumstr + ")";

                            table_row3_name.setText(tablename);
                            table_row3_name.setTextColor(Color.parseColor(tableTxtColor));

                            int tablecolornum = mergednum % 7;

                            table_main_bill_count.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                            table_main_bill_count.setText("   Merged Table   ");
                            table_main_bill_count.setTextColor(Color.parseColor("#ffffff"));
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

                // -----------------------------------------------------------------------------------


                if (boxkinds.equals("table")) {
                    LinearLayout parentTableLn = table_row3.findViewById(R.id.parentTableLn);

                    final String finalTablename = tablename;
                    final String finalTableidx = tableidx;
                    final String finalTableTxtColor = tableTxtColor;
                    table_row3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogsSave.saveLogsInDB(232);
                            BillSplitMerge.setInitValuesForBillPay();

                            tableCell_singleClick(view, parentTableLn, table_row3, finalTableidx,
                                    finalTablename, finalTableTxtColor, -1, "A");

                            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                                //jihun park sub display
                                //10292024 don't hide dual display when going into dine in main middle service
                                //PaxPresentation.setLogo();
                                //MainActivity.updatePresentation();
                            }

                        }
                    });
                    // table double touch

                    // table longclick
                    table_row3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            tableCell_longClick(v);

                            return false;
                        }
                    });
                    table_row3.setTag(tableidx);
                }

                String getHoldCodeTemp = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

                // 주문금액관련 -----------------------------------------------------------------------------------
                double mTableAmount = 0.0;

                TextView amountTv = table_row3.findViewById(R.id.table_main_table_totalprice);
                amountTv.setTextSize(f_globalFontSize + 20 * f_fontsize_forPAX);

                int mSubTableCount = getTableSplitCount(tableidx);

                if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
                    for (int wj = 0; wj < mSubTableCount; wj++) {
                        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                                " select holdcode from salon_store_restaurant_table_split " +
                                        " where tableidx like '%" + tableidx + "%' and subtablenum = '" + (wj + 1) + "' "
                        );

                        if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                            mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                    " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
                            ));
                        }
                    }
                } else {                                                 // Table split 이 아닌 경우
                    if (!GlobalMemberValues.isStrEmpty(getHoldCodeTemp)) {
                        mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select sum(sTotalAmount) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' "
                        ));
                    }
                }


                amountTv.setText("$" + GlobalMemberValues.getCommaStringForDouble(mTableAmount + ""));
                // ------------------------------------------------------------------------------------------------

                // Capacity ---------------------------------------------------------------------------------------
                TextView capacityTv = table_row3.findViewById(R.id.capacityTv);
                capacityTv.setTextSize(f_globalFontSize + 14 * f_fontsize_forPAX);
                capacityTv.setText("");
                int peopleCnt = getTablePeopleCntByTableIdx(tableidx);
                String capacityStr = "Capacity " + peopleCnt + " / " + capacity;
                capacityTv.setText(capacityStr);
                // -------------------------------------------------------------------------------------------------

                // Ordered Date ---------------------------------------------------------------------------------------
                TextView ordereddate = table_row3.findViewById(R.id.ordereddate);

                String strQuery_temp = " select top 1 wdate from temp_salecart " +
                        " where holdcode = '" + getHoldCodeTemp + "' order by wdate asc";

                ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
                String ordereddateValue = "";
                try {
                    while (timeCursor.next()){
                        String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                        ordereddateValue = getTime;


                    }
                    timeCursor.close();
                } catch (Exception e) {
                }


                String date1 = ordereddateValue;

                if (GlobalMemberValues.isTimeDisplayonTable()){
                    ordereddate = timeupdate_in_textview(ordereddate,date1);
                }



//                if (!GlobalMemberValues.isStrEmpty(ordereddateValue)) {
//                    ordereddate.setText(ordereddateValue);
//                } else {
//                    LinearLayout ordereddateLn = table_row3.findViewById(R.id.ordereddateLn);
//                    ordereddateLn.setVisibility(View.INVISIBLE);
//                }

//                int tableordercnt_int = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' "));
                int tableordercnt_int = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        "select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' "));

                if (tableordercnt_int > 0) {
                    tableLn.setBackgroundColor(Color.parseColor("#214283"));
                } else {
                    tableLn.setBackgroundColor(Color.parseColor("#7D6C3A"));
                }

                // -------------------------------------------------------------------------------------------------

                if (table_main_view != null && table_row3 != null) {
                    table_main_view.addView(table_row3);
                }
                // ------------------------------------------------------------------------------------------------------------------
            } else {
                setTableView_etc_Block_make(boxkinds, boxtype, xvalue, yvalue);
            }

        }
    }

    public void viewTableSettingsTypeB(String[] paramTableInfo) {
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

        // 01112023
        String size = "";

        GlobalMemberValues.logWrite("zonejjjlog", "여기실행됨...JJ" + "\n");

        for (int i = 0; i < paramTableInfo.length; i++) {
            tablearr = paramTableInfo[i].split("-JJJ-");

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

            // 01112023
            size = tablearr[13];
            if (GlobalMemberValues.isStrEmpty(size)) {
                size = "M";
            }

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


            double xvaluerate_dbl = GlobalMemberValues.getDoubleAtString(xvaluerate);
            double yvaluerate_dbl = GlobalMemberValues.getDoubleAtString(yvaluerate);

            double xvalue = 0;
            if (xvaluerate_dbl > 0) {
                xvalue = main_100_width * xvaluerate_dbl * 0.01;
            }

            double yvalue = 0;
            if (yvaluerate_dbl > 0) {
                yvalue = main_100_height * yvaluerate_dbl * 0.01;
            }

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvaluerate : " + xvaluerate + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvaluerate : " + yvaluerate + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_width : " + main_100_width + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_height : " + main_100_height + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvalue : " + xvalue + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvalue : " + yvalue + "\n\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n\n\n\n");


            if (boxkinds.equals("table")) {
                // 테이블 만들기 ------------------------------------------------------------------------------------------------------------
                LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                final LinearLayout table_row3 = (LinearLayout)mInflater.inflate(R.layout.table_main_table_5, table_main_view, false);

                table_row3.setX((float)xvalue);
                table_row3.setY((float)yvalue);

                ImageView table_icon_iv = table_row3.findViewById(R.id.table_main_table_image);
                table_icon_iv.setImageResource(R.drawable.ic_tables_rectangle);

                int lnWidth = 180;
                int lnHeight = 200;


                lnWidth = main_100_width / 13;
                lnHeight = main_100_height / 8;

                switch (size){
                    case "S" :
                        lnWidth = (int) (((double)lnWidth / 100.00 ) * (double)80);
                        lnHeight = (int) (((double)lnHeight / 100.00 ) * (double)80);
                        break;
                    case "M" :
                        break;
                    case "L" :
                        lnWidth = (int) (((double)lnWidth / 100.00 ) * (double)120);
                        lnHeight = (int) (((double)lnHeight / 100.00 ) * (double)120);
                        break;
                }

                // LinearLayout 파라미터 설정
                LinearLayout.LayoutParams tableLnParams
                        = new LinearLayout.LayoutParams(lnWidth, lnHeight);
                LinearLayout toptableLn = table_row3.findViewById(R.id.toptableLn);
                toptableLn.setLayoutParams(tableLnParams);

                LinearLayout parentTableLn = table_row3.findViewById(R.id.parentTableLn);
                parentTableLn.setBackgroundColor(Color.parseColor(colorvalue));

                LinearLayout tableLn = table_row3.findViewById(R.id.tableLn);
                //tableLn.setBackgroundColor(Color.parseColor(colorvalue));

                TextView table_row3_name = table_row3.findViewById(R.id.main_table_row_title);
                float i_textSize = f_globalFontSize + 30 * f_fontsize_forPAX;
                // 230112
                switch (size){
                    case "S" :
                        i_textSize = (float) (((double)i_textSize / 100.00 ) * (double)80);
                        break;
                    case "M" :
                        break;
                    case "L" :
                        i_textSize = (float) (((double)i_textSize / 100.00 ) * (double)120);
                        break;
                }
                // 230112
                table_row3_name.setTextSize(i_textSize);
                if (GlobalMemberValues.isDeviceSunmiFromDB()){
                    i_textSize = 25;
                    switch (size){
                        case "S" :
                            i_textSize = (float) (((double)i_textSize / 100.00 ) * (double)60);
                            break;
                        case "M" :
                            break;
                        case "L" :
                            i_textSize = (float) (((double)i_textSize / 100.00 ) * (double)100);
                            break;
                    }
                    table_row3_name.setTextSize(i_textSize);
                }

                if (GlobalMemberValues.isDevicePAXFromDB()){
                    table_row3_name.setTextSize(i_textSize);
                }


                table_row3_name.setText(tablename);

                TextView table_main_bill_count = table_row3.findViewById(R.id.table_main_bill_count);
                table_main_bill_count.setTextSize(f_globalFontSize + 14 * f_fontsize_forPAX);
                table_main_bill_count.setText("");

                TextView totalordersTv = table_row3.findViewById(R.id.totalordersTv);
                totalordersTv.setTextSize(f_globalFontSize + 16 * f_fontsize_forPAX);
                //totalordersTv.setText("");

                tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
                mAllTablesArrList.add(tableidx);

                // 테이블이 split 되어 있는지 확인
                int subTableCount = getTableSplitCount(tableidx);

                if (subTableCount > 0 && subTableCount > 1) {
                    String tempSubTableStr = subTableCount + "ea";
//                    tablename = tempSubTableStr + " Split";

                    tablename = "S1";
                    for (int q = 2 ; subTableCount >= q ; q++){
                        tablename += "|S" + String.valueOf(q);
                    }

//                for (int jjj = 0; jjj < subTableCount; jjj++) {
//                    tempSubTableStr += " " + (jjj + 1);
//                }

                    table_row3_name.setText(tablename);
                    table_row3_name.setTextSize(f_globalFontSize + 26 * f_fontsize_forPAX);
                }

                String getHoldCodeTemp = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);

                int mergednum = 0;

                String strQuery_temp = " select top 1 wdate, mergednum from temp_salecart " +
                        " where holdcode = '" + getHoldCodeTemp + "' order by wdate asc";
                ResultSet timeCursor = MssqlDatabase.getResultSetValue(strQuery_temp);
                String ordereddateValue = "";
                boolean isOrderInTable = false;
                try {
                    while (timeCursor.next()){
                        String getTime = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,0), 1);
                        ordereddateValue = getTime;

                        mergednum = GlobalMemberValues.getIntAtString(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(timeCursor,1), 1));

                        isOrderInTable = true;
                    }
                    timeCursor.close();
                } catch (Exception e) {
                }
                TextView orderTime_tv = table_row3.findViewById(R.id.ordereddate);
                String date1 = ordereddateValue;

                if (GlobalMemberValues.isTimeDisplayonTable() && !date1.equals("")){
                    orderTime_tv = timeupdate_in_textview(orderTime_tv,date1);
                }


                String tableTxtColor = "#ffffff";

                // mergednum -------------------------------------------------------------------------

                if (mergednum > 0) {
                    if (getTableCountByHoldcode(getHoldCodeTemp) == 1
                            && getTableCountByTableidxInMergedTable(tableidx) < 2) {
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
                        tableTxtColor = "#e8e215";

                        String mergednumstr = "0" + mergednum;
                        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                        tablename = "Merged\n(" + mergednumstr + ")";

                        table_row3_name.setText(tablename);
                        table_row3_name.setTextColor(Color.parseColor(tableTxtColor));
                        table_row3_name.setTextSize(f_globalFontSize + 26 * f_fontsize_forPAX);

                        int tablecolornum = mergednum % 7;

                        table_main_bill_count.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                        table_main_bill_count.setText("   Merged Table   ");
                        table_main_bill_count.setTextColor(Color.parseColor("#ffffff"));
                    }
                } else {
                    if (getTableCountByTableidxInMergedTable(tableidx) > 1) {
                        String tempHoldCode = TableSaleMain.getHoldCodeByTableidxInMergedTable(tableidx);
                        if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {


                            tableTxtColor = "#e8e215";

                            mergednum = TableSaleMain.getMergedNumByTableidxInMergedTable(tableidx);

                            String mergednumstr = "0" + mergednum;
                            mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                            tablename = "Merged\n(" + mergednumstr + ")";

                            table_row3_name.setText(tablename);
                            table_row3_name.setTextColor(Color.parseColor(tableTxtColor));
                            table_row3_name.setTextSize(f_globalFontSize + 26 * f_fontsize_forPAX);

                            int tablecolornum = mergednum % 7;

                            table_main_bill_count.setBackgroundColor(Color.parseColor(GlobalMemberValues.MERGEDTABLECOLOR[tablecolornum]));
                            table_main_bill_count.setText("   Merged Table   ");
                            table_main_bill_count.setTextColor(Color.parseColor("#ffffff"));
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

                // -----------------------------------------------------------------------------------


                int tableordercnt_int = 0;
                if (isOrderInTable){
                    parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5_2);

                    // 해당 테이블 주문메뉴 수
//                tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where tableidx like '%" + tableidx + "%' ");
                    tableordercnt = MssqlDatabase.getResultSetValueToString("select count(*) from temp_salecart where holdcode = '" + getHoldCodeTemp + "' ");
                    tableordercnt_int = GlobalMemberValues.getIntAtString(tableordercnt);
                    GlobalMemberValues.logWrite("jjjtableinfolog", "tableordercnt : " + tableordercnt + "\n");

                    // 04222023
                    // bill 프린팅을 했는지 여부
                    if (GlobalMemberValues.getBillPrintedStatus(tableidx, mSubTableNum)) {
                        parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5_3);
                    }

                } else {
                    parentTableLn.setBackgroundResource(R.drawable.roundlayout_table5);
                }

                if (boxkinds.equals("table")) {
                    final String finalTablename = tablename;
                    final String finalTableidx = tableidx;
                    final String finalTableTxtColor = tableTxtColor;
                    int finalTableordercnt_int = tableordercnt_int;

                    // 04252023
                    boolean finalIsOrderInTable = isOrderInTable;

                    table_row3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            // 04252023
//                            // 주문이 없는 테이블인데,
//                            // 클릭할 때 주문겁색을 했더니 주문이 있는 주문을 깅여
//                            if (!finalIsOrderInTable) {
//                                String tempSql = " select count(*) from temp_salecart " +
//                                        " where holdcode = '" + getHoldCodeTemp + "' ";
//                                int orderedCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempSql));
//
//                                if (orderedCnt > 0) {
//                                    GlobalMemberValues.displayDialog(mContext, "Warning",
//                                            "This table is already in progress\nPlease select another table", "Close");
////                                    viewTableSettigns(mSelectedZoneIdx);
//                                    return;
//                                }
//                            }


                            if (!GlobalMemberValues.isShowQuickMenusInTableBoard){
                                if(SystemClock.elapsedRealtime() - mLastClickTime < 700) {
                                    return;
                                }
                                mLastClickTime = SystemClock.elapsedRealtime();
                            }

                            if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<16>")){
                                GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                                return;
                            }
                            LogsSave.saveLogsInDB(232);
                            BillSplitMerge.setInitValuesForBillPay();

                            tableCell_singleClick(view, parentTableLn, table_row3, finalTableidx,
                                    finalTablename, finalTableTxtColor, finalTableordercnt_int, "B");
                            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                                //jihun park sub display
                                //10292024 don't hide dual display when going into dine in main middle service
                                //PaxPresentation.setLogo();
                                //MainActivity.updatePresentation();
                            }
                        }
                    });

                    // table double touch


                    // table longclick
                    table_row3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<16>")){
                                GlobalMemberValues.displayDialog(TableSaleMain.mContext, "Warning", "You do not have permission", "Close");
                                return false;
                            }
                            tableCell_longClick(v);
                            return false;
                        }
                    });
                    table_row3.setTag(tableidx);
                }

                if (table_main_view != null && table_row3 != null) {
                    table_main_view.addView(table_row3);
                }
                // ------------------------------------------------------------------------------------------------------------------
            } else {
                setTableView_etc_Block_make(boxkinds, boxtype, xvalue, yvalue);
            }
        }
    }

    public void viewTableSettingsTypeC(String[] paramTableInfo) {
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
        String boxtype = "";
        String boxkinds = "";

        for (int i = 0; i < paramTableInfo.length; i++) {
            tablearr = paramTableInfo[i].split("-JJJ-");

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

            double xvaluerate_dbl = GlobalMemberValues.getDoubleAtString(xvaluerate);
            double yvaluerate_dbl = GlobalMemberValues.getDoubleAtString(yvaluerate);

            double xvalue = 0;
            if (xvaluerate_dbl > 0) {
                xvalue = main_100_width * xvaluerate_dbl * 0.01;
            }

            double yvalue = 0;
            if (yvaluerate_dbl > 0) {
                yvalue = main_100_height * yvaluerate_dbl * 0.01;
            }

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvaluerate : " + xvaluerate + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvaluerate : " + yvaluerate + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_width : " + main_100_width + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "main_100_height : " + main_100_height + "\n");

            GlobalMemberValues.logWrite("xyvaluejjj1", "xvalue : " + xvalue + "\n");
            GlobalMemberValues.logWrite("xyvaluejjj1", "yvalue : " + yvalue + "\n\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n\n\n\n");



            if (boxkinds.equals("table")){
                // 테이블 만들기 ------------------------------------------------------------------------------------------------------------
                LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                final LinearLayout table_row3 = (LinearLayout)mInflater.inflate(R.layout.table_main_table_1, table_main_view, false);

                table_row3.setX((float)xvalue);
                table_row3.setY((float)yvalue);

                ImageView table_icon_iv = table_row3.findViewById(R.id.tableimagetype1);
                table_icon_iv.setImageResource(R.drawable.ic_tables_rectangle);

                int lnWidth = 180;
                int lnHeight = 200;

                lnWidth = main_100_width / 13;
                lnHeight = main_100_height / 8;

                // LinearLayout 파라미터 설정
                LinearLayout.LayoutParams tableLnParams
                        = new LinearLayout.LayoutParams(lnWidth, lnHeight);
                RelativeLayout toptableRt = table_row3.findViewById(R.id.toptableRt);
                toptableRt.setLayoutParams(tableLnParams);

                TextView main_table_row_title = table_row3.findViewById(R.id.main_table_row_title);
                main_table_row_title.setText(tablename);

                tableidx = "T" + GlobalMemberValues.getTableIdxWithoutStringT(tableidx) + "T";
                mAllTablesArrList.add(tableidx);

                // 테이블이 split 되어 있는지 확인
                int subTableCount = getTableSplitCount(tableidx);

                if (subTableCount > 0 && subTableCount > 1) {
                    String tempSubTableStr = subTableCount + "ea";
                    for (int jjj = 0; jjj < subTableCount; jjj++) {
                        tempSubTableStr += " " + (jjj + 1);
                    }
                    //table_main_bill_count.setText("Split : " + tempSubTableStr);
                }

                String tableTxtColor = "#ffffff";


                final String finalTablename = tablename;
                final String finalTableidx = tableidx;
                final String finalTableTxtColor = tableTxtColor;
                table_row3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogsSave.saveLogsInDB(232);
                        TextView title = view.findViewById(R.id.main_table_row_title);
                        title.setTextColor(Color.parseColor(finalTableTxtColor));
                        mSelectedTablesArrList.remove(finalTableidx);
                        title.setText(finalTablename);

                        if (table_row3.isSelected()){
                            table_row3.setSelected(false);
                            toptableRt.setBackgroundResource(R.drawable.roundlayout_table);
                        } else {
                            table_row3.setSelected(true);
                            toptableRt.setBackgroundResource(R.drawable.roundlayout_table_selected);

                            title.setText("Sel.");
                            title.setTextColor(Color.parseColor("#ffffff"));

                            mSelectedTablesArrList.remove(finalTableidx);
                            mSelectedTablesArrList.add(finalTableidx);
                            //Toast.makeText(getApplicationContext(), "Table " + finalI + " select!", Toast.LENGTH_SHORT).show();
                        }

                        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "선택된 값 ===============================" + "\n");
                        for (String tempidx : mSelectedTablesArrList) {
                            GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "값 : " + tempidx + "\n");
                        }
                        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "=========================================" + "\n");


                        if (waitDouble == true) {
                            // single click
                            waitDouble = false;
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sleep(DOUBLE_CLICK_TIME);
                                        if (waitDouble == false) {
                                            waitDouble = true;
                                            //single click event

                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            thread.start();
                        } else {
                            waitDouble = true;
                            //double click event
                            int mSubTableCount = getTableSplitCount(finalTableidx);

                            if (mSubTableCount > 0 && mSubTableCount > 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
                                builder.setTitle("merge split?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                            setTwoSplit(finalTableidx);
                                        setTableMerge(finalTableidx);
                                        //viewTableSettigns(mSelectedZoneIdx);
                                        setInitValues();
                                        // 클라우드 업로드
                                        uploadTableDataCloudExe();
                                    }
                                });
                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                            setClearSelectedTableIdx(true);
                                        }
                                    }
                                });
                                builder.create();
                                builder.show();
                                setInitValues();
                                return;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
                                builder.setTitle("Table Split");
                                builder.setTitle("Would you like to split this table into two?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setTwoSplit(finalTableidx);
                                        //viewTableSettigns(mSelectedZoneIdx);
                                        setInitValues();
                                        // 클라우드 업로드
                                        uploadTableDataCloudExe();
                                    }
                                });
                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                            setClearSelectedTableIdx(true);
                                        }
                                    }
                                });
                                builder.create();
                                builder.show();
                            }
                        }

                    }
                });

                // table double touch


                // table longclick
                table_row3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        tableCell_longClick(v);

                        return false;
                    }
                });
//                    table_row3.setOnDragListener(mDragListener);
                table_row3.setTag(tableidx);

                //TextView totalordersstrtv = table_row3.findViewById(R.id.totalordersstrtv);
                //totalordersstrtv.setTextSize(f_globalFontSize + totalordersstrtv.getTextSize() * f_fontsize_forPAX);

                if (table_main_view != null && table_row3 != null) {
                    table_main_view.addView(table_row3);
                }
                // ------------------------------------------------------------------------------------------------------------------

            } else {
                setTableView_etc_Block_make(boxkinds, boxtype, xvalue, yvalue);
            }


        }
    }


    public void setTablePanSize() {
        if (table_main_view != null){
            main_100_width = table_main_view.getWidth();
            main_100_height = table_main_view.getHeight();
        }

        parentLnWidth = 0;
        parentLnHeight = 0;

        parentLnWidth = (GlobalMemberValues.getDisplayWidth(getApplicationContext()) / 10) * 5;
        parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(getApplicationContext()) / 10) * 5;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(getApplicationContext()) / 100) * 80;
        }

        GlobalMemberValues.logWrite("xyvaluejjj2", "main_100_width : " + main_100_width + "\n");
        GlobalMemberValues.logWrite("xyvaluejjj2", "main_100_height : " + main_100_height + "\n");

        GlobalMemberValues.logWrite("xyvaluejjj2", "parentLnWidth : " + parentLnWidth + "\n");
        GlobalMemberValues.logWrite("xyvaluejjj2", "parentLnHeight : " + parentLnHeight + "\n");
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

        // 07212024 - TOrder Send Data
        GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity, "");
    }

    public void setTableSplit() {
        if (mSelectedTablesArrList.size() > 1) {
            Vector<String> updateVector = new Vector<String>();
            String strQuery = "";

            int tempNum = 0;
            for (String tempTableIdx : mSelectedTablesArrList) {
                if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                    int tempTblIdxss = GlobalMemberValues.getIntAtString(GlobalMemberValues.getReplaceText(tempTableIdx, "T", ""));

                    String newHoldCode = GlobalMemberValues.makeHoldCode() + "" + tempTblIdxss;

                    strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '0', isCloudUpload = 0  " +
                            " where tableidx like '%" + tempTableIdx + "%' ";
                    updateVector.addElement(strQuery);

                    // salon_store_restaurant_table_peoplecnt 테이블 관련 ---------------------------------------------------
                    int tempPeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                            " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where tableidx like '%" + tempTableIdx + "%' "
                    ));

                    strQuery = " delete from salon_store_restaurant_table_peoplecnt where tableidx like '%" + tempTableIdx + "%' ";
                    updateVector.addElement(strQuery);

                    strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tempTableIdx + "%' ";
                    updateVector.addElement(strQuery);

                    strQuery = " insert into salon_store_restaurant_table_peoplecnt " +
                            " (tableidx, holdcode, peoplecnt) values ( " +
                            "'" + tempTableIdx + "', " +
                            "'" + newHoldCode + "', " +
                            "'" + tempPeopleCnt + "' " +
                            " )";
                    updateVector.addElement(strQuery);

                    // ----------------------------------------------------------------------------------------------------


                    // 04252023
                    // 테이블 이동 관련정보를 새로고침을 위한 테이블에 저장
                    strQuery = " insert into salon_table_statuschange (holdcode, stcode, tableidx, delyn, ctype) values ( " +
                            " '" + newHoldCode + "', " +
                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                            " '" + tempTableIdx + "', " +
                            " '" + "N" + "', " +
                            " '" + "split" + "' " +
                            " ) ";
                    updateVector.addElement(strQuery);
                }

                tempNum++;
            }
            if (updateVector.size() > 0) {
                for (String tempQuery : updateVector) {
                    //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(updateVector);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {


                    // 05222023 -----------------------------------------------------------------------------------------------
                    for (String tempTableIdx : mSelectedTablesArrList) {
                        String tableInfos = "0" + "-JJJ-" + tempTableIdx + "-JJJ-" + mSubTableNum + "-JJJ-" + "" + "-JJJ-" + "END";
                        String tempGetHoldcode = getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = GlobalMemberValues.phoneorderPrintingJson(tempGetHoldcode, "R", tableInfos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonObject != null) {
                            // 11102023
                            if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(jsonObject.toString())) {
                                strQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                        " (salesCode, scode, sidx, stcode, jsonstr, printedyn, presalescode) values ( " +
                                        " 'K" + tempGetHoldcode + "', " +
                                        " '" + GlobalMemberValues.SALON_CODE + "', " +
                                        "  " + GlobalMemberValues.STORE_INDEX + ", " +
                                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                                        " '" + GlobalMemberValues.getDBTextAfterChecked(jsonObject.toString(), 0) + "', " +
                                        " 'Y', " +
                                        " 'splitmergedtable' " +
                                        " ) ";
                                MssqlDatabase.executeTransactionByQuery(strQuery);
                            }

                        }
                    }
                    // 05222023 -----------------------------------------------------------------------------------------------





                    //GlobalMemberValues.logWrite("mergetablelog", "mSelectedZoneIdx : " + mSelectedZoneIdx + "\n");
                    viewTableSettigns(mSelectedZoneIdx);
                    setInitValues();

                    // 클라우드 업로드
                    uploadTableDataCloudExe();
                }
            }
        } else {
            if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
                String getTableIdx = mSelectedTablesArrList.get(0);
                if (!GlobalMemberValues.isStrEmpty(getTableIdx)) {
                    int tableidxCnt = getTableCountByHoldcode(getHoldCodeByTableidx(getTableIdx, TableSaleMain.mSubTableNum));

                    if (tableidxCnt > 1) {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Please split the merged tables first", "Close");
                    } else {
                        openSplit();
                    }
                }
            }
        }
    }

    public static void setOrderOfTable(String paramHoldCode) {
        // 현재 리스트를 Hold 처리한다.
        //CommandButton.setHoldSales("");
        String temp_holdcode = paramHoldCode;
        // 06.16.2022
        MainMiddleService.mHoldCode = temp_holdcode;

//        GlobalMemberValues.logWrite("holdcodelog", " select holdcode from temp_salecart where tableidx like '%" + paramTableIdx + "%' " + "\n");
//        GlobalMemberValues.logWrite("holdcodelog", " temp_holdcode : " + temp_holdcode + "\n");

        ResultSet tempSaleCartCursor;
        String maxIdxAfterDbInsert = "";
        String strDeleteTempSaleCartQuery = "";
        String tempCustomerId = "";

        String strQuery_add = "";
        if (GlobalMemberValues.isPaymentByBills) {
            //    strQuery_add = " and idx in (" + GlobalMemberValues.mCartIdxsOnBillPay + ") ";
        }

        String strQuery = "";
        strQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE + "', " +
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
                " modifieridx, modifiercode, " +
                " memoToKitchen, " +
                " sPriceAmount, sTaxAmount, sTotalAmount, togodelitype, " +
                " selectedDcExtraType, selectedDcExtraAllEach, dcextratype, dcextravalue " +
                " from temp_salecart " +
                " where holdcode = '" + temp_holdcode + "' " + strQuery_add +
                " order by idx asc";
        GlobalMemberValues.logWrite("recallholdlog", "recallSQLQuery : " + strQuery + "\n");
        int tempCount = 0;
        tempSaleCartCursor = MssqlDatabase.getResultSetValue(strQuery);

        // 05.02.2022 ---------------------------------------------------------
        int all_dcextra_cnt = 0;
        int each_dcextra_cnt = 0;
        ArrayList<String> each_dcextra_idx_arr = new ArrayList<String>();

        boolean isDcExtra = false;
        String selectedDcExtraType = "";
        String selectedDcExtraAllEach = "";
        String dcextratype = "";        // %, $
        String dcextravalue = "";
        String dcextraidx = "";
        // ---------------------------------------------------------------------

        try {
            while (tempSaleCartCursor.next()) {
                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                //temp_holdcode = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(0), 1);
                String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                String temp_optionprice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1), "2");
                String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                String temp_additionalprice1 = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1), "2");
                String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                String temp_additionalprice2 = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1), "2");

                String temp_modifieridx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                String temp_modifiercode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);

                String temp_memotokitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);
                String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);
                String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);

                String temp_togodelitype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);

                // 05.02.2022 -----------------------------------------------------------------------------------------------------------------------------------
                String temp_selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                String temp_selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1);
                String temp_dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1);
                String temp_dcextravalue = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,48), 1);
                String temp_salecartidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,28), 1);

                if (!GlobalMemberValues.isStrEmpty(temp_dcextratype)) {
                    dcextratype = temp_dcextratype;
                }
                if (!GlobalMemberValues.isStrEmpty(temp_dcextravalue) && GlobalMemberValues.getDoubleAtString(temp_dcextravalue) > 0) {
                    dcextravalue = temp_dcextravalue;
                }
                if (!GlobalMemberValues.isStrEmpty(temp_selectedDcExtraType)) {
                    selectedDcExtraType = temp_selectedDcExtraType;
                }
                // -----------------------------------------------------------------------------------------------------------------------------------------------


                double temp_optionprice_dbl = GlobalMemberValues.getDoubleAtString(temp_optionprice);
                double temp_additionalprice1_dbl = GlobalMemberValues.getDoubleAtString(temp_additionalprice1);
                double temp_additionalprice2_dbl = GlobalMemberValues.getDoubleAtString(temp_additionalprice2);

                String temp_sPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1), "2");
                double temp_sPrice_dbl = GlobalMemberValues.getDoubleAtString(temp_sPrice);

                double temp_sPrice_final_dbl = temp_sPrice_dbl - (temp_optionprice_dbl + temp_additionalprice1_dbl + temp_additionalprice2_dbl);

                MainMiddleService.mModifierCode = temp_modifiercode;
                MainMiddleService.mModifierIdx = temp_modifieridx;
                MainMiddleService.mOptionTxt = temp_optionTxt;
                MainMiddleService.mOptionPrice = temp_optionprice;
                MainMiddleService.mAdditionalTxt1 = temp_additionalTxt1;
                MainMiddleService.mAdditionalprice1 = temp_additionalprice1;
                MainMiddleService.mAdditionalTxt2 = temp_additionalTxt2;
                MainMiddleService.mAdditionalprice2 = temp_additionalprice2;

                MainMiddleService.mMemoToKitchen = temp_memotokitchen;

                String paramsString[] = {
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,1), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,2), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,3), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,4), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,7), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,8), 1),
                        (temp_sPrice_final_dbl + ""),
                        (temp_sPrice_final_dbl + ""),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,11), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,12), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,13), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,14), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,15), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,16), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,17), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,18), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,19), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,20), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,22), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,23), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,26), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,27), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,28), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,29), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,30), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,31), 1),
                        temp_togodelitype
                };


                maxIdxAfterDbInsert = MainMiddleService.insertTempSaleCart(paramsString);


                // 05.02.2022 -------------------------------------------------
                // discount 처리됐는지 여부 확인
                if (temp_selectedDcExtraAllEach.equals("ALL")) {
                    all_dcextra_cnt++;
                }
                if (temp_selectedDcExtraAllEach.equals("EACH")) {
                    each_dcextra_cnt++;
                    each_dcextra_idx_arr.add(tempCount + "");
                }
                // -------------------------------------------------------------

                if (tempCount == 0) {
                    tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,18), 1);
                }
                tempCount++;
            }
            tempSaleCartCursor.close();


            // common gratuity 관련
            GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
            // common gratuity 관련
            GlobalMemberValues.addCartLastItemForCommonGratuityUse();


        }catch (Exception e){
        }

        GlobalMemberValues.B_FIRSTORDER_YN = false;
        if (GlobalMemberValues.isStrEmpty(temp_holdcode)) {
            // 첫 주문시 temp_holdcode 가 비어있음
            temp_holdcode = GlobalMemberValues.makeHoldCode();
            // 첫 주문시 B_FIRSTORDER_YN 해당 값은 true
            GlobalMemberValues.B_FIRSTORDER_YN = true;
        }

        // 06.02.2022 ---------------------------------------------------------------
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCode_byRepay)) {
            temp_holdcode = GlobalMemberValues.mHoldCode_byRepay;
        }
        // --------------------------------------------------------------------------

        MainMiddleService.mHoldCode = temp_holdcode;

        GlobalMemberValues.logWrite("taesooanniejjjlog", "MainMiddleService.mHoldCode : " + MainMiddleService.mHoldCode + "\n");

        // 고객정보 배치
        if (GlobalMemberValues.isStrEmpty(tempCustomerId)) {
            tempCustomerId = MssqlDatabase.getResultSetValueToString(
                    "select customerId from temp_salecart_deliveryinfo where holdcode = '" + temp_holdcode + "' "
            );
        }
        // todo recall mDbinit 제거하기.
        if (!GlobalMemberValues.isStrEmpty(tempCustomerId)) {
            Recall.setCustomerInfoAtOutClass(tempCustomerId, MainActivity.mDbInit);
        }

        // 고객수(인원수)를 처음 지정하고 sale 로 들어갈 경우 ----------------------------------------------------------------
        // 고객수 저장 테이블 salon_store_restaurant_table_peoplecnt 에는 holdcode 가 저장되지 않았으므로
        // salon_store_restaurant_table_peoplecnt 의 holdcode 를 저장한다.
        String tempHCode = MssqlDatabase.getResultSetValueToString(
                " select holdcode from salon_store_restaurant_table_peoplecnt where tableidx like '%" + mTableIdxArrList.get(0) + "%' ");
        if (GlobalMemberValues.isStrEmpty(tempHCode)) {
            String tempHoldCode = getHoldCodeByTableidx(mTableIdxArrList.get(0), TableSaleMain.mSubTableNum);
            if (GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                tempHoldCode = MainMiddleService.mHoldCode;
            }
            GlobalMemberValues.logWrite("peoplecntlog2", "tempHoldCode : " + tempHoldCode + "\n");
            Vector<String> updateVector = new Vector<String>();
            strQuery = " update salon_store_restaurant_table_peoplecnt set holdcode = '" + tempHoldCode + "' " +
                    " where tableidx like '%" + mTableIdxArrList.get(0) + "%' ";
            updateVector.addElement(strQuery);

            for (String tempQuery : updateVector) {
                GlobalMemberValues.logWrite("peoplecntlog", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(updateVector);
            if (returnResult == "N" || returnResult == "") {
                //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                //return;
            } else {
            }
        }
        // --------------------------------------------------------------------------------------------------------------------

        // 05.02.2022 -------------------------------------------------------------------------------------
        // all discount 적용인지 확인
        GlobalMemberValues.logWrite("dcrecalltestjjjlog", "all_dcextra_cnt : " + all_dcextra_cnt + "\n");
        if (all_dcextra_cnt > 0) {
            if (Discount.discountRateEditText == null) {
                MainActivity.doDiscountOpen();
            }

            isDiscountAdjust = true;

            // All extra / discount 를 체크
            Discount.discountAllEachSwitch.setChecked(true);

            // all extra / extra 값 입력
            Discount.discountRateEditText.setText(dcextravalue);

            String dcextratype_tmp = dcextratype + "J";
            if (dcextratype_tmp.equals("%J")) {
                Discount.discountRateTypeButton.setChecked(true);
                Discount.discountRateTypeButton.setBackgroundColor(Color.parseColor("#b5a917"));
            } else {
                Discount.discountRateTypeButton.setChecked(false);
                Discount.discountRateTypeButton.setBackgroundColor(Color.parseColor("#278e24"));
            }

            if (selectedDcExtraType.equals("DC")) {
                Discount.discountDiscountExtraSwitch.setChecked(true);
            } else {
                Discount.discountDiscountExtraSwitch.setChecked(false);
            }

            //Discount.applyCoupon("");
        }

        // each discount 적용인지 확인
        GlobalMemberValues.logWrite("jjjselectedsalecartidxjjjlog", "each_dcextra_cnt : " + each_dcextra_cnt + "\n");
        if (each_dcextra_cnt > 0 && each_dcextra_idx_arr != null && each_dcextra_idx_arr.size() > 0) {
            if (Discount.discountRateEditText == null) {
                MainActivity.doDiscountOpen();
            }

            isDiscountAdjust = true;

            mSelected_index_for_isCheckedConfrim = each_dcextra_idx_arr;

            // each extra / discount 를 체크
            Discount.discountAllEachSwitch.setChecked(false);

            GlobalMemberValues.logWrite("jjjselectedsalecartidxjjjlog", "dcextravalue : " + dcextravalue + "\n");

            // each extra / extra 값 입력
            Discount.discountRateEditText.setText(dcextravalue);

            String dcextratype_tmp = dcextratype + "J";
            if (dcextratype_tmp.equals("%J")) {
                Discount.discountRateTypeButton.setChecked(true);
                Discount.discountRateTypeButton.setBackgroundColor(Color.parseColor("#b5a917"));
            } else {
                Discount.discountRateTypeButton.setChecked(false);
                Discount.discountRateTypeButton.setBackgroundColor(Color.parseColor("#278e24"));
            }

            if (selectedDcExtraType.equals("DC")) {
                Discount.discountDiscountExtraSwitch.setChecked(true);
            } else {
                Discount.discountDiscountExtraSwitch.setChecked(false);
            }


            //Discount.applyCoupon("");
        }
        // ------------------------------------------------------------------------------------------------

    }

    public void setTableMerge(String str_noneSelected) {
        // 먼저 선택된 테이블들중에 이미 합쳐진 테이블이 있을 경우 ----------------------------------------------
        // merge 된 테이블을 split 하고 진행하라는 메시지를 띄운다..
        if (!GlobalMemberValues.isStrEmpty(str_noneSelected)){
            setClearSelectedTableIdx(true);
            mSelectedTablesArrList.add(str_noneSelected);
        }
        int tempTableCnt = 0;
        String tempHoldCode = "";
        ArrayList<String> addTableIdx = new ArrayList<String>();
        for (String tempTableIdx : mSelectedTablesArrList) {
            tempHoldCode = getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum);
            int tableidxCnt = getTableCountByHoldcode(getHoldCodeByTableidx(tempHoldCode, TableSaleMain.mSubTableNum));

            if (tableidxCnt > 1) {
                ResultSet tempCursor;
                String strQuery = " select tableidx from temp_salecart where holdcode = '" + tempHoldCode + "' ";
                tempCursor = MssqlDatabase.getResultSetValue(strQuery);
                try {
                    while (tempCursor.next()) {
                        String tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);
                        addTableIdx.add(tableidx);
                    }
                    tempCursor.close();
                } catch (Exception e){

                }

            } else {

            }

            tempTableCnt += tableidxCnt;
        }

        if (addTableIdx.size() > 0) {
            for (String tableidx : addTableIdx) {
                mSelectedTablesArrList.add(tableidx);
            }
        }

//        if (mSelectedTablesArrList.size() != tempTableCnt) {
//            GlobalMemberValues.displayDialog(mContext, "Warning", "Please split the merged tables first", "Close");
//            return;
//        }
        // ------------------------------------------------------------------------------------------------------

        Vector<String> updateVector = new Vector<String>();
        String strQuery = "";

        String newHoldCode = GlobalMemberValues.makeHoldCode();

        int newMergedNum = 0;
        if (mSelectedTablesArrList.size() > 1) {
            newMergedNum = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(" select max(mergednum) from temp_salecart ")) + 1;
        }

        // 05262023
        int tempCnt = 0;
        String tempPastHoldcode = "";
        String mergednumstr = "0" + newMergedNum;
        mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
        String tempMergedStr = "Merged (" + mergednumstr + ")";

        for (String tempTableIdx : mSelectedTablesArrList) {
            if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                // 05262023
                if (tempCnt == 0) {
                    tempPastHoldcode = TableSaleMain.getHoldCodeByTableidx(tempTableIdx, mSubTableNum);
                } else {
                    tempPastHoldcode = tempPastHoldcode + "-J-" + TableSaleMain.getHoldCodeByTableidx(tempTableIdx, mSubTableNum);
                }

                strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '" + newMergedNum + "', isCloudUpload = 0 " +
                        " where tableidx like '%" + tempTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = "delete from salon_store_restaurant_table_split where tableidx like '%" + tempTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " update salon_store_restaurant_table_peoplecnt " +
                        " set holdcode = '" + newHoldCode + "' " +
                        " where tableIdx like '%" + tempTableIdx + "%' ";
                updateVector.addElement(strQuery);
//                strQuery = "update salon_store_restaurant_table_peoplecnt set holdcode = '" + newHoldCode + "' where tableidx like '%" + tempTableIdx + "%' ";
//                updateVector.addElement(strQuery);

                strQuery = "delete from salon_store_restaurant_table_merge where tableidx like '%" + tempTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " insert into salon_store_restaurant_table_merge (tableidx, holdcode, mergednum) values ( " +
                        "'" + tempTableIdx + "', " +
                        "'" + newHoldCode + "', " +
                        "'" + newMergedNum + "' " +
                        " ) ";
                updateVector.addElement(strQuery);


                // 04252023
                // 테이블 이동 관련정보를 새로고침을 위한 테이블에 저장
                strQuery = " insert into salon_table_statuschange (holdcode, stcode, tableidx, delyn, ctype) values ( " +
                        " '" + newHoldCode + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + tempTableIdx + "', " +
                        " '" + "N" + "', " +
                        " '" + "merge" + "' " +
                        " ) ";
                updateVector.addElement(strQuery);

                tempCnt++;
            }
        }
        if (updateVector.size() > 0) {
            for (String tempQuery : updateVector) {
                GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
            }

            // 05262023 ---------------------------------------------------------------------------------------
            JSONObject tempJson = new JSONObject();
            try {
                tempJson.put("currentholdcode", newHoldCode);
                tempJson.put("pastholdcode", tempPastHoldcode);
                tempJson.put("mergetablename", tempMergedStr);

                GlobalMemberValues.logWrite("jsonforbilllogjjj", "여기6 : " + tempJson.toString() + "\n");

                // 11102023
                if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(tempJson.toString())) {
                    strQuery = " insert into salon_sales_kitchenprintingdata_json " +
                            " (salesCode, scode, sidx, stcode, jsonstr, printedyn, presalescode) values ( " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(newHoldCode,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                            "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                            " '" + GlobalMemberValues.getDBTextAfterChecked(tempJson.toString(), 0) + "', " +
                            " 'Y', " +
                            " 'pastholdcode' " +
                            " ) ";
                    updateVector.addElement(strQuery);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 05262023 ---------------------------------------------------------------------------------------


            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(updateVector);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                return;
            } else {
                // 08072023 ---------------------------------------------------------
                // 08252023
                TableSaleMain.mTableIdxArrList = TableSaleMain.mSelectedTablesArrList;



                // 09262023

                // 10092023
                setGratuity(newHoldCode);

                // 08072023 ---------------------------------------------------------





                //GlobalMemberValues.logWrite("mergetablelog", "mSelectedZoneIdx : " + mSelectedZoneIdx + "\n");
                viewTableSettigns(mSelectedZoneIdx);
                setInitValues();

                // 클라우드 업로드
                uploadTableDataCloudExe();
            }
        }
        setClearSelectedTableIdx(true);
    }

    //09112024 sendDataToTOrder parameter added to indicate whether method should send data to torder with its execution
    //Added to prevent making api call twice.
    public void setClearTable(ArrayList<String> paramArrList, Boolean sendDataToTOrder) {
        GlobalMemberValues.logWrite("jjjclearlog", "tables : " + paramArrList.toString() + "\n");
        if (paramArrList != null && paramArrList.size() > 0) {
            // 테이블 초기화후 주문이 있는 테이블일 경우 취소주방프린팅을 해줄 것인지 여부를 묻는다
            if (GlobalMemberValues.isOpenmsgwhendeletemenu()){
                new AlertDialog.Builder(mActivity)
                        .setTitle("Table Clear")
                        .setMessage("Would you like to print the canceled order to the kitchen?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        kitchenPrintingCanceledMenus(paramArrList);
                                        //09112024 send table cleared call to TOrder API if torder is in use
                                        if(GlobalMemberValues.isTOrderUse() && sendDataToTOrder){
                                            GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity, "0");
                                        }
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                setClearTableExe(paramArrList);
                                //viewTableSettigns(mSelectedZoneIdx);
                                //09112024 send table cleared call to TOrder API if torder is in use
                                if(GlobalMemberValues.isTOrderUse() && sendDataToTOrder){
                                    GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity, "0");
                                }
                            }
                        })
                        .show();

            } else {
                kitchenPrintingCanceledMenus(paramArrList);
                //09112024 send table cleared call to TOrder API if torder is in use
                if(GlobalMemberValues.isTOrderUse() && sendDataToTOrder){
                    GlobalMemberValues.sendDataToTOrderService(MainActivity.mContext, MainActivity.mActivity, "0");
                }
            }

        }
    }

    public void setClearTableExe(ArrayList<String> paramArrList) {
        for (String tempTableIdx : new ArrayList<String>(paramArrList)) {
            //GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "값 : " + tempidx + "\n");

            // 해당 테이블의 cart 비우기
            setClearCartOfTableByTableidx(tempTableIdx);
        }
        // 장바구니 비우기
        MainMiddleService.clearListExe();

        GlobalMemberValues.mArrListForTSM = null;

        viewTableSettigns(mSelectedZoneIdx);
    }

    public void kitchenPrintingCanceledMenus(ArrayList<String> paramArrList) {
        GlobalMemberValues.logWrite("jjtablelog", "여기시작됨..." + "\n");
        for (String tempTableIdx : paramArrList) {
            GlobalMemberValues.logWrite("jjtablelog", "tempTableIdx : " + tempTableIdx + "\n");
            GlobalMemberValues.logWrite("jjtablelog", "mSubTableNum : " + TableSaleMain.mSubTableNum + "\n");

            GlobalMemberValues.mCancelKitchenPrinting = "Y";         // 주문취소 키친프린팅인지 여부
            Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
            phoneorderkitchenPrintingExe(getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum));
        }

        setClearTableExe(paramArrList);
        viewTableSettigns(mSelectedZoneIdx);
    }

    public void allTablesClear() {
        String strQuery = "";
        Vector<String> deleteVec = new Vector<String>();

        strQuery = "delete from temp_salecart where not(tableidx = '' or tableidx is null) ";
        deleteVec.addElement(strQuery);

        strQuery = "delete from salon_store_restaurant_table_split ";
        deleteVec.addElement(strQuery);

        strQuery = "delete from salon_store_restaurant_table_peoplecnt ";
        deleteVec.addElement(strQuery);

        strQuery = "delete from salon_store_restaurant_table_merge ";
        deleteVec.addElement(strQuery);

        strQuery = "delete from bill_list ";
        deleteVec.addElement(strQuery);

        if (deleteVec.size() > 0) {
            for (String tempQuery : deleteVec) {
                //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(deleteVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                return;
            } else {
            }
        }
    }

    public static void setClearCartOfTableByTableidx(String paramTableIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramTableIdx)) {
            // 먼저 holdcode 를 구한다.
            String temp_holdcode = getHoldCodeByTableidx(paramTableIdx, TableSaleMain.mSubTableNum);

            GlobalMemberValues.logWrite("sqlcheckjjjlog", "temp_holdcode : " + temp_holdcode + "\n");

            String strQuery = "";
            Vector<String> deleteVec = new Vector<String>();

            strQuery = "delete from temp_salecart where holdcode = '" + temp_holdcode + "' ";
            deleteVec.addElement(strQuery);

            strQuery = "delete from temp_salecart_deliveryinfo where holdcode = '" + temp_holdcode + "' ";
            deleteVec.addElement(strQuery);

            strQuery = "delete from temp_salecart_optionadd where holdcode = '" + temp_holdcode + "' ";
            deleteVec.addElement(strQuery);

            strQuery = "delete from temp_salecart_optionadd_imsi where holdcode = '" + temp_holdcode + "' ";
            deleteVec.addElement(strQuery);


            strQuery = "insert into temp_salecart_del ( " +
                    " holdcode, sidx, stcode, tableidx, alldelyn " +
                    " ) values ( " +
                    " '" + temp_holdcode + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + paramTableIdx + "', " +
                    " 'Y' " +
                    " ) ";
            deleteVec.addElement(strQuery);

            if (!GlobalMemberValues.isStrEmpty(temp_holdcode) && !temp_holdcode.equals("NOHOLDCODE")) {
                strQuery = "insert into temp_salecart_del2 ( " +
                        " holdcode, stcode, tableidx " +
                        " ) values ( " +
                        " '" + temp_holdcode + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + paramTableIdx + "' " +
                        " ) ";
                deleteVec.addElement(strQuery);

                GlobalMemberValues.logWrite("sqlcheckjjjlog", "sql : " + strQuery + "\n");

                // 해당 스테이션 외의 다른 스테이션의 salon_newcartcheck_bystation 데이터 지우기
                strQuery = " delete from salon_newcartcheck_bystation where " +
                        " holdcode = '" + temp_holdcode + "' " +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
                deleteVec.addElement(strQuery);

                GlobalMemberValues.logWrite("sqlcheckjjjlog", "sql : " + strQuery + "\n");
            }


//            // salon_newcartcheck_bystation 관련 수정 또는 등록
//            strQuery = " select count(*) from salon_newcartcheck_bystation where " +
//                    " stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
//                    " and holdcode = '" + temp_holdcode + "' ";
//            GlobalMemberValues.logWrite("mergetablelog", "query : " + strQuery + "\n");
//            int tempCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));
//            if (tempCnt == 0 && !GlobalMemberValues.isStrEmpty(temp_holdcode)) {
//                strQuery = " insert into salon_newcartcheck_bystation (holdcode, stcode, delyn) values ( " +
//                        " '" + temp_holdcode + "', " +
//                        " '" + GlobalMemberValues.STATION_CODE + "', " +
//                        " 'Y' " +
//                        " ) ";
//                deleteVec.addElement(strQuery);
//            }
//            strQuery = " update salon_newcartcheck_bystation set delyn = 'Y' " +
//                    " where holdcode = '" + temp_holdcode + "' and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
//            deleteVec.addElement(strQuery);

            strQuery = "delete from salon_store_restaurant_table_split where tableidx like '%" + paramTableIdx + "%' ";
            deleteVec.addElement(strQuery);

            strQuery = "delete from salon_store_restaurant_table_peoplecnt where tableidx like '%" + paramTableIdx + "%' ";
            deleteVec.addElement(strQuery);

            strQuery = "delete from salon_store_restaurant_table_merge where tableidx like '%" + paramTableIdx + "%' ";
            deleteVec.addElement(strQuery);




            if (deleteVec.size() > 0) {
                for (String tempQuery : deleteVec) {
                    GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(deleteVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {
                    mSelectedTablesArrList.remove(paramTableIdx);
                }
            }
        }
    }

    public static void setClearCartOfTableByHoldcode(String paramHoldcode) {
        if (!GlobalMemberValues.isStrEmpty(paramHoldcode)) {
            String strQuery = "";
            Vector<String> deleteVec = new Vector<String>();

            /** salon_store_restaurant_table_split 테이블 삭제한다. **************************************************/
            strQuery = "delete from salon_store_restaurant_table_split where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** salon_store_restaurant_table_merge 테이블 삭제한다. **************************************************/
            strQuery = "delete from salon_store_restaurant_table_merge where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** salon_store_restaurant_table_peoplecnt 테이블 삭제한다. **************************************************/
            strQuery = "delete from salon_store_restaurant_table_peoplecnt where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** temp_salecart 테이블에서 해당 주문건을 삭제한다. *****************************************************/
            strQuery = "delete from temp_salecart where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** temp_salecart_deliveryinfo 테이블에서 해당 주문건을 삭제한다. ******************************************/
            strQuery = "delete from temp_salecart_deliveryinfo where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** temp_salecart_optionadd 테이블에서 해당 주문건을 삭제한다. *****************************************************/
            strQuery = "delete from temp_salecart_optionadd where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            /** temp_salecart_optionadd_imsi 테이블에서 해당 주문건을 삭제한다. *****************************************************/
            strQuery = "delete from temp_salecart_optionadd_imsi where holdcode = '" + paramHoldcode + "' ";
            deleteVec.addElement(strQuery);
            /*********************************************************************************************************/

            if (deleteVec.size() > 0) {
                for (String tempQuery : deleteVec) {
                    //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(deleteVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {
                    mSelectedTablesArrList.remove(paramHoldcode);
                }
            }
        }
    }

    public void checkTransferTable() {
        // 선택된 테이블 중 빈테이블이 있는지 확인한다.
        String prevTableIdx = "";
        String nextTableIdx = "";
        int tableOrders = 0;
        int mergedTables = 0;
        for (String tempTableIdx : mSelectedTablesArrList) {
            int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(idx) from temp_salecart where tableidx like '%" + tempTableIdx + "%' "
            ));
            GlobalMemberValues.logWrite("transferlog2", "tableOrderCount : " + tableOrderCount + "\n");
            if (tableOrderCount > 0) {
                prevTableIdx = tempTableIdx;
                tableOrders++;
            } else {
                nextTableIdx = tempTableIdx;
            }

            int mergedTablesCount = getTableCountByHoldcode(getHoldCodeByTableidx(tempTableIdx, TableSaleMain.mSubTableNum));
            GlobalMemberValues.logWrite("transferlog", "mergedTablesCount : " + mergedTablesCount + "\n");
            if (mergedTablesCount > 1) {
                mergedTables++;
            }
        }

        GlobalMemberValues.logWrite("transferlog", "tableOrders : " + tableOrders + "\n");
        GlobalMemberValues.logWrite("transferlog", "mergedTables : " + mergedTables + "\n");

        if (tableOrders > 1) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "There must be one empty table selected", "Close");
            return;
        }

        if (mergedTables > 0) {
            GlobalMemberValues.displayDialog(mContext, "Warning", "The merged table cannot be transferred", "Close");
            return;
        }

        if ((mActivity != null) && (!mActivity.isFinishing())) {
            String finalPrevTableIdx = prevTableIdx;
            String finalNextTableIdx = nextTableIdx;
            new AlertDialog.Builder(mActivity)
                    .setTitle("Table Transfer")
                    .setMessage("Do you want to transfer the selected table?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    GlobalMemberValues.logWrite("transferlog2", "finalPrevTableIdx : " + finalPrevTableIdx + "\n");
                                    GlobalMemberValues.logWrite("transferlog2", "finalNextTableIdx : " + finalNextTableIdx + "\n");
                                    setTransferTable(finalPrevTableIdx, finalNextTableIdx);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                setClearSelectedTableIdx(true);
                            }
                        }
                    })
                    .show();
        }

    }

    public void setTransferTable(String paramPrevTableIdx, String paramNextTableIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramPrevTableIdx) && !GlobalMemberValues.isStrEmpty(paramNextTableIdx)) {
            String strQuery = "";

            String preSalesCode = TableSaleMain.getHoldCodeByTableidx(paramPrevTableIdx,"1");

            strQuery = " select count(*) from salon_sales_kitchenprintingdata_json where salescode = '" + preSalesCode + "' " +
                    " and not(presalescode = '' or presalescode is null) ";
            GlobalMemberValues.logWrite("movetablelogjjj", "sql : " + strQuery + "\n");
            int presalesCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

            if (presalesCount > 0) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "The move table operation you previously requested is still in progress", "Close");
            } else {
                Vector<String> updateVector = new Vector<String>();


                String newHoldCode = GlobalMemberValues.makeHoldCode();

                String tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(paramNextTableIdx, "T", "") + "' ");

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
                        " tableidx, subtablenum, billnum, kitchenprintedyn, togotype, wdate, " +
                        // 05312024
                        " tordercode, iscloudupload " +
                        " ) " +
                        " select " +
                        " '" + newHoldCode + "', " +
                        " sidx, stcode, midx, svcIdx,  " +
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
                        " '" + paramNextTableIdx + "', " +
                        " subtablenum, billnum, kitchenprintedyn, togotype, wdate, " +
                        // 05312024
                        " tordercode, 0 " +
                        " from temp_salecart " +
                        " where tableIdx like '%" + paramPrevTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " delete from temp_salecart where tableIdx like '%" + paramPrevTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " update salon_store_restaurant_table_peoplecnt " +
                        " set holdcode = '" + newHoldCode + "', tableIdx = '" + paramNextTableIdx + "' " +
                        " where tableIdx like '%" + paramPrevTableIdx + "%' ";
                updateVector.addElement(strQuery);

//            strQuery = " update temp_salecart set tableIdx = '" + paramNextTableIdx + "', " +
//                    " holdcode = '" + newHoldCode + "', isCloudUpload = 0 " +
//                    " where tableIdx like '%" + paramPrevTableIdx + "%' ";
//            updateVector.addElement(strQuery);

                strQuery = " update salon_store_restaurant_table_split set tableIdx = '" + paramNextTableIdx + "', " +
                        " holdcode = '" + newHoldCode + "' " +
                        " where tableIdx like '%" + paramPrevTableIdx + "%' ";
                updateVector.addElement(strQuery);

                strQuery = " update salon_sales_kitchenprintingdata_json set salescode = '" + newHoldCode + "', " +
                        " presalescode = '" + preSalesCode + "', nowtableidx = '" + paramNextTableIdx + "', " +
                        " nowtablename = '" + tableName + "' " +
                        " where salescode = '" + preSalesCode + "' ";
                updateVector.addElement(strQuery);


                // 04252023
                // 테이블 이동 관련정보를 새로고침을 위한 테이블에 저장
                strQuery = " insert into salon_table_statuschange (holdcode, stcode, tableidx, delyn, ctype) values ( " +
                        " '" + newHoldCode + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + paramNextTableIdx + "', " +
                        " '" + "N" + "', " +
                        " '" + "move" + "' " +
                        " ) ";
                updateVector.addElement(strQuery);


                for (String tempQuery : updateVector) {
                    GlobalMemberValues.logWrite("transferlog2", "query : " + tempQuery + "\n");
                }

                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(updateVector);
                if (returnResult == "N" || returnResult == "") {
                    //GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    //return;
                } else {
                    // 클라우드 업로드
                    viewTableSettigns(mSelectedZoneIdx);
                    uploadTableDataCloudExe();
                }

                //06032024 if using torder send data to torder API
                // 07172024
                // 아래 주석처리
//            if (GlobalMemberValues.isTOrderUse()){
//                GlobalMemberValues.sendTOrderAPIOrderData("K");
//            }

            }
        }
    }

    // 06.10.2022
    public static String geTableidxBytHoldCode(String paramHoldcode) {
        String temp_tableidx =  "";
        temp_tableidx = MssqlDatabase.getResultSetValueToString(
                " select top 1 tableidx from temp_salecart where holdcode = '" + paramHoldcode + "' order by idx asc "
        );

        return temp_tableidx;
    }


    public static String getHoldCodeByTableidx(String paramTableIdx, String paramSubTableNum) {
        if (GlobalMemberValues.isStrEmpty(paramSubTableNum)) {
            paramSubTableNum = "1";
        }
        paramTableIdx = paramTableIdx.replace("TT","T");
        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                " select distinct holdcode from temp_salecart where tableidx like '%" + paramTableIdx + "%' and subtablenum = '" + paramSubTableNum + "' "
        );
        if (GlobalMemberValues.isStrEmpty(temp_holdcode)) {
            temp_holdcode = getHoldCodeByTableidxInMergedTable(paramTableIdx);
        }
        if (GlobalMemberValues.isStrEmpty(temp_holdcode)) {
            temp_holdcode = getHoldCodeByTableidxInPeopleCnt(paramTableIdx);
        }

        return temp_holdcode;
    }

    public static int getTableCountByTableidxInMergedTable(String paramTableIdx) {
        int tableCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from salon_store_restaurant_table_merge where holdcode = '" + getHoldCodeByTableidxInMergedTable(paramTableIdx) + "' "
        ));

        return tableCount;
    }

    public static String getHoldCodeByTableidxInMergedTable(String paramTableIdx) {
        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                " select distinct holdcode from salon_store_restaurant_table_merge where tableidx like '%" + paramTableIdx + "%' "
        );

        return temp_holdcode;
    }

    public static String getHoldCodeByTableidxInPeopleCnt(String paramTableIdx) {
        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                " select distinct holdcode from salon_store_restaurant_table_peoplecnt where tableidx like '%" + paramTableIdx + "%' "
        );

        return temp_holdcode;
    }

    public static int getMergedNumByTableidxInMergedTable(String paramTableIdx) {
        int temp_mergednum = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select distinct mergednum from salon_store_restaurant_table_merge where tableidx like '%" + paramTableIdx + "%' "
        ));

        return temp_mergednum;
    }

    public static int getTableCountByHoldcode(String paramHoldCode) {
        int tableidxCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(distinct tableidx) from temp_salecart where holdcode = '" + paramHoldCode + "' "
        ));

        return tableidxCnt;
    }

    public void openSplit() {
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            Intent intent = new Intent(getApplicationContext(), TableSplit.class);
            intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
            startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }

            setInitValues();
        }
    }

    public void setTwoSplit(String mTableIdx){
        int paramTableCount = 2;
        if (paramTableCount > 0) {
            String strQuery = "";
            Vector<String> queryVec = new Vector<String>();

            strQuery = "delete from salon_store_restaurant_table_split where tableidx like '" + mTableIdx + "' ";
            queryVec.addElement(strQuery);

            for (int jjj = 0; jjj < paramTableCount; jjj++) {
                String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                        " select holdcode from temp_salecart where tableidx like '%" + mTableIdx + "%' and subtablenum = '" + (jjj + 1) + "' "
                );
                if (GlobalMemberValues.isStrEmpty(temp_holdcode)) {
                    temp_holdcode = GlobalMemberValues.makeHoldCode();
                }

                strQuery = " insert into salon_store_restaurant_table_split " +
                        " (tableidx, subtablenum, holdcode) values ( " +
                        "'" + mTableIdx + "', " +
                        "'" + (jjj + 1) + "', " +
                        "'" + temp_holdcode + "' " +
                        " )";
                queryVec.addElement(strQuery);
            }

            if (queryVec.size() > 0) {
                for (String tempQuery : queryVec) {
                    GlobalMemberValues.logWrite("splitsqllog", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(queryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {
//                    closeActivity();
                    viewTableSettigns(mSelectedZoneIdx);
                }
            }
        }
    }

    public static void openSplittedTableList(String paramFrom) {
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            Intent intent = new Intent(mContext.getApplicationContext(), TableSplittedList.class);
            intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
            intent.putExtra("from", paramFrom);
            mActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }

            setInitValues2();
        }
    }

    public static void openBillSplitMerge() {
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            // 주문이 있는 테이블만 처리하도록
            int tableOrderCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(idx) from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "
            ));
            if (tableOrderCount == 0) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "There is no order history in the selected table", "Close");
                setInitValues();
                return;
            }

            Intent intent = new Intent(mContext.getApplicationContext(),BillSplitMerge.class);
            intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
            intent.putExtra("selectedSubTableNum", mSubTableNum);
            mActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
            //05172024 if using QSR Mode, ignore tableidx concept
        } else if (GlobalMemberValues.isQSRPOSonRestaurantPOS){
//            Intent intent = new Intent(MainActivity.mContext,BillSplitMerge.class);
//            intent.putExtra("selectedTableIdx", "");
//            intent.putExtra("selectedSubTableNum", mSubTableNum);
//            intent.putExtra("currentHoldCode", MainMiddleService.mHoldCode);
//            MainActivity.mActivity.startActivity(intent);
//            if (GlobalMemberValues.isUseFadeInOut()) {
//                MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
//            }
        }
    }

    public static void openBillSplitMerge_in_QSRMode(){

        // 주문이 있는 테이블만 처리하도록
        if (GlobalMemberValues.mSelectedTableIdx.isEmpty()) return;

        Intent intent = new Intent(MainActivity.mContext,BillSplitMerge.class);
        intent.putExtra("selectedTableIdx", GlobalMemberValues.mSelectedTableIdx);
        intent.putExtra("selectedSubTableNum", mSubTableNum);
        intent.putExtra("currentHoldCode", MainMiddleService.mHoldCode);
        MainActivity.mActivity.startActivity(intent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    public static void openBillSplit() {
        openBillSplitMerge();

//        Intent intent = new Intent(mContext.getApplicationContext(),TableSaleBillSplit.class);
//        intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
//        intent.putExtra("selectedSubTableNum", mSubTableNum);
//        mActivity.startActivity(intent);
//        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
//
//        setInitValues();
    }

    public static void openBillPrint(String is_mainIn) {
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            Intent intent = new Intent(mContext.getApplicationContext(),TableSaleBillPrint.class);
            intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
            intent.putExtra("selectedSubTableNum", mSubTableNum);
            intent.putExtra("isMainIn", is_mainIn);
            mActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
            //setInitValues();
        } else {
            if (is_mainIn.equals("Y")){
                GlobalMemberValues.openRestaurantTable();
            }
        }
    }

    public static void openDetailInfo() {
        if (mSelectedTablesArrList != null && mSelectedTablesArrList.size() > 0) {
            Intent intent = new Intent(mContext.getApplicationContext(),TableDetailInfo.class);

            intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
            intent.putExtra("selectedSubTableNum", mSubTableNum);

            mActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }

            setInitValues();
        }
    }

    public static void openPeopleCnt() {

        int defaultCustomerCnt = 2;

        if (GlobalMemberValues.isViewCustomerNumbers()) {
            // To Go 주문 (quick sale) 인지 여부
            String tempGetType = "H";
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
            }

            // To Go 주문 (quick sale) 이 아닐 경우에만 고객수 창을 띄운다. (To Go 주문일 경우 고객수 창 띄우지 않음)
            if (tempi == 0) {
                // 05262023 --------------------------------------------------------------------------------------------
                // 주문이 있는 경우에는 고객수 입력창을 띄우지 않는다.
                int tOrdersCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                        " select count(*) from temp_salecart where tableidx like '%" + mSelectedTablesArrList.get(0) + "%' "));
                // 05262023 --------------------------------------------------------------------------------------------


                if (tOrdersCnt > 0) {
                    if (GlobalMemberValues.mPeopleCnt_byRepay > 0) {
                        defaultCustomerCnt = GlobalMemberValues.mPeopleCnt_byRepay;
                    }
                    TablePeopleCnt.setTablePeopleCnt(defaultCustomerCnt, false, mContext,
                            mSelectedTablesArrList.get(0), selectedSubTableHoldCode);
                } else {
                    Intent intent = new Intent(mContext.getApplicationContext(),TablePeopleCnt.class);
                    if (mSelectedTablesArrList.size() != 0){
                        intent.putExtra("selectedTableIdx", mSelectedTablesArrList.get(0));
                    } else {
                        intent.putExtra("selectedTableIdx", "0");
                    }
                    intent.putExtra("selectedHoldCode", selectedSubTableHoldCode);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }

                GlobalMemberValues.logWrite("ppcntlogjjj", "여기왔음.." + "\n");


            } else {
                // 05.31.2022 -----------------------------------------------------------
                if (GlobalMemberValues.mPeopleCnt_byRepay > 0) {
                    defaultCustomerCnt = GlobalMemberValues.mPeopleCnt_byRepay;
                }
                TablePeopleCnt.setTablePeopleCnt(defaultCustomerCnt, false, mContext,
                        mSelectedTablesArrList.get(0), selectedSubTableHoldCode);
                // ----------------------------------------------------------------------
            }
        } else {
            if (mSelectedTablesArrList.size() == 0) {
                GlobalMemberValues.displayDialog(mContext, "Warning", "Pleas select a table", "Close");
                return;
            }

            // 05.31.2022 -----------------------------------------------------------
            int tempPpCnt = 2;
            if (GlobalMemberValues.mPeopleCnt_byRepay > 0) {
                tempPpCnt = GlobalMemberValues.mPeopleCnt_byRepay;
            }
            TablePeopleCnt.setTablePeopleCnt(tempPpCnt, false, mContext,
                    mSelectedTablesArrList.get(0), selectedSubTableHoldCode);
            // ----------------------------------------------------------------------
        }

        setInitValues();
    }

    public static void kitchenPrint(String paramHoldCode, boolean paramIsAlert) {
        if (paramIsAlert) {                             // 테이블창에서 테이블선택하고 프린팅 할 경우
            if ((mActivity != null) && (!mActivity.isFinishing())) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Kitchen Printing")
                        .setMessage("Are you sure you want to print in the kitchen?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
                                        phoneorderkitchenPrintingExe(paramHoldCode);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                    setClearSelectedTableIdx(true);
                                }
                            }
                        })
                        .show();
            }
        } else {                    // 장바구니에 담고 프린팅 할 경우
            Recall.mKitchenPrintOnRecall = "Y";             // Recall.mKitchenPrintOnRecall 의 값이 N 일 경우 프린트 안된 것만 프린트됨
            phoneorderkitchenPrintingExe(paramHoldCode);
        }
    }
    public static void kitchenPrint(String paramHoldCode, boolean paramIsAlert, Context context) {
        if (paramIsAlert) {                             // 테이블창에서 테이블선택하고 프린팅 할 경우
            if ((mActivity != null) && (!mActivity.isFinishing())) {
                new AlertDialog.Builder(context)
                        .setTitle("Kitchen Printing")
                        .setMessage("Are you sure you want to print in the kitchen?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
                                        phoneorderkitchenPrintingExe(paramHoldCode);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                                    setClearSelectedTableIdx(true);
                                }
                            }
                        })
                        .show();
            }
        } else {                    // 장바구니에 담고 프린팅 할 경우
            Recall.mKitchenPrintOnRecall = "Y";             // Recall.mKitchenPrintOnRecall 의 값이 N 일 경우 프린트 안된 것만 프린트됨
            phoneorderkitchenPrintingExe(paramHoldCode);
        }
    }

    public static void phoneorderkitchenPrintingExe(String paramHoldCode) {
        GlobalMemberValues.logWrite("jjjprintinglog", "paramHoldCode : " + paramHoldCode + "\n");

        String get_tableInfos = "";
        get_tableInfos = getTableInfosByHoldCode(paramHoldCode);

        GlobalMemberValues.logWrite("jjjprintinglog", "get_tableInfos : " + get_tableInfos + "\n");

        try {
            GlobalMemberValues.phoneorderPrinting(paramHoldCode, "K", get_tableInfos);

            // 07172024
            // 아래 주석처리
//            if (GlobalMemberValues.isTOrderUse()){
//                GlobalMemberValues.sendTOrderAPIOrderData("K");
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getTableInfosByHoldCode(String paramHoldCode) {
        String get_tableInfos = "";
        String strQuery  = " select top 1 mergednum, tableidx, subtablenum, billnum " +
                " from temp_salecart where holdcode = '" + paramHoldCode + "' order by idx asc ";
        GlobalMemberValues.logWrite("jjjprintinglog", "strQuery : " + strQuery + "\n");
        ResultSet tempCursor = MssqlDatabase.getResultSetValue(strQuery);
        int tempCursor_count = 0;
        boolean tempCursor_isBeforeFirst = false;
        try {
//            tempCursor.last();
//            tempCursor_count = tempCursor.getRow();
//            tempCursor.beforeFirst();
//            tempCursor_isBeforeFirst = tempCursor.isBeforeFirst();

            if (tempCursor != null) {
                while(tempCursor.next()) {
                    String get_mergednum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);
                    String get_tableidx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,1), 1);
                    String get_subtablenum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,2), 1);
                    String get_billnum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,3), 1);

                    get_tableInfos = get_mergednum + "-JJJ-" + get_tableidx + "-JJJ-" + get_subtablenum + "-JJJ-" + get_billnum + "-JJJ-" + "END";
                }
            }
            tempCursor.close();
        }catch (Exception e){

        }


        return get_tableInfos;
    }

    public static void setOrderStart(ArrayList<String> paramArr, boolean b_emp_selected, boolean isOpenAtPeopleCnt) {
        GlobalMemberValues.logWrite("jjjcustjjjlog", "setOrderStart 시작" + "\n");

        // isOpenAtPeopleCnt 가 false 일 경우, 즉 TablePeopleCnt 클래스에서 오픈한 경우가 아닐 때에만 ---------------------------
        // 선택한 테이블에 저장된 메뉴가 없으면 선택된 테이블의 idx 에 해당되는 holdcode 로
        // salon_store_restaurant_table_peoplecnt 에 저장되어 있을지 모르는 데이터를 삭제한다.
        if (!isOpenAtPeopleCnt) {
            if (TableSaleMain.mSelectedTablesArrList.size() > 0) {
                int tempItemCnt = GlobalMemberValues.getIntAtString(
                        MssqlDatabase.getResultSetValueToString(
                                " select count(*) from temp_salecart where tableidx = '" + TableSaleMain.mSelectedTablesArrList.get(0) + "' "
                        )
                );
                GlobalMemberValues.logWrite("ppcntlogjjj2", "tempItemCnt : " + tempItemCnt + "\n");
                GlobalMemberValues.logWrite("ppcntlogjjj", "tableidx : " + TableSaleMain.mSelectedTablesArrList.get(0) + "\n");
                if (tempItemCnt == 0) {
                    String strQuery = "";
                    Vector<String> queryVec = new Vector<String>();
                    strQuery = " delete from salon_store_restaurant_table_peoplecnt where tableidx = '" + TableSaleMain.mSelectedTablesArrList.get(0) + "' ";
                    GlobalMemberValues.logWrite("ppcntlogjjj", "strQuery : " + strQuery + "\n");
                    queryVec.addElement(strQuery);
                    String returnResult = MssqlDatabase.executeTransaction(queryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                        return;
                    } else {
                    }
                }
            }
        }
        // -----------------------------------------------------------------------------------------------------------------

        // 먼저 테이블에 인원수를 지정했는지 확인
        int tablepeoplecnt = 0;
        if (!GlobalMemberValues.isStrEmpty(selectedSubTableHoldCode)) {
            GlobalMemberValues.logWrite("jjjsaletablelog2", "여기...1" + "\n");
            tablepeoplecnt = getTablePeopleCntByHoldCode(selectedSubTableHoldCode);
        } else {
            GlobalMemberValues.logWrite("jjjsaletablelog2", "여기...2" + "\n");
            tablepeoplecnt = getTablePeopleCntByHoldCode(getHoldCodeByTableidx(paramArr.get(0), TableSaleMain.mSubTableNum));
        }
        GlobalMemberValues.logWrite("jjjsaletablelog", "holdcode : " + getHoldCodeByTableidx(paramArr.get(0), TableSaleMain.mSubTableNum) + "\n");
        GlobalMemberValues.logWrite("jjjsaletablelog", "tablepeoplecnt : " + tablepeoplecnt + "\n");

        GlobalMemberValues.logWrite("ppcntlogjjj", "mTablePeopleCnt : " + mTablePeopleCnt + "\n");

        if (mTablePeopleCnt > 0) {
            tablepeoplecnt = mTablePeopleCnt;
        }

        // bill_list 에 해당 테이블의 데이터가 있을 경우 (bill split 이 있을 경우)
        int getBillCnt = 0;
        if (paramArr.size() > 0) {
            String tempHoldCode = getHoldCodeByTableidx(paramArr.get(0), mSubTableNum);
            getBillCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select count(*) from bill_list where holdcode = '" + tempHoldCode + "' "
            ));
        }

        if (!GlobalMemberValues.isPaymentByBills && getBillCnt > 0) {
            openBillSplitMerge();
        } else {
            if (tablepeoplecnt == 0) {
//            GlobalMemberValues.logWrite("jjjsaletablelog", "여기..1" + "\n");
                openPeopleCnt();
            } else {
                TableSaleMain.mTablePeopleCnt = tablepeoplecnt;

                openSales(paramArr, b_emp_selected);
            }
        }
    }

    public static void openSales(ArrayList<String> paramArr, boolean b_emp_selected) {
//            GlobalMemberValues.logWrite("jjjsaletablelog", "여기..2" + "\n");
//            GlobalMemberValues.logWrite("jjjsaletablelog", "1. MainMiddleService.mHoldCode : " + MainMiddleService.mHoldCode + "\n");
        mTableIdxArrList = paramArr;
//            GlobalMemberValues.logWrite("jjjsaletablelog", "selected table idx : " + mTableIdxArrList + "\n");

        if (GlobalMemberValues.isQSRPOSonRestaurantPOS) {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.isSelected()) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_ORDER_LIST.callOnClick();
            setCloseActivity(false);
        } else {
            // 직원 선택창 여부
            if (!b_emp_selected) {
                if (EmployeeSelectionPopup.openCount == 0) {
//                    Intent employeeSelectionPopup = new Intent(mContext.getApplicationContext(), EmployeeSelectionPopup.class);
//                    employeeSelectionPopup.putExtra("main_side","T");
//                    employeeSelectionPopup.putExtra("table_idx_arr_list", paramArr);
//                    mActivity.startActivity(employeeSelectionPopup);
//                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
//
                    Intent intent = new Intent(mContext.getApplicationContext(), EmployeeKeyIn.class);
                    intent.putExtra("main_side","T");
                    intent.putExtra("table_idx_arr_list", paramArr);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }
                }
            } else {
                setCloseActivity(false);
            }
        }
    }

    public static int getTableSplitCount(String paramTableIdx) {
        int mSubTableCount = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select count(idx) from salon_store_restaurant_table_split where tableidx like '%" + paramTableIdx + "%' "
        ));

        return mSubTableCount;
    }

    public static int getTablePeopleCntByHoldCode(String paramHoldCode) {
        int mTablePeopleCnt = 0;
        if (!GlobalMemberValues.isStrEmpty(paramHoldCode)) {
            mTablePeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where holdcode = '" + paramHoldCode + "' "
            ));
        }
        return mTablePeopleCnt;
    }

    public static int getTablePeopleCntByTableIdx(String paramTableIdx) {
        GlobalMemberValues.logWrite("tbdtinfo", "paramTableIdx : " + paramTableIdx + "\n");

        int mTablePeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where tableidx like '%" + paramTableIdx + "%' "
        ));

        return mTablePeopleCnt;
    }

    public static void setCloseActivity(boolean paramInitTableIdx) {
        if (paramInitTableIdx) {
            mTableIdxArrList = null;
            // Restaurant 관련 ---------------------------
            setInitValuesForRestaurant();
            // -------------------------------------------

            setMainTopButtonLn(true);
        } else {
            GlobalMemberValues.now_saletypeisrestaurant = true;

            setTableNameValues(mTableIdxArrList.get(0));

            // 먼저 holdcode 를 구한다.
            String temp_holdcode = "";
            if (!GlobalMemberValues.isStrEmpty(selectedSubTableHoldCode)) {
                temp_holdcode = selectedSubTableHoldCode;
            } else {
                temp_holdcode = getHoldCodeByTableidx(mTableIdxArrList.get(0).toString(), TableSaleMain.mSubTableNum);
            }

            if (GlobalMemberValues.isQSRPOSonRestaurantPOS){
                if (temp_holdcode.isEmpty()){
                    temp_holdcode = getHoldCodeByTableidx_byQSR(mTableIdxArrList.get(0).toString(), TableSaleMain.mSubTableNum);
                }
            }

            // 06.02.2022 ------------------------------------------------------------------
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCode_byRepay)) {
                temp_holdcode = GlobalMemberValues.mHoldCode_byRepay;
            }
            // -----------------------------------------------------------------------------

//            GlobalMemberValues.logWrite("jjjsaletablelog", "temp_holdcode : " + temp_holdcode + "\n");
            setOrderOfTable(temp_holdcode);
//            GlobalMemberValues.logWrite("jjjsaletablelog", "2. MainMiddleService.mHoldCode : " + MainMiddleService.mHoldCode + "\n");

            setMainTopButtonLn(false);
        }
        selectedSubTableHoldCode = "";

        // bill 관련 ----------------------------------------------------------------------------------------
        if (GlobalMemberValues.isPaymentByBills) {                  // bill pay 일 때
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.setText(
                    GlobalMemberValues.getCommaStringForDouble(GlobalMemberValues.mPayAmountOnBill + "")
            );

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTOFTEXTVIEW.setVisibility(View.VISIBLE);

            // bill 과 관련없는 버튼들은 보이지 않도록 처리
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.GONE);

            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_QUICK.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.INVISIBLE);
            if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y")){
                GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.VISIBLE);
            } else {
                GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.GONE);
            }
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGOUT.setVisibility(View.INVISIBLE);
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GC_BALANCE.setVisibility(View.INVISIBLE);

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setVisibility(View.INVISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setVisibility(View.INVISIBLE);

            // payment 프레임 보여주기
            Payment payment = new Payment(MainActivity.mActivity, MainActivity.mContext, MainActivity.dataAtSqlite);
            payment.setPaymentView();
        } else if (GlobalMemberValues.is_billprint_in_payment){

            Payment payment = new Payment(MainActivity.mActivity, MainActivity.mContext, MainActivity.dataAtSqlite);
            payment.setPaymentView();

            GlobalMemberValues.is_billprint_in_payment = false;

        } else {                                                    // bill pay 아닐 때
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTTEXTVIEW.setText("");

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_BILLAMOUNTOFTEXTVIEW.setVisibility(View.GONE);

            // bill 과 관련없는 버튼들은 보이지 않도록 처리
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);

            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_DISCOUNT.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_QUICK.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_PRODUCT.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GIFTCARD.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.VISIBLE);
            if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y")){
                GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.VISIBLE);
            } else {
                GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_TIMEMENU.setVisibility(View.GONE);
            }

            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_MENUSEARCH.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_LOGOUT.setVisibility(View.VISIBLE);
//            GlobalMemberValues.GLOBAL_BUTTON_MAIN_SIDE_GC_BALANCE.setVisibility(View.VISIBLE);

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setVisibility(View.VISIBLE);


            // payment 프레임 감추기
            GlobalMemberValues.setFrameLayoutVisibleChange("main");
        }
        // -------------------------------------------------------------------------------------------------

        closeTableSaleMain();
    }

    public static void setMainTopButtonLn(boolean paramIsSaleMode) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // runOnUiThread를 추가하고 그 안에 UI작업을 한다.
                ((TableSaleMain)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (paramIsSaleMode) {
                            if (GlobalMemberValues.GLOBAL_MAINTOPLN1 != null
                                    && GlobalMemberValues.GLOBAL_MAINTOPLN2 != null) {
//                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.GONE);
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2_sizeup);
//                AppTopBar.btn_table_sale.setVisibility(View.VISIBLE);
                                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.VISIBLE);
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
                                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
                            }
                        } else {
                            if (GlobalMemberValues.GLOBAL_MAINTOPLN1 != null
                                    && GlobalMemberValues.GLOBAL_MAINTOPLN2 != null) {
                                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.GONE);
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
                                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
                                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
//                AppTopBar.btn_table_sale.setVisibility(View.GONE);
                            }
                        }

                    }
                });
            }
        }).start();

//        if (paramIsSaleMode) {
//            if (GlobalMemberValues.GLOBAL_MAINTOPLN1 != null
//                    && GlobalMemberValues.GLOBAL_MAINTOPLN2 != null) {
////                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.VISIBLE);
////                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.GONE);
////                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2_sizeup);
////                AppTopBar.btn_table_sale.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.GONE);
//                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
//            }
//        } else {
//            if (GlobalMemberValues.GLOBAL_MAINTOPLN1 != null
//                    && GlobalMemberValues.GLOBAL_MAINTOPLN2 != null) {
//                GlobalMemberValues.GLOBAL_MAINTOPLN1.setVisibility(View.GONE);
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_MAINTOPLN2.setVisibility(View.VISIBLE);
//                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONPAYMENT.setBackgroundResource(R.drawable.ab_imagebutton_main_payment2);
////                AppTopBar.btn_table_sale.setVisibility(View.GONE);
//            }
//        }
    }

    public static void setTableNameValues(String paramTableIdx) {
        // Restaurant 관련 데이터 설정 --------------------------------------
        String tableidx = paramTableIdx;

        String mergednum = "0";
        String subtablenum = "1";

        String strQuery = "select top 1 mergednum, subtablenum from temp_salecart " +
                " where tableidx like '%" + tableidx + "%' " +
                " order by idx desc";
        ResultSet tempCursor = MssqlDatabase.getResultSetValue(strQuery);
        int tempCursor_count = 0;
        boolean tempCursor_isBeforeFirst = false;
        try {
            tempCursor.last();
            tempCursor_count = tempCursor.getRow();
            tempCursor.beforeFirst();
            tempCursor_isBeforeFirst = tempCursor.isBeforeFirst();

            if (tempCursor_count > 0 && tempCursor_isBeforeFirst) {
                mergednum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,0), 1);
                subtablenum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCursor,1), 1);

                if (GlobalMemberValues.isStrEmpty(mergednum)) {
                    mergednum = "0";
                }
                if (GlobalMemberValues.isStrEmpty(subtablenum)) {
                    subtablenum = "1";
                }
            }
            tempCursor.close();
        }catch (Exception e){

        }


        String tempTableIdx = GlobalMemberValues.getReplaceText(tableidx, "T", "");
        tempTableIdx = GlobalMemberValues.getReplaceText(tempTableIdx, "[", "");
        tempTableIdx = GlobalMemberValues.getReplaceText(tempTableIdx, "]", "");
        String tableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select tablename from salon_store_restaurant_table where idx = '" + tempTableIdx + "' ");

        String temp_getHoldCodeByTableidx = getHoldCodeByTableidx(tableidx,TableSaleMain.mSubTableNum);
        int temp_getTableCountByHoldcode = getTableCountByHoldcode(temp_getHoldCodeByTableidx);
        int temp_getTableSplitCount = getTableSplitCount(tableidx);

        String temp_tablename = "";
        if (GlobalMemberValues.s_str_tableinfo != null && GlobalMemberValues.s_str_tableinfo.length > 0) {
            String[] tablearr = null;
            int zonei = 0;
            for (int i = 0; i < GlobalMemberValues.s_str_tableinfo.length ; i++){
                String[] temp_table_info = GlobalMemberValues.s_str_tableinfo[i].split("-JJJ-");
                String temp_table_idx = temp_table_info[0];
                if (temp_table_idx.equals(tempTableIdx)){
                    temp_tablename = temp_table_info[1];
                }
            }
//            tablearr = GlobalMemberValues.s_str_tableinfo[GlobalMemberValues.getIntAtString(tempTableIdx) - 1].split("-JJJ-");
//            temp_tablename = tablearr[1];
        }

        if (getTableCountByHoldcode(getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum)) == 1) {
            if (getTableSplitCount(tableidx) > 1) {
                if (tableName.isEmpty()){
                    tableName = temp_tablename + " (S - " + mSubTableNum + ")";
                } else {
                    tableName = tableName + "-" + subtablenum;
                }
            } else {
                tableName = temp_tablename;
            }
        } else {
            if (tableName.isEmpty()){
                String mergednumstr = "0" + mergednum;
                mergednumstr = "M-" + mergednumstr.substring((mergednumstr.length() - 2), mergednumstr.length());
                tableName = mergednumstr;
            }
        }

        mTableName = tableName;
        // ------------------------------------------------------------------
    }

    public static void setInitValuesForRestaurant() {
        GlobalMemberValues.now_saletypeisrestaurant = false;
    }

    public static void setInitValues() {
        if (!GlobalMemberValues.isShowQuickMenusInTableBoard && mSelectedTablesArrList != null) {
            setClearSelectedTableIdx(true);
        }

        setInitValues2();
    }

    public static void setInitValues2() {
        mTableIdxArrList = null;

        mSelectedIdxArrListInCart = null;
        GlobalMemberValues.mCancelKitchenPrinting = "N";

        if (GlobalMemberValues.isQSRPOSonRestaurantPOS){

        } else {
            GlobalMemberValues.mSelectedTableIdx = "";
        }

        GlobalMemberValues.mDeletedSaleCartIdx = "";
    }

    public static void setInitValuesSub() {
        mSubTableNum = "1";
        mMergedNum = "0";
        mTableName = "";
    }

    public void setInitOnFocus() {
        // 크기 가져오기
        setTablePanSize();

        GlobalMemberValues.logWrite("selecttbljjj", "여기 : " + mSelectedTablesArrList.toString() + "\n");
        GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "값 : " + mSelectedTablesArrList.toString() + "\n");

        //viewTableSettigns(mSelectedZoneIdx);
        if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
            GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "여기 오면 안되는데요!" + "\n");
            setClearSelectedTableIdx(true);
        }
        doCellSelectEvent();
    }

    @Override
    public void onResume() {
        super.onResume();

        // 10202024 -----------------------------------
        GlobalMemberValues.is_nowsalemain = false;
        // 10202024 -----------------------------------

        // 04302024
        tablesalemainactivity = (LinearLayout)findViewById(R.id.tablesalemainactivity);
        if (GlobalMemberValues.isQSRPOSonRestaurantPOS) {
            tablesalemainactivity.setVisibility(View.GONE);
        } else {
            tablesalemainactivity.setVisibility(View.VISIBLE);
        }


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

        GlobalMemberValues.mToGoType = "";

        GlobalMemberValues.isOpenTableSaleMain = false;

        GlobalMemberValues.mIsClickSendToKitchen = false;
        GlobalMemberValues.mIsClickPayment = false;

        GlobalMemberValues.mTempCustomerInfo = "";

        TableSaleMain.isClickCommandOnTable = false;

        GlobalMemberValues.isKitchenReprinting = "N";

//        if (mSelectedZoneIdx != null) {
//            // viewTableSettigns(mSelectedZoneIdx);
//            GlobalMemberValues.logWrite("newcartchecklogjjj", "JJJ 여기에..." + "\n");
//            setContent();
//        }
        //setInitValues();

        GlobalMemberValues.mCardSalesIdxForBillPay = "";

        // 06.07.2022 ---------------------------------------
        GlobalMemberValues.isRepay = false;
        GlobalMemberValues.isRepay2 = false;
        GlobalMemberValues.isOpenPayment = false;
        GlobalMemberValues.mTableIdx_byRepay = "";
        GlobalMemberValues.mHoldCode_byRepay = "";
        GlobalMemberValues.mPeopleCnt_byRepay = 0;

        GlobalMemberValues.mOnVoidForPartial = false;
        // --------------------------------------------------
        if (TableSaleBillPrint.mActivity != null && !TableSaleBillPrint.mActivity.isFinishing()) {
            TableSaleBillPrint.mActivity.finish();
        }



        // 05202024
        if (GlobalMemberValues.isTOrderUse()) {
            GlobalMemberValues.SAVEORDELETE = "del";
            GlobalMemberValues.setTableIdxInCloud(mContext, mActivity);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        GlobalMemberValues.isOpenTableSaleMain = false;

        if (hasFocus) {
            setInitOnFocus();
            if (isFocusViewTable) {
                viewTableSettigns(mSelectedZoneIdx);
                isFocusViewTable = false;
            }
        }

        GlobalMemberValues.logWrite("jjjfocuslogjjj", "mTempCount : " + mTempCount + "\n");
        if (mTempCount == 0) {
            viewTableSettigns(mSelectedZoneIdx);
        }
        mTempCount++;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9898){
            if (data == null) return;
            if (data.getExtras().getString("result").equals("pass_ok")){
                if ((mActivity != null) && (!mActivity.isFinishing())) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Table Clear")
                            .setMessage("Do you want to empty all tables?" +
                                    "\nCancellation details are not printed in the kitchen" +
                                    "\nTouch [TABLE CLEAR] to print the cancellation history to the kitchen")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            allTablesClear();

                                            mAllTablesArrList.clear();

                                            setInitValues();

                                            viewTableSettigns(mSelectedZoneIdx);
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    setClearSelectedTableIdx(true);
                                }
                            })
                            .show();
                }
            }
        }
    }

    public void setTableView_etc_Block_make(String boxkinds, String boxtype, double xvalue, double yvalue){
        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout table_row3 = (LinearLayout)mInflater.inflate(R.layout.table_main_dummy, table_main_view, false);


        ImageView table_icon_iv = table_row3.findViewById(R.id.table_dummy_imageview);


        table_row3.setX((float)xvalue);
        table_row3.setY((float)yvalue);


        int lnWidth = 180;
        int lnHeight = 200;

        lnWidth = main_100_width / 13;
        lnHeight = main_100_height / 8;

        LinearLayout.LayoutParams tableLnParams
                = new LinearLayout.LayoutParams(lnWidth, lnHeight);
        LinearLayout toptableLn = table_row3.findViewById(R.id.toptableLn);
        toptableLn.setLayoutParams(tableLnParams);

        switch (boxkinds) {
            case "wall" : {
                switch (boxtype) {
                    case "0" : {
                        table_row3.setHorizontalGravity(LinearLayout.HORIZONTAL);
                        table_icon_iv.setImageResource(R.drawable.wall_01);
                    }
                    break;
                    case "1" : {
                        table_row3.setHorizontalGravity(LinearLayout.VERTICAL);
                        table_icon_iv.setImageResource(R.drawable.wall_02);
                    }
                    break;
                    case "2" : {
                        table_row3.setHorizontalGravity(LinearLayout.HORIZONTAL);
                        table_icon_iv.setImageResource(R.drawable.wall_03);
                    }
                    break;
                    case "3" : {
                        table_row3.setHorizontalGravity(LinearLayout.VERTICAL);
                        table_icon_iv.setImageResource(R.drawable.wall_04);
                    }
                    break;
                }
                break;
            }
            case "hallway" : {
                switch (boxtype) {
                    case "0" : {
                        table_icon_iv.setImageResource(R.drawable.hallway_01);
                    }
                    break;
                    case "1" : {
                        table_icon_iv.setImageResource(R.drawable.hallway_02);
                    }
                    break;
                    case "2" : {
                        table_icon_iv.setImageResource(R.drawable.hallway_03);
                    }
                    break;
                    case "3" : {
                        table_icon_iv.setImageResource(R.drawable.hallway_04);
                    }
                    break;
                }
                break;
            }
            case "door" : {
                switch (boxtype) {
                    case "0" : {
                        table_icon_iv.setImageResource(R.drawable.door_01);
                    }
                    break;
                    case "1" : {
                        table_icon_iv.setImageResource(R.drawable.door_02);
                    }
                    break;
                    case "2" : {
                        table_icon_iv.setImageResource(R.drawable.door_03);
                    }
                    break;
                    case "3" : {
                        table_icon_iv.setImageResource(R.drawable.door_04);
                    }
                    break;
                }
                break;
            }
            case "etc" : {
                switch (boxtype) {
                    case "0" : {
                        table_icon_iv.setImageResource(R.drawable.etc_01);
                    }
                    break;
                    case "1" : {
                        table_icon_iv.setImageResource(R.drawable.etc_02);
                    }
                    break;
                    case "2" : {
                        table_icon_iv.setImageResource(R.drawable.etc_03);
                    }
                    break;
                    case "3" : {
                        table_icon_iv.setImageResource(R.drawable.etc_04);
                    }
                    break;
                }
                break;
            }
            case "window" : {
                switch (boxtype) {
                    case "0" : {
                        table_icon_iv.setImageResource(R.drawable.windows_01);
                    }
                    break;
                    case "1" : {
                        table_icon_iv.setImageResource(R.drawable.windows_02);
                    }
                    break;
                    case "2" : {
                        table_icon_iv.setImageResource(R.drawable.windows_03);
                    }
                    break;
                    case "3" : {
                        table_icon_iv.setImageResource(R.drawable.windows_04);
                    }
                    break;
                }
                break;
            }
        }
        if (table_main_view != null && table_row3 != null) {
            table_main_view.addView(table_row3);
        }
    }

    public static void setClearSelectedTableIdx(boolean paramBool) {
        if (paramBool) {
            GlobalMemberValues.logWrite("mSelectedTablesArrListLog", "여기도 오면 안되는데요!!!!!!!!!!!!!" + "\n");
            if (mSelectedTablesArrList != null){
                if (GlobalMemberValues.is_customerMain){

                } else {
                    TableSaleMain.mSelectedTablesArrList.clear();
                }

            }
        }
    }

    public static void closeTableSaleMain() {
        GlobalMemberValues.isOpenTableSaleMain = false;
        mActivity.finish();
    }

    public void setTableTwoSplit(String tempTableIdx) {
        if (tempTableIdx != null) {
            Vector<String> updateVector = new Vector<String>();
            String strQuery = "";
            strQuery = "select holdcode from salon_store_restaurant_table_merge where tableidx like '" + tempTableIdx + "%' ";
            String str_Holdcode = MainActivity.mDbInit.dbExecuteReadReturnString(
                    strQuery
            );
            Cursor cur_merged_tableidx = MainActivity.mDbInit.dbExecuteRead("select tableidx from salon_store_restaurant_table_merge where holdcode like '" + str_Holdcode + "%' ");
            while (cur_merged_tableidx.moveToNext()) {
                String str_merged_tableidx = MainActivity.mDbInit.dbExecuteReadReturnString("select holdcode from salon_store_restaurant_table_merge where tableidx like '" + GlobalMemberValues.getDBTextAfterChecked(cur_merged_tableidx.getString(0), 1) + "%' ");
                Log.e("jihun test",str_merged_tableidx + " 머지된 테이블 IDX");
            }
            cur_merged_tableidx.close();

//            "select tableidx from salon_store_restaurant_table_merge where holdcode like '" + "02252021R7N43H1YGWK14K3" + "%' ";




//            for (String tempTableIdx : mSelectedTablesArrList) {
//                if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
//                    String newHoldCode = GlobalMemberValues.makeHoldCode();
//
//                    strQuery = " update temp_salecart set holdcode = '" + newHoldCode + "', mergednum = '0', isCloudUpload = 0  " +
//                            " where tableidx like '%" + tempTableIdx + "%' ";
//                    updateVector.addElement(strQuery);
//
//                    // salon_store_restaurant_table_peoplecnt 테이블 관련 ---------------------------------------------------
//                    int tempPeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
//                            " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where tableidx like '%" + tempTableIdx + "%' "
//                    ));
//
//                    strQuery = " delete from salon_store_restaurant_table_peoplecnt where tableidx like '%" + tempTableIdx + "%' ";
//                    updateVector.addElement(strQuery);
//
//                    strQuery = " delete from salon_store_restaurant_table_merge where tableidx like '%" + tempTableIdx + "%' ";
//                    updateVector.addElement(strQuery);
//
//                    strQuery = " insert into salon_store_restaurant_table_peoplecnt " +
//                            " (tableidx, holdcode, peoplecnt) values ( " +
//                            "'" + tempTableIdx + "', " +
//                            "'" + newHoldCode + "', " +
//                            "'" + tempPeopleCnt + "' " +
//                            " )";
//                    updateVector.addElement(strQuery);
//                    // ----------------------------------------------------------------------------------------------------
//                }
//            }
//            if (updateVector.size() > 0) {
//                for (String tempQuery : updateVector) {
//                    //GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
//                }
//                // 트랜잭션으로 DB 처리한다.
//                String returnResult = MssqlDatabase.executeTransaction(updateVector);
//                if (returnResult == "N" || returnResult == "") {
//                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
//                    return;
//                } else {
//                    //GlobalMemberValues.logWrite("mergetablelog", "mSelectedZoneIdx : " + mSelectedZoneIdx + "\n");
//                    viewTableSettigns(mSelectedZoneIdx);
//
//                    setInitValues();
//
//                    // 클라우드 업로드
//                    uploadTableDataCloudExe();
//                }
//            }
//        } else {
//            String getTableIdx = mSelectedTablesArrList.get(0);
//            if (!GlobalMemberValues.isStrEmpty(getTableIdx)) {
//                int tableidxCnt = getTableCountByHoldcode(getHoldCodeByTableidx(getTableIdx, TableSaleMain.mSubTableNum));
//
//                if (tableidxCnt > 1) {
//                    GlobalMemberValues.displayDialog(mContext, "Warning", "Please split the merged tables first", "Close");
//                } else {
//                    openSplit();
//                }
//            }
        }
    }

//    View.OnDragListener mDragListener = new View.OnDragListener() {
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//
//            // TODO Auto-generated method stub
//
//            LinearLayout table_row;
//
//            //드래그 객체가 버튼인지 확인
//
//            if(v instanceof LinearLayout){
//
//                table_row = (LinearLayout)v;
//
//            }else{
//
//                return false;
//
//            }
//            LinearLayout table_row_temp = (LinearLayout)event.getLocalState();
////            if (table_row == table_row_temp) {
////                // 같은 테이블 위로 드랍함.
////
////                return true;
////            }
//
//
//
//
//
//            //이벤트를 받음
//
//            switch(event.getAction()){
//
//                //드래그가 시작되면
//
//                case DragEvent.ACTION_DRAG_STARTED:
//
//                    //클립 설명이 텍스트면
//
//                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
//
////                        btn.setText("Drop OK");//버튼의 글자를 바꿈
//
//                        return true;
//
//                    }else{//인텐트의 경우 이쪽으로 와서 드래그를 받을 수가 없다.
//
//                        return false;
//
//                    }
//
//                    //드래그가 뷰의 경계안으로 들어오면
//
//                case DragEvent.ACTION_DRAG_ENTERED:
//
////                    btn.setText("Enter");//버튼 글자 바꿈
//
//                    return true;
//
//                //드래그가 뷰의 경계밖을 나가면
//
//                case DragEvent.ACTION_DRAG_EXITED:
//
////                    btn.setText("Exit");//버튼 글자 바꿈
//
//                    return true;
//
//                //드래그가 드롭되면
//
//                case DragEvent.ACTION_DROP:
//
//                    setClearSelectedTableIdx(true);
//
//                    String text = event.getClipData().getItemAt(0).getText().toString();
//
//                    //
//                    if (table_row == table_row_temp) {
//
//                        // 같은 테이블 위로 드랍함.
//                        // table info 보여줌.
//                        String tableidx = (String)table_row.getTag();
//                        int mSubTableCount = getTableSplitCount(tableidx);
//                        Double mTableAmount = 0.0;
//                        String tableAmount = "";
//
//                        if (mSubTableCount > 0 && mSubTableCount > 1) {         // Table split 이 되어 있는 경우
//                            for (int wj = 0; wj < mSubTableCount; wj++) {
//                                String temp_holdcode = MssqlDatabase.getResultSetValueToString(
//                                        " select holdcode from salon_store_restaurant_table_split " +
//                                                " where tableidx like '%" + tableidx + "%' and subtablenum = '" + (wj + 1) + "' "
//                                );
//
//                                if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
//                                    mTableAmount += GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
//                                            " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
//                                    ));
//                                }
//                            }
//                        } else {                                                 // Table split 이 아닌 경우
//                            String temp_holdcode = getHoldCodeByTableidx(tableidx, TableSaleMain.mSubTableNum);
////                    GlobalMemberValues.logWrite("strQueryLog", "query : " + " select holdcode from temp_salecart where tableidx like '%" + tableidx + "%' " + "\n");
//
//                            if (!GlobalMemberValues.isStrEmpty(temp_holdcode)) {
//                                mTableAmount = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
//                                        " select sum(sTotalAmount) from temp_salecart where holdcode = '" + temp_holdcode + "' "
//                                ));
//                            }
//                        }
//
//
//                        tableAmount = ("$" + GlobalMemberValues.getCommaStringForDouble(mTableAmount + ""));
//
//                        int peopleCnt = getTablePeopleCntByTableIdx(tableidx);
//
//                        Intent pushIntent = new Intent(mContext, TableInfoPopup.class);
//                        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//
//                        pushIntent.putExtra("table_info_name", tableidx);
//                        pushIntent.putExtra("table_info_capacity", peopleCnt + "");
////                pushIntent.putExtra("table_info_menu", webOrdersCustomerName);
//                        pushIntent.putExtra("table_info_menu_total", tableAmount);
//
//                        startActivity(pushIntent);
//                        return true;
//                    }
//
//
//                    //
//
//                    if (table_row_temp != null && table_row != null){
//                        String touchup_table_idx = (String)table_row_temp.getTag();
//                        String drop_target_table_idx = (String)table_row.getTag();
//
//                        mSelectedTablesArrList.add(touchup_table_idx);
//                        mSelectedTablesArrList.add(drop_target_table_idx);
//
//
//                        final List<String> ListItems = new ArrayList<>();
//                        ListItems.add("Table Merge");
//                        ListItems.add("Table Split");
//                        ListItems.add("Move Table");
//                        ListItems.add("Close");
//                        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(TableSaleMain.this);
//                        builder.setTitle(touchup_table_idx + " & " +drop_target_table_idx);
//                        builder.setItems(items, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int pos) {
//                                String selectedText = items[pos].toString();
//                                Toast.makeText(TableSaleMain.this, selectedText, Toast.LENGTH_SHORT).show();
//
//                                switch (pos){
//                                    case 0 :
//                                        // merge
//                                        if ((mActivity != null) && (!mActivity.isFinishing())) {
//                                            // 선택된 테이블이 1개이고, 선택된 테이블이 Merge 된 테이블이면 다시 merge 하지 못한다.
//                                            if (mSelectedTablesArrList.size() == 1 && getTableCountByTableidxInMergedTable(mSelectedTablesArrList.get(0)) > 0) {
//                                                GlobalMemberValues.displayDialog(mContext, "Warning", "Merged tables cannot be merged again.", "Close");
//                                                setInitValues();
//                                                return;
//                                            }
//
//                                            // 선택된 테이블이 1개이고, 선택된 테이블이 split 된 테이블이 아닌 일반 테이블이면 merge 하지 못한다.
//                                            if (mSelectedTablesArrList.size() == 1 && getTableSplitCount(mSelectedTablesArrList.get(0)) == 0) {
//                                                GlobalMemberValues.displayDialog(mContext, "Warning", "Please select 2 or more tables to merge.", "Close");
//                                                setInitValues();
//                                                return;
//                                            }
//
//                                            new AlertDialog.Builder(mActivity)
//                                                    .setTitle("Table Merge")
//                                                    .setMessage("Do you want to merge the selected tables?")
//                                                    //.setIcon(R.drawable.ic_launcher)
//                                                    .setPositiveButton("Yes",
//                                                            new DialogInterface.OnClickListener() {
//                                                                public void onClick(DialogInterface dialog, int which) {
//                                                                    setTableMerge();
//                                                                }
//                                                            })
//                                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            //
//                                                        }
//                                                    })
//                                                    .show();
//                                        }
//                                        break;
//                                    case 1 :
//                                        // split
//                                        if (mSelectedTablesArrList.size() == 1 && getTableCountByTableidxInMergedTable(mSelectedTablesArrList.get(0)) > 0) {
//                                            GlobalMemberValues.displayDialog(mContext, "Warning", "Select all merged tables if the table is already joined", "Close");
//                                            setInitValues();
//                                            return;
//                                        }
//                                        setTableSplit();
//                                        break;
//                                    case 2 :
//                                        // move
//                                        checkTransferTable();
//                                        break;
//                                }
//                            }
//                        });
//                        builder.show();
//                    }
//
//                    return true;
//
//                //드래그 성공 취소 여부에 상관없이 모든뷰에게
//
//                case DragEvent.ACTION_DRAG_ENDED:
//
//                    if(event.getResult()){//드래그 성공시
//
//                    }else{//드래그 실패시
//
//                    }
//
//                    return true;
//
//            }
//
//            return true;
//
//        }
//
//    };


    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        public void onAnimationEnd(Animation animation){
            if (i_anim_kind == 0){
                if(isPageOpen){
                    table_main_side_scrollview.setVisibility(View.INVISIBLE);
                    isPageOpen = false;
                }
                else{
                    isPageOpen = true;
                }
            } else {

            }
//            table_main_side_scrollview.clearAnimation();
        }
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class SlidingTogoViewAnimationListener implements Animation.AnimationListener{

        public void onAnimationEnd(Animation animation){
            viewTableSettignsForQuick();
            table_main_grid_relative_view_loading.setVisibility(View.INVISIBLE);

        }
        @Override
        public void onAnimationStart(Animation animation) {
            table_main_grid_relative_view_loading.setVisibility(View.VISIBLE);
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
        if (table_main_grid_relative_view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)) {
            int scrcoords[] = new int[2];
            table_main_grid_relative_view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + table_main_grid_relative_view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + table_main_grid_relative_view.getTop() - scrcoords[1];
            if (x < table_main_grid_relative_view.getLeft() || x > table_main_grid_relative_view.getRight() || y < table_main_grid_relative_view.getTop() || y > table_main_grid_relative_view.getBottom()){
                if (table_main_grid_relative_view.getVisibility() == View.VISIBLE){
//                    quick_table_popup_btn.setSelected(false);
                    table_main_grid_relative_view.setVisibility(View.GONE);
                    table_main_grid_relative_view.startAnimation(Quick_LeftAnim);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public TextView timeupdate_in_textview(TextView textView, String date1){

//        String dateString = "2023-05-11 15:29:04.663";
        if (date1.isEmpty()) {
            textView.setText("");
            return textView;
        }
        SimpleDateFormat sdf_old = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        Date date = null;
        try {
            date = sdf_old.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            // 파싱 실패시.
            textView.setText("");
            return textView;
        }
        long timestamp = date.getTime();
        Date date_old = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        String formattedTime = sdf.format(date_old);
        textView.setText(formattedTime);
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                while (true){
//
//                    try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                        Date date = new Date();
//                        String nowDate = simpleDateFormat.format(date);
////                    //현재시간 구하기 (시작 시간)
////
//                        Date inputDate = simpleDateFormat.parse(nowDate);
//                        Date currDate = simpleDateFormat.parse(date1);
//                        // 시작 시간, 종료 시간 파싱해서 계산할 수 있게 바꿈
//
//                        long hour = (inputDate.getTime() - currDate.getTime()) / (1000) / 3600 % 24;
//                        long min = (inputDate.getTime() - currDate.getTime()) / (1000) / 60 % 60;
//
//                        String s_hour = "", s_min = "", s_second = "";
//                        if (hour < 10){
//                            s_hour = "0" + hour;
//                        } else s_hour = "" + hour;
//                        if (min < 10){
//                            s_min = "0" + min;
//                        } else s_min = "" + min;
//                        // (시작 시간 - 종료시간)  시간 차이나는 부분을 시, 분, 초로 얻음
//
//                        String finalS_second = s_second;
//                        String finalS_min = s_min;
//                        String finalS_hour = s_hour;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView.setText(finalS_hour +":"+ finalS_min);
//                            }
//                        });
//                    }
//                    catch(ParseException e) {
//                        // 예외 처리
//                    }
//
//                    try {
//                        sleep(60000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        thread.setDaemon(true);
//        thread.start();



        return textView;
    }

    /** Override 메소드 ****************************************************************************/
    @Override
    //종료처리시 종료할지 물어보기 추가
    public void onBackPressed() {
        //GlobalMemberValues.setCloseAndroidApp(mActivity);
    }
    /**********************************************************************************************/




    // 10092023
    public static void setGratuity(String paramHoldCode) {
        boolean isMMSHoldCode = false;
        if (!GlobalMemberValues.isStrEmpty(MainMiddleService.mHoldCode) && MainMiddleService.mHoldCode.equals("NOHOLDCODE")) {
            isMMSHoldCode = true;
        }

        GlobalMemberValues.logWrite("setGratuityjjjlog", "paramHoldCode : " + paramHoldCode + "\n");

        MainMiddleService.mHoldCode = paramHoldCode;
        isAfterMerge = true;
        // common gratuity 관련
        // 먼저 DB 에서 삭제한다.
        String strQuery = " delete from temp_salecart where " +
                " holdcode = '" + paramHoldCode + "' " +
                " and svcName = '" + GlobalMemberValues.mCommonGratuityName + "' ";
        MssqlDatabase.executeTransactionByQuery(strQuery);
        GlobalMemberValues.addCartLastItemForCommonGratuityUse();
        if (isMMSHoldCode) {
            MainMiddleService.mHoldCode = "NOHOLDCODE";
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String getHoldCodeByTableidx_byQSR(String paramTableIdx, String paramSubTableNum) {
        if (GlobalMemberValues.isStrEmpty(paramSubTableNum)) {
            paramSubTableNum = "1";
        }
        paramTableIdx = paramTableIdx.replace("TT","T");
        String temp_holdcode = MssqlDatabase.getResultSetValueToString(
                "SELECT distinct holdcode FROM temp_salecart WHERE holdcode LIKE '%"+paramTableIdx+"%' and subtablenum = '" + paramSubTableNum + "' "
        );

        return temp_holdcode;
    }

}


