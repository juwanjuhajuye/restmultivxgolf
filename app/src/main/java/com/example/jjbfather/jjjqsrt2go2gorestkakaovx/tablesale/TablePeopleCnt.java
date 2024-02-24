package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.CommandButton;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.util.ArrayList;
import java.util.Vector;

public class TablePeopleCnt extends Activity {
    static Activity activity;
    static Context context;

    private LinearLayout parentLn;

    private Button qtyEdit_suButton1,qtyEdit_suButton2,qtyEdit_suButton3;
    private Button qtyEdit_suButton4,qtyEdit_suButton5,qtyEdit_suButton6;
    private Button qtyEdit_suButton7,qtyEdit_suButton8,qtyEdit_suButton9;
    private Button qtyEdit_suButton0,qtyEdit_suButton00,qtyEdit_suButtonBack;
    private Button qtyEdit_suButtonV,qtyEdit_suButtonX;

    private EditText qtyEdit_qty;
    private TextView quantityTitleTextView;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    Intent mIntent;

    int mOrgQty = 1;

    String mTableidx = "";
    String mHoldCode = "";

    // 06.01.2022
    static String mFromMain = "N";

    private float f_fontsize_forPAX = GlobalMemberValues.getGlobalFontSize();
    private float f_globalFontSize = GlobalMemberValues.globalAddFontSize();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_people_cnt);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 14) * 4;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 70;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 80;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.qtyEditLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        activity = this;
        context = this;

        mTableidx = "";
        mHoldCode = "";

        // 06.01.2022
        mFromMain = "N";

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mTableidx = mIntent.getStringExtra("selectedTableIdx");
            mHoldCode = mIntent.getStringExtra("selectedHoldCode");

            // 06.01.2022 -------------------------------------------------
            mFromMain = mIntent.getStringExtra("frommain");
            if (GlobalMemberValues.isStrEmpty(mFromMain)) {
                mFromMain = "N";
            }
            // ------------------------------------------------------------

            GlobalMemberValues.logWrite("tablepeoplecntlog", "mTableidx : " + mTableidx + "\n");
            GlobalMemberValues.logWrite("tablepeoplecntlog", "mHoldCode : " + mHoldCode + "\n");

            GlobalMemberValues.logWrite("tablepeoplecntlog", "mFromMain : " + mFromMain + "\n");
            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

        setLayoutContent();
    }

    public void setLayoutContent() {
        qtyEdit_suButton1 = (Button)findViewById(R.id.qtyEdit_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton1.setTextSize(25);
            qtyEdit_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton1.setTextSize(25
            );
        }
        qtyEdit_suButton2 = (Button)findViewById(R.id.qtyEdit_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton2.setTextSize(25
            );
            qtyEdit_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton2.setTextSize(25
            );
        }
        qtyEdit_suButton3 = (Button)findViewById(R.id.qtyEdit_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton3.setTextSize(25
            );
            qtyEdit_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton3.setTextSize(25
            );
        }
        qtyEdit_suButton4 = (Button)findViewById(R.id.qtyEdit_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton4.setTextSize(25
            );
            qtyEdit_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton4.setTextSize(25
            );
        }
        qtyEdit_suButton5 = (Button)findViewById(R.id.qtyEdit_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton5.setTextSize(25
            );
            qtyEdit_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton5.setTextSize(25
            );
        }
        qtyEdit_suButton6 = (Button)findViewById(R.id.qtyEdit_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton6.setTextSize(25
            );
            qtyEdit_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton6.setTextSize(25
            );
        }
        qtyEdit_suButton7 = (Button)findViewById(R.id.qtyEdit_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton7.setTextSize(25
            );
            qtyEdit_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton7.setTextSize(25
            );
        }
        qtyEdit_suButton8 = (Button)findViewById(R.id.qtyEdit_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton8.setTextSize(25
            );
            qtyEdit_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton8.setTextSize(25
            );
        }
        qtyEdit_suButton9 = (Button)findViewById(R.id.qtyEdit_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton9.setTextSize(25
            );
            qtyEdit_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton9.setTextSize(25
            );
        }
        qtyEdit_suButton0 = (Button)findViewById(R.id.qtyEdit_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton0.setTextSize(25
            );
            qtyEdit_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton0.setTextSize(25
            );
        }
        qtyEdit_suButton00 = (Button)findViewById(R.id.qtyEdit_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton00.setTextSize(25
            );
            qtyEdit_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton00.setTextSize(25
            );
        }
        qtyEdit_suButtonBack = (Button)findViewById(R.id.qtyEdit_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButtonBack.setText("");
            qtyEdit_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            qtyEdit_suButtonBack.setTextSize(
                    qtyEdit_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButtonV = (Button)findViewById(R.id.qtyEdit_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButtonV.setText("");
            qtyEdit_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            qtyEdit_suButtonV.setTextSize(
                    qtyEdit_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButtonX = (Button)findViewById(R.id.qtyEdit_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButtonX.setText("");
            qtyEdit_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            qtyEdit_suButtonX.setTextSize(
                    qtyEdit_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        qtyEdit_suButton1.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton2.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton3.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton4.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton5.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton6.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton7.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton8.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton9.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton0.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButton00.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButtonBack.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButtonV.setOnClickListener(qtyEditButtonClickListener);
        qtyEdit_suButtonX.setOnClickListener(qtyEditButtonClickListener);

        qtyEdit_qty = (EditText)findViewById(R.id.qtyEdit_qty);
        qtyEdit_qty.setTextSize(GlobalMemberValues.globalAddFontSize() + 16);
        qtyEdit_qty.setOnTouchListener(mTouchQtyEditTvTouchListener);

        quantityTitleTextView = (TextView)findViewById(R.id.quantityTitleTextView);
        quantityTitleTextView.setTextSize(f_globalFontSize
                + quantityTitleTextView.getTextSize() * f_fontsize_forPAX);
//        quantityTitleTextView.setTextSize(quantityTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
//        quantityTitleTextView.setTextSize(30);

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), qtyEdit_qty);

        GlobalMemberValues.logWrite("taesooanniejjjlog","TableSaleMain.mSubTableNum(TPC) : " + TableSaleMain.mSubTableNum + "\n");

        
        // 05.31.2022 -------------------------------------------------------------------------------------------------------------
        // Repay 관련
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mTableIdx_byRepay)
                && !GlobalMemberValues.isStrEmpty(GlobalMemberValues.mHoldCode_byRepay)
                && GlobalMemberValues.mPeopleCnt_byRepay > 0) {

            GlobalMemberValues.logWrite("splitsqllog", "People Count 여기...0" + "\n");

            GlobalMemberValues.logWrite("splitsqllog", "tempTableIdx2 : " + GlobalMemberValues.mTableIdx_byRepay + "\n");
            GlobalMemberValues.logWrite("splitsqllog", "tempHoldCode2 : " + GlobalMemberValues.mHoldCode_byRepay + "\n");
            GlobalMemberValues.logWrite("splitsqllog", "tempPeopleCnt2 : " + GlobalMemberValues.mPeopleCnt_byRepay + "\n");

            TablePeopleCnt.setTablePeopleCnt(GlobalMemberValues.mPeopleCnt_byRepay, true,
                    context, GlobalMemberValues.mTableIdx_byRepay, GlobalMemberValues.mHoldCode_byRepay);
        }

        GlobalMemberValues.mTableIdx_byRepay = "";
        GlobalMemberValues.mHoldCode_byRepay = "";
        GlobalMemberValues.mPeopleCnt_byRepay = 0;
        // -----------------------------------------------------------------------------------------------------------------------
    }

    View.OnClickListener qtyEditButtonClickListener = new View.OnClickListener() {
        StringBuilder sb = new StringBuilder();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qtyEdit_suButton1 : {
                    numberButtonClick(qtyEdit_suButton1);
                    break;
                }
                case R.id.qtyEdit_suButton2 : {
                    numberButtonClick(qtyEdit_suButton2);
                    break;
                }
                case R.id.qtyEdit_suButton3 : {
                    numberButtonClick(qtyEdit_suButton3);
                    break;
                }
                case R.id.qtyEdit_suButton4 : {
                    numberButtonClick(qtyEdit_suButton4);
                    break;
                }
                case R.id.qtyEdit_suButton5 : {
                    numberButtonClick(qtyEdit_suButton5);
                    break;
                }
                case R.id.qtyEdit_suButton6 : {
                    numberButtonClick(qtyEdit_suButton6);
                    break;
                }
                case R.id.qtyEdit_suButton7 : {
                    numberButtonClick(qtyEdit_suButton7);
                    break;
                }
                case R.id.qtyEdit_suButton8 : {
                    numberButtonClick(qtyEdit_suButton8);
                    break;
                }
                case R.id.qtyEdit_suButton9 : {
                    numberButtonClick(qtyEdit_suButton9);
                    break;
                }
                case R.id.qtyEdit_suButton0 : {
                    numberButtonClick(qtyEdit_suButton0);
                    break;
                }
                case R.id.qtyEdit_suButton00 : {
                    numberButtonClick(qtyEdit_suButton00);
                    break;
                }
                case R.id.qtyEdit_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mQtyEtValue);
                    if (!GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mQtyEtValue = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mQtyEtValue)) {
                            mQtyEtValue = "0";
                        }
                    } else {
                        mQtyEtValue = "0";
                    }
                    qtyEdit_qty.setText(mQtyEtValue);
                    break;
                }
                case R.id.qtyEdit_suButtonV : {
                    // TextView 의 수량을 edittingQty 에 담는다.
                    String edittingQty = "0";
                    if (!GlobalMemberValues.isStrEmpty(qtyEdit_qty.getText().toString())) {
                        edittingQty = qtyEdit_qty.getText().toString();
                    }

                    if (edittingQty.equals("0")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please enter one or more", "Close");
                        return;
                    }

                    // 수량을 Integer 로 형변환
                    int tempEdittingQty = GlobalMemberValues.getIntAtString(edittingQty);
                    if (tempEdittingQty == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please enter one or more", "Close");
                        return;
                    }

                    setTablePeopleCnt(tempEdittingQty, true, context, mTableidx, mHoldCode);
                    GlobalMemberValues.getMainCustomerPeopleCnt(mHoldCode);
                    break;
                }
                case R.id.qtyEdit_suButtonX : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
            }
        }
    };

    public static void setTablePeopleCnt(int paramSu, boolean paramIsInside, Context paramContext,
                                         String paramTableIdx, String paramHoldcode) {
        if (paramSu == 0) {
            GlobalMemberValues.displayDialog(paramContext, "Warning", "Please enter one or more", "Close");
            GlobalMemberValues.logWrite("splitsqllog", "People Count 여기..." + "\n");
            return;
        }

        String strQuery = "";
        Vector<String> queryVec = new Vector<String>();

        String schHoldCode = "";
        if (!GlobalMemberValues.isStrEmpty(paramHoldcode)) {
            schHoldCode = paramHoldcode;
        } else {
            schHoldCode = TableSaleMain.getHoldCodeByTableidx(paramTableIdx, TableSaleMain.mSubTableNum);
        }

        if (TableSaleMain.getTablePeopleCntByHoldCode(schHoldCode) > 0) {
            strQuery = "delete from salon_store_restaurant_table_peoplecnt where tableidx like '" + paramTableIdx + "' and holdcode = '" + schHoldCode + "' ";
            queryVec.addElement(strQuery);
        }

        strQuery = " insert into salon_store_restaurant_table_peoplecnt " +
                " (tableidx, holdcode, peoplecnt) values ( " +
                "'" + paramTableIdx + "', " +
                "'" + schHoldCode + "', " +
                "'" + paramSu + "' " +
                " )";
        queryVec.addElement(strQuery);

        if (queryVec.size() > 0) {
            for (String tempQuery : queryVec) {
                GlobalMemberValues.logWrite("splitsqllog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(queryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                return;
            } else {
                // 06.01.2022 -----------------------------------------------------------
                if (mFromMain == "Y" || mFromMain.equals("Y")) {
                    strQuery = "";
                    strQuery = " delete from temp_salecart where svcName like '" + GlobalMemberValues.mCommonGratuityName + "' and holdcode = '" + schHoldCode + "' ";
                    MainActivity.mDbInit.dbExecuteWriteReturnValue(strQuery);

                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                    CommandButton.setHoldSales("");
//                    GlobalMemberValues.openRestaurantTable();
                }
                // ----------------------------------------------------------------------

                ArrayList<String> mArr = new ArrayList<String>();
                mArr.add(paramTableIdx);
                TableSaleMain.mTablePeopleCnt = paramSu;
                TableSaleMain.setOrderStart(mArr, false, true);


                if (paramIsInside) {
                    activity.finish();       // 해당 Dialog 를 닫는다.
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }
            }
        }

    }

    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        sb.append(mQtyEtValue).append(btn.getText().toString());
        int tempNumber = Integer.parseInt(sb.toString());
        if (tempNumber < 100) {
            mQtyEtValue = String.valueOf(tempNumber);
            qtyEdit_qty.setText(mQtyEtValue);
        }
    }

    View.OnTouchListener mTouchQtyEditTvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            qtyEdit_qty.setFocusable(true);
            qtyEdit_qty.selectAll();
            // 키보드 안보이게
            GlobalMemberValues.setKeyPadHide(getApplication(), qtyEdit_qty);
            //qtyEdit_qty.hasFocus();
            return true;
        }
    };

}
