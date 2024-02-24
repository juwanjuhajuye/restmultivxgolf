package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


public class Popup_to_go_table extends Dialog {

    private Context mContext;
    private String title, mainTxt;
    private CustomDialogClickListener clickListener;

    public Popup_to_go_table(Context context, String title, String maintxt, CustomDialogClickListener click_true) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.mainTxt = maintxt;
        this.clickListener = click_true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_to_go_table);

        TextView title_txv = findViewById(R.id.option_codetype_dialog_title_tv);
        title_txv.setText(title);
        TextView main_txv = findViewById(R.id.option_codetype_dialog_text);
        main_txv.setText(mainTxt);
        TextView true_txv = findViewById(R.id.option_codetype_dialog_positive);
        true_txv.setOnClickListener(v -> {
            // 저장버튼 클릭
            this.clickListener.onPositiveClick();
            dismiss();
        });
        TextView false_txv = findViewById(R.id.option_codetype_dialog_negative);
        false_txv.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.clickListener.onNegativeClick();
            dismiss();
        });
    }
}
