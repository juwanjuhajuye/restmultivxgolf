package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-04-18.
 */
public class ConfigDevicePrinterInfo {
    public String cfprintername = "";                            // 프린터종류
    public String cfautoreceipt = "";                            // 자동프린팅 여부 (0 : 자동프린팅    1 : 자동프린팅 하지 않음)
    public String cfreceiptfontsize = "";                        // 영수증 텍스트 폰트사이즈
    public String cfreceiptpapercount = "";                      // 영수증 출력 수량
    public String cfreceiptfooter = "";                          // 영수증 하단 정보

    public String cftenderuseforzerobalance = "";                // 금액 zero 시 결제(Tender) 사용여부
                                                                 // (0 : 제로금액 결제가능     1 : 제로금액 결제안됨)

    public String cfautodiscountuseforonlycash = "";             // 현금으로만 결제시 자동할인 여부
                                                                 // (0 : 할인사용         1 : 할인안함)

    public String cfautodiscountuseforonlycashrate = "";         // 현금으로만 결제시 자동할인 비율

    public String cfcustomercarddigitpositionalign = "";         // 고객카드에서 읽어올 코드값 시작부분
                                                                 // (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)

    public String cfcustomercarddigitpositionstart = "";         // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을
                                                                 // 선택하였을 경우의 시작값

    public String cfcustomercarddigitpositioncount = "";         // 고객카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을
                                                                 // 선택하였을 경우의 시작값부터 글자수)

    public String cfgiftcarddigitpositionalign = "";             // 기프트카드에서 읽어올 코드값 시작부분
                                                                 // (L : 왼쪽부터   M : 가운데부터    R : 오른쪽부터     D : 기본값)

    public String cfgiftcarddigitpositionstart = "";             // 기프트카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을
                                                                 // 선택하였을 경우의 시작값

    public String cfgiftcarddigitpositioncount = "";             // 기프트카드에서 읽어올 코드값 시작부분을 M(가운데부터) 을
                                                                 // 선택하였을 경우의 시작값부터 글자수)

    public String cfemployeecommissionpolicy = "";               // 직원 커미션 정책
                                                                 // 0 : 직원커미션만 적용
                                                                 // 1 : 서비스에 커미션이 적용되지 않았을 경우 직원커미션을 적용하고,
                                                                 // 서비스 커미션이 있을 경우 서비스커미션을 적용

    public String cfmanagerpwduseforvoid = "";                   // Void 시 매니저 비밀번호 입력여부
                                                                 // (0 : 비밀번호 입력          1 : 비밀번호 입력하지 않음)

    public String cfcashdraweronoffonsalemode = "";              // 세일모드에서 돈통 오픈버튼 배치여부
                                                                 // (0 : 배치         1 : 배치안함)

    public String cfnetworkprinterip = "";                       // 네트워크 프린터 IP
    public String cfnetworkprinterport = "";                     // 네트워크 프린터 PORT


    public ConfigDevicePrinterInfo() {
        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성

        String strQuery = "select printername, autoreceipt, receiptfontsize, receiptpapercount, receiptfooter, " +
                " tenderuseforzerobalance, autodiscountuseforonlycash, autodiscountuseforonlycashrate, " +
                " customercarddigitpositionalign, customercarddigitpositionstart, customercarddigitpositioncount, " +
                " giftcarddigitpositionalign, giftcarddigitpositionstart, giftcarddigitpositioncount, " +
                " employeecommissionpolicy, managerpwduseforvoid, cashdraweronoffonsalemode, " +
                " networkprinterip1, networkprinterip2, networkprinterip3, networkprinterip4, networkprinterport " +
                " from salon_storestationsettings_deviceprinter ";
        Cursor devicePrinterCursor = dbInit.dbExecuteRead(strQuery);
        if (devicePrinterCursor.getCount() > 0 && devicePrinterCursor.moveToFirst()) {
            cfprintername = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(0), 1);
            cfautoreceipt = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(1), 1);
            cfreceiptfontsize = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(2), 1);
            cfreceiptpapercount = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(3), 1);
            cfreceiptfooter = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(4), 1);
            cftenderuseforzerobalance = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(5), 1);
            cfautodiscountuseforonlycash = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(6), 1);
            cfautodiscountuseforonlycashrate = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(7), 1);
            cfcustomercarddigitpositionalign = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(8), 1);
            cfcustomercarddigitpositionstart = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(9), 1);
            cfcustomercarddigitpositioncount = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(10), 1);
            cfgiftcarddigitpositionalign = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(11), 1);
            cfgiftcarddigitpositionstart = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(12), 1);
            cfgiftcarddigitpositioncount = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(13), 1);
            cfemployeecommissionpolicy = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(14), 1);
            cfmanagerpwduseforvoid = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(15), 1);
            cfcashdraweronoffonsalemode = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(16), 1);

            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(17), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(18), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(19), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(20), 1);
            cfnetworkprinterip = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;
            cfnetworkprinterport = GlobalMemberValues.getDBTextAfterChecked(devicePrinterCursor.getString(21), 1);
        }
    }
}
