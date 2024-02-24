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
public class ProductInfoAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryProductInfo> mArSrc;
    int layout;
    TemporaryProductInfo mTempCustomerInfo;

    public ProductInfoAdapter(Context context, int alayout, ArrayList<TemporaryProductInfo> aarSrc) {
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
        String tempSaleYN = getProductSaleYN(mArSrc.get(position).pdSalePrice,
                mArSrc.get(position).pdSaleStart, mArSrc.get(position).pdSaleEnd);
        String tempSalePrice = "";
        if (tempSaleYN == "Y") {
            tempSalePrice = mArSrc.get(position).pdSalePrice;
        }

        TextView tvPdName = (TextView)convertView.findViewById(R.id.productListViewItemProductName);
        tvPdName.setText(mArSrc.get(position).pdName);
        tvPdName.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvPdName.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPdDescription = (TextView)convertView.findViewById(R.id.productListViewItemProductDescription);
        tvPdDescription.setText(mArSrc.get(position).pdDescription);
        tvPdDescription.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvPdDescription.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPdPrice = (TextView)convertView.findViewById(R.id.productListViewItemProductPrice);
        tvPdPrice.setText(GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).pdPrice));
        tvPdPrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvPdPrice.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPdSalePrice = (TextView)convertView.findViewById(R.id.productListViewItemSalePrice);
        tvPdSalePrice.setText(GlobalMemberValues.getCommaStringForDouble(tempSalePrice));
        tvPdSalePrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvPdSalePrice.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("ProductInfo", "Product Name : " + mArSrc.get(position).pdName);

        return convertView;
    }

    public String getProductSaleYN(String paramSalePrice,
                                  String paramSaleStart, String paramSaleEnd) {
        String insPdSaleYN = "N";
        // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
        if ((!GlobalMemberValues.isStrEmpty(paramSaleStart)
                && DateMethodClass.getDiffDayCount(paramSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                && (!GlobalMemberValues.isStrEmpty(paramSaleEnd)
                && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, paramSaleEnd) >= 0)
                &&!GlobalMemberValues.isStrEmpty(paramSalePrice)) {
            insPdSaleYN = "Y";
        }
        // -------------------------------------------------------------------------------------
        return insPdSaleYN;
    }
}
