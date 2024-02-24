package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import android.content.Context;

import com.elo.device.DeviceManager;
import com.elo.device.enums.EloPlatform;
import com.elo.device.exceptions.UnsupportedEloPlatform;

/**
 * Created by elo on 9/8/17.
 */

public class ApiAdapterFactory {

    private static ApiAdapterFactory instance;      // Singleton instance

    Context context;

    private ApiAdapterFactory(Context context) {
        this.context = context;
    }

    public static ApiAdapterFactory getInstance(Context context) {
        synchronized (ApiAdapterFactory.class) {
            if (instance == null) {
                instance = new ApiAdapterFactory(context);
            }
        }
        return instance;
    }

    public ApiAdapter getApiAdapter(EloPlatform platform) {
        ApiAdapterImpl apiAdapterImpl = new ApiAdapterImpl();
        try {
            switch (platform) {
            case PAYPOINT_1:
                initApiAdapter1_0(apiAdapterImpl);
                break;
            case PAYPOINT_REFRESH:
                initApiAdapterRefresh(apiAdapterImpl);
                break;
            case PAYPOINT_2:
                initApiAdapter2_0(apiAdapterImpl);
                break;
            default:
                return null;
            }
        } catch (UnsupportedEloPlatform e) {
            return null;
        }
        return apiAdapterImpl;
    }

    private void initApiAdapter2_0(ApiAdapterImpl apiAdapterImpl) throws UnsupportedEloPlatform {
        DeviceManager deviceManager = DeviceManager.getInstance(EloPlatform.PAYPOINT_2, context);
        CommonUtil2_0 commonUtil = new CommonUtil2_0(context);
        ActivityMonitor activityMonitor = new ActivityMonitor();

        apiAdapterImpl.setActivityMonitor(activityMonitor);
        apiAdapterImpl.setCashDrawerAdapter(new CashDrawerAdapter2_0(deviceManager, commonUtil));
        apiAdapterImpl.setPrinterAdapter(new PrinterAdapter2_0(deviceManager, commonUtil, activityMonitor));
        apiAdapterImpl.setCfdAdapter(new CfdAdapterRefreshOr2_0(deviceManager));
        apiAdapterImpl.setBarCodeReaderAdapter(new BarCodeReaderAdapter2_0(deviceManager, commonUtil, activityMonitor));
        apiAdapterImpl.setFtdiAdapter(new FtdiAdapterCommon(deviceManager));
    }

    private void initApiAdapterRefresh(ApiAdapterImpl apiAdapterImpl) throws UnsupportedEloPlatform {
        DeviceManager deviceManager = DeviceManager.getInstance(EloPlatform.PAYPOINT_REFRESH, context);
        CommonUtil2_0 commonUtil = new CommonUtil2_0(context);
        ActivityMonitor activityMonitor = new ActivityMonitor();

        apiAdapterImpl.setActivityMonitor(activityMonitor);
        apiAdapterImpl.setCashDrawerAdapter(new CashDrawerAdapterRefresh(deviceManager));
        apiAdapterImpl.setPrinterAdapter(new PrinterAdapterRefresh(deviceManager));
        apiAdapterImpl.setCfdAdapter(new CfdAdapterRefreshOr2_0(deviceManager));
        apiAdapterImpl.setBarCodeReaderAdapter(new BarCodeReaderAdapterRefresh(deviceManager));
        apiAdapterImpl.setFtdiAdapter(new FtdiAdapterCommon(deviceManager));
    }

    private void initApiAdapter1_0(ApiAdapterImpl apiAdapterImpl){
        ActivityMonitor activityMonitor = new ActivityMonitor();

        apiAdapterImpl.setActivityMonitor(activityMonitor);
        apiAdapterImpl.setCashDrawerAdapter(new CashDrawerAdapter1_0());
        apiAdapterImpl.setCfdAdapter(new CfdAdapter1_0());
        apiAdapterImpl.setBarCodeReaderAdapter(new BarCodeReaderAdapter1_0());
        apiAdapterImpl.setPrinterAdapter(new PrinterAdapter1_0());
        apiAdapterImpl.setFtdiAdapter(new FtdiAdapterNull()); // TODO:  Need real FTDI adapter.
    }
}
