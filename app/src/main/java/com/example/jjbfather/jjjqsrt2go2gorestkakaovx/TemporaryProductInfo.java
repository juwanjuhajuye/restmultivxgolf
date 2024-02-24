package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-27.
 */

public class TemporaryProductInfo {
    public String pdIdx, pdSidx, pdName, pdDescription, pdWdate, pdPrice;
    public String pdPosCode, pdDelyn, pdPointRatio, pdSalePrice;
    public String pdSaleStart, pdSaleEnd, pdStrFileName, pdStrFilePath, pdStrOrgFileName;
    public String pdActiveyn, pdProductCode;

    public TemporaryProductInfo() {
        this.pdIdx = "";
        this.pdSidx = "";
        this.pdName = "";
        this.pdDescription = "";
        this.pdWdate = "";
        this.pdPrice = "";
        this.pdPosCode = "";
        this.pdDelyn = "";
        this.pdPointRatio = "";
        this.pdSalePrice = "";
        this.pdSaleStart = "";
        this.pdSaleEnd = "";
        this.pdStrFileName = "";
        this.pdStrFilePath = "";
        this.pdStrOrgFileName = "";
        this.pdActiveyn = "";
        this.pdProductCode = "";
    }

    public TemporaryProductInfo(String[] tempArray) {
        this.pdIdx = tempArray[0];
        this.pdSidx = tempArray[1];
        this.pdName = tempArray[2];
        this.pdDescription = tempArray[3];
        this.pdWdate = tempArray[4];
        this.pdPrice = tempArray[5];
        this.pdPosCode = tempArray[6];
        this.pdDelyn = tempArray[7];
        this.pdPointRatio = tempArray[8];
        this.pdSalePrice = tempArray[9];
        this.pdSaleStart = tempArray[10];
        this.pdSaleEnd = tempArray[11];
        this.pdStrFileName = tempArray[12];
        this.pdStrFilePath = tempArray[13];
        this.pdStrOrgFileName = tempArray[14];
        this.pdActiveyn = tempArray[15];
        this.pdProductCode = tempArray[16];
    }
}
