package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.DatabaseInit;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GetDataAtSQLite;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.TemporarySaleCart;

import java.util.ArrayList;

public class EmployeeKeyIn extends Activity {
    final String TAG = "EmployeeKeyInLog";

    static Activity mActivity;
    static Context mContext;

    // DB 객체 선언
    DatabaseInit dbInit;

    private LinearLayout parentLn, emptyLinearLayout;
    private Button closeBtn;

    private Button cashEnter_suButton1,cashEnter_suButton2,cashEnter_suButton3;
    private Button cashEnter_suButton4,cashEnter_suButton5,cashEnter_suButton6;
    private Button cashEnter_suButton7,cashEnter_suButton8,cashEnter_suButton9;
    private Button cashEnter_suButton0,cashEnter_suButtonBack;
    private Button cashEnter_suButtonX, cashEnter_nextStep;

    private static TextView servernumtv, msgtv;

    static String mServerNum = "";

    StringBuffer sb = new StringBuffer();

    static String mMainSide = "";
    static ArrayList<String> mParamArr;

    static String mSelectedYN = "N";

    Intent mIntent;

    static boolean is_input_pass = false;
    static String temp_eid = "";

    // 07202023
    static boolean isClickNextBtn = false;

    //11292023

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_employee_key_in);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        mContext = this;

        // DatabaseInit 객체 생성
        dbInit = new DatabaseInit(mContext);

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            mMainSide = mIntent.getStringExtra("main_side");
            mParamArr = mIntent.getStringArrayListExtra("table_idx_arr_list");
            /*******************************************************************************************/
            GlobalMemberValues.logWrite(TAG, "mMainSide : " + mMainSide + "\n");
