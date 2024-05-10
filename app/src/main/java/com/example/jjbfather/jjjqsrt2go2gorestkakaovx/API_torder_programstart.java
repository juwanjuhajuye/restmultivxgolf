package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

public class API_torder_programstart extends AsyncTask {
    final String TAG = "API_torder_programstart";

    String mReturnValue = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();

    String JsonMsg = "";

    int cloudSentResult = 0;
    String smsSentResultStr = "";

    public API_torder_programstart(String paramJson) {
        mStrUrl = GlobalMemberValues.API_TORDER;
        JsonMsg = paramJson;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GlobalMemberValues.logWrite(TAG, "url :" + mStrUrl + "\n");

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            HttpURLConnection conn = null;
            String response = "";

            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
            try {
                URL url = new URL(mStrUrl);

                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                //conn.setRequestProperty ("Authorization", basicAuth);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization","Bearer " + GlobalMemberValues.TORDER_API_KEY);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);;

                os = conn.getOutputStream();
                os.write(JsonMsg.getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();
                String responseBody = conn.getResponseMessage();
                String reponseMethod = conn.getRequestMethod();

                GlobalMemberValues.logWrite("TORDERDEBUG", String.valueOf(responseCode));

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
                    GlobalMemberValues.logWrite(TAG, "DATA response = " + response + "\n");

                    mFlag = true;
                    cloudSentResult = 0;
                    smsSentResultStr = response;
                } else {
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
                    GlobalMemberValues.logWrite(TAG, "DATA response = " + response + "\n");

                    mFlag = false;
                    cloudSentResult = 0;
                    smsSentResultStr = response;
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                cloudSentResult = 1;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                cloudSentResult = 2;
            } catch ( Exception e ) {
                GlobalMemberValues.logWrite(TAG, "error msg :" + e.toString() + "\n");
                cloudSentResult = 3;
            }
            // ---------------------------------------------------------------------------------

        } catch (Exception e) {
            // 예외발생
            GlobalMemberValues.logWrite("TORDERDEBUG", "error msg :" + e.toString() + "\n");
        }
        return null;
    }
}