package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.SystemClock;
import android.view.View;

public abstract class JJJ_OnSingleClickListener implements View.OnClickListener {
    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL = 1000;

    private long mLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if(elapsedTime <= MIN_CLICK_INTERVAL){
            return;
        }

        // 중복 클릭아 아니라면 추상함수 호출
        onSingleClick(v);
    }
}
