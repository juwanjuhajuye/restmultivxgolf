package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2017-07-19.
 */
public class CustomerPointEdit extends Activity {
    Activity activity;
    Context context;

    private LinearLayout parentLn;

    private LinearLayout customerPointEditKeypadLn, customerPointEditHistoryLn;

    private Button customerPointEdit_suButton1,customerPointEdit_suButton2,customerPointEdit_suButton3;
    private Button customerPointEdit_suButton4,customerPointEdit_suButton5,customerPointEdit_suButton6;
    private Button customerPointEdit_suButton7,customerPointEdit_suButton8,customerPointEdit_suButton9;
    private Button customerPointEdit_suButton0,customerPointEdit_suButton00,customerPointEdit_suButtonBack;
    private Button customerPointEdit_suButtonV,customerPointEdit_suButtonX, customerPointEdit_suButtonX2;

    private Button customerPointEditHistoryBackButton, customerPointEdit_historyButton, customerPointEditSyncPointWithCloud;

    private EditText customerPointAddUseEv, customerPointContentsEv;

    private TextView quantityTitleTextView;

    private TextView customerPointAddUseTv, customerPointContentsTv;
    private TextView customerPointHistoryTitleDateTv, customerPointHistoryTitleAddedTv;
    private TextView customerPointHistoryTitleUsedTv, customerPointHistoryTitleContentsTv;

    private TextView customerPointHistoryBalanceTv;

    private RadioGroup customerPointAddUseRadioGroup;
    private RadioButton customerPointAddRadioButton, customerPointUseRadioButton;

    // 기프트카드 적립/사용 히스토리 리스트뷰
    private ListView customerPointHistoryListView;

    // ListView 에 고객항목 붙일 때 필요한 객체들
    TemporaryCustomerPointHistoryList mTempCpHistoryList;
    public static ArrayList<TemporaryCustomerPointHistoryList> mGeneralArrayList;
    public static CustomerPointHistoryListAdapter mcustomerPointHistoryListAdapter;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    String mCustomerPointAddUseRadioBtnValue = "1";

    String mMemMileageUploadReturnValue = "";

    String mGetMemberMileage = "";

    // DB 객체 선언
    static DatabaseInit dbInit;

    public ProgressDialog memMileageCheckProDial;

