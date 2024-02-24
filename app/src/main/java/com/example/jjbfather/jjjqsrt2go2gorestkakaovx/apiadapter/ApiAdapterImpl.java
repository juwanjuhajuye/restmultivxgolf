package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.elo.device.DeviceManager;

import java.io.IOException;

/**
 * Created by elo on 9/8/17.
 *
 * The implementation of the API adapter.  It consists of delegates that invoke the per-peripheral
 * methods.
 *
 * Note that the constructor of this class is package-private.  This implementation should only be
 * created by the factory class.  The factory will set appropriate delegate classes; these setters
 * are also package-private.
 */

public class ApiAdapterImpl implements ApiAdapter {

    private DeviceManager deviceManager;

    // The Activity Monitor, if it's needed
    private ActivityMonitor activityMonitor;

    // Delegates for peripheral-specific adapters
    private CashDrawerAdapter cashDrawerAdapter;
    private PrinterAdapter printerAdapter;
    private CfdAdapter cfdAdapter;
    private BarCodeReaderAdapter barCodeReaderAdapter;
    private FtdiAdapter ftdiAdapter;

    ApiAdapterImpl() {
    }

    public ActivityMonitor getActivityMonitor() {
        return activityMonitor;
    }

    void setActivityMonitor(ActivityMonitor activityMonitor) {
        this.activityMonitor = activityMonitor;
    }

    /*
     *  CashDrawer delegate and setter methods
     */
    void setCashDrawerAdapter(CashDrawerAdapter cashDrawerAdapter) {
        this.cashDrawerAdapter = cashDrawerAdapter;
    }

    @Override
    public boolean isCashDrawerOpen() {
        return cashDrawerAdapter.isCashDrawerOpen();
    }

    @Override
    public void openCashDrawer() {
        cashDrawerAdapter.openCashDrawer();
    }

    /*
     *  Printer delegate and setter methods
     */
    void setPrinterAdapter(PrinterAdapter printerAdapter) {
        this.printerAdapter = printerAdapter;
    }

    @Override
    public boolean hasPaper() throws IOException {
        return printerAdapter.hasPaper();
    }

    @Override
    public void print(String stringData) throws IOException {
        printerAdapter.print(stringData);
    }

    @Override
    public void print(byte[] byteData) throws IOException {
        printerAdapter.print(byteData);
    }

    /*
     *  CFD delegate and setter methods
     */
    void setCfdAdapter(CfdAdapter cfdAdapter) {
        this.cfdAdapter = cfdAdapter;
    }

    @Override
    public void cfdSetBacklight(boolean on) {
        cfdAdapter.cfdSetBacklight(on);
    }

    @Override
    public void cfdClear() {
        cfdAdapter.cfdClear();
    }

    @Override
    public void cfdSetLine(int line, String text) {
        cfdAdapter.cfdSetLine(line, text);
    }

    /*
     *  Barcode Scanner delegate and setter methods
     */
    void setBarCodeReaderAdapter(BarCodeReaderAdapter barCodeReaderAdapter) {
        this.barCodeReaderAdapter = barCodeReaderAdapter;
    }

    @Override
    public boolean isBarCodeReaderEnabled() {
        return barCodeReaderAdapter.isBarCodeReaderEnabled();
    }

    @Override
    public void setBarCodeReaderEnabled(boolean enabled) {
        barCodeReaderAdapter.setBarCodeReaderEnabled(enabled);
    }

    @Override
    public boolean isBarCodeReaderKbdMode() {
        return barCodeReaderAdapter.isBarCodeReaderKbdMode();
    }

    @Override
    public void setBarCodeReaderKbdMode() {
        barCodeReaderAdapter.setBarCodeReaderKbdMode();
    }

    /*
     *  FTDI delegate and setter methods.
     *  TODO:  This is temporary, because the methods should do more high-level work
     */
    void setFtdiAdapter(FtdiAdapter ftdiAdapter) {
        this.ftdiAdapter = ftdiAdapter;
    }

    @Override
    public String[] getFtdiDevicesList() {
        return ftdiAdapter.getFtdiDevicesList();
    }

    @Override
    public int ftdiOpen(String path, int baudrate, int flags) {
        return ftdiAdapter.ftdiOpen(path, baudrate, flags);
    }

    @Override
    public void ftdiClose(int fd) {
        ftdiAdapter.ftdiClose(fd);
    }

    @Override
    public int ftdiRead(int fd, byte[] data) {
        return ftdiAdapter.ftdiRead(fd, data);
    }

    @Override
    public void ftdiWrite(int fd, byte[] data, int length) {
        ftdiAdapter.ftdiWrite(fd, data, length);
    }
}
