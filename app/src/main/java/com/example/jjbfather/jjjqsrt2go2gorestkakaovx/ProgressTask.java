package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressTask extends AsyncTask<Void, Void, Void> {
   private ProgressBar progressBar;

   ProgressTask(ProgressBar progressBar) {
      this.progressBar = progressBar;
   }

   @Override
   protected void onPreExecute() {
      progressBar.setVisibility(View.VISIBLE); // Task 시작 전 ProgressBar를 보이게 합니다.
   }

   @Override
   protected Void doInBackground(Void... args) {
      // 긴 작업을 여기서 수행합니다.
      // 이 부분은 메인 스레드가 아닌 별도의 스레드에서 동작하기 때문에 UI를 직접 조작할 수 없습니다.

      return null;
   }

   @Override
   protected void onPostExecute(Void result) {
      progressBar.setVisibility(View.GONE); // Task 완료 후 ProgressBar를 숨깁니다.
   }
}