package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2016-06-22.
 */
public class ImageDownload {
    private static final String TAG = "ImageDownload";

    public void getDownloadCloudFiles (String paramType) {

        switch (paramType) {
            /** salon_storeservice_main 다운로드 *************************************************/
            case "service" : {
                ImageDownload_service imageDownService = new ImageDownload_service();
                imageDownService.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (imageDownService.mFlag) {
                        GlobalMemberValues.logWrite(TAG, "클라우드에서 이미지 다운로드 모두 성공\n");
                        //MainActivity.mActivity.recreate();
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite(TAG, "Thread Error : " + e.getMessage());
                }
                break;
            }
            /**************************************************************************************/

        }

        // 이미지 다운로드 후 5초(GlobalMemberValues.WAITSECAFTERDOWNLOAD)정도 기다린다.
        // (데이터 다운로드가 완료되도 이미지 다운로드는 시간이 좀 더 걸린다...)
        try {
            Thread.sleep(GlobalMemberValues.API_THREAD_TIME * GlobalMemberValues.WAITSECAFTERDOWNLOAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
