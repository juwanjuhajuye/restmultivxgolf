package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.util.Vector;

public class MssqlCreateTables {
    public static void createTablesForMSSQL() {
        Vector<String> vec = new Vector<String>();

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONINFO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICESUBSETMENU);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICESUBORDER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICESUB);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEMAINORDER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEMAIN);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPRODUCTIPKOHISTORY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPRODUCT);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPHOTO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPG);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREINFOWORKTIMEFORPOS);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREINFOWORKTIME);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREINFO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREGIFTCARD);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREGENERAL);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREEMPLOYEEWORKWEEKDAY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREEMPLOYEEWORKTIME);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREEMPLOYEELOGINLOG);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREEMPLOYEE);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPART);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREPARTEMPLOYEE);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONMEMBER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONINFO2);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONINFO1);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONGENERAL);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_RESERVATIONTIME);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_RESERVATIONSERVICE);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_RESERVATION);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_PGINFO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_MEMBER2);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_MEMBER1);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_MEMBERMILEAGE);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_COUPONREGISTRATIONNO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_COUPONMAILINGPUSHSEND);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_COUPONISSUEHISTORY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_COUPONIMGTYPE);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_COUPON);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_ADMIN);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREMEMOSEL);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEOPTIONBTN);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEOPTION);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEOPTIONITEM);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICEADDITIONAL);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICECOMMONMODIFIER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICECOMMONMODIFIERITEM);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREDELIVERYCOMPANY);

        // 안드로이드 포스에만 있는 테이블
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECART);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTORDERED);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTDEL);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREGIFTCARDNUMBER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREGIFTCARDNUMBERHISTORY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESALESDETAIL);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESALES);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESTIP);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONCLOCKINOUT);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SYNCDATALOG);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_BASICPOSINFORMATION);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCARD);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESALESRETURN);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSSYSTEM);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER2);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER3);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER4);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER5);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER6);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSPAYMENTGATEWAY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_MEMBER_MEMO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_CLOUDDBBACKUPLOG);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESBATCH);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESBATCHJSON);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECART_DELIVERYINFO);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESWEBPUSHREALTIME);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTOPTIONADD);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTOPTIONADDGM);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTOPTIONADDIMSI);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTOPTIONADDIMSIGM);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCASHINOUTHISTORY);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONEMPLOYEELOGINOUTHISTORY);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_MEMBERSALEVISIT);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORECASHINOUTREASON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCASHOUTJSON);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESENDOFDAYJSON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCASHOUTEMP);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONKITCHENPRINTINGDATA);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONCONNECTEDBLUETOOTHINFO);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCUSTOMERORDERNUMBER);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESPHONEORDERNUMBER);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESRECEIPTJSON);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESKITCHENJSON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCASHINOUTHISTORYLIST);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESERVICESUBTIMEMENUPRICE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESCUSTOMERPAGERNUMBER);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESSIGNEDIMG);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPMILEAGECART);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREDISCOUNTBUTTON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_KITCHENPRINTINGLOG);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORETARE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORERESTAURANTTABLE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORERESTAURANTTABLEZONE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORERESTAURANTTABLESPLIT);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORERESTAURANTTABLEPEOPLECNT);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORERESTAURANTTABLEMERGE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORECURBSIDEPICKUP);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORECURBSIDENEWSIDEMENU);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESALESRETURNBYEMPLOYEE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONNEWCARTCHECKBYSTATION);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSELECTEDTABLEIDXBYSTATION);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPSALECARTDEL2);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_BILLLIST);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_BILLLISTPAID);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_CARDPROCESSINGDATA);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESPECIALREQUEST);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL1);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL2);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL3);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL4);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL5);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSMSTEXTSAMPLE);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESTIPSPLIT);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_MASTER);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_bTNLOGS);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_BILLLISTRECEIPTJSON);

        vec.addElement(SqlStatements_mssql.SQL_CREATE_TEMPITEMPRINTING);

        // 04252023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONNEWCARTCHECKBYSTATION2);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONTABLESTATUSCHANGE);

        // 05012023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONBILLPRINTED);




        //05152023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_LABELPRINTEDJSON);


        //08082023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREEMPLOYEEROLE);


        //10092023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONBILLPRINTEDITEMQTY);


        // 10272023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONITEMDELETEREASON);


        // 11012023
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREITEMDELETEREASON);


        // 01172024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSONTORDER);


        // 02192024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREMEMBERLEVEL);

        // 10202024 -----------------------------------
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSONTORDERORIGIN);
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSONTORDERQUERY);
        // 10202024 -----------------------------------

        // 11152024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESTABLEORDERQRCODEINFO);

        // 05302024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSALESTOGODELIVERYFEE);

        // 07102024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_TORDERJSONDATA);


        // 07182024
        // 카드결제 기기등록관련
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONPGIP);

        // 08162024
        vec.addElement(SqlStatements_mssql.SQL_CREATE_SALONSTOREBREAKTIME);

        MssqlDatabase.executeTransaction(vec);
    }
}

