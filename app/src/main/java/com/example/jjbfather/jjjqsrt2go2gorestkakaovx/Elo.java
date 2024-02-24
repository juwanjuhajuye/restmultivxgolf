package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import com.elo.device.ProductInfo;
import com.elo.device.enums.EloPlatform;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.ApiAdapter;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-01.
 */

public class Elo {
    public static Context mContext;

    public static ProductInfo mProductInfo;
    public static ApiAdapter mApiAdapter;

    public enum PrinterMake {mSTAR, mRONGTA};
    public static PrinterMake mMyPrinter;

    public Elo(Context paramContext) {
        mContext = paramContext;
    }

    // Elo 관련 초기화
    public static void eloInit() {
        if (MainActivity.mApiAdapter == null) {
            MainActivity.setEloInit();
        }

        mProductInfo = MainActivity.mProductInfo;
        EloPlatform platform = mProductInfo.eloPlatform;
        mApiAdapter = MainActivity.mApiAdapter;

        switch (platform) {  // TODO: Base off detection, not platform
            case PAYPOINT_1:
            case PAYPOINT_REFRESH:
                mMyPrinter = PrinterMake.mRONGTA;
                break;
            case PAYPOINT_2:
                mMyPrinter = PrinterMake.mSTAR;
                break;
            default:
                mMyPrinter = null;
        }
    }
}