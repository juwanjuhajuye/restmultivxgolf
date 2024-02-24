package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryCustomerMemoHistory {
    public String customerMemoIdx;
    public String customerMemoDate, customerMemoNote;

    public TemporaryCustomerMemoHistory() {
        this.customerMemoIdx = "";
        this.customerMemoDate = "";
        this.customerMemoNote = "";
    }

    public TemporaryCustomerMemoHistory(String[] tempArray) {
        this.customerMemoIdx = tempArray[0];
        this.customerMemoDate = tempArray[1];
        this.customerMemoNote = tempArray[2];
    }
}
