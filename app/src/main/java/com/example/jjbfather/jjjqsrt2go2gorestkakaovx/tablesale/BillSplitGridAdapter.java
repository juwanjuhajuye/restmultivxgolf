package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class BillSplitGridAdapter extends BaseAdapter {

    ArrayList<String> listViewItemList = new ArrayList<String>();

    public BillSplitGridAdapter(ArrayList<String> arr) {
        this.listViewItemList = arr;
    }

    @Override
    public int getCount() {
        if (listViewItemList == null) return 0;
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tablesale_grid_bill_row, parent, false);
        }

        String[] item_string = listViewItemList.get(position).split("-JJJ-");
        String str_title = "";
        String str_menu_qty = "";
        String str_amount = "";
        String str_paidyn = "";
        String paidyn_string = "";

        str_title = item_string[0];
        str_menu_qty = item_string[1];
        str_amount = item_string[2];
        str_paidyn = item_string[5];
        if (GlobalMemberValues.isStrEmpty(str_paidyn)) {
            str_paidyn = "N";
        }
        if (str_paidyn.equals("Y")) {
            paidyn_string = "PAID";
        } else {
            paidyn_string = "";
        }

        if (str_title.equals("empty")){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.tablesale_grid_bill_row_empty, parent, false);
        } else {
            TextView tv_title = convertView.findViewById(R.id.tablesale_grid_row_title);
            tv_title.setText(str_title);
            TextView tv_menu_qty = convertView.findViewById(R.id.tablesale_grid_row_menu);
            tv_menu_qty.setText("Menu : " + str_menu_qty);
            TextView tv_amount = convertView.findViewById(R.id.tablesale_grid_row_amount);
            tv_amount.setText("Amount : $" +str_amount);
            TextView tv_menu_paidyn = convertView.findViewById(R.id.tablesale_grid_row_paidyn);
            tv_menu_paidyn.setText(paidyn_string);
        }

        return convertView;
    }
}
