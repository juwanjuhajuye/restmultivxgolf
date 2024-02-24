package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.elo.device.DeviceManager;
import com.elo.device.peripherals.Printer;

import java.io.IOException;

/**
 * Created by elo on 9/8/17.
 */

public class PrinterAdapterRefresh implements PrinterAdapter {
    private static final String TAG = "PrinterAdapter2_0";

    private Printer printer;

    /**
     *  Package-private constructor
     */
    PrinterAdapterRefresh(DeviceManager deviceManager) {
        this.printer = deviceManager.getPrinter();
    }

    @Override
    public boolean hasPaper() throws IOException {
        return printer.hasPaper();
    }

    @Override
    public void print(String stringData) throws IOException {
        printer.print(stringData);
    }

    @Override
    public void print(byte[] byteData) throws IOException {
        print(new String(byteData));
    }
}
