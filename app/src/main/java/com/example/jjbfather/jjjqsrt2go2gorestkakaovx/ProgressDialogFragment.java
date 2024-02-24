package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by linhb on 2015-09-16.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment {

    public interface OnSetListener{
        void onSetListener(ProgressDialog dialog, boolean cancelable, boolean enDismiss);
    }

    public static ProgressDialogFragment newInstance(String msg, boolean cancelable, boolean enDismiss) {
        ProgressDialogFragment frag = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("progress_msg", msg);
        args.putBoolean("progress_cancelable", cancelable);
        args.putBoolean("progress_enDismiss", enDismiss);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        boolean cancelable = getArguments().getBoolean("progress_cancelable");
        boolean enDismiss = getArguments().getBoolean("progress_enDismiss");
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getArguments().getString("progress_msg"));
        dialog.setIndeterminate(true);
        dialog.setCancelable(cancelable);
        if(!enDismiss)
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return (i == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0);
                }
            });
        dialog.setCanceledOnTouchOutside(false);
        OnSetListener listener = (OnSetListener)getActivity();
        listener.onSetListener(dialog, cancelable, enDismiss);
        return dialog;
    }
}
