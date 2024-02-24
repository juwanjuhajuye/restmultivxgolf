package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Sam4sSocketPrinter {

    static Connect mContext;
    static private Socket socket;
    static private DataOutputStream writeSocket;
    static private DataInputStream readSocket;
    static private Handler mHandler = new Handler(Looper.getMainLooper());

    static Object lock = new Object();



    static private ServerSocket serverSocket;

    //set server

    //set client

    static class Connect extends Thread {

        String ip_num;
        int port_num;
        JSONObject jsonObject;

        public Connect(String ip, int port, JSONObject jsonObject) {
            this.ip_num = ip;
            this.port_num = port;
            this.jsonObject = jsonObject;
        }
        public Connect(String ip, int port) {
            this.ip_num = ip;
            this.port_num = port;
        }

        public void run() {

            if (GlobalMemberValues.b_socket_connected){
                Log.e("이미 연결됨!","이미 연결됨!");
                setToast("You are already connected!");
                return;
            }
            Log.d("Connect", "Run Connect");
            String ip = null;
            int port = 0;

            try {
//                ip = et1.getText().toString();
//                port = Integer.parseInt(et2.getText().toString());
            } catch (Exception e) {
                final String recvInput = "Please enter exactly!";
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }
                });
            }

            try {
                if (socket == null) socket = new Socket(ip_num, port_num,null, port_num);

                writeSocket = new DataOutputStream(socket.getOutputStream());
                readSocket = new DataInputStream(socket.getInputStream());

                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast("The connection was successful.");
                        GlobalMemberValues.b_socket_connected = true;
                        if (jsonObject == null){
//                            disConnect();
                        } else {
                            connect_json_toMessage(jsonObject);
                        }

                        GlobalMemberValues.s_str_serverSocketIP = ip_num;
                        GlobalMemberValues.s_i_serverSocketPort = port_num;
                    }

                });
//                (new recvSocket()).start();
                GlobalMemberValues.b_socket_connected = true;
            } catch (Exception e) {
                final String recvInput = "Connection failed.";
                Log.d("Connect", e.getMessage());
                GlobalMemberValues.b_socket_connected = false;
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
//                        setToast(recvInput);
                        if (jsonObject == null){
                        } else {
                            reconnect(ip_num,port_num,jsonObject);
                        }
                    }

                });
            }

