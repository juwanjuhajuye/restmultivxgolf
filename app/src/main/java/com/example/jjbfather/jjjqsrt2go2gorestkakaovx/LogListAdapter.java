package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LogListAdapter extends BaseAdapter {

    ArrayList<ArrayList> arrayList;
    Context mContext;

    public LogListAdapter(Context context, ArrayList<ArrayList> arr_list){
        mContext = context;
        arrayList = arr_list;
    }

    @Override
    public int getCount() {
        if (arrayList == null) return 0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.loglist_row, parent, false);
        }

        TextView tv_date = convertView.findViewById(R.id.loglist_row_date);
        TextView tv_emp_name = convertView.findViewById(R.id.loglist_row_empname);
        TextView tv_btn_name = convertView.findViewById(R.id.loglist_row_btnname);
        TextView tv_btn_text = convertView.findViewById(R.id.loglist_row_btntext);

        TextView tv_loglist_row_salescode = convertView.findViewById(R.id.loglist_row_salescode);

        TextView tv_row_stcode = convertView.findViewById(R.id.loglist_row_stcode);

        tv_date.setText((CharSequence) arrayList.get(position).get(9));
        tv_emp_name.setText((CharSequence) arrayList.get(position).get(8));
        tv_btn_name.setText((CharSequence) arrayList.get(position).get(4));
        tv_btn_text.setText((CharSequence) arrayList.get(position).get(6));

        tv_loglist_row_salescode.setText((CharSequence) arrayList.get(position).get(10));

        tv_row_stcode.setText((CharSequence) arrayList.get(position).get(2));

        return convertView;
    }
}
