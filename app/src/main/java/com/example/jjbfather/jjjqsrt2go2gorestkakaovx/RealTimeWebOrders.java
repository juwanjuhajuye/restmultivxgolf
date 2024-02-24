package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class RealTimeWebOrders extends Activity {
    final String TAG = "RealTimeWebOrders";

    Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    private Button closeBtn;
    private LinearLayout realTimeWebOrdersListLinearLayout;
    ScrollView realTimeWebOrdersListScrollView;

    private TextView tempRtoTv1;

    Intent mIntent;

    public ProgressDialog proDial;       // 프로그래스바
    public int mTempFlag = 0;

    // 주문내역을 저장할 ListView
    public static ArrayList<TemporaryWebOrders> mCollectionOrdersArrList;


    // 10112023
    private String mOnlinetype = "";
    private String mOrderfrom = "";
    private String mSalescodethirdparty = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_real_time_web_orders);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(context);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            // mCustomerValue = mIntent.getStringExtra("CustomerValue");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
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

        emptyLinearLayout = (LinearLayout)findViewById(R.id.emptyLinearLayout);
        emptyLinearLayout.setOnClickListener(realTimeWebOrdersBtnClickListener);

        // 스크롤뷰 객체 생성
        realTimeWebOrdersListScrollView = (ScrollView)findViewById(R.id.realTimeWebOrdersListScrollView);
        // ScrollView 에 속한 첫번째 LinearLayout 객체
        realTimeWebOrdersListLinearLayout = (LinearLayout)findViewById(R.id.realTimeWebOrdersListLinearLayout);
        /***********************************************************************************************************/

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(realTimeWebOrdersBtnClickListener);
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
        tempRtoTv1 = (TextView) findViewById(R.id.tempRtoTv1);
        tempRtoTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + 14);

        mCollectionOrdersArrList = null;
        mCollectionOrdersArrList = new ArrayList<TemporaryWebOrders>();

        setWebOrdersInPush();
    }

    public void setWebOrdersInPush() {
        // 뷰 추가전 먼저 초기화(삭제)한다.
        realTimeWebOrdersListLinearLayout.removeAllViews();

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

        if (GlobalMemberValues.getIntAtString(dbInit.dbExecuteReadReturnString("select count(idx) from salon_sales_web_push_realtime where viewyn = 'N' ")) > 0) {
            // LinearLayout 객체 생성
            LinearLayout newLn = new LinearLayout(context);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.VERTICAL);
            //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
            newLn.setBackgroundResource(R.drawable.roundlayout_background_realtimeweborders_ln1);
            newLn.setPadding(0, 0, 0, 0);

            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams statusLnLayoutParams1
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            statusLnLayoutParams1.setMargins(0, 0, 0, 0);

            // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
            LinearLayout.LayoutParams statusTvLayoutParams2
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            statusTvLayoutParams2.setMargins(0, 0, 0, 0);

            LinearLayout listLn1 = new LinearLayout(context);
            listLn1.setLayoutParams(statusLnLayoutParams1);
            listLn1.setOrientation(LinearLayout.VERTICAL);
            //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

            /**
            // Title
            TextView listTextVIew0 = new TextView(context);
            listTextVIew0.setLayoutParams(statusTvLayoutParams2);
            listTextVIew0.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew0.setPadding(10, 5, 10, 5);
            listTextVIew0.setText(" Unchecked Push List(s)");
            listTextVIew0.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew0.setTextColor(Color.parseColor("#000000"));
            listTextVIew0.setBackgroundResource(R.drawable.roundlayout_background_realtimeweborders_titlebg);;
            listTextVIew0.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew0);
             **/

            newLn.addView(listLn1);
            realTimeWebOrdersListLinearLayout.addView(newLn);
        }

        /** Push 안 읽은거 리스트 시작 ******************************************************************************************************/
        String strQuery = "";
        strQuery = "select idx, salesCode, customerId, customerName, customerPhone, " +
                " deliveryday, deliverytime, deliverydate, deliverytakeaway, saleDate, customerordernumber, " +
                // 08302032
                " onlinetype, " +

                // 10112023
                " orderfrom, " +
                " salescodethirdparty " +

                " from salon_sales_web_push_realtime " +
                " where viewyn = 'N' " +
                " order by idx desc ";
        Cursor realTimeWebOrdersCursor;
        realTimeWebOrdersCursor = dbInit.dbExecuteRead(strQuery);
        while (realTimeWebOrdersCursor.moveToNext()) {
            String vIdx = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(0), 1);
            final String vSalesCode = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(1), 1);
            String vCustomerId = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(2), 1);
            String vCustomerName = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(3), 1);
            String vCustomerPhone = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(4), 1);
            String vDeliveryday = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(5), 1);
            String vDeliverytime = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(6), 1);
            String vDeliverydate = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(7), 1);
            String vDeliveryTakeaway = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(8), 1);
            String vSaleDate = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(9), 1);
            String vCustomerOrderNumber = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(10), 1);




            // 08302023
            String vOnlineType = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(11), 1);


            // 10112023
            String vOrderfrom = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(12), 1);
            String vSalescodethirdparty = GlobalMemberValues.getDBTextAfterChecked(realTimeWebOrdersCursor.getString(13), 1);
            String salesCode_str = vSalesCode;
            if (!GlobalMemberValues.isStrEmpty(vOrderfrom) && !GlobalMemberValues.isStrEmpty(vSalescodethirdparty)) {
                salesCode_str = vSalescodethirdparty;
            }

            if (GlobalMemberValues.isStrEmpty(vOnlineType)) {
                vOnlineType = "W";
            }
            String webOrdersOnlineType_str = "Web Order";
            switch (vOnlineType) {
                case "W" : {
                    webOrdersOnlineType_str = "Web Order";
                    break;
                }
                case "M" : {
                    webOrdersOnlineType_str = "Mobile Order";
                    break;
                }
                case "K" : {
                    webOrdersOnlineType_str = "Kiosk Order";
                    break;
                }
                case "T" : {
                    webOrdersOnlineType_str = "OTTER";

                    // 10112023
                    if (!GlobalMemberValues.isStrEmpty(vOrderfrom)) {
                        webOrdersOnlineType_str = vOrderfrom.toUpperCase();
                    }

                    break;
                }
            }




            if (GlobalMemberValues.isStrEmpty(vDeliveryTakeaway)) {
                vDeliveryTakeaway = "T";
            }

            String strDeliveryTakeaway = "Pick Up";
            String strDeliveryTakeawayColor = "#f85c8e";
            String strDeliveryDate = "";
            if (vDeliveryTakeaway.equals("D")) {
                strDeliveryTakeaway = "Delivery";
                strDeliveryTakeawayColor = "#23b3c2";
                strDeliveryDate = " (" + vDeliverydate + ")";
            } else {
                strDeliveryTakeaway = "Pick Up";
                strDeliveryTakeawayColor = "#f85c8e";
                strDeliveryDate = "";
            }


            // LinearLayout 객체 생성
            LinearLayout newLn = new LinearLayout(context);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.VERTICAL);
            //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
            newLn.setBackgroundResource(R.drawable.button_selector_realtimeweborders_ln1);
            newLn.setPadding(8, 5, 8, 5);

            /** 주문요약 ********************************************************************************************************/
            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams statusLnLayoutParams1
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            statusLnLayoutParams1.setMargins(0, 0, 0, 0);

            // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
            LinearLayout.LayoutParams statusTvLayoutParams2
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            statusTvLayoutParams2.setMargins(0, 0, 0, 0);

            LinearLayout listLn1 = new LinearLayout(context);
            listLn1.setLayoutParams(statusLnLayoutParams1);
            listLn1.setOrientation(LinearLayout.VERTICAL);
            //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

            // Sales Code --------------------------------------------------------------------------------------
            LinearLayout tableinfoLn = new LinearLayout(MainActivity.mContext);
            tableinfoLn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            tableinfoLn.setOrientation(LinearLayout.HORIZONTAL);



            // 08302023
            TextView tableinfoTv0 = new TextView(MainActivity.mContext);
            tableinfoTv0.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tableinfoTv0.setPadding(10, 5, 10, 5);
            tableinfoTv0.setText(webOrdersOnlineType_str);
            tableinfoTv0.setTextColor(Color.parseColor("#f8df00"));
            tableinfoTv0.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE + 6);
            tableinfoTv0.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
            tableinfoLn.addView(tableinfoTv0);

            TextView tableinfoTv2 = new TextView(MainActivity.mContext);
            tableinfoTv2.setGravity(Gravity.CENTER);
            tableinfoTv2.setPadding(10, 5, 10, 5);
            tableinfoTv2.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            tableinfoTv2.setText(" X ");
            //tableinfoTv2.setBackgroundColor(Color.parseColor("#72817a"));
            tableinfoTv2.setBackgroundResource(R.drawable.roundlayout_background_realtimeweborders_ln1);
            tableinfoTv2.setTextColor(Color.parseColor("#ffffff"));
            tableinfoTv2.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE + 4);
            tableinfoTv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
            tableinfoLn.addView(tableinfoTv2);

            tableinfoTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readOrder(vIdx);
                }
            });


            listLn1.addView(tableinfoLn);
            // -------------------------------------------------------------------------------------------------


            // Customer Name
            TextView tableinfoTv1 = new TextView(context);
            tableinfoTv1.setLayoutParams(statusTvLayoutParams2);
            tableinfoTv1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tableinfoTv1.setPadding(10, 5, 10, 5);

            // 10112023
            tableinfoTv1.setText(salesCode_str);

            tableinfoTv1.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            tableinfoTv1.setTextColor(Color.parseColor("#ffffff"));
            tableinfoTv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(tableinfoTv1);



            // Customer Name
            TextView listTextVIewC = new TextView(context);
            listTextVIewC.setLayoutParams(statusTvLayoutParams2);
            listTextVIewC.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIewC.setPadding(10, 5, 10, 5);
            listTextVIewC.setText("Order # : " + vCustomerOrderNumber);
            listTextVIewC.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIewC.setTextColor(Color.parseColor("#ffffff"));
            listTextVIewC.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIewC);

            // 05192023
            if (GlobalMemberValues.isStrEmpty(GlobalMemberValues.getReplaceText(vCustomerName, " ", ""))) {
                vCustomerName = "GUEST";
            }

            // Customer Name
            TextView listTextVIew2 = new TextView(context);
            listTextVIew2.setLayoutParams(statusTvLayoutParams2);
            listTextVIew2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew2.setPadding(10, 5, 10, 5);
            listTextVIew2.setText("Customer : " + vCustomerName);
            listTextVIew2.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew2.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew2);

            // Customer Phone
            TextView listTextVIew3 = new TextView(context);
            listTextVIew3.setLayoutParams(statusTvLayoutParams2);
            listTextVIew3.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew3.setPadding(10, 5, 10, 5);
            listTextVIew3.setText("Phone : " + vCustomerPhone);
            listTextVIew3.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew3.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew3);

            // Delivery / Takeaway
