package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-06-22.
 */
public class ImageDownloadAtSysFolder {
    private static final String TAG = "ImageDownloadAtSysFolder";

    URL mUrl;
    boolean mFlag = false;
    Bitmap mSaveBm;

    String downloadType = "";
    String serviceIdx = "";

    // 이곳에 이미지 다운로드 처리 메소드
    public void downlaodImages(String paramDownloadType, String paramWebImageUrl, String paramServiceIdx) {
        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        downloadType = paramDownloadType;
        serviceIdx = paramServiceIdx;

        //Bitmap webImageBitmap = getImageFromURL(paramWebImageUrl);

        ImageDownloadOpenHttpConnection iDOpHttpCon = new ImageDownloadOpenHttpConnection();
        iDOpHttpCon.execute(paramWebImageUrl);

        GlobalMemberValues.logWrite(TAG, "paramWebImageUrl : " + paramWebImageUrl + "\n");
    }

    private void saveImageInSystem(Bitmap paramBitmap) {
        if (paramBitmap != null) {
            String tempSubFolder = "";
            switch (downloadType) {
                case "service" : {
                    tempSubFolder = GlobalMemberValues.FOLDER_SERVICEIMAGE;
                    break;
                }
            }

            OutputStream outStream = null;
            String strExtStorageDirectory = Environment.getExternalStorageDirectory().toString() +
                    "/" + GlobalMemberValues.STATION_CODE + "/" + tempSubFolder;

            GlobalMemberValues.logWrite(TAG, "directory : " + strExtStorageDirectory + "\n");

            File dir = new File(strExtStorageDirectory);
            // 상위 디렉토리가 존재하지 않을 경우 생성
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일이름 만들기
            String tempFileName = downloadType + "img_" + serviceIdx + ".png";

            //다운로드 폴더에 동일한 파일명이 존재하는지 확인
            if (new File(strExtStorageDirectory + "/" + tempFileName).exists() == false) {

            } else {

            }

            File imageFile = new File(strExtStorageDirectory, tempFileName);
            try {
                outStream = new FileOutputStream(imageFile);
                paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();

                //Toast.makeText(MainActivity.mContext, "Saved", Toast.LENGTH_SHORT).show();
                GlobalMemberValues.logWrite(TAG, "저장파일명 : " + tempFileName + "\n");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.mContext, e.toString(), Toast.LENGTH_SHORT).show();
                GlobalMemberValues.logWrite(TAG, "Error : " + e.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.mContext, e.toString(), Toast.LENGTH_SHORT).show();
                GlobalMemberValues.logWrite(TAG, "Error : " + e.toString() + "\n");
            }

            mFlag = true;
        } else {
            mFlag = false;
        }
    }

    private Bitmap getImageFromURL(String strImageURL) {
        Bitmap imgBitmap = null;
        try {
            URL url = new URL(strImageURL);
            URLConnection conn = url.openConnection();
            conn.connect();
            int nSize = conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
            imgBitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgBitmap;
    }

    private class ImageDownloadOpenHttpConnection extends AsyncTask<Object, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap mBitmap = null;
            //bmImage = (ImageView) params[0];
            String imageWebUrl = (String) params[0];
            InputStream in = null;
            try {
                in = new URL(imageWebUrl).openStream();
                mBitmap = BitmapFactory.decodeStream(in);
                in.close();
                GlobalMemberValues.logWrite(TAG, "이미지 다운로드 실행1\n");

            } catch (Exception ex) {
                GlobalMemberValues.logWrite(TAG, "error : " + ex.getMessage().toString() + "\n");
                ex.printStackTrace();
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            super.onPostExecute(bm);
            mSaveBm = bm;
            GlobalMemberValues.logWrite(TAG, "이미지 다운로드 실행2\n");
            //bmImage.setImageBitmap(bm);
            if (mSaveBm != null) {
                saveImageInSystem(mSaveBm);
            }
        }
    }

}
