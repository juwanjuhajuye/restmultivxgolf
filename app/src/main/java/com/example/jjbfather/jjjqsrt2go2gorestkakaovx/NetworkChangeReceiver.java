package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int conn = NetworkUtil.getConnectivityStatus(context);
        String tempStatus = null;
        int tempStatusImageId = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            tempStatus = "WIFI";
            tempStatusImageId = R.drawable.aa_images_main_wifi;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_wifi_lite;
            }
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            tempStatus = "3G/LTE";
            tempStatusImageId = R.drawable.aa_images_main_lte;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_lte_lite;
            }
        } else if (conn == NetworkUtil.TYPE_ETHENET) {
            tempStatus = "ONLINE";
            tempStatusImageId = R.drawable.aa_images_main_online;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_online_lite;
            }
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            tempStatus = "NOT CONNECTED";
            tempStatusImageId = R.drawable.aa_images_main_disconnect;
            // Lite 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tempStatusImageId = R.drawable.aa_images_main_disconnect_lite;
            }
        }

        GlobalMemberValues.logWrite("networkchecklog", "연결상태 : " + tempStatus + "\n");
        GlobalMemberValues.changeNetworkUI(tempStatus, tempStatusImageId);

        String strStatus = NetworkUtil.getConnectivityStatusString(context);
        Toast.makeText(context, strStatus, Toast.LENGTH_LONG).show();

        GlobalMemberValues.GLOBALNETWORKSTATUS = conn;
    }
}

