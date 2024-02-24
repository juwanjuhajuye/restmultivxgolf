package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.io.Serializable;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-15.
 */
public class TemporarySaleCart implements Serializable {
    String mHoldCode, mSidx, mStcode, mMidx, mSvcidx;
    String mSvcName, mSvcFileName, mSvcFilePath;
    String mSvcPositionNo, mSvcOrgPrice, mSvcSetMenuYN;
    String mSPrice, mSTax, mSQty;
    String mSPriceAmount, mSTaxAmount, mSTotalAmount;
    String mSCommission, mSPoint, mSCommissionAmount, mSPointAmount, mSSaleYN;
    String mCustomerId, mCustomerName, mCustomerPhoneNo, mSaveType;
    public String mEmpIdx;
    public String mEmpName;
    public String tempSaleCartIdx;
    String mQuickSaleYN;
    String mSvcCategoryName;
    String mGiftCardNumber, mGiftCardSavePrice;
    String selectedDcExtraPrice, selectedDcExtraType, selectedDcExtraAllEach, couponNumber;
    String selectedDcExtraParentTempSaleCartIdx;

    String mCommissionRatioType, mCommissionRatio, mPointRatio;
    String mSPriceBalAmount;
    String mTaxExempt;

    String mCategoryColor;

    String mRcode;

    String eachDcExtraStr;

    String optionTxt, optionprice;
    String additionalTxt1, additionalprice1;
    String additionalTxt2, additionalprice2;

    String allDcExtraPrice;
    String allDcExtraType;

    String staticMSPrice;

    String kitchenMemo;

    String discountButtonName;

    String modifieridx;

    String modifiercode;

    TemporarySaleCart selectedDcExtraParentTemporarySaleCart;

    // Discount/Extra 취소를 위한 변수
    String mSPriceBalAmount_org;
    String mSTaxAmount_org;
    String mSTotalAmount_org;
    String mSCommissionAmount_org;
    String mSPointAmount_org;

    // Quick Sale 일 경우 주방프린팅을 할지 여부
    String mQuickSaleKitchenPrintingYN;

    String mDcExtraForReturn;

    String mServiceType;

