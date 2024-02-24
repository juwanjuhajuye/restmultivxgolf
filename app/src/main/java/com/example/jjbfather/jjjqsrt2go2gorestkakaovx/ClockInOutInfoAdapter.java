package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-15.
 */
public class ClockInOutInfoAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryClockInOut> mArSrc;
    int layout;

    public ClockInOutInfoAdapter(Context context, int alayout, ArrayList<TemporaryClockInOut> aarSrc) {
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

        String tempClockInDate = mArSrc.get(position).clockinDay + " " + mArSrc.get(position).clockinTime;
        String tempClockOutDate = mArSrc.get(position).clockoutDay + " " + mArSrc.get(position).clockoutTime;

        TextView clockInOutCountTv = (TextView)convertView.findViewById(R.id.clockInOutSearchListCountTv);
        clockInOutCountTv.setText(mArSrc.get(position).count);
        clockInOutCountTv.setTextSize(GlobalMemberValues.BASICCLOCKINOUTLISTTTEXTSIZE);

        TextView clockInOutEmpNameTv = (TextView)convertView.findViewById(R.id.clockInOutSearchListEmpNameTv);
        clockInOutEmpNameTv.setText(mArSrc.get(position).employeeName);
        clockInOutEmpNameTv.setTextSize(GlobalMemberValues.BASICCLOCKINOUTLISTTTEXTSIZE);

        TextView clockInOutInDateTv = (TextView)convertView.findViewById(R.id.clockInOutSearchListInDateTv);
        clockInOutInDateTv.setText(tempClockInDate);
        clockInOutInDateTv.setTextSize(GlobalMemberValues.BASICCLOCKINOUTLISTTTEXTSIZE);

        TextView clockInOutOutDateTv = (TextView)convertView.findViewById(R.id.clockInOutSearchListOutDateTv);
        clockInOutOutDateTv.setText(tempClockOutDate);
        clockInOutOutDateTv.setTextSize(GlobalMemberValues.BASICCLOCKINOUTLISTTTEXTSIZE);

        TextView clockInOutWorkTimeAmountTv = (TextView)convertView.findViewById(R.id.clockInOutSearchListTimeAmountTv);
        clockInOutWorkTimeAmountTv.setText(mArSrc.get(position).workTimeAmount);
        clockInOutWorkTimeAmountTv.setTextSize(GlobalMemberValues.BASICCLOCKINOUTLISTTTEXTSIZE);

        Button clockInOutSearchListEditBtn = (Button)convertView.findViewById(R.id.clockInOutSearchListEditBtn);
        clockInOutSearchListEditBtn.setTag(mArSrc.get(position).idx);

        clockInOutSearchListEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saleHistoryReturnIntent = new Intent(mContext, ClockInOutEdit.class);
                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                //saleHistoryIntent.putExtra("ParentMainMiddleService", this.getClass());
                saleHistoryReturnIntent.putExtra("clockInOutIdx", v.getTag().toString());
                // -------------------------------------------------------------------------------------
                mContext.startActivity(saleHistoryReturnIntent);
            }
        });

        GlobalMemberValues.logWrite("ClockInOutInfoAdapter", "clockoutDate : " + mArSrc.get(position).clockoutDate + "\n");
        return convertView;
    }
}
