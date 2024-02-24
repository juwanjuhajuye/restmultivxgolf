package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class Popup_to_go_table_3btn extends Dialog {

    private Context mContext;
    private String title, mainTxt;
    private CustomDialogClickListener clickListener;

    private float f_fontsize_forPAX = GlobalMemberValues.getGlobalFontSize();
    private float f_globalFontSize = GlobalMemberValues.globalAddFontSize();

    public Popup_to_go_table_3btn(Context context, String title, String maintxt, CustomDialogClickListener clicklistener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.mainTxt = maintxt;
        this.clickListener = clicklistener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_to_go_table_3btn);

//        TextView title_txv = findViewById(R.id.option_codetype_dialog_title_tv);
//        title_txv.setText(title);
//        title_txv.setTextSize(f_globalFontSize
//                + title_txv.getTextSize() * f_fontsize_forPAX);

        TextView main_txv = findViewById(R.id.option_codetype_dialog_text);
        main_txv.setTextSize(f_globalFontSize
                + main_txv.getTextSize() * f_fontsize_forPAX);
        main_txv.setText(mainTxt);

        TextView true_txv = findViewById(R.id.option_codetype_dialog_positive);
        true_txv.setTextSize(f_globalFontSize
                + true_txv.getTextSize() * f_fontsize_forPAX);
        true_txv.setOnClickListener(v -> {
            // 저장버튼 클릭
            this.clickListener.onPositiveClick();
            dismiss();
        });

//        TextView mid_txv = findViewById(R.id.option_codetype_dialog_mid);
//        mid_txv.setTextSize(f_globalFontSize
//                + mid_txv.getTextSize() * f_fontsize_forPAX);
//        mid_txv.setOnClickListener(v -> {
//            // 저장버튼 클릭
//            this.clickListener.onMiddClick();
//            dismiss();
//        });
//        mid_txv.setVisibility(View.GONE);

        TextView false_txv = findViewById(R.id.option_codetype_dialog_negative);
        false_txv.setTextSize(f_globalFontSize
                + false_txv.getTextSize() * f_fontsize_forPAX);
        false_txv.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.clickListener.onNegativeClick();
            dismiss();
        });
    }
}
