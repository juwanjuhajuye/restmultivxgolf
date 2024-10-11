package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static SplashActivity instance;

    public static Activity mActivity;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        instance = this; // 현재 액티비티 인스턴스를 저장
        // MainActivity에서 이 SplashActivity를 실행한 후
        // MainActivity는 바로 BActivity로 전환할 수 있음

        mContext = this;

    }

    public static void dismiss() {
        // 외부에서 SplashActivity를 종료할 수 있게 하는 메서드
        if (instance != null) {
            instance.finish();
            instance = null; // 메모리 릭 방지
        }
    }

    @Override
    public void onBackPressed() {
        // 로딩 중에는 뒤로 가기 버튼 비활성화
    }
}