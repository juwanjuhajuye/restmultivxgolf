package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.star;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;

import java.util.ArrayList;

import static com.starmicronics.starioextension.StarIoExt.Emulation;

public class StarPrinterHelper {

    public static final String TAG = "StarPrinterHelper";

    // Our singleton instance.  If we want to support multiple printers, we could keep a HashMap
    // of ports to instances here.  But for now, we just have the one.
    private static StarPrinterHelper myInstance;
    private StarIoExtManager starIoExtManager;

    // Constants for setting up the printer
    private static final String PRINTER_PORT_SETTINGS = "";
    private static final int TIMEOUT_MS = 10000;

    // Printer info
    public static final Emulation emulation = Emulation.StarGraphic;

    public enum Result {
        Success,
        ErrorUnknown,
        ErrorOpenPort,
        ErrorBeginCheckedBlock,
        ErrorEndCheckedBlock,
        ErrorWritePort,
        ErrorReadPort,
    }

    public interface SendCallback {
        void onStatus(boolean result, Result communicateResult);
    }

    /**
     *  This is a generic callback for printer sends.  All it does is log messages.  If you want to
     *  handle callbacks in the main code, you can create a generic callback/notification handler,
     *  and have this callback invoke it.  Bear in mind that it should be generic, since not every
     *  platform and peripheral will use it.
     */
    public static SendCallback defaultSendCallback = new SendCallback() {
        @Override
        public void onStatus(boolean result, Result communicateResult) {
            String msg;

            switch (communicateResult) {
            case Success :                msg = "Success!";                               break;
            case ErrorOpenPort:           msg = "Fail to openPort";                       break;
            case ErrorBeginCheckedBlock:  msg = "Printer is offline (beginCheckedBlock)"; break;
            case ErrorEndCheckedBlock:    msg = "Printer is offline (endCheckedBlock)";   break;
            case ErrorReadPort:           msg = "Read port error (readPort)";             break;
            case ErrorWritePort:          msg = "Write port error (writePort)";           break;
            default:                      msg = "Unknown error";                          break;
            }

            Log.d(TAG, "result = " + msg);
        }
    };

    // Set this class up as a singleton
    private StarPrinterHelper(String portName, Context context) {
        starIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, portName,
                PRINTER_PORT_SETTINGS, TIMEOUT_MS, context);

        starIoExtManager.setCashDrawerOpenActiveHigh(true);
    }

    public static synchronized StarPrinterHelper getInstance(String portName, Context context) {
        if (myInstance == null) {
            myInstance = new StarPrinterHelper(portName, context);
        }
        return myInstance;
    }

    public static String getPrinterPortName(Context ctx) {
        try {
            ArrayList<PortInfo> portInfos;
            portInfos = StarIOPort.searchPrinter("USB:", ctx);
            if (portInfos.size() == 0) {
                return null;
            }
            return portInfos.get(0).getPortName();
        }
        catch (StarIOPortException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void connect(StarPrinterCallback callback) {
        starIoExtManager.setListener(callback);
        starIoExtManager.connect(callback);
    }

    public void disconnect(StarPrinterCallback callback) {
        starIoExtManager.disconnect(callback);
        starIoExtManager.setListener(null);
    }

    private byte[] createOpenDrawerData() {

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();
        builder.appendPeripheral(ICommandBuilder.PeripheralChannel.No1);
        builder.endDocument();

        return builder.getCommands();
    }

     public Boolean getPrinterPaperStatus() {
        StarIoExtManager.PrinterPaperStatus paper_status = starIoExtManager.getPrinterPaperReadyStatus();

        switch(paper_status) {
            case Ready:
            case NearEmpty:
                return true;

            default:
                return false;
        }
    }

    private void sendCommands(byte[] commands, SendCallback callback) {
        SendCommandThread thread = new SendCommandThread(commands, callback);
        thread.start();
    }

    private void sendCommandsUnchecked(byte[] commands, SendCallback callback) {
        SendCommandThread thread = new SendCommandThread(commands, callback, true);
        thread.start();
    }

    public void print(byte[] data, SendCallback cb) {
        sendCommands(data, cb);
    }

    public void openDrawer(SendCallback cb) {
        byte[] cmd;
        cmd = createOpenDrawerData();
        sendCommandsUnchecked(cmd, cb);
    }

    private class SendCommandThread extends Thread {
        private SendCallback mCallback;
        private byte[] mCommands;

        private StarIOPort mPort;

        private String mPortName = null;
        private boolean mEnableCheckedBlock = true;

        SendCommandThread(byte[] commands, SendCallback callback) {
            mCommands     = commands;
            mCallback     = callback;
            mEnableCheckedBlock    = true;
        }

        SendCommandThread(byte[] commands, SendCallback callback, boolean disableCheckedBlock) {
            mCommands              = commands;
            mCallback              = callback;
            mEnableCheckedBlock    = (! disableCheckedBlock);
        }

        @Override
        public void run() {
            Result communicateResult = Result.ErrorOpenPort;
            boolean result = false;

            synchronized (starIoExtManager) {
                try {
                    StarPrinterStatus status;
                    mPort = starIoExtManager.getPort();

                    if(mPort == null) {
                        throw new StarIOPortException("The printer is offline");
                    }

                    if(mEnableCheckedBlock) {
                        communicateResult = Result.ErrorBeginCheckedBlock;
                        status = mPort.beginCheckedBlock();
                        if (status.offline) {
                            throw new StarIOPortException("The printer is offline");
                        }
                    }

                    communicateResult = Result.ErrorWritePort;

                    mPort.writePort(mCommands, 0, mCommands.length);

                    if(mEnableCheckedBlock) {
                        communicateResult = Result.ErrorEndCheckedBlock;
                        mPort.setEndCheckedBlockTimeoutMillis(30000);     // 30000mS!!!
                        status = mPort.endCheckedBlock();

                        if (status.coverOpen) {
                            throw new StarIOPortException("Printer cover is open");
                        } else if (status.receiptPaperEmpty) {
                            throw new StarIOPortException("Receipt paper is empty");
                        } else if (status.offline) {
                            throw new StarIOPortException("Printer is offline");
                        }
                    }

                    result = true;
                    communicateResult = Result.Success;
                } catch (StarIOPortException e) {
                    Log.e(TAG, "Exception while sending command", e);
                }

                if (mPort != null && mPortName != null) {
                    try {
                        StarIOPort.releasePort(mPort);
                    } catch (StarIOPortException e) {
                        // Nothing
                        Log.e(TAG, "Exception while releasing port");
                    }
                    mPort = null;
                } else {
                    Log.e(TAG, "Port or PortName is null");
                }

                resultSendCallback(result, communicateResult, mCallback);
            }
        }

        private void resultSendCallback(final boolean result, final Result communicateResult, final SendCallback callback) {
            if (callback != null) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onStatus(result, communicateResult);
                    }
                });
            }
        }
    }
}
