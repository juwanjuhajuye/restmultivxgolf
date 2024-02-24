package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class BillSplitMergeMenuListAdapter  extends BaseAdapter {

    ArrayList<String> listViewItemList = new ArrayList<String>();

    public BillSplitMergeMenuListAdapter(ArrayList<String> arr) {
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
            convertView = inflater.inflate(R.layout.tablesale_menu_list_row, parent, false);
        }
        GlobalMemberValues.logWrite("billsplitlog", "value : " + listViewItemList.get(position) + "\n");
        String[] item_string = listViewItemList.get(position).split("-JJJ-");

        String getCartIdx = item_string[0];

        ImageView tablesale_menulist_checkbox = (ImageView) convertView.findViewById(R.id.tablesale_menulist_checkbox);
        if (BillExecute.isInBillList_byCartIdx(getCartIdx)) {
            tablesale_menulist_checkbox.setVisibility(View.INVISIBLE);
        }

        final RelativeLayout relativeLayout = convertView.findViewById(R.id.tablesale_menulist_view);
        final View finalConvertView = convertView;
        if (!BillExecute.isInBillList_byCartIdx(getCartIdx)) {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp = getCartIdx + "-JJJ-" + item_string[2];
                    TableSaleMain.mSelectedIdxArrListInCart.remove(temp);

                    if (relativeLayout.isSelected()){
                        finalConvertView.setSelected(false);
                        TableSaleMain.mSelectedIdxArrListInCart.remove(temp);
                    } else {
                        finalConvertView.setSelected(true);
                        TableSaleMain.mSelectedIdxArrListInCart.add(temp);
                    }
                }
            });
        }


        TextView itemNameTv = (TextView) convertView.findViewById(R.id.tablesale_menulist_menuname);
        itemNameTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        TextView itemPriceTv = (TextView) convertView.findViewById(R.id.tablesale_menulist_price);
        itemPriceTv.setTextSize(GlobalMemberValues.globalAddFontSize()
                + 22 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());


        itemNameTv.setText(item_string[1]);
        itemPriceTv.setText(item_string[2]);
//        billNumTv.setText(TableSaleBillSplit.getBillNumString(item_string[3]));

//        LinearLayout optionTxtLn = (LinearLayout) convertView.findViewById(R.id.optionTxtLn);
//        optionTxtLn.setVisibility(View.GONE);

//        String optionTxt = "";
//        if (!GlobalMemberValues.isStrEmpty(item_string[4])) {
//            optionTxtLn.setVisibility(View.VISIBLE);
//            optionTxt = "\n" + item_string[4];
//            TextView optionTxtTv = (TextView) convertView.findViewById(R.id.optionTxtTv);
//            optionTxtTv.setTextSize(GlobalMemberValues.globalAddFontSize()
//                    + 20 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
//            optionTxtTv.setText(optionTxt);
//        }

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

