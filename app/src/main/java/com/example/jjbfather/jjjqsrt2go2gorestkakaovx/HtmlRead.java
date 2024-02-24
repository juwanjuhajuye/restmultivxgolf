package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pc on 2016-07-04.
 */
public class HtmlRead {
    Handler handler2 = new Handler(); // 화면에 그려주기 위한 객체

    public HtmlRead() {
        GlobalMemberValues.logWrite("htmlreadtest", "여기까지...1\n");
    }

    public void readHTML(final String paramUrlAddress) { // 웹에서 html 읽어오기
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuffer sb = new StringBuffer();
                try {
                    URL url = new URL(paramUrlAddress);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection(); // 접속
                    if (conn != null) {
                        conn.setConnectTimeout(5000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            //    데이터 읽기
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream(),"utf-8"));//utf-8,euc-kr
                            while(true) {
                                String line = br.readLine();
                                if (line == null) break;
                                sb.append(line+"\n");
                            }
                            br.close(); // 스트림 해제
                        }
                        conn.disconnect(); // 연결 끊기
                    }
                    // 값을 출력하기
                    //GlobalMemberValues.logWrite("htmlreadtest", "HTML 결과물...: " + sb.toString() + "\n");
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            //tv.setText(sb.toString());
                            PaymentReview.htmlStr = sb.toString();
                            PaymentEmailReceipt.htmlStr = sb.toString();
                            GlobalMemberValues.logWrite("htmlreadtest", "HTML 결과물...: " + PaymentReview.htmlStr + "\n");
                            GlobalMemberValues.logWrite("htmlreadtest", "HTML 결과물...: " + PaymentEmailReceipt.htmlStr + "\n");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    }
}