//            GlobalMemberValues.logWrite(TAG, "mParamArr : " + mParamArr.toString() + "\n");
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        mSelectedYN = "N";
        mServerNum = "";

        // 07202023
        isClickNextBtn = false;

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        emptyLinearLayout = (LinearLayout)findViewById(R.id.emptyLinearLayout);
        emptyLinearLayout.setOnClickListener(cashEnterButtonClickListener);

        /***********************************************************************************************************/

        cashEnter_suButton1 = (Button)findViewById(R.id.cashEnter_suButton1);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton1.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton1.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton1.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton1.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton2 = (Button)findViewById(R.id.cashEnter_suButton2);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton2.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton2.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton2.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton2.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton3 = (Button)findViewById(R.id.cashEnter_suButton3);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton3.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton3.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton3.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton3.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton4 = (Button)findViewById(R.id.cashEnter_suButton4);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton4.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton4.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton4.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton4.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton5 = (Button)findViewById(R.id.cashEnter_suButton5);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton5.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton5.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton5.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton5.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton6 = (Button)findViewById(R.id.cashEnter_suButton6);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton6.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton6.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton6.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton6.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton7 = (Button)findViewById(R.id.cashEnter_suButton7);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton7.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton7.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton7.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton7.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton8 = (Button)findViewById(R.id.cashEnter_suButton8);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton8.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton8.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton8.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton8.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton9 = (Button)findViewById(R.id.cashEnter_suButton9);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton9.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton9.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton9.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton9.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButton0 = (Button)findViewById(R.id.cashEnter_suButton0);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize() * 1.3f
            );
            cashEnter_suButton0.setTextColor(Color.parseColor(GlobalMemberValues.GLOBAL_NUMBERBUTTON_COLOR));
            cashEnter_suButton0.setBackgroundResource(R.drawable.ab_imagebutton_qty_number);
        } else {
            cashEnter_suButton0.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButton0.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }
        cashEnter_suButtonBack = (Button)findViewById(R.id.cashEnter_suButtonBack);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonBack.setText("");
            cashEnter_suButtonBack.setBackgroundResource(R.drawable.ab_imagebutton_qty_delete);
        } else {
            cashEnter_suButtonBack.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButtonBack.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }


        cashEnter_suButtonX = (Button)findViewById(R.id.cashEnter_suButtonX);
        if (GlobalMemberValues.IMAGEBUTTONYN == 0) {
            cashEnter_suButtonX.setText("");
            cashEnter_suButtonX.setBackgroundResource(R.drawable.ab_imagebutton_close_big);
        } else {
            cashEnter_suButtonX.setTextSize(GlobalMemberValues.globalAddFontSize() +
                    cashEnter_suButtonX.getTextSize()
                            * GlobalMemberValues.getGlobalFontSize()
            );
        }

        cashEnter_nextStep = (Button)findViewById(R.id.cashEnter_nextStep);
        cashEnter_nextStep.setTextSize(GlobalMemberValues.globalAddFontSize() +
                cashEnter_nextStep.getTextSize()
                        * GlobalMemberValues.getGlobalFontSize()
        );

        cashEnter_suButton1.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton2.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton3.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton4.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton5.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton6.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton7.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton8.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton9.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButton0.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonBack.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_suButtonX.setOnClickListener(cashEnterButtonClickListener);
        cashEnter_nextStep.setOnClickListener(cashEnterButtonClickListener);


        servernumtv = (TextView) findViewById(R.id.servernumtv);
        servernumtv.setTextSize(GlobalMemberValues.globalAddFontSize() + servernumtv.getTextSize()
                * GlobalMemberValues.getGlobalFontSize());
        servernumtv.setText("");

        msgtv = (TextView) findViewById(R.id.msgtv);
        msgtv.setTextSize(GlobalMemberValues.globalAddFontSize() + msgtv.getTextSize()
                * GlobalMemberValues.getGlobalFontSize());
        //msgtv.setText("");
        msgtv.setTextColor(Color.parseColor("#ffffff"));


        // 직원코드 사용여부. Pass in employee id automatically if only password is used for verification
        // There can be no password repeats so each password is unique to an employee
        if (!GlobalMemberValues.isServerCodeUse()) {
            GlobalMemberValues.logWrite("eidlogjjj222222", "GlobalMemberValues.SERVER_ID : " + GlobalMemberValues.SERVER_ID + "\n");
            servernumtv.setText(GlobalMemberValues.SERVER_ID);

            // 07202023
            msgtv.setText("ENTER YOUR PASSWORD");

            temp_eid = GlobalMemberValues.SERVER_ID;

            goNextStep();

            //authenticateServerCode();


        }
    }

    View.OnClickListener cashEnterButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cashEnter_suButton1 : {
                    numberButtonClick(cashEnter_suButton1);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton2 : {
                    numberButtonClick(cashEnter_suButton2);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton3 : {
                    numberButtonClick(cashEnter_suButton3);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton4 : {
                    numberButtonClick(cashEnter_suButton4);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton5 : {
                    numberButtonClick(cashEnter_suButton5);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton6 : {
                    numberButtonClick(cashEnter_suButton6);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton7 : {
                    numberButtonClick(cashEnter_suButton7);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton8 : {
                    numberButtonClick(cashEnter_suButton8);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton9 : {
                    numberButtonClick(cashEnter_suButton9);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                case R.id.cashEnter_suButton0 : {
                    numberButtonClick(cashEnter_suButton0);

                    // 키패드(키보드) 감추기
                    //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                    break;
                }
                /**
                 case R.id.cashEnter_suButton00 : {
                 numberButtonClick(cashEnter_suButton00);

                 // 키패드(키보드) 감추기
                 //GlobalMemberValues.setKeyPadHide(getApplication(), cashAmountEv);
                 break;
                 }
                 **/
                case R.id.cashEnter_suButtonBack : {
                    sb.delete(0, sb.toString().length());
                    sb.append(mServerNum);
                    if (!GlobalMemberValues.isStrEmpty(mServerNum)) {
                        sb.delete((sb.toString().length()-1), sb.toString().length());
                        mServerNum = sb.toString();
                        if (GlobalMemberValues.isStrEmpty(mServerNum)) {
                            mServerNum = "";
                        }
                    } else {
                        mServerNum = "";
                    }
                    servernumtv.setText(mServerNum);
                    break;
                }

                case R.id.cashEnter_nextStep : {
                    // 07202023
                    isClickNextBtn = true;

                    goNextStep();

                    break;
                }

                case R.id.cashEnter_suButtonX : {
                    closeActivity();

                    break;
                }

                case R.id.emptyLinearLayout : {
                    closeActivity();
                    break;
                }

            }
        }
    };


    private void numberButtonClick(Button btn) {
        sb.delete(0, sb.toString().length());
        if (mServerNum.length() < 8) {
            sb.append(mServerNum).append(btn.getText().toString());
            int tempNumber = Integer.parseInt(sb.toString());
            mServerNum = String.valueOf(tempNumber);
            servernumtv.setText(mServerNum);
        }
    }

    public static void goNextStep() {
        //current input
        String getInputNum = servernumtv.getText().toString();
        GlobalMemberValues.logWrite("eidlogjjj", "eid1 : " + getInputNum + "\n");

        //if there is input
        if (!GlobalMemberValues.isStrEmpty(getInputNum)) {
            // 직원정보 셋팅
            //setEmpInfo(getEmpNum);
            //GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;

            //11302023
            //checking user code
            if(!is_input_pass){
                checkServerAccessCode(getInputNum);
            }
            //checking password
            else {
                //parameter: user code, password
                checkAccessCodePassword(temp_eid, getInputNum);
            }
        }
        //if input is empty, and -> button (next step button) is pressed
        else {
            if (is_input_pass){
                msgtv.setTextColor(Color.RED);
                msgtv.setText("ENTER YOUR PASSWORD");
                servernumtv.setText("");
                mServerNum = "";
            }
            else {
                msgtv.setTextColor(Color.RED);
                msgtv.setText("ENTER YOUR ACCESS CODE");
                servernumtv.setText("");
                mServerNum = "";
            }

        }
    }

    //DEPRECATED
    public static void setEmpInfo(String paramEid) {
        String input_pass = "";
        if (is_input_pass){
            input_pass = paramEid;
            paramEid = temp_eid;
        }

        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(mContext);
        String getEmpInfo = dataAtSqlite.getEmployeeInfoByEid(paramEid);



        // 07132023
        boolean isOnlyServerPassword = false;
        if (GlobalMemberValues.isServerCodeUse() && GlobalMemberValues.isServerPasswordUse()) {
            getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessCode(paramEid);
            GlobalMemberValues.logWrite("eidlogjjj33333", "여기용1" + "\n");
        }
        if (GlobalMemberValues.isServerCodeUse() && !GlobalMemberValues.isServerPasswordUse()) {
            getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessCode(paramEid);
            GlobalMemberValues.logWrite("eidlogjjj33333", "여기용2" + "\n");
        }
        if (!GlobalMemberValues.isServerCodeUse() && GlobalMemberValues.isServerPasswordUse()) {
            getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessPassword(paramEid);
            isOnlyServerPassword = true;
            GlobalMemberValues.logWrite("eidlogjjj33333", "여기용3" + "\n");
        }


        if (!GlobalMemberValues.isStrEmpty(getEmpInfo)) {
            String[] strEmployeeInfoArr = getEmpInfo.split(GlobalMemberValues.STRSPLITTER1);

            if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {

                if (isOnlyServerPassword) {
                    is_input_pass = false;
                    empPassSuccess(strEmployeeInfoArr);
                } else {
                    if (GlobalMemberValues.isServerPasswordUse()){
                        if (is_input_pass){
                            if (strEmployeeInfoArr[9].equals(input_pass)){
                                // 비번까지 완료.
                                is_input_pass = false;
                                empPassSuccess(strEmployeeInfoArr);
                            } else {
                                // 비번 틀림.
                                // set pass input
                                msgtv.setText("WRONG PASSWORD\nTRY AGAIN");
                                servernumtv.setText("");
                                mServerNum = "";
                            }
                        } else {
                            is_input_pass = true;
                            temp_eid = paramEid;
                            // set pass input
                            msgtv.setText("ENTER YOUR PASSWORD");
                            servernumtv.setText("");
                            mServerNum = "";
                        }
                    } else {
                        is_input_pass = false;
                        empPassSuccess(strEmployeeInfoArr);
                    }
                }




            }
        } else {
            // 07202023
            if (isClickNextBtn) {
                msgtv.setText("WRONG NUMBER\nTRY AGAIN");
                msgtv.setTextColor(Color.RED);
            }

            servernumtv.setText("");
            mServerNum = "";
        }
    }

    public static void checkServerAccessCode(String servercode){
        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(mContext);

        //pulls employee info from db using employee server code
        String getEmpInfo = dataAtSqlite.getEmployeeInfoByEid(servercode);

        getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessCode(servercode);

        //Employee is found, servercode is valid
        if (!GlobalMemberValues.isStrEmpty(getEmpInfo)) {
            String[] strEmployeeInfoArr = getEmpInfo.split(GlobalMemberValues.STRSPLITTER1);

            if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
                //If only server code is checked, just check if the entered user exists
                //If both server code and password is checked, prompt user to type in password also.
                if (GlobalMemberValues.isServerPasswordUse()){
                    //update variables to update UI to prompt user to enter password
                    is_input_pass = true;
                    temp_eid = servercode;
                    // set pass input
                    msgtv.setText("ENTER YOUR PASSWORD");
                    servernumtv.setText("");
                    mServerNum = "";
                }
                //only server code is required, user passes security without password check.
                else {
                    is_input_pass = false;
                    empPassSuccess(strEmployeeInfoArr);
                }
            }
        }
        //user code doesn't exist
        else {
            // 07202023
            if (isClickNextBtn) {
                msgtv.setText("WRONG NUMBER\nTRY AGAIN");
                msgtv.setTextColor(Color.RED);
            }

            servernumtv.setText("");
            mServerNum = "";
        }
    }

    public static void checkAccessCodePassword(String servercode, String password){

        GetDataAtSQLite dataAtSqlite = new GetDataAtSQLite(mContext);
        String getEmpInfo = "";

        //If only password is checked, just check if the entered password exists.
        //If both server code and password is checked, check the entered password against the previously entered server/employee code.
        boolean isOnlyServerPassword = false;

        if (GlobalMemberValues.isServerCodeUse() && GlobalMemberValues.isServerPasswordUse()) {
            getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessCode(servercode);
        }

        if (!GlobalMemberValues.isServerCodeUse() && GlobalMemberValues.isServerPasswordUse()) {
            getEmpInfo = dataAtSqlite.getEmployeeInfoByServerAccessPassword(password);
            isOnlyServerPassword = true;
        }

        //Only checks for password, not user code
        if (isOnlyServerPassword){
            //Password is found, password is valid
            //Since passwords must be unique, each password corresponds to only one employee/server
            if (!GlobalMemberValues.isStrEmpty(getEmpInfo)) {
                String[] strEmployeeInfoArr = getEmpInfo.split(GlobalMemberValues.STRSPLITTER1);

                // 비번까지 완료. password passed
                is_input_pass = false;
                //passes the check, user is moved into menu screen
                empPassSuccess(strEmployeeInfoArr);
            }
            //password is not found!
            else {
                if (isClickNextBtn) {
                    msgtv.setText("WRONG PASSWORD\nTRY AGAIN");
                    msgtv.setTextColor(Color.RED);
                }

                //reset input field
                servernumtv.setText("");
                mServerNum = "";
            }
        }
        //Checks for user code and password
        else {
            //user code is found
            if (!GlobalMemberValues.isStrEmpty(getEmpInfo)) {
                String[] strEmployeeInfoArr = getEmpInfo.split(GlobalMemberValues.STRSPLITTER1);

                if (!GlobalMemberValues.isStrEmpty(strEmployeeInfoArr[1])) {
                    //Check if the password stored in employeeinfo array matches the entered password
                    if (strEmployeeInfoArr[9].equals(password)){
                        // 비번까지 완료. password passed
                        is_input_pass = false;
                        //passes the check, user is moved into menu screen
                        empPassSuccess(strEmployeeInfoArr);
                    } else {
                        // 비번 틀림. wrong password
                        msgtv.setText("WRONG PASSWORD\nTRY AGAIN");
                        msgtv.setTextColor(Color.RED);
                        servernumtv.setText("");
                        mServerNum = "";
                    }
                }
            }
            //user code is not found!
            //This should never happen? Because user code should already be verified.
            else {
                // 07202023
                if (isClickNextBtn) {
                    msgtv.setText("WRONG NUMBER\nTRY AGAIN");
                    msgtv.setTextColor(Color.RED);
                }

                //reset input field
                servernumtv.setText("");
                mServerNum = "";
            }
        }

    }

    private static void empPassSuccess(String[] strEmployeeInfoArr){

//                GlobalMemberValues.GLOBAL_EMPLOYEEINFO
//                        = new TemporaryEmployeeInfo(strEmployeeInfoArr[0], strEmployeeInfoArr[1], strEmployeeInfoArr[2], strEmployeeInfoArr[3],
//                        strEmployeeInfoArr[4], strEmployeeInfoArr[5], strEmployeeInfoArr[6], strEmployeeInfoArr[7], strEmployeeInfoArr[8],
//                        strEmployeeInfoArr[9], strEmployeeInfoArr[10], strEmployeeInfoArr[12]);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINBUTTONTOPEMPLOYEEINFO.setText(strEmployeeInfoArr[1]);

        // 0719 server 정보 추가.
        GlobalMemberValues.SERVER_IDX = strEmployeeInfoArr[0];
        GlobalMemberValues.SERVER_ID = strEmployeeInfoArr[8];
        GlobalMemberValues.SERVER_NAME = strEmployeeInfoArr[1];

        GlobalMemberValues.logWrite("jjjserverloginlog", "GlobalMemberValues.SERVER_IDX : " + GlobalMemberValues.SERVER_IDX + "\n");
        GlobalMemberValues.logWrite("jjjserverloginlog", "GlobalMemberValues.SERVER_NAME : " + GlobalMemberValues.SERVER_NAME + "\n");


        // main_side_button on
        // 타임메뉴 셋
        MainActivity mainActivity = new MainActivity();
        mainActivity.timeMenu_getServiceTime();

        //GlobalMemberValues.LOGIN_EMPLOYEE_ID = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empEid;

        // 타임메뉴 설정된대로 실행.
        GlobalMemberValues.openTimeMenuSelectPopupAuto();

        mSelectedYN = "Y";

        // 서비스 선택리스트에 선택된 서비스가 있을 경우
        // 선택한 서비스의 employee 를 변경할지 여부를 물어 처리한다.
        if (MainMiddleService.selectedPosition != -1) {
            new AlertDialog.Builder(mContext)
                    .setTitle("Item Delete")
                    .setMessage("Change the employee seleted item?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    TemporarySaleCart tempSCIns = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);

                                    // temp_salecart 테이블 에서 선택한 서비스의 직원 employee 를 변경한다.
                                    // DB 수정
                                    String tempSaleCartIdx = tempSCIns.tempSaleCartIdx;
                                    if (!GlobalMemberValues.isStrEmpty(tempSaleCartIdx)) {
                                        String strSqlQuery = "update temp_salecart set " +
                                                " empIdx ='" + strEmployeeInfoArr[0] + "', " +
                                                " empName ='" + strEmployeeInfoArr[1] + "' " +
                                                " where idx = '" + tempSaleCartIdx + "' ";
                                        GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "temp_salecart update query : " + strSqlQuery + "\n");
                                        DatabaseInit dbInit = new DatabaseInit(MainActivity.mContext);
                                        String returnCode = "";
                                        returnCode = dbInit.dbExecuteWriteReturnValue(strSqlQuery);
                                        GlobalMemberValues.logWrite("EmployeeSelecttionClassLog", "returnCode : " + returnCode + "\n");
                                        if (!GlobalMemberValues.isStrEmpty(returnCode) && returnCode == "0") {
                                            // 변경된 TemporarySaleCart 객체를 부모의 ArrayList 객체에 담는다.
                                            tempSCIns.mEmpIdx = strEmployeeInfoArr[0];
                                            tempSCIns.mEmpName = strEmployeeInfoArr[1];

                                            MainMiddleService.mGeneralArrayList.set(MainMiddleService.selectedPosition, tempSCIns);
                                            MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                                            MainMiddleService.selectedPosition = -1;
                                        }
                                    }
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainMiddleService.mSaleCartAdapter.notifyDataSetChanged();
                            MainMiddleService.selectedPosition = -1;
                        }
                    })
                    .show();
        }

        closeActivity();
    }

    private static void closeActivity() {
        if (mSelectedYN == "Y") {
            if (mMainSide!= null) {
                if (mMainSide.equals("T")){
                    TableSaleMain.setOrderStart(mParamArr, true, false);
                } else if (mMainSide.equals("H")){
                    mActivity.setResult(RESULT_OK);
                }
            }
        } else {
            if (!GlobalMemberValues.isShowQuickMenusInTableBoard) {
                TableSaleMain.setClearSelectedTableIdx(true);
            }
            TableSaleMain.doCellSelectEvent();
        }
        is_input_pass = false;
        temp_eid = "";
        mActivity.finish();
        if (GlobalMemberValues.isUseFadeInOut()) {
            mActivity.overridePendingTransition(R.anim.act_in_right, R.anim.act_out_right);
        }
    }
}