package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WaitingListAdapter extends BaseAdapter {

    public WaitingListAdapter(){

    }

    @Override
    public int getCount() {
        return 0;
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
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.salehistory_list_item, parent, false);
        }
        TextView textView1 = convertView.findViewById(R.id.waitingview_list_content1);
        TextView textView2 = convertView.findViewById(R.id.waitingview_list_content2);
        TextView textView2_1 = convertView.findViewById(R.id.waitingview_list_content2_1);
        TextView textView3 = convertView.findViewById(R.id.waitingview_list_content3);
        TextView textView4 = convertView.findViewById(R.id.waitingview_list_content4);
        TextView textView5 = convertView.findViewById(R.id.waitingview_list_content5);
        TextView textView6 = convertView.findViewById(R.id.waitingview_list_content6);
        return convertView;
    }
}
