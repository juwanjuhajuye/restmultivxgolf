package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

public class PushPopupForTablePay extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn, pushTablePay_confirmBtn;


    private TextView pushNewSideMenuTableName, pushNewSideMenuSaleCode, pushNewSideMenuOrderDetail, bottominfotv;

    private String tablepay_tablename, tablepay_holdcode, tablepay_totalpay, tablepay_salescode, tablepay_tableidx;

    private String tailMsg;

    Intent mIntent;
    // alram
    public final static int REPEAT_DELAY = 2000;
    public Handler popup_handler;

    private static final String TAG = "PushCurbNewSideMenu";
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_push_popup_for_tablepay);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 40;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 50;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
        }
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.pushForTablePayLn);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값

            tablepay_salescode = mIntent.getStringExtra("tablepay_salescode");
            tablepay_holdcode = mIntent.getStringExtra("tablepay_holdcode");
            tablepay_totalpay = mIntent.getStringExtra("tablepay_totalpay");
            tablepay_tablename = mIntent.getStringExtra("tablepay_tablename");
            tablepay_tableidx = mIntent.getStringExtra("tablepay_tableidx");

            Log.d(TAG, "tablepay_salescode: " + tablepay_salescode);
            Log.d(TAG, "tablepay_holdcode: " + tablepay_holdcode);
            Log.d(TAG, "tablepay_totalpay: " + tablepay_totalpay);
            Log.d(TAG, "tablepay_tablename: " + tablepay_tableidx);
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
        //updateCheckDatabase();

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
        closeBtn.setOnClickListener(pushForTablePayClickListener);
        closeBtn.setText("");
        closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);

        pushTablePay_confirmBtn = (Button)findViewById(R.id.pushTablePay_confirmBtn);
        pushTablePay_confirmBtn.setOnClickListener(pushForTablePayClickListener);

        pushNewSideMenuTableName = (TextView)findViewById(R.id.pushTablePay_tablename);
        pushNewSideMenuTableName.setText(tablepay_tablename);

        pushNewSideMenuSaleCode = (TextView)findViewById(R.id.pushTablePay_salecode);
        pushNewSideMenuSaleCode.setText(tablepay_salescode);

        pushNewSideMenuOrderDetail = (TextView)findViewById(R.id.pushTablePay_totalpay);
        pushNewSideMenuOrderDetail.setText("$" + GlobalMemberValues.getCommaStringForDouble(tablepay_totalpay));

        bottominfotv = (TextView)findViewById(R.id.bottominfotv);
        bottominfotv.setText(tablepay_tablename + "'s customer has completed the payment by TABLE PAY");
    }

    public void startNotificationSound(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    View.OnClickListener pushForTablePayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeButton : {
                    initTablesClose();
                    break;
                }
                case R.id.pushTablePay_confirmBtn : {
                    openSaleHistoryWeb();
                    break;
                }
            }
        }
    };

    private void openSaleHistoryWeb() {
        new AlertDialog.Builder(mContext)
                .setTitle("RETURN")
                .setMessage("Would you like to view the details?")
                //.setIcon(R.drawable.ic_launcher)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent adminPasswordIntent = new Intent(mContext.getApplicationContext(), SaleHistory_web.class);
                                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                adminPasswordIntent.putExtra("pushSalesCode", tablepay_salescode);
                                // -------------------------------------------------------------------------------------
                                mActivity.startActivity(adminPasswordIntent);
                                if (GlobalMemberValues.isUseFadeInOut()) {
                                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                }

                                initTablesClose();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        initTablesClose();
                    }
                })
                .show();
    }

    private void initTablesClose() {
        popup_handler.removeMessages(0);
        TableSaleMain.setClearCartOfTableByHoldcode(tablepay_holdcode);

        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popup_handler.removeMessages(0);
    }

}