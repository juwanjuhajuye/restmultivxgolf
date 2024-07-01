package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-09-23.
 */
public class DatabaseHandler {

    /** 데이터베이스 객체를 보유하고 다룰 컨텍스트. */
    private Context context = null;

    /** 테이블을 생성할 수 있는 SQLite 데이터베이스 객체. */
    private SQLiteDatabase database = null;

    /**
     * DatabaseHandler 객체를 초기화. 생성될 데이터베이스 파일은 context 파라메터에 대입되는 객체가 보유.
     * @param dbName 생성할 데이터베이스 파일의 이름입니다. 이미 존재하는 파일이면, 데이터베이스 오픈.
     * @param context 데이터베이스를 보유하고 제어할 컨텍스트.
     */
    public DatabaseHandler(String dbName, Context context) {
        try {
            this.context = context;
            this.database = this.context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        } catch (Exception e) {
        }
    }

    /** 데이터 Insert / Update / Delete ***************************************************/
    public void executeWrite(String _query) {
        this.database.execSQL(_query);
    }
    /*************************************************************************************/

    /** 데이터 Insert / Update / Delete 후 결과값 리턴***************************************/
    public String executeWriteReturnValue(String _query) {
        String returnCode = "";

        this.database.beginTransaction();
        try {
            this.database.execSQL(_query);
            this.database.setTransactionSuccessful();
            returnCode = "0";
        } catch (Exception e) {
            returnCode = "1";
            GlobalMemberValues.logWrite("DATABASE_ERROR", "ERROR 메시지 : " + e.getMessage() + "\n");
            e.printStackTrace();
        } finally {
            this.database.endTransaction();
        }
        return returnCode;
    }
    /*************************************************************************************/

    /** 데이터 Insert / Update / Delete (Transaction 용) **********************************/
    public void executeWriteForTransaction(Vector<String> queryVec) {
        this.database.beginTransaction();
        try {
            for (String _query : queryVec) {
                this.database.execSQL(_query);
            }
            this.database.setTransactionSuccessful();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("DATABASE_ERROR", "ERROR 메시지 : " + e.getMessage() + "\n");
            e.printStackTrace();
        } finally {
            this.database.endTransaction();
        }
    }
    /*************************************************************************************/

    /** 데이터 Insert / Update / Delete (Transaction 용) **********************************/
    public String executeWriteForTransactionReturnResult(Vector<String> queryVec) {
        String returnResult = "N";

        this.database.beginTransaction();
        try {
            for (String _query : queryVec) {
                GlobalMemberValues.logWrite("WRITE_QUERY", "쿼리 : " + _query + "\n");
                this.database.execSQL(_query);
            }
            this.database.setTransactionSuccessful();
            returnResult = "Y";

            // mssql 연동일 경우
            if (GlobalMemberValues.isPossibleMssqlInfo()) {
                if (GlobalMemberValues.datadownloadingyn == "Y" || GlobalMemberValues.datadownloadingyn.equals("Y")) {
                    if (GlobalMemberValues.mssql_sync == "Y" || GlobalMemberValues.mssql_sync.equals("Y")) {
                        GlobalMemberValues.logWrite("mssqljjjlog2", "여기1" + "\n");
                        returnResult = MssqlDatabase.executeTransaction(queryVec);
                    }
                } else {
                    GlobalMemberValues.logWrite("mssqljjjlog2", "여기2" + "\n");
                    returnResult = MssqlDatabase.executeTransaction(queryVec);
                }
                GlobalMemberValues.logWrite("mssqljjjlog", "here..1" + "\n");
            }

        } catch (Exception e) {
            GlobalMemberValues.logWrite("DATABASE_ERROR", "ERROR 메시지 : " + e.getMessage() + "\n");
            e.printStackTrace();
            returnResult = "N";
        } finally {
            this.database.endTransaction();
        }

        return returnResult;
    }
    /*************************************************************************************/
    /** 데이터 Insert / Update / Delete (Transaction 용) **********************************/
    public String executeWriteForTransactionReturnResultOnlySqllite(Vector<String> queryVec) {
        String returnResult = "N";

        this.database.beginTransaction();
        try {
            for (String _query : queryVec) {
                GlobalMemberValues.logWrite("WRITE_QUERY", "쿼리 : " + _query + "\n");
                this.database.execSQL(_query);
            }
            this.database.setTransactionSuccessful();
            returnResult = "Y";

        } catch (Exception e) {
            GlobalMemberValues.logWrite("DATABASE_ERROR", "ERROR 메시지 : " + e.getMessage() + "\n");
            e.printStackTrace();
            returnResult = "N";
        } finally {
            this.database.endTransaction();
        }

        return returnResult;
    }
    /*************************************************************************************/

