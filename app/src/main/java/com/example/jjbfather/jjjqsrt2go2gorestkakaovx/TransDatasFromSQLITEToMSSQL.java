package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

import java.util.Vector;

public class TransDatasFromSQLITEToMSSQL {
    String tableName = "";
    String[] columnsArr;
    String uniqueColumn = "";
    String orderbyColumn = "";

    public TransDatasFromSQLITEToMSSQL(String paramTableName, String[] paramArrColumn, String paramUniqueColumn, String paramOrderbyColumn) {
        tableName = paramTableName;
        columnsArr = paramArrColumn;
        uniqueColumn = paramUniqueColumn;
        orderbyColumn = paramOrderbyColumn;
    }

    public boolean transDatasExecute() {
        boolean returnData = false;

        if (!GlobalMemberValues.isStrEmpty(tableName) && (columnsArr != null && columnsArr.length > 0)
                && !GlobalMemberValues.isStrEmpty(orderbyColumn)) {

            Vector<String> dbVec = new Vector<String>();

            String selQuery = "";
            String insQuery = "";

            String selColumns = "";
            String insColumnDatas = "";

            int uniqueValueCnt = 0;

            for (int i = 0; i < columnsArr.length; i++) {
                if (i > 0) {
                    selColumns += ", ";
                }
                selColumns += columnsArr[i];
            }

            selQuery = " select " + selColumns + " from " + tableName + " order by " + orderbyColumn + " asc ";

            Cursor dataCursor = MainActivity.mDbInit.dbExecuteRead(selQuery);
            while (dataCursor.moveToNext()) {
                for (int j = 0; j < columnsArr.length; j++) {
                    if (j > 0) {
                        insColumnDatas += ", ";
                    }
                    insColumnDatas += "" + getDataOnColumn(dataCursor.getString(j)) + "";

                    if (!GlobalMemberValues.isStrEmpty(uniqueColumn)) {
                        if (columnsArr[j].toUpperCase().equals(uniqueColumn.toUpperCase())) {
                            uniqueValueCnt = GlobalMemberValues.getIntAtString(MssqlDatabase.getResultSetValueToString(
                                    " select count(*) from " + tableName + " where " + uniqueColumn + "'" + dataCursor.getString(j) + "'"
                            ));
                        }
                    } else {
                        uniqueValueCnt = 0;
                    }
                }

                if (uniqueValueCnt == 0) {
                    insQuery = " insert into ( " + selColumns + " ) values ( " + insColumnDatas + " )";

                    dbVec.add(insQuery);
                }

                insColumnDatas = "";
                uniqueValueCnt = 0;
            }
            dataCursor.close();

            for (String tempQuery : dbVec) {
                GlobalMemberValues.logWrite("TransDatasToMSSQLlogjjj", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MssqlDatabase.executeTransaction(dbVec);
            if (returnResult == "N" || returnResult == "") {
                // GlobalMemberValues.logWrite("torderquerydblogjjj", "query returned error");
                returnData = false;
            } else { // 정상처리일 경우
                returnData = true;
            }
        } else {
            returnData = false;
        }

        return returnData;
    }

    public String getDataOnColumn(String paramData) {
        String returnData = "";

        if (paramData != null){
            if (paramData.toLowerCase().equals("null")) {
                returnData = "" + paramData + "";
            } else {
                returnData = "'" + paramData + "'";
            }
        }



        return returnData;
    }

}
