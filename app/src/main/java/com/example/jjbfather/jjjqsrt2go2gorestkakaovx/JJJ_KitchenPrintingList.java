package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class JJJ_KitchenPrintingList extends Activity {
    static Activity mActivity;
    static Context mContext;
    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    Intent mIntent;

    // salesCode 값
    String mSalesCode = "";
    static ArrayList<String> mSelectedItemIdxArrayList;

    static LinearLayout listLn;

    TextView titleTv1, titleTv2, titleTv3;
    TextView titleTv4, titleTv5;

    Button saleHistoryCustomerSearchSHButton, saleHistoryCloseBtn;
    Button reloadbtn, deletebtn, reprintbtn;

    TextView mSelectedDateTextView;

    static TextView saleHistoryStartDateEditText;
    static EditText saleHistoryCustomerSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_kitchenprinting_list);
        this.setFinishOnTouchOutside(false);

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 9;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 100;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.settingsLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mContext = this;
        mActivity = this;

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 salesCode 값
            //mSalesCode = mIntent.getStringExtra("selectedSalesCode");
            //GlobalMemberValues.logWrite("salesHistoryReturn", "넘겨받은 selectedSalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
        } else {
            finish();
            if (GlobalMemberValues.isUseFadeInOut()) {
                mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
            }
        }

        setContents();
    }

    public void setContents() {

        titleTv1 = (TextView)findViewById(R.id.titleTv1);
        titleTv1.setTextSize(GlobalMemberValues.globalAddFontSize() + titleTv1.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv2 = (TextView)findViewById(R.id.titleTv2);
        titleTv2.setTextSize(GlobalMemberValues.globalAddFontSize() + titleTv2.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv3 = (TextView)findViewById(R.id.titleTv3);
        titleTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + titleTv3.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv4 = (TextView)findViewById(R.id.titleTv4);
        titleTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + titleTv4.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        titleTv5 = (TextView)findViewById(R.id.titleTv5);
        titleTv5.setTextSize(GlobalMemberValues.globalAddFontSize() + titleTv5.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        saleHistoryCloseBtn = (Button)findViewById(R.id.saleHistoryCloseBtn);
        saleHistoryCloseBtn.setOnClickListener(saleHistoryBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryCloseBtn.setText("");
            saleHistoryCloseBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            saleHistoryCloseBtn.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryCloseBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        saleHistoryStartDateEditText = (TextView)findViewById(R.id.saleHistoryStartDateEditText);

        saleHistoryCustomerSearchEditText = (EditText)findViewById(R.id.saleHistoryCustomerSearchEditText);
        saleHistoryCustomerSearchEditText.setOnTouchListener(saleHistoryCustomerSearchEditTextTouchListener);
        saleHistoryCustomerSearchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);      // 키패드(키보드) 엔터모양 변경
        saleHistoryCustomerSearchEditText.setInputType(InputType.TYPE_CLASS_TEXT);          // 키패드(키보드) 타입을 텍스트로
        final String tempSchDate = saleHistoryStartDateEditText.getText().toString();
        final String tempSchTxt = saleHistoryCustomerSearchEditText.getText().toString();

        if (GlobalMemberValues.isStrEmpty(tempSchDate)) {
            saleHistoryStartDateEditText.setText(GlobalMemberValues.STR_NOW_DATE);
        }

        saleHistoryCustomerSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 엔터키 터치시 실행
                        setSearchSalesHistory(tempSchDate, tempSchTxt);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        saleHistoryStartDateEditText.setOnClickListener(saleHistoryBtnClickListener);

        saleHistoryCustomerSearchSHButton = (Button)findViewById(R.id.saleHistoryCustomerSearchSHButton);
        saleHistoryCustomerSearchSHButton.setOnClickListener(saleHistoryBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            saleHistoryCustomerSearchSHButton.setText("");
            saleHistoryCustomerSearchSHButton.setBackgroundResource(R.drawable.ab_imagebutton_salehistory_search);
        } else {
            saleHistoryCustomerSearchSHButton.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    saleHistoryCustomerSearchSHButton.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        reloadbtn = (Button)findViewById(R.id.reloadbtn);
        reloadbtn.setOnClickListener(saleHistoryBtnClickListener);
        reloadbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + reloadbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        deletebtn = (Button)findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(saleHistoryBtnClickListener);
        deletebtn.setTextSize(GlobalMemberValues.globalAddFontSize() + deletebtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        reprintbtn = (Button)findViewById(R.id.reprintbtn);
        reprintbtn.setOnClickListener(saleHistoryBtnClickListener);
        reprintbtn.setTextSize(GlobalMemberValues.globalAddFontSize() + reprintbtn.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        listLn = (LinearLayout)findViewById(R.id.listLn);


        mSelectedItemIdxArrayList = new ArrayList<String>();

        setSearchSalesHistory("", "");
    }

    public static void setSearchSalesHistory(String paramDate, String paramSearchTxt) {
        listLn.removeAllViews();

        // Sub LinearLayout 파라미터 설정
        LinearLayout.LayoutParams subNewLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewLnLayoutParams.setMargins(0, 0, 0, 0);
        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);

        if (GlobalMemberValues.isStrEmpty(paramDate)) {
            paramDate = GlobalMemberValues.STR_NOW_DATE;
        }

        // salon_sales_kitchen_json 데이터 출력 ---------------------------------------------------------------------------------------------------
        String tempIdx = "";
        String tempJsonData = "";
        String tempSalesCode = "";
        String tempIsCloudUpload = "";
        String tempWDate = "";

        String tempOrderNo = "";
        String tempPagerNo = "";

        String tempStatusStr = "";

        Cursor dataCursor;
        String detailQuery = "";
        detailQuery = " select idx, jsonstr, salesCode, isCloudUpload, wdate " +
                " from salon_sales_kitchen_json where " +
                " ( strftime('%m-%d-%Y', wdate) between '" + paramDate + "'" +
                " and '" + DateMethodClass.getCustomEditDate(paramDate, 0, 0, 0) + "' ) " +
                " and jsonstr like '%" + paramSearchTxt + "%' " +
                " and not(salesCode = '' or salesCode is null) " +
                " order by idx desc ";
        dataCursor = MainActivity.mDbInit.dbExecuteRead(detailQuery);
        int listCount = 1;
        while (dataCursor.moveToNext()) {
            tempIdx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
            tempJsonData = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
            tempSalesCode = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
            tempIsCloudUpload = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
            tempWDate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);

            if (GlobalMemberValues.isStrEmpty(tempIsCloudUpload)) {
                tempIsCloudUpload = "0";
            }
            tempStatusStr = "<font color=\"#f00c00\">Not Uploaded</font>";
            if (tempIsCloudUpload == "1" || tempIsCloudUpload.equals("1")) {
                tempStatusStr = "<font color=\"#2530f5\">Uploaded</font>";
            }

            if (!GlobalMemberValues.isStrEmpty(tempJsonData)) {
                JSONObject tempJson = null;
                try {
                    tempJson = new JSONObject(tempJsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tempOrderNo = GlobalMemberValues.getDataInJsonData(tempJson, "customerordernumber");
                tempPagerNo = GlobalMemberValues.getDataInJsonData(tempJson, "customerpagernumber");
            } else {
                tempOrderNo = "";
                tempPagerNo = "";
            }

            LinearLayout subNewLn = new LinearLayout(mContext);
            subNewLn.setLayoutParams(subNewLnLayoutParams);
            subNewLn.setOrientation(LinearLayout.HORIZONTAL);
            subNewLn.setBackgroundColor(Color.parseColor("#ffffff"));

            // 체크박스
            CheckBox subNewCb1 = new CheckBox(mContext);
            subNewCb1.setLayoutParams(subNewTvLayoutParams);
            subNewCb1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f));
            subNewCb1.setTag(tempIdx);
            subNewCb1.setPadding(5, 5, 5, 5);
            subNewCb1.setHighlightColor(Color.parseColor("#000000"));
            subNewCb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String chkIdx = buttonView.getTag().toString();
                    if (!GlobalMemberValues.isStrEmpty(chkIdx)) {
                        if (isChecked) {
                            mSelectedItemIdxArrayList.add(chkIdx);
                        } else {
                            mSelectedItemIdxArrayList.remove(chkIdx);
                        }
                    }
                }
            });
            subNewLn.addView(subNewCb1);

            // Receipt No.
            TextView subNewTv7 = new TextView(mContext);
            subNewTv7.setLayoutParams(subNewTvLayoutParams);
            subNewTv7.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.5f));
            subNewTv7.setGravity(Gravity.CENTER);
            subNewTv7.setPadding(5, 0, 5, 0);
            subNewTv7.setText(tempSalesCode);
            subNewTv7.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
            subNewTv7.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
            subNewLn.addView(subNewTv7);

            // Order No.
            TextView subNewTv3 = new TextView(mContext);
            subNewTv3.setLayoutParams(subNewTvLayoutParams);
            subNewTv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            subNewTv3.setGravity(Gravity.CENTER);
            subNewTv3.setPadding(5, 0, 5, 0);
            subNewTv3.setText(tempOrderNo);
            subNewTv3.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
            subNewTv3.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
            subNewLn.addView(subNewTv3);

            // Pager No.
            TextView subNewTv4 = new TextView(mContext);
            subNewTv4.setLayoutParams(subNewTvLayoutParams);
            subNewTv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
            subNewTv4.setGravity(Gravity.CENTER);
            subNewTv4.setPadding(0, 0, 5, 0);
            subNewTv4.setText(tempPagerNo);
            subNewTv4.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
            subNewTv4.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
            subNewLn.addView(subNewTv4);

            // Status
            TextView subNewTv5 = new TextView(mContext);
            subNewTv5.setLayoutParams(subNewTvLayoutParams);
            subNewTv5.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
            subNewTv5.setGravity(Gravity.CENTER);
            subNewTv5.setPadding(0, 0, 5, 0);
            subNewTv5.setText(Html.fromHtml(tempStatusStr));
            subNewTv5.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
            subNewTv5.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
            subNewLn.addView(subNewTv5);

            // Wdate
            TextView subNewTv6 = new TextView(mContext);
            subNewTv6.setLayoutParams(subNewTvLayoutParams);
            subNewTv6.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            subNewTv6.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            subNewTv6.setPadding(0, 0, 5, 0);
            subNewTv6.setText(tempWDate);
            subNewTv6.setTextSize(GlobalMemberValues.globalAddFontSize() + GlobalMemberValues.BASICSALEHISTORYRETURNITEMLISTTTEXTSIZE);
            subNewTv6.setTextColor(Color.parseColor(GlobalMemberValues.SALESHISTORYLIST_TEXTCOLOR));
            subNewLn.addView(subNewTv6);

            listLn.addView(subNewLn);

            listCount++;
        }
        // ----------------------------------------------------------------------------------------------------------------------------------
    }

    View.OnTouchListener saleHistoryCustomerSearchEditTextTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    View.OnClickListener saleHistoryBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reloadbtn : {
                    searchKitchenPrintingData();

                    break;
                }
                case R.id.deletebtn : {
                    if (!mActivity.isFinishing()) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Database Backup")
                                .setMessage("Are you sure you want to delete the selected data?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteSelectedData();
                                    }
                                }).show();
                    }

                    break;
                }
                case R.id.reprintbtn : {
                    if (!mActivity.isFinishing()) {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Database Backup")
                                .setMessage("Are you sure you want to re-upload and re-print the selected data?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        reprintSelectedData();
                                    }
                                }).show();
                    }


                    break;
                }
                case R.id.saleHistoryCloseBtn : {
                    finish();
                    // 초기화
                    setInit();
                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), paymentReviewEditTextEmailAddr1);
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
                    }
                    break;
                }
                case R.id.saleHistoryCustomerSearchSHButton : {
                    searchKitchenPrintingData();

                    break;
                }
                case R.id.saleHistoryStartDateEditText : {
                    String tempClockInOutSearchDay = saleHistoryStartDateEditText.getText().toString();
                    if (GlobalMemberValues.isStrEmpty(tempClockInOutSearchDay)) {
                        tempClockInOutSearchDay = GlobalMemberValues.STR_NOW_DATE;
                    }
                    mSelectedDateTextView = saleHistoryStartDateEditText;
                    openDatePickerDialog(tempClockInOutSearchDay);
                    break;
                }
            }
        }
    };

    public static void deleteSelectedData() {
        if (mSelectedItemIdxArrayList.size() > 0) {
            for (int i = 0; i < mSelectedItemIdxArrayList.size(); i++) {
                setDeleteKitchenPrinting(mSelectedItemIdxArrayList.get(i));
            }
            searchKitchenPrintingData();
        } else {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Select the data to delete", "Close");
        }
    }

    public static void reprintSelectedData() {
        if (mSelectedItemIdxArrayList.size() > 0) {
            for (int i = 0; i < mSelectedItemIdxArrayList.size(); i++) {
                setKitchenReprint(mSelectedItemIdxArrayList.get(i));
            }
            searchKitchenPrintingData();
        } else {
            GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Select the data to re-print", "Close");
        }
    }

    public static void searchKitchenPrintingData() {
        String tempSchDate = saleHistoryStartDateEditText.getText().toString();
        String tempSchTxt = saleHistoryCustomerSearchEditText.getText().toString();
        setSearchSalesHistory(tempSchDate, tempSchTxt);
    }

    public void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(mContext, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            mSelectedDateTextView.setText(tempMonth + "-" + tempDay + "-" + tempYear);
        }
    };

    public static void setDeleteKitchenPrinting(String paramIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramIdx)) {
            String strQuery =  "";
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            strQuery = " delete from salon_sales_kitchen_json " +
                    " where idx = '" + paramIdx + "' ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("setPrintedKitchenPrintinglog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {

            }
        }
    }

    public static void setKitchenReprint(String paramIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramIdx)) {
            String strQuery =  "";
            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
            String returnResult = "";
            Vector<String> strInsertQueryVec = new Vector<String>();
            strQuery = " update salon_sales_kitchen_json set " +
                    " isCloudUpload = 0 " +
                    " where idx = '" + paramIdx + "' ";
            strInsertQueryVec.addElement(strQuery);

            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("setPrintedKitchenPrintinglog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {
            }
        }
    }


    // 초기화 메소드
    private void setInit() {
        mSalesCode = "";
        mSelectedItemIdxArrayList = null;
    }

}
