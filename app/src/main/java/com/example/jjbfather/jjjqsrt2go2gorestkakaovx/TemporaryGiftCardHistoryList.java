package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 ||
public class TemporaryGiftCardHistoryList {
    public String giftCardHistoryIdx;
    public String giftCardHistoryDate, giftCardHistoryAddUsePrice, giftCardHistoryAddUseType;

    public TemporaryGiftCardHistoryList() {
        this.giftCardHistoryIdx = "";
        this.giftCardHistoryDate = "";
        this.giftCardHistoryAddUsePrice = "";
        this.giftCardHistoryAddUseType = "";
    }

    public TemporaryGiftCardHistoryList(String[] tempArray) {
        this.giftCardHistoryIdx = tempArray[0];
        this.giftCardHistoryDate = tempArray[1];
        this.giftCardHistoryAddUsePrice = tempArray[2];
        this.giftCardHistoryAddUseType = tempArray[3];
    }
}
