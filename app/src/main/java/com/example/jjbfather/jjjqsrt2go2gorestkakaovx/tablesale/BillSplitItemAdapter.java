package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class BillSplitItemAdapter extends BaseAdapter {

    ArrayList<String> listViewItemList = new ArrayList<String>();

    public BillSplitItemAdapter(ArrayList<String> arr) {
        this.listViewItemList = arr;
    }
    @Override
    public int getCount() {
        String[] item_string = listViewItemList.get(listViewItemList.size()-1).split("-JJJ-");
        if (item_string[1].equals(GlobalMemberValues.mCommonGratuityName)){
            return listViewItemList.size()-1;
        } else {
            return listViewItemList.size();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_main_split_item_row, parent, false);
        }
        GlobalMemberValues.logWrite("billsplitlog", "value : " + listViewItemList.get(position) + "\n");
        String[] item_string = listViewItemList.get(position).split("-JJJ-");

        final RelativeLayout relativeLayout = convertView.findViewById(R.id.table_main_split_item_view);
        final View finalConvertView = convertView;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableSaleMain.mSelectedIdxArrListInCart.remove(item_string[0]);

                if (relativeLayout.isSelected()){
                    finalConvertView.setSelected(false);
                    TableSaleMain.mSelectedIdxArrListInCart.remove(item_string[0]);
                } else {
                    finalConvertView.setSelected(true);
                    TableSaleMain.mSelectedIdxArrListInCart.add(item_string[0]);
                }
            }
        });

        TextView itemNameTv = (TextView) convertView.findViewById(R.id.table_main_split_item_name);
        itemNameTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView itemQtyTv = (TextView) convertView.findViewById(R.id.table_main_split_item_qty);
        itemQtyTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView billNumTv = (TextView) convertView.findViewById(R.id.table_main_split_item_index);
        billNumTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView item_server_name = (TextView) convertView.findViewById(R.id.table_main_split_item_server);
        item_server_name.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        itemNameTv.setText(item_string[1]);
        itemQtyTv.setText(item_string[2]);
        billNumTv.setText(TableSaleBillSplit.getBillNumString(item_string[3]));
        item_server_name.setText(item_string[5]);

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

        if (item_string[1].equals(GlobalMemberValues.mCommonGratuityName)){
            convertView.setVisibility(View.GONE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
}
