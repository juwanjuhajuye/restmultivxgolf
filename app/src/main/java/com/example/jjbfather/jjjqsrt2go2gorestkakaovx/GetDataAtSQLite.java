package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.database.Cursor;

import java.sql.ResultSet;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-10.
 */
public class GetDataAtSQLite {
    private DatabaseInit dbInit;

    public GetDataAtSQLite(Context context) {
        dbInit = new DatabaseInit(context);
        if (dbInit == null) {
            dbInit = MainActivity.mDbInit;
        }
        //GlobalMemberValues.logWrite("GetDataAtSQLite", "생성자 내부\n");
    }

    /*** 카테고리 정보 리턴 메소드 **************************************************/
    //리턴값 : 인덱스 || 카테고리명 || 카테고리컬러값 || positionNo
    public String[] getCategoryInfo() {
        String[] categoryInfoArray = new String[GlobalMemberValues.STOREMAXCATEGORYSU];
        for (int i = 0; i < GlobalMemberValues.STOREMAXCATEGORYSU; i++) {
            categoryInfoArray[i] = "";
        }
        String strQuery = "select idx, servicename, colorNo, positionNo, servicename2 " +
                " from salon_storeservice_main " +
                " where delyn = 'N' " +
                " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                " and positionNo > 0 and not(positionNo is null or positionNo = '')" +
                " and categoryusezone like '%P%' " +
                " order by positionNo asc, servicename asc";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        String colorNoValue = GlobalMemberValues.CATEGORYCOLORVALUE[0];

        String tempAllCategoryName = "";
        String tempCategoryName2 = "";
        int colorNoData = 0;
        while (cursor.moveToNext()) {
            int position = cursor.getInt(3);
            if (position > 0 && position <= GlobalMemberValues.STOREMAXCATEGORYSU) {
                colorNoData = cursor.getInt(2);
                if (colorNoData > 0) {
                    colorNoValue = GlobalMemberValues.CATEGORYCOLORVALUE[(colorNoData-1)];
                }

                tempAllCategoryName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);

                tempCategoryName2 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
                if (!GlobalMemberValues.isStrEmpty(tempCategoryName2)) {
                    tempAllCategoryName += "\n" + tempCategoryName2;
                }
                categoryInfoArray[position-1] = cursor.getInt(0) + "||" +
                        tempAllCategoryName + "||" +
                        colorNoValue + "||" +
                        position;
                //GlobalMemberValues.logWrite("GetDataAtSQLite-getCategoryInfo", "데이터" + position + " : " + categoryInfoArray[position-1] + "\n");
            }
        }
        return categoryInfoArray;
    }
    /********************************************************************************/


    /*** 서비스 정보 리턴 메소드 **************************************************/
    //리턴값 : 인덱스 || 카테고리인덱스 || 서비스명 || positionNo || 파일이름 || 파일경로 || 서비스가격
    //                || 커미션ratio || 커미션타입 || 포인트비율 || 세일가격 || 세일시작일 || 세일마감일
    //                || 셋트메뉴여부 || 카테고리이름
    public String[] getServiceInfo(String categoryIdx, int menusu) {
        // 현재 요일(요일번호) 구한다
        int tempTodayWeekdayNum = GlobalMemberValues.getWeekDayNum();

        String[] serviceInfoArray = new String[menusu];
        for (int i = 0; i < menusu; i++) {
            serviceInfoArray[i] = "";
        }
        String addSQLQuery = "";
        if (GlobalMemberValues.TIMEMENUUSEYN.equals("Y") || GlobalMemberValues.TIMEMENUUSEYN == "Y") {
            addSQLQuery = " and case when(a.timemenuyn == 'Y') then a.weekdays like '%" + tempTodayWeekdayNum + "%' " +
                    " else (a.weekdays like '%%' or a.weekdays is null) end " +
                    " and case when(a.timemenuyn == 'Y') then a.timemenutime like '%" + GlobalMemberValues.SELECTED_TIME_CODEVALUE + "%' " +
                    " else (a.timemenutime like '%%' or a.timemenutime is null) end ";
        }
        String strQuery = "select a.idx, a.midx, a.servicename, a.positionNo, a.strFileName, a.strFilePath, " +
                " a.service_price, a.commissionratioType, a.commissionratio, a.pointratio, " +
                " a.saleprice, a.salestart, a.saleend, a.activeyn,  " +
                " b.servicename as castegoryName, a.servicename2, a.servicename3, a.colorNo " +
                // jihun park categoryname2 가져오기 추가
                ", b.servicename2 as categoryName2" +  // 18 번.
                ", a.imageusezone " +
                //
                " from salon_storeservice_sub as a " +
                " left outer join salon_storeservice_main as b on a.midx = b.idx " +
                " where a.delyn = 'N' " +
                " and a.midx = '" + categoryIdx + "' " +
                " and not(a.activeyn = 'N') " +
                " and b.sidx='" + GlobalMemberValues.STORE_INDEX + "' " +
                " and a.positionNo > 0 and not(a.positionNo is null or a.positionNo = '') " +
                " and a.menuusezone like '%P%' " +
                " and (a.divideryn = 'N' or a.divideryn = '' or a.divideryn is null) " +
                //" and ( " +
                //" a.timemenuyn = 'N' or a.timemenuyn is null " +
                //" or a.timemenuyn = '' or not(a.timemenuyn = 'Y') " +
                //" or a.timemenutime like '%" + GlobalMemberValues.NOW_TIME_CODEVALUE + "%' " +
                //" or a.weekdays is null or a.weekdays = '' " +
                //" ) " +
                addSQLQuery +
                " order by a.positionNo asc, a.servicename asc";
        GlobalMemberValues.logWrite("MainMiddleServiceQuery", "Query : " + strQuery + "\n");
        Cursor cursor = dbInit.dbExecuteRead(strQuery);

        String tempServiceNameAll = "";
        String tempServiceName2 = "";
        String tempServiceName3 = "";
        String tempColorNo = "";
        String tempCategoryName = "";

        String item_img = "";
        String item_path = "";
        String temp_imageusezone = "";

        while (cursor.moveToNext()) {
            int position = cursor.getInt(3);
            if (position > 0 && position <= menusu) {
                String svcActiveYN = cursor.getString(13);
                if(svcActiveYN == null || svcActiveYN == "" || svcActiveYN.equals("")) {
                    svcActiveYN = "Y";
                }

                tempServiceNameAll = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
                tempServiceName2 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(15), 1);
                tempServiceName3 = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(16), 1);

                if (!GlobalMemberValues.isStrEmpty(tempServiceName2)) {
                    tempServiceNameAll += "\n" + tempServiceName2;
                }
                if (!GlobalMemberValues.isStrEmpty(tempServiceName3)) {
                    tempServiceNameAll += "\n" + tempServiceName3;
                }

                tempColorNo = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(17), 1);
                if (GlobalMemberValues.isStrEmpty(tempColorNo)) {
                    tempColorNo = "0";
                }

                tempCategoryName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(18), 1);
                if (GlobalMemberValues.isStrEmpty(tempCategoryName)){
                    tempCategoryName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(14), 1);
                } else {
                    tempCategoryName = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(14), 1) +
                            "-AAA-" +
                            tempCategoryName;
                }

                String tempQuery = " select count(*) from temp_salecart where holdcode = '" + MainMiddleService.mHoldCode + "' " +
                        " and svcidx = '" + GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "' ";
                String svcQtyInCart = MainActivity.mDbInit.dbExecuteReadReturnString(tempQuery);

                item_img = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
                item_path = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1);
                temp_imageusezone = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(19), 1);
                if (temp_imageusezone.indexOf("P") > -1) {
                } else {
                    item_img = "";
                    item_path = "";
                }

                serviceInfoArray[position-1] = cursor.getInt(0) + "||" +
                        cursor.getInt(1) + "||" +
                        tempServiceNameAll + "||" +
                        item_img + "||" +
                        item_path + "||" +
                        position + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(11), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13), 1) + "||" +
