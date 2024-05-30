package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-09-23.
 */


/** 테이블 생성을 위한 스크립트. */
/******************************************************************************************/
public class SqlStatements {
    // 테이블 salon_storestationinfo 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONINFO =
            "CREATE TABLE IF NOT EXISTS salon_storestationinfo ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "stSerialNumber nvarchar(100)," +
                    "stNumber smallint," +
                    "stname nvarchar(100) ," +
                    "mainYN varchar(2) DEFAULT 'N'," +
                    "useYN varchar(2) DEFAULT 'Y'," +
                    "delyn varchar(2) DEFAULT 'N'," +
                    "pushreceiveyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now','localtime'))," +
                    "mdate datetime, " +
                    "cashinoutyn nvarchar(2) DEFAULT 'Y'," +
                    "paymentyn nvarchar(2) DEFAULT 'Y'," +
                    "paymenttype nvarchar(200) DEFAULT 'cash,creditcard,giftcard,point,other', " +
                    "sttype nvarchar(2) DEFAULT 'R', " +
                    "eodyn nvarchar(2) DEFAULT 'Y' " +
                    ")";

    // 테이블 salon_storeservice_sub_setmenu 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICESUBSETMENU =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_sub_setmenu ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "sidx int,"+
                    "midx int,"+
                    "svcidx int,"+
                    "orgsvcidx int,"+
                    "sessionValue nvarchar(50),"+
                    "orgServicePrice money,"+
                    "orgCommissionratioType nvarchar(2),"+
                    "orgCommissionratio money,"+
                    "orgPointraio money,"+
                    "orgSubServiceTime smallint,"+
                    "wdate datetime DEFAULT (datetime('now', 'localtime')),"+
                    "mdate datetime,"+
                    "org_pos_main_code nvarchar(50),"+
                    "org_pos_sub_code nvarchar(50)" +
                    ")";


    // 테이블 salon_storeservice_sub_order 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICESUBORDER =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_sub_order ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "soidx int," +
                    "ordervalue int" +
                    ")";


    // 테이블 salon_storeservice_sub 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICESUB =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_sub ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "midx int, " +
                    "servicename nvarchar(200), " +
                    "servicename2 nvarchar(200), " +
                    "servicename3 nvarchar(200), " +
                    "servicenamealt nvarchar(500), " +
                    "timefee nvarchar(200), " +
                    "description ntext, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "service_price money, " +
                    "pos_main_code nvarchar(50), " +
                    "pos_sub_code nvarchar(50), " +
                    "delyn nvarchar(2) DEFAULT 'N', " +
                    "subServiceTime int DEFAULT 0, " +
                    "positionNo smallint, " +
                    "commissionratioType nvarchar(2), " +
                    "commissionratio money, " +
                    "pointratio money, " +
                    "saleprice money, " +
                    "salestart nvarchar(50), " +
                    "saleend nvarchar(50), " +
                    "strFileName nvarchar(100), " +
                    "strFilePath nvarchar(200), " +
                    "strOrgFileName nvarchar(100), " +
                    "setmenuYN nvarchar(2) DEFAULT 'N', " +
                    "setmenuListArr nvarchar(1000), " +
                    "activeyn nvarchar(2) DEFAULT 'Y', " +
                    "taxfreeyn nvarchar(2) DEFAULT 'Y', " +
                    "timemenuyn nvarchar(2) DEFAULT 'N', " +
                    "timemenutime nvarchar(20), " +
                    "modifiertype nvarchar(2) DEFAULT 'A', " +
                    "kitchenprintyn nvarchar(2) DEFAULT 'Y', " +
                    "kitchenprintnum nvarchar(40) DEFAULT '1', " +
                    "dcapplyyn nvarchar(2) DEFAULT 'Y', " +
                    "weekdays nvarchar(30), " +
                    "colorNo smallint, " +
                    "taxtype nvarchar(2) DEFAULT 'S', " +
                    "taxvalues nvarchar(50) DEFAULT '', " +
                    "taxexemptvalues nvarchar(50) DEFAULT '', " +
                    "mobileprice money, " +
                    "scaleuseyn nvarchar(2) DEFAULT 'N', " +
                    "tareidx int null, " +
                    "tareweight money null, " +
                    "tareqty int null, " +
                    "taretotalweight money null, " +
                    "perweight money null, " +
                    "dynamicpriceyn nvarchar(2) DEFAULT 'N', " +
                    "barcode nvarchar(200) DEFAULT '',  " +
                    "divideryn nvarchar(2) DEFAULT 'N',  " +
                    "nameforlabel nvarchar(300) DEFAULT '', " +
                    "imageusezone nvarchar(50) DEFAULT 'P,K,O', " +
                    "menuusezone nvarchar(50) DEFAULT 'P,K,O', " +
                    "mdforceyn nvarchar(2) DEFAULT 'Y', " +
                    "showynifzero_m nvarchar(2) DEFAULT 'Y', " +
                    "showynifzero_k nvarchar(2) DEFAULT 'Y', " +
                    "labelprintyn nvarchar(2) DEFAULT 'N', " +
                    "labelprintnum nvarchar(50) DEFAULT '', " +
                    // 05032023
                    "wmuseyn nvarchar(2) DEFAULT 'N', " +
                    // 09142023
                    "wmtype nvarchar(2) DEFAULT '', " +
                    "wmbean nvarchar(200) DEFAULT '' " +
                    ")";


    // 테이블 salon_storeservice_main_order 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEMAINORDER =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_main_order ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "oidx int," +
                    "ordervalue int" +
                    ")";


    // 테이블 salon_storeservice_main 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEMAIN =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_main ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "servicename nvarchar(50), " +
                    "servicename2 nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "pos_main_code nvarchar(50), " +
                    "delyn nvarchar(50) DEFAULT 'N', " +
                    "positionNo smallint, " +
                    "colorNo smallint, " +
                    "commissionratio money DEFAULT 0.0, " +
                    "pointratio money DEFAULT 0.0, " +
                    "menusu int DEFAULT 0, " +
                    "taxfreeyn nvarchar(2) DEFAULT 'Y', " +
                    "kitchenprintnum nvarchar(40) DEFAULT '1', " +
                    "categoryusezone nvarchar(50) DEFAULT 'P,K,O', " +
                    "labelprintnum nvarchar(40) DEFAULT '' " +
                    ")";


    // 테이블 salon_storeproduct_ipkohistory 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPRODUCTIPKOHISTORY =
            "CREATE TABLE IF NOT EXISTS salon_storeproduct_ipkohistory ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "SEQ int, " +
                    "sidx int, " +
                    "product_idx int, " +
                    "KIND nvarchar(50), " +
                    "IDN_COD nvarchar(50), " +
                    "NAM_COD nvarchar(50), " +
                    "DES_COD nvarchar(100), " +
                    "CAT_COD nvarchar(50), " +
                    "ADDQTY int, " +
                    "ADDDATE datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 salon_storeproduct 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPRODUCT =
            "CREATE TABLE IF NOT EXISTS salon_storeproduct ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "name nvarchar(200), " +
                    "description ntext, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "price money, " +
                    "pos_code nvarchar(50), " +
                    "delyn nvarchar(2) DEFAULT 'N', " +
                    "pointratio money, " +
                    "saleprice money, " +
                    "salestart nvarchar(50), " +
                    "saleend nvarchar(50), " +
                    "strFileName nvarchar(100), " +
                    "strFilePath nvarchar(200), " +
                    "strOrgFileName nvarchar(100), " +
                    "activeyn nvarchar(2) DEFAULT 'Y', " +
                    "productCode nvarchar(200), " +
                    "taxfreeyn nvarchar(2) DEFAULT 'Y', " +
                    "dcapplyyn nvarchar(2) DEFAULT 'Y', " +
                    "onhand int DEFAULT 0, " +
                    "productalertqty int DEFAULT 0 " +
                    ")";


    // 테이블 salon_storephoto 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPHOTO =
            "CREATE TABLE IF NOT EXISTS salon_storephoto ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "photo nvarchar(50), " +
                    "caption nvarchar(100), " +
                    "wdate datetime, " +
                    "mdate datetime, " +
                    "aid nvarchar(50)" +
                    ")";


    // 테이블 salon_storePG 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPG =
            "CREATE TABLE IF NOT EXISTS salon_storePG ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "pgidx int, " +
                    "pgname nvarchar(100), " +
                    "pgmid nvarchar(50), " +
                    "pgid nvarchar(50), " +
                    "pgpwd nvarchar(50), " +
                    "pgipurl nvarchar(100), " +
                    "pgport nvarchar(20), " +
                    "useyn varchar(2) DEFAULT 'Y', " +
                    "delyn varchar(2) DEFAULT 'N', " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 salon_storeinfo_worktime_forPOS 생성쿼리
    public static final String SQL_CREATE_SALONSTOREINFOWORKTIMEFORPOS =
            "CREATE TABLE IF NOT EXISTS salon_storeinfo_worktime_forPOS ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "SUN_OPN nvarchar(10) DEFAULT '', " +
                    "SUN_CLS nvarchar(10) DEFAULT '', " +
                    "MON_OPN nvarchar(10) DEFAULT '', " +
                    "MON_CLS nvarchar(10) DEFAULT '', " +
                    "TUE_OPN nvarchar(10) DEFAULT '', " +
                    "TUE_CLS nvarchar(10) DEFAULT '', " +
                    "WED_OPN nvarchar(10) DEFAULT '', " +
                    "WED_CLS nvarchar(10) DEFAULT '', " +
                    "THU_OPN nvarchar(10) DEFAULT '', " +
                    "THU_CLS nvarchar(10) DEFAULT '', " +
                    "FRI_OPN nvarchar(10) DEFAULT '', " +
                    "FRI_CLS nvarchar(10) DEFAULT '', " +
                    "SAT_OPN nvarchar(10) DEFAULT '', " +
                    "SAT_CLS nvarchar(10) DEFAULT '', " +
                    "wdate datetime" +
                    ")";


    // 테이블 salon_storeinfo_worktime 생성쿼리
    public static final String SQL_CREATE_SALONSTOREINFOWORKTIME =
            "CREATE TABLE IF NOT EXISTS salon_storeinfo_worktime ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "sworktime int, " +
                    "eworktime int, " +
                    "workWeekday nvarchar(2), " +
                    "useyn nvarchar(2)" +
                    ")";


    // 테이블 salon_storeinfo 생성쿼리
    public static final String SQL_CREATE_SALONSTOREINFO =
            "CREATE TABLE IF NOT EXISTS salon_storeinfo ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "name nvarchar(50), " +

                    // 09012023
                    "name2 nvarchar(200) default '', " +

                    "addr1 nvarchar(100), " +
                    "addr2 nvarchar(100), " +
                    "city nvarchar(50), " +
                    "state nvarchar(50), " +
                    "zip nvarchar(50), " +
                    "country nvarchar(50), " +
                    "phone nvarchar(50), " +
                    "fax nvarchar(50), " +
                    "bizhour ntext, " +
                    "servicetime1 nvarchar(50), " +
                    "servicetime2 nvarchar(50), " +
                    "wdate datetime, " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "store_workweekday nvarchar(50), " +
                    "status nvarchar(2) DEFAULT '0', " +
                    "email nvarchar(200), " +
                    "name_en nvarchar(200)" +
                    ")";


    // 테이블 salon_storegiftcard 생성쿼리
    public static final String SQL_CREATE_SALONSTOREGIFTCARD =
            "CREATE TABLE IF NOT EXISTS salon_storegiftcard ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "name nvarchar(200), " +
                    "description ntext, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "price money, " +
                    "pos_code nvarchar(50), " +
                    "delyn nvarchar(2) DEFAULT 'N', " +
                    "pointratio money, " +
                    "saleprice money, " +
                    "salestart nvarchar(50), " +
                    "saleend nvarchar(50), " +
                    "strFileName nvarchar(100), " +
                    "strFilePath nvarchar(200), " +
                    "strOrgFileName nvarchar(100)" +
                    ")";


    // 테이블 salon_storegeneral 생성쿼리
    public static final String SQL_CREATE_SALONSTOREGENERAL =
            "CREATE TABLE IF NOT EXISTS salon_storegeneral ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "managerpwd nvarchar(50), " +
                    "tax1 float DEFAULT (0.0), " +
                    "tax2 float DEFAULT (0.0), " +
                    "tax3 float DEFAULT (0.0), " +
                    "tax4 float DEFAULT (0.0), " +
                    "tax5 float DEFAULT (0.0), " +
                    "rspuseyn varchar(2) DEFAULT 'Y', " +
                    "rsprate float DEFAULT (0.0), " +
                    "categorysu smallint, " +
                    "delyn varchar(2) DEFAULT 'N', " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime," +
                    "mstart smallint DEFAULT 6, " +
                    "mend smallint DEFAULT 12, " +
                    "astart smallint DEFAULT 12, " +
                    "aend smallint DEFAULT 18, " +
                    "estart smallint DEFAULT 18, " +
                    "eend smallint DEFAULT 24, " +
                    "nstart smallint DEFAULT 24, " +
                    "nend smallint DEFAULT 6, " +
                    "takeawayyn varchar(2) DEFAULT 'Y', " +
                    "deliveryyn varchar(2) DEFAULT 'Y', " +
                    "deliverycharge money DEFAULT 0.0, " +
                    "deliveryfree money DEFAULT 0.0, " +
                    "pickupcharge money DEFAULT 0.0, " +
                    "picktype_here nvarchar(2) DEFAULT 'N'," +
                    "picktype_togo nvarchar(2) DEFAULT 'N'," +
                    "picktype_delivery nvarchar(2) DEFAULT 'N', " +
                    "phoneorder nvarchar(2) DEFAULT 'N', " +
                    "kitchenprintsu int DEFAULT 1, " +
                    "minpayforsign money DEFAULT 0.0, " +
                    "storetype nvarchar(4) DEFAULT 'J1', " +
                    "franchiseyn nvarchar(2) DEFAULT 'N', " +
                    "ordercompletesmsyn nvarchar(2) DEFAULT 'N', " +
                    "ordercompletesmsremain int DEFAULT 0, " +
                    "ordercompletesmsterm int DEFAULT 0, " +
                    "togotaxfreeyn nvarchar(2) DEFAULT 'N', " +
                    "togotaxfreetype nvarchar(50) DEFAULT 'N', " +
                    "heretaxfreeyn nvarchar(2) DEFAULT 'N', " +
                    "heretaxfreetype nvarchar(50) DEFAULT 'N', " +
                    "deliverytaxfreeyn nvarchar(2) DEFAULT 'N', " +
                    "deliverytaxfreetype nvarchar(50) DEFAULT 'N', " +
                    "dineintaxfreeyn nvarchar(2) DEFAULT 'N', " +
                    "dineintaxfreetype nvarchar(50) DEFAULT 'N', " +
                    "mobiletaxfreeyn nvarchar(2) DEFAULT 'N', " +
                    "mobiletaxfreetype nvarchar(50) DEFAULT 'N', " +
                    "taxexemptbytotalyn nvarchar(2) DEFAULT 'Y', " +
                    "modifierviewtype nvarchar(20) DEFAULT '', " +
                    "posdualdpimg nvarchar(300) DEFAULT '', " +
                    "posdualdptype nvarchar(2) DEFAULT '0', " +
                    "posreceiptlogimg nvarchar(300) DEFAULT '', " +
                    "scaleuseyn nvarchar(2) DEFAULT 'N', " +
                    "scaleunit nvarchar(10) DEFAULT '', " +
                    "scaleminweight money DEFAULT 0.0, " +
                    "scalemaxweight money DEFAULT 0.0, " +
                    "postype nvarchar(2) DEFAULT 'Q', " +
                    "scaledecimaltwiceyn nvarchar(2) DEFAULT 'N', " +
                    "webpayuseyn nvarchar(2) DEFAULT 'N', " +
                    "curbsidepickupyn nvarchar(2) DEFAULT 'N', " +
                    "sideorderyn nvarchar(2) DEFAULT 'N', " +

