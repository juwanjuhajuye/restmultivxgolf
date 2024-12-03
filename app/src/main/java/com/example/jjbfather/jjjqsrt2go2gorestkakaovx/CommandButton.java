package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.star.StarPrintStart;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;
import com.pax.poslink.peripheries.POSLinkCashDrawer;
import com.pax.poslink.peripheries.ProcessResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-11-19.
 */
public class CommandButton {
    static Activity mActivity;
    static Context context;
    public static Context insContext;
    GetDataAtSQLite dataAtSqlite;

    // DB 객체 선언
    static DatabaseInit dbInit;

    Button closeBtn;

    private TextView commandButtonEditText;

    private ImageButton creditCardStatusButton;

    private Button salehistoryCommandButton,clockinoutCommandButton;
    private Button taxexemptCommandButton,undotaxexemptCommandButton,batchsummaryCommandButton;
    private Button holdCommandButton,recallCommandButton,reservationCommandButton;
    private Button resetCommandButton, backupDBCommandButton, restoreDBCommandButton;
    private Button cashdrawerCommandButton, backOfficeMainCommandButton, settingsCommandButton;
    private Button realtimewebordersButton, cashinoutButton;
    private LinearLayout downloadCommandButton, cloudCommandButton;
    private TextView downloadCommandButtonTextView, downloadSyncDataLastTimeTextView;
    private TextView cloudCloudCommandButtonTextView, cloudBackOfficeCommandButtonTextView;
    //private Button cashinoutCommandButton;
    private Button commandButton_togo_table;

    private Button bayReservationWindowButton;

    static TemporarySaleCart parentTemporarySaleCart;

    public static ProgressDialog itemProDial;

    public CommandButton(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        // 객체 생성과 함께 Employee 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPBUTTON3.setOnClickListener(commandButtonClickListener);
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND.setOnClickListener(commandButtonClickListener);
        }
    }

    public void setContents() {
        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("commandButtonCloseBtnTag");
        closeBtn.setOnClickListener(commandButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }


        /***********************************************************************************************************/

        commandButtonEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("commandButtonEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            commandButtonEditText.setText("");
            commandButtonEditText.setBackgroundResource(R.drawable.ab_imagebutton_command_title);
        } else {
            commandButtonEditText.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        /** 버튼 객체 생성 및 클릭 리스너 연결 **********************************************************************/
        // ----------------------------------------------------------------------------------------------------------------
        downloadCommandButton = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("downloadCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            downloadCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_download);
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            downloadCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_download_lite);
        }
        downloadCommandButtonTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("downloadCommandButtonTextViewTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            downloadCommandButtonTextView.setText("");
        }
        downloadSyncDataLastTimeTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("downloadSyncDataLastTimeTextViewTag");
        // 클라우드에서 최종으로 데이터를 다운로드 한 일시를 가져와 downloadSyncDataLastTimeTextView 에 넣는다.
        String strDateTime = GlobalMemberValues.getSyncDataLastTime(dbInit);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            //int tempLeftSize = (int)((GlobalMemberValues.BASICNUMBERTEXTSIZE + 50));
            downloadSyncDataLastTimeTextView.setPadding(0, 2, 0, 0);
            downloadSyncDataLastTimeTextView.setText("" + strDateTime);
        } else {
            downloadSyncDataLastTimeTextView.setText("LAST SYNCHRONIZED\n" + strDateTime);
        }
        downloadSyncDataLastTimeTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        // ----------------------------------------------------------------------------------------------------------------
        salehistoryCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("salehistoryCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            salehistoryCommandButton.setText("");
            salehistoryCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_saleshistory);
        } else {
            salehistoryCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            salehistoryCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_saleshistory_lite);
        }

        clockinoutCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("clockinoutCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            clockinoutCommandButton.setText("");
            clockinoutCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_clockinout);
        } else {
            clockinoutCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        taxexemptCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("taxexemptCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            taxexemptCommandButton.setText("");
            taxexemptCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_taxexempt);
        } else {
            taxexemptCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            taxexemptCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_taxexempt_lite);
        }

        undotaxexemptCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("undotaxexemptCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            undotaxexemptCommandButton.setText("");
            undotaxexemptCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_undotaxexempt);
        } else {
            undotaxexemptCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            undotaxexemptCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_undotaxexempt_lite);
        }

        batchsummaryCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("batchsummaryCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            batchsummaryCommandButton.setText("");
            batchsummaryCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_batchsummary);
        } else {
            batchsummaryCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            batchsummaryCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_batchsummary_lite);
        }

        holdCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("holdCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            holdCommandButton.setText("");
            holdCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_hold);
        } else {
            holdCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        recallCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("recallCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            recallCommandButton.setText("");
            recallCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_recall);
        } else {
            recallCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        reservationCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("reservationCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            reservationCommandButton.setText("");
            reservationCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_reservation);
        } else {
            reservationCommandButton.setTextSize(
                    (GlobalMemberValues.BASICNUMBERTEXTSIZE + 5)
            );
        }
        cloudCommandButton = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("cloudCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_backoffice);
        }
        cloudCloudCommandButtonTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("cloudCloudCommandButtonTextViewTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudCloudCommandButtonTextView.setText("");
        }
        cloudBackOfficeCommandButtonTextView = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("cloudBackOfficeCommandButtonTextViewTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cloudBackOfficeCommandButtonTextView.setText("");
        }
        /**
        resetCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("resetCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            resetCommandButton.setText("");
            resetCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_systemreset);
        } else {
            resetCommandButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
         **/

        backupDBCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("backupDBCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            backupDBCommandButton.setText("");
            backupDBCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_backup);
        } else {
            backupDBCommandButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            backupDBCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_backup_lite);
        }

        restoreDBCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("restoreDBCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            restoreDBCommandButton.setText("");
            restoreDBCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_restore);
        } else {
            restoreDBCommandButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            restoreDBCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_restore_lite);
        }

        cashdrawerCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("cashdrawerCommandButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashdrawerCommandButton.setText("");
            cashdrawerCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_cashdrawer);
        } else {
            cashdrawerCommandButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        realtimewebordersButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("realtimewebordersButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            realtimewebordersButton.setText("");
            realtimewebordersButton.setBackgroundResource(R.drawable.ab_imagebutton_realtimeweborders_quickview);
        } else {
            realtimewebordersButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        if (GlobalMemberValues.isOnlineOrderUseYN()) {
            realtimewebordersButton.setVisibility(View.VISIBLE);
        } else {
            realtimewebordersButton.setVisibility(View.GONE);
        }

        cashinoutButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON
                .findViewWithTag("cashinoutButtonTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashinoutButton.setText("");
            if (GlobalMemberValues.isCashInOutPossible()){
                cashinoutButton.setBackgroundResource(R.drawable.ab_imagebutton_command_cashinout);
            } else {
                cashinoutButton.setBackgroundResource(R.drawable.ab_imagebutton_command_end_sales_turn);
            }
        } else {
            cashinoutButton.setTextSize(
                    GlobalMemberValues.BASICNUMBERTEXTSIZE
            );
        }

        // Settings 버튼 (Lite 버전에만 있음)
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            settingsCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("settingsCommandButtonTag");
            settingsCommandButton.setBackgroundResource(R.drawable.ab_imagebutton_command_settings_lite);
        }

        // 돈통 오픈버튼 배치여부 -------------------------------------------------------------------------------------------------
        int cashdraweronoffonsalemode = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select cashdraweronoffonsalemode from salon_storestationsettings_deviceprinter")
        );
        if (cashdraweronoffonsalemode == 0) {
            cashdrawerCommandButton.setVisibility(View.VISIBLE);
        } else {
            cashdrawerCommandButton.setVisibility(View.INVISIBLE);
        }
        // ------------------------------------------------------------------------------------------------------------------------

        // creditCardStatus
        creditCardStatusButton = (ImageButton) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("creditCardStatusCommandButtonTag");
        if (!GlobalMemberValues.isCardStatusSaveUse()) {
            creditCardStatusButton.setVisibility(View.GONE);
        } else {
            creditCardStatusButton.setVisibility(View.VISIBLE);
        }


