package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class LoadingIntent extends Activity {
    public static Activity mActivity;
    public static Context mContext;
    public static ProgressDialog ldProDial;

    public static ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_intent);
        this.setFinishOnTouchOutside(false);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int thisTableHeightSize = metrics.heightPixels;

        float thisRatio = 1.0f;
        if (thisTableHeightSize < 768) {
            //thisRatio = (float)thisTableHeightSize / (float)GlobalMemberValues.tabletBasicHeight;
        }

        ImageView splashImg = (ImageView)findViewById(R.id.splashImage);
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) splashImg.getLayoutParams();
        //params.height = (int)((GlobalMemberValues.tabletBasicHeight / 2) * thisRatio);

        //splashImg.setLayoutParams(params);

        mActivity = this;
        mContext = this;

//        mProgressBar = (ProgressBar)findViewById(R.id.progressBar1);

        GlobalMemberValues.progressBarDialog = new ProgressBarDialog(mContext, GlobalMemberValues.DOWNLOAD_PROGRESS);
        GlobalMemberValues.progressBarDialog.show();

        // 프로그래스 바를 실행~
//        ldProDial = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, GlobalMemberValues.DOWNLOAD_PROGRESS, true, false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalMemberValues.progressBarDialog.dismiss();
    }
}
