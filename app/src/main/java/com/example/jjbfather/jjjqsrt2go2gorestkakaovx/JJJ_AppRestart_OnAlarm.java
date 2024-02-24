package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JJJ_AppRestart_OnAlarm extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        // intent로부터 전달받은 string
        //String nowTime = intent.getStringExtra("NOWTIME");

        // 앱 재시작
        GlobalMemberValues.setRestartAndroidAppMethod(MainActivity.mActivity);

        // 앱 종료
        //GlobalMemberValues.setCloseAndroidAppMethod(MainActivity.mActivity);

        //MainActivity.alarmtxt.setText("알람이 실행되었네요...1");
    }
}
