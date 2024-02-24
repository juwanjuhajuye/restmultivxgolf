package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class JJJ_PrintingKitchen extends Service {
    public JJJ_PrintingKitchen() {
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
            GlobalMemberValues.logWrite("printKitchenOnly", "여기실행됨..2 \n");
            GlobalMemberValues.printingKitchenExecute();
        } catch (Exception e) {
            //GlobalMemberValues.displayDialog(SaleHistory.context, "Warning", e.getMessage().toString(), "Close");
            GlobalMemberValues.logWrite("jjjprintingservicelog", "에러내용 : " + e.getMessage().toString() + "\n");
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d("StartService","onDestroy()");
        super.onDestroy();
    }
}
