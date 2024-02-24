package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryClockInOut {
    public String idx, sidx, stcode, employeeIdx, employeeName;
    public String clockinDate, clockinDay, clockinTime;
    public String clockoutDate, clockoutDay, clockoutTime;
    public String workTimeAmount;
    public String clockinout_closeYN;
    public String count;


    public TemporaryClockInOut() {
        this.idx = "";
        this.sidx = "";
        this.stcode = "";
        this.employeeIdx = "";
        this.employeeName = "";

        this.clockinDate = "";
        this.clockinDay = "";
        this.clockinTime = "";

        this.clockoutDate = "";
        this.clockoutDay = "";
        this.clockoutTime = "";

        this.workTimeAmount = "";
        this.clockinout_closeYN = "";

        this.count = "";
    }

    public TemporaryClockInOut(String[] tempArray) {
        this.idx = tempArray[0];
        this.sidx = tempArray[1];
        this.stcode = tempArray[2];
        this.employeeIdx = tempArray[3];
        this.employeeName = tempArray[4];

        this.clockinDate = tempArray[5];
        this.clockinDay = tempArray[6];
        this.clockinTime = tempArray[7];

        this.clockoutDate = tempArray[8];
        this.clockoutDay = tempArray[9];
        this.clockoutTime = tempArray[10];

        this.workTimeAmount = tempArray[11];
        this.clockinout_closeYN = tempArray[12];

        this.count = tempArray[13];
    }
}
