package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.register.barcodereader.BarcodeReader;

/**
 * Created by elo on 14/9/17.
 */

public class BarCodeReaderAdapter1_0 implements BarCodeReaderAdapter {

    private BarcodeReader barcodeReader;

    BarCodeReaderAdapter1_0() {
        this.barcodeReader = new BarcodeReader();
    }

    @Override
    public boolean isBarCodeReaderEnabled() {
        return false;
    }

    @Override
    public void setBarCodeReaderEnabled(boolean enabled) {
        barcodeReader.turnOnLaser();
    }

    @Override
    public boolean isBarCodeReaderKbdMode() {
        return false;
    }

    @Override
    public void setBarCodeReaderKbdMode() {
        return; // In 1.0 devices we do not have KbdMode
    }
}
