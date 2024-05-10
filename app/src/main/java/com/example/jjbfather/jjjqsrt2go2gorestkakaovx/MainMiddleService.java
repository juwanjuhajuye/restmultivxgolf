package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-13.
 */
public class MainMiddleService {
    static Activity mActivity;
    static Context context;
    public static Context insContext;
    GetDataAtSQLite dataAtSqlite;

    String categoryColorValue;
    int categoryColorXmlValue;

    static Button preClickButton;

    static TemporarySaleCart mTempSaleCart = null;

    public static ArrayList<TemporarySaleCart> mGeneralArrayList;
    public static ArrayList<TemporarySaleCart> mGeneralArrayList_copy;
    public static SaleCartAdapter mSaleCartAdapter;

    //jihun park presentation listview
    public static PresentationCartAdapter mPresentationCartAdapter;

    static int listViewCount = 0;

    public static int selectedPosition = -1;

    // HOLD 코드
    public static String mHoldCode = "NOHOLDCODE";

    // 고객 및 직원정보 관련 변수 선언
    static String insCustomerId = "";
    static String insCustomerName = "";
    static String insCustomerPhone = "";
    static String insEmpIdx = "";
    static String insEmpName = "";

    // Discount / Extra 관련 변수
    static String mIsAllDiscount = "N";
    static Vector<String> discountExtraPositionVector = null;

    // DB 객체 선언
    static DatabaseInit dbInit;

    // 애니메이션 관련 객체 ---------------------------------
    static AnimationSet animSet;

    static TranslateAnimation animTrans;
    static RotateAnimation animRotat;
    static Animation animScale;

    static int mServiceBall;
    // ----------------------------------------------------

    // 옵션 & 추가사항 관련 객체 ---------------------------
    public static String mModifierCode = "";
    public static String mModifierIdx = "";
    public static String mOptionTxt = "";
    public static String mOptionPrice = "0";
    public static String mAdditionalTxt1 = "";
    public static String mAdditionalprice1 = "0";
    public static String mAdditionalTxt2 = "";
    public static String mAdditionalprice2 = "0";

    public static String mMemoToKitchen = "";
    // ----------------------------------------------------

    static View mTouchedView;
    static Button mTouchedView_fromMenuSearch;
    static String[] mTouchedArr;

    static int mListviewItemCount = 0;

    // 멀티선택관련 추가
    // 선택여부 배열
    static boolean[] isCheckedConfrim;

    static String mQuickSaleYN = "N";

    static String mQuickSaleKitchenPrintingYN = "N";

    // dynamic price
    static View dynamicPrice_tempView;

    // 01262023
    String mCateIdx = "";

    public MainMiddleService(Activity actvt, Context context, GetDataAtSQLite dataAtSqlite) {
        this.mActivity = actvt;
        this.context = context;
        this.dataAtSqlite = dataAtSqlite;

        mGeneralArrayList = new ArrayList<TemporarySaleCart>();

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        GlobalMemberValues.setBooleanDevice();
    }

    public void getServiceButtonColor(String paramCategoryColorValue) {
        if (GlobalMemberValues.isStrEmpty(paramCategoryColorValue)) {
            this.categoryColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[0];
            this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_DEFAULT;
        } else {
            this.categoryColorValue = paramCategoryColorValue;
            switch (paramCategoryColorValue) {
                case "#a0a0a0" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[0];
//                    Resources res = Resources.getSystem();
//                    Drawable drawable = ResourcesCompat.getDrawable(res,R.drawable.ab_roundlayout_category_item_bg,null);

//                    categoryColorXmlValue = 0;
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[0];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_1;
                    break;
                }
                case "#f8b452" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[1];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[1];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_2;
                    break;
                }
                case "#b06cd0" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[2];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[2];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_3;
                    break;
                }
                case "#5c8cb4" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[3];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[3];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_4;
                    break;
                }
                case "#a8bc58" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[4];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[4];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_5;
                    break;
                }
                case "#8ababc" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[5];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[5];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_6;
                    break;
                }
                case "#de7676" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[6];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[6];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_7;
                    break;
                }
                case "#fc78c4" : {
                    this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE[7];
                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        this.categoryColorXmlValue = GlobalMemberValues.CATEGORYCOLORXMLVALUE_LITE[7];
                    }
                    this.mServiceBall = R.drawable.aa_images_serviceball_8;
                    break;
                }
            }
        }

    }

    /** 중간 서비스 배치하기 메소드 ********************************************************/
    public void setMiddleService(String paramCateIdx, String paramCategoryColorValue) {
        GlobalMemberValues.setBooleanDevice();

        String cateIdx = paramCateIdx;

        // 서비스(메뉴) 관련 추가 ------------------------------------------------------------------
        ScrollView tempSelectedSV = null;

        // 선택한 카테고리의 최대 서비스(메뉴)수 구하기
        int tempServiceSuInCategory = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select menusu from salon_storeservice_main where idx = '" + cateIdx + "' "
                )
        );


        if (tempServiceSuInCategory == 0) {
            tempServiceSuInCategory = GlobalMemberValues.STOREMAXSERVICESU;
        }

        ScrollView tempServiceScrollView60 = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag60");
        ScrollView tempServiceScrollView72 = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag72");
        ScrollView temp_customer_serviceScrollView60 = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("customer_servicemenulayout_60");
        if (GlobalMemberValues.is_customerMain){
            temp_customer_serviceScrollView60.fullScroll(ScrollView.FOCUS_UP);
            tempServiceSuInCategory = 60;
        } else {
            temp_customer_serviceScrollView60.setVisibility(View.GONE);
        }


        //GlobalMemberValues.logWrite("categorymenusulog", "tempServiceSuInCategory : " + tempServiceSuInCategory + "\n");
        // 스크롤을 맨 위로
        tempServiceScrollView60.fullScroll(ScrollView.FOCUS_UP);
        tempServiceScrollView72.fullScroll(ScrollView.FOCUS_UP);


        tempServiceScrollView60.setVisibility(View.GONE);
        tempServiceScrollView72.setVisibility(View.GONE);


        Animation animation1;
        // 애니메이션 옵션 저장
        animation1 = AnimationUtils.loadAnimation(MainActivity.mContext, R.anim.act_in_top);
        //Animation animation = new AlphaAnimation(0, 1);
        animation1.setDuration(200);

        switch (tempServiceSuInCategory) {
            case 60 : {

                if (GlobalMemberValues.is_customerMain){
                    temp_customer_serviceScrollView60.setVisibility(View.VISIBLE);
                    temp_customer_serviceScrollView60.setAnimation(animation1);

                    tempSelectedSV = temp_customer_serviceScrollView60;

                    GlobalMemberValues.logWrite("categorymenusulog", "여기열림 : customer_60" + "\n");
                    break;
                } else {
                    tempServiceScrollView60.setVisibility(View.VISIBLE);
                    tempServiceScrollView60.setAnimation(animation1);

                    tempSelectedSV = tempServiceScrollView60;

                    GlobalMemberValues.logWrite("categorymenusulog", "여기열림 : 60" + "\n");
                    break;
                }

            }
            case 72 : {
                tempServiceScrollView72.setVisibility(View.VISIBLE);
                tempServiceScrollView72.setAnimation(animation1);

                tempSelectedSV = tempServiceScrollView72;

                GlobalMemberValues.logWrite("categorymenusulog", "여기열림 : 72" + "\n");
                break;
            }
            default : {
                tempServiceScrollView60.setVisibility(View.VISIBLE);
                tempServiceScrollView60.setAnimation(animation1);

                tempSelectedSV = tempServiceScrollView60;

                GlobalMemberValues.logWrite("categorymenusulog", "여기열림 : 60" + "\n");

                break;
            }
        }

        // ---------------------------------------------------------------------------------------

        // 카테고리 인덱스값이 없을 경우 첫번째 카테고리의 인덱스를 가져온다.
        if (cateIdx == null || cateIdx == "" || cateIdx.equals("")) {
            MainTopCategory mMainTopCate = new MainTopCategory(mActivity, context, dataAtSqlite, 0);
            cateIdx = mMainTopCate.getFirstCategoryIdx();
        } else {
//            Button initServiceBtn = null;
            TextView initServiceText = null;
            // 서비스를 가져오기전 서비스 버튼들의 텍스트를 초기화 한다.
            for (int i = 0; i < tempServiceSuInCategory; i++) {
//                initServiceBtn = (Button)tempSelectedSV.findViewWithTag("middleServiceButtonTag" + (i+1));
                initServiceText = (TextView)tempSelectedSV.findViewWithTag("middleServiceItemNameTag" + (i+1));
//                initServiceBtn.setText("");
                initServiceText.setText("");
                //initServiceBtn.setBackgroundColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKBACKGROUNDCOLOR1));
//                initServiceBtn.setBackgroundResource(R.drawable.aa_images_menubutton);
                initServiceText.setBackgroundResource(R.drawable.aa_images_menubutton);
                // Lite 버전 관련
                if (GlobalMemberValues.isLiteVersion()) {
//                    initServiceBtn.setBackgroundResource(R.drawable.aa_images_menubutton_lite);
                    initServiceText.setBackgroundResource(R.drawable.aa_images_menubutton_lite);
                }
//                initServiceBtn.setTag(initServiceBtn.getId(), null);        // 태그값도 초기화
                initServiceText.setTag(initServiceText.getId(), null);        // 태그값도 초기화

//                LinearLayout middleServiceLn = (LinearLayout)tempSelectedSV.findViewWithTag("middleServiceLinearLayoutTag" + (i+1));
//                middleServiceLn.setVisibility(View.GONE);
            }
        }

        // 01262023
        mCateIdx = cateIdx;

        GlobalMemberValues.logWrite("categorymenusulog", "cateIdx : " + cateIdx + "\n");

        // 서비스 배열 가져오기 (GetDataAtSQLite 클래스의 getServiceInfo 메소드를 통해 가져온다)
        String[] strArrService = dataAtSqlite.getServiceInfo(cateIdx, tempServiceSuInCategory);

        String strServiceInfo = "";        // Split 을 통해 가져오는 String 값 저장용 객체 선언
        String tempServicePositionNo = "";     // 임시 PositionNo 를 저장할 객체 선언

        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            GlobalMemberValues.BASICSERVICENAMETEXTSIZE = 16;
        }

        float serviceBtnTextSize = GlobalMemberValues.BASICSERVICENAMETEXTSIZE;
        if (GlobalMemberValues.isDeviceElo()) {
            serviceBtnTextSize = 20;//GlobalMemberValues.BASICSERVICENAMETEXTSIZE_FORELO;
        }

        if (GlobalMemberValues.isDeviceSunmiFromDB()){
            serviceBtnTextSize = 15;
        }
        if (GlobalMemberValues.isDevicePAXFromDB()){
            serviceBtnTextSize = 12;
        }

        // 여기 추가
        int tempServiceSuInCategory_in_use = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        "select max(positionNo) from salon_storeservice_sub where midx = '" + cateIdx + "' " +
                                " and not(positionNo is null or positionNo = '') "
                )
        );
        int line_menu_su = 0;
        int linesu = 0;
        int totalLinesu = 0;
        switch (tempServiceSuInCategory) {
            case 60 : {
                totalLinesu = 14;
                line_menu_su = 5;
                linesu = (tempServiceSuInCategory_in_use / 5 );
                if (tempServiceSuInCategory_in_use % 5 > 0) {
                    linesu = linesu + 1;
                }
                break;
            }
            case 72 : {
                totalLinesu = 12;
                line_menu_su = 6;
                linesu = (tempServiceSuInCategory_in_use / 6 );
                if (tempServiceSuInCategory_in_use % 6 > 0) {
                    linesu = linesu + 1;
                }
                break;
            }
            default : {
                totalLinesu = 14;
                line_menu_su = 5;
                linesu = (tempServiceSuInCategory_in_use / 5 );
                if (tempServiceSuInCategory_in_use % 5 > 0) {
                    linesu = linesu + 1;
                }
                break;
            }
        }

        TableRow tempTR = null;
        for (int i = 0; i < (totalLinesu); i++) {
            tempTR = (TableRow) tempSelectedSV.findViewWithTag("tablerowNo" + (i+1));
            tempTR.setVisibility(View.GONE);
        }
        for (int i = 0; i < linesu; i++) {
            tempTR = (TableRow) tempSelectedSV.findViewWithTag("tablerowNo" + (i+1));
            tempTR.setVisibility(View.VISIBLE);
        }

        int templinecunt = linesu * line_menu_su;
        // 해당 스토어의 최대 서비스수(GlobalMemberValues.STOREMAXSERVICESU) 만큼 FOR 구문 돌린다.

        GlobalMemberValues.logWrite("mmlogjjj", "templinecunt : " + templinecunt + "\n");
        int tempIndex = 0;
        for (int i = 0; i < templinecunt; i++) {
            if (strArrService[i] != null && strArrService[i] != "" && !strArrService[i].equals("")) {
                strServiceInfo = strArrService[i];
                final String[] strServiceInfoArr = strServiceInfo.split(GlobalMemberValues.STRSPLITTER1);
                tempServicePositionNo = strServiceInfoArr[5];

                if (Integer.parseInt(tempServicePositionNo) > 0) {         // 포지션값이 0 이상 값일때에만
                    boolean isBgImage = false;
                    String strFilePath = "";
                    /** 다운로드한 서비스 이미지가 있는지 체크한다. ********************************************/
                    if (!GlobalMemberValues.isStrEmpty(strServiceInfoArr[3])) {
                        // 이미지 경로 + 파일명 만들기
                        strFilePath = Environment.getExternalStorageDirectory().toString() +
                                "/" + GlobalMemberValues.STATION_CODE +
                                "/" + GlobalMemberValues.FOLDER_SERVICEIMAGE +
                                "/serviceimg_" + strServiceInfoArr[0] + ".png";
                        if (new File(strFilePath).exists() == false) {
                            //GlobalMemberValues.logWrite("savedFileCheck", "파일이 없습니다.");
                            isBgImage = false;
                        } else {
                            isBgImage = true;
                        }
                    } else {
                        isBgImage = false;
                    }
                    /****************************************************************************************/

                    // 배경색 구하기
                    String tempColorNo = strServiceInfoArr[16];
                    if (GlobalMemberValues.isStrEmpty(tempColorNo)) {
                        tempColorNo = "0";
                    }

                    // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
                    LinearLayout middleServiceLn = (LinearLayout)tempSelectedSV.findViewWithTag("middleServiceLinearLayoutTag" + tempServicePositionNo);
                    if(GlobalMemberValues.is_customerMain){
                        TableRow.LayoutParams middleServiceLn_Params = new TableRow.LayoutParams(middleServiceLn.getWidth(),320);
                        middleServiceLn_Params.setMargins(1,1,1,1);
                        middleServiceLn.setLayoutParams(middleServiceLn_Params);
                    } else {
                        TableRow.LayoutParams middleServiceLn_Params = new TableRow.LayoutParams(middleServiceLn.getWidth(),200);
                        middleServiceLn_Params.setMargins(2,2,2,2);
                        middleServiceLn.setLayoutParams(middleServiceLn_Params);
                    }


                    middleServiceLn.setVisibility(View.VISIBLE);

                    Button middleServiceBtn = (Button)tempSelectedSV.findViewWithTag("middleServiceButtonTag" + tempServicePositionNo);

                    String tepmServiceName = strServiceInfoArr[2];
                    if (tepmServiceName == null) {
                        tepmServiceName = "";
                    }
//                    middleServiceBtn.setAllCaps(false);
                    middleServiceBtn.setText(GlobalMemberValues.changeBrLetter(tepmServiceName));
                    middleServiceBtn.setTextSize(0);
//                    middleServiceBtn.setTextSize(serviceBtnTextSize);

                    TextView middleServiceNameText = (TextView)tempSelectedSV.findViewWithTag("middleServiceItemNameTag" + tempServicePositionNo);
                    if (tepmServiceName == null) {
                        tepmServiceName = "";
                    }
                    middleServiceNameText.setAllCaps(false);
                    middleServiceNameText.setText(GlobalMemberValues.changeBrLetter(tepmServiceName));
                    //middleServiceBtn.setTextSize(15);
                    middleServiceNameText.setTextColor(Color.parseColor("#000000"));
                    middleServiceNameText.setTextSize(serviceBtnTextSize);

                    TextView middleServicePriceText = (TextView)tempSelectedSV.findViewWithTag("middleServiceItemPriceTag" + tempServicePositionNo);
                    if (GlobalMemberValues.isDeviceSunmiFromDB()){
                        middleServicePriceText.setTextSize(serviceBtnTextSize);
                    }
                    if (GlobalMemberValues.isDevicePAXFromDB()){
                        middleServicePriceText.setTextSize(serviceBtnTextSize);
                    }

                    if (GlobalMemberValues.getIntAtString(tempColorNo) == 0) {
                        middleServicePriceText.setTextColor(Color.parseColor(paramCategoryColorValue));
                    } else {
                        String tempColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(tempColorNo) - 1];
                        middleServicePriceText.setTextColor(Color.parseColor(tempColorValue));
                    }
                    middleServicePriceText.setText("$ "+ GlobalMemberValues.getCommaStringForDouble(strServiceInfoArr[6]));

                    //middleServiceBtn.setBackgroundColor(Color.parseColor(this.categoryColorValue));
                    if (!isBgImage) {
                        if (GlobalMemberValues.getIntAtString(tempColorNo) == 0) {
                            getServiceButtonColor(paramCategoryColorValue);
                        } else {
                            String tempColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(tempColorNo) - 1];
                            getServiceButtonColor(tempColorValue);
                        }
                        //middleServiceLn.setBackgroundColor(Color.parseColor("#00000000"));
//                        middleServiceBtn.setBackgroundColor(Color.parseColor(paramCategoryColorValue));
//                        middleServiceBtn.setBackgroundResource(this.categoryColorXmlValue);
//                        middleServiceLn.setBackgroundResource(R.drawable.aa_images_menubutton);

                        if (GlobalMemberValues.is_customerMain){
                            middleServiceLn.setBackgroundColor(Color.parseColor("#1e2545"));
                        } else {
                            middleServiceLn.setBackgroundResource(this.categoryColorXmlValue);
                        }

                        middleServiceBtn.setBackgroundResource(R.drawable.aa_images_menubutton);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,tempServiceSuInCategory - 12);
                        params.setMargins(5,5,5,5);
                        middleServiceBtn.setLayoutParams(params);
                        // Lite 버전 관련
                        if (GlobalMemberValues.isLiteVersion()) {
                            middleServiceLn.setBackgroundResource(R.drawable.aa_images_menubutton_lite);
                        }
                        middleServiceBtn.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_ONCLICKTEXTCOLOR1));

                        // Lite 버전 관련
                        if (GlobalMemberValues.isLiteVersion()) {
                            middleServiceBtn.setTextColor(Color.parseColor("#ffffff"));
                        }

                        // 이미지 없을때 사이즈 조정
                        middleServiceBtn.setVisibility(View.GONE);
                    } else {
                        if (GlobalMemberValues.getIntAtString(tempColorNo) == 0) {
                            getServiceButtonColor(paramCategoryColorValue);
                        } else {
                            String tempColorValue = GlobalMemberValues.CATEGORYCOLORVALUE[GlobalMemberValues.getIntAtString(tempColorNo) - 1];
                            getServiceButtonColor(tempColorValue);
                        }
                        GlobalMemberValues.logWrite("savedFileCheck", "파일명 : " + strFilePath + "\n");
                        GlobalMemberValues.logWrite("savedFileCheck", "파일이 있습니다." + "\n");
                        // Bitmap 으로 변환
                        Bitmap serviceBgImageBm = BitmapFactory.decodeFile(strFilePath);
                        Drawable bgDrawable = new BitmapDrawable(serviceBgImageBm);
//                        middleServiceLn.setBackground(bgDrawable);
//                        middleServiceLn.setBackgroundResource(GlobalMemberValues.CATEGORYCOLORXMLVALUE_DEFAULT_TRANS);
                        if (GlobalMemberValues.is_customerMain){
                            middleServiceLn.setBackgroundColor(Color.parseColor("#1e2545"));
                        } else {
                            middleServiceLn.setBackgroundResource(this.categoryColorXmlValue);
                        }
                        middleServiceBtn.setBackground(bgDrawable);
//                        middleServiceBtn.getBackground().setColorFilter(Color.parseColor("#8D8D8D"), PorterDuff.Mode.MULTIPLY);

                        if (GlobalMemberValues.is_customerMain){
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(1,1,1,1);
                            middleServiceBtn.setLayoutParams(params);
                        } else {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,tempServiceSuInCategory - 12);
                            params.setMargins(5,5,5,5);
                            middleServiceBtn.setLayoutParams(params);
                        }


                        middleServiceBtn.setTextColor(Color.parseColor("#ffffff"));

                        // 이미지 없을때 사이즈 조정
                        middleServiceBtn.setVisibility(View.VISIBLE);
                    }

                    if (GlobalMemberValues.is_customerMain){
                        middleServiceNameText.setTextColor(Color.parseColor("#ffffff"));
                        middleServiceNameText.setBackgroundColor(Color.parseColor("#1e2545"));
                    }

                    middleServiceBtn.setPadding(3,3,3,3);

                    // Service 의 정보를 임시로 저장하기 위한 클래스인 TemporaryServiceInfo 를 이용하여 카테고리정보 저장
                    TemporaryServiceInfo tempServiceInfo = new TemporaryServiceInfo(strServiceInfoArr[0], strServiceInfoArr[1],
                            tepmServiceName, strServiceInfoArr[3], strServiceInfoArr[4], strServiceInfoArr[5],
                            strServiceInfoArr[6], strServiceInfoArr[7], strServiceInfoArr[8], strServiceInfoArr[9], strServiceInfoArr[10],
                            strServiceInfoArr[11], strServiceInfoArr[12], strServiceInfoArr[13], strServiceInfoArr[14], "");

                    // 위에서 생성한 클래스를 Tag 값으로 저장 (setTag)
                    // setTag 로 객체 저장시 서비스 버튼의 아이디를 KEY 값으로 사용한다.
                    if (tempServiceInfo != null) {
                        tempServiceInfo.svcCategoryColor = paramCategoryColorValue;
                        middleServiceBtn.setTag(middleServiceBtn.getId(), tempServiceInfo);
                        middleServiceNameText.setTag(middleServiceNameText.getId(), tempServiceInfo);
                    }

                    if (!GlobalMemberValues.isStrEmpty(strServiceInfoArr[2])) {
                        if (tempIndex == 0) {
                            mTouchedView_fromMenuSearch = middleServiceBtn;
                        }
                        tempIndex++;
                        // 서비스버튼 클릭시 이벤트
                        middleServiceBtn.setOnClickListener(mServiceButtonListner);
                        middleServiceNameText.setOnClickListener(mServiceTextViewListner);
                        middleServicePriceText.setOnClickListener(mServicePriceTextViewListner);
//                        middleServiceNameText.setOnClickListener(mServiceButtonListner);
                        // 서비스버튼 길게 클릭시 이벤트
                        middleServiceBtn.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Intent serviceDetailPopupIntent = new Intent(MainActivity.mContext, ServiceDetailPopup.class);
                                serviceDetailPopupIntent.putExtra("ServiceIdx", strServiceInfoArr[0]);
                                mActivity.startActivity(serviceDetailPopupIntent);
                                if (GlobalMemberValues.isUseFadeInOut()) {
                                    mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
                                }
                                return true;
                            }
                        });
                    }

                    GlobalMemberValues.logWrite("MainActivity-MiddleService", "서비스 정보 " + i + " : " + strArrService[i] + "\n");
                }
            } else {
                LinearLayout middleServiceLn = (LinearLayout)tempSelectedSV.findViewWithTag("middleServiceLinearLayoutTag" + (i+1));
                TableRow.LayoutParams middleServiceLn_Params = new TableRow.LayoutParams(middleServiceLn.getWidth(),200);
                middleServiceLn_Params.setMargins(2,2,2,2);
                middleServiceLn.setLayoutParams(middleServiceLn_Params);
                middleServiceLn.setVisibility(View.VISIBLE);
                //middleServiceLn.setLayoutParams(new LinearLayout.LayoutParams(middleServiceLn.getWidth(),(middleServiceLn.getHeight() + 50)));
                middleServiceLn.setBackgroundColor(Color.TRANSPARENT);


                Button middleServiceBtn = (Button)tempSelectedSV.findViewWithTag("middleServiceButtonTag" + (i+1));
                middleServiceBtn.setBackgroundColor(Color.TRANSPARENT);
                middleServiceBtn.setBackgroundResource(R.drawable.aa_images_menubutton);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,tempServiceSuInCategory - 12);
                params.setMargins(5,5,5,5);
                middleServiceBtn.setLayoutParams(params);
                middleServiceBtn.setOnClickListener(null);
                middleServiceBtn.setVisibility(View.GONE);


                TextView middleServiceNameText = (TextView)tempSelectedSV.findViewWithTag("middleServiceItemNameTag" + (i+1));
                middleServiceNameText.setText("");
                middleServiceNameText.setOnClickListener(null);
                TextView middleServicePriceText = (TextView)tempSelectedSV.findViewWithTag("middleServiceItemPriceTag" + (i+1));
                middleServicePriceText.setText("");
                middleServicePriceText.setOnClickListener(null);

                if (GlobalMemberValues.is_customerMain){
                    middleServiceLn.setVisibility(View.INVISIBLE);
                } else {

                }


