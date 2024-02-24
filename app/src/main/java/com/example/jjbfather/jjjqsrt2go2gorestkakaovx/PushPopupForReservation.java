package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PushPopupForReservation extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView pushForReservationCloseTextView;
    private Button pushForReservationDetailViewButton;

    private TextView pushReservationDateTextView, pushReservationEmployeeTextView;
    private TextView pushReservationCustomerTextView, pushReservationPhoneTextView;

    private String reservationDate, reservationEmployee, reservationCustomer;
    private String reservationPhone, reservtationCode;
    private String tailMsg;

    Intent mIntent;

    private static final String TAG = "PushPopupForReservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_push_popup_for_reservation);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 35;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 40;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 40;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.pushForReservationLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            reservationDate = mIntent.getStringExtra("reservationDate");
            reservationEmployee = mIntent.getStringExtra("reservationEmployee");
            reservationCustomer = mIntent.getStringExtra("reservationCustomer");
            reservationPhone = mIntent.getStringExtra("reservationPhone");
            reservtationCode = mIntent.getStringExtra("reservtationCode");
            tailMsg = mIntent.getStringExtra("tailMsg");
            Log.d(TAG, "reservationDate: " + reservationDate);
            Log.d(TAG, "reservationEmployee: " + reservationEmployee);
            Log.d(TAG, "reservationCustomer: " + reservationCustomer);
            Log.d(TAG, "reservationPhone: " + reservationPhone);
            Log.d(TAG, "reservtationCode: " + reservtationCode);
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
    }

    public void setContents() {
        switch (tailMsg) {
            case "CANCEL" : {
                parentLn.setBackgroundResource(R.drawable.aa_images_resvcanceled);
                break;
            }
        }

        pushForReservationCloseTextView = (TextView)findViewById(R.id.pushForReservationCloseTextView);
        pushForReservationCloseTextView.setOnClickListener(pushForReservationButtonClickListener);

        pushForReservationDetailViewButton = (Button)findViewById(R.id.pushForReservationDetailViewButton);
        pushForReservationDetailViewButton.setOnClickListener(pushForReservationButtonClickListener);

        pushReservationDateTextView = (TextView) findViewById(R.id.pushReservationDateTextView);
        pushReservationDateTextView.setText(reservationDate);

        pushReservationEmployeeTextView = (TextView) findViewById(R.id.pushReservationEmployeeTextView);
        pushReservationEmployeeTextView.setText(reservationEmployee);

        pushReservationCustomerTextView = (TextView) findViewById(R.id.pushReservationCustomerTextView);
        pushReservationCustomerTextView.setText(reservationCustomer);

        pushReservationPhoneTextView = (TextView) findViewById(R.id.pushReservationPhoneTextView);
        pushReservationPhoneTextView.setText(reservationPhone);
    }

    View.OnClickListener pushForReservationButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pushForReservationDetailViewButton : {
                    Intent intent;
                    if (GlobalMemberValues.isServiceRunningCheck(mContext, "com.example.jjbfather.jjjqsr")) {
                        if (MainActivity.isOpenApplication) {
                            intent = new Intent(mContext, ReservationWebPage.class);
                            Log.d(TAG, "여기실행1");
                        } else {
                            intent = new Intent(mContext, MainActivity.class);
                            Log.d(TAG, "여기실행2");
                        }
                    } else {
                        if (MainActivity.isOpenApplication) {
                            intent = new Intent(mContext, ReservationWebPage.class);
                            Log.d(TAG, "여기실행3");
                        } else {
                            intent = mContext.getPackageManager().getLaunchIntentForPackage("com.example.jjbfather.jjjqsr");
                            //intent = new Intent(this, MainActivity.class);
                            Log.d(TAG, "여기실행4");
                        }
                    }

                    intent.putExtra("pushIntentOpenType", "reservation");
                    intent.putExtra("pushReservtationCode", reservtationCode);

                    startActivity(intent);
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
                case R.id.pushForReservationCloseTextView : {
                    finish();

                    break;
                }
            }
        }
    };
}