//            TextView listTextVIew4 = new TextView(context);
//            listTextVIew4.setLayoutParams(statusTvLayoutParams2);
//            listTextVIew4.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//            listTextVIew4.setPadding(10, 5, 10, 5);
//            listTextVIew4.setText(strDeliveryTakeaway + strDeliveryDate);
//            listTextVIew4.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
//            listTextVIew4.setTextColor(Color.parseColor(strDeliveryTakeawayColor));
//            listTextVIew4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
//            listLn1.addView(listTextVIew4);

            // Sale Date
            TextView listTextVIew5 = new TextView(context);
            listTextVIew5.setLayoutParams(statusTvLayoutParams2);
            listTextVIew5.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew5.setPadding(10, 5, 10, 5);
            listTextVIew5.setText(vSaleDate);
            listTextVIew5.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew5.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew5);

            newLn.addView(listLn1);
            /********************************************************************************************************************/

            final String finalVDeliveryTakeaway = vDeliveryTakeaway;

            // 10112023
            String finalVOnlineType = vOnlineType;

            newLn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleHistory_web.mDeliveryTakeawayValueFromOutActivity = finalVDeliveryTakeaway;

                    // 10112023
                    mOnlinetype = finalVOnlineType;
                    mOrderfrom = vOrderfrom;
                    mSalescodethirdparty = vSalescodethirdparty;

                    openIntentSaleHistoryWeb(vSalesCode);
                }
            });

            realTimeWebOrdersListLinearLayout.addView(newLn);
        }
        /** Push 안 읽은거 리스트 끝 ********************************************************************************************************/


        /** 온라인 오더 리스스 시작 *********************************************************************************************************/
        /**

        // 프로그래스바 띄우기 -------------------------------------------------
        proDial = new ProgressDialog(mActivity);
        proDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDial.setTitle("Navyzebra QSR Cloud");
        proDial.setMessage("Online orders' data is loading...");
        proDial.setProgress(0);
        proDial.setMax(100);
        proDial.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        proDial.show();
        // -------------------------------------------------------------------

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                APIDownLoad_online_orders_realtime apiDownloadOnlineOrdersRealtime = new APIDownLoad_online_orders_realtime();
                apiDownloadOnlineOrdersRealtime.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiDownloadOnlineOrdersRealtime.mFlag) {
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTempFlag = 0;
                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handlerWebOrder.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
         **/

        /** 온라인 오더 리스스 끝 ***********************************************************************************************************/
    }

    public void readOrder(String paramIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramIdx)) {
            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = " update salon_sales_web_push_realtime set viewyn = 'Y' where idx = '" + paramIdx + "' ";
            strInsertQueryVec.addElement(strQuery);
            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {
                setWebOrdersInPush();
            }
        }
    }


    private Handler handlerWebOrder = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempFlag == 0) {
                // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
                //setWebOrdersInCloud();
                // -------------------------------------------------------------------------------------
                // 2. 로딩 인텐트 또는 프로그래스바를 사라지게 함 -------------------------------------------------------
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료전... \n");
                proDial.dismiss();
                GlobalMemberValues.logWrite(TAG, "프로그래스바 종료후... \n");
                // -------------------------------------------------------------------------------------
            }
        }
    };

    public void setWebOrdersInCloud() {
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

        if (RealTimeWebOrders.mCollectionOrdersArrList.size() > 0) {
            // LinearLayout 객체 생성
            LinearLayout newLn = new LinearLayout(context);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.VERTICAL);
            //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
            newLn.setBackgroundResource(R.drawable.roundlayout_background_realtimeweborders_ln1);
            newLn.setPadding(0, 0, 0, 0);

            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams statusLnLayoutParams1
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            statusLnLayoutParams1.setMargins(0, 0, 0, 0);

            // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
            LinearLayout.LayoutParams statusTvLayoutParams2
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            statusTvLayoutParams2.setMargins(0, 0, 0, 0);

            LinearLayout listLn1 = new LinearLayout(context);
            listLn1.setLayoutParams(statusLnLayoutParams1);
            listLn1.setOrientation(LinearLayout.VERTICAL);
            //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

            // Title
            TextView listTextVIew0 = new TextView(context);
            listTextVIew0.setLayoutParams(statusTvLayoutParams2);
            listTextVIew0.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew0.setPadding(10, 5, 10, 5);
            listTextVIew0.setText(" Cloud Order List(s)");
            listTextVIew0.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew0.setTextColor(Color.parseColor("#000000"));
            listTextVIew0.setBackgroundResource(R.drawable.roundlayout_background_realtimeweborders_titlebg);;
            listTextVIew0.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew0);

            newLn.addView(listLn1);
            realTimeWebOrdersListLinearLayout.addView(newLn);
        }

        /** 클라우드에서 가져온 웹오더 리스트 시작 *************************************************************************************/
        for (int weborderi = 0; weborderi < RealTimeWebOrders.mCollectionOrdersArrList.size(); weborderi++) {
            TemporaryWebOrders temporaryWebOrders = RealTimeWebOrders.mCollectionOrdersArrList.get(weborderi);

            // salon_sales_web 테이블 정보 ----------------------------------------------------------------------------------
            String vIdx = temporaryWebOrders.idx;
            final String vSalesCode = temporaryWebOrders.salesCode;

            String vCustomerName = temporaryWebOrders.customerName;
            String vCustomerPhone = temporaryWebOrders.customerPhone;

            String vDeliverydate = temporaryWebOrders.deliverydate;

            String vDeliveryTakeaway = temporaryWebOrders.deliverytakeaway;
            String vDeliveryStatus = temporaryWebOrders.DeliveryStatus;
            String vTakeawayStatus = temporaryWebOrders.TakeawayStatus;

            String vSaleDate = temporaryWebOrders.SaleDate;
            // ------------------------------------------------------------------------------------------------------------

            if (GlobalMemberValues.isStrEmpty(vDeliveryTakeaway)) {
                vDeliveryTakeaway = "T";
            }

            String strDeliveryTakeaway = "Pick Up";
            String strDeliveryTakeawayColor = "#f85c8e";
            String strDeliveryDate = "";
            if (vDeliveryTakeaway.equals("D")) {
                strDeliveryTakeaway = "Delivery";
                strDeliveryTakeawayColor = "#23b3c2";
                strDeliveryDate = " (" + vDeliverydate + ")";
            } else {
                strDeliveryTakeaway = "Pick Up";
                strDeliveryTakeawayColor = "#f85c8e";
                strDeliveryDate = "";
            }

            // LinearLayout 객체 생성
            LinearLayout newLn = new LinearLayout(context);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.VERTICAL);
            //newLn.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));
            newLn.setBackgroundResource(R.drawable.button_selector_realtimeweborders_ln1);
            newLn.setPadding(8, 5, 8, 5);
            newLn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            /** 주문요약 ********************************************************************************************************/
            // LinearLayout 파라미터 설정
            LinearLayout.LayoutParams statusLnLayoutParams1
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            statusLnLayoutParams1.setMargins(0, 0, 0, 0);

            // TextView 파라미터 설정 (한줄에 2개 이상 TextView 가 있을 경우)
            LinearLayout.LayoutParams statusTvLayoutParams2
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            statusTvLayoutParams2.setMargins(0, 0, 0, 0);

            LinearLayout listLn1 = new LinearLayout(context);
            listLn1.setLayoutParams(statusLnLayoutParams1);
            listLn1.setOrientation(LinearLayout.VERTICAL);
            //customerValueLn1.setBackgroundColor(Color.parseColor(salonSalesLnBackGroundColor));

            // Sales Code
            TextView listTextVIew1 = new TextView(context);
            listTextVIew1.setLayoutParams(statusTvLayoutParams2);
            listTextVIew1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew1.setPadding(10, 5, 10, 5);
            listTextVIew1.setText("Sale Code : " + vSalesCode);
            listTextVIew1.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew1.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew1);

            // Customer Name
            TextView listTextVIew2 = new TextView(context);
            listTextVIew2.setLayoutParams(statusTvLayoutParams2);
            listTextVIew2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew2.setPadding(10, 5, 10, 5);
            listTextVIew2.setText("Customer : " + vCustomerName);
            listTextVIew2.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew2.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew2);

            // Customer Phone
            TextView listTextVIew3 = new TextView(context);
            listTextVIew3.setLayoutParams(statusTvLayoutParams2);
            listTextVIew3.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew3.setPadding(10, 5, 10, 5);
            listTextVIew3.setText("Phone : " + vCustomerPhone);
            listTextVIew3.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew3.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew3);

            // Delivery / Takeaway
            TextView listTextVIew4 = new TextView(context);
            listTextVIew4.setLayoutParams(statusTvLayoutParams2);
            listTextVIew4.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew4.setPadding(10, 5, 10, 5);
            listTextVIew4.setText(strDeliveryTakeaway + strDeliveryDate);
            listTextVIew4.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew4.setTextColor(Color.parseColor(strDeliveryTakeawayColor));
            listTextVIew4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew4);

            // Sale Date
            TextView listTextVIew5 = new TextView(context);
            listTextVIew5.setLayoutParams(statusTvLayoutParams2);
            listTextVIew5.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            listTextVIew5.setPadding(10, 5, 10, 5);
            listTextVIew5.setText(vSaleDate);
            listTextVIew5.setTextSize(GlobalMemberValues.globalAddFontSize() +GlobalMemberValues.BASICSALEHISTORYLISTTTEXTSIZE);
            listTextVIew5.setTextColor(Color.parseColor("#ffffff"));
            listTextVIew5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
            listLn1.addView(listTextVIew5);

            newLn.addView(listLn1);
            /********************************************************************************************************************/

            final String finalVDeliveryTakeaway = vDeliveryTakeaway;
            newLn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleHistory_web.mDeliveryTakeawayValueFromOutActivity = finalVDeliveryTakeaway;
                    openIntentSaleHistoryWeb(vSalesCode);
                }
            });

            realTimeWebOrdersListLinearLayout.addView(newLn);
        }
        /** 클라우드에서 가져온 웹오더 리스트 끝 ***************************************************************************************/
    }

    public void openIntentSaleHistoryWeb(String paramSalesCode) {
        GlobalMemberValues.logWrite("realtimeValue", "paramSalesCode : " + paramSalesCode + "\n");
        Intent webOrdersIntent = new Intent(context, SaleHistory_web.class);
        webOrdersIntent.putExtra("pushSalesCode", paramSalesCode);


        // 10112023
        webOrdersIntent.putExtra("pushOrderType", mOnlinetype);
        webOrdersIntent.putExtra("pushOrderfrom", mOrderfrom);
        webOrdersIntent.putExtra("pushSalescodethirdparty", mSalescodethirdparty);
        mOnlinetype = "";
        mOrderfrom = "";
        mSalescodethirdparty = "";


        startActivity(webOrdersIntent);
        if (GlobalMemberValues.isUseFadeInOut()) {
            overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }

        finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
        }
    }

    View.OnClickListener realTimeWebOrdersBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeButton : {
                    closeActivity();
                    break;
                }

                case R.id.emptyLinearLayout : {
                    closeActivity();
                    break;
                }
            }
        }
    };

    private void closeActivity() {
        finish();
        // 초기화
        setInit();
        // 키패드(키보드) 감추기
        //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_left, R.anim.act_out_left);
        }
    }

    private void setInit() {
        // 오더 갯수 확인 및 AppTopBar Push 버튼 깜빡임 Animation.
        int cnt = GlobalMemberValues.get_web_push_realtime_n_cnt();
        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON != null){
            if (cnt > 0){
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_APPTOP_PUSH_BUTTON.clearAnimation();
            }
        }
        //
    }

}