//                // 여기 추가
//                if (line_menu_su != 0) {
//                    if (i / line_menu_su > linesu){
////                if (i > tempServiceSuInCategory_in_use){
//                        middleServiceLn.setVisibility(View.GONE);
//                    }
//                }

            }
        }

//        for (int i = (templinecunt - 1); i < tempServiceSuInCategory; i++) {
//            LinearLayout middleServiceLn = (LinearLayout)tempSelectedSV.findViewWithTag("middleServiceLinearLayoutTag" + (i+1));
//            middleServiceLn.setVisibility(View.GONE);
//        }

//        ServiceListAdapter adapter = new ServiceListAdapter(serviceListArrayList);
//        tempSelectedSV.setAdapter(adapter);

        // CANCEL 버튼 클릭시 (리스트 항목 전체 삭제)
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONCANCEL.setOnClickListener(mainSaleCartlButtonClick);

        if (GlobalMemberValues.is_customerMain){
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MAIN_CANCEL.setOnClickListener(mainSaleCartlButtonClick);
        }

        GlobalMemberValues.logWrite("servicejjjlog", "서비스 배치가 끝났음...\n");
    }
    /*******************************************************************************/

    public static void openEditModifier(String paramSvcIdx, String paramModifierCode, int paramPosition, TemporarySaleCart paramtempSaleCart) {
        Intent openIntent = new Intent(MainActivity.mContext, MainMiddleServiceModifer.class);
        openIntent.putExtra("ServiceIdx", paramSvcIdx);
        openIntent.putExtra("modifiercode", paramModifierCode);
        openIntent.putExtra("position", paramPosition + "");
        openIntent.putExtra("ParentTemporarySaleCartInstance", paramtempSaleCart);
        mActivity.startActivity(openIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
        }
    }

    View.OnClickListener mServiceButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 네트워크 상태 체크
            // GlobalMemberValues.checkOnlineService(MainActivity.mContext, MainActivity.mActivity);

            // All Discount / Extra 가 실행된지 체크후 -------------------------------
            // 실행후라면 이후의 코드를 실행하지 않는다.
            if (!checkAllDiscountExtra()) return;
            // -----------------------------------------------------------------------

            // 선택한 직원 체크
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO == null) {
                GlobalMemberValues.displayDialog(context, "Warning", "Choose a employee", "Close");
                return;
            } else {
                // ADMIN 접속일 경우 선택못하게..
                if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx)) {
                    GlobalMemberValues.displayDialog(context, "Warning", "Choose a employee", "Close");
                    return;
                }
            }

            Button btn = (Button)v;
            // 서비스 메뉴명이 있을 때에만..
            if (!GlobalMemberValues.isStrEmpty(btn.getText().toString())) {
                if (btn.getText().toString() != "") GlobalMemberValues.str_logsaveIn_MenuNames = btn.getText().toString();
                LogsSave.saveLogsInDB(99);
                // 현재 클릭한 서비스버튼에 Tag 저장되어 있는 TemporaryCategory 객체를 가져온다.
                TemporaryServiceInfo tempCif = (TemporaryServiceInfo)btn.getTag(btn.getId());
                // ★★★★★★★★반드시 해당 객체가 있는지 체크해 준다★★★★★★★★

                selectService(tempCif, btn);

            }
        }
    };

    View.OnClickListener mServiceTextViewListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tempServiceSuInCategory = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(
                            "select menusu from salon_storeservice_main where idx = '" + mCateIdx + "' "
                    )
            );
            if (tempServiceSuInCategory == 0) {
                tempServiceSuInCategory = GlobalMemberValues.STOREMAXSERVICESU;
            }
            ScrollView tempServiceScrollView = null;
            switch (tempServiceSuInCategory) {
                case 60 : {
                    if (GlobalMemberValues.is_customerMain){
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("customer_servicemenulayout_60");
                        break;
                    } else {
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag60");
                        break;
                    }

                }
                case 72 : {
                    tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag72");
                    break;
                }
                default : {
                    if (GlobalMemberValues.is_customerMain){
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("customer_servicemenulayout_60");
                        break;
                    } else {
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag60");
                        break;
                    }
                }
            }

            String tempTag = (String)v.getTag();
            tempTag = tempTag.replace("middleServiceItemNameTag","");
            Button middleServiceBtn = (Button)tempServiceScrollView.findViewWithTag("middleServiceButtonTag" + tempTag);
            middleServiceBtn.performClick();

        }
    };

    View.OnClickListener mServicePriceTextViewListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tempServiceSuInCategory = GlobalMemberValues.getIntAtString(
                    MainActivity.mDbInit.dbExecuteReadReturnString(
                            "select menusu from salon_storeservice_main where idx = '" + mCateIdx + "' "
                    )
            );
            if (tempServiceSuInCategory == 0) {
                tempServiceSuInCategory = GlobalMemberValues.STOREMAXSERVICESU;
            }
            ScrollView tempServiceScrollView = null;
            switch (tempServiceSuInCategory) {
                case 60 : {
                    if (GlobalMemberValues.is_customerMain){
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("customer_servicemenulayout_60");
                        break;
                    } else {
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag60");
                        break;
                    }
                }
                case 72 : {
                    tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag72");
                    break;
                }
                default : {
                    if (GlobalMemberValues.is_customerMain){
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("customer_servicemenulayout_60");
                        break;
                    } else {
                        tempServiceScrollView = (ScrollView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINMIDDLESERVICEPARENT.findViewWithTag("serviceLayoutTag60");
                        break;
                    }
                }
            }

            String tempTag = (String)v.getTag();
            tempTag = tempTag.replace("middleServiceItemPriceTag","");
            Button middleServiceBtn = (Button)tempServiceScrollView.findViewWithTag("middleServiceButtonTag" + tempTag);
            middleServiceBtn.performClick();
        }
    };

    public static void selectService(TemporaryServiceInfo tempCif, Button btn) {
        if (tempCif != null) {
            if (tempCif.svcMdx != null) {
                String tempSvcIdx = GlobalMemberValues.getConvertText(tempCif.svcIdx);
                String tempSvcMdx = GlobalMemberValues.getConvertText(tempCif.svcMdx);
                String tempSvcCategoryName = GlobalMemberValues.getConvertText(tempCif.svcCategoryName);
                String tempSvcServiceName = GlobalMemberValues.getConvertText(tempCif.svcServiceName);
                String tempSvcStrFileName = GlobalMemberValues.getConvertText(tempCif.svcStrFileName);
                String tempSvcStrFilePath = GlobalMemberValues.getConvertText(tempCif.svcStrFilePath);
                String tempSvcPositionNo = GlobalMemberValues.getConvertText(tempCif.svcPositionNo);
                String tempSvcServicePrice = GlobalMemberValues.getConvertText(tempCif.svcServicePrice);
                String tempSvcCommissionRatioType = GlobalMemberValues.getConvertText(tempCif.svcCommissionRatioType);
                String tempSvcCommissionRatio = GlobalMemberValues.getConvertText(tempCif.svcCommissionRatio);
                String tempSvcPointRatio = GlobalMemberValues.getConvertText(tempCif.svcPointRatio);
                String tempSvcSalePrice = GlobalMemberValues.getConvertText(tempCif.svcSalePrice);
                String tempSvcSaleStart = GlobalMemberValues.getConvertText(tempCif.svcSaleStart);
                String tempSvcSaleEnd = GlobalMemberValues.getConvertText(tempCif.svcSaleEnd);
                String tempSvcSetMenuYN = GlobalMemberValues.getConvertText(tempCif.svcSetMenuYN);
                String tempSvcCategoryColor = GlobalMemberValues.getConvertText(tempCif.svcCategoryColor);
                //GlobalMemberValues.logWrite("MainMiddleService-TempInstance", "서비스 인덱스 : " + paramCateIdx + "\n");

                /**
                 GlobalMemberValues.logWrite("ClickServiceInfo", "-------- 클릭한 서비스 정보 ---------\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "idx : " + tempSvcIdx + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Midx : " + tempSvcMdx + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Service Name : " + tempSvcServiceName + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "FileName : " + tempSvcStrFileName + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "FilePath : " + tempSvcStrFilePath + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "PositionNo : " + tempSvcPositionNo + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Price : " + tempSvcServicePrice + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Commission Type : " + tempSvcCommissionRatioType + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Commission Ratio : " + tempSvcCommissionRatio + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Point Ratio : " + tempSvcPointRatio + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Sale Price : " + tempSvcSalePrice + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Sale Start : " + tempSvcSaleStart + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Sale End : " + tempSvcSaleEnd + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "Set Meny YN : " + tempSvcSetMenuYN + "\n");
                 GlobalMemberValues.logWrite("ClickServiceInfo", "----------------------------------\n");
                 **/

                /** 데이터베이스 temp_salecart 에 먼저 저장한다.******************************************************/
                String sHoldCode = mHoldCode;                      // 홀드코드 (처음저장시에는 값이 없음)
                String sQty = "1";                          // 수량 (처음저장시 무조건 1)
                String sSaveType = "0";                     // 0 : 서비스       1: 상품          2: 기프트 카드

                String sQuickSaleYN = "N";                  // quick sale 여부

                // 고객 및 직원 정보 셋팅
                setCustomerEmployeeInfo();

                String sCustomerId = insCustomerId;
                String sCustomerName = insCustomerName;
                String sCustomerPhone = insCustomerPhone;

                // 직원정보 구하기
                String sEmpIdx = insEmpIdx;
                String sEmpName = insEmpName;

                GlobalMemberValues.logWrite("MainMiddleService", "tempSvcCategoryName : " + tempSvcCategoryName + "\n");

                // insertTempSaleCart 메소드에 전달할 값을 String 배열로 만든다.
                String paramsString[] = {
                        sQty, sHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, tempSvcMdx, tempSvcIdx,
                        tempSvcServiceName, tempSvcStrFileName, tempSvcStrFilePath,
                        tempSvcServicePrice, tempSvcSalePrice, tempSvcSaleStart, tempSvcSaleEnd,
                        tempSvcCommissionRatio, tempSvcCommissionRatioType, tempSvcPointRatio,
                        tempSvcPositionNo, tempSvcSetMenuYN,
                        sCustomerId, sCustomerName, sCustomerPhone, sSaveType, sEmpIdx, sEmpName, sQuickSaleYN,
                        tempSvcCategoryName, "", "", "", tempSvcCategoryColor
                };

                //maxIdxAfterDbInsert = insertTempSaleCart(paramsString);
                //if (!GlobalMemberValues.isStrEmpty(maxIdxAfterDbInsert)) {        // DB insert 후 MAXIDX 가 리턴되었을 때
                // 리스트뷰에 선택했던 서비스(상품) 들을 리스팅한다.
                //    GlobalMemberValues.logWrite("DBINSERTRESULT", "선택 서비스 데이터 MAXIDX - " + maxIdxAfterDbInsert + "\n");
                //}


                /***************************************************************************************/

                // 현재 클릭한 버튼객체를 preClickButton 에 담는다.
                preClickButton = btn;

                // Scale 을 사용하는 아이템인지 여부
                String tempScaleUseYN = MainActivity.mDbInit.dbExecuteReadReturnString(" select scaleuseyn from salon_storeservice_sub where idx = '" + tempSvcIdx + "' ");
                if (GlobalMemberValues.isStrEmpty(tempScaleUseYN)) {
                    tempScaleUseYN = "N";
                }

                //tempScaleUseYN = "Y";
                if (tempScaleUseYN == "Y" || tempScaleUseYN.equals("Y")) {
                    if (MainActivity.mUsbReceiver != null) {
                        //GlobalMemberValues.displayDialog(MainActivity.mContext, "", "Under Construction...", "Close");

                        // 메뉴버튼 비활성화 (두번 누르는거 방지)
                        preClickButton.setEnabled(false);
                        // 저울관련 팝업
                        Intent tempIntent = new Intent(MainActivity.mContext, MainMiddleService_ScaleWeight.class);
                        tempIntent.putExtra("ServiceIdx", tempSvcIdx);
                        mActivity.startActivity(tempIntent);
                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }

                    } else {
                        GlobalMemberValues.displayDialog(context, "Warning", "Scale is not connected", "Close");
                    }
                } else {
//                    // 옵션, Add 있는지 체크
//                    checkHasOption(btn, paramsString);

                    // 2022 01 06 추가. dynimic price
                    if (GlobalMemberValues.isDynamicPrice(tempSvcIdx)){
                        Intent dynamicPriceIntent = new Intent(context.getApplicationContext(), DynamicPrice.class);
                        // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                        dynamicPriceIntent.putExtra("paramsString", paramsString);
                        // -------------------------------------------------------------------------------------
                        mActivity.startActivity(dynamicPriceIntent);
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        dynamicPrice_tempView = (View)btn;
                    } else {
                        checkHasOption(btn, paramsString);
                    }
                }
            }
        }
    }

    public static void checkHasOption(View v, String[] paramsString) {
        // 가장먼저 뷰 객체를 static 변수에 담는다.
        mTouchedView = v;
        mTouchedArr = paramsString;

        String tempSvcIdx = paramsString[5];
        int optionCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option where svcidx = " + tempSvcIdx));
        int additionalCount = GlobalMemberValues.getIntAtString(
                dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_additional where svcidx = " + tempSvcIdx));

        // 옵션&추가사항 객체에 값 할당전 초기화 -------
        mModifierCode = "";
        mModifierIdx = "";
        mOptionTxt = "";
        mOptionPrice = "0";
        mAdditionalTxt1 = "";
        mAdditionalprice1 = "0";
        mAdditionalTxt2 = "";
        mAdditionalprice2 = "0";

        mMemoToKitchen = "";
        // -------------------------------------------

        // modifier force y/n 값을 확인해서 ------------------------------------------------------------------------
        // y 일 경우 : 무조건 modifier 창을 띄우고
        // n 일 경우 : modifier 창을 띄우지 않고 바로 담는다
        String mdforceyn = MainActivity.mDbInit.dbExecuteReadReturnString(" select mdforceyn from salon_storeservice_sub where idx = '" + tempSvcIdx + "' ");
        if (GlobalMemberValues.isStrEmpty(mdforceyn)) {
            mdforceyn = "Y";
        }
        if (mdforceyn == "N" || mdforceyn.equals("N")) {
            optionCount = 0;
            additionalCount = 0;
        }
        // ------------------------------------------------------------------------------------------------------

        if (optionCount > 0 || additionalCount > 0) {
            if (GlobalMemberValues.getFoodModifierType(tempSvcIdx).equals("A")) {
                MainActivity.proCustomDial = new JJJCustomAnimationDialog(MainActivity.mContext);
                MainActivity.proCustomDial.show();

                Intent mainMiddleServiceOptionAdd = new Intent(MainActivity.mContext, MainMiddleServiceOptionAdd.class);
                mainMiddleServiceOptionAdd.putExtra("ServiceIdx", tempSvcIdx);
                mActivity.startActivity(mainMiddleServiceOptionAdd);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                }
            } else {
                MainActivity.proCustomDial = new JJJCustomAnimationDialog(MainActivity.mContext);
                MainActivity.proCustomDial.show();

                Intent mainMiddleServiceOptionAdd = new Intent(MainActivity.mContext, MainMiddleServiceModifer.class);
                mainMiddleServiceOptionAdd.putExtra("ServiceIdx", tempSvcIdx);
                mainMiddleServiceOptionAdd.putExtra("modifiercode", "");
                mainMiddleServiceOptionAdd.putExtra("position", "");
                mActivity.startActivity(mainMiddleServiceOptionAdd);
                if (GlobalMemberValues.isUseFadeInOut()) {
                    mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                }
            }

        } else {
            // 애니메이션 처리...
            animServices(mTouchedView, paramsString);
        }
    }

    public static void dynamicPriceSubmit(String[] paramsString){
        // 애니메이션 처리...
//        animServices(dynamicPrice_tempView, paramsString);
        checkHasOption(dynamicPrice_tempView, paramsString);
    }

    public static void animServices(View v, final String[] paramsString) {
        final String[] maxIdxAfterDbInsert = {""};
        if (GlobalMemberValues.ITEMADDANIMATIONYN == "Y" || GlobalMemberValues.ITEMADDANIMATIONYN.equals("Y")) {
            /** 애니메이션 처리... ******************************************************************/
            // 먼저 ListView 담긴 아이템 수를 구한다.
            float tempToYDelta;
            //int tempListViewSize = mGeneralArrayList.size();
            int tempListViewSize = mListviewItemCount;
            if (tempListViewSize < 8) {
                tempToYDelta = 0.0f + ((float)(tempListViewSize) * 0.13f);
            } else {
                tempToYDelta = ((float)(7) * 0.13f) - 0.07f;
            }
            /**
             if (tempListViewSize < 4) {
             tempToYDelta = 0.0f + ((float)(tempListViewSize) * 0.26f);
             } else {
             tempToYDelta = ((float)(4) * 0.26f) - 0.07f;
             }
             **/

            final ImageView iv = new ImageView(MainActivity.mContext);
            iv.setImageResource(mServiceBall);
            GlobalMemberValues.GLOBAL_LAYOUT_ROOT.addView(iv);
            GlobalMemberValues.GLOBAL_LAYOUT_ROOT.bringChildToFront(iv);

            int[] location = new int[2];
            if (v == null) {
                GlobalMemberValues.logWrite("animviewlog", "v 객체는 null 입니다." + "\n");
            } else {
                v.getLocationOnScreen(location);    // 서비스 버튼의 좌표값
            }

            // AnimationSet 을 이용하여 두개 이상 애니메이션 연결
            animSet = new AnimationSet(true);

            // 이동 애니메이션 -------------------------------------------------------
            animTrans = new TranslateAnimation (
                    Animation.ABSOLUTE,
                    location[0],           // fromXDelta

                    Animation.RELATIVE_TO_PARENT,
                    -0.48f,                 // toXDelta

                    Animation.ABSOLUTE,
                    location[1],           // fromYDelta

                    Animation.RELATIVE_TO_PARENT,
                    tempToYDelta                   // toYDelta
            );
            animTrans.setDuration(150);
            animSet.addAnimation(animTrans);
            // ---------------------------------------------------------------------

            // 크기 애니메이션 -------------------------------------------------------
            animScale = AnimationUtils.loadAnimation(
                    MainActivity.mContext, R.anim.anim_scale_alpha);


            animSet.addAnimation(animScale);
            // ---------------------------------------------------------------------

            iv.startAnimation(animSet);
            animTrans.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // common gratuity 관련
                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                    maxIdxAfterDbInsert[0] = insertTempSaleCart(paramsString);
                    if (!GlobalMemberValues.isStrEmpty(maxIdxAfterDbInsert[0])) {        // DB insert 후 MAXIDX 가 리턴되었을 때
                        // 리스트뷰에 선택했던 서비스(상품) 들을 리스팅한다.
                        GlobalMemberValues.logWrite("DBINSERTRESULT", "선택 서비스 데이터 MAXIDX - " + maxIdxAfterDbInsert[0] + "\n");
                    }

                    // common gratuity 관련
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                    iv.setVisibility(View.GONE);
                    //GlobalMemberValues.GLOBAL_LAYOUT_ROOT.removeView(iv);
                    animTrans.reset();
                    animSet.reset();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            /***************************************************************************************/
        } else {
            setInsertCartNoAnimation(paramsString);
        }


        // 05192023
        // 0519 -------------------------------------------------------------------------------------------------------
        // 같은 holdcode 로 등록된 salon_billprinted 테이블의 데이터를 삭제한다.
        String upQueryStr = " delete from salon_billprinted where holdcode = '" + mHoldCode + "' ";
        MssqlDatabase.executeTransactionByQuery(upQueryStr);
        // 0501 -------------------------------------------------------------------------------------------------------
    }

    public static void setInsertCartNoAnimation(String[] paramsString) {
        final String[] maxIdxAfterDbInsert = {""};

        // common gratuity 관련
        GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

        maxIdxAfterDbInsert[0] = insertTempSaleCart(paramsString);
        if (!GlobalMemberValues.isStrEmpty(maxIdxAfterDbInsert[0])) {        // DB insert 후 MAXIDX 가 리턴되었을 때
            // 리스트뷰에 선택했던 서비스(상품) 들을 리스팅한다.
            GlobalMemberValues.logWrite("DBINSERTRESULT", "선택 서비스 데이터 MAXIDX - " + maxIdxAfterDbInsert[0] + "\n");
        }

        // common gratuity 관련
        GlobalMemberValues.addCartLastItemForCommonGratuityUse();
    }

    /** TEMP_SALECART 테이블 입력 메소드 ******************************************************************************************/
    public static String insertTempSaleCart(String[] paramArrStr) {
        String maxIdx = "";
        if (paramArrStr != null) {
            String getParamsString[] = paramArrStr;

            int insSQty = 1;
            String tempSQty = getParamsString[0];
            if (!GlobalMemberValues.isStrEmpty(tempSQty)) {
                insSQty = Integer.parseInt(tempSQty);
            }

            String insHoldCode = getParamsString[1];                                 // Hold 코드 (최초입력할 때는 값이 없음.
            String insSidx = getParamsString[2];
            String insStcode = getParamsString[3];                                     // 스테이션 코드
            String insMidx = getParamsString[4];
            String insSvcidx = getParamsString[5];

            String insSvcName = GlobalMemberValues.getChangedStringAfterEnterStr(getParamsString[6]);
            String insSvcFileName = getParamsString[7];
            String insSvcFilePath = getParamsString[8];

            String svcServicePrice = getParamsString[9];
            String svcSalePrice = getParamsString[10];
            String svcSaleStart = getParamsString[11];
            String svcSaleEnd = getParamsString[12];

            String svcCommissionRatio = getParamsString[13];
            String svcCommissionRatioType = getParamsString[14];
            String svcPointRatio = getParamsString[15];

            String insSvcPositionNo = getParamsString[16];
            String insSvcSetMenuYN = getParamsString[17];

            String insCustomerId = getParamsString[18];
            String insCustomerName = getParamsString[19];
            String insCustomerPhoneNo = getParamsString[20];

            String insSaveType = getParamsString[21];

            String insEmpIdx = getParamsString[22];
            String insEmpName = getParamsString[23];

            if (!GlobalMemberValues.SERVER_IDX.isEmpty()){
                insEmpIdx = GlobalMemberValues.SERVER_IDX;
            }
            if (!GlobalMemberValues.SERVER_NAME.isEmpty()){
                insEmpName = GlobalMemberValues.SERVER_NAME;
            }

            String insQuickSaleYN = getParamsString[24];

            String insCategoryName = getParamsString[25];

            GlobalMemberValues.logWrite("MainMiddleService", "categoryName : " + insCategoryName + "\n");

            String insGiftCardNumber = "";
            if (getParamsString.length > 26) {
                insGiftCardNumber = getParamsString[26];
            }

            String insGiftCardSavePrice = "";
            if (getParamsString.length > 27) {
                insGiftCardSavePrice = getParamsString[27];
            }

            String insMaxIdx = "";
            if (getParamsString.length > 28) {
                insMaxIdx = getParamsString[28];
            }

            String insCategoryColor = "";
            if (getParamsString.length > 29) {
                insCategoryColor = getParamsString[29];
            }

            String insTaxExempt = "N";
            if (getParamsString.length > 30) {
                insTaxExempt = GlobalMemberValues.getStringFormatNumber(getParamsString[30], "2");
            }

            String insRcode = "";
            if (getParamsString.length > 31) {
                insRcode = getParamsString[31];
            }

            // togo deli type
            String insTogoDeliType = "H";
            if (getParamsString.length > 32) {
                insTogoDeliType = getParamsString[32];
            }

            // 처리전에 먼저 tempsalecart 테이블에 몇개의 항목이 담겨있는지 체크하여
            // 한개도 없을 경우 mListviewItemCount 의 값을 0 으로 초기화한다.
            int nowListCount = GlobalMemberValues.getIntAtString(
                    MssqlDatabase.getResultSetValueToString("select count(idx) from temp_salecart where holdCode = '" + insHoldCode + "' "));
            if (nowListCount == 0) {
                mListviewItemCount = 0;
            }

            if (!GlobalMemberValues.isStrEmpty(mOptionTxt) || !GlobalMemberValues.isStrEmpty(mAdditionalTxt1)
                    || !GlobalMemberValues.isStrEmpty(mAdditionalTxt2)) {
                mListviewItemCount += 2;
            } else {
                mListviewItemCount += 1;
            }

            GlobalMemberValues.logWrite("RECALLTAXEXEMPT", "taxexempt from recall = " + insTaxExempt + "\n");

            double insSPrice = 0.00;
            double insSvcOrgPrice = 0.00;          // 세일이전 원가
            if (!GlobalMemberValues.isStrEmpty(svcServicePrice)) {
                insSPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(svcServicePrice, "2"));
                insSvcOrgPrice = insSPrice;
            }

            // Time Menu 타임메뉴 관련 ----------------------------------------------------------------------------------------------------
            if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
                // Time Menu 일 경우 타임별 가격으로 한다.
                String tempTimemenuYN = MssqlDatabase.getResultSetValueToString(
                        " select timemenuyn from salon_storeservice_sub where idx = '" + insSvcidx + "' ");
                if (GlobalMemberValues.isStrEmpty(tempTimemenuYN)) {
                    tempTimemenuYN = "N";
                }

                GlobalMemberValues.logWrite("timemenulog", "tempTimemenuYN :  " + tempTimemenuYN + "\n");

                double tempTimeMenuPrice = 0;
                if (tempTimemenuYN.equals("Y") || tempTimemenuYN == "Y") {
                    GlobalMemberValues.logWrite("timemenulog", "tempTimemenuYN :  " + " select " + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "_" + GlobalMemberValues.getWeekDayNum() +
                            " from salon_storeservice_sub_timemenuprice where svcidx = '" + insSvcidx + "' " + "\n");

                    tempTimeMenuPrice = GlobalMemberValues.getDoubleAtString(
                            MssqlDatabase.getResultSetValueToString(
                                    " select " + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "_" + GlobalMemberValues.getWeekDayNum() +
                                            " from salon_storeservice_sub_timemenuprice where svcidx = '" + insSvcidx + "' ")
                    );
                }

                GlobalMemberValues.logWrite("timemenulog", "tempTimeMenuPrice :  " + tempTimeMenuPrice + "\n");

                if (tempTimeMenuPrice > 0) {
                    insSPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getCommaStringForDouble(tempTimeMenuPrice + ""));
                }
            }
            // --------------------------------------------------------------------------------------------------------------------------

            String insSSaleYN = "N";
            // 세일 기간이고, 세일가격에 값이 있을 때 가격을 세일가격으로 저장한다. ------------------------------
            if ((!GlobalMemberValues.isStrEmpty(svcSaleStart)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.getReplaceText(svcSaleStart, "/", "-"), GlobalMemberValues.STR_NOW_DATE) >= 0)
                    && (!GlobalMemberValues.isStrEmpty(svcSaleEnd)
                    && DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, GlobalMemberValues.getReplaceText(svcSaleEnd, "/", "-")) >= 0)
                    &&!GlobalMemberValues.isStrEmpty(svcSalePrice)) {
                insSPrice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(svcSalePrice, "2"));
                insSSaleYN = "Y";

