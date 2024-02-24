package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
//import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class JJJ_RemoveCard extends Activity {
    final String TAG = "JJJ_RemoveCardLog";

    Activity mActivity;
    Context context;

    private LinearLayout parentLn;
    private Button closeBtn;
    private TextView msgTv;

    Intent mIntent;

    // 효과음
    SoundPool sound;
    int soundId;
    int streamId;

    Ringtone ringtone;

    // 진동
//    Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      // 타이틀바 안보이게
        setContentView(R.layout.activity_jjj__remove_card);
        this.setFinishOnTouchOutside(false);

        mActivity = this;
        context = this;

        parentLn = (LinearLayout)findViewById(R.id.realtimewebordersLinearLayout);

        mIntent = getIntent();
        if (mIntent != null) {
            /** 부모로부터 받은 객체 및 값 저장 ***********************************************************/
            // 부모로부터 받은 Extra 값
            //mPaymentBalanceToPay = mIntent.getStringExtra("paymentBalanceToPay");
            /*******************************************************************************************/
        } else {
            finish();
        }

        setContents();
    }

    public void setContents() {
        // 효과음 재생
        sound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);// maxStreams, streamType, srcQuality
        soundId = sound.load(this, R.raw.siren, 1);
        streamId = sound.play(soundId, 1.0F, 1.0F,  1,  -1,  2.0F);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        ringtone.play();

        // 진동
//        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(new long[]{100,1000,100,500,100,500,100,1000}, 0);

        // Edit 텍스트 이외의 화면터치시 키보드 사라지게.. --------------------------------------------------------
        parentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parentLn.getWindowToken(), 0);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        /** 객체 생성 및 클릭 리스너 연결 **************************************************************************/
        // 닫기 버튼 생성 및 버튼 클릭 리스너 연결
        closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setTextSize(GlobalMemberValues.globalAddFontSize() + closeBtn.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        closeBtn.setOnClickListener(btnClickLsn);
        /***********************************************************************************************************/

        msgTv = (TextView) findViewById(R.id.msgTv);
        msgTv.setTextSize(GlobalMemberValues.globalAddFontSize() + msgTv.getTextSize() * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        switch (GlobalMemberValues.CARD_PROCESSING_STEP) {
            case 0 : {
                //msgTv.setText("Invalid or Unusable card");
//                msgTv.setText("REMOVE YOUR CARD.\nPLEASE SUBMIT SIGNATURE AGAIN");
                msgTv.setText("PLEASE REMOVE YOUR\nCARD AND TRY AGAIN");

                break;
            }
            case 1 : {
                msgTv.setText("Please, remove your card");

                break;
            }
            case 99 : {
                msgTv.setText("YOU HAVE OPEN ORDERS.\n\nBEFORE YOU CASH OUT,\nPLEASE CLOSE ALL ORDERS.");
            }
        }

    }

    View.OnClickListener btnClickLsn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBtn : {
                    ringtone.stop();
                    sound.stop(streamId);
//                    vibrator.cancel();
                    mActivity.finish();
                    break;
                }
            }
        }
    };
}
