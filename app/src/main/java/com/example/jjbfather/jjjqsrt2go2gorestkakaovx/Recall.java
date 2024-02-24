package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;

import java.sql.ResultSet;
import java.util.Vector;

public class Recall extends Activity {
    public static Activity mActivity;
    public static Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private Button closeBtn;

    private LinearLayout recallSaleListLinearLayout;

    private LinearLayout parentLn;

    private TextView recallTitleTextView;
    private TextView kindTitleTextView, serviceNameTitleTextView, qtyTitleTextView;
    private TextView amountTitleTextView, employeeTitleTextView, totalTitleTextView;

    ScrollView recallSaleListScrollView;

    Button recallRecallButton, recallDeleteButton, kitchenprintButton, checkprintButton, labelprintButton;

    GetDataAtSQLite dataAtSqlite;

    Intent mIntent;

    String mSelectedHoldCode = "";
    LinearLayout mSelectedLn;

    public static String mKitchenPrintOnRecall = "N";

    public static String mKitchenReprint = "N";

    public static Context insContext;

    int REQUESTCODE_RECALL = 5001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_recall);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        dataAtSqlite = new GetDataAtSQLite(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 11) * 8;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.recallLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
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
        mKitchenPrintOnRecall = "N";
        mKitchenReprint = "N";

        recallTitleTextView = (TextView)findViewById(R.id.recallTitleTextView);
        recallTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + recallTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        kindTitleTextView = (TextView)findViewById(R.id.kindTitleTextView);
        kindTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + kindTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        serviceNameTitleTextView = (TextView)findViewById(R.id.serviceNameTitleTextView);
        serviceNameTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + serviceNameTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        qtyTitleTextView = (TextView)findViewById(R.id.qtyTitleTextView);
        qtyTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + qtyTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        amountTitleTextView = (TextView)findViewById(R.id.amountTitleTextView);
        amountTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + amountTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        employeeTitleTextView = (TextView)findViewById(R.id.employeeTitleTextView);
        employeeTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + employeeTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        totalTitleTextView = (TextView)findViewById(R.id.totalTitleTextView);
        totalTitleTextView.setTextSize(GlobalMemberValues.globalAddFontSize() + totalTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize());


        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.recallCloseBtn);
        closeBtn.setOnClickListener(recallBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        /***********************************************************************************************************/

        // 스크롤뷰 객체 생성
        recallSaleListScrollView = (ScrollView)findViewById(R.id.recallSaleListScrollView);

        /** 객체 생성 및 리스너 연결 *******************************************************************************/
        recallRecallButton = (Button)findViewById(R.id.recallRecallButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            recallRecallButton.setText("");
            recallRecallButton.setBackgroundResource(R.drawable.ab_imagebutton_recall_recall);
        } else {
            recallRecallButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    recallRecallButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        recallDeleteButton = (Button)findViewById(R.id.recallDeleteButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            recallDeleteButton.setText("");
            recallDeleteButton.setBackgroundResource(R.drawable.ab_imagebutton_recall_delete);
        } else {
            recallDeleteButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    recallDeleteButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        kitchenprintButton = (Button)findViewById(R.id.kitchenprintButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            kitchenprintButton.setText("");
            kitchenprintButton.setBackgroundResource(R.drawable.ab_imagebutton_recall_kitchenprint);
        } else {
            kitchenprintButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    kitchenprintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        checkprintButton = (Button)findViewById(R.id.checkprintButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            checkprintButton.setText("");
            checkprintButton.setBackgroundResource(R.drawable.ab_imagebutton_recall_checkprint);
        } else {
            checkprintButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    checkprintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        labelprintButton = (Button)findViewById(R.id.labelprintButton);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            labelprintButton.setText("");
            labelprintButton.setBackgroundResource(R.drawable.ab_imagebutton_recall_labelprint);
        } else {
            labelprintButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    labelprintButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        recallRecallButton.setOnClickListener(recallBtnClickListener);
        recallDeleteButton.setOnClickListener(recallBtnClickListener);
        kitchenprintButton.setOnClickListener(recallBtnClickListener);
        checkprintButton.setOnClickListener(recallBtnClickListener);
        labelprintButton.setOnClickListener(recallBtnClickListener);
        /***********************************************************************************************************/

        setSearchRecall();

        // 초기화
        setInit();
    }

    View.OnClickListener recallBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.recallCloseBtn : {
                    finish();
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }
                    break;
                }
                case R.id.recallRecallButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedHoldCode)) {
                        GlobalMemberValues.displayDialog(context, "Recall Hold List", "Choose a hold list", "Close");
                        return;
                    }

                    if ((mActivity != null) && (!mActivity.isFinishing())) {
                        new AlertDialog.Builder(context)
                                .setTitle("Recall Confirmation")
                                .setMessage("Are you sure you want to recall?\nThe current list will be on hold")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                setRecallHoldList();
                                                // 리스트뷰에 옮긴 후에 창을 닫는다.
                                                finish();
                                                if (GlobalMemberValues.isUseFadeInOut()) {
                                                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                                }
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                })
                                .show();
                    }

                    break;
                }
                case R.id.recallDeleteButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedHoldCode)) {
                        GlobalMemberValues.displayDialog(context, "Delete Hold List", "Choose a hold list", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("RECALL")
                            .setMessage("Are you sure you want to delete?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                            setDeleteHoldList();

                                            if (!GlobalMemberValues.checkEmployeePermission(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx, GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName, "<20>")){
//                                                GlobalMemberValues.displayDialog(context, "Warning", "You do not have permission", "Close");
                                                // adminpassword.
                                                Intent adminPasswordIntent = new Intent(MainActivity.mContext.getApplicationContext(), AdminPassword.class);
                                                // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                                                adminPasswordIntent.putExtra("openClassMethod", "recall_delete_order");
                                                // -------------------------------------------------------------------------------------
                                                insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                                                mActivity.startActivityForResult(adminPasswordIntent,REQUESTCODE_RECALL);
                                                if (GlobalMemberValues.isUseFadeInOut()) {
                                                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                                }
                                                return;
                                            } else {
                                                setDeleteHoldList();
                                            }

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
                case R.id.kitchenprintButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedHoldCode)) {
                        GlobalMemberValues.displayDialog(context, "Kitchen Printing", "Choose a hold list", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("RECALL")
                            .setMessage("Are you sure you want to print in the kitchen?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                mKitchenPrintOnRecall = "Y";
                                                mKitchenReprint = "Y";
                                                GlobalMemberValues.phoneorderPrinting(mSelectedHoldCode, "K", "");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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
                case R.id.checkprintButton : {
                    if (GlobalMemberValues.isStrEmpty(mSelectedHoldCode)) {
                        GlobalMemberValues.displayDialog(context, "Check Printing", "Choose a hold list", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("RECALL")
                            .setMessage("Are you sure you want to print on the receipt printer?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                //mKitchenPrintOnRecall = "Y";
                                                GlobalMemberValues.phoneorderPrinting(mSelectedHoldCode, "R", "");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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
                case R.id.labelprintButton: {
                    if (GlobalMemberValues.isStrEmpty(mSelectedHoldCode)) {
                        GlobalMemberValues.displayDialog(context, "Label Printing", "Choose a hold list", "Close");
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("RECALL")
                            .setMessage("Are you sure you want to print in the label?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                mKitchenPrintOnRecall = "Y";
                                                GlobalMemberValues.phoneorderPrinting_label(mSelectedHoldCode, "K", "");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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

    public void setRecallHoldList() {
        // 현재 리스트를 Hold 처리한다.
        CommandButton.setHoldSales("");

        ResultSet tempSaleCartCursor;
        String maxIdxAfterDbInsert = "";
        String strDeleteTempSaleCartQuery = "";
        String tempCustomerId = "";
        String tempSaleCartQuery = "";
        tempSaleCartQuery = "select sQty, holdcode, '" + GlobalMemberValues.STORE_INDEX + "', '" + GlobalMemberValues.STATION_CODE + "', " +
                " midx, svcIdx, " +
                " svcName, svcFileName, svcFilePath, " +
                " sPrice, sPrice, '', '', " +
                " sCommissionRatio, sCommissionRatioType, sPointRatio,  " +
                " svcPositionNo, svcSetMenuYN, " +
                " customerId, customerName, customerPhoneNo,  " +
                " saveType, empIdx, empName, quickSaleYN, " +
                " svcCategoryName, " +
                " giftcardNumber, giftcardSavePrice, " +
                " idx, svcCategoryColor, taxExempt, " +
                " reservationCode, " +
                " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                " modifieridx, modifiercode, " +
                " memoToKitchen, " +
                " sPriceAmount, sTaxAmount, sTotalAmount, " +
                " selectedDcExtraType, dcextratype, dcextravalue, discountbuttonname " +            // Discount 관련처리
                " from temp_salecart " +
                " where holdcode = '" + mSelectedHoldCode + "' " +
                " order by idx asc";
        GlobalMemberValues.logWrite("recallholdlog", "recallSQLQuery : " + tempSaleCartQuery + "\n");
        int tempCount = 0;
        tempSaleCartCursor = MssqlDatabase.getResultSetValue(tempSaleCartQuery);

        // Discount 관련처리 -----------------------------------------------------------------------------------
        int tempDCExtraKindCnt = 0;
        int tempDCExtraTypeCnt = 0;
        String dcextra_cartidx = "";
        String dcextra_kind = "";
        String dcextra_type = "";
        String dcextra_value = "";
        String dcextra_checkeditem = "";
        String dcextra_discountbuttonname = "";
        // ---------------------------------------------------------------------------------------------------

        try {
            while (tempSaleCartCursor.next()) {
                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String temp_optionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,32), 1);
                String temp_optionprice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,33), 1), "2");
                String temp_additionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,34), 1);
                String temp_additionalprice1 = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,35), 1), "2");
                String temp_additionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,36), 1);
                String temp_additionalprice2 = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,37), 1), "2");

                String temp_modifieridx = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,38), 1);
                String temp_modifiercode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,39), 1);

                String temp_memotokitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,40), 1);

                String temp_sPriceAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,41), 1);
                String temp_sTaxAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,42), 1);
                String temp_sTotalAmount = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,43), 1);

                // Discount 관련처리
                String temp_selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,44), 1);
                String temp_dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,45), 1);
                String temp_dcextravalue = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,46), 1);
                String temp_discountbuttonname = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,47), 1);




                double temp_optionprice_dbl = GlobalMemberValues.getDoubleAtString(temp_optionprice);
                double temp_additionalprice1_dbl = GlobalMemberValues.getDoubleAtString(temp_additionalprice1);
                double temp_additionalprice2_dbl = GlobalMemberValues.getDoubleAtString(temp_additionalprice2);

                String temp_sPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,9), 1), "2");
                double temp_sPrice_dbl = GlobalMemberValues.getDoubleAtString(temp_sPrice);

                double temp_sPrice_final_dbl = temp_sPrice_dbl - (temp_optionprice_dbl + temp_additionalprice1_dbl + temp_additionalprice2_dbl);

                MainMiddleService.mModifierCode = temp_modifiercode;
                MainMiddleService.mModifierIdx = temp_modifieridx;
                MainMiddleService.mOptionTxt = temp_optionTxt;
                MainMiddleService.mOptionPrice = temp_optionprice;
                MainMiddleService.mAdditionalTxt1 = temp_additionalTxt1;
                MainMiddleService.mAdditionalprice1 = temp_additionalprice1;
                MainMiddleService.mAdditionalTxt2 = temp_additionalTxt2;
                MainMiddleService.mAdditionalprice2 = temp_additionalprice2;

                MainMiddleService.mMemoToKitchen = temp_memotokitchen;

                GlobalMemberValues.logWrite("recalltajjlog", "temp_sPrice_final_dbl : " + temp_sPrice_final_dbl + "\n");
                GlobalMemberValues.logWrite("recalltajjlog", "temp_optionprice : " + temp_optionprice + "\n");
                GlobalMemberValues.logWrite("recalltajjlog", "temp_additionalprice1 : " + temp_additionalprice1 + "\n");
                GlobalMemberValues.logWrite("recalltajjlog", "temp_additionalprice2 : " + temp_additionalprice2 + "\n");

                String paramsString[] = {
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,1), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,2), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,3), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,4), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,5), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,6), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,7), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,8), 1),
                        (temp_sPrice_final_dbl + ""),
                        (temp_sPrice_final_dbl + ""),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,11), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,12), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,13), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,14), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,15), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,16), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,17), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,18), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,19), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,20), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,21), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,22), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,23), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,24), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,25), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,26), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,27), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,28), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,29), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,30), 1),
                        GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,31), 1)
                };

                // common gratuity 관련
                GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                maxIdxAfterDbInsert = MainMiddleService.insertTempSaleCart(paramsString);

                // common gratuity 관련
                GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                GlobalMemberValues.logWrite("RECALLTAXEXEMPT", "taxexempt = " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,30), 1) + "\n");

                /**
                 if (!GlobalMemberValues.isStrEmpty(maxIdxAfterDbInsert)) {        // DB insert 후 MAXIDX 가 리턴되었을 때
                 // MainMiddleService.insertTempSaleCart 구문 실행전에 먼저 해당 데이터를 삭제처리한다.
                 strDeleteTempSaleCartQuery = "delete froom temp_salecart " +
                 " where idx = '" + GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(26), 1) + "' ";
                 GlobalMemberValues.logWrite("strDeleteTempSaleCartQuery", "deleteQuery : " + strDeleteTempSaleCartQuery + "\n");
                 String returnCode = "";
                 returnCode = dbInit.dbExecuteWriteReturnValue(strDeleteTempSaleCartQuery);
                 if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                 maxIdxAfterDbInsert = MainMiddleService.insertTempSaleCart(paramsString);
                 }
                 }
                 **/

                // Discount 관련처리 -----------------------------------------------------------------------------------
                GlobalMemberValues.logWrite("recalldclogjjj",
                        "temp_dcextratype : " + temp_dcextratype + "\n");
                if (!GlobalMemberValues.isStrEmpty(temp_dcextratype)) {
                    if (temp_dcextratype.equals("%") || temp_dcextratype.equals("$")) {
//                    // temp_salecart 의 idx
//                    if (tempDCExtraTypeCnt > 0) {
//                        dcextra_cartidx += "-JJJ-";
//                    }
//                    dcextra_cartidx += maxIdxAfterDbInsert;

                        // dc / extra 여부
                        if (tempDCExtraTypeCnt > 0) {
                            dcextra_kind += "-JJJ-";
                        }
                        dcextra_kind += temp_selectedDcExtraType;

                        // $ 또는 % 여부
                        if (tempDCExtraTypeCnt > 0) {
                            dcextra_type += "-JJJ-";
                        }
                        dcextra_type += temp_dcextratype;

                        // dc 또는 extra 금액 또는 비율
                        if (tempDCExtraTypeCnt > 0) {
                            dcextra_value += "-JJJ-";
                        }
                        dcextra_value += temp_dcextravalue;

                        // 선택된 메뉴아이템 순서
                        if (tempDCExtraTypeCnt > 0) {
                            dcextra_checkeditem += "-JJJ-";
                        }
                        dcextra_checkeditem += tempCount;

                        // discount button name
                        if (tempDCExtraTypeCnt > 0) {
                            dcextra_discountbuttonname += "-JJJ-";
                        }
                        dcextra_discountbuttonname += temp_discountbuttonname;


                        tempDCExtraTypeCnt++;
                    }
                }
                // -------------------------------------------------------------------------------------------------


                if (tempCount == 0) {
                    tempCustomerId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,18), 1);
                }
                tempCount++;
            }
        } catch (Exception e){

        }

        MainMiddleService.mHoldCode = mSelectedHoldCode;
        GlobalMemberValues.logWrite("CheckHoldCode", "recall 에서 넘겨준 holdcode : " + MainMiddleService.mHoldCode + "\n");

        // 고객정보 배치
        if (GlobalMemberValues.isStrEmpty(tempCustomerId)) {
            tempCustomerId = dbInit.dbExecuteReadReturnString(
                    "select customerId from temp_salecart_deliveryinfo where holdcode = '" + mSelectedHoldCode + "' "
            );
        }
        if (!GlobalMemberValues.isStrEmpty(tempCustomerId)) {
            setCustomerInfoAtOutClass(tempCustomerId, dbInit);
        }

        // Discount 관련처리 ------------------------------------------------------------------------------------------
        // tempCount 와 tempDCExtraTypeCnt 의 값이 같으면 All Discount, 다르면 each discount
        if (tempDCExtraTypeCnt > 0) {
            // all discount 여부 확인
            String tempSql = " select count(*) from temp_salecart where holdcode = '" + mSelectedHoldCode + "'" +
                    " and selectedDcExtraAllEach = 'ALL' ";
            int allDCCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(tempSql));
            if (allDCCnt > 0) {              // All discount
                String discountName = MssqlDatabase.getResultSetValueToString(
                        " select discountbuttonname from temp_salecart where holdcode = '" + mSelectedHoldCode + "' "
                );
                String dcextratype = MssqlDatabase.getResultSetValueToString(
                        " select dcextratype from temp_salecart where holdcode = '" + mSelectedHoldCode + "' "
                );
                String dcextravalue = MssqlDatabase.getResultSetValueToString(
                        " select dcextravalue from temp_salecart where holdcode = '" + mSelectedHoldCode + "' "
                );

                if (Discount.discountDiscountExtraSwitch == null) {
                    MainActivity.doDiscountOpen();
                }

                Discount.discountAllEachSwitch.setChecked(true);            // all, each 여부

                if (dcextratype.equals("%")) {
                    Discount.discountRateTypeButton.setChecked(true);
                } else {
                    Discount.discountRateTypeButton.setChecked(false);
                }
                Discount.discountRateEditText.setText(dcextravalue);
                Discount.applyCoupon(discountName);
            } else {                                             // Each discount
                if (!GlobalMemberValues.isStrEmpty(dcextra_value)) {
                    GlobalMemberValues.logWrite("recalldclogjjj",
                            "dcextra_checkeditem : " + dcextra_checkeditem + "\n");

                    MainMiddleService.setChecked(GlobalMemberValues.getIntAtString(dcextra_checkeditem));

                    String dcextra_checkeditem_arr[] = dcextra_checkeditem.split("-JJJ-");
                    String dcextra_kind_arr[] = dcextra_kind.split("-JJJ-");
                    String dcextra_type_arr[] = dcextra_type.split("-JJJ-");
                    String dcextra_value_arr[] = dcextra_value.split("-JJJ-");
                    String dcextra_discountbuttonname_arr[] = dcextra_discountbuttonname.split("-JJJ-");
                    for (int i = 0; i < dcextra_checkeditem_arr.length; i++) {
                        GlobalMemberValues.logWrite("recalldclogjjj",
                                "check i : " + dcextra_checkeditem_arr[i] + "\n");
                        //MainMiddleService.setChecked(GlobalMemberValues.getIntAtString(dcextra_checkeditem_arr[i]));

                        if (Discount.discountDiscountExtraSwitch == null) {
                            MainActivity.doDiscountOpen();
                        }

                        String tempDcExtraType = "DC";
                        String tempDcExtraStr = "";
                        String tempMSaveType = "";          // TemporarySaleCart mSaveType 에 저장할 때 DC / Extra 여부 저장
                        if (dcextra_kind_arr[i].equals("DC")) {
                            //Discount.discountDiscountExtraSwitch.setChecked(true);
                            tempDcExtraType = "DC";
                            tempDcExtraStr = "DISCOUNT";
                            tempMSaveType = "8";
                        } else {
                            //Discount.discountDiscountExtraSwitch.setChecked(false);
                            tempDcExtraType = "EX";
                            tempDcExtraStr = "EXTRA";
                            tempMSaveType = "9";
                        }

//                        if (dcextra_type_arr[i].equals("%")) {
//                            Discount.discountRateTypeButton.setChecked(true);
//                        } else {
//                            Discount.discountRateTypeButton.setChecked(false);
//                        }
//
//                        Discount.discountAllEachSwitch.setChecked(false);            // all, each 여부
//
//                        Discount.discountRateEditText.setText(dcextra_value_arr[i]);


                        String tempRateType = "";
                        if (dcextra_type_arr[i].equals("%")) {
                            tempRateType = "%";
                        } else {
                            tempRateType = "$";
                        }

                        int tempPosition = GlobalMemberValues.getIntAtString(dcextra_checkeditem_arr[i]) + i;

                        //Discount.setDiscountExtraPrev(dcextra_value_arr[i], dcextra_type_arr[i], tempDcExtraType, tempDcExtraStr, tempMSaveType, "EACH");
                        Discount.setDiscountExtraItem(dcextra_value_arr[i], tempRateType, tempDcExtraType, tempDcExtraStr,
                                tempMSaveType, "EACH", tempPosition);

                    }
                }

            }
        }
        // --------------------------------------------------------------------------------------------------------
    }

    public void setDeleteHoldList() {
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";
        // DB 쿼리저장용 백터 객체 생성
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strDeleteQuery = "";

        strDeleteQuery = "delete from temp_salecart where holdcode = '" + mSelectedHoldCode + "' ";
        strInsertQueryVec.addElement(strDeleteQuery);

        strDeleteQuery = "delete from temp_salecart_deliveryinfo where holdcode = '" + mSelectedHoldCode + "' ";
        strInsertQueryVec.addElement(strDeleteQuery);

        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
            return;
        } else {
            setSearchRecall();
        }
    }

    public void setSearchRecall() {
        setRecall();
    }

    public void setRecall() {
        ResultSet tempSaleCartCursor;
        String strSqlQueryCust = "";
        String tempCustId, tempCustName, tempCustPhoneNo;


        String tempSearchWord = "";
        String addQuery = "";
        if (!GlobalMemberValues.isStrEmpty(tempSearchWord)) {
            addQuery = " and holdcode in (select holdcode from temp_salecart_deliveryinfo " +
                    " where customerName like '" + tempSearchWord + "' or customerPhone like '%" + tempSearchWord + "%' ) ";
        }
        String strSqlQueryGroupBy = "select holdcode, sum(sTaxAmount), sum(sTotalAmount), customerId, customerName, customerPhoneNo " +
                " from temp_salecart " +
                " where not(holdcode = '" + MainMiddleService.mHoldCode + "') and (tableidx = '' or tableidx is null) " +
                addQuery +
                " group by holdcode order by idx asc";


//        String strSqlQueryGroupBy = "select holdcode, sum(sTaxAmount), sum(sTotalAmount) " +
//                " from temp_salecart " +
//                " where not(holdcode = '" + MainMiddleService.mHoldCode + "') and (tableidx = '' or tableidx is null) " +
//                " group by holdcode";
//        GlobalMemberValues.logWrite("recallSQLQuery2", "strSqlQueryGroupBy : " + strSqlQueryGroupBy + "\n");

        // 세일정보 가져오기 (GetDataAtSQLite 클래스의 getrecall 메소드를 통해 가져온다)
        tempSaleCartCursor = MssqlDatabase.getResultSetValue(strSqlQueryGroupBy);

        // ScrollView 에 속한 첫번째 LinearLayout 객체
        recallSaleListLinearLayout = (LinearLayout)findViewById(R.id.recallSaleListLinearLayout);

        // 뷰 추가전 먼저 초기화(삭제)한다.
        recallSaleListLinearLayout.removeAllViews();

        // LinearLayout 파라미터 설정
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(0, 0, 0, 10);
        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);

        int custCount = 0;
        try {
            while (tempSaleCartCursor.next()) {
                String dbHoldCode = GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor,0);

                // 세일코드값이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(dbHoldCode, " ", ""))) {
                    // 고객정보 추출
                    tempCustId = "";
                    tempCustName = "";
                    tempCustPhoneNo = "";
                    strSqlQueryCust = " select customerId, customerName, customerPhoneNo from temp_salecart where holdcode = N'" + dbHoldCode + "' ";
                    ResultSet tempCustRS = MssqlDatabase.getResultSetValue(strSqlQueryCust);
                    while (tempCustRS.next()) {
                        tempCustId = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCustRS,0), 1);
                        tempCustName = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCustRS,1), 1);
                        tempCustPhoneNo = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempCustRS,2), 1);
                    }
                    tempCustRS.close();

                    // LinearLayout 객체 생성
                    LinearLayout newLn = new LinearLayout(this);
                    newLn.setLayoutParams(newLnLayoutParams);
                    newLn.setOrientation(LinearLayout.VERTICAL);
                    //newLn.setBackgroundColor(Color.parseColor(GlobalMemberValues.RECALLLISTBGCOLOR));
                    newLn.setBackgroundResource(R.drawable.button_selector_recall_listbg);
                    newLn.setTag(dbHoldCode);

                    newLn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mSelectedHoldCode == v.getTag().toString()) {
                                mSelectedLn = null;
                                mSelectedHoldCode = "";
                                v.setBackgroundResource(R.drawable.roundlayout_recall_listbg);
                            } else {
                                // 앞서 선택한 LinearLayout 객체의 배경색을 초기화
                                if (mSelectedLn != null) {
                                    mSelectedLn.setBackgroundResource(R.drawable.roundlayout_recall_listbg);
                                }
                                mSelectedLn = (LinearLayout)v;
                                mSelectedHoldCode = v.getTag().toString();
                                v.setBackgroundResource(R.drawable.roundlayout_button_selector_gray);
                            }
                        }
                    });

                    String dbCustomerName = MssqlDatabase.getResultSetValueToString(
                            "select customerName from temp_salecart_deliveryinfo where holdcode = '" + dbHoldCode + "' ");
                    String tempCustomerName = "";
                    if (!GlobalMemberValues.isStrEmpty(dbCustomerName)) {
                        tempCustomerName = dbCustomerName;
                    } else {
                        tempCustomerName = tempCustName;
                    }

                    String tempCustomerPhoneNum = "";