//                GlobalMemberValues.logWrite("saledatepricelog", "GlobalMemberValues.STR_NOW_DATE : " + GlobalMemberValues.STR_NOW_DATE + "\n");
//                GlobalMemberValues.logWrite("saledatepricelog", "svcSaleStart : " + svcSaleStart + "\n");
//                GlobalMemberValues.logWrite("saledatepricelog", "svcSaleEnd : " + svcSaleEnd + "\n");
//
//                GlobalMemberValues.logWrite("saledatepricelog", "DateMethodClass.getDiffDayCount(svcSaleStart, GlobalMemberValues.STR_NOW_DATE) : " + DateMethodClass.getDiffDayCount(svcSaleStart, GlobalMemberValues.STR_NOW_DATE) + "\n");
//                GlobalMemberValues.logWrite("saledatepricelog", "DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, svcSaleEnd) : " + DateMethodClass.getDiffDayCount(GlobalMemberValues.STR_NOW_DATE, svcSaleEnd) + "\n");
            }
            // -------------------------------------------------------------------------------------

            // 옵션, 추가사항 가격
            double insOptionprice = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(mOptionPrice, "2"));
            double insAdditionalprice1 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(mAdditionalprice1, "2"));
            double insAdditionalprice2 = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(mAdditionalprice2, "2"));

            insSPrice += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((insOptionprice + insAdditionalprice1 + insAdditionalprice2), "2"));

            double insSpriceAmount = insSPrice * insSQty;

            // Tax, Tax x qty 구하기 ----------------------------------------------------------------
            double insSTax = 0.00;
            double insSTaxAmount = 0.00;
            double taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;

            if (MainMiddleService.mQuickSaleYN == "Y" || MainMiddleService.mQuickSaleYN.equals("Y")) {
                switch (insSaveType) {
                    case "0" : {        // 서비스일 때
                        taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;
                        break;
                    }
                    case "1" : {        // 상품일 때
                        taxToUse = GlobalMemberValues.STORE_PRODUCT_TAX;
                        break;
                    }
                    case "2" : {        // 기프트 카드일 때는 tax 가 안붙는다.
                        taxToUse = 0.0;
                        break;
                    }
                }



                if (GlobalMemberValues.getItemTaxFreeYNForQuickSale(MainActivity.mContext, insMidx) == "Y" ||
                        GlobalMemberValues.getItemTaxFreeYNForQuickSale(MainActivity.mContext, insMidx).equals("Y")) {
                    taxToUse = 0.00;
                }
            } else {
                switch (insSaveType) {
                    case "0" : {        // 서비스일 때
                        taxToUse = GlobalMemberValues.STORE_SERVICE_TAX;
                        // DB 에서 해당상품의 TAXFREEYN 을 가져온다.
                        if (GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "S", insSvcidx) == "Y" ||
                                GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "S", insSvcidx).equals("Y")) {
                            taxToUse = 0.00;
                        } else {
                            // TAX 타입이 멀티일 경우
                            if (GlobalMemberValues.isTaxTypeMulti("S", insSvcidx)) {
                                taxToUse = GlobalMemberValues.getItemTaxInMultiTax("S", insSvcidx);
                            }
                        }
                        break;
                    }
                    case "1" : {        // 상품일 때
                        taxToUse = GlobalMemberValues.STORE_PRODUCT_TAX;
                        // DB 에서 해당상품의 TAXFREEYN 을 가져온다.
                        if (GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "P", insSvcidx) == "Y" ||
                                GlobalMemberValues.getItemTaxFreeYN(MainActivity.mContext, "P", insSvcidx).equals("Y")) {
                            taxToUse = 0.00;
                        }
                        break;
                    }
                    case "2" : {        // 기프트 카드일 때는 tax 가 안붙는다.
                        taxToUse = 0.0;
                        break;
                    }
                }
                GlobalMemberValues.logWrite("getItemTaxFreeYN", "taxToUse : " + taxToUse + "\n");
            }

            GlobalMemberValues.logWrite("getItemTaxFreeYN3", "insSPrice : " + insSPrice + "\n");
            GlobalMemberValues.logWrite("getItemTaxFreeYN3", "taxToUse : " + taxToUse + "\n");

            double tempInsSvcTax = insSPrice * taxToUse * 0.01;

            GlobalMemberValues.logWrite("getItemTaxFreeYN2", "tempInsSvcTax : " + tempInsSvcTax + "\n");
            GlobalMemberValues.logWrite("getItemTaxFreeYN", "taxToUse : " + taxToUse + "\n");

            // 06122023
            // tempInsSvcTax > 0 ---> tempInsSvcTax != 0 으로 수정
            if (tempInsSvcTax != 0) {
                // Tax, 소수점 두자리까지
                insSTax = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getCommaStringForDouble(tempInsSvcTax + ""));
