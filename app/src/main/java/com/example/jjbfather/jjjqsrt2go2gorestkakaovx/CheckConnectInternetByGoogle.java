package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wanhaye on 2018-02-13.
 */

class CheckConnectInternetByGoogle extends Thread{

    private boolean success;
    private String host;

    public CheckConnectInternetByGoogle(String host){
        this.host = host;
    }

    @Override
    public void run() {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)new URL(host).openConnection();
            conn.setRequestProperty("User-Agent","Android");
            conn.setConnectTimeout(1000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 204) success = true;
            else success = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        if(conn != null){
            conn.disconnect();
        }
    }

    public boolean isSuccess(){
        return success;
    }
}
