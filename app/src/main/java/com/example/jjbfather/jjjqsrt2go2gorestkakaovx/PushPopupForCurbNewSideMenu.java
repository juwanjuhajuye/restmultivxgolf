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

import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class PushPopupForCurbNewSideMenu extends Activity {
    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private Button closeBtn;
    private Button pushForCurbsideDetailViewButton;


    private TextView pushNewSideMenuTableName, pushNewSideMenuSaleCode, pushNewSideMenuCallTime, pushNewSideMenuOrderDetail;

    private String curbsidemenu_tablename,curbsidemenu_salecode, curbsidemenu_ordernum, curbsidemenu_ordertime, curbsidemenu_orderDetail, curbsidemenu_tableidx, sideMenu_index;

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
        setContentView(R.layout.activity_push_popup_for_curbsidemenu);
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

            sideMenu_index = mIntent.getStringExtra("sideMenu_index");
            curbsidemenu_salecode = mIntent.getStringExtra("sideMenu_salecode");
            curbsidemenu_tableidx = mIntent.getStringExtra("sideMenu_tableidx");
            curbsidemenu_tablename = mIntent.getStringExtra("sideMenu_tablename");
            curbsidemenu_ordernum = mIntent.getStringExtra("sideMenu_ordernum");
            curbsidemenu_ordertime = mIntent.getStringExtra("sideMenu_ordertime");
            curbsidemenu_orderDetail = mIntent.getStringExtra("sideMenu_orderdetail");

            Log.d(TAG, "sideMenu_index: " + sideMenu_index);
            Log.d(TAG, "curbsidemenu_salecode: " + curbsidemenu_salecode);
            Log.d(TAG, "curbsidemenu_tableidx: " + curbsidemenu_tableidx);
            Log.d(TAG, "curbsidemenu_tablename: " + curbsidemenu_tablename);
            Log.d(TAG, "curbsidemenu_ordernum: " + curbsidemenu_ordernum);
            Log.d(TAG, "curbsidemenu_ordertime: " + curbsidemenu_ordertime);
            Log.d(TAG, "curbsidemenu_orderDetail: " + curbsidemenu_orderDetail);
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
        pushForCurbsideDetailViewButton = (Button)findViewById(R.id.pushCurbsidemenu_DetailViewButton);
        pushForCurbsideDetailViewButton.setOnClickListener(pushForCursideButtonClickListener);

        pushNewSideMenuTableName = (TextView)findViewById(R.id.pushCurbsidemenu_tablename);
        pushNewSideMenuTableName.setText(curbsidemenu_tablename);

        pushNewSideMenuSaleCode = (TextView)findViewById(R.id.pushCurbsidemenu_salecode);
        pushNewSideMenuSaleCode.setText(curbsidemenu_ordernum);

        pushNewSideMenuCallTime = (TextView)findViewById(R.id.pushCurbsidemenu_ordertime);
        pushNewSideMenuCallTime.setText(curbsidemenu_ordertime);

        pushNewSideMenuOrderDetail = (TextView)findViewById(R.id.pushCurbsidemenu_orderdetail);
        pushNewSideMenuOrderDetail.setText(curbsidemenu_orderDetail);
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
        intent.putExtra("pushSalesCode", curbsidemenu_salecode);
//        intent.putExtra("pushDeliveryTakeaway", webOrdersDeliveryTakeaway);
        startActivity(intent);

        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
        }

    }

    public void updateCheckDatabase(){
        if (GlobalMemberValues.getCurbsideNewSideMenuCount_sidemenuidx(sideMenu_index) == 0) {
            String returnResult = "";
            String strQuery = "";
            // salon_sales_web_push_realtime 테이블 저장
            Vector<String> strQueryVec = new Vector<String>();

            //먼저 같은 SalesCode 로 저장된 값을 삭제
//                strQuery = "delete from salon_sales_web_push_realtime where salesCode = '"+ curside_salecode + "' ";
//                strQueryVec.addElement(strQuery);

            strQuery = "insert into salon_store_curbsidenewsidemenu (" +
                    " salesCode, tableidx, tablename, ordertime, ordernum, orderdetail, sidemenuidx" +
                    " ) values ( " +
                    " '" + curbsidemenu_salecode + "', " +
                    " '" + curbsidemenu_tableidx + "', " +
                    " '" + curbsidemenu_tablename + "', " +
                    " '" + curbsidemenu_ordertime + "', " +
                    " '" + curbsidemenu_ordernum + "', " +
                    " '" + curbsidemenu_orderDetail + "', " +
                    " '" + sideMenu_index + "' " +
                    " ) ";

            strQueryVec.addElement(strQuery);
            for (String tempQuery : strQueryVec) {
                GlobalMemberValues.logWrite("NewSideMenuOpen", "query : " + tempQuery + "\n");
            }
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strQueryVec);
            GlobalMemberValues.logWrite("NewSideMenuOpen", "DB 입력결과 : " + returnResult + "\n");
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
                case R.id.pushCurbsidemenu_DetailViewButton : {

                    new AlertDialog.Builder(mContext)
                            .setTitle("Arrival confirm")
                            .setMessage("Arrival confirm?\n[Sale Group# : " + sideMenu_index + "]")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String curbsideConfirmString = "";
                                            if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                                                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                                                    API_sidemenuorder_confirm api_weborder_customer_arrive_confirm = new API_sidemenuorder_confirm(sideMenu_index);
                                                    try {
                                                        curbsideConfirmString = api_weborder_customer_arrive_confirm.execute(null, null, null).get().toString();
                                                        if (curbsideConfirmString.equals("00")){
                                                            // 성공
                                                            GlobalMemberValues.displayDialog(mContext, "Arrival confirm", "confirm success", "Close");
                                                        } else {
                                                            GlobalMemberValues.displayDialog(mContext, "Arrival confirm", "confirm fail", "Close");
                                                        }
                                                    } catch (ExecutionException e) {
                                                        e.printStackTrace();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (api_weborder_customer_arrive_confirm.mFlag) {
                                                        curbsideConfirmString = api_weborder_customer_arrive_confirm.mReturnValue;

                                                    }
//                                                            try {
//                                                                Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
//
//                                                            } catch (InterruptedException e) {
//                                                                GlobalMemberValues.logWrite("newCursidesrt confirm error", "Thread Error : " + e.getMessage() + "\n");
//                                                                //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
//                                                            }
                                                }
                                            }
                                            popup_handler.removeMessages(0);
                                            finish();
                                            if (GlobalMemberValues.isUseFadeInOut()) {
                                                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                            }
                                            GlobalMemberValues.logWrite("newCursidesrt confirm error", curbsideConfirmString + "\n");
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    popup_handler.removeMessages(0);
                                    finish();
                                    if (GlobalMemberValues.isUseFadeInOut()) {
                                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                    }
                                }
                            })
                            .show();

                    // 프린팅


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