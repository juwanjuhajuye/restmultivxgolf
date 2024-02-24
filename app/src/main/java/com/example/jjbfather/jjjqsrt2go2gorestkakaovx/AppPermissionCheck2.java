package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.R;

public class AppPermissionCheck2 extends AppCompatActivity {
    Activity mActivity;
    Context mContext;

    Activity mParamActivity;
    Context mParamContext;

    private static final int REQUEST_CALL_PHONE = 3;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 1;

    private String mCloseType = "N";

    public AppPermissionCheck2() {
    }

    public AppPermissionCheck2(Activity paramActivity, Context paramContext) {
        this.mActivity = paramActivity;
        this.mContext = paramContext;

        mCloseType = "N";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_permission_check2);
    }

    public void openQRCodeReaderAtPermissionClass() {
        GlobalMemberValues.openQRCodeReader(MainActivity.mActivity, MainActivity.mContext, MainActivity.CALL_ZXING_RESULT_DISCOUNT);
    }

    public void permissionCheck(Activity paramActivity, Context paramContext, String paramCheckType, String paramCloseType) {
        mParamActivity = paramActivity;
        mContext = mParamContext;
        mCloseType = paramCloseType;

        switch (paramCheckType) {
            case "CAMERA" : {
                int permissionCamera = ContextCompat.checkSelfPermission(paramContext, android.Manifest.permission.CAMERA);
                if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                            paramActivity, new String[]{
                                    android.Manifest.permission.CAMERA
                            }, REQUEST_CAMERA
                    );
                } else {
                    // 퍼미션 허용.....
                    // QR 코드 리더 열기
                    openQRCodeReaderAtPermissionClass();
                }
                break;
            }
            case "STORAGE" : {
                int permissionReadStorage = ContextCompat.checkSelfPermission(paramContext, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                int permissionWriteStorage = ContextCompat.checkSelfPermission(paramContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                            paramActivity, new String[] {
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, REQUEST_EXTERNAL_STORAGE
                    );
                } else {
                    // 퍼미션 허용
                }
                break;
            }
            case "CALLPHONE" : {
                int permissionPhone = ContextCompat.checkSelfPermission(paramContext, android.Manifest.permission.CALL_PHONE);
                if(permissionPhone == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(
                                paramActivity, new String[] {android.Manifest.permission.CALL_PHONE
                                }, REQUEST_CALL_PHONE

                        );
                    }
                } else {
                    // 퍼미션 허용
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA : {
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "Camera permission authorized", Toast.LENGTH_SHORT).show();
                            // QR 코드 리더 열기
                            openQRCodeReaderAtPermissionClass();
                        } else {
                            Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            case REQUEST_EXTERNAL_STORAGE : {
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "Read/write storage permission authorized", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Read/write storage permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            case REQUEST_CALL_PHONE: {
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.CALL_PHONE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "Call phone permission authorized", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Call phone permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }

        if (mCloseType.equals("Y") || mCloseType == "Y") {
            mParamActivity.finish();
        }
    }
}