                    "gratuityuseyn nvarchar(2) DEFAULT 'N', " +
                    "gratuitytype nvarchar(4) DEFAULT '%', " +
                    "gratuitycustomercount money DEFAULT 1, " +
                    "gratuityvalue money DEFAULT 0.0, " +
                    "cashoutreportitems nvarchar(200) DEFAULT '<1>,<2>,<3>,<4>,<5>', " +

                    "divideruseyn nvarchar(2) DEFAULT 'N', " +
                    "labelprinteruse nvarchar(2) DEFAULT 'N', " +

                    "managerpwdnz nvarchar(100) default '0904', " +

                    "suggestiontiptype nvarchar(10) DEFAULT 'AT', " +

                    "commongratuitytype nvarchar(10) DEFAULT 'AT', " +

                    "multistationsyncuseyn nvarchar(2) DEFAULT 'N', " +

                    "addpaytype nvarchar(4) default 'A', " +
                    "addpaymoneytype nvarchar(4) default '%', " +
                    "addpaymoney money default 0, " +
                    "addpayname nvarchar(200) default '', " +
                    "addpaytaxtype nvarchar(10) default 'AT', " +

                    "scaleautoweighyn nvarchar(2) DEFAULT 'N', " +

                    // 05032023
                    "wmuseyn nvarchar(2) DEFAULT 'N', " +
                    "wmapiip nvarchar(200) DEFAULT '', " +

                    // 05112023
                    "wmoptionstsr nvarchar(1000) DEFAULT '', " +
                    "addpaycustomerselectyn nvarchar(2) DEFAULT 'N', " +

                    // 06212023
                    "addpayitempriceshowyn nvarchar(2) DEFAULT 'Y', " +
                    "cashinoutstartendcashyn nvarchar(2) DEFAULT 'Y', " +

                    // 09192023
                    "addpaynameoncustompopup nvarchar(2) DEFAULT 'Y', " +

                    // 08012023
                    "gratuitydelyn nvarchar(2) DEFAULT 'Y', " +
                    "crmuseyn nvarchar(2) DEFAULT 'Y', " +
                    "cashdcshowonreceiptyn nvarchar(2) DEFAULT 'Y', " +
                    "cashdctaxshowyn nvarchar(2) DEFAULT 'Y', " +

                    // 03252024
                    "torderuseyn nvarchar(2) DEFAULT 'N', " +
                    "torderkey nvarchar(100) DEFAULT '', " +

                    // 05302024
                    "torderapikey nvarchar(1000) DEFAULT '', " +
                    "torderpartnerid nvarchar(200) DEFAULT '', " +
                    "torderapiurl nvarchar(1000) DEFAULT '', " +

                    // 04302024
                    "qsronrestaurantyn nvarchar(2) DEFAULT 'N' " +

                    ")";


    // 테이블 salon_storeemployee_workweekday 생성쿼리
    public static final String SQL_CREATE_SALONSTOREEMPLOYEEWORKWEEKDAY =
            "CREATE TABLE IF NOT EXISTS salon_storeemployee_workweekday ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "employeeidx int," +
                    "workWeekdays nvarchar(50) DEFAULT ('1,2,3,4,5,6,7')" +
                    ")";


    // 테이블 salon_storeemployee_worktime 생성쿼리
    public static final String SQL_CREATE_SALONSTOREEMPLOYEEWORKTIME =
            "CREATE TABLE IF NOT EXISTS salon_storeemployee_worktime ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "employeeidx int, " +
                    "sworktime int, " +
                    "eworktime int, " +
                    "workWeekday nvarchar(2)" +
                    ")";


    // 테이블 salon_storeemployee_loginlog 생성쿼리
    public static final String SQL_CREATE_SALONSTOREEMPLOYEELOGINLOG =
            "CREATE TABLE IF NOT EXISTS salon_storeemployee_loginlog ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int, " +
                    "eid nvarchar(50), " +
                    "eLevel varchar(2), " +
                    "login_ip nvarchar(20), " +
                    "login_date datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 salon_storeemployee 생성쿼리
    public static final String SQL_CREATE_SALONSTOREEMPLOYEE =
            "CREATE TABLE IF NOT EXISTS salon_storeemployee ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int, " +
                    "name nvarchar(50), " +
                    "age smallint DEFAULT 0, " +
                    "phone nvarchar(50), " +
                    "email nvarchar(100), " +
                    "photo nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50) DEFAULT '', " +
                    "pos_emp_code nvarchar(50), " +
                    "SUN_OPN nvarchar(10) DEFAULT '', " +
                    "SUN_CLS nvarchar(10) DEFAULT '', " +
                    "MON_OPN nvarchar(10) DEFAULT '', " +
                    "MON_CLS nvarchar(10) DEFAULT '', " +
                    "TUE_OPN nvarchar(10) DEFAULT '', " +
                    "TUE_CLS nvarchar(10) DEFAULT '', " +
                    "WED_OPN nvarchar(10) DEFAULT '', " +
                    "WED_CLS nvarchar(10) DEFAULT '', " +
                    "THU_OPN nvarchar(10) DEFAULT '', " +
                    "THU_CLS nvarchar(10) DEFAULT '', " +
                    "FRI_OPN nvarchar(10) DEFAULT '', " +
                    "FRI_CLS nvarchar(10) DEFAULT '', " +
                    "SAT_OPN nvarchar(10) DEFAULT '', " +
                    "SAT_CLS nvarchar(50) DEFAULT '', " +
                    "delyn nvarchar(2) DEFAULT 'N', " +
                    "eid nvarchar(50), " +
                    "pwd nvarchar(100), " +
                    "eLevel varchar(2) DEFAULT '1', " +
                    "poslistviewyn nvarchar(2) DEFAULT 'Y', " +
                    "permission nvarchar(200) DEFAULT '', " +
                    "empcardnum nvarchar(200) DEFAULT '', " +
                    "emptype nvarchar(2) DEFAULT '0', " +
                    "serveraccesscode nvarchar(100) DEFAULT '', " +
                    "serveraccesspwd nvarchar(100) DEFAULT '' " +
                    ")";


    // 테이블 salon_member 생성쿼리
    public static final String SQL_CREATE_SALONMEMBER =
            "CREATE TABLE IF NOT EXISTS salon_member ( " +
                    "uid nvarchar(200), " +
                    "scode nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "pos_mem_code nvarchar(50), " +
                    "mileage money DEFAULT 0.0, " +
                    "memcardnum nvarchar(100), " +
                    "memmobile nvarchar(100), " +
                    "sidx int" +
                    ")";


    // 테이블 salon_info2 생성쿼리
    public static final String SQL_CREATE_SALONINFO2 =
            "CREATE TABLE IF NOT EXISTS salon_info2 ( " +
                    "scode nvarchar(50), " +
                    "status nvarchar(2), " +
                    "pagetype nvarchar(2), " +
                    "mdate datetime, " +
                    "aid nvarchar(50)" +
                    ")";


    // 테이블 salon_info1 생성쿼리
    public static final String SQL_CREATE_SALONINFO1 =
            "CREATE TABLE IF NOT EXISTS salon_info1 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "name nvarchar(50), " +
                    "sid nvarchar(50), " +
                    "password nvarchar(50), " +
                    "owner nvarchar(50), " +
                    "charger nvarchar(50), " +
                    "chargerphone nvarchar(50), " +
                    "chargeremail nvarchar(100), " +
                    "logofile nvarchar(50), " +
                    "wdate datetime, " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "salon_type nvarchar(50) DEFAULT 'nail', " +
                    "salon_url nvarchar(200), " +
                    "serviceitem nvarchar(100)" +
                    ")";


    // 테이블 salon_general 생성쿼리
    public static final String SQL_CREATE_SALONGENERAL =
            "CREATE TABLE IF NOT EXISTS salon_general ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "managerpwd nvarchar(50), " +
                    "tax1 float DEFAULT (0.0), " +
                    "tax2 float DEFAULT (0.0), " +
                    "tax3 float DEFAULT (0.0), " +
                    "tax4 float DEFAULT (0.0), " +
                    "tax5 float DEFAULT (0.0), " +
                    "rspuseyn nvarchar(50) DEFAULT 'Y', " +
                    "rsprate float DEFAULT (0.0), " +
                    "mdate datetime, " +
                    "appUseYN nvarchar(50) DEFAULT 'N', " +
                    "homepageyn nvarchar(2), " +
                    "homepageurl nvarchar(200), " +
                    "franchiseyn nvarchar(2) DEFAULT 'N' " +
                    ")";


    // 테이블 reservation_time 생성쿼리
    public static final String SQL_CREATE_RESERVATIONTIME =
            "CREATE TABLE IF NOT EXISTS reservation_time ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "rcode nvarchar(50), " +
                    "rdate datetime, " +
                    "employee_idx int, " +
                    "rtime nvarchar(50), " +
                    "ent_nal datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 reservation_service 생성쿼리
    public static final String SQL_CREATE_RESERVATIONSERVICE =
            "CREATE TABLE IF NOT EXISTS reservation_service ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "rcode nvarchar(50), " +
                    "service_idx int" +
                    ")";


    // 테이블 reservation 생성쿼리
    public static final String SQL_CREATE_RESERVATION =
            "CREATE TABLE IF NOT EXISTS reservation ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "rcode nvarchar(50), " +
                    "scode nvarchar(50), " +
                    "sidx int, " +
                    "employee_idx int, " +
                    "rdate datetime, " +
                    "uid nvarchar(200), " +
                    "name nvarchar(50), " +
                    "phone nvarchar(50), " +
                    "status nvarchar(50), " +
                    "wdate datetime, " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "memo ntext, " +
                    "readflag bit, " +
                    "readdatetime datetime, " +
                    "regType nvarchar(50) DEFAULT 'W'" +
                    ")";


    // 테이블 pg_info 생성쿼리
    public static final String SQL_CREATE_PGINFO =
            "CREATE TABLE IF NOT EXISTS pg_info ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pgname nvarchar(100), " +
                    "pgurl nvarchar(100), " +
                    "pgport nvarchar(20)" +
                    ")";


    // 테이블 member2 생성쿼리
    public static final String SQL_CREATE_MEMBER2 =
            "CREATE TABLE IF NOT EXISTS member2 ( " +
                    "uid nvarchar(200), " +
                    "phone nvarchar(50), " +
                    "mobile nvarchar(50), " +
                    "byear nvarchar(50), " +
                    "bmonth nvarchar(50), " +
                    "bday nvarchar(50), " +
                    "addr1 nvarchar(150), " +
                    "addr2 nvarchar(100), " +
                    "zip nvarchar(50), " +
                    "state nvarchar(50), " +
                    "city nvarchar(50), " +
                    "country nvarchar(50) DEFAULT 'United States', " +
                    "membershipcardno nvarchar(200), " +
                    "mileage money, " +
                    "memo ntext" +
                    ")";


    // 테이블 member1 생성쿼리
    public static final String SQL_CREATE_MEMBER1 =
            "CREATE TABLE IF NOT EXISTS member1 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "uid nvarchar(200), " +
                    "name nvarchar(50), " +
                    "password nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "aid nvarchar(50), " +
                    "emailreceiveyn nvarchar(50)  DEFAULT 'Y', " +
                    "lastlogin datetime DEFAULT (datetime('now', 'localtime')), " +
                    "lastvisit datetime, " +
                    "lastvisitForSale datetime, " +
                    "delyn nvarchar(50) DEFAULT 'n', " +
                    "gender nvarchar(50) DEFAULT 'f', " +
                    "grade nvarchar(50) DEFAULT '1'" +
                    ")";


    // 테이블 member_mileage 생성쿼리
    public static final String SQL_CREATE_MEMBERMILEAGE =
            "CREATE TABLE IF NOT EXISTS member_mileage ( " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "contents nvarchar(200), " +
                    "mileage money, " +
                    "uid nvarchar(200), " +
                    "mcase nvarchar(50), " +                            // 1 : 적립    2 : 사용
                    "seno INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "membershipcardno nvarchar(200), " +
                    "sid nvarchar(100), " +
                    "savetype nvarchar(2) DEFAULT 'P', " +
                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "codeforupload nvarchar(100) NULL, " +
                    "idxfromweb int null " +
                    ")";


    // 테이블 coupon_registration_no 생성쿼리
    public static final String SQL_CREATE_COUPONREGISTRATIONNO =
            "CREATE TABLE IF NOT EXISTS coupon_registration_no ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "reg_no nvarchar(50), " +
                    "useyn nvarchar(50), " +
                    "scode nvarchar(50), " +
                    "mdate datetime, " +
                    "givenyn nvarchar(50)" +
                    ")";


    // 테이블 coupon_mailingpush_send 생성쿼리
    public static final String SQL_CREATE_COUPONMAILINGPUSHSEND =
            "CREATE TABLE IF NOT EXISTS coupon_mailingpush_send ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cp_idx int, " +
                    "scode nvarchar(50), " +
                    "sidx int, " +
                    "couponTitle nvarchar(100), " +
                    "couponContents ntext, " +
                    "dcrate money, " +
                    "period_sdate datetime, " +
                    "period_edate datetime, " +
                    "coupon_type nvarchar(50), " +
                    "info1 ntext, " +
                    "info2 ntext, " +
                    "coupon_name nvarchar(50), " +
                    "off_type nvarchar(50), " +
                    "cp_img_type nvarchar(50) DEFAULT '0', " +
                    "cp_user_img nvarchar(300), " +
                    "send_nal datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 coupon_issue_history 생성쿼리
    public static final String SQL_CREATE_COUPONISSUEHISTORY =
            "CREATE TABLE IF NOT EXISTS coupon_issue_history ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cp_idx int, " +
                    "scode nvarchar(50), " +
                    "uid nvarchar(200), " +
                    "viewyn nvarchar(50) DEFAULT 'N', " +
                    "usedyn nvarchar(50) DEFAULT 'N', " +
                    "ent_nal datetime DEFAULT (datetime('now', 'localtime')), " +
                    "barcode nvarchar(50), " +
                    "sendType nvarchar(10) DEFAULT 'email', " +
                    "mailPushIdx int" +
                    ")";


    // 테이블 coupon_imgtype 생성쿼리
    public static final String SQL_CREATE_COUPONIMGTYPE =
            "CREATE TABLE IF NOT EXISTS coupon_imgtype ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cidx int, " +
                    "imgonly nchar(2)" +
                    ")";


    // 테이블 coupon 생성쿼리
    public static final String SQL_CREATE_COUPON =
            "CREATE TABLE IF NOT EXISTS coupon ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int, " +
                    "dcrate money, " +
                    "period_sdate datetime, " +
                    "period_edate datetime, " +
                    "coupon_type nvarchar(50), " +
                    "useyn nvarchar(50) DEFAULT 'y', " +
                    "info1 ntext, " +
                    "info2 ntext, " +
                    "info3 ntext, " +
                    "info4 ntext, " +
                    "info5 ntext, " +
                    "coupon_name nvarchar(50), " +
                    "coupon_tel nvarchar(50), " +
                    "off_type nvarchar(50), " +
                    "cp_img_type nvarchar(50) DEFAULT '0', " +
                    "cp_user_img nvarchar(300), " +
                    "applytype nvarchar(2) DEFAULT 'B', " +
                    "coupontype nvarchar(2) DEFAULT 'S', " +
                    "mcouponnumber nvarchar(200) DEFAULT '' " +
                    ")";


    // 테이블 admin 생성쿼리
    public static final String SQL_CREATE_ADMIN =
            "CREATE TABLE IF NOT EXISTS admin ( " +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar(30), " +
                    "admin_id nvarchar(30), " +
                    "pass nvarchar(20), " +
                    "memlevel smallint, " +
                    "department varchar(30), " +
                    "ip varchar(30), " +
                    "email varchar(100)" +
                    ")";