    /** 데이터 읽어오기 Reader ************************************************************/
//    public Cursor executeRead(String _query) {
//        if (_query == null) _query = "";
//        Cursor c = database.rawQuery(_query, null);
//        return c;
//    }
    public Cursor executeRead(String _query) {
        Cursor c = null;
        if (!GlobalMemberValues.isStrEmpty(_query) && database != null) {
            c = database.rawQuery(_query, null);
        }
        //c.close();
        return c;
    }
    /**********************************************************************/

    /**
     * 전달받은 DB테이블(tblName) 의 데이터를 삭제하는 메소드.
     */
    public void deleteTableData(String tblName) {
        //먼저 해당 테이블이 데이터베이스에 있는지 확인한다.

        String delQuery = "delete from " + tblName;
        this.database.execSQL(delQuery);
    }



    /**
     * 이 객체의 데이터베이스로부터 테이블의 이름들을 구하려면, 이 메소드를 호출.
     * 참고 사이트 - http://stackoverflow.com/questions/15383847/how-to-get-all-table-names-in-android-sqlite-database
     * @return 데이터베이스의 테이블들의 이름들을 담은 리스트 객체
     */
    public List<String> getTableNames() {
        List<String> tableNameList = new LinkedList<String>();
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tableNameList.add(c.getString(0));
                c.moveToNext();
            }
        }
        c.close();
        return tableNameList;
    }

    /**
     * 검색하는 테이블에 있는 컬럼명을 리스트 객체에 담는 메소드.
     */
    public List<String> getColumnNames(String tblName) {
        List<String> columnNames = new LinkedList<String>();
        Cursor c = database.rawQuery("pragma table_info(" + tblName + ")", null);

        // cursor 오류
        // c.close(); // onResume() 내부에서 닫거나 호출의 경우 Cursor 재 사용 시 오류 발생 가능성 .. 추후 수정할것.

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                columnNames.add(c.getString(1));
                c.moveToNext();
            }
        }

        c.close();
        return columnNames;
    }


    /**
     * 해당 테이블이 있는지 체크
     */
    public int getSchTableCount(String tblName) {
        int schCount = 0;
        Cursor c = database.rawQuery("Select Exists(SELECT name FROM sqlite_master WHERE type = 'table' and name = '" + tblName + "') as rowCount ", null);
        while (c.moveToNext()) {
            schCount += (Integer)c.getInt(0);
            //GlobalMemberValues.logWrite("DATACount", "Select Exists(SELECT name FROM sqlite_master WHERE type = 'table' and name = '\" + tblName + \"') as rowCount ");
        }
        c.close();
        return schCount;
    }

    /**
     * 테이블과 컬럼명으로 검색하여 해당 테이블에 검색하는 컬럼이 있는지 조사하는 메소드.
     * 리턴값이 0 이면 없음. 0 이상이면 해당 테이블에 검색하는 컬럼이 있음.
     */
    public int getSchTableColumnCount(String tblName, String clmName) {
        int schCount = 0;       // 컬럼명이 검색된 수 초기화

        List<String> listColumnNames = getColumnNames(tblName);
        for (String columnName : listColumnNames) {
            if(columnName.toString().trim().equals(clmName.toString().trim())) {
                schCount += 1;
                //GlobalMemberValues.logWrite("TCount", "증가 - " + columnName + ":" + clmName);
            } else {
                //GlobalMemberValues.logWrite("TCount", "증가안함 - " + columnName + ":" + clmName);
            }
        }

        return schCount;
    }


    /**
     * 사용한 데이터베이스를 닫는다.
     */
    public void close() {
        if (null != this.database) {
            this.database.close();
        }
    }

    //06272024
    /**  Open database ***************************************************/
    public void openDatabase(String dbName){
        this.database = this.context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

}
