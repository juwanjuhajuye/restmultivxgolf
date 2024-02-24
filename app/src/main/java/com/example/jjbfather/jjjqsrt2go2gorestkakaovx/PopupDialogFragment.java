package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by linhb on 2015-09-16.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;


public class PopupDialogFragment extends DialogFragment {
    public static PopupDialogFragment newInstance(String title, String msg, boolean isError, boolean confirmable) {
        PopupDialogFragment frag = new PopupDialogFragment();
        Bundle args = new Bundle();
        args.putString("popup_title", title);
        args.putString("popup_msg", msg);
        args.putBoolean("popup_isError", isError);
        args.putBoolean("popup_confirmable", confirmable);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder popBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        if(getArguments().getBoolean("popup_isError"))
            popBuilder.setIcon(R.drawable.error);
        popBuilder.setTitle(getArguments().getString("popup_title"));
        popBuilder.setMessage(getArguments().getString("popup_msg"));
        if(getArguments().getBoolean("popup_confirmable"))
            popBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
        return popBuilder.create();
    }
}
