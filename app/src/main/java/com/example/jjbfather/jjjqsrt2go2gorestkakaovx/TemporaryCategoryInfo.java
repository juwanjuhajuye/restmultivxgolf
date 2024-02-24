package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-12.
 */
public class TemporaryCategoryInfo {
    public String categoryName, categoryIdx, categoryColorNo, categoryPositionNo;

    public TemporaryCategoryInfo() {
        this.categoryIdx = "";
        this.categoryName = "";
        this.categoryColorNo = "";
        this.categoryPositionNo = "";
    }
    
    public TemporaryCategoryInfo(String cateIdx, String cateName, String cateColorNo, String catePositionNo) {
        this.categoryIdx = cateIdx;
        this.categoryName = cateName;
        this.categoryColorNo = cateColorNo;
        this.categoryPositionNo = catePositionNo;
    }
}
