package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by pc on 2016-12-21.
 */

public class AppUpdate {
    static final String TAG = "AppUpdate";

    public static ProgressDialog itemProDial;
    public static int mTempFlagItemDown = 0;

    public static Activity mActivity;
    public static Context mContext;

    public AppUpdate() {

    }

    // 플레이스토어를 통한 앱 업데이트
    public static void appUpdateFromPlayStore(final Activity paramActivity, Context paramContext) {
        mTempFlagItemDown = 0;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(mContext);
                return;
            }

            mActivity = paramActivity;
            mContext = paramContext;

            // 안드로이드 포스 앱 버전 체크 및 플레이스토어 연결 ------------------------------------------------------------
            // GooglePlay 버전정보
            //String appVersionOnStore = "1.1.1";
            String appVersionOnStore = "";
            appVersionOnStore = MarketVersionChecker.getMarketVersionFast(paramContext.getPackageName());

            // Device 버전정보
            String appVersionOnDevice = "";
            try {
                appVersionOnDevice = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                GlobalMemberValues.logWrite(TAG, "erro : " + e.getMessage().toString() + "\n");
            }

            GlobalMemberValues.logWrite(TAG, "store version : " + appVersionOnStore + "\n");
            GlobalMemberValues.logWrite(TAG, "device version : " + appVersionOnDevice + "\n");

            if(!GlobalMemberValues.isStrEmpty(appVersionOnStore) && !GlobalMemberValues.isStrEmpty(appVersionOnDevice))
            {
                GlobalMemberValues.logWrite(TAG, "compare : " + appVersionOnStore.compareTo(appVersionOnDevice) + "\n");
                if (appVersionOnStore.compareTo(appVersionOnDevice) > 0) {
                    // 업데이트 필요
                    new AlertDialog.Builder(paramContext)
                            .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                            .setMessage("Would you update this NZQSR POS?")
                            .setCancelable(true)
                            .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.example.jjbfather.jjjqsr"));
                                    paramActivity.startActivity(intent);
                                }
                            })
                            .setNegativeButton("No Update",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //
                                        }
                                    })
                            .show();
                } else {
                    // 업데이트 불필요

                }
            }
            // ----------------------------------------------------------------------------------------------------------
        }
    }

    // 클라우드 서버 FTP 를 통한 앱 업데이트
    public static void appUpdateFromCloudServer(final Activity paramActivity, final Context paramContext) {
        mTempFlagItemDown = 0;

        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                GlobalMemberValues.showDialogNoInternet(mContext);
                return;
            }

            mActivity = paramActivity;
            mContext = paramContext;

            String appVersionOnCloud = "";
            appVersionOnCloud = getAppVersionOnCloud();

            // Device 버전정보
            String appVersionOnDevice = "";
            try {
                appVersionOnDevice = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                GlobalMemberValues.logWrite(TAG, "err : " + e.getMessage().toString() + "\n");
            }

            GlobalMemberValues.logWrite(TAG, "cloud version : " + appVersionOnCloud + "\n");
            GlobalMemberValues.logWrite(TAG, "device version : " + appVersionOnDevice + "\n");

            if(!GlobalMemberValues.isStrEmpty(appVersionOnCloud) && !GlobalMemberValues.isStrEmpty(appVersionOnDevice)) {
                GlobalMemberValues.logWrite(TAG, "compare : " + appVersionOnCloud.compareTo(appVersionOnDevice) + "\n");
                if (appVersionOnCloud.compareTo(appVersionOnDevice) > 0) {
                    if (!paramActivity.isFinishing()) {
                        // 업데이트 필요
                        new AlertDialog.Builder(paramContext)
                                .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                                .setMessage("Would you download new version QSRt?")
                                .setCancelable(true)
                                .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        itemProDial = ProgressDialog.show(
                                                paramContext, "NZQSR POS Application Update", "Update File is Downloading...", true, false
                                        );

                                        // 1. 처리가 오래걸리는 부분 실행 -----------------------------------------------------
                                        // FTP 에서 설치파일(apk) 다운로드 ----------------------------------------------------
                                        FileDownloadByFTP downFtp = new FileDownloadByFTP(MainActivity.mContext);
                                        downFtp.downloadAppType = "";
                                        downFtp.downloadFileStr = Environment.getExternalStorageDirectory().toString() + "/Download/NZQSR.apk";
                                        downFtp.downloadFileOfFtp = "APPAPK/NZQSR.apk";
                                        downFtp.downloadFileType = "A";

                                        GlobalMemberValues.logWrite(TAG, "file down loc : " + downFtp.downloadFileStr + "\n");
                                        GlobalMemberValues.logWrite(TAG, "file ftp loc : " + downFtp.downloadFileOfFtp + "\n");

                                        downFtp.execute();
                                        // ---------------------------------------------------------------------------------
                                    }
                                })
                                .setNegativeButton("No Update",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //
                                            }
                                        })
                                .show();
                    }

                } else {
                    // 업데이트 불필요

                }
            }
        }
    }

    public static void installUpdateFile() {
        if (mTempFlagItemDown > 0) {
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            itemProDial.dismiss();
            // -------------------------------------------------------------------------------------

            if (!MainActivity.mActivity.isFinishing()) {
                new AlertDialog.Builder(MainActivity.mActivity)
                        .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                        .setMessage("Do you want to install new version?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                installNewApp();
                            }
                        }).show();
            }

            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------


            // -------------------------------------------------------------------------------------

            mTempFlagItemDown = 0;
        }
    }

    public static void installNewApp() {
        // 다운로드 한 설치파일(apk)로 앱 재설치 ----------------------------------------------------------------
        File sd = Environment.getExternalStorageDirectory();
        try {
            GlobalMemberValues.logWrite(TAG + "log", "sd : " + sd.toString() + "\n");
            File downloadFile = new File(sd, "download/NZQSR.apk");
            if (downloadFile.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    GlobalMemberValues.logWrite(TAG + "log", "여기실행1..." + "\n");
                    GlobalMemberValues.logWrite(TAG + "log", "package name : " + MainActivity.mContext.getApplicationContext().getPackageName() + "\n");

                    //intent.setDataAndType( Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
                    uri = FileProvider.getUriForFile(MainActivity.mContext, MainActivity.mContext.getApplicationContext().getPackageName() + ".fileprovider", downloadFile);
                } else {
                    GlobalMemberValues.logWrite(TAG + "log", "여기실행2..." + "\n");
                    GlobalMemberValues.logWrite(TAG + "log", "package name : " + MainActivity.mContext.getApplicationContext().getPackageName() + "\n");

                    uri = Uri.fromFile(downloadFile);
                }

                GlobalMemberValues.logWrite(TAG + "log", "여기실행3..." + "\n");
                intent.setDataAndType( uri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mActivity.startActivity(intent);
            }

            // 현재 앱 종료
            //GlobalMemberValues.setCloseAndroidAppMethod(mActivity);

        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG + "log", e.getMessage().toString() + "\n");
        }

        // --------------------------------------------------------------------------------------------------
    }

    // 클라우드 서버를 통한 앱 버전 확인
    public static String getAppVersionOnCloud() {
        String returnValue = "";

        API_appupdate_check apicheckInstance = new API_appupdate_check();
        apicheckInstance.execute(null, null, null);
        try {
            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
            if (apicheckInstance.mFlag) {
                returnValue = apicheckInstance.mReturnValue;
            }
        } catch (InterruptedException e) {
            returnValue = "";
        }

        GlobalMemberValues.logWrite(TAG, "returnValue : " + returnValue + "\n");
        return returnValue;
    }
}
