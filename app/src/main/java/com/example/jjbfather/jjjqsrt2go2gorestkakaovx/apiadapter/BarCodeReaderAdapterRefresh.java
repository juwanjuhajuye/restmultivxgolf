package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.elo.device.DeviceManager;
import com.elo.device.enums.Status;
import com.elo.device.peripherals.BarCodeReader;

/**
 * Created by oren on 9/11/17.
 */

public class BarCodeReaderAdapterRefresh implements BarCodeReaderAdapter {

    BarCodeReader barCodeReader;

    BarCodeReaderAdapterRefresh(DeviceManager deviceManager) {
        this.barCodeReader = deviceManager.getBarCodeReader();
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
        return true;    // Numa reader is always in keyboard mode
    }

    @Override
    public void setBarCodeReaderKbdMode() {
        // No-op
    }
}