//                insSTaxAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((tempInsSvcTax * insSQty), "2"));
                insSTaxAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getCommaStringForDouble(tempInsSvcTax + "")) * insSQty;

                // TaxExempt 인 경우 세금합계는 무조건 0 으로
                if (insTaxExempt == "Y" || insTaxExempt.equals("Y")) {
                    insSTaxAmount = 0.00;
                }
            }

            GlobalMemberValues.logWrite("getItemTaxFreeYN2", "tempInsSvcTax : " + tempInsSvcTax + "\n");


            // common gratuity
            if (insSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
                insSTaxAmount = 0.00;
                insSTax = 0.00;
            }

            // -------------------------------------------------------------------------------------

            GlobalMemberValues.logWrite("getItemTaxFreeYNJJJ", "insSQty : " + insSQty + "\n");
            GlobalMemberValues.logWrite("getItemTaxFreeYNJJJ", "insSTaxAmount : " + insSTaxAmount + "\n");

            GlobalMemberValues.logWrite("getItemTaxFreeYN", "taxAmount : " + insSTaxAmount + "\n");
            GlobalMemberValues.logWrite("TAXAMOUNTAFTERRECALL", "taxAmount : " + insSTaxAmount + "\n");


            GlobalMemberValues.logWrite("taxchecklogjjj", "taxAmount1 : " + insSTaxAmount + "\n");

            GlobalMemberValues.logWrite("quickjjjlog", "mQuickSaleYN : " + mQuickSaleYN + "\n");
            GlobalMemberValues.logWrite("quickjjjlog", "insSvcidx : " + insSvcidx + "\n");
            GlobalMemberValues.logWrite("quickjjjlog", "insQuickSaleYN : " + insQuickSaleYN + "\n");

            // 092022
            // cash discount 또는 card extra 일 경우에는 tax 를 0 으로 처리한다.
            if (insSvcidx.equals("0") && !MainMiddleService.mQuickSaleYN.equals("Y") && !insQuickSaleYN.equals("Y")) {
                insSTax = 0.0;
                insSTaxAmount = 0.0;
            }

            GlobalMemberValues.logWrite("taxchecklogjjj", "taxAmount2 : " + insSTaxAmount + "\n");

            MainMiddleService.mQuickSaleYN = "N";

            // 04172023
            if (insSvcName.equals(GlobalMemberValues.getAddPayName())) {
                // 06122023
                String[] getAddPayValue = GlobalMemberValues.getAddPayData();
                if (getAddPayValue != null && getAddPayValue.length == 4) {
                    String getAddpaytaxtype = getAddPayValue[3];

                    GlobalMemberValues.logWrite("getAddPayTaxJJJLog", "getAddpaytaxtype : " + getAddpaytaxtype + "\n");

                    // 09282023
                    if (getAddpaytaxtype.equals("AT")) {
                        insSTax = 0.0;
                        insSTaxAmount = 0.0;
                    } else {

                        if (GlobalMemberValues.getTaxExemptItemsCountInCart() > 0
                                && GlobalMemberValues.getAllItemsCountInCart() == GlobalMemberValues.getTaxExemptItemsCountInCart()) {
                            insSTax = 0.0;
                            insSTaxAmount = 0.0;
                        }


                        if (GlobalMemberValues.getGiftCardItemsCountInCart() > 0
                                && GlobalMemberValues.getAllItemsCountInCart() == GlobalMemberValues.getGiftCardItemsCountInCart()) {
                            insSTax = 0.0;
                            insSTaxAmount = 0.0;
                        }

                    }
                }
            }

            // 전체 합계 (sTotalAmount) 구하기 (sPriceAmount + sTaxAmount)
            double insSTotalAmount = insSpriceAmount + insSTaxAmount;

            // Commission 구하기 ----------------------------------------------------------------
            double insSCommission = 0.00;
            double insSCommissionAmount = 0.00;

            double insEmpCommission = 0.00;
            double insEmpCommissionAmount = 0.00;

            double insSEmpCommissionAmount = 0.00;

            // 서비스메뉴별 커미션 구하기
            if (!GlobalMemberValues.isStrEmpty(svcCommissionRatio)) {
                double tempCommissionRatio = GlobalMemberValues.getDoubleAtString(svcCommissionRatio);
                if (!GlobalMemberValues.isStrEmpty(svcCommissionRatioType)) {
                    if (svcCommissionRatioType == "%" || svcCommissionRatioType.equals("%")) {
                        insSCommission = (insSpriceAmount * tempCommissionRatio) * 0.01;
                        insSCommission = GlobalMemberValues.getDoubleAtString(
                                GlobalMemberValues.getStringFormatNumber(insSCommission, "2"));
                    } else {
                        insSCommission = tempCommissionRatio;
                    }
                }
            }
            if (insSCommission > 0) {
                insSCommissionAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((insSCommission * insSQty),"2"));
            }

            // 직원별 커미션 구하기
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                double tempEmpCommissionratio = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empCommissionratio);
                if (tempEmpCommissionratio > 0) {
                    insEmpCommission = (insSpriceAmount * tempEmpCommissionratio) * 0.01;
                    insEmpCommission = GlobalMemberValues.getDoubleAtString(
                            GlobalMemberValues.getStringFormatNumber(insEmpCommission, "2"));
                } else {
                    insEmpCommission = 0.00;
                    insEmpCommissionAmount = 0.00;
                }
            } else {
                insEmpCommission = 0.00;
                insEmpCommissionAmount = 0.00;
            }
            if (insEmpCommission > 0) {
                insEmpCommissionAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((insEmpCommission * insSQty),"2"));
            }

            switch (GlobalMemberValues.GLOBAL_COMMISSIONRATIOTYPE) {
                case 0 : {
                    insSEmpCommissionAmount = insEmpCommissionAmount;
                    break;
                }
                case 1 : {
                    insSEmpCommissionAmount = insSCommissionAmount + insEmpCommissionAmount;
                    break;
                }
            }
            // -------------------------------------------------------------------------------------

            // sPoint 구하기 ------------------------------------------------------------------------
            double insSPoint = 0.00;
            double insSPointAmount = 0.00;
            // 포인트 사용이 Y 일 경우에만..
            if (GlobalMemberValues.REWARDSALADUSEYN == "Y" || GlobalMemberValues.REWARDSALADUSEYN.equals("Y")) {
                if (!GlobalMemberValues.isStrEmpty(svcPointRatio)) {
//                    insSPoint = (insSpriceAmount * GlobalMemberValues.getDoubleAtString(svcPointRatio)) * 0.01;
                    insSPoint = (insSPrice * GlobalMemberValues.getDoubleAtString(svcPointRatio)) * 0.01;
                    insSPoint = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(insSPoint, "2"));
                }
                if (insSPoint > 0) {
                    insSPointAmount = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber((insSPoint * insSQty), "2"));
                }
            }
            // -------------------------------------------------------------------------------------

            String mTableIdx = "";

            String returnCode = "";
            if (GlobalMemberValues.isStrEmpty(insMaxIdx)) {
                String tableIdx = "";
                if (TableSaleMain.mTableIdxArrList != null) {
                    tableIdx = TableSaleMain.mTableIdxArrList.get(0).toString();
                    if (GlobalMemberValues.isStrEmpty(tableIdx)) {
                        tableIdx = "";
                    }
                } else {
                    tableIdx = "";
                }

                GlobalMemberValues.mSelectedTableIdx = tableIdx;
                mTableIdx = tableIdx;


                // 같은 메뉴, 모디파이어로 저장된 메뉴가 있을 경우 -------------------------------------------------------
                boolean isEditQty = false;
                String tempMaxIdx = "";
                if (GlobalMemberValues.getQtyaddupYN()){
                    int i_mGeneralArrayListSize = mGeneralArrayList.size();
                    for (int z = 0; i_mGeneralArrayListSize > z ; z++) {
                        TemporarySaleCart temporarySaleCart = mGeneralArrayList.get(z);

                        // 11032022
                        // discount 또는 extra 가 적용됐는지 여부 확인
                        double itemdcextraprice = GlobalMemberValues.getDoubleAtString(MssqlDatabase.getResultSetValueToString(
                                " select selectedDcExtraPrice from temp_salecart where idx = '" + temporarySaleCart.tempSaleCartIdx + "' "
                        ));

                        // 11132022
                        // 키친프린팅이 된 메뉴인지 확인한다
                        // 키친프린팅이 된 메뉴는 수량증가 아닌 새로운 메뉴로 담는다
                        String itemkitchenprintingyn = "N";
                        itemkitchenprintingyn = MssqlDatabase.getResultSetValueToString(
                                " select kitchenprintedyn from temp_salecart where idx = '" + temporarySaleCart.tempSaleCartIdx + "' "
                        );
                        if (GlobalMemberValues.isStrEmpty(itemkitchenprintingyn)) {
                            itemkitchenprintingyn = "N";
                        }


                        if(temporarySaleCart.mSvcName.indexOf("=====") > -1) {      // 구분선 (divider) 일 경우
                        } else {                                                            // 구분선 (divider) 아닐 경우
                            if (temporarySaleCart.mSvcCategoryName.equals("QUICK SALE")) {
                                if (!GlobalMemberValues.hasDividerLineAfter(insHoldCode, temporarySaleCart.tempSaleCartIdx) && temporarySaleCart.mSvcName.equals(insSvcName)
                                        && temporarySaleCart.optionTxt.equals(mOptionTxt)
                                        && temporarySaleCart.additionalTxt1.equals(mAdditionalTxt1)
                                        && temporarySaleCart.additionalTxt2.equals(mAdditionalTxt2)
                                        && GlobalMemberValues.getStringFormatNumber(temporarySaleCart.mSPrice, "2").equals(svcServicePrice)
                                        && itemdcextraprice == 0
                                        && itemkitchenprintingyn.equals("N")) {
                                    String addOne = String.valueOf(GlobalMemberValues.getIntAtString(temporarySaleCart.mSQty) + insSQty);

                                    GlobalMemberValues.logWrite("jjjqtyedit", "여기진입 - " + z + "\n");
                                    GlobalMemberValues.logWrite("jjjqtyedit", "addOne - " + addOne + "\n");

                                    // 리스트 뷰의 같은 항목에 갯수 추가
                                    qty_set(addOne,temporarySaleCart,z);

                                    isEditQty = true;
                                    tempMaxIdx = temporarySaleCart.tempSaleCartIdx;

                                    break;
                                }
                            } else {
                                if (!GlobalMemberValues.hasDividerLineAfter(insHoldCode, temporarySaleCart.tempSaleCartIdx) && temporarySaleCart.mSvcName.equals(insSvcName)
                                        && temporarySaleCart.optionTxt.equals(mOptionTxt)
                                        && temporarySaleCart.additionalTxt1.equals(mAdditionalTxt1)
                                        && temporarySaleCart.additionalTxt2.equals(mAdditionalTxt2)
                                        && GlobalMemberValues.getStringFormatNumber(temporarySaleCart.mSPrice, "2").equals(GlobalMemberValues.getStringFormatNumber(insSPrice, "2"))
                                        && temporarySaleCart.mSvcidx.equals(insSvcidx)
                                        && temporarySaleCart.mGiftCardNumber.equals(insGiftCardNumber)
                                        && temporarySaleCart.kitchenMemo.equals("")
                                        && itemdcextraprice == 0
                                        && itemkitchenprintingyn.equals("N")) {
                                    String addOne = String.valueOf(GlobalMemberValues.getIntAtString(temporarySaleCart.mSQty) + insSQty);

                                    GlobalMemberValues.logWrite("jjjqtyedit", "여기진입 - " + z + "\n");
                                    GlobalMemberValues.logWrite("jjjqtyedit", "addOne - " + addOne + "\n");

                                    // 리스트 뷰의 같은 항목에 갯수 추가
                                    qty_set(addOne,temporarySaleCart,z);

                                    isEditQty = true;
                                    tempMaxIdx = temporarySaleCart.tempSaleCartIdx;

                                    break;
                                }
                            }
                        }
                    }
                }

                // -------------------------------------------------------------------------------------------------

                if (isEditQty) {
                    return tempMaxIdx;
                }

                double sPriceBalAmount_org = 0.0;
                double sTaxAmount_org = 0.0;
                double sTotalAmount_org = 0.0;
                double sCommissionAmount_org = 0.0;
                double sPointAmount_org = 0.0;

                if (insSpriceAmount != 0 && insSQty != 0) {
                    sPriceBalAmount_org = insSpriceAmount / insSQty;
                }
                if (insSTaxAmount != 0 && insSQty != 0) {
                    sTaxAmount_org = insSTaxAmount / insSQty;
                }
                if (insSTotalAmount != 0 && insSQty != 0) {
                    sTotalAmount_org = insSTotalAmount / insSQty;
                }
                if (insSEmpCommissionAmount != 0 && insSQty != 0) {
                    sCommissionAmount_org = insSEmpCommissionAmount / insSQty;
                }
                if (insSPointAmount != 0 && insSQty != 0) {
                    sPointAmount_org = insSPointAmount / insSQty;
                }

                // 05152023
                // mergednum 구하기
                String tempMergednum = MssqlDatabase.getResultSetValueToString(
                        " select mergednum from temp_salecart where holdcode = '" + TableSaleMain.getHoldCodeByTableidx(tableIdx, TableSaleMain.mSubTableNum) + "' "
                );
                if (GlobalMemberValues.isStrEmpty(tempMergednum)) {
                    tempMergednum = "0";
                }

                // 02032024
                // torder 연동을 위한 코드
                // 포스에서 받은 주문일 경우에는 P 로 시작하는 코드를 만들어 넣는다.
                String tordercodeforposorder = GlobalMemberValues.makeTOrderCodeForPOSOrder();

                String strInsSqlQuery = "insert into temp_salecart ( " +
                        " holdcode, sidx, stcode, midx, svcIdx,  " +
                        " svcName, svcFileName, svcFilePath, svcPositionNo, svcOrgPrice," +
                        " svcSetMenuYN, sPrice, sTax, sQty, sPriceAmount," +
                        " sTaxAmount, sTotalAmount, sCommission, sPoint, sCommissionAmount, " +
                        " sPointAmount, sSaleYN, customerId, customerName, customerPhoneNo, " +
                        " saveType, empIdx, empName, quickSaleYN, svcCategoryName, " +
                        " giftcardNumber, giftcardSavePrice, sCommissionRatioType, sCommissionRatio, sPointRatio,  " +
                        " sPriceBalAmount, svcCategoryColor, reservationCode, optionTxt, optionprice, " +
                        " additionalTxt1, additionalprice1, additionalTxt2, additionalprice2, modifieridx, " +
                        " modifiercode, memoToKitchen, " +

                        " sPriceBalAmount_org, sTaxAmount_org, sTotalAmount_org, sCommissionAmount_org, sPointAmount_org, " +
                        " tableidx, subtablenum, togotype, " +

                        // 05152023
                        " mergednum, " +

                        // 02032024
                        " tordercode " +

                        " ) values ( " +

                        " '" + insHoldCode + "', " +
                        " '" + insSidx + "', " +
                        " '" + insStcode + "', " +
                        " '" + insMidx + "', " +
                        " '" + insSvcidx + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(insSvcName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insSvcFileName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insSvcFilePath, 0) + "', " +
                        " '" + insSvcPositionNo + "', " +
                        " '" + insSvcOrgPrice + "', " +

                        " '" + insSvcSetMenuYN + "', " +
                        " '" + insSPrice + "', " +
                        " '" + insSTax + "', " +
                        " '" + insSQty + "', " +
                        " '" + insSpriceAmount + "', " +

                        " '" + insSTaxAmount + "', " +
                        " '" + insSTotalAmount + "', " +
                        " '" + insSCommission + "', " +
                        " '" + insSPoint + "', " +
                        " '" + insSEmpCommissionAmount + "', " +

                        " '" + insSPointAmount + "', " +
                        " '" + insSSaleYN + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerId, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insCustomerPhoneNo, 0) + "', " +

                        " '" + insSaveType + "', " +
                        " '" + insEmpIdx + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insEmpName, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insQuickSaleYN, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insCategoryName, 0) + "', " +

                        " '" + GlobalMemberValues.getDBTextAfterChecked(insGiftCardNumber, 0) + "', " +
                        " '" + GlobalMemberValues.getDBTextAfterChecked(insGiftCardSavePrice, 0) + "', " +
                        " '" + svcCommissionRatioType + "', " +
                        " '" + svcCommissionRatio + "', " +
                        " '" + svcPointRatio + "', " +

                        " '" + insSpriceAmount + "', " +
                        " '" + insCategoryColor + "', " +
                        " '" + insRcode + "', " +
                        " '" + mOptionTxt + "', " +
                        " '" + mOptionPrice + "', " +

                        " '" + mAdditionalTxt1 + "', " +
                        " '" + mAdditionalprice1 + "', " +
                        " '" + mAdditionalTxt2 + "', " +
                        " '" + mAdditionalprice2 + "', " +
                        " '" + mModifierIdx + "', " +

                        " '" + mModifierCode + "', " +
                        " '" + mMemoToKitchen + "', " +

                        " '" + sPriceBalAmount_org + "', " +
                        " '" + sTaxAmount_org + "', " +
                        " '" + sTotalAmount_org + "', " +
                        " '" + sCommissionAmount_org + "', " +
                        " '" + sPointAmount_org + "', " +

                        " '" + tableIdx + "', " +

                        " '" + TableSaleMain.mSubTableNum + "', " +

                        " '" + GlobalMemberValues.mToGoType + "', " +

                        // 05152023
                        " '" + tempMergednum + "', " +

                        // 02032024
                        " '" + GlobalMemberValues.getDBTextAfterChecked(tordercodeforposorder, 0) + "' " +

                        " ) ";
                GlobalMemberValues.logWrite("commongratuitychecklogjjj", "insSTax : " + insSTax + "\n");

                GlobalMemberValues.logWrite("TEMPSALECARTINSERT", "INS QUERY : " + strInsSqlQuery + "\n");

                returnCode = MssqlDatabase.executeTransactionByQuery(strInsSqlQuery);
            } else {
                GlobalMemberValues.logWrite("TEMPSALECARTINSERT", "에러......." + "\n");

                returnCode = "0";
            }



            // 09262023
            if (TableSaleMain.isAfterMerge) {
                GlobalMemberValues.logWrite("gratuitylogjjj55555", "여기.2 : " + "\n");
                returnCode = "";
                TableSaleMain.isAfterMerge = false;
            }


            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
