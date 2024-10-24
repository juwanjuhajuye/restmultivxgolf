package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class BayReservationViewer extends AppCompatActivity implements BayReservationViewerAdapter.ItemClickListener {

    private ImageButton closeBtn, searchBtn;

    private TextView searchDateEditText;

    private String searchDate = "07-09-2024";

    private String apiURLWithoutDate = "https://vxgolf.2go2go.com/api/API_Check_NewReservationList_ForAndroid.asp?";
    private String apiURLDate = "&schdate=07-09-2024";
    BayReservationViewerAdapter adapter;

    //Current JSON containing reservation data
    private JSONObject jsonData = null;

    Intent mIntent;
    private Boolean openedFromTable = false;


    // 08092024
    public static String mHoldcode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_bay_reservation_viewer);
        this.setFinishOnTouchOutside(false);

        //Set searchDate to current date right now.
        Calendar currCalendar = Calendar.getInstance();
        int month = currCalendar.get(Calendar.MONTH);
        int day = currCalendar.get(Calendar.DAY_OF_MONTH);
        int year = currCalendar.get(Calendar.YEAR);

        //set up the apiURL
        apiURLWithoutDate = apiURLWithoutDate + "sidx=" + GlobalMemberValues.STORE_INDEX + "&stcode=" + GlobalMemberValues.STATION_CODE;

        //add 1 to month because month as an integer starts at 0, meaning January is 0 instead of 1.
        searchDate = (month+1) + "-" + day + "-" + year;

        apiURLDate = "&schdate=" + year + "-" + (month+1) + "-" + day;

        //fetch the data from api and update the json
        new APIDataFetcher().execute();


        //10172024 set intent value
        mIntent = getIntent();

        if (mIntent != null) {
            openedFromTable = mIntent.getBooleanExtra("OpenedFromTable", false);
        }


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.bayreservation_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BayReservationViewerAdapter(this, new ArrayList<String[]>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        setContents();
    }

    private void setContents(){
        closeBtn = findViewById(R.id.bayreservation_list_close);
        closeBtn.setOnClickListener(bayReservationListBtnClickListener);

        searchBtn = findViewById(R.id.bayreservation_list_search);
        searchBtn.setOnClickListener(bayReservationListBtnClickListener);

        searchDateEditText = findViewById(R.id.bayreservation_list_StartDateEditText);
        searchDateEditText.setOnClickListener(bayReservationListBtnClickListener);
        searchDateEditText.setText(searchDate);
    }

    private void updateReservationList(JSONObject reservationInfoJSON){
        // data to populate the RecyclerView with
        ArrayList<String[]> reservationInfo = new ArrayList<>();
        int reservationCount = 0;
        try {
            if(reservationInfoJSON != null){
                reservationCount = reservationInfoJSON.getJSONArray("reservationList").length();
            }
        } catch (JSONException e) {
            GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "DATA FAILED TO POPULATE LIST");
        }

        //Go through each reservation and put each reservation(String array) into reservationInfo arraylist.
        for(int i=0; i<reservationCount; i++){
            try {
                String[] reservationInfoArray = new String [11];
                reservationInfoArray[0] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("reservationdate"));
                reservationInfoArray[1] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("tablename"));
                reservationInfoArray[2] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("bayservicename"));
                reservationInfoArray[3] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("customername"));
                reservationInfoArray[4] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("customerphone"));
                reservationInfoArray[5] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("holdcode"));
                reservationInfoArray[6] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("downloadyn"));
                reservationInfoArray[7] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("calledyn"));
                reservationInfoArray[8] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("customersu"));
                reservationInfoArray[9] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("reservationregdate"));
                reservationInfoArray[10] = (reservationInfoJSON.getJSONArray("reservationList").getJSONObject(i).getString("delyn"));


                reservationInfo.add(reservationInfoArray);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        adapter.setItems(reservationInfo);
        adapter.notifyDataSetChanged();
    }

    private class APIDataFetcher extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject returnJSON = null;

            try {
                returnJSON = fetchAPIData();
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", returnJSON.toString());
            }
            catch(Exception ex) {
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "data failed to fetch from API");
            }
            return returnJSON;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // Do the UI-task here
            if(result != null){
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", result.toString());
                jsonData = result;
                updateReservationList(jsonData);
            }
        }

        @Override
        protected void onPreExecute() {
            // Do the UI-task here
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Do the UI-task here which has to be done during backgroung tasks are running like a downloading process
        }
    }

    private class APIDataPoster extends AsyncTask<String, Void, JSONObject> {
        String holdcode = "";
        
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject returnJSON = null;

            try {
                returnJSON = deleteReservationAPICall(holdcode);
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", returnJSON.toString());
            }
            catch(Exception ex) {
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "data failed to fetch from API");
            }
            return returnJSON;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // Do the UI-task here
            if(result != null){
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", result.toString());
                jsonData = result;
                updateReservationList(jsonData);
            }
        }

        @Override
        protected void onPreExecute() {
            // Do the UI-task here
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Do the UI-task here which has to be done during backgroung tasks are running like a downloading process
        }
    }

    //Method to fetch data from api
    private JSONObject fetchAPIData(){
        JSONObject returnJson = null;
        String tagName = "";
        String responseBody = "";
        String mApiReturnCode = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite("datacheckjjjlog", "url : " + apiURLWithoutDate + apiURLDate + "\n");

            URL url = new URL(apiURLWithoutDate + apiURLDate);
            //URL url = new URL("https://httpbin.org/get");

            HttpURLConnection conn = null;

            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String strCurrentLine;
            try{
                while ((strCurrentLine = br.readLine()) != null) {
                    GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", strCurrentLine);
                    responseBody = responseBody + strCurrentLine;
                }
            } finally {
                conn.disconnect();
            }

        } catch (Exception e) {
            // 예외발생
            GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "DATA FAILED TO FETCH FROM API");
        }

        //Try to convert the return value from a string to JSON
        if(!Objects.equals(responseBody, "")){
            try {
                returnJson = new JSONObject(responseBody);
            } catch (Throwable t) {
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "Could not parse malformed JSON");
            }
        }

        return returnJson;
    }

    //Method to delete reservation data from API
    private JSONObject deleteReservationAPICall(String holdcode){
        String returnString = "";
        JSONObject returnJson = null;

        try {
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            HttpURLConnection conn = null;
            String response = "";

            // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
            try {
                URL url = new URL("https://vxgolf.2go2go.com/api/API_UP_ReservationDelete_ForAndroid.asp?" +
                        "sidx=" + GlobalMemberValues.STORE_INDEX + "&holdcode=" + holdcode );

                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                //conn.setRequestProperty ("Authorization", basicAuth);
                conn.setRequestMethod("POST");
                //conn.setRequestProperty("Authorization", "Bearer " + GlobalMemberValues.getTOrderApiKey());
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                ;

                os = conn.getOutputStream();
                os.flush();

                int responseCode = conn.getResponseCode();
                String responseBody = conn.getResponseMessage();
                String reponseMethod = conn.getRequestMethod();

                GlobalMemberValues.logWrite("TORDERDEBUG", String.valueOf(responseCode));

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    is = conn.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();
                    response = new String(byteData);
                    GlobalMemberValues.logWrite("BayReservationViewerAdapter", "DATA response = " + response + "\n");

                    //mFlag = true;
                    //cloudSentResult = 0;
                    //smsSentResultStr = response;
                } else {
                    is = conn.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();
                    response = new String(byteData);
                    GlobalMemberValues.logWrite("BayReservationViewerAdapter", "DATA response = " + response + "\n");

                }

                returnString = response;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //cloudSentResult = 1;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //cloudSentResult = 2;
            } catch (Exception e) {
                GlobalMemberValues.logWrite("BayReservationViewerAdapter", "error msg :" + e.toString() + "\n");
                //cloudSentResult = 3;
            }
            // ---------------------------------------------------------------------------------

        } catch (Exception e) {
            // 예외발생
            GlobalMemberValues.logWrite("BayReservationViewerAdapter", "error msg :" + e.toString() + "\n");
        }

        //Try to convert the return value from a string to JSON
        if(!Objects.equals(returnString, "")){
            try {
                returnJson = new JSONObject(returnString);
            } catch (Throwable t) {
                GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "Could not parse malformed JSON");
            }
        }

        return returnJson;
    }


    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.bayreservation_list_button_delete : {
                GlobalMemberValues.logWrite("BayReservationViewerAdapter", "Hello: " + adapter.getHoldCode(position));
                //make POST request to delete reservation using API
                APIDataPoster apiPostTask = new APIDataPoster();
                apiPostTask.holdcode = adapter.getHoldCode(position);
                apiPostTask.execute();
                
                try {
                    new APIDataFetcher().execute();
                } catch (Exception e) {
                    GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "Error: " + e);
                }
                break;
            }
            case R.id.bayreservation_list_button_get_reservation : {
                GlobalMemberValues.logWrite("BayReservationViewerAdapter", "Bye: " + adapter.getHoldCode(position));
                mHoldcode = adapter.getHoldCode(position);
                GlobalMemberValues.checkTableOrder(MainActivity.mContext, MainActivity.mActivity);

                //10172024 only open table if the bay reservation viewer was not started from a table screen
                //this stops a table scren opening on top of another, preventing interaction.
                if (!openedFromTable){
                    GlobalMemberValues.openRestaurantTable();
                }
                finish();
                break;
            }
            case R.id.bayreservation_list_button_restore: {
                GlobalMemberValues.logWrite("BayReservationViewerAdapter", "Hello: " + adapter.getHoldCode(position));
                //make POST request to delete reservation using API
                APIDataPoster apiPostTask = new APIDataPoster();
                apiPostTask.holdcode = adapter.getHoldCode(position);
                apiPostTask.execute();

                try {
                    new APIDataFetcher().execute();
                } catch (Exception e) {
                    GlobalMemberValues.logWrite("BAYRESERVATIONVIEWER", "Error: " + e);
                }
                break;
            }
        }
    }

    View.OnClickListener bayReservationListBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bayreservation_list_close: {
                    finish();
                    break;
                }
                case R.id.bayreservation_list_StartDateEditText:{
                    openDatePickerDialog(searchDate);
                    break;
                }
                case R.id.bayreservation_list_search:{
                    //Rerun API call with new date selected
                    new APIDataFetcher().execute();
                    break;
                }
            }
        }
    };

    //Method that opens date dialog where user can pick which date to search
    private void openDatePickerDialog(String paramDate) {
        String tempSplitStr[] = null;
        tempSplitStr = paramDate.split("-");
        int tempMonth = Integer.parseInt(tempSplitStr[0]);
        int tempDay = Integer.parseInt(tempSplitStr[1]);
        int tempYear = Integer.parseInt(tempSplitStr[2]);
        DatePickerDialog dateDialog = new DatePickerDialog(this, dateSelectListener, tempYear, tempMonth-1, tempDay);
        dateDialog.show();
    }

    //What to do after the user selects the date
    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Parse chosen date and save it.
            String tempYear = String.valueOf(year);
            String tempMonth = String.valueOf(monthOfYear+1);
            if ((monthOfYear+1) < 10) {
                tempMonth = "0" + tempMonth;
            }
            String tempDay = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                tempDay = "0" + tempDay;
            }
            //Save value and update UI
            searchDate = tempMonth + "-" + tempDay + "-" + tempYear;
            searchDateEditText.setText(searchDate);

            apiURLDate = "&schdate=" + searchDate;

        }
    };
}