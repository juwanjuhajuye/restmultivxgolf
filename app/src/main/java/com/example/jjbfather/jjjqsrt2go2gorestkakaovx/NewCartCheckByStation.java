package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NewCartCheckByStation extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public NewCartCheckByStation() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        String CHANNEL_ID = "NewCartCheckByStation";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("").setContentText("").build();
            startForeground(2,notification); // 추가
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForeground(1,new Notification()); // 추가
//        } else {
//
//        }

        //Thread mThread = new Thread(this);
        //mThread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            GlobalMemberValues.logWrite("newcartcheckbystatoinlog", "신규 New Cart By Station 체크서비스 시작" + "\n");
            check_newCartCheckByStation();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("newcartcheckbystatoinlog", "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }

    public void check_newCartCheckByStation() {
        int newCartCount = 0;
        if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STATION_CODE)) {
            String strQuery = "";

            // 해당 스테이션에서 어떤 작업을 수행중인지 확인
            // salon_selectedtableidx_bystation 테이블에 해당 스테이션 관련 데이터가 있는지 확인해서 있으면 활동중으로 인식한다.
            strQuery = " select count(*) from salon_selectedtableidx_bystation where stcode = '" + GlobalMemberValues.STATION_CODE + "' ";
            int tempStationCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));
            if (tempStationCnt > 0) {
                return;
            }

            // 선택된 zone 의 table idx
            String tempTableidx = "";
            String tableidx_s = "";
            strQuery = "select idx " +
                    " from salon_store_restaurant_table " +
                    " where zoneidx = '" + GlobalMemberValues.mGlobal_selectedZoneIdx + "' and deleteyn = 'N' and useyn = 'Y' " +
                    " order by idx asc";
            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(strQuery);
            int tempCnt = 0;
            while (dataCursor.moveToNext()) {
                tempTableidx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
                if (tempCnt > 0) {
                    tableidx_s += ",";
                }
                tableidx_s += tempTableidx;
                tempCnt++;
            }
            dataCursor.close();

            String tempStartUploadDay1 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -1);
            String tempStartUploadDay2 = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, 2);



            // 새롭게 담긴 메뉴가 있는지 체크 -----------------------------------------------------------------------------------------
            String addStrQuery1 = "";
            if (!GlobalMemberValues.isStrEmpty(TableSaleMain.mLastCheckCartIdx)) {
                addStrQuery1 = " and idx > " + TableSaleMain.mLastCheckCartIdx;
            }
            strQuery = " select count(distinct holdcode) from temp_salecart where " +
                    " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                    " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                    " and not(holdcode = 'NOHOLDCODE') " + addStrQuery1 +
                    " and replace(tableidx, 'T', '') in (" + tableidx_s + ") ";
            GlobalMemberValues.logWrite("wanhayejjjlog", "strQuery : " + strQuery + "\n");
            int newOrderCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

            if (newOrderCnt > 0) {
                Vector<String> strInsertQueryVec = new Vector<String>();
                String tempHoldCode = "";
                String tempCount = "";
                String insStrQuery = "";

                strQuery = " select distinct holdcode from temp_salecart where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " +
                        " and replace(tableidx, 'T', '') in (" + tableidx_s + ")  ";
                GlobalMemberValues.logWrite("wanhayejjjlog2", "strQuery : " + strQuery + "\n");

                GlobalMemberValues.logWrite("newcartchecklogjjjsql", "strQuery : " + strQuery + "\n");

                ResultSet getResult = MssqlDatabase.getResultSetValue(strQuery);
                try {
                    while (getResult.next()) {
                        tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult,0), 1);
                        if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                            strQuery = " select count(*) from salon_newcartcheck_bystation where " +
                                    " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                    " and delyn = 'N' ";
                            tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                            if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                insStrQuery = " insert into salon_newcartcheck_bystation (holdcode, stcode, delyn) values ( " +
                                        " '" + tempHoldCode + "', " +
                                        " '" + GlobalMemberValues.STATION_CODE + "', " +
                                        " '" + "N" + "' " +
                                        " ) ";
                                strInsertQueryVec.add(insStrQuery);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                newCartCount++;
                            }
                        }
                    }
                    getResult.close();
                    for (String tempQuery : strInsertQueryVec) {
                        GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "query : " + tempQuery + "\n");
                    }
                    // 트랜잭션으로 DB 처리한다.
                    if (strInsertQueryVec.size() > 0) {
                        String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                        if (returnResult == "N" || returnResult == "") {
                            //newCartCount = 0;
                            //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "데이터베이스 에러" + "\n");
                            return;
                        } else {
                        }
                    }

                    MssqlDatabase.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------

            // 삭제된 메뉴가 있는지 체크 ------------------------------------------------------------------------------------------------
            if (newCartCount == 0) {
                boolean tempbool = true;

                String addStrQuery2 = "";
                if (!GlobalMemberValues.isStrEmpty(TableSaleMain.mLastCheckCartIdx_Del)) {
                    addStrQuery2 = " and idx > " + TableSaleMain.mLastCheckCartIdx_Del;
                }
                strQuery = " select count(distinct holdcode) from temp_salecart_del2 where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " + addStrQuery2 +
                        " and replace(tableidx, 'T', '') in (" + tableidx_s + ") ";
                int newOrderDelCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                if (newOrderDelCnt > 0 && tempbool) {
                    Vector<String> strInsertQueryVec2 = new Vector<String>();
                    String tempHoldCode = "";
                    String tempCount = "";
                    String insStrQuery = "";

                    strQuery = " select distinct holdcode from temp_salecart_del2 where " +
                            " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                            " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "')" +
                            " and not(holdcode = 'NOHOLDCODE') " +
                            " and replace(tableidx, 'T', '') in (" + tableidx_s + ")  ";

                    GlobalMemberValues.logWrite("newcartchecklogjjjsql", "strQuery : " + strQuery + "\n");

                    ResultSet getResult2 = MssqlDatabase.getResultSetValue(strQuery);
                    try {
                        while (getResult2.next()) {
                            tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult2,0), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                                strQuery = " select count(*) from salon_newcartcheck_bystation where " +
                                        " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                        " and delyn = 'Y' ";
                                tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                                if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                    insStrQuery = " insert into salon_newcartcheck_bystation (holdcode, stcode, delyn) values ( " +
                                            " '" + tempHoldCode + "', " +
                                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                                            " '" + "Y" + "' " +
                                            " ) ";
                                    strInsertQueryVec2.add(insStrQuery);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    newCartCount++;
                                }
                            }
                        }
                        getResult2.close();
                        for (String tempQuery : strInsertQueryVec2) {
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        if (strInsertQueryVec2.size() > 0) {
                            String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec2);
                            if (returnResult == "N" || returnResult == "") {
                                //newCartCount = 0;
                                //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "데이터베이스 에러" + "\n");
                                return;
                            } else {
                            }
                        }

                        MssqlDatabase.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------


            // 04252023
            // 테이블 이동 있는지 체크 ---------------------------------------------------------------------------------
            if (newCartCount == 0) {
                strQuery = " select count(*) from salon_table_statuschange where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " +
                        " and ctype = 'move' ";
                GlobalMemberValues.logWrite("wanhayejjjlog", "strQuery : " + strQuery + "\n");
                int newChangeCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                if (newChangeCnt > 0) {
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String tempHoldCode = "";
                    String tempCount = "";
                    String insStrQuery = "";

                    strQuery = " select distinct holdcode from salon_table_statuschange where " +
                            " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                            " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                            " and not(holdcode = 'NOHOLDCODE') " +
                            " and ctype = 'move' ";
                    GlobalMemberValues.logWrite("wanhayejjjlog2", "strQuery : " + strQuery + "\n");

                    GlobalMemberValues.logWrite("newcartchecklogjjjsql", "strQuery : " + strQuery + "\n");

                    ResultSet getResult = MssqlDatabase.getResultSetValue(strQuery);
                    try {
                        while (getResult.next()) {
                            tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult,0), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                                strQuery = " select count(*) from salon_newcartcheck_bystation2 where " +
                                        " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                        " and ctype = 'move' " +
                                        " and delyn = 'N' ";
                                tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                                if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                    insStrQuery = " insert into salon_newcartcheck_bystation2 (holdcode, stcode, delyn, ctype) values ( " +
                                            " '" + tempHoldCode + "', " +
                                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                                            " '" + "N" + "', " +
                                            " '" + "move" + "' " +
                                            " ) ";
                                    strInsertQueryVec.add(insStrQuery);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    newCartCount++;
                                }
                            }
                        }
                        getResult.close();
                        for (String tempQuery : strInsertQueryVec) {
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        if (strInsertQueryVec.size() > 0) {
                            String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                            if (returnResult == "N" || returnResult == "") {
                                //newCartCount = 0;
                                //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "데이터베이스 에러" + "\n");
                                return;
                            } else {
                            }
                        }

                        MssqlDatabase.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------



            // 04252023
            // 테이블 merge 있는지 체크 ---------------------------------------------------------------------------------
            if (newCartCount == 0) {
                strQuery = " select count(*) from salon_table_statuschange where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " +
                        " and ctype = 'merge' ";
                GlobalMemberValues.logWrite("wanhayejjjlog", "strQuery : " + strQuery + "\n");
                int newChangeCnt2 = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                if (newChangeCnt2 > 0) {
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String tempHoldCode = "";
                    String tempCount = "";
                    String insStrQuery = "";

                    strQuery = " select distinct holdcode from salon_table_statuschange where " +
                            " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                            " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                            " and not(holdcode = 'NOHOLDCODE') " +
                            " and ctype = 'merge' ";
                    GlobalMemberValues.logWrite("wanhayejjjlog2", "strQuery : " + strQuery + "\n");

                    GlobalMemberValues.logWrite("newcartchecklogjjjsql", "strQuery : " + strQuery + "\n");

                    ResultSet getResult = MssqlDatabase.getResultSetValue(strQuery);
                    try {
                        while (getResult.next()) {
                            tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult,0), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                                strQuery = " select count(*) from salon_newcartcheck_bystation2 where " +
                                        " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                        " and ctype = 'merge' " +
                                        " and delyn = 'N' ";
                                tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                                if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                    insStrQuery = " insert into salon_newcartcheck_bystation2 (holdcode, stcode, delyn, ctype) values ( " +
                                            " '" + tempHoldCode + "', " +
                                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                                            " '" + "N" + "', " +
                                            " '" + "merge" + "' " +
                                            " ) ";
                                    strInsertQueryVec.add(insStrQuery);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    newCartCount++;
                                }
                            }
                        }
                        getResult.close();
                        for (String tempQuery : strInsertQueryVec) {
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        if (strInsertQueryVec.size() > 0) {
                            String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                            if (returnResult == "N" || returnResult == "") {
                                //newCartCount = 0;
                                //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "데이터베이스 에러" + "\n");
                                return;
                            } else {
                            }
                        }

                        MssqlDatabase.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            // ---------------------------------------------------------------------------------------------------------------------



            // 04252023
            // 테이블 split 있는지 체크 ---------------------------------------------------------------------------------
            if (newCartCount == 0) {
                strQuery = " select count(*) from salon_table_statuschange where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " +
                        " and ctype = 'split' ";
                GlobalMemberValues.logWrite("wanhayejjjlog", "strQuery : " + strQuery + "\n");
                int newChangeCnt3 = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                if (newChangeCnt3 > 0) {
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String tempHoldCode = "";
                    String tempCount = "";
                    String insStrQuery = "";

                    strQuery = " select distinct holdcode from salon_table_statuschange where " +
                            " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                            " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                            " and not(holdcode = 'NOHOLDCODE') " +
                            " and ctype = 'split' ";
                    GlobalMemberValues.logWrite("wanhayejjjlog2", "strQuery : " + strQuery + "\n");

                    GlobalMemberValues.logWrite("newcartchecklogjjjsql", "strQuery : " + strQuery + "\n");

                    ResultSet getResult = MssqlDatabase.getResultSetValue(strQuery);
                    try {
                        while (getResult.next()) {
                            tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult,0), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                                strQuery = " select count(*) from salon_newcartcheck_bystation2 where " +
                                        " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                        " and ctype = 'split' " +
                                        " and delyn = 'N' ";
                                tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                                if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                    insStrQuery = " insert into salon_newcartcheck_bystation2 (holdcode, stcode, delyn, ctype) values ( " +
                                            " '" + tempHoldCode + "', " +
                                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                                            " '" + "N" + "', " +
                                            " '" + "split" + "' " +
                                            " ) ";
                                    strInsertQueryVec.add(insStrQuery);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    newCartCount++;
                                }
                            }
                        }
                        getResult.close();
                        for (String tempQuery : strInsertQueryVec) {
                            GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        if (strInsertQueryVec.size() > 0) {
                            String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                            if (returnResult == "N" || returnResult == "") {
                                //newCartCount = 0;
                                //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "데이터베이스 에러" + "\n");
                                return;
                            } else {
                            }
                        }

                        MssqlDatabase.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------



            // 05032023
            // bill print 있는지 체크 ---------------------------------------------------------------------------------
            if (newCartCount == 0) {
                strQuery = " select count(*) from salon_table_statuschange where " +
                        " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                        " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                        " and not(holdcode = 'NOHOLDCODE') " +
                        " and ctype = 'billprint' ";
                int newChangeCnt4 = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(strQuery));

                GlobalMemberValues.logWrite("billprintchjjjlogcnt", "newChangeCnt4 : " + newChangeCnt4 + "\n");

                if (newChangeCnt4 > 0) {
                    Vector<String> strInsertQueryVec = new Vector<String>();
                    String tempHoldCode = "";
                    String tempCount = "";
                    String insStrQuery = "";

                    strQuery = " select distinct holdcode from salon_table_statuschange where " +
                            " wdate between '" + tempStartUploadDay1 + "' and '" + tempStartUploadDay2 + "'" +
                            " and not(stcode = '" + GlobalMemberValues.STATION_CODE + "') " +
                            " and not(holdcode = 'NOHOLDCODE') " +
                            " and ctype = 'billprint' ";
                    GlobalMemberValues.logWrite("billprintchjjjlog", "strQuery : " + strQuery + "\n");

                    ResultSet getResult = MssqlDatabase.getResultSetValue(strQuery);
                    try {
                        while (getResult.next()) {
                            tempHoldCode = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(getResult,0), 1);
                            if (!GlobalMemberValues.isStrEmpty(tempHoldCode)) {
                                strQuery = " select count(*) from salon_newcartcheck_bystation2 where " +
                                        " holdcode = '" + tempHoldCode + "' and stcode = '" + GlobalMemberValues.STATION_CODE + "' " +
                                        " and ctype = 'billprint' " +
                                        " and delyn = 'N' ";
                                tempCount = MssqlDatabase.getResultSetValueToString(strQuery);
                                GlobalMemberValues.logWrite("newcartcheckbystatoinlogjjj", "tempCount : " + tempCount + "\n");
                                if (GlobalMemberValues.getIntAtString(tempCount) == 0) {
                                    insStrQuery = " insert into salon_newcartcheck_bystation2 (holdcode, stcode, delyn, ctype) values ( " +
                                            " '" + tempHoldCode + "', " +
                                            " '" + GlobalMemberValues.STATION_CODE + "', " +
                                            " '" + "N" + "', " +
                                            " '" + "billprint" + "' " +
                                            " ) ";
                                    strInsertQueryVec.add(insStrQuery);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    newCartCount++;
                                } else {

                                }
                            }
                        }
                        getResult.close();
                        for (String tempQuery : strInsertQueryVec) {
                            GlobalMemberValues.logWrite("billprintchjjjlog", "query : " + tempQuery + "\n");
                        }
                        // 트랜잭션으로 DB 처리한다.
                        if (strInsertQueryVec.size() > 0) {
                            String returnResult = MssqlDatabase.executeTransaction(strInsertQueryVec);
                            if (returnResult == "N" || returnResult == "") {
                                //newCartCount = 0;
                                //GlobalMemberValues.displayDialog(context, "Warning", "Database Error", "Close");
                                GlobalMemberValues.logWrite("billprintchjjjlog", "데이터베이스 에러" + "\n");
                                return;
                            } else {
                            }
                        }

                        MssqlDatabase.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------

        }


        GlobalMemberValues.logWrite("newitemcountjjj", "newCartCount1 : " + newCartCount + "\n");
        if (newCartCount > 0) {
            GlobalMemberValues.logWrite("newitemcountjjj", "newCartCount2 : " + newCartCount + "\n");


            //((TableSaleMain)TableSaleMain.mContext).onResume();

            //((TableSaleMain)TableSaleMain.mContext).onWindowFocusChanged(true);
            TableSaleMain.onRefreshAtOutside();
            newCartCount = 0;

            GlobalMemberValues.logWrite("newcartchecklogjjj", "여기실행됨...2" + "\n");
        }

        newCartCount = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        } else {
            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION != null && GlobalMemberValues.CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION != null) {
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWCARDCHECKBYSTATION.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_NEWCARDCHECKBYSTATION);
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}