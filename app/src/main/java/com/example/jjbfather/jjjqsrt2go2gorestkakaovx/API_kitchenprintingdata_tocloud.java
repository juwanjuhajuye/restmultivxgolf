package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class API_kitchenprintingdata_tocloud extends AsyncTask {
    final String TAG = "API_kitchenprintingdata_tocloud_log";

    String mReturnValue = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag = false;
    public Vector<String> sqlQueryVec = new Vector<String>();

    String JsonMsg = "";

    int cloudSentResult = 0;
    String smsSentResultStr = "";

    public API_kitchenprintingdata_tocloud(String paramJson) {
        if (!GlobalMemberValues.isStrEmpty(paramJson)) {
            mStrUrl = GlobalMemberValues.getCloudKitchenPrinterUrl();
            JsonMsg = paramJson;
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GlobalMemberValues.logWrite(TAG, "url :" + GlobalMemberValues.getCloudKitchenPrinterUrl() + "\n");

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            HttpURLConnection conn = null;
            String response = "";

            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
            try {
                URL url = new URL(GlobalMemberValues.getCloudKitchenPrinterUrl());

                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);;

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
                    GlobalMemberValues.logWrite(TAG, "DATA response = " + response + "\n");

                    mFlag = true;
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
        }
        return null;
    }
}
