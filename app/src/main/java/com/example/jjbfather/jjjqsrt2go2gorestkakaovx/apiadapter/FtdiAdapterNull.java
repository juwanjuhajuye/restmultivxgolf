package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

/**
 * Created by elo on 9/14/17.
 *
 * TODO:  Temporary Null adapter for use on Paypoint 1.0
 */

public class FtdiAdapterNull implements FtdiAdapter {
    FtdiAdapterNull() {
    }

    @Override
    public String[] getFtdiDevicesList() {
        return new String[0];
    }

    @Override
    public int ftdiOpen(String path, int baudrate, int flags) {
        return 0;
    }

    @Override
    public void ftdiClose(int fd) {
    }

    @Override
    public int ftdiRead(int fd, byte[] data) {
        return 0;
    }

    @Override
    public void ftdiWrite(int fd, byte[] data, int length) {
    }
}
