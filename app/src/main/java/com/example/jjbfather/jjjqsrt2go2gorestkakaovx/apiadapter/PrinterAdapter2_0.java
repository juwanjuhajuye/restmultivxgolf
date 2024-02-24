package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import android.content.Context;
import android.util.Log;

import com.elo.device.DeviceManager;
import com.elo.device.peripherals.Printer;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.star.StarPrinterCallback;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.star.StarPrinterHelper;
import com.starmicronics.starioextension.IConnectionCallback;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by elo on 9/8/17.
 */

public class PrinterAdapter2_0 implements PrinterAdapter, Observer {
    private static final String TAG = "PrinterAdapter2_0";

    private Printer printer;
    private CommonUtil2_0 commonUtil;
    private ActivityMonitor activityMonitor;
    private StarPrinterCallback starPrinterCallback;
    private StarPrinterHelper starPrinterHelper;
    private boolean starPrinterConnected;

    /**
     *  Package-private constructor
     */
    PrinterAdapter2_0(DeviceManager deviceManager, CommonUtil2_0 commonUtil, ActivityMonitor activityMonitor) {
        this.printer = deviceManager.getPrinter();
        this.commonUtil = commonUtil;
        this.activityMonitor = activityMonitor;
        init();
    }

    @Override
    public boolean hasPaper() throws IOException {
        if (starPrinterConnected)
            return starPrinterHelper.getPrinterPaperStatus();
        else
            return false;
    }

    @Override
    public void print(String stringData) throws IOException {
        print(stringData.getBytes());
    }

    @Override
    public void print(byte[] byteData) throws IOException {
        if (!starPrinterConnected) {
            throw new RuntimeException("Printer not connected, cannot perform operation");
        }
        starPrinterHelper.print(byteData, StarPrinterHelper.defaultSendCallback);
    }

    /**
     * We've been notified of an Activity lifecycle update.  This should only be called by an Observable
     *
     * @param observable  Observable doing the notification
     * @param o  String corresponding to event
     */
    @Override
    public void update(Observable observable, Object o) {
        // If we weren't able to find a printer, don't even bother
        if (starPrinterHelper == null) return;

        String event = (String) o;
        if (ActivityMonitor.EVENT_ON_START.equals(event)) {
            Log.d("TAG", "Connecting to the printer");
            starPrinterHelper.connect(starPrinterCallback);
        } else if (ActivityMonitor.EVENT_ON_STOP.equals(event)) {
            Log.d("TAG", "Disconnecting from the printer");
            starPrinterHelper.disconnect(starPrinterCallback);
        }
    }

    private void init() {
        // Set the printer connect notification callback
        starPrinterCallback = new StarPrinterCallback() {
            @Override
            public void onDisconnected() {
                super.onDisconnected();
                starPrinterConnected = false;
                commonUtil.setStarPrinterConnected(false);
            }
            @Override
            public void onConnected(IConnectionCallback.ConnectResult connectResult) {
                super.onConnected(connectResult);
                starPrinterConnected = true;
                commonUtil.setStarPrinterConnected(true);
            }
            @Override
            public void onPrinterImpossible() {
                super.onPrinterImpossible();
                Log.e(TAG, "STAR printer is being impossible today");
                starPrinterConnected = false;
                commonUtil.setStarPrinterConnected(false);
            }
        };

        // Initialize a STAR printer, if present in the system
        Context context = commonUtil.getContext();
        String printerPortName = StarPrinterHelper.getPrinterPortName(context);
        if (printerPortName != null) {
            starPrinterHelper = StarPrinterHelper.getInstance(printerPortName, context);
        }

        // Observe the MainActivity.  We want to play nice by connecting when the activity
        // starts, and disconnecting when it stops.  This makes it so that when our activity
        // is in the background, other Activities can connect to the printer
        activityMonitor.addObserver(this);
    }
}
