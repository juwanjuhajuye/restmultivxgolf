package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_ETHENET = 3;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (GlobalMemberValues.isOnlineInternet("http://clients3.google.com/generate_204")) {
                    return TYPE_WIFI;
                }
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (GlobalMemberValues.isOnlineInternet("http://clients3.google.com/generate_204")) {
                    return TYPE_MOBILE;
                }
            } else {
                if (GlobalMemberValues.isOnlineInternet("http://clients3.google.com/generate_204")) {
                    return TYPE_ETHENET;
                }
            }
        } else {
            if (GlobalMemberValues.isOnlineInternet("http://clients3.google.com/generate_204")) {
                return TYPE_ETHENET;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_ETHENET) {
            status = "Internet enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
