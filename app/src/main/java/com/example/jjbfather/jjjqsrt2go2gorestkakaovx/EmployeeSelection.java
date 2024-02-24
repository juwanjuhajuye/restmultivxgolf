package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-22.
 */
public class EmployeeSelection {
    Activity mActivity;
    Context context;
    GetDataAtSQLite dataAtSqlite;
    Button closeBtn;

    TextView employeeSelectionTitleEditText;
    LinearLayout employeeListLinearLayout;
    LinearLayout departmentListLinearLayout;
    LinearLayout departmentLn;

    String mSelectedDepartmentIdx = "";

    LinearLayout mSelectedLn;

    public EmployeeSelection(Activity paramActivity, Context paramContext, GetDataAtSQLite paramDataAtSqlite) {
        this.mActivity = paramActivity;
        this.context = paramContext;
        dataAtSqlite = paramDataAtSqlite;

        // 객체 생성과 함께 Employee 버튼 클릭 리스너 연결
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONEMPLOYEE.setOnClickListener(employeeBtnClickListener);

        // Employee Selection Title 객체 생성
        employeeSelectionTitleEditText = (TextView)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION
                .findViewWithTag("employeeSelectionTitleEditTextTag");
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            employeeSelectionTitleEditText.setText("");
            employeeSelectionTitleEditText.setBackgroundResource(R.drawable.ab_imagebutton_employeeselection_title);
        } else {
            employeeSelectionTitleEditText.setTextSize(
                    employeeSelectionTitleEditText.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        // Department List LinearLayout 객체 생성
        departmentLn = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION
                .findViewWithTag("departmentLnTag");

        // Employee List LinearLayout 객체 생성
        employeeListLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION
                .findViewWithTag("linearLayoutEmployeeListTag");

        // Department List LinearLayout 객체 생성
        departmentListLinearLayout = (LinearLayout)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION
                .findViewWithTag("linearLayoutDepartmentListTag");

        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)GlobalMemberValues.GLOBAL_LAYOUTMEMBER_FRAMELAYOUT_EMPLOYEESELECTION
                .findViewWithTag("employeeSelectionCloseBtnTag");
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
                "select count(idx) from salon_storepart where sidx = '" + GlobalMemberValues.STORE_INDEX + "' "));

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

        Cursor empSalesCursor;
        String strSearchQuery = " select idx, partname from salon_storepart where sidx = '" + GlobalMemberValues.STORE_INDEX + "' ";
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

            if (departmentCnt == 0 && GlobalMemberValues.isStrEmpty(mSelectedDepartmentIdx)) {
                newLn.setBackgroundResource(R.drawable.button_selector_departselected);
                mSelectedLn = newLn;
            }

            if (departmentCnt == 0) {
                setEmployeeInfo(tempDepartmentIdx, "S");
            }

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

        if (mSelectedDepartmentIdx.equals("nopart")) {
            noPartNewLn.setBackgroundResource(R.drawable.button_selector_departselected);
            mSelectedLn = noPartNewLn;
        }

        if (departmentCnt == 0) {
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
    }
    /************************************************************************/

    /** 직원 배치하기 ********************************************************/
    public void setEmployeeInfo(String paramDepartmentIdx, String paramSearchType) {
        GlobalMemberValues.logWrite("DepartmentLog", "GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN : " + GlobalMemberValues.GLOBAL_DEPARTMENTVIEWYN + "\n");

        String searchQueryStr = " a.poslistviewyn = 'Y' ";

        if (paramSearchType.equals("S")) {
            if (!GlobalMemberValues.isStrEmpty(paramDepartmentIdx)) {
                searchQueryStr += " and a.idx in ( " +
                        " select employeeidx from salon_storepart_employee where sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                        " and partidx = '" + paramDepartmentIdx + "' " +
                        " ) ";
            } else {
                searchQueryStr += " and a.idx not in ( " +
                        " select employeeidx from salon_storepart_employee where sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                        " ) ";
            }
        }

        // 직원배열 배열 가져오기 (GetDataAtSQLite 클래스의 getEmployeeInfo 메소드를 통해 가져온다)
        String[] strArrEmployee = dataAtSqlite.getEmployeeInfo(searchQueryStr, "", "");

        String strEmployeeInfo = "";        // Splite 을 통해 가져오는 String 값 저장용 객체 선언
        String tempEmpName = "";

        // 안드로이드 포스 시스템 정보에 직원 GridLayout 에 보여줄 item 수로 저장된 수만큼 FOR 구문 돌린다.
        for (int i = 0; i < GlobalMemberValues.EmployeeItem_Count_At_TableLayout; i++) {
            // 부모뷰 객체(LinearLayout) 를 이용하여 Tag 값으로 View 객체를 생성한다.
            LinearLayout employeeLinear
                    = (LinearLayout)employeeListLinearLayout.findViewWithTag("employeeSelectionLinearTag" + (i+1));
            employeeLinear.removeAllViews();

            if (!GlobalMemberValues.isStrEmpty(strArrEmployee[i])) {
                strEmployeeInfo = strArrEmployee[i];
                String[] tempStrEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);

                // 직원 이름 및 idx 값이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfo) && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[0])
                        && !GlobalMemberValues.isStrEmpty(tempStrEmployeeInfoArr[1])) {

                    tempEmpName = tempStrEmployeeInfoArr[1];

                    // 버튼을 동적으로 생성한다.
                    Button empBtnInLinear = new Button(context);
                    LinearLayout.LayoutParams btnParam
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    empBtnInLinear.setLayoutParams(btnParam);
                    //empBtnInLinear.setBackgroundColor(Color.parseColor("#fff8e8"));
                    empBtnInLinear.setBackgroundResource(R.drawable.button_selector_employee_selection);
                    empBtnInLinear.setText(tempEmpName);
                    empBtnInLinear.setTextColor(Color.parseColor(GlobalMemberValues.CATEGORY_NOCLICKTEXTCOLOR1));
                    empBtnInLinear.setTextSize(empBtnInLinear.getTextSize() * GlobalMemberValues.getGlobalFontSize());
                    empBtnInLinear.setGravity(Gravity.CENTER);

                    if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null) {
                        if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx == tempStrEmployeeInfoArr[0]
                                || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx.equals(tempStrEmployeeInfoArr[0])) {
                            empBtnInLinear.setBackgroundResource(R.drawable.button_selector_employee_selected);
                            empBtnInLinear.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }

                    empBtnInLinear.setTag(strEmployeeInfo);
                    // 동적으로 생성한 직원버튼 클릭시 이벤트리스터 연결
                    empBtnInLinear.setOnClickListener(employeeBtnSelectedClickListener);

                    // LinearLayout 에 버튼을 붙인다.
                    employeeLinear.addView(empBtnInLinear);
                }
                //GlobalMemberValues.logWrite("MainActivity-TopCategory", "카테고리정보 " + i + " : " + strArrEmployee[i] + "\n");
            } else {
                tempEmpName = " ";

                // 버튼을 동적으로 생성한다.
                Button empBtnInLinear = new Button(context);
                LinearLayout.LayoutParams btnParam
                        = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                empBtnInLinear.setLayoutParams(btnParam);
                empBtnInLinear.setBackgroundColor(Color.parseColor("#292929"));
                empBtnInLinear.setText(tempEmpName);

                // LinearLayout 에 버튼을 붙인다.
                employeeLinear.addView(empBtnInLinear);
            }
        }
    }
    /*******************************************************************************/



    View.OnClickListener employeeBtnSelectedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            // Tag 값이 있을 경우에만
            if (!GlobalMemberValues.isStrEmpty(btn.getTag().toString())) {
                // 먼저 현재 선택한 직원을 변경한다.
                String strEmployeeInfo = btn.getTag().toString();
                final String[] strEmployeeInfoArr = strEmployeeInfo.split(GlobalMemberValues.STRSPLITTER1);
                // Tag 에 직원이름이 있을 경우에만..
                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
                    GlobalMemberValues.GLOBAL_EMPLOYEEINFO
                            = new TemporaryEmployeeInfo(strEmployeeInfoArr[0], strEmployeeInfoArr[1], strEmployeeInfoArr[2], strEmployeeInfoArr[3],
                            strEmployeeInfoArr[4], strEmployeeInfoArr[5], strEmployeeInfoArr[6], strEmployeeInfoArr[7], strEmployeeInfoArr[8],
                            strEmployeeInfoArr[9], strEmployeeInfoArr[10], strEmployeeInfoArr[12]);
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText("Server : " + strEmployeeInfoArr[1]);

                    // 서비스 선택리스트에 선택된 서비스가 있을 경우
                    // 선택한 서비스의 employee 를 변경할지 여부를 물어 처리한다.
                    if (MainMiddleService.selectedPosition != -1) {
                        new AlertDialog.Builder(context)
                                .setTitle("Item Delete")
                                .setMessage("Change the employee seleted item?")
                                //.setIcon(R.drawable.ic_launcher)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                TemporarySaleCart tempSCIns = null;
                                                String tempSaleCartIdx = "";
                                                if (MainMiddleService.mGeneralArrayList.size() > 0) {
                                                    tempSCIns = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);
                                                    tempSaleCartIdx = tempSCIns.tempSaleCartIdx;
                                                }

                                                // temp_salecart 테이블 에서 선택한 서비스의 직원 employee 를 변경한다.
                                                // DB 수정
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
                }
            }
            //GlobalMemberValues.logWrite("EmpButtonInfo", "직원 IDX : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx + "\n");
            //GlobalMemberValues.logWrite("EmpButtonInfo", "직원 이름 : " + GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName + "\n");
            GlobalMemberValues.setFrameLayoutVisibleChange("main");
        }
    };

    View.OnClickListener employeeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainRightBottom_Employee : {
                    GlobalMemberValues.setFrameLayoutVisibleChange("employeeSelection");
                    setDepartEmpSwitch();
                    break;
                }
                case R.id.employeeSelectionCloseBtn : {
                    GlobalMemberValues.setFrameLayoutVisibleChange("main");
                    break;
                }
            }
        }
    };


}
