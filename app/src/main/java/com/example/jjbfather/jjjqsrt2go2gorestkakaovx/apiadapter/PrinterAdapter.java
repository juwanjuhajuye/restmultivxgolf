package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import java.io.IOException;

/**
 * Created by oren on 9/8/17.
 */

public interface PrinterAdapter {

    public boolean hasPaper() throws IOException;

    // Caller can use either of these methods.  If the wrong method is used for the underlying
    // hardware, the adapter should convert the data to the other type.  But this way, if the
    // native type is known ahead of time, we can avoid unnecessary costly conversion.
    public void print(String stringData) throws IOException;
    public void print(byte[] byteData) throws IOException;
}
