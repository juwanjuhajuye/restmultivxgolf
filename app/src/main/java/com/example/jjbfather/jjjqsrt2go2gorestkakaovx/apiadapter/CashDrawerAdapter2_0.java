package com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter;

import android.content.Context;

import com.elo.device.DeviceManager;
import com.elo.device.peripherals.CashDrawer;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.apiadapter.star.StarPrinterHelper;

/**
 * Created by elo on 9/8/17.
 */

public class CashDrawerAdapter2_0 implements CashDrawerAdapter {

    private CashDrawer cashDrawer;
    private CommonUtil2_0 commonUtil;
    private StarPrinterHelper starPrinterHelper;

    /**
     *  Package-private constructor
     */
    CashDrawerAdapter2_0(DeviceManager deviceManager, CommonUtil2_0 commonUtil) {
        this.cashDrawer = deviceManager.getCashDrawer();
        this.commonUtil = commonUtil;

        // Get an instance of the printer helper so we can communicate to it
        Context context = commonUtil.getContext();
        String printerPortName = StarPrinterHelper.getPrinterPortName(context);
        if (printerPortName != null) {
            starPrinterHelper = StarPrinterHelper.getInstance(printerPortName, context);
        }
    }

    @Override
    public boolean isCashDrawerOpen() {
        return cashDrawer.isOpen();
    }

    @Override
    public void openCashDrawer() {
        if (!commonUtil.isStarPrinterConnected()) {
            throw new RuntimeException("Printer not connected, cannot perform operation");
        }
        starPrinterHelper.openDrawer(StarPrinterHelper.defaultSendCallback);
    }
}
