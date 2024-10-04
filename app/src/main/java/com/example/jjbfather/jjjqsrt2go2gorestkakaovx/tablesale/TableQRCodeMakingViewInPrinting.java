package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.tablesale;
//aa
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.API_tableqrcodeset_tocloud;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.GlobalMemberValues;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainActivity;

public class TableQRCodeMakingViewInPrinting {


    public static LinearLayout makingLinearLayoutForTableQRCode(final String paramTableIdx) {
        if (!GlobalMemberValues.isStrEmpty(paramTableIdx)) {
            return  null;
        }

        String tableIdx = GlobalMemberValues.getReplaceText(paramTableIdx, "T", "");
        String tableCode = GlobalMemberValues.makeTOrderCodeForPOSOrder();

        // Table Code 값을 클라우드에 전송해서 저장 ------------------------------------
        String returnApiValue = "00";

        String upCloudDatas = "tableidx=" + tableIdx + "&tablecode=" + tableCode;

        API_tableqrcodeset_tocloud apiIns = new API_tableqrcodeset_tocloud(upCloudDatas);
        apiIns.execute(null, null, null);
        try {
            Thread.sleep(2000);
            if (apiIns.mFlag) {
                returnApiValue = apiIns.mReturnValue;
            }
        } catch (InterruptedException e) {
            //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
        }
        // -------------------------------------------------------------------------

        if (returnApiValue.equals("00")) {
            final LinearLayout.LayoutParams matchParentParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 32);
            matchParentParams.setMargins(0, 0, 0, 0);

            final LinearLayout printingLn = new LinearLayout(MainActivity.mContext);
            printingLn.setLayoutParams(new LinearLayout.LayoutParams(GlobalMemberValues.PRINTINGPAPERSIZE_ONCLOVER, LinearLayout.LayoutParams.MATCH_PARENT));
            printingLn.setOrientation(LinearLayout.VERTICAL);
            printingLn.setBackgroundColor(Color.parseColor("#ffffff"));

            Thread thread = new Thread() {
                @Override
                public void run() {
                    // QR code 데이터 생성
                    String paramValue = "NZ" + GlobalMemberValues.STORE_INDEX + "JJJWHY" + tableIdx + "JJJWHY" + tableCode + "ONT";

                    String tempAppStr = GlobalMemberValues.CLOUD_SERVER_URL_BASIC;
                    String qrcodeStr = GlobalMemberValues.getReplaceText(tempAppStr, GlobalMemberValues.CLOUD_HOST, GlobalMemberValues.MOBILE_HOST);
                    qrcodeStr += "intro/?n=" + paramValue;
                    GlobalMemberValues.logWrite("qrcodejjjlog", "qrcode txt : " + qrcodeStr + "\n");


                    // 테이블명 구하기
                    String tablename = MainActivity.mDbInit.dbExecuteReadReturnString(
                            " select tablename from salon_store_restaurant_table where idx = '" + paramTableIdx + "' "
                    );


                    // 테이블명 -----------------------------------------------------------------------------------------------------
                    TextView batchtitleTv = new TextView(MainActivity.mContext);
                    batchtitleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
                    batchtitleTv.setGravity(Gravity.CENTER);
                    batchtitleTv.setText(tablename);

                    batchtitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER + 10);
                    GlobalMemberValues.setTextStyleOnClover(batchtitleTv);
                    printingLn.addView(batchtitleTv);
                    // --------------------------------------------------------------------------------------------------------------------

                    // 두줄 점선 -----------------------------------------------------------------------------------------------------------------------
                    //printingLn.addView(GlobalMemberValues.getDoubleDotLineViewForClover(MainActivity.mContext));
                    // ---------------------------------------------------------------------------------------------------------------------------------
                    // 한줄 점선 -----------------------------------------------------------------------------------------------------------------------
                    printingLn.addView(GlobalMemberValues.getDotLineViewForClover(MainActivity.mContext));
                    // ---------------------------------------------------------------------------------------------------------------------------------

                    // qr code -----------------------------------------------------------------------------------------
                    LinearLayout tablepayLn = new LinearLayout(MainActivity.mContext);
                    tablepayLn.setLayoutParams(matchParentParams);
                    tablepayLn.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tablepayTv = new TextView(MainActivity.mContext);
                    tablepayTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    tablepayTv.setText("Scan the QR code below to order on the table");
                    tablepayTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (GlobalMemberValues.PRINTINGFONTSIZE_ONCLOVER - 5));
                    GlobalMemberValues.setTextStyleOnClover(tablepayTv);
                    tablepayTv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    tablepayLn.addView(tablepayTv);

                    printingLn.addView(tablepayLn);


                    ImageView qrImage = new ImageView(MainActivity.mContext);
                    qrImage.setImageBitmap(GlobalMemberValues.generateRQCode(qrcodeStr));
                    printingLn.addView(qrImage);
                    // ------------------------------------------------------------------------------------------------


                }

            };
            thread.start();

            try {
                thread.join();
            } catch(InterruptedException e) {}

            return printingLn;
        } else {
            return null;
        }
    }
}
