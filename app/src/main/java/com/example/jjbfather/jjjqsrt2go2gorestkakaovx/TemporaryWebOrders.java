package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-27.
 */

public class TemporaryWebOrders {
    public String idx, salesCode, customerId, customerName, customerPhone, customerEmail;
    public String customerAddr1, customerAddr2, customerCity, customerState, customerZip;
    public String deliveryday, deliverytime, deliverydate, customermemo, couponnumber;
    public String deliverytakeaway, discount, discountTxt;
    public String giftcardNumberUsed, giftcardPriceUsed;

    public String CardPriceUsed, CashPriceUsed, PointPriceUsed, DeliveryStatus, TakeawayStatus, ReceiptJson;
    public String SaleDate, Status;
    public String SalesItems, KitchenprintedYN, DeliveryPickupFee, CustomerOrderNumber;
    public String CustomerAddressAll;
    public String UseDcomYN;
    public String CarInfo_License, CarInfo_Color, CarInfo_Type, CarInfo_ParkingSpace;
    public String onlinetype;

    public String Pickupdone;

    // 10112023
    public String orderfrom, salescodethirdparty;

    public TemporaryWebOrders(String[] tempArray) {
        this.idx = tempArray[0];
        this.salesCode = tempArray[1];
        this.customerId = tempArray[2];
        this.customerName = tempArray[3];
        this.customerPhone = tempArray[4];
        this.customerEmail = tempArray[5];

        this.customerAddr1 = tempArray[6];
        this.customerAddr2 = tempArray[7];
        this.customerCity = tempArray[8];
        this.customerState = tempArray[9];
        this.customerZip = tempArray[10];

        this.deliveryday = tempArray[11];
        this.deliverytime = tempArray[12];
        this.deliverydate = tempArray[13];
        this.customermemo = tempArray[14];
        this.couponnumber = tempArray[15];
        this.deliverytakeaway = tempArray[16];
        this.discount = tempArray[17];
        this.discountTxt = tempArray[18];

        this.giftcardNumberUsed = tempArray[19];
        this.giftcardPriceUsed = tempArray[20];

        this.CardPriceUsed = tempArray[21];
        this.CashPriceUsed = tempArray[22];
        this.PointPriceUsed = tempArray[23];
        this.DeliveryStatus = tempArray[24];
        this.TakeawayStatus = tempArray[25];
        this.ReceiptJson = tempArray[26];
        this.SaleDate = tempArray[27];
        this.Status = tempArray[28];

        this.SalesItems = tempArray[29];
        this.KitchenprintedYN = tempArray[30];

        this.DeliveryPickupFee = tempArray[31];

        this.CustomerOrderNumber = tempArray[32];
        this.CustomerAddressAll = tempArray[33];
        this.UseDcomYN = tempArray[34];
        this.CarInfo_License = tempArray[35];
        this.CarInfo_Color = tempArray[36];
        this.CarInfo_Type = tempArray[37];
        this.CarInfo_ParkingSpace = tempArray[38];

        if (tempArray.length > 39) {
            this.Pickupdone = tempArray[39];
        }
        if (tempArray.length > 40) {
            this.onlinetype = tempArray[40];
        }

        // 10112023
        if (tempArray.length > 41) {
            this.orderfrom = tempArray[41];
        }
        if (tempArray.length > 42) {
            this.salescodethirdparty = tempArray[42];
        }
    }
}
