package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class Sound_timerHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what){
            case 0:
                this.removeMessages(1);
                this.sendEmptyMessage(1);
                break;
            case 1:
                if (!GlobalMemberValues.isSoundContinue){
                    GlobalMemberValues.isSoundContinue = true;
                    this.removeMessages(1);
                    break;
                }
                startNotificationSound();
                this.sendEmptyMessageDelayed(1,2000);
                break;
            case 2:
                this.removeMessages(1);
                break;
        }

    }

    public void startNotificationSound(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(MainActivity.mContext, notification);
        if (r != null) {
            r.play();
        }
    }
}