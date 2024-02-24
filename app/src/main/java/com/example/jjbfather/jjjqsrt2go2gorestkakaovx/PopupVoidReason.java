package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class PopupVoidReason extends Dialog {

    private Button popup_void_reason_ok;
    private Button popup_void_reason_cancel;

    public EditText et_reason;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;


        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.popup_void_reason);

        //셋팅
        popup_void_reason_ok=(Button)findViewById(R.id.popup_void_reason_ok);
        popup_void_reason_cancel=(Button)findViewById(R.id.popup_void_reason_cancel);

        et_reason = (EditText) findViewById(R.id.popup_void_reason_reasontxt);


        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        popup_void_reason_ok.setOnClickListener(mPositiveListener);
        popup_void_reason_cancel.setOnClickListener(mNegativeListener);
    }

    //생성자 생성
    public PopupVoidReason(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super(context);
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;
    }
}
