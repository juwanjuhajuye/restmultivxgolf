package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryCustomerInfo {
    public String memIdx, memUid, memName, memPassword, memWdate, memGender;
    public String memPhone, memMobile, memBYear, memBMonth, memBday;
    public String memAddr1, memAddr2, memZip, memState, memCity;
    public String memMembershipCardNo, memMemo, memMileage;
    public String memLastvisitForSale, memcardnum, memmemmobile;
    public String memGrade, memGradePointRatio, memGradeName;

    public TemporaryCustomerInfo() {
        this.memIdx = "";
        this.memUid = "";
        this.memName = "";
        this.memPassword = "";
        this.memWdate = "";
        this.memGender = "";

        this.memPhone = "";
        this.memMobile = "";
        this.memBYear = "";
        this.memBMonth = "";
        this.memBday = "";

        this.memAddr1 = "";
        this.memAddr2 = "";
        this.memZip = "";
        this.memState = "";
        this.memCity = "";
        this.memMembershipCardNo = "";
        this.memMemo = "";
        this.memMileage = "";

        this.memLastvisitForSale = "";

        this.memcardnum = "";
        this.memmemmobile = "";

        this.memGrade = "";
        this.memGradePointRatio = "";
        this.memGradeName = "";

    }

    public TemporaryCustomerInfo(String[] tempArray) {
        this.memIdx = tempArray[0];
        this.memUid = tempArray[1];
        this.memName = tempArray[2];
        this.memPassword = tempArray[3];
        this.memWdate = tempArray[4];
        this.memGender = tempArray[5];

        this.memPhone = tempArray[6];
        this.memMobile = tempArray[7];
        this.memBYear = tempArray[8];
        this.memBMonth = tempArray[9];
        this.memBday = tempArray[10];

        this.memAddr1 = tempArray[11];
        this.memAddr2 = tempArray[12];
        this.memZip = tempArray[13];

        if (tempArray.length > 14) {
            this.memState = tempArray[14];
        }
        if (tempArray.length > 15) {
            this.memCity = tempArray[15];
        }
        if (tempArray.length > 16) {
            this.memMembershipCardNo = tempArray[16];
        }
        if (tempArray.length > 17) {
            this.memMemo = tempArray[17];
        }
        if (tempArray.length > 18) {
            this.memMileage = tempArray[18];
        }
        if (tempArray.length > 19) {
            this.memLastvisitForSale = tempArray[19];
        }
        if (tempArray.length > 20) {
            this.memcardnum = tempArray[20];
        }
        if (tempArray.length > 21) {
            this.memmemmobile = tempArray[21];
        }
        if (tempArray.length > 22) {
            this.memGrade = tempArray[22];
        }
        if (tempArray.length > 23) {
            this.memGradePointRatio = tempArray[23];
        }
        if (tempArray.length > 24) {
            this.memGradeName = tempArray[24];
        }
    }
}