    // 프렌차이즈 관련 -------------------------------
    LinearLayout offlineLn, onlineLn;
    WebView customerPointWv;
    private final Handler handler = new Handler();
    ProgressDialog dialog;
    private String mCurrentUrl;
    // -----------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_customer_point_edit);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 6;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 10;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.customerPointEditLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        activity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 선택포시션 값
            //parentSelectedPosition = mIntent.getStringExtra("ParentSaleCartPosition");
            /*******************************************************************************************/
        } else {
            finish();
        }
    }

    public void setLayoutContent() {
        mGetMemberMileage = "";

        customerPointEditKeypadLn = (LinearLayout)findViewById(R.id.customerPointEditKeypadLn);
        customerPointEditHistoryLn = (LinearLayout)findViewById(R.id.customerPointEditHistoryLn);

        customerPointEdit_suButton1 = (Button)findViewById(R.id.customerPointEdit_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton2 = (Button)findViewById(R.id.customerPointEdit_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton3 = (Button)findViewById(R.id.customerPointEdit_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton4 = (Button)findViewById(R.id.customerPointEdit_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton5 = (Button)findViewById(R.id.customerPointEdit_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton6 = (Button)findViewById(R.id.customerPointEdit_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton7 = (Button)findViewById(R.id.customerPointEdit_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton8 = (Button)findViewById(R.id.customerPointEdit_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton9 = (Button)findViewById(R.id.customerPointEdit_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton0 = (Button)findViewById(R.id.customerPointEdit_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButton00 = (Button)findViewById(R.id.customerPointEdit_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            customerPointEdit_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            customerPointEdit_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            customerPointEdit_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButtonBack = (Button)findViewById(R.id.customerPointEdit_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButtonBack.setText("");
            customerPointEdit_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            customerPointEdit_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButtonV = (Button)findViewById(R.id.customerPointEdit_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButtonV.setText("");
            customerPointEdit_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            customerPointEdit_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        customerPointEdit_suButtonX = (Button)findViewById(R.id.customerPointEdit_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_suButtonX.setText("");
            customerPointEdit_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            customerPointEdit_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        customerPointEditHistoryBackButton = (Button)findViewById(R.id.customerPointEditHistoryBackButton);
        customerPointEditHistoryBackButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerPointEditHistoryBackButton.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerPointEdit_historyButton = (Button)findViewById(R.id.customerPointEdit_historyButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            customerPointEdit_historyButton.setText("");
            customerPointEdit_historyButton.setBackgroundResource(R.drawable.ab_imagebutton_customerpoint_history);
        } else {
            customerPointEdit_historyButton.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                    customerPointEdit_historyButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        customerPointEditSyncPointWithCloud = (Button)findViewById(R.id.customerPointEditSyncPointWithCloud);
        customerPointEditSyncPointWithCloud.setTextSize(GlobalMemberValues.globalAddFontSize() + 
                customerPointEditSyncPointWithCloud.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );

        customerPointEdit_suButtonX2 = (Button)findViewById(R.id.customerPointEdit_suButtonX2);

        customerPointAddUseRadioGroup = (RadioGroup)findViewById(R.id.customerPointAddUseRadioGroup);
        customerPointAddUseRadioGroup.setOnCheckedChangeListener(checkAddUse);

        customerPointAddRadioButton = (RadioButton)findViewById(R.id.customerPointAddRadioButton);
        customerPointAddRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointAddRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointUseRadioButton = (RadioButton)findViewById(R.id.customerPointUseRadioButton);
        customerPointUseRadioButton.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointUseRadioButton.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointEdit_suButton1.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton2.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton3.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton4.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton5.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton6.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton7.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton8.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton9.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton0.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButton00.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButtonBack.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButtonV.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButtonX.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_suButtonX2.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEditHistoryBackButton.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEdit_historyButton.setOnClickListener(customerPointEditButtonClickListener);
        customerPointEditSyncPointWithCloud.setOnClickListener(customerPointEditButtonClickListener);

        customerPointAddUseEv = (EditText)findViewById(R.id.customerPointAddUseEv);
        customerPointAddUseEv.setOnTouchListener(mTouchCustomerPointEditTvTouchListener);

        customerPointContentsEv = (EditText)findViewById(R.id.customerPointContentsEv);
        //customerPointContentsEv.setOnTouchListener(mTouchCustomerPointContentsEditTvTouchListener);

        quantityTitleTextView = (TextView)findViewById(R.id.quantityTitleTextView);
        quantityTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + quantityTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName)) {
            quantityTitleTextView.setText("\"" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName + "\" Point");
        }

        customerPointAddUseTv = (TextView)findViewById(R.id.customerPointAddUseTv);
        customerPointAddUseTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointAddUseTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointContentsTv = (TextView)findViewById(R.id.customerPointContentsTv);
        customerPointContentsTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointContentsTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryTitleDateTv = (TextView)findViewById(R.id.customerPointHistoryTitleDateTv);
        customerPointHistoryTitleDateTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryTitleDateTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryTitleAddedTv = (TextView)findViewById(R.id.customerPointHistoryTitleAddedTv);
        customerPointHistoryTitleAddedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryTitleAddedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryTitleUsedTv = (TextView)findViewById(R.id.customerPointHistoryTitleUsedTv);
        customerPointHistoryTitleUsedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryTitleUsedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryTitleUsedTv = (TextView)findViewById(R.id.customerPointHistoryTitleUsedTv);
        customerPointHistoryTitleUsedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryTitleUsedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryTitleContentsTv = (TextView)findViewById(R.id.customerPointHistoryTitleContentsTv);
        customerPointHistoryTitleContentsTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryTitleContentsTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        customerPointHistoryBalanceTv = (TextView)findViewById(R.id.customerPointHistoryBalanceTv);
        customerPointHistoryBalanceTv.setTextSize(GlobalMemberValues.globalAddFontSize() + customerPointHistoryBalanceTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // 고객리스트 보여지는 ListView 객체 생성
        customerPointHistoryListView = (ListView)findViewById(R.id.customerPointHistoryListView);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), customerPointAddUseEv);

        // 프렌차이즈 관련
        offlineLn = (LinearLayout)findViewById(R.id.offlineLn);
        onlineLn = (LinearLayout)findViewById(R.id.onlineLn);

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            offlineLn.setVisibility(View.VISIBLE);

            customerPointEditSyncPointWithCloud.setVisibility(View.GONE);

            setCustomerMemoForWeb();
        }
    }

    View.OnClickListener customerPointEditButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customerPointEdit_suButton1 : {
                    numberButtonClick(customerPointEdit_suButton1);
                    break;
                }
                case R.id.customerPointEdit_suButton2 : {
                    numberButtonClick(customerPointEdit_suButton2);
                    break;
                }
                case R.id.customerPointEdit_suButton3 : {
                    numberButtonClick(customerPointEdit_suButton3);
                    break;
                }
                case R.id.customerPointEdit_suButton4 : {
                    numberButtonClick(customerPointEdit_suButton4);
                    break;
                }
                case R.id.customerPointEdit_suButton5 : {
                    numberButtonClick(customerPointEdit_suButton5);
                    break;
                }
                case R.id.customerPointEdit_suButton6 : {
                    numberButtonClick(customerPointEdit_suButton6);
                    break;
                }
                case R.id.customerPointEdit_suButton7 : {
                    numberButtonClick(customerPointEdit_suButton7);
                    break;
                }
                case R.id.customerPointEdit_suButton8 : {
                    numberButtonClick(customerPointEdit_suButton8);
                    break;
                }
                case R.id.customerPointEdit_suButton9 : {
                    numberButtonClick(customerPointEdit_suButton9);
                    break;
                }
                case R.id.customerPointEdit_suButton0 : {
                    numberButtonClick(customerPointEdit_suButton0);
                    break;
                }
                case R.id.customerPointEdit_suButton00 : {
                    numberButtonClick(customerPointEdit_suButton00);
                    break;
                }
                case R.id.customerPointEdit_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mQtyEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mQtyEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                            mQtyEtValue = "";
                        }
                    } else {
                        mQtyEtValue = "";
                    }
                    customerPointAddUseEv.setText(mQtyEtValue);

                    // 커서를 맨 뒤로...
                    GlobalMemberValues.setCursorLastDigit(customerPointAddUseEv);
                    break;
                }
                case R.id.customerPointEdit_suButtonV : {
                    setCustomerPoint();
                    break;
                }
                case R.id.customerPointEdit_suButtonX : {
                    init();
                    finish();
                    break;
                }
                case R.id.customerPointEdit_suButtonX2 : {
                    init();
                    finish();
                    break;
                }
                case R.id.customerPointEditHistoryBackButton : {
                    showKeypad();
                    break;
                }
                case R.id.customerPointEdit_historyButton : {
                    setVisibilityCustomerPointHistory();
                    break;
                }
                case R.id.customerPointEditSyncPointWithCloud : {
                    new AlertDialog.Builder(context)
                            .setTitle("Data Download")
                            .setMessage("Would you like to sync customer points with the cloud?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            syncMileageWithCloud();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            })
                            .show();

                    break;
                }
            }
        }
    };



    RadioGroup.OnCheckedChangeListener checkAddUse = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getId()) {
                case R.id.customerPointAddUseRadioGroup : {
                    switch (checkedId) {
                        // Add
                        case R.id.customerPointAddRadioButton : {
                            mCustomerPointAddUseRadioBtnValue = "1";
                            break;
                        }
                        // Use
                        case R.id.customerPointUseRadioButton : {
                            mCustomerPointAddUseRadioBtnValue = "2";
                            break;
                        }
                    }
                    GlobalMemberValues.logWrite("CustomerPointEdit", "value : " + mCustomerPointAddUseRadioBtnValue + "\n");
                    break;
                }
            }
        }
    };

    private void syncMileageWithCloud() {
        // 프로그래스 바를 실행~
        memMileageCheckProDial = ProgressDialog.show(context, "", "Customer's point is being downloaded from cloud", true, false);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    if (!GlobalMemberValues.isOnline().equals("00")) {
                        GlobalMemberValues.showDialogNoInternet(context);
                        mGetMemberMileage = "";
                    } else {
                        API_download_membermileage apiMemMileageDownload = new API_download_membermileage(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid);
                        apiMemMileageDownload.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (apiMemMileageDownload.mFlag) {
                                mGetMemberMileage = apiMemMileageDownload.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            mGetMemberMileage = "";
                        }
                    }
                }
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                memMileageDownloadHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler memMileageDownloadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!GlobalMemberValues.isStrEmpty(mGetMemberMileage)) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                // temp_salecart 테이블 수정
                Vector<String> strUpdateQueryVec = new Vector<String>();
                String strUpdateQuery = "";

                strUpdateQuery = "update member2 set " +
                        " mileage = '" + GlobalMemberValues.getDoubleAtString(mGetMemberMileage) + "' " +
                        " where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' ";
                strUpdateQueryVec.addElement(strUpdateQuery);

                strUpdateQuery = "update salon_member set " +
                        " mileage = '" + GlobalMemberValues.getDoubleAtString(mGetMemberMileage) + "' " +
                        " where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' ";
                strUpdateQueryVec.addElement(strUpdateQuery);

                for (String tempQuery : strUpdateQueryVec) {
                    GlobalMemberValues.logWrite("MemberMileageDownloadQuery2", "query : " + tempQuery + "\n");
                }
                // 트랜잭션으로 DB 처리한다.
                String returnResult = "";
                returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                } else {
                    GlobalMemberValues.GLOBAL_CUSTOMERINFO.memMileage = mGetMemberMileage;
                    // 고객정보 변수할당
                    GlobalMemberValues.setCustomerSelected(GlobalMemberValues.GLOBAL_CUSTOMERINFO);

                    activity.recreate();
                }
                // -------------------------------------------------------------------------------------
                // 2. 프로그래스바를 사라지게 함 -------------------------------------------------------
                memMileageCheckProDial.dismiss();
                // -------------------------------------------------------------------------------------

                Toast.makeText(context, "Customer points have been synced with the cloud", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showKeypad() {
        init();
        customerPointEditHistoryLn.setVisibility(View.GONE);

        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        customerPointEditKeypadLn.setVisibility(View.VISIBLE);
        customerPointEditKeypadLn.setAnimation(animation1);
    }

    private void setCustomerPoint() {
        String evcustomerPointAddUse = customerPointAddUseEv.getText().toString();
        GlobalMemberValues.logWrite("customerPointAddUse", "evcustomerPointAddUse number : " + evcustomerPointAddUse + "\n");
        if (!GlobalMemberValues.isStrEmpty(evcustomerPointAddUse)) {
            String insCustomerPointContents = customerPointContentsEv.getText().toString();

            Vector<String> strInsertQueryVec = new Vector<String>();
            String strInsSqlQuery = "";

            strInsSqlQuery = "insert into member_mileage (contents, mileage, uid, mcase, membershipcardno, sid, codeforupload " +
                    " ) values ( " +
                    " '" + insCustomerPointContents + "', " +
                    " '" + evcustomerPointAddUse + "', " +
                    " '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "', " +
                    " '" + mCustomerPointAddUseRadioBtnValue + "', " +            // 1: 적립        2 : 사용
                    " '" + "" + "', " +             // 멤버쉽카드번호가 있을 경우 추후 코딩할 것
                    " '" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "', " +
                    " '" + GlobalMemberValues.getCodeForUpload("mileage") + "' " +
                    " ) ";
            strInsertQueryVec.addElement(strInsSqlQuery);

            double tempInsertMileage = 0;
            if (mCustomerPointAddUseRadioBtnValue == "1" || mCustomerPointAddUseRadioBtnValue.equals("1")) {
                tempInsertMileage = GlobalMemberValues.getDoubleAtString(evcustomerPointAddUse);
            } else {
                tempInsertMileage = GlobalMemberValues.getDoubleAtString(evcustomerPointAddUse) * -1;
            }

            strInsSqlQuery = "update salon_member set mileage = mileage + " + tempInsertMileage +
                    " where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' " +
                    " and scode = '" + GlobalMemberValues.SALON_CODE + "' ";
            strInsertQueryVec.addElement(strInsSqlQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
            }

            // 트랜잭션으로 DB 처리한다.
            String returnResult = "N";
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            } else {
                // 프렌차이즈 관련
                if (GlobalMemberValues.IS_COM_CHAINSTORE) {
                    String tempMaxSeno = MainActivity.mDbInit.dbExecuteReadReturnString("select seno from member_mileage order by seno desc limit 1");
                    String tempMdate = MainActivity.mDbInit.dbExecuteReadReturnString("select mdate from member_mileage order by seno desc limit 1");
                    String tempCodeForUpload = MainActivity.mDbInit.dbExecuteReadReturnString("select codeforupload from member_mileage order by seno desc limit 1");
                    String tempParamValue = tempMdate + "-JJJ-" + insCustomerPointContents + "-JJJ-" + evcustomerPointAddUse + "-JJJ-" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid +
                            "-JJJ-" + mCustomerPointAddUseRadioBtnValue + "-JJJ-" + tempCodeForUpload + "-JJJ-" + tempMaxSeno;
                    setCustomerPointValueForFranchise(tempParamValue);
                } else {
                    setCustomerPointValue();
                }
                setVisibilityCustomerPointHistory();
            }
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Enter point", "Close");
        }
    }

    private void setCustomerPointValue() {
        String tempCustomerPoint = "0";
        tempCustomerPoint = dbInit.dbExecuteReadReturnString(
                "select mileage from salon_member where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' " +
                        " and scode = '" + GlobalMemberValues.SALON_CODE + "' ");
        if (GlobalMemberValues.isStrEmpty(tempCustomerPoint)) {
            tempCustomerPoint = "0";
        }
        GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("Point : "
                + GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDoubleAtString(tempCustomerPoint), "2"));
        GlobalMemberValues.GLOBAL_CUSTOMERINFO.memMileage = tempCustomerPoint;

        final String finalCustomerPoint = tempCustomerPoint;

        // API 를 통해 클라우드에 해당고객의 포인트를 업로드
        Thread thread = new Thread(new Runnable() {
            public void run() {
                if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                    if (!GlobalMemberValues.isOnline().equals("00")) {
                    } else {
                        // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                        API_membermileageupload_tocloud apiMemMileageUpload
                                = new API_membermileageupload_tocloud(GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid, finalCustomerPoint);
                        apiMemMileageUpload.execute(null, null, null);
                        try {
                            Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                            if (apiMemMileageUpload.mFlag) {
                                mMemMileageUploadReturnValue = apiMemMileageUpload.mReturnValue;
                            }
                        } catch (InterruptedException e) {
                            mMemMileageUploadReturnValue = "";
                        }
                        // ---------------------------------------------------------------------------------
                    }
                }

                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                memReghandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler memReghandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mMemMileageUploadReturnValue.equals("00")) {
                Toast.makeText(context, "Completed synchronization with cloud server", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setVisibilityCustomerPointHistory() {
        // 기프트카드 사용/적립 히스토리 보여주기
        customerPointEditKeypadLn.setVisibility(View.GONE);

        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_bottom);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);
        customerPointEditHistoryLn.setVisibility(View.VISIBLE);
        customerPointEditHistoryLn.setAnimation(animation1);

        setCustomerPointHistoryListView();
    }

    private void numberButtonClick(Button btn) {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.toString().length());
        if (mQtyEtValue.length() < 12) {
            sb.append(mQtyEtValue).append(btn.getText().toString());
            Long tempNumber = Long.parseLong(sb.toString());
            mQtyEtValue = String.valueOf(tempNumber);
            String inputSu = GlobalMemberValues.getStringFormatNumber((Double.parseDouble(mQtyEtValue) * 0.01), "2");
            customerPointAddUseEv.setText(inputSu);

            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(customerPointAddUseEv);
        }
    }

    View.OnTouchListener mTouchCustomerPointEditTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            GlobalMemberValues.setKeyPadHide(context, customerPointAddUseEv);
            customerPointAddUseEv.requestFocus();
            customerPointAddUseEv.setSelection(customerPointAddUseEv.length());
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(customerPointAddUseEv);
            showKeypad();
            return true;
        }
    };

    View.OnTouchListener mTouchCustomerPointContentsEditTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            GlobalMemberValues.setKeyPadHide(context, customerPointContentsEv);
            customerPointContentsEv.requestFocus();
            customerPointContentsEv.setSelection(customerPointContentsEv.length());
            // 커서를 맨 뒤로...
            GlobalMemberValues.setCursorLastDigit(customerPointContentsEv);
            showKeypad();
            return true;
        }
    };

    /** 기프트카드 적립/사용 리스트 배치하기 ********************************************************/
    public void setCustomerPointHistoryListView() {
        DatabaseInit dbInit = new DatabaseInit(context);
        String strQuery = "select seno, mileage, mcase, mdate, contents from member_mileage " +
                " where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' " +
                " order by mdate desc ";
        Cursor customerPointHistoryCursor = dbInit.dbExecuteRead(strQuery);

        double totalBalaceMMH = 0.0;

        // ArrayList 객체 생성
        mGeneralArrayList = new ArrayList<TemporaryCustomerPointHistoryList>();
        while (customerPointHistoryCursor.moveToNext()) {
            String tempAddUsePrice = customerPointHistoryCursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(tempAddUsePrice)) {
                String paramsTempcustomerPointHistoryListArray[] = {
                        GlobalMemberValues.getDBTextAfterChecked(customerPointHistoryCursor.getString(0), 1),
                        GlobalMemberValues.getDBTextAfterChecked(customerPointHistoryCursor.getString(1), 1),
                        GlobalMemberValues.getDBTextAfterChecked(customerPointHistoryCursor.getString(2), 1),
                        GlobalMemberValues.getDBTextAfterChecked(customerPointHistoryCursor.getString(3), 1),
                        GlobalMemberValues.getDBTextAfterChecked(customerPointHistoryCursor.getString(4), 1)
                };
                mTempCpHistoryList = new TemporaryCustomerPointHistoryList(paramsTempcustomerPointHistoryListArray);
                mGeneralArrayList.add(mTempCpHistoryList);

                totalBalaceMMH += GlobalMemberValues.getDoubleAtString(tempAddUsePrice);
            }
        }

        // 실제 현재 보유한 포인트 총액
        double realPointTotal = GlobalMemberValues.getDoubleAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                " select mileage from salon_member where uid = '" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid + "' "));

        // 히스토리 합산금액과 현재 보유한 포인트 차액이 있을 경우 history 에 이월금이라고 표시해준다.
        double realPointInTotalBalaceMMH = realPointTotal - totalBalaceMMH;
        if (realPointInTotalBalaceMMH != 0) {
            String tempContents = "Add the difference amount to the total amount";
            String tempMcase = "1";
            if (realPointInTotalBalaceMMH < 0) {
                tempContents = "Subtract the difference from the total amount";
                tempMcase = "2";
            }
            String paramsTempcustomerPointHistoryListArray[] = {
                    "", GlobalMemberValues.getCommaStringForDouble(realPointInTotalBalaceMMH + ""), tempMcase, "-------", tempContents
            };
            mTempCpHistoryList = new TemporaryCustomerPointHistoryList(paramsTempcustomerPointHistoryListArray);
            mGeneralArrayList.add(mTempCpHistoryList);
        }

        mcustomerPointHistoryListAdapter = new CustomerPointHistoryListAdapter(context, R.layout.customerpoint_history_list, mGeneralArrayList);
        customerPointHistoryListView.setAdapter(mcustomerPointHistoryListAdapter);

        // 프렌차이즈 관련
        if (!GlobalMemberValues.IS_COM_CHAINSTORE) {
            customerPointHistoryBalanceTv.setText("Balance : " + GlobalMemberValues.getCommaStringForDouble(realPointTotal + ""));
        }

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너
        //customerPointHistoryListView.setOnItemClickListener(mCustomerInfoItemClickListener);
    }
    /*******************************************************************************/

    private void init() {
        mGetMemberMileage = "";
        customerPointAddUseEv.setText("");
        mQtyEtValue = "";
    }







    // 프렌차이즈 관련
    private void setMileagedBalance(String paramValue) {
        if (GlobalMemberValues.IS_COM_CHAINSTORE && !GlobalMemberValues.isStrEmpty(paramValue)) {
            String tempAddedBalance = "0.0";
            String tempUsedBalance = "0.0";
            String tempTotalBalance = "0.0";
            String[] arrrGiftCardBalanceValue = paramValue.split(GlobalMemberValues.STRSPLITTER2);
            if (arrrGiftCardBalanceValue.length > 0) {
                tempAddedBalance = arrrGiftCardBalanceValue[0];
            }
            if (arrrGiftCardBalanceValue.length > 1) {
                tempUsedBalance = arrrGiftCardBalanceValue[1];
            }
            if (arrrGiftCardBalanceValue.length > 2) {
                tempTotalBalance = arrrGiftCardBalanceValue[2];
            }

            customerPointHistoryBalanceTv.setText("Balance : " + GlobalMemberValues.getCommaStringForDouble(tempTotalBalance + ""));
            GlobalMemberValues.GLOBAL_BOTTOMMEMBER_POINT.setText("Point : " + GlobalMemberValues.getCommaStringForDouble(tempTotalBalance + ""));
        }
    }


    private void setCustomerPointValueForFranchise(String paramValue) {
        String evcustomerPointAddUse = customerPointAddUseEv.getText().toString();
        GlobalMemberValues.logWrite("customerPointAddUse", "evcustomerPointAddUse number : " + evcustomerPointAddUse + "\n");
        if (!GlobalMemberValues.isStrEmpty(evcustomerPointAddUse)) {
            String insCustomerPointContents = customerPointContentsEv.getText().toString();

            String[] arrValue = paramValue.split("-JJJ-");

            String tempMdate = "";
            if (arrValue.length > 0) {
                tempMdate = arrValue[0];
            }
            String tempContents = "";
            if (arrValue.length > 1) {
                tempContents = arrValue[1];
            }
            String tempMileage = "";
            if (arrValue.length > 2) {
                tempMileage = arrValue[2];
            }
            String tempUid = "";
            if (arrValue.length > 3) {
                tempUid = arrValue[3];
            }
            String tempMcase = "";
            if (arrValue.length > 4) {
                tempMcase = arrValue[4];
            }
            String tempCodeForUpload = "";
            if (arrValue.length > 5) {
                tempCodeForUpload = arrValue[5];
            }
            String tempMaxSeno = "";
            if (arrValue.length > 6) {
                tempMaxSeno = arrValue[6];
            }

            String webviewUrl = GlobalMemberValues.CLOUD_SERVER_URL + "NZCCUSTOMER/";
            webviewUrl += "webview_customerpoint_editresult.asp?";
            webviewUrl += "scode=" + GlobalMemberValues.SALON_CODE;
            webviewUrl += "&sidx=" + GlobalMemberValues.STORE_INDEX;
            webviewUrl += "&stcode=" + GlobalMemberValues.STATION_CODE;
            webviewUrl += "&contents=" + tempContents;
            webviewUrl += "&mileage=" + tempMileage;
            webviewUrl += "&uid=" + tempUid;
            webviewUrl += "&mcase=" + tempMcase;
            webviewUrl += "&savetype=" + "P";
            webviewUrl += "&seno=" + tempMaxSeno;
            webviewUrl += "&mdate=" + tempMdate;
            webviewUrl += "&employeeIdx=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            webviewUrl += "&employeeName=" + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            webviewUrl += "&codefrompos=" + tempCodeForUpload;
            webviewUrl = GlobalMemberValues.getReplaceText(webviewUrl, " ", "|||");

            customerPointWv.loadUrl(webviewUrl);
            GlobalMemberValues.logWrite("customerwebviewlo", "cloud url : " + webviewUrl + "\n");
        }

    }

    private void setCustomerMemoForWeb() {
        // 히스토리 리스트 보여지는 ListView 객체 생성 - WEB 용 -------------------------------------------------------------------------
        customerPointWv = (WebView)findViewById(R.id.customerSearchWv);

        if (GlobalMemberValues.IS_COM_CHAINSTORE) {
            offlineLn.setVisibility(View.GONE);
            onlineLn.setVisibility(View.VISIBLE);

            customerPointEditSyncPointWithCloud.setVisibility(View.GONE);
        }

        //스크롤바 없애기
        customerPointWv.setVerticalScrollBarEnabled(false);
        customerPointWv.setHorizontalScrollBarEnabled(false);

        customerPointWv.setWebViewClient(new WebViewClient());
        WebSettings set = customerPointWv.getSettings();

        // 화면 꽉 차게
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        set.setLoadWithOverviewMode(true);
        set.setUseWideViewPort(true);
        customerPointWv.setInitialScale(1);
        //자바스크립트 가능하게
        set.setJavaScriptEnabled(true);
        //웹뷰 멀티터치 가능하게
        set.setBuiltInZoomControls(true);
        set.setSupportZoom(true);
        //Local Storage, sessionStorage 유효화
        set.setDomStorageEnabled(true);
        //window.open 메서드 이용할 때의 동작 설정
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        //아래것 사용하면 팝업이 사용안됨
        //set.setSupportMultipleWindows(true);

        //API Level 16 부터 이용 가능
        try {
            set.setAllowUniversalAccessFromFileURLs(true);
        } catch(Exception e) {
            //아무것도 하지 않는다.
        }

        //웹에서 자바스크립트로 앱의 메소드 사용을 위한 것..
        //AndroidBridge 라는 사용자 클래스를 만들어 거기에 메소두를 구현하고,
        //웹에서 thisWebViewName 이름으로 메소드 접근하여 사용
        //ex)웹에서 웹뷰에서 미리 정의해든 사용자클래스 AndroidBridge 클래스의 setMessage 메소드를 사용할 경우
        // window.thisWebViewName.setMessage("메시지 내용");
        customerPointWv.addJavascriptInterface(new AndroidBridge(), "customerdatatrans");
        customerPointWv.setWebChromeClient(wcc);
        customerPointWv.setWebViewClient(new WebViewClientClass());

        mCurrentUrl = null;

        String webviewUrl = GlobalMemberValues.CLOUD_SERVER_URL + "NZCCUSTOMER/";
        webviewUrl += "webview_customerpoint.asp?";
        webviewUrl += "scode=" + GlobalMemberValues.SALON_CODE;
        webviewUrl += "&sidx=" + GlobalMemberValues.STORE_INDEX;
        webviewUrl += "&stcode=" + GlobalMemberValues.STATION_CODE;
        webviewUrl += "&uid=" + GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
        webviewUrl = GlobalMemberValues.getReplaceText(webviewUrl, " ", "|||");

        customerPointWv.loadUrl(webviewUrl);
        GlobalMemberValues.logWrite("customerwebviewlo", "cloud url : " + webviewUrl + "\n");
        // -------------------------------------------------------------------------------------------------------------------------------
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(mCurrentUrl != null && url != null && url.equals(mCurrentUrl)) {
                //webview.goBack();
                mCurrentUrl = null;
            } else {
                view.loadUrl(url);
                mCurrentUrl = url;
            }
            return true;
        }
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);

            GlobalMemberValues.logWrite("webcloudurl", "url : " + url + "\n");

            if (!activity.isFinishing()) {
                dialog = new JJJCustomAnimationDialog(activity);
                dialog.show();
            }
        }

        // 페이지 로딩시
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, "Web Error : " + description, Toast.LENGTH_SHORT).show();
            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (dialog !=null && dialog.isShowing()) {
                dialog.cancel();
            }

            String urlString = customerPointWv.getUrl().toString();
            if (urlString.indexOf("posweblogin") == -1) {

            } else {

            }
            GlobalMemberValues.logWrite("nowURLCloudAddressIndexOf", "indexof count : " + urlString.indexOf("posweblogin") + "\n");
        }
    }


    WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog
                    .Builder(context)
                    .setTitle("AlertDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,String message, final JsResult result) {
            new AlertDialog.Builder(context)
                    .setTitle("ConfirmDialog")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();

            return true;
        }

    };

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    //Toast.makeText(GiftCardBalanceCheck.this, msg, Toast.LENGTH_SHORT).show();
                    if (!GlobalMemberValues.isStrEmpty(msg)) {
                        setMileagedBalance(msg);
                        GlobalMemberValues.logWrite("historywebaddresslog", "msg : " + msg + "\n");
                    }
                }
            });
        }
    }


}
