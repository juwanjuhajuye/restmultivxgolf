package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.register.printer.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by elo on 14/9/17.
 */

public class PrinterAdapter1_0 implements PrinterAdapter {

    private SerialPort serialPort;
    private static final String SERIAL_OPEN_ERROR = "Failed to open printer serial port";

    PrinterAdapter1_0() {
        try {
            serialPort = new SerialPort(new File("/dev/ttymxc1"), 9600, 0);
        } catch (IOException e) {
            // Do nothing in constructor.  If we failed to open the serial port,
            // throw an exception when a printer method is called
        }
    }

    @Override
    public boolean hasPaper() throws IOException {
        if (serialPort == null) {
            throw new IOException(SERIAL_OPEN_ERROR);
        }
        InputStream iStream = serialPort.getInputStream();

        byte[] command = {0x10, 0x04, 0x04};
        print(command);
        String result = Integer.toHexString(iStream.read());
        return result.contains("12");
    }

    @Override
    public void print(String stringData) throws IOException {
        print(stringData.getBytes());
    }

    @Override
    public void print(byte[] byteData) throws IOException {
        if (serialPort == null) {
            throw new IOException(SERIAL_OPEN_ERROR);
        }
        OutputStream oStream = serialPort.getOutputStream();
        oStream.write(byteData, 0, byteData.length);
        oStream.flush();
    }
}
