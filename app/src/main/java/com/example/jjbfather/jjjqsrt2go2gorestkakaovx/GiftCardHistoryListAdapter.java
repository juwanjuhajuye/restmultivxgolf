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
public class GiftCardHistoryListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryGiftCardHistoryList> mArSrc;
    int layout;
    TemporaryGiftCardHistoryList mTempGiftCardHistoryList;

    public GiftCardHistoryListAdapter(Context context, int alayout, ArrayList<TemporaryGiftCardHistoryList> aarSrc) {
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
        TextView tvDate = (TextView)convertView.findViewById(R.id.giftCardHistoryDateTv);
        tvDate.setText(mArSrc.get(position).giftCardHistoryDate);
        tvDate.setTextSize(GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvDate.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        String tempAddedPrice = "-";
        String tempUsedPrice = "-";

        switch (mArSrc.get(position).giftCardHistoryAddUseType) {
            case "A" : {
                tempAddedPrice = GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).giftCardHistoryAddUsePrice);
                break;
            }
            case "U" : {
                tempUsedPrice = GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).giftCardHistoryAddUsePrice);
            }
        }

        TextView tvAdded = (TextView)convertView.findViewById(R.id.giftCardHistoryAddedTv);
        tvAdded.setText(tempAddedPrice);
        tvAdded.setTextSize(GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvAdded.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvUsed = (TextView)convertView.findViewById(R.id.giftCardHistoryUsedTv);
        tvUsed.setText(tempUsedPrice);
        tvUsed.setTextSize(GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvUsed.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("GiftCardHistoryList", "Date : " + mArSrc.get(position).giftCardHistoryDate + "\n");
        GlobalMemberValues.logWrite("GiftCardHistoryList", "Added : " + tempAddedPrice + "\n");
        GlobalMemberValues.logWrite("GiftCardHistoryList", "Used : " + tempUsedPrice + "\n");

        return convertView;
    }
}