//                // 0424 -------------------------------------------------------------------------------------------------------
//                // 정상적인 등록이 된 경우
//                // 같은 holdcode 로 등록된 기존 데이터의 billprintedyn 의 값을 N 으로 수정한다.
//                String upQueryStr = " update temp_salecart set billprintedyn = 'N' where holdcode = '" + insHoldCode + "' ";
//                returnCode = MssqlDatabase.executeTransactionByQuery(upQueryStr);
//                // 0424 -------------------------------------------------------------------------------------------------------


                GlobalMemberValues.logWrite("uploadcartdatalog", "mTableIdx : " + mTableIdx + "\n");

                // 장바구니데이터 업로드
                GlobalMemberValues gm = new GlobalMemberValues();
                GlobalMemberValues.logWrite("uploadcartdatalog", "isPOSWebPay : " + gm.isPOSWebPay() + "\n");
                if (gm.isPOSWebPay() && !GlobalMemberValues.isStrEmpty(mTableIdx)
                        && (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                    GlobalMemberValues.logWrite("uploadcartdatalog", "장바구니데이터 업로드 진입" + "\n");
                    GlobalMemberValues.setSendCartToCloud(MainActivity.mContext, MainActivity.mActivity);
                }

                GlobalMemberValues.logWrite("TEMPSALECARTINSERT", "정상처리..111" + "\n");
                if (GlobalMemberValues.isStrEmpty(insMaxIdx)) {
                    maxIdx = MssqlDatabase.getResultSetValueToString("select max(idx) from temp_salecart");
//                    maxIdx = dbInit.dbExecuteReadReturnString("select max(idx) from temp_salecart");
                } else {
                    maxIdx = insMaxIdx;
                }
                //GlobalMemberValues.logWrite("testQuery", "returncode : " + returnCode + "\n");
                //GlobalMemberValues.logWrite("testQuery", "query : select max(idx) from temp_salecart\n");

                GlobalMemberValues.logWrite("jjjtaxamjjjlog", "insSTaxAmount : " + insSTaxAmount + "\n");


                //TemporarySaleCart 객체 생성시 전달할 값을 String 배열로 만든다.
                String paramsTempSaleCartArr[] = {
                        insHoldCode, insSidx, insStcode, insMidx, insSvcidx,
                        insSvcName, insSvcFileName, insSvcFilePath,

                        insSvcPositionNo, String.valueOf(insSvcOrgPrice), insSvcSetMenuYN,
                        String.valueOf(insSPrice), String.valueOf(insSTax), String.valueOf(insSQty), String.valueOf(insSpriceAmount),
                        String.valueOf(insSTaxAmount), String.valueOf(insSTotalAmount),

                        String.valueOf(insSCommission), String.valueOf(insSPoint), String.valueOf(insSEmpCommissionAmount), String.valueOf(insSPointAmount), insSSaleYN,

                        insCustomerId, insCustomerName, insCustomerPhoneNo, insSaveType,

                        insEmpIdx, insEmpName,

                        maxIdx,

                        insQuickSaleYN,

                        insCategoryName,

                        insGiftCardNumber, insGiftCardSavePrice,

                        "","","","","",

                        svcCommissionRatioType, svcCommissionRatio, svcPointRatio, String.valueOf(insSpriceAmount)
                };
                mTempSaleCart = new TemporarySaleCart(paramsTempSaleCartArr);
                mTempSaleCart.mTaxExempt = insTaxExempt;
                mTempSaleCart.mCategoryColor = insCategoryColor;
                mTempSaleCart.mRcode = insRcode;
                mTempSaleCart.staticMSPrice = GlobalMemberValues.getStringFormatNumber(mTempSaleCart.mSPrice, "2");

                // 옵션&추가사항 관련값
                mTempSaleCart.modifiercode = mModifierCode;
                mTempSaleCart.modifieridx = mModifierIdx;
                mTempSaleCart.optionTxt = mOptionTxt;
                mTempSaleCart.optionprice = GlobalMemberValues.getStringFormatNumber(mOptionPrice, "2");
                mTempSaleCart.additionalTxt1 = mAdditionalTxt1;
                mTempSaleCart.additionalprice1 = GlobalMemberValues.getStringFormatNumber(mAdditionalprice1, "2");
                mTempSaleCart.additionalTxt2 = mAdditionalTxt2;
                mTempSaleCart.additionalprice2 = GlobalMemberValues.getStringFormatNumber(mAdditionalprice2, "2");

                mTempSaleCart.kitchenMemo = mMemoToKitchen;

                mTempSaleCart.mQuickSaleKitchenPrintingYN = MainMiddleService.mQuickSaleKitchenPrintingYN;

                // togo deli type
                switch (insTogoDeliType){
                    case "H":
                        mTempSaleCart.mServiceType = "HERE";
                        break;
                    case "T":
                        mTempSaleCart.mServiceType = "TO GO";
                        break;
                    case "D":
                        mTempSaleCart.mServiceType = "DELIVERY";
                        break;
                }




                String[] strValueForTextView = null;
                if (listViewCount == 0) {
                    mGeneralArrayList.add(mTempSaleCart);

                    // Lite 버전 관련
                    if (GlobalMemberValues.isLiteVersion()) {
                        mSaleCartAdapter = new SaleCartAdapter(context, R.layout.main_salecart_list_lite, mGeneralArrayList);

                        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                            mPresentationCartAdapter = new PresentationCartAdapter(context, R.layout.presentation_list_item, mGeneralArrayList);
                        }
                    } else if (GlobalMemberValues.is_customerMain) {
                        mSaleCartAdapter = new SaleCartAdapter(context, R.layout.main_customer_salecart_list, mGeneralArrayList);

                        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                            mPresentationCartAdapter = new PresentationCartAdapter(context, R.layout.presentation_list_item, mGeneralArrayList);
                        }
                    } else {
                        mSaleCartAdapter = new SaleCartAdapter(context, R.layout.main_salecart_list, mGeneralArrayList);

                        mSaleCartAdapter.registerDataSetObserver(new DataSetObserver() {
                            @Override
                            public void onChanged() {
                                super.onChanged();
                                MainActivity.setMainBillPrintButtonVisible(false);
                            }
                        });

                        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                            mPresentationCartAdapter = new PresentationCartAdapter(context, R.layout.presentation_list_item, mGeneralArrayList);
                        }
                    }



                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(mSaleCartAdapter);
                    //jihun park presentation listview
                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        if (mPresentationCartAdapter != null && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.setAdapter(mPresentationCartAdapter);
                        }
                    }

                    strValueForTextView = getCalcSubTotalTaxTotalValue(mGeneralArrayList);
                } else {
                    mGeneralArrayList.add(mTempSaleCart);           // 리스트뷰에 항목추가
                    mSaleCartAdapter.notifyDataSetChanged();        // 추가된 항목을 Adapter 에 알림

                    selectedPosition = -1;

                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        mPresentationCartAdapter.notifyDataSetChanged();
                    }
                    // 추가되는 항목으로 스크롤을 이동시킴
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.smoothScrollToPosition(mSaleCartAdapter.getCount());
                    //jihun park presentation listview
                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        if (mPresentationCartAdapter != null && GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.smoothScrollToPosition(mPresentationCartAdapter.getCount());
                        }
                    }

                    strValueForTextView = getCalcSubTotalTaxTotalValue(mGeneralArrayList);
                }

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    // 멀티선택관련 추가
                    // 선택여부 배열크기 생성
                    selectedPosition = -1;
                    isCheckedConfrim = new boolean[mGeneralArrayList.size()];
                }

                // total order qty
                if (!mGeneralArrayList.isEmpty()){
                    int totalOrderQTY = 0;
                    for (int z = 0; mGeneralArrayList.size() > z ; z++){
                        if (mGeneralArrayList.get(z).mSvcName.equals(GlobalMemberValues.mCommonGratuityName) || mGeneralArrayList.get(z).mSvcName.contains("========")){

                        } else {
                            try{
                                totalOrderQTY += Integer.parseInt(mGeneralArrayList.get(z).mSQty);
                            }catch (NumberFormatException e){
                                totalOrderQTY += 0;
                            }
//                            totalOrderQTY += Integer.parseInt(mGeneralArrayList.get(z).mSQty);
                        }

                    }
                    // main top total order count update
                    GlobalMemberValues.setMainTotalOrderCountTextview();
                }

                // getCalcSubTotalTaxTotalValue 메소드로 구해온 값들을 Sub Total, Tax, Total 텍스트뷰에 넣는다.
                setCalcSubTotalTaxTotalValue(strValueForTextView);

                // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너 (선택항목의 position 값을 멤버변수 selectedPosition 에 할당한다.
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setOnItemClickListener(mSaleCartItemClickListener);
                // 리스트 항목을 길게 클릭할 때 발생하는 이벤트 리스너 (선택항목을 삭제)
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setOnItemLongClickListener(mSaleCartItemLongClickListner);

                /** SaleCart 리스트 아래의 CANCEL, DELETE, QTY, SALE 버튼의 클릭이벤트리스너 정의 *************************************/
                // DELETE 버튼 클릭시 (선택 리스트 항목 삭제)
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONDELETE.setOnClickListener(mainSaleCartlButtonClick);
                // QTY 버튼 클릭시 (팝업창으로 수량입력받는 레이어띄움)
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY.setOnClickListener(mainSaleCartlButtonClick);
                // 073120
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_PLUS.setOnClickListener(mainSaleCartlButtonClick);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONQTY_MINUS.setOnClickListener(mainSaleCartlButtonClick);

                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_TOGO.setOnClickListener(mainSaleCartlButtonClick);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_DELIVERY.setOnClickListener(mainSaleCartlButtonClick);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_GENERAL_MODIFIER.setOnClickListener(mainSaleCartlButtonClick);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTON_BILLPRINT.setOnClickListener(mainSaleCartlButtonClick);

                /**********************************************************************************************************************/

                // option / additional 값 초기화
                mModifierCode = "";
                mModifierIdx = "";
                mOptionTxt = "";
                mOptionPrice = "0";
                mAdditionalTxt1 = "";
                mAdditionalprice1 = "0";
                mAdditionalTxt2 = "";
                mAdditionalprice2 = "0";

                mMemoToKitchen = "";

                listViewCount++;

                // 카트에 아이템 추가 성공시 메인메뉴에 표시.


            } else {
                GlobalMemberValues.logWrite("TEMPSALECARTINSERT", "DB 처리 에러" + "\n");
            }
        }

        GlobalMemberValues.logWrite("NowListViewCount", "NowListViewCount : " + mListviewItemCount + "\n");

        if (mGeneralArrayList.size() > 0){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
        }

        return maxIdx;
    }
    /***********************************************************************************************************************/



    /**** Discount / Extra 후 리스트뷰에 추가시 사용하는 메소드 ***************************************************************/
    public static void addListViewItemForDiscountExtra(TemporarySaleCart paramTempSaleCart, int addPosition) {
        GlobalMemberValues.logWrite("multicheckedlogDc", "여기실행3" + "\n");

        if (addPosition == 9999) {
            mGeneralArrayList.add(paramTempSaleCart);                        // 리스트뷰에 항목추가
        } else {
            mGeneralArrayList.add(addPosition, paramTempSaleCart);           // 지정된 포지션으로 리스트뷰에 항목추가
        }

        if (GlobalMemberValues.isMultiCheckOnCart()) {
            // 멀티선택관련 추가
            // 선택여부 배열크기 생성
            isCheckedConfrim = new boolean[mGeneralArrayList.size()];
        }

        mSaleCartAdapter.notifyDataSetChanged();        // 추가된 항목을 Adapter 에 알림
        // 추가되는 항목으로 스크롤을 이동시킴
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.smoothScrollToPosition(mSaleCartAdapter.getCount());

        // jihun
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            mPresentationCartAdapter.notifyDataSetChanged();
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.smoothScrollToPosition(mPresentationCartAdapter.getCount());
        }
        //

        GlobalMemberValues.logWrite("multicheckedlogDc", "여기실행777" + "\n");

        String[] strValueForTextView = null;
        strValueForTextView = getCalcSubTotalTaxTotalValue(mGeneralArrayList);
        // getCalcSubTotalTaxTotalValue 메소드로 구해온 값들을 Sub Total, Tax, Total 텍스트뷰에 넣는다.
        setCalcSubTotalTaxTotalValue(strValueForTextView);

        GlobalMemberValues.logWrite("multicheckedlogDc", "여기실행4" + "\n");

        // 리스트 항목을 클릭할 때 발생하는 이벤트 리스너 (선택항목의 position 값을 멤버변수 selectedPosition 에 할당한다.
        //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setOnItemClickListener(mSaleCartItemClickListener);
        // 리스트 항목을 길게 클릭할 때 발생하는 이벤트 리스너 (선택항목을 삭제)
        //GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setOnItemLongClickListener(mSaleCartItemLongClickListner);
    }
    /***********************************************************************************************************************/



    // SaleCart 리스트뷰 아래의 버튼 클릭시 이벤트
    static AdapterView.OnClickListener mainSaleCartlButtonClick = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View v) {
            //GlobalMemberValues.logWrite("IDCHECKED", "Button ID : " + v.getId() + "\n");
            switch (v.getId()) {
                // CANCEL 버튼 클릭시 (리스트 항목 전체 삭제)
                case R.id.customer_main_cancel : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------
                    if (mGeneralArrayList == null || mGeneralArrayList.size() == 0) {
                        MainMiddleService.initList();
                        GlobalMemberValues.openRestaurantTable();
                        return;
                    }

                    new AlertDialog.Builder(context)
                            .setTitle("Item Delete")
                            .setMessage("Are you sure you want to cancel?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LogsSave.saveLogsInDB(88);
                                            GlobalMemberValues.mCancelBtnClickYN = "Y";
                                            setEmptyInSaleCart(false);
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
                case R.id.mainSaleCartButton_Cancel : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------


                    if (mGeneralArrayList == null || mGeneralArrayList.size() == 0) {
                        MainMiddleService.initList();
                        GlobalMemberValues.openRestaurantTable();
                        return;
                    }

                    new AlertDialog.Builder(context)
                            .setTitle("Delete All Items")
                            .setMessage("Are you sure you want to cancel?")
                            //.setIcon(R.drawable.ic_launcher)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                            if (GlobalMemberValues.isSavingItemDeleteReason()){
                                                Intent itemDeleteReasonIntent = new Intent(MainActivity.mContext, ItemDeleteReason.class);
                                                itemDeleteReasonIntent.putExtra("is_delete_clear", "clear");
                                                mActivity.startActivity(itemDeleteReasonIntent);

                                                if (GlobalMemberValues.isUseFadeInOut()) {
                                                    mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                                                }
                                            } else {
                                                item_delete_action_all();
                                            }

                                            //04182024 when menu is cancelled send POST request to TORDER
                                            if(GlobalMemberValues.isTOrderUse()){
                                                GlobalMemberValues.sendTOrderAPITableClear(GlobalMemberValues.mSelectedTableIdx);
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
                // DELETE 버튼 클릭시 (선택 리스트 항목 삭제)
                case R.id.mainSaleCartButton_Delete : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------

                    // All Discount / Extra 가 실행된지 체크후 ----------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                        return;
                    }

                    if (GlobalMemberValues.isSavingItemDeleteReason()){
                        Intent itemDeleteReasonIntent = new Intent(MainActivity.mContext, ItemDeleteReason.class);
                        itemDeleteReasonIntent.putExtra("is_delete_clear", "delete");
                        mActivity.startActivity(itemDeleteReasonIntent);

                        if (GlobalMemberValues.isUseFadeInOut()) {
                            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                        }

                        return;
                    } else {
                        item_delete_action();
                    }

                    // item_delete_action(); 으로 이동


                    break;
                }
                // QTY 버튼 클릭시 (수량입력받는 팝업창 띄움)
                case R.id.mainSaleCartButton_Qty : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------

                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    if (mGeneralArrayList.size() == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose item(s)", "Close");
                        return;
                    }

                    // 멀티선택관련 추가
                    int selectedPositionForQtyChange = -1;
                    if (GlobalMemberValues.isMultiCheckOnCart()) {
                        int tempSize = mGeneralArrayList.size();
                        if (tempSize == 0) {
                            return;
                        }
                        int checkedItemCount = 0;
                        for(int i = 0; i < tempSize; i++){
                            if(isCheckedConfrim[i]){
                                checkedItemCount++;
                                selectedPositionForQtyChange = i;
                            }
                        }

                        if (checkedItemCount == 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                            return;
                        }

                        if (checkedItemCount > 1) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You only need to select one item to change the quantity", "Close");
                            initCheckItem();
                            return;
                        }
                    } else {
                        selectedPositionForQtyChange = selectedPosition;
                    }

                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                        return;
                    }

                    /**
                     for (String tempValue : discountExtraPositionVector) {
                     GlobalMemberValues.logWrite("dcextravectorlog", "tempValue : " + tempValue + "\n");
                     }

                     if (discountExtraPositionVector != null) {

                     GlobalMemberValues.logWrite("dcextravectorlog", "selectedPositionForQtyChange : " + selectedPositionForQtyChange + "\n");

                     // 먼저 선택한 항목이 Discount / Extra 한 항목인지 체크한다.
                     //int tempLoc = discountExtraPositionVector.lastIndexOf("dcex_" + String.valueOf(selectedPositionForQtyChange));
                     //GlobalMemberValues.logWrite("MainMiddleService", "DiscountExtraPosition index of : " + tempLoc + "\n");
                     if (discountExtraPositionVector.contains("dcex_" + String.valueOf(selectedPositionForQtyChange))) {
                     GlobalMemberValues.displayDialog(context, "Edit Quantity", "You can't add/edit/delete the item\n" +
                     "because this sale includes 'Discount' or 'Extra'", "Close");
                     return;
                     }
                     }
                     **/

                    TemporarySaleCart tempSCIns = mGeneralArrayList.get(selectedPositionForQtyChange);

                    if (tempSCIns.mSaveType == "2" || tempSCIns.mSaveType.equals("2")) {
                        GlobalMemberValues.displayDialog(context, "Warning", "You can buy only one gift card.", "Close");
                        return;
                    }

                    String itemQty = tempSCIns.mSQty;
                    if (GlobalMemberValues.isStrEmpty(itemQty)) {
                        itemQty = "0";
                    }

                    String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                    if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                        GlobalMemberValues.displayDialog(context, "Edit Quantity", "You can't add/edit/delete the item\n" +
                                "because this sale includes 'Discount' or 'Extra'", "Close");
                        return;
                    }

                    //GlobalMemberValues.logWrite("selectedPositionForQtyChangelog", "mSaveType : " + tempSCIns.mSaveType + "\n");
                    //GlobalMemberValues.logWrite("selectedPositionForQtyChangelog", "selectedDcExtraType : " + tempSCIns.selectedDcExtraType + "\n");

                    Intent qtyEditIntent = new Intent(context.getApplicationContext(), QtyEditActivity.class);
                    //qtyEditIntent.setAction(Intent.ACTION_GET_CONTENT);
                    //qtyEditIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    GlobalMemberValues.logWrite("ReturnQtyEditValue", "넘겨줄 포지션값 : " + selectedPositionForQtyChange + "\n");

                    // Dialog 에 Extra 로 객체 및 데이터 전달하기 ------------------------------------------------
                    //qtyEditIntent.putExtra("ParentMainMiddleService", this.getClass());
                    qtyEditIntent.putExtra("ParentTemporarySaleCartInstance", tempSCIns);
                    qtyEditIntent.putExtra("ParentSaleCartPosition", String.valueOf(selectedPositionForQtyChange));
                    // -------------------------------------------------------------------------------------

                    //mActivity.startActivityForResult(qtyEditIntent, GlobalMemberValues.ACT_SUB_QTYEDITPOPUP);
                    insContext = context;       // Dialog 에서 임시로 사용할 context 에 MainActivity 의 context 를 할당한다.
                    mActivity.startActivity(qtyEditIntent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }


                    /**
                     LinearLayout layout = (LinearLayout)View.inflate(context,
                     R.layout.quantity_edit, null);
                     //final EditText editPopup = (EditText)layout.findViewById(R.id.editPopup);
                     //editPopup.setText(editActivity.getText().toString());

                     AlertDialog.Builder popupDialog = new AlertDialog.Builder(context);
                     popupDialog
                     .setView(layout)
                     .show();
                     **/

                    break;
                }
                case R.id.mainSaleCartButton_Qty_plus : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------

                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    if (mGeneralArrayList.size() == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose item(s)", "Close");
                        return;
                    }

                    if (selectedPosition == -1){
                        return;
                    }

                    // 선택한 아이템이 Common Gratuity 라면 실행 안되게 한다.
                    TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(selectedPosition);
                    if (tempSaleCartInstance.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
                        return;
                    }

                    // 멀티선택관련 추가
                    int selectedPositionForQtyChange = -1;
                    if (GlobalMemberValues.isMultiCheckOnCart()) {
                        int tempSize = mGeneralArrayList.size();
                        if (tempSize == 0) {
                            return;
                        }
                        int checkedItemCount = 0;
                        for(int i = 0; i < tempSize; i++){
                            if(isCheckedConfrim[i]){
                                checkedItemCount++;
                                selectedPositionForQtyChange = i;
                            }
                        }

                        if (checkedItemCount == 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                            return;
                        }

                        if (checkedItemCount > 1) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You only need to select one item to change the quantity", "Close");
                            initCheckItem();
                            return;
                        }
                    } else {
                        selectedPositionForQtyChange = selectedPosition;
                    }

                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                        return;
                    }



                    TemporarySaleCart tempSCIns = mGeneralArrayList.get(selectedPositionForQtyChange);

                    String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                    if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                        GlobalMemberValues.displayDialog(context, "Edit Quantity", "You can't add/edit/delete the item\n" +
                                "because this sale includes 'Discount' or 'Extra'", "Close");
                        return;
                    }


                    // common gratuity 관련
                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();


                    String addOne = String.valueOf (GlobalMemberValues.getIntAtString(tempSCIns.mSQty) + 1);
                    qty_set(addOne,tempSCIns,selectedPositionForQtyChange);


                    // common gratuity 관련
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                    // 한개의 아이템 qty 변경시 아이템 선택이 해제되는것을 막기 위해서 아래로 변경
//                    setCheckedItems(selectedPositionForQtyChange);
                    // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
                    mSaleCartAdapter.notifyDataSetChanged();
                    if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                        mPresentationCartAdapter.notifyDataSetChanged();
                    }
                    // 한개의 아이템 qty 변경시 아이템 선택이 해제되는것을 막기 위해서 아래로 변경

                    selectedPosition = selectedPositionForQtyChange;
                    LogsSave.saveLogsInDB(91);
                    break;
                }
                case R.id.mainSaleCartButton_Qty_min : {
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------

                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    if (mGeneralArrayList.size() == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose item(s)", "Close");
                        return;
                    }

                    if (selectedPosition == -1){
                        return;
                    }

                    // 선택한 아이템이 Common Gratuity 라면 실행 안되게 한다.
                    TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(selectedPosition);
                    if (tempSaleCartInstance.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
                        return;
                    }

                    // 멀티선택관련 추가
                    int selectedPositionForQtyChange = -1;
                    if (GlobalMemberValues.isMultiCheckOnCart()) {
                        int tempSize = mGeneralArrayList.size();
                        if (tempSize == 0) {
                            return;
                        }
                        int checkedItemCount = 0;
                        for(int i = 0; i < tempSize; i++){
                            if(isCheckedConfrim[i]){
                                checkedItemCount++;
                                selectedPositionForQtyChange = i;
                            }
                        }

                        if (checkedItemCount == 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                            return;
                        }

                        if (checkedItemCount > 1) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You only need to select one item to change the quantity", "Close");
                            initCheckItem();
                            return;
                        }
                    } else {
                        selectedPositionForQtyChange = selectedPosition;
                    }

                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                        return;
                    }
                    TemporarySaleCart tempSCIns = mGeneralArrayList.get(selectedPositionForQtyChange);

                    String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                    if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                        GlobalMemberValues.displayDialog(context, "Edit Quantity", "You can't add/edit/delete the item\n" +
                                "because this sale includes 'Discount' or 'Extra'", "Close");
                        return;
                    }


                    if ((GlobalMemberValues.getIntAtString(tempSCIns.mSQty) - 1) > 0){
                        String addOne = String.valueOf (GlobalMemberValues.getIntAtString(tempSCIns.mSQty) - 1);

                        // common gratuity 관련
                        GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();

                        qty_set(addOne,tempSCIns,selectedPositionForQtyChange);

                        // common gratuity 관련
                        GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                        // 한개의 아이템 qty 변경시 아이템 선택이 해제되는것을 막기 위해서 아래로 변경
//                    setCheckedItems(selectedPositionForQtyChange);
                        // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
                        mSaleCartAdapter.notifyDataSetChanged();
                        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                            mPresentationCartAdapter.notifyDataSetChanged();
                        }
                        // 한개의 아이템 qty 변경시 아이템 선택이 해제되는것을 막기 위해서 아래로 변경

                        selectedPosition = selectedPositionForQtyChange;

                    } else {
                    }

                    LogsSave.saveLogsInDB(92);

                    break;
                }
                case R.id.general_modifier:
                    // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                    if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                    // ------------------------------------------------------------

                    // All Discount / Extra 가 실행된지 체크후 -------------------------------
                    // 실행후라면 이후의 코드를 실행하지 않는다.
                    if (!checkAllDiscountExtra()) return;
                    // -----------------------------------------------------------------------

                    if (mGeneralArrayList.size() == 0) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose item(s)", "Close");
                        return;
                    }
                    LogsSave.saveLogsInDB(94);
                    // 멀티선택관련 추가
                    int selectedPositionForQtyChange = -1;
                    if (GlobalMemberValues.isMultiCheckOnCart()) {
                        int tempSize = mGeneralArrayList.size();
                        if (tempSize == 0) {
                            return;
                        }
                        int checkedItemCount = 0;
                        for(int i = 0; i < tempSize; i++){
                            if(isCheckedConfrim[i]){
                                checkedItemCount++;
                                selectedPositionForQtyChange = i;
                            }
                        }

                        if (checkedItemCount == 0) {
                            GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                            return;
                        }

                        if (checkedItemCount > 1) {
                            GlobalMemberValues.displayDialog(context, "Warning", "You only need to select one item to change the general modifier", "Close");
                            initCheckItem();
                            return;
                        }
                    } else {
                        selectedPositionForQtyChange = selectedPosition;
                    }

                    if (selectedPosition == -1) {
                        GlobalMemberValues.displayDialog(context, "Warning", "Choose a item", "Close");
                        return;
                    }
                    TemporarySaleCart tempSCIns = mGeneralArrayList.get(selectedPositionForQtyChange);

                    String tempSelectedDcExtraType = tempSCIns.selectedDcExtraType;
                    if (tempSelectedDcExtraType.toUpperCase().equals("DC") || tempSelectedDcExtraType.toUpperCase().equals("EX")) {
                        GlobalMemberValues.displayDialog(context, "General Modifier", "You can't add/edit/delete the item\n" +
                                "because this sale includes 'Discount' or 'Extra'", "Close");
                        return;
                    }


                    // general modifier
                    TemporarySaleCart tempCif = mGeneralArrayList.get(selectedPositionForQtyChange);
                    String tempSvcIdx = GlobalMemberValues.getConvertText(tempCif.mSvcidx);

                    GlobalMemberValues.logWrite("positionlogjjj", "selectedPositionForQtyChange : " + selectedPositionForQtyChange + "\n");

                    Intent modgm = new Intent(MainActivity.mContext, MainMiddleServiceGeneralModifer.class);
                    modgm.putExtra("ServiceIdx", tempSvcIdx);
                    modgm.putExtra("modifiercode", tempSCIns.modifiercode);
                    modgm.putExtra("position", (selectedPositionForQtyChange + ""));
                    mActivity.startActivity(modgm);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
                    }

                    break;
                case R.id.mainSaleCartButton_togo:
                    for(int i = 0 ; i < isCheckedConfrim.length;i++){
                        if (isCheckedConfrim[i]){

                            if (mGeneralArrayList.get(i).mServiceType == "TO GO"){
                                mGeneralArrayList.get(i).mServiceType = "HERE";
                            } else {
                                mGeneralArrayList.get(i).mServiceType = "TO GO";
                            }

                            String strQuery = " update temp_salecart set togodelitype = 'T' " +
                                    " where holdcode = '"+mGeneralArrayList.get(i).mHoldCode+"' " +"and idx = " + "'"+ mGeneralArrayList.get(i).tempSaleCartIdx +"'";
                            MssqlDatabase.executeTransactionByQuery(strQuery);
                        }
                    }

                    initCheckItem();
                    MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                    break;
                case R.id.mainSaleCartButton_delivery:
                    for(int i = 0 ; i < isCheckedConfrim.length;i++){
                        if (isCheckedConfrim[i]){
                            mGeneralArrayList.get(i).mServiceType = "DELIVERY";
                            String strQuery = " update temp_salecart set togodelitype = 'D' " +
                                    " where holdcode = '"+mGeneralArrayList.get(i).mHoldCode+"' " +"and idx = " + "'"+ mGeneralArrayList.get(i).tempSaleCartIdx +"'";
                            MssqlDatabase.executeTransactionByQuery(strQuery);
                        }
                    }
                    initCheckItem();
                    MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                    break;

                case R.id.mainSaleCartButton_billprint : {
                    //03112024 check if item has been added before bill print
                    //if item has been added, prompt user to print to kitchen first.
                    if (MainActivity.temp_str_salecart_cnt == MainMiddleService.mGeneralArrayList.size()) {
                        LogsSave.saveLogsInDB(90);
                        TableSaleMain.openBillPrint("Y");
                        if (GlobalMemberValues.isBillPrintPopupOpen()){

                        } else {
                            MainMiddleService.initList();
                        }
                    } else {
                        GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning",
                                "There is an added menu\nPlease print the kitchen or delete the added menu", "Close");
                    }

                    break;
                }
            }
        }
    };

    public static void setEmptyInSaleCart(boolean paramFromMain) {
        if (!paramFromMain) {
            // POS 타입이 레스토랑일 경우 테이블 메인을 띄운다.
            GlobalMemberValues globalMemberValues = new GlobalMemberValues();
            if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
                if (!GlobalMemberValues.now_saletypeisrestaurant) {
//                    AppTopBar.btn_table_sale.setVisibility(View.VISIBLE);
                } else {
//                    AppTopBar.btn_table_sale.setVisibility(View.GONE);
                }

                if (TableSaleMain.mSelectedTablesArrList != null) {
                    GlobalMemberValues.logWrite("jjtablelog",
                            "선택한 tableidx : " + TableSaleMain.mSelectedTablesArrList.toString() + "\n");
                    GlobalMemberValues.logWrite("jjtablelog",
                            "선택한 arr size : " + TableSaleMain.mSelectedTablesArrList.size() + "\n");
                }

                if (TableSaleMain.mSelectedTablesArrList.size() > 0) {
                    GlobalMemberValues.mArrListForTSM = TableSaleMain.mSelectedTablesArrList;
                    Intent intent = new Intent(MainActivity.mContext, TableSaleMain.class);
                    intent.putExtra("actiontype", "cleartable");
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                } else {
                    clearListExe();
                    Intent intent = new Intent(MainActivity.mContext, TableSaleMain.class);
                    mActivity.startActivity(intent);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                }
            } else {
                clearListExe();
            }
        } else {
            clearListExe();
        }
    }

    public static void clearListExe() {
        clearListView();
        GlobalMemberValues.setCustomerInfoInit();
        //GlobalMemberValues.setEmployeeInfoInit();

        GlobalMemberValues.ITEMCANCELAPPLY = 0;
        MainMiddleService.mHoldCode = "NOHOLDCODE";
    }

    public static void clearListView() {
        String returnDbResult = "0";
        Vector<String> strInsertQueryVec = new Vector<String>();
        String strQuery_ = "";

        GlobalMemberValues.logWrite("alldeljjjlog", "holdcode : " + mHoldCode + "\n");

        // DB 처리를 먼저 실행한다.
        String tempTableIdx = MssqlDatabase.getResultSetValueToString(
                " select tableidx from temp_salecart where holdcode = '" + mHoldCode + "' "
        );
        if (GlobalMemberValues.isStrEmpty(tempTableIdx)) {
            tempTableIdx = "";
        }

        // NOHOLDCODE 로 저장되어 있는 것들부터 모두 삭제
        strQuery_ = "delete from temp_salecart where holdcode = 'NOHOLDCODE' " ;
        strInsertQueryVec.addElement(strQuery_);
        strQuery_ = "delete from temp_salecart_deliveryinfo where holdcode = 'NOHOLDCODE' " ;
        strInsertQueryVec.addElement(strQuery_);
        strQuery_ = "delete from temp_salecart_optionadd where holdcode = 'NOHOLDCODE' " ;
        strInsertQueryVec.addElement(strQuery_);
        strQuery_ = "delete from temp_salecart_optionadd_imsi where holdcode = 'NOHOLDCODE' " ;
        strInsertQueryVec.addElement(strQuery_);
        strQuery_ = "delete from salon_store_restaurant_table_peoplecnt where holdcode = 'NOHOLDCODE' " ;
        strInsertQueryVec.addElement(strQuery_);

        // 다음으로 특정 Holdcode 로 저장되어 있는 것들 삭제
        strQuery_ = "delete from temp_salecart where holdcode = '" + mHoldCode + "' " ;
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        strQuery_ = "delete from temp_salecart_deliveryinfo where holdcode = '" + mHoldCode + "' " ;
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        strQuery_ = "delete from temp_salecart_optionadd where holdcode = '" + mHoldCode + "' " ;
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        strQuery_ = "delete from temp_salecart_optionadd_imsi where holdcode = '" + mHoldCode + "' " ;
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        // split last 결제시 금액 나오지 않는 현상때문에 주석처리
//        strQuery_ = "delete from bill_list where holdcode = '" + mHoldCode + "' " ;
//        if (GlobalMemberValues.isLastBillList()) {
//            strInsertQueryVec.addElement(strQuery_);
//        }

        strQuery_ = "delete from salon_store_restaurant_table_peoplecnt where holdcode = '" + mHoldCode + "' " ;
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        strQuery_ = "insert into temp_salecart_del ( " +
                " holdcode, sidx, stcode, alldelyn, tableidx " +
                " ) values ( " +
                " '" + mHoldCode + "', " +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " 'Y', " +
                " '" + tempTableIdx + "' " +
                " ) ";
        if (GlobalMemberValues.isLastBillList()) {
            strInsertQueryVec.addElement(strQuery_);
        }

        if (!GlobalMemberValues.isStrEmpty(mHoldCode) && !mHoldCode.equals("NOHOLDCODE")) {
            strQuery_ = "insert into temp_salecart_del2 ( " +
                    " holdcode, stcode, tableidx " +
                    " ) values ( " +
                    " '" + mHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + tempTableIdx + "' " +
                    " ) ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strQuery_);
            }

            // 해당 스테이션 외의 다른 스테이션의 salon_newcartcheck_bystation 데이터 지우기
            strQuery_ = " delete from salon_newcartcheck_bystation where " +
                    " holdcode = '" + mHoldCode + "' " +
                    " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
            if (GlobalMemberValues.isLastBillList()) {
                strInsertQueryVec.addElement(strQuery_);
            }
        }


        for (String tempQuery : strInsertQueryVec) {
            GlobalMemberValues.logWrite("PaymentQueryString", "query : " + tempQuery + "\n");
        }
        // 트랜잭션으로 DB 처리한다.
        returnDbResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);

        // DB 에서 삭제가 정상적으로 되었을 경우에만 (리턴값이 "0" 일경우)
        if (returnDbResult == "N" || returnDbResult == "") {
        } else {

            // 장바구니데이터 삭제 클라우드 전송
            GlobalMemberValues gm = new GlobalMemberValues();
            if (gm.isPOSWebPay() &&
                    (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                GlobalMemberValues.setSendCartDeleteToCloud(MainActivity.mContext, MainActivity.mActivity);
            }

            listViewCount = 0;                  // 리스트뷰에 있는 항목수를 0 으로 초기화
            mTempSaleCart = null;               // TemporarySaleCart 객체 초기화
            mGeneralArrayList.clear();          // ArrayList 초기화

            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW != null) {
                try {
                    mSaleCartAdapter.notifyDataSetChanged();
//                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(null);   // 어뎁터 초기화
                } catch (Exception e) {
                    GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
                }
            }

            //jihun park presentation listview
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.setAdapter(null);
                }
            }

            if (mSaleCartAdapter != null && !mSaleCartAdapter.isEmpty()) {
                mSaleCartAdapter.notifyDataSetChanged();
            }

            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (mPresentationCartAdapter != null && !mPresentationCartAdapter.isEmpty()) {
                    mPresentationCartAdapter.notifyDataSetChanged();
                }
            }

            // Sub Total, Tax, Total 텍스트뷰 $0 으로 초기화
            setCalcSubTotalTaxTotalValue(null);
            //GlobalMemberValues.logWrite("ButtonClick", "clicked\n");

            selectedPosition = -1;

            mIsAllDiscount = "N";
            discountExtraPositionVector = null;

            Discount.mCouponNumberVec.removeAllElements();
            Discount.mCouponNumberVec.clear();

            mListviewItemCount = 0;

            GlobalMemberValues.eloCfdScreenViewInit();
        }
    }

    public static void clearOnlyListView() {
        listViewCount = 0;                  // 리스트뷰에 있는 항목수를 0 으로 초기화
        mTempSaleCart = null;               // TemporarySaleCart 객체 초기화
        mGeneralArrayList.clear();          // ArrayList 초기화

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW != null) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(null);   // 어뎁터 초기화
        }

        //jihun park presentation listview
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.setAdapter(null);
            }
        }

        if (mSaleCartAdapter != null) {
            mSaleCartAdapter.notifyDataSetChanged();
        }

        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            if (mPresentationCartAdapter != null) {
                mPresentationCartAdapter.notifyDataSetChanged();
            }
        }

        // Sub Total, Tax, Total 텍스트뷰 $0 으로 초기화
        setCalcSubTotalTaxTotalValue(null);
        //GlobalMemberValues.logWrite("ButtonClick", "clicked\n");

        selectedPosition = -1;

        mIsAllDiscount = "N";
        discountExtraPositionVector = null;
        mHoldCode = "NOHOLDCODE";

        mListviewItemCount = 0;

        if (mGeneralArrayList.size() > 0){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
        }
    }


    /** Sub Total, Tax, Total 텍스트뷰의 텍스트 셋팅 메소드 ******************************************************************/
    public static void setCalcSubTotalTaxTotalValue(String[] strArray) {
        String subtotalTxt = "0.0";
        String taxTxt = "0.0";
        String totalTxt = "0.0";
        String commongratuityTxt = "0.0";

        if (strArray != null) {
            // Sub Total 텍스트뷰에 값을 넣는다.
            if (!GlobalMemberValues.isStrEmpty(strArray[0])) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.setText("$" + GlobalMemberValues.getCommaStringForDouble(strArray[0]));
                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (strArray[0] != null && !strArray[0].equals("")) {
                        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null) {
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText(GlobalMemberValues.getCommaStringForDouble(strArray[0]));
                        }
                    }
                }
                subtotalTxt = GlobalMemberValues.getCommaStringForDouble(strArray[0]);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.setText("$0");
                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText("0");
                    }
                }
                subtotalTxt = "0.0";
            }
            // Common Gratuity 텍스트뷰에 값을 넣는다.
            if (!GlobalMemberValues.isStrEmpty(strArray[3])){
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setText("$" + GlobalMemberValues.getCommaStringForDouble(strArray[3]));
                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
//                    if (strArray[3] != null && !strArray[3].equals("")) {
//                        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null) {
//                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText(GlobalMemberValues.getCommaStringForDouble(strArray[3]));
//                        }
//                    }
                }
                commongratuityTxt = GlobalMemberValues.getCommaStringForDouble(strArray[3]);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setText("$0");
                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
