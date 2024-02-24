package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.util.Log;

/**
 * Created by linhb on 2015-09-22.
 */
abstract class BaseFragmentActivity extends FragmentActivity implements Runnable {

    protected DataFragment dataFragment;
    protected AsyncPosLinkTask mTask;

    abstract void onTaskCompleted();

    abstract DialogFragment createDialog();

    @Override
    public void run()
    {

    }

    public void setTask()
    {
        FragmentManager fm = getSupportFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag(this.getLocalClassName()+"Data");
        Log.i("PaxPaymentClass", "클래스이름 : " + this.getLocalClassName()+"Data" + "\n");
        // create the fragment and data the first time
        Log.i("PaxPaymentClass", "여기까지..1" + "\n");
        if (dataFragment == null)
        {
            // add the fragment
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, this.getLocalClassName()+"Data").commit();
        }
        Log.i("PaxPaymentClass", "여기까지..2" + "\n");
        mTask = (AsyncPosLinkTask)dataFragment.getData();
        if (mTask != null)
        {
            mTask.setActivity(this);
        }
        Log.i("PaxPaymentClass", "여기까지..3" + "\n");

    }
}
