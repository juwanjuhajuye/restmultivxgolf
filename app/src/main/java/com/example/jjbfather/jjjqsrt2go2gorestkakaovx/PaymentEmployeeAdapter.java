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
 * Created by BCS_RTBS_JJFATHER on 2015-11-04.
 */
public class PaymentEmployeeAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryPaymentEmployeeInfo> mArSrc;
    int layout;
    TemporaryCustomerInfo mTempCustomerInfo;

    public PaymentEmployeeAdapter(Context context, int alayout, ArrayList<TemporaryPaymentEmployeeInfo> aarSrc) {
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
        TextView tvPEName = (TextView)convertView.findViewById(R.id.paymentEmployeeName);
        tvPEName.setText(mArSrc.get(position).empName);
        tvPEName.setTextSize(GlobalMemberValues.BASICPAYMENTEMPLOYEELISTTTEXTSIZE);
        tvPEName.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPEAmount = (TextView)convertView.findViewById(R.id.paymentEmployeeAmount);
        tvPEAmount.setText(GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).empAmount));
        tvPEAmount.setTextSize(GlobalMemberValues.BASICPAYMENTEMPLOYEELISTTTEXTSIZE);
        tvPEAmount.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        //GlobalMemberValues.logWrite("LVListViewIdx", "Employee Name : " + mArSrc.get(position).empName);

        return convertView;
    }
}
