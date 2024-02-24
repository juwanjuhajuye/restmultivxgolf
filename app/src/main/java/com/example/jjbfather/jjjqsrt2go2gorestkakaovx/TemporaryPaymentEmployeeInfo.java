package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-11-04.
 */
public class TemporaryPaymentEmployeeInfo {
    public String empIdx, empName, empAmount;
    public String empHoldCode;

    public TemporaryPaymentEmployeeInfo() {
        this.empIdx = "";
        this.empName = "";
        this.empAmount = "";
        this.empHoldCode = "";
    }

    public TemporaryPaymentEmployeeInfo(String[] tempArray) {
        this.empIdx = tempArray[0];
        this.empName = tempArray[1];
        this.empAmount = tempArray[2];
        this.empHoldCode = tempArray[3];
    }
}
