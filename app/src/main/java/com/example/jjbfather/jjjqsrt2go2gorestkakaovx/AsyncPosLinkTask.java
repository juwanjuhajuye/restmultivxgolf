package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;
import androidx.fragment.app.DialogFragment;
import android.util.Log;

/**
 * Created by linhb on 2015-09-22.
 */
public class AsyncPosLinkTask extends AsyncTask<Void, Void, Void>
{
    private BaseFragmentActivity activity;

    private boolean isCompleted;

    private DialogFragment mLoadingDialog;

    public AsyncPosLinkTask(BaseFragmentActivity activity)
    {
        this.activity = activity;
        Log.i("PaxPaymentClass", "AsyncPosLinkTask 여기옴.." + "\n");
    }

    @Override
    protected void onPreExecute()
    {
        displayDialog();
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        if (activity != null) {
            activity.run();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused)
    {
        isCompleted = true;
        notifyActivityTaskCompleted();
        if (mLoadingDialog != null)
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e){

            }

    }

    /**
     * @param activity the required activity
     */
    public void setActivity(BaseFragmentActivity activity)
    {
        if (activity == null) {
            mLoadingDialog.dismiss();
        }

        this.activity = activity;

        if (activity != null && !isCompleted)
        {
            displayDialog();
        }

        if (isCompleted)
        {
            notifyActivityTaskCompleted();
        }
    }

    private void notifyActivityTaskCompleted()
    {
        if (null != activity) {

            PaymentCreditCard.mProgress_handler.sendEmptyMessage(0);
            activity.onTaskCompleted();
            cancel(true);
        }
    }

    private void displayDialog()
    {
        try {
            mLoadingDialog = activity.createDialog();
            mLoadingDialog.show(activity.getSupportFragmentManager(), activity.toString());
//            activity.getSupportFragmentManager().beginTransaction().commitAllowingStateLoss();
            activity.getSupportFragmentManager().beginTransaction().commitNow();
        } catch (Exception e){

        }

    }

}
