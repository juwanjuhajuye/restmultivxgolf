package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_splash);
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

        finish();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

}
