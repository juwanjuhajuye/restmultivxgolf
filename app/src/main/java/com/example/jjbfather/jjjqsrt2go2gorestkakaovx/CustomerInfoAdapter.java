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
public class CustomerInfoAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryCustomerInfo> mArSrc;
    int layout;
    TemporaryCustomerInfo mTempCustomerInfo;

    public CustomerInfoAdapter(Context context, int alayout, ArrayList<TemporaryCustomerInfo> aarSrc) {
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
        TextView tvUid = (TextView)convertView.findViewById(R.id.customerSearchListViewItemUid);
        tvUid.setText(mArSrc.get(position).memUid);
        tvUid.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvUid.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvName = (TextView)convertView.findViewById(R.id.customerSearchListViewItemName);
        tvName.setText(mArSrc.get(position).memName);
        tvName.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvName.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPhone = (TextView)convertView.findViewById(R.id.customerSearchListViewItemPhone);
        tvPhone.setText(mArSrc.get(position).memmemmobile);
        tvPhone.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvPhone.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        // 이메일 주소 가져오기
        String customerEmail = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select email from member2 where uid = '" + mArSrc.get(position).memUid + "' ");

        TextView tvAddr = (TextView)convertView.findViewById(R.id.customerSearchListViewItemAddr);
        //tvAddr.setText(mArSrc.get(position).memAddr1);
        tvAddr.setText(customerEmail);
        tvAddr.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICCUSTOMERSEARCHTEXTSIZE);
        tvAddr.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("LVListViewIdx", "Customer Name : " + mArSrc.get(position).memName);

        return convertView;
    }
}
