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

public class SaleHistoryListWebAdapter extends BaseAdapter {
    private int itemsize = 0;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<String> listViewItemList;

    // ListViewAdapter의 생성자
    public SaleHistoryListWebAdapter(ArrayList<String> arrayList) {
        this.listViewItemList = arrayList;
    }

    @Override
    public void notifyDataSetChanged() {
        itemsize = listViewItemList.size();

        super.notifyDataSetChanged();
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return itemsize;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.salehistory_list_item_web, parent, false);
        }
        String[] item_string = listViewItemList.get(position).split("-JJJ-");

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView text1 = (TextView) convertView.findViewById(R.id.salehistory_list_text1);
        text1.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text2 = (TextView) convertView.findViewById(R.id.salehistory_list_text2);
        text2.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text3 = (TextView) convertView.findViewById(R.id.salehistory_list_text3);
        text3.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text4 = (TextView) convertView.findViewById(R.id.salehistory_list_text4);
        text4.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text5 = (TextView) convertView.findViewById(R.id.salehistory_list_text5);
        text5.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView text6 = (TextView) convertView.findViewById(R.id.salehistory_list_text6);
        text6.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // jihun 063020
        TextView salehistory_list_text_customer_arrive = (TextView)convertView.findViewById(R.id.salehistory_list_text_customer_arrive);
        salehistory_list_text_customer_arrive.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // onlineordertype 210127
        TextView salehistory_list_onlineordertype = (TextView) convertView.findViewById(R.id.salehistory_list_onlineordertype);
        text6.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // status
        TextView salehistory_list_item_status = (TextView) convertView.findViewById(R.id.salehistory_list_item_status);
        salehistory_list_item_status.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 14 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());



        String tempSaleDate = item_string[4];
        if (GlobalMemberValues.getIntAtString(item_string[7]) > 0) {
            String statusStr = "void";
            if (GlobalMemberValues.getIntAtString(item_string[7]) > 2) {
                statusStr = "return";
            }
            tempSaleDate = tempSaleDate + "<font color=\"#f82823\"> ["+ statusStr +"]</font>";
        }

        String str_ordertype = "";
        switch (item_string[11]){
            case "W" :
                str_ordertype = "WEB";
                salehistory_list_onlineordertype.setTextColor(Color.parseColor("#1062dc"));
                break;
            case "M" :
                str_ordertype = "MOBLIE";
                salehistory_list_onlineordertype.setTextColor(Color.parseColor("#ff322d"));
                break;
            case "A" :
                str_ordertype = "APP";
                salehistory_list_onlineordertype.setTextColor(Color.parseColor("#67ac09"));
                break;
            case "K" :
                str_ordertype = "KIOSK";
                salehistory_list_onlineordertype.setTextColor(Color.parseColor("#c124aa"));
                break;
            case "T" :
                str_ordertype = "OTTER";

                // 10112023
                if (item_string.length > 11) {
                    str_ordertype = item_string[12];
                }

                salehistory_list_onlineordertype.setTextColor(Color.parseColor("#a96d25"));
                break;
            default:
                break;
        }

        // 아이템 내 각 위젯에 데이터 반영
        text1.setText(item_string[0]);
        if (item_string[12].equals("Y")) {
            text2.setText(item_string[1] + " (Curbside)");  // pick up type
        } else {
            text2.setText(item_string[1]);  // pick up type
        }
        text3.setText(item_string[2]);
        text4.setText(GlobalMemberValues.getCommaStringForDouble(item_string[3]));
        text5.setText(Html.fromHtml(tempSaleDate));
        text6.setText(item_string[5]);
        salehistory_list_onlineordertype.setText(str_ordertype);

        if (item_string[10].equals("Y")){
            salehistory_list_text_customer_arrive.setText("");
        } else if (item_string[10].equals("N")){
            salehistory_list_text_customer_arrive.setText("Arrive not confirm");
        } else {
            salehistory_list_text_customer_arrive.setText("");
        }

        // Status
        String tempStr = item_string[1];
        if (GlobalMemberValues.isStrEmpty(tempStr)) {
            tempStr = "TO GO";
        }

        GlobalMemberValues.logWrite("webordertestlog", "type : " + tempStr + "\n");
        GlobalMemberValues.logWrite("webordertestlog", "item_string[9] : " + item_string[9] + "\n");

        salehistory_list_item_status.setText("");

        if (item_string[8].equals("2") || item_string[9].equals("2")){
            salehistory_list_item_status.setText("DONE");
        }

        // 11072023
        if (item_string[13].equals("Y")) {
            salehistory_list_item_status.setText("Done");
        }

        if (!tempStr.equals("DELIVERY")) {
            if (item_string[9].equals("2")) {
                salehistory_list_item_status.setText("Sent Notification");

                if (item_string[13].equals("Y")) {
                    salehistory_list_item_status.setText("Done");
                }
            }
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
