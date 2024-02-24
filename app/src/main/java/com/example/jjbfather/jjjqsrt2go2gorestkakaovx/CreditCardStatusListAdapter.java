package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;

public class CreditCardStatusListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<String> mArSrc;
    int layout;

    public CreditCardStatusListAdapter(Context context,  ArrayList<String> aarSrc) {
        this.mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArSrc = aarSrc;
    }

    @Override
    public int getCount() {
        return mArSrc.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.credit_card_status_list_row, parent, false);
        }
        String[] tempStrings = mArSrc.get(position).split("-JJJ-");

        TextView txt_date = (TextView)convertView.findViewById(R.id.date_text_credit_card_status_row);
        txt_date.setText(tempStrings[0]);
        txt_date.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView txt_itemname = (TextView)convertView.findViewById(R.id.itemname_credit_card_status_row);
        txt_itemname.setText(tempStrings[1]);
        txt_itemname.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView txt_amount = (TextView)convertView.findViewById(R.id.amount_credit_card_status_row);
        txt_amount.setText(tempStrings[2]);
        txt_amount.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView txt_result = (TextView)convertView.findViewById(R.id.result_credit_card_status_row);
        if (tempStrings[3] != null){
            if (tempStrings[3].equals("PENDDING")){
                String temp_str = tempStrings[3].replace("PENDDING","PENDING");
                txt_result.setText(temp_str);
            } else {
                txt_result.setText(tempStrings[3]);
            }
        }
        txt_result.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        ImageButton imageButton_del = convertView.findViewById(R.id.del_credit_card_status_row);
        imageButton_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CreditCardStatusActivity.mContext)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete this sale status?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String tempResultDb = Payment_CreditCard_Proc_Exec.deleteCardTryList(tempStrings[4]);
                                        if (tempResultDb.equals("Y")) {
                                            CreditCardStatusActivity.searchData();
                                        }
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
        });

        ImageButton imageButton_force = convertView.findViewById(R.id.blank_credit_card_status_row);
        imageButton_force.setVisibility(View.VISIBLE);
        if (tempStrings[5].equals("Y")) {
            imageButton_force.setVisibility(View.VISIBLE);
            imageButton_force.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(CreditCardStatusActivity.mContext)
                            .setTitle("Force Sale")
                            .setMessage("Do you want to force processing?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            CreditCardStatusActivity.setForceSale(tempStrings[4], tempStrings[2]);
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                            .show();
                }
            });
        } else {
            imageButton_force.setVisibility(View.GONE);
        }

        return convertView;
    }
}
