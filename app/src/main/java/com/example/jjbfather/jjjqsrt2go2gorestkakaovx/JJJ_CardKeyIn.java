package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class JJJ_CardKeyIn extends Activity {
    Activity activity;
    Context context;

    private LinearLayout parentLn;

    private Button cardinfo_suButton1, cardinfo_suButton2, cardinfo_suButton3;
    private Button cardinfo_suButton4, cardinfo_suButton5, cardinfo_suButton6;
    private Button cardinfo_suButton7, cardinfo_suButton8, cardinfo_suButton9;
    private Button cardinfo_suButton0, cardinfo_suButton00, cardinfo_suButtonBack;
    private Button cardinfo_suButtonV, cardinfo_suButtonX;

    private TextView cardnumberEv;

    private TextView giftCardNumberTv, giftCardUsedTv, giftCardBalaceTv;
    private TextView expdateTv, cvvTv;

    private TextView mNowFocusTv;
    private int mSelectedTv = 0;

    String mCardNumber = "";
    String mExpDate = "";
    String mCVVNumber = "";

    StringBuffer sb1 = new StringBuffer();
    StringBuffer sb2 = new StringBuffer();
    StringBuffer sb3 = new StringBuffer();


    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_jjj__card_key_in);
        this.setFinishOnTouchOutside(false);

        activity = this;
        context = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mCardNumber = mIntent.getStringExtra("keyincardnumber");
            mExpDate = mIntent.getStringExtra("keyinexpdate");
            mCVVNumber = mIntent.getStringExtra("keyincvvnumber");

            GlobalMemberValues.logWrite("cardinfolog", "mCardNumber : " + mCardNumber + "\n");
            GlobalMemberValues.logWrite("cardinfolog", "mExpDate : " + mExpDate + "\n");
            GlobalMemberValues.logWrite("cardinfolog", "mCVVNumber : " + mCVVNumber + "\n");
            /*******************************************************************************************/
        } else {
            setFinish();
        }

        setContents();
    }

    public void setContents() {
        // MSR 활성화
        try {
            //magSwiper(this, context.getApplicationContext());
        } catch (Exception e) {
        }

        cardinfo_suButton1 = (Button) findViewById(R.id.cardinfo_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton2 = (Button) findViewById(R.id.cardinfo_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton3 = (Button) findViewById(R.id.cardinfo_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton4 = (Button) findViewById(R.id.cardinfo_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton5 = (Button) findViewById(R.id.cardinfo_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton6 = (Button) findViewById(R.id.cardinfo_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton7 = (Button) findViewById(R.id.cardinfo_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton8 = (Button) findViewById(R.id.cardinfo_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton9 = (Button) findViewById(R.id.cardinfo_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton0 = (Button) findViewById(R.id.cardinfo_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButton00 = (Button) findViewById(R.id.cardinfo_suButton00);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX() * 1.3f
            );
            cardinfo_suButton00.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cardinfo_suButton00.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cardinfo_suButton00.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButton00.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButtonBack = (Button) findViewById(R.id.cardinfo_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButtonBack.setText("");
            cardinfo_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            cardinfo_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButtonV = (Button) findViewById(R.id.cardinfo_suButtonV);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButtonV.setText("");
            cardinfo_suButtonV.setBackgroundResource(R.drawable.ab_imagebutton_qty_enter);
        } else {
            cardinfo_suButtonV.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButtonV.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }
        cardinfo_suButtonX = (Button) findViewById(R.id.cardinfo_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cardinfo_suButtonX.setText("");
            cardinfo_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_qty_close);
        } else {
            cardinfo_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cardinfo_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }


        cardinfo_suButton1.setOnClickListener(clickView);
        cardinfo_suButton2.setOnClickListener(clickView);
        cardinfo_suButton3.setOnClickListener(clickView);
        cardinfo_suButton4.setOnClickListener(clickView);
        cardinfo_suButton5.setOnClickListener(clickView);
        cardinfo_suButton6.setOnClickListener(clickView);
        cardinfo_suButton7.setOnClickListener(clickView);
        cardinfo_suButton8.setOnClickListener(clickView);
        cardinfo_suButton9.setOnClickListener(clickView);
        cardinfo_suButton0.setOnClickListener(clickView);
        cardinfo_suButton00.setOnClickListener(clickView);
        cardinfo_suButtonBack.setOnClickListener(clickView);
        cardinfo_suButtonV.setOnClickListener(clickView);
        cardinfo_suButtonX.setOnClickListener(clickView);

        cardnumberEv = (TextView) findViewById(R.id.cardnumberEv);
        cardnumberEv.setTextSize(GlobalMemberValues.globalAddFontSize() + cardnumberEv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        cardnumberEv.setBackgroundResource(R.drawable.roundlayout_background_paymentgiftcard2);
        //cardnumberEv.setText(mCardNumber);
        cardnumberEv.setOnClickListener(clickView);
        mNowFocusTv = cardnumberEv;
        mSelectedTv = 0;

        expdateTv = (TextView) findViewById(R.id.expdateTv);
        expdateTv.setTextSize(GlobalMemberValues.globalAddFontSize() + expdateTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        //expdateTv.setText(mExpDate);
        expdateTv.setOnClickListener(clickView);

        cvvTv = (TextView) findViewById(R.id.cvvTv);
        cvvTv.setTextSize(GlobalMemberValues.globalAddFontSize() + cvvTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        //cvvTv.setText(mCVVNumber);
        cvvTv.setOnClickListener(clickView);

        giftCardNumberTv = (TextView) findViewById(R.id.giftCardNumberTv);
        giftCardNumberTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardNumberTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardUsedTv = (TextView) findViewById(R.id.giftCardUsedTv);
        giftCardUsedTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardUsedTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        giftCardBalaceTv = (TextView) findViewById(R.id.giftCardBalaceTv);
        giftCardBalaceTv.setTextSize(GlobalMemberValues.globalAddFontSize() + giftCardBalaceTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        viewCardInfo();
    }

    private void setClickTv(TextView paramTv, int paramSelected) {
        cardnumberEv.setBackgroundResource(R.drawable.roundlayout_background_paymentgiftcard);
        expdateTv.setBackgroundResource(R.drawable.roundlayout_background_paymentgiftcard);
        cvvTv.setBackgroundResource(R.drawable.roundlayout_background_paymentgiftcard);

        paramTv.setBackgroundResource(R.drawable.roundlayout_background_paymentgiftcard2);

        mNowFocusTv = paramTv;
        mSelectedTv = paramSelected;
    }

    View.OnClickListener clickView = new View.OnClickListener() {
        StringBuilder sb5 = new StringBuilder();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardnumberEv : {
                    setClickTv(cardnumberEv, 0);
                    break;
                }
                case R.id.expdateTv : {
                    setClickTv(expdateTv, 1);
                    break;
                }
                case R.id.cvvTv : {
                    setClickTv(cvvTv, 2);
                    break;
                }

                case R.id.cardinfo_suButton1: {
                    numberButtonClick(cardinfo_suButton1);
                    break;
                }
                case R.id.cardinfo_suButton2: {
                    numberButtonClick(cardinfo_suButton2);
                    break;
                }
                case R.id.cardinfo_suButton3: {
                    numberButtonClick(cardinfo_suButton3);
                    break;
                }
                case R.id.cardinfo_suButton4: {
                    numberButtonClick(cardinfo_suButton4);
                    break;
                }
                case R.id.cardinfo_suButton5: {
                    numberButtonClick(cardinfo_suButton5);
                    break;
                }
                case R.id.cardinfo_suButton6: {
                    numberButtonClick(cardinfo_suButton6);
                    break;
                }
                case R.id.cardinfo_suButton7: {
                    numberButtonClick(cardinfo_suButton7);
                    break;
                }
                case R.id.cardinfo_suButton8: {
                    numberButtonClick(cardinfo_suButton8);
                    break;
                }
                case R.id.cardinfo_suButton9: {
                    numberButtonClick(cardinfo_suButton9);
                    break;
                }
                case R.id.cardinfo_suButton0: {
                    numberButtonClick(cardinfo_suButton0);
                    break;
                }
                case R.id.cardinfo_suButton00: {
                    numberButtonClick(cardinfo_suButton00);
                    break;
                }
                case R.id.cardinfo_suButtonBack: {
                    switch (mSelectedTv) {
                        case 0 : {
                            sb5.delete(0, sb5.toString().length());
                            sb5.append(mCardNumber);
                            if (!GlobalMemberValues.isStrEmpty(mCardNumber)) {
                                sb5.delete((sb5.toString().length() - 1), sb5.toString().length());
                                mCardNumber = sb5.toString();
                                if (GlobalMemberValues.isStrEmpty(mCardNumber)) {
                                    mCardNumber = "";
                                }
                            } else {
                                mCardNumber = "";
                            }
                            cardnumberEv.setText(mCardNumber);

                            break;
                        }
                        case 1 : {
                            sb5.delete(0, sb5.toString().length());
                            sb5.append(mExpDate);
                            if (!GlobalMemberValues.isStrEmpty(mExpDate)) {
                                sb5.delete((sb5.toString().length() - 1), sb5.toString().length());
                                mExpDate = sb5.toString();
                                if (GlobalMemberValues.isStrEmpty(mExpDate)) {
                                    mExpDate = "";
                                }
                            } else {
                                mExpDate = "";
                            }
                            expdateTv.setText(mExpDate);

                            break;
                        }
                        case 2 : {
                            sb5.delete(0, sb5.toString().length());
                            sb5.append(mCVVNumber);
                            if (!GlobalMemberValues.isStrEmpty(mCVVNumber)) {
                                sb5.delete((sb5.toString().length() - 1), sb5.toString().length());
                                mCVVNumber = sb5.toString();
                                if (GlobalMemberValues.isStrEmpty(mCVVNumber)) {
                                    mCVVNumber = "";
                                }
                            } else {
                                mCVVNumber = "";
                            }
                            cvvTv.setText(mCVVNumber);

                            break;
                        }
                    }

                    viewCardInfo();

                    break;
                }
                case R.id.cardinfo_suButtonV: {
                    boolean isPossible1 = false;
                    boolean isPossible2 = false;
                    boolean isPossible3 = false;

                    if (!GlobalMemberValues.isStrEmpty(mCardNumber) && mCardNumber.length() > 14) {
                        isPossible1 = true;
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Enter card number", "Close");
                        return;
                    }

                    if (!GlobalMemberValues.isStrEmpty(mExpDate) && mExpDate.length() == 4) {
                        isPossible2 = true;
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Enter exp date", "Close");
                        return;
                    }

                    if (!GlobalMemberValues.isStrEmpty(mCVVNumber) && mCVVNumber.length() == 3) {
                        isPossible3 = true;
                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Enter cvv number", "Close");
                        return;
                    }

                    if (isPossible1 && isPossible2 && isPossible3) {
                        PaymentCreditCard.setCardInfo(mCardNumber, mExpDate, mCVVNumber, "", "");
                        setFinish();
                    }

                    break;
                }
                case R.id.cardinfo_suButtonX: {
                    init();
                    setFinish();
                    break;
                }
            }
        }
    };

    private void viewCardInfo() {
        // Card Number
        String editCardNum = mCardNumber;
        if (!GlobalMemberValues.isStrEmpty(mCardNumber)) {
            if (mCardNumber.length() < 5) {
                editCardNum = mCardNumber;
            } else if (mCardNumber.length() > 4 && mCardNumber.length() < 9) {
                editCardNum = mCardNumber.substring(0, 4) + " " + mCardNumber.substring(4, mCardNumber.length());
            } else if (mCardNumber.length() > 8 && mCardNumber.length() < 13) {
                editCardNum = mCardNumber.substring(0, 4) + " " + mCardNumber.substring(4, 8) + " " + mCardNumber.substring(8, mCardNumber.length());
            } else if (mCardNumber.length() > 12) {
                editCardNum = mCardNumber.substring(0, 4) + " " + mCardNumber.substring(4, 8) + " " + mCardNumber.substring(8, 12) + " " + mCardNumber.substring(12, mCardNumber.length());
            } else {
                editCardNum = mCardNumber;
            }
        }
        cardnumberEv.setText(editCardNum);


        // Exp Date
        String editExpDate = mExpDate;
        if (!GlobalMemberValues.isStrEmpty(mExpDate)) {
            if (mExpDate.length() < 3) {
                editExpDate = mExpDate;
            } else {
                editExpDate = mExpDate.substring(0, 2) + "/" + mExpDate.substring(2, mExpDate.length());
            }
        }
        expdateTv.setText(editExpDate);

        cvvTv.setText(mCVVNumber);
    }

    private void numberButtonClick(Button btn) {
        switch (mSelectedTv) {
            case 0 : {
                if (mCardNumber.length() < 16) {
                    sb1.delete(0, sb1.toString().length());
                    sb1.append(mCardNumber).append(btn.getText().toString());
                    mCardNumber = sb1.toString();

                    if (mCardNumber.length() == 16) {
                        setClickTv(expdateTv, 1);
                    }
                }

                break;
            }
            case 1 : {
                if (mExpDate.length() < 4) {
                    sb2.delete(0, sb2.toString().length());
                    sb2.append(mExpDate).append(btn.getText().toString());
                    mExpDate = sb2.toString();

                    if (mExpDate.length() == 4) {
                        setClickTv(cvvTv, 2);
                    }
                }

                break;
            }
            case 2 : {
                if (mCVVNumber.length() < 3) {
                    sb3.delete(0, sb3.toString().length());
                    sb3.append(mCVVNumber).append(btn.getText().toString());
                    mCVVNumber = sb3.toString();
                }

                break;
            }
        }

        viewCardInfo();
    }

    private void setFinish() {
        activity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            activity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
        }
    }

    private void init() {
        cardnumberEv.setText("");
        expdateTv.setText("");
        cvvTv.setText((""));
        mCardNumber = "";
        mExpDate = "";
        mCVVNumber = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("registrationComplete"));

        GlobalMemberValues.RECEIPTNOFORUPLOAD = "";

        // 세일, 기프트카드, 고객포인트(마일리지) 데이터 클라우드 전송
        GlobalMemberValues.serviceStartSendDataToCloud(context, activity);
    }
}