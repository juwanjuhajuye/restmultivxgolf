package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CreditCardStatusPopup extends Dialog{

    private TextView textView;
    private ImageButton btn_close;
    private String stringArrayList;

    private View.OnClickListener mCloseClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.popup_credit_card_status);

        setLayout();
        setClickListener(mCloseClickListener);
    }

    public CreditCardStatusPopup(Context context , String stringArrayList , View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.stringArrayList = stringArrayList;
        this.mCloseClickListener = singleListener;
    }

    private void setClickListener(View.OnClickListener close){
        if(close!=null){
            btn_close.setOnClickListener(close);
        }else {

        }
    }



    /*
     * Layout
     */
    private void setLayout(){
        textView = (TextView) findViewById(R.id.credit_card_status_text_main);
        String[] temp_result = stringArrayList.split("-JJJ-");
        String temp_str = "";
        temp_str = Payment_CreditCard_Proc_Exec.getMenuItemsAll(temp_result[4]);
        textView.setText(temp_str);

        btn_close = (ImageButton) findViewById(R.id.credit_card_status_popup_close_btn);
    }

}