//        GlobalMemberValues gm = new GlobalMemberValues();
//        if (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R")) {
//            creditCardStatusButton.setVisibility(View.GONE);
//        } else {
//            creditCardStatusButton.setVisibility(View.VISIBLE);
//        }

        // commandButton_togo_table
        commandButton_togo_table = (Button) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("commandButton_togo_table_Tag");
//        commandButton_togo_table.setTextSize(GlobalMemberValues.globalAddFontSize() +
//                commandButton_togo_table.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
//        );

        //cashinoutCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("cashinoutCommandButtonTag");
        //backOfficeMainCommandButton = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("backOfficeMainCommandButtonTag");

        //07082024 set bayReservationWindow button
        bayReservationWindowButton = (Button) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_COMMANDBUTTON.findViewWithTag("bayReservationCheckButtonTag");

        salehistoryCommandButton.setOnClickListener(commandButtonClickListener);
        taxexemptCommandButton.setOnClickListener(commandButtonClickListener);
        undotaxexemptCommandButton.setOnClickListener(commandButtonClickListener);
        batchsummaryCommandButton.setOnClickListener(commandButtonClickListener);
        holdCommandButton.setOnClickListener(commandButtonClickListener);
        recallCommandButton.setOnClickListener(commandButtonClickListener);
        reservationCommandButton.setOnClickListener(commandButtonClickListener);
        //resetCommandButton.setOnClickListener(commandButtonClickListener);
        backupDBCommandButton.setOnClickListener(commandButtonClickListener);
        restoreDBCommandButton.setOnClickListener(commandButtonClickListener);
        cashdrawerCommandButton.setOnClickListener(commandButtonClickListener);
        realtimewebordersButton.setOnClickListener(commandButtonClickListener);
        //cashinoutCommandButton.setOnClickListener(commandButtonClickListener);
        //backOfficeMainCommandButton.setOnClickListener(commandButtonClickListener);
        cashinoutButton.setOnClickListener(commandButtonClickListener);

        downloadCommandButton.setOnClickListener(commandButtonClickListener2);
        clockinoutCommandButton.setOnClickListener(commandButtonClickListener2);
        cloudCommandButton.setOnClickListener(commandButtonClickListener2);

        creditCardStatusButton.setOnClickListener(commandButtonClickListener);

        commandButton_togo_table.setOnClickListener(commandButtonClickListener);

        //07082024 set onclicklistener for bayReservationWindow Button
        bayReservationWindowButton.setOnClickListener(commandButtonClickListener);

        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            settingsCommandButton.setOnClickListener(commandButtonClickListener);
        }

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONHOLD.setOnClickListener(commandButtonClickListener);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONRECALL.setOnClickListener(commandButtonClickListener);
        /***********************************************************************************************************/

        if (TableSaleMain.isClickCommandOnTable) {
            closeBtn.setVisibility(View.GONE);
            downloadCommandButton.setVisibility(View.VISIBLE);
            commandButton_togo_table.setVisibility(View.VISIBLE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
            downloadCommandButton.setVisibility(View.INVISIBLE);

            // 04302024
            if (!GlobalMemberValues.isQSRPOSonRestaurantPOS) {
                downloadCommandButton.setVisibility(View.INVISIBLE);
            } else {
                downloadCommandButton.setVisibility(View.VISIBLE);
            }

            commandButton_togo_table.setVisibility(View.INVISIBLE);

        }

        if (GlobalMemberValues.getStationType() == "Q" || GlobalMemberValues.getStationType().equals("Q")) {
            downloadCommandButton.setVisibility(View.VISIBLE);
            backupDBCommandButton.setVisibility(View.INVISIBLE);
            restoreDBCommandButton.setVisibility(View.INVISIBLE);
        }

        //TableSaleMain.isClickCommandOnTable = false;
    }

    public static void setDatabaseAndApiDataDownloadThread(int paramStatus, int actionType) {
        final int PARAMSTATUS = paramStatus;
        final int PARAMACTIOINTYPE = actionType;

        if (GlobalMemberValues.LOADINGINTENTUSE == "1" || GlobalMemberValues.LOADINGINTENTUSE.equals("1")) {
            // 로딩 인텐트 오픈
            Intent loadingIntent = new Intent(MainActivity.mContext.getApplicationContext(), LoadingIntent.class);
            mActivity.startActivity(loadingIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }



        } else {
            // 프로그래스 바를 실행~
            MainActivity.proDial = ProgressDialog.show(MainActivity.mContext, GlobalMemberValues.ANDROIDPOSNAME, GlobalMemberValues.DOWNLOAD_PROGRESS, true, false);

        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                // 화면재개 지연시간 초기화
                GlobalMemberValues.RESTARTSCREEN_DELYTIME = "0";

                // 먼저 salon_member 의 고객포인트를 임시저장소(temp_mileagecart) 에 저장한다.
                boolean tempBoolean = GlobalMemberValues.saveTempMileageCart();
                if (!tempBoolean) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Error", "Close");
                    return;
                }

                // DB 생성 및 처리 관련 메소드
                // setDatabaseAndApiDataDownload(int paramStatus, int actionType)
                // paramStatus       --- 0 : 메소드 실행             1 : 실행안함
                // actionType        --- 0 : DB 테이블 먼저 삭제     1 : 삭제안함
                MainActivity.setDatabaseAndApiDataDownload(PARAMSTATUS, PARAMACTIOINTYPE, 1);
                // ---------------------------------------------------------------------------------

                GlobalMemberValues.logWrite("screendelaytimelog", "GlobalMemberValues.RESTARTSCREEN_DELYTIME : " + GlobalMemberValues.RESTARTSCREEN_DELYTIME);
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

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            GlobalMemberValues.displayDialog(context, "Download Data", "Data has downloaded from Cloud Server", "Close");
            // -------------------------------------------------------------------------------------
            // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
            if (GlobalMemberValues.LOADINGINTENTUSE == "1" || GlobalMemberValues.LOADINGINTENTUSE.equals("1")) {
                GlobalMemberValues.setFinishLoadingIntent();
            } else {
                MainActivity.proDial.dismiss();
            }
            // -------------------------------------------------------------------------------------

            // 3. MainActivity 리로드 --------------------------------------------------------------
            GlobalMemberValues.ITEMCANCELAPPLY = 1;
            GlobalMemberValues.MAINRECREATEYN = "Y";
            //MainActivity.mActivity.recreate();
            MainActivity.employeeLogout();
            // -------------------------------------------------------------------------------------

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlobalMemberValues.copyMileageFromTempMileageCart();
        }
    };

    View.OnClickListener commandButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topButton3 : {
                    if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                    } else {
                        GlobalMemberValues.setFrameLayoutVisibleChange("commandButton");
                        setContents();
                    }
                    break;
                }
                case R.id.mainRightBottom_Command : {
                    //GlobalMemberValues.displayDialog(context, "", "여기!!!!!", "Close");
                    if (Payment.mCardPaidYN == "Y" || Payment.mCardPaidYN.equals("Y")) {
                    } else {
                        GlobalMemberValues.setFrameLayoutVisibleChange("commandButton");
                        setContents();

                        // Lite 버전 관련
                        if (GlobalMemberValues.isLiteVersion()) {
                            // 하단버튼 초기화
                            GlobalMemberValues.setInitMainBottomButtonBg();
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCOMMAND.setBackgroundResource(R.drawable.aa_images_main_command_rollover_lite);
                        }
                    }
                    break;
                }
                case R.id.commandButtonCloseBtn : {
                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null){
                        GlobalMemberValues.GLOBAL_EMPLOYEEINFO = new TemporaryEmployeeInfo();
                    }
                    if (!GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName.equals("ADMIN")){
                        GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    } else {
//                        GlobalMemberValues.setCloseAndroidApp(mActivity);
                        MainActivity.setEmployeeLogout();
                    }

                    break;
                }

                case R.id.salehistoryCommandButton : {
                    LogsSave.saveLogsInDB(210);
                    //Intent saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistory.class);
                    Intent saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistoryList.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    saleHistoryIntent.putExtra("saleHistoryTagValue", "");
                    // 03102018
                    GlobalMemberValues.sh_fromCommand = "Y";
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(saleHistoryIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.reservationCommandButton : {
                    LogsSave.saveLogsInDB(211);
                    Intent saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistoryList_web.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    saleHistoryIntent.putExtra("saleHistoryTagValue", "");
                    GlobalMemberValues.shweb_fromCommand = "Y";
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(saleHistoryIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }

                    /** 관리자 비밀번호를 물어야 할 때....
                     // 인터넷 연결되었을 경우에만
                     if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                     Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                     // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                     adminPasswordIntent.putExtra("openClassMethod", "command_reservation");
                     // -------------------------------------------------------------------------------------
                     insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                     mActivity.startActivity(adminPasswordIntent);
                     mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                     } else {
                     GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                     }
                     **/

                    /** 관리자 비밀번호를 묻지않고 오픈할 경우
                     if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                     if (!GlobalMemberValues.isOnline().equals("00")) {
                     GlobalMemberValues.showDialogNoInternet(context);
                     return;
                     }
                     Intent reservationWebPageIntent = new Intent(context.getApplicationContext(), ReservationWebPage.class);
                     // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                     //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                     // -------------------------------------------------------------------------------------
                     mActivity.startActivity(reservationWebPageIntent);
                     mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                     } else {
                     GlobalMemberValues.openNetworkNotConnected();
                     }
                     /********************************************************************************************************/

                    break;
                }

                case R.id.taxexemptCommandButton : {
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<4>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "command_taxexempt");
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.undotaxexemptCommandButton : {
                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!MainMiddleService.checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<4>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                    if (!GlobalMemberValues.isMultiCheckOnCart()) {
                        if (MainMiddleService.selectedPosition == -1
                                || GlobalMemberValues.isStrEmpty(String.valueOf(MainMiddleService.selectedPosition))) {
                            GlobalMemberValues.displayDialog(context, "UNDO TAX EXEMPT", "Choose a item", "Close");
                            return;
                        }
                    }
                    LogsSave.saveLogsInDB(213);
                    setUndoTaxExempt();

                    break;
                }
                case R.id.batchsummaryCommandButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<5>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "command_batchsummary");
                    adminPasswordIntent.putExtra("openClassName", "commandbutton");
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.holdCommandButton : {
                    LogsSave.saveLogsInDB(215);
                    //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                    setHoldSalesCheck();
                    break;
                }
                case R.id.recallCommandButton : {
                    LogsSave.saveLogsInDB(216);
                    setRecallCheck();
                    break;
                }
                case R.id.directButtonHoldButton : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    setHoldSalesCheck();
                    break;
                }
                case R.id.directButtonRecallButton: {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    setRecallCheck();
                    break;
                }

                /**
                 case R.id.resetCommandButton : {
                 Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                 // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                 adminPasswordIntent.putExtra("openClassMethod", "command_reset");
                 // -------------------------------------------------------------------------------------
                 insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                 mActivity.startActivity(adminPasswordIntent);
                 mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                 break;
                 }
                 **/
                case R.id.backupDBCommandButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<7>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }
                    Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "command_databasebackup");
                    adminPasswordIntent.putExtra("openClassName", "commandbutton");
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.restoreDBCommandButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<7>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent.putExtra("openClassMethod", "command_databaserestore");
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(adminPasswordIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }

                case R.id.cashdrawerCommandButton : {
                    LogsSave.saveLogsInDB(222);
                    openCashDrawer();
                    break;
                }

                case R.id.realtimewebordersButton : {
                    Intent realTimeWebOrdersIntent = new Intent(MainActivity.mContext.getApplicationContext(), RealTimeWebOrders.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    mActivity.startActivity(realTimeWebOrdersIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
                    }
                    break;
                }
                case R.id.cashinoutButton : {
//                    if (!GlobalMemberValues.isCashInOutPossible()) {
//                        GlobalMemberValues.displayDialog(context, "Warning", "This station have no permission for ‘Cash in / out’.", "Close");
//                        return;
//                    }

                    if (!GlobalMemberValues.isCashInOutPossible()) {
                        new AlertDialog.Builder(context)
                                .setTitle("End Sales Turn")
                                .setMessage("Are you sure you want to end sales turn?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LogsSave.saveLogsInDB(221);
                                                setOpenCashInOut();
                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                .show();
                    } else {
                        setOpenCashInOut();
                    }

                    break;
                }

                /**
                 case R.id.backOfficeMainCommandButton : {
                 Intent adminPasswordIntent = new Intent(context.getApplicationContext(), AdminPassword.class);
                 // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                 adminPasswordIntent.putExtra("openClassMethod", "command_backofficemain");
                 // -------------------------------------------------------------------------------------
                 insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                 mActivity.startActivity(adminPasswordIntent);
                 mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                 break;
                 }
                 **/
                /**
                 case R.id.cashinoutCommandButton : {
                 //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                 Intent cashInOutIntent = new Intent(context.getApplicationContext(), CashInOut.class);
                 // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                 //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                 cashInOutIntent.putExtra("cashInOutTagValue", "");
                 // -------------------------------------------------------------------------------------
                 insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                 mActivity.startActivity(cashInOutIntent);

                 break;
                 }
                 **/

                case R.id.settingsCommandButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<8>")) {
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
                    break;
                }
                case R.id.commandButton_creditcardstatus : {
                    Intent creditCardStatusIntent = new Intent(MainActivity.mContext.getApplicationContext(), CreditCardStatusActivity.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------

                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(creditCardStatusIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.commandButton_togo_table : {
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
                        //if ((MainMiddleService.mGeneralArrayList.toString().equals(MainActivity.temp_str_salecart))) {
                            MainMiddleService.initList();
                            GlobalMemberValues.openRestaurantTable();
                        } else {
                            //07182024 this part isn't needed anymore?
//                            if (MainActivity.temp_str_salecart_cnt > 0) {
//                                if (MainActivity.temp_str_salecart_cnt == MainMiddleService.mGeneralArrayList.size()) {
//                                    MainMiddleService.initList();
//                                    GlobalMemberValues.openRestaurantTable();
//                                } else {
//                                    GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
//                                            "There is an added menu\nPlease print the kitchen or delete the added menu", "Close");
//                                }
//                                return;
//                            }

                            Popup_to_go_table_3btn popup_to_go_table = new Popup_to_go_table_3btn(
                                    MainActivity.mContext, "","There is an ordered menu. Would you like to print into the kitchen?", new CustomDialogClickListener() {
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
                    } else {
                        // 메인 뷰에서 테이블 Close 후 다른 새 테이블로 들어왔을때 Cart List 가 초기화되지 않는 현상이 있어 추가함.
                        MainMiddleService.initList();
                        GlobalMemberValues.openRestaurantTable();
                    }
                    break;
                }

                //07082024 add button to open Bay Reservation Window
                case R.id.bayReservationCheckButton: {
                    Intent bayReservationWindowIntent = new Intent(MainActivity.mContext.getApplicationContext(), BayReservationViewer.class);
                    //saleHistoryIntent.putExtra("saleHistoryTagValue", "");
                    // 03102018
                    // -------------------------------------------------------------------------------------
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(bayReservationWindowIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };

    public static void setOpenCashInOut() {
        if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
            //GlobalMemberValues.displayDialog(context, "Cash In/Out", "Under Construction", "Close");
            Intent intentCashInOutPopupPrevAdmin = new Intent(MainActivity.mContext.getApplicationContext(), CashInOutPopupPreviousListAdmin.class);
            mActivity.startActivity(intentCashInOutPopupPrevAdmin);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        } else {
            //GlobalMemberValues.displayDialog(context, "Cash In/Out", "Under Construction", "Close");
            //CashInOutPopup.proDial = ProgressDialog.show(MainActivity.mContext, GlobalMemberValues.ANDROIDPOSNAME, "Cash In / Out Page is Loading...", true, false);
            MainActivity.proCustomDial = new JJJCustomAnimationDialog(MainActivity.mContext);
            MainActivity.proCustomDial.show();

            /**
             try {
             Thread.sleep(2000);
             } catch (InterruptedException e) {
             e.printStackTrace();
             }

             try {
             Intent intentCashInOutPopup = new Intent(MainActivity.mContext.getApplicationContext(), CashInOutPopup.class);
             mActivity.startActivity(intentCashInOutPopup);
             mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
             } catch (Exception e) {
             }
             **/

            new Thread(new Runnable() {
                @Override public void run() {

                    try {
                        String direxetypeValue = "";
                        Intent intentCashInOutPopup = new Intent(MainActivity.mContext.getApplicationContext(), CashInOutPopup.class);
                        if (!GlobalMemberValues.isCashInOutPossible()) {
                            direxetypeValue = "endingcash";
                        } else {
                            direxetypeValue = "";
                        }
                        intentCashInOutPopup.putExtra("direxetype", direxetypeValue);

                        mActivity.startActivity(intentCashInOutPopup);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    } catch (Exception e) {
                    }
                }
            }).start();

        }
    }


    JJJ_OnSingleClickListener commandButtonClickListener2 = new JJJ_OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            switch (v.getId()) {

                case R.id.downloadCommandButton : {
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<6>")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                        return;
                    }

                    // 인터넷 연결되었을 경우에만
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            return;
                        }

                        // os 버전 체크
                        // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
                        GlobalMemberValues.setFileAccessPermission(MainActivity.mActivity, MainActivity.mContext);

                        // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
                        //CommandButton.backupDatabase(false);

                        Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent.putExtra("openClassMethod", "command_download");
                        adminPasswordIntent.putExtra("openClassName", "commandbutton");
                        // -------------------------------------------------------------------------------------
                        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                        mActivity.startActivity(adminPasswordIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                        // MainMiddleService.clearListView();
                    } else {
                        //GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                }

                case R.id.clockinoutCommandButton : {
                    LogsSave.saveLogsInDB(218);
                    // clockinout 의 타입을 구한다. (0 : 오프라인   1 : 온라인)
                    String strQuery = "select clockinouttype from salon_storestationsettings_system ";
                    String tempClockInOutType = dbInit.dbExecuteReadReturnString(strQuery);

                    Intent clockInOutIntent;
                    if (GlobalMemberValues.getIntAtString(tempClockInOutType) == 0) {
                        clockInOutIntent = new Intent(MainActivity.mContext.getApplicationContext(), ClockInOut.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                        clockInOutIntent.putExtra("clockInOutValue", "");
                        // -------------------------------------------------------------------------------------
                        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                        mActivity.startActivity(clockInOutIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                        }
                    } else {
                        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                            if (!GlobalMemberValues.isOnline().equals("00")) {
                                GlobalMemberValues.showDialogNoInternet(context);
                                return;
                            }
                            clockInOutIntent = new Intent(MainActivity.mContext.getApplicationContext(), ClockInOutNavtiveWeb.class);
                            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                            clockInOutIntent.putExtra("clockInOutValue", "");
                            // -------------------------------------------------------------------------------------
                            insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                            mActivity.startActivity(clockInOutIntent);
                            if (GlobalMemberValues.isUseFadeInOut()) {
                                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                            }
                        } else {
                            GlobalMemberValues.openNetworkNotConnected();
                        }
                    }

                    break;
                }

                case R.id.cloudCommandButton : {
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(context);
                            return;
                        }
                        Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent.putExtra("openClassMethod", "command_cloud");
                        adminPasswordIntent.putExtra("openClassName", "commandbutton");
                        // -------------------------------------------------------------------------------------
                        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                        mActivity.startActivity(adminPasswordIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    } else {
                        GlobalMemberValues.openNetworkNotConnected();
                    }

                    break;
                }

            }
        }
    };

    public static void openBatchSummary(String paramAutoCloseYN) {

        MainActivity.proCustomDial = ProgressDialog.show(MainActivity.mContext, GlobalMemberValues.ANDROIDPOSNAME, "Loading Card Sale", true, false);
        MainActivity.proCustomDial.show();

        try {
            GlobalMemberValues.logWrite("batchsummaryjjj", "여기실행...1" + "\n");
            //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
            Intent batchSummaryIntent = new Intent(mActivity, BatchSummary.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            batchSummaryIntent.putExtra("autocloseyn", paramAutoCloseYN);
            // -------------------------------------------------------------------------------------
            insContext = MainActivity.mContext;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
            MainActivity.mActivity.startActivity(batchSummaryIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                MainActivity.mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
            GlobalMemberValues.logWrite("batchsummaryjjj", "여기실행...2" + "\n");
        } catch (Exception e) {
            GlobalMemberValues.logWrite("batchsummaryjjj", "여기실행...3" + "\n");
            GlobalMemberValues.logWrite("batchsummaryjjj", "에러메시지 : " + e.getMessage().toString() + "\n");
        }
    }

    public static void restoreDatabaseAfterCheckCloudBackup() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            String strCloudDbBackUpYN = GlobalMemberValues.getCheckCloudBackupYN(GlobalMemberValues.STATION_CODE);
            GlobalMemberValues.logWrite("CommandLog", "strCloudDbBackUpYN  : " + strCloudDbBackUpYN + "\n");
            // 클라우드 백업을 사용하는 경우
            if (strCloudDbBackUpYN == "Y" || strCloudDbBackUpYN.equals("Y")) {
                new AlertDialog.Builder(context)
                        .setTitle("Local / Cloud Retore")
                        .setMessage("You can restore from the cloud system.\nTouch [Cloud System] button to restore from the cloud system\n" +
                                "or Touch [Local System] button to restore from the local system")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Cloud System",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!GlobalMemberValues.isOnline().equals("00")) {
                                            GlobalMemberValues.showDialogNoInternet(context);
                                        } else {
                                            // FTP 복원 메소드 실행
                                            restoreDatabaseFromCloud();
                                        }
                                    }
                                })
                        .setNegativeButton("Local System",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        restoreDatabase();
                                    }
                                })
                        .show();
            } else {
                restoreDatabase();
            }
        } else {
            restoreDatabase();
        }
    }

    public static void restoreDatabaseFromCloud() {
        itemProDial = ProgressDialog.show(context, "Cloud Backup", "The database is being restored from the cloud...", true, false);

        FileDownloadByFTP downFtp = new FileDownloadByFTP(MainActivity.mContext);
        downFtp.downloadAppType = "";
        downFtp.downloadFileStr = Environment.getExternalStorageDirectory().toString() + "/Download/" + GlobalMemberValues.DATABASE_NAME;
        downFtp.downloadFileOfFtp = GlobalMemberValues.DATABASE_NAME + "_" + GlobalMemberValues.STATION_CODE;

        GlobalMemberValues.logWrite("commandLog", "file down loc : " + downFtp.downloadFileStr + "\n");
        GlobalMemberValues.logWrite("commandLog", "file ftp loc : " + downFtp.downloadFileOfFtp + "\n");

        downFtp.execute();
    }

    public static void restoreDatabase() {
        // 패키지명 가져오기
        String tempPackagename = MainActivity.mContext.getPackageName();
        //Toast.makeText(context, "패키지명 : " + tempPackagename, Toast.LENGTH_SHORT).show();
        //GlobalMemberValues.logWrite("commandButtonDatabase", "패키지명 : " + tempPackagename + "\n");
        // 데이터베이스가 저장된 경로 가져오기
        File tempFile = MainActivity.mContext.getDatabasePath(GlobalMemberValues.DATABASE_NAME);
        String tempDbPath = tempFile.getPath();
        //Toast.makeText(context, "DB 경로 : " + tempDbPath, Toast.LENGTH_SHORT).show();
        GlobalMemberValues.logWrite("commandButtonDatabase", "DB 경로 : " + tempDbPath + "\n");
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        //Toast.makeText(context, "저장 경로 : " + sdcard, Toast.LENGTH_SHORT).show();
        //GlobalMemberValues.logWrite("commandButtonDatabase", "저장 경로 : " + sdcard + "\n");

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                File backupDB = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME);
                File backupDBWal = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME + "-wal");
                File backupDBShm = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME + "-shm");

                File currentDB = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME);
                File currentDBWal = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME + "-wal");
                File currentDBShm = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME + "-shm");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                dbInit.closeDBHanlder();

                //06272024 Loop transferFrom method to make sure all the content transfers
                for(long count = currentDB.length(); count > 0L;){
                    final long transferred = dst.transferFrom(
                            src, dst.position(), count);
                    dst.position(dst.position() + transferred);
                    count -= transferred;
                }
                src.close();
                dst.close();

                //06272024 only if the device is using wal mode, restore the -wal and -shm file
                if (backupDBWal.exists() && currentDBWal.exists()) {
                    FileChannel srcWal = new FileInputStream(currentDBWal).getChannel();
                    FileChannel dstWal = new FileOutputStream(backupDBWal).getChannel();

                    for (long count = currentDBWal.length(); count > 0L; ) {
                        final long transferred = dstWal.transferFrom(
                                srcWal, dstWal.position(), count);
                        dstWal.position(dstWal.position() + transferred);
                        count -= transferred;
                    }

                    srcWal.close();
                    dstWal.close();
                }
                if (backupDBShm.exists() && currentDBShm.exists()){

                    FileChannel srcShm = new FileInputStream(currentDBShm).getChannel();
                    FileChannel dstShm = new FileOutputStream(backupDBShm).getChannel();

                    for(long count = currentDBShm.length(); count > 0L;){
                        final long transferred = dstShm.transferFrom(
                                srcShm, dstShm.position(), count);
                        dstShm.position(dstShm.position() + transferred);
                        count -= transferred;
                    }

                    srcShm.close();
                    dstShm.close();
                }

                dbInit.openDBHandler();

                if (!MainActivity.mActivity.isFinishing()) {
                    Toast.makeText(MainActivity.mContext, "Database restoration OK", Toast.LENGTH_SHORT).show();
                }

                GlobalMemberValues.displayDialog(context, "Database Restore", "Database Restored OK", "Close");
                GlobalMemberValues.logWrite("commandButtonDatabase", "복원결과 : OK\n");

                // DB 복원후........데이터베이스 생성 및 테이블 생성, 추가, 삭제 및 컬럼 추가, 삭제
                dbInit.initDatabaseTables();
            }
        } catch (Exception e) {
            if (!MainActivity.mActivity.isFinishing()) {
                Toast.makeText(MainActivity.mContext, "Database restoration is failed", Toast.LENGTH_SHORT).show();
            }

            GlobalMemberValues.displayDialog(context, "Database Restore", "Database restoration is failed", "Close");
            GlobalMemberValues.logWrite("commandButtonDatabase", "에러메시지 : " + e.getMessage().toString() + "\n");
        }
    }

    public static void backupDatabase(boolean paramOpenDialog) {
        // 패키지명 가져오기
        String tempPackagename = MainActivity.mContext.getPackageName();
        //Toast.makeText(context, "패키지명 : " + tempPackagename, Toast.LENGTH_SHORT).show();
        //GlobalMemberValues.logWrite("commandButtonDatabase", "패키지명 : " + tempPackagename + "\n");
        // 데이터베이스가 저장된 경로 가져오기
        File tempFile = MainActivity.mContext.getDatabasePath(GlobalMemberValues.DATABASE_NAME);
        String tempDbPath = tempFile.getPath();
        //Toast.makeText(context, "DB 경로 : " + tempDbPath, Toast.LENGTH_SHORT).show();
        GlobalMemberValues.logWrite("commandButtonDatabase", "DB 경로 : " + tempDbPath + "\n");
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        //Toast.makeText(context, "저장 경로 : " + sdcard, Toast.LENGTH_SHORT).show();
        //GlobalMemberValues.logWrite("commandButtonDatabase", "저장 경로 : " + sdcard + "\n");
        GlobalMemberValues.b_ftp_dialog_showing = false;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File BackupDir = new File(sd, "Download");
                BackupDir.mkdir();

                File currentDB = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME);
                File currentDBWal = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME + "-wal");
                File currentDBShm = new File(
                        data, "//data//" + tempPackagename + "//databases//" + GlobalMemberValues.DATABASE_NAME + "-shm");

                File backupDB = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME);
                File backupDBWal = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME + "-wal");
                File backupDBShm = new File(sd, "Download/" + GlobalMemberValues.DATABASE_NAME + "-shm");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                //06272024 only if the device is using wal mode, back up -wal and -shm files,
                //and flush the -wal file into main db file.
                if (currentDBWal.exists()){
                    //05312024 Flush wal content into main database file
                    int wal_busy,wal_log,wal_checkpointed = -99;

                    Cursor cursor = dbInit.dbExecuteRead("PRAGMA wal_checkpoint");
                    if (cursor.moveToFirst()) {
                        wal_busy = cursor.getInt(0);
                        wal_log = cursor.getInt(1);
                        wal_checkpointed = cursor.getInt(2);
                    }

                    cursor = dbInit.dbExecuteRead("PRAGMA wal_checkpoint(TRUNCATE)");
                    cursor.getCount();
                    cursor = dbInit.dbExecuteRead("PRAGMA wal_checkpoint");
                    if (cursor.moveToFirst()) {
                        wal_busy = cursor.getInt(0);
                        wal_log = cursor.getInt(1);
                        wal_checkpointed = cursor.getInt(2);
                    }
                }

                dbInit.closeDBHanlder();

                //06272024 Loop transferFrom method to make sure all the content transfers
                for(long count = currentDB.length(); count > 0L;){
                    final long transferred = dst.transferFrom(
                            src, dst.position(), count);
                    dst.position(dst.position() + transferred);
                    count -= transferred;
                }

                src.close();
                dst.close();

                if(currentDBWal.exists()){
                    FileChannel srcWal = new FileInputStream(currentDBWal).getChannel();
                    FileChannel dstWal = new FileOutputStream(backupDBWal).getChannel();

                    FileChannel srcShm = new FileInputStream(currentDBShm).getChannel();
                    FileChannel dstShm = new FileOutputStream(backupDBShm).getChannel();

                    for(long count = currentDBWal.length(); count > 0L;){
                        final long transferred = dstWal.transferFrom(
                                srcWal, dstWal.position(), count);
                        dstWal.position(dstWal.position() + transferred);
                        count -= transferred;
                    }

                    srcWal.close();
                    dstWal.close();

                    for(long count = currentDBShm.length(); count > 0L;){
                        final long transferred = dstShm.transferFrom(
                                srcShm, dstShm.position(), count);
                        dstShm.position(dstShm.position() + transferred);
                        count -= transferred;
                    }

                    srcShm.close();
                    dstShm.close();
                }

                dbInit.openDBHandler();

                if (!MainActivity.mActivity.isFinishing()) {
                    Toast.makeText(MainActivity.mContext, "Database Backup OK", Toast.LENGTH_SHORT).show();
                }

                GlobalMemberValues.logWrite("commandButtonDatabase", "백업결과 : OK\n");

                String strCloudDbBackUpYN = GlobalMemberValues.getCheckCloudBackupYN(GlobalMemberValues.STATION_CODE);
                GlobalMemberValues.logWrite("CommandLog", "strCloudDbBackUpYN (backup & upload)  : " + strCloudDbBackUpYN + "\n");

                //GlobalMemberValues.displayDialog(context, "Database Backup", "GlobalMemberValues.GLOBALNETWORKSTATUS  : " + GlobalMemberValues.GLOBALNETWORKSTATUS, "Close");


                // 클라우드 백업을 사용하는 경우
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0 && (strCloudDbBackUpYN == "Y" || strCloudDbBackUpYN.equals("Y"))) {
                    if (GlobalMemberValues.GLOBAL_DATABASEBACKUPINTENDER) {
                        if (!GlobalMemberValues.isCloudBackupInTenderBakcup()) {
                            return;
                        }
                    }

                    GlobalMemberValues.GLOBAL_DATABASEBACKUPINTENDER = false;

                    // Cloud 백업 실행 --------------------------------------------------------------------------------------
                    String tempBoolean = "N";
                    if (paramOpenDialog) {
                        tempBoolean = "Y";
                    } else {
                        tempBoolean = "N";
                    }

                    GlobalMemberValues.M_FTPBASCIDIR = "";      // FTP 에 저장할 폴더 : 기본 폴더로..

                    Intent uploadDatabaseBackupToCloud
                            = new Intent(MainActivity.mContext.getApplicationContext(), UploadDataBaseBackupToCloud.class);
                    uploadDatabaseBackupToCloud.putExtra("openDialog", tempBoolean);
                    //mActivity.startActivity(uploadSalesDataToCloudIntent);
                    //sendSalesDataToCloud(returnSalesCode);

                    GlobalMemberValues.CURRENTSERVICEINTENT_DBBACKUP = uploadDatabaseBackupToCloud;           // 실행되는 서비스 인텐트를 저장해둔다.
                    GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_DBBACKUP = MainActivity.mActivity;           // 서비스를 실행시킨 액티비티를 저장해 둔다.

                    MainActivity.mActivity.startService(uploadDatabaseBackupToCloud);

                    // paramOpenDialog 이 false 인 경우에는 아래 구문 실행하지 않는다.
                    // paramOpenDialog 이 true 인 경우 : Command 에서 Backup 실행했을 경우...
                    if (paramOpenDialog) {
                        itemProDial = ProgressDialog.show(context, "Cloud Backup", "Cloud backup is progress...", true, false);
                    }
                    // -----------------------------------------------------------------------------------------------------
                } else {
                    if (paramOpenDialog) {
//                        GlobalMemberValues.displayDialog(context, "Database Backup", "Database Backup OK", "Close");
                        GlobalMemberValues.ftpUpLoaddisplayDialog(context, "Database Backup", "Database Backup OK", "Close");
                    }
                }

            }
        } catch (Exception e) {
            if (!MainActivity.mActivity.isFinishing()) {
                // 060123
                // Timer 에서 앱 종료시 오류 발생해서 주석처리.
                // AppCloseScheduler 에서 실행
                // GlobalMemberValues.doMethodInClose(MainActivity.mActivity); 타이머
//                Toast.makeText(MainActivity.mContext, "Database backup is failed", Toast.LENGTH_SHORT).show();
            }
            GlobalMemberValues.displayDialog(context, "Database Backup", "Database backup is failed", "Close");
            GlobalMemberValues.logWrite("commandButtonDatabase", "에러메시지 : " + e.getMessage().toString() + "\n");
        }
    }

    public static void setReservationOpen() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            Intent reservationWebPageIntent = new Intent(MainActivity.mContext.getApplicationContext(), ReservationWebPage.class);
            // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
            //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
            // -------------------------------------------------------------------------------------
            mActivity.startActivity(reservationWebPageIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        } else {
            GlobalMemberValues.openNetworkNotConnected();
        }
    }

    public static void setBackOfficeMainOpen() {
        Intent backOfficeMainIntent = new Intent(MainActivity.mContext.getApplicationContext(), BackOfficeMain.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        // -------------------------------------------------------------------------------------
        mActivity.startActivity(backOfficeMainIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
        }
    }



    public static void setCloudOpenOutside() {
        GlobalMemberValues.openCloudWeb(MainActivity.mContext.getApplicationContext(), mActivity);
    }

    public static void setTaxExemptOutside() {
        if (GlobalMemberValues.isMultiCheckOnCart()) {
            new AlertDialog.Builder(context)
                    .setTitle("TAX EXEMPT")
                    .setMessage("Do you want to exempt the tax?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int tempSize = MainMiddleService.mGeneralArrayList.size();
                                    if (tempSize == 0) {
                                        return;
                                    }
                                    LogsSave.saveLogsInDB(212);
                                    boolean[] copyIsCheckedConfrim = MainMiddleService.isCheckedConfrim;

                                    int checkedItemCount = 0;
                                    for(int i = 0; i < tempSize; i++){
                                        if(copyIsCheckedConfrim[i]){
                                            checkedItemCount++;
                                        }
                                    }
                                    if (MainMiddleService.mGeneralArrayList.size() > 0 && checkedItemCount > 0) {
                                        boolean tempLastItem = false;
                                        int tempCount = 0;
                                        for(int j = (tempSize - 1); j >= 0; j--){
                                            if(copyIsCheckedConfrim[j]){
                                                tempCount++;
                                                if (checkedItemCount == tempCount) {
                                                    tempLastItem = true;
                                                } else {
                                                    tempLastItem = false;
                                                }

                                                setTaxExempt(j);
                                            }
                                        }
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
            //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
            if (MainMiddleService.selectedPosition == -1
                    || GlobalMemberValues.isStrEmpty(String.valueOf(MainMiddleService.selectedPosition))) {
                GlobalMemberValues.displayDialog(context, "TAX EXEMPT", "Choose a item", "Close");
                return;
            }
            new AlertDialog.Builder(context)
                    .setTitle("TAX EXEMPT")
                    .setMessage("Do you want to exempt the tax?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    setTaxExempt(MainMiddleService.selectedPosition);
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        }
    }

    public static void setDownloadOutside() {
        // 테이블 추가, 수정, 삭제 및 컬럼 추가, 삭제 -------------------------------------------
        DatabaseInit dbInit = new DatabaseInit(context);
        dbInit.initDatabaseTables();
        // ----------------------------------------------------------------------------------

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            /**
            if ((MainActivity.mActivity != null) && (!MainActivity.mActivity.isFinishing())) {
                new AlertDialog.Builder(context)
                        .setTitle("Data Download")
                        .setMessage("POS data will be replaced by Cloud Data\nDo you want to download?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        GlobalMemberValues.MSYNCDATATYPE = "COMMAND_DOWNLOAD";
                                        setDatabaseAndApiDataDownloadThread(0, GlobalMemberValues.INSERTDATAAFTERDELETE);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
             **/
            Intent intent = new Intent(MainActivity.mContext.getApplicationContext(), CommandDownloadData.class);
            mActivity.startActivity(intent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
            MainActivity.proCustomDial = ProgressDialog.show(MainActivity.mContext, GlobalMemberValues.ANDROIDPOSNAME, "Loading Screens..", true, false);
            MainActivity.proCustomDial.show();

        } else {
            //GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            GlobalMemberValues.openNetworkNotConnected();
        }
    }

    public static void setRecallCheck() {
        //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
        Intent recallIntent = new Intent(MainActivity.mContext.getApplicationContext(), Recall.class);
        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
        //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
        recallIntent.putExtra("", "");
        // -------------------------------------------------------------------------------------
        insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
        mActivity.startActivity(recallIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    public static void setHoldSalesCheck() {
        if (MainMiddleService.mGeneralArrayList.size() == 0) {
            GlobalMemberValues.displayDialog(context, "Warning", "No item to hold", "Close");
            return;
        }

        setHoldSales("INTENTOPEN");
//        new AlertDialog.Builder(context)
//                .setTitle("HOLD ITEM")
//                .setMessage("Hold all items?")
//                        //.setIcon(R.drawable.ic_launcher)
//                .setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                setHoldSales("INTENTOPEN");
//                            }
//                        })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //
//                    }
//                })
//                .show();
    }

    public static void setHoldSales(String paramFlag) {
        GlobalMemberValues.isHold = true;
        String tempHoldCode = "";
        if (MainMiddleService.mHoldCode.equals("NOHOLDCODE")) {
            if (GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                    "select count(idx) from temp_salecart where holdcode = 'NOHOLDCODE' ")) > 0) {

                tempHoldCode = GlobalMemberValues.makeHoldCode();
                String strSqlQuery = "update temp_salecart set " +
                        " holdcode ='" + tempHoldCode + "' " +
                        " where holdcode = 'NOHOLDCODE' ";

//                DatabaseInit dbInit = new DatabaseInit(context);
                String returnCode = "";
                returnCode = MssqlDatabase.executeTransactionByQuery(strSqlQuery);
                if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                    //MainMiddleService.clearOnlyListView();
                    MainMiddleService.mHoldCode = tempHoldCode;
                    openDeliveryTakeawayInfoIntent(paramFlag);
                }
            }
            GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (신규) : " + tempHoldCode + "\n");
        } else {
            tempHoldCode = MainMiddleService.mHoldCode;
            //MainMiddleService.clearOnlyListView();
            GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (기존) : " + tempHoldCode + "\n");
            openDeliveryTakeawayInfoIntent(paramFlag);
        }
        //GlobalMemberValues.setCustomerInfoInit();

//        String tempHoldCode = "";
//        if (MainMiddleService.mHoldCode.equals("NOHOLDCODE")) {
//            if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString(
//                    "select count(idx) from temp_salecart where holdcode = 'NOHOLDCODE' ")) > 0) {
//
//                tempHoldCode = GlobalMemberValues.makeHoldCode();
//                String strSqlQuery = "update temp_salecart set " +
//                        " holdcode ='" + tempHoldCode + "' " +
//                        " where holdcode = 'NOHOLDCODE' ";
//
//                DatabaseInit dbInit = new DatabaseInit(context);
//                String returnCode = "";
//                returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
//                if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
//                    //MainMiddleService.clearOnlyListView();
//                    MainMiddleService.mHoldCode = tempHoldCode;
//                    openDeliveryTakeawayInfoIntent(paramFlag);
//                }
//            }
//            GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (신규) : " + tempHoldCode + "\n");
//        } else {
//            tempHoldCode = MainMiddleService.mHoldCode;
//            //MainMiddleService.clearOnlyListView();
//            GlobalMemberValues.logWrite("CheckHoldCode", "mGetHoldCode (기존) : " + tempHoldCode + "\n");
//            openDeliveryTakeawayInfoIntent(paramFlag);
//        }
//        //GlobalMemberValues.setCustomerInfoInit();
    }

    public static void openDeliveryTakeawayInfoIntent(String paramFlag) {
        if (paramFlag.equals("INTENTOPEN")) {
            Payment.openGetFoodTypeIntent("");
            /**
            Intent deliverytakeawayIntent = new Intent(MainActivity.mContext.getApplicationContext(), DeliveryTakeawayInfo.class);
            //deliverytakeawayIntent.putExtra("HoldCode", paramHoldCode);
            mActivity.startActivity(deliverytakeawayIntent);
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
             **/
        } else {
            GlobalMemberValues.setCustomerInfoInit();
            MainMiddleService.clearOnlyListView();
        }
    }

    public static void setTaxExempt(int paramSelectedPosition) {
        /** temp_salecart 테이블 저장 및 TemporarySaleCart 객체에 저장된 값을 변경 *****************************************/
        String parentSelectedPosition = String.valueOf(paramSelectedPosition);
        parentTemporarySaleCart = MainMiddleService.mGeneralArrayList.get(GlobalMemberValues.getIntAtString(parentSelectedPosition));

        // BAL 단가 합계 변경 ------------------------------------------------------------------------------
        Double tempMSPriceBalAmount = 0.00;
        if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPriceBalAmount)) {
            tempMSPriceBalAmount = (Double.parseDouble(parentTemporarySaleCart.mSPriceBalAmount));
        }
        // ---------------------------------------------------------------------------------------------

        // 세금 합계 변경 ------------------------------------------------------------------------------
        String insMSTax = "0.0";

        Double tempMSTaxAmount = 0.00;
        String insMSTaxAmount = "0.0";
        // ---------------------------------------------------------------------------------------------
        // 총액 변경 -----------------------------------------------------------------------------------
        Double tempMSTotalAmount = tempMSPriceBalAmount + tempMSTaxAmount;
        String insMSTotalAmount = "0.0";
        if (tempMSTotalAmount > 0) {
            insMSTotalAmount = String.format("%.2f", tempMSTotalAmount);
        }
        // ---------------------------------------------------------------------------------------------

        String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
        if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
            String strSqlQuery = "update temp_salecart set " +
                    //" sTax ='" + insMSTax + "', " +
                    " sTaxAmount ='" + insMSTaxAmount + "', " +
                    " sTotalAmount ='" + insMSTotalAmount + "', " +
                    " taxExempt = 'Y' " +
                    " where idx = '" + tempSaleCartIdx + "' ";
            GlobalMemberValues.logWrite("TaxExemptUpdateQuery", strSqlQuery + "\n");
            DatabaseInit dbInit = new DatabaseInit(context);
            String returnCode = "";
            returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
            GlobalMemberValues.logWrite("updateQuery", "returnCode : " + returnCode + "\n");
            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                // TemporarySaleCart 객체 변수값 변경
                parentTemporarySaleCart.mSTaxAmount = insMSTaxAmount;
                parentTemporarySaleCart.mSTotalAmount = insMSTotalAmount;
                parentTemporarySaleCart.mTaxExempt = "Y";

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    MainMiddleService.isCheckedConfrim = new boolean[MainMiddleService.mGeneralArrayList.size()];
                }

                // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                MainMiddleService.mGeneralArrayList.set(Integer.parseInt(parentSelectedPosition), parentTemporarySaleCart);
                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                String[] strValueForTextView = null;
                strValueForTextView = MainMiddleService.getCalcSubTotalTaxTotalValue(MainMiddleService.mGeneralArrayList);
                MainMiddleService.setCalcSubTotalTaxTotalValue(strValueForTextView);

                if (!GlobalMemberValues.isMultiCheckOnCart()) {
                    MainMiddleService.selectedPosition = -1;
                }
            }
        }
        /*****************************************************************************************************/
    }

    public void setUndoTaxExempt() {
        if (GlobalMemberValues.isMultiCheckOnCart()) {
            int tempSize = MainMiddleService.mGeneralArrayList.size();
            if (tempSize == 0) {
                return;
            }

            boolean[] copyIsCheckedConfrim = MainMiddleService.isCheckedConfrim;

            int checkedItemCount = 0;
            for(int i = 0; i < tempSize; i++){
                if(copyIsCheckedConfrim[i]){
                    checkedItemCount++;
                }
            }
            if (MainMiddleService.mGeneralArrayList.size() > 0 && checkedItemCount > 0) {
                boolean tempLastItem = false;
                int tempCount = 0;
                for(int j = (tempSize - 1); j >= 0; j--){
                    if(copyIsCheckedConfrim[j]){
                        tempCount++;
                        if (checkedItemCount == tempCount) {
                            tempLastItem = true;
                        } else {
                            tempLastItem = false;
                        }

                        setUndoTaxExemptItem(j);
                    }
                }
            }
        } else {
            setUndoTaxExemptItem(MainMiddleService.selectedPosition);
        }
    }

    public static void setUndoTaxExemptItem(int paramSelectedPosition) {

        /** temp_salecart 테이블 저장 및 TemporarySaleCart 객체에 저장된 값을 변경 *****************************************/
        String parentSelectedPosition = String.valueOf(paramSelectedPosition);
        parentTemporarySaleCart = MainMiddleService.mGeneralArrayList.get(paramSelectedPosition);

        // BAL 단가 합계 변경 ------------------------------------------------------------------------------
        Double tempMSPriceBalAmount = 0.00;
        if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPriceBalAmount)) {
            tempMSPriceBalAmount = (Double.parseDouble(parentTemporarySaleCart.mSPriceBalAmount));
        }
        // ---------------------------------------------------------------------------------------------

        // 세금 합계 변경 ------------------------------------------------------------------------------
        String tempSaveType = "0";
        if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSaveType)) {
            tempSaveType = parentTemporarySaleCart.mSaveType;
        }

        GlobalMemberValues.logWrite("getItemTaxFreeYN", "tempSaveType (Command) : " + tempSaveType + "\n");

        String insSvcidx = parentTemporarySaleCart.mSvcidx;

        double taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;
        switch (tempSaveType) {
            case "0" : {        // 서비스일 때
                taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;
                // DB 에서 해당상품의 TAXFREEYN 을 가져온다.
                if (GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "S", insSvcidx) == "Y" ||
                        GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "S", insSvcidx).equals("Y")) {
                    taxToUse = 0.00;
                } else {
                    // TAX 타입이 멀티일 경우
                    if (GlobalMemberValues.isTaxTypeMulti("S", insSvcidx)) {
                        taxToUse = GlobalMemberValues.getItemTaxInMultiTax("S", insSvcidx);
                    }
                }
                break;
            }
            case "1" : {        // 상품일 때
                taxToUse = GlobalMemberValues.STORE_PRODUCT_TAX;
                // DB 에서 해당상품의 TAXFREEYN 을 가져온다.
                if (GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "P", insSvcidx) == "Y" ||
                        GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "P", insSvcidx).equals("Y")) {
                    taxToUse = 0.00;
                }
                break;
            }
            case "2" : {        // 기프트 카드일 때는 tax 가 안붙는다.
                taxToUse = 0.0;
                break;
            }
        }
        double tempMSTaxAmount = (tempMSPriceBalAmount * taxToUse) * 0.01;
        String insMSTaxAmount = "0.0";
        if (tempMSTaxAmount > 0) {
            insMSTaxAmount = String.format("%.2f", tempMSTaxAmount);
        }

        String insMSTax = insMSTaxAmount;
        int tempQty = GlobalMemberValues.getIntAtString(parentTemporarySaleCart.mSQty);
        if (tempQty > 0) {
            insMSTax = GlobalMemberValues.getStringFormatNumber((tempMSTaxAmount / tempQty), "2");
        }
        // ---------------------------------------------------------------------------------------------

        // 총액 변경 -----------------------------------------------------------------------------------
        Double tempMSTotalAmount = tempMSPriceBalAmount + tempMSTaxAmount;
        String insMSTotalAmount = "0.0";
        if (tempMSTotalAmount > 0) {
            insMSTotalAmount = String.format("%.2f", tempMSTotalAmount);
        }
        // ---------------------------------------------------------------------------------------------

        String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
        if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
            String strSqlQuery = "update temp_salecart set " +
                    //" sTax ='" + insMSTax + "', " +
                    " sTaxAmount ='" + insMSTaxAmount + "', " +
                    " sTotalAmount ='" + insMSTotalAmount + "', " +
                    " taxExempt ='N' " +
                    " where idx = '" + tempSaleCartIdx + "' ";
            GlobalMemberValues.logWrite("TaxExemptUpdateQuery", strSqlQuery + "\n");
            DatabaseInit dbInit = new DatabaseInit(context);
            String returnCode = "";
            returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
            GlobalMemberValues.logWrite("updateQuery", "returnCode : " + returnCode + "\n");
            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                // TemporarySaleCart 객체 변수값 변경
                parentTemporarySaleCart.mSTaxAmount = insMSTaxAmount;
                parentTemporarySaleCart.mSTotalAmount = insMSTotalAmount;
                parentTemporarySaleCart.mTaxExempt = "";

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    MainMiddleService.isCheckedConfrim = new boolean[MainMiddleService.mGeneralArrayList.size()];
                }

                // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                MainMiddleService.mGeneralArrayList.set(Integer.parseInt(parentSelectedPosition), parentTemporarySaleCart);
                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                String[] strValueForTextView = null;
                strValueForTextView = MainMiddleService.getCalcSubTotalTaxTotalValue(MainMiddleService.mGeneralArrayList);
                MainMiddleService.setCalcSubTotalTaxTotalValue(strValueForTextView);

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    MainMiddleService.selectedPosition = -1;
                }
            }
        }
        /*****************************************************************************************************/
    }

    public static void openCashDrawer() {
        // 지훈아 이곳에 돈통 열리는 코드를 입력하면 된다~

        // reprint 돈통 off
        if (GlobalMemberValues.mReReceiptprintYN.equals("Y")) return;

        String tempPrinterName = GlobalMemberValues.getSavedPrinterName(MainActivity.mContext);

        switch (tempPrinterName) {
            case "STAR": {
                //  test 필요함
//                GlobalMemberValues.printReceiptByJHP(null, context, "openCashDrawer");
                StarPrintStart starPrintStart = new StarPrintStart();
                starPrintStart.openDrawer();
                break;
            }
            case "PosBank" : {
                GlobalMemberValues.printReceiptByJHP(null, context, "openCashDrawer");
                break;
            }
            case "Elo" : {
                Elo elo = new Elo(MainActivity.mContext);
                elo.eloInit();
                if (Elo.mApiAdapter != null) {
                    GlobalMemberValues.eloOpenDrawer(Elo.mApiAdapter);
                }
                break;
            }
            case "PAX" : {
                ProcessResult result = POSLinkCashDrawer.getInstance(MainActivity.mContext).open();
                if (!result.getCode().equals(ProcessResult.CODE_OK)) {
                }
                break;
            }
            case "Giant-100":{
                Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
                giantPrinter.connect(MainActivity.mContext, null, "openCashDrawer");
                break;
            }
            case "Epson-T88" : {

                //
                GlobalMemberValues.printReceiptByJHP(null, context, "openCashDrawer");
                //
                break;

            }
        }
    }
}
