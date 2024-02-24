package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

/**
 * Created by JJBFATHER on 2015-12-08.
 */
public class ProgressBarDialog extends AlertDialog {
    private TextView txt_title, txt_persen;
    private Context c;
    public static ProgressBar mProgressBar;
    private int progressStatus = 0;
    public static Handler mProgressBar_handler = new Handler();

    private String str_title;

    public ProgressDialog ldProDial;

    public ProgressBarDialog(Context context, String title) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        c = context;
        str_title = title;
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
    @Override
    public void show() {
        super.show();
        setContentView(R.layout.dialog_custombar);

        mProgressBar = findViewById(R.id.progressBar1);
        txt_title = findViewById(R.id.progressBarTitle);
        txt_persen = findViewById(R.id.progressBarPer);

        txt_title.setText(str_title);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
//                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
//
//                    // Update the progress bar
//                    mProgressBar_handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgressBar.setProgress(progressStatus);
//                            // Show the progress on TextView
//
//                        }
//                    });
                }
            }
        }).start();
    }
    @Override
    public void dismiss() {
        super.dismiss();
        if (ldProDial != null) {
            if (ldProDial.isShowing()) {
                ldProDial.dismiss();
            }
        }
    }

    public void setProgress(int a){
        mProgressBar.setProgress(a);
        txt_persen.setText(a + "%");

        if (a == 100){
            MainActivity.handler_loading_popup.post(new Runnable() {
                @Override
                public void run() {
                    ldProDial = ProgressDialog.show(getContext(), GlobalMemberValues.ANDROIDPOSNAME, "Loading Screens..", true, false);
                    ldProDial.show();
                }
            });

        }
    }
}

