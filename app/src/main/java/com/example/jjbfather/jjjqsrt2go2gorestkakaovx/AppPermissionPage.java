package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import androidx.core.content.ContextCompat;
import android.os.Bundle;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class AppPermissionPage extends Activity {
    Activity mActivity;
    Context mContext;

    AppPermissionCheck2 appPermissionCheck2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_permission_page);

        mActivity = this;
        mContext = this;

        appPermissionCheck2 = new AppPermissionCheck2(mActivity, mContext);

        appPermissionThread(mActivity, mContext);
    }

    public void appPermissionThread(Activity paramActivity, Context paramContext) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                appPermissionDoing(mActivity, mContext);
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    public void appPermissionDoing(Activity paramActivity, Context paramContext) {
        //appPermissionCheck2.permissionCheck(mActivity, mContext, "CALLPHONE");
        appPermissionCheck2.permissionCheck(paramActivity, paramContext, "STORAGE", "N");
        //appPermissionCheck2.permissionCheck(mActivity, mContext, "CAMERA");
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int permissionReadStorage = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWriteStorage = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
                // 퍼미션이 허용되지 않았을 때...
                appPermissionThread(mActivity, mContext);
            } else {
                // 퍼미션이 허용되었을 때...
                mActivity.finish();
            }
        }
    };
}
