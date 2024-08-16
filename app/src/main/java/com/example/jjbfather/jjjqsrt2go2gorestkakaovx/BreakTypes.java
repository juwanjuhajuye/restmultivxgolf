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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class BreakTypes extends Dialog {

    private ImageButton bt_popup_close_btn;
    private ListView bt_popup_listview;
    private View.OnClickListener mCloseClickListener;

    private Context getContext = null;

    private JSONArray bt_jsonArray;

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_break_types);
        setLayout();
        setClickListener(mCloseClickListener);
    }

    public BreakTypes(Context context, View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);

        this.getContext = context;
        this.mCloseClickListener = singleListener;

        Cursor cursor = MainActivity.mDbInit.dbExecuteRead(
                "Select name, duration from salon_storebreaktime " +
                        " where useyn = 'Y' and delyn = 'N' " +
                        " order by sortnum asc"
        );
        bt_jsonArray = new JSONArray();

        String btename = "";
        String btduration = "";
        while (cursor.moveToNext()) {
            btename = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
            btduration = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);

            bt_jsonArray.put(btename + "[" + btduration + " min(s)]-jjj-" + btduration + "-jjj-END");
        }

    }

    private void setClickListener(View.OnClickListener close){
        if(close!=null){
            bt_popup_close_btn.setOnClickListener(close);
        }else {

        }
    }


    /*
     * Layout
     */
    private void setLayout(){
        bt_popup_close_btn = findViewById(R.id.mms_popup_close_btn);

        bt_popup_listview = findViewById(R.id.mms_popup_listview);
        listAdapter = new ListAdapter(bt_jsonArray);
        bt_popup_listview.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
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
                convertView = inflater.inflate(R.layout.breaktype_list, parent, false);
            }

            String getDuration = "";

            TextView rowtitle_txtview = convertView.findViewById(R.id.breaktype_list_row_txt);
            String temp_txt = "";
            try {
                temp_txt = list_json.getString(position);
                String[] temp_txt_spt = temp_txt.split("-jjj-");
                String intxt = temp_txt_spt[0];
                rowtitle_txtview.setText(intxt);

                getDuration = temp_txt_spt[1];

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String finalGetDuration = getDuration;
            rowtitle_txtview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClockInOutNavtiveWeb.mDataValues[9] = finalGetDuration;

                    ClockInOutNavtiveWeb.setClockInOutByApi(ClockInOutNavtiveWeb.mDataValues);
                    setInit();

                    ClockInOutNavtiveWeb.breakTypes.cancel();
                }
            });

            return convertView;
        }
    }

    public void setInit() {
        ClockInOutNavtiveWeb.initValue();
    }

}