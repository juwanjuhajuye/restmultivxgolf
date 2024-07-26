package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.AlertDialog;
import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale.TableSaleMain;

import java.io.File;

public class PaxPresentation extends Presentation {
    private TextView presentation_title1, presentation_title2, presentation_title3;

    private File outputFile; //파일명까지 포함한 경로
    private File path;//디렉토리경로

    public static boolean is_logo_full = false;
    public static LinearLayout presentation_full_image_ln;
    public static ImageView presentation_full_image;
    public static TextView presentation_full_salon_name;

    public static Context presentation_context;

    public PaxPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        presentation_context = outerContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pax_presentation);
        setContent();
    }

    private void setContent() {//jihun park presentation listview
        // 07.18.2022 - add pay for cash, card
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT = (RelativeLayout) findViewById(R.id.prnt_addpayRt);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYRT.setVisibility(View.GONE);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRNT_PAYMENTADDPAYTV = (TextView)findViewById(R.id.prnt_addpayTv);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_LISTVIEW = (ListView)findViewById(R.id.presentation_list);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_SUBTOTALTEXTVIEW = (TextView)findViewById(R.id.presentation_tv_subtotal);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TAXTEXTVIEW = (TextView)findViewById(R.id.presentation_tv_tax);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_TOTALTEXTVIEW = (TextView)findViewById(R.id.presentation_tv_total);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPLN = (RelativeLayout) findViewById(R.id.presentation_tv_delivery_togo_fee_ln);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPTEXTTV = (TextView) findViewById(R.id.presentation_tv_delivery_togo_fee_type);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_MAINDELIVERYPICKUPPRICETV = (TextView) findViewById(R.id.presentation_tv_delivery_togo_fee_price);

        presentation_title1 = (TextView)findViewById(R.id.presentation_title1);
        presentation_title2 = (TextView)findViewById(R.id.presentation_title2);
        presentation_title3 = (TextView)findViewById(R.id.presentation_title3);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN = (LinearLayout) findViewById(R.id.presention_ad_ln);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_IMAGEVIEW = (ImageView) findViewById(R.id.presentation_iv_imageview);
        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW = (VideoView) findViewById(R.id.presentation_iv_videoview);

        presentation_title1.setTextSize(GlobalMemberValues.globalAddFontSize() + 24 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        presentation_title2.setTextSize(GlobalMemberValues.globalAddFontSize() + 24 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());
        presentation_title3.setTextSize(GlobalMemberValues.globalAddFontSize() + 24 * GlobalMemberValues.getGlobalFontSize() + GlobalMemberValues.globalAddFontSizeForPAX());

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN.setVisibility(View.GONE);

        GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.GONE);
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
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN.setVisibility(View.VISIBLE);

            if (adType == "0" || adType.equals("0")) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_IMAGEVIEW.setVisibility(View.VISIBLE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.GONE);

                Glide
                        .with(getContext())
                        .load(adImage) // 이미지 url
//                .override(200,200)    // 이미지 크기
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)  // 리소스 재사용.
                        .into(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_IMAGEVIEW);
            } else {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_IMAGEVIEW.setVisibility(View.GONE);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.VISIBLE);

                if (dualdpadtype == "W" || dualdpadtype.equals("W")) {
                    // 가장 먼저 인터넷 체크를 한다.
                    if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
                        if (!GlobalMemberValues.isOnline2().equals("00")) {
                            // 인터넷 연결이 안되었을 경우
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.GONE);
                        }
                    } else {
                        if (!GlobalMemberValues.isOnline2().equals("00")) {
                            // 인터넷 연결이 안되었을 경우
                            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.GONE);
                        }
                    }
                } else {
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVisibility(View.VISIBLE);
                }

                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setOnPreparedListener(onPrepared);

                MediaController mediaController = new MediaController(MainActivity.mContext);
                mediaController.setAnchorView(GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW);

                Uri video = Uri.parse(adImage);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setMediaController(mediaController);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setVideoURI(video);
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.requestFocus();
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.start();

                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        mediaController.setVisibility(View.INVISIBLE);

                    }
                });
            }
        } else {
            if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN != null) {
                GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_ADLN.setVisibility(View.GONE);
            }
        }


        //
        presentation_full_image_ln = findViewById(R.id.presentation_full_image_ln);
        presentation_full_image = findViewById(R.id.presentation_full_image);
        presentation_full_salon_name = findViewById(R.id.presentation_full_salon_name);
        File path = GlobalMemberValues.ADFILELOCALPATH;
        File isFile = new File(path, "receiptlogoimg.png"); //파일명까지 포함함 경로의 File 객체 생성
        String logourl = isFile.getPath();
        if (new File(logourl).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(isFile.getAbsolutePath());
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            Matrix matrix1 = new Matrix() ;
            matrix1.postScale(3.0f, 3.0f);

            bitmap = Bitmap.createBitmap(bitmap, 0,0,w,h,matrix1,false);
//            presentation_full_image.setImageMatrix(matrix1) ;
//            presentation_full_image.setImageBitmap(BitmapFactory.decodeFile(isFile.getAbsolutePath()));
            presentation_full_image.setImageBitmap(bitmap);
        } else {
            presentation_full_salon_name.setText(GlobalMemberValues.SALON_NAME);
        }

    }

    public static void setLogo(){
        presentation_full_image_ln.setVisibility(View.VISIBLE);
    }
    public static void unSetLogo(){
        presentation_full_image_ln.setVisibility(View.GONE);
    }

    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener =
            new MediaPlayer.OnVideoSizeChangedListener() {
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setLayoutParams(lp);
                }
            };

    private MediaPlayer.OnPreparedListener onPrepared = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            mp.setOnVideoSizeChangedListener(onVideoSizeChangedListener);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_PRESENTATION_VIDEOVIEW.setLayoutParams(lp);
        }
    };
}
