package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.util.ArrayList;

public class CreditCardStatusActivity extends Activity {

    static Activity mActivity;
    static Context mContext;
    Intent mIntent;
    LinearLayout parentLn;
    static ListView listView;
    static CreditCardStatusListAdapter creditCardStatusListAdapter;
    static CreditCardStatusPopup creditCardStatusPopup;

    static TextView credit_card_status_list_StartDateEditText;
    static TextView credit_card_status_list_EndDateEditText;
    TextView mSelectedDateTextView;
    ImageButton creditcardstatus_list_date_search, creditcardstatus_close;
    static CheckBox credit_card_status_pending;
    static CheckBox credit_card_status_success;
    static CheckBox credit_card_status_decline;

    static ArrayList<String> stringArrayList;

    public static int mListPageno = 1;

    public String mGetSearchWord = "";

    public static String temp_start_date = "";
    public static String temp_end_date = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_credit_card_status);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;


        parentLn = (LinearLayout)findViewById(R.id.creditcardstatusparentln);



        mIntent = getIntent();
        if (mIntent != null) {

        } else {
            finish();
        }

        setContents();

    }

    private void setContents(){
        creditcardstatus_list_date_search = findViewById(R.id.creditcardstatus_list_date_search);
        creditcardstatus_list_date_search.setOnClickListener(onClickListener);

        credit_card_status_pending = findViewById(R.id.credit_card_status_pending);
        credit_card_status_pending.setButtonDrawable(R.drawable.ab_check_credit_card_status);
        credit_card_status_success = findViewById(R.id.credit_card_status_success);
        credit_card_status_success.setButtonDrawable(R.drawable.ab_check_credit_card_status);
        credit_card_status_decline = findViewById(R.id.credit_card_status_decline);
        credit_card_status_decline.setButtonDrawable(R.drawable.ab_check_credit_card_status);

        credit_card_status_list_StartDateEditText = findViewById(R.id.credit_card_status_list_StartDateEditText);
        credit_card_status_list_StartDateEditText.setOnClickListener(onClickListener);
        credit_card_status_list_EndDateEditText = findViewById(R.id.credit_card_status_list_EndDateEditText);
        credit_card_status_list_EndDateEditText.setOnClickListener(onClickListener);

        creditcardstatus_close = findViewById(R.id.creditcardstatus_close);
        creditcardstatus_close.setOnClickListener(onClickListener);

        listView = findViewById(R.id.creditcardstatus_list);

        String searchStartDate = credit_card_status_list_StartDateEditText.getText().toString();
        String searchEndDate = credit_card_status_list_EndDateEditText.getText().toString();
        if (GlobalMemberValues.isStrEmpty(searchStartDate)) {
            searchStartDate = GlobalMemberValues.STR_NOW_DATE;
            credit_card_status_list_StartDateEditText.setText(searchStartDate);
        }
        if (GlobalMemberValues.isStrEmpty(searchEndDate)) {
            searchEndDate = GlobalMemberValues.STR_NOW_DATE;
            credit_card_status_list_EndDateEditText.setText(searchEndDate);
        }

        if (!GlobalMemberValues.isStrEmpty(mGetSearchWord)) {
            searchStartDate = "01-01-2010";
            searchEndDate = "12-31-2030";

            credit_card_status_list_StartDateEditText.setText(searchStartDate);
            credit_card_status_list_EndDateEditText.setText(searchEndDate);
        }

        if (temp_start_date != GlobalMemberValues.STR_NOW_DATE && temp_start_date != ""){
            searchStartDate = temp_start_date;
            credit_card_status_list_StartDateEditText.setText(searchStartDate);
        }
        if (temp_end_date != GlobalMemberValues.STR_NOW_DATE && temp_end_date != ""){
            searchEndDate = temp_end_date;
            credit_card_status_list_EndDateEditText.setText(searchEndDate);
        }
        setSaleHistoryList(searchStartDate,searchEndDate, "P,S,D");

    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(this, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }
    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.creditcardstatus_list_date_search : {
                    searchData();
                    break;
                }
                case R.id.credit_card_status_list_StartDateEditText : {
                    String tempClockInOutSearchDay = credit_card_status_list_StartDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = credit_card_status_list_StartDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
                case R.id.credit_card_status_list_EndDateEditText : {
                    String tempClockInOutSearchDay = credit_card_status_list_EndDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = credit_card_status_list_EndDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
                case R.id.creditcardstatus_close : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };

    public static void searchData() {
        temp_start_date = "";
        temp_end_date = "";

        String kind_string = "";
        if (credit_card_status_pending.isChecked()){
            kind_string = "P";
        }
        if (credit_card_status_success.isChecked()){
            if (GlobalMemberValues.isStrEmpty(kind_string)){
                kind_string = "S";
            } else {
                kind_string = kind_string + ",S";
            }
        }
        if (credit_card_status_decline.isChecked()){
            if (GlobalMemberValues.isStrEmpty(kind_string)){
                kind_string = "D";
            } else {
                kind_string = kind_string + ",D";
            }
        }

        String tempStartDate = credit_card_status_list_StartDateEditText.getText().toString();
        String tempEndDate = credit_card_status_list_EndDateEditText.getText().toString();

        setSaleHistoryList(tempStartDate, tempEndDate, kind_string);
    }
    private static void setSaleHistoryList(String paramStartDate, String paramEndDate, String kind_string) {
        temp_start_date = paramStartDate;
        temp_end_date = paramEndDate;

        listView.setAdapter(null);      // 초기화
        stringArrayList = Payment_CreditCard_Proc_Exec.getProcList(paramStartDate,paramEndDate,kind_string);
        creditCardStatusListAdapter = new CreditCardStatusListAdapter(mContext,stringArrayList);
        listView.setDividerHeight(2);
        listView.setAdapter(creditCardStatusListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = stringArrayList.get(position);
                creditCardStatusPopup = new CreditCardStatusPopup(mContext, temp, information_close_listener);
                creditCardStatusPopup.show();
            }
        });
    }

    public static void setForceSale(String paramIdx, String paramAmount) {
        if (Payment.paymentOfflineSwitch != null) {
            Payment.paymentOfflineSwitch.setChecked(false);
            try {
                if (Payment.paymentCardEditText != null) {
                    Payment.paymentCardEditText.setText(paramAmount);
                }
                Payment.setPaymentPrice();
                Payment.setFinishProcess();

                String tempResult = Payment_CreditCard_Proc_Exec.updateCardTryList(paramIdx);
                if (tempResult.equals("Y")) {
                    if (mActivity != null){
                        mActivity.finish();
                    }
                    if (Payment.paymentChangeEditText != null){
                        String tempChange = Payment.paymentChangeEditText.getText().toString();
                        if (GlobalMemberValues.isStrEmpty(tempChange)
                                || GlobalMemberValues.getDoubleAtString(tempChange) < 0) {
                            PaymentCreditCard.setFinishActivity();
                        } else {
                            PaymentCreditCard.setFinishActivity();
                        }
                    } else {
                        PaymentCreditCard.setFinishActivity();
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //PaymentCreditCard.finishCreditCardProcessing();
        } else {
            GlobalMemberValues.displayDialog(mContext, "Waraning", "This is only possible when payment is in progress", "Close");
        }
    }

    static View.OnClickListener information_close_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            creditCardStatusPopup.cancel();
        }
    };

}
