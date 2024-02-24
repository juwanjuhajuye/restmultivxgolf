package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.Vector;

public class PushPopupForCustomerArrive extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;
    private Button pushForCurbsideDetailViewButton;

    private TextView pushCurbsideCallTimeTextView, pushCurbsidePhonenumTextView, pushCurbsideOrdernumTextView, pushCurbsideSalecodeTextView;

    private TextView pushCurbsideCarInfoTextView1, pushCurbsideCarInfoTextView2, pushCurbsideCarInfoTextView3;
    private View pushCurbsideCarInfoTextView2_color;
    private TextView pushCurbsideCarInfoTextView_parkingspace;

    private String curbside_carinfo, curbside_calltime, curbside_cus_phone, curbside_ordernum, curbside_salecode;

    private String tailMsg;

    Intent mIntent;
    // alram
    public final static int REPEAT_DELAY = 2000;
    public Handler popup_handler;

    private static final String TAG = "PushPopupForCurside";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_push_popup_for_customer_arrive);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 40;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.pushForCursideLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값


            curbside_carinfo = mIntent.getStringExtra("curbside_carinfo");
            curbside_calltime = mIntent.getStringExtra("curbside_calltime");
            curbside_cus_phone = mIntent.getStringExtra("curbside_cus_phone");
            curbside_salecode = mIntent.getStringExtra("curbside_salecode");
            curbside_ordernum = mIntent.getStringExtra("curbside_ordernum");


            Log.d(TAG, "curbside_carinfo: " + curbside_carinfo);
            Log.d(TAG, "curbside_calltime: " + curbside_calltime);
            Log.d(TAG, "curbside_cus_phone: " + curbside_cus_phone);
            Log.d(TAG, "curbside_salecode: " + curbside_salecode);
            Log.d(TAG, "curbside_ordernum: " + curbside_ordernum);
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }



        setContents();
//        startNotificationSound();
        updateCheckDatabase();

        popup_handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                // 할일들을 여기에 등록
                startNotificationSound();
                this.sendEmptyMessageDelayed(0, REPEAT_DELAY);        // REPEAT_DELAY 간격으로 계속해서 반복하게 만들어준다
            }

        };
        popup_handler.sendEmptyMessage(0);
    }

    public void setContents() {

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(pushForCursideButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        pushForCurbsideDetailViewButton = (Button)findViewById(R.id.pushForCursideDetailViewButton);
        pushForCurbsideDetailViewButton.setOnClickListener(pushForCursideButtonClickListener);

        if (!curbside_carinfo.isEmpty()){

            String[] temp_carinfo = curbside_carinfo.split("-TAJ-");

            pushCurbsideCarInfoTextView1 = (TextView)findViewById(R.id.pushCurbsideCarInfoTextView1);
            if (temp_carinfo.length >= 1){
                pushCurbsideCarInfoTextView1.setText(temp_carinfo[0]);
            }

            pushCurbsideCarInfoTextView2_color = (View) findViewById(R.id.pushCurbsideCarInfoTextView2_color);
            if (temp_carinfo.length >= 2){
                pushCurbsideCarInfoTextView2_color.setBackgroundColor(Color.parseColor(temp_carinfo[1]));
            }

            pushCurbsideCarInfoTextView3 = (TextView)findViewById(R.id.pushCurbsideCarInfoTextView3);
            if (temp_carinfo.length >= 3){
                pushCurbsideCarInfoTextView3.setText(temp_carinfo[2]);
            }

            pushCurbsideCarInfoTextView_parkingspace = (TextView)findViewById(R.id.pushCurbsideCarInfoTextView_parkingspace);
            if (temp_carinfo.length >= 4){
                pushCurbsideCarInfoTextView_parkingspace.setText(temp_carinfo[3]);
            }

        }




        pushCurbsideCallTimeTextView = (TextView) findViewById(R.id.pushCurbsideCallTimeTextView);
        pushCurbsideCallTimeTextView.setText(curbside_calltime);

        pushCurbsidePhonenumTextView = (TextView) findViewById(R.id.pushCurbsidePhonenumTextView);
        pushCurbsidePhonenumTextView.setText(curbside_cus_phone);

        pushCurbsideOrdernumTextView = (TextView) findViewById(R.id.pushCurbsideOrdernumTextView);
        pushCurbsideOrdernumTextView.setText(curbside_ordernum);

        pushCurbsideSalecodeTextView = (TextView) findViewById(R.id.pushCurbsideSalecodeTextView);
        pushCurbsideSalecodeTextView.setText(curbside_salecode);

    }

    public void startNotificationSound(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    public void openSaleHistoryWeb() {
        Intent intent;
        if (GlobalMemberValues.isServiceRunningCheck(mContext, "com.example.jjbfather.jjjqsr")) {
            if (MainActivity.isOpenApplication) {
                GlobalMemberValues.shweb_fromCommand = "Y";
                intent = new Intent(mContext, SaleHistory_web.class);
                Log.d(TAG, "여기실행1");
            } else {
                intent = new Intent(mContext, MainActivity.class);
                Log.d(TAG, "여기실행2");
            }
        } else {
            if (MainActivity.isOpenApplication) {
                GlobalMemberValues.shweb_fromCommand = "Y";
                intent = new Intent(mContext, SaleHistory_web.class);
                Log.d(TAG, "여기실행3");
            } else {
                intent = mContext.getPackageManager().getLaunchIntentForPackage("com.example.jjbfather.jjjqsr");
                //intent = new Intent(this, MainActivity.class);
                Log.d(TAG, "여기실행4");
            }
        }
//        SaleHistory_web.mDeliveryTakeawayValueFromOutActivity = webOrdersDeliveryTakeaway;
        intent.putExtra("pushIntentOpenType", "pushweborders");
        intent.putExtra("pushSalesCode", curbside_salecode);
//        intent.putExtra("pushDeliveryTakeaway", webOrdersDeliveryTakeaway);
        startActivity(intent);

        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }

    }

    public void updateCheckDatabase(){
        if (GlobalMemberValues.getCurbsidePickupCount(curbside_salecode) == 0) {
            String returnResult = "";
            String strQuery = "";
            // salon_sales_web_push_realtime 테이블 저장
            Vector<String> strQueryVec = new Vector<String>();

            //먼저 같은 SalesCode 로 저장된 값을 삭제
//                strQuery = "delete from salon_sales_web_push_realtime where salesCode = '"+ curside_salecode + "' ";
//                strQueryVec.addElement(strQuery);

            strQuery = "insert into salon_store_curbsidepickup (" +
                    " carinfo, calltime, phonenum, salesCode, ordernum" +
                    " ) values ( " +
                    " '" + curbside_carinfo + "', " +
                    " '" + curbside_calltime + "', " +
                    " '" + curbside_cus_phone + "', " +
                    " '" + curbside_salecode + "', " +
                    " '" + curbside_ordernum + "' " +
                    " ) ";

            strQueryVec.addElement(strQuery);
            for (String tempQuery : strQueryVec) {
                GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "query : " + tempQuery + "\n");
            }
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
            GlobalMemberValues.logWrite("NewWebOrderCheckOpen", "DB 입력결과 : " + returnResult + "\n");
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else { // 정상처리일 경우 팝업창 오픈

            }
        }
    }

    View.OnClickListener pushForCursideButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pushForCursideDetailViewButton : {
                    popup_handler.removeMessages(0);
                    openSaleHistoryWeb();
                    break;
                }
                case R.id.closeButton : {
                    popup_handler.removeMessages(0);
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }
            }
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popup_handler.removeMessages(0);
    }

}
