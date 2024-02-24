package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
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
public class MenuSearchInfoAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporaryServiceInfo> mArSrc;
    int layout;
    TemporaryProductInfo mTempCustomerInfo;

    public MenuSearchInfoAdapter(Context context, int alayout, ArrayList<TemporaryServiceInfo> aarSrc) {
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
        String tempSaleYN = getProductSaleYN(mArSrc.get(position).svcSalePrice,
                mArSrc.get(position).svcSaleStart, mArSrc.get(position).svcSaleEnd);
        String tempSalePrice = "";
        if (tempSaleYN == "Y") {
            tempSalePrice = mArSrc.get(position).svcSalePrice;
        }

        TextView tvMenuName = (TextView)convertView.findViewById(R.id.productListViewItemMenuName);
        tvMenuName.setText(Html.fromHtml(mArSrc.get(position).svcServiceNameForSearch));
        tvMenuName.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvMenuName.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvCategory = (TextView)convertView.findViewById(R.id.productListViewItemCategory);
        tvCategory.setText(mArSrc.get(position).svcCategoryName);
        tvCategory.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvCategory.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvPrice = (TextView)convertView.findViewById(R.id.productListViewItemMenuPrice);
        tvPrice.setText(GlobalMemberValues.getCommaStringForDouble(mArSrc.get(position).svcServicePrice));
        tvPrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvPrice.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvSalePrice = (TextView)convertView.findViewById(R.id.productListViewItemSalePrice);
        tvSalePrice.setText(GlobalMemberValues.getCommaStringForDouble(tempSalePrice));
        tvSalePrice.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICPRODUCTLISTTEXTSIZE);
        tvSalePrice.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

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
