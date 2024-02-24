package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DatabaseInit;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class TableSaleSelect extends Activity {
    final String TAG = "TableSaleSelectLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout, emptyLinearLayout2, emptyLinearLayout3, emptyLinearLayout4;
    private Button closeBtn;

    private Button gotablebtn, gosalebtn;

    Intent mIntent;
    static String mSalesCode = "";
    static String mBalanceAmountValue = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_sale_select);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mSalesCode = mIntent.getStringExtra("tempSalesCode");
            mBalanceAmountValue = mIntent.getStringExtra("paymentCreditCardPopupAmount");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("cashpadlog", "mPaymentBalanceToPay : " + mPaymentBalanceToPay + "\n");
        } else {
            finish();
        }

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

        emptyLinearLayout = (LinearLayout)findViewById(R.id.emptyLinearLayout);
        emptyLinearLayout.setOnClickListener(payclickListener);

        emptyLinearLayout2 = (LinearLayout)findViewById(R.id.emptyLinearLayout2);
        emptyLinearLayout2.setOnClickListener(payclickListener);

        emptyLinearLayout3 = (LinearLayout)findViewById(R.id.emptyLinearLayout3);
        emptyLinearLayout3.setOnClickListener(payclickListener);

        emptyLinearLayout4 = (LinearLayout)findViewById(R.id.emptyLinearLayout4);
        emptyLinearLayout4.setOnClickListener(payclickListener);

        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closebtn);
        closeBtn.setOnClickListener(payclickListener);
        /***********************************************************************************************************/

        gotablebtn = (Button)findViewById(R.id.gotablebtn);
        gotablebtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                gotablebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        gosalebtn = (Button)findViewById(R.id.gosalebtn);
        gosalebtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                gosalebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        gotablebtn.setOnClickListener(payclickListener);
        gosalebtn.setOnClickListener(payclickListener);
    }

    View.OnClickListener payclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.gotablebtn : {
                    Intent intent = new Intent(context.getApplicationContext(), TableSaleMain.class);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }

                    closeActivity();
                    break;
                }
                case R.id.gosalebtn : {
                    //Payment.openCreditCardActivity(mSalesCode, mBalanceAmountValue, "");
                    closeActivity();
                    break;
                }
                case R.id.closebtn : {
                    closeActivity();
                    break;
                }
                case R.id.emptyLinearLayout : {
                    closeActivity();
                    break;
                }
                case R.id.emptyLinearLayout2 : {
                    closeActivity();
                    break;
                }
                case R.id.emptyLinearLayout3 : {
                    closeActivity();
                    break;
                }
                case R.id.emptyLinearLayout4 : {
                    closeActivity();
                    break;
                }
            }
        }
    };

    private void closeActivity() {
        finish();
        // 키패드(키보드) 감추기
        //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
        //mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
    }
}
