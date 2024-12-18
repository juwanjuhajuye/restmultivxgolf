package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class SendDataToTOrderAfterDownloadService extends Service implements Runnable {

    static DatabaseInit dbinitSendDataToTorderAfterDownloadService;
    public SendDataToTOrderAfterDownloadService() {
        if (MainActivity.mDbInit == null){
            dbinitSendDataToTorderAfterDownloadService = new DatabaseInit(MainActivity.mContext);
        } else {
            dbinitSendDataToTorderAfterDownloadService = MainActivity.mDbInit;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        GlobalMemberValues.logWrite("API_torder_programstart", "SendDataToTOrderAfterDownloadService Running!");
        JSONObject tableInfoJSON = makeTableInfoJSON();
        JSONObject menuCategoryInfoJSON = makeMenuCategoryJSON();
        JSONObject menuInfoJSON = makeMenuJSON();

        JSONObject savedTableInfoJSON = null;
        JSONObject savedMenuCategoryInfoJSON = null;
        JSONObject savedMenuInfoJSON = null;

        Boolean prevDataExists = false;

        String strCheckQuery = "select tableinfojson, menucategoryinfojson, menuinfojson from torder_json_data where idx=1";

        Cursor dataCursor = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(strCheckQuery);
        while (dataCursor.moveToNext()) {
            try {
                savedTableInfoJSON = new JSONObject(GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                savedMenuCategoryInfoJSON = new JSONObject(GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                savedMenuInfoJSON = new JSONObject(GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            prevDataExists = true;
        }

        GlobalMemberValues.logWrite("API_torder_programstart", "prevDataExists is: " + prevDataExists);

        //After making the JSON for the information to send, send the POST request to the TOrder API
        //if the JSON do not match, meaning the data has changed.
        try {
            if (savedTableInfoJSON == null || !savedTableInfoJSON.getJSONObject("data").toString().equals(tableInfoJSON.getJSONObject("data").toString())) {
                int cloudSentResultTableInfo = 0;
                GlobalMemberValues.logWrite("API_torder_programstart", "tableinfo data sending");

                API_torder_programstart apiTorderProgramstartTableInfo = new API_torder_programstart(tableInfoJSON.toString());
                apiTorderProgramstartTableInfo.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiTorderProgramstartTableInfo.mFlag) {
                        GlobalMemberValues.logWrite("API_torder_programstart", "tableinfo data sent");
                        cloudSentResultTableInfo = apiTorderProgramstartTableInfo.cloudSentResult;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("TORDERAPI", "Thread Error : " + e.getMessage() + "\n");
                }
            }
        } catch (JSONException e) {
            GlobalMemberValues.logWrite("API_torder_programstart", "tableinfo data exception");
            throw new RuntimeException(e);
        }


        try {
            if (savedMenuCategoryInfoJSON == null || !savedMenuCategoryInfoJSON.getJSONObject("data").toString().equals(menuCategoryInfoJSON.getJSONObject("data").toString())) {
                int cloudSentResultCategoryInfo = 0;
                GlobalMemberValues.logWrite("API_torder_programstart", "categoryinfo data sending");

                API_torder_programstart apiTorderProgramstartCategoryInfo = new API_torder_programstart(menuCategoryInfoJSON.toString());
                apiTorderProgramstartCategoryInfo.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiTorderProgramstartCategoryInfo.mFlag) {
                        GlobalMemberValues.logWrite("API_torder_programstart", "categoryinfo data sent");
                        cloudSentResultCategoryInfo = apiTorderProgramstartCategoryInfo.cloudSentResult;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("TORDERAPI", "Thread Error : " + e.getMessage() + "\n");
                }
            }
        } catch (JSONException e) {
            GlobalMemberValues.logWrite("API_torder_programstart", "categoryinfo data exception");
            throw new RuntimeException(e);
        }


        try {
            if (savedMenuInfoJSON == null || !savedMenuInfoJSON.getJSONObject("data").toString().equals(menuInfoJSON.getJSONObject("data").toString())) {
                int cloudSentResultMenuInfo = 0;
                GlobalMemberValues.logWrite("API_torder_programstart", "menuinfo data sending");

                API_torder_programstart apiTorderProgramstartMenuInfo = new API_torder_programstart(menuInfoJSON.toString());
                apiTorderProgramstartMenuInfo.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_UPLOAD_THREAD_TIME);
                    if (apiTorderProgramstartMenuInfo.mFlag) {
                        GlobalMemberValues.logWrite("API_torder_programstart", "menuinfo data sent");
                        cloudSentResultMenuInfo = apiTorderProgramstartMenuInfo.cloudSentResult;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("TORDERAPI", "Thread Error : " + e.getMessage() + "\n");
                }
            }
        } catch (JSONException e) {
            GlobalMemberValues.logWrite("API_torder_programstart", "menuinfo data sending exception");
            throw new RuntimeException(e);
        }


        String strInsQuery = "";

        //Save the new json data
        strInsQuery = "insert into torder_json_data " +
                "(tableinfojson, menucategoryinfojson, menuinfojson) values " +
                "('" + GlobalMemberValues.getDBTextAfterChecked(tableInfoJSON.toString(), 0) + "', '" + GlobalMemberValues.getDBTextAfterChecked(menuCategoryInfoJSON.toString(), 0) + "', '" + GlobalMemberValues.getDBTextAfterChecked(menuInfoJSON.toString(), 0) + "')";

        dbinitSendDataToTorderAfterDownloadService.dbExecuteWriteReturnValue(strInsQuery);

    }

    //06032024 API call to make when table info has changed to TOrder API.
    //Currently this is sent when the application downloads/updates data at the start of the application.
    public JSONObject makeTableInfoJSON() {
        String code = "P0201";
        String storeId = GlobalMemberValues.makeStoreCodeForTOrder();
        String message = "Store table info updated";

        // create a clock
        ZoneId utczone = null;
        ZonedDateTime timestamp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            utczone = ZoneId.of("UTC");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timestamp = ZonedDateTime.now(utczone);
        }

        //data object where table information will go into
        JSONObject data = new JSONObject();

        //tables json array where table objects will go into
        JSONArray tables = new JSONArray();

        //Get table zones for the store
        String strQuery = "select idx from salon_store_restaurant_tablezone " +
                " where deleteyn = 'N' and useyn = 'Y' " +
                " order by idx asc";

        ArrayList<String> tableZoneArray = new ArrayList<String>();

        Cursor dataCursor = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(strQuery);
        while (dataCursor.moveToNext()) {
            String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
            tableZoneArray.add(idx);
        }

        //For each table zone, get list of tables in that zone and add it to the 'data' jsonobject
        for (int i = 0; i < tableZoneArray.size(); i++) {
            String stringQuery = "select idx, tablename, zoneidx, mdate from salon_store_restaurant_table " +
                    " where deleteyn = 'N' and useyn = 'Y' and zoneidx = " + tableZoneArray.get(i) + " " +
                    " order by idx asc";

            Cursor dataCur = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(stringQuery);

            String idx = "";
            String tablename = "";
            String zoneidx = "";
            String mdate = "";

            while (dataCur.moveToNext()) {
                idx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(0), 1);
                tablename = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(1), 1);
                zoneidx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(2), 1);
                mdate = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(3), 1);

                JSONObject table = new JSONObject();
                try {
                    table.put("id", GlobalMemberValues.M_DBCODENAME + "tb_" + idx);
                    table.put("name", tablename);
                    table.put("floorCode", GlobalMemberValues.M_DBCODENAME + "tz_" + zoneidx);
                    table.put("updatedTime", mdate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //add the newly formed table object into tables array
                tables.put(table);
            }
        }

        //put tables json array into 'data' object.
        try {
            data.put("tables", tables);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create request body JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("code", code);
            requestBody.put("storeId", storeId);
            requestBody.put("message", message);
            requestBody.put("timestamp", timestamp);
            requestBody.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestBody;
    }

    public JSONObject makeMenuCategoryJSON(){
        String code = "P0301";
        String storeId = GlobalMemberValues.makeStoreCodeForTOrder();
        String message = "Store menu category updated";

        // create a clock
        ZoneId utczone = null;
        ZonedDateTime timestamp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            utczone = ZoneId.of("UTC");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timestamp = ZonedDateTime.now(utczone);
        }

        //data object where category information will go into
        JSONObject data = new JSONObject();

        //jsonArray where each category will be inserted into as an object.
        JSONArray goodGroups = new JSONArray();

        //Get categories of the store
        String strQuery = "select idx, servicename, wdate, mdate from salon_storeservice_main " +
                " where delyn = 'N'" +
                " order by idx asc";

        Cursor dataCursor = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(strQuery);

        //Loop through each category
        while (dataCursor.moveToNext()) {
            String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
            String servicename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
            String wdate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
            String mdate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);

            //relationsGood Array where each realtionsGoodObject will be inserted into
            JSONArray relationsGood = new JSONArray();

            //get menu items of the category
            String stringQuery = "select idx, positionNo from salon_storeservice_sub " +
                    " where delyn = 'N' and activeyn = 'Y' and midx = " + idx +
                    " order by idx asc";

            Cursor dataCur = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(stringQuery);

            //insert each menu item into relationsGood Array
            while (dataCur.moveToNext()) {
                String menuidx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(0), 1);
                String positionNo = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(1), 1);

                JSONObject relationsGoodObject = new JSONObject();
                try {
                    relationsGoodObject.put("goodId", GlobalMemberValues.M_DBCODENAME + "mn_" + menuidx);
                    relationsGoodObject.put("sort", positionNo);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                relationsGood.put(relationsGoodObject);
            }

            JSONObject goodGroup = new JSONObject();

            try {
                goodGroup.put("id", GlobalMemberValues.M_DBCODENAME + "ct_" + idx);
                goodGroup.put("name", servicename);

                //due to how menu is strctured, no category should have an 'upper' category, thus leave relationsGoodGroup should be empty
                goodGroup.put("relationsGoodGroup", new JSONArray());
                goodGroup.put("relationsGood", relationsGood);
                goodGroup.put("createdTime", wdate);
                goodGroup.put("updatedTime", mdate);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            goodGroups.put(goodGroup);
        }

        //Add goodGroups array, an array of menu categories into the 'data' key of the JSON.
        try {
            data.put("goodGroups", goodGroups);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create request body JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("code", code);
            requestBody.put("storeId", storeId);
            requestBody.put("message", message);
            requestBody.put("timestamp", timestamp);
            requestBody.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestBody;
    }

    public JSONObject makeMenuJSON(){
        String code = "P0302";
        String storeId = GlobalMemberValues.makeStoreCodeForTOrder();
        String message = "Store menu item(s) updated";

        // create a clock
        ZoneId utczone = null;
        ZonedDateTime timestamp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            utczone = ZoneId.of("UTC");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timestamp = ZonedDateTime.now(utczone);
        }

        //data object where table information will go into.
        JSONObject data = new JSONObject();

        //Goods JSONArray where each menu item and modifier item will be inserted into
        JSONArray goods = new JSONArray();

        //Get menu items of the store
        String strQuery = "select idx, servicename, servicename2, service_price, mdate from salon_storeservice_sub " +
                " where delyn = 'N' and activeyn = 'Y'" +
                " order by idx asc";

        Cursor dataCursor = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(strQuery);

        //Loop through each menu item
        while (dataCursor.moveToNext()) {
            String idx = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(0), 1);
            String servicename = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(1), 1);
            String servicename2 = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(2), 1);
            String servicepriceString = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(3), 1);
            String mdate = GlobalMemberValues.getDBTextAfterChecked(dataCursor.getString(4), 1);
            BigDecimal serviceprice = BigDecimal.ZERO;
            if (servicepriceString != null && !servicepriceString.equals("")){
                serviceprice = new BigDecimal(servicepriceString);
            }

            JSONObject good = new JSONObject();

            try {
                good.put("id", GlobalMemberValues.M_DBCODENAME + "mn_" + idx);
                good.put("type", "1");
                good.put("name", servicename + " " + servicename2);
                good.put("price", serviceprice);
                //Since this is an menu item, not a modifier item, it should have no 'parents', thus relations array should be empty
                good.put("relations", new JSONArray());
                good.put("updatedTime", mdate);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            goods.put(good);
        }

        //Get modifier items of the store
        String stringQuery = "select idx, itemname, itemprice, svcidx, optionidx from salon_storeservice_option_item " +
                " where itemuseyn = 'Y'" +
                " order by idx asc";

        Cursor dataCur = dbinitSendDataToTorderAfterDownloadService.dbExecuteRead(stringQuery);

        //Loop through each modifier item
        while (dataCur.moveToNext()) {
            String idx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(0), 1);
            String itemname = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(1), 1);
            String itempriceString = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(2), 1);
            String optionidx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(4), 1);
            BigDecimal itemprice = BigDecimal.ZERO;
            if (itempriceString != null && !itempriceString.equals("")){
                itemprice = new BigDecimal(itempriceString);
            }
            String svcidx = GlobalMemberValues.getDBTextAfterChecked(dataCur.getString(3), 1);

            //Use optionidx to check mdate for the modifier group, to get the updated time
            String checkmdateQuery = "select mdate from salon_storeservice_option " +
                    " where idx = '" + optionidx + "'";

            String mdate = dbinitSendDataToTorderAfterDownloadService.dbExecuteReadReturnString(checkmdateQuery);

            JSONObject good = new JSONObject();

            //relations array keep track of menu item to modifier item relationship.
            //Format: menu item id/modifier item id
            //Due to application structure, each modifier item should only be linked to one menu item, although each menu item
            //can have multiple modifier item.
            JSONArray relations = new JSONArray();

            JSONObject path = new JSONObject();
            try {
                path.put("path", GlobalMemberValues.M_DBCODENAME + "mn_" + svcidx + "/" + GlobalMemberValues.M_DBCODENAME + "mi_" + idx);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            relations.put(path);

            try {
                good.put("id", GlobalMemberValues.M_DBCODENAME + "mi_" + idx);
                good.put("type", "2");
                good.put("name", itemname);
                good.put("price", itemprice);
                good.put("relations", relations);
                good.put("updatedTime", mdate);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            goods.put(good);
        }

        //put goods array that contains menu item objects and modifier item objects into the data object.
        try {
            data.put("goods", goods);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create request body JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("code", code);
            requestBody.put("storeId", storeId);
            requestBody.put("message", message);
            requestBody.put("timestamp", timestamp);
            requestBody.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestBody;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
