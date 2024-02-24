package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class UploadSaleJsonToCloud extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;

    int jsonSentResult = 0;
    String jsonSentResultStr = "";
    Vector<String > apiUpdateQueryVec = null;

    String TAG = "salejsonuploadlog";


    public UploadSaleJsonToCloud() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
        //receivedSalesCode = MainActivity.mSalesCode;

        GlobalMemberValues.logWrite(TAG, "생성자 진입" + "\n");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        //Thread mThread = new Thread(this);
        //mThread.start();

        GlobalMemberValues.logWrite(TAG, "onCreate..." + "\n");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            sendSaleJsonToCloud();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(MainActivity.context, "Warning", e.getMessage().toString(), "Close");
        }
    }


    public void sendSaleJsonToCloud() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            String strUpdateQuery = "";
            Vector<String> apiVec = new Vector<String>();
            String salescode = "";
            String jsonstr = "";

            String strQueryString = " select salescode, receiptJson from salon_sales " +
                    " where (isCloudUpload2 = 0 or isCloudUpload2 is null) " +
                    " and not(receiptJson is null or receiptJson = '') " +
                    " and delyn = 'N' " +
                    " order by idx asc " +
                    " limit 1 ";

            GlobalMemberValues.logWrite(TAG, "sql : " + strQueryString + "\n");

            int tempi = 0;
            Cursor cartCursor = dbInitForUploadCloud.dbExecuteRead(strQueryString);
            while (cartCursor.moveToNext()) {
                salescode = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(0), 1);
                jsonstr = GlobalMemberValues.getDBTextAfterChecked(cartCursor.getString(1), 1);

                GlobalMemberValues.logWrite(TAG + "WANHAYEJJJ333", tempi + "-jsonstr : " + jsonstr + "\n");
//                JSONObject tempJson = null;
                if (!GlobalMemberValues.isStrEmpty(jsonstr)) {

                    jsonstr = jsonstr + "-WJJJ-" + salescode +
                            "-WJJJ-" + GlobalMemberValues.SALON_CODE +
                            "-WJJJ-" + GlobalMemberValues.STORE_INDEX +
                            "-WJJJ-" + GlobalMemberValues.STATION_CODE;

                    GlobalMemberValues.logWrite("WANHAYEJSONV", tempi + "jsonstr : " + jsonstr + "\n\n\n");
                }

                strUpdateQuery = "update salon_sales set isCloudUpload2 = 1 " +
                        " where salescode = '" + salescode + "' ";
                apiVec.addElement(jsonstr + "|||" + strUpdateQuery);

                tempi++;
            }

            if (apiVec.size() > 0) {
                for (String params : apiVec) {
                    GlobalMemberValues.logWrite(TAG, "params : " + params + "\n");
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        String[] tempParams = params.split(GlobalMemberValues.STRSPLITTER2);
                        // API 로 넘기는 문자열에는 공백이 있으면 안되므로
                        // 공백부분을 ||| 으로 처리하고
                        // 웹 API 처리하는 부분에서 ||| 을 공백으로 치환한다.
                        String paramStr = GlobalMemberValues.getReplaceText(tempParams[0], " ", "|||");
                        sendJsonDataToCloud(paramStr, tempParams[1]);
                    }
                }
            }
        }

        // 서비스 중지
        if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEJSON != null && GlobalMemberValues.CURRENTSERVICEINTENT_SALEJSON != null)
            GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_SALEJSON.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_SALEJSON);
    }

    public void sendJsonDataToCloud(String JsonMsg, String paramUpdateSql) {
        GlobalMemberValues.logWrite(TAG + "JSONVINMETHOD", "JsonMsg : " + JsonMsg + "\n");
        GlobalMemberValues.logWrite(TAG + "WANHAYEJJJ2", "여기에..1" + "\n\n\n");
        jsonSentResult = 99;
        jsonSentResultStr = "";
        String tempApiURL = "API_DOWNLOAD_SALEJSON.asp";

        Thread thread = new Thread(new Runnable() {
            public void run() {
                //GlobalMemberValues.logWrite(TAG, "url :" + GlobalMemberValues.API_WEB + "API_SMS_Receipt.asp" + "\n");
                GlobalMemberValues.logWrite(TAG, "url :" + GlobalMemberValues.API_WEB + tempApiURL + "\n");

                OutputStream os = null;
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                HttpURLConnection conn = null;
                String response = "";

                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                try {
                    URL url = new URL(GlobalMemberValues.API_WEB + tempApiURL);

                    conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5 * 1000);
                    conn.setReadTimeout(5 * 1000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    os = conn.getOutputStream();
                    os.write(JsonMsg.getBytes());
                    os.flush();

                    int responseCode = conn.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK) {

                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];
                        byte[] byteData = null;
                        int nLength = 0;
                        while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            baos.write(byteBuffer, 0, nLength);
                        }
                        byteData = baos.toByteArray();
                        response = new String(byteData);
                        GlobalMemberValues.logWrite(TAG + "JJJ2", "DATA response = " + response + "\n");

                        jsonSentResult = 0;
                        jsonSentResultStr = response;
                    }

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    jsonSentResult = 1;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    jsonSentResult = 2;
                } catch ( Exception e ) {
                    GlobalMemberValues.logWrite(TAG, "error msg :" + e.toString() + "\n");
                    jsonSentResult = 3;
                }
                // ---------------------------------------------------------------------------------

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                if (apiUpdateQueryVec == null) {
                    apiUpdateQueryVec = new Vector<String>();
                }
                apiUpdateQueryVec.addElement(paramUpdateSql);

                handlersms.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    Handler handlersms = new Handler() {
        public void handleMessage(Message msg) {
            jsonSentResultStr = GlobalMemberValues.getReplaceText(jsonSentResultStr, "[object Object]", "");
            GlobalMemberValues.logWrite("WANHAYERESULTV", "jsonSentResultStr : " + jsonSentResultStr + "\n\n\n");
            if (GlobalMemberValues.isStrEmpty(jsonSentResultStr)) {
                GlobalMemberValues.displayDialog(context, "Warning", "failure -- [CODE : " + jsonSentResult + "]", "Close");
            } else {
                String returncode = "";
                String returnresult = "";
                JSONObject resultJson;
                try {
                    GlobalMemberValues.logWrite(TAG + "WANHAYEJJJWWWWW", "jsonSentResultStr : " + jsonSentResultStr + "\n\n\n");

                    resultJson = new JSONObject(jsonSentResultStr);
                    returncode = GlobalMemberValues.getDataInJsonData(resultJson, "returncode");
                    returnresult = GlobalMemberValues.getDataInJsonData(resultJson, "returnresult");

                    GlobalMemberValues.logWrite(TAG + "WANHAYERESULTV", "returncode : " + returncode + "\n\n\n");
                    GlobalMemberValues.logWrite(TAG + "WANHAYERESULTV", "returnresult : " + returnresult + "\n\n\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (returncode == "00" || returncode.equals("00")) {
                    if (apiUpdateQueryVec != null) {
                        // 전송 성공시
                        String returnResult = dbInitForUploadCloud.dbExecuteWriteForTransactionReturnResult(apiUpdateQueryVec);
                    }
                } else {
                    GlobalMemberValues.displayDialog(context, "Warning", "failure -- [" + returncode + " : " + returnresult + "]", "Close");
                }
            }
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
