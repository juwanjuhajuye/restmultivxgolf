package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class WaitingView extends Activity {
   ListView waitingList;
   ImageButton btn_notify, btn_sit, btn_walk, btn_edit, btn_Close;
   Button btn_add;
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_waiting);
      setContent();
   }

   private void setContent(){
      waitingList = findViewById(R.id.waitingview_list);
      waitingList.setAdapter(new WaitingListAdapter());

      btn_add = findViewById(R.id.waitingview_btn_add);
      btn_add.setOnClickListener(onClickListener);

      btn_notify = findViewById(R.id.waitingview_btn_notify);
      btn_sit = findViewById(R.id.waitingview_btn_sit);
      btn_walk = findViewById(R.id.waitingview_btn_walk);
      btn_edit = findViewById(R.id.waitingview_btn_edit);

      btn_Close = findViewById(R.id.waitingview_btn_close);
   }

   View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         switch (v.getId()){
            case R.id.waitingview_btn_add:
               Intent waiting_add_edit_popup_intent = new Intent(MainActivity.mContext.getApplicationContext(), Waiting_Add_Edit_Popup.class);
               startActivity(waiting_add_edit_popup_intent);
               break;
         }
      }
   };
}
