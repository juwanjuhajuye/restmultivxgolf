package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.util.Vector;

public class MssqlAlterTables {
    public static void alterTablesForMSSQL() {
        // 테이블 컬럼 추가
        addTableColumn();

        // 테이블 컬럼 수정
        alterTableColumnType();

        GlobalMemberValues.logWrite("altertablejjjlog", "여기요3" + "\n");
    }

    public static void addTableColumn() {
        String altTableName = "";

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.21.2020, abc 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "abc", "nvarchar(6)", "DEFAULT '180'");

        // salon_storestationsettings_paymentgateway 테이블 컬럼 추가
        // 9.28.2020, timeout 컬럼 추가
        altTableName = "salon_storestationsettings_paymentgateway";
        alterDatabaseTableColumn(altTableName, "timeout", "nvarchar(2)", "DEFAULT '1'");

        // salon_sales_detail 테이블 컬럼 추가
        // 10.5.2020, dcextraforreturn 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "dcextraforreturn", "money", "DEFAULT 0");

        // temp_salecart 테이블 컬럼 추가
        // 10.14.2020, cardtryyn 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "cardtryyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_sales 테이블 컬럼 추가
        // 10.19.2020, salepg_ip 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "salepg_ip", "nvarchar(30)", "DEFAULT ''");

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 11.2.2020, printedyn 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        alterDatabaseTableColumn(altTableName, "printedyn", "nvarchar(2)", "DEFAULT 'N'");


        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 11.21.2020, mssqlsyncyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "mssqlsyncyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.21.2020, mssqldbip 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "mssqldbip", "nvarchar(500)", "DEFAULT ''");

        // salon_sales_detail 테이블 컬럼 추가
        // 12.7.2020, frommssql 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "frommssql", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.10.2020, vrkitchenprinting 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "vrkitchenprinting", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.18.2020, autoweborderprinting 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "autoweborderprinting", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 12.26.2020, initcapacity 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "initcapacity", "smallint", "DEFAULT 500");

        // salon_sales_return 테이블 컬럼 추가
        // 12.26.2020, wdate 컬럼 추가
        altTableName = "salon_sales_return";
        alterDatabaseTableColumn(altTableName, "wdate", "datetime", "NULL");

        // salon_sales_cashout_json 테이블 컬럼 추가
        // 4.6.2021, isCloudUpload 컬럼 추가
        altTableName = "salon_sales_cashout_json";
        alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0");

        // salon_sales_endofday_json 테이블 컬럼 추가
        // 4.6.2021, isCloudUpload 컬럼 추가
        altTableName = "salon_sales_endofday_json";
        alterDatabaseTableColumn(altTableName, "isCloudUpload", "smallint", "DEFAULT 0");

        // salon_sales 테이블 컬럼 추가
        // 4.20.2021, isCloudUpload2 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "isCloudUpload2", "smallint", "DEFAULT 0");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.21.2021, passwordyninmod 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "passwordyninmod", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.23.2021, passwordyninmod 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "tableboardtype", "nvarchar(2)", "DEFAULT '2'");

        // salon_newcartcheck_bystation 테이블 컬럼 추가
        // 5.26.2021, tableidx 컬럼 추가
        altTableName = "salon_newcartcheck_bystation";
        alterDatabaseTableColumn(altTableName, "tableidx", "nvarchar(30)", "DEFAULT ''");

        // salon_newcartcheck_bystation 테이블 컬럼 추가
        // 5.26.2021, delyn 컬럼 추가
        altTableName = "salon_newcartcheck_bystation";
        alterDatabaseTableColumn(altTableName, "delyn", "nvarchar(2)", "DEFAULT 'N'");

        // temp_salecart_del 테이블 컬럼 추가
        // 5.26.2021, tableidx 컬럼 추가
        altTableName = "temp_salecart_del";
        alterDatabaseTableColumn(altTableName, "tableidx", "nvarchar(50)", "DEFAULT ''");

