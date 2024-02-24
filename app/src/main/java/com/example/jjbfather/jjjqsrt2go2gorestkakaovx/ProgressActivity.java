package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;


public class ProgressActivity extends Activity {

    public static Activity mProgressActivity;
    public ProgressDialog proDial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_splash);

        mProgressActivity = this;
        proDial = new ProgressDialog(this);
        proDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDial.setTitle(GlobalMemberValues.ANDROIDPOSNAME);
        proDial.setMessage(GlobalMemberValues.DOWNLOAD_PROGRESS);
        proDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDial.show();
        this.setFinishOnTouchOutside(false);
    }
}
