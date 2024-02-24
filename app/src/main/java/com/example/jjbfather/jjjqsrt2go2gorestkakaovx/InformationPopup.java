package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class InformationPopup extends Dialog{

    private TextView txt_version;
    private Button btn_close;
    private String str_version;

    private View.OnClickListener mCloseClickListener;

    private TextView tv1, tv2, tv3, tv4, tv5;
    private TextView ntepversiontv, siteurltv;
    private TextView information_popup_model_name, information_popup_make_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.infomation_popup);

        setLayout();
        setVersion(str_version);
        setClickListener(mCloseClickListener);
    }

    public InformationPopup(Context context , String version ,View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.str_version = version;
        this.mCloseClickListener = singleListener;
    }

    private void setVersion(String version){
        txt_version.setText(version);
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
        txt_version = (TextView) findViewById(R.id.information_popup_version_num);

        ntepversiontv = (TextView) findViewById(R.id.ntepversiontv);
        siteurltv = (TextView) findViewById(R.id.siteurltv);
        information_popup_model_name = (TextView) findViewById(R.id.information_popup_model_name);
        information_popup_make_name = (TextView) findViewById(R.id.information_popup_make_name);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);

        txt_version.setTextSize(txt_version.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        ntepversiontv.setTextSize(ntepversiontv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        siteurltv.setTextSize(siteurltv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        information_popup_model_name.setTextSize(information_popup_model_name.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        information_popup_make_name.setTextSize(information_popup_make_name.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        tv1.setTextSize(tv1.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        tv2.setTextSize(tv2.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        tv3.setTextSize(tv3.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        tv4.setTextSize(tv4.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        tv5.setTextSize(tv5.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        btn_close = (Button) findViewById(R.id.information_popup_close_btn);
    }

}