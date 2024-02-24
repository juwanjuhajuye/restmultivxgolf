package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class AppPermissionCheckClass extends Activity {

    public static Activity activity;
    public static Context context;

    public static String returnPermissionValue = "N";
    public static String returnPermissionBringValue = "";

    public static int mTempPermissionThreadFlag = 1;        // 0 : 완료           1 : 미완료

    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check_class);

        closeBtn = (Button)findViewById(R.id.closeBtn);

    }

    public static void checkPermission(Activity paramActivity, Context paramContext, final String paramPermissionType) {
        activity = paramActivity;
        context = paramContext;

        returnPermissionBringValue = "";

        switch (paramPermissionType) {
            // 전화걸기 관련
            case Manifest.permission.CALL_PHONE : {
                Log.i("PermissionTest", "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT + "\n");
                Log.i("PermissionTest", "Build.VERSION_CODES.M : " + Build.VERSION_CODES.M + "\n");

                        /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.
                        *  int를 쓴 이유? 안드로이드는 C기반이기 때문에, Boolean 이 잘 안쓰인다.
                        */
                    int permissionResult = context.checkSelfPermission(Manifest.permission.CALL_PHONE);

                            /* CALL_PHONE의 권한이 없을 때 */
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                                /* 사용자가 CALL_PHONE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                                * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                                */
                        if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                    })

                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(activity, "전화걸기 기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                            Log.i("PermissionTest", "전화걸기 권한부여 : NO\n");
                                            returnPermissionValue = "N";
                                            mTempPermissionThreadFlag = 0;
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            // CALL_PHONE 권한을 Android OS 에 요청한다.
                            activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    } else {   /* CALL_PHONE의 권한이 있을 때 */
                        Log.i("PermissionTest", "전화걸기 권한부여 : YES\n");
                        Log.i("PermissionTest", "전화걸기 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "전화걸기 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                        returnPermissionValue = "Y";
                        mTempPermissionThreadFlag = 0;
                    }
                } else {    /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                    Log.i("PermissionTest", "마시멜로우 이하 버전이라 자동으로 전화걸기 권한부여 : YES\n");
                    Log.i("PermissionTest", "전화걸기 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                    Log.i("PermissionTest", "전화걸기 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) : " +
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                    returnPermissionValue = "Y";
                    mTempPermissionThreadFlag = 0;
                }

                break;
            }

            // 파일읽기 관련
            case Manifest.permission.READ_EXTERNAL_STORAGE : {
                Log.i("PermissionTest", "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT + "\n");
                Log.i("PermissionTest", "Build.VERSION_CODES.M : " + Build.VERSION_CODES.M + "\n");

                        /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.
                            *  int를 쓴 이유? 안드로이드는 C기반이기 때문에, Boolean 이 잘 안쓰인다.
                            */
                    int permissionResult = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                            /* READ_EXTERNAL_STORAGE 의 권한이 없을 때 */
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                                /* 사용자가 READ_EXTERNAL_STORAGE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                                * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                                */
                        if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("서비스 메뉴 이미비 보기기능을 사용하기 위해서는 단말기의 \"파일읽기\" 권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2000);
                                            }

                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(activity, "파일읽기 기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                            Log.i("PermissionTest", "파일읽기 부여 : NO\n");

                                            returnPermissionValue = "N";
                                            mTempPermissionThreadFlag = 0;
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            // READ_EXTERNAL_STORAGE 권한을 Android OS 에 요청한다.
                            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2000);
                        }
                    } else {   /* READ_EXTERNAL_STORAGE 의 권한이 있을 때 */
                        Log.i("PermissionTest", "읽기권한 부여 : YES\n");
                        Log.i("PermissionTest", "읽기권한 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "읽기권한 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                        returnPermissionValue = "Y";
                        mTempPermissionThreadFlag = 0;
                    }
                } else {    /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                    Log.i("PermissionTest", "마시멜로우 이하 버전이라 자동으로 읽기권한 부여 : YES\n");
                    Log.i("PermissionTest", "읽기권한 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                    Log.i("PermissionTest", "읽기권한 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) : " +
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                    returnPermissionValue = "Y";
                    mTempPermissionThreadFlag = 0;
                }
                break;
            }

            // 파일쓰기 관련
            case Manifest.permission.WRITE_EXTERNAL_STORAGE : {
                Log.i("PermissionTest", "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT + "\n");
                Log.i("PermissionTest", "Build.VERSION_CODES.M : " + Build.VERSION_CODES.M + "\n");

                        /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.
                            *  int를 쓴 이유? 안드로이드는 C기반이기 때문에, Boolean 이 잘 안쓰인다.
                            */
                    int permissionResult = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                            /* WRITE_EXTERNAL_STORAGE 의 권한이 없을 때 */
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                                /* 사용자가 READ_EXTERNAL_STORAGE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                                * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                                */
                        if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("서비스 메뉴 이미비 보기기능을 사용하기 위해서는 단말기의 \"파일읽기\" 권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                                            }

                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(activity, "파일읽기 기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                            Log.i("PermissionTest", "파일읽기 부여 : NO\n");

                                            returnPermissionValue = "N";
                                            mTempPermissionThreadFlag = 0;
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            // WRITE_EXTERNAL_STORAGE 권한을 Android OS 에 요청한다.
                            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                        }
                    } else {   /* WRITE_EXTERNAL_STORAGE 의 권한이 있을 때 */
                        Log.i("PermissionTest", "쓰기권한 부여 : YES\n");
                        Log.i("PermissionTest", "쓰기권한 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "쓰기권한 ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) + "\n");

                        returnPermissionValue = "Y";
                        mTempPermissionThreadFlag = 0;
                    }
                } else {    /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                    Log.i("PermissionTest", "마시멜로우 이하 버전이라 자동으로 쓰기권한 부여 : YES\n");
                    Log.i("PermissionTest", "쓰기권한 PackageManager.PERMISSION_GRANTED : " + PackageManager.PERMISSION_GRANTED + "\n");
                    Log.i("PermissionTest", "쓰기권한 ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) : " +
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) + "\n");

                    returnPermissionValue = "Y";
                    mTempPermissionThreadFlag = 0;
                }
                break;
            }
        }

    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTempPermissionThreadFlag == 0) {
                returnPermissionBringValue = returnPermissionValue;
            }

        }
    };

    /**
     * 사용자가 권한을 허용했는지 거부했는지 체크
     * @param requestCode   1000번
     * @param permissions   개발자가 요청한 권한들
     * @param grantResults  권한에 대한 응답들
     *                    permissions와 grantResults는 인덱스 별로 매칭된다.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull final int[] grantResults) {
        Log.i("PermissionTest", "requestCode : " + requestCode + "\n");

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 처리가 오래걸리는 부분 실행 --------------------------------------------------
                switch (requestCode) {

                    // 전화걸기 관련
                    case 1000 : {
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/

                        Log.i("PermissionTest", "전화걸기 grantResults.length (rq) : " + grantResults.length + "\n");
                        Log.i("PermissionTest", "전화걸기 grantResults[0] (rq) : " + grantResults[0] + "\n");
                        Log.i("PermissionTest", "전화걸기 PackageManager.PERMISSION_GRANTED (rq) : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "전화걸기 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) (rq) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                            /**
                             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                             //startActivity(intent);
                             Log.i("PermissionTest", "전화걸기 부여 (rq) : YES\n");
                             }
                             **/
                            Log.i("PermissionTest", "전화걸기 부여 (rq) : YES\n");

                            returnPermissionValue = "Y";
                            mTempPermissionThreadFlag = 0;
                        }
                        else {
                            Toast.makeText(activity, "권한 요청을 거부했습니다.(rq)", Toast.LENGTH_SHORT).show();
                            Log.i("PermissionTest", "전화걸기 부여 (rq) : NO\n");

                            returnPermissionValue = "N";
                            mTempPermissionThreadFlag = 0;
                        }

                        break;
                    }

                    // 파일읽기 관련
                    case 2000 : {
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/

                        Log.i("PermissionTest", "파일읽기 grantResults.length (rq) : " + grantResults.length + "\n");
                        Log.i("PermissionTest", "파일읽기 grantResults[0] (rq) : " + grantResults[0] + "\n");
                        Log.i("PermissionTest", "파일읽기 PackageManager.PERMISSION_GRANTED (rq) : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "파일읽기 ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) (rq) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) + "\n");

                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                            /**
                             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                             //startActivity(intent);
                             Log.i("PermissionTest", "파일읽기 부여 (rq) : YES\n");
                             }
                             **/

                            Log.i("PermissionTest", "파일읽기 부여 (rq) : YES\n");

                            returnPermissionValue = "Y";
                            mTempPermissionThreadFlag = 0;
                        }
                        else {
                            Toast.makeText(activity, "권한 요청을 거부했습니다.(rq)", Toast.LENGTH_SHORT).show();
                            Log.i("PermissionTest", "파일읽기 부여 (rq) : NO\n");

                            returnPermissionValue = "N";
                            mTempPermissionThreadFlag = 0;
                        }

                        break;
                    }

                    // 파일쓰기 관련
                    case 3000 : {
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/

                        Log.i("PermissionTest", "파일쓰기 grantResults.length (rq) : " + grantResults.length + "\n");
                        Log.i("PermissionTest", "파일쓰기 grantResults[0] (rq) : " + grantResults[0] + "\n");
                        Log.i("PermissionTest", "파일쓰기 PackageManager.PERMISSION_GRANTED (rq) : " + PackageManager.PERMISSION_GRANTED + "\n");
                        Log.i("PermissionTest", "파일쓰기 ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) (rq) : " +
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) + "\n");

                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                            /**
                             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                             //startActivity(intent);
                             Log.i("PermissionTest", "파일쓰기 부여 (rq) : YES\n");
                             }
                             **/

                            Log.i("PermissionTest", "파일쓰기 부여 (rq) : YES\n");

                            returnPermissionValue = "Y";
                            mTempPermissionThreadFlag = 0;
                        }
                        else {
                            Toast.makeText(activity, "권한 요청을 거부했습니다.(rq)", Toast.LENGTH_SHORT).show();
                            Log.i("PermissionTest", "파일쓰기 부여 (rq) : NO\n");

                            returnPermissionValue = "N";
                            mTempPermissionThreadFlag = 0;
                        }

                        break;
                    }
                }

                // ---------------------------------------------------------------------------------
                // 2. 작업이 끝나면 이 핸들러를 호출 -----------------------------------------------
                handler.sendEmptyMessage(0);
                // ---------------------------------------------------------------------------------
            }
        });
        thread.start();



    }



}
