package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

class BillListAdapter extends BaseAdapter {
    ArrayList<String> listViewItemList = new ArrayList<String>();

    public BillListAdapter(ArrayList<String> arr) {
        this.listViewItemList = arr;
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_listview_row, parent, false);
        }

        GlobalMemberValues.logWrite("billsplitlog", "value : " + listViewItemList.get(position) + "\n");
        String[] item_string = listViewItemList.get(position).split("-JJJ-");

        TextView textView1 = (TextView)convertView.findViewById(R.id.table_main_billprint_list_txt1);
        textView1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView textView2 = (TextView)convertView.findViewById(R.id.totalamounttv);
        textView2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 30 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.table_main_billprint_list_check_img);
        imageView.setVisibility(View.INVISIBLE);

        if (!GlobalMemberValues.isStrEmpty(item_string[0]) && TableSaleBillPrint.mSelectedBillNum.equals(item_string[0])) {
            textView1.setBackgroundColor(Color.parseColor("#2f3443"));
            textView1.setTextColor(Color.WHITE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        textView1.setText("Bill " + TableSaleBillSplit.getBillNumString(item_string[0]));
        textView2.setText("$ " + GlobalMemberValues.getCommaStringForDouble(item_string[1]));

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
