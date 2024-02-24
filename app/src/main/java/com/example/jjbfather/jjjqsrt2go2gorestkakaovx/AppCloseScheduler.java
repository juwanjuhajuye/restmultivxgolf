package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AppCloseScheduler {

    private Timer timer;

    public void scheduleTask() {
        timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3); // 작동 시간의 시간 설정 (예: 10시)
        calendar.set(Calendar.MINUTE, 30); // 작동 시간의 분 설정 (예: 0분)
        calendar.set(Calendar.SECOND, 0); // 작동 시간의 초 설정 (예: 0초)

        // 현재 시간과 작동 시간의 차이 계산
        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();

        if (delay < 0) {
            // 작동 시간이 이미 지났을 경우 다음 날로 설정
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        }

        // 작동 시간에 실행될 작업 정의
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 작동할 함수 또는 작업을 여기에 작성합니다.
                if (MainActivity.mActivity != null){
                    GlobalMemberValues.doMethodInClose(MainActivity.mActivity);
                }
            }
        };

        // 작업을 예약
        timer.schedule(task, delay);
    }

    public void cancelTask() {
        // 작업 예약을 취소
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}