package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class TableInforPopupMenuListAdapter extends BaseAdapter {
    ArrayList<String> itemList;

    public TableInforPopupMenuListAdapter(ArrayList<String> arr) {
        this.itemList = arr;
    }
    @Override
    public int getCount() {
        return this.itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_info_menu_list_row, parent, false);
        }
        GlobalMemberValues.logWrite("tableinforlist", "value : " + itemList.get(position) + "\n");
        String[] item_string = itemList.get(position).split("-JJJ-");


        TextView itemNameTv = (TextView) convertView.findViewById(R.id.table_info_menu_list_itemname);
        itemNameTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView itemQtyTv = (TextView) convertView.findViewById(R.id.table_info_menu_list_itemqty);
        itemQtyTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView billNumTv = (TextView) convertView.findViewById(R.id.table_info_menu_list_itemindex);
        billNumTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        itemNameTv.setText(item_string[1]);
        itemQtyTv.setText(item_string[2]);
        billNumTv.setText(TableSaleBillSplit.getBillNumString(item_string[3]));

        LinearLayout optionTxtLn = (LinearLayout) convertView.findViewById(R.id.optionTxtLn);
        optionTxtLn.setVisibility(View.GONE);

        String optionTxt = "";
        if (!GlobalMemberValues.isStrEmpty(item_string[4])) {
            optionTxtLn.setVisibility(View.VISIBLE);
            optionTxt = "\n" + item_string[4];
            TextView optionTxtTv = (TextView) convertView.findViewById(R.id.optionTxtTv);
            optionTxtTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                    + 20 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
            optionTxtTv.setText(optionTxt);
        }

        return convertView;
    }
}
