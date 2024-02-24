package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-15.
 */
public class CustomerMemoHistoryListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryCustomerMemoHistory> mArSrc;
    int layout;
    TemporaryCustomerMemoHistory mTempCustomerMemoHistoryList;

    public CustomerMemoHistoryListAdapter(Context context, int alayout, ArrayList<TemporaryCustomerMemoHistory> aarSrc) {
        this.mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArSrc = aarSrc;
        this.layout = alayout;
    }

    @Override
    public int getCount() {
        return mArSrc.size();
    }

    @Override
    public String getItem(int position) {
        //return mArSrc.get(position).sHoldCode;
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = mInflater.inflate(layout, parent, false);
        }

        TextView tvDate = (TextView)convertView.findViewById(R.id.customerMemoHistoryDateTv);
        tvDate.setText(mArSrc.get(position).customerMemoDate);
        tvDate.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE - 2));
        tvDate.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvNote = (TextView)convertView.findViewById(R.id.customerMemoHistoryAddedTv);
        tvNote.setText(mArSrc.get(position).customerMemoNote);
        tvNote.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvNote.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("CustomerHistoryList", "Date : " + mArSrc.get(position).customerMemoDate + "\n");
        GlobalMemberValues.logWrite("CustomerHistoryList", "Note : " + mArSrc.get(position).customerMemoNote + "\n");

        return convertView;
    }
}