//                String dbCustomerPhoneNum = dbInit.dbExecuteReadReturnString(
//                        "select customerPhone from temp_salecart_deliveryinfo where holdcode = '" + dbHoldCode + "' ");
//                if (!GlobalMemberValues.isStrEmpty(dbCustomerPhoneNum)) {
//                    tempCustomerPhoneNum = dbCustomerPhoneNum;
//                } else {
//                    tempCustomerPhoneNum = GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(5), 1);
//                }
                    tempCustomerPhoneNum = tempCustPhoneNo;

                    String dbCustomerDeliveryTakeaway = MssqlDatabase.getResultSetValueToString(
                            "select deliverytakeaway from temp_salecart_deliveryinfo where holdcode = '" + dbHoldCode + "' ");
                    String tempCustomerDeliveryTakeaway = "";
                    String tempCustomerDeliveryTakeawayTxColor = "#3047b4";
                    if (dbCustomerDeliveryTakeaway.equals("D")) {
                        tempCustomerDeliveryTakeaway = "Delivery";
                        tempCustomerDeliveryTakeawayTxColor = "#3047b4";
                    } else {
                        if (dbCustomerDeliveryTakeaway.equals("H")) {
                            tempCustomerDeliveryTakeaway = "Here";
                            tempCustomerDeliveryTakeawayTxColor = "#31a439";
                        } else {
                            tempCustomerDeliveryTakeaway = "Pick Up";
                            tempCustomerDeliveryTakeawayTxColor = "#e91b58";
                        }
                    }

                    String tempCustomerInfo = "";
                    if (!GlobalMemberValues.isStrEmpty(tempCustId)) {
                        tempCustomerInfo = "ID : " + tempCustId;
                    } else {
                        tempCustomerInfo = "Customer : WALK IN";
                    }

                    // jihun 0918 add
                    String dbOrderNumber = MssqlDatabase.getResultSetValueToString(
                            "select phoneordernumber from temp_salecart_deliveryinfo where holdcode = '" + dbHoldCode + "' ");
                    String tempdbOrderNumber = "";
                    if (!GlobalMemberValues.isStrEmpty(dbOrderNumber)) {
                        tempdbOrderNumber = dbOrderNumber;
                    }

                    // LinearLayout 파라미터 설정
                    LinearLayout.LayoutParams customerLnLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    customerLnLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 하나의 TextView 만 있을 경우)
                    LinearLayout.LayoutParams tipNewTvLayoutParams1
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    tipNewTvLayoutParams1.setMargins(0, 0, 0, 0);

                    // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
                    LinearLayout.LayoutParams tipNewTvLayoutParams2
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tipNewTvLayoutParams2.setMargins(0, 0, 0, 0);

                    // Phone Order 여부 및 키친프린트 했는지 여부 ------------------------------------------------------------------
                    if (GlobalMemberValues.getPhoneOrderYN(dbHoldCode).equals("Y")) {
                        String tempKitchenPrinted = "";
                        String tempLnBg = "#975366";
                        String tempTextColor = GlobalMemberValues.RECALLLISTBGCOLOR;

                        String tempLabelPrinted = "";
                        String tempTextColor_label = GlobalMemberValues.RECALLLISTBGCOLOR;

                        if (GlobalMemberValues.getPhoneorderKitchenPrintedYN(dbHoldCode).equals("Y")) {
                            tempKitchenPrinted = "Printed in the kitchen";
                            tempLnBg = "#0054d5";
                            tempTextColor = "#ffffff";
                            tempTextColor_label = "#ffffff";
                        } else {
                            tempKitchenPrinted = "Not Printed kitchen";
                            tempLnBg = "#f0f0f0";
                            tempTextColor = GlobalMemberValues.RECALLLISTBGCOLOR;
                            tempTextColor_label = GlobalMemberValues.RECALLLISTBGCOLOR;
                        }

                        if (GlobalMemberValues.getPhoneorderLabelPrintedYN(dbHoldCode).equals("Y")) {
                            tempLabelPrinted = "Printed in the Label";
                        } else {
                            tempLabelPrinted = "Not Printed label";
                        }

                        LinearLayout customerValueLn2 = new LinearLayout(this);
                        customerValueLn2.setLayoutParams(customerLnLayoutParams1);
                        customerValueLn2.setOrientation(LinearLayout.HORIZONTAL);
                        customerValueLn2.setBackgroundColor(Color.parseColor(tempLnBg));

                        // jihun 091820
                        // Order Number
                        TextView orderNumberTv = new TextView(this);
                        orderNumberTv.setLayoutParams(tipNewTvLayoutParams2);
                        //holdecodeNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 6);
                        orderNumberTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        orderNumberTv.setPadding(15, 5, 10, 5);
                        orderNumberTv.setText("PHONE_" + tempdbOrderNumber + "  /");
                        //holdecodeNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                        orderNumberTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                        orderNumberTv.setTextColor(Color.parseColor(tempTextColor));
                        orderNumberTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f));
                        customerValueLn2.addView(orderNumberTv);

                        // Hold Code
                        TextView holdecodeNewTv = new TextView(this);
                        holdecodeNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        //holdecodeNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 6);
                        holdecodeNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        holdecodeNewTv.setPadding(15, 5, 10, 5);
                        holdecodeNewTv.setText(dbHoldCode);
                        //holdecodeNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                        holdecodeNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                        holdecodeNewTv.setTextColor(Color.parseColor(tempTextColor));
                        holdecodeNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
                        customerValueLn2.addView(holdecodeNewTv);

                        // Phone Order 여부
                        TextView phoneorderynNewTv = new TextView(this);
                        phoneorderynNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        //phoneorderynNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 6);
                        phoneorderynNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        phoneorderynNewTv.setPadding(15, 5, 10, 5);
                        phoneorderynNewTv.setText("Phone Order");
                        //phoneorderynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                        phoneorderynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                        phoneorderynNewTv.setTextColor(Color.parseColor(tempTextColor));
                        phoneorderynNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        customerValueLn2.addView(phoneorderynNewTv);

                        LinearLayout printedYN_ln = new LinearLayout(this);
                        printedYN_ln.setLayoutParams(tipNewTvLayoutParams2);
                        printedYN_ln.setOrientation(LinearLayout.VERTICAL);

                        // Phone Order 키친프린트 했는지 여부
                        TextView phoneorderkitchenprintedynNewTv = new TextView(this);
                        phoneorderkitchenprintedynNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        //phoneorderkitchenprintedynNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 8);
                        phoneorderkitchenprintedynNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        phoneorderkitchenprintedynNewTv.setPadding(5, 5, 15, 5);
                        phoneorderkitchenprintedynNewTv.setText(tempKitchenPrinted);
                        //phoneorderkitchenprintedynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                        phoneorderkitchenprintedynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                        phoneorderkitchenprintedynNewTv.setTextColor(Color.parseColor(tempTextColor));
                        if (GlobalMemberValues.getPhoneorderKitchenPrintedYN(dbHoldCode).equals("N")) {
                            phoneorderkitchenprintedynNewTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                        }
                        phoneorderkitchenprintedynNewTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));


                        // Phone Order 라벨프린트 했는지 여부
                        TextView phoneorderlabelprintedynNewTv = new TextView(this);
                        phoneorderlabelprintedynNewTv.setLayoutParams(tipNewTvLayoutParams2);
                        //phoneorderkitchenprintedynNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 8);
                        phoneorderlabelprintedynNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        phoneorderlabelprintedynNewTv.setPadding(5, 5, 15, 5);
                        phoneorderlabelprintedynNewTv.setText(tempLabelPrinted);
                        //phoneorderkitchenprintedynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                        phoneorderlabelprintedynNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                        phoneorderlabelprintedynNewTv.setTextColor(Color.parseColor(tempTextColor_label));
                        if (GlobalMemberValues.getPhoneorderLabelPrintedYN(dbHoldCode).equals("N")) {
                            phoneorderlabelprintedynNewTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                        }
                        phoneorderlabelprintedynNewTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

                        printedYN_ln.addView(phoneorderkitchenprintedynNewTv);
                        printedYN_ln.addView(phoneorderlabelprintedynNewTv);
                        customerValueLn2.addView(printedYN_ln);

                        newLn.addView(customerValueLn2);
                    }
                    // ---------------------------------------------------------------------------------------------------------

                    // 고객정보 내용 보여주는 레이아웃 ------------------------------------------------------------------------------
                    LinearLayout customerValueLn1 = new LinearLayout(this);
                    customerValueLn1.setLayoutParams(customerLnLayoutParams1);
                    customerValueLn1.setOrientation(LinearLayout.HORIZONTAL);
                    //customerValueLn1.setBackgroundColor(Color.parseColor(tempTitleBackgroundColor));

                    // 고객 아이디
                    TextView customerIdValueNewTv = new TextView(this);
                    customerIdValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerIdValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 6);
                    customerIdValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerIdValueNewTv.setPadding(15, 5, 5, 5);
                    customerIdValueNewTv.setText(tempCustomerInfo);
                    //customerIdValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                    customerIdValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                    customerIdValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR_CUSTOMER));
                    customerIdValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    customerValueLn1.addView(customerIdValueNewTv);

                    // 고객 이름
                    TextView customerNameValueNewTv = new TextView(this);
                    customerNameValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerNameValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 8);
                    customerNameValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerNameValueNewTv.setPadding(5, 5, 5, 5);
                    customerNameValueNewTv.setText("Name : " + tempCustomerName);
                    //customerNameValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                    customerNameValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                    customerNameValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR_CUSTOMER));
                    customerNameValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.3f));
                    customerValueLn1.addView(customerNameValueNewTv);

                    // 전화번호
                    TextView customerPhoneValueNewTv = new TextView(this);
                    customerPhoneValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerPhoneValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 13);
                    customerPhoneValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    customerPhoneValueNewTv.setPadding(5, 5, 5, 5);
                    customerPhoneValueNewTv.setText("Phone# : " + tempCustomerPhoneNum);
                    //customerPhoneValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                    customerPhoneValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                    customerPhoneValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR_CUSTOMER));
                    customerPhoneValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    customerValueLn1.addView(customerPhoneValueNewTv);

                    // Delivery, Takeaway 정보
                    TextView customerDeliveryTakeawayValueNewTv = new TextView(this);
                    customerDeliveryTakeawayValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                    customerDeliveryTakeawayValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 13);
                    customerDeliveryTakeawayValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    customerDeliveryTakeawayValueNewTv.setPadding(5, 5, 15, 5);
                    customerDeliveryTakeawayValueNewTv.setText(tempCustomerDeliveryTakeaway);
                    //customerDeliveryTakeawayValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + 13);
                    customerDeliveryTakeawayValueNewTv.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLCUSTOMERINFOTTEXTSIZE);
                    customerDeliveryTakeawayValueNewTv.setTextColor(Color.parseColor(tempCustomerDeliveryTakeawayTxColor));
                    customerDeliveryTakeawayValueNewTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
                    customerValueLn1.addView(customerDeliveryTakeawayValueNewTv);

                    // 고객 코드
                    /**
                     TextView customerPosCodeTitleNewTv = new TextView(this);
                     customerPosCodeTitleNewTv.setLayoutParams(tipNewTvLayoutParams2);
                     customerPosCodeTitleNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 22);
                     customerPosCodeTitleNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                     customerPosCodeTitleNewTv.setPadding(5, 5, 5, 5);
                     customerPosCodeTitleNewTv.setText("Code : ");
                     customerPosCodeTitleNewTv.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                     customerValueLn1.addView(customerPosCodeTitleNewTv);

                     TextView customerPosCodeValueNewTv = new TextView(this);
                     customerPosCodeValueNewTv.setLayoutParams(tipNewTvLayoutParams2);
                     customerPosCodeValueNewTv.setWidth(GlobalMemberValues.getDisplayWidth(this) / 10);
                     customerPosCodeValueNewTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                     customerPosCodeValueNewTv.setPadding(5, 5, 5, 5);
                     customerPosCodeValueNewTv.setText(GlobalMemberValues.getDBTextAfterChecked(tempSaleCartCursor.getString(13), 1));
                     customerPosCodeValueNewTv.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                     customerValueLn1.addView(customerPosCodeValueNewTv);
                     **/

                    newLn.addView(customerValueLn1);
                    // ---------------------------------------------------------------------------------------------------------

                    // ------------------------------------------------------------------------------------------------------------------

                    String tempSaleCartDetailQuery = "";
                    // salon_sales_detail 데이터 출력 ---------------------------------------------------------------------------------------------------
                    tempSaleCartDetailQuery = "select idx, holdcode, svcIdx, svcName, svcOrgPrice, svcSetMenuYN, sPrice, sQty, " +
                            " sPriceAmount, sTaxAmount, sTotalAmount, sSaleYN, customerId, customerName, customerPhoneNo, " +
                            " saveType, empIdx, empName, quickSaleYN, sPriceBalAmount, " +
                            " optionTxt, optionprice, additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, " +
                            " sTax , memoToKitchen , " +
                            " selectedDcExtraAllEach , selectedDcExtraType, dcextratype, dcextravalue, selectedDcExtraPrice" +    // 28,29,30,31,32
                            " from temp_salecart " +
                            " where holdcode = '" + dbHoldCode + "' " +
                            " order by idx asc";
                    GlobalMemberValues.logWrite("recallSQLQuery", "recallSQLQuery : " + tempSaleCartDetailQuery + "\n");

                    ResultSet detailRs = null;
                    detailRs = MssqlDatabase.getResultSetValue(tempSaleCartDetailQuery);
                    while (detailRs.next()) {
                        double tempSPrice = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,6), 1), "2")
                        );
                        double tempSQty = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,7), 1));
                        double tempSTax = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,26), 1), "2")
                        );
                        //double tempSPriceAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((tempSPrice * tempSQty) + "", "2"));
                        //double tempSTaxAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((tempSTax * tempSQty) + "", "2"));
                        //double tempTotalAmount = tempSPriceAmount + tempSTaxAmount;

                        double tempSPriceAmount = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,8), 1), "2")
                        );
                        double tempSTaxAmount = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,9), 1), "2")
                        );
                        double tempTotalAmount = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,10), 1), "2")
                        );

                        LinearLayout subNewLn = new LinearLayout(this);
                        subNewLn.setLayoutParams(subNewLnLayoutParams);
                        String salesCodeAndIdx = dbHoldCode + "||" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,0), 1);
                        subNewLn.setTag(salesCodeAndIdx);
                        //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(detailRs.getString(13), 1));
                        subNewLn.setOrientation(LinearLayout.HORIZONTAL);
                        //subNewLn.setBackgroundColor(Color.parseColor(tempSaleCartDetailLnBackGroundColor));
                        // Kind (SaveType 0 : 서비스    1 : Product     2 : GiftCard)
                        TextView subNewTv1 = new TextView(this);
                        subNewTv1.setLayoutParams(subNewTvLayoutParams);
                        subNewTv1.setWidth(GlobalMemberValues.getDisplayWidth(this) / 27);
                        subNewTv1.setGravity(Gravity.CENTER);
                        String subSaveType = GlobalMemberValues.getSaveType(
                                GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,15), 1));
                        subNewTv1.setText(subSaveType);
                        subNewTv1.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        //subNewTv1.setBackgroundResource(R.drawable.xml_border);
                        subNewLn.addView(subNewTv1);

                        // 서비스 이름
                        TextView subNewTv2 = new TextView(this);
                        subNewTv2.setLayoutParams(subNewTvLayoutParams);
                        subNewTv2.setWidth(GlobalMemberValues.getDisplayWidth(this) / 5);
                        subNewTv2.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,3), 1));
                        subNewTv2.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        subNewLn.addView(subNewTv2);

                        // 수량
                        TextView subNewTv3 = new TextView(this);
                        subNewTv3.setLayoutParams(subNewTvLayoutParams);
                        subNewTv3.setWidth(GlobalMemberValues.getDisplayWidth(this) / 23);
                        subNewTv3.setGravity(Gravity.CENTER);
                        subNewTv3.setPadding(5, 0, 5, 0);
                        subNewTv3.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,7), 1));
                        subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        subNewLn.addView(subNewTv3);

                        // Amount (세금미포함)
                        TextView subNewTv4 = new TextView(this);
                        subNewTv4.setLayoutParams(subNewTvLayoutParams);
                        subNewTv4.setWidth(GlobalMemberValues.getDisplayWidth(this) / 18);
                        subNewTv4.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        subNewTv4.setPadding(0, 0, 5, 0);
                        subNewTv4.setText(GlobalMemberValues.getCommaStringForDouble(tempSPriceAmount + ""));
                        subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        subNewLn.addView(subNewTv4);

                        // Employee
                        TextView subNewTv5 = new TextView(this);
                        subNewTv5.setLayoutParams(subNewTvLayoutParams);
                        subNewTv5.setWidth(GlobalMemberValues.getDisplayWidth(this) / 9);
                        subNewTv5.setGravity(Gravity.CENTER);
                        subNewTv5.setPadding(5, 0, 5, 0);
                        subNewTv5.setText(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,17), 1));
                        subNewTv5.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv5.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        subNewLn.addView(subNewTv5);

                        // totalAmount
                        TextView subNewTv6 = new TextView(this);
                        subNewTv6.setLayoutParams(subNewTvLayoutParams);
                        subNewTv6.setWidth(GlobalMemberValues.getDisplayWidth(this) / 16);
                        subNewTv6.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                        subNewTv6.setPadding(0, 0, 5, 0);
                        subNewTv6.setText(GlobalMemberValues.getCommaStringForDouble(tempTotalAmount + ""));
                        subNewTv6.setTextColor(Color.parseColor(GlobalMemberValues.RECALLLIST_TEXTCOLOR));
                        subNewTv6.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICHOLDRECALLLISTTTEXTSIZE);
                        subNewLn.addView(subNewTv6);

                        newLn.addView(subNewLn);


                        // 옵션 또는 추가사항이 있을 경우 아래에 보여준다. --------------------------------------------------------------------
                        String tempOptionTxt = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,20), 1);
                        String tempOptionPrice = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,21), 1);

                        String tempAdditionalTxt1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,22), 1);
                        String tempAdditionalPrice1 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,23), 1);

                        String tempAdditionalTxt2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,24), 1);
                        String tempAdditionalPrice2 = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,25), 1);

                        if (!GlobalMemberValues.isStrEmpty(tempOptionTxt) ||
                                !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1) || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {

                            String tempOptionAddTxt = "";
                            if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                                tempOptionAddTxt = "[Option] "+ tempOptionTxt;
                            }
                            if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                                if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                                    tempOptionAddTxt += "\n[Add Ingredients] " + tempAdditionalTxt1;
                                } else {
                                    tempOptionAddTxt = "[Add Ingredients] " + tempAdditionalTxt1;
                                }
                            }
                            if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                                if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                                    tempOptionAddTxt += "\n[Add Ingredients2] " + tempAdditionalTxt2;
                                } else {
                                    tempOptionAddTxt = "[Add Ingredients2] " + tempAdditionalTxt2;
                                }
                            }

                            double tempOptionAdditionalPrice = GlobalMemberValues.getDoubleAtString(tempOptionPrice) +
                                    GlobalMemberValues.getDoubleAtString(tempAdditionalPrice1) +
                                    GlobalMemberValues.getDoubleAtString(tempAdditionalPrice2);
                            tempOptionAdditionalPrice = tempOptionAdditionalPrice * tempSQty;

                            LinearLayout subOptionAddLn = new LinearLayout(context);
                            subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                            //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                            subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);

                            // 화살표
                            ImageView optionAddImg = new ImageView(context);
                            optionAddImg.setPadding(10, 0, 10, 0);
                            optionAddImg.setImageResource(R.drawable.aa_images_arrow);
                            optionAddImg.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
                            subOptionAddLn.addView(optionAddImg);

                            // Option & Additional 내용
                            TextView optionAddTv = new TextView(context);
                            optionAddTv.setLayoutParams(subNewTvLayoutParams);
                            optionAddTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            optionAddTv.setPadding(5, 0, 5, 0);
                            optionAddTv.setText(tempOptionAddTxt);
                            optionAddTv.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 2));
                            optionAddTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            optionAddTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                            optionAddTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                            subOptionAddLn.addView(optionAddTv);

                            // Option & Additional 가격
                            TextView optionAddPriceTv = new TextView(context);
                            optionAddPriceTv.setLayoutParams(subNewTvLayoutParams);
                            optionAddPriceTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                            optionAddPriceTv.setPadding(5, 0, 5, 0);
                            optionAddPriceTv.setText("(" + GlobalMemberValues.getCommaStringForDouble(tempOptionAdditionalPrice + "") + ")");
                            optionAddPriceTv.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 2));
                            optionAddPriceTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            optionAddPriceTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                            optionAddPriceTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                            subOptionAddLn.addView(optionAddPriceTv);

                            // Option & Additional 빈칸
                            TextView optionAddEmptyTv = new TextView(context);
                            optionAddEmptyTv.setLayoutParams(subNewTvLayoutParams);
                            optionAddEmptyTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));
                            subOptionAddLn.addView(optionAddEmptyTv);

                            newLn.addView(subOptionAddLn);
                        }
                        // 27 memoToKitchen
                        String str_memoToKitchen = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,27), 1);
                        if (!str_memoToKitchen.isEmpty() && !(str_memoToKitchen.equals("nokitchenmemo") || str_memoToKitchen.equals("") )){
                            LinearLayout subOptionAddLn = new LinearLayout(context);
                            subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                            //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                            subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);

                            ImageView optionAddImg = new ImageView(context);
                            optionAddImg.setPadding(10, 0, 10, 0);
                            optionAddImg.setImageResource(R.drawable.aa_images_arrow);
                            optionAddImg.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
                            subOptionAddLn.addView(optionAddImg);

                            TextView memoToKitckenTv = new TextView(context);
                            memoToKitckenTv.setLayoutParams(subNewTvLayoutParams);
                            memoToKitckenTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            memoToKitckenTv.setPadding(5, 0, 5, 10);
                            memoToKitckenTv.setText("-- Special Request -- : " + str_memoToKitchen);
                            memoToKitckenTv.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 2));
                            memoToKitckenTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                            memoToKitckenTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                            memoToKitckenTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                            subOptionAddLn.addView(memoToKitckenTv);

                            newLn.addView(subOptionAddLn);
                        }

                        // discount / extra
                        String selectedDcExtraAllEach = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,28), 1);
                        String selectedDcExtraType = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,29), 1);
                        String dcextratype = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,30), 1);
                        String dcextravalue = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,31), 1),"2");
                        String selectedDcExtraPrice = GlobalMemberValues.getStringFormatNumber(GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(detailRs,32), 1), "2");
                        if (!selectedDcExtraAllEach.isEmpty()){
                            if (selectedDcExtraAllEach.equals("ALL")){
                                String str_all_aheadtxt = "";
                                if (selectedDcExtraType.equals("DC")){
                                    // all dc
                                    str_all_aheadtxt = "ALL Discount " + "(" + dcextravalue + dcextratype + ")";
                                } else {
                                    // add ex
                                    str_all_aheadtxt = "ALL Extra " + "(" + dcextravalue + dcextratype + ")";
                                }
                                if (detailRs.isLast()){
                                    LinearLayout subOptionAddLn = new LinearLayout(context);
                                    subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                                    //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                    subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);

                                    ImageView optionAddImg = new ImageView(context);
                                    optionAddImg.setPadding(10, 0, 10, 0);
                                    optionAddImg.setImageResource(R.drawable.aa_images_arrow);
                                    optionAddImg.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
                                    optionAddImg.setVisibility(View.INVISIBLE);
                                    subOptionAddLn.addView(optionAddImg);

                                    TextView memoToKitckenTv = new TextView(context);
                                    memoToKitckenTv.setLayoutParams(subNewTvLayoutParams);
                                    memoToKitckenTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                    memoToKitckenTv.setPadding(5, 0, 5, 10);
                                    memoToKitckenTv.setText(str_all_aheadtxt);
                                    memoToKitckenTv.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 2));
                                    memoToKitckenTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                    memoToKitckenTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                                    memoToKitckenTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                                    subOptionAddLn.addView(memoToKitckenTv);
                                    newLn.addView(subOptionAddLn);
                                }

                            } else if (selectedDcExtraAllEach.equals("EACH")){
                                String str_each_aheadtxt = "";
                                if (selectedDcExtraType.equals("DC")){
                                    // each dc
                                    str_each_aheadtxt = "Each Discount " + "(" + dcextravalue + dcextratype + ") : ";
                                } else {
                                    // each ex
                                    str_each_aheadtxt = "Each Extra " + "(" + dcextravalue + dcextratype + ") : ";
                                }

                                LinearLayout subOptionAddLn = new LinearLayout(context);
                                subOptionAddLn.setLayoutParams(subNewLnLayoutParams);
                                //subNewLn.setTag(GlobalMemberValues.getDBTextAfterChecked(salonSalesDetailCursor.getString(13), 1));
                                subOptionAddLn.setOrientation(LinearLayout.HORIZONTAL);

                                ImageView optionAddImg = new ImageView(context);
                                optionAddImg.setPadding(10, 0, 10, 0);
                                optionAddImg.setImageResource(R.drawable.aa_images_arrow);
                                optionAddImg.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
                                subOptionAddLn.addView(optionAddImg);

                                TextView memoToKitckenTv = new TextView(context);
                                memoToKitckenTv.setLayoutParams(subNewTvLayoutParams);
                                memoToKitckenTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                memoToKitckenTv.setPadding(5, 0, 5, 10);
                                memoToKitckenTv.setText(str_each_aheadtxt + selectedDcExtraPrice);
                                memoToKitckenTv.setTextSize(GlobalMemberValues.globalAddFontSize() + (GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE - 2));
                                memoToKitckenTv.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
                                memoToKitckenTv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                                memoToKitckenTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 5.0f));
                                subOptionAddLn.addView(memoToKitckenTv);

                                newLn.addView(subOptionAddLn);
                            }
                        }

                        // discount / extra


                        // -----------------------------------------------------------------------------------------------------------------
                    }
                    detailRs.close();
                    // ----------------------------------------------------------------------------------------------------------------------------------

                    recallSaleListLinearLayout.addView(newLn);
                    custCount++;
                }
            }
        }catch (Exception e){
            GlobalMemberValues.logWrite("TestLog", "Exception : " + e.toString() + "\n");
        }

    }

    /** 고객정보 배치하기 ********************************************************/
    public static void setCustomerInfoAtOutClass(String paramCustomerId, DatabaseInit paramDbInit) {
        if (!GlobalMemberValues.isStrEmpty(paramCustomerId)) {
            String strQuery = "select a.idx, a.uid, a.name, a.password, a.wdate, a.gender, b.phone, b.mobile, " +
                    " b.byear, b.bmonth, b.bday, b.addr1, b.addr2, b.zip, b.state, b.city, b.membershipCardNo, b.memo, b.mileage, " +
                    " a.lastvisitForSale " +
                    " from member1 a left join member2 b on a.uid = b.uid " +
                    " where a.delyn = 'n' and a.uid = '" + paramCustomerId + "' ";
            GlobalMemberValues.logWrite("setCustomerInfoAtOutClassMethod", "쿼리 : " + strQuery + "\n");
            Cursor customerInfoCursor;
            customerInfoCursor = paramDbInit.dbExecuteRead(strQuery);
            if (customerInfoCursor.getCount() > 0 && customerInfoCursor.moveToFirst()) {
                String tempCustName = customerInfoCursor.getString(2);
                // 고객 이름이 있을 경우에만
                if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(tempCustName, " ", ""))) {
                    //TemporaryCustomerInfo 객체 생성시 전달할 값을 String 배열로 만든다.
                    String paramsTempCustomerinfoArray[] = {
                            String.valueOf(customerInfoCursor.getInt(0)),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(1), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(2), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(3), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(4), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(5), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(6), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(7), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(8), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(9), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(10), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(11), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(12), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(13), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(14), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(15), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(16), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(17), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(18), 1),
                            GlobalMemberValues.getDBTextAfterChecked(customerInfoCursor.getString(19), 1),
                    };

                    TemporaryCustomerInfo selectedItemCustomerInfo = new TemporaryCustomerInfo(paramsTempCustomerinfoArray);
                    // temp_salecart 테이블 수정
                    Vector<String> strUpdateQueryVec = new Vector<String>();
                    String strDeleteQuery = "update temp_salecart set " +
                            " customerId = '" + selectedItemCustomerInfo.memUid + "', " +
                            " customerName = '" + selectedItemCustomerInfo.memName + "', " +
                            " customerPhoneNo = '" + selectedItemCustomerInfo.memPhone + "' " +
                            " where holdcode = '" + MainMiddleService.mHoldCode + "' ";
                    strUpdateQueryVec.addElement(strDeleteQuery);
                    for (String tempQuery : strUpdateQueryVec) {
                        GlobalMemberValues.logWrite("setCustomerInfoAtOutClassMethod", "query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    String returnResult = "";
                    returnResult = paramDbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
                    if (returnResult == "N" || returnResult == "") {
                        GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                        return;
                    } else {
                        GlobalMemberValues.logWrite("setCustomerInfoAtOutClassMethod", "고객정보정상할당됨... uid : " + selectedItemCustomerInfo.memUid + "\n");
                        // 고객정보 변수할당
                        GlobalMemberValues.setCustomerSelected(selectedItemCustomerInfo);
                    }
                }
            }
        }
    }

    /*******************************************************************************/




    // 초기화 메소드
    private void setInit() {
        // 검색날짜를 현재일로 초기화
        //recallStartDateEditText.setText(GlobalMemberValues.STR_NOW_DATE);
        //recallEndDateEditText.setText(GlobalMemberValues.STR_NOW_DATE);
        mSelectedHoldCode = "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_RECALL) {
            if(resultCode == Activity.RESULT_OK) {
                setDeleteHoldList();
            }
        }
    }

}
