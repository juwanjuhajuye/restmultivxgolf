package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class CashInOutPopupKeyin extends Activity {
    final String TAG = "CashInOutPopupKeyinLog";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView titleTv;
    private EditText kitchenMemoEv;
    private Button kitchenMemoBtn, kitchenMemoClearBtn, kitchenMemoCloseBtn;

    Intent mIntent;

    TemporarySaleCart parentTemporarySaleCart;
    String parentSelectedPosition;

    String mRegmemotxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cash_in_out_popup_keyin);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {

            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 등록된 키친메모
            mRegmemotxt = mIntent.getStringExtra("regmemotxt");
            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

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

        titleTv = (TextView) findViewById(R.id.titleTv);
        titleTv.setTextSize(
                titleTv.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );
        if (parentTemporarySaleCart != null) {
            if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSvcName)) {
                titleTv.setText("MEMO (" + parentTemporarySaleCart.mSvcName + ")");
            }
        }

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        kitchenMemoCloseBtn = (Button) findViewById(R.id.kitchenMemoCloseBtn);
        kitchenMemoCloseBtn.setOnClickListener(kitchenMemoButtonClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            kitchenMemoCloseBtn.setText("");
            kitchenMemoCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            kitchenMemoCloseBtn.setTextSize(
                    kitchenMemoCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        kitchenMemoBtn = (Button)findViewById(R.id.kitchenMemoBtn);
        kitchenMemoBtn.setOnClickListener(kitchenMemoButtonClickListener);
        kitchenMemoBtn.setTextSize(
                kitchenMemoBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        kitchenMemoClearBtn = (Button)findViewById(R.id.kitchenMemoClearBtn);
        kitchenMemoClearBtn.setOnClickListener(kitchenMemoButtonClickListener);
        kitchenMemoClearBtn.setTextSize(
                kitchenMemoClearBtn.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        kitchenMemoEv = (EditText)findViewById(R.id.kitchenMemoEv);
        kitchenMemoEv.setTextSize(
                kitchenMemoEv.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );
        kitchenMemoEv.setText("");
        kitchenMemoEv.setOnTouchListener(kitchenMemoEvTouchListener);
        kitchenMemoEv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        kitchenMemoEv.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        kitchenMemoEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        insertKitchenMemo();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        kitchenMemoEv.setText(mRegmemotxt);
    }

    View.OnTouchListener kitchenMemoEvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    private void setMemoClearEditText() {
        kitchenMemoEv.setSelection(0);
        kitchenMemoEv.setText("");
        GlobalMemberValues.setUseableOrNotEditText(kitchenMemoEv, true);
        kitchenMemoEv.setBackgroundResource(R.drawable.roundlayout_background_custmemo);
        kitchenMemoEv.requestFocus();
    }

    View.OnClickListener kitchenMemoButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kitchenMemoCloseBtn : {
                    closeActivity();
                }
                case R.id.kitchenMemoBtn : {
                    insertKitchenMemo();
                    break;
                }
                case R.id.kitchenMemoClearBtn : {
                    kitchenMemoEv.setText("");
                    kitchenMemoEv.requestFocus();
                    break;
                }
            }
        }
    };

    private void insertKitchenMemo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

        String tempValue = kitchenMemoEv.getText().toString();
        CashInOutPopup.cashInOutReasonEv.setText(tempValue);

        closeActivity();
    }

    private void closeActivity() {
        CashInOutPopup.isMemoOpen = false;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);

        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_top);
        }
    }
}
