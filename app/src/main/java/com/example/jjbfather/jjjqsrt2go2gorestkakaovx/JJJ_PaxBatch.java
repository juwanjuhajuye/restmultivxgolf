package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Spinner;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.pax.poslink.BatchRequest;
import com.pax.poslink.BatchResponse;
import com.pax.poslink.PosLink;
import com.pax.poslink.ProcessTransResult;
import com.pax.poslink.ProcessTransResult.ProcessTransResultCode;

import org.json.JSONException;
import org.json.JSONObject;

public class JJJ_PaxBatch extends BaseFragmentActivity implements OnClickListener,OnTouchListener, ProgressDialogFragment.OnSetListener {
    Activity mActivity = null;
    Context mContext = null;

    private static final String TAG = "BatchActivity";

    private Spinner mTransTypeRequestEdit = null;

    private String[] mStrArrayTrans = null;

    // jihun park
    private PosLink poslink = new PosLink();
    //    private PosLink poslink = new PosLink(this, Context.class);

    private static ProcessTransResult ptr;
    private String mRequestCommand = "RESET";

    private BroadcastReceiver mCommandDishBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(Constant.BROADCAST_COMMAND)) {
                mRequestCommand = intent.getStringExtra(Constant.COMMAND_NAME);
                for (String i : mStrArrayTrans)
                {
                    if (i.equals(mRequestCommand))
                    {
                        initVisibleView(mRequestCommand);
                    }
                }
            }
        }
    };

    private void initVisibleView(String RequestCommand){
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.TRANSACTION_SUCCESSED:
                    BatchResponse response = (BatchResponse)msg.obj;
                    setBatchResponse(response);
                    break;
                case Constant.TRANSACTION_TIMEOOUT:
                case Constant.TRANSACTION_FAILURE:
                    String title = msg.getData().getString(Constant.DIALOG_TITLE);
                    String message = msg.getData().getString(Constant.DIALOG_MESSAGE);
                    PopupDialogFragment.newInstance(title, message, true, true).show(getSupportFragmentManager(), "batch_failure");
                    getSupportFragmentManager().beginTransaction().commitAllowingStateLoss();
                    break;
            }

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjjpax_batch);

        mActivity = this;
        mContext = this;

        setTask();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_COMMAND);
        registerReceiver(mCommandDishBroadcastReceiver, filter);

        // PAX 2018 버전에 추가된 사항
        initPOSLink();

        paymentProcess();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if(mTask!= null)
            mTask.setActivity(null);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCommandDishBroadcastReceiver);
    }

    public void paymentProcess() {
        // as processTrans is block,so we should put it in an async task
        mTask = new AsyncPosLinkTask(this);
        dataFragment.setData(mTask);
        mTask.execute();
    }

    private void process() {
        BatchRequest batchrequest = new BatchRequest();
        setBatchRequest(batchrequest);
        Log.i(TAG, "BatchRequest.TransType = " + batchrequest.TransType);

        // set the folder whereto read the "comsetting.ini" file
        poslink.appDataFolder = getApplicationContext().getFilesDir().getAbsolutePath();
        poslink.BatchRequest = batchrequest;

        poslink.SetCommSetting(SettingINI.getCommSettingFromFile(poslink.appDataFolder + "/" + SettingINI.FILENAME));

        // ProcessTrans is Blocking call, will return when the transaction is
        // complete.
        ptr = poslink.ProcessTrans();
    }

    private void setBatchRequest(BatchRequest request) {
        // request.TransType =
        // request.ParseTransType(getStringFromEdit(mTransTypeRequestEdit));
        // request.EDCType =
        // request.ParseEDCType(getStringFromEdit(mEDCTypeRequestEdit));
        String strIndicator;

        /* 20120209 QC Bug No30 CombBox Start */
        request.TransType = request.ParseTransType("BATCHCLOSE");
        request.EDCType = request.ParseEDCType("ALL");

        /* 20120209 QC Bug No30 CombBox End */
        request.Timestamp = "";
    }

    private void setBatchResponse(BatchResponse response) {
        /**
         mResultCodeResponseEdit.setText(response.ResultCode);           // 결과코드 (000000 : 성공)
         mResultTxtResponseEdit.setText(response.ResultTxt);
         mCreditCountResponseEdit.setText(response.CreditCount);         // 크레딧카드 배치수
         mCreditAmountResponseEdit.setText(response.CreditAmount);       // 크레딧카드
         mDebitCountResponseEdit.setText(response.DebitCount);
         mDebitAmountResponseEdit.setText(response.DebitAmount);
         mEBTCountResponseEdit.setText(response.EBTCount);
         mEBTAmountResponseEdit.setText(response.EBTAmount);
         mGiftCountResponseEdit.setText(response.GiftCount);
         mGiftAmountResponseEdit.setText(response.GiftAmount);
         mLoyaltyCountResponseEdit.setText(response.LoyaltyCount);
         mLoyaltyAmountResponseEdit.setText(response.LoyaltyAmount);
         mCashCountResponseEdit.setText(response.CashCount);
         mCashAmountResponseEdit.setText(response.CashAmount);
         mTimestampResponseEdit.setText(response.Timestamp);
         mTIDResponseEdit.setText(response.TID);
         mMIDResponseEdit.setText(response.MID);
         mHostTraceNumResponseEdit.setText(response.HostTraceNum);
         mBatchNumResponseEdit.setText(response.BatchNum);
         mAuthCodeResponseEdit.setText(response.AuthCode);
         mHostCodeResponseEdit.setText(response.HostCode);
         mHostResponseResponseEdit.setText(response.HostResponse);
         mMessageResponseEdit.setText(response.Message);
         mExtDataResponseEdit.setText(response.ExtData);
         //saf information
         mSAFTotalCnt.setText(response.SAFTotalCount);
         mSAFTotalAmount.setText(response.SAFTotalAmount);
         mUploadRecords.setText(response.UploadRecords);
         mUploadAmount.setText(response.UploadAmount);
         mFailedRecords.setText(response.FailedRecords);
         mTotalFailed.setText(response.TotalFailedDatabase);
         mDeleteRecords.setText(response.DeleteRecords);
         **/

        JSONObject tempJsonResult = new JSONObject();
        try {
            tempJsonResult.put("ResultCode", response.ResultCode);
            tempJsonResult.put("CreditCount", response.CreditCount);
            tempJsonResult.put("CreditAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.CreditAmount) * 0.01, "2"));
            tempJsonResult.put("DebitCount", response.DebitCount);
            tempJsonResult.put("DebitAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.DebitAmount) * 0.01, "2"));
            tempJsonResult.put("EBTCount", response.EBTCount);
            tempJsonResult.put("EBTAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.EBTAmount) * 0.01, "2"));
            tempJsonResult.put("GiftCount", response.GiftCount);
            tempJsonResult.put("GiftAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.GiftAmount) * 0.01, "2"));
            tempJsonResult.put("LoyaltyCount", response.LoyaltyCount);
            tempJsonResult.put("LoyaltyAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.LoyaltyAmount) * 0.01, "2"));
            tempJsonResult.put("CashCount", response.CashCount);
            tempJsonResult.put("CashAmount", GlobalMemberValues.getStringFormatNumber(
                    GlobalMemberValues.getDoubleAtString(response.CashAmount) * 0.01, "2"));
            tempJsonResult.put("BatchNumber", response.BatchNum);

            // Batch 일시 구하기
            //String batchDate = DateMethodClass.nowDate();
            String batchDate = DateMethodClass.nowMonthGet() + "/" +
                    DateMethodClass.nowDayGet() + "/" +
                    DateMethodClass.nowYearGet() + " " +
                    DateMethodClass.nowHourGet() + ":" +
                    DateMethodClass.nowMinuteGet() + ":" +
                    DateMethodClass.nowSecondGet();

            tempJsonResult.put("BatchDate", batchDate);

            BatchSummary.setBatchSaleResult(tempJsonResult);

            GlobalMemberValues.logWrite("VoidReturnValue", "ResultCode : " + response.ResultCode + "\n");
            GlobalMemberValues.logWrite("VoidReturnValue", "credit count : " + response.CreditCount + "\n");
            GlobalMemberValues.logWrite("VoidReturnValue", "credit amout : " + response.CreditAmount + "\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
    public void getResponseResult(JSONObject paramJson) throws JSONException {
        String resultCode = "";
        String creditCount = "";
        String creditAmount = "";
        if (paramJson.toString().contains("ResultCode")){
            resultCode = paramJson.getString("ResultCode");
        }
        if (paramJson.toString().contains("CreditCount")){
            creditCount = paramJson.getString("CreditCount");
        }
        if (paramJson.toString().contains("CreditAmount")){
            creditAmount = paramJson.getString("CreditAmount");
        }
    }
     **/

    @Override
    public View getCurrentFocus() {
        return super.getCurrentFocus();
    }

    @Override
    public void run() {
        //processTransactions
        process();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    @Override
    public void onSetListener(ProgressDialog dialog, boolean cancelable, boolean enDismiss)
    {
        //don't need to set a listener
    }

    @Override
    public void onTaskCompleted()
    {
        dataFragment.removeData();
        // There will be 2 separate results that you must handle. First is the
        // ProcessTransResult, this will give you the result of the
        // request to call poslink. BatchResponse should only be checked if
        // ProcessTransResultCode.Code == OK.

        // transaction successful
        if (ptr.Code == ProcessTransResultCode.OK) {
            // BatchResponse is the result of the batch transaction to the
            //  server.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BatchResponse response = poslink.BatchResponse;
            Message msg = new Message();
            msg.what = Constant.TRANSACTION_SUCCESSED;
            msg.obj = response;
            mHandler.sendMessage(msg);

            Log.i(TAG, "Transaction sucessed!");
        }
        // transaction timeout
        else if (ptr.Code == ProcessTransResultCode.TimeOut) {
            Message msg = new Message();
            msg.what = Constant.TRANSACTION_TIMEOOUT;
            Bundle b = new Bundle();
            b.putString(Constant.DIALOG_TITLE, String.valueOf(ptr.Code));
            b.putString(Constant.DIALOG_MESSAGE, ptr.Msg);
            msg.setData(b);
            mHandler.sendMessage(msg);

            Log.e(TAG, "Transaction TimeOut! " + String.valueOf(ptr.Code));
            Log.e(TAG, "Transaction TimeOut! " + ptr.Msg);
        }
        // transaction failed
        else {
            Message msg = new Message();
            msg.what = Constant.TRANSACTION_FAILURE;
            Bundle b = new Bundle();
            b.putString(Constant.DIALOG_TITLE, String.valueOf(ptr.Code));
            b.putString(Constant.DIALOG_MESSAGE, ptr.Msg);
            msg.setData(b);
            mHandler.sendMessage(msg);

            Log.e(TAG, "Transaction Error! " + String.valueOf(ptr.Code));
            Log.e(TAG, "Transaction Error! " + ptr.Msg);
        }
    }

    @Override
    public DialogFragment createDialog()
    {
        return ProgressDialogFragment.newInstance(getResources().getString(R.string.batch_process_prompt),false, false);
    }

    @Override
    public void onClick(View v) {

    }

    // PAX 2018 버전에 추가된 사항
    private void initPOSLink() {
        JJJ_PaxPay_POSLinkCreatorWrapper.createSync(getApplicationContext(), new JJJ_PaxPay_AppThreadPool.FinishInMainThreadCallback<PosLink>() {
            @Override
            public void onFinish(PosLink result) {
                poslink = result;
            }
        });
    }
}
