package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class PaymentSelectOtherPay extends Activity {
    final String TAG = "PaymentSelectOtherPayLog";

    Activity mActivity;
    Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private ScrollView appcompanySv;
    private LinearLayout appcompanyLn;

    private Button closeBtn, downloadappnameBtn;

    private TextView otherpaymentTv;

    private LinearLayout parentLn;

    Intent mIntent;

    String mSelectedPayment = "";
    String m_from_custInfoYN = "";

    public ProgressDialog itemProDial;
    public int mTempFlagItemDown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_payment_select_other_pay);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = 0;
        int parentLnHeight = 0;
        // LITE 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 5;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 80;
        } else {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 3;
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 9) * 6;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.paymnetReviewLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Balance 값
            mSelectedPayment = mIntent.getStringExtra("selectedpayment");
            m_from_custInfoYN = mIntent.getStringExtra("from_custinfoYN");
            //GlobalMemberValues.logWrite(TAG, "넘겨받은 mOtherPayment : " + mOtherPayment + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite(TAG, "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 ******************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.paymnetOtherPayCloseBtn);
        closeBtn.setOnClickListener(otherPaymentBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        downloadappnameBtn = (Button)findViewById(R.id.downloadappnameBtn);
        downloadappnameBtn.setOnClickListener(otherPaymentBtnClickListener);
        downloadappnameBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                downloadappnameBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        otherpaymentTv = (TextView)findViewById(R.id.otherpaymentTv);
        otherpaymentTv.setTextSize(GlobalMemberValues.globalAddFontSize() + otherpaymentTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        otherpaymentTv.setText("AMOUNT : " + GlobalMemberValues.getStringFormatNumber(Payment.mBalanceToPay * -1, "2"));

        if (mSelectedPayment.equals("Y")){
            otherpaymentTv.setText("");
        }

        // 스크롤뷰 객체 생성
        appcompanySv = (ScrollView)findViewById(R.id.appcompanySv);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        appcompanyLn = (LinearLayout)findViewById(R.id.appcompanyLn);
        /***********************************************************************************************************/

        setOtherPaymentCompany();
    }

    private void setOtherPaymentCompany() {
        appcompanyLn.removeAllViews();

        int btnHeight = 140;
        if (GlobalMemberValues.thisTabletRealHeight < GlobalMemberValues.thisTabletRealWidth) {
            if (GlobalMemberValues.thisTabletRealHeight < 800) {
                btnHeight = 100;
            }
        } else {
            if (GlobalMemberValues.thisTabletRealWidth < 800) {
                btnHeight = 100;
            }
        }

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewBtnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
        subNewBtnLayoutParams.setMargins(0, 0, 0, 0);

        // LinearLayout 객체 생성
        LinearLayout newLn = new LinearLayout(mContext);
        newLn.setLayoutParams(newLnLayoutParams);
        newLn.setOrientation(LinearLayout.VERTICAL);
        newLn.setPadding(0, 0, 0, 0);

        String otherpaymentcompanySql = " select appname from salon_store_deliveryappcompany " +
                " where useyn = 'Y' " +
                " and delyn = 'N' " +
                " order by appname asc, idx asc ";
        GlobalMemberValues.logWrite(TAG, "otherpaymentcompanySql : " + otherpaymentcompanySql + "\n");
        Cursor otherpaymentcompanyCursor = dbInit.dbExecuteRead(otherpaymentcompanySql);
        while (otherpaymentcompanyCursor.moveToNext()) {
            final String tempAppName = GlobalMemberValues.getDBTextAfterChecked(otherpaymentcompanyCursor.getString(0), 1);
            if (!GlobalMemberValues.isStrEmpty(tempAppName)) {
                LinearLayout subNewLn = new LinearLayout(mContext);
                subNewLn.setLayoutParams(subNewLnLayoutParams);
                subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                subNewLn.setPadding(0, 0, 0, 8);

                Button subNewBtn = new Button(mContext);
                subNewBtn.setLayoutParams(subNewBtnLayoutParams);
                subNewBtn.setGravity(Gravity.CENTER);
                subNewBtn.setText(tempAppName);
                subNewBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
                subNewBtn.setTextColor(Color.parseColor("#ffffff"));
                subNewBtn.setPaintFlags(subNewBtn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

                if (tempAppName.equals(mSelectedPayment)) {
                    subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay);
                } else {
                    subNewBtn.setBackgroundResource(R.drawable.button_selector_category_otherpay_selected);
                }

                subNewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setOtherPayment(tempAppName);
                    }
                });

                subNewLn.addView(subNewBtn);

                newLn.addView(subNewLn);
            }
        }

        appcompanyLn.addView(newLn);

    }

    View.OnClickListener otherPaymentBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.paymnetOtherPayCloseBtn : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.downloadappnameBtn : {
                    downloadReasonAppName();
                    break;
                }

            }
        }
    };

    public void setOtherPayment(String paramCheckCompany) {
        if (m_from_custInfoYN == null){
            Payment.mIsPaymentTypeCheck = "Y";

            // 10202023
            if (Payment.mBalanceToPay <= 0) {
                Payment.setPaymentTypeEditText(Payment.mFocustEditText, Payment.mBalanceToPay);
                Payment.mCheckCompany = paramCheckCompany;

                Payment.paymentCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
                Payment.paymentCheckTitleTextView.setText(paramCheckCompany);
            }
            Payment.mCardUseYN = "N";
            Payment.setPaymentPrice();
        } else {
            if (m_from_custInfoYN.equals("Y")){
                Intent intentR = new Intent();
                intentR.putExtra("select_txt" , paramCheckCompany); //사용자에게 입력받은값 넣기
                setResult(RESULT_OK,intentR); //결과를 저장
                if (GlobalMemberValues.isUseFadeInOut()) {
                    overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                }
                finish();
                return;
            } else {
                Payment.mIsPaymentTypeCheck = "Y";

                // 10202023
                if (Payment.mBalanceToPay <= 0) {
                    Payment.setPaymentTypeEditText(Payment.mFocustEditText, Payment.mBalanceToPay);
                    Payment.mCheckCompany = paramCheckCompany;

                    Payment.paymentCheckTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);
                    Payment.paymentCheckTitleTextView.setText(paramCheckCompany);
                }
                Payment.mCardUseYN = "N";
                Payment.setPaymentPrice();
            }
        }




        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    private void downloadReasonAppName() {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            // 데이터 다운로드 전 현재 데이터베이스를 백업한다.
            CommandButton.backupDatabase(false);

            String[] tblNameArr = null;
            String tempTblMsg = "";

            tblNameArr = new String[]{
                    "salon_store_deliveryappcompany"
            };
            tempTblMsg = "Partner App Name";

            final String currentSyncButtonStr = "member";

            if (tblNameArr != null) {
                final String[] finalTblNameArr = tblNameArr;
                final String finalTempTblMsg = tempTblMsg;
                new AlertDialog.Builder(mContext)
                        .setTitle("Partner App Name Download")
                        .setMessage("Download " + tempTblMsg + " Data?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 프로그래스 바를 실행~
                                        itemProDial = ProgressDialog.show(
                                                mContext, finalTempTblMsg + " DATA DOWNLOAD", finalTempTblMsg + " Data is Downloading...", true, false
                                        );
                                        Thread thread = new Thread(new Runnable() {
                                            public void run() {
                                                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                                                dbInit.insertDownloadDataInDatabase(finalTblNameArr, currentSyncButtonStr, 1);
                                                // ---------------------------------------------------------------------------------
                                                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                                                itemdownhandler.sendEmptyMessage(0);
                                                // ---------------------------------------------------------------------------------
                                            }
                                        });
                                        thread.start();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        })
                        .show();
            }
        } else {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Internet is not connected", "Close");
        }
    }

    private Handler itemdownhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlagItemDown == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                setOtherPaymentCompany();
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                itemProDial.dismiss();
                // -------------------------------------------------------------------------------------
            }
        }
    };
}