//                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null){
//                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText("0");
//                    }
                }
                commongratuityTxt = "0.0";
            }

            // Tax 텍스트뷰에 값을 넣는다.
            if (!GlobalMemberValues.isStrEmpty(strArray[1])) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.setText("$" + GlobalMemberValues.getCommaStringForDouble(strArray[1]));

                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW != null){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW.setText(GlobalMemberValues.getCommaStringForDouble(strArray[1]));
                    }
                }

                taxTxt = GlobalMemberValues.getCommaStringForDouble(strArray[1]);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.setText("$0");

                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW != null){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW.setText("0");
                    }

                }

                taxTxt = "0.0";
            }

            // 102022
            double deliverypickupfee = 0.0;
            String tempDPfee = GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINDELIVERYPICKUPPRICETV.getText().toString();
            GlobalMemberValues.logWrite("jjjtotaljjjlogjjj", "tempDPfee : " + tempDPfee + "\n");
            if (!GlobalMemberValues.isStrEmpty(tempDPfee)) {
                tempDPfee = GlobalMemberValues.getReplaceText(tempDPfee, "$", "");
                deliverypickupfee = GlobalMemberValues.getDoubleAtString(tempDPfee);
            }

            // Total 텍스트뷰에 값을 넣는다.
            double tempTotalValue = GlobalMemberValues.getDoubleAtString(subtotalTxt) + GlobalMemberValues.getDoubleAtString(taxTxt) + GlobalMemberValues.getDoubleAtString(commongratuityTxt);

            // 102022
            GlobalMemberValues.logWrite("jjjtotaljjjlogjjj", "tempTotalValue : " + tempTotalValue + "\n");
            tempTotalValue += deliverypickupfee;

            if (tempTotalValue > 0) {
                totalTxt = GlobalMemberValues.getCommaStringForDouble(tempTotalValue + "");

                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setText("$" + totalTxt);

                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null){
                        if (totalTxt.contains("$")) {
                            totalTxt = totalTxt.replace("$","");
                        }
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText(totalTxt);
                    }
                }
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setText("$0");

                //jihun park
                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null){
                        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText("0");
                    }

                }

                totalTxt = "0.0";
            }
        } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setText("$0");
            //jihun park
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText("0");
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW.setText("0");
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText("0");
                }
            }
        }

        GlobalMemberValues.logWrite("discountminuslog", "total : " + GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString() + "\n");

        if (GlobalMemberValues.getDoubleAtString(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.getText().toString()) <= 0) {
            // POS 화면
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINSUBTOTALTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTAXTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINTOTALTEXTVIEW.setText("$0");
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINCOMMONGRATUITYTEXTVIEW.setText("$0");
            if (mGeneralArrayList.isEmpty()){
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY != null){
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAIN_TOP_LEFT_TOTAL_ORDER_QTY.setText("0");
                }
            } else {

            }


            // Dual Display 화면
            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW.setText("0");
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW.setText("0");
                }
                if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW != null) {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW.setText("0");
                }
            }

            subtotalTxt = "0.0";
            taxTxt = "0.0";
            totalTxt = "0.0";
            commongratuityTxt = "0.0";
        }


        // 하단은 common gratuity 적용하지 않음.
        String strLine1 = subtotalTxt + " + Tax " + taxTxt;
        String strLine2 = "Total " + totalTxt;
        GlobalMemberValues.eloCfdScreenViewOn(strLine1, strLine2);

        GlobalMemberValues.setOrderListOnCloverDisplay(MainMiddleService.mHoldCode);
    }

    /** Sub Total, Tax, Total 구하는 메소드 ********************************************************************************/
    // String 배열로 Sub Total, Tax, Total 값을 리턴해 준다.---------------------------------------------------------------
    public static String[] getCalcSubTotalTaxTotalValue(ArrayList<TemporarySaleCart> paramSaleCart) {
        double tvSubTotalValue = 0.0;
        double tvSubTotalValueBal = 0.0;
        double tvTaxValue = 0.0;
        double tvTotalValue = 0.0;
        double d_tempLastItemTax = 0.0;
        double tv_CommonGratuitybalue = 0.0;
        for (TemporarySaleCart tempSaleCart : paramSaleCart) {
            if (!(tempSaleCart.mSaveType == "8" || tempSaleCart.mSaveType == "9")) {
                double tempSPriceAmount = 0.0;
                tempSPriceAmount = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSPriceAmount);
                double tempSPriceBalAmount = 0.0;
                tempSPriceBalAmount = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSPriceBalAmount);
                double tempSTaxAmount = 0.0;
                tempSTaxAmount = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSTaxAmount);
                double tempSTotalAmount = 0.0;
                tempSTotalAmount = GlobalMemberValues.getDoubleAtString(tempSaleCart.mSTotalAmount);

                GlobalMemberValues.logWrite("jjjtaxlogjjjWhy", "tempSTaxAmount  : " + tempSTaxAmount + "\n");

                GlobalMemberValues.logWrite("subtotaltaxtotallog2", "tempSPriceBalAmount  : " + GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSPriceBalAmount, "2")) + "\n");

                tvSubTotalValue += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSPriceAmount, "2"));                // 할인/추가 이전금액
                tvSubTotalValueBal += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSPriceBalAmount, "2"));          // 할인/추가 적용금액
                tvTaxValue      += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSTaxAmount, "2"));
                tvTotalValue    += GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSTotalAmount, "2"));

                d_tempLastItemTax = tempSTaxAmount;

                if (tempSaleCart.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
                    tv_CommonGratuitybalue = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tempSPriceBalAmount,"2"));
                    tvSubTotalValueBal = tvSubTotalValueBal - tv_CommonGratuitybalue;
                }
            }
        }

        GlobalMemberValues.logWrite("subtotaltaxtotallog", "tvSubTotalValueBal  : " + tvSubTotalValueBal + "\n");
        GlobalMemberValues.logWrite("subtotaltaxtotallog", "tvTaxValue  : " + tvTaxValue + "\n");
        GlobalMemberValues.logWrite("subtotaltaxtotallog", "tvTotalValue  : " + tvTotalValue + "\n");