        // temp_salecart 테이블 컬럼 추가
        // 5.28.2021, dcextratype 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "dcextratype", "nvarchar(2)", "DEFAULT ''");

        // temp_salecart 테이블 컬럼 추가
        // 5.28.2021, dcextravalue 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "dcextravalue", "money", "DEFAULT 0");

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 6.10.2021, boxtype 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        alterDatabaseTableColumn(altTableName, "boxtype", "nvarchar(50)", "DEFAULT '0'");

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 6.10.2021, boxkinds 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        alterDatabaseTableColumn(altTableName, "boxkinds", "nvarchar(50)", "DEFAULT 'table'");

        // bill_list 테이블 컬럼 추가
        // 6.14.2021, paidyn 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "paidyn", "nvarchar(2)", "DEFAULT 'N'");

        // bill_list_paid 테이블 컬럼 추가
        // 6.21.2021, paytype 컬럼 추가
        altTableName = "bill_list_paid";
        alterDatabaseTableColumn(altTableName, "paytype", "nvarchar(50)", "DEFAULT ''");

        // bill_list_paid 테이블 컬럼 추가
        // 6.21.2021, changeamount 컬럼 추가
        altTableName = "bill_list_paid";
        alterDatabaseTableColumn(altTableName, "changeamount", "money", "DEFAULT 0");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.22.2021, pagernumbershowonpickuporder 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "pagernumbershowonpickuporder", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.22.2021, ordernumbershowonpickuporder 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "ordernumbershowonpickuporder", "nvarchar(2)", "DEFAULT 'N'");

        // card_processing_data 테이블 컬럼 추가
        // 6.26.2021, delyn 컬럼 추가
        altTableName = "card_processing_data";
        alterDatabaseTableColumn(altTableName, "delyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.26.2021, cardstatususe 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "cardstatususe", "nvarchar(2)", "DEFAULT 'N'");

        // bill_list_paid 테이블 컬럼 추가
        // 7.2.2021, ordernum 컬럼 추가
        altTableName = "bill_list_paid";
        alterDatabaseTableColumn(altTableName, "ordernum", "nvarchar(100)", "DEFAULT ''");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.2.2021, qtyinsyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "qtyinsyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.2.2021, customernumberviewyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "customernumberviewyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_sales 테이블 컬럼 추가
        // 7.7.2021, cancelreason 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "cancelreason", "ntext", "DEFAULT ''");

        // salon_storeemployee 테이블 컬럼 추가
        // 7.14.2021, cashieryn 컬럼 추가
        altTableName = "salon_storeemployee";
        alterDatabaseTableColumn(altTableName, "emptype", "nvarchar(2)", "DEFAULT '0'");

        // salon_sales 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL");

        // salon_sales 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''");

        // salon_sales_detail 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL");

        // salon_sales_detail 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''");

        // salon_sales_tip 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_tip";
        alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL");

        // salon_sales_tip 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_tip";
        alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''");

        // salon_sales_card 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_sales_card";
        alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL");

        // salon_sales_card 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_sales_card";
        alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''");

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 7.19.2021, serverIdx 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        alterDatabaseTableColumn(altTableName, "serverIdx", "int", "NULL");

        // salon_storegiftcard_number_history 테이블 컬럼 추가
        // 7.19.2021, serverName 컬럼 추가
        altTableName = "salon_storegiftcard_number_history";
        alterDatabaseTableColumn(altTableName, "serverName", "nvarchar(100)", "DEFAULT ''");


        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, cashinoutyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "cashinoutyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, paymentyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "paymentyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.17.2021, cashinoutyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "cashinoutyn", "nvarchar(200)", "DEFAULT 'cash,creditcard,giftcard,point,other' ");

        // temp_salecart 테이블 컬럼 추가
        // 8.17.2021, togodelitype  컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "togodelitype ", "nvarchar(2)", "DEFAULT 'H'");

        // salon_sales_detail 테이블 컬럼 추가
        // 8.17.2021, togodelitype  컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "togodelitype ", "nvarchar(2)", "DEFAULT 'H'");


        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuityuseyn  컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "gratuityuseyn ", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuitytype  컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "gratuitytype ", "nvarchar(4)", "DEFAULT '%'");

        // salon_storegeneral 테이블 컬럼 추가
        // 8.19.2021, gratuityvalue  컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "gratuityvalue ", "money", "DEFAULT 0.0");

        // salon_storegeneral 테이블 컬럼 추가
        // 8.20.2021, gratuitycustomercount  컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "gratuitycustomercount ", "money", "DEFAULT 1");

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, minval  컬럼 추가
        altTableName = "salon_storeservice_option";
        alterDatabaseTableColumn(altTableName, "minval ", "smallint", "DEFAULT 0");

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, maxval  컬럼 추가
        altTableName = "salon_storeservice_option";
        alterDatabaseTableColumn(altTableName, "maxval ", "smallint", "DEFAULT 1");

        // salon_storeservice_option 테이블 컬럼 추가
        // 8.20.2021, maxsumval  컬럼 추가
        altTableName = "salon_storeservice_option";
        alterDatabaseTableColumn(altTableName, "maxsumval ", "smallint", "DEFAULT 1");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, mssqldbip 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "databasename", "nvarchar(50)", "DEFAULT 'JJJQSRDBMULTI'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, databasepass 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "databasepass", "nvarchar(50)", "DEFAULT 'DhksGkDP@02)'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, mobilehost 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "mobilehost", "nvarchar(50)", "DEFAULT 'yukdaejangm'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.21.2021, cloudhost 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "cloudhost", "nvarchar(50)", "DEFAULT 'yukdaejangcloud'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.26.2021, serverpassworduse 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "serverpassworduse", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.28.2021, servercodeuse 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "servercodeuse", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, pickuptypepopupuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "pickuptypepopupuseyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, printingcategoryyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "printingcategoryyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 11.8.2021, cashoutreportitems 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "cashoutreportitems", "nvarchar(200)", "DEFAULT '<1>,<2>,<3>,<4>,<5>'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_here_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "customer_info_here_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_togo_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "customer_info_togo_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 11.8.2021, customer_info_delivery_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "customer_info_delivery_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 11.12.2021, quicksaleyn 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        alterDatabaseTableColumn(altTableName, "quicksaleyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.17.2021, divideryn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "divideryn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 11.17.2021, divideruseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "divideruseyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 11.30.2021, labelprinteruse 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "labelprinteruse", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 11.30.2021, nameforlabel 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "nameforlabel", "nvarchar(300)", "DEFAULT ''");

        // salon_storestationinfo 테이블 컬럼 추가
        // 12.16.2021, sttype 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "sttype", "nvarchar(2)", "DEFAULT 'R'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 1.13.2022, imageusezone 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "imageusezone", "nvarchar(50)", "DEFAULT 'P,K,O'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 1.27.2022, menuusezone 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "menuusezone", "nvarchar(50)", "DEFAULT 'P,K,O'");

        // salon_storeservice_main 테이블 컬럼 추가
        // 1.27.2022, categoryusezone 컬럼 추가
        altTableName = "salon_storeservice_main";
        alterDatabaseTableColumn(altTableName, "categoryusezone", "nvarchar(50)", "DEFAULT 'P,K,O'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 2.8.2022, mdforceyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "mdforceyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 2.10.2022, preSalesCode 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        alterDatabaseTableColumn(altTableName, "preSalesCode", "nvarchar(100)", "DEFAULT ''");

        // salon_storegeneral 테이블 컬럼 추가
        // 3.4.2022, managerpwdnz 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "managerpwdnz", "nvarchar(100)", "DEFAULT '0904'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.5.2022, showynifzero_m 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "showynifzero_m", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.5.2022, showynifzero_k 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "showynifzero_k", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.10.2022, nowtableidx 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        alterDatabaseTableColumn(altTableName, "nowtableidx", "nvarchar(50)", "DEFAULT ''");

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.10.2022, nowtablename 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        alterDatabaseTableColumn(altTableName, "nowtablename", "nvarchar(200)", "DEFAULT ''");

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.12.2022, empinfoprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        alterDatabaseTableColumn(altTableName, "empinfoprintingyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storestationsettings_deviceprinter 테이블 컬럼 추가
        // 3.12.2022, menulistprintingyn 컬럼 추가
        altTableName = "salon_storestationsettings_deviceprinter";
        alterDatabaseTableColumn(altTableName, "menulistprintingyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_sales_kitchenprintingdata_json 테이블 컬럼 추가
        // 3.18.2022, reprintyn 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json";
        alterDatabaseTableColumn(altTableName, "reprintyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 3.29.2022, labelprintyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "labelprintyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storeservice_sub 테이블 컬럼 추가
        // 4.5.2022, labelprintnum 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "labelprintnum", "nvarchar(50)", "DEFAULT ''");

        // temp_salecart 테이블 컬럼 추가
        // 4.14.2022, labelprintedyn 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "labelprintedyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storeemployee 테이블 컬럼 추가
        // 4.15.2022, serveraccesscode 컬럼 추가
        altTableName = "salon_storeemployee";
        alterDatabaseTableColumn(altTableName, "serveraccesscode", "nvarchar(100)", "DEFAULT ''");

        // salon_storeemployee 테이블 컬럼 추가
        // 4.15.2022, serveraccesspwd 컬럼 추가
        altTableName = "salon_storeemployee";
        alterDatabaseTableColumn(altTableName, "serveraccesspwd", "nvarchar(100)", "DEFAULT ''");

        // salon_storesmstextsample 테이블 컬럼 추가
        // 4.19.2022, sortnum 컬럼 추가
        altTableName = "salon_storesmstextsample";
        alterDatabaseTableColumn(altTableName, "sortnum", "smallint", "NULL");

        // bill_list_paid 테이블 컬럼 추가
        // 4.25.2022, cardsalesidx 컬럼 추가
        altTableName = "bill_list_paid";
        alterDatabaseTableColumn(altTableName, "cardsalesidx", "int", "NULL");

        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype13 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        alterDatabaseTableColumn(altTableName, "cashtype13", "int", "DEFAULT 0");
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype14 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        alterDatabaseTableColumn(altTableName, "cashtype14", "int", "DEFAULT 0");
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype15 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        alterDatabaseTableColumn(altTableName, "cashtype15", "int", "DEFAULT 0");
        // salon_sales_cashintout_history_cashlist 테이블 컬럼 추가
        // 4.29.2022, cashtype16 컬럼 추가
        altTableName = "salon_sales_cashintout_history_cashlist";
        alterDatabaseTableColumn(altTableName, "cashtype16", "int", "DEFAULT 0");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, openmsgwhendeletemenu_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "openmsgwhendeletemenu_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, otherpayprinting_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "otherpayprinting_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.9.2022, tipprintingwhentogo_yn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "tipprintingwhentogo_yn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 5.10.2022, suggestiontiptype 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "suggestiontiptype", "nvarchar(10)", "DEFAULT 'AT'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 5.20.2022, billprintpopupyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "billprintpopupyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 5.22.2022, commongratuitytype 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "commongratuitytype", "nvarchar(10)", "DEFAULT 'AT'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 6.20.2022, custombillsplituseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "custombillsplituseyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storegeneral 테이블 컬럼 추가
        // 6.20.2022, multistationsyncuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "multistationsyncuseyn", "nvarchar(2)", "DEFAULT 'N'");


        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaytype 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaytype", "nvarchar(4)", "DEFAULT 'A'");

        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaymoneytype 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaymoneytype", "nvarchar(4)", "DEFAULT '%'");

        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaymoney 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaymoney", "money", "DEFAULT 0");

        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpaytaxtype 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaytaxtype", "nvarchar(10)", "DEFAULT 'AT'");

        // salon_storegeneral 테이블 컬럼 추가
        // 7.15.2022, addpayname 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpayname", "nvarchar(200)", "DEFAULT ''");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.30.2022, pagernumofdigits 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "pagernumofdigits", "smallint", "DEFAULT 0");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 7.30.2022, customeronlinecheckyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "customeronlinecheckyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storestationinfo 테이블 컬럼 추가
        // 8.16.2022, eodyn 컬럼 추가
        altTableName = "salon_storestationinfo";
        alterDatabaseTableColumn(altTableName, "eodyn", "nvarchar(2)", "DEFAULT 'Y'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 9.30.2022, startpagernum 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "startpagernum", "smallint", "DEFAULT 0");

        // salon_storegeneral 테이블 컬럼 추가
        // 10.4.2022, scaleautoweighyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "scaleautoweighyn", "nvarchar(2)", "DEFAULT 'N'");

        // btn_logs 테이블 컬럼 추가
        // 10.27.2022, salescode 컬럼 추가
        altTableName = "btn_logs";
        alterDatabaseTableColumn(altTableName, "salescode", "nvarchar(100)", "DEFAULT ''");

        // btn_logs 테이블 컬럼 추가
        // 10.27.2022, scode 컬럼 추가
        altTableName = "btn_logs";
        alterDatabaseTableColumn(altTableName, "scode", "nvarchar(500)", "DEFAULT ''");

        // temp_salecart 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''");

        // temp_salecart_ordered 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "temp_salecart_ordered";
        alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''");

        // salon_sales 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''");

        // salon_sales_detail 테이블 컬럼 추가
        // 11.18.2022, togotype 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "togotype", "nvarchar(2)", "DEFAULT ''");

        // salon_sales 테이블 컬럼 추가
        // 12.22.2022, cancelreason 컬럼 추가
        altTableName = "salon_sales";
        alterDatabaseTableColumn(altTableName, "cancelreason", "ntext", "DEFAULT ''");

        // salon_store_restaurant_table 테이블 컬럼 추가
        // 1.11.2023, size 컬럼 추가
        altTableName = "salon_store_restaurant_table";
        alterDatabaseTableColumn(altTableName, "size", "nvarchar(2)", "DEFAULT 'M'");

        // bill_list_paid 테이블 컬럼 추가
        // 1.5.2023, billcode 컬럼 추가
        altTableName = "bill_list_paid";
        alterDatabaseTableColumn(altTableName, "billcode", "nvarchar(100)", "DEFAULT ''");

        // btn_logs 테이블 컬럼 추가
        // 1.12.2023, holdcode 컬럼 추가
        altTableName = "btn_logs";
        alterDatabaseTableColumn(altTableName, "holdcode", "nvarchar(200)", "DEFAULT ''");

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023, selectedDcExtraAllEach 컬럼 추가
        altTableName = "temp_item_printing";
        alterDatabaseTableColumn(altTableName, "selectedDcExtraAllEach", "nvarchar(10)", "DEFAULT ''");

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023, selectedDcExtraType 컬럼 추가
        altTableName = "temp_item_printing";
        alterDatabaseTableColumn(altTableName, "selectedDcExtraType", "nvarchar(20)", "DEFAULT ''");

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023, dcextratype 컬럼 추가
        altTableName = "temp_item_printing";
        alterDatabaseTableColumn(altTableName, "dcextratype", "nvarchar(2)", "DEFAULT ''");

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023, dcextravalue 컬럼 추가
        altTableName = "temp_item_printing";
        alterDatabaseTableColumn(altTableName, "dcextravalue", "money", "DEFAULT 0");

        // temp_item_printing 테이블 컬럼 추가
        // 1.12.2023, selectedDcExtraPrice 컬럼 추가
        altTableName = "temp_item_printing";
        alterDatabaseTableColumn(altTableName, "selectedDcExtraPrice", "money", "DEFAULT 0");


        // salon_sales_detail 테이블 컬럼 추가
        // 2.7.2023, kitchenPrintedYN 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "kitchenPrintedYN", "nvarchar(2)", "DEFAULT 'N'");

        // salon_sales_detail 테이블 컬럼 추가
        // 2.7.2023, labelPrintedYN 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "labelPrintedYN", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 2.13.2023, saledatauploadpauseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "saledatauploadpauseyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storediscountbutton 테이블 컬럼 추가
        // 3.21.2023, sortnum 컬럼 추가
        altTableName = "salon_storediscountbutton";
        alterDatabaseTableColumn(altTableName, "sortnum", "smallint", "DEFAULT 1");

        // salon_storeservice_main 테이블 컬럼 추가
        // 4.6.2023, labelprintnum 컬럼 추가
        altTableName = "salon_storeservice_main";
        alterDatabaseTableColumn(altTableName, "labelprintnum", "nvarchar(40)", "DEFAULT ''");

        // temp_salecart 테이블 컬럼 추가
        // 4.19.2023, pastholdcode 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "pastholdcode", "nvarchar(1000)", "DEFAULT ''");

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, gratuityamt 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "gratuityamt", "money", "DEFAULT 0");

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, subtotalamt 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "subtotalamt", "money", "DEFAULT 0");

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, taxamt 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "taxamt", "money", "DEFAULT 0");

        // bill_list 테이블 컬럼 추가
        // 4.21.2023, totalamountAmt 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "totalamountAmt", "money", "DEFAULT 0");

        // temp_salecart 테이블 컬럼 추가
        // 4.22.2023, billprintedyn 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "billprintedyn", "nvarchar(2)", "DEFAULT 'N'");


        // temp_salecart_ordered 테이블 컬럼 추가
        // 4.24.2023, pastholdcode 컬럼 추가
        altTableName = "temp_salecart_ordered";
        alterDatabaseTableColumn(altTableName, "pastholdcode", "nvarchar(1000)", "DEFAULT ''");

        // temp_salecart_ordered 테이블 컬럼 추가
        // 4.24.2023, billprintedyn 컬럼 추가
        altTableName = "temp_salecart_ordered";
        alterDatabaseTableColumn(altTableName, "billprintedyn", "nvarchar(2)", "DEFAULT 'N'");


        // salon_newcartcheck_bystation2 테이블 컬럼 추가
        // 4.25.2023, ctype 컬럼 추가
        altTableName = "salon_newcartcheck_bystation2";
        alterDatabaseTableColumn(altTableName, "ctype", "nvarchar(30)", "DEFAULT ''");

        // salon_table_statuschange 테이블 컬럼 추가
        // 4.25.2023, ctype 컬럼 추가
        altTableName = "salon_table_statuschange";
        alterDatabaseTableColumn(altTableName, "ctype", "nvarchar(30)", "DEFAULT ''");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 4.27.2023, timeviewontableyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "timeviewontableyn", "nvarchar(2)", "DEFAULT 'N'");


        // salon_storegeneral 테이블 컬럼 추가
        // 5.3.2023, wmuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "wmuseyn", "nvarchar(2)", "DEFAULT 'N'");
        // salon_storegeneral 테이블 컬럼 추가
        // 5.3.2023, wmapiip 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "wmapiip", "nvarchar(200)", "DEFAULT ''");
        // salon_storeservice_sub 테이블 컬럼 추가
        // 5.3.2023, wmuseyn 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "wmuseyn", "nvarchar(2)", "DEFAULT 'N'");
        // salon_sales_detail 테이블 컬럼 추가
        // 5.3.2023, wmodyn 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "wmodyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_sales_detail 테이블 컬럼 추가
        // 5.5.2023, wmodresult 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "wmodresult", "nvarchar(2)", "DEFAULT 'N'");
        // salon_sales_detail 테이블 컬럼 추가
        // 5.8.2023, wmodresultmsg 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "wmodresultmsg", "nvarchar(1000)", "DEFAULT ''");


        // salon_sales_detail 테이블 컬럼 추가
        // 5.10.2023, wmurl 컬럼 추가
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumn(altTableName, "wmurl", "nvarchar(1000)", "DEFAULT ''");

        // salon_storegeneral 테이블 컬럼 추가
        // 5.11.2023, wmoptionstsr 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "wmoptionstsr", "nvarchar(1000)", "DEFAULT ''");

        // salon_storegeneral 테이블 컬럼 추가
        // 5.11.2023, addpaycustomerselectyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaycustomerselectyn", "nvarchar(2)", "DEFAULT 'N'");


        // bill_list 테이블 컬럼 추가
        // 5.15.2023, dcextraAmt 컬럼 추가
        altTableName = "bill_list";
        alterDatabaseTableColumn(altTableName, "dcextraAmt", "money", "DEFAULT 0");


        // temp_salecart 테이블 컬럼 추가
        // 5.18.2023, billidx_byitemsplit 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "billidx_byitemsplit", "nvarchar(20)", "DEFAULT ''");
        // temp_salecart_ordered 테이블 컬럼 추가
        // 5.18.2023, billidx_byitemsplit 컬럼 추가
        altTableName = "temp_salecart_ordered";
        alterDatabaseTableColumn(altTableName, "billidx_byitemsplit", "nvarchar(20)", "DEFAULT ''");


        // salon_storegeneral 테이블 컬럼 추가
        // 6.21.2023, addpayitempriceshowyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpayitempriceshowyn", "nvarchar(2)", "DEFAULT 'Y'");
        // salon_storegeneral 테이블 컬럼 추가
        // 6.21.2023, cashinoutstartendcashyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "cashinoutstartendcashyn", "nvarchar(2)", "DEFAULT 'Y'");



        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, gratuitydelyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "gratuitydelyn", "nvarchar(2)", "DEFAULT 'Y'");
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, crmuseyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "crmuseyn", "nvarchar(2)", "DEFAULT 'Y'");
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, cashdcshowonreceiptyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "cashdcshowonreceiptyn", "nvarchar(2)", "DEFAULT 'Y'");
        // salon_storegeneral 테이블 컬럼 추가
        // 8.1.2023, cashdctaxshowyn 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "cashdctaxshowyn", "nvarchar(2)", "DEFAULT 'Y'");


        // salon_storeservice_sub 테이블 컬럼 추가
        // 8.2.2023, servicenamealt 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "servicenamealt", "nvarchar(500)", "DEFAULT ''");


        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 8.30.2023, onlinetype 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        alterDatabaseTableColumn(altTableName, "onlinetype", "nvarchar(2)", "DEFAULT 'W'");



        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.14.2023, wmtype 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "wmtype", "nvarchar(2)", "DEFAULT ''");
        // salon_storeservice_sub 테이블 컬럼 추가
        // 9.14.2023, wmbean 컬럼 추가
        altTableName = "salon_storeservice_sub";
        alterDatabaseTableColumn(altTableName, "wmbean", "nvarchar(200)", "DEFAULT ''");



        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 9.15.2023, receiptprintedyn 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        alterDatabaseTableColumn(altTableName, "receiptprintedyn", "nvarchar(2)", "DEFAULT 'N'");
        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 9.15.2023, kitchenprintedyn 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        alterDatabaseTableColumn(altTableName, "kitchenprintedyn", "nvarchar(2)", "DEFAULT 'N'");


        // salon_storegeneral 테이블 컬럼 추가
        // 9.19.2023, addpaynameoncustompopup 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "addpaynameoncustompopup", "nvarchar(2)", "DEFAULT 'Y'");


        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 10.11.2023, orderfrom 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        alterDatabaseTableColumn(altTableName, "orderfrom", "nvarchar(300)", "DEFAULT ''");
        // salon_sales_web_push_realtime 테이블 컬럼 추가
        // 10.11.2023, salescodethirdparty 컬럼 추가
        altTableName = "salon_sales_web_push_realtime";
        alterDatabaseTableColumn(altTableName, "salescodethirdparty", "nvarchar(300)", "DEFAULT ''");


        // salon_storestationsettings_system 테이블 컬럼 추가
        // 10.27.2023, itemdeletereasonyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "itemdeletereasonyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_storestationsettings_system 테이블 컬럼 추가
        // 1.17.2024, tableorderuseyn 컬럼 추가
        altTableName = "salon_storestationsettings_system";
        alterDatabaseTableColumn(altTableName, "tableorderuseyn", "nvarchar(2)", "DEFAULT 'N'");

        // salon_sales_kitchenprintingdata_json_torder 테이블 컬럼 추가
        // 1.17.2024, clouddbidx 컬럼 추가
        altTableName = "salon_sales_kitchenprintingdata_json_torder";
        alterDatabaseTableColumn(altTableName, "clouddbidx", "int", "NULL");


        // temp_salecart 테이블 컬럼 추가
        // 2.2.2024, tordercode 컬럼 추가
        altTableName = "temp_salecart";
        alterDatabaseTableColumn(altTableName, "tordercode", "nvarchar(100)", "DEFAULT ''");

        // temp_salecart_del 테이블 컬럼 추가
        // 2.3.2024, tordercode 컬럼 추가
        altTableName = "temp_salecart_del";
        alterDatabaseTableColumn(altTableName, "tordercode", "nvarchar(100)", "DEFAULT ''");


        // salon_storegeneral 테이블 컬럼 추가
        // 2.27.2024, pointmintouse 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "pointmintouse", "money", "DEFAULT 0");

        // salon_storegeneral 테이블 컬럼 추가
        // 2.27.2024, pointmaxpayble 컬럼 추가
        altTableName = "salon_storegeneral";
        alterDatabaseTableColumn(altTableName, "pointmaxpayble", "money", "DEFAULT 0");

    }

    public static void alterTableColumnType() {
        String altTableName = "";

        // salon_sales_detail 테이블 컬럼 수정
        // 10.8.2021, isQuickSale 컬럼 수정
        altTableName = "salon_sales_detail";
        alterDatabaseTableColumnType(altTableName, "isQuickSale", "nvarchar(2)", "NULL");


        // salon_storeinfo 테이블 컬럼 추가
        // 9.1.2023, name2 컬럼 추가
        altTableName = "salon_storeinfo";
        alterDatabaseTableColumn(altTableName, "name2", "nvarchar(200)", "DEFAULT ''");

    }


    public static void alterDatabaseTableColumn(String tblName, String tblColumnName, String tblColumnDataType, String tblColumnEtc) {
        Vector<String> vec = new Vector<String>();

        String strQuery = "";
        strQuery = "IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = '" + tblName + "' AND column_name = '" + tblColumnName + "') " +
                "alter table " + tblName + " add " + tblColumnName + " " + tblColumnDataType + " " + tblColumnEtc;

        GlobalMemberValues.logWrite("altertablejjjlog", "sql " + strQuery + "\n");

        vec.add(strQuery);
        MssqlDatabase.executeTransaction(vec);
    }


    public static void alterDatabaseTableColumnType(String tblName, String tblColumnName, String tblColumnDataType, String tblColumnEtc) {
        Vector<String> vec = new Vector<String>();

        String strQuery = "";
        strQuery = "IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = '" + tblName + "' AND column_name = '" + tblColumnName + "') " +
                "alter table " + tblName + " alter column " + tblColumnName + " " + tblColumnDataType + " " + tblColumnEtc;

        vec.add(strQuery);
        MssqlDatabase.executeTransaction(vec);
    }
}
