package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-13.
 */
public class MainTopCategory {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;
    Button preClickButton;
    MainMiddleService mmsvc;

    public MainTopCategory(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite, int oType) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        if (oType == 0) {
            mmsvc = new MainMiddleService(mActivity, context, dataAtSqlite);
        }
    }

    /** 상단 카테고리 배치하기 ********************************************************/
    public void setTopCategory() {
        // 카테고리 배열 가져오기 (GetDataAtSQLite 클래스의 getCategoryInfo 메소드를 통해 가져온다)
        String[] strArrCategory = dataAtSqlite.getCategoryInfo();

        String strCateInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempCategoryPositionNo = "";     // 임시 PositionNo 를 저장할 객체 선언

        String firstCateIdx = getFirstCategoryIdx();
        // 첫번째 카테고리의 배경색을 선언 및 초기화
        String firstCateBGColor = GlobalMemberValues.CATEGORYCOLORVALUE[0];

        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE = 16;
        }

        float categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE;
        if (GlobalMemberValues.isDeviceElo()) {
            categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE_FORELO;
        }
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            categoryBtnTextSize = GlobalMemberValues.BASICCATEGORYNAMETEXTSIZE - 2;
        }

        int categoryLnHeight = 77;
        if (GlobalMemberValues.mDeviceEloYN == "Y" || GlobalMemberValues.mDeviceEloYN.equals("Y")) {
            categoryLnHeight = 80;
        } else if (GlobalMemberValues.mDevicePAX) {
            categoryLnHeight = 76;
        } else {
            categoryLnHeight = 77;
        }

        GlobalMemberValues.logWrite("categoryheightlog", "category height : " + categoryLnHeight + "\n");

        // 주문시 고객정보 입력페이지 보여줄지 여부
        GlobalMemberValues.mCustomerInfoShowYN = GlobalMemberValues.isCustomerInfoShow();

        // 초기화
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++) {
            LinearLayout topCategoryLn = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryLnTag" + (i + 1));
            topCategoryLn.setBackgroundColor(Color.parseColor("#00000000"));

            Button topCategoryBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryButtonTag" + (i + 1));
            topCategoryBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, categoryLnHeight));
            topCategoryBtn.setAllCaps(false);
            topCategoryBtn.setText("");
            topCategoryBtn.setBackgroundResource(R.drawable.button_selector_newcategory);
        }

        if (GlobalMemberValues.is_customerMain){
            LinearLayout topCategoryLn = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT;
            topCategoryLn.setBackgroundColor(Color.parseColor("#0e1127"));
        }

        boolean b_temp_categorycnt = false;
        // 해당 스토어의 최대 카테고리수(GlobalMemberValues.STOREMAXCATEGOR YSU) 만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++) {
            if (strArrCategory[i] != null && strArrCategory[i] != "" && !strArrCategory[i].equals("")) {
                strCateInfo = strArrCategory[i];
                String[] strCateInfoArr = strCateInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempCategoryPositionNo = strCateInfoArr[3];
                if (Integer.parseInt(tempCategoryPositionNo) > 0) {         // 포지션값이 0 이상 값일때에만
                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    LinearLayout topCategoryLn = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryLnTag" + tempCategoryPositionNo);
                    topCategoryLn.setVisibility(View.VISIBLE);



                    Button topCategoryBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryButtonTag" + tempCategoryPositionNo);
                    topCategoryBtn.setVisibility(View.VISIBLE);
                    topCategoryBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, categoryLnHeight));
                    topCategoryBtn.setAllCaps(false);
                    topCategoryBtn.setText(GlobalMemberValues.changeBrLetter(strCateInfoArr[1]));
                    //topCategoryBtn.setTextSize(15);
                    topCategoryBtn.setTextSize(categoryBtnTextSize);

                    //TextView topCategoryTv = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryTextViewTag" + tempCategoryPositionNo);
                    //topCategoryTv.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));

                    //topCategoryBtn.setBackgroundColor(Color.parseColor("#ffffff"));


                    if (GlobalMemberValues.is_customerMain){
                        topCategoryLn.setBackgroundColor(Color.parseColor("#424864"));
                        topCategoryBtn.setBackgroundResource(R.drawable.button_selector_customer_newcategory);
                        topCategoryBtn.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        topCategoryLn.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));
                        topCategoryBtn.setBackgroundResource(R.drawable.button_selector_newcategory);
                        topCategoryBtn.setTextColor(Color.parseColor("#3e3d42"));
                    }

                    // 첫번째 카테고리일 경우 버튼 배경색을 TextView 배경색과 동일하게 한다. (눌려진 느낌)
                    if (strCateInfoArr[0].equals(firstCateIdx)) {
                        //topCategoryBtn.setBackgroundColor(Color.parseColor(strCateInfoArr[2]));
                        //topCategoryBtn.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_ONCLICKTEXTCOLOR1));
                        topCategoryBtn.setBackgroundResource(GlobalMemberValues.getCategoryBackgroundOver(strCateInfoArr[2]));
                        topCategoryBtn.setTextColor(Color.parseColor(strCateInfoArr[2]));
                        preClickButton = topCategoryBtn;

                        firstCateBGColor = strCateInfoArr[2];
                    }

                    // Category 의 정보를 임시로 저장하기 위한 클래스인 TemporaryCategoryInfo 를 이용하여 카테고리정보 저장
                    TemporaryCategoryInfo tempCategoryInfo
                            = new TemporaryCategoryInfo(strCateInfoArr[0], strCateInfoArr[1], strCateInfoArr[2], strCateInfoArr[3]);
                    // 위에서 생성한 클래스를 Tag 값으로 저장 (setTag)
                    // setTag 로 객체 저장시 카테고리버튼의 아이디를 KEY 값으로 사용한다.
                    if (tempCategoryInfo != null) {
                        topCategoryBtn.setTag(topCategoryBtn.getId(), tempCategoryInfo);
                    }


                    // 카테고리 클릭시 이벤트
                    topCategoryBtn.setOnClickListener(mCategoryButtonListner);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrCategory[i] + "\n");
            } else {
                LinearLayout topCategoryLn = (LinearLayout) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryLnTag" + (i + 1));
                topCategoryLn.setVisibility(View.INVISIBLE);
                Button topCategoryBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOPCATEGORYPARENT.findViewWithTag("topCategoryButtonTag" + (i + 1));
                topCategoryBtn.setVisibility(View.INVISIBLE);
                topCategoryBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));

                if (i%5 == 1){
                    b_temp_categorycnt = true;
                }
                if (b_temp_categorycnt){
                    topCategoryLn.setVisibility(View.GONE);
                    topCategoryBtn.setVisibility(View.GONE);
                }
            }
        }

        // 카테고리 셋팅을 완료한 이후에 첫번째 카테고리의 서비스메뉴를 셋팅한다.-----------------
        // ★★★★★★★★★★★★ 로그인 화면 구성시 이 안의 내용은 지운다. ★★★★★★★★★★★★★★★★★★★★★★★★★
        // 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급
        /**
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null) {
            GlobalMemberValues.GLOBAL_EMPLOYEEINFO
                    = new TemporaryEmployeeInfo("478", "Emp 001", "41", "01020099356", "voiceguy@naver.com", "",
                    "2015-05-28T11:39:00", "1051", "1051", "1111", "1");
        }
         **/
        //--------------------------------------------------------------------------

        // 카테고리 셋팅을 완료한 이후에 첫번째 카테고리의 서비스메뉴를 셋팅한다.-----------------
        mmsvc.setMiddleService(firstCateIdx, firstCateBGColor);
        //--------------------------------------------------------------------------

        // 카테고리 배치가 끝난 후 TemporarySaleCart 의 객체를 null 로 하고
        // holdcode 값이 "NOHOLDCODE" 인 데이터를 DB 에서 지운다.
        MainMiddleService.mTempSaleCart = null;
        String strQuery_ = "delete from temp_salecart where holdcode = 'NOHOLDCODE' " ;
        DatabaseInit dbInit = new DatabaseInit(context);
        String returnDbResult = dbInit.dbExecuteWriteReturnValue(strQuery_);
    }
    /*******************************************************************************/

    public String getFirstCategoryIdx() {
        String topCategoryIdx = "";
        String strQuery = "select idx " +
                " from salon_storeservice_main " +
                " where delyn = 'N' " +
                " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                " and positionNo > 0 and not(positionNo is null or positionNo = '')" +
                " order by positionNo asc, servicename asc limit 1";
        DatabaseInit dbInit = new DatabaseInit(context);
        Cursor c = dbInit.dbExecuteRead(strQuery);
        if (c.getCount() > 0 && c.moveToFirst()) {
            topCategoryIdx = c.getString(0);
        }
        c.close();
        return topCategoryIdx;
    }


    View.OnClickListener mCategoryButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // 클릭한 카테고리 버튼이 바로 이전에 클릭된 버튼 (preClickButton) 이 아닐 경우에만..
            if (btn != preClickButton) {
                GlobalMemberValues.str_logsaveIn_CategoryNames = btn.getText().toString();
                LogsSave.saveLogsInDB(98);
                /** 전에 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                //preClickButton.setBackgroundColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKBACKGROUNDCOLOR1));
                preClickButton.setBackgroundResource(R.drawable.button_selector_newcategory);
                preClickButton.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKTEXTCOLOR1));
                /*****************************************************************************************/

                // 현재 클릭한 카테고리버튼에 Tag 저장되어 있는 TemporaryCategory 객체를 가져온다.
                TemporaryCategoryInfo tempCif = (TemporaryCategoryInfo)btn.getTag(btn.getId());
                // ★★★★★★★★반드시 해당 객체가 있는지 체크해 준다★★★★★★★★
                if (tempCif != null) {
                    /** 현재 클릭한 카테고리의 Color 값을 저장한다.****************************************************/
                    String tempCateColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[0];
                    if (!GlobalMemberValues.isStrEmpty(tempCif.categoryColorNo)) {
                        tempCateColorValue = tempCif.categoryColorNo;
                        //GlobalMemberValues.logWrite("CategoryColorNo", "Color : " + tempCif.categoryColorNo);
                    }
                    /*****************************************************************************************/

                    /** 현재 클릭한 카테고리 버튼 배경색 & 텍스트칼라 설정관련 *****************************************/
                    //btn.setBackgroundColor(Color.parseColor(tempCateColorValue));
                    btn.setBackgroundResource(GlobalMemberValues.getCategoryBackgroundOver(tempCif.categoryColorNo));
                    btn.setTextColor(Color.parseColor(tempCateColorValue));
                    /*****************************************************************************************/

                    String paramCateIdx = "";
                    if (tempCif.categoryIdx != null) {
                        paramCateIdx = tempCif.categoryIdx;
                        mmsvc.setMiddleService(paramCateIdx, tempCateColorValue);
                        //GlobalMemberValues.logWrite("MainTopCategory-TempInstance", "카테고리 인덱스 : " + paramCateIdx + "\n");
                    } else {
                        paramCateIdx = "";
                    }

                    // 현재 클릭한 버튼객체를 preClickButton 에 담는다.
                    preClickButton = btn;
                }
            }
        }
    };

}
