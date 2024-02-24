package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by pc on 2016-12-21.
 */

public class AppUpdate_PrintingApp {
    static final String TAG = "AppUpdate";

    public static ProgressDialog itemProDial;
    public static int mTempFlagItemDown = 0;

    public static Activity mActivity;
    public static Context mContext;

    public AppUpdate_PrintingApp() {

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

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.example.jjbfather.jjjprinter"));
            paramActivity.startActivity(intent);
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

            // 업데이트 필요
            new AlertDialog.Builder(paramContext)
                    .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                    .setMessage("Would you download the printing app?")
                    .setCancelable(true)
                    .setPositiveButton("Download Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            itemProDial = ProgressDialog.show(
                                    paramContext, "NZQSR PRINTING APP", "PRINTING APP APK File is Downloading...", true, false
                            );

                            // 1. 처리가 오래걸리는 부분 실행 -----------------------------------------------------
                            // FTP 에서 설치파일(apk) 다운로드 ----------------------------------------------------
                            FileDownloadByFTP downFtp = new FileDownloadByFTP(MainActivity.mContext);
                            downFtp.downloadAppType = "P";
                            downFtp.downloadFileStr = Environment.getExternalStorageDirectory().toString() + "/Download/QSRt_PrintingApp.apk";
                            downFtp.downloadFileOfFtp = "APPAPK/QSRt_PrintingApp.apk";
                            downFtp.downloadFileType = "A";

                            GlobalMemberValues.logWrite(TAG, "file down loc : " + downFtp.downloadFileStr + "\n");
                            GlobalMemberValues.logWrite(TAG, "file ftp loc : " + downFtp.downloadFileOfFtp + "\n");

                            downFtp.execute();
                            // ---------------------------------------------------------------------------------

                        }
                    })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                    .show();

        }
    }

    public static void installUpdateFile() {
        if (mTempFlagItemDown > 0) {
            // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
            itemProDial.dismiss();
            // -------------------------------------------------------------------------------------

            if (!MainActivity.mActivity.isFinishing()) {
                new AlertDialog.Builder(SettingsDeviceKitchen.mActivity)
                        .setTitle(GlobalMemberValues.ANDROIDPOSNAME)
                        .setMessage("Do you want to install printing app?")
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
            File downloadFile = new File(sd, "Download/QSRt_PrintingApp.apk");
            if (downloadFile.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    GlobalMemberValues.logWrite(TAG + "log", "여기실행1..." + "\n");
                    GlobalMemberValues.logWrite(TAG + "log", "package name : " + GlobalMemberValues.PRINTINGAPPPACKAGENAME + "\n");

                    //intent.setDataAndType( Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
                    uri = FileProvider.getUriForFile(MainActivity.mContext, GlobalMemberValues.PRINTINGAPPPACKAGENAME + ".fileprovider", downloadFile);
                } else {
                    GlobalMemberValues.logWrite(TAG + "log", "여기실행2..." + "\n");
                    GlobalMemberValues.logWrite(TAG + "log", "package name : " + GlobalMemberValues.PRINTINGAPPPACKAGENAME + "\n");

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
}
