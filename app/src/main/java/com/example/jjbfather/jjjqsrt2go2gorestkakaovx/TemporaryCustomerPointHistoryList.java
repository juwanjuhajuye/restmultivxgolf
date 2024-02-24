package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryCustomerPointHistoryList {
    public String customerPointHistoryIdx;
    public String customerPointHistoryDate, customerPointMcase, customerPointMileage;
    public String customerPointContents;

    public TemporaryCustomerPointHistoryList() {
        this.customerPointHistoryIdx = "";
        this.customerPointHistoryDate = "";
        this.customerPointMcase = "";
        this.customerPointMileage = "";
        this.customerPointContents = "";
    }

    public TemporaryCustomerPointHistoryList(String[] tempArray) {
        this.customerPointHistoryIdx = tempArray[0];
        this.customerPointMileage = tempArray[1];
        this.customerPointMcase = tempArray[2];
        this.customerPointHistoryDate = tempArray[3];
        this.customerPointContents = tempArray[4];
    }
}
