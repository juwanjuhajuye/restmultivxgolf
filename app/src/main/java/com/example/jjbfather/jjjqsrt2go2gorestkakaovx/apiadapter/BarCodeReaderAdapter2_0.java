package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.elo.device.DeviceManager;
import com.elo.device.enums.Status;
import com.elo.device.peripherals.BarCodeReader;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by oren on 9/11/17.
 */

public class BarCodeReaderAdapter2_0 implements BarCodeReaderAdapter, Observer {

    BarCodeReader barCodeReader;
    CommonUtil2_0 commonUtil2_0;
    ActivityMonitor activityMonitor;

    BarCodeReaderAdapter2_0(DeviceManager deviceManager, CommonUtil2_0 commonUtil2_0, ActivityMonitor activityMonitor) {
        this.barCodeReader = deviceManager.getBarCodeReader();
        this.commonUtil2_0 = commonUtil2_0;
        this.activityMonitor = activityMonitor;
    }

    /**
     * We've been notified of an Activity lifecycle update.  This should only be called by an Observable
     *
     * @param observable  Observable doing the notification
     * @param o  String corresponding to event
     */
    @Override
    public void update(Observable observable, Object o) {
        String event = (String) o;
        if (ActivityMonitor.EVENT_ON_PAUSE.equals(event)) {
            if (barCodeReader.getStatus().equals(Status.ENABLED)) {
                barCodeReader.setEnabled(false);
            }
        }
    }

    @Override
    public boolean isBarCodeReaderEnabled() {
        return barCodeReader.getStatus() == Status.ENABLED;
    }

    @Override
    public void setBarCodeReaderEnabled(boolean enabled) {
        barCodeReader.setEnabled(enabled);
    }

    @Override
    public boolean isBarCodeReaderKbdMode() {
        return barCodeReader.isKbMode(commonUtil2_0.getContext());
    }

    @Override
    public void setBarCodeReaderKbdMode() {
        barCodeReader.setKbMode(commonUtil2_0.getContext());
    }
}