    // 테이블 temp_salecart 생성쿼리
    public static final String SQL_CREATE_TEMPSALECART =
            "CREATE TABLE IF NOT EXISTS temp_salecart ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "midx int, " +
                    "svcIdx int, " +
                    "svcName nvarchar(200), " +
                    "svcFileName nvarchar(100), " +
                    "svcFilePath nvarchar(200), " +
                    "svcPositionNo smallint, " +
                    "svcOrgPrice money, " +
                    "svcSetMenuYN nvarchar(2), " +

                    "sPrice money, " +
                    "sTax money, " +
                    "sQty int, " +
                    "sPriceAmount money, " +
                    "sTaxAmount money, " +
                    "sTotalAmount money, " +

                    "sCommission money, " +
                    "sPoint money, " +

                    "sCommissionAmount money, " +
                    "sPointAmount money, " +

                    "sSaleYN nvarchar(2), " +

                    "customerId nvarchar(200), " +
                    "customerName nvarchar(50), " +
                    "customerPhoneNo nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "saveType nvarchar(2), " +

                    "empIdx int, " +
                    "empName nvarchar(100), " +

                    "quickSaleYN nvarchar(2) DEFAULT 'N', " +
                    "svcCategoryName nvarchar(200), " +

                    "giftcardNumber nvarchar(200),  " +
                    "giftcardSavePrice money, " +

                    "selectedDcExtraPrice money, " +
                    "selectedDcExtraType nvarchar(20), " +
                    "selectedDcExtraAllEach nvarchar(10), " +
                    "couponNumber nvarchar(100), " +
                    "sCommissionRatioType nvarchar(2), " +
                    "sCommissionRatio money, " +
                    "sPointRatio money, " +
                    "sPriceBalAmount money, " +
                    "svcCategoryColor nvarchar(100) NULL, " +
                    "taxExempt nvarchar(2) DEFAULT 'N'," +
                    "reservationCode nvarchar(100) NULL, " +

                    "optionTxt nvarchar(300) NULL, " +
                    "optionprice money DEFAULT 0.0,  " +

                    "additionalTxt1 nvarchar(300) NULL, " +
                    "additionalprice1 money DEFAULT 0.0,  " +

                    "additionalTxt2 nvarchar(300) NULL, " +
                    "additionalprice2 money DEFAULT 0.0,  " +

                    "memoToKitchen ntext, " +

                    "discountbuttonname nvarchar(100) NULL, " +

                    "modifieridx nvarchar(200) DEFAULT '', " +

                    "modifiercode nvarchar(100) DEFAULT '', " +

                    // Discount / Extra 전의 원래 값
                    "sPriceBalAmount_org money null, " +
                    "sTaxAmount_org money null, " +
                    "sTotalAmount_org money null, " +
                    "sCommissionAmount_org money null, " +
                    "sPointAmount_org money null, " +

                    "tableidx nvarchar(100) DEFAULT '',  " +
                    "billtag nvarchar(100) DEFAULT '', " +

                    "mergednum smallint DEFAULT 0, " +
                    "subtablenum smallint DEFAULT 1, " +
                    "billnum smallint DEFAULT 1, " +

