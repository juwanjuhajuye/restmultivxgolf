package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.util.ArrayList;

public class EmployeeSelectionPopup extends Activity {
    private LinearLayout parentLn;
    Intent mIntent;

    public static Activity mActivity;
    public static Context context;

    GetDataAtSQLite dataAtSqlite;
    Button closeBtn, initselectedbtn;

    LinearLayout employeeListLinearLayout;
    LinearLayout departmentListLinearLayout;
    LinearLayout departmentLn;

    String mSelectedDepartmentIdx = "";

    LinearLayout mSelectedLn;

    public static int openCount = 0;

    String mOpenFrom = "";
    String mGetEmpIdx = "";
    static String mMainSide = "";
    static ArrayList<String> mParamArr;
    //*
    GridView employee_list_grid;
    String[] strArrEmployee;
    //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_employee_selection_popup);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;
        dataAtSqlite = new GetDataAtSQLite(this);

        int parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 10) * 8;
        int parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 8;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        parentLn = (LinearLayout)findViewById(R.id.employeeSelectPopupLn);
        parentLn.setLayoutParams(parentLnParams);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mOpenFrom = mIntent.getStringExtra("openfrom");
            mGetEmpIdx = mIntent.getStringExtra("empidx");
            mMainSide = mIntent.getStringExtra("main_side");
            mParamArr = mIntent.getStringArrayListExtra("table_idx_arr_list");
            // GlobalMemberValues.logWrite("PaymentReview", "넘겨받은 paymentChangeAmount : " + paymentChangeAmount + "\n");
            /*******************************************************************************************/
            //GlobalMemberValues.logWrite("ReturnQtyEditValue", "MainMiddleService 의 리스트뷰 항목수 : " + parentMainMiddleService.listViewCount + "\n");
        } else {
            closeEmployeePopupClose();
        }

        openCount++;

        // 프로그래스 바를 실행~
//        itemProDial = ProgressDialog.show(mContext, GlobalMemberValues.ANDROIDPOSNAME, "Bill split page is loading...", true, false);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                setContents();
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                startingHandler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();
    }

    private Handler startingHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 1. 이곳에 시간이 걸리는 작업이 끝난후 처리해야할 부분을 넣음. -----------------------
            setDepartEmpSwitch();

