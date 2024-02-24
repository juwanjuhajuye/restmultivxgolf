package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

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

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class QtyEditActivity extends Activity {
    Activity activity;
    Context context;

    private LinearLayout parentLn;

    private Button qtyEdit_suButton1,qtyEdit_suButton2,qtyEdit_suButton3;
    private Button qtyEdit_suButton4,qtyEdit_suButton5,qtyEdit_suButton6;
    private Button qtyEdit_suButton7,qtyEdit_suButton8,qtyEdit_suButton9;
    private Button qtyEdit_suButton0,qtyEdit_suButton00,qtyEdit_suButtonBack;
    private Button qtyEdit_suButtonV,qtyEdit_suButtonX;

    private EditText qtyEdit_qty;
    private TextView serviceNameTv;
    private TextView quantityTitleTextView;

    TemporarySaleCart parentTemporarySaleCart;

    String mQtyEtValue = "";

    StringBuffer sb = new StringBuffer();

    String parentSelectedPosition;

    Intent mIntent;

    int mOrgQty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_qty_edit);
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

        setLayoutContent();

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            parentTemporarySaleCart = (TemporarySaleCart)mIntent.getSerializableExtra("ParentTemporarySaleCartInstance");
            // 부모로부터 받은 선택포시션 값
            parentSelectedPosition = mIntent.getStringExtra("ParentSaleCartPosition");
            GlobalMemberValues.logWrite("ReturnQtyEditValue", "넘겨받은 포지션값 : " + parentSelectedPosition + "\n");
            /*******************************************************************************************/

            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");

            // 부모로부터 넘겨받은 TemporarySaleCart 객체가 null 이 아닐 때
            if (parentTemporarySaleCart != null) {
                String tempEvValue = "0";
                if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSQty)) {
                    tempEvValue = String.valueOf(parentTemporarySaleCart.mSQty);
                    mOrgQty = GlobalMemberValues.getIntAtString(tempEvValue);
                }
                qtyEdit_qty.setText(tempEvValue);

                if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSvcName)) {
                    serviceNameTv.setText(parentTemporarySaleCart.mSvcName);
                }

                GlobalMemberValues.logWrite("TAJJLOG", "parentTemporarySaleCart.optionprice : " + parentTemporarySaleCart.optionprice + "\n");
            }
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
            }
        }

    }

    public void setLayoutContent() {
        qtyEdit_suButton1 = (Button)findViewById(R.id.qtyEdit_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton1.setTextSize(
                    qtyEdit_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton1.setTextSize(
                    qtyEdit_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton2 = (Button)findViewById(R.id.qtyEdit_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton2.setTextSize(
                    qtyEdit_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton2.setTextSize(
                    qtyEdit_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton3 = (Button)findViewById(R.id.qtyEdit_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton3.setTextSize(
                    qtyEdit_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton3.setTextSize(
                    qtyEdit_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton4 = (Button)findViewById(R.id.qtyEdit_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton4.setTextSize(
                    qtyEdit_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton4.setTextSize(
                    qtyEdit_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton5 = (Button)findViewById(R.id.qtyEdit_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton5.setTextSize(
                    qtyEdit_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton5.setTextSize(
                    qtyEdit_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton6 = (Button)findViewById(R.id.qtyEdit_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton6.setTextSize(
                    qtyEdit_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton6.setTextSize(
                    qtyEdit_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton7 = (Button)findViewById(R.id.qtyEdit_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton7.setTextSize(
                    qtyEdit_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton7.setTextSize(
                    qtyEdit_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton8 = (Button)findViewById(R.id.qtyEdit_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton8.setTextSize(
                    qtyEdit_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton8.setTextSize(
                    qtyEdit_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton9 = (Button)findViewById(R.id.qtyEdit_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton9.setTextSize(
                    qtyEdit_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton9.setTextSize(
                    qtyEdit_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton0 = (Button)findViewById(R.id.qtyEdit_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton0.setTextSize(
                    qtyEdit_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton0.setTextSize(
                    qtyEdit_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        qtyEdit_suButton00 = (Button)findViewById(R.id.qtyEdit_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            qtyEdit_suButton00.setTextSize(
                    qtyEdit_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            qtyEdit_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            qtyEdit_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            qtyEdit_suButton00.setTextSize(
                    qtyEdit_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
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

        serviceNameTv = (TextView)findViewById(R.id.serviceNameTv);
        serviceNameTv.setTextSize(serviceNameTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        quantityTitleTextView = (TextView)findViewById(R.id.serviceNameTv);
        quantityTitleTextView.setTextSize(quantityTitleTextView.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        // 키보드 안보이게
        GlobalMemberValues.setKeyPadHide(getApplication(), qtyEdit_qty);
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

                    /** temp_salecart 테이블 저장 및 TemporarySaleCart 객체에 저장된 값을 변경 *****************************************/
                    // 수량을 Integer 로 형변환
                    int tempEdittingQty = GlobalMemberValues.getIntAtString(edittingQty);
                    if (tempEdittingQty == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Please enter one or more", "Close");
                        return;
                    }
                    // 단가 합계 변경 ------------------------------------------------------------------------------
                    Double tempMSPriceAmount = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPrice)) {
                        tempMSPriceAmount = (Double.parseDouble(parentTemporarySaleCart.mSPrice)) * tempEdittingQty;
                    }
                    String insMSPriceAmount = "0.0";
                    if (tempMSPriceAmount > 0) {
                        insMSPriceAmount = String.format("%.2f", tempMSPriceAmount);
                    }

                    GlobalMemberValues.logWrite("TAJJLOG", "insMSPriceAmount : " + insMSPriceAmount + "\n");
                    // ---------------------------------------------------------------------------------------------
                    // BAL 단가 합계 변경 ------------------------------------------------------------------------------
                    Double tempMSPriceBalAmount = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPrice)) {
                        tempMSPriceBalAmount = (Double.parseDouble(parentTemporarySaleCart.mSPrice)) * tempEdittingQty;
                    }
                    String insMSPriceBalAmount = "0.0";
                    if (tempMSPriceBalAmount > 0) {
                        insMSPriceBalAmount = String.format("%.2f", tempMSPriceBalAmount);
                    }

                    GlobalMemberValues.logWrite("TAJJLOG", "insMSPriceBalAmount : " + insMSPriceBalAmount + "\n");
                    // ---------------------------------------------------------------------------------------------
                    // 세금 합계 변경 ------------------------------------------------------------------------------
                    // Tax 값이 0 인 경우 Tax Exempt 를 한 경우이므로, 재설정이 필요없다.
                    double tempTaxAmountSaleCart = GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.mSTaxAmount);
                    double tempMSTaxAmount = 0.00;
                    String insMSTaxAmount = "0.0";
                    if (tempTaxAmountSaleCart > 0) {
                        String tempSaveType = parentTemporarySaleCart.mSaveType;
                        if (GlobalMemberValues.isStrEmpty(tempSaveType)) {
                            tempSaveType = "0";
                        }
                        double tempTaxRate = 0.0;
                        switch (tempSaveType) {
                            case "0" : {
                                tempTaxRate = GlobalMemberValues.getTax1InStoreGeneral();
                                break;
                            }
                            case "1" : {
                                tempTaxRate = GlobalMemberValues.getTax2InStoreGeneral();
                                break;
                            }
                        }

                        if (tempTaxRate > 0) {
                            tempMSTaxAmount = GlobalMemberValues.getDoubleAtString(insMSPriceBalAmount) * tempTaxRate * 0.01;
                        }

                        /**
                        if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSTax)) {
                            tempMSTaxAmount = (Double.parseDouble(parentTemporarySaleCart.mSTax)) * tempEdittingQty;
                        }
                         **/

                        if (tempMSTaxAmount > 0) {
                            insMSTaxAmount = String.format("%.2f", tempMSTaxAmount);
                        }
                    }
                    // ---------------------------------------------------------------------------------------------
                    // 총액 변경 -----------------------------------------------------------------------------------
                    Double tempMSTotalAmount = tempMSPriceBalAmount + tempMSTaxAmount;
                    String insMSTotalAmount = "0.0";
                    if (tempMSTotalAmount > 0) {
                        insMSTotalAmount = String.format("%.2f", tempMSTotalAmount);
                    }
                    // ---------------------------------------------------------------------------------------------
                    // 커미션 합계 변경 ------------------------------------------------------------------------------
                    Double tempMSCommissionAmount = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSCommission)) {
                        tempMSCommissionAmount = (Double.parseDouble(parentTemporarySaleCart.mSCommission)) * tempEdittingQty;
                    }
                    String insMSCommissionAmount  = "0.0";
                    if (tempMSCommissionAmount > 0) {
                        insMSCommissionAmount  = String.format("%.2f", tempMSCommissionAmount);
                    }
                    // ---------------------------------------------------------------------------------------------
                    // 포인트 합계 변경 ------------------------------------------------------------------------------
                    Double tempMSPointAmount = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPoint)) {
                        tempMSPointAmount = (Double.parseDouble(parentTemporarySaleCart.mSPoint)) * tempEdittingQty;
                    }
                    String insMSPointAmount  = "0.0";
                    if (tempMSPointAmount > 0) {
                        insMSPointAmount  = String.format("%.2f", tempMSPointAmount);
                    }
                    // ---------------------------------------------------------------------------------------------

                    // option price 합계 변경 -----------------------------------------------------------------------
                    Double tempOptionprice = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.optionprice)) {
                        //tempOptionprice = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.optionprice)) * tempEdittingQty;
                        tempOptionprice = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.optionprice));
                    }
                    String insOptionprice = "0.0";
                    if (tempOptionprice > 0) {
                        insOptionprice = GlobalMemberValues.getStringFormatNumber(tempOptionprice, "2");
                    }

                    GlobalMemberValues.logWrite("TAJJLOG", "insOptionprice : " + insOptionprice + "\n");
                    // ---------------------------------------------------------------------------------------------

                    // additional price1 합계 변경 ------------------------------------------------------------------
                    Double tempAdditionalprice1 = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.additionalprice1)) {
                        //tempAdditionalprice1 = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.additionalprice1)) * tempEdittingQty;
                        tempAdditionalprice1 = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.additionalprice1));
                    }
                    String insAdditionalprice1 = "0.0";
                    if (tempAdditionalprice1 > 0) {
                        insAdditionalprice1 = String.format("%.2f", tempAdditionalprice1);
                    }
                    // ---------------------------------------------------------------------------------------------

                    // additional price2 합계 변경 ------------------------------------------------------------------
                    Double tempAdditionalprice2 = 0.00;
                    if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.additionalprice2)) {
                        //tempAdditionalprice2 = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.additionalprice2)) * tempEdittingQty;
                        tempAdditionalprice2 = (GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.additionalprice2));
                    }
                    String insAdditionalprice2 = "0.0";
                    if (tempAdditionalprice2 > 0) {
                        insAdditionalprice2 = String.format("%.2f", tempAdditionalprice2);
                    }
                    // ---------------------------------------------------------------------------------------------

                    String tempSaleCartIdx = parentTemporarySaleCart.tempSaleCartIdx;
                    if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {

                        String strSqlQuery = "update temp_salecart set " +
                                " sQty ='" + edittingQty + "', " +
                                " sPriceAmount ='" + insMSPriceAmount + "', " +
                                " sPriceBalAmount ='" + insMSPriceBalAmount + "', " +
                                " sTaxAmount ='" + insMSTaxAmount + "', " +
                                " sTotalAmount ='" + insMSTotalAmount + "', " +
                                " sCommissionAmount ='" + insMSCommissionAmount + "', " +
                                " sPointAmount ='" + insMSPointAmount + "', " +
                                " optionprice ='" + insOptionprice + "', " +
                                " additionalprice1 ='" + insAdditionalprice1 + "', " +
                                " additionalprice2 ='" + insAdditionalprice2 + "', " +
                                " isCloudUpload = 0 " +
                                " where idx = '" + tempSaleCartIdx + "' ";
                        GlobalMemberValues.logWrite("updateQuery", strSqlQuery + "\n");
                        DatabaseInit dbInit = new DatabaseInit(MainMiddleService.insContext);
                        String returnCode = "";
                        returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
                        GlobalMemberValues.logWrite("updateQuery", "returnCode : " + returnCode + "\n");
                        if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                            // common gratuity 관련
                            GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();


                            // 장바구니데이터 업로드
                            GlobalMemberValues gm = new GlobalMemberValues();
                            if (gm.isPOSWebPay() && !GlobalMemberValues.isStrEmpty(tempSaleCartIdx)
                                    && (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                                GlobalMemberValues.setSendCartToCloud(MainActivity.mContext, MainActivity.mActivity);
                            }

                            // TemporarySaleCart 객체 변수값 변경
                            parentTemporarySaleCart.mSQty = edittingQty;
                            parentTemporarySaleCart.mSPriceAmount = insMSPriceAmount;
                            parentTemporarySaleCart.mSPriceBalAmount = insMSPriceBalAmount;
                            parentTemporarySaleCart.mSTaxAmount = insMSTaxAmount;
                            parentTemporarySaleCart.mSTotalAmount = insMSTotalAmount;
                            parentTemporarySaleCart.mSCommissionAmount = insMSCommissionAmount;
                            parentTemporarySaleCart.mSPointAmount= insMSPointAmount;
                            if (parentTemporarySaleCart.mSaveType != "2") {
                                if (tempMSTaxAmount > 0) {
                                    parentTemporarySaleCart.mTaxExempt = "";
                                } else {
                                    // tax(tempMSTaxAmount) 가 0 이더라도 Tax Exempt 를 하지 않고
                                    // 원래의 Tax(tempTaxAmountSaleCart) 가 0 이었을 경우에는 mTaxExempt 를 Y 로 하지 않는다.
                                    // 따라서 원래 Tax(tempTaxAmountSaleCart) 가 0 이상이었는데 Tax Exempt 를 했던 경우에만
                                    // mTaxExempt 를 Y 로 한다.
                                    if (tempTaxAmountSaleCart > 0) {
                                        parentTemporarySaleCart.mTaxExempt = "Y";
                                    }

                                }
                            }

                            /**
                            double adjOptionprice = GlobalMemberValues.getDoubleAtString(insOptionprice) / tempEdittingQty;
                            double adjAdditionalprice1 = GlobalMemberValues.getDoubleAtString(insAdditionalprice1) / tempEdittingQty;
                            double adjAdditionalprice2 = GlobalMemberValues.getDoubleAtString(insAdditionalprice2) / tempEdittingQty;
                             **/

                            parentTemporarySaleCart.optionprice = insOptionprice;
                            parentTemporarySaleCart.additionalprice1 = insAdditionalprice1;
                            parentTemporarySaleCart.additionalprice2 = insAdditionalprice2;

                            GlobalMemberValues.logWrite("TAJJLOG", "parentTemporarySaleCart.optionprice : " + parentTemporarySaleCart.optionprice + "\n");
                            GlobalMemberValues.logWrite("TAJJLOG", "parentTemporarySaleCart.additionalprice1 : " + parentTemporarySaleCart.additionalprice1 + "\n");
                            GlobalMemberValues.logWrite("TAJJLOG", "parentTemporarySaleCart.additionalprice2 : " + parentTemporarySaleCart.additionalprice2 + "\n");


                            if (GlobalMemberValues.isMultiCheckOnCart()) {
                                // 멀티선택관련 추가
                                // 선택여부 배열크기 생성
                                MainMiddleService.isCheckedConfrim = new boolean[MainMiddleService.mGeneralArrayList.size()];
                            }

                            // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                            MainMiddleService.mGeneralArrayList.set(Integer.parseInt(parentSelectedPosition), parentTemporarySaleCart);
                            MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                                MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();
                            }

                            String[] strValueForTextView = null;
                            strValueForTextView = MainMiddleService.getCalcSubTotalTaxTotalValue(MainMiddleService.mGeneralArrayList);
                            MainMiddleService.setCalcSubTotalTaxTotalValue(strValueForTextView);

                            MainMiddleService.selectedPosition = -1;



                            // common gratuity 관련
                            GlobalMemberValues.addCartLastItemForCommonGratuityUse();
                        }
                    }
                    /*****************************************************************************************************/


                    finish();       // 해당 Dialog 를 닫는다.
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        activity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
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

    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        sb.append(mQtyEtValue).append(btn.getText().toString());
        int tempNumber = Integer.parseInt(sb.toString());
        if (tempNumber < 1000) {
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
