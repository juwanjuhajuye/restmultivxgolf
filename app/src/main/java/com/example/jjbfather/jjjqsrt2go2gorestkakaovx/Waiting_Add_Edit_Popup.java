package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;

public class Waiting_Add_Edit_Popup extends Activity {

    EditText editText_phone, editText_name, editText_memo;
    GridView gridView_size, gridView_partysize1, gridView_partysize2, gridView_time;

    Button btn_save, btn_cancel;
    ImageButton btn_close;

    Spinner spinner_time1,spinner_time2,spinner_time3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_popup_add_edit);

    }

    private void setContent(){
        editText_phone = findViewById(R.id.waitingpopup_editText_phone);
        editText_name = findViewById(R.id.waitingpopup_editText_name);
        editText_memo = findViewById(R.id.waitingpopup_editText_memo);

        btn_save = findViewById(R.id.waitingpopup_btn_save);
        btn_cancel = findViewById(R.id.waitingpopup_btn_cancel);
        btn_close = findViewById(R.id.waitingpopup_btn_close);

        gridView_size = findViewById(R.id.waitingpopup_gridView_size);
        gridView_partysize1 = findViewById(R.id.waitingpopup_gridView_partysize1);
        gridView_partysize2 = findViewById(R.id.waitingpopup_gridView_partysize2);
        gridView_time = findViewById(R.id.waitingpopup_gridView_time);

        spinner_time1 = findViewById(R.id.waitingpopup_spinner_time1);
        spinner_time2 = findViewById(R.id.waitingpopup_spinner_time2);
        spinner_time3 = findViewById(R.id.waitingpopup_spinner_time3);
    }
}