//            if (itemProDial != null) {
//                itemProDial.dismiss();
//            }
            // -------------------------------------------------------------------------------------
        }
    };

    public void setContents() {
        // Department List LinearLayout 객체 생성
        departmentLn = (LinearLayout)parentLn.findViewWithTag("departmentLnTag");

        // Employee List LinearLayout 객체 생성
        employeeListLinearLayout = (LinearLayout)parentLn.findViewWithTag("linearLayoutEmployeeListTag");

        // Department List LinearLayout 객체 생성
        departmentListLinearLayout = (LinearLayout)parentLn.findViewWithTag("linearLayoutDepartmentListTag");

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)parentLn.findViewWithTag("employeeSelectionCloseBtnTag");
        closeBtn.setOnClickListener(employeeBtnClickListener);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        initselectedbtn = (Button)parentLn.findViewWithTag("initselectedbtnTag");
        initselectedbtn.setOnClickListener(employeeBtnClickListener);
        initselectedbtn.setTextSize(32 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
    }

    /** 부서/직원 배치하기 ********************************************************/
    public void setDepartEmpSwitch() {
        mSelectedDepartmentIdx = "";

        String departmentViewYN = MainActivity.mDbInit.dbExecuteReadReturnString(
                "select departmentviewyn from salon_storestationsettings_system");
        if (GlobalMemberValues.isStrEmpty(departmentViewYN)) {
            departmentViewYN = "N";
        }

        int departmentCnt = GlobalMemberValues.getIntAtString(MainActivity.mDbInit.dbExecuteReadReturnString(
                "select count(idx) from salon_storeemployeerole where sidx = '" + GlobalMemberValues.STORE_INDEX + "' and not(delyn = 'Y') and useYN = 'Y' "));

        GlobalMemberValues.logWrite("employeerolejjjlog", "departmentViewYN : " + departmentViewYN + "\n");
        GlobalMemberValues.logWrite("employeerolejjjlog", "departmentCnt : " + departmentCnt + "\n");

        if ((departmentViewYN == "Y" || departmentViewYN.equals("Y")) && departmentCnt > 0) {
            departmentLn.setVisibility(View.VISIBLE);
            setDepartmentInfo();
        } else {
            departmentLn.setVisibility(View.GONE);
            setEmployeeInfo("", "D");
        }
        GlobalMemberValues.logWrite("DepartmentLog", "GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN : " + GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN + "\n");
    }
    /*******************************************************************************/

    /** 부서 배치하기 ********************************************************/
    public void setDepartmentInfo() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);

        departmentListLinearLayout.removeAllViews();

        LinearLayout.LayoutParams newLnLayoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLnLayoutParams.setMargins(2, 2, 2, 5);

        // 부서가 없는 직원들을 위한 부서.. -------------------------------------------------------------------------
        // LinearLayout 객체 생성
        final LinearLayout noPartNewLn = new LinearLayout(MainActivity.mContext);
        noPartNewLn.setLayoutParams(newLnLayoutParams);
        noPartNewLn.setOrientation(LinearLayout.HORIZONTAL);
        noPartNewLn.setPadding(5, 5, 5, 5);

        noPartNewLn.setBackgroundResource(R.drawable.button_selector_depart);
        noPartNewLn.setTag("");

        // Department Name
        TextView noDepartmentNameTv = new TextView(MainActivity.mContext);

        noDepartmentNameTv.setText("All Roles");
        noDepartmentNameTv.setTextSize(16.0f);
        noDepartmentNameTv.setTextColor(Color.parseColor("#ffffff"));
        noDepartmentNameTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        noDepartmentNameTv.setLayoutParams(newLnLayoutParams);
        noDepartmentNameTv.setPadding(3, 7, 3, 7);
        noDepartmentNameTv.setPaintFlags(noDepartmentNameTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        if (mSelectedDepartmentIdx.equals("nopart") || GlobalMemberValues.isStrEmpty(mSelectedDepartmentIdx)) {
            noPartNewLn.setBackgroundResource(R.drawable.button_selector_departselected);
            mSelectedLn = noPartNewLn;
            setEmployeeInfo("", "S");
        }

        noPartNewLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempDepartIdx = v.getTag().toString();
                mSelectedLn.setBackgroundResource(R.drawable.button_selector_depart);
                mSelectedDepartmentIdx = "nopart";
                mSelectedLn = noPartNewLn;
                mSelectedLn.setBackgroundResource(R.drawable.button_selector_departselected);

                setEmployeeInfo(tempDepartIdx, "S");
            }
        });

        noPartNewLn.addView(noDepartmentNameTv);
        departmentListLinearLayout.addView(noPartNewLn);
        // -------------------------------------------------------------------------------------------------------

        Cursor empSalesCursor;
        String strSearchQuery = " select idx, rolename from salon_storeemployeerole where sidx = '" + GlobalMemberValues.STORE_INDEX + "' and not(delyn = 'Y') and useYN = 'Y' order by rolename asc ";
        GlobalMemberValues.logWrite("DepartmentLog", "query : " + strSearchQuery + "\n");
        empSalesCursor = dbInit.dbExecuteRead(strSearchQuery);
        int departmentCnt = 0;
        while (empSalesCursor.moveToNext()) {
            String tempDepartmentIdx = GlobalMemberValues.getDBTextAfterChecked(empSalesCursor.getString(0), 1);
            String tempDepartmentName = GlobalMemberValues.getDBTextAfterChecked(empSalesCursor.getString(1), 1);

            // LinearLayout 객체 생성
            final LinearLayout newLn = new LinearLayout(MainActivity.mContext);
            newLn.setLayoutParams(newLnLayoutParams);
            newLn.setOrientation(LinearLayout.HORIZONTAL);
            newLn.setPadding(5, 5, 5, 5);

            newLn.setBackgroundResource(R.drawable.button_selector_depart);
            newLn.setTag(tempDepartmentIdx);

            // Department Name
            TextView departmentNameTv = new TextView(MainActivity.mContext);

            departmentNameTv.setText(tempDepartmentName);
            departmentNameTv.setTextSize(16.0f);
            departmentNameTv.setTextColor(Color.parseColor("#ffffff"));
            departmentNameTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            departmentNameTv.setLayoutParams(newLnLayoutParams);
            departmentNameTv.setPadding(3, 7, 3, 7);
            departmentNameTv.setPaintFlags(departmentNameTv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

            /**
            if (departmentCnt == 0 && GlobalMemberValues.isStrEmpty(mSelectedDepartmentIdx)) {
                newLn.setBackgroundResource(R.drawable.button_selector_departselected);
                mSelectedLn = newLn;
            }

            if (departmentCnt == 0) {
                setEmployeeInfo(tempDepartmentIdx, "S");
            }
             **/

            newLn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tempDepartIdx = v.getTag().toString();
                    if (!GlobalMemberValues.isStrEmpty(tempDepartIdx)) {
                        mSelectedLn.setBackgroundResource(R.drawable.button_selector_depart);
                        mSelectedDepartmentIdx = tempDepartIdx;
                        mSelectedLn = newLn;
                        mSelectedLn.setBackgroundResource(R.drawable.button_selector_departselected);

                        setEmployeeInfo(tempDepartIdx, "S");
                    }
                }
            });

            newLn.addView(departmentNameTv);
            departmentListLinearLayout.addView(newLn);

            departmentCnt++;
        }
    }
    /************************************************************************/

    /** 직원 배치하기 ********************************************************/
    public void setEmployeeInfo(String paramDepartmentIdx, String paramSearchType) {
        GlobalMemberValues.logWrite("DepartmentLog", "GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN : " + GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN + "\n");

        String searchQueryStr = " not((a.name = '') or (name is null)) ";

        if (GlobalMemberValues.isStrEmpty(mOpenFrom)) {
            searchQueryStr += " and a.poslistviewyn = 'Y' ";
        }

        if (paramSearchType.equals("S")) {
            if (!GlobalMemberValues.isStrEmpty(paramDepartmentIdx)) {
                searchQueryStr += " and a.emptype = '" + paramDepartmentIdx + "' ";
            }
        }

        // 직원배열 배열 가져오기 (GetDataAtSQLite 클래스의 getEmployeeInfo 메소드를 통해 가져온다)
        strArrEmployee = dataAtSqlite.getEmployeeInfo(searchQueryStr, "", "");

        Employee_grid_adapter adapter = new Employee_grid_adapter();
        employee_list_grid = (GridView)findViewById(R.id.employee_list_grid);
        employee_list_grid.setAdapter(adapter);
    }
    /*******************************************************************************/



    View.OnClickListener employeeBtnSelectedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // Tag 값이 있을 경우에만
            String strEmployeeInfo = btn.getTag().toString();

            if (GlobalMemberValues.isStrEmpty(mOpenFrom)) {
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfo)) {
                    setSelectedEmployee(context, strEmployeeInfo, "Y");
                }
                //GlobalMemberValues.logWrite("EmpButtonInfo", "직원 IDX : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "\n");
                //GlobalMemberValues.logWrite("EmpButtonInfo", "직원 이름 : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "\n");
                GlobalMemberValues.setFrameLayoutVisibleChange("main");
            } else {
                String[] tempStrEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);
                String empIdx = tempStrEmployeeInfoArr[0];
                String empName = tempStrEmployeeInfoArr[1];

                if (!GlobalMemberValues.isStrEmpty(empIdx) && !GlobalMemberValues.isStrEmpty(empName)) {
                    ClockInOutNavtiveWeb.mSelectedEmpIdx = empIdx;
                    ClockInOutNavtiveWeb.empTv.setText(empName);

                    closeEmployeePopupClose();
//                    if (GlobalMemberValues.isStrEmpty(mMainSide)){
//                        // 로그인 페이지에서 들어온 경우.
//                        closeEmployeePopupClose();
//                    } else {
//
//                        if (GlobalMemberValues.mPasswordYN_inMod.equals("Y")){
//                            // pass 물어봐야함.
//                            new AlertDialog.Builder(mActivity)
//                                    .setTitle("Warning")
//                                    .setMessage("There is no 'cash in' record\nClick [Yes] to log out")
//                                    .setNegativeButton("No", null)
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            String savedPassword = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empPwd;
//
//                                            if (savedPassword.equals(getPassword)) {
//                                                GlobalMemberValues.isEmployeeLoginPopup = false;
//                                                GlobalMemberValues.isOpenedTimeMenuPopup = false;
//                                                closeEmployeePopupClose();
//                                            }
//                                        }
//                                    }).show();
//
//
//                        } else {
//                            // pass 필요없음.
//                            closeEmployeePopupClose();
//                        }
//
//                    }
                }
            }
        }
    };

    public static void setSelectedEmployee(Context paramContext, String paramEmployeeInfo, String paramCloseYN) {
        if (GlobalMemberValues.isStrEmpty(paramEmployeeInfo)) {
            return;
        }

        final String[] strEmployeeInfoArr = paramEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);

        if (GlobalMemberValues.isStrEmpty(mMainSide)){
            GlobalMemberValues.b_isNotComplite_CashIn = false;
            // 로그인 페이지에서 들어온경우
            // Cash In 이후 Cash Out 하지 않은 직원이 있을 경우 다른 직원선택이 안되도록 한다. -------------------------------------
            String tempSql = " select EmployeeIdx from salon_sales_cashintout_history " +
                    " where cashoutNum = 0 " +
                    " and inoutreason like '%Starting cash%' ";
            String tempEmployeeIdx = MainActivity.mDbInit.dbExecuteReadReturnString(tempSql);
            //GlobalMemberValues.logWrite("selectedEmployeelog", "previous emp : " + tempEmployeeIdx + "\nnow selected emp : " + strEmployeeInfoArr[0] + "\n");
            if (!GlobalMemberValues.isStrEmpty(tempEmployeeIdx)) {
                if (!tempEmployeeIdx.equals(strEmployeeInfoArr[0])) {
                    GlobalMemberValues.displayDialog(paramContext, "Warning", "Please select the employee currently 'Cash In'", "Close");
                    GlobalMemberValues.b_isNotComplite_CashIn = true;
                    return;
                }
            }
            // -----------------------------------------------------------------------------------------------------------------
        } else {
            // 로그인 이외의 페이지에서 들어온 경우.
        }
        // 먼저 현재 선택한 직원을 변경한다.
        if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
            GlobalMemberValues.GLOBAL_EMPLOYEEINFO
                    = new TemporaryEmployeeInfo(strEmployeeInfoArr[0], strEmployeeInfoArr[1], strEmployeeInfoArr[2], strEmployeeInfoArr[3],
                    strEmployeeInfoArr[4], strEmployeeInfoArr[5], strEmployeeInfoArr[6], strEmployeeInfoArr[7], strEmployeeInfoArr[8],
                    strEmployeeInfoArr[9], strEmployeeInfoArr[10], strEmployeeInfoArr[12]);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText("Server : " + strEmployeeInfoArr[1]);

            if (GlobalMemberValues.isStrEmpty(mMainSide)){
                // 선택한 직원명을 Login 직원항목에 입력
                Employee_Login.employeeLoginEmpSelectTextView.setText(strEmployeeInfoArr[1]);
            } else {
                if (mMainSide.equals("Y")){
                    // main_side_button on
                    // 타임메뉴 셋
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.timeMenu_getServiceTime();

                    GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;

                    // 타임메뉴 설정된대로 실행.
                    GlobalMemberValues.openTimeMenuSelectPopupAuto();
                } else if (mMainSide.equals("T")) {
                    // main_side_button on
                    // 타임메뉴 셋
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.timeMenu_getServiceTime();

                    GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;

                    // 타임메뉴 설정된대로 실행.
                    GlobalMemberValues.openTimeMenuSelectPopupAuto();
                }
            }

            // 서비스 선택리스트에 선택된 서비스가 있을 경우
            // 선택한 서비스의 employee 를 변경할지 여부를 물어 처리한다.
            if (MainMiddleService.selectedPosition != -1) {
                new AlertDialog.Builder(paramContext)
                        .setTitle("Item Delete")
                        .setMessage("Change the employee seleted item?")
                        //.setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);

                                        // temp_salecart 테이블 에서 선택한 서비스의 직원 employee 를 변경한다.
                                        // DB 수정
                                        String tempSaleCartIdx = tempSCIns.tempSaleCartIdx;
                                        if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                                            String strSqlQuery = "update temp_salecart set " +
                                                    " empIdx ='" + strEmployeeInfoArr[0] + "', " +
                                                    " empName ='" + strEmployeeInfoArr[1] + "' " +
                                                    " where idx = '" + tempSaleCartIdx + "' ";
                                            GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "temp_salecart update query : " + strSqlQuery + "\n");
                                            DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
                                            String returnCode = "";
                                            returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
                                            GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "returnCode : " + returnCode + "\n");
                                            if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                                                // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                                                tempSCIns.mEmpIdx = strEmployeeInfoArr[0];
                                                tempSCIns.mEmpName = strEmployeeInfoArr[1];

                                                MainMiddleService.mGeneralArrayList.set(MainMiddleService.selectedPosition, tempSCIns);
                                                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                                                MainMiddleService.selectedPosition = -1;
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                                MainMiddleService.selectedPosition = -1;
                            }
                        })
                        .show();
            }

            if (paramCloseYN == "Y" || paramCloseYN.equals("Y")) {
                closeEmployeePopupClose();
            }
        }
    }

    View.OnClickListener employeeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.employeeSelectionCloseBtn : {
                    closeEmployeePopupClose();

                    break;
                }
                case R.id.initselectedbtn : {
                    GlobalMemberValues.GLOBAL_EMPLOYEEINFO = null;
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText("");
                    Employee_Login.employeeLoginEmpSelectTextView.setText("");
                    setContents();
                    break;
                }
            }
        }
    };

    private static void closeEmployeePopupClose () {
        if (mMainSide!= null){
            if (mMainSide.equals("T")){
                TableSaleMain.setOrderStart(mParamArr, true, false);
            }
        }
        openCount = 0;
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_bottom, R.anim.act_out_bottom);
        }
    }

    class Employee_grid_adapter extends BaseAdapter {
        LayoutInflater  inf;

        @Override
        public int getCount() {
            return strArrEmployee.length;
        }

        @Override
        public Object getItem(int i) {
            return strArrEmployee[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inf.inflate(R.layout.employee_grid_row, null);
            }
            Button employee_name = (Button)view.findViewById(R.id.employee_list_name);

            String strEmployeeInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
            String tempEmpName = "";
            strEmployeeInfo = strArrEmployee[i];
            String[] tempStrEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);

            if (!GlobalMemberValues.isStrEmpty(strArrEmployee[i])) {
                employee_name.setVisibility(View.VISIBLE);

                tempEmpName = tempStrEmployeeInfoArr[1];
                // 03072023
                String tempSql = " select EmployeeIdx from salon_sales_cashintout_history " +
                        " where cashoutNum = 0 " +
                        " and inoutreason like '%Starting cash%' ";
                String cashinEmpIdx = MainActivity.mDbInit.dbExecuteReadReturnString(tempSql);
                if (!GlobalMemberValues.isStrEmpty(cashinEmpIdx) && cashinEmpIdx.equals(tempStrEmployeeInfoArr[0])) {
                    tempEmpName += "\n(Cash In)";
                }

                // 직원 이름 및 idx 값이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfo) && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[0])
                        && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[1])) {

                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx == tempStrEmployeeInfoArr[0]
                                || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx.equals(tempStrEmployeeInfoArr[0])) {
                            employee_name.setBackgroundResource(R.drawable.button_selector_employee_popup_select2);
                            employee_name.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                }
                employee_name.setText(tempEmpName);
                employee_name.setTag(strEmployeeInfo);
                employee_name.setOnClickListener(employeeBtnSelectedClickListener);
            } else {
                employee_name.setVisibility(View.GONE);
            }



            return view;
        }
    }

}
