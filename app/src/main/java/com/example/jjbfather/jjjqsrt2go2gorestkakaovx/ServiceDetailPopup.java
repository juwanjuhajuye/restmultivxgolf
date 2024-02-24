package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import java.io.File;

public class ServiceDetailPopup extends Activity {
    private static final String TAG = "ServiceDetailPopup";

    Activity mActivity;
    Context mContext;

    private LinearLayout parentLn;

    private Button serviceDetailPopupCloseButton;

    private ImageView serviceDetailPopupServiceImageView;
    private TextView serviceDetailPopupServiceNameTextView;
    private TextView serviceDetailPopupCategoryTextView, serviceDetailPopupPriceTextView;
    private TextView serviceDetailPopupTimeTextView, serviceDetailPopupPointRatioTextView;
    private TextView serviceDetailPopupSalePriceTextView, serviceDetailPopupSalePeriodTextView;
    private TextView serviceDetailPopupDescriptionTextView;

    Intent mIntent;
    String mServiceIdx = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_service_detail_popup);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 30;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 65;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.serviceDetailPopupLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mActivity = this;
        mContext = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 TemporarySaleCart 객체
            mServiceIdx = mIntent.getStringExtra("ServiceIdx");
            GlobalMemberValues.logWrite(TAG, "넘겨받은 Service Idx : " + mServiceIdx + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_right);
            }
        }

        setContents();
    }

    private void setContents() {
        serviceDetailPopupCloseButton = (Button) findViewById(R.id.serviceDetailPopupCloseButton);
        serviceDetailPopupCloseButton.setOnClickListener(serviceDetailPopupButtonClickListener);

        serviceDetailPopupServiceImageView = (ImageView) findViewById(R.id.serviceDetailPopupServiceImageView);

        serviceDetailPopupServiceNameTextView = (TextView) findViewById(R.id.serviceDetailPopupServiceNameTextView);
        serviceDetailPopupCategoryTextView = (TextView) findViewById(R.id.serviceDetailPopupCategoryTextView);
        serviceDetailPopupPriceTextView = (TextView) findViewById(R.id.serviceDetailPopupPriceTextView);
        serviceDetailPopupTimeTextView = (TextView) findViewById(R.id.serviceDetailPopupTimeTextView);
        serviceDetailPopupPointRatioTextView = (TextView) findViewById(R.id.serviceDetailPopupPointRatioTextView);
        serviceDetailPopupSalePriceTextView = (TextView) findViewById(R.id.serviceDetailPopupSalePriceTextView);
        serviceDetailPopupSalePeriodTextView = (TextView) findViewById(R.id.serviceDetailPopupSalePeriodTextView);
        serviceDetailPopupDescriptionTextView = (TextView) findViewById(R.id.serviceDetailPopupDescriptionTextView);

        String strQuery = "select midx, servicename, service_price, subServiceTime, " +
                " pointratio, saleprice, salestart, saleend, strFilePath, description " +
                " from salon_storeservice_sub " +
                " where idx = '" + mServiceIdx + "' ";
        GlobalMemberValues.logWrite(TAG, "Query : " + strQuery + "\n");
        DatabaseInit dbInit = new DatabaseInit(mContext);   // DatabaseInit 객체 생성
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String tempMidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
            String tempServiceName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
            String tempServicePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
            String tempServiceTime = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1);
            String tempPointRatio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
            String tempSalePrice = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
            String tempSaleStart = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1);
            String tempSaleEnd = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1);
            String tempStrFilePath = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1);
            String tempDescription = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1);

            String categoryName = dbInit.dbExecuteReadReturnString(
                    "select servicename from salon_storeservice_main where idx = '" + tempMidx + "' "
            );

            // 서비스 시간 구하기
            String strServiceTimeHour = "";
            String strServiceTimeMinute = "";

            int intServiceTime = GlobalMemberValues.getIntAtString(tempServiceTime) * 15;

            int intServiceTimeHours = intServiceTime / 60;
            if (intServiceTimeHours < 10) {
                strServiceTimeHour = "0" + intServiceTimeHours;
            } else {
                strServiceTimeHour = String.valueOf(intServiceTimeHours);
            }

            int intServiceTimeMinutes = intServiceTime % 60;
            if (intServiceTimeMinutes < 10) {
                strServiceTimeMinute = "0" + intServiceTimeMinutes;
            } else {
                strServiceTimeMinute = String.valueOf(intServiceTimeMinutes);
            }

            String serviceTimeAll = strServiceTimeHour + ":" + strServiceTimeMinute;

            serviceDetailPopupServiceNameTextView.setText(tempServiceName);
            serviceDetailPopupCategoryTextView.setText(categoryName);
            serviceDetailPopupPriceTextView.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempServicePrice));

            serviceDetailPopupTimeTextView.setText(serviceTimeAll);
            serviceDetailPopupPointRatioTextView.setText(GlobalMemberValues.getCommaStringForDouble(tempPointRatio) + "%");
            // 세일 기간이고, 세일가격에 값이 있을 때  ------------------------------
            if ((!GlobalMemberValues.isStrEmpty(tempSaleStart)
                    && DateMethodClass.getDiffDayCount(tempSaleStart, GlobalMemberValues.STR_NOW_DATE) >= 0)
                    && (!GlobalMemberValues.isStrEmpty(tempSaleEnd)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, tempSaleEnd) >= 0)
                    &&!GlobalMemberValues.isStrEmpty(tempSalePrice)) {
                serviceDetailPopupSalePriceTextView.setText("$" + GlobalMemberValues.getCommaStringForDouble(tempSalePrice));
                serviceDetailPopupSalePeriodTextView.setText(tempSaleStart + "~" + tempSaleEnd);
            } else {
                serviceDetailPopupSalePriceTextView.setText("--");
                serviceDetailPopupSalePeriodTextView.setText("--");
            }

            serviceDetailPopupDescriptionTextView.setText(tempDescription);

            /** 다운로드한 서비스 이미지가 있는지 체크한다. ********************************************/
            Boolean isBgImage = false;
            String strFilePath = "";
            if (!GlobalMemberValues.isStrEmpty(tempStrFilePath)) {
                // 이미지 경로 + 파일명 만들기
                strFilePath = Environment.getExternalStorageDirectory().toString() +
                        "/" + GlobalMemberValues.STATION_CODE +
                        "/" + GlobalMemberValues.FOLDER_SERVICEIMAGE +
                        "/serviceimg_" + mServiceIdx + ".png";
                if (new File(strFilePath).exists() == false) {
                    //GlobalMemberValues.logWrite("savedFileCheck", "파일이 없습니다.");
                    isBgImage = false;
                } else {
                    isBgImage = true;
                }
            } else {
                isBgImage = false;
            }

            serviceDetailPopupServiceImageView.setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃 크기에 이미지를 맞춘다.
            if (!isBgImage) {
                serviceDetailPopupServiceImageView.setImageResource(R.drawable.aa_images_servicenoimage);
            } else {
                // Bitmap 으로 변환
                Bitmap serviceBgImageBm = BitmapFactory.decodeFile(strFilePath);
                serviceDetailPopupServiceImageView.setImageBitmap(serviceBgImageBm);
                //serviceDetailPopupServiceImageView.setPadding(3, 3, 3, 3);
                GlobalMemberValues.logWrite("imageurllog", "img url : " + strFilePath + "\n");
            }
            /****************************************************************************************/

        } else {
            serviceDetailPopupCategoryTextView.setText("");
            serviceDetailPopupPriceTextView.setText("");
            serviceDetailPopupTimeTextView.setText("");
            serviceDetailPopupPointRatioTextView.setText("");
            serviceDetailPopupSalePriceTextView.setText("--");
            serviceDetailPopupSalePeriodTextView.setText("--");
        }
    }


    View.OnClickListener serviceDetailPopupButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.serviceDetailPopupCloseButton : {
                    finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_right);
                    }
                    break;
                }
            }
        }
    };
}
