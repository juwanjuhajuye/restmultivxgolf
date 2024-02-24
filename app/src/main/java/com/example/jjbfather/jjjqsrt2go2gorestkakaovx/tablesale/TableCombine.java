package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DatabaseInit;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MssqlDatabase;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class TableCombine extends Activity {
    public static Activity mActivity;
    public static Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn;

    private TextView tablecombinetitletxt;

    private Button closeBtn;

    private LinearLayout combineLn;

    Intent mIntent;

    static String mSalesCode = "";

    public String mSelectedSalonSalesCardIdx = "";
    public String mSelectedPayType = "CARD";
    public LinearLayout mSelectedLn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_table_combine);
        this.setFinishOnTouchOutside(false);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 90) * 30;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.billPaidListLinearLayout);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            mSalesCode = mIntent.getStringExtra("salesCode");
            GlobalMemberValues.logWrite("billPaidList", "넘겨받은 SalesCode : " + mSalesCode + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            finish();
        }

        GlobalMemberValues.logWrite("billPaidListLog", "billPaidList 실행 - 3" + "\n");

        setContents();
    }

    public void setContents() {
        mActivity = this;
        mContext = this;

        dbInit = new DatabaseInit(this);

        GlobalMemberValues.mOnVoidForBillPay = false;

        tablecombinetitletxt = (TextView) parentLn
                .findViewWithTag("tablecombinetitletxtTag");
        tablecombinetitletxt.setTextSize(
                tablecombinetitletxt.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );


        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn
                .findViewWithTag("combineCloseBtnTag");
        closeBtn.setOnClickListener(billPaidListBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_tipadjustment_close);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        combineLn = (LinearLayout)findViewById(R.id.combineLn);



        /** 결제시 사용한 카드 리스트 ******************************************************************************/
        // LinearLayout 파라미터 설정
        // 09062023
        // 09082023
        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        newLnLayoutParams.setMargins(0, 0, 0, 10);

        // Sub TextView 파라미터 설정
        LinearLayout.LayoutParams subNewTvLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        subNewTvLayoutParams.setMargins(0, 0, 0, 0);

        String tempTableIdx = "";
        String tempTableName = "";
        for (int i = 0; i < TableSaleMain.mSelectedTablesArrList.size(); i++) {
            GlobalMemberValues.logWrite("seltblLogjjj", "table idx : " + tempTableIdx + "\n");
            tempTableIdx = TableSaleMain.mSelectedTablesArrList.get(i);
            if (!GlobalMemberValues.isStrEmpty(tempTableIdx)) {
                tempTableIdx = GlobalMemberValues.getReplaceText(tempTableIdx, "T", "");
            }
            tempTableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                    " select tablename from salon_store_restaurant_table where idx = '" + tempTableIdx + "' "
            );

            if (!GlobalMemberValues.isStrEmpty(tempTableName)) {
                // LinearLayout 객체 생성
                LinearLayout newLn = new LinearLayout(this);
                newLn.setLayoutParams(newLnLayoutParams);
                newLn.setOrientation(LinearLayout.VERTICAL);
                newLn.setBackgroundResource(R.drawable.button_selector_category2);
                newLn.setTag(tempTableIdx);
                newLn.setPadding(15, 15, 15, 15);


                String finalTempTableIdx = tempTableIdx;
                newLn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // combine 관련 메소드를 여기에...
                        setTableCombine(finalTempTableIdx);
                    }
                });

                TextView splitpaynumTextView = new TextView(this);
                splitpaynumTextView.setLayoutParams(subNewTvLayoutParams);
                splitpaynumTextView.setGravity(Gravity.CENTER);
                splitpaynumTextView.setPadding(0, 0, 0, 0);
                splitpaynumTextView.setText(tempTableName);

                // 09062023
                splitpaynumTextView.setTextSize(38);
                splitpaynumTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                splitpaynumTextView.setTextColor(Color.parseColor("#000000"));
                newLn.addView(splitpaynumTextView);

                combineLn.addView(newLn);
            }
        }

        /***********************************************************************************************************/
    }

    public void setTableCombine(String paramChoiceTableIdx) {

        if (!GlobalMemberValues.isStrEmpty(paramChoiceTableIdx)) {
            int totalPeopleCnt = 0;
            String strQuery = "";

            if (!paramChoiceTableIdx.contains("T")) {
                paramChoiceTableIdx = "T" + paramChoiceTableIdx + "T";
            }

            String tempHoldCode = TableSaleMain.getHoldCodeByTableidx(paramChoiceTableIdx, TableSaleMain.mSubTableNum);

            // salon_store_restaurant_table_peoplecnt 수 구하기
            strQuery = " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where tableIdx like '%" + paramChoiceTableIdx + "%' ";
            totalPeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

            int tempPeopleCnt = 0;
            String tempTblIdx = "";
            String pastHoldCodeStr = tempHoldCode;
            Vector<String> updateVector = new Vector<String>();
            int tempcnt = 0;
            for (String tempTableIdx : TableSaleMain.mSelectedTablesArrList) {
                if (!GlobalMemberValues.isStrEmpty(tempTableIdx) && !paramChoiceTableIdx.equals(tempTableIdx)) {
                    tempTblIdx = tempTableIdx;
                    if (!tempTblIdx.contains("T")) {
                        tempTblIdx = "T" + tempTblIdx + "T";
                    }
                    strQuery = " select sum(peoplecnt) from salon_store_restaurant_table_peoplecnt where tableIdx like '%" + tempTblIdx + "%' ";
                    tempPeopleCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                    pastHoldCodeStr += "-J-" + TableSaleMain.getHoldCodeByTableidx(tempTblIdx, TableSaleMain.mSubTableNum);

                    strQuery = " update temp_salecart " +
                            " set holdcode = '" + tempHoldCode + "', " +
                            " tableidx = '" + paramChoiceTableIdx + "', " +
                            " mergednum = '0', isCloudUpload = 0 " +
                            " where tableidx like '%" + tempTblIdx + "%' ";
                    updateVector.addElement(strQuery);


                    strQuery = " delete from salon_store_restaurant_table_peoplecnt " +
                            " where tableIdx like '%" + tempTblIdx + "%' ";
                    updateVector.addElement(strQuery);


                    strQuery = "delete from salon_store_restaurant_table_merge where tableidx like '%" + tempTblIdx + "%' ";
                    updateVector.addElement(strQuery);


                    totalPeopleCnt += tempPeopleCnt;

                    tempcnt++;
                }
            }

            strQuery = " update salon_store_restaurant_table_peoplecnt " +
                    " set peoplecnt = '" + totalPeopleCnt + "' " +
                    " where tableIdx like '%" + paramChoiceTableIdx + "%' ";
            updateVector.addElement(strQuery);


            // 04252023
            // 테이블 이동 관련정보를 새로고침을 위한 테이블에 저장
            strQuery = " insert into salon_table_statuschange (holdcode, stcode, tableidx, delyn, ctype) values ( " +
                    " '" + tempHoldCode + "', " +
                    " '" + GlobalMemberValues.STATION_CODE + "', " +
                    " '" + paramChoiceTableIdx + "', " +
                    " '" + "N" + "', " +
                    " '" + "merge" + "' " +
                    " ) ";
            updateVector.addElement(strQuery);


            if (updateVector.size() > 0) {
                for (String tempQuery : updateVector) {
                    GlobalMemberValues.logWrite("mergetablelog", "query : " + tempQuery + "\n");
                }

                // 05262023 ---------------------------------------------------------------------------------------
                JSONObject tempJson = new JSONObject();
                try {
                    String combineTableName = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select tablename from salon_store_restaurant_table where idx = '" + GlobalMemberValues.getReplaceText(paramChoiceTableIdx, "T", "") + "' "
                    );

                    tempJson.put("currentholdcode", tempHoldCode);
                    tempJson.put("pastholdcode", pastHoldCodeStr);
                    tempJson.put("mergetablename", combineTableName);
                    // tempJson.put("mergetablename", "Combined");


                    // 11102023
                    if (GlobalMemberValues.isPossibleSavingKitchenPrintingDataJson(tempJson.toString())) {
                        strQuery = " insert into salon_sales_kitchenprintingdata_json " +
                                " (salesCode, scode, sidx, stcode, jsonstr, printedyn, presalescode) values ( " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(tempHoldCode,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.SALON_CODE,0) + "', " +
                                "  " + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STORE_INDEX,0) + ", " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.STATION_CODE,0) + "', " +
                                " '" + GlobalMemberValues.getDBTextAfterChecked(tempJson.toString(), 0) + "', " +
                                " 'Y', " +
                                " 'pastholdcode' " +
                                " ) ";
                        updateVector.addElement(strQuery);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 05262023 ---------------------------------------------------------------------------------------



                // 트랜잭션으로 DB 처리한다.
                String returnResult = MssqlDatabase.executeTransaction(updateVector);
                if (returnResult == "N" || returnResult == "") {
                    GlobalMemberValues.displayDialog(mContext, "Warning", "Database Error", "Close");
                    return;
                } else {
                    // 08072023 ---------------------------------------------------------
                    MainMiddleService.mHoldCode = tempHoldCode;

                    // common gratuity 관련
                    GlobalMemberValues.deleteCartLastItemForCommonGratuityUse();
                    // common gratuity 관련
                    GlobalMemberValues.addCartLastItemForCommonGratuityUse();

                    MainMiddleService.mHoldCode = "NOHOLDCODE";
                    // 08072023 ---------------------------------------------------------

                    //GlobalMemberValues.logWrite("mergetablelog", "mSelectedZoneIdx : " + mSelectedZoneIdx + "\n");
                    TableSaleMain.onRefreshAtOutside();
                    TableSaleMain.setInitValues();

                    setFinish();
                }
            }
            TableSaleMain.setClearSelectedTableIdx(true);
        }

    }

    View.OnClickListener billPaidListBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.combineCloseBtn : {
                    setFinish();
                    break;
                }
            }
        }
    };

    public static void setFinish() {
        mActivity.finish();
    }

}
