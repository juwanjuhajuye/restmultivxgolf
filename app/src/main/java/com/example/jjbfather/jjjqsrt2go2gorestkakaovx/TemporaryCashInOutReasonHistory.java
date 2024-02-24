package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryCashInOutReasonHistory {
    public String reasonIdx;
    public String inouttype, inoutmoney, inoutreason;
    public String inoutdate;

    public TemporaryCashInOutReasonHistory() {
        this.reasonIdx = "";
        this.inouttype = "";
        this.inoutmoney = "";
        this.inoutreason = "";
        this.inoutdate = "";
    }

    public TemporaryCashInOutReasonHistory(String[] tempArray) {
        this.reasonIdx = tempArray[0];
        this.inouttype = tempArray[1];
        this.inoutmoney = tempArray[2];
        this.inoutreason = tempArray[3];
        this.inoutdate = tempArray[4];
    }
}