//        // tax 계산점 변경.
//        double new_d_total_tax = GlobalMemberValues.getDoubleAtString(GlobalMemberValues.getStringFormatNumber(tvSubTotalValueBal * GlobalMemberValues.STORE_SERVICE_TAX * 0.01,"2"));
//        if (new_d_total_tax > tvTaxValue){
//            // 데이터베이스 update 필요함.
//            d_tempLastItemTax = d_tempLastItemTax + (new_d_total_tax - tvTaxValue);
//
//            String strSqlQuery1 = "select top 1 idx from temp_salecart where stcode = '"+ GlobalMemberValues.getStationCode() + "' order by idx desc";
//            DatabaseInit dbInit = new DatabaseInit(context);
//            String returnCode = "";
////            returnCode = dbInit.dbExecuteReadReturnString(strSqlQuery1);
//            returnCode = MssqlDatabase.getResultSetValueToString(strSqlQuery1);
//            String strSqlQuery = "update temp_salecart set " +
//                    " sTaxAmount ='" + d_tempLastItemTax + "' " +
//                    " where idx = '" + returnCode + "' and not(svcName like '%" + GlobalMemberValues.mCommonGratuityName + "%') and sTax > 0 ";
//            GlobalMemberValues.logWrite("updateQuery", strSqlQuery + "\n");
////            dbInit.dbExecuteWriteReturnValue(strSqlQuery);
//            MssqlDatabase.executeTransactionByQuery(strSqlQuery);
//
//
//            tvTaxValue = new_d_total_tax;
//            //
//
//
//        } else {
//
//        }
//        // tax 계산점 변경.

        String[] returnStrValue = {
                // 소수점 2자리까지
                GlobalMemberValues.getStringFormatNumber(tvSubTotalValueBal,"2"),
                GlobalMemberValues.getStringFormatNumber(tvTaxValue, "2"),
                GlobalMemberValues.getStringFormatNumber(tvTotalValue, "2"),
                GlobalMemberValues.getStringFormatNumber(tv_CommonGratuitybalue, "2")
        };

        return returnStrValue;
    }
    /***********************************************************************************************************************/

    public static void setSelectedPosition(int paramPosition) {
        GlobalMemberValues.logWrite("SelectedMaxIdx", "position : " + paramPosition + "\n");
        //LinearLayout tempLn = (LinearLayout)view.findViewById(R.id.mainSaleCartDiscountLinearLayout);
        //tempLn.setVisibility(View.VISIBLE);
        //selectedItemView = (LinearLayout)view;
        // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
        if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
        // ------------------------------------------------------------
        if (selectedPosition == paramPosition) {
            mSaleCartAdapter.notifyDataSetChanged();

            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                mPresentationCartAdapter.notifyDataSetChanged();
            }

            selectedPosition = -1;
        } else {
            selectedPosition = paramPosition;
        }
    }

    // cart 의 모든 아이템을 선택 또는 해제시
    public static void setAllSelectedPosition(){
        if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
        boolean b_is_allselected = true;
        for (int position = 0 ; MainMiddleService.mGeneralArrayList.size() > position ; position++ ){
            String itemSaveType = mGeneralArrayList.get(position).mSaveType;
            if (!(itemSaveType == "8" || itemSaveType == "9")) {
                if (!isCheckedConfrim[position]){
                    // 한번이라도 여기 들어오면 all selected 가 아닌상태.
                    b_is_allselected = false;
                }
            }
        }

        if (!b_is_allselected){
            // all selected 로 변경
            for (int position = 0 ; MainMiddleService.mGeneralArrayList.size() > position ; position++ ){
                if (mGeneralArrayList.size() > 0) {
                    String itemSaveType = mGeneralArrayList.get(position).mSaveType;

                    if (!(itemSaveType == "8" || itemSaveType == "9")) {
                        GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no1 : " + isCheckedConfrim[position]);
                        isCheckedConfrim[position] = true;
                        GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no2 : " + isCheckedConfrim[position]);
                        selectedPosition = position;
                    }
                }
            }
        } else {
            // all selected 해제
            for (int position = 0 ; MainMiddleService.mGeneralArrayList.size() > position ; position++ ){
                if (mGeneralArrayList.size() > 0) {
                    String itemSaveType = mGeneralArrayList.get(position).mSaveType;

                    if (!(itemSaveType == "8" || itemSaveType == "9")) {
                        GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no1 : " + isCheckedConfrim[position]);
                        isCheckedConfrim[position] = false;
                        GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no2 : " + isCheckedConfrim[position]);
                    }
                }
            }
        }

    }

    /** 리스트뷰의 항목을 클릭할 때 발생하는 OnClickListenr ****************************************************************/
    // 짧게(일반적인) 한번 클릭할 때
    static AdapterView.OnItemClickListener mSaleCartItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSelectedPosition(position);

            if (GlobalMemberValues.isMultiCheckOnCart()) {
                // 멀티선택관련 추가
                // 리스트뷰 선택함수 호출
                setChecked(position);
            }

            // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
            mSaleCartAdapter.notifyDataSetChanged();

            if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                mPresentationCartAdapter.notifyDataSetChanged();
            }
        }
    };

    public static void setCheckedItems(int position){
        if (GlobalMemberValues.isMultiCheckOnCart()) {
            // 멀티선택관련 추가
            // 리스트뷰 선택함수 호출
            setChecked(position);
        }

        // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
        mSaleCartAdapter.notifyDataSetChanged();

        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            mPresentationCartAdapter.notifyDataSetChanged();
        }
    }

    // 멀티선택관련 추가
    // 리스트뷰 선택함수
    public static void setChecked(int position) {
        if (mGeneralArrayList.size() > 0) {
            String itemSaveType = mGeneralArrayList.get(position).mSaveType;

            if (!(itemSaveType == "8" || itemSaveType == "9")) {
                GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no1 : " + isCheckedConfrim[position]);
                isCheckedConfrim[position] = !isCheckedConfrim[position];
                GlobalMemberValues.logWrite("CheckedListViewLog", "yes/no2 : " + isCheckedConfrim[position]);

                //
                if (isCheckedConfrim[position]) {
                    selectedPosition = position;
                }
            }
        }
    }

    // 길게 클릭할 때
    static AdapterView.OnItemLongClickListener mSaleCartItemLongClickListner = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // 주방에 보낼 메모창 띄우기
            openKitchenMemoPopupOnItem(position);

            /**
             if (mIsAllDiscount == "Y") {
             GlobalMemberValues.displayDialog(context, "Warning", "You can't add/edit/delete the item\n" +
             "because this sale includes 'All Discount' or 'All Extra'", "Close");
             } else {
             if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED != "Y") {
             // 선택한 항목의 포지션을 선택포지션값(selectedPosition) 에 할당
             selectedPosition = position;
             new AlertDialog.Builder(context)
             .setTitle("Item Delete")
             .setMessage("Do you want to delete this item?")
             //.setIcon(R.drawable.ic_launcher)
             .setPositiveButton("Yes",
             new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
             // 리스트뷰의 항목을 삭제하는 메소드 호출
             deleteListViewItem();
             }
             })
             .setNegativeButton("No", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
             //
             }
             })
             .show();
             }
             }
             **/

            return true;
        }
    };
    /***********************************************************************************************************************/

    /** 리스트뷰의 선택항목 삭제처리 메소드 ****************************************************************/
    // 멀티선택관련 추가
    // 기존 함수 수정
    public static void deleteListViewItem() {
        if (GlobalMemberValues.isMultiCheckOnCart()) {
            int tempSize = mGeneralArrayList.size();
            if (tempSize == 0) {
                return;
            }
            int checkedItemCount = 0;
            for(int i = 0; i < tempSize; i++){
                if(isCheckedConfrim[i]){
                    checkedItemCount++;
                }
            }

//            // 230816 추가
//            if (checkedItemCount > 1){
//
//                mGeneralArrayList_copy = new ArrayList<TemporarySaleCart>();
//                for (TemporarySaleCart temp_a : mGeneralArrayList){
//                    mGeneralArrayList_copy.add(temp_a);
//                }
//            }

            if (mGeneralArrayList.size() > 0 && checkedItemCount > 0) {

                boolean tempLastItem = false;
                int tempCount = 0;
                for(int j = (tempSize - 1); j >= 0; j--){
                    if(isCheckedConfrim[j]){
                        tempCount++;
                        if (checkedItemCount == tempCount) {
                            tempLastItem = true;
                        } else {
                            tempLastItem = false;
                        }
                        GlobalMemberValues.logWrite("multicheckedlog", "j : " + j + "\n");
                        deleteItem(j, tempLastItem, true, "Delete Item");
                    }
                }

            }
        } else {
            if (mGeneralArrayList.size() > 0 && selectedPosition > -1) {
                deleteItem(selectedPosition, false, true, "");
            }
        }

        //jihun selected 초기화.
        selectedPosition = -1;
        mSaleCartAdapter.notifyDataSetChanged();
        // 장바구니 메뉴가 담길때만 send to kitchen 보이게
        if (mGeneralArrayList.size() > 0){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.VISIBLE);
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER != null) GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONSAVEORDER.setVisibility(View.INVISIBLE);
        }
    }

    public static void deleteItem(int paramSelectedPosition, boolean paramLastItem, boolean paramIsAlert, String str_title) {
        // 230816 title 추가
        if (str_title == "" || str_title.isEmpty()) {
            str_title = "Table Clear";
        }

        GlobalMemberValues globalMemberValues = new GlobalMemberValues();

        // 레스토랑일 경우 삭제된 메뉴를 주방으로 프린팅할지를 물어본다.
        if (globalMemberValues.getPOSType().toUpperCase() == "R" || globalMemberValues.getPOSType().toUpperCase().equals("R")) {
            if (TableSaleMain.mSelectedTablesArrList != null && TableSaleMain.mSelectedTablesArrList.size() > 0) {
                TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(paramSelectedPosition);
                if (paramIsAlert && !tempSaleCartInstance.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)) {
                    if (GlobalMemberValues.isOpenmsgwhendeletemenu()){
                        new AlertDialog.Builder(mActivity)
                                .setTitle(str_title)
                                .setMessage("Would you like to print the canceled order to the kitchen?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                printCancelItem(tempSaleCartInstance,paramSelectedPosition,paramLastItem);
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteItemExe(paramSelectedPosition,paramLastItem);
                                    }
                                })
                                .show();
                    } else {
                        printCancelItem(tempSaleCartInstance,paramSelectedPosition,paramLastItem);
                    }

                } else {
                    deleteItemExe(paramSelectedPosition,paramLastItem);
                }
            } else {
                deleteItemExe(paramSelectedPosition,paramLastItem);
            }


        } else {
            deleteItemExe(paramSelectedPosition,paramLastItem);
        }
    }

    // 230816

    public static void printCancelItem(TemporarySaleCart tempSaleCartInstance, int paramSelectedPosition, boolean paramLastItem){
        GlobalMemberValues.logWrite("jjtablelog", "mSelectedTableIdx : " + GlobalMemberValues.mSelectedTableIdx + "\n");

//                                        if (TableSaleMain.mSelectedTablesArrList != null) {
//                                            GlobalMemberValues.logWrite("jjtablelog",
//                                                    "선택한 tableidx : " + TableSaleMain.mSelectedTablesArrList.toString() + "\n");
//                                            GlobalMemberValues.logWrite("jjtablelog",
//                                                    "선택한 arr size : " + TableSaleMain.mSelectedTablesArrList.size() + "\n");
//                                        }

        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.mSelectedTableIdx)) {
            ArrayList<String> tempArr = new ArrayList<String>();
            tempArr.add(GlobalMemberValues.mSelectedTableIdx);

//                                                TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(paramSelectedPosition);
            String tempHoldCode = tempSaleCartInstance.mHoldCode;
            String tempSaleCardIdx = tempSaleCartInstance.tempSaleCartIdx;

            GlobalMemberValues.logWrite("jjtablelog", "tempSaleCardIdx : " + tempSaleCardIdx + "\n");
            GlobalMemberValues.logWrite("jjtablelog", "tempHoldCode : " + tempHoldCode + "\n");

            if (!GlobalMemberValues.isStrEmpty(tempSaleCardIdx) && !tempHoldCode.equals("NOHOLDCODE")) {
                GlobalMemberValues.mDeletedSaleCartIdx = tempSaleCardIdx;
                GlobalMemberValues.mCancelKitchenPrinting = "Y";         // 주문취소 키친프린팅인지 여부
                Recall.mKitchenPrintOnRecall = "Y";                      // Recall.mKitchenPrintOnRecall 의 값이 Y 일 경우 프린트여부와 상관없이 모두 프린트됨
                TableSaleMain.phoneorderkitchenPrintingExe(tempHoldCode);

                // 03.11.2022
                // 윈도우 키친프린팅용 작업 (salon_sales_kitchenprintingdata_json 테이블에 저장) --------------------------------
                String get_tableInfos = "";
                get_tableInfos = TableSaleMain.getTableInfosByHoldCode(tempHoldCode);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = GlobalMemberValues.getKitchenPrintingJsonForWindows(tempHoldCode, get_tableInfos, tempSaleCardIdx);
                    String strQuery = " update salon_sales_kitchenprintingdata_json set " +
                            " jsonstr = N'" + jsonObject.toString() + "', printedyn = 'N' " +
                            " where salescode = '" + tempHoldCode + "' ";
                    MssqlDatabase.executeTransactionByQuery(strQuery);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // --------------------------------------------------------------------------------------------------------

                deleteItemExe(paramSelectedPosition,paramLastItem);
            } else {
                deleteItemExe(paramSelectedPosition,paramLastItem);
            }
        } else {
            deleteItemExe(paramSelectedPosition,paramLastItem);
        }
    }

    // 멀티선택관련 추가
    public static void deleteItemExe(int paramSelectedPosition, boolean paramLastItem) {
        GlobalMemberValues.logWrite("multicheckedlog", "paramSelectedPosition : " + paramSelectedPosition + "\n");
        TemporarySaleCart tempSaleCartInstance = null;
        if (mGeneralArrayList.size() > paramSelectedPosition) {
            tempSaleCartInstance = mGeneralArrayList.get(paramSelectedPosition);
        } else {
            return;
        }

        // 먼저 해당 항목이 Discount / Extra 용 아이템인지 확인한다.
        // TemporarySaleCart 객체 변수에 selectedDcExtraParentTempSaleCartIdx (부모아이템 temp_salecart 인덱스값) 이
        // 있으면 Discount / Extra 용 아이템
        // 또한 paramSelectedPosition 값이 무조건 0 이상이어야 한다.
        // 왜냐하면 Discount / Extra 용 아이템의 포지션값은 1 이상어야야만 하니까...
        if (paramSelectedPosition > 0 && !GlobalMemberValues.isStrEmpty(tempSaleCartInstance.selectedDcExtraParentTempSaleCartIdx)) {
            // Discount / Extra 아이템 삭제후 부모아이템 삭제를 위해 부모아이템의 포지션값을 미리 저장해 둔다.
            int tempPrePosition = paramSelectedPosition - 1;

            // 해당 Discount / Extra 아이템 삭제
            mGeneralArrayList.remove(paramSelectedPosition);

            // 부모아이템 삭제를 위한 준비
            // 추후에 부모아이템 삭제시에 TemporarySaleCart 객체의 selectedDcExtraPrice 값의 유무로
            // 부모아이템에 해당하는 Discount / Extra 아이템 삭제처리를 하는데,
            // Discount / Extra 아이템을 먼저 삭제후 부모아이템을 삭제하는 것이기 때문에
            // 부모아이템의 TemporarySaleCart 객체의 selectedDcExtraPrice 값을 비워둔다..
            mGeneralArrayList.get(tempPrePosition).selectedDcExtraPrice = "";
            paramSelectedPosition = tempPrePosition;
            deleteListViewItem();
        }

        // 옵션값이 있는지 체크한다.
        String tempOptionAdditionalYN = "N";
        if (!GlobalMemberValues.isStrEmpty(tempSaleCartInstance.optionTxt) ||
                !GlobalMemberValues.isStrEmpty(tempSaleCartInstance.additionalTxt1) ||!GlobalMemberValues.isStrEmpty(tempSaleCartInstance.additionalTxt2)) {
            tempOptionAdditionalYN = "Y";
        }

        // 쿠폰번호를 가져온다.
        String tempCouponNumber = tempSaleCartInstance.couponNumber;
        // DB 처리를 위해 선택된 리스트 항목의 tempSaleCartIdx 값을 가져온다.
        String tempSaleCartIdx = tempSaleCartInstance.tempSaleCartIdx;
        if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) { // tempSaleCartIdx (temp_salecart 테이블의 idx 값) 이 있을 경우에만..
            Vector<String> strTempVec = new Vector<String>();
            String returnDbResult = "";

            // DB 처리를 먼저 실행한다.

            String tempTableIdx = MssqlDatabase.getResultSetValueToString(
                    " select tableidx from temp_salecart where idx = '" + tempSaleCartIdx + "'"
            );
            if (GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                tempTableIdx = "";
            }

            // 02092024
            // temp_salecart 삭제전에 tordercode 값을 가져온다.
            String temp_tordercode = MssqlDatabase.getResultSetValueToString(
                    " select tordercode from temp_salecart where idx = '" + tempSaleCartIdx + "'"
            );

            String strQuery_ = "delete from temp_salecart where idx = '" + tempSaleCartIdx + "'";
            //String returnDbResult = dbInit.dbExecuteWriteReturnValue(strQuery_);
            strTempVec.addElement(strQuery_);

            strQuery_ = "insert into temp_salecart_del ( " +
                    " holdcode, sidx, stcode, midx, svcIdx, cartIdx, tableidx, tordercode " +
                    " ) values ( " +
                    " '" + tempSaleCartInstance.mHoldCode + "', " +
                    " '" + tempSaleCartInstance.mSidx + "', " +
                    " '" + tempSaleCartInstance.mStcode + "', " +
                    " '" + tempSaleCartInstance.mMidx + "', " +
                    " '" + tempSaleCartInstance.mSvcidx + "', " +
                    " '" + tempSaleCartIdx + "', " +
                    " '" + tempTableIdx + "', " +
                    " '" + temp_tordercode + "' " +
                    " ) ";
            strTempVec.addElement(strQuery_);

            if (!GlobalMemberValues.isStrEmpty(tempSaleCartInstance.mHoldCode)
                    && !tempSaleCartInstance.mHoldCode.equals("NOHOLDCODE")) {
                strQuery_ = "insert into temp_salecart_del2 ( " +
                        " holdcode, stcode, tableidx " +
                        " ) values ( " +
                        " '" + tempSaleCartInstance.mHoldCode + "', " +
                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                        " '" + tempTableIdx + "' " +
                        " ) ";
                strTempVec.addElement(strQuery_);

                // 해당 스테이션 외의 다른 스테이션의 salon_newcartcheck_bystation 데이터 지우기
                strQuery_ = " delete from salon_newcartcheck_bystation where " +
                        " holdcode = '" + tempSaleCartInstance.mHoldCode + "' " +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') ";
                strTempVec.addElement(strQuery_);
            }

            for (String tempQuery : strTempVec) {
                GlobalMemberValues.logWrite("deletesqljjjlog", "query : " + tempQuery + "\n");
            }

            returnDbResult = dbInit.dbExecuteWriteForTransactionReturnResult(strTempVec);
            // DB 처리가 정상적으로 되었을 경우에만 (리턴값이 "N" 아닐경우)
            if (returnDbResult == "N" || returnDbResult == "") {        // DB (salon_sales_card 테이블) 입력실패
                GlobalMemberValues.displayDialog(context, "Warning", "Database Error. Try Again", "Close");
            } else {
                // 장바구니데이터 삭제 클라우드 전송
                GlobalMemberValues gm = new GlobalMemberValues();
                if (gm.isPOSWebPay() &&
                        (gm.getPOSType().toUpperCase() == "R" || gm.getPOSType().toUpperCase().equals("R"))) {
                    GlobalMemberValues.setSendCartDeleteToCloud(MainActivity.mContext, MainActivity.mActivity);
                }

                mGeneralArrayList.remove(paramSelectedPosition);
                // 해당 항목이 Discount 또는 Extra 일 경우 해당되는 항목도 삭제
                // TemporarySaleCart 객체 selectedDcExtraPrice 변수에 값이 있으면 Discount 또는 Extra
                String tempSelectedDcExtraPrice = tempSaleCartInstance.selectedDcExtraPrice;
                if (!GlobalMemberValues.isStrEmpty(tempSelectedDcExtraPrice)) {
                    mGeneralArrayList.remove(paramSelectedPosition);
                }
                /** Discount / Extra 시에 해당 서비스/상품/기프트카드 항목의 position 값을
                 /** Vector<String> 으로 저장한 것을 이용하여 삭제하는 방법
                 /*******************************************************************************************************
                 // 해당 항목이 Discount 또는 Extra 일 경우 해당되는 항목도 삭제
                 //GlobalMemberValues.logWrite("MainMiddleService", "DiscountExtraPosition size : " + discountExtraPositionVector.size() + "\n");
                 if (discountExtraPositionVector != null && discountExtraPositionVector.size() > 0) {
                 int tempLoc = discountExtraPositionVector.lastIndexOf("dcex_" + String.valueOf(paramSelectedPosition));
                 GlobalMemberValues.logWrite("MainMiddleService", "DiscountExtraPosition index of : " + tempLoc + "\n");
                 if (tempLoc == 0) {
                 mGeneralArrayList.remove(paramSelectedPosition);
                 discountExtraPositionVector.removeElement("dcex_" + String.valueOf(paramSelectedPosition));
                 }
                 }
                 ********************************************************************************************************/
                // 멀티선택관련 추가
                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    if (paramLastItem) {
                        isCheckedConfrim = new boolean[mGeneralArrayList.size()];
                    }
                }

                mSaleCartAdapter.notifyDataSetChanged();

                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    mPresentationCartAdapter.notifyDataSetChanged();
                }

                //paramSelectedPosition = -1;

                // Sub Total, Tax, Total 텍스트뷰에 값을 할당한다.
                String calResultStrArr[] = getCalcSubTotalTaxTotalValue(mGeneralArrayList);
                setCalcSubTotalTaxTotalValue(calResultStrArr);

                // 쿠폰사용이 있을 경우 쿠폰사용초기화
                // (Discount 클래스의 mCouponNumberVec 에서 해당 쿠폰삭제)
                if (!GlobalMemberValues.isStrEmpty(tempCouponNumber)) {
                    Discount.mCouponNumberVec.removeElement(tempCouponNumber);
                }

                // 옵션값이 있을 경우 mListviewItemCount 의 값을 -2 한다.
                if (tempOptionAdditionalYN == "Y" || tempOptionAdditionalYN.equals("Y")) {
                    if (mListviewItemCount > 1) {
                        mListviewItemCount = mListviewItemCount - 2;
                    } else {
                        mListviewItemCount = 0;
                    }
                } else {
                    if (mListviewItemCount > 0) {
                        mListviewItemCount = mListviewItemCount - 1;
                    } else {
                        mListviewItemCount = 0;
                    }
                }
            }
        }
    }

    /***********************************************************************************************************************/

    /** 주방에 보낼 메모 작성 팝업페이지 오픈 ***********************************************************************************/
    public static void openKitchenMemoPopupOnItem(int paramPosition) {
        //setSelectedPosition(paramPosition);
//        selectedPosition = paramPosition;

        //GlobalMemberValues.logWrite("ArrayListSize", "ArrayList Size : " + mGeneralArrayList.size() + "\n");
        if (mGeneralArrayList.size() > 0 && paramPosition > -1) {
            TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(paramPosition);
            String tempHoldCode = tempSaleCartInstance.mHoldCode;

            TemporarySaleCart tempSCIns = mGeneralArrayList.get(paramPosition);

            Intent kitchenMemoIntent = new Intent(MainActivity.mContext, MemoToKitchen.class);
            kitchenMemoIntent.putExtra("holdcode", tempHoldCode);
            kitchenMemoIntent.putExtra("ParentTemporarySaleCartInstance", tempSCIns);
            kitchenMemoIntent.putExtra("ParentSaleCartPosition", String.valueOf(paramPosition));
            mActivity.startActivity(kitchenMemoIntent);
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

//        if (mGeneralArrayList.size() > 0 && selectedPosition > -1) {
//            TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(selectedPosition);
//            String tempHoldCode = tempSaleCartInstance.mHoldCode;
//
//            TemporarySaleCart tempSCIns = mGeneralArrayList.get(selectedPosition);
//
//            Intent kitchenMemoIntent = new Intent(MainActivity.mContext, MemoToKitchen.class);
//            kitchenMemoIntent.putExtra("holdcode", tempHoldCode);
//            kitchenMemoIntent.putExtra("ParentTemporarySaleCartInstance", tempSCIns);
//            kitchenMemoIntent.putExtra("ParentSaleCartPosition", String.valueOf(selectedPosition));
//            mActivity.startActivity(kitchenMemoIntent);
//            mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
//        }
    }
    /***********************************************************************************************************************/

    /**
     public void setListViewItem(ArrayList<TemporarySaleCart> paramArrayList) {
     listViewCount = 0;                  // 리스트뷰에 있는 항목수를 0 으로 초기화
     mTempSaleCart = null;               // TemporarySaleCart 객체 초기화
     mGeneralArrayList.clear();          // ArrayList 초기화
     mSaleCartAdapter = null;
     GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(null);   // 어뎁터 초기화

     mGeneralArrayList = paramArrayList;

     mSaleCartAdapter = new SaleCartAdapter(context, R.layout.main_salecart_list, mGeneralArrayList);
     GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(mSaleCartAdapter);
     String[] strValueForTextView = null;
     strValueForTextView = getCalcSubTotalTaxTotalValue(paramArrayList);
     // getCalcSubTotalTaxTotalValue 메소드로 구해온 값들을 Sub Total, Tax, Total 텍스트뷰에 넣는다.
     setCalcSubTotalTaxTotalValue(strValueForTextView);

     listViewCount = mGeneralArrayList.size();
     }
     **/

    public static void setCustomerEmployeeInfo() {
        if (GlobalMemberValues.GLOBAL_CUSTOMERINFO != null) {
            insCustomerId = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memUid;
            insCustomerName = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memName;
            insCustomerPhone = GlobalMemberValues.GLOBAL_CUSTOMERINFO.memPhone;
        }
        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
            insEmpIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            insEmpName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
        }
    }

    public static boolean checkAllDiscountExtra() {
        boolean returnBoolean = true;
        if (mIsAllDiscount == "Y") {
            GlobalMemberValues.displayDialog(context, "Warning", "You can't add/edit/delete the item\n" +
                    "because this sale includes 'All Discount' or 'All Extra'", "Close");
            returnBoolean = false;
        }
        return returnBoolean;
    }

    public static void qty_set(String edittingQty, TemporarySaleCart parentTemporarySaleCart, int parentSelectedPosition) {
        int tempEdittingQty = GlobalMemberValues.getIntAtString(edittingQty);;
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
        double tempmSPrice = 0.00;
        if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSPrice)) {
            tempmSPrice = (Double.parseDouble(parentTemporarySaleCart.mSPrice));
            tempMSPriceBalAmount = tempmSPrice * tempEdittingQty;
        }
        String insMSPriceBalAmount = "0.0";
        if (tempMSPriceBalAmount > 0) {
            insMSPriceBalAmount = String.format("%.2f", tempMSPriceBalAmount);
        }

        GlobalMemberValues.logWrite("TAJJLOG", "insMSPriceBalAmount : " + insMSPriceBalAmount + "\n");
        // ---------------------------------------------------------------------------------------------
        // 세금 합계 변경 ------------------------------------------------------------------------------
        // Tax 값이 0 인 경우 Tax Exempt 를 한 경우이므로, 재설정이 필요없다.
        double tempTaxEach = 0.00;
        double tempTaxAmountSaleCart = GlobalMemberValues.getDoubleAtString(parentTemporarySaleCart.mSTaxAmount);
        double tempMSTaxAmount = 0.00;
        String insMSTaxAmount = "0.0";
        boolean is_tax_multiYN = GlobalMemberValues.isTaxTypeMulti("S", parentTemporarySaleCart.mSvcidx);
        if (tempTaxAmountSaleCart > 0) {
            String tempSaveType = parentTemporarySaleCart.mSaveType;
            if (GlobalMemberValues.isStrEmpty(tempSaveType)) {
                tempSaveType = "0";
            }
            double tempTaxRate = 0.0;
            switch (tempSaveType) {
                case "0" : {
                    if (is_tax_multiYN){
                        // tempTaxRate = GlobalMemberValues.getTax3InStoreGeneral() + GlobalMemberValues.getTax4InStoreGeneral() + GlobalMemberValues.getTax5InStoreGeneral();
                        // 10212022
                        tempTaxRate = GlobalMemberValues.getItemTaxInMultiTax("S", parentTemporarySaleCart.mSvcidx);
                    } else {
                        tempTaxRate = GlobalMemberValues.getTax1InStoreGeneral();
                    }
                    break;
                }
                case "1" : {
                    tempTaxRate = GlobalMemberValues.getTax2InStoreGeneral();
                    break;
                }
            }

            if (tempTaxRate > 0) {
                tempTaxEach = tempmSPrice * tempTaxRate * 0.01;
            }

//            String tempTaxEachStr = String.format("%.2f", tempTaxEach);
            String tempTaxEachStr = GlobalMemberValues.getStringFormatNumber(tempTaxEach,"2");


            tempMSTaxAmount = GlobalMemberValues.getDoubleAtString(tempTaxEachStr) * tempEdittingQty;

            /**
             if (!GlobalMemberValues.isStrEmpty(parentTemporarySaleCart.mSTax)) {
             tempMSTaxAmount = (Double.parseDouble(parentTemporarySaleCart.mSTax)) * tempEdittingQty;
             }
             **/

            if (tempMSTaxAmount > 0) {
//                insMSTaxAmount = String.format("%.2f", tempMSTaxAmount);
                insMSTaxAmount = GlobalMemberValues.getStringFormatNumber(tempMSTaxAmount,"2");
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
            DatabaseInit dbInit = new DatabaseInit(context);
            String returnCode = "";
            returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
            GlobalMemberValues.logWrite("updateQuery", "returnCode : " + returnCode + "\n");
            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
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


                // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                MainMiddleService.mGeneralArrayList.set(parentSelectedPosition, parentTemporarySaleCart);
                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();

                if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
                    MainMiddleService.mPresentationCartAdapter.notifyDataSetChanged();
                }

                String[] strValueForTextView = null;
                strValueForTextView = MainMiddleService.getCalcSubTotalTaxTotalValue(MainMiddleService.mGeneralArrayList);
                MainMiddleService.setCalcSubTotalTaxTotalValue(strValueForTextView);

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    // 멀티선택관련 추가
                    // 선택여부 배열크기 생성
                    //MainMiddleService.isCheckedConfrim = new boolean[MainMiddleService.mGeneralArrayList.size()];
                }

                //MainMiddleService.selectedPosition = -1;

                // main top total order count update
                GlobalMemberValues.setMainTotalOrderCountTextview();
            }
        }
    }

    public static void setInsertDividerLine() {
        if (GlobalMemberValues.isUseDividerLine()) {
            String strQuery = "select a.idx, a.midx, a.servicename " +
                    " from salon_storeservice_sub as a " +
                    " where a.divideryn = 'Y' ";
            GlobalMemberValues.logWrite("dividerlogjjj", "Query : " + strQuery + "\n");

            String svcidx = "";
            String midx = "";
            String servicename = "";

            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            while (cursor.moveToNext()) {
                svcidx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1);
                midx = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
                servicename = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
            }

            String paramsString[] = {
                    "1", mHoldCode, GlobalMemberValues.STORE_INDEX, GlobalMemberValues.STATION_CODE, midx, svcidx,
                    servicename, "", "",
                    "0", "0", "", "",
                    "0", "", "0",
                    "0", "N",
                    "", "", "", "0", "", "", "Y",
                    "", "", "", "", ""
            };

            setInsertCartNoAnimation(paramsString);
        }
    }

    private static void initCheckItem() {
        mSaleCartAdapter.notifyDataSetChanged();        // 추가된 항목을 Adapter 에 알림
        if (GlobalMemberValues.isMultiCheckOnCart()) {
            // 멀티선택관련 추가
            // 선택여부 배열크기 생성
            MainMiddleService.isCheckedConfrim = new boolean[MainMiddleService.mGeneralArrayList.size()];
        }
        MainMiddleService.selectedPosition = -1;

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX != null){
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX.isChecked()){
                GlobalMemberValues.ALL_CHECKBOX_INIT = true;
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINITEM_ALLCHECKBOX.setChecked(false);
            }
        }
    }

    public static void initList(){
        listViewCount = 0;                  // 리스트뷰에 있는 항목수를 0 으로 초기화
        mTempSaleCart = null;               // TemporarySaleCart 객체 초기화
        mGeneralArrayList.clear();          // ArrayList 초기화

        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW != null) {
            try {
                mSaleCartAdapter.notifyDataSetChanged();
//                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setAdapter(null);   // 어뎁터 초기화
            } catch (Exception e) {
                GlobalMemberValues.logWrite("Error", "Msg. : " + e.getMessage().toString() + "\n");
            }
        }

        //jihun park presentation listview
        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW.setAdapter(null);
            }
        }

        if (mSaleCartAdapter != null && !mSaleCartAdapter.isEmpty()) {
            mSaleCartAdapter.notifyDataSetChanged();
        }

        if (GlobalMemberValues.ISDUALDISPLAYPOSSIBLE) {
            if (mPresentationCartAdapter != null && !mPresentationCartAdapter.isEmpty()) {
                mPresentationCartAdapter.notifyDataSetChanged();
            }
        }

        // Sub Total, Tax, Total 텍스트뷰 $0 으로 초기화
        setCalcSubTotalTaxTotalValue(null);
        //GlobalMemberValues.logWrite("ButtonClick", "clicked\n");

        selectedPosition = -1;

        mIsAllDiscount = "N";
        discountExtraPositionVector = null;

        Discount.mCouponNumberVec.removeAllElements();
        Discount.mCouponNumberVec.clear();

        mListviewItemCount = 0;
    }

    public static void item_delete_action(){
        LogsSave.saveLogsInDB(87);

        // 02272023
        // 먼저 전체선택인지 체크해서 -----------------------------------------------------------------------------
        // 전체 선책일 경우 All Cancel 이 실행되도록 한다.
        int checkedItemCount = 0;
        int tempSize = mGeneralArrayList.size();
        for(int i = 0; i < tempSize; i++){
            if(isCheckedConfrim[i]){
                checkedItemCount++;
            }
        }


        if (checkedItemCount == mGeneralArrayList.size()) {
            GlobalMemberValues.mCancelBtnClickYN = "Y";
            setEmptyInSaleCart(false);
            return;
        }
        // ----------------------------------------------------------------------------------------------------------


        TemporarySaleCart tempSaleCartInstance = mGeneralArrayList.get(selectedPosition);
        if (tempSaleCartInstance.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
            // 리스트뷰의 항목을 삭제하는 메소드 호출
            deleteListViewItem();
        } else {
            // 리스트뷰의 항목을 삭제하는 메소드 호출
            deleteListViewItem();
            // common gratuity 관련
            GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
            GlobalMemberValues.addCartLastItemForCommonGratuityUse();
        }

        if (mGeneralArrayList.size() == 1){
            TemporarySaleCart temp = mGeneralArrayList.get(0);
            if (temp.mSvcName.equals(GlobalMemberValues.mCommonGratuityName)){
                GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
                deleteItemExe(0,false);
            }
        }

        GlobalMemberValues.setMainTotalOrderCountTextview();
    }

    public static void item_delete_action_all(){
        LogsSave.saveLogsInDB(88);
        GlobalMemberValues.mCancelBtnClickYN = "Y";
        setEmptyInSaleCart(false);
    }

}