package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by pc on 2016-07-25.
 */
public class FTPConnector {
    final String TAG = "FTPConnectLog";


    private String SERVER = ""; // FTP 호스트 주소
    private int port = 21; //포트번호
    private String ID = ""; // 유저 아이디
    private String PASS = ""; // 유저 패스워드
    private String mEncodingSet = ""; // 케릭터 셋
    private String mDefaultWorkDirectory = "/"; // 기본 작업 디렉토리

    private FTPClient mFtp = null;
    private FileInputStream fis = null;
    private OutputStream fos =null;
    /**
     * FTPConnector의 객체를 생성합니다.<br/>
     *
     * @param server
     *            서버 호스트 주소입니다.
     * @param id
     *            로그인 아이디입니다.
     * @param pass
     *            로그인 패스워드입니다.
     * @param encodingSet
     *            인코딩 종류를 지정합니다. UTF-8 or EUC-KR
     * @param workDirctory
     *            작업을 진행할 FTP 서버의 디렉터리를 지정합니다.<br/>
     *            예)/www/test
     */
    public FTPConnector(String server, int port, String id, String pass,
                        String encodingSet, String workDirctory) {
        // TODO Auto-generated constructor stub
        this.SERVER = server;
        this.port = port;
        this.ID = id;
        this.PASS = pass;
        this.mEncodingSet = encodingSet;
        this.mDefaultWorkDirectory = workDirctory;
        mFtp = new FTPClient(); // 객체 생성
    }
    /**
     * FTP서버로 접속을 시도합니다.
     *
     * @return 서버 접속 성공 여부를 리턴합니다.
     */
    public boolean ftpLogin() {
        boolean loginResult = false;
        try {
            mFtp.setControlEncoding(mEncodingSet);
            mFtp.connect(SERVER, port);
            loginResult = mFtp.login(ID, PASS);
            mFtp.enterLocalPassiveMode(); // PassiveMode 접속
            mFtp.makeDirectory(mDefaultWorkDirectory);
            mFtp.changeWorkingDirectory(mDefaultWorkDirectory);
        } catch (IOException e) {
            GlobalMemberValues.logWrite(TAG, e.toString() + "\n");
        }
        if (!loginResult) {
            GlobalMemberValues.logWrite(TAG, "로그인 실패" + "\n");
            return false;
        } else {
            GlobalMemberValues.logWrite(TAG, "로그인 succeeded" + "\n");
            return true;
        }
    }
    /**
     * FTP서버로 파일을 전송
     * 전송할 파일의 객체를 필요로 합니다.
     * 파일전송 성공 실패 여부를 리턴합니다.
     */
    public int ftpUploadFile(String paramLocalFileStr, String paramTailOfFileName) {
        int returnCode = 2;
        /**
         * 0 : 업로드 정상완료
         * 1 : FTP 접속안됨
         * 2 : 파일전송실패
         * 3 : 파일전송문제발생 (Exception 발생)
         * 4 : FTP 접속 스트림 닫고 로그아웃중 오류 발생
         * 5 : FTP 접속 종료중 문제 발생
         */

        if (!mFtp.isConnected()) {
            GlobalMemberValues.logWrite(TAG, "현재 FTP 서버에 접속되어 있지 않습니다." + "\n");
            returnCode = 1;
            //return false;
        }

        boolean uploadResult = false;
        try {
            File tempFile = new File(paramLocalFileStr);

            mFtp.setFileType(FTP.BINARY_FILE_TYPE); // 스트림으로 보낼 파일의 유형
            fis = new FileInputStream(tempFile);
            String uploadFileName = tempFile.getName() + "_" + paramTailOfFileName;
            uploadResult = mFtp.storeFile(uploadFileName, fis);
            if (!uploadResult) {
                Log.e(TAG, "파일 전송을 실패하였습니다." + "\n");
                //uploadResult = false;
                returnCode = 2;
            } else {
                returnCode = 0;
            }
        } catch (Exception e) {
            GlobalMemberValues.logWrite("FTPEXECEPTIONLOG", "로그값 : " + e.getMessage().toString() + "\n");
            Log.e(TAG, "파일전송에 문제가 생겼습니다. " + e.toString() + "\n");
            //uploadResult = false;
            returnCode = 3;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    mFtp.logout();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    GlobalMemberValues.logWrite(TAG, "FTP 접속 스트림 닫고 로그아웃중 오류 발생" + "\n");
                    e.printStackTrace();
                    //uploadResult =  false;
                    returnCode = 4;
                }
            }
            if (mFtp.isConnected()) {
                try {
                    mFtp.disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    GlobalMemberValues.logWrite(TAG, "FTP 접속 종료중 문제 발생" + "\n");
                    //uploadResult =  false;
                    returnCode = 5;
                }
            }
        }
        //return uploadResult;
        return returnCode;
    }

    public int ftpDownloadFile(String paramFtpFileStr, String paramLocalFileStr){
        // paramFtpFileStr : FTP 에서 다운로드 받을 폴더+파일. 확장자 포함
        //                   (예를 들어 abc 폴더안에 있는 a.txt 파일이면 abc/a.txt )
        //                   (폴더없이 a.txt 이면 그냥 a.txt )
        // paramLocalFileStr : FTP 에서 다운로드 받은 파일을 Local 태블릿에 저장할 폴더+파일명
        int returnCode = 2;
        /**
         * 0 : 업로드 정상완료
         * 1 : FTP 접속안됨
         * 2 : 파일다운로드실패
         * 3 : 파일다운로드 문제발생 (Exception 발생)
         * 4 : FTP 다운로드 스트림 닫고 로그아웃중 오류 발생
         * 5 : FTP 다운로드 종료중 문제 발생
         */

        if (!mFtp.isConnected()) {
            GlobalMemberValues.logWrite(TAG, "현재 FTP 서버에 접속되어 있지 않습니다." + "\n");
            returnCode = 1;
            //return false;
        }

        boolean downloadResult = false;
        try {
            mFtp.setFileType(FTP.BINARY_FILE_TYPE);
            fos = new FileOutputStream(new File(paramLocalFileStr));
            downloadResult = mFtp.retrieveFile(paramFtpFileStr, fos);
            fos.close();
            if (!downloadResult) {
                Log.e(TAG, "FTP 다운로드 실패." + "\n");
                //uploadResult = false;
                returnCode = 2;
            } else {
                GlobalMemberValues.logWrite(TAG, "FTP 다운로드 성공" + "\n");
                returnCode = 0;
            }
        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG, "FTP 다운로드 실패" + "\n");
            e.printStackTrace();
            returnCode = 3;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    mFtp.logout();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    GlobalMemberValues.logWrite(TAG, "FTP 접속 스트림 닫고 로그아웃중 오류 발생" + "\n");
                    e.printStackTrace();
                    //uploadResult =  false;
                    returnCode = 4;
                }
            }

            if (mFtp.isConnected()) {
                try {
                    mFtp.disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    GlobalMemberValues.logWrite(TAG, "FTP 접속 종료중 문제 발생" + "\n");
                    //uploadResult =  false;
                    returnCode = 5;
                }
            }
        }

        return returnCode;
    }

}
