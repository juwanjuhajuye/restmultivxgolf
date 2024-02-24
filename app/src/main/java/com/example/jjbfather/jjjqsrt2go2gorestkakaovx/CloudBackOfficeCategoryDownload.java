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

public class CloudBackOfficeCategoryDownload extends Activity {
    static Activity mActivity;
    Context context;

    private LinearLayout parentLn;

    private TextView allcategorydownbloadTv;
    private Button closeBtn;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_cloud_back_office_category_download);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // 인터넷 상태 체크
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
        } else {
            GlobalMemberValues.displayDialog(context, "Warning", "Internet is not connected", "Close");
            closeActivity();
        }

        GlobalMemberValues.logWrite("displanylogdata", "width : " + GlobalMemberValues.thisTabletRealWidth + "\n");
        GlobalMemberValues.logWrite("displanylogdata", "height : " + GlobalMemberValues.thisTabletRealHeight + "\n");

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
        if (GlobalMemberValues.thisTabletRealWidth > 1280) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
        }
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 40;
        if (GlobalMemberValues.thisTabletRealHeight > 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 40;
        }

        // Device 가 Elo 일 때...
        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
        } else {
            if (GlobalMemberValues.isDeviceElo()) {
                parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 70;
            } else {
                if (GlobalMemberValues.thisTabletRealHeight == 1920 && GlobalMemberValues.thisTabletRealWidth == 1032) {
                    parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 100) * 40;
                }
            }
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.cloudBackOfficeLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // paymentChangeAmount = mIntent.getStringExtra("PaymentChangeAmount");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            closeActivity();
        }

        setContents();
    }

    public void setContents() {
        closeBtn = (Button) findViewById(R.id.cloudBackOfficeCloseBtn);
        closeBtn.setOnClickListener(cloudBackOfficeBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
            );
        }

        allcategorydownbloadTv = (TextView)findViewById(R.id.allcategorydownbloadTv);
        allcategorydownbloadTv.setTextSize(GlobalMemberValues.globalAddFontSize() +
                allcategorydownbloadTv.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX()
        );
        allcategorydownbloadTv.setOnClickListener(cloudBackOfficeBtnClickListener);

        setTopCategory();
    }

    View.OnClickListener cloudBackOfficeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cloudBackOfficeCloseBtn : {
                    closeActivity();

                    break;
                }
                case R.id.allcategorydownbloadTv : {
                    CloudBackOffice.mProcessDownload = "Y";
                    CloudBackOffice.mCategoryIdx = "";

                    CloudBackOffice.openDialog();
                    closeActivity();
                    break;
                }
            }
        }
    };

    /** 상단 카테고리 배치하기 ********************************************************/
    public void setTopCategory() {
        // 카테고리 배열 가져오기 (GetDataAtSQLite 클래스의 getCategoryInfo 메소드를 통해 가져온다)
        String[] strArrCategory = MainActivity.dataAtSqlite.getCategoryInfo();

        String strCateInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempCategoryPositionNo = "";     // 임시 PositionNo 를 저장할 객체 선언

        float categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE;
        if (GlobalMemberValues.isDeviceElo()) {
            categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE_FORELO;
        }

        // 해당 스토어의 최대 카테고리수(GlobalMemberValues.STOREMAXCATEGORYSU) 만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++) {
            if (strArrCategory[i] != null && strArrCategory[i] != "" && !strArrCategory[i].equals("")) {
                strCateInfo = strArrCategory[i];
                String[] strCateInfoArr = strCateInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempCategoryPositionNo = strCateInfoArr[3];
                if (Integer.parseInt(tempCategoryPositionNo) > 0) {         // 포지션값이 0 이상 값일때에만
                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    LinearLayout topCategoryLn = (LinearLayout) parentLn.findViewWithTag("topCategoryLnTag" + tempCategoryPositionNo);
                    topCategoryLn.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));

                    Button topCategoryBtn = (Button)parentLn.findViewWithTag("topCategoryButtonTag" + tempCategoryPositionNo);
                    topCategoryBtn.setAllCaps(false);
                    topCategoryBtn.setText(GlobalMemberValues.changeBrLetter(strCateInfoArr[1]));
                    //topCategoryBtn.setTextSize(15);
                    topCategoryBtn.setTextSize(categoryBtnTextSize);

                    topCategoryBtn.setBackgroundResource(R.drawable.button_selector_newcategory);
                    topCategoryBtn.setTextColor(Color.parseColor("#3e3d42"));

                    topCategoryBtn.setTag(strCateInfoArr[0]);

                    // 카테고리 클릭시 이벤트
                    topCategoryBtn.setOnClickListener(mCategoryButtonListner);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrCategory[i] + "\n");
            }
        }
    }
    /*******************************************************************************/


    View.OnClickListener mCategoryButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            String tempCategoryIdx = btn.getTag().toString();

            CloudBackOffice.mProcessDownload = "Y";
            CloudBackOffice.mCategoryIdx = tempCategoryIdx;

            GlobalMemberValues.displayDialog(context, "Warning", tempCategoryIdx, "Close");

            if (!GlobalMemberValues.isStrEmpty(tempCategoryIdx)) {
                String tempCateName1 = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicename from salon_storeservice_main where idx = " + tempCategoryIdx);
                String tempCateName2 = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select servicename2 from salon_storeservice_main where idx = " + tempCategoryIdx);

                String categoryName = "";
                categoryName = tempCateName1;
                if (!GlobalMemberValues.isStrEmpty(tempCateName2)) {
                    categoryName += tempCateName2;
                }

                GlobalMemberValues.displayDialog(context, "Warning", categoryName, "Close");

                String tempDownloadButtonName = CloudBackOffice.cloudBackOfficeItemDownloadButton.getText().toString();
                tempDownloadButtonName += " (" + categoryName + ")";
                CloudBackOffice.cloudBackOfficeItemDownloadButton.setText(tempDownloadButtonName);

                CloudBackOffice.openDialog();

                closeActivity();
            }
        }
    };

    private void closeActivity() {
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }
}
