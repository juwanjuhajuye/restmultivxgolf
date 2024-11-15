package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.List;

public class Table_list_popup_adapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> items;
    private String tableidx;

    public Table_list_popup_adapter(Context context, List<String> items, String tableidx) {
        super(context, R.layout.table_popup_list_item, items);
        this.context = context;
        this.items = items;
        this.tableidx = tableidx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.table_popup_list_item, parent, false);
        }

        TextView textView = view.findViewById(R.id.text_view);
        textView.setText(items.get(position));


        if ("QR Code Order On".equals(items.get(position))){
            if (GlobalMemberValues.isTableOrderQRCodeOn(tableidx)){
                textView.setTextColor(Color.BLUE); //
            } else {
                textView.setTextColor(Color.BLACK); //
            }
        }

        if ("QR Code Order Off".equals(items.get(position))){
            if (GlobalMemberValues.isTableOrderQRCodeOn(tableidx)){
                textView.setTextColor(Color.BLACK); //
            } else {
                textView.setTextColor(Color.BLUE); //
            }
        }


        return view;
    }
}