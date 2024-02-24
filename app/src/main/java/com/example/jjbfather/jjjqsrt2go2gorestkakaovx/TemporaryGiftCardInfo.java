package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-27.
 */

public class TemporaryGiftCardInfo {
    public String gcIdx, gcPrice, gcPointRatio, gcSalePrice, gcSaleStart;
    public String gcSaleEnd, gcStrFileName, gcStrFilePath, gcStrOrgFileName, gcName;

    public TemporaryGiftCardInfo() {
        this.gcIdx = "";
        this.gcPrice = "";
        this.gcPointRatio = "";
        this.gcSalePrice = "";
        this.gcSaleStart = "";
        this.gcSaleEnd = "";
        this.gcStrFileName = "";
        this.gcStrFilePath = "";
        this.gcStrOrgFileName = "";
        this.gcName = "";
    }

    public TemporaryGiftCardInfo(String[] tempArray) {
        this.gcIdx = tempArray[0];
        this.gcPrice = tempArray[1];
        this.gcPointRatio = tempArray[2];
        this.gcSalePrice = tempArray[3];
        this.gcSaleStart = tempArray[4];
        this.gcSaleEnd = tempArray[5];
        this.gcStrFileName = tempArray[6];
        this.gcStrFilePath = tempArray[7];
        this.gcStrOrgFileName = tempArray[8];
        this.gcName = tempArray[9];
    }
}
