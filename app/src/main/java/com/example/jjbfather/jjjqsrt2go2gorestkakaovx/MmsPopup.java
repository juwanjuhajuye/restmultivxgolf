package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MmsPopup extends Dialog {

    private ImageButton mms_popup_close_btn, mms_popup_send_btn;
    private ListView mms_popup_listview;
    private EditText mms_popup_to_phone_number_edtxt,mms_popup_to_message_edtxt;
    private View.OnClickListener mCloseClickListener;

    private Context getContext = null;
    private String getOrderNum = "";
    private String getPhoneNum = "";

    private JSONArray mms_jsonArray;

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.mms_popup);
        setLayout();
        setClickListener(mCloseClickListener);

    }

    public MmsPopup(Context context, View.OnClickListener singleListener, String paramOrderNum, String paramPhoneNum) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);

        this.getContext = context;
        this.mCloseClickListener = singleListener;
        this.getOrderNum = paramOrderNum;
        this.getPhoneNum = paramPhoneNum;

        Cursor cursor = MainActivity.mDbInit.dbExecuteRead("Select name from salon_storesmstextsample order by sortnum asc");
        mms_jsonArray = new JSONArray();

        while (cursor.moveToNext()) {
            mms_jsonArray.put(cursor.getString(0));
        }

    }

    private void setClickListener(View.OnClickListener close){
        if(close!=null){
            mms_popup_close_btn.setOnClickListener(close);
        }else {

        }
    }


    /*
     * Layout
     */
    private void setLayout(){
        mms_popup_close_btn = findViewById(R.id.mms_popup_close_btn);

        mms_popup_send_btn = findViewById(R.id.mms_popup_send_btn);


        mms_popup_listview = findViewById(R.id.mms_popup_listview);
        listAdapter = new ListAdapter(mms_jsonArray);
        mms_popup_listview.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        mms_popup_to_phone_number_edtxt = findViewById(R.id.mms_popup_to_phone_number_edtxt);
        mms_popup_to_message_edtxt = findViewById(R.id.mms_popup_to_message_edtxt);

        setInit();

        mms_popup_to_phone_number_edtxt.setText(getPhoneNum);


        mms_popup_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toPhoneNum = mms_popup_to_phone_number_edtxt.getText().toString();
                String tempMsg = mms_popup_to_message_edtxt.getText().toString();

                GlobalMemberValues.sendSMSMsgSend(getContext, getOrderNum, toPhoneNum, tempMsg);

                setInit();
            }
        });

    }

    class ListAdapter extends BaseAdapter{
        JSONArray list_json;

        public ListAdapter(JSONArray jsonArray){
            list_json = jsonArray;
        }

        @Override
        public int getCount() {
            return list_json.length();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mmspopup_list_row, parent, false);
            }

            TextView rowtitle_txtview = convertView.findViewById(R.id.mms_list_row_txt);
            String temp_txt = "";
            try {
                temp_txt = list_json.getString(position);
                rowtitle_txtview.setText(temp_txt);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String finalTemp_txt = temp_txt;
            rowtitle_txtview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mms_popup_to_message_edtxt.setText(finalTemp_txt);
                }
            });

            return convertView;
        }
    }

    public void setInit() {
        mms_popup_to_phone_number_edtxt.setText("");
        mms_popup_to_message_edtxt.setText("");
    }

}