                    "kitchenprintedyn nvarchar(2) DEFAULT 'N', " +

                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)

                    "cardtryyn nvarchar(2) DEFAULT 'N', " +                      // 카드결제를 시도했는지 여부

                    "dcextratype nvarchar(2) DEFAULT '', " +                    // DC / Extra 타입 : %, $
                    "dcextravalue money DEFAULT 0, " +                           // DC / Extra 적용된 금액 또는 비율

                    "togodelitype nvarchar(2) DEFAULT 'H', " +                      // 메뉴 식사장소 타입 (H, T, D)

                    "labelprintedyn nvarchar(2) DEFAULT 'N', " +

                    "togotype nvarchar(2) DEFAULT '', " +                       // to go 주문시 타입 (C : CALL IN    W : WALK IN)

                    "pastholdcode nvarchar(1000) DEFAULT '', " +                 // merge 하기 전의 holdcode

                    "billprintedyn nvarchar(2) DEFAULT 'N', " +                 // bill 프린팅 했는지 여부

                    // 05182023
                    "billidx_byitemsplit nvarchar(20) DEFAULT '', " +                        // bill 을 item 으로 split 시 billidx

                    // 02032024
                    "tordercode nvarchar(100) DEFAULT '' " +                        // Torder 에서 받은 주문은 T 로 시작 포스에서 주문은 P 로 시작

                    ")";


    // 테이블 temp_salecart_ordered 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTORDERED =
            "CREATE TABLE IF NOT EXISTS temp_salecart_ordered ( " +
                    "idx int, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "midx int, " +
                    "svcIdx int, " +
                    "svcName nvarchar(200), " +
                    "svcFileName nvarchar(100), " +
                    "svcFilePath nvarchar(200), " +
                    "svcPositionNo smallint, " +
                    "svcOrgPrice money, " +
                    "svcSetMenuYN nvarchar(2), " +

                    "sPrice money, " +
                    "sTax money, " +
                    "sQty int, " +
                    "sPriceAmount money, " +
                    "sTaxAmount money, " +
                    "sTotalAmount money, " +

                    "sCommission money, " +
                    "sPoint money, " +

                    "sCommissionAmount money, " +
                    "sPointAmount money, " +

                    "sSaleYN nvarchar(2), " +

                    "customerId nvarchar(200), " +
                    "customerName nvarchar(50), " +
                    "customerPhoneNo nvarchar(50), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "saveType nvarchar(2), " +

                    "empIdx int, " +
                    "empName nvarchar(100), " +

                    "quickSaleYN nvarchar(2) DEFAULT 'N', " +
                    "svcCategoryName nvarchar(200), " +

                    "giftcardNumber nvarchar(200),  " +
                    "giftcardSavePrice money, " +

                    "selectedDcExtraPrice money, " +
                    "selectedDcExtraType nvarchar(20), " +
                    "selectedDcExtraAllEach nvarchar(10), " +
                    "couponNumber nvarchar(100), " +
                    "sCommissionRatioType nvarchar(2), " +
                    "sCommissionRatio money, " +
                    "sPointRatio money, " +
                    "sPriceBalAmount money, " +
                    "svcCategoryColor nvarchar(100) NULL, " +
                    "taxExempt nvarchar(2) DEFAULT 'N'," +
                    "reservationCode nvarchar(100) NULL, " +

                    "optionTxt nvarchar(300) NULL, " +
                    "optionprice money DEFAULT 0.0,  " +

                    "additionalTxt1 nvarchar(300) NULL, " +
                    "additionalprice1 money DEFAULT 0.0,  " +

                    "additionalTxt2 nvarchar(300) NULL, " +
                    "additionalprice2 money DEFAULT 0.0,  " +

                    "memoToKitchen ntext, " +

                    "discountbuttonname nvarchar(100) NULL, " +

                    "modifieridx nvarchar(200) DEFAULT '', " +

                    "modifiercode nvarchar(100) DEFAULT '', " +

                    // Discount / Extra 전의 원래 값
                    "sPriceBalAmount_org money null, " +
                    "sTaxAmount_org money null, " +
                    "sTotalAmount_org money null, " +
                    "sCommissionAmount_org money null, " +
                    "sPointAmount_org money null, " +

                    "tableidx nvarchar(100) DEFAULT '',  " +
                    "billtag nvarchar(100) DEFAULT '', " +

                    "mergednum smallint DEFAULT 0, " +
                    "subtablenum smallint DEFAULT 1, " +
                    "billnum smallint DEFAULT 1, " +

                    "kitchenprintedyn nvarchar(2) DEFAULT 'N', " +

                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)

                    "cardtryyn nvarchar(2) DEFAULT 'N', " +                      // 카드결제를 시도했는지 여부

                    "dcextratype nvarchar(2) DEFAULT '', " +                    // DC / Extra 타입 : %, $
                    "dcextravalue money DEFAULT 0, " +                           // DC / Extra 적용된 금액 또는 비율

                    "togodelitype nvarchar(2) DEFAULT 'H', " +                      // 메뉴 식사장소 타입 (H, T, D)

                    "labelprintedyn nvarchar(2) DEFAULT 'N', " +

                    "togotype nvarchar(2) DEFAULT '', " +                       // to go 주문시 타입 (C : CALL IN    W : WALK IN)

                    "pastholdcode nvarchar(1000) DEFAULT '', " +                 // merge 하기 전의 holdcode

                    "billprintedyn nvarchar(2) DEFAULT 'N', " +                 // bill 프린팅 했는지 여부

                    // 05182023
                    "billidx_byitemsplit nvarchar(20) DEFAULT '', " +                        // bill 을 item 으로 split 시 billidx

                    // 02032024
                    "tordercode nvarchar(100) DEFAULT '' " +                        // Torder 에서 받은 주문은 T 로 시작 포스에서 주문은 P 로 시작


                    ")";


    // 테이블 salon_storegiftcard_number 생성쿼리
    public static final String SQL_CREATE_SALONSTOREGIFTCARDNUMBER =
            "CREATE TABLE IF NOT EXISTS salon_storegiftcard_number ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "gcNumber nvarchar(100), " +
                    "customerId nvarchar(200), " +
                    "customerName nvarchar(100), " +
                    "remainingPrice money, "+
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_storegiftcard_number_history 생성쿼리
    public static final String SQL_CREATE_SALONSTOREGIFTCARDNUMBERHISTORY =
            "CREATE TABLE IF NOT EXISTS salon_storegiftcard_number_history ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "gcNumber nvarchar(100), " +
                    "saleItemIdx int NULL," +
                    "empIdx int NULL," +
                    "empName nvarchar(100), " +
                    "serverIdx int NULL," +
                    "serverName nvarchar(100), " +
                    "customerId nvarchar(200), " +
                    "customerName nvarchar(100), " +
                    "addUsePrice money, " +
                    "addUseType nvarchar(2), " +                     // use : U     add : A
                    "addUseMemo nvarchar(200) NULL, " +
                    "salesCode nvarchar(100) NULL, " +
                    "codeforupload nvarchar(100) NULL, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "firstsaveyn nvarchar(2) DEFAULT 'N', " +                   // 최소 적립(구매)인지 여부
                    "giftcardname nvarchar(100) NULL " +
                    ")";

    // 테이블 salon_sales_detail 생성쿼리
    public static final String SQL_CREATE_SALONSTORESALESDETAIL =
            "CREATE TABLE IF NOT EXISTS salon_sales_detail ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    // 각종 코드값 관련
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "holdCode nvarchar(100), " +                         // 홀드코드
                    "reservationCode nvarchar(100), " +                  // 예약코드

                    // 스토어, 스테이션 관련
                    "sidx int, " +                                       // 스토어코드
                    "stcode nvarchar(50)," +                             // 스테이션코드

                    // 카테고리 관련
                    "categoryCode int, " +                               // 카테고리코드 (midx)
                    "categoryName nvarchar(100), " +                     // 카테고리이름

                    // 아이템(서비스/상품/기프트카드) 관련
                    "itemIdx int null, " +                               // 아이템 인덱스
                    "itemName nvarchar(200), " +                         // 아이템 이름
                    "itemFileName nvarchar(200)," +                      // 파일이름
                    "itemFilePath nvarchar(200)," +                      // 파일경로
                    "servicePositionNo smallint null," +                 // 포지션번호
                    "qty int," +                                         // 수량
                    "salesOrgPrice money, " +                            // 원가 (단가) -- 세일전 금액

                    // 판매가 관련
                    "salesPrice money, " +                               // 판매가 (단가) -- 세일반영된 금액
                    "salesPriceAmount money, " +                         // 판매가 총액

                    // 결제금액 관련
                    "salesBalPrice money, " +                            // 실결제금액 (단가)
                    "salesBalPriceAmount money, " +                      // 실결제금액총액 -- Discount/Extra 반영된 금액

                    // 세금관련
                    "tax money," +                                       // 세금
                    "taxAmount money," +                                 // 세금 총액 (세금 x 수량)

                    // 총액
                    "totalAmount money, " +                                // 총액 (실결제금액 총액 + 세금 총액)

                    // 커미션 관련
                    "commissionRatioType nvarchar(2), " +                // 커미션타입 (%, $)
                    "commissionRatio money, " +                          // 커미션율 또는 커미션금액
                    "commission money, " +                               // 커미션 (단가)
                    "commissionAmount money, " +                         // 커미션 총액

                    // 포인트 관련
                    "pointRatio money, " +                               // 포인트율
                    "point money, " +                                    // 포인트 (단가)
                    "pointAmount money," +                               // 포인트 총액

                    // 고객정보 관련
                    "customerId nvarchar(200), " +                       // 고객아이디
                    "customerName nvarchar(100), " +                     // 고객이름
                    "customerPhone nvarchar(100), " +                    // 고객전화번호
                    "customerPosCode nvarchar(100), " +                  // 고객코드

                    // 직원정보 관련
                    "employeeIdx int null, " +                           // 직원코드
                    "employeeName nvarchar(100), " +                     // 직원이름

                    "severIdx int null, " +                             // Server 코드
                    "serverName nvarchar(100), " +                      // Server 이름

                    // 적립 기프트카드 관련
                    "giftcardNumberToSave nvarchar(200), " +             // 구매 또는 적립할 기프트카드번호
                    "giftcardSavePriceToSave money, " +                  // 키프트카드 적립할 금액

                    // 쿠폰관련
                    "couponNumber nvarchar(100), " +                     // 사용한 쿠폰번호

                    // Each Discount / Extra 관련
                    "eachDiscountExtraPrice money, " +                   // 개별 할인 / 추가 금액
                    "eachDiscountExtraType nvarchar(10), " +             // 개별 할인 / 추가 (DC : 할인   EX : 추가)
                    "eachDiscountExtraStr nvarchar(100), " +             // 개별 할인 / 추가 문자열
                    // (Extra 20%, Discount -15.52 등)

                    "dcextraforreturn money default 0.0, " +             // return 시 사용을 위한

                    // 기타정보 관련
                    "saveType nvarchar(2), " +                           // 저장 타입 (0 : 서비스   1 : 상품   2: 기프트카드)
                    "isQuickSale nvarchar(2) null, " +                      // 퀵세일여부
                    "isSale smallint null, " +                           // 세일가격인지 여부

                    "isCloudUpload smallint null, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    // 0 : 정상주문      1 : 배치전 void     2 : 배치완료      3 : 배치완료         4 : 배치후 리턴            5 : 취소
                    "status smallint null, " +                            // 거래상태
                    "delyn varchar(2) DEFAULT 'N', " +                    // 삭제여부
                    "saleDate datetime DEFAULT (datetime('now', 'localtime')), " +      // 판매일시
                    "parentSalesCode nvarchar(100), " +                    // void 나 return 일 경우 원래 세일코드
                    "parentSalesIdx int null, " +                          // void 나 return 일 경우 원래 세일 Idx
                    "returnCode nvarchar(100), " +                         // 리턴코드 (리턴일 경우 salon_sales_return 테이블과 바인딩할 코드값)

                    "optionTxt nvarchar(300) NULL, " +
                    "optionprice money DEFAULT 0.0,  " +

                    "additionalTxt1 nvarchar(300) NULL, " +
                    "additionalprice1 money DEFAULT 0.0,  " +

                    "additionalTxt2 nvarchar(300) NULL, " +
                    "additionalprice2 money DEFAULT 0.0,  " +

                    "cashoutNum int DEFAULT 0, " +                             // cash out 여부

                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부

                    "checkcompany nvarchar(200) DEFAULT '', " +        // 외부에서 결제한 회사 (배달회사)

                    "memoToKitchen ntext, " +

                    "discountbuttonname nvarchar(100) NULL, " +

                    "tableidx nvarchar(100) DEFAULT '',  " +
                    "mergednum smallint DEFAULT 0, " +
                    "subtablenum smallint DEFAULT 1, " +
                    "billnum smallint DEFAULT 1, " +
                    "tablename nvarchar(200) DEFAULT '', " +

                    "frommssql nvarchar(2) DEFAULT 'N', " +

                    "togodelitype nvarchar(2) DEFAULT 'H', " +                      // 메뉴 식사장소 타입 (H, T, D)

                    "togotype nvarchar(2) DEFAULT '', " +                       // to go 주문시 타입 (C : CALL IN    W : WALK IN)

                    "kitchenPrintedYN varchar(2) DEFAULT 'N', " +                    // 키친프린터 출력 여부.
                    "labelPrintedYN varchar(2) DEFAULT 'N', " +                    // 라벨프린터 출력 여부.

                    "wmodyn varchar(2) DEFAULT 'N', " +                    // Wingman 로봇팔 주문 여부
                    "wmodresult varchar(2) DEFAULT 'N', " +                    // Wingman 로봇팔 주문처리 여부
                    "wmodresultmsg varchar(1000) DEFAULT '', " +                    // Wingman 로봇팔 주문처리 오류 메시지
                    "wmurl varchar(1000) DEFAULT '' " +                    // Wingman 로봇팔 연결URL

                    ")";

    // 테이블 salon_sales 생성쿼리
    public static final String SQL_CREATE_SALONSTORESALES =
            "CREATE TABLE IF NOT EXISTS salon_sales ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    // 각종 코드값 관련
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "holdCode nvarchar(100), " +                         // 홀드코드
                    "reservationCode nvarchar(100), " +                  // 예약코드

                    // 스토어, 스테이션 관련
                    "sidx int, " +                                       // 스토어코드
                    "stcode nvarchar(50), " +                             // 스테이션코드

                    "qty int," +                                         // 전체수량

                    "salesBalPriceAmount money, " +                      // 실결제금액총액 -- Discount/Extra 반영된 금액
                    "taxAmount money," +                                 // 세금 총액 (세금 x 수량)
                    "totalAmount money, " +                                // 총액 (실결제금액 총액 + 세금 총액)

                    // 결제유형별 정보 -----------------------------------------------------------------------------
                    "isTotalCashUse smallint null, " +                     // 현금 사용여부
                    "isTotalCardUse smallint null, " +                     // 카드 사용여부
                    "isTotalGiftCardUse smallint null, " +                 // 기프트카드 사용여부
                    "isTotalCheckUse smallint null, " +                    // 체크 사용여부
                    "isTotalPointUse smallint null, " +                    // 포인트 사용여부

                    "useTotalCashAmount money null, " +                     // 현금 실결제금액
                    "useTotalCardAmount money null, " +                     // 카드 실결제금액
                    "useTotalGiftCardAmount money null, " +                 // 기프트카드 실결제금액
                    "useTotalCheckAmount money null, " +                    // 체크 실결제금액
                    "useTotalPointAmount money null, " +                    // 포인트 실결제금액

                    "useTotalCashRatio money null, " +                     // 현금 실결제금액 비율
                    "useTotalCardRatio money null, " +                     // 카드 실결제금액 비율
                    "useTotalGiftCardRatio money null, " +                 // 기프트카드 실결제금액 비율
                    "useTotalCheckRatio money null, " +                    // 체크 실결제금액 비율
                    "useTotalPointRatio money null, " +                    // 포인트 실결제금액 비율

                    "useTotalCashAmountAfterReturned money null, " +                     // 리턴후 남은 현금 실결제금액
                    "useTotalCardAmountAfterReturned money null, " +                     // 리턴후 남은 카드 실결제금액
                    "useTotalGiftCardAmountAfterReturned money null, " +                 // 리턴후 남은 기프트카드 실결제금액
                    "useTotalCheckAmountAfterReturned money null, " +                    // 리턴후 남은 체크 실결제금액
                    "useTotalPointAmountAfterReturned money null, " +                    // 리턴후 남은 포인트 실결제금액
                    // ---------------------------------------------------------------------------------------------

                    "commissionAmount money, " +                         // 커미션 총액
                    "pointAmount money," +                               // 포인트 총액

                    // 고객정보 관련
                    "customerId nvarchar(200), " +                       // 고객아이디
                    "customerName nvarchar(100), " +                     // 고객이름
                    "customerPhone nvarchar(100), " +                    // 고객전화번호
                    "customerPosCode nvarchar(100), " +                  // 고객코드

                    "customerAddr1 nvarchar(200) null, " +                  // 주소1
                    "customerAddr2 nvarchar(200) null, " +                  // 주소2
                    "customerCity nvarchar(100) null, " +                  // City
                    "customerState nvarchar(100) null, " +                  // State
                    "customerZip nvarchar(20) null, " +                  // Zipcode
                    "customermemo ntext null, " +                  // memo

                    "deliveryday nvarchar(10) null, " +                  // 예약배송일자
                    "deliverytime nvarchar(10) null, " +                  // 예약배송시간
                    "deliverydate datetime DEFAULT (datetime('now', 'localtime')), " +                  // 예약배송일시
                    "deliverytakeaway nvarchar(10) DEFAULT 'H', " +      // 수령방법

                    // 사용 기프트카드 관련
                    "giftcardNumberUsed nvarchar(200), " +             // 구매 또는 적립할 기프트카드번호
                    "giftcardPriceUsed money, " +                  // 키프트카드 적립할 금액

                    // Discount / Extra
                    "totalDiscountExtraPrice money, " +                  // 전체 할인/추가한 총액
                    "totalDiscountPrice money, " +                       // 전체 할인 총액
                    "totalExtraPrice money, " +                          // 전체 추가한 총액
                    "eachDiscountExtraPrice money, " +                   // Each Discount 또는 Extra 금액
                    "allDiscountExtraPrice money, " +                    // All Discount 또는 Extra 금액
                    "allDiscountExtraStr nvarchar(100), " +              // All Discount 또는 Extra 문자열
                    // (Extra 20%, Discount -15.52 등)
                    // 카드결제 Tid
                    "cardTidNumber nvarchar(100), " +                  // 카드결제 Tid 번호

                    "isCloudUpload smallint null, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "delyn varchar(2) DEFAULT 'N', " +                   // 삭제여부
                    "saleDate datetime DEFAULT (datetime('now', 'localtime')), " +     // 판매일시
                    "status smallint null, " +                           // 거래상태 (0 : 정상주문     1 : 배치전 void)
                    "deliveryStatus smallint DEFAULT 0, " +              // 0 : 배달전(조리중)  1 : 배달중   2 : 배달완료
                    "takeawayStatus smallint DEFAULT 0, " +              // 0 : 수령전(조리중)  2 : 수령완료
                    "changeMoney money DEFAULT 0.0,  " +                 // 거스름돈
                    "receiptJson ntext null, " +                          // 영수증 Json
                    "kitchenprintedyn varchar(2) DEFAULT 'N', " +        // 주방에 프린트 했는지 여부 (Y / N)

                    // 직원정보 관련
                    "employeeIdx int null, " +                           // 직원코드
                    "employeeName nvarchar(100), " +                     // 직원이름

                    "severIdx int null, " +                             // Server 코드
                    "serverName nvarchar(100), " +                      // Server 이름

                    "cashoutNum int null DEFAULT 0, " +                             // cash out 여부

                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부

                    "deliverypickupfee money DEFAULT 0.0,   " +

                    "checkcompany nvarchar(200) DEFAULT '', " +        // 외부에서 결제한 회사 (배달회사)

                    "phoneorder nvarchar(2) DEFAULT 'N', " +             // Phone Order 여부
                    "customerordernumber nvarchar(50) DEFAULT '', " +    // Customer Order Number
                    "customerpagernumber nvarchar(50) DEFAULT '', " +    // Customer Order Number

                    "returneddeliverypickupfee money DEFAULT 0, " +      // 리턴한 Delivery Pickup Fee
                    "returnedtip money DEFAULT 0, " +                    // 리턴한 TIP

                    "tablename nvarchar(200) DEFAULT '', " +
                    "tablepeoplecnt money DEFAULT 0,  " +

                    "customerEmail nvarchar(300) null,  " +

                    "salepg_ip nvarchar(30) DEFAULT '', " +              // 카드결제일 경우 사용한 단말기 ip

                    "isCloudUpload2 smallint default 0, " +               // 클라우드 Json 데이터 업로드 여부 (0 : 업로드이전  1 : 업로드완료)

                    "cancelreason ntext DEFAULT '', " +              // void 일 경우 void 사유

                    "togotype nvarchar(2) DEFAULT '' " +                       // to go 주문시 타입 (C : CALL IN    W : WALK IN)

                    ")";

    // 테이블 salon_sales_tip 생성쿼리
    public static final String SQL_CREATE_SALONSALESTIP =
            "CREATE TABLE IF NOT EXISTS salon_sales_tip ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "salesCode nvarchar(100), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "severIdx int null, " +                             // Server 코드
                    "serverName nvarchar(100), " +                      // Server 이름
                    "usedCash money DEFAULT 0.0, " +
                    "usedCard money DEFAULT 0.0, " +
                    "isCloudUpload smallint null, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "cardCom nvarchar(50) NULL, " +                       // 기 결제된 카드발행사
                    "cloudIdx int null, " +                               // 클라우드에 저장된 테이블(TBPOSEMPTIPSHIST)의 idx
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "cashoutNum int null DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "adjustedyn varchar(2) DEFAULT 'Y', " +                     // 팁 프로세싱됐는지 여부
                    "refnum nvarchar(100) DEFAULT '', " +                       // 레퍼런스 번호
                    "codeforupload nvarchar(100) NULL, " +
                    "globalUID nvarchar(50) NULL, " +                    // globaluid
                    "split_transaction_id nvarchar(100) default '' " +
                    ")";

    // 테이블 salon_clockinout 생성쿼리
    public static final String SQL_CREATE_SALONCLOCKINOUT =
            "CREATE TABLE IF NOT EXISTS salon_clockinout ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "stcode nvarchar(50), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "clockinDate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "clockinDay nvarchar(10) NULL, " +
                    "clockinTime nvarchar(10) NULL, " +
                    "clockoutDate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "clockoutDay nvarchar(10) NULL, " +
                    "clockoutTime nvarchar(10) NULL, " +
                    "clockinout_closeYN nvarchar(2) DEFAULT 'N', " +
                    "worktimeAmount int NULL, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 sync_data_log 생성쿼리
    public static final String SQL_CREATE_SYNCDATALOG =
            "CREATE TABLE IF NOT EXISTS sync_data_log ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "syncType nvarchar(50), " +
                    "isCloudUpload smallint null, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 basic_pos_information 생성쿼리
    public static final String SQL_CREATE_BASICPOSINFORMATION =
            "CREATE TABLE IF NOT EXISTS basic_pos_information ( " +
                    "sid nvarchar(50), " +
                    "scode nvarchar(50), " +
                    "sname nvarchar(100), " +
                    "sidx int," +
                    "stcode nvarchar(50), " +
                    "serialNumber nvarchar(100), " +
                    "stname nvarchar(100), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_card 생성쿼리
    public static final String SQL_CREATE_SALONSALESCARD =
            "CREATE TABLE IF NOT EXISTS salon_sales_card ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "tid nvarchar(200), " +                               // 카드 트랜잭션 코드
                    "sidx int, " +                                       // 스토어코드
                    "stcode nvarchar(50)," +                             // 스테이션코드
                    "cardCom nvarchar(50), " +                           // 카드 발행사 (VISA : V MASTER : M AMERICAN EXPRESS : A)
                    "priceAmount money, " +                              // 결제금액
                    "tipAmount money DEFAULT 0.0, " +                    // 팁 금액
                    "insertSwipeKeyin nvarchar(10)," +                   // 카드 정보 입력 방법 (IS : Insert / Swipe,    KI : Key In)
                    "status smallint null, " +                           // 거래상태
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "cardLastFourDigitNumbers nvarchar(10)," +           // 사용한 카드 뒤 4자리 번호
                    "cardRefNumber nvarchar(20)," +                      // 레퍼런스 값
                    "cardEmvAid nvarchar(20)," +                            // EMV 결제시 리턴받은 AID 값
                    "cardEmvTsi nvarchar(20)," +                            // EMV 결제시 리턴받은 Tsi 값
                    "cardEmvTvr nvarchar(20)," +                            // EMV 결제시 리턴받은 Tvr 값
                    "returnCode nvarchar(100)," +                           // 리턴시 바인딩할 코드값
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +

                    // 직원정보 관련
                    "employeeIdx int null, " +                           // 직원코드
                    "employeeName nvarchar(100), " +                     // 직원이름

                    "severIdx int null, " +                             // Server 코드
                    "serverName nvarchar(100), " +                      // Server 이름

                    "cashoutNum int null DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "split_transaction_id nvarchar(100) default '', " +

                    // 05302024
                    // 리턴시 원래 tip 금액 저장
                    "orgTip money DEFAULT 0.0 " +

                    ")";


    // 테이블 salon_sales_return 생성쿼리
    public static final String SQL_CREATE_SALONSTORESALESRETURN =
            "CREATE TABLE IF NOT EXISTS salon_sales_return ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "returnCode nvarchar(100), " +                         // 리턴코드 (salon_sales_detail 테이블과 바인딩할 코드값)

                    // 스토어, 스테이션 관련
                    "sidx int null, " +                                       // 스토어코드
                    "stcode nvarchar(50), " +                             // 스테이션코드

                    // 결제유형별 정보 -----------------------------------------------------------------------------
                    "isTotalCashUse smallint null, " +                     // 현금 사용여부
                    "isTotalCardUse smallint null, " +                     // 카드 사용여부
                    "isTotalGiftCardUse smallint null, " +                 // 기프트카드 사용여부
                    "isTotalCheckUse smallint null, " +                    // 체크 사용여부
                    "isTotalPointUse smallint null, " +                    // 포인트 사용여부

                    "useTotalCashAmount money null, " +                     // 현금 실결제금액
                    "useTotalCardAmount money null, " +                     // 카드 실결제금액
                    "useTotalGiftCardAmount money null, " +                 // 기프트카드 실결제금액
                    "useTotalCheckAmount money null, " +                    // 체크 실결제금액
                    "useTotalPointAmount money null, " +                    // 포인트 실결제금액

                    "useTotalCashRatio money null, " +                     // 현금 실결제금액 비율
                    "useTotalCardRatio money null, " +                     // 카드 실결제금액 비율
                    "useTotalGiftCardRatio money null, " +                 // 기프트카드 실결제금액 비율
                    "useTotalCheckRatio money null, " +                    // 체크 실결제금액 비율
                    "useTotalPointRatio money null, " +                    // 포인트 실결제금액 비율
                    // ---------------------------------------------------------------------------------------------

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 테이블 salon_storestationsettings_system 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSSYSTEM =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_system ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "splashuse smallint NULL," +
                    "inreverse smallint NULL," +
                    "cloudurl nvarchar(200) NULL," +
                    "downloaddata smallint NULL," +
                    "databasebackup smallint NULL," +
                    "clockinouttype smallint NULL," +
                    "synchronizationType smallint DEFAULT 0," +
                    "commisionratioType smallint DEFAULT 0," +
                    "gmailId nvarchar(200)," +
                    "gmailpwd nvarchar(200)," +
                    "departmentviewyn nvarchar(2) NULL, " +
                    "mileagesyncinselectcustomer nvarchar(2) DEFAULT 'N'," +
                    "showcostafterdcextra nvarchar(2) DEFAULT 'N'," +
                    "cardislast nvarchar(2) DEFAULT 'N'," +
                    "pointpaysavepointyn nvarchar(2) DEFAULT 'N'," +
                    "timemenuautoreload nvarchar(2) DEFAULT 'N'," +
                    "pushtype nvarchar(2) DEFAULT '1'," +
                    "devicekind nvarchar(100) DEFAULT ''," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "picktype_here nvarchar(2) DEFAULT 'N'," +
                    "picktype_togo nvarchar(2) DEFAULT 'N'," +
                    "picktype_delivery nvarchar(2) DEFAULT 'N', " +
                    "customerinfoshow nvarchar(2) DEFAULT 'N', " +
                    "customerselectreceipt nvarchar(2) DEFAULT 'N', " +
                    "empcardstartnum smallint DEFAULT 1," +
                    "empcardcountnum smallint DEFAULT 0," +
                    "giftcardstartnum smallint DEFAULT 1," +
                    "giftcardcountnum smallint DEFAULT 0, " +
                    "cashpaduse nvarchar(2) DEFAULT 'N', " +
                    "cloudkitchenprinteryn nvarchar(2) DEFAULT 'N', " +
                    "cloudkitchenprinterurl nvarchar(200) DEFAULT '', " +
                    "autoreceiptprintingyn nvarchar(2) DEFAULT 'Y', " +
                    "couponinfoview nvarchar(2) DEFAULT 'N', " +
                    "onlineorderuseyn nvarchar(2) DEFAULT 'N', " +
                    "timemenuuseyn nvarchar(2) DEFAULT 'Y', " +
                    "couponimmediately nvarchar(2) DEFAULT 'Y', " +
                    "timemenuchecktime smallint DEFAULT 10, " +
                    "maxpagernum smallint DEFAULT 50, " +
                    "pageruseyn nvarchar(2) DEFAULT 'N', " +
                    "pagernumberautoyn nvarchar(2) DEFAULT 'Y', " +
                    "productminalertyn nvarchar(2) DEFAULT 'N', " +
                    "daystouploaddata nvarchar(6) DEFAULT '10', " +
                    "batchincashoutyn nvarchar(2) DEFAULT 'N', " +       // 캐시아웃할 때 바치도 할지여부
                    "startingcashprintyn nvarchar(2) DEFAULT 'Y', " +       // Starting Cash 등록할 때 프린팅할지 여부
                    "kitchenprintfontsize nvarchar(2) DEFAULT 'R', " +              // R : 레귤러 사이즈               L : 라지 사이즈
                    "kitchenordernumbersectionviewyn nvarchar(2) DEFAULT 'Y', " +   // 키친프린팅때 order number 섹션 보여줄지 여부
                    "saledatemodifyyn nvarchar(2) DEFAULT 'N', " +           // 세일 일자 변경가능 여부
                    "salehistorypageno smallint DEFAULT 30, " +              // Sale History 한페이지에 보여질 데이터 수
                    "buttonmodifierbottomviewyn nvarchar(2) DEFAULT 'Y', " +      // 버튼형 모디파이어 창에서 하단부분의 price, add, amount 부분 보여줄지 여부
                    "receipttypeonnoselect nvarchar(2) DEFAULT 'Y', " +       // Customer Receipt Select 가 No 일 때 영수증 출력타입 (A : 모두 출력    C : Customer     M : Merchant)
                    "modifierpriceviewyn nvarchar(2) DEFAULT 'N', " +          // Modifier 가격 표시여부
                    "pagernumberheadertxt nvarchar(40) DEFAULT '', " +         // Pager 번호 헤더에 쓰일 텍스트
                    "dualdisplaypossible nvarchar(2) DEFAULT 'N', " +          // 듀얼 모니터 지원여부
                    "pricedollaryn nvarchar(2) DEFAULT 'N', " +         // 가격 앞에 $ 표시 여부
                    "dualdpadtype nvarchar(2) DEFAULT 'W', " +         // 듀얼디스플레이에 사용되는 이미지/동영상이 로컬에 저장될지 여부 (L : 로컬      W : 웹)
                    "dualdpadlocalpath nvarchar(200) DEFAULT '', " +         // 듀얼디스플레이에 사용되는 이미지/동영상의 로컬 저장경로
                    "thankyoupageyn nvarchar(2) DEFAULT 'N', " +          // 듀얼모니터일 경우 결제완료후 감사문구 페이지 여부
                    "uploadynonlysalesdata nvarchar(2) DEFAULT 'N', " +          // 세일시 해당 세일만 업로드할지 여부
                    "autosaledatauploadyn nvarchar(2) DEFAULT 'N', " +          // 세일데이터 업로드 기능 자동실행여부
                    "serverstationip1 nvarchar(4) NULL," +                 // 서버스테이션 ip1
                    "serverstationip2 nvarchar(4) NULL," +                 // 서버스테이션 ip2
                    "serverstationip3 nvarchar(4) NULL," +                 // 서버스테이션 ip3
                    "serverstationip4 nvarchar(4) NULL," +                 // 서버스테이션 ip4
                    "serverstationport nvarchar(10) NULL, " +                 // 서버스테이션 port
                    "serverport nvarchar(10) NULL, " +                 // 서버일 때 port
                    "serverclienttype nvarchar(2) DEFAULT 'N',    " +          // Server / Client 여부
                    "cloudbackupintenderbackupyn nvarchar(2) DEFAULT 'N', " +          // Tender 시 데이터베이스 백업할 때 클라우드 백업여부
                    "localkitchenprintingtype nvarchar(2) DEFAULT 'N', " +          // N : Network           S : Serial
                    "kitchenprintingtexttype nvarchar(2) DEFAULT 'I', " +          // 키친프린팅 텍스트 타입
                    "itemanimationyn nvarchar(2) DEFAULT 'N', " +          // 상품 당길때 Animation 사용여부
                    "carddirectyn nvarchar(2) DEFAULT 'N', " +           // 카드 direct 결제가능 여부
                    "modifierqtyviewyn nvarchar(2) DEFAULT 'Y', " +          // Modifier 의 수량을 영수증에서 보여줄지 여부
                    "mssqlsyncyn nvarchar(2) DEFAULT 'N', " +         // 데이터 다운로드시 mssql 에도 함께 저장여부
                    "mssqldbip nvarchar(500) DEFAULT '', " +                 // mssql db ip
                    "vrkitchenprinting nvarchar(2) DEFAULT 'N', " +          // void, return 시 키친프린팅 여부
                    "autoweborderprinting nvarchar(2) DEFAULT 'N', " +          // 온라인오더시 자동으로 receipt 프린팅 되는지 여부
                    "initcapacity smallint DEFAULT 500, " +                       // 데이터 자동 백업 초기화 기준 용량 mb
                    "qtyaddupyn nvarchar(2) DEFAULT 'N', " +          // 아이템 추가시 갯수 qty 에 추가 여부 (N : 겹치지 않게 추가, Y : 겹치게 추가 )
                    "tipaddhistoryvisibleyn nvarchar(2) DEFAULT 'Y', " +          // sale history tip add 버튼 보이게 하기 (Y : 보이게 하기 N : 안보이게 하기 )
                    "passwordyninmod nvarchar(2) DEFAULT 'N', " +          // 다른 직원이 주문을 추가, 삭제, 수정할 때 비밀번호를 물어볼지 여부
                    "tableboardtype nvarchar(2) DEFAULT '2', " +          // 테이블메인 탁자모양 타입 (A : 탁자수량에 따른 자동설정 1 : 큰타입   2 : 중간 타입, 3 : 가장 작은 타입)
                    "pagernumbershowonpickuporder nvarchar(2) DEFAULT 'N', " +      // exchange receipt 발행시 pager number 표시 여부
                    "ordernumbershowonpickuporder nvarchar(2) DEFAULT 'N', " +       // exchange receipt 발행시 order number 표시 여부
                    "cardstatususe nvarchar(2) DEFAULT 'N', " +       // 신용카드 결제시도시 결제관련 데이터 임시 저장
                    "qtyinsyn nvarchar(2) DEFAULT 'N', " +               // 수량(qty)을 수기로 입력할지 여부
                    "customernumberviewyn nvarchar(2) DEFAULT 'N', " +               // 테이블에 앉은 고객 수량 설정창 보여줄지 여부
                    "databasename nvarchar(50) DEFAULT '" + GlobalMemberValues.DATABASE_NAME + "', " + // introDemo - Database Name
                    "databasepass nvarchar(50) DEFAULT 'DhksGkDP@02)', " +  // introDemo - Database Pass
                    "mobilehost nvarchar(50) DEFAULT 'yukdaejangm', " +     // introDemo - Mobile Host
                    "cloudhost nvarchar(50) DEFAULT 'yukdaejangcloud', " +    // introDemo - Cloud Host
                    "serverpassworduse nvarchar(2) DEFAULT 'N', " +      // server 변경시 pass 입력할지 여부
                    "servercodeuse nvarchar(2) DEFAULT 'N', " +      // server 변경 사용 여부
                    "pickuptypepopupuseyn nvarchar(2) DEFAULT 'N', " +      // 퀵 세일시 pick up 타입 팝업창 사용여부
                    "printingcategoryyn nvarchar(2) DEFAULT 'N', " +      // 프린팅시 카테고리명 표시여부
                    "customer_info_here_yn nvarchar(2) DEFAULT 'N', " +      // Payment 클릭시 CRM Input 창 보이게 하는 여부
                    "customer_info_togo_yn nvarchar(2) DEFAULT 'N', " +      // Payment 클릭시 CRM Input 창 보이게 하는 여부
                    "customer_info_delivery_yn nvarchar(2) DEFAULT 'N', " +   // Payment 클릭시 CRM Input 창 보이게 하는 여부

                    "openmsgwhendeletemenu_yn nvarchar(2) DEFAULT 'N', " +   // 메뉴 삭제시 키친프린팅을 하겠냐는 메시지 오픈 여부
                    "otherpayprinting_yn nvarchar(2) DEFAULT 'N', " +   // OTHER PAYMENT (GRUB HUB, DOOR DASH, UBER) 일 때 영수증 프린팅 여부
                    "tipprintingwhentogo_yn nvarchar(2) DEFAULT 'N', " +   // to go 주문일 때 tip 관련된 부분을 영수증에 표기할 지 여부

                    "billprintpopupyn nvarchar(2) DEFAULT 'N', " +   // bill print 클릭시 팝업오픈 여부

                    "custombillsplituseyn nvarchar(2) DEFAULT 'N', " +   // bill split 시 custom split 사용여부

                    "pagernumofdigits smallint default 0, " +

                    "customeronlinecheckyn nvarchar(2) DEFAULT 'Y', " +

                    "saledatauploadpauseyn nvarchar(2) DEFAULT 'N', " +        // 세일 데이터 업로드 Pause

                    "timeviewontableyn nvarchar(2) DEFAULT 'N', " +        // 고객의 테이블 점유시간 표시여부

                    // 10272023
                    "itemdeletereasonyn nvarchar(2) DEFAULT 'N', " +        //  메뉴 삭제시 이유남겨야 될지 여부

                    // 01172024
                    "tableorderuseyn nvarchar(2) DEFAULT 'N', " +        //  테이블 오더 사용 여부


                    // 04192024
                    // 온라인 주문 개선관련
                    "pushpopupopenyn nvarchar(2) DEFAULT 'N'" +                   // push popup 창 오픈 여부

                    ")";


    // 테이블 salon_storestationsettings_deviceprinter 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "employeecommissionpolicy smallint NULL," +
                    "managerpwduseforvoid nvarchar(100) NULL," +
                    "cashdraweronoffonsalemode smallint NULL," +
                    "voidprint smallint DEFAULT 0," +
                    "returnprint smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "signatureprintyn nvarchar(2) DEFAULT 'Y', " +
                    "signatureprintseperately nvarchar(2) DEFAULT 'N', " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "modifierprintyn nvarchar(2) DEFAULT 'Y', " +
                    "cashdraweropenonreceipt nvarchar(2) DEFAULT 'Y', " +
                    "cardmerchantreceiptyn nvarchar(2) DEFAULT 'Y', " +
                    "ordernumbersectionviewyn nvarchar(2) DEFAULT 'Y', " +
                    "taxexemptprintingyn nvarchar(2) DEFAULT 'Y', " +
                    "logoprintingonreceiptyn nvarchar(2) DEFAULT 'N', " +              // 영수증 프린팅때 로고이미지 프린팅 여부
                    "logoimgpath nvarchar(200) DEFAULT '', " +         // 영수증 프린팅때 로고이미지 경로
                    "empinfoprintingyn nvarchar(2) DEFAULT 'Y', " +         // employee 정보 출력여부
                    "menulistprintingyn nvarchar(2) DEFAULT 'Y' " +         // 주문 메뉴리스트 출력여부
                    ")";

    // 테이블 salon_storestationsettings_deviceprinter2 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER2 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter2 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "kitchenprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";

    // 테이블 salon_storestationsettings_deviceprinter3 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER3 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter3 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "kitchenprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 salon_storestationsettings_deviceprinter4 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER4 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter4 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "kitchenprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";

    // 테이블 salon_storestationsettings_deviceprinter5 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER5 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter5 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "kitchenprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";

    // 테이블 salon_storestationsettings_deviceprinter6 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER6 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter6 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "kitchenprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";

    // 테이블 salon_storestationsettings_paymentgateway 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSPAYMENTGATEWAY =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_paymentgateway ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "cardchargesystemuse smallint NULL," +
                    "paymentgateway smallint NULL," +
                    "paymentgatewayid nvarchar(200) NULL," +
                    "paymentgatewaypwd nvarchar(200) NULL," +
                    "tipuse smallint NULL," +
                    "networkip1 nvarchar(4) NULL," +
                    "networkip2 nvarchar(4) NULL," +
                    "networkip3 nvarchar(4) NULL," +
                    "networkip4 nvarchar(4) NULL," +
                    "networkport nvarchar(10) NULL," +
                    "signpaduseyn nvarchar(2) DEFAULT 'Y'," +
                    "tipselectonsignature nvarchar(2) DEFAULT 'Y'," +
                    "tipselect1 money DEFAULT 5.0, " +
                    "tipselect2 money DEFAULT 10.0, " +
                    "tipselect3 money DEFAULT 15.0, " +
                    "tipselect4 money DEFAULT 20.0, " +
                    "tiplineonreceipt nvarchar(2) DEFAULT 'Y',  "  +
                    "signatureimagedeletedaysago nvarchar(6) DEFAULT '180'," +
                    "ischeckbeforecardpay nvarchar(2) DEFAULT 'N', " +               // 카드결제전 인터넷 연결 체크여부
                    "commtype smallint DEFAULT 1, " +                                // 카드연결타입
                    "keyinyn nvarchar(2) DEFAULT 'N', " +                            // key in 가능여부
                    "tipprocessingyn nvarchar(2) DEFAULT 'N', " +                    // 결제시 Tip 프로세싱을 할 경우 (Y : 결제시 팁프로세싱,   N : Batch 시 팁프로세싱)
                    "timeout nvarchar(2) DEFAULT '1', " +                            // 결제시 pax 연결제한시간
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";


    // 테이블 member_memo 생성쿼리
    public static final String SQL_CREATE_MEMBER_MEMO =
            "CREATE TABLE IF NOT EXISTS member_memo ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "uid nvarchar(200), " +
                    "memo ntext, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 테이블 salon_storememosel 생성쿼리
    public static final String SQL_CREATE_SALONSTOREMEMOSEL =
            "CREATE TABLE IF NOT EXISTS salon_storememosel ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "description nvarchar(200), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 clouddbbackup_log 생성쿼리
    public static final String SQL_CREATE_CLOUDDBBACKUPLOG =
            "CREATE TABLE IF NOT EXISTS clouddbbackup_log ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "num int NULL, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_storepart 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPART =
            "CREATE TABLE IF NOT EXISTS salon_storepart ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "partname nvarchar(100)," +
                    "useYN varchar(2) DEFAULT 'Y'," +
                    "delyn varchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now','localtime'))," +
                    "mdate datetime" +
                    ")";

    // 테이블 salon_storepart_employee 생성쿼리
    public static final String SQL_CREATE_SALONSTOREPARTEMPLOYEE =
            "CREATE TABLE IF NOT EXISTS salon_storepart_employee ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "partidx int NULL," +
                    "employeeidx int NULL," +
                    "wdate datetime DEFAULT (datetime('now','localtime')) " +
                    ")";


    // 테이블 salon_sales_batch 생성쿼리
    public static final String SQL_CREATE_SALONSALESBATCH =
            "CREATE TABLE IF NOT EXISTS salon_sales_batch ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_batch 생성쿼리
    public static final String SQL_CREATE_SALONSALESBATCHJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_batch_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "jsonstr ntext, " +                         // 세일코드
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 temp_salecart_deliveryinfo 생성쿼리
    public static final String SQL_CREATE_TEMPSALECART_DELIVERYINFO =
            "CREATE TABLE IF NOT EXISTS temp_salecart_deliveryinfo ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +

                    "customerId nvarchar(200) null, " +                    // 고객아이디 (전화번호)
                    "customerName nvarchar(200) null, " +                  // 고객명
                    "customerPhone nvarchar(200) null, " +                  // 전화번호
                    "customerEmail nvarchar(300) null, " +                 // 고객이메일

                    "customerAddr1 nvarchar(200) null, " +                 // 주소1
                    "customerAddr2 nvarchar(200) null, " +                 // 주소2
                    "customerCity nvarchar(100) null, " +                  // City
                    "customerState nvarchar(100) null, " +                 // State
                    "customerZip nvarchar(20) null, " +                    // Zipcode
                    "customermemo ntext null, " +                          // memo

                    "deliveryday nvarchar(10) null, " +                  // 예약배송일자
                    "deliverytime nvarchar(10) null, " +                  // 예약배송시간
                    "deliverydate datetime DEFAULT (datetime('now', 'localtime')), " +                  // 예약배송일시
                    "deliverytakeaway nvarchar(10) DEFAULT 'H', " +      // 수령방법

                    "delyn varchar(2) DEFAULT 'N', " +                   // 삭제여부
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +     // 저장일시
                    "phoneorder varchar(2) DEFAULT 'N', " +
                    "phoneordernumber varchar(20) DEFAULT '', " +
                    "kitchenprintedyn varchar(2) DEFAULT 'N' " +
                    ")";

    // 테이블 salon_sales_web_push_realtime 생성쿼리
    public static final String SQL_CREATE_SALONSALESWEBPUSHREALTIME =
            "CREATE TABLE IF NOT EXISTS salon_sales_web_push_realtime ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    // 각종 코드값 관련
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "customerordernumber nvarchar(50) DEFAULT '', " +    // Customer Order Number
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +                                       // 스토어코드

                    // 고객정보 관련
                    "customerId nvarchar(200), " +                       // 고객아이디
                    "customerName nvarchar(100), " +                     // 고객이름
                    "customerPhone nvarchar(100), " +                    // 고객전화번호

                    "deliveryday nvarchar(10) null, " +                  // 예약배송일자
                    "deliverytime nvarchar(10) null, " +                  // 예약배송시간
                    "deliverydate datetime DEFAULT (datetime('now', 'localtime')), " +  // 예약배송일시
                    "deliverytakeaway nvarchar(10) DEFAULT 'H', " +                     // 수령방법
                    "tablename nvarchar(100) , " +                     // 테이블 번호
                    "tableidx nvarchar(100) DEFAULT '', " +                                 // 테이블 idx

                    // 08302023
                    "onlinetype varchar(2) DEFAULT 'W', " +                                 // 온라인주문타입

                    // 09152023
                    "receiptprintedyn nvarchar(2) DEFAULT 'N', " +                      // 영수증 receipt 프린팅 여부
                    "kitchenprintedyn nvarchar(2) DEFAULT 'N', " +                      // 키친프린팅 여부

                    // 10112023
                    "orderfrom nvarchar(300) DEFAULT '', " +                      // orderfrom (doordash, uber eats 등)
                    "salescodethirdparty nvarchar(300) DEFAULT '', " +            // 3rd party 주문번호

                    "viewyn varchar(2) DEFAULT 'N', " +                                 // 열람여부
                    "saleDate datetime DEFAULT (datetime('now', 'localtime')) " +     // 판매일시
                    ")";

    // 테이블 salon_storeservice_option_btn 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEOPTIONBTN =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_option_btn ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "midx int, " +
                    "svcidx int, " +
                    "btnname nvarchar(200), " +
                    "colorcode nvarchar(2) DEFAULT 'R', " +
                    "useyn nvarchar(2) DEFAULT 'Y', " +
                    "ignoreprice nvarchar(2) DEFAULT 'N', " +
                    "price money DEFAULT 0.0, " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime " +
                    ")";

    // 테이블 salon_storeservice_option 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEOPTION =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_option ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "midx int, " +
                    "svcidx int, " +
                    "optionname nvarchar(200), " +
                    "optiontype nvarchar(2) DEFAULT 'S', " +
                    "optionpilsuyn nvarchar(2) DEFAULT 'Y', " +
                    "optionuseyn nvarchar(2) DEFAULT 'Y', " +
                    "autoviewinposyn nvarchar(2) DEFAULT 'N', " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "minval smallint DEFAULT 0, " +
                    "maxval smallint DEFAULT 1, " +
                    "maxsumval smallint DEFAULT 1 " +
                    ")";

    // 테이블 salon_storeservice_option_item 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEOPTIONITEM =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_option_item ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "midx int, " +
                    "svcidx int, " +
                    "optionidx int, " +
                    "itemname nvarchar(200), " +
                    "itemprice money DEFAULT 0.0, " +
                    "itemuseyn nvarchar(2) DEFAULT 'Y', " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_storeservice_additional 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICEADDITIONAL =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_additional ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "midx int, " +
                    "svcidx int, " +
                    "addname nvarchar(200), " +
                    "addprice money DEFAULT 0.0, " +
                    "adduseyn nvarchar(2) DEFAULT 'Y', " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime " +
                    ")";

    // 테이블 salon_storeservice_commonmodifier 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICECOMMONMODIFIER =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_commonmodifier ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "optionname nvarchar(200), " +
                    "optiontype nvarchar(2) DEFAULT 'S', " +
                    "optionpilsuyn nvarchar(2) DEFAULT 'Y', " +
                    "optionuseyn nvarchar(2) DEFAULT 'Y', " +
                    "autoviewinposyn nvarchar(2) DEFAULT 'N', " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime, " +
                    "minval smallint DEFAULT 0, " +
                    "maxval smallint DEFAULT 1, " +
                    "maxsumval smallint DEFAULT 1 " +
                    ")";

    // 테이블 salon_storeservice_commonmodifier_item 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICECOMMONMODIFIERITEM =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_commonmodifier_item ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int, " +
                    "optionidx int, " +
                    "itemname nvarchar(200), " +
                    "itemprice money DEFAULT 0.0, " +
                    "itemuseyn nvarchar(2) DEFAULT 'Y', " +
                    "sortnum smallint, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_cashintout_history 생성쿼리
    public static final String SQL_CREATE_SALONSALESCASHINOUTHISTORY =
            "CREATE TABLE IF NOT EXISTS salon_sales_cashintout_history ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "inouttype nvarchar(10) DEFAULT '+', " +
                    "inoutmoney money DEFAULT 0.0, " +
                    "inoutreason nvarchar(100) DEFAULT '', " +
                    "cashoutNum int DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_employee_loginout_history 생성쿼리
    public static final String SQL_CREATE_SALONEMPLOYEELOGINOUTHISTORY =
            "CREATE TABLE IF NOT EXISTS salon_employee_loginout_history ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "loginouttype smallint DEFAULT 0, " +
                    "loginoutdatetime datetime DEFAULT (datetime('now', 'localtime')) , " +
                    "cashoutNum int DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0 " +                             // End of Day 여부
                    ")";

    // 테이블 member_salevisit 생성쿼리
    public static final String SQL_CREATE_MEMBERSALEVISIT =
            "CREATE TABLE IF NOT EXISTS member_salevisit ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "uid nvarchar(200), " +
                    "name nvarchar(50), " +
                    "lastvisitForSale datetime " +
                    ")";

    // 테이블 salon_store_cashinoutreason 생성쿼리
    public static final String SQL_CREATE_SALONSTORECASHINOUTREASON =
            "CREATE TABLE IF NOT EXISTS salon_store_cashinoutreason ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "description nvarchar(200), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_cashout_json 생성쿼리
    public static final String SQL_CREATE_SALONSALESCASHOUTJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_cashout_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "jsonstr ntext, " +
                    "cashoutNum int DEFAULT 0, " +
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "isCloudUpload smallint DEFAULT 0 " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    ")";

    // 테이블 salon_sales_customerordernumber 생성쿼리
    public static final String SQL_CREATE_SALONSALESCUSTOMERORDERNUMBER =
            "CREATE TABLE IF NOT EXISTS salon_sales_customerordernumber ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "customerordernumber nvarchar(4) DEFAULT ''," +       // 고객용 주문번호
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_customerpagernumber 생성쿼리
    public static final String SQL_CREATE_SALONSALESCUSTOMERPAGERNUMBER =
            "CREATE TABLE IF NOT EXISTS salon_sales_customerpagernumber ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "customerpagernumber nvarchar(4) DEFAULT ''," +       // 고객용 페이저 번호
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_phoneordernumber 생성쿼리
    public static final String SQL_CREATE_SALONSALESPHONEORDERNUMBER =
            "CREATE TABLE IF NOT EXISTS salon_sales_phoneordernumber ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "phoneordernumber nvarchar(4) DEFAULT ''," +       // phone order number
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_store_deliveryappcompany 생성쿼리
    public static final String SQL_CREATE_SALONSTOREDELIVERYCOMPANY =
            "CREATE TABLE IF NOT EXISTS salon_store_deliveryappcompany ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int," +
                    "appname nvarchar(200) DEFAULT '', " +
                    "useyn nvarchar(2) DEFAULT 'Y', " +
                    "delyn nvarchar(2) DEFAULT 'N', " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 temp_salecart_optionadd 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTOPTIONADD =
            "CREATE TABLE IF NOT EXISTS temp_salecart_optionadd ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "svcIdx int, " +

                    "optioncategoryidx int NULL, " +
                    "optionitemidx int NULL, " +

                    "optioncategoryname nvarchar(200) DEFAULT '', " +
                    "modifiername nvarchar(200) DEFAULT '', " +
                    "optionaddname nvarchar(200) DEFAULT '', " +
                    "modifieroptionaddfullname nvarchar(400) DEFAULT '', " +

                    "modifierprice money DEFAULT 0.0, " +
                    "optionaddprice money DEFAULT 0.0, " +

                    "qty int DEFAULT 1, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "modifiercode nvarchar(100) DEFAULT '' " +

                    ")";

    // 테이블 temp_salecart_optionadd_gm 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTOPTIONADDGM =
            "CREATE TABLE IF NOT EXISTS temp_salecart_optionadd_gm ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "svcIdx int, " +

                    "optioncategoryidx int NULL, " +
                    "optionitemidx int NULL, " +

                    "optioncategoryname nvarchar(200) DEFAULT '', " +
                    "modifiername nvarchar(200) DEFAULT '', " +
                    "optionaddname nvarchar(200) DEFAULT '', " +
                    "modifieroptionaddfullname nvarchar(400) DEFAULT '', " +

                    "modifierprice money DEFAULT 0.0, " +
                    "optionaddprice money DEFAULT 0.0, " +

                    "qty int DEFAULT 1, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "modifiercode nvarchar(100) DEFAULT '' " +

                    ")";

    // 테이블 temp_salecart_optionadd_imsi 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTOPTIONADDIMSI =
            "CREATE TABLE IF NOT EXISTS temp_salecart_optionadd_imsi ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "svcIdx int, " +

                    "optioncategoryidx int NULL, " +
                    "optionitemidx int NULL, " +

                    "optioncategoryname nvarchar(200) DEFAULT '', " +
                    "modifiername nvarchar(200) DEFAULT '', " +
                    "optionaddname nvarchar(200) DEFAULT '', " +
                    "modifieroptionaddfullname nvarchar(400) DEFAULT '', " +

                    "modifierprice money DEFAULT 0.0, " +
                    "optionaddprice money DEFAULT 0.0, " +

                    "qty int DEFAULT 1, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "modifiercode nvarchar(100) DEFAULT '' " +

                    ")";

    // 테이블 temp_salecart_optionadd_imsi_gm 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTOPTIONADDIMSIGM =
            "CREATE TABLE IF NOT EXISTS temp_salecart_optionadd_imsi_gm ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int, " +
                    "stcode nvarchar(50), " +
                    "svcIdx int, " +

                    "optioncategoryidx int NULL, " +
                    "optionitemidx int NULL, " +

                    "optioncategoryname nvarchar(200) DEFAULT '', " +
                    "modifiername nvarchar(200) DEFAULT '', " +
                    "optionaddname nvarchar(200) DEFAULT '', " +
                    "modifieroptionaddfullname nvarchar(400) DEFAULT '', " +

                    "modifierprice money DEFAULT 0.0, " +
                    "optionaddprice money DEFAULT 0.0, " +

                    "qty int DEFAULT 1, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "modifiercode nvarchar(100) DEFAULT '' " +

                    ")";

    // 테이블 salon_sales_receipt_json 생성쿼리
    public static final String SQL_CREATE_SALONSALESRECEIPTJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_receipt_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "jsonstr ntext, " +
                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_kitchen_json 생성쿼리
    public static final String SQL_CREATE_SALONSALESKITCHENJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_kitchen_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "jsonstr ntext, " +
                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_cashintout_history_cashlist 생성쿼리
    public static final String SQL_CREATE_SALONSALESCASHINOUTHISTORYLIST =
            "CREATE TABLE IF NOT EXISTS salon_sales_cashintout_history_cashlist ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chidx int NULL," +
                    "cashtype1 int DEFAULT 0, " +
                    "cashtype2 int DEFAULT 0, " +
                    "cashtype3 int DEFAULT 0, " +
                    "cashtype4 int DEFAULT 0, " +
                    "cashtype5 int DEFAULT 0, " +
                    "cashtype6 int DEFAULT 0, " +
                    "cashtype7 int DEFAULT 0, " +
                    "cashtype8 int DEFAULT 0, " +
                    "cashtype9 int DEFAULT 0, " +
                    "cashtype10 int DEFAULT 0, " +
                    "cashtype11 int DEFAULT 0, " +
                    "cashtype12 int DEFAULT 0, " +
                    "cashtype13 int DEFAULT 0, " +
                    "cashtype14 int DEFAULT 0, " +
                    "cashtype15 int DEFAULT 0, " +
                    "cashtype16 int DEFAULT 0, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_storeservice_sub_timemenuprice 생성쿼리
    public static final String SQL_CREATE_SALONSTORESERVICESUBTIMEMENUPRICE =
            "CREATE TABLE IF NOT EXISTS salon_storeservice_sub_timemenuprice ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int, " +
                    "midx int, " +
                    "svcidx int,  " +

                    "m_0 money DEFAULT 0.0, " +
                    "m_1 money DEFAULT 0.0, " +
                    "m_2 money DEFAULT 0.0, " +
                    "m_3 money DEFAULT 0.0, " +
                    "m_4 money DEFAULT 0.0, " +
                    "m_5 money DEFAULT 0.0, " +
                    "m_6 money DEFAULT 0.0, " +

                    "a_0 money DEFAULT 0.0, " +
                    "a_1 money DEFAULT 0.0, " +
                    "a_2 money DEFAULT 0.0, " +
                    "a_3 money DEFAULT 0.0, " +
                    "a_4 money DEFAULT 0.0, " +
                    "a_5 money DEFAULT 0.0, " +
                    "a_6 money DEFAULT 0.0, " +

                    "e_0 money DEFAULT 0.0, " +
                    "e_1 money DEFAULT 0.0, " +
                    "e_2 money DEFAULT 0.0, " +
                    "e_3 money DEFAULT 0.0, " +
                    "e_4 money DEFAULT 0.0, " +
                    "e_5 money DEFAULT 0.0, " +
                    "e_6 money DEFAULT 0.0, " +

                    "n_0 money DEFAULT 0.0, " +
                    "n_1 money DEFAULT 0.0, " +
                    "n_2 money DEFAULT 0.0, " +
                    "n_3 money DEFAULT 0.0, " +
                    "n_4 money DEFAULT 0.0, " +
                    "n_5 money DEFAULT 0.0, " +
                    "n_6 money DEFAULT 0.0, " +

                    "mdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";

    // 테이블 salon_sales_signedimg 생성쿼리
    public static final String SQL_CREATE_SALONSALESSIGNEDIMG =
            "CREATE TABLE IF NOT EXISTS salon_sales_signedimg ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "salesCode nvarchar(100), " +
                    "signedimgdir nvarchar(300), " +
                    "delyn varchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 테이블 temp_mileagecart 생성쿼리
    public static final String SQL_CREATE_TEMPMILEAGECART =
            "CREATE TABLE IF NOT EXISTS temp_mileagecart ( " +
                    "uid nvarchar(200), " +
                    "mileage money DEFAULT 0.0 " +
                    ")";

    // 테이블 salon_storediscountbutton 생성쿼리
    public static final String SQL_CREATE_SALONSTOREDISCOUNTBUTTON =
            "CREATE TABLE IF NOT EXISTS salon_storediscountbutton ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "aid nvarchar(100) NULL, " +
                    "name nvarchar(100) NULL, " +
                    "discount money NULL,"+
                    "discounttype nvarchar(2) DEFAULT '%'," +
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "sortnum smallint DEFAULT 1 " +
                    ")";

    // 테이블 kitchenprinting_log 생성쿼리
    public static final String SQL_CREATE_KITCHENPRINTINGLOG =
            "CREATE TABLE IF NOT EXISTS kitchenprinting_log ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "salesCode nvarchar(100) NULL, " +
                    "kitchenJson ntext null, " +                          // 영수증 Json
                    "printyn nvarchar(2) DEFAULT 'N'" +
                    ")";

    // 테이블 salon_store_tare 생성쿼리
    public static final String SQL_CREATE_SALONSTORETARE =
            "CREATE TABLE IF NOT EXISTS salon_store_tare ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "aid nvarchar(100) NULL, " +
                    "tarename nvarchar(100) NULL, " +
                    "tareweight money NULL,"+
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_endofday_json 생성쿼리
    public static final String SQL_CREATE_SALONSALESENDOFDAYJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_endofday_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "jsonstr ntext, " +
                    "endofdayDate nvarchar(20), " +
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "isCloudUpload smallint DEFAULT 0 " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    ")";

    // 테이블 salon_sales_cashout_emp 생성쿼리
    public static final String SQL_CREATE_SALONSALESCASHOUTEMP =
            "CREATE TABLE IF NOT EXISTS salon_sales_cashout_emp ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "employeeIdx int, " +
                    "employeeName nvarchar(100), " +
                    "totalcash money DEFAULT 0.0, " +
                    "totalcreditcard money DEFAULT 0.0, " +
                    "totalgiftcard money DEFAULT 0.0, " +
                    "totalpoints money DEFAULT 0.0, " +
                    "totalothers money DEFAULT 0.0, " +
                    "totalamountonhand money DEFAULT 0.0, " +
                    "registerstartamount money DEFAULT 0.0, " +
                    "endingmoney money DEFAULT 0.0, " +
                    "netamountonhand money DEFAULT 0.0, " +
                    "cashoutNum int DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_sales_kitchenprintingdata 생성쿼리
    public static final String SQL_CREATE_SALONKITCHENPRINTINGDATA =
            "CREATE TABLE IF NOT EXISTS salon_sales_kitchenprintingdata ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "jsonstr ntext, " +                            // 프린팅 데이터
                    "printedyn nvarchar(2) DEFAULT 'N'," +                   // 프린팅 됐는지 여부 (Y : 완료     N : 미완료)
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_connectedbluetoothinfo 생성쿼리
    public static final String SQL_CREATE_SALONCONNECTEDBLUETOOTHINFO =
            "CREATE TABLE IF NOT EXISTS salon_connectedbluetoothinfo ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "deviceaddress nvarchar(200) DEFAULT '', " +
                    "delyn nvarchar(2) DEFAULT 'N'," +                   // 삭제여부 (Y : 삭제     N : 삭제아님)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_store_restaurant_table 생성쿼리
    public static final String SQL_CREATE_SALONSTORERESTAURANTTABLE =
            "CREATE TABLE IF NOT EXISTS salon_store_restaurant_table ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "zoneidx int NULL," +
                    "tablename nvarchar(200) DEFAULT '', " +
                    "capacity int DEFAULT 0," +
                    "colorvalue nvarchar(20) DEFAULT '', " +
                    "tabletype nvarchar(50) DEFAULT '', " +
                    "chargeridx int NULL," +
                    "pagernum nvarchar(20) DEFAULT '', " +
                    "xvalue money DEFAULT 0," +
                    "yvalue money DEFAULT 0," +
                    "xvaluerate money DEFAULT 0," +
                    "yvaluerate money DEFAULT 0," +
                    "useyn nvarchar(2) DEFAULT 'Y', " +
                    "deleteyn nvarchar(2) DEFAULT 'N', " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "boxtype nvarchar(50) DEFAULT '0', " +
                    "boxkinds nvarchar(50) DEFAULT 'table',  " +
                    "quicksaleyn nvarchar(2) DEFAULT 'N', " +
                    "size nvarchar(2) DEFAULT 'M' " +
                    ")";

    // 테이블 salon_store_restaurant_tablezone 생성쿼리
    public static final String SQL_CREATE_SALONSTORERESTAURANTTABLEZONE =
            "CREATE TABLE IF NOT EXISTS salon_store_restaurant_tablezone ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "zonename nvarchar(200) DEFAULT '', " +
                    "tablecounts int DEFAULT 0," +
                    "zonetype nvarchar(100) DEFAULT '', " +
                    "useyn nvarchar(2) DEFAULT 'Y', " +
                    "deleteyn nvarchar(2) DEFAULT 'N', " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_store_restaurant_table_split 생성쿼리
    public static final String SQL_CREATE_SALONSTORERESTAURANTTABLESPLIT =
            "CREATE TABLE IF NOT EXISTS salon_store_restaurant_table_split ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tableidx nvarchar(100) null, " +
                    "subtablenum smallint null, " +
                    "holdcode nvarchar(200) null, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_store_restaurant_table_peoplecnt 생성쿼리
    public static final String SQL_CREATE_SALONSTORERESTAURANTTABLEPEOPLECNT =
            "CREATE TABLE IF NOT EXISTS salon_store_restaurant_table_peoplecnt ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tableidx nvarchar(100) null, " +
                    "subtablenum smallint DEFAULT 1, " +
                    "holdcode nvarchar(200) null, " +
                    "peoplecnt smallint null, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_store_restaurant_table_merge 생성쿼리
    public static final String SQL_CREATE_SALONSTORERESTAURANTTABLEMERGE =
            "CREATE TABLE IF NOT EXISTS salon_store_restaurant_table_merge ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tableidx nvarchar(100) null, " +
                    "holdcode nvarchar(200) null, " +
                    "mergednum smallint DEFAULT 1, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 table_split 생성쿼리
    public static final String SQL_CREATE_SALONSTORECURBSIDEPICKUP =
            "CREATE TABLE IF NOT EXISTS salon_store_curbsidepickup ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100) null, " +
                    "carinfo nvarchar(100) null, " +
                    "calltime datetime, " +
                    "phonenum nvarchar(100), " +
                    "ordernum nvarchar(30), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 table_split 생성쿼리
    public static final String SQL_CREATE_SALONSTORECURBSIDENEWSIDEMENU =
            "CREATE TABLE IF NOT EXISTS salon_store_curbsidenewsidemenu ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100) null, " +
                    "sidemenuidx int DEFAULT 0," +
                    "tableidx nvarchar(100) null, " +
                    "tablename nvarchar(100) null, " +
                    "ordertime datetime, " +
                    "ordernum nvarchar(30), " +
                    "orderdetail nvarchar(1000), " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 테이블 temp_salecart 생성쿼리
    public static final String SQL_CREATE_TEMPSALECARTDEL =
            "CREATE TABLE IF NOT EXISTS temp_salecart_del ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int null, " +
                    "stcode nvarchar(50), " +
                    "midx int null, " +
                    "svcIdx int null, " +
                    "cartIdx int null, " +

                    "alldelyn nvarchar(2) DEFAULT 'N', " +

                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)

                    "tableidx nvarchar(50) default '', " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +


                    // 02032024
                    "tordercode nvarchar(100) DEFAULT '' " +                        // Torder 에서 받은 주문은 T 로 시작 포스에서 주문은 P 로 시작

                    ")";

    // 테이블 salon_sales_kitchenprintingdata_json 생성쿼리
    public static final String SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSON =
            "CREATE TABLE IF NOT EXISTS salon_sales_kitchenprintingdata_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100) NULL, " +
                    "scode nvarchar(50) NULL, " +
                    "sidx int NULL, " +
                    "stcode nvarchar(50) NULL," +
                    "jsonstr ntext NULL, " +

                    "downloadyn nvarchar(2) DEFAULT 'N', " +

                    "printedyn nvarchar(2) DEFAULT 'N', " +

                    "uploaddate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "downloaddate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "preSalesCode nvarchar(100) DEFAULT '', " +
                    "nowtableidx nvarchar(50) DEFAULT '', " +
                    "nowtablename nvarchar(200) DEFAULT '',  " +

                    "reprintyn nvarchar(2) DEFAULT 'N' " +
                    ")";


    // 테이블 salon_sales_return_byemplyee 생성쿼리
    public static final String SQL_CREATE_SALONSTORESALESRETURNBYEMPLOYEE =
            "CREATE TABLE IF NOT EXISTS salon_sales_return_byemplyee ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    // 각종 코드값 관련
                    "salesCode nvarchar(100), " +                         // 세일코드

                    // 스토어, 스테이션 관련
                    "sidx int, " +                                       // 스토어코드
                    "stcode nvarchar(50), " +                             // 스테이션코드

                    "realreturnprice_cash money default 0, " +                      // 실제 리턴된 금액 CASH
                    "realreturnprice_card money default 0, " +                      // 실제 리턴된 금액 CARD
                    "realreturnprice_gc money default 0, " +                      // 실제 리턴된 금액 GIFT CARD
                    "realreturnprice_check money default 0, " +                      // 실제 리턴된 금액 CHECK
                    "realreturnprice_point money default 0, " +                      // 실제 리턴된 금액 POINT

                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +     // 리턴일시

                    // 직원정보 관련
                    "employeeIdx int null, " +                           // 직원코드
                    "employeeName nvarchar(100) " +                     // 직원이름
                    ")";

    public static final String SQL_CREATE_TEMPSALECARTDEL2 =
            "CREATE TABLE IF NOT EXISTS temp_salecart_del2 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100), " +
                    "stcode nvarchar(50), " +
                    "tableidx nvarchar(50) DEFAULT '', " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";

    public static final String SQL_CREATE_SALONNEWCARTCHECKBYSTATION =
            "CREATE TABLE IF NOT EXISTS salon_newcartcheck_bystation ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +
                    "tableidx nvarchar(30) default '', " +
                    "delyn nvarchar(2) default 'N', " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";

    // 테이블 bill_list 생성쿼리
    public static final String SQL_CREATE_BILLLIST =
            "CREATE TABLE IF NOT EXISTS bill_list ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "holdcode nvarchar(100), " +
                    "sidx int null, " +
                    "stcode nvarchar(50), " +
                    "billamount money default 0, " +
                    "tableidx nvarchar(50) default '', " +
                    "billsplittype nvarchar(2) default '', " +              // 0 : 메뉴별로 split     1 : evenly split     2 : amount split
                    "cartidxs nvarchar(200) default '', " +                   // 메뉴별로 split 일 경우 temp_salecart 의 idx 값 (, 로 구분)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "paidyn nvarchar(2) default 'N', " +                     // 결제됐는지 여부

                    // 04212023
                    "gratuityamt money default 0, " +                       // Common Gratuity 분할 금액
                    "subtotalamt money default 0, " +                       // Sub Total 분할 금액
                    "taxamt money default 0, " +                            // Tax 분할 금액
                    "totalamountAmt money default 0, " +                     // Total Amount 분할 금액

                    // 05152023
                    "dcextraAmt money default 0 " +                     // discount, extra 금액
                    ")";

    // 테이블 bill_list_paid 생성쿼리
    public static final String SQL_CREATE_BILLLISTPAID =
            "CREATE TABLE IF NOT EXISTS bill_list_paid ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salescode nvarchar(100) default '', " +
                    "holdcode nvarchar(100) default '', " +
                    "ordernum nvarchar(100) default '', " +
                    "sidx int null, " +
                    "stcode nvarchar(50), " +
                    "billidx int null, " +
                    "paidamount money default 0, " +
                    "changeamount money default 0, " +
                    "paytype nvarchar(50) default '', " +                     // 결제타입 (CASH, CARD, GIFTCARD, CHECK, POINT)
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "cardsalesidx int null, " +
                    "billcode nvarchar(100) default '', " +                // bill_list_paid 와 bill_list_receipt_json 이 연결되는 코드
                    "split_transaction_id nvarchar(100) default '' " +
                    ")";

    // 테이블 card_processing_data 생성쿼리
    public static final String SQL_CREATE_CARDPROCESSINGDATA =
            "CREATE TABLE IF NOT EXISTS card_processing_data ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    // 각종 코드값 관련
                    "salesCode nvarchar(100) default '', " +                         // 세일코드
                    "holdcode nvarchar(100) default '', " +                         // 세일코드

                    // 스토어, 스테이션 관련
                    "sidx int, " +                                       // 스토어코드
                    "stcode nvarchar(50), " +                             // 스테이션코드

                    "customerId nvarchar(200), " +
                    "customerName nvarchar(50), " +
                    "employeeIdx int null, " +                                  // 직원코드
                    "employeeName nvarchar(100) null, " +                     // 직원이름

                    "menuitems_list nvarchar(200) null, " +                     // 주문한 메뉴들 (리스트용)
                    "menuitems_all nvarchar(1000) null, " +                     // 주문한 메뉴들 (전체)

                    // 카드결제 정보
                    "amount money default 0, " +                                // 카드결제금액
                    "procestype nvarchar(10) default 'SALE', " +                // 카드결제 타입 SALE, VOID, RETURN...

                    // 카드결제 리턴정보
                    "resultcode nvarchar(50) default '', " +                     // 리턴코드
                    "resultmsg nvarchar(200) default '', " +                     // 결과메시지
                    "tid nvarchar(200) default '', " +                           // auth code (transaction id)
                    "refnum nvarchar(50) default '', " +                     // 레퍼런스 코드
                    "cardcom nvarchar(50) default '', " +                     // 카드사
                    "holdername nvarchar(200) default '', " +                     // 카드 소유자명

                    "delyn nvarchar(2) default 'N', " +                     // 삭제여부

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +     // 저장일시

                    ")";

    // 테이블 salon_storespecialrequest 생성쿼리
    public static final String SQL_CREATE_SALONSTORESPECIALREQUEST =
            "CREATE TABLE IF NOT EXISTS salon_storespecialrequest ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "aid nvarchar(100) NULL, " +
                    "name nvarchar(1000) NULL, " +
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 테이블 salon_storestationsettings_system_receipt 생성쿼리
    public static final String SQL_CREATE_SALONSTORE_SETTINGS_SYSTEM_RECEIPT =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_system_receipt ( " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "receipt_type nvarchar(10) DEFAULT '1'," +
                    "header_yn nvarchar(2) DEFAULT 'Y'," +
                    "stationnumber_yn nvarchar(2) DEFAULT 'Y'," +
                    "cashier_yn nvarchar(2) DEFAULT 'Y'," +
                    "ordernumber_yn nvarchar(2) DEFAULT 'Y'," +
                    "ordertype_yn nvarchar(2) DEFAULT 'Y'," +
                    "ordertypefont_yn nvarchar(2) DEFAULT 'Y'," +
                    "ordertypealignment nvarchar(2) DEFAULT 'T'," +
                    "tablenumber_yn nvarchar(2) DEFAULT 'Y'," +
                    "tablenumberfont_yn nvarchar(2) DEFAULT 'Y'," +
                    "pagernumber_yn nvarchar(2) DEFAULT 'Y'," +
                    "pagernumberfont_yn nvarchar(2) DEFAULT 'Y'," +
                    "pagernumberalignment nvarchar(2) DEFAULT 'T'," +
                    "ordermemo_yn nvarchar(2) DEFAULT 'Y'," +
                    "itemmemo_yn nvarchar(2) DEFAULT 'Y'," +
                    "displayitemline_yn nvarchar(2) DEFAULT 'Y'," +
                    "displayvoiditem_yn nvarchar(2) DEFAULT 'Y'," +
                    "guestinfo_yn nvarchar(2) DEFAULT 'Y'," +
                    "quantitysumarize_yn nvarchar(2) DEFAULT 'Y'," +
                    "topfeedrowcount_yn nvarchar(2) DEFAULT 'Y'," +

                    "stationnumber_type nvarchar(2) DEFAULT '1'," +     // 1 : NORMAL , 2 : BOLD , 3 : ITALIC
                    "stationnumber_size nvarchar(2) DEFAULT '1'," +     // 1 : SMALL , 2 : MEDIUM , 3 : LARGE , 4 : X-LARGE , 5 : 2X-LARGE

                    "ordernumber_type nvarchar(2) DEFAULT '1'," +       // 1 : NORMAL , 2 : BOLD , 3 : ITALIC
                    "ordernumber_position nvarchar(2) DEFAULT '1'," +   // 1 : TOP , 2 : BOTTOM

                    "customerinfo_yn nvarchar(2) DEFAULT 'Y'," +        // 1 : SHOW , 2 : HIDE

                    "totalamount_size nvarchar(2) DEFAULT '1'," +       // 1 : SMALL , 2 : MEDIUM , 3 : LARGE , 4 : X-LARGE , 5 : 2X-LARGE

                    "tip_type nvarchar(2) DEFAULT '1'," +               // 1 : NORMAL , 2 : BOLD , 3 : ITALIC
                    "tip_size nvarchar(2) DEFAULT '1'," +               // 1 : SMALL , 2 : MEDIUM , 3 : LARGE , 4 : X-LARGE , 5 : 2X-LARGE

                    "bottomfeedrowcount_yn nvarchar(2) DEFAULT '1'," +  // bottom row feed 1-10

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 테이블 salon_storestationsettings_deviceprinter_label1 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL1 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_label1 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "labelprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "connectusb smallint NULL," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";
    // 테이블 salon_storestationsettings_deviceprinter_label2 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL2 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_label2 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "labelprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "connectusb smallint NULL," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";
    // 테이블 salon_storestationsettings_deviceprinter_label3 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL3 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_label3 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "labelprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "connectusb smallint NULL," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";
    // 테이블 salon_storestationsettings_deviceprinter_label4 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL4 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_label4 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "labelprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "connectusb smallint NULL," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";
    // 테이블 salon_storestationsettings_deviceprinter_label5 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_LABEL5 =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_label5 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "labelprinting smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "connectusb smallint NULL," +
                    "mdate datetime DEFAULT (datetime('now', 'localtime'))" +
                    ")";

    // 테이블 salon_storesmstextsample 생성쿼리
    public static final String SQL_CREATE_SALONSMSTEXTSAMPLE =
            "CREATE TABLE IF NOT EXISTS salon_storesmstextsample ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "aid nvarchar(100) NULL, " +
                    "name nvarchar(1000) NULL, " +
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "sortnum smallint " +
                    ")";

    // 테이블 salon_sales_tip_split 생성쿼리
    public static final String SQL_CREATE_SALONSALESTIPSPLIT =
            "CREATE TABLE IF NOT EXISTS salon_sales_tip_split ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salescode nvarchar(100) default '', " +
                    "sidx int null, " +
                    "stcode nvarchar(50), " +
                    "billpaididx int null, " +
                    "cardsalesidx int null, " +
                    "cardsalestipidx int null, " +
                    "tipamount money default 0, " +
                    "paytype nvarchar(50) default '', " +                     // 결제타입 (CASH, CARD, GIFTCARD, CHECK, POINT)
                    "globalUID nvarchar(50) NULL, " +                    // globaluid
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "split_transaction_id nvarchar(100) default '' " +
                    ")";


    // 테이블 salon_storestationsettings_deviceprinter_master 생성쿼리
    public static final String SQL_CREATE_SALONSTORESTATIONSETTINGSDEVICEPRINTER_MASTER =
            "CREATE TABLE IF NOT EXISTS salon_storestationsettings_deviceprinter_master ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "printername nvarchar(100) NULL," +
                    "autoreceipt smallint NULL," +
                    "receiptfontsize smallint NULL," +
                    "receiptpapercount smallint NULL," +
                    "receiptfooter ntext NULL," +
                    "tenderuseforzerobalance smallint NULL," +
                    "autodiscountuseforonlycash smallint NULL," +
                    "autodiscountuseforonlycashrate money NULL," +
                    "customercarddigitpositionalign nvarchar(2) NULL," +
                    "customercarddigitpositionstart smallint NULL," +
                    "customercarddigitpositioncount smallint NULL," +
                    "giftcarddigitpositionalign nvarchar(2) NULL," +
                    "giftcarddigitpositionstart smallint NULL," +
                    "giftcarddigitpositioncount smallint NULL," +
                    "employeecommissionpolicy smallint NULL," +
                    "managerpwduseforvoid nvarchar(100) NULL," +
                    "cashdraweronoffonsalemode smallint NULL," +
                    "voidprint smallint DEFAULT 0," +
                    "returnprint smallint NULL," +
                    "networkprinterip1 nvarchar(4) NULL," +
                    "networkprinterip2 nvarchar(4) NULL," +
                    "networkprinterip3 nvarchar(4) NULL," +
                    "networkprinterip4 nvarchar(4) NULL," +
                    "networkprinterport nvarchar(10) NULL," +
                    "printlanguage nvarchar(4) DEFAULT 'US'," +
                    "signatureprintyn nvarchar(2) DEFAULT 'Y', " +
                    "signatureprintseperately nvarchar(2) DEFAULT 'N', " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "modifierprintyn nvarchar(2) DEFAULT 'Y', " +
                    "cashdraweropenonreceipt nvarchar(2) DEFAULT 'Y', " +
                    "cardmerchantreceiptyn nvarchar(2) DEFAULT 'Y', " +
                    "ordernumbersectionviewyn nvarchar(2) DEFAULT 'Y', " +
                    "taxexemptprintingyn nvarchar(2) DEFAULT 'Y', " +
                    "printreceiptkind smallint NULL," +         // Master 영수증 종류
                    "logoprintingonreceiptyn nvarchar(2) DEFAULT 'N', " +              // 영수증 프린팅때 로고이미지 프린팅 여부
                    "logoimgpath nvarchar(200) DEFAULT '', " +         // 영수증 프린팅때 로고이미지 경로
                    "empinfoprintingyn nvarchar(2) DEFAULT 'Y', " +         // employee 정보 출력여부
                    "menulistprintingyn nvarchar(2) DEFAULT 'Y' " +         // 주문 메뉴리스트 출력여부
                    ")";


    // 테이블 btn_logs 생성쿼리
    public static final String SQL_CREATE_bTNLOGS =
            "CREATE TABLE IF NOT EXISTS btn_logs ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "salescode nvarchar(100), " +
                    "holdcode nvarchar(200), " +   // holdcode 추가.
                    "sidx int null, " +
                    "stcode nvarchar(50) default '', " +
                    "btnpagename nvarchar(300) default '', " +
                    "btnname nvarchar(300) default '', " +
                    "btnlogkor nvarchar(1000) default '', " +
                    "btnlogeng nvarchar(1000) default '', " +
                    "employeeIdx int null, " +                           // 직원코드
                    "employeeName nvarchar(100) default '', " +                     // 직원이름
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 01052023
    // 테이블 bill_list_receipt_json 생성쿼리
    public static final String SQL_CREATE_BILLLISTRECEIPTJSON =
            "CREATE TABLE IF NOT EXISTS bill_list_receipt_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100), " +                         // 세일코드
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(20), " +
                    "jsonstr ntext, " +
                    "isCloudUpload smallint DEFAULT 0, " +                     // 클라우드 업로드 여부 (0 : 업로드이전  1 : 업로드완료)
                    "billcode nvarchar(100) default '', " +                // bill_list_paid 와 bill_list_receipt_json 이 연결되는 코드
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";

    // 01062023
    // 테이블 temp_item_printing 생성쿼리
    public static final String SQL_CREATE_TEMPITEMPRINTING =
            "CREATE TABLE IF NOT EXISTS temp_item_printing ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "printcode nvarchar(100) NULL, " +                         // 프린팅을 위한 임시코드

                    "svcIdx int NULL, " +

                    "svcName nvarchar(200) NULL, " +

                    "optionTxt nvarchar(300) NULL, " +
                    "optionprice money DEFAULT 0.0,  " +

                    "additionalTxt1 nvarchar(300) NULL, " +
                    "additionalprice1 money DEFAULT 0.0,  " +

                    "additionalTxt2 nvarchar(300) NULL, " +
                    "additionalprice2 money DEFAULT 0.0,  " +

                    "memoToKitchen ntext NULL, " +

                    "itemprice money NULL, " +
                    "itemtax money NULL, " +
                    "qty int NULL, " +

                    // 추가
                    "selectedDcExtraAllEach nvarchar(10), " +
                    "selectedDcExtraType nvarchar(20), " +
                    "dcextratype nvarchar(2) DEFAULT '', " +                    // DC / Extra 타입 : %, $
                    "dcextravalue money DEFAULT 0, " +                           // DC / Extra 적용된 금액 또는 비율
                    "selectedDcExtraPrice money DEFAULT 0, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";



    // 04252023
    public static final String SQL_CREATE_SALONNEWCARTCHECKBYSTATION2 =
            "CREATE TABLE IF NOT EXISTS salon_newcartcheck_bystation2 ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +
                    "tableidx nvarchar(30) default '', " +

                    "ctype nvarchar(30) default '', " +

                    "delyn nvarchar(2) default 'N', " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";


    // 04252023
    public static final String SQL_CREATE_SALONTABLESTATUSCHANGE =
            "CREATE TABLE IF NOT EXISTS salon_table_statuschange ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +
                    "tableidx nvarchar(30) default '', " +

                    "ctype nvarchar(30) default '', " +

                    "delyn nvarchar(2) default 'N', " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";

    // 05012023
    public static final String SQL_CREATE_SALONBILLPRINTED =
            "CREATE TABLE IF NOT EXISTS salon_billprinted ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";


    // 05152023
    // 테이블 labelprinted_json 생성쿼리
    public static final String SQL_CREATE_LABELPRINTEDJSON =
            "CREATE TABLE IF NOT EXISTS labelprinted_json ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "stcode nvarchar(50), " +
                    "jsonstr ntext, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";


    // 08082023
    // 테이블 salon_storeemployeerole 생성쿼리
    public static final String SQL_CREATE_SALONSTOREEMPLOYEEROLE =
            "CREATE TABLE IF NOT EXISTS salon_storeemployeerole ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), "      +                         // 살롱코드
                    "sidx int NULL," +
                    "rolename nvarchar(100), " +
                    "useyn nvarchar(2) default 'Y', " +
                    "delyn nvarchar(2) default 'N', " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "mdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "permission nvarchar(200) default '' " +
                    ")";


    // 10092023
    // 테이블 salon_billprinted_itemqty 생성쿼리
    public static final String SQL_CREATE_SALONBILLPRINTEDITEMQTY =
            "CREATE TABLE IF NOT EXISTS salon_billprinted_itemqty ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +

                    "qtycount int default 0," +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";


    // 10272023
    // 테이블 salon_itemdeletereason 생성쿼리
    public static final String SQL_CREATE_SALONITEMDELETEREASON =
            "CREATE TABLE IF NOT EXISTS salon_itemdeletereason ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "holdcode nvarchar(100) null, " +
                    "stcode nvarchar(100) null, " +

                    "serverIdx int NULL," +
                    "serverName nvarchar(100) null, " +

                    "reason nvarchar(1000) null, " +

                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";


    // 11012023
    // 테이블 salon_storeitemdeletereason 생성쿼리
    public static final String SQL_CREATE_SALONSTOREITEMDELETEREASON =
            "CREATE TABLE IF NOT EXISTS salon_storeitemdeletereason ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "stcode nvarchar(50)," +
                    "aid nvarchar(100) NULL, " +
                    "name nvarchar(1000) NULL, " +
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')) " +
                    ")";



    // 01172024
    // 테이블 salon_sales_kitchenprintingdata_json_torder 생성쿼리
    public static final String SQL_CREATE_SALONSALESKITCHENPRINTINGDATAJSONTORDER =
            "CREATE TABLE IF NOT EXISTS salon_sales_kitchenprintingdata_json_torder ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "salesCode nvarchar(100) NULL, " +
                    "scode nvarchar(50) NULL, " +
                    "sidx int NULL, " +

                    "jsonstr ntext NULL, " +

                    "downloaddate datetime DEFAULT (datetime('now', 'localtime')), " +

                    "tableidx nvarchar(50) DEFAULT '', " +
                    "tablename nvarchar(200) DEFAULT '', " +
                    "orderfrom nvarchar(200) DEFAULT '',  " +

                    "clouddbidx int NULL " +                         // 클라우드 db idx

                    ")";

    // 02192024
    // 테이블 salon_storememberlevel 생성쿼리
    public static final String SQL_CREATE_SALONSTOREMEMBERLEVEL =
            "CREATE TABLE IF NOT EXISTS salon_storememberlevel ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "scode nvarchar(50), " +
                    "sidx int NULL," +
                    "aid nvarchar(100) NULL, " +
                    "levelname nvarchar(100) NULL, " +
                    "pointratio money NULL,"+
                    "useyn nvarchar(2) DEFAULT 'Y'," +
                    "delyn nvarchar(2) DEFAULT 'N'," +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "edate datetime DEFAULT (datetime('now', 'localtime')) " +

                    ")";


    // 05302024
    // 테이블 salon_sales_togodeliveryfee 생성쿼리
    public static final String SQL_CREATE_SALONSALESTOGODELIVERYFEE =
            "CREATE TABLE IF NOT EXISTS salon_sales_togodeliveryfee ( " +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sidx int NULL," +
                    "salesCode nvarchar(100), " +
                    "deliverypickupfee money DEFAULT 0.0, " +
                    "wdate datetime DEFAULT (datetime('now', 'localtime')), " +
                    "cashoutNum int null DEFAULT 0, " +                             // cash out 여부
                    "endofdayNum int DEFAULT 0, " +                             // End of Day 여부
                    "employeeidx int null " +
                    ")";




/******************************************************************************************/
}
