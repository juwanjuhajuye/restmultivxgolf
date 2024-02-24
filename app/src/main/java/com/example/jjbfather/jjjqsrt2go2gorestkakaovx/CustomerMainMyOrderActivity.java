package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.io.File;
import java.util.ArrayList;

public class CustomerMainMyOrderActivity extends Activity {
   ListView mainCustomer_SaleCartList;
   TextView mainCustomer_CommonGratuityValue,mainCustomer_SubTotalValue,mainCustomer_TaxValue,mainCustomer_TotalValue,customer_main_tablename;
   SaleCartAdapter saleCartAdapter;

   String str_tax,str_gratuity,str_subtotal,str_total;

   Button customer_main_my_order;

   private File outputFile; //파일명까지 포함한 경로
   private File path;//디렉토리경로

   public static LinearLayout customer_myorder_full_image_ln;
   public static ImageView customer_myorder_full_image;
   public static TextView customer_myorder_full_salon_name;
   public ArrayList<TemporarySaleCart> mGeneralArrayList = new ArrayList<TemporarySaleCart>();

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_customer_myorder);

      Intent intent = getIntent();
      str_tax = intent.getStringExtra("tax");
      str_gratuity = intent.getStringExtra("gratuity");
      str_subtotal = intent.getStringExtra("subtotal");
      str_total = intent.getStringExtra("total");

      GlobalMemberValues.is_myorderOpen = true;

      setContents();
   }

   public void setContents(){
      mainCustomer_SaleCartList = findViewById(R.id.mainCustomer_SaleCartList);
      mainCustomer_CommonGratuityValue = findViewById(R.id.mainCustomer_CommonGratuityValue);
      mainCustomer_SubTotalValue = findViewById(R.id.mainCustomer_SubTotalValue);
      mainCustomer_TaxValue = findViewById(R.id.mainCustomer_TaxValue);
      mainCustomer_TotalValue = findViewById(R.id.mainCustomer_TotalValue);
      customer_main_tablename = findViewById(R.id.customer_main_tablename);

      mGeneralArrayList.addAll(MainMiddleService.mGeneralArrayList);

      saleCartAdapter = new SaleCartAdapter(this,R.layout.customer_my_order_list_row,mGeneralArrayList);
      mainCustomer_SaleCartList.setAdapter(saleCartAdapter);

      mainCustomer_TaxValue.setText(str_tax);
      mainCustomer_CommonGratuityValue.setText(str_gratuity);
      mainCustomer_SubTotalValue.setText(str_subtotal);
      mainCustomer_TotalValue.setText(str_total);

      customer_main_my_order = findViewById(R.id.customer_main_my_order);
      customer_main_my_order.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            TableSaleMain.goSalesMain();
            GlobalMemberValues.is_myorderOpen = false;
            finish();
         }
      });

      GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN = (LinearLayout) findViewById(R.id.customer_myorder_ad_ln);

      GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_IMAGEVIEW = (ImageView) findViewById(R.id.customer_myorder_iv_imageview);
      GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW = (VideoView) findViewById(R.id.customer_myorder_iv_videoview);

      GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN.setVisibility(View.GONE);

      GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setOnErrorListener(new MediaPlayer.OnErrorListener() {
         @Override
         public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.GONE);
            return true;
         }
      });

      // 광고 이미지
      String adType = "0";
      String adImage = "";
      String dualdpadtype = "L";

      adType = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdptype from salon_storegeneral ");
      if (GlobalMemberValues.isStrEmpty(adType)) {
         adType = "0";
      }

      path = GlobalMemberValues.ADFILELOCALPATH;

      if (MainActivity.mDbInit != null) {
         dualdpadtype = MainActivity.mDbInit.dbExecuteReadReturnString(" select dualdpadtype from salon_storestationsettings_system ");
         if (dualdpadtype.equals("L")) {
            if (adType == "0" || adType.equals("0")) {
               outputFile = new File(path, "ad.gif"); //파일명까지 포함함 경로의 File 객체 생성
            } else {
               outputFile = new File(path, "ad.mp4"); //파일명까지 포함함 경로의 File 객체 생성
            }
            adImage = outputFile.getPath();
            GlobalMemberValues.logWrite("adimagelogjjj2", "여기실행됨1... adImage : " + adImage + "\n");
            if (!new File(adImage).exists()) {
               adImage = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdpimg from salon_storegeneral ");
               GlobalMemberValues.logWrite("adimagelogjjj2", "여기실행됨2... adImage : " + adImage + "\n");
            }
         } else {
            adImage = MainActivity.mDbInit.dbExecuteReadReturnString(" select posdualdpimg from salon_storegeneral ");
            GlobalMemberValues.logWrite("adimagelogjjj2", "여기실행됨3... adImage : " + adImage + "\n");
         }
      }
      GlobalMemberValues.logWrite("adimagelogjjj", "adType : " + adType + "\n");
      GlobalMemberValues.logWrite("adimagelogjjj", "adImage : " + adImage + "\n");
      if (!GlobalMemberValues.isStrEmpty(adImage)) {
         GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN.setVisibility(View.VISIBLE);

         if (adType == "0" || adType.equals("0")) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_IMAGEVIEW.setVisibility(View.VISIBLE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.GONE);

            Glide
                    .with(getApplicationContext())
                    .load(adImage) // 이미지 url
//                .override(200,200)    // 이미지 크기
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)  // 리소스 재사용.
                    .into(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_IMAGEVIEW);
         } else {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_IMAGEVIEW.setVisibility(View.GONE);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.VISIBLE);

            if (dualdpadtype == "W" || dualdpadtype.equals("W")) {
               // 가장 먼저 인터넷 체크를 한다.
               if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                  if (!GlobalMemberValues.isOnline2().equals("00")) {
                     // 인터넷 연결이 안되었을 경우
                     GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.GONE);
                  }
               } else {
                  if (!GlobalMemberValues.isOnline2().equals("00")) {
                     // 인터넷 연결이 안되었을 경우
                     GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.GONE);
                  }
               }
            } else {
               GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVisibility(View.VISIBLE);
            }

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setOnPreparedListener(onPrepared);

            MediaController mediaController = new MediaController(MainActivity.mContext);
            mediaController.setAnchorView(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW);

            Uri video = Uri.parse(adImage);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setMediaController(mediaController);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setVideoURI(video);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.requestFocus();
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.start();

            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {
                  mp.setLooping(true);
                  mediaController.setVisibility(View.INVISIBLE);

               }
            });
         }
      } else {
         if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN != null) {
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_ADLN.setVisibility(View.GONE);
         }
      }


      //
      customer_myorder_full_image_ln = findViewById(R.id.customer_myorder_full_image_ln);
      customer_myorder_full_image = findViewById(R.id.customer_myorder_full_image);
      customer_myorder_full_salon_name = findViewById(R.id.customer_myorder_full_salon_name);
      File path = GlobalMemberValues.ADFILELOCALPATH;
      File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
      String logourl = isFile.getPath();
      if (new File(logourl).exists()) {
         Matrix matrix1 = new Matrix() ;
         matrix1.postScale(2.0f, 2.0f);
         customer_myorder_full_image.setImageMatrix(matrix1) ;
         customer_myorder_full_image.setImageBitmap(BitmapFactory.decodeFile(isFile.getAbsolutePath()));
      } else {
         customer_myorder_full_salon_name.setText(GlobalMemberValues.SALON_NAME);
      }

   }
   private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener =
           new MediaPlayer.OnVideoSizeChangedListener() {
              public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                         ViewGroup.LayoutParams.MATCH_PARENT);
                 GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setLayoutParams(lp);
              }
           };

   private MediaPlayer.OnPreparedListener onPrepared = new MediaPlayer.OnPreparedListener() {
      public void onPrepared(MediaPlayer mp) {
         mp.setOnVideoSizeChangedListener(onVideoSizeChangedListener);

         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.MATCH_PARENT);
         GlobalMemberValues.GLOBAL_LAYOUTMEMBER_CUSTOMER_MYORDER_VIDEOVIEW.setLayoutParams(lp);
      }
   };

   @Override
   protected void onDestroy() {
      super.onDestroy();
      GlobalMemberValues.is_myorderOpen = false;
   }
}
