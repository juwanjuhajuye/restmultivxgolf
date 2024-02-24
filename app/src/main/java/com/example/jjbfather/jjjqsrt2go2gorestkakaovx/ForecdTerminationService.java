package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ForecdTerminationService extends Service {

   @Override
   public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분
      Log.e("Error","onTaskRemoved - 강제 종료 " + rootIntent);
      Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();
      GlobalMemberValues.doMethodInClose(MainActivity.mActivity);
      stopSelf(); //서비스 종료
   }

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }
}
