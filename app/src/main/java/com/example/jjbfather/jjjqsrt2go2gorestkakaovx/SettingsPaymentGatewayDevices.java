package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class SettingsPaymentGatewayDevices extends Activity {
    static Activity mActivity;
    Context context;

    // DB 객체 선언
    DatabaseInit dbInit;

    private Button closeBtn;

    private Button pgipbtn;


    private EditText pgip01, pgip02, pgip03, pgip04, pgip05, pgip06, pgip07, pgip08, pgip09, pgip10;

    private EditText pgport01, pgport02, pgport03, pgport04, pgport05, pgport06, pgport07, pgport08, pgport09, pgport10;

    private Button pgbtn01, pgbtn02, pgbtn03, pgbtn04, pgbtn05, pgbtn06, pgbtn07, pgbtn08, pgbtn09, pgbtn10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_settings_payment_gateway_devices);
        this.setFinishOnTouchOutside(false);



        int parentLnWidth = 0;
        int parentLnHeight = 0;
        parentLnWidth = (GlobalMemberValues.getDisplayWidth(this) / 9) * 6;
        parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 10) * 9;
        if (GlobalMemberValues.thisTabletRealHeight < 800) {
            parentLnHeight = (GlobalMemberValues.getDisplayHeiheight(this) / 100) * 96;
        }

        LinearLayout.LayoutParams parentLnParams
                = new LinearLayout.LayoutParams(parentLnWidth, parentLnHeight);
        parentLnParams.setMargins(0, 0, 0, 0);

        LinearLayout parentLn = (LinearLayout)findViewById(R.id.settingsSystemLinearLayout);
        parentLn.setLayoutParams(parentLnParams);




        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(this);

        setContents();
    }

    public void setContents() {
        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.settingsSystemLn);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        mActivity = this;
        context = this;

        String pgdevicenum = "";
        String networkip = "";
        String networkport = "";

        String[] pgdevicenum_arr = {"", "", "", "", "", "", "", "", "", ""};
        String[] networkip_arr = {"", "", "", "", "", "", "", "", "", ""};
        String[] networkport_arr = {"", "", "", "", "", "", "", "", "", ""};

        String strQuery = "select pgdevicenum, networkip, networkport from salon_pgip ";
        ResultSet tempSaleCartCursor = MssqlDatabase.getResultSetValue(strQuery);
        try {
            int i = 0;
            while (tempSaleCartCursor.next()) {
                pgdevicenum = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 0), 1);
                networkip = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 1), 1);
                networkport = GlobalMemberValues.getDBTextAfterChecked(GlobalMemberValues.resultDB_checkNull_string(tempSaleCartCursor, 2), 1);

                if (GlobalMemberValues.isStrEmpty(pgdevicenum)) {
                    pgdevicenum = "";
                }
                if (GlobalMemberValues.isStrEmpty(networkip)) {
                    networkip = "";
                }
                if (GlobalMemberValues.isStrEmpty(networkport)) {
                    networkport = "";
                }

                pgdevicenum_arr[i] = pgdevicenum;
                networkip_arr[i] = networkip;
                networkport_arr[i] = networkport;

                i++;
            }
        } catch (Exception e) {

        }


        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.activityCloseBtn);
        closeBtn.setOnClickListener(mButtonClick);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            closeBtn.setText("");
            closeBtn.setBackgroundResource(R.drawable.ab_imagebutton_close_common2);
        } else {
            closeBtn.setTextSize(
                    closeBtn.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }



        // ip address ----------------------------------------------------------------------
        pgip01 = (EditText)findViewById(R.id.pgip01);
        pgip01.setText(networkip_arr[0]);
        pgip01.setTextSize(pgip01.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip02 = (EditText)findViewById(R.id.pgip02);
        pgip02.setText(networkip_arr[1]);
        pgip02.setTextSize(pgip02.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip03 = (EditText)findViewById(R.id.pgip03);
        pgip03.setText(networkip_arr[2]);
        pgip03.setTextSize(pgip03.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip04 = (EditText)findViewById(R.id.pgip04);
        pgip04.setText(networkip_arr[3]);
        pgip04.setTextSize(pgip04.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip05 = (EditText)findViewById(R.id.pgip05);
        pgip05.setText(networkip_arr[4]);
        pgip05.setTextSize(pgip05.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip06 = (EditText)findViewById(R.id.pgip06);
        pgip06.setText(networkip_arr[5]);
        pgip06.setTextSize(pgip06.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip07 = (EditText)findViewById(R.id.pgip07);
        pgip07.setText(networkip_arr[6]);
        pgip07.setTextSize(pgip07.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip08 = (EditText)findViewById(R.id.pgip08);
        pgip08.setText(networkip_arr[7]);
        pgip08.setTextSize(pgip08.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip09 = (EditText)findViewById(R.id.pgip09);
        pgip09.setText(networkip_arr[8]);
        pgip09.setTextSize(pgip09.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgip10 = (EditText)findViewById(R.id.pgip10);
        pgip10.setText(networkip_arr[9]);
        pgip10.setTextSize(pgip10.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // ip address ----------------------------------------------------------------------



        // port ----------------------------------------------------------------------
        pgport01 = (EditText)findViewById(R.id.pgport01);
        pgport01.setText(networkport_arr[0]);
        pgport01.setTextSize(pgport01.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport02 = (EditText)findViewById(R.id.pgport02);
        pgport02.setText(networkport_arr[1]);
        pgport02.setTextSize(pgport02.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport03 = (EditText)findViewById(R.id.pgport03);
        pgport03.setText(networkport_arr[2]);
        pgport03.setTextSize(pgport03.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport04 = (EditText)findViewById(R.id.pgport04);
        pgport04.setText(networkport_arr[3]);
        pgport04.setTextSize(pgport04.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport05 = (EditText)findViewById(R.id.pgport05);
        pgport05.setText(networkport_arr[4]);
        pgport05.setTextSize(pgport05.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport06 = (EditText)findViewById(R.id.pgport06);
        pgport06.setText(networkport_arr[5]);
        pgport06.setTextSize(pgport06.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport07 = (EditText)findViewById(R.id.pgport07);
        pgport07.setText(networkport_arr[6]);
        pgport07.setTextSize(pgport07.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport08 = (EditText)findViewById(R.id.pgport08);
        pgport08.setText(networkport_arr[7]);
        pgport08.setTextSize(pgport08.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport09 = (EditText)findViewById(R.id.pgport09);
        pgport09.setText(networkport_arr[8]);
        pgport09.setTextSize(pgport09.getTextSize() * GlobalMemberValues.getGlobalFontSize());

        pgport10 = (EditText)findViewById(R.id.pgport10);
        pgport10.setText(networkport_arr[9]);
        pgport10.setTextSize(pgport10.getTextSize() * GlobalMemberValues.getGlobalFontSize());
        // port ----------------------------------------------------------------------



        // select button ----------------------------------------------------------------------
        pgbtn01 = (Button)findViewById(R.id.pgbtn01);
        pgbtn01.setOnClickListener(mButtonClick);
//        pgbtn01.setTextSize(
//                pgbtn01.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );

        pgbtn02 = (Button)findViewById(R.id.pgbtn02);
        pgbtn02.setOnClickListener(mButtonClick);
//        pgbtn02.setTextSize(
//                pgbtn02.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn03 = (Button)findViewById(R.id.pgbtn03);
        pgbtn03.setOnClickListener(mButtonClick);
//        pgbtn03.setTextSize(
//                pgbtn03.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn04 = (Button)findViewById(R.id.pgbtn04);
        pgbtn04.setOnClickListener(mButtonClick);
//        pgbtn04.setTextSize(
//                pgbtn04.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn05 = (Button)findViewById(R.id.pgbtn05);
        pgbtn05.setOnClickListener(mButtonClick);
//        pgbtn05.setTextSize(
//                pgbtn05.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn06 = (Button)findViewById(R.id.pgbtn06);
        pgbtn06.setOnClickListener(mButtonClick);
//        pgbtn06.setTextSize(
//                pgbtn06.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn07 = (Button)findViewById(R.id.pgbtn07);
        pgbtn07.setOnClickListener(mButtonClick);
//        pgbtn07.setTextSize(
//                pgbtn07.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn08 = (Button)findViewById(R.id.pgbtn08);
        pgbtn08.setOnClickListener(mButtonClick);
//        pgbtn08.setTextSize(
//                pgbtn08.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn09 = (Button)findViewById(R.id.pgbtn09);
        pgbtn09.setOnClickListener(mButtonClick);
//        pgbtn09.setTextSize(
//                pgbtn09.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );
        pgbtn10 = (Button)findViewById(R.id.pgbtn10);
        pgbtn10.setOnClickListener(mButtonClick);
//        pgbtn10.setTextSize(
//                pgbtn10.getTextSize()
//                        * GlobalMemberValues.getGlobalFontSize()
//        );


        String thisPgdevicenum = MainActivity.mDbInit.dbExecuteReadReturnString(
                " select pgdevicenum from salon_storestationsettings_paymentgateway "
        );

        if (GlobalMemberValues.isStrEmpty(thisPgdevicenum)) {
            thisPgdevicenum = "";
        }
        setColorPgBtn(thisPgdevicenum);
        // select button ----------------------------------------------------------------------


        pgipbtn = (Button)findViewById(R.id.pgipbtn);
        pgipbtn.setOnClickListener(mButtonClick);
        pgipbtn.setText("");
        pgipbtn.setBackgroundResource(R.drawable.ab_imagebutton_settings_enter);
    }

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pgipbtn : {
                    int resultValue = setSettingSave();
                    if (resultValue > 0) {
                        String thisPgdevicenum = MainActivity.mDbInit.dbExecuteReadReturnString(
                                " select pgdevicenum from salon_storestationsettings_paymentgateway "
                        );

                        String ipaddr = MssqlDatabase.getResultSetValueToString(
                                " select networkip from salon_pgip where pgdevicenum = '" + thisPgdevicenum + "' "
                        );

                        SettingsPaymentGateway.devicenumTextView.setText(ipaddr);

                        GlobalMemberValues.displayDialog(context, "", "Successfully Updated", "Close");
                    } else {
                        GlobalMemberValues.displayDialog(context, "", "Update Failure", "Close");
                    }
                    break;
                }
                case R.id.pgbtn01 : {
                    selectPG("PG01");
                    break;
                }
                case R.id.pgbtn02 : {
                    selectPG("PG02");
                    break;
                }
                case R.id.pgbtn03 : {
                    selectPG("PG03");
                    break;
                }
                case R.id.pgbtn04 : {
                    selectPG("PG04");
                    break;
                }
                case R.id.pgbtn05 : {
                    selectPG("PG05");
                    break;
                }
                case R.id.pgbtn06 : {
                    selectPG("PG06");
                    break;
                }
                case R.id.pgbtn07 : {
                    selectPG("PG07");
                    break;
                }
                case R.id.pgbtn08 : {
                    selectPG("PG08");
                    break;
                }
                case R.id.pgbtn09 : {
                    selectPG("PG09");
                    break;
                }
                case R.id.pgbtn10 : {
                    selectPG("PG10");
                    break;
                }

                case R.id.activityCloseBtn : {
                    mActivity.finish();
                    if (GlobalMemberValues.isUseFadeInOut()) {
                        mActivity.overridePendingTransition(R.anim.act_in_top, R.anim.act_out_top);
                    }

                    break;
                }

            }
        }
    };

    public void selectPG(String paramPGDeviceNum) {
        if (!GlobalMemberValues.isStrEmpty(paramPGDeviceNum)) {
            Vector<String> updateVec = new Vector<String>();
            String strQuery = " update salon_storestationsettings_paymentgateway set pgdevicenum = '" + paramPGDeviceNum + "' ";
            updateVec.addElement(strQuery);

            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(updateVec);
            if (returnResult == "N" || returnResult == "") {
                GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {
                setColorPgBtn(paramPGDeviceNum);

                String deviceNumPGIP = "";
                if (!GlobalMemberValues.isStrEmpty(paramPGDeviceNum)) {
                    deviceNumPGIP = GlobalMemberValues.getPGDeviceNum() + " (IP : " + GlobalMemberValues.getPGIP(GlobalMemberValues.getPGDeviceNum()) + ") ";
                }

                SettingsPaymentGateway.devicenumTextView.setText(deviceNumPGIP);

                mActivity.finish();
            }
        }
    }

    public void setColorPgBtn(String paramPgDeviceNum) {
        pgbtn01.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn01.setTextColor(Color.parseColor("#000000"));
        pgbtn01.setText("select");

        pgbtn02.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn02.setTextColor(Color.parseColor("#000000"));
        pgbtn02.setText("select");

        pgbtn03.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn03.setTextColor(Color.parseColor("#000000"));
        pgbtn03.setText("select");

        pgbtn04.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn04.setTextColor(Color.parseColor("#000000"));
        pgbtn04.setText("select");

        pgbtn05.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn05.setTextColor(Color.parseColor("#000000"));
        pgbtn05.setText("select");

        pgbtn06.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn06.setTextColor(Color.parseColor("#000000"));
        pgbtn06.setText("select");

        pgbtn07.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn07.setTextColor(Color.parseColor("#000000"));
        pgbtn07.setText("select");

        pgbtn08.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn08.setTextColor(Color.parseColor("#000000"));
        pgbtn08.setText("select");

        pgbtn09.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn09.setTextColor(Color.parseColor("#000000"));
        pgbtn09.setText("select");

        pgbtn10.setBackgroundColor(Color.parseColor("#7b7b7b"));
        pgbtn10.setTextColor(Color.parseColor("#000000"));
        pgbtn10.setText("select");

        if (!GlobalMemberValues.isStrEmpty(paramPgDeviceNum)) {
            switch (paramPgDeviceNum) {
                case "PG01" : {
                    pgbtn01.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn01.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn01.setText("SELECTED");
                    break;
                }
                case "PG02" : {
                    pgbtn02.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn02.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn02.setText("SELECTED");
                    break;
                }
                case "PG03" : {
                    pgbtn03.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn03.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn03.setText("SELECTED");
                    break;
                }
                case "PG04" : {
                    pgbtn04.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn04.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn04.setText("SELECTED");
                    break;
                }
                case "PG05" : {
                    pgbtn05.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn05.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn05.setText("SELECTED");
                    break;
                }
                case "PG06" : {
                    pgbtn06.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn06.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn06.setText("SELECTED");
                    break;
                }
                case "PG07" : {
                    pgbtn07.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn07.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn07.setText("SELECTED");
                    break;
                }
                case "PG08" : {
                    pgbtn08.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn08.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn08.setText("SELECTED");
                    break;
                }
                case "PG09" : {
                    pgbtn09.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn09.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn09.setText("SELECTED");
                    break;
                }
                case "PG10" : {
                    pgbtn10.setBackgroundColor(Color.parseColor("#f21563"));
                    pgbtn10.setTextColor(Color.parseColor("#ffffff"));
                    pgbtn10.setText("SELECTED");
                    break;
                }
            }
        }

    }


    public int setSettingSave() {
        String[] pgdevicenum_arr = {"PG01", "PG02", "PG03", "PG04", "PG05", "PG06", "PG07", "PG08", "PG09", "PG10"};
        String[] networkip_arr = {"", "", "", "", "", "", "", "", "", ""};
        String[] networkport_arr = {"", "", "", "", "", "", "", "", "", ""};

        networkip_arr[0] = pgip01.getText().toString();
        networkip_arr[1] = pgip02.getText().toString();
        networkip_arr[2] = pgip03.getText().toString();
        networkip_arr[3] = pgip04.getText().toString();
        networkip_arr[4] = pgip05.getText().toString();
        networkip_arr[5] = pgip06.getText().toString();
        networkip_arr[6] = pgip07.getText().toString();
        networkip_arr[7] = pgip08.getText().toString();
        networkip_arr[8] = pgip09.getText().toString();
        networkip_arr[9] = pgip10.getText().toString();

        networkport_arr[0] = pgport01.getText().toString();
        networkport_arr[1] = pgport02.getText().toString();
        networkport_arr[2] = pgport03.getText().toString();
        networkport_arr[3] = pgport04.getText().toString();
        networkport_arr[4] = pgport05.getText().toString();
        networkport_arr[5] = pgport06.getText().toString();
        networkport_arr[6] = pgport07.getText().toString();
        networkport_arr[7] = pgport08.getText().toString();
        networkport_arr[8] = pgport09.getText().toString();
        networkport_arr[9] = pgport10.getText().toString();


        Vector<String> strUpdateQueryVec = new Vector<String>();
        String updStrQuery = "";
        for (int i = 0; i < 10; i++) {
            updStrQuery = "update salon_pgip set " +
                    " networkip = '" + networkip_arr[i] + "', " +
                    " networkport = '" + networkport_arr[i] + "' " +
                    " where pgdevicenum = '" + pgdevicenum_arr[i] + "' ";
            strUpdateQueryVec.addElement(updStrQuery);
        }

        int returnValue = 0;

        // 트랜잭션으로 DB 처리한다.
        if (strUpdateQueryVec.size() > 0) {
            // 트랜잭션으로 DB 처리한다.
            String returnResult = dbInit.dbExecuteWriteForTransactionReturnResult(strUpdateQueryVec);
            if (returnResult == "N" || returnResult == "") {
                returnValue = 0;
            } else {
                returnValue = 1;
            }
        } else {
            returnValue = 0;
        }

        return returnValue;
    }



}