    public TemporarySaleCart(String[] tempArray) {
        this.mHoldCode = tempArray[0];                  // Hold Code
        this.mSidx = tempArray[1];                      // 스토어인덱스
        this.mStcode = tempArray[2];                    // 스테이션코드
        this.mMidx = tempArray[3];                      // 카테고리 인덱스
        this.mSvcidx = tempArray[4];                    // 서비스 인덱스

        this.mSvcName = tempArray[5];                   // 서비스 이름
        this.mSvcFileName = tempArray[6];               // 서비스 이미지 파일이름
        this.mSvcFilePath = tempArray[7];               // 서비스 이미지 파일경로

        this.mSvcPositionNo = tempArray[8];             // 서비스 포지션 번호
        this.mSvcOrgPrice = tempArray[9];               // 서비스 원가
        this.mSvcSetMenuYN = tempArray[10];             // 서비스 셋트메뉴 여부

        this.mSPrice = tempArray[11];                   // 단가 (판매가)
        this.mSTax = tempArray[12];                     // Tax
        this.mSQty = tempArray[13];                     // 수량

        this.mSPriceAmount = tempArray[14];             // 단가 합계 (단가 x 수량)
        this.mSTaxAmount = tempArray[15];               // 세금 합계 (세금 x 수량)
        this.mSTotalAmount = tempArray[16];             // 총액 (단가 합계 + 세금 합계)

        this.mSCommission = tempArray[17];              // 커미션
        this.mSPoint = tempArray[18];                   // 포인트
        this.mSCommissionAmount = tempArray[19];        // 커미션 합게 (커미션 x 수량)
        this.mSPointAmount = tempArray[20];             // 포인트 합계 (포인트 x 수량)
        this.mSSaleYN = tempArray[21];                  // 세일여부

        this.mCustomerId = tempArray[22];               // 회원아이디
        this.mCustomerName = tempArray[23];             // 회원이름
        this.mCustomerPhoneNo = tempArray[24];          // 회원 전화번호

        // 저장타입 (0 : 서비스      1 : 상품          2 : 기프트카드         3 : 전체할인)
        //        (8 : 할인(Discount)용 아이템 추가    9 : 추가(Extra)용 아이템 추가)
        this.mSaveType = tempArray[25];

        this.mEmpIdx = tempArray[26];                   // 직원 인덱스
        this.mEmpName = tempArray[27];                  // 직원 이름

        this.tempSaleCartIdx = tempArray[28];           // temp_salecart 인덱스

        this.mQuickSaleYN = tempArray[29];              // Quick Sale 서비스/상품 여부

        this.mSvcCategoryName = tempArray[30];          // 카테고리 이름

        if (tempArray.length > 31) {
            this.mGiftCardNumber = tempArray[31];       // 기프트 카드 번호
        } else {
            this.mGiftCardNumber = "";
        }

        if (tempArray.length > 32) {
            this.mGiftCardSavePrice = tempArray[32];        // 기프트 카드 원래 가격
        } else {
            this.mGiftCardSavePrice = "";
        }

        //GlobalMemberValues.logWrite("ArrayLength", "length : " + tempArray.length + "\n");

        // 선택서비스/상품/기프트의 할인/추가 금액
        if (tempArray.length > 33) {
            this.selectedDcExtraPrice = tempArray[33];
        } else {
            this.selectedDcExtraPrice = "";
        }
        // 선택서비스/상품/기프트의 할인/추가 여부
        if (tempArray.length > 34) {
            this.selectedDcExtraType = tempArray[34];
        } else {
            this.selectedDcExtraType = "";
        }
        // 선택서비스/상품/기프트의 Discount / Extra 종류
        // ( ALL : 전체      EACH : 단일 )
        if (tempArray.length > 35) {
            this.selectedDcExtraAllEach = tempArray[35];
        } else {
            this.selectedDcExtraAllEach = "";
        }
        // 쿠폰번호
        if (tempArray.length > 36) {
            this.couponNumber = tempArray[36];
        } else {
            this.couponNumber = "";
        }

        // 할인대상 서비스/상품/기프트카드의 tempSaleCartIdx
        if (tempArray.length > 37) {
            this.selectedDcExtraParentTempSaleCartIdx = tempArray[37];
        } else {
            this.selectedDcExtraParentTempSaleCartIdx = "";
        }

        if (tempArray.length > 38) {
            this.mCommissionRatioType = tempArray[38];
        } else {
            this.mCommissionRatioType = "";
        }
        if (tempArray.length > 39) {
            this.mCommissionRatio = tempArray[39];
        } else {
            this.mCommissionRatio = "";
        }
        if (tempArray.length > 40) {
            this.mPointRatio = tempArray[40];
        } else {
            this.mPointRatio = "";
        }
        if (tempArray.length > 41) {
            this.mSPriceBalAmount = tempArray[41];
        } else {
            this.mSPriceBalAmount = "";
        }
        if (tempArray.length > 42) {
            this.mTaxExempt = tempArray[42];
        } else {
            this.mTaxExempt = "";
        }

        allDcExtraPrice = "0";
        allDcExtraType = "";

        staticMSPrice = "";

        kitchenMemo = "";

        discountButtonName = "";

        selectedDcExtraParentTemporarySaleCart = null;

        this.mSPriceBalAmount_org = (GlobalMemberValues.getDoubleAtString(this.mSPriceBalAmount) / GlobalMemberValues.getIntAtString(mSQty)) + "";
        this.mSTaxAmount_org = (GlobalMemberValues.getDoubleAtString(this.mSTaxAmount) / GlobalMemberValues.getIntAtString(mSQty)) + "";
        this.mSTotalAmount_org = (GlobalMemberValues.getDoubleAtString(this.mSTotalAmount) / GlobalMemberValues.getIntAtString(mSQty)) + "";
        this.mSCommissionAmount_org = (GlobalMemberValues.getDoubleAtString(this.mSCommissionAmount) / GlobalMemberValues.getIntAtString(mSQty)) + "";
        this.mSPointAmount_org = (GlobalMemberValues.getDoubleAtString(this.mSPointAmount) / GlobalMemberValues.getIntAtString(mSQty)) + "";

        mQuickSaleKitchenPrintingYN = "N";

        mDcExtraForReturn = "0";

        mServiceType = "";
    }

}
