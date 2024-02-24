package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pc on 2016-07-25.
 */

public class FileDownloadByFTP extends android.os.AsyncTask<Void,Void,Void>{
    // 호출하는 클래스에서 설정해야 하는 값 ----------------------------------------------------------------------------------------------
    public String downloadFileStr = "";             // FTP 에서 다운로드 받은 파일을 Local 태블릿에 저장할 폴더+파일명. 확장자 포함
    public String downloadFileOfFtp = "";           // FTP 에서 다운로드 받을 폴더+파일명. 확장자 포함
    // -------------------------------------------------------------------------------------------------------------------------------

    public String downloadFileType = "";            // "" : 데이터베이스 다운로드    A : 업데이트 설치앱 다운로드

    public String downloadAppType = "";             // "" : QSR t POS 파일 다운로드   P : 프린팅앱 다운로드

    int resultCode = 2;                             // 기본값을 실패(2) 로 한다.

    //FTPConnector ftpConnector = new FTPConnector("106.246.239.220",1213,"jihunpark","Wlgnsvkr@01^","UTF-8","");
    FTPConnector ftpConnector = new FTPConnector(GlobalMemberValues.M_FTPIP, GlobalMemberValues.M_FTPPORT, GlobalMemberValues.M_FTPID,
            GlobalMemberValues.M_FTPPWD, GlobalMemberValues.M_FTPENCODING, GlobalMemberValues.M_FTPBASCIDIR);
    Context context;
    public FileDownloadByFTP(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        boolean loginResult = ftpConnector.ftpLogin();
        //String data = "/storage/emulated/0/a.txt";

        if (loginResult) {
            resultCode = ftpConnector.ftpDownloadFile(downloadFileOfFtp, downloadFileStr);
        } else {
            // 로그인 실패
            GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "Cloud server login failed", "Close");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // 결과값을 Dialog 로 알려준다.
        switch (resultCode) {
            case 0 : {
                if (GlobalMemberValues.isStrEmpty(downloadFileType)) {
                    Toast.makeText(MainActivity.mContext, "Database has been restored successfully from cloud server", Toast.LENGTH_SHORT).show();
                    //GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Database has been restored successfully from cloud server", "Close");
                    CommandButton.restoreDatabase();
                    if (CommandButton.itemProDial != null){
                        if (CommandButton.itemProDial.isShowing()) {
                            CommandButton.itemProDial.dismiss();
                        }
                    }

                } else {
                    switch (downloadFileType) {
                        case "A" : {
                            if (GlobalMemberValues.isStrEmpty(downloadAppType)) {
                                AppUpdate.mTempFlagItemDown++;
                                Toast.makeText(MainActivity.mContext, "Update app file has been downloaded successfully from cloud server", Toast.LENGTH_SHORT).show();
                                AppUpdate.installUpdateFile();
                                if (AppUpdate.itemProDial.isShowing()) {
                                    AppUpdate.itemProDial.dismiss();
                                }
                            } else {
                                AppUpdate_PrintingApp.mTempFlagItemDown++;
                                Toast.makeText(MainActivity.mContext, "Printing app file has been downloaded successfully from cloud server", Toast.LENGTH_SHORT).show();
                                AppUpdate_PrintingApp.installUpdateFile();
                                if (AppUpdate_PrintingApp.itemProDial.isShowing()) {
                                    AppUpdate_PrintingApp.itemProDial.dismiss();
                                }
                            }

                            break;
                        }
                    }
                }
                break;
            }
            case 1 : {
                GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "Cloud server is not connected", "Close");
                break;
            }
            case 2 : {
                GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "Database has not been restored from cloud server", "Close");
                break;
            }
            case 3 : {
                GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "There is a problem while downloading database", "Close");
                break;
            }
            case 4 : {
                GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "Cloud Server connection error occurred\nwhile closing the stream Logout", "Close");
                break;
            }
            case 5 : {
                GlobalMemberValues.displayDialog(context, "NAVYZEBRA QSR POS t", "Cloud Server error occurred\nduring the connection termination", "Close");
                break;
            }
        }
        if (BackOfficeCommand.itemProDial != null){
            if (BackOfficeCommand.itemProDial.isShowing()) {
                BackOfficeCommand.itemProDial.dismiss();
            }
        }
    }
}