//                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(14), 1) + "||" +
                        tempCategoryName + "||" +
                        svcActiveYN + "||" +
                        tempColorNo + "||" +
                        svcQtyInCart;
            }
            //GlobalMemberValues.logWrite("GetDataAtSQLite-viewServiceInfo", position + " : " + serviceInfoArray[position-1] + "\n");
        }
        return serviceInfoArray;
    }
    /********************************************************************************/


    /*** 직원정보 리턴 메소드 **************************************************/
    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String[] getEmployeeInfo(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        String[] employeeInfoArray = new String[GlobalMemberValues.EmployeeItem_Count_At_TableLayout];
        for (int i = 0; i < GlobalMemberValues.EmployeeItem_Count_At_TableLayout; i++) {
            employeeInfoArray[i] = "";
        }

        String searchStr = paramSearchQuery;
        if (!GlobalMemberValues.isStrEmpty(searchStr)) {
            searchStr = " and " + searchStr;
        }
        String groupbyStr = paramGroupbyQuery;
        if (!GlobalMemberValues.isStrEmpty(groupbyStr)) {
            groupbyStr = " group by " + groupbyStr;
        }
        String orderbyStr = paramOrderbyQuery;
        if (!GlobalMemberValues.isStrEmpty(orderbyStr)) {
            orderbyStr = " order by " + orderbyStr;
        } else {
            orderbyStr = " order by a.name asc";
        }

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.clockinoutpwd, a.eLevel, a.sidx, a.commissionratio " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                searchStr + groupbyStr + orderbyStr; // + " limit 20 ";


        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int empCount = 0;
        while (cursor.moveToNext()) {
            if (empCount < GlobalMemberValues.EmployeeItem_Count_At_TableLayout) {
                String empName = cursor.getString(1);
                if (!GlobalMemberValues.isStrEmpty(empName)) {
                    tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
                    if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                        tempCommissionratio = "0";
                    }

                    employeeInfoArray[empCount] = cursor.getInt(0) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                            cursor.getInt(2) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                            GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                            String.valueOf(cursor.getInt(11)) + "||" +
                            tempCommissionratio;
                    GlobalMemberValues.logWrite("getemployeeInfo", "데이터" + empCount + " : " + employeeInfoArray[empCount] + "\n");
                    empCount++;
                }
            }
        }
        return employeeInfoArray;
    }
    /********************************************************************************/


    /*** 직원정보 리턴 메소드 (직원 한명만 검색해서 가져올 때) **********************************************/
    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String getEmployeeInfoByEid(String paramEmpEid) {
        String returnStr = "";

        String searchStr = "";
        if (!GlobalMemberValues.isStrEmpty(paramEmpEid)) {
            searchStr = " and eid = '" + paramEmpEid + "' ";
        }

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.clockinoutpwd, a.eLevel, a.sidx, a.commissionratio, a.permission " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " + searchStr;


        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int empCount = 0;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String empName = cursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(empName)) {
                tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
                if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                    tempCommissionratio = "0";
                }

                returnStr = cursor.getInt(0) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        cursor.getInt(2) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        String.valueOf(cursor.getInt(11)) + "||" +
                        tempCommissionratio + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13),1);

                empCount++;
            }
        }
        return returnStr;
    }
    /********************************************************************************/



    /*** 직원정보 리턴 메소드 (직원 한명만 검색해서 가져올 때) **********************************************/
    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String getEmployeeInfoByServerAccessCode(String paramServerAccessCode) {
        String returnStr = "";

        String searchStr = "";
        if (!GlobalMemberValues.isStrEmpty(paramServerAccessCode)) {
            searchStr = " and serveraccesscode = '" + paramServerAccessCode + "' ";
        }

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.serveraccesspwd, a.eLevel, a.sidx, a.commissionratio, a.permission " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " + searchStr;


        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int empCount = 0;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String empName = cursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(empName)) {
                tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
                if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                    tempCommissionratio = "0";
                }

                returnStr = cursor.getInt(0) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        cursor.getInt(2) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        String.valueOf(cursor.getInt(11)) + "||" +
                        tempCommissionratio + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13),1);

                empCount++;
            }
        }
        return returnStr;
    }
    /********************************************************************************/


    /*** 직원정보 리턴 메소드 (직원 한명만 검색해서 가져올 때) **********************************************/
    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String getEmployeeInfoByServerAccessPassword(String paramServerAccessPassword) {
        String returnStr = "";

        String searchStr = "";
        if (!GlobalMemberValues.isStrEmpty(paramServerAccessPassword)) {
            searchStr = " and serveraccesspwd = '" + paramServerAccessPassword + "' ";
        }

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.serveraccesspwd, a.eLevel, a.sidx, a.commissionratio, a.permission " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " + searchStr;


        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int empCount = 0;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String empName = cursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(empName)) {
                tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
                if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                    tempCommissionratio = "0";
                }

                returnStr = cursor.getInt(0) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        cursor.getInt(2) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        String.valueOf(cursor.getInt(11)) + "||" +
                        tempCommissionratio + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13),1);

                empCount++;
            }
        }
        return returnStr;
    }
    /********************************************************************************/


    /*** 직원정보 리턴 메소드 (직원 한명만 검색해서 가져올 때) **********************************************/
    //리턴값 : 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급 || 스토어인덱스
    public String getEmployeeInfoByClockInOutPwd(String paramClockInOutPwd) {
        String returnStr = "";

        String searchStr = "";
        if (!GlobalMemberValues.isStrEmpty(paramClockInOutPwd)) {
            searchStr = " and clockinoutpwd = '" + paramClockInOutPwd + "' ";
        }

        String strQuery = "select a.idx, a.name, a.age, a.phone, a.email, a.photo, a.wdate, " +
                " a.pos_emp_code, a.eid, a.clockinoutpwd, a.eLevel, a.sidx, a.commissionratio, a.permission " +
                " from salon_storeemployee a" +
                " where a.delyn = 'N' and not((a.name is null) or (a.name = '')) " +
                " and a.sidx = '" + GlobalMemberValues.STORE_INDEX + "' " + searchStr;


        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        String tempCommissionratio = "0";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int empCount = 0;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String empName = cursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(empName)) {
                tempCommissionratio = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1);
                if (GlobalMemberValues.isStrEmpty(tempCommissionratio)) {
                    tempCommissionratio = "0";
                }

                returnStr = cursor.getInt(0) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        cursor.getInt(2) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        String.valueOf(cursor.getInt(11)) + "||" +
                        tempCommissionratio + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13),1);

                empCount++;
            }
        }
        return returnStr;
    }
    /********************************************************************************/


    /*** 고객정보 리턴 메소드 **************************************************/
    // Cursor 값을 리턴한다.
    public Cursor getCustomerInfo(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        Cursor returnCustomerInfoCursor;

        String searchStr = paramSearchQuery;
        if (!GlobalMemberValues.isStrEmpty(searchStr)) {
            searchStr = " and " + searchStr;
        }
        String groupbyStr = paramGroupbyQuery;
        if (!GlobalMemberValues.isStrEmpty(groupbyStr)) {
            groupbyStr = " group by " + groupbyStr;
        }
        String orderbyStr = paramOrderbyQuery;
        if (!GlobalMemberValues.isStrEmpty(orderbyStr)) {
            orderbyStr = " order by " + orderbyStr;
        } else {
            orderbyStr = " order by a.name asc";
        }

        String strQuery = "select a.idx, a.uid, a.name, a.password, a.wdate, a.gender, b.phone, b.mobile, " +
                " b.byear, b.bmonth, b.bday, b.addr1, b.addr2, b.zip, b.state, b.city, b.membershipCardNo, b.memo, c.mileage, " +
                " strftime('%m-%d-%Y %H:%M:%S', a.lastvisitForSale), c.memcardnum, c.memmobile " +
                " from member1 a left join member2 b on a.uid = b.uid left join salon_member c on a.uid = c.uid " +
                " where a.delyn = 'n' " +
                //" and a.uid in (select uid from salon_member where sidx = '" + GlobalMemberValues.STORE_INDEX + "') " +
                searchStr + groupbyStr + orderbyStr;

        GlobalMemberValues.logWrite("getemployeeInfo", "쿼리 : " + strQuery + "\n");

        returnCustomerInfoCursor = dbInit.dbExecuteRead(strQuery);
        return returnCustomerInfoCursor;
    }
    /********************************************************************************/



    /*** 상품(Product)정보 리턴 메소드 **************************************************/
    // Cursor 값을 리턴한다.
    public Cursor getProductInfo(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        Cursor returnProductInfoCursor;

        String searchStr = paramSearchQuery;
        if (!GlobalMemberValues.isStrEmpty(searchStr)) {
            searchStr = " and " + searchStr;
        }
        String groupbyStr = paramGroupbyQuery;
        if (!GlobalMemberValues.isStrEmpty(groupbyStr)) {
            groupbyStr = " group by " + groupbyStr;
        }
        String orderbyStr = paramOrderbyQuery;
        if (!GlobalMemberValues.isStrEmpty(orderbyStr)) {
            orderbyStr = " order by " + orderbyStr;
        } else {
            orderbyStr = " order by a.name asc";
        }

        String strQuery = "select a.idx, a.sidx, a.name, a.description, a.wdate, a.price, a.pos_code, " +
                " a.delyn, a.pointratio, a.saleprice, a.salestart, a.saleend, a.strFileName, a.strFilePath, a.strOrgFileName, " +
                " a.activeyn, a.productCode " +
                " from salon_storeproduct a " +
                " where sidx ='" + GlobalMemberValues.STORE_INDEX + "' " +
                " and a.delyn = 'N' and a.activeyn = 'Y' " +
                searchStr + groupbyStr + orderbyStr;

        GlobalMemberValues.logWrite("getProductInfo", "쿼리 : " + strQuery + "\n");

        returnProductInfoCursor = dbInit.dbExecuteRead(strQuery);
        return returnProductInfoCursor;
    }
    /********************************************************************************/


    /*** 상품(Product)정보 리턴 메소드 **************************************************/
    // Cursor 값을 리턴한다.
    public Cursor getFoodSearch(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery, String paramSearchTxt) {
        Cursor returnMenuSearchCursor;

        String searchStr = paramSearchQuery;
        if (!GlobalMemberValues.isStrEmpty(searchStr)) {
            searchStr = " and " + searchStr;
        }
        String groupbyStr = paramGroupbyQuery;
        if (!GlobalMemberValues.isStrEmpty(groupbyStr)) {
            groupbyStr = " group by " + groupbyStr;
        }

        String orderbyStr = paramOrderbyQuery;
        if (!GlobalMemberValues.isStrEmpty(orderbyStr)) {
            orderbyStr = " order by " + orderbyStr;
        } else {
            if (GlobalMemberValues.isStrEmpty(paramSearchTxt)) {
                orderbyStr = " order by (a.servicename || ' ' || a.servicename2 || ' ' || a.servicename3) asc";
            } else {
                orderbyStr = " order by instr(upper(a.servicename || ' ' || a.servicename2 || ' ' || a.servicename3), upper('" + paramSearchTxt + "')) asc, ";
                orderbyStr += " (a.servicename || ' ' || a.servicename2 || ' ' || a.servicename3) asc ";
            }
        }

        String strQuery = "select a.idx, a.midx, a.servicename, a.positionNo, a.strFileName, a.strFilePath, " +
                " a.service_price, a.commissionratioType, a.commissionratio, a.pointratio, " +
                " a.saleprice, a.salestart, a.saleend, a.activeyn,  " +
                " b.servicename as castegoryName, a.servicename2, a.servicename3, a.colorNo " +
                " from salon_storeservice_sub as a " +
                " left outer join salon_storeservice_main as b on a.midx = b.idx " +

                " where a.delyn = 'N' " +
                " and not(a.activeyn = 'N') " +
                " and b.sidx='" + GlobalMemberValues.STORE_INDEX + "' " +
                " and a.positionNo > 0 and not(a.positionNo is null or a.positionNo = '') " +
                " and a.menuusezone like '%P%' " +
                " and (a.divideryn = 'N' or a.divideryn = '' or a.divideryn is null) " +
                searchStr + groupbyStr + orderbyStr;


        GlobalMemberValues.logWrite("getFoodSearchLog", "쿼리 : " + strQuery + "\n");

        returnMenuSearchCursor = dbInit.dbExecuteRead(strQuery);
        return returnMenuSearchCursor;
    }
    /********************************************************************************/


    /*** 기프트카드 정보 리턴 메소드 **************************************************/
    //리턴값 : 인덱스 || 기프트카드명 || 가격 || pointratio || saleprice || saleStart || saleEnd
    // || strFileName || strFilePath || strOrgFileName
    public String[] getGiftCardInfo() {
        String[] giftCardInfoArray = new String[GlobalMemberValues.STOREMAXGIFTCARDSU];
        for (int i = 0; i < GlobalMemberValues.STOREMAXGIFTCARDSU; i++) {
            giftCardInfoArray[i] = "";
        }
        String strQuery = "select idx, name, price, pointratio, saleprice, saleStart, saleEnd, " +
                " strFileName, strFilePath, strOrgFileName " +
                " from salon_storegiftcard " +
                " where delyn = 'N' " +
                " and sidx = '" + GlobalMemberValues.STORE_INDEX + "' " +
                " order by name asc limit 20";
        Cursor cursor = dbInit.dbExecuteRead(strQuery);
        int gcCount = 0;
        while (cursor.moveToNext()) {
            String tempGiftCardName = cursor.getString(1);
            if (!GlobalMemberValues.isStrEmpty(tempGiftCardName)) {
                giftCardInfoArray[gcCount] = cursor.getInt(0) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1);
                gcCount++;
            }
        }
        return giftCardInfoArray;
    }
    /********************************************************************************/


    /*** 세일정보 리턴 메소드 **************************************************/
    // Cursor 값을 리턴한다.
    public ResultSet getSaleHistory(String paramSearchQuery, String paramGroupbyQuery, String paramOrderbyQuery) {
        //Cursor returnSaleHistoryCursor;
        ResultSet returnSaleHistoryCursor;

        String searchStr = paramSearchQuery;
        if (!GlobalMemberValues.isStrEmpty(searchStr)) {
            searchStr = " and " + searchStr;
        }
        String groupbyStr = paramGroupbyQuery;
        if (!GlobalMemberValues.isStrEmpty(groupbyStr)) {
            groupbyStr = " group by " + groupbyStr;
        }
        String orderbyStr = paramOrderbyQuery;
        if (!GlobalMemberValues.isStrEmpty(orderbyStr)) {
            orderbyStr = " order by " + orderbyStr;
        } else {
            orderbyStr = " order by a.idx desc";
        }

        String strQuery = "select a.idx, a.salesCode, a.salesBalPriceAmount, a.taxAmount, a.totalAmount, " +
                " a.useTotalCashAmount, a.useTotalCardAmount, a.useTotalGiftCardAmount, a.useTotalCheckAmount, a.useTotalPointAmount, " +
                " a.customerId, a.customerName, a.customerPhone, a.customerPosCode, " +
                " a.totalDiscountExtraPrice, a.eachDiscountExtraPrice, a.allDiscountExtraPrice, a.saleDate, a.status, a.changeMoney, " +
                " a.totalDiscountPrice, totalExtraPrice, a.allDiscountExtraStr, " +
                " a.customerEmail, a.customerAddr1, a.customerAddr2, a.customerCity, a.customerState, a.customerZip, " +
                " a.deliveryday, a.deliverytime, a.deliverydate, a.deliverytakeaway, " +
                " a.customermemo, a.deliveryStatus, a.takeawayStatus, a.kitchenprintedyn, a.deliverypickupfee, a.customerordernumber, a.customerpagernumber, a.stcode " +
                " from salon_sales a " +
                " where a.delyn = 'N' " +
                searchStr + groupbyStr + orderbyStr;

        GlobalMemberValues.logWrite("getSaleHistory", "sale history 쿼리 : " + strQuery + "\n");

        //returnSaleHistoryCursor = dbInit.dbExecuteRead(strQuery);
        returnSaleHistoryCursor = MssqlDatabase.getResultSetValue(strQuery);

        return returnSaleHistoryCursor;
    }
    /********************************************************************************/


    /*** 저장된 Modifier 리턴 메소드 **************************************************/
    public String[] getSaleCartModifier(String paramTable, String paramModifierCode) {
        String[] saleCartModifierArray = null;

        // 전체 배열수를 구한다.
        int tempArrayCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select count(idx) from " + paramTable + " where  modifiercode = '" + paramModifierCode + "' "
                )
        );
        if (tempArrayCount > 0) {
            saleCartModifierArray = new String[tempArrayCount];
            for (int i = 0; i < tempArrayCount; i++) {
                saleCartModifierArray[i] = "";
            }
            String strQuery = " select A.holdcode, A.sidx, A.stcode, A.svcIdx, A.modifiername, A.optionaddname, A.modifieroptionaddfullname, " +
                    " A.modifierprice, A.optionaddprice, A.optioncategoryidx, A.optionitemidx, A.optioncategoryname, A.modifiercode, A.qty " +
                    " from " + paramTable + " A left join salon_storeservice_option B on A.optioncategoryidx = B.idx " +
                    " where  A.modifiercode = '" + paramModifierCode + "' " +
                    " order by B.sortnum asc, A.modifieroptionaddfullname asc ";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                saleCartModifierArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(11), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13), 1);
                gcCount++;
            }
            cursor.close();
        }
        return saleCartModifierArray;
    }
    /********************************************************************************/

    /*** Modifier Option 정보 리턴 메소드 **************************************************/
    public String[] getModifierOption(String paramServiceIdx, int paramArrayCount) {
        String[] modifierOptionArray = null;

        if (paramArrayCount > 0) {
            modifierOptionArray = new String[paramArrayCount];
            for (int i = 0; i < paramArrayCount; i++) {
                modifierOptionArray[i] = "";
            }
            String strQuery = "select idx, optionname, optionpilsuyn, optiontype, autoviewinposyn from salon_storeservice_option " +
                    " where  svcIdx = '" + paramServiceIdx + "' " +
                    " and optionuseyn = 'Y' " +
                    " order by sortnum asc, optionname asc, idx desc";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                modifierOptionArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
                gcCount++;
            }
            cursor.close();
        }
        return modifierOptionArray;
    }
    /********************************************************************************/

    /*** Modifier Option Item 정보 리턴 메소드 **************************************************/
    public String[] getModifierOptionItem(String paramOptionIdx) {
        String[] modifierOptionItemArray = null;

        // 전체 배열수를 구한다.
        int tempArrayCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select count(idx) from salon_storeservice_option_item where optionidx = '" + paramOptionIdx + "' and itemuseyn = 'Y' "
                )
        );

        if (tempArrayCount > 0) {
            modifierOptionItemArray = new String[tempArrayCount];
            for (int i = 0; i < tempArrayCount; i++) {
                modifierOptionItemArray[i] = "";
            }
            String strQuery = "select itemname, itemprice, idx from salon_storeservice_option_item " +
                    " where optionidx = '" + paramOptionIdx + "' and itemuseyn = 'Y' " +
                    " order by sortnum asc ";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                modifierOptionItemArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
                gcCount++;
            }
            cursor.close();
        }
        return modifierOptionItemArray;
    }
    /********************************************************************************/

    /*** Modifier Option 정보 리턴 메소드 **************************************************/
    public String[] getModifierButton(String paramServiceIdx, int paramArrayCount) {
        String[] modifierButtonArray = null;

        if (paramArrayCount > 0) {
            modifierButtonArray = new String[paramArrayCount];
            for (int i = 0; i < paramArrayCount; i++) {
                modifierButtonArray[i] = "";
            }
            String strQuery = " select idx, btnname, colorcode, price, ignoreprice from salon_storeservice_option_btn " +
                    " where svcIdx = '" + paramServiceIdx + "' " +
                    " and useyn = 'Y' " +
                    " order by sortnum asc, btnname asc, idx desc";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                modifierButtonArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
                gcCount++;
            }
            cursor.close();
        }
        return modifierButtonArray;
    }
    /********************************************************************************/










    /*** General Modifier ***********************************************************/
    /********************************************************************************/

    /*** 저장된 Modifier 리턴 메소드 **************************************************/
    public String[] getSaleCartModifier_gm(String paramTable, String paramModifierCode) {
        String[] saleCartModifierArray = null;

        // 전체 배열수를 구한다.
        int tempArrayCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select count(idx) from " + paramTable + " where  modifiercode = '" + paramModifierCode + "' "
                )
        );
        if (tempArrayCount > 0) {
            saleCartModifierArray = new String[tempArrayCount];
            for (int i = 0; i < tempArrayCount; i++) {
                saleCartModifierArray[i] = "";
            }
            String strQuery = " select A.holdcode, A.sidx, A.stcode, A.svcIdx, A.modifiername, A.optionaddname, A.modifieroptionaddfullname, " +
                    " A.modifierprice, A.optionaddprice, A.optioncategoryidx, A.optionitemidx, A.optioncategoryname, A.modifiercode, A.qty " +
                    " from " + paramTable + " A left join salon_storeservice_commonmodifier B on A.optioncategoryidx = B.idx " +
                    " where  A.modifiercode = '" + paramModifierCode + "' " +
                    " order by B.idx asc, A.modifieroptionaddfullname asc ";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                saleCartModifierArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(5), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(6), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(7), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(8), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(9), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(10), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(11), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(12), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(13), 1);
                gcCount++;
            }
            cursor.close();
        }
        return saleCartModifierArray;
    }
    /********************************************************************************/

    /*** Modifier Option 정보 리턴 메소드 **************************************************/
    public String[] getModifierOption_gm(String paramServiceIdx, int paramArrayCount) {
        String[] modifierOptionArray = null;

        if (paramArrayCount > 0) {
            modifierOptionArray = new String[paramArrayCount];
            for (int i = 0; i < paramArrayCount; i++) {
                modifierOptionArray[i] = "";
            }
            String strQuery = "select idx, optionname, optionpilsuyn, optiontype, autoviewinposyn from salon_storeservice_commonmodifier " +
                    " where  optionuseyn = 'Y' " +
                    " order by idx asc, optionname asc, idx desc";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                modifierOptionArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(3), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(4), 1);
                gcCount++;
            }
            cursor.close();
        }
        return modifierOptionArray;
    }
    /********************************************************************************/

    /*** Modifier Option Item 정보 리턴 메소드 **************************************************/
    public String[] getModifierOptionItem_gm(String paramOptionIdx) {
        String[] modifierOptionItemArray = null;

        // 전체 배열수를 구한다.
        int tempArrayCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select count(idx) from salon_storeservice_commonmodifier_item where optionidx = '" + paramOptionIdx + "' and itemuseyn = 'Y' "
                )
        );

        if (tempArrayCount > 0) {
            modifierOptionItemArray = new String[tempArrayCount];
            for (int i = 0; i < tempArrayCount; i++) {
                modifierOptionItemArray[i] = "";
            }
            String strQuery = "select itemname, itemprice, idx from salon_storeservice_commonmodifier_item " +
                    " where optionidx = '" + paramOptionIdx + "' and itemuseyn = 'Y' " +
                    " order by sortnum asc ";
            Cursor cursor = dbInit.dbExecuteRead(strQuery);
            int gcCount = 0;
            while (cursor.moveToNext()) {
                modifierOptionItemArray[gcCount] = GlobalMemberValues.getDBTextAfterChecked(cursor.getString(0), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(1), 1) + "||" +
                        GlobalMemberValues.getDBTextAfterChecked(cursor.getString(2), 1);
                gcCount++;
            }
            cursor.close();
        }
        return modifierOptionItemArray;
    }
    /********************************************************************************/

}