//            if (jsonObject == null) {
//                if (GlobalMemberValues.s_str_serverSocketIP.equals(ip_num)
//                        && GlobalMemberValues.s_i_serverSocketPort == port_num) {
//
//                    final String recvInput = "The connection was successful!";
//                    mHandler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            setToast(recvInput);
//                        }
//                    });
//                } else {
//                    try {
//                        socket = new Socket(ip_num, port_num);
//                        writeSocket = new DataOutputStream(socket.getOutputStream());
//                        readSocket = new DataInputStream(socket.getInputStream());
//
//                        mHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                                setToast("The connection was successful.");
//                                b_connected = true;
//                                if (jsonObject == null){
//                                    disConnect();
//                                } else {
//                                    connect_json_toMessage(jsonObject);
//                                }
//
//                                GlobalMemberValues.s_str_serverSocketIP = ip_num;
//                                GlobalMemberValues.s_i_serverSocketPort = port_num;
//                            }
//
//                        });
//                        (new recvSocket()).start();
//                    } catch (Exception e) {
//                        final String recvInput = "Connection failed.";
//                        Log.d("Connect", e.getMessage());
//                        mHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
////                        setToast(recvInput);
//                                if (jsonObject == null){
//                                } else {
//                                    reconnect(ip_num,port_num,jsonObject);
//                                }
//                            }
//
//                        });
//
//                    }
//                }
//            } else {
//                try {
//                    socket = new Socket(ip_num, port_num);
//                    writeSocket = new DataOutputStream(socket.getOutputStream());
//                    readSocket = new DataInputStream(socket.getInputStream());
//
//                    mHandler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            setToast("The connection was successful.");
//                            b_connected = true;
//                            if (jsonObject == null){
//                                disConnect();
//                            } else {
//                                connect_json_toMessage(jsonObject);
//                            }
//
//                            GlobalMemberValues.s_str_serverSocketIP = ip_num;
//                            GlobalMemberValues.s_i_serverSocketPort = port_num;
//                        }
//
//                    });
//                    (new recvSocket()).start();
//                } catch (Exception e) {
//                    final String recvInput = "Connection failed.";
//                    Log.d("Connect", e.getMessage());
//                    mHandler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
////                        setToast(recvInput);
//                            if (jsonObject == null){
//                            } else {
//                                reconnect(ip_num,port_num,jsonObject);
//                            }
//                        }
//
//                    });
//
//                }
//            }




        }
    }

    class Disconnect extends Thread {
        public void run() {
            try {
                if (socket != null) {
                    synchronized (lock) {
                        socket.close();

                    }

                    GlobalMemberValues.b_socket_connected = false;
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            setToast("The connection has been terminated.");
                        }
                    });

                }

            } catch (Exception e) {
                final String recvInput = "Connection failed.";
                Log.d("Connect", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
//                        setToast(recvInput);
                    }

                });

            }

        }
    }

    static class SetServer extends Thread {

        int portNum;
        public SetServer(int port) {
            portNum = port;
        }

        public void run() {
            try {
//                int port = Integer.parseInt(et2.getText().toString());

                int port = portNum;
                serverSocket = new ServerSocket(port);
                final String result = "Server port " + port + " is ready.";

                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(result);
                    }
                });

                socket = serverSocket.accept();
                writeSocket = new DataOutputStream(socket.getOutputStream());
                readSocket = new DataInputStream(socket.getInputStream());

                while (true) {

                    synchronized (lock) {
                        byte[] b = new byte[5000];
                        int ac = readSocket.read(b, 0, b.length);
                        String input = new String(b, 0, b.length);
                        final String recvInput = input.trim();

                        if(ac==-1)
                            break;
//                    final JSONObject jsonObject = new JSONObject(recvInput);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
//                            setToast(recvInput);
//                            receivePrinter(recvInput);

                                //
                                if (recvInput == null){
//                                    (new CloseServer()).start();
//                                    String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
//                                            "select serverport from salon_storestationsettings_system"), 1);
//                                    if (!GlobalMemberValues.isStrEmpty(tempValue)) {
//                                        (new SetServer(Integer.parseInt(tempValue))).start();
//                                    }
                                } else {
                                    receivePrinter(recvInput);
                                }

                            }

                        });
                    }

                }
                mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast("The connection has been terminated.");
                        (new CloseServer()).start();
                        String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                                "select serverport from salon_storestationsettings_system"), 1);
                        if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                            (new SetServer(Integer.parseInt(tempValue))).start();
                        }
                    }

                });
                serverSocket.close();
                socket.close();
            } catch (Exception e) {
                Log.d("SetServer", e.getMessage());
                if (e.getMessage().contains("Address already in use")){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            setToast("Server port " + portNum + " is ready!");
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
//                        setToast(recvInput);
//                        mThread.start();

//                        (new CloseServer()).start();
//                        String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
//                                "select serverport from salon_storestationsettings_system"), 1);
//                        if (!GlobalMemberValues.isStrEmpty(tempValue)) {
//                            (new SetServer(Integer.parseInt(tempValue))).start();
//                        }

                        }

                    });
                }


            }

        }
    }

    static class recvSocket extends Thread {

        public void run() {
            try {
                readSocket = new DataInputStream(socket.getInputStream());

                while (true) {
                    byte[] b = new byte[5000];
                    int ac = readSocket.read(b, 0, b.length);
                    String input = new String(b, 0, b.length);
                    final String recvInput = input.trim();

                    if(ac==-1)
                        break;

                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            setToast(recvInput);
//                            receivePrinter(jsonObject);
                            Log.e("socket connection", recvInput);
                        }

                    });
                }
                mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast("The connection has been terminated.");
                    }

                });
            } catch (Exception e) {
                final String recvInput = "There was a problem with the connection and has terminated..";
                Log.d("SetServer", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
//                        setToast(recvInput);
                    }

                });

            }

        }
    }

    static class CloseServer extends Thread {
        public void run() {
            try {
                if (serverSocket != null) {

                        serverSocket.close();
                        socket.close();
                    GlobalMemberValues.b_socket_connected = false;


                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
//                            setToast("서버가 종료되었습니다..");
                        }
                    });
                }
            } catch (Exception e) {
                final String recvInput = "Server preparation failed.";
                Log.d("SetServer", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    static class sendMessage extends Thread {
        JSONObject jsonObject;
        public sendMessage(JSONObject data) {
            jsonObject = data;
        }

        public void run() {
            try {
//                byte[] b = new byte[100];
//                b = "{\"receiptno\":\"K090720192TV0RBM189\",\"saledate\":\"09\\/07\\/2019\",\"saletime\":\"04:48:38\",\"storename\":\"LA Branch\",\"storeaddress1\":\"3055 Wilshire Blvd #620\",\"storeaddress2\":\"\",\"storecity\":\"Los Angeles\",\"storestate\":\"CA\",\"storezip\":\"90010\",\"storephone\":\"2133829300\",\"saleitemlist\":[{\"itemname\":\"WHY MATCHA AFFOGATO ICE CREAM\",\"itemqty\":\"1\",\"itemprice\":\"10.00\",\"itemamount\":\"10.00\",\"itemdcextratype\":\"\",\"itemdcextraprice\":\"\",\"itemdcextrastr\":\"\",\"itemtaxexempt\":\"N\",\"optiontxt\":\"\",\"optionprice\":\"0\",\"additionaltxt1\":\"\",\"additionalprice1\":\"0\",\"additionaltxt2\":\"\",\"additionalprice2\":\"0\",\"kitchenmemo\":\"nokitchenmemo\"}],\"employeename\":\"Wanhaye Kim\",\"customerphonenum\":\"Walk In\",\"customeraddress\":\"\",\"customername\":\" \",\"alldiscountextraprice\":\"0.00\",\"alldiscountextrstr\":\"\",\"totalextradiscountprice\":\"0.00\",\"totaldiscountprice\":\"0.00\",\"totalextraprice\":\"0.00\",\"subtotal\":\"10.00\",\"tax\":\"0.00\",\"totalamount\":\"11.50\",\"deliverytakeaway\":\"T\",\"deliverypickupfee\":\"1.50\",\"customerordernumber\":\"7-P0001\",\"customerpagernumber\":\"\",\"cashtendered\":\"11.50\",\"change\":\"0\",\"creditcardtendered\":\"0\",\"giftcardtendered\":\"0\",\"checktendered\":\"0\",\"pointtendered\":\"0\",\"receiptprinttype\":\"3\",\"receiptfooter\":\"\"}".getBytes();
//                writeSocket.write(b);
//                b = "jh test".getBytes();

//                if (socket.isConnected() && ! socket.isClosed()){
//
//                } else {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            // 재연결........
//                            reconnect(String.valueOf(socket.getInetAddress()),socket.getPort(),jsonObject);
//                            try {
//                                sleep(2000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }

                jsonObject.put("SCODE", GlobalMemberValues.SALON_CODE);
                jsonObject.put("SIDX", GlobalMemberValues.STORE_INDEX);
                jsonObject.put("STCODE", GlobalMemberValues.STATION_CODE);

                writeSocket.write(jsonObject.toString().getBytes());

//                sleep(2000);

//                disConnect();
            } catch (Exception e) {
                final String recvInput = "The message transmission failed.";
                Log.d("SetServer", e.getMessage());
                if (e.getMessage().contains("Broken pipe")) {
                    socket = null;
                }
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                        Log.e("send Message 실패","send Message 실패");
                        connectServer(GlobalMemberValues.getSocketServerIp(),Integer.parseInt(GlobalMemberValues.getSocketServerPort()));
                        GlobalMemberValues.b_socket_connected = false;
                        reconnect_sendmessage(jsonObject);
                    }

                });

            }

        }
    }



    static void setToast(String msg) {
//        Toast.makeText(MainActivity.mContext, msg, Toast.LENGTH_SHORT).show();
        GlobalMemberValues.makeToastText(msg);
    }
    // setServer
    static void setSocketPrinterServerStart(int port){
        (new SetServer(port)).start();
    }

    // connect server
    static void connectServer(String ip, int port){
        (new Connect(ip,port)).start();
        if (readSocket != null){
            reconnect_check();
        }
    }
    // connect server
    static void connectServerToPrint(String ip, int port,JSONObject jsonObject){
        (new Connect(ip,port,jsonObject)).start();
    }

    // disconnect server
    void disConnect(){
        (new Disconnect()).start();
        (new CloseServer()).start();
    }

    // sendMessage
    static void connect_json_toMessage(JSONObject data){
        (new sendMessage(data)).start();
    }

    // printer 함수 만들기.
    static public void receivePrinter(String  jsonObject_str){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonObject_str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GlobalMemberValues.printGateByKitchen(jsonObject,MainActivity.mContext,"kitchen1");

//        Sam4GiantPrinter giantPrinter = new Sam4GiantPrinter();
//        giantPrinter.connectKitchen1(MainActivity.mContext, jsonObject, "kitchen1");

//        Sam4GiantSocketPrinter1 giantPrinter = new Sam4GiantSocketPrinter1();
//        giantPrinter.connectKitchen1(MainActivity.mContext, jsonObject, "kitchen1");

//        (new CloseServer()).start();
//        String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
//                "select serverport from salon_storestationsettings_system"), 1);
//        if (!GlobalMemberValues.isStrEmpty(tempValue)) {
//            (new SetServer(Integer.parseInt(tempValue))).start();
//        }
    }

    Thread mThread = new Thread(){
        @Override
        public void run() {
            super.run();
                try {
                    sleep(5000);
                    if (socket != null) {
                        Log.e("jihun thread test","socket 널 아님!!!");
                        if (socket.getKeepAlive()){

                        } else {
                            (new CloseServer()).start();
                            String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                                    "select serverport from salon_storestationsettings_system"), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                                (new SetServer(Integer.parseInt(tempValue))).start();
                            }
                        }
                    } else {
                        Log.e("jihun thread test","socket null!!!!");
                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    };

    public void resetServer(){
        while (true){

            try {

                if (socket.getKeepAlive()){

                } else {
                    (new CloseServer()).start();
                    String tempValue = GlobalMemberValues.getDBTextAfterChecked(MainActivity.mDbInit.dbExecuteReadReturnString(
                            "select serverport from salon_storestationsettings_system"), 1);
                    if (!GlobalMemberValues.isStrEmpty(tempValue)) {
                        (new SetServer(Integer.parseInt(tempValue))).start();
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reconnect(final String ip, final int port, final JSONObject jsonObject) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                (new Connect(ip,port,jsonObject)).start();
            }
        };
        thread.start();
    }

    ////// 새로 작업..

    // set server 하면 포트 열려있는지 확인하고 닫혀있으면 재 open

    // set connect 하면 연결 확인하고 실패하면 재 연결..
    public static void reconnect_check(){
        if (readSocket == null) {
            return;
        }
        if (!GlobalMemberValues.b_one_run_thread) {
            GlobalMemberValues.b_one_run_thread = true;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (true){

                        byte[] b = new byte[500];
                        int  readbytes = 0;
                        try {
                            sleep(5000);
                            readbytes = readSocket.read(b);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 연결이 되어 있음..
                        if(readbytes == 0){
                            Log.e("연결 유지..","연결 유지..");
                        }
                        // 끊어졌네요.
                        if (readbytes == -1){
                            Log.e("연결 끊어짐..","연결 끊어짐..");
                            GlobalMemberValues.b_socket_connected = false;
                            connectServer(GlobalMemberValues.getSocketServerIp(),Integer.parseInt(GlobalMemberValues.getSocketServerPort()));
                        }
                        // 리시브된 데이타가 있네요..
                        if(readbytes >0){
                        }
                    }

                }
            };
            thread.start();
        }
    }

    // 메세지 전송 실패시 반복해서 전송시도..
    // 성공할때까지.
    public static void reconnect_sendmessage(final JSONObject jsonObject){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connect_json_toMessage(jsonObject);
            }
        };
        thread.start();
    }

}
