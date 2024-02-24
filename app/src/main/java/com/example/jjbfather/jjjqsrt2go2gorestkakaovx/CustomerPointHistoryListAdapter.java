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
 * Created by BCS_RTBS_JJFATHER on 2017-07-19.
 */
public class CustomerPointHistoryListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryCustomerPointHistoryList> mArSrc;
    int layout;

    public CustomerPointHistoryListAdapter(Context context, int alayout, ArrayList<TemporaryCustomerPointHistoryList> aarSrc) {
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
        TextView tvDate = (TextView)convertView.findViewById(R.id.customerPointHistoryDateTv);
        tvDate.setText(mArSrc.get(position).customerPointHistoryDate);
        tvDate.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvDate.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        String tempAddedPrice = "-";
        String tempUsedPrice = "-";

        switch (mArSrc.get(position).customerPointMcase) {
            case "1" : {
                tempAddedPrice = GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).customerPointMileage);
                break;
            }
            case "2" : {
                tempUsedPrice = GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).customerPointMileage);
            }
        }

        TextView tvAdded = (TextView)convertView.findViewById(R.id.customerPointHistoryAddedTv);
        tvAdded.setText(tempAddedPrice);
        tvAdded.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvAdded.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvUsed = (TextView)convertView.findViewById(R.id.customerPointHistoryUsedTv);
        tvUsed.setText(tempUsedPrice);
        tvUsed.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvUsed.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvContents = (TextView)convertView.findViewById(R.id.customerPointHistoryContentsTv);
        tvContents.setText(mArSrc.get(position).customerPointContents);
        tvContents.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvContents.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("customerPointHistoryList", "Date : " + mArSrc.get(position).customerPointHistoryDate + "\n");
        GlobalMemberValues.logWrite("customerPointHistoryList", "Added : " + tempAddedPrice + "\n");
        GlobalMemberValues.logWrite("customerPointHistoryList", "Used : " + tempUsedPrice + "\n");

        return convertView;
    }
}
