package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class AppTopBar {
    static Activity mActivity;
    static Context context;
    GetDataAtSQLite dataAtSqlite;

    public static Context insContext;

    LinearLayout parentLn;

    // DB 객체 선언
    DatabaseInit dbInit;

    CustomDigitalClock customDigitalClock;
    TextView topSalonNameLogo;
    TextView main_selected_table;
    Button topSalonRealTimeWebOrdersButton;

    private ImageView topReservationSmallIncoImageView;
    private ImageView topGiftCardSmallIncoImageView;
    private ImageView maintoplogoimage;

    public static ImageButton btn_table_sale;

    public AppTopBar(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        setContent();
    }

    public void setContent() {
        parentLn = GlobalMemberValues.GLOBAL_APPTOPBAR;
        // 디지털 시계
        customDigitalClock = (CustomDigitalClock)parentLn.findViewWithTag("customDigitalClockTag");
        if (GlobalMemberValues.mDeviceTabletPC) {
            customDigitalClock.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        } else {
            customDigitalClock.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
        }


        // Time Menu 타임메뉴 시간대 선택창 오픈 버튼
        topReservationSmallIncoImageView = (ImageView)parentLn.findViewWithTag("topReservationSmallIncoImageViewTag");
        topReservationSmallIncoImageView
                .setOnClickListener(new JJJ_OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
                            GlobalMemberValues.setNowTimeCodeValue();
                            GlobalMemberValues.openTimeMenuSelectPopup();
                        } else {
                            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                                    "Please change whether to use the time menu in the [ Settings > System ] to 'Active'", "Close");
                        }
                    }
                });

        // 기프트카드 잔액확인 바로가기 버튼
        topGiftCardSmallIncoImageView = (ImageView)parentLn.findViewWithTag("topGiftCardSmallIncoImageViewTag");
        topGiftCardSmallIncoImageView.setOnClickListener(new JJJ_OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent GiftCardBalanceCheck = new Intent(MainActivity.mContext, com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GiftCardBalanceCheck.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                // -------------------------------------------------------------------------------------
                mActivity.startActivity(GiftCardBalanceCheck);

                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
            }
        });

        // 기프트카드 잔액확인 바로가기 버튼
        maintoplogoimage = (ImageView)parentLn.findViewWithTag("maintoplogoimageTag");
        if (GlobalMemberValues.CLOUD_SERVER_URL_BASIC.indexOf("2go2go") != -1) {
            maintoplogoimage.setImageResource(R.drawable.aa_images_salonposlogo_2go2go);
        }

        // jihun park table sale 진입버튼
//        btn_table_sale = (ImageButton)parentLn.findViewById(R.id.main_goto_tablesale);
//        btn_table_sale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //CommandButton.setHoldSales("");
//
//                Intent intent = new Intent(MainActivity.mContext, TableSaleMain.class);
//                mActivity.startActivity(intent);
//                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
//            }
//        });
        GlobalMemberValues globalMemberValues = new GlobalMemberValues();
//        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
//            if (!GlobalMemberValues.now_saletypeisrestaurant) {
//                btn_table_sale.setVisibility(View.VISIBLE);
//            } else {
//                btn_table_sale.setVisibility(View.GONE);
//            }
//        } else {
//            btn_table_sale.setVisibility(View.GONE);
//        }

        // Real Time WebOrders 버튼
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON = (Button) parentLn.findViewWithTag("topSalonRealTimeWebOrdersButtonTag");
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setText("PUSH");
        // jihun 073120
//        topSalonRealTimeWebOrdersButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 12
//                        * GlobalMemberValues.getGlobalFontSize());
        // jihun 0812 크기 고정
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setTextSize(16);
//        topSalonRealTimeWebOrdersButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
//                topSalonRealTimeWebOrdersButton.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize());
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setTextColor(Color.parseColor("#d9dbe4"));
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setBackgroundResource(R.drawable.ab_imagebutton_realtimeweborders_qorders);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setOnClickListener(new JJJ_OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent realTimeWebOrdersIntent = new Intent(MainActivity.mContext, RealTimeWebOrders.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                // -------------------------------------------------------------------------------------
                mActivity.startActivity(realTimeWebOrdersIntent);

                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
                }
            }
        });

        if (GlobalMemberValues.isOnlineOrderUseYN()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setVisibility(View.VISIBLE);
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.setVisibility(View.GONE);
        }

        // 살롱이름 / 로고
        topSalonNameLogo = (TextView)parentLn.findViewWithTag("topSalonNameLogoTag");

        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.SALON_LOGOIMAGE)) {
            // 이미지일 경우
        } else {
//            topSalonNameLogo.setText("" + GlobalMemberValues.SALON_NAME + "");
            if (GlobalMemberValues.getStoreNameEN().equals("")){
                topSalonNameLogo.setText("" + GlobalMemberValues.getStoreName() + "");
            } else {
                topSalonNameLogo.setText("" + GlobalMemberValues.getStoreName() + "(" + GlobalMemberValues.getStoreNameEN() + ")");
            }

            topSalonNameLogo.setTextSize(32 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            topSalonNameLogo.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            topSalonNameLogo.setHorizontallyScrolling(true);
            topSalonNameLogo.setMarqueeRepeatLimit(-1);
            topSalonNameLogo.setFocusable(true);
            topSalonNameLogo.setFocusableInTouchMode(true);
            topSalonNameLogo.setSelected(true);
        }


        // 인터넷 상태 체크 -------------------------------------------------------------------------
        int conn = NetworkUtil.getConnectivityStatus(context);
        String tempStatus = null;
        int tempStatusImageId = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            tempStatus = "WIFI";
            tempStatusImageId = R.drawable.aa_images_main_wifi;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_wifi_lite;
            }
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            tempStatus = "3G/LTE";
            tempStatusImageId = R.drawable.aa_images_main_lte;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_lte_lite;
            }
        } else if (conn == NetworkUtil.TYPE_ETHENET) {
            tempStatus = "ONLINE";
            tempStatusImageId = R.drawable.aa_images_main_online;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_online_lite;
            }
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            tempStatus = "NOT CONNECTED";
            tempStatusImageId = R.drawable.aa_images_main_disconnect;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_disconnect_lite;
            }
        }

        GlobalMemberValues.changeNetworkUI(tempStatus, tempStatusImageId);

        GlobalMemberValues.GLOBALNETWORKSTATUS = conn;
        // ------------------------------------------------------------------------------------------


        // 클라우드 백오피스 오픈
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON1.setOnClickListener(new JJJ_OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    // 네트워크에 연결되어 있어도 인터넷이 연결안되어있을 수 있으므로
                    // 인터넷 연결을 먼저 확인한다.
                    if (GlobalMemberValues.isOnline().equals("00")) {
                        Intent adminPasswordIntent = new Intent(MainActivity.mContext, AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent.putExtra("openClassMethod", "command_cloud");
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(adminPasswordIntent);

                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                        MainActivity.proCustomDial = ProgressDialog.show(MainActivity.mContext, GlobalMemberValues.ANDROIDPOSNAME, "Loading Screens..", true, false);
                        MainActivity.proCustomDial.show();
                    } else {
                        GlobalMemberValues.showDialogNoInternet(MainActivity.mContext);
                    }
                } else {
                    //GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                    GlobalMemberValues.openNetworkNotConnected();
                }

            }
        });

        // 셋팅 페이지 오픈
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON2.setOnClickListener(new JJJ_OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                // 권한체크
                if (!GlobalMemberValues.checkEmployeePermission(
                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<8>")) {
                    GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                    return;
                }

                if (MainMiddleService.mSaleCartAdapter != null){
                    if (MainMiddleService.mSaleCartAdapter.getCount() != 0){
                        GlobalMemberValues.displayDialog(context, "Warning", "There is a menu in the shopping cart. \nPlease delete the history or try again after the order is completed.", "Close");
                        return;
                    }
                }

                Intent adminPasswordIntent = new Intent(MainActivity.mContext, AdminPassword.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                adminPasswordIntent.putExtra("openClassMethod", "apptopbar_settings");
                // -------------------------------------------------------------------------------------
                insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                mActivity.startActivity(adminPasswordIntent);

                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                }
            }
        });

        // 앱 종료
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON4.setOnClickListener(new JJJ_OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                    Payment.setGroupVoidCardStart();
                } else {
                    GlobalMemberValues.setCloseAndroidApp(mActivity);
                }
            }
        });
    }

    public static void openSettings() {
        //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
        Intent settingsIntent = new Intent(MainActivity.mContext, Settings.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(settingsIntent);

        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

}
