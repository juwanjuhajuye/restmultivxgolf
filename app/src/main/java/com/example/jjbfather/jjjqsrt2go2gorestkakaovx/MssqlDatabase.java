package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class MssqlDatabase {
    public static Connection conn;

    public static boolean tryConnect(boolean showMessage) {
        if (GlobalMemberValues.mssql_useyn == "N" || GlobalMemberValues.mssql_useyn.equals("N")) {
            return false;
        } else {
            try {
                GlobalMemberValues.logWrite("DBCONNECTLOG", "여기 진입...1" + "\n");
                if (conn != null && !conn.isClosed()) {
                    return true;
                }

                GlobalMemberValues.logWrite("DBCONNECTLOG", "여기 진입...2" + "\n");

                // TODO 어디선가 정보를 입력받아옴.
                //String dbIp = "110.234.17.236";   // 뒤에 :1443 은 입력하지 않는다.
                String dbIp = GlobalMemberValues.mssql_ip;   // 뒤에 :1443 은 입력하지 않는다.
                String dbName = GlobalMemberValues.mssql_db;
                String dbUser = GlobalMemberValues.mssql_id;
                String dbUserPass = GlobalMemberValues.mssql_pw;

                GlobalMemberValues.logWrite("mssqlsynclog", "dbIp : " + dbIp + "\n");

                MssqlConnection connClass = new MssqlConnection();
                conn = connClass.getConnection(dbUser, dbUserPass, dbName, dbIp);
                if (conn == null) {
                    if (showMessage)
                        GlobalMemberValues.logWrite("DBCONNECTLOG", "연결실패 .... : " + connClass.getLastErrMsg() + "\n");
                    showToast("연결실패 .... : " + connClass.getLastErrMsg());
                    return false;
                } else {
                    if (conn.isClosed()) {
                        if (showMessage)
                            GlobalMemberValues.logWrite("DBCONNECTLOG", "연결실패" + "\n");
                        showToast("DataBase 연결 실패.");
                        return false;
                    } else {
                        if (showMessage)
                            GlobalMemberValues.logWrite("DBCONNECTLOG", "연결성공!!!" + "\n");
                        showToast("DataBase 연결에 성공했습니다.");
                        return true;
                    }
                }
            } catch (SQLException e) {
                if (showMessage) {
                    showToast(e.getMessage());
                }
                e.printStackTrace();
                return false;
            }
        }
    }

    public static String executeTransaction(Vector<String> paramVec) {
        String returnvalue = "N";
        if (paramVec != null & paramVec.size() > 0) {
            if(MssqlDatabase.tryConnect(true) ){
                try {
                    //Switch to manual transaction mode by setting
                    //autocommit to false. Note that this starts the first
                    //manual transaction.
                    conn.setAutoCommit(false);
                    Statement stmt = conn.createStatement();

                    for (String tempQuery : paramVec) {
                        if (!GlobalMemberValues.isStrEmpty(tempQuery)) {
                            GlobalMemberValues.logWrite("WANHAYEEXEDBLOG3", "jjjquery : " + tempQuery + "\n");
                            if (tempQuery.indexOf("' :00'") > -1) {
                                tempQuery = GlobalMemberValues.getReplaceText(tempQuery, "' :00'", "getdate()");
                            }
                            if (tempQuery.contains("datetime('now', 'localtime')")){
                                tempQuery = tempQuery.replace("datetime('now', 'localtime')","getdate()");
                            }

                            stmt.executeUpdate(tempQuery);
                        }
                    }

                    conn.commit(); //This commits the transaction and starts a new one.
                    stmt.close(); //This turns off the transaction.

                    GlobalMemberValues.logWrite("WANHAYEEXEDBLOG2", "Transaction succeeded. Both records were written to the database." + "\n");

                    returnvalue = "Y";
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        GlobalMemberValues.logWrite("WANHAYEEXEDBLOG2", "Transaction failed." + "\n");
                        conn.rollback();
                        returnvalue = "Y";
                    }
                    catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            } else {
                GlobalMemberValues.logWrite("WANHAYEEXEDBLOG2", "연결실패..." + "\n");
            }
        } else {
            GlobalMemberValues.logWrite("WANHAYEEXEDBLOG2", "데이터없음" + "\n");
        }

        return returnvalue;
    }

    public static String executeTransactionByQuery(String paramQuery) {
        String returnvalue = "1";
        if (!GlobalMemberValues.isStrEmpty(paramQuery)) {
            if(MssqlDatabase.tryConnect(true) ){
                try {
                    //Switch to manual transaction mode by setting
                    //autocommit to false. Note that this starts the first
                    //manual transaction.
                    conn.setAutoCommit(false);
                    Statement stmt = conn.createStatement();

                    GlobalMemberValues.logWrite("DATATRANSLOG", "query : " + paramQuery + "\n");
                    stmt.executeUpdate(paramQuery);

                    conn.commit(); //This commits the transaction and starts a new one.
                    stmt.close(); //This turns off the transaction.

                    GlobalMemberValues.logWrite("DATATRANSLOG", "Transaction succeeded. Both records were written to the database." + "\n");

                    returnvalue = "0";
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        GlobalMemberValues.logWrite("DATATRANSLOG", "Transaction failed." + "\n");
                        conn.rollback();
                        returnvalue = "1";
                    }
                    catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        }

        return returnvalue;
    }

    public static ResultSet getResultSetValue(String paramQuery) {
        ResultSet returnvalue = null;
        if( MssqlDatabase.tryConnect(true) ){
            try {
                Statement tempStmt = MssqlDatabase.conn.createStatement();
                if (tempStmt != null) {
                    returnvalue = tempStmt.executeQuery(paramQuery);
                }
                //tempStmt.close();
                //MssqlDatabase.conn.close();
            }  catch (Exception e) {
                e.printStackTrace();
                GlobalMemberValues.logWrite("TestLog", "err3 : " + e.toString() + "\n");
            }
        }

        return returnvalue;
    }

    public static String getResultSetValueToString(String paramQuery) {
        String returnString = "";
        if( MssqlDatabase.tryConnect(true) ){
            try {
                Statement tempStmt = conn.createStatement();

                if (tempStmt != null) {
                    ResultSet tempRS = tempStmt.executeQuery(paramQuery);
                    if (tempRS != null) {
                        while(tempRS.next()){
                            returnString = GlobalMemberValues.getDBTextAfterChecked(tempRS.getString(1), 0);
                        }
                    }

                    tempRS.close();
                }

                tempStmt.close();
                //MssqlDatabase.conn.close();
            }  catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (returnString == null) returnString = "";

        return returnString;
    }

    public static void showToast(String text){
//        Toast.makeText(MainActivity.mContext.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    public static int getCount(ResultSet paramRs) {
        try {
            return paramRs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}