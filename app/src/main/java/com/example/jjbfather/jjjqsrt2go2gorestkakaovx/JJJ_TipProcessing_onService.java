package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class JJJ_TipProcessing_onService extends Service {
    public JJJ_TipProcessing_onService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("StartService","onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("StartService","onCreate()");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            GlobalMemberValues.logWrite("onetimetipadjustmentlog", "여기실행1" + "\n");
            GlobalMemberValues.exeOneTimeTipAdjustment();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            GlobalMemberValues.logWrite("jjjonetimetipadjustmenterrorlog", "에러내용 : " + e.getMessage().toString() + "\n");
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d("StartService","onDestroy()");
        super.onDestroy();
    }
}
