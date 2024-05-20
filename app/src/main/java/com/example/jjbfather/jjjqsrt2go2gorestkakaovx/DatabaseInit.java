package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-06.
 */
public class DatabaseInit {
    private DatabaseHandler dbHandler;
    Context mContext;

    public DatabaseInit(Context context) {
        mContext = context;
        //DatabaseHandler 클래스로부터 데이터베이스 핸들객체 생성
        this.dbHandler = new DatabaseHandler(GlobalMemberValues.DATABASE_NAME, mContext);
        //GlobalMemberValues.logWrite("DatabaseInit", "생성자 내부\n");
    }

    /** 테이블 생성 및 초기화 메소드 ***************************************************************/
    public void initDatabaseTables() {
        /** 테이블 생성 ***************************************************************/
        /** 테이블 하나씩 생성할 때....................
         //클라우드 다운로드 관련 테이블 생성
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESTATIONINFO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESERVICESUBSETMENU);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESERVICESUBORDER);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESERVICESUB);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESERVICEMAINORDER);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTORESERVICEMAIN);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREPRODUCTIPKOHISTORY);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREPRODUCT);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREPHOTO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREPG);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREINFOWORKTIMEFORPOS);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREINFOWORKTIME);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREINFO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREGIFTCARD);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREGENERAL);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEEWORKWEEKDAY);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEEWORKTIME);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEELOGINLOG);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEE);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONMEMBER);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONINFO2);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONINFO1);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_SALONGENERAL);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_RESERVATIONTIME);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_RESERVATIONSERVICE);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_RESERVATION);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_PGINFO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_MEMBER2);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_MEMBER1);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_MEMBERMILEAGE);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_MEMBER_MEMO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_COUPONREGISTRATIONNO);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_COUPONMAILINGPUSHSEND);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_COUPONISSUEHISTORY);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_COUPONIMGTYPE);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_COUPON);
         this.dbHandler.executeWrite(SqlStatements.SQL_CREATE_ADMIN);

         /** 테이블을 벡터에 담아서 트랜잭션으로 테이블을 만들 때...................... **/
        // 테이블 생성 쿼리를 벡터(Vector) 컬렉션에 담는다.
        Vector<String> strCreateTableVec = new Vector<String>();
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONINFO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICESUBSETMENU);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICESUBORDER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICESUB);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEMAINORDER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEMAIN);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPRODUCTIPKOHISTORY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPRODUCT);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPHOTO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPG);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREINFOWORKTIMEFORPOS);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREINFOWORKTIME);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREINFO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREGIFTCARD);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREGENERAL);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEEWORKWEEKDAY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEEWORKTIME);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEELOGINLOG);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEE);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPART);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREPARTEMPLOYEE);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONMEMBER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONINFO2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONINFO1);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONGENERAL);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_RESERVATIONTIME);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_RESERVATIONSERVICE);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_RESERVATION);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_PGINFO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_MEMBER2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_MEMBER1);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_MEMBERMILEAGE);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_COUPONREGISTRATIONNO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_COUPONMAILINGPUSHSEND);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_COUPONISSUEHISTORY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_COUPONIMGTYPE);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_COUPON);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_ADMIN);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREMEMOSEL);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEOPTIONBTN);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEOPTION);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEOPTIONITEM);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICEADDITIONAL);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICECOMMONMODIFIER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICECOMMONMODIFIERITEM);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREDELIVERYCOMPANY);

        // 안드로이드 포스에만 있는 테이블
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECART);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTORDERED);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTDEL);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREGIFTCARDNUMBER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREGIFTCARDNUMBERHISTORY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESALESDETAIL);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESALES);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESTIP);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONCLOCKINOUT);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SYNCDATALOG);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_BASICPOSINFORMATION);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCARD);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESALESRETURN);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSSYSTEM);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER3);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER4);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER5);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER6);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSPAYMENTGATEWAY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_MEMBER_MEMO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_CLOUDDBBACKUPLOG);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESBATCH);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESBATCHJSON);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECART_DELIVERYINFO);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESWEBPUSHREALTIME);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTOPTIONADD);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTOPTIONADDGM);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTOPTIONADDIMSI);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTOPTIONADDIMSIGM);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCASHINOUTHISTORY);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONEMPLOYEELOGINOUTHISTORY);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_MEMBERSALEVISIT);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORECASHINOUTREASON);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCASHOUTJSON);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESENDOFDAYJSON);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCASHOUTEMP);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONKITCHENPRINTINGDATA);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONCONNECTEDBLUETOOTHINFO);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCUSTOMERORDERNUMBER);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESPHONEORDERNUMBER);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESRECEIPTJSON);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESKITCHENJSON);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCASHINOUTHISTORYLIST);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESERVICESUBTIMEMENUPRICE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESCUSTOMERPAGERNUMBER);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESSIGNEDIMG);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPMILEAGECART);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREDISCOUNTBUTTON);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_KITCHENPRINTINGLOG);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORETARE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORERESTAURANTTABLE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORERESTAURANTTABLEZONE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORERESTAURANTTABLESPLIT);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORERESTAURANTTABLEPEOPLECNT);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORERESTAURANTTABLEMERGE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORECURBSIDEPICKUP);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORECURBSIDENEWSIDEMENU);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSON);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESALESRETURNBYEMPLOYEE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPSALECARTDEL2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONNEWCARTCHECKBYSTATION);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_BILLLIST);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_BILLLISTPAID);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_CARDPROCESSINGDATA);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESPECIALREQUEST);

        // SQL_CREATE_SALONSTORE_SETTINGS_SYSTEM_RECEIPT
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORE_SETTINGS_SYSTEM_RECEIPT);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL1);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL3);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL4);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL5);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSMSTEXTSAMPLE);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESTIPSPLIT);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_MASTER);

        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_bTNLOGS);

        // 01052023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_BILLLISTRECEIPTJSON);

        // 01062023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_TEMPITEMPRINTING);

        // 04252023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONNEWCARTCHECKBYSTATION2);
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONTABLESTATUSCHANGE);

        // 05012023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONBILLPRINTED);



        // 05152023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_LABELPRINTEDJSON);

        // 08082023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREEMPLOYEEROLE);

        // 10092023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONBILLPRINTEDITEMQTY);

        // 10272023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONITEMDELETEREASON);

        // 11012023
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREITEMDELETEREASON);

        // 01172024
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSONTORDER);

        // 02192024
        strCreateTableVec.addElement(SqlStatements.SQL_CREATE_SALONSTOREMEMBERLEVEL);


        // 테이블 컬럼 수정, 삭제 등 처리
        // ...................................


        this.dbHandler.executeWriteForTransaction(strCreateTableVec);

        /****************************************************************************/

        /** 테이블 수정, 삭제 및 컬럼 추가, 삭제 ****************************************/
        upDelDatabaseTables();
        /****************************************************************************/
    }
    /*****************************************************************************************/

    /** 테이블 생성 및 초기화 메소드 ***************************************************************/
    public void upDelDatabaseTables() {
        /** 테이블 추가 **************************************************************/

        /****************************************************************************/



        /** 테이블 삭제 **************************************************************/

        /****************************************************************************/



        /** 컬럼 추가 ****************************************************************/
        addTableColumns();
        /****************************************************************************/

        /** 컬럼 수정 ****************************************************************/
        alterTableColumn();
        /****************************************************************************/


        /** 컬럼 삭제 ****************************************************************/

        /****************************************************************************/
    }


    public void addTableColumns() {
        /** 컬럼 추가 ****************************************************************/
        String altTableName = "";
        // salon_storeemployee 테이블 컬럼 추가
        // 12.12.2016, commissionratio 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "commissionratio", "money", "NULL", 0);
            alterDatabaseTableColumn(altTableName, "clockinoutpwd", "nvarchar(50)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.12.2016, commisionratioType 컬럼 추가
        /*altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "commisionratioType", "smallint", "DEFAULT 0", 0);
        }*/

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 12.17.2016, voidprint 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "voidprint", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 12.17.2016, returnprint 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "returnprint", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 12.17.2016, returnprint 컬럼 추가
        altTableName = "member2";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "email", "nvarchar(200)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.7.2017, departmentviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "departmentviewyn", "nvarchar(2)", "NULL", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 2.7.2017, poslistviewyn 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "poslistviewyn", "nvarchar(2)", "NULL", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 2.22.2017, signpaduseyn 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "signpaduseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.24.2017, printlanguage 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "printlanguage", "nvarchar(4)", "DEFAULT 'US'", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 5.19.2017, returnCode 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "returnCode", "nvarchar(100)", "NULL", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 5.24.2017, cardComp 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardCom", "nvarchar(50)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerId 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerId", "nvarchar(200)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerName 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerName", "nvarchar(200)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerPhone 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerPhone", "nvarchar(200)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerEmail 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerEmail", "nvarchar(300)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerAddr1 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerAddr1", "nvarchar(200)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerAddr2 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerAddr2", "nvarchar(200)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerCity 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerCity", "nvarchar(100)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerState 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerState", "nvarchar(20)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customerZip 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerZip", "nvarchar(20)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, customermemo 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customermemo", "ntext", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, deliveryday 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliveryday", "nvarchar(10)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, deliverytime 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverytime", "nvarchar(10)", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, deliverydate 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverydate", "datetime", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.26.2017, deliverytakeaway 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverytakeaway", "nvarchar(10)", "DEFAULT 'D'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.28.2017, deliveryStatus 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliveryStatus", "smallint", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 6.28.2017, takeawayStatus 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "takeawayStatus", "smallint", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 7.6.2017, takeawayStatus 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 7.10.2017, commissionratio 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "commissionratio", "money", "DEFAULT 0", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 7.10.2017, pointratio 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pointratio", "money", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.21.2017, mileagesyncinselectcustomer 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mileagesyncinselectcustomer", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, optionTxt 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optionTxt", "nvarchar(300)", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, optionprice 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optionprice", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, additionalTxt1 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalTxt1", "nvarchar(300)", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, additionalprice1 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalprice1", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, additionalTxt2 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalTxt2", "nvarchar(300)", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.2.2017, additionalprice1 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalprice2", "money", "DEFAULT 0", 0);
        }



        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, optionTxt 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optionTxt", "nvarchar(300)", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, optionprice 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optionprice", "money", "DEFAULT 0", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, additionalTxt1 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalTxt1", "nvarchar(300)", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, additionalprice1 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalprice1", "money", "DEFAULT 0", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, additionalTxt2 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalTxt2", "nvarchar(300)", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.2.2017, additionalprice1 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "additionalprice2", "money", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.1.2017, showcostafterdcextra 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "showcostafterdcextra", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.1.2017, cardislast 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardislast", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_member 테이블 컬럼 추가
        // 9.19.2017, mileage 컬럼 추가
        altTableName = "salon_member";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mileage", "money", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.19.2017, pointpaysavepointyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pointpaysavepointyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.19.2017, pointpaysavepointyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pointpaysavepointyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 10.30.2017, employeeIdx 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "employeeIdx", "int", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 10.30.2017, employeeName 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "employeeName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 10.30.2017, employeeIdx 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "employeeIdx", "int", "NULL", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 10.30.2017, employeeName 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "employeeName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 10.30.2017, cashoutNum 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashoutNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 10.30.2017, cashoutNum 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashoutNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 10.30.2017, cashoutNum 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashoutNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 10.30.2017, cashoutNum 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashoutNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_cashintout_history 테이블 컬럼 추가
        // 10.30.2017, scode 컬럼 추가
        altTableName = "salon_sales_cashintout_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scode", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_employee_loginout_history 테이블 컬럼 추가
        // 10.30.2017, scode 컬럼 추가
        altTableName = "salon_employee_loginout_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scode", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.23.2017, timemenuyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timemenuyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.23.2017, timemenutime 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timemenutime", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, mstart 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mstart", "smallint", "DEFAULT 6", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, mend 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mend", "smallint", "DEFAULT 12", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, astart 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "astart", "smallint", "DEFAULT 12", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, aend 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "aend", "smallint", "DEFAULT 18", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, estart 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "estart", "smallint", "DEFAULT 18", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, eend 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "eend", "smallint", "DEFAULT 24", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, nstart 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "nstart", "smallint", "DEFAULT 24", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.23.2017, nend 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "nend", "smallint", "DEFAULT 6", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.24.2017, timemenuautoreload 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timemenuautoreload", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.13.2017, tipselectonsignature 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipselectonsignature", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.13.2017, tipselect1 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipselect1", "money", "DEFAULT 5.0", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.13.2017, tipselect2 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipselect2", "money", "DEFAULT 10.0", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.13.2017, tipselect3 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipselect3", "money", "DEFAULT 15.0", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.13.2017, tipselect4 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipselect4", "money", "DEFAULT 20.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 12.19.2017, takeawayyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "takeawayyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 12.19.2017, deliveryyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliveryyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 12.19.2017, deliverycharge 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverycharge", "money", "DEFAULT 0.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 12.19.2017, deliveryfree 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliveryfree", "money", "DEFAULT 0.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 12.25.2017, pickupcharge 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pickupcharge", "money", "DEFAULT 0.0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 12.27.2017, deliverypickupfee 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverypickupfee", "money", "DEFAULT 0", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 12.27.2017, permission 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "permission", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 1.21.2018, pushtype 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pushtype", "nvarchar(2)", "DEFAULT '1'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.14.2018, devicekind 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "devicekind", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storestationinfo 테이블 컬럼 추가
        // 2.19.2018, pushreceiveyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pushreceiveyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.15.2018, picktype_here 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_here", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.15.2018, picktype_togo 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_togo", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.15.2018, picktype_delivery 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_delivery", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 3.16.2018, tiplineonreceipt 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tiplineonreceipt", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.17.2018, customerinfoshow 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerinfoshow", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.26.2018, picktype_here 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_here", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.26.2018, picktype_togo 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_togo", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.26.2018, picktype_delivery 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "picktype_delivery", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 3.29.2018, empcardnum 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "empcardnum", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.31.2018, customerselectreceipt 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerselectreceipt", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 4.4.2018, signatureprintyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "signatureprintyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.8.2018, empcardstartnum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "empcardstartnum", "smallint", "DEFAULT 1", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.8.2018, empcardcountnum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "empcardcountnum", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.8.2018, giftcardstartnum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "giftcardstartnum", "smallint", "DEFAULT 1", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.8.2018, giftcardcountnum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "giftcardcountnum", "smallint", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 4.17.2018, checkcompany 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "checkcompany", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 4.17.2018, checkcompany 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "checkcompany", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_store_deliveryappcompany 테이블 컬럼 추가
        // 4.24.2018, delyn 컬럼 추가
        altTableName = "salon_store_deliveryappcompany";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "delyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 4.25.2018, modifiertype 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifiertype", "nvarchar(2)", "DEFAULT 'A'", 0);
        }

        // temp_salecart_deliveryinfo 테이블 컬럼 추가
        // 4.27.2018, phoneorder 컬럼 추가
        altTableName = "temp_salecart_deliveryinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "phoneorder", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 4.27.2018, phoneorder 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "phoneorder", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // temp_salecart_deliveryinfo 테이블 컬럼 추가
        // 4.28.2018, phoneordernumber 컬럼 추가
        altTableName = "temp_salecart_deliveryinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "phoneordernumber", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // temp_salecart_deliveryinfo 테이블 컬럼 추가
        // 4.28.2018, kitchenprintedyn 컬럼 추가
        altTableName = "temp_salecart_deliveryinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintedyn", "nvarchar(20)", "DEFAULT 'N'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 4.28.2018, phoneorder 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "phoneorder", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 5.3.2018, kitchenprintyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 5.10.2018, modifierprintyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifierprintyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 5.18.2018, signatureprintseperately 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "signatureprintseperately", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 5.24.2018, customerordernumber 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerordernumber", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 5.30.2017, kitchenprintnum 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintnum", "nvarchar(40)", "DEFAULT '1'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.30.2018, kitchenprintsu 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintsu", "int", "DEFAULT 1", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.19.2018, cashpaduse 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashpaduse", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 7.25.2018, cashdraweropenonreceipt 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashdraweropenonreceipt", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.27.2018, cloudkitchenprinteryn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cloudkitchenprinteryn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.27.2018, cloudkitchenprinterurl 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cloudkitchenprinterurl", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.9.2018, memoToKitchen 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "memoToKitchen", "ntext", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.12.2018, memoToKitchen 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "memoToKitchen", "ntext", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 8.27.2018, autoreceiptprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "autoreceiptprintingyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.6.2018, couponinfoview 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "couponinfoview", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.11.2018, onlineorderuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "onlineorderuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.13.2018, weekdays 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "weekdays", "nvarchar(30)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.14.2018, weekdays 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "weekdays", "nvarchar(30)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.18.2018, timemenuuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timemenuuseyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.27.2018, dcapplyyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcapplyyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // coupon 테이블 컬럼 추가
        // 9.27.2018, applytype 컬럼 추가
        altTableName = "coupon";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "applytype", "nvarchar(2)", "DEFAULT 'B'", 0);
        }

        // coupon 테이블 컬럼 추가
        // 9.27.2018, coupontype 컬럼 추가
        altTableName = "coupon";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "coupontype", "nvarchar(2)", "DEFAULT 'S'", 0);
        }

        // coupon 테이블 컬럼 추가
        // 9.27.2018, mcouponnumber 컬럼 추가
        altTableName = "coupon";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mcouponnumber", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.27.2018, couponimmediately 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "couponimmediately", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storeproduct 테이블 컬럼 추가
        // 9.28.2018, dcapplyyn 컬럼 추가
        altTableName = "salon_storeproduct";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcapplyyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.2.2018, timemenuchecktime 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timemenuchecktime", "smallint", "DEFAULT 10", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 10.22.2018, servicename2 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "servicename2", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 10.22.2018, menusu 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "menusu", "int", "DEFAULT 60", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 10.22.2018, taxfreeyn 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxfreeyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 10.22.2018, servicename2 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "servicename2", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 10.22.2018, servicename3 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "servicename3", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 10.22.2018, colorNo 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "colorNo", "smallint", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 10.27.2018, customerpagernumber 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerpagernumber", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.27.2018, maxpagernum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "maxpagernum", "smallint", "DEFAULT 50", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.27.2018, pageruseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pageruseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.30.2018, pagernumberautoyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pagernumberautoyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storeservice_option_btn 테이블 컬럼 추가
        // 11.6.2018, ignoreprice 컬럼 추가
        altTableName = "salon_storeservice_option_btn";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ignoreprice", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 12.1.2018, signatureimagedeletedaysago 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "signatureimagedeletedaysago", "nvarchar(6)", "DEFAULT '180'", 0);
        }

        // salon_sales_signedimg 테이블 컬럼 추가
        // 12.1.2018, delyn 컬럼 추가
        altTableName = "salon_sales_signedimg";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "delyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // member_mileage 테이블 컬럼 추가
        // 12.3.2018, savetype 컬럼 추가
        altTableName = "member_mileage";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "savetype", "nvarchar(2)", "DEFAULT 'P'", 0);
        }

        // member_mileage 테이블 컬럼 추가
        // 12.3.2018, isCloudUpload 컬럼 추가
        altTableName = "member_mileage";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0", 0);
        }

        // member_mileage 테이블 컬럼 추가
        // 12.3.2018, idxfromweb 컬럼 추가
        altTableName = "member_mileage";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "idxfromweb", "int", "NULL", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 12.5.2018, isCloudUpload 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0", 0);
        }

        // salon_storeproduct 테이블 컬럼 추가
        // 12.7.2018, onhand 컬럼 추가
        altTableName = "salon_storeproduct";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "onhand", "int", "DEFAULT 0", 0);
        }

        // salon_storeproduct 테이블 컬럼 추가
        // 12.7.2018, productalertqty 컬럼 추가
        altTableName = "salon_storeproduct";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "productalertqty", "int", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.7.2018, productminalertyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "productminalertyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_member 테이블 컬럼 추가
        // 12.29.2018, memcardnum 컬럼 추가
        altTableName = "salon_member";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "memcardnum", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_member 테이블 컬럼 추가
        // 12.29.2018, memmobile 컬럼 추가
        altTableName = "salon_member";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "memmobile", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 2.1.2019, cardmerchantreceiptyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardmerchantreceiptyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 2.1.2019, minpayforsign 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "minpayforsign", "money", "DEFAULT 0.0", 0);
        }

        // salon_general 테이블 컬럼 추가
        // 2.9.2019, franchise 추가
        altTableName = "salon_general";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "franchiseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 2.9.2019, storetype 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "storetype", "nvarchar(4)", "DEFAULT 'J1'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 2.9.2019, franchise 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "franchiseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.10.2019, daystouploaddata 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "daystouploaddata", "nvarchar(6)", "DEFAULT '10'", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 2.15.2019, codeforupload 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "codeforupload", "nvarchar(100)", "NULL", 0);
        }

        // member_mileage 테이블 컬럼 추가
        // 2.15.2019, codeforupload 컬럼 추가
        altTableName = "member_mileage";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "codeforupload", "nvarchar(100)", "NULL", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 2.16.2019, firstsaveyn 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "firstsaveyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 2.16.2019, giftcardname 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "giftcardname", "nvarchar(100)", "NULL", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 2.18.2019, codeforupload 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "codeforupload", "nvarchar(100)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.19.2019, batchincashoutyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "batchincashoutyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.19.2019, startingcashprintyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "startingcashprintyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storediscountbutton 테이블 컬럼 추가
        // 2.26.2019, name 추가
        altTableName = "salon_storediscountbutton";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "name", "nvarchar(100)", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.27.2019, discountbuttonname 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "discountbuttonname", "nvarchar(100)", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 2.27.2019, discountbuttonname 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "discountbuttonname", "nvarchar(100)", "NULL", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.1.2019, ordercompletesmsyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordercompletesmsyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.1.2019, ordercompletesmsremain 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordercompletesmsremain", "int", "DEFAULT 0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.1.2019, ordercompletesmsterm 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordercompletesmsterm", "int", "DEFAULT 0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.1.2019, togotaxfreeyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotaxfreeyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.2.2019, togotaxfreetype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotaxfreetype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.3.2019, ordernumbersectionviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordernumbersectionviewyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.3.2019, printfontsize 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintfontsize", "nvarchar(2)", "DEFAULT 'R'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 3.4.2019, kitchenordernumbersectionviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenordernumbersectionviewyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 3.17.2019, ischeckbeforecardpay 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ischeckbeforecardpay", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.19.2019, taxtype 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxtype", "nvarchar(2)", "DEFAULT 'S'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.19.2019, taxvalues 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxvalues", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.20.2019, taxexemptvalues 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxexemptvalues", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.20.2019, mobileprice 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mobileprice", "money", "DEFAULT 0.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, heretaxfreeyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "heretaxfreeyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, heretaxfreetype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "heretaxfreetype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, deliverytaxfreeyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverytaxfreeyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, deliverytaxfreetype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "deliverytaxfreetype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, dineintaxfreeyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dineintaxfreeyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, dineintaxfreetype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dineintaxfreetype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, mobiletaxfreeyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mobiletaxfreeyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.20.2019, mobiletaxfreetype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mobiletaxfreetype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.21.2019, taxexemptbytotalyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxexemptbytotalyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.28.2019, modifierviewtype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifierviewtype", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.2.2019, saledatemodifyyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "saledatemodifyyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.5.2019, salehistorypageno 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "salehistorypageno", "smallint", "DEFAULT 30", 0);
        }

        // temp_salecart_optionadd 테이블 컬럼 추가
        // 4.8.2019, optioncategoryname 컬럼 추가
        altTableName = "temp_salecart_optionadd";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optioncategoryname", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // temp_salecart_optionadd 테이블 컬럼 추가
        // 4.8.2019, optioncategoryidx 컬럼 추가
        altTableName = "temp_salecart_optionadd";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optioncategoryidx", "int", "NULL", 0);
        }

        // temp_salecart_optionadd 테이블 컬럼 추가
        // 4.8.2019, optionitemidx 컬럼 추가
        altTableName = "temp_salecart_optionadd";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "optionitemidx", "int", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 4.9.2019, modifieridx 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifieridx", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // temp_salecart_optionadd 테이블 컬럼 추가
        // 4.10.2019, modifiercode 컬럼 추가
        altTableName = "temp_salecart_optionadd";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifiercode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 4.10.2019, modifiercode 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifiercode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.23.2019, buttonmodifierbottomviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "buttonmodifierbottomviewyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.23.2018, receipttypeonnoselect 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "receipttypeonnoselect", "nvarchar(2)", "DEFAULT 'A'", 0);
        }

        // salon_storeservice_option 테이블 컬럼 추가
        // 4.24.2019, autoviewinposyn 컬럼 추가
        altTableName = "salon_storeservice_option";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "autoviewinposyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 4.24.2019, taxexemptprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxexemptprintingyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.6.2019, modifierpriceviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifierpriceviewyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.13.2019, pagernumberheadertxt 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pagernumberheadertxt", "nvarchar(40)", "DEFAULT '#'", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.16.2019, sPriceBalAmount_org 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sPriceBalAmount_org", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.16.2019, sTaxAmount_org 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sTaxAmount_org", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.16.2019, sTotalAmount_org 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sTotalAmount_org", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.16.2019, sCommissionAmount_org 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sCommissionAmount_org", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.16.2019, sPointAmount_org 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sPointAmount_org", "money", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.17.2019, dualdisplaypossible 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dualdisplaypossible", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 5.23.2019, commtype 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "commtype", "smallint", "DEFAULT 1", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.23.2019, posdualdpimg 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "posdualdpimg", "nvarchar(300)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.24.2019, posdualdptype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "posdualdptype", "nvarchar(2)", "DEFAULT '0'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.29.2019, pricedollaryn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pricedollaryn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.12.2019, adlocalyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dualdpadtype", "nvarchar(2)", "DEFAULT 'W'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.12.2019, dualdpadlocalpath 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dualdpadlocalpath", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 6.25.2019, posreceiptlogimg 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "posreceiptlogimg", "nvarchar(300)", "DEFAULT ''", 0);
        }


        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 6.25.2019, logoprintingonreceiptyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "logoprintingonreceiptyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 6.25.2019, logoimgpath 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "logoimgpath", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.2.2019, thankyoupageyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "thankyoupageyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.4.2019, uploadynonlysalesdata 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "uploadynonlysalesdata", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.5.2019, autosaledatauploadyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "autosaledatauploadyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverstationip1 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverstationip1", "nvarchar(4)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverstationip2 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverstationip2", "nvarchar(4)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverstationip3 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverstationip3", "nvarchar(4)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverstationip4 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverstationip4", "nvarchar(4)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverstationport 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverstationport", "nvarchar(10)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverport 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverport", "nvarchar(10)", "NULL", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.9.2019, serverclienttype 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverclienttype", "nvarchar(2)", "DEFAULT 'C'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.12.2019, cloudbackupintenderbackupyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cloudbackupintenderbackupyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.25.2019, scaleuseyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaleuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.25.2019, tareidx 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tareidx", "int", "NULL", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.25.2019, tareweight 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tareweight", "money", "NULL", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.25.2019, tareqty 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tareqty", "int", "NULL", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.25.2019, taretotalweight 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taretotalweight", "money", "NULL", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.26.2019, perweight 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "perweight", "money", "NULL", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 9.26.2019, scaleuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaleuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 9.26.2019, scaleunit 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaleunit", "nvarchar(10)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 9.30.2019, returneddeliverypickupfee 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "returneddeliverypickupfee", "money", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 9.30.2019, returnedtip 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "returnedtip", "money", "DEFAULT 0", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_cashintout_history 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales_cashintout_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_employee_loginout_history 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_employee_loginout_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_cashout_json 테이블 컬럼 추가
        // 10.4.2017, endofdayNum 컬럼 추가
        altTableName = "salon_sales_cashout_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayNum", "int", "DEFAULT 0", 0);
        }

        // salon_sales_endofday_json 테이블 컬럼 추가
        // 10.7.2019, endofdayDate 컬럼 추가
        altTableName = "salon_sales_endofday_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "endofdayDate", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.16.2019, localkitchenprintingtype 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "localkitchenprintingtype", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.30.2019, kitchenprintingtexttype 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintingtexttype", "nvarchar(2)", "DEFAULT 'I'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.1.2019, itemanimationyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "itemanimationyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.4.2019, scaleminweight 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaleminweight", "money", "DEFAULT 0.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.4.2019, scalemaxweight 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scalemaxweight", "money", "DEFAULT 0.0", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 11.4.2019, kitchenprintnum 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintnum", "nvarchar(40)", "DEFAULT '1'", 0);
        }

        // temp_salecart_optionadd 테이블 컬럼 추가
        // 11.13.2019, qty 컬럼 추가
        altTableName = "temp_salecart_optionadd";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "qty", "int", "DEFAULT 1", 0);
        }

        // temp_salecart_optionadd_imsi 테이블 컬럼 추가
        // 11.13.2019, qty 컬럼 추가
        altTableName = "temp_salecart_optionadd_imsi";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "qty", "int", "DEFAULT 1", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.15.2019, dynamicpriceyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dynamicpriceyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 11.25.2019, keyinyn 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "keyinyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 11.26.2019, adjustedyn 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "adjustedyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 11.26.2019, refnum 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "refnum", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 11.27.2019, tipprocessingyn 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipprocessingyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.17.2019, carddirectyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "carddirectyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 1.2.2020, postype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "postype", "nvarchar(2)", "DEFAULT 'Q'", 0);
        }

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 1.2.2020, colorvalue 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "colorvalue", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.3.2020, modifierqtyviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "modifierqtyviewyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.10.2020, tableidx 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableidx", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.10.2020, billtag 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billtag", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 2.28.2020, barcode 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "barcode", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.28.2020, mergednum 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mergednum", "smallint", "DEFAULT 0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 2.28.2020, scaledecimaltwiceyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaledecimaltwiceyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.29.2020, subtablenum 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "subtablenum", "smallint", "DEFAULT 1", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.29.2020, billnum 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billnum", "smallint", "DEFAULT 1", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 3.7.2020, kitchenprintedyn 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_sales_detail 테이블 컬럼 추가
        // 3.9.2020, tableidx 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableidx", "nvarchar(100)", "DEFAULT ''", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 3.9.2020, mergednum 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mergednum", "smallint", "DEFAULT 0", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 3.9.2020, subtablenum 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "subtablenum", "smallint", "DEFAULT 1", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 3.9.2020, billnum 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billnum", "smallint", "DEFAULT 1", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 3.9.2020, tablename 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tablename", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 3.9.2020, tablename 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tablename", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 3.10.2020, tablepeoplecnt 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tablepeoplecnt", "smallint", "DEFAULT 0", 0);
        }

        // salon_store_restaurant_table_peoplecnt 테이블 컬럼 추가
        // 3.10.2020, holdcode 컬럼 추가
        altTableName = "salon_store_restaurant_table_peoplecnt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "holdcode", "nvarchar(200)", "DEFAULT ''", 0);
        }
        // salon_store_restaurant_table_peoplecnt 테이블 컬럼 추가
        // 3.12.2020, subtablenum 컬럼 추가
        altTableName = "salon_store_restaurant_table_peoplecnt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "subtablenum", "smallint", "DEFAULT 1", 0);
        }

        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 4.1.2020, customerordernumber 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerordernumber", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 7.7.2020, tablename 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tablename", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 7.7.2020, tableidx 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableidx", "int", "DEFAULT 0", 0);
        }

        // salon_store_curbsidenewsidemenu 테이블 컬럼 추가
        // 7.16.2020, sidemenuidx 컬럼 추가
        altTableName = "salon_store_curbsidenewsidemenu";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sidemenuidx", "int", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 7.23.2020, isCloudUpload 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 7.23.2020, webpayuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "webpayuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 7.26.2020, curbsidepickupyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "curbsidepickupyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 7.26.2020, sideorderyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sideorderyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 9.12.2020, timeout 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timeout", "nvarchar(2)", "DEFAULT '1'", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 10.5.2020, dcextraforreturn 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextraforreturn", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 10.14.2020, cardtryyn 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardtryyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_sales 테이블 컬럼 추가
        // 10.19.2020, salepg_ip 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "salepg_ip", "nvarchar(30)", "DEFAULT ''", 0);
        }


        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 11.2.2020, printedyn 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "printedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.21.2020, mssqlsyncyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mssqlsyncyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.21.2020, mssqldbip 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mssqldbip", "nvarchar(500)", "DEFAULT ''", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 12.7.2020, frommssql 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "frommssql", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.10.2020, vrkitchenprinting 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "vrkitchenprinting", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.18.2020, autoweborderprinting 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "autoweborderprinting", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.26.2020, initcapacity 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "initcapacity", "smallint", "DEFAULT 500", 0);
        }

        // salon_sales_return 테이블 컬럼 추가
        // 12.26.2020, wdate 컬럼 추가
        altTableName = "salon_sales_return";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wdate", "datetime", "NULL", 0);
        }

        // salon_sales_card 테이블 칼럼 추가
        // 21.01.20 amountdue 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "amountdue", "nvarchar(30)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 21.01.26 qtyaddupyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "qtyaddupyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 21.02.04, tipaddhistoryvisibleyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipaddhistoryvisibleyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_sales_cashout_json 테이블 컬럼 추가
        // 4.6.2021, isCloudUpload 컬럼 추가
        altTableName = "salon_sales_cashout_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0", 0);
        }

        // salon_sales_endofday_json 테이블 컬럼 추가
        // 4.6.2021, isCloudUpload 컬럼 추가
        altTableName = "salon_sales_endofday_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 4.20.2021, isCloudUpload2 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "isCloudUpload2", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.21.2021, passwordyninmod 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "passwordyninmod", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.23.2021, tableboardtype 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableboardtype", "nvarchar(2)", "DEFAULT '2'", 0);
        }

        // temp_salecart_del 테이블 컬럼 추가
        // 5.26.2021, tableidx 컬럼 추가
        altTableName = "temp_salecart_del";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableidx", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.28.2021, dcextratype 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextratype", "nvarchar(2)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 5.28.2021, dcextravalue 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextravalue", "money", "DEFAULT 0", 0);
        }

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 6.10.2021, boxtype 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "boxtype", "nvarchar(50)", "DEFAULT '0'", 0);
        }

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 6.10.2021, boxkinds 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "boxkinds", "nvarchar(50)", "DEFAULT 'table'", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 6.14.2021, paidyn 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "paidyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // bill_list_paid 테이블 컬럼 추가
        // 6.21.2021, paytype 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "paytype", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // bill_list_paid 테이블 컬럼 추가
        // 6.21.2021, changeamount 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "changeamount", "money", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.22.2018, pagernumbershowonpickuporder 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pagernumbershowonpickuporder", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.22.2018, ordernumbershowonpickuporder 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordernumbershowonpickuporder", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // card_processing_data 테이블 컬럼 추가
        // 6.26.2018, delyn 컬럼 추가
        altTableName = "card_processing_data";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "delyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.26.2021, cardstatususe 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardstatususe", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // bill_list_paid 테이블 컬럼 추가
        // 7.2.2021, ordernum 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordernum", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.2.2021, qtyinsyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "qtyinsyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.2.2021, customernumberviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customernumberviewyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 7.7.2021, cancelreason 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cancelreason", "ntext", "DEFAULT ''", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 7.14.2021, cashieryn 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "emptype", "nvarchar(2)", "DEFAULT '0'", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL", 0);
        }

        // salon_sales_tip 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_tip";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL", 0);
        }

        // salon_sales_card 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL", 0);
        }

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''", 0);
        }


        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, cashinoutyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashinoutyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, paymentyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "paymentyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, paymenttype 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "paymenttype", "nvarchar(200)", "DEFAULT 'cash,creditcard,giftcard,point,other' ", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 8.17.2021, togodelitype 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togodelitype", "nvarchar(2)", "DEFAULT 'H'", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 8.17.2021, togodelitype 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togodelitype", "nvarchar(2)", "DEFAULT 'H'", 0);
        }


        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuityuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuityuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuitytype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuitytype", "nvarchar(4)", "DEFAULT '%'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuityvalue 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuityvalue", "money", "DEFAULT 0.0", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 8.20.2021, gratuitycustomercount 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuitycustomercount", "money", "DEFAULT 1", 0);
        }

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, minval 컬럼 추가
        altTableName = "salon_storeservice_option";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "minval", "smallint", "DEFAULT 0", 0);
        }

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, maxval 컬럼 추가
        altTableName = "salon_storeservice_option";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "maxval", "smallint", "DEFAULT 1", 0);
        }

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, maxsumval 컬럼 추가
        altTableName = "salon_storeservice_option";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "maxsumval", "smallint", "DEFAULT 1", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, databasename 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "databasename", "nvarchar(50)", "DEFAULT '" + GlobalMemberValues.DATABASE_NAME + "'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, databasepass 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "databasepass", "nvarchar(50)", "DEFAULT 'DhksGkDP@02)'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, mobilehost 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mobilehost", "nvarchar(50)", "DEFAULT 'yukdaejangm'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, cloudhost 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cloudhost", "nvarchar(50)", "DEFAULT 'yukdaejangcloud'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.26.2021, serverpassworduse 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serverpassworduse", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.28.2021, servercodeuse 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "servercodeuse", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, pickuptypepopupuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pickuptypepopupuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, printingcategoryyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "printingcategoryyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.8.2021, cashoutreportitems 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashoutreportitems", "nvarchar(200)", "DEFAULT '<1>,<2>,<3>,<4>,<5>'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_here 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customer_info_here_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_togo 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customer_info_togo_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_delivery 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customer_info_delivery_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 11.12.2021, quicksaleyn 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "quicksaleyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.17.2021, divideryn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "divideryn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.17.2021, divideruseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "divideruseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 11.30.2021, labelprinteruse 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelprinteruse", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.30.2021, nameforlabel 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "nameforlabel", "nvarchar(300)", "DEFAULT ''", 0);
        }

        // salon_storestationinfo 테이블 컬럼 추가
        // 12.16.2021, sttype 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sttype", "nvarchar(2)", "DEFAULT 'R'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 1.13.2022, imageusezone 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "imageusezone", "nvarchar(50)", "DEFAULT 'P,K,O'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 1.27.2022, menuusezone 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "menuusezone", "nvarchar(50)", "DEFAULT 'P,K,O'", 0);
        }

        // salon_storeservice_main 테이블 컬럼 추가
        // 1.27.2022, categoryusezone 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "categoryusezone", "nvarchar(50)", "DEFAULT 'P,K,O'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 2.8.2022, mdforceyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "mdforceyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 2.8.2022, preSalesCode 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "preSalesCode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 3.4.2022, managerpwdnz 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "managerpwdnz", "nvarchar(100)", "DEFAULT '0904'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.5.2022, showynifzero_m 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "showynifzero_m", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.5.2022, showynifzero_k 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "showynifzero_k", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.10.2022, nowtableidx 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "nowtableidx", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.10.2022, nowtablename 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "nowtablename", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.12.2022, empinfoprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "empinfoprintingyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.12.2022, menulistprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "menulistprintingyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.18.2022, reprintyn 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "reprintyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.29.2022, labelprintyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelprintyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeservice_sub 테이블 컬럼 추가
        // 4.5.2022, labelprintnum 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelprintnum", "nvarchar(50)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 4.14.2022, labelprintedyn 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 4.15.2022, serveraccesscode 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serveraccesscode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storeemployee 테이블 컬럼 추가
        // 4.15.2022, serveraccesspwd 컬럼 추가
        altTableName = "salon_storeemployee";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "serveraccesspwd", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_storesmstextsample 테이블 컬럼 추가
        // 4.19.2022, sortnum 컬럼 추가
        altTableName = "salon_storesmstextsample";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sortnum", "smallint", "NULL", 0);
        }

        // bill_list_paid 테이블 컬럼 추가
        // 4.25.2022, cardsalesidx 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cardsalesidx", "int", "NULL", 0);
        }

        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype13 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashtype13", "int", "DEFAULT 0", 0);
        }
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype14 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashtype14", "int", "DEFAULT 0", 0);
        }
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype15 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashtype15", "int", "DEFAULT 0", 0);
        }
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype16 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashtype16", "int", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, openmsgwhendeletemenu_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "openmsgwhendeletemenu_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, otherpayprinting_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "otherpayprinting_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, tipprintingwhentogo_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tipprintingwhentogo_yn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.10.2022, suggestiontiptype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "suggestiontiptype", "nvarchar(10)", "DEFAULT 'AT'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.20.2022, billprintpopupyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billprintpopupyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.22.2022, commongratuitytype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "commongratuitytype", "nvarchar(10)", "DEFAULT 'AT'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.20.2022, custombillsplituseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "custombillsplituseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 6.20.2022, multistationsyncuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "multistationsyncuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaytype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaytype", "nvarchar(4)", "DEFAULT 'A'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaymoneytype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaymoneytype", "nvarchar(4)", "DEFAULT '%'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaymoney 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaymoney", "money", "DEFAULT 0", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaytaxtype 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaytaxtype", "nvarchar(10)", "DEFAULT 'AT'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpayname 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpayname", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.30.2022, pagernumofdigits 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pagernumofdigits", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.30.2022, customeronlinecheckyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customeronlinecheckyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.16.2022, eodyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "eodyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }

        // salon_storestationsettings_deviceprinter_master 테이블 컬럼 추가
        // 09.16.2022, masteruseyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter_master";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "masteruseyn", "smallint", "DEFAULT 0", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.30.2022, startpagernum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "startpagernum", "smallint", "DEFAULT 1", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 10.4.2022, scaleautoweighyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "scaleautoweighyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // btn_logs 테이블 컬럼 추가
        // 10.27.2022, salescode 컬럼 추가
        altTableName = "btn_logs";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "salescode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''", 0);
        }

        // temp_salecart_ordered 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "temp_salecart_ordered";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''", 0);
        }

        // salon_sales 테이블 컬럼 추가
        // 12.22.2022, cancelreason 컬럼 추가
        altTableName = "salon_sales";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cancelreason", "ntext", "DEFAULT ''", 0);
        }

        // bill_list_paid 테이블 컬럼 추가
        // 1.5.2023, billcode 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billcode", "nvarchar(100)", "DEFAULT ''", 0);
        }

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 1.11.2023, size 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "size", "nvarchar(2)", "DEFAULT 'M'", 0);
        }


        // btn_logs 테이블 컬럼 추가
        // 1.12.2023 holdcode 추가
        altTableName = "btn_logs";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "holdcode", "nvarchar(200)", "DEFAULT ''", 0);
        }

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023 selectedDcExtraAllEach 추가
        altTableName = "temp_item_printing";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "selectedDcExtraAllEach", "nvarchar(10)", "DEFAULT ''", 0);
        }
        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023 selectedDcExtraType 추가
        altTableName = "temp_item_printing";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "selectedDcExtraType", "nvarchar(20)", "DEFAULT ''", 0);
        }
        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023 dcextratype 추가
        altTableName = "temp_item_printing";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextratype", "nvarchar(2)", "DEFAULT ''", 0);
        }
        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023 dcextravalue 추가
        altTableName = "temp_item_printing";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextravalue", "money", "DEFAULT 0", 0);
        }
        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023 selectedDcExtraPrice 추가
        altTableName = "temp_item_printing";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "selectedDcExtraPrice", "money", "DEFAULT 0", 0);
        }

        ////////
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 stationnumber_type 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "stationnumber_type", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 stationnumber_size 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "stationnumber_size", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 ordernumber_type 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordernumber_type", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 ordernumber_position 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ordernumber_position", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 customerinfo_yn 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "customerinfo_yn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 totalamount_size 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "totalamount_size", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 tip_type 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tip_type", "nvarchar(2)", "DEFAULT '1'", 0);
        }
        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 1.20.2023 tip_size 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tip_size", "nvarchar(2)", "DEFAULT '1'", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 2.7.2023, kitchenPrintedYN 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenPrintedYN", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales_detail 테이블 컬럼 추가
        // 2.7.2023, size 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelPrintedYN", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.13.2023, saledatauploadpauseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "saledatauploadpauseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storediscountbutton 테이블 컬럼 추가
        // 3.21.2023, sortnum 컬럼 추가
        altTableName = "salon_storediscountbutton";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "sortnum", "smallint", "DEFAULT 1", 0);
        }


        // salon_storeservice_main 테이블 컬럼 추가
        // 4.6.2023, labelprintnum 컬럼 추가
        altTableName = "salon_storeservice_main";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "labelprintnum", "nvarchar(40)", "DEFAULT ''", 0);
        }


        // temp_salecart 테이블 컬럼 추가
        // 4.19.2023, pastholdcode 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pastholdcode", "nvarchar(1000)", "DEFAULT ''", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, gratuityamt 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuityamt", "money", "DEFAULT 0", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, subtotalamt 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "subtotalamt", "money", "DEFAULT 0", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, taxamt 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "taxamt", "money", "DEFAULT 0", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, totalamountAmt 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "totalamountAmt", "money", "DEFAULT 0", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 4.22.2023, billprintedyn 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // temp_salecart_ordered 테이블 컬럼 추가
        // 4.24.2023, pastholdcode 컬럼 추가
        altTableName = "temp_salecart_ordered";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "pastholdcode", "nvarchar(1000)", "DEFAULT ''", 0);
        }
        // temp_salecart_ordered 테이블 컬럼 추가
        // 4.24.2023, billprintedyn 컬럼 추가
        altTableName = "temp_salecart_ordered";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_newcartcheck_bystation2 테이블 컬럼 추가
        // 4.25.2023, ctype 컬럼 추가
        altTableName = "salon_newcartcheck_bystation2";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ctype", "nvarchar(30)", "DEFAULT ''", 0);
        }
        // salon_table_statuschange 테이블 컬럼 추가
        // 4.25.2023, ctype 컬럼 추가
        altTableName = "salon_table_statuschange";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "ctype", "nvarchar(30)", "DEFAULT ''", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.27.2023, timeviewontableyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "timeviewontableyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }



        // salon_storegeneral 테이블 컬럼 추가
        // 5.3.2023, wmuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 5.3.2023, wmapiip 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmapiip", "nvarchar(200)", "DEFAULT ''", 0);
        }
        // salon_storeservice_sub 테이블 컬럼 추가
        // 5.3.2023, wmuseyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 5.3.2023, wmodyn 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmodyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 5.5.2023, wmodresult 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmodresult", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_sales_detail 테이블 컬럼 추가
        // 5.8.2023, wmodresultmsg 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmodresultmsg", "nvarchar(1000)", "DEFAULT ''", 0);
        }


        // salon_sales_detail 테이블 컬럼 추가
        // 5.10.2023, wmurl 컬럼 추가
        altTableName = "salon_sales_detail";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmurl", "nvarchar(1000)", "DEFAULT ''", 0);
        }

        // salon_storegeneral 테이블 컬럼 추가
        // 5.11.2023, wmoptionstsr 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmoptionstsr", "nvarchar(1000)", "DEFAULT ''", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 5.11.2023, addpaycustomerselectyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaycustomerselectyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // bill_list 테이블 컬럼 추가
        // 5.15.2023, dcextraAmt 컬럼 추가
        altTableName = "bill_list";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "dcextraAmt", "money", "DEFAULT 0", 0);
        }


        // temp_salecart 테이블 컬럼 추가
        // 5.18.2023, billidx_byitemsplit 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billidx_byitemsplit", "nvarchar(20)", "DEFAULT ''", 0);
        }
        // temp_salecart_ordered 테이블 컬럼 추가
        // 5.18.2023, billidx_byitemsplit 컬럼 추가
        altTableName = "temp_salecart_ordered";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "billidx_byitemsplit", "nvarchar(20)", "DEFAULT ''", 0);
        }

        // salon_storeinfo 테이블 컬럼 추가
        // 6.20.2023, name_en 컬럼 추가
        altTableName = "salon_storeinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "name_en", "nvarchar(200)", "DEFAULT ''", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 6.21.2023, addpayitempriceshowyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpayitempriceshowyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 6.21.2023, cashinoutstartendcashyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashinoutstartendcashyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }


        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, gratuitydelyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "gratuitydelyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, crmuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "crmuseyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, cashdcshowonreceiptyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashdcshowonreceiptyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, cashdctaxshowyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "cashdctaxshowyn", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }


        // salon_storeservice_sub 테이블 컬럼 추가
        // 8.2.2023, servicenamealt 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "servicenamealt", "nvarchar(500)", "DEFAULT ''", 0);
        }


        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 8.30.2023, onlinetype 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "onlinetype", "nvarchar(2)", "DEFAULT 'W'", 0);
        }

        // salon_storeinfo 테이블 컬럼 추가
        // 9.1.2023, name2 컬럼 추가
        altTableName = "salon_storeinfo";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "name2", "nvarchar(200)", "DEFAULT ''", 0);
        }




        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.14.2023, wmtype 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmtype", "nvarchar(2)", "DEFAULT ''", 0);
        }
        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.14.2023, wmbean 컬럼 추가
        altTableName = "salon_storeservice_sub";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "wmbean", "nvarchar(200)", "DEFAULT ''", 0);
        }



        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 9.15.2023, receiptprintedyn 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "receiptprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 9.15.2023, kitchenprintedyn 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "kitchenprintedyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_storegeneral 테이블 컬럼 추가
        // 9.19.2023, addpaynameoncustompopup 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "addpaynameoncustompopup", "nvarchar(2)", "DEFAULT 'Y'", 0);
        }


        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 10.11.2023, orderfrom 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "orderfrom", "nvarchar(300)", "DEFAULT ''", 0);
        }
        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 10.11.2023, salescodethirdparty 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "salescodethirdparty", "nvarchar(300)", "DEFAULT ''", 0);
        }


        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.27.2023, itemdeletereasonyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "itemdeletereasonyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_storestationsettings_system_receipt 테이블 컬럼 추가
        // 11.1.2023 bottomfeedrowcount_yn 추가
        altTableName = "salon_storestationsettings_system_receipt";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "bottomfeedrowcount_yn", "nvarchar(2)", "DEFAULT '1'", 0);
        }

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 1.17.2024, tableorderuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tableorderuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }

        // salon_sales_kitchenprintingdata_json_torder 테이블 컬럼 추가
        // 1.17.2024, clouddbidx 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json_torder";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "clouddbidx", "int", "NULL", 0);
        }

        // temp_salecart 테이블 컬럼 추가
        // 2.2.2024, tordercode 컬럼 추가
        altTableName = "temp_salecart";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tordercode", "nvarchar(100)", "DEFAULT ''", 0);
        }


        // temp_salecart_del 테이블 컬럼 추가
        // 2.3.2024, tordercode 컬럼 추가
        altTableName = "temp_salecart_del";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tordercode", "nvarchar(100)", "DEFAULT ''", 0);
        }



        // bill_list_paid 테이블 컬럼 추가
        // 2.3.2024, tordercode 컬럼 추가
        altTableName = "bill_list_paid";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "split_transaction_id", "nvarchar(100)", "DEFAULT ''", 0);
        }
        // salon_sales_card 테이블 컬럼 추가
        // 2.3.2024, tordercode 컬럼 추가
        altTableName = "salon_sales_card";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "split_transaction_id", "nvarchar(100)", "DEFAULT ''", 0);
        }
        // salon_sales_tip_split 테이블 컬럼 추가
        // 2.3.2024, tordercode 컬럼 추가
        altTableName = "salon_sales_tip_split";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "split_transaction_id", "nvarchar(100)", "DEFAULT ''", 0);
        }


        // temp_salecart_ordered 테이블 컬럼 추가
        // 3.19.2024, tordercode 컬럼 추가
        altTableName = "temp_salecart_ordered";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "tordercode", "nvarchar(100)", "DEFAULT ''", 0);
        }



        // salon_storegeneral 테이블 컬럼 추가
        // 3.25.2024, torderuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "torderuseyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }
        // salon_storegeneral 테이블 컬럼 추가
        // 3.25.2024, torderkey 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "torderkey", "nvarchar(100)", "DEFAULT ''", 0);
        }


        // salon_storegeneral 테이블 컬럼 추가
        // 4.30.2024, qsronrestaurantyn 컬럼 추가
        altTableName = "salon_storegeneral";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "qsronrestaurantyn", "nvarchar(2)", "DEFAULT 'N'", 0);
        }


        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 5.17.2024, stcode 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        if (checkTable(altTableName) > 0) {
            alterDatabaseTableColumn(altTableName, "stcode", "nvarchar(50)", "DEFAULT ''", 0);
        }

        /****************************************************************************/
    }

    public void alterTableColumn() {
        /** 컬럼 추가 ****************************************************************/
        String altTableName = "";

        // salon_sales_detail 테이블 컬럼 추가
        // 10.8.2020, isQuickSale 컬럼 추가
//        altTableName = "salon_sales_detail";
//        if (checkTable(altTableName) > 0) {
//            alterDatabaseTableColumn(altTableName, "isQuickSale", "nvarchar(2)", "DEFAULT 'N'", 1);
//        }

        /****************************************************************************/
    }
    /*****************************************************************************************/

    /** 테이블 전체 삭제 ***************************************************************/
    public void dropAllDatabaseTables(String[] strArr) {
        String tempQuery = "";
        for (int i = 0; i < strArr.length; i++) {
            tempQuery = "DROP TABLE IF EXISTS " + GlobalMemberValues.dbTableNameGroup[i];
            this.dbHandler.executeWrite(tempQuery);
            GlobalMemberValues.logWrite("DatabaseTablesDrop", "삭제테이블 : " + GlobalMemberValues.dbTableNameGroup[i] + "\n");
        }
    }
    /*****************************************************************************************/

    /** 테이블 하나씩 삭제 ***************************************************************/
    public void dropEachDatabaseTable(String tblName) {
        if (!GlobalMemberValues.isStrEmpty(tblName)) {
            //GlobalMemberValues.logWrite("DatabaseTablesDrop", "단독테이블삭제 진입\n");
            String tempQuery = "";
            tempQuery = "DROP TABLE IF EXISTS " + tblName;
            this.dbHandler.executeWrite(tempQuery);
            //GlobalMemberValues.logWrite("DatabaseTablesDrop", "삭제테이블 : " + tblName + "\n");
        }
    }
    /*****************************************************************************************/

    /** 테이블 수정(ALTER) 메소드 *************************************************************
     Parameter 설명
     tblName : 테이블명
     tblColumnName : 컬럼명
     tblColumnDataType : 컬럼 데이터타입 ex) nvarchar(30)
     tblColumnEtc : 컬럼 기타 정보 ex) PRIMARY KEY AUTOINCREMENT **/
    public void alterDatabaseTableColumn(String tblName, String tblColumnName, String tblColumnDataType, String tblColumnEtc, int makeType) {
        switch(makeType) {
            case 0: {
                if(dbHandler.getSchTableColumnCount(tblName, tblColumnName) == 0) {
                    //Toast.makeText(getApplication(), "컬럼이 없음", Toast.LENGTH_LONG).show();
                    this.dbHandler.executeWrite(getMakeSqlStatement(tblName, tblColumnName, tblColumnDataType, tblColumnEtc, makeType));
                }
                break;
            }
            case 1: {
                if(dbHandler.getSchTableColumnCount(tblName, tblColumnName) > 0) {
                    //Toast.makeText(getApplication(), "컬럼이 없음", Toast.LENGTH_LONG).show();
                    this.dbHandler.executeWrite(getMakeSqlStatement(tblName, tblColumnName, tblColumnDataType, tblColumnEtc, makeType));
                }
                break;
            }
        }

    }
    /*****************************************************************************************/


    /** 테이블 수정관련 쿼리를 만들어주는 메소드. ************************************************
     Parameter 설명
     tblName : 테이블명
     tblColumnName : 컬럼명
     tblColumnDataType : 컬럼 데이터타입 ex) nvarchar(30)
     tblColumnEtc : 컬럼 기타 정보 ex) PRIMARY KEY AUTOINCREMENT
     makeType : 타입  0 : 수정(ALTER), 1 : 삭제(DROP) **/
    public String getMakeSqlStatement (String tblName, String tblColumnName, String tblColumnDataType, String tblColumnEtc, int makeType) {
        String makeStatement = null;
        switch(makeType) {
            case 0: {           // 컬럼 추가
                makeStatement = "ALTER TABLE " + tblName + " add " + tblColumnName  + " " + tblColumnDataType + " " + tblColumnEtc;
                break;
            }
            case 1: {           // 컬럼 수정
                makeStatement = "ALTER TABLE " + tblName + " alter column " + tblColumnName  + " " + tblColumnDataType + " " + tblColumnEtc;
                break;
            }
        }
        return makeStatement;
    }
    /*****************************************************************************************/


    public int checkTable(String tblName) {
        int schCnt = dbHandler.getSchTableCount(tblName);
        //GlobalMemberValues.logWrite("DatabaseInit-checkTable", "Table Count : " + schCnt);
        return schCnt;
    }

    /** 데이터 Insert / Update / Delete ***********************************/
    public void dbExecuteWrite(String _query) {
        dbHandler.executeWrite(_query);
    }
    /**********************************************************************/

    /** 데이터 Insert / Update / Delete 후 결과값 리턴 ********************/
    public String dbExecuteWriteReturnValue(String _query) {
        String returnCode = "";

        String returnCode1 = "";
        String returnCode2 = "";

        returnCode1 = dbHandler.executeWriteReturnValue(_query);

        // mssql 연동일 경우
        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            GlobalMemberValues.logWrite("mssqljjjlog", "here..2" + "\n");
            returnCode2 = MssqlDatabase.executeTransactionByQuery(_query);
        }

        if (GlobalMemberValues.isPossibleMssqlInfo()) {
            returnCode = returnCode2;
        } else {
            returnCode = returnCode1;
        }

        return returnCode;
    }
    /**********************************************************************/

    /** 데이터 Insert / Update / Delete 후 결과값 리턴 ********************/
    public String dbExecuteWriteReturnValueOnlySqlite(String _query) {
        String returnCode = "";

        String returnCode1 = "";
        String returnCode2 = "";

        returnCode1 = dbHandler.executeWriteReturnValue(_query);

        return returnCode1;
    }
    /** 데이터 Insert / Update / Delete (Transaction 용) 후 리턴 **********/
    public String dbExecuteWriteForTransactionReturnResultOnlySqllite(Vector<String> queryVec) {
        String returnResult = "";
        String returnResult1 = "";
        String returnResult2 = "";

        // sqlite
        returnResult1 = dbHandler.executeWriteForTransactionReturnResultOnlySqllite(queryVec);

//        // mssql 연동일 경우
//        if (GlobalMemberValues.isPossibleMssqlInfo()) {
//            GlobalMemberValues.logWrite("mssqljjjlog", "here..1" + "\n");
//            returnResult2 = MssqlDatabase.executeTransaction(queryVec);
//        }

//        if (GlobalMemberValues.isPossibleMssqlInfo()) {
//            returnResult = returnResult2;
//        } else {
//            returnResult = returnResult1;
//        }

        returnResult = returnResult1;

        return returnResult;
    }
    /**********************************************************************/
    /**********************************************************************/

    /** 데이터 Insert / Update / Delete (Transaction 용) ******************/
    public void dbExecuteWriteForTransaction(Vector<String> queryVec) {
        dbHandler.executeWriteForTransaction(queryVec);
    }
    /**********************************************************************/

    /** 데이터 Insert / Update / Delete (Transaction 용) 후 리턴 **********/
    public String dbExecuteWriteForTransactionReturnResult(Vector<String> queryVec) {
        String returnResult = "";
        String returnResult1 = "";
        String returnResult2 = "";

        // sqlite
        returnResult1 = dbHandler.executeWriteForTransactionReturnResult(queryVec);

//        // mssql 연동일 경우
//        if (GlobalMemberValues.isPossibleMssqlInfo()) {
//            GlobalMemberValues.logWrite("mssqljjjlog", "here..1" + "\n");
//            returnResult2 = MssqlDatabase.executeTransaction(queryVec);
//        }

//        if (GlobalMemberValues.isPossibleMssqlInfo()) {
//            returnResult = returnResult2;
//        } else {
//            returnResult = returnResult1;
//        }

        returnResult = returnResult1;

        return returnResult;
    }
    /**********************************************************************/

    /** 데이터 읽어오기 Reader ********************************************/
    public Cursor dbExecuteRead(String _query) {
        Cursor c = dbHandler.executeRead(_query);
        return c;
    }
    /**********************************************************************/

    /** 데이터 하나만 읽어서 String 값으로 리턴 ***************************/
    public String dbExecuteReadReturnString(String _query) {
        String returnStr = "";
        if (GlobalMemberValues.isStrEmpty(_query)) {
            return "";
        }
        if (dbHandler == null) {
            return "";
        }

        Cursor c = dbHandler.executeRead(_query);
        //GlobalMemberValues.logWrite("dbExecuteReadReturnString", "Query - " + _query + "\n");

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            returnStr = c.getString(0);
            //GlobalMemberValues.logWrite("dbExecuteReadReturnString", "returnStr - " + returnStr + "\n");
            returnStr = GlobalMemberValues.getDBTextAfterChecked(returnStr, 1);
        }
        // jihun add
        if (c != null){
            c.close();
        } else {
        }

        if (returnStr == null) returnStr = "";

        return returnStr;
    }
    /**********************************************************************/


    /** API 데이터 다운로드 후 데이터베이스 입력 메소드 *******************/
    public void insertDatabaseAfterAPIDownload() {
        APIDownLoad apiDownLoad = new APIDownLoad();
        Vector<String> mVector = new Vector<String>();
        Vector<String> dbVector = new Vector<String>();
        String pTblName = "";
        String dbInsUpdDelQuery = "";
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";

        // 글로벌클래스(GlobalMemberValues) 배열변수(dbTableNameGroupForApi)에
        // 저장된 데이터베이스 테이블들을 가져와 String 배열변수에 저장한다.
        String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi;
        int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
        for (int i = 0; i < allTableSize ; i++) {
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", dbTableArr[i] + "\n");
            pTblName = dbTableArr[i];   // 처리할 테이블
            // 테이블이 내부DB(SQLite) 에 있을 때에만 테이블 삭제후 입력 처리한다.
            if (checkTable(pTblName) > 0) {
                //dbHandler.deleteTableData(pTblName);    //-------- 1. 입력처리전 먼저 현재 DB 에 있는 데이터를 삭제(delete)
                //1. 입력처리전 먼저 현재 DB 에 있는 데이터를 삭제(delete)
                dbInsUpdDelQuery = "delete from " + pTblName;
                dbVector.addElement(dbInsUpdDelQuery);
                mVector = apiDownLoad.getApiDownLoadVectorData(pTblName);   // DB 처리용 쿼리조합 Vector 값 저장
                for (String strVec : mVector) {
                    String[] vecStrSplit = strVec.split(GlobalMemberValues.STRSPLITTER1);
                    //dbExecuteWrite(vecStrSplit[1]);
                    //2. 다운로드 데이터 등록(insert)
                    dbVector.addElement(vecStrSplit[1]);
                }
            }
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", " 처리한 테이블 : " + pTblName + "\n");
        }

        // 다운로드 Log 내용 기록 ----------------------------------------------------------------------------
        String syncInsertSql = "insert into sync_data_log (sidx, stcode, syncType, isCloudUpload) values (" +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " '" + GlobalMemberValues.MSYNCDATATYPE + "', " +
                " '0' " +
                ")";
        dbVector.addElement(syncInsertSql);
        // ---------------------------------------------------------------------------------------------------

        for (String tempQuery : dbVector) {
            GlobalMemberValues.logWrite("dbInitDataQuery", "dbInitDataQuery : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        returnResult = dbExecuteWriteForTransactionReturnResult(dbVector);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Data Download Error from Cloud Server", "Close");
            return;
        } else { // 정상처리일 경우
            // 클라우드에서 데이터가 정상적으로 다운로드 되었을 경우 이미지를 다운로드한다.
            GlobalMemberValues.logWrite("ImageDownloadFunc", "여기 실행이오...1" + "\n");
            ImageDownload imgDownload = new ImageDownload();
            imgDownload.getDownloadCloudFiles("service");
            GlobalMemberValues.logWrite("ImageDownloadFunc", "여기 실행이오...2" + "\n");
        }
    }
    /******************************************************************************************/


    /** API 데이터 다운로드 후 데이터베이스 입력 메소드 *******************/
    public void insertDownloadDataInDatabase(String[] paramTblArr, String paramItemGroup, int paramInternetCheckType) {
        GlobalMemberValues.logWrite("DataInDatabase", "여기왔음...111" + "\n");

        GlobalMemberValues.logWrite("DataInDatabase", "paramInternetCheckType : " + paramInternetCheckType + "\n");

        if (paramInternetCheckType == 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                return;
            }
        }

        GlobalMemberValues.logWrite("DataInDatabase", "여기왔음...222" + "\n");

        // 데이터 다운로드일 때
        GlobalMemberValues.datadownloadingyn = "Y";

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            insertDownloadDataInApiProcessing(paramTblArr, paramItemGroup, paramInternetCheckType);
        } else {
            insertDownloadDataInGettingQuery(paramTblArr, paramItemGroup, paramInternetCheckType);
        }

        GlobalMemberValues.logWrite("DataInDatabase", "여기왔음...333" + "\n");
    }
    /******************************************************************************************/

    /** API 데이터 다운로드 후 데이터베이스 입력 메소드 *******************/
    public void insertDownloadDataInGettingQuery(String[] paramTblArr, String paramItemGroup, int paramInternetCheckType) {
        GlobalMemberValues.logWrite("InGettingQuery", "여기왔음...111" + "\n");

        GlobalMemberValues.logWrite("InGettingQuery", "paramInternetCheckType : " + paramInternetCheckType + "\n");

        if (paramInternetCheckType == 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                return;
            }
        }

        GlobalMemberValues.logWrite("InGettingQuery", "여기왔음...222" + "\n");

        APIDownLoad apiDownLoad = new APIDownLoad();
        Vector<String> mVector = new Vector<String>();
        Vector<String> dbVector = new Vector<String>();
        String pTblName = "";
        String dbInsUpdDelQuery = "";
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";

        // 글로벌클래스(GlobalMemberValues) 배열변수(dbTableNameGroupForApi)에
        // 저장된 데이터베이스 테이블들을 가져와 String 배열변수에 저장한다.
        String[] dbTableArr = paramTblArr;
        int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
        for (int i = 0; i < allTableSize ; i++) {
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", dbTableArr[i] + "\n");
            pTblName = dbTableArr[i];   // 처리할 테이블
            // 테이블이 내부DB(SQLite) 에 있을 때에만 테이블 삭제후 입력 처리한다.
            if (checkTable(pTblName) > 0) {
                if (pTblName.equals("member2")) {
                    // 테이블 member2 다운로드일 경우 기존 데이터를 삭제하지 않는다.
                } else {
                    //dbHandler.deleteTableData(pTblName);    //-------- 1. 입력처리전 먼저 현재 DB 에 있는 데이터를 삭제(delete)
                    //1. 입력처리전 먼저 현재 DB 에 있는 데이터를 삭제(delete)
                    dbInsUpdDelQuery = "delete from " + pTblName;
                    dbVector.addElement(dbInsUpdDelQuery);
                }
                mVector = apiDownLoad.getApiDownLoadVectorData(pTblName);   // DB 처리용 쿼리조합 Vector 값 저장
                for (String strVec : mVector) {
                    String[] vecStrSplit = strVec.split(GlobalMemberValues.STRSPLITTER1);
                    //dbExecuteWrite(vecStrSplit[1]);

                    // member2 데이터 다운로드일 경우
                    if (pTblName.equals("member2")) {
                        String tempUid = vecStrSplit[0];
                        // 회원 아이디로 검색해서 안드로이드 포스 DB 에 해당 회원이 있는지 확인하여
                        // 회원이 있을 경우 수정쿼리문으로, 없을 경우 등록쿼리문으로 처리한다.
                        String tempSchSql = "select count(uid) from " + pTblName + " where uid = '" + tempUid + "' ";
                        if (GlobalMemberValues.getIntAtString(dbExecuteReadReturnString(tempSchSql)) > 0) {
                            //2. 다운로드 데이터 수정(update)
                            dbVector.addElement(vecStrSplit[2]);
                        } else {
                            //2. 다운로드 데이터 등록(insert)
                            dbVector.addElement(vecStrSplit[1]);
                        }
                    } else {
                        //2. 다운로드 데이터 등록(insert)
                        dbVector.addElement(vecStrSplit[1]);
                    }

                }
            }
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", " 처리한 테이블 : " + pTblName + "\n");
        }

        // 다운로드 Log 내용 기록 ----------------------------------------------------------------------------
        String syncInsertSql = "insert into sync_data_log (sidx, stcode, syncType, isCloudUpload) values (" +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " '" + GlobalMemberValues.MSYNCDATATYPE + "', " +
                " '0' " +
                ")";
        dbVector.addElement(syncInsertSql);
        // ---------------------------------------------------------------------------------------------------

        for (String tempQuery : dbVector) {
            GlobalMemberValues.logWrite("InGettingQuery", "dbInitDataQuery : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        returnResult = dbExecuteWriteForTransactionReturnResult(dbVector);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Data Download Error from Cloud Server", "Close");
            return;
        } else { // 정상처리일 경우
            GlobalMemberValues.downloadImageFromCloud(paramItemGroup);
        }

        GlobalMemberValues.logWrite("InGettingQuery", "여기왔음...333" + "\n");
    }
    /******************************************************************************************/


    /** API 데이터 다운로드 후 데이터베이스 입력 메소드 *******************/
    public void insertDownloadDataInApiProcessing(String[] paramTblArr, String paramItemGroup, int paramInternetCheckType) {
        GlobalMemberValues.logWrite("InApiProcessing", "여기왔음...111" + "\n");

        GlobalMemberValues.logWrite("InApiProcessing", "paramInternetCheckType : " + paramInternetCheckType + "\n");

        if (paramInternetCheckType == 0) {
            if (!GlobalMemberValues.isOnline().equals("00")) {
                return;
            }
        }

        GlobalMemberValues.logWrite("InApiProcessing", "여기왔음...222" + "\n");

        APIDownLoad apiDownLoad = new APIDownLoad();
        String pTblName = "";

        // 글로벌클래스(GlobalMemberValues) 배열변수(dbTableNameGroupForApi)에
        // 저장된 데이터베이스 테이블들을 가져와 String 배열변수에 저장한다.
        String[] dbTableArr = paramTblArr;
        final int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
        for (int i = 0; i < allTableSize ; i++) {
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", dbTableArr[i] + "\n");
            pTblName = dbTableArr[i];   // 처리할 테이블
            // 테이블이 내부DB(SQLite) 에 있을 때에만 테이블 삭제후 입력 처리한다.
            if (checkTable(pTblName) > 0) {
                apiDownLoad.getApiDownLoadVectorData(pTblName);   // DB 처리용 쿼리조합 Vector 값 저장
            }
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", " 처리한 테이블 : " + pTblName + "\n");
            GlobalMemberValues.loadingProgressBarUpdate(allTableSize,i);
            if (GlobalMemberValues.GLOBALNETWORKSTATUS == NetworkUtil.TYPE_NOT_CONNECTED) {
                Log.e("jihun park ","NetworkUtil.TYPE_NOT_CONNECTEDNetworkUtil.TYPE_NOT_CONNECTED");
                i = 9999;
                // 종료
                GlobalMemberValues.b_datadownload_not_complete = true;
            }
        }

        Vector<String> dbVector = new Vector<String>();
        // 트랜잭션 처리후 결과값 저장 객체
        String returnResult = "";

        // 다운로드 Log 내용 기록 ----------------------------------------------------------------------------
        String syncInsertSql = "insert into sync_data_log (sidx, stcode, syncType, isCloudUpload) values (" +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " '" + GlobalMemberValues.MSYNCDATATYPE + "', " +
                " '0' " +
                ")";
        dbVector.addElement(syncInsertSql);
        // ---------------------------------------------------------------------------------------------------

        for (String tempQuery : dbVector) {
            GlobalMemberValues.logWrite("InApiProcessing", "dbInitDataQuery : " + tempQuery + "\n");
        }

        // 트랜잭션으로 DB 처리한다.
        returnResult = dbExecuteWriteForTransactionReturnResult(dbVector);
        if (returnResult == "N" || returnResult == "") {
            GlobalMemberValues.displayDialog(mContext, "Warning", "Data Download Error from Cloud Server", "Close");
            return;
        } else { // 정상처리일 경우
            switch (paramItemGroup) {
                case "store" : {
                    // 스토어 데이터 다운로드일 경우 스토어정보를 초기화한다.
                    MainActivity.setStoreInformationInit();
                    break;
                }
                case "service" : {
                    // 서비스/카테고리 데이터 다운로드일 경우 이미지를 다운로드한다.
                    GlobalMemberValues.logWrite("InApiProcessing", "여기 실행이오...1" + "\n");
                    ImageDownload imgDownload = new ImageDownload();
                    imgDownload.getDownloadCloudFiles("service");
                    GlobalMemberValues.logWrite("InApiProcessing", "여기 실행이오...2" + "\n");
                }
                case "member" : {
                    try {
                        Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITSECAFTERDOWNLOAD);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        GlobalMemberValues.logWrite("InApiProcessing", "여기왔음...333" + "\n");
    }
    /******************************************************************************************/



    /** API 데이터 다운로드 후 데이터베이스 입력 메소드 *******************/
    public void insertDatabaseAfterAPIDownload_Original() {
        APIDownLoad apiDownLoad = new APIDownLoad();
        Vector<String> mVector = new Vector<String>();
        String pTblName = "";

        // 글로벌클래스(GlobalMemberValues) 배열변수(dbTableNameGroupForApi)에
        // 저장된 데이터베이스 테이블들을 가져와 String 배열변수에 저장한다.
        String[] dbTableArr = GlobalMemberValues.dbTableNameGroupForApi;
        int allTableSize = dbTableArr.length;   // 데이터베이스 테이블 배열 수
        for (int i = 0; i < allTableSize ; i++) {
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", dbTableArr[i] + "\n");
            pTblName = dbTableArr[i];   // 처리할 테이블
            // 테이블이 내부DB(SQLite) 에 있을 때에만 테이블 삭제후 입력 처리한다.
            if (checkTable(pTblName) > 0) {
                dbHandler.deleteTableData(pTblName);    //-------- 1. 입력처리전 먼저 현재 DB 에 있는 데이터를 삭제(delete)
                mVector = apiDownLoad.getApiDownLoadVectorData(pTblName);   // DB 처리용 쿼리조합 Vector 값 저장
                for (String strVec : mVector) {
                    String[] vecStrSplit = strVec.split(GlobalMemberValues.STRSPLITTER1);
                    dbExecuteWrite(vecStrSplit[1]);     //-------- 2. 다운로드 데이터 등록(insert)
                    //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", " INSERT QUERY : " + vecStrSplit[1] + "\n");
                }
            }
            //GlobalMemberValues.logWrite("DatabaseInit-insertDatabaseAfterAPIDownload", " 처리한 테이블 : " + pTblName + "\n");
        }

        // 데이터 등록이 완료되면 sync_data_log 에 기록한다.
        String syncType = "MAIN";
        String syncInsertSql = "insert into sync_data_log (sidx, stcode, syncType, isCloudUpload) values (" +
                " '" + GlobalMemberValues.STORE_INDEX + "', " +
                " '" + GlobalMemberValues.STATION_CODE + "', " +
                " '" + syncType + "', " +
                " '0' " +
                ")";
    }
    /******************************************************************************************/

    /** Close 메소드 이후에 메소드나 변수를 정의하면 안된다!!!!!!!!!!!!!!!!!!!!!**/
    /** DB Handler 객체를 닫는 메소드 ***************************************/
    public void closeDBHanlder() {
        dbHandler.close();
    }
    /**********************************************************************/


    /**
     * 테이블명 및 컬럼명 가져오는 구문
     //테이블명 가져오기
     List<String> listTableNames = this.dbHandler.getTableNames();
     TextView tvTableNames = (TextView)findViewById(R.id.tvTableNames);
     StringBuilder tvTableNamesTextBuilder = new StringBuilder();
     tvTableNamesTextBuilder.append("테이블 이름들:");
     for (String tableName : listTableNames) {
     tvTableNamesTextBuilder.append('\n' + tableName);
     }
     //컬럼명 가져오기
     List<String> listColumnNames = this.dbHandler.getColumnNames("member2");
     tvTableNamesTextBuilder.append("\n\n테이블 member2 의 컬럼들:");
     for (String columnName : listColumnNames) {
     tvTableNamesTextBuilder.append('\n' + columnName);
     }

     tvTableNames.setText(tvTableNamesTextBuilder);
     */
}
