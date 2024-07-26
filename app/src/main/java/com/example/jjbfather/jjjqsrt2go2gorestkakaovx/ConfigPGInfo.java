package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.database.Cursor;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-04-18.
 */
public class ConfigPGInfo {
    public String cfcardchargesystemuse = "";            // 포스연동 카드단말기 사용유무
                                                                //    0 : 사용 (연동 터미널 및 독립 터미널 사용)
                                                                //    1 : 사용안함 (독립형 터미널만사용)

    public String cfpaymentgateway = "";                 // PG 종류
                                                                //    0 : Ingenico ICT220
                                                                //    1 : PAX POSLink

    public String cfpaymentgatewayid = "";               // PG ID
    public String cfpaymentgatewaypwd = "";              // PG PWD

    public String cftipuse = "";                         // 팁에 카드결제사용 여부
                                                                //    (0 : 사용      1 : 사용안함)

    public String cfnetworkip = "";                       // 네트워크 IP
    public String cfnetworkport = "";                     // 네트워크 PORT

    public String cfcommtype = "TCP";                       // Comm Type


    public ConfigPGInfo() {

        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);   // DatabaseInit 객체 생성
        String strQuery = "select cardchargesystemuse, paymentgateway, paymentgatewayid, paymentgatewaypwd, tipuse, " +
                " networkip1, networkip2, networkip3, networkip4, networkport, commtype, " +

                // 07182024
                // 카드결제 기기등록관련
                " pgdevicenum " +

                " from salon_storestationsettings_paymentgateway ";
        Cursor pgCursor = dbInit.dbExecuteRead(strQuery);
        if (pgCursor != null && pgCursor.getCount() > 0 && pgCursor.moveToFirst()) {
            cfcardchargesystemuse = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(0), 1);
            cfpaymentgateway = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(1), 1);
            cfpaymentgatewayid = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(2), 1);
            cfpaymentgatewaypwd = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(3), 1);
            cftipuse = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(4), 1);

            String tempIp1 = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(5), 1);
            String tempIp2 = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(6), 1);
            String tempIp3 = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(7), 1);
            String tempIp4 = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(8), 1);
            cfnetworkport = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(9), 1);
            cfnetworkip = tempIp1 + "." + tempIp2 + "." + tempIp3 + "." + tempIp4;

            int tempCommtypeInt = 1;
            String tempCommtypeTxt = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(10), 1);
            if (!GlobalMemberValues.isStrEmpty(tempCommtypeTxt)) {
                tempCommtypeInt = GlobalMemberValues.getIntAtString(tempCommtypeTxt);
            }
            cfcommtype = GlobalMemberValues.COMMTYPE[tempCommtypeInt];
            GlobalMemberValues.logWrite("paxcommsettinglog", "cfcommtype : " + cfcommtype + "\n");


            // 07182024 ----------------------------------------------------------------------------------
            // 카드결제 기기등록관련
            String pgdevicenum = GlobalMemberValues.getDBTextAfterChecked(pgCursor.getString(11), 1);
            String tempPGIP = "";
            String tempPGPort = "";
            if (!GlobalMemberValues.isStrEmpty(pgdevicenum)) {
                tempPGIP = GlobalMemberValues.getPGIP(pgdevicenum);
                tempPGPort = GlobalMemberValues.getPGPort(pgdevicenum);
            }
            if (!GlobalMemberValues.isStrEmpty(tempPGIP)) {
                cfnetworkip = tempPGIP;
            }

            if (!GlobalMemberValues.isStrEmpty(tempPGPort)) {
                cfnetworkport = tempPGPort;
            }
            // 07182024 ----------------------------------------------------------------------------------

            GlobalMemberValues.logWrite("paxcommsettinglogjjj", "cfnetworkip : " + cfnetworkip + "\n");
            GlobalMemberValues.logWrite("paxcommsettinglogjjj", "cfnetworkport : " + cfnetworkport + "\n");

        }
    }
}
