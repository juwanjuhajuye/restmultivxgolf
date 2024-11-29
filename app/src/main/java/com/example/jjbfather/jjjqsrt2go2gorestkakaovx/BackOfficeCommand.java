package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class BackOfficeCommand extends Activity {
    static Activity mActivity_BackOfficeCommand;
    static Context mContext;

    private RelativeLayout btn_backup_database, btn_restore_database, btn_download_upload;
    private RelativeLayout btn_system_setting, btn_cloud_back_office;
    private RelativeLayout btn_cash_in_out, btn_cash_drawer, btn_gift_card_balance_check;
    private RelativeLayout btn_pos_sales_history, btn_online_sales_history, btn_end_of_day, btn_batch_summary;
    private Button btn_close;

    private TextView txt_store, txt_store_code, txt_station_code, txt_pushYN, txt_station_is_main;
    public static ProgressDialog itemProDial;
    static DatabaseInit dbInit;
    public ProgressDialog back_progress_dial;
    public Handler handler_loading_popup = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_backoffice_command);

        mActivity_BackOfficeCommand = this;
        mContext = this;
        dbInit = new DatabaseInit(mContext);

        setContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (back_progress_dial != null){
            if (back_progress_dial.isShowing()){
                back_progress_dial.cancel();
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1010:{
                this.finish();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void setContent(){
        txt_store = (TextView)findViewById(R.id.back_office_title_store);
        txt_store_code = (TextView)findViewById(R.id.back_office_title_store_code);
        txt_station_code = (TextView)findViewById(R.id.back_office_title_station_code);
        txt_pushYN = (TextView)findViewById(R.id.back_office_title_push);
        txt_station_is_main = (TextView)findViewById(R.id.back_office_title_station_name);

        btn_download_upload = (RelativeLayout)findViewById(R.id.back_office_download_upload);
        btn_download_upload.setOnClickListener(command_btn_clickListener);
        btn_backup_database = (RelativeLayout)findViewById(R.id.back_office_backup_database);
        btn_backup_database.setOnClickListener(command_btn_clickListener);
        btn_restore_database = (RelativeLayout)findViewById(R.id.back_office_restore_database);
        btn_restore_database.setOnClickListener(command_btn_clickListener);
        btn_system_setting = (RelativeLayout)findViewById(R.id.back_office_system_setting);
        btn_system_setting.setOnClickListener(command_btn_clickListener);
        btn_cloud_back_office = (RelativeLayout)findViewById(R.id.back_office_cloud_back_office);
        btn_cloud_back_office.setOnClickListener(command_btn_clickListener);
        btn_cash_in_out = (RelativeLayout)findViewById(R.id.back_office_cash_in_out);
        btn_cash_in_out.setOnClickListener(command_btn_clickListener);
        btn_cash_drawer = (RelativeLayout)findViewById(R.id.back_office_cash_drawer);
        btn_cash_drawer.setOnClickListener(command_btn_clickListener);
        btn_gift_card_balance_check = (RelativeLayout)findViewById(R.id.back_office_giftcard_balance_check);
        btn_gift_card_balance_check.setOnClickListener(command_btn_clickListener);
        btn_pos_sales_history = (RelativeLayout)findViewById(R.id.back_office_pos_sales_history);
        btn_pos_sales_history.setOnClickListener(command_btn_clickListener);
        btn_online_sales_history = (RelativeLayout)findViewById(R.id.back_office_online_sales_history);
        btn_online_sales_history.setOnClickListener(command_btn_clickListener);
        btn_end_of_day = (RelativeLayout)findViewById(R.id.back_office_end_of_day);
        btn_end_of_day.setOnClickListener(command_btn_clickListener);
        btn_batch_summary = (RelativeLayout)findViewById(R.id.back_office_batch_summary);
        btn_batch_summary.setOnClickListener(command_btn_clickListener);

        btn_close = (Button)findViewById(R.id.back_office_title_close_btn);
        btn_close.setOnClickListener(command_btn_clickListener);


        // Station 정보 가져와 storestationinfoTextView 에 기재하기 ----------------------------------------------
        String tempMainYN = "";
        String tempPushReceiveYN = "";
        String strQuery = "select mainYN, pushreceiveyn from salon_storestationinfo where stcode = '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "' ";
        Cursor stationinfoCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
        if (stationinfoCursor.getCount() > 0 && stationinfoCursor.moveToFirst()) {
            tempMainYN = GlobalMemberValues.getDBTextAfterChecked(stationinfoCursor.getString(0), 1);
            tempPushReceiveYN = GlobalMemberValues.getDBTextAfterChecked(stationinfoCursor.getString(1), 1);
            if (GlobalMemberValues.isStrEmpty(tempMainYN)) {
                tempMainYN = "N";
            }
            if (GlobalMemberValues.isStrEmpty(tempPushReceiveYN)) {
                tempPushReceiveYN = "Y";
            }
        }

        String tempStoreName = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select name from salon_storeinfo "
        );
        String storestationinfoStr = "";
        storestationinfoStr = "Store : <font color=\"#15addf\">" + tempStoreName + "</font>" +
                " / Store Code : <font color=\"#15addf\">" + GlobalMemberValues.STORE_INDEX + "</font>" +
                " / Station Code : <font color=\"#15addf\">" + GlobalMemberValues.STATION_CODE + "</font>";
        if (tempPushReceiveYN == "Y" || tempPushReceiveYN.equals("Y")) {
            storestationinfoStr += " / Push : <font color=\"#15addf\">Receiving</font>";
        } else {
            storestationinfoStr += " / Push : <font color=\"#15addf\">Not Receiving</font>";
        }
        if (tempMainYN == "Y" || tempMainYN.equals("Y")) {
            storestationinfoStr += " / <font color=\"#fb719d\">Main Station</font>";
        }
        GlobalMemberValues.logWrite("storestationinfolog", "SERIAL_NUMBER : " + GlobalMemberValues.SERIAL_NUMBER + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "tempMainYN : " + tempMainYN + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "tempPushReceiveYN : " + tempPushReceiveYN + "\n");
        GlobalMemberValues.logWrite("storestationinfolog", "store / station info : " + storestationinfoStr + "\n");

        txt_store.setText(tempStoreName);
        txt_store_code.setText(GlobalMemberValues.STORE_INDEX);
        txt_station_code.setText(GlobalMemberValues.STATION_CODE);
        if (tempPushReceiveYN == "Y" || tempPushReceiveYN.equals("Y")) {
            txt_pushYN.setText("Receiving");
        } else {
            txt_pushYN.setText("Not Receiving");
        }
        if (tempMainYN == "Y" || tempMainYN.equals("Y")){
            txt_station_is_main.setText("Main Station");
        } else {
            txt_station_is_main.setText("Not Main Station");
        }

    }

    View.OnClickListener command_btn_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back_office_download_upload :
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<6>")) {
                        GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                        return;
                    }

                    // 인터넷 연결되었을 경우에만
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(getApplicationContext());
                            return;
                        }

                        // os 버전 체크
                        // 마시멜로우 이상 버전에서 파일접근권한 이 없을 경우
                        GlobalMemberValues.setFileAccessPermission(MainActivity.mActivity, MainActivity.mContext);

                        // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
                        CommandButton.backupDatabase(false);

                        Intent adminPasswordIntent_back_office_download_upload = new Intent(getApplicationContext(), AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent_back_office_download_upload.putExtra("openClassMethod", "command_download");
                        adminPasswordIntent_back_office_download_upload.putExtra("openClassName", "backofficecommand");
                        // -------------------------------------------------------------------------------------
                        startActivity(adminPasswordIntent_back_office_download_upload);

                        if (GlobalMemberValues.isUseFadeInOut()) {
                            overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                        MainMiddleService.clearListView();

                        back_progress_dial = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, "Loading Screens..", true, false);
                        back_progress_dial.show();

                    } else {
                        //GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                case R.id.back_office_backup_database :
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<7>")) {
                        GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent_back_office_backup_database = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent_back_office_backup_database.putExtra("openClassMethod", "command_databasebackup");
                    adminPasswordIntent_back_office_backup_database.putExtra("openClassName", "backofficecommand");
                    // -------------------------------------------------------------------------------------
                    startActivity(adminPasswordIntent_back_office_backup_database);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                case R.id.back_office_restore_database :
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<7>")) {
                        GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent_back_office_restore_database = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent_back_office_restore_database.putExtra("openClassMethod", "login_databaserestore");
                    adminPasswordIntent_back_office_restore_database.putExtra("openClassName", "backofficecommand");
                    // -------------------------------------------------------------------------------------
                    startActivity(adminPasswordIntent_back_office_restore_database);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                case R.id.back_office_system_setting :
                    LogsSave.saveLogsInDB(22);
                    //GlobalMemberValues.displayDialog(context, "NAVYZEBRA MSALON ANDROID", "Under Construction", "Close");
                    Intent settingsIntent = new Intent(MainActivity.mContext, Settings.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    startActivity(settingsIntent);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                case R.id.back_office_cloud_back_office :
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        LogsSave.saveLogsInDB(23);
                        if (!GlobalMemberValues.isOnline().equals("00")) {
                            GlobalMemberValues.showDialogNoInternet(getApplicationContext());
                            return;
                        }
                        Intent adminPasswordIntent_back_office_cloud_back_office = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        adminPasswordIntent_back_office_cloud_back_office.putExtra("openClassMethod", "command_cloud");
                        adminPasswordIntent_back_office_cloud_back_office.putExtra("openClassName", "backofficecommand");
                        // -------------------------------------------------------------------------------------

                        startActivity(adminPasswordIntent_back_office_cloud_back_office);

                        if (GlobalMemberValues.isUseFadeInOut()) {
                            overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }
                    } else {
                        GlobalMemberValues.openNetworkNotConnected();
                    }
                    break;
                case R.id.back_office_cash_in_out :
                    LogsSave.saveLogsInDB(24);
                    if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                        //GlobalMemberValues.displayDialog(context, "Cash In/Out", "Under Construction", "Close");
                        Intent intentCashInOutPopupPrevAdmin = new Intent(MainActivity.mContext.getApplicationContext(), CashInOutPopupPreviousListAdmin.class);
                        startActivity(intentCashInOutPopupPrevAdmin);

                        if (GlobalMemberValues.isUseFadeInOut()) {
                            overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
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
                                    Intent intentCashInOutPopup = new Intent(MainActivity.mContext.getApplicationContext(), CashInOutPopup.class);
                                    startActivity(intentCashInOutPopup);

                                    if (GlobalMemberValues.isUseFadeInOut()) {
                                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }).start();

                    }
                    break;
                case R.id.back_office_cash_drawer :
                    LogsSave.saveLogsInDB(25);
                    CommandButton.openCashDrawer();
                    break;
                case R.id.back_office_giftcard_balance_check :
                    LogsSave.saveLogsInDB(26);
                    Intent GiftCardBalanceCheck = new Intent(MainActivity.mContext, GiftCardBalanceCheck.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    // -------------------------------------------------------------------------------------
                    startActivity(GiftCardBalanceCheck);

                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                case R.id.back_office_pos_sales_history :
                    LogsSave.saveLogsInDB(27);
                    Intent saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistoryList.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    saleHistoryIntent.putExtra("saleHistoryTagValue", "");
                    // 03102018
                    GlobalMemberValues.sh_fromCommand = "Y";
                    // -------------------------------------------------------------------------------------
                    startActivity(saleHistoryIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                case R.id.back_office_online_sales_history :
                    LogsSave.saveLogsInDB(28);
                    Intent online_saleHistoryIntent = new Intent(MainActivity.mContext.getApplicationContext(), SaleHistory_web.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                    online_saleHistoryIntent.putExtra("saleHistoryTagValue", "");
                    GlobalMemberValues.sh_fromCommand = "Y";
                    startActivity(online_saleHistoryIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                case R.id.back_office_end_of_day :
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<11>")) {
                        GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent_back_office_end_of_day = new Intent(getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent_back_office_end_of_day.putExtra("openClassMethod", "endofday");
                    adminPasswordIntent_back_office_end_of_day.putExtra("openClassName", "backofficecommand");
                    // -------------------------------------------------------------------------------------
                    startActivity(adminPasswordIntent_back_office_end_of_day);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                case R.id.back_office_batch_summary :
                    // 권한체크
                    if (!GlobalMemberValues.checkEmployeePermission(
                            TableSaleMain.ID_forPermission, TableSaleMain.NAME_forPermission, "<5>")) {
                        GlobalMemberValues.displayDialog(getApplicationContext(), "Warning", "You do not have permission", "Close");
                        return;
                    }

                    Intent adminPasswordIntent_adminPasswordIntentback_office_batch_summary = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    adminPasswordIntent_adminPasswordIntentback_office_batch_summary.putExtra("openClassMethod", "command_batchsummary");
                    adminPasswordIntent_adminPasswordIntentback_office_batch_summary.putExtra("openClassName", "backofficecommand");
                    // -------------------------------------------------------------------------------------
                    startActivity(adminPasswordIntent_adminPasswordIntentback_office_batch_summary);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                case R.id.back_office_title_close_btn :
                    finish();
                    break;
                    default :
                        break;
            }
        }
    };

    public static void restoreDatabaseAfterCheckCloudBackup() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            String strCloudDbBackUpYN = GlobalMemberValues.getCheckCloudBackupYN(GlobalMemberValues.STATION_CODE);
            GlobalMemberValues.logWrite("CommandLog", "strCloudDbBackUpYN  : " + strCloudDbBackUpYN + "\n");
            // 클라우드 백업을 사용하는 경우
            if (strCloudDbBackUpYN == "Y" || strCloudDbBackUpYN.equals("Y")) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Local / Cloud Retore")
                        .setMessage("You can restore from the cloud system.\nTouch [Cloud System] button to restore from the cloud system\n" +
                                "or Touch [Local System] button to restore from the local system")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Cloud System",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!GlobalMemberValues.isOnline().equals("00")) {
                                            GlobalMemberValues.showDialogNoInternet(mContext);
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
        itemProDial = ProgressDialog.show(mContext, "Cloud Backup", "The database is being restored from the cloud...", true, false);

        FileDownloadByFTP downFtp = new FileDownloadByFTP(mContext);
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

                GlobalMemberValues.displayDialog(mContext, "Database Restore", "Database Restored OK", "Close");
                GlobalMemberValues.logWrite("commandButtonDatabase", "복원결과 : OK\n");

                // DB 복원후........데이터베이스 생성 및 테이블 생성, 추가, 삭제 및 컬럼 추가, 삭제
                dbInit.initDatabaseTables();
            }
        } catch (Exception e) {
            if (!MainActivity.mActivity.isFinishing()) {
                Toast.makeText(MainActivity.mContext, "Database restoration is failed", Toast.LENGTH_SHORT).show();
            }

            GlobalMemberValues.displayDialog(mContext, "Database Restore", "Database restoration is failed", "Close");
            GlobalMemberValues.logWrite("commandButtonDatabase", "에러메시지 : " + e.getMessage().toString() + "\n");
        }
    }

}
