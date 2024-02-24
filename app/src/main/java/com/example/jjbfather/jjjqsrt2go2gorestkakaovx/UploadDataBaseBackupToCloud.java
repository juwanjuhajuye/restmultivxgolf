package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class UploadDataBaseBackupToCloud extends Service implements Runnable {
    Context context;

    // SalesCode
    String receivedSalesCode;

    String openDialog = "";

    public UploadDataBaseBackupToCloud() {
        this.context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (intent != null) {
            openDialog = intent.getStringExtra("openDialog");
        }


        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            backupToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(Payment.context, "Warning", e.getMessage().toString(), "Close");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void backupToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            GlobalMemberValues.logWrite("CloudBackup", "openDialog2 : " + openDialog + "\n");

            /** 1. DB 명 + 스테이션 코드 백업 **********************************************************************/
            // FTP 에 저장할 폴더
            GlobalMemberValues.M_FTPBASCIDIR = "";      // 기본 폴더로..

            FileUploadByFTP upFtp = new FileUploadByFTP();
            upFtp.uploadFileStr = Environment.getExternalStorageDirectory().toString() + "/Download/" + GlobalMemberValues.DATABASE_NAME;
            //upFtp.openDialog = paramOpenDialog;

            boolean tempBoolean = false;
            if (openDialog.equals("Y") || openDialog == "Y") {
                tempBoolean = true;
            } else {
                tempBoolean = false;
            }
            upFtp.openDialog = tempBoolean;
            // true 로 할 경우 Dialog 로 보여진다.
            GlobalMemberValues.logWrite("MainActivityLog", "file loc : " + upFtp.uploadFileStr + "\n");
            upFtp.execute();
            /****************************************************************************************************/

            int maxNum = GlobalMemberValues.saveBackupLog();

            /** 2. 이중백업을 위한 폴더에 백업Db 업로드 **************************************************************/
            // FTP 에 저장할 폴더
            int tempFolderNum = 1;
            if (maxNum > 5) {
                tempFolderNum = maxNum % 5;
            } else {
                tempFolderNum = maxNum;
            }
            GlobalMemberValues.M_FTPBASCIDIR = "SecondBackUp\\" + tempFolderNum;

            FileUploadByFTP upFtp2 = new FileUploadByFTP();
            upFtp2.uploadFileStr = Environment.getExternalStorageDirectory().toString() + "/Download/" + GlobalMemberValues.DATABASE_NAME;
            //upFtp2.openDialog = paramOpenDialog;

            boolean tempBoolean2 = false;
            if (openDialog.equals("Y") || openDialog == "Y") {
                tempBoolean2 = true;
            } else {
                tempBoolean2 = false;
            }
            upFtp2.openDialog = tempBoolean2;
            // true 로 할 경우 Dialog 로 보여진다.
            GlobalMemberValues.logWrite("MainActivityLog", "file loc : " + upFtp2.uploadFileStr + "\n");
            upFtp2.execute();
            /****************************************************************************************************/
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_DBBACKUP != null && GlobalMemberValues.CURRENTSERVICEINTENT_DBBACKUP != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_DBBACKUP.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_DBBACKUP);
    }
}
