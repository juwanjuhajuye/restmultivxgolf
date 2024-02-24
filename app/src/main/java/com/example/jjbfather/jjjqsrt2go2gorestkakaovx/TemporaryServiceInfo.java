package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-13.
 */

public class TemporaryServiceInfo {
    public String svcIdx, svcMdx, svcServiceName, svcStrFileName, svcStrFilePath;
    public String svcPositionNo, svcServicePrice, svcCommissionRatioType, svcCommissionRatio;
    public String svcPointRatio, svcSalePrice, svcSaleStart, svcSaleEnd, svcSetMenuYN;
    public String svcCategoryName;
    public String svcCategoryColor;
    public String svcServiceNameForSearch;

    public TemporaryServiceInfo() {
        this.svcIdx = "";
        this.svcMdx = "";
        this.svcServiceName = "";
        this.svcStrFileName = "";
        this.svcStrFilePath = "";
        this.svcPositionNo = "";
        this.svcServicePrice = "";
        this.svcCommissionRatioType = "";
        this.svcCommissionRatio = "";
        this.svcPointRatio = "";
        this.svcSalePrice = "";
        this.svcSaleStart = "";
        this.svcSaleEnd = "";
        this.svcSetMenuYN = "";
        this.svcCategoryName = "";
        this.svcCategoryColor = "";
        this.svcServiceNameForSearch = "";
    }

    public TemporaryServiceInfo(String paramIdx, String paramMdx,
             String paramServiceName, String paramStrFileName, String paramStrFilePath, String paramPositionNo,
             String paramServicePrice, String paramCommissionRatioType, String paramCommissionRatio, String paramPointRatio,
             String paramSalePrice, String paramSaleStart, String paramSaleEnd, String paramSetMenuYN, String paramCategoryName, String paramServiceNameForSearch) {
        this.svcIdx = paramIdx;
        this.svcMdx = paramMdx;
        this.svcServiceName = GlobalMemberValues.getChangedStringAfterEnterStr(paramServiceName);
        this.svcStrFileName = paramStrFileName;
        this.svcStrFilePath = paramStrFilePath;
        this.svcPositionNo = paramPositionNo;
        this.svcServicePrice = paramServicePrice;
        this.svcCommissionRatioType = paramCommissionRatioType;
        this.svcCommissionRatio = paramCommissionRatio;
        this.svcPointRatio = paramPointRatio;
        this.svcSalePrice = paramSalePrice;
        this.svcSaleStart = paramSaleStart;
        this.svcSaleEnd = paramSaleEnd;
        this.svcSetMenuYN = paramSetMenuYN;
        this.svcCategoryName = paramCategoryName;
        this.svcServiceNameForSearch = paramServiceNameForSearch;
    }
}
