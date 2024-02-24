package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by pc on 2016-07-25.
 */
public class FileUploadByFTP extends android.os.AsyncTask<Void,Void,Void>{
    // 호출하는 클래스에서 설정해야 하는 값 ------------------------------------------------------
    public String uploadFileStr = "";           // 로컬에 있는 파일 경로+파일. 확장자 포함
    public boolean openDialog = false;
    // ---------------------------------------------------------------------------------------

    int resultCode = 2;                         // 기본값을 실패(2) 로 한다.

    //FTPConnector ftpConnector = new FTPConnector("106.246.239.220",1213,"jihunpark","Wlgnsvkr@01^","UTF-8","");
    FTPConnector ftpConnector = new FTPConnector(GlobalMemberValues.M_FTPIP, GlobalMemberValues.M_FTPPORT, GlobalMemberValues.M_FTPID,
            GlobalMemberValues.M_FTPPWD, GlobalMemberValues.M_FTPENCODING, GlobalMemberValues.M_FTPBASCIDIR);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean loginResult = ftpConnector.ftpLogin();
        //String data = "/storage/emulated/0/a.txt";

        if (loginResult) {
            resultCode = ftpConnector.ftpUploadFile(uploadFileStr, GlobalMemberValues.STATION_CODE);
        } else {
            if (openDialog) {
                // 로그인 실패
                GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud server login failed", "Close");
            } else {
                //Toast.makeText(MainActivity.mContext, "Cloud server login failed", Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (CommandButton.itemProDial != null && CommandButton.itemProDial.isShowing()) {
            CommandButton.itemProDial.dismiss();
        }

        if (openDialog) {
            // 결과값을 Dialog 로 알려준다.
            switch (resultCode) {
                case 0 : {
//                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud backup has been successfully completed", "Close");
                    GlobalMemberValues.ftpUpLoaddisplayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud backup has been successfully completed", "Close");
                    break;
                }
                case 1 : {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud server is not connected", "Close");
                    break;
                }
                case 2 : {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Database has not been uploaded to cloud server", "Close");
                    break;
                }
                case 3 : {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "There is a problem while uploading database", "Close");
                    break;
                }
                case 4 : {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud server connection error occurred\nwhile closing the stream Logout", "Close");
                    break;
                }
                case 5 : {
                    GlobalMemberValues.displayDialog(MainActivity.mContext, "NAVYZEBRA QSR POS t", "Cloud server error occurred\nduring the connection termination", "Close");
                    break;
                }
            }
        } else {
            // 결과값을 Dialog 로 알려준다.
            switch (resultCode) {
                case 0 : {
//                    Toast.makeText(MainActivity.mContext, "Cloud backup has been successfully completed.", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 1 : {
//                    Toast.makeText(MainActivity.mContext, "Cloud server is not connected", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 2 : {
//                    Toast.makeText(MainActivity.mContext, "Database has not been uploaded to cloud server", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 3 : {
//                    Toast.makeText(MainActivity.mContext, "There is a problem while uploading database", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 4 : {
//                    Toast.makeText(MainActivity.mContext, "Cloud server connection error occurred\n" +
//                            "while closing the stream Logout", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 5 : {
//                    Toast.makeText(MainActivity.mContext, "Cloud server error occurred\n" +
//                            "during the connection termination", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}