package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BayReservationViewerAdapter extends RecyclerView.Adapter<BayReservationViewerAdapter.ViewHolder>{
    private List<String[]> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    BayReservationViewerAdapter(Context context, List<String[]> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.bayreservation_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String time = mData.get(position)[0];
        String bay = mData.get(position)[1];
        String serviceitem = mData.get(position)[2];
        String customer = mData.get(position)[3];
        String phonenum = mData.get(position)[4];
        String holdCode = mData.get(position)[5];
        String downloadYN = mData.get(position)[6];
        String calledyn = mData.get(position)[7];
        String customercount = mData.get(position)[8];
        String reservationRegisterDate = mData.get(position)[9];
        String delyn = mData.get(position)[10];

        holder.timeTextView.setText(time);
        holder.regTimeTextView.setText(reservationRegisterDate);
        holder.bayTextView.setText(bay);
        holder.serviceItemTextView.setText(serviceitem);
        holder.customerTextView.setText(customer);
        holder.customerCountTextView.setText(customercount);
        holder.phoneNumTextView.setText(phonenum);
        holder.holdCode = holdCode;
        holder.downloadYN = downloadYN;
        holder.calledyn = calledyn;
        if (delyn.equals("Y")){
            holder.statusTextView.setText("Deleted");
        } else {
            holder.statusTextView.setText("Active");
        }


        GlobalMemberValues.logWrite("calledynlog", "calledyn : " + calledyn + "\n");
        GlobalMemberValues.logWrite("calledynlog", "holder.calledyn : " + holder.calledyn + "\n");

        if (delyn.equals("Y")){
            holder.restoreButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.GONE);
            holder.getReservationButton.setVisibility(View.GONE);
        } else {
            holder.restoreButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.getReservationButton.setVisibility(View.VISIBLE);
        }

        if (holder.calledyn.equals("Y")) {
            holder.deleteButton.setVisibility(View.GONE);
            holder.getReservationButton.setVisibility(View.GONE);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<String[]> data){
        this.mData = data;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView timeTextView;
        TextView regTimeTextView;
        TextView bayTextView;
        TextView serviceItemTextView;
        TextView customerTextView;
        TextView customerCountTextView;
        TextView phoneNumTextView;

        TextView statusTextView;

        TextView deleteButton;
        TextView getReservationButton;

        TextView restoreButton;

        String holdCode;

        String downloadYN = "";

        String calledyn = "";

        ViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.bayreservation_list_time);
            regTimeTextView = itemView.findViewById(R.id.bayreservation_list_register_time);
            bayTextView = itemView.findViewById(R.id.bayreservation_list_bay);
            serviceItemTextView = itemView.findViewById(R.id.bayreservation_list_serviceitem);
            customerTextView = itemView.findViewById(R.id.bayreservation_list_customer);
            customerCountTextView = itemView.findViewById(R.id.bayreservation_list_customer_count);
            phoneNumTextView = itemView.findViewById(R.id.bayreservation_list_phonenum);
            statusTextView = itemView.findViewById(R.id.bayreservation_list_status);

            deleteButton = itemView.findViewById(R.id.bayreservation_list_button_delete);
            getReservationButton = itemView.findViewById(R.id.bayreservation_list_button_get_reservation);
            restoreButton = itemView.findViewById(R.id.bayreservation_list_button_restore);

            deleteButton.setOnClickListener(this);
            getReservationButton.setOnClickListener(this);
            restoreButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getHoldCode(int id) {
        return mData.get(id)[5];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
