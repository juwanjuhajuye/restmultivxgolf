package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PopupIpInput extends Dialog {

    private Button popupipinputok;
    private Button popupipinputcancel;

    public TextView ip1;
    public EditText popup_dabasename_edtxt,popup_dbpassword_edtxt,popup_mobilehost_edtxt,popup_cloudhost_edtxt;

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

        setContentView(R.layout.popupipinput);

        //셋팅
        popupipinputok=(Button)findViewById(R.id.popupipinputok);
        popupipinputcancel=(Button)findViewById(R.id.popupipinputcancel);

        ip1 = (TextView)findViewById(R.id.mssqldbipTextView1);


        popup_dabasename_edtxt = (EditText)findViewById(R.id.popup_dabasename_edtxt);
        popup_dbpassword_edtxt = (EditText)findViewById(R.id.popup_dbpassword_edtxt);
        popup_mobilehost_edtxt = (EditText)findViewById(R.id.popup_mobilehost_edtxt);
        popup_cloudhost_edtxt = (EditText)findViewById(R.id.popup_cloudhost_edtxt);

        popup_dabasename_edtxt.setText(GlobalMemberValues.DATABASE_NAME);
        popup_dbpassword_edtxt.setText(GlobalMemberValues.mssql_pw);
        popup_mobilehost_edtxt.setText(GlobalMemberValues.MOBILE_HOST);
        popup_cloudhost_edtxt.setText(GlobalMemberValues.CLOUD_HOST);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        popupipinputok.setOnClickListener(mPositiveListener);
        popupipinputcancel.setOnClickListener(mNegativeListener);
    }

    //생성자 생성
    public PopupIpInput(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super(context);
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;
    }
}
