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
public class CashInOutReasonHistoryListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryCashInOutReasonHistory> mArSrc;
    int layout;
    TemporaryCashInOutReasonHistory mTempCustomerMemoHistoryList;

    public CashInOutReasonHistoryListAdapter(Context context, int alayout, ArrayList<TemporaryCashInOutReasonHistory> aarSrc) {
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

        TextView tvDate = (TextView)convertView.findViewById(R.id.cashinoutreasonDateTv);
        tvDate.setText(mArSrc.get(position).inoutdate);
        tvDate.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        tvDate.setTextColor(Color.parseColor("#969597"));

        TextView tvReason = (TextView)convertView.findViewById(R.id.cashinoutreasonReasonTv);
        tvReason.setText(mArSrc.get(position).inoutreason);
        tvReason.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        tvReason.setTextColor(Color.parseColor("#969597"));

        String tempInOutMoney = mArSrc.get(position).inoutmoney;
        TextView tvAmount = (TextView)convertView.findViewById(R.id.cashinoutreasonAmountTv);
        tvAmount.setText(tempInOutMoney);
        tvAmount.setTextSize(GlobalMemberValues.globalAddFontSize() + 12);
        if (GlobalMemberValues.getDoubleAtString(tempInOutMoney) < 0) {
            tvAmount.setTextColor(Color.parseColor("#ee0e02"));
        } else {
            tvAmount.setTextColor(Color.parseColor("#969597"));
        }


        GlobalMemberValues.logWrite("CashInOutReasonHistoryList", "Date : " + mArSrc.get(position).inoutdate + "\n");
        GlobalMemberValues.logWrite("CashInOutReasonHistoryList", "Reason : " + mArSrc.get(position).inoutreason + "\n");
        GlobalMemberValues.logWrite("CashInOutReasonHistoryList", "Amount : " + mArSrc.get(position).inoutmoney + "\n");

        return convertView;
    }
}
