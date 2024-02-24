package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;
import android.os.AsyncTask;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-06-22.
 */
public class ImageDownload_service extends AsyncTask {
    private static final String TAG = "ImageDownload_service";

    boolean mFlag = false;

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            String tempServiceIdx = "";
            String serviceImageUrlPath = "";

            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String strQuery = "select idx, strFilePath from salon_storeservice_sub " +
                    " where not(strFilePath is null or strFilePath = '') and delyn = 'N' and activeyn = 'Y' " +
                    " order by idx asc";
            Cursor serviceCursor;
            serviceCursor = dbInit.dbExecuteRead(strQuery);
            GlobalMemberValues.logWrite("ImageDownloadFunc", "여기까지 왔음..2");
            while (serviceCursor.moveToNext()) {
                GlobalMemberValues.logWrite("ImageDownloadFunc", "여기까지 왔음..3");
                tempServiceIdx = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(0), 1);
                serviceImageUrlPath = GlobalMemberValues.getDBTextAfterChecked(serviceCursor.getString(1), 1);
                GlobalMemberValues.logWrite("ImageDownloadFunc", "webImageUrl : " + serviceImageUrlPath);
                if (!GlobalMemberValues.isStrEmpty(serviceImageUrlPath) && !GlobalMemberValues.isStrEmpty(tempServiceIdx)) {
                    ImageDownloadAtSysFolder imageDownSysFolder = new ImageDownloadAtSysFolder();
                    imageDownSysFolder.downlaodImages("service", serviceImageUrlPath, tempServiceIdx);
                }
            }

            mFlag = true;

        } catch (Exception e) {
            GlobalMemberValues.logWrite(TAG, "예외발생 : " + e.getMessage());
        }

        return null;
    }